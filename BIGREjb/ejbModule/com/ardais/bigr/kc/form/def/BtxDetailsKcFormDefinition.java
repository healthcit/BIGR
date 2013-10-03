package com.ardais.bigr.kc.form.def;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.kc.form.def.FormDefinitionTypes;
import org.springframework.util.StringUtils;

/**
 * Abstract base class that holds the details of transactions that manipulate and retreive 
 * KnowledgeCapture form definitions.  This BTX details supports the performer methods in
 * {@link BtxPerformerKcFormDefinitionOperations}. 
 * 
 * <p>The following are input fields.  Not all are used in each performer method.  See the
 * comments in the performer methods in <code>BtxPerformerKcFormDefinitionOperations</code>
 * to see which input fields are used.
 * <ul>
 * <li>{@link #setFormDefinition(BigrFormDefinition) form definition}: A form definition.
 *     One or more of its fields will be set depending upon the transaction.</li>
 * <li>{@link #setQueryCriteria(BigrFormDefinitionQueryCriteria) query criteria}: Query criteria 
 *     that indicates the form definition(s) that should be returned after the transaction is
 *     performed, if any.</li>
 * </ul>
 * <p>The following are output fields.  Not all are returned from each performer method.  See the
 * comments in the performer methods in <code>BtxPerformerKcFormDefinitionOperations</code>
 * to see which output fields are returned.
 * <ul>
 * <li>{@link #getFormDefinition() form definition}: A single form definition.</li>
 * <li>{@link #getFormDefinitions() an array of form definitions}:  An array of form definitions
 * is returned if a query criteria were set as input.</li>
 * <li>{@link #getAccountList() account list}: A list of the accounts in the system.  This is
 * always returned, and is useful for the Form Designer transaction which displays a list
 * of accounts.</li>
 * </ul>
 * 
 */
public abstract class BtxDetailsKcFormDefinition extends BTXDetails {

  private List _formDefinitions;
  private List _dataFormDefinitions;
  private List _queryFormDefinitions;
  private List _resultsFormDefinitions;
  private BigrFormDefinition _formDefinition;
  private LegalValueSet _accountList;
  private BigrFormDefinitionQueryCriteria _queryCriteria;
  private boolean _confirmedRemove;
  private String _confirmRemoveMessage;
  

  /**
   * Creates a new <code>BtxDetailsKcFormDefinition</code>.
   */
  public BtxDetailsKcFormDefinition() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    return null;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    String type = _formDefinition.getFormType();
    StringBuffer sb = new StringBuffer();

    sb.append(Escaper.htmlEscapeAndPreserveWhitespace(type));
    
    sb.append(" form named \"");
    sb.append(Escaper.htmlEscapeAndPreserveWhitespace(_formDefinition.getName()));
    sb.append("\"");

    if (type.equals(FormDefinitionTypes.DATA)) {
      sb.append(" for object type ");
      sb.append(Escaper.htmlEscapeAndPreserveWhitespace(_formDefinition.getObjectType()));      
    }

    sb.append(" in account ");
    //do not escape account, since prepareLink does that.
    sb.append(IcpUtils.prepareLink(_formDefinition.getAccount(), getLoggedInUserSecurityInfo()));
    
    sb.append(".  This form was marked ");
    if(_formDefinition.isEnabled()) {
      sb.append("enabled.");
    }
    else {
      sb.append("disabled.");
    }
    
