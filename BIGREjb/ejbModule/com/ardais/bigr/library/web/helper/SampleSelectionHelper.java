package com.ardais.bigr.library.web.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.IdGenerator;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.assistants.ProjectDataBean;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.AsmData;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.javabeans.DonorData;
import com.ardais.bigr.javabeans.OrderData;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.javabeans.RnaData;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.javabeans.ShoppingCartData;
import com.ardais.bigr.kc.form.BigrFormInstance;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.lims.javabeans.DiagnosticTestResultData;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionData;
import com.ardais.bigr.query.ColumnDescriptor;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.VariablePrecisionDateTime;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.gboss.GbossValueSet;
import com.gulfstreambio.gboss.GbossValueSets;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.gulfstreambio.kc.form.DataElementValue;
import com.ardais.bigr.query.ColumnConstants;


public class SampleSelectionHelper implements PopulatableFromBtxDetails, Serializable {

  private static final int MAX_NOTES_LENGTH = 60;
  private static final int MAX_LOGICAL_REPOSITORY_LENGTH = 30;

  private ProductDto _ssData;
  private SampleData _sampleData;
  private PathologyEvaluationData _peData;
  private ConsentData _consentData;
  private PathReportSectionData _sectionData;
  private PathReportData _pathData;
  private AsmData _asmData;
  private DonorData _donorData;
  private OrderData _orderData;
  private ProjectDataBean _projectData;
  private RnaData _rnaData;
  private List _diagnosticTestResultData;
  

  private static Map _sampleStatusMap;

  private Integer _amountOnHold; // not inherent to the sample, this is specific to the user

  private String _tnmDisplayString; // caches the value of the complex TNM string
  private boolean _appearanceNormal = true;
  private boolean _neoplasticDx = false;

  private boolean _selectable = true;
  private boolean _selected = false;
  private boolean _selectionDisabled = false;
  private String _rowClass = "white";
  private String _rowClassUnselected = "white";
  private String _selectorId = null;

  private static final String NA_STRING = "N/A";
  private static final String DISPLAY_AS_EMPTY_STRING = "See Donor Case Report";

  private SimpleDateFormat _dateFormatter = new SimpleDateFormat("MM/dd/yyyy");

  /**
   * no-arg constructor for BTX framework use.  Caller must call populateFromBtxDetails if this is used.
   */
  public SampleSelectionHelper() {
  }

  public SampleSelectionHelper(ProductDto ssData) {
    setSsData(ssData);
  }

  static {
    _sampleStatusMap = new HashMap();
    _sampleStatusMap.put(FormLogic.SMPL_COCONSUMED, "CON");
    _sampleStatusMap.put(FormLogic.SMPL_ARCOCASEPULL, "CPL");
    _sampleStatusMap.put(FormLogic.SMPL_ARCOOTHER, "OTH");
    _sampleStatusMap.put(FormLogic.SMPL_BOXSCAN, "INR");
    _sampleStatusMap.put(FormLogic.SMPL_ARMVTOPATH, "HST");
    _sampleStatusMap.put(FormLogic.SMPL_CORND, "R&D");
    _sampleStatusMap.put(FormLogic.SMPL_MICOOTHER, "MIO");
    _sampleStatusMap.put(FormLogic.SMPL_RNDREQUEST, "R&D");
    _sampleStatusMap.put(FormLogic.SMPL_QCAWAITING, "WTG");
    _sampleStatusMap.put(FormLogic.SMPL_QCINPROCESS, "PRC");
    _sampleStatusMap.put(FormLogic.SMPL_QCVERIFIED, "VER");
    _sampleStatusMap.put(FormLogic.SMPL_REQUESTED, "REQ");
    _sampleStatusMap.put(FormLogic.SMPL_CHECKEDOUT, "OUT");
    _sampleStatusMap.put(FormLogic.SMPL_TRANSFER, "TFR");
  }

  private void setSsData(ProductDto ssData) {
    
    _ssData = ssData;
    _sampleData = _ssData.getSampleData();
    //_peData = _ssData.getPathologyEvaluationData();
    _consentData = _ssData.getConsentData();
    _sectionData = _ssData.getSectionData();
    _pathData = _ssData.getPathData();
    _asmData = _ssData.getAsmData();
    _orderData = _ssData.getOrderData();
    _rnaData = _ssData.getRnaData();
    _peData = _ssData.getPathologyEvaluationData();
    _projectData = _ssData.getProjectData();
    _donorData = _ssData.getDonorData();
    
    // now set some values that are useful in formatting or displaying proper data in getters

    String appearance = _sampleData.getAppearanceBest();
    _appearanceNormal =
      (Constants.APPEARANCE_MICRO_NORMAL.equals(appearance)
        || Constants.APPEARANCE_MICRO_LESION.equals(appearance)
        || Constants.APPEARANCE_GROSS_NORMAL.equals(appearance))
        ? true
        : false;

    String bestDx = getBestCaseDiagnosis();
    _neoplasticDx =
      (!ApiFunctions.isEmpty(bestDx)) && BigrGbossData.isTopLevelDiagnosisNeoplastic(bestDx);
  }

  public void populateFromBtxDetails(BTXDetails btx) {
    ProductDto ssData = ((BTXDetailsGetSamples) btx).getSingleData();
    setSsData(ssData);
  }

  public ProductDto getProductData() {
    return _ssData;
  }

  public RnaData getRnaDataObject() {
    return _rnaData;
  }

  public String getSampleId() {
    String value = (_sampleData == null) ? null : _sampleData.getSampleId();
    return (value == null) ? "" : value;
  }

  /**
   * @return the user-viewable description of this sample (case/asm for tissue, case/prep for RNA)
   */
  public String getDisplayName() {
    return isRna() ? getConsentId() + '-' + getRnaPrep() // RNA case-prep
    : getConsentId() + '-' + getAsmPosition(); // Tissue case-ASM
  }

  /**
   * @return the id of the sample.  If it is RNA return the vial ID, if tissue, the sample ID.
   */
  public String getId() {
    return isRna() ? getRnaVialId() : getSampleId();
  }

  public String getProductType() {
    return GbossFactory.getInstance().getDescription(getSampleType());
  }

  public boolean isRna() {
    return _rnaData != null;
  }

  public String getSampleType() {
    String sampleType = null;
    if (_sampleData != null) {
      sampleType = _sampleData.getSampleTypeCui();
    }
    if ((sampleType == null) && (_rnaData != null)) {
      sampleType = _rnaData.getSampleType();
    }
    return (sampleType == null) ? "" : sampleType;
  }
  
  public String getSampleBoxLocation() {
    String sampleBoxLocation = null;
    BTXBoxLocation storgeLocation = null;
    
    if (_sampleData != null) {
      storgeLocation = _sampleData.getStorageLocation();
    }
    if ((sampleBoxLocation == null) && (_rnaData != null)) {
      storgeLocation = _rnaData.getStorageLocation();
    }
   
    if(storgeLocation != null) {
    sampleBoxLocation = "LOCATION: "+ (storgeLocation.getLocationAddressID()==null?"":storgeLocation.getLocationAddressID()) +"<br>"+
    "ROOM: "+ (storgeLocation.getRoomID()==null?"":storgeLocation.getRoomID()) +"<br>"+
    "UNIT: "+ (storgeLocation.getUnitName()==null?"":storgeLocation.getUnitName())+"<br>"+
    "DRAWER-SLOT: "+ (storgeLocation.getDrawerID()== null?"":storgeLocation.getDrawerID()) +"-"+(storgeLocation.getSlotID()==null?"":storgeLocation.getSlotID())+"<br>"+
    "BOX ID: "+(storgeLocation.getBoxBarcodeID()==null?"":storgeLocation.getBoxBarcodeID());
    }
    
    return (sampleBoxLocation == null) ? "" : sampleBoxLocation;
  }

