package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPSampleBean_46ba81fe
 */
public class EJSJDBCPersisterCMPSampleBean_46ba81fe extends EJSJDBCPersister implements com.ardais.bigr.iltds.beans.EJSFinderSampleBean {
	private static final String _createString = "INSERT INTO ILTDS_SAMPLE (SAMPLE_BARCODE_ID, CELL_REF_LOCATION, ASM_NOTE, TYPE_OF_FIXATIVE, ALLOCATION_IND, BARCODE_SCAN_DATETIME, ORPHAN_DATETIME, ASM_POSITION, ARDAIS_ACCT_KEY, BORN_DATE, SUBDIVISION_DATE, PARENT_BARCODE_ID, PULL_YN, PULL_REASON, RELEASED_YN, DISCORDANT_YN, PULL_DATE, DI_MIN_THICKNESS_PFIN_CID, DI_MAX_THICKNESS_PFIN_CID, DI_WIDTH_ACROSS_PFIN_CID, FORMAT_DETAIL_CID, SAMPLE_SIZE_MEETS_SPECS, TOTAL_HOURS_IN_FIXATIVE, QCPOSTED_YN, HISTO_MIN_THICKNESS_PFIN_CID, HISTO_MAX_THICKNESS_PFIN_CID, HISTO_WIDTH_ACROSS_PFIN_CID, HISTO_REEMBED_REASON_CID, HISTO_SUBDIVIDABLE, HISTO_NOTES, OTHER_HISTO_REEMBED_REASON, PARAFFIN_FEATURE_CID, OTHER_PARAFFIN_FEATURE, RECEIPT_DATE, LAST_KNOWN_LOCATION_ID, IMPORTED_YN, CUSTOMER_ID, SAMPLE_TYPE_CID, COLLECTION_DATETIME, PRESERVATION_DATETIME, VOLUME, COLLECTION_DATETIME_DPC, PRESERVATION_DATETIME_DPC, BUFFER_TYPE_CUI, BUFFER_TYPE_OTHER, TOTAL_NUM_OF_CELLS, TOTAL_NUM_OF_CELLS_EX_REP_CUI, CELLS_PER_ML, PERCENT_VIABILITY, SOURCE, CONCENTRATION, YIELD, VOLUME_UNIT_CUI, VOLUME_UL, WEIGHT, WEIGHT_MG, WEIGHT_UNIT_CUI, SAMPLE_REGISTRATION_FORM, BOX_BARCODE_ID, ASM_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ILTDS_SAMPLE  WHERE SAMPLE_BARCODE_ID = ?";
	private static final String _storeString = "UPDATE ILTDS_SAMPLE  SET CELL_REF_LOCATION = ?, ASM_NOTE = ?, TYPE_OF_FIXATIVE = ?, ALLOCATION_IND = ?, BARCODE_SCAN_DATETIME = ?, ORPHAN_DATETIME = ?, ASM_POSITION = ?, ARDAIS_ACCT_KEY = ?, BORN_DATE = ?, SUBDIVISION_DATE = ?, PARENT_BARCODE_ID = ?, PULL_YN = ?, PULL_REASON = ?, RELEASED_YN = ?, DISCORDANT_YN = ?, PULL_DATE = ?, DI_MIN_THICKNESS_PFIN_CID = ?, DI_MAX_THICKNESS_PFIN_CID = ?, DI_WIDTH_ACROSS_PFIN_CID = ?, FORMAT_DETAIL_CID = ?, SAMPLE_SIZE_MEETS_SPECS = ?, TOTAL_HOURS_IN_FIXATIVE = ?, QCPOSTED_YN = ?, HISTO_MIN_THICKNESS_PFIN_CID = ?, HISTO_MAX_THICKNESS_PFIN_CID = ?, HISTO_WIDTH_ACROSS_PFIN_CID = ?, HISTO_REEMBED_REASON_CID = ?, HISTO_SUBDIVIDABLE = ?, HISTO_NOTES = ?, OTHER_HISTO_REEMBED_REASON = ?, PARAFFIN_FEATURE_CID = ?, OTHER_PARAFFIN_FEATURE = ?, RECEIPT_DATE = ?, LAST_KNOWN_LOCATION_ID = ?, IMPORTED_YN = ?, CUSTOMER_ID = ?, SAMPLE_TYPE_CID = ?, COLLECTION_DATETIME = ?, PRESERVATION_DATETIME = ?, VOLUME = ?, COLLECTION_DATETIME_DPC = ?, PRESERVATION_DATETIME_DPC = ?, BUFFER_TYPE_CUI = ?, BUFFER_TYPE_OTHER = ?, TOTAL_NUM_OF_CELLS = ?, TOTAL_NUM_OF_CELLS_EX_REP_CUI = ?, CELLS_PER_ML = ?, PERCENT_VIABILITY = ?, SOURCE = ?, CONCENTRATION = ?, YIELD = ?, VOLUME_UNIT_CUI = ?, VOLUME_UL = ?, WEIGHT = ?, WEIGHT_MG = ?, WEIGHT_UNIT_CUI = ?, SAMPLE_REGISTRATION_FORM = ?, BOX_BARCODE_ID = ?, ASM_ID = ? WHERE SAMPLE_BARCODE_ID = ?";
	private static final String _loadString = " SELECT T1.SAMPLE_BARCODE_ID, T1.CELL_REF_LOCATION, T1.ASM_NOTE, T1.TYPE_OF_FIXATIVE, T1.ALLOCATION_IND, T1.BARCODE_SCAN_DATETIME, T1.ORPHAN_DATETIME, T1.ASM_POSITION, T1.ARDAIS_ACCT_KEY, T1.BORN_DATE, T1.SUBDIVISION_DATE, T1.PARENT_BARCODE_ID, T1.PULL_YN, T1.PULL_REASON, T1.RELEASED_YN, T1.DISCORDANT_YN, T1.PULL_DATE, T1.DI_MIN_THICKNESS_PFIN_CID, T1.DI_MAX_THICKNESS_PFIN_CID, T1.DI_WIDTH_ACROSS_PFIN_CID, T1.FORMAT_DETAIL_CID, T1.SAMPLE_SIZE_MEETS_SPECS, T1.TOTAL_HOURS_IN_FIXATIVE, T1.QCPOSTED_YN, T1.HISTO_MIN_THICKNESS_PFIN_CID, T1.HISTO_MAX_THICKNESS_PFIN_CID, T1.HISTO_WIDTH_ACROSS_PFIN_CID, T1.HISTO_REEMBED_REASON_CID, T1.HISTO_SUBDIVIDABLE, T1.HISTO_NOTES, T1.OTHER_HISTO_REEMBED_REASON, T1.PARAFFIN_FEATURE_CID, T1.OTHER_PARAFFIN_FEATURE, T1.RECEIPT_DATE, T1.LAST_KNOWN_LOCATION_ID, T1.IMPORTED_YN, T1.CUSTOMER_ID, T1.SAMPLE_TYPE_CID, T1.COLLECTION_DATETIME, T1.PRESERVATION_DATETIME, T1.VOLUME, T1.COLLECTION_DATETIME_DPC, T1.PRESERVATION_DATETIME_DPC, T1.BUFFER_TYPE_CUI, T1.BUFFER_TYPE_OTHER, T1.TOTAL_NUM_OF_CELLS, T1.TOTAL_NUM_OF_CELLS_EX_REP_CUI, T1.CELLS_PER_ML, T1.PERCENT_VIABILITY, T1.SOURCE, T1.CONCENTRATION, T1.YIELD, T1.VOLUME_UNIT_CUI, T1.VOLUME_UL, T1.WEIGHT, T1.WEIGHT_MG, T1.WEIGHT_UNIT_CUI, T1.SAMPLE_REGISTRATION_FORM, T1.BOX_BARCODE_ID, T1.ASM_ID FROM ILTDS_SAMPLE  T1 WHERE T1.SAMPLE_BARCODE_ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPSampleBean_46ba81fe
	 */
	public EJSJDBCPersisterCMPSampleBean_46ba81fe() throws java.rmi.RemoteException {
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
		SampleBean b = (SampleBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.sample_barcode_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.sample_barcode_id);
			}
			if (b.cell_ref_location == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.cell_ref_location);
			}
			if (b.asm_note == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.asm_note);
			}
			if (b.type_of_fixative == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.type_of_fixative);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.allocation_ind);
			if (objectTemp == null) {
				pstmt.setNull(5, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(5, (java.lang.String)objectTemp);
			}
			if (b.barcode_scan_datetime == null) {
				pstmt.setNull(6, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(6, b.barcode_scan_datetime);
			}
			if (b.orphan_datetime == null) {
				pstmt.setNull(7, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(7, b.orphan_datetime);
			}
			if (b.asm_position == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.asm_position);
			}
			if (b.ardais_acct_key == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.ardais_acct_key);
			}
			if (b.born_date == null) {
				pstmt.setNull(10, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(10, b.born_date);
			}
			if (b.subdivision_date == null) {
				pstmt.setNull(11, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(11, b.subdivision_date);
			}
			if (b.parent_barcode_id == null) {
				pstmt.setNull(12, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(12, b.parent_barcode_id);
			}
			if (b.pullYN == null) {
				pstmt.setNull(13, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(13, b.pullYN);
			}
			if (b.pullReason == null) {
				pstmt.setNull(14, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(14, b.pullReason);
			}
			if (b.releasedYN == null) {
				pstmt.setNull(15, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(15, b.releasedYN);
			}
			if (b.discordantYN == null) {
				pstmt.setNull(16, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(16, b.discordantYN);
			}
			if (b.pullDate == null) {
				pstmt.setNull(17, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(17, b.pullDate);
			}
			if (b.diMinThicknessPfinCid == null) {
				pstmt.setNull(18, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(18, b.diMinThicknessPfinCid);
			}
			if (b.diMaxThicknessPfinCid == null) {
				pstmt.setNull(19, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(19, b.diMaxThicknessPfinCid);
			}
			if (b.diWidthAcrossPfinCid == null) {
				pstmt.setNull(20, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(20, b.diWidthAcrossPfinCid);
			}
			if (b.formatDetailCid == null) {
				pstmt.setNull(21, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(21, b.formatDetailCid);
			}
			if (b.sampleSizeMeetsSpecs == null) {
				pstmt.setNull(22, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(22, b.sampleSizeMeetsSpecs);
			}
			if (b.hoursInFixative == null) {
				pstmt.setNull(23, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(23, b.hoursInFixative.intValue());
			}
			if (b.qcpostedYN == null) {
				pstmt.setNull(24, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(24, b.qcpostedYN);
			}
			if (b.histoMinThicknessPfinCid == null) {
				pstmt.setNull(25, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(25, b.histoMinThicknessPfinCid);
			}
			if (b.histoMaxThicknessPfinCid == null) {
				pstmt.setNull(26, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(26, b.histoMaxThicknessPfinCid);
			}
			if (b.histoWidthAcrossPfinCid == null) {
				pstmt.setNull(27, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(27, b.histoWidthAcrossPfinCid);
			}
			if (b.histoReembedReasonCid == null) {
				pstmt.setNull(28, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(28, b.histoReembedReasonCid);
			}
			if (b.histoSubdividable == null) {
				pstmt.setNull(29, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(29, b.histoSubdividable);
			}
			if (b.histoNotes == null) {
				pstmt.setNull(30, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(30, b.histoNotes);
			}
			if (b.otherHistoReembedReason == null) {
				pstmt.setNull(31, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(31, b.otherHistoReembedReason);
			}
			if (b.paraffinFeatureCid == null) {
				pstmt.setNull(32, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(32, b.paraffinFeatureCid);
			}
			if (b.otherParaffinFeature == null) {
				pstmt.setNull(33, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(33, b.otherParaffinFeature);
			}
			if (b.receiptDate == null) {
				pstmt.setNull(34, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(34, b.receiptDate);
			}
			if (b.lastknownlocationid == null) {
				pstmt.setNull(35, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(35, b.lastknownlocationid);
			}
			if (b.importedYN == null) {
				pstmt.setNull(36, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(36, b.importedYN);
			}
			if (b.customerId == null) {
				pstmt.setNull(37, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(37, b.customerId);
			}
			if (b.sampleTypeCid == null) {
				pstmt.setNull(38, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(38, b.sampleTypeCid);
			}
			if (b.collection_datetime == null) {
				pstmt.setNull(39, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(39, b.collection_datetime);
			}
			if (b.preservation_datetime == null) {
				pstmt.setNull(40, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(40, b.preservation_datetime);
			}
			if (b.volume == null) {
				pstmt.setNull(41, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(41, b.volume);
			}
			if (b.collection_datetime_dpc == null) {
				pstmt.setNull(42, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(42, b.collection_datetime_dpc);
			}
			if (b.preservation_datetime_dpc == null) {
				pstmt.setNull(43, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(43, b.preservation_datetime_dpc);
			}
			if (b.bufferTypeCui == null) {
				pstmt.setNull(44, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(44, b.bufferTypeCui);
			}
			if (b.bufferTypeOther == null) {
				pstmt.setNull(45, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(45, b.bufferTypeOther);
			}
			if (b.totalNumOfCells == null) {
				pstmt.setNull(46, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(46, b.totalNumOfCells);
			}
			if (b.totalNumOfCellsExRepCui == null) {
				pstmt.setNull(47, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(47, b.totalNumOfCellsExRepCui);
			}
			if (b.cellsPerMl == null) {
				pstmt.setNull(48, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(48, b.cellsPerMl);
			}
			if (b.percentViability == null) {
				pstmt.setNull(49, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(49, b.percentViability.intValue());
			}
			if (b.source == null) {
				pstmt.setNull(50, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(50, b.source);
			}
			if (b.concentration == null) {
				pstmt.setNull(51, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(51, b.concentration);
			}
			if (b.yield == null) {
				pstmt.setNull(52, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(52, b.yield);
			}
			if (b.volumeUnitCui == null) {
				pstmt.setNull(53, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(53, b.volumeUnitCui);
			}
			if (b.volumeInUl == null) {
				pstmt.setNull(54, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(54, b.volumeInUl);
			}
			if (b.weight == null) {
				pstmt.setNull(55, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(55, b.weight);
			}
			if (b.weightInMg == null) {
				pstmt.setNull(56, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(56, b.weightInMg);
			}
			if (b.weightUnitCui == null) {
				pstmt.setNull(57, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(57, b.weightUnitCui);
			}
			if (b.sampleRegistrationForm == null) {
				pstmt.setNull(58, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(58, b.sampleRegistrationForm);
			}
			if (b.samplebox_box_barcode_id == null) {
				pstmt.setNull(59, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(59, b.samplebox_box_barcode_id);
			}
			if (b.asm_asm_id == null) {
				pstmt.setNull(60, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(60, b.asm_asm_id);
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
		SampleBean b = (SampleBean) eb;
		com.ardais.bigr.iltds.beans.SampleKey _primaryKey = (com.ardais.bigr.iltds.beans.SampleKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		int tempint;

		b.sample_barcode_id = _primaryKey.sample_barcode_id;
		b.cell_ref_location = resultSet.getString(2);
		b.asm_note = resultSet.getString(3);
		b.type_of_fixative = resultSet.getString(4);
		b.allocation_ind = (java.lang.String)com.ibm.vap.converters.VapTrimStringConverter.singleton().objectFrom(resultSet.getString(5));
		b.barcode_scan_datetime = resultSet.getTimestamp(6);
		b.orphan_datetime = resultSet.getTimestamp(7);
		b.asm_position = resultSet.getString(8);
		b.ardais_acct_key = resultSet.getString(9);
		b.born_date = resultSet.getTimestamp(10);
		b.subdivision_date = resultSet.getTimestamp(11);
		b.parent_barcode_id = resultSet.getString(12);
		b.pullYN = resultSet.getString(13);
		b.pullReason = resultSet.getString(14);
		b.releasedYN = resultSet.getString(15);
		b.discordantYN = resultSet.getString(16);
		b.pullDate = resultSet.getTimestamp(17);
		b.diMinThicknessPfinCid = resultSet.getString(18);
		b.diMaxThicknessPfinCid = resultSet.getString(19);
		b.diWidthAcrossPfinCid = resultSet.getString(20);
		b.formatDetailCid = resultSet.getString(21);
		b.sampleSizeMeetsSpecs = resultSet.getString(22);
		tempint = resultSet.getInt(23);
		b.hoursInFixative = resultSet.wasNull() ? null : new Integer(tempint);
		b.qcpostedYN = resultSet.getString(24);
		b.histoMinThicknessPfinCid = resultSet.getString(25);
		b.histoMaxThicknessPfinCid = resultSet.getString(26);
		b.histoWidthAcrossPfinCid = resultSet.getString(27);
		b.histoReembedReasonCid = resultSet.getString(28);
		b.histoSubdividable = resultSet.getString(29);
		b.histoNotes = resultSet.getString(30);
		b.otherHistoReembedReason = resultSet.getString(31);
		b.paraffinFeatureCid = resultSet.getString(32);
		b.otherParaffinFeature = resultSet.getString(33);
		b.receiptDate = resultSet.getTimestamp(34);
		b.lastknownlocationid = resultSet.getString(35);
		b.importedYN = resultSet.getString(36);
		b.customerId = resultSet.getString(37);
		b.sampleTypeCid = resultSet.getString(38);
		b.collection_datetime = resultSet.getTimestamp(39);
		b.preservation_datetime = resultSet.getTimestamp(40);
		b.volume = resultSet.getBigDecimal(41);
		b.collection_datetime_dpc = resultSet.getString(42);
		b.preservation_datetime_dpc = resultSet.getString(43);
		b.bufferTypeCui = resultSet.getString(44);
		b.bufferTypeOther = resultSet.getString(45);
		b.totalNumOfCells = resultSet.getBigDecimal(46);
		b.totalNumOfCellsExRepCui = resultSet.getString(47);
		b.cellsPerMl = resultSet.getBigDecimal(48);
		tempint = resultSet.getInt(49);
		b.percentViability = resultSet.wasNull() ? null : new Integer(tempint);
		b.source = resultSet.getString(50);
		b.concentration = resultSet.getBigDecimal(51);
		b.yield = resultSet.getBigDecimal(52);
		b.volumeUnitCui = resultSet.getString(53);
		b.volumeInUl = resultSet.getBigDecimal(54);
		b.weight = resultSet.getBigDecimal(55);
		b.weightInMg = resultSet.getBigDecimal(56);
		b.weightUnitCui = resultSet.getString(57);
		b.sampleRegistrationForm = resultSet.getString(58);
		b.samplebox_box_barcode_id = resultSet.getString(59);
		b.asm_asm_id = resultSet.getString(60);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		SampleBean b = (SampleBean) eb;
		com.ardais.bigr.iltds.beans.SampleKey _primaryKey = (com.ardais.bigr.iltds.beans.SampleKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.sample_barcode_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.sample_barcode_id);
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
		SampleBean b = (SampleBean) eb;
		com.ardais.bigr.iltds.beans.SampleKey _primaryKey = new com.ardais.bigr.iltds.beans.SampleKey();
		_primaryKey.sample_barcode_id = b.sample_barcode_id;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		SampleBean b = (SampleBean) eb;
		com.ardais.bigr.iltds.beans.SampleKey _primaryKey = new com.ardais.bigr.iltds.beans.SampleKey();
		_primaryKey.sample_barcode_id = b.sample_barcode_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.sample_barcode_id == null) {
				pstmt.setNull(60, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(60, _primaryKey.sample_barcode_id);
			}
			if (b.cell_ref_location == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.cell_ref_location);
			}
			if (b.asm_note == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.asm_note);
			}
			if (b.type_of_fixative == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.type_of_fixative);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.allocation_ind);
			if (objectTemp == null) {
				pstmt.setNull(4, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(4, (java.lang.String)objectTemp);
			}
			if (b.barcode_scan_datetime == null) {
				pstmt.setNull(5, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(5, b.barcode_scan_datetime);
			}
			if (b.orphan_datetime == null) {
				pstmt.setNull(6, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(6, b.orphan_datetime);
			}
			if (b.asm_position == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.asm_position);
			}
			if (b.ardais_acct_key == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.ardais_acct_key);
			}
			if (b.born_date == null) {
				pstmt.setNull(9, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(9, b.born_date);
			}
			if (b.subdivision_date == null) {
				pstmt.setNull(10, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(10, b.subdivision_date);
			}
			if (b.parent_barcode_id == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.parent_barcode_id);
			}
			if (b.pullYN == null) {
				pstmt.setNull(12, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(12, b.pullYN);
			}
			if (b.pullReason == null) {
				pstmt.setNull(13, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(13, b.pullReason);
			}
			if (b.releasedYN == null) {
				pstmt.setNull(14, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(14, b.releasedYN);
			}
			if (b.discordantYN == null) {
				pstmt.setNull(15, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(15, b.discordantYN);
			}
			if (b.pullDate == null) {
				pstmt.setNull(16, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(16, b.pullDate);
			}
			if (b.diMinThicknessPfinCid == null) {
				pstmt.setNull(17, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(17, b.diMinThicknessPfinCid);
			}
			if (b.diMaxThicknessPfinCid == null) {
				pstmt.setNull(18, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(18, b.diMaxThicknessPfinCid);
			}
			if (b.diWidthAcrossPfinCid == null) {
				pstmt.setNull(19, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(19, b.diWidthAcrossPfinCid);
			}
			if (b.formatDetailCid == null) {
				pstmt.setNull(20, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(20, b.formatDetailCid);
			}
			if (b.sampleSizeMeetsSpecs == null) {
				pstmt.setNull(21, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(21, b.sampleSizeMeetsSpecs);
			}
			if (b.hoursInFixative == null) {
				pstmt.setNull(22, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(22, b.hoursInFixative.intValue());
			}
			if (b.qcpostedYN == null) {
				pstmt.setNull(23, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(23, b.qcpostedYN);
			}
			if (b.histoMinThicknessPfinCid == null) {
				pstmt.setNull(24, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(24, b.histoMinThicknessPfinCid);
			}
			if (b.histoMaxThicknessPfinCid == null) {
				pstmt.setNull(25, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(25, b.histoMaxThicknessPfinCid);
			}
			if (b.histoWidthAcrossPfinCid == null) {
				pstmt.setNull(26, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(26, b.histoWidthAcrossPfinCid);
			}
			if (b.histoReembedReasonCid == null) {
				pstmt.setNull(27, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(27, b.histoReembedReasonCid);
			}
			if (b.histoSubdividable == null) {
				pstmt.setNull(28, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(28, b.histoSubdividable);
			}
			if (b.histoNotes == null) {
				pstmt.setNull(29, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(29, b.histoNotes);
			}
			if (b.otherHistoReembedReason == null) {
				pstmt.setNull(30, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(30, b.otherHistoReembedReason);
			}
			if (b.paraffinFeatureCid == null) {
				pstmt.setNull(31, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(31, b.paraffinFeatureCid);
			}
			if (b.otherParaffinFeature == null) {
				pstmt.setNull(32, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(32, b.otherParaffinFeature);
			}
			if (b.receiptDate == null) {
				pstmt.setNull(33, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(33, b.receiptDate);
			}
			if (b.lastknownlocationid == null) {
				pstmt.setNull(34, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(34, b.lastknownlocationid);
			}
			if (b.importedYN == null) {
				pstmt.setNull(35, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(35, b.importedYN);
			}
			if (b.customerId == null) {
				pstmt.setNull(36, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(36, b.customerId);
			}
			if (b.sampleTypeCid == null) {
				pstmt.setNull(37, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(37, b.sampleTypeCid);
			}
			if (b.collection_datetime == null) {
				pstmt.setNull(38, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(38, b.collection_datetime);
			}
			if (b.preservation_datetime == null) {
				pstmt.setNull(39, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(39, b.preservation_datetime);
			}
			if (b.volume == null) {
				pstmt.setNull(40, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(40, b.volume);
			}
			if (b.collection_datetime_dpc == null) {
				pstmt.setNull(41, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(41, b.collection_datetime_dpc);
			}
			if (b.preservation_datetime_dpc == null) {
				pstmt.setNull(42, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(42, b.preservation_datetime_dpc);
			}
			if (b.bufferTypeCui == null) {
				pstmt.setNull(43, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(43, b.bufferTypeCui);
			}
			if (b.bufferTypeOther == null) {
				pstmt.setNull(44, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(44, b.bufferTypeOther);
			}
			if (b.totalNumOfCells == null) {
				pstmt.setNull(45, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(45, b.totalNumOfCells);
			}
			if (b.totalNumOfCellsExRepCui == null) {
				pstmt.setNull(46, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(46, b.totalNumOfCellsExRepCui);
			}
			if (b.cellsPerMl == null) {
				pstmt.setNull(47, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(47, b.cellsPerMl);
			}
			if (b.percentViability == null) {
				pstmt.setNull(48, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(48, b.percentViability.intValue());
			}
			if (b.source == null) {
				pstmt.setNull(49, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(49, b.source);
			}
			if (b.concentration == null) {
				pstmt.setNull(50, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(50, b.concentration);
			}
			if (b.yield == null) {
				pstmt.setNull(51, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(51, b.yield);
			}
			if (b.volumeUnitCui == null) {
				pstmt.setNull(52, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(52, b.volumeUnitCui);
			}
			if (b.volumeInUl == null) {
				pstmt.setNull(53, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(53, b.volumeInUl);
			}
			if (b.weight == null) {
				pstmt.setNull(54, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(54, b.weight);
			}
			if (b.weightInMg == null) {
				pstmt.setNull(55, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(55, b.weightInMg);
			}
			if (b.weightUnitCui == null) {
				pstmt.setNull(56, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(56, b.weightUnitCui);
			}
			if (b.sampleRegistrationForm == null) {
				pstmt.setNull(57, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(57, b.sampleRegistrationForm);
			}
			if (b.samplebox_box_barcode_id == null) {
				pstmt.setNull(58, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(58, b.samplebox_box_barcode_id);
			}
			if (b.asm_asm_id == null) {
				pstmt.setNull(59, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(59, b.asm_asm_id);
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
		SampleBean b = (SampleBean) eb;
		com.ardais.bigr.iltds.beans.SampleKey _primaryKey = new com.ardais.bigr.iltds.beans.SampleKey();
		_primaryKey.sample_barcode_id = b.sample_barcode_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.sample_barcode_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.sample_barcode_id);
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
		com.ardais.bigr.iltds.beans.SampleKey key = new com.ardais.bigr.iltds.beans.SampleKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.sample_barcode_id = resultSet.getString(1);
			return key;
		}
return null;
	}
	/**
	 * findSampleByAsm
	 */
	public EJSFinder findSampleByAsm(com.ardais.bigr.iltds.beans.AsmKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.SAMPLE_BARCODE_ID, T1.CELL_REF_LOCATION, T1.ASM_NOTE, T1.TYPE_OF_FIXATIVE, T1.ALLOCATION_IND, T1.BARCODE_SCAN_DATETIME, T1.ORPHAN_DATETIME, T1.ASM_POSITION, T1.ARDAIS_ACCT_KEY, T1.BORN_DATE, T1.SUBDIVISION_DATE, T1.PARENT_BARCODE_ID, T1.PULL_YN, T1.PULL_REASON, T1.RELEASED_YN, T1.DISCORDANT_YN, T1.PULL_DATE, T1.DI_MIN_THICKNESS_PFIN_CID, T1.DI_MAX_THICKNESS_PFIN_CID, T1.DI_WIDTH_ACROSS_PFIN_CID, T1.FORMAT_DETAIL_CID, T1.SAMPLE_SIZE_MEETS_SPECS, T1.TOTAL_HOURS_IN_FIXATIVE, T1.QCPOSTED_YN, T1.HISTO_MIN_THICKNESS_PFIN_CID, T1.HISTO_MAX_THICKNESS_PFIN_CID, T1.HISTO_WIDTH_ACROSS_PFIN_CID, T1.HISTO_REEMBED_REASON_CID, T1.HISTO_SUBDIVIDABLE, T1.HISTO_NOTES, T1.OTHER_HISTO_REEMBED_REASON, T1.PARAFFIN_FEATURE_CID, T1.OTHER_PARAFFIN_FEATURE, T1.RECEIPT_DATE, T1.LAST_KNOWN_LOCATION_ID, T1.IMPORTED_YN, T1.CUSTOMER_ID, T1.SAMPLE_TYPE_CID, T1.COLLECTION_DATETIME, T1.PRESERVATION_DATETIME, T1.VOLUME, T1.COLLECTION_DATETIME_DPC, T1.PRESERVATION_DATETIME_DPC, T1.BUFFER_TYPE_CUI, T1.BUFFER_TYPE_OTHER, T1.TOTAL_NUM_OF_CELLS, T1.TOTAL_NUM_OF_CELLS_EX_REP_CUI, T1.CELLS_PER_ML, T1.PERCENT_VIABILITY, T1.SOURCE, T1.CONCENTRATION, T1.YIELD, T1.VOLUME_UNIT_CUI, T1.VOLUME_UL, T1.WEIGHT, T1.WEIGHT_MG, T1.WEIGHT_UNIT_CUI, T1.SAMPLE_REGISTRATION_FORM, T1.BOX_BARCODE_ID, T1.ASM_ID FROM ILTDS_SAMPLE  T1 WHERE T1.ASM_ID = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.asm_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, inKey.asm_id);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findAll
	 */
	public EJSFinder findAll() throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" select  q1.\"SAMPLE_BARCODE_ID\",  q1.\"CELL_REF_LOCATION\",  q1.\"ASM_NOTE\",  q1.\"TYPE_OF_FIXATIVE\",  q1.\"ALLOCATION_IND\",  q1.\"BARCODE_SCAN_DATETIME\",  q1.\"ORPHAN_DATETIME\",  q1.\"ASM_POSITION\",  q1.\"ARDAIS_ACCT_KEY\",  q1.\"BORN_DATE\",  q1.\"SUBDIVISION_DATE\",  q1.\"PARENT_BARCODE_ID\",  q1.\"PULL_YN\",  q1.\"PULL_REASON\",  q1.\"RELEASED_YN\",  q1.\"DISCORDANT_YN\",  q1.\"PULL_DATE\",  q1.\"DI_MIN_THICKNESS_PFIN_CID\",  q1.\"DI_MAX_THICKNESS_PFIN_CID\",  q1.\"DI_WIDTH_ACROSS_PFIN_CID\",  q1.\"FORMAT_DETAIL_CID\",  q1.\"SAMPLE_SIZE_MEETS_SPECS\",  q1.\"TOTAL_HOURS_IN_FIXATIVE\",  q1.\"QCPOSTED_YN\",  q1.\"HISTO_MIN_THICKNESS_PFIN_CID\",  q1.\"HISTO_MAX_THICKNESS_PFIN_CID\",  q1.\"HISTO_WIDTH_ACROSS_PFIN_CID\",  q1.\"HISTO_REEMBED_REASON_CID\",  q1.\"HISTO_SUBDIVIDABLE\",  q1.\"HISTO_NOTES\",  q1.\"OTHER_HISTO_REEMBED_REASON\",  q1.\"PARAFFIN_FEATURE_CID\",  q1.\"OTHER_PARAFFIN_FEATURE\",  q1.\"RECEIPT_DATE\",  q1.\"LAST_KNOWN_LOCATION_ID\",  q1.\"IMPORTED_YN\",  q1.\"CUSTOMER_ID\",  q1.\"SAMPLE_TYPE_CID\",  q1.\"COLLECTION_DATETIME\",  q1.\"PRESERVATION_DATETIME\",  q1.\"VOLUME\",  q1.\"COLLECTION_DATETIME_DPC\",  q1.\"PRESERVATION_DATETIME_DPC\",  q1.\"BUFFER_TYPE_CUI\",  q1.\"BUFFER_TYPE_OTHER\",  q1.\"TOTAL_NUM_OF_CELLS\",  q1.\"TOTAL_NUM_OF_CELLS_EX_REP_CUI\",  q1.\"CELLS_PER_ML\",  q1.\"PERCENT_VIABILITY\",  q1.\"SOURCE\",  q1.\"CONCENTRATION\",  q1.\"YIELD\",  q1.\"VOLUME_UNIT_CUI\",  q1.\"VOLUME_UL\",  q1.\"WEIGHT\",  q1.\"WEIGHT_MG\",  q1.\"WEIGHT_UNIT_CUI\",  q1.\"SAMPLE_REGISTRATION_FORM\",  q1.\"BOX_BARCODE_ID\",  q1.\"ASM_ID\" from ILTDS_SAMPLE q1");
			Object convObj = null;
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findSampleBySamplebox
	 */
	public EJSFinder findSampleBySamplebox(com.ardais.bigr.iltds.beans.SampleboxKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.SAMPLE_BARCODE_ID, T1.CELL_REF_LOCATION, T1.ASM_NOTE, T1.TYPE_OF_FIXATIVE, T1.ALLOCATION_IND, T1.BARCODE_SCAN_DATETIME, T1.ORPHAN_DATETIME, T1.ASM_POSITION, T1.ARDAIS_ACCT_KEY, T1.BORN_DATE, T1.SUBDIVISION_DATE, T1.PARENT_BARCODE_ID, T1.PULL_YN, T1.PULL_REASON, T1.RELEASED_YN, T1.DISCORDANT_YN, T1.PULL_DATE, T1.DI_MIN_THICKNESS_PFIN_CID, T1.DI_MAX_THICKNESS_PFIN_CID, T1.DI_WIDTH_ACROSS_PFIN_CID, T1.FORMAT_DETAIL_CID, T1.SAMPLE_SIZE_MEETS_SPECS, T1.TOTAL_HOURS_IN_FIXATIVE, T1.QCPOSTED_YN, T1.HISTO_MIN_THICKNESS_PFIN_CID, T1.HISTO_MAX_THICKNESS_PFIN_CID, T1.HISTO_WIDTH_ACROSS_PFIN_CID, T1.HISTO_REEMBED_REASON_CID, T1.HISTO_SUBDIVIDABLE, T1.HISTO_NOTES, T1.OTHER_HISTO_REEMBED_REASON, T1.PARAFFIN_FEATURE_CID, T1.OTHER_PARAFFIN_FEATURE, T1.RECEIPT_DATE, T1.LAST_KNOWN_LOCATION_ID, T1.IMPORTED_YN, T1.CUSTOMER_ID, T1.SAMPLE_TYPE_CID, T1.COLLECTION_DATETIME, T1.PRESERVATION_DATETIME, T1.VOLUME, T1.COLLECTION_DATETIME_DPC, T1.PRESERVATION_DATETIME_DPC, T1.BUFFER_TYPE_CUI, T1.BUFFER_TYPE_OTHER, T1.TOTAL_NUM_OF_CELLS, T1.TOTAL_NUM_OF_CELLS_EX_REP_CUI, T1.CELLS_PER_ML, T1.PERCENT_VIABILITY, T1.SOURCE, T1.CONCENTRATION, T1.YIELD, T1.VOLUME_UNIT_CUI, T1.VOLUME_UL, T1.WEIGHT, T1.WEIGHT_MG, T1.WEIGHT_UNIT_CUI, T1.SAMPLE_REGISTRATION_FORM, T1.BOX_BARCODE_ID, T1.ASM_ID FROM ILTDS_SAMPLE  T1 WHERE T1.BOX_BARCODE_ID = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.box_barcode_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, inKey.box_barcode_id);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.iltds.beans.Sample findByPrimaryKey(com.ardais.bigr.iltds.beans.SampleKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Sample) home.activateBean(key);
	}
}
