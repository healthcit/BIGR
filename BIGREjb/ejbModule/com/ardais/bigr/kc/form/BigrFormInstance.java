package com.ardais.bigr.kc.form;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.gulfstreambio.kc.form.DataElement;
import com.gulfstreambio.kc.form.FormInstance;

/**
 * Represents a form instance in BIGR.  Can be initialized with or return a KnowledgeCapture 
 * {@link com.gulfstreambio.kc.form.FormInstance FormInstance} instance.
 */
public class BigrFormInstance implements Serializable {

  private String _formInstanceId;
  private String _domainObjectId;
  private String _domainObjectType;
  private Timestamp _creationDateTime;
  private Timestamp _modificationDateTime;  
  private String _formDefinitionId;
  private BigrFormDefinition _formDef;
  private FormInstance _kcFormInstance;
  private List _dataElements;

  /**
   * Creates a new <code>BigrFormInstance</code>. 
   */
  public BigrFormInstance() {
    super();
  }

  /**
   * Creates a new <code>BigrFormInstance</code> from the specified KnowledgeCapture 
   * <code>FormInstance</code>.  All of this fields of this <code>BigrFormInstance</code> 
   * are initialized from the KnowledgeCapture <code>FormInstance</code>.
   * 
   * @param  kcForm  the KnowledgeCapture <code>FormInstance</code>
   */
  public BigrFormInstance(FormInstance kcFormInstance) {
    super();

    // Copy all of the simple properties from the KC form to this BIGR form.
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, kcFormInstance);
    
    // Copy all of the data elements.
    DataElement[] dataElements = kcFormInstance.getDataElements();
    for (int i = 0; i < dataElements.length; i++) {
      addDataElement(dataElements[i]);
    }
  }

  public Timestamp getCreationDateTime() {
    return _creationDateTime;
  }

  public void setCreationDateTime(Timestamp date) {
    _creationDateTime = date;
  }

  public String getDomainObjectId() {
    return _domainObjectId;
  }

  public void setDomainObjectId(String string) {
    _domainObjectId = string;
  }

  public String getDomainObjectType() {
    return _domainObjectType;
  }

  public void setDomainObjectType(String string) {
    _domainObjectType = string;
  }

  public String getFormDefinitionId() {
    return _formDefinitionId;
  }

  public void setFormDefinitionId(String string) {
    _formDefinitionId = string;
  }

  public String getFormInstanceId() {
    return _formInstanceId;
  }

  public void setFormInstanceId(String string) {
    _formInstanceId = string;
  }

  public BigrFormDefinition getFormDefinition() {
    return _formDef;
  }

  public void setFormDefinition(BigrFormDefinition formDef) {
    _formDef = formDef;
  }

  public void addDataElement(DataElement dataElement) {
    if (_dataElements == null) {
      _dataElements = new ArrayList();
    }
    _dataElements.add(dataElement);
  }
  
  public Iterator getDataElements() {
    return (_dataElements == null) ? Collections.EMPTY_LIST.iterator() : _dataElements.iterator();
  }
  
  /**
   * Returns the KnowledgeCapture <code>FormInstance</code> that is derived from this BIGR
   * form instance. 
   *  
   * @return  The KnowledgeCapture <code>FormInstance</code>.
   */
  public FormInstance getKcFormInstance() {
    if (_kcFormInstance == null) {
      _kcFormInstance = new FormInstance();

      // Copy all of the simple properties from this BIGR form to the KC form.
      BigrBeanUtilsBean.SINGLETON.copyProperties(_kcFormInstance, this);

      // Copy all of the data elements.
      Iterator i = getDataElements();
      while (i.hasNext()) {
        _kcFormInstance.addDataElement((DataElement) i.next());
      }
    }      

    return _kcFormInstance;
  }
  /**
   * @return Returns the modificationDateTime.
   */
  public Timestamp getModificationDateTime() {
    return _modificationDateTime;
  }
  /**
   * @param modificationDateTime The modificationDateTime to set.
   */
  public void setModificationDateTime(Timestamp modificationDateTime) {
    _modificationDateTime = modificationDateTime;
  }
}