  public String getSampleTypeDisplay() {
    String sampleType = getSampleType();
    return (ApiFunctions.isEmpty(sampleType))
      ? ""
      : GbossFactory.getInstance().getDescription(sampleType);
  }

  public String getSampleFormatDetail() {
    String value = (_sampleData == null) ? null : _sampleData.getFormatDetail();
    return (value == null) ? "" : GbossFactory.getInstance().getDescription(value);
  }

  public String getSampleFixativeType() {
    String value = (_sampleData == null) ? null : _sampleData.getFixativeType();
    return (value == null) ? "" : GbossFactory.getInstance().getDescription(value);
  }

  public String getSampleSectionCount() {
    String value = (_sampleData == null) ? null : _sampleData.getSectionCount();
    return (value == null) ? "" : value;
  }

  public String getSampleSource() {
    String value = (_sampleData == null) ? null : _sampleData.getSource();
    return (value == null) ? "" : value;
  }

  public boolean isVerified() {
    return (_sampleData != null) ? _sampleData.isQcVerified() : false;
  }

  public boolean isSampleRestricted() {
    return (_sampleData != null) ? _sampleData.isRestricted() : false;
  }

  /**
   * @return true if this item (sample, RNA, ...) is a BMS item.
   * @see #isBmsSample()
   */
  public boolean isBms() {
    return _ssData.isBms();
  }

  /**
   * @return true if the sample associated with this item is a BMS sample.  The sample
   *    associated with the item is the sample itself for samples, and is the representative
   *    sample of the derivative for derivatives (e.g. RNA).
   * @see #isBms()
   */
  public boolean isBmsSample() {
    return (_sampleData != null) ? _sampleData.isBms() : false;
  }

  public String getBmsYesNoDisplayText() {
    return (isBms() ? FormLogic.DB_YES_TEXT : FormLogic.DB_NO_TEXT);
  }

  public String getConsentId() {
    String value = "";
    if (_consentData != null) {
      value = _consentData.getConsentId();
      value = (value == null) ? "" : value;
    }
    return value;
  }

  public String getConsentLocation() {
    String value = "";
    if (_consentData != null) {
      value = _consentData.getLocation();
      value = (value == null) ? "" : value;
    }
    return value;
  }

  public String getDiagnosticResult() {

    StringBuffer buff = new StringBuffer();
    boolean start = true;
    if (_consentData != null) {
      if (_consentData.getDiagnosticTestResultData() != null) {
        Iterator iter = _consentData.getDiagnosticTestResultData().iterator();
        while (iter.hasNext()) {
          DiagnosticTestResultData data = (DiagnosticTestResultData) iter.next();
          String conceptCid = data.getTestCid();
          String resultCid = data.getResultCid();
          String concept = null;
          String result = null;

          if (!ApiFunctions.isEmpty(conceptCid)) {
            concept = GbossFactory.getInstance().getDescription(conceptCid);
          }
          if (!ApiFunctions.isEmpty(resultCid)) {
            result = GbossFactory.getInstance().getDescription(resultCid);
          }
          if (!start) {
            buff.append("<br>");
          }
          buff.append(concept);
          buff.append(": ");
          buff.append(result);
          if (start) {
            start = false;
          }
        }
      }

    }
    return buff.toString();
  }

  public String getDonorId() {
    String value = "";
    if (_consentData != null) {
      value = _consentData.getDonorId();
      value = (value == null) ? "" : value;
    }
    return value;
  }

  public String getDonorRace() {
    String value = null;
    String cui = null;
    GbossValueSet vSet = null;
    GbossValueSets vSets = null;
    
    
    if (_consentData != null ) {
      
      if(_consentData.getDonorData() != null) {
      cui = _consentData.getDonorData().getRace();
      
    
   
      vSets=GbossFactory.getInstance().getValueSets();
      
      for( Iterator vItr =vSets.getValueSets().iterator(); vItr.hasNext();) {
          vSet =  (GbossValueSet) vItr.next();
          
           if (ColumnConstants._RACE.equals(vSet.getDescription()) && cui != null) {
               
                value = vSet.getValue(cui).getDescription();
                break;
           }
      }
            
      }
      
      value = (value == null) ? "" : value;
    }
    return value;
  }
  
  public String getPathReportId() {
    String value = "";
    if (_pathData != null) {
      value = _pathData.getPathReportId();
      value = (value == null) ? "" : value;
    }
    return value;
  }

  public String getAsmPosition() {
    String value = (_sampleData == null) ? null : _sampleData.getAsmPosition();
    return (value == null) ? "" : value;
  }

  public String getSampleAlias() {
    String value = (_sampleData == null) ? null : _sampleData.getSampleAlias();
    return (value == null) ? "" : value;
  }

  public String getConsentCustomerId() {
    String value = (_consentData == null) ? null : _consentData.getCustomerId();
    return (value == null) ? "" : value;
  }

  public String getDonorCustomerId() {
    String value = (_consentData == null) ? null : _consentData.getDonorCustomerId();
    return (value == null) ? "" : value;
  }

  public String getHistoNotes() {
    String value = (_sampleData == null) ? null : _sampleData.getHistoNotes();
    return (value == null) ? "" : value;
  }

  public String getDonorCommentsShort() {
    return shorterString(getDonorComments(), MAX_NOTES_LENGTH);
  }

  public String getDonorComments() {
    StringBuffer buff = new StringBuffer(50);
    if (_sampleData != null && !ApiFunctions.isEmpty(_sampleData.getAsmNote())) {
      buff.append(_sampleData.getAsmNote());
    }
    if (_asmData != null && !ApiFunctions.isEmpty(_asmData.getModuleComments())) {
      if (buff.length() > 0) {
        buff.append("\n-------\n");
      }
      buff.append(_asmData.getModuleComments());
    }

    return buff.toString();
  }

  public String getGender() {
    String value = "";
    if (_consentData != null) {
      value = _consentData.getGender();
      if (Constants.GENDER_FEMALE.equals(value)) {
        value = "Female";
      }
      else if (Constants.GENDER_MALE.equals(value)) {
        value = "Male";
      }
      else {
        value = "";
      }
    }
    return value;
  }

  public String getAgeAtCollection() {
    return IltdsUtils.getSampleAgeAtCollection(getSampleId());
  }

  public String getAppearanceBest() {
    String value = "";
    if (_sampleData != null) {
      String code = _sampleData.getAppearanceBest();
      value = (ApiFunctions.isEmpty(code)) ? "" : GbossFactory.getInstance().getDescription(code);
    }
    return value;
  }

  public String getStageGrouping() {
    String value = "";

    String code = _sectionData == null ? null : _sectionData.getStageGrouping();
    if (!_neoplasticDx || _appearanceNormal) {
      value = NA_STRING;
    }
    else if (ApiFunctions.isEmpty(code)) {
      //MR7893
      if (!ApiFunctions.isEmpty(ValidateIds.validateId(_sampleData.getSampleId(), ValidateIds.TYPESET_SAMPLE_IMPORTED, false))) {
        value = NA_STRING;
      }
      else {
        value = "in process";
      }
    }
    else if (ArtsConstants.isDisplayLookupAsEmpty(code)) {
      value = DISPLAY_AS_EMPTY_STRING;
    }
    else {
      value = GbossFactory.getInstance().getDescription(code);
    }

    return value;
  }

