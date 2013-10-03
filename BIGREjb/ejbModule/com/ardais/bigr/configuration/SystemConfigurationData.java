package com.ardais.bigr.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.digester.Digester;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.concept.BigrConceptList;
import com.ardais.bigr.iltds.assistants.LegalValue;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.databeans.Attribute;
import com.ardais.bigr.iltds.databeans.ChildSampleType;
import com.ardais.bigr.iltds.databeans.DerivativeOperation;
import com.ardais.bigr.iltds.databeans.DerivativeOperationConfiguration;
import com.ardais.bigr.iltds.databeans.ParentSampleType;
import com.ardais.bigr.iltds.databeans.SampleType;
import com.ardais.bigr.iltds.databeans.SampleTypeConfiguration;
import com.ardais.bigr.iltds.databeans.StorageType;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.BigrXMLParserBase;
import com.ardais.bigr.util.Constants;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;

/**
 * @author JEsielionis
 * Created on Oct 11, 2005
 */
public class SystemConfigurationData extends BigrXMLParserBase implements Serializable {
  
  private Map _conceptListMap = new HashMap();
  private List _conceptListList = new ArrayList();
  private Map _simpleListMap = new HashMap();
  private List _simpleListList = new ArrayList();
  private Map _legalValueSetMap = new HashMap();
  private List _legalValueSetList = new ArrayList();
  private SampleTypeConfiguration _sampleTypeConfiguration = null;
  private DerivativeOperationConfiguration _derivativeOperationConfiguration = null;
  private boolean _immutable = false;
  private BigrFormDefinition _resultsFormDefinition = null;
  private Map _sampleTypeDefaultedAttributes = new HashMap();
  private Map _sampleTypeInheritedAttributes = new HashMap();

  /**
   * Parses the XML in files.
   * @param  configFis  the <code>FileInputStream</code> for the file containing the 
   * configuration information to parse
   * @param  resultViewFis  the <code>FileInputStream</code> for the file containing the 
   * result view information to parse
   */
  public void initOrRefresh(FileInputStream configFis, FileInputStream resultViewFis) {
    parseConfigurationXML(new InputSource(configFis));
    _resultsFormDefinition = new BigrFormDefinition(ResultsFormDefinition.createFromXml(resultViewFis));
    _resultsFormDefinition.setFormDefinitionId(Constants.DEFAULT_RESULTS_VIEW_ID);
  }

  /**
   * Parses the XML in strings.
   * @param  configurationXml  the <code>String</code> containing the configuration information to parse
   * @param  resultViewXml  the <code>String</code> containing the result view information to parse
   */
  public void initOrRefresh(String configurationXml, String resultViewXml) {
    parseConfigurationXML(new InputSource(new StringReader(configurationXml)));
    _resultsFormDefinition = new BigrFormDefinition(ResultsFormDefinition.createFromXml(resultViewXml));
    _resultsFormDefinition.setFormDefinitionId(Constants.DEFAULT_RESULTS_VIEW_ID);
  }
  
