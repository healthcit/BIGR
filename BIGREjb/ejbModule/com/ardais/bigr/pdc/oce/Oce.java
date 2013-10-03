package com.ardais.bigr.pdc.oce;
import com.ardais.bigr.pdc.javabeans.OceData;
import java.util.Map;
/**
 * Remote interface for Enterprise Bean: Oce
 */
public interface Oce extends javax.ejb.EJBObject {
	public OceData getOceData(OceData databean)
		throws java.rmi.RemoteException;
	public java.util.Map getTableColumnNames()
		throws java.rmi.RemoteException;
	public OceData updateOce(OceData databean)
		throws java.rmi.RemoteException;
	/**
	 * Insert the method's description here.
	 * Creation date: (7/26/01 1:57:59 PM)
	 * @param TableName java.lang.String
	 * @param ColumnName java.lang.String
	 * @param TypeCode java.lang.String
	 * @param WhereClause java.lang.String
	 * @param User java.lang.String
	 * @param OtherText java.lang.String
	 */
	public void createOce(
		String tableName,
		String columnName,
		String typeCode,
		String whereClause,
		String user,
		String otherText,
		String othertext_Column_Name)
		throws java.rmi.RemoteException;
	/**
	 * Updates status of row specified with Obsolete.
	 */

	public void updateStatus(
		String tableName,
		String columnName,
		String typeCode,
		String primaryKey,
		String user,
        String statusFlag)
		throws java.rmi.RemoteException;	
	/**
	     * Retrieves disease type, account for a path report specified by 
	     * whereClause and assigns these values to disease, account.
	     * 
	     * @param whereClause a string which uniquely identifies a row in
	     *         PDC_PATH_REPORT_SECTION table.
	     * @return Map the map of keys and values. 
	     */
	public Map retrieveCaseInfo(String whereClause)
		throws java.rmi.RemoteException;
}
