package com.gulfstreambio.kc.form.def;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiException;

/**
 * Holds query criteria for finding form definitions using the 
 * {@link FormDefinitionService#findFormDefinitions(FormDefinitionQueryCriteria) findFormDefinitions}
 * method.  Each distinct type of query criteria is ANDed with the others when performing the query  
 * to return form definitions in the <code>findFormDefinitions</code> method.  The treatment of 
 * multiple query criteria of the same type is specified in the methods to add each type of query
 * criteria.  Duplicate criteria for all types is ignored.
 */
public class FormDefinitionQueryCriteria implements Serializable {

  private Set _formDefinitionIds;
  private Set _formTypes;
  private Set _formNames;
  private Boolean _enabled;
  private Set _tags;

	private Set<String> roleIds = new HashSet<String>();
  
  /**
   * Creates a new <code>FormDefinitionQueryCriteria</code> instance.
   */
  public FormDefinitionQueryCriteria() {
    super();
  }

  Set getFormDefinitionIds() {
    return (_formDefinitionIds == null) ? Collections.EMPTY_SET : _formDefinitionIds;
  }
  
  /**
   * Adds a unique KnowledgeCapture form definition id as a query criteria.  All specified ids
   * will be ORed in the resulting query.  If the caller wishes to retrieve a single form 
   * definition by its unique id, method
   * {@link FormDefinitionService#findFormDefinitionById(String) findFormDefinitionById} can be
   * used.
   * 
   * @param  id  the unique KnowledgeCapture form definition id
   */
  public void addFormDefinitionId(String id) {
    if (_formDefinitionIds == null) {
      _formDefinitionIds = new HashSet();
    }
    _formDefinitionIds.add(id);      
  }

  Set getFormTypes() {
    return (_formTypes == null) ? Collections.EMPTY_SET : _formTypes;
  }
  
  /**
   * Adds the specified form type as a query criteria.  All specified types will be ORed in the 
   * resulting query.  
   * 
   * @param  type  the form type.  Must be one of the form type constants defined in 
   *         {@link FormDefinitionTypes}.
   * @throws  ApiException if the specified type is not a valid form type.  
   */
  public void addFormType(String type) {
    if (FormDefinitionTypes.isValidFormDefinitionType(type)) {
      if (_formTypes == null) {
        _formTypes = new HashSet();
      }
      _formTypes.add(type);      
    }
    else {
      throw new ApiException("Attempt to add invalid form type (" + type + ") as form definition query criteria");
    }
  }

  Set getFormNames() {
    return (_formNames == null) ? Collections.EMPTY_SET : _formNames;
  }

  /**
   * Adds the specified form name as a query criteria.  All specified types will be ORed in the 
   * resulting query.  
   * 
   * @param  name  the form name.
   */
  public void addFormName(String name) {
    if (_formNames == null) {
      _formNames = new HashSet();
    }
    _formNames.add(name);      
  }
  
  Boolean isEnabled() {
    return _enabled;
  }

  /**
   * Sets whether enabled or disabled form definitions should be returned as a query criteria.
   * If this method is not called, then both enabled and disabled form definitions will be
   * returned from a query using this <code>FormDefinitionQueryCriteria</code>.
   * 
   * @param  enabled  <code>true</code> to return only enabled form definitions;
   *                  <code>false</code> to return only disabled form definitions
   */
  public void setEnabled(boolean enabled) {
    _enabled = new Boolean(enabled);
  }

  Set getTags() {
    return (_tags == null) ? Collections.EMPTY_SET : _tags;
  }

  /**
   * Adds the specified tag as a query criteria.  All specified tags will be ANDed in the 
   * resulting query.
   * 
   * @param  tag  the <code>Tag</code>
   */
  public void addTag(Tag tag) {
    if (_tags == null) {
      _tags = new HashSet();
    }
    _tags.add(tag);      
  }

	public Set<String> getRoleIds()
	{
		return roleIds;
	}

	public void addRoleId(String id) {
		if (roleIds == null) {
			roleIds = new HashSet<String>();
		}
		roleIds.add(id);
	}
}
