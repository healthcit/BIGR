package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;

/**
 */
public class CompositionVerticalColumn extends SampleColumnImpl {

  public CompositionVerticalColumn(ViewParams cp) {
    super(
      60,
      "need to add label",
      "add label",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_CUST | ColumnPermissions.ROLE_DI,
      ColumnPermissions.SCRN_SAMP_AMOUNTS, // amounts only
      cp);
    setBodyColAlign("center");
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    //this is a best guess representation of raw data to return here, since
    //the getBodyText output is heavily dependent on html
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(128);

    result.append("NRM: ");
    result.append(ssh.getNormal());
    result.append(" ");

    result.append("LSN: ");
    result.append(ssh.getLesion());
    result.append(" ");
    
    result.append("TMR: ");
    result.append(ssh.getTumor());
    result.append(" ");
    
    result.append("TCS: ");
    result.append(ssh.getCellularStroma());
    result.append(" ");
    
    result.append("ACS: ");
    result.append(ssh.getAcellularStroma());
    result.append(" ");
    
    result.append("NEC: ");
    result.append(ssh.getNecrosis());
    result.append(" ");
    
    return result.toString();
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(128);

    result.append("<td>");
    result.append("<table border=\"1\" cellspacing=\"0\" style=\"FONT-SIZE: 8px;\">");
    
    result.append("<tr><td>NRM</td><td>");
    result.append(ssh.getNormal());
    result.append("</td></tr>");

    result.append("<tr><td>LSN</td><td>");
    result.append(ssh.getLesion());
    result.append("</td></tr>");
    
    result.append("<tr><td>TMR</td><td>");
    result.append(ssh.getTumor());
    result.append("</td></tr>");
    
    result.append("<tr><td>TCS</td><td>");
    result.append(ssh.getCellularStroma());
    result.append("</td></tr>");
    
    result.append("<tr><td>ACS</td><td>");
    result.append(ssh.getAcellularStroma());
    result.append("</td></tr>");
    
    result.append("<tr><td>NEC</td><td>");
    result.append(ssh.getNecrosis());
    result.append("</td></tr>");
    
    result.append("</table></td>");
    
    return result.toString();
  }

}
