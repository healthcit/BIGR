package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.assistants.ProjectDataBean;
import com.ardais.bigr.iltds.assistants.SampleStatusAssistant;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.ardais.bigr.iltds.databeans.PolicyData;
import com.ardais.bigr.iltds.databeans.SampleTypeConfiguration;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.kc.form.BigrFormInstance;
import com.ardais.bigr.kc.form.BigrFormInstanceEnabled;
import com.ardais.bigr.lims.javabeans.ImageData;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.PolicyUtils;
import com.ardais.bigr.util.VariablePrecisionDateTime;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;

/**
 * Represents the raw data of a sample.
 */
public class SampleData implements BigrFormInstanceEnabled, Serializable {

// NOTE: if you add any mutable data elements, make sure to update
//       the populate(SampleData) method to handle them appropriately. 
  private String _asmPosition;
  private boolean _discordant;
  private String _formatDetail;
  private String _fixativeType;
  private String _gender;
  private String _location;
  private boolean _pulled;
  private String _pullReason;
  private boolean _qcVerified;
  private String _qcStatus;
  private Date _qcStatusDate;
  private boolean _released;
  private boolean _qcPosted;
  private boolean _restricted;
  private String _sampleId;
  private String _generatedSampleId;
  private String _invStatus;
  private Date _invStatusDate;
  private String _projectStatus;
  private Date _projectStatusDate;
  private String _salesStatus;
  private Date _salesStatusDate;
  private String _sectionCount;
  private String _rnaVialId;
  private String _tmaId;
  private String _boxBarcodeId;
  private String _asmNote;

  private List _cartEntries = new ArrayList();

  private ProjectDataBean _projectData;
  private AsmData _asmData;
  private OrderData _orderData;
  private PathologyEvaluationData _peData;
  private ArrayList _images;
  private List _logicalRepositories;
  private ArrayList _slides;
  private boolean _bms = false;
  private BTXBoxLocation _storageLocation;
  private ManifestDto _manifest;
  private SampleTypeConfiguration _sampleTypeConfiguration;
  private PolicyData _policyData;
  private List _sampleStatuses;

  private Date _pulledDate;

  // Derived fields (not in ILTDS_SAMPLE table) needed for HistoQC
  private String _consentId;
  private String _slidesExist;

  // HistoQC fields
  private String _diMinThicknessPfinCid;
  private String _diMaxThicknessPfinCid;
  private String _diWidthAcrossPfinCid;
  private String _histoMinThicknessPfinCid;
  private String _histoMaxThicknessPfinCid;
  private String _histoWidthAcrossPfinCid;
  private String _histoReembedReasonCid;
  private String _histoMinThicknessPfin;
  private String _histoMaxThicknessPfin;
  private String _histoWidthAcrossPfin;
  private String _histoReembedReason;
  private String _otherHistoReembedReason;
  private String _histoSubdividable;
  private String _histoNotes;

  private String _parentId;

  // MR4852
  private String _paraffinFeatureCid;
  private String _otherParaffinFeature;

  private Timestamp _subdivisionDate;
  private boolean _subdivided;
  private String _weightAsString;
  private BigDecimal _weight;//MR8755
  private BigDecimal _weightInMg;//MR8755
  private String _weightUnitCui;//MR8755
  private String _ardaisId;
  private String _asmId;
  private String _sampleAlias;
  private String _ardaisAcctKey;
  private String _importedYN;
  private String _donorAlias;
  private String _consentAlias;
  
  private String _sampleType; // CUI description, do not use to store CUI.
  private String _sampleTypeCui;
  
  private VariablePrecisionDateTime _collectionDateTime;
  private VariablePrecisionDateTime _preservationDateTime;

  private String _preparedBy;
  private String _preparedByName;
  private String _procedure;
  private String _procedureOther;

  private String _volumeUnitCui;//MR8936
  private BigDecimal _volumeInUl;//MR8936
  private BigDecimal _volume;//MR8936
  private String _volumeAsString;

  private String _tissueFindingCui;
  private String _tissueFindingOther;
  private String _appearanceBest;
  private String _grossAppearance;
  private String _diagnosisCuiBest;
  private String _diagnosisOtherBest;
  private String _tissueOriginCui;
  private String _tissueOriginOther;
  private String _tissue;
  private String _tissueOther;

  private String _bufferType; // CUI description, do not use to store CUI.
  private String _bufferTypeCui;
  private String _bufferTypeOther;

  private BigDecimal _totalNumOfCells;
  private String _totalNumOfCellsAsString;
  private String _totalNumOfCellsExRep; // CUI description, do not use to store CUI.
  private String _totalNumOfCellsExRepCui;

  private BigDecimal _cellsPerMl;
  private String _cellsPerMlAsString;

  private Integer _percentViability;
  private String _percentViabilityAsString;
  
  private Boolean _consumed;
  
  //MR8350
  private String _source;
  
  private BigDecimal _concentration;
  private String _concentrationAsString;
  private BigDecimal _yield;
  private String _yieldAsString;
  
  private BigrFormInstance _bigrFormInstance;
  private String _registrationFormId;
  private DataFormDefinition _registrationForm;
  private FormInstance _registrationFormInstance;
  private String _kcForm;
  
  //the following fields are not stored in the database, but instead are used for printing
  //a label for this sample
  private boolean _printLabel = false;
  private String _labelCount;
  private String _templateDefinitionName;
  private String _printerName;
  
  //for SWP-1110
  private List _attachments;

  /**
   * Creates a new <code>SampleData</code>.
   */
  public SampleData() {
  }

  /**
   * Creates a new <code>SampleData</code>, initialized from
   * the data in the current row of the result set.
   *
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public SampleData(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
  }

  /**
   * Creates a new <code>SampleData</code> from a SampleAccessBean.
   * 
   * @param  sampleBean a <code>SampleAccessBean</code> the sample access bean
   */
  public SampleData(SampleAccessBean sampleBean) {
    this();
    populate(sampleBean);
  }

  /**
   * Creates a new <code>SampleData</code> from a SampleData.
   * 
   * @param  sampleData a <code>SampleData</code> bean
   */
  public SampleData(SampleData sampleData) {
    this();
    populate(sampleData);
  }

