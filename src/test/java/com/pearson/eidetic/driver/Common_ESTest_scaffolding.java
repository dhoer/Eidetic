/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Fri Nov 13 18:03:18 GMT 2015
 */

package com.pearson.eidetic.driver;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class Common_ESTest_scaffolding {

  @org.junit.Rule 
  public org.junit.rules.Timeout globalTimeout = new org.junit.rules.Timeout(4000); 

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);

  @BeforeClass 
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "com.pearson.eidetic.driver.Common"; 
    org.evosuite.runtime.GuiSupport.initialize(); 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfThreads = 100; 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfIterationsPerLoop = 10000; 
    org.evosuite.runtime.RuntimeSettings.mockSystemIn = true; 
    org.evosuite.runtime.RuntimeSettings.sandboxMode = org.evosuite.runtime.sandbox.Sandbox.SandboxMode.RECOMMENDED; 
    org.evosuite.runtime.sandbox.Sandbox.initializeSecurityManagerForSUT(); 
    initializeClasses();
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
  } 

  @AfterClass 
  public static void clearEvoSuiteFramework(){ 
    Sandbox.resetDefaultSecurityManager(); 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
  } 

  @Before 
  public void initTestCase(){ 
    threadStopper.storeCurrentThreads();
    threadStopper.startRecordingTime();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().initHandler(); 
    org.evosuite.runtime.sandbox.Sandbox.goingToExecuteSUTCode(); 
    setSystemProperties(); 
    org.evosuite.runtime.GuiSupport.setHeadless(); 
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
    org.evosuite.runtime.agent.InstrumentingAgent.activate(); 
  } 

  @After 
  public void doneWithTestCase(){ 
    threadStopper.killAndJoinClientThreads();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().safeExecuteAddedHooks(); 
    resetClasses(); 
    org.evosuite.runtime.sandbox.Sandbox.doneWithExecutingSUTCode(); 
    org.evosuite.runtime.agent.InstrumentingAgent.deactivate(); 
    org.evosuite.runtime.GuiSupport.restoreHeadlessMode(); 
  } 

  public void setSystemProperties() {
 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
    java.lang.System.setProperty("java.vm.vendor", "Oracle Corporation"); 
    java.lang.System.setProperty("java.specification.version", "1.8"); 
            java.lang.System.setProperty("java.home", "/Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home/jre"); 
    java.lang.System.setProperty("user.dir", "/Users/uwalkj6/Documents/NetBeansProjects/eidetic"); 
    java.lang.System.setProperty("java.io.tmpdir", "/var/folders/cs/d6h1sj7j2g9_zhmr2syzkrysl5mt07/T/"); 
    java.lang.System.setProperty("awt.toolkit", "sun.lwawt.macosx.LWCToolkit"); 
    java.lang.System.setProperty("file.encoding", "UTF-8"); 
    java.lang.System.setProperty("file.separator", "/"); 
        java.lang.System.setProperty("java.awt.graphicsenv", "sun.awt.CGraphicsEnvironment"); 
    java.lang.System.setProperty("java.awt.headless", "true"); 
    java.lang.System.setProperty("java.awt.printerjob", "sun.lwawt.macosx.CPrinterJob"); 
    java.lang.System.setProperty("java.class.path", "/Users/uwalkj6/.m2/repository/org/evosuite/evosuite-master/1.0.1/evosuite-master-1.0.1.jar:/Users/uwalkj6/.m2/repository/org/evosuite/evosuite-standalone-runtime/1.0.1/evosuite-standalone-runtime-1.0.1-tests.jar:/Users/uwalkj6/.m2/repository/org/evosuite/evosuite-client/1.0.1/evosuite-client-1.0.1-tests.jar:/Users/uwalkj6/.m2/repository/org/evosuite/evosuite-runtime/1.0.1/evosuite-runtime-1.0.1.jar:/Users/uwalkj6/.m2/repository/org/hsqldb/hsqldb/2.3.2/hsqldb-2.3.2.jar:/Users/uwalkj6/.m2/repository/org/springframework/spring-orm/4.1.6.RELEASE/spring-orm-4.1.6.RELEASE.jar:/Users/uwalkj6/.m2/repository/org/springframework/spring-beans/4.1.6.RELEASE/spring-beans-4.1.6.RELEASE.jar:/Users/uwalkj6/.m2/repository/org/springframework/spring-core/4.1.6.RELEASE/spring-core-4.1.6.RELEASE.jar:/Users/uwalkj6/.m2/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar:/Users/uwalkj6/.m2/repository/org/springframework/spring-jdbc/4.1.6.RELEASE/spring-jdbc-4.1.6.RELEASE.jar:/Users/uwalkj6/.m2/repository/org/springframework/spring-tx/4.1.6.RELEASE/spring-tx-4.1.6.RELEASE.jar:/Users/uwalkj6/.m2/repository/org/springframework/spring-context/4.1.6.RELEASE/spring-context-4.1.6.RELEASE.jar:/Users/uwalkj6/.m2/repository/org/springframework/spring-aop/4.1.6.RELEASE/spring-aop-4.1.6.RELEASE.jar:/Users/uwalkj6/.m2/repository/aopalliance/aopalliance/1.0/aopalliance-1.0.jar:/Users/uwalkj6/.m2/repository/org/springframework/spring-expression/4.1.6.RELEASE/spring-expression-4.1.6.RELEASE.jar:/Users/uwalkj6/.m2/repository/junit/junit/4.12/junit-4.12.jar:/Users/uwalkj6/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:/Users/uwalkj6/.m2/repository/org/mockito/mockito-all/2.0.2-beta/mockito-all-2.0.2-beta.jar:/Users/uwalkj6/.m2/repository/org/slf4j/slf4j-api/1.7.12/slf4j-api-1.7.12.jar:/Users/uwalkj6/.m2/repository/org/ow2/asm/asm-all/5.0.4/asm-all-5.0.4.jar:/Users/uwalkj6/.m2/repository/javax/xml/bind/jaxb-api/2.2.12/jaxb-api-2.2.12.jar:/Users/uwalkj6/.m2/repository/net/sf/opencsv/opencsv/2.3/opencsv-2.3.jar:/Users/uwalkj6/.m2/repository/dk/brics/automaton/automaton/1.11-8/automaton-1.11-8.jar:/Users/uwalkj6/.m2/repository/ch/qos/logback/logback-classic/1.1.3/logback-classic-1.1.3.jar:/Users/uwalkj6/.m2/repository/ch/qos/logback/logback-core/1.1.3/logback-core-1.1.3.jar:/Users/uwalkj6/.m2/repository/com/googlecode/gentyref/gentyref/1.2.0/gentyref-1.2.0.jar:/Users/uwalkj6/.m2/repository/net/sf/jgrapht/jgrapht/0.8.3/jgrapht-0.8.3.jar:/Users/uwalkj6/.m2/repository/com/panayotis/JavaPlot/0.4.0/JavaPlot-0.4.0.jar:/Users/uwalkj6/.m2/repository/com/thoughtworks/xstream/xstream/1.4.8/xstream-1.4.8.jar:/Users/uwalkj6/.m2/repository/xmlpull/xmlpull/1.1.3.1/xmlpull-1.1.3.1.jar:/Users/uwalkj6/.m2/repository/xpp3/xpp3_min/1.1.4c/xpp3_min-1.1.4c.jar:/Users/uwalkj6/.m2/repository/org/apache/commons/commons-lang3/3.3.2/commons-lang3-3.3.2.jar:/Users/uwalkj6/.m2/repository/commons-io/commons-io/2.4/commons-io-2.4.jar:/Users/uwalkj6/.m2/repository/commons-cli/commons-cli/1.3.1/commons-cli-1.3.1.jar:/Users/uwalkj6/.m2/repository/org/kohsuke/graphviz-api/1.1/graphviz-api-1.1.jar:/Users/uwalkj6/.m2/repository/oro/oro/2.0.8/oro-2.0.8.jar:/Users/uwalkj6/.m2/repository/org/jboss/spec/javax/servlet/jboss-servlet-api_3.1_spec/1.0.0.Final/jboss-servlet-api_3.1_spec-1.0.0.Final.jar:/Users/uwalkj6/.m2/repository/javax/inject/javax.inject/1/javax.inject-1.jar:/Users/uwalkj6/.m2/repository/javax/enterprise/cdi-api/1.2/cdi-api-1.2.jar:/Users/uwalkj6/.m2/repository/javax/el/javax.el-api/3.0.0/javax.el-api-3.0.0.jar:/Users/uwalkj6/.m2/repository/javax/interceptor/javax.interceptor-api/1.2/javax.interceptor-api-1.2.jar:/Users/uwalkj6/.m2/repository/org/hibernate/hibernate-entitymanager/4.3.10.Final/hibernate-entitymanager-4.3.10.Final.jar:/Users/uwalkj6/.m2/repository/org/jboss/logging/jboss-logging/3.1.3.GA/jboss-logging-3.1.3.GA.jar:/Users/uwalkj6/.m2/repository/org/jboss/logging/jboss-logging-annotations/1.2.0.Beta1/jboss-logging-annotations-1.2.0.Beta1.jar:/Users/uwalkj6/.m2/repository/org/hibernate/hibernate-core/4.3.10.Final/hibernate-core-4.3.10.Final.jar:/Users/uwalkj6/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:/Users/uwalkj6/.m2/repository/org/jboss/jandex/1.1.0.Final/jandex-1.1.0.Final.jar:/Users/uwalkj6/.m2/repository/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:/Users/uwalkj6/.m2/repository/xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2.jar:/Users/uwalkj6/.m2/repository/org/hibernate/common/hibernate-commons-annotations/4.0.5.Final/hibernate-commons-annotations-4.0.5.Final.jar:/Users/uwalkj6/.m2/repository/org/hibernate/javax/persistence/hibernate-jpa-2.1-api/1.0.0.Final/hibernate-jpa-2.1-api-1.0.0.Final.jar:/Users/uwalkj6/.m2/repository/org/jboss/spec/javax/transaction/jboss-transaction-api_1.2_spec/1.0.0.Final/jboss-transaction-api_1.2_spec-1.0.0.Final.jar:/Users/uwalkj6/.m2/repository/org/javassist/javassist/3.18.1-GA/javassist-3.18.1-GA.jar:/Users/uwalkj6/Documents/NetBeansProjects/eidetic/target/classes:/Users/uwalkj6/.m2/repository/ch/qos/logback/logback-classic/1.1.3/logback-classic-1.1.3.jar:/Users/uwalkj6/.m2/repository/ch/qos/logback/logback-core/1.1.3/logback-core-1.1.3.jar:/Users/uwalkj6/.m2/repository/org/slf4j/slf4j-api/1.7.12/slf4j-api-1.7.12.jar:/Users/uwalkj6/.m2/repository/commons-configuration/commons-configuration/1.10/commons-configuration-1.10.jar:/Users/uwalkj6/.m2/repository/commons-lang/commons-lang/2.6/commons-lang-2.6.jar:/Users/uwalkj6/.m2/repository/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk/1.10.8/aws-java-sdk-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-support/1.10.8/aws-java-sdk-support-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-simpledb/1.10.8/aws-java-sdk-simpledb-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-simpleworkflow/1.10.8/aws-java-sdk-simpleworkflow-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-storagegateway/1.10.8/aws-java-sdk-storagegateway-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-route53/1.10.8/aws-java-sdk-route53-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-s3/1.10.8/aws-java-sdk-s3-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-importexport/1.10.8/aws-java-sdk-importexport-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-sts/1.10.8/aws-java-sdk-sts-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-sqs/1.10.8/aws-java-sdk-sqs-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-rds/1.10.8/aws-java-sdk-rds-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-redshift/1.10.8/aws-java-sdk-redshift-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-elasticbeanstalk/1.10.8/aws-java-sdk-elasticbeanstalk-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-glacier/1.10.8/aws-java-sdk-glacier-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-iam/1.10.8/aws-java-sdk-iam-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-datapipeline/1.10.8/aws-java-sdk-datapipeline-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-elasticloadbalancing/1.10.8/aws-java-sdk-elasticloadbalancing-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-emr/1.10.8/aws-java-sdk-emr-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-elasticache/1.10.8/aws-java-sdk-elasticache-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-elastictranscoder/1.10.8/aws-java-sdk-elastictranscoder-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-ec2/1.10.8/aws-java-sdk-ec2-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-dynamodb/1.10.8/aws-java-sdk-dynamodb-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-sns/1.10.8/aws-java-sdk-sns-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cloudtrail/1.10.8/aws-java-sdk-cloudtrail-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cloudwatch/1.10.8/aws-java-sdk-cloudwatch-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-logs/1.10.8/aws-java-sdk-logs-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cognitoidentity/1.10.8/aws-java-sdk-cognitoidentity-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cognitosync/1.10.8/aws-java-sdk-cognitosync-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-directconnect/1.10.8/aws-java-sdk-directconnect-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cloudformation/1.10.8/aws-java-sdk-cloudformation-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cloudfront/1.10.8/aws-java-sdk-cloudfront-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-kinesis/1.10.8/aws-java-sdk-kinesis-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-opsworks/1.10.8/aws-java-sdk-opsworks-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-ses/1.10.8/aws-java-sdk-ses-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-autoscaling/1.10.8/aws-java-sdk-autoscaling-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cloudsearch/1.10.8/aws-java-sdk-cloudsearch-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cloudwatchmetrics/1.10.8/aws-java-sdk-cloudwatchmetrics-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-swf-libraries/1.10.8/aws-java-sdk-swf-libraries-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-codedeploy/1.10.8/aws-java-sdk-codedeploy-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-codepipeline/1.10.8/aws-java-sdk-codepipeline-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-kms/1.10.8/aws-java-sdk-kms-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-config/1.10.8/aws-java-sdk-config-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-lambda/1.10.8/aws-java-sdk-lambda-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-ecs/1.10.8/aws-java-sdk-ecs-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cloudhsm/1.10.8/aws-java-sdk-cloudhsm-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-ssm/1.10.8/aws-java-sdk-ssm-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-workspaces/1.10.8/aws-java-sdk-workspaces-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-machinelearning/1.10.8/aws-java-sdk-machinelearning-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-directory/1.10.8/aws-java-sdk-directory-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-efs/1.10.8/aws-java-sdk-efs-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-codecommit/1.10.8/aws-java-sdk-codecommit-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-devicefarm/1.10.8/aws-java-sdk-devicefarm-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-core/1.10.8/aws-java-sdk-core-1.10.8.jar:/Users/uwalkj6/.m2/repository/org/apache/httpcomponents/httpclient/4.3.6/httpclient-4.3.6.jar:/Users/uwalkj6/.m2/repository/org/apache/httpcomponents/httpcore/4.3.3/httpcore-4.3.3.jar:/Users/uwalkj6/.m2/repository/commons-codec/commons-codec/1.6/commons-codec-1.6.jar:/Users/uwalkj6/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.5.3/jackson-databind-2.5.3.jar:/Users/uwalkj6/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.5.0/jackson-annotations-2.5.0.jar:/Users/uwalkj6/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.5.3/jackson-core-2.5.3.jar:/Users/uwalkj6/.m2/repository/joda-time/joda-time/2.8.1/joda-time-2.8.1.jar:/Users/uwalkj6/.m2/repository/com/googlecode/json-simple/json-simple/1.1.1/json-simple-1.1.1.jar:/Users/uwalkj6/.m2/repository/com/google/guava/guava/18.0/guava-18.0.jar:/Users/uwalkj6/Documents/NetBeansProjects/eidetic/target/classes:/Users/uwalkj6/.m2/repository/ch/qos/logback/logback-classic/1.1.3/logback-classic-1.1.3.jar:/Users/uwalkj6/.m2/repository/ch/qos/logback/logback-core/1.1.3/logback-core-1.1.3.jar:/Users/uwalkj6/.m2/repository/org/slf4j/slf4j-api/1.7.12/slf4j-api-1.7.12.jar:/Users/uwalkj6/.m2/repository/commons-configuration/commons-configuration/1.10/commons-configuration-1.10.jar:/Users/uwalkj6/.m2/repository/commons-lang/commons-lang/2.6/commons-lang-2.6.jar:/Users/uwalkj6/.m2/repository/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk/1.10.8/aws-java-sdk-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-support/1.10.8/aws-java-sdk-support-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-simpledb/1.10.8/aws-java-sdk-simpledb-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-simpleworkflow/1.10.8/aws-java-sdk-simpleworkflow-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-storagegateway/1.10.8/aws-java-sdk-storagegateway-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-route53/1.10.8/aws-java-sdk-route53-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-s3/1.10.8/aws-java-sdk-s3-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-importexport/1.10.8/aws-java-sdk-importexport-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-sts/1.10.8/aws-java-sdk-sts-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-sqs/1.10.8/aws-java-sdk-sqs-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-rds/1.10.8/aws-java-sdk-rds-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-redshift/1.10.8/aws-java-sdk-redshift-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-elasticbeanstalk/1.10.8/aws-java-sdk-elasticbeanstalk-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-glacier/1.10.8/aws-java-sdk-glacier-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-iam/1.10.8/aws-java-sdk-iam-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-datapipeline/1.10.8/aws-java-sdk-datapipeline-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-elasticloadbalancing/1.10.8/aws-java-sdk-elasticloadbalancing-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-emr/1.10.8/aws-java-sdk-emr-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-elasticache/1.10.8/aws-java-sdk-elasticache-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-elastictranscoder/1.10.8/aws-java-sdk-elastictranscoder-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-ec2/1.10.8/aws-java-sdk-ec2-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-dynamodb/1.10.8/aws-java-sdk-dynamodb-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-sns/1.10.8/aws-java-sdk-sns-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cloudtrail/1.10.8/aws-java-sdk-cloudtrail-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cloudwatch/1.10.8/aws-java-sdk-cloudwatch-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-logs/1.10.8/aws-java-sdk-logs-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cognitoidentity/1.10.8/aws-java-sdk-cognitoidentity-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cognitosync/1.10.8/aws-java-sdk-cognitosync-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-directconnect/1.10.8/aws-java-sdk-directconnect-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cloudformation/1.10.8/aws-java-sdk-cloudformation-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cloudfront/1.10.8/aws-java-sdk-cloudfront-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-kinesis/1.10.8/aws-java-sdk-kinesis-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-opsworks/1.10.8/aws-java-sdk-opsworks-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-ses/1.10.8/aws-java-sdk-ses-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-autoscaling/1.10.8/aws-java-sdk-autoscaling-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cloudsearch/1.10.8/aws-java-sdk-cloudsearch-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cloudwatchmetrics/1.10.8/aws-java-sdk-cloudwatchmetrics-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-swf-libraries/1.10.8/aws-java-sdk-swf-libraries-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-codedeploy/1.10.8/aws-java-sdk-codedeploy-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-codepipeline/1.10.8/aws-java-sdk-codepipeline-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-kms/1.10.8/aws-java-sdk-kms-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-config/1.10.8/aws-java-sdk-config-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-lambda/1.10.8/aws-java-sdk-lambda-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-ecs/1.10.8/aws-java-sdk-ecs-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-cloudhsm/1.10.8/aws-java-sdk-cloudhsm-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-ssm/1.10.8/aws-java-sdk-ssm-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-workspaces/1.10.8/aws-java-sdk-workspaces-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-machinelearning/1.10.8/aws-java-sdk-machinelearning-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-directory/1.10.8/aws-java-sdk-directory-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-efs/1.10.8/aws-java-sdk-efs-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-codecommit/1.10.8/aws-java-sdk-codecommit-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-devicefarm/1.10.8/aws-java-sdk-devicefarm-1.10.8.jar:/Users/uwalkj6/.m2/repository/com/amazonaws/aws-java-sdk-core/1.10.8/aws-java-sdk-core-1.10.8.jar:/Users/uwalkj6/.m2/repository/org/apache/httpcomponents/httpclient/4.3.6/httpclient-4.3.6.jar:/Users/uwalkj6/.m2/repository/org/apache/httpcomponents/httpcore/4.3.3/httpcore-4.3.3.jar:/Users/uwalkj6/.m2/repository/commons-codec/commons-codec/1.6/commons-codec-1.6.jar:/Users/uwalkj6/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.5.3/jackson-databind-2.5.3.jar:/Users/uwalkj6/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.5.0/jackson-annotations-2.5.0.jar:/Users/uwalkj6/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.5.3/jackson-core-2.5.3.jar:/Users/uwalkj6/.m2/repository/joda-time/joda-time/2.8.1/joda-time-2.8.1.jar:/Users/uwalkj6/.m2/repository/com/googlecode/json-simple/json-simple/1.1.1/json-simple-1.1.1.jar:/Users/uwalkj6/.m2/repository/com/google/guava/guava/18.0/guava-18.0.jar"); 
    java.lang.System.setProperty("java.class.version", "52.0"); 
        java.lang.System.setProperty("java.endorsed.dirs", "/Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home/jre/lib/endorsed"); 
    java.lang.System.setProperty("java.ext.dirs", "/Users/uwalkj6/Library/Java/Extensions:/Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home/jre/lib/ext:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java"); 
    java.lang.System.setProperty("java.library.path", "lib"); 
    java.lang.System.setProperty("java.runtime.name", "Java(TM) SE Runtime Environment"); 
    java.lang.System.setProperty("java.runtime.version", "1.8.0_25-b17"); 
    java.lang.System.setProperty("java.specification.name", "Java Platform API Specification"); 
    java.lang.System.setProperty("java.specification.vendor", "Oracle Corporation"); 
        java.lang.System.setProperty("java.vendor", "Oracle Corporation"); 
    java.lang.System.setProperty("java.vendor.url", "http://java.oracle.com/"); 
    java.lang.System.setProperty("java.version", "1.8.0_25"); 
    java.lang.System.setProperty("java.vm.info", "mixed mode"); 
    java.lang.System.setProperty("java.vm.name", "Java HotSpot(TM) 64-Bit Server VM"); 
    java.lang.System.setProperty("java.vm.specification.name", "Java Virtual Machine Specification"); 
    java.lang.System.setProperty("java.vm.specification.vendor", "Oracle Corporation"); 
    java.lang.System.setProperty("java.vm.specification.version", "1.8"); 
    java.lang.System.setProperty("java.vm.version", "25.25-b02"); 
    java.lang.System.setProperty("line.separator", "\n"); 
    java.lang.System.setProperty("os.arch", "x86_64"); 
    java.lang.System.setProperty("os.name", "Mac OS X"); 
    java.lang.System.setProperty("os.version", "10.10.4"); 
    java.lang.System.setProperty("path.separator", ":"); 
    java.lang.System.setProperty("user.country", "US"); 
    java.lang.System.setProperty("user.home", "/Users/uwalkj6"); 
    java.lang.System.setProperty("user.language", "en"); 
    java.lang.System.setProperty("user.name", "uwalkj6"); 
    java.lang.System.setProperty("user.timezone", "America/Chicago"); 
          }

  private static void initializeClasses() {
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(Common_ESTest_scaffolding.class.getClassLoader() ,
      "com.pearson.eidetic.driver.Common",
      "com.amazonaws.ClientConfiguration",
      "com.amazonaws.Protocol"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(Common_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "com.pearson.eidetic.driver.Common",
      "com.amazonaws.Protocol"
    );
  }
}
