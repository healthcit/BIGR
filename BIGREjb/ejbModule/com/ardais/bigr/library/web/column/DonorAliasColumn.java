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
public class DonorAliasColumn extends SampleColumnImpl {

  private SecurityInfo _secInfo = null;

  public DonorAliasColumn(ViewParams cp) {
    super(
      90,
      "library.ss.result.header.donorAlias.label",
      "library.ss.result.header.donorAlias.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_DI,
      ColumnPermissions.ALL,
      cp);
    _secInfo = ((cp == null) ? null : cp.getSecurityInfo());
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getDonorCustomerId();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    String donorId = ssh.getDonorId();
    String alias = ssh.getDonorCustomerId();

    StringBuffer result = new StringBuffer(128);
    
    if (_secInfo != null && (_secInfo.isInRoleSystemOwner() || (_secInfo.isInRoleDi() && ssh.isBms()))) {
      result.append("<td>");
		if (_secInfo.isHasPrivilege(SecurityInfo.PRIV_ORM_ACCESS_DONOR_VIEW))
		{
			result.append(IcpUtils.preparePopupLink(donorId, alias, _secInfo));
		}
		else
		{
			result.append(alias);
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
		return ColumnMetaData.KEY_DONOR_CUSTOMER_ID;
	}
}
