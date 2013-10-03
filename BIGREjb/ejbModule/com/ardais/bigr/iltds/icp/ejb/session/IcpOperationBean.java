package com.ardais.bigr.iltds.icp.ejb.session;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.SessionBean;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.databeans.AsmData;
import com.ardais.bigr.iltds.databeans.CaseData;
import com.ardais.bigr.iltds.databeans.DateData;
import com.ardais.bigr.iltds.databeans.DonorData;
import com.ardais.bigr.iltds.databeans.LogicalRepositoryExtendedData;
import com.ardais.bigr.iltds.databeans.SampleData;
import com.ardais.bigr.iltds.databeans.SampleStatusData;
import com.ardais.bigr.iltds.databeans.SlideData;
import com.ardais.bigr.iltds.databeans.TopLineData;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.orm.beans.OrmUserManagement;
import com.ardais.bigr.orm.beans.OrmUserManagementHome;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.LogicalRepositoryUtils;

/**
 * This is a Session Bean Class
 */
public class IcpOperationBean implements SessionBean {
  private javax.ejb.SessionContext mySessionCtx = null;
  private final static long serialVersionUID = 3206093459760846163L;

  public AsmData getAsmData(
    AsmData asmData,
    SecurityInfo securityInfo,
    boolean getSub,
    boolean getParents) {
    StringBuffer query = new StringBuffer();
    query.append("SELECT asm.ASM_ID                      AS ASM_ID,        \n");
    query.append("       asm.CONSENT_ID                  AS CONSENT_ID,    \n");
    query.append("       asm.ARDAIS_ID                   AS DONOR_ID,      \n");
    query.append("       asm.ORGAN_SITE_CONCEPT_ID       AS TISSUE_TYPE,   \n");
    query.append("       asm.ORGAN_SITE_CONCEPT_ID_OTHER AS OTHER_TISSUE,  \n");
    query.append("       asm.ASM_FORM_ID                 AS ASM_FORM_ID,   \n");
    query.append("       asm.SPECIMEN_TYPE               AS APPEARANCE,    \n");
    query.append("		 asm.PFIN_MEETS_SPECS			 AS PFIN_MEETS_SPECS, \n");
    query.append("		 asm.MODULE_WEIGHT				 AS WEIGHT,			\n");
    query.append("		 asm.MODULE_COMMENTS			 AS COMMENTS,		\n");
    query.append("       asm_form.GROSSING_DATE          AS GROSSING_DATE, \n");
    query.append("       asm_form.REMOVAL_DATE           AS REMOVAL_DATE,   \n");
    query.append("       asm_form.SURGICALTIME_ACCURACY_CID    AS ACCURACY \n");
    query.append("FROM   ILTDS_ASM      asm,                               \n");
    query.append("       ILTDS_ASM_FORM asm_form                           \n");
    query.append("WHERE  asm.ASM_ID = ?                                    \n");
    query.append("       AND asm.ASM_FORM_ID = asm_form.ASM_FORM_ID        \n");

    List results = runQuery(query.toString(), asmData.getAsm_id());

    if (results.size() > 0) {
      HashMap record = (HashMap) results.get(0);

      asmData.setAsm_id((String) record.get("ASM_ID"));
      asmData.setCase_id((String) record.get("CONSENT_ID"));
      asmData.setDonor_id((String) record.get("DONOR_ID"));
      asmData.setTissue_type((String) record.get("TISSUE_TYPE"));
      asmData.setOther_tissue((String) record.get("OTHER_TISSUE"));
      asmData.setAsm_form_id((String) record.get("ASM_FORM_ID"));
      asmData.setAppearance((String) record.get("APPEARANCE"));
      asmData.setTimeOfGrossing(new DateData((Timestamp) record.get("GROSSING_DATE")));
      asmData.setTimeOfRemoval(new DateData((Timestamp) record.get("REMOVAL_DATE")));
      asmData.setSurgical_accuracy((String) record.get("ACCURACY"));
      asmData.setModule_weight((String) record.get("WEIGHT"));
      asmData.setModule_comments((String) record.get("COMMENTS"));
      asmData.setPfin_meets_specs((String) record.get("PFIN_MEETS_SPECS"));

      if (getSub) {
        asmData.setFrozen_samples(
          getSampleData(
            asmData,
            securityInfo,
            getSub,
            false,
            ArtsConstants.SAMPLE_TYPE_FROZEN_TISSUE));
        asmData.setParaffin_samples(
          getSampleData(
            asmData,
            securityInfo,
            getSub,
            false,
            ArtsConstants.SAMPLE_TYPE_PARAFFIN_TISSUE));
      }
      if (getParents) {
        CaseData caseData = new CaseData();
        caseData.setCase_id(asmData.getCase_id());
        caseData = getCaseData(caseData, securityInfo, true);
        asmData.setParent(caseData);
      }
    }
    return asmData;
  }

