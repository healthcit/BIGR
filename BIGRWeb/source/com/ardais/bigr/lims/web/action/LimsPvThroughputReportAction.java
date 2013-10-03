package com.ardais.bigr.lims.web.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiDateUtil;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.lims.beans.LimsOperation;
import com.ardais.bigr.lims.beans.LimsOperationHome;
import com.ardais.bigr.lims.web.form.LimsPvThroughputReportForm;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class LimsPvThroughputReportAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    LimsPvThroughputReportForm lf = (LimsPvThroughputReportForm) form;
    
    String username = lf.getUserName();
    Timestamp fromDate = ApiDateUtil.convertStringToTimeStamp(lf.getFromDate() + " 0:00", "MM/dd/yyyy HH:mm");
    Timestamp toDate = ApiDateUtil.convertStringToTimeStamp(lf.getToDate() + " 23:59", "MM/dd/yyyy HH:mm");
    
    LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
    LimsOperation operation = home.create();
    LegalValueSet report =
      operation.getPvThroughputReport(username, fromDate, toDate);
    request.setAttribute("reportDetails", report);
    return mapping.findForward("success");
  }

}
