package com.gulfstreambio.kc.form.def.results;

import java.io.FileInputStream;
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
 * Represents a KnowledgeCapture results form definition.  This class is basically a simple JavaBean
 * that holds the XML representation of a KnowledgeCapture results form definition, combined with
 * additional attributes from the database.
 * <p>
 * Since <code>ResultsFormDefinition</code> implements the <code>FormDefinition</code> interface,
 * and since Java does not support covariant return types (until Java 1.5), 
 * <code>ResultsFormDefinition</code> supports a "dual" API.  The types returned by the
 * <code>FormDefinition</code> interface methods are other form definition interfaces, as they
 * must be.  In addition, companion methods return the specific results form implementation class.
 * For example, {@link #getDataElement(String) getDataElement} is a <code>FormDefinition</code> 
 * interface method that returns a {@link FormDefinitionDataElement}, also an interface, whereas
 * {@link #getResultsDataElement(String) getResultsDataElement} returns a 
 * {@link ResultsFormDefinitionDataElement}, which implements 
 * <code>FormDefinitionDataElement</code>.  The results form specific methods all start with 
 * "getResults", to distinguish them from the interface methods.
 */
public class ResultsFormDefinition extends FormDefinitionBase {

  private ResultsFormDefinitionElements _formElements;
  private Map _dataElementCache;
  private List _dataElements;
  private Map _hostElementCache;
  private List _hostElements;

  /**
   *  Creates a new <code>ResultsFormDefinition</code>. 
   */
  public ResultsFormDefinition() {
    super();
  }

  /**
   * Creates a new <code>ResultsFormDefinition</code> initialized from the specified one.
   * 
   * @param  form  the form definition used to initialize this form definition  
   */
  public ResultsFormDefinition(ResultsFormDefinition form) {
    super(form);
    ResultsFormDefinitionElements elements = form.getResultsFormElements();
    if (elements != null) {
      setResultsFormElements(new ResultsFormDefinitionElements(elements));      
    }
  }

  public FormDefinition copy() {
    return new ResultsFormDefinition(this);
  }
  
  public String getType() {
    return FormDefinitionTypes.RESULTS;
  }

  public FormDefinitionElements getFormElements() {
    return getResultsFormElements();
  }

  public FormDefinitionCategory[] getCategories() {
    throw new UnsupportedOperationException("KnowledgeCapture results form definitions do not contain categories.");
  }

  public FormDefinitionCategory getCategory(String displayName) {
    throw new UnsupportedOperationException("KnowledgeCapture results form definitions do not contain categories.");
  }

  public FormDefinitionDataElement[] getDataElements() {
    return getResultsDataElements();
  }

  public FormDefinitionDataElement getDataElement(String cuiOrSystemName) {
    return getResultsDataElement(cuiOrSystemName);
  }  
  
  /**
   * Returns the elements of this results form definition.
   * 
   * @return  The elements, as a <code>ResultsFormDefinitionElements</code> instance.
   */
  public ResultsFormDefinitionElements getResultsFormElements() {
    return _formElements;
  }

  public void setResultsFormElements(ResultsFormDefinitionElements elements) {
    elements.setResultsForm(this);
    _formElements = elements;
  }

  /**
   * Returns the host element definition with the specified data element host id. 
   * 
   * @param  hostId  the host Id
   * @return  The <code>ResultsFormDefinitionHostElement</code>.  If such a host element does not 
   *          exist, then <code>null</code> is returned.
   */
  public ResultsFormDefinitionHostElement getResultsHostElement(String hostId) {
    cacheHostElements();
    return (ResultsFormDefinitionHostElement) _hostElementCache.get(hostId);
  }
  
  /**
   * Returns all host element definitions that comprise this form definition.
   * 
   * @return  An array of all host elements.
   */
  public ResultsFormDefinitionHostElement[] getResultsHostElements() {
    cacheHostElements();
    if (_hostElements == null) {
      return new ResultsFormDefinitionHostElement[0];
    }
    else {
      return (ResultsFormDefinitionHostElement[]) 
        _hostElements.toArray(new ResultsFormDefinitionHostElement[0]);      
    }
  }

  /**
   * Returns the data element definition with the specified data element CUI or system name.
   * 
   * @param  cuiOrSystemName  the CUI or system name
   * @return  The <code>ResultsFormDefinitionDataElement</code>.  If such a data element does not 
   *          exist, then <code>null</code> is returned.
   */
  public ResultsFormDefinitionDataElement getResultsDataElement(String cuiOrSystemName) {
    cacheDataElements();
    ResultsFormDefinitionDataElement def = 
      (ResultsFormDefinitionDataElement) _dataElementCache.get(cuiOrSystemName);
    if (def == null) {
      String canonical = SystemNameUtils.convertToCanonicalForm(cuiOrSystemName);
      def = (ResultsFormDefinitionDataElement) _dataElementCache.get(canonical);
    }
    return def;
  }
  
  /**
   * Returns all data element definitions that comprise this form definition.
   * 
   * @return  An array of all data elements.
   */
  public ResultsFormDefinitionDataElement[] getResultsDataElements() {
    cacheDataElements();
    if (_dataElements == null) {
      return new ResultsFormDefinitionDataElement[0];
    }
    else {
      return (ResultsFormDefinitionDataElement[]) 
        _dataElements.toArray(new ResultsFormDefinitionDataElement[0]);      
    }
  }

  private void cacheDataElements() {
    if (_dataElementCache == null) {
      ResultsFormDefinitionElements feDefs = getResultsFormElements();
      if (feDefs != null) {
        _dataElementCache = new HashMap();
        _dataElements = new ArrayList();
        cacheDataElements(feDefs.getResultsFormElements());
      }
    }
  }
  
  private void cacheDataElements(ResultsFormDefinitionElement[] formElements) {
    for (int i = 0; i < formElements.length; i++) {
      ResultsFormDefinitionElement fe = formElements[i];
      if (fe.isDataElement()) {
        ResultsFormDefinitionDataElement de = fe.getResultsDataElement();
        _dataElements.add(de);
        DetDataElement metadata = 
          DetService.SINGLETON.getDataElementTaxonomy().getDataElement(de.getCui());
        if (metadata != null) {
          _dataElementCache.put(metadata.getCui(), de);          
          _dataElementCache.put(metadata.getSystemName(), de);
        }
      }
    }
  }

  private void cacheHostElements() {
    if (_hostElements == null) {
      ResultsFormDefinitionElements feDefs = getResultsFormElements();
      if (feDefs != null) {
        _hostElementCache = new HashMap();
        _hostElements = new ArrayList();
        cacheHostElements(feDefs.getResultsFormElements());
      }
    }
  }

  private void cacheHostElements(ResultsFormDefinitionElement[] formElements) {
    for (int i = 0; i < formElements.length; i++) {
      ResultsFormDefinitionElement fe = formElements[i];
      if (fe.isHostElement()) {
        ResultsFormDefinitionHostElement he = fe.getResultsHostElement();
        _hostElements.add(he);
        _hostElementCache.put(he.getHostId(), he);
      }
    }
  }

  /**
   * Creates and returns a new <code>ResultsFormDefinition</code> from the specified formDefinition XML
   * document. 
   * 
   * @param  formDefinitionXml  the formDefinition XML document
   * @return  The <code>ResultsFormDefinition</code> instance.
   * @throws  ApiException if there is a problem parsing the form definition XML document.
   */
  public static ResultsFormDefinition createFromXml(String formDefinitionXml) {
    ResultsFormDefinitionParser parser = new ResultsFormDefinitionParser();
    return parser.performParse(formDefinitionXml); 
  }

  /**
   * Creates and returns a new <code>ResultsFormDefinition</code> from the specified formDefinition XML
   * document. 
   * 
   * @param  fis  a <code>FileInputStream</code> to a file containing a formDefinition XML document
   * @return  The <code>ResultsFormDefinition</code> instance.
   * @throws  ApiException if there is a problem parsing the form definition XML document.
   */
  public static ResultsFormDefinition createFromXml(FileInputStream fis) {
    ResultsFormDefinitionParser parser = new ResultsFormDefinitionParser();
    return parser.performParse(fis); 
  }
 
  public void toXml(StringBuffer buf) {
    buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    buf.append("<!DOCTYPE resultsFormDefinition PUBLIC \"");
    buf.append(ResultsFormDefinitionParser.DOCTYPE_PUBLIC_IDENTIFIER_XML1);
    buf.append("\" \"");
    buf.append(ResultsFormDefinitionParser.DOCTYPE_DTD_URI_XML1);
    buf.append("\">\n");
    buf.append("<resultsFormDefinition");
    BigrXmlUtils.writeAttribute(buf, "name", getName());
    buf.append(">\n");

    ResultsFormDefinitionElements formElements = getResultsFormElements();
    if (formElements != null) {
      formElements.toXml(buf); 
    }

    buf.append("\n</resultsFormDefinition>");
  }

  /**
   * Implements {@link java.lang.Object#equals(java.lang.Object) equals}.  We intentionally do not
   * consider tags, the id, whether the form definition is enabled, etc. since the 
   * core of the form definition is really the form element definitions and the name.
   */
  public boolean equals(Object obj) {
    if (!equalsExceptName(obj)) {
      return false;
    }
    else {
      ResultsFormDefinition f = (ResultsFormDefinition) obj;
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
    if (!(obj instanceof ResultsFormDefinition)) {
      return false;
    }
    
    ResultsFormDefinition f = (ResultsFormDefinition) obj;
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
