package com.ardais.bigr.lims.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationSampleData;

/**
 * Represents the details of a create pathology evaluation prepare new business transaction.
 * This is the transaction invoked when a user wishes to create a brand new evaluation, using
 * nothing as a source (as opposed to a copy or edit, which uses an existing evaluation to seed
 * the values for a new evaluation)
 * 
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setSlideId (String) slideId}: The slide to which the new evaluation will be linked 
 * </li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setSourceEvaluationId (String) sourceEvaluationId}: The id of the source evaluation, if any, 
 * which should be used to prepopulate this new evaluation 
 * </li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setPathologyEvaluationSampleData(PathologyEvaluationSampleData) pathologyEvaluationSampleData}: 
 * An object containing information about the sample to which this new evaluation will belong.  Used by the
 * front end to display info about that sample.
 * </li>
 * <li>{@link #setPathologyEvaluationData(PathologyEvaluationData) pathologyEvaluationData}: 
 * An object containing information about the new evaluation.  If no source evaluation id is specified 
 * most fields in this bean will be blank, but some fields (like slideId, sampleId, createMethod) will be 
 * populated.  If a source evaluation id is specified, then all data fields on the new evaluation will be
 * pre-populated with whatever values the source evaluation had.  One exception to this is the concatenated
 * external and internal comments, since those fields will be computed when the new evaluation is saved.
 * </li>
 * <li>{@link #setIncidents(List) reIncidents}: 
 * A list of any incidents for the sample to which this new evaluation will belong that we should
 * display.  Currently used by the front end to display re-PV incidents created since the reported
 * date on the currently reported evaluation for the sample.
 * </li>
 * </ul>
 */
public class BTXDetailsCreatePathologyEvaluationPrepare extends BTXDetails implements Serializable {
	private static final long serialVersionUID = 7575821436702558589L;

	private String _slideId;
	private String _sourceEvaluationId;
	private PathologyEvaluationSampleData _pathologyEvaluationSampleData;
	private PathologyEvaluationData _pathologyEvaluationData;
  private List _incidents;
	
	/** 
	 * Constructor
	 */
	public BTXDetailsCreatePathologyEvaluationPrepare() {
		super();
	}		

	/**
	 * Returns the pathologyEvaluationData.
	 * @return PathologyEvaluationData
	 */
	public PathologyEvaluationData getPathologyEvaluationData() {
		return _pathologyEvaluationData;
	}

	/**
	 * Returns the pathologyEvaluationSampleData.
	 * @return PathologyEvaluationSampleData
	 */
	public PathologyEvaluationSampleData getPathologyEvaluationSampleData() {
		return _pathologyEvaluationSampleData;
	}

  /**
   * Returns the incidents.
   * @return List
   */
  public List getIncidents() {
    return _incidents;
  }

	/**
	 * Returns the slideId.
	 * @return String
	 */
	public String getSlideId() {
		return _slideId;
	}

	/**
	 * Returns the sourceEvaluationId.
	 * @return String
	 */
	public String getSourceEvaluationId() {
		return _sourceEvaluationId;
	}

	/**
	 * Sets the pathologyEvaluationData.
	 * @param pathologyEvaluationData The pathologyEvaluationData to set
	 */
	public void setPathologyEvaluationData(PathologyEvaluationData pathologyEvaluationData) {
		_pathologyEvaluationData = pathologyEvaluationData;
	}

	/**
	 * Sets the pathologyEvaluationSampleData.
	 * @param pathologyEvaluationSampleData The pathologyEvaluationSampleData to set
	 */
	public void setPathologyEvaluationSampleData(PathologyEvaluationSampleData pathologyEvaluationSampleData) {
		_pathologyEvaluationSampleData = pathologyEvaluationSampleData;
	}

  /**
   * Sets the incidents.
   * @param incidents The incidents to set
   */
  public void setIncidents(List incidents) {
    _incidents = incidents;
  }

	/**
	 * Sets the slideId.
	 * @param slideId The slideId to set
	 */
	public void setSlideId(String slideId) {
		_slideId = slideId;
	}

	/**
	 * Sets the sourceEvaluationId.
	 * @param sourceEvaluationId The sourceEvaluationId to set
	 */
	public void setSourceEvaluationId(String sourceEvaluationId) {
		_sourceEvaluationId = sourceEvaluationId;
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
		String msg = "BTXDetailsCreatePathologyEvaluationPrepare.doGetDetailsAsHTML() method called in error!";
		return msg;
	}
	
	/**
	 * Return the business transaction type code for the transaction that this
	 * class represents.
	 *
	 * @return the transaction type code.
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_CREATE_PATH_EVAL_PREPARE;
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

		set.add(getSlideId());
		if (getPathologyEvaluationData() != null) {
			set.add(getPathologyEvaluationData().getSampleId());
		}
		if (getSourceEvaluationId() != null && getSourceEvaluationId() != "") {
			set.add(getSourceEvaluationId());
		}
		
		return set;
	}

}
