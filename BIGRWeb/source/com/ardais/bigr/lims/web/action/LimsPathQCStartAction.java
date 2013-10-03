package com.ardais.bigr.lims.web.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.lims.web.form.LimsPathQcForm;
import com.ardais.bigr.query.LimsPathQcQuery;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.LogicalRepositoryUtils;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class LimsPathQCStartAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    request.getSession().removeAttribute(LimsPathQcQuery.LIMSPATHQCQUERY_KEY);
    request.getSession().removeAttribute(LimsConstants.LIMS_CHUNK_INDEX);
    request.getSession().removeAttribute(LimsConstants.LIMS_CURRENT_CHUNK);
    
    LimsPathQcForm lf;

    if (request.getSession(false).getAttribute("limsPathQcForm") == null) {

      lf = new LimsPathQcForm();

      if (lf.getPathologistList() == null || lf.getPathologistList().length == 0) {
        lf.setPathologistList(new String[] { LimsConstants.DEFAULT_ALL });
      }
      //default the selected logical repository to All
      if (lf.getLogicalRepositoryList() == null || lf.getLogicalRepositoryList().length == 0) {
        lf.setLogicalRepositoryList(new String[] { LimsConstants.DEFAULT_ALL });
      }
      if (lf.getIncludeList() == null || lf.getIncludeList().length == 0) {
        lf.setIncludeList(new String[] { LimsConstants.PATH_QC_SAMPLES_FOR_RELEASE });
      }
      lf.setPvStatus(LimsConstants.PATH_QC_BOTH_SAMPLES);
      request.getSession(false).setAttribute("limsPathQcForm", lf);
    }
    else {

      lf = (LimsPathQcForm) request.getSession(false).getAttribute("limsPathQcForm");
      lf.setTargetSampleId("");
      lf.setPathQcSampleDetails(null);
      lf.setPvCount("");
      lf.setReleasedCount("");
      lf.setPulledCount("");
      lf.setReturnedSamplesCount("");
      lf.setUnPVedCount("");
      lf.setQcPostedCount("");
      lf.setViewIncidentReport(null);
      lf.setViewQCEdit(null);
    }
    
    //populate the list of logical repositories from which the user can select.
    //If the user can see all logical repositories query the db to get the complete list
    //otherwise just take them from the SecurityInfo.
    //NOTE: unlike the list of pathologists (which is populated by the form the first time
    //it's requested), the logical repository list is populated here because we need
    //information in the request.
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
    List logicalRepositories;
    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
      logicalRepositories = LogicalRepositoryUtils.getAllLogicalRepositories();      
    }
    else {
      logicalRepositories = securityInfo.getLogicalRepositoriesByFullName();
    }
    LegalValueSet logicalRepositoryList = new LegalValueSet();
    Iterator iterator = logicalRepositories.iterator();
    while (iterator.hasNext()) {
      LogicalRepository lr = (LogicalRepository)iterator.next();
      logicalRepositoryList.addLegalValue(lr.getId(),lr.getFullName());      
    }
    logicalRepositoryList.addLegalValue(0, LimsConstants.DEFAULT_ALL, LimsConstants.DEFAULT_ALL);
    lf.setLogicalRepositoryOptions(logicalRepositoryList);

    return (mapping.findForward("success"));
  }

}
