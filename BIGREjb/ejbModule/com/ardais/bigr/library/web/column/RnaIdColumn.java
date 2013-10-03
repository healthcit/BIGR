package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RnaIdColumn extends SampleColumnImpl {

  private SecurityInfo _secInfo;
  /**
   * Constructor for SampleIdColumn.
   * @param width
   * @param headerText
   * @param headerTooltip
   * @param bodyText
   * @param flags
   */
  public RnaIdColumn(ViewParams cp) {
    super(
      81,
      "library.ss.result.header.rnaId.label",
      "library.ss.result.header.rnaId.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_DI,
      ColumnPermissions.SCRN_SELECTION | ColumnPermissions.SCRN_ICP,
      cp);
    _secInfo = ((cp == null) ? null : cp.getSecurityInfo());
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getRnaVialId();
  }

  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();

    boolean showRnaVialId = false;
    String rnaVialId = IcpUtils.preparePopupLink(getRawBodyText(rp), _secInfo);
    if (!ApiFunctions.isEmpty(rnaVialId)) {
      if (_secInfo.isInRoleSystemOwner() || (_secInfo.isInRoleDi() && ssh.isBms())) {
        showRnaVialId = true;
      }
    }

    StringBuffer result = new StringBuffer(128);
    result.append("<td>");
    if (showRnaVialId) {
      result.append(rnaVialId);
    }
    result.append("</td>");
    return result.toString();
  }
}
