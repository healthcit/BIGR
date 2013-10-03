package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RnaAmountInputColumn extends SampleColumnImpl {

  private boolean _isDi; // flag used to suppress 100ug amount for DI users
  
  /**
   * Constructor for AmountSelectedColumn.
   * @param width
   * @param headerText
   * @param headerTooltip
   * @param permissions
   * @param transactions
   * @param cp
   */
  public RnaAmountInputColumn(ViewParams cp) {
    super(70, 
           "library.ss.result.header.rnaRequestAmount.label", 
           "library.ss.result.header.rnaRequestAmount.comment", 
           ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_CUST | ColumnPermissions.ROLE_DI, 
           ColumnPermissions.SCRN_SAMP_AMOUNTS, 
           cp);
    SecurityInfo secInfo = cp==null ? null : cp.getSecurityInfo();
    _isDi = (secInfo != null) && secInfo.isInRoleDi() ;
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    //nothing to return here since getBodyText is all html
    return "";
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumn#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(90);
    String sid = ssh.getId();
    int remaining = Integer.parseInt(ssh.getRnaAmountAvailable());

    /**  do standardization in GUI, because sometimes we need to add larger custom amount
      // round down remaining to a standard aliquot amount
      if (remaining >= 100) remaining = 100;
      else if (remaining >= 50) remaining = 50;
      else if (remaining >= 20) remaining = 20;
      else remaining = 10;
    **/
    
    result.append("<td>");
    
    // drop-down for available amounts, and add "max=" to help javascript set amounts
    result.append("<select max=\"" + remaining + "\" name=\"amount\">");
    result.append("<option selected value=\"" +Constants.ANY+ "\"> -- </option>");
    result.append("<option value=\"10\">5-10</option>");
    if (remaining >= 20)
      result.append("<option value=\"20\">20</option>");
    if (remaining >= 50) 
      result.append("<option value=\"50\">50</option>");
    if (remaining >= 100 && !_isDi) // no 100ug for DI user 
      result.append("<option value=\"100\">100</option>");
      
    result.append("</select>");
    
    // hidden input to identify rows (positionally correlates to amounts)
    result.append("<input name=\"id\" type=\"hidden\" value=\"");
    result.append(     sid);
    result.append(          "\"/>");

    result.append("</td>");
    return result.toString();

  }

}
