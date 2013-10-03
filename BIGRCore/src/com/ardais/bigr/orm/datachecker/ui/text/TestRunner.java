package com.ardais.bigr.orm.datachecker.ui.text;

import java.io.PrintStream;
import java.util.Enumeration;

import junit.framework.Test;
import junit.framework.TestFailure;
import junit.framework.TestResult;

import com.ardais.bigr.orm.datachecker.framework.TestFailureDetails;
import com.ardais.bigr.orm.datachecker.ui.DynamicTestFactory;
import com.ardais.bigr.orm.datachecker.ui.DynamicTestRunner;
import com.ardais.bigr.orm.datachecker.ui.TestRunnerUtils;

/**
 * A specialization of the default JUnit text user interface that is customized
 * to support the Data Checker's special needs, such as the ability to run
 * dynamically constructed tests.
 */
public class TestRunner extends junit.textui.TestRunner implements DynamicTestRunner {
  /**
   * The factory used to dynamically create Data Checker tests.
   */
  private DynamicTestFactory _dynamicTestFactory = null;
  
  public TestRunner() {
    super();
  }
  
  public DynamicTestFactory getDynamicTestFactory() {
    return _dynamicTestFactory;
  }
  
  /**
   * Returns the Test corresponding to the given suite.  This overrides
   * the default JUnit implementation iin order to support dynamic test
   * construction.
   */
  public Test getTest(String suiteClassName) {
    if (suiteClassName != null && suiteClassName.equals("Dynamic Tests")) {
      clearStatus();
      try {
        return getDynamicTestFactory().getInstance();
      }
      catch (Exception e) {
        runFailed("Failed to get dynamic test suite:" + e.toString());
        return null;
      }
    }
    else {
      return super.getTest(suiteClassName);
    }
  }

  /**
   * Prints failures to the standard output, making use of the {@link TestFailureDetails}
   * interface for tests that implement it.  This overrides the JUnit default,
   */
  public void printFailures(TestResult result) {
    if (result.failureCount() != 0) {
      PrintStream writer = writer();
      if (result.failureCount() == 1)
        writer.println("There was " + result.failureCount() + " failure:");
      else
        writer.println("There were " + result.failureCount() + " failures:");
      int i = 1;
      for (Enumeration e = result.failures(); e.hasMoreElements(); i++) {
        TestFailure failure = (TestFailure) e.nextElement();
        Test failedTest = failure.failedTest();

        writer.println();
        writer.println(i + ") " + failedTest);
        writer.print("   ");

        // If the failed test implements the TestFailureDetails interface we
        // use the failure details String that the getFailureDetailString method
        // of that interface supplies.  Otherwise we use a default failure details
        // string constructed from a filtered exception stack trace.
        //
        if (failedTest instanceof TestFailureDetails) {
          writer.println(((TestFailureDetails) failedTest).getFailureDetailString(failure));
        }
        else {
          writer.println(getFilteredTrace(failure.thrownException()));
        }
      }
    }
  }
  
  public void setDynamicTestFactory(DynamicTestFactory newValue) {
    _dynamicTestFactory = newValue;
  }
  
  /**
   * Starts a test run. Analyzes the command line arguments
   * and runs the given test suite.
   * See {@link TestRunnerUtils#processArguments(DynamicTestRunner, String[]) TestRunnerUtils.processArguments}
   * for a description of the command line arguments that the Data Checker supports.
   */
  protected TestResult start(String args[]) throws Exception {
    boolean wait = false;

    String suiteName = TestRunnerUtils.processArguments(this, args);

    try {
      Test test = getTest(suiteName);
      return doRun(test, wait);
    }
    catch (Exception e) {
      throw new Exception("Could not create and run test suite: " + e.toString());
    }
  }
  
  public static void main(String args[]) {
    TestRunner aTestRunner = new TestRunner();
    try {
      TestResult r = aTestRunner.start(args);
      if (!r.wasSuccessful())
        System.exit(-1);
      System.exit(0);
    }
    catch (Exception e) {
      System.err.println(e.getMessage());
      System.exit(-2);
    }
  }
}
