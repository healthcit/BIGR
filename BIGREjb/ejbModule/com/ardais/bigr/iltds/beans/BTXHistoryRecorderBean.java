package com.ardais.bigr.iltds.beans;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Iterator;
import java.util.Set;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.TemporaryClob;
import com.ardais.bigr.btx.framework.BtxHistoryObjectEncoding;
import com.ardais.bigr.btx.framework.BtxHistoryObjectUtils;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;

/**
 * @see BTXHistoryRecorder
 */
public class BTXHistoryRecorderBean implements SessionBean {

  // These are the maximum lengths that can be stored in the
  // ILTDS_BTX_HISTORY.EXCEPTION_TEXT and .EXCEPTION_STACKTRACE columns.
  // We use these to truncate the values if necessary before inserting
  // them into database records.
  //
  private static final int COL_LENGTH_EXCEPTION_TEXT = 255;
  private static final int COL_LENGTH_EXCEPTION_STACKTRACE = 4000;

  private javax.ejb.SessionContext mySessionCtx = null;
  private final static long serialVersionUID = 3206093459760846163L;

  /**
   * @see BTXHistoryRecorder#record(BTXDetails)
   */
  public BTXDetails record(BTXDetails btxDetails) {
    final String INSERT_HISTORY_NO_BOXLOC_SQL = "{ call btx.insert_history_no_boxloc(?," +
      // btx_id OUT parameter
    "?,?,?,?,?,?," + // properties common to all transaction types
    "?,?," + // box id and contents
    "?,?," + // id lists 1-2
    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," + // attrib 1-20
    "?,?," + // clobs 1-2
  "?,?) }"; // history object encoding and history object

    final String INSERT_HISTORY_WITH_BOXLOC_SQL = "{ call btx.insert_history_with_boxloc(?," +
      // btx_id OUT parameter
    "?,?,?,?,?,?," + // properties common to all transaction types
    "?,?," + // box id and contents
    "?,?," + // id lists 1-2
    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," + // attrib 1-20
    "?,?," + // clobs 1-2
    "?,?," + // history object encoding and history object
    "?,?,?,?,?,?,?,?," + // box location 1
  "?,?,?,?,?,?,?,?) }"; // box location 2

    Connection con = null;
    CallableStatement cstmt = null;
    PreparedStatement pstmt = null;
    TemporaryClob idlistClob1 = null;
    TemporaryClob idlistClob2 = null;
    TemporaryClob boxContentsClob = null;
    TemporaryClob clob1 = null;
    TemporaryClob clob2 = null;
    TemporaryClob historyObjectClob = null;
    try {
      btxDetails.setEndTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));

      BTXHistoryRecord history = btxDetails.describeAsHistoryRecord();

      BTXBoxLocation boxLoc1 = history.getBoxLocation1();
      BTXBoxLocation boxLoc2 = history.getBoxLocation2();
      boolean hasBoxLocations = ((boxLoc1 != null) || (boxLoc2 != null));

      String sqlText =
        (hasBoxLocations ? INSERT_HISTORY_WITH_BOXLOC_SQL : INSERT_HISTORY_NO_BOXLOC_SQL);

      // Truncate exceptionText if necessary so that it will fit in
      // the database column.
      //
      String exceptionText = history.getExceptionText();
      // If the exception text is empty, make sure a null is what gets
      // written to the database, not an empty string.
      //
      if (ApiFunctions.isEmpty(exceptionText)) {
        exceptionText = null;
      }
      if (exceptionText != null && exceptionText.length() > COL_LENGTH_EXCEPTION_TEXT) {
        exceptionText = exceptionText.substring(0, COL_LENGTH_EXCEPTION_TEXT - 3) + "...";
      }

      // Truncate exceptionStackTrace if necessary so that it will
      // fit in the database column.
      //
      String exceptionStackTrace = history.getExceptionStackTrace();
      // If the exception stack trace is empty, make sure a null is what
      // gets written to the database, not an empty string.
      //
      if (ApiFunctions.isEmpty(exceptionStackTrace)) {
        exceptionStackTrace = null;
      }
      if (exceptionStackTrace != null
        && exceptionStackTrace.length() > COL_LENGTH_EXCEPTION_STACKTRACE) {
        exceptionStackTrace =
          exceptionStackTrace.substring(0, COL_LENGTH_EXCEPTION_STACKTRACE - 3) + "...";
      }

      con = ApiFunctions.getDbConnection();

      // needed because we use addBatch/executeBatch later.
      //
      con.setAutoCommit(false);

      cstmt = con.prepareCall(sqlText);
      cstmt.registerOutParameter(1, Types.BIGINT);

      cstmt.setTimestamp(2, history.getEndTimestamp());
      cstmt.setString(3, history.getBTXType());
      cstmt.setString(4, history.getUserId());
      cstmt.setTimestamp(5, history.getBeginTimestamp());
      cstmt.setString(6, exceptionText);
      cstmt.setString(7, exceptionStackTrace);

      boxContentsClob = new TemporaryClob(con, history.getPackedBoxContents());

      cstmt.setString(8, history.getBoxId());
      cstmt.setClob(9, boxContentsClob.getSQLClob());

      idlistClob1 = new TemporaryClob(con, history.getPackedIdList1());
      idlistClob2 = new TemporaryClob(con, history.getPackedIdList2());

      cstmt.setClob(10, idlistClob1.getSQLClob());
      cstmt.setClob(11, idlistClob2.getSQLClob());

