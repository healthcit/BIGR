package com.gulfstreambio.bigr.error;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ardais.bigr.api.ApiFunctions;

class BigrValidationErrorProperties extends Properties {

  private static BigrValidationErrorProperties _instance;

  /**
   * Retrieve an instance of this class.
   * @return the instance
   */
  static BigrValidationErrorProperties getInstance() {
    if (_instance == null) {
      _instance = new BigrValidationErrorProperties();
    }
    return _instance;
  }

  /**
   * The constructor is private, use {@link #getInstance()} to retrieve an
   * instance of the class.
   */
  private BigrValidationErrorProperties() {
    super();
    InputStream is = null;
    try {
      is = this.getClass().getResourceAsStream(ApiFunctions.CLASSPATH_RESOURCES_PREFIX + "BigrServiceResources.properties");
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
