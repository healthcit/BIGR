package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.query.*;

/**
 */
public class RnaPrepColumn extends SampleColumnImpl {

  public RnaPrepColumn(ViewParams cp) {
    super(
      45,
      "library.ss.result.header.rnaPrep.label",
      "library.ss.result.header.rnaPrep.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_CUST | ColumnPermissions.ROLE_DI,
      ColumnPermissions.ALL,
      cp);
    setBodyColAlign("center");
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getRnaPrep();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer(128);
    result.append("<td>");
    result.append(getRawBodyText(rp));
    result.append("</td>");
    return result.toString();
  }

}
