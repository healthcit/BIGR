package com.ardais.bigr.iltds.assistants;

import java.io.*;
import java.sql.*;

/**
 * This class represents the data for a work order.
 */
public class WorkOrderDataBean implements Serializable {
	private Date _endDate;
	private String _id;
	private Integer _listOrder;
	private String _notes;
	private Integer _numberOfSamples;
	private Integer _percentComplete;
	private String _projectId;
	private Date _startDate;
	private String _status;
	private String _type;
	private String _workOrderName;
/**
 * Creates a new empty <code>WorkOrderDataBean</code>.
 */
public WorkOrderDataBean() {
	super();
}
/**
 * Creates a <code>WorkOrderDataBean</code> that is initialized
 * from the specified <code>WorkOrderDataBean</code>.
 *
 * @param  copyFrom  the <code>WorkOrderDataBean</code> to use to
 *		initialize this <code>WorkOrderDataBean</code>
 */
public WorkOrderDataBean(WorkOrderDataBean copyFrom) {
	this();
	setEndDate(copyFrom.getEndDate());
	setId(copyFrom.getId());
	setListOrder(copyFrom.getListOrder());
	setNotes(copyFrom.getNotes());
	setNumberOfSamples(copyFrom.getNumberOfSamples());
	setPercentComplete(copyFrom.getPercentComplete());
	setProjectId(copyFrom.getProjectId());
	setStartDate(copyFrom.getStartDate());
	setStatus(copyFrom.getStatus());
	setType(copyFrom.getType());
	setWorkOrderName(copyFrom.getWorkOrderName());
}
/**
 * Creates a new <code>WorkOrderDataBean</code> from the
 * specified <code>ResultSet</code>.  The <code>WorkOrderDataBean</code>
 * is initialized from the data at the current cursor position
 * within the <code>ResultSet</code>.  No attempt is made to
 * determine whether the <code>ResultSet</code> is positioned on a
 * valid row.
 *
 * @param  rs  the <code>ResultSet</code>
 */
public WorkOrderDataBean(ResultSet rs) throws SQLException {
	this();
	setEndDate(rs.getDate("enddate"));
	setId(rs.getString("workorderid"));
	if (rs.getString("listorder") != null) {
		setListOrder(new Integer(rs.getInt("listorder")));
	}
	setNotes(rs.getString("notes"));
	if (rs.getString("numberofsamples") != null) {
		setNumberOfSamples(new Integer(rs.getInt("numberofsamples")));
	}
	if (rs.getString("percentcomplete") != null) {
		setPercentComplete(new Integer(rs.getInt("percentcomplete")));
	}
	setProjectId(rs.getString("projectid"));
	setStartDate(rs.getDate("startdate"));
	setStatus(rs.getString("status"));
	setType(rs.getString("workordertype"));
	setWorkOrderName(rs.getString("workordername"));
}
/**
 * Returns this work order's end date.
 * 
 * @return  The end date.
 */
public Date getEndDate() {
	return _endDate;
}
/**
 * Returns the work order's id.
 * 
 * @return  The id.
 */
public String getId() {
	return _id;
}
/**
 * Returns the list order of this work order within the project.
 * 
 * @return  The list order.
 */
public Integer getListOrder() {
	return _listOrder;
}
/**
 * Returns this work order's notes.
 * 
 * @return  The notes.
 */
public String getNotes() {
	return _notes;
}
/**
 * Returns the number of samples in this work order.
 * 
 * @return  The number of samples.
 */
public Integer getNumberOfSamples() {
	return _numberOfSamples;
}
/**
 * Returns the work order's percentage completion.
 * 
 * @return  The percentage completion.
 */
public Integer getPercentComplete() {
	return _percentComplete;
}
/**
 * Returns the project id with which this work order is associated.
 * 
 * @return  The project id.
 */
public String getProjectId() {
	return _projectId;
}
/**
 * Returns the work order's start date.
 * 
 * @return  The start date.
 */
public Date getStartDate() {
	return _startDate;
}
/**
 * Returns the work order's status.
 * 
 * @return  The status.
 */
public String getStatus() {
	return _status;
}
/**
 * Returns the work order's type.
 * 
 * @return  The type.
 */
public String getType() {
	return _type;
}
/**
 * Returns the work order's name.
 * 
 * @return  The name.
 */
public String getWorkOrderName() {
	return _workOrderName;
}
/**
 * Sets the work order's end date.
 * 
 * @param  endDate  the end date
 */
public void setEndDate(Date endDate) {
	_endDate = endDate;
}
/**
 * Sets the work order's id.
 * 
 * @param  id  the id
 */
public void setId(String id) {
	_id = id;
}
/**
 * Sets the list order of this work order within the project.
 * 
 * @param  listOrder  the list order
 */
public void setListOrder(Integer listOrder) {
	_listOrder = listOrder;
}
/**
 * Sets the work order's notes.
 * 
 * @param  notes  the notes
 */
public void setNotes(String notes) {
	_notes = notes;
}
/**
 * Sets the number of samples in the work order.
 * 
 * @param  numberOfSamples  the number of samples
 */
public void setNumberOfSamples(Integer numberOfSamples) {
	_numberOfSamples = numberOfSamples;
}
/**
 * Sets the percentage complete of the work order.
 * 
 * @param  percentComplete  the percentage complete 
 */
public void setPercentComplete(Integer percentComplete) {
	_percentComplete = percentComplete;
}
/**
 * Sets the project id with which this work order is associated.
 * 
 * @param  projectId  the project id
 */
public void setProjectId(String projectId) {
	_projectId = projectId;
}
/**
 * Sets the work order's start date.
 * 
 * @param  startDate  the start date
 */
public void setStartDate(Date startDate) {
	_startDate = startDate;
}
/**
 * Sets the work order's status.
 * 
 * @param  status  the status
 */
public void setStatus(String status) {
	_status = status;
}
/**
 * Sets the work order's type.
 * 
 * @param  type  the type
 */
public void setType(String type) {
	_type = type;
}
/**
 * Sets the work order's name.
 * 
 * @param  workOrderName  the name
 */
public void setWorkOrderName(String workOrderName) {
	_workOrderName = workOrderName;
}
}
