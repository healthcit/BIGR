package com.ardais.bigr.iltds.helpers;

import javax.servlet.ServletRequest;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.assistants.LegalValueSetHierarchy;
import com.ardais.bigr.iltds.assistants.WorkOrderDataBean;

/**
 * <code>WorkOrderHelper</code> aids in the generation of JSPs
 * that allow viewing and editing of work orders.
 */
public class WorkOrderHelper extends JspHelper {

	// Constants that specify request parameter names.
	public static final String ID_END_DAY = "endDay";
	public static final String ID_END_MONTH = "endMonth";
	public static final String ID_END_YEAR = "endYear";
	public static final String ID_LISTORDER = "listOrder";
	public static final String ID_NOTES = "notes";
	public static final String ID_NUM_SAMPLES = "numSamples";
	public static final String ID_PERCENT_COMPLETE = "percent";
	public static final String ID_START_DAY = "startDay";
	public static final String ID_START_MONTH = "startMonth";
	public static final String ID_START_YEAR = "startYear";
	public static final String ID_STATUS = "status";
	public static final String ID_TYPE = "type";
	public static final String ID_WORKORDER_ID = "workOrderId";
	public static final String ID_WORKORDER_NAME = "workOrderName";
	
	// Values that are read directly from request parameters.
	private String _endDay;
	private String _endMonth;
	private String _endYear;
	private String _id;
	private String _listOrder;
	private String _notes;
	private String _numSamples;
	private String _percentComplete;
	private String _projectId;
	private String _startDay;
	private String _startMonth;
	private String _startYear;
	private String _status;
	private String _type;
	private String _workOrderName;

	// An instance of the data bean that holds the data for
	// this helper.
	private WorkOrderDataBean _dataBean;

	// Helpers for the date fields.
	private DateHelper _endDate;
	private DateHelper _startDate;

