package com.ardais.bigr.iltds.helpers;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.assistants.*;
import java.util.*;
import javax.servlet.*;

/**
 * <code>WorkOrderHelper</code> aids in the generation of JSPs
 * that allow viewing and editing of line items.
 */
public class LineItemHelper extends JspHelper {

	// Constants that specify request parameter names.
	public static final String ID_COMMENTS = "comments";
	public static final String ID_FORMAT = "format";
	public static final String ID_LINEITEM_ID = "lineItemId";
	public static final String ID_LINEITEM_NUMBER = "lineItemNum";
	public static final String ID_NOTES = "notes";
	public static final String ID_QUANTITY = "quantity";
	
	// Values that are read directly from request parameters.
	private String _comments;
	private String _format;
	private String _id;
	private String _lineItemNumber;
	private String _notes;
	private String _projectId;
	private String _quantity;

	// The data bean that holds the line item data.
	private LineItemDataBean _dataBean;

	// Legal value sets that represent the contents of
	// dropdowns.
	private LegalValueSet _formatList;

	// Holds the samples for this line item.
	private ArrayList _samples;

	// Used to display the samples in the project in an HTML table.
	private SampleTableHelper _tableHelper;
	
	// Used to display the samples not returned in the query -- these are important
	// for users' of PTS to see...
	private List _samplesNotReturned;
/**
 * Creates a new <code>LineItemHelper</code>.
 */
public LineItemHelper() {
	super();
}
/**
 * Creates a <code>LineItemHelper</code>, initializing its data
 * from the specified data bean.
 */
public LineItemHelper(LineItemDataBean dataBean) {
	this();
	_dataBean = dataBean;

	List sampleDataBeans = _dataBean.getSamples();
	int count = sampleDataBeans.size();
	for (int i = 0; i < count; i++) {
		SampleDataBean sampleBean = (SampleDataBean)sampleDataBeans.get(i);
		addSample(new SampleHelper(sampleBean));
	}
}
/**
 * Creates a new <code>LineItemHelper</code> for the project
 * specified by the project id.
 *
 * @param  id  the project id
 */
public LineItemHelper(String projectId) {
	this();
	_projectId = projectId;
}
/**
 * Creates a <code>LineItemHelper</code>, initializing its data
 * from the specified request.
 */
public LineItemHelper(ServletRequest request) {
	this();
	_comments = safeTrim(request.getParameter(ID_COMMENTS));
	_format = safeTrim(request.getParameter(ID_FORMAT));
	_id = safeTrim(request.getParameter(ID_LINEITEM_ID));
	_lineItemNumber = safeTrim(request.getParameter(ID_LINEITEM_NUMBER));
	_notes = safeTrim(request.getParameter(ID_NOTES));
	_projectId = safeTrim(request.getParameter(ProjectHelper.ID_PROJECT_ID));
	_quantity = safeTrim(request.getParameter(ID_QUANTITY));
}
/**
 * Adds a <code>SampleHelper</code>to this <code>LineItemHelper</code>.
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
 * Returns this line item's comments, suitable for display.  If the
 * comments are <code>null</code>, then the empty string is returned.
 * 
 * @return  This line item's comments.
 */
public String getComments() {
	String comments = getRawComments();
	return (comments == null) ? "" : comments;
}
/**
 * Returns the <code>LineItemDataBean</code> that contains
 * the line item data fields associated with this 
 * <code>LineItemHelper</code>.
 * 
 * @return  The <code>LineItemDataBean</code>.
 */
public LineItemDataBean getDataBean() throws ApiException {
	if (_dataBean == null) {
		_dataBean = new LineItemDataBean();
		
		_dataBean.setComments(getRawComments());
		_dataBean.setFormat(getRawFormat());
		if (!isEmpty(getRawLineItemNumber())) {
			_dataBean.setLineItemNumber(Integer.valueOf(getRawLineItemNumber()));
		}
		_dataBean.setNotes(getRawNotes());
		_dataBean.setProjectId(getRawProjectId());
		if (!isEmpty(getRawQuantity())) {
			_dataBean.setQuantity(Integer.valueOf(getRawQuantity()));
		}
		
		if (!isNew()) {
			_dataBean.setId(getRawId());
		}
	}
	return _dataBean;
}
/**
 * Returns this line item's format, suitable for display.  If the
 * format is <code>null</code>, then the empty string is returned.
 * 
 * @return  This line item's format.
 */
public String getFormat() {
	String format = getRawFormat();
	return (format == null) ? "" : format;
}
/**
 * Generates the HTML for the list of formats.
 *
 * @return  The HMTL that represents the formats.
 */
public String getFormatList() throws ApiException {
	if (_formatList == null) {
		try {
			_formatList = getListGenerator().getFinishedProductFormats();
			_formatList.addLegalValue(0, "", "Select a Format");
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
	return generateSelectList(ID_FORMAT, _formatList, getRawFormat());
}
/**
 * Returns this line item's id, suitable for display. 
 * If the id is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This line item's id.
 */
public String getId() {
	String id = getRawId();
	return (id == null) ? "" : id;
}
/**
 * Returns this line item's line number, suitable for display. 
 * If the line number is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This line item's line number.
 */
public String getLineItemNumber() {
	String number = getRawLineItemNumber();
	return (number == null) ? "" : number;
}
/**
 * Returns this line item's notes, suitable for display.  If the
 * notes are <code>null</code>, then the empty string is returned.
 * 
 * @return  This line item's notes.
 */
public String getNotes() {
	String notes = getRawNotes();
	return (notes == null) ? "" : notes;
}
/**
 * Returns this line item's project id, suitable for display. 
 * If the id is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This line item's project id.
 */
public String getProjectId() {
	String id = getRawProjectId();
	return (id == null) ? "" : id;
}
/**
 * Returns this line item's requested quantity, suitable for display. 
 * If the quantity is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This line item's quantity.
 */
public String getQuantity() {
	String quantity = getRawQuantity();
	return (quantity == null) ? "" : quantity;
}
/**
 * Returns this line items's comments.    If the comments are
 * <code>null</code>, then <code>null</code> is returned.
 * 
 * @return  The comments.
 */
private String getRawComments() {
	if (_comments != null) {
		return _comments;
	}
	else if (_dataBean != null) {
		return _dataBean.getComments();
	}
	return null;
}
/**
 * Returns this line items's format.  If the format is
 * <code>null</code>, then <code>null</code> is returned
 *
 * @return  The format.
 */
private String getRawFormat() {
	if (_format != null) {
		return _format;
	}
	else if (_dataBean != null) {
		return _dataBean.getFormat();
	}
	return null;
}
/**
 * Returns this line item's id.  If the id is null, then
 * null is returned.
 * 
 * @return  The line item's id.
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
 * Returns this line item's line number.    If the line number is
 * <code>null</code>, then <code>null</code> is returned.
 * 
 * @return  The line item's line number.
 */
public String getRawLineItemNumber() {
	if (_lineItemNumber != null) {
		return _lineItemNumber;
	}
	else if (_dataBean != null) {
		Integer number = _dataBean.getLineItemNumber();
		if (number != null) {
			return number.toString();
		}
	}
	return null;
}
/**
 * Returns the line items's notes.    If the notes are
 * <code>null</code>, then <code>null</code> is returned.
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
 * Returns this line item's project id.  If the id is null, then
 * null is returned.
 * 
 * @return  The line item's project id.
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
 * Returns this line item's quantity.    If the quantity is
 * <code>null</code>, then <code>null</code> is returned.
 * 
 * @return  The line item's quantity.
 */
private String getRawQuantity() {
	if (_quantity != null) {
		return _quantity;
	}
	else if (_dataBean != null) {
		Integer quantity = _dataBean.getQuantity();
		if (quantity != null) {
			return quantity.toString();
		}
	}
	return null;
}
/**
 * Returns the quantity of selected samples for this line item.  If the quantity is
 * <code>null</code>, then <code>null</code> is returned.
 * 
 * @return  The line item's selected sample quantity.
 */
private String getRawSelectedQuantity() {
	if (_dataBean != null) {
		Integer quantity = _dataBean.getSelectedQuantity();
		if (quantity != null) {
			return quantity.toString();
		}
	}
	return null;
}
/**
 * Returns an <code>Iterator</code> of <code>SampleHelper</code>s
 * that were added to this <code>LineItemHelper</code>.
 *
 * @return  An <code>Iterator</code> of <code>SampleHelper</code>s.
 */
public Iterator getSamples() {
	if (_samples == null) {
		_samples = new ArrayList();
	}
	return _samples.iterator();
}
/**
 * Returns  a List of Samples that were not Returned by the 
 * availableInvQuery(), but that were specified in the list
 * of sample ids in the UI
 */
public List getSamplesNotReturned() {
	return _samplesNotReturned;
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
 * Returns the quantity of samples selected for this line item, suitable 
 * for display.  If the quantity is <code>null</code>, then the empty string
 * is returned.
 * 
 * @return  This line item's quantity of selected samples.
 */
public String getSelectedQuantity() {
	String quantity = getRawSelectedQuantity();
	return (quantity == null) ? "" : quantity;
}
/**
 * Returns <code>true</code> if this line items has comments;
 * <code>false</code> otherwise.
 * 
 * @return  A boolean indicating whether this line item has comments.
 */
public boolean hasComments() {
	return (getRawComments() != null);
}
/**
 * Returns <code>true</code> if this line items has notes;
 * <code>false</code> otherwise.
 * 
 * @return  A boolean indicating whether this line item has notes.
 */
public boolean hasNotes() {
	return (getRawNotes() != null);
}
/**
 * Returns <code>true</code> if this helper is for a new
 * line item; <code>false</code> otherwise.
 * 
 * @return  A boolean indicating whether this line item is new.
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
	if (isEmpty(getRawQuantity())) {
		getMessageHelper().addMessage("Quantity must be specified");
		isValid = false;
	}
	
	if (isEmpty(getNotes())) {
		getMessageHelper().addMessage("Description must be specified");
		isValid = false;
	}

	// Make sure the quantity is a whole number.
	if (!isEmpty(getRawQuantity())) {
		try {
			int quantity = Integer.parseInt(getRawQuantity());
			if (quantity < 0) {
				getMessageHelper().addMessage("Quantity cannot be a negative number.");
				isValid = false;
			}
		}
		catch (NumberFormatException e) {
			getMessageHelper().addMessage("Quantity must be a whole number.");
			isValid = false;
		}
	}
		
	if (!isValid) {
		getMessageHelper().setError(true);
	}
	return isValid;
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
 * Sets a List of Samples that were not Returned by the 
 * availableInvQuery(), but that were specified in the list
 * of sample ids in the UI
 */
public void setSamplesNotReturned(List samplesNotReturned) {
	_samplesNotReturned = samplesNotReturned;
}
}