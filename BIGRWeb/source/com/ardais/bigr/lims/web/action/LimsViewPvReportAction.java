package com.ardais.bigr.lims.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.helpers.SampleAppearance;
import com.ardais.bigr.lims.beans.LimsDataValidator;
import com.ardais.bigr.lims.beans.LimsDataValidatorHome;
import com.ardais.bigr.lims.beans.LimsOperation;
import com.ardais.bigr.lims.beans.LimsOperationHome;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationSampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class LimsViewPvReportAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    PathologyEvaluationData ped = null;
    LimsOperation operation = null;

    //get some information from the request
    String peId = request.getParameter("peId");
    boolean validPeId = ApiFunctions.safeStringLength(peId) > 0;
    String sampleId = request.getParameter("sampleId");
    boolean validSampleId = ApiFunctions.safeStringLength(sampleId) > 0;
    String showImageReport = request.getParameter("showImageReport");
    boolean showImage = ApiFunctions.safeStringLength(showImageReport) > 0;
    LimsDataValidatorHome ldvHome = (LimsDataValidatorHome) EjbHomes.getHome(LimsDataValidatorHome.class);
    LimsDataValidator limsVal = ldvHome.create();
    boolean isSamplePulled = limsVal.isSamplePulled(sampleId);

    //if showImageReport, just get the sample data and forward.
    if (showImage && validSampleId) {
      if (operation == null) {
        LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
        operation = home.create();
      }
      PathologyEvaluationSampleData sed = operation.getEvaluationSampleData(sampleId);
      request.setAttribute("sample", sed);
      request.setAttribute("evaluation", new PathologyEvaluationData());
      request.setAttribute("showImageReport", "true");
      return (mapping.findForward("success"));
    }
    else if (showImage && !validSampleId) {
      throw new ApiException("ViewPvReportAction: Sample must be specified for showing image report");
    }
    
    //if this is a pulled sample, just get the sample data and forward.
    if (isSamplePulled) {
      if (operation == null) {
        LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
        operation = home.create();
      }
      PathologyEvaluationSampleData sed = operation.getEvaluationSampleData(sampleId);
      request.setAttribute("sample", sed);
      return (mapping.findForward("successPulledSample"));
    }

    /* The way we determine what data to show is as follows:
     * 1) if an evaluation has been passed in via the request, use it. This will only be the 
     * case when we're showing the PV form from Create Evaluation.
     * 2) if a valid PE id has been specified, use that to get the PE data to show
     * 3) if a valid sample id has been specified, use it's reported evaluation to get the 
     * PE data to show. 
     * NOTE: ped may be null when we're finished in this section of the code.  This could
     * happen if there is no reported evaluation for a sample (see MR5824), for example.  The
     * JSP will handle this case. */
    //try to get the evaluation from the request
    ped = (PathologyEvaluationData) request.getAttribute("evaluation");
    //if that didn't work, see if we can use either the pathology evaluation id or sample id
    //passed in, if any
    if (ped == null) {
      if (validPeId) {
        //use the peId to get the data 
        if (operation == null) {
          LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
          operation = home.create();
        }
        ped = operation.getPathologyEvaluationDataWithFeatureLists(peId);
      }
      else if (validSampleId) {
        //get the reported evaluation
        if (operation == null) {
          LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
          operation = home.create();
        }
        ped = operation.getReportedPathologyEvaluation(sampleId);
        //if there's a reported evaluation, get it's full data
        if (ped != null) {
          ped = operation.getPathologyEvaluationDataWithFeatureLists(ped.getEvaluationId());
        }
        //otherwise we couldn't find a reported evaluation so put a message into the
        //request to indicate that and the jsp will display it
        else {
          request.setAttribute(
            "noDataReason",
            "The specified sample no longer has a reported pathology evaluation.");
        }
      }
    }

    //if we found pe data, set it into the request and get the information for the sample
    //as well
    if (ped != null) {
      request.setAttribute("evaluation", ped);
      String pedSampleId = ped.getSampleId();
      if (operation == null) {
        LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
        operation = home.create();
      }
      PathologyEvaluationSampleData sed = operation.getEvaluationSampleData(pedSampleId);
      //Get diagnosis from DDC primary section.
      String donorInstDiagnosis = sed.getDIPathReportPrimaryDx();
      //If primary section is not available, get case diagnosis.
      if (ApiFunctions.isEmpty(donorInstDiagnosis)) {
        donorInstDiagnosis = sed.getCaseDx();
      }
      SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
      //If Microscopic appearance is not available    
      if (ApiFunctions.isEmpty(ped.getMicroscopicAppearance())) {
        SampleAppearance sap =
          new SampleAppearance(
            ped.getSampleId(),
            donorInstDiagnosis,
            ped.getDiagnosis(),
            ped.getTumorCells(),
            ped.getLesionCells(),
            ped.getNormalCells(),
            ped.getNecrosisCells(),
            ped.getHypoacellularStromaCells(),
            ped.getCellularStromaCells(),
            securityInfo);
        ped.setMicroscopicAppearance(sap.computeSampleAppearance(false));
      }

      request.setAttribute("sample", sed);
    }

    return (mapping.findForward("success"));

  }
}
