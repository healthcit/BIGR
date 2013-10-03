package com.gulfstreambio.kc.form;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Holds query criteria for finding form definitions using the 
 * {@link FormInstanceService#findFormInstances(FormInstanceQueryCriteria) findFormInstances}
 * method.  Each distinct type of query criteria is ANDed with the others when performing the query  
 * to return form instances in the <code>findFormInstances</code> method.  The treatment of 
 * multiple query criteria of the same type is specified in the methods to add each type of query
 * criteria.  Duplicate criteria for all types is ignored.
 */
public class FormInstanceQueryCriteria implements Serializable {

  private Set _formInstanceIds;
  private Set _formDefinitionIds;
  private Set _domainObjectIds;
  private Set _domainObjectTypes;
  
  /**
   * Creates a new <code>FormInstanceQueryCriteria</code> instance.
   */
  public FormInstanceQueryCriteria() {
    super();
  }

  Set getFormInstanceIds() {
    return (_formInstanceIds == null) ? Collections.EMPTY_SET : _formInstanceIds;
  }
  
  /**
   * Adds a unique KnowledgeCapture form instance id as a query criteria.  All specified ids
   * will be ORed in the resulting query.  If the caller wishes to retrieve a single form 
   * instance by its unique id, method
   * {@link FormInstanceService#findFormInstanceById(String) findFormInstanceById} can be
   * used.
   * 
   * @param  id  the unique KnowledgeCapture form instance id
   */
  public void addFormInstanceId(String id) {
    if (_formInstanceIds == null) {
      _formInstanceIds = new HashSet();
    }
    _formInstanceIds.add(id);      
  }

  Set getFormDefinitionIds() {
    return (_formDefinitionIds == null) ? Collections.EMPTY_SET : _formDefinitionIds;
  }
  
  /**
   * Adds a unique KnowledgeCapture form definition id as a query criteria.  All specified ids
   * will be ORed in the resulting query.  Using this query criteria will return all form instances
   * that were created from the specified form definition ids.  
   * 
   * @param  id  the unique KnowledgeCapture form definition id
   */
  public void addFormDefinitionId(String id) {
    if (_formDefinitionIds == null) {
      _formDefinitionIds = new HashSet();
    }
    _formDefinitionIds.add(id);      
  }

  Set getDomainObjectIds() {
    return (_domainObjectIds == null) ? Collections.EMPTY_SET : _domainObjectIds;
  }
  
  /**
   * Adds the specified domain object id as a query criteria.  All specified ids will be ORed in 
   * the resulting query.  
   * 
   * @param  id  the domain object id
   */
  public void addDomainObjectId(String id) {
    if (_domainObjectIds == null) {
      _domainObjectIds = new HashSet();
    }
    _domainObjectIds.add(id);      
  }

  Set getDomainObjectTypes() {
    return (_domainObjectTypes == null) ? Collections.EMPTY_SET : _domainObjectTypes;
  }
  
  /**
   * Adds the specified domain object type as a query criteria.  All specified types will be ORed 
   * in the resulting query.  
   * 
   * @param  type  the domain object type
   */
  public void addDomainObjectType(String type) {
    if (_domainObjectTypes == null) {
      _domainObjectTypes = new HashSet();
    }
    _domainObjectTypes.add(type);      
  }
}
