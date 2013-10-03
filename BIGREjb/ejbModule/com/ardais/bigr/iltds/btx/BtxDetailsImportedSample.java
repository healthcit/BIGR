package com.ardais.bigr.iltds.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Iterator;


import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.ArdaisStaff;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.IcpUtils;
import com.ardais.bigr.util.VariablePrecisionDateTime;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.DataElement;

public class BtxDetailsImportedSample extends BTXDetails implements Serializable {
  
  private String _sampleAction;
  private String _newDonor;
  private String _newCase;
  private String _policyId;
  private String _linkedIndicator;
  private String _consentVersionId;
  private List _preparedByChoices;
  private Map _sampleRegistrationFormIds;

  private SampleData _sampleData;
  private boolean _derivativeOperationAction = false;

  private BtxHistoryAttributes _attributes;
  
  //label printing information (available templates, printers, etc)
  private Map _labelPrintingData;
  
  //number of times each label should be printed
  private String _labelCount;
  
  //selected template definition to use for printing the labels
  private String _templateDefinitionName;
  
  //selected printer to use for printing the labels
  private String _printerName;
  
  //field to indicate that a sample label should be printed
  private boolean _printSampleLabel = false;

  //output field to indicate if the sample alias is required
  private boolean _sampleAliasRequired = false;
  
