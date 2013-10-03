package com.ardais.bigr.library.web.column;

import java.io.IOException;
import java.util.Properties;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.web.StrutsProperties;

public class DiagnosticResultColumn extends SampleColumnImpl {

  private SecurityInfo _securityInfo;

  public DiagnosticResultColumn(ViewParams vp) {
    super(
      120,
      "library.ss.diagnosticresult.label",
      "library.ss.diagnosticresult.result.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_CUST | ColumnPermissions.ROLE_DI,
      ColumnPermissions.SCRN_SELECTION | ColumnPermissions.SCRN_ICP | ColumnPermissions.SCRN_DERIV_OPS_SUMMARY,
      ColumnPermissions.TX_SAMP_SELECTION | ColumnPermissions.TX_TYPE_ICP | ColumnPermissions.TX_TYPE_DERIV_OPS | ColumnPermissions.TX_REQUEST_SAMP,
      vp);

    _securityInfo = (vp == null) ? null : vp.getSecurityInfo();
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    if (!isShown())
      return "";
        
    //this is a best guess representation of raw data to return here, since
    //the getBodyText output is heavily dependent on html
    Properties prop = StrutsProperties.getInstance();

    SampleSelectionHelper helper =  rp.getItemHelper();  
    String diags = helper.getDiagnosticResult();
    String psa = null;
    String dre = null;
    
    if ((_securityInfo != null) && (_securityInfo.isHasPrivilege(SecurityInfo.PRIV_PSA_DRE))) {
      psa = helper.getPsa();
      dre = helper.getDre();
    }
    
    StringBuffer resultText = new StringBuffer();

    resultText.append(diags);

    if (!ApiFunctions.isEmpty(psa)) {
      if (resultText.length() > 0) {
        resultText.append(" ");        
      }
      resultText.append(prop.getProperty("library.ss.query.tests.psa"));
      resultText.append(": ");
      resultText.append(psa);
    }

    if (!ApiFunctions.isEmpty(dre)) {
      if (resultText.length() > 0) {
        resultText.append(" ");        
      }
      resultText.append(prop.getProperty("library.ss.query.tests.dre"));
      resultText.append(": ");
      resultText.append(dre);
    }

    return resultText.toString();
    
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    if (!isShown())
      return "";
        
    Properties prop = StrutsProperties.getInstance();

    SampleSelectionHelper helper =  rp.getItemHelper();  
    String diags = helper.getDiagnosticResult();
    String psa = null;
    String dre = null;
    
    if ((_securityInfo != null) && (_securityInfo.isHasPrivilege(SecurityInfo.PRIV_PSA_DRE))) {
      psa = helper.getPsa();
      dre = helper.getDre();
    }
    
    StringBuffer resultText = new StringBuffer();

    resultText.append(diags);

    if (!ApiFunctions.isEmpty(psa)) {
      if (resultText.length() > 0) {
        resultText.append("<br>");        
      }
      resultText.append(prop.getProperty("library.ss.query.tests.psa"));
      resultText.append(": ");
      resultText.append(psa);
    }

    if (!ApiFunctions.isEmpty(dre)) {
      if (resultText.length() > 0) {
        resultText.append("<br>");        
      }
      resultText.append(prop.getProperty("library.ss.query.tests.dre"));
      resultText.append(": ");
      resultText.append(dre);
    }

    
    StringBuffer result = new StringBuffer(128);
    if (resultText.length() > 25) {
      result.append("<td onmouseout=\"return nd();\" onmouseover=\"return overlib('");
      Escaper.jsEscapeInXMLAttr(resultText.toString(), result);
      result.append("');\">");
      result.append((Escaper.htmlEscape(resultText.substring(0, 24))));
      result.append("...");
    }
    else {
      result.append("<td>");
      result.append(Escaper.htmlEscape(resultText.toString()));
    }
    result.append("</td>");

    return result.toString();
  }

}
