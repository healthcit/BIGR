package com.ardais.bigr.reports.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.ardais.bigr.reports.web.form.BigrReportsForm;
import com.ardais.bigr.reports.DonorAndCaseReportVO;
//import com.gulfstreambio.bigr.integration.aperio.AperioForm;

/**
 * A BIGR action that simply forwards to the context-relative URI specified by the parameter
 * attribute of the associated action mapping.  This is analagous to the Struts ForwardAction,
 * but extends BigrAction and thus inherits all of its functionality.
 */
public class BigrReportsAction extends BigrAction {
  
  private final static String FORWARD_DONOR_CASE_SEARCH = "donorCaseSearchCriteria";
  
  public BigrReportsAction() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(com.ardais.bigr.web.action.BigrActionMapping, com.ardais.bigr.web.form.BigrActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {
    

    BigrReportsForm reportsForm = (BigrReportsForm) form;
    DonorAndCaseReportVO donorAndCaseReportVO = reportsForm.getDonorAndCaseReportVO();
    
    ActionForward forward = mapping
    .findForward(FORWARD_DONOR_CASE_SEARCH);
    //DealerRiskScoreBO dealerRiskScoreBO = (DealerRiskScoreBO) dealerRiskScoreForm.get("dealerRiskScoreBO");
    

   // return new ActionForward(mapping.getParameter());
    return forward;
  }

}
