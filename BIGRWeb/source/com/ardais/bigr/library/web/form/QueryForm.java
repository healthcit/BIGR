package com.ardais.bigr.library.web.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ardais.bigr.filter.PersonalFilterService;
import com.ardais.bigr.filter.domain.PersonalFilter;
import com.ardais.bigr.filter.domain.SamplePersonalFilter;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.javabeans.DiagnosticTestFilterDto;
import com.ardais.bigr.library.btx.BTXDetailsGetSampleSummaryStart;
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
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.ardais.bigr.web.form.PopulatesRequestFromBtxDetails;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.ardais.bigr.dataImport.web.form.SampleForm;
import com.ardais.bigr.dataImport.web.form.CaseForm;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class QueryForm extends BigrActionForm implements PopulatesRequestFromBtxDetails {

    String _txType;
    QueryIdForm _idForm;
    QueryAppearanceForm _appearanceForm;
    QueryAttributesForm _attributesForm;
    QueryDiagnosisForm _diagnosisForm; 
    String _diagnosisStr;
    QueryTissueForm _tissueForm;
    QueryDiagnosticTestForm _testForm;
    QueryKcForm _kcForm;
	private QueryPersonalFilterForm filterForm;

    private String _resultsFormDefinitionId;
    private List _resultsFormDefinitions;
	private String _sortedColumn;
	private boolean _isDescSort;
    
    public static String CONTROL_OTHER ="Not_Drawing_Other";
    
    { 
        reset();
    }
    /**
     * broadcast the reset to my sub-forms
     * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
     */
    protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
      QueryFormDefinition[] queryForms = getKc().getQueryFormDefinitions();
      QueryFormDefinition qfd = getKc().getQueryFormDefinition();//MR9049
      
      reset();
      //the Attributes form must have it's doReset method called here, to populate the
      //logical repository choices that the user can make
      getAttributes().doReset(mapping, request);
      getKc().setQueryFormDefinitions(queryForms);
      getKc().setQueryFormDefinition(qfd);//MR9049
    }
    
    private void reset() {
        _idForm = new QueryIdForm();
        _appearanceForm = new QueryAppearanceForm();
        _attributesForm = new QueryAttributesForm();
        _diagnosisForm = new QueryDiagnosisForm();
        _tissueForm = new QueryTissueForm();
        _testForm = new QueryDiagnosticTestForm();
        _kcForm = new QueryKcForm();
		filterForm = new QueryPersonalFilterForm();
        _resultsFormDefinitionId = null;
        //don't refresh the results form definition list, as it won't have changed and this
        //way there is no need to find all of the places where it would need to be refreshed.
        //_resultsFormDefinitions = null;
    }

    /**
     * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
     */
    public ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
      ResultsHelper rh = ResultsHelper.get(getTxType(), request);
      getKc().setQueryFormDefinition(rh.getQueryForm());

        ActionErrors errors = new ActionErrors(); 

        appendErrors("id", getId().validate(mapping, request), errors);
        appendErrors("appearance", getAppearance().validate(mapping, request), errors);
        appendErrors("attributes", getAttributes().validate(mapping, request), errors);
        appendErrors("diagnosis", getDiagnosis().validate(mapping, request), errors);
        appendErrors("tissue", getTissue().validate(mapping, request), errors);
        appendErrors("test", getDiagnosticTest().validate(mapping, request), errors);
        appendErrors("kc", getKc().validate(mapping, request), errors);

        return errors;
    }

    /**
     * Returns the appearance.
     * @return BigrActionForm
     */
    public QueryAppearanceForm getAppearance() {
        return _appearanceForm;
    }

    /**
     * Returns the demographics.
     * @return BigrActionForm
     */
    public QueryAttributesForm getAttributes() {
        return _attributesForm;
    }

    /**
     * Returns the diagnosis.
     * @return BigrActionForm
     */
    public QueryDiagnosisForm getDiagnosis() {
        return _diagnosisForm;
    }

    /**
     * Returns the id.
     * @return BigrActionForm
     */
    public QueryIdForm getId() {
        return _idForm;
    }

    /**
     * Returns the tissue.
     * @return BigrActionForm
     */
    public QueryTissueForm getTissue() {
        return _tissueForm;
    }

    /**
     * @return BigrActionForm
     */
    public QueryKcForm getKc() {
        return _kcForm;
    }

    /**
     * Sets the appearance.
     * @param appearance The appearance to set
     */
    public void setAppearance(QueryAppearanceForm appearance) {
        _appearanceForm = appearance;
    }

    /**
     * Sets the demographics.
     * @param demographics The demographics to set
     */
    public void setAttributes(QueryAttributesForm attributes) {
        _attributesForm = attributes;
    }

    /**
     * Sets the diagnosis.
     * @param diagnosis The diagnosis to set
     */
    public void setDiagnosis(QueryDiagnosisForm diagnosis) {      
        _diagnosisForm = diagnosis;
   }
        
    /**
     * Sets the diagnosis.
     * @param diagnosis The diagnosis to set
     */
    public void setDiagnosis(String diagnosis) {      
        _diagnosisStr = diagnosis;
   }

    
    
    /**
     * Sets the id.
     * @param id The id to set
     */
    public void setId(QueryIdForm id) {
        _idForm = id;
    }

    /**
     * Sets the tissue.
     * @param tissue The tissue to set
     */
    public void setTissue(QueryTissueForm tissue) {
        _tissueForm = tissue;
    }

    /**
     * @param kc The KC to set
     */
    public void setKc(QueryKcForm kc) {
        _kcForm = kc;
    }

    public void setFilters(Map filters) {
        getId().setFilters(filters);
        getAttributes().setFilters(filters);
        getAppearance().setFilters(filters);
        getDiagnosis().setFilters(filters);
        getTissue().setFilters(filters);
        getDiagnosticTest().setFilters(filters);
        getKc().setFilters(filters);
    }

	public Map getFilters() {
        Map results = new HashMap();
        results.putAll(getId().getFilters());
        results.putAll(getAttributes().getFilters());
        results.putAll(getAppearance().getFilters());
        results.putAll(getDiagnosis().getFilters());
        results.putAll(getTissue().getFilters());
        results.putAll(getDiagnosticTest().getFilters());
        results.putAll(getKc().getFilters());
        return results;
	}
    /**
     * Returns the txType.
     * @return String
     */
    public String getTxType() {
        return _txType;
    }

    /**
     * Sets the txType.
     * @param txType The txType to set
     */
    public void setTxType(String txType) {
        _txType = txType;
    }

    /**
     * @return
     */
    public QueryDiagnosticTestForm getDiagnosticTest() {
      return _testForm;
    }

    /**
     * @param form
     */
    public void setDiagnosticTest(QueryDiagnosticTestForm form) {
      _testForm = form;
    }
        
    public void populateRequestFromBtxDetails(BTXDetails btxDetails, BigrActionMapping mapping,
                                              HttpServletRequest request) {
      
      BTXDetailsGetSampleSummaryStart btx = (BTXDetailsGetSampleSummaryStart) btxDetails;
      getKc().setQueryFormDefinitions(btx.getQueryFormDefinitions());
      setResultsFormDefinitionId(btx.getResultsFormDefinitionId());
      setResultsFormDefinitions(btx.getResultsFormDefinitions());
    
      populateFromSavedState(request);
     
      //SWP-1114, create a sampleForm bean in request to display tissue dropdown list
      if("TxSampSel".equalsIgnoreCase(getTxType()) || "TxRequestSamples".equalsIgnoreCase(getTxType())) {               
        SampleForm _sampleForm = new SampleForm();
        _sampleForm.setSampleData(new com.ardais.bigr.javabeans.SampleData());      
        CaseForm _caseForm = new CaseForm();
        
        
        request.setAttribute("caseForm", _caseForm);
        request.setAttribute("sampleForm", _sampleForm);
        request.setAttribute("CONTROL_OTHER", CONTROL_OTHER);
        
        
        
        //SWP-1114, if the user pick up a tissue from the dropdown list, add it to the current options
        if(btx.getSubActionType()!= null && btx.getSubActionType().equals("SelectFromDropdown")) {
         
          String[] selections = null;
          //create a hashmap
          Hashtable codeHash = null;
          
          if("libraryTissueOrigin".equals(request.getParameter("fieldId"))) {
        
          //get the selected value  
            selections = constructOptionsArray( this.getTissue().getTissueOrigin() , request.getParameter("selectedValue"));
          this.getTissue().setTissueOrigin(selections);
          //create a hashmap
          codeHash = new Hashtable();
          for(int i=0; i< selections.length; i++) {
            codeHash.put(selections[i], selections[i]);
            
          }
          
          //get the selected Option
          selections = constructOptionsArray( this.getTissue().getTissueOriginLabel() , request.getParameter("selectedLabel"));
          this.getTissue().setTissueOriginLabel(selections);
          
          }
          
          if("libraryTissueFinding".equals(request.getParameter("fieldId"))) {
            
              //get the selected value  
                selections = constructOptionsArray( this.getTissue().getTissueFinding() , request.getParameter("selectedValue"));
              this.getTissue().setTissueFinding(selections);
              //create a hashmap
              codeHash = new Hashtable();
              for(int i=0; i< selections.length; i++) {
                codeHash.put(selections[i], selections[i]);
                
              }
              
              //get the selected Option
              selections = constructOptionsArray( this.getTissue().getTissueFindingLabel() , request.getParameter("selectedLabel"));
              this.getTissue().setTissueFindingLabel(selections);
              
           }
          
          if("libraryCaseDiagnosis".equals(request.getParameter("fieldId"))) {
            
              //get the selected value  
              selections = constructOptionsArray( this.getDiagnosis().getCaseDiagnosis() , request.getParameter("selectedValue"));
              this.getDiagnosis().setCaseDiagnosis(selections);
              //create a hashmap
              codeHash = new Hashtable();
              for(int i=0; i< selections.length; i++) {
                codeHash.put(selections[i], selections[i]);
                
              }
              
              //get the selected Option
              selections = constructOptionsArray( this.getDiagnosis().getCaseDiagnosisLabel() , request.getParameter("selectedLabel"));
              this.getDiagnosis().setCaseDiagnosisLabel(selections);
              
           }
          
          //put codeHash in session
          request.getSession().setAttribute(request.getParameter("fieldId") + ".selected." + getTxType(), codeHash);
                           
          //put selections in session
          request.getSession().setAttribute(request.getParameter("fieldId") + ".label." + getTxType(), new ArrayList(Arrays.asList(selections)));
          
                    
          System.out.println("I am in selection ="+ request.getParameter("fieldId"));
       }
       
        
        
        
     }
    }
    
    private void populateFromSavedState(HttpServletRequest request) {
      ResultsHelper rh = ResultsHelper.get(getTxType(), request);
      synchronized (rh) {
        if (rh != null) {
          QueryKcForm kcForm = getKc();
          // Set the query form from the ResultsHelper in the KC form, only if the query form still
          // exists (i.e. it was not deleted).
          QueryFormDefinition form = rh.getQueryForm();
          if (form != null) {
            boolean exists = false;
            QueryFormDefinition[] forms = kcForm.getQueryFormDefinitions();
            for (int i = 0; i < forms.length; i++) {
              if (forms[i].getFormDefinitionId().equals(form.getFormDefinitionId())) {
                exists = true;
                break;
              }
            }
            if (!exists) {
              form = null;
              rh.setQueryForm(null);
            }
          }
          kcForm.setQueryFormDefinition(form);

			Map m;
			final String personalFilterId = getFilterForm().getFilterId();
			final boolean loadPersonalFilter = StringUtils.isNotBlank(personalFilterId);
			if (loadPersonalFilter)
			{
				m = loadPersonalFilter(personalFilterId);
			}
			else
			{
          		m = rh.getFilterMap();
			}
          //if the filter map is null or empty, this is the first time the user is 
          //going into sample selection.  If that's the case, for Ardais users we
          //need to default a couple of parameters as specified below:
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
          if (m == null || m.isEmpty()) {
            Map defaultFilterMap = kcForm.getFiltersFromQueryForm();
            SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
            String txType = request.getParameter("txType");
            if (securityInfo.isInRoleSystemOwner()) {
              if (ResultsHelper.TX_TYPE_SAMP_SEL.equals(txType) ||
                  ResultsHelper.TX_TYPE_SAMP_REQUEST.equals(txType)) {
                FilterRestrictedStatus filterRestricted = new FilterRestrictedStatus(Constants.RESTRICTED_U);
                defaultFilterMap.put(FilterConstants.KEY_RESTRICTED_FOR_DI,filterRestricted);
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
                  LogicalRepository lr = (LogicalRepository)iterator.next();
                  lrList.add(lr.getId());     
                }
                if (!lrList.isEmpty()) {
                  RepeatingSingleData singleData = new RepeatingSingleData((String[]) lrList.toArray(new String[0]));
                  RepeatingFilterData filterData = new RepeatingFilterData();
                  filterData.add(singleData);
                  FilterLogicalRepository filterLogicalRepository = new FilterLogicalRepository(filterData);
                  defaultFilterMap.put(FilterConstants.KEY_LOGICAL_REPOSITORY,filterLogicalRepository);
                }
              }
            }
            if (securityInfo.isInRoleSystemOwnerOrDi()) {
              if (ResultsHelper.TX_TYPE_SAMP_REQUEST.equals(txType)) {
                FilterLocalSamples filterLocalSamples = new FilterLocalSamples(securityInfo.getUserLocationId());
                defaultFilterMap.put(FilterConstants.KEY_LOCAL_SAMPLES_ONLY,filterLocalSamples);
              }
            }

            //if we've come up with any default filters, change the null/empty map
            //to our map
            if (!defaultFilterMap.isEmpty()) {
              m = defaultFilterMap;
            }
          }
          
          setFilters(m);

		  if (loadPersonalFilter)
		  {
		  	  saveDataToSession(request.getSession());
		  }

          String txType = getTxType();
          HttpSession session = request.getSession(false);
          DiagnosticTestFilterDto dto = (DiagnosticTestFilterDto)session.getAttribute(txType + Constants.DIAGNOSTIC_TEST_RESULT_FILTER);
          if (dto != null) {
            getDiagnosticTest().populateFromDto(dto);
          }
        }
      }
    }
    
    
    private String[] constructOptionsArray(String[] originals, String param) {
      
      String[] selections = null;
                 
      if(originals != null) {
       
         boolean isDuplicated = false;  
         
        //first, check if the newly-selected value is duplicated or not
        for (int j=0; j< originals.length; j++) {
          
          if(originals[j].equalsIgnoreCase(param))
            isDuplicated = true;  
        }
         
         if(isDuplicated)
           selections = originals;
         else {
           selections = new String[originals.length+1];
        
        for (int i=0; i < selections.length; i++) {
                      
          if(i < originals.length) {
          selections[i] = originals[i];
          }
          else selections[i] = param;
        }
      
       }  
        
      }
      else {
          selections = new String[]{param};
      }
      
      return selections;
    }
    
    public String getResultsFormDefinitionId() {
      return _resultsFormDefinitionId;
    }
         
    public void setResultsFormDefinitionId(String resultsFormDefinitionId) {
      _resultsFormDefinitionId = resultsFormDefinitionId;
    }
    
    public List getResultsFormDefinitions() {
      return _resultsFormDefinitions;
    }
    
    public void setResultsFormDefinitions(List resultsFormDefinitions) {
      _resultsFormDefinitions = resultsFormDefinitions;
    }

	public boolean getIsDescSort()
	{
		return _isDescSort;
	}

	public void setIsDescSort(boolean _isDescSort)
	{
		this._isDescSort = _isDescSort;
	}

	public String getSortedColumn()
	{
		return _sortedColumn;
	}

	public void setSortedColumn(String _sortedColumn)
	{
		this._sortedColumn = _sortedColumn;
	}

	public QueryPersonalFilterForm getFilterForm()
	{
		return filterForm;
	}

	public void setFilterForm(QueryPersonalFilterForm filterForm)
	{
		this.filterForm = filterForm;
	}

	protected Map loadPersonalFilter(String personalFilterId)
	{
		final PersonalFilter filter = PersonalFilterService.SINGLETON.getPersonalFilter(personalFilterId, SamplePersonalFilter.class);
		if (filter != null)
		{
			getFilterForm().setFilterName(filter.getName());
			final String serializedValue = filter.getValue();
			if (StringUtils.isNotBlank(serializedValue))
			{
				return (Map) new XStream().fromXML(serializedValue);
			}
		}
		return null;
	}

	protected void saveDataToSession(HttpSession session)
	{
		final String txType = getTxType();

		final QueryDiagnosisForm diagnosis = getDiagnosis();
		final QueryTissueForm tissue = getTissue();

		if (diagnosis.getCaseDiagnosis() != null && diagnosis.getCaseDiagnosisLabel() != null)
		{
			session.setAttribute("libraryCaseDiagnosis.selected." + txType, convertToHashTable(diagnosis.getCaseDiagnosis()));
			session.setAttribute("libraryCaseDiagnosis.label." + txType, new ArrayList(Arrays.asList(diagnosis.getCaseDiagnosisLabel())));
		}

		if (tissue.getTissueFinding() != null && tissue.getTissueFindingLabel() != null)
		{
			session.setAttribute("libraryTissueFinding.selected." + txType, convertToHashTable(tissue.getTissueFinding()));
			session.setAttribute("libraryTissueFinding.label." + txType, new ArrayList(Arrays.asList(tissue.getTissueFindingLabel())));
		}

		if (tissue.getTissueOrigin() != null && tissue.getTissueOriginLabel() != null)
		{
			session.setAttribute("libraryTissueOrigin.selected." + txType, convertToHashTable(tissue.getTissueOrigin()));
			session.setAttribute("libraryTissueOrigin.label." + txType, new ArrayList(Arrays.asList(tissue.getTissueOriginLabel())));
		}
	}

	protected Hashtable<String, String> convertToHashTable(String[] values)
	{
		final Hashtable<String, String> result = new Hashtable<String, String>();

		for (String value : values)
		{
			result.put(value, value);
		}

		return result;
	}
}
