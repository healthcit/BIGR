package com.ardais.bigr.library.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.dataImport.web.form.CaseForm;
import com.ardais.bigr.dataImport.web.form.SampleForm;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.javabeans.DiagnosticTestFilterDto;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.library.web.form.QueryAttributesForm;
import com.ardais.bigr.library.web.form.QueryDiagnosisForm;
import com.ardais.bigr.library.web.form.QueryForm;
import com.ardais.bigr.library.web.form.QueryKcForm;
import com.ardais.bigr.library.web.form.QueryTissueForm;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.RepeatingFilterData;
import com.ardais.bigr.query.filters.RepeatingSingleData;
import com.ardais.bigr.query.generator.FilterLocalSamples;
import com.ardais.bigr.query.generator.FilterLogicalRepository;
import com.ardais.bigr.query.generator.FilterRestrictedStatus;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.LogicalRepositoryUtils;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;

/**
 * @author jthompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class QueryClearAction extends BigrAction {
  private void saveForm(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request) {
    HttpSession session = request.getSession();
    String scope = mapping.getScope();
    if ("session".equals(scope)) {
      session.setAttribute(mapping.getName(), form);
    }
    if ("request".equals(scope)) {
      request.setAttribute(mapping.getName(), form);
    }
  }

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    QueryForm qForm = (QueryForm) form;
    String txType = qForm.getTxType();
    HttpSession session = request.getSession(false);

    // If a KC form has been selected and potentially populated, we have to call the KC form's 
    // getFilters method to process any input that the user may have entered and set the values
    // in the query form definition referenced in the KC form, so the values can be displayed 
    // when we return the page.  The KC form will be in the session in ResultsHelper if a KC form
    // has been chosen.  If the KC tab is to be cleared, it will be cleared properly later in 
    // this method.    
    QueryKcForm kcForm = qForm.getKc();
    if (kcForm != null) {
      ResultsHelper rh = ResultsHelper.get(txType, request);
      QueryFormDefinition qfd = rh.getQueryForm();
      if (qfd != null) {
       kcForm.setQueryFormDefinition(qfd);
        kcForm.getFilters();   
      }
    }
   
    // The user requested that the query be cleared.
    String clearParam = request.getParameter("clearQuery");
    if ("all".equals(clearParam)) {
      clearQuery(mapping, form, request);

    }
    else if ("id".equals(clearParam)) {
      clearQueryId(mapping, form, request);

    }
    else if ("attributes".equals(clearParam)) {
      clearQueryAttributes(mapping, form, request);

    }
    else if ("diagnosis".equals(clearParam)) {
      clearQueryDiagnosis(mapping, form, request);

    }
    else if ("tissue".equals(clearParam)) {
      clearQueryTissue(mapping, form, request);

    }
    else if ("appearance".equals(clearParam)) {
      clearQueryAppearance(mapping, form, request);

    }
    else if ("diagnosticTest".equals(clearParam)) {
      clearQueryDiagnosticTest(mapping, form, request);

    }
    else if ("clinLabTest".equals(clearParam)) {
      clearQueryClinLab(mapping, form, request);

    }
    else if ("clinFind".equals(clearParam)) {
      clearQueryClinFindings(mapping, form, request);

    }
    else if ("kc".equals(clearParam)) {
      clearQueryKc(mapping, form, request);
     }
    else if ("selected_null_KC_form".equals(clearParam)) {
      nullQueryKcForm(mapping, form, request);
    }
    
    //update the results helper to contain the appropriate filters
    ResultsHelper rh = ResultsHelper.get(txType, request);
    rh.setFilters(((QueryForm) form).getFilters());
    
    // Update the diagnosis and tissue forms from the session so that their labels will get 
    // properly initialized for redisplay in the UI.  If the diagnosis and tissue forms were
    // cleared, then the updateFromSession call wil quietly do nothing.
    QueryDiagnosisForm dForm = qForm.getDiagnosis();
    if (dForm != null) {
      dForm.updateFromSession(session, txType);
    }
    QueryTissueForm tForm = qForm.getTissue();
    if (tForm != null) {
      tForm.updateFromSession(session, txType);
    }
    
    
    //SWP-1114, create a sampleForm bean in request to display tissue dropdown list in case this action fail
    if(ResultsHelper.TX_TYPE_SAMP_SEL.equalsIgnoreCase(txType) || ResultsHelper.TX_TYPE_SAMP_REQUEST.equalsIgnoreCase(txType)) {               
      SampleForm _sampleForm = new SampleForm();
      _sampleForm.setSampleData(new com.ardais.bigr.javabeans.SampleData());      
      CaseForm _caseForm = new CaseForm();
      
      request.setAttribute("caseForm", _caseForm);
      request.setAttribute("sampleForm", _sampleForm);
      request.setAttribute("CONTROL_OTHER", QueryForm.CONTROL_OTHER);
    }
    
    return new ActionForward(mapping.getInput());
  }

  /**
   * Clears the query.
   */
  private void clearQuery(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request) {
    
    QueryForm qForm = (QueryForm) form;
    String txType = qForm.getTxType();
    String resultsFormDefinitionId = qForm.getResultsFormDefinitionId();
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);

    form.reset(mapping, request);
    clearQueryAppearance(mapping, form, request);
    clearQueryAttributes(mapping, form, request);
    clearQueryDiagnosis(mapping, form, request);
    clearQueryId(mapping, form, request);
    clearQueryTissue(mapping, form, request);
    clearQueryDiagnosticTest(mapping, form, request);
    clearQueryClinLab(mapping, form, request);
    clearQueryClinFindings(mapping, form, request);
    clearQueryKc(mapping, form, request);
    
    //restore the results form definition in use
    qForm.setResultsFormDefinitionId(resultsFormDefinitionId);
    //restore the list of results form definitions for the user
    qForm.setResultsFormDefinitions(FormUtils.getResultsFormDefinitionsForUser(securityInfo, true));
    saveForm(mapping, form, request);

  }

  /**
   * Clears the query.
   */
  private void clearQueryAppearance(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request) {
    QueryForm qForm = (QueryForm) form;

    qForm.getAppearance().reset(mapping, request);
  }

  /**
   * Clears the query.
   */
  private void clearQueryDiagnosticTest(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request) {
    QueryForm qForm = (QueryForm) form;
    String txType = qForm.getTxType();
    qForm.getDiagnosticTest().resetDiagnosticTest();
    HttpSession session = request.getSession();
    DiagnosticTestFilterDto dto = 
      (DiagnosticTestFilterDto) session.getAttribute(txType + Constants.DIAGNOSTIC_TEST_RESULT_FILTER);
    if (dto != null) {
      dto.clearDiagnosticTests();      
    }
    saveForm(mapping, qForm, request);
  }

  private void clearQueryClinLab(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request) {
    QueryForm qForm = (QueryForm) form;
    String txType = qForm.getTxType();
    qForm.getDiagnosticTest().resetClinLab();
    HttpSession session = request.getSession();
    DiagnosticTestFilterDto dto = 
      (DiagnosticTestFilterDto) session.getAttribute(txType + Constants.DIAGNOSTIC_TEST_RESULT_FILTER);
    if (dto != null) {
      dto.clearClinLab();      
    }
    saveForm(mapping, qForm, request);
  }

  private void clearQueryClinFindings(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request) {
    QueryForm qForm = (QueryForm) form;
    String txType = qForm.getTxType();
    qForm.getDiagnosticTest().resetClinFindings();
    HttpSession session = request.getSession();
    DiagnosticTestFilterDto dto = 
      (DiagnosticTestFilterDto) session.getAttribute(txType + Constants.DIAGNOSTIC_TEST_RESULT_FILTER);
    if (dto != null) {
      dto.clearClinFindings();      
    }
    saveForm(mapping, qForm, request);
  }
  
  /**
   * Clears the query.
   */
  private void clearQueryAttributes(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request) {
    QueryForm qForm = (QueryForm) form;

    qForm.getAttributes().reset(mapping, request);
    handleSpecialAttributeDefaults(qForm.getAttributes(), request);
    saveForm(mapping, qForm, request);
  }

  /**
   * Clears the query.
   */
  private void clearQueryDiagnosis(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request) {
    QueryForm qForm = (QueryForm) form;
    String txType = qForm.getTxType();

    qForm.getDiagnosis().reset(mapping, request);
    saveForm(mapping, qForm, request);
    HttpSession session = request.getSession();
    session.removeAttribute("libraryCaseDiagnosis.selected." + txType);
    session.removeAttribute("libraryCaseDiagnosis.label." + txType);
    session.removeAttribute("librarySamplePathology.selected." + txType);
    session.removeAttribute("librarySamplePathology.label." + txType);
  }

  private void clearQueryKc(BigrActionMapping mapping,
                                    BigrActionForm form,
                                    HttpServletRequest request) {
    
//MR9049 when we reset a form, we want to reload the form with system defaults
    QueryForm qForm = (QueryForm) form;
    if (qForm.getKc().getQueryFormDefinition() != null){
    
    String currentQformID = qForm.getKc().getQueryFormDefinition().getFormDefinitionId();
    FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON
      .findFormDefinitionById(currentQformID);
     QueryFormDefinition defaultQFormDefinition = response.getQueryFormDefinition();
   qForm.getKc().setQueryFormDefinition(defaultQFormDefinition);
    ResultsHelper rh = ResultsHelper.get(qForm.getTxType(), request);
    synchronized (rh) {
    rh.setQueryForm(defaultQFormDefinition);
    }
    saveForm(mapping, qForm, request);  
  }
  }
  //use select null form, go to blank page
  private void nullQueryKcForm(BigrActionMapping mapping,
                            BigrActionForm form,
                            HttpServletRequest request) {
    QueryForm qForm = (QueryForm) form;
    qForm.getKc().reset(mapping, request);
    ResultsHelper rh = ResultsHelper.get(qForm.getTxType(), request);
    synchronized (rh) {
       rh.setQueryForm(null);
    }
    saveForm(mapping, qForm, request);  
  }
  /**
   * Clears the query.
   */
  private void clearQueryId(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request) {
    QueryForm qForm = (QueryForm) form;

    qForm.getId().reset(mapping, request);
    saveForm(mapping, qForm, request);
  }

  /**
   * Clears the query.
   */
  private void clearQueryTissue(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request) {
    QueryForm qForm = (QueryForm) form;
    String txType = qForm.getTxType();

    qForm.getTissue().reset(mapping, request);
    saveForm(mapping, qForm, request);

    HttpSession session = request.getSession();
    session.removeAttribute("libraryTissueOrigin.selected." + txType);
    session.removeAttribute("libraryTissueOrigin.label." + txType);
    session.removeAttribute("libraryTissueFinding.selected." + txType);
    session.removeAttribute("libraryTissueFinding.label." + txType);
  }

  
  
  //For Ardais users we need to default a couple of parameters as specified below:
  //MR6833 - Ardais users should default the Allocation Status query parameter 
  //         to "Unrestricted" for both Sample Selection and Request Samples
  //MR6828 - Ardais users should default selected Logical Repositories
  //         to all non-BMS possiblities for sample selection.
  //This is done here instead of in the QueryAttributesForm.doReset() method because
  //of MR6849.  In a nutshell, if the user changes either of these default
  //parameters to Any/All, does a search, and then goes back to the Sample Selection 
  //screen, setting these defaults in the doReset() method would "override" the user's
  //choice of Any/All.  That's because the form is populated with the defaults, and then
  //the filters from the previous request are used to put the form back to how the user
  //had it.  We don't create a filter for an Any/All choice, so the default would not
  //be put back to Any/All.
  private void handleSpecialAttributeDefaults(
    QueryAttributesForm form,
    HttpServletRequest request) {
    Map defaultFilterMap = new HashMap();
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
    String txType = request.getParameter("txType");
    if (securityInfo.isInRoleSystemOwner()) {
      if (ResultsHelper.TX_TYPE_SAMP_SEL.equals(txType)
        || ResultsHelper.TX_TYPE_SAMP_REQUEST.equals(txType)) {
        FilterRestrictedStatus filterRestricted =
          new FilterRestrictedStatus(Constants.RESTRICTED_U);
        defaultFilterMap.put(FilterConstants.KEY_RESTRICTED_FOR_DI, filterRestricted);
      }
      if (ResultsHelper.TX_TYPE_SAMP_SEL.equals(txType)) {
        List logicalRepositories;
        if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
          logicalRepositories = LogicalRepositoryUtils.getAllLogicalRepositories();
        }
        else {
          logicalRepositories = securityInfo.getLogicalRepositoriesByFullName();
        }
        Iterator iterator = logicalRepositories.iterator();
        ArrayList lrList = new ArrayList();
        while (iterator.hasNext()) {
          LogicalRepository lr = (LogicalRepository) iterator.next();
          lrList.add(lr.getId());
        }
        if (!lrList.isEmpty()) {
          RepeatingSingleData singleData = new RepeatingSingleData((String[]) lrList.toArray(new String[0]));
          RepeatingFilterData filterData = new RepeatingFilterData();
          filterData.add(singleData);
          FilterLogicalRepository filterLogicalRepository = new FilterLogicalRepository(filterData);
          defaultFilterMap.put(FilterConstants.KEY_LOGICAL_REPOSITORY, filterLogicalRepository);
        }
      }
    }
    if (securityInfo.isInRoleSystemOwnerOrDi()) {
      if (ResultsHelper.TX_TYPE_SAMP_REQUEST.equals(txType)) {
        FilterLocalSamples filterLocalSamples = new FilterLocalSamples(securityInfo.getUserLocationId());
        defaultFilterMap.put(FilterConstants.KEY_LOCAL_SAMPLES_ONLY,filterLocalSamples);
      }
    }
    //if we've come up with any default filters, update the form
    if (!defaultFilterMap.isEmpty()) {
      form.setFilters(defaultFilterMap);
    }
  }
}
