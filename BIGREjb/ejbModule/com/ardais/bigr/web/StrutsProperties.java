package com.ardais.bigr.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ardais.bigr.api.ApiFunctions;

/**
 * @author dfeldman
 *
 * Represents the information in BigrWebResources.properties as a Properties object.
 * Retrieve an instance of this class using the {@link #getInstance()} method.
 */
public class StrutsProperties extends Properties {

  private static StrutsProperties _instance;

  /**
   * Retrieve an instance of this class.
   * @return the instance
   */
  public static StrutsProperties getInstance() {
    if (_instance == null) {
      _instance = new StrutsProperties();
    }
    return _instance;
  }

  /**
   * The constructor is private, use {@link #getInstance()} to retrieve an
   * instance of the class.
   */
  private StrutsProperties() {
    super();
    InputStream is = null;
    try {
      is = this.getClass().getResourceAsStream(ApiFunctions.CLASSPATH_RESOURCES_PREFIX + "BigrWebResources.properties");
      load(is);
    }
    catch (IOException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(is);
    }
  }

}
