package com.gulfstreambio.gboss;

import java.io.FileInputStream;

import org.xml.sax.ErrorHandler;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiResources;

public class GbossFactory {
  
  private static Gboss _systemInstance = null;

  public GbossFactory() {
    super();
  }
  
  /**
   * Initialize or refresh the system's Gboss information.
   */
  public static synchronized void initOrRefresh() {
    _systemInstance = null;
    getInstance();
  }
  
  /**
   * Retrieve the Gboss instance in use by the system.
   * @return Gboss  the Gboss instance in use by the system
   */
  public static synchronized Gboss getInstance() {
    if (_systemInstance == null) {
      FileInputStream gbossFis = null;
      try {
        ApiLogger.getLog().info("Started initializing GBOSS...");
        String gbossFilename = ApiResources.getResourcePath(GbossUtils.XML_FILE_NAME);
        gbossFis = new FileInputStream(gbossFilename);
        Gboss gboss = new Gboss(gbossFis, null);
        gboss.validate();
        gboss.setImmutable();
        //if we made it here then we have a valid gboss data object, so
        //it's ok to point the data member to it
        _systemInstance = gboss;
        ApiLogger.getLog().info("Finished initializing GBOSS.");
      }
      catch (Exception e) {
        ApiLogger.log("GBOSS initialization failed.", e);
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        // Don't use the usual ApiFunctions .close() method or exception-tranformation
        // function here -- because of when this code gets called very early in
        // application initialization, we don't want to risk any circular initialization
        // situations between the static class initializers in this class and the ones
        // in ApiFunctions.
        if (gbossFis != null) {
          try {
            gbossFis.close();
          }
          catch (Exception e) {
          }
        }
      }
    }
    return _systemInstance;
  }
  
  /**
   * Create a Gboss object from the data in a file input stream.
   * @param  fis  the <code>FileInputStream</code> for the file containing the 
   * GBOSS information to parse.
   */
  public Gboss getInstance(FileInputStream fis) {
    return new Gboss(fis, null);
  }
  
  /**
   * Create a Gboss object from the data in a String.
   * @param  gbossXml  the <code>String</code> containing the GBOSS information to parse
   */
  public Gboss getInstance(String s) {
    return new Gboss(s, null);
  }
  
  /**
   * Create a Gboss object from the data in a file input stream, using the specified ErrorHandler
   * to handle any parsing errors.
   * @param  gbossFis  the <code>FileInputStream</code> for the file containing the 
   * @param  errorHandler  the <code>ErrorHandler</code> that will handle parsing errors.
   */
  public Gboss getInstance(FileInputStream fis, ErrorHandler errorHandler) {
    return new Gboss(fis, errorHandler);
  }
  
  /**
   * Create a Gboss object from the data in a String, using the specified ErrorHandler
   * to handle any parsing errors.
   * @param  gbossXml  the <code>String</code> containing the GBOSS information to parse
   * @param  errorHandler  the <code>ErrorHandler</code> that will handle parsing errors.
   */
  public Gboss getInstance(String s, ErrorHandler errorHandler) {
    return new Gboss(s, errorHandler);
  }

}
