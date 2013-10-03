package com.ardais.bigr.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The <code>ApiLogger</code> class consists of a collection of static methods
 * for logging messages to a log file.  It uses log4j, via the Jakarta Commons
 * logging component, which is configured via its own properties file.  The
 * <code>log</code> methods of this class log an error to the default BIGR log 
 * file, which is what should be used in most situations.
 *
 * DEVELOPERS NOTE:  This class must not make use of any other Ardais-written 
 * classes.  We require this so that we can never end up in a recursive 
 * situation where we are trying to log an error which in turn calls code that 
 * encounters another error which it then tries to log by using this class.  
 * This could lead to infinite looping or synchronization deadlocks.  In 
 * particular, this class should never user the ApiFunctions class even in very 
 * simple ways, such as retrieving constants from it -- ApiFunctions has a 
 * static initializer that gets executed when it loads that does some fairly 
 * complex processing that could lead to calling back into this logging code.
 */
public class ApiLogger {

  //-- Logger Roots -------------------------------------

  /**
   * The root name of all BIGR loggers.  Any BIGR logger that is created should
   * be prefixed with this string.  The string includes a '.' at the end.
   */
  public static final String BIGR_LOGGER_ROOT = "com.ardais.bigr";

  /**
   * The name of the top-level ILTDS logger.
   */
  public static final String BIGR_ILTDS_LOGGER_ROOT = BIGR_LOGGER_ROOT + ".ILTDS";

  /**
   * The name of the top-level BIGR Library logger.
   */
  public static final String BIGR_LIBRARY_LOGGER_ROOT = BIGR_LOGGER_ROOT + ".library";

  //-- General-Purpose Loggers -------------------------------------

  /**
   * The name of the default BIGR logger.
   */
  public static final String BIGR_LOGNAME_DEFAULT = BIGR_LOGGER_ROOT + ".DEFAULT";

  /**
   * The name of the logger for computed data information.
   */
  public static final String BIGR_LOGNAME_COMPUTED_DATA = BIGR_LOGGER_ROOT + ".COMPUTEDDATA";

  /**
   * The name of the logger for Velocity information.
   */
  public static final String BIGR_LOGNAME_VELOCITY = BIGR_LOGGER_ROOT + ".VELOCITY";

  /**
   * The name of the loggers for HTTP request information.
   */
  public static final String BIGR_LOGNAME_HTTP_REQUEST_PARAMS = BIGR_LOGGER_ROOT + ".HTTP_REQUEST_PARAMS";
  public static final String BIGR_LOGNAME_HTTP_REQUEST_HEADERS = BIGR_LOGGER_ROOT + ".HTTP_REQUEST_HEADERS";

  /**
   * The name of the logger for BIGR's Validation Framework.
   */
  public static final String BIGR_LOGNAME_VALIDATION = BIGR_LOGGER_ROOT + ".VALIDATION";

  //-- BIGR Library Loggers -------------------------------------

  /**
   * The name of the logger for BIGR Library performance information.
   */
  public static final String BIGR_LOGNAME_LIBRARY_PERFORMANCE =
    BIGR_LIBRARY_LOGGER_ROOT + ".PERFORMANCE";

  /**
   * The name of the logger for BIGR Library database query information.
   */
  public static final String BIGR_LOGNAME_LIBRARY_QUERY = BIGR_LIBRARY_LOGGER_ROOT + ".QUERY";

  /**
   * The name of the logger for BIGR Library metadata.
   */
  public static final String BIGR_LOGNAME_LIBRARY_META = BIGR_LIBRARY_LOGGER_ROOT + ".META";

  //-- ILTDS Loggers -------------------------------------

  /**
   * The name of the logger for ILTDS Receipt Verification.
   */
  public static final String BIGR_LOGNAME_ILTDS_RECEIPT_VERIFICATION =
    BIGR_ILTDS_LOGGER_ROOT + ".RECEIPTVERIFICATION";

  /**
   * The default logger.
   */
  private static Log _bigrLog = LogFactory.getLog(BIGR_LOGNAME_DEFAULT);

  /**
   * This is private -- the methods of this class are static and no instance
   * should ever be constructed..
   */
  private ApiLogger() {
    super();
  }

  /**
   * Convenience method equivalent to <code>getLog().debug(...)</code>.
   */
  public static void debug(String msg) {
    _bigrLog.debug(msg);
  }

  /**
   * Convenience method equivalent to <code>getLog().debug(...)</code>.
   */
  public static void debug(String msg, Throwable t) {
    _bigrLog.debug(msg, t);
  }

  /**
   * Convenience method equivalent to <code>getLog().info(...)</code>.
   */
  public static void info(String msg) {
    _bigrLog.info(msg);
  }

  /**
   * Convenience method equivalent to <code>getLog().info(...)</code>.
   */
  public static void info(String msg, Throwable t) {
    _bigrLog.info(msg, t);
  }

  /**
   * Convenience method equivalent to <code>getLog().warn(...)</code>.
   */
  public static void warn(String msg) {
    _bigrLog.warn(msg);
  }

  /**
   * Convenience method equivalent to <code>getLog().warn(...)</code>.
   */
  public static void warn(String msg, Throwable t) {
    _bigrLog.warn(msg, t);
  }

