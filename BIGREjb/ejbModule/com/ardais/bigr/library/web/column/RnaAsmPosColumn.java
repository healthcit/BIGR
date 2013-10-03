package com.ardais.bigr.library.web.column;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;

/**
 */
public class RnaAsmPosColumn extends SampleColumnImpl {

  public RnaAsmPosColumn(ViewParams cp) {
    super(
      30,
      "library.ss.result.header.asmId.label",
      "library.ss.result.header.asmId.rna.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER,
      ColumnPermissions.ALL,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(128);
    List rnaPoolSamples = ssh.getRnaDataObject().getSamples();

    if (rnaPoolSamples.size() > 1) {
      StringBuffer asmPosition = new StringBuffer(64);
      Iterator poolIter = rnaPoolSamples.iterator();
      while (poolIter.hasNext()) {
        SampleData sampleData = (SampleData) poolIter.next();
        asmPosition.append(ApiFunctions.safeString(sampleData.getAsmPosition()));
        asmPosition.append(' ');
      }
      result.append(asmPosition.toString());
    }
    else {
      result.append(ssh.getAsmPosition());
    }
    return result.toString();
  }

  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(128);
    List rnaPoolSamples = ssh.getRnaDataObject().getSamples();

    if (rnaPoolSamples.size() > 1) {
      StringBuffer asmPosition = new StringBuffer(64);
      Iterator poolIter = rnaPoolSamples.iterator();
      while (poolIter.hasNext()) {
        SampleData sampleData = (SampleData) poolIter.next();
        asmPosition.append(ApiFunctions.safeString(sampleData.getAsmPosition()));
        asmPosition.append(' ');
      }
      result.append("<td title=\"");
      result.append(asmPosition.toString());
      result.append("\">");
      result.append("...");
      result.append("</td>");
    }
    else {
      result.append("<td>");
      result.append(ssh.getAsmPosition());
      result.append("</td>");
    }
    return result.toString();
  }

}
