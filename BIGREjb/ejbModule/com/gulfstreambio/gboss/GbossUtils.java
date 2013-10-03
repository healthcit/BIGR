package com.gulfstreambio.gboss;

/**
 * GBOSS specific utilities.
 */
public class GbossUtils {

  /**
   * The preferred classpath location under which GBOSS resources are located.
   * This is the preferred place to put resources that should be considered
   * part of the software itself, rather than part of the description of how
   * to install or deploy the software.  Any resources that we'd expect to be
   * different in different deployments should NOT got here -- possibly
   * Api.properties or some other file under the ApiResources.getResourceDirectory()
   * directory would be a better location for such information.
   */
  public static final String GBOSS_CLASSPATH_RESOURCES_PREFIX = "/com/gulfstreambio/gboss/resources/";

  /**
   * Various constants related to XML schema parsing
   */
  public static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
  public static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema"; 
  public static final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
  public final static String XML_FILE_NAME = "gboss.xml";
  public static final String XML_SCHEMA_NAME = "gboss.xsd";

  // Cannot be instantiated since this is a class of static methods.
  private GbossUtils() {
    super();
  }

}
