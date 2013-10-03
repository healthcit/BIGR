package com.ardais.bigr.btx.framework;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.digester.Digester;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXMLParserBase;

/**
 * Parse the BTX configuration XML file (based on btxConfig.dtd) and register that
 * information with the static BtxConfiguration object.
 */
class BtxConfigurationParser extends BigrXMLParserBase {
  // This class is has package visibility.

  /**
   * Constructor for BtxConfigurationParser.
   */
  public BtxConfigurationParser() {
    super();
  }

  /**
   * Parse the XML file representing the BTX configuration and register that
   * information with the static BtxConfiguration object.  Currently,
   * once the configuration is parsed it is made immutable, and there's no provision
   * for reloading the configuration beyond restarting the application.
   * 
   * <p>The BTX configuration describes the BTX transactions involved in an application.
   */
  public void parseConfiguration() {
    // Create a default digester and register the location of the BTX configuration DTD.
    //
    Digester digester = makeDigester();
    {
      URL dtdURL =
        getClass().getResource(ApiFunctions.CLASSPATH_RESOURCES_PREFIX + "btxConfig.dtd");
      digester.register("-//Ardais Corporation//DTD BTX Configuration 1.0//EN", dtdURL.toString());
    }

    // The BtxConfiguration class only has static methods, but we need an
    // instance so that we can put it on the digester's stack.
    //
    BtxConfiguration config = BtxConfiguration.SINGLETON;

    // Any time we see a transaction element, create a BtxTransactionMetaData object
    digester.addObjectCreate("*/transaction", BtxTransactionMetaData.class);
    // Set transaction properties.  The SetProperties rule sets properties whose names are
    // the same in the XML file and in the BtxTransactionMetaData class, and sets properties
    // whose names differ according to the information in the two string arrays.
    digester.addSetProperties(
      "*/transaction",
      new String[] { "type", "performerMethod", "validatorMethod" },
      new String[] { "txType", "performerMethodName", "validatorMethodName" });
    // Register the completed BtxTransactionMetaData object with its parent BtxConfiguration object.
    digester.addSetNext(
      "*/transaction",
      "registerTransaction",
      "com.ardais.bigr.btx.framework.BtxTransactionMetaData");

    // Set each custom property for the transaction.
    digester.addCallMethod("*/transaction/set-property", "setProperty", 2);
    digester.addCallParam("*/transaction/set-property", 0, "property");
    digester.addCallParam("*/transaction/set-property", 1, "value");

    // Now that everything is set up, parse the configuration file(s).  There may be more
    // than one, the resource names of the configuration files are stored in the
    // btxConfig.properties file, see getBtxConfigXmlResourceNames for details.
    //
    try {
      Iterator iter = getBtxConfigXmlResourceNames().iterator();
      while (iter.hasNext()) {
        String resourceName = (String) iter.next();

        // Add the configuration as the first thing on the digester's stack,
        // so that the digester can access it during parsing.  We have to do this
        // each iteration, because this stack becomes empty after each call to
        // digester.parse.
        //
        digester.push(config);

        URL xmlURL = getClass().getResource(resourceName);

        if (xmlURL == null) {
          throw new ApiException("Resource not found: " + resourceName);
        }

        digester.parse(xmlURL.toString());
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    // Finally, validate the configuration and make it immutable (makeImmutable does both).
    //
    BtxConfiguration.makeImmutable();
  }

  /**
   * Return the list of BTX XML configuration resource names as defined in the "xmlResources"
   * property of the general BTX configuration properties.  A runtime exception is thrown if
   * this list is empty.  Each resource name must be suitable for passing to the
   * {@link Class#getResource(String) getResource} method on this object's Class.
   * 
   * @return the list of resource names.
   */
  private List getBtxConfigXmlResourceNames() {
    List result = null;

    Properties props = BtxConfiguration.getProperties();

    String[] resourceNames = ApiFunctions.splitAndTrim(props.getProperty("xmlResources", ""), ",");

    result = new ArrayList(resourceNames.length);
    for (int i = 0; i < resourceNames.length; i++) {
      String resourceName = resourceNames[i];

      if (!ApiFunctions.isEmpty(resourceName)) {
        result.add(resourceName);
      }
    }

    if (result.isEmpty()) {
      throw new ApiException("No xmlResources found in the general BTX configuration properties.");
    }

    return result;
  }

}
