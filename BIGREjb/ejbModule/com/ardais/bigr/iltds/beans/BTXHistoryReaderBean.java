package com.ardais.bigr.iltds.beans;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.BtxHistoryObjectEncoding;
import com.ardais.bigr.btx.framework.BtxHistoryObjectParser;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryDisplayLine;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.security.SecurityInfo;

/**
 * @see BTXHistoryReader
 */
public class BTXHistoryReaderBean implements SessionBean {
  private javax.ejb.SessionContext mySessionCtx = null;
  private final static long serialVersionUID = 3206093459760846163L;

  // If you change this, you'll also need to change all the places in this
  // class where we use numeric positions in JDBC result sets and prepared
  // statements based on this.
  //
  private static final String COMMON_SELECT_LIST =
    "h.BTX_ID, "
      + "h.BTX_TYPE, "
      + "h.BTX_ARDAIS_USER_ID, "
      + "h.BTX_BEGIN_DATETIME, "
      + "h.BTX_END_DATETIME, "
      + "h.EXCEPTION_TEXT, "
      + "h.EXCEPTION_STACKTRACE, "
      + "h.BOX_BARCODE_ID, "
      + "h.BOX_CONTENTS, "
      + "h.ID_LIST_1, "
      + "h.ID_LIST_2, "
      + "h.BOX_LOCATION_1, "
      + "h.BOX_LOCATION_2, "
      + "h.ATTRIB_1, "
      + "h.ATTRIB_2, "
      + "h.ATTRIB_3, "
      + "h.ATTRIB_4, "
      + "h.ATTRIB_5, "
      + "h.ATTRIB_6, "
      + "h.ATTRIB_7, "
      + "h.ATTRIB_8, "
      + "h.ATTRIB_9, "
      + "h.ATTRIB_10, "
      + "h.ATTRIB_11, "
      + "h.ATTRIB_12, "
      + "h.ATTRIB_13, "
      + "h.ATTRIB_14, "
      + "h.ATTRIB_15, "
      + "h.ATTRIB_16, "
      + "h.ATTRIB_17, "
      + "h.ATTRIB_18, "
      + "h.ATTRIB_19, "
      + "h.ATTRIB_20, "
      + "h.CLOB_1, "
      + "h.CLOB_2, "
      + "h.HISTORY_OBJECT_ENCODING, "
      + "h.HISTORY_OBJECT, "
      + "loc1.LOCATION_ADDRESS_ID AS LOCATION_ADDRESS_ID_1, "
      + "loc1.ROOM_ID AS ROOM_ID_1, "
      + "loc1.UNIT_NAME AS UNIT_NAME_1, "
      + "loc1.DRAWER_ID AS DRAWER_ID_1, "
      + "loc1.SLOT_ID AS SLOT_ID_1, "
      + "loc1.STORAGE_TYPE AS STORAGE_TYPE_1, "
      + "loc1.LOCATION_NAME AS LOCATION_NAME_1, "
      + "loc2.LOCATION_ADDRESS_ID AS LOCATION_ADDRESS_ID_2, "
      + "loc2.ROOM_ID AS ROOM_ID_2, "
      + "loc2.UNIT_NAME AS UNIT_NAME_2, "
      + "loc2.DRAWER_ID AS DRAWER_ID_2, "
      + "loc2.SLOT_ID AS SLOT_ID_2, "
      + "loc2.STORAGE_TYPE AS STORAGE_TYPE_2, "
      + "loc2.LOCATION_NAME AS LOCATION_NAME_2";

  private static final String COMMON_FROM =
    "ILTDS_BTX_HISTORY h, ILTDS_BTX_INVOLVED_LOCATION loc1, ILTDS_BTX_INVOLVED_LOCATION loc2";

  private static final String COMMON_WHERE =
    "(h.BOX_LOCATION_1 = loc1.ID(+) AND h.BOX_LOCATION_2 = loc2.ID(+))";

  private static final String COMMON_ORDER_BY = "h.BTX_END_DATETIME DESC, h.BTX_ID DESC";

