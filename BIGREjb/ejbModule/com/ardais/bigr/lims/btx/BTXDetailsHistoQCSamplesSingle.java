package com.ardais.bigr.lims.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a histology qc update sample business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setInputId(String) inputId}: The id of the slide or sample.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BTXDetailsHistoQCSamplesSingle extends BTXDetails implements Serializable {

  private static final long serialVersionUID = -6540709743626308928L;

  private SampleData _sampleData;
  private SampleAccessBean _sampleBean;
  private IdList _childIdList;

  /** 
   * Default constructor.
   */
  public BTXDetailsHistoQCSamplesSingle() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_HISTO_QC_SAMPLES_SINGLE;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(128);
    SampleData sampleData = getSampleData();

    boolean isParent = ApiFunctions.isEmpty(sampleData.getParentId());

    if (isParent) {
      sb.append("<li>For parent sample ");
    }
    else {
      sb.append("<li>For child sample ");
    }

    sb.append(IcpUtils.prepareLink(sampleData.getSampleId(), securityInfo));

    String data = null;
    sb.append(": MIN Thickness: [");
    data = sampleData.getHistoMinThicknessPfin();
    sb.append((ApiFunctions.isEmpty(data)) ? "" : data);

    sb.append("], MAX Thickness: [");
    data = sampleData.getHistoMaxThicknessPfin();
    sb.append((ApiFunctions.isEmpty(data)) ? "" : data);

    sb.append("], Width: [");
    data = sampleData.getHistoWidthAcrossPfin();
    sb.append((ApiFunctions.isEmpty(data)) ? "" : data);

    if (isParent) {
      sb.append("], Subdivisible?: [" + sampleData.getHistoSubdividable() + "], ");
    }
    else {
      sb.append("], Not Subdivisible, ");
    }

    sb.append("Re-embed Reason: [");
    data = sampleData.getHistoReembedReason();
    sb.append((ApiFunctions.isEmpty(data)) ? "" : data);

    sb.append("], Other Reason: [");
    data = sampleData.getOtherHistoReembedReason();
    sb.append((ApiFunctions.isEmpty(data)) ? "N/A" : Escaper.htmlEscape(data));

    sb.append("], Notes: [");
    Escaper.htmlEscape(sampleData.getHistoNotes(), sb);
    sb.append("]</li>");

    if (isParent) {
      IdList childIds = getChildIdList();
      if (childIds.size() > 0) {
        sb.append("<li>Children: ");
        childIds.appendICPHTML(sb, securityInfo);
        sb.append(" inherit same dimensions.</li>");
      }
    }

    return sb.toString();
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

    if (getSampleData() != null) {
      set.add(getSampleData().getSampleId());
    }

    return set;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);

    SampleData sampleData = getSampleData();

    history.setIdList1(getChildIdList());
    history.setAttrib1(sampleData.getSampleId());
    history.setAttrib2(sampleData.getHistoMinThicknessPfin());
    history.setAttrib3(sampleData.getHistoMaxThicknessPfin());
    history.setAttrib4(sampleData.getHistoWidthAcrossPfin());
    history.setAttrib5(sampleData.getHistoSubdividable());
    history.setAttrib6(sampleData.getHistoReembedReason());
    history.setAttrib7(sampleData.getOtherHistoReembedReason());
    history.setAttrib8(sampleData.getHistoNotes());
    history.setAttrib9(sampleData.getParentId());
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);

    IdList idList = history.getIdList1();
    setChildIdList(idList);

    SampleData sampleData = new SampleData();

    sampleData.setSampleId(history.getAttrib1());
    sampleData.setHistoMinThicknessPfin(history.getAttrib2());
    sampleData.setHistoMaxThicknessPfin(history.getAttrib3());
    sampleData.setHistoWidthAcrossPfin(history.getAttrib4());
    sampleData.setHistoSubdividable(history.getAttrib5());
    sampleData.setHistoReembedReason(history.getAttrib6());
    sampleData.setOtherHistoReembedReason(history.getAttrib7());
    sampleData.setHistoNotes(history.getAttrib8());
    sampleData.setParentId(history.getAttrib9());

    setSampleData(sampleData);
  }

  /**
   * Returns the sampleData.
   * @return SampleData
   */
  public SampleData getSampleData() {
    return _sampleData;
  }

  /**
   * Sets the sampleData
   * @param sampleData The sampleData to set.
   */
  public void setSampleData(SampleData sampleData) {
    _sampleData = sampleData;
  }

  /**
   * Returns the sampleBean.
   * @return SampleAccessBean
   */
  public SampleAccessBean getSampleBean() {
    return _sampleBean;
  }

  /**
   * Sets the sampleBean
   * @param sampleBean The sampleBean to set.
   */
  public void setSampleBean(SampleAccessBean sampleBean) {
    _sampleBean = sampleBean;
  }

  /**
   * Return the list of the child sample ids (if parent).
   *
   * @return the list of child sample ids.
   */
  public IdList getChildIdList() {
    return _childIdList;
  }

  /**
   * Set the list of the child sample ids associated with parent.
   *
   * @param childIdList the list of child sample ids.
   */
  public void setChildIdList(IdList childIdList) {
    _childIdList = childIdList;
  }
}
