package com.ardais.bigr.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import sun.misc.BASE64Encoder;

import com.ardais.bigr.beanutils.BigrConvertUtilsBean;
import com.ardais.bigr.util.BigrSecureRandom;

/**
 * Generic ApiFunctions class, calls from the Security Manager, Connection Pool Manager etc. would
 * be made to this class, which contains functions for dedicated and properties file driven DB
 * Connections, Connection Pool calls, generic Query and Transaction DB Calls, block insert and
 * update function calls, Connection close and Session invalidate functions et al.
 */
public class ApiFunctions {
  /**
   * The preferred classpath location under which BIGR resources are located. This is the preferred
   * place to put resources that should be considered part of the software itself, rather than part
   * of the description of how to install or deploy the software. Any resources that we'd expect to
   * be different in different deployments should NOT got here -- possibly Api.properties or some
   * other file under the ApiResources.getResourceDirectory() directory would be a better location
   * for such information.
   */
  public static final String CLASSPATH_RESOURCES_PREFIX = "/com/ardais/bigr/resources/";

  /**
   * An empty string constant to ensure that all empty strings are represented by the same string
   * object (to use memory more efficiently).
   */
  public static final String EMPTY_STRING = "";

  public static final String[] EMPTY_STRING_ARRAY = new String[0];

  public static final String[][] EMPTY_STRING_ARRAY_ARRAY = new String[0][0];

  public static final String LABELPRINTING_FAIL = "fail";

  public static final String LABELPRINTING_PASS = "pass";

  public static final String SAMPLE_LABEL_PRINTER = "sampleprn";

  public static final String FROZEN_SLIDELABEL_PRINTER = "slidefro";

  public static final String PARAFFIN_SLIDELABEL_PRINTER = "slideprn";

  public static final String SAMPLE_FILENAME_PREFIX = ApiResources
      .getResourcePath("limsreport/sample_label_");

  public static final String SLIDE_FILENAME_PREFIX = ApiResources
      .getResourcePath("limsreport/slide_label_");

  public static final String SAMPLE_LABEL_TYPE = "sample";

  public static final String SLIDE_LABEL_TYPE = "slide";

  public static final String LABEL_INFO_SMALL_LABEL = "smallLabel";

  public static final String LABEL_INFO_SAMPLE_ID = "sampleId";

  private static final String REPEAT_FRAGMENT_MARKER_BEGIN = "%[";

  private static final String REPEAT_FRAGMENT_MARKER_END = "%]";

  private static javax.sql.DataSource _ds = null;
  private static javax.sql.DataSource _ds_v5 = null;

  private static Object _dsLock = new Object();

  // Used as synchronization locks to prevent simultaneous attempts
  // to print to the same printer. There's one lock per printer.
  //
  private static Map _printerLocks;

  private static boolean _isInitialized = false;

  static {
    initialize();
  }

  public ApiFunctions() {
  }

  /**
   * Close/free a {@link TemporaryClob}object. The object may be null, in which case nothing
   * happens.
   * 
   * @param obj the object to close
   * @param con the database connection that the temporary clob was created in
   */
  public static void close(TemporaryClob obj, Connection con) {
    if (obj == null) return;

    if (con == null) {
      throw new IllegalArgumentException(
          "The connection must not be null, a temporary clob must be freed before closing its connection.");
    }

    obj.free(con);
  }
  
  /**
   * Close/free a {@link TemporaryBlob}object. The object may be null, in which case nothing
   * happens.
   * 
   * @param obj the object to close
   * @param con the database connection that the temporary blob was created in
   */
  public static void close(TemporaryBlob obj, Connection con) {
    if (obj == null) return;

    if (con == null) {
      throw new IllegalArgumentException(
          "The connection must not be null, a temporary blob must be freed before closing its connection.");
    }

    obj.free(con);
  }

  /**
   * Close an input stream, throwing an ApiException rather than an IOException on error.
   * 
   * @param obj the object to close
   */
  public static void close(InputStream obj) {
    if (obj == null) return;
    try {
      obj.close();
    }
    catch (IOException ex) {
      throw new ApiException("Error closing input stream", ex);
    }
  }

  /**
   * Close an output stream, throwing an ApiException rather than an IOException on error.
   * 
   * @param obj the object to close
   */
  public static void close(OutputStream obj) {
    if (obj == null) return;
    try {
      obj.close();
    }
    catch (IOException ex) {
      throw new ApiException("Error closing output stream", ex);
    }
  }

  /**
   * Close a reader, throwing an ApiException rather than an IOException on error.
   * 
   * @param obj the object to close
   */
  public static void close(Reader obj) {
    if (obj == null) return;
    try {
      obj.close();
    }
    catch (IOException ex) {
      throw new ApiException("Error closing reader", ex);
    }
  }

  /**
   * Close a writer, throwing an ApiException rather than an IOException on error.
   * 
   * @param obj the object to close
   */
  public static void close(Writer obj) {
    if (obj == null) return;
    try {
      obj.close();
    }
    catch (IOException ex) {
      throw new ApiException("Error closing writer", ex);
    }
  }

  /**
   * Close a database connection, throwing an ApiException rather than an SQLException on error.
   * 
   * @param obj the object to close
   */
  public static void close(Connection obj) {
    if (obj == null) return;
    try {
      obj.close();
    }
    catch (SQLException e) {
      throwAsRuntimeException(e);
    }
  }

  /**
   * Close a database connection, statement and result set, throwing an ApiException rather than an
   * SQLException on error. Any of the parameters may be null, in which case it does not attempt to
   * close the corresponding object. This is a convenience method to handle the very common case
   * where all three of these objects are closed at once.
   * 
   * @param connection the connection to close
   * @param statement the statement to close
   * @param resultSet the result set to close
   */
  public static void close(Connection connection, Statement statement, ResultSet resultSet) {
    close(resultSet);
    close(statement);
    close(connection);
  }

  /**
   * Close a database result set, throwing an ApiException rather than an SQLException on error.
   * 
   * @param obj the object to close
   */
  public static void close(ResultSet obj) {
    if (obj == null) return;
    try {
      obj.close();
    }
    catch (SQLException ex) {
      throw new ApiException("Error closing database result set", ex);
    }
  }

  /**
   * Close a database statement, throwing an ApiException rather than an SQLException on error.
   * 
   * @param obj the object to close
   */
  public static void close(Statement obj) {
    if (obj == null) return;
    try {
      obj.close();
    }
    catch (SQLException ex) {
      throw new ApiException("Error closing database statement", ex);
    }
  }

  /**
   * Close a naming context, throwing an ApiException rather than an NamingException on error.
   * 
   * @param obj the object to close
   */
  public static void close(javax.naming.Context obj) {
    if (obj == null) return;
    try {
      obj.close();
    }
    catch (NamingException ex) {
      throw new ApiException("Error closing naming context: " + ex.getExplanation(), ex);
    }
  }

  /**
   * Close a socket, throwing an ApiException rather than an IOException on error.
   * 
   * @param obj the socket to close
   */
  public static void close(Socket obj) {
    if (obj == null) return;
    try {
      obj.close();
    }
    catch (IOException ex) {
      throw new ApiException("Error closing socket", ex);
    }
  }

  /**
   * Close a database connection, throwing an ApiException rather than an SQLException on error.
   * 
   * @param obj the object to close
   */
  public static void closeDbConnection(Connection obj) {
    close(obj);
  }

  /**
   * Convert an arbitrary exception to an exception that contains the same information but is a
   * {@link RuntimeException}instead. If the exception is already an {@link ApiException}, this
   * simply throws it. Otherwise this throws an {@link ApiException}that wraps <code>e</code>.
   * We wrap the original exception with an ApiException even if it is already a RuntimeException
   * because ApiException preserves stack trace information about the wrapped exception when an
   * exception is passed from the server side of an EJB call to the client side. Other exceptions
   * normally lose their server-side stack trace information in this situation, and that information
   * can be very helpful in diagnosing problems.
   * 
   * If you are just going to throw the converted exception and not do anything else with it, you
   * should use {@link #throwAsRuntimeException(Exception) throwAsRuntimeException}instead.
   * 
   * @param e the exception to convert
   * @return <code>e</code> if it is already an <code>ApiException</code>, otherwise an
   *         {@link ApiException}that wraps <code>e</code>.
   */
  public static RuntimeException convertToRuntimeException(Exception e) {
    if (e instanceof ApiException) {
      return (RuntimeException) e;
    }
    else {
      return new ApiException(e);
    }
  }

  /**
   * Insert the method's description here. Creation date: (5/4/01 5:01:51 PM)
   * 
   * @return java.lang.String
   */
  public static String encryptPassword(String input) {
    byte[] buf = safeString(input).getBytes();

    MessageDigest algorithm = null;
    try {
      algorithm = MessageDigest.getInstance("SHA-1");
    }
    catch (NoSuchAlgorithmException ex) {
      throwAsRuntimeException(ex);
    }
    algorithm.reset();
    algorithm.update(buf);
    byte[] digest = algorithm.digest();

    BASE64Encoder encoder = new BASE64Encoder();
    String output = encoder.encode(digest);
    output = output.substring(0, output.length() - 1);

    return output;
  }
  