      cstmt.setString(12, history.getAttrib1());
      cstmt.setString(13, history.getAttrib2());
      cstmt.setString(14, history.getAttrib3());
      cstmt.setString(15, history.getAttrib4());
      cstmt.setString(16, history.getAttrib5());
      cstmt.setString(17, history.getAttrib6());
      cstmt.setString(18, history.getAttrib7());
      cstmt.setString(19, history.getAttrib8());
      cstmt.setString(20, history.getAttrib9());
      cstmt.setString(21, history.getAttrib10());
      cstmt.setString(22, history.getAttrib11());
      cstmt.setString(23, history.getAttrib12());
      cstmt.setString(24, history.getAttrib13());
      cstmt.setString(25, history.getAttrib14());
      cstmt.setString(26, history.getAttrib15());
      cstmt.setString(27, history.getAttrib16());
      cstmt.setString(28, history.getAttrib17());
      cstmt.setString(29, history.getAttrib18());
      cstmt.setString(30, history.getAttrib19());
      cstmt.setString(31, history.getAttrib20());

      clob1 = new TemporaryClob(con, history.getClob1());
      clob2 = new TemporaryClob(con, history.getClob2());

      cstmt.setClob(32, clob1.getSQLClob());
      cstmt.setClob(33, clob2.getSQLClob());

      {
        BtxHistoryObjectEncoding historyObjectEncoding =
          BtxHistoryObjectUtils.encode(history.getHistoryObject());
        historyObjectClob = new TemporaryClob(con, historyObjectEncoding.getEncodedObject());

        cstmt.setString(34, historyObjectEncoding.getEncodingScheme());
        cstmt.setClob(35, historyObjectClob.getSQLClob());
      }

      if (hasBoxLocations) {
        cstmt.setString(36, ((boxLoc1 != null) ? "Y" : "N"));
        BTXBoxLocation boxLoc = ((boxLoc1 != null) ? boxLoc1 : new BTXBoxLocation());
        setLocationAddressNameWhenEmpty(boxLoc);
        cstmt.setString(37, boxLoc.getLocationAddressID());
        cstmt.setString(38, boxLoc.getRoomID());
        cstmt.setString(39, boxLoc.getUnitName());
        cstmt.setString(40, boxLoc.getDrawerID());
        cstmt.setString(41, boxLoc.getSlotID());
        cstmt.setString(42, boxLoc.getStorageTypeDesc());
        cstmt.setString(43, boxLoc.getLocationAddressName());

        cstmt.setString(44, ((boxLoc2 != null) ? "Y" : "N"));
        boxLoc = ((boxLoc2 != null) ? boxLoc2 : new BTXBoxLocation());
        setLocationAddressNameWhenEmpty(boxLoc);
        cstmt.setString(45, boxLoc.getLocationAddressID());
        cstmt.setString(46, boxLoc.getRoomID());
        cstmt.setString(47, boxLoc.getUnitName());
        cstmt.setString(48, boxLoc.getDrawerID());
        cstmt.setString(49, boxLoc.getSlotID());
        cstmt.setString(50, boxLoc.getStorageTypeDesc());
        cstmt.setString(51, boxLoc.getLocationAddressName());
      }

      cstmt.execute();

      // Get the transaction id that was returned from the stored
      // procedure call and set it into btxDetails.  Get the transaction
      // end timestamp that was returned from the stored procedure call
      // and store it into btxDetails as well.
      //
      long btx_id = cstmt.getLong(1);
      btxDetails.setTransactionId(btx_id);

      cstmt.close();
      cstmt = null;

      // Record the set of objects that are involved in this transaction.
      //
      {
        sqlText =
          "insert into iltds_btx_involved_object (btx_id, object_id, direct) values (?,?,'Y')";

        pstmt = con.prepareStatement(sqlText);

        Set involvedObjects = btxDetails.getDirectlyInvolvedObjects();

        pstmt.setLong(1, btx_id);

        Iterator iter = involvedObjects.iterator();
        while (iter.hasNext()) {
          String objectId = (String) iter.next();
          pstmt.setString(2, objectId);
          pstmt.addBatch();
        }

        pstmt.executeBatch();

        pstmt.close();
        pstmt = null;
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(boxContentsClob, con);
      ApiFunctions.close(idlistClob1, con);
      ApiFunctions.close(idlistClob2, con);
      ApiFunctions.close(clob1, con);
      ApiFunctions.close(clob2, con);
      ApiFunctions.close(historyObjectClob, con);
      ApiFunctions.close(pstmt);
      ApiFunctions.close(cstmt);
      ApiFunctions.close(con);
    }

    return btxDetails;
  }

  /**
   * If the supplied box location object has a location id but no location
   * name, attempt to look up its name and set the object's location name
   * field.
   * 
   * @param boxLocation the box location object.
   */
  private static void setLocationAddressNameWhenEmpty(BTXBoxLocation boxLocation) {
    if (boxLocation == null) {
      return;
    }

    String locAddressName = boxLocation.getLocationAddressName();

    if (ApiFunctions.isEmpty(locAddressName)) {
      String locAddressID = boxLocation.getLocationAddressID();

      if (!ApiFunctions.isEmpty(locAddressID)) {
        try {
          GeolocationAccessBean geoLoc =
            new GeolocationAccessBean(new GeolocationKey((String) locAddressID));
          boxLocation.setLocationAddressName(geoLoc.getLocation_name());
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }
      }
    }
  }

  public void ejbActivate() throws java.rmi.RemoteException {
  }

  public void ejbCreate() throws javax.ejb.CreateException, EJBException {
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