  public String getTumorStageType() {
    String value = "";
    if (_sectionData != null) {
      String code = _sectionData.getTumorStageType();
      if (!ApiFunctions.isEmpty(code)) {
        value = ArtsConstants.getTumorStageType(code);
      }
    }
    return value;
  }

  public String getTumorStageDesc() {
    String value = "";
    if (_sectionData != null) {
      String code = _sectionData.getTumorStageDesc();
      if (!ApiFunctions.isEmpty(code)) {
        value = GbossFactory.getInstance().getDescription(code);
      }
    }
    return value;
  }

  public String getDonorConsentCount() {
    String value = "";
    if (_donorData != null) {
      if (!ApiFunctions.isEmpty(_donorData.getConsentCount())
        && !"1".equals(_donorData.getConsentCount())) {
        value = _donorData.getConsentCount();
      }

    }
    return value;
  }

  public String getLymphNodeStage() {
    String value = "";
    if (_sectionData != null) {
      String code = _sectionData.getLymphNodeStage();
      if (!ApiFunctions.isEmpty(code)) {
        value = GbossFactory.getInstance().getDescription(code);
      }
    }
    return value;
  }

  public String getDistantMetastasis() {
    String value = "";
    if (_sectionData != null) {
      String code = _sectionData.getDistantMetastasis();
      if (!ApiFunctions.isEmpty(code)) {
        value = GbossFactory.getInstance().getDescription(code);
      }
    }
    return value;
  }

  /**
   * Return a string describing TNM -- Tumor, lymph Node, distand Metastisis.
   * If the sample is not diseased (because the case is not, or this particular sample is
   * normal tissue from a diseased case) show "N/A".
   * If the sample is diseased tissue, but the data is not there, indicate that the evaluation or
   * data entry process is not complete.
   * Otherwise (it is a diseased sample that we have data for) show the TNM description.
   */
  public String getTnm() {
    String value;
    String disp = getTnmDisplayString();
    if (!_neoplasticDx || _appearanceNormal) {
      value = NA_STRING;
    }
    else if (ApiFunctions.isEmpty(disp)) {
      //MR7893
      if (!ApiFunctions.isEmpty(ValidateIds.validateId(_sampleData.getSampleId(), ValidateIds.TYPESET_SAMPLE_IMPORTED, false))) {
        value = NA_STRING;
      }
      else {
        value = "in process";
      }
    }
    else {
      value = disp;
    }
    return value;
  }

  // return the string for TNM, or null if there is no data
  private String getTnmDisplayString() {
    if (_tnmDisplayString == null) {
      if (_appearanceNormal || _sectionData == null) {
        return null;
      }

      String rawType = _sectionData.getTumorStageType();
      String rawT = _sectionData.getTumorStageDesc();
      String rawN = _sectionData.getLymphNodeStage();
      String rawM = _sectionData.getDistantMetastasis();

      // all null?
      if (rawT == null && rawN == null && rawM == null && rawType == null) {
        return null;
      }

      StringBuffer buf = new StringBuffer(64);

      if (ArtsConstants.isLookupNotReported(rawType)
        || ArtsConstants.isLookupNotReported(rawT)
        || ArtsConstants.isLookupNotReported(rawN)
        || ArtsConstants.isLookupNotReported(rawM)) {
        buf.append("Not Reported");
      }
      else if (
        ArtsConstants.isDisplayLookupAsEmpty(rawType)
          || ArtsConstants.isDisplayLookupAsEmpty(rawT)
          || ArtsConstants.isDisplayLookupAsEmpty(rawN)
          || ArtsConstants.isDisplayLookupAsEmpty(rawM)) {
        buf.append(DISPLAY_AS_EMPTY_STRING);
      }
      else {

        // Type/Classification System
        if (!ApiFunctions.isEmpty(rawType) && !ArtsConstants.isDisplayLookupAsEmpty(rawType)) {
          buf.append(getTumorStageType());
          buf.append("; ");
        }

        //if the classification system is Ann Arbor, return pNot applicable
        if (rawType.equals(ArtsConstants.CODE_TUMOR_STAGE_TYPE_ANNARBOR)) {
          buf.append("pNot applicable");
          _tnmDisplayString = buf.toString();
          return _tnmDisplayString;
        }

        boolean pAdded = false;

        // T
        if (!ApiFunctions.isEmpty(rawT) && !ArtsConstants.isDisplayLookupAsEmpty(rawT)) {
          String t = getTumorStageDesc();
          if (!pAdded) {
            buf.append("p");
            pAdded = true;
          }
          int i = t.indexOf(':');
          if (i > 0) {
            t = t.substring(i + 2);
          }
          buf.append(t);
        }

        // N
        if (!ApiFunctions.isEmpty(rawN) && !ArtsConstants.isDisplayLookupAsEmpty(rawN)) {
          String n = getLymphNodeStage();
          if (!pAdded) {
            buf.append("p");
            pAdded = true;
          }
          int i = n.indexOf(':');
          if (i > 0) {
            n = n.substring(i + 2);
          }
          buf.append(n);
        }

        // M
        if (!ApiFunctions.isEmpty(rawM) && !ArtsConstants.isDisplayLookupAsEmpty(rawM)) {
          if (!pAdded) {
            buf.append("p");
            pAdded = true;
          }
          buf.append(getDistantMetastasis());
        }
      }
      _tnmDisplayString = buf.toString();
    }
    return _tnmDisplayString;
  }

  public String getHistologicalNuclearGrade() {
    String value;
    String code = getHistologicalNuclearGradeDisplayString();
    if (!_neoplasticDx || _appearanceNormal) {
      value = NA_STRING;
    }
    else if (ApiFunctions.isEmpty(code)) {
      //MR7893
      if (!ApiFunctions.isEmpty(ValidateIds.validateId(_sampleData.getSampleId(), ValidateIds.TYPESET_SAMPLE_IMPORTED, false))) {
        value = NA_STRING;
      }
      else {
        value = "in process";
      }
    }
    else {
      value = code;
    }
    return value;
  }

  private String getHistologicalNuclearGradeDisplayString() {
    if (_appearanceNormal || _sectionData == null) {
      return null;
    }

    Integer gleasonPrimary = _sectionData.getGleasonPrimary();
    Integer gleasonSecondary = _sectionData.getGleasonSecondary();
    Integer gleasonTotal = _sectionData.getGleasonTotal();
    String rawHng = _sectionData.getHistologicalNuclearGrade();

    // all null?    
    if (gleasonPrimary == null
      && gleasonSecondary == null
      && gleasonTotal == null
      && rawHng == null) {
      return null;
    }

    StringBuffer buf = new StringBuffer(40);

    // If gleason score is present, then display it.
    if ((gleasonPrimary != null) || (gleasonSecondary != null) || (gleasonTotal != null)) {
      buf.append("Gleason Score: ");
      buf.append(gleasonPrimary.toString());
      buf.append('+');
      buf.append(gleasonSecondary.toString());
      buf.append('=');
      buf.append(gleasonTotal.toString());
      buf.append("/10");
    }

    // If HNG is present, display it. 
    // Both HNG and Gleason are only present for Neoplasm of Prostate = '126906006'
    if (rawHng != null) {
      if (buf.length() > 0) {
        buf.append("<br/>"); // break if both HNG and Gleason
      }
      if (ArtsConstants.isLookupNotReported(rawHng)) {
        buf.append("Not Reported");
      }
      else if (ArtsConstants.isDisplayLookupAsEmpty(rawHng)) {
        buf.append(DISPLAY_AS_EMPTY_STRING);
      }
      else {
        buf.append(GbossFactory.getInstance().getDescription(rawHng));
      }
    }
    String value = buf.toString();
    return value;
  }

