/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pearson.eidetic.aws;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.CopySnapshotRequest;
import com.amazonaws.services.ec2.model.CopySnapshotResult;
import com.amazonaws.services.ec2.model.CreateSnapshotRequest;
import com.amazonaws.services.ec2.model.CreateSnapshotResult;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.DeleteSnapshotRequest;
import com.amazonaws.services.ec2.model.DeleteTagsRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeSnapshotsRequest;
import com.amazonaws.services.ec2.model.DescribeSnapshotsResult;
import com.amazonaws.services.ec2.model.DescribeVolumesRequest;
import com.amazonaws.services.ec2.model.DescribeVolumesResult;
import com.pearson.eidetic.globals.GlobalVariables;
import com.pearson.eidetic.utilities.StackTrace;
import com.pearson.eidetic.utilities.Threads;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author uwalkj6
 */
public class EC2ClientMethods {
    
    private static final Logger logger = LoggerFactory.getLogger(AwsAccount.class.getName());
    
    public static CreateSnapshotResult createSnapshot(AmazonEC2Client ec2Client, CreateSnapshotRequest snapshotRequest,
            Integer numRetries, Integer maxApiRequestsPerSecond, String uniqueAwsAccountIdentifier) {
        CreateSnapshotResult createSnapshotResult = null;
        for (int i = 0; i <= numRetries; i++) {
            try {
                // if the initial download attempt failed, wait for i * 500ms 
                if (i > 0) {
                    long sleepTimeInMilliseconds = 500 * i;
                    Threads.sleepMilliseconds(sleepTimeInMilliseconds);
                    logger.debug(snapshotRequest.toString() + System.lineSeparator() + "**********I am sleeping because something is blocking on AWS****************");

                }

                AtomicLong requestAttemptCounter = GlobalVariables.apiRequestAttemptCountersByAwsAccount.get(uniqueAwsAccountIdentifier);
                long currentRequestCount = requestAttemptCounter.incrementAndGet();

                while (currentRequestCount > maxApiRequestsPerSecond) {
                    Threads.sleepMilliseconds(50);
                    currentRequestCount = requestAttemptCounter.incrementAndGet();
                    logger.debug(snapshotRequest.toString() + System.lineSeparator() + "****** I am waiting because the request rate is being throttled by Eidetic");
                }
                Long elapsedTime;
                Long startTime = System.currentTimeMillis();
                
                createSnapshotResult = ec2Client.createSnapshot(snapshotRequest);

                elapsedTime = (new Date()).getTime() - startTime;
                
                if (createSnapshotResult != null) {                    
                    GlobalVariables.apiRequestCountersByAwsAccount.get(uniqueAwsAccountIdentifier).incrementAndGet();
                    logger.debug("************** I finished this snapshot and it took " + elapsedTime.toString() + " ms *******************");
                    break;
                }
            } catch (Exception e) {
                logger.error(snapshotRequest.toString() + System.lineSeparator() + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e));
            }
        }
        logger.info("Event=\"Snapshot Created\", Volume_id=\"" + snapshotRequest.getVolumeId() + "\"");
        return createSnapshotResult;

    }
    
    public static CopySnapshotResult copySnapshot(AmazonEC2Client ec2Client, CopySnapshotRequest snapshotRequest,
            Integer numRetries, Integer maxApiRequestsPerSecond, String uniqueAwsAccountIdentifier) {
        CopySnapshotResult copySnapshotResult = null;
        for (int i = 0; i <= numRetries; i++) {
            try {
                // if the initial download attempt failed, wait for i * 500ms 
                if (i > 0) {
                    long sleepTimeInMilliseconds = 500 * i;
                    Threads.sleepMilliseconds(sleepTimeInMilliseconds);
                }

                AtomicLong requestAttemptCounter = GlobalVariables.apiRequestAttemptCountersByAwsAccount.get(uniqueAwsAccountIdentifier);
                long currentRequestCount = requestAttemptCounter.incrementAndGet();

                while (currentRequestCount > maxApiRequestsPerSecond) {
                    Threads.sleepMilliseconds(50);
                    currentRequestCount = requestAttemptCounter.incrementAndGet();
                }
                
                copySnapshotResult = ec2Client.copySnapshot(snapshotRequest);

                if (copySnapshotResult != null) {
                    GlobalVariables.apiRequestCountersByAwsAccount.get(uniqueAwsAccountIdentifier).incrementAndGet();
                    break;
                }
            } catch (Exception e) {
                if (e.toString().contains("20109")) {
                    return copySnapshotResult;
                }
                
                logger.error(snapshotRequest.toString() + System.lineSeparator() + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e));
            }
        }
        logger.info("Event=\"Snapshot Copied\", source_region=\"" + snapshotRequest.getSourceRegion() + 
                "\", destination_region=\"" + snapshotRequest.getDestinationRegion() + "\", source_snapshot_id=\"" + snapshotRequest.getSourceSnapshotId() + "\"");
        return copySnapshotResult;

    }
    
    public static void createTags(AmazonEC2Client ec2Client, CreateTagsRequest createTagsRequest,
            Integer numRetries, Integer maxApiRequestsPerSecond, String uniqueAwsAccountIdentifier) {
        
        for (int i = 0; i <= numRetries; i++) {
            try {
                // if the initial download attempt failed, wait for i * 500ms 
                if (i > 0) {
                    long sleepTimeInMilliseconds = 500 * i;
                    Threads.sleepMilliseconds(sleepTimeInMilliseconds);
                }

                AtomicLong requestAttemptCounter = GlobalVariables.apiRequestAttemptCountersByAwsAccount.get(uniqueAwsAccountIdentifier);
                long currentRequestCount = requestAttemptCounter.incrementAndGet();

                while (currentRequestCount > maxApiRequestsPerSecond) {
                    Threads.sleepMilliseconds(50);
                    currentRequestCount = requestAttemptCounter.incrementAndGet();
                }

                try {
                    ec2Client.createTags(createTagsRequest);
                    try {
                        GlobalVariables.apiRequestCountersByAwsAccount.get(uniqueAwsAccountIdentifier).incrementAndGet();
                    } catch (Exception e) {
                        logger.error(createTagsRequest.toString() + System.lineSeparator() + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e));
                    }
                    break;
                } catch (Exception e) {
                    if (e.toString().contains("TagLimitExceeded")) {
                        break;
                    }
                    logger.error(createTagsRequest.toString() + System.lineSeparator() + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e));
                }
                
            } catch (Exception e) {
                logger.error(createTagsRequest.toString() + System.lineSeparator() + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e));
            }
        }

    }
    
    public static void deleteTags(AmazonEC2Client ec2Client, DeleteTagsRequest deleteTagsRequest,
            Integer numRetries, Integer maxApiRequestsPerSecond, String uniqueAwsAccountIdentifier) {
        
        for (int i = 0; i <= numRetries; i++) {
            try {
                // if the initial download attempt failed, wait for i * 500ms 
                if (i > 0) {
                    long sleepTimeInMilliseconds = 500 * i;
                    Threads.sleepMilliseconds(sleepTimeInMilliseconds);
                }

                AtomicLong requestAttemptCounter = GlobalVariables.apiRequestAttemptCountersByAwsAccount.get(uniqueAwsAccountIdentifier);
                long currentRequestCount = requestAttemptCounter.incrementAndGet();

                while (currentRequestCount > maxApiRequestsPerSecond) {
                    Threads.sleepMilliseconds(50);
                    currentRequestCount = requestAttemptCounter.incrementAndGet();
                }

                try {
                    ec2Client.deleteTags(deleteTagsRequest);
                    try {
                        GlobalVariables.apiRequestCountersByAwsAccount.get(uniqueAwsAccountIdentifier).incrementAndGet();
                    } catch (Exception e) {
                        logger.error(deleteTagsRequest.toString() + System.lineSeparator() + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e));
                    }
                    break;
                } catch (Exception e) {
                    logger.error(deleteTagsRequest.toString() + System.lineSeparator() + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e));
                }
                
            } catch (Exception e) {
                logger.error(deleteTagsRequest.toString() + System.lineSeparator() + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e));
            }
        }

    }
    
    
    public static void deleteSnapshot(AmazonEC2Client ec2Client, DeleteSnapshotRequest deletesnapshotRequest,
            Integer numRetries, Integer maxApiRequestsPerSecond, String uniqueAwsAccountIdentifier) {
        for (int i = 0; i <= numRetries; i++) {
            try {
                // if the initial download attempt failed, wait for i * 500ms 
                if (i > 0) {
                    long sleepTimeInMilliseconds = 500 * i;
                    Threads.sleepMilliseconds(sleepTimeInMilliseconds);
                }

                AtomicLong requestAttemptCounter = GlobalVariables.apiRequestAttemptCountersByAwsAccount.get(uniqueAwsAccountIdentifier);
                long currentRequestCount = requestAttemptCounter.incrementAndGet();

                while (currentRequestCount > maxApiRequestsPerSecond) {
                    Threads.sleepMilliseconds(50);
                    currentRequestCount = requestAttemptCounter.incrementAndGet();
                }

                try {
                    ec2Client.deleteSnapshot(deletesnapshotRequest);
                    try {
                        GlobalVariables.apiRequestCountersByAwsAccount.get(uniqueAwsAccountIdentifier).incrementAndGet();
                    } catch (Exception e) {
                        logger.error(deletesnapshotRequest.toString() + System.lineSeparator() + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e));
                    }
                    break;
                } catch (Exception e) {
                    logger.error(deletesnapshotRequest.toString() + System.lineSeparator() + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e));
                }

            } catch (Exception e) {
                logger.error(deletesnapshotRequest.toString() + System.lineSeparator() + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e));
            }
        }
        logger.info("Event=\"Snapshot Deleted\", Snapshot_id=\"" + deletesnapshotRequest.getSnapshotId() + "\"");
    }
    
    public static DescribeVolumesResult describeVolumes(AmazonEC2Client ec2Client, DescribeVolumesRequest describeVolumesRequest,
            Integer numRetries, Integer maxApiRequestsPerSecond, String uniqueAwsAccountIdentifier) {
        DescribeVolumesResult describeVolumesResult = null;
        for (int i = 0; i <= numRetries; i++) {
            try {
                // if the initial download attempt failed, wait for i * 500ms 
                if (i > 0) {
                    long sleepTimeInMilliseconds = 500 * i;
                    Threads.sleepMilliseconds(sleepTimeInMilliseconds);
                }

                AtomicLong requestAttemptCounter = GlobalVariables.apiRequestAttemptCountersByAwsAccount.get(uniqueAwsAccountIdentifier);
                long currentRequestCount = requestAttemptCounter.incrementAndGet();

                while (currentRequestCount > maxApiRequestsPerSecond) {
                    Threads.sleepMilliseconds(50);
                    currentRequestCount = requestAttemptCounter.incrementAndGet();
                }

                describeVolumesResult = ec2Client.describeVolumes(describeVolumesRequest);

                if (describeVolumesResult != null) {
                    GlobalVariables.apiRequestCountersByAwsAccount.get(uniqueAwsAccountIdentifier).incrementAndGet();
                    break;
                }
            } catch (Exception e) {
                logger.error(describeVolumesRequest.toString() + System.lineSeparator() + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e));
            }
        }

        return describeVolumesResult;

    }
    
    public static DescribeSnapshotsResult describeSnapshots(AmazonEC2Client ec2Client, DescribeSnapshotsRequest describeSnapshotsRequest,
            Integer numRetries, Integer maxApiRequestsPerSecond, String uniqueAwsAccountIdentifier) {
        DescribeSnapshotsResult describeSnapshotsResult = null;
        for (int i = 0; i <= numRetries; i++) {
            try {
                // if the initial download attempt failed, wait for i * 500ms 
                if (i > 0) {
                    long sleepTimeInMilliseconds = 500 * i;
                    Threads.sleepMilliseconds(sleepTimeInMilliseconds);
                }

                AtomicLong requestAttemptCounter = GlobalVariables.apiRequestAttemptCountersByAwsAccount.get(uniqueAwsAccountIdentifier);
                long currentRequestCount = requestAttemptCounter.incrementAndGet();

                while (currentRequestCount > maxApiRequestsPerSecond) {
                    Threads.sleepMilliseconds(50);
                    currentRequestCount = requestAttemptCounter.incrementAndGet();
                }

                describeSnapshotsResult = ec2Client.describeSnapshots(describeSnapshotsRequest);

                if (describeSnapshotsResult != null) {
                    GlobalVariables.apiRequestCountersByAwsAccount.get(uniqueAwsAccountIdentifier).incrementAndGet();
                    break;
                }
            } catch (Exception e) {
                logger.error(describeSnapshotsRequest.toString() + System.lineSeparator() + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e));
            }
        }

        return describeSnapshotsResult;

    }
    
    public static DescribeInstancesResult describeInstances(AmazonEC2Client ec2Client, DescribeInstancesRequest describeInstancesRequest,
            Integer numRetries, Integer maxApiRequestsPerSecond, String uniqueAwsAccountIdentifier) {
        DescribeInstancesResult describeInstancesResult = null;
        for (int i = 0; i <= numRetries; i++) {
            try {
                // if the initial download attempt failed, wait for i * 500ms 
                if (i > 0) {
                    long sleepTimeInMilliseconds = 500 * i;
                    Threads.sleepMilliseconds(sleepTimeInMilliseconds);
                }

                AtomicLong requestAttemptCounter = GlobalVariables.apiRequestAttemptCountersByAwsAccount.get(uniqueAwsAccountIdentifier);
                
                long currentRequestCount = requestAttemptCounter.incrementAndGet();

                while (currentRequestCount > maxApiRequestsPerSecond) {
                    Threads.sleepMilliseconds(50);
                    currentRequestCount = requestAttemptCounter.incrementAndGet();
                }

                describeInstancesResult = ec2Client.describeInstances(describeInstancesRequest);

                if (describeInstancesResult != null) {
                    GlobalVariables.apiRequestCountersByAwsAccount.get(uniqueAwsAccountIdentifier).incrementAndGet();
                    break;
                }
            } catch (Exception e) {
                logger.error(describeInstancesRequest.toString() + System.lineSeparator() + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e));
            }
        }

        return describeInstancesResult;

    }
    
    
    
}
