package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;

/**
 * Sample registration attribute "Source".
 */
public class SampleSourceColumn extends SampleColumnImpl {

  public SampleSourceColumn(ViewParams cp) {
    super(
      90,
      "library.ss.result.header.sampleSource.label",
      "library.ss.result.header.sampleSource.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_DI | ColumnPermissions.ROLE_CUST,
      ColumnPermissions.SCRN_CONFIRM_REQ | ColumnPermissions.SCRN_HOLD_LIST | ColumnPermissions.SCRN_SELECTION | ColumnPermissions.SCRN_ICP | ColumnPermissions.SCRN_DERIV_OPS_SUMMARY | ColumnPermissions.SCRN_ORDER_DET,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getSampleSource();
  }
 
   /* (non-Javadoc)
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(com.ardais.bigr.library.web.column.SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer(128);
    result.append("<td>");
    result.append(getRawBodyText(rp));
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
		return ColumnMetaData.KEY_SAMPLE_SOURCE;
	}
}
