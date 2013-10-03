package com.ardais.bigr.dataImport.web.form;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class PrintLabelsForm extends BigrActionForm {
  
  //selected action (reprint or preprint)
  private String _action;
  
  //selection object type (donor, case, sample, etc)
  private String _objectType;
  
  //number of objects for which preprinted labels should be printed
  private String _preprintedLabelCount;
  
  //number of times each label should be printed
  private String _labelCount;
  
  //ids of existing objects for which labels should be reprinted
  private String[] _ids;
  
  //selected template definition to use for printing the labels
  private String _templateDefinitionName;
  
  //selected printer to use for printing the labels
  private String _printerName;

  //label printing information (available templates, printers, etc)
  private Map _labelPrintingData;
  //set of available actions
  private LegalValueSet _actionChoices;
  //set of available objects for which labels can be printed
  private LegalValueSet _objectTypeChoices;

  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _action = null;
    _objectType = null;
    _preprintedLabelCount = null;
    _labelCount = null;
    _ids = null;
    _templateDefinitionName = null;
    _printerName = null;
    _labelPrintingData = null;
    _actionChoices = null;
    _objectTypeChoices = null;
    populateDropDownChoices();
  }
  
  private void populateDropDownChoices() {
    LegalValueSet actionChoices = new LegalValueSet();
    actionChoices.addLegalValue(Constants.LABEL_PRINTING_ACTION_PREPRINT, "Preprint labels");
    actionChoices.addLegalValue(Constants.LABEL_PRINTING_ACTION_REPRINT, "Reprint labels");
    setActionChoices(actionChoices);
    LegalValueSet objectTypeChoices = new LegalValueSet();
    Iterator iterator = Constants.LABEL_PRINTING_OBJECT_TYPES.keySet().iterator();
    while (iterator.hasNext()) {
      String objectType = (String)iterator.next();
      objectTypeChoices.addLegalValue(objectType, ApiFunctions.capitalize(objectType));
    }
    setObjectTypeChoices(objectTypeChoices);
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    //nothing to return here - all validation is done in the action
    return null;
  }
  
  /**
   * @return Returns the action.
   */
  public String getAction() {
    return _action;
  }
  
  /**
   * @return Returns the ids.
   */
  public String[] getIds() {
    return _ids;
  }
  
  /**
   * @return Returns the labelPrintingData.
   */
  public Map getLabelPrintingData() {
    return _labelPrintingData;
  }
  
  /**
   * @return Returns the printerName.
   */
  public String getPrinterName() {
    return _printerName;
  }
  
  /**
   * @return Returns the templateDefinitionName.
   */
  public String getTemplateDefinitionName() {
    return _templateDefinitionName;
  }
  
  /**
   * @return Returns the objectType.
   */
  public String getObjectType() {
    return _objectType;
  }
  
  /**
   * @param action The action to set.
   */
  public void setAction(String action) {
    _action = action;
  }
  
  /**
   * @param ids The ids to set.
   */
  public void setIds(String[] ids) {
    _ids = ids;
  }
  
  /**
   * @param labelPrintingData The labelPrintingData to set.
   */
  public void setLabelPrintingData(Map labelPrintingData) {
    _labelPrintingData = labelPrintingData;
  }
  
  /**
   * @param printerName The printerName to set.
   */
  public void setPrinterName(String printerName) {
    _printerName = printerName;
  }

  /**
   * @param templateDefinitionName The templateDefinitionName to set.
   */
  public void setTemplateDefinitionName(String templateDefinitionName) {
    _templateDefinitionName = templateDefinitionName;
  }
  
  /**
   * @param objectType The objectType to set.
   */
  public void setObjectType(String objectType) {
    _objectType = objectType;
  }
  
  /**
   * @return Returns the actionChoices.
   */
  public LegalValueSet getActionChoices() {
    return _actionChoices;
  }
  
  /**
   * @return Returns the objectTypeChoices.
   */
  public LegalValueSet getObjectTypeChoices() {
    return _objectTypeChoices;
  }
  
  /**
   * @return Returns the labelCount.
   */
  public String getLabelCount() {
    return _labelCount;
  }
  
  /**
   * @return Returns the preprintedLabelCount.
   */
  public String getPreprintedLabelCount() {
    return _preprintedLabelCount;
  }
  
  /**
   * @param actionChoices The actionChoices to set.
   */
  private void setActionChoices(LegalValueSet actionChoices) {
    _actionChoices = actionChoices;
  }
  
  /**
   * @param objectTypeChoices The objectTypeChoices to set.
   */
  private void setObjectTypeChoices(LegalValueSet objectTypeChoices) {
    _objectTypeChoices = objectTypeChoices;
  }
  
  /**
   * @param labelCount The labelCount to set.
   */
  public void setLabelCount(String labelCount) {
    _labelCount = labelCount;
  }
  
  /**
   * @param preprintedLabelCount The preprintedLabelCount to set.
   */
  public void setPreprintedLabelCount(String preprintedLabelCount) {
    _preprintedLabelCount = preprintedLabelCount;
  }
}
