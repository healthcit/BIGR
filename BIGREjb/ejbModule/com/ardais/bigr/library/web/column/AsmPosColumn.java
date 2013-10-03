package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;

/**
 */
public class AsmPosColumn extends SampleColumnImpl {

  public AsmPosColumn(ViewParams cp) {
    super(
      30,
      "library.ss.result.header.asmId.label",
      "library.ss.result.header.asmId.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_CUST | ColumnPermissions.ROLE_DI,
      ColumnPermissions.SCRN_CONFIRM_REQ | ColumnPermissions.SCRN_HOLD_LIST | ColumnPermissions.SCRN_SELECTION | ColumnPermissions.SCRN_ICP | ColumnPermissions.SCRN_DERIV_OPS_SUMMARY,
      cp);
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getAsmPosition();
  }

  protected String getBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer(128);
    result.append("<td>");
    result.append(getRawBodyText(rp));
    result.append("</td>");
    return result.toString();
  }

}
