package com.gulfstreambio.gboss;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.digester.Digester;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXMLParserBase;
import com.ardais.bigr.util.SystemNameUtils;

public class Gboss extends BigrXMLParserBase implements Serializable {
  
  private GbossDataElementTaxonomy _dataElementTaxonomy = null;
  private GbossAdes _ancillaryDataElements = null;
  private GbossUnits _units = null;
  private GbossValues _values = null;
  private GbossValueSets _valueSets = null;
  private boolean _immutable = false;

  private List _categoryList = new ArrayList(); 
  private Map _categoryCache = new HashMap(); 
  private Map _dataElementCache = new HashMap(); 
  private Map _adeCache = new HashMap(); 
  private Map _adeElementCache = new HashMap(); 
  private Map _conceptsByCuiCache = new HashMap();
  private Map _conceptsBySystemNameCache = new HashMap();
  private Map _valueCache = new HashMap();
  
  /**
   * Create a Gboss object from the data in a file input stream.
   * @param  gbossFis  the <code>FileInputStream</code> for the file containing the 
   * GBOSS information to parse.
   * @param  errorHandler  the <code>ErrorHandler</code> that will handle parsing errors.
   * Note - constructor is package protected as GbossFactory.getInstance is the preferred means of
   * of instantiation
   */
  Gboss(FileInputStream gbossFis, ErrorHandler errorHandler) {
    parseXML(new InputSource(gbossFis), errorHandler);
  }

  /**
   * Create a Gboss object from the data in a String.
   * @param  gbossXml  the <code>String</code> containing the GBOSS information to parse
   * @param  errorHandler  the <code>ErrorHandler</code> that will handle parsing errors.
   * Note - constructor is package protected as GbossFactory.getInstance is the preferred means of
   * of instantiation
   */
  Gboss(String gbossXml, ErrorHandler errorHandler) {
    parseXML(new InputSource(new StringReader(gbossXml)), errorHandler);
  }
  
  /**
   * Private method that constructs a SAX parser that can validate using an XML schema
   */
  private SAXParser getParser() {
    URL schemaURL =
      getClass().getResource(GbossUtils.GBOSS_CLASSPATH_RESOURCES_PREFIX + GbossUtils.XML_SCHEMA_NAME);
    String[] schemas = {schemaURL.toString()};
    
    SAXParserFactory factory = SAXParserFactory.newInstance();
    factory.setNamespaceAware(true); 
    factory.setValidating(true);
    SAXParser parser = null;
    try {
      parser = factory.newSAXParser();
      parser.setProperty(GbossUtils.SCHEMA_LANGUAGE, GbossUtils.W3C_XML_SCHEMA);
      parser.setProperty(GbossUtils.SCHEMA_SOURCE, schemas);
    }
    catch (ParserConfigurationException e) {
      throw new ApiException(e);
    }
    catch (SAXException e) {
      throw new ApiException(e);
    }
    return parser;
  }
  
