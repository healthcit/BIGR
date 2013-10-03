package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.query.*;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;

/**
 * Donor Institution location where sample came from.
 */
public class RegistrationSiteColumn extends SampleColumnImpl {

  public RegistrationSiteColumn(ViewParams cp) {
    super(
      75,
      "library.ss.result.header.source.label",
      "library.ss.result.header.source.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER,
      ColumnPermissions.SCRN_CONFIRM_REQ | ColumnPermissions.SCRN_HOLD_LIST | ColumnPermissions.SCRN_SELECTION | ColumnPermissions.SCRN_ICP | ColumnPermissions.SCRN_DERIV_OPS_SUMMARY,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getConsentLocation();
  }

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
		return ColumnMetaData.KEY_CONSENT_LOCATION;
	}
}
