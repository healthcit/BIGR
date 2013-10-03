package com.ardais.bigr.library.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.dataImport.web.form.CaseForm;
import com.ardais.bigr.dataImport.web.form.SampleForm;
import com.ardais.bigr.iltds.btx.BTXActionForward;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.library.btx.BTXDetailsGetSummarySql;
import com.ardais.bigr.library.web.form.QueryForm;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.query.ProductQuery;
import com.ardais.bigr.query.RnaQuery;
import com.ardais.bigr.query.SampleQuery;
import com.ardais.bigr.query.filters.FilterSet;
import com.ardais.bigr.query.filters.RnaFilters;
import com.ardais.bigr.query.filters.SampleFilters;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.action.BtxAction;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;

/**
 * Returns the SQL for the specified query parameters.
 */
public class QuerySqlAction extends BtxAction {

  /* (non-Javadoc)
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected BTXDetails doBtxPerform(
    BTXDetails btxGeneric,
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    QueryForm ssForm = (QueryForm) form;
    String txType = ssForm.getTxType();
    String resultsFormDefinitionId = ssForm.getResultsFormDefinitionId();

    SecurityInfo securityInfo = getSecurityInfo(request);
    if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_SHOW_SQL_TAB)) {
      /**
       * @todo  create and throw BigrSecurityException
       */
      throw new ApiException(
        "SECURITY ALERT ("
          + securityInfo.getAccount()
          + "/"
          + securityInfo.getUsername()
          + "): You do not have permission to access requested functionality.");
    }

    BTXDetailsGetSummarySql btx = (BTXDetailsGetSummarySql) btxGeneric;

    FilterSet filts = btx.getFilterSet();
    SampleFilters sampleFilters = new SampleFilters(filts);
    RnaFilters rnaFilters = new RnaFilters(filts);

    // Return the SQL queries for display on the query page.
    StringBuffer debugSql = new StringBuffer(1024);

    debugSql.append(" Tissue Criteria \n");
    debugSql.append(sampleFilters.toString());

    if (txType.equals(ResultsHelper.TX_TYPE_SAMP_SEL)) {
      debugSql.append("\n\n RNA Criteria \n");
      debugSql.append(rnaFilters.toString());
    }

    ProductQuery sampleQuery = new SampleQuery(0, securityInfo);
    debugSql.append("<p>-- Frozen and Paraffin query<br>");
    btx = (BTXDetailsGetSummarySql) sampleQuery.getSummarySql(btx);
    debugSql.append(btx.getSqlResult());

    if (txType.equals(ResultsHelper.TX_TYPE_SAMP_SEL)) {
      ProductQuery rnaQuery = new RnaQuery(0, securityInfo);
      debugSql.append("<p>-- RNA query<br>");
      btx = (BTXDetailsGetSummarySql) rnaQuery.getSummarySql(btx);
      debugSql.append(btx.getSqlResult());
    }

    String sql = debugSql.toString();
    btx.setSqlResult(sql);
    request.setAttribute("debugSql", debugSql.toString());
    
    //SWP-1114, create a sampleForm bean in request to display tissue dropdown list in case this action fail
    if(ResultsHelper.TX_TYPE_SAMP_SEL.equalsIgnoreCase(txType) || ResultsHelper.TX_TYPE_SAMP_REQUEST.equalsIgnoreCase(txType)) {               
      SampleForm _sampleForm = new SampleForm();
      _sampleForm.setSampleData(new com.ardais.bigr.javabeans.SampleData());      
      CaseForm _caseForm = new CaseForm();
      
      request.setAttribute("caseForm", _caseForm);
      request.setAttribute("sampleForm", _sampleForm);
      request.setAttribute("CONTROL_OTHER", QueryForm.CONTROL_OTHER);
    }
    
    //restore the results form definition in use
    ssForm.setResultsFormDefinitionId(resultsFormDefinitionId);
    //restore the list of results form definitions for the user
    ssForm.setResultsFormDefinitions(FormUtils.getResultsFormDefinitionsForUser(securityInfo, true));

    btx.setActionForward(new BTXActionForward("success")); // control handled via old dispatcher
    return btx;

  }
}
