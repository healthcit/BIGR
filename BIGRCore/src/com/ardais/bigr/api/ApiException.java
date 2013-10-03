package com.ardais.bigr.api;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * A generic exception class, to provide a uniform exception handling mechanism across BIGR.
 * ApiException allows you to wrap another exception within an ApiException in a way
 * that preserves all of the original exception's details.  It also is designed to serialize
 * its stack trace correctly across client/server boundaries in a remote procedure call.
 */
public class ApiException extends RuntimeException implements Serializable {
  private static final long serialVersionUID = -5088328040448897970L;

  private String _msg = null;

  // These two _throwable* members support the ability of an ApiException
  // to wrap another exception.  _throwableToString stores the result of calling
  // toString() on the wrapped exception, and _throwableStackTrace stores the
  // wrapped exception's stack trace.  We store these two fields as Strings
  // rather than storing the actual wrapped exception object so that this class
  // can be serialized robustly even if the wrapped exception isn't serializable.
  //
  private String _throwableToString = null;
  private String _throwableStackTrace = null;

  // Exception stack traces don't seem to serialize properly across remote
  // procedure calls, so we have a variable here that we can store the
  // stack trace in as a string that will serialize correctly.  This variable
  // gets set when the exception is constructed.
  //
  private String _myStackTrace = null;
  /**
   * Constructor Class with no input parameters
   */
  public ApiException() {
    super();

    fillInStackTrace();
  }

  public ApiException(String s) {
    this(s, (Throwable) null);
  }
  /**
   * Wrap a <code>Throwable</code> as an <code>ApiException</code>.
   *
   * Creation date: (10/29/2001 6:15:50 PM)
   *
   * @param msg an optional additional message to add to the information
   *   in the <code>Throwable</code> being wrapped.
   * @param t the <code>Throwable</code> object to wrap
   */
  public ApiException(String msg, Throwable t) {
    this();

    _msg = msg;

    setThrowable(t);

    // Throwable's constructor calls fillInStackTrace, but we need to
    // call it again here after we've set all of the local class
    // properties, since they affect the result of getMessage and
    // possibly other methods called (directly or indirectly) by
    // fillInStackTrace.
    //
    fillInStackTrace();
  }
  /**
   * Wrap a <code>Throwable</code> as an <code>ApiException</code>.
   *
   * Creation date: (10/29/2001 6:15:50 PM)
   * @param t the <code>Throwable</code> object to wrap
   */
  public ApiException(Throwable t) {
    this((String) null, t);
  }
  public Throwable fillInStackTrace() {
    Throwable t = super.fillInStackTrace();

    // Store the stack trace now as a string in a serializable field so that the
    // correct stack trace will be transferred to the clide side if an exception
    // occurs in the server side of a method call.    
    {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);

      super.printStackTrace(pw);

      pw.flush();
      pw.close();

      _myStackTrace = sw.toString();
    }

    return t;
  }
  public String getMessage() {
    String msg = ((_msg == null) ? "" : _msg);

    if (_throwableToString == null) {
      return msg;
    }
    else if (msg.length() == 0) {
      return _throwableToString;
    }
    else {
      StringBuffer sb = new StringBuffer(128);
      sb.append(msg);
      if (msg.charAt(msg.length() - 1) != '.')
        sb.append('.');
      sb.append("  Wrapped exception: ");
      sb.append(_throwableToString);
      return sb.toString();
    }
  }
  public void printStackTrace() {
    printStackTrace(System.err);
  }
  public void printStackTrace(java.io.PrintStream s) {
    synchronized (s) {
      s.print(_myStackTrace);
      if (_throwableStackTrace != null) {
        s.println("WRAPPED EXCEPTION:");
        s.print(_throwableStackTrace);
      }
    }
  }
  public void printStackTrace(java.io.PrintWriter s) {
    synchronized (s) {
      s.print(_myStackTrace);
      if (_throwableStackTrace != null) {
        s.println("WRAPPED EXCEPTION:");
        s.print(_throwableStackTrace);
      }
    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (03/13/2002 4:08:31 PM)
   * @param t java.lang.Throwable
   */
  private void setThrowable(Throwable t) {
    if (t == null) {
      _throwableToString = null;
      _throwableStackTrace = null;
      return;
    }

    // We store the toString() value for the wrapped exception rather than the
    // getMessage() value so that we don't lose information about the class of the
    // wrapped Exception (which is included in what toString() returns, but not in
    // what getMessage() returns).
    //
    _throwableToString = t.toString();
    if (_throwableToString == null)
      _throwableToString = "";

    {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);

      t.printStackTrace(pw);

      pw.flush();
      pw.close();

      _throwableStackTrace = sw.toString();
    }
  }
}
