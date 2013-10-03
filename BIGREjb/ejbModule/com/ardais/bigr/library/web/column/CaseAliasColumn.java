package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 */
public class CaseAliasColumn extends SampleColumnImpl {

  private SecurityInfo _secInfo;

  public CaseAliasColumn(ViewParams cp) {
    super(
      90,
      "library.ss.result.header.caseAlias.label",
      "library.ss.result.header.caseAlias.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_DI,
      ColumnPermissions.ALL,
      cp);
    _secInfo = cp == null ? null : cp.getSecurityInfo();
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getConsentCustomerId();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    String caseId = ssh.getConsentId();
    String donorId = ssh.getDonorId();
    String pathReportId = ssh.getPathReportId();
    String sampleId = ssh.getSampleId();
    String caseAlias = ssh.getConsentCustomerId();
    String donorAlias = ssh.getDonorCustomerId();
    String sampleAlias = ssh.getSampleAlias();

    StringBuffer result = new StringBuffer(128);

    if (_secInfo != null && (_secInfo.isInRoleSystemOwner() || (_secInfo.isInRoleDi() && ssh.isBms()))) {
//      result.append("<td class=\"fakeLink\"><span caseMenu=\"caseMenuPopup\" sampleId=\"");
//      result.append(sampleId);
//      result.append("\" sampleAlias=\"");
//      result.append(Escaper.urlEncode(sampleAlias));
//      result.append("\" consentId=\"");
//      result.append(caseId);
//      result.append("\" consentAlias=\"");
//      result.append(Escaper.urlEncode(caseAlias));
//      result.append("\" donorId=\"");
//      result.append(donorId);
//      result.append("\" donorAlias=\"");
//      result.append(Escaper.urlEncode(donorAlias));
//      result.append("\" pathId=\"");
//      result.append(pathReportId);
//      result.append("\">");
//      result.append(Escaper.htmlEscape(getRawBodyText(rp)));
//      result.append("</span></td>");
        result.append("<td>");
		if (_secInfo.isHasPrivilege(SecurityInfo.PRIV_ORM_ACCESS_CASE_VIEW))
		{
			result.append(IcpUtils.preparePopupLink(caseId, caseAlias, _secInfo));
		}
		else
		{
			result.append(caseAlias);
		}
        result.append("</td>");
    }
    else {
      result.append("<td></td>");
    }

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
		return ColumnMetaData.KEY_CONSENT_CUSTOMER_ID;
	}
}
