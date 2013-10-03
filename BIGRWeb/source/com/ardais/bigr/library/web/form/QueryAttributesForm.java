package com.ardais.bigr.library.web.form;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.library.web.helper.LibraryValuesHelper;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.RepeatingFilterData;
import com.ardais.bigr.query.filters.RepeatingSingleData;
import com.ardais.bigr.query.generator.FilterAgeAtCollection;
import com.ardais.bigr.query.generator.FilterAsciiReportContains;
import com.ardais.bigr.query.generator.FilterBmsYN;
import com.ardais.bigr.query.generator.FilterDateOfCollection;
import com.ardais.bigr.query.generator.FilterDateReceived;
import com.ardais.bigr.query.generator.FilterDonorInst;
import com.ardais.bigr.query.generator.FilterExcludeImplicitRNAFilters;
import com.ardais.bigr.query.generator.FilterGender;
import com.ardais.bigr.query.generator.FilterHoldSoldStatus;
import com.ardais.bigr.query.generator.FilterLinked;
import com.ardais.bigr.query.generator.FilterLocalSamples;
import com.ardais.bigr.query.generator.FilterLogicalRepository;
import com.ardais.bigr.query.generator.FilterPathVerifiedStatus;
import com.ardais.bigr.query.generator.FilterQcInProcess;
import com.ardais.bigr.query.generator.FilterQcInProcessNot;
import com.ardais.bigr.query.generator.FilterQcStatus;
import com.ardais.bigr.query.generator.FilterRestrictedStatus;
import com.ardais.bigr.query.generator.FilterSampleType;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.LogicalRepositoryUtils;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class QueryAttributesForm extends BigrActionForm {

  private String _gender;
  private String[] _sampleType;
  private String _allSampleTypes;
  private String _linked;
  private String _restricted;
  private String _pvStatus;
  private String _ageAtCollectionFrom;
  private String _ageAtCollectionTo;  
  private String _donorInst;
  private String _asciiReportContains;
  private String _holdSoldOption;  
  private String _recvDateStart;
  private String _recvDateEnd;
  private String[] _qcStatus;
  private String _qcInProcess;
  private String _excludeImplicitRNAFilters;
  private String _allLogicalRepositories;
  private String[] _logicalRepositoryList;
  //depending on the BMS Y/N value, the logical repository options
  //may consist of all, bms only, or non-bms only, so we need 3
  //legal value sets to hold those options.
  private LegalValueSet _logicalRepositoryOptionsAll;
  private LegalValueSet _logicalRepositoryOptionsBMS;
  private LegalValueSet _logicalRepositoryOptionsNonBMS;
  private String _bms_YN;
  private boolean _localOnly;
  private String _userId;
  private String _location;
  private String _dateOfCollectionFrom;
  private String _dateOfCollectionTo;
  
  public QueryAttributesForm() {
    reset();
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    reset();
    //populate the list of available Logical Repositories from which the user can choose
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
    List logicalRepositories;
    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
      logicalRepositories = LogicalRepositoryUtils.getAllLogicalRepositories();      
    }
    else {
      logicalRepositories = securityInfo.getLogicalRepositoriesByFullName();
    }
    // populate the userid and the location
    setUserId(securityInfo.getUsername());
    setLocation(securityInfo.getUserLocationId());
    LegalValueSet logicalRepositoryOptionsAll = new LegalValueSet();
    LegalValueSet logicalRepositoryOptionsBMS = new LegalValueSet();
    LegalValueSet logicalRepositoryOptionsNonBMS = new LegalValueSet();
    Iterator iterator = logicalRepositories.iterator();
    while (iterator.hasNext()) {
      LogicalRepository lr = (LogicalRepository)iterator.next();
      logicalRepositoryOptionsAll.addLegalValue(lr.getId(),lr.getFullName());
      if (FormLogic.DB_YES.equalsIgnoreCase(lr.getBmsYN())) { 
        logicalRepositoryOptionsBMS.addLegalValue(lr.getId(),lr.getFullName());     
      }
      else {
        logicalRepositoryOptionsNonBMS.addLegalValue(lr.getId(),lr.getFullName());     
      }
    }
    setLogicalRepositoryOptionsAll(logicalRepositoryOptionsAll);
    setLogicalRepositoryOptionsBMS(logicalRepositoryOptionsBMS);
    setLogicalRepositoryOptionsNonBMS(logicalRepositoryOptionsNonBMS);
    
    String txType = request.getParameter("txType");
    if (securityInfo.isInRoleSystemOwner() && ResultsHelper.TX_TYPE_SAMP_REQUEST.equals(txType)) {
      //default the BMS Y/N input to non-BMS
      _bms_YN = FormLogic.DB_YES;
    }
    //NOTE - there are a couple of default values for this form that are set
    //in StartAction.doPerform() and QueryClearAction.doPerform().  They are unique 
    //in that they are only set the first time a query of a given type 
    //(i.e. Sample Selection, RequestSamples, etc) is executed, or when the 
    //attributes form is cleared.  See those methods for details.
  }
  
  private void reset() {
    _ageAtCollectionFrom = Constants.ANY;
    _ageAtCollectionTo = Constants.ANY;
    _gender = Constants.ANY;
    _pvStatus = Constants.ANY;
    _asciiReportContains = null;
    _restricted = Constants.ANY;
    _localOnly = false;
    _linked = Constants.ANY;
    _sampleType = null;
    _allSampleTypes = "true";
    _donorInst = Constants.ANY;
    _holdSoldOption = Constants.HOLDSOLD_ALL_SAMPLES;
    _recvDateEnd = null;
    _recvDateStart = null;
    _qcStatus = new String[] {Constants.ANY};
    _qcInProcess = Constants.ANY;
    _excludeImplicitRNAFilters = null;
    //default the logical repository selection to "All"
    _allLogicalRepositories = "true";
    _logicalRepositoryList = null;
    _logicalRepositoryOptionsAll = null;
    _logicalRepositoryOptionsBMS = null;
    _logicalRepositoryOptionsNonBMS = null;
    _bms_YN = null;
    _localOnly = false;
    _userId = null;
    _dateOfCollectionFrom = null;
    _dateOfCollectionTo = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  public ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    //if the user hasn't specified either "All" or specific logical repositories return an error.
    ActionErrors errors = new ActionErrors();
    if (!"true".equalsIgnoreCase(_allLogicalRepositories) && _logicalRepositoryList == null) {
      errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.noValue", "Inventory Groups"));
    }
    if (!"true".equalsIgnoreCase(_allSampleTypes) && _sampleType == null) {
      errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.noValue", "Sample Types"));
    }
    return errors;
  }

  public Collection getDonorInstList() {
    return LibraryValuesHelper.getDonorAccounts();
  }

  /**
   * Returns the ageAtCollectionFrom.
   * @return String
   */
  public String getAgeAtCollectionFrom() {
    return _ageAtCollectionFrom;
  }

  public String[] getAgeAtCollectionFromList() {
    return LibraryValuesHelper.getAgeAtCollectionFromList();
  }

  /**
     * Returns the pathVerified status.
     * @return String
     */
  public String getPathVerifiedStatus() {
    return _pvStatus;
  }

  /**
  * Sets the pathVerified status.
  * @param pvStatus The path verified status to set
  */
  public void setPathVerifiedStatus(String pvStatus) {
    _pvStatus = pvStatus;
  }

  /**
   * Returns the ageAtCollectionTo.
   * @return String
   */
  public String getAgeAtCollectionTo() {
    return _ageAtCollectionTo;
  }

  public String[] getAgeAtCollectionToList() {
    return LibraryValuesHelper.getAgeAtCollectionToList();
  }

  /**
   * Returns the gender.
   * @return String[]
   */
  public String getGender() {
    return _gender;
  }

  /**
   * Sets dateOfCollectionFrom
   */
  public void setDateOfCollectionFrom(String dateOfCollectionFrom)
  {
	  _dateOfCollectionFrom = dateOfCollectionFrom;
  }
  
  /**
   * Sets dateOfCollectionTo
   */
  public void setDateOfCollectionTo(String dateOfCollectionTo)
  {
	  _dateOfCollectionTo = dateOfCollectionTo;
  }
  
  /**
   * Sets the ageAtCollectionFrom.
   * @param ageAtCollectionFrom The ageAtCollectionFrom to set
   */
  public void setAgeAtCollectionFrom(String ageAtCollectionFrom) {
    _ageAtCollectionFrom = ageAtCollectionFrom;
  }

  /**
   * Sets the ageAtCollectionTo.
   * @param ageAtCollectionTo The ageAtCollectionTo to set
   */
  public void setAgeAtCollectionTo(String ageAtCollectionTo) {
    _ageAtCollectionTo = ageAtCollectionTo;
  }

  /**
  * Returns the linked.
  * @return Boolean
  */
  public String getLinked() {
    return _linked;
  }

  /**
   * Returns the restricted.
   * @return Boolean
   */
  public String getRestricted() {
    return _restricted;
  }

  /**
   * Sets the linked.
   * @param linked The linked to set
   */
  public void setLinked(String linked) {
    _linked = linked;
  }

  /**
   * Sets the restricted.
   * @param restricted The restricted to set
   */
  public void setRestricted(String restricted) {
    _restricted = restricted;
  }

  /**
   * Sets the gender.
   * @param gender The gender to set
   */
  public void setGender(String gender) {
    _gender = gender;
  }

  /**
   * Sets dateOfCollectionFrom
   */
  public String getDateOfCollectionFrom()
  {
	  return _dateOfCollectionFrom;
  }
  
  /**
   * Sets dateOfCollectionTo
   */
  public String getDateOfCollectionTo()
  {
	  return _dateOfCollectionTo;
  }
  
  public Map getFilters() {
    Map results = new HashMap();
    Filter.addStringEqualToMap(new FilterGender(getGender()), results);
    Filter.addStringEqualToMap(new FilterLinked(getLinked()), results);
    Filter.addStringEqualToMap(new FilterDonorInst(getDonorInst()), results);
    Filter.addStringEqualToMap(new FilterPathVerifiedStatus(getPathVerifiedStatus()), results);

    if (!"true".equalsIgnoreCase(getAllSampleTypes())) {
      String[] sampleTypes = getSampleType();
      if ((sampleTypes != null) && (sampleTypes.length > 0)) {
         Filter.addToMap(new FilterSampleType(sampleTypes), results);
      }
    }

    
    if(StringUtils.isNotEmpty(getRecvDateStart()) || StringUtils.isNotEmpty(getRecvDateEnd())){
      Filter.addDateRangeToMap(new FilterDateReceived(getRecvDateStart(), getRecvDateEnd()), results);
    }
    
    if(StringUtils.isNotEmpty(getDateOfCollectionFrom()) || StringUtils.isNotEmpty(getDateOfCollectionTo())){
        Filter.addDateRangeToMap(new FilterDateOfCollection(getDateOfCollectionFrom(), getDateOfCollectionTo()), results);
      }

    if (StringUtils.isNotEmpty(getExcludeImplicitRNAFilters())) {
      Filter.addToMap(new FilterExcludeImplicitRNAFilters(), results);
    }

    // adds without Factory        
    String ageTo = getAgeAtCollectionTo();
    String ageFrom = getAgeAtCollectionFrom();
    if (!Constants.ANY.toUpperCase().equals(ageTo.toUpperCase())
      || !Constants.ANY.toUpperCase().equals(ageFrom.toUpperCase())) {
      ageTo = Constants.ANY.toUpperCase().equals(ageTo.toUpperCase()) ? "130" : ageTo;
      ageTo = "89+".equals(ageTo.toUpperCase()) ? "130" : ageTo;
      ageFrom = Constants.ANY.toUpperCase().equals(ageFrom.toUpperCase()) ? "0" : ageFrom;
      FilterAgeAtCollection.addToMap(ageFrom, ageTo, results);
    }

    if (!Filter.isEmpty(getRestricted())) {
      Filter restricted = new FilterRestrictedStatus(getRestricted());
      results.put(restricted.getKey(), restricted);
    }
    Filter.addStringContainsToMap(new FilterAsciiReportContains(getAsciiReportContains()), results);
    
    if (!Constants.HOLDSOLD_ALL_SAMPLES.equals(getHoldSoldOption())) {
      Filter.addStringEqualToMap(new FilterHoldSoldStatus(getHoldSoldOption()), results);
   }
   
   String[] qcs = getQcStatus();
   if (qcs!=null && qcs.length > 0  && !Constants.ANY.equals(qcs[0])) {
      Filter.addToMap(new FilterQcStatus(getQcStatus()), results);
   }
   
   String qcproc = getQcInProcess();
   if (qcproc!=null && qcproc.length() > 0) {
      if (qcproc.equals(Constants.QC_INPROCESS)) {
        Filter.addToMap(new FilterQcInProcess(), results);
      }
      else if (qcproc.equals(Constants.QC_INPROCESS_NOT)) {
        Filter.addToMap(new FilterQcInProcessNot(), results);
      }
      else {
        // ANY QC -- do not filter
      }
   }
   
   //only use individual logical repositories if the all checkbox is not checked
   if (!"true".equalsIgnoreCase(getAllLogicalRepositories())) {
     String[] logicalRepositories = getLogicalRepositoryList();
     if (logicalRepositories != null && 
         logicalRepositories.length > 0) {
           RepeatingSingleData singleData = new RepeatingSingleData(getLogicalRepositoryList());
           singleData.setFilterName("Inventory Group");
           RepeatingFilterData filterData = new RepeatingFilterData();
           filterData.add(singleData);
           Filter.addToMap(new FilterLogicalRepository(filterData), results);
     }
   }
   
   //MR 6913 -- add the query parameter for local samples, specifying the userid
   if (getLocalOnly()) {
     Filter.addToMap(new FilterLocalSamples(getLocation()), results);
   }

   Filter.addStringEqualToMap(new FilterBmsYN(getBms_YN()), results);   
        
   return results;
  }

  /**
   * Method setFilters.
   * @param filters
   */
  public void setFilters(Map m) {
    String gender = FilterConstants.KEY_GENDER;
    String sampleTypes = FilterConstants.KEY_SAMPLE_TYPE;
    String linked = FilterConstants.KEY_LINKED;
    String pved = FilterConstants.KEY_PATHVERIFIED_STATUS;
    String age = FilterConstants.KEY_AGEATCOLLECTION;
    String donorInst = FilterConstants.KEY_DONOR_INST;
    String restricted = FilterConstants.KEY_RESTRICTED_FOR_DI;
    String asciiReportContKey = FilterConstants.KEY_ASCIIREPORTCONTAINS;
    String holdSoldStatus = FilterConstants.KEY_HOLD_SOLD_STATUS;
    String dateReceived = FilterConstants.KEY_SAMPLE_DATE_RECEIVED;
    String dateCollected = FilterConstants.KEY_SAMPLE_DATE_COLLECTED;
    String qcStatuses = FilterConstants.KEY_QC_STATUS;
    String qcInProc = FilterConstants.KEY_QC_INPROCESS;
    String qcInProcNot = FilterConstants.KEY_QC_INPROCESS_NOT;
    String excludeImplicitRNAFilters = FilterConstants.KEY_EXCLUDE_IMPLICIT_RNA_FILTERS;
    
    if (m.containsKey(gender)) {
      FilterGender f = (FilterGender) m.get(gender);
      setGender(f.getFilterValue());
    }
    if (m.containsKey(sampleTypes)) {
      FilterSampleType f = (FilterSampleType) m.get(sampleTypes);
      setSampleType(f.getFilterValues());
    }
    if (m.containsKey(linked)) {
      FilterLinked f = (FilterLinked) m.get(linked);
      setLinked(f.getFilterValue());
    }
    if (m.containsKey(pved)) {
      FilterPathVerifiedStatus f = (FilterPathVerifiedStatus) m.get(pved);
      setPathVerifiedStatus(f.getFilterValue());
    }
    if (m.containsKey(age)) {
      FilterAgeAtCollection f = (FilterAgeAtCollection) m.get(age);
      setAgeAtCollectionFrom(f.getMinAsString());
      setAgeAtCollectionTo(f.getMaxAsString());
    }
    if (m.containsKey(restricted)) {
      FilterRestrictedStatus f = (FilterRestrictedStatus) m.get(restricted);
      setRestricted(f.getFilterValue());
    }
    if (m.containsKey(donorInst)) {
       FilterDonorInst f = (FilterDonorInst) m.get(donorInst);
       setDonorInst(f.getFilterValue());
    }
    if (m.containsKey(asciiReportContKey)) {
        FilterAsciiReportContains f = (FilterAsciiReportContains) m.get(asciiReportContKey);
        setAsciiReportContains(f.getPhrase());
    }
    if (m.containsKey(holdSoldStatus)) {
      FilterHoldSoldStatus f = (FilterHoldSoldStatus) m.get(holdSoldStatus);
      setHoldSoldOption(f.getFilterValue());
    }
    if (m.containsKey(dateReceived)) {
      FilterDateReceived f = (FilterDateReceived) m.get(dateReceived);
      setRecvDateStart(f.getStartForDisplay());
      setRecvDateEnd(f.getEndForDisplay());
    }
    if (m.containsKey(dateCollected)) {
        FilterDateOfCollection f = (FilterDateOfCollection) m.get(dateCollected);
        setDateOfCollectionFrom(f.getStartForDisplay());
        setDateOfCollectionTo(f.getEndForDisplay());
      }
    if (m.containsKey(qcStatuses)) {
      FilterQcStatus f = (FilterQcStatus) m.get(qcStatuses);
      setQcStatus(f.getFilterValues());
    }
    if (m.containsKey(qcInProc)) {
      setQcInProcess(Constants.QC_INPROCESS);
    }
    if (m.containsKey(qcInProcNot)) {
      setQcInProcess(Constants.QC_INPROCESS_NOT);
    }
    if (m.containsKey(excludeImplicitRNAFilters)) {
      setExcludeImplicitRNAFilters("true");
    }
    if (m.containsKey(FilterConstants.KEY_LOGICAL_REPOSITORY)) {
      FilterLogicalRepository f = (FilterLogicalRepository) m.get(FilterConstants.KEY_LOGICAL_REPOSITORY);
      setLogicalRepositoryList(f.getRepeatingValues().getParameterValues());
    }  
    if (m.containsKey(FilterConstants.KEY_LOCAL_SAMPLES_ONLY)) {
      //if the filter exists, set the value to be true.  This filter is a little different
      //in that the value isn't "true" or "false", but rather it holds a location code.
      FilterLocalSamples f = (FilterLocalSamples) m.get(FilterConstants.KEY_LOCAL_SAMPLES_ONLY);
      // MR 8047 -- presence of null indicates that the user has explicitly unchecked the option.
      if (f.getFilterValue() == null) {
        setlocalOnly(false);
      }
      else {
        setlocalOnly(true);
      }
    }       
    if (m.containsKey(FilterConstants.KEY_BMS_YN)) {
      FilterBmsYN f = (FilterBmsYN) m.get(FilterConstants.KEY_BMS_YN);
      setBms_YN(f.getFilterValue());
    }
  }
  /**
   * Returns the donorInst.
   * @return String
   */
  public String getDonorInst() {
    return _donorInst;
  }

  /**
   * Sets the donorInst.
   * @param donorInst The donorInst to set
   */
  public void setDonorInst(String donorInst) {
    _donorInst = donorInst;
  }
  /**
   * Returns the rawPathNotesContains.
   * @return String
   */
  public String getAsciiReportContains() {
    return _asciiReportContains;
  }

  /**
   * Sets the rawPathNotesContains.
   * @param rawPathNotesContains The rawPathNotesContains to set
   */
  public void setAsciiReportContains(String rawPathNotesContains) {
    _asciiReportContains = rawPathNotesContains;
  }

  /**
   * Returns the holdSoldOption.
   * @return String
   */
  public String getHoldSoldOption() {
    return _holdSoldOption;
  }

  /**
   * Sets the holdSoldOption.
   * @param holdSoldOption The holdSoldOption to set
   */
  public void setHoldSoldOption(String holdSoldOption) {
    _holdSoldOption = holdSoldOption;
  }

  /**
   * Returns the recvDateEnd.
   * @return String
   */
  public String getRecvDateEnd() {
    return _recvDateEnd;
  }

  /**
   * Returns the recvDateStart.
   * @return String
   */
  public String getRecvDateStart() {
    return _recvDateStart;
  }

  /**
   * Sets the recvDateEnd.
   * @param recvDateEnd The recvDateEnd to set
   */
  public void setRecvDateEnd(String recvDateEnd) {
    _recvDateEnd = recvDateEnd;
  }

  /**
   * Sets the recvDateStart.
   * @param recvDateStart The recvDateStart to set
   */
  public void setRecvDateStart(String recvDateStart) {
    _recvDateStart = recvDateStart;
  }

  /**
   * Returns the qcStatus.
   * @return String[]
   */
  public String[] getQcStatus() {
    return _qcStatus;
  }

  /**
   * Sets the qcStatus.
   * @param qcStatus The qcStatus to set
   */
  public void setQcStatus(String[] qcStatus) {
    _qcStatus = qcStatus;
  }

  /**
   * Returns the qcInprocess flag (any, inprocess, notinprocess).
   * @return String
   */
  public String getQcInProcess() {
    return _qcInProcess;
  }

  /**
   * Sets the qcInprocess.
   * @param qcInprocess The qcInprocess to set
   */
  public void setQcInProcess(String qcInprocess) {
    _qcInProcess = qcInprocess;
  }

  /**
   * Returns the excludeImplicitRNAFilters.
   * @return String
   */
  public String getExcludeImplicitRNAFilters() {
    return _excludeImplicitRNAFilters;
  }

  /**
   * Sets the excludeImplicitRNAFilters.
   * @param excludeImplicitRNAFilters The excludeImplicitRNAFilters to set
   */
  public void setExcludeImplicitRNAFilters(String excludeImplicitRNAFilters) {
    _excludeImplicitRNAFilters = excludeImplicitRNAFilters;
  }

  /**
   * Returns the logicalRepositoryList.
   * @return String[]
   */
  public String[] getLogicalRepositoryList() {
    if (_logicalRepositoryList == null) {
      return new String[] {
      };
    }
    return _logicalRepositoryList;
  }

  /**
   * Sets the logicalRepositoryList.
   * @param logicalRepositoryList The logicalRepositoryList to set
   */
  public void setLogicalRepositoryList(String[] logicalRepositoryList) {
    _logicalRepositoryList = logicalRepositoryList;
    if (_logicalRepositoryList != null && _logicalRepositoryList.length > 0) {
      setAllLogicalRepositories("false");
    }
  }

  /**
   * @return
   */
  public String getBms_YN() {
    return _bms_YN;
  }

  /**
   * @param string
   */
  public void setBms_YN(String string) {
    _bms_YN = string;
  }

  /**
   * @return
   */
  public String getAllLogicalRepositories() {
    return _allLogicalRepositories;
  }

  /**
   * @param string
   */
  public void setAllLogicalRepositories(String string) {
    _allLogicalRepositories = string;
  }

  /**
   * @return
   */
  public LegalValueSet getLogicalRepositoryOptionsAll() {
    return _logicalRepositoryOptionsAll;
  }

  /**
   * @return
   */
  public LegalValueSet getLogicalRepositoryOptionsBMS() {
    return _logicalRepositoryOptionsBMS;
  }

  /**
   * @return
   */
  public LegalValueSet getLogicalRepositoryOptionsNonBMS() {
    return _logicalRepositoryOptionsNonBMS;
  }

  /**
   * @param set
   */
  public void setLogicalRepositoryOptionsAll(LegalValueSet set) {
    _logicalRepositoryOptionsAll = set;
  }

  /**
   * @param set
   */
  public void setLogicalRepositoryOptionsBMS(LegalValueSet set) {
    _logicalRepositoryOptionsBMS = set;
  }

  /**
   * @param set
   */
  public void setLogicalRepositoryOptionsNonBMS(LegalValueSet set) {
    _logicalRepositoryOptionsNonBMS = set;
  }
  
  /**
     * Returns the local only samples.
     * @return Boolean
     */
  public boolean getLocalOnly() {
    return _localOnly;
  }

  /**
  * Sets the local only samples.
  * @param localOnly
  */
  public void setlocalOnly(boolean localOnly) {
    _localOnly = localOnly;
  }
  
  /**
     * Returns the local only samples.
     * @return Boolean
     */
  public String getUserId() {
    return _userId;
  }

  /**
  * Sets the local only samples.
  * @param localOnly
  */
  public void setUserId(String userId) {
    _userId = userId;
  }
  
  /**
     * Returns the local only samples.
     * @return Boolean
     */
  public String getLocation() {
    return _location;
  }

  /**
  * Sets the local only samples.
  * @param localOnly
  */
  public void setLocation(String location) {
    _location = location;
  }

  public String[] getSampleType() {
    return (_sampleType == null) ? new String[] {} : _sampleType;
  }

  public void setSampleType(String[] types) {
    if (types != null) {
      if (types.length == getSampleTypeList().getCount()) {
        setAllSampleTypes("true");
        _sampleType = null;
      }
      else if (types.length > 0){
        setAllSampleTypes("false");
        _sampleType = types;
      }
    }
    else {
      _sampleType = types;
    }
  }

  public String getAllSampleTypes() {
    return _allSampleTypes;
  }

  public void setAllSampleTypes(String string) {
    _allSampleTypes = string;
  }

  public LegalValueSet getSampleTypeList() {
      return SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_SAMPLE_TYPES).toLegalValueSet();
  }
}
