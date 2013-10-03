package com.ardais.bigr.library.web.column;

import java.io.IOException;
import java.util.Properties;

import com.ardais.bigr.aperio.AperioImageService;
import com.ardais.bigr.library.SampleViewData;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.web.StrutsProperties;

/**
 * @author dfeldman
 *
 * This is the superclass for the "Select" row, which has a checkbox for the user to select
 * the item, a red R to indicate it is restricted, and in other contexts, a drop-down of 
 * deliverable formats.
 * 
 * This column changes it appearance a lot depending on context.  The checkbox is used in the 
 * main results view and hold lists.  The deliverable drop down is used on the RequestConfirm
 * screen.
 * 
 * There is Tissue and RNA-specific processing depending on the row
 * that does a couple sub-behaviors differently, for tissue vs. RNA, such 
 * as create the PV Report link in the appropriate manner.
 */
public class SampleSelectIsPvIsRestrictedIsBmsIdColumn extends SampleColumnImpl {

  /**
   * This is the suffix that is appended to the resource keys for the column header
   * and tooltip when the column doesn't support selection.
   * 
   * @see #setColumnSupportsSelection(boolean)
   */
  private static final String NO_SELECTION_SUPPORT_SUFFIX = ".noselect";

  private String _headerTextResourceKey = null;
  private String _headerTooltipResourceKey = null;

  public SampleSelectIsPvIsRestrictedIsBmsIdColumn(ViewParams cp) {
    // The second and thirds args below are just bogus strings.  The actual header
    // text and tooltip for the column are set in special methods for this column
    // (initHeaders and setColumnSupportsSelection).
    super(
      65,
      "select box",
      "select box tooltip",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_DI | ColumnPermissions.ROLE_CUST,
      ColumnPermissions.SCRN_SELECTION | ColumnPermissions.SCRN_ICP | ColumnPermissions.SCRN_DERIV_OPS_SUMMARY | ColumnPermissions.SCRN_HOLD_LIST,
      ColumnPermissions.TX_SAMP_SELECTION | ColumnPermissions.TX_TYPE_ICP | ColumnPermissions.TX_TYPE_DERIV_OPS | ColumnPermissions.TX_REQUEST_SAMP,
      cp);

    initHeaders(cp);
    
    //if we're showing this column for the ICP screen, disable the selection capability
    if (cp.isScreen(ColumnPermissions.SCRN_ICP)) {
      setColumnSupportsSelection(false);
    }
  }

  private void initHeaders(ViewParams cp) {
    boolean useRnaHeader = (cp != null && !cp.isTissueTab());
    // rna header is more generic, use except for tissue
    boolean isHoldList = (cp != null && cp.isScreen(ColumnPermissions.SCRN_HOLD_LIST));
    boolean isDerivativeOps = (cp != null && cp.isScreen(ColumnPermissions.SCRN_DERIV_OPS_SUMMARY));
    System.out.println("I am in select ="+cp.getKey());
    if (isHoldList) {
      setHeaderTooltipResourceKey("library.ss.result.header.remove.comment");
      setHeaderTextResourceKey("library.ss.result.header.remove.label");
    }
    else if (isDerivativeOps) {
      setHeaderTooltipResourceKey("library.ss.result.header.select.comment.derivativeOps");
      setHeaderTextResourceKey("library.ss.result.header.select.label");      
    }
    else {
      String tooltipKey =
        (useRnaHeader
          ? "library.ss.result.header.select.comment.rna"
          : "library.ss.result.header.select.comment");
      setHeaderTooltipResourceKey(tooltipKey);
      setHeaderTextResourceKey("library.ss.result.header.select.label");
    }

    setHeaderAndTooltip();
  }

  private void setHeaderAndTooltip() {
    // This assumes that the headerResourceKey and headerTooltipResourceKey properties have
    // already been set.

    Properties props = StrutsProperties.getInstance();
    setHeaderTooltip(props.getProperty(getHeaderTooltipResourceKey()));
    setHeaderText(props.getProperty(getHeaderTextResourceKey()));
  }

