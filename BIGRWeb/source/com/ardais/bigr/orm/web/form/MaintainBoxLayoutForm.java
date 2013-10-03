package com.ardais.bigr.orm.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainBoxLayoutStart;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * Struts ActionForm class for maintaining box layouts.
 */
public class MaintainBoxLayoutForm extends BigrActionForm {
  private boolean _resetForm;
  private boolean _retry;

  private String _operation; // "Create", "Update", or "Delete"

  private String _boxLayoutId;
  private String _numberOfColumns;
  private String _numberOfRows;
  private String _xaxisLabelCid;
  private String _yaxisLabelCid;
  private String _tabIndexCid;

  private List _boxLayoutForms;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _resetForm = false;
    _retry = false;
    _operation = Constants.OPERATION_CREATE;

    _boxLayoutId = null;
    _numberOfColumns = "0";
    _numberOfRows = "0";
    _xaxisLabelCid = null;
    _yaxisLabelCid = null;
    _tabIndexCid = null;

    _boxLayoutForms = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    // Note: only primitive validations occur here. Any business rule or
    // business validation should occur in the BTX performer class.

    ActionErrors errors = new ActionErrors();
    ActionError error = null;

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
    else {
      // Form level validation will short circuit performer level validation. Users may
      // have to submit the page more than once to see all the validation checks. If
      // any of the following checks fail, no performer level validation is done. This
      // is something we should look into solving.

      // Check for required field: number of columns.
      if (ApiFunctions.isEmpty(getNumberOfColumns())) {
        // Required parameter.
        error = new ActionError("orm.error.boxLayout.requiredNumberOfColumns");
        errors.add(ActionErrors.GLOBAL_ERROR, error);
      }
      else {
        // Validate if value is an integer.
        if (!ApiFunctions.isInteger(getNumberOfColumns())) {
          error = new ActionError("errors.int", "Number of columns");
          errors.add(ActionErrors.GLOBAL_ERROR, error);
        }
      }

      // Check for required field: allocation denominator.
      if (ApiFunctions.isEmpty(getNumberOfRows())) {
        // Required parameter.
        error = new ActionError("orm.error.boxLayout.requiredNumberOfRows");
        errors.add(ActionErrors.GLOBAL_ERROR, error);
      }
      else {
        // Validate if value is an integer.
        if (!ApiFunctions.isInteger(getNumberOfRows())) {
          error = new ActionError("errors.int", "Number of rows");
          errors.add(ActionErrors.GLOBAL_ERROR, error);
        }
      }
      // Set all the box layouts if an error has occured at form level validations. This is
      // normally handled at BTX level validation. In this case, conversion checks are being
      // made at the form level, which bypass BTX level validation.
      if (!errors.isEmpty()) {
        setBoxLayoutForms(convertToBoxLayoutForms(BoxLayoutUtils.getAllBoxLayoutDtos()));
      }

      // No further validations here.  All validations are done in the BTX business logic bean.
      return errors;
    }
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
    if (isRetry() && btxDetails0 instanceof BtxDetailsMaintainBoxLayoutStart) {
      BtxDetailsMaintainBoxLayoutStart btxDetails =
        (BtxDetailsMaintainBoxLayoutStart) btxDetails0;
      btxDetails.setPopulateOutputFields(false);
    }
  }

  /**
   * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails)
   */
  public void populateFromBtxDetails(BTXDetails btxDetails) {
    super.populateFromBtxDetails(btxDetails);

    // Convert the list of BoxLayoutDtos into BoxLayoutForms.
    if (btxDetails instanceof BtxDetailsMaintainBoxLayoutStart) {
      setBoxLayoutForms(convertToBoxLayoutForms(BoxLayoutUtils.getAllBoxLayoutDtos()));
    }
  }

  /**
   * Convert a list of box layout dtos to a list of box layout forms.
   * 
   * @param boxLayoutDtos
   * @return
   */
  private List convertToBoxLayoutForms(List boxLayoutDtos) {
    List boxLayoutForms = new ArrayList();
        
    for (int i = 0; i < boxLayoutDtos.size(); i++) {
      BoxLayoutDto boxLayoutDto = (BoxLayoutDto)boxLayoutDtos.get(i);
      BoxLayoutForm boxLayoutForm = new BoxLayoutForm();

      BigrBeanUtilsBean.SINGLETON.copyProperties(boxLayoutForm, boxLayoutDto);

      boxLayoutForms.add(boxLayoutForm);
    }
    
    return boxLayoutForms;
  }

  /**
   * Return the legal value set for the box layout label types.
   * 
   * @return LegalValueSet
   */
  public LegalValueSet getBoxLayoutLabelTypeSet() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_BOX_LAYOUT_LABEL_TYPE_XY_AXIS);
  }

  /**
   * Return the legal value set for the box layout tab index types.
   * 
   * @return LegalValueSet
   */
  public LegalValueSet getBoxLayoutTabIndexSet() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_BOX_LAYOUT_TAB_DIRECTION);
  }

  /**
   * @return
   */
  public List getBoxLayoutForms() {
    return _boxLayoutForms;
  }

  /**
   * @return
   */
  public String getBoxLayoutId() {
    return _boxLayoutId;
  }

  /**
   * @return
   */
  public String getNumberOfColumns() {
    return _numberOfColumns;
  }

  /**
   * @return
   */
  public String getNumberOfRows() {
    return _numberOfRows;
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
   * @return
   */
  public String getTabIndexCid() {
    return _tabIndexCid;
  }

  /**
   * @return
   */
  public String getXaxisLabelCid() {
    return _xaxisLabelCid;
  }

  /**
   * @return
   */
  public String getYaxisLabelCid() {
    return _yaxisLabelCid;
  }

  /**
   * @param list
   */
  public void setBoxLayoutForms(List list) {
    _boxLayoutForms = list;
  }

  /**
   * @param string
   */
  public void setBoxLayoutId(String string) {
    _boxLayoutId = string;
  }

  /**
   * @param string
   */
  public void setNumberOfColumns(String string) {
    _numberOfColumns = string;
  }

  /**
   * @param string
   */
  public void setNumberOfRows(String string) {
    _numberOfRows = string;
  }

  /**
   * @param string
   */
  public void setOperation(String string) {
    _operation = string;
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

  /**
   * @param string
   */
  public void setTabIndexCid(String string) {
    _tabIndexCid = string;
  }

  /**
   * @param string
   */
  public void setXaxisLabelCid(String string) {
    _xaxisLabelCid = string;
  }

  /**
   * @param string
   */
  public void setYaxisLabelCid(String string) {
    _yaxisLabelCid = string;
  }
}