  // -----------  composition values from sample -----------
  public String getNormal() {
    if (_rnaData == null) {
      return (_peData == null ? "" : String.valueOf(_peData.getNormalCells()));
    }
    else {
      Integer value = _rnaData.getNormalCells();
      return (value == null ? "" : String.valueOf(value));
    }
  }

  public String getAcellularStroma() {
    if (_rnaData == null) {
      return (_peData == null ? "" : String.valueOf(_peData.getHypoacellularStromaCells()));
    }
    else {
      Integer value = _rnaData.getAcellularstromaCells();
      return (value == null ? "" : String.valueOf(value));
    }
  }

  public String getCellularStroma() {
    if (_rnaData == null) {
      return (_peData == null ? "" : String.valueOf(_peData.getCellularStromaCells()));
    }
    else {
      Integer value = _rnaData.getCellularstromaCells();
      return (value == null ? "" : String.valueOf(value));
    }
  }

  public String getLesion() {
    if (_rnaData == null) {
      return (_peData == null ? "" : String.valueOf(_peData.getLesionCells()));
    }
    else {
      Integer value = _rnaData.getLesionCells();
      return (value == null ? "" : String.valueOf(value));
    }
  }

  public String getNecrosis() {
    if (_rnaData == null) {
      return (_peData == null ? "" : String.valueOf(_peData.getNecrosisCells()));
    }
    else {
      Integer value = _rnaData.getNecrosisCells();
      return (value == null ? "" : String.valueOf(value));
    }
  }

  public String getTumor() {
    if (_rnaData == null) {
      return (_peData == null ? "" : String.valueOf(_peData.getTumorCells()));
    }
    else {
      Integer value = _rnaData.getTumorCells();
      return (value == null ? "" : String.valueOf(value));
    }
  }

  // -------------------------------------------------------------------------------------

  public String getLimsDiagnosis() {
    String value = "";
    if (_peData != null) {
      String other = _peData.getDiagnosisOther();
      if (other != null) {
        value = "Other: " + other;
      }
      else {
        String code = _peData.getDiagnosis();
        value = (code == null) ? "" : BigrGbossData.getDiagnosisDescription(code);
      }
    }
    return value;
  }

  public String getTissueOfOrigin() {
    String value = "";
    if (_peData != null) {
      String other = _peData.getTissueOfOriginOther();
      if (other != null) {
        value = "Other: " + other;
      }
      else {
        String code = _peData.getTissueOfOrigin();
        value = (code == null) ? "" : BigrGbossData.getTissueDescription(code);
      }
    }
    return value;
  }

  public String getTissueOfFinding() {
    String value = "";
    if (_peData != null) {
      String other = _peData.getTissueOfFindingOther();
      if (other != null) {
        value = "Other: " + other;
      }
      else {
        String code = _peData.getTissueOfFinding();
        value = (code == null) ? "" : BigrGbossData.getTissueDescription(code);
      }
    }
    return value;
  }

  public String getExternalData() {
    String value = "";
    if (_rnaData != null) {
      value = _rnaData.getExternalComments();
      value = (value == null) ? "" : value;
    }
    else {
      if (_peData != null) {
        value = _peData.getConcatenatedExternalData();
        value = (value == null) ? "" : value;
      }
    }
    return value;
  }

  public String getExternalDataHtml() {
    return Escaper.htmlEscape(getExternalData());
  }

  public String getExternalDataJs() {
    return Escaper.jsEscapeInXMLAttr(getExternalData());
  }

  public String getExternalDataShort() {
    return shorterString(getExternalData(), MAX_NOTES_LENGTH);
  }

  public boolean isExternalDataTooLong() {
    return getExternalData().trim().length() > MAX_NOTES_LENGTH;
  }

  public String getLogicalRepositoryShortNamesShort() {
    return shorterString(getLogicalRepositoryShortNames(), MAX_LOGICAL_REPOSITORY_LENGTH);
  }

  public boolean isLogicalRepositoryShortNamesTooLong() {
    return getLogicalRepositoryShortNames().trim().length() > MAX_LOGICAL_REPOSITORY_LENGTH;
  }

  private String shorterString(String s, int maxlength) {
    if (s == null) {
      return "";
    }
    String t = s.trim();
    int length = t.length();
    if (length < maxlength) {
      return t;
    }
    else {
      StringBuffer sb = new StringBuffer(maxlength + 3);
      sb.append(t.substring(0, maxlength));
      sb.append("...");
      return sb.toString();
    }
  }

  public String getInternalData() {
    String value = "";
    if (_rnaData != null) {
      value = _rnaData.getInternalComments();
      value = (value == null) ? "" : value;
    }
    else {
      if (_peData != null) {
        value = _peData.getConcatenatedInternalData();
        value = (value == null) ? "" : value;
      }
    }
    return value;
  }

  //    public String getMicroscopicAppearanceName() {
  //        String value = "";
  //        if (_peData != null) {
  //            value = _peData.getMicroscopicAppearanceName();
  //            value = (value == null) ? "" : value;
  //        }
  //      return value;
  //    }

  public String getInternalDataHtml() {
    return Escaper.htmlEscape(getInternalData() + " " + getHistoNotes());
  }

  public String getInternalDataJs() {
    return Escaper.jsEscapeInXMLAttr(getInternalData() + " " + getHistoNotes());
  }

  public String getInternalDataShort() {
    String value = "";
    value = getInternalData() + " " + getHistoNotes();
    if (isInternalDataTooLong()) {
      return value.substring(0, MAX_NOTES_LENGTH) + " ...";
    }
    else {
      return value.trim();
    }
  }

  public boolean isInternalDataTooLong() {
    int length = getInternalData().trim().length() + getHistoNotes().trim().length();
    return (length > MAX_NOTES_LENGTH);
  }

  public String getKcDataShort(ColumnDescriptor columnDescriptor) {
    String value = getKcDataValue(columnDescriptor);
    if (isKcDataTooLong(columnDescriptor)) {
      return value.substring(0, MAX_NOTES_LENGTH) + " ...";
    }
    else {
      return value.trim();
    }
  }
  
  public boolean isKcDataTooLong(ColumnDescriptor columnDescriptor) {
    int length = getKcDataValue(columnDescriptor).trim().length();
    return (length > MAX_NOTES_LENGTH);
  }

  /**
   * Get the code for the best value for case diagnosis.  Note that LIMS diagnosis 
   * for this individual sample may be more specific, but this is our best value for the
   * illness that corresponds to this case.
   */
  private String getBestCaseDiagnosis() {
    String value = "";
    if (_sectionData != null) {
      value = _sectionData.getDiagnosis();
    }
    if (ApiFunctions.isEmpty(value)) {
      value = (_consentData == null ? "" : _consentData.getDiagnosis());
    }
    return ApiFunctions.safeString(value);
  }

