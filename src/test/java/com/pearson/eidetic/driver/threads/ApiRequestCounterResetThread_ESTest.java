/*
 * This file was automatically generated by EvoSuite
 * Fri Nov 13 18:02:27 GMT 2015
 */

package com.pearson.eidetic.driver.threads;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import com.pearson.eidetic.aws.AwsAccount;
import com.pearson.eidetic.driver.threads.ApiRequestCounterResetThread;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true) 
public class ApiRequestCounterResetThread_ESTest extends ApiRequestCounterResetThread_ESTest_scaffolding {

  @Test
  public void test0()  throws Throwable  {
      ApiRequestCounterResetThread apiRequestCounterResetThread0 = null;
      try {
        apiRequestCounterResetThread0 = new ApiRequestCounterResetThread((List<AwsAccount>) null, 2175);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         assertThrownBy("com.pearson.eidetic.driver.threads.ApiRequestCounterResetThread", e);
      }
  }
}