  public BoxDto getBoxData(BoxDto boxData, SecurityInfo securityInfo, boolean getContents) {
    StringBuffer query = new StringBuffer();
    query.append("SELECT box.BOX_BARCODE_ID               BOX_BARCODE_ID,       \n");
    query.append("       box_location.LOCATION_ADDRESS_ID LOCATION,             \n");
    query.append("       box_location.ROOM_ID             ROOM,               \n");
    query.append("       box_location.DRAWER_ID           DRAWER,             \n");
    query.append("       box_location.SLOT_ID             SLOT,               \n");
    query.append("       box_location.UNIT_NAME           UNIT,                 \n");
    query.append("       box.MANIFEST_NUMBER              MANIFEST_NUMBER,      \n");
    query.append("       box.STORAGE_TYPE_CID             BOX_CONTAINER_TYPE,   \n");
    query.append("       box.BOX_CHECK_IN_DATE            BOX_CHECK_IN_DATE,    \n");
    query.append("       box.BOX_CHECK_OUT_DATE           BOX_CHECK_OUT_DATE,   \n");
    query.append("       box.BOX_CHECK_OUT_REASON         BOX_CHECK_OUT_REASON, \n");
    query.append("       box.BOX_LAYOUT_ID                BOX_LAYOUT_ID         \n");
    query.append("FROM   ILTDS_BOX_LOCATION  box_location,                      \n");
    query.append("       ILTDS_SAMPLE_BOX    box                                \n");
    query.append("WHERE  box.BOX_BARCODE_ID = ?                                 \n");
    query.append("       AND box.BOX_BARCODE_ID = box_location.BOX_BARCODE_ID(+)   \n");

    List results = runQuery(query.toString(), boxData.getBoxId());

    if (results.size() > 0) {
      boxData.setExists(true);
      HashMap record = (HashMap) results.get(0);

      boxData.setManifestId((String) record.get("MANIFEST_NUMBER"));
      boxData.setStorageTypeCid((String) record.get("BOX_CONTAINER_TYPE"));
      boxData.setCheckOutReason((String) record.get("BOX_CHECK_OUT_REASON"));
      boxData.setCheckInDate((new DateData((Timestamp) record.get("BOX_CHECK_IN_DATE"))));
      boxData.setCheckOutDate((new DateData((Timestamp) record.get("BOX_CHECK_OUT_DATE"))));
      boxData.setBoxId((String) record.get("BOX_BARCODE_ID"));
      boxData.setLocation((String) record.get("LOCATION"));
      boxData.setRoom((String) record.get("ROOM"));
      boxData.setDrawer((String) record.get("DRAWER"));
      boxData.setSlot((String) record.get("SLOT"));
      boxData.setUnitName((String) record.get("UNIT"));
      boxData.setBoxLayoutId((String) record.get("BOX_LAYOUT_ID"));
      
      if (!ApiFunctions.isEmpty(boxData.getBoxLayoutId())) {
        boxData.setBoxLayoutDto(BoxLayoutUtils.getBoxLayoutDto(boxData.getBoxLayoutId()));
      }

      if (getContents) {
        populateBoxContents(boxData, securityInfo);
      }
    }
    else {
      boxData.setExists(false);
    }

    return boxData;
  }

  private void populateBoxContents(BoxDto boxData, SecurityInfo securityInfo) {
    String query =
      "SELECT s.SAMPLE_BARCODE_ID, s.CUSTOMER_ID, s.CELL_REF_LOCATION, s.BMS_YN, s.ALLOCATION_IND "
        + "\nFROM ILTDS_SAMPLE s "
        + "\nWHERE s.BOX_BARCODE_ID = ? ";

    List results = runQuery(query, boxData.getBoxId());

    List samples = new ArrayList();
    Iterator iter = results.iterator();
    while (iter.hasNext()) {
      HashMap data = (HashMap) iter.next();
      SampleData sampleData = new SampleData();
      sampleData.setSample_id((String) data.get("SAMPLE_BARCODE_ID"));
      sampleData.setCustomer_id((String) data.get("CUSTOMER_ID"));
      sampleData.setCell_ref_location((String) data.get("CELL_REF_LOCATION"));
      sampleData.setBmsYN((String) data.get("BMS_YN"));
      sampleData.setRestricted(
        FormLogic.ALL_RESTRICTED_IND.equals((String) data.get("ALLOCATION_IND")));
      samples.add(sampleData);
    }

    boxData.setSamples(samples);
  }

  private BoxDto getBoxData(
    SampleData sampleData,
    SecurityInfo securityInfo,
    boolean getContents) {
    BoxDto boxData = new BoxDto();

    boxData.setBoxId(sampleData.getBox_barcode_id());

    return getBoxData(boxData, securityInfo, getContents);
  }

