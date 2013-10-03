package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.es.helpers.FormLogic;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;



/**
 */
public class RnaImagesPreviewColumn extends SampleColumnImpl {

  public RnaImagesPreviewColumn(ViewParams cp) {
    super(
      170,
      "library.ss.result.header.images.label",
      "library.ss.result.header.images.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_CUST | ColumnPermissions.ROLE_DI,
      ColumnPermissions.SCRN_SAMP_AMOUNTS,  // amount view only
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    //nothing to return here since getBodyText is all html
    return "";
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(128);
    
    ssh.getRnaDataObject().loadImagesFromDB(); // no info by default !
    
    result.append("<td>");
    result.append("<img src=\"");
    result.append(     ssh.getRnaDataObject().getImageUrl(FormLogic.RNA_IMAGE_PATH4X_TYPE));
    result.append(           "\" height=80 width=80 border=0/>");
    result.append("&nbsp;");
    result.append("<img src=\"");
    result.append(     ssh.getRnaDataObject().getImageUrl(FormLogic.RNA_IMAGE_PATH20X_TYPE));
    result.append(           "\" height=80 width=80 border=0/>");
    result.append("</td>");
    return result.toString();
  }

  public String getHeader() {
    if (!isShown())
      return "";
    return ("<td>&nbsp;</td>");
  }

}