  public String getDdcDiagnosis() {
    String value = "";
    if (_sectionData != null) {
      String other = _sectionData.getDiagnosisOther();
      if (other != null) {
        value = "Other: " + other;
      }
      else {
        String code = _sectionData.getDiagnosis();
        value = (code == null) ? "" : BigrGbossData.getDiagnosisDescription(code);
      }
    }
    return value;
  }

  public String getDdcTissueOriginName() {
    String value = "";
    if (_sectionData != null) {
      value = _sectionData.getTissueOriginName();
    }
    return ApiFunctions.safeString(value);
  }

  public String getDdcTissueFindingName() {
    String value = "";
    if (_sectionData != null) {
      value = _sectionData.getTissueFindingName();
    }
    return ApiFunctions.safeString(value);
  }

  public String getIltdsDiagnosis() {
    String value = "";
    if (_consentData != null) {
      String other = _consentData.getDiagnosisOther();
      if (other != null) {
        value = "Other: " + other;
      }
      else {
        value = _consentData.getDiagnosisName();
      }
    }
    return value;
  }

  public String getAsmTissue() {
    String value = "";
    if (_asmData != null) {
      String other = _asmData.getTissueOther();
      if (other != null) {
        value = "Other: " + other;
      }
      else {
        String code = _asmData.getTissue();
        value = (code == null) ? "" : BigrGbossData.getTissueDescription(code);
      }
    }
    return value;
  }

  public String getAsmProcedure() {
    String value = "";
    if (_asmData != null) {
      value = _asmData.getProcedureName();
    }
    return value;
  }

  public String getAsmPreparedBy() {
    String value = "";
    if (_asmData != null) {
      value = _asmData.getPreparedByName();
    }
    return value;
  }

  public String getSampleTissueOfOrigin() {
    String value = "";
    if (_sampleData != null) {
      value = _sampleData.getTissueOriginName();
    }
    return value;
  }

  public String getRnaVialId() {
    String value = (_rnaData == null) ? null : _rnaData.getRnaVialId();
    return (value == null) ? "" : value;
  }

  public String getRnaStatus() {
    String value = (_rnaData == null) ? null : _rnaData.getRnaStatus();
    return (value == null) ? "" : value;
  }

  public String getRnaPooledTissue() {
    // null or anything but "-1" displays as blank, if "-1", mark P for "Pooled"
    String value = (_rnaData == null) ? null : _rnaData.getPooledTissue();
    return ("-1".equals(value)) ? "P" : "";
  }

  public String getRnaCaseExhausted() {
    // null or anything but "Y" displays as blank, if "Y", mark E for "exhausted"
    String value = (_rnaData == null) ? null : _rnaData.getCaseExhausted();
    return ("Y".equals(value)) ? "E" : "";
  }

  public String getRnaConsentId() {
    String value = (_rnaData == null) ? null : _rnaData.getConsentId();
    return (value == null) ? "" : value;
  }

  public String getRnaPrep() {
    String value = (_rnaData == null) ? null : _rnaData.getPrep();
    return (value == null) ? "" : value;
  }

  public String getRnaQuality() {
    Integer value = (_rnaData == null) ? null : _rnaData.getQuality();
    return (value == null) ? "" : value.toString();
  }

  public String getRnaRatio() {
    BigDecimal value = (_rnaData == null) ? null : _rnaData.getRatio();
    return (value == null) ? "" : value.toString();
  }

  public String getRnaAmountAvailable() {
    Integer value = (_rnaData == null) ? null : _rnaData.getRnaAmountAvailable();
    return (value == null) ? "" : value.toString();
  }

  public String getRnaAmountRemaining() {
    Integer value = (_rnaData == null) ? null : _rnaData.getRnaAmountRemaining();
    return (value == null) ? "" : value.toString();
  }

  public String getRnaProject() {
    String value = (_rnaData == null) ? null : _rnaData.getRnaProject();
    return (value == null) ? "" : value;
  }

  public String getRnaHoldProject() {
    String value = (_rnaData == null) ? null : _rnaData.getRnaHoldProject();
    return (value == null) ? "" : value;
  }

  public String getRnaNotes() {
    String value = (_rnaData == null) ? null : _rnaData.getNotes();
    return (value == null) ? "" : value;
  }

  public String getRnaNotesHtml() {
    return Escaper.htmlEscape(getRnaNotes());
  }

  public String getRnaNotesJs() {
    return Escaper.jsEscapeInXMLAttr(getRnaNotes());
  }

  public String getRnaNotesShort() {
    return shorterString(getRnaNotes(), MAX_NOTES_LENGTH);
  }

  public boolean isRnaNotesTooLong() {
    return getRnaNotes().trim().length() > MAX_NOTES_LENGTH;
  }

  public String getRnaComments() {
    String value = "";
    if (_rnaData != null) {
      value = _rnaData.getExternalComments();
      return value = (value == null) ? "" : value;
    }
    else {
      if (_peData == null)
        return "";
      else {
        value = _peData.getConcatenatedExternalData();
        return value == null ? "" : value;
      }
    }
  }

  public String getInventoryStatus() {
    String value = (_sampleData == null) ? "" : _sampleData.getInvStatus();
    if (FormLogic.SMPL_COCONSUMED.equals(value)
      || FormLogic.SMPL_CORND.equals(value)
      || FormLogic.SMPL_ARCOOTHER.equals(value)) {
      if (_sampleData.getRnaVialId() != null) {
        String vialId = _sampleData.getRnaVialId();
        if (vialId.startsWith("RN")) {
          return "RNA";
        }
        else if (vialId.startsWith("DN")) {
          return "DNA";
        }
        else if (vialId.startsWith("PL")) {
          return "PRO";
        }
        else {
          return "DER";
        }
      }
      else if (_sampleData.getTmaId() != null) {
        return "TMA";
      }
      else if (_sampleData.isSubdivided()) {
        return "SUB";
      }
      else {
        return ApiFunctions.safeString((String) _sampleStatusMap.get(value));
      }
    }
    else if (_sampleData != null && _sampleData.getBoxBarcodeId() == null) {
      return "OUT";
    }
    else {
      return (value == null) ? "" : ApiFunctions.safeString((String) _sampleStatusMap.get(value));
    }
  }

  public String getProjectStatus() {
    String value = (_sampleData == null) ? null : _sampleData.getProjectStatus();
    return (value == null) ? "" : value;
  }

  /**
   * 
   * Return which of the statuses the sample has attained (somewhat equivalent to how 
   * well prepared and verified the sample has become).  Statuses in ascending order are:
   * WTG, VER, REL.  Posted and Pulled are similar, but posted is in the qcPosted() method,
   * and Pulled status clears all other statuses, and is not part of the normal progression.
   * 
   * @return the status prefixed with a lowercase p if the sample is In Process (meaning it
   * is scheduled for PV or re-PV).
   */
  public String getQcStatus() {
    if (_sampleData == null || ApiFunctions.isEmpty(_sampleData.getQcStatus()))
      return "";

    boolean inProc = _sampleData.getQcStatus().equals(FormLogic.SMPL_QCINPROCESS);
    String qcStat;
    if (_sampleData.isPulled()) {
      qcStat = "PUL";
    }
    else if (_sampleData.isReleased())
      qcStat = "REL";
    else if (_sampleData.isQcVerified())
      qcStat = "VER";
    else
      qcStat = "WTG"; // waiting

    if (inProc)
      qcStat = "p" + qcStat;

    return qcStat;
  }

