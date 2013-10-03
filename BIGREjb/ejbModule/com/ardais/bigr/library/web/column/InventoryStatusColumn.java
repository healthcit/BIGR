package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;

/**
 */
public class InventoryStatusColumn extends SampleColumnImpl {
  
  private SecurityInfo _securityInfo = null;

  public InventoryStatusColumn(ViewParams cp) {
    super(
      30,
      "library.ss.result.header.invStatus.label",
      "library.ss.result.header.invStatus.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_DI,
      ColumnPermissions.ALL,
      cp);
    _securityInfo = (cp == null) ? null : cp.getSecurityInfo();
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    String status = rp.getItemHelper().getInventoryStatus();
    StringBuffer result = new StringBuffer(128);
    if (_securityInfo.isInRoleSystemOwner()) {
      result.append(status);
    }
    else if (_securityInfo.isInRoleDi()) {
      if (ssh.isBms()) {
        if (ssh.isRna()) {
          // TODO: Show appropriate status details for RNA.  This record could be RNA
          // because this column appears on the hold list page where a particular row could be
          // either tissue or RNA.
          result.append("N/A");
        }
        else { // not isRna
          result.append(status);
        }
      }
    }
    return result.toString();
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
