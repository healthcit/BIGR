package com.ardais.bigr.pdc.beans;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.TemporaryClob;
import com.ardais.bigr.iltds.assistants.LegalValue;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.performers.BtxPerformerPlaceHolder;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.btx.BTXDetailsCreatePathReport;
import com.ardais.bigr.pdc.btx.BTXDetailsCreatePathReportDiagnostic;
import com.ardais.bigr.pdc.btx.BTXDetailsCreatePathReportSection;
import com.ardais.bigr.pdc.btx.BTXDetailsCreatePathReportSectionFinding;
import com.ardais.bigr.pdc.btx.BTXDetailsCreateRawPathReport;
import com.ardais.bigr.pdc.btx.BTXDetailsPathReport;
import com.ardais.bigr.pdc.btx.BTXDetailsUpdatePathReport;
import com.ardais.bigr.pdc.btx.BTXDetailsUpdatePathReportDiagnostic;
import com.ardais.bigr.pdc.btx.BTXDetailsUpdatePathReportSection;
import com.ardais.bigr.pdc.btx.BTXDetailsUpdatePathReportSectionFinding;
import com.ardais.bigr.pdc.btx.BTXDetailsUpdateRawPathReport;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData;
import com.ardais.bigr.pdc.oce.util.OceUtil;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * This is a Session Bean Class
 */