  //method to get the logical repository short names
  public String getLogicalRepositoryShortNames() {
    List logicalRepositories = _ssData.getLogicalRepositories();
    if (logicalRepositories == null || logicalRepositories.isEmpty()) {
      return "";
    }
    else {
      StringBuffer buff = new StringBuffer(50);
      Iterator iterator = logicalRepositories.iterator();
      boolean includeComma = false;
      while (iterator.hasNext()) {
        LogicalRepository lr = (LogicalRepository) iterator.next();
        if (includeComma) {
          buff.append(", ");
        }
        buff.append(lr.getShortName());
        includeComma = true;
      }
      return buff.toString();
    }
  }

  public String getPullDetails() {
    if (_sampleData == null)
      return "";

    if (!_sampleData.isPulled())
      return "";

    String rsn = _sampleData.getPullReason(); // might be quite long
    StringBuffer sb = new StringBuffer(rsn.length() + 12);
    sb.append(rsn);
    sb.append(" - ");
    sb.append(_sampleData.getPulledDate());
    return sb.toString();
  }

  public boolean isPulled() {
    return (_sampleData != null) && _sampleData.isPulled();
  }

  public String getQcPosted() {
    if (_sampleData == null)
      return "";
    String status = (_sampleData.isQCPosted()) ? "Y" : "";
    return status;
  }

  public String getSalesStatus() {
    String value = (_sampleData == null) ? null : _sampleData.getSalesStatus();
    return (value == null) ? "" : value;
  }

  public String getPsa() {
    String value = "";
    if (_consentData != null) {
      value = _consentData.getPsa();
    }
    return value;
  }

  public String getDre() {
    String value = "";
    if (_consentData != null) {
      String cui = _consentData.getDreCid();
      if (!ApiFunctions.isEmpty(cui)) {
        value = GbossFactory.getInstance().getDescription(cui); 
      }
    }
    return value;
  }


  public String getProject() {
    String value = "";
    if (_projectData != null) {
      value = _projectData.getProjectName();
      value = (value == null) ? "" : value;
    }
    return value;
  }

  public String getProjectRequestDate() {
    String value = "";
    if (_projectData != null) {
      java.sql.Date date = _projectData.getRequestDate();
      value = (date == null) ? "" : _dateFormatter.format(date);
    }
    return value;
  }

  public String getProjectDaysSinceRequestDate() {
    if (_projectData != null) {
      java.sql.Date date = _projectData.getRequestDate();
      if (date != null) {
        long timeDiff = System.currentTimeMillis() - date.getTime();
        timeDiff = timeDiff / 1000;
        timeDiff = timeDiff / 86400;
        if (timeDiff > 999) {
          return "**";
        }
        return timeDiff + "";
      }
    }
    return "";
  }

  /**
   * @return concatenation of user IDs and dates for hold entries against this sample.
   */
  public String getCartEntriesString() {
    StringBuffer sb = new StringBuffer();
    if (_sampleData == null)
      return "";
    List entries = _sampleData.getCartEntries();
    int len = entries.size();
    for (int i = 0; i < len; i++) {
      ShoppingCartData scd = (ShoppingCartData) entries.get(i);
      sb.append(scd.getUserId());
      // put in the amount, if there is one
      Integer amt = scd.getAmount();
      if (amt != null) {
        sb.append('(');
        sb.append(amt);
        sb.append("ug)");
      }
      sb.append("-");
      sb.append(_dateFormatter.format(scd.getCreation()));
      if (i < len - 1)
        sb.append("\n");
    }
    String res = sb.toString();
    return res;
  }

  public String getCartDaysSinceCreationDate() {
    if (_sampleData == null)
      return "";
    List entries = _sampleData.getCartEntries();
    if (entries == null || entries.isEmpty())
      return "";
      
    // If there's more than one shopping cart entry, use the max number of days
    // the item has been on any of the carts.
      
    long now = System.currentTimeMillis();
    long maxTimeDiff = 0;
    Iterator iter = entries.iterator();
    while (iter.hasNext()) {
      ShoppingCartData scd = (ShoppingCartData) iter.next();
      Date date = scd.getCreation();
      long timeDiff = now - date.getTime();
      if (timeDiff > maxTimeDiff) {
        maxTimeDiff = timeDiff;
      }
    }

    // So far maxTimeDiff is in milliseconds, convert it to days.
    maxTimeDiff = maxTimeDiff / 1000;
    maxTimeDiff = maxTimeDiff / 86400;
    if (maxTimeDiff > 999) {
      return "**";
    }
    return maxTimeDiff + "";
  }

  public String getOrderDescription() {
    String value = "";
    if (_orderData != null) {
      value = _orderData.getOrderDescription();
      value = (value == null) ? "" : value;
    }
    return value;
  }

  /**
   * Returns the amountOnHold for the particular user or shopping cart.
   * @return Integer
   */
  public String getAmountOnHold() {
    return _amountOnHold == null ? "" : _amountOnHold.toString();
  }

  /**
   * Sets the amountOnHold for the particular user or shopping cart..
   * @param amountOnHold The amountOnHold 
   */
  public void setAmountOnHold(Integer amountOnHold) {
    _amountOnHold = amountOnHold;
  }

  // =========================================================================
  //          SORTING  CODE
  // =========================================================================

  /**
   * Sort the list of SampleSelectionHelpers by CaseID, and then by ASM
   * @param  helpers the list of SampleSelectionHelpers to sort.
   */
  public static void sortByDonorCaseAsm(List helpers) {
    if (ApiFunctions.isEmpty(helpers)) {
      return;
    }
    Collections.sort(helpers, new DonorCaseASMPrepCompare());
  }

  private static class DonorCaseASMPrepCompare implements Comparator {
    public int compare(Object o1, Object o2) {
      SampleSelectionHelper ssh1 = (SampleSelectionHelper) o1;
      SampleSelectionHelper ssh2 = (SampleSelectionHelper) o2;
      int d = ssh1.getDonorId().compareTo(ssh2.getDonorId());
      if (d == 0) {
        int c = ssh1.getConsentId().compareTo(ssh2.getConsentId());
        if (c == 0) {
          int p = compareAsmPrep(ssh1, ssh2);
          return p;
        }
        else {
          return c;
        }
      }
      else {
        return d;
      }
    }

    // compare Tissue ASM position and RNA preps, with Tissue before RNA, and using
    // numeric order for RNA prep (so 2 comes befor 10)
    private int compareAsmPrep(SampleSelectionHelper ssh1, SampleSelectionHelper ssh2) {
      boolean rna1 = ssh1.isRna();
      boolean rna2 = ssh2.isRna();

      if (!rna1 && !rna2) {
        return ssh1.getAsmPosition().compareTo(ssh2.getAsmPosition());
      }
      else if (!rna1 && rna2) {
        return -1; // samples before RNA
      }
      else if (rna1 && !rna2) {
        return 1; // RNA after samples
      }
      else { // both rna
        int p1 = Integer.parseInt(ssh1.getRnaPrep());
        int p2 = Integer.parseInt(ssh2.getRnaPrep());
        return p1 < p2 ? -1 : p1 == p2 ? 0 : 1;
      }
    }
  }

  /**
   * @return true if the sample is selectable on a display.  This isn't an inherent
   *   property of the sample and this field is only defined in contexts where it
   *   is sets by special context-specific code (such as when displaying a sample
   *   selection results page).
   */
  public boolean isSelectable() {
    return _selectable;
  }

