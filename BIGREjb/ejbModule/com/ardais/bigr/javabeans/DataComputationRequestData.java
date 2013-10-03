/**
 * Created on Feb 11, 2003
 */
package com.ardais.bigr.javabeans;

import java.io.Serializable;

import java.util.List;

/**
 * @author jesielionis
 * Represents a request to compute data for a list of specified instances of an 
 * object type.  A list of ids should be supplied, and the objectType attribute should 
 * contain the type of object for which we need to compute data - for example, PathologyEvaluation 
 * is a legitimate value since we sometimes need to recompute the internal and external comments 
 * for these objects.
 */
public class DataComputationRequestData implements Serializable {
	private static final long serialVersionUID = 3707387626982967963L;
	private List _ids;
	private List _fieldsToCompute;
	private String _objectType;	
	
	/**
	 * Constructor for DataComputationRequestData.
	 */
	public DataComputationRequestData() { 
		super();
	}

	/**
	 * Returns the objectType.
	 * @return String
	 */
	public String getObjectType() {
		return _objectType;
	}

	/**
	 * Returns the ids.
	 * @return List
	 */
	public List getIds() {
		return _ids;
	}

	/**
	 * Returns the fieldsToCompute.
	 * @return List
	 */
	public List getFieldsToCompute() {
		return _fieldsToCompute;
	}

	/**
	 * Sets the objectType.
	 * @param objectType The objectType to set
	 */
	public void setObjectType(String objectType) {
		_objectType = objectType;
	}

	/**
	 * Sets the ids.
	 * @param ids The ids to set
	 */
	public void setIds(List ids) {
		_ids = ids;
	}

	/**
	 * Sets the fieldsToCompute.
	 * @param fieldsToCompute The fieldsToCompute to set
	 */
	public void setFieldsToCompute(List fieldsToCompute) {
		_fieldsToCompute = fieldsToCompute;
	}

}
