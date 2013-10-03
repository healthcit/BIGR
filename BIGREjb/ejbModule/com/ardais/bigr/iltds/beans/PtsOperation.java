package com.ardais.bigr.iltds.beans;

import javax.ejb.EJBObject;

import com.ardais.bigr.security.SecurityInfo;

/**
 * <code>PtsOperation</code> is the remote interface for the PtsOperation bean.
 */
public interface PtsOperation extends EJBObject {

  /**
   * 
   * @return void
   * @param samples java.lang.String []
   * @param lineItemId java.lang.String
   * @param projectId java.lang.String
   */
  void addSamplesToProject(
    java.lang.String[] samples,
    java.lang.String lineItemId,
    java.lang.String projectId)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return java.util.List
   * @param query com.ardais.bigr.es.javabeans.QueryBean
   */
  java.util.List availableInvQuery(com.ardais.bigr.es.javabeans.QueryBean query, SecurityInfo security)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.SampleQueryBuilder
   */
  com.ardais.bigr.iltds.assistants.SampleQueryBuilder buildSamplesToRemoveQuery()
    throws java.rmi.RemoteException;

  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.LineItemDataBean
   * @param dataBean com.ardais.bigr.iltds.assistants.LineItemDataBean
   */
  com.ardais.bigr.iltds.assistants.LineItemDataBean createLineItem(
    com.ardais.bigr.iltds.assistants.LineItemDataBean dataBean)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.ProjectDataBean
   * @param dataBean com.ardais.bigr.iltds.assistants.ProjectDataBean
   */
  com.ardais.bigr.iltds.assistants.ProjectDataBean createProject(
    com.ardais.bigr.iltds.assistants.ProjectDataBean dataBean)
    throws javax.ejb.DuplicateKeyException, java.rmi.RemoteException;

  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.WorkOrderDataBean
   * @param dataBean com.ardais.bigr.iltds.assistants.WorkOrderDataBean
   */
  com.ardais.bigr.iltds.assistants.WorkOrderDataBean createWorkOrder(
    com.ardais.bigr.iltds.assistants.WorkOrderDataBean dataBean)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return java.util.List
   * @param lineItemId java.lang.String
   */
  java.util.List getCompleteSampleInfo(java.lang.String lineItemId)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.LineItemDataBean
   * @param lineItemId java.lang.String
   */
  com.ardais.bigr.iltds.assistants.LineItemDataBean getLineItem(java.lang.String lineItemId)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return java.util.List
   * @param projectId java.lang.String
   */
  java.util.List getLineItems(java.lang.String projectId) throws java.rmi.RemoteException;

  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.ProjectDataBean
   * @param projectId java.lang.String
   */
  com.ardais.bigr.iltds.assistants.ProjectDataBean getProject(java.lang.String projectId)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.ProjectDataBean
   * @param projectName java.lang.String
   */
  com.ardais.bigr.iltds.assistants.ProjectDataBean getProjectByName(java.lang.String projectName)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return java.util.List
   * @param accountKey java.lang.String
   */
  java.util.List getProjects(java.lang.String accountKey) throws java.rmi.RemoteException;


  /**
   * 
   * @return java.util.List
   * @param projectId java.lang.String
   */
  java.util.List getSamplesOnHold(java.lang.String projectId) throws java.rmi.RemoteException;


  /**
   * 
   * @return java.util.List
   * @param projectId java.lang.String
   */
  java.util.List getSamplesToAddToHold(java.lang.String projectId) throws java.rmi.RemoteException;

  /**
   * 
   * @return java.util.List
   * @param projectId java.lang.String
   */
  java.util.List getSamplesToRemove(java.lang.String projectId) throws java.rmi.RemoteException;

  /**
   * 
   * @return int
   * @param projectId java.lang.String
   */
  int getTotalSamples(java.lang.String projectId) throws java.rmi.RemoteException;

  /**
   * 
   * @return int
   * @param projectId java.lang.String
   */
  int getTotalSamplesOnHold(java.lang.String projectId) throws java.rmi.RemoteException;

  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.WorkOrderDataBean
   * @param workOrderId java.lang.String
   */
  com.ardais.bigr.iltds.assistants.WorkOrderDataBean getWorkOrder(java.lang.String workOrderId)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return java.util.List
   * @param projectId java.lang.String
   */
  java.util.List getWorkOrders(java.lang.String projectId) throws java.rmi.RemoteException;

  java.lang.String addSamplesToHoldForProject(
    java.lang.String[] samples,
    java.lang.String user,
    java.lang.String account)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return int
   * @param samples java.lang.String []
   * @param accounts java.lang.String []
   * @param users java.lang.String []
   */
  int removeSamplesFromHold(
    java.lang.String[] samples,
    java.lang.String[] accounts,
    java.lang.String[] users)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return int
   * @param projectId java.lang.String
   * @param lineItemIds java.lang.String []
   * @param sampleIds java.lang.String []
   */
  int removeSamplesFromProject(
    java.lang.String projectId,
    java.lang.String[] lineItemIds,
    java.lang.String[] sampleIds)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.LineItemDataBean
   * @param dataBean com.ardais.bigr.iltds.assistants.LineItemDataBean
   */
  com.ardais.bigr.iltds.assistants.LineItemDataBean updateLineItem(
    com.ardais.bigr.iltds.assistants.LineItemDataBean dataBean)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.ProjectDataBean
   * @param dataBean com.ardais.bigr.iltds.assistants.ProjectDataBean
   */
  com.ardais.bigr.iltds.assistants.ProjectDataBean updateProject(
    com.ardais.bigr.iltds.assistants.ProjectDataBean dataBean)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.WorkOrderDataBean
   * @param dataBean com.ardais.bigr.iltds.assistants.WorkOrderDataBean
   */
  com.ardais.bigr.iltds.assistants.WorkOrderDataBean updateWorkOrder(
    com.ardais.bigr.iltds.assistants.WorkOrderDataBean dataBean)
    throws java.rmi.RemoteException;
}