  private void parseConfigurationXML(InputSource inputSource) {

    // Create a default digester.
    Digester digester = makeDigester();

    // Register the location of the system configuration DTD.
    URL dtdURL =
      SystemConfigurationData.class.getResource(ApiFunctions.CLASSPATH_RESOURCES_PREFIX + Constants.SYSTEM_CONFIGURATION_DTD);
    digester.register(
      "-//Ardais Corporation//DTD System Configuration XML1//EN",
      dtdURL.toString());

    digester.push(this);

    // Define the digester rules...

    // Set the properties of the SystemConfiguration object.  XML attribute names matching
    // DataElementTaxonomy property names are automatically set.
    digester.addSetProperties("systemConfiguration");

    // Create an ade object for each ade element.
    digester.addObjectCreate("*/conceptList", BigrConceptList.class);
    // Set the properties of the BigrConceptList object.  XML attribute names matching
    // BigrConceptList property names are automatically set.
    digester.addSetProperties("*/conceptList");
    // Add the BigrConceptList object to its parent object on the top of the stack.
    digester.addSetNext("*/conceptList", "addConceptList", "com.ardais.bigr.concept.BigrConceptList");
    
    // Retrieve the BigrConcept object for each concept element from
    // ORMMasterData and add it to the concept list.
    digester.addRule("*/conceptList/concept", new AddBigrConceptRule("addConcept"));    

    digester.addObjectCreate("*/simpleList", SimpleList.class);
    digester.addSetProperties("*/simpleList");
    digester.addSetNext("*/simpleList", "addSimpleList", "com.ardais.bigr.configuration.SimpleList");
    digester.addCallMethod("*/simpleList/listItem", "addListItem", 1);
    digester.addCallParam("*/simpleList/listItem", 0, "item");

    digester.addObjectCreate("*/legalValueSet", LegalValueSet.class);
    digester.addSetProperties("*/legalValueSet");
    digester.addSetNext("*/legalValueSet", "addLegalValueSet", "com.ardais.bigr.iltds.assistants.LegalValueSet");
    digester.addObjectCreate("*/legalValue", LegalValue.class);
    digester.addSetProperties("*/legalValue");
    digester.addSetNext("*/legalValue", "addLegalValue", "com.ardais.bigr.iltds.assistants.LegalValue");
    
    // Only a single sampleTypeConfiguration object should exist.
    digester.addObjectCreate("*/sampleTypeConfiguration", SampleTypeConfiguration.class);
    digester.addSetProperties("*/sampleTypeConfiguration");
    digester.addSetRoot("*/sampleTypeConfiguration",
      "setSampleTypeConfiguration",
      "com.ardais.bigr.iltds.databeans.SampleTypeConfiguration");
    
    // A sampleTypeConfiguration element can have multiple sampeType elements.
    digester.addObjectCreate("*/sampleTypeConfiguration/sampleType", SampleType.class);
    digester.addSetProperties("*/sampleTypeConfiguration/sampleType");
    digester.addSetNext("*/sampleTypeConfiguration/sampleType",
      "addSampleType",
      "com.ardais.bigr.iltds.databeans.SampleType");

    // A sampleType element can have multiple storage type elements.
    digester.addObjectCreate("*/sampleType/storageType", StorageType.class);
    digester.addSetProperties("*/sampleType/storageType");
    digester.addSetNext("*/sampleType/storageType",
      "addStorageType",
      "com.ardais.bigr.iltds.databeans.StorageType");

    // Only a single derivativeOperationConfiguration object should exist.
    digester.addObjectCreate("*/derivativeOperationConfiguration", DerivativeOperationConfiguration.class);
    digester.addSetProperties("*/derivativeOperationConfiguration");
    digester.addSetRoot("*/derivativeOperationConfiguration",
      "setDerivativeOperationConfiguration",
      "com.ardais.bigr.iltds.databeans.DerivativeOperationConfiguration");
    
    // A derivativeOperationConfiguration element can have multiple derivativeOperation elements.
    digester.addObjectCreate("*/derivativeOperationConfiguration/derivativeOperation", DerivativeOperation.class);
    digester.addSetProperties("*/derivativeOperationConfiguration/derivativeOperation");
    digester.addSetNext("*/derivativeOperationConfiguration/derivativeOperation",
      "addDerivativeOperation",
      "com.ardais.bigr.iltds.databeans.DerivativeOperation");

    // A derivativeOperation element can have multiple parent sample type elements.
    digester.addObjectCreate("*/derivativeOperation/parentSampleType", ParentSampleType.class);
    digester.addSetProperties("*/derivativeOperation/parentSampleType");
    digester.addSetNext("*/derivativeOperation/parentSampleType",
      "addParentSampleType",
      "com.ardais.bigr.iltds.databeans.ParentSampleType");

    // A parentSampleType element can have multiple child sample type elements.
    digester.addObjectCreate("*/parentSampleType/childSampleType", ChildSampleType.class);
    digester.addSetProperties("*/parentSampleType/childSampleType");
    digester.addSetNext("*/parentSampleType/childSampleType",
      "addChildSampleType",
      "com.ardais.bigr.iltds.databeans.ChildSampleType");

    // Now that everything is set up, parse the XML.
    try {
      digester.parse(inputSource);
    }
    catch (SAXException se) {
      throw new ApiException(se);
    }
    catch (IOException ie) {
      throw new ApiException(ie);
    }
  }
  
