package com.ardais.bigr.pdc.beans;

import java.rmi.RemoteException;

import com.ardais.bigr.pdc.javabeans.AttachmentData;
import com.ardais.bigr.security.SecurityInfo;


/**
 * This is an Enterprise Java Bean Remote Interface
 */
public interface DDCDonor extends javax.ejb.EJBObject {

/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.DonorData
 * @param donorData com.ardais.bigr.pdc.javabeans.DonorData
 */
com.ardais.bigr.pdc.javabeans.DonorData buildDonorData(com.ardais.bigr.pdc.javabeans.DonorData donorData) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.ClinicalDataData
 * @param clinicalData com.ardais.bigr.pdc.javabeans.ClinicalDataData
 */
com.ardais.bigr.pdc.javabeans.ClinicalDataData createClinicalData(com.ardais.bigr.pdc.javabeans.ClinicalDataData clinicalData, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException;

/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.DonorData
 * @param donorData com.ardais.bigr.pdc.javabeans.DonorData
 */
com.ardais.bigr.pdc.javabeans.DonorData createDonorProfile(com.ardais.bigr.pdc.javabeans.DonorData donorData, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException;
/**
* 
* @return java.util.List
* @param donorData com.ardais.bigr.pdc.javabeans.AttachmentData
*/
java.util.List getAttachments(com.ardais.bigr.pdc.javabeans.DonorData dData) throws java.rmi.RemoteException;

/**
 * return boolean
 * @param deleteAttachId
 */
boolean deleteAttachment(String deleteAttachId) throws RemoteException;


/**
 * 
 * @param attachData
 * @param securityInfo
 * @return
 */
AttachmentData insertDonorAttachment(AttachmentData attachData, SecurityInfo securityInfo)throws RemoteException; 
/**
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.ClinicalDataData
 * @param clinicalData com.ardais.bigr.pdc.javabeans.ClinicalDataData
 */
com.ardais.bigr.pdc.javabeans.ClinicalDataData getClinicalData(com.ardais.bigr.pdc.javabeans.ClinicalDataData clinicalData) throws java.rmi.RemoteException;
/**
 * 
 * @return java.util.List
 * @param clinicalData com.ardais.bigr.pdc.javabeans.ClinicalDataData
 */
java.util.List getClinicalDataList(com.ardais.bigr.pdc.javabeans.ClinicalDataData clinicalData) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.ConsentData
 * @param consentData com.ardais.bigr.pdc.javabeans.ConsentData
 */
com.ardais.bigr.pdc.javabeans.ConsentData getConsentDetail(com.ardais.bigr.pdc.javabeans.ConsentData consentData) throws java.rmi.RemoteException;
/**
 * 
 * @return java.util.List
 * @param donorData com.ardais.bigr.pdc.javabeans.DonorData
 */
java.util.List getConsents(com.ardais.bigr.pdc.javabeans.DonorData donorData) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.DonorData
 * @param ardaisId java.lang.String
 */
com.ardais.bigr.pdc.javabeans.DonorData getDonorCaseSummary(java.lang.String ardaisId, boolean linkedCasesOnly) throws java.rmi.RemoteException;

/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.DonorData
 * @param donorData com.ardais.bigr.pdc.javabeans.DonorData
 */
com.ardais.bigr.pdc.javabeans.DonorData getDonorProfile(com.ardais.bigr.pdc.javabeans.DonorData donorData) throws java.rmi.RemoteException;

/**
 * @return java.lang.String
 * @param java.lang.String
 */
String getDonorAccount(String ardaisID) throws java.rmi.RemoteException;

boolean isPresent(com.ardais.bigr.pdc.javabeans.DonorData donorData) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.ConsentData
 * @param consentData com.ardais.bigr.pdc.javabeans.ConsentData
 */
com.ardais.bigr.pdc.javabeans.ConsentData updateCaseProfileNotes(com.ardais.bigr.pdc.javabeans.ConsentData consentData, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.ClinicalDataData
 * @param clinicalData com.ardais.bigr.pdc.javabeans.ClinicalDataData
 */
com.ardais.bigr.pdc.javabeans.ClinicalDataData updateClinicalData(com.ardais.bigr.pdc.javabeans.ClinicalDataData clinicalData, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException;

/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.DonorData
 * @param donorData com.ardais.bigr.pdc.javabeans.DonorData
 */
com.ardais.bigr.pdc.javabeans.DonorData updateDonorProfile(com.ardais.bigr.pdc.javabeans.DonorData donorData, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException;

}
