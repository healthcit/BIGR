package com.ardais.bigr.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.CreateException;
import javax.ejb.SessionContext;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;

/**
 * Bean implementation class for Enterprise Bean: SequenceGenerator
 */
public class SequenceGeneratorBean implements javax.ejb.SessionBean {

  private SessionContext mySessionCtx;

  /**
   * getSessionContext
   */
  public SessionContext getSessionContext() {
    return mySessionCtx;
  }
  /**
   * setSessionContext
   */
  public void setSessionContext(SessionContext ctx) {
    mySessionCtx = ctx;
  }
  /**
   * ejbCreate
   */
  public void ejbCreate() throws CreateException {
  }
  /**
   * ejbActivate
   */
  public void ejbActivate() {
  }
  /**
   * ejbPassivate
   */
  public void ejbPassivate() {
  }
  /**
   * ejbRemove
   */
  public void ejbRemove() {
  }

  /**
   * 
   * @param seqName String
   * @return Integer
   */
  public Integer getSeqNextVal(String seqName) throws ApiException {
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
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return new Integer(nextVal);
  }
}
