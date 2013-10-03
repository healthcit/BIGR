package com.gulfstreambio.kc.form.def.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.util.BigrXmlUtils;
import com.ardais.bigr.util.SystemNameUtils;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionBase;
import com.gulfstreambio.kc.form.def.FormDefinitionCategory;
import com.gulfstreambio.kc.form.def.FormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.FormDefinitionElements;
import com.gulfstreambio.kc.form.def.FormDefinitionTypes;

/**
 * Represents a KnowledgeCapture query form definition.  This class is basically a simple JavaBean
 * that holds the XML representation of a KnowledgeCapture query form definition, combined with
 * additional attributes from the database.
 * <p>
 * Since <code>QueryFormDefinition</code> implements the <code>FormDefinition</code> interface, and
 * since Java does not support covariant return types (until Java 1.5), 
 * <code>QueryFormDefinition</code> supports a "dual" API.  The types returned by the
 * <code>FormDefinition</code> interface methods are other form definition interfaces, as they
 * must be.  In addition, companion methods return the specific query form implementation class.
 * For example, {@link #getCategory(String) getCategory} is a <code>FormDefinition</code> interface 
 * method that returns a {@link FormDefinitionCategory}, also an interface, whereas
 * {@link #getQueryCategory(String) getQueryCategory} returns a {@link QueryFormDefinitionCategory},
 * which implements <code>FormDefinitionCategory</code>.  The query form specific methods all start
 * with "getQuery", to distinguish them from the interface methods.
 */
public class QueryFormDefinition extends FormDefinitionBase {

  private QueryFormDefinitionElements _formElements;
  private QueryFormDefinitionRollupValueSets _rollupValueSets;
  private Map _dataElementCache;
  private List _dataElements;
  private Map _categoryCache;
  private List _categories;

  /**
   *  Creates a new <code>QueryFormDefinition</code>. 
   */
  public QueryFormDefinition() {
    super();
  }

  /**
   * Creates a new <code>QueryFormDefinition</code> initialized from the specified one.
   * 
   * @param  form  the form definition used to initialize this form definition  
   */
  public QueryFormDefinition(QueryFormDefinition form) {
    super(form);
    QueryFormDefinitionElements elements = form.getQueryFormElements();
    if (elements != null) {
      setQueryFormElements(new QueryFormDefinitionElements(elements));      
    }
    QueryFormDefinitionRollupValueSets valueSets = form.getRollupValueSets();
    if (valueSets != null) {
      setRollupValueSets(new QueryFormDefinitionRollupValueSets(valueSets));      
    }
  }

  public FormDefinition copy() {
    return new QueryFormDefinition(this);
  }

  public String getType() {
    return FormDefinitionTypes.QUERY;
  }

  public FormDefinitionElements getFormElements() {
    return getQueryFormElements();
  }
  
  public FormDefinitionCategory[] getCategories() {
    return getQueryCategories();
  }   

  public FormDefinitionCategory getCategory(String displayName) {
    return getQueryCategory(displayName);
  }

  public FormDefinitionDataElement[] getDataElements() {
    return getQueryDataElements();
  }

  public FormDefinitionDataElement getDataElement(String cuiOrSystemName) {
    return getQueryDataElement(cuiOrSystemName);
  }

  /**
   * Returns the elements of this query form definition.
   * 
   * @return  The elements, as a <code>QueryFormDefinitionElements</code> instance.
   */
  public QueryFormDefinitionElements getQueryFormElements() {
    return _formElements;
  }

  /**
   * Sets the elements of this query form definition.
   * 
   * @param  elements  the elements, as a <code>QueryFormDefinitionElements</code> instance
   */
  public void setQueryFormElements(QueryFormDefinitionElements elements) {
    elements.setQueryForm(this);
    _formElements = elements;
  }
  
  /**
   * Returns the category of this query form definition with the specified display name. 
   * 
   * @param  displayName  the case-sensitive display name of the category
   * @return  The <code>QueryFormDefinitionCategory</code>.  If such a category does not exist, 
   *          then <code>null</code> is returned.
   */
  public QueryFormDefinitionCategory getQueryCategory(String displayName) {
    cacheCategories();
    return (QueryFormDefinitionCategory) _categoryCache.get(displayName);
  }
  
