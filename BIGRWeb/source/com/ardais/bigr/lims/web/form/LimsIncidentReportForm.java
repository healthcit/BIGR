package com.ardais.bigr.lims.web.form;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.lims.btx.BTXDetailsCreateIncidents;
import com.ardais.bigr.lims.javabeans.IncidentReportData;
import com.ardais.bigr.lims.javabeans.IncidentReportLineItem;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * This represents the web form that is used to save incident reports.
 */
public class LimsIncidentReportForm extends BigrActionForm {

  private ArrayList _incidentReportDataList = new ArrayList();
  
  private String _generalComments;

  private String[] _saveList;
  private String[] _sampleIdList;
  private String[] _caseIdList;
  private String[] _asmPositionList;
  private String[] _slideIdList;
  private String[] _pathologistList;
  private String[] _incidentActionList;
  private String[] _otherIncidentActionList;
  private String[] _reasonList;
  private String[] _requestorCodeList;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _incidentReportDataList = new ArrayList(); 
    _generalComments = null;
    _saveList = null;
    _sampleIdList = null;
    _caseIdList = null;
    _slideIdList = null;
    _incidentActionList = null;
    _otherIncidentActionList = null;
    _reasonList = null;
    _requestorCodeList = null;
    _asmPositionList = null;
    _pathologistList = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    // Note: only primitive validations occur here. Any business rule or
    // business validation should occur in session bean method.  Since there is no
    // primitive validation to do this method does nothing.  All checking to make sure
    // that all required fields have been provided to create an incident report are
    // done either in the action or a btx session bean call.
    ActionErrors errors = new ActionErrors();
    return errors;
  }
  
  /** Method to copy the properties on this form into corresponding fields on a btxdetails object.
   *  Since the form has a collection of string arrays representing the data passed in from the
   *  jsp, and the btxdetails for this operation expects an IncidentReportData
   *  object, we need to override this method and create the IncidentReportData object
   *  from the String arrays and then copy it to the btxdetails object.
   * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(BTXDetails, BigrActionMapping, HttpServletRequest)
   */
  public void describeIntoBtxDetails(BTXDetails btxDetails, BigrActionMapping mapping, HttpServletRequest request) {
    super.describeIntoBtxDetails(btxDetails, mapping,request);
    BTXDetailsCreateIncidents details = (BTXDetailsCreateIncidents)btxDetails;
    IncidentReportData data = new IncidentReportData();
    data.setGeneralComments(getGeneralComments());
    if (getSampleIdList() != null) {
      int count = getSampleIdList().length;
      for (int i=0; i<count; i++) {
        IncidentReportLineItem lineItem = new IncidentReportLineItem();
        lineItem.setAction(getIncidentActionList()[i]);
        lineItem.setActionOther(getOtherIncidentActionList()[i]);
        lineItem.setAsmPosition(getAsmPositionList()[i]);
        lineItem.setCaseId(getCaseIdList()[i]);
        lineItem.setGeneralComments(getGeneralComments());
        lineItem.setPathologist(getPathologistList()[i]);
        lineItem.setReason(getReasonList()[i]);
        lineItem.setRequestorCode(getRequestorCodeList()[i]);
        lineItem.setSampleId(getSampleIdList()[i]);
        String saveValue = getSaveList()[i].trim().toUpperCase();
        lineItem.setSave(saveValue.equals("TRUE"));
        lineItem.setSlideId(getSlideIdList()[i]);
        data.addLineItem(lineItem);
      }
    }
    details.setIncidentReportData(data);
   }

  /**
   * Returns the asmPositionList.
   * @return String[]
   */
  public String[] getAsmPositionList() {
    return _asmPositionList;
  }

  /**
   * Returns the caseIdList.
   * @return String[]
   */
  public String[] getCaseIdList() {
    return _caseIdList;
  }

  /**
   * Returns the generalComments.
   * @return String
   */
  public String getGeneralComments() {
    return _generalComments;
  }

  /**
   * Returns the incidentActionList.
   * @return String[]
   */
  public String[] getIncidentActionList() {
    return _incidentActionList;
  }

  /**
   * Returns the incidentReportDataList.
   * @return ArrayList
   */
  public ArrayList getIncidentReportDataList() {
    return _incidentReportDataList;
  }

  /**
   * Returns the otherIncidentActionList.
   * @return String[]
   */
  public String[] getOtherIncidentActionList() {
    return _otherIncidentActionList;
  }

  /**
   * Returns the pathologistList.
   * @return String[]
   */
  public String[] getPathologistList() {
    return _pathologistList;
  }

  /**
   * Returns the reasonList.
   * @return String[]
   */
  public String[] getReasonList() {
    return _reasonList;
  }

  /**
   * Returns the requestorCodeList.
   * @return String[]
   */
  public String[] getRequestorCodeList() {
    return _requestorCodeList;
  }

  /**
   * Returns the sampleIdList.
   * @return String[]
   */
  public String[] getSampleIdList() {
    return _sampleIdList;
  }

  /**
   * Returns the saveList.
   * @return String[]
   */
  public String[] getSaveList() {
    return _saveList;
  }

  /**
   * Returns the slideIdList.
   * @return String[]
   */
  public String[] getSlideIdList() {
    return _slideIdList;
  }

  /**
   * Sets the asmPositionList.
   * @param asmPositionList The asmPositionList to set
   */
  public void setAsmPositionList(String[] asmPositionList) {
    _asmPositionList = asmPositionList;
  }

  /**
   * Sets the caseIdList.
   * @param caseIdList The caseIdList to set
   */
  public void setCaseIdList(String[] caseIdList) {
    _caseIdList = caseIdList;
  }

  /**
   * Sets the generalComments.
   * @param generalComments The generalComments to set
   */
  public void setGeneralComments(String generalComments) {
    _generalComments = generalComments;
  }

  /**
   * Sets the incidentActionList.
   * @param incidentActionList The incidentActionList to set
   */
  public void setIncidentActionList(String[] incidentActionList) {
    _incidentActionList = incidentActionList;
  }

  /**
   * Sets the incidentReportDataList.
   * @param incidentReportDataList The incidentReportDataList to set
   */
  public void setIncidentReportDataList(ArrayList incidentReportDataList) {
    _incidentReportDataList = incidentReportDataList;
  }

  /**
   * Sets the otherIncidentActionList.
   * @param otherIncidentActionList The otherIncidentActionList to set
   */
  public void setOtherIncidentActionList(String[] otherIncidentActionList) {
    _otherIncidentActionList = otherIncidentActionList;
  }

  /**
   * Sets the pathologistList.
   * @param pathologistList The pathologistList to set
   */
  public void setPathologistList(String[] pathologistList) {
    _pathologistList = pathologistList;
  }

  /**
   * Sets the reasonList.
   * @param reasonList The reasonList to set
   */
  public void setReasonList(String[] reasonList) {
    _reasonList = reasonList;
  }

  /**
   * Sets the requestorCodeList.
   * @param requestorCodeList The requestorCodeList to set
   */
  public void setRequestorCodeList(String[] requestorCodeList) {
    _requestorCodeList = requestorCodeList;
  }

  /**
   * Sets the sampleIdList.
   * @param sampleIdList The sampleIdList to set
   */
  public void setSampleIdList(String[] sampleIdList) {
    _sampleIdList = sampleIdList;
  }

  /**
   * Sets the saveList.
   * @param saveList The saveList to set
   */
  public void setSaveList(String[] saveList) {
    _saveList = saveList;
  }

  /**
   * Sets the slideIdList.
   * @param slideIdList The slideIdList to set
   */
  public void setSlideIdList(String[] slideIdList) {
    _slideIdList = slideIdList;
  }

}
