package com.ardais.bigr.iltds.assistants;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.gen.DbAliases;

/**
 * This class represents the data for a project.
 */
public class ProjectDataBean implements Serializable {
	private java.sql.Date _approvedDate;
	private String _client;
	private String _id;
	private String _notes;
	private Integer _percentComplete;
	private String _projectName;
	private String _requestedBy;
	private java.sql.Date _requestDate;
	private java.sql.Date _shippedDate;
	private String _status;

	// Holds the line items in this project.
	private ArrayList _lineItems;

/**
 * Creates a new, empty <code>ProjectDataBean</code>.
 */
public ProjectDataBean() {
	super();
}

	/**
	 * Creates a new <code>ProjectDataBean</code>, initialized from
	 * the data in the current row of the result set.
	 *
	 * @param  columns  a <code>Map</code> containing a key for each column in
	 * 									 the <code>ResultSet</code>.  Each key must be one of the
	 * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
	 * 									 and the corresponding value is ignored.
	 * @param  rs  the <code>ResultSet</code>
	 */
	public ProjectDataBean(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
	}

	/**
	 * Populates this <code>AsmData</code> from the data in the current row
	 * of the result set.
	 * 
	 * @param  columns  a <code>Map</code> containing a key for each column in
	 * 									 the <code>ResultSet</code>.  Each key must be one of the
	 * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
	 * 									 and the corresponding value is ignored.
	 * @param  rs  the <code>ResultSet</code>
	 */
	public void populate(Map columns, ResultSet rs) {
    try {
	    if (columns.containsKey(DbAliases.PROJECT_NAME)) {
	    	setProjectName(rs.getString(DbAliases.PROJECT_NAME));
	    }
	    if (columns.containsKey(DbAliases.PROJECT_DATE_REQUESTED)) {
	    	setRequestDate(rs.getDate(DbAliases.PROJECT_DATE_REQUESTED));
	    }

    } catch (SQLException e) {
			ApiFunctions.throwAsRuntimeException(e);
    }
	}


/**
 * Creates a new <code>ProjectDataBean</code> that is initialized
 * from the specified <code>ProjectDataBean</code>.
 *
 * @param  copyFrom  the <code>ProjectDataBean</code> to use to
 *		initialize this <code>ProjectDataBean</code>
 */
public ProjectDataBean(ProjectDataBean copyFrom) {
	this();
  if (copyFrom.getApprovedDate() != null) {
    setApprovedDate((Date) copyFrom.getApprovedDate().clone());
  }
	setClient(copyFrom.getClient());
	setId(copyFrom.getId());
	setNotes(copyFrom.getNotes());
	setPercentComplete(copyFrom.getPercentComplete());
	setProjectName(copyFrom.getProjectName());
  if (copyFrom.getRequestDate() != null) {
    setRequestDate((Date) copyFrom.getRequestDate().clone());
  }
	setRequestedBy(copyFrom.getRequestedBy());
  if (copyFrom.getShippedDate() != null) {
    setShippedDate((Date) copyFrom.getShippedDate().clone());
  }
	setStatus(copyFrom.getStatus());
}
/**
 * Creates a new <code>ProjectDataBean</code> from the
 * specified <code>ResultSet</code>.  The <code>ProjectDataBean</code>
 * is initialized from the data at the current cursor position
 * within the <code>ResultSet</code>.  No attempt is made to
 * determine whether the <code>ResultSet</code> is positioned on a
 * valid row.
 *
 * @param  rs  the <code>ResultSet</code>
 */
public ProjectDataBean(ResultSet rs) throws SQLException {
	this();
	setApprovedDate(rs.getDate("dateapproved"));
	setClient(rs.getString("ardais_acct_key"));
	setId(rs.getString("projectid"));
	setNotes(rs.getString("notes"));
	if (rs.getString("percentcomplete") != null) {
		setPercentComplete(new Integer(rs.getInt("percentcomplete")));
	}
	setProjectName(rs.getString("projectname"));
	setRequestDate(rs.getDate("daterequested"));
	setRequestedBy(rs.getString("ardais_user_id"));
	setShippedDate(rs.getDate("dateshipped"));
	setStatus(rs.getString("status"));	
}
/**
 * Adds a <code>LineItemDataBean</code>to this <code>ProjectDataBean</code>.
 * The line item is added to the end of an ordered list.
 *
 * @param  dataBean  the <code>LineItemDataBean</code>
 */
public void addLineItem(LineItemDataBean dataBean) {
	if (_lineItems == null) {
		_lineItems = new ArrayList();
	}
	_lineItems.add(dataBean);
}
/**
 * Returns the approved date.
 * 
 * @return  The approved date.
 */
public java.sql.Date getApprovedDate() {
	return _approvedDate;
}
/**
 * Returns the Ardais account key of the client for
 * this project.
 * 
 * @return  The Ardais account key.
 */
public String getClient() {
	return _client;
}
/**
 * Returns this project's id.
 * 
 * @return  This project's id.
 */
public String getId() {
	return _id;
}
/**
 * Returns a <code>List</code> of <code>LineItemDataBean</code>s,
 * that were added to this <code>ProjectDataBean</code>.  If there
 * are no <code>LineItemDataBean</code>s then an empty <code>List</code>
 * is returned.
 *
 * @return  A <code>List</code> of <code>LineItemDataBean</code>s.
 */
public List getLineItems() {
	if (_lineItems == null) {
		_lineItems = new ArrayList();
	}
	return _lineItems;
}
/**
 * Returns the project's notes.
 * 
 * @return  The project's notes.
 */
public String getNotes() {
	return _notes;
}
/**
 * Returns the percent completion of the project.
 * 
 * @return  The project's percent completion.
 */
public Integer getPercentComplete() {
	return _percentComplete;
}
/**
 * Returns the project name.
 * 
 * @return  The project name.
 */
public String getProjectName() {
	return _projectName;
}
/**
 * Returns the request date of the project.
 * 
 * @return  The project's request date.
 */
public java.sql.Date getRequestDate() {
	return _requestDate;
}
/**
 * Returns the user id of the requesting user.
 * 
 * @return  The user id.
 */
public String getRequestedBy() {
	return _requestedBy;
}
/**
 * Returns the date on which the samples in the project
 * were shipped.
 * 
 * @return  The shipped date.
 */
public java.sql.Date getShippedDate() {
	return _shippedDate;
}
/**
 * Returns the project's status.
 * 
 * @return  The project's status.
 */
public String getStatus() {
	return _status;
}
/**
 * Resets the state of this <code>ProjectDataBean</code>.
 */
public void reset() {
	_projectName = null;
	_client = null;
	_requestedBy = null;
	_status = null;
	_requestDate = null;
	_approvedDate = null;
	_shippedDate = null;
	_notes = null;
	_percentComplete = null;
}
/**
 * Sets the approved date.
 * 
 * @param  approvedDate  the new approved date
 */
public void setApprovedDate(java.sql.Date approvedDate) {
	_approvedDate = approvedDate;
}
/**
 * Sets the Ardais account key of the client for
 * this project.
 * 
 * @param  client  the Ardais account key
 */
public void setClient(String client) {
	_client = client;
}
/**
 * Sets this project's id.
 * 
 * @param  id  this project's id
 */
public void setId(String id) {
	_id = id;
}
/**
 * Sets the project's notes.
 * 
 * @param  notes  the project's notes
 */
public void setNotes(String notes) {
	_notes = notes;
}
/**
 * Sets the percent completion of the project.
 * 
 * @param  percentComplete  the percent completion.
 */
public void setPercentComplete(Integer percentComplete) {
	_percentComplete = percentComplete;
}
/**
 * Sets the project name.
 * 
 * @param  projectName  the project name
 */
public void setProjectName(String projectName) {
	_projectName = projectName;
}
/**
 * Sets the request date of the project.
 * 
 * @param  requestDate  the request date
 */
public void setRequestDate(java.sql.Date requestDate) {
	_requestDate = requestDate;
}
/**
 * Sets the user id of the user requesting the project.
 * 
 * @param  requestedBy  the user id
 */
public void setRequestedBy(String requestedBy) {
	_requestedBy = requestedBy;
}
/**
 * Sets the date on which the samples in the project
 * were shipped.
 * 
 * @param  shippedDate  the shipped date
 */
public void setShippedDate(java.sql.Date shippedDate) {
	_shippedDate = shippedDate;
}
/**
 * Sets the project's status.
 * 
 * @param  status  the status
 */
public void setStatus(String status) {
	_status = status;
}
}
