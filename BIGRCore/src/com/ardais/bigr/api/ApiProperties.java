package com.ardais.bigr.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class ApiProperties {

  // Stores properties;
  private static Properties properties = new Properties();

  // Static initialization of class.
  static {
    System.out.println("ApiProperties : Performing static initialization.");
    readProperties();
    System.out.println("ApiProperties : Completed static initialization.");
  }

  /**
   * ApiProperties default constructor.
   */
  private ApiProperties() {
    super();
  }
  
  /**
   * Returns the value associated with the key.
   * Returns null if the key is not found.
   * @return Returned associated value.
   * @param key Resource key.
   */
  public static String getProperty(String key) {
    return getProperty(key, null);
  }
  
  /**
   * Returns the value associated with the key.
   * Returns defaultValue if the key is not found or is empty.
   * @param key Resource key.
   * @param defaultValue the default value to return
   * @return Returned associated value.
   */
  public static String getProperty(String key, String defaultValue) {
    String v = properties.getProperty(key);
    if (v == null || v.trim().length() == 0)
      return defaultValue;
    return v;
  }
  
  /**
   * Reads a property and converts it to a boolean.
   * @return Returned associated value.
   * @param param  Resource key.
   */
  public static boolean getPropertyAsBool(String key) {
    return ("true".equalsIgnoreCase(getProperty(key)));
  }
  
  /**
   * Reads a property and converts it to a boolean.
   * @param key Resource key.
   * @param defaultValue  Default value if key is not found or is empty. 
   * @return boolean Returned associated value.
   */
  public static boolean getPropertyAsBool(String key, boolean defaultValue) {
    String v = getProperty(key);
    if (v != null)
      v = v.trim();
    if (v == null || v.length() == 0)
      return defaultValue;

    return ("true".equalsIgnoreCase(v));
  }
  
  /**
   * Reads a property and converts it to an integer.
   * @return Returned value in resource.
   * @param key Resource key.
   */
  public static int getPropertyAsInt(String key) {
    return getPropertyAsInt(key, 0);
  }
  
  /**
   * Reads a property and converts it to an integer.
   * @return Returned associated value.
   * @param key Resource key
   * @param defaultValue Default value if key is not found or is empty. 
   */
  public static int getPropertyAsInt(String key, int defaultValue) {
    int i = defaultValue;

    {
      String v = getProperty(key);
      if (v != null)
        v = v.trim();
      if (v == null || v.length() == 0)
        return i;

      i = Integer.valueOf(v).intValue();
    }

    return i;
  }
  
  /**
   * Reads a property and converts it to a long.
   * @return Returned value in resource.
   * @param key Resource key.
   */
  public static long getPropertyAsLong(String key) {
    return getPropertyAsLong(key, 0);
  }
  
  /**
   * Reads a property and converts it to a long.
   * @return Returned associated value.
   * @param key Resource key
   * @param defaultValue Default value if key is not found or is empty. 
   */
  public static long getPropertyAsLong(String key, long defaultValue) {
    long i = defaultValue;

    {
      String v = getProperty(key);
      if (v != null)
        v = v.trim();
      if (v == null || v.length() == 0)
        return i;

      i = Long.valueOf(v).longValue();
    }

    return i;
  }
  
  /**
   * @return true if legacy RNA support is enabled.
   */
  public static boolean isEnabledLegacy() {
    // This property controls whether old-style RNA functionality is enabled (when
    // RNA ans samples were different kinds of things, vs. the new way where RNA is
    // just another kind of sample).
    //
    return getPropertyAsBool(ApiResources.ENABLE_FEATURE_LEGACY, false);
  }
  
  /**
   * @return true if legacy DDC_CHECK flag support is enabled.
   */
  public static boolean isEnabledDDC_Check() {
    // This property controls whether old-style DDC CHECK functionality is enabled
    //
    return getPropertyAsBool(ApiResources.ENABLE_FEATURE_DDC_CHECK, false);
  }
  
  
  
  /**
   * Read the properties file and store the values.
   */
  private static void readProperties() {
    String propsFilename = ApiResources.getResourcePath("Api.properties");
    FileInputStream fis = null;
    try {

      System.out.println(
        "ApiProperties.readProperties() : Using resource file |" + propsFilename + "|");
      fis = new FileInputStream(propsFilename);
      properties.load(fis);

      // Loop through the property values, trimming whitespace from the
      // values.  Nagaraja had a very nasty problem where the database was deadlocking
      // for no apparent reason, and it turned out to be due to extra whitespace
      // in his Api.properties file.
      //
      Enumeration propNames = properties.propertyNames();
      while (propNames.hasMoreElements()) {
        String propName = (String) propNames.nextElement();
        String propValue = properties.getProperty(propName);
        if (propValue != null) {
          properties.setProperty(propName, propValue.trim());
        }
      }
    }
    catch (IOException e) {
      ApiLogger.log(
        "ApiProperties.readProperties() : Failed to read property file |" + propsFilename + "|",
        e);
    }
    finally {
      // Don't use the usual ApiFunctions .close() method or exception-tranformation
      // function here -- because of when this code gets called very early in
      // application initialization, we don't want to risk any circular initialization
      // situations between the static class initializers in this class and the ones
      // in ApiFunctions.
      //
      if (fis != null)
        try {
          fis.close();
        }
        catch (Exception e) {
        }
    }
  }
}
