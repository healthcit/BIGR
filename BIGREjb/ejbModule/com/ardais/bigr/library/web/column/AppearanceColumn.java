package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;

/**
 */
public class AppearanceColumn extends SampleColumnImpl {

  public AppearanceColumn(ViewParams cp) {
    super(
      80,
      "library.ss.appearance.label",
      "library.ss.appearance.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_CUST | ColumnPermissions.ROLE_DI,
      ColumnPermissions.ALL,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getAppearanceBest();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer(35);
    result.append("<td>");
    result.append(getRawBodyText(rp)); 
    result.append("</td>");
    return result.toString();
  }

  /**
   * Allow override of the width, because RNA options are all smaller text, so we can
   * have a narrower column in RNA-only views.
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#setWidth(int)
   */
  public AppearanceColumn overrideWidth(int width) {
    super.setWidth(width);
    return this;
  }

	@Override
	public boolean sortable()
	{
		return true;
	}

	@Override
	public String getColumnMetadataKey()
	{
		return ColumnMetaData.KEY_SAMPLE_APPEARANCE_BEST;
	}
}
