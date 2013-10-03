package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;

/**
 */
public class RnaNotesColumn extends SampleColumnImpl {

  private SecurityInfo _securityInfo;

  public RnaNotesColumn(ViewParams cp) {
    super(
      200,
      "library.ss.result.header.rnaNotes.label",
      "library.ss.result.header.rnaNotes.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_DI,
      ColumnPermissions.SCRN_SELECTION,
      cp);
    _securityInfo = (cp == null) ? null : cp.getSecurityInfo();
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getRnaNotes();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(com.ardais.bigr.library.web.column.SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();

    // The column is only available for Ardais and DI users, but there is an additional 
    // restriction that DI users can only see BMS RNA.  Set the flag appropriately as to whether
    // we should show anything in the column.
    boolean showCol = true;
    if (_securityInfo == null) {
      showCol = false;
    } 
    else if ((_securityInfo.isInRoleDi()) && (!ssh.isBms())) {
      showCol = false;
    } 

    StringBuffer result = new StringBuffer(128);
    if (showCol && (ssh.isRnaNotesTooLong())) {
      result.append("<td onmouseout=\"return nd();\" onmouseover=\"return overlib('");
      Escaper.jsEscapeInXMLAttr(ssh.getRnaNotesHtml(), result);
      result.append("');\">");
    }
    else {
      result.append("<td>");
    }
    if (showCol) {
      Escaper.htmlEscape(ssh.getRnaNotesShort(), result);
    }
    else {
      result.append("&nbsp;");
    }
    result.append("</td>");
    return result.toString();
  }

}
