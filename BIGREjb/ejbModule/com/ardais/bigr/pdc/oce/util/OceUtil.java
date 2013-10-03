package com.ardais.bigr.pdc.oce.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.oce.Oce;
import com.ardais.bigr.pdc.oce.OceHome;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.EjbHomes;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.gboss.GbossValueSet;

/**
 * @author Nagraj
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OceUtil {

  private static Map _oceDataMap;
  private static Map oceMap;

  //Type codes for Attributes
  public final static String OCE_TYPECODE_DIAGNOSIS_IND = "AD";
  public final static String OCE_TYPECODE_DIAGNOSIS = "Diagnosis";

  public final static String OCE_TYPECODE_TISSUE_IND = "AT";
  public final static String OCE_TYPECODE_TISSUE = "Tissue";

  public final static String OCE_TYPECODE_PROCEDURE_IND = "AP";
  public final static String OCE_TYPECODE_PROCEDURE = "Procedure";

  public final static String OCE_TYPECODE_METASTASIS_IND = "AM";
  public final static String OCE_TYPECODE_METASTASIS = "Metastasis";

  public final static String OCE_TYPECODE_TREATMENT_IND = "AR";
  public final static String OCE_TYPECODE_TREATMENT = "Treatment";

  public final static String OCE_TYPECODE_DISTANT_METASTASIS_IND = "CM";
  public final static String OCE_TYPECODE_DISTANT_METASTASIS = "Distant Metastasis";

  public final static String OCE_TYPECODE_HNG_IND = "CN";
  public final static String OCE_TYPECODE_HNG = "Histological Nuclear Grade";

  public final static String OCE_TYPECODE_HISTOLOGICAL_TYPE_IND = "CH";
  public final static String OCE_TYPECODE_HISTOLOGICAL_TYPE = "Histological Type";

  public final static String OCE_TYPECODE_LYMPH_NODE_IND = "CL";
  public final static String OCE_TYPECODE_LYMPH_NODE = "Lymph Node Stage Desc";

  public final static String OCE_TYPECODE_STAGE_GROUPING_IND = "CG";
  public final static String OCE_TYPECODE_STAGE_GROUPING = "Stage Groupings";

  public final static String OCE_TYPECODE_TUMOR_STAGE_DESC_IND = "CS";
  public final static String OCE_TYPECODE_TUMOR_STAGE_DESC = "Tumor Stage Desc";

  public final static String OCE_TYPECODE_TUMOR_STAGE_TYPE_IND = "CT";
  public final static String OCE_TYPECODE_TUMOR_STAGE_TYPE = "Tumor Stage Type";

  //Type codes for status
  public final static String OCE_STATUS_FIXED_IND = "F";
  public final static String OCE_STATUS_FIXED = "Fixed";

  public final static String OCE_STATUS_NOTFIXED_IND = "N";
  public final static String OCE_STATUS_NOTFIXED = "New";

  public final static String OCE_STATUS_OBSOLETE_IND = "O";
  public final static String OCE_STATUS_OBSOLETE = "Obsolete";

  public final static String OCE_STATUS_ALL_IND = "A";
  public final static String OCE_STATUS_ALL = "All";

  public final static String OCE_STATUS_HOLD_IND = "H";
  public final static String OCE_STATUS_HOLD = "Hold";

  public final static String OCE_STATUS_INAPP_IND = "I";
  public final static String OCE_STATUS_INAPP = "Inapp";

  public final static String OCE_STATUS_CONCEPT_IND = "C";
  public final static String OCE_STATUS_CONCEPT = "Concept";

  public final static String OCE_STATUS_NOTFIXED_HOLD_CONCEPT_IND = "NHC";
  public final static String OCE_STATUS_NOTFIXED_HOLD_CONCEPT = "Not fixed";

  //Codes for each table attribute
  //LIMS_PATHOLOGY_EVALUATION
  public static final String LIMS_PE_OTHER_DIAGNOSIS = "LIMS_PE_OTHER_DIAGNOSIS";
  public static final String LIMS_PE_OTHER_TISSUE_ORIGIN = "LIMS_PE_OTHER_TISSUE_ORIGIN";
  public static final String LIMS_PE_OTHER_TISSUE_FINDING = "LIMS_PE_OTHER_TISSUE_FINDING";
  //ILTDS_ASM
  public static final String ILTDS_ASM_OTHER_ORGANSITE = "ILTDS_ASM_OTHER_ORGANSITE";

  //ILTDS_ASM_FORM
  public static final String ILTDS_ASMFORM_OTHER_SURGICAL_SPEC =
    "ILTDS_ASMFORM_OTHER_SURGICAL_SPEC";
  //ILTDS_INFORMED_CONSENT
  public static final String ILTDS_INFORMED_CONSENT_OTHER_DX = "ILTDS_INFORMED_CONSENT_OTHER_DX";
  //PDC_PATHOLOGY_REPORT  	
  public static final String PDC_PATH_REPORT_OTHER_PROC = "PDC_PATH_REPORT_OTHER_PROC";
  //PDC_PATH_REPORT_DX
  public static final String PDC_PATHREPORT_DX_OTHER_DX = "PDC_PATHREPORT_DX_OTHER_DX";
  public static final String PDC_PATHREPORT_DX_OTHER_TISSUE = "PDC_PATHREPORT_DX_OTHER_TISSUE";
  //PDC_PATH_REPORT_SECTION
  public static final String PDC_PATH_SECTION_OTHER_DX = "PDC_PATH_SECTION_OTHER_DX";
  public static final String PDC_PATH_SECTION_OTHER_TISSUE_ORIGIN =
    "PDC_PATH_SECTION_OTHER_TISSUE_ORIGIN";
  public static final String PDC_PATH_SECTION_OTHER_TISSUE_FINDING =
    "PDC_PATH_SECTION_OTHER_TISSUE_FINDING";

  public static final String PDC_PATH_SECTION_OTHER_DISTANT_METASTASIS =
    "PDC_PATH_SECTION_OTHER_DISTANT_METASTASIS";
  public static final String PDC_PATH_SECTION_OTHER_HNG = "PDC_PATH_SECTION_OTHER_HNG";
  public static final String PDC_PATH_SECTION_OTHER_HISTOLOGICAL_TYPE =
    "PDC_PATH_SECTION_OTHER_HISTOLOGICAL_TYPE";
  public static final String PDC_PATH_SECTION_OTHER_LYMPH_NODE =
    "PDC_PATH_SECTION_OTHER_LYMPH_NODE";
  public static final String PDC_PATH_SECTION_OTHER_STAGE_GROUPING =
    "PDC_PATH_SECTION_OTHER_STAGE_GROUPING";
  public static final String PDC_PATH_SECTION_OTHER_TUMOR_STAGE_DESC =
    "PDC_PATH_SECTION_OTHER_TUMOR_STAGE_DESC";
  public static final String PDC_PATH_SECTION_OTHER_TUMOR_STAGE_TYPE =
    "PDC_PATH_SECTION_OTHER_TUMOR_STAGE_TYPE";

  public static final String SURGICAL_SPEC_WHERE_CLAUSE =
    "WHERE ARDAIS_ID = ? AND CONSENT_ID = ? AND ASM_FORM_ID = ?";

  public static List FILTERED_ATTRIBUTES = new ArrayList();

  //Static block for initializing 
  static {
    oceMap = new HashMap();
    oceMap.put(OCE_TYPECODE_DIAGNOSIS_IND, OCE_TYPECODE_DIAGNOSIS);
    oceMap.put(OCE_TYPECODE_TISSUE_IND, OCE_TYPECODE_TISSUE);
    oceMap.put(OCE_TYPECODE_PROCEDURE_IND, OCE_TYPECODE_PROCEDURE);
    oceMap.put(OCE_TYPECODE_METASTASIS_IND, OCE_TYPECODE_METASTASIS);
    oceMap.put(OCE_TYPECODE_TREATMENT_IND, OCE_TYPECODE_TREATMENT);

    oceMap.put(OCE_TYPECODE_DISTANT_METASTASIS_IND, OCE_TYPECODE_DISTANT_METASTASIS);
    oceMap.put(OCE_TYPECODE_HNG_IND, OCE_TYPECODE_HNG);
    oceMap.put(OCE_TYPECODE_HISTOLOGICAL_TYPE_IND, OCE_TYPECODE_HISTOLOGICAL_TYPE);
    oceMap.put(OCE_TYPECODE_LYMPH_NODE_IND, OCE_TYPECODE_LYMPH_NODE);
    oceMap.put(OCE_TYPECODE_STAGE_GROUPING_IND, OCE_TYPECODE_STAGE_GROUPING);
    oceMap.put(OCE_TYPECODE_TUMOR_STAGE_DESC_IND, OCE_TYPECODE_TUMOR_STAGE_DESC);
    oceMap.put(OCE_TYPECODE_TUMOR_STAGE_TYPE_IND, OCE_TYPECODE_TUMOR_STAGE_TYPE);

    oceMap.put(OCE_STATUS_FIXED_IND, OCE_STATUS_FIXED);
    oceMap.put(OCE_STATUS_NOTFIXED_IND, OCE_STATUS_NOTFIXED);
    oceMap.put(OCE_STATUS_OBSOLETE_IND, OCE_STATUS_OBSOLETE);
    oceMap.put(OCE_STATUS_ALL_IND, OCE_STATUS_ALL);
    oceMap.put(OCE_STATUS_HOLD_IND, OCE_STATUS_HOLD);
    oceMap.put(OCE_STATUS_INAPP_IND, OCE_STATUS_INAPP);
    oceMap.put(OCE_STATUS_CONCEPT_IND, OCE_STATUS_CONCEPT);
    oceMap.put(OCE_STATUS_NOTFIXED_HOLD_CONCEPT_IND, OCE_STATUS_NOTFIXED_HOLD_CONCEPT);

    _oceDataMap = new HashMap();
    //LIMS_PATHOLOGY_EVALUATION
    _oceDataMap.put(
      LIMS_PE_OTHER_DIAGNOSIS,
      new createOceData(
        "LIMS_PATHOLOGY_EVALUATION",
        "DIAGNOSIS_CONCEPT_ID",
        OCE_TYPECODE_DIAGNOSIS_IND,
        "OTHER_DIAGNOSIS_NAME",
        "WHERE PE_ID = ?"));
    _oceDataMap.put(
      LIMS_PE_OTHER_TISSUE_FINDING,
      new createOceData(
        "LIMS_PATHOLOGY_EVALUATION",
        "TISSUE_FINDING_CONCEPT_ID",
        OCE_TYPECODE_TISSUE_IND,
        "OTHER_TISSUE_FINDING_NAME",
        "WHERE PE_ID = ?"));
    _oceDataMap.put(
      LIMS_PE_OTHER_TISSUE_ORIGIN,
      new createOceData(
        "LIMS_PATHOLOGY_EVALUATION",
        "TISSUE_ORIGIN_CONCEPT_ID",
        OCE_TYPECODE_TISSUE_IND,
        "OTHER_TISSUE_ORIGIN_NAME",
        "WHERE PE_ID = ?"));
    _oceDataMap.put(
      ILTDS_ASM_OTHER_ORGANSITE,
      new createOceData(
        "ILTDS_ASM",
        "ORGAN_SITE_CONCEPT_ID",
        OCE_TYPECODE_TISSUE_IND,
        "ORGAN_SITE_CONCEPT_ID_OTHER",
        "WHERE ASM_ID = ?"));
    _oceDataMap.put(
      ILTDS_ASMFORM_OTHER_SURGICAL_SPEC,
      new createOceData(
        "ILTDS_ASM_FORM",
        "SURGICAL_SPECIMEN_ID",
        OCE_TYPECODE_PROCEDURE_IND,
        "SURGICAL_SPECIMEN_ID_OTHER",
        SURGICAL_SPEC_WHERE_CLAUSE));
    _oceDataMap.put(
      ILTDS_INFORMED_CONSENT_OTHER_DX,
      new createOceData(
        "ILTDS_INFORMED_CONSENT",
        "DISEASE_CONCEPT_ID",
        OCE_TYPECODE_DIAGNOSIS_IND,
        "DISEASE_CONCEPT_ID_OTHER",
        "WHERE ARDAIS_ID = ? AND CONSENT_ID = ?"));

    _oceDataMap.put(
      PDC_PATH_REPORT_OTHER_PROC,
      new createOceData(
        "PDC_PATHOLOGY_REPORT",
        "PROCEDURE_CONCEPT_ID",
        OCE_TYPECODE_PROCEDURE_IND,
        "OTHER_PR_NAME",
        "WHERE PATH_REPORT_ID = ?"));

    _oceDataMap.put(
      PDC_PATHREPORT_DX_OTHER_DX,
      new createOceData(
        "PDC_PATH_REPORT_DX",
        "PATH_DX_CONCEPT_ID",
        OCE_TYPECODE_DIAGNOSIS_IND,
        "OTHER_PATH_DX",
        "WHERE FINDING_LINE_ID = ?"));

    _oceDataMap.put(
      PDC_PATHREPORT_DX_OTHER_TISSUE,
      new createOceData(
        "PDC_PATH_REPORT_DX",
        "PATH_TC_CONCEPT_ID",
        OCE_TYPECODE_TISSUE_IND,
        "OTHER_PATH_TISSUE",
        "WHERE FINDING_LINE_ID = ?"));

    _oceDataMap.put(
      PDC_PATH_SECTION_OTHER_DX,
      new createOceData(
        "PDC_PATH_REPORT_SECTION",
        "DIAGNOSIS_CONCEPT_ID",
        OCE_TYPECODE_DIAGNOSIS_IND,
        "OTHER_DX_NAME",
        "WHERE PATH_REPORT_SECTION_ID = ?"));
    _oceDataMap.put(
      PDC_PATH_SECTION_OTHER_TISSUE_ORIGIN,
      new createOceData(
        "PDC_PATH_REPORT_SECTION",
        "TISSUE_ORIGIN_CONCEPT_ID",
        OCE_TYPECODE_TISSUE_IND,
        "OTHER_ORIGIN_TC_NAME",
        "WHERE PATH_REPORT_SECTION_ID = ?"));
    _oceDataMap.put(
      PDC_PATH_SECTION_OTHER_TISSUE_FINDING,
      new createOceData(
        "PDC_PATH_REPORT_SECTION",
        "TISSUE_FINDING_CONCEPT_ID",
        OCE_TYPECODE_TISSUE_IND,
        "OTHER_FINDING_TC_NAME",
        "WHERE PATH_REPORT_SECTION_ID = ?"));

    _oceDataMap.put(
      PDC_PATH_SECTION_OTHER_DISTANT_METASTASIS,
      new createOceData(
        "PDC_PATH_REPORT_SECTION",
        "DISTANT_METASTASIS",
        OCE_TYPECODE_DISTANT_METASTASIS_IND,
        "OTHER_DISTANT_METASTASIS",
        "WHERE PATH_REPORT_SECTION_ID = ?"));
    _oceDataMap.put(
      PDC_PATH_SECTION_OTHER_HNG,
      new createOceData(
        "PDC_PATH_REPORT_SECTION",
        "HISTOLOGICAL_NUCLEAR_GRADE",
        OCE_TYPECODE_HNG_IND,
        "OTHER_HISTO_NUCLEAR_GRADE",
        "WHERE PATH_REPORT_SECTION_ID = ?"));
    _oceDataMap.put(
      PDC_PATH_SECTION_OTHER_HISTOLOGICAL_TYPE,
      new createOceData(
        "PDC_PATH_REPORT_SECTION",
        "HISTOLOGICAL_TYPE",
        OCE_TYPECODE_HISTOLOGICAL_TYPE_IND,
        "OTHER_HISTOLOGICAL_TYPE",
        "WHERE PATH_REPORT_SECTION_ID = ?"));
    _oceDataMap.put(
      PDC_PATH_SECTION_OTHER_LYMPH_NODE,
      new createOceData(
        "PDC_PATH_REPORT_SECTION",
        "LYMPH_NODE_STAGE",
        OCE_TYPECODE_LYMPH_NODE_IND,
        "OTHER_LYMPH_NODE_STAGE",
        "WHERE PATH_REPORT_SECTION_ID = ?"));
    _oceDataMap.put(
      PDC_PATH_SECTION_OTHER_STAGE_GROUPING,
      new createOceData(
        "PDC_PATH_REPORT_SECTION",
        "STAGE_GROUPING",
        OCE_TYPECODE_STAGE_GROUPING_IND,
        "OTHER_STAGE_GROUPING",
        "WHERE PATH_REPORT_SECTION_ID = ?"));
    _oceDataMap.put(
      PDC_PATH_SECTION_OTHER_TUMOR_STAGE_DESC,
      new createOceData(
        "PDC_PATH_REPORT_SECTION",
        "TUMOR_STAGE_DESC",
        OCE_TYPECODE_TUMOR_STAGE_DESC_IND,
        "OTHER_TUMOR_STAGE_DESC",
        "WHERE PATH_REPORT_SECTION_ID = ?"));
    _oceDataMap.put(
      PDC_PATH_SECTION_OTHER_TUMOR_STAGE_TYPE,
      new createOceData(
        "PDC_PATH_REPORT_SECTION",
        "TUMOR_STAGE_TYPE",
        OCE_TYPECODE_TUMOR_STAGE_TYPE_IND,
        "OTHER_TUMOR_STAGE_TYPE",
        "WHERE PATH_REPORT_SECTION_ID = ?"));

    //populate the list of attributes that have value choices determined by
    //the value of other attributes.  For example, the Histological Nuclear
    //Grade attribute on the path report section has it's values determined by
    //the disease specified on the case.  This list should include any attribute
    //in OceHelper.getDropdownList that calls the getOtherValueList method on
    //this class
    FILTERED_ATTRIBUTES.add(OCE_TYPECODE_DISTANT_METASTASIS_IND);
    FILTERED_ATTRIBUTES.add(OCE_TYPECODE_HNG_IND);
    FILTERED_ATTRIBUTES.add(OCE_TYPECODE_HISTOLOGICAL_TYPE_IND);
    FILTERED_ATTRIBUTES.add(OCE_TYPECODE_LYMPH_NODE_IND);
    FILTERED_ATTRIBUTES.add(OCE_TYPECODE_STAGE_GROUPING_IND);
    FILTERED_ATTRIBUTES.add(OCE_TYPECODE_TUMOR_STAGE_DESC_IND);
    FILTERED_ATTRIBUTES.add(OCE_TYPECODE_TUMOR_STAGE_TYPE_IND);

  }
  /**
   * Returns decoded value for <code>code</code>
   * @return String
   */
  public static String lookupOceConstant(String code) {
    return (String) oceMap.get(code);
  }
  /**
   * Creates new row for Other Code Editor.
   * @param key 
   * @param the list contains primary key values.
   * @param other text to be inserted.
   * @param the userId.
   */
  public static void createOce(String key, List primaryKeyList, String otherText, String userID) {
    //Validate the key. If key is not valid throw exception.
    Class clazz = OceUtil.class;
    try {
      clazz.getField(key);
    }
    catch (NoSuchFieldException e) {
      throw new RuntimeException(
        "OceUtil.createOce: Value for key "
          + key
          + " is not valid."
          + "Use static member variables of OceUtil as key");
    }
    createOceData oceData = (createOceData) getOceDataMap().get(key);
    //Validate primaryKeyList.
    if (ApiFunctions.isEmpty(primaryKeyList)) {
      throw new RuntimeException("OceUtil.createOce: Value for primaryKeyList must not be null ot empty.");
    }
    //check number of arguments supplied. If number of arguments are not equal
    //to arguments required throw exception.
    int argsSize = primaryKeyList.size();
    int argsRequired = StringUtils.countMatches(oceData.getWhereClause(), "?");
    if (argsSize != argsRequired) {
      throw new RuntimeException(
        "OceUtil.createOce: Not enough arguments for primaryKeyList."
          + "Refer documentation of OceUtil class.");
    }
    //Validate otherText
    if (ApiFunctions.isEmpty(otherText)) {
      throw new RuntimeException("OceUtil.createOce: Value for otherText must not be null ot empty.");
    }
    //validate userId
    if (ApiFunctions.isEmpty(userID)) {
      throw new RuntimeException("OceUtil.createOce: Value for userID must not be null ot empty.");
    }

    try {
      String tableName = oceData.getTableName();
      String columnName = oceData.getColumnName();
      String typeCode = oceData.getTypeCode();
      OceHome home = (OceHome) EjbHomes.getHome(OceHome.class);
      Oce oceBean = home.create();
      oceBean.createOce(
        tableName,
        columnName,
        typeCode,
        buildWhereClause(oceData.getWhereClause(), primaryKeyList),
        userID,
        otherText,
        oceData.getOtherColumnName());
    }
    catch (Exception e) {
      throw new ApiException("Exception at com.ardais.bigr.pdc.oce.util.OceUtil.createOce", e);
    }
  }

  /**
   * Updates status of specified row as Obsolete.
   * @param key 
   * @param the list contains primary key values.
   * @param the userId
   */
  public static void markStatusObsolete(String key, String primaryKey, String userID) {
    //Validate the key. If key is not valid throw exception.
    Class clazz = OceUtil.class;
    try {
      clazz.getField(key);
    }
    catch (NoSuchFieldException e) {
      throw new RuntimeException(
        "OceUtil.createOce: Value for key "
          + key
          + " is not valid."
          + "Use static member variables of OceUtil as key");
    }
    createOceData oceData = (createOceData) getOceDataMap().get(key);

    //validate userId
    if (ApiFunctions.isEmpty(userID)) {
      throw new RuntimeException("OceUtil.createOce: Value for userID must not be null ot empty.");
    }

    try {
      String tableName = oceData.getTableName();
      String columnName = oceData.getColumnName();
      String typeCode = oceData.getTypeCode();
      OceHome home = (OceHome) EjbHomes.getHome(OceHome.class);
      Oce oceBean = home.create();
      oceBean.updateStatus(
        tableName,
        columnName,
        typeCode,
        primaryKey,
        userID,
        OCE_STATUS_OBSOLETE_IND);
    }
    catch (Exception e) {
      throw new ApiException("Exception at com.ardais.bigr.pdc.oce.util.OceUtil.createOce", e);
    }

  }

  /**
   * 
   */
  public static String buildWhereClause(String whereClause, List args) {
    for (int i = 0; i < args.size(); i++) {
      whereClause = StringUtils.replaceOnce(whereClause, "?", "'" + (String) args.get(i) + "'");
    }
    return whereClause;
  }

  /**
     * Returns the <code>LegalValueSet</code> that contains the values
     * and display values for the specified category and disease. 
     * 
     * @param whereClause a where clause string that identifies a unique
     *         row in PDC_PATH_REPORT_SECTION table.
     * @param category the category
     * @return  The <code>LegalValueSet</code> of values, or <code>null</code>
     *          if the specified category has no associated <code>LegalValueSet</code>s. 
     */
  public static LegalValueSet getOtherValueList(String whereClause, String category) {
    LegalValueSet _values = null;
    //if an unrecognized category is passed in, throw an exception.  Different categories have
    //different mechanisms by which they get their values (i.e. some are determined by disease,
    //others not) so this ensures that all categories this method should handle are covered.
    if (!ArtsConstants.VALUE_SET_DDC_DISTANT_METASTASIS.equals(category) &&
        !ArtsConstants.VALUE_SET_HISTOLOGICAL_NUCLEAR_GRADE.equals(category) &&
        !ArtsConstants.VALUE_SET_HISTOLOGICAL_TYPE.equals(category) &&
        !ArtsConstants.VALUE_SET_DDC_LYMPH_NODE_STAGE_DESC.equals(category) &&
        !ArtsConstants.VALUE_SET_DDC_STAGE_GROUPING.equals(category) &&
        !ArtsConstants.VALUE_SET_DDC_TUMOR_STAGE_DESC.equals(category) &&
        !ArtsConstants.VALUE_SET_TUMOR_STAGE_TYPE.equals(category)) {
      throw new ApiException("Unrecognized category (" + category + ") encountered.");
    }
    
    //
    try {
      //Get disease type and account info.
      OceHome home = (OceHome) EjbHomes.getHome(OceHome.class);
      Oce oceBean = home.create();
      Map info = oceBean.retrieveCaseInfo(whereClause);
      //make sure all variables are populated.
      if ((info != null) && (info.size() == 5)) {
        //values for LYMPH_NODE_STAGE_DESC, TUMOR_STAGE_DESC
        //DISTANT_METASTASIS, and STAGE_GROUPINGS depend on the value of TUMOR_STAGE_TYPE. 
        //LegalValueSet allValues contains seperate list of values for
        //each type of TUMOR_STAGE_TYPE. Get the corresponding list for 
        //TUMOR_STAGE_DESC, LYMPH_NODE_STAGE_DESC, etc.
        if ((ArtsConstants.VALUE_SET_DDC_LYMPH_NODE_STAGE_DESC.equals(category)) || 
            (ArtsConstants.VALUE_SET_DDC_TUMOR_STAGE_DESC.equals(category)) ||
            (ArtsConstants.VALUE_SET_DDC_DISTANT_METASTASIS.equals(category)) ||
            (ArtsConstants.VALUE_SET_DDC_STAGE_GROUPING.equals(category))) {
          LegalValueSet allValues =
            BigrGbossData.getPdcStageHierarchyLookupList(category, (String) info.get("DISEASE"));
          String tumorStage = (String) info.get("TUMOR_STAGE_TYPE");
          if (tumorStage != null) {
            for (int i = 0; i < allValues.getCount(); i++) {
              if (tumorStage.equals(allValues.getValue(i))) {
                _values = allValues.getSubLegalValueSet(i);
                break;
              }
            }
          }
        }
        //If category is HNG and disease is Neoplasm of Prostate
        //MR 5846                
        else if (
          (ArtsConstants.VALUE_SET_HISTOLOGICAL_NUCLEAR_GRADE.equals(category))
            && (((String) info.get("DISEASE")).equals("126906006"))) {
          String gleason = (String) info.get("TOTAL_GLEASON_SCORE");
          int totalGleason = ApiFunctions.safeInteger(gleason, 0);

          //If Total Gleason Score = 2-4, then only CA00175C (G1) is valid.         
          if ((totalGleason >= 2) && (totalGleason <= 4)) {
            _values = new LegalValueSet();
            _values.addLegalValue("CA00175C", GbossFactory.getInstance().getDescription("CA00175C"));

          }
          //If Total Gleason Score = 5-6, then only CA00176C (G2) is valid.         
          else if ((totalGleason >= 5) && (totalGleason <= 6)) {
            _values = new LegalValueSet();
            _values.addLegalValue("CA00176C", GbossFactory.getInstance().getDescription("CA00176C"));

          }
          //If Total Gleason Score = 7-10, then only 369727006 (G3-4) is valid.         
          else if ((totalGleason >= 7) && (totalGleason <= 10)) {
            _values = new LegalValueSet();
            _values.addLegalValue("369727006", GbossFactory.getInstance().getDescription("369727006"));

          }
          else {
            GbossValueSet vs = BigrGbossData.getValueSetByDiagnosis(ArtsConstants.VALUE_SET_HISTOLOGICAL_NUCLEAR_GRADE, (String)info.get("DISEASE"));
            _values = BigrGbossData.getValueSetAsLegalValueSet(vs);
          }

        }
        //For all other  cases
        else {
          GbossValueSet vs = BigrGbossData.getValueSetByDiagnosis(category, (String)info.get("DISEASE"));
          _values = BigrGbossData.getValueSetAsLegalValueSet(vs);
        }
      }
    }
    catch (Exception ex) {
      throw new ApiException(ex);
    }
    return _values;
  }

  /**
   * Inner class
   */
  private static class createOceData {
    private String _tableName;
    private String _columnName;
    private String _typeCode;
    private String _otherColumnName;
    private String _whereClause;
    /**
     * Creates new createOceData.
     */
    public createOceData(
      String argTableName,
      String argColumnName,
      String argTypeCode,
      String argOtherColumnName,
      String argWhereClause) {
      if (argTableName == null)
        throw new IllegalArgumentException("OceUtil: Value for argTableName must not be null or empty.");
      if (argColumnName == null)
        throw new IllegalArgumentException("OceUtil: Value for argColumnName must not be null or empty.");
      if (argTypeCode == null)
        throw new IllegalArgumentException("OceUtil: Value for argTypeCode must not be null or empty.");
      if (argOtherColumnName == null)
        throw new IllegalArgumentException("OceUtil: Value for argOtherColumnName must not be null or empty.");
      if (argWhereClause == null)
        throw new IllegalArgumentException("OceUtil: Value for argwhereClause must not be null or empty.");

      _tableName = argTableName;
      _columnName = argColumnName;
      _typeCode = argTypeCode;
      _otherColumnName = argOtherColumnName;
      _whereClause = argWhereClause;

    }

    /**
     * Returns the columnName.
     * @return String
     */
    public String getColumnName() {
      return _columnName;
    }

    /**
     * Returns the otherColumnName.
     * @return String
     */
    public String getOtherColumnName() {
      return _otherColumnName;
    }

    /**
     * Returns the tableName.
     * @return String
     */
    public String getTableName() {
      return _tableName;
    }

    /**
     * Returns the typeCode.
     * @return String
     */
    public String getTypeCode() {
      return _typeCode;
    }

    /**
     * Returns the whereClause.
     * @return String
     */
    public String getWhereClause() {
      return _whereClause;
    }

  }

  /**
   * Returns the oceDataMap.
   * @return Map
   */
  public static Map getOceDataMap() {
    return _oceDataMap;
  }

}