  /**
   * @see BTXHistoryReader#getHistoryDisplayLines(String, SecurityInfo)
   */
  public List getHistoryDisplayLines(String objectId, SecurityInfo securityInfo) {
    ArrayList list = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet resultSet = null;
    try {
      // Users without the PRIV_ICP_SUPERUSER privilege can only see transactions that were
      // performed by users in the same account as themselves.
      //
      boolean filterByAccount = (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER));
      boolean filterByObjectId = (!ApiFunctions.isEmpty(objectId));

      StringBuffer sqlText = new StringBuffer(2048);

      sqlText.append("select ");
      sqlText.append(COMMON_SELECT_LIST);
      sqlText.append("\nfrom ");
      sqlText.append(COMMON_FROM);
      if (filterByObjectId) {
        sqlText.append(", ILTDS_BTX_INVOLVED_OBJECT obj");
      }
      if (filterByAccount) {
        sqlText.append(", ES_ARDAIS_USER u");
      }
      sqlText.append("\nwhere ");
      sqlText.append(COMMON_WHERE);
      if (filterByObjectId) {
        sqlText.append(" AND h.btx_id = obj.btx_id\n AND obj.object_id = ?");
      }
      if (filterByAccount) {
        sqlText.append(" AND h.btx_ardais_user_id = u.ardais_user_id\n AND u.ardais_acct_key = ?");
      }
      sqlText.append("\norder by ");
      sqlText.append(COMMON_ORDER_BY);

      con = ApiFunctions.getDbConnection();

      pstmt = con.prepareStatement(sqlText.toString());

      int nextParamIndex = 1;

      if (filterByObjectId) {
        pstmt.setString(nextParamIndex, objectId);
        nextParamIndex++;
      }
      if (filterByAccount) {
        pstmt.setString(nextParamIndex, securityInfo.getAccount());
        nextParamIndex++;
      }

      resultSet = pstmt.executeQuery();

      BTXHistoryRecord history = new BTXHistoryRecord();
      BtxHistoryObjectParser historyObjectParser = new BtxHistoryObjectParser();

      while (resultSet.next()) {
        BTXDetails btxDetails =
          reconstituteFromCurrentResultSetPosition(
            resultSet,
            history,
            historyObjectParser,
            securityInfo);

        BTXHistoryDisplayLine displayLine = new BTXHistoryDisplayLine();
        displayLine.setTransactionId(btxDetails.getTransactionId());
        displayLine.setBTXType(btxDetails.getBTXType());
        displayLine.setUserId(btxDetails.getUserId());
        displayLine.setEndTimestamp(btxDetails.getEndTimestamp());
        displayLine.setDetailsAsHTML(btxDetails.getDetailsAsHTML());

        list.add(displayLine);
      }

      list.trimToSize();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, resultSet);
    }

    return list;
  }

  /**
   * Given a JDBC result set object that is positioned on a record that contains
   * information from both ILTDS_BTX_HISTORY and ILTDS_BTX_INVOLVED_LOCATION,
   * instantiate a BTXDetails object that represents the business transaction
   * described by that history record.
   * <p>
   * Beginning at position 1, the columns of the result set must be in this order:
   * <ol>
   * <li>ILTDS_BTX_HISTORY.BTX_ID</li>
   * <li>ILTDS_BTX_HISTORY.BTX_TYPE</li>
   * <li>ILTDS_BTX_HISTORY.BTX_ARDAIS_USER_ID</li>
   * <li>ILTDS_BTX_HISTORY.BTX_BEGIN_DATETIME</li>
   * <li>ILTDS_BTX_HISTORY.BTX_END_DATETIME</li>
   * <li>ILTDS_BTX_HISTORY.EXCEPTION_TEXT</li>
   * <li>ILTDS_BTX_HISTORY.EXCEPTION_STACKTRACE</li>
   * <li>ILTDS_BTX_HISTORY.BOX_BARCODE_ID</li>
   * <li>ILTDS_BTX_HISTORY.BOX_CONTENTS</li>
   * <li>ILTDS_BTX_HISTORY.ID_LIST_1</li>
   * <li>ILTDS_BTX_HISTORY.ID_LIST_2</li>
   * <li>ILTDS_BTX_HISTORY.BOX_LOCATION_1</li>
   * <li>ILTDS_BTX_HISTORY.BOX_LOCATION_2</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_1</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_2</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_3</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_4</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_5</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_6</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_7</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_8</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_9</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_10</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_11</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_12</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_13</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_14</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_15</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_16</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_17</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_18</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_19</li>
   * <li>ILTDS_BTX_HISTORY.ATTRIB_20</li>
   * <li>ILTDS_BTX_HISTORY.CLOB_1</li>
   * <li>ILTDS_BTX_HISTORY.CLOB_2</li>
   * <li>ILTDS_BTX_HISTORY.HISTORY_OBJECT_ENCODING</li>
   * <li>ILTDS_BTX_HISTORY.HISTORY_OBJECT</li>
   * <li>ILTDS_BTX_INVOLVED_LOCATION.LOCATION_ADDRESS_ID (for BOX_LOCATION_1)</li>
   * <li>ILTDS_BTX_INVOLVED_LOCATION.ROOM_ID (for BOX_LOCATION_1)</li>
   * <li>ILTDS_BTX_INVOLVED_LOCATION.UNIT_NAME (for BOX_LOCATION_1)</li>
   * <li>ILTDS_BTX_INVOLVED_LOCATION.DRAWER_ID (for BOX_LOCATION_1)</li>
   * <li>ILTDS_BTX_INVOLVED_LOCATION.SLOT_ID (for BOX_LOCATION_1)</li>
   * <li>ILTDS_BTX_INVOLVED_LOCATION.STORAGE_TYPE (for BOX_LOCATION_1)</li>
   * <li>ILTDS_BTX_INVOLVED_LOCATION.LOCATION_NAME (for BOX_LOCATION_1)</li>
   * <li>ILTDS_BTX_INVOLVED_LOCATION.LOCATION_ADDRESS_ID (for BOX_LOCATION_2)</li>
   * <li>ILTDS_BTX_INVOLVED_LOCATION.ROOM_ID (for BOX_LOCATION_2)</li>
   * <li>ILTDS_BTX_INVOLVED_LOCATION.UNIT_NAME (for BOX_LOCATION_2)</li>
   * <li>ILTDS_BTX_INVOLVED_LOCATION.DRAWER_ID (for BOX_LOCATION_2)</li>
   * <li>ILTDS_BTX_INVOLVED_LOCATION.SLOT_ID (for BOX_LOCATION_2)</li>
   * <li>ILTDS_BTX_INVOLVED_LOCATION.STORAGE_TYPE (for BOX_LOCATION_2)</li>
   * <li>ILTDS_BTX_INVOLVED_LOCATION.LOCATION_NAME (for BOX_LOCATION_2)</li>
   * </ol>
   *
   * @param resultSet the JDBC result set to read data from.
   * @param history a history-record object to use as a scratchpad.  Passing this
   *     in rather than creating it locally keeps us from creating a lot of throwaway
   *     objects when processing large result sets.
   * @param securityInfo the security credentials of the current user.
   * 
   * @return the instantiated BTXDetails object.
   */
  private BTXDetails reconstituteFromCurrentResultSetPosition(
    ResultSet resultSet,
    BTXHistoryRecord history,
    BtxHistoryObjectParser historyObjectParser,
    SecurityInfo securityInfo) {

    BTXDetails btxDetails = null;

    try {
      history.setTransactionId(resultSet.getLong(1));
      history.setBTXType(resultSet.getString(2));
      history.setUserId(resultSet.getString(3));
      history.setBeginTimestamp(resultSet.getTimestamp(4));
      history.setEndTimestamp(resultSet.getTimestamp(5));
      history.setExceptionText(resultSet.getString(6));
      history.setExceptionStackTrace(resultSet.getString(7));
      history.setBox(
        new BoxDto(resultSet.getString(8), ApiFunctions.getStringFromClob(resultSet.getClob(9))));

      history.setIdList1(new IdList(ApiFunctions.getStringFromClob(resultSet.getClob(10))));
      history.setIdList2(new IdList(ApiFunctions.getStringFromClob(resultSet.getClob(11))));

      BigDecimal boxLoc1RecordId = resultSet.getBigDecimal(12);
      BigDecimal boxLoc2RecordId = resultSet.getBigDecimal(13);

      history.setAttrib1(resultSet.getString(14));
      history.setAttrib2(resultSet.getString(15));
      history.setAttrib3(resultSet.getString(16));
      history.setAttrib4(resultSet.getString(17));
      history.setAttrib5(resultSet.getString(18));
      history.setAttrib6(resultSet.getString(19));
      history.setAttrib7(resultSet.getString(20));
      history.setAttrib8(resultSet.getString(21));
      history.setAttrib9(resultSet.getString(22));
      history.setAttrib10(resultSet.getString(23));
      history.setAttrib11(resultSet.getString(24));
      history.setAttrib12(resultSet.getString(25));
      history.setAttrib13(resultSet.getString(26));
      history.setAttrib14(resultSet.getString(27));
      history.setAttrib15(resultSet.getString(28));
      history.setAttrib16(resultSet.getString(29));
      history.setAttrib17(resultSet.getString(30));
      history.setAttrib18(resultSet.getString(31));
      history.setAttrib19(resultSet.getString(32));
      history.setAttrib20(resultSet.getString(33));

      history.setClob1(ApiFunctions.getStringFromClob(resultSet.getClob(34)));
      history.setClob2(ApiFunctions.getStringFromClob(resultSet.getClob(35)));

      {
        String historyObjectEncodingScheme = resultSet.getString(36);
        String historyObjectString = ApiFunctions.getStringFromClob(resultSet.getClob(37));

        BtxHistoryObjectEncoding historyObjectEncoding =
          new BtxHistoryObjectEncoding(historyObjectEncodingScheme, historyObjectString);

        history.setHistoryObject(historyObjectParser.performParse(historyObjectEncoding));
      }

      if (boxLoc1RecordId == null) {
        history.setBoxLocation1(null);
      }
      else {
        BTXBoxLocation boxLoc = new BTXBoxLocation();

        boxLoc.setLocationAddressID(resultSet.getString(38));
        boxLoc.setRoomID(resultSet.getString(39));
        boxLoc.setUnitName(resultSet.getString(40));
        boxLoc.setDrawerID(resultSet.getString(41));
        boxLoc.setSlotID(resultSet.getString(42));
        boxLoc.setStorageTypeDesc(resultSet.getString(43));
        boxLoc.setLocationAddressName(resultSet.getString(44));

        history.setBoxLocation1(boxLoc);
      }

      if (boxLoc2RecordId == null) {
        history.setBoxLocation2(null);
      }
      else {
        BTXBoxLocation boxLoc = new BTXBoxLocation();

        boxLoc.setLocationAddressID(resultSet.getString(45));
        boxLoc.setRoomID(resultSet.getString(46));
        boxLoc.setUnitName(resultSet.getString(47));
        boxLoc.setDrawerID(resultSet.getString(48));
        boxLoc.setSlotID(resultSet.getString(49));
        boxLoc.setStorageTypeDesc(resultSet.getString(50));
        boxLoc.setLocationAddressName(resultSet.getString(51));

        history.setBoxLocation2(boxLoc);
      }

      btxDetails = BTXDetails.getInstance(history, securityInfo);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    return btxDetails;
  }

  public void ejbActivate() throws java.rmi.RemoteException {
  }

  public void ejbCreate() throws javax.ejb.CreateException, EJBException {
  }

  public void ejbPassivate() throws java.rmi.RemoteException {
  }

  public void ejbRemove() throws java.rmi.RemoteException {
  }

  /**
   * @return javax.ejb.SessionContext
   */
  public javax.ejb.SessionContext getSessionContext() {
    return mySessionCtx;
  }

  public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException {
    mySessionCtx = ctx;
  }
}
