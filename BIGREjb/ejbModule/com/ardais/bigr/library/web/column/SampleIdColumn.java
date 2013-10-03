package com.ardais.bigr.library.web.column;

import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

import java.io.IOException;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SampleIdColumn extends SampleColumnImpl {

  private SecurityInfo _secInfo;

  /**
   * Constructor for SampleIdColumn.
   * @param width
   * @param headerText
   * @param headerTooltip
   * @param bodyText
   * @param flags
   */
  public SampleIdColumn(ViewParams cp) {
    super(
      81,
      "library.ss.sampleid.label",
      "library.ss.sampleid.comment",
      ColumnPermissions.ROLE_ALL,
      ColumnPermissions.ALL,
      cp);
    _secInfo = cp == null ? null : cp.getSecurityInfo();
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getSampleId();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumn#writeBodyText(PrintWriter, SampleSelectionHelper)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer(128);
    
    result.append("<td>");
	  if (_secInfo.isHasPrivilege(SecurityInfo.PRIV_ORM_ACCESS_SAMPLE_VIEW))
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
		return ColumnMetaData.KEY_SAMPLE_ID;
	}
}