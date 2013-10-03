package com.ardais.bigr.lims.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationSampleData;

/**
 * Represents the details of a get sample pathology (manage pathology evaluations) business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setSampleId(String) sampleId}: The id of the sample for which we're retrieving this information.</li>
 * <li>{@link #setSlideId (String) slideId}: The id of the slide belonging to the sample for which we're
 * retrieving this information.</li>
 * </ul>
 * Note: Either sampleId or slideId is required, but not both.  If both are provided, sampleId is used. If
 * neither are provided are error occurs.
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setPathologyEvaluationSampleData(PathologyEvaluationSampleData) pathologyEvaluationSampleData}: 
 * An object containing information about the sample for which we're retrieving this information.  Used by the
 * front end to display info about that sample.
 * <li>{@link #setPathologyEvaluations(List) pathologyEvaluations}: 
 * A list of pathology evaluations created for the sample.
 * <li>{@link #setBtxHistoryRecords(List) btxHistoryRecords}: 
 * A list of pathology evaluation related btx history records related to the sample.
 * </ul>
 */
public class BTXDetailsGetSamplePathologyInfo extends BTXDetails implements Serializable {
	private static final long serialVersionUID = -4051453459120878995L;

	private String _sampleId;
	private String _slideId;
	private PathologyEvaluationSampleData _pathologyEvaluationSampleData;
	private List _pathologyEvaluations;
	private List _btxHistoryRecords;
	
	private String _message;
	
	/** 
	 * Constructor
	 */
	public BTXDetailsGetSamplePathologyInfo() {
		super();
	}
			
	/**
	 * Returns the btxHistoryRecords.
	 * @return List
	 */
	public List getBtxHistoryRecords() {
		return _btxHistoryRecords;
	}

	/**
	 * Returns the pathologyEvaluations.
	 * @return List
	 */
	public List getPathologyEvaluations() {
		return _pathologyEvaluations;
	}

	/**
	 * Returns the pathologyEvaluationSampleData.
	 * @return PathologyEvaluationSampleData
	 */
	public PathologyEvaluationSampleData getPathologyEvaluationSampleData() {
		return _pathologyEvaluationSampleData;
	}

	/**
	 * Returns the sampleId.
	 * @return String
	 */
	public String getSampleId() {
		return _sampleId;
	}

	/**
	 * Returns the slideId.
	 * @return String
	 */
	public String getSlideId() {
		return _slideId;
	}

	/**
	 * Sets the btxHistoryRecords.
	 * @param btxHistoryRecords The btxHistoryRecords to set
	 */
	public void setBtxHistoryRecords(List btxHistoryRecords) {
		_btxHistoryRecords = btxHistoryRecords;
	}

	/**
	 * Sets the pathologyEvaluations.
	 * @param pathologyEvaluations The pathologyEvaluations to set
	 */
	public void setPathologyEvaluations(List pathologyEvaluations) {
		_pathologyEvaluations = pathologyEvaluations;
	}

	/**
	 * Sets the pathologyEvaluationSampleData.
	 * @param pathologyEvaluationSampleData The pathologyEvaluationSampleData to set
	 */
	public void setPathologyEvaluationSampleData(PathologyEvaluationSampleData pathologyEvaluationSampleData) {
		_pathologyEvaluationSampleData = pathologyEvaluationSampleData;
	}

	/**
	 * Sets the sampleId.
	 * @param sampleId The sampleId to set
	 */
	public void setSampleId(String sampleId) {
		_sampleId = sampleId;
	}

	/**
	 * Sets the slideId.
	 * @param slideId The slideId to set
	 */
	public void setSlideId(String slideId) {
		_slideId = slideId;
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
		String msg = "BTXDetailsGetSamplePathologyInfo.doGetDetailsAsHTML() method called in error!";
		return msg;
	}
	
	/**
	 * Return the business transaction type code for the transaction that this
	 * class represents.
	 *
	 * @return the transaction type code.
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_MANAGE_PATH_EVAL;
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
		set.add(getSampleId());
		return set;
	}

	/**
	 * Returns the message.
	 * @return String
	 */
	public String getMessage() {
		return _message;
	}

	/**
	 * Sets the message.
	 * @param message The message to set
	 */
	public void setMessage(String message) {
		_message = message;
	}

}
