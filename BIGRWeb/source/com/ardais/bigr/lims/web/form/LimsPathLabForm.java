package com.ardais.bigr.lims.web.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.lims.btx.BTXDetailsPathLabSlides;
import com.ardais.bigr.lims.javabeans.SlideData;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LimsPathLabForm extends BigrActionForm {
  private String _limsUserId;
  private String _newSlideId;
  private String _newBmsValue;
  private String[] _slideIdList;
  private String[] _bmsValueList;
  private String[] _sampleIdList;
  private String[] _diNameList;
  private String[] _caseIdList;
  private String[] _sampleTypeDisplayList;
  private String[] _procedureList;
  private String[] _cutLevelList;
  private String _operationType;
  private String[] _getReport;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  public ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = new ActionErrors();

    if (mapping.getPath().equals("/lims/limsPathLab")) {
      //validate valid user id.     
      if (ApiFunctions.isEmpty(getLimsUserId())) {
        errors.add("user", new ActionError("lims.error.invalidLIMSUser"));
        setLimsUserId(null);
      }
      //No sample entered and "Get Report" button pressed.
      else if (ApiFunctions.isEmpty(getNewSlideId()) && ApiFunctions.isEmpty(getGetReport())) {
        //Set operation type.        
        setOperationType(BTXDetailsPathLabSlides.OP_TYPE_SCAN_USER);
      }
      //Slide Id entered and "Get Report" button not pressed.
      else if (!ApiFunctions.isEmpty(getNewSlideId()) && ApiFunctions.isEmpty(getGetReport())) {
        //Set operation type.        
        setOperationType(BTXDetailsPathLabSlides.OP_TYPE_ADD_SLIDE);
        //Check for the slide format         
        if (!ValidateIds.isValid(getNewSlideId(), ValidateIds.TYPESET_SLIDE, false)) {
          errors.add("newSlide", new ActionError("lims.error.invalidSlideFormat", getNewSlideId()));
        }
        //validate sample id doesn't already exist in list
        if (getSlideIdList() != null) {
          List slides = Arrays.asList(getSlideIdList());
          if (slides.contains(getNewSlideId())) {
            errors.add(
              "duplicateSlide",
              new ActionError("lims.error.duplicateSlide", getNewSlideId()));
            setNewSlideId(null);
          }
        }
      }
      //"Get Report" button pressed.
      else if (!ApiFunctions.isEmpty(getGetReport())) {
        //Set operation type.
        setOperationType(BTXDetailsPathLabSlides.OP_TYPE_GET_REPORT);
      }
    }
    return errors;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.DescribableIntoBtxDetails#describeIntoBtxDetails(BTXDetails, BigrActionMapping, HttpServletRequest)
   */
  public void describeIntoBtxDetails(
    BTXDetails btxDetails,
    BigrActionMapping mapping,
    HttpServletRequest request) {
    BTXDetailsPathLabSlides createSlidesPrepare = (BTXDetailsPathLabSlides) btxDetails;
    //If user entered User Id.
    if (getOperationType().equals(BTXDetailsPathLabSlides.OP_TYPE_SCAN_USER)) {

      createSlidesPrepare.setOperationType(BTXDetailsPathLabSlides.OP_TYPE_SCAN_USER);
      createSlidesPrepare.setLimsUserId(getLimsUserId());
    }
    //If user entered/scanned slide Id.
    else if (getOperationType().equals(BTXDetailsPathLabSlides.OP_TYPE_ADD_SLIDE)) {
      createSlidesPrepare.setOperationType(BTXDetailsPathLabSlides.OP_TYPE_ADD_SLIDE);
      createSlidesPrepare.setLimsUserId(getLimsUserId());
      createSlidesPrepare.setCurrentSlide(getNewSlideId());
    }
    //If "Get Report" button pressed
    else if (getOperationType().equals(BTXDetailsPathLabSlides.OP_TYPE_GET_REPORT)) {
      createSlidesPrepare.setOperationType(BTXDetailsPathLabSlides.OP_TYPE_GET_REPORT);
      String[] slides = getSlideIdList();
      //Add all the slides to List.
      ArrayList slideDataBeans = new ArrayList();
      for (int i = 0; i < slides.length; i++) {
        SlideData slideData = new SlideData();
        slideData.setSlideId(slides[i]);
        slideDataBeans.add(slideData);
      }
      createSlidesPrepare.setLimsUserId(getLimsUserId());
      createSlidesPrepare.setSlideData(slideDataBeans);
    }
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#populateFromBtxDetails(BTXDetails)
   */
  public void populateFromBtxDetails(BTXDetails btxDetails) {
    BTXDetailsPathLabSlides createSlidesPrepare = (BTXDetailsPathLabSlides) btxDetails;
    //If user enters/scans user Id.
    if (getOperationType().equals(BTXDetailsPathLabSlides.OP_TYPE_SCAN_USER)) {
      if (!createSlidesPrepare.isTransactionCompleted()) {
        setLimsUserId(null);
      }
    }
    //If user enters/scans sample Id.
    if (getOperationType().equals(BTXDetailsPathLabSlides.OP_TYPE_ADD_SLIDE)) {
      if (createSlidesPrepare.isTransactionCompleted()) {
        //Add new slide Id to slide id list.
        setSlideIdList(addItemToArray(getNewSlideId(), getSlideIdList()));
        //add the Bms value for the new slide to the Bms value list
        setBmsValueList(
          addItemToArray(createSlidesPrepare.getCurrentBmsValue(), getBmsValueList()));
      }
      setNewSlideId(null);
    }
    //If "get Report" button is pressed
    if (getOperationType().equals(BTXDetailsPathLabSlides.OP_TYPE_GET_REPORT)) {

      List list = createSlidesPrepare.getSlideData();

      String[] sampleLabelList = new String[list.size()];
      String[] bmsYNList = new String[list.size()];
      String[] slideLabelList = new String[list.size()];
      String[] donorInstNameList = new String[list.size()];
      String[] caseIdList = new String[list.size()];
      String[] sampleTypeDisplayList = new String[list.size()];
      String[] procedureList = new String[list.size()];
      String[] cutLevelList = new String[list.size()];
      //Loop thriugh the results.
      for (int i = 0; i < list.size(); i++) {
        SlideData slide = (SlideData) list.get(i);
        String sampleTypeCid = slide.getSampleTypeCid();
        String sampleTypeDisplay =
          (ApiFunctions.isEmpty(sampleTypeCid)
            ? ""
            : GbossFactory.getInstance().getDescription(sampleTypeCid));
        sampleLabelList[i] = slide.getSampleId();
        bmsYNList[i] = slide.getBmsYN();
        slideLabelList[i] = slide.getSlideId();
        donorInstNameList[i] = slide.getDonorInstitutionName();
        caseIdList[i] = slide.getCaseId();
        sampleTypeDisplayList[i] = sampleTypeDisplay;
        procedureList[i] = slide.getProcedure();
        cutLevelList[i] = String.valueOf(slide.getCutLevel());
      }
      //Set the lists to form
      setSampleIdList(sampleLabelList);
      setBmsValueList(bmsYNList);
      setSlideIdList(slideLabelList);
      setDiNameList(donorInstNameList);
      setCaseIdList(caseIdList);
      setCutLevelList(cutLevelList);
      setSampleTypeDisplayList(sampleTypeDisplayList);
      setProcedureList(procedureList);
    }
  }

  /**
   * Adds item to myArray.
   * @param <code>String</code> item
   * @param <code>String[]</code> myArray.
   */
  private String[] addItemToArray(String item, String[] myArray) {
    if (!ApiFunctions.isEmpty(item)) {
      if (ApiFunctions.isEmpty(myArray)) {
        return myArray = new String[] { item };
      }
      else {
        String[] temp = new String[myArray.length + 1];
        System.arraycopy(myArray, 0, temp, 0, myArray.length);
        temp[temp.length - 1] = item;
        return temp;
      }
    }
    else {
      return null;
    }
  }

  /**
   * Returns the limsUserId.
   * @return String
   */
  public String getLimsUserId() {
    return _limsUserId;
  }

  /**
   * Returns the newSlideId.
   * @return String
   */
  public String getNewSlideId() {
    return _newSlideId;
  }

  /**
   * Returns the slideIdList.
   * @return String[]
   */
  public String[] getSlideIdList() {
    return _slideIdList;
  }

  /**
   * Sets the limsUserId.
   * @param limsUserId The limsUserId to set
   */
  public void setLimsUserId(String limsUserId) {
    _limsUserId = limsUserId;
  }

  /**
   * Sets the newSlideId.
   * @param newSlideId The newSlideId to set
   */
  public void setNewSlideId(String newSlideId) {
    _newSlideId = newSlideId;
  }

  /**
   * Sets the slideIdList.
   * @param slideIdList The slideIdList to set
   */
  public void setSlideIdList(String[] slideIdList) {
    _slideIdList = slideIdList;
  }

  /**
   * Returns the cutLevelList.
   * @return String[]
   */
  public String[] getCutLevelList() {
    return _cutLevelList;
  }

  /**
   * Returns the diNameList.
   * @return String[]
   */
  public String[] getDiNameList() {
    return _diNameList;
  }

  /**
   * Returns the sampleTypeDisplayList.
   * @return String[]
   */
  public String[] getSampleTypeDisplayList() {
    return _sampleTypeDisplayList;
  }

  /**
   * Returns the procedureList.
   * @return String[]
   */
  public String[] getProcedureList() {
    return _procedureList;
  }

  /**
   * Returns the sampleIdList.
   * @return String[]
   */
  public String[] getSampleIdList() {
    return _sampleIdList;
  }

  /**
   * Sets the cutLevelList.
   * @param cutLevelList The cutLevelList to set
   */
  public void setCutLevelList(String[] cutLevelList) {
    _cutLevelList = cutLevelList;
  }

  /**
   * Sets the diNameList.
   * @param diNameList The diNameList to set
   */
  public void setDiNameList(String[] diNameList) {
    _diNameList = diNameList;
  }

  /**
   * Sets the sampleTypeDisplayList.
   * @param sampleTypeDisplayList The sampleTypeDisplayList to set
   */
  public void setSampleTypeDisplayList(String[] sampleTypeDisplayList) {
    _sampleTypeDisplayList = sampleTypeDisplayList;
  }

  /**
   * Sets the procedureList.
   * @param procedureList The procedureList to set
   */
  public void setProcedureList(String[] procedureList) {
    _procedureList = procedureList;
  }

  /**
   * Sets the sampleIdList.
   * @param sampleIdList The sampleIdList to set
   */
  public void setSampleIdList(String[] sampleIdList) {
    _sampleIdList = sampleIdList;
  }

  /**
   * Returns the caseIdList.
   * @return String[]
   */
  public String[] getCaseIdList() {
    return _caseIdList;
  }

  /**
   * Sets the caseIdList.
   * @param caseIdList The caseIdList to set
   */
  public void setCaseIdList(String[] caseIdList) {
    _caseIdList = caseIdList;
  }

  /**
   * Returns the operationType.
   * @return String
   */
  public String getOperationType() {
    return _operationType;
  }

  /**
   * Sets the operationType.
   * @param operationType The operationType to set
   */
  public void setOperationType(String operationType) {
    _operationType = operationType;
  }

  /**
   * Returns the getReport.
   * @return String[]
   */
  public String[] getGetReport() {
    return _getReport;
  }

  /**
   * Sets the getReport.
   * @param getReport The getReport to set
   */
  public void setGetReport(String[] getReport) {
    _getReport = getReport;
  }

  /**
   * @return
   */
  public String[] getBmsValueList() {
    return _bmsValueList;
  }

  /**
   * @return
   */
  public String getNewBmsValue() {
    return _newBmsValue;
  }

  /**
   * @param strings
   */
  public void setBmsValueList(String[] strings) {
    _bmsValueList = strings;
  }

  /**
   * @param string
   */
  public void setNewBmsValue(String string) {
    _newBmsValue = string;
  }

}
