package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;

/**
 */
public class RnaAmountRemainingColumn extends SampleColumnImpl {

  private SecurityInfo _secInfo = null;

  public RnaAmountRemainingColumn(ViewParams cp) {
    super(
      50,
      "library.ss.result.header.rnaAmountRemaining.label",
      "library.ss.result.header.rnaAmountRemaining.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_DI,
      ColumnPermissions.ALL,
      cp);
    _secInfo = ((cp == null) ? null : cp.getSecurityInfo());
    setBodyColAlign("center");
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getRnaAmountRemaining();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();

    boolean showRnaAmountRemaining = false;
    String rnaAmountRemaining = getRawBodyText(rp);
    if (!ApiFunctions.isEmpty(rnaAmountRemaining)) {
      if (_secInfo.isInRoleSystemOwner() || (_secInfo.isInRoleDi() && ssh.isBms())) {
        showRnaAmountRemaining = true;
      }
    }

    StringBuffer result = new StringBuffer(128);
    result.append("<td>");
    if (showRnaAmountRemaining) {
      result.append(rnaAmountRemaining);
    }
    result.append("</td>");
    return result.toString();
  }
}