  public CaseData getCaseData(
    CaseData caseData,
    SecurityInfo securityInfo,
    boolean getParents) {

    StringBuffer query = new StringBuffer();
    query.append("SELECT case.CONSENT_ID                  AS CONSENT_ID,               \n");
    query.append("       case.CUSTOMER_ID                 AS CUSTOMER_ID,              \n");
    query.append("       case.CONSENT_DATETIME            AS CONSENT_DATETIME,         \n");
    query.append("       case.CONSENT_RELEASE_DATETIME    AS CONSENT_RELEASE_DATETIME, \n");
    query.append("       case.FORM_VERIFIED_DATETIME      AS FORM_VERIFIED_DATETIME,   \n");
    query.append("       case.CONSENT_LOCATION_ADDRESS_ID AS LOCATION,				   \n");
    query.append("       case.ARDAIS_ID                   AS ARDAIS_ID ,               \n");
    query.append("       case.DDC_CHECK_FLAG              AS DDC_CHECK_FLAG,           \n");
    query.append("       case.CONSENT_PULL_DATETIME       AS CONSENT_PULL_DATETIME,    \n");
    query.append("       case.LINKED                      AS LINKED,                   \n");
    query.append("       revoked.REVOKED_TIMESTAMP        AS REVOKED_TIMESTAMP,         \n");
    query.append("       case.DISEASE_CONCEPT_ID          AS ILTDS_DIAGNOSIS,         \n");
    query.append("       case.DISEASE_CONCEPT_ID_OTHER    AS ILTDS_DIAGNOSIS_OTHER,    \n");
    query.append("       staff.ARDAIS_ACCT_KEY            AS CONSENT_ACCOUNT          \n");
    query.append("FROM   ILTDS_INFORMED_CONSENT case,                                  \n");
    query.append("       ILTDS_REVOKED_CONSENT_ARCHIVE revoked,                        \n");
    query.append("       ILTDS_ARDAIS_STAFF staff                                      \n");
    query.append("WHERE      case.CONSENT_ID = ?                                       \n");
    query.append("       AND case.CONSENT_ID = revoked.CONSENT_ID(+)                   \n");
    query.append("       AND staff.ARDAIS_STAFF_ID = case.FORM_ENTRY_STAFF_ID          \n");

    List results = runQuery(query.toString(), caseData.getCase_id());

    if (results.size() > 0) {
      caseData.setExists(true);

      HashMap record = (HashMap) results.get(0);

      caseData.setCase_id((String) record.get("CONSENT_ID"));
      caseData.setCustomer_id((String) record.get("CUSTOMER_ID"));
      caseData.setDonor_id((String) record.get("ARDAIS_ID"));
      caseData.setConsentDate(new DateData((Timestamp) record.get("CONSENT_DATETIME")));
      caseData.setLinked((String) record.get("LINKED"));
      caseData.setVerified_date(new DateData((Timestamp) record.get("FORM_VERIFIED_DATETIME")));
      caseData.setRelease_date(new DateData((Timestamp) record.get("CONSENT_RELEASE_DATETIME")));
      caseData.setDonor_location((String) record.get("LOCATION"));
      caseData.setConsentAccount((String) record.get("CONSENT_ACCOUNT"));
      caseData.setDdc_check((String) record.get("DDC_CHECK_FLAG"));
      caseData.setPulled_date(new DateData((Timestamp) record.get("CONSENT_PULL_DATETIME")));
      caseData.setRevoked_date(new DateData((Timestamp) record.get("REVOKED_TIMESTAMP")));

      // stash the case diagnosis in case there is no DDC diagnosis...
      caseData.setDiagnosis((String) record.get("ILTDS_DIAGNOSIS"));
      // MR 7882 also need the other text...
      caseData.setDx_Other((String) record.get("ILTDS_DIAGNOSIS_OTHER"));

      if (getParents) {
        DonorData donorData = new DonorData();
        donorData.setDonor_id(caseData.getDonor_id());
        donorData = getDonorData(donorData, securityInfo, true);
        caseData.setParent(donorData);
      }
    }

    StringBuffer query2 = new StringBuffer();
    query2.append("SELECT report.DIAGNOSIS_CONCEPT_ID                   AS DIAGNOSIS,     \n");
    query2.append("       report.TISSUE_CONCEPT_ID                      AS TISSUE,        \n");
    query2.append("       report.PATH_REPORT_ID                         AS PATH_REPORT_ID,\n");
    query2.append("	      report.PROCEDURE_CONCEPT_ID                   AS PROC,          \n");
    query2.append("       report.OTHER_PR_NAME                          AS OTHER_PROC,    \n");
    query2.append("	      nvl2(report.PATHOLOGY_ASCII_REPORT, 'Y', 'N') AS ASCII_REPORT,  \n");
    query2.append("       primsect.OTHER_DX_NAME                        AS OTHER_DX,      \n");
    query2.append("       primsect.OTHER_ORIGIN_TC_NAME                 AS OTHER_TC_ORIGIN,   \n");
    query2.append("       primsect.OTHER_FINDING_TC_NAME                AS OTHER_TC_FINDING   \n");
    query2.append("FROM   PDC_PATHOLOGY_REPORT    report,                                 \n");
    query2.append("       PDC_PATH_REPORT_SECTION primsect                                \n");
    query2.append("WHERE  report.CONSENT_ID           = ?                             \n");
    query2.append(
      "       AND report.PRIMARY_PATH_REPORT_SECTION_ID=primsect.PATH_REPORT_SECTION_ID(+)   \n");

    results = runQuery(query2.toString(), caseData.getCase_id());

    if (results.size() > 0) {
      caseData.setExists(true);

      HashMap record = (HashMap) results.get(0);

      String diagnosis = (String) record.get("DIAGNOSIS");
      if (!ApiFunctions.isEmpty(diagnosis)) {
        caseData.setDiagnosis((String) record.get("DIAGNOSIS"));
        caseData.setDx_Other((String) record.get("OTHER_DX"));
      }
      caseData.setTissue((String) record.get("TISSUE"));
      caseData.setProcedure((String) record.get("PROC"));
      caseData.setProc_other((String) record.get("OTHER_PROC"));
      caseData.setAscii_report((String) record.get("ASCII_REPORT"));
      caseData.setPath_report_id((String) record.get("PATH_REPORT_ID"));
      caseData.setTc_Origin_Other((String) record.get("OTHER_TC_ORIGIN"));
      caseData.setTc_Finding_Other((String) record.get("OTHER_TC_FINDING"));

    }

    StringBuffer query3 = new StringBuffer();
    if (caseData.isLinked()) {
      // MR 6765 Retrieve Policy and IRB data for linked case
      query3.append(
        "SELECT policy.name                   AS POLICY_NAME,                       \n");
      query3.append(
        "       policy.id                     AS POLICY_ID,                         \n");
      query3.append(
        "       consent.consent_version_num   AS IRB_NAME                          \n");
      query3.append(
        "FROM   ILTDS_INFORMED_CONSENT    consent,                                  \n");
      query3.append("       ARD_POLICY                policy                                   \n");
      query3.append(
        "WHERE  consent.CONSENT_ID           = ?                                    \n");
      query3.append(
        "       AND consent.policy_id = policy.id                                   \n");
    }
    else {
      query3.append("SELECT policy.name                   AS POLICY_NAME,                      \n");
      query3.append(
        "       policy.id                     AS POLICY_ID                          \n");
      query3.append(
        "FROM   ILTDS_INFORMED_CONSENT    consent,                                  \n");
      query3.append(
        "       ARD_POLICY                policy                                    \n");
      query3.append(
        "WHERE  consent.CONSENT_ID           = ?                                    \n");
      query3.append(
        "       AND consent.policy_id = policy.id                                   \n");
    }

    results = runQuery(query3.toString(), caseData.getCase_id());

    if (results.size() > 0) {
      caseData.setExists(true);

      HashMap record = (HashMap) results.get(0);

      caseData.setPolicy_name((String) record.get("POLICY_NAME"));
      caseData.setPolicy_id((String) record.get("POLICY_ID"));

      if (caseData.isLinked()) {
        caseData.setProtocol_name((String) record.get("IRB_NAME"));
      }
      else {
        caseData.setProtocol_name(null);
      }

    }

    return caseData;
  }