  public void addConceptList(BigrConceptList list) {
    checkImmutable();
    String listName = ApiFunctions.safeTrim(list.getName());
    if (ApiFunctions.isEmpty(listName)) {
      throw new ApiException("Attempted to add conceptList with empty name.");
    }
    else {
      if (_conceptListMap.put(listName.toLowerCase(), list) != null) {
        throw new ApiException("Duplicate conceptLists with name " + list.getName() + " encountered.");
      }
      else {
        _conceptListList.add(list);
      }
    }
  }
  
  List getConceptListList() {
    return _conceptListList;
  }
  
  public BigrConceptList getConceptList(String listName) {
    listName = ApiFunctions.safeTrim(listName);
    if (ApiFunctions.isEmpty(listName)) {
      throw new ApiException("A non-empty list name must be specified.");
    }
    else {
      return (BigrConceptList)_conceptListMap.get(listName.toLowerCase());
    }
  }

  public void addSimpleList(SimpleList list) {
    checkImmutable();
    String listName = ApiFunctions.safeTrim(list.getName());
    if (ApiFunctions.isEmpty(listName)) {
      throw new ApiException("Attempted to add simpleList with empty name.");
    }
    else {
      if (_simpleListMap.put(listName.toLowerCase(), list) != null) {
        throw new ApiException("Duplicate simpleLists with name " + list.getName() + " encountered.");
      }
      else {
        _simpleListList.add(list);
      }
    }
  }
  
  /**
   * @return
   */
  List getSimpleListList() {
    return _simpleListList;
  }
  
  public SimpleList getSimpleList(String listName) {
    listName = ApiFunctions.safeTrim(listName);
    if (ApiFunctions.isEmpty(listName)) {
      throw new ApiException("A non-empty list name must be specified.");
    }
    else {
      return (SimpleList)_simpleListMap.get(listName.toLowerCase());
    }
  }
  
  public void addLegalValueSet(LegalValueSet set) {
    checkImmutable();
    String name = ApiFunctions.safeTrim(set.getName());
    if (ApiFunctions.isEmpty(name)) {
      throw new ApiException("Attempted to add legalValueSet with empty name.");
    }
    else {
      if (_legalValueSetMap.put(name.toLowerCase(), set) != null) {
        throw new ApiException("Duplicate legalValueSets with name " + name + " encountered.");
      }
      else {
        _legalValueSetList.add(set);
      }
    }
  }

  List getLegalValueSetList() {
    return _legalValueSetList;
  }
  
  public LegalValueSet getLegalValueSet(String name) {
    name = ApiFunctions.safeTrim(name);
    if (ApiFunctions.isEmpty(name)) {
      throw new ApiException("A non-empty legal value set name must be specified.");
    }
    else {
      return (LegalValueSet)_legalValueSetMap.get(name.toLowerCase());
    }
  }

  /**
   * @return
   */
  public SampleTypeConfiguration getSampleTypeConfiguration() {
    return _sampleTypeConfiguration;
  }

  /**
   * @param configuration
   */
  public void setSampleTypeConfiguration(SampleTypeConfiguration configuration) {
    checkImmutable();
    _sampleTypeConfiguration = configuration;
  }
  
  /**
   * @return
   */
  public DerivativeOperationConfiguration getDerivativeOperationConfiguration() {
    return _derivativeOperationConfiguration;
  }

  /**
   * @param configuration
   */
  public void setDerivativeOperationConfiguration(DerivativeOperationConfiguration configuration) {
    _derivativeOperationConfiguration = configuration;
  }
  
