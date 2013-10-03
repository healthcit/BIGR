package com.ardais.bigr.es.beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
/**
 * This is a Session Bean Class
 */
public class SequenceGenBean implements SessionBean {
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;

	/**
	 * ejbActivate method comment
	 */
	public void ejbActivate() throws java.rmi.RemoteException {
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException The exception description.
	 */
	public void ejbCreate() throws javax.ejb.CreateException, EJBException {
	}
	/**
	 * ejbPassivate method comment
	 */
	public void ejbPassivate() throws java.rmi.RemoteException {
	}
	/**
	 * ejbRemove method comment
	 */
	public void ejbRemove() throws java.rmi.RemoteException {
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2/21/01 10:16:53 AM)
	 * @return java.lang.String
	 * @param seqName java.lang.String
	 */
	public int getSeqNextVal(String seqName) throws ApiException {
		java.sql.Connection con = null;
    PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		int nextVal = -1;

		query = "SELECT " + seqName + ".NEXTVAL FROM DUAL";

		try {
      con = ApiFunctions.getDbConnection();
			pstmt = con.prepareStatement(query);
			rs = ApiFunctions.queryDb(pstmt, con);
			while (rs.next()) {
				nextVal = rs.getInt(1);
			}
		} catch (Exception e) {
			ApiFunctions.throwAsRuntimeException(e);
    } finally {
			ApiFunctions.close(con, pstmt, rs);
		}
    return nextVal;
	}
	/**
	 * getSessionContext method comment
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	/**
	 * setSessionContext method comment
	 * @param ctx javax.ejb.SessionContext
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException {
		mySessionCtx = ctx;
	}
}