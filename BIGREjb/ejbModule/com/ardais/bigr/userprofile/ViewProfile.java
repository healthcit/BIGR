package com.ardais.bigr.userprofile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.form.def.BigrFormDefinitionQueryCriteria;
import com.ardais.bigr.kc.form.def.BtxDetailsKcFormDefinitionLookup;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.query.ColumnConstants;
import com.ardais.bigr.query.ColumnDescriptor;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.def.FormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionHostElement;

/**
 * The results view to use for a view of data. This is a back-end object and should be used for
 * communicating among back-end components. To expose this sort of information to the front end, use
 * ColumnList.
 */
public class ViewProfile implements UserProfileTopic, Serializable {

  /// ===================================================

  /* pkg private */
  static final String XML_TOPIC_NAME = "viewProfile";

  static final String XML_FORM_DEF_ID_NAME = "resultsFormDefinitionId";

  static final String XML_NAME_NAME = "name";

  private String _resultsFormDefinitionId = null;

  private BigrFormDefinition _resultsFormDefinition = null;

  private boolean _needToRetrieveFormDefinition = true;

  private SecurityInfo _securityInfo = null;

  /**
   * Constructor for ProfileTopic.
   */
  public ViewProfile(SecurityInfo securityInfo) {
    _securityInfo = securityInfo;
  }

  /**
   * @see com.ardais.bigr.userprofile.UserProfileTopic#addToUserProfileTopics(java.lang.String,
   *      com.ardais.bigr.userprofile.UserProfileTopics)
   */
  public void addToUserProfileTopics(String name, UserProfileTopics topics) {
    topics.addViewProfile(name, this);
  }

  public List toColumnDescriptors() {
    return toColumnDescriptors(false);
  }

  public List toColumnDescriptors(boolean useOnlyColumnsFromResultsForm) {
    List returnValue = new ArrayList();
    //hack to handle RNA based screens
    if (new Integer(ColumnPermissions.PROD_RNA).toString().equalsIgnoreCase(
        getResultsFormDefinitionId())) {
      List keys = ApiFunctions.safeToList(ColumnConstants.standardRnaColumns());
      // Filter out columns that may not exist any more, and internal columns for external users.
      keys.retainAll(ColumnConstants.allColumns());
      if (_securityInfo.isInRoleCustomerOrDi()) {
        keys.retainAll(ColumnConstants.externallyVisibleColumns());
      }
      Iterator keyIterator = keys.iterator();
      while (keyIterator.hasNext()) {
        returnValue.add(new ColumnDescriptor((String) keyIterator.next()));
      }
      return returnValue;
    }
    if (new Integer(ColumnPermissions.PROD_GENERIC).toString().equalsIgnoreCase(
        getResultsFormDefinitionId())) {
      List keys = ApiFunctions.safeToList(ColumnConstants.standardGenericColumns());
      // Filter out columns that may not exist any more, and internal columns for external users.
      keys.retainAll(ColumnConstants.allColumns());
      if (_securityInfo.isInRoleCustomerOrDi()) {
        keys.retainAll(ColumnConstants.externallyVisibleColumns());
      }
      Iterator keyIterator = keys.iterator();
      while (keyIterator.hasNext()) {
        returnValue.add(new ColumnDescriptor((String) keyIterator.next()));
      }
      return returnValue;
    }
    //end of hack to handle RNA based screens

    //three columns that should always be shown are row index, select and sample action. Because 
    //they should always be shown we don't allow the user to manipulate them in their form 
    //definitions, so we always add them to the column list
    //add them to the column list unless the flag is set to not do so
    if (!useOnlyColumnsFromResultsForm) {
      returnValue.add(new ColumnDescriptor(ColumnConstants._ROW_INDEX));
      returnValue.add(new ColumnDescriptor(ColumnConstants._SELECT_BOX_WITH_PV));
      returnValue.add(new ColumnDescriptor(ColumnConstants._SAMPLE_ACTION));
    }
    //now add a column for each element on the form, making sure to respect ordering
    //first, order the elements appropriately. Although it should never happen handle the
    //situation where two elements have identical order values by maintaining a map
    //of lists, instead of a map of elements, so that if two elements have the same order they can
    //be placed in the same list instead of replacing an existing element with a subsequent
    // element.
    Map elementMap = new HashMap();
    ResultsFormDefinition resultsFormDefinition = (ResultsFormDefinition) getResultsFormDefinition()
        .getKcFormDefinition();
    //first, add the host elements to the map
    ResultsFormDefinitionHostElement[] hostElements = resultsFormDefinition
        .getResultsHostElements();
    for (int hostElementIndex = 0; hostElementIndex < hostElements.length; hostElementIndex++) {
      ResultsFormDefinitionHostElement hostElement = hostElements[hostElementIndex];
      Tag[] tags = hostElement.getTags();
      for (int tagIndex = 0; tagIndex < tags.length; tagIndex++) {
        Tag tag = tags[tagIndex];
        if (TagTypes.ORDER.equalsIgnoreCase(tag.getType())) {
          String orderValue = tag.getValue();
          //turn the order value into an Integer. If we used the string value as the
          //key a column with an order of "10" would appear between a column with an
          //order of "1" and a column with an order of "2".
          Integer order = new Integer(orderValue);
          List elements = (List) elementMap.get(order);
          if (elements == null) {
            elements = new ArrayList();
          }
          elements.add(new ColumnDescriptor(hostElement.getHostId()));
          elementMap.put(order, elements);
        }
      }
    }
    //now add the data (KC) elements, if any, to the map
    ResultsFormDefinitionDataElement[] dataElements = resultsFormDefinition
        .getResultsDataElements();
    if (dataElements.length > 0) {
      //first, get all the data form definitions from which the data elements were taken and put
      // them
      //into a hashmap
      Set formDefinitionIds = new HashSet();
      Map formDefinitionMap = new HashMap();
      for (int dataElementIndex = 0; dataElementIndex < dataElements.length; dataElementIndex++) {
        ResultsFormDefinitionDataElement dataElement = dataElements[dataElementIndex];
        Tag[] tags = dataElement.getTags();
        for (int tagIndex = 0; tagIndex < tags.length; tagIndex++) {
          Tag tag = tags[tagIndex];
          if (TagTypes.PARENT.equalsIgnoreCase(tag.getType())) {
            formDefinitionIds.add(tag.getValue());
          }
        }
      }
      BtxDetailsKcFormDefinitionLookup btxDetails = new BtxDetailsKcFormDefinitionLookup();
      BigrFormDefinitionQueryCriteria criteria = new BigrFormDefinitionQueryCriteria();
      Iterator idIterator = formDefinitionIds.iterator();
      while (idIterator.hasNext()) {
        criteria.addFormDefinitionId((String) idIterator.next());
      }
      btxDetails.setQueryCriteria(criteria);
      btxDetails.setLoggedInUserSecurityInfo(_securityInfo);
      btxDetails.setBeginTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
      btxDetails = (BtxDetailsKcFormDefinitionLookup) Btx
          .perform(btxDetails, "kc_form_defs_lookup");
      BigrFormDefinition[] formDefinitions = btxDetails.getFormDefinitions();
      for (int i = 0; i < formDefinitions.length; i++) {
        BigrFormDefinition formDef = formDefinitions[i];
        formDefinitionMap.put(formDef.getFormDefinitionId(), formDef);
      }
      //now process the data elements
      for (int dataElementIndex = 0; dataElementIndex < dataElements.length; dataElementIndex++) {
        ResultsFormDefinitionDataElement dataElement = dataElements[dataElementIndex];
        Tag[] tags = dataElement.getTags();
        String orderValue = null;
        String parent = null;
        for (int tagIndex = 0; tagIndex < tags.length; tagIndex++) {
          Tag tag = tags[tagIndex];
          if (TagTypes.ORDER.equalsIgnoreCase(tag.getType())) {
            orderValue = tag.getValue();
          }
          else if (TagTypes.PARENT.equalsIgnoreCase(tag.getType())) {
            parent = tag.getValue();
          }
        }
        //turn the order value into an Integer. If we used the string value as the
        //key a column with an order of "10" would appear between a column with an
        //order of "1" and a column with an order of "2".
        Integer order = new Integer(orderValue);
        List elements = (List) elementMap.get(order);
        if (elements == null) {
          elements = new ArrayList();
        }
        String cui = dataElement.getCui();
        BigrFormDefinition formDefinition = (BigrFormDefinition) formDefinitionMap.get(parent);
        String objectType = formDefinition.getObjectType();
        FormDefinitionDataElement fdDataElement = formDefinition.getKcFormDefinition()
            .getDataElement(cui);
        String displayName = fdDataElement.getDisplayName();
        if (ApiFunctions.isEmpty(displayName)) {
          displayName = DetService.SINGLETON.getDataElementTaxonomy().getCuiDescription(
              fdDataElement.getCui());
        }
        DetDataElement detDataElement = DetService.SINGLETON.getDataElementTaxonomy()
            .getDataElement(fdDataElement.getCui());
        String dataType = detDataElement.getDatatype();
        //TODO - better way of determining width?
        int width = 75;
        if (detDataElement.isMultivalued()) {
          width = 120;
        }
        elements.add(new ColumnDescriptor(cui, objectType, dataType, displayName, width));
        elementMap.put(order, elements);
      }
    }

    //now that the elements are mapped by order, add them to the column list
    TreeMap sortedElements = new TreeMap(elementMap);
    Iterator listIterator = sortedElements.values().iterator();
    while (listIterator.hasNext()) {
      Iterator elementIterator = ((List) listIterator.next()).iterator();
      while (elementIterator.hasNext()) {
        returnValue.add(elementIterator.next());
      }
    }
    return returnValue;
  }

