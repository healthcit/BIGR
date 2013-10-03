package com.ardais.bigr.lims.btx;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.javabeans.IdList;

/**
 * Represents the details of a get path qc summary (Path QC) business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * Note: The following optional input fields are all used to filter (or sort) the sample ids returned by this transaction.
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setLogicalRepositories(IdList) logicalRepositories}: If specified, this list is used to filter out any samples
 * that do not belong to the specified logical repositories.</li>
 * <li>{@link #setPathologists(IdList) pathologists}: If specified, this list is used to filter out any samples
 * that do not have a reported evaluation created by the specified pathologist(s).</li>
 * <li>{@link #setReportedDateStart(Date) startDate}: If specified, a date representing the start of a date range
 * in which a sample was last path verified.</li>
 * <li>{@link #setReportedDateEnd(Date) endDate}: If specified, a date representing the end of a date range
 * in which a sample was last path verified.</li>
 * <li>{@link #setCaseIds(IdList) caseIds}: If specified, this list is used to filter out any samples
 * that do not belong to the specified case(s).</li>
 * <li>{@link #setSampleIds(IdList) sampleIds}: If specified, this list is used to filter out any samples
 * that are not in the specified list.</li>
 * <li>{@link #setSlideIds(IdList) slideIds}: If specified, this list is used to filter out any samples
 * that are not the parents of the specified slides.</li>
 * <li>{@link #setQCStatusInProcess(boolean) qcStatusInProcess}: If false, this boolean is used to filter out
 * samples that do not have a QC_STATUS value of QCINPROCESS.</li>
 * <li>{@link #setQCStatusAwaiting(boolean) qcStatusAwaiting}: If false, this boolean is used to filter out
 * samples that do not have a QC_STATUS value of QCAWAITING.</li>
 * <li>{@link #setIncludeQCPostedSamples(boolean) imcludeQCPosted}: If true, return samples
 * that are not pulled and are QCPosted.</li>
 * <li>{@link #setIncludeUnPVdSamples(boolean) includeUnPVd}: If true, return samples
 * that are not pulled and not PV'd.</li>
 * <li>{@link #setIncludeUnreleasedSamples(boolean) includeUnreleased}: If true, return samples
 * that are not pulled, are PV'd, but are not released.</li>
 * <li>{@link #setIncludeReleasedSamples(boolean) includeReleased}: If true, return samples
 * that are not pulled and are released.</li>
 * <li>{@link #setIncludePulledSamples(boolean) includePulled}: If true, return samples
 * that are pulled.</li>
 * <li>{@link #setSortColumn(String) sortColumn}: If specified, causes the samples to be returned sorted by
 * the specified column.
 * <li>{@link #setSortOrder(String) sortOrder}: If specified, causes the samples to be returned in either
 * ascending or descending order.
 * <li>{@link #setRetrieveCounts(boolean) retrieveCounts}: If true, returns the total number of samples
 * for each QC Status (pulled, pv'd, released, etc). True by default.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul> 
 * <li>{@link #setUnPvedCount(int) pvCount}: The number of unPVed samples in the db.</li>
 * <li>{@link #setQCPostedCount(int) pvCount}: The number of QCPosted samples in the db.</li>
 * <li>{@link #setPvCount(int) pvCount}: The number of PVed samples in the db.</li>
 * <li>{@link #setReleasedCount(int) releasedCount}: The number of released samples in the db.</li>
 * <li>{@link #setPulledCount(int) pulledCount}: The number of pulled samples in the db.</li>
 * <li>{@link #setIds(IdList) ids}: A list of ids for the samples matching the specified filters
 * </ul>
 */
public class BTXDetailsGetPathQCSampleSummary extends BTXDetails implements Serializable {
	private static final long serialVersionUID = -610060250366665361L;

	//input data members
  private IdList _logicalRepositories;
	private IdList _pathologists;
	private Date _reportedDateStart;
	private Date _reportedDateEnd;
	private IdList _caseIds;
	private IdList _sampleIds;
	private IdList _slideIds;
	private boolean _qcStatusInProcess;
	private boolean _qcStatusAwaiting;
	private boolean _includeUnPVdSamples;
	private boolean _includeUnreleasedSamples;
	private boolean _includeReleasedSamples;
	private boolean _includePulledSamples;
	private boolean _includeQCPostedSamples;
	private String _sortColumn;
	private String _sortOrder;
	private boolean _retrieveCounts = true;
	
	//output data members
	private int _qcPostedCount;
	private int _unPVedCount;
	private int _pvCount;
	private int _releasedCount;
	private int _pulledCount;
	private IdList _ids;
	
	/**
	 * Constructor for BTXDetailsGetPathQCSampleSummary.
	 */
	public BTXDetailsGetPathQCSampleSummary() {
		super();
	}

	/**
	 * Returns the caseIds.
	 * @return IdList
	 */
	public IdList getCaseIds() {
		return _caseIds;
	}

	/**
	 * Returns the includePulledSamples.
	 * @return boolean
	 */
	public boolean isIncludePulledSamples() {
		return _includePulledSamples;
	}

