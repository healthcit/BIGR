package com.ardais.bigr.dataImport.web.form;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateImportedSample;
import com.ardais.bigr.iltds.btx.BtxDetailsImportedSample;
import com.ardais.bigr.iltds.btx.BtxDetailsModifyImportedSample;
import com.ardais.bigr.iltds.databeans.IrbVersionData;
import com.ardais.bigr.iltds.databeans.PolicyData;
import com.ardais.bigr.iltds.databeans.SampleTypeConfiguration;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.TypeFinder;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.javabeans.SampleDataForUI;
import com.ardais.bigr.kc.form.BigrFormInstance;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.PolicyUtils;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.FormContextStack;
import com.gulfstreambio.kc.web.support.KcUiContext;
import com.gulfstreambio.kc.web.support.WebUtils;

/**
 * Struts ActionForm class for creating a new sample.
 */
public class SampleForm extends BigrActionForm {
  private SampleData _sampleData;
  private String _newDonor;
  private String _linkedIndicator;
  private String _consentVersionId;
  private String _policyId;
  private String _newCase;
  private List _consentChoices;
  private List _policyChoices;
  private LegalValueSet _appearanceChoices;
  private LegalValueSet _fixativeTypeChoices;
  private LegalValueSet _formatDetailChoices;
  private String _rememberedData;
  private List _matchingSamples;
  private String _originalSampleType;
  private List _preparedByChoices;
  private Map _sampleRegistrationFormIds;
  private LegalValueSet _bufferTypeChoices;
  private LegalValueSet _totalNumOfCellsExRepChoices;
  private LegalValueSet _volumeUnitChoices;
  private LegalValueSet _weightUnitChoices;
  
  private BigrFormDefinition[] _formDefinitions;
  private BigrFormInstance[] _formInstances;
  private String _form; 
  
  private boolean _createForm;
  private boolean _readOnly;
  private boolean _printSampleLabel;
  private boolean _generateSampleId;
  private String _generatedSampleId;
  
  //label printing information (available templates, printers, etc)
  private Map _labelPrintingData;
  
  //number of times each label should be printed
  private String _labelCount;
  
  //selected template definition to use for printing the labels
  private String _templateDefinitionName;
  
  //selected printer to use for printing the labels
  private String _printerName;

