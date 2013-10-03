package com.ardais.bigr.lims.btx;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.javabeans.IdList;

/**
 * Represent the details of a view relationship business transaction.
 * 
 * <p>The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setParentId(String) Parent id}: The id of the parent sample (the sample that
 *     is being divided).</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setChildIdList(IdList) Child id list}: The list of the child sample ids
 *     produced by the subdivision.</li>
 * </ul>
 */
public class BTXDetailsViewSubdivide extends BTXDetails implements BTXDetailsSubdivideRelationship, Serializable {

  private static final long serialVersionUID = 8675309968762217768L;

  private String _parentId;
  private String _consentId;

  private Map _warningInfo;
  private Timestamp _subdivisionTimestamp = null;
  private String _asmPosition;
  private IdList _childIdList;
  private HashMap _childAsmPositions;
  private Date _qcInProcess;
  private Date _rndRequested;

  /** 
   * Default constructor.
   */
  public BTXDetailsViewSubdivide() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_VIEW_SUBDIVIDE;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public java.util.Set getDirectlyInvolvedObjects() {
    Set set = null;

    if (_childIdList == null) {
      set = new HashSet();
    }
    else {
      set = _childIdList.getDirectlyInvolvedObjects();
    }

    if (_parentId != null) {
      set.add(_parentId);
    }

    return set;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    String msg = "BTXDetailsViewRelationship.doGetDetailsAsHTML() method called in error!";
    return msg;
  }

  /**
   * Return the id of the parent sample (the sample that is being divided).
   *
   * @return the parent sample id.
   */
  public String getParentId() {
    return _parentId;
  }

  /**
   * Set the id of the parent sample (the sample that is being divided).
   *
   * @param newParentId the parent sample id.
   */
  public void setParentId(String parentId) {
    _parentId = ApiFunctions.safeString(parentId);
  }

  /**
   * Returns the consentIdList.
   * 
   * @return String
   */
  public String getConsentId() {
    return _consentId;
  }

  /**
   * Sets the consentId.
   * 
   * @param consentId The consentId to set.
   */
  public void setConsentId(String consentId) {
    _consentId = ApiFunctions.safeString(consentId);
  }

  /**
   * Returns the asmPosition.
   * 
   * @return String
   */
  public String getAsmPosition() {
    return _asmPosition;
  }

  /**
   * Sets the asmPosition.
   * 
   * @param asmPosition The asmPosition to set
   */
  public void setAsmPosition(String asmPosition) {
    _asmPosition = ApiFunctions.safeString(asmPosition);
  }

  /**
   * Returns the warningInfo map.
   * 
   * @returns Map
   */
  public Map getWarningInfo() {
    return _warningInfo;
  }

  /**
   * Sets the warningInfo map.
   * 
   * @param warningInfo The warningInfo map to set.
   */
  public void setWarningInfo(Map warningInfo) {
    _warningInfo = warningInfo;
  }

  /**
   * 
   */
  public Timestamp getSubdivisionTimestamp() {
    return _subdivisionTimestamp;
  }

  /**
   * 
   */
  public void setSubdivisionTimestamp(Timestamp subdivisionTimestamp) {
    _subdivisionTimestamp = subdivisionTimestamp;
  }

  /**
   * Return the list of the child sample ids produced by the subdivision.
   *
   * @return the list of child sample ids.
   */
  public IdList getChildIdList() {
    return _childIdList;
  }

  /**
   * Set the list of the child sample ids produced by the subdivision.
   *
   * @param childIdList the list of child sample ids.
   */
  public void setChildIdList(IdList childIdList) {
    _childIdList = childIdList;
  }

  /**
   * 
   */
  public HashMap getChildAsmPositions() {
    return _childAsmPositions;
  }

  /**
   * 
   */
  public void setChildAsmPositions(HashMap childAsmPositions) {
    _childAsmPositions = childAsmPositions;
  }

  /**
   * 
   */
  public Date getQCInProcess() {
    return _qcInProcess;
  }

  /**
   * 
   */
  public void setQCInProcess(Date qcInProcess) {
    _qcInProcess = qcInProcess;
  }

  /**
   * 
   */
  public Date getRNDRequested() {
    return _rndRequested;
  }

  /**
   * 
   */
  public void setRNDRequested(Date rndRequested) {
    _rndRequested = rndRequested;
  }
}
