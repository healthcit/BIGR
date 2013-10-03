package com.ardais.bigr.orm.datachecker.ui.swing;

import com.ardais.bigr.orm.datachecker.framework.*;
import java.awt.Component;
import javax.swing.JTextArea;
import java.io.*;

import junit.runner.*;
import junit.framework.*;

/**
 * A failure detail view that supports tests that implement the
 * {@link TestFailureDetails} interface.  This interface allows
 * customized test-specific details to be displayed.  Otherwise this
 * class is simply a copy of the default JUnit failure detail view.
 */
class DetailedFailureDetailView implements FailureDetailView {
	JTextArea fTextArea;
	public void clear() {
		fTextArea.setText("");
	}
	/**
	 * Returns the component used to present the trace
	 */
	public Component getComponent() {
		if (fTextArea == null) {
			fTextArea= new JTextArea();
			fTextArea.setRows(5);
			fTextArea.setTabSize(0);
			fTextArea.setEditable(false);
		}
		return fTextArea;
	}
	/**
	 * Shows a TestFailure, making use of the {@link TestFailureDetails}
	 * interface for tests that implement it.
	 */
	public void showFailure(TestFailure failure) {
		String failureDetails = null;
		Test failedTest = failure.failedTest();
		
		// If the failed test implements the TestFailureDetails interface we
		// use the failure details String that the getFailureDetailString method
		// of that interface supplies.  Otherwise we use a default failure details
		// string constructed from a filtered exception stack trace.
		//
		if (failedTest instanceof TestFailureDetails) {
			failureDetails = ((TestFailureDetails)failedTest).getFailureDetailString(failure);
		}
		else {
			failureDetails = BaseTestRunner.getFilteredTrace(failure.thrownException());
		}

		fTextArea.setText(failureDetails);
		fTextArea.select(0, 0);	
	}
}
