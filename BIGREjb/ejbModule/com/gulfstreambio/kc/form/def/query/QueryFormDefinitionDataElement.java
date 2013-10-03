package com.gulfstreambio.kc.form.def.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionDataElementBase;
import com.gulfstreambio.kc.form.def.Tag;

/**
 * Represents a single data element within a KnowledgeCapture {@link QueryFormDefinition}.
 */
public class QueryFormDefinitionDataElement 
  extends FormDefinitionDataElementBase implements Serializable {

  private QueryFormDefinition _form;
  private QueryFormDefinitionCategory _parentCategory;
  private List _adeElements;
  private Map _adeElementsCache;
  private QueryFormDefinitionValueSet _valueSet;
  private String _rollupValueSetId;
  private int _xmlIndentLevel = 0;

  /**
   *  Creates a new <code>QueryFormDefinitionDataElement</code>. 
   */
  public QueryFormDefinitionDataElement() {
    super();
  }

  public QueryFormDefinitionDataElement(QueryFormDefinitionDataElement dataElement) {
    super(dataElement);
    setRollupValueSetId(dataElement.getRollupValueSetId());
    QueryFormDefinitionAdeElement[] adeElements = dataElement.getAdeElements();
    for (int i = 0; i < adeElements.length; i++) {
      addAdeElement(new QueryFormDefinitionAdeElement(adeElements[i]));
    }
    QueryFormDefinitionValueSet valueSet = dataElement.getValueSet();
    if (valueSet != null) {
      setValueSet(new QueryFormDefinitionValueSet(valueSet));
    }
    
  }

  public FormDefinition getForm() {
    return getQueryForm();
  }

  /**
   * Returns the id of the rollup value set associated with this data element, if any.
   * 
   * @return  The rollup value set id.  Since rollup value sets are optional, this may return 
   *          <code>null</code>. 
   */
  public String getRollupValueSetId() {
    return _rollupValueSetId;
  }

  /**
   * Sets the id of the rollup value set associated with this data element.  This id should be the 
   * id of a rollup value set within the set of rollup value sets associated with the query form, 
   * though this is not checked.  
   * 
   * @param  id  the rollup value set id
   */
  public void setRollupValueSetId(String id) {
    _rollupValueSetId = id;
  }

  /**
   * Returns the rollup value set associated with this data element, if any.
   * 
   * @return  The rollup value set.  Since rollup value sets are optional, this may return 
   *          <code>null</code>. 
   */
  public QueryFormDefinitionRollupValueSet getRollupValueSet() {
    QueryFormDefinitionRollupValueSets valueSets = getQueryForm().getRollupValueSets();
    return (valueSets == null) ? null : valueSets.getValueSet(getRollupValueSetId());
  }

  /**
   * Adds the specified ADE element to this data element.
   * 
   * @param  adeElement  the <code>QueryFormDefinitionAdeElement</code>
   */
  public void addAdeElement(QueryFormDefinitionAdeElement adeElement) {
    if (_adeElements == null) {
      _adeElements = new ArrayList();
      _adeElementsCache = new HashMap();
    }
    _adeElements.add(adeElement);
    _adeElementsCache.put(adeElement.getCui(), adeElement);
  }

  /**
   * Returns the ADE element with the specified CUI or system name associated with this data 
   * element.
   * 
   * @return  The <code>QueryFormDefinitionAdeElement</code>, or <code>null</code> if the specified
   * ADE element is not associated with this data element.
   */
  public QueryFormDefinitionAdeElement getAdeElement(String cuiOrSystemName) {
    if (_adeElements == null) {
      return null;
    }
    else {
      QueryFormDefinitionAdeElement adeElement = 
        (QueryFormDefinitionAdeElement) _adeElementsCache.get(cuiOrSystemName);
      if (adeElement == null) {
        DetAdeElement detElement = 
          DetService.SINGLETON.getDataElementTaxonomy().getAdeElement(cuiOrSystemName);        
        adeElement = 
          (QueryFormDefinitionAdeElement) _adeElementsCache.get(detElement.getCui());
      }
      return adeElement;
    }
  }
  
  /**
   * Returns the ADE elements associated with this data element.
   * 
   * @return  The array of <code>QueryFormDefinitionAdeElement</code>.  If there are no ADE 
   * elements associated with this data element, then an empty array is returned.
   */
  public QueryFormDefinitionAdeElement[] getAdeElements() {
    return (_adeElements == null) 
      ? new QueryFormDefinitionAdeElement[0] 
      : (QueryFormDefinitionAdeElement[]) _adeElements.toArray(new QueryFormDefinitionAdeElement[0]);
  }
  
  /**
   * Returns the value set associated with this data element, if any.
   * 
   * @return  The value set.  Since value sets are optional, this may return <code>null</code>. 
   */
  public QueryFormDefinitionValueSet getValueSet() {
    return _valueSet;
  }

  /**
   * Sets the value set associated with this data element.
   * 
   * @param  valueSet the value set 
   */
  public void setValueSet(QueryFormDefinitionValueSet valueSet) {
    _valueSet = valueSet;
    if (valueSet != null) {
      valueSet.setQueryForm(getQueryForm());      
    }
  }

  /**
   * Returns the query form definition with which this data element is associated.
   *  
   * @return  The <code>QueryFormDefinition</code>.
   */
  public QueryFormDefinition getQueryForm() {
    return _form;
  }

  /**
   * Sets the query form definition with which this data element is associated.
   * As a side effect, also set the query form definition in the value set, if any. 
   *  
   * @param  form  the <code>QueryFormDefinition</code>
   */
  void setQueryForm(QueryFormDefinition form) {
    _form = form;
    QueryFormDefinitionValueSet valueSet = getValueSet();
    if (valueSet != null) {
      valueSet.setQueryForm(form);      
    }
  }

  /**
   * Returns the parent category of this data element.  
   * 
   * @return  The parent category.  If this data element does not have a parent category,
   *          <code>null</code> is returned.
   */
  public QueryFormDefinitionCategory getParentQueryCategory() {
    return _parentCategory;
  }

  /**
   * Sets the parent category of this data element.
   * 
   * @param  category  the parent category
   */
  void setParentQueryCategory(QueryFormDefinitionCategory category) {
    _parentCategory = category;
  }

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    int indent = getXmlIndentLevel();
    BigrXmlUtils.writeElementStartTag(buf, "dataElement", indent);
    BigrXmlUtils.writeAttribute(buf, "cui", getCui());
    String displayName = getDisplayName();
    if (!ApiFunctions.isEmpty(displayName)) {
      BigrXmlUtils.writeAttribute(buf, "displayName", displayName);
    }
    String valueSetId = getRollupValueSetId();
    if (!ApiFunctions.isEmpty(valueSetId)) {
      BigrXmlUtils.writeAttribute(buf, "rollupValueSetId", valueSetId);
    }
    buf.append('>');
    
    int childIndent = indent + 1;
    QueryFormDefinitionAdeElement[] adeElements = getAdeElements();
    for (int i = 0; i < adeElements.length; i++) {
      QueryFormDefinitionAdeElement adeElement = adeElements[i];
      adeElement.setXmlIndentLevel(childIndent);
      adeElement.toXml(buf);
    }
    
    QueryFormDefinitionValueSet valueSet = getValueSet();
    if (valueSet != null) {
      valueSet.setXmlIndentLevel(childIndent);
      valueSet.toXml(buf);
    }
    
    Tag[] tags = getTags();
    for (int i = 0; i < tags.length; i++) {
      Tag tag = tags[i];
      tag.setXmlIndentLevel(childIndent);
      tag.toXml(buf);
    }
    
    BigrXmlUtils.writeElementEndTag(buf, "dataElement", indent);
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
    if (!(obj instanceof QueryFormDefinitionDataElement)) {
      return false;
    }
    
    QueryFormDefinitionDataElement element = (QueryFormDefinitionDataElement) obj;
    String thisCui = getCui();
    String otherCui = element.getCui();
    String thisDisplayName = getDisplayName();        
    String otherDisplayName = element.getDisplayName();
        
    boolean result = true;
    result = result && ((thisCui == null) ? otherCui == null : thisCui.equals(otherCui));  
    result =
      result && ((thisDisplayName == null) 
          ? otherDisplayName == null : thisDisplayName.equals(otherDisplayName));
    result = result && (getTags().equals(element.getTags()));
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    String cui = getCui();
    String displayName = getDisplayName();        

    int result = 17;
    
    result = 37*result + ((cui == null) ? 0 : cui.hashCode());
    result = 37*result + ((displayName == null) ? 0 : displayName.hashCode());
    result = 37*result + getTags().hashCode();
    
    return result;
  }

}