  private DonorData getDonorData(
    DonorData donorData,
    SecurityInfo securityInfo,
    boolean getParents) {

    StringBuffer query = new StringBuffer();
    query.append("SELECT donor.ARDAIS_ID   AS ARDAIS_ID,   \n");
    query.append("       donor.CUSTOMER_ID AS CUSTOMER_ID, \n");
    query.append("       donor.GENDER      AS GENDER,      \n");
    query.append("       donor.RACE        AS RACE         \n");
    query.append("FROM   PDC_ARDAIS_DONOR donor            \n");
    query.append("WHERE  donor.ARDAIS_ID = ?               \n");

    List results = runQuery(query.toString(), donorData.getDonor_id());

    if (results.size() > 0) {
      HashMap record = (HashMap) results.get(0);

      donorData.setDonor_id((String) record.get("ARDAIS_ID"));
      donorData.setCustomer_id((String) record.get("CUSTOMER_ID"));
      donorData.setGender((String) record.get("GENDER"));
      donorData.setRace((String) record.get("RACE"));
    }
    donorData.setRepCount(getRepCount(donorData.getDonor_id()));
    return donorData;
  }

  private List getSampleData(
    AsmData asmData,
    SecurityInfo securityInfo,
    boolean getSub,
    boolean getParents,
    String sampleTypeCid) {

    StringBuffer query = new StringBuffer();
    query.append("SELECT sample.SAMPLE_BARCODE_ID   SAMPLE_BARCODE_ID,  \n");
    query.append("sample.SECTION_COUNT SECTION_COUNT  \n");
    query.append("FROM   ILTDS_SAMPLE sample                          \n");
    query.append("WHERE  sample.ASM_ID = ?                            \n");
    query.append("       AND sample.sample_type_cid = '" + sampleTypeCid + "'         \n");
    query.append("ORDER BY SAMPLE_BARCODE_ID asc                      \n");

    List results = runQuery(query.toString(), asmData.getAsm_id());

    ArrayList samples = new ArrayList();
    for (int i = 0; i < results.size(); i++) {
      HashMap data = (HashMap) results.get(i);
      SampleData sampleData = new SampleData();
      sampleData.setSample_id((String) data.get("SAMPLE_BARCODE_ID"));
      sampleData.setSecCount((String) data.get("SECTION_COUNT"));
      samples.add(getSampleData(sampleData, securityInfo, getSub, getParents));
    }
    return samples;
  }

