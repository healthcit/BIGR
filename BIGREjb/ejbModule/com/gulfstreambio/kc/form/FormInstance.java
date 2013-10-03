package com.gulfstreambio.kc.form;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.util.SystemNameUtils;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;

/**
 * Represents a form instance in KnowledgeCapture.
 */
public class FormInstance implements Serializable {
  
  private String _formInstanceId;
  private String _domainObjectId;
  private String _domainObjectType;
  private Timestamp _creationDateTime;
  private Timestamp _modificationDateTime;
  private String _formDefinitionId;
  private List _dataElements;
  private Map _dataElementCache;

  /**
   *  Creates a new <code>FormInstance</code>. 
   */
  public FormInstance() {
    super();
  }

  public FormInstance(FormInstance form) {
    this();
    setCreationDateTime(form.getCreationDateTime());
    setDomainObjectId(form.getDomainObjectId());
    setDomainObjectType(form.getDomainObjectType());
    setFormDefinitionId(form.getFormDefinitionId());
    setFormInstanceId(form.getFormInstanceId());
    setModificationDateTime(form.getModificationDateTime());
    DataElement[] dataElements = form.getDataElements();
    for (int i = 0; i < dataElements.length; i++) {
      addDataElement(new DataElement(dataElements[i]));
    }
  }

  public Timestamp getCreationDateTime() {
    return _creationDateTime;
  }

  public void setCreationDateTime(Timestamp datetime) {
    _creationDateTime = datetime;
  }

  public Timestamp getModificationDateTime() {
    return _modificationDateTime;
  }

  public void setModificationDateTime(Timestamp datetime) {
    _modificationDateTime = datetime;
  }

  public String getDomainObjectId() {
    return _domainObjectId;
  }

  public void setDomainObjectId(String id) {
    _domainObjectId = id;
  }

  public String getDomainObjectType() {
    return _domainObjectType;
  }

  public void setDomainObjectType(String type) {
    _domainObjectType = type;
  }

  public String getFormDefinitionId() {
    return _formDefinitionId;
  }

  public void setFormDefinitionId(String id) {
    _formDefinitionId = id;
  }

  public String getFormInstanceId() {
    return _formInstanceId;
  }

  public void setFormInstanceId(String id) {
    _formInstanceId = id;
  }

  public void addDataElement(DataElement dataElement) {
    if (_dataElements == null) {
      _dataElements = new ArrayList();
      _dataElementCache = new HashMap();
    }
    _dataElements.add(dataElement);
    DetDataElement metadata = 
      DetService.SINGLETON.getDataElementTaxonomy().getDataElement(dataElement.getCuiOrSystemName());
    if (metadata != null) {
      _dataElementCache.put(metadata.getCui(), dataElement);
      _dataElementCache.put(metadata.getSystemName(), dataElement);      
    }
  }

  public DataElement getDataElement(String cuiOrSystemName) {
    if (_dataElements == null) {
      return null;
    }
    DataElement dataElement = (DataElement) _dataElementCache.get(cuiOrSystemName);
    if (dataElement == null) {
      String canonical = SystemNameUtils.convertToCanonicalForm(cuiOrSystemName);
      dataElement = (DataElement) _dataElementCache.get(canonical);      
    }
    return dataElement;
  }
  
  public void removeDataElement(DataElement dataElement) {
    if (_dataElements != null) {
      _dataElements.remove(dataElement);
      DetDataElement metadata = 
        DetService.SINGLETON.getDataElementTaxonomy().getDataElement(dataElement.getCuiOrSystemName());
      if (metadata != null) {
        _dataElementCache.remove(metadata.getCui());
        _dataElementCache.remove(metadata.getSystemName());      
      }
    }
  }

  public DataElement[] getDataElements() {
    return (_dataElements == null) 
      ? new DataElement[0] : (DataElement[]) _dataElements.toArray(new DataElement[0]);
  }

}
