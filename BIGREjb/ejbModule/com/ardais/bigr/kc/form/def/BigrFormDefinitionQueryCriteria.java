package com.ardais.bigr.kc.form.def;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.form.def.FormDefinitionQueryCriteria;
import com.gulfstreambio.kc.form.def.FormDefinitionTypes;
import com.gulfstreambio.kc.form.def.Tag;

/**
 * Holds query criteria for finding form definitions.  Can convert itself to a KnowledgeCapture
 * <code>FormDefinitionQueryCriteria</code> for use in the KnowledgeCapture Form Defintion API.
 */
public class BigrFormDefinitionQueryCriteria implements Serializable {

  private Set _formDefinitionIds;
  private Set _formTypes;
  private Set _formNames;
  private String _account;
  private String _user;
  private Set _objectTypes;
  private String _use;
  private Boolean _enabled;
  private Set<String> roleIds;

  /**
   * Creates a new <code>BigrFormDefinitionQueryCriteria</code>.
   */
  public BigrFormDefinitionQueryCriteria() {
    super();
  }

  public Set getFormDefinitionIds() {
    return (_formDefinitionIds == null) ? Collections.EMPTY_SET : _formDefinitionIds;
  }
  
  public void addFormDefinitionId(String id) {
    if (_formDefinitionIds == null) {
      _formDefinitionIds = new HashSet();
    }
    _formDefinitionIds.add(id);      
  }

  public Set getFormTypes() {
    return (_formTypes == null) ? Collections.EMPTY_SET : _formTypes;
  }
  
  public void addFormType(String type) {
    if (!FormDefinitionTypes.isValidFormDefinitionType(type)) {
      throw new ApiException("Attempt to add invalid form definition type (" + type + ") in BigrFormDefinitionQueryCriteria");
    }
    if (_formTypes == null) {
      _formTypes = new HashSet();
    }
    _formTypes.add(type);      
  }

  public Set getFormNames() {
    return (_formNames == null) ? Collections.EMPTY_SET : _formNames;
  }

  public void addFormName(String name) {
    if (_formNames == null) {
      _formNames = new HashSet();
    }
    _formNames.add(name);      
  }
  
  public Boolean isEnabled() {
    return _enabled;
  }

  public void setEnabled(boolean enabled) {
    _enabled = new Boolean(enabled);
  }

  public String getAccount() {
    return _account;
  }
  
  public void setAccount(String account) {
    _account = account;
  }
  
  public Set getObjectTypes() {
    return (_objectTypes == null) ? Collections.EMPTY_SET : _objectTypes;
  }
  
  public void addObjectType(String objectType) {
    if (!BigrFormDefinition.isValidObjectType(objectType)) {
      throw new ApiException("Attempt to add invalid object type (" + objectType + ") in BigrFormDefinitionQueryCriteria");
    }
    if (_objectTypes == null) {
      _objectTypes = new HashSet();
    }
    _objectTypes.add(objectType);      
  }
  
  public String getUse() {
    return _use;
  }
  
  public void setUse(String use) {
    if (!BigrFormDefinition.isValidUse(use)) {
      throw new ApiException("Attempt to set invalid use (" + use + ") in BigrFormDefinitionQueryCriteria");
    }
    _use = use;
  }  

  public String getUser() {
    return _user;
  }
  
  public void setUser(String user) {
    _user = user;
  }

	public Set<String> getRoleIds()
	{
		return (roleIds == null) ? new HashSet<String>() : roleIds;
	}

	public void setRoleIds(Set<String> roleIds)
	{
		this.roleIds = roleIds;
	}

  public FormDefinitionQueryCriteria toKcFormDefinitionQueryCriteria() {
    FormDefinitionQueryCriteria kcCriteria = new FormDefinitionQueryCriteria();
    Iterator i = getFormDefinitionIds().iterator();
    while (i.hasNext()) {
      kcCriteria.addFormDefinitionId((String) i.next());      
    }
    i = getFormNames().iterator();
    while (i.hasNext()) {
      kcCriteria.addFormName((String) i.next());      
    }
    i = getFormTypes().iterator();
    while (i.hasNext()) {
      kcCriteria.addFormType((String) i.next());      
    }
    if (isEnabled() != null) {
      kcCriteria.setEnabled(isEnabled().booleanValue());
    }
    String account = getAccount();
    if (!ApiFunctions.isEmpty(account)) {
      kcCriteria.addTag(new Tag(TagTypes.ACCOUNT, account));
    }
    String use = getUse();
    if (!ApiFunctions.isEmpty(use)) {
      kcCriteria.addTag(new Tag(TagTypes.USES, use));
    }
    String user = getUser();
    if (!ApiFunctions.isEmpty(user)) {
      kcCriteria.addTag(new Tag(TagTypes.USERNAME, user));
    }
    i = getObjectTypes().iterator();
    while (i.hasNext()) {
      kcCriteria.addTag(new Tag(TagTypes.DOMAIN_OBJECT, (String) i.next()));
    }
	  i = getRoleIds().iterator();
	  while (i.hasNext()) {
		  kcCriteria.addRoleId((String)i.next());
	  }
    return kcCriteria;
  }
}