public class DDCPathologyBean implements SessionBean {
	private javax.ejb.SessionContext mySessionCtx = null;
	private final static long serialVersionUID = 3206093459760846163L;

/**
 * Creates a new pathology report and updates all fields except its raw pathology report.
 * All pathology report fields are obtained from the input <code>PathReportData</code>.
 * A <code>PathReportData</code> bean containing the data of the new pathology report is returned.
 *
 * @param  pathReport  a <code>PathReportData</code> bean containing path report fields
 * @return  A <code>PathReportData</code> bean containing the new pathology report.
 * @see  #createRawPathReport(com.ardais.bigr.pdc.javabeans.PathReportData)
 */
public PathReportData buildPathReport(PathReportData pathReport) {
    try {
        PathReportData newPathReport = getPathReport(pathReport);
        if (newPathReport.getPathReportId() != null) {
            pathReport = newPathReport;
        }

    } catch (Exception e) {
        ApiLogger.log("Error retreiving path report: ", e);
    }
    try {
        pathReport.setSections(getPathReportSections(pathReport));
    } catch (Exception e) {
        ApiLogger.log("Error retreiving path report treatments: ", e);
    }
    try {
        pathReport.setDiagnostics(getPathReportDiagnostics(pathReport));
    } catch (Exception e) {
        ApiLogger.log("Error retreiving path report diagnostics: ", e);
    }

    try {
        for (int i = 0; i < pathReport.getSections().size(); i++) {
            PathReportSectionData pathSection = (PathReportSectionData) pathReport.getSections().get(i);
            pathSection.setFindings(getPathReportSectionFindings(pathSection));
        }
    } catch (Exception e) {
        ApiLogger.log("Error retreiving path report diagnostics: ", e);
    }

    return pathReport;
}
/**
 * Creates a new pathology report and updates all fields except its raw pathology report.
 * All pathology report fields are obtained from the input <code>PathReportData</code>.
 * A <code>PathReportData</code> bean containing the data of the new pathology report is returned.
 *
 * @param  pathReport  a <code>PathReportData</code> bean containing path report fields
 * @return  A <code>PathReportData</code> bean containing the new pathology report.
 * @see  #createRawPathReport(com.ardais.bigr.pdc.javabeans.PathReportData)
 */
public PathReportData createPathReport(PathReportData pathReport, SecurityInfo securityInfo) {

	Connection con = null;
	BufferedWriter w = null;
	String id = null;

	StringBuffer insertSql = new StringBuffer(256);
	insertSql.append("{ CALL INSERT INTO pdc_pathology_report");
	insertSql.append(" (path_report_id,");
	insertSql.append(" ardais_id,");
	insertSql.append(" consent_id,");
	insertSql.append(" disease_concept_id,");
	insertSql.append(" procedure_concept_id,");
	insertSql.append(" other_pr_name,");
	insertSql.append(" path_check_date_mm,");
	insertSql.append(" path_check_date_yyyy,");
	insertSql.append(" create_user, create_date, ");
	insertSql.append(" additional_note)");
	insertSql.append(" VALUES (pdc_path_report_id_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, empty_clob())");
	insertSql.append(" RETURNING path_report_id INTO ? }");

	StringBuffer clobSql = new StringBuffer(128);
    clobSql.append("SELECT additional_note");
    clobSql.append(" FROM pdc_pathology_report");
    clobSql.append(" WHERE path_report_id = ?");
    clobSql.append(" FOR UPDATE");
			
	con = ApiFunctions.getDbConnection();
  boolean success = false;
	try {
		// Insert the new path report, saving the returned path
		// report id in the data bean.
		CallableStatement cstmt = con.prepareCall(insertSql.toString());
		java.sql.Timestamp time = new java.sql.Timestamp((new java.util.Date()).getTime());
		cstmt.setString(1, pathReport.getArdaisId());
		cstmt.setString(2, pathReport.getConsentId());
		cstmt.setString(3, pathReport.getDisease());
		cstmt.setString(4, pathReport.getProcedure());
		cstmt.setString(5, pathReport.getProcedureOther());
		cstmt.setString(6, pathReport.getPathReportMonth());
		cstmt.setString(7, pathReport.getPathReportYear());
		cstmt.setString(8, pathReport.getCreateUser());
		cstmt.setTimestamp(9, time);
		cstmt.registerOutParameter(10, Types.VARCHAR);
		cstmt.execute();
		id = cstmt.getString(10);
		pathReport.setPathReportId(id);

		// Execute the select query to lock the row just inserted so the
		// CLOB field can be updated. Use the returned CLOB locator to
		// update the additional note CLOB.
		String additionalNote = pathReport.getAdditionalNote();
		if (!ApiFunctions.isEmpty(additionalNote)) {
	        PreparedStatement pstmt = con.prepareStatement(clobSql.toString());
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getObject("additional_note");
				w = new BufferedWriter(clob.getCharacterOutputStream());
				w.write(additionalNote);
				w.flush();
				w.close();
			}
		}
    success = true;
		
		// Update OCE if an "other" procedure was entered.
	    String otherProcedure = pathReport.getProcedureOther();
		if (!ApiFunctions.isEmpty(otherProcedure)) {
			
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATH_REPORT_OTHER_PROC,
				list,
				otherProcedure,
				pathReport.getCreateUser());
		}
	}
	catch (Exception e) {
		throw new ApiException(e);
	}
    finally {
	    try {
			if (w != null) w.close();
			if (con != null) con.close();
	    }
		catch (IOException e) {
			throw new ApiException(e);
		}
		catch (SQLException e) {
			throw new ApiException(e);
		}
    }
  //if the path report was successfully created, log that fact in ICP
  if (success) {
    BTXDetailsCreatePathReport btxDetails = new BTXDetailsCreatePathReport();
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btxDetails.setTransactionType("pdc_placeholder");
    btxDetails.setPathReportData(pathReport);
    BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
    btx.perform(btxDetails);
  }
	return pathReport;
}
public PathReportDiagnosticData createPathReportDiagnostic(PathReportDiagnosticData pathReportDiagnostic, SecurityInfo securityInfo) {
	StringBuffer sql = new StringBuffer(256);
	sql.append("INSERT INTO pdc_path_report_diagnostics");
	sql.append(" (diagnostics_concept_id,");
	sql.append(" path_report_id,");
	sql.append(" diagnostic_type,");
	sql.append(" diagnostic_results_cid,");
	sql.append(" diagnostic_note)");
	sql.append(" VALUES (?, ?, ?, ?, ?)");
			
	Connection con = ApiFunctions.getDbConnection();
  boolean success = false;
	try {
		// Insert the new path report diagnostic.
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, pathReportDiagnostic.getDiagnostic());
		pstmt.setString(2, pathReportDiagnostic.getPathReportId());
		pstmt.setString(3, pathReportDiagnostic.getType());
		pstmt.setString(4, pathReportDiagnostic.getResult());
		pstmt.setString(5, pathReportDiagnostic.getNote());
		pstmt.executeUpdate();
    success = true;
	}
	catch (Exception e) {
		throw new ApiException(e);
	}
    finally {
	    ApiFunctions.closeDbConnection(con);
    }
  //if the path report diagnostic was successfully created, log that fact in ICP
  if (success) {
    BTXDetailsCreatePathReportDiagnostic btxDetails = new BTXDetailsCreatePathReportDiagnostic();
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btxDetails.setTransactionType("pdc_placeholder");
    btxDetails.setPathReportDiagnosticData(pathReportDiagnostic);
    BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
    btx.perform(btxDetails);
  }
	return pathReportDiagnostic;
}
public PathReportSectionData createPathReportSection(PathReportSectionData pathReportSection, SecurityInfo securityInfo) { 
	StringBuffer sql = new StringBuffer(256);
	sql.append("{ CALL INSERT INTO pdc_path_report_section");
	sql.append(" (path_report_section_id,");
	sql.append(" path_report_id,");
	sql.append(" consent_id,");
	sql.append(" primary_ind,");
	sql.append(" section_identifier,");
	sql.append(" create_date,");
	sql.append(" create_user,");
	sql.append(" total_nodes_positive,");
	sql.append(" total_nodes_examined,");
	sql.append(" total_gleason_score,");
	sql.append(" primary_gleason_score,");
	sql.append(" secondary_gleason_score,");
	sql.append(" extranodal_spread,");
	sql.append(" perineural_invasion_ind,");
	sql.append(" diagnosis_concept_id,");
	sql.append(" tissue_origin_concept_id,");
	sql.append(" tissue_finding_concept_id,");
	sql.append(" lymph_node_stage,");
	sql.append(" histological_nuclear_grade,");
	sql.append(" venous_vessel_invasion,");
	sql.append(" lymphatic_vascular_invasion,");
	sql.append(" extensive_intraductal_comp,");
	sql.append(" inflamm_cell_infiltrate,");
	sql.append(" cell_infiltrate_amt,");
	sql.append(" size_largest_nodal_mets,");
	sql.append(" joint_component,");
	sql.append(" tumor_configuration,");
	sql.append(" tumor_size,");
	sql.append(" tumor_weight,");
	sql.append(" tumor_stage_desc,");
	sql.append(" tumor_stage_type,");
	sql.append(" margins_involved_desc,");
	sql.append(" margins_uninvolved_desc,");
	sql.append(" histological_type,");
	sql.append(" other_dx_name,");
	sql.append(" other_origin_tc_name,");
	sql.append(" other_finding_tc_name,");
	sql.append(" distant_metastasis,");
	sql.append(" distant_metastasis_ind,");
	sql.append(" lymph_node_stage_ind,");
	sql.append(" tumor_stage_desc_ind,");
	sql.append(" other_histological_type,");
	sql.append(" other_histo_nuclear_grade,");
	sql.append(" other_tumor_stage_type,");
	sql.append(" stage_grouping,");
	sql.append(" other_stage_grouping,");
	sql.append(" other_lymph_node_stage,");
	sql.append(" other_tumor_stage_desc,");
	sql.append(" other_distant_metastasis,");
	sql.append(" lymph_node_notes,");
	sql.append(" path_section_notes)");
	sql.append(" VALUES (pdc_path_report_section_id_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	sql.append(" RETURNING path_report_section_id INTO ? }");
			
	Connection con = ApiFunctions.getDbConnection();
	PreparedStatement pstmt = null;
	CallableStatement cstmt = null;
	TemporaryClob clob = null;
  boolean success = false;
	try {
		// If this section is the primary, then make the existing primary a secondary.
		String primary = pathReportSection.getPrimary();
		if ((primary != null) && (primary.equals("Y"))) {
			StringBuffer primarySql = new StringBuffer(128);
			primarySql.append("UPDATE pdc_path_report_section");
			primarySql.append(" SET primary_ind = 'N'");
			primarySql.append(" WHERE path_report_id = ?");
			primarySql.append(" AND primary_ind = 'Y'");
			pstmt = con.prepareStatement(primarySql.toString());
			pstmt.setString(1, pathReportSection.getPathReportId());
			pstmt.executeUpdate();
			pstmt.close();
		}

		// Insert the new path report section.
		cstmt = con.prepareCall(sql.toString());
		java.sql.Timestamp time = new java.sql.Timestamp((new java.util.Date()).getTime());
		cstmt.setString(1, pathReportSection.getPathReportId());
		cstmt.setString(2, pathReportSection.getConsentId());
		cstmt.setString(3, primary);
		cstmt.setString(4, pathReportSection.getSectionIdentifier());
		cstmt.setTimestamp(5, time);
		cstmt.setString(6, pathReportSection.getCreateUser());
		cstmt.setString(7, pathReportSection.getTotalNodesPositive());
		cstmt.setString(8, pathReportSection.getTotalNodesExamined());
		Integer score = pathReportSection.getGleasonTotal();
		if (score == null)
			cstmt.setNull(9, Types.NUMERIC);
		else
			cstmt.setInt(9, score.intValue());
		score = pathReportSection.getGleasonPrimary();
		if (score == null)
			cstmt.setNull(10, Types.NUMERIC);
		else
			cstmt.setInt(10, score.intValue());
		score = pathReportSection.getGleasonSecondary();
		if (score == null)
			cstmt.setNull(11, Types.NUMERIC);
		else
			cstmt.setInt(11, score.intValue());
		cstmt.setString(12, pathReportSection.getExtranodalSpread());
		cstmt.setString(13, pathReportSection.getPerineuralInvasion());
		cstmt.setString(14, pathReportSection.getDiagnosis());
		cstmt.setString(15, pathReportSection.getTissueOrigin());
		cstmt.setString(16, pathReportSection.getTissueFinding());
		cstmt.setString(17, pathReportSection.getLymphNodeStage());
		cstmt.setString(18, pathReportSection.getHistologicalNuclearGrade());
		cstmt.setString(19, pathReportSection.getVenousVesselInvasion());
		cstmt.setString(20, pathReportSection.getLymphaticVascularInvasion());
		cstmt.setString(21, pathReportSection.getExtensiveIntraductalComp());
		cstmt.setString(22, pathReportSection.getInflammCellInfiltrate());
		cstmt.setString(23, pathReportSection.getCellInfiltrateAmount());
		cstmt.setString(24, pathReportSection.getSizeLargestNodalMets());
		cstmt.setString(25, pathReportSection.getJointComponent());
		cstmt.setString(26, pathReportSection.getTumorConfig());
		cstmt.setString(27, pathReportSection.getTumorSize());
		cstmt.setString(28, pathReportSection.getTumorWeight());
		cstmt.setString(29, pathReportSection.getTumorStageDesc());
		cstmt.setString(30, pathReportSection.getTumorStageType());
		cstmt.setString(31, pathReportSection.getMarginsInvolved());
		cstmt.setString(32, pathReportSection.getMarginsUninvolved());
		cstmt.setString(33, pathReportSection.getHistologicalType());
		cstmt.setString(34, pathReportSection.getDiagnosisOther());
		cstmt.setString(35, pathReportSection.getTissueOriginOther());
		cstmt.setString(36, pathReportSection.getTissueFindingOther());
		cstmt.setString(37, pathReportSection.getDistantMetastasis());
		cstmt.setString(38, pathReportSection.getDistantMetastasisInd());
		cstmt.setString(39, pathReportSection.getLymphNodeStageInd());
		cstmt.setString(40, pathReportSection.getTumorStageDescInd());
		cstmt.setString(41, pathReportSection.getHistologicalTypeOther());
		cstmt.setString(42, pathReportSection.getHistologicalNuclearGradeOther());
		cstmt.setString(43, pathReportSection.getTumorStageTypeOther());
		cstmt.setString(44, pathReportSection.getStageGrouping());
		cstmt.setString(45, pathReportSection.getStageGroupingOther());
		cstmt.setString(46, pathReportSection.getLymphNodeStageOther());
		cstmt.setString(47, pathReportSection.getTumorStageDescOther());
		cstmt.setString(48, pathReportSection.getDistantMetastasisOther());
		cstmt.setString(49, pathReportSection.getLymphNodeNotes());
		clob = new TemporaryClob(con, pathReportSection.getSectionNotes());
		cstmt.setClob(50, clob.getSQLClob());
		cstmt.registerOutParameter(51, Types.VARCHAR);
		cstmt.execute();
		String id = cstmt.getString(51);
		pathReportSection.setPathReportSectionId(id);
    success = true;

		// Update OCE if an "other" diagnosis was entered.
	    String otherDiagnosis = pathReportSection.getDiagnosisOther();
		if (!ApiFunctions.isEmpty(otherDiagnosis)) {
			
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATH_SECTION_OTHER_DX,
				list,
				otherDiagnosis,
				pathReportSection.getCreateUser());
		}
		
		// Update OCE if an "other" finding tissue was entered.
	    String otherTissueFinding = pathReportSection.getTissueFindingOther();
		if (!ApiFunctions.isEmpty(otherTissueFinding)) {			
			
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATH_SECTION_OTHER_TISSUE_FINDING,
				list,
				otherTissueFinding,
				pathReportSection.getCreateUser());	
		}

		// Update OCE if an "other" origin tissue was entered.
	    String otherTissueOrigin = pathReportSection.getTissueOriginOther();
		if (!ApiFunctions.isEmpty(otherTissueOrigin)) {
			
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATH_SECTION_OTHER_TISSUE_ORIGIN,
				list,
				otherTissueOrigin,
				pathReportSection.getCreateUser());	
		}
		// Insert into OCE if "other" distant metastasis entered.
		String otherDistantMetastasis = pathReportSection.getDistantMetastasisOther();
		if (!ApiFunctions.isEmpty(otherDistantMetastasis)) {
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATH_SECTION_OTHER_DISTANT_METASTASIS,
				list,
				otherDistantMetastasis,
				pathReportSection.getCreateUser());	
		}	
		// Insert into OCE if "other" histological nuclear grade entered.
		String otherHng = pathReportSection.getHistologicalNuclearGradeOther();
		if (!ApiFunctions.isEmpty(otherHng)) {
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATH_SECTION_OTHER_HNG,
				list,
				otherHng,
				pathReportSection.getCreateUser());	
		}	
		// Insert into OCE if "other" histological type entered.
		String otherHistologicalType = pathReportSection.getHistologicalTypeOther();
		if (!ApiFunctions.isEmpty(otherHistologicalType)) {
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATH_SECTION_OTHER_HISTOLOGICAL_TYPE,
				list,
				otherHistologicalType,
				pathReportSection.getCreateUser());	
		}	
		// Insert into OCE if "other" lymph node stage desc entered.
		String otherLymphNode = pathReportSection.getLymphNodeStageOther();
		if (!ApiFunctions.isEmpty(otherLymphNode)) {
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATH_SECTION_OTHER_LYMPH_NODE,
				list,
				otherLymphNode,
				pathReportSection.getCreateUser());	
		}	
		// Insert into OCE if "other" stage groupings entered.
		String otherStageGroupings = pathReportSection.getStageGroupingOther();
		if (!ApiFunctions.isEmpty(otherStageGroupings)) {
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATH_SECTION_OTHER_STAGE_GROUPING,
				list,
				otherStageGroupings,
				pathReportSection.getCreateUser());	
		}	
		// Insert into OCE if "other" tumor stage desc entered.
		String otherTumorStageDesc = pathReportSection.getTumorStageDescOther();
		if (!ApiFunctions.isEmpty(otherTumorStageDesc)) {
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATH_SECTION_OTHER_TUMOR_STAGE_DESC,
				list,
				otherTumorStageDesc,
				pathReportSection.getCreateUser());	
		}	
		// Insert into OCE if "other" tumor stage type entered.
		String otherTumorStageType = pathReportSection.getTumorStageTypeOther();
		if (!ApiFunctions.isEmpty(otherTumorStageType)) {
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATH_SECTION_OTHER_TUMOR_STAGE_TYPE,
				list,
				otherTumorStageType,
				pathReportSection.getCreateUser());	
		}
		
		
	}
	catch (Exception e) {
		throw new ApiException(e);
	}
    finally {
		ApiFunctions.close(clob, con);
	    ApiFunctions.close(pstmt);
	    ApiFunctions.close(cstmt);
	    ApiFunctions.closeDbConnection(con);
    }
    
  //if the path report section was successfully created, log that fact in ICP
  if (success) {
    BTXDetailsCreatePathReportSection btxDetails = new BTXDetailsCreatePathReportSection();
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btxDetails.setTransactionType("pdc_placeholder");
    btxDetails.setPathReportSectionData(pathReportSection);
    BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
    btx.perform(btxDetails);
  }
    
	return pathReportSection;
}
public PathReportSectionFindingData createPathReportSectionFinding(PathReportSectionFindingData dataBean, SecurityInfo securityInfo) {
	StringBuffer sql = new StringBuffer(256);
	sql.append("{ CALL INSERT INTO pdc_path_report_dx");
	sql.append(" (finding_line_id,");
	sql.append(" path_report_section_id,");
	sql.append(" path_dx_concept_id,");
	sql.append(" other_path_dx,");
	sql.append(" path_tc_concept_id,");
	sql.append(" other_path_tissue,");
	sql.append(" finding_notes)");
	sql.append(" VALUES (pdc_finding_line_id_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)");
	sql.append(" RETURNING finding_line_id INTO ? }");
			
	Connection con = ApiFunctions.getDbConnection();
	CallableStatement cstmt = null;
  boolean success = false;
	try {
		// Insert the new path report section additional finding.
		cstmt = con.prepareCall(sql.toString());
		cstmt.setString(1, dataBean.getPathReportSectionId());
		cstmt.setString(2, dataBean.getDiagnosis());
		cstmt.setString(3, dataBean.getDiagnosisOther());
		cstmt.setString(4, dataBean.getTissue());
		cstmt.setString(5, dataBean.getTissueOther());
		cstmt.setString(6, dataBean.getNote());
		cstmt.registerOutParameter(7, Types.NUMERIC);
		cstmt.execute();
		String id = cstmt.getString(7);
		dataBean.setFindingId(id);
    success = true;

		// Update OCE if an "other" diagnosis was entered.
	    String otherDiagnosis = dataBean.getDiagnosisOther();
		if (!ApiFunctions.isEmpty(otherDiagnosis)) {
			
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATHREPORT_DX_OTHER_DX,
				list,
				otherDiagnosis,
				dataBean.getEditUser()); 
		}
		
		// Update OCE if an "other" tissue was entered.
	    String otherTissue = dataBean.getTissueOther(); 
		if (!ApiFunctions.isEmpty(otherTissue)) {
			
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATHREPORT_DX_OTHER_TISSUE,
				list,
				otherTissue,
				dataBean.getEditUser());      
		}

	}
	catch (Exception e) {
		throw new ApiException(e);
	}
    finally {
	    ApiFunctions.close(cstmt);
	    ApiFunctions.closeDbConnection(con);
    }
    
  //if the path report section finding was successfully created, log that fact in ICP
  if (success) {
    //first get the name of the section to which we just added the finding, so it can be logged
    PathReportSectionData prsd = new PathReportSectionData();
    prsd.setPathReportSectionId(dataBean.getPathReportSectionId());
    prsd = getPathReportSection(prsd);
    String identifier = prsd.getSectionIdentifier();
    if (!ApiFunctions.isEmpty(identifier)) {
      dataBean.setPathReportSectionName(GbossFactory.getInstance().getDescription(identifier));
    }
    //now do the logging
    BTXDetailsCreatePathReportSectionFinding btxDetails = new BTXDetailsCreatePathReportSectionFinding();
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btxDetails.setTransactionType("pdc_placeholder");
    btxDetails.setPathReportSectionFindingData(dataBean);
    BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
    btx.perform(btxDetails);
  }
	return dataBean;
}
/**
 * Creates a new pathology report and updates its raw pathology report.  All required
 * pathology report fields and the raw pathology report are obtained from the
 * input <code>PathReportData</code>.  This method does not check that the raw 
 * pathology report contains data, and a new pathology report will be created
 * regardless.  A <code>PathReportData</code> bean containing the data of the new 
 * pathology report is returned.
 *
 * @param  pathReport  a <code>PathReportData</code> bean containing the raw pathology
 *						report and other required path report fields
 * @return  A <code>PathReportData</code> bean containing the new pathology report.
 */