  /**
   * Returns all category definitions that comprise this form definition.  The returned array
   * is a flattened view of all descendant categories in depth-first order.
   * 
   * @return  An array of all descendant categories.
   */
  public QueryFormDefinitionCategory[] getQueryCategories() {
    cacheCategories();
    if (_categories == null) {
      return new QueryFormDefinitionCategory[0];
    }
    else {
      return (QueryFormDefinitionCategory[]) _categories.toArray(new QueryFormDefinitionCategory[0]); 
    }
  }  
  
  /**
   * Returns the data element of this query form definition with the specified CUI or system name. 
   * 
   * @param  cuiOrSystemName the CUI or system name
   * @return  The <code>QueryFormDefinitionDataElement</code>.  If such a data element does not 
   *          exist, then <code>null</code> is returned.
   */
  public QueryFormDefinitionDataElement getQueryDataElement(String cuiOrSystemName) {
    cacheDataElements();
    QueryFormDefinitionDataElement def = 
      (QueryFormDefinitionDataElement) _dataElementCache.get(cuiOrSystemName);
    if (def == null) {
      String canonical = SystemNameUtils.convertToCanonicalForm(cuiOrSystemName);
      def = (QueryFormDefinitionDataElement) _dataElementCache.get(canonical);
    }
    return def;
  }
  
  /**
   * Returns all data element definitions that comprise this form definition.  The returned array
   * is a flattened view of all descendant data elements in depth-first order.
   * 
   * @return  An array of all descendant data elements.
   */
  public QueryFormDefinitionDataElement[] getQueryDataElements() {
    cacheDataElements();
    if (_dataElements == null) {
      return new QueryFormDefinitionDataElement[0];
    }
    else {
      return (QueryFormDefinitionDataElement[]) 
        _dataElements.toArray(new QueryFormDefinitionDataElement[0]);      
    }
  }

  public void setRollupValueSets(QueryFormDefinitionRollupValueSets valueSets) {
    _rollupValueSets = valueSets;
    _rollupValueSets.setQueryForm(this);
  }
  
  public QueryFormDefinitionRollupValueSets getRollupValueSets() {
    return _rollupValueSets;
  }
  
  private void cacheDataElements() {
    if (_dataElementCache == null) {
      QueryFormDefinitionElements elements = getQueryFormElements();
      if (elements != null) {
        _dataElementCache = new HashMap();
        _dataElements = new ArrayList();
        cacheDataElements(elements.getQueryFormElements());
      }
    }
  }
  
  private void cacheDataElements(QueryFormDefinitionElement[] formElements) {
    for (int i = 0; i < formElements.length; i++) {
      QueryFormDefinitionElement element = formElements[i];
      if (element.isDataElement()) {
        QueryFormDefinitionDataElement de = element.getQueryDataElement();
        _dataElements.add(de);
        DetDataElement metadata = 
          DetService.SINGLETON.getDataElementTaxonomy().getDataElement(de.getCui());
        if (metadata != null) {
          _dataElementCache.put(metadata.getCui(), de);          
          _dataElementCache.put(metadata.getSystemName(), de);
        }
      }
      else if (element.isCategory()) {
        QueryFormDefinitionCategory cat = element.getQueryCategory();
        cacheDataElements(cat.getQueryFormElements());        
      }
    }
  }

  private void cacheCategories() {
    if (_categories == null) {
      QueryFormDefinitionElements elements = getQueryFormElements();
      if (elements != null) {
        _categoryCache = new HashMap();
        _categories = new ArrayList();
        cacheCategories(elements.getQueryFormElements());
      }
    }
  }

  private void cacheCategories(QueryFormDefinitionElement[] formElements) {
    for (int i = 0; i < formElements.length; i++) {
      QueryFormDefinitionElement element = formElements[i];
      if (element.isCategory()) {
        QueryFormDefinitionCategory category = element.getQueryCategory();
        _categoryCache.put(category.getDisplayName(), category);
        _categories.add(category);
        cacheCategories(category.getQueryFormElements());        
      }
    }
  }
  