  private void parseXML(InputSource inputSource, ErrorHandler errorHandler) {

    Digester digester = makeDigester(getParser(), errorHandler);

    digester.push(this);

    // Define the digester rules...

    // Set the properties of the GBOSS object.  XML attribute names matching
    // property names are automatically set.
    digester.addSetProperties("gboss");

    // A single det element should exist, so create it
    digester.addObjectCreate("*/det", GbossDataElementTaxonomy.class);
    digester.addSetProperties("*/det");
    digester.addSetRoot("*/det", "setDataElementTaxonomy", "com.gulfstreambio.gboss.GbossDataElementTaxonomy");
    
    //the det element and category elements can contain category elements.
    digester.addObjectCreate("*/category", GbossCategory.class);
    digester.addSetProperties("*/category");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/category", "vIntro", "VIntro");
    digester.addSetProperties("*/category", "vRevised", "VRevised");
    digester.addSetNext("*/category",
      "addCategory",
      "com.gulfstreambio.gboss.GbossCategory");
    //cache the concept represented by the category
    digester.addSetRoot("*/category",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossCategory");
    //the category element can contain dataElementInt elements.
    digester.addObjectCreate("*/category/dataElementInt", GbossDataElementInt.class);
    digester.addSetProperties("*/category/dataElementInt");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/category/dataElementInt", "vIntro", "VIntro");
    digester.addSetProperties("*/category/dataElementInt", "vRevised", "VRevised");
    digester.addBeanPropertySetter("*/category/dataElementInt/longDescription");
    digester.addBeanPropertySetter("*/category/dataElementInt/comment");
    digester.addSetProperties("*/category/dataElementInt/adeRef", "cui", "adeRef");
    digester.addBeanPropertySetter("*/category/dataElementInt/min");
    digester.addSetProperties("*/category/dataElementInt/min", "inclusive", "minInclusive");
    digester.addBeanPropertySetter("*/category/dataElementInt/max");
    digester.addSetProperties("*/category/dataElementInt/max", "inclusive", "maxInclusive");
    digester.addSetProperties("*/category/dataElementInt/unitRef", "cui", "unitRef");
    digester.addSetNext("*/category/dataElementInt",
      "addDataElement",
      "com.gulfstreambio.gboss.GbossDataElement");
    //cache the concept represented by the data element
    digester.addSetRoot("*/category/dataElementInt",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossDataElement");
    //the category element can contain dataElementFloat elements.
    digester.addObjectCreate("*/category/dataElementFloat", GbossDataElementFloat.class);
    digester.addSetProperties("*/category/dataElementFloat");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/category/dataElementFloat", "vIntro", "VIntro");
    digester.addSetProperties("*/category/dataElementFloat", "vRevised", "VRevised");
    digester.addBeanPropertySetter("*/category/dataElementFloat/longDescription");
    digester.addBeanPropertySetter("*/category/dataElementFloat/comment");
    digester.addSetProperties("*/category/dataElementFloat/adeRef", "cui", "adeRef");
    digester.addBeanPropertySetter("*/category/dataElementFloat/min");
    digester.addSetProperties("*/category/dataElementFloat/min", "inclusive", "minInclusive");
    digester.addBeanPropertySetter("*/category/dataElementFloat/max");
    digester.addSetProperties("*/category/dataElementFloat/max", "inclusive", "maxInclusive");
    digester.addSetProperties("*/category/dataElementFloat/unitRef", "cui", "unitRef");
    digester.addSetNext("*/category/dataElementFloat",
      "addDataElement",
      "com.gulfstreambio.gboss.GbossDataElement");
    //cache the concept represented by the data element
    digester.addSetRoot("*/category/dataElementFloat",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossDataElement");
    //the category element can contain dataElementDate elements.
    digester.addObjectCreate("*/category/dataElementDate", GbossDataElementDate.class);
    digester.addSetProperties("*/category/dataElementDate");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/category/dataElementDate", "vIntro", "VIntro");
    digester.addSetProperties("*/category/dataElementDate", "vRevised", "VRevised");
    digester.addBeanPropertySetter("*/category/dataElementDate/longDescription");
    digester.addBeanPropertySetter("*/category/dataElementDate/comment");
    digester.addSetProperties("*/category/dataElementDate/adeRef", "cui", "adeRef");
    digester.addBeanPropertySetter("*/category/dataElementDate/min", "minString");
    digester.addSetProperties("*/category/dataElementDate/min", "inclusive", "minInclusive");
    digester.addBeanPropertySetter("*/category/dataElementDate/max", "maxString");
    digester.addSetProperties("*/category/dataElementDate/max", "inclusive", "maxInclusive");
    digester.addSetNext("*/category/dataElementDate",
      "addDataElement",
      "com.gulfstreambio.gboss.GbossDataElement");
    //cache the concept represented by the data element
    digester.addSetRoot("*/category/dataElementDate",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossDataElement");
    //the category element can contain dataElementVpd elements.
    digester.addObjectCreate("*/category/dataElementVpd", GbossDataElementVpd.class);
    digester.addSetProperties("*/category/dataElementVpd");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/category/dataElementVpd", "vIntro", "VIntro");
    digester.addSetProperties("*/category/dataElementVpd", "vRevised", "VRevised");
    digester.addBeanPropertySetter("*/category/dataElementVpd/longDescription");
    digester.addBeanPropertySetter("*/category/dataElementVpd/comment");
    digester.addSetProperties("*/category/dataElementVpd/adeRef", "cui", "adeRef");
    digester.addBeanPropertySetter("*/category/dataElementVpd/min", "minString");
    digester.addSetProperties("*/category/dataElementVpd/min", "inclusive", "minInclusive");
    digester.addBeanPropertySetter("*/category/dataElementVpd/max", "maxString");
    digester.addSetProperties("*/category/dataElementVpd/max", "inclusive", "maxInclusive");
    digester.addSetNext("*/category/dataElementVpd",
      "addDataElement",
      "com.gulfstreambio.gboss.GbossDataElement");
    //cache the concept represented by the data element
    digester.addSetRoot("*/category/dataElementVpd",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossDataElement");
    //the category element can contain dataElementReport elements.
    digester.addObjectCreate("*/category/dataElementReport", GbossDataElementReport.class);
    digester.addSetProperties("*/category/dataElementReport");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/category/dataElementReport", "vIntro", "VIntro");
    digester.addSetProperties("*/category/dataElementReport", "vRevised", "VRevised");
    digester.addBeanPropertySetter("*/category/dataElementReport/longDescription");
    digester.addBeanPropertySetter("*/category/dataElementReport/comment");
    digester.addSetProperties("*/category/dataElementReport/adeRef", "cui", "adeRef");
    digester.addSetNext("*/category/dataElementReport",
      "addDataElement",
      "com.gulfstreambio.gboss.GbossDataElement");
    //cache the concept represented by the data element
    digester.addSetRoot("*/category/dataElementReport",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossDataElement");
    //the category element can contain dataElementText elements.
    digester.addObjectCreate("*/category/dataElementText", GbossDataElementText.class);
    digester.addSetProperties("*/category/dataElementText");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/category/dataElementText", "vIntro", "VIntro");
    digester.addSetProperties("*/category/dataElementText", "vRevised", "VRevised");
    digester.addBeanPropertySetter("*/category/dataElementText/longDescription");
    digester.addBeanPropertySetter("*/category/dataElementText/comment");
    digester.addSetProperties("*/category/dataElementText/adeRef", "cui", "adeRef");
    digester.addSetNext("*/category/dataElementText",
      "addDataElement",
      "com.gulfstreambio.gboss.GbossDataElement");
    //cache the concept represented by the data element
    digester.addSetRoot("*/category/dataElementText",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossDataElement");
    //the category element can contain dataElementCv elements.
    digester.addObjectCreate("*/category/dataElementCv", GbossDataElementCv.class);
    digester.addSetProperties("*/category/dataElementCv");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/category/dataElementCv", "vIntro", "VIntro");
    digester.addSetProperties("*/category/dataElementCv", "vRevised", "VRevised");
    digester.addBeanPropertySetter("*/category/dataElementCv/longDescription");
    digester.addBeanPropertySetter("*/category/dataElementCv/comment");
    digester.addSetProperties("*/category/dataElementCv/adeRef", "cui", "adeRef");
    digester.addSetProperties("*/category/dataElementCv/broadValueSetRef", "cui", "broadValueSetRef");
    digester.addSetProperties("*/category/dataElementCv/nonAtvValueSetRef", "cui", "nonAtvValueSetRef");
    digester.addSetNext("*/category/dataElementCv",
      "addDataElement",
      "com.gulfstreambio.gboss.GbossDataElement");
    //cache the concept represented by the data element
    digester.addSetRoot("*/category/dataElementCv",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossDataElement");
    
    //a single ades element should exist, so create it
    digester.addObjectCreate("*/ades", GbossAdes.class);
    digester.addSetProperties("*/ades");
    digester.addSetRoot("*/ades", "setAncillaryDataElements", "com.gulfstreambio.gboss.GbossAdes");
    //the ades element can contain ade elements.
    digester.addObjectCreate("*/ades/ade", GbossAde.class);
    digester.addSetProperties("*/ades/ade");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/ades/ade", "vIntro", "VIntro");
    digester.addSetProperties("*/ades/ade", "vRevised", "VRevised");
    digester.addSetNext("*/ades/ade",
      "addAncillaryDataElement",
      "com.gulfstreambio.gboss.GbossAde");
    //cache the concept represented by the ancillary data element
    digester.addSetRoot("*/ades/ade",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossAde");
    //the ade element can contain adeElementInt elements.
    digester.addObjectCreate("*/ades/ade/adeElementInt", GbossAdeElementInt.class);
    digester.addSetProperties("*/ades/ade/adeElementInt");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/ades/ade/adeElementInt", "vIntro", "VIntro");
    digester.addSetProperties("*/ades/ade/adeElementInt", "vRevised", "VRevised");
    digester.addBeanPropertySetter("*/ades/ade/adeElementInt/longDescription");
    digester.addBeanPropertySetter("*/ades/ade/adeElementInt/comment");
    digester.addBeanPropertySetter("*/ades/ade/adeElementInt/min");
    digester.addSetProperties("*/ades/ade/adeElementInt/min", "inclusive", "minInclusive");
    digester.addBeanPropertySetter("*/ades/ade/adeElementInt/max");
    digester.addSetProperties("*/ades/ade/adeElementInt/max", "inclusive", "maxInclusive");
    digester.addSetProperties("*/ades/ade/adeElementInt/unitRef", "cui", "unitRef");
    digester.addSetNext("*/ades/ade/adeElementInt",
      "addAncillaryDataElementElement",
      "com.gulfstreambio.gboss.GbossAdeElement");
    //cache the concept represented by the ancillary data element
    digester.addSetRoot("*/ades/ade/adeElementInt",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossAdeElement");
    //the ade element can contain adeElementFloat elements.
    digester.addObjectCreate("*/ades/ade/adeElementFloat", GbossAdeElementFloat.class);
    digester.addSetProperties("*/ades/ade/adeElementFloat");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/ades/ade/adeElementFloat", "vIntro", "VIntro");
    digester.addSetProperties("*/ades/ade/adeElementFloat", "vRevised", "VRevised");
    digester.addBeanPropertySetter("*/ades/ade/adeElementFloat/longDescription");
    digester.addBeanPropertySetter("*/ades/ade/adeElementFloat/comment");
    digester.addBeanPropertySetter("*/ades/ade/adeElementFloat/min");
    digester.addSetProperties("*/ades/ade/adeElementFloat/min", "inclusive", "minInclusive");
    digester.addBeanPropertySetter("*/ades/ade/adeElementFloat/max");
    digester.addSetProperties("*/ades/ade/adeElementFloat/max", "inclusive", "maxInclusive");
    digester.addSetProperties("*/ades/ade/adeElementFloat/unitRef", "cui", "unitRef");
    digester.addSetNext("*/ades/ade/adeElementFloat",
      "addAncillaryDataElementElement",
      "com.gulfstreambio.gboss.GbossAdeElement");
    //cache the concept represented by the ancillary data element
    digester.addSetRoot("*/ades/ade/adeElementFloat",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossAdeElement");
    //the ade element can contain adeElementDate elements.
    digester.addObjectCreate("*/ades/ade/adeElementDate", GbossAdeElementDate.class);
    digester.addSetProperties("*/ades/ade/adeElementDate");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/ades/ade/adeElementDate", "vIntro", "VIntro");
    digester.addSetProperties("*/ades/ade/adeElementDate", "vRevised", "VRevised");
    digester.addBeanPropertySetter("*/ades/ade/adeElementDate/longDescription");
    digester.addBeanPropertySetter("*/ades/ade/adeElementDate/comment");
    digester.addBeanPropertySetter("*/ades/ade/adeElementDate/min", "minString");
    digester.addSetProperties("*/ades/ade/adeElementDate/min", "inclusive", "minInclusive");
    digester.addBeanPropertySetter("*/ades/ade/adeElementDate/max", "maxString");
    digester.addSetProperties("*/ades/ade/adeElementDate/max", "inclusive", "maxInclusive");
    digester.addSetNext("*/ades/ade/adeElementDate",
      "addAncillaryDataElementElement",
      "com.gulfstreambio.gboss.GbossAdeElement");
    //cache the concept represented by the ancillary data element
    digester.addSetRoot("*/ades/ade/adeElementDate",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossAdeElement");
    //the ade element can contain adeElementVpd elements.
    digester.addObjectCreate("*/ades/ade/adeElementVpd", GbossAdeElementVpd.class);
    digester.addSetProperties("*/ades/ade/adeElementVpd");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/ades/ade/adeElementVpd", "vIntro", "VIntro");
    digester.addSetProperties("*/ades/ade/adeElementVpd", "vRevised", "VRevised");
    digester.addBeanPropertySetter("*/ades/ade/adeElementVpd/longDescription");
    digester.addBeanPropertySetter("*/ades/ade/adeElementVpd/comment");
    digester.addBeanPropertySetter("*/ades/ade/adeElementVpd/min", "minString");
    digester.addSetProperties("*/ades/ade/adeElementVpd/min", "inclusive", "minInclusive");
    digester.addBeanPropertySetter("*/ades/ade/adeElementVpd/max", "maxString");
    digester.addSetProperties("*/ades/ade/adeElementVpd/max", "inclusive", "maxInclusive");
    digester.addSetNext("*/ades/ade/adeElementVpd",
      "addAncillaryDataElementElement",
      "com.gulfstreambio.gboss.GbossAdeElement");
    //cache the concept represented by the ancillary data element
    digester.addSetRoot("*/ades/ade/adeElementVpd",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossAdeElement");
    //the ade element can contain adeElementReport elements.
    digester.addObjectCreate("*/ades/ade/adeElementReport", GbossAdeElementReport.class);
    digester.addSetProperties("*/ades/ade/adeElementReport");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/ades/ade/adeElementReport", "vIntro", "VIntro");
    digester.addSetProperties("*/ades/ade/adeElementReport", "vRevised", "VRevised");
    digester.addBeanPropertySetter("*/ades/ade/adeElementReport/longDescription");
    digester.addBeanPropertySetter("*/ades/ade/adeElementReport/comment");
    digester.addSetNext("*/ades/ade/adeElementReport",
      "addAncillaryDataElementElement",
      "com.gulfstreambio.gboss.GbossAdeElement");
    //cache the concept represented by the ancillary data element
    digester.addSetRoot("*/ades/ade/adeElementReport",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossAdeElement");
    //the ade element can contain adeElementText elements.
    digester.addObjectCreate("*/ades/ade/adeElementText", GbossAdeElementText.class);
    digester.addSetProperties("*/ades/ade/adeElementText");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/ades/ade/adeElementText", "vIntro", "VIntro");
    digester.addSetProperties("*/ades/ade/adeElementText", "vRevised", "VRevised");
    digester.addBeanPropertySetter("*/ades/ade/adeElementText/longDescription");
    digester.addBeanPropertySetter("*/ades/ade/adeElementText/comment");
    digester.addSetNext("*/ades/ade/adeElementText",
      "addAncillaryDataElementElement",
      "com.gulfstreambio.gboss.GbossAdeElement");
    //cache the concept represented by the ancillary data element
    digester.addSetRoot("*/ades/ade/adeElementText",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossAdeElement");
    //the ade element can contain adeElementCv elements.
    digester.addObjectCreate("*/ades/ade/adeElementCv", GbossAdeElementCv.class);
    digester.addSetProperties("*/ades/ade/adeElementCv");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/ades/ade/adeElementCv", "vIntro", "VIntro");
    digester.addSetProperties("*/ades/ade/adeElementCv", "vRevised", "VRevised");
    digester.addBeanPropertySetter("*/ades/ade/adeElementCv/longDescription");
    digester.addBeanPropertySetter("*/ades/ade/adeElementCv/comment");
    digester.addSetProperties("*/ades/ade/adeElementCv/broadValueSetRef", "cui", "broadValueSetRef");
    digester.addSetNext("*/ades/ade/adeElementCv",
      "addAncillaryDataElementElement",
      "com.gulfstreambio.gboss.GbossAdeElement");
    //cache the concept represented by the ancillary data element
    digester.addSetRoot("*/ades/ade/adeElementCv",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossAdeElement");
    
    //a single units element should exist, so create it
    digester.addObjectCreate("*/units", GbossUnits.class);
    digester.addSetProperties("*/units");
    digester.addSetRoot("*/units", "setUnits", "com.gulfstreambio.gboss.GbossUnits");
    //the units element can contain unit elements.
    digester.addObjectCreate("*/units/unit", GbossUnit.class);
    digester.addSetProperties("*/units/unit");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/units/unit", "vIntro", "VIntro");
    digester.addSetProperties("*/units/unit", "vRevised", "VRevised");
    digester.addSetNext("*/units/unit",
      "addUnit",
      "com.gulfstreambio.gboss.GbossUnit");
    //cache the concept represented by the unit
    digester.addSetRoot("*/units/unit",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossUnit");
    
    //a single values element should exist, so create it
    digester.addObjectCreate("*/values", GbossValues.class);
    digester.addSetProperties("*/values");
    digester.addSetRoot("*/values", "setValues", "com.gulfstreambio.gboss.GbossValues");
    
    //a single valueSets element should exist, so create it
    digester.addObjectCreate("*/valueSets", GbossValueSets.class);
    digester.addSetProperties("*/valueSets");
    digester.addSetRoot("*/valueSets", "setValueSets", "com.gulfstreambio.gboss.GbossValueSets");
    //the valueSets element can contain valueSet elements.
    digester.addObjectCreate("*/valueSets/valueSet", GbossValueSet.class);
    digester.addSetProperties("*/valueSets/valueSet");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/valueSets/valueSet", "vIntro", "VIntro");
    digester.addSetProperties("*/valueSets/valueSet", "vRevised", "VRevised");
    digester.addSetNext("*/valueSets/valueSet",
      "addValueSet",
      "com.gulfstreambio.gboss.GbossValueSet");
    //cache the concept represented by the valueSet
    digester.addSetRoot("*/valueSets/valueSet",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossValueSet");

    //values, valueSet, and value elements can contain value elements.
    digester.addObjectCreate("*/value", GbossValue.class);
    digester.addSetProperties("*/value");
    //handle vIntro and vRevised specially, since introspection is looking for setvIntro and
    //setvRevised methods
    digester.addSetProperties("*/value", "vIntro", "VIntro");
    digester.addSetProperties("*/value", "vRevised", "VRevised");
    digester.addBeanPropertySetter("*/value/longDescription");
    digester.addBeanPropertySetter("*/value/comment");
    digester.addSetNext("*/value",
      "addValue",
      "com.gulfstreambio.gboss.GbossValue");
    //cache the concept represented by the value
    digester.addSetRoot("*/value",
      "cacheConcept",
      "com.gulfstreambio.gboss.GbossValue");

    //valueSet and value elements can contain valueRef elements.  To handle them, we create a
    //GbossValue object and resolve the reference after the parsing is complete (see below)
    digester.addObjectCreate("*/valueRef", GbossValue.class);
    digester.addSetProperties("*/valueRef");
    digester.addSetNext("*/valueRef",
      "addValue",
      "com.gulfstreambio.gboss.GbossValue");
    
    // Now that everything is set up, parse the XML.
    // Also resolve any references held by any of the objects we just created
    try {
      digester.parse(inputSource);
      resolveReferences();
    }
    catch (SAXException se) {
      throw new ApiException(se);
    }
    catch (IOException ie) {
      throw new ApiException(ie);
    }
  }
  
  /**
   * Private method that resolves any references held by the objects created via parsing.
   * Objects that can hold references are:
   * GbossAdeElementCv - broadValueSetRef
   * GbossAdeElementFloat - unitRef
   * GbossAdeElementInt - unitRef
   * GbossValueSet - child GbossValue objects (and their GbossValue children, nested N-levels)
   * GbossDataElement* - adeRef
   * GbossDataElementCv - broadValueSetRef, nonAtvValueSetRef
   * GbossDataElementFloat - unitRef
   * GbossDataElementInt - unitRef
   */
  private void resolveReferences() {
    //because of the way we are parsing the GBOSS xml file (see parseXML for more details),
    //a GbossValue object (and/or any of it's children) contained in a GbossValueSet may be a value 
    //reference that must be looked up.  To be safe, for every GbossValue in a GbossValueSet (including
    //the children of a GbossValue) try to look up the value from the cui and if a GbossValue is
    //returned use it instead of the original
    Iterator valueSetIterator = getValueSets().getValueSets().iterator();
    while (valueSetIterator.hasNext()) {
      GbossValueSet valueSet = (GbossValueSet)valueSetIterator.next();
      List values = valueSet.getValues();
      for (int i=0; i<values.size(); i++) {
        values.set(i, resolveValueReference((GbossValue)values.get(i)));
      }
    }
    
    Iterator adesIterator = getAncillaryDataElements().getAncillaryDataElements().iterator();
    while (adesIterator.hasNext()) {
      Iterator elementIterator = ((GbossAde)adesIterator.next()).getAncillaryDataElementElements().iterator();
      while (elementIterator.hasNext()) {
        GbossAdeElement element = (GbossAdeElement)elementIterator.next();
        if (element instanceof GbossAdeElementCv) {
          GbossAdeElementCv elementCv = (GbossAdeElementCv)element;
          GbossValueSet valueSet = resolveValueSetReference(elementCv.getBroadValueSetRef());
          elementCv.setBroadValueSet(valueSet);
        }
        else if (element instanceof GbossAdeElementFloat) {
          GbossAdeElementFloat elementFloat = (GbossAdeElementFloat)element;
          GbossUnit unit = resolveUnitReference(elementFloat.getUnitRef());
          elementFloat.setUnit(unit);
        }
        else if (element instanceof GbossAdeElementInt) {
          GbossAdeElementInt elementInt = (GbossAdeElementInt)element;
          GbossUnit unit = resolveUnitReference(elementInt.getUnitRef());
          elementInt.setUnit(unit);
        }
      }
    }
      
    resolveReferencesInCategories(getDataElementTaxonomy().getCategories());
  }
  
  private void resolveReferencesInCategories(List categories) {
    Iterator categoryIterator = categories.iterator();
    while (categoryIterator.hasNext()) {
      GbossCategory category = (GbossCategory)categoryIterator.next();
      if (!ApiFunctions.isEmpty(category.getCategories())) {
        resolveReferencesInCategories(category.getCategories());
      }
      else {
        Iterator dataElementIterator = category.getDataElements().iterator();
        while (dataElementIterator.hasNext()) {
          GbossDataElement dataElement = (GbossDataElement)dataElementIterator.next();
          GbossAde ade = resolveAdeReference(dataElement.getAdeRef());
          dataElement.setAde(ade);
          if (dataElement instanceof GbossDataElementCv) {
            GbossDataElementCv dataElementCv = (GbossDataElementCv)dataElement;
            GbossValueSet valueSet = resolveValueSetReference(dataElementCv.getBroadValueSetRef());
            dataElementCv.setBroadValueSet(valueSet);
            valueSet = resolveValueSetReference(dataElementCv.getNonAtvValueSetRef());
            dataElementCv.setNonAtvValueSet(valueSet);
          }
          else if (dataElement instanceof GbossDataElementFloat) {
            GbossDataElementFloat dataElementFloat = (GbossDataElementFloat)dataElement;
            GbossUnit unit = resolveUnitReference(dataElementFloat.getUnitRef());
            dataElementFloat.setUnit(unit);
          }
          else if (dataElement instanceof GbossDataElementInt) {
            GbossDataElementInt dataElementInt = (GbossDataElementInt)dataElement;
            GbossUnit unit = resolveUnitReference(dataElementInt.getUnitRef());
            dataElementInt.setUnit(unit);
          }
        }
      }
    }
  }
  
  private GbossValueSet resolveValueSetReference(String reference) {
    GbossValueSet returnValue = null;
    if (!ApiFunctions.isEmpty(reference)) {
      returnValue = getValueSets().getValueSet(reference);
    }
    return returnValue;
  }
  
  private GbossUnit resolveUnitReference(String reference) {
    GbossUnit returnValue = null;
    if (!ApiFunctions.isEmpty(reference)) {
      returnValue = getUnits().getUnit(reference);
    }
    return returnValue;
  }
    
  private GbossValue resolveValueReference(GbossValue value) {
    //If the value passed in is a reference, populate the reference from the real GbossValue.
    GbossValue lookupValue = findValueFromReference(value);
    if (lookupValue != null) {
      //call the populate() method instead of something like BigrBeanUtilsBean.SINGLETON.copyProperties(),
      //since we need to preserve some data on the source (such as parent value set)
      value.populate(lookupValue);
    }
    //now resolve any references contained in the children of this GbossValue
    List values = value.getValues();
    for (int i=0; i<values.size(); i++) {
      values.set(i, resolveValueReference((GbossValue)values.get(i)));
    }
    return value;
  }
    
  private GbossValue findValueFromReference(GbossValue value) {
    GbossValue returnValue = null;
    if (value != null) {
      returnValue = getValues().getValue(value.getCui());
    }
    return returnValue;
  }
  
  private GbossAde resolveAdeReference(String reference) {
    GbossAde returnValue = null;
    if (!ApiFunctions.isEmpty(reference)) {
      returnValue = getAncillaryDataElements().getAncillaryDataElement(reference);
    }
    return returnValue;
  }

  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    _immutable = true;
    if (_dataElementTaxonomy != null) {
      _dataElementTaxonomy.setImmutable();
    }
    if (_ancillaryDataElements != null) {
      _ancillaryDataElements.setImmutable();
    }
    if (_units != null) {
      _units.setImmutable();
    }
    if (_valueSets != null) {
      _valueSets.setImmutable();
    }
    if (_values != null) {
      _values.setImmutable();
    }

    _categoryCache = Collections.unmodifiableMap(_categoryCache);
    _dataElementCache = Collections.unmodifiableMap(_dataElementCache);
    _adeCache = Collections.unmodifiableMap(_adeCache);
    _adeElementCache = Collections.unmodifiableMap(_adeElementCache);
  }

  /**
   * Is this instance immutable.
   */
  public boolean isImmutable() {
    return _immutable;
  }

  /**
   * Throw an exception if this instance is immutable.
   */
  private void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable Gboss: " + this);
    }
  }
  
  /**
   * Verify that the data in this object is valid. Throws an IllegalStateException 
   * if invalid data is found
   */
  public void validate() {
    StringBuffer problems = new StringBuffer(100);
    boolean appendComma = false;
    if (getDataElementTaxonomy() != null) {
      List problemCategories = new ArrayList();
      validateCategories(getDataElementTaxonomy().getCategories(), problemCategories);
      if (!ApiFunctions.isEmpty(problemCategories)) {
        problems.append("more than one ancestor (including the category itself) with a non-null ");
        problems.append("databaseType value was found for the following categories: ");
        boolean includeComma = false;
        Iterator problemCategoryIterator = problemCategories.iterator();
        while (problemCategoryIterator.hasNext()) {
          GbossCategory problemCategory = (GbossCategory)problemCategoryIterator.next();
          if (includeComma) {
            problems.append(", ");
          }
          problems.append(problemCategory.getDescription());
          problems.append(" (cui=");
          problems.append(problemCategory.getCui());
          problems.append(")");
          includeComma = true;
        }
      }
    }
    if (problems.length() > 0) {
      throw new IllegalStateException("GBOSS is in an invalid state: " + problems.toString());
    }
  }
  
  //NOTE:  when we move to Java 1.5, handle this rule via the Schematron assertion
  //contained in the file named constraint.sch located in 
  //com.gulfstreambio.gboss.resources - at that time, we can also remove the parentCategory
  //functionality from the GbossCategory.java class, if nothing else is making use of it (it was
  //added to implement this validation)
  //every category that contains data elements must have exactly one category ancestor
  //that has a non-null databaseType value.  The category containing the data elements
  //is included in the chain (i.e. it is allowed to have a databaseType value and
  //if it does then none of it's ancestors can.
  private void validateCategories(List categories, List problemCategories) {
    Iterator categoryIterator = categories.iterator();
    while (categoryIterator.hasNext()) {
      GbossCategory category = (GbossCategory)categoryIterator.next();
      boolean multipleValuesFound = false;
      //if this catagory contains data elements, do the check
      if (!ApiFunctions.isEmpty(category.getDataElements())) {
        boolean databaseTypeValueFound = !ApiFunctions.isEmpty(category.getDatabaseType());
        GbossCategory parentCategory = category.getParentCategory();
        while (!multipleValuesFound && parentCategory != null) {
          if (!ApiFunctions.isEmpty(parentCategory.getDatabaseType())) {
            if (databaseTypeValueFound) {
              multipleValuesFound = true;
            }
            databaseTypeValueFound = true;
          }
          parentCategory = parentCategory.getParentCategory();
        }
        if (multipleValuesFound) {
          problemCategories.add(category);
        }
      }
      //if no problem was found with the current category, validate it's child categories.
      //if problems were found with the current category then checking the children
      //will result in additional errors from the same problem
      if (!multipleValuesFound && !ApiFunctions.isEmpty(category.getCategories())) {
        validateCategories(category.getCategories(), problemCategories);
      }
    }
  }
  
  /**
   * @param ancillaryDataElements The ancillaryDataElements to set.
   */
  public void setAncillaryDataElements(GbossAdes ancillaryDataElements) {
    _ancillaryDataElements = ancillaryDataElements;
  }

  /**
   * @return Returns the ancillaryDataElements.
   */
  public GbossAdes getAncillaryDataElements() {
    return _ancillaryDataElements;
  }

  /**
   * @param dataElementTaxonomy The dataElementTaxonomy to set.
   */
  public void setDataElementTaxonomy(GbossDataElementTaxonomy dataElementTaxonomy) {
    _dataElementTaxonomy = dataElementTaxonomy;
  }
  
  /**
   * @return Returns the dataElementTaxonomy.
   */
  public GbossDataElementTaxonomy getDataElementTaxonomy() {
    return _dataElementTaxonomy;
  }
  
  /**
   * @param units The units to set.
   */
  public void setUnits(GbossUnits units) {
    _units = units;
  }  
  
  /**
   * @return Returns the units.
   */
  public GbossUnits getUnits() {
    return _units;
  }
  
  /**
   * @param values The values to set.
   */
  public void setValues(GbossValues values) {
    _values = values;
  }
  
  /**
   * @return Returns the values.
   */
  public GbossValues getValues() {
    return _values;
  }
  
  /**
   * @param valueSets The valueSets to set.
   */
  public void setValueSets(GbossValueSets valueSets) {
    _valueSets = valueSets;
  }
  
  /**
   * @return Returns the valueSets.
   */
  public GbossValueSets getValueSets() {
    return _valueSets;
  }

  /**
   * Returns the ADE with the specified CUI or system name.
   * 
   * @param cuiOrSystemName  the CUI or system name
   * @return  The ADE, or null if no such ADE exists.
   */
  public GbossAde getAncillaryDataElement(String cuiOrSystemName) {
    GbossAde ade = (GbossAde) _adeCache.get(cuiOrSystemName);
    if ((ade == null) && (cuiOrSystemName != null)) {
      String canonicalSystemName = SystemNameUtils.convertToCanonicalForm(cuiOrSystemName);
      ade = (GbossAde) _adeCache.get(canonicalSystemName);
    }
    return ade;
  }  
  
  /**
   * Returns the ADE element with the specified CUI or system name.
   * 
   * @param cuiOrSystemName  the CUI or system name
   * @return  The ADE element, or null if no such ADE element exists.
   */
  public GbossAdeElement getAncillaryDataElementElement(String cuiOrSystemName) {
    GbossAdeElement adeElement = (GbossAdeElement) _adeElementCache.get(cuiOrSystemName);
    if ((adeElement == null) && (cuiOrSystemName != null)) {
      String canonicalSystemName = SystemNameUtils.convertToCanonicalForm(cuiOrSystemName);
      adeElement = (GbossAdeElement) _adeElementCache.get(canonicalSystemName);
    }
    return adeElement;
  }  

  public List getCategories() {
    return _categoryList;
  }  
  
  /**
   * Returns the category with the specified CUI or system name.
   * 
   * @param cuiOrSystemName  the CUI or system name
   * @return  The category, or null if no such category exists.
   */
  public GbossCategory getCategory(String cuiOrSystemName) {
    GbossCategory category = (GbossCategory) _categoryCache.get(cuiOrSystemName);
    if ((category == null) && (cuiOrSystemName != null)) {
      String canonicalSystemName = SystemNameUtils.convertToCanonicalForm(cuiOrSystemName);
      category = (GbossCategory) _categoryCache.get(canonicalSystemName);
    }
    return category;
  }  

  /**
   * Returns the data element with the specified CUI or system name.
   * 
   * @param cuiOrSystemName  the CUI or system name
   * @return  The data element, or null if no such data element exists.
   */
  public GbossDataElement getDataElement(String cuiOrSystemName) {
    GbossDataElement dataElement = (GbossDataElement) _dataElementCache.get(cuiOrSystemName);
    if ((dataElement == null) && (cuiOrSystemName != null)) {
      String canonicalSystemName = SystemNameUtils.convertToCanonicalForm(cuiOrSystemName);
      dataElement = (GbossDataElement) _dataElementCache.get(canonicalSystemName);
    }
    return dataElement;
  }  

  /**
   * Returns an indication of whether the specified code is in fact a code.
   *
   * @param  code  the code in question
   * @return  <code>true</code> if the code is a valid code; <code>false</code> otherwise
   */
  public boolean isCode(String code) {
    return _conceptsByCuiCache.containsKey(code);
  }

  /**
   * Returns an indication of whether the specified system name is in fact a system name.
   *
   * @param  systemName  the system name in question.  It does not have to be in canonical form.
   * @return  <code>true</code> if the system name is a valid system name; 
   *          <code>false</code> otherwise
   */
  public boolean isSystemName(String systemName) {
    String s = SystemNameUtils.convertToCanonicalForm(systemName);
    return _conceptsBySystemNameCache.containsKey(s);
  }

  /**
   * Returns the code of the concept corresponding to the specified code or system name.  If the 
   * input is a code then it is returned.  If the input is a system name, then the 
   * code of the corresponding concept is returned. 
   *
   * @param  codeOrSystemName  the code or system name of the concept 
   * @return  The code.
   * @throws IllegalArgumentException if the concept does not exist.
   */
  public String getConceptCode(String codeOrSystemName) {

    // Convert the input to canonical form if it is a system name. 
    String identifier = codeOrSystemName;
    if (isSystemName(identifier)) {
      identifier = SystemNameUtils.convertToCanonicalForm(identifier);
    }

    // Purposefully look up the concept to get the IllegalArgumentException if the identifier
    // does not correspond to a concept. 
    GbossConcept c = getConcept(identifier);

    return c.getCui();
  }

  /**
   * Returns the GbossConcept object for the specified concept code or system name.
   *
   * @param  codeOrSystemName  the concept code or system name
   * @return  The concept object.
   * @throws IllegalArgumentException if the concept does not exist.
   */
  public GbossConcept getConcept(String codeOrSystemName) {
    GbossConcept concept = (GbossConcept) _conceptsByCuiCache.get(codeOrSystemName);
    if (concept == null) {
      concept = (GbossConcept) _conceptsBySystemNameCache.get(codeOrSystemName);
    }
    if (concept == null) {
      throw new IllegalArgumentException(
        "There is no concept with code or system name = " + codeOrSystemName);
    }
    return concept;
  }

  /**
   * Returns the GbossValue object for the specified cui.
   *
   * @param  cui  the cui
   * @return  The GbossValue object.
   * @throws IllegalArgumentException if the GbossValue does not exist.
   */
  public GbossValue getValue(String code) {
    GbossValue value = (GbossValue) _valueCache.get(code);
    if (value == null) {
      throw new IllegalArgumentException(
        "There is no value with code = " + code);
    }
    return value;
  }

  /**
   * Returns the description, or display value, for the specified concept
   * code.
   *
   * @param  code  the concept code, an exception will be thrown if the
   *     concept does not exist.
   * @return  The description associated with the concept code.
   */
  public String getDescription(String code) {
    return getConcept(code).getDescription();
  }
  
  private void cacheConceptByCui(GbossConcept concept) {
    if (concept != null && !ApiFunctions.isEmpty(concept.getCui())) {
      _conceptsByCuiCache.put(concept.getCui(), concept);
    }
  }
  
  public void cacheConcept(GbossCategory category) {
    cacheConceptByCui(category);
    if (category != null && !ApiFunctions.isEmpty(category.getSystemName())) {
      _conceptsBySystemNameCache.put(category.getSystemName(), category);
    }
    _categoryList.add(category);
    _categoryCache.put(category.getCui(), category);
    _categoryCache.put(category.getSystemName(), category);
    category.initCache();
  }
  
  public void cacheConcept(GbossDataElement dataElement) {
    cacheConceptByCui(dataElement);
    if (dataElement != null && !ApiFunctions.isEmpty(dataElement.getSystemName())) {
      _conceptsBySystemNameCache.put(dataElement.getSystemName(), dataElement);
    }
    _dataElementCache.put(dataElement.getCui(), dataElement);
    _dataElementCache.put(dataElement.getSystemName(), dataElement);              
  }
  
  public void cacheConcept(GbossAdeElement ancillaryDataElementElement) {
    cacheConceptByCui(ancillaryDataElementElement);
    if (ancillaryDataElementElement != null && !ApiFunctions.isEmpty(ancillaryDataElementElement.getSystemName())) {
      _conceptsBySystemNameCache.put(ancillaryDataElementElement.getSystemName(), ancillaryDataElementElement);
    }
    _adeElementCache.put(ancillaryDataElementElement.getCui(), ancillaryDataElementElement);
    _adeElementCache.put(ancillaryDataElementElement.getSystemName(), ancillaryDataElementElement);        
  }
  
  public void cacheConcept(GbossAde ade) {
    cacheConceptByCui(ade);
    if (ade != null && !ApiFunctions.isEmpty(ade.getSystemName())) {
      _conceptsBySystemNameCache.put(ade.getSystemName(), ade);
    }
    _adeCache.put(ade.getCui(), ade);
    _adeCache.put(ade.getSystemName(), ade);
  }
  
  public void cacheConcept(GbossUnit unit) {
    cacheConceptByCui(unit);
  }
  
  public void cacheConcept(GbossValueSet valueSet) {
    cacheConceptByCui(valueSet);
  }
  
  public void cacheConcept(GbossValue value) {
    cacheConceptByCui(value);
    _valueCache.put(value.getCui(), value);
  }
  
}