public PathReportData createRawPathReport(PathReportData pathReport, SecurityInfo securityInfo) {
	BufferedWriter w = null;
	PathReportData newBean = null;
	String id = null;
	boolean hasPathReport = !ApiFunctions.isEmpty(pathReport.getRawPathReport());

	StringBuffer insertSql = new StringBuffer(128);
	insertSql.append("{ CALL INSERT INTO pdc_pathology_report");
	insertSql.append(" (ardais_id, consent_id, path_report_id, create_user, create_date, pathology_ascii_report)");
	insertSql.append(" VALUES ( ?, ?, pdc_path_report_id_seq.NEXTVAL, ?, ?, ");
	if (hasPathReport)
		insertSql.append(" empty_clob())");
	else
		insertSql.append(" NULL)");
	insertSql.append(" RETURNING path_report_id INTO ? }");

	Connection con = ApiFunctions.getDbConnection();
	CallableStatement cstmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
  boolean success = false;
	try {
		// Insert the new path report, saving the returned path
		// report id.
		cstmt = con.prepareCall(insertSql.toString());
		java.sql.Timestamp time = new java.sql.Timestamp((new java.util.Date()).getTime());
		cstmt.setString(1, pathReport.getArdaisId());
		cstmt.setString(2, pathReport.getConsentId());
		cstmt.setString(3, pathReport.getCreateUser());
		cstmt.setTimestamp(4, time);
		cstmt.registerOutParameter(5, Types.VARCHAR);
		cstmt.execute();
		id = cstmt.getString(5);
    pathReport.setPathReportId(id);

		// Execute the select query to lock the row just inserted so the
		// CLOB field can be updated. Use the returned CLOB locator to
		// update the raw path report CLOB.
		if (hasPathReport) {
			StringBuffer clobSql = new StringBuffer(128);
		    clobSql.append("SELECT pathology_ascii_report");
		    clobSql.append(" FROM pdc_pathology_report");
		    clobSql.append(" WHERE path_report_id = ?");
		    clobSql.append(" FOR UPDATE");
			
	        pstmt = con.prepareStatement(clobSql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getObject("pathology_ascii_report");
				w = new BufferedWriter(clob.getCharacterOutputStream());
				w.write(pathReport.getRawPathReport());
				w.flush();
				w.close();
			}
		}
    success = true;
	}
	catch (IOException e) {
		throw new ApiException(e);
	}
	catch (SQLException e) {
		throw new ApiException(e);
	}
    finally {
	    ApiFunctions.close(pstmt);
	    ApiFunctions.close(con, cstmt, rs);
    }
    
  //if the path report was successfully created, log that fact in ICP
  if (success) {
    BTXDetailsCreateRawPathReport btxDetails = new BTXDetailsCreateRawPathReport();
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btxDetails.setTransactionType("pdc_placeholder");
    btxDetails.setPathReportData(pathReport);
    BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
    btx.perform(btxDetails);
  }

	newBean = new PathReportData();
	newBean.setPathReportId(id);
	newBean.setArdaisId(pathReport.getArdaisId());
	newBean.setConsentId(pathReport.getConsentId());
	newBean.setRawPathReport(pathReport.getRawPathReport());

	return newBean;
}
/**
 * ejbActivate method comment
 */
public void ejbActivate() throws java.rmi.RemoteException {}
/**
 * ejbCreate method comment
 * @exception javax.ejb.CreateException The exception description.
 */
public void ejbCreate() throws javax.ejb.CreateException, EJBException {}
/**
 * ejbPassivate method comment
 */
public void ejbPassivate() throws java.rmi.RemoteException {}
/**
 * ejbRemove method comment
 */
public void ejbRemove() throws java.rmi.RemoteException {}

/**
 */
public PathReportData getPathReport(PathReportData pathReport) {
	String sql = "SELECT p.* FROM pdc_pathology_report p WHERE p.path_report_id = ?";
	String[] params = new String[] { pathReport.getPathReportId() };
	List results = ApiFunctions.runQuery(sql, params,  PathReportData.class);

	// This returns the original pathReport for BIGR Library
	// please talk to Jake it you need to change this!
	return (results.size() > 0) ? (PathReportData)results.get(0) : new PathReportData();
}
public PathReportDiagnosticData getPathReportDiagnostic(PathReportDiagnosticData pathReportDiagnostic) { 
	String sql = "SELECT d.* FROM pdc_path_report_diagnostics d WHERE d.path_report_id = ? AND d.diagnostics_concept_id = ? AND d.id = ?";
	String[] params	 = new String[] { pathReportDiagnostic.getPathReportId(), pathReportDiagnostic.getDiagnostic(), pathReportDiagnostic.getId().toString() };
	List results = ApiFunctions.runQuery(sql, params,  PathReportDiagnosticData.class);	
	return (results.size() > 0) ? (PathReportDiagnosticData)results.get(0) : new PathReportDiagnosticData();
}
public List getPathReportDiagnostics(PathReportData pathReport) {
	String sql = "SELECT d.* FROM pdc_path_report_diagnostics d WHERE d.path_report_id = ?";
	String[] params = new String[] { pathReport.getPathReportId() };
	return ApiFunctions.runQuery(sql, params, PathReportDiagnosticData.class);
}
public PathReportSectionData getPathReportSection(PathReportSectionData pathReportSection) { 
	String sql = "SELECT s.* FROM pdc_path_report_section s WHERE s.path_report_section_id = ?";
	String[] params = new String[] { pathReportSection.getPathReportSectionId() };
	List results = ApiFunctions.runQuery(sql, params, PathReportSectionData.class);
	return (results.size() > 0) ? (PathReportSectionData)results.get(0) : new PathReportSectionData();
}
public PathReportSectionFindingData getPathReportSectionFinding(PathReportSectionFindingData dataBean) { 
	String sql = "SELECT f.* FROM pdc_path_report_dx f WHERE f.finding_line_id = ?";
	String[] params = new String[] { dataBean.getFindingId() };
	List results = ApiFunctions.runQuery(sql, params, PathReportSectionFindingData.class);
	return (results.size() > 0) ? (PathReportSectionFindingData)results.get(0) : new PathReportSectionFindingData();
}
public List getPathReportSectionFindings(PathReportSectionData dataBean) {
	String sql = "SELECT f.* FROM pdc_path_report_dx f WHERE f.path_report_section_id = ?";
	String[] params = new String[] { dataBean.getPathReportSectionId() };
	return ApiFunctions.runQuery(sql, params, PathReportSectionFindingData.class);
}
public List getPathReportSections(PathReportData pathReport) {
	String sql = "SELECT s.* FROM pdc_path_report_section s WHERE s.path_report_id = ? Order By s.PRIMARY_IND DESC, s.SECTION_IDENTIFIER ASC";
	String[] params = new String[] { pathReport.getPathReportId() };
	return ApiFunctions.runQuery(sql, params, PathReportSectionData.class);
}
/**
 */
