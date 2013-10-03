package com.ardais.bigr.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Contains constants that relate to ARTS.
 */
public class ArtsConstants {
	private static Set _notReportedSet;
	private static Set _displayAsEmptySet;
	private static Map _tumorStageTypeMap;

  private final static String TUMOR_STAGE_TYPE_ANNARBOR = "Ann Arbor";
	private final static String TUMOR_STAGE_TYPE_AJCC5 = "AJCC5";
	private final static String TUMOR_STAGE_TYPE_AJCC6 = "AJCC6";
	private final static String TUMOR_STAGE_TYPE_CAP = "CAP";
	private final static String TUMOR_STAGE_TYPE_NR = "Not Reported";
	private final static String TUMOR_STAGE_TYPE_OTHER = "";

  //the Ann Arbor constant needs to be public because PathSection.jsp uses it
  public final static String CODE_TUMOR_STAGE_TYPE_ANNARBOR = "CA03139C";
	private final static String CODE_TUMOR_STAGE_TYPE_AJCC5 = "CA00421C";
	private final static String CODE_TUMOR_STAGE_TYPE_AJCC6 = "CA00422C";
	private final static String CODE_TUMOR_STAGE_TYPE_CAP = "CA00010G";
	private final static String CODE_TUMOR_STAGE_TYPE_NR = "CA00354C";
	private final static String CODE_TUMOR_STAGE_TYPE_OTHER = "CA00446C";
  
  public final static String DIAGNOSIS_NEOPLASTIC = "55342001^^";
  public final static String DIAGNOSIS_NON_NEOPLASTIC = "64572001^^";
  public final static String DIAGNOSIS_OTHER = "CA00089D^^";
  public final static String DIAGNOSIS_NO_ABNORMALITY = "CA00237D^^";
  
  //Paraffin dimension set
  public final static String PARAFFIN_DIMENSION_NODATA = "CA01025C";
  
  // Password policy codes.
  public final static String PASSWORD_POLICY_EXPIRES = "CA01759C";
  public final static String PASSWORD_POLICY_NEVER_EXPIRES = "CA01760C";
  public final static String PASSWORD_POLICY_INHERIT = "CA01761C";

  public final static String STANDARD_VALUE_CUI = "CA11534C";
  
  public final static String DEMOGRAPHIC_DATA_ETHNIC_BG = "CA01136C";
  public final static String DEMOGRAPHIC_DATA_RACE = "CA01138C";
  
  public final static String LABORATORY_STUDIES = "CA01353C";
  public final static String DEMOGRAPHIC_DATA = "CA01274C";
  public final static String DIAGNOSIS = "CA01132C";

  // Other codes
  public final static String OTHER_TISSUE = "91723000";
  public final static String OTHER_DIAGNOSIS = "CA00038D^^";
  public final static String OTHER_PROCEDURE = "CA00004P";
  public final static String OTHER_CHEMOTHERAPY = "CA01131C";
  public final static String OTHER_RADIOTHERAPY = "CA01244C";
  public final static String OTHER_PRIMARY_TUMOR_STAGE = "CA00434C";
  public final static String OTHER_METASTASIS_STAGE = "CA00431C";
  public final static String OTHER_LYMPH_NODE_STAGE = "CA00445C";
  public final static String OTHER_STAGE_GROUPING = "CA00418C";
  public final static String OTHER_EM_EXAM = "CA01170C";
  public final static String OTHER_PAST_MEDICAL_HISTORY = "CA01222C";
  public final static String OTHER_FAMILY_HISTORY = "CA01181C";
  public final static String OTHER_BUFFER_TYPE = "CA11163C";
  public final static String OTHER_DERIVATION_OPERATION = "CA11185C";
    
  // Allocation format codes.
  public final static String ALLOCATION_FORMAT_ALL = "CA01094C";
  public final static String ALLOCATION_FORMAT_NONE = "CA01095C";

  // Box layout axis label codes.
  public final static String BOX_LAYOUT_ASC_NUMBERS = "CA01805C";
  public final static String BOX_LAYOUT_ASC_LETTERS = "CA01807C";
  public final static String BOX_LAYOUT_DSC_NUMBERS = "CA01785C";
  public final static String BOX_LAYOUT_DSC_LETTERS = "CA01784C";
  
