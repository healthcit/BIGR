package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;

/**
 */
public class DonorCommentsColumn extends SampleColumnImpl {

  public DonorCommentsColumn(ViewParams cp) {
    super(
      200,
      "library.ss.result.header.donorComments.label",
      "library.ss.result.header.donorComments.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_DI,
      ColumnPermissions.SCRN_SELECTION
        | ColumnPermissions.SCRN_HOLD_LIST
        | ColumnPermissions.SCRN_CONFIRM_REQ
        | ColumnPermissions.SCRN_ORDER_DET
        | ColumnPermissions.SCRN_ICP
        | ColumnPermissions.SCRN_DERIV_OPS_SUMMARY,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getDonorComments();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(128);
    String commentsLong = ssh.getDonorComments();
    String commentsShort = ssh.getDonorCommentsShort();
    result.append("<td>");
    if (commentsLong.length() > commentsShort.length()) {
      result.append("<span onmouseout=\"return nd();\" onmouseover=\"return overlib('");
      Escaper.jsEscapeInXMLAttr(Escaper.htmlEscapeAndPreserveWhitespace(commentsLong), result);
      result.append("');\">");
    }
    Escaper.htmlEscape(commentsShort, result);
    if (commentsLong.length() > commentsShort.length()) {
      result.append("</span>");
    }
    result.append("</td>");
    return result.toString();
  }

	@Override
	public boolean sortable()
	{
		return true;
	}

	@Override
	public String getColumnMetadataKey()
	{
		return ColumnMetaData.KEY_SAMPLE_ASM_NOTE;
	}
}