  /**
   * Sets default operators on all value sets in all data elements if appropriate.
   */
  void setValueSetDefaultOperators() {
    QueryFormDefinitionDataElement[] dataElements = getQueryDataElements();
    for (int i = 0; i < dataElements.length; i++) {
      QueryFormDefinitionDataElement dataElement = dataElements[i];
      QueryFormDefinitionValueSet valueSet = dataElement.getValueSet();
      if (valueSet != null) {
        valueSet.setDefaultOperator();
      }
      QueryFormDefinitionRollupValueSet rollupValueSet = dataElement.getRollupValueSet();
      if (rollupValueSet != null) {
        QueryFormDefinitionRollupValue[] rollupValues = rollupValueSet.getValues();
        for (int j = 0; j < rollupValues.length; j++) {
          QueryFormDefinitionRollupValue rollupValue = rollupValues[j];
          rollupValue.getValueSet().setDefaultOperator();
        }
      }
      QueryFormDefinitionAdeElement[] adeElements = dataElement.getAdeElements();
      for (int k = 0; k < adeElements.length; k++) {
        QueryFormDefinitionAdeElement adeElement = adeElements[k];
        valueSet = adeElement.getValueSet();
        if (valueSet != null) {
          valueSet.setDefaultOperator();
        }
      }      
    }
  }

  /**
   * Creates and returns a new <code>QueryFormDefinition</code> from the specified XML
   * document. 
   * 
   * @param  xml  the XML document
   * @return  The <code>QueryFormDefinition</code> instance.
   * @throws  ApiException if there is a problem parsing the XML document.
   */
  public static QueryFormDefinition createFromXml(String xml) {
    QueryFormDefinitionParser parser = new QueryFormDefinitionParser();
    return parser.performParse(xml); 
  }

  public void toXml(StringBuffer buf) {
    buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    buf.append("<!DOCTYPE queryFormDefinition PUBLIC \"");
    buf.append(QueryFormDefinitionParser.DOCTYPE_PUBLIC_IDENTIFIER_XML1);
    buf.append("\" \"");
    buf.append(QueryFormDefinitionParser.DOCTYPE_DTD_URI_XML1);
    buf.append("\">");
    BigrXmlUtils.writeElementStartTag(buf, "queryFormDefinition", 0);
    BigrXmlUtils.writeAttribute(buf, "name", getName());
    buf.append(">");

    QueryFormDefinitionElements formElements = getQueryFormElements();
    if (formElements != null) {
      formElements.setXmlIndentLevel(1); 
      formElements.toXml(buf); 
    }
    
    QueryFormDefinitionRollupValueSets rollupValueSets = getRollupValueSets();
    if (rollupValueSets != null) {
      rollupValueSets.setXmlIndentLevel(1); 
      rollupValueSets.toXml(buf); 
    }

    BigrXmlUtils.writeElementEndTag(buf, "queryFormDefinition", 0);
  }

  /**
   * Implements {@link java.lang.Object#equals(java.lang.Object) equals}.  We intentionally do not
   * consider value sets, tags, the id, whether the form definition is enabled, etc. since they 
   * the core of the form definition is really the form element definitions and the name.
   */
  public boolean equals(Object obj) {
    if (!equalsExceptName(obj)) {
      return false;
    }
    else {
      QueryFormDefinition f = (QueryFormDefinition) obj;
      return (getName() == null) ? f.getName() == null : getName().equals(f.getName());
    }
  }

  /**
   * Indicates whether the specified object is equal to this <code>DataFormDefinition</code> except
   * for its name.  This is handy since in some contexts we do not allow anything about a form 
   * definition to be changed except for its name, for example when a form definition has 
   * instances associated with it.
   */
  public boolean equalsExceptName(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof QueryFormDefinition)) {
      return false;
    }
    
    QueryFormDefinition f = (QueryFormDefinition) obj;
    return (_formElements == null)
      ? f._formElements == null
      : _formElements.equals(f._formElements);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    int result = 17;
    
    result = 37*result + ((getName() == null) ? 0 : getName().hashCode());
    result = 37*result + ((_formElements == null) ? 0 : _formElements.hashCode());
    
    return result;
  }

}