  /**
   * Populates this <code>SampleData</code> from the data in the current row of 
   * the result set.
   * 
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   or {@link com.ardais.bigr.util.DbConstants}
   *                   or the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public void populate(Map columns, ResultSet rs) {
    Timestamp collectionDateTime = null;
    String collectionDpc = null;
    Timestamp preservationDateTime = null;
    String preservationDpc = null;

    try {
      if (columns.containsKey(DbAliases.RNA_SAMPLE_ID)) {
        setSampleId(rs.getString(DbAliases.RNA_SAMPLE_ID));
      }
      if (columns.containsKey(DbAliases.SAMPLE_ASM_POSITION)) {
        setAsmPosition(rs.getString(DbAliases.SAMPLE_ASM_POSITION));
      }
      if (columns.containsKey(DbAliases.SAMPLE_ASM_ID)) {
        setAsmId(rs.getString(DbAliases.SAMPLE_ASM_ID));
      }
      if (columns.containsKey(DbAliases.SAMPLE_DISCORDANT)) {
        setDiscordant(DbUtils.convertStringToBoolean(rs.getString(DbAliases.SAMPLE_DISCORDANT)));
      }
      if (columns.containsKey(DbAliases.SAMPLE_SAMPLE_TYPE_CID)) {
        setSampleTypeCui(rs.getString(DbAliases.SAMPLE_SAMPLE_TYPE_CID));
      }
      if (columns.containsKey(DbAliases.SAMPLE_FORMAT_DETAIL)) {
        setFormatDetail(rs.getString(DbAliases.SAMPLE_FORMAT_DETAIL));
      }
      if (columns.containsKey(DbAliases.SAMPLE_FIXATIVE_TYPE)) {
        setFixativeType(rs.getString(DbAliases.SAMPLE_FIXATIVE_TYPE));
      }
      if (columns.containsKey(DbAliases.SAMPLE_ID)) {
        setSampleId(rs.getString(DbAliases.SAMPLE_ID));
      }
      if (columns.containsKey(DbAliases.SAMPLE_LOCATION)) {
        setLocation(rs.getString(DbAliases.SAMPLE_LOCATION));
      }
      if (columns.containsKey(DbAliases.SAMPLE_INV_STATUS)) {
        setInvStatus(rs.getString(DbAliases.SAMPLE_INV_STATUS));
      }
      if (columns.containsKey(DbAliases.SAMPLE_INV_STATUS_DATE)) {
        setInvStatusDate(rs.getDate(DbAliases.SAMPLE_INV_STATUS_DATE));
      }
      if (columns.containsKey(DbAliases.SAMPLE_PULLED)) {
        setPulled(DbUtils.convertStringToBoolean(rs.getString(DbAliases.SAMPLE_PULLED)));
      }
      if (columns.containsKey(DbAliases.SAMPLE_DISCORDANT)) {
        setDiscordant(DbUtils.convertStringToBoolean(rs.getString(DbAliases.SAMPLE_DISCORDANT)));
      }
      if (columns.containsKey(DbAliases.SAMPLE_RESTRICTED)) {
        setRestricted("R".equalsIgnoreCase(rs.getString(DbAliases.SAMPLE_RESTRICTED)));
      }
      if (columns.containsKey(DbAliases.SAMPLE_PROJECT_STATUS)) {
        setProjectStatus(rs.getString(DbAliases.SAMPLE_PROJECT_STATUS));
      }
      if (columns.containsKey(DbAliases.SAMPLE_PROJECT_STATUS_DATE)) {
        setProjectStatusDate(rs.getDate(DbAliases.SAMPLE_PROJECT_STATUS_DATE));
      }
      if (columns.containsKey(DbAliases.SAMPLE_SALES_STATUS)) {
        setSalesStatus(rs.getString(DbAliases.SAMPLE_SALES_STATUS));
      }
      if (columns.containsKey(DbAliases.SAMPLE_SALES_STATUS_DATE)) {
        setSalesStatusDate(rs.getDate(DbAliases.SAMPLE_SALES_STATUS_DATE));
      }
      if (columns.containsKey(DbAliases.SAMPLE_PULL_DATE)) {
        setPulledDate(rs.getDate(DbAliases.SAMPLE_PULL_DATE));
      }
      if (columns.containsKey(DbAliases.SAMPLE_PULLED_REASON)) {
        setPullReason(rs.getString(DbAliases.SAMPLE_PULLED_REASON));
      }
      if (columns.containsKey(DbAliases.SAMPLE_QCVERIFIED)) {
        setQcVerified(DbUtils.convertStringToBoolean(rs.getString(DbAliases.SAMPLE_QCVERIFIED)));
      }
      if (columns.containsKey(DbAliases.SAMPLE_QCSTATUS)) {
        setQcStatus(rs.getString(DbAliases.SAMPLE_QCSTATUS));
      }
      if (columns.containsKey(DbAliases.SAMPLE_QCSTATUS_DATE)) {
        setQcStatusDate(rs.getDate(DbAliases.SAMPLE_QCSTATUS_DATE));
      }
      if (columns.containsKey(DbAliases.SAMPLE_RELEASED)) {
        setReleased(DbUtils.convertStringToBoolean(rs.getString(DbAliases.SAMPLE_RELEASED)));
      }
      if (columns.containsKey(DbAliases.SAMPLE_QCPOSTED)) {
        setQCPosted(DbUtils.convertStringToBoolean(rs.getString(DbAliases.SAMPLE_QCPOSTED)));
      }
      if (columns.containsKey(DbAliases.DONOR_GENDER)) {
        setGender(rs.getString(DbAliases.DONOR_GENDER));
      }
      if (columns.containsKey(DbAliases.SAMPLE_DI_MIN_THICKNESS)) {
        setDiMinThicknessPfinCid(rs.getString(DbAliases.SAMPLE_DI_MIN_THICKNESS));
      }
      if (columns.containsKey(DbAliases.SAMPLE_DI_MAX_THICKNESS)) {
        setDiMaxThicknessPfinCid(rs.getString(DbAliases.SAMPLE_DI_MAX_THICKNESS));
      }
      if (columns.containsKey(DbAliases.SAMPLE_DI_WIDTH)) {
        setDiWidthAcrossPfinCid(rs.getString(DbAliases.SAMPLE_DI_WIDTH));
      }
      if (columns.containsKey(DbAliases.SAMPLE_HISTO_MIN_THICKNESS)) {
        setHistoMinThicknessPfinCid(rs.getString(DbAliases.SAMPLE_HISTO_MIN_THICKNESS));
      }
      if (columns.containsKey(DbAliases.SAMPLE_HISTO_MAX_THICKNESS)) {
        setHistoMaxThicknessPfinCid(rs.getString(DbAliases.SAMPLE_HISTO_MAX_THICKNESS));
      }
      if (columns.containsKey(DbAliases.SAMPLE_HISTO_WIDTH)) {
        setHistoWidthAcrossPfinCid(rs.getString(DbAliases.SAMPLE_HISTO_WIDTH));
      }
      if (columns.containsKey(DbAliases.SAMPLE_HISTO_REEMBED_REASON)) {
        setHistoReembedReasonCid(rs.getString(DbAliases.SAMPLE_HISTO_REEMBED_REASON));
      }
      if (columns.containsKey(DbAliases.SAMPLE_OTHER_HISTO_REEMBED_REASON)) {
        setOtherHistoReembedReason(rs.getString(DbAliases.SAMPLE_OTHER_HISTO_REEMBED_REASON));
      }
      if (columns.containsKey(DbAliases.SAMPLE_HISTO_SUBDIVIDABLE)) {
        setHistoSubdividable(rs.getString(DbAliases.SAMPLE_HISTO_SUBDIVIDABLE));
      }
      if (columns.containsKey(DbAliases.SAMPLE_HISTO_NOTES)) {
        setHistoNotes(rs.getString(DbAliases.SAMPLE_HISTO_NOTES));
      }
      if (columns.containsKey(DbAliases.SAMPLE_PARENT_ID)) {
        setParentId(rs.getString(DbAliases.SAMPLE_PARENT_ID));
      }
      if (columns.containsKey(DbAliases.SAMPLE_SECTION_COUNT)) {
        setSectionCount(rs.getString(DbAliases.SAMPLE_SECTION_COUNT));
      }
      if (columns.containsKey(DbAliases.SAMPLE_PARAFFIN_FEATURE_CID)) {
        setHistoReembedReasonCid(rs.getString(DbAliases.SAMPLE_PARAFFIN_FEATURE_CID));
      }
      if (columns.containsKey(DbAliases.SAMPLE_OTHER_PARAFFIN_FEATURE)) {
        setOtherHistoReembedReason(rs.getString(DbAliases.SAMPLE_OTHER_PARAFFIN_FEATURE));
      }
      if (columns.containsKey(DbAliases.RNA_RNAVIALID)) {
        setRnaVialId(rs.getString(DbAliases.RNA_RNAVIALID));
      }
      if (columns.containsKey(DbAliases.TMA_BLOCKSETID)) {
        setTmaId(rs.getString(DbAliases.TMA_BLOCKSETID));
      }
      if (columns.containsKey(DbAliases.SAMPLE_ASM_NOTE)) {
        setAsmNote(rs.getString(DbAliases.SAMPLE_ASM_NOTE));
      }
      if (columns.containsKey(DbAliases.SAMPLE_SUBDIVISION_DATE)) {
        setSubdivisionDate(rs.getTimestamp(DbAliases.SAMPLE_SUBDIVISION_DATE));
      }
      if (columns.containsKey(DbAliases.SAMPLE_BOX_BARCODE_ID)) {
        setBoxBarcodeId(rs.getString(DbAliases.SAMPLE_BOX_BARCODE_ID));
      }
      if (columns.containsKey(DbAliases.SAMPLE_BMS_YN)) {
        setBms("Y".equals(rs.getString(DbAliases.SAMPLE_BMS_YN)));
      }
      if (columns.containsKey(DbAliases.SAMPLE_ARDAIS_ID)) {
        setArdaisId(rs.getString(DbAliases.SAMPLE_ARDAIS_ID));
      }
      if (columns.containsKey(DbAliases.SAMPLE_CONSENT_ID)) {
        setConsentId(rs.getString(DbAliases.SAMPLE_CONSENT_ID));
      }
      if (columns.containsKey(DbAliases.SAMPLE_CUSTOMER_ID)) {
        setSampleAlias(rs.getString(DbAliases.SAMPLE_CUSTOMER_ID));
      }
      if (columns.containsKey(DbAliases.SAMPLE_ACCOUNT)) {
        setArdaisAcctKey(rs.getString(DbAliases.SAMPLE_ACCOUNT));
      }
      if (columns.containsKey(DbAliases.SAMPLE_IMPORTED_YN)) {
        setImportedYN(rs.getString(DbAliases.SAMPLE_IMPORTED_YN));
      }
      if (columns.containsKey(DbAliases.SAMPLE_COLLECTION_DATETIME) && columns.containsKey(DbAliases.SAMPLE_COLLECTION_DATETIME_DPC)) {
        setCollectionDateTime(new VariablePrecisionDateTime(rs.getTimestamp(DbAliases.SAMPLE_COLLECTION_DATETIME), rs.getString(DbAliases.SAMPLE_COLLECTION_DATETIME_DPC)));
      }
      if (columns.containsKey(DbAliases.SAMPLE_PRESERVATION_DATETIME) && columns.containsKey(DbAliases.SAMPLE_PRESERVE_DATETIME_DPC)) {
        setPreservationDateTime(new VariablePrecisionDateTime(rs.getTimestamp(DbAliases.SAMPLE_PRESERVATION_DATETIME), rs.getString(DbAliases.SAMPLE_PRESERVE_DATETIME_DPC)));
      }           
      if (columns.containsKey(DbAliases.SAMPLE_VOLUME)) {
        setVolume(rs.getBigDecimal(DbAliases.SAMPLE_VOLUME));
      }
      if (columns.containsKey(DbAliases.SAMPLE_VOLUME_UNIT_CUI)) {
        setVolumeUnitCui(rs.getString(DbAliases.SAMPLE_VOLUME_UNIT_CUI));
        }
      if (columns.containsKey(DbAliases.SAMPLE_WEIGHT)) {
        setWeight(rs.getBigDecimal(DbAliases.SAMPLE_WEIGHT));
      }
      if (columns.containsKey(DbAliases.SAMPLE_WEIGHT_UNIT_CUI)) {
        setWeightUnitCui(rs.getString(DbAliases.SAMPLE_WEIGHT_UNIT_CUI));
        }
      if (columns.containsKey(DbAliases.SAMPLE_TISSUE_FINDING_CUI)) {
        setTissueFindingCui(rs.getString(DbAliases.SAMPLE_TISSUE_FINDING_CUI));
      }
      if (columns.containsKey(DbAliases.SAMPLE_TISSUE_FINDING_OTHER)) {
        setTissueFindingOther(rs.getString(DbAliases.SAMPLE_TISSUE_FINDING_OTHER));
      }
      if (columns.containsKey(DbAliases.SAMPLE_APPEARANCE_BEST)) {
        setAppearanceBest(rs.getString(DbAliases.SAMPLE_APPEARANCE_BEST));
      }
      if (columns.containsKey(DbAliases.SAMPLE_DIAGNOSIS_CUI_BEST)) {
        setDiagnosisCuiBest(rs.getString(DbAliases.SAMPLE_DIAGNOSIS_CUI_BEST));
      }
      if (columns.containsKey(DbAliases.SAMPLE_DIAGNOSIS_OTHER_BEST)) {
        setDiagnosisOtherBest(rs.getString(DbAliases.SAMPLE_DIAGNOSIS_OTHER_BEST));
      }
      if (columns.containsKey(DbAliases.SAMPLE_TISSUE_ORIGIN_CUI)) {
        setTissueOriginCui(rs.getString(DbAliases.SAMPLE_TISSUE_ORIGIN_CUI));
      }
      if (columns.containsKey(DbAliases.SAMPLE_TISSUE_ORIGIN_OTHER)) {
        setTissueOriginOther(rs.getString(DbAliases.SAMPLE_TISSUE_ORIGIN_OTHER));
      }
      if (columns.containsKey(DbAliases.SAMPLE_BUFFER_TYPE_CUI)) {
        setBufferTypeCui(rs.getString(DbAliases.SAMPLE_BUFFER_TYPE_CUI));
      }
      if (columns.containsKey(DbAliases.SAMPLE_BUFFER_TYPE_OTHER)) {
        setBufferTypeOther(rs.getString(DbAliases.SAMPLE_BUFFER_TYPE_OTHER));
      }
      if (columns.containsKey(DbAliases.SAMPLE_TOTAL_NUM_OF_CELLS)) {
        setTotalNumOfCells(rs.getBigDecimal(DbAliases.SAMPLE_TOTAL_NUM_OF_CELLS));
      }
      if (columns.containsKey(DbAliases.SAMPLE_TOTAL_NUM_OF_CELLS_EX_REP_CUI)) {
        setTotalNumOfCellsExRepCui(rs.getString(DbAliases.SAMPLE_TOTAL_NUM_OF_CELLS_EX_REP_CUI));
      }
      if (columns.containsKey(DbAliases.SAMPLE_CELLS_PER_ML)) {
        setCellsPerMl(rs.getBigDecimal(DbAliases.SAMPLE_CELLS_PER_ML));
      }
      if (columns.containsKey(DbAliases.SAMPLE_PERCENT_VIABILITY)) {
        setPercentViabilityAsString(rs.getString(DbAliases.SAMPLE_PERCENT_VIABILITY));
        // Fetch the field as a string because rs.getInt() returns zero if the value is null.
        // setPercentViability(new Integer(rs.getInt(DbAliases.SAMPLE_PERCENT_VIABILITY)));
      }
      if (columns.containsKey(DbAliases.SAMPLE_SOURCE)) {
        setSource(rs.getString(DbAliases.SAMPLE_SOURCE));
      }
      if (columns.containsKey(DbAliases.SAMPLE_CONCENTRATION)) {
        setConcentration(rs.getBigDecimal(DbAliases.SAMPLE_CONCENTRATION));
      }
      if (columns.containsKey(DbAliases.SAMPLE_YIELD)) {
        setYield(rs.getBigDecimal(DbAliases.SAMPLE_YIELD));
      }

      if (columns.containsKey(DbConstants.RNA_SAMPLE_ID)) {
        setSampleId(rs.getString(DbConstants.RNA_SAMPLE_ID));
      }
      if (columns.containsKey(DbConstants.SAMPLE_ASM_POSITION)) {
        setAsmPosition(rs.getString(DbConstants.SAMPLE_ASM_POSITION));
      }
      if (columns.containsKey(DbConstants.SAMPLE_ASM_ID)) {
        setAsmId(rs.getString(DbConstants.SAMPLE_ASM_ID));
      }
      if (columns.containsKey(DbConstants.SAMPLE_DISCORDANT)) {
        setDiscordant(DbUtils.convertStringToBoolean(rs.getString(DbConstants.SAMPLE_DISCORDANT)));
      }
      if (columns.containsKey(DbConstants.SAMPLE_SAMPLE_TYPE_CID)) {
        setSampleTypeCui(rs.getString(DbConstants.SAMPLE_SAMPLE_TYPE_CID));
      }
      if (columns.containsKey(DbConstants.SAMPLE_FORMAT_DETAIL)) {
        setFormatDetail(rs.getString(DbConstants.SAMPLE_FORMAT_DETAIL));
      }
      if (columns.containsKey(DbConstants.SAMPLE_FIXATIVE_TYPE)) {
        setFixativeType(rs.getString(DbConstants.SAMPLE_FIXATIVE_TYPE));
      }
      if (columns.containsKey(DbConstants.SAMPLE_ID)) {
        setSampleId(rs.getString(DbConstants.SAMPLE_ID));
      }
      if (columns.containsKey(DbConstants.SAMPLE_LOCATION)) {
        setLocation(rs.getString(DbConstants.SAMPLE_LOCATION));
      }
      if (columns.containsKey(DbConstants.SAMPLE_INV_STATUS)) {
        setInvStatus(rs.getString(DbConstants.SAMPLE_INV_STATUS));
      }
      if (columns.containsKey(DbConstants.SAMPLE_INV_STATUS_DATE)) {
        setInvStatusDate(rs.getDate(DbConstants.SAMPLE_INV_STATUS_DATE));
      }
      if (columns.containsKey(DbConstants.SAMPLE_PULLED)) {
        setPulled(DbUtils.convertStringToBoolean(rs.getString(DbConstants.SAMPLE_PULLED)));
      }
      if (columns.containsKey(DbConstants.SAMPLE_DISCORDANT)) {
        setDiscordant(DbUtils.convertStringToBoolean(rs.getString(DbConstants.SAMPLE_DISCORDANT)));
      }
      if (columns.containsKey(DbConstants.SAMPLE_RESTRICTED)) {
        setRestricted("R".equalsIgnoreCase(rs.getString(DbConstants.SAMPLE_RESTRICTED)));
      }
      if (columns.containsKey(DbConstants.SAMPLE_PROJECT_STATUS)) {
        setProjectStatus(rs.getString(DbConstants.SAMPLE_PROJECT_STATUS));
      }
      if (columns.containsKey(DbConstants.SAMPLE_PROJECT_STATUS_DATE)) {
        setProjectStatusDate(rs.getDate(DbConstants.SAMPLE_PROJECT_STATUS_DATE));
      }
      if (columns.containsKey(DbConstants.SAMPLE_SALES_STATUS)) {
        setSalesStatus(rs.getString(DbConstants.SAMPLE_SALES_STATUS));
      }
      if (columns.containsKey(DbConstants.SAMPLE_SALES_STATUS_DATE)) {
        setSalesStatusDate(rs.getDate(DbConstants.SAMPLE_SALES_STATUS_DATE));
      }
      if (columns.containsKey(DbConstants.SAMPLE_PULL_DATE)) {
        setPulledDate(rs.getDate(DbConstants.SAMPLE_PULL_DATE));
      }
      if (columns.containsKey(DbConstants.SAMPLE_PULLED_REASON)) {
        setPullReason(rs.getString(DbConstants.SAMPLE_PULLED_REASON));
      }
      if (columns.containsKey(DbConstants.SAMPLE_QCVERIFIED)) {
        setQcVerified(DbUtils.convertStringToBoolean(rs.getString(DbConstants.SAMPLE_QCVERIFIED)));
      }
      if (columns.containsKey(DbConstants.SAMPLE_QCSTATUS)) {
        setQcStatus(rs.getString(DbConstants.SAMPLE_QCSTATUS));
      }
      if (columns.containsKey(DbConstants.SAMPLE_QCSTATUS_DATE)) {
        setQcStatusDate(rs.getDate(DbConstants.SAMPLE_QCSTATUS_DATE));
      }
      if (columns.containsKey(DbConstants.SAMPLE_RELEASED)) {
        setReleased(DbUtils.convertStringToBoolean(rs.getString(DbConstants.SAMPLE_RELEASED)));
      }
      if (columns.containsKey(DbConstants.SAMPLE_QCPOSTED)) {
        setQCPosted(DbUtils.convertStringToBoolean(rs.getString(DbConstants.SAMPLE_QCPOSTED)));
      }
      if (columns.containsKey(DbConstants.DONOR_GENDER)) {
        setGender(rs.getString(DbConstants.DONOR_GENDER));
      }
      if (columns.containsKey(DbConstants.SAMPLE_DI_MIN_THICKNESS)) {
        setDiMinThicknessPfinCid(rs.getString(DbConstants.SAMPLE_DI_MIN_THICKNESS));
      }
      if (columns.containsKey(DbConstants.SAMPLE_DI_MAX_THICKNESS)) {
        setDiMaxThicknessPfinCid(rs.getString(DbConstants.SAMPLE_DI_MAX_THICKNESS));
      }
      if (columns.containsKey(DbConstants.SAMPLE_DI_WIDTH)) {
        setDiWidthAcrossPfinCid(rs.getString(DbConstants.SAMPLE_DI_WIDTH));
      }
      if (columns.containsKey(DbConstants.SAMPLE_HISTO_MIN_THICKNESS)) {
        setHistoMinThicknessPfinCid(rs.getString(DbConstants.SAMPLE_HISTO_MIN_THICKNESS));
      }
      if (columns.containsKey(DbConstants.SAMPLE_HISTO_MAX_THICKNESS)) {
        setHistoMaxThicknessPfinCid(rs.getString(DbConstants.SAMPLE_HISTO_MAX_THICKNESS));
      }
      if (columns.containsKey(DbConstants.SAMPLE_HISTO_WIDTH)) {
        setHistoWidthAcrossPfinCid(rs.getString(DbConstants.SAMPLE_HISTO_WIDTH));
      }
      if (columns.containsKey(DbConstants.SAMPLE_HISTO_REEMBED_REASON)) {
        setHistoReembedReasonCid(rs.getString(DbConstants.SAMPLE_HISTO_REEMBED_REASON));
      }
      if (columns.containsKey(DbConstants.SAMPLE_OTHER_HISTO_REEMBED_REASON)) {
        setOtherHistoReembedReason(rs.getString(DbConstants.SAMPLE_OTHER_HISTO_REEMBED_REASON));
      }
      if (columns.containsKey(DbConstants.SAMPLE_HISTO_SUBDIVIDABLE)) {
        setHistoSubdividable(rs.getString(DbConstants.SAMPLE_HISTO_SUBDIVIDABLE));
      }
      if (columns.containsKey(DbConstants.SAMPLE_HISTO_NOTES)) {
        setHistoNotes(rs.getString(DbConstants.SAMPLE_HISTO_NOTES));
      }
      if (columns.containsKey(DbConstants.SAMPLE_PARENT_ID)) {
        setParentId(rs.getString(DbConstants.SAMPLE_PARENT_ID));
      }
      if (columns.containsKey(DbConstants.SAMPLE_SECTION_COUNT)) {
        setSectionCount(rs.getString(DbConstants.SAMPLE_SECTION_COUNT));
      }
      if (columns.containsKey(DbConstants.SAMPLE_PARAFFIN_FEATURE_CID)) {
        setHistoReembedReasonCid(rs.getString(DbConstants.SAMPLE_PARAFFIN_FEATURE_CID));
      }
      if (columns.containsKey(DbConstants.SAMPLE_OTHER_PARAFFIN_FEATURE)) {
        setOtherHistoReembedReason(rs.getString(DbConstants.SAMPLE_OTHER_PARAFFIN_FEATURE));
      }
      if (columns.containsKey(DbConstants.RNA_RNAVIALID)) {
        setRnaVialId(rs.getString(DbConstants.RNA_RNAVIALID));
      }
      if (columns.containsKey(DbConstants.TMA_BLOCKSETID)) {
        setTmaId(rs.getString(DbConstants.TMA_BLOCKSETID));
      }
      if (columns.containsKey(DbConstants.SAMPLE_ASM_NOTE)) {
        setAsmNote(rs.getString(DbConstants.SAMPLE_ASM_NOTE));
      }
      if (columns.containsKey(DbConstants.SAMPLE_SUBDIVISION_DATE)) {
        setSubdivisionDate(rs.getTimestamp(DbConstants.SAMPLE_SUBDIVISION_DATE));
      }
      if (columns.containsKey(DbConstants.SAMPLE_BOX_BARCODE_ID)) {
        setBoxBarcodeId(rs.getString(DbConstants.SAMPLE_BOX_BARCODE_ID));
      }
      if (columns.containsKey(DbConstants.SAMPLE_BMS_YN)) {
        setBms("Y".equals(rs.getString(DbConstants.SAMPLE_BMS_YN)));
      }
      if (columns.containsKey(DbConstants.SAMPLE_ARDAIS_ID)) {
        setArdaisId(rs.getString(DbConstants.SAMPLE_ARDAIS_ID));
      }
      if (columns.containsKey(DbConstants.SAMPLE_CONSENT_ID)) {
        setConsentId(rs.getString(DbConstants.SAMPLE_CONSENT_ID));
      }
      if (columns.containsKey(DbConstants.SAMPLE_CUSTOMER_ID)) {
        setSampleAlias(rs.getString(DbConstants.SAMPLE_CUSTOMER_ID));
      }
      if (columns.containsKey(DbConstants.SAMPLE_ACCOUNT)) {
        setArdaisAcctKey(rs.getString(DbConstants.SAMPLE_ACCOUNT));
      }
      if (columns.containsKey(DbConstants.SAMPLE_IMPORTED_YN)) {
        setImportedYN(rs.getString(DbConstants.SAMPLE_IMPORTED_YN));
      }
      if (columns.containsKey(DbConstants.SAMPLE_COLLECTION_DATETIME) && columns.containsKey(DbConstants.SAMPLE_COLLECTION_DATETIME_DPC)) {
        setCollectionDateTime(new VariablePrecisionDateTime(rs.getTimestamp(DbConstants.SAMPLE_COLLECTION_DATETIME), rs.getString(DbConstants.SAMPLE_COLLECTION_DATETIME_DPC)));
      }
      if (columns.containsKey(DbConstants.SAMPLE_PRESERVATION_DATETIME) && columns.containsKey(DbConstants.SAMPLE_PRESERVE_DATETIME_DPC)) {
        setPreservationDateTime(new VariablePrecisionDateTime(rs.getTimestamp(DbConstants.SAMPLE_PRESERVATION_DATETIME), rs.getString(DbConstants.SAMPLE_PRESERVE_DATETIME_DPC)));
      }
      if (columns.containsKey(DbConstants.SAMPLE_VOLUME)) {
        setVolume(rs.getBigDecimal(DbConstants.SAMPLE_VOLUME));
      }
      if (columns.containsKey(DbConstants.SAMPLE_VOLUME_UNIT_CUI)) {
        setVolumeUnitCui(rs.getString(DbConstants.SAMPLE_VOLUME_UNIT_CUI));
      }
      if (columns.containsKey(DbConstants.SAMPLE_WEIGHT)) {
        setWeight(rs.getBigDecimal(DbConstants.SAMPLE_WEIGHT));
      }
      if (columns.containsKey(DbConstants.SAMPLE_WEIGHT_UNIT_CUI)) {
        setWeightUnitCui(rs.getString(DbConstants.SAMPLE_WEIGHT_UNIT_CUI));
      }
     if (columns.containsKey(DbConstants.SAMPLE_TISSUE_FINDING_CUI)) {
        setTissueFindingCui(rs.getString(DbConstants.SAMPLE_TISSUE_FINDING_CUI));
      }
      if (columns.containsKey(DbConstants.SAMPLE_TISSUE_FINDING_OTHER)) {
        setTissueFindingOther(rs.getString(DbConstants.SAMPLE_TISSUE_FINDING_OTHER));
      }
      if (columns.containsKey(DbConstants.SAMPLE_APPEARANCE_BEST)) {
        setAppearanceBest(rs.getString(DbConstants.SAMPLE_APPEARANCE_BEST));
      }
      if (columns.containsKey(DbConstants.SAMPLE_DIAGNOSIS_CUI_BEST)) {
        setDiagnosisCuiBest(rs.getString(DbConstants.SAMPLE_DIAGNOSIS_CUI_BEST));
      }
      if (columns.containsKey(DbConstants.SAMPLE_DIAGNOSIS_OTHER_BEST)) {
        setDiagnosisOtherBest(rs.getString(DbConstants.SAMPLE_DIAGNOSIS_OTHER_BEST));
      }
      if (columns.containsKey(DbConstants.SAMPLE_TISSUE_ORIGIN_CUI)) {
        setTissueOriginCui(rs.getString(DbConstants.SAMPLE_TISSUE_ORIGIN_CUI));
      }
      if (columns.containsKey(DbConstants.SAMPLE_TISSUE_ORIGIN_OTHER)) {
        setTissueOriginOther(rs.getString(DbConstants.SAMPLE_TISSUE_ORIGIN_OTHER));
      }
      if (columns.containsKey(DbConstants.SAMPLE_BUFFER_TYPE_CUI)) {
        setBufferTypeCui(rs.getString(DbConstants.SAMPLE_BUFFER_TYPE_CUI));
      }
      if (columns.containsKey(DbConstants.SAMPLE_BUFFER_TYPE_OTHER)) {
        setBufferTypeOther(rs.getString(DbConstants.SAMPLE_BUFFER_TYPE_OTHER));
      }
      if (columns.containsKey(DbConstants.SAMPLE_TOTAL_NUM_OF_CELLS)) {
        setTotalNumOfCells(rs.getBigDecimal(DbConstants.SAMPLE_TOTAL_NUM_OF_CELLS));
      }
      if (columns.containsKey(DbConstants.SAMPLE_TOTAL_NUM_OF_CELLS_EX_REP_CUI)) {
        setTotalNumOfCellsExRepCui(rs.getString(DbConstants.SAMPLE_TOTAL_NUM_OF_CELLS_EX_REP_CUI));
      }
      if (columns.containsKey(DbConstants.SAMPLE_CELLS_PER_ML)) {
        setCellsPerMl(rs.getBigDecimal(DbConstants.SAMPLE_CELLS_PER_ML));
      }
      if (columns.containsKey(DbConstants.SAMPLE_PERCENT_VIABILITY)) {
        setPercentViabilityAsString(rs.getString(DbConstants.SAMPLE_PERCENT_VIABILITY));
        // Fetch the field as a string because rs.getInt() returns zero if the value is null.
        // setPercentViability(new Integer(rs.getInt(DbConstants.SAMPLE_PERCENT_VIABILITY)));
      }
      if (columns.containsKey(DbConstants.SAMPLE_SOURCE)) {
        setSource(rs.getString(DbConstants.SAMPLE_SOURCE));
      }
      if (columns.containsKey(DbConstants.SAMPLE_CONCENTRATION)) {
        setConcentration(rs.getBigDecimal(DbConstants.SAMPLE_CONCENTRATION));
      }
      if (columns.containsKey(DbConstants.SAMPLE_YIELD)) {
        setYield(rs.getBigDecimal(DbConstants.SAMPLE_YIELD));
      }
      if (columns.containsKey(DbConstants.SAMPLE_REG_FORM)) {
        setRegistrationFormId(rs.getString(DbConstants.SAMPLE_REG_FORM));
      }

      if (getSubdivisionDate() != null) {
        setSubdivided(true);
      }
      else {
        setSubdivided(false);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * Populates this <code>SampleData</code> from a SampleAccessBean.
   * 
   * @param  sampleBean a <code>SampleAccessBean</code> the sample access bean
   */
  public void populate(SampleAccessBean sampleBean) {

    if (sampleBean == null) {
      return;
    }

    SampleKey samplePk = null;

    try {
      samplePk = (SampleKey) sampleBean.getEJBRef().getPrimaryKey();
      Timestamp timestamp = null;

      setSampleId(samplePk.sample_barcode_id);
      setAsmPosition(sampleBean.getAsm_position());
      setDiscordant(DbUtils.convertStringToBoolean(sampleBean.getDiscordantYN()));
      setSampleTypeCui(sampleBean.getSampleTypeCid());
      setFormatDetail(sampleBean.getFormatDetailCid());
      setLocation(sampleBean.getCell_ref_location());

      setInvStatus(sampleBean.getInv_status());
      timestamp = sampleBean.getInv_status_date();
      if (timestamp != null) {
        setInvStatusDate(new Date(timestamp.getTime()));
      }

      setPulled(DbUtils.convertStringToBoolean(sampleBean.getPullYN()));

      setProjectStatus(sampleBean.getProject_status());
      timestamp = sampleBean.getProject_status_date();
      if (timestamp != null) {
        setProjectStatusDate(new Date(timestamp.getTime()));
      }

      setSalesStatus(sampleBean.getSales_status());
      timestamp = sampleBean.getSales_status_date();
      if (timestamp != null) {
        setSalesStatusDate(new Date(timestamp.getTime()));
      }

      timestamp = sampleBean.getPullDate();
      if (timestamp != null) {
        setPulledDate(new Date(timestamp.getTime()));
      }

      setPullReason(sampleBean.getPullReason());
      setQcVerified(DbUtils.convertStringToBoolean(sampleBean.getQc_verified()));

      setQcStatus(sampleBean.getQc_status());
      timestamp = sampleBean.getQc_status_date();
      if (timestamp != null) {
        setQcStatusDate(new Date(timestamp.getTime()));
      }

      setParentId(sampleBean.getParent_barcode_id());
      setReleased(DbUtils.convertStringToBoolean(sampleBean.getReleasedYN()));
      setQCPosted(DbUtils.convertStringToBoolean(sampleBean.getQcpostedYN()));

      setDiMinThicknessPfinCid(sampleBean.getDiMinThicknessPfinCid());
      setDiMaxThicknessPfinCid(sampleBean.getDiMaxThicknessPfinCid());
      setDiWidthAcrossPfinCid(sampleBean.getDiWidthAcrossPfinCid());
      setHistoMinThicknessPfinCid(sampleBean.getHistoMinThicknessPfinCid());
      setHistoMaxThicknessPfinCid(sampleBean.getHistoMaxThicknessPfinCid());
      setHistoWidthAcrossPfinCid(sampleBean.getHistoWidthAcrossPfinCid());
      setHistoReembedReasonCid(sampleBean.getHistoReembedReasonCid());
      setOtherHistoReembedReason(sampleBean.getOtherHistoReembedReason());
      setHistoSubdividable(sampleBean.getHistoSubdividable());
      setHistoNotes(sampleBean.getHistoNotes());

      //MR4852
      setParaffinFeatureCid(sampleBean.getParaffinFeatureCid());
      setOtherParaffinFeature(sampleBean.getOtherParaffinFeature());

      setSubdivisionDate(sampleBean.getSubdivision_date());
      if (sampleBean.getSubdivision_date() != null) {
        setSubdivided(true);
      }
      else {
        setSubdivided(false);
      }

      setSampleAlias(sampleBean.getCustomerId());
      setArdaisAcctKey(sampleBean.getArdais_acct_key());
      setConsentId(sampleBean.getConsentId());
      setArdaisId(sampleBean.getArdaisId());
      setImportedYN(sampleBean.getImportedYN());
      setVolume(sampleBean.getVolume());
      setVolumeUnitCui(ApiFunctions.safeString(sampleBean.getVolumeUnitCui()));
      setWeight(sampleBean.getWeight());
      setWeightUnitCui(ApiFunctions.safeString(sampleBean.getWeightUnitCui()));
      setTissueFindingCui(ApiFunctions.safeString(sampleBean.getTissueFindingCid()));
      setTissueFindingOther(ApiFunctions.safeString(sampleBean.getTissueFindingOther()));
      setAppearanceBest(ApiFunctions.safeString(sampleBean.getAppearanceBest()));
      setDiagnosisCuiBest(ApiFunctions.safeString(sampleBean.getDiagnosisCuiBest()));
      setDiagnosisOtherBest(ApiFunctions.safeString(sampleBean.getDiagnosisOtherBest()));
      setTissueOriginCui(ApiFunctions.safeString(sampleBean.getTissueOriginCid()));
      setTissueOriginOther(ApiFunctions.safeString(sampleBean.getTissueOriginOther()));
      setBufferTypeCui(ApiFunctions.safeString(sampleBean.getBufferTypeCui()));
      setBufferTypeOther(ApiFunctions.safeString(sampleBean.getBufferTypeOther()));
      setTotalNumOfCells(sampleBean.getTotalNumOfCells());
      setTotalNumOfCellsExRepCui(ApiFunctions.safeString(sampleBean.getTotalNumOfCellsExRepCui()));
      setCellsPerMl(sampleBean.getCellsPerMl());
      setPercentViability(sampleBean.getPercentViability());
      setSource(sampleBean.getSource());
      setConcentration(sampleBean.getConcentration());
      setYield(sampleBean.getYield());
      
      Timestamp collectionDateTime = sampleBean.getCollection_datetime();
      String collectionDateTimeDpc = sampleBean.getCollection_datetime_dpc();
      if ((collectionDateTime != null) && !ApiFunctions.isEmpty(collectionDateTimeDpc)) {
        setCollectionDateTime(new VariablePrecisionDateTime(collectionDateTime, collectionDateTimeDpc));
      }

      Timestamp preservationDateTime = sampleBean.getPreservation_datetime();
      String preservationDateTimeDpc = sampleBean.getPreservation_datetime_dpc();
      if ((preservationDateTime != null) && !ApiFunctions.isEmpty(preservationDateTimeDpc)) {
        setPreservationDateTime(new VariablePrecisionDateTime(preservationDateTime, preservationDateTimeDpc));
      }

    }
    catch (Exception e) {
      ApiLogger.log(
        "Error retrieving data from SampleAccessBean with PK = "
          + samplePk.sample_barcode_id
          + ": Error = "
          + e.getLocalizedMessage());
      throw new ApiException(e);
    }
  }

