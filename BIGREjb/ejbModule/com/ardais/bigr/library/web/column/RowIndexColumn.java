package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.query.*;

/**
 * The column that shows the row number.
 */
public class RowIndexColumn extends SampleColumnImpl {
  
  /**
   * Constructor for RowIndexColumn.
   */
  public RowIndexColumn(ViewParams cp) {
    // use undefined struts string properties to get empty header text
    super(
      35,
      "asdf no header",
      "no tooltip",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_DI | ColumnPermissions.ROLE_CUST,
      ColumnPermissions.ALL,
      cp);
    setBodyColAlign("right");
  }

  public String getHeader() {
    if (!isShown())
      return "";
    return ("<td>&nbsp;</td>");
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return Integer.toString(1 + rp.getItemIndexInOverallResultSet());
  }

  protected String getBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer(128);
    result.append("<td class=\"green\">");
    result.append(getRawBodyText(rp));
    result.append("</td>");
    return result.toString();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumn#getColumnDescription()
   */
  public String getColumnDescription() {
    return "(Row Index)";
  }

}