  /**
   * Convenience method equivalent to <code>getLog().error(...)</code>.
   */
  public static void error(String msg) {
    _bigrLog.error(msg);
  }

  /**
   * Convenience method equivalent to <code>getLog().error(...)</code>, except as noted below.
   * 
   * <p>
   * If the exception is a "Broken pipe" exception we only log it if DEBUG logging is
   * enabled.  See MR 5673 for details
   * on why we need to detect these specially.  In short, WebSphere throws these when the user
   * navigates away from a page before it has been completely sent from the server (and the
   * server attempts to write the rest of the response to what is now a
   * "broken communication pipe").  The user never sees them (because the pipe from the server to
   * them is broken), and they don't signify a true problem, but they do clutter up our log files.
   * These exceptions don't have a special class, and we've seen them show up with various
   * classes such as IOException and ServletException.  The best test we can make is that the
   * message part of the exception is always "Broken pipe".  This doesn't feel like a good way
   * to test it since for all we know there are completely different exceptions that other
   * components could throw that have the same message, but it seems to be the best we can do for
   * now.
   */
  public static void error(String msg, Throwable t) {
    if (_bigrLog.isErrorEnabled() && (!isIgnorableException(t))) {
      _bigrLog.error(msg, t);
    }
  }

  /**
   * Convenience method equivalent to <code>getLog().fatal(...)</code>.
   */
  public static void fatal(String msg) {
    _bigrLog.fatal(msg);
  }

  /**
   * Convenience method equivalent to <code>getLog().fatal(...)</code>.
   */
  public static void fatal(String msg, Throwable t) {
    _bigrLog.fatal(msg, t);
  }

  /**
   * Logs a message to the default log file.
   *
   * @param  msg  the message to log
   */
  public static void log(String msg) {
    error(msg);
  }

  /**
   * Logs a message regarding an exception to the default log file.  If the msg 
   * parameter is not null, it is prepended to the exception information.  This 
   * logs the exception along with its stack trace.  The message is logged at
   * the ERROR level.
   * 
   * <p>
   * If the exception is a "Broken pipe" exception we only log it if DEBUG logging is
   * enabled.  See MR 5673 for details
   * on why we need to detect these specially.  In short, WebSphere throws these when the user
   * navigates away from a page before it has been completely sent from the server (and the
   * server attempts to write the rest of the response to what is now a
   * "broken communication pipe").  The user never sees them (because the pipe from the server to
   * them is broken), and they don't signify a true problem, but they do clutter up our log files.
   * These exceptions don't have a special class, and we've seen them show up with various
   * classes such as IOException and ServletException.  The best test we can make is that the
   * message part of the exception is always "Broken pipe".  This doesn't feel like a good way
   * to test it since for all we know there are completely different exceptions that other
   * components could throw that have the same message, but it seems to be the best we can do for
   * now.
   *
   * @param  msg  the optional prefix message
   * @param  t  the exception to log
   */
  public static void log(String msg, Throwable t) {
    error(msg, t);
  }

  /**
   * Logs an exception to the default log file.
   *
   * @param  e  the exception to log
   */
  public static void log(Throwable e) {
    log("Exception occurred", e);
  }

  /**
   * Returns the default logger.  If the intention is to log an error, then use
   * one of this class' <code>log</code> methods.
   *
   * @return  The default logger.
   */
  public static Log getLog() {
    return _bigrLog;
  }

  /**
   * Return a logger name formed by appending suffix to prefix, separating them with a period.
   * If either prefix or suffix is empty, the other is returned without adding a period.
   */
  public static String getLoggerName(String prefix, String suffix) {
    if (prefix == null || prefix.length() == 0) {
      return suffix;
    }
    if (suffix == null || suffix.length() == 0) {
      return prefix;
    }
    return prefix + "." + suffix;
  }

  /**
   * Return true if the specified object is a "Broken pipe" exception.  See MR 5673 for details
   * on why we need to detect these specially.  In short, WebSphere throws these when the user
   * navigates away from a page before it has been completely sent from the server (and the
   * server attempts to write the rest of the response to what is now a
   * "broken communication pipe").  The user never sees (because the pipe from the server to them
   * is broken), and they don't signify a true problem, but they do clutter up our log files.
   * These exceptions don't have a special class, and we've seen them show up with various
   * classes such as IOException and ServletException.  The best test we can make is that the
   * message part of the exception always ends with "Broken pipe".  This doesn't feel like a good
   * way to test it since for all we know there are completely different exceptions that other
   * components could throw that have the same message, but it seems to be the best we can do for
   * now.
   * 
   * @param the exception to test
   * @return true if the exception is a "Broken pipe" exception.
   */
  private static boolean isBrokenPipeException(Throwable e) {
    if (e != null && e instanceof Exception) {
      String message = e.getMessage();
      if (message != null) {
        if (message.endsWith("Broken pipe")) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Return true if the Throwable is a "Broken pipe" exception or other exception
   * that can be ignored.  Whether an exception is ignorable may depend on the level
   * of logging that is enabled. 
   * 
   * @see #error(String, Throwable)
   */
  public static boolean isIgnorableException(Throwable t) {
    // A broken pipe exception is ignorable when debug logging is disabled

    return ((!_bigrLog.isDebugEnabled()) && isBrokenPipeException(t));
  }

}
