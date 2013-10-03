package com.ardais.bigr.iltds.helpers;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.assistants.*;
import com.ardais.bigr.iltds.beans.*;
import com.ardais.bigr.util.EjbHomes;
import java.rmi.RemoteException;
import java.util.*;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * <code>ProjectHelper</code> aids in the generation of JSPs
 * that allow viewing and editing of projects.
 */
public class ProjectHelper extends JspHelper {

	// Constants that specify request parameter names.
	public static final String ID_APPROVED_DAY = "approvedDay";
	public static final String ID_APPROVED_MONTH = "approvedMonth";
	public static final String ID_APPROVED_YEAR = "approvedYear";
	public static final String ID_CLIENT = "client";
	public static final String ID_NOTES = "notes";
	public static final String ID_PERCENT_COMPLETE = "percent";
	public static final String ID_PROJECT_ID = "projId";
	public static final String ID_PROJECT_NAME = "projName";
	public static final String ID_REQUEST_DAY = "requestDay";
	public static final String ID_REQUEST_MONTH = "requestMonth";
	public static final String ID_REQUEST_YEAR = "requestYear";
	public static final String ID_REQUESTED_BY = "requestor";
	public static final String ID_SHIPPED_DAY = "shippedDay";
	public static final String ID_SHIPPED_MONTH = "shippedMonth";
	public static final String ID_SHIPPED_YEAR = "shippedYear";
	public static final String ID_STATUS = "status";

	public static final String ID_DELIVERTO = "deliverTo";
	public static final String ID_SAMPLE_COUNT = "sampleCount";
	public static final String ID_SHOPPING_CART = "shoppingCart";
	public static final String ID_ORDER = "orderNumber";
		
	// Values that are read directly from request parameters.
	private String _approvedDay;
	private String _approvedMonth;
	private String _approvedYear;
	private String _client;
	private String _id;
	private String _notes;
	private String _percentComplete;
	private String _projectName;
	private String _requestedBy;
	private String _requestDay;
	private String _requestMonth;
	private String _requestYear;
	private String _shippedDay;
	private String _shippedMonth;
	private String _shippedYear;
	private String _status;

	// Values that can be set directly.
	private int _totalSamples = 0;
	private int _totalSamplesOnHold = 0;
	
	// An instance of the associated data bean.
	private ProjectDataBean _dataBean;

	// Helpers for the date fields.
	private DateHelper _approvedDateHelper;
	private DateHelper _requestDateHelper;
	private DateHelper _shippedDateHelper;

	// Legal value sets that represent the contents of
	// dropdowns.
	private LegalValueSet _clientList;
	private LegalValueSet _statusList;
	private LegalValueSet _requestorList;
	private LegalValueSet _deliverToList;
	private LegalValueSet _orderList;
	private LegalValueSet _shoppingCartList;
	private LegalValueSet _userList;

	// Holds the work orders in this project.
	private ArrayList _workOrders;

	// Holds the line items in this project.
	private ArrayList _lineItems;

	// Holds the samples in this project.
	private ArrayList _samples;

