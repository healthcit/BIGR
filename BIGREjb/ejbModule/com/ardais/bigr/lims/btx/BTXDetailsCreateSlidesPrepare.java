package com.ardais.bigr.lims.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.lims.javabeans.CreateSlidesPrepareData;

/**
 * Represent the details of a create slides prepare business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 * <p>
 *<h4>Required input fields for operation type = scan user</h4>
 * <ul>
 * <li>{@link #setLimsUserId(String) limsUserId}: The id of the LIMS user performing the transaction.</li> 
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 * 
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul> 
 * 
 * <p>
 *<h4>Required input fields for operation type = add sample</h4>
 * <ul>
 * <li>{@link #setLimsUserId(String) limsUserId}: The id of the LIMS user performing the transaction.</li> 
 * <li>{@link #setCurrentSample(String) currentSample}: The sample id to be validated.</li> 
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 * 
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul> 
 * 
 * <p>
 * <h4>Required input fields for operation type = get Labels</h4>
 * <ul>
 * <li>{@link #setLimsUserId(String) limsUserId}: The id of the LIMS user performing the transaction.</li>
 * <li>{@link #setCreateSlidesPrepareData(List) createSlidesPrepareData}: A list of CreateSlidesPrepareData 
 * data beans, each of which holds a sample id and the number of new slides to create for that sample.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setCreateSlidesPrepareData(List) createSlidesPrepareData}: The same list of CreateSlidesPrepareData 
 * data beans passed as input, each data bean having been updated with information about whether the sample exists
 * in the database or not, whether it is pulled or not, and (if the sample exists) with a list of SlideData data 
 * beans.  Each SlideData data bean in that list holds either the sample id, slide id, procedure, level, and number 
 * of an existing slide for the sample, or the sample id, slide id, and cut level of a new slide for the sample.  
 * Existing slides will have an isNew value of false, and new slides will have an isNew value of true.</li>
 * </ul>
 */
public class BTXDetailsCreateSlidesPrepare extends BTXDetails implements Serializable {
	private static final long serialVersionUID = -1920713660648308866L;
	public static final String OP_TYPE_SCAN_USER = "scanUser";
	public static final String OP_TYPE_ADD_SAMPLE = "addSample";
	public static final String OP_TYPE_GET_LABEL = "getLabel";

	private String _limsUserId;
	private List _createSlidesPrepareData;
	private String _operationType;
	
	private String _currentSample;
	private boolean _currentSamplePulled;
	private boolean _currentSampleRequested;
	
	
	/** 
	 * Constructor
	 */
	public BTXDetailsCreateSlidesPrepare() {
		super();
	}	
	
	/**
	 * Returns the limsUserId.
	 * @return String
	 */
	public String getLimsUserId() {
		return _limsUserId;
	}

	/**
	 * Returns the createSlidesPrepareData.
	 * @return List
	 */
	public List getCreateSlidesPrepareData() {
		return _createSlidesPrepareData;
	}

	/**
	 * Sets the limsUserId.
	 * @param limsUserId The limsUserId to set
	 */
	public void setLimsUserId(String limsUserId) {
		_limsUserId = limsUserId;
	}

	/**
	 * Sets the slideData.
	 * @param slideData The slideData to set
	 */
	public void setCreateSlidesPrepareData(List createSlidesPrepareData) {
		_createSlidesPrepareData = createSlidesPrepareData;
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
		String msg = "BTXDetailsCreateSlidesPrepare.doGetDetailsAsHTML() method called in error!";
		return msg;
	}
	
	/**
	 * Return the business transaction type code for the transaction that this
	 * class represents.
	 *
	 * @return the transaction type code.
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_CREATE_SLIDES_PREPARE;
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

		if (getCreateSlidesPrepareData() != null) {
			Iterator iterator = getCreateSlidesPrepareData().iterator();
			while (iterator.hasNext()) {
				CreateSlidesPrepareData data = (CreateSlidesPrepareData)iterator.next();
				set.add(data.getSampleId());
			}
		}
		
		return set;
	}
	/**
	 * Returns the operationType.
	 * @return String
	 */
	public String getOperationType() {
		return _operationType;
	}

	/**
	 * Sets the operationType.
	 * @param operationType The operationType to set
	 */
	public void setOperationType(String operationType) {
		_operationType = operationType;
	}

	/**
	 * Returns the currentSample.
	 * @return String
	 */
	public String getCurrentSample() {
		return _currentSample;
	}

	/**
	 * Returns the currentSamplePulled.
	 * @return boolean
	 */
	public boolean isCurrentSamplePulled() {
		return _currentSamplePulled;
	}

	/**
	 * Returns the currentSampleRequested.
	 * @return boolean
	 */
	public boolean isCurrentSampleRequested() {
		return _currentSampleRequested;
	}

	/**
	 * Sets the currentSample.
	 * @param currentSample The currentSample to set
	 */
	public void setCurrentSample(String currentSample) {
		_currentSample = currentSample;
	}

	/**
	 * Sets the currentSamplePulled.
	 * @param currentSamplePulled The currentSamplePulled to set
	 */
	public void setCurrentSamplePulled(boolean currentSamplePulled) {
		_currentSamplePulled = currentSamplePulled;
	}

	/**
	 * Sets the currentSampleRequested.
	 * @param currentSampleRequested The currentSampleRequested to set
	 */
	public void setCurrentSampleRequested(boolean currentSampleRequested) {
		_currentSampleRequested = currentSampleRequested;
	}

}