  public SampleData getSampleData(
    SampleData sampleData,
    SecurityInfo securityInfo,
    boolean getSub,
    boolean getParents) {

    StringBuffer query = new StringBuffer();
    query.append("SELECT sample.SAMPLE_BARCODE_ID SAMPLE_BARCODE_ID, \n");
    query.append("       sample.PARENT_BARCODE_ID PARENT_BARCODE_ID, \n");
    query.append("       sample.BOX_BARCODE_ID BOX_BARCODE_ID, \n");
    query.append("       sample.ASM_ID ASM_ID, \n");
    query.append("       sample.ALLOCATION_IND ALLOCATION_IND, \n");
    query.append("       sample.ARDAIS_ACCT_KEY ARDAIS_ACCT_KEY, \n");
    query.append("       account.ARDAIS_ACCT_COMPANY_DESC ARDAIS_ACCT_COMPANY_DESC, \n");
    query.append("       sample.ASM_POSITION ASM_POSITION, \n");
    query.append(" 	     sample.ASM_NOTE ASM_NOTE, \n");
    query.append(" 	     sample.TYPE_OF_FIXATIVE TYPE_OF_FIXATIVE,	\n");
    query.append("       sample.QC_STATUS QC_STATUS, \n");
    query.append("       sample.INV_STATUS INV_STATUS, \n");
    query.append("       sample.PROJECT_STATUS PROJECT_STATUS, \n");
    query.append("       sample.CELL_REF_LOCATION CELL_REF_LOCATION,	\n");
    query.append("       sample.QC_VERIFIED QC_VERIFIED, \n");
    query.append("       sample.SALES_STATUS SALES_STATUS, \n");
    query.append("       cart.ARDAIS_USER_ID HOLD_USER, \n");
    query.append("       cart.ARDAIS_ACCT_KEY HOLD_ACCT, \n");
    query.append("       sample.PULL_YN PULL_YN, \n");
    query.append("       sample.PULL_DATE PULL_DATE, \n");
    query.append("       sample.RELEASED_YN RELEASED_YN, \n");
    query.append("		   sample.SAMPLE_SIZE_MEETS_SPECS SAMPLE_SIZE_MEETS_SPECS, \n");
    query.append("		   sample.FORMAT_DETAIL_CID FORMAT_DETAIL_CID, \n");
    query.append("       sample.DI_MIN_THICKNESS_PFIN_CID DI_MIN_THICKNESS_PFIN_CID, \n");
    query.append("    sample.DI_MAX_THICKNESS_PFIN_CID DI_MAX_THICKNESS_PFIN_CID, \n");
    query.append("    sample.DI_WIDTH_ACROSS_PFIN_CID DI_WIDTH_ACROSS_PFIN_CID, \n");
    query.append("    sample.BEST_MIN_THICKNESS_PFIN_CID BEST_MIN_THICKNESS_PFIN_CID, \n");
    query.append("    sample.BEST_MAX_THICKNESS_PFIN_CID BEST_MAX_THICKNESS_PFIN_CID, \n");
    query.append("    sample.BEST_WIDTH_ACROSS_PFIN_CID BEST_WIDTH_ACROSS_PFIN_CID, \n");
    query.append("    sample.HISTO_REEMBED_REASON_CID HISTO_REEMBED_REASON_CID, \n");
    query.append("    sample.OTHER_HISTO_REEMBED_REASON OTHER_HISTO_REEMBED_REASON, \n");
    query.append("    sample.HISTO_SUBDIVIDABLE HISTO_SUBDIVIDABLE, \n");
    query.append("    sample.HISTO_NOTES HISTO_NOTES, \n");
    query.append("    sample.BORN_DATE BORN_DATE, \n");
    query.append("    sample.SUBDIVISION_DATE SUBDIVISION_DATE, \n");
    query.append("		sample.TOTAL_HOURS_IN_FIXATIVE TOTAL_HOURS_IN_FIXATIVE, \n");
    query.append("       sample.SECTION_COUNT SECTION_COUNT, \n");
    query.append("       sample.PARAFFIN_FEATURE_CID PARAFFIN_FEATURE_CID, \n");
    query.append("       sample.OTHER_PARAFFIN_FEATURE OTHER_PARAFFIN_FEATURE, \n");
    query.append("       sample.BMS_YN BMS_YN, \n");
    query.append("       sample.LAST_KNOWN_LOCATION_ID LAST_KNOWN_LOCATION_ID, \n");
    query.append("       sample.RECEIPT_DATE RECEIPT_DATE, \n");
    query.append("       sample.CONSENT_ID CONSENT_ID, \n");
    query.append("       sample.ARDAIS_ID ARDAIS_ID, \n");
    query.append("       sample.CUSTOMER_ID CUSTOMER_ID, \n");
    query.append("       sample.SOURCE SOURCE \n");
    query.append("FROM   ILTDS_SAMPLE sample, \n");
    query.append("       ES_ARDAIS_ACCOUNT account, \n");
    query.append("       ES_SHOPPING_CART_DETAIL cart \n");
    query.append("WHERE  sample.SAMPLE_BARCODE_ID = ? \n");
    query.append("       AND sample.ARDAIS_ACCT_KEY = account.ARDAIS_ACCT_KEY(+) \n");
    query.append("       AND sample.SAMPLE_BARCODE_ID = cart.BARCODE_ID(+) \n");

    List results = runQuery(query.toString(), sampleData.getSample_id());

    if (results.size() > 0) {
      sampleData.setExists(true);
      HashMap record = (HashMap) results.get(0);

      sampleData.setSample_id((String) record.get("SAMPLE_BARCODE_ID"));
      sampleData.setCustomer_id((String) record.get("CUSTOMER_ID"));
      sampleData.setDonor_id((String) record.get("ARDAIS_ID"));
      sampleData.setCase_id((String) record.get("CONSENT_ID"));
      sampleData.setParentBarcodeId((String) record.get("PARENT_BARCODE_ID"));
      sampleData.setAsm_id((String) record.get("ASM_ID"));
      sampleData.setRestricted(FormLogic.ALL_RESTRICTED_IND.equals(record.get("ALLOCATION_IND")));
      sampleData.setAccountId((String) record.get("ARDAIS_ACCT_KEY"));
      sampleData.setAccount_name((String) record.get("ARDAIS_ACCT_COMPANY_DESC"));
      sampleData.setAsm_position((String) record.get("ASM_POSITION"));
      sampleData.setComment((String) record.get("ASM_NOTE"));
      sampleData.setType_fixative((String) record.get("TYPE_OF_FIXATIVE"));
      sampleData.setCell_ref_location((String) record.get("CELL_REF_LOCATION"));
      sampleData.setQcVerified("Y".equals(record.get("QC_VERIFIED")));
      sampleData.setBox_barcode_id((String) record.get("BOX_BARCODE_ID"));
      sampleData.setSales_status((String) record.get("SALES_STATUS"));
      sampleData.setQc_status((String) record.get("QC_STATUS"));
      sampleData.setInv_status((String) record.get("INV_STATUS"));
      sampleData.setHoldUserId((String) record.get("HOLD_USER"));
      sampleData.setHoldAccountId((String) record.get("HOLD_ACCT"));
      sampleData.setTopLine(getTopLineData(sampleData, true));
      sampleData.setProjectStatus((String) record.get("PROJECT_STATUS"));
      sampleData.setQcPulled("Y".equals(record.get("PULL_YN")));
      sampleData.setPullDate((Timestamp) record.get("PULL_DATE"));
      sampleData.setQcReleased("Y".equals(record.get("RELEASED_YN")));

      SampleStatusData sampleShipStatus = new SampleStatusData();
      sampleShipStatus.setSample_id(sampleData.getSample_id());
      // mr 6966 - using transfer status now
      sampleShipStatus = getSampleStatusData(sampleShipStatus, FormLogic.SMPL_TRANSFER, "desc");
      sampleData.setLast_transfer_status(sampleShipStatus);

      // mr 6967 -- using the receipt date from db, not based on status
      sampleData.setReceipt_date(new DateData((Timestamp) record.get("RECEIPT_DATE")));

      sampleData.setDiMaxThicknessCid((String) record.get("DI_MAX_THICKNESS_PFIN_CID"));
      sampleData.setDiMinThicknessCid((String) record.get("DI_MIN_THICKNESS_PFIN_CID"));
      sampleData.setDiWidthAcrossCid((String) record.get("DI_WIDTH_ACROSS_PFIN_CID"));
      sampleData.setFormatDetailCid((String) record.get("FORMAT_DETAIL_CID"));
      sampleData.setSampleSizeMeetsSpecs((String) record.get("SAMPLE_SIZE_MEETS_SPECS"));
      sampleData.setSecCount((String) record.get("SECTION_COUNT"));
      if (record.get("TOTAL_HOURS_IN_FIXATIVE") != null) {
        sampleData.setHoursInFixative(new Integer((String) record.get("TOTAL_HOURS_IN_FIXATIVE")));
      }
      sampleData.setBmsYN((String) record.get("BMS_YN"));
      sampleData.setLastKnownLocationId((String) record.get("LAST_KNOWN_LOCATION_ID"));

      sampleData.setBestMaxThicknessCid((String) record.get("BEST_MAX_THICKNESS_PFIN_CID"));
      sampleData.setBestMinThicknessCid((String) record.get("BEST_MIN_THICKNESS_PFIN_CID"));
      sampleData.setBestWidthAcrossCid((String) record.get("BEST_WIDTH_ACROSS_PFIN_CID"));

      sampleData.setHistoReembedReasonCid((String) record.get("HISTO_REEMBED_REASON_CID"));
      sampleData.setOtherHistoReembedReason((String) record.get("OTHER_HISTO_REEMBED_REASON"));
      sampleData.setHistoSubdivisible((String) record.get("HISTO_SUBDIVIDABLE"));
      sampleData.setHistoNotes((String) record.get("HISTO_NOTES"));
      sampleData.setBornDate((Timestamp) record.get("BORN_DATE"));
      sampleData.setSubdivisionDate((Timestamp) record.get("SUBDIVISION_DATE"));

      // MR4852
      sampleData.setParaffinFeatureCid((String) record.get("PARAFFIN_FEATURE_CID"));
      sampleData.setOtherParaffinFeature((String) record.get("OTHER_PARAFFIN_FEATURE"));
      
      //MR8350
      sampleData.setSource((String)record.get("SOURCE"));

      //populate logical repositories
      sampleData.setLogicalRepositories(
        LogicalRepositoryUtils.getLogicalRepositoriesForSample(
          sampleData.getSample_id(),
          securityInfo));
          
      //populate parent and child sample lists (MR8270)
      sampleData.setChildSamples(getChildSamples(sampleData));
      sampleData.setParentSamples(getParentSamples(sampleData));

      AsmData asmData = new AsmData();

      if (getParents && sampleData.getAsm_id() != null) {
        asmData.setAsm_id(sampleData.getAsm_id());
        asmData = getAsmData(asmData, securityInfo, false, true);
        sampleData.setParent(asmData);

      }

      if (getSub) {
        sampleData.setSlides(getSlideData(sampleData, getSub));
        sampleData.setSubdivisionChildIds(getSubdivisionChildIds(sampleData));
      }

      sampleData.setBox(getBoxData(sampleData, securityInfo, false));

      StringBuffer querySub = new StringBuffer();
      querySub.append("SELECT SAMPLE_ID,             \n");
      querySub.append("       VIABLETUMERCELLS,       \n");
      querySub.append("       VIABLENORMALCOMP,       \n");
      querySub.append("       CELLULARSTROMA,         \n");
      querySub.append("       ACELLULARSTROMA,        \n");
      querySub.append("       NECROSIS,               \n");
      querySub.append("       VIABLELESIONALCELLS,    \n");
      querySub.append("       COMMENTS,               \n");
      querySub.append("       INTERNAL_COMMENTS,      \n");
      querySub.append("       DX_OTHER,               \n");
      querySub.append("       TC_OTHER_ORIGIN,		\n");
      querySub.append("       TC_OTHER_FINDING,		\n");
      querySub.append("       TISSUE_ORIGIN_CONCEPT_ID, \n");
      querySub.append("       TISSUE_FINDING_CONCEPT_ID \n");
      querySub.append("FROM   LIMS_BIGR_LIBRARY_V     \n");
      querySub.append("WHERE  SAMPLE_ID = ? 	        \n");

      List resultsSub = runQuery(querySub.toString(), sampleData.getSample_id());

      if (resultsSub.size() > 0) {
        HashMap recordSub = (HashMap) resultsSub.get(0);
        sampleData.setAcellularstroma((String) recordSub.get("ACELLULARSTROMA"));
        sampleData.setCellularstroma((String) recordSub.get("CELLULARSTROMA"));
        sampleData.setNecrosis((String) recordSub.get("NECROSIS"));
        sampleData.setViablenormalcomp((String) recordSub.get("VIABLENORMALCOMP"));
        sampleData.setViabletumercells((String) recordSub.get("VIABLETUMERCELLS"));
        sampleData.setViablelesioncells((String) recordSub.get("VIABLELESIONALCELLS"));
        sampleData.setComments((String) recordSub.get("COMMENTS"));
        sampleData.setInternal_comments((String) recordSub.get("INTERNAL_COMMENTS"));
        sampleData.setDxOther((String) recordSub.get("DX_OTHER"));
        sampleData.setTcOtherFinding((String) recordSub.get("TC_OTHER_FINDING"));
        sampleData.setTcOtherOrigin((String) recordSub.get("TC_OTHER_ORIGIN"));
        sampleData.setTissueFinding((String) recordSub.get("TISSUE_FINDING_CONCEPT_ID"));
        sampleData.setTissueOrigin((String) recordSub.get("TISSUE_ORIGIN_CONCEPT_ID"));
      }
    }
    else {
      sampleData.setExists(false);
    }
    return sampleData;
  }

