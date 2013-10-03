package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;

/**
 */
public class AsmTissueColumn extends SampleColumnImpl {

  // MR 7333: This column is being added to support Julie's work where she uses ICP Case
  // to QC data.  Since it is only being added for her, we'll start out by making this
  // only available to system owner users, and only in the ICP context. 

  public AsmTissueColumn(ViewParams cp) {
    super(
      100,
      "library.ss.result.header.asmtissue.label",
      "library.ss.result.header.asmtissue.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER,
      ColumnPermissions.SCRN_ICP,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    return ssh.getAsmTissue();
  }
    
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer(64);
    result.append("<td>");
    Escaper.htmlEscape(getRawBodyText(rp), result);
    result.append("</td>");
    return result.toString();
  }

}