  /**
   * By default, this column assumes that it supports selection and the the column header
   * and tooltip should reflect that.  Set this to false if the column does not support
   * selection.  This will alter the column header and tooltip appropriately.
   * 
   * @param supportsSelection The new selection-support indicator.
   */
  public void setColumnSupportsSelection(boolean supportsSelection) {
    // There are two sets of resource keys for this column's header text and tooltip text.
    // One set is for when the column doesn't support selection, and the other is for when
    // it does.  The keys in the no-selection set are equal to the keys in the
    // supports-selection set with NO_SELECTION_SUPPORT_SUFFIX appended to the end.
    
    if (supportsSelection) {
      // Replace the header and tooltip keys with their "supports selection" versions if they
      // aren't already the supports-selection versions.

      String tipKey = getHeaderTooltipResourceKey();
      if (tipKey.endsWith(NO_SELECTION_SUPPORT_SUFFIX)) {
        setHeaderTooltipResourceKey(
          tipKey.substring(0, tipKey.length() - NO_SELECTION_SUPPORT_SUFFIX.length()));
      }
      String textKey = getHeaderTextResourceKey();
      if (textKey.endsWith(NO_SELECTION_SUPPORT_SUFFIX)) {
        setHeaderTextResourceKey(
          textKey.substring(0, textKey.length() - NO_SELECTION_SUPPORT_SUFFIX.length()));
      }
    }
    else {
      // Replace the header and tooltip keys with their "no selection" versions if they aren't
      // already the no-selection versions.

      String tipKey = getHeaderTooltipResourceKey();
      if (!tipKey.endsWith(NO_SELECTION_SUPPORT_SUFFIX)) {
        setHeaderTooltipResourceKey(tipKey + NO_SELECTION_SUPPORT_SUFFIX);
      }
      String textKey = getHeaderTextResourceKey();
      if (!textKey.endsWith(NO_SELECTION_SUPPORT_SUFFIX)) {
        setHeaderTextResourceKey(textKey + NO_SELECTION_SUPPORT_SUFFIX);
      }
    }

    setHeaderAndTooltip();
  }

  /**
   * @return
   */
  public String getHeaderTextResourceKey() {
    return _headerTextResourceKey;
  }

  /**
   * @return
   */
  public String getHeaderTooltipResourceKey() {
    return _headerTooltipResourceKey;
  }

  /**
   * @param string
   */
  public void setHeaderTextResourceKey(String string) {
    _headerTextResourceKey = string;
  }

  /**
   * @param string
   */
  public void setHeaderTooltipResourceKey(String string) {
    _headerTooltipResourceKey = string;
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    //nothing to return since getBodyText is all html
    return "";
  }

  protected String getBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer(128);
    SampleViewData viewData = rp.getItemViewData();
    SampleSelectionHelper ssh = rp.getItemHelper();

    result.append("<td>");

    if (viewData.isIncludeItemSelector()) {
      // id of sample, regardless of if it is an RNA sample or Tissue sample
      String itemId = ssh.getId();

      result.append("<input type=\"checkbox\" name=\"");
      result.append(viewData.getItemSelectorName());
      result.append('"');
      if (ssh.isSelectionDisabled()) {
        result.append(" disabled");
      }
      if (ssh.isSelected()) {
        result.append(" checked");
      }
      result.append(" id=\"");
      result.append(ssh.getSelectorId());
      result.append("\" value=\"");
      result.append(itemId);
      result.append("\" stype=\"");
      result.append(ssh.getSampleType());
      result.append("\">");
    }

    appendPVLinkString(rp, result);
	appendAperioLinkString(rp, ssh, result);

    result.append("</td>");
    return result.toString();
  }

  // ============  building the Path Verification (PV) Link ============================
  // ============  ============  ============  ============  ============  ============  

  // the link to the PV screen differs from Tissue to RNA.  Build the right one
  private void appendPVLinkString(SampleRowParams rp, StringBuffer sb) throws IOException {
    if (rp.getItemHelper().isRna())
      appendRnaPVLinkString(rp, sb);
    else
      appendTissuePVLinkString(rp, sb);
  }

	private void appendAperioLinkString(SampleRowParams rp, SampleSelectionHelper helper, StringBuffer sb)
	{
		if (AperioImageService.SINGLETON.hasImage(helper.getSampleId()))
		{
			sb.append("<img")
				.append(" src='/BIGR/images/looking_glass.gif'")
				.append(" height='15px'")
				.append(" width='15px'")
				.append(" title='Aperio'")
				.append(" style='cursor:pointer;margin-left:3px;'")
				.append(String.format(" onclick=\"prepareRowData(%s);openUrl('javascript:openAperio(\\'$$sample_id$$\\');','Aperio');\"", rp.getItemIndexInView()))
				.append(" />");
		}
	}

  private void appendTissuePVLinkString(SampleRowParams rp, StringBuffer sb) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    boolean isPved = ssh.isVerified();
    String link = null;
    if (isPved) {
      link =
        ColumnHelper.generatePvReportHtml(
          null,
          null,
          ssh.getSampleId(),
          null,
          "/images/microscope.gif",
          null,
          null);
    }
    else if (ssh.isPulled()) {
      link =
        ColumnHelper.generatePullInfoHtml(
          ssh.getSampleId(),
          ssh.getPullDetails(),
          "/images/pulled.gif",
          null);
    }
    else {
      link = "<img src=\"/BIGR/images/px1.gif\" width=\"14\" border=\"0\"/>";
    }
    sb.append(link);
  }

  private void appendRnaPVLinkString(SampleRowParams rp, StringBuffer sb) {
    sb.append("<span class=\"fakeLink\" onclick=\"openRnaInfo('");
    sb.append(rp.getItemHelper().getRnaVialId());
    sb.append("');\"><img src=\"/BIGR/images/microscope.gif\" width=\"14\"/></span>");
  }
  // ============  ============  ============  ============  ============  ============  

}
