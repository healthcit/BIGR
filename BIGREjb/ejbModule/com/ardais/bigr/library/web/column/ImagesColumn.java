package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;

/**
 */
public class ImagesColumn extends SampleColumnImpl {

  /**
   * Constructor with explicit values for header information.  Use by subclasses only.
   */
  /*package private*/ ImagesColumn(
        String overrideHeaderKey, String overrideHeaderTooltipKey, 
        int overrideWidth, int overrideRoles, int overrideScreens,
        ViewParams cp) {
    super(
      overrideWidth,
      overrideHeaderKey,
      overrideHeaderTooltipKey,
      overrideRoles,
      overrideScreens,
      cp);
      
      setBodyColAlign("center");
  }

  /**
   * Constructor with explicit values for header information.  Use by subclasses only.
   */
  /*package private*/ ImagesColumn(
        String overrideHeaderKey, String overrideHeaderTooltipKey, 
        int overrideWidth,
        ViewParams cp) {
    this(
      overrideHeaderKey,
      overrideHeaderTooltipKey,
      overrideWidth,
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_CUST | ColumnPermissions.ROLE_DI,
      ColumnPermissions.ALL,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    //nothing to return here, as the getBodyText method returns just html
    return "";
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    if (rp.getItemHelper().isRna())
      return getRnaBodyText(rp);
    else
      return getTissueBodyText(rp);
  }

  /**
   * Get the link for Tissue samples
   */
  private String getTissueBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    String sampleId = ssh.getSampleId();
    StringBuffer result = new StringBuffer(128);
    result.append("<td>");
    if (ssh.isVerified()) {
      result.append(
          ColumnHelper.generatePvReportHtml(
          null,
          null,
          sampleId,
          null,
          "/images/microscope.gif",
          null,
          null));
    }
    else if (ssh.isPulled()) {
      result.append(
          ColumnHelper.generatePullInfoHtml(
          sampleId,
          ssh.getPullDetails(),
          "/images/pulled.gif",
          null));
    }
    else {
      result.append("&nbsp;");
    }
    result.append("</td>");
    return result.toString();
  }

  /**
   * Get the link for RNA Samples
   */
  protected String getRnaBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer(128);
    result.append(
      "<td><span class=\"fakeLink\" onclick=\"openRnaInfo('" + rp.getItemHelper().getRnaVialId() + "');\">");
    result.append(
      "<img src=\"/BIGR/images/microscope.gif\" height=15 width=15 border=0></span></td>");
    return result.toString();
  }


  /**
   * @see com.ardais.bigr.library.web.column.SampleColumn#getColumnDescription()
   */
  public String getColumnDescription() {
    return "View Images";
  }


}
