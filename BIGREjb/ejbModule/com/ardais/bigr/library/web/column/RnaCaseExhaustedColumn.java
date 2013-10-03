package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.query.*;

/**
 * @author sislam
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RnaCaseExhaustedColumn extends SampleColumnImpl {

  public RnaCaseExhaustedColumn(ViewParams cp) {
    super(
      10,
      "library.ss.result.header.rnaCaseExhausted.label",
      "library.ss.result.header.rnaCaseExhausted.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER,
      ColumnPermissions.SCRN_SELECTION | ColumnPermissions.SCRN_ICP,
      cp);
    setBodyColAlign("center");
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getRnaCaseExhausted();
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
