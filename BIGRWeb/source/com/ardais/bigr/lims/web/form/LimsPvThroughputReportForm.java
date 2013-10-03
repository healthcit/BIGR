package com.ardais.bigr.lims.web.form;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiDateUtil;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.lims.beans.LimsOperation;
import com.ardais.bigr.lims.beans.LimsOperationHome;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * This represents the web form that is used to get Throughput Report 
 * of specified user/All users.
 */
public class LimsPvThroughputReportForm extends BigrActionForm {
	
	private String _userName;
	private String _fromDate;
	private String _toDate;

	/**
	 * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
	 */
	protected void doReset(
		BigrActionMapping mapping,
		HttpServletRequest request) {
	}

	/**
	 * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
	 */
	protected ActionErrors doValidate(
		BigrActionMapping mapping,
		HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (ApiFunctions.isEmpty(getFromDate())) {
			errors.add("throughput", new ActionError(
						"lims.error.reports.enterFromDate"));
		}
		
		if (ApiFunctions.isEmpty(getToDate())) {
			errors.add("throughput", new ActionError(
						"lims.error.reports.enterToDate"));
		}
		
		if ((!ApiFunctions.isEmpty(getFromDate())) &&
		    (!ApiFunctions.isEmpty(getToDate()))) {
		    Timestamp fromDate = ApiDateUtil.convertStringToTimeStamp(getFromDate(), "MM/dd/yyyy");		
		    Timestamp toDate = ApiDateUtil.convertStringToTimeStamp(getToDate(), "MM/dd/yyyy");
		    if (fromDate.after(toDate)) {
		    	errors.add("throughput", new ActionError(
						"lims.error.reports.fromDateAfterToDate"));
		    }
		}
			
		return errors;
	}

	/**
	 * Returns the fromDae.
	 * @return String
	 */
	public String getFromDate() {
		return _fromDate;
	}

	/**
	 * Returns the toDate.
	 * @return String
	 */
	public String getToDate() {
		return _toDate;
	}

	/**
	 * Returns the userName.
	 * @return String
	 */
	public String getUserName() {
		return _userName;
	}

	/**
	 * Sets the fromDae.
	 * @param fromDae The fromDae to set
	 */
	public void setFromDate(String fromDate) {
		_fromDate = fromDate;
	}

	/**
	 * Sets the toDate.
	 * @param toDate The toDate to set
	 */
	public void setToDate(String toDate) {
		_toDate = toDate;
	}

	/**
	 * Sets the userName.
	 * @param userName The userName to set
	 */
	public void setUserName(String userName) {
		_userName = userName;
	}
	/**
	 * Returns the list of pathologists.
	 * @return Vector
	 */
	public Vector getPathologistList() {
			Vector tempVector = null;

			try {
        LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
        LimsOperation operation = home.create();
				tempVector = (Vector) operation.getPathologistList();
				tempVector.insertElementAt(LimsConstants.DEFAULT_ALL, 0);
			} catch (Exception ex) {
				ApiFunctions.throwAsRuntimeException(ex);
			} 
		return tempVector;
	}	
}
