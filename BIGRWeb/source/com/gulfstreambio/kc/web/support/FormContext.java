package com.gulfstreambio.kc.web.support;

import com.gulfstreambio.kc.det.DetAde;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.AdeElement;
import com.gulfstreambio.kc.form.DataElement;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionCategory;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionAdeElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionCategory;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;

/**
 * Provides context at different granularities for user interface renderers such as JSP tags and 
 * JSP fragments.  Has many methods used to get and set appropriate context at different levels
 * of granularity.  Intended to be used by user interface renderers of data form definitions and
 * query form definitions.
 * <p>
 * A <code>FormContext</code> instance cannot be created using the <code>new</code> operator or
 * a static factory method.  Instead the form context must always be obtained from the top of
 * the form context stack using the {@link FormContextStack#peek() FormContextStack peek} method.
 * Once a <code>FormContext</code> instance is obtained, the caller wishing to alter the context 
 * calls one or more set methods and then pushes the <code>FormContext</code> on the form context 
 * stack using the {@link FormContextStack#push(FormContext) FormContextStack push} method.
 * This actually creates a copy of the supplied <code>FormContext</code> instance and pushes it
 * on the stack, at which time the new context that was set takes effect.  This mechanism prevents
 * accidental overwriting of <code>FormContext</code> instances on the stack, but callers must
 * keep in mind that setters do not take effect until the <code>FormContext</code> instance is
 * pushed on the stack.
 * 
 * @see FormContextStack
 */
public class FormContext {

  private DataFormDefinition _dataFormDefinition;
  private DataFormDefinition _dataFormDefinitionBuffer;
  private boolean _isDataFormDefinitionBufferSet;
  
  private DataFormDefinitionCategory _dataFormDefinitionCategory;
  private DataFormDefinitionCategory _dataFormDefinitionCategoryBuffer;
  private boolean _isDataFormDefinitionCategoryBufferSet;

  private DataFormDefinitionDataElement _dataFormDefinitionDataElement;   
  private DataFormDefinitionDataElement _dataFormDefinitionDataElementBuffer;   
  private boolean _isDataFormDefinitionDataElementBufferSet;
  
  private DataFormDefinitionHostElement _dataFormDefinitionHostElement;   
  private DataFormDefinitionHostElement _dataFormDefinitionHostElementBuffer;   
  private boolean _isDataFormDefinitionHostElementBufferSet;
  
  private DetAdeElement _detAdeElement;   
  private DetAdeElement _detAdeElementBuffer;   
  private boolean _isDetAdeElementBufferSet;
  
  private FormInstance _formInstance;
  private FormInstance _formInstanceBuffer;
  private boolean _isFormInstanceBufferSet;
  
  private DataElement _dataElement;   
  private DataElement _dataElementBuffer;   
  private boolean _isDataElementBufferSet;
  
  private AdeElement _adeElement;   
  private AdeElement _adeElementBuffer;   
  private boolean _isAdeElementBufferSet;
  
  private String _domainObjectId;
  private String _domainObjectIdBuffer;
  private boolean _isDomainObjectIdSet;
  
  private String _domainObjectType;
  private String _domainObjectTypeBuffer;
  private boolean _isDomainObjectTypeSet;

  private String _javascriptObjectId;
  private String _javascriptObjectIdBuffer;
  private boolean _isJavascriptObjectIdSet;    
  
  private QueryFormDefinition _queryFormDefinition;
  private QueryFormDefinition _queryFormDefinitionBuffer;
  private boolean _isQueryFormDefinitionBufferSet;

  private QueryFormDefinitionCategory _queryFormDefinitionCategory;
  private QueryFormDefinitionCategory _queryFormDefinitionCategoryBuffer;
  private boolean _isQueryFormDefinitionCategoryBufferSet;

  private QueryFormDefinitionDataElement _queryFormDefinitionDataElement;   
  private QueryFormDefinitionDataElement _queryFormDefinitionDataElementBuffer;   
  private boolean _isQueryFormDefinitionDataElementBufferSet;
  
  private QueryFormDefinitionAdeElement _queryFormDefinitionAdeElement;
  private QueryFormDefinitionAdeElement _queryFormDefinitionAdeElementBuffer;
  private boolean _isQueryFormDefinitionAdeElementBufferSet;

