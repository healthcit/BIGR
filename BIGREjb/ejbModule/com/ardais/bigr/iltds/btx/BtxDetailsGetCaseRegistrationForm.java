package com.ardais.bigr.iltds.btx;

import java.util.Set;

import com.ardais.bigr.api.ApiException;

public class BtxDetailsGetCaseRegistrationForm extends BtxDetailsCreateImportedCase {

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_GET_CASE_REG_FORM;
  }

  public Set getDirectlyInvolvedObjects() {
    return null;
  }

  protected String doGetDetailsAsHTML() {
    throw new ApiException("doGetDetailsAsHTML should not have been called, this class does not support transaction logging.");
  }

  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("describeIntoHistoryRecord should not have been called, this class does not support transaction logging.");
  }

  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("populateFromHistoryRecord should not have been called, this class does not support transaction logging.");
  }

}
