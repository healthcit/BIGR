package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;

/**
 */
public class SampleBoxLocationColumn extends SampleColumnImpl {

  private SecurityInfo _secInfo = null;

  public SampleBoxLocationColumn(ViewParams cp) {
    super(
      120,
      "library.ss.result.sampleBoxLocation.label",
      "library.ss.result.sampleBoxLocation.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_DI,
      ColumnPermissions.SCRN_CONFIRM_REQ
        | ColumnPermissions.SCRN_HOLD_LIST
        | ColumnPermissions.SCRN_SELECTION | ColumnPermissions.SCRN_ICP | ColumnPermissions.SCRN_DERIV_OPS_SUMMARY,
      cp);
    setBodyColAlign("left");

    _secInfo = cp == null ? null : cp.getSecurityInfo();
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
  
    StringBuffer result = new StringBuffer(16);
    SampleSelectionHelper helper = rp.getItemHelper();
    if ((helper != null)) {
     
        result.append(helper.getSampleBoxLocation());
      
    }
    return result.toString();    
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
   
    StringBuffer result = new StringBuffer(16);
    result.append("<td>");
    result.append(getRawBodyText(rp));
    result.append("</td>");
    return result.toString();
  }

}
