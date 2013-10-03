package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.query.*;

/**
 */
public class HngColumn extends SampleColumnImpl {

  public HngColumn(ViewParams cp) {
    super(
      200,
      "library.ss.hng.label",
      "library.ss.hng.result.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_CUST | ColumnPermissions.ROLE_DI,
      ColumnPermissions.SCRN_CONFIRM_REQ | ColumnPermissions.SCRN_HOLD_LIST | ColumnPermissions.SCRN_SELECTION | ColumnPermissions.SCRN_ICP | ColumnPermissions.SCRN_DERIV_OPS_SUMMARY | ColumnPermissions.SCRN_ORDER_DET,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getHistologicalNuclearGrade();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer(128);
    result.append("<td>");
//    result.append("&nbsp;&nbsp;");
    result.append(getRawBodyText(rp));
    result.append("</td>");
    return result.toString();
  }

}
