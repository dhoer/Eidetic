/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pearson.eidetic.driver.threads.subthreads;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.Snapshot;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.Volume;
import com.pearson.eidetic.driver.threads.EideticSubThread;
import com.pearson.eidetic.driver.threads.EideticSubThreadMethods;
import static com.pearson.eidetic.driver.threads.EideticSubThreadMethods.range;
import com.pearson.eidetic.globals.ApplicationConfiguration;
import com.pearson.eidetic.utilities.StackTrace;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author uwalkj6
 */
public class SnapshotChecker extends EideticSubThreadMethods implements Runnable, EideticSubThread {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class.getName());
    
    private Boolean isFinished_ = null;
    private final String awsAccessKeyId_;
    private final String awsSecretKey_;
    private final String uniqueAwsAccountIdentifier_;
    private final Integer maxApiRequestsPerSecond_;
    private final Integer numRetries_;
    private final com.amazonaws.regions.Region region_;
    private final ArrayList<Volume> VolumeNoTime_;
    private final ArrayList<Volume> VolumeTime_;
    
    public SnapshotChecker(String awsAccessKeyId, String awsSecretKey, String uniqueAwsAccountIdentifier, Integer maxApiRequestsPerSecond,  
            Integer numRetries, com.amazonaws.regions.Region region, ArrayList<Volume> VolumeNoTime, ArrayList<Volume> VolumeTime) {
        this.awsAccessKeyId_ = awsAccessKeyId;
        this.awsSecretKey_ = awsSecretKey;
        this.uniqueAwsAccountIdentifier_ = uniqueAwsAccountIdentifier;
        this.maxApiRequestsPerSecond_ = maxApiRequestsPerSecond;
        this.numRetries_ = numRetries;
        this.region_ = region;
        this.VolumeNoTime_ = VolumeNoTime;
        this.VolumeTime_ = VolumeTime;
    }
    @Override
    public void run() {
        isFinished_ = false;
        //kill thread if wrong creds \/ \/ \/ \/
        AmazonEC2Client ec2Client = connect(region_, awsAccessKeyId_, awsSecretKey_);

        for (Volume vol : VolumeNoTime_) {
            try {
                Date date = new java.util.Date();
                JSONParser parser = new JSONParser();

                String inttagvalue = getIntTagValue(vol);
                if (inttagvalue == null) {
                    continue;
                }

                JSONObject eideticParameters;
                try {
                    Object obj = parser.parse(inttagvalue);
                    eideticParameters = (JSONObject) obj;
                } catch (Exception e) {
                    logger.error("Event=Error, Error=\"Malformed Eidetic Tag\", Volume_id=\"" + vol.getVolumeId() + "\", stacktrace=\""
                                + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e) + "\"");
                    continue;
                }

                String period = getPeriod(eideticParameters, vol);
                if (period == null) {
                    continue;
                }

                Integer keep = getKeep(eideticParameters, vol);
                if (keep == null) {
                    continue;
                }

                Boolean success;
                success = snapshotDecision(ec2Client, vol, period);
                if (!success) {
                    continue;
                }

                success = snapshotCreation(ec2Client, vol, period, date);
                if (!success) {
                    continue;
                }

                snapshotDeletion(ec2Client, vol, period, keep);

                logger.info("Event=\"Error\", Error=\"normal eidetic process did not create a snapshot for volume in alloted time.\", Volume_id=\"" + vol.getVolumeId() + "\"");

            } catch (Exception e) {
                logger.error("Event=\"Error\", Error=\"error in SnapshotChecker workflow\", stacktrace=\""
                        + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e) + "\"");
            }

        }

        for (Volume vol : VolumeTime_) {
            try {
                Date date = new java.util.Date();
                JSONParser parser = new JSONParser();

                String inttagvalue = getIntTagValue(vol);
                if (inttagvalue == null) {
                    continue;
                }

                JSONObject eideticParameters;
                try {
                    Object obj = parser.parse(inttagvalue);
                    eideticParameters = (JSONObject) obj;
                } catch (Exception e) {
                    logger.error("Event=Error, Error=\"Malformed Eidetic Tag\", Volume_id=\"" + vol.getVolumeId() + "\", stacktrace=\""
                                + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e) + "\"");
                    continue;
                }

                String period = getPeriod(eideticParameters, vol);
                if (period == null) {
                    continue;
                }

                Integer keep = getKeep(eideticParameters, vol);
                if (keep == null) {
                    continue;
                }

                Boolean success;
                success = snapshotDecision(ec2Client, vol, period);
                if (!success) {
                    continue;
                }

                success = snapshotCreation(ec2Client, vol, period, date);
                if (!success) {
                    continue;
                }

                snapshotDeletion(ec2Client, vol, period, keep);

                logger.info("Event=\"Error\", Error=\"normal eidetic process did not create a snapshot for volume in alloted time.\", Volume_id=\"" + vol.getVolumeId() + "\"");

            } catch (Exception e) {
                logger.info("Event=\"Error\", Error=\"error in EideticChcker workflow', Volume_id=\"" + vol.getVolumeId() + "\", stacktrace=\""
                        + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e) + "\"");
            }
        }

        ec2Client.shutdown();
        isFinished_ = true;

    }

    @Override
    public boolean isFinished() {
        return isFinished_; //To change body of generated methods, choose Tools | Templates.
    }

    public AmazonEC2Client connect(Region region, String awsAccessKey, String awsSecretKey) {
        AmazonEC2Client ec2Client;
        String endpoint = "ec2." + region.getName() + ".amazonaws.com";

        AWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTPS);

        ec2Client = new AmazonEC2Client(credentials, clientConfig);
        ec2Client.setRegion(region);
        ec2Client.setEndpoint(endpoint);
        return ec2Client;
    }

    public String getIntTagValue(Volume vol) {
        if (vol == null) {
            return null;
        }

        String inttagvalue = null;
        for (Tag tag : vol.getTags()) {
            if ("Eidetic".equalsIgnoreCase(tag.getKey())) {
                inttagvalue = tag.getValue();
                break;
            }
        }

        return inttagvalue;

    }

    public String getPeriod(JSONObject eideticParameters, Volume vol) {
        if ((eideticParameters == null)) {
            return null;
        }
        JSONObject createSnapshot = null;
        if (eideticParameters.containsKey("CreateSnapshot")) {
            createSnapshot = (JSONObject) eideticParameters.get("CreateSnapshot");
        }
        if (createSnapshot == null) {
            logger.error("Event=Error, Error=\"Malformed Eidetic Tag\", Volume_id=\"" + vol.getVolumeId() + "\"");
            return null;
        }

        String period = null;
        if (createSnapshot.containsKey("Interval")) {
            try {
                period = createSnapshot.get("Interval").toString();
            } catch (Exception e) {
                logger.error("Event=Error, Error=\"Malformed Eidetic Tag\", Volume_id=\"" + vol.getVolumeId() + "\", stacktrace=\""
                                + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e) + "\"");
            }
        }
        
        return period;
    }

    public Integer getKeep(JSONObject eideticParameters, Volume vol) {
        if ((eideticParameters == null) || (vol == null)) {
            return null;
        }

        JSONObject createSnapshot = null;
        if (eideticParameters.containsKey("CreateSnapshot")) {
            createSnapshot = (JSONObject) eideticParameters.get("CreateSnapshot");
        }
        if (createSnapshot == null) {
            logger.error("Event=Error, Error=\"Malformed Eidetic Tag\", Volume_id=\"" + vol.getVolumeId() + "\"");
            return null;
        }

        Integer keep = null;
        if (createSnapshot.containsKey("Retain")) {
            try {
                keep = Integer.parseInt(createSnapshot.get("Retain").toString());
            } catch (Exception e) {
                logger.error("Event=Error, Error=\"Malformed Eidetic Tag\", Volume_id=\"" + vol.getVolumeId() + "\", stacktrace=\""
                                + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e) + "\"");
            }
        }

        return keep;
    }

    public boolean snapshotDecision(AmazonEC2Client ec2Client, Volume vol, String period) {
        if ((ec2Client == null) || (vol == null) || (period == null)) {
            return false;
        }
        try {

            List<Snapshot> int_snapshots = getAllSnapshotsOfVolume(ec2Client, vol, numRetries_, maxApiRequestsPerSecond_, uniqueAwsAccountIdentifier_);

            List<Snapshot> comparelist = new ArrayList();

            for (Snapshot snapshot : int_snapshots) {
                String sndesc = snapshot.getDescription();
                if ("week".equalsIgnoreCase(period) && sndesc.startsWith("week_snapshot")) {
                    comparelist.add(snapshot);
                } else if ("day".equalsIgnoreCase(period) && sndesc.startsWith("day_snapshot")) {
                    comparelist.add(snapshot);
                } else if ("hour".equalsIgnoreCase(period) && sndesc.startsWith("hour_snapshot")) {
                    comparelist.add(snapshot);
                } else if ("month".equalsIgnoreCase(period) && sndesc.startsWith("month_snapshot")) {
                    comparelist.add(snapshot);
                }
            }

            List<Snapshot> sortedCompareList = new ArrayList<>(comparelist);
            sortSnapshotsByDate(sortedCompareList);

            int hours = getHoursBetweenNowAndNewestSnapshot(sortedCompareList);
            int days = getDaysBetweenNowAndNewestSnapshot(sortedCompareList);

            if (("week".equalsIgnoreCase(period) && days < 0) || ("week".equalsIgnoreCase(period) && days >= 14)) {
            } else if (("hour".equalsIgnoreCase(period) && hours < 0) || ("hour".equalsIgnoreCase(period) && hours >= 2)) {
            } else if (("day".equalsIgnoreCase(period) && days < 0) || ("day".equalsIgnoreCase(period) && hours >= 25)) {
            } else if (("month".equalsIgnoreCase(period) && days < 0) || ("month".equalsIgnoreCase(period) && days >= 60)) {
            } else {
                return false;
            }

        } catch (Exception e) {
            logger.info("Event=\"Error\", Error=\"error in snapshotDecision\", stacktrace=\""
                    + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e) + "\"");
            return false;
        }

        return true;
    }

    public boolean snapshotCreation(AmazonEC2Client ec2Client, Volume vol, String period, Date date) {
        if ((date == null) || (ec2Client == null) || (vol == null) || (period == null)) {
            return false;
        }

        try {

            if ("day".equalsIgnoreCase(period)) {
            } else if ("hour".equalsIgnoreCase(period)) {
            } else if ("week".equalsIgnoreCase(period)) {
            } else if ("month".equalsIgnoreCase(period)) {
            } else {
                logger.error("Event=Error, Error=\"Malformed Eidetic Tag\", Volume_id=\"" + vol.getVolumeId() + "\"");
                return false;
            }

            Collection<Tag> tags_volume = getResourceTags(vol);

            String volumeAttachmentInstance = "none";
            try {
                volumeAttachmentInstance = vol.getAttachments().get(0).getInstanceId();
            } catch (Exception e) {
                logger.debug("Volume not attached to instance: " + vol.getVolumeId());
            }

            String description = period + "_snapshot " + vol.getVolumeId() + " by snapshot checker at " + date.toString()
                    + ". Volume attached to " + volumeAttachmentInstance;

            Snapshot current_snap;
            try {
                current_snap = createSnapshotOfVolume(ec2Client, vol, description, numRetries_, maxApiRequestsPerSecond_, uniqueAwsAccountIdentifier_);
            } catch (Exception e) {
                logger.info("Event=\"Error\", Error=\"error creating snapshot from volume\", Volume_id=\"" + vol.getVolumeId() + "\", stacktrace=\""
                        + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e) + "\"");
                return false;
            }

            try {
                setResourceTags(ec2Client, current_snap, tags_volume, numRetries_, maxApiRequestsPerSecond_, uniqueAwsAccountIdentifier_);
            } catch (Exception e) {
                logger.info("Event=\"Error\", Error=\"error adding tags to snapshot\", Snapshot_id=\"" + current_snap.getSnapshotId() + "\", stacktrace=\""
                        + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e) + "\"");
            }

        } catch (Exception e) {
            logger.info("Event=\"Error\", Error=\"error in snapshotCreation\", stacktrace=\""
                    + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e) + "\"");
        }

        return true;
    }

    public boolean snapshotDeletion(AmazonEC2Client ec2Client, Volume vol, String period, Integer keep) {
        if ((keep == null) || (ec2Client == null) || (vol == null) || (period == null)) {
            return false;
        }

        try {
            List<Snapshot> del_snapshots = getAllSnapshotsOfVolume(ec2Client, vol, numRetries_, maxApiRequestsPerSecond_, uniqueAwsAccountIdentifier_);

            List<Snapshot> deletelist = new ArrayList();

            for (Snapshot snapshot : del_snapshots) {
                String desc = snapshot.getDescription();
                if ("week".equals(period) && desc.startsWith("week_snapshot")) {
                    deletelist.add(snapshot);
                } else if ("day".equals(period) && desc.startsWith("day_snapshot")) {
                    deletelist.add(snapshot);
                } else if ("hour".equals(period) && desc.startsWith("hour_snapshot")) {
                    deletelist.add(snapshot);
                } else if ("month".equals(period) && desc.startsWith("month_snapshot")) {
                    deletelist.add(snapshot);
                }
            }

            List<Snapshot> sortedDeleteList = new ArrayList<>(deletelist);
            sortSnapshotsByDate(sortedDeleteList);

            int delta = sortedDeleteList.size() - keep;

            for (int i : range(0, delta - 1)) {
                try {
                    deleteSnapshot(ec2Client, sortedDeleteList.get(i), numRetries_, maxApiRequestsPerSecond_, uniqueAwsAccountIdentifier_);
                } catch (Exception e) {
                    logger.info("Event=\"Error\", Error=\"error deleting snapshot\", Snapshot_id=\"" + sortedDeleteList.get(i).getSnapshotId() + "\", stacktrace=\""
                            + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e) + "\"");
                }
            }
        } catch (Exception e) {
            logger.info("Event=\"Error\", Error=\"error in snapshotDeletion\", stacktrace=\""
                    + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e) + "\"");
        }

        return true;
    }

}
