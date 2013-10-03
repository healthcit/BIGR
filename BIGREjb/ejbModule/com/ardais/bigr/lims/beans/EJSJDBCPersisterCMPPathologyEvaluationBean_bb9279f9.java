package com.ardais.bigr.lims.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPPathologyEvaluationBean_bb9279f9
 */
public class EJSJDBCPersisterCMPPathologyEvaluationBean_bb9279f9 extends EJSJDBCPersister implements com.ardais.bigr.lims.beans.EJSFinderPathologyEvaluationBean {
	private static final String _createString = "INSERT INTO LIMS_PATHOLOGY_EVALUATION (PE_ID, SLIDE_ID, SAMPLE_BARCODE_ID, MICROSCOPIC_APPEARANCE, REPORTED_YN, CREATE_TYPE, TUMOR_CELLS, NORMAL_CELLS, HYPOACELLULARSTROMA_CELLS, NECROSIS_CELLS, LESION_CELLS, DIAGNOSIS_CONCEPT_ID, TISSUE_ORIGIN_CONCEPT_ID, CREATE_DATE, CREATE_USER, MIGRATED_YN, TISSUE_FINDING_CONCEPT_ID, OTHER_TISSUE_FINDING_NAME, OTHER_DIAGNOSIS_NAME, OTHER_TISSUE_ORIGIN_NAME, EXTERNAL_COMMENTS, INTERNAL_COMMENTS, SOURCE_PE_ID, CELLULARSTROMA_CELLS, REPORTED_DATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM LIMS_PATHOLOGY_EVALUATION  WHERE PE_ID = ?";
	private static final String _storeString = "UPDATE LIMS_PATHOLOGY_EVALUATION  SET SLIDE_ID = ?, SAMPLE_BARCODE_ID = ?, MICROSCOPIC_APPEARANCE = ?, REPORTED_YN = ?, CREATE_TYPE = ?, TUMOR_CELLS = ?, NORMAL_CELLS = ?, HYPOACELLULARSTROMA_CELLS = ?, NECROSIS_CELLS = ?, LESION_CELLS = ?, DIAGNOSIS_CONCEPT_ID = ?, TISSUE_ORIGIN_CONCEPT_ID = ?, CREATE_DATE = ?, CREATE_USER = ?, MIGRATED_YN = ?, TISSUE_FINDING_CONCEPT_ID = ?, OTHER_TISSUE_FINDING_NAME = ?, OTHER_DIAGNOSIS_NAME = ?, OTHER_TISSUE_ORIGIN_NAME = ?, EXTERNAL_COMMENTS = ?, INTERNAL_COMMENTS = ?, SOURCE_PE_ID = ?, CELLULARSTROMA_CELLS = ?, REPORTED_DATE = ? WHERE PE_ID = ?";
	private static final String _loadString = " SELECT T1.PE_ID, T1.SLIDE_ID, T1.SAMPLE_BARCODE_ID, T1.MICROSCOPIC_APPEARANCE, T1.REPORTED_YN, T1.CREATE_TYPE, T1.TUMOR_CELLS, T1.NORMAL_CELLS, T1.HYPOACELLULARSTROMA_CELLS, T1.NECROSIS_CELLS, T1.LESION_CELLS, T1.DIAGNOSIS_CONCEPT_ID, T1.TISSUE_ORIGIN_CONCEPT_ID, T1.CREATE_DATE, T1.CREATE_USER, T1.MIGRATED_YN, T1.TISSUE_FINDING_CONCEPT_ID, T1.OTHER_TISSUE_FINDING_NAME, T1.OTHER_DIAGNOSIS_NAME, T1.OTHER_TISSUE_ORIGIN_NAME, T1.EXTERNAL_COMMENTS, T1.INTERNAL_COMMENTS, T1.SOURCE_PE_ID, T1.CELLULARSTROMA_CELLS, T1.REPORTED_DATE FROM LIMS_PATHOLOGY_EVALUATION  T1 WHERE T1.PE_ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPPathologyEvaluationBean_bb9279f9
	 */
	public EJSJDBCPersisterCMPPathologyEvaluationBean_bb9279f9() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postInit
	 */
	public void postInit() {
	}
	/**
	 * _create
	 */
	public void _create(EntityBean eb) throws Exception {
		Object objectTemp = null;
		PathologyEvaluationBean b = (PathologyEvaluationBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.evaluationId == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.evaluationId);
			}
			if (b.slideId == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.slideId);
			}
			if (b.sampleId == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.sampleId);
			}
			if (b.microscopicAppearance == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.microscopicAppearance);
			}
			if (b.reportedYN == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.reportedYN);
			}
			if (b.createMethod == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.createMethod);
			}
			if (b.tumorCells == null) {
				pstmt.setNull(7, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(7, b.tumorCells.intValue());
			}
			if (b.normalCells == null) {
				pstmt.setNull(8, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(8, b.normalCells.intValue());
			}
			if (b.hypoacellularstromaCells == null) {
				pstmt.setNull(9, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(9, b.hypoacellularstromaCells.intValue());
			}
			if (b.necrosisCells == null) {
				pstmt.setNull(10, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(10, b.necrosisCells.intValue());
			}
			if (b.lesionCells == null) {
				pstmt.setNull(11, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(11, b.lesionCells.intValue());
			}
			if (b.diagnosis == null) {
				pstmt.setNull(12, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(12, b.diagnosis);
			}
			if (b.tissueOfOrigin == null) {
				pstmt.setNull(13, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(13, b.tissueOfOrigin);
			}
			if (b.createDate == null) {
				pstmt.setNull(14, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(14, b.createDate);
			}
			if (b.pathologist == null) {
				pstmt.setNull(15, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(15, b.pathologist);
			}
			if (b.migratedYN == null) {
				pstmt.setNull(16, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(16, b.migratedYN);
			}
			if (b.tissueOfFinding == null) {
				pstmt.setNull(17, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(17, b.tissueOfFinding);
			}
			if (b.tissueOfFindingOther == null) {
				pstmt.setNull(18, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(18, b.tissueOfFindingOther);
			}
			if (b.diagnosisOther == null) {
				pstmt.setNull(19, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(19, b.diagnosisOther);
			}
			if (b.tissueOfOriginOther == null) {
				pstmt.setNull(20, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(20, b.tissueOfOriginOther);
			}
			if (b.concatenatedExternalComments == null) {
				pstmt.setNull(21, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(21, b.concatenatedExternalComments);
			}
			if (b.concatenatedInternalComments == null) {
				pstmt.setNull(22, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(22, b.concatenatedInternalComments);
			}
			if (b.sourceEvaluationId == null) {
				pstmt.setNull(23, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(23, b.sourceEvaluationId);
			}
			if (b.cellularstromaCells == null) {
				pstmt.setNull(24, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(24, b.cellularstromaCells.intValue());
			}
			if (b.reportedDate == null) {
				pstmt.setNull(25, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(25, b.reportedDate);
			}
			pstmt.executeUpdate();
		}
		finally {
			returnPreparedStatement(pstmt);
		}
	}
	/**
	 * hydrate
	 */
	public void hydrate(EntityBean eb, Object data, Object pKey) throws Exception {
		Object objectTemp = null;
		PathologyEvaluationBean b = (PathologyEvaluationBean) eb;
		java.lang.String evaluationId = (java.lang.String)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		int tempint;

		b.evaluationId = evaluationId;
		b.slideId = resultSet.getString(2);
		b.sampleId = resultSet.getString(3);
		b.microscopicAppearance = resultSet.getString(4);
		b.reportedYN = resultSet.getString(5);
		b.createMethod = resultSet.getString(6);
		tempint = resultSet.getInt(7);
		b.tumorCells = resultSet.wasNull() ? null : new Integer(tempint);
		tempint = resultSet.getInt(8);
		b.normalCells = resultSet.wasNull() ? null : new Integer(tempint);
		tempint = resultSet.getInt(9);
		b.hypoacellularstromaCells = resultSet.wasNull() ? null : new Integer(tempint);
		tempint = resultSet.getInt(10);
		b.necrosisCells = resultSet.wasNull() ? null : new Integer(tempint);
		tempint = resultSet.getInt(11);
		b.lesionCells = resultSet.wasNull() ? null : new Integer(tempint);
		b.diagnosis = resultSet.getString(12);
		b.tissueOfOrigin = resultSet.getString(13);
		b.createDate = resultSet.getTimestamp(14);
		b.pathologist = resultSet.getString(15);
		b.migratedYN = resultSet.getString(16);
		b.tissueOfFinding = resultSet.getString(17);
		b.tissueOfFindingOther = resultSet.getString(18);
		b.diagnosisOther = resultSet.getString(19);
		b.tissueOfOriginOther = resultSet.getString(20);
		b.concatenatedExternalComments = resultSet.getString(21);
		b.concatenatedInternalComments = resultSet.getString(22);
		b.sourceEvaluationId = resultSet.getString(23);
		tempint = resultSet.getInt(24);
		b.cellularstromaCells = resultSet.wasNull() ? null : new Integer(tempint);
		b.reportedDate = resultSet.getTimestamp(25);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		PathologyEvaluationBean b = (PathologyEvaluationBean) eb;
		java.lang.String evaluationId = (java.lang.String)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (evaluationId == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, evaluationId);
			}
			resultSet = pstmt.executeQuery();
			if (!(resultSet.next())) throw new javax.ejb.ObjectNotFoundException();
			hydrate(eb, resultSet, pKey);
		}
		finally {
			if (resultSet != null) resultSet.close();
			returnPreparedStatement(pstmt);
		}
	}
	/**
	 * refresh
	 */
	public void refresh(EntityBean eb, boolean forUpdate) throws Exception {
		PathologyEvaluationBean b = (PathologyEvaluationBean) eb;
		java.lang.String evaluationId;
		evaluationId = b.evaluationId;
		java.lang.String _primaryKey = evaluationId;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		PathologyEvaluationBean b = (PathologyEvaluationBean) eb;
		java.lang.String evaluationId;
		evaluationId = b.evaluationId;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (evaluationId == null) {
				pstmt.setNull(25, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(25, evaluationId);
			}
			if (b.slideId == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.slideId);
			}
			if (b.sampleId == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.sampleId);
			}
			if (b.microscopicAppearance == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.microscopicAppearance);
			}
			if (b.reportedYN == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.reportedYN);
			}
			if (b.createMethod == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.createMethod);
			}
			if (b.tumorCells == null) {
				pstmt.setNull(6, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(6, b.tumorCells.intValue());
			}
			if (b.normalCells == null) {
				pstmt.setNull(7, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(7, b.normalCells.intValue());
			}
			if (b.hypoacellularstromaCells == null) {
				pstmt.setNull(8, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(8, b.hypoacellularstromaCells.intValue());
			}
			if (b.necrosisCells == null) {
				pstmt.setNull(9, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(9, b.necrosisCells.intValue());
			}
			if (b.lesionCells == null) {
				pstmt.setNull(10, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(10, b.lesionCells.intValue());
			}
			if (b.diagnosis == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.diagnosis);
			}
			if (b.tissueOfOrigin == null) {
				pstmt.setNull(12, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(12, b.tissueOfOrigin);
			}
			if (b.createDate == null) {
				pstmt.setNull(13, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(13, b.createDate);
			}
			if (b.pathologist == null) {
				pstmt.setNull(14, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(14, b.pathologist);
			}
			if (b.migratedYN == null) {
				pstmt.setNull(15, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(15, b.migratedYN);
			}
			if (b.tissueOfFinding == null) {
				pstmt.setNull(16, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(16, b.tissueOfFinding);
			}
			if (b.tissueOfFindingOther == null) {
				pstmt.setNull(17, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(17, b.tissueOfFindingOther);
			}
			if (b.diagnosisOther == null) {
				pstmt.setNull(18, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(18, b.diagnosisOther);
			}
			if (b.tissueOfOriginOther == null) {
				pstmt.setNull(19, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(19, b.tissueOfOriginOther);
			}
			if (b.concatenatedExternalComments == null) {
				pstmt.setNull(20, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(20, b.concatenatedExternalComments);
			}
			if (b.concatenatedInternalComments == null) {
				pstmt.setNull(21, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(21, b.concatenatedInternalComments);
			}
			if (b.sourceEvaluationId == null) {
				pstmt.setNull(22, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(22, b.sourceEvaluationId);
			}
			if (b.cellularstromaCells == null) {
				pstmt.setNull(23, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(23, b.cellularstromaCells.intValue());
			}
			if (b.reportedDate == null) {
				pstmt.setNull(24, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(24, b.reportedDate);
			}
			pstmt.executeUpdate();
		}
		finally {
			returnPreparedStatement(pstmt);
		}
	}
	/**
	 * remove
	 */
	public void remove(EntityBean eb) throws Exception {
		Object objectTemp = null;
		PathologyEvaluationBean b = (PathologyEvaluationBean) eb;
		java.lang.String evaluationId;
		evaluationId = b.evaluationId;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (evaluationId == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, evaluationId);
			}
			pstmt.executeUpdate();
		}
		finally {
			returnPreparedStatement(pstmt);
		}
	}
	/**
	 * getPrimaryKey
	 */
	public Object getPrimaryKey(Object data) throws Exception {
		java.lang.String evaluationId;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			evaluationId = resultSet.getString(1);
			return evaluationId;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.lims.beans.PathologyEvaluation findByPrimaryKey(java.lang.String primaryKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.lims.beans.PathologyEvaluation) home.activateBean(primaryKey);
	}
	/**
	 * findAll
	 */
	public EJSFinder findAll() throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.PE_ID, T1.SLIDE_ID, T1.SAMPLE_BARCODE_ID, T1.MICROSCOPIC_APPEARANCE, T1.REPORTED_YN, T1.CREATE_TYPE, T1.TUMOR_CELLS, T1.NORMAL_CELLS, T1.HYPOACELLULARSTROMA_CELLS, T1.NECROSIS_CELLS, T1.LESION_CELLS, T1.DIAGNOSIS_CONCEPT_ID, T1.TISSUE_ORIGIN_CONCEPT_ID, T1.CREATE_DATE, T1.CREATE_USER, T1.MIGRATED_YN, T1.TISSUE_FINDING_CONCEPT_ID, T1.OTHER_TISSUE_FINDING_NAME, T1.OTHER_DIAGNOSIS_NAME, T1.OTHER_TISSUE_ORIGIN_NAME, T1.EXTERNAL_COMMENTS, T1.INTERNAL_COMMENTS, T1.SOURCE_PE_ID, T1.CELLULARSTROMA_CELLS, T1.REPORTED_DATE FROM LIMS_PATHOLOGY_EVALUATION  T1 WHERE PE_ID = PE_ID");
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findAllNonMigrated
	 */
	public EJSFinder findAllNonMigrated() throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.PE_ID, T1.SLIDE_ID, T1.SAMPLE_BARCODE_ID, T1.MICROSCOPIC_APPEARANCE, T1.REPORTED_YN, T1.CREATE_TYPE, T1.TUMOR_CELLS, T1.NORMAL_CELLS, T1.HYPOACELLULARSTROMA_CELLS, T1.NECROSIS_CELLS, T1.LESION_CELLS, T1.DIAGNOSIS_CONCEPT_ID, T1.TISSUE_ORIGIN_CONCEPT_ID, T1.CREATE_DATE, T1.CREATE_USER, T1.MIGRATED_YN, T1.TISSUE_FINDING_CONCEPT_ID, T1.OTHER_TISSUE_FINDING_NAME, T1.OTHER_DIAGNOSIS_NAME, T1.OTHER_TISSUE_ORIGIN_NAME, T1.EXTERNAL_COMMENTS, T1.INTERNAL_COMMENTS, T1.SOURCE_PE_ID, T1.CELLULARSTROMA_CELLS, T1.REPORTED_DATE FROM LIMS_PATHOLOGY_EVALUATION  T1 WHERE MIGRATED_YN = \'N\'");
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findBySampleId
	 */
	public EJSFinder findBySampleId(java.lang.String sampleId) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.PE_ID, T1.SLIDE_ID, T1.SAMPLE_BARCODE_ID, T1.MICROSCOPIC_APPEARANCE, T1.REPORTED_YN, T1.CREATE_TYPE, T1.TUMOR_CELLS, T1.NORMAL_CELLS, T1.HYPOACELLULARSTROMA_CELLS, T1.NECROSIS_CELLS, T1.LESION_CELLS, T1.DIAGNOSIS_CONCEPT_ID, T1.TISSUE_ORIGIN_CONCEPT_ID, T1.CREATE_DATE, T1.CREATE_USER, T1.MIGRATED_YN, T1.TISSUE_FINDING_CONCEPT_ID, T1.OTHER_TISSUE_FINDING_NAME, T1.OTHER_DIAGNOSIS_NAME, T1.OTHER_TISSUE_ORIGIN_NAME, T1.EXTERNAL_COMMENTS, T1.INTERNAL_COMMENTS, T1.SOURCE_PE_ID, T1.CELLULARSTROMA_CELLS, T1.REPORTED_DATE FROM LIMS_PATHOLOGY_EVALUATION  T1 WHERE SAMPLE_BARCODE_ID=?");
			if (sampleId == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, sampleId);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
}
