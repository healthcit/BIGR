package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;

/**
 */
public class TissueColumn extends SampleColumnImpl {

  public TissueColumn(ViewParams cp) {
    super(
      180,
      "library.ss.result.header.tissue.label",
      "library.ss.result.header.tissue.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_CUST | ColumnPermissions.ROLE_DI,
      ColumnPermissions.ALL,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(128);
    if (ssh.isVerified()) {
      String tissueOfOrigin = ssh.getTissueOfOrigin();
      String tissueOfFinding = ssh.getTissueOfFinding();
      if (!ApiFunctions.isEmpty(tissueOfOrigin) || !ApiFunctions.isEmpty(tissueOfFinding)) {
        result.append(tissueOfOrigin);
        result.append('/');
        result.append(tissueOfFinding);
      }
    }
    else {
      String sampleTissueOfOrigin = ssh.getSampleTissueOfOrigin();
      String asmTissue = ssh.getAsmTissue();
      if (!ApiFunctions.isEmpty(sampleTissueOfOrigin) || !ApiFunctions.isEmpty(asmTissue)) {
        result.append(sampleTissueOfOrigin);
        result.append('/');
        result.append(asmTissue);
      }
    }
    return result.toString();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    StringBuffer result = new StringBuffer(128);
    result.append("<td>");
    if (ssh.isVerified()) {
      String tissueOfOrigin = ssh.getTissueOfOrigin();
      String tissueOfFinding = ssh.getTissueOfFinding();
      if (!ApiFunctions.isEmpty(tissueOfOrigin) || !ApiFunctions.isEmpty(tissueOfFinding)) {
        Escaper.htmlEscape(tissueOfOrigin, result);
        result.append('/');
        Escaper.htmlEscape(tissueOfFinding, result);
      }
    }
    else {
      String sampleTissueOfOrigin = ssh.getSampleTissueOfOrigin();
      String asmTissue = ssh.getAsmTissue();
      if (!ApiFunctions.isEmpty(sampleTissueOfOrigin) || !ApiFunctions.isEmpty(asmTissue)) {
        Escaper.htmlEscape(sampleTissueOfOrigin, result);
        result.append('/');
        Escaper.htmlEscape(asmTissue, result);
      }
    }
    result.append("</td>");
    return result.toString();
  }
}