  /**
   * @return true if the sample is selected on a display.  This isn't an inherent
   *   property of the sample and this field is only defined in contexts where it
   *   is sets by special context-specific code (such as when displaying a sample
   *   selection results page).
   */
  public boolean isSelected() {
    return _selected;
  }

  /**
   * @return true if the sample's checkbox is to be disabled on the display.  This isn't an inherent
   *   property of the sample and this field is only defined in contexts where it
   *   is sets by special context-specific code (such as when displaying a sample
   *   selection results page).
   */
  public boolean isSelectionDisabled() {
    return _selectionDisabled;
  }

  /**
   * Set to true if the sample is selectable on a display.  This isn't an inherent
   *   property of the sample and this field is only defined in contexts where it
   *   is sets by special context-specific code (such as when displaying a sample
   *   selection results page).
   * @param b
   */
  public void setSelectable(boolean b) {
    _selectable = b;
  }

  /**
   * Set to true if the sample is selected on a display.  This isn't an inherent
   *   property of the sample and this field is only defined in contexts where it
   *   is sets by special context-specific code (such as when displaying a sample
   *   selection results page).
   * @param b
   */
  public void setSelected(boolean b) {
    _selected = b;
  }

  /**
   * Set to true if the sample's checkbox is to be disabled on the display.  This isn't an inherent
   *   property of the sample and this field is only defined in contexts where it
   *   is sets by special context-specific code (such as when displaying a sample
   *   selection results page).
   * @param b
   */
  public void setSelectionDisabled(boolean b) {
    _selectionDisabled = b;
  }

  /**
   * @return the CSS class name to apply to the sample's table row in a display.  This isn't an
   *   inherent property of the sample and this field is only defined in contexts where it
   *   is sets by special context-specific code (such as when displaying a sample
   *   selection results page).
   */
  public String getRowClass() {
    return _rowClass;
  }

  /**
   * Set to a CSS class name to apply to the sample's table row in a display.  This isn't an
   *   inherent property of the sample and this field is only defined in contexts where it
   *   is sets by special context-specific code (such as when displaying a sample
   *   selection results page).
   * @param string
   */
  public void setRowClass(String string) {
    _rowClass = string;
  }

  /**
   * @return the CSS class name to apply to the sample's table row in a display when it is
   *   not a selected row.  This isn't an
   *   inherent property of the sample and this field is only defined in contexts where it
   *   is sets by special context-specific code (such as when displaying a sample
   *   selection results page).
   */
  public String getRowClassUnselected() {
    return _rowClassUnselected;
  }

  /**
   * Set to a CSS class name to apply to the sample's table row in a display when it is
   *   not a selected row.  This isn't an
   *   inherent property of the sample and this field is only defined in contexts where it
   *   is sets by special context-specific code (such as when displaying a sample
   *   selection results page).
   * @param string
   */
  public void setRowClassUnselected(String string) {
    _rowClassUnselected = string;
  }

  /**
   * @return The id of the control for selecting this sample's row.  This will be set to a
   *   unique id when this is called if its current value is empty.
   */
  public String getSelectorId() {
    if (ApiFunctions.isEmpty(_selectorId)) {
      setSelectorId(IdGenerator.genUniqueId("c"));
    }
    return _selectorId;
  }

  /**
   * @param string The id of the control for selecting this sample's row.
   */
  public void setSelectorId(String string) {
    _selectorId = string;
  }

  public String getProjectDescriptionAsText() {
    String projectDescription = null;

    StringBuffer result = new StringBuffer(128);
    String userAndDate = getCartEntriesString();

      if (isRna()) {
        String holdProj = getRnaHoldProject();
        boolean hasHoldProj = !ApiFunctions.isEmpty(holdProj);
        boolean hasUserAndDt = !ApiFunctions.isEmpty(userAndDate);
        boolean showProject = false;
        result.append(holdProj);
        if (hasHoldProj && hasUserAndDt) { // comma if we have both
          result.append(", ");
        }
        if (hasUserAndDt) {
          result.append("H");
          result.append(getCartDaysSinceCreationDate());
        }
      }
      else { // not isRna

        if (getProject() != "") {
          result.append(getProject()
              + " - "
              + getProjectRequestDate());
          result.append(" P");
          result.append(getProjectDaysSinceRequestDate());
        }
        else if (userAndDate.length() > 0) {
          result.append("H");
          result.append(getCartDaysSinceCreationDate());
        }
        else if (getSalesStatus().equals(FormLogic.SMPL_ADDTOCART)) {
          result.append("H");
        }
        else if (getProjectStatus().equals("PRJADDED")) {
          result.append("P");
        }
      }

    projectDescription = result.toString();    
    return projectDescription;
    
  }

  public String getProjectDescriptionAsHtml() {
    String projectDescription = null;

    StringBuffer result = new StringBuffer(128);
    String userAndDate = getCartEntriesString();

      if (isRna()) {
        String holdProj = getRnaHoldProject();
        boolean hasHoldProj = !ApiFunctions.isEmpty(holdProj);
        boolean hasUserAndDt = !ApiFunctions.isEmpty(userAndDate);
        boolean showProject = false;

        String mouseover = "";
        if (hasUserAndDt) {
          mouseover = "title=\"" + userAndDate + "\"";
        }

        result.append("<td ");
        result.append(mouseover); // tooltip/mouseover of user+creation date
        result.append(">");

        result.append(holdProj);

        if (hasHoldProj && hasUserAndDt) { // comma if we have both
          result.append(", ");
        }

        if (hasUserAndDt) {
          result.append("H");
          result.append(getCartDaysSinceCreationDate());
        }

        result.append("</td>");
      }
      else { // not isRna

        if (getProject() != "") {
          result.append(
            "<td bgColor=red title=\""
              + getProject()
              + " - "
              + getProjectRequestDate()
              + "\">");
          result.append("P");
          result.append(getProjectDaysSinceRequestDate());
        }
        else if (userAndDate.length() > 0) {
          result.append("<td bgColor=red title=\"" + userAndDate + "\">");
          result.append("H");
          result.append(getCartDaysSinceCreationDate());
        }
        else if (getSalesStatus().equals(FormLogic.SMPL_ADDTOCART)) {
          result.append("<td bgColor=red title=\"Orphan\">");
          result.append("H");
        }
        else if (getProjectStatus().equals("PRJADDED")) {
          result.append("<td bgColor=red title=\"Orphan\">");
          result.append("P");
        }
        else {
          result.append("<td>");
        }
        result.append("</td>");
      }

    projectDescription = result.toString();    
    return projectDescription;
    
  }
  public String getTotalNumOfCells() {
    StringBuffer buffer = new StringBuffer();

    if (_sampleData != null) {
      String totalNumOfCells = _sampleData.getTotalNumOfCellsAsString();
      if (!ApiFunctions.isEmpty(totalNumOfCells)) {
        buffer.append(totalNumOfCells);
        String totalNumOfCellsExRepCui = _sampleData.getTotalNumOfCellsExRepCui();
        if (!ApiFunctions.isEmpty(totalNumOfCellsExRepCui)) {
          buffer.append(" ");
          buffer.append(GbossFactory.getInstance().getDescription(totalNumOfCellsExRepCui));
        }
      }
    }
    return ApiFunctions.safeString(buffer.toString());
  }
  public String getVolume() {
   StringBuffer buffer = new StringBuffer();
 
   if (_sampleData != null) {
    String volume = _sampleData.getVolumeAsString();
    if (!ApiFunctions.isEmpty(volume)) {
      buffer.append(volume);
    String volumeUnitCui = _sampleData.getVolumeUnitCui();
    if (!ApiFunctions.isEmpty(volumeUnitCui)) {
      buffer.append(" ");
      buffer.append(GbossFactory.getInstance().getDescription(volumeUnitCui));
      
    }
    }
    }
    return ApiFunctions.safeString(buffer.toString());
  }
  public String getWeight() {
    StringBuffer buffer = new StringBuffer();
  
    if (_sampleData != null) {
     String weight = _sampleData.getWeightAsString();
     if (!ApiFunctions.isEmpty(weight)) {
       buffer.append(weight);
     String weightUnitCui = _sampleData.getWeightUnitCui();
     if (!ApiFunctions.isEmpty(weightUnitCui)) {
       buffer.append(" ");
       buffer.append(GbossFactory.getInstance().getDescription(weightUnitCui));
       
     }
     }
     }
     return ApiFunctions.safeString(buffer.toString());
   }
  public String getConcentration() {
    String concentration = ApiFunctions.EMPTY_STRING;
    if (_sampleData != null) {
      concentration = _sampleData.getConcentrationAsString();
    }
    return concentration;
  }
  
