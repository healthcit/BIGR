package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;

/**
 */
public class LogicalRepositoryShortNameColumn extends SampleColumnImpl {

  public LogicalRepositoryShortNameColumn(ViewParams cp) {
    super(
      75,
      "library.ss.logicalRepository.label",
      "library.ss.logicalRepository.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_CUST | ColumnPermissions.ROLE_DI,
      ColumnPermissions.SCRN_CONFIRM_REQ | ColumnPermissions.SCRN_HOLD_LIST | ColumnPermissions.SCRN_SELECTION | ColumnPermissions.SCRN_ICP | ColumnPermissions.SCRN_DERIV_OPS_SUMMARY | ColumnPermissions.SCRN_ORDER_DET,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getLogicalRepositoryShortNames();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(128);
    if (ssh.isLogicalRepositoryShortNamesTooLong()) {
      result.append("<td title=\"" + ssh.getLogicalRepositoryShortNames() + "\">");
    }
    else {
      result.append("<td>");
    }
    result.append(ssh.getLogicalRepositoryShortNamesShort());
    result.append("</td>");
    return result.toString();
  }

}
