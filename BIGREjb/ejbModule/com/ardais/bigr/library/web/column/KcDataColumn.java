package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnConstants;
import com.ardais.bigr.query.ColumnDescriptor;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.gulfstreambio.gboss.GbossFactory;

/**
 */
public class KcDataColumn extends SampleColumnImpl {

  private ColumnDescriptor _columnDescriptor;
  
  public KcDataColumn(ColumnDescriptor columnDescriptor, ViewParams cp) {
    super(
      columnDescriptor.getWidth(),
      null,
      null,
      columnDescriptor.getColumnDescription(),
      columnDescriptor.getColumnDescription() + " (source: " + columnDescriptor.getDomainObjectType().toLowerCase() + ")",
      ColumnPermissions.ALL,
      ColumnPermissions.ALL,
      ColumnPermissions.ALL,
      cp);
    //include the units (if any) in the column description
    if (!ApiFunctions.isEmpty(columnDescriptor.getUnitCui())) {
      String unitDescription = GbossFactory.getInstance().getDescription(columnDescriptor.getUnitCui());
      setHeaderText(getHeaderText() + " (" + unitDescription + ")");
    }
    _columnDescriptor = columnDescriptor;
  }  
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getKcDataValue(_columnDescriptor);
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(128);
    
    if (ssh.isKcDataTooLong(_columnDescriptor)) {
      result.append("<td onmouseout=\"return nd();\" onmouseover=\"return overlib('");
      Escaper.jsEscapeInXMLAttr(Escaper.htmlEscape(ssh.getKcDataValue(_columnDescriptor)), result);
      result.append("');\">");
    }
    else {
      result.append("<td>");
    }
    Escaper.htmlEscape(ssh.getKcDataShort(_columnDescriptor), result);
    result.append("</td>");
    return result.toString();
  
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumn#getKey()
   */
  public String getKey() {
    return ColumnConstants._KC_DATA;
  }
}
