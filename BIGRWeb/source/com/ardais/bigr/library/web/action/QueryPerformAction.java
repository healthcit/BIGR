package com.ardais.bigr.library.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.query.sorting.SortByColumn;
import com.ardais.bigr.query.sorting.SortOrder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.dataImport.web.form.CaseForm;
import com.ardais.bigr.dataImport.web.form.SampleForm;
import com.ardais.bigr.iltds.btx.BTXActionForward;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.library.btx.BTXDetailsForSampleSummary;
import com.ardais.bigr.library.btx.BTXDetailsGetSampleSummary;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.form.QueryForm;
import com.ardais.bigr.library.web.form.QueryIdForm;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.action.BtxAction;
import com.ardais.bigr.web.form.BigrActionForm;

public class QueryPerformAction extends BtxAction {

  private static Log _perfLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_PERFORMANCE);

	public static final String QUERY_REQUEST_PARAM_MAP = "QUERY_REQUEST_PARAM_MAP";

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected BTXDetails doBtxPerform(
    BTXDetails genericBtx,
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    long tStart = 0;
    if (_perfLog.isDebugEnabled()) {
      _perfLog.debug("    0: START QueryPerformAction");
      tStart = System.currentTimeMillis();
    }
    try {
      BTXDetailsGetSampleSummary btx = (BTXDetailsGetSampleSummary) genericBtx;

      QueryForm ssForm = (QueryForm) form;
      String txType = ssForm.getTxType();
      boolean doRnaQuery = false;
      boolean doSampleQuery = true;
      boolean rnaVialIdPresent = false;
      boolean sampleIdPresent = false;
      
      QueryIdForm idForm = ssForm.getId();
      if (idForm != null && idForm.getRnaIds() != null && idForm.getRnaIds().length > 0) {
        rnaVialIdPresent = true;
      }
      if (idForm != null && idForm.getTissueIds() != null && idForm.getTissueIds().length > 0) {
        sampleIdPresent = true;
      }

      int chunkSize = ApiProperties.getPropertyAsInt("api.bigr.library.chunk.size", 0);
      
      //SWP-1114, create a sampleForm bean in request to display tissue dropdown list in case this action fail
      if(ResultsHelper.TX_TYPE_SAMP_SEL.equalsIgnoreCase(txType) || ResultsHelper.TX_TYPE_SAMP_REQUEST.equalsIgnoreCase(txType)) {               
        SampleForm _sampleForm = new SampleForm();
        _sampleForm.setSampleData(new com.ardais.bigr.javabeans.SampleData());      
        CaseForm _caseForm = new CaseForm();
        
        request.setAttribute("caseForm", _caseForm);
        request.setAttribute("sampleForm", _sampleForm);
        request.setAttribute("CONTROL_OTHER", QueryForm.CONTROL_OTHER);
      }

      //if this is a request samples transaction, there are 2 things we need to check
      //First, since rna cannot currently be BMS if they've specified RNA ids and BMS return an error.
      //Second, if the user has specified sample ids make sure they all match the BMS/non-BMS 
      //parameter value (i.e don't let them specify non-BMS sample ids if they've said to return 
      //BMS samples).
      String bms = ssForm.getAttributes().getBms_YN();
      //MR8010 - check if bms has been set. 
      if (txType.equals(ResultsHelper.TX_TYPE_SAMP_REQUEST) && !ApiFunctions.isEmpty(bms)) {
        if (bms.equalsIgnoreCase(FormLogic.DB_YES) && rnaVialIdPresent) {
          btx.addActionError(new BtxActionError("library.ss.error.bmsnotapplicabletorna"));
          btx.setActionForward(new BTXActionForward("fail"));
          return btx;
        }
        if (sampleIdPresent) {
          String[] ids = idForm.getTissueIds();
          ArrayList tissueIds = new ArrayList(ids.length);
          for (int i = 0; i < ids.length; i++) {
            tissueIds.add(ids[i]);
          }
          String bmsParam;
          if (bms.equalsIgnoreCase(FormLogic.DB_YES)) {
            bmsParam = IltdsUtils.NONBMS;
          }
          else {
            bmsParam = IltdsUtils.BMS;
          }
          ArrayList mismatchedSamples = IltdsUtils.getBMSSamplesFromList(bmsParam, tissueIds);
          if (mismatchedSamples.size() > 0) {
            StringBuffer badIds = new StringBuffer(50);
            boolean includeComma = false;
            Iterator iterator = mismatchedSamples.iterator();
            while (iterator.hasNext()) {
              if (includeComma) {
                badIds.append(", ");
              }
              badIds.append((String) iterator.next());
              includeComma = true;
            }
            if (bms.equalsIgnoreCase(FormLogic.DB_YES)) {
              btx.addActionError(
                new BtxActionError(
                  "library.ss.error.mismatchedBMSandids",
                  "BMS",
                  "non-BMS",
                  badIds.toString()));
            }
            else {
              btx.addActionError(
                new BtxActionError(
                  "library.ss.error.mismatchedBMSandids",
                  "non-BMS",
                  "BMS",
                  badIds.toString()));
            }
            btx.setActionForward(new BTXActionForward("fail"));
            return btx;
          }
        }
      }

      // Check RNA has some criteria selected
      if (txType.equals(ResultsHelper.TX_TYPE_SAMP_SEL)) {
        doRnaQuery = btx.isRnaCriteriaSelected();
      }

      //Commented out for MR6182
      //// Check if only sample or RNA ids were specified.  If so, then only
      //// run the query for the type of id specified.
      //boolean doSampleQuery = true;
      //String[] ids = ssForm.getId().getItemIds();
      //if (ids != null) {
      //  String[] sampleIds = IdValidator.validSampleIds(ids);
      //  String[] rnaIds = IdValidator.validRnaIds(ids);
      //  if ((sampleIds.length > 0) && (rnaIds.length == 0)) {
      //    doRnaQuery = false;
      //  }
      //  if ((rnaIds.length > 0) && (sampleIds.length == 0)) {
      //    doSampleQuery = false;
      //  }
      //}

      // Check a product has some criteria selected, otherwise exit
      if (doSampleQuery) {
        doSampleQuery = btx.isSampleCriteriaSelected();
      }
      if (!doSampleQuery && !doRnaQuery) {
        btx.addActionError(new BtxActionError("library.ss.error.criterianotselected"));
        btx.setActionForward(new BTXActionForward("fail"));
        return btx; // "fail"
      }


      // MR 7900: If only RNA Vial Id is specified in SampleIDs list, then display only Rna. If only
      // Tissue Sample Id is specified then display only tissue samples.
      if (rnaVialIdPresent && !sampleIdPresent) {
        doSampleQuery = false;  
      }
      if (!rnaVialIdPresent && sampleIdPresent) {
        doRnaQuery = false;  
      }

		final String sortedColumn = ssForm.getSortedColumn();
		final boolean isDesc = ssForm.getIsDescSort();
		if (StringUtils.isNotBlank(sortedColumn))
		{
			btx.addSortColumn(new SortByColumn(sortedColumn,
											   isDesc ? SortOrder.DESC : SortOrder.ASC));
		}
		else
		{
			btx.removeSorting();
		}

      // Create the results helper, and indicate what is to be displayed.
      ResultsHelper helper = ResultsHelper.get(txType, request);
      synchronized (helper) {
        String initialDisplayType = ResultsHelper.PRODUCT_TYPE_SAMPLE;
        if (doRnaQuery && !doSampleQuery)
          initialDisplayType = ResultsHelper.PRODUCT_TYPE_RNA;

        helper.setProductType(initialDisplayType);

        // do the queries, aborting if there are errors
        if (doSampleQuery) {
          btx.setBeginTimestamp(new java.sql.Timestamp(new java.util.Date().getTime()));
          btx.setProductDescription(BTXDetailsForSampleSummary.REQUEST_TYPE_TISSUE);
          BTXDetails btxReturn = helper.setSampleFiltersAndQuery(btx, chunkSize);
          BtxActionErrors errors = btxReturn.getActionErrors();
          if (!errors.empty()) {
            btxReturn.setActionForward(new BTXActionForward("fail"));
            return btxReturn;
          }
        }
        else {
          helper.removeSampleQuery();
        }
        if (doRnaQuery) {
          btx.setBeginTimestamp(new java.sql.Timestamp(new java.util.Date().getTime()));
          btx.setProductDescription(BTXDetailsForSampleSummary.REQUEST_TYPE_RNA);
          BTXDetails btxReturn = helper.setRnaFiltersAndQuery(btx, chunkSize);
          BtxActionErrors errors = btxReturn.getActionErrors();
          if (!errors.empty()) {
            btxReturn.setActionForward(new BTXActionForward("fail"));
            return btxReturn;
          }
        }
        else {
          helper.removeRnaQuery();
        }

        // Check if there are no results (for either product type)
        if (!helper.isAnyResults()) {
          request.setAttribute("nomatchingitem", "true");
          btx.setActionForward(new BTXActionForward("fail"));
          return btx;
        }

        // set the initial results to Sample if there's data, RNA otherwise
        if (!helper.isAnyResults(ResultsHelper.PRODUCT_TYPE_SAMPLE)) {
          helper.setProductType(ResultsHelper.PRODUCT_TYPE_RNA);
        }
        else {
          helper.setProductType(ResultsHelper.PRODUCT_TYPE_SAMPLE);
        }

        //          helper.setChunk(0); // probably not necessary (default?)
        int prod = ResultsHelper.getProductBits(helper.getProductType());
        int tx = ResultsHelper.getTxBits(txType);

        SecurityInfo securityInfo = btx.getLoggedInUserSecurityInfo();
        ViewParams vp = new ViewParams(securityInfo, prod, ColumnPermissions.SCRN_SELECTION, tx);
        BTXDetailsGetSamples btxsamps = new BTXDetailsGetSamples();
        btxsamps.setDiagnosticTestFilterDto(ssForm.getDiagnosticTest().describeIntoDto(request, txType));
        btxsamps.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
        btxsamps.setViewParams(vp);
		btxsamps.setSortColumns(btx.getSortColumns());
        QueryForm theForm = (QueryForm)form;
        //if a results form definition to use for querying/rendering the details
        //was specified, pass it along to the code that will get the results
        btxsamps.setResultsFormDefinitionId(theForm.getResultsFormDefinitionId());
        btxsamps.setCurrentChunk(0);
        helper.updateHelpers(btxsamps);
        //set up the request attributes needed to display the view link
        request.setAttribute("resultsFormDefinitionId", btxsamps.getViewProfile().getResultsFormDefinitionId());
        request.setAttribute("resultsFormDefinitions", FormUtils.getResultsFormDefinitionsForUser(btx.getLoggedInUserSecurityInfo(), true));
      }

		saveQueryParameters(request);

      btx.setActionForward(new BTXActionForward("success"));
      return btx;
    }
    finally {
      if (_perfLog.isDebugEnabled()) {
        long elapsedTime = System.currentTimeMillis() - tStart;
        _perfLog.debug("    0: END   QueryPerformAction (" + elapsedTime + " ms)");
      }
    }
  }

	protected void saveQueryParameters(HttpServletRequest request)
	{
		final Map params = new HashMap();
		params.putAll(request.getParameterMap());
		params.remove("isDescSort");
		params.remove("sortedColumn");
		request.getSession().setAttribute(QUERY_REQUEST_PARAM_MAP, params);
	}

}