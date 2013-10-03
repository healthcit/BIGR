package com.ardais.bigr.api;

import java.io.File;

/**
 * Contains information about the Api resource directory and properties files.
 */
public class ApiResources {
  public final static String API_PROVIDER_URL = "api.jndi.providerURL";
  public final static String API_INITIAL_CONTEXT_FACTORY = "api.jndi.initialContextFactory";
  public final static String API_JNDI_EJB_PACKAGES = "api.jndi.ejb.packages";
  public final static String API_JNDI_EJB_PACKAGE_DELIMITERS = "api.jndi.ejb.package.delimiters";
  public final static String API_JNDI_EJB_BINDINGS_CAPACITY = "api.jndi.ejb.bindings.capacity";
  
  public final static String API_DATASOURCE = "api.jdbc.ds";
  public final static String API_DATASOURCE_V5 = "api.jdbc.ds.v5";
  public final static String API_DBUSER = "api.db.user";
  public final static String API_DBPWD = "api.db.password";
  
  public final static String MAX_LOGIN_ATTEMPTS = "api.login.maxLoginAttempts";
  public final static String MAX_SECURITY_QUESTION_ATTEMPTS = "api.login.maxSecurityQuestionAttempts";

  public final static String SMTP_HOST = "api.mail.smtp";

  public final static String API_MAIL_FROM_DEFAULT = "api.mail.from.default";

  public final static String USER_INACTIVITY = "api.session.inactive";

  public final static String SUPPORT_NEWER_BROWSERS = "api.browser.supportnewer";
  
  public final static String BOOTSTRAP_USER = "api.bigr.bootstrap.user";
  public final static String BOOTSTRAP_ACCOUNT = "api.bigr.bootstrap.account";

  // This property controls whether old-style RNA functionality is enabled (when
  // RNA ans samples were different kinds of things, vs. the new way where RNA is
  // just another kind of sample).  Update: 5.22.07 Used to control additional
  // legacy display fields, RNA is one of those
  //
  public final static String ENABLE_FEATURE_LEGACY = "api.bigr.enable.legacy";
  
  // This property controls whether old-style DDC checks are to be used
  //
  public final static String ENABLE_FEATURE_DDC_CHECK = "api.bigr.enable.ddc_check";
  
  // The context-relative path to the kc directory in the web server.  
  public final static String KC_JSP_PATH = "com.gulfstreambio.kc.jsppath";
  
  // The the full Java path of the class that implements ElementRendererFactory for rendering host 
  // elements in KC forms.  This is only needed if you plan on adding host elements to KC forms.
  public final static String KC_HOST_RENDERER_FACTORY = 
    "com.gulfstreambio.kc.HostElementRendererFactory";
  
  // The the full Java path of the class that implements ElementRendererFactory for rendering KC 
  // data elements in KC forms.  This is optional, and is provided as one means of overriding the 
  // default UI rendering for KC data elements.  If not specified, the default KC element renderer 
  // factory, com.gulfstreambio.kc.web.support.KcElementRendererFactory is used.
  public final static String KC_KC_RENDERER_FACTORY = 
    "com.gulfstreambio.kc.KcElementRendererFactory";
  
  //The JAAS login configuration to utilize for the authentication of users
  public final static String JAAS_LOGIN_CONFIGURATION = "com.gulfstreambio.jaas_configuration";
  
  private static final String _resourceDirectory;

  // Static initialization
  static {
    // Initialize _resourceDirectory.  See getResourceDirectory().

    String dir = System.getProperty("bigr.resource.dir", "/opt/WebSphere/AppServer/bigr/");

    // Unix-style paths are recognized on Windows, so we check for either / or \ at the
    // end of the directory before we append File.separator.
    //
    if (!(dir.endsWith("/") || dir.endsWith("\\"))) {
      dir += File.separator;
    }

    _resourceDirectory = dir;
  }

  /**
   * This class is not meant to be instantiated so the constructor is private.
   */
  private ApiResources() {
    super();
  }

  /**
   * @return the directory where BIGR configuration files are rooted.  The path name is
   *   guaranteed to end with File.separator.  The default is "/opt/WebSphere/AppServer/bigr/",
   *   but you can override it by setting the Java system property "bigr.resource.dir".
   */
  public static String getResourceDirectory() {
    return _resourceDirectory;
  }

  public static String getResourcePath(String fileName) {
    String path = getResourceDirectory();

    if (fileName != null && fileName.length() != 0) {
      path += fileName;
    }

    return path;
  }
}