  //field to indicate if the sample alias is required
  private boolean _sampleAliasRequired;
  
  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _sampleData = new SampleDataForUI();
    _newDonor = null;
    _linkedIndicator = null;
    _consentVersionId = null;
    _policyId = null;
    _newCase = null;
    _consentChoices = null;
    _policyChoices = null;
    _appearanceChoices = null;
    _fixativeTypeChoices = null;
    _formatDetailChoices = null;
    _matchingSamples = null;
    _originalSampleType = null;
    _preparedByChoices = null;
    _sampleRegistrationFormIds = null;
    _bufferTypeChoices = null;
    _totalNumOfCellsExRepChoices = null;
    _volumeUnitChoices = null;
    _weightUnitChoices = null;
    _formDefinitions = null;
    _formInstances = null;
    populateDropDownChoices();
    _createForm = false;
    _readOnly = false;
    _form = null;
    _printSampleLabel = false;
    _labelPrintingData = null;
    _labelCount = "1";
    _templateDefinitionName = null;
    _printerName = null;
    _sampleAliasRequired = false;
}
  
  private void populateDropDownChoices() {
    try {
      ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
      ListGenerator listBean = home.create();
      setAppearanceChoices(listBean.getSpecimenTypes());
      setFixativeTypeChoices(BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_TYPE_OF_FIXATIVE));
      setFormatDetailChoices(BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_SAMPLE_FORMAT_DETAIL));
      setBufferTypeChoices(BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_BUFFER_TYPE));
      setTotalNumOfCellsExRepChoices(BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_EXP_CELLS_PER_ML));
      setVolumeUnitChoices(BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_SAMPLE_VOLUME_UNIT));   
      setWeightUnitChoices(BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_SAMPLE_WEIGHT_UNIT)); 
      }
    catch (Exception e) {
      throw new ApiException(e);
    }
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    // Here we just make sure the double-entered ids (or customer ids) for both donor and
    // case match, if the donor and/or case is not new.
    // All other validation is done in the BTX performer.
    ActionErrors errors = new ActionErrors();
    
    String myTag = ApiFunctions.safeString(mapping.getTag());
    
    //if the tag on the action = "CreateSampleSuccess", then we've successfully
    //created a sample and now want to return to the Create Sample page (so
    //the user can create another sample).  Most of the data on the form should
    //be reset, but the user may have elected to "remember" the donor and or
    //"case", so if either of those choices have been made save the appropriate
    //ids, reset the form, and then repopulate those ids.  We can also skip 
    //validation since it is unnecessary to validate anything here. 
    if ("CreateSampleSuccess".equalsIgnoreCase(myTag)) {
      //grab the sample data from the last sample created
      SampleData savedSampleData = getSampleData();
      String jsonForm = _form;
      //store whether or not a label is to be printed and the data for printing
      boolean printLabel = isPrintSampleLabel();
      String labelCount = getLabelCount();
      String templateDefinitionName = getTemplateDefinitionName();
      String printerName = getPrinterName();
      Map labelPrintingData = getLabelPrintingData();
      boolean sampleAliasRequired = isSampleAliasRequired();
      //reset the form
      doReset(mapping,request);
      if (TypeFinder.CASE.equalsIgnoreCase(getRememberedData())) {
        //blank out fields that we're not retaining values for and then set the sample data
        //on the form to the saved sample data.  That way all of the attributes whose values
        //should be retained will have the correct values.
        savedSampleData.setSampleAlias(null);
        savedSampleData.setSampleId(null);
        setSampleData(savedSampleData);
        _form = jsonForm;
        setPrintSampleLabel(printLabel);
        setLabelCount(labelCount);
        setTemplateDefinitionName(templateDefinitionName);
        setPrinterName(printerName);
        setLabelPrintingData(labelPrintingData);
        setSampleAliasRequired(sampleAliasRequired);
        //null out the generated sample id.  If the user had selected to generate an id and made a mistake
        //(and was thus returned to the create sample page), the generated id is remembered in a hidden
        //field.  When the user fixes their mistake and resubmits, that generated id is passed in as
        //a request parameter.  After the sample is created, we go to a page that requires this form
        //so Struts creates it and populates it from the request, thus setting the generatedSampleId
        //to the id of the sample just created.  Since the form has a value for the generated sample
        //id the page is populated with that value, and when the user tries to create the next
        //sample they are shown a message stating that the sample already exists.
        setGeneratedSampleId(null);
      }
      else if (TypeFinder.DONOR.equalsIgnoreCase(getRememberedData())) {
        getSampleData().setArdaisId(savedSampleData.getArdaisId());
        getSampleData().setDonorAlias(savedSampleData.getDonorAlias());
      }
    }
    return errors;
  }
  
  public void describeIntoBtxDetails(BTXDetails btxDetails0, BigrActionMapping mapping,
                                     HttpServletRequest request) {
    
    super.describeIntoBtxDetails(btxDetails0, mapping, request);
    
    // Create a new form instance.
    String jsonForm = getForm();
    FormInstance form = ApiFunctions.isEmpty(jsonForm) ? 
        new FormInstance() : WebUtils.convertToFormInstance(jsonForm);
    
    // Populate the BTX details with the form instance.
    BtxDetailsImportedSample myDetails = (BtxDetailsImportedSample)btxDetails0;
    myDetails.getSampleData().setRegistrationFormInstance(form);
  }

  /* 
   * Store necessary session-based information
   * @see com.ardais.bigr.web.form.PopulatesRequestFromBtxDetails#populateRequestFromBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void populateRequestFromBtxDetails(BTXDetails btxDetails, 
                                            BigrActionMapping mapping,
                                            HttpServletRequest request) {

    // Always set up the KC form context, whether an error occurred or not, so that the
    // place we forward to can at least find the registration form.  If the transaction did
    // not get the registration form, then this setup will not do any harm.
    BtxDetailsImportedSample myDetails = (BtxDetailsImportedSample)btxDetails;
    SampleData sampleData = myDetails.getSampleData();
    setupKcFormContext(request, sampleData);
    
    //if the transaction was successful, store the donor and case information in the session
    if (btxDetails.isTransactionCompleted() && !btxDetails.isHasException()) {
      //note - this is applicable only to creating a new imported sample.  Modifying an imported 
      //sample is handled below. 
      if (btxDetails instanceof BtxDetailsCreateImportedSample) {
        String donorId = sampleData.getArdaisId();
        String donorAlias = sampleData.getDonorAlias();
        String caseId = sampleData.getConsentId();
        String caseAlias = sampleData.getConsentAlias();
        String sampleId = sampleData.getSampleId();
        String sampleAlias = sampleData.getSampleAlias();
        //because we're passing all 3 ids into the storeLastUsed method it's irrelevant what
        //we pass for the last parameter, but given that we're creating a new object it's good 
        //practice to pass false here to ensure that any saved ids are cleared out.
        PdcUtils.storeLastUsedDonorCaseAndSample(request, donorId, donorAlias, caseId, caseAlias, 
            sampleId, sampleAlias, false);
      }
      //note - this handles both modify imported sample start and modify imported sample.
      //only do this if a single sample was found - in the case of multiple matching samples (i.e.
      //an alias was specified that matches more than one sample id) skip this step since the specific
      //sample information has not yet been determined.  In such a scenario, the matchingSamples
      //property will be non-empty
      else if (btxDetails instanceof BtxDetailsModifyImportedSample && ApiFunctions.isEmpty(getMatchingSamples())) {
        String donorId = sampleData.getArdaisId();
        String donorAlias = sampleData.getDonorAlias();
        String caseId = sampleData.getConsentId();
        String caseAlias = sampleData.getConsentAlias();
        String sampleId = sampleData.getSampleId();
        String sampleAlias = sampleData.getSampleAlias();
        //because we're passing all 3 ids into the storeLastUsed method it's irrelevant what
        //we pass for the last parameter, but given that we're modifying an existing object it's 
        //good practice to pass true here to ensure that any saved ids are maintained if possible.
        PdcUtils.storeLastUsedDonorCaseAndSample(request, donorId, donorAlias, caseId, caseAlias, 
            sampleId, sampleAlias, true);
      }      
    }
  }

  private void setupKcFormContext(HttpServletRequest request, SampleData sampleData) {
    FormContextStack stack = FormContextStack.getFormContextStack(request);
    FormContext formContext = stack.peek();
    DataFormDefinition formDef = sampleData.getRegistrationForm();
    if (formDef != null) {
      formContext.setDataFormDefinition(formDef);
    }
    FormInstance form = sampleData.getRegistrationFormInstance();
    if (form != null) {
      formContext.setFormInstance(form);          
    }
    formContext.setDomainObjectId(sampleData.getSampleId());
    stack.push(formContext);

    KcUiContext kcUiContext = KcUiContext.getKcUiContext(request);
    String contextPath = request.getContextPath();
    kcUiContext.setAdePopupUrl(contextPath + "/kc/ade/popup.do");
  }
  
  /**
   * @return
   */
  public LegalValueSet getSampleTypeChoices() {
    SampleTypeConfiguration sampleTypeConfiguration = getSampleData().getSampleTypeConfiguration();
    if (sampleTypeConfiguration == null) {
      sampleTypeConfiguration = new SampleTypeConfiguration();
    }
    LegalValueSet sampleTypeSet = sampleTypeConfiguration.getSupportedSampleTypesAsLVS();
    //if there is a sample type in use on this form, make sure it's value appears in the
    //sample type dropdown
    String sampleTypeCui = getSampleData().getSampleTypeCui();
    if (!ApiFunctions.isEmpty(sampleTypeCui) && 
        !sampleTypeConfiguration.isSampleTypeSupported(sampleTypeCui)) {
      String description = GbossFactory.getInstance().getDescription(sampleTypeCui);
      sampleTypeSet.addLegalValue(sampleTypeCui, description);        
    }
    return sampleTypeSet;
  }

  public String getIrbProtocolName() {
    String protocolName = "";
    String consentVersionId = getConsentVersionId();
    if (!ApiFunctions.isEmpty(consentVersionId)) {
      IrbVersionData irbData = IltdsUtils.getIrbVersionData(consentVersionId);
      if (irbData != null) {
        protocolName = irbData.getIrbProtocolName();
      }
    }
    return protocolName;
  }
  
  public String getConsentVersionName() {
    String name = "";
    String consentVersionId = getConsentVersionId();
    if (!ApiFunctions.isEmpty(consentVersionId)) {
      IrbVersionData irbData = IltdsUtils.getIrbVersionData(consentVersionId);
      if (irbData != null) {
        name = irbData.getConsentVersionName();
      }
    }
    return name;
  }

  public String getPolicyName() {
    String policyName = "";
    String policyId = getPolicyId();
    if (!ApiFunctions.isEmpty(policyId)) {
      PolicyData policyData = PolicyUtils.getPolicyData(policyId);
      if (policyData != null) {
        policyName = policyData.getPolicyName();  
      }
    }
    return policyName;
  }

  /**
   * @return
   */
  public LegalValueSet getAppearanceChoices() {
    return _appearanceChoices;
  }

  /**
   * @return
   */
  public LegalValueSet getBufferTypeChoices() {
    return _bufferTypeChoices;
  }

  /**
   * @return
   */
  public List getConsentChoices() {
    return _consentChoices;
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
  public LegalValueSet getFixativeTypeChoices() {
    return _fixativeTypeChoices;
  }

  /**
   * @return
   */
  public LegalValueSet getFormatDetailChoices() {
    return _formatDetailChoices;
  }

  /**
   * @return
   */
  public String[] getHourList() {
    return Constants.HOURS;
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
  public List getMatchingSamples() {
    return _matchingSamples;
  }

  /**
   * @return
   */
  public String[] getMinuteList() {
    return Constants.MINUTES;
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
  public String getOriginalSampleType() {
    return _originalSampleType;
  }

  /**
   * @return
   */
  public List getPolicyChoices() {
    return _policyChoices;
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
  public String getRememberedData() {
    return _rememberedData;
  }

  /**
   * @return
   */
  public SampleData getSampleData() {
    return _sampleData;
  }

  /**
   * @return
   */
  public LegalValueSet getTotalNumOfCellsExRepChoices() {
    return _totalNumOfCellsExRepChoices;
  }
 
  /**
   * @return
   */
  public LegalValueSet getVolumeUnitChoices() {
    return _volumeUnitChoices;
  }
  /**
   * @param set
   */
  public void setAppearanceChoices(LegalValueSet set) {
    _appearanceChoices = set;
  }

  /**
   * @param set
   */
  public void setBufferTypeChoices(LegalValueSet set) {
    _bufferTypeChoices = set;
  }

  /**
   * @param list
   */
  public void setConsentChoices(List list) {
    _consentChoices = list;
  }

  /**
   * @param string
   */
  public void setConsentVersionId(String string) {
    _consentVersionId = string;
  }

  /**
   * @param set
   */
  public void setFixativeTypeChoices(LegalValueSet set) {
    _fixativeTypeChoices = set;
  }

  /**
   * @param set
   */
  public void setFormatDetailChoices(LegalValueSet set) {
    _formatDetailChoices = set;
  }

  /**
   * @param string
   */
  public void setLinkedIndicator(String string) {
    _linkedIndicator = string;
  }

  /**
   * @param list
   */
  public void setMatchingSamples(List list) {
    _matchingSamples = list;
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
  public void setOriginalSampleType(String string) {
    _originalSampleType = string;
  }

  /**
   * @param list
   */
  public void setPolicyChoices(List list) {
    _policyChoices = list;
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
  public void setRememberedData(String string) {
    _rememberedData = string;
  }

  /**
   * @param data
   */
  public void setSampleData(SampleData data) {
    _sampleData = data;
  }

  /**
   * @param set
   */
  public void setTotalNumOfCellsExRepChoices(LegalValueSet set) {
    _totalNumOfCellsExRepChoices = set;
  }
  
  /**
   * @param set
   */
  public void setVolumeUnitChoices(LegalValueSet set) {
      _volumeUnitChoices = set;
  }
  /**
   * @param set
   */
  public void setWeightUnitChoices(LegalValueSet set) {
      _weightUnitChoices = set;
  }
  /**
   * @param set
   */
  public LegalValueSet getWeightUnitChoices() {
      return _weightUnitChoices ;
  }
  /**
   * @return Returns the formDefinitions.
   */
  public BigrFormDefinition[] getFormDefinitions() {
    return _formDefinitions;
  }
  
  /**
   * @return Returns the formInstances.
   */
  public BigrFormInstance[] getFormInstances() {
    return _formInstances;
  }
  
  /**
   * @param formDefinitions The formDefinitions to set.
   */
  public void setFormDefinitions(BigrFormDefinition[] formDefinitions) {
    _formDefinitions = formDefinitions;
  }
  
  /**
   * @param formInstances The formInstances to set.
   */
  public void setFormInstances(BigrFormInstance[] formInstances) {
    _formInstances = formInstances;
  }

  public String getFormDefinitionId() {
    return "";
  }
  
  public LegalValueSet getFormDefinitionsAsLegalValueSet() {
    LegalValueSet lvs = new LegalValueSet();
    BigrFormDefinition[] formDefs = getFormDefinitions();
    if (formDefs != null) {
      for (int i = 0; i < formDefs.length; i++) {
        BigrFormDefinition formDef = formDefs[i];
        lvs.addLegalValue(formDef.getFormDefinitionId(), formDef.getName());
      }
    }
    return lvs;
  }

  public boolean isCreateForm() {
    return _createForm;
  }

  public void setCreateForm(boolean createForm) {
    _createForm = createForm;
  }

  public boolean isReadOnly() {
    return _readOnly;
  }

  public void setReadOnly(boolean readOnly) {
    _readOnly = readOnly;
  }
  

  public Map getSampleRegistrationFormIds() {
    return _sampleRegistrationFormIds;
  }

  public void setSampleRegistrationFormIds(Map sampleRegistrationFormIds) {
    _sampleRegistrationFormIds = sampleRegistrationFormIds;
  }

  public void setForm(String form) {
    _form = form;
  }
  
  public String getForm() {
    return _form;
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
  
  public boolean isGenerateSampleId() {
    return _generateSampleId;
  }
  public void setGenerateSampleId(boolean generateSampleId) {
    _generateSampleId = generateSampleId;
  }
  
  public String getGeneratedSampleId() {
    return _generatedSampleId;
  }
  public void setGeneratedSampleId(String generatedSampleId) {
    _generatedSampleId = generatedSampleId;
  }
  
  /**
   * @return Returns the labelPrintingData.
   */
  public Map getLabelPrintingData() {
    return _labelPrintingData;
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
   * @param labelPrintingData The labelPrintingData to set.
   */
  public void setLabelPrintingData(Map labelPrintingData) {
    _labelPrintingData = labelPrintingData;
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
