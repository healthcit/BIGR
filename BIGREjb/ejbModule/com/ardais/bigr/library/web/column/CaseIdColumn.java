package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 */
public class CaseIdColumn extends SampleColumnImpl {

  private SecurityInfo _secInfo;

  public CaseIdColumn(ViewParams cp) {
    super(
      90,
      "library.ss.result.header.caseId.label",
      "library.ss.result.header.caseId.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_CUST | ColumnPermissions.ROLE_DI,
      ColumnPermissions.ALL,
      cp);
    _secInfo = cp == null ? null : cp.getSecurityInfo();
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getConsentId();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer(64);
    
    result.append("<td>");
	  if (_secInfo.isHasPrivilege(SecurityInfo.PRIV_ORM_ACCESS_CASE_VIEW))
	  {
		  result.append(IcpUtils.preparePopupLink(getRawBodyText(rp), _secInfo));
	  }
	  else
	  {
		  result.append(getRawBodyText(rp));
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
		return ColumnMetaData.KEY_SAMPLE_CONSENT_ID;
	}
}
