package com.ardais.bigr.library.web.column;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * @author sislam
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FormatDetailColumn extends SampleColumnImpl {

  /**
   * Constructor for FormatDetailColumn.
   * @param width
   * @param headerText
   * @param headerTooltip
   * @param permissions
   * @param transactions
   * @param cp
   */
  public FormatDetailColumn(ViewParams cp) {
    super(
      95,
      "library.ss.result.header.formatDetail.label",
      "library.ss.result.header.formatDetail.comment",
      ColumnPermissions.ROLE_ALL,
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
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(128);
    Map formatMap = new HashMap();
    int formatCount = 0;

    if (ssh.isRna()) {
      List rnaPoolSamples = rp.getItemHelper().getRnaDataObject().getSamples();
      if (rnaPoolSamples.size() > 1) {
        String formatDetail = "";
        Iterator poolIter = rnaPoolSamples.iterator();
        while (poolIter.hasNext()) {
          SampleData sampleData = (SampleData) poolIter.next();
          formatDetail = sampleData.getFormatDetail();
          formatDetail =
            (formatDetail == null) ? "" : GbossFactory.getInstance().getDescription(formatDetail);
          if (!formatMap.containsKey(formatDetail)) {
            formatMap.put(formatDetail, formatDetail);
          }
        }

        Iterator it = formatMap.values().iterator();
        formatDetail = "";
        while (it.hasNext()) {
          formatDetail += it.next() + " ";
          formatCount += 1;
        }

        result.append(formatDetail);
      }
      else {
        result.append(ssh.getSampleFormatDetail());
      }
    }
    else {
      result.append(ssh.getSampleFormatDetail());
    }
    return result.toString();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(128);
    Map formatMap = new HashMap();
    int formatCount = 0;

    if (ssh.isRna()) {
      List rnaPoolSamples = rp.getItemHelper().getRnaDataObject().getSamples();
      if (rnaPoolSamples.size() > 1) {
        String formatDetail = "";
        Iterator poolIter = rnaPoolSamples.iterator();
        while (poolIter.hasNext()) {
          SampleData sampleData = (SampleData) poolIter.next();
          formatDetail = sampleData.getFormatDetail();
          formatDetail =
            (formatDetail == null) ? "" : GbossFactory.getInstance().getDescription(formatDetail);
          if (!formatMap.containsKey(formatDetail)) {
            formatMap.put(formatDetail, formatDetail);
          }
        }

        Iterator it = formatMap.values().iterator();
        formatDetail = "";
        while (it.hasNext()) {
          formatDetail += it.next() + " ";
          formatCount += 1;
        }

        if (formatCount > 1) {
          result.append("<td title=\"" + formatDetail + "\">");
          result.append("...");
        }
        else {
          result.append("<td>");
          result.append(formatDetail);
          result.append("</td>");
        }
      }
      else {
        result.append("<td>");
        result.append(ssh.getSampleFormatDetail());
        result.append("</td>");
      }
    }
    else {
      result.append("<td>");
      result.append(ssh.getSampleFormatDetail());
      result.append("</td>");
    }
    return result.toString();
  }
}
