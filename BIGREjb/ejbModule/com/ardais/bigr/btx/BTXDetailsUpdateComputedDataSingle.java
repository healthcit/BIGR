/**
 * Created on Feb 11, 2003
 */
package com.ardais.bigr.btx;

import java.io.Serializable;
import java.util.Set;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.javabeans.DataComputationRequestData;

/**
 * Represent the details of an update computed data transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setDataComputationRequestData(DataComputationRequestData) dataComputationRequestData}: A 
 * DataComputationRequestData data bean, holding either a list of ids or having a boolean set that
 * indicates all objects should be updated, and an object type for which this request is being made.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setCount(count) dataComputationRequestData} which represents number rows updated.</li>
 * </ul>
 */
public class BTXDetailsUpdateComputedDataSingle extends BTXDetails implements Serializable {
	private static final long serialVersionUID = -1329227027951438484L;
	private DataComputationRequestData _dataComputationRequestData;
    private int _count = 0;
	
	/**
	 * Constructor for BTXDetailsUpdateComputedDataSingle.
	 */
	public BTXDetailsUpdateComputedDataSingle() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_UPDATE_COMPUTED_DATA_SINGLE;
	}

	/* (non-Javadoc)
	 * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
	 */
	public Set getDirectlyInvolvedObjects() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
	 */
	protected String doGetDetailsAsHTML() {
		String msg = "BTXDetailsUpdateComputedDataSingle.doGetDetailsAsHTML() method called in error!";
		return msg;
	}

	/**
	 * Returns the dataComputationRequestData.
	 * @return DataComputationRequestData
	 */
	public DataComputationRequestData getDataComputationRequestData() {
		return _dataComputationRequestData;
	}

	/**
	 * Sets the dataComputationRequestData.
	 * @param dataComputationRequestData The dataComputationRequestData to set
	 */
	public void setDataComputationRequestData(DataComputationRequestData dataComputationRequestData) {
		_dataComputationRequestData = dataComputationRequestData;
	}

	/**
	 * Returns the count.
	 * @return int
	 */
	public int getCount() {
		return _count;
	}

	/**
	 * Sets the count.
	 * @param count The count to set
	 */
	public void setCount(int count) {
		_count = count;
	}

}
