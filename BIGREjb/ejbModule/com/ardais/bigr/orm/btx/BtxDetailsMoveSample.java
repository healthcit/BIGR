package com.ardais.bigr.orm.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.IcpUtils;

public class BtxDetailsMoveSample extends BTXDetails {
  
  private String _inventoryGroup;
  private String _inventoryGroupName;
  private String _action;
  private String _sampleId;
  private String _note;
  private String _sampleAlias;

  /**
   * 
   */
  public BtxDetailsMoveSample() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MOVE_SAMPLE_BY_INVENTORY_GROUP;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    set.add(getSampleId());
    return set;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();
    
    StringBuffer sb = new StringBuffer(128);
    sb.append("Sample ");
    StringBuffer linkText = new StringBuffer(40);
    linkText.append(getSampleId());
    if (!ApiFunctions.isEmpty(getSampleAlias())) {
      linkText.append(" (");
      linkText.append(getSampleAlias());
      linkText.append(")");
    }
    sb.append(IcpUtils.prepareLink(getSampleId(), linkText.toString(), securityInfo));
    sb.append(" was ");
    if (getAction().equalsIgnoreCase(Constants.OPERATION_ADD)) {
      sb.append("added to ");
    }
    else if (getAction().equalsIgnoreCase(Constants.OPERATION_REMOVE)) {
      sb.append("removed from ");
    }
    sb.append("inventory group ");
    String prefixedInventoryGroupId = FormLogic.makePrefixedLogicalRepositoryId(getInventoryGroup());
    sb.append(IcpUtils.prepareLink(prefixedInventoryGroupId, getInventoryGroupName(), securityInfo));
    sb.append(".");

    String note = getNote();
    if (!ApiFunctions.isEmpty(note)) {
      sb.append("<br>");
      sb.append("Comment: ");
      Escaper.htmlEscapeAndPreserveWhitespace(note,sb);
    }

    return sb.toString();
  }

  /**
   * @return
   */
  public String getAction() {
    return _action;
  }

  /**
   * @return
   */
  public String getInventoryGroup() {
    return _inventoryGroup;
  }

  /**
   * @return
   */
  public String getSampleId() {
    return _sampleId;
  }

  /**
   * @param string
   */
  public void setAction(String string) {
    _action = string;
  }

  /**
   * @param string
   */
  public void setInventoryGroup(String string) {
    _inventoryGroup = string;
  }

  /**
   * @param string
   */
  public void setSampleId(String string) {
    _sampleId = string;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);
    history.setAttrib1(getAction());
    history.setAttrib2(getSampleId());
    history.setAttrib3(getInventoryGroup());
    history.setAttrib4(getInventoryGroupName());
    history.setAttrib5(getNote());
    history.setAttrib6(getSampleAlias());
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    setAction(history.getAttrib1());
    setSampleId(history.getAttrib2());
    setInventoryGroup(history.getAttrib3());
    setInventoryGroupName(history.getAttrib4());
    setNote(history.getAttrib5());
    setSampleAlias(history.getAttrib6());
  }

  /**
   * @return
   */
  public String getNote() {
    return _note;
  }

  /**
   * @param string
   */
  public void setNote(String string) {
    _note = string;
  }

  /**
   * @return
   */
  public String getInventoryGroupName() {
    return _inventoryGroupName;
  }

  /**
   * @param string
   */
  public void setInventoryGroupName(String string) {
    _inventoryGroupName = string;
  }

  /**
   * @return Returns the sampleAlias.
   */
  public String getSampleAlias() {
    return _sampleAlias;
  }
  
  /**
   * @param sampleAlias The sampleAlias to set.
   */
  public void setSampleAlias(String sampleAlias) {
    _sampleAlias = sampleAlias;
  }
}