    sb.append(" Click ");
    sb.append(
        IcpUtils.prepareLinkToRenderText(Escaper.htmlEscapeAndPreserveWhitespace(_formDefinition.getFormDefinitionXml()),
            "here", 
            getLoggedInUserSecurityInfo(), true)); 
    sb.append(" to see the XML comprising the form definition.");
    return sb.toString();
  }

  public BigrFormDefinition getFormDefinition() {
    return _formDefinition;
  }

  public void setFormDefinition(BigrFormDefinition definition) {
    _formDefinition = definition;
  }

  public LegalValueSet getAccountList() {
    return _accountList;
  }

  void setAccountList(LegalValueSet list) {
    _accountList = list;
  }

  public BigrFormDefinition[] getFormDefinitions() {
    return getArray(_formDefinitions);
  }

  public BigrFormDefinition[] getDataFormDefinitions() {
    return getArray(_dataFormDefinitions);
  }

  public BigrFormDefinition[] getQueryFormDefinitions() {
    return getArray(_queryFormDefinitions);
  }

  public BigrFormDefinition[] getResultsFormDefinitions() {
    return getArray(_resultsFormDefinitions);
  }

  private BigrFormDefinition[] getArray(List list) {
    BigrFormDefinition[] returnValue;
    if (ApiFunctions.isEmpty(list)) {
      returnValue = new BigrFormDefinition[0];
    }
    else {
      returnValue = (BigrFormDefinition[])list.toArray(new BigrFormDefinition[list.size()]);
    }
    return returnValue; 
  }

  void addFormDefinition(BigrFormDefinition definition) {
    if (definition == null) {
      return;
    }
    //add the form definition to the list of all form definitions
    if (_formDefinitions == null) {
      _formDefinitions = new ArrayList();
    }
    _formDefinitions.add(definition);
    //then add it to the individual list to which it belongs
    if (FormDefinitionTypes.DATA.equalsIgnoreCase(definition.getFormType())) {
      if (_dataFormDefinitions == null) {
        _dataFormDefinitions = new ArrayList();
      }
      _dataFormDefinitions.add(definition);
    }
    else if (FormDefinitionTypes.QUERY.equalsIgnoreCase(definition.getFormType())) {
      if (_queryFormDefinitions == null) {
        _queryFormDefinitions = new ArrayList();
      }
      _queryFormDefinitions.add(definition);
    }
    else if (FormDefinitionTypes.RESULTS.equalsIgnoreCase(definition.getFormType())) {
      if (_resultsFormDefinitions == null) {
        _resultsFormDefinitions = new ArrayList();
      }
      _resultsFormDefinitions.add(definition);
    }
  }

  BigrFormDefinitionQueryCriteria getQueryCriteria() {
    return _queryCriteria;
  }

  public void setQueryCriteria(BigrFormDefinitionQueryCriteria queryCriteria) {
    _queryCriteria = queryCriteria;
  }
  
  public boolean isConfirmedRemove() {
    return _confirmedRemove;
  }
  
  public void setConfirmedRemove(boolean confirmedRemove) {
    _confirmedRemove = confirmedRemove;
  }

  public String getConfirmRemoveMessage() {
    return _confirmRemoveMessage;
  }
  public void setConfirmRemoveMessage(String confirmRemoveMessage) {
    _confirmRemoveMessage = confirmRemoveMessage;
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    BigrFormDefinition formDef = new BigrFormDefinition();  
    formDef.setAccount(history.getAttrib1());
    formDef.setFormDefinitionId(history.getAttrib2());
    formDef.setName(history.getAttrib3());
    formDef.setObjectType(history.getAttrib4());
    formDef.setEnabled(Boolean.valueOf(history.getAttrib5()));
    formDef.setFormType(history.getAttrib6());
	formDef.setRolesSharedTo(StringUtils.commaDelimitedListToStringArray(history.getAttrib7()));
	formDef.setDefaultSharedViews(StringUtils.commaDelimitedListToStringArray(history.getAttrib8()));
    formDef.setFormDefinitionXml(history.getClob1());
    this.setFormDefinition(formDef);
  }
  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);
    history.setAttrib1(_formDefinition.getAccount());
    history.setAttrib2(_formDefinition.getFormDefinitionId());
    history.setAttrib3(_formDefinition.getName());
    history.setAttrib4(_formDefinition.getObjectType());
    history.setAttrib5(Boolean.toString(_formDefinition.isEnabled()));
    history.setAttrib6(_formDefinition.getFormType());
	history.setAttrib7(StringUtils.arrayToCommaDelimitedString(_formDefinition.getRolesSharedTo()));
	history.setAttrib8(StringUtils.arrayToCommaDelimitedString(_formDefinition.getDefaultSharedViews()));
    history.setClob1(_formDefinition.getFormDefinitionXml());
  }
}
