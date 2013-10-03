package com.ardais.bigr.configuration;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;

/**
 * Used to capture file based printing data when processing the labelPrintingConfiguration.xml 
 * document.  This class is not intended to be used for any other purpose.
 */
public class Account implements Serializable {
  
  private String _id;
  private String _bartenderCommandLine;
  private Map _resultsFormDefinitionMap = new HashMap();
  private Map _labelTemplateDefinitionMap = new HashMap();
  private Map _labelPrinterMap = new HashMap();
  private boolean _immutable = false;
  
  /**
   * Creates a new Account.
   */
  public Account() {
    super();
  }
  
  /**
   * @return Returns the id.
   */
  public String getId() {
    return _id;
  }
  
  /**
   * @return Returns the bartenderCommandLine.
   */
  public String getBartenderCommandLine() {
    return _bartenderCommandLine;
  }

  public void setId(String id) {
    checkImmutable();
    _id = id;
  }
  
  /**
   * @param bartenderCommandLine The bartenderCommandLine to set.
   */
  public void setBartenderCommandLine(String bartenderCommandLine) {
    checkImmutable();
    _bartenderCommandLine = bartenderCommandLine;
  }
  
  public void addResultsFormDefinition(ResultsFormDefinition resultsFormDefinition) {
    checkImmutable();
    String name = ApiFunctions.safeTrim(resultsFormDefinition.getName());
    if (ApiFunctions.isEmpty(name)) {
      throw new ApiException("Attempted to add a results form definition with an empty name.");
    }
    else {
      if (_resultsFormDefinitionMap.put(name.toLowerCase(), resultsFormDefinition) != null) {
        throw new ApiException("Results form definition with duplicate id " + name + " encountered.");
      }
    }
  }
  
  public void addLabelTemplateDefinition(LabelTemplateDefinition labelTemplateDefinition) {
    checkImmutable();
    String name = ApiFunctions.safeTrim(labelTemplateDefinition.getName());
    if (ApiFunctions.isEmpty(name)) {
      throw new ApiException("Attempted to add a label template definition with an empty name.");
    }
    else {
      if (_labelTemplateDefinitionMap.put(name.toLowerCase(), labelTemplateDefinition) != null) {
        throw new ApiException("Label template definition with duplicate name " + name + " encountered.");
      }
      labelTemplateDefinition.setParent(this);
    }
  }
  
  public void addLabelPrinter(LabelPrinter labelPrinter) {
    checkImmutable();
    String name = ApiFunctions.safeTrim(labelPrinter.getName());
    if (ApiFunctions.isEmpty(name)) {
      throw new ApiException("Attempted to add a label printer with an empty name.");
    }
    else {
      if (_labelPrinterMap.put(name.toLowerCase(), labelPrinter) != null) {
        throw new ApiException("Label printer with duplicate name " + name + " encountered.");
      }
      labelPrinter.setParent(this);
    }
  }
  
  public Map getResultsFormDefinitions() {
    return _resultsFormDefinitionMap;
  }
  
  public Map getLabelTemplateDefinitions() {
    return _labelTemplateDefinitionMap;
  }
  
  public Map getLabelPrinters() {
    return _labelPrinterMap;
  }
  
  /**
   * Is this instance immutable.
   */
  public boolean isImmutable() {
    return _immutable;
  }
  
  public void setImmutable() {
    _immutable = true;
    //update the various entities in the various maps this object holds to be immutable
    //nothing to do for the results form definitions as resultsFormDefinition objects
    //cannot be made immutable, but make the map immutable
    _resultsFormDefinitionMap = Collections.unmodifiableMap(_resultsFormDefinitionMap);
    Iterator labelPrinterIterator = _labelPrinterMap.keySet().iterator();
    while (labelPrinterIterator.hasNext()) {
      ((LabelPrinter)_labelPrinterMap.get(labelPrinterIterator.next())).setImmutable();
    }
    _labelPrinterMap = Collections.unmodifiableMap(_labelPrinterMap);
    Iterator labelTemplateDefinitionIterator = _labelTemplateDefinitionMap.keySet().iterator();
    while (labelTemplateDefinitionIterator.hasNext()) {
      ((LabelTemplateDefinition)_labelTemplateDefinitionMap.get(labelTemplateDefinitionIterator.next())).setImmutable();
    }
    _labelTemplateDefinitionMap = Collections.unmodifiableMap(_labelTemplateDefinitionMap);
  }

  /**
   * Throw an exception if this instance is immutable.
   */
  private void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable Account: " + this);
    }
  }
  
}