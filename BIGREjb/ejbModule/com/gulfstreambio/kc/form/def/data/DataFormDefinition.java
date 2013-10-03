package com.gulfstreambio.kc.form.def.data;

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
 * Represents a KnowledgeCapture data form definition.  This class is basically a simple JavaBean
 * that holds the XML representation of a KnowledgeCapture data form definition, combined with
 * additional attributes from the database.
 * <p>
 * Since <code>DataFormDefinition</code> implements the <code>FormDefinition</code> interface, and
 * since Java does not support covariant return types (until Java 1.5), 
 * <code>DataFormDefinition</code> supports a "dual" API.  The types returned by the
 * <code>FormDefinition</code> interface methods are other form definition interfaces, as they
 * must be.  In addition, companion methods return the specific data form implementation class.
 * For example, {@link #getCategory(String) getCategory} is a <code>FormDefinition</code> interface 
 * method that returns a {@link FormDefinitionCategory}, also an interface, whereas
 * {@link #getDataCategory(String) getDataCategory} returns a {@link DataFormDefinitionCategory},
 * which implements <code>FormDefinitionCategory</code>.  The data form specific methods all start
 * with "getData", to distinguish them from the interface methods.
 */
public class DataFormDefinition extends FormDefinitionBase {

  private DataFormDefinitionElements _formElements;
  private DataFormDefinitionValueSets _valueSets;
  private Map _dataElementCache;
  private List _dataElements;
  private Map _hostElementCache;
  private List _hostElements;
  private Map _categoryCache;
  private List _categories;

  /**
   *  Creates a new <code>DataFormDefinition</code>. 
   */
  public DataFormDefinition() {
    super();
  }

  /**
   * Creates a new <code>DataFormDefinition</code> initialized from the specified one.
   * 
   * @param  form  the form definition used to initialize this form definition  
   */
  public DataFormDefinition(DataFormDefinition form) {
    super(form);
    DataFormDefinitionElements elements = form.getDataFormElements();
    if (elements != null) {
      setDataFormElements(new DataFormDefinitionElements(elements));      
    }
    DataFormDefinitionValueSets valueSets = form.getValueSets();
    if (valueSets != null) {
      setValueSets(new DataFormDefinitionValueSets(valueSets));      
    }
  }
  
  public FormDefinition copy() {
    return new DataFormDefinition(this);
  }
  
  public String getType() {
    return FormDefinitionTypes.DATA;
  }
  
  public FormDefinitionElements getFormElements() {
    return getDataFormElements();
  }
  
  public FormDefinitionCategory[] getCategories() {
    return getDataCategories();
  }  

  public FormDefinitionCategory getCategory(String displayName) {
    return getDataCategory(displayName);
  }
  
  public FormDefinitionDataElement[] getDataElements() {
    return getDataDataElements();
  }

  public FormDefinitionDataElement getDataElement(String cuiOrSystemName) {
    return getDataDataElement(cuiOrSystemName);
  }

  /**
   * Returns the elements of this data form definition.
   * 
   * @return  The elements, as a <code>DataFormDefinitionElements</code> instance.
   */
  public DataFormDefinitionElements getDataFormElements() {
    return _formElements;
  }

  public void setDataFormElements(DataFormDefinitionElements elements) {
    elements.setDataForm(this);
    _formElements = elements;
  }
  
  /**
   * Returns the category definition with the specified display name. 
   * 
   * @param  displayName  the case-sensitive display name of the category
   * @return  The <code>DataFormDefinitionCategory</code>.  If such a category does not exist, 
   *          then null is returned.
   */
  public DataFormDefinitionCategory getDataCategory(String displayName) {
    cacheCategories();
    return (DataFormDefinitionCategory) _categoryCache.get(displayName);
  }
  
  /**
   * Returns all category definitions that comprise this form definition.  The returned array
   * is a flattened view of all descendant categories in depth-first order.
   * 
   * @return  An array of all descendant categories.
   */
  public DataFormDefinitionCategory[] getDataCategories() {
    cacheCategories();
    if (_categories == null) {
      return new DataFormDefinitionCategory[0];
    }
    else {
      return (DataFormDefinitionCategory[]) _categories.toArray(new DataFormDefinitionCategory[0]);      
    }
  }

  /**
   * Returns the data element definition with the specified data element CUI or system name. 
   * 
   * @param  cuiOrSystemName the CUI or system name
   * @return  The <code>DataFormDefinitionDataElement</code>.  If such a data element does not  
   *          exist, then <code>null</code> is returned.
   */
  public DataFormDefinitionDataElement getDataDataElement(String cuiOrSystemName) {
    cacheDataElements();
    DataFormDefinitionDataElement def = 
      (DataFormDefinitionDataElement) _dataElementCache.get(cuiOrSystemName);
    if (def == null) {
      String canonical = SystemNameUtils.convertToCanonicalForm(cuiOrSystemName);
      def = (DataFormDefinitionDataElement) _dataElementCache.get(canonical);
    }
    return def;
  }
  
  /**
   * Returns all data element definitions that comprise this form definition.  The returned array
   * is a flattened view of all descendant data elements in depth-first order.
   * 
   * @return  An array of all descendant data elements.
   */
  public DataFormDefinitionDataElement[] getDataDataElements() {
    cacheDataElements();
    if (_dataElements == null) {
      return new DataFormDefinitionDataElement[0];
    }
    else {
      return (DataFormDefinitionDataElement[]) 
        _dataElements.toArray(new DataFormDefinitionDataElement[0]);      
    }
  }
  
  /**
   * Returns the host element definition with the specified data element host id. 
   * 
   * @param  hostId  the host Id
   * @return  The <code>DataFormDefinitionHostElement</code>.  If such a host element does not 
   *          exist, then <code>null</code> is returned.
   */
  public DataFormDefinitionHostElement getDataHostElement(String hostId) {
    cacheHostElements();
    return (DataFormDefinitionHostElement) _hostElementCache.get(hostId);
  }
  
  /**
   * Returns all host element definitions that comprise this form definition.
   * 
   * @return  An array of all host elements.
   */
  public DataFormDefinitionHostElement[] getDataHostElements() {
    cacheHostElements();
    if (_hostElements == null) {
      return new DataFormDefinitionHostElement[0];
    }
    else {
      return (DataFormDefinitionHostElement[]) 
        _hostElements.toArray(new DataFormDefinitionHostElement[0]);      
    }
  }

  public void setValueSets(DataFormDefinitionValueSets valueSets) {
    valueSets.setDataForm(this);
    _valueSets = valueSets;
  }
  
  public DataFormDefinitionValueSets getValueSets() {
    if (_valueSets == null) {
      setValueSets(new DataFormDefinitionValueSets());
    }
    return _valueSets;
  }
  
  private void cacheDataElements() {
    if (_dataElementCache == null) {
      DataFormDefinitionElements feDefs = getDataFormElements();
      if (feDefs != null) {
        _dataElementCache = new HashMap();
        _dataElements = new ArrayList();
        cacheDataElements(feDefs.getDataFormElements());
      }
    }
  }
  
  private void cacheDataElements(DataFormDefinitionElement[] formElementDefs) {
    for (int i = 0; i < formElementDefs.length; i++) {
      DataFormDefinitionElement fe = formElementDefs[i];
      if (fe.isDataElement()) {
        DataFormDefinitionDataElement de = fe.getDataDataElement();
        _dataElements.add(de);
        DetDataElement metadata = 
          DetService.SINGLETON.getDataElementTaxonomy().getDataElement(de.getCui());
        if (metadata != null) {
          _dataElementCache.put(metadata.getCui(), de);          
          _dataElementCache.put(metadata.getSystemName(), de);
        }
      }
      else if (fe.isCategory()) {
        DataFormDefinitionCategory cat = fe.getDataCategory();
        cacheDataElements(cat.getDataFormElements());        
      }
    }
  }

  private void cacheHostElements() {
    if (_hostElements == null) {
      DataFormDefinitionElements feDefs = getDataFormElements();
      if (feDefs != null) {
        _hostElementCache = new HashMap();
        _hostElements = new ArrayList();
        cacheHostElements(feDefs.getDataFormElements());
      }
    }
  }

  private void cacheHostElements(DataFormDefinitionElement[] formElements) {
    for (int i = 0; i < formElements.length; i++) {
      DataFormDefinitionElement fe = formElements[i];
      if (fe.isHostElement()) {
        DataFormDefinitionHostElement he = fe.getDataHostElement();
        _hostElements.add(he);
        _hostElementCache.put(he.getHostId(), he);
      }
      else if (fe.isCategory()) {
        DataFormDefinitionCategory cat = fe.getDataCategory();
        cacheHostElements(cat.getDataFormElements());       
      }
    }
  }

  private void cacheCategories() {
    if (_categories == null) {
      DataFormDefinitionElements feDefs = getDataFormElements();
      if (feDefs != null) {
        _categoryCache = new HashMap();
        _categories = new ArrayList();
        cacheCategories(feDefs.getDataFormElements());
      }
    }
  }

  private void cacheCategories(DataFormDefinitionElement[] formElementDefs) {
    for (int i = 0; i < formElementDefs.length; i++) {
      DataFormDefinitionElement fe = formElementDefs[i];
      if (fe.isCategory()) {
        DataFormDefinitionCategory cat = fe.getDataCategory();
        _categoryCache.put(cat.getDisplayName(), cat);
        _categories.add(cat);
        cacheCategories(cat.getDataFormElements());        
      }
    }
  }

  /**
   * Creates and returns a new <code>DataFormDefinition</code> from the specified formDefinition XML
   * document. 
   * 
   * @param  formDefinitionXml  the data form definition XML document
   * @return  The <code>DataFormDefinition</code> instance.
   * @throws  ApiException if there is a problem parsing the form definition XML document.
   */
  public static DataFormDefinition createFromXml(String xml) {
    DataFormDefinitionParser parser = new DataFormDefinitionParser();
    return parser.performParse(xml); 
  }

  public void toXml(StringBuffer buf) {
    buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    buf.append("<!DOCTYPE formDefinition PUBLIC \"");
    buf.append(DataFormDefinitionParser.DOCTYPE_PUBLIC_IDENTIFIER_XML1);
    buf.append("\" \"");
    buf.append(DataFormDefinitionParser.DOCTYPE_DTD_URI_XML1);
    buf.append("\">");
    BigrXmlUtils.writeElementStartTag(buf, "formDefinition", 0);
    BigrXmlUtils.writeAttribute(buf, "name", getName());
    buf.append('>');

    DataFormDefinitionElements formElementDefs = getDataFormElements();
    if (formElementDefs != null) {
      formElementDefs.setXmlIndentLevel(1); 
      formElementDefs.toXml(buf); 
    }
    
    DataFormDefinitionValueSets valueSets = getValueSets();
    if (valueSets != null) {
      valueSets.setXmlIndentLevel(1);
      valueSets.toXml(buf);      
    }

    BigrXmlUtils.writeElementEndTag(buf, "formDefinition", 0);
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