  public static boolean generateEmail(String fromAddress, String recipient, String subject,
                                      String body) {
    return generateEmail(fromAddress, recipient, subject, body, true);
  }

  public static boolean generateEmail(String fromAddress, String recipient, String subject,
                                      String body, boolean asHtml) {
    try {
      if ((fromAddress != null && !fromAddress.trim().equals(""))
          && (recipient != null && !recipient.trim().equals(""))) {
        String smtpHost = ApiProperties.getProperty(ApiResources.SMTP_HOST);

        // If smtpHost is NONE (case-insensitive), don't attempt to send email and just
        // return true as if the email was successfully sent. A real BIGR server should
        // never be configured to have NONE as the SMTP host since some of the email messages
        // are critical (for example, emailing a new password to a user), but this option is
        // handy on demo machines that aren't connected to a network where they can reach
        // an SMTP server (for example, the standalone tradeshow demo machine).
        //
        if ("NONE".equalsIgnoreCase(smtpHost)) {
          return true;
        }

        //Start a session
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", smtpHost);

        Session session1 = Session.getInstance(prop, null);

        //Construct a message
        MimeMessage msg = new MimeMessage(session1);
        msg.setFrom(new InternetAddress(fromAddress));
        StringTokenizer recipients = new StringTokenizer(recipient, ";");
        while (recipients.hasMoreElements()) {
          msg.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(
              (String) recipients.nextElement()));
        }
        msg.setSubject(subject);

        StringBuffer sb = new StringBuffer();
        if (asHtml) {
          sb.append("<HTML>\n");
          sb.append("<HEAD>\n");
          sb.append("<TITLE<\n");
          sb.append(subject + "\n");
          sb.append("</TITLE>\n");
          sb.append("</HEAD>\n");
          sb.append("<BODY bgcolor=\"#ffffff\">\n");
          sb
              .append("<TABLE bgcolor=\"#336666\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><table border=\"0\" cellpadding=\"0\" cellspacing=\"1\" width=\"640\"><tr bgcolor=\"#ffffff\"><td align=\"center\"><font face=\"Arial, Helvetica, sans-serif\" color=\"#336666\"><H3>"
                  + subject + "</H3></font></td></tr>");
          sb
              .append("<tr bgcolor=\"#ffffff\"><td><font face=\"Arial, Helvetica, sans-serif\" size=\"2\">"
                  + body + "</font></td></tr>");
          sb.append("</font></td></tr></table></td></tr></table></BODY>\n");
          sb.append("<HTML>\n");
  
          msg.setContent(sb.toString(), "text/html");
        }
        else {
          sb.append(body);
          msg.setContent(sb.toString(), "text/plain");
        }
        msg.setHeader("X-Mailer", "sendmail");

        Transport.send(msg);
      }
    }
    catch (SendFailedException sfe) {
      ApiLogger.error("Generate email failed.", sfe);
      return false;
    }
    catch (Exception e) {
      throwAsRuntimeException(e);
    }
    return true;
  }

	/**
	* Generic Connection function to get a database connection with Default JDBC settings using DataSource v4
	*
	* @return Connection
	*/
	public static Connection getDbConnection()
	{
	    return getDbConnection(new BigrDataSourceHolder()
		{
			@Override
			public void setDataSource(DataSource dataSource)
			{
				_ds = dataSource;
			}

			@Override
			public DataSource getDataSource()
			{
				return _ds;
			}

			@Override
			public String getDataSourcePropertyKey()
			{
				return ApiResources.API_DATASOURCE;
			}
		});
	}

	/**
	 * Generic Connection function to get a database connection with Default JDBC settings using DataSource v5
	 *
	 * @return Connection
	 */
	public static Connection getDbConnectionV5()
	{
		return getDbConnection(new BigrDataSourceHolder()
		{
			@Override
			public void setDataSource(DataSource dataSource)
			{
				_ds_v5 = dataSource;
			}

			@Override
			public DataSource getDataSource()
			{
				return _ds_v5;
			}

			@Override
			public String getDataSourcePropertyKey()
			{
				return ApiResources.API_DATASOURCE_V5;
			}
		});
	}

  private static Connection getDbConnection(BigrDataSourceHolder dsHolder) {
    Connection con = null;
    try {
      String user = ApiProperties.getProperty(ApiResources.API_DBUSER);
      String pass = ApiProperties.getProperty(ApiResources.API_DBPWD);
      synchronized (_dsLock) {
        initDataSource(dsHolder); // this does nothing if it is already initialized
        con = dsHolder.getDataSource().getConnection(user, pass);
      }
    }
    catch (Exception ex) {
      throwAsRuntimeException(ex);
    }
    return con;
  }

  /**
   * Return the value of the input CLOB as a String. If the CLOB is <code>null</code>, then
   * <code>null</code> is returned.
   * 
   * @param clob the CLOB
   * @return A String that holds the contents of the CLOB.
   */
  public static String getStringFromClob(Clob clob) throws SQLException {
    String returnValue = null;
    if (clob != null) {
      BufferedReader r = null;
      try {
        r = new BufferedReader(clob.getCharacterStream());
        StringBuffer buf = new StringBuffer();
        for (int c = r.read(); c != -1; c = r.read()) {
          buf.append((char) c);
        }
        returnValue = buf.toString();
      }
      catch (IOException ioe) {
        throwAsRuntimeException(ioe);
      }
      finally {
        if (r != null) {
          try {
            r.close();
          }
          catch (IOException ioe) {
            throwAsRuntimeException(ioe);
          }
        }
      }
    }
    return returnValue;
  }

  /**
   * The first time this is called it initializes the static members of this class. Subsequent calls
   * do nothing.
   */
  public static synchronized void initialize() {
    if (_isInitialized) return;

    // First initialize log4j, as anything else we do here might log something.
    //
    initLog4j();

    // Initialize the random number generator.
    //
    BigrSecureRandom.initialize();

    // We no longer initialize the DataSource here, we wait until the first time someone tries
    // to get a database connection instead. This avoids problems when someone is just trying
    // run some simple code from a "main" method that doesn't use the database. Formerly,
    // unless the classpath was set up carefully and the app server was running, exceptions
    // were sometimes thrown when trying to load ApiFunctions for other purposes.

    {
      // _printerLocks needs to have one entry per slide printer, with the
      // key being the printer name (case sensitive). Each entry in the map
      // must have a *different* object as its value, but the value can be any
      // non-null object as long as they are all different instances.

      _printerLocks = new HashMap();

      _printerLocks.put(SAMPLE_LABEL_PRINTER, new Object());
      _printerLocks.put(FROZEN_SLIDELABEL_PRINTER, new Object());
      _printerLocks.put(PARAFFIN_SLIDELABEL_PRINTER, new Object());
    }

    _isInitialized = true;
  }

  /**
   * Shut BIGR down gracefully.
   */
  public static synchronized void shutdownBIGR() {
    if (!_isInitialized) return;

    PeriodicNotifier.removeAllListeners();

    freeDataSources();

  }

  /**
   * Initialize log4j. This must only be called from the initialize method.
   */
  private static void initLog4j() {
    // Make log4j re-read its configuration file periodically.
    // We set it up to check the file to see if it has been updated
    // every minute.
    //
    // Getting the property file name is a bit fragile, since the system
    // property that we use to specify the filename initially expects a URL,
    // but the configureAndWatch method we need to call here takes a file
    // name, not a URL. So, we get the system property and expect it to have
    // the form "file:<absolute path>", for example
    // file:/opt/WebSphere/AppServer/bigr/log4j.properties. If the system
    // property doesn't exist at all or has an empty value we don't throw
    // an exception, we just don't do anything to configure log4j. But
    // if the property exists and doesn't start with "file:" we log an
    // error (though we don't throw an exception).

    String log4jPropURL = System.getProperty("log4j.configuration", "file:"
        + ApiResources.getResourcePath("log4j.properties"));

    if (log4jPropURL.length() > 0) {
      if (!log4jPropURL.startsWith("file:")) {
        ApiLogger.log("ApiFunctions.initLog4j expected the log4j.properties "
            + "system property to start with file:, but its value is: " + log4jPropURL
            + "  The log4j configuration file will not be re-read periodically.");
      }
      else {
        // Get the file name, everything after the file:
        //
        String log4jPropFile = log4jPropURL.substring(5);

        // The second argument is the recheck time interval at which
        // log4j will check to see if its configuration file has been
        // updated. It is a number of milliseconds. So, 60000 = 1 minute.
        //
        // This passes true to the notifyAtStartup argument so that the log4j properties file
        // will always be read immediately on initialization, even though this causes it to be
        // initialized twice in some cases. Initializing it twice doesn't do any harm, and this
        // was helpful in some cases when code was being run outside of a WebSphere app server.
        //
        new FileWatcher(log4jPropFile, true, 60000, new FileWatcherListener() {
          public void onFileChanged(FileWatcher watcher) {
            new PropertyConfigurator().doConfigure(watcher.getFilename(), LogManager
                .getLoggerRepository());
          }
        });
      }
    }
  }

  /**
   * Initialize the static DataSource object. This must be called before anything tries to access
   * the _ds variable.
   */
  private static void initDataSource(BigrDataSourceHolder dsHolder) {
    synchronized (_dsLock) {
      if (dsHolder.getDataSource() == null) {
        String connJdbc = ApiProperties.getProperty(dsHolder.getDataSourcePropertyKey()).trim();
        Context ctx = null;
        try {
          java.util.Properties params = new Properties();

          String providerURL = safeTrim(ApiProperties.getProperty(ApiResources.API_PROVIDER_URL));

          if (!ApiFunctions.isEmpty(providerURL)) {
            params.put(javax.naming.Context.PROVIDER_URL, providerURL);
          }

          params.put(Context.INITIAL_CONTEXT_FACTORY, safeTrim(ApiProperties
              .getProperty(ApiResources.API_INITIAL_CONTEXT_FACTORY)));

          ctx = new InitialContext(params);
		  dsHolder.setDataSource((javax.sql.DataSource) ctx.lookup(connJdbc));
        }
        catch (NamingException e) {
          throw new ApiException("Data Source not set up properly: " + e.getExplanation(), e);
        }
        catch (Exception ex) {
          throw new ApiException("Data Source not set up properly: ", ex);
        }
        finally {
          close(ctx);
        }
      }
    }
  }

  /**
   * Dispose of the static DataSource object.
   */
  private static void freeDataSources() {
    synchronized (_dsLock) {
		if (_ds != null) {
			_ds = null;
		}
		if (_ds_v5 != null) {
			_ds_v5 = null;
		}
    }
  }

  /**
   * Returns <code>true</code> if the map is either null or empty, <code>false</code> otherwise.
   * 
   * @param m the <code>Map</code>
   * @return <code>true</code> if the map is empty
   */
  public static boolean isEmpty(Map m) {
    return ((m == null) || m.isEmpty());
  }

  /**
   * Returns <code>true</code> if the collection is either null or empty, <code>false</code>
   * otherwise.
   * 
   * @param c the <code>Collection</code>
   * @return <code>true</code> if the collection is empty
   */
  public static boolean isEmpty(Collection c) {
    return ((c == null) || c.isEmpty());
  }

  /**
   * Returns <code>true</code> if the string is either <code>null</code> or the length < 0
   * <code>false</code> otherwise.
   * 
   * @param s the <code>String</code>
   * @return <code>true</code> if the string is empty
   */
  public static boolean isEmpty(String[] s) {
    return ((s == null) || (s.length == 0));
  }

  /**
   * Returns <code>true</code> if the string is either <code>null</code> or the empty string
   * (""); <code>false</code> otherwise.
   * 
   * @param s the <code>String</code>
   * @return <code>true</code> if the string is empty
   */
  public static boolean isEmpty(String s) {
    return ((s == null) || (s.length() == 0));
  }

  /**
   * Returns <code>true</code> if the string is either <code>null</code> or the empty string
   * ("") or is all whitespace (that is, it is empty after calling {@link String#trim()}on it);
   * <code>false</code> otherwise.
   * 
   * @param s the <code>String</code>
   * @return <code>true</code> if the string is empty or all whitespace
   */
  public static boolean isEmptyOrWhitespace(String s) {
    return ((s == null) || (s.length() == 0) || (s.trim().length() == 0));
  }

  /**
   * Returns <code>true</code> if all the strings are either <code>null</code> or the empty
   * string (""); <code>false</code> otherwise.
   * 
   * @param s the <code>String[]</code> holding the strings to check
   * @return <code>true</code> if the strings are all empty
   */
  public static boolean isAllEmpty(String[] s) {
    if (s != null) {
      for (int i = 0; i < s.length; i++) {
        if (!isEmpty(s[i])) return false;
      }
    }
    return true;
  }

  /**
   * Returns <code>true</code> if the string is an integer; <code>false</code> otherwise. If the
   * input string is <code>null</code>, then <code>false</code> is returned.
   * 
   * @param s the <code>String</code>
   * @return <code>true</code> if the string is an integer
   * @see #isPositiveInteger(java.lang.String)
   */
  public static boolean isInteger(String s) {
    if (ApiFunctions.isEmpty(s)) return false;

    try {
      Integer.parseInt(s);
    }
    catch (NumberFormatException e) {
      return false;
    }

    return true;
  }

  /**
   * Returns <code>true</code> if the string is an integer that is greater than or equal to zero;
   * <code>false</code> otherwise. If the input string is <code>null</code>, then
   * <code>false</code> is returned.
   * 
   * @param s the <code>String</code>
   * @return <code>true</code> if the string is a positive integer
   * @see #isInteger(java.lang.String)
   */
  public static boolean isPositiveInteger(String s) {
    if (ApiFunctions.isEmpty(s)) return false;

    try {
      int quantity = Integer.parseInt(s);
      if (quantity < 0) return false;
    }
    catch (NumberFormatException e) {
      return false;
    }

    return true;
  }

  /**
   * Returns <code>true</code> if the string is a double; <code>false</code> otherwise. If the
   * input string is <code>null</code>, then <code>false</code> is returned.
   * 
   * @param s the <code>String</code>
   * @return <code>true</code> if the string is an double
   */
  public static boolean isDouble(String s) {
    if (ApiFunctions.isEmpty(s)) return false;

    try {
      Double.parseDouble(s);
    }
    catch (NumberFormatException e) {
      return false;
    }

    return true;
  }

  
  /**
   * Returns <code>true</code> if the string is a float; <code>false</code> otherwise. If the
   * input string is <code>null</code>, then <code>false</code> is returned.
   * 
   * @param s the <code>String</code>
   * @return <code>true</code> if the string is an double
   */
  public static boolean isFloat(String s) {
    if (ApiFunctions.isEmpty(s)) return false;

    try {
      Float.parseFloat(s);
    }
    catch (NumberFormatException e) {
      return false;
    }

    return true;
  }  
  
  /**
   * Generic Query method to execute a query submitted from a JSP page, the input could be an
   * embedded SQL Statement, or a DB Stored Procedure Call.
   * 
   * @param PreparedStatement pstmt, Connection con
   * @return ResultSet
   */
  public static ResultSet queryDb(PreparedStatement pstmt, Connection con) {
    try {
      return pstmt.executeQuery();
    }
    catch (SQLException e) {
      throwAsRuntimeException(e);
      // Unreached, keep compiler happy.
      return null;
    }
  }

  public static String replace(String target, String from, String to) {
    // target is the original string
    // from is the string to be replaced
    // to is the string which will used to replace

    if (target == null) return null;

    int start = target.indexOf(from);
    if (start == -1) return target;
    int lf = from.length();
    char[] targetChars = target.toCharArray();
    StringBuffer buffer = new StringBuffer();
    int copyFrom = 0;

    while (start != -1) {
      buffer.append(targetChars, copyFrom, start - copyFrom);
      buffer.append(to);
      copyFrom = start + lf;
      start = target.indexOf(from, copyFrom);
    }

    buffer.append(targetChars, copyFrom, targetChars.length - copyFrom);
    return buffer.toString();
  }

  /**
   * Split the supplied text into an array of strings based on the specified separator characters.
   * Each character in <code>separators</code> is considered to be a separator character on its
   * own. If <code>separators</code> is null, all whitespace characters are treated as separators.
   * After spliiting the input text, each of the split strings has whitespace trimmed from it.
   * 
   * @param text the string to split
   * @param separators the separator characters
   * @return the array of strings resulting from the split. This will be a zero-element array when
   *         text == null.
   * 
   * @see #separateString(String, String)
   */
  public static String[] splitAndTrim(String text, String separators) {
    if (text == null) {
      return new String[0];
    }

    String[] strings = StringUtils.split(text, separators);

    for (int i = 0; i < strings.length; i++) {
      strings[i] = safeString(safeTrim(strings[i]));
    }

    return strings;
  }

  /**
   * Separate a string into an array of strings, splitting based on the specified separator string.
   * Unlink {@link #splitAndTrim(String, String)}, the separator parameter specifies a single
   * separator string, not a list of individual separator characters.
   * 
   * @param text the string to separate
   * @param separator occurrences of this string are where the string will be separated. It must be
   *          non-empty.
   * @return the array of separated strings. If the text parameter is either null or an empty
   *         string, the returned array will be a zero-length array.
   * 
   * @see #splitAndTrim(String, String)
   */
  public static String[] separateString(String text, String separator) {
    if (ApiFunctions.isEmpty(separator)) {
      throw new IllegalArgumentException("separator must not be null or empty");
    }

    if (ApiFunctions.isEmpty(text)) {
      return new String[0];
    }

    int i = 0;
    int j = 0;
    int sepLen = separator.length();
    int textLen = text.length();
    List pieces = new ArrayList();
    do {
      j = text.indexOf(separator, i);
      if (j < 0) {
        if (i >= textLen) {
          // This handles the case where the text ends with the separator.
          pieces.add("");
        }
        else {
          pieces.add(text.substring(i));
        }
      }
      else {
        pieces.add(text.substring(i, j));
        i = j + sepLen;
      }
    } while (j >= 0);

    return (String[]) pieces.toArray(new String[0]);
  }

  /**
   * Runs the specified query and returns the results as a {@link java.util.List}of data beans.
   * <p>
   * If the query has bind variables, then the params array must have a parameter to match each
   * variable, in the proper order. If the query does not have bind variables, then the params array
   * can either be an array of zero length or <code>null</code>. All variables are assumed to be
   * of type {@link java.lang.String}.
   * <p>
   * The dataBeanClass specifies the class of the data bean that is returned in the
   * {@link java.util.List}, and it must have a constructor that takes a {@link java.sql.ResultSet}
   * as an argument. An {@link ApiException}wrapping a {@link java.lang.NoSuchMethodException}is
   * thrown if the dataBeanClass does not have a constructor that takes a {@link java.sql.ResultSet}
   * argument.
   * 
   * @param query the query to be executed
   * @param params the array of parameters
   * @param dataBeanClass the data bean to create from each row of the result set
   * @return The <code>List</code> of data beans. If the query returned zero rows, then an empty
   *         list is returned, not <code>null</code>.
   */
  public static List runQuery(String query, String[] params, Class dataBeanClass) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List results = new ArrayList();

    try {
      // Prepare and execute the query.
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      if (params != null) {
        for (int i = 0; i < params.length; i++) {
          pstmt.setString(i + 1, params[i]);
        }
      }
    
      rs = ApiFunctions.queryDb(pstmt, con);

      // Get the constructor of the data bean class that takes
      // a ResultSet, and use it to instantiate a data bean
      // per row of the ResultSet.
      Class constructorArgs[] = { java.sql.ResultSet.class };
      Constructor constructor = dataBeanClass.getConstructor(constructorArgs);
      Object args[] = { rs };
      while (rs.next()) {       
        results.add(constructor.newInstance(args));
      }
    }
    catch (Exception e) {
      ApiLogger.log(e);
      throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return results;
  }

  /**
   * Runs the specified query and returns the results as a {@link java.util.List}of data beans.
   * <p>
   * If the query has bind variables, then the params array must have a parameter to match each
   * variable, in the proper order. If the query does not have bind variables, then the params array
   * can either be an array of zero length or <code>null</code>. All variables are assumed to be
   * of type {@link java.lang.String}.
   * <p>
   * The dataBeanClass specifies the class of the data bean that is returned in the
   * {@link java.util.List}, and it must have a constructor that takes a {@link java.sql.ResultSet}
   * and {@link java.util.HashMap}as arguments. An {@link ApiException}wrapping a
   * {@link java.lang.NoSuchMethodException}is thrown if the dataBeanClass does not have a
   * constructor that takes {@link java.sql.ResultSet}and {@link java.util.HashMap}arguments.
   * 
   * @param query the query to be executed
   * @param params the array of parameters
   * @param dataBeanClass the data bean to create from each row of the result set
   * @return The <code>List</code> of data beans. If the query returned zero rows, then an empty
   *         list is returned, not <code>null</code>.
   */
  public static List runQueryForMappedDataBean(String query, String[] params, Class dataBeanClass) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List results = new ArrayList();

    try {
      // Prepare and execute the query.
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      if (params != null) {
        for (int i = 0; i < params.length; i++) {
          pstmt.setString(i + 1, params[i]);
        }
      }
      rs = ApiFunctions.queryDb(pstmt, con);
      ResultSetMetaData meta = rs.getMetaData();
      int columnCount = meta.getColumnCount();
      HashMap lookup = new HashMap();

      for (int i = 0; i < columnCount; i++) {
        lookup.put(meta.getColumnName(i + 1).toLowerCase(), null);
      }

      // Get the constructor of the data bean class that takes
      // a Map and ResultSet, and use it to instantiate a data bean
      // per row of the ResultSet.
      Class constructorArgs[] = { java.util.Map.class, java.sql.ResultSet.class };
      Constructor constructor = dataBeanClass.getConstructor(constructorArgs);
      Object args[] = { lookup, rs };
      while (rs.next()) {
        results.add(constructor.newInstance(args));
      }
    }
    catch (Exception e) {
      ApiLogger.log(e);
      throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return results;
  }

  /**
   * Execute the query and return a List of results, where the results are from the first (and
   * probably only) data value in the result set. The prepared statement must have one formal
   * parameter, and must return the desired value as the first value in the result set. Sample
   * usage: runQueryForStrings( "Select justOneValue FROM myTable t where t.val LIKE ? ",
   * "someStringToMatchOn") will retun all the values for justOneValue such that t.val matches the
   * like string.
   * 
   * @param prepStatementString the SQL describing the prepared statement
   * @param param the actual parameter to bind to the prepared statement formal parameter
   * @returns a list of values corresponding to the first element in each row of the result set.
   */
  public static List runQueryForSingleValueList(String prepStatementString, String param) {
    List results = null;
    Connection con = getDbConnection();
    try {
      PreparedStatement ps = con.prepareStatement(prepStatementString);
      ps.setString(1, param);
      ResultSet rs = ps.executeQuery();
      results = new ArrayList();
      while (rs.next()) {
        String val = rs.getString(1);
        if (val != null) // singe query w/ no data can return null as value
            results.add(val);
      }
    }
    catch (SQLException e) {
      throwAsRuntimeException(e);
    }
    finally {
      try {
        con.close();
      }
      catch (SQLException e) {
        throwAsRuntimeException(e);
      }
    }
    return results;
  }

  /**
   * Converts all whitespace to single spaces. E.g. "the quick brown \n\n fox." will be converted to
   * "the quick bronw fox."
   * 
   * @param s the string to convert
   * @returns a new string with only single spaces as whitespace.
   */
  public static String removeExtraWhitespace(String s) {
    String whiteSpace = " " + '\t' + '\r' + '\n';
    StringTokenizer st = new StringTokenizer(s, whiteSpace);
    if (!st.hasMoreTokens()) return ""; // no tokens? all blank
    StringBuffer result = new StringBuffer(s.length());
    while (st.hasMoreTokens()) {
      result.append(st.nextToken());
      result.append(' ');
    }
    result.deleteCharAt(result.length() - 1); // remove last space
    return result.toString();
  }

  /**
   * Returns true if the specified object are equal, otherwise false. If obj1 is null then this
   * returns true only if obj2 is also null. Otherwise this returns obj1.equals(obj2).
   * 
   * @param obj1 the first object
   * @param obj2 the second object
   * 
   * @return true if the objects are equal or both are null.
   */
  public static boolean safeEquals(Object obj1, Object obj2) {
    if (obj1 == null) {
      return (obj2 == null);
    }
    else {
      return obj1.equals(obj2);
    }
  }

  /**
   * Returns an integer constructed from the given string. If the string is empty then the specified
   * default value is returned.
   * 
   * @param s the string to convert to an integer
   * @param defaultValue the value to return if the string is empty
   * @return if the string is empty return <code>defaultValue</code> otherwise return the integer
   *         obtained by parsing the string.
   */
  public static int safeInteger(String s, int defaultValue) {
    Integer i = safeInteger(s);
    return (i == null) ? defaultValue : i.intValue();
  }

  /**
   * Returns an Integer constructed from the given string. The string is first trimmed. If the
   * string is empty or cannot be successfully converted to an integer then null is returned.
   * 
   * @param s the string to convert to an Integer
   * @return if the string is empty return <code>null</code> otherwise return the Integer obtained
   *         by parsing the string.
   */
  public static Integer safeInteger(String s) {
    if ((s == null) || (s.trim().length() == 0)) {
      return null;
    }
    else {
      try {
        return new Integer(s);
      }
      catch (NumberFormatException e) {
        return null;
      }
    }
  }

  /**
   * Returns a BigDecimal constructed from the given string. The string is first trimmed. If the
   * string is empty or cannot be successfully converted to an integer then null is returned.
   * 
   * @param s the string to convert to BigDecimal
   * @return if the string is empty return <code>null</code> otherwise return the BigDecimal
   *         obtained by parsing the string.
   */
  public static BigDecimal safeBigDecimal(String s) {
    if ((s == null) || (s.trim().length() == 0) ) {
  
      return null;
    }
    else {
      try {
        return new BigDecimal(s);
      }
      catch (NumberFormatException e) {
        return null;
      }
      catch (StringIndexOutOfBoundsException e){
        return null;
      }
    }
  }

  /**
   * Returns a java.sql.Date constructed from the given string. The string is first trimmed. If the
   * string is empty or cannot be successfully converted to a Date then null is returned.
   * 
   * @param s the string to convert to a Date
   * @return if the string is empty return <code>null</code> otherwise return the Date obtained by
   *         parsing the string.
   */
  public static Date safeDate(String s) {
    try {
      return (Date) BigrConvertUtilsBean.SINGLETON.convert(s, Date.class);
    }
    catch (ConversionException e) {
      return null;
    }
  }

  /**
   * Returns string representation of date in mm/dd/yyyy format from the given java.sql.Date date.
   * If the date is empty then null is returned.
   * 
   * @param date the Date to convert to a String
   * @return if the date is null return <code>null</code> otherwise return string from date in
   *         mm/dd/yyyy format.
   */
  public static String sqlDateToString(java.sql.Date date) {
    if (date == null) return null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    return dateFormat.format(date);
  }

  /**
   * Returns string representation of timestamp in mm/dd/yyyy h:mm am/pm format from the given 
   * timestamp. If the timestamp is empty then null is returned.
   * 
   * @param timestamp the timestamp to convert to a String
   * @return if the timestamp is null return <code>null</code> otherwise return string from date in
   *         mm/dd/yyyy h:mm am/pm format.
   */
  public static String sqlTimestampToString(Timestamp timestamp) {
    if (timestamp == null) return null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
    return dateFormat.format(timestamp);
  }

  /**
   * Returns a non-null string representing a given string.
   * 
   * @param s the string
   * @return {@link #EMPTY_STRING EMPTY_STRING}if <code>s</code> is <code>null</code>,
   *         otherwise <code>s</code>.
   */
  public static String safeString(String s) {
    return (s == null) ? EMPTY_STRING : s;
  }

  /**
   * If s a null, return null. Otherwise, modify the input array to have each of its elements x
   * replaced by safeString(x). Note that this modifies the input array.
   * 
   * @param s The input string array.
   * @return The modified array.
   */
  public static String[] safeString(String[] s) {
    if (s == null) {
      return null;
    }
    else {
      for (int i = 0; i < s.length; i++) {
        s[i] = safeString(s[i]);
      }
    }
    return s;
  }

  /**
   * Return the input argument if it is non-null, otherwise return an empty string array.
   * 
   * @param s The input array.
   * @return The non-null array.
   */
  public static String[] safeStringArray(String[] s) {
    return ((s == null) ? EMPTY_STRING_ARRAY : s);
  }

  /**
   * Returns the length of the specified string, or zero if the string is null.
   * 
   * @param s the string
   * @return zero if <code>s</code> is <code>null</code>, otherwise <code>s.length()</code>.
   */
  public static int safeStringLength(String s) {
    return (s == null) ? 0 : s.length();
  }

  /**
   * Returns a string representing the specified object.
   * 
   * @param o the object to convert to a string
   * @return <code>o.toString()</code> if <code>o</code> is not <code>null</code>, otherwise
   *         the empty string.
   */
  public static String safeToString(Object o) {
    return (o == null) ? EMPTY_STRING : o.toString();
  }

  /**
   * Returns a trimmed version of all of the input <code>String</code>s. If the input
   * <code>String</code> array is <code>null</code>, then <code>null</code> is returned.
   * 
   * @param s the array of <code>String</code> s
   * @return A trimmed version of the <code>String</code>s, or <code>null</code>.
   */
  public static String[] safeTrim(String[] s) {
    if (s == null) {
      return null;
    }
    else {
      for (int i = 0; i < s.length; i++) {
        s[i] = safeTrim(s[i]);
      }
    }
    return s;
  }

  /**
   * Returns a trimmed version of the input <code>String</code>. If the input <code>String</code>
   * is <code>null</code>, then <code>null</code> is returned.
   * 
   * @param s the <code>String</code>
   * @return A trimmed version of the <code>String</code>, or <code>null</code>.
   */
  public static String safeTrim(String s) {
    return (s == null) ? s : s.trim();
  }

  /**
   * Truncates the string representation of a decimal number to the specified number of decimal
   * places. The method simply truncates and does not perform rounding.
   * 
   * @param s the string to truncate
   * @param numberDecimalPlaces the number of decimal places to the right of the decimal
   * @return The truncated string. Returns null if the input string is null, returns the empty
   *         string if the input string is the empty string, and returns the input string unaltered
   *         if its number of decimal places is less than or equal to the specified number of
   *         decimal places.
   */
  public static String safeTruncate(String s, int numberDecimalPlaces) {
    if (s == null) {
      return null;
    }

    String truncated = s;
    if (!ApiFunctions.isEmpty(s)) {
      int decPoint = s.indexOf(".");
      if (decPoint > 0 && (s.length() > (decPoint + numberDecimalPlaces + 1))) {
        truncated = s.substring(0, decPoint + numberDecimalPlaces + 1);
      }
    }

    return truncated;
  }

  /**
   * Convert an arbitrary exception to an exception that contains the same information but is a
   * {@link RuntimeException}instead, and then throw the resulting exception. If the exception is
   * already an {@link ApiException}, this simply throws it. Otherwise this throws an
   * {@link ApiException}that wraps <code>e</code>. We wrap the original exception with an
   * ApiException even if it is already a RuntimeException because ApiException preserves stack
   * trace information about the wrapped exception when an exception is passed from the server side
   * of an EJB call to the client side. Other exceptions normally lose their server-side stack trace
   * information in this situation, and that information can be very helpful in diagnosing problems.
   * 
   * @param e the exception to convert
   */
  public static void throwAsRuntimeException(Exception e) {
    throw convertToRuntimeException(e);
  }

  /**
   * Given a java.sql.Timestamp, return the milliseconds value (as returned by Date.getTime()) with
   * more precision than seconds. The Timestamp class defines its getTime method to only return
   * values in 1-second increments, and says that you need to add in a value based on its getNanos
   * method if you want millisecond precision. This method does that addition.
   * 
   * @param timestamp the timestamp
   * @return the time as a milliseconds value
   */
  public static long timestampToMillis(Timestamp timestamp) {
    return timestamp.getTime() + (timestamp.getNanos() / 1000000);
  }

  /**
   * Run time process to call external command to print lable(s). Synchronization is required for
   * the printing.
   */
  private static Object getPrinterLockObject(String printerName) {
    if (printerName == null) {
      throw new IllegalArgumentException(
          "PrintSlideCommand.getPrinterLockObject: the printer name must not be null.");
    }

    Object lock = _printerLocks.get(printerName);

    if (lock == null) {
      throw new IllegalArgumentException(
          "PrintSlideCommand.getPrinterLockObject: Unknown printer '" + printerName + "'.");
    }

    return lock;
  }

  /**
   * Print a list of labels to a single printer, returning a message confirming the result or
   * containing any errors detected. Do nothing if labelDescriptions is empty or null.
   */
  public static String printLabels(String printerName, String fileNamePrefix,
                                   List labelDescriptions, String labelType) throws IOException,
      InterruptedException {

    if (labelDescriptions == null || labelDescriptions.size() == 0) {
      return ApiFunctions.EMPTY_STRING;
    }

    // Everything else in this procedure should be in the synchronized block, to ensure
    // no bad behavior if two users try to print to the same printer at once.
    //
    synchronized (getPrinterLockObject(printerName)) {
      boolean fail = false;

      StringBuffer spoolFileName = new StringBuffer(128);
      Random rand = new Random();
      int raNum = rand.nextInt(10000);

      //Generate unique file name
      spoolFileName.append(fileNamePrefix);
      spoolFileName.append(printerName);
      spoolFileName.append("_");
      spoolFileName.append(System.currentTimeMillis());
      spoolFileName.append("_");
      spoolFileName.append(raNum);
      spoolFileName.append(".txt");
      Writer fout = new BufferedWriter(new FileWriter(spoolFileName.toString()));

      String cmd = "lp -d " + printerName + " " + spoolFileName;

      if (labelType.equalsIgnoreCase(SAMPLE_LABEL_TYPE)) {
        // The following is an example of what should be sent to the printer:
        // "^XA"
        // "^PRC"
        // "^LH0,0^FS"
        // "^LL150"
        // "^MD0"
        // "^MMT"
        // "^MNY"
        // "^LH200,70^FS"
        // "^F022,106^A0N,89,112^CI13^FR^FDAP2^FS"
        // "^LH30,20^FS"
        // "^BY4,3.0^FO670,6^CVY^B7N,9,3,4,8,N^FR^FDPA0000215C^FS"
        // "^FO750,90^A0N,55,80^CI13^FR^FDPA0000215C^FS"
        // "^PQ1,0,0,N"
        // "^XZ"
        for (int i = 0; i < labelDescriptions.size(); i++) {
          Map labelInfo = (Map) labelDescriptions.get(i);

          // Get the small label, for frozen it's the last 4 digits of the sample id.
          // For paraffin it's the ASM position.
          String smallLabel = (String) labelInfo.get(LABEL_INFO_SMALL_LABEL);
          String sampleId = (String) labelInfo.get(LABEL_INFO_SAMPLE_ID);

          fout.write("^XA\n");
          fout.write("^PRC\n");
          fout.write("^LH0,0^FS\n");
          fout.write("^LL150\n");
          fout.write("^MD0\n");
          fout.write("^MMT\n");
          fout.write("^MNY\n");
          fout.write("^LH200,70^FS\n");
          fout.write("^F022,106^A0N,89,112^CI13^FR^FD" + smallLabel + "^FS\n");
          fout.write("^LH30,20^FS\n");
          fout.write("^BY4,3.0^FO670,6^CVY^B7N,9,3,4,8,N^FR^FD" + sampleId + "^FS\n");
          fout.write("^FO750,90^A0N,55,80^CI13^FR^FD" + sampleId + "^FS\n");
          fout.write("^PQ1,0,0,N\n");
          fout.write("^XZ\n");
        }
      }
      else if (labelType.equalsIgnoreCase(SLIDE_LABEL_TYPE)) {
        for (int i = 0; i < labelDescriptions.size(); i++) {
          Map labelInfo = (Map) labelDescriptions.get(i);
          String label = ApiFunctions.safeString((String) labelInfo.get("slide"));
          String barcode = ApiFunctions.safeString((String) labelInfo.get("block"));
          String caseid = ApiFunctions.safeString((String) labelInfo.get("caseid"));
          String cutnumber = ApiFunctions.safeString(labelInfo.get("slideNumber").toString());
          String asmPosition = ApiFunctions.safeString((String) labelInfo.get("asmPos"));

          //Mario, 3/25/2002: MR2991: Changed all zpl settings; removed cutproc, level

          String caseChr;
          String caseFlag;
          String caseRemainder;
          if (!ApiFunctions.isEmpty(caseid)) {
            caseChr = caseid.substring(0, 1);
            caseFlag = caseid.substring(1, 2);
            caseRemainder = caseid.substring(2);
          }
          else {
            caseChr = ApiFunctions.EMPTY_STRING;
            caseFlag = ApiFunctions.EMPTY_STRING;
            caseRemainder = ApiFunctions.EMPTY_STRING;
          }

          fout.write("^XA\n");
          fout.write("^LH60,95\n");
          fout.write("^BY3,,80\n");
          if (caseFlag.equals("I")) {
            fout.write("^FWB^FO100,460^AV^FD" + caseChr + "^FS\n");
            fout.write("^FWB^FO100,415^AV^FD" + caseFlag + "^FS\n");
          }
          else {
            fout.write("^FWB^FO100,465^AV^FD" + caseChr + "^FS\n");
            fout.write("^FWB^FO100,410^AV^FD" + caseFlag + "^FS\n");
          }
          fout.write("^FWB^FO100,60^AV^FD" + caseRemainder + "^FS\n");
          //fout.write("^FO180,60^BC,Y,N^FD" + label + "^FS\n"); Mario: 1D version
          //fout.write("^FO180,100^B7,8,2,1,15,N^FR^FD" + label + "^FS\n"); Mario: original 2D
          // version
          //fout.write("^FO180,60^CVY^B7,10,2,4,12,N^FR^FD" + label + "^FS\n"); Mario: last working
          // 2D version
          fout.write("^FO180,60^BC,Y,N^FD" + label + "^FS\n");
          fout.write("^FO260,100^AV^FD" + label + "^FS\n");
          fout.write("^FO325,100^AV^FD" + barcode + "^FS\n");
          fout.write("^FO390,400^AV^FD" + asmPosition + "^FS\n");
          fout.write("^FO390,60^AV^FDS" + cutnumber + "^FS\n");
          fout.write("^XZ\n");
        }
      }
      fout.flush();
      fout.close();

      {
        Process p = Runtime.getRuntime().exec(cmd);
        int exitValue = p.waitFor();
        //Check exit value for the process. 0 indicates normal termination.
        if (exitValue != 0) {
          fail = true;
        }

        p.destroy();
      }

      if (fail) {
        return LABELPRINTING_FAIL;
      }
      else {
        return LABELPRINTING_PASS;
      }
    } // end synchronized block
  }

  /**
   * Print a list of slide labels to a single printer, returning a message confirming the result or
   * containing any errors detected. Do nothing if labelDescriptions is empty or null.
   */
  public static String printUserLabel(String userName) throws IOException, InterruptedException {

    if (userName == null) {
      return ApiFunctions.EMPTY_STRING;
    }

    // Everything else in this procedure should be in the synchronized block, to ensure
    // no bad behavior if two users try to print to the same printer at once.
    //
    synchronized (getPrinterLockObject(FROZEN_SLIDELABEL_PRINTER)) {
      boolean fail = false;
      Random rand = new Random();
      int raNum = rand.nextInt(10000);
      //Generate unique file name.
      StringBuffer spoolFileName = new StringBuffer(128);
      spoolFileName.append(ApiResources.getResourcePath("limsreport/user_label_"));
      spoolFileName.append(System.currentTimeMillis());
      spoolFileName.append("_");
      spoolFileName.append(raNum);
      spoolFileName.append(".txt");
      Writer fout = new BufferedWriter(new FileWriter(spoolFileName.toString()));
      //Create print command.
      String cmd = "lp -d " + FROZEN_SLIDELABEL_PRINTER + " " + spoolFileName;

      fout.write("^XA\n");
      fout.write("^LH70,70\n");
      fout.write("^BY3,,150\n");
      fout.write("^FWB^FO120,70^AV^FD" + userName + "^FS\n");
      fout.write("^FO220,70^BC,,N,,^FD" + userName + "^FS\n");
      fout.write("^XZ\n");

      fout.flush();
      fout.close();

      {
        Process p = Runtime.getRuntime().exec(cmd);
        int exitValue = p.waitFor();
        //Check exit value for the process. 0 indicates normal termination.
        if (exitValue != 0) {
          fail = true;
        }

        p.destroy();
      }

      if (fail) {
        return LABELPRINTING_FAIL;
      }
      else {
        return LABELPRINTING_PASS;
      }
    } // end synchronized block
  }

  /**
   * Print a list of slide labels, sending all paraffin labels to one printer and all frozen labels
   * to another.
   */
  public static String printLabels(List labelDescriptions) throws IOException, InterruptedException {

    List frozenLabels = new ArrayList();
    List paraffinLabels = new ArrayList();

    // Send to different printers according to the sample format
    // Make two lists of labels, one for frozen samples and the other paraffin.
    //
    Iterator iter = labelDescriptions.iterator();
    while (iter.hasNext()) {
      Map labelInfo = (Map) iter.next();
      String label = (String) labelInfo.get("slide");

      if (label.startsWith("SF")) {
        frozenLabels.add(labelInfo);
      }
      else {
        paraffinLabels.add(labelInfo);
      }
    }

    String message = printLabels(FROZEN_SLIDELABEL_PRINTER, SLIDE_FILENAME_PREFIX, frozenLabels,
        SLIDE_LABEL_TYPE);

    message += printLabels(PARAFFIN_SLIDELABEL_PRINTER, SLIDE_FILENAME_PREFIX, paraffinLabels,
        SLIDE_LABEL_TYPE);

    return message;
  }

  public static int occurrencesOf(char c, String s) {
    if (isEmpty(s)) {
      return 0;
    }

    int numOccurrences = 0;
    for (int index = s.indexOf(c); index > 0; index = s.indexOf(c, index + 1)) {
      numOccurrences++;
    }
    return numOccurrences;
  }

  /**
   * Returns true if the specified collection contains the same element more than once. This is more
   * efficient than calling {@link #duplicateSet(Collection) duplicateSet}and checking to see
   * whether the returned set is empty.
   * 
   * @param coll the collection
   * @return true if there are duplicates
   */
  public static boolean hasDuplicates(Collection coll) {
    if (coll instanceof Set) {
      // sets never contain duplicates
      return false;
    }

    // Otherwise put all of the collection elements into a set. If the
    // size of the set is different from the size of the original
    // collection, there are duplicates.
    //
    return (coll.size() != (new HashSet(coll)).size());
  }

  /**
   * Returns the set of items from the specified collection that are duplicates (they appear more
   * than once in the collection). If you just want to determine whether a collection has
   * duplicates, it is more efficient to use {@link #hasDuplicates(Collection) hasDuplicates}
   * instead.
   * 
   * @param coll the collection
   * @return the set of duplicate items
   */
  public static Set duplicateSet(Collection coll) {
    Set duplicates = new HashSet();

    if (!(coll instanceof Set)) { // sets never contain duplicates
      Map cardinalityMap = CollectionUtils.getCardinalityMap(coll);

      Iterator iter = cardinalityMap.entrySet().iterator();
      while (iter.hasNext()) {
        Map.Entry entry = (Map.Entry) iter.next();
        int cardinality = ((Integer) entry.getValue()).intValue();
        if (cardinality > 1) {
          duplicates.add(entry.getKey());
        }
      }
    }

    return duplicates;
  }

  /**
   * Chunks the array of strings and returns a List of chunks.
   * 
   * @param strings the array of strings to chunk
   * @return A List of arrays of strings, each of which is of size chunkSize except for (most
   *         likely) the last array. If the input array is null or empty, then an empty list is
   *         returned.
   */
  public static List chunkStrings(String[] strings, int chunkSize) {

    List stringChunks = new ArrayList();
    if (!ApiFunctions.isEmpty(strings)) {
      int numStrings = strings.length;
      int chunkIndex = 0;
      String[] stringChunk = null;

      for (int i = 0; i < numStrings; i++) {
        String s = strings[i];

        // If this is the very first string or we've reached the chunk size,
        // then add the current chunk to the list of chunks, and create
        // a new chunk of the correct size. We'll always create a chunk
        // size array unless we don't have that many strings left, in which
        // case we'll create an array sized to the number of strings left.
        if ((chunkIndex == 0) || (chunkIndex >= chunkSize)) {
          if (stringChunk != null) {
            stringChunks.add(stringChunk);
          }
          int samplesLeft = numStrings - i;
          stringChunk = (samplesLeft < chunkSize) ? new String[samplesLeft] : new String[chunkSize];
          chunkIndex = 0;
        }

        // Add the string to the current chunk.
        stringChunk[chunkIndex++] = s;
      }

      // Add the last chunk to the list of chunks.
      if (stringChunk != null) {
        stringChunks.add(stringChunk);
      }
    }

    return stringChunks;
  }

  /**
   * Concatenate the two arrays into one array.
   * 
   * @param ss1 the first array
   * @param ss2 the second array
   * @return an array containing the elements of ss1 followed by the element of ss2.
   */
  public static String[] concatenate(String[] ss1, String[] ss2) {
    int len1 = ss1.length;
    int len2 = ss2.length;
    String[] all = new String[len1 + len2];
    System.arraycopy(ss1, 0, all, 0, len1);
    System.arraycopy(ss2, 0, all, len1, len2);
    return all;
  }

  /**
   * Similar to Set.removeAll, but operates on String arrays. Copy an array and remove certain
   * elements. Also remove duplicates. Does not preserve order.
   * 
   * @param all the overall array to start with
   * @param remove the strings to remove
   * @returns the unique strings in
   * @param all, other than strings that appear in
   * @param remove.
   */
  public static String[] copyExcept(String[] all, String[] remove) {
    Set keep = new HashSet(Arrays.asList(all));
    Set discard = new HashSet(Arrays.asList(remove));
    keep.removeAll(discard);
    return (String[]) keep.toArray(new String[keep.size()]);
  }

  /**
   * Given a fully-qualified class name, return the name of the class with the package part stripped
   * off.
   * 
   * @return the short class name
   */
  public static String shortClassName(String className) {
    if (className == null) {
      throw new IllegalArgumentException("className must not be null");
    }

    int i = className.lastIndexOf('.');
    if (i < 0) {
      return className;
    }
    return className.substring(i + 1);
  }

  public static boolean safeArrayContains(String[] array, String s) {
    return ((array == null) || (s == null)) ? false : arrayContains(array, s);
  }

  public static boolean arrayContains(String[] array, String s) {
    for (int i = 0; i < array.length; i++) {
      if (s.equals(array[i])) {
        return true;
      }
    }
    return false;
  }

  public static String longStringAsPopup(String message, int len) {
    StringBuffer result = new StringBuffer(128);

    if (message != null) {
      if (message.length() > len) {
        result.append("<td title=\"");
        result.append((Escaper.htmlEscape(message)) + "\">");
        result.append((Escaper.htmlEscape(message.substring(0, len - 1))));
        result.append("...");
        result.append("</td>");
      }
      else {
        result.append("<td>");
        result.append(Escaper.htmlEscape(message));
        result.append("</td>");
      }
    }
    return result.toString();

  }

  public static String longStringAsPopup(String message, int len, int rowspan) {
    StringBuffer result = new StringBuffer(128);

    if (message != null) {
      if (message.length() > len) {
        result.append("<td rowspan = \"" + rowspan + "\" + title=\"");
        result.append((Escaper.htmlEscape(message)) + "\">");
        result.append((Escaper.htmlEscape(message.substring(0, len - 1))));
        result.append("...");
        result.append("</td>");
      }
      else {
        result.append("<td rowspan = \"" + rowspan + "\">");
        result.append(Escaper.htmlEscape(message));
        result.append("</td>");
      }
    }
    return result.toString();

  }

  /**
   * Ensure that the named directory path exists, creating new directories if necessary.
   * 
   * @param directoryPath the directory path
   */
  public static void ensureDirectoryPathExists(String directoryPath) {
    ensureDirectoryPathExists(new File(directoryPath));
  }

  /**
   * Ensure that the supplied directory path exists, creating new directories if necessary.
   * 
   * @param dir the directory path
   */
  public static void ensureDirectoryPathExists(File dir) {
    if (!dir.exists()) {
      boolean succeeded = dir.mkdirs();
      if (!succeeded) {
        throw new ApiException("Failed to create directory path: " + dir.getAbsolutePath());
      }
    }
  }

  /**
   * Given a directory path prefix (which may be null) and a Java package name, return the directory
   * beneath the prefix directory in which a file in the specified package would exist.
   * 
   * @param pathPrefix The directory path prefix.
   * @param packageName The Java package name.
   * @return the appropriate directory for the package.
   */
  public static File makePackagePath(String pathPrefix, String packageName) {
    String packagePathPart = packageName.replace('.', File.separatorChar);

    if (ApiFunctions.isEmpty(pathPrefix)) {
      return new File(packagePathPart);
    }
    else {
      return new File(pathPrefix, packagePathPart);
    }
  }

  /**
   * Given a directory path prefix (which may be null) and a Java package name, return the directory
   * beneath the prefix directory in which a file in the specified package would exist.
   * 
   * @param pathPrefix The directory path prefix.
   * @param packageName The Java package name.
   * @return the appropriate directory for the package.
   */
  public static File makePackagePath(File pathPrefix, String packageName) {
    return makePackagePath(((pathPrefix == null) ? (String) null : pathPrefix.getPath()),
        packageName);
  }

  /**
   * Returns the <code>Collection</code> as an array of strings. Each object in the collection
   * will be converted to a String by calling its toString method.
   * 
   * @param c the <code>Collection</code>
   * @return An array of String. If the input <code>Collection</code> is null, then null is
   *         returned.
   */
  public static String[] toStringArray(Collection c) {
    if (c == null) {
      return null;
    }

    String[] array = new String[c.size()];
    Iterator iter = c.iterator();
    for (int i = 0; iter.hasNext(); i++) {
      Object o = (Object) iter.next();
      array[i] = (o == null) ? null : o.toString();
    }
    return array;
  }

  /**
   * Returns the <code>Iterator</code> as an array of strings by calling the toString() method of
   * each object in the <code>Iterator</code>.
   * 
   * @param i the <code>Iterator</code>
   * @return An array of String. If the input <code>Iterator</code> is null, then null is
   *         returned.
   */
  public static String[] toStringArray(Iterator i) {
    if (i == null) {
      return null;
    }
    else {
      List l = new ArrayList();
      while (i.hasNext()) {
        Object o = i.next();
        if (o == null) {
          l.add(null);
        }
        else {
          String s = o.toString();
          l.add(s);
        }
      }
      return toStringArray(l);
    }
  }

  /**
   * Returns the <code>Collection</code> as an array of an array of strings. This simply calls the
   * <code>toArray</code> method of <code>Collection</code>, but takes care of the awkward
   * syntax involved in passing in an empty array and casting the result.
   * 
   * @param c the <code>Collection</code>
   * @return An array of array of String. If the input <code>Collection</code> is null, then null
   *         is returned.
   */
  public static String[][] toStringArrayArray(Collection c) {
    return (c == null) ? null : (String[][]) c.toArray(EMPTY_STRING_ARRAY_ARRAY);
  }

  /**
   * Returns the array of strings as a comma-separated string. Each entry in the array is separated
   * by a comma with no extra whitespace.
   * 
   * @param s the array of strings
   * @return A comma-separated list of array entries as a String. If the input is null, then null is
   *         returned. If the input is an empty array, then the empty string is returned.
   */
  public static String toCommaSeparatedList(String[] s) {
    if (s == null) {
      return null;
    }
    else if (s.length == 0) {
      return EMPTY_STRING;
    }
    else {
      StringBuffer buf = new StringBuffer();
      for (int i = 0; i < s.length; i++) {
        if (i > 0) {
          buf.append(',');
        }
        buf.append(s[i]);
      }
      return buf.toString();
    }
  }

  /**
   * Return a string that is meant to follow the "IN" keyword in a SQL where clause. This returns a
   * string of the form "(?,?,?,...)" where the number of question marks to include is specified by
   * the numParameters argument.
   * 
   * @param numParameters the number of question marks, must be greater than zero
   * @return the query string fragment
   * 
   * @see #bindBindParameterList(PreparedStatement, int, Collection)
   */
  public static String makeBindParameterList(int numParameters) {
    if (numParameters <= 0) {
      throw new IllegalArgumentException("The numParameters argument must be > 0: " + numParameters);
    }

    StringBuffer sb = new StringBuffer();
    sb.append('(');
    boolean commaNeeded = false;
    for (int i = 0; i < numParameters; i++) {
      if (commaNeeded) {
        sb.append(',');
      }
      else {
        commaNeeded = true;
      }
      sb.append('?');
    }
    sb.append(')');

    return sb.toString();
  }

  /**
   * Return a string that is meant to be used in a SQL where clause to test if a given string column
   * is equal to one of a list of strings.
   * 
   * <p>
   * Let's call the value of the <code>compareTo</code> parameter "x". This returns a string of
   * the form "((x = ?) or (x = ?) or ...)" where the number of question marks to include is
   * specified by the <code>numParameters</code> argument.
   * 
   * @param compareTo The string to compare to. This will normally be a table column identifier, for
   *          example "sample.sample_barcode_id".
   * @param numParameters The number of question marks, must be greater than zero.
   * @return The query string fragment.
   * 
   * @see #bindBindParameterList(PreparedStatement, int, List)
   */
  public static String makeBindConditionStringOrList(String compareTo, int numParameters) {
    if (numParameters <= 0) {
      throw new IllegalArgumentException("The numParameters argument must be > 0: " + numParameters);
    }

    StringBuffer sb = new StringBuffer(512);
    sb.append('(');
    boolean orNeeded = false;
    for (int i = 0; i < numParameters; i++) {
      if (orNeeded) {
        sb.append(" or ");
      }
      else {
        orNeeded = true;
      }
      sb.append('(');
      sb.append(compareTo);
      sb.append(" = ?)");
    }
    sb.append(')');

    return sb.toString();
  }

  /**
   * This binds a list of strings to a consecutive set of PreparedStatement bind parameters using
   * <code>setString</code>. The position parameters passed to setString will start with
   * <code>firstIndex</code>. (The index is an index into the PreparedStatement's bind
   * parameters, not an index into the list of strings.)
   * 
   * @param pstmt the prepared statement
   * @param firstIndex the starting index into the prepared statement's bind parameters
   * @param strings the list of strings
   * 
   * @see #makeBindParameterList(int)
   */
  public static void bindBindParameterList(PreparedStatement pstmt, int firstIndex, Collection strings) {
    try {
      int i = firstIndex;
      Iterator iter = strings.iterator();
      while (iter.hasNext()) {
        String s = (String) iter.next();
        pstmt.setString(i, s);
        i++;
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * Given a list, return the list if it is non-null, otherwise return Collections.EMPTY_LIST.
   * 
   * @param c The list.
   * @return The non-null list.
   */
  public static List safeList(List c) {
    return ((c == null) ? Collections.EMPTY_LIST : c);
  }

  /**
   * Given a string array, return the list if it is non-null, otherwise return
   * Collections.EMPTY_LIST.
   * 
   * @param c The String[].
   * @return The non-null list.
   */
  public static List safeToList(String[] c) {
    return ((c == null) ? Collections.EMPTY_LIST : new ArrayList(Arrays.asList(c)));
  }

  /**
   * Given a map, return the map if it is non-null, otherwise return Collections.EMPTY_MAP.
   * 
   * @param c The map.
   * @return The non-null map.
   */
  public static Map safeMap(Map c) {
    return ((c == null) ? Collections.EMPTY_MAP : c);
  }

  /**
   * Given a set, return the set if it is non-null, otherwise return Collections.EMPTY_SET.
   * 
   * @param c The set.
   * @return The non-null set.
   */
  public static Set safeSet(Set c) {
    return ((c == null) ? Collections.EMPTY_SET : c);
  }

  /**
   * Given an array of objects, return it as a set if it is non-null, otherwise return
   * Collections.EMPTY_SET.
   * 
   * @param array the array
   * @return The non-null set.
   */
  public static Set safeSet(Object[] array) {
    if (array == null) {
      return Collections.EMPTY_SET;
    }
    else {
      Set set = new HashSet();
      for (int i = 0; i < array.length; i++) {
        set.add(array[i]);
      }
      return set;
    }
  }

  /**
   * @param s
   * @return
   */
  public static String stripRepeatFragmentMarkers(String s) {
    s = ApiFunctions.replace(s, REPEAT_FRAGMENT_MARKER_BEGIN, "");
    s = ApiFunctions.replace(s, REPEAT_FRAGMENT_MARKER_END, "");
    return s;
  }

  /**
   * If string contains repeating segment surrounded by [% and %], an array will be created with
   * first element as the string before the repeat segment, second element is the repeat segment
   * without the repeat symbols and last element as the string after the repeat segment.
   * 
   * @param s String to parse
   * @return Single element array if there is no repeat segment, otherwise three element array.
   */
  public static String[] separateRepeatFragmentParts(String s) {
    String[] result = new String[] { "", "", "" };

    if (!ApiFunctions.isEmpty(s)) {
      int beginPos = s.indexOf(REPEAT_FRAGMENT_MARKER_BEGIN);
      if (beginPos >= 0) {
        int endPos = s.indexOf(REPEAT_FRAGMENT_MARKER_END, beginPos);
        if (endPos < 0) {
          throw new ApiException("Missing ending repeat marker in '" + s + "'.");
        }
        int beginMarkerLength = REPEAT_FRAGMENT_MARKER_BEGIN.length();
        int endMarkerLength = REPEAT_FRAGMENT_MARKER_END.length();
        result[0] = ApiFunctions.safeString(s.substring(0, beginPos));
        result[1] = ApiFunctions.safeString(s.substring(beginPos + beginMarkerLength, endPos));
        result[2] = ApiFunctions.safeString(s.substring(endPos + endMarkerLength));
      }
      else { // no repeat marker
        result[1] = s;
      }
    }

    return result;
  }

  /**
   * Return a string stripping off all characters except alphanumeric characters.
   * 
   * @param s String to parse
   * @return String with alphanumeric characters only.
   */
  public static String makeOnlyAlphaNumeric(String s) {
    String result = "";

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (Character.isDigit(c) || Character.isLetter(c)) {
        result += c;
      }
    }
    return result;
  }

  /**
   * @param s The string to check
   * @return true if a string has repeat segment in it.
   */
  public static boolean hasRepeatSegment(String s) {
    int startIndex = s.indexOf(REPEAT_FRAGMENT_MARKER_BEGIN);
    int endIndex = s.indexOf(REPEAT_FRAGMENT_MARKER_END);
    return ((startIndex != endIndex) && (endIndex > startIndex));
  }

  public static long iteratorSize(Iterator iter) {
    long count = 0;
    if (iter != null) {
      while (iter.hasNext()) {
        iter.next();
        count++;
      }
    }
    return count;
  }

  public static String capitalize(String s) {
    if (ApiFunctions.isEmpty(s)) {
      return s;
    }
    else {
      return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
  }
  public static BigDecimal trimTrailingZeros(BigDecimal decimal){
    StringBuffer buffer = new StringBuffer(decimal.toString());
    int decimalIndex = buffer.indexOf(".");
    while ( buffer.length() > 0 && decimalIndex > 0){
      if (buffer.charAt(buffer.length()-1) == '0'){
        buffer.deleteCharAt(buffer.length()-1);
      }
      else return new BigDecimal(buffer.toString());
    }
    return decimal;
  }
  
  public static String padLeft(String stringToPad, char padChar, int length) {
    if (stringToPad == null) {
      return null;
    }
    
    int numPaddingChars = length - stringToPad.length();
    if (numPaddingChars <= 0) {
      return stringToPad;
    }
    
    char[] newChars = new char[length];
    int i = 0;
    while (i < numPaddingChars) {
      newChars[i++] = padChar;
    }
    
    char[] stringToPadChars = stringToPad.toCharArray();
    for (int j = 0; j < stringToPadChars.length; j++) {
      newChars[i++] = stringToPadChars[j];
    }    
    
    return new String(newChars);
  }
  
  public static byte[] convertFileToByteArray(File file) {
    InputStream is = null;
    byte[] returnValue = null;
    
    try {
      is = new FileInputStream(file);
      // Get the size of the file
      long length = file.length();

      if (length > Integer.MAX_VALUE) {
          // File is too large
        throw new ApiException(file.getName() + " is too large to convert to a byte array.");
      }

      // Create the byte array to hold the data
      returnValue = new byte[(int)length];

      // Read in the bytes
      int offset = 0;
      int numRead = 0;
      while (offset < returnValue.length
             && (numRead=is.read(returnValue, offset, returnValue.length-offset)) >= 0) {
          offset += numRead;
      }

      // Ensure all the bytes have been read in
      if (offset < returnValue.length) {
          throw new ApiException("Could not completely read file " + file.getName());
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      // Close the input stream
      ApiFunctions.close(is);
    }

    return returnValue;
  }
  
  public static void sendFileViaTcpIp(File file, String host, int port) {
    Socket socket = null;
    OutputStream os = null;
    try {
      socket = new Socket(host, port);
      os = socket.getOutputStream();
      byte[] bytes = convertFileToByteArray(file);
      os.write(bytes);
      os.flush();
    }
    catch (Exception ex) {
      ApiFunctions.throwAsRuntimeException(ex);
    }
    finally {
      ApiFunctions.close(os);
      ApiFunctions.close(socket);
    }
    
  }

}