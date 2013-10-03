package com.ardais.bigr.iltds.op;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.pdc.oce.util.OceUtil;
import com.ardais.bigr.util.EjbHomes;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;
/**
 * Insert the type's description here.
 * Creation date: (1/17/2001 6:33:50 PM)
 * @author: Jake Thompson
 */
public class CaseReleaseInsert extends com.ardais.bigr.iltds.op.StandardOperation {
  /**
   * CaseReleaseSubmitted constructor comment.
   * @param req javax.servlet.http.HttpServletRequest
   * @param res javax.servlet.http.HttpServletResponse
   * @param ctx javax.servlet.ServletContext
   */
  public CaseReleaseInsert(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }
  /**
   * Insert the method's description here.
   * Creation date: (1/17/2001 6:33:50 PM)
   */
  public void invoke() throws IOException, Exception {
    try {
      String user = (String) request.getSession(false).getAttribute("user");
      String account = (String) request.getSession(false).getAttribute("account");

      String consentID = request.getParameter("consentID");
      Timestamp myTimestamp = new java.sql.Timestamp(new java.util.Date().getTime());
      String diagnosis = request.getParameter("diagnosis");
      String otherDiagnosis = request.getParameter("otherDiagnosis");
      String topLineDiagnosis;

      ConsentAccessBean myConsent = new ConsentAccessBean();
      AccessBeanEnumeration enum1 = (AccessBeanEnumeration) myConsent.findByConsentID(consentID);
      ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
      ListGenerator lookup = home.create();

      if (diagnosis != null
        && !diagnosis.equalsIgnoreCase(lookup.lookupDiseaseCode("Other diagnosis"))) {
        topLineDiagnosis = diagnosis;
      }
      else {
        topLineDiagnosis = lookup.lookupDiseaseCode("Other diagnosis");
      }

      ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean();
      staff = (ArdaisstaffAccessBean) staff.findLocByUserProf(user, account).nextElement();

      while (enum1.hasMoreElements()) {
        ConsentAccessBean myConsentAB = (ConsentAccessBean) enum1.nextElement();
        myConsentAB.setConsent_release_datetime(myTimestamp);
        myConsentAB.setConsent_release_staff_id(
          ((ArdaisstaffKey) staff.__getKey()).ardais_staff_id);
        myConsentAB.setDisease_concept_id(topLineDiagnosis);
        myConsentAB.setDisease_concept_id_other(otherDiagnosis);
        myConsentAB.commitCopyHelper();
        
        //give the samples in this case a sales status of GENERELEASED if appropriate
        IltdsUtils.applyPolicyToSamplesInCase(myConsentAB, IltdsUtils.APPLY_POLICY_FOR_SALES_STATUS);

        // Save other diagnosis.
        if ((diagnosis != null)
          && (diagnosis.equalsIgnoreCase(lookup.lookupDiseaseCode("Other diagnosis")))) {

          List pKeys = new ArrayList();
          pKeys.add(myConsentAB.getArdais_id());
          pKeys.add(consentID);

          OceUtil.createOce(OceUtil.ILTDS_INFORMED_CONSENT_OTHER_DX, pKeys, otherDiagnosis, user);
        }
      }
      request.setAttribute("myError", "Case ID " + consentID + " has been released.");
      servletCtx.getRequestDispatcher("/hiddenJsps/iltds/caseRelease/caseRelease.jsp").forward(
        request,
        response);
    }
    catch (Exception e) {
      e.printStackTrace();
      ReportError err = new ReportError(request, response, servletCtx);
      err.setFromOp(this.getClass().getName());
      err.setErrorMessage(e.toString());
      try {
        err.invoke();
      }
      catch (Exception _axxx) {
      }
      return;
    }
  }
}
