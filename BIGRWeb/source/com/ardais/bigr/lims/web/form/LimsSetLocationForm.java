package com.ardais.bigr.lims.web.form;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.lims.btx.BTXDetailsSetSlideLocations;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.lims.javabeans.SlideData;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LimsSetLocationForm extends BigrActionForm {
  private String _limsUserId;
  private String _slideId;
  private String _location;
  private String _setLocation;

  private String _operationType;
  private String _message;

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

    if (ApiFunctions.isEmpty(getLimsUserId())) {
      errors.add("user", new ActionError("lims.error.invalidLIMSUser"));
      setLimsUserId(null);
    }
    else if (ApiFunctions.isEmpty(getSlideId())) {
      setOperationType(BTXDetailsSetSlideLocations.OP_TYPE_SCAN_USER);

    }
    else if (ApiFunctions.isEmpty(getLocation())) {
      setOperationType(BTXDetailsSetSlideLocations.OP_TYPE_SCAN_SLIDE);
      request.setAttribute("locationChoices", getLocationChoices());
    }
    else if (
      !ApiFunctions.isEmpty(getLimsUserId())
        && !ApiFunctions.isEmpty(getSlideId())
        && !ApiFunctions.isEmpty(getLocation())
        && !ApiFunctions.isEmpty(getSetLocation())) {
      setOperationType(BTXDetailsSetSlideLocations.OP_TYPE_SET_LOC);

    }
    else {
      errors.add("user", new ActionError("lims.invalid.operation"));
    }

    /*	
    
    	try {
    		if (!FormLogic.validSlideID(getSlideId())) {
    			errors.add(
    				"newSlide",
    				new ActionError(
    					"lims.error.invalidSlideFormat",
    					getSlideId()));
    				setSlideId(null);
    				} else if (
    					getSlideId() == null
    						|| !data.doesSlideExist(getSlideId())) {
    			errors.add(
    				"newSlide",
    				new ActionError("lims.error.invalidSlide", getSlideId()));
    				setSlideId(null);
    				}
    	} catch (Exception e) {
    		ApiLogger.log("Error Validating Lims User ID: ", e);
    			errors.add(
    				"user",
    				new ActionError("lims.error.invalidLIMSUser"));
    			setSlideId(null);
    			}
    } else if (
    	mapping.getPath().equals(
    		"/lims/limsSetLocation")) { //validate valid user id
    try {
    		if (!data.isValidLimsUser(getLimsUserId())) {
    			errors.add(
    				"user",
    				new ActionError("lims.error.invalidLIMSUser"));
    				setLimsUserId(null);
    				}
    	} catch (Exception e) {
    		ApiLogger.log("Error Validating Lims User ID: ", e);
    			errors.add(
    				"user",
    				new ActionError("lims.error.invalidLIMSUser"));
    			}
    
    	try {
    		if (getSlideId() == null || !data.doesSlideExist(getSlideId())) {
    			errors.add(
    				"newSlide",
    				new ActionError("lims.error.invalidSlide", getSlideId()));
    				setSlideId(null);
    				}
    	} catch (Exception e) {
    		ApiLogger.log("Error Validating Lims User ID: ", e);
    			errors.add(
    				"user",
    				new ActionError("lims.error.invalidLIMSUser"));
    			setSlideId(null);
    			}
    }
    */
    return errors;
  }
  /**
   * Returns the limsUserId.
   * @return String
   */
  public String getLimsUserId() {
    return _limsUserId;
  } /**
  		 * Returns the slideId.
  		 * @return String
  		 */
  public String getSlideId() {
    return _slideId;
  } /**
  		 * Returns the location.
  		 * @return String
  		 */
  public String getLocation() {
    return _location;
  } /**
  		 * Sets the limsUserId.
  		 * @param limsUserId The limsUserId to set
  		 */
  public void setLimsUserId(String limsUserId) {
    _limsUserId = limsUserId;
  } /**
  		 * Sets the slideId.
  		 * @param slideId The slideId to set
  		 */
  public void setSlideId(String slideId) {
    _slideId = slideId;
  } /**
  		 * Sets the location.
  		 * @param location The location to set
  		 */
  public void setLocation(String location) {
    _location = location;
  } /**
  		 * Returns the locationChoices.
  		 * @return Collection
  		 */
  public Collection getLocationChoices() {
    return LimsConstants.VALID_USER_SELECTABLE_LIMS_LOCATIONS;
  } /**
  		 * Returns the operationType.
  		 * @return String
  		 */
  public String getOperationType() {
    return _operationType;
  } /**
  		 * Sets the operationType.
  		 * @param operationType The operationType to set
  		 */
  public void setOperationType(String operationType) {
    _operationType = operationType;
  } /**
  		 * Returns the setLocation.
  		 * @return String
  		 */
  public String getSetLocation() {
    return _setLocation;
  } /**
  		 * Sets the setLocation.
  		 * @param setLocation The setLocation to set
  		 */
  public void setSetLocation(String setLocation) {
    _setLocation = setLocation;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(BTXDetails, BigrActionMapping, HttpServletRequest)
   */
  public void describeIntoBtxDetails(
    BTXDetails btxDetails0,
    BigrActionMapping mapping,
    HttpServletRequest request) {
    super.describeIntoBtxDetails(btxDetails0, mapping, request);

    BTXDetailsSetSlideLocations setSlides = (BTXDetailsSetSlideLocations) btxDetails0;

    if (getOperationType().equals(BTXDetailsSetSlideLocations.OP_TYPE_SCAN_USER)) {
      setSlides.setOperationType(BTXDetailsSetSlideLocations.OP_TYPE_SCAN_USER);
      setSlides.setLimsUserId(getLimsUserId());
    }
    else if (getOperationType().equals(BTXDetailsSetSlideLocations.OP_TYPE_SCAN_SLIDE)) {
      setSlides.setOperationType(BTXDetailsSetSlideLocations.OP_TYPE_SCAN_SLIDE);
      setSlides.setSlideId(getSlideId());
    }
    else if (getOperationType().equals(BTXDetailsSetSlideLocations.OP_TYPE_SET_LOC)) {
      setSlides.setLimsUserId(getLimsUserId());
      ArrayList slideDataBeans = new ArrayList();
      SlideData slideData = new SlideData();
      slideData.setSlideId(getSlideId());
      slideData.setLocation(getLocation());
      slideDataBeans.add(slideData);
      setSlides.setSlideData(slideDataBeans);
      setSlides.setOperationType(BTXDetailsSetSlideLocations.OP_TYPE_SET_LOC);
    }

  }

  /**
   * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(BTXDetails)
   */
  public void populateFromBtxDetails(BTXDetails btxDetails) {
    super.populateFromBtxDetails(btxDetails);

    if (getOperationType().equals(BTXDetailsSetSlideLocations.OP_TYPE_SCAN_USER)) {
      if (!btxDetails.isTransactionCompleted()) {
        setLimsUserId(null);
      }
    }
    else if (getOperationType().equals(BTXDetailsSetSlideLocations.OP_TYPE_SCAN_SLIDE)) {
      if (!btxDetails.isTransactionCompleted()) {
        setSlideId(null);
      }
    }
    else if (getOperationType().equals(BTXDetailsSetSlideLocations.OP_TYPE_SET_LOC)) {

      setSlideId(null);
      setLocation(null);
    }
  }

  /**
   * Returns the message.
   * @return String
   */
  public String getMessage() {
    return _message;
  }

  /**
   * Sets the message.
   * @param message The message to set
   */
  public void setMessage(String message) {
    _message = message;
  }

}
