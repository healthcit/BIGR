package com.ardais.bigr.orm.web.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainLogicalRepository;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainLogicalRepositoryStart;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * Struts ActionForm class for maintaining logical repositories.
 */
public class MaintainLogicalRepositoryForm extends BigrActionForm {
  private boolean _resetForm;
  private boolean _retry;
  private String _operation; // "Create", "Update", or "Delete"
  private String _repositoryId;
  private String _repositoryFullName; // must be null for op = "Delete"
  private String _repositoryShortName; // must be null for op = "Delete"
  private Boolean _bms; // ignored for op = "Delete"
  private List _logicalRepositories; // list of LogicalRepository data beans

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _resetForm = false;
    _retry = false;
    _operation = Constants.OPERATION_CREATE;
    _repositoryId = null;
    _repositoryFullName = null;
    _repositoryShortName = null;
    _bms = java.lang.Boolean.TRUE; 
    _logicalRepositories = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    // All validation is done in the BTX performer.

    boolean doNotValidate = false;

    // If retry is true, it means we're coming back to this form to display validation errors.
    // In most cases we don't have to do anything special, but the "Delete" operation is a
    // special case since the form fields aren't used to gather input for Delete operations.
    // So, when the operation is "Delete" and we're retrying, we reset the form.  The user
    // will still see the validation errors, but the form will otherwise look like a fresh
    // "Create" form.
    //
    // If we're retrying we don't do any further validation, since we can assume in this
    // case that there are problems and we're just trying to report them.
    //
    if (isRetry()) {
      doNotValidate = true;
      if (Constants.OPERATION_DELETE.equals(getOperation())) {
        setResetForm(true);
      }
    }

    // The isResetForm() "field" is an instruction that the form fields are to be reset to their
    // default values before proceeding.
    //
    // When we reset the form, we don't do any further validation.
    //
    if (isResetForm()) {
      doNotValidate = true;
      doReset(mapping, request);
    }

    if (doNotValidate) {
      return null;
    }
    
    // No further validations here.  All validations are done in the BTX business logic bean.

    return null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void describeIntoBtxDetails(
    BTXDetails btxDetails0,
    BigrActionMapping mapping,
    HttpServletRequest request) {

    super.describeIntoBtxDetails(btxDetails0, mapping, request);

    // If we're retrying and the btxDetails object is the "Start" version, set an extra
    // flag on the btxDetails object telling it not to populate some of its usual output
    // fields.  This is useful when the user attempted an update but got a validation
    // error, and we want to keep their data fields as-is for them to correct instead of
    // resetting them to the values in the database, which is what would happen by default.
    //
    if (isRetry() && btxDetails0 instanceof BtxDetailsMaintainLogicalRepositoryStart) {
      BtxDetailsMaintainLogicalRepositoryStart btxDetails =
        (BtxDetailsMaintainLogicalRepositoryStart) btxDetails0;
      btxDetails.setPopulateRepositoryOutputFields(false);
    }
  }

  /**
   * @return
   */
  public Boolean getBms() {
    return _bms;
  }

  /**
   * @return a list of {@link com.ardais.bigr.iltds.assistants.LogicalRepository} objects
   *    representing all existing logical repositories.
   */
  public List getLogicalRepositories() {
    return _logicalRepositories;
  }

  /**
   * @return
   */
  public String getOperation() {
    return _operation;
  }

  /**
   * @return
   */
  public String getRepositoryFullName() {
    return _repositoryFullName;
  }

  /**
   * @return
   */
  public String getRepositoryId() {
    return _repositoryId;
  }

  /**
   * @return
   */
  public String getRepositoryShortName() {
    return _repositoryShortName;
  }

  /**
   * @return
   */
  public boolean isResetForm() {
    return _resetForm;
  }

  /**
   * @return
   */
  public boolean isRetry() {
    return _retry;
  }

  /**
   * @param boolean1
   */
  public void setBms(Boolean boolean1) {
    _bms = boolean1;
  }

  /**
   * @param list a list of {@link com.ardais.bigr.iltds.assistants.LogicalRepository} objects.
   */
  public void setLogicalRepositories(List list) {
    _logicalRepositories = list;
  }

  /**
   * @param string
   */
  public void setOperation(String string) {
    _operation = string;
  }

  /**
   * @param string
   */
  public void setRepositoryFullName(String string) {
    _repositoryFullName = string;
  }

  /**
   * @param string
   */
  public void setRepositoryId(String string) {
    _repositoryId = string;
  }

  /**
   * @param string
   */
  public void setRepositoryShortName(String string) {
    _repositoryShortName = string;
  }

  /**
   * @param b
   */
  public void setResetForm(boolean b) {
    _resetForm = b;
  }

  /**
   * @param b
   */
  public void setRetry(boolean b) {
    _retry = b;
  }

}