public PathReportData getPathReportSummary(PathReportData pathReport) {
	String id = pathReport.getPathReportId();
	
	StringBuffer pathSql = new StringBuffer(128);
	pathSql.append("SELECT p.*");
	pathSql.append(" FROM pdc_pathology_report p");
	pathSql.append(" WHERE p.path_report_id = ?");
	
	StringBuffer sectionsSql = new StringBuffer(128);
	sectionsSql.append("SELECT");
	sectionsSql.append(" s.path_report_section_id,");
	sectionsSql.append(" s.path_report_id,");
	sectionsSql.append(" s.section_identifier,");
	sectionsSql.append(" s.diagnosis_concept_id,");
	sectionsSql.append(" s.other_dx_name,");
	sectionsSql.append(" s.tissue_origin_concept_id,");
	sectionsSql.append(" s.other_origin_tc_name,");
	sectionsSql.append(" s.tissue_finding_concept_id,");
	sectionsSql.append(" s.other_finding_tc_name,");
	sectionsSql.append(" s.primary_ind");
	sectionsSql.append(" FROM pdc_path_report_section s");
	sectionsSql.append(" WHERE s.path_report_id = ?");
	sectionsSql.append(" ORDER BY s.section_identifier");

	StringBuffer diagnosticsSql = new StringBuffer(256);
	diagnosticsSql.append("SELECT d.path_report_id, d.diagnostics_concept_id, d.diagnostic_results_cid, d.id");
	diagnosticsSql.append(" FROM pdc_path_report_diagnostics d");
	diagnosticsSql.append(" WHERE d.path_report_id = ?");

	PathReportData pathData = null;
	Connection con = ApiFunctions.getDbConnection();
	try {
		// Get the path report.
		PreparedStatement pstmt = con.prepareStatement(pathSql.toString());
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			pathData = new PathReportData(rs);
		}
		rs.close();

		// Get the path report sections.
		pstmt = con.prepareStatement(sectionsSql.toString());
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			PathReportSectionData sectionData = new PathReportSectionData();
			sectionData.setPathReportId(rs.getString("path_report_id"));
			sectionData.setPathReportSectionId(rs.getString("path_report_section_id"));
			sectionData.setDiagnosis(rs.getString("diagnosis_concept_id"));
			sectionData.setDiagnosisOther(rs.getString("other_dx_name"));
			sectionData.setTissueOrigin(rs.getString("tissue_origin_concept_id"));
			sectionData.setTissueOriginOther(rs.getString("other_origin_tc_name"));
			sectionData.setTissueFinding(rs.getString("tissue_finding_concept_id"));
			sectionData.setTissueFindingOther(rs.getString("other_finding_tc_name"));
			sectionData.setPrimary(rs.getString("primary_ind"));
			sectionData.setSectionIdentifier(rs.getString("section_identifier"));
			pathData.addSection(sectionData);
		}
		rs.close();

		// Get the path report diagnostics.
		pstmt = con.prepareStatement(diagnosticsSql.toString());
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			PathReportDiagnosticData diagnosticData = new PathReportDiagnosticData();
			diagnosticData.setDiagnostic(rs.getString("diagnostics_concept_id"));
			diagnosticData.setPathReportId(rs.getString("path_report_id"));
			diagnosticData.setResult(rs.getString("diagnostic_results_cid"));
      Integer theInt = new Integer(rs.getInt("id"));
      diagnosticData.setId(theInt);
			pathData.addDiagnostic(diagnosticData);
		}
		rs.close();
	}
	catch (SQLException e) {
		throw new ApiException(e);
	}
	finally {
		ApiFunctions.closeDbConnection(con);
	}	
	return pathData;
}
/**
 */
public PathReportData getRawPathReport(PathReportData pathReport) {
	String id = pathReport.getPathReportId();
	
	StringBuffer sql = new StringBuffer(128);
	sql.append("SELECT p.ardais_id, p.consent_id, p.pathology_ascii_report");
	sql.append(" FROM pdc_pathology_report p");
	sql.append(" WHERE p.path_report_id = ?");
	
	PathReportData pathData = new PathReportData();
	pathData.setPathReportId(id);
	Connection con = ApiFunctions.getDbConnection();
	try {
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			pathData.setArdaisId(rs.getString("ardais_id"));
			pathData.setConsentId(rs.getString("consent_id"));
			pathData.setRawPathReport(ApiFunctions.getStringFromClob(rs.getClob("pathology_ascii_report")));
		}
		rs.close();
	}
	catch (SQLException e) {
		throw new ApiException(e);
	}
	finally {
		ApiFunctions.closeDbConnection(con);
	}	
	return pathData;
}
/**
 */
public PathReportData getRawPathReport(String lineId) {
	String tableName = null;
	String whereClause = null;
	StringBuffer mySQL = new StringBuffer(256);
    StringBuffer pathReport = new StringBuffer(256);
	PreparedStatement pStmt = null;
	ResultSet resultSet = null;
	Connection con = ApiFunctions.getDbConnection();
	PathReportData pathData = new PathReportData();
	 try {    
        
        mySQL.append("SELECT TABLE_NAME, COLUMN_NAME, WHERE_CLAUSE, ");
        mySQL.append("ABSTRACT_USER, TO_CHAR(ABSTRACT_DATE, 'MM/DD/YYYY') ABSTRACT_DATE, ");
        mySQL.append("OTHER_TEXT, TYPE_CODE FROM ARD_OTHER_CODE_EDITS ");
        mySQL.append("WHERE OTHER_LINE_ID = ? ");
        
        pStmt = con.prepareStatement(mySQL.toString());
        pStmt.setString(1, lineId);
        resultSet = pStmt.executeQuery();
        while (resultSet.next()) {
        	tableName = resultSet.getString("TABLE_NAME");        
        	whereClause = resultSet.getString("WHERE_CLAUSE");
            //Add user info to PathReport MR 4985
            pathReport.append("Other Text: " + resultSet.getString("OTHER_TEXT") + "\n");
            pathReport.append("Abstract User: " + resultSet.getString("ABSTRACT_USER") + "\n");
            pathReport.append("Abstract Date: " + resultSet.getString("ABSTRACT_DATE") + "\n");            
            pathReport.append("Data Table: " + resultSet.getString("TABLE_NAME") + "\n");
            pathReport.append("Data Column: " + resultSet.getString("COLUMN_NAME") + "\n");
            pathReport.append("Type Code: " + resultSet.getString("TYPE_CODE") + "\n\n");
	 	}
        
        if ((tableName == null) || (whereClause == null)) {        	
        	ApiLogger.log("No data available for OTHER_LINE_ID" + lineId);
        	throw new ApiException("Invalid line Id");
        }
        mySQL = new StringBuffer(128);
        if (ApiFunctions.safeEquals("PDC_PATHOLOGY_REPORT", tableName)) {	        
        	mySQL.append("SELECT ARDAIS_ID, CONSENT_ID, PATHOLOGY_ASCII_REPORT ,CONSENT_ID ");
        	mySQL.append("FROM PDC_PATHOLOGY_REPORT   " + whereClause);
        	
        } else  if (ApiFunctions.safeEquals("LIMS_PATHOLOGY_EVALUATION", tableName)) {	        
        	mySQL.append("SELECT REPORT.ARDAIS_ID, REPORT.CONSENT_ID, ");
        	mySQL.append("REPORT.PATHOLOGY_ASCII_REPORT ");
			mySQL.append("FROM ILTDS_SAMPLE SAMPLE, ILTDS_ASM ASM,PDC_PATHOLOGY_REPORT REPORT, ");
			mySQL.append("LIMS_PATHOLOGY_EVALUATION EVAL ");			
			mySQL.append(whereClause);
			mySQL.append(" AND SAMPLE.SAMPLE_BARCODE_ID = EVAL.SAMPLE_BARCODE_ID ");
			mySQL.append(" AND ASM.ASM_ID=SAMPLE.ASM_ID ");
			mySQL.append("AND REPORT.ARDAIS_ID=ASM.ARDAIS_ID  ");
			mySQL.append("AND REPORT.CONSENT_ID=ASM.CONSENT_ID ");
			        	
        } else if (ApiFunctions.safeEquals("ILTDS_ASM", tableName)) {
        	mySQL.append("SELECT REPORT.ARDAIS_ID, REPORT.CONSENT_ID, ");
	        mySQL.append("REPORT.PATHOLOGY_ASCII_REPORT,ILTDS_ASM.CONSENT_ID ");
			mySQL.append("FROM ILTDS_ASM, PDC_PATHOLOGY_REPORT REPORT ");
			mySQL.append(whereClause);
			mySQL.append(" AND REPORT.CONSENT_ID=ILTDS_ASM.CONSENT_ID ");
						
        } else if (ApiFunctions.safeEquals("ILTDS_ASM_FORM", tableName)) {
			mySQL.append("SELECT ARDAIS_ID, CONSENT_ID, PATHOLOGY_ASCII_REPORT ");
			mySQL.append("FROM PDC_PATHOLOGY_REPORT WHERE CONSENT_ID= ");
			mySQL.append("(SELECT CONSENT_ID FROM ILTDS_ASM_FORM ");
			mySQL.append(whereClause + ")");
						
        } else if (ApiFunctions.safeEquals("ILTDS_INFORMED_CONSENT", tableName)) {
        	mySQL.append("SELECT ARDAIS_ID, CONSENT_ID, PATHOLOGY_ASCII_REPORT ");
			mySQL.append("FROM PDC_PATHOLOGY_REPORT WHERE CONSENT_ID= ");
			mySQL.append("(SELECT CONSENT_ID FROM ILTDS_INFORMED_CONSENT ");
			mySQL.append(whereClause + ")");
						
        } else if (ApiFunctions.safeEquals("PDC_PATH_REPORT_DX", tableName)) {
	        mySQL.append("SELECT REPORT.ARDAIS_ID, REPORT.CONSENT_ID, ");
			mySQL.append("REPORT.PATHOLOGY_ASCII_REPORT ");
			mySQL.append("FROM PDC_PATHOLOGY_REPORT REPORT, PDC_PATH_REPORT_SECTION SEC ");
			mySQL.append("WHERE REPORT.PATH_REPORT_ID = SEC.PATH_REPORT_ID ");
			mySQL.append("AND SEC.PATH_REPORT_SECTION_ID = ");
			mySQL.append("(SELECT PATH_REPORT_SECTION_ID FROM PDC_PATH_REPORT_DX ");			
			mySQL.append(whereClause + ")");
			
        } else if (ApiFunctions.safeEquals("PDC_PATH_REPORT_SECTION", tableName)) {
	        mySQL.append("SELECT ARDAIS_ID, CONSENT_ID, PATHOLOGY_ASCII_REPORT ");
			mySQL.append("FROM PDC_PATHOLOGY_REPORT WHERE PATH_REPORT_ID= ");
			mySQL.append("(SELECT PATH_REPORT_ID FROM PDC_PATH_REPORT_SECTION ");
			mySQL.append(whereClause + ")");	
        } else if (ApiFunctions.safeEquals("LIMS_PE_FEATURES", tableName)) {
        	mySQL.append("SELECT REPORT.ARDAIS_ID, REPORT.CONSENT_ID, ");
        	mySQL.append("REPORT.PATHOLOGY_ASCII_REPORT ");
			mySQL.append("FROM ILTDS_SAMPLE SAMPLE, ILTDS_ASM ASM,PDC_PATHOLOGY_REPORT REPORT, ");
			mySQL.append("LIMS_PATHOLOGY_EVALUATION EVAL ");			
			mySQL.append("WHERE PE_ID = (SELECT PE_ID FROM LIMS_PE_FEATURES ");
			mySQL.append(whereClause + ") ");
			mySQL.append("AND SAMPLE.SAMPLE_BARCODE_ID = EVAL.SAMPLE_BARCODE_ID ");
			mySQL.append("AND ASM.ASM_ID=SAMPLE.ASM_ID ");
			mySQL.append("AND REPORT.ARDAIS_ID=ASM.ARDAIS_ID  ");
			mySQL.append("AND REPORT.CONSENT_ID=ASM.CONSENT_ID ");
        }
        
        pStmt = con.prepareStatement(mySQL.toString());
        resultSet = pStmt.executeQuery();
        while (resultSet.next()) {
			pathData.setArdaisId(resultSet.getString("ARDAIS_ID"));
			pathData.setConsentId(resultSet.getString("CONSENT_ID"));
            //Add user info to PathReport MR 4985
            pathReport.append(ApiFunctions.getStringFromClob(resultSet.getClob("PATHOLOGY_ASCII_REPORT")));
			pathData.setRawPathReport(pathReport.toString());
        }  

    } catch (Exception ex) {
	    throw new ApiException(ex.getMessage());
    } finally {
       ApiFunctions.close(con, pStmt, resultSet);
    }
	return pathData;
}
/**
 * Insert the method's description here.
 * Creation date: (7/31/2002 4:47:30 PM)
 * @return com.ardais.bigr.iltds.assistants.LegalValueSet
 * @param caseId java.lang.String
 */
