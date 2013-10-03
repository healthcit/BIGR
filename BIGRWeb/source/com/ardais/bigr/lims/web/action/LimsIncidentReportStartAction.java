package com.ardais.bigr.lims.web.action;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.lims.beans.LimsOperation;
import com.ardais.bigr.lims.beans.LimsOperationHome;
import com.ardais.bigr.lims.javabeans.IncidentReportData;
import com.ardais.bigr.lims.javabeans.IncidentReportLineItem;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationSampleData;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.query.LimsPathQcQuery;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class LimsIncidentReportStartAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {
    
    //if there is already an IncidentReportData object in the request, use it.  This will be the
    //case when we've arrived back here from a submit of the form and there's invalid data.
    //Otherwise figure out what samples to show in the incident report and get the data
    if (request.getAttribute(IncidentReportData.INCIDENT_REPORT_KEY) == null) {
      HashSet samples = null;
      //if a specific sampleId has been specified then show that sample in the incident report form
      String sampleId = (String) request.getParameter("sampleId");
      if (!ApiFunctions.safeString(sampleId).equals("")) {
        samples = new HashSet();
        samples.add(sampleId);
      }
      //otherwise get the samples from the session
      else {
        LimsPathQcQuery query =
        (LimsPathQcQuery) request.getSession().getAttribute(LimsPathQcQuery.LIMSPATHQCQUERY_KEY);
        samples = query.getMarkedSamples();
      }
      
      PathologyEvaluationSampleData data;
      PathologyEvaluationData eval;
  
      IncidentReportData report = new IncidentReportData();
      //report.setCreatedBy((String) request.getSession(false).getAttribute("user"));
      //report.setCreateDate(Calendar.getInstance().getTime());
  
      LimsOperation operation = null;
      Iterator iter = samples.iterator();
      while (iter.hasNext()) {
        IncidentReportLineItem lineItem = new IncidentReportLineItem();
        String sample = (String) iter.next();
        if (operation == null) {
          LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
          operation = home.create();
        }
        data = operation.getEvaluationSampleData(sample);
        eval = data.getReportedEvaluation();

        lineItem.setPullReason(data.getPullOrDiscordantReason());
        lineItem.setAsmPosition(data.getAsmPosition());
        lineItem.setCaseId(data.getCaseId());
        if (eval != null) {
          lineItem.setPathologist(eval.getPathologist());
          lineItem.setSlideId(eval.getSlideId());
        }
        lineItem.setSampleId(sample);
        //by default all line items should be marked to be saved to the database
        lineItem.setSave(true);
        report.addLineItem(lineItem);
      }
  
      request.setAttribute(IncidentReportData.INCIDENT_REPORT_KEY, report);
    }
    
    //add the legal value set of actions to the request
    request.setAttribute("actionSet", getActionSet());
    
    //Not currently showing a "deliver to" column on the Incident Report
    //screen, so commenting this out for now.
    ////add the list of "deliver to" choices to the request.
    //request.setAttribute("deliverToSet", getDeliverToSet(request));

    return (mapping.findForward("success"));
  }
  
  private LegalValueSet getDeliverToSet(HttpServletRequest request)
  throws Exception {
    LegalValueSet deliverTo = new LegalValueSet();
    String account =
      (String) request.getSession(false).getAttribute("account");
    ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
    ListGenerator list = home.create();
    Vector v = list.lookupArdaisStaffByAccountId(account);
    for (int i=0; i<v.size(); i = i+2) {
      deliverTo.addLegalValue((String)v.get(i+1),(String)v.get(i));
    }
    return deliverTo;
  }
  
  private LegalValueSet getActionSet() {
    LegalValueSet actions = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_LIMS_INCIDENT_ACTION);
    return actions;
  }

}
