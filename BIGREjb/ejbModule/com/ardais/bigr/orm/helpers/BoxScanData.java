package com.ardais.bigr.orm.helpers;

import java.io.Serializable;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.javabeans.AccountBoxLayoutDto;

public class BoxScanData implements Serializable {
  static final long serialVersionUID = 8881212491758368573L;

  private List _accountBoxLayouts = null;
  private String _defaultBoxLayoutId = null;
  
  /**
   * Return the number of account box layouts for this account.
   * 
   * @return int
   */
  public int getNumberOfAccountBoxLayouts() {
    int numberOfAccountBoxLayouts = 0;
    List accountBoxLayouts = getAccountBoxLayouts();

    if (!ApiFunctions.isEmpty(accountBoxLayouts)) {
      numberOfAccountBoxLayouts = accountBoxLayouts.size();
    }
    return numberOfAccountBoxLayouts;
  }
  
  /**
   * Return all the box layouts that exist in the system for this account as a legal value set.
   * 
   * @return LegalValueSet
   */
  public LegalValueSet getAccountBoxLayoutSet() {
    LegalValueSet lvs = new LegalValueSet();

    List accountBoxLayouts = getAccountBoxLayouts();

    for (int i = 0; i < accountBoxLayouts.size(); i++) {
      AccountBoxLayoutDto accountBoxLayoutDto = (AccountBoxLayoutDto)accountBoxLayouts.get(i);
      lvs.addLegalValue(accountBoxLayoutDto.getBoxLayoutId(), accountBoxLayoutDto.getBoxLayoutName());
    }
    return lvs;
  }
  
  /**
   * @return
   */
  public List getAccountBoxLayouts() {
    return _accountBoxLayouts;
  }

  /**
   * @return
   */
  public String getDefaultBoxLayoutId() {
    String defaultBoxLayoutId = _defaultBoxLayoutId;
    if ((ApiFunctions.isEmpty(_defaultBoxLayoutId)) && !ApiFunctions.isEmpty(_accountBoxLayouts)) {
      AccountBoxLayoutDto accountBoxLayoutDto = (AccountBoxLayoutDto)_accountBoxLayouts.get(0);
      defaultBoxLayoutId = accountBoxLayoutDto.getBoxLayoutId();
    }
    return defaultBoxLayoutId;
  }
  
  public boolean isEmpty() {
    return ApiFunctions.isEmpty(_accountBoxLayouts);
  }

  /**
   * @param list
   */
  public void setAccountBoxLayouts(List list) {
    _accountBoxLayouts = list;
  }

  /**
   * @param string
   */
  public void setDefaultBoxLayoutId(String string) {
    _defaultBoxLayoutId = string;
  }
}