  /**
   * Populates the basic information of this <code>SampleData</code> with information from the
   * specified <code>ResultSet</code>.
   * 
   * @param  rs  the <code>ResultSet</code> to populate from
   */
  public void populateBasicInfo(ResultSet rs) {
    populate(DbUtils.getColumnNames(rs), rs);
  }

  /**
   * Populates the basic information of this <code>SampleData</code> with the basic information
   * from another <code>SampleData</code>.
   * 
   * @param  sampleData the <code>SampleData</code> to populate from
   */
  private void populateBasicInfo(SampleData sampleData) {
    if (sampleData == null) {
      return;
    }

    setSampleId(sampleData.getSampleId());
    setSampleAlias(sampleData.getSampleAlias());
    setAppearanceBest(sampleData.getAppearanceBest());
    setArdaisAcctKey(sampleData.getArdaisAcctKey());
    setArdaisId(sampleData.getArdaisId());
    setAsmId(sampleData.getAsmId());
    setAsmNote(sampleData.getAsmNote());
    setAsmPosition(sampleData.getAsmPosition());
    setBms(sampleData.isBms());
    setBoxBarcodeId(sampleData.getBoxBarcodeId());
    setBufferTypeCui(sampleData.getBufferTypeCui());
    setBufferTypeOther(sampleData.getBufferTypeOther());
    setCellsPerMl(sampleData.getCellsPerMl());
    setCollectionDateTime(sampleData.getCollectionDateTime());
    setConcentration(sampleData.getConcentration());
    setConsentId(sampleData.getConsentId());
    setDiagnosisCuiBest(sampleData.getDiagnosisCuiBest());
    setDiagnosisOtherBest(sampleData.getDiagnosisOtherBest());
    setDiMaxThicknessPfinCid(sampleData.getDiMaxThicknessPfinCid());
    setDiMinThicknessPfinCid(sampleData.getDiMinThicknessPfinCid());
    setDiscordant(sampleData.isDiscordant());
    setDiWidthAcrossPfinCid(sampleData.getDiWidthAcrossPfinCid());
    setFixativeType(sampleData.getFixativeType());
    setFormatDetail(sampleData.getFormatDetail());
    setHistoMaxThicknessPfinCid(sampleData.getHistoMaxThicknessPfinCid());    
    setHistoMinThicknessPfinCid(sampleData.getHistoMinThicknessPfinCid());
    setHistoNotes(sampleData.getHistoNotes());
    setHistoReembedReasonCid(sampleData.getHistoReembedReasonCid());
    setHistoSubdividable(sampleData.getHistoSubdividable());
    setHistoWidthAcrossPfinCid(sampleData.getHistoWidthAcrossPfinCid());
    setImportedYN(sampleData.getImportedYN());
    setInvStatus(sampleData.getInvStatus());
    setInvStatusDate(sampleData.getInvStatusDate());
    setLocation(sampleData.getLocation());
    setOtherHistoReembedReason(sampleData.getOtherHistoReembedReason());
    setOtherParaffinFeature(sampleData.getOtherParaffinFeature());
    setParaffinFeatureCid(sampleData.getParaffinFeatureCid());
    setParentId(sampleData.getParentId());
    setPercentViability(sampleData.getPercentViability());
    setPreservationDateTime(sampleData.getPreservationDateTime());
    setProjectStatus(sampleData.getProjectStatus());
    setProjectStatusDate(sampleData.getProjectStatusDate());
    setPulled(sampleData.isPulled());
    setPulledDate(sampleData.getPulledDate());
    setPullReason(sampleData.getPullReason());
    setQCPosted(sampleData.isQCPosted());
    setQcStatus(sampleData.getQcStatus());
    setQcStatusDate(sampleData.getQcStatusDate());
    setQcVerified(sampleData.isQcVerified());
    setReleased(sampleData.isReleased());
    setRestricted(sampleData.isRestricted());
    setSalesStatus(sampleData.getSalesStatus());
    setSalesStatusDate(sampleData.getSalesStatusDate());
    setSampleTypeCui(sampleData.getSampleTypeCui());
    setSectionCount(sampleData.getSectionCount());
    setSubdivided(sampleData.isSubdivided());
    setSubdivisionDate(sampleData.getSubdivisionDate());
    setTissueFindingCui(sampleData.getTissueFindingCui());
    setTissueFindingOther(sampleData.getTissueFindingOther());
    setTissueOriginCui(sampleData.getTissueOriginCui());
    setTissueOriginOther(sampleData.getTissueOriginOther());
    setTotalNumOfCells(sampleData.getTotalNumOfCells());
    setTotalNumOfCellsExRepCui(sampleData.getTotalNumOfCellsExRepCui());
    setVolume(sampleData.getVolume());
    setVolumeUnitCui(sampleData.getVolumeUnitCui());
    setWeight(sampleData.getWeight());
    setWeightUnitCui(sampleData.getWeightUnitCui());
    setYield(sampleData.getYield());
  }