  public BigrFormDefinition getDefaultResultsView() {
    return _resultsFormDefinition;
  }
  
  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    _immutable = true;
    Iterator conceptListIterator = _conceptListList.iterator();
    while (conceptListIterator.hasNext()) {
      ((BigrConceptList)conceptListIterator.next()).setCompleted();
      //note that there is no need to set each BigrConcept in each list to be immutable,
      //because when the list was created it was done so by retrieving BigrConcepts 
      //from ORMMasterData, and those BigrConcepts were already marked immutable
    }
    _conceptListMap = Collections.unmodifiableMap(_conceptListMap);
    _conceptListList = Collections.unmodifiableList(_conceptListList);
    Iterator simpleListIterator = _simpleListList.iterator();
    while (simpleListIterator.hasNext()) {
      ((SimpleList)simpleListIterator.next()).setCompleted();
      //note that there is no need to set each item in each list to be immutable,
      //because simple lists are comprised of strings
    }
    _simpleListMap = Collections.unmodifiableMap(_simpleListMap);
    _simpleListList = Collections.unmodifiableList(_simpleListList);
    if (_sampleTypeConfiguration != null) {
      _sampleTypeConfiguration.setImmutable();
    }
    if (_derivativeOperationConfiguration != null) {
      _derivativeOperationConfiguration.setImmutable();
    }
    if (_resultsFormDefinition != null) {
      _resultsFormDefinition.setImmutable();
    }
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
      throw new IllegalStateException("Attempted to modify an immutable SystemConfiguration: " + this);
    }
  }
  
  /**
   * Verify that the configuration data in this object is valid. Throws an IllegalStateException 
   * if invalid data is found
   */
  public void validate() {
    StringBuffer problems = new StringBuffer(100);
    boolean appendComma = false;
    //verify that all sample types are in the sample type concept list
    Iterator sampleTypeIterator = getSampleTypeConfiguration().getSampleTypeList().iterator();
    SampleType sampleType = null;
    Attribute attribute = null;
    StorageType storageType = null;
    while (sampleTypeIterator.hasNext()) {
      sampleType = (SampleType)sampleTypeIterator.next();
      if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(sampleType.getCui()))) {
        if (appendComma) {
          problems.append(",");
        }
        problems.append(" sampleType with empty cui encountered.");
        appendComma = true;
      }
      else {
        if (!getConceptList(SystemConfiguration.CONCEPT_LIST_SAMPLE_TYPES).containsConcept(sampleType.getCui())) {
          if (appendComma) {
            problems.append(",");
          }
          problems.append(" sampleType with cui (" + sampleType.getCui() + ") not in " + SystemConfiguration.CONCEPT_LIST_SAMPLE_TYPES + " concept list encountered");
          appendComma = true;
        }
      }
      //verify that all storage types under a sample type are in the storage types concept list
      Iterator storageTypeIterator = sampleType.getStorageTypeList().iterator();
      while (storageTypeIterator.hasNext()) {
        storageType = (StorageType)storageTypeIterator.next();
        if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(storageType.getCui()))) {
          if (appendComma) {
            problems.append(",");
          }
          problems.append(" storageType with empty cui encountered.");
          appendComma = true;
        }
        else {
          if (!getConceptList(SystemConfiguration.CONCEPT_LIST_STORAGE_TYPES).containsConcept(storageType.getCui())) {
            if (appendComma) {
              problems.append(",");
            }
            problems.append(" storageType with cui (" + storageType.getCui() + ") not in " + SystemConfiguration.CONCEPT_LIST_STORAGE_TYPES + " concept list encountered");
            appendComma = true;
          }
        }
      }
    }
    
    //verify that all derivative operations are in the derivative operations concept list
    Iterator derivativeOperationIterator = getDerivativeOperationConfiguration().getDerivativeOperationList().iterator();
    DerivativeOperation derivativeOperation = null;
    while (derivativeOperationIterator.hasNext()) {
      derivativeOperation = (DerivativeOperation)derivativeOperationIterator.next();
      if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(derivativeOperation.getCui()))) {
        if (appendComma) {
          problems.append(",");
        }
        problems.append(" derivativeOperation with empty cui encountered.");
        appendComma = true;
      }
      else {
        if (!getConceptList(SystemConfiguration.CONCEPT_LIST_DERIVATION_OPERATIONS).containsConcept(derivativeOperation.getCui())) {
          if (appendComma) {
            problems.append(",");
          }
          problems.append(" derivative operation with cui (" + derivativeOperation.getCui() + ") not in " + SystemConfiguration.CONCEPT_LIST_DERIVATION_OPERATIONS + " concept list encountered");
          appendComma = true;
        }
      }
    }
    if (problems.length() > 0) {
      throw new IllegalStateException("System configuration is in an invalid state:" + problems.toString());
    }
  }

}
