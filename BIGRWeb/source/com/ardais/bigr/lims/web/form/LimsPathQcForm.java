package com.ardais.bigr.lims.web.form;

import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.lims.beans.LimsOperation;
import com.ardais.bigr.lims.beans.LimsOperationHome;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.query.LimsPathQcQuery;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LimsPathQcForm extends BigrActionForm {

  //Filter values
  private String[] _caseIdList;
  private String[] _sampleIdList;
  private String[] _slideIdList;
  private String[] _logicalRepositoryList;
  private LegalValueSet _logicalRepositoryOptions;
  private String[] _pathologistList;
  private Vector _pathologistSelectList;
  private String[] _caseDxList;
  private String[] _samplePathologyList;

  private String _startDate;
  private String _endDate;
  private String _sortValue;
  private String _sortOrderValue;
  private String _viewIncidentReport;
  private String _viewQCEdit;

  private String[] _includeList;
  private String _pvStatus;
  private String[] _markedSamples;

  private String _index;
  private List _pathQcSampleDetails;

  private String _targetSampleId;
  private String _sampleFunction;
  private String _reason;
  private String _reportMostRecentEval;

  private String _unPVedCount;
  private String _pvCount;
  private String _releasedCount;
  private String _pulledCount;
  private String _returnedSamplesCount;
  private String _qcPostedCount;
  private String _retrieveCounts;
  private String _searchCriteriaStyle = "inline"; //set default value as "inline"
  private String _updateAll; //submit button "updateAll"

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _includeList = null;
    _pvStatus = null;
    _markedSamples = null;
    _logicalRepositoryList = null;
    _pathologistList = null;
    _caseIdList = null;
    _sampleIdList = null;
    _slideIdList = null;
    _retrieveCounts = null;
    _updateAll = null;
    _reportMostRecentEval = null;

  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  public ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {

    ActionErrors errors = new ActionErrors();

    if (request.getParameter("calendar") != null) {
      return errors;
    }

    LimsPathQcQuery query =
      (LimsPathQcQuery) request.getSession().getAttribute(LimsPathQcQuery.LIMSPATHQCQUERY_KEY);

    if (query == null
      && (request.getParameter("filter") == null
        || !request.getParameter("filter").equals("Search"))) {
      errors.add("search", new ActionError("lims.error.pathQc.enterSearch"));
      this.setViewIncidentReport(null);
    }
    //Check is there any samples selected for PathQc, if updateAll button is 
    //pressed. See MR 5605 for more details.
    if (!ApiFunctions.isEmpty(getUpdateAll())) {
      boolean selected = false;
      Enumeration params = request.getParameterNames();
      while (params.hasMoreElements()) {
        String paramName = (String) params.nextElement();
        if (paramName.startsWith("QC")) {
          if (!ApiFunctions
            .safeEquals(request.getParameter(paramName), LimsConstants.LIMS_TX_NONE)) {
            selected = true;
            break;
          }
        }
      }
      //If no samples selected on current page and no samples selected 
      //in previous session for PathQC return error.

      if ((!selected)
        && ((query.getMarkedSampleFunctions() == null)
          || query.getMarkedSampleFunctions().size() == 0)) {
        errors.add("updateAll", new ActionError("lims.error.pathQc.noSelection"));
      }
    }

    return errors;
  }

  /**
   * Returns the caseIdList.
   * @return String[]
   */
  public String[] getCaseIdList() {

    if ((_caseIdList != null) && (_caseIdList.length > 0)) {
      // validate the ids here
      ValidateIds validId = new ValidateIds();
      String[] full_caseIdList = new String[_caseIdList.length];
      int count = 0; //counter to determine how many bad ids values
      for (int i = 0; i < _caseIdList.length; i++) {
        String validatedId = validId.validate(_caseIdList[i], ValidateIds.TYPESET_CASE, true);
        if (validatedId != null) {
          // move all the valid case ids into full_caseIdList
          full_caseIdList[i] = validatedId;
        }
        else
          count++;
      }

      // create the storage space for all non-null ids
      String[] non_null_caseIdList = new String[(_caseIdList.length - count)];
      int j = 0;
      for (int i = 0; i < _caseIdList.length; i++) {
        if (full_caseIdList[i] != null) {
          non_null_caseIdList[j] = full_caseIdList[i];
          j++;
        }
      }

      // set the ids to the non-null list...
      _caseIdList = non_null_caseIdList;
    }

    return _caseIdList;
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
   * Returns the pathologistList.
   * @return String[]
   */
  public String[] getPathologistList() {
    if (_pathologistList == null) {
      return new String[] {
      };
    }
    return _pathologistList;
  }

  /**
   * Returns the sampleIdList.
   * @return String[]
   */
  public String[] getSampleIdList() {
    return _sampleIdList;
  }

  /**
   * Returns the samplePathologyList.
   * @return String[]
   */
  public String[] getSamplePathologyList() {
    return _samplePathologyList;
  }

  /**
   * Returns the slideIdList.
   * @return String[]
   */
  public String[] getSlideIdList() {
    return _slideIdList;
  }

  /**
   * Sets the caseIdList.
   * @param caseIdList The caseIdList to set
   */
  public void setCaseIdList(String[] caseIdList) {
    _caseIdList = caseIdList;
  }

  /**
   * Sets the logicalRepositoryList.
   * @param logicalRepositoryList The logicalRepositoryList to set
   */
  public void setLogicalRepositoryList(String[] logicalRepositoryList) {
    _logicalRepositoryList = logicalRepositoryList;
  }

  /**
   * Sets the pathologistList.
   * @param pathologistList The pathologistList to set
   */
  public void setPathologistList(String[] pathologistList) {
    _pathologistList = pathologistList;
  }

  /**
   * Sets the sampleIdList.
   * @param sampleIdList The sampleIdList to set
   */
  public void setSampleIdList(String[] sampleIdList) {
    _sampleIdList = sampleIdList;
  }

  /**
   * Sets the samplePathologyList.
   * @param samplePathologyList The samplePathologyList to set
   */
  public void setSamplePathologyList(String[] samplePathologyList) {
    _samplePathologyList = samplePathologyList;
  }

  /**
   * Sets the slideIdList.
   * @param slideIdList The slideIdList to set
   */
  public void setSlideIdList(String[] slideIdList) {
    _slideIdList = slideIdList;
  }

  /**
   * Returns the caseDxList.
   * @return String[]
   */
  public String[] getCaseDxList() {
    return _caseDxList;
  }

  /**
   * Returns the endDate.
   * @return String
   */
  public String getEndDate() {
    return _endDate;
  }

  /**
   * Returns the startDate.
   * @return String
   */
  public String getStartDate() {
    return _startDate;
  }

  /**
   * Sets the caseDxList.
   * @param caseDxList The caseDxList to set
   */
  public void setCaseDxList(String[] caseDxList) {
    _caseDxList = caseDxList;
  }

  /**
   * Sets the endDate.
   * @param endDate The endDate to set
   */
  public void setEndDate(String endDate) {
    _endDate = endDate;
  }

  /**
   * Sets the startDate.
   * @param startDate The startDate to set
   */
  public void setStartDate(String startDate) {
    _startDate = startDate;
  }

  /**
   * Returns the options the user has for specifying logical repositories
   * @return
   */
  public LegalValueSet getLogicalRepositoryOptions() {
    return _logicalRepositoryOptions;
  }

  /**
   * Returns the pathologistSelectList.
   * @return Vector
   */
  public Vector getPathologistSelectList() {
    if (_pathologistSelectList == null) {

      try {
        LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
        LimsOperation operation = home.create();
        Vector tempVector = (Vector) operation.getPathologistList();
        tempVector.insertElementAt(LimsConstants.DEFAULT_ALL, 0);
        setPathologistSelectList(tempVector);
      }
      catch (RemoteException e) {
      }
      catch (NamingException e) {
      }
      catch (CreateException e) {
      }
      catch (ClassNotFoundException e) {
      }
    }

    return _pathologistSelectList;
  }

  /**
   * Set the choices the user has for selecting logical repositories
   * @param set
   */
  public void setLogicalRepositoryOptions(LegalValueSet logicalRepositoryOptions) {
    _logicalRepositoryOptions = logicalRepositoryOptions;
  }

  /**
   * Sets the pathologistSelectList.
   * @param pathologistSelectList The pathologistSelectList to set
   */
  public void setPathologistSelectList(Vector pathologistSelectList) {
    _pathologistSelectList = pathologistSelectList;
  }

  /**
   * Returns the includeList.
   * @return String[]
   */
  public String[] getIncludeList() {

    return _includeList;
  }

  /**
   * Sets the includeList.
   * @param includeList The includeList to set
   */
  public void setIncludeList(String[] includeList) {
    _includeList = includeList;
  }

  /**
   * Returns the markedSamples.
   * @return String[]
   */
  public String[] getMarkedSamples() {
    return _markedSamples;
  }

  /**
   * Sets the markedSamples.
   * @param markedSamples The markedSamples to set
   */
  public void setMarkedSamples(String[] markedSamples) {
    _markedSamples = markedSamples;
  }

  /**
   * Returns the sortOptions.
   * @return LegalValueSet
   */
  public LegalValueSet getSortOptions() {
    LegalValueSet options = new LegalValueSet();
    options.addLegalValue(
      LimsConstants.PATH_QC_SORT_REPORTEDDATE,
      LimsConstants.PATH_QC_SORT_REPORTEDDATE_LABEL);
    options.addLegalValue(
      LimsConstants.PATH_QC_SORT_QCSTATUS,
      LimsConstants.PATH_QC_SORT_QCSTATUS_LABEL);
    options.addLegalValue(
      LimsConstants.PATH_QC_SORT_CASEID,
      LimsConstants.PATH_QC_SORT_CASEID_LABEL);
    options.addLegalValue(
      LimsConstants.PATH_QC_SORT_SAMPLEID,
      LimsConstants.PATH_QC_SORT_SAMPLEID_LABEL);
    options.addLegalValue(
      LimsConstants.PATH_QC_SORT_PATHOLOGIST,
      LimsConstants.PATH_QC_SORT_PATHOLOGIST_LABEL);
    options.addLegalValue(
      LimsConstants.PATH_QC_SORT_PULLDATE,
      LimsConstants.PATH_QC_SORT_PULLDATE_LABEL);
    options.addLegalValue(
      LimsConstants.PATH_QC_SORT_CREATIONDATE,
      LimsConstants.PATH_QC_SORT_CREATIONDATE_LABEL);

    return options;
  }

  /**
   * Returns the sortOrderOptions.
   * @return LegalValueSet
   */
  public LegalValueSet getSortOrderOptions() {
    LegalValueSet options = new LegalValueSet();
    options.addLegalValue(LimsConstants.SORT_ORDER_ASC, LimsConstants.SORT_ORDER_ASC_LABEL);
    options.addLegalValue(LimsConstants.SORT_ORDER_DESC, LimsConstants.SORT_ORDER_DESC_LABEL);

    return options;
  }

  /**
   * Returns the sortOrderValue.
   * @return String
   */
  public String getSortOrderValue() {
    return _sortOrderValue;
  }

  /**
   * Returns the sortValue.
   * @return String
   */
  public String getSortValue() {
    return _sortValue;
  }

  /**
   * Sets the sortOrderValue.
   * @param sortOrderValue The sortOrderValue to set
   */
  public void setSortOrderValue(String sortOrderValue) {
    _sortOrderValue = sortOrderValue;
  }

  /**
   * Sets the sortValue.
   * @param sortValue The sortValue to set
   */
  public void setSortValue(String sortValue) {
    _sortValue = sortValue;
  }

  /**
   * Returns the viewIncidentReport.
   * @return String
   */
  public String getViewIncidentReport() {
    return _viewIncidentReport;
  }

  /**
   * Sets the viewIncidentReport.
   * @param viewIncidentReport The viewIncidentReport to set
   */
  public void setViewIncidentReport(String viewIncidentReport) {
    _viewIncidentReport = viewIncidentReport;
  }

  /**
   * Returns the viewQCEdit.
   * @return String
   */
  public String getViewQCEdit() {
    return _viewQCEdit;
  }

  /**
   * Sets the viewQCEdit.
   * @param viewQCEdit The viewQCEdit to set
   */
  public void setViewQCEdit(String viewQCEdit) {
    _viewQCEdit = viewQCEdit;
  }

  /**
   * Returns the index.
   * @return String
   */
  public String getIndex() {
    return _index;
  }

  /**
   * Sets the index.
   * @param index The index to set
   */
  public void setIndex(String index) {
    _index = index;
  }
  /**
   * Returns the pathQcSampleDetails.
   * @return List
   */
  public List getPathQcSampleDetails() {
    return _pathQcSampleDetails;
  }

  /**
   * Sets the pathQcSampleDetails.
   * @param pathQcSampleDetails The pathQcSampleDetails to set
   */
  public void setPathQcSampleDetails(List pathQcSampleDetails) {
    _pathQcSampleDetails = pathQcSampleDetails;
  }

  /**
   * Returns the reason.
   * @return String
   */
  public String getReason() {
    return _reason;
  }

  /**
   * Returns the sampleFunction.
   * @return String
   */
  public String getSampleFunction() {
    return _sampleFunction;
  }

  /**
   * Returns the targetSampleId.
   * @return String
   */
  public String getTargetSampleId() {
    return _targetSampleId;
  }

  /**
   * Sets the reason.
   * @param reason The reason to set
   */
  public void setReason(String reason) {
    _reason = reason;
  }

  /**
   * Sets the sampleFunction.
   * @param sampleFunction The sampleFunction to set
   */
  public void setSampleFunction(String sampleFunction) {
    _sampleFunction = sampleFunction;
  }

  /**
   * Sets the targetSampleId.
   * @param targetSampleId The targetSampleId to set
   */
  public void setTargetSampleId(String targetSampleId) {
    _targetSampleId = targetSampleId;
  }

  /**
   * Returns the unPVedCount.
   * @return String
   */
  public String getUnPVedCount() {
    return _unPVedCount;
  }

  /**
   * Returns the pulledCount.
   * @return String
   */
  public String getPulledCount() {
    return _pulledCount;
  }

  /**
   * Returns the pvCount.
   * @return String
   */
  public String getPvCount() {
    return _pvCount;
  }

  /**
   * Returns the releasedCount.
   * @return String
   */
  public String getReleasedCount() {
    return _releasedCount;
  }

  /**
   * Returns the returnedSamplesCount.
   * @return String
   */
  public String getReturnedSamplesCount() {
    return _returnedSamplesCount;
  }

  /**
   * Sets the unPVedCount.
   * @param unPVedCount The unPVedCount to set
   */
  public void setUnPVedCount(String unPVedCount) {
    _unPVedCount = unPVedCount;
  }

  /**
   * Sets the pulledCount.
   * @param pulledCount The pulledCount to set
   */
  public void setPulledCount(String pulledCount) {
    _pulledCount = pulledCount;
  }

  /**
   * Sets the pvCount.
   * @param pvCount The pvCount to set
   */
  public void setPvCount(String pvCount) {
    _pvCount = pvCount;
  }

  /**
   * Sets the releasedCount.
   * @param releasedCount The releasedCount to set
   */
  public void setReleasedCount(String releasedCount) {
    _releasedCount = releasedCount;
  }

  /**
   * Sets the returnedSamplesCount.
   * @param returnedSamplesCount The returnedSamplesCount to set
   */
  public void setReturnedSamplesCount(String returnedSamplesCount) {
    _returnedSamplesCount = returnedSamplesCount;
  }

  /**
   * Returns the pvStatus.
   * @return String
   */
  public String getPvStatus() {
    return _pvStatus;
  }

  /**
   * Sets the pvStatus.
   * @param pvStatus The pvStatus to set
   */
  public void setPvStatus(String pvStatus) {
    _pvStatus = pvStatus;
  }

  /**
   * Returns the qcPostedCount.
   * @return String
   */
  public String getQcPostedCount() {
    return _qcPostedCount;
  }

  /**
   * Sets the qcPostedCount.
   * @param qcPostedCount The qcPostedCount to set
   */
  public void setQcPostedCount(String qcPostedCount) {
    _qcPostedCount = qcPostedCount;
  }

  /**
   * Returns the retrieveCounts.
   * @return String
   */
  public String getRetrieveCounts() {
    return _retrieveCounts;
  }

  /**
   * Sets the retrieveCounts.
   * @param retrieveCounts The retrieveCounts to set
   */
  public void setRetrieveCounts(String retrieveCounts) {
    _retrieveCounts = retrieveCounts;
  }

  /**
   * Returns the searchCriteriaStyle.
   * @return String
   */
  public String getSearchCriteriaStyle() {
    return _searchCriteriaStyle;
  }

  /**
   * Sets the searchCriteriaStyle.
   * @param searchCriteriaStyle The searchCriteriaStyle to set
   */
  public void setSearchCriteriaStyle(String searchCriteriaStyle) {
    _searchCriteriaStyle = searchCriteriaStyle;
  }

  /**
   * Returns the updateAll.
   * @return String
   */
  public String getUpdateAll() {
    return _updateAll;
  }

  /**
   * Sets the updateAll.
   * @param updateAll The updateAll to set
   */
  public void setUpdateAll(String updateAll) {
    _updateAll = updateAll;
  }

  /**
   * @return
   */
  public String getReportMostRecentEval() {
    return _reportMostRecentEval;
  }

  /**
   * @param string
   */
  public void setReportMostRecentEval(String string) {
    _reportMostRecentEval = string;
  }

}
