/**
 * Created on Feb 11, 2003
 */
package com.ardais.bigr.btx;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * Represent the details of an update computed data business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setDataComputationRequests(List) dataComputationRequests}: A list of DataComputationRequestData data beans, 
 * each of which holds the details for an individual data computation request 
 * (@see com.ardais.bigr.javabeans.DataComputationRequestData for details).</li>
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
 */
public class BTXDetailsUpdateComputedData extends BTXDetails implements Serializable {
	private static final long serialVersionUID = 3944420368081413891L;
	private List _dataComputationRequests;
    private int _count = 0;
	
	/**
	 * Constructor for BTXDetailsUpdateComputedData.
	 */
	public BTXDetailsUpdateComputedData() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_UPDATE_COMPUTED_DATA;
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
		String msg = "BTXDetailsUpdateComputedData.doGetDetailsAsHTML() method called in error!";
		return msg;
	}

	/**
	 * Returns the dataComputationRequests.
	 * @return List
	 */
	public List getDataComputationRequests() {
		return _dataComputationRequests;
	}

	/**
	 * Sets the dataComputationRequests.
	 * @param dataComputationRequests The dataComputationRequests to set
	 */
	public void setDataComputationRequests(List dataComputationRequests) {
		_dataComputationRequests = dataComputationRequests;
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
