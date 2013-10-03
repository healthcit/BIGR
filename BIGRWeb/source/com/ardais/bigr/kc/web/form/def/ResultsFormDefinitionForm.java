package com.ardais.bigr.kc.web.form.def;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.ardais.bigr.util.RoleUtils;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.form.def.BtxDetailsKcFormDefinition;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.library.web.column.SampleColumn;
import com.ardais.bigr.library.web.column.configuration.ColumnWriterList;
import com.ardais.bigr.query.ColumnConstants;
import com.ardais.bigr.query.ColumnDescriptor;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.kc.form.def.FormDefinitionTypes;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionElements;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionHostElement;

public class ResultsFormDefinitionForm extends BigrActionForm {
  
  public static String SEPERATOR = "::";
  public static String COMMA = ",";
  public static String ELEMENT_TYPE_HOST = "host";
  public static String ELEMENT_TYPE_DATA = "data";
  
  //the form definition currently being viewed/edited
  private BigrFormDefinition _formDefinition;
  
  //the kc form definition currently being viewed/edited
  private ResultsFormDefinition _kcFormDefinition;
  
  //the list of data form definitions for the user's account (provides
  //universe of KC data elements)
  private List _dataFormDefinitions;
  
  //boolean to indicate if changes were made but not saved (used in populateFromBtxDetails
  //when the transaction has failed)
  private boolean _unsavedChangesExist = false;
  
  //the list of existing results form definitions for the user
  private List _resultsFormDefinitions;
  
  private SecurityInfo _securityInfo = null;
  
  private List _bigrColumns;
  
  private String _selectedElementIds = null;
  
  private boolean _useSystemDefault = false;
  
