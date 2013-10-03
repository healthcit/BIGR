package com.ardais.bigr.api;

import org.apache.commons.logging.impl.LogFactoryImpl;

/**
 * Robustly create the kind of log that we want in BIGR.
 * 
 * @author gyost
 */
public class ApiLogFactory extends LogFactoryImpl {
  // This class is here primarily to ensure that our logger will continue to get used
  // even if IBM puts a commons-logging.properties file in the WebSphere class path that points
  // to a different logger.  Now all we have to worry about is whether they do something to
  // that makes Commons Logging use a different LogFactory...

  public ApiLogFactory() {
    super();
  }

  /**
   * @see org.apache.commons.logging.impl.LogFactoryImpl#getLogClassName()
   */
  protected String getLogClassName() {
    // For BIGR, we always want to use the Log4j logger, ignoring any Commons Logging
    // configuration in the class path or elsewhere that may say different.
    
    return "org.apache.commons.logging.impl.Log4JLogger";
  }

}
