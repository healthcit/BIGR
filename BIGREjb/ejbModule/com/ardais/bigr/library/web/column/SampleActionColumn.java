package com.ardais.bigr.library.web.column;

import com.ardais.bigr.aperio.AperioImageService;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Column that shows the action menu for each sample.
 */
public class SampleActionColumn extends SampleColumnImpl {

  public SampleActionColumn(ViewParams cp) {
    super(
        50,
        "library.ss.result.header.sampleAction.label",
        "library.ss.result.header.sampleAction.comment",
        ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_CUST | ColumnPermissions.ROLE_DI,
        ColumnPermissions.ALL,
        cp);
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(com.ardais.bigr.library.web.column.SampleRowParams)
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
    result.append("<td>");
    result.append("<script type=\"text/javascript\">");
    result.append("_sampleActionParams[");
    result.append(rp.getItemIndexInView());
    result.append("] = ");    
    try {
      JSONObject sampleParams = new JSONObject();
      sampleParams.putOpt("sampleId", sampleId);
      sampleParams.putOpt("sampleAlias", Escaper.urlEncode(sampleAlias));
      sampleParams.putOpt("consentId", caseId);
      sampleParams.putOpt("caseAlias", Escaper.urlEncode(caseAlias));
      sampleParams.putOpt("donorId", donorId);
      sampleParams.putOpt("donorAlias", Escaper.urlEncode(donorAlias));
      sampleParams.putOpt("pathId", pathReportId);
	  sampleParams.putOpt("sampleHasAperioImage", AperioImageService.SINGLETON.hasImage(sampleId));
      result.append(sampleParams.toString());
    }
    catch (JSONException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    result.append(";</script>");
    result.append("<span class=\"sampleActionMenuLink\" onclick=\"displaySampleActionMenu(");
    result.append(rp.getItemIndexInView());
    result.append(");\">Action");
    result.append("<img src=/BIGR/images/rarrow.gif");
    result.append(">");
    result.append("</span></td>");
    return result.toString();
  }
  
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    // Since this column is not displaying any data there is no raw text so we'll return 
    // an empty string.
    return "";
  }
}
