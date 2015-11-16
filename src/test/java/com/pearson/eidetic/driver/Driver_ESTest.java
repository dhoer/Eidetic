/*
 * This file was automatically generated by EvoSuite
 * Fri Nov 13 17:59:46 GMT 2015
 */

package com.pearson.eidetic.driver;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import com.pearson.eidetic.driver.Driver;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.System;
import org.evosuite.runtime.testdata.EvoSuiteFile;
import org.evosuite.runtime.testdata.FileSystemHandling;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true) 
public class Driver_ESTest extends Driver_ESTest_scaffolding {

  @Test
  public void test0()  throws Throwable  {
      boolean boolean0 = Driver.readAndSetLogbackConfiguration("7x#[", "7x#[");
      assertFalse(boolean0);
  }

  @Test
  public void test1()  throws Throwable  {
      boolean boolean0 = Driver.initializeApplication();
      assertFalse(boolean0);
  }

  @Test
  public void test2()  throws Throwable  {
      Driver driver0 = new Driver();
  }

  @Test
  public void test3()  throws Throwable  {
      Driver.startAccountSnapshotThreads();
  }

  @Test
  public void test4()  throws Throwable  {
      Driver.startAccountSnapshotCreationTimeThreads();
  }

  @Test
  public void test5()  throws Throwable  {
      Driver.startAccountSnapshotCleaningThreads();
  }

  @Test
  public void test6()  throws Throwable  {
      Driver.startAccountSnapshotCheckerThreads();
  }

  @Test
  public void test7()  throws Throwable  {
      Driver.startAccountCopySnapshotThreads();
  }

}
