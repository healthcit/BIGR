package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.query.*;

/**
 */
public class DiagnosisLimsColumn extends SampleColumnImpl {

  public DiagnosisLimsColumn(ViewParams cp) {
    super(
      200,
      "library.ss.samplePathology.result.label",
      "library.ss.samplePathology.result.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_CUST | ColumnPermissions.ROLE_DI,
      ColumnPermissions.ALL,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    return rp.getItemHelper().getLimsDiagnosis();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer(128);
    result.append("<td>");
    Escaper.htmlEscape(getRawBodyText(rp), result);
    result.append("</td>");
    return result.toString();
  }

}
