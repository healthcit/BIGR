package com.ardais.bigr.orm.datachecker.ui;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;

import com.ardais.bigr.orm.datachecker.framework.XMLDataTestSuite;

/**
 * Various static utility methods that are useful in implementing
 * JUnit test runners.
 */
public final class TestRunnerUtils {
  /**
   * All methods on this class are static, make this private so that it
   * can't be instantiated.
   */
  private TestRunnerUtils() {
    super();
  }

  /**
   * Process the Data Checker command line arguments and return the name of the suite
   * class to run or null.  This processes arguments for the Data Checker
   * runner, which always uses dynamically-created tests.  So the suite
   * name returned is always "Dynamic Tests".
   *
   * The format of the command line is:
   * <pre>
   *     TestRunner xmlTestDocURI [testCaseId]*
   * </pre>
   * where <code>xmlTestDocURI is the URI of the XML document that defines the
   * tests, each <code>testCaseId</code> (if any) is the id of an XML element in
   * that document.  If no test case ids are specified, all tests in the document
   * are executed.  Otherwise, a test is only executed if its id or the id of one
   * of its parents is listed in the command-line arguments.
   *
   * @param runner the test runner that will run the tests
   * @param args the array of command-line arguments
   *
   * @return the test suite identifier, which must be "Dynamic Tests" for the
   *   dynamically created tests that the Data Checker uses.
   */
  public static String processArguments(DynamicTestRunner runner, String[] args) throws Exception {
    final String suiteName = "Dynamic Tests";

    Set nodesToProcess = null;

    if (args.length == 0) {
      throw new IllegalArgumentException("Usage: TestRunner xmlTestDocURI [testCaseId]*");
    }

    final String xmlDocumentURI = args[0];

    // The remaining arguments are the ids of the test nodes in the
    // XML document whose tests should be run.  We wait until here to
    // allocate a List object since a null value for nodesToProcess
    // means "process all nodes" while a non-null value containing an
    // empty List object means "process no nodes".
    //
    if (args.length > 1) {
      nodesToProcess = new HashSet();

      for (int i = 1; i < args.length; i++) {
        nodesToProcess.add(args[i]);
      }
    }

    final Set finalNodesToProcess = nodesToProcess;

    runner.setDynamicTestFactory(new DynamicTestFactory() {
      public Test getInstance() throws Exception {
        return new XMLDataTestSuite(xmlDocumentURI, finalNodesToProcess);
      }
    });

    return suiteName;
  }
}