	// Used to display the samples in the project in an HTML table.
	private SampleTableHelper _tableHelper;

/**
 * Creates a new <code>ProjectHelper</code>.
 */
public ProjectHelper() {
	super();
}
/**
 * Creates a <code>ProjectHelper</code>, initializing its data
 * from the specified data bean.
 *
 * @param  dataBean  the <code>ProjectDataBean</code> holding the
 *		project data.
 */
public ProjectHelper(ProjectDataBean dataBean) {
	super();
	_dataBean = dataBean;

	List lineItemDataBeans = _dataBean.getLineItems();
	int count = lineItemDataBeans.size();
	for (int i = 0; i < count; i++) {
		LineItemDataBean liBean = (LineItemDataBean)lineItemDataBeans.get(i);
		addLineItem(new LineItemHelper(liBean));
	}
}
/**
 * Creates a new <code>ProjectHelper</code> with just the
 * specified project id.
 *
 * @param  id  the project id
 */
public ProjectHelper(String id) {
	this();
	_id = id;
}
/**
 * Creates a <code>ProjectHelper</code>, initializing its data
 * from the specified request.
 */
public ProjectHelper(HttpServletRequest request) {
	super();	
	_id = safeTrim(request.getParameter(ID_PROJECT_ID));
	_approvedDay = safeTrim(request.getParameter(ID_APPROVED_DAY));
	_approvedMonth = safeTrim(request.getParameter(ID_APPROVED_MONTH));
	_approvedYear = safeTrim(request.getParameter(ID_APPROVED_YEAR));
	_client = safeTrim(request.getParameter(ID_CLIENT));
	_notes = safeTrim(request.getParameter(ID_NOTES));
	_percentComplete = safeTrim(request.getParameter(ID_PERCENT_COMPLETE));
	_projectName = safeTrim(request.getParameter(ID_PROJECT_NAME));
	_requestDay = safeTrim(request.getParameter(ID_REQUEST_DAY));
	_requestMonth = safeTrim(request.getParameter(ID_REQUEST_MONTH));
	_requestYear = safeTrim(request.getParameter(ID_REQUEST_YEAR));
	if (isNew()) {
		_requestedBy = safeTrim((String)request.getSession(false).getAttribute("user"));
	}
	else {
		_requestedBy = safeTrim(request.getParameter(ID_REQUESTED_BY));
	}
	_shippedDay = safeTrim(request.getParameter(ID_SHIPPED_DAY));
	_shippedMonth = safeTrim(request.getParameter(ID_SHIPPED_MONTH));
	_shippedYear = safeTrim(request.getParameter(ID_SHIPPED_YEAR));
	_status = safeTrim(request.getParameter(ID_STATUS));	
}
/**
 * Adds a <code>LineItemHelper</code>to this <code>ProjectHelper</code>.
 * The line item is added to the end of an ordered list.
 *
 * @param  helper  the <code>LineItemHelper</code>
 */
public void addLineItem(LineItemHelper helper) {
	if (_lineItems == null) {
		_lineItems = new ArrayList();
	}
	_lineItems.add(helper);
}
/**
 * Adds a <code>SampleHelper</code>to this <code>ProjectHelper</code>.
 * The sample is added to the end of an ordered list.
 *
 * @param  helper  the <code>SampleHelper</code>
 */
public void addSample(SampleHelper helper) {
	if (_samples == null) {
		_samples = new ArrayList();
	}
	_samples.add(helper);
}
/**
 * Adds a <code>WorkOrderHelper</code>to this <code>ProjectHelper</code>.
 * The work order is added to the end of an ordered list.
 *
 * @param  helper  the <code>WorkOrderHelper</code>
 */
public void addWorkOrder(WorkOrderHelper helper) {
	if (_workOrders == null) {
		_workOrders = new ArrayList();
	}
	_workOrders.add(helper);
}
/**
 * Returns this project's approved date, suitable for
 * display.  If the approved date is <code>null</code>,
 * then the empty string is returned.
 *
 * @return  The HMTL that represents the approved date.
 */
public String getApprovedDate() {
	DateHelper helper = getApprovedDateHelper();
	return (helper == null) ? "" : helper.getFormattedDate();
}
/**
 * Returns a <code>DateHelper</code> for the project's approved date.
 * <code>null</code> is returned if there is no approved date.
 * 
 * @return  The approved date <code>DateHelper</code>.
 * @throws  <code>NumberFormatException</code> if the day, month or year are
 *		not convertable to <code>int</code>.
 * @throws  <code>IllegalArgumentException</code> if the day, month and year 
 *		do not specify a valid date.
 */
private DateHelper getApprovedDateHelper() {
	if (_approvedDateHelper == null) {
		if (!isEmpty(_approvedDay) 
			&& !isEmpty(_approvedMonth)
			&& !isEmpty(_approvedYear)) {
			_approvedDateHelper = new DateHelper(_approvedDay, _approvedMonth, _approvedYear);	
		}
		else if (_dataBean != null) {
			Date approvedDate = _dataBean.getApprovedDate();
			if (approvedDate != null) {
				_approvedDateHelper = new DateHelper(approvedDate);
			}
		}
	}
	return _approvedDateHelper;
}
/**
 * Generates the HTML for the list of days for approved date.
 *
 * @return  The HMTL that represents the day list.
 */
public String getApprovedDayList() throws ApiException {
	return generateSelectList(ID_APPROVED_DAY, 
							  DateHelper.getDayList(), 
							  getRawApprovedDay());
}
/**
 * Generates the HTML for the list of months for approved date.
 *
 * @return  The HMTL that represents the month list.
 */
public String getApprovedMonthList() throws ApiException {
	return generateSelectList(ID_APPROVED_MONTH, 
							  DateHelper.getMonthList(), 
							  getRawApprovedMonth());
}
/**
 * Generates the HTML for the list of years for approved date.
 *
 * @return  The HMTL that represents the year list.
 */
public String getApprovedYearList() throws ApiException {
	return generateSelectList(ID_APPROVED_YEAR, 
							  DateHelper.getYearList(2002, 2010), 
							  getRawApprovedYear());
}
/**
 * Returns this project's client, suitable for display. 
 * If the client is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This project's client.
 */
public String getClient() throws ApiException {
	String client = getRawClient();
	try {
		if (client != null) {
			return getListGenerator().lookupDIAccountName(client);
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
 * Generates the HTML for the list of clients.
 * Selects the correct client in the list if this 
 * helper is for an existing project.
 *
 * @return  The HMTL that represents the client list.
 */
public String getClientList() throws ApiException {
	if (_clientList == null) {
		try {
			_clientList = getListGenerator().getPtsClients();
			_clientList.addLegalValue(0, "", "Select a Client");
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
	return generateSelectList(ID_CLIENT, _clientList, getRawClient());
}
/**
 * Returns the <code>ProjectDataBean</code> that contains
 * the project data fields associated with this 
 * <code>ProjectHelper</code>.
 * 
 * @return  The <code>ProjectDataBean</code>.
 */
public ProjectDataBean getDataBean() throws ApiException {
	if (_dataBean == null) {
		_dataBean = new ProjectDataBean();
		_dataBean.setClient(getRawClient());
		_dataBean.setNotes(getRawNotes());
		_dataBean.setProjectName(getRawProjectName());
		_dataBean.setRequestedBy(getRawRequestedBy());
		_dataBean.setStatus(getRawStatus());

		if (!isNew()) {
			_dataBean.setId(getRawId());
		}

		if (!isEmpty(getRawPercentComplete())) {
			_dataBean.setPercentComplete(Integer.valueOf(getRawPercentComplete()));
		}

		DateHelper dateHelper = getApprovedDateHelper();
		if (dateHelper != null) {
			_dataBean.setApprovedDate(new java.sql.Date(dateHelper.getDate().getTime()));
		}
		dateHelper = getRequestDateHelper();
		if (dateHelper != null) {
			_dataBean.setRequestDate(new java.sql.Date(dateHelper.getDate().getTime()));
		}
		dateHelper = getShippedDateHelper();
		if (dateHelper != null) {
			_dataBean.setShippedDate(new java.sql.Date(dateHelper.getDate().getTime()));
		}
	}
	return _dataBean;
}
/**
 * Returns this project's id, suitable for display. 
 * If the id is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  The project id.
 */
public String getId() throws ApiException {
	String id = getRawId();
	return (id == null) ? "" : id;
}
/**
 * Returns a <code>List</code> of <code>LineItemHelper</code>s,
 * that were added to this <code>ProjectHelper</code>.  If there
 * are no <code>LineItemHelper</code>s then an empty <code>List</code>
 * is returned, not <code>null</code>.
 *
 * @return  A <code>List</code> of <code>LineItemHelper</code>s.
 */
public List getLineItems() {
	if (_lineItems == null) {
		_lineItems = new ArrayList();
	}
	return _lineItems;
}
/**
 * Returns this project's notes, suitable for display.  If the
 * notes are <code>null</code>, then the empty string is returned.
 * 
 * @return  The project notes.
 */
public String getNotes() throws ApiException {
	String notes = getRawNotes();
	return (notes == null) ? "" : notes;
}
/**
 * Generates the HTML for the list of orders for this
 * project's client, and selects the specified order in the list.
 *
 * @param  selectedOrder  the order to select in the list
 * @return  The HMTL that represents the order list.
 */
public String getOrderList(String selectedOrder) {
	if (_orderList == null) {
		try {
			String client = getRawClient();
			if (client != null) {
				_orderList = getListGenerator().getPtsOrders(client);
				_orderList.addLegalValue(0, "", "Select");
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
	}
	return generateSelectList(ID_ORDER, _orderList, selectedOrder);
}
/**
 * Returns this project's percent completion, suitable for
 * display.  If the percent completion is <code>null</code>,
 * then the empty string is returned.
 * 
 * @return  The percent completion.
 */
public String getPercentComplete() throws ApiException {
	String percent = getRawPercentComplete();
	return (percent == null) ? "" : percent;
}
/**
 * Returns this project's name, suitable for display. 
 * If the name is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This project's name.
 */
public String getProjectName() throws ApiException {
	String name = getRawProjectName();
	return (name == null) ? "" : name;
}
/**
 * Returns this project's approved day, returning <code>null</code>
 * if the approved day is <code>null</code>.
 * 
 * @return  The approved day.
 */
private String getRawApprovedDay() {
	Date approvedDate = null;
	if (_approvedDay != null) {
		return _approvedDay;
	}
	else if (_approvedDateHelper != null) {
		return _approvedDateHelper.getDayString();
	}
	else if (_dataBean != null) {
		approvedDate = _dataBean.getApprovedDate();
	}

	if (approvedDate != null) {
		_approvedDateHelper = new DateHelper(approvedDate);
		return _approvedDateHelper.getDayString();
	}
	return null;
}
/**
 * Returns this project's approved month, returning <code>null</code>
 * if the approved month is <code>null</code>.
 * 
 * @return  The approved month.
 */
private String getRawApprovedMonth() {
	Date approvedDate = null;
	if (_approvedMonth != null) {
		return _approvedMonth;
	}
	else if (_approvedDateHelper != null) {
		return _approvedDateHelper.getMonthString();
	}
	else if (_dataBean != null) {
		approvedDate = _dataBean.getApprovedDate();
	}

	if (approvedDate != null) {
		_approvedDateHelper = new DateHelper(approvedDate);
		return _approvedDateHelper.getMonthString();
	}
	return null;
}
/**
 * Returns this project's approved year, returning <code>null</code>
 * if the approved year is <code>null</code>.
 * 
 * @return  The approved year.
 */
private String getRawApprovedYear() {
	Date approvedDate = null;
	if (_approvedYear != null) {
		return _approvedYear;
	}
	else if (_approvedDateHelper != null) {
		return _approvedDateHelper.getYearString();
	}
	else if (_dataBean != null) {
		approvedDate = _dataBean.getApprovedDate();
	}

	if (approvedDate != null) {
		_approvedDateHelper = new DateHelper(approvedDate);
		return _approvedDateHelper.getYearString();
	}
	return null;
}
/**
 * Returns this project's client.  If the client is null, then
 * null is returned.
 * 
 * @return  The client.
 */
public String getRawClient() {
	if (_client != null) {
		return _client;
	}
	else if (_dataBean != null) {
		return _dataBean.getClient();
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
 * Returns this project's notes.  If the notes are 
 * <code>null</code>, then <code>null</code> is returned.
 * 
 * @return  The project notes.
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
 * Returns this project's percent completion.  If the percent 
 * completion is <code>null</code>, then the empty string is returned.
 * 
 * @return  The percent completion.
 */
private String getRawPercentComplete() {
	if (_percentComplete != null) {
		return _percentComplete;
	}
	else if (_dataBean != null) {
		Integer p = _dataBean.getPercentComplete();
		return (p == null) ? null : p.toString();
	}
	return null;
}
/**
 * Returns this project's name, returning <code>null</code>
 * if the name is <code>null</code>.
 * 
 * @return  This project's name.
 */
private String getRawProjectName() {
	if (_projectName != null) {
		return _projectName;
	}
	else if (_dataBean != null) {
		return _dataBean.getProjectName();
	}
	return null;
}
/**
 * Returns this project's request day, returning <code>null</code>
 * if the request day is <code>null</code>.
 * 
 * @return  The request day.
 */
private String getRawRequestDay() {
	Date requestDate = null;
	if (_requestDay != null) {
		return _requestDay;
	}
	else if (_requestDateHelper != null) {
		return _requestDateHelper.getDayString();
	}
	else if (_dataBean != null) {
		requestDate = _dataBean.getRequestDate();
	}
	else if (isNew()) {
		requestDate = new Date();
	}

	if (requestDate != null) {
		_requestDateHelper = new DateHelper(requestDate);
		return _requestDateHelper.getDayString();
	}
	return null;
}
/**
 * Returns the person that requested this project.  If the requestor
 * is null, then null is returned.
 * 
 * @return  The requestor.
 */
private String getRawRequestedBy() {
	if (_requestedBy != null) {
		return _requestedBy;
	}
	else if (_dataBean != null) {
		return _dataBean.getRequestedBy();
	}
	return null;
}
/**
 * Returns this project's request month, returning <code>null</code>
 * if the request month is <code>null</code>.
 * 
 * @return  The request month.
 */
private String getRawRequestMonth() {
	Date requestDate = null;
	if (_requestMonth != null) {
		return _requestMonth;
	}
	else if (_requestDateHelper != null) {
		return _requestDateHelper.getMonthString();
	}
	else if (_dataBean != null) {
		requestDate = _dataBean.getRequestDate();
	}
	else if (isNew()) {
		requestDate = new Date();
	}

	if (requestDate != null) {
		_requestDateHelper = new DateHelper(requestDate);
		return _requestDateHelper.getMonthString();
	}
	return null;
}
/**
 * Returns this project's request year, returning <code>null</code>
 * if the request year is <code>null</code>.
 * 
 * @return  The request year.
 */
private String getRawRequestYear() {
	Date requestDate = null;
	if (_requestYear != null) {
		return _requestYear;
	}
	else if (_requestDateHelper != null) {
		return _requestDateHelper.getYearString();
	}
	else if (_dataBean != null) {
		requestDate = _dataBean.getRequestDate();
	}
	else if (isNew()) {
		requestDate = new Date();
	}

	if (requestDate != null) {
		_requestDateHelper = new DateHelper(requestDate);
		return _requestDateHelper.getYearString();
	}
	return null;
}
/**
 * Returns this project's shipped day, returning <code>null</code>
 * if the shipped day is <code>null</code>.
 * 
 * @return  The shipped day.
 */
private String getRawShippedDay() {
	Date shippedDate = null;
	if (_shippedDay != null) {
		return _shippedDay;
	}
	else if (_shippedDateHelper != null) {
		return _shippedDateHelper.getDayString();
	}
	else if (_dataBean != null) {
		shippedDate = _dataBean.getShippedDate();
	}

	if (shippedDate != null) {
		_shippedDateHelper = new DateHelper(shippedDate);
		return _shippedDateHelper.getDayString();
	}
	return null;
}
/**
 * Returns this project's shipped month, returning <code>null</code>
 * if the shipped month is <code>null</code>.
 * 
 * @return  The shipped month.
 */
private String getRawShippedMonth() {
	Date shippedDate = null;
	if (_shippedMonth != null) {
		return _shippedMonth;
	}
	else if (_shippedDateHelper != null) {
		return _shippedDateHelper.getMonthString();
	}
	else if (_dataBean != null) {
		shippedDate = _dataBean.getShippedDate();
	}

	if (shippedDate != null) {
		_shippedDateHelper = new DateHelper(shippedDate);
		return _shippedDateHelper.getMonthString();
	}
	return null;
}
/**
 * Returns this project's shipped year, returning <code>null</code>
 * if the shipped year is <code>null</code>.
 * 
 * @return  The shipped year.
 */
private String getRawShippedYear() {
	Date shippedDate = null;
	if (_shippedYear != null) {
		return _shippedYear;
	}
	else if (_shippedDateHelper != null) {
		return _shippedDateHelper.getYearString();
	}
	else if (_dataBean != null) {
		shippedDate = _dataBean.getShippedDate();
	}

	if (shippedDate != null) {
		_shippedDateHelper = new DateHelper(shippedDate);
		return _shippedDateHelper.getYearString();
	}
	return null;
}
/**
 * Returns this project's status.  If the status is null, then
 * null is returned.
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
 * Returns this project's request date, suitable for
 * display.  If the request date is <code>null</code>,
 * then the empty string is returned.
 *
 * @return  The HMTL that represents the request date.
 */
public String getRequestDate() {
	DateHelper helper = getRequestDateHelper();
	return (helper == null) ? "" : helper.getFormattedDate();
}
/**
 * Returns a <code>DateHelper</code> for the project's request date.
 * <code>null</code> is returned if there is no request date.
 * 
 * @return  The request date <code>DateHelper</code>.
 * @throws  <code>NumberFormatException</code> if the day, month or year are
 *		not convertable to <code>int</code>.
 * @throws  <code>IllegalArgumentException</code> if the day, month and year 
 *		do not specify a valid date.
 */
private DateHelper getRequestDateHelper() {
	if (_requestDateHelper == null) {
		if (!isEmpty(_requestDay) 
				 && !isEmpty(_requestMonth)
				 && !isEmpty(_requestYear)) {
			_requestDateHelper = new DateHelper(_requestDay, _requestMonth, _requestYear);	
		}
		else if (_dataBean != null) {
			Date requestDate = _dataBean.getRequestDate();
			if (requestDate != null) {
				_requestDateHelper = new DateHelper(requestDate);
			}
		}
	}
	return _requestDateHelper;
}
/**
 * Generates the HTML for the list of days for request date.
 *
 * @return  The HMTL that represents the day list.
 */
public String getRequestDayList() throws ApiException {
	return generateSelectList(ID_REQUEST_DAY, 
							  DateHelper.getDayList(), 
							  getRawRequestDay());
}
/**
 * Returns who requested this project, suitable for display.  If the
 * requetor is <code>null</code>, then the empty string is returned.
 * 
 * @return  The project requestor.
 */
public String getRequestedBy() throws ApiException {
	String requestor = getRawRequestedBy();
	return (requestor == null) ? "" : requestor;
}
/**
 * Generates the HTML for the list of months for request date.
 *
 * @return  The HMTL that represents the month list.
 */
public String getRequestMonthList() throws ApiException {
	return generateSelectList(ID_REQUEST_MONTH, 
							  DateHelper.getMonthList(), 
							  getRawRequestMonth());
}
/**
 * Generates the HTML for the list of years for request date.
 *
 * @return  The HMTL that represents the year list.
 */
public String getRequestYearList() throws ApiException {
	return generateSelectList(ID_REQUEST_YEAR, 
							  DateHelper.getYearList(2002, 2010), 
							  getRawRequestYear());
}
/**
 * Returns an <code>List</code> of <code>SampleHelper</code>s
 * that were added to this <code>ProjectHelper</code>.  If there
 * are no <code>SampleHelper</code>s then an empty <code>List</code>
 * is returned, not <code>null</code>.
 *
 * @return  A <code>List</code> of <code>SampleHelper</code>s.
 */
public List getSamples() {
	if (_samples == null) {
		_samples = new ArrayList();
	}
	return _samples;
}
/**
 * Returns the <code>SampleTableHelper</code> that was set for this <code>ProjectHelper</code>.
 * If no <code>SampleTableHelper</code> was set then <code>null</code> is returned.
 * 
 * @return  The <code>SampleTableHelper</code>.
 */
public SampleTableHelper getSampleTableHelper() {
	return _tableHelper;
}
/**
 * Returns this project's shipped date, suitable for
 * display.  If the shipped date is <code>null</code>,
 * then the empty string is returned.
 *
 * @return  The HMTL that represents the shipped date.
 */
public String getShippedDate() {
	DateHelper helper = getShippedDateHelper();
	return (helper == null) ? "" : helper.getFormattedDate();
}
/**
 * Returns a <code>DateHelper</code> for the project's shipped date.
 * <code>null</code> is returned if there is no shipped date.
 * 
 * @return  The shipped date <code>DateHelper</code>.
 * @throws  <code>NumberFormatException</code> if the day, month or year are
 *		not convertable to <code>int</code>.
 * @throws  <code>IllegalArgumentException</code> if the day, month and year 
 *		do not specify a valid date.
 */
private DateHelper getShippedDateHelper() {
	if (_shippedDateHelper == null) {
		if (!isEmpty(_shippedDay) 
			&& !isEmpty(_shippedMonth)
			&& !isEmpty(_shippedYear)) {
			_shippedDateHelper = new DateHelper(_shippedDay, _shippedMonth, _shippedYear);	
		}
		else if (_dataBean != null) {
			Date shippedDate = _dataBean.getShippedDate();
			if (shippedDate != null) {
				_shippedDateHelper = new DateHelper(shippedDate);
			}
		}
	}
	return _shippedDateHelper;
}
/**
 * Generates the HTML for the list of days for shipped date.
 *
 * @return  The HMTL that represents the day list.
 */
public String getShippedDayList() throws ApiException {
	return generateSelectList(ID_SHIPPED_DAY, 
							  DateHelper.getDayList(), 
							  getRawShippedDay());
}
/**
 * Generates the HTML for the list of months for shipped date.
 *
 * @return  The HMTL that represents the month list.
 */
public String getShippedMonthList() throws ApiException {
	return generateSelectList(ID_SHIPPED_MONTH, 
							  DateHelper.getMonthList(), 
							  getRawShippedMonth());
}
/**
 * Generates the HTML for the list of years for shipped date.
 *
 * @return  The HMTL that represents the year list.
 */
public String getShippedYearList() throws ApiException {
	return generateSelectList(ID_SHIPPED_YEAR, 
							  DateHelper.getYearList(2002, 2010), 
							  getRawShippedYear());
}
/**
 * Generates the HTML for the list of shopping carts for this
 * project's client.
 *
 * @return  The HMTL that represents the shopping cart list.
 */
public String getShoppingCartList() throws ApiException {
	if (_shoppingCartList == null) {
		try {
			String client = getRawClient();
			if (client != null) {
				_shoppingCartList = getListGenerator().getPtsShoppingCarts(client);
				_shoppingCartList.addLegalValue(0, "", "Select");
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
	}
	return generateSelectList(ID_SHOPPING_CART, _shoppingCartList);
}
/**
 * Generates the HTML for the list of shopping carts for this
 * project's client, and selects the specified cart in the list.
 *
 * @param  selectedCart  the cart to select in the list
 * @return  The HMTL that represents the shopping cart list.
 */
public String getShoppingCartList(String selectedCart) throws ApiException {
	if (_shoppingCartList == null) {
		try {
			String client = getRawClient();
			if (client != null) {
				_shoppingCartList = getListGenerator().getPtsShoppingCarts(client);
				_shoppingCartList.addLegalValue(0, "", "Select");
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
	}
	return generateSelectList(ID_SHOPPING_CART, _shoppingCartList, selectedCart);
}
/**
 * Returns this project's status, suitable for display. 
 * If the status is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This project's status.
 */
public String getStatus() throws ApiException {
	String status = getRawStatus();
	try {
		return (status == null) ? 
			"" : 
			getListGenerator().lookupArdLookupDescription(FormLogic.ARD_LOOKUP_PROJECT_STATUS, status);
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
/**
 * Generates the HTML for the list of project statuses.
 * Selects the correct status in the list if this helper is
 * for an existing project.
 *
 * @return  The HMTL that represents the status list.
 */
public String getStatusList() throws ApiException {
	if (_statusList == null) {
		try {
			_statusList = getListGenerator().getArdLookup(FormLogic.ARD_LOOKUP_PROJECT_STATUS);
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
	return generateSelectList(ID_STATUS, _statusList, getRawStatus());
}
/**
 * Returns the total number of samples in this project.
 * 
 * @return  The total number of samples.
 */
public String getTotalSamples() {
	return String.valueOf(_totalSamples);
}
/**
 * Returns the total number of samples in this project that are on hold.
 * 
 * @return  The total number of on hold samples.
 */
public String getTotalSamplesOnHold() {
	return String.valueOf(_totalSamplesOnHold);
}
/**
 * Generates the HTML for the list of shopping carts for this
 * project's client.
 *
 * @return  The HMTL that represents the shopping cart list.
 */
public String getUserList() throws ApiException {
	if (_userList == null) {
		try {
			String client = getRawClient();
			if (client != null) {
				_userList = getListGenerator().getPtsUsersByAccount(client);
				_userList.addLegalValue(0, "", "Select");
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
	}
	return generateSelectList(ID_SHOPPING_CART, _userList);
}
/**
 * Returns a <code>List</code> of <code>WorkOrderHelper</code>s,
 * that were added to this <code>ProjectHelper</code>.  If there
 * are no <code>WorkOrderHelper</code>s then an empty <code>List</code>
 * is returned, not <code>null</code>.
 *
 * @return  A <code>List</code> of <code>WorkOrderHelper</code>s.
 */
public List getWorkOrders() {
	if (_workOrders == null) {
		_workOrders = new ArrayList();
	}
	return _workOrders;
}
/**
 * Returns <code>true</code> if this helper is for a new
 * project; <code>false</code> otherwise.
 * 
 * @return  A boolean indicating whether this project is new.
 */
public boolean isNew() {
	return (isEmpty(getRawId()));
}
/**
 * Returns <code>true</code> if the project data associated 
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
	if (isEmpty(getRawProjectName())) {
		getMessageHelper().addMessage("Project Name must be specified");
		isValid = false;
	}
	if (isEmpty(getRawClient())) {
		getMessageHelper().addMessage("Client must be specified");
		isValid = false;
	}
	
	// Make sure that the request date is filled in and is
	// a valid date.
	try {
		DateHelper dateHelper = getRequestDateHelper();
		
		if (dateHelper == null) {
			getMessageHelper().addMessage("Request Date must be specified.");
			isValid = false;
		} else if (dateHelper.getDate().after(new java.util.Date())){
			getMessageHelper().addMessage("Request Date must be in the past.");
			isValid = false;
		}
		
	}
	catch (NumberFormatException e) {
		getMessageHelper().addMessage("Request Date is not a valid date.");
		isValid = false;
	}
	catch (IllegalArgumentException e) {
		getMessageHelper().addMessage("Request Date is not a valid date.");
		isValid = false;
	}

	// Make sure that the approved and shipped dates are valid dates.
	try {
		if ((!isEmpty(_approvedDay) || !isEmpty(_approvedMonth) || !isEmpty(_approvedYear))
			&& (isEmpty(_approvedDay) || isEmpty(_approvedMonth) || isEmpty(_approvedYear))) {
			getMessageHelper().addMessage("Approved Date not fully specified.");
			isValid = false;
		}
		else {
			DateHelper dateHelper = getApprovedDateHelper();
			if(dateHelper != null && dateHelper.getDate().after(new java.util.Date())){
				getMessageHelper().addMessage("Approved date must be in the past.");
				isValid = false;
			}
		}
		
	}
	catch (NumberFormatException e) {
		getMessageHelper().addMessage("Approved Date is not a valid date.");
		isValid = false;
	}
	catch (IllegalArgumentException e) {
		getMessageHelper().addMessage("Approved Date is not a valid date.");
		isValid = false;
	}
	try {
		if ((!isEmpty(_shippedDay) || !isEmpty(_shippedMonth) || !isEmpty(_shippedYear))
			&& (isEmpty(_shippedDay) || isEmpty(_shippedMonth) || isEmpty(_shippedYear))) {
			getMessageHelper().addMessage("Shipped Date not fully specified.");
			isValid = false;
		}
		else {
			DateHelper dateHelper = getShippedDateHelper();
			if(dateHelper != null && dateHelper.getDate().after(new java.util.Date())){
				getMessageHelper().addMessage("Shipped date must be in the past.");
				isValid = false;
			}
		}
	}
	catch (NumberFormatException e) {
		getMessageHelper().addMessage("Shipped Date is not a valid date.");
		isValid = false;
	}
	catch (IllegalArgumentException e) {
		getMessageHelper().addMessage("Shipped Date is not a valid date.");
		isValid = false;
	}

	// Make sure percent complete is a whole number.
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
/**
 * Returns a <code>ProjectHelper</code> that is initialized from the
 * <code>HttpServletRequest</code>.  A new <code>ProjectHelper</code>
 * is created from one of the standard request parameters or attributes
 * that are used to hold a project id or project data bean.  This method 
 * is intended to be called from the first op of the many transactions
 * that require a project to be selected and "prepared" for the transaction,
 * and it performs many of the common actions needed by those prepare
 * ops, including setting the returned helper as a request attribute and
 * saving the project id in the HTTP session.
 *
 * This method is not intended to be called to create or update a project.
 * For that, use the constructor that takes an <code>HttpServletRequest</code>.
 *
 * @param  the <code>HttpServletRequest</code>
 * @return  A <code>ProjectHelper</code> for the project specified by
 *			a request parameter/attribute.
 * @throws  <code>ServletException</code> if a request parameter/attribute
 *		that identifies a project is not found.
 * @throws  <code>ApiException</code> if there is a problem looking up the
 *		specified project.
 * @see #ProjectHelper(HttpServletRequest)
 */
public static ProjectHelper prepareFromRequest(HttpServletRequest request) throws ServletException, ApiException {
	String projectId = request.getParameter(ProjectHelper.ID_PROJECT_ID);
	ProjectDataBean dataBean = (ProjectDataBean)request.getAttribute(JspHelper.ID_DATA_BEAN);
	ProjectHelper helper = null;

	// If there is a data bean set as a request attribute,
	// use the data bean to create the project helper.
	if (dataBean != null) {
		helper = new ProjectHelper(dataBean);
		projectId = dataBean.getId();
	}

	// If there is a project id in the request, then use it to look
	// up the project and create the helper from the returned data bean.
	else if (projectId != null) {
		try {
      PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
      PtsOperation ptsOp = home.create();
			helper = new ProjectHelper(ptsOp.getProject(projectId));
		}
		catch (javax.ejb.CreateException e) {
			throw new ApiException("Error retriving project information", e);
		}
		catch (javax.naming.NamingException e) {
			throw new ApiException("Error retriving project information", e);
		}
    catch (java.rmi.RemoteException e) {
      throw new ApiException("Error retriving project information", e);
    }
    catch (ClassNotFoundException e) {
      throw new ApiException("Error retriving project information", e);
    }
	}

	// If there isn't a project bean or id in the request, then
	// throw an exception since we don't know which project is
	// being requested.
	else {
		throw new ServletException("Required request parameter/attribute not found.");
	}

	request.getSession(false).setAttribute(ProjectHelper.ID_PROJECT_ID, projectId);
	request.setAttribute(JspHelper.ID_HELPER, helper);
	
	return helper;
}
/**
 * Sets the <code>SampleTableHelper</code> that will be used to generate
 * the table of samples.
 * 
 * @param  helper  the <code>SampleTableHelper</code>
 */
public void setSampleTableHelper(SampleTableHelper helper) {
	_tableHelper = helper;
}
/**
 * Sets the total number of samples in this project.
 * 
 * @param  total  The total number of samples.
 */
public void setTotalSamples(int total) {
	_totalSamples = total;
}
/**
 * Sets the total number of samples in this project that are on hold
 * 
 * @param  total  The total number of on hold samples.
 */
public void setTotalSamplesOnHold(int total) {
	_totalSamplesOnHold = total;
}
}