  private DataFormDataElementContext _dataElementContext;
  private DataFormHostElementContext _hostElementContext;
  private DataFormAdeElementContext _adeElementContext;
  private QueryFormDataElementContext _queryDataElementContext;
  private QueryFormAdeElementContext _queryAdeElementContext;
  
  private PageLinkCollection _pageLinks;
  private PageLinkCollection _pageLinksBuffer;
  private boolean _isPageLinksBufferSet;
  private PageLink _pageLink;
  private PageLink _pageLinkBuffer;
  private boolean _isPageLinkBufferSet;
  private PageLink _parentPageLink;
  private PageLink _parentPageLinkBuffer;
  private boolean _isParentPageLinkBufferSet;
  
  /**
   * Creates a new <code>FormContext</code>.
   */
  FormContext() {
    super();
  }
  
  FormContext createCopy() {
    FormContext copy = new FormContext();

    copy._dataFormDefinition = (this._isDataFormDefinitionBufferSet) 
      ? this._dataFormDefinitionBuffer : this._dataFormDefinition;

    copy._dataFormDefinitionCategory = (this._isDataFormDefinitionCategoryBufferSet)
      ? this._dataFormDefinitionCategoryBuffer : this._dataFormDefinitionCategory;
    
    copy._dataFormDefinitionDataElement = (this._isDataFormDefinitionDataElementBufferSet)
      ? this._dataFormDefinitionDataElementBuffer : this._dataFormDefinitionDataElement;

    copy._dataFormDefinitionHostElement = (this._isDataFormDefinitionHostElementBufferSet)
    ? this._dataFormDefinitionHostElementBuffer : this._dataFormDefinitionHostElement;

    copy._detAdeElement = (this._isDetAdeElementBufferSet) 
      ? this._detAdeElementBuffer : this._detAdeElement;

    copy._formInstance = (this._isFormInstanceBufferSet) 
      ? this._formInstanceBuffer : this._formInstance;

    copy._dataElement = (this._isDataElementBufferSet) 
      ? this._dataElementBuffer : this._dataElement;

    copy._adeElement = (this._isAdeElementBufferSet) 
      ? this._adeElementBuffer : this._adeElement;

    copy._domainObjectId = (this._isDomainObjectIdSet) 
      ? this._domainObjectIdBuffer : this._domainObjectId;

    copy._domainObjectType = (this._isDomainObjectTypeSet) 
      ? this._domainObjectTypeBuffer : this._domainObjectType;

    copy._javascriptObjectId = (this._isJavascriptObjectIdSet) 
      ? this._javascriptObjectIdBuffer : this._javascriptObjectId;

    copy._queryFormDefinition = (this._isQueryFormDefinitionBufferSet) 
      ? this._queryFormDefinitionBuffer : this._queryFormDefinition;

    copy._queryFormDefinitionCategory = (this._isQueryFormDefinitionCategoryBufferSet)
      ? this._queryFormDefinitionCategoryBuffer : this._queryFormDefinitionCategory;
  
    copy._queryFormDefinitionDataElement = (this._isQueryFormDefinitionDataElementBufferSet)
      ? this._queryFormDefinitionDataElementBuffer : this._queryFormDefinitionDataElement;

    copy._queryFormDefinitionAdeElement = (this._isQueryFormDefinitionAdeElementBufferSet)
      ? this._queryFormDefinitionAdeElementBuffer : this._queryFormDefinitionAdeElement;

    if (!this._isDataFormDefinitionDataElementBufferSet && !this._isDataElementBufferSet) {
      copy._dataElementContext = this._dataElementContext;
    }

    if (!this._isDataFormDefinitionHostElementBufferSet) {
      copy._hostElementContext = this._hostElementContext;      
    }
    
    if (!this._isDataFormDefinitionDataElementBufferSet && !this._isDataElementBufferSet 
        && !this._isDetAdeElementBufferSet && !this._isAdeElementBufferSet) {
      copy._adeElementContext = this._adeElementContext;
    }

    if (!this._isQueryFormDefinitionDataElementBufferSet) {
      copy._queryDataElementContext = this._queryDataElementContext;      
    }

    if (!this._isQueryFormDefinitionDataElementBufferSet && !this._isDetAdeElementBufferSet) {
      copy._queryAdeElementContext = this._queryAdeElementContext;      
    }

    copy._pageLinks = (this._isPageLinksBufferSet) ? this._pageLinksBuffer : this._pageLinks;
    copy._pageLink = (this._isPageLinkBufferSet) ? this._pageLinkBuffer : this._pageLink;
    copy._parentPageLink = 
      (this._isParentPageLinkBufferSet) ? this._parentPageLinkBuffer : this._parentPageLink;

    return copy;
  }

