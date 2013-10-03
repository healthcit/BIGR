package com.ardais.bigr.lims.web.form;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.lims.btx.BTXDetailsPrintSlides;
import com.ardais.bigr.lims.btx.BTXDetailsPrintSlidesValidation;
import com.ardais.bigr.web.action.BigrActionMapping;

/**
 * 
 */
public class LimsPrintSlideLabelForm extends LimsLabelGenForm {
  //Made this class as subclass of LimsLabelGenForm to reuse LimsPrintSlideLabel
  //Action.

  private String _newSlideId;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    setSlideLabelList(null);
    setHasBeenPrinted(null);
    setMessage(null);
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  public ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = new ActionErrors();

    // If there's a slide label, validate it and convert it to canonical form.
    String newSlideId = getNewSlideId();
    if (!ApiFunctions.isEmpty(newSlideId)) {
      // Canonicalize the id here
      String canonicalNewSlideId =
        ValidateIds.validateId(newSlideId, ValidateIds.TYPESET_SLIDE, true);
      if (canonicalNewSlideId == null) {
        errors.add(
          "newSlide",
          new ActionError("lims.error.invalidSlideFormat", Escaper.htmlEscape(newSlideId)));
      }
      else {
        newSlideId = canonicalNewSlideId;
        setNewSlideId(newSlideId);
      }
    }

    if (mapping.getPath().equals("/lims/limsPrintSlideLabelScanAll")) {
      if (ApiFunctions.isEmpty(getLimsUserId())) {
        //validate valid user id.       
        errors.add("user", new ActionError("lims.error.invalidLIMSUser"));
        setLimsUserId(null);
      }
      else if (ApiFunctions.isEmpty(newSlideId)) {
        //No slide entered
        setOperationType(BTXDetailsPrintSlides.OP_TYPE_SCAN_USER);
      }
      else {
        //Slide Id entered
        setOperationType(BTXDetailsPrintSlides.OP_TYPE_ADD_SLIDE);
        //Check for the duplicate sample.
        if ((!ApiFunctions.isEmpty(newSlideId)) && (getSlideLabelList() != null)) {
          List samples = Arrays.asList(getSlideLabelList());
          if (samples.contains(newSlideId)) {
            errors.add(
              "duplicateSample",
              new ActionError("lims.error.duplicateSample", newSlideId));
            setNewSlideId(null);
          }
        }
      }
    }

    return errors;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.DescribableIntoBtxDetails#describeIntoBtxDetails
   * (BTXDetails, BigrActionMapping, HttpServletRequest)
   */
  public void describeIntoBtxDetails(
    BTXDetails btxDetails,
    BigrActionMapping mapping,
    HttpServletRequest request) {
    BTXDetailsPrintSlidesValidation createSlidesPrepare =
      (BTXDetailsPrintSlidesValidation) btxDetails;
    //If user entered User Id.
    if (getOperationType().equals(BTXDetailsPrintSlides.OP_TYPE_SCAN_USER)) {

      createSlidesPrepare.setOperationType(BTXDetailsPrintSlides.OP_TYPE_SCAN_USER);
      createSlidesPrepare.setLimsUserId(getLimsUserId());
    }
    //If user entered/scanned sample Id.
    else if (getOperationType().equals(BTXDetailsPrintSlides.OP_TYPE_ADD_SLIDE)) {
      createSlidesPrepare.setOperationType(BTXDetailsPrintSlides.OP_TYPE_ADD_SLIDE);
      createSlidesPrepare.setLimsUserId(getLimsUserId());
      createSlidesPrepare.setCurrentSlide(getNewSlideId());
    }
  }

  public void populateFromBtxDetails(BTXDetails btxDetails) {
    BTXDetailsPrintSlidesValidation createSlidesPrepare =
      (BTXDetailsPrintSlidesValidation) btxDetails;
    //If user enters/scans user Id.
    if (getOperationType().equals(BTXDetailsPrintSlides.OP_TYPE_SCAN_USER)) {
      if (!createSlidesPrepare.isTransactionCompleted()) {
        setLimsUserId(null);
      }
    }
    //If user enters/scans sample Id.
    if (getOperationType().equals(BTXDetailsPrintSlides.OP_TYPE_ADD_SLIDE)) {
      if (createSlidesPrepare.isTransactionCompleted()) {
        //add sample to sample list and add pulled/requested to their lists.
        setSlideLabelList(addItemToArray(getNewSlideId(), getSlideLabelList()));
        setHasBeenPrinted(addItemToArray("false", getHasBeenPrinted()));
      }
      setNewSlideId(null);

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
   * Returns the newSlideId.
   * @return String
   */
  public String getNewSlideId() {
    return _newSlideId;
  }

  /**
   * Sets the newSlideId.
   * @param newSlideId The newSlideId to set
   */
  public void setNewSlideId(String newSlideId) {
    if (!ApiFunctions.isEmpty(newSlideId)) {
      _newSlideId = StringUtils.trim(newSlideId);
    }
    else {
      _newSlideId = newSlideId;
    }
  }

}