	/**
	 * Returns the includeReleasedSamples.
	 * @return boolean
	 */
	public boolean isIncludeReleasedSamples() {
		return _includeReleasedSamples;
	}

	/**
	 * Returns the includeUnreleasedSamples.
	 * @return boolean
	 */
	public boolean isIncludeUnreleasedSamples() {
		return _includeUnreleasedSamples;
	}

	/**
	 * Returns the includeUnPVdSamples.
	 * @return boolean
	 */
	public boolean isIncludeUnPVdSamples() {
		return _includeUnPVdSamples;
	}

	/**
	 * Returns the includeQCPostedSamples.
	 * @return boolean
	 */
	public boolean isIncludeQCPostedSamples() {
		return _includeQCPostedSamples;
	}
	
	/**
	 * Returns the qcStatusAwaiting.
	 * @return boolean
	 */
	public boolean isQcStatusAwaiting() {
		return _qcStatusAwaiting;
	}

	/**
	 * Returns the qcStatusInProcess.
	 * @return boolean
	 */
	public boolean isQcStatusInProcess() {
		return _qcStatusInProcess;
	}

  /**
   * Returns the logical repositories
   * @return IdList
   */
  public IdList getLogicalRepositories() {
    return _logicalRepositories;
  }

	/**
	 * Returns the pathologists.
	 * @return IdList
	 */
	public IdList getPathologists() {
		return _pathologists;
	}

	/**
	 * Returns the ids of the samples matching the query.
	 * @return IdList
	 */
	public IdList getIds() {
		return _ids;
	}

	/**
	 * Returns the pulledCount.
	 * @return int
	 */
	public int getPulledCount() {
		return _pulledCount;
	}

	/**
	 * Returns the pvCount.
	 * @return int
	 */
	public int getPvCount() {
		return _pvCount;
	}

	/**
	 * Returns the reportedDateEnd.
	 * @return Date
	 */
	public Date getReportedDateEnd() {
		return _reportedDateEnd;
	}

	/**
	 * Returns the reportedDateStart.
	 * @return Date
	 */
	public Date getReportedDateStart() {
		return _reportedDateStart;
	}

	/**
	 * Returns the releasedCount.
	 * @return int
	 */
	public int getReleasedCount() {
		return _releasedCount;
	}

	/**
	 * Returns the sampleIds.
	 * @return IdList
	 */
	public IdList getSampleIds() {
		return _sampleIds;
	}

	/**
	 * Returns the slideIds.
	 * @return IdList
	 */
	public IdList getSlideIds() {
		return _slideIds;
	}

	/**
	 * Returns the sortColumn.
	 * @return String
	 */
	public String getSortColumn() {
		return _sortColumn;
	}

	/**
	 * Returns the sortOrder.
	 * @return String
	 */
	public String getSortOrder() {
		return _sortOrder;
	}

	/**
	 * Returns the number of samples returned.
	 * @return String
	 */
	public int getReturnedSampleCount() {
		if (getIds() != null) {
			return getIds().size();
		}
		else {
			return 0;
		}
	}

	/**
	 * Returns the unPVedCount.
	 * @return int
	 */
	public int getUnPVedCount() {
		return _unPVedCount;
	}

	/**
	 * Returns the qcPostedCount.
	 * @return int
	 */
	public int getQcPostedCount() {
		return _qcPostedCount;
	}

	/**
	 * Returns the retrieveCounts.
	 * @return boolean
	 */
	public boolean isRetrieveCounts() {
		return _retrieveCounts;
	}

	/**
	 * Sets the caseIds.
	 * @param caseIds The caseIds to set
	 */
	public void setCaseIds(IdList caseIds) {
		_caseIds = caseIds;
	}

	/**
	 * Sets the includePulledSamples.
	 * @param includePulledSamples The includePulledSamples to set
	 */
	public void setIncludePulledSamples(boolean includePulledSamples) {
		_includePulledSamples = includePulledSamples;
	}

	/**
	 * Sets the includeReleasedSamples.
	 * @param includeReleasedSamples The includeReleasedSamples to set
	 */
	public void setIncludeReleasedSamples(boolean includeReleasedSamples) {
		_includeReleasedSamples = includeReleasedSamples;
	}

	/**
	 * Sets the includeUnreleasedSamples.
	 * @param includeUnreleasedSamples The includeUnreleasedSamples to set
	 */
	public void setIncludeUnreleasedSamples(boolean includeUnreleasedSamples) {
		_includeUnreleasedSamples = includeUnreleasedSamples;
	}

	/**
	 * Sets the includeUnPVdSamples.
	 * @param includeUnPVdSamples The includeUnPVdSamples to set
	 */
	public void setIncludeUnPVdSamples(boolean includeUnPVdSamples) {
		_includeUnPVdSamples = includeUnPVdSamples;
	}

	/**
	 * Sets the includeQCPostedSamples.
	 * @param includeQCPostedSamples The includeQCPostedSamples to set
	 */
	public void setIncludeQCPostedSamples(boolean includeQCPostedSamples) {
		_includeQCPostedSamples = includeQCPostedSamples;
	}
	
