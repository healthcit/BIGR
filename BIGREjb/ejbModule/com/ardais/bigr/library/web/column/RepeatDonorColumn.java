package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.query.*;

/**
 */
public class RepeatDonorColumn extends SampleColumnImpl {

  public RepeatDonorColumn(ViewParams cp) {
    super(
      20,
      "library.ss.result.header.rep.label",
      "library.ss.result.header.rep.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER,
      ColumnPermissions.ALL,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getDonorConsentCount();
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
