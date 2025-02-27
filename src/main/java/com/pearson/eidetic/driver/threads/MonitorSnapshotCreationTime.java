/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pearson.eidetic.driver.threads;

import com.pearson.eidetic.aws.AwsAccount;
import com.pearson.eidetic.driver.Monitor;
import com.pearson.eidetic.driver.MonitorMethods;
import com.pearson.eidetic.driver.threads.subthreads.SnapshotCreationTime;
import com.pearson.eidetic.globals.ApplicationConfiguration;
import com.pearson.eidetic.utilities.StackTrace;
import com.pearson.eidetic.utilities.Threads;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author uwalkj6
 */
public class MonitorSnapshotCreationTime extends MonitorMethods implements Runnable, Monitor {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class.getName());

    private final AwsAccount awsAccount_;

    public MonitorSnapshotCreationTime(AwsAccount awsAccount) {
        this.awsAccount_ = awsAccount;
    }

    @Override
    public void run() {
        while (true) {
            try {

                ArrayList<SnapshotCreationTime> threads = new ArrayList<>();

                threads.add(new SnapshotCreationTime(awsAccount_));

                Threads.threadExecutorFixedPool(threads, 1, 60, TimeUnit.SECONDS);

                Threads.sleepSeconds(30);

            } catch (Exception e) {
                logger.error("Error=\"MonitorSnapshotCreationTimeFailure\", stacktrace=\"" + e.toString() + System.lineSeparator() + StackTrace.getStringFromStackTrace(e) + "\"");
                Threads.sleepSeconds(10);
            }
        }

    }
}