public com.ardais.bigr.iltds.assistants.LegalValueSet getSectionIdentifierList(String pathReportId, String pathReportSecId) {
	StringBuffer sql = new StringBuffer(256);
	sql.append("select section_identifier from PDC_PATH_REPORT_SECTION where path_report_id = ? ");

	if (pathReportSecId != null) {
		sql.append("and path_report_section_id <> ?");
    }
    
    LegalValueSet allSections = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_PATH_SECTION_ID);

    Set sectionsToExclude = new HashSet();
	
    Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
        con = ApiFunctions.getDbConnection();
		pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, pathReportId);
		if (pathReportSecId != null)
			pstmt.setString(2, pathReportSecId);
		rs = pstmt.executeQuery();
		while (rs.next()) {
            sectionsToExclude.add(rs.getString("section_identifier"));
		}
	} catch (SQLException e) {
        ApiFunctions.throwAsRuntimeException(e);
	} finally {
		ApiFunctions.close(con, pstmt, rs);
	}
    
    LegalValueSet lSet = new LegalValueSet();
    Iterator iter = allSections.getIterator();
    while (iter.hasNext()) {
        LegalValue lv = (LegalValue) iter.next();
        String sectionCode = lv.getValue();
        String sectionDesc = lv.getDisplayValue();
        
        if (! sectionsToExclude.contains(sectionCode)) {
            lSet.addLegalValue(sectionCode, sectionDesc);
        }
    }

	return lSet;
}
/**
 * getSessionContext method comment
 * @return javax.ejb.SessionContext
 */
public javax.ejb.SessionContext getSessionContext() {
	return mySessionCtx;
}
/**
 * Returns <code>true</code> if a path report section with the same
 * unique fields as those specified in the input data bean already exists.
 *
 * @param  dataBean  the {@link com.ardais.bigr.pdc.javabeans.PathReportSectionData}
 * @return  <code>true</code> if the path report section exists
 */
public boolean isExistsPathReportSection(PathReportSectionData dataBean) { 
	String pathReportSectionId = dataBean.getPathReportSectionId();
	
	StringBuffer sql = new StringBuffer(128);
	sql.append("SELECT count(*)");
	sql.append(" FROM pdc_path_report_section");
	sql.append(" WHERE path_report_id = ?");
	sql.append(" AND section_identifier = ?");
	if (!ApiFunctions.isEmpty(pathReportSectionId))
		sql.append(" AND path_report_section_id <> ?");

	Connection con = ApiFunctions.getDbConnection();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
		pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, dataBean.getPathReportId());
		pstmt.setString(2, dataBean.getSectionIdentifier());
		if (!ApiFunctions.isEmpty(pathReportSectionId))
			pstmt.setString(3, pathReportSectionId);
		
		rs = pstmt.executeQuery();
		if (rs.next() && (rs.getInt(1) > 0))
			return true;
	}
	catch (Exception e) {
		throw new ApiException(e);
	}
	finally {
		ApiFunctions.close(con, pstmt, rs);
	}	
	return false;
}
/*
 * Returns <code>true</code> if the additional finding already exists
 * for the path report section specified in the input data bean.
 *
 * @param  dataBean  the {@link com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData}
 */
public boolean isExistsPathReportSectionFinding(PathReportSectionFindingData dataBean) { 
	StringBuffer sql = new StringBuffer(128);
	sql.append("SELECT count(*)");
	sql.append(" FROM pdc_path_report_dx");
	sql.append(" WHERE path_report_section_id = ?");
	sql.append(" AND path_dx_concept_id = ?");
	sql.append(" AND path_tc_concept_id = ?");

	Connection con = ApiFunctions.getDbConnection();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
		pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, dataBean.getPathReportSectionId());
		pstmt.setString(2, dataBean.getDiagnosis());
		pstmt.setString(3, dataBean.getTissue());
		rs = pstmt.executeQuery();
		if (rs.next() && (rs.getInt(1) > 0))
			return true;
	}
	catch (Exception e) {
		throw new ApiException(e);
	}
	finally {
		ApiFunctions.close(con, pstmt, rs);
	}	
	return false;
}
/**
 * setSessionContext method comment
 * @param ctx javax.ejb.SessionContext
 */
public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException {
	mySessionCtx = ctx;
}
/**
 * Updates a pathology report's data, except for the raw pathology report.  The 
 * pathology report id of the pathology report and the data to update are
 * obtained from the input <code>PathReportData</code>.  A <code>PathReportData</code> 
 * bean containing the data of the pathology report is returned.
 *
 * @param  pathReport  a <code>PathReportData</code> bean containing the path report id
 *						and the fields to be updated
 * @return  A <code>PathReportData</code> bean containing the updated pathology report data.
 * @see #updateRawPathReport(com.ardais.bigr.pdc.javabeans.PathReportData)
 */
