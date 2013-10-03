package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;

/**
 */
public class PvNotesInternalColumn extends SampleColumnImpl {

  public PvNotesInternalColumn(ViewParams cp) {
    super(
      200,
      "library.ss.result.header.verifNotesInt.label",
      "library.ss.result.header.verifNotesInt.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER,
      ColumnPermissions.SCRN_CONFIRM_REQ | ColumnPermissions.SCRN_HOLD_LIST | ColumnPermissions.SCRN_SELECTION | ColumnPermissions.SCRN_ICP | ColumnPermissions.SCRN_DERIV_OPS_SUMMARY,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getInternalData() + " " + rp.getItemHelper().getHistoNotes();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(128);
    if (ssh.isInternalDataTooLong()) {
      result.append("<td onmouseout=\"return nd();\" onmouseover=\"return overlib('");
      Escaper.jsEscapeInXMLAttr(ssh.getInternalDataHtml(), result);
      result.append("');\">");
    }
    else {
      result.append("<td>");
    }
    Escaper.htmlEscape(ssh.getInternalDataShort(), result);
    result.append("</td>");
    return result.toString();
  }

}
