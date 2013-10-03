package com.ardais.bigr.iltds.assistants;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * This class represents the data for a line item.
 */
public class LineItemDataBean implements Serializable {
	private String _comments;
	private String _format;
	private String _id;
	private Integer _lineItemNumber;
	private String _notes;
	private String _projectId;
	private Integer _quantity;
	private Integer _selectedQuantity;

	// Holds the samples for this line item.
	private ArrayList _samples;

/**
 * Creates a new empty <code>LineItemDataBean</code>.
 */
public LineItemDataBean() {
	super();
}
/**
 * Creates a new <code>LineItemDataBean</code> that is initialized
 * from the specified <code>LineItemDataBean</code>.
 *
 * @param  copyFrom  the <code>LineItemDataBean</code> to use to
 *		initialize this <code>LineItemDataBean</code>
 */
public LineItemDataBean(LineItemDataBean copyFrom) {
	this();
	setComments(copyFrom.getComments());
	setFormat(copyFrom.getFormat());
	setId(copyFrom.getId());
	setLineItemNumber(copyFrom.getLineItemNumber());
	setNotes(copyFrom.getNotes());
	setProjectId(copyFrom.getProjectId());
	setQuantity(copyFrom.getQuantity());
	setSelectedQuantity(copyFrom.getSelectedQuantity());
}
/**
 * Creates a new <code>LineItemDataBean</code> from the
 * specified <code>ResultSet</code>.  The <code>LineItemDataBean</code>
 * is initialized from the data at the current cursor position
 * within the <code>ResultSet</code>.  No attempt is made to
 * determine whether the <code>ResultSet</code> is positioned on a
 * valid row.
 *
 * @param  rs  the <code>ResultSet</code>
 */
public LineItemDataBean(ResultSet rs) throws SQLException {
	this();
	setComments(rs.getString("comments"));
	setFormat(rs.getString("format"));
	setId(rs.getString("lineitemid"));
	if (rs.getString("lineitemnumber") != null) {
		setLineItemNumber(new Integer(rs.getInt("lineitemnumber")));
	}
	setNotes(rs.getString("notes"));
	setProjectId(rs.getString("projectid"));
	if (rs.getString("quantity") != null) {
		setQuantity(new Integer(rs.getInt("quantity")));
	}
}
/**
 * Adds a <code>SampleDataBean</code>to this <code>LineItemDataBean</code>.
 * The sample is added to the end of an ordered list.
 *
 * @param  dataBean  the <code>SampleDataBean</code>
 */
public void addSample(SampleDataBean dataBean) {
	if (_samples == null) {
		_samples = new ArrayList();
	}
	_samples.add(dataBean);
}
/**
 * Returns the line item's comments.
 * 
 * @return  The comments.
 */
public String getComments() {
	return _comments;
}
/**
 * Returns the line item's format.
 * 
 * @return  The format.
 */
public String getFormat() {
	return _format;
}
/**
 * Returns the line item's id.
 * 
 * @return  The id.
 */
public String getId() {
	return _id;
}
/**
 * Returns the line item number.
 * 
 * @return  The line item number.
 */
public Integer getLineItemNumber() {
	return _lineItemNumber;
}
/**
 * Returns the line item's notes.
 * 
 * @return  The notes.
 */
public String getNotes() {
	return _notes;
}
/**
 * Returns the project id with which this line item is associated.
 * 
 * @return  The project id.
 */
public String getProjectId() {
	return _projectId;
}
/**
 * Returns the line item's requested quantity of samples.
 * 
 * @return  The quantity.
 */
public Integer getQuantity() {
	return _quantity;
}
/**
 * Returns a <code>List</code> of <code>SampleDataBean</code>s
 * that were added to this <code>LineItemDataBean</code>, in the
 * order they were added.  If no samples were added, then an
 * empty <code>List</code> is returned.
 *
 * @return  A <code>List</code> of <code>SampleDataBean</code>s.
 */
public List getSamples() {
	if (_samples == null) {
		_samples = new ArrayList();
	}
	return _samples;
}
/**
 * Returns the quantity of samples selected for this line item.
 * 
 * @return  The quantity of samples selected.
 */
public Integer getSelectedQuantity() {
	return _selectedQuantity;
}
/**
 * Sets the line item's comments.
 * 
 * @param  comments  the comments
 */
public void setComments(String comments) {
	_comments = comments;
}
/**
 * Sets the line item's format.
 * 
 * @param  format  the format
 */
public void setFormat(String format) {
	_format = format;
}
/**
 * Sets the line item's id.
 * 
 * @param  lineItemId  the id
 */
public void setId(String id) {
	_id = id;
}
/**
 * Sets the line item number.
 * 
 * @param  lineItemNumber  the line item number
 */
public void setLineItemNumber(Integer lineItemNumber) {
	_lineItemNumber = lineItemNumber;
}
/**
 * Sets the line item's notes.
 * 
 * @param  notes  the notes
 */
public void setNotes(String notes) {
	_notes = notes;
}
/**
 * Sets the project id with which this line item is associated.
 * 
 * @param  projectId  the project id
 */
public void setProjectId(String projectId) {
	_projectId = projectId;
}
/**
 * Sets the line item's requested quantity of samples.
 * 
 * @param  quantity  the quantity
 */
public void setQuantity(Integer quantity) {
	_quantity = quantity;
}
/**
 * Sets the quantity of samples selected for this line item.
 * 
 * @param  selectedQuantity  the quantity of samples selected
 */
public void setSelectedQuantity(Integer selectedQuantity) {
	_selectedQuantity = selectedQuantity;
}
}