  /**
   * Populates the basic imported information of this <code>SampleData</code> with information 
   * from the specified <code>ResultSet</code>.
   * 
   * @param  rs  the <code>ResultSet</code> to populate from
   */
  public void populateBasicImportedInfo(ResultSet rs) {
    try {
      setDonorAlias(rs.getString(DbAliases.DONOR_CUSTOMER_ID));
      setConsentAlias(rs.getString(DbAliases.CONSENT_CUSTOMER_ID));
      setPreparedBy(rs.getString(DbAliases.ASM_FORM_PREPARED_BY));
      setProcedure(rs.getString(DbAliases.ASM_FORM_PROCEDURE));
      setProcedureOther(rs.getString(DbAliases.ASM_FORM_PROCEDURE_OTHER));
      setGrossAppearance(rs.getString(DbAliases.ASM_APPEARANCE));
      setTissue(rs.getString(DbAliases.ASM_TISSUE));
      setTissueOther(rs.getString(DbAliases.ASM_TISSUE_OTHER));
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
}

  /**
   * Populates this <code>SampleData</code> with the information from 
   * another <code>SampleData</code>.
   * 
   * @param  sampleData the <code>SampleData</code> to populate from
   */
  private void populate(SampleData sampleData) {
    if (sampleData == null) {
      return;
    }
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, sampleData);
    
    //copy properties doesnt directly copy value attributes with weight
    //and volume. this is because of changing values and units
    //setWeightInMg and setVolumeInUl changes are also overriden
    setWeightAsString(sampleData._weightAsString);
    setWeightUnitCui(sampleData._weightUnitCui);
    setVolumeAsString(sampleData._volumeAsString);
    setVolumeUnitCui(sampleData._volumeUnitCui);
    
    //for mutable objects, we need to make copies of the values on the
    //original SampleData bean.  Otherwise, if someone alters that value
    //they will be altering it for both this SampleData bean AND the
    //original SampleData bean.
    if (sampleData.getQcStatusDate() != null) {
      setQcStatusDate((Date)sampleData.getQcStatusDate().clone());
    }
    if (sampleData.getInvStatusDate() != null) {
      setInvStatusDate((Date)sampleData.getInvStatusDate().clone());
    }
    if (sampleData.getProjectStatusDate() != null) {
      setProjectStatusDate((Date)sampleData.getProjectStatusDate().clone());
    }
    if (sampleData.getSalesStatusDate() != null) {
      setSalesStatusDate((Date)sampleData.getSalesStatusDate().clone());
    }
    if (!ApiFunctions.isEmpty(sampleData.getCartEntries())) {
      _cartEntries.clear();
      Iterator iterator = sampleData.getCartEntries().iterator();
      while (iterator.hasNext()) {
        _cartEntries.add(new ShoppingCartData((ShoppingCartData)iterator.next()));
      }
    }
    if (sampleData.getProjectData() != null) {
      setProjectData(new ProjectDataBean(sampleData.getProjectData()));
    }
    if (sampleData.getAsmData() != null) {
      setAsmData(new AsmData(sampleData.getAsmData()));
    }
    if (sampleData.getOrderData() != null) {
      setOrderData(new OrderData(sampleData.getOrderData()));
    }
    if (sampleData.getPathologyEvaluationData() != null) {
      setPathologyEvaluationData(new PathologyEvaluationData(sampleData.getPathologyEvaluationData()));
    }
    if (!ApiFunctions.isEmpty(sampleData.getImages())) {
      _images.clear();
      Iterator iterator = sampleData.getImages().iterator();
      while (iterator.hasNext()) {
        _images.add(new ImageData((ImageData)iterator.next()));
      }
    }
    if (!ApiFunctions.isEmpty(sampleData.getLogicalRepositories())) {
      _logicalRepositories.clear();
      Iterator iterator = sampleData.getLogicalRepositories().iterator();
      while (iterator.hasNext()) {
        _logicalRepositories.add(new LogicalRepository((LogicalRepository)iterator.next()));
      }
    }
    if (!ApiFunctions.isEmpty(sampleData.getSlides())) {
      _slides.clear();
      Iterator iterator = sampleData.getSlides().iterator();
      while (iterator.hasNext()) {
        _slides.add((String)iterator.next());
      }
    }
    if (sampleData.getStorageLocation() != null) {
      setStorageLocation(new BTXBoxLocation(sampleData.getStorageLocation()));
    }
    if (sampleData.getManifest() != null) {
      setManifest(new ManifestDto(sampleData.getManifest()));
    }
    //sampleTypeConfiguration will be set when requested, so no need to do it here
    if (!ApiFunctions.isEmpty(sampleData.getSampleStatuses())) {
      _sampleStatuses.clear();
      Iterator iterator = sampleData.getSampleStatuses().iterator();
      while (iterator.hasNext()) {
        _sampleStatuses.add(new SampleStatusAssistant((SampleStatusAssistant)iterator.next()));
      }
    }
    if (sampleData.getPulledDate() != null) {
      setPulledDate((Date) sampleData.getPulledDate().clone());
    }
    if (sampleData.getSubdivisionDate() != null) {
      setSubdivisionDate((Timestamp) sampleData.getSubdivisionDate().clone());
    }
    if (sampleData.getCollectionDateTime() != null) {
      setCollectionDateTime(new VariablePrecisionDateTime(sampleData.getCollectionDateTime()));
    }
    if (sampleData.getPreservationDateTime() != null) {
      setPreservationDateTime(new VariablePrecisionDateTime(sampleData.getPreservationDateTime()));
    }
    if (sampleData.getBigrFormInstance() != null) {
      setBigrFormInstance(new BigrFormInstance(sampleData.getBigrFormInstance().getKcFormInstance()));
    }
    if (sampleData.getRegistrationForm() != null) {
      setRegistrationForm(new DataFormDefinition(sampleData.getRegistrationForm()));
    }
    if (sampleData.getRegistrationFormInstance() != null) {
      setRegistrationFormInstance(new FormInstance(sampleData.getRegistrationFormInstance()));
    }
    if (sampleData.getKcForm() != null) {
      setKcForm(sampleData.getKcForm());
    }
  }