	// Legal value sets that represent the contents of
	// dropdowns.
	private LegalValueSet _statusList;
	private LegalValueSetHierarchy _typeList;
	private LegalValueSet _statusTypeMapping;
/**
 * Creates a new empty <code>WorkOrderHelper</code>.
 */
public WorkOrderHelper() {
	super();
}
/**
 * Creates a <code>WorkOrderHelper</code>, initializing its data
 * from the specified data bean.
 */
public WorkOrderHelper(WorkOrderDataBean dataBean) {
	this();
	_dataBean = dataBean;
}
/**
 * Creates a <code>WorkOrderHelper</code> for the project
 * with the specified id.
 */
public WorkOrderHelper(String projectId) {
	this();
	_projectId = projectId;
}
/**
 * Creates a <code>WorkOrderHelper</code>, initializing its data
 * from the specified request.
 */
public WorkOrderHelper(ServletRequest request) {
	this();
	_id = safeTrim(request.getParameter(ID_WORKORDER_ID));
	_projectId = safeTrim(request.getParameter(ProjectHelper.ID_PROJECT_ID));
	_endDay = safeTrim(request.getParameter(ID_END_DAY));
	_endMonth = safeTrim(request.getParameter(ID_END_MONTH));
	_endYear = safeTrim(request.getParameter(ID_END_YEAR));
	_listOrder = safeTrim(request.getParameter(ID_LISTORDER));
	_notes = safeTrim(request.getParameter(ID_NOTES));
	_numSamples = safeTrim(request.getParameter(ID_NUM_SAMPLES));
	_percentComplete = safeTrim(request.getParameter(ID_PERCENT_COMPLETE));
	_startDay = safeTrim(request.getParameter(ID_START_DAY));
	_startMonth = safeTrim(request.getParameter(ID_START_MONTH));
	_startYear = safeTrim(request.getParameter(ID_START_YEAR));
	_status = safeTrim(request.getParameter(ID_STATUS));	
	_type = safeTrim(request.getParameter(ID_TYPE));	
	_workOrderName = safeTrim(request.getParameter(ID_WORKORDER_NAME));
}
/**
 * Generates the HTML for the array of work order status
 * select lists by work order type.
 *
 * @return  The HMTL that represents the status array.
 */
public String getAllStatusLists() throws ApiException {
	LegalValueSetHierarchy types = getTypesAndStatuses();
	StringBuffer buf = new StringBuffer(1024);
	buf.append("var statuses = new Array();");
	int typeCount = types.getCount();
	for (int i = 0; i < typeCount; i++) {
		String key = types.getValue(i);
		LegalValueSet statuses = types.getSubLegalValueSet(i);
		buf.append("statuses[\"");
		buf.append(key);
		buf.append("\"] = \"");
		buf.append(Escaper.jsEscapeInScriptTag(generateSelectList(ID_STATUS, statuses)));
		buf.append("\";");
	}
	return buf.toString();
}
/**
 * Returns the <code>WorkOrderDataBean</code> that contains
 * the work order data fields associated with this 
 * <code>WorkOrderHelper</code>.
 * 
 * @return  The <code>WorkOrderDataBean</code>.
 */
public WorkOrderDataBean getDataBean() throws ApiException {
	if (_dataBean == null) {
		_dataBean = new WorkOrderDataBean();
		
		DateHelper dateHelper = getEndDateHelper();
		if (dateHelper != null) {
			_dataBean.setEndDate(new java.sql.Date(dateHelper.getDate().getTime()));
		}
		if (!isEmpty(getRawListOrder())) {
			_dataBean.setListOrder(Integer.valueOf(getRawListOrder()));
		}
		if (!isEmpty(getRawNotes())) {
			_dataBean.setNotes(getRawNotes());
		}
		if (!isEmpty(getRawNumSamples())) {
			_dataBean.setNumberOfSamples(Integer.valueOf(getRawNumSamples()));
		}
		if (!isEmpty(getRawPercentComplete())) {
			_dataBean.setPercentComplete(Integer.valueOf(getRawPercentComplete()));
		}
		if (!isEmpty(getRawProjectId())) {
			_dataBean.setProjectId(getRawProjectId());
		}
		dateHelper = getStartDateHelper();
		if (dateHelper != null) {
			_dataBean.setStartDate(new java.sql.Date(dateHelper.getDate().getTime()));
		}
		if (!isEmpty(getRawStatus())) {
			_dataBean.setStatus(getRawStatus());
		}
		if (!isEmpty(getRawType())) {
			_dataBean.setType(getRawType());
		}
		if (!isEmpty(getRawWorkOrderName())) {
			_dataBean.setWorkOrderName(getRawWorkOrderName());
		}
			
		if (!isNew()) {
			_dataBean.setId(getRawId());
		}
	}
	return _dataBean;
}
/**
 * Returns this work order's end date, suitable for
 * display.  If the end date is <code>null</code>,
 * then the empty string is returned.
 *
 * @return  The HMTL that represents the end date.
 */
public String getEndDate() {
	DateHelper helper = getEndDateHelper();
	return (helper == null) ? "" : helper.getFormattedDate();
}
/**
 * Returns a <code>DateHelper</code> for this work order's end date.
 * <code>null</code> is returned if there is no end date.
 * 
 * @return  The end date <code>DateHelper</code>.
 */
private DateHelper getEndDateHelper() {
	if (_endDate == null) {
		if (_dataBean != null) {
			java.sql.Date endDate = _dataBean.getEndDate();
			if (endDate != null) {
				_endDate = new DateHelper(endDate);
			}
		}
		else if (!isEmpty(_endDay) 
				 && !isEmpty(_endMonth)
				 && !isEmpty(_endYear)) {
			_endDate = new DateHelper(_endDay, _endMonth, _endYear);	
		}
	}
	return _endDate;
}
/**
 * Generates the HTML for the list of days for end date.
 *
 * @return  The HMTL that represents the day list.
 */
public String getEndDayList() {
	return generateSelectList(ID_END_DAY, DateHelper.getDayList(), getRawEndDay());
}
/**
 * Generates the HTML for the list of months for end date.
 *
 * @return  The HMTL that represents the month list.
 */
public String getEndMonthList() {
	return generateSelectList(ID_END_MONTH, DateHelper.getMonthList(), getRawEndMonth());
}
/**
 * Generates the HTML for the list of years for end date.
 *
 * @return  The HMTL that represents the year list.
 */
public String getEndYearList() {
	return generateSelectList(ID_END_YEAR, DateHelper.getYearList(2002, 2010), getRawEndYear());
}
/**
 * Returns this work order's id, suitable for display. 
 * If the id is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  The work order id.
 */
public String getId() {
	String id = getRawId();
	return (id == null) ? "" : id;
}
/**
 * Returns this work order's notes, suitable for display.  If the
 * notes are <code>null</code>, then the empty string is returned.
 * 
 * @return  The work order notes.
 */
public String getNotes() {
	String notes = getRawNotes();
	return (notes == null) ? "" : notes;
}
/**
 * Returns the number of samples in the work order, suitable
 * for display.  If the number of samples is <code>null</code>,
 * then the empty string is returned.
 * 
 * @return  The number of samples.
 */
public String getNumSamples() {
	String num = getRawNumSamples();
	return (num == null) ? "" : num;
}
/**
 * Returns the work order's percent completion, suitable for
 * display.  If the percent completion is <code>null</code>,
 * then the empty string is returned.
 * 
 * @return  The percent completion.
 */
public String getPercentComplete() {
	String percent = getRawPercentComplete();
	return (percent == null) ? "" : percent;
}
/**
 * Returns this work order's project id, suitable for display. 
 * If the id is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This work order's project id.
 */
public String getProjectId() {
	String id = getRawProjectId();
	return (id == null) ? "" : id;
}
/**
 * Returns this work order's end day, returning <code>null</code>
 * if the end day is <code>null</code>.
 * 
 * @return  The end day.
 */
private String getRawEndDay() {
	if (_endDay != null) {
		return _endDay;
	}
	else if (_endDate != null) {
		return _endDate.getDayString();
	}
	else if (_dataBean != null) {
		java.sql.Date endDate = _dataBean.getEndDate();
		if (endDate != null) {
			_endDate = new DateHelper(endDate);
			return _endDate.getDayString();
		}
	}
	return null;
}
/**
 * Returns this work order's end month, returning <code>null</code>
 * if the end month is <code>null</code>.
 * 
 * @return  The end month.
 */
private String getRawEndMonth() {
	if (_endMonth != null) {
		return _endMonth;
	}
	else if (_endDate != null) {
		return _endDate.getMonthString();
	}
	else if (_dataBean != null) {
		java.sql.Date endDate = _dataBean.getEndDate();
		if (endDate != null) {
			_endDate = new DateHelper(endDate);
			return _endDate.getMonthString();
		}
	}
	return null;
}
/**
 * Returns this work order's end year, returning <code>null</code>
 * if the end year is <code>null</code>.
 * 
 * @return  The end year.
 */
private String getRawEndYear() {
	if (_endYear != null) {
		return _endYear;
	}
	else if (_endDate != null) {
		return _endDate.getYearString();
	}
	else if (_dataBean != null) {
		java.sql.Date endDate = _dataBean.getEndDate();
		if (endDate != null) {
			_endDate = new DateHelper(endDate);
			return _endDate.getYearString();
		}
	}
	return null;
}
/**
 * Returns this work order's id.  If the id is null, then
 * null is returned.
 * 
 * @return  The work order id.
 */
public String getRawId() {
	if (_id != null) {
		return _id;
	}
	else if (_dataBean != null) {
		return _dataBean.getId();
	}
	return null;
}
/**
 * Returns the work order's list order.  If the list order
 * is <code>null</code>, then <code>null</code> is returned.
 * 
 * @return  The list order.
 */
public String getRawListOrder() {
	if (_listOrder != null) {
		return _listOrder;
	}
	else if (_dataBean != null) {
		Integer listOrder = _dataBean.getListOrder();
		if (listOrder != null) {
			return listOrder.toString();
		}
	}
	return null;
}
/**
 * Returns this work order's notes, returning <code>null</code>
 * if the notes are <code>null</code>.
 * 
 * @return  The notes.
 */
private String getRawNotes() {
	if (_notes != null) {
		return _notes;
	}
	else if (_dataBean != null) {
		return _dataBean.getNotes();
	}
	return null;
}
/**
 * Returns the number of samples in the work order.  If the
 * number of samples is <code>null</code>, then <code>null</code>
 * is returned.
 * 
 * @return  The number of samples.
 */
private String getRawNumSamples() {
	if (_numSamples != null) {
		return _numSamples;
	}
	else if (_dataBean != null) {
		Integer n = _dataBean.getNumberOfSamples();
		if (n != null) {
			return n.toString();
		}
	}

	return null;
}
/**
 * Returns the work order's percent completion.  If the percent 
 * completion is <code>null</code>, then <code>null</code> is returned.
 * 
 * @return  The percent completion.
 */
private String getRawPercentComplete() {
	if (_percentComplete != null) {
		return _percentComplete;
	}
	else if (_dataBean != null) {
		Integer p = _dataBean.getPercentComplete();
		if (p != null) {
			return p.toString();
		}
	}

	return null;
}
/**
 * Returns this work order's project id.  If the id is null, then
 * null is returned.
 * 
 * @return  The work order's project id.
 */
public String getRawProjectId() {
	if (_projectId != null) {
		return _projectId;
	}
	else if (_dataBean != null) {
		return _dataBean.getProjectId();
	}
	return null;
}
/**
 * Returns this work order's start day, returning <code>null</code>
 * if the start day is <code>null</code>.
 * 
 * @return  The start day.
 */
private String getRawStartDay() {
	if (_startDay != null) {
		return _startDay;
	}
	else if (_startDate != null) {
		return _startDate.getDayString();
	}
	else if (_dataBean != null) {
		java.sql.Date startDate = _dataBean.getStartDate();
		if (startDate != null) {
			_startDate = new DateHelper(startDate);
			return _startDate.getDayString();
		}
	}
	return null;
}
/**
 * Returns this work order's start month, returning <code>null</code>
 * if the start month is <code>null</code>.
 * 
 * @return  The start month.
 */
private String getRawStartMonth() {
	if (_startMonth != null) {
		return _startMonth;
	}
	else if (_startDate != null) {
		return _startDate.getMonthString();
	}
	else if (_dataBean != null) {
		java.sql.Date startDate = _dataBean.getStartDate();
		if (startDate != null) {
			_startDate = new DateHelper(startDate);
			return _startDate.getMonthString();
		}
	}
	return null;
}
/**
 * Returns this work order's start year, returning <code>null</code>
 * if the start year is <code>null</code>.
 * 
 * @return  The start year.
 */
private String getRawStartYear() {
	if (_startYear != null) {
		return _startYear;
	}
	else if (_startDate != null) {
		return _startDate.getYearString();
	}
	else if (_dataBean != null) {
		java.sql.Date startDate = _dataBean.getStartDate();
		if (startDate != null) {
			_startDate = new DateHelper(startDate);
			return _startDate.getYearString();
		}
	}
	return null;
}
/**
 * Returns this work order's status, returning <code>null</code>,
 * if the status is <code>null</code>.
 *
 * @return  The status.
 */
private String getRawStatus() {
	if (_status != null) {
		return _status;
	}
	else if (_dataBean != null) {
		return _dataBean.getStatus();
	}
	return null;
}
/**
 * Returns this work order's type, returning <code>null</code>,
 * if the type is <code>null</code>.
 * 
 * @return  This work order's type.
 */
public String getRawType() {
	if (_type != null) {
		return _type;
	}
	else if (_dataBean != null) {
		return _dataBean.getType();
	}
	return null;
}
/**
 * Returns this work order's name.  If the name is 
 * <code>null</code>, then <code>null</code> is returned.
 * 
 * @return  This work order's name.
 */
private String getRawWorkOrderName() {
	if (_workOrderName != null) {
		return _workOrderName;
	}
	else if (_dataBean != null) {
		return _dataBean.getWorkOrderName();
	}
	return null;
}
/**
 * Returns the work order's start date, suitable for
 * display.  If the start date is <code>null</code>,
 * then the empty string is returned.
 *
 * @return  The HMTL that represents the start date.
 */
public String getStartDate() {
	DateHelper helper = getStartDateHelper();
	return (helper == null) ? "" : helper.getFormattedDate();
}
/**
 * Returns a <code>DateHelper</code> for this work order's start date.
 * <code>null</code> is returned if there is no start date.
 * 
 * @return  The start date <code>DateHelper</code>.
 */
private DateHelper getStartDateHelper() {
	if (_startDate == null) {
		if (_dataBean != null) {
			java.sql.Date startDate = _dataBean.getStartDate();
			if (startDate != null) {
				_startDate = new DateHelper(startDate);
			}
		}
		else if (!isEmpty(_startDay) 
				 && !isEmpty(_startMonth)
				 && !isEmpty(_startYear)) {
			_startDate = new DateHelper(_startDay, _startMonth, _startYear);	
		}
	}
	return _startDate;
}
/**
 * Generates the HTML for the list of days for start date.
 *
 * @return  The HMTL that represents the day list.
 */
public String getStartDayList() {
	return generateSelectList(ID_START_DAY, DateHelper.getDayList(), getRawStartDay());
}
/**
 * Generates the HTML for the list of months for start date.
 *
 * @return  The HMTL that represents the month list.
 */
public String getStartMonthList() {
	return generateSelectList(ID_START_MONTH, DateHelper.getMonthList(), getRawStartMonth());
}
/**
 * Generates the HTML for the list of years for start date.
 *
 * @return  The HMTL that represents the year list.
 */
public String getStartYearList() {
	return generateSelectList(ID_START_YEAR, DateHelper.getYearList(2002, 2010), getRawStartYear());
}
/**
 * Returns this work order's status, suitable for display.  If the
 * status is <code>null</code>, then the empty string is returned.
 * 
 * @return  This work order's status.
 */
public String getStatus() throws ApiException {
	String status = getRawStatus();
	String type = getRawType();
	try {
		if ((status != null) && (type != null)) {
			String category = getTypeStatusMapping().getDisplayValue(type);
			return getListGenerator().lookupArdLookupDescription(category, status);
		}
	}
	catch (javax.naming.NamingException e) {
		throw new ApiException(e);
	}
	catch (javax.ejb.CreateException e) {
		throw new ApiException(e);
	}
	catch (java.rmi.RemoteException e) {
		throw new ApiException(e);
	}
  catch (ClassNotFoundException e) {
    throw new ApiException(e);
  }
	return "";
}
/**
 * Generates the HTML for the list of work order statuses.
 * Selects the correct status in the list if this helper is
 * for an existing work order.
 *
 * @return  The HMTL that represents the status list.
 */
public String getStatusList() throws ApiException {
	if (_statusList == null) {
		String type = getRawType();
		if (type != null) {
			try {
				_statusList = getListGenerator().getWorkOrderStatusesForType(type);			
			}
			catch (javax.naming.NamingException e) {
				throw new ApiException(e);
			}
			catch (javax.ejb.CreateException e) {
				throw new ApiException(e);
			}
			catch (java.rmi.RemoteException e) {
				throw new ApiException(e);
			}
      catch (ClassNotFoundException e) {
        throw new ApiException(e);
      }
			return generateSelectList(ID_STATUS, _statusList, getRawStatus());
		}
	}
	return "";
}
/**
 * Returns this work order's type, suitable for display.  If the 
 * type is <code>null</code>, then the empty string is returned.
 * 
 * @return  This work order's type.
 */
public String getType() throws ApiException {
	String type = getRawType();
	try {
		if (type != null) {
			return getListGenerator().lookupArdLookupDescription(FormLogic.ARD_LOOKUP_WO_TYPE, type);
		}
	}
	catch (javax.naming.NamingException e) {
		throw new ApiException(e);
	}
	catch (javax.ejb.CreateException e) {
		throw new ApiException(e);
	}
	catch (java.rmi.RemoteException e) {
		throw new ApiException(e);
	}
  catch (ClassNotFoundException e) {
    throw new ApiException(e);
  }
	return "";
}
/**
 * Generates the HTML for the list of work order types.
 * Selects the correct type in the list if this helper is
 * for an existing work order.
 *
 * @return  The HMTL that represents the types list.
 */
public String getTypeList() throws ApiException {
	StringBuffer buf = new StringBuffer(256);
	buf.append("<select name=\"");
	buf.append(ID_TYPE);
	buf.append("\" onchange=\"replaceStatusList();\"");
	buf.append(">");
	LegalValueSet lvs = getTypesAndStatuses();
	String type = getRawType();
	for (int i = 0; i < lvs.getCount(); i++) {
       	buf.append("<option value=\"");
       	String value = lvs.getValue(i);
       	buf.append(value);
       	buf.append('"');
       	if ((type != null) && (type.equals(value))) {
	       	buf.append(" selected");
		}
		buf.append('>');
       	buf.append(lvs.getDisplayValue(i));
       	buf.append("</option>");
	}
    buf.append("</select>");
    return buf.toString();
	
}
/**
 * Gets the list of work order types and the statuses associated
 * with each type.
 */
private LegalValueSetHierarchy getTypesAndStatuses() throws ApiException {
	if (_typeList == null) {
		try {
			_typeList = getListGenerator().getWorkOrderTypesAndStatuses();			
		}
		catch (javax.naming.NamingException e) {
			throw new ApiException(e);
		}
		catch (javax.ejb.CreateException e) {
			throw new ApiException(e);
		}
		catch (java.rmi.RemoteException e) {
			throw new ApiException(e);
		}
    catch (ClassNotFoundException e) {
      throw new ApiException(e);
    }
	}
	return _typeList;
}
/**
 * Gets the mapping of statuses to types.
 */
private LegalValueSet getTypeStatusMapping() throws ApiException {
	if (_statusTypeMapping == null) {
		try {
			_statusTypeMapping = getListGenerator().getWorkOrderStatusTypeMapping();
		}
		catch (javax.naming.NamingException e) {
			throw new ApiException(e);
		}
		catch (javax.ejb.CreateException e) {
			throw new ApiException(e);
		}
		catch (java.rmi.RemoteException e) {
			throw new ApiException(e);
		}
    catch (ClassNotFoundException e) {
      throw new ApiException(e);
    }
	}
	return _statusTypeMapping;
}
/**
 * Returns this work order's name, suitable for display. 
 * If the name is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This work order's name.
 */
public String getWorkOrderName() {
	String name = getRawWorkOrderName();
	return (name == null) ? "" : name;
}
/**
 * Returns <code>true</code> if this work order has notes;
 * <code>false</code> otherwise.
 * 
 * @return  A boolean indicating whether this work order has notes.
 */
public boolean hasNotes() {
	return (getRawNotes() != null);
}
/**
 * Returns <code>true</code> if this helper is for a new
 * work order; <code>false</code> otherwise.
 * 
 * @return  A boolean indicating whether this work order is new.
 */
public boolean isNew() {
	return (getRawId() == null);
}
/**
 * Returns <code>true</code> if the work order data associated 
 * with this helper is valid; otherwise returns <code>false</code>.
 * Generally this method is called after calling the constructor
 * that takes a <code>ServletRequest</code> as input, since it
 * is desirable to validate the request parameters.  If validation
 * fails, the <code>getMessages</code> method can be called to
 * display a message to the user indicating the problems.
 *
 * @return  <code>true</code> if all parameters are valid;
 *			<code>false</code> otherwise.
 */
public boolean isValid() {
	boolean isValid = true;
	
	// Make sure that all required fields are filled in.
	if (isEmpty(getRawWorkOrderName())) {
		getMessageHelper().addMessage("Work Order Name must be specified");
		isValid = false;
	}
	if (isEmpty(getRawType())) {
		getMessageHelper().addMessage("Work Order Type must be specified");
		isValid = false;
	}
	
	// Make sure that the start and end dates are valid dates,
	// and that end date is >= start date.
	DateHelper startDateHelper = null;
	DateHelper endDateHelper = null;
	try {
		if ((!isEmpty(_startDay) || !isEmpty(_startMonth) || !isEmpty(_startYear))
			&& (isEmpty(_startDay) || isEmpty(_startMonth) || isEmpty(_startYear))) {
			getMessageHelper().addMessage("Start Date not fully specified.");
			isValid = false;
		}
		else {
			startDateHelper = getStartDateHelper();
		}
	}
	catch (NumberFormatException e) {
		getMessageHelper().addMessage("Start Date is not a valid date.");
		isValid = false;
	}
	catch (IllegalArgumentException e) {
		getMessageHelper().addMessage("Start Date is not a valid date.");
		isValid = false;
	}
	try {
		if ((!isEmpty(_endDay) || !isEmpty(_endMonth) || !isEmpty(_endYear))
			&& (isEmpty(_endDay) || isEmpty(_endMonth) || isEmpty(_endYear))) {
			getMessageHelper().addMessage("End Date not fully specified.");
			isValid = false;
		}
		else {
			endDateHelper = getEndDateHelper();
		}
	}
	catch (NumberFormatException e) {
		getMessageHelper().addMessage("End Date is not a valid date.");
		isValid = false;
	}
	catch (IllegalArgumentException e) {
		getMessageHelper().addMessage("End Date is not a valid date.");
		isValid = false;
	}
	if ((startDateHelper != null) 
		&& (endDateHelper != null)
		&& (startDateHelper.getDate().getTime() > endDateHelper.getDate().getTime())) {
		getMessageHelper().addMessage("End Date must be greater than Start Date.");
		isValid = false;
	}

	// Make sure number of samples and percent complete are whole numbers.
	if (!isEmpty(getRawNumSamples())) {
		try {
			int numSamples = Integer.parseInt(getRawNumSamples());
			if (numSamples < 0) {
				getMessageHelper().addMessage("Number of Samples cannot be a negative number.");
				isValid = false;
			}
		}
		catch (NumberFormatException e) {
			getMessageHelper().addMessage("Number of Samples must be a whole number.");
			isValid = false;
		}
	}
	if (!isEmpty(getRawPercentComplete())) {
		try {
			int percent = Integer.parseInt(getRawPercentComplete());
			if ((percent < 0) || (percent > 100)) {
				getMessageHelper().addMessage("Percent Complete must be between 0 and 100.");
				isValid = false;
			}
		}
		catch (NumberFormatException e) {
			getMessageHelper().addMessage("Percent Complete must be a whole number.");
			isValid = false;
		}
	}
		
	if (!isValid) {
		getMessageHelper().setError(true);
	}
	return isValid;
}
}
