package com.gulfstreambio.kc.form.def.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionDataElementBase;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.data.calculation.Calculation;

/**
 * Represents a single data element definition in a KnowledgeCapture {@link DataFormDefinition}.
 */
public class DataFormDefinitionDataElement
  extends FormDefinitionDataElementBase implements Serializable {

  private DataFormDefinition _form;
  private DataFormDefinitionCategory _parentCategory;
  private boolean _required = false;
  private String _standardValueSetId;
  private String _narrowValueSetId;
  private List _adeElements;
  private int _xmlIndentLevel = 0;
  private Calculation _calculation;
  private boolean _displayStandardValueSet = false;

  /**
   *  Creates a new <code>DataFormDefinitionDataElement</code>. 
   */
  public DataFormDefinitionDataElement() {
    super();
  }

  public DataFormDefinitionDataElement(DataFormDefinitionDataElement dataElement) {
    super(dataElement);
    setRequired(dataElement.isRequired());
    setNarrowValueSetId(dataElement.getNarrowValueSetId());
    setStandardValueSetId(dataElement.getStandardValueSetId());
    setCalculation(dataElement.getCalculation());
    setDisplayStandardValueSet(dataElement.isDisplayStandardValueSet());
    DataFormDefinitionAdeElement[] adeElements = dataElement.getAdeElements();
    for (int i = 0; i < adeElements.length; i++) {
      addAdeElement(new DataFormDefinitionAdeElement(adeElements[i]));
    }
  }

  public FormDefinition getForm() {
    return getDataForm();
  }

  /**
   * Returns whether a value is required for this data element in form instances created
   * from this form definition. 
   * 
   * @return  true if a value is required; false otherwise.
   */
  public boolean isRequired() {
    return _required;
  }

  /**
   * Sets whether a value is required for this data element in form instances created
   * from this form definition.
   *  
   * @param  required  true if a value is required; false otherwise.
   */
  public void setRequired(boolean required) {
    _required = required;
  }

  public String getNarrowValueSetId() {
    return _narrowValueSetId;
  }

  public DataFormDefinitionValueSet getNarrowValueSet() {
    DataFormDefinitionValueSets valueSetDefs = getDataForm().getValueSets();
    return valueSetDefs.getValueSet(getNarrowValueSetId());
  }

  public void setNarrowValueSetId(String id) {
    _narrowValueSetId = id;
  }

  public String getStandardValueSetId() {
    return _standardValueSetId;
  }

  public DataFormDefinitionValueSet getStandardValueSet() {
    DataFormDefinitionValueSets valueSetDefs = getDataForm().getValueSets();
    return valueSetDefs.getValueSet(getStandardValueSetId());
  }

  public void setStandardValueSetId(String id) {
    _standardValueSetId = id;
  }

  public DataFormDefinition getDataForm() {
    return _form;
  }

  void setDataForm(DataFormDefinition form) {
    _form = form;
  }

  /**
   * Returns the parent category definition of this data element definition.  This will return
   * <code>null</code> if the data element does not have a parent category.
   * 
   * @return The parent category definition.
   */
  public DataFormDefinitionCategory getParentDataCategory() {
    return _parentCategory;
  }

  /**
   * Sets the parent category definition of this data element definition.
   * 
   * @param  category  the parent category definition
   */
  void setParentDataCategory(DataFormDefinitionCategory category) {
    _parentCategory = category;
  }

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    int indent = getXmlIndentLevel();
    BigrXmlUtils.writeElementStartTag(buf, "dataElementDefinition", indent);
    BigrXmlUtils.writeAttribute(buf, "cui", getCui());
    BigrXmlUtils.writeAttribute(buf, "required", (isRequired() ? "true" : "false"));
    BigrXmlUtils.writeAttribute(buf, "displayStandardValueSet", (isDisplayStandardValueSet() ? "true" : "false"));
    String displayName = getDisplayName();
    if (!ApiFunctions.isEmpty(displayName)) {
      BigrXmlUtils.writeAttribute(buf, "displayName", displayName);
    }
    String valueSetId = getStandardValueSetId();
    if (!ApiFunctions.isEmpty(valueSetId)) {
      BigrXmlUtils.writeAttribute(buf, "standardValueSetId", valueSetId);
    }
    valueSetId = getNarrowValueSetId();
    if (!ApiFunctions.isEmpty(valueSetId)) {
      BigrXmlUtils.writeAttribute(buf, "narrowValueSetId", valueSetId);
    }
    buf.append('>');
    
    int childIndent = indent + 1;
    DataFormDefinitionAdeElement[] adeElements = getAdeElements();  
    for (int i = 0; i < adeElements.length; i++) {
      adeElements[i].setXmlIndentLevel(childIndent);
      adeElements[i].toXml(buf);
    }
    
    Tag[] tags = getTags();
    for (int i = 0; i < tags.length; i++) {
      Tag tag = tags[i];
      tag.setXmlIndentLevel(childIndent);
      tag.toXml(buf);
    }
    
    if (getCalculation() != null) {
      Calculation calculation = getCalculation();
      calculation.setXmlIndentLevel(childIndent);
      calculation.toXml(buf);
    }
    
    BigrXmlUtils.writeElementEndTag(buf, "dataElementDefinition", indent);
  }

  private int getXmlIndentLevel() {
    return _xmlIndentLevel;
  }

  void setXmlIndentLevel(int indentLevel) {
    _xmlIndentLevel = indentLevel;
  }
    
  /**
   * Implements {@link java.lang.Object#equals(java.lang.Object) equals}.  We intentionally do not
   * consider the value sets since they are only a convenience in terms of providing more directed
   * sets of values and are not an inherent part of the object.
   */
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DataFormDefinitionDataElement)) {
      return false;
    }
    
    DataFormDefinitionDataElement de = (DataFormDefinitionDataElement) obj;
    String thisCui = getCui();
    String otherCui = de.getCui();
    String thisDisplayName = getDisplayName();        
    String otherDisplayName = de.getDisplayName();
    
    boolean result = true;
    result = result && ((thisCui == null) ? otherCui == null : thisCui.equals(otherCui));  
    result =
      result && ((thisDisplayName == null) 
          ? otherDisplayName == null : thisDisplayName.equals(otherDisplayName));
    result = result && (_required == de._required);
    result = result && (getTags().equals(de.getTags()));
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    String cui = getCui();
    String displayName = getDisplayName();        

    int result = 17;
    
    result = 37*result + (_required ? 0 : 1);    
    result = 37*result + ((cui == null) ? 0 : cui.hashCode());
    result = 37*result + ((displayName == null) ? 0 : displayName.hashCode());
    result = 37*result + getTags().hashCode();
    
    return result;
  }

  /**
   * Returns the ADE element definition with the specified CUI if it has been added to this data 
   * form definition.
   * 
   * @param  cui the ADE element CUI
   * @return The ADE element with the specified CUI, or <code>null</code> if no such ADE element
   * exists.
   */  
  public DataFormDefinitionAdeElement getAdeElement(String cui) {
    DataFormDefinitionAdeElement[] elements = getAdeElements();
    for (int i = 0; i < elements.length; i++) {
      DataFormDefinitionAdeElement element = elements[i];
      if (cui.equals(element.getCui())) {
        return element;
      }
    }
    return null;    
  }
  
  /**
   * Returns all of the ADE element definitions that have been added to this data form definition.
   * 
   * @return The ADE element definitions.
   */
  public DataFormDefinitionAdeElement[] getAdeElements() {
    if (_adeElements == null) {
      return new DataFormDefinitionAdeElement[0];
    }
    else {
      return (DataFormDefinitionAdeElement[]) 
        _adeElements.toArray(new DataFormDefinitionAdeElement[0]);      
    }
  }
  
  /**
   * Adds an ADE element definition to this data form definition.
   * 
   * @param adeElement the ADE element definition
   */
  public void addAdeElement(DataFormDefinitionAdeElement adeElement) {
    if (_adeElements == null) {
      _adeElements = new ArrayList();
    }
    _adeElements.add(adeElement);
  }
  
  /**
   * @return Returns the calculation.
   */
  public Calculation getCalculation() {
    return _calculation;
  }
  
  /**
   * @param calculation The calculation to set.
   */
  public void setCalculation(Calculation calculation) {
    _calculation = calculation;
  }

  /**
   * Returns whether the standard value set should be displayed for this data element
   * when input controls are rendered.
   * 
   * @return  true if the standard value set should be displayed; false otherwise.
   */
  public boolean isDisplayStandardValueSet() {
    return _displayStandardValueSet;
  }

  /**
   * Sets whether the standard value set should be displayed for this data element
   * when input controls are rendered.
   *  
   * @param  required  if the standard value set should be displayed; false otherwise.
   */
  public void setDisplayStandardValueSet(boolean displayStandardValueSet) {
    _displayStandardValueSet = displayStandardValueSet;
  }
}