  /**
   * Method setSalesStatus.
   */
  private void setSalesStatus() {
  }

  /**
   * Returns the asmPosition.
   * @return String
   */
  public String getAsmPosition() {
    return _asmPosition;
  }

  /**
   * Returns the isDiscordant.
   * @return boolean
   */
  public boolean isDiscordant() {
    return _discordant;
  }

  /**
   * Returns the sample type CUI.
   * @return String
   */
  public String getSampleTypeCui() {
    return _sampleTypeCui;
  }

  /**
   * Returns the format.
   * @return String
   */
  public String getFormatDetail() {
    return _formatDetail;
  }

  /** Returns the format detail display name */
  public String getFormatDetailName() {
    String returnValue = "";
    String code = getFormatDetail();
    if (ApiFunctions.isEmpty(code)) {
      returnValue = "";
    }
    else {
      returnValue = GbossFactory.getInstance().getDescription(code);
    }
    return returnValue;
  }

  /**
   * Returns the gender.
   * @return String
   */
  public String getGender() {
    return _gender;
  }

  /**
   * Returns the location.
   * @return String
   */
  public String getLocation() {
    return _location;
  }

  /**
   * Returns the invStatus.
   * @return String
   */
  public String getInvStatus() {
    return _invStatus;
  }

  /**
   * Returns the isPulled.
   * @return boolean
   */
  public boolean isPulled() {
    return _pulled;
  }

  /**
   * Returns the pullReason.
   * @return String
   */
  public String getPullReason() {
    return _pullReason;
  }

  /**
   * Returns the qcVerified.
   * @return boolean
   */
  public boolean isQcVerified() {
    return _qcVerified;
  }

  /**
   * Returns the qcStatus.
   * @return String
   */
  public String getQcStatus() {
    return _qcStatus;
  }

  /**
   * Returns the isReleased.
   * @return boolean
   */
  public boolean isReleased() {
    return _released;
  }

  /**
   * Returns the isQCPosted.
   * @return boolean
   */
  public boolean isQCPosted() {
    return _qcPosted;
  }

  /**
   * Returns the restricted.
   * @return boolean
   */
  public boolean isRestricted() {
    return _restricted;
  }

  /**
   * @return True if the sample is in any BMS logical repository.  It is possible for a sample
   * to be in some BMS logical repository (so that
   * {@link #isBms()} will return true) but for the sample not to be a BMS sample from the user's
   * perspective (so that {@link #isBmsFromUsersPerspective()} will return false).  This happens
   * when a sample is in some
   * non-BMS logical repository that the user does have access to, but all of the BMS repositories
   * that the sample is part of are inaccessible to the user.
   * 
   * @see #isBmsFromUsersPerspective()
   */
  public boolean isBms() {
    return _bms;
  }

