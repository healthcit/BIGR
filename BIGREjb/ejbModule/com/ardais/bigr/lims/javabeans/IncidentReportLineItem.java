package com.ardais.bigr.lims.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.util.gen.DbAliases;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * @author rnelson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class IncidentReportLineItem implements Serializable {
	private static final long serialVersionUID = 8571130068330141654L;
  
  boolean _save;
	String _caseId;
	String _asmPosition;
	String _sampleId;
	String _slideId;
	String _pathologist;
	String _action;
  String _actionDescription;
  String _actionOther;
  String _reason;
  String _requestorCode;
  String _generalComments;
  String _incidentId;
  String _createUser;
  Timestamp _createDate;
  String _pullReason;

  /**
   * Creates a new <code>PathologyEvaluationData</code>.
   */
  public IncidentReportLineItem() {
  }
  
  /**
   * Creates a new <code>PathologyEvaluationData</code>, initialized from
   * the data in the current row of the result set.
   *
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public IncidentReportLineItem(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
  }
  
  /**
   * Populates this <code>IncidentReportLineItem</code> from the data in the current row of 
   * the result set.
   * 
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public void populate(Map columns, ResultSet rs) {
      try {
        if (columns.containsKey(DbAliases.INCIDENT_ID)) {
          setIncidentId(rs.getString(DbAliases.INCIDENT_ID));
        }
        if (columns.containsKey(DbAliases.LIMS_INCIDENT_CREATE_USER)) {
          setCreateUser(rs.getString(DbAliases.LIMS_INCIDENT_CREATE_USER));
        }
        if (columns.containsKey(DbAliases.LIMS_INCIDENT_CREATE_DATE)) {
          setCreateDate(rs.getTimestamp(DbAliases.LIMS_INCIDENT_CREATE_DATE));
        }
        if (columns.containsKey(DbAliases.LIMS_INCIDENT_SAMPLE_ID)) {
          setSampleId(rs.getString(DbAliases.LIMS_INCIDENT_SAMPLE_ID));
        }
        if (columns.containsKey(DbAliases.LIMS_INCIDENT_CONSENT_ID)) {
          setCaseId(rs.getString(DbAliases.LIMS_INCIDENT_CONSENT_ID));
        }
        if (columns.containsKey(DbAliases.LIMS_INCIDENT_ACTION)) {
          setAction(rs.getString(DbAliases.LIMS_INCIDENT_ACTION));
        }
        if (columns.containsKey(DbAliases.LIMS_INCIDENT_REASON)) {
          setReason(rs.getString(DbAliases.LIMS_INCIDENT_REASON));
        }
        if (columns.containsKey(DbAliases.LIMS_INCIDENT_SLIDE_ID)) {
          setSlideId(rs.getString(DbAliases.LIMS_INCIDENT_SLIDE_ID));
        }
        if (columns.containsKey(DbAliases.LIMS_INCIDENT_PATHOLOGIST)) {
          setPathologist(rs.getString(DbAliases.LIMS_INCIDENT_PATHOLOGIST));
        }
        if (columns.containsKey(DbAliases.LIMS_INCIDENT_RE_PV_REQUESTOR_CODE)) {
          setRequestorCode(rs.getString(DbAliases.LIMS_INCIDENT_RE_PV_REQUESTOR_CODE));
        }
        if (columns.containsKey(DbAliases.LIMS_INCIDENT_ACTION_OTHER)) {
          setActionOther(rs.getString(DbAliases.LIMS_INCIDENT_ACTION_OTHER));
        }
        if (columns.containsKey(DbAliases.LIMS_INCIDENT_COMMENTS)) {
          setGeneralComments(rs.getString(DbAliases.LIMS_INCIDENT_COMMENTS));
        }
      } 
      catch (SQLException e) {
          ApiFunctions.throwAsRuntimeException(e);
      }
  }
	
	/**
	 * Returns the asmPosition.
	 * @return String
	 */
	public String getAsmPosition() {
		if(_asmPosition == null) {
			return "";
		}
		return _asmPosition;
	}

	/**
	 * Returns the caseId.
	 * @return String
	 */
	public String getCaseId() {
		if(_caseId == null) {
			return "";
		}
		return _caseId;
	}

	/**
	 * Returns the pathologist.
	 * @return String
	 */
	public String getPathologist() {
		if(_pathologist == null) {
			return "";
		}
		return _pathologist;
	}

	/**
	 * Returns the sampleId.
	 * @return String
	 */
	public String getSampleId() {
		if(_sampleId == null) {
			return "";
		}
		return _sampleId;
	}

	/**
	 * Returns the slideId.
	 * @return String
	 */
	public String getSlideId() {
		if(_slideId == null) {
			return "";
		}
		return _slideId;
	}

	/**
	 * Sets the slideId.
	 * @param slideId The slideId to set
	 */
	public void setSlideId(String slideId) {
		_slideId = slideId;
	}
    
  /** Returns the action name */
  public String getActionName() {
    String returnValue = "";
    String code = getAction();
    if (code == null || code.equals("")) {
      throw new ApiException("IncidentReportLineItem.getActionName() called on blank action.");
    }
    if (code.equals(LimsConstants.INCIDENT_ACTION_OTHER)) {
      returnValue = getActionOther();
    }
    else {
      returnValue = GbossFactory.getInstance().getDescription(code);
    }
    return returnValue;
  }

  /**
   * Returns the action.
   * @return String
   */
  public String getAction() {
    if (_action == null) {
      return "";
    }
    return _action;
  }

  /**
   * Returns the actionOther.
   * @return String
   */
  public String getActionOther() {
    if (_actionOther == null) {
      return "";
    }
    return _actionOther;
  }

  /**
   * Returns the generalComments.
   * @return String
   */
  public String getGeneralComments() {
    if (_generalComments == null) {
      return "";
    }
    return _generalComments;
  }

  /**
   * Returns the reason.
   * @return String
   */
  public String getReason() {
    if (_reason == null) {
      return "";
    }
    return _reason;
  }
    
  /** Returns the requestor code name */
  public String getRequestorCodeName() {
    String returnValue = "";
    String code = getRequestorCode();
    if (code == null || code.equals("")) {
      returnValue = "";
    }
    else {
      returnValue = GbossFactory.getInstance().getDescription(code);
    }
    return returnValue;
  }

  /**
   * Returns the requestorCode.
   * @return String
   */
  public String getRequestorCode() {
    if (_requestorCode == null) {
      return "";
    }
    return _requestorCode;
  }

  /**
   * Returns the save.
   * @return boolean
   */
  public boolean isSave() {
    return _save;
  }

  /**
   * Returns the incidentId.
   * @return String
   */
  public String getIncidentId() {
    if (_incidentId == null) {
      return "";
    }
    return _incidentId;
  }

  /**
   * Returns the createDate.
   * @return Timestamp
   */
  public Timestamp getCreateDate() {
    return _createDate;
  }

  /**
   * Returns the createUser.
   * @return String
   */
  public String getCreateUser() {
    return _createUser;
  }

  /**
   * Returns the pullReason.
   * @return String
   */
  public String getPullReason() {
    return _pullReason;
  }

  /**
   * Sets the action.
   * @param action The action to set
   */
  public void setAction(String action) {
    _action = action;
    if (!ApiFunctions.isEmpty(action)) {
      setActionDescription(GbossFactory.getInstance().getDescription(action));
    }
  }

  /**
   * Sets the actionOther.
   * @param actionOther The actionOther to set
   */
  public void setActionOther(String actionOther) {
    _actionOther = actionOther;
  }

  /**
   * Sets the generalComments.
   * @param generalComments The generalComments to set
   */
  public void setGeneralComments(String generalComments) {
    _generalComments = generalComments;
  }

  /**
   * Sets the reason.
   * @param reason The reason to set
   */
  public void setReason(String reason) {
    _reason = reason;
  }

  /**
   * Sets the requestorCode.
   * @param requestorCode The requestorCode to set
   */
  public void setRequestorCode(String requestorCode) {
    _requestorCode = requestorCode;
  }

  /**
   * Sets the save.
   * @param save The save to set
   */
  public void setSave(boolean save) {
    _save = save;
  }

  /**
   * Sets the incidentId.
   * @param incidentId The incidentId to set
   */
  public void setIncidentId(String incidentId) {
    _incidentId = incidentId;
  }

  /**
   * Sets the createDate.
   * @param createDate The createDate to set
   */
  public void setCreateDate(Timestamp createDate) {
    _createDate = createDate;
  }

  /**
   * Sets the createUser.
   * @param createUser The createUser to set
   */
  public void setCreateUser(String createUser) {
    _createUser = createUser;
  }

  /**
   * Sets the asmPosition.
   * @param asmPosition The asmPosition to set
   */
  public void setAsmPosition(String asmPosition) {
    _asmPosition = asmPosition;
  }

  /**
   * Sets the caseId.
   * @param caseId The caseId to set
   */
  public void setCaseId(String caseId) {
    _caseId = caseId;
  }

  /**
   * Sets the pathologist.
   * @param pathologist The pathologist to set
   */
  public void setPathologist(String pathologist) {
    _pathologist = pathologist;
  }

  /**
   * Sets the sampleId.
   * @param sampleId The sampleId to set
   */
  public void setSampleId(String sampleId) {
    _sampleId = sampleId;
  }

  /**
   * Sets the pullReason.
   * @param pullReason The pullReason to set
   */
  public void setPullReason(String pullReason) {
    _pullReason = pullReason;
  }

  /**
   * @return
   */
  public String getActionDescription() {
    return _actionDescription;
  }

  /**
   * @param string
   */
  public void setActionDescription(String string) {
    _actionDescription = string;
  }

}