  /**
   * Return a list of the ids of the subdivision children of the specified sample, or an empty
   * list if there are none.  This assumes that the sample_id and subdivision_date of the 
   * sampleData object passed in are set to their correct values, and it assumes that if the
   * sample doesn't have a subdivsion date, it doesn't have any subdivision children.
   *  
   * @param sampleData The sample data.
   * @return The list of child sample ids.
   */
  private IdList getSubdivisionChildIds(SampleData sampleData) {
    IdList childIds = new IdList();

    if (sampleData.getSubdivisionDate() == null) {
      return childIds;
    }

    String query =
      "select sample_barcode_id from iltds_sample "
        + "\nwhere parent_barcode_id = ? "
        + "\norder by sample_barcode_id";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, sampleData.getSample_id());
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        childIds.add(rs.getString(1));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return childIds;
  }

  /**
   * Return a list of the child samples of the specified sample, or an empty
   * list if there are none.  Note that this method will find both children of "old-style"
   * subdivided samples as well as children of the new derivative operations
   *  
   * @param sampleData The sample data.
   * @return The list of child samples.
   */
  private List getChildSamples(SampleData sampleData) {
    List children = new ArrayList();
    List childIds = new ArrayList();

    //get the ids of the children
    String query =
      "select sample_barcode_id from iltds_sample "
        + "\nwhere parent_barcode_id = ? "
        + "\nunion "
        + "select child_sample_barcode_id as sample_barcode_id from iltds_sample_genealogy "
        + "where parent_sample_barcode_id = ? "
        + "\norder by sample_barcode_id";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, sampleData.getSample_id());
      pstmt.setString(2, sampleData.getSample_id());
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        childIds.add(rs.getString("sample_barcode_id"));
      }
      ApiFunctions.close(pstmt);
      ApiFunctions.close(rs);
      
      //now get the relevant details for the children
      if (!ApiFunctions.isEmpty(childIds)) {
        StringBuffer sql = new StringBuffer(100);
        sql.append("select * from iltds_sample sample where ");
        sql.append(ApiFunctions.makeBindConditionStringOrList("sample.sample_barcode_id", childIds.size()));
        pstmt = con.prepareStatement(sql.toString());
        ApiFunctions.bindBindParameterList(pstmt, 1, childIds);
        rs = ApiFunctions.queryDb(pstmt, con);
        while (rs.next()) {
          SampleData child = new SampleData();
          child.setSample_id(rs.getString("sample_barcode_id"));
          child.setCustomer_id(rs.getString("customer_id"));
          children.add(child);
        }
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return children;
  }

  /**
   * Return a list of the parent samples of the specified sample, or an empty
   * list if there are none.  Note that this method will find both parents of "old-style"
   * subdivided samples as well as parents of the new derivative operations
   *  
   * @param sampleData The sample data.
   * @return The list of parent samples.
   */
  private List getParentSamples(SampleData sampleData) {
    List parents = new ArrayList();
    List parentIds = new ArrayList();

    //get the ids of the parents
    String query =
      "select parent_barcode_id as sample_barcode_id from iltds_sample "
        + "\nwhere sample_barcode_id = ? and parent_barcode_id is not null"
        + "\nunion "
        + "select parent_sample_barcode_id as sample_barcode_id from iltds_sample_genealogy "
        + "where child_sample_barcode_id = ? "
        + "\norder by sample_barcode_id";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, sampleData.getSample_id());
      pstmt.setString(2, sampleData.getSample_id());
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        parentIds.add(rs.getString("sample_barcode_id"));
      }
      ApiFunctions.close(pstmt);
      ApiFunctions.close(rs);
      
      //now get the relevant details for the parents
      if (!ApiFunctions.isEmpty(parentIds)) {
        StringBuffer sql = new StringBuffer(100);
        sql.append("select * from iltds_sample sample where ");
        sql.append(ApiFunctions.makeBindConditionStringOrList("sample.sample_barcode_id", parentIds.size()));
        pstmt = con.prepareStatement(sql.toString());
        ApiFunctions.bindBindParameterList(pstmt, 1, parentIds);
        rs = ApiFunctions.queryDb(pstmt, con);
        while (rs.next()) {
          SampleData parent = new SampleData();
          parent.setSample_id(rs.getString("sample_barcode_id"));
          parent.setCustomer_id(rs.getString("customer_id"));
          parents.add(parent);
        }
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return parents;
  }

  private SampleStatusData getSampleStatusData(SampleStatusData statusData, String code, String orderBy) {

    StringBuffer query = new StringBuffer();
    query.append("SELECT status.SAMPLE_BARCODE_ID       SAMPLE_ID,  \n");
    query.append("       status.STATUS_TYPE_CODE        CODE,       \n");
    query.append("       status.SAMPLE_STATUS_DATETIME  DATETIME    \n");
    query.append("FROM   ILTDS_SAMPLE_STATUS   status               \n");
    query.append("WHERE  status.SAMPLE_BARCODE_ID = ?               \n");
    query.append("       AND status.STATUS_TYPE_CODE = '" + code + "'   \n");
    if (!ApiFunctions.isEmpty(orderBy)) {
      query.append("ORDER BY status.SAMPLE_STATUS_DATETIME ");
      query.append(orderBy);
    }

    List results = runQuery(query.toString(), statusData.getSample_id());

    if (results.size() > 0) {
      HashMap record = (HashMap) results.get(0);
      statusData.setSample_id((String) record.get("SAMPLE_ID"));
      statusData.setCode((String) record.get("CODE"));
      statusData.setDate(new DateData((Timestamp) record.get("DATETIME")));
    }
    return statusData;
  }

  private List getSlideData(
    com.ardais.bigr.iltds.databeans.SampleData sampleData,
    boolean getSub) {
    StringBuffer query = new StringBuffer();

    query.append("SELECT SLIDE_ID, CREATE_DATE \n");
    query.append("FROM LIMS_SLIDE \n");
    query.append("WHERE SAMPLE_BARCODE_ID = ? \n");
    query.append("ORDER BY CREATE_DATE DESC\n");

    List results = runQuery(query.toString(), sampleData.getSample_id());

    ArrayList slides = new ArrayList();
    for (int i = 0; i < results.size(); i++) {
      HashMap data = (HashMap) results.get(i);
      SlideData slideData = new SlideData();
      slideData.setSlide_id((String) data.get("SLIDE_ID"));
      slideData.setCreation_date(new DateData((Timestamp) data.get("CREATE_DATE")));
      slides.add(slideData);
    }
    return slides;
  }

  private int getRepCount(String ardaisId) {
    String query =
      "SELECT COUNT(CONSENT_ID) REPCOUNT "
        + "\nFROM ILTDS_INFORMED_CONSENT "
        + "\nWHERE ARDAIS_ID = ?";

    List results = runQuery(query, ardaisId);

    HashMap data = (HashMap) results.get(0);

    String countStr = (String) data.get("REPCOUNT");

    return Integer.parseInt(countStr);
  }

  private TopLineData getTopLineData(SampleData sampleData, boolean getSub) {
    TopLineData topLineData = new TopLineData();
    StringBuffer query = new StringBuffer();

    query.append("SELECT sample.TISSUE_ORIGIN_CUI    AS TISSUE_ORIGIN_CODE,  \n");
    query.append("       sample.TISSUE_FINDING_CUI   AS TISSUE_FINDING_CODE, \n");
    query.append("       sample.DIAGNOSIS_CUI_BEST   AS DISEASE_CODE,   	   \n");
    query.append("       sample.APPEARANCE_BEST      AS SPECIMEN_CODE,       \n");
    query.append("       sample.SAMPLE_TYPE_CID      AS SAMPLE_TYPE_CID      \n");
    query.append("FROM   ILTDS_SAMPLE            sample                  \n");
    query.append("WHERE  sample.SAMPLE_BARCODE_ID = ?      \n");

    List results = runQuery(query.toString(), sampleData.getSample_id());

    if (results.size() > 0) {
      HashMap record = (HashMap) results.get(0);

      topLineData.setAppearance_code((String) record.get("SPECIMEN_CODE"));
      topLineData.setSampleTypeCid((String) record.get("SAMPLE_TYPE_CID"));
      topLineData.setDiagnosis_code((String) record.get("DISEASE_CODE"));
      topLineData.setTissue_code((String) record.get("TISSUE_ORIGIN_CODE"));
      topLineData.setTissueFinding_code((String) record.get("TISSUE_FINDING_CODE"));
    }

    return topLineData;
  }

  private List runQuery(String query, String param) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List results = new ArrayList();

    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, param);
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        ResultSetMetaData metadata = rs.getMetaData();
        HashMap data = new HashMap();

        int columns = metadata.getColumnCount();

        for (int i = 1; i <= columns; i++) {
          String columnName = metadata.getColumnName(i);
          if (metadata.getColumnClassName(i).equals("java.sql.Timestamp")) {
            data.put(columnName, rs.getTimestamp(columnName));
          }
          else {
            data.put(columnName, rs.getString(columnName));
          }
        }
        results.add(data);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return results;
  }

  public LogicalRepositoryExtendedData getLogicalRepositoryData(
    String repositoryId,
    SecurityInfo securityInfo) {

    LogicalRepositoryExtendedData result =
      (LogicalRepositoryExtendedData) IltdsUtils.getLogicalRepositoryData(
        repositoryId,
        false,
        LogicalRepositoryExtendedData.class);

    if (result == null) {
      result = new LogicalRepositoryExtendedData();
      result.setId(repositoryId);
      result.setExists(false);
      result.setUsersWithExplicitAccess(new ArrayList());
      result.setUsersWithImplicitAccess(new ArrayList());
    }
    else {
      result.setExists(true);
      result.setItemCount(getLogicalRepositoryItemCount(repositoryId));
      result.setUsersWithExplicitAccess(getLogicalRepositoryExplicitUsers(repositoryId));
      result.setUsersWithImplicitAccess(getLogicalRepositoryImplicitUsers());
    }

    return result;
  }

  private int getLogicalRepositoryItemCount(String repositoryId) {
    int result = 0;

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      String queryString =
        "select count(distinct r.item_id) from ard_logical_repos_item r where r.repository_id = ?";

      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setBigDecimal(1, new BigDecimal(repositoryId));
      rs = ApiFunctions.queryDb(pstmt, con);

      rs.next();
      result = rs.getInt(1);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return result;
  }

  private List getLogicalRepositoryExplicitUsers(String repositoryId) {
    List result = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      StringBuffer sb = new StringBuffer();
      sb.append(
        "select eau.ardais_user_id, eau.user_firstname, eau.user_lastname, eau.ardais_acct_key ");
      sb.append("from es_ardais_user eau, ard_logical_repos_user lruser ");
      sb.append("where lruser.ardais_user_id = eau.ardais_user_id and ");
      sb.append("lruser.repository_id = ? ");
      sb.append(
        "order by upper(eau.user_lastname), upper(eau.user_firstname), upper(eau.ardais_acct_key)");
      String queryString = sb.toString();
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setBigDecimal(1, new BigDecimal(repositoryId));
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        UserDto user = new UserDto();
        user.setUserId(rs.getString("ardais_user_id"));
        user.setAccountId(rs.getString("ardais_acct_key"));
        user.setFirstName(rs.getString("user_firstname"));
        user.setLastName(rs.getString("user_lastname"));
        result.add(user);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return result;
  }

  private List getLogicalRepositoryImplicitUsers() {
    List result = null;
    //just return a list of users who have the "View All Logical Repository" privilege
    try {
      OrmUserManagementHome home = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
      OrmUserManagement bean = home.create();
      result = bean.findUsersWithPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    return result;
  }

  public void ejbActivate() throws java.rmi.RemoteException {
  }
  public void ejbCreate() throws javax.ejb.CreateException, javax.ejb.EJBException {
  }
  public void ejbPassivate() throws java.rmi.RemoteException {
  }
  public void ejbRemove() throws java.rmi.RemoteException {
  }
  public javax.ejb.SessionContext getSessionContext() {
    return mySessionCtx;
  }
  public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException {
    mySessionCtx = ctx;
  }
}