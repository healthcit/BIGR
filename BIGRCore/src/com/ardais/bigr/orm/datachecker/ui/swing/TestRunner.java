package com.ardais.bigr.orm.datachecker.ui.swing;

import javax.swing.JOptionPane;

import junit.framework.Test;
import junit.runner.BaseTestRunner;
import junit.runner.FailureDetailView;

import com.ardais.bigr.orm.datachecker.ui.DynamicTestFactory;
import com.ardais.bigr.orm.datachecker.ui.DynamicTestRunner;
import com.ardais.bigr.orm.datachecker.ui.TestRunnerUtils;

/**
 * A specialization of the default JUnit Swing user interface that is customized
 * to support the Data Checker's special needs, such as the ability to run
 * dynamically constructed tests.
 */
public class TestRunner extends junit.swingui.TestRunner implements DynamicTestRunner {
  /**
   * The factory used to dynamically create Data Checker tests.
   */
  private DynamicTestFactory _dynamicTestFactory = null;

  public TestRunner() {
    super();
  }

  /**
   * Create a view to diplay test failures. This overrides the default JUnit implementation,
   * which allowed you to specify a failure view class in a properties file and defaulted
   * to the JUnit junit.swingui.DefaultFailureDetailView class.  Unfortunately the properties file mechanism
   * is implemented in a way that would make it awkward to customize robustly in our environment
   * (the properties files are store in a user-specific location).  So, instead, we mimic
   * the JUnit approach but use a different default class ({@link DetailedFailureDetailView}).
   */
  protected FailureDetailView createFailureDetailView() {
    // JUnit defines FAILUREDETAILVIEW_KEY as private so we have to duplicate its
    // definition here (I use a slightly different name to avoid confusion).
    //
    final String FAILURE_DETAIL_VIEW_KEY = "FailureViewClass";

    String className = BaseTestRunner.getPreference(FAILURE_DETAIL_VIEW_KEY);
    if (className != null) {
      Class viewClass = null;
      try {
        viewClass = Class.forName(className);
        return (FailureDetailView) viewClass.newInstance();
      }
      catch (Exception e) {
        JOptionPane.showMessageDialog(
          fFrame,
          "Could not create Failure DetailView - using default view");
      }
    }
    return new DetailedFailureDetailView();
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
   * Processes the command line arguments and
   * returns the name of the suite class to run or null.
   * See {@link TestRunnerUtils#processArguments(DynamicTestRunner, String[]) TestRunnerUtils.processArguments}
   * for a description of the command line arguments that the Data Checker supports.
   */
  protected String processArguments(String[] args) {
    setLoading(false);

    String suiteName = null;

    try {
      suiteName = TestRunnerUtils.processArguments(this, args);
    }
    catch (Exception e) {
      // This intentionally sends output to System.out rather logging
      // as we usually do with error messages since this method gets
      // called when a program is invoked from a command line and we want
      // the user to see the error output in their command window.
      //
      System.out.println(e.toString());
      suiteName = null;
    }

    return suiteName;
  }

  public void setDynamicTestFactory(DynamicTestFactory newValue) {
    _dynamicTestFactory = newValue;
  }

  public static void main(String[] args) {
    new TestRunner().start(args);
  }
}
