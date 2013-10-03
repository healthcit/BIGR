package com.ardais.bigr.lims.beans;


/**
 * Remote interface for Enterprise Bean: LIMSDataValidator
 */
public interface LimsDataValidator extends javax.ejb.EJBObject {

/**
 * 
 * @return boolean
 * @param username String
 */
boolean isValidLimsUser(String username) throws java.rmi.RemoteException;

/**
 * 
 * @return boolean
 * @param sampleId String
 */
boolean doesSampleExist(String sampleId) throws java.rmi.RemoteException;

/**
 * 
 * @return boolean
 * @param slideId String
 */
boolean doesSlideExist(String slideId) throws java.rmi.RemoteException;

/**
 * 
 * @return boolean
 * @param evalutionId String
 */
boolean doesEvaluationExist(String evaluationId) throws java.rmi.RemoteException;

/**
 * 
 * @return boolean
 * @param evalutionId String
 */
boolean isEvaluationMigrated(String evaluationId) throws java.rmi.RemoteException;

/**
 * 
 * @return boolean
 * @param slideId String
 */
boolean isParentSamplePulled(String slideId) throws java.rmi.RemoteException;

/**
 * 
 * @return boolean
 * @param sampleId String
 */
boolean isSamplePulled(String sampleId) throws java.rmi.RemoteException;

/**
 * 
 * @return boolean
 * @param sampleId String
 */
boolean isSampleDiscordant(String sampleId) throws java.rmi.RemoteException;

/**
 * 
 * @return boolean
 * @param sampleId String
 */
boolean isSampleReleased(String sampleId) throws java.rmi.RemoteException;

/**
 * 
 * @return boolean
 * @param sampleId String
 */
boolean isSampleQCPosted(String sampleId) throws java.rmi.RemoteException;

/**
 * 
 * @return boolean
 * @param sampleId String
 */
boolean isSampleRequested(String sampleId) throws java.rmi.RemoteException;
}
