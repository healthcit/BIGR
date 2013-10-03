package com.ardais.bigr.api;

import java.lang.reflect.*;
/**
 * Insert the type's description here.
 * Creation date: (07/19/2002 2:42:21 PM)
 * @author: Jake Thompson
 */
public final class MainRunner {
/**
 * There's no reason to instantiate this class, so we make its constructor private.
 */
private MainRunner() {
	super();
}
/**
 * Starts the application.
 * @param args an array of command-line arguments
 */
public static void main(String[] args) {
	try {
    // Do standard application initialization.
    ApiFunctions.initialize();
    
		if (args == null || args.length < 1) {
			throw new IllegalArgumentException("Usage: MainRunner <classname> [<args> ...]");
		}

		Class runClass = Class.forName(args[0]);

		Method mainMethod = runClass.getMethod("main", new Class[] { (new String[0]).getClass() });

		String[] otherArgs = new String[args.length - 1];
		if (args.length > 1) {
			System.arraycopy(args, 1, otherArgs, 0, otherArgs.length);
		}

		mainMethod.invoke(null, new Object[] { otherArgs });
	}
	catch (Exception e) {
		// We write the exception to both System.err and the BIGR log file because
		// sometimes commands are invoked interactively (and the user will want to
		// see the message on the console) and sometimes they are invoked by
		// non-interactive processes such as cron (in which case we'll want to see
		// the error logged in the log file).
		
		StringBuffer argList = new StringBuffer(128);
		for (int i = 0; i < args.length; i++) {
			argList.append(args[i]);
			argList.append(' ');
		}
		ApiLogger.log("Failed to run command: MainRunner " + argList, e);
		System.err.println("Failed to run command: MainRunner " + argList);
		e.printStackTrace();
	}
}
}
