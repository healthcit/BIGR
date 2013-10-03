package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;

/**
 */
public class QcStatusColumn extends SampleColumnImpl {

  public QcStatusColumn(ViewParams cp) {
    super(
      31,
      "library.ss.result.header.qcStatus.label",
      "library.ss.result.header.qcStatus.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER,
      ColumnPermissions.SCRN_CONFIRM_REQ | ColumnPermissions.SCRN_HOLD_LIST | ColumnPermissions.SCRN_SELECTION | ColumnPermissions.SCRN_ICP | ColumnPermissions.SCRN_DERIV_OPS_SUMMARY,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getQcStatus();
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(128);
    if (ssh.isPulled()) {
      String pullDetails = ssh.getPullDetails();
      result.append("<td title='"+pullDetails+"'>");
    }
    else {
      result.append("<td>");
    }
    result.append(getRawBodyText(rp));
    result.append("</td>");
    return result.toString();
  }

}
