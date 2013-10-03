package com.ardais.bigr.lims.btx;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Represent the details of a sample subdivision business transaction.
 * 
 * <p>The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setParentId(String) Parent id}: The id of the parent sample (the sample that
 *     is being divided).</li>
 * <li>{@link #setNumberOfChildren(int) Number of children}: The number of children to divide
 *     the parent sample into.</li>
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
public class BTXDetailsSubdivide
  extends BTXDetails
  implements BTXDetailsSubdivideRelationship, Serializable {

  private static final long serialVersionUID = 7961490968762217768L;

  private String _parentId;
  private String _consentId;

  private String _histoMinThicknessPfinCid;
  private String _histoMaxThicknessPfinCid;
  private String _histoWidthAcrossPfinCid;
  private String _histoNotes;
  private int _numberOfChildren;

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
  public BTXDetailsSubdivide() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_SUBDIVIDE;
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
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    //   getParentId
    //   getChildIdList

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(128);

    // The result has this form:
    //    Divided <parent> into <length of child list> children: <list of child ids>

    IdList childIds = getChildIdList();

    sb.append("<li>Divided ");
    sb.append(IcpUtils.prepareLink(_parentId, securityInfo));
    sb.append(" into ");
    sb.append(childIds.size());
    sb.append(" children: ");
    childIds.appendICPHTML(sb, securityInfo);

    String data = null;
    sb.append(" Histology QC - MIN Thickness: [");
    data = getHistoMinThicknessPfinCid();
    Escaper.htmlEscape((ApiFunctions.isEmpty(data)) ? "" : GbossFactory.getInstance().getDescription(data), sb);

    sb.append("], MAX Thickness: [");
    data = getHistoMaxThicknessPfinCid();
    Escaper.htmlEscape((ApiFunctions.isEmpty(data)) ? "" : GbossFactory.getInstance().getDescription(data), sb);

    sb.append("], Width: [");
    data = getHistoWidthAcrossPfinCid();
    Escaper.htmlEscape((ApiFunctions.isEmpty(data)) ? "" : GbossFactory.getInstance().getDescription(data), sb);

    sb.append("], Subdivisible?: [Y], ");

    sb.append("Notes: [");
    Escaper.htmlEscapeAndPreserveWhitespace(getHistoNotes(), sb);
    sb.append("]</li>");

    return sb.toString();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);

    // We don't record the numberOfChildren field explicitly in the
    // history, it can be derived from the IdList that we record.

    history.setIdList1(getChildIdList());
    history.setAttrib1(_parentId);
    history.setAttrib2(getHistoMinThicknessPfinCid());
    history.setAttrib3(getHistoMaxThicknessPfinCid());
    history.setAttrib4(getHistoWidthAcrossPfinCid());
    history.setAttrib5(getHistoNotes());
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);

    // We don't record the numberOfChildren field explicitly in the
    // history, it can be derived from the IdList that we record.

    IdList idList = history.getIdList1();

    setChildIdList(idList);
    setNumberOfChildren((idList == null) ? 0 : idList.size());
    setParentId(history.getAttrib1());
    setHistoMinThicknessPfinCid(history.getAttrib2());
    setHistoMaxThicknessPfinCid(history.getAttrib3());
    setHistoWidthAcrossPfinCid(history.getAttrib4());
    setHistoNotes(history.getAttrib5());

    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    //
    setAsmPosition(null);
    setChildAsmPositions(null);
    setQCInProcess(null);
    setRNDRequested(null);
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
   * Returns the histoMinThicknessPfinCid.
   * 
   * @return String
   */
  public String getHistoMinThicknessPfinCid() {
    return _histoMinThicknessPfinCid;
  }

  /**
   * Sets the histoMinThicknessPfinCid.
   * 
   * @param histoMinThicknessPfinCid The histoMinThicknessPfinCid to set.
   */
  public void setHistoMinThicknessPfinCid(String histoMinThicknessPfinCid) {
    _histoMinThicknessPfinCid = ApiFunctions.safeString(histoMinThicknessPfinCid);
  }

  /**
   * Returns the histoMaxThicknessPfinCid.
   * 
   * @return String
   */
  public String getHistoMaxThicknessPfinCid() {
    return _histoMaxThicknessPfinCid;
  }

  /**
   * Sets the histoMaxThicknessPfinCid.
   * 
   * @param histoMaxThicknessPfinCid The histoMaxThicknessPfinCid to set.
   */
  public void setHistoMaxThicknessPfinCid(String histoMaxThicknessPfinCid) {
    _histoMaxThicknessPfinCid = ApiFunctions.safeString(histoMaxThicknessPfinCid);
  }

  /**
   * Returns the histoWidthAcrossPfinCid.
   * 
   * @return String
   */
  public String getHistoWidthAcrossPfinCid() {
    return _histoWidthAcrossPfinCid;
  }

  /**
   * Sets the histoWidthAcrossPfinCid.
   * 
   * @param histoWidthAcrossPfinCid The histoWidthAcrossPfinCid to set.
   */
  public void setHistoWidthAcrossPfinCid(String histoWidthAcrossPfinCid) {
    _histoWidthAcrossPfinCid = ApiFunctions.safeString(histoWidthAcrossPfinCid);
  }

  /**
   * Returns the histoNotes.
   * 
   * @return String
   */
  public String getHistoNotes() {
    return _histoNotes;
  }

  /**
   * Sets the histoNotes.
   * 
   * @param histoNotes The histoNotes to set.
   */
  public void setHistoNotes(String histoNotes) {
    _histoNotes = ApiFunctions.safeString(histoNotes);
  }

  /**
   * Return the number of children to divide the parent sample into.
   *
   * @return the number of children.
   */
  public int getNumberOfChildren() {
    return _numberOfChildren;
  }

  /**
   * Set the number of children to divide the parent sample into.
   *
   * @param newNumberOfChildren the number of children.
   */
  public void setNumberOfChildren(int numberOfChildren) {
    _numberOfChildren = numberOfChildren;
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