	/**
	 * Sets the qcStatusAwaiting.
	 * @param qcStatusAwaiting The qcStatusAwaiting to set
	 */
	public void setQcStatusAwaiting(boolean qcStatusAwaiting) {
		_qcStatusAwaiting = qcStatusAwaiting;
	}

	/**
	 * Sets the qcStatusInProcess.
	 * @param qcStatusInProcess The qcStatusInProcess to set
	 */
	public void setQcStatusInProcess(boolean qcStatusInProcess) {
		_qcStatusInProcess = qcStatusInProcess;
	}

  /**
   * Sets the logical repositories
   * @param logicalRepositories  The logical repositories to set
   */
  public void setLogicalRepositories(IdList logicalRepositories) {
    _logicalRepositories = logicalRepositories;
  }

	/**
	 * Sets the pathologists.
	 * @param pathologists The pathologists to set
	 */
	public void setPathologists(IdList pathologists) {
		_pathologists = pathologists;
	}

	/**
	 * Sets the ids.
	 * @param ids The ids to set
	 */
	public void setIds(IdList ids) {
		_ids = ids;
	}

	/**
	 * Sets the pulledCount.
	 * @param pulledCount The pulledCount to set
	 */
	public void setPulledCount(int pulledCount) {
		_pulledCount = pulledCount;
	}

	/**
	 * Sets the pvCount.
	 * @param pvCount The pvCount to set
	 */
	public void setPvCount(int pvCount) {
		_pvCount = pvCount;
	}

	/**
	 * Sets the reportedDateEnd.
	 * @param reportedDateEnd The reportedDateEnd to set
	 */
	public void setReportedDateEnd(Date reportedDateEnd) {
		_reportedDateEnd = reportedDateEnd;
	}

	/**
	 * Sets the reportedDateStart.
	 * @param reportedDateStart The reportedDateStart to set
	 */
	public void setReportedDateStart(Date reportedDateStart) {
		_reportedDateStart = reportedDateStart;
	}

	/**
	 * Sets the releasedCount.
	 * @param releasedCount The releasedCount to set
	 */
	public void setReleasedCount(int releasedCount) {
		_releasedCount = releasedCount;
	}

	/**
	 * Sets the sampleIds.
	 * @param sampleIds The sampleIds to set
	 */
	public void setSampleIds(IdList sampleIds) {
		_sampleIds = sampleIds;
	}

	/**
	 * Sets the slideIds.
	 * @param slideIds The slideIds to set
	 */
	public void setSlideIds(IdList slideIds) {
		_slideIds = slideIds;
	}

	/**
	 * Sets the sortColumn.
	 * @param sortColumn The sortColumn to set
	 */
	public void setSortColumn(String sortColumn) {
		_sortColumn = sortColumn;
	}

	/**
	 * Sets the sortOrder.
	 * @param sortOrder The sortOrder to set
	 */
	public void setSortOrder(String sortOrder) {
		_sortOrder = sortOrder;
	}

	/**
	 * Sets the unPVedCount.
	 * @param unPVedCount The unPVedCount to set
	 */
	public void setUnPVedCount(int unPVedCount) {
		_unPVedCount = unPVedCount;
	}

	/**
	 * Sets the qcPostedCount.
	 * @param qcPostedCount The qcPostedCount to set
	 */
	public void setQcPostedCount(int qcPostedCount) {
		_qcPostedCount = qcPostedCount;
	}

	/**
	 * Sets the retrieveCounts.
	 * @param retrieveCounts The retrieveCounts to set
	 */
	public void setRetrieveCounts(boolean retrieveCounts) {
		_retrieveCounts = retrieveCounts;
	}

	/**
	 * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
	 * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
	 * method.  Since this transaction is not logged, there won't be any history so this 
	 * method should never be called.  If for some reason this method is called it 
	 * returns a message to the effect that it was called in error.
	 *
	 * @return an HTML string that defines how the transaction details are presented
	 * in a transaction-history web page
	 */
  protected String doGetDetailsAsHTML() {
		String msg = "BTXDetailsGetPathQCSampleSummary.doGetDetailsAsHTML() method called in error!";
		return msg;
	}
	
	/**
	 * Return the business transaction type code for the transaction that this
	 * class represents.
	 *
	 * @return the transaction type code.
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_PATH_QC_SUMMARY;
	}
	
	/**
	 * Return a set of the object ids of the objects that are directly involved
	 * in this transaction.  This set does not contain the ids of objects that
	 * are considered to be indirectly involved in the transaction, and it does
	 * not include the user id of the user who performed the transaction.
	 * <p>
	 * For example, a transaction that scans a box of samples directly involves the box
	 * object and each of the sample objects, and indirectly involves the
	 * asm, asm form, case and donor objects for each sample.
	 *
	 * @return the set of directly involved object ids.
	 */
	public Set getDirectlyInvolvedObjects() {
		Set set = new HashSet();
		return set;
	}

}