  public String getYield() {
    String yield = ApiFunctions.EMPTY_STRING;
    if (_sampleData != null) {
      yield = _sampleData.getYieldAsString();
    }
    return yield;
  }

  public String getBufferTypeCui() {
    String bufferTypeCui = null;
    if (_sampleData != null) {
      bufferTypeCui = _sampleData.getBufferTypeCui();
    }
    return bufferTypeCui;
  }

  public String getBufferType() {
    String bufferTypeCui = getBufferTypeCui();
    StringBuffer buffer = new StringBuffer();

    if (!ApiFunctions.isEmpty(bufferTypeCui)) {
      buffer.append(GbossFactory.getInstance().getDescription(bufferTypeCui));
      if (ArtsConstants.OTHER_BUFFER_TYPE.equals(bufferTypeCui)) {
        buffer.append(" : ");
        buffer.append(ApiFunctions.safeString(_sampleData.getBufferTypeOther()));
      }
    }
    return ApiFunctions.safeString(buffer.toString());
  }
  
  

  public String getCellsPerMl() {
    String cellsPerMl = ApiFunctions.EMPTY_STRING;
    if (_sampleData != null) {
      cellsPerMl = _sampleData.getCellsPerMlAsString();
    }
    return cellsPerMl;
  }

  public String getPercentViability() {
    String percentViability = ApiFunctions.EMPTY_STRING;
    if (_sampleData != null) {
      percentViability = _sampleData.getPercentViabilityAsString();
    }
    return percentViability;
  }

  public String getDateOfCollectionWithPrecision() {
    StringBuffer buffer = new StringBuffer();

    if (_sampleData != null) {
      VariablePrecisionDateTime collectionDateTime = _sampleData.getCollectionDateTime();
      if (collectionDateTime != null) {
        if (!collectionDateTime.isEmpty()) {
          Escaper.htmlEscape(collectionDateTime.toString(), buffer);
        }
      }
    }
    return ApiFunctions.safeString(buffer.toString());
  }

  public String getDateOfPreservationWithPrecision() {
    StringBuffer buffer = new StringBuffer();

    if (_sampleData != null) {
      VariablePrecisionDateTime preservationDateTime = _sampleData.getPreservationDateTime();
      if (preservationDateTime != null) {
        if (!preservationDateTime.isEmpty()) {
          Escaper.htmlEscape(preservationDateTime.toString(), buffer);
        }
      }
    }
    return ApiFunctions.safeString(buffer.toString());
  }

  public String getSampleCollectionElapsedTime() {
    StringBuffer buffer = new StringBuffer();

    if (_sampleData != null) {
      VariablePrecisionDateTime collectionDateTime = _sampleData.getCollectionDateTime();
      VariablePrecisionDateTime preservationDateTime = _sampleData.getPreservationDateTime();
      if ((collectionDateTime != null) && (preservationDateTime != null)) {
        String cdtp = collectionDateTime.getPrecision();
        String pdtp = preservationDateTime.getPrecision();
      
        if ((!ApiFunctions.isEmpty(cdtp) && Constants.MINUTE.equals(cdtp)) &&
            (!ApiFunctions.isEmpty(pdtp) && Constants.MINUTE.equals(pdtp)))
        {
          Timestamp collectionTimestamp = collectionDateTime.getTimestamp();
          Timestamp preservationTimestamp = preservationDateTime.getTimestamp();
          if (collectionTimestamp != null && preservationTimestamp != null) {
            long collectionTime = collectionTimestamp.getTime();
            long preservationTime = preservationTimestamp.getTime();
            if (preservationTime >= collectionTime) {
              long elapsedTime = (preservationTime - collectionTime) / 60000L;
              buffer.append(elapsedTime);
            }
          }
        }
      }
    }
    return ApiFunctions.safeString(buffer.toString());
  }
  
  public String getKcDataValue(ColumnDescriptor columnDescriptor) {
    String returnValue = ApiFunctions.EMPTY_STRING;
    BigrFormInstance bigrFormInstance = null;
    if (columnDescriptor != null) {
      if (TagTypes.DOMAIN_OBJECT_VALUE_DONOR.equalsIgnoreCase(columnDescriptor.getDomainObjectType())) {
        if (_donorData != null) {
          bigrFormInstance = _donorData.getBigrFormInstance();
        }
      }
      else if (TagTypes.DOMAIN_OBJECT_VALUE_CASE.equalsIgnoreCase(columnDescriptor.getDomainObjectType())) {
        if (_consentData != null) {
          bigrFormInstance = _consentData.getBigrFormInstance();
        }
      }
      else if (TagTypes.DOMAIN_OBJECT_VALUE_SAMPLE.equalsIgnoreCase(columnDescriptor.getDomainObjectType())) {
        if (_sampleData != null) {
          bigrFormInstance = _sampleData.getBigrFormInstance();
        }
      }
    }
    if (bigrFormInstance != null) {
      boolean includeComma = false;
      DataElementValue[] values = bigrFormInstance.getKcFormInstance().getDataElement(columnDescriptor.getCui()).getDataElementValues();
      StringBuffer buff = new StringBuffer(50);
      for (int i=0; i<values.length; i++) {
        if (includeComma) {
          buff.append(", ");
        }
        includeComma = true;
        DataElementValue value  = values[i];
        buff.append(value.getValue());
        if (!ApiFunctions.isEmpty(value.getValueOther())) {
          buff.append(" (");
          buff.append(value.getValueOther());
          buff.append(")");
        }
      }
      returnValue = buff.toString();
    }
    return returnValue;
  }
  
  public BigrFormInstance getConsentBigrFormInstance() {
    BigrFormInstance returnValue = null;
    if (_consentData != null) {
      returnValue = _consentData.getBigrFormInstance();
    }
    return returnValue;
  }
  
  public BigrFormInstance getSampleBigrFormInstance() {
    BigrFormInstance returnValue = null;
    if (_sampleData != null) {
      returnValue = _sampleData.getBigrFormInstance();
    }
    return returnValue;
  }
}
