package com.ardais.bigr.lims.btx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.javabeans.IdList;

/**
 * Represents the details of a get path qc details (Path QC) business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setSampleIds(IdList) ids}: A list of sample ids for which we're retrieving data
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul> 
 * <li>{@link #setPathologyEvaluationSampleDatas(List) pathologyEvaluationSampleDatas}: A list of 
 * pathologyEvaluationSampleData data beans containing the information for the samples specified
 * </ul>
 */
public class BTXDetailsGetPathQCSampleDetails extends BTXDetails implements Serializable {
	private static final long serialVersionUID = -3929513414023294162L;

	//input data members
	private IdList _sampleIds;
	
	//output data members
	private List _pathologyEvaluationSampleDatas;
	
	/**
	 * Constructor for BTXDetailsGetPathQCSampleDetails.
	 */
	public BTXDetailsGetPathQCSampleDetails() {
		super();
	}
	
	/**
	 * Returns the sampleIds.
	 * @return IdList
	 */
	public IdList getSampleIds() {
		return _sampleIds;
	}
	
	/**
	 * Returns the pathologyEvaluationSampleDatas.
	 * @return List
	 */
	public List getPathologyEvaluationSampleDatas() {
		if (_pathologyEvaluationSampleDatas == null) {
			_pathologyEvaluationSampleDatas = new ArrayList();
			return _pathologyEvaluationSampleDatas;
		}else {
			return _pathologyEvaluationSampleDatas;
		}
		
	}

	/**
	 * Sets the sampleIds.
	 * @param sampleIds The sampleIds to set
	 */
	public void setSampleIds(IdList sampleIds) {
		_sampleIds = sampleIds;
	}

	/**
	 * Sets the pathologyEvaluationSampleDatas.
	 * @param pathologyEvaluationSampleDatas The pathologyEvaluationSampleDatas to set
	 */
	public void setPathologyEvaluationSampleDatas(List pathologyEvaluationSampleDatas) {
		_pathologyEvaluationSampleDatas = pathologyEvaluationSampleDatas;
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
		String msg = "BTXDetailsGetPathQCSampleDetails.doGetDetailsAsHTML() method called in error!";
		return msg;
	}
	
	/**
	 * Return the business transaction type code for the transaction that this
	 * class represents.
	 *
	 * @return the transaction type code.
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_PATH_QC_DETAILS;
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
		return getSampleIds().getDirectlyInvolvedObjects();
	}
}
