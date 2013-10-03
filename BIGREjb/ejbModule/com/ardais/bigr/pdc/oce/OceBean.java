package com.ardais.bigr.pdc.oce;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.pdc.javabeans.OceData;
import com.ardais.bigr.pdc.javabeans.OceRowData;
import com.ardais.bigr.pdc.oce.util.OceUtil;

/**
 * Bean implementation class for Enterprise Bean: Oce
 */
public class OceBean implements javax.ejb.SessionBean {
  private javax.ejb.SessionContext mySessionCtx;
  /**
   * getSessionContext
   */
  public javax.ejb.SessionContext getSessionContext() {
    return mySessionCtx;
  }
  /**
   * setSessionContext
   */
  public void setSessionContext(javax.ejb.SessionContext ctx) {
    mySessionCtx = ctx;
  }
  /**
   * ejbActivate
   */
  public void ejbActivate() {
  }
  /**
   * ejbCreate
   */
  public void ejbCreate() throws javax.ejb.CreateException {
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
   * Returns <code>OceData</code> databean which contains query results as a list 
   * for the values specified in databean.
   * 
   * @param databean a <code>OceData</code> databean containing all the
   * required fields
   * @return OceData a <code>OceData</code> containing all required fields 
   * passed in and query results as a list.
   */
  public OceData getOceData(OceData databean) {
    List list = new ArrayList();
    StringBuffer mySQL = new StringBuffer(128);
    String listOrder = null;
    String whereClause;
    ResultSet resultSet = null;
    PreparedStatement pStmt = null;
    Connection con = ApiFunctions.getDbConnection();
    try {
      if (ApiFunctions.safeEquals("Asc", databean.getListOrder())) {
        listOrder = "ABSTRACT_DATE ASC";
      }
      else if (ApiFunctions.safeEquals("Dsc", databean.getListOrder())) {
        listOrder = "ABSTRACT_DATE DESC";
      }
      else if (ApiFunctions.safeEquals("Alpha", databean.getListOrder())) {
        listOrder = "UPPER(TRIM(OTHER_TEXT))";
      }
      if (ApiFunctions.safeEquals("A", databean.getStatus())) {
        whereClause =
          "WHERE TABLE_NAME='"
            + databean.getTableName()
            + "' AND TYPE_CODE='"
            + databean.getAttribute()
            + "'";
      }
      else if (ApiFunctions.safeEquals("NHC", databean.getStatus())){
        whereClause =
          "WHERE TABLE_NAME='"
            + databean.getTableName()
            + "' AND TYPE_CODE='"
            + databean.getAttribute()
            + "' AND (status_FLAG='N' OR STATUS_FLAG='H' OR STATUS_FLAG='C')";
      }
      else {
        whereClause =
          "WHERE TABLE_NAME='"
            + databean.getTableName()
            + "' AND TYPE_CODE='"
            + databean.getAttribute()
            + "' AND status_FLAG='"
            + databean.getStatus()
            + "'";
      }
      
      //MR7413
      //if a start date has been specified, put it into the query
      if (!ApiFunctions.isEmpty(databean.getStartDate())) {
        whereClause = whereClause + " AND ABSTRACT_DATE >= to_date('" + databean.getStartDate() + " 00:00:00', 'MM/DD/YYYY HH24:MI:SS')";
      }
      //if an end date has been specified, put it into the query
      if (!ApiFunctions.isEmpty(databean.getEndDate())) {
        whereClause = whereClause + " AND ABSTRACT_DATE <= to_date('" + databean.getEndDate() + " 23:59:59', 'MM/DD/YYYY HH24:MI:SS')";
      }
      
      //tack on the order by clause
      whereClause = whereClause + " ORDER BY " + listOrder; 

      mySQL.append("SELECT OTHER_LINE_ID, OTHER_TEXT, STATUS_FLAG, ");
      mySQL.append("EDIT_TEXT, WHERE_CLAUSE FROM ARD_OTHER_CODE_EDITS ");
      mySQL.append(whereClause);
      pStmt = con.prepareStatement(mySQL.toString());
      resultSet = pStmt.executeQuery();
      while (resultSet.next()) {
        OceRowData rowData =
          new OceRowData(
            resultSet.getString("OTHER_LINE_ID"),
            resultSet.getString("OTHER_TEXT"),
            OceUtil.lookupOceConstant(resultSet.getString("STATUS_FLAG")));
        rowData.setFullySpecifiedName(resultSet.getString("EDIT_TEXT"));
        rowData.setWhereClause(resultSet.getString("WHERE_CLAUSE"));
        list.add(rowData);
      }
      databean.setList(list);
    }
    catch (SQLException SQLex) {
      ApiLogger.log(SQLex);
      throw new ApiException(SQLex.getMessage());
    }
    finally {
      ApiFunctions.close(resultSet);
      ApiFunctions.close(pStmt);
      ApiFunctions.close(con);
    }
    return databean;
  }
  /**
   * Returns all distinct table names and column names in ARD_OTHER_CODE_EDITS
   * table.
   * @return <code>Map</code> a Map contains table names as keys and list of
   * column names as values.
   */
  public Map getTableColumnNames() throws com.ardais.bigr.api.ApiException {
    List columns = null;
    ResultSet rs = null;
    String tableName = null;
    PreparedStatement pStmt = null;
    Map data = new HashMap();
    StringBuffer mySQL = new StringBuffer(256);
    Connection con = ApiFunctions.getDbConnection();

    try {
      mySQL.append("SELECT distinct a.table_name, b.lookup_cd_desc, a.type_code  ");
      mySQL.append("FROM   ard_other_code_edits a, ard_lookup_all b ");
      mySQL.append("WHERE  a.type_code = b.lookup_cd AND	   b.category = 'TYPE_CODE' ");
      mySQL.append("order by table_name, lookup_cd_desc");
      pStmt = con.prepareStatement(mySQL.toString());
      rs = pStmt.executeQuery();
      while (rs.next()) {
        if ((tableName == null) || (!rs.getString("table_name").equals(tableName))) {
          if (columns != null)
            data.put(tableName, columns);
          tableName = rs.getString("table_name");
          columns = new ArrayList();
        }
        columns.add(rs.getString("type_code") + "/" + rs.getString("lookup_cd_desc"));
      }

      if ((tableName != null) && (columns != null))
        data.put(tableName, columns);

    }
    catch (SQLException SQLex) {
      throw new ApiException(SQLex.getMessage());

    }
    finally {
      ApiFunctions.close(con, pStmt, rs);
    }
    return data;
  }
  /**
   * Updates ARD_OTHER_CODE_EDITS table with the values specified in a 
   * <code>OceData</code> databean.
   * 
   * @param <code>OceData</code>.
   * @return <code>OceData</code> a databean which contains results of update.
   */
  public OceData updateOce(OceData databean) {
    List list = databean.getList();
    List summary = new ArrayList();
    List peIdList = null;
    Connection con = ApiFunctions.getDbConnection();
    try {

      /* For every OceRowData passed in via the bean, 
       * 1) figure out what row (or rows, if the rowData is cloned) need to be updated in the 
       *    ARD_OTHER_CODE_EDITS table, and in the case of an update, in the table containing 
       *    the "other" text as well. 
       * 2) update the row(s) 
       */
      for (int i = 0; i < list.size(); i++) {
        OceRowData rowData = (OceRowData) list.get(i);
        StringBuffer mySql = new StringBuffer(256);
        int success = 0;
        String tableName = null;
        String columnName = null;
        String otherTextColumnName = null;
        String whereClause = null;
        String otherText = null;
        String otherLineId = null;

        /* build the sql statement to get the row(s) from ARD_OTHER_CODE_EDITS that need to be 
         * updated. Both cloned and nonCloned updates share a select and from clause, so add those
         * to the query */
        mySql.append("SELECT TABLE_NAME, COLUMN_NAME, OTHER_TEXT_COLUMN_NAME, WHERE_CLAUSE, ");
        mySql.append("OTHER_TEXT, OTHER_LINE_ID FROM ARD_OTHER_CODE_EDITS ");

        /* if this update is cloned, we need to get the rows that have the same tablename, 
         * typecode, and status as the databean passed in, and that have the same "other" text 
         * (ignoring leading/trailing spaces and capitalization) as the rowData. Although we could use
         * the otherText attribute on the rowData, it's safer to get this value from the database (just in
         * case something is funny with the otherText value passed in.  I saw this once with an otherText
         * value that contained a double quote.  That problem is fixed, but this way is just safer).*/
        if (rowData.isClone()) {
          mySql.append("WHERE TABLE_NAME = ? AND TYPE_CODE = ? ");
          //if the status is the not fixed/hold/concept indicator, include all 3 statuses.
          //otherwise include the single specified status
          if (OceUtil.OCE_STATUS_NOTFIXED_HOLD_CONCEPT_IND.equals(databean.getStatus())) {
            mySql.append("AND (STATUS_FLAG = 'N' OR STATUS_FLAG = 'H' OR STATUS_FLAG = 'C') ");
          }
          else {
            mySql.append("AND STATUS_FLAG = ? ");
          }
          mySql.append("AND UPPER(TRIM(OTHER_TEXT)) = ");
          mySql.append("UPPER(TRIM((SELECT OTHER_TEXT FROM ARD_OTHER_CODE_EDITS WHERE OTHER_LINE_ID = ?)))");
        }
        /* if this update is not cloned, we need to select the row in the table that has the line id
         * specified in the rowData. */
        else {
          mySql.append("WHERE OTHER_LINE_ID = ?");
        }

        PreparedStatement outerStmt = con.prepareStatement(mySql.toString());

        /* insert the parameters into the sql. */
        if (rowData.isClone()) {
          outerStmt.setString(1, databean.getTableName());
          outerStmt.setString(2, databean.getAttribute());
          if (OceUtil.OCE_STATUS_NOTFIXED_HOLD_CONCEPT_IND.equals(databean.getStatus())) {
            //the 3 status values are already in the query so the next parameter is #3
            outerStmt.setString(3, rowData.getLineId());
          }
          else {
            outerStmt.setString(3, databean.getStatus());
            outerStmt.setString(4, rowData.getLineId());
          }
        }
        else {
          outerStmt.setString(1, rowData.getLineId());
        }

        /* execute the query */
        ResultSet resultSet = outerStmt.executeQuery();

        /* Iterate over the rows returned from the query, and update the appropriate database 
         * tables. */
        while (resultSet.next()) {
          tableName = resultSet.getString("TABLE_NAME");
          columnName = resultSet.getString("COLUMN_NAME");
          otherTextColumnName = resultSet.getString("OTHER_TEXT_COLUMN_NAME");
          whereClause = resultSet.getString("WHERE_CLAUSE");
          otherText = resultSet.getString("OTHER_TEXT");
          otherLineId = resultSet.getString("OTHER_LINE_ID");

          mySql = new StringBuffer(128);

          /* If the user is updating the row to have a status of Hold, Inappropriate, or Concept,
           * the only table that needs to be updated is the ARD_OTHER_CODE_EDITS table.  Do
           * the update and add the results to the summary data this method returns. */
          if ((ApiFunctions.safeEquals(OceUtil.OCE_STATUS_HOLD_IND, rowData.getStatus()))
            || (ApiFunctions.safeEquals(OceUtil.OCE_STATUS_INAPP_IND, rowData.getStatus()))
            || (ApiFunctions.safeEquals(OceUtil.OCE_STATUS_CONCEPT_IND, rowData.getStatus()))) {
            mySql.append("UPDATE ARD_OTHER_CODE_EDITS SET STATUS_FLAG = ?, ");
            mySql.append("EDIT_USER = ?, EDIT_DATE = ? WHERE OTHER_LINE_ID = ?");
            PreparedStatement updateStmt = con.prepareStatement(mySql.toString());
            updateStmt.setString(1, rowData.getStatus());
            updateStmt.setString(2, databean.getUser());
            updateStmt.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            updateStmt.setString(4, otherLineId);

            success = updateStmt.executeUpdate();
            updateStmt.close();
            if (success > 0) {
              if (OceUtil.OCE_STATUS_HOLD_IND.equals(rowData.getStatus())) {
                summary.add(
                  new OceRowData(
                    otherText,
                    OceUtil.lookupOceConstant(OceUtil.OCE_STATUS_HOLD_IND)));
              }
              else if (OceUtil.OCE_STATUS_INAPP_IND.equals(rowData.getStatus())) {
                summary.add(
                  new OceRowData(
                    otherText,
                    OceUtil.lookupOceConstant(OceUtil.OCE_STATUS_INAPP_IND)));
              }
              else if (OceUtil.OCE_STATUS_CONCEPT_IND.equals(rowData.getStatus())) {
                summary.add(
                  new OceRowData(
                    otherText,
                    OceUtil.lookupOceConstant(OceUtil.OCE_STATUS_CONCEPT_IND)));
              }
            }
          }
          /* If the user is updating the row to have a status of Update (ie. indicating he wants
           * the "other" value to be replaced) then we need to update the specified table to null 
           * out the "other" value and update the existing ARTS code (which should be a code for
           * "other") with the new value, so do that update now.  
           * NOTE: as a safety measure we make sure the column that we're going to update with a 
           * new ARTS code contains the appropriate ARTS code for "Other" (i.e. if we're updating
           * a diagnosis column it needs to contain the ARTS code for "Other Diagnosis", if we're
           * updating a tissue column it needs to containg the ARTS code for "Other Tissue", etc), 
           * to ensure nothing is updated unless it is an "Other". */
          else {
            /* get the appropriate "Other" code to use in our sql statement */
            String otherCode = determineAppropriateOtherCode(databean);
            
            /* build the sql to do the update.  PDC_PATHOLOGY_REPORT and PDC_PATH_REPORT_SECTION
             * keep track of the last person to modify the table and when they did so, so if we're
             * dealing with either table then we need to update the attributes used to track updates 
             * in addition to the regular update. */
            mySql.append("UPDATE " + tableName);
            mySql.append(" SET " + columnName + " = ?, ");
            mySql.append(otherTextColumnName + " = NULL");
            if ("PDC_PATHOLOGY_REPORT".equals(tableName) || 
                "PDC_PATH_REPORT_SECTION".equals(tableName)) {
              mySql.append(", LAST_UPDATE_USER = ?, LAST_UPDATE_DATE = ?");
            }
            mySql.append(" " + whereClause + " AND ");
            mySql.append(columnName + " = '" + otherCode + "'");

            /* Generate the prepared statement to update the table containing the "other" value 
             * and fill in the parameters */
            PreparedStatement updateStmt = con.prepareStatement(mySql.toString());
            updateStmt.setString(1, rowData.getArtsCode());
            if ("PDC_PATHOLOGY_REPORT".equals(tableName) || 
                "PDC_PATH_REPORT_SECTION".equals(tableName)) {
              updateStmt.setString(2, databean.getUser());
              updateStmt.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            }
            
            /* do the update, get the number of rows that were updated, and close the
             * prepared statement */
            success = updateStmt.executeUpdate();
            updateStmt.close();

            mySql = new StringBuffer(128);

            /* if the update resulted in a row being updated, we need to update the row in 
             * ARD_OTHER_CODE_EDITS to reflect the fact that the row was fixed.  We use
             * a "stored procedure" here so we can return the text for the new ARTS code
             * without having to do another query. */
            if (success != 0) {
              mySql.append("{call UPDATE ARD_OTHER_CODE_EDITS SET STATUS_FLAG = 'F', ");
              mySql.append("EDIT_USER = ?, EDIT_CODE = ?, EDIT_TEXT = ");
              mySql.append("(SELECT LOOKUP_TYPE_CD_DESC ");
              mySql.append("FROM PDC_LOOKUP WHERE LOOKUP_TYPE_CD = ?)");
              mySql.append(", EDIT_DATE = ? WHERE OTHER_LINE_ID = ? ");
              mySql.append("returning edit_text into ?}");

              CallableStatement cs = con.prepareCall(mySql.toString());
              cs.setString(1, databean.getUser());
              cs.setString(2, rowData.getArtsCode());
              cs.setString(3, rowData.getArtsCode());
              cs.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
              cs.setString(5, otherLineId);
              cs.registerOutParameter(6, Types.VARCHAR);
              cs.execute();

              /* return the updated information in the summary info this method provides */
              OceRowData summaryData = new OceRowData(otherText, "Updated");
              summaryData.setFullySpecifiedName(cs.getString(6));
              summary.add(summaryData);
              
              /* close the callable statement */
              cs.close();

              /* If the table we just updated was the Pathology Evaluation features table,
               * keep track of the pathology evaluation id that was affected so that the
               * caller of this method can call code to update the concatenated internal and
               * external features.  The update we just performed will need to be reflected
               * in those fields. */
              if (tableName.equals("LIMS_PE_FEATURES")) {
                String pe_id = null;
                if (peIdList == null) {
                  peIdList = new ArrayList();
                }
                mySql = new StringBuffer(128);
                mySql.append("SELECT PE_ID FROM LIMS_PE_FEATURES ");
                mySql.append(whereClause);
                PreparedStatement selectStmt = con.prepareStatement(mySql.toString());
                ResultSet peResultSet = selectStmt.executeQuery();
                while (peResultSet.next()) {
                  pe_id = peResultSet.getString("PE_ID");
                }
                peResultSet.close();
                selectStmt.close();
                if (pe_id != null) {
                  peIdList.add(pe_id);
                }
              }
            }
            /* if the update did NOT result in a row being updated, we need to update the row in 
             * ARD_OTHER_CODE_EDITS to reflect the fact that the row was obsolete.  This could
             * happen, for example, if someone puts in a DDC diagnosis of other (causing a row
             * to be entered into the ARD_OTHER_CODE_EDITS table), and then changes that value
             * to a non-other diagnosis before OCE is invoked.  The row in ARD_OTHER_CODE_EDITS 
             * is not updated or removed when that happens.  When OCE is invoked, the code above 
             * checks to make sure the existing value in the table to be updated is the ARTS code 
             * for "other".  Since it is not in this case, that row will not be updated (and 
             * correctly so - we don't want to override the value the user selected), so we need 
             * to mark the entry in the ARD_OTHER_CODE_EDITS table as obsolete. */
            else {
              mySql.append("UPDATE ARD_OTHER_CODE_EDITS SET STATUS_FLAG = 'O', ");
              mySql.append("EDIT_USER = ?, EDIT_DATE = ? WHERE OTHER_LINE_ID = ?");
              updateStmt = con.prepareStatement(mySql.toString());
              updateStmt.setString(1, databean.getUser());
              updateStmt.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
              updateStmt.setString(3, otherLineId);

              /* return the updated information in the summary info this method provides */
              success = updateStmt.executeUpdate();
              updateStmt.close();
              if (success > 0)
                summary.add(new OceRowData(otherText, OceUtil.OCE_STATUS_OBSOLETE));
            }
          }
        }
        ApiFunctions.close(resultSet);
        ApiFunctions.close(outerStmt);
      }
      
      /* Return the summary information and the ids of an pathology evaluations to the caller. */
      databean.setList(summary);
      databean.setIdList(peIdList);
    }
    catch (Exception ex) {
      throw new ApiException(ex.getMessage());
    }
    finally {
      ApiFunctions.close(con);
    }
    return databean;
  }
  
  /* Private method to determine the appropriate ARTS code for "Other", given the type of
   * attribute we're going to update.  For example, if the column we're going to update
   * is a diagnosis column, return the ARTS code for Other Diagnosis. */
  private String determineAppropriateOtherCode(OceData databean) {
    String returnValue = null;
    /* We're updating a diagnosis */
    if (OceUtil.OCE_TYPECODE_DIAGNOSIS_IND.equals(databean.getAttribute())) {
      returnValue = FormLogic.OTHER_DX;
    }
    /* We're updating a procedure */
    else if (OceUtil.OCE_TYPECODE_PROCEDURE_IND.equals(databean.getAttribute())) {
      returnValue = FormLogic.OTHER_PX;
    }
    /* We're updating a tissue or metastasis. */
    else if (OceUtil.OCE_TYPECODE_TISSUE_IND.equals(databean.getAttribute())
              || OceUtil.OCE_TYPECODE_METASTASIS_IND.equals(databean.getAttribute())) {
       returnValue = FormLogic.OTHER_TISSUE;
    }
    /* We're updating an indicator on a DDC Path Report Section */
    else if (OceUtil.OCE_TYPECODE_DISTANT_METASTASIS_IND.equals(databean.getAttribute())) {
      returnValue = FormLogic.OTHER_DISTANT_METASTASIS;
    }
    else if (OceUtil.OCE_TYPECODE_HNG_IND.equals(databean.getAttribute())) {
      returnValue = FormLogic.OTHER_HISTOLOGICAL_NUCLEAR_GRADE;
    }
    else if (OceUtil.OCE_TYPECODE_HISTOLOGICAL_TYPE_IND.equals(databean.getAttribute())) {
      returnValue = FormLogic.OTHER_HISTOLOGICAL_TYPE;
    }
    else if (OceUtil.OCE_TYPECODE_LYMPH_NODE_IND.equals(databean.getAttribute())) {
      returnValue = FormLogic.OTHER_LYMPH_NODE_STAGE_DESC;
    }
    else if (OceUtil.OCE_TYPECODE_STAGE_GROUPING_IND.equals(databean.getAttribute())) {
      returnValue = FormLogic.OTHER_STAGE_GROUPING;
    }
    else if (OceUtil.OCE_TYPECODE_TUMOR_STAGE_DESC_IND.equals(databean.getAttribute())) {
      returnValue = FormLogic.OTHER_TUMOR_STAGE_DESC;
    }
    else if (OceUtil.OCE_TYPECODE_TUMOR_STAGE_TYPE_IND.equals(databean.getAttribute())) {
      returnValue = FormLogic.OTHER_TUMOR_STAGE_TYPE;
    }
    
    return returnValue;
  }

  /**
   * Creates a new row in ARD_OTHER_CODE_EDITS with the values specified as 
   * arguments.
   * 
   * @param TableName value for column TABLE_NAME.
   * @param ColumnName value for column COLUMN_NAME.
   * @param TypeCode value for column TYPE_CODE.
   * @param WhereClause value for column WHERE_CLAUSE.
   * @param User value for column CREATE_USER.
   * @param OtherText value for column OTHER_TEXT.
   */
  public void createOce(
    String tableName,
    String columnName,
    String typeCode,
    String whereClause,
    String user,
    String otherText,
    String othertext_Column_Name) {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int rowNum = 0;
    try {
      con = ApiFunctions.getDbConnection();

      //check if already there is a record for this params.If exists update.
      //Query modified by Nagaraja Rao 01/24/2002 MR2906
      //If user modifies D/T/P second time from non Other to Other set STATUS_FLAG='N' and update Other text

      String strUpdate =
        "UPDATE ARD_OTHER_CODE_EDITS SET OTHER_TEXT=?,STATUS_FLAG='N' "
          + " WHERE TABLE_NAME=? AND  WHERE_CLAUSE= ? AND OTHER_TEXT_COLUMN_NAME=? ";

      ps = con.prepareStatement(strUpdate);
      ps.setString(1, otherText);
      ps.setString(2, tableName);
      ps.setString(3, whereClause.trim());
      ps.setString(4, othertext_Column_Name);

      int success = ps.executeUpdate();
      ApiFunctions.close(ps);
      ps = null;

      //if there is no record with this params insert new record.
      if (success < 1) {

        //Get next value from the Sequence
        String selectStmt = "SELECT ARD_OTHER_LINE_ID_SEQ.NEXTVAL FROM DUAL";
        ps = con.prepareStatement(selectStmt);
        rs = ApiFunctions.queryDb(ps, con);

        while (rs.next()) {
          rowNum = rs.getInt(1);
        }
        ps.close();
        ps = null;

        // System.out.println("got next val"+rowNum);	
        selectStmt =
          "insert into ARD_OTHER_CODE_EDITS (OTHER_LINE_ID,TABLE_NAME,COLUMN_NAME,TYPE_CODE, "
            + " WHERE_CLAUSE,ABSTRACT_USER,OTHER_TEXT,ABSTRACT_DATE,STATUS_FLAG,OTHER_TEXT_COLUMN_NAME) values(?,?,?,?,?,?,?,?,?,?)";

        ps = con.prepareStatement(selectStmt);
        ps.setInt(1, rowNum);
        ps.setString(2, tableName);
        ps.setString(3, columnName);
        ps.setString(4, typeCode);
        ps.setString(5, whereClause.trim());
        ps.setString(6, user);
        ps.setString(7, otherText);
        ps.setTimestamp(8, new java.sql.Timestamp(new java.util.Date().getTime()));
        ps.setString(9, "N");
        ps.setString(10, othertext_Column_Name);

        ps.executeUpdate();
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, ps, rs);
    }
  }
  /**
   * Updates status of row specified with Obsolete.
     * 
     * @param TableName value for column TABLE_NAME.
     * @param ColumnName value for column COLUMN_NAME.
     * @param TypeCode value for column TYPE_CODE.
     * @param WhereClause value for column PRIMARY_KEY.
     * @param User value for column CREATE_USER.
     * @param OtherText value for column STATUS_FLAG.
   */

  public void updateStatus(
    String tableName,
    String columnName,
    String typeCode,
    String primaryKey,
    String user,
    String statusFlag) {

    Connection con = null;
    PreparedStatement ps = null;
    try {
      con = ApiFunctions.getDbConnection();
      StringBuffer strUpdate = new StringBuffer();

      if ("ILTDS_ASM_FORM".equals(tableName)) {
        strUpdate.append("UPDATE ARD_OTHER_CODE_EDITS SET STATUS_FLAG = ?, ");
        strUpdate.append("EDIT_USER = ?,EDIT_DATE = ? ");
        strUpdate.append("WHERE UPPER(TABLE_NAME) = UPPER(?) ");
        strUpdate.append("AND UPPER(COLUMN_NAME) = UPPER(?) AND WHERE_CLAUSE = '");
        strUpdate.append(primaryKey);
        strUpdate.append("' AND TYPE_CODE = ? AND STATUS_FLAG <> '");
        strUpdate.append(OceUtil.OCE_STATUS_FIXED_IND + "'");
      }
      else {
        strUpdate.append("UPDATE ARD_OTHER_CODE_EDITS SET STATUS_FLAG = ?, ");
        strUpdate.append("EDIT_USER = ?,EDIT_DATE = ? ");
        strUpdate.append("WHERE UPPER(TABLE_NAME) = UPPER(?) ");
        strUpdate.append("AND UPPER(COLUMN_NAME) = UPPER(?) AND WHERE_CLAUSE LIKE '%''");
        strUpdate.append(primaryKey);
        strUpdate.append("''%' AND TYPE_CODE = ? AND STATUS_FLAG <> '");
        strUpdate.append(OceUtil.OCE_STATUS_FIXED_IND + "'");
      }

      ps = con.prepareStatement(strUpdate.toString());
      ps.setString(1, statusFlag);
      ps.setString(2, user);
      ps.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
      ps.setString(4, tableName);
      ps.setString(5, columnName);
      ps.setString(6, typeCode);

      ps.executeUpdate();

    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(ps);
      ApiFunctions.close(con);
    }
  }

  /**
   * Retrieves disease type, account for a path report specified by 
   * whereClause and assigns these values to disease, account.
   * 
   * @param whereClause a string which uniquely identifies a row in
   *         PDC_PATH_REPORT_SECTION table.
   * @return Map the map of keys and values. 
   */
  public Map retrieveCaseInfo(String whereClause) {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Map vals = new HashMap();
    try {
      con = ApiFunctions.getDbConnection();
      StringBuffer query = new StringBuffer();
      query.append("SELECT  DISTINCT DISEASE_CONCEPT_ID, ");
      query.append("ARDAIS_ACCT_KEY ,SEC.TOTAL_NODES_POSITIVE,");
       query.append("SEC.TUMOR_STAGE_TYPE, SEC.TOTAL_GLEASON_SCORE ");
      query.append("FROM ILTDS_ASM ASM, ILTDS_SAMPLE SAMPLE ,");
      query.append("(SELECT PATH_REPORT_ID,TOTAL_NODES_POSITIVE,  ");
      query.append("TUMOR_STAGE_TYPE, TOTAL_GLEASON_SCORE FROM ");
      query.append("PDC_PATH_REPORT_SECTION " + whereClause);
      query.append(") SEC, PDC_PATHOLOGY_REPORT REPORT ");
      query.append("WHERE REPORT.CONSENT_ID = ASM.CONSENT_ID ");
      query.append("AND ASM.ASM_ID = SAMPLE.ASM_ID ");
      query.append("AND REPORT.PATH_REPORT_ID = SEC.PATH_REPORT_ID");

      ps = con.prepareStatement(query.toString());
      rs = ps.executeQuery();
      while (rs.next()) {
        vals.put("DISEASE", rs.getString("DISEASE_CONCEPT_ID"));
        vals.put("ACCOUNT", rs.getString("ARDAIS_ACCT_KEY"));
        vals.put("POSITIVE_NODES", rs.getString("TOTAL_NODES_POSITIVE"));
        vals.put("TUMOR_STAGE_TYPE", rs.getString("TUMOR_STAGE_TYPE"));
        vals.put("TOTAL_GLEASON_SCORE", rs.getString("TOTAL_GLEASON_SCORE"));
      }

    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, ps, rs);
    }
    return vals;
  }
}