  // Box layout tab index codes.
  public final static String BOX_LAYOUT_TAB_DOWN = "CA01809C";
  public final static String BOX_LAYOUT_TAB_RIGHT = "CA01810C";
  
  // Sample type codes.
  public final static String SAMPLE_TYPE_FROZEN_TISSUE = "CA00052C";
  public final static String SAMPLE_TYPE_PARAFFIN_TISSUE = "CA09400C";
  public final static String SAMPLE_TYPE_RNA = "CA09572C";
  public final static String SAMPLE_TYPE_SERUM = "CA11117C";
  public final static String SAMPLE_TYPE_UNKNOWN = "CA11122C";

  // Sample attribute CUIs.  These must match the CUIs in the "sample_attributes" concept list
  // in systemConfiguration.xml.
  public final static String ATTRIBUTE_BUFFER_TYPE = "CA11155C";
  public final static String ATTRIBUTE_CELLS_PER_ML = "CA11157C";
  public final static String ATTRIBUTE_COMMENT = "CA11154C";
  public final static String ATTRIBUTE_CONCENTRATION = "CA01033C";
  public final static String ATTRIBUTE_DATE_OF_COLLECTION = "CA11152C";
  public final static String ATTRIBUTE_DATE_OF_PRESERVATION = "CA11153C";
  public final static String ATTRIBUTE_FIXATIVE_TYPE = "CA11148C";
  public final static String ATTRIBUTE_FORMAT_DETAIL = "CA11149C";
  public final static String ATTRIBUTE_GROSS_APPEARANCE = "CA11147C";
  public final static String ATTRIBUTE_PERCENT_VIABILITY = "CA11158C";
  public final static String ATTRIBUTE_PREPARED_BY = "CA11150C";
  public final static String ATTRIBUTE_PROCEDURE = "CA11145C";
  public final static String ATTRIBUTE_SOURCE = "CA11747C";
  public final static String ATTRIBUTE_TISSUE = "CA11146C";
  public final static String ATTRIBUTE_TOTAL_NUMBER_OF_CELLS = "CA11156C";
  public final static String ATTRIBUTE_VOLUME = "CA11151C";
  public final static String ATTRIBUTE_WEIGHT = "CA11745C";
  public final static String ATTRIBUTE_YIELD = "CA01034C";
  
  // Case attribute CUIs.  These must match the CUIs in the "case_attributes" concept list
  // in systemConfiguration.xml.
  public final static String ATTRIBUTE_CASE_COMMENTS = "CA11755C";   
  public final static String ATTRIBUTE_CASE_DIAGNOSIS = "CA11754C";

  // Donor attribute CUIs.  These must match the CUIs in the "donor_attributes" concept list
  // in systemConfiguration.xml.
  public final static String ATTRIBUTE_DONOR_ETHNICITY = "CA11751C";
  public final static String ATTRIBUTE_DONOR_GENDER = "CA11749C";
  public final static String ATTRIBUTE_DONOR_NOTES = "CA11753C";   
  public final static String ATTRIBUTE_DONOR_RACE = "CA11752C";
  public final static String ATTRIBUTE_DONOR_YOB = "CA11748C";
  public final static String ATTRIBUTE_DONOR_ZIP_CODE = "CA11750C";
  
  private static Set _donorAttributes;
  static {
    _donorAttributes = new HashSet();
    _donorAttributes.add(ArtsConstants.ATTRIBUTE_DONOR_ETHNICITY);
    _donorAttributes.add(ArtsConstants.ATTRIBUTE_DONOR_GENDER);
    _donorAttributes.add(ArtsConstants.ATTRIBUTE_DONOR_NOTES);
    _donorAttributes.add(ArtsConstants.ATTRIBUTE_DONOR_RACE);
    _donorAttributes.add(ArtsConstants.ATTRIBUTE_DONOR_YOB);
    _donorAttributes.add(ArtsConstants.ATTRIBUTE_DONOR_ZIP_CODE);
  }

  private static Set _caseAttributes;
  static {
    _caseAttributes = new HashSet();
    _caseAttributes.add(ArtsConstants.ATTRIBUTE_CASE_COMMENTS);
    _caseAttributes.add(ArtsConstants.ATTRIBUTE_CASE_DIAGNOSIS);
  }
  
