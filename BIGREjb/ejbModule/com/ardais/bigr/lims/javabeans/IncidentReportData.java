package com.ardais.bigr.lims.javabeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails;
import com.ardais.bigr.lims.btx.BTXDetailsCreateIncidents;

/**
 * @author rnelson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class IncidentReportData implements PopulatableFromBtxDetails, Serializable {
	private static final long serialVersionUID = 2362940170467474224L;
	
	String _createdBy;
	String _createDate;
  String _generalComments;
	
	public static final String INCIDENT_REPORT_KEY = "incident_report_key";
	
	List _lineItems; //list of IncidentReportLineItems
	/**
	 * Returns the createDate.
	 * @return String
	 */
	public String getCreateDate() {
		return _createDate;
	}

	/**
	 * Returns the createdBy.
	 * @return String
	 */
	public String getCreatedBy() {
		return _createdBy;
	}

	/**
	 * Returns the lineItems.
	 * @return List
	 */
	public List getLineItems() {
		return _lineItems;
	}

	/**
	 * Sets the createDate.
	 * @param createDate The createDate to set
	 */
	public void setCreateDate(String createDate) {
		_createDate = createDate;
	}
	
	/**
	 * Sets the createDate.
	 * @param createDate The createDate to set
	 */
	public void setCreateDate(java.util.Date date) {
		String temp;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		
		
		temp = cal.get(Calendar.MONTH) + 1 + "/" + 
			   cal.get(Calendar.DAY_OF_MONTH) + "/" + 
			   cal.get(Calendar.YEAR) + " " + 
			   ((cal.get(Calendar.HOUR) == 0)?12:cal.get(Calendar.HOUR)) + ":" +
			   cal.get(Calendar.MINUTE) + " " +
			   ((cal.get(Calendar.AM_PM) == Calendar.AM)?"am":"pm");
		
		_createDate = temp;
	}

	/**
	 * Sets the createdBy.
	 * @param createdBy The createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		_createdBy = createdBy;
	}

	/**
	 * Sets the lineItems.
	 * @param lineItems The lineItems to set
	 */
	public void setLineItems(List lineItems) {
		_lineItems = lineItems;
	}
	
	/**
	 * Adds a lineitem.
	 * @param lineItem The lineItem to add
	 */
	public void addLineItem(IncidentReportLineItem lineItem) {
		if(_lineItems == null){
			_lineItems = new ArrayList();	
			_lineItems.add(lineItem);	
		} else {
			_lineItems.add(lineItem);	
		}
	}
  
  /**
   * Method to populate this object from the data in a BtxDetails object
   * @param btxDetails  The BTXDetails object holding the information
   * */
  public void populateFromBtxDetails(BTXDetails btxDetails) {
    IncidentReportData source = ((BTXDetailsCreateIncidents)btxDetails).getIncidentReportData();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, source);
  }

  /**
   * Returns the generalComments.
   * @return String
   */
  public String getGeneralComments() {
    return _generalComments;
  }

  /**
   * Sets the generalComments.
   * @param generalComments The generalComments to set
   */
  public void setGeneralComments(String generalComments) {
    _generalComments = generalComments;
  }

}
