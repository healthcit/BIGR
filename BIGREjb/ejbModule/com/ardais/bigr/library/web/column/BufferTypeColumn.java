package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;

/**
 */
public class BufferTypeColumn extends SampleColumnImpl {

  public BufferTypeColumn(ViewParams cp) {
    super(
      140,
      "library.ss.result.header.bufferType.label",
      "library.ss.result.header.bufferType.comment",
      ColumnPermissions.ROLE_ALL,
      ColumnPermissions.SCRN_SELECTION
        | ColumnPermissions.SCRN_HOLD_LIST
        | ColumnPermissions.SCRN_CONFIRM_REQ
        | ColumnPermissions.SCRN_ORDER_DET
        | ColumnPermissions.SCRN_ICP
        | ColumnPermissions.SCRN_DERIV_OPS_SUMMARY,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    return ssh.getBufferType();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(com.ardais.bigr.library.web.column.SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {

    StringBuffer result = new StringBuffer(128);
    result.append("<td>");
    Escaper.htmlEscape(getRawBodyText(rp), result);
    result.append("</td>");
    return result.toString();
  }
}