  public DataFormDefinitionCategory getDataFormDefinitionCategory() {
    return _dataFormDefinitionCategory;
  }
  
  public void setDataFormDefinitionCategory(DataFormDefinitionCategory category) {
    _dataFormDefinitionCategoryBuffer = category;
    _isDataFormDefinitionCategoryBufferSet = true;
  }
  
  public DataFormDefinitionDataElement getDataFormDefinitionDataElement() {
    return _dataFormDefinitionDataElement;
  }
  
  public void setDataFormDefinitionDataElement(DataFormDefinitionDataElement dataElement) {
    _dataFormDefinitionDataElementBuffer = dataElement;
    _isDataFormDefinitionDataElementBufferSet = true;
  }
  
  public DataFormDefinitionHostElement getDataFormDefinitionHostElement() {
    return _dataFormDefinitionHostElement;
  }
  
  public void setDataFormDefinitionHostElement(DataFormDefinitionHostElement hostElement) {
    _dataFormDefinitionHostElementBuffer = hostElement;
    _isDataFormDefinitionHostElementBufferSet = true;
  }  

  public DataElement getDataElement() {
    return _dataElement;
  }
  
  public void setDataElement(DataElement dataElement) {
    _dataElementBuffer = dataElement;
    _isDataElementBufferSet = true;
  }
  
  public AdeElement getAdeElement() {
    return _adeElement;
  }
  
  public void setAdeElement(AdeElement adeElement) {
    _adeElementBuffer = adeElement;
    _isAdeElementBufferSet = true;
  }
  
  public DataFormDefinition getDataFormDefinition() {
    return _dataFormDefinition;
  }
  
  public void setDataFormDefinition(DataFormDefinition form) {
    _dataFormDefinitionBuffer = form;
    _isDataFormDefinitionBufferSet = true;
  }
  
  public DetAdeElement getDetAdeElement() {
    if (_detAdeElement == null) {
      AdeElement instance = getAdeElement();
      if (instance != null) {
        _detAdeElement = 
          DetService.SINGLETON.getDataElementTaxonomy().getAdeElement(instance.getCuiOrSystemName());
      }      
    }
    return _detAdeElement;
  }
  
  public void setDetAdeElement(DetAdeElement adeElement) {
    _detAdeElementBuffer = adeElement;
    _isDetAdeElementBufferSet = true;
  }
  
  public FormInstance getFormInstance() {
    return _formInstance;
  }
  
  public void setFormInstance(FormInstance form) {
    _formInstanceBuffer = form;
    _isFormInstanceBufferSet = true;
  }

  public String getDomainObjectId() {
    if (_domainObjectId != null) {
      return _domainObjectId;      
    }
    else {
      FormInstance form = getFormInstance();
      return (form != null) ? form.getDomainObjectId() : null;
    }
  }
  
  public void setDomainObjectId(String domainObjectId) {
    _domainObjectIdBuffer = domainObjectId;
    _isDomainObjectIdSet = true;
  }

  public String getDomainObjectType() {
    if (_domainObjectType != null) {
      return _domainObjectType;      
    }
    else {
      FormInstance form = getFormInstance();
      return (form != null) ? form.getDomainObjectType() : null;
    }
  }
  
  public void setDomainObjectType(String domainObjectType) {
    _domainObjectTypeBuffer = domainObjectType;
    _isDomainObjectTypeSet = true;
  }

  public String getJavascriptObjectId() {
    return _javascriptObjectId;
  }

  public void setJavascriptObjectId(String id) {
    _javascriptObjectIdBuffer = id;
    _isJavascriptObjectIdSet = true;    
  }

  public DetDataElement getDetDataElement() {
    DataFormDefinitionDataElement def = getDataFormDefinitionDataElement();
    if (def != null) {
      return DetService.SINGLETON.getDataElementTaxonomy().getDataElement(def.getCui());
    }
    else {
      DataElement dataElement = getDataElement();
      if (dataElement != null) {
        return DetService.SINGLETON.getDataElementTaxonomy().getDataElement(dataElement.getCuiOrSystemName());
      }
      else {
        QueryFormDefinitionDataElement queryDef = getQueryFormDefinitionDataElement();
        if (queryDef != null) {
          return DetService.SINGLETON.getDataElementTaxonomy().getDataElement(queryDef.getCui());
        }
        else {
          return null;          
        }
      }
    }
  }
  