  /**
   * @return True if any of the logical repositories returned by {@link #getLogicalRepositories()}
   * is both a BMS logical repository and is accessible to the user represented by
   * <code>securityInfo</code>.  This tells us whether the sample is a BMS sample from this user's
   * perspective.  It is possible for a sample to be in some BMS logical repository (so that
   * {@link #isBms()} will return true) but for the sample not to be a BMS sample from the user's
   * perspective (so that this method will return false).  This happens when a sample is in some
   * non-BMS logical repository that the user does have access to, but all of the BMS repositories
   * that the sample is part of are inaccessible to the user.
   * 
   * @param securityInfo The user's security information.
   * @see #isBms()
   */
  public boolean isBmsFromUsersPerspective(SecurityInfo securityInfo) {
    // If this isn't a BMS sample, it can't be in any BMS logical repository at all, so
    // we don't need to do any more checking in that case.  Also, if the current user doesn't
    // have access to any BMS inventory groups at all, we can immediately return false.
    if (!securityInfo.isHasBmsAccess() || !isBms()) {
      return false;
    }

    List repositories = getLogicalRepositories();
    if (repositories == null) {
      return false;
    }

    Iterator iter = repositories.iterator();
    while (iter.hasNext()) {
      LogicalRepository repos = (LogicalRepository) iter.next();
      if (repos != null) {
        if (repos.isBms() && securityInfo.isLogicalRepositoryAccessible(repos)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns the sampleId.
   * @return String
   */
  public String getSampleId() {
    return _sampleId;
  }

  /**
   * Sets the asmPosition.
   * @param asmPosition The asmPosition to set
   */
  public void setAsmPosition(String asmPosition) {
    _asmPosition = asmPosition;
  }

  /**
   * Sets the discordant.
   * @param discordant The discordant to set
   */
  public void setDiscordant(boolean discordant) {
    _discordant = discordant;
  }

  /**
   * Sets the sample type CUI.
   * @param sampleTypeCui The sample type to set
   */
  public void setSampleTypeCui(String sampleTypeCui) {
    _sampleTypeCui = sampleTypeCui;
  }

  /**
   * Sets the format.
   * @param format The format to set
   */
  public void setFormatDetail(String formatDet) {
    _formatDetail = formatDet;
  }

  /**
   * Sets the gender.
   * @param gender The gender to set
   */
  public void setGender(String gender) {
    _gender = gender;
  }

  /**
   * Sets the location.
   * @param location The location to set
   */
  public void setLocation(String location) {
    _location = location;
  }

  /**
   * Sets the invStatus.
   * @param invStatus The invStatus to set
   */
  public void setInvStatus(String invStatus) {
    _invStatus = invStatus;
  }

  /**
   * Sets the pulled.
   * @param pulled The pulled to set
   */
  public void setPulled(boolean pulled) {
    _pulled = pulled;
  }

  /**
   * Sets the pullReason.
   * @param pullReason The pullReason to set
   */
  public void setPullReason(String pullReason) {
    _pullReason = pullReason;
  }

  /**
   * Sets the qcVerified.
   * @param qcVerified The qcVerified to set
   */
  public void setQcVerified(boolean qcVerified) {
    _qcVerified = qcVerified;
  }

  /**
   * Sets the qcStatus.
   * @param qcStatus The qcStatus to set
   */
  public void setQcStatus(String qcStatus) {
    _qcStatus = qcStatus;
  }

  /**
   * Sets the released.
   * @param released The released to set
   */
  public void setReleased(boolean released) {
    _released = released;
  }

  /**
   * Sets the qcPosted.
   * @param qcPosted The qcPosted to set
   */
  public void setQCPosted(boolean qcPosted) {
    _qcPosted = qcPosted;
  }

  /**
   * Sets the sampleId.
   * @param sampleId The sampleId to set
   */
  public void setSampleId(String sampleId) {
    _sampleId = sampleId;
  }

  /**
   * Sets the restricted.
   * @param restricted The restricted to set
   */
  public void setRestricted(boolean restricted) {
    _restricted = restricted;
  }

  /**
   * Returns the ASM data bean for the ASM associated with this sample.
   * 
   * @return  The AsmData bean.
   */
  public AsmData getAsmData() {
    return _asmData;
  }

  /**
   * Sets the ASM data bean for the ASM associated with this sample.
   * 
   * @param  asmData  the AsmData bean
   */
  public void setAsmData(AsmData asmData) {
    _asmData = asmData;
  }

  /**
   * Returns the pathology evaluation data bean for the pathology evaluation 
   * associated with this sample.
   * 
   * @return  The PathologyEvaluationData bean.
   */
  public PathologyEvaluationData getPathologyEvaluationData() {
    return _peData;
  }

  /**
   * Sets the pathology evaluation data bean for the pathology evaluation
   * associated with this sample.
   * 
   * @param  peData  the PathologyEvaluationData bean
   */
  public void setPathologyEvaluationData(PathologyEvaluationData peData) {
    _peData = peData;
  }

  /**
   * Returns the pulledDate.
   * @return Date
   */
  public Date getPulledDate() {
    return _pulledDate;
  }

  /**
   * Sets the pulledDate.
   * @param pulledDate The pulledDate to set
   */
  public void setPulledDate(Date pulledDate) {
    _pulledDate = pulledDate;
  }

  /**
   * Returns the images.
   * @return ArrayList
   */
  public ArrayList getImages() {
    return _images;
  }

  /**
   * Sets the images.
   * @param images The images to set
   */
  public void setImages(ArrayList images) {
    _images = images;
  }

  /**
   * Returns the consentIdList.
   * @return String
   */
  public String getConsentId() {
    return _consentId;
  }

  /**
   * Sets the consentId.
   * @param consentId The consentId to set.
   */
  public void setConsentId(String consentId) {
    _consentId = consentId;
  }

  /**
   * Returns the slidesExist.
   * @return String
   */
  public String getSlidesExist() {
    return _slidesExist;
  }

  /**
   * Sets the slidesExist.
   * @param slidesExist The slidesExist to set.
   */
  public void setSlidesExist(String slidesExist) {
    _slidesExist = slidesExist;
  }

  /**
   * Returns the diMinThicknessPfinCid.
   * @return String
   */
  public String getDiMinThicknessPfinCid() {
    return _diMinThicknessPfinCid;
  }

  /**
   * Sets the diMinThicknessPfinCid.
   * @param diMinThicknessPfinCid The diMinThicknessPfinCid to set.
   */
  public void setDiMinThicknessPfinCid(String diMinThicknessPfinCid) {
    _diMinThicknessPfinCid = diMinThicknessPfinCid;
  }

  /**
   * Returns the diMaxThicknessPfinCid.
   * @return String
   */
  public String getDiMaxThicknessPfinCid() {
    return _diMaxThicknessPfinCid;
  }

  /**
   * Sets the diMaxThicknessPfinCid.
   * @param diMaxThicknessPfinCid The diMaxThicknessPfinCid to set.
   */
  public void setDiMaxThicknessPfinCid(String diMaxThicknessPfinCid) {
    _diMaxThicknessPfinCid = diMaxThicknessPfinCid;
  }

  /**
   * Returns the diWidthAcrossPfinCid.
   * @return String
   */
  public String getDiWidthAcrossPfinCid() {
    return _diWidthAcrossPfinCid;
  }

  /**
   * Sets the diWidthAcrossPfinCid.
   * @param diWidthAcrossPfinCid The diWidthAcrossPfinCid to set.
   */
  public void setDiWidthAcrossPfinCid(String diWidthAcrossPfinCid) {
    _diWidthAcrossPfinCid = diWidthAcrossPfinCid;
  }

  /**
   * Returns the histoMinThicknessPfinCid.
   * @return String
   */
  public String getHistoMinThicknessPfinCid() {
    return _histoMinThicknessPfinCid;
  }

  /**
   * Sets the histoMinThicknessPfinCid.
   * @param histoMinThicknessPfinCid The histoMinThicknessPfinCid to set.
   */
  public void setHistoMinThicknessPfinCid(String histoMinThicknessPfinCid) {
    _histoMinThicknessPfinCid = histoMinThicknessPfinCid;
    if (!ApiFunctions.isEmpty(histoMinThicknessPfinCid)) {
      setHistoMinThicknessPfin(GbossFactory.getInstance().getDescription(histoMinThicknessPfinCid));
    }
  }

  /**
   * Returns the histoMaxThicknessPfinCid.
   * @return String
   */
  public String getHistoMaxThicknessPfinCid() {
    return _histoMaxThicknessPfinCid;
  }

  /**
   * Sets the histoMaxThicknessPfinCid.
   * @param histoMaxThicknessPfinCid The histoMaxThicknessPfinCid to set.
   */
  public void setHistoMaxThicknessPfinCid(String histoMaxThicknessPfinCid) {
    _histoMaxThicknessPfinCid = histoMaxThicknessPfinCid;
    if (!ApiFunctions.isEmpty(histoMaxThicknessPfinCid)) {
      setHistoMaxThicknessPfin(GbossFactory.getInstance().getDescription(histoMaxThicknessPfinCid));
    }
  }

  /**
   * Returns the histoWidthAcrossPfinCid.
   * @return String
   */
  public String getHistoWidthAcrossPfinCid() {
    return _histoWidthAcrossPfinCid;
  }

  /**
   * Sets the histoWidthAcrossPfinCid.
   * @param histoWidthAcrossPfinCid The histoWidthAcrossPfinCid to set.
   */
  public void setHistoWidthAcrossPfinCid(String histoWidthAcrossPfinCid) {
    _histoWidthAcrossPfinCid = histoWidthAcrossPfinCid;
    if (!ApiFunctions.isEmpty(histoWidthAcrossPfinCid)) {
      setHistoWidthAcrossPfin(GbossFactory.getInstance().getDescription(histoWidthAcrossPfinCid));
    }
  }

  /**
   * Returns the histoReembedReasonCid.
   * @return String
   */
  public String getHistoReembedReasonCid() {
    return _histoReembedReasonCid;
  }

  /**
   * Sets the histoReembedReasonCid.
   * @param histoReembedReasonCid The histoReembedReasonCid to set.
   */
  public void setHistoReembedReasonCid(String histoReembedReasonCid) {
    _histoReembedReasonCid = histoReembedReasonCid;
    if (!ApiFunctions.isEmpty(histoReembedReasonCid)) {
      setHistoReembedReason(GbossFactory.getInstance().getDescription(histoReembedReasonCid));
    }
  }

  /**
   * Returns the otherHistoReembedReason.
   * @return String
   */
  public String getOtherHistoReembedReason() {
    return _otherHistoReembedReason;
  }

  /**
   * Sets the otherHistoReembedReason.
   * @param otherHistoReembedReason The otherHistoReembedReason to set.
   */
  public void setOtherHistoReembedReason(String otherHistoReembedReason) {
    _otherHistoReembedReason = otherHistoReembedReason;
  }

  /**
   * Returns the histoSubdividable.
   * @return String
   */
  public String getHistoSubdividable() {
    return _histoSubdividable;
  }

  /**
   * Sets the histoSubdividable.
   * @param histoSubdividable The histoSubdividable to set.
   */
  public void setHistoSubdividable(String histoSubdividable) {
    _histoSubdividable = histoSubdividable;
  }

  /**
   * Returns the histoNotes.
   * @return String
   */
  public String getHistoNotes() {
    return _histoNotes;
  }

  /**
   * Sets the histoNotes.
   * @param histoNotes The histoNotes to set.
   */
  public void setHistoNotes(String histoNotes) {
    _histoNotes = histoNotes;
  }

  /**
   * Returns the parentId.
   * @return String
   */
  public String getParentId() {
    return _parentId;
  }

  /**
   * Sets the parentId.
   * @param parentId The parentId to set.
   */
  public void setParentId(String parentId) {
    _parentId = parentId;
  }

  /**
   * Returns the invStatusDate.
   * @return Date
   */
  public Date getInvStatusDate() {
    return _invStatusDate;
  }

  /**
   * Returns the projectStatus.
   * @return String
   */
  public String getProjectStatus() {
    return _projectStatus;
  }

  /**
   * Returns the projectStatusDate.
   * @return Date
   */
  public Date getProjectStatusDate() {
    return _projectStatusDate;
  }

  /**
   * Returns the qcStatusDate.
   * @return Date
   */
  public Date getQcStatusDate() {
    return _qcStatusDate;
  }

  /**
   * Returns the salesStatus.
   * @return String
   */
  public String getSalesStatus() {
    return _salesStatus;
  }

  /**
   * Returns the salesStatusDate.
   * @return Date
   */
  public Date getSalesStatusDate() {
    return _salesStatusDate;
  }

  /**
   * Sets the invStatusDate.
   * @param invStatusDate The invStatusDate to set
   */
  public void setInvStatusDate(Date invStatusDate) {
    _invStatusDate = invStatusDate;
  }

  /**
   * Sets the projectStatus.
   * @param projectStatus The projectStatus to set
   */
  public void setProjectStatus(String projectStatus) {
    _projectStatus = projectStatus;
  }

  /**
   * Sets the projectStatusDate.
   * @param projectStatusDate The projectStatusDate to set
   */
  public void setProjectStatusDate(Date projectStatusDate) {
    _projectStatusDate = projectStatusDate;
  }

  /**
   * Sets the qcStatusDate.
   * @param qcStatusDate The qcStatusDate to set
   */
  public void setQcStatusDate(Date qcStatusDate) {
    _qcStatusDate = qcStatusDate;
  }

  /**
   * Sets the salesStatus.
   * @param salesStatus The salesStatus to set
   */
  public void setSalesStatus(String salesStatus) {
    _salesStatus = salesStatus;
  }

  /**
   * Sets the salesStatusDate.
   * @param salesStatusDate The salesStatusDate to set
   */
  public void setSalesStatusDate(Date salesStatusDate) {
    _salesStatusDate = salesStatusDate;
  }

  /**
   * Returns the projectData.
   * @return ProjectDataBean
   */
  public ProjectDataBean getProjectData() {
    return _projectData;
  }

  /**
   * Sets the projectData.
   * @param projectData The projectData to set
   */
  public void setProjectData(ProjectDataBean projectData) {
    _projectData = projectData;
  }

  /**
   * Add a shopping cart entry representing a hold against this sample
   */
  public void addCartEntry(String user, Date created, Integer amt) {
    _cartEntries.add(new ShoppingCartData(user, created, amt));
  }

  /**
   * Get all the shopping cart (hold) entries against this sample.
   */
  public List getCartEntries() {
    return _cartEntries;
  }

  /**
   * Returns the orderData.
   * @return OrderData
   */
  public OrderData getOrderData() {
    return _orderData;
  }

  /**
   * Sets the orderData.
   * @param orderData The orderData to set
   */
  public void setOrderData(OrderData orderData) {
    _orderData = orderData;
  }

  /**
   * Returns the sectionCount.
   * @return String
   */
  public String getSectionCount() {
    return _sectionCount;
  }

  /**
   * Sets the sectionCount.
   * @param sectionCount The sectionCount to set
   */
  public void setSectionCount(String sectionCount) {
    _sectionCount = sectionCount;
  }

  /**
   * Returns the paraffinFeatureCid.
   * @return String
   */
  public String getParaffinFeatureCid() {
    return _paraffinFeatureCid;
  }

  /**
   * Sets the paraffinFeatureCid.
   * @param paraffinFeatureCid The paraffinFeatureCid to set.
   */
  public void setParaffinFeatureCid(String paraffinFeatureCid) {
    _paraffinFeatureCid = paraffinFeatureCid;
  }

  /**
   * Returns the otherParaffinFeature.
   * @return String
   */
  public String getOtherParaffinFeature() {
    return _otherParaffinFeature;
  }

  /**
   * Sets the otherParaffinFeature.
   * @param otherParaffinFeature The otherParaffinFeature to set.
   */
  public void setOtherParaffinFeature(String otherParaffinFeature) {
    _otherParaffinFeature = otherParaffinFeature;
  }
  /**
   * Returns the rnaVialId.
   * @return String
   */
  public String getRnaVialId() {
    return _rnaVialId;
  }

  /**
   * Sets the rnaVialId.
   * @param rnaVialId The rnaVialId to set
   */
  public void setRnaVialId(String rnaVialId) {
    _rnaVialId = rnaVialId;
  }

  /**
   * Returns the tmaId.
   * @return String
   */
  public String getTmaId() {
    return _tmaId;
  }

  /**
   * Sets the tmaId.
   * @param tmaId The tmaId to set
   */
  public void setTmaId(String tmaId) {
    _tmaId = tmaId;
  }

  /**
   * Returns the subdivide date.
   * @return Timestamp
   */
  public Timestamp getSubdivisionDate() {
    return _subdivisionDate;
  }

  /**
   * Sets the subdivide date.
   * @param subdivisionDate The subdivide date.
   */
  public void setSubdivisionDate(Timestamp subdivisionDate) {
    _subdivisionDate = subdivisionDate;
  }

  /**
   * Returns true if sample has been subdivided.
   * @return boolean
   */
  public boolean isSubdivided() {
    return _subdivided;
  }

  /**
   * Set to true if sample has been subdivided.
   * @param subdivided True if sample has been subdivided.
   */
  public void setSubdivided(boolean subdivided) {
    _subdivided = subdivided;
  }
  /**
   * Returns the boxBarcodeId.
   * @return String
   */
  public String getBoxBarcodeId() {
    return _boxBarcodeId;
  }

  /**
   * Sets the boxBarcodeId.
   * @param boxBarcodeId The boxBarcodeId to set
   */
  public void setBoxBarcodeId(String boxBarcodeId) {
    _boxBarcodeId = boxBarcodeId;
  }

  /**
   * Returns the asmNote.
   * @return String
   */
  public String getAsmNote() {
    return _asmNote;
  }
  
  public String getNote() {
    return _asmNote;
  }

  /**
   * Sets the asmNote.
   * @param asmNote The asmNote to set
   */
  public void setAsmNote(String asmNote) {
    _asmNote = asmNote;
  }

  public void setNote(String note) {
    _asmNote = note;
  }

  /**
   * @return
   */
  public List getLogicalRepositories() {
    return _logicalRepositories;
  }

  /**
   * @param list
   */
  public void setLogicalRepositories(List list) {
    _logicalRepositories = list;
  }

  /**
   * @return
   */
  public BTXBoxLocation getStorageLocation() {
    return _storageLocation;
  }

  /**
   * @return True if this item's storage location is known and its location address id is the
   *    same as the specified user's location address id.
   * @param securityInfo The user's security information.
   */
  public boolean isStoredLocally(SecurityInfo securityInfo) {
    boolean result = false;
    BTXBoxLocation boxloc = getStorageLocation();
    if (boxloc != null) {
      String addressId = boxloc.getLocationAddressID();
      if (addressId != null && addressId.equals(securityInfo.getUserLocationId())) {
        result = true;
      }
    }
    return result;
  }

  /**
   * @param list
   */
  public void setStorageLocation(BTXBoxLocation boxloc) {
    _storageLocation = boxloc;
  }

  /**
   * @param True if this is a BMS inventory item.
   */
  public void setBms(boolean newValue) {
    _bms = newValue;
  }
  /**
   * @return
   */
  public ArrayList getSlides() {
    return _slides;
  }

  /**
   * @param list
   */
  public void setSlides(ArrayList list) {
    _slides = list;
  }

  /**
   * @return True if any of the logical repositories returned by {@link #getLogicalRepositories()}
   * is accessible to the user represented by <code>securityInfo</code>.  If the user
   * has the View All Logical Repositories privilege, this always returns true even if the
   * sample is not in any logical repositories.
   * 
   * @param securityInfo The user's security information.
   */
  public boolean isAccessibleToUser(SecurityInfo securityInfo) {
    boolean returnValue = false;
    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
      returnValue = true;
    }
    else {
      returnValue = securityInfo.isAnyLogicalRepositoryAccessible(getLogicalRepositories());
    }
    return returnValue;
  }

  /**
   * @return
   */
  public String getArdaisId() {
    return _ardaisId;
  }

  /**
   * @param string
   */
  public void setArdaisId(String string) {
    _ardaisId = string;
  }

  /**
   * @return
   */
  public String getSampleAlias() {
    return _sampleAlias;
  }

  /**
   * @param string
   */
  public void setSampleAlias(String string) {
    _sampleAlias = string;
  }

  /**
   * @return
   */
  public ManifestDto getManifest() {
    return _manifest;
  }

  /**
   * @param dto
   */
  public void setManifest(ManifestDto dto) {
    _manifest = dto;
  }

  /**
   * @return
   */
  public String getArdaisAcctKey() {
    return _ardaisAcctKey;
  }

  /**
   * @param string
   */
  public void setArdaisAcctKey(String string) {
    _ardaisAcctKey = string;
  }

  /**
   * @return
   */
  public String getImportedYN() {
    return _importedYN;
  }

  /**
   * @param string
   */
  public void setImportedYN(String string) {
    _importedYN = string;
  }


  public List getAttachments() {
    return _attachments;
  }
  
  
  public void setAttachments (List data) {
    this._attachments = data;
  }
  
  
  /**
   * @return
   */
  public String getConsentAlias() {
    return _consentAlias;
  }

  /**
   * @return
   */
  public String getDonorAlias() {
    return _donorAlias;
  }

  /**
   * @param string
   */
  public void setConsentAlias(String string) {
    _consentAlias = string;
  }

  /**
   * @param string
   */
  public void setDonorAlias(String string) {
    _donorAlias = string;
  }

  /**
   * @param string
   */
  public void setHistoMaxThicknessPfin(String string) {
    _histoMaxThicknessPfin = string;
  }

  /**
   * @param string
   */
  public void setHistoMinThicknessPfin(String string) {
    _histoMinThicknessPfin = string;
  }

  /**
   * @param string
   */
  public void setHistoReembedReason(String string) {
    _histoReembedReason = string;
  }

  /**
   * @param string
   */
  public void setHistoWidthAcrossPfin(String string) {
    _histoWidthAcrossPfin = string;
  }

  /**
   * @return
   */
  public String getHistoMaxThicknessPfin() {
    return _histoMaxThicknessPfin;
  }

  /**
   * @return
   */
  public String getHistoMinThicknessPfin() {
    return _histoMinThicknessPfin;
  }

  /**
   * @return
   */
  public String getHistoReembedReason() {
    return _histoReembedReason;
  }

  /**
   * @return
   */
  public String getHistoWidthAcrossPfin() {
    return _histoWidthAcrossPfin;
  }

  /**
   * @return
   */
  public String getAppearanceBest() {
    return _appearanceBest;
  }

  /**
   * @param string
   */
  public void setAppearanceBest(String string) {
    _appearanceBest = string;
  }

  /**
   * @return
   */
  public String getDiagnosisCuiBest() {
    return _diagnosisCuiBest;
  }

  /**
   * @return
   */
  public String getDiagnosisOtherBest() {
    return _diagnosisOtherBest;
  }

  /** Returns the diagnosis best name */
  public String getDiagnosisBestName() {
    String returnValue = "";
    String code = getDiagnosisCuiBest();
    if (!ApiFunctions.isEmpty(code)) {
      if (FormLogic.OTHER_DX.equals(code)) {
        returnValue = "Other";
        if (!ApiFunctions.isEmpty(getDiagnosisOtherBest())) {
          returnValue = returnValue + ": " + getDiagnosisOtherBest();
        }
      }
      else {
        returnValue = BigrGbossData.getDiagnosisDescription(code);
      }
    }
    return returnValue;
  }

  /**
   * @param string
   */
  public void setDiagnosisCuiBest(String string) {
    _diagnosisCuiBest = string;
  }

  /**
   * @param string
   */
  public void setDiagnosisOtherBest(String string) {
    _diagnosisOtherBest = string;
  }

  /**
   * @return
   */
  public String getTissueOriginCui() {
    return _tissueOriginCui;
  }

  /**
   * @return
   */
  public String getTissueOriginOther() {
    return _tissueOriginOther;
  }

  /** Returns the tissue origin name */
  public String getTissueOriginName() {
    String returnValue = "";
    String code = getTissueOriginCui();
    if (!ApiFunctions.isEmpty(code)) {
      if (FormLogic.OTHER_TISSUE.equals(code)) {
        returnValue = "Other";
        if (!ApiFunctions.isEmpty(getTissueOriginOther())) {
          returnValue = returnValue + ": " + getTissueOriginOther();
        }
      }
      else {
        returnValue = BigrGbossData.getTissueDescription(code);
      }
    }
    return returnValue;
  }

  /**
   * @param string
   */
  public void setTissueOriginCui(String string) {
    _tissueOriginCui = string;
  }

  /**
   * @param string
   */
  public void setTissueOriginOther(String string) {
    _tissueOriginOther = string;
  }

  /**
   * @return
   */
  public String getTissueFindingCui() {
    return _tissueFindingCui;
  }

  /**
   * @return
   */
  public String getTissueFindingOther() {
    return _tissueFindingOther;
  }

  /** Returns the tissue finding name */
  public String getTissueFindingName() {
    String returnValue = "";
    String code = getTissueFindingCui();
    if (!ApiFunctions.isEmpty(code)) {
      if (FormLogic.OTHER_TISSUE.equals(code)) {
        returnValue = "Other";
        if (!ApiFunctions.isEmpty(getTissueFindingOther())) {
          returnValue = returnValue + ": " + getTissueFindingOther();
        }
      }
      else {
        returnValue = BigrGbossData.getTissueDescription(code);
      }
    }
    return returnValue;
  }

  /**
   * @param string
   */
  public void setTissueFindingCui(String string) {
    _tissueFindingCui = string;
  }

  /**
   * @param string
   */
  public void setTissueFindingOther(String string) {
    _tissueFindingOther = string;
  }

  /**
   * @return
   */
  public String getAsmId() {
    return _asmId;
  }

  /**
   * @param string
   */
  public void setAsmId(String string) {
    _asmId = string;
  }

  /**
   * @return
   */
  public String getPreparedBy() {
    return _preparedBy;
  }

  public String getPreparedByName() {
    if (_preparedByName == null) {
      if (!ApiFunctions.isEmpty(_preparedBy)) {
        try {
          ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(_preparedBy));
          _preparedByName = (staff.getArdais_staff_fname() + " " + staff.getArdais_staff_lname());
        }
        catch (Exception e) {
          _preparedByName = "";
        }
      }
      else {
        _preparedByName = "";
      }
    }
    return _preparedByName;
  }

  /**
   * @return
   */
  public String getProcedure() {
    return _procedure;
  }

  /**
   * @return
   */
  public String getProcedureOther() {
    return _procedureOther;
  }

  public String getProcedureName() {
    String returnValue = "";
    String code = getProcedure();
    if (!ApiFunctions.isEmpty(code)) {
      if (ArtsConstants.OTHER_PROCEDURE.equals(code)) {
        returnValue = "Other";
        if (!ApiFunctions.isEmpty(getProcedureOther())) {
          returnValue = returnValue + ": " + getProcedureOther();
        }
      }
      else {
        returnValue = BigrGbossData.getProcedureDescription(code);
      }
    }
    return returnValue;
  }

  /**
   * @param string
   */
  public void setPreparedBy(String string) {
    _preparedBy = string;
  }

  /**
   * @param string
   */
  public void setProcedure(String string) {
    _procedure = string;
  }

  /**
   * @param string
   */
  public void setProcedureOther(String string) {
    _procedureOther = string;
  }

  /**
   * @return
   */
  public String getGrossAppearance() {
    return _grossAppearance;
  }
  
  public String getGrossAppearanceAsString() {
    String grossAppearance = getGrossAppearance();
    if (ApiFunctions.isEmpty(grossAppearance)) {
      return ApiFunctions.EMPTY_STRING;
    }
    else {
      return FormLogic.lookupAppearance(grossAppearance);
    }
  }


  /**
   * @param string
   */
  public void setGrossAppearance(String string) {
    _grossAppearance = string;
  }

  /**
   * @return
   */
  public String getTissue() {
    return _tissue;
  }

  /**
   * @return
   */
  public String getTissueOther() {
    return _tissueOther;
  }

  public String getTissueName() {
    String returnValue = "";
    String code = getTissue();
    if (!ApiFunctions.isEmpty(code)) {
      if (FormLogic.OTHER_TISSUE.equals(code)) {
        returnValue = "Other";
        if (!ApiFunctions.isEmpty(getTissueOther())) {
          returnValue = returnValue + ": " + getTissueOther();
        }
      }
      else {
        returnValue = BigrGbossData.getTissueDescription(code);
      }
    }
    return returnValue;
  }

  /**
   * @param string
   */
  public void setTissue(String string) {
    _tissue = string;
  }

  /**
   * @param string
   */
  public void setTissueOther(String string) {
    _tissueOther = string;
  }

  public PolicyData getPolicyData() {
    if (_policyData == null) {
      String consentId = getConsentId();
      if (consentId != null) {
        _policyData = IltdsUtils.getPolicyForConsent(consentId);
      }
    }
    return _policyData;
  }

  /**
   * @return
   */
  public SampleTypeConfiguration getSampleTypeConfiguration() {
    if (_sampleTypeConfiguration == null) {
      PolicyData policyData = getPolicyData();
      if (policyData != null) {
        String policyId = policyData.getPolicyId();
        SampleTypeConfiguration stc = PolicyUtils.getSampleTypeConfiguration(policyId);
        setSampleTypeConfiguration(stc);
      }
    }
    return _sampleTypeConfiguration;
  }

  /**
   * @param configuration
   */
  public void setSampleTypeConfiguration(SampleTypeConfiguration configuration) {
    _sampleTypeConfiguration = configuration;
  }

  /**
   * @return
   */
  public String getFixativeType() {
    return _fixativeType;
  }

  public String getFixativeTypeAsString() {
    String fixativeTypeCui = getFixativeType();
    if (ApiFunctions.isEmpty(fixativeTypeCui)) {
      return ApiFunctions.EMPTY_STRING;
    }
    else {
      return GbossFactory.getInstance().getDescription(fixativeTypeCui);
    }
  }

  /**
   * @param string
   */
  public void setFixativeType(String string) {
    _fixativeType = string;
  }

  public void addSampleStatus(SampleStatusAssistant status) {
    if (_sampleStatuses == null) {
      _sampleStatuses = new ArrayList();
    }
    _sampleStatuses.add(status);
  }

  public List getSampleStatuses() {
    if (_sampleStatuses == null) {
      return Collections.EMPTY_LIST;
    }
    else {
      return _sampleStatuses;
    }
  }

  /**
   * Returns an indication of whether this sample is imported or traditional.  The sample id
   * must have been set for this method to work properly.
   * 
   * @return  <code>true</code> if this sample is an imported sample; false otherwise.
   */
  public boolean isImported() {
    String sampleId = getSampleId();
    if (ApiFunctions.isEmpty(sampleId)) {
      return false;
    }
    else {
      ValidateIds idValidator = new ValidateIds();
      String validatedId =
        idValidator.validate(getSampleId(), ValidateIds.TYPESET_SAMPLE_IMPORTED, false);
      return (validatedId != null);
    }
  }

  /**
   * Returns an indication of whether this sample has been "created" or not.  For the purposes of
   * this method, we define created strictly as follows:  it was created via sample registration 
   * or the traditional ASM process.  This sample has NOT been created if it either does not exist 
   * at all in the database, or it exists in the database but has only been box scanned.  This 
   * object should be populated with at least the basic information as described in 
   * {@link com.ardais.bigr.iltds.helpers.SampleSelect SampleSelect} before this method is called.
   * 
   * @return  <code>true</code> if this sample has been created; false otherwise.
   */
  public boolean isCreated() {

    // This turns out to be a simple check, since any sample that has been created via either 
    // sample registration or the traditional ASM process will have a consent id. 
    return (!ApiFunctions.isEmpty(getConsentId()));
  }

  /**
   * Returns whether this sample is consumed.  If this sample was explicity set to be consumed 
   * or not via {@link #setConsumed(boolean)}, then return that setting.  Otherwise, if sample
   * statuses were added to this sample, then return there is a COCONSUMED
   * status, and otherwise return false. 
   * 
   * @return  <code>true</code> if this sample is consumed; <code>false</code> otherwise.
   */
  public boolean isConsumed() {
    if (_consumed != null) {
      return _consumed.booleanValue();
    }
    else {
      Iterator statuses = getSampleStatuses().iterator();
      while (statuses.hasNext()) {
        SampleStatusAssistant status = (SampleStatusAssistant) statuses.next();
        if (FormLogic.SMPL_COCONSUMED.equals(status.getStatusTypeCode())) {
          return true;
        }
      }
    }
    return false;
  }

  public void setConsumed(boolean consumed) {
    _consumed = new Boolean(consumed);
  }

  /**
   * @return
   */
  public VariablePrecisionDateTime getCollectionDateTime() {
    return _collectionDateTime;
  }
  
  public String getCollectionDateTimeAsString() {
    return ApiFunctions.safeToString(getCollectionDateTime());
  }

  /**
   * @param time
   */
  public void setCollectionDateTime(VariablePrecisionDateTime time) {
    _collectionDateTime = time;
  }

  /**
   * @return
   */
  public VariablePrecisionDateTime getPreservationDateTime() {
    return _preservationDateTime;
  }
  
  public String getPreservationDateTimeAsString() {
    return ApiFunctions.safeToString(getPreservationDateTime());
  }

  /**
   * @param time
   */
  public void setPreservationDateTime(VariablePrecisionDateTime time) {
    _preservationDateTime = time;
  }

  /**
   * Return the sample type in non-ARTS code form.
   * 
   * @return The translated code name.
   */
  public String getSampleType() {
    String sampleType = _sampleType;
    if (ApiFunctions.isEmpty(sampleType)) {
      if (!ApiFunctions.isEmpty(_sampleTypeCui)) {
        sampleType = GbossFactory.getInstance().getDescription(_sampleTypeCui);
      }
    }
    return ApiFunctions.safeString(sampleType);
  }

  /**
   * Do not call this method if you want to persist the ARTS value. This method is to be used for
   * the translated ARTS value.
   * 
   * @param sampleType The non-ARTS code value of sample type.
   */
  public void setSampleType(String sampleType) {
    _sampleType = sampleType;
  }

  /**
   * Return the buffer type in non-ARTS code form.
   * 
   * @return The translated code name.
   */
  public String getBufferType() {
    String bufferType = _bufferType;
    if (ApiFunctions.isEmpty(bufferType)) {
      if (!ApiFunctions.isEmpty(_bufferTypeCui)) {
        bufferType = GbossFactory.getInstance().getDescription(_bufferTypeCui);
      }
    }
    return ApiFunctions.safeString(bufferType);
  }

  /**
   * Do not call this method if you want to persist the ARTS value. This method is to be used for
   * the translated ARTS value.
   * 
   * @param bufferType The non-ARTS code value of buffer type.
   */
  public void setBufferType(String bufferType) {
    _bufferType = bufferType;
  }

  /**
   * @return
   */
  public String getBufferTypeCui() {
    return _bufferTypeCui;
  }

  /**
   * @param string
   */
  public void setBufferTypeCui(String string) {
    _bufferTypeCui = string;
  }

  /**
   * @return
   */
  public String getBufferTypeOther() {
    return _bufferTypeOther;
  }

  /**
   * @param string
   */
  public void setBufferTypeOther(String string) {
    _bufferTypeOther = string;
  }

  public String getBufferTypeAsString() {
    String bufferTypeCui = getBufferTypeCui();
    if (ApiFunctions.isEmpty(bufferTypeCui)) {
      return ApiFunctions.EMPTY_STRING;
    }
    else if (bufferTypeCui.equals(ArtsConstants.OTHER_BUFFER_TYPE)) {
      String bufferTypeOther = getBufferTypeOther();
      if (ApiFunctions.isEmpty(bufferTypeOther)) {
        return GbossFactory.getInstance().getDescription(bufferTypeCui);
      }
      else {
        return "Other: " + bufferTypeOther; 
      }
    }
    else {
      return GbossFactory.getInstance().getDescription(bufferTypeCui);
    }
  }

  /**
   * @return
   */
  public BigDecimal getCellsPerMl() {
    return _cellsPerMl;
  }

  /**
   * @param decimal
   */
  public void setCellsPerMl(BigDecimal decimal) {
    _cellsPerMl = decimal;
    _cellsPerMlAsString = ApiFunctions.safeToString(decimal);
  }

  /**
   * @return
   */
  public String getCellsPerMlAsString() {
    return ApiFunctions.safeString(ApiFunctions.safeTruncate(_cellsPerMlAsString, 2));
  }

  /**
   * @param string
   */
  public void setCellsPerMlAsString(String string) {
    _cellsPerMlAsString = ApiFunctions.safeTrim(string);
    _cellsPerMl = ApiFunctions.safeBigDecimal(string);
  }

  /**
   * @return
   */
  public Integer getPercentViability() {
    return _percentViability;
  }

  /**
   * @param integer
   */
  public void setPercentViability(Integer integer) {
    _percentViability = integer;
    _percentViabilityAsString = ApiFunctions.safeToString(integer);
  }

  /**
   * @return
   */
  public String getPercentViabilityAsString() {
    return ApiFunctions.safeString(_percentViabilityAsString);
  }

  /**
   * @param string
   */
  public void setPercentViabilityAsString(String string) {
    _percentViabilityAsString = ApiFunctions.safeTrim(string);
    _percentViability = ApiFunctions.safeInteger(string);
  }

  /**
   * @return
   */
  public BigDecimal getTotalNumOfCells() {
    return _totalNumOfCells;
  }

  /**
   * @param decimal
   */
  public void setTotalNumOfCells(BigDecimal decimal) {
    _totalNumOfCells = decimal;
    _totalNumOfCellsAsString = ApiFunctions.safeToString(decimal);
  }

  /**
   * @return
   */
  public String getTotalNumOfCellsAsString() {
    return ApiFunctions.safeString(ApiFunctions.safeTruncate(_totalNumOfCellsAsString, 2));
  }

  /**
   * @param string
   */
  public void setTotalNumOfCellsAsString(String string) {
    _totalNumOfCellsAsString = ApiFunctions.safeTrim(string);
    _totalNumOfCells = ApiFunctions.safeBigDecimal(string);
  }

  /**
   * Return the ARTS code description of total number of cells exponential representation.
   * 
   * @return The translated code name.
   */
  public String getTotalNumOfCellsExRep() {
    String totalNumOfCellsExRep = _totalNumOfCellsExRep;
    if (ApiFunctions.isEmpty(totalNumOfCellsExRep)) {
      if (!ApiFunctions.isEmpty(_totalNumOfCellsExRepCui)) {
        totalNumOfCellsExRep = GbossFactory.getInstance().getDescription(_totalNumOfCellsExRepCui);
      }
    }
    return ApiFunctions.safeString(totalNumOfCellsExRep);
  }

  /**
   * Do not call this method if you want to persist the ARTS value. This method is to be used for
   * the translated ARTS value.
   * 
   * @param totalNumOfCellsExRep The ARTS code description of total number of cells exponential representation.
   */
  public void setTotalNumOfCellsExRep(String totalNumOfCellsExRep) {
    _totalNumOfCellsExRep = totalNumOfCellsExRep;
  }

  /**
   * @return
   */
  public String getTotalNumOfCellsExRepCui() {
    return _totalNumOfCellsExRepCui;
  }

  /**
   * @param string
   */
  public void setTotalNumOfCellsExRepCui(String string) {
    _totalNumOfCellsExRepCui = string;
  }
 
 /**
   * @return
   */
 public String getVolumeUnitCui(){
    return _volumeUnitCui;
}
  /**
   * @param string
   */
  public void setVolumeUnitCui(String string){
    _volumeUnitCui = string;
     updateVolumeInUl();
 }
  
  /**
   * @return
   */
  public BigDecimal getVolume() {
    return _volume;
  }

  /**
   * @param decimal
   */
  public void setVolume(BigDecimal decimal) {
    _volume = decimal;
    _volumeAsString = ApiFunctions.safeToString(decimal);
    updateVolumeInUl();
}
 /**
   * @return
   */
  public BigDecimal getVolumeInUl() {
    return _volumeInUl;
  }
  /**
   * @return
   */
  public String getVolumeUnitAsString() {
    String volumeUnitString = _volumeUnitCui;
          if (!ApiFunctions.isEmpty(_volumeUnitCui) ) {
        volumeUnitString = GbossFactory.getInstance().getDescription(_volumeUnitCui);
   }
    return ApiFunctions.safeString(volumeUnitString);
     }
 /**
   * @param decimal
   */
  public void setVolumeInUl(BigDecimal decimal) {
    _volumeInUl = decimal;
    _volume = decimal;
    _volumeAsString = ApiFunctions.safeToString(decimal);
    _volumeUnitCui = ArtsConstants.VOLUME_UNIT_UL;
 }
  
  /**
   * @return
   */
  public BigDecimal getWeight(){
    return _weight;
  }
  
  public void updateWeightInMg(){
   if (!ApiFunctions.isEmpty(_weightUnitCui) && _weight != null){
      BigDecimal mgConversionFactor = Constants.getWeightInMgConversionFactor(_weightUnitCui);
      _weightInMg = _weight.multiply(mgConversionFactor);
     }
  }
  
  /**
   * @param decimal
   */
  public void setWeight(BigDecimal decimal){
       _weight = decimal;
       _weightAsString = ApiFunctions.safeToString(decimal);
       updateWeightInMg();
 }
  /**
   * @return
   */
  public BigDecimal getWeightInMg(){
         return _weightInMg;
  }
   /**
   * @param decimal
   */
  public void setWeightInMg(BigDecimal decimal){
      _weightInMg = decimal;
      _weight = decimal;
      _weightAsString = ApiFunctions.safeToString(decimal);
      _weightUnitCui = ArtsConstants.WEIGHT_UNIT_MG;
}
 /**
   * @return
   */
  public String getWeightUnitCui(){
    return _weightUnitCui;
      }
  /**
   * @return
   */
  public String getWeightUnitAsString() {
    String weightUnitString = _weightUnitCui;
    if (!ApiFunctions.isEmpty(_weightUnitCui) ) {
      weightUnitString = GbossFactory.getInstance().getDescription(_weightUnitCui);
      }
        return ApiFunctions.safeString(weightUnitString);
  }
 /**
   * @param string
   */
   public void setWeightUnitCui(String string){
       _weightUnitCui = string;
      updateWeightInMg();
 }
   
  public void updateVolumeInUl(){
     if (!ApiFunctions.isEmpty(_volumeUnitCui) && _volume != null ){
       BigDecimal ulConversionFactor = Constants.getVolumeInUlConversionFactor(_volumeUnitCui);
       _volumeInUl = _volume.multiply(ulConversionFactor);
      }
    }
   /**
   * @return
   */
  public String getVolumeAsString() {
    return ApiFunctions.safeString(_volumeAsString);
  }
   /**
   * @param string
   */
  public void setVolumeAsString(String string) {
    _volumeAsString = ApiFunctions.safeTrim(string);
   _volume = ApiFunctions.safeBigDecimal(string);
   updateVolumeInUl();
}
/**
   * @return
   */
  public String getWeightAsString() {
    return ApiFunctions.safeString(_weightAsString);
  }
  /**
   * @param string
   */
  public void setWeightAsString(String string) {
    _weightAsString = ApiFunctions.safeTrim(string);
    _weight = ApiFunctions.safeBigDecimal(string);
   updateWeightInMg();
 } 
  /**
   * @return
   */
  public String getSource() {
    return _source;
  }

  /**
   * @param string
   */
  public void setSource(String string) {
    _source = string;
  }

  /**
   * @return
   */
  public BigDecimal getYield() {
    return _yield;
  }

  /**
   * @param decimal
   */
  public void setYield(BigDecimal decimal) {
    _yield = decimal;
    _yieldAsString = ApiFunctions.safeToString(decimal);
  }

  /**
   * @return
   */
  public String getYieldAsString() {
    return ApiFunctions.safeString(_yieldAsString);
  }

  /**
   * @param string
   */
  public void setYieldAsString(String string) {
    _yieldAsString = ApiFunctions.safeTrim(string);
    _yield = ApiFunctions.safeBigDecimal(string);
  }

  /**
   * @return
   */
  public BigDecimal getConcentration() {
    return _concentration;
  }

  /**
   * @param decimal
   */
  public void setConcentration(BigDecimal decimal) {
    _concentration = decimal;
    _concentrationAsString = ApiFunctions.safeToString(decimal);
  }

  /**
   * @return
   */
  public String getConcentrationAsString() {
    return ApiFunctions.safeString(_concentrationAsString);
  }

  /**
   * @param string
   */
  public void setConcentrationAsString(String string) {
    _concentrationAsString = ApiFunctions.safeTrim(string);
    _concentration = ApiFunctions.safeBigDecimal(string);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.kc.form.BigrFormInstanceEnabled#getBigrFormInstance()
   */
  public BigrFormInstance getBigrFormInstance() {
    return _bigrFormInstance;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.kc.form.BigrFormInstanceEnabled#setBigrFormInstance(com.ardais.bigr.kc.form.BigrFormInstance)
   */
  public void setBigrFormInstance(BigrFormInstance bigrFormInstance) {
    _bigrFormInstance = bigrFormInstance;
  }

  public String getRegistrationFormId() {
    return _registrationFormId;
  }
  public void setRegistrationFormId(String id) {
    _registrationFormId = id;
  }

  public DataFormDefinition getRegistrationForm() {
    return _registrationForm;
  }
  public void setRegistrationForm(DataFormDefinition form) {
    _registrationForm = form;
  }

  public FormInstance getRegistrationFormInstance() {
    return _registrationFormInstance;
  }
  public void setRegistrationFormInstance(FormInstance form) {
    _registrationFormInstance = form;
  }

  public String getKcForm() {
    return _kcForm;
  }

  public void setKcForm(String kcForm) {
    _kcForm = kcForm;
  }

  public boolean isPrintLabel() {
    return _printLabel;
  }
  
  public String getLabelCount() {
    return _labelCount;
  }

  public String getPrinterName() {
    return _printerName;
  }

  public String getTemplateDefinitionName() {
    return _templateDefinitionName;
  }

  public void setPrintLabel(boolean printLabel) {
    _printLabel = printLabel;
  }

  public void setLabelCount(String labelCount) {
    _labelCount = labelCount;
  }

  public void setPrinterName(String printerName) {
    _printerName = printerName;
  }

  public void setTemplateDefinitionName(String templateDefinitionName) {
    _templateDefinitionName = templateDefinitionName;
  }
  
  public String getGeneratedSampleId() {
    return _generatedSampleId;
  }
  public void setGeneratedSampleId(String generatedSampleId) {
    _generatedSampleId = generatedSampleId;
  }
}