public PathReportData updatePathReport(PathReportData pathReport, SecurityInfo securityInfo) {

	Connection con = null;
	BufferedWriter w = null;
	String currentOtherProcedure = null;
	String id = pathReport.getPathReportId();
  boolean pathReportAbstracted = false;

  pathReportAbstracted = PdcUtils.isPathReportAbstracted(id);
  
  String forUpdateSql = "SELECT * FROM pdc_pathology_report WHERE path_report_id = ? FOR UPDATE";
	String emptyClobSql = "UPDATE pdc_pathology_report SET additional_note = empty_clob() WHERE path_report_id = ?";
	String nullSql = "UPDATE pdc_pathology_report SET additional_note = null WHERE path_report_id = ?";
	String clobSelectSql = "SELECT additional_note FROM pdc_pathology_report WHERE path_report_id = ?";
	
	StringBuffer updateSql = new StringBuffer(256);
	updateSql.append("UPDATE pdc_pathology_report");
	updateSql.append(" set disease_concept_id = ?,");
	updateSql.append(" procedure_concept_id = ?,");
	updateSql.append(" other_pr_name = ?,");
	updateSql.append(" path_check_date_mm = ?,");
	updateSql.append(" path_check_date_yyyy = ?,");
	updateSql.append(" last_update_user = ?,");
	updateSql.append(" last_update_date = ?");
	updateSql.append(" WHERE path_report_id = ?");

	con = ApiFunctions.getDbConnection();
  boolean success = false;
	try {
		// Select the row for update to lock it, since we will
		// be updating a CLOB field.
		PreparedStatement pstmt = con.prepareStatement(forUpdateSql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {

			currentOtherProcedure = rs.getString("other_pr_name");
			
			// If the additional note is empty, then set the CLOB field 
			// to null.
			if (ApiFunctions.isEmpty(pathReport.getAdditionalNote())) {
				pstmt = con.prepareStatement(nullSql);
				pstmt.setString(1, id);
				pstmt.executeUpdate();
			}

			// Update the CLOB field by setting it to an empty CLOB
			// in preparation for writing updated data to it.  Select 
			// the CLOB field (actually the CLOB locator), and then 
			// update the CLOB data through the locator.
			else {
				pstmt = con.prepareStatement(emptyClobSql);
				pstmt.setString(1, id);
				pstmt.executeUpdate();

				pstmt = con.prepareStatement(clobSelectSql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getObject("additional_note");
					w = new BufferedWriter(clob.getCharacterOutputStream());
					w.write(pathReport.getAdditionalNote());
					w.flush();
					w.close();
				}
			}
			
			// Update all non-CLOB fields.
			pstmt = con.prepareStatement(updateSql.toString());
			pstmt.setString(1, pathReport.getDisease());
			pstmt.setString(2, pathReport.getProcedure());
			pstmt.setString(3, pathReport.getProcedureOther());
			pstmt.setString(4, pathReport.getPathReportMonth());
			pstmt.setString(5, pathReport.getPathReportYear());
			pstmt.setString(6, pathReport.getLastUpdateUser());
			pstmt.setTimestamp(7, new java.sql.Timestamp((new java.util.Date()).getTime()));
			pstmt.setString(8, id);
			pstmt.executeUpdate();	
      success = true;		

			// Update OCE if a new "other" procedure was entered.
		    String otherProcedure = pathReport.getProcedureOther();
			if ((ApiFunctions.isEmpty(currentOtherProcedure) && !ApiFunctions.isEmpty(otherProcedure)) ||
				(!ApiFunctions.isEmpty(currentOtherProcedure) && !ApiFunctions.isEmpty(otherProcedure) && !currentOtherProcedure.equalsIgnoreCase(otherProcedure))) {
					
				List list = new ArrayList();
				list.add(id);
				OceUtil.createOce(
					OceUtil.PDC_PATH_REPORT_OTHER_PROC,
					list,
					otherProcedure,
					pathReport.getLastUpdateUser());
			}

			// If the new "other" procedure is empty and an "other" procedure existed,
			// then update OCE to indicate that.
			if (!ApiFunctions.isEmpty(currentOtherProcedure) && ApiFunctions.isEmpty(otherProcedure)) {				
				
				OceUtil.markStatusObsolete(OceUtil.PDC_PATH_REPORT_OTHER_PROC, 
				                           id, 
				                           pathReport.getLastUpdateUser());				
				
			}
		}
	}
	catch (Exception e) {
		throw new ApiException(e);
	}
    finally {
	    try {
			if (w != null) w.close();
			if (con != null) con.close();
	    }
		catch (IOException e) {
			throw new ApiException(e);
		}
		catch (SQLException e) {
			throw new ApiException(e);
		}
    }
  //if the path report was successfully edited, log that fact in ICP
  if (success) {
    
    BTXDetailsPathReport btxDetails = null;
    if (pathReportAbstracted) {
      btxDetails = new BTXDetailsUpdatePathReport();
    }
    else {
      btxDetails = new BTXDetailsCreatePathReport();
    }
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btxDetails.setTransactionType("pdc_placeholder");
    btxDetails.setPathReportData(pathReport);
    BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
    btx.perform(btxDetails);
  }

	return pathReport;
}
public PathReportDiagnosticData updatePathReportDiagnostic(PathReportDiagnosticData pathReportDiagnostic, SecurityInfo securityInfo) {
	StringBuffer sql = new StringBuffer(256);
	sql.append("UPDATE pdc_path_report_diagnostics");
	sql.append(" SET diagnostic_results_cid = ?,");
	sql.append(" diagnostic_note = ?");
	sql.append(" WHERE diagnostics_concept_id = ? AND path_report_id = ?");
  sql.append(" AND id = ?");
			
	Connection con = ApiFunctions.getDbConnection();
  boolean success = false;
	try {
		// Update the path report diagnostic.
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, pathReportDiagnostic.getResult());
		pstmt.setString(2, pathReportDiagnostic.getNote());
		pstmt.setString(3, pathReportDiagnostic.getDiagnostic());
		pstmt.setString(4, pathReportDiagnostic.getPathReportId());
    pstmt.setInt(5, pathReportDiagnostic.getId().intValue());
		pstmt.executeUpdate();
    success = true;
	}
	catch (Exception e) {
		throw new ApiException(e);
	}
    finally {
	    ApiFunctions.closeDbConnection(con);
    }
  //if the path report diagnostic was successfully edited, log that fact in ICP
  if (success) {
    BTXDetailsUpdatePathReportDiagnostic btxDetails = new BTXDetailsUpdatePathReportDiagnostic();
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btxDetails.setTransactionType("pdc_placeholder");
    btxDetails.setPathReportDiagnosticData(pathReportDiagnostic);
    BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
    btx.perform(btxDetails);
  }
	return pathReportDiagnostic;
}
public PathReportSectionData updatePathReportSection(PathReportSectionData pathReportSection, SecurityInfo securityInfo) {
	StringBuffer selectSql = new StringBuffer(256);
	selectSql.append("SELECT other_dx_name, other_origin_tc_name, other_finding_tc_name, ");
	selectSql.append("other_distant_metastasis, other_histo_nuclear_grade, ");
	selectSql.append("other_histological_type, other_lymph_node_stage, other_stage_grouping, ");
	selectSql.append("other_tumor_stage_desc, other_tumor_stage_type ");
	selectSql.append(" FROM pdc_path_report_section");
	selectSql.append(" WHERE path_report_section_id = ?");

	StringBuffer sql = new StringBuffer(256);
	sql.append("UPDATE pdc_path_report_section");
	sql.append(" SET primary_ind = ?,");
	sql.append(" section_identifier = ?,");
	sql.append(" last_update_date = ?,");
	sql.append(" last_update_user = ?,");
	sql.append(" total_nodes_positive = ?,");
	sql.append(" total_nodes_examined = ?,");
	sql.append(" total_gleason_score = ?,");
	sql.append(" primary_gleason_score = ?,");
	sql.append(" secondary_gleason_score = ?,");
	sql.append(" extranodal_spread = ?,");
	sql.append(" perineural_invasion_ind = ?,");
	sql.append(" diagnosis_concept_id = ?,");
	sql.append(" tissue_origin_concept_id = ?,");
	sql.append(" tissue_finding_concept_id = ?,");
	sql.append(" lymph_node_stage = ?,");
	sql.append(" histological_nuclear_grade = ?,");
	sql.append(" venous_vessel_invasion = ?,");
	sql.append(" lymphatic_vascular_invasion = ?,");
	sql.append(" extensive_intraductal_comp = ?,");
	sql.append(" inflamm_cell_infiltrate = ?,");
	sql.append(" cell_infiltrate_amt = ?,");
	sql.append(" size_largest_nodal_mets = ?,");
	sql.append(" joint_component = ?,");
	sql.append(" tumor_configuration = ?,");
	sql.append(" tumor_size = ?,");
	sql.append(" tumor_weight = ?,");
	sql.append(" tumor_stage_desc = ?,");
	sql.append(" tumor_stage_type = ?,");
	sql.append(" margins_involved_desc = ?,");
	sql.append(" margins_uninvolved_desc = ?,");
	sql.append(" histological_type = ?,");
	sql.append(" other_dx_name = ?,");
	sql.append(" other_origin_tc_name = ?,");
	sql.append(" other_finding_tc_name = ?,");
	sql.append(" distant_metastasis = ?,");
	sql.append(" distant_metastasis_ind = ?,");
	sql.append(" lymph_node_stage_ind = ?,");
	sql.append(" tumor_stage_desc_ind = ?,");
	sql.append(" other_histological_type = ?,");
	sql.append(" other_histo_nuclear_grade = ?,");
	sql.append(" other_tumor_stage_type = ?,");
	sql.append(" stage_grouping = ?,");
	sql.append(" other_stage_grouping = ?,");
	sql.append(" other_lymph_node_stage = ?,");
	sql.append(" other_tumor_stage_desc = ?,");
	sql.append(" other_distant_metastasis = ?,");
	sql.append(" lymph_node_notes = ?,");
	sql.append(" path_section_notes = ?");
	sql.append(" WHERE path_report_section_id = ?");
			
	Connection con = ApiFunctions.getDbConnection();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	TemporaryClob clob = null;
	String id = pathReportSection.getPathReportSectionId();
  boolean success = false;
	try {
		// Get the current "other" diagnosis and tissue values.
		String currentOtherDiagnosis = null;
		String currentOtherTissueOrigin = null;
		String currentOtherTissueFinding = null;
		String currentOtherDistantMetastasis = null;
		String currentOtherHng = null;
		String currentOtherHistologicalType = null;
		String currentOtherLymphNode = null;
		String currentOtherStageGrouping = null;
		String currentOtherTumorStageDesc = null;
		String currentOtherTumorStageType = null;
		pstmt = con.prepareStatement(selectSql.toString());
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			currentOtherDiagnosis = rs.getString("other_dx_name");
			currentOtherTissueOrigin = rs.getString("other_origin_tc_name");
			currentOtherTissueFinding = rs.getString("other_finding_tc_name");
			
			currentOtherDistantMetastasis = rs.getString("other_distant_metastasis");
			currentOtherHng = rs.getString("other_histo_nuclear_grade");
			currentOtherHistologicalType = rs.getString("other_histological_type");
			currentOtherLymphNode = rs.getString("other_lymph_node_stage");
			currentOtherStageGrouping = rs.getString("other_stage_grouping");
			currentOtherTumorStageDesc = rs.getString("other_tumor_stage_desc");
			currentOtherTumorStageType = rs.getString("other_tumor_stage_type");     
		}

		// If this section is the primary, then make the existing primary a secondary.
		String primary = pathReportSection.getPrimary();
		if ((primary != null) && (primary.equals("Y"))) {
			StringBuffer primarySql = new StringBuffer(128);
			primarySql.append("UPDATE pdc_path_report_section");
			primarySql.append(" SET primary_ind = 'N'");
			primarySql.append(" WHERE path_report_id = ?");
			primarySql.append(" AND path_report_section_id <> ?");
			primarySql.append(" AND primary_ind = 'Y'");
			pstmt = con.prepareStatement(primarySql.toString());
			pstmt.setString(1, pathReportSection.getPathReportId());
			pstmt.setString(2, pathReportSection.getPathReportSectionId());
			pstmt.executeUpdate();		
		}
		
		// Update the path report section.
		pstmt = con.prepareStatement(sql.toString());
		java.sql.Timestamp time = new java.sql.Timestamp((new java.util.Date()).getTime());
		pstmt.setString(1, primary);
		pstmt.setString(2, pathReportSection.getSectionIdentifier());
		pstmt.setTimestamp(3, time);
		pstmt.setString(4, pathReportSection.getLastUpdateUser());
		pstmt.setString(5, pathReportSection.getTotalNodesPositive());
		pstmt.setString(6, pathReportSection.getTotalNodesExamined());
		Integer score = pathReportSection.getGleasonTotal();
		if (score == null)
			pstmt.setNull(7, Types.NUMERIC);
		else
			pstmt.setInt(7, score.intValue());
		score = pathReportSection.getGleasonPrimary();
		if (score == null)
			pstmt.setNull(8, Types.NUMERIC);
		else
			pstmt.setInt(8, score.intValue());
		score = pathReportSection.getGleasonSecondary();
		if (score == null)
			pstmt.setNull(9, Types.NUMERIC);
		else
			pstmt.setInt(9, score.intValue());
		pstmt.setString(10, pathReportSection.getExtranodalSpread());
		pstmt.setString(11, pathReportSection.getPerineuralInvasion());
		pstmt.setString(12, pathReportSection.getDiagnosis());
		pstmt.setString(13, pathReportSection.getTissueOrigin());
		pstmt.setString(14, pathReportSection.getTissueFinding());
		pstmt.setString(15, pathReportSection.getLymphNodeStage());
		pstmt.setString(16, pathReportSection.getHistologicalNuclearGrade());
		pstmt.setString(17, pathReportSection.getVenousVesselInvasion());
		pstmt.setString(18, pathReportSection.getLymphaticVascularInvasion());
		pstmt.setString(19, pathReportSection.getExtensiveIntraductalComp());
		pstmt.setString(20, pathReportSection.getInflammCellInfiltrate());
		pstmt.setString(21, pathReportSection.getCellInfiltrateAmount());
		pstmt.setString(22, pathReportSection.getSizeLargestNodalMets());
		pstmt.setString(23, pathReportSection.getJointComponent());
		pstmt.setString(24, pathReportSection.getTumorConfig());
		pstmt.setString(25, pathReportSection.getTumorSize());
		pstmt.setString(26, pathReportSection.getTumorWeight());
		pstmt.setString(27, pathReportSection.getTumorStageDesc());
		pstmt.setString(28, pathReportSection.getTumorStageType());
		pstmt.setString(29, pathReportSection.getMarginsInvolved());
		pstmt.setString(30, pathReportSection.getMarginsUninvolved());
		pstmt.setString(31, pathReportSection.getHistologicalType());
		pstmt.setString(32, pathReportSection.getDiagnosisOther());
		pstmt.setString(33, pathReportSection.getTissueOriginOther());
		pstmt.setString(34, pathReportSection.getTissueFindingOther());
		pstmt.setString(35, pathReportSection.getDistantMetastasis());
		pstmt.setString(36, pathReportSection.getDistantMetastasisInd());
		pstmt.setString(37, pathReportSection.getLymphNodeStageInd());
		pstmt.setString(38, pathReportSection.getTumorStageDescInd());
		pstmt.setString(39, pathReportSection.getHistologicalTypeOther());
		pstmt.setString(40, pathReportSection.getHistologicalNuclearGradeOther());
		pstmt.setString(41, pathReportSection.getTumorStageTypeOther());
		pstmt.setString(42, pathReportSection.getStageGrouping());
		pstmt.setString(43, pathReportSection.getStageGroupingOther());
		pstmt.setString(44, pathReportSection.getLymphNodeStageOther());
		pstmt.setString(45, pathReportSection.getTumorStageDescOther());
		pstmt.setString(46, pathReportSection.getDistantMetastasisOther());
		pstmt.setString(47, pathReportSection.getLymphNodeNotes());
		clob = new TemporaryClob(con, pathReportSection.getSectionNotes());
		pstmt.setClob(48, clob.getSQLClob());
		pstmt.setString(49, id);
		pstmt.executeUpdate();
    success = true;

		// Update OCE if a new "other" diagnosis was entered.
	    String otherDiagnosis = pathReportSection.getDiagnosisOther();
		if ((ApiFunctions.isEmpty(currentOtherDiagnosis) && !ApiFunctions.isEmpty(otherDiagnosis)) ||
			(!ApiFunctions.isEmpty(currentOtherDiagnosis) && !ApiFunctions.isEmpty(otherDiagnosis) && !currentOtherDiagnosis.equalsIgnoreCase(otherDiagnosis))) {
				
				List list = new ArrayList();
				list.add(id);
				OceUtil.createOce(
					OceUtil.PDC_PATH_SECTION_OTHER_DX,
					list,
					otherDiagnosis,
					pathReportSection.getLastUpdateUser());
		}

		// If the new "other" diagnosis is empty and an "other" diagnosis existed,
		// then update OCE to indicate that.
		if (!ApiFunctions.isEmpty(currentOtherDiagnosis) && ApiFunctions.isEmpty(otherDiagnosis)) {
			
			OceUtil.markStatusObsolete(OceUtil.PDC_PATH_SECTION_OTHER_DX, 
				                           id, 
				                           pathReportSection.getLastUpdateUser());			
			
		}

		// Update OCE if a new "other" origin tissue was entered.
	    String otherTissueOrigin = pathReportSection.getTissueOriginOther();
		if ((ApiFunctions.isEmpty(currentOtherTissueOrigin) && !ApiFunctions.isEmpty(otherTissueOrigin)) ||
			(!ApiFunctions.isEmpty(currentOtherTissueOrigin) && !ApiFunctions.isEmpty(otherTissueOrigin) && !currentOtherTissueOrigin.equalsIgnoreCase(otherTissueOrigin))) {
			
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATH_SECTION_OTHER_TISSUE_ORIGIN,
				list,
				otherTissueOrigin,
				pathReportSection.getLastUpdateUser());
		}

		// If the new "other" origin tissue is empty and an "other" origin tissue existed,
		// then update OCE to indicate that.
		if (!ApiFunctions.isEmpty(currentOtherTissueOrigin) && ApiFunctions.isEmpty(otherTissueOrigin)) {
			
			OceUtil.markStatusObsolete(OceUtil.PDC_PATH_SECTION_OTHER_TISSUE_ORIGIN, 
				                           id, 
				                           pathReportSection.getLastUpdateUser());
		}

		// Update OCE if a new "other" finding tissue was entered.
	    String otherTissueFinding = pathReportSection.getTissueFindingOther();
		if ((ApiFunctions.isEmpty(currentOtherTissueFinding) && !ApiFunctions.isEmpty(otherTissueFinding)) ||
			(!ApiFunctions.isEmpty(currentOtherTissueFinding) && !ApiFunctions.isEmpty(otherTissueFinding) && !currentOtherTissueFinding.equalsIgnoreCase(otherTissueFinding))) {
			
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATH_SECTION_OTHER_TISSUE_FINDING,
				list,
				otherTissueFinding,
				pathReportSection.getLastUpdateUser());			
			      
		}

		// If the new "other" finding tissue is empty and an "other" finding tissue existed,
		// then update OCE to indicate that.
		if (!ApiFunctions.isEmpty(currentOtherTissueFinding) && ApiFunctions.isEmpty(otherTissueFinding)) {
			
			OceUtil.markStatusObsolete(OceUtil.PDC_PATH_SECTION_OTHER_TISSUE_FINDING, 
				                           id, 
				                           pathReportSection.getLastUpdateUser());
		}
		// Update OCE if a new "other" distant metastasis was entered.
		String otherDistantMetastasis = pathReportSection.getDistantMetastasisOther();
		if ((ApiFunctions.isEmpty(currentOtherDistantMetastasis) && !ApiFunctions.isEmpty(otherDistantMetastasis)) ||
			(!ApiFunctions.isEmpty(currentOtherDistantMetastasis) && !ApiFunctions.isEmpty(otherDistantMetastasis) && 
			 !currentOtherDistantMetastasis.equalsIgnoreCase(otherDistantMetastasis))) {
				
				List list = new ArrayList();
				list.add(id);
				OceUtil.createOce(
					OceUtil.PDC_PATH_SECTION_OTHER_DISTANT_METASTASIS,
					list,
					otherDistantMetastasis,
					pathReportSection.getLastUpdateUser());
		}
		// If the new "other" distant metastasis is empty and an "other" distant metastasis existed,
		// then update OCE to indicate that.
		if (!ApiFunctions.isEmpty(currentOtherDistantMetastasis) && ApiFunctions.isEmpty(otherDistantMetastasis)) {
			
			OceUtil.markStatusObsolete(OceUtil.PDC_PATH_SECTION_OTHER_DISTANT_METASTASIS, 
				                           id, 
				                           pathReportSection.getLastUpdateUser());
		}
		// Update OCE if a new "other" histological nuclear grade was entered.
		String otherHng = pathReportSection.getHistologicalNuclearGradeOther();
		if ((ApiFunctions.isEmpty(currentOtherHng) && !ApiFunctions.isEmpty(otherHng)) ||
			(!ApiFunctions.isEmpty(currentOtherHng) && !ApiFunctions.isEmpty(otherHng) && 
			 !currentOtherHng.equalsIgnoreCase(otherHng))) {
				
				List list = new ArrayList();
				list.add(id);
				OceUtil.createOce(
					OceUtil.PDC_PATH_SECTION_OTHER_HNG,
					list,
					otherHng,
					pathReportSection.getLastUpdateUser());
		}
		// If the new "other" distant metastasis is empty and an "other"  histological nuclear grade existed,
		// then update OCE to indicate that.
		if (!ApiFunctions.isEmpty(currentOtherHng) && ApiFunctions.isEmpty(otherHng)) {
			
			OceUtil.markStatusObsolete(OceUtil.PDC_PATH_SECTION_OTHER_HNG, 
				                           id, 
				                           pathReportSection.getLastUpdateUser());
		}
		// Update OCE if a new "other" histological type was entered.
		String otherHistologicalType = pathReportSection.getHistologicalTypeOther();
		if ((ApiFunctions.isEmpty(currentOtherHistologicalType) && !ApiFunctions.isEmpty(otherHistologicalType)) ||
			(!ApiFunctions.isEmpty(currentOtherHistologicalType) && !ApiFunctions.isEmpty(otherHistologicalType) && 
			 !currentOtherHistologicalType.equalsIgnoreCase(otherHistologicalType))) {
				
				List list = new ArrayList();
				list.add(id);
				OceUtil.createOce(
					OceUtil.PDC_PATH_SECTION_OTHER_HISTOLOGICAL_TYPE,
					list,
					otherHistologicalType,
					pathReportSection.getLastUpdateUser());
		}
		// If the new "other" histological type is empty and an "other" histological type existed,
		// then update OCE to indicate that.
		if (!ApiFunctions.isEmpty(currentOtherHistologicalType) && ApiFunctions.isEmpty(otherHistologicalType)) {
			
			OceUtil.markStatusObsolete(OceUtil.PDC_PATH_SECTION_OTHER_HISTOLOGICAL_TYPE, 
				                           id, 
				                           pathReportSection.getLastUpdateUser());
		}
		// Update OCE if a new "other" lymph node stage was entered.
		String otherLymphNode = pathReportSection.getLymphNodeStageOther();
		if ((ApiFunctions.isEmpty(currentOtherLymphNode) && !ApiFunctions.isEmpty(otherLymphNode)) ||
			(!ApiFunctions.isEmpty(currentOtherLymphNode) && !ApiFunctions.isEmpty(otherLymphNode) && 
			 !currentOtherLymphNode.equalsIgnoreCase(otherLymphNode))) {
				
				List list = new ArrayList();
				list.add(id);
				OceUtil.createOce(
					OceUtil.PDC_PATH_SECTION_OTHER_LYMPH_NODE,
					list,
					otherLymphNode,
					pathReportSection.getLastUpdateUser());
		}
		// If the new "other" distant metastasis is empty and an "other" lymph node stage was existed,
		// then update OCE to indicate that.
		if (!ApiFunctions.isEmpty(currentOtherLymphNode) && ApiFunctions.isEmpty(otherLymphNode)) {
			
			OceUtil.markStatusObsolete(OceUtil.PDC_PATH_SECTION_OTHER_LYMPH_NODE, 
				                           id, 
				                           pathReportSection.getLastUpdateUser());
		}
		// Update OCE if a new "other" stage grouping was entered.
		String otherStageGrouping = pathReportSection.getStageGroupingOther();
		if ((ApiFunctions.isEmpty(currentOtherStageGrouping) && !ApiFunctions.isEmpty(otherStageGrouping)) ||
			(!ApiFunctions.isEmpty(currentOtherStageGrouping) && !ApiFunctions.isEmpty(otherStageGrouping) && 
			 !currentOtherStageGrouping.equalsIgnoreCase(otherStageGrouping))) {
				
				List list = new ArrayList();
				list.add(id);
				OceUtil.createOce(
					OceUtil.PDC_PATH_SECTION_OTHER_STAGE_GROUPING,
					list,
					otherStageGrouping,
					pathReportSection.getLastUpdateUser());
		}
		// If the new "other" stage grouping is empty and an "other" stage grouping was existed,
		// then update OCE to indicate that.
		if (!ApiFunctions.isEmpty(currentOtherStageGrouping) && ApiFunctions.isEmpty(otherStageGrouping)) {
			
			OceUtil.markStatusObsolete(OceUtil.PDC_PATH_SECTION_OTHER_STAGE_GROUPING, 
				                           id, 
				                           pathReportSection.getLastUpdateUser());
		}
		// Update OCE if a new "other" tumor stage desc was entered.
		String otherTumorStageDesc = pathReportSection.getTumorStageDescOther();
		if ((ApiFunctions.isEmpty(currentOtherTumorStageDesc) && !ApiFunctions.isEmpty(otherTumorStageDesc)) ||
			(!ApiFunctions.isEmpty(currentOtherTumorStageDesc) && !ApiFunctions.isEmpty(otherTumorStageDesc) && 
			 !currentOtherTumorStageDesc.equalsIgnoreCase(otherTumorStageDesc))) {
				
				List list = new ArrayList();
				list.add(id);
				OceUtil.createOce(
					OceUtil.PDC_PATH_SECTION_OTHER_TUMOR_STAGE_DESC,
					list,
					otherTumorStageDesc,
					pathReportSection.getLastUpdateUser());
		}
		// If the new "other" tumor stage desc is empty and an "other" tumor stage desc was existed,
		// then update OCE to indicate that.
		if (!ApiFunctions.isEmpty(currentOtherTumorStageDesc) && ApiFunctions.isEmpty(otherTumorStageDesc)) {
			
			OceUtil.markStatusObsolete(OceUtil.PDC_PATH_SECTION_OTHER_TUMOR_STAGE_DESC, 
				                           id, 
				                           pathReportSection.getLastUpdateUser());
		}
		
		// Update OCE if a new "other" tumor stage type was entered.
		String otherTumorStageType = pathReportSection.getTumorStageTypeOther();
		if ((ApiFunctions.isEmpty(currentOtherTumorStageType) && !ApiFunctions.isEmpty(otherTumorStageType)) ||
			(!ApiFunctions.isEmpty(currentOtherTumorStageType) && !ApiFunctions.isEmpty(otherTumorStageType) && 
			 !currentOtherTumorStageType.equalsIgnoreCase(otherTumorStageType))) {
				
				List list = new ArrayList();
				list.add(id);
				OceUtil.createOce(
					OceUtil.PDC_PATH_SECTION_OTHER_TUMOR_STAGE_TYPE,
					list,
					otherTumorStageType,
					pathReportSection.getLastUpdateUser());
		}
		// If the new "other" tumor stage type is empty and an "other" tumor stage type was existed,
		// then update OCE to indicate that.
		if (!ApiFunctions.isEmpty(currentOtherTumorStageType) && ApiFunctions.isEmpty(otherTumorStageType)) {
			
			OceUtil.markStatusObsolete(OceUtil.PDC_PATH_SECTION_OTHER_TUMOR_STAGE_TYPE, 
				                           id, 
				                           pathReportSection.getLastUpdateUser());
		}
		
		
		
	}
	catch (Exception e) {
		throw new ApiException(e);
	}
    finally {
		ApiFunctions.close(clob, con);
	    ApiFunctions.close(con, pstmt, rs);
    }
    
  //if the path report section was successfully edited, log that fact in ICP
  if (success) {
    BTXDetailsUpdatePathReportSection btxDetails = new BTXDetailsUpdatePathReportSection();
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btxDetails.setTransactionType("pdc_placeholder");
    btxDetails.setPathReportSectionData(pathReportSection);
    BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
    btx.perform(btxDetails);
  }

	return pathReportSection;
}
public PathReportSectionFindingData updatePathReportSectionFinding(PathReportSectionFindingData dataBean, SecurityInfo securityInfo) {
	StringBuffer selectSql = new StringBuffer(256);
	selectSql.append("SELECT other_path_dx, other_path_tissue");
	selectSql.append(" FROM pdc_path_report_dx");
	selectSql.append(" WHERE finding_line_id = ?");

	StringBuffer sql = new StringBuffer(256);
	sql.append("UPDATE pdc_path_report_dx");
	sql.append(" SET path_dx_concept_id = ?,");
	sql.append(" other_path_dx = ?,");
	sql.append(" path_tc_concept_id = ?,");
	sql.append(" other_path_tissue = ?,");
	sql.append(" finding_notes = ?");
	sql.append(" WHERE finding_line_id = ?");
			
	Connection con = ApiFunctions.getDbConnection();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String id = dataBean.getFindingId();
  boolean success = false;
	try {
		// Get the current "other" diagnosis and tissue values.
		String currentOtherDiagnosis = null;
		String currentOtherTissue = null;
		pstmt = con.prepareStatement(selectSql.toString());
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			currentOtherDiagnosis = rs.getString("other_path_dx");
			currentOtherTissue = rs.getString("other_path_tissue");
		}

		// Update the path report section additional finding.
		pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, dataBean.getDiagnosis());
		pstmt.setString(2, dataBean.getDiagnosisOther());
		pstmt.setString(3, dataBean.getTissue());
		pstmt.setString(4, dataBean.getTissueOther());
		pstmt.setString(5, dataBean.getNote());
		pstmt.setString(6, id);
		pstmt.executeUpdate();
    success = true;

		// Update OCE if an "other" diagnosis was entered.
	    String otherDiagnosis = dataBean.getDiagnosisOther();
		if ((ApiFunctions.isEmpty(currentOtherDiagnosis) && !ApiFunctions.isEmpty(otherDiagnosis)) ||
			(!ApiFunctions.isEmpty(currentOtherDiagnosis) && !ApiFunctions.isEmpty(otherDiagnosis) && !currentOtherDiagnosis.equalsIgnoreCase(otherDiagnosis))) {
			
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATHREPORT_DX_OTHER_DX,
				list,
				otherDiagnosis,
				dataBean.getEditUser());  
		}
		
		// If the new "other" diagnosis is empty and an "other" diagnosis existed,
		// then update OCE to indicate that.
		if (!ApiFunctions.isEmpty(currentOtherDiagnosis) && ApiFunctions.isEmpty(otherDiagnosis)) {
			
			OceUtil.markStatusObsolete(OceUtil.PDC_PATHREPORT_DX_OTHER_DX, 
				                           id, 
				                           dataBean.getEditUser());
		}

		// Update OCE if an "other" tissue was entered.
	    String otherTissue = dataBean.getTissueOther(); 
		if ((ApiFunctions.isEmpty(currentOtherTissue) && !ApiFunctions.isEmpty(otherTissue)) ||
			(!ApiFunctions.isEmpty(currentOtherTissue) && !ApiFunctions.isEmpty(otherTissue) && !currentOtherDiagnosis.equalsIgnoreCase(otherTissue))) {
			
			List list = new ArrayList();
			list.add(id);
			OceUtil.createOce(
				OceUtil.PDC_PATHREPORT_DX_OTHER_TISSUE,
				list,
				otherTissue,
				dataBean.getEditUser());     
		}

		
		// If the new "other" tissue is empty and an "other" tissue existed,
		// then update OCE to indicate that.
		if (!ApiFunctions.isEmpty(currentOtherTissue) && ApiFunctions.isEmpty(otherTissue)) {
			
			OceUtil.markStatusObsolete(OceUtil.PDC_PATHREPORT_DX_OTHER_TISSUE, 
				                           id, 
				                           dataBean.getEditUser());
		}
	}
	catch (Exception e) {
		throw new ApiException(e);
	}
    finally {
	    ApiFunctions.close(con, pstmt, rs);
    }
    
  //if the path report section finding was successfully edited, log that fact in ICP
  if (success) {
    //first get the name of the section to which the finding we just edited belongs, so it can be logged
    PathReportSectionFindingData prsfd = new PathReportSectionFindingData();
    prsfd.setFindingId(dataBean.getFindingId());
    prsfd = getPathReportSectionFinding(prsfd);
    PathReportSectionData prsd = new PathReportSectionData();
    prsd.setPathReportSectionId(prsfd.getPathReportSectionId());
    prsd = getPathReportSection(prsd);
    String identifier = prsd.getSectionIdentifier();
    if (!ApiFunctions.isEmpty(identifier)) {
      dataBean.setPathReportSectionName(GbossFactory.getInstance().getDescription(identifier));
    }
    //now do the logging
    BTXDetailsUpdatePathReportSectionFinding btxDetails = new BTXDetailsUpdatePathReportSectionFinding();
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btxDetails.setTransactionType("pdc_placeholder");
    btxDetails.setPathReportSectionFindingData(dataBean);
    BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
    btx.perform(btxDetails);
  }
	return dataBean;
}
/**
 * Updates a pathology report's raw pathology report.  The pathology report id
 * and the raw pathology report are obtained from the input 
 * <code>PathReportData</code>.  A <code>PathReportData</code> bean containing
 * the data of the pathology report is returned.
 *
 * @param  pathReport  a <code>PathReportData</code> bean containing the raw pathology
 *						report and the path report id
 * @return  A <code>PathReportData</code> bean containing the updated pathology report data.
 */
