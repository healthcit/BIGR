package com.ardais.bigr.lims.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPSlideBean_cc534205
 */
public class EJSJDBCPersisterCMPSlideBean_cc534205 extends EJSJDBCPersister implements com.ardais.bigr.lims.beans.EJSFinderSlideBean {
	private static final String _createString = "INSERT INTO LIMS_SLIDE (SLIDE_ID, SECTION_NUMBER, CREATE_DATE, DESTROY_DATE, SECTION_LEVEL, CURRENT_LOCATION, SAMPLE_BARCODE_ID, CREATE_USER, SECTION_PROC) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM LIMS_SLIDE  WHERE SLIDE_ID = ?";
	private static final String _storeString = "UPDATE LIMS_SLIDE  SET SECTION_NUMBER = ?, CREATE_DATE = ?, DESTROY_DATE = ?, SECTION_LEVEL = ?, CURRENT_LOCATION = ?, SAMPLE_BARCODE_ID = ?, CREATE_USER = ?, SECTION_PROC = ? WHERE SLIDE_ID = ?";
	private static final String _loadString = " SELECT T1.SLIDE_ID, T1.SECTION_NUMBER, T1.CREATE_DATE, T1.DESTROY_DATE, T1.SECTION_LEVEL, T1.CURRENT_LOCATION, T1.SAMPLE_BARCODE_ID, T1.CREATE_USER, T1.SECTION_PROC FROM LIMS_SLIDE  T1 WHERE T1.SLIDE_ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPSlideBean_cc534205
	 */
	public EJSJDBCPersisterCMPSlideBean_cc534205() throws java.rmi.RemoteException {
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
		SlideBean b = (SlideBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.slideId == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.slideId);
			}
			pstmt.setInt(2, b.sectionNumber);
			if (b.createDate == null) {
				pstmt.setNull(3, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(3, b.createDate);
			}
			if (b.destroyDate == null) {
				pstmt.setNull(4, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(4, b.destroyDate);
			}
			pstmt.setInt(5, b.sectionLevel);
			if (b.currentLocation == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.currentLocation);
			}
			if (b.sampleBarcodeId == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.sampleBarcodeId);
			}
			if (b.createUser == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.createUser);
			}
			if (b.sectionProc == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.sectionProc);
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
		SlideBean b = (SlideBean) eb;
		java.lang.String slideId = (java.lang.String)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.slideId = slideId;
		b.sectionNumber = resultSet.getInt(2);
		b.createDate = resultSet.getTimestamp(3);
		b.destroyDate = resultSet.getTimestamp(4);
		b.sectionLevel = resultSet.getInt(5);
		b.currentLocation = resultSet.getString(6);
		b.sampleBarcodeId = resultSet.getString(7);
		b.createUser = resultSet.getString(8);
		b.sectionProc = resultSet.getString(9);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		SlideBean b = (SlideBean) eb;
		java.lang.String slideId = (java.lang.String)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (slideId == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, slideId);
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
		SlideBean b = (SlideBean) eb;
		java.lang.String slideId;
		slideId = b.slideId;
		java.lang.String _primaryKey = slideId;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		SlideBean b = (SlideBean) eb;
		java.lang.String slideId;
		slideId = b.slideId;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (slideId == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, slideId);
			}
			pstmt.setInt(1, b.sectionNumber);
			if (b.createDate == null) {
				pstmt.setNull(2, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(2, b.createDate);
			}
			if (b.destroyDate == null) {
				pstmt.setNull(3, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(3, b.destroyDate);
			}
			pstmt.setInt(4, b.sectionLevel);
			if (b.currentLocation == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.currentLocation);
			}
			if (b.sampleBarcodeId == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.sampleBarcodeId);
			}
			if (b.createUser == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.createUser);
			}
			if (b.sectionProc == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.sectionProc);
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
		SlideBean b = (SlideBean) eb;
		java.lang.String slideId;
		slideId = b.slideId;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (slideId == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, slideId);
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
		java.lang.String slideId;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			slideId = resultSet.getString(1);
			return slideId;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.lims.beans.Slide findByPrimaryKey(java.lang.String primaryKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.lims.beans.Slide) home.activateBean(primaryKey);
	}
}
