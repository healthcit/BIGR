package com.ardais.bigr.dataImport.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.pdc.helpers.SelectDonorHelper;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class ClinicalDataExtractionStartAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    SelectDonorHelper helper = (SelectDonorHelper) request.getAttribute("helper");

    if (helper == null) {
      helper = new SelectDonorHelper();
      helper.setCtx(JspHelper.SEARCH_CONTEXT_CLINICAL_DATA_EXTRACTION);
      helper.setOp("CaseListPrepare");
      helper.setPathOp("ClinicalDataSummaryPrepare");
      helper.setImportedYN("Y");
      helper.setPageTitle("Select Donor (Clinical Data Extraction)");
      helper.determineLinkInformation(request,helper.getCtx(),helper.getOp(),helper.getPathOp());
      request.setAttribute("helper", helper);
    }
    return (mapping.findForward("success"));
  }

}