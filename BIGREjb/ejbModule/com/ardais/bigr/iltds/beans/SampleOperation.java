package com.ardais.bigr.iltds.beans;

import java.rmi.RemoteException;
import java.util.List;

import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.pdc.javabeans.AttachmentData;
import com.ardais.bigr.security.SecurityInfo;

/**
 * Remote interface for Enterprise Bean: SampleOperation
 */
public interface SampleOperation extends javax.ejb.EJBObject {

  /**
   * Given a list of sample ids, return null if all of the sample ids
   * potentially describe a real sample and satisfy additional rules
   * that make the suitable for the contents of a box in a box scan (either
   * a regular DI or Ardais box scan, or a sample check-out box scan).
   * The extra rules that are checked are that there
   * are no duplicates in the list, the samples are all the same format,
   * there is at least one sample in the list, and there are at most
   * as many samples in the list as can fit in a box.
   * 
   * <p>If, in addition, the
   * samplesMustExist flag is true, then this also checks that the samples
   * all currently have records in the database.  If there are any
   * errors, then a string describing at least one of the problems that
   * was detected will be returned.
   * 
   * <p>If, in addition, the enforceRequestedSampleRestrictions flag is true, 
   * then this also checks that the samples are not in violation of any rules
   * around scanning samples on requests.
   * 
   * @param sampleIds a list of sample ids to check
   * @param samplesMustExist if true, also check that the samples exist
   *     in the database
   * @param enforceRequestedSampleRestrictions if true, enforce rules around requested samples
   * @param enforceStorageTypes if true, enforces the rule that samples must have a common storage type
   * @return null or an error string as described above
   */
  String validSamplesForBoxScan(
    List sampleIds,
    boolean samplesMustExist,
    boolean enforceRequestedSampleRestrictions,
    boolean enforceStorageTypes,
    SecurityInfo securityInfo,
    BoxLayoutDto boxLayoutDto)
    throws java.rmi.RemoteException;

  /**
   * Identify what samples (if any) contained in a list of sample ids are non-local.
   *
   * @param  sampleIds  a list of sample ids
   * @param  securityInfo  the SecurityInfo object for the currently logged in user
   * @param  ignoreSamplesWithNoLocation  a boolean to indicate if samples with no location
   * are to be ignored (if true, they will not be returned as non-local).
   * @param  samplesMustBeInRepository a boolean to indicate if the samples must be in
   * a box at the current user's location.  True means they do, false means the samples
   * just have to have a last known location id equal to the current users location
   * @return List  a list of the samples in the sampleIds list that are non-local
   * to the currently logged in user
   */
  public List identifyNonLocalSamples(
    List sampleIds,
    SecurityInfo securityInfo,
    boolean ignoreSamplesWithNoLocation,
    boolean samplesMustBeInRepository)
    throws java.rmi.RemoteException;
  
  /**
   * 
   * @param attachData
   * @param securityInfo
   * @return
   */
  public AttachmentData insertSampleAttachment(AttachmentData attachData, SecurityInfo securityInfo)throws RemoteException; 
  
}
