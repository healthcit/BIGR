package com.ardais.bigr.orm.web.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class ManageShippingPartnersForm extends BigrActionForm {
  
  private String _accountId = null;
  private String _accountName = null;
  
  private String[] _selectedShippingPartners = null;
  
  private List _availableShippingPartners = null;
  private List _assignedShippingPartners = null;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _accountId = null;
    _accountName = null;
    _selectedShippingPartners = null;
    
    _availableShippingPartners = null;
    _assignedShippingPartners = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
     return null; 
  }

  /**
   * @return a String containing information about the Account
   */
  public String getAccountInformation() {
    StringBuffer buff = new StringBuffer(50);
    String accountName = getAccountName();
    if (accountName != null) {
      buff.append(accountName);
    }
    String accountId = getAccountId();
    if (accountId != null) {
      buff.append(" (");
      buff.append(accountId);
      buff.append(")");
    }
    return buff.toString();
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
  public String getAccountName() {
    return _accountName;
  }

  /**
   * @return
   */
  public List getAssignedShippingPartners() {
    return _assignedShippingPartners;
  }

  /**
   * @return
   */
  public List getAvailableShippingPartners() {
    return _availableShippingPartners;
  }

  /**
   * @return
   */
  public String[] getSelectedShippingPartners() {
    return _selectedShippingPartners;
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
  public void setAccountName(String string) {
    _accountName = string;
  }

  /**
   * @param list
   */
  public void setAssignedShippingPartners(List list) {
    _assignedShippingPartners = list;
  }

  /**
   * @param list
   */
  public void setAvailableShippingPartners(List list) {
    _availableShippingPartners = list;
  }

  /**
   * @param strings
   */
  public void setSelectedShippingPartners(String[] strings) {
    _selectedShippingPartners = strings;
  }
}
