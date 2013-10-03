package com.ardais.bigr.orm.web.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainAccountUrl;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.UrlUtils;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * Struts ActionForm class for maintaining account urls.
 */
public class MaintainAccountUrlForm extends BigrActionForm {
  private boolean _resetForm;
  private boolean _retry;
  
  private String _operation = null;
  private String _urlId = null;
  private String _accountId = null;
  private String _objectType = null;
  private String _url = null;
  private String _label = null;
  private String _target = null;
  private LegalValueSet _accountList = null;
  
  // The list of urls to which the account had access to at the start of this transaction.
  private List _urls = null;
  
  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _resetForm = false;
    _retry = false;
    _operation = Constants.OPERATION_CREATE;
    
    _urlId = null;
    _accountId = null;
    _objectType = null;
    _url = null;
    _label = null;
    _target = null;
    _urls = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {

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
    // When we reset the form, we don't do any further validation.
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
   * @return
   */
  public String getAccountId() {
    return _accountId;
  }

  /**
   * @return
   */
  public List getUrls() {
    return _urls;
  }

  /**
   * @param string
   */
  public void setAccountId(String string) {
    _accountId = string;
  }

  /**
   * @param list
   */
  public void setUrls(List list) {
    _urls = list;
  }

  /**
   * @return
   */
  public String getLabel() {
    return _label;
  }

  /**
   * @return
   */
  public String getObjectType() {
    return _objectType;
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
  public String getTarget() {
    return _target;
  }

  /**
   * @return
   */
  public String getUrl() {
    return _url;
  }

  /**
   * @return
   */
  public String getUrlId() {
    return _urlId;
  }

  /**
   * @param string
   */
  public void setLabel(String string) {
    _label = string;
  }

  /**
   * @param string
   */
  public void setObjectType(String string) {
    _objectType = string;
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
  public void setTarget(String string) {
    _target = string;
  }

  /**
   * @param string
   */
  public void setUrl(String string) {
    _url = string;
  }

  /**
   * @param string
   */
  public void setUrlId(String string) {
    _urlId = string;
  }

  /**
   * @return
   */
  public LegalValueSet getLegalObjectTypes() {
    LegalValueSet lvs = new LegalValueSet();
    lvs.addLegalValue(UrlUtils.OBJECT_TYPE_DONOR, "Donor");
    lvs.addLegalValue(UrlUtils.OBJECT_TYPE_CASE, "Case");
    lvs.addLegalValue(UrlUtils.OBJECT_TYPE_SAMPLE, "Sample");
    lvs.addLegalValue(UrlUtils.OBJECT_TYPE_MENU, "Main menu");
    return lvs;
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
   * @return
   */
  public LegalValueSet getAccountList() {
    return _accountList;
  }

  /**
   * @param set
   */
  public void setAccountList(LegalValueSet set) {
    _accountList = set;
  }

}
