package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.query.*;

/**
 */
public class RnaAmountOnHoldForCartColumn extends SampleColumnImpl {

  public RnaAmountOnHoldForCartColumn(ViewParams cp) {
    super(
      54,
      "library.ss.result.header.rnaAmountOnHold.label",
      "library.ss.result.header.rnaAmountOnHold.comment", 
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_DI | ColumnPermissions.ROLE_CUST,
      ColumnPermissions.ALL ^ ColumnPermissions.SCRN_SAMP_AMOUNTS,
      ColumnPermissions.ALL ^ ColumnPermissions.TX_REQUEST_SAMP ^ ColumnPermissions.TX_ORDER_DETAIL,
      cp);
    setBodyColAlign("center");
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    String amt = rp.getItemHelper().getAmountOnHold();
    if ("10".equals(amt)) { // for 10, we show the user 5-10
      amt="5-10"; 
    }
    return amt;
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer(20);
    result.append("<td>");
    result.append(getRawBodyText(rp));
    result.append("</td>");
    return result.toString();
  }

}