  private static Set _sampleAttributes;
  static {
    _sampleAttributes = new HashSet();
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_BUFFER_TYPE);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_CELLS_PER_ML);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_COMMENT);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_CONCENTRATION);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_FORMAT_DETAIL);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_PREPARED_BY);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_PROCEDURE);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_SOURCE);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_TISSUE);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_VOLUME);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_WEIGHT);
    _sampleAttributes.add(ArtsConstants.ATTRIBUTE_YIELD);
  }
  
  //unit of volume consants
  public final static String VOLUME_UNIT_ML = "CA11742C";
  public final static String VOLUME_UNIT_UL = "CA11743C";
  public final static String VOLUME_UNIT_NL = "CA11744C";
  //unit of weight constants
  public final static String WEIGHT_UNIT_G = "CA11739C";
  public final static String WEIGHT_UNIT_MG = "CA11740C";
  public final static String WEIGHT_UNIT_UG = "CA11746C";
  public final static String WEIGHT_UNIT_NG = "CA11741C";
  
  //Value Set constants
  public final static String VALUE_SET_ACCOUNT_PASSWORD_POLICY = "CA11536C";
  public final static String VALUE_SET_ALLOCATION_FORMAT = "CA11537C";
  public final static String VALUE_SET_BOX_LAYOUT_LABEL_TYPE_XY_AXIS = "CA11538C";
  public final static String VALUE_SET_BOX_LAYOUT_TAB_DIRECTION = "CA11539C";
  public final static String VALUE_SET_CASE_PULL_REASON = "CA11540C";
  public final static String VALUE_SET_DDC_COUNT_LYMPH_NODES = "CA11541C";
  public final static String VALUE_SET_DIAGNOSTICS_DDC1 = "CA11546C";
  public final static String VALUE_SET_DRE_RESULTS = "CA11547C";
  public final static String VALUE_SET_IMPORTED_CASE_PULL_REASON = "CA11548C";
  public final static String VALUE_SET_LIMS_STRUCTURE = "CA11549C";
  public final static String VALUE_SET_REQUEST_DELIVERY_TYPE = "CA11551C";
  public final static String VALUE_SET_SS_SRCH_CELL_PROLIFERATION = "CA04672C";
  public final static String VALUE_SET_SS_SRCH_DISTANT_METASTASIS = "CA11552C";
  public final static String VALUE_SET_SS_SRCH_DRE_RESULTS = "CA11553C";
  public final static String VALUE_SET_SS_SRCH_GENES = "CA04674C";
  public final static String VALUE_SET_SS_SRCH_HIST_NUCLEAR_GRADE = "CA11554C";
  public final static String VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY = "CA04673C";
  public final static String VALUE_SET_SS_SRCH_IMMUNOPHENOTYPE = "CA04675C";
  public final static String VALUE_SET_SS_SRCH_LYMPH_NODE_STAGE_DESC = "CA11555C";
  public final static String VALUE_SET_SS_SRCH_PSA_RESULTS = "CA11556C";
  public final static String VALUE_SET_SS_SRCH_STAGE_GROUPINGS = "CA11557C";
  public final static String VALUE_SET_SS_SRCH_TUMOR_STAGE_DESC = "CA11558C";
  public final static String VALUE_SET_USER_PASSWORD_POLICY = "CA11559C";
  //from Pdc_Snomed_LookupBean.initPdcLookupStageHierarchies
  public final static String VALUE_SET_DDC_DISTANT_METASTASIS = "CA11542C";
  public final static String VALUE_SET_DDC_LYMPH_NODE_STAGE_DESC = "CA11543C";
  public final static String VALUE_SET_DDC_STAGE_GROUPING = "CA11544C";
  public final static String VALUE_SET_DDC_TUMOR_STAGE_DESC = "CA11545C";
  //from GetDxTcHierarchySBBean.getLesionMap()
  public final static String VALUE_SET_LESIONS = "CA12171C";
  //from GetDxTcHierarchySBBean.getDxTcHierarchyConceptGraph()
  //and from GetDxTcHierarchySBBean.getDxTcHierarchy(), passing "D" as the code
  public final static String VALUE_SET_DIAGNOSIS_HIERARCHY = "CA11969C";
  //from GetDxTcHierarchySBBean.getDxTcProcedureMap(), passing "T" as the code
  //and from GetDxTcHierarchySBBean.getDxTcHierarchy(), passing "T" as the code
  public final static String VALUE_SET_TISSUE_HIERARCHY = "CA11970C";
  //from GetDxTcHierarchySBBean.getDxTcProcedureMap(), passing "P" as the code
  //and from GetDxTcHierarchySBBean.getDxTcHierarchy(), passing "P" as the code
  public final static String VALUE_SET_PROCEDURE_HIERARCHY = "CA11971C";
  //from PDC_LOOKUP, using category domain
  public final static String VALUE_SET_TYPE_OF_FIXATIVE = "CA12177C";
  public final static String VALUE_SET_SAMPLE_FORMAT_DETAIL = "CA12178C";
  public final static String VALUE_SET_BUFFER_TYPE = "CA12179C";
  public final static String VALUE_SET_EXP_CELLS_PER_ML = "CA12180C";
  public final static String VALUE_SET_LIMS_INFLAMMATION = "CA12181C";
  public final static String VALUE_SET_LIMS_TUMOR_FEATURES = "CA12182C";
  public final static String VALUE_SET_LIMS_TUMOR_STROMA_CELLULAR_FEATURES = "CA12183C";
  public final static String VALUE_SET_LIMS_TUMOR_STROMA_HYPOACELLULAR_FEATURES = "CA12184C";
  public final static String VALUE_SET_SURGICAL_REMOVAL_TIME_ACCURACY = "CA12185C";
  public final static String VALUE_SET_PARAFFIN_DIMENSIONS = "CA12186C";
  public final static String VALUE_SET_PARAFFIN_FEATURE = "CA12187C";
  public final static String VALUE_SET_SAMPLE_TYPE = "CA12188C";
  public final static String VALUE_SET_LIMS_RE_PV_REASON = "CA12189C";
  public final static String VALUE_SET_LIMS_RE_PV_SOURCE = "CA12190C";
  public final static String VALUE_SET_LIMS_INCIDENT_ACTION = "CA12191C";
  public final static String VALUE_SET_LIMS_PULL_REASON = "CA12192C";
  public final static String VALUE_SET_LIMS_EXTERNAL_FEATURES = "CA12193C";
  public final static String VALUE_SET_LIMS_INTERNAL_QUALITY_ISSUES = "CA12194C";
  public final static String VALUE_SET_HISTO_REEMBED_REASON = "CA12195C";
  public final static String VALUE_SET_PATH_SECTION_ID = "CA12196C";
  public final static String VALUE_SET_SAMPLE_VOLUME_UNIT = "CA12197C";
  public final static String VALUE_SET_SAMPLE_WEIGHT_UNIT = "CA12198C";
  public final static String VALUE_SET_MEDICAL_RECORD_SECTION = "CA12199C";
  public final static String VALUE_SET_COUNTRY = "CA12200C";
  public final static String VALUE_SET_ETHNICITY = "CA12201C";
  public final static String VALUE_SET_RACE = "CA12202C";
  public final static String VALUE_SET_DISEASE_TYPE = "CA12203C";
  //Appearance value sets.  These did not come from PDC_LOOKUP but were in the
  //Java code up to now
  public final static String VALUE_SET_GROSS_APPEARANCE = "CA12204C";
  public final static String VALUE_SET_MICROSCOPIC_APPEARANCE = "CA12205C";
  //from PDC_LOOKUP, using category domain and domain_rep.  For these, domain_rep
  //was the same for all rows
  public final static String VALUE_SET_CELL_INFILTRATE_AMT = "CA12206C";
  public final static String VALUE_SET_EXTRANODAL_SPREAD = "CA12207C";
  public final static String VALUE_SET_EXTENSIVE_INTRADUCTAL_COMPONENT = "CA12208C";
  public final static String VALUE_SET_INFLAMMATORY_CELL_INFILTRATE = "CA12209C";
  public final static String VALUE_SET_JOINT_COMPONENT = "CA12210C";
  public final static String VALUE_SET_LYMPHATIC_VASCULAR_INVASION = "CA12211C";
  public final static String VALUE_SET_PERINEURAL_INVASION_IND = "CA12212C";
  public final static String VALUE_SET_VENOUS_VESSEL_INVASION = "CA12213C";
  //from PDC_LOOKUP, using category domain and domain_rep.    
  public final static String VALUE_SET_TUMOR_CONFIGURATION = "CA12217C";
  public final static String VALUE_SET_TUMOR_STAGE_TYPE = "CA12219C";
  public final static String VALUE_SET_HISTOLOGICAL_TYPE = "CA12246C";
  //from PDC_LOOKUP, using category domain, source, and domain_rep.    
  public final static String VALUE_SET_HISTOLOGICAL_NUCLEAR_GRADE = "CA12239C";
  public final static String VALUE_SET_HISTOLOGICAL_NUCLEAR_GRADE_DUKE = "CA12241C";
  
	/**
	 * @see java.lang.Object#Object()
	 */
	private ArtsConstants() {
	}

	static {
		_notReportedSet = new HashSet();
		_notReportedSet.add("CA00346C");
		_notReportedSet.add("CA00592C");
		_notReportedSet.add("CA00354C");
		_notReportedSet.add("CA00430C");
		_notReportedSet.add("CA00228C");
		_notReportedSet.add("CA00233C");
	}

	static {
		_displayAsEmptySet = new HashSet();
		_displayAsEmptySet.add("CA00353C");
		_displayAsEmptySet.add("CA00433C");
		_displayAsEmptySet.add("CA00434C");
		_displayAsEmptySet.add("CA00429C");
		_displayAsEmptySet.add("CA00431C");
		_displayAsEmptySet.add("CA00444C");
		_displayAsEmptySet.add("CA00445C");
//		_displayAsEmptySet.add("309689007");
		_displayAsEmptySet.add("CA00419C");
		_displayAsEmptySet.add("CA00418C");
		_displayAsEmptySet.add("CA00446C");
//		_displayAsEmptySet.add("CA00229C");  no longer used
		_displayAsEmptySet.add("CA00416C");
		_displayAsEmptySet.add("CA00356C");
//		_displayAsEmptySet.add("CA00234C");
//		_displayAsEmptySet.add("CA00417C");  was "see other path notes" recycled as "transformation zone (-)"
	}

	static {
		_tumorStageTypeMap = new HashMap();
    _tumorStageTypeMap.put(
      CODE_TUMOR_STAGE_TYPE_ANNARBOR,
      TUMOR_STAGE_TYPE_ANNARBOR);
		_tumorStageTypeMap.put(
			CODE_TUMOR_STAGE_TYPE_AJCC5,
			TUMOR_STAGE_TYPE_AJCC5);
		_tumorStageTypeMap.put(
			CODE_TUMOR_STAGE_TYPE_AJCC6,
			TUMOR_STAGE_TYPE_AJCC6);
		_tumorStageTypeMap.put(CODE_TUMOR_STAGE_TYPE_CAP, TUMOR_STAGE_TYPE_CAP);
		_tumorStageTypeMap.put(CODE_TUMOR_STAGE_TYPE_NR, TUMOR_STAGE_TYPE_NR);
		_tumorStageTypeMap.put(
			CODE_TUMOR_STAGE_TYPE_OTHER,
			TUMOR_STAGE_TYPE_OTHER);
	}

	public static boolean isLookupNotReported(String code) {
		return _notReportedSet.contains(code);
	}

	public static boolean isDisplayLookupAsEmpty(String code) {
		return _displayAsEmptySet.contains(code);
	}

	public static String getTumorStageType(String code) {
		return (String) _tumorStageTypeMap.get(code);
	}

  /**
   * Returns the set of all donor attributes, where each member of the set is a donor
   * attribute CUI.
   * 
   * @return The set of donor attributes.
   */
  public static Set getDonorAttributes() {
    return new HashSet(_donorAttributes);
  }

  /**
   * Returns the set of all case attributes, where each member of the set is a case
   * attribute CUI.
   * 
   * @return The set of case attributes.
   */
  public static Set getCaseAttributes() {
    return new HashSet(_caseAttributes);
  }

  /**
   * Returns the set of all sample attributes, where each member of the set is a sample
   * attribute CUI.
   * 
   * @return The set of sample attributes.
   */
  public static Set getSampleAttributes() {
    return new HashSet(_sampleAttributes);
  }
}