  /**
   * 
   */
  public BtxDetailsImportedSample() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return null;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    SampleData sampleData = getSampleData();
    if (sampleData != null) {
      set.add(sampleData.getSampleId());
    }
    return set;
  }

  /**
   * Fill a business transaction history record object with information
   * from this transaction details object.  This method will set <b>all</b>
   * fields on the history record, even ones not used by the this type of
   * transaction.  Fields that aren't used by this transaction type will be
   * set to their initial default values.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will have its fields set to
   *    the transaction information.
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);
    history.setHistoryObject(describeAsHistoryObject());
  }

  /**
   * Populate the fields of this object with information contained in a
   * business transaction history record object.  This method must set <b>all</b>
   * fields on this object, as if it had been newly created immediately before
   * this method was called.  A runtime exception is thrown if the transaction type
   * represented by the history record doesn't match the transaction type represented
   * by this object.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will be used as the
   *    information source.  A runtime exception is thrown if this is null.
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    _sampleData = new SampleData();
    super.populateFromHistoryRecord(history);
    _attributes = (BtxHistoryAttributes)history.getHistoryObject();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();
    StringBuffer sb = new StringBuffer(100);

    String sampleAction = (String)_attributes.getAttribute("sampleAction");

    if (Constants.OPERATION_CREATE.equalsIgnoreCase(sampleAction)) {
      sb.append("Created");
    }
    if (Constants.OPERATION_UPDATE.equalsIgnoreCase(sampleAction)){
      sb.append("Modified");
    }
    sb.append(" sample ");
    
    String sampleAlias = null;
    if (_attributes.containsAttribute("sampleAlias")) {
      sampleAlias = (String)_attributes.getAttribute("sampleAlias");
    }
    else if (_attributes.containsAttribute("customerId")) {
      sampleAlias = (String)_attributes.getAttribute("customerId");
    }

    sb.append(IcpUtils.prepareLink((String)_attributes.getAttribute("sampleId"), securityInfo));

    if (!ApiFunctions.isEmpty(sampleAlias)) {
      sb.append(" (");
      Escaper.htmlEscape(sampleAlias, sb);
      sb.append(")");
    }

    sb.append(" belonging to case ");
    sb.append(IcpUtils.prepareLink((String)_attributes.getAttribute("consentId"), securityInfo));
    String consentAlias = null;
    if (_attributes.containsAttribute("consentAlias")) {
      consentAlias = (String)_attributes.getAttribute("consentAlias");
    }
    if (!ApiFunctions.isEmpty(consentAlias)) {
      sb.append(" (");
      Escaper.htmlEscape(consentAlias, sb);
      sb.append(")");
    }
    sb.append(" and donor ");
    sb.append(IcpUtils.prepareLink((String)_attributes.getAttribute("ardaisId"), securityInfo));
    String donorAlias = null;
    if (_attributes.containsAttribute("donorAlias")) {
      donorAlias = (String)_attributes.getAttribute("donorAlias");
    }
    if (!ApiFunctions.isEmpty(donorAlias)) {
      sb.append(" (");
      Escaper.htmlEscape(donorAlias, sb);
      sb.append(")");
    }
    sb.append(".");

    boolean includeComma = false;
    StringBuffer more = new StringBuffer(200);
    if (_attributes.containsAttribute("sampleType")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a sample type of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("sampleType"), more);
    }

    if (_attributes.containsAttribute("procedure")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a procedure of '");
      Escaper.htmlEscape((String)_attributes.getAttribute("procedure"), more);
      if (_attributes.containsAttribute("procedureOther")) {
        more.append(" (");
        Escaper.htmlEscape((String)_attributes.getAttribute("procedureOther"), more);
        more.append(")");
      }
      more.append("'");
    }

    if (_attributes.containsAttribute("tissue")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a tissue of '");
      Escaper.htmlEscape((String)_attributes.getAttribute("tissue"), more);
      if (_attributes.containsAttribute("tissueOther")) {
        more.append(" (");
        Escaper.htmlEscape((String)_attributes.getAttribute("tissueOther"), more);
        more.append(")");
      }
      more.append("'");
    }

    if (_attributes.containsAttribute("grossAppearance")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a gross appearance of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("grossAppearance"), more);
    }

    if (_attributes.containsAttribute("formatDetail")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a format detail of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("formatDetail"), more);
    }

    if (_attributes.containsAttribute("fixativeType")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a fixative type of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("fixativeType"), more);
    }

    if (_attributes.containsAttribute("collectionDateTime")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a date of collection of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("collectionDateTime"), more);
    }
    else if (_attributes.containsAttribute("dateOfCollection")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a date of collection of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("dateOfCollection"), more);
      if (_attributes.containsAttribute("hourOfCollection") &&
          _attributes.containsAttribute("minuteOfCollection") &&
          _attributes.containsAttribute("meridianOfCollection"))
      {
        more.append(" ");
        Escaper.htmlEscape((String)_attributes.getAttribute("hourOfCollection"), more);
        more.append(":");
        Escaper.htmlEscape((String)_attributes.getAttribute("minuteOfCollection"), more);
        more.append(" ");
        Escaper.htmlEscape((String)_attributes.getAttribute("meridianOfCollection"), more);
      }
    }

    if (_attributes.containsAttribute("preservationDateTime")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a date of preservation of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("preservationDateTime"), more);
    }
    else if (_attributes.containsAttribute("dateOfPreservation")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a date of preservation of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("dateOfPreservation"), more);
      if (_attributes.containsAttribute("hourOfPreservation") &&
          _attributes.containsAttribute("minuteOfPreservation") &&
          _attributes.containsAttribute("meridianOfPreservation"))
      {
        more.append(" ");
        Escaper.htmlEscape((String)_attributes.getAttribute("hourOfPreservation"), more);
        more.append(":");
        Escaper.htmlEscape((String)_attributes.getAttribute("minuteOfPreservation"), more);
        more.append(" ");
        Escaper.htmlEscape((String)_attributes.getAttribute("meridianOfPreservation"), more);
      }
    }

    if (_attributes.containsAttribute("volume")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a volume of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("volume"), more);
      if (_attributes.containsAttribute("volumeUnit")) {
        more.append(" ");
        Escaper.htmlEscape((String)_attributes.getAttribute("volumeUnit"), more);
      }
    }
    
    if (_attributes.containsAttribute("weight")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a weight of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("weight"), more);
      if (_attributes.containsAttribute("weightUnit")) {
        more.append(" ");
        Escaper.htmlEscape((String)_attributes.getAttribute("weightUnit"), more);
      }
    }

    if (_attributes.containsAttribute("bufferType")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a buffer type of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("bufferType"), more);
      if (_attributes.containsAttribute("bufferTypeOther")) {
        more.append(" (");
        Escaper.htmlEscape((String)_attributes.getAttribute("bufferTypeOther"), more);
        more.append(")");
      }
    }

    if (_attributes.containsAttribute("totalNumberOfCells")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a total number of cells of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("totalNumberOfCells"), more);
      if (_attributes.containsAttribute("totalNumberOfCellsExponentialRep")) {
        more.append(" ");
        Escaper.htmlEscape((String)_attributes.getAttribute("totalNumberOfCellsExponentialRep"), more);
      }
    }

    if (_attributes.containsAttribute("cellsPerMl")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a cells/ml of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("cellsPerMl"), more);
    }

    if (_attributes.containsAttribute("percentViability")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a percent viability of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("percentViability"), more);
    }
    if (_attributes.containsAttribute("source")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a source of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("source"), more);
    }

    if (_attributes.containsAttribute("concentration")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a concentration of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("concentration"), more);
    }

    if (_attributes.containsAttribute("yield")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a yield of ");
      Escaper.htmlEscape((String)_attributes.getAttribute("yield"), more);
    }
    
    
    //SWP-1103
    if(_attributes.containsAttribute("dataElements")) {
       
       List attrList = (List)_attributes.getAttribute("dataElements");
         
       for(Iterator aItr = attrList.iterator(); aItr.hasNext();) {
            String cuiValue =(String)aItr.next();
        if(cuiValue.indexOf("&&") > -1) {
            String cui = cuiValue.substring(0, cuiValue.indexOf("&&"));
            String value = cuiValue.substring( cuiValue.indexOf("&&")+2);            
           
                       
         if (includeComma) {
           more.append(", ");
         }
         includeComma = true;
         more.append(" a "+ cui+" of ");
         Escaper.htmlEscape(value, more);
        }
      }
    }

    
    if (_attributes.containsAttribute("preparedBy")) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" and was prepared by ");
      Escaper.htmlEscape((String)_attributes.getAttribute("preparedBy"), more);
    }

    if (more.length() > 0) {
      sb.append("  The sample has");
      sb.append(more.toString());
      sb.append(".");
    }

    if (_attributes.containsAttribute("consumed")) {
      if ("true".equals((String)_attributes.getAttribute("consumed"))) {
        sb.append("  The sample was marked consumed.");
      }
    }

    if (_attributes.containsAttribute("note")) {
      sb.append("  Comments:<br>");
      Escaper.htmlEscapeAndPreserveWhitespace((String)_attributes.getAttribute("note"), sb);
    }
    return sb.toString();
  }

  private Object describeAsHistoryObject() {
    BtxHistoryAttributes attributes = new BtxHistoryAttributes();
    SampleData sampleData = getSampleData();
    if (sampleData != null) {
      if (!ApiFunctions.isEmpty(sampleData.getArdaisId())) {
        attributes.setAttribute("ardaisId", sampleData.getArdaisId());
      }
      if (!ApiFunctions.isEmpty(sampleData.getDonorAlias())) {
        attributes.setAttribute("donorAlias", sampleData.getDonorAlias());
      }
      if (!ApiFunctions.isEmpty(sampleData.getConsentId())) {
        attributes.setAttribute("consentId", sampleData.getConsentId());
      }
      if (!ApiFunctions.isEmpty(sampleData.getConsentAlias())) {
        attributes.setAttribute("consentAlias", sampleData.getConsentAlias());
      }
      if (!ApiFunctions.isEmpty(sampleData.getSampleId())) {
        attributes.setAttribute("sampleId", sampleData.getSampleId());
      }
      if (!ApiFunctions.isEmpty(sampleData.getSampleAlias())) {
        // Formerly stored as "customerId". Be sure to parse for both cases when reading XML.
        attributes.setAttribute("sampleAlias", sampleData.getSampleAlias());
      }
      if (!ApiFunctions.isEmpty(sampleData.getSampleTypeCui())) {
        attributes.setAttribute("sampleType", sampleData.getSampleType());
      }
      if (!ApiFunctions.isEmpty(sampleData.getProcedure())) {
        attributes.setAttribute("procedure", BigrGbossData.getProcedureDescription(sampleData.getProcedure()));
      }
      if (!ApiFunctions.isEmpty(sampleData.getProcedureOther())) {
        attributes.setAttribute("procedureOther", sampleData.getProcedureOther());
      }
      if (!ApiFunctions.isEmpty(sampleData.getTissue())) {
        attributes.setAttribute("tissue", BigrGbossData.getTissueDescription(sampleData.getTissue()));
       
      }
      if (!ApiFunctions.isEmpty(sampleData.getTissueOther())) {
        attributes.setAttribute("tissueOther", sampleData.getTissueOther());
      }
      if (!ApiFunctions.isEmpty(sampleData.getGrossAppearance())) {
        attributes.setAttribute("grossAppearance", FormLogic.lookupAppearance(sampleData.getGrossAppearance()));
      }
      if (!ApiFunctions.isEmpty(sampleData.getFixativeType())) {
        attributes.setAttribute("fixativeType", GbossFactory.getInstance().getDescription(sampleData.getFixativeType()));
      }
      if (!ApiFunctions.isEmpty(sampleData.getFormatDetail())) {
        attributes.setAttribute("formatDetail", GbossFactory.getInstance().getDescription(sampleData.getFormatDetail()));
      }
      VariablePrecisionDateTime collectionDateTime = sampleData.getCollectionDateTime();
      if (collectionDateTime != null) {
        if (!collectionDateTime.isEmpty()) {
          // Formerly held in 4 separate fields:
          // - dateOfCollection
          // - hourOfCollection
          // - minuteOfCollection
          // - meridianOfCollection
          // Be sure to handle both cases when parsing the XML.
          attributes.setAttribute("collectionDateTime", collectionDateTime.toString());
          attributes.setAttribute("collectionDpc", collectionDateTime.getPrecision());
        }
      }
      VariablePrecisionDateTime preservationDateTime = sampleData.getPreservationDateTime();
      if (preservationDateTime != null) {
        if (!preservationDateTime.isEmpty()) {
          // Formerly held in 4 separate fields:
          // - dateOfPreservation
          // - hourOfPreservation
          // - minuteOfPreservation
          // - meridianOfPreservation
          // Be sure to handle both cases when parsing the XML.
          attributes.setAttribute("preservationDateTime", preservationDateTime.toString());
          attributes.setAttribute("preservationDpc", preservationDateTime.getPrecision());
        }
      }
      
      //SWP1103
      if(sampleData.getRegistrationFormInstance()!= null) {
        DataElement[] datas = sampleData.getRegistrationFormInstance().getDataElements();
        
        if(datas != null) {
          List attrList = new ArrayList();
          
          for (int i=0; i< datas.length; i++) {
            DataElement element = datas[i];
                 
            DetDataElement dedElement = DetService.SINGLETON.getDataElementTaxonomy().getDataElement(element.getCuiOrSystemName());
 
            String option = null;
            
            //dropdown list
            if(dedElement.getBroadValueSet() != null)
            option =  BigrGbossData.getValueSet( dedElement.getBroadValueSet().getCui()).getValue(element.getElementValue(0).getValue()).getDescription();
            else //text field
            {
              option =element.getElementValue(0).getValue();
            }
            
            String cuiValue = dedElement.getDescription()+"&&" + option;
           
            attrList.add(cuiValue);
          }
             if(attrList.size() > 0)
             attributes.setAttribute("dataElements", attrList);
        }
      
      }
      
      if (!ApiFunctions.isEmpty(sampleData.getPreparedBy())) {
        try {
          ArdaisStaff staff = new ArdaisStaff(new ArdaisstaffAccessBean(new ArdaisstaffKey(sampleData.getPreparedBy())));
          attributes.setAttribute("preparedBy", staff.getFullName());
        } catch (Exception e) {
          attributes.setAttribute("preparedBy", sampleData.getPreparedBy());
        }
      }
      if (!ApiFunctions.isEmpty(sampleData.getVolumeAsString())) {
        attributes.setAttribute("volume", sampleData.getVolumeAsString());
      }
      if (!ApiFunctions.isEmpty(sampleData.getVolumeUnitAsString())) {
        attributes.setAttribute("volumeUnit", sampleData.getVolumeUnitAsString());
      }
      if (!ApiFunctions.isEmpty(sampleData.getWeightAsString())) {
        attributes.setAttribute("weight", sampleData.getWeightAsString());
      }
      if (!ApiFunctions.isEmpty(sampleData.getWeightUnitAsString())) {
        attributes.setAttribute("weightUnit", sampleData.getWeightUnitAsString());
      }
      if (!ApiFunctions.isEmpty(sampleData.getAsmNote())) {
        attributes.setAttribute("note", sampleData.getAsmNote());
      }
      if (!ApiFunctions.isEmpty(getSampleAction())) {
        attributes.setAttribute("sampleAction", getSampleAction());
      }
      if (!ApiFunctions.isEmpty(sampleData.getAsmId())) {
        attributes.setAttribute("asmId", sampleData.getAsmId());
      }
      if (!ApiFunctions.isEmpty(sampleData.getBufferType())) {
        attributes.setAttribute("bufferType", sampleData.getBufferType());
      }
      if (!ApiFunctions.isEmpty(sampleData.getBufferTypeOther())) {
        attributes.setAttribute("bufferTypeOther", sampleData.getBufferTypeOther());
      }
      if (!ApiFunctions.isEmpty(sampleData.getTotalNumOfCellsAsString())) {
        attributes.setAttribute("totalNumberOfCells", sampleData.getTotalNumOfCellsAsString());
      }
      if (!ApiFunctions.isEmpty(sampleData.getTotalNumOfCellsExRepCui())) {
        attributes.setAttribute("totalNumberOfCellsExponentialRep", sampleData.getTotalNumOfCellsExRep());
      }
      if (!ApiFunctions.isEmpty(sampleData.getCellsPerMlAsString())) {
        attributes.setAttribute("cellsPerMl", sampleData.getCellsPerMlAsString());
      }
      if (!ApiFunctions.isEmpty(sampleData.getPercentViabilityAsString())) {
        attributes.setAttribute("percentViability", sampleData.getPercentViabilityAsString());
      }
      if (!ApiFunctions.isEmpty(sampleData.getSource())) {
        attributes.setAttribute("source", sampleData.getSource());
      }
      if (sampleData.isConsumed()) {
        attributes.setAttribute("consumed", "true");
      }
      if (!ApiFunctions.isEmpty(sampleData.getConcentrationAsString())) {
        attributes.setAttribute("concentration", sampleData.getConcentrationAsString());
      }
      if (!ApiFunctions.isEmpty(sampleData.getYieldAsString())) {
        attributes.setAttribute("yield", sampleData.getYieldAsString());
      }
    }
    return attributes;
  }

  /**
   * @return
   */
  public String getConsentVersionId() {
    return _consentVersionId;
  }

  /**
   * @return
   */
  public String getLinkedIndicator() {
    return _linkedIndicator;
  }

  /**
   * @return
   */
  public String getNewCase() {
    return _newCase;
  }

  /**
   * @return
   */
  public String getNewDonor() {
    return _newDonor;
  }

  /**
   * @return
   */
  public String getPolicyId() {
    return _policyId;
  }

  /**
   * @return
   */
  public List getPreparedByChoices() {
    return _preparedByChoices;
  }

  /**
   * @return
   */
  public String getSampleAction() {
    return _sampleAction;
  }

  /**
   * @return
   */
  public SampleData getSampleData() {
    return _sampleData;
  }

  /**
   * @param string
   */
  public void setConsentVersionId(String string) {
    _consentVersionId = string;
  }

  /**
   * @param string
   */
  public void setLinkedIndicator(String string) {
    _linkedIndicator = string;
  }

  /**
   * @param string
   */
  public void setNewCase(String string) {
    _newCase = string;
  }

  /**
   * @param string
   */
  public void setNewDonor(String string) {
    _newDonor = string;
  }

  /**
   * @param string
   */
  public void setPolicyId(String string) {
    _policyId = string;
  }

  /**
   * @param list
   */
  public void setPreparedByChoices(List list) {
    _preparedByChoices = list;
  }

  /**
   * @param string
   */
  public void setSampleAction(String string) {
    _sampleAction = string;
  }

  /**
   * @param data
   */
  public void setSampleData(SampleData data) {
    _sampleData = data;
  }
  

  /**
   * @return
   */
  public boolean isDerivativeOperationAction() {
    return _derivativeOperationAction;
  }

  /**
   * @param b
   */
  public void setDerivativeOperationAction(boolean b) {
    _derivativeOperationAction = b;
  }

  public Map getSampleRegistrationFormIds() {
    return _sampleRegistrationFormIds;
  }

  public void setSampleRegistrationFormIds(Map ids) {
    _sampleRegistrationFormIds = ids;
  }
  
  public Map getLabelPrintingData() {
    return _labelPrintingData;
  }
  
  public void setLabelPrintingData(Map labelPrintingData) {
    _labelPrintingData = labelPrintingData;
  }
  
  /**
   * @return Returns the printSampleLabel.
   */
  public boolean isPrintSampleLabel() {
    return _printSampleLabel;
  }
  
  /**
   * @param printSampleLabel The printSampleLabel to set.
   */
  public void setPrintSampleLabel(boolean printSampleLabel) {
    _printSampleLabel = printSampleLabel;
  }
  
  /**
   * @return Returns the labelCount.
   */
  public String getLabelCount() {
    return _labelCount;
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
   * @param labelCount The labelCount to set.
   */
  public void setLabelCount(String labelCount) {
    _labelCount = labelCount;
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
   * @return Returns the sampleAliasRequired.
   */
  public boolean isSampleAliasRequired() {
    return _sampleAliasRequired;
  }
  
  /**
   * @param sampleAliasRequired The sampleAliasRequired to set.
   */
  public void setSampleAliasRequired(boolean sampleAliasRequired) {
    _sampleAliasRequired = sampleAliasRequired;
  }
  
}
