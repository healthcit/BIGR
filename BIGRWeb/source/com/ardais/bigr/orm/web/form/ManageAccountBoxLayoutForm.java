package com.ardais.bigr.orm.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.javabeans.AccountBoxLayoutDto;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.orm.btx.BtxDetailsManageAccountBoxLayout;
import com.ardais.bigr.orm.btx.BtxDetailsManageAccountBoxLayoutStart;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * Struts ActionForm class for managing account box layouts.
 */
public class ManageAccountBoxLayoutForm extends BigrActionForm implements AccountBasedForm {
  private boolean _resetForm;
  private boolean _retry;

  private String _operation; // "Create", "Update", or "Delete"

  private String _accountBoxLayoutId;

  private String _boxLayoutName;
  private String _accountId;
  private String _boxLayoutId;
  private boolean _defaultAccountBoxLayout;
  
  private String _currentDefaultBoxLayoutId;

  private List _accountBoxLayoutForms;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _resetForm = false;
    _retry = false;
    _operation = Constants.OPERATION_CREATE;
    
    _accountBoxLayoutId = "0";
    _boxLayoutName = null;
    _boxLayoutId = null;
    _defaultAccountBoxLayout = false;
    
    _currentDefaultBoxLayoutId = null;

    _accountBoxLayoutForms = null;
    
    // Do not reset the accountId and accountName fields.
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    // Note: only primitive validations occur here. Any business rule or
    // business validation should occur in the BTX performer class.

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
    if (isRetry() && btxDetails0 instanceof BtxDetailsManageAccountBoxLayoutStart) {
      BtxDetailsManageAccountBoxLayoutStart btxDetails =
        (BtxDetailsManageAccountBoxLayoutStart) btxDetails0;
      btxDetails.setPopulateOutputFields(false);
    }
  }

  /**
   * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails)
   */
  public void populateFromBtxDetails(BTXDetails btxDetails) {
    super.populateFromBtxDetails(btxDetails);

    // Convert the list of AccountBoxLayoutDtos into AccountBoxLayoutForms.
    if (btxDetails instanceof BtxDetailsManageAccountBoxLayoutStart) {
      BtxDetailsManageAccountBoxLayoutStart btx = (BtxDetailsManageAccountBoxLayoutStart) btxDetails;
      
      String currentDefaultBoxLayoutId = getCurrentDefaultBoxLayoutId();

      if (Constants.OPERATION_UPDATE.equals(getOperation())) {
        String boxLayoutId = getBoxLayoutId();
        if (!ApiFunctions.isEmpty(boxLayoutId)) {
          if (boxLayoutId.equals(currentDefaultBoxLayoutId)) {
            setDefaultAccountBoxLayout(true);
          }
        }
      }

      setAccountBoxLayoutForms(convertToAccountBoxLayoutForms(currentDefaultBoxLayoutId, btx.getAccountBoxLayouts()));
    }
  }

  /**
   * Convert a list of account box layout dtos to a list of account box layout forms.
   * 
   * @param boxLayoutDtos
   * @return
   */
  private List convertToAccountBoxLayoutForms(String defaultBoxLayoutId, List accountBoxLayoutDtos) {
    List accountBoxLayoutForms = new ArrayList();
        
    if (!ApiFunctions.isEmpty(accountBoxLayoutDtos)) {
      for (int i = 0; i < accountBoxLayoutDtos.size(); i++) {
        AccountBoxLayoutDto accountBoxLayoutDto = (AccountBoxLayoutDto)accountBoxLayoutDtos.get(i);
        AccountBoxLayoutForm accountBoxLayoutForm = new AccountBoxLayoutForm();

        BigrBeanUtilsBean.SINGLETON.copyProperties(accountBoxLayoutForm, accountBoxLayoutDto);
      
        if (accountBoxLayoutForm.getBoxLayoutId().equalsIgnoreCase(defaultBoxLayoutId)) {
          accountBoxLayoutForm.setCurrentDefaultAccountBoxLayout(true);
        }

        accountBoxLayoutForms.add(accountBoxLayoutForm);
      }
    }
    
    return accountBoxLayoutForms;
  }

  /**
   * Return all the box layouts that exist in the system as a legal value set.
   * 
   * @return LegalValueSet
   */
  public LegalValueSet getBoxLayoutAttributeSet() {
    LegalValueSet lvs = new LegalValueSet();

    List boxLayouts = BoxLayoutUtils.getAllBoxLayoutDtos();
    for (int i = 0; i < boxLayouts.size(); i++) {
      BoxLayoutDto boxLayoutDto = (BoxLayoutDto)boxLayouts.get(i);
      BoxLayoutForm boxLayoutForm = new BoxLayoutForm();
      BigrBeanUtilsBean.SINGLETON.copyProperties(boxLayoutForm, boxLayoutDto);
      
      lvs.addLegalValue(boxLayoutForm.getBoxLayoutId(), boxLayoutForm.getBoxLayoutAttributes());
    }

    return lvs;
  }

  /**
   * @return
   */
  public List getAccountBoxLayoutForms() {
    return _accountBoxLayoutForms;
  }

  /**
   * @return
   */
  public String getAccountBoxLayoutId() {
    return _accountBoxLayoutId;
  }

  /**
   * @return
   */
  public String getAccountId() {
    return _accountId;
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
  public String getBoxLayoutName() {
    return _boxLayoutName;
  }

  /**
   * @return
   */
  public String getCurrentDefaultBoxLayoutId() {
    return _currentDefaultBoxLayoutId;
  }

  /**
   * @return
   */
  public boolean isDefaultAccountBoxLayout() {
    return _defaultAccountBoxLayout;
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
   * @param list
   */
  public void setAccountBoxLayoutForms(List list) {
    _accountBoxLayoutForms = list;
  }

  /**
   * @param string
   */
  public void setAccountBoxLayoutId(String string) {
    _accountBoxLayoutId = string;
  }

  /**
   * @param string
   */
  public void setAccountId(String string) {
    _accountId = string;
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
  public void setBoxLayoutName(String string) {
    _boxLayoutName = string;
  }

  /**
   * @param string
   */
  public void setCurrentDefaultBoxLayoutId(String string) {
    _currentDefaultBoxLayoutId = string;
  }

  /**
   * @param b
   */
  public void setDefaultAccountBoxLayout(boolean b) {
    _defaultAccountBoxLayout = b;
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

  /* (non-Javadoc)
   * @see com.ardais.bigr.orm.web.form.AccountBasedForm#getAccountData()
   */
  public AccountDto getAccountData() {
    AccountDto accountData = new AccountDto();
    accountData.setId(getAccountId());
    return accountData;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.orm.web.form.AccountBasedForm#getObjectType()
   */
  public String getObjectType() {
    return Constants.OBJECT_TYPE_ACCOUNT;
  }
}