  public DetAde getDetAde() {
    DetDataElement dataElement = getDetDataElement();
    return (dataElement == null) ? null : dataElement.getAde();
  }

  public DataFormElementContext getDataFormElementContext() {
    if (getDetAdeElement() != null) {
      return getDataFormAdeElementContext();
    }
    else if (getDetDataElement() != null) {
      return getDataFormDataElementContext();
    }
    else {
      return null;
    }
  }
  
  public DataFormDataElementContext getDataFormDataElementContext() {
    if (_dataElementContext == null) {
      _dataElementContext = 
        new DataFormDataElementContext(getDataFormDefinitionDataElement(), getDataElement());
    }
    return _dataElementContext;
  }

  public DataFormHostElementContext getDataFormHostElementContext() {
    if (_hostElementContext == null) {
      _hostElementContext = 
        new DataFormHostElementContext(getDataFormDefinitionHostElement());
    }
    return _hostElementContext;
  }

  public DataFormAdeElementContext getDataFormAdeElementContext() {
    if (_adeElementContext == null) {
      _adeElementContext = 
        new DataFormAdeElementContext(getDataFormDataElementContext(), getDetAdeElement(), getAdeElement());
    }
    return _adeElementContext;
  }
  
  public QueryFormElementContext getQueryFormElementContext() {
    if (getDetAdeElement() != null) {
      return getQueryFormAdeElementContext();
    }
    else if (getDetDataElement() != null) {
      return getQueryFormDataElementContext();
    }
    else {
      return null;
    }
  }

  public QueryFormDataElementContext getQueryFormDataElementContext() {
    if (_queryDataElementContext == null) {
      _queryDataElementContext = 
        new QueryFormDataElementContext(getQueryFormDefinitionDataElement());
    }
    return _queryDataElementContext;
  }

  public QueryFormAdeElementContext getQueryFormAdeElementContext() {
    if (_adeElementContext == null) {
      _queryAdeElementContext = 
        new QueryFormAdeElementContext(getQueryFormDataElementContext(), getDetAdeElement());
    }
    return _queryAdeElementContext;
  }
  
  public QueryFormDefinition getQueryFormDefinition() {
    return _queryFormDefinition;
  }
  
  public void setQueryFormDefinition(QueryFormDefinition form) {
    _queryFormDefinitionBuffer = form;
    _isQueryFormDefinitionBufferSet = true;
  }  
  
  public QueryFormDefinitionCategory getQueryFormDefinitionCategory() {
    return _queryFormDefinitionCategory;
  }
  
  public void setQueryFormDefinitionCategory(QueryFormDefinitionCategory category) {
    _queryFormDefinitionCategoryBuffer = category;
    _isQueryFormDefinitionCategoryBufferSet = true;
  }
  
  public QueryFormDefinitionDataElement getQueryFormDefinitionDataElement() {
    return _queryFormDefinitionDataElement;
  }
  
  public void setQueryFormDefinitionDataElement(QueryFormDefinitionDataElement queryElement) {
    _queryFormDefinitionDataElementBuffer = queryElement;
    _isQueryFormDefinitionDataElementBufferSet = true;
  }

  public QueryFormDefinitionAdeElement getQueryFormDefinitionAdeElement() {
    return _queryFormDefinitionAdeElement;
  }
  
  public void setQueryFormDefinitionAdeElement(QueryFormDefinitionAdeElement queryElement) {
    _queryFormDefinitionAdeElementBuffer = queryElement;
    _isQueryFormDefinitionAdeElementBufferSet = true;
  }
  
  public PageLinkCollection getPageLinks() {
    return _pageLinks;
  }

  public void setPageLinks(PageLinkCollection pageLinks) {
    _pageLinksBuffer = pageLinks;
    _isPageLinksBufferSet = true;
  }
  
  public PageLink getPageLink() {
    return _pageLink;
  }

  public void setPageLink(PageLink pageLink) {
    _pageLinkBuffer = pageLink;
    _isPageLinkBufferSet = true;
  }

  public PageLink getParentPageLink() {
    return _parentPageLink;
  }

  public void setParentPageLink(PageLink pageLink) {
    _parentPageLinkBuffer = pageLink;
    _isParentPageLinkBufferSet = true;
  }
}
