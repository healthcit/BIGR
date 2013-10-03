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

public class FixativeTypeColumn extends SampleColumnImpl {

  /**
   * Constructor for FixativeTypeColumn.
   * @param width
   * @param headerText
   * @param headerTooltip
   * @param permissions
   * @param transactions
   * @param cp
   */
  public FixativeTypeColumn(ViewParams cp) {
    super(
      95,
      "library.ss.result.header.fixativeType.label",
      "library.ss.result.header.fixativeType.comment",
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
    Map fixativeMap = new HashMap();
    int fixativeCount = 0;

    if (ssh.isRna()) {
      List rnaPoolSamples = rp.getItemHelper().getRnaDataObject().getSamples();
      if (rnaPoolSamples.size() > 1) {
        String fixativeType = "";
        Iterator poolIter = rnaPoolSamples.iterator();
        while (poolIter.hasNext()) {
          SampleData sampleData = (SampleData) poolIter.next();
          fixativeType = sampleData.getFixativeType();
          fixativeType =
            (fixativeType == null) ? "" : GbossFactory.getInstance().getDescription(fixativeType);
          if (!fixativeMap.containsKey(fixativeType)) {
            fixativeMap.put(fixativeType, fixativeType);
          }
        }

        Iterator it = fixativeMap.values().iterator();
        fixativeType = "";
        while (it.hasNext()) {
          fixativeType += it.next() + " ";
          fixativeCount += 1;
        }

        result.append(fixativeType);
      }
      else {
        result.append(ssh.getSampleFixativeType());
      }
    }
    else {
      result.append(ssh.getSampleFixativeType());
    }
    return result.toString();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(128);
    Map fixativeMap = new HashMap();
    int fixativeCount = 0;

    if (ssh.isRna()) {
      List rnaPoolSamples = rp.getItemHelper().getRnaDataObject().getSamples();
      if (rnaPoolSamples.size() > 1) {
        String fixativeType = "";
        Iterator poolIter = rnaPoolSamples.iterator();
        while (poolIter.hasNext()) {
          SampleData sampleData = (SampleData) poolIter.next();
          fixativeType = sampleData.getFixativeType();
          fixativeType =
            (fixativeType == null) ? "" : GbossFactory.getInstance().getDescription(fixativeType);
          if (!fixativeMap.containsKey(fixativeType)) {
            fixativeMap.put(fixativeType, fixativeType);
          }
        }

        Iterator it = fixativeMap.values().iterator();
        fixativeType = "";
        while (it.hasNext()) {
          fixativeType += it.next() + " ";
          fixativeCount += 1;
        }

        if (fixativeCount > 1) {
          result.append("<td title=\"" + fixativeType + "\">");
          result.append("...");
        }
        else {
          result.append("<td>");
          result.append(fixativeType);
          result.append("</td>");
        }
      }
      else {
        result.append("<td>");
        result.append(ssh.getSampleFixativeType());
        result.append("</td>");
      }
    }
    else {
      result.append("<td>");
      result.append(ssh.getSampleFixativeType());
      result.append("</td>");
    }
    return result.toString();
  }
}
