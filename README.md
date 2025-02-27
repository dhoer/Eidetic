# Eidetic

## General Overview

Eidetic is an automated backup system, which interacts with Amazon Web Services API to programmatically take a backup of a marked EBS volume by utilizing Amazons EBS Snapshot feature. The EBS volume is marked by the addition of a tag. The tag key allows Eidetic to find the volume through search filtering functionality provided by Amazon and the tag value specifies actions and methods Eidetic will use to interact with the aforementioned EBS volume. There are multiple Eidetic extensions which enhance functionality including extra region redundancy and snapshot validation. Each piece of the Eidetic follows:

List of Eidetic Automated Backup System components:

- Eidetic
- Eidetic Express
- Amnesia
- Eidetic Checker
- Snapshot Time Poller
- Tag Checker
- Error Checker

You are allowed to have any combination of components to run at any time. You can make your choice via the application.properties configuration file. 

Eidetic has the capability to run over multiple AWS accounts. 

logback_config.xml has the parameters for the logging of Eidetic. Logging is an essential aspect to Eidetic as it alerts when there are misconfigured tags, snapshots in error states, or any general error when running the process. Eidetic will also write out general amazon API interaction information. Processing these logs with systems such as Splunk or Logstash would be best practice.
  
  
## Component Overview

### Eidetic  
	
Eidetic is the heart of the Eidetic automated backup system. The main process which will filter tagged volumes by keys and acts accordingly. When passed an AWS account in the application.properties file, it will iterate through each region provided by Amazon searching for volumes to act upon. To interact with Eidetic:

Create tags in this format (tag-key : tag-value): 

 `Eidetic : { “CreateSnapshot” : { “Interval” : “x”, “Retain” : “y” } }`

where:

- x: the interval, possible choices are hour, day, week, or month

- y: the quantity of snapshots to retain, values range from a minimum of two and upwards

  
  
Optional: add extra parameter, “RunAt” to “day” interval. 

`{ “CreateSnapshot” : { “Interval” : “day”, “Retain” : “y”,  “RunAt” : “z”} }`

where:

- day: The interval must be set to “day”

- y: the quantity of snapshots to retain, values range from a minimum of two and upwards

- z: the time to take the snapshot. Values range from “00:00:00” to “23:59:59”

Example Tags:

```
Eidetic : { “CreateSnapshot” : { “Interval” : “hour”, “Retain” : “24” } }

Eidetic : { “CreateSnapshot” : { “Interval” : “day”, “Retain” : “10” } }

Eidetic : { “CreateSnapshot” : { “Interval” : “week”, “Retain” : “2” } }

Eidetic : { “CreateSnapshot” : { “Interval” : “day”, “Retain” : “5”,  “RunAt” : “09:30:00”} }

Eidetic : { “CreateSnapshot” : { “Interval” : “day”, “Retain” : “7”,  “RunAt” : “18:45:00”} }

```
  
  
### Eidetic Express

Eidetic Express is an extension to Eidetic where Eidetic tagged EBS volumes have an additional parameter inside the Eidetic tag value. Eidetic Express will then ship the created Eidetic snapshots from one region to another region for redundancy purposes. 

As an important note, CreateSnapshot parameter is the parameter that will actually create the snapshot. If there is just CopySnapshot without the CreateSnapshot, it will have no created snapshots to copy over. Eidetic Express will retain as many snapshots as specified in the source region.
  
  
Usage:
`Eidetic : { “CopySnapshot” : “r” }`

where:

- r: the destination region for the snapshot to be copied to.

Example Tags:

```
Eidetic : { “CopySnapshot” : “us-east-1”, “CreateSnapshot” : { “Interval” : “hour”, “Retain” : “4” } }

Eidetic : { “CreateSnapshot” : { “Interval” : “day”, “Retain” : “10” }, “CopySnapshot” : “us-west-2”  }

Eidetic : { “CopySnapshot” : “ap-northeast-1”, “CreateSnapshot” : { “Interval” : “day”, “Retain” : “5”,  “RunAt” : “12:30:00”} }

```
  
### Amnesia 

Amnesia is a snapshot cleanup utility. When a tag’s interval value is changed on a volume, the old snapshots pertaining to that interval will remain as they are outside the normal eidetic clean up process. Volumes, which are deleted and also tagged, will fall into the same category. Thus these are stranded snapshots. Amnesia will inspect these Eidetic snapshots, and if they are older than x days, where x is configured in the application.properties file, it will delete them. You may also add in an overall snapshot retention time, where if any snapshot is older than the set time, it will delete them. 
 
### Eidetic Checker

Eidetic Checker is one of the validation extensions for Eidetic. Just as Eidetic iterates through regions for tagged EBS volumes, Eidetic Checker will do the same but instead of acting on the tags, it will validate the execution of said tags. 

For example, take this tag value:

`{ “CreateSnapshot” : { “Interval” : “day”, “Retain” : “7” } }`

This tag value has been applied to an EBS volume. It will see if there are in fact snapshots executing in a daily time frame. If not, it will take a snapshot immediately and write out an error log for human inspection. 
  
  
### Snapshot Time Poller

As of the writing of this Readme, Amazon does not provide timing information for how long a snapshot processing up to S3 takes. Snapshot Time Poller will query all snapshots that are in a pending state and add a tag saying:

`CreationPending : n`

where:

- n: the number of minutes this snapshot has been in a pending state.
  
  
Once the snapshot is in a complete state, Snapshot Time Poller will replace the CreationPending tag with:

`CreationComplete : t`

where:

- t: the total number of minutes for this snapshot to complete.

This can provide useful data for a number of purposes. For example, how frequently to take snapshots of a highly active volume, deciding whether to use Amazon’s snapshot utility for data migration in a finite deployment window, etc. As a note, the minutes reported may have a margin of error due to API saturation, the server hosting the Eidetic JVM CPU bottlenecking, Eidetic not running, etc.  Snapshot Time Poller should be used as an estimate.
  
  
### Tag Checker

Since Eidetic and extensions work off on tags, there needs to be validation that tags exist for volumes of importance. We can add a tag to our instance to declare which mount point is the location of our important data. 

For example, lets say on our database instance, the mount point of /dev/xvdk is where our important business related data is stored. The tag on the instance would appear like so:

`Data : /dev/xvdk`

Tag Checker will find tagged instances and ensure an Eidetic tag is on the volume at that specific mount point. If not, it will create an Eidetic tag on aforementioned volume. The tag value is specified in the application.properties configuration file.
  
  
### Error Checker

There is the possibility of snapshot resulting in an error state at some point after creation. If we are snapshotting a volume of importance, we need to know immediately that an error has occurred. Error Checker will scan for all snapshots in an error state and write a log detailing the snapshot. Error Checker will clean the snapshot afterwards.