  public String toXml(String name) {
    String returnValue = "";

    //if the view is the system default, don't return anything. That way the
    //only things that will be written to the user profile will be non-default values
    if (!Constants.DEFAULT_RESULTS_VIEW_ID.equalsIgnoreCase(getResultsFormDefinitionId())) {

      StringBuffer result = new StringBuffer(512);
      Map attrs = new HashMap();
      attrs.put(XML_NAME_NAME, name);
      attrs.put(XML_FORM_DEF_ID_NAME, getResultsFormDefinitionId());

      UserProfileTopicSerializer.appendStartTagWithAttrs(XML_TOPIC_NAME, attrs, result);
      UserProfileTopicSerializer.appendEndTag(XML_TOPIC_NAME, result);
      result.append('\n');
      returnValue = result.toString();
    }

    return returnValue;
  }

  public boolean equals(Object o) {
    boolean returnValue = false;
    if (o instanceof ViewProfile) {
      ViewProfile vp = (ViewProfile) o;
      returnValue = getResultsFormDefinitionId().equals(vp.getResultsFormDefinitionId());
    }
    return returnValue;
  }

  /**
   * @return Returns the resultsFormDefinitionId.
   */
  public String getResultsFormDefinitionId() {
    return _resultsFormDefinitionId;
  }

  /**
   * @param resultsFormDefinitionId The resultsFormDefinitionId to set.
   */
  public void setResultsFormDefinitionId(String resultsFormDefinitionId) {
    _resultsFormDefinitionId = resultsFormDefinitionId;
    
    // This used to immediately retrieve the results form definition and set _resultsFormDefinition
    // to it, but in some cases this was a performance problem when nobody ever needed
    // the actual for definition object.  So we've changed things to lazily initialize
    // _resultsFormDefinition.
    
    _needToRetrieveFormDefinition = true;
    _resultsFormDefinition = null;
  }
  
  public void setResultsFormDefinition(BigrFormDefinition resultsFormDefinition) {
    _needToRetrieveFormDefinition = false;
    _resultsFormDefinition = resultsFormDefinition;
  }

  public BigrFormDefinition getResultsFormDefinition() {
    if (_needToRetrieveFormDefinition) {
      if (!ApiFunctions.isEmpty(_resultsFormDefinitionId)) {
        //hack to handle RNA based screens
        if (new Integer(ColumnPermissions.PROD_RNA).toString().equalsIgnoreCase(
            _resultsFormDefinitionId)
            || new Integer(ColumnPermissions.PROD_GENERIC).toString().equalsIgnoreCase(
                _resultsFormDefinitionId)) {
          _resultsFormDefinition = null;
        }
        else {
          _resultsFormDefinition = FormUtils.getFormDefinition(_securityInfo,
              _resultsFormDefinitionId);
        }
      }
      else {
        _resultsFormDefinition = null;
      }
      _needToRetrieveFormDefinition = false;
    }

    return _resultsFormDefinition;
  }

}
