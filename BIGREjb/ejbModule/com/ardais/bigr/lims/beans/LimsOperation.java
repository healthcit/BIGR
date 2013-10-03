package com.ardais.bigr.lims.beans;
import java.rmi.RemoteException;
import java.util.List;

import com.ardais.bigr.javabeans.AsmData;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.lims.javabeans.SlideData;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationSampleData;
import java.sql.Timestamp;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
/**
 * Remote interface for Enterprise Bean: LimsOperation
 */
public interface LimsOperation extends javax.ejb.EJBObject {
	/**
	 	 * Creates a new <code>SlideData</code> and populates caseId, sampleId, 
	 	 * ASM position, slide number fields for slideId.
	 	 *
	 	 * @param  <code>String</code> slideId
	 	 * @return  A <code>SlideData</code> bean.
	 	 */
	public SlideData getPrintLabelData(String slideId)
		throws java.rmi.RemoteException;
		
	/**
 	 * Returns the sample id to which a slide belongs
 	 *
 	 * @param  <code>String</code> slideId
 	 * @return  A <code>String</code>.
 	 */
	public String getSampleIdForSlide(String slideId)
		throws java.rmi.RemoteException;
	    
  /**
   * Returns the bms value for the sample to which a slide belongs
   *
   * @param  <code>String</code> slideId
   * @return  A <code>String</code>.
   */
  public String getBmsValueForSlide(String slideId)
    throws java.rmi.RemoteException;
  
	/**
 	 * Returns the consent information related to a sample
 	 *
 	 * @param  <code>String</code> sampleId
 	 * @return  A <code>ConsentData</code> data bean.
 	 */
	public ConsentData getConsentData(String sampleId)
		throws java.rmi.RemoteException;
	
	/**
 	 * Returns the sample information for a sample
 	 *
 	 * @param  <code>String</code> sampleId
 	 * @return  A <code>SampleData</code> data bean.
 	 */
	public SampleData getSampleData(String sampleId)
		throws java.rmi.RemoteException;
	
	/**
 	 * Returns the asm information related to a sample
 	 *
 	 * @param  <code>String</code> sampleId
 	 * @return  An <code>AsmData</code> data bean.
 	 */
	public AsmData getAsmData(String sampleId)
		throws java.rmi.RemoteException;
		
	/**
 	 * Returns the PDC path report section information related to a sample
 	 *
 	 * @param  <code>String</code> sampleId
 	 * @return  A <code>List of PathReportSectionData</code> data beans.
 	 */
	public List getPdcPathReportSectionData(String sampleId)
		throws java.rmi.RemoteException;
		
	/**
 	 * Returns the information for the reported pathology evaluation for a sample
 	 *
 	 * @param  <code>String</code> sampleId
 	 * @return  A <code>PathologyEvaluationData</code> data bean.
 	 */
	public PathologyEvaluationData getReportedPathologyEvaluation(String sampleId)
		throws java.rmi.RemoteException;
	
	/**
 	 * Returns the information for a specified pathology evaluation
 	 *
 	 * @param  <code>String</code> evaluationId
 	 * @return  A <code>PathologyEvaluationData</code> data bean.
 	 */
	public PathologyEvaluationData getPathologyEvaluationData(String evaluationId)
		throws java.rmi.RemoteException;
		
	/**
 	 * Returns the information for a specified pathology evaluation, 
 	 * including feature lists.  This method should only be invoked when
 	 * it is necessary to have the feature lists populated, which is an
 	 * infrequent occurrence - it is currently only necessary when showing the
 	 * Ardais PV report and when creating a new evaluation from an existing one.  
 	 * Most times the getPathologyEvaluationData(String evaluationId)
 	 * method should be sufficient.
 	 *
 	 * @param  <code>String</code> evaluationId
 	 * @return  A <code>PathologyEvaluationData</code> data bean.
 	 */
	public PathologyEvaluationData getPathologyEvaluationDataWithFeatureLists(String evaluationId)
		throws java.rmi.RemoteException;
		
	/**
 	 * Returns all slides created from a sample, along with the images for that slide
 	 *
 	 * @param  <code>String</code> sampleId
 	 * @return  A <code>List of SlideData</code> data beans.
 	 */
	public List getSlidesForSample(String sampleId)
		throws java.rmi.RemoteException;
		
	/**
 	 * Returns all child sample ids from a parent sample.
 	 *
 	 * @param  <code>String</code> sampleId
 	 * @return  A <code>List of Strings</code> containing child sample ids.
 	 */
	public List getChildSampleIdsForSample(String sampleId)
		throws java.rmi.RemoteException;

	/**
 	 * Returns all evaluations created for a sample
 	 *
 	 * @param  <code>String</code> sampleId
 	 * @return  A <code>List of PathologyEvaluationData</code> data beans.
 	 */
	public List getPathologyEvaluationsForSample(String sampleId)
		throws java.rmi.RemoteException;
	/**
 	 * Returns all pathologist that have performed an evaluation
 	 *
 	 * 
 	 * @return  A List of Ardais User Ids 
 	 */
	public List getPathologistList() 
		throws RemoteException;	
	/**
	 * Returns a new PathologyEvaluationSampleData data bean.  This routine
	 * is called from any transaction that returns sample data for a pathology operation (currently
	 * CreatePathologyEvaluationPrepare and GetPathologyEvaluations), so make sure all data
	 * required for those screens is populated 
	 * @param String sampleId
	 * @return <code>PathologyEvaluationSampleData</code>
	 */
	public PathologyEvaluationSampleData getEvaluationSampleData(String sampleId)
		throws java.rmi.RemoteException;
	/**
	 	 * Returns Throughput report details of user/all users
	 	 * within specified dates. 
	 	 * @param  <code>String</code> userId.
	 	 * @param <code>Timestamp</code> fromDate.
	 	 * @param <code>Timestamp</code> toDate.
	 	 * @return <code>LegalValueSet</code>.
	 	 */
	public LegalValueSet getPvThroughputReport(
		String userId,
		Timestamp fromDate,
		Timestamp toDate)
		throws java.rmi.RemoteException;
	/**
	     * Updates rows which are not fixed in ARD_OTHER_CODE_EDITS table for 
	     * pathology evaluation created for sampleId with specified statusFlag.
	     * @param sampleId
	     * @param statusFlag
	     */
	public void updateLimsOCEDataStatus(
		String sampleId,
		String statusFlag,
		String userId)
		throws java.rmi.RemoteException;
    
  /**
     * Returns all incidents of a given type created for a sample
     * since a given date
     *
     * @param  <code>String</code> sampleId
     * @param  <code>String</code> action
     * @param  <code>Timestamp</code> fromDate
     * @return  A <code>List of IncidentReportLineItem</code> data beans.
     */
  public List getIncidentsForSample(String sampleId, String action, Timestamp fromDate) throws java.rmi.RemoteException;
}
