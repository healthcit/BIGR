package com.ardais.bigr.iltds.databeans;

import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;
import com.ardais.bigr.util.PolicyUtils;

public class PolicyExtendedData extends PolicyData {
  static final long serialVersionUID = -6598187378827104039L;
  
  private List _associatedIrbIdList = null;
  private SecurityInfo _securityInfo;

  public PolicyExtendedData() {
    super();
  }

  public void processSecurityInfo(SecurityInfo securityInfo) {
    setAssociatedIrbIdList(PolicyUtils.getIrbsRelatedToPolicy(getPolicyId()));
    StringBuffer sb = new StringBuffer(512);

    Iterator iterator = getAssociatedIrbIdList().iterator();
    boolean addComma = false;
    while (iterator.hasNext()) {
      if (addComma) {
        sb.append(", ");
      }
      addComma = true;
      sb.append((String)iterator.next());
    }

    // Set the associated irbs field.
    setAssociatedIrbs(sb.toString());
    
    setSecurityInfo(securityInfo);
  }

  public String getAccountName() {
    String accountName = null;
    String accountId = getAccountId();
    if (_securityInfo != null) {
      accountName = IltdsUtils.getAccountName(accountId) + " (" + IcpUtils.prepareLink(accountId, _securityInfo) + ")";
    }
    else {
      accountName = IltdsUtils.getAccountName(accountId) + " (" + accountId + ")";
    }
    return accountName;
  }

  /**
   * @return
   */
  public List getAssociatedIrbIdList() {
    return _associatedIrbIdList;
  }

  /**
   * @return
   */
  public SecurityInfo getSecurityInfo() {
    return _securityInfo;
  }

  /**
   * @param list
   */
  public void setAssociatedIrbIdList(List list) {
    _associatedIrbIdList = list;
  }

  /**
   * @param info
   */
  public void setSecurityInfo(SecurityInfo info) {
    _securityInfo = info;
  }
}
