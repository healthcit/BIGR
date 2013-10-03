package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;

/**
 */
public class DateOfCollectionColumn extends SampleColumnImpl {

  public DateOfCollectionColumn(ViewParams cp) {
    super(
      115,
      "library.ss.result.header.dateOfCollection.label",
      "library.ss.result.header.dateOfCollection.comment",
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
    return rp.getItemHelper().getDateOfCollectionWithPrecision();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer(128);
    result.append("<td>");
    Escaper.htmlEscape(getRawBodyText(rp), result);
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
		return ColumnMetaData.KEY_SAMPLE_COLLECTION_DATETIME;
	}
}