public PathReportData updateRawPathReport(PathReportData pathReport, SecurityInfo securityInfo) {

	Connection con = null;
	BufferedWriter w = null;

  boolean hasRawPathReport = false;

  String forUpdateSql = "SELECT * FROM pdc_pathology_report WHERE path_report_id = ? FOR UPDATE";
	String emptyClobSql = "UPDATE pdc_pathology_report SET pathology_ascii_report = empty_clob() WHERE path_report_id = ?";
	String nullSql = "UPDATE pdc_pathology_report SET pathology_ascii_report = null WHERE path_report_id = ?";
	String clobSelectSql = "SELECT pathology_ascii_report FROM pdc_pathology_report WHERE path_report_id = ?";
	String updateSql = "UPDATE pdc_pathology_report SET last_update_user = ?, last_update_date = ? WHERE path_report_id = ?";
	String id = pathReport.getPathReportId();

  hasRawPathReport = PdcUtils.hasRawPathReport(id);

	con = ApiFunctions.getDbConnection();
  boolean success = false;
	try {
		// Select the row for update to lock it, since we will
		// be updating a CLOB field.
		PreparedStatement pstmt = con.prepareStatement(forUpdateSql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {

			// If the path report is empty, then set the CLOB field 
			// to null.
			if (ApiFunctions.isEmpty(pathReport.getRawPathReport())) {
				pstmt = con.prepareStatement(nullSql);
				pstmt.setString(1, id);
				pstmt.executeUpdate();
			}

			// Update the CLOB field by setting it to an empty CLOB
			// in preparation for writing updated data to it, and then
			// select the CLOB field (actually the CLOB locator), and
			// update the CLOB data through the locator.
			else {			

				pstmt = con.prepareStatement(emptyClobSql);
				pstmt.setString(1, id);
				pstmt.executeUpdate();

				pstmt = con.prepareStatement(clobSelectSql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getObject("pathology_ascii_report");
					w = new BufferedWriter(clob.getCharacterOutputStream());
					w.write(pathReport.getRawPathReport());
					w.flush();
					w.close();
				}
			}
			
			// Update all non-CLOB fields.
			pstmt = con.prepareStatement(updateSql);
			pstmt.setString(1, pathReport.getLastUpdateUser());
			pstmt.setTimestamp(2, new java.sql.Timestamp((new java.util.Date()).getTime()));
			pstmt.setString(3, id);
			pstmt.executeUpdate();
      success = true;
		}
	}
	catch (IOException e) {
		throw new ApiException(e);
	}
	catch (SQLException e) {
		throw new ApiException(e);
	}
    finally {
	    try {
			if (w != null) w.close();
			if (con != null) con.close();
	    }
		catch (IOException e) {
			throw new ApiException(e);
		}
		catch (SQLException e) {
			throw new ApiException(e);
		}
    }
    
  //if the path report was successfully edited, log that fact in ICP
  if (success) {
    
    BTXDetailsPathReport btxDetails = null;
    if (hasRawPathReport) {
      btxDetails = new BTXDetailsUpdateRawPathReport();
    }
    else {
      btxDetails = new BTXDetailsCreateRawPathReport();
    }
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btxDetails.setTransactionType("pdc_placeholder");
    btxDetails.setPathReportData(pathReport);
    BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
    btx.perform(btxDetails);
  }

	return pathReport;
}
}