  //boolean to indicate if the result definition page should be closed.  This will be
  //true if the user pressed the Ok button (and if there were changes made that those
  //changes were successfully saved as the anonymous view).
  private boolean _closePage = false;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _formDefinition = null;
    _kcFormDefinition = null;
    _unsavedChangesExist = false;
    _dataFormDefinitions = null;
    _resultsFormDefinitions = null;
    _securityInfo = WebUtils.getSecurityInfo(request);
    _bigrColumns = null;
    _selectedElementIds = null;
    _useSystemDefault = false;
    _closePage = false;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
     return null; 
  }
  
  public void describeIntoBtxDetails(BTXDetails btxDetails0,
                                     BigrActionMapping mapping,
                                     HttpServletRequest request) {
    //if isUseSystemDefault is true then this form should act as if the system default view is being
    //requested.  This is used in situations like a successful form deletion (and the system default
    //should now be selected), etc.
    if (isUseSystemDefault()) {
      setFormDefinition(SystemConfiguration.getDefaultResultsView());
    }
    super.describeIntoBtxDetails(btxDetails0, mapping, request);
    //if there are selected element ids present, turn them into data elements on
    //a KC form definition and set the XML for that form definition on the BIGR form
    String elementIds = getSelectedElementIds();
    //now that we've retrieved any selected element ids clear them out so we only process them once
    setSelectedElementIds(null);
    if (!ApiFunctions.isEmpty(elementIds)) {
      ResultsFormDefinition resultsFormDefinition = new ResultsFormDefinition();
      ResultsFormDefinitionElements elements = new ResultsFormDefinitionElements();
      StringTokenizer elementTokenizer = new StringTokenizer(elementIds, COMMA);
      int count = 1;
      while (elementTokenizer.hasMoreTokens()) {
        String elementId = elementTokenizer.nextToken().trim();
        StringTokenizer idTokenizer = new StringTokenizer(elementId, SEPERATOR);
        while (idTokenizer.hasMoreTokens()) {
          String type = idTokenizer.nextToken();
          String formId = idTokenizer.nextToken();
          String id = idTokenizer.nextToken();
          if (ELEMENT_TYPE_HOST.equalsIgnoreCase(type)) {
            ResultsFormDefinitionHostElement element = new ResultsFormDefinitionHostElement();
            element.setHostId(id);
            Tag tag = new Tag();
            tag.setType(TagTypes.ORDER);
            tag.setValue(count+"");
            element.addTag(tag);
            elements.addResultsHostElement(element);
          }
          else if (ELEMENT_TYPE_DATA.equalsIgnoreCase(type)) {
            ResultsFormDefinitionDataElement element = new ResultsFormDefinitionDataElement();
            element.setCui(id);
            Tag tag = new Tag();
            tag.setType(TagTypes.PARENT);
            tag.setValue(formId);
            element.addTag(tag);
            tag = new Tag();
            tag.setType(TagTypes.ORDER);
            tag.setValue(count+"");
            element.addTag(tag);
            elements.addResultsDataElement(element);
          }
          else {
            throw new ApiException("Unexpected element type " + type + " encountered");
          }
        }
        count = count + 1;
      }
      resultsFormDefinition.setResultsFormElements(elements);
      //have to set the name as well, since this isn't copied from the BIGR form (see
      //BigrFormDefinition.getKcFormDefinition() for details
      resultsFormDefinition.setName(getFormDefinition().getName());
      //store the ResultsFormDefinition here, so we can preserve what the user specified
      //and make use of that if there was a problem storing the form
      setKcFormDefinition(resultsFormDefinition);
      getFormDefinition().setFormDefinitionXml(resultsFormDefinition.toXml());
      //if the username or account of the BIGR form are empty, set them to be the values 
      //for the logged in user.  This will be the case when the user is using the system
      //default as a source
      if (ApiFunctions.isEmpty(getFormDefinition().getAccount())) {
        getFormDefinition().setAccount(_securityInfo.getAccount());
      }
      if (ApiFunctions.isEmpty(getFormDefinition().getUserName())) {
        getFormDefinition().setUserName(_securityInfo.getUsername());
      }
    }
  }
  
  public void populateFromBtxDetails(BTXDetails btxDetails) {
    super.populateFromBtxDetails(btxDetails);
    //if the transaction was successful, set the KC form definition on this form to be what was
    //returned.  Otherwise, set the flag that indicates unsaved changes exist
    if (btxDetails.isTransactionCompleted()) {
      setKcFormDefinition((ResultsFormDefinition)((BtxDetailsKcFormDefinition)btxDetails).getFormDefinition().getKcFormDefinition());
    }
    else {
      _unsavedChangesExist = true;
      //if the page was to be closed, do not close it because there was a problem
      _closePage = false;
    }
  }

  /**
   * @return Returns the dataFormDefinitions.
   */
  public List getDataFormDefinitions() {
    if (_dataFormDefinitions == null) {
      _dataFormDefinitions = new ArrayList();
    }
    return _dataFormDefinitions;
  }

  /**
   * @return Returns the dataFormDefinitions as a Map.
   */
  public Map getDataFormDefinitionsAsMap() {
    Map returnValue = new HashMap();
    Iterator iterator = getDataFormDefinitions().iterator();
    while (iterator.hasNext()) {
      BigrFormDefinition form = (BigrFormDefinition) iterator.next();
      returnValue.put(form.getFormDefinitionId(), form);
    }
    return returnValue;
  }
  
  /**
   * @return Returns the resultsFormDefinition.
   */
  public BigrFormDefinition getFormDefinition() {
    if (_formDefinition == null) {
      _formDefinition = new BigrFormDefinition();
      _formDefinition.setFormType(FormDefinitionTypes.RESULTS);
      _formDefinition.setAccount(_securityInfo.getAccount());
      _formDefinition.setUserName(_securityInfo.getUsername());
      _formDefinition.setEnabled(true);
    }
    return _formDefinition;
  }
  
  /**
   * @return Returns the resultsFormDefinitions.
   */
  public List getResultsFormDefinitions() {
    if (_resultsFormDefinitions == null) {
      _resultsFormDefinitions = new ArrayList();
    }
    return _resultsFormDefinitions;
  }
  
  /**
   * @param dataFormDefinitions The dataFormDefinitions to set.
   */
  public void setDataFormDefinitions(List dataFormDefinitions) {
    _dataFormDefinitions = dataFormDefinitions;
  }
  
  /**
   * @param resultsFormDefinition The resultsFormDefinition to set.
   */
  public void setFormDefinition(BigrFormDefinition formDefinition) {
    _formDefinition = formDefinition;
  }
  
  /**
   * @param resultsFormDefinitions The resultsFormDefinitions to set.
   */
  public void setResultsFormDefinitions(List resultsFormDefinitions) {
    _resultsFormDefinitions = resultsFormDefinitions;
  }
  
  public List getBigrColumns() {
    if (_bigrColumns == null) {
      Set availableColumns = ColumnConstants.getAccessibleResultsViewColumns(_securityInfo);
      _bigrColumns = new ArrayList();
      Iterator availIterator = availableColumns.iterator();
      while (availIterator.hasNext()) {
        _bigrColumns.add(new ColumnDescriptor((String)availIterator.next()));
      }
      //turn the list of column keys into a list of SampleColumn objects
      ViewParams vp = new ViewParams(_securityInfo, ColumnPermissions.ALL, ColumnPermissions.ALL, ColumnPermissions.ALL);
      _bigrColumns = (new ColumnWriterList(_bigrColumns, vp)).getColumns();
      //order the columns alphabetically
      Iterator columnIterator = _bigrColumns.iterator();
      Map columnMap = new HashMap();
      while (columnIterator.hasNext()) {
        SampleColumn column = (SampleColumn)columnIterator.next();
        columnMap.put(column.getRawHeaderText(), column);
      }
      Map sortedMap = Collections.synchronizedMap(new TreeMap(columnMap));
      Iterator keyIterator = sortedMap.keySet().iterator();
      List sortedList = new ArrayList();
      while (keyIterator.hasNext()) {
        sortedList.add(sortedMap.get(keyIterator.next()));
      }
      _bigrColumns = sortedList;
    }
    return _bigrColumns;
  }
  
  public Map getBigrColumnsAsMap() {
    Map returnValue = new HashMap();
    Iterator iterator = getBigrColumns().iterator();
    while (iterator.hasNext()) {
      SampleColumn column = (SampleColumn) iterator.next();
      returnValue.put(column.getKey(), column);
    }
    return returnValue;
  }
  
  /**
   * @return Returns the selectedElementIds.
   */
  public String getSelectedElementIds() {
    return _selectedElementIds;
  }
  
  /**
   * @param selectedElementIds The selectedElementIds to set.
   */
  public void setSelectedElementIds(String selectedElementIds) {
    _selectedElementIds = selectedElementIds;
  }
  
  /**
   * @return Returns the useSystemDefault.
   */
  public boolean isUseSystemDefault() {
    return _useSystemDefault;
  }
  
  /**
   * @param useSystemDefault The useSystemDefault to set.
   */
  public void setUseSystemDefault(boolean useSystemDefault) {
    _useSystemDefault = useSystemDefault;
  }
  
  /**
   * @return Returns the kcFormDefinition.
   */
  public ResultsFormDefinition getKcFormDefinition() {
    return _kcFormDefinition;
  }
  
  /**
   * @param kcFormDefinition The kcFormDefinition to set.
   */
  public void setKcFormDefinition(ResultsFormDefinition kcFormDefinition) {
    _kcFormDefinition = kcFormDefinition;
  }
  
  /**
   * @return Returns the unsavedChangesExist.
   */
  public boolean isUnsavedChangesExist() {
    return _unsavedChangesExist;
  }
  
  /**
   * @return Returns the closePage.
   */
  public boolean isClosePage() {
    return _closePage;
  }
  
  /**
   * @param closePage The closePage to set.
   */
  public void setClosePage(boolean closePage) {
    _closePage = closePage;
  }

	public List getAvailableRoles()
	{
		return RoleUtils.getAllRoles();
	}
}
