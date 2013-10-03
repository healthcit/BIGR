package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;

/**
 */
public class RnaHoldStatusColumn extends SampleColumnImpl {
  private SecurityInfo _secInfo = null;

  public RnaHoldStatusColumn(ViewParams cp) {
    super(
      90,
      "library.ss.result.header.rnaHoldStatus.label",
      "library.ss.result.header.rnaHoldStatus.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_DI,
      ColumnPermissions.SCRN_CONFIRM_REQ
        | ColumnPermissions.SCRN_HOLD_LIST
        | ColumnPermissions.SCRN_SELECTION | ColumnPermissions.SCRN_ICP,
      cp);
    _secInfo = ((cp == null) ? null : cp.getSecurityInfo());
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getProjectDescriptionAsText();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer(128);
    SampleSelectionHelper ssh = rp.getItemHelper();
 
    if (_secInfo.isInRoleSystemOwner() || (_secInfo.isInRoleDi() && ssh.isBms())) {
      result.append(ssh.getProjectDescriptionAsHtml());
    }
    else {
      result.append("<td></td>");
    }
    return result.toString();
  }

}
