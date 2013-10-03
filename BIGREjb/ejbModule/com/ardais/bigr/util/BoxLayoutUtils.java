package com.ardais.bigr.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.es.beans.ArdaisaccountAccessBean;
import com.ardais.bigr.es.beans.ArdaisaccountKey;
import com.ardais.bigr.iltds.beans.SampleboxAccessBean;
import com.ardais.bigr.iltds.beans.SampleboxKey;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.javabeans.AccountBoxLayoutDto;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.orm.helpers.BoxScanData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * Utility functions for box layout functionality.
 */
public class BoxLayoutUtils {
  
  public final static String DEFAULT_BOX_LAYOUT = "LY000001";

  private final static String[] ALPHA_LABEL =
  {
    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
    "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
  };

  /**
   * Return information about the box layout with the specified id.
   * 
   * @param boxLayoutId The primary key of the box layout.
   * @param exceptionIfNotExists If true, throw an exception if there is no box layout
   *     with the specified id.  If false, return null if the box layout doesn't exist.
   * @param resultClass The class of object to create to hold the result.  This must be
   *     {@link BoxLayoutDto} or a class that extends it.  This method only populates
   *     fields that exist on the BoxLayoutDto class itself.
   * @return the box layout information
   */
  public static BoxLayoutDto getBoxLayoutDto(
    String boxLayoutId,
    boolean exceptionIfNotExists,
    Class resultClass) {

    BoxLayoutDto result = null;
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_BOX_LAYOUT);
      query.append(" ");
      query.append(DbAliases.TABLE_BOX_LAYOUT);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.LY_BOX_LAYOUT_ID);
      query.append(" = ?");
      
      result = (BoxLayoutDto) resultClass.newInstance();

      result.setBoxLayoutId(boxLayoutId);

      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, boxLayoutId);
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      if (rs.next()) {
        result.populateFromResultSet(columns, rs);
      }
      else if (exceptionIfNotExists){
        throw new ApiException("Could not retrieve box layout information for box layout id = " + boxLayoutId);
      }
      else {
        result = null;
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return result;
  }

  /**
   * Return information about the box layout with the specified box layout id.
   * 
   * @param boxLayoutId The primary key of the box layout.
   * @return the box layout information
   */
  public static BoxLayoutDto getBoxLayoutDto(String boxLayoutId) {
    return getBoxLayoutDto(boxLayoutId, true, BoxLayoutDto.class);
  }

  public static BoxLayoutDto getBoxLayoutDtoByBoxId(
    String boxId,
    boolean exceptionIfNotExists,
    Class resultClass) {

    BoxLayoutDto result = null;
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_BOX_LAYOUT);
      query.append(" ");
      query.append(DbAliases.TABLE_BOX_LAYOUT);
      query.append(", iltds_sample_box isb");
      query.append(" WHERE ");
      query.append(" isb.box_barcode_id = ? AND ");
      query.append(DbAliases.TABLE_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.LY_BOX_LAYOUT_ID);
      query.append(" = isb.box_layout_id");
      
      result = (BoxLayoutDto) resultClass.newInstance();

      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, boxId);
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      if (rs.next()) {
        result.populateFromResultSet(columns, rs);
      }
      else if (exceptionIfNotExists){
        throw new ApiException("Could not retrieve box layout information for box id = " + boxId);
      }
      else {
        result = null;
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return result;
  }

  public static BoxLayoutDto getBoxLayoutDtoByBoxId(String boxId) {
    return getBoxLayoutDtoByBoxId(boxId, true, BoxLayoutDto.class);
  }

  /**
   * Validate the specified box layou id.  Return the canonical form of the id if the
   * id is valid, otherwise return null and add appropriate ActionErrors
   * to btxDetails.  When isRequired is false, a null id is considered valid.  To
   * make it easier for callers to know whether the id was valid in all situations,
   * this method returns an empty string ("") when the input id is null and isRequired
   * is false. 
   * 
   * @param id The id to validate.
   * @param btxDetails The btxDetails object to add ActionErrors to.
   * @param lenient True to accept non-canonical forms of the id (e.g. "fr12").
   * @param isRequired True if the id is required.
   * @param mustExist True if the id must specify a box layout that exists in the database.
   *     If isRequired is false and the id is empty, then the existence check is
   *     not performed, so if you want to be certain that the id is the id of an existing
   *     object, you must pass <code>true</code> to both isRequired and mustExist.
   * @return The box layout id in canonical form if it is valid, otherwise null.
   */
  public static String checkBoxLayoutId(
    String id,
    BTXDetails btxDetails,
    boolean lenient,
    boolean isRequired,
    boolean mustExist)
  {
    boolean isOk = true;

    if (lenient) {
      id = ApiFunctions.safeString(ApiFunctions.safeTrim(id));
    }

    if (ApiFunctions.isEmpty(id)) {
      if (isRequired) {
        btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.requiredBoxLayoutId"));
        return null;
      }
      else {
        return ApiFunctions.EMPTY_STRING;
      }
    }

    {
      String validatedId = ValidateIds.validateId(id, ValidateIds.TYPESET_BOX_LAYOUT, lenient);
      if (validatedId == null) {
        btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.invalidBoxLayoutId"));
        return null;
      }
      id = validatedId;
    }

    if (mustExist && !isExistingBoxLayout(id)) {
      isOk = false;
      btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.notFountBoxLayoutId"));
    }
    return (isOk ? id : null);
  }

  /**
   * Validate the specified box layou id.  Return the canonical form of the id if the
   * id is valid, otherwise return null.  When isRequired is false, a null id is considered
   * valid.  To make it easier for callers to know whether the id was valid in all situations,
   * this method returns an empty string ("") when the input id is null and isRequired
   * is false. 
   * 
   * @param id The id to validate.
   * @param lenient True to accept non-canonical forms of the id (e.g. "fr12").
   * @param isRequired True if the id is required.
   * @param mustExist True if the id must specify a box layout that exists in the database.
   *     If isRequired is false and the id is empty, then the existence check is
   *     not performed, so if you want to be certain that the id is the id of an existing
   *     object, you must pass <code>true</code> to both isRequired and mustExist.
   * @return The box layout id in canonical form if it is valid, otherwise null.
   */
  public static String checkBoxLayoutId(
    String id,
    boolean lenient,
    boolean isRequired,
    boolean mustExist)
  {
    boolean isOk = true;

    if (lenient) {
      id = ApiFunctions.safeString(ApiFunctions.safeTrim(id));
    }

    if (ApiFunctions.isEmpty(id)) {
      if (isRequired) {
        return null;
      }
      else {
        return ApiFunctions.EMPTY_STRING;
      }
    }

    {
      String validatedId = ValidateIds.validateId(id, ValidateIds.TYPESET_BOX_LAYOUT, lenient);
      if (validatedId == null) {
        return null;
      }
      id = validatedId;
    }

    if (mustExist && !isExistingBoxLayout(id)) {
      isOk = false;
    }
    return (isOk ? id : null);
  }

  /**
   * Return true if there is a box layout with the specified id.
   * 
   * @param boxLayoutId The primary key of the box layout.
   * @return true if there is a box layout with the specified id.
   */
  public static boolean isExistingBoxLayout(String boxLayoutId) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean exists = false;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_BOX_LAYOUT);
      query.append(" ");
      query.append(DbAliases.TABLE_BOX_LAYOUT);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.LY_BOX_LAYOUT_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, boxLayoutId);
      rs = ApiFunctions.queryDb(pstmt, con);

      exists = rs.next();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return exists;
  }

  /**
   * Get a list of all box layouts, sorted by box layout id.
   * @return the list of all box layouts.
   */
  public static List getAllBoxLayoutDtos() {
    List result = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_BOX_LAYOUT);
      query.append(" ");
      query.append(DbAliases.TABLE_BOX_LAYOUT);
      query.append(" ORDER BY upper(");
      query.append(DbAliases.TABLE_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.LY_BOX_LAYOUT_ID);
      query.append(") ASC");

      pstmt = con.prepareStatement(query.toString());
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      while (rs.next()) {
        BoxLayoutDto bldto = new BoxLayoutDto();
        bldto.populateFromResultSet(columns, rs);
        result.add(bldto);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return result;
  }
  
  public static String convertToXaxisLabel(int column, BoxLayoutDto boxLayoutDto) {
    String label = null;

    String xaxisLabelCid = boxLayoutDto.getXaxisLabelCid();
    int numberOfColumns = boxLayoutDto.getNumberOfColumns();

    if (ArtsConstants.BOX_LAYOUT_ASC_LETTERS.equals(xaxisLabelCid)) {
      //if the column is from the single letter range, just use it to determine the label
      if (column <= ALPHA_LABEL.length) {
        label = ALPHA_LABEL[column - 1];
      }
      else {
        //otherwise, additional processing is needed to determine the two letter label
        int firstIndex;
        int secondIndex;
        //determine if we're at the boundary of a range.  If so, don't increase the first index and
        //max the second index.  For example a column value of 52 should translate to AZ, 78
        //should translate to BZ, etc.  Without this action 52 would translate to BA, 78 would
        //translate to CA, etc.
        boolean boundaryReached = (column%ALPHA_LABEL.length == 0);
        if (boundaryReached) {
          firstIndex = (column/ALPHA_LABEL.length) - 2;
          secondIndex = ALPHA_LABEL.length - 1;
        }
        else {
          firstIndex = (column/ALPHA_LABEL.length) - 1;
          secondIndex = (column%ALPHA_LABEL.length) - 1;
        }
        label = ALPHA_LABEL[firstIndex] + ALPHA_LABEL[secondIndex];
      }
    }
    else if (ArtsConstants.BOX_LAYOUT_DSC_LETTERS.equals(xaxisLabelCid)) {
      //since we're decending (from N down to 1), subtract the column number passed in from the 
      //total number of columns to determine the index. For example if there are 30 total columns
      //then the label for the first column is derived from an index of 30 (30 - 1 + 1), the label 
      //for the second column is derived from an index of 29 (30 - 2 + 1), etc.
      int columnNumber = (numberOfColumns - column) + 1;
      //if the index is from the single letter range, just use it to determine the label
      if (columnNumber <= ALPHA_LABEL.length) {
        label = ALPHA_LABEL[columnNumber - 1];
      }
      else {
        //otherwise, additional processing is needed to determine the two letter label
        int firstIndex;
        int secondIndex;
        //determine if we're at the boundary of a range.  If so, don't increase the first index and
        //max the second index.  For example a columnNumber value of 52 should translate to AZ, 78
        //should translate to BZ, etc.  Without this action 52 would translate to BA, 78 would
        //translate to CA, etc.
        boolean boundaryReached = (columnNumber%ALPHA_LABEL.length == 0);
        if (boundaryReached) {
          firstIndex = (columnNumber/ALPHA_LABEL.length) - 2;
          secondIndex = ALPHA_LABEL.length - 1;
        }
        else {
          firstIndex = (columnNumber/ALPHA_LABEL.length) - 1;
          secondIndex = (columnNumber%ALPHA_LABEL.length) - 1;
        }
        label = ALPHA_LABEL[firstIndex] + ALPHA_LABEL[secondIndex];
      }
    }
    else if (ArtsConstants.BOX_LAYOUT_ASC_NUMBERS.equals(xaxisLabelCid)) {
      label = Integer.toString(column);
    }
    else if (ArtsConstants.BOX_LAYOUT_DSC_NUMBERS.equals(xaxisLabelCid)) {
      label = Integer.toString((numberOfColumns - column) + 1);
    }
    return (label);
  }
  
  public static String convertToYaxisLabel(int row, BoxLayoutDto boxLayoutDto) {
    String label = null;

    String yaxisLabelCid = boxLayoutDto.getYaxisLabelCid();
    int numberOfRows = boxLayoutDto.getNumberOfRows();

    if (ArtsConstants.BOX_LAYOUT_ASC_LETTERS.equals(yaxisLabelCid)) {
      //if the row is from the single letter range, just use it to determine the label
      if (row <= ALPHA_LABEL.length) {
        label = ALPHA_LABEL[row - 1];
      }
      else {
        //otherwise, additional processing is needed to determine the two letter label
        int firstIndex;
        int secondIndex;
        //determine if we're at the boundary of a range.  If so, don't increase the first index and
        //max the second index.  For example a row value of 52 should translate to AZ, 78
        //should translate to BZ, etc.  Without this action 52 would translate to BA, 78 would
        //translate to CA, etc.
        boolean boundaryReached = (row%ALPHA_LABEL.length == 0);
        if (boundaryReached) {
          firstIndex = (row/ALPHA_LABEL.length) - 2;
          secondIndex = ALPHA_LABEL.length - 1;
        }
        else {
          firstIndex = (row/ALPHA_LABEL.length) - 1;
          secondIndex = (row%ALPHA_LABEL.length) - 1;
        }
        label = ALPHA_LABEL[firstIndex] + ALPHA_LABEL[secondIndex];
      }
    }
    else if (ArtsConstants.BOX_LAYOUT_DSC_LETTERS.equals(yaxisLabelCid)) {
      //since we're decending (from N down to 1), subtract the row number passed in from the 
      //total number of rows to determine the index. For example if there are 30 total rows then the
      //label for the first row is derived from an index of 30 (30 - 1 + 1), the label for the 
      //second row is derived from an index of 29 (30 - 2 + 1), etc.
      int rowNumber = (numberOfRows - row) + 1;
      //if the index is from the single letter range, just use it to determine the label
      if (rowNumber <= ALPHA_LABEL.length) {
        label = ALPHA_LABEL[rowNumber - 1];
      }
      else {
        //otherwise, additional processing is needed to determine the two letter label
        int firstIndex;
        int secondIndex;
        //determine if we're at the boundary of a range.  If so, don't increase the first index and
        //max the second index.  For example a rowNumber value of 52 should translate to AZ, 78
        //should translate to BZ, etc.  Without this action 52 would translate to BA, 78 would
        //translate to CA, etc.
        boolean boundaryReached = (rowNumber%ALPHA_LABEL.length == 0);
        if (boundaryReached) {
          firstIndex = (rowNumber/ALPHA_LABEL.length) - 2;
          secondIndex = ALPHA_LABEL.length - 1;
        }
        else {
          firstIndex = (rowNumber/ALPHA_LABEL.length) - 1;
          secondIndex = (rowNumber%ALPHA_LABEL.length) - 1;
        }
        label = ALPHA_LABEL[firstIndex] + ALPHA_LABEL[secondIndex];
      }
    }
    else if (ArtsConstants.BOX_LAYOUT_ASC_NUMBERS.equals(yaxisLabelCid)) {
      label = Integer.toString(row);
    }
    else if (ArtsConstants.BOX_LAYOUT_DSC_NUMBERS.equals(yaxisLabelCid)) {
      label = Integer.toString((numberOfRows - row) + 1);
    }
    return (label);
  }
  
  public static String translateCellRef(String cellRef, String boxId) {
    if (!ApiFunctions.isEmpty(boxId)) {
      try {
        SampleboxAccessBean sbAB = new SampleboxAccessBean(new SampleboxKey(boxId));
        BoxLayoutDto boxLayoutDto = getBoxLayoutDto(sbAB.getBoxLayoutId());
        return translateCellRef(cellRef, boxLayoutDto);
      }
      catch (Exception ex) {
        return cellRef;
      }
    }
    else {
      return cellRef;
    }
  }

  /**
   * CellRef value is always assigned from left to right. This works fine for sorting when the
   * tab order is Tab Right. But when the tab order is Tab Left, the sorting by CellRef value does
   * not work. This method returns the list of CellRef value by the tab order. We should use the
   * index value of CellRef for sorting.
   * @param boxId
   * @return the list of CellRef value by tab order.
   */
  public static List getCellRefByTabOrber(String boxId) {
    BoxLayoutDto boxLayoutDto = getBoxLayoutDtoByBoxId(boxId);
    List cellRefList = new ArrayList();
    if (boxLayoutDto != null) {
      int numberOfColumns = boxLayoutDto.getNumberOfColumns();
      int numberOfRows = boxLayoutDto.getNumberOfRows();
      if (boxLayoutDto.getTabIndexCid().equals(ArtsConstants.BOX_LAYOUT_TAB_DOWN)) {
        for (int col=1; col<= numberOfColumns; col++) {
          for (int row=1; row<=numberOfRows; row++) {
            int cellValue = (row-1)* numberOfColumns + col;
            cellRefList.add(cellValue + "");
          }
        }
      }
      else if (boxLayoutDto.getTabIndexCid().equals(ArtsConstants.BOX_LAYOUT_TAB_RIGHT)) {
        for (int row = 1; row <= numberOfRows; row++) {
          for (int col = 1; col <= numberOfColumns; col++) {
            int cellValue = (row - 1) * numberOfColumns + col;
            cellRefList.add(cellValue + "");
          }
        }
      }
    }
    return cellRefList;
  }

  public static String translateCellRef(String cellRef, BoxLayoutDto boxLayoutDto) {

    String cellName = cellRef;
    
    int cellIndex = 0;
    
    // Convert the cell reference into a cell index.
    try {
      cellIndex = Integer.parseInt(cellRef) - 1;

      if (boxLayoutDto != null) {
        int numberOfColumns = boxLayoutDto.getNumberOfColumns();
        
        int column = (cellIndex % numberOfColumns) + 1;
        int row = (cellIndex / numberOfColumns) + 1;
        
        cellName =
          convertToXaxisLabel(column, boxLayoutDto) + "-" + convertToYaxisLabel(row, boxLayoutDto);
      }
    }
    catch (NumberFormatException nfe) {
      // If the cellRef is not a number, set the cell name to be the cell ref.
    }
    
    return cellName;
  }


  /**
   * Wrapper method for getAccountBoxLayoutDtosByAccountId() and getDefaultBoxLayoutIdByAccountId()
   * that uses the security info object in order get the account id. 
   * 
   * @param securityInfo the security info object.
   * 
   * @return a box scan data object {@link BoxScanData}).
   */
  public static BoxScanData prepareForBoxScan(SecurityInfo securityInfo) throws Exception {
    String accountId = securityInfo.getAccount();
    BoxScanData boxScanData = new BoxScanData();

    boxScanData.setAccountBoxLayouts(getAccountBoxLayoutDtosByAccountId(accountId));
    boxScanData.setDefaultBoxLayoutId(getDefaultBoxLayoutIdByAccountId(accountId));
    
    return boxScanData;
  }

  /**
   * Get the account box layouts to which the account has visiblity as a list ordered by
   * case-insensitive name.
   * 
   * @param accountId the account Id
   * 
   * @return a list of the visible account box layouts ({@link AccountBoxLayoutDto} objects).
   */
  public static List getAccountBoxLayoutDtosByAccountId(String accountId) {
    List result = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      
      query.append("SELECT ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_NAME);
      query.append(", ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".*");
      query.append(" FROM ");
      query.append(DbConstants.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(" ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(", ");
      query.append(DbConstants.TABLE_ARDAIS_ACCOUNT);
      query.append(" ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_ARDAIS_ACCT_KEY);
      query.append(" = ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_KEY);
      query.append(" AND ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_ARDAIS_ACCT_KEY);
      query.append(" = ?");
      query.append(" ORDER BY upper(");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_BOX_LAYOUT_NAME);
      query.append(") ASC");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, accountId);
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      while (rs.next()) {
        AccountBoxLayoutDto abldto = new AccountBoxLayoutDto();
        abldto.populateFromResultSet(columns, rs);
        result.add(abldto);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return result;
  }
  
  /**
   * 
   * @param defaultBoxLayoutId
   * @param accountId
   * @return
   * @throws Exception
   */
  public static String getDefaultBoxLayoutIdByAccountId(String accountId) throws Exception {
    
    String defaultBoxLayoutId = null;
    try {
      ArdaisaccountKey accountKey = new ArdaisaccountKey(accountId);
      ArdaisaccountAccessBean accountAB = new ArdaisaccountAccessBean(accountKey);

      defaultBoxLayoutId = accountAB.getDefaultBoxLayoutId();
    }
    catch (Exception ex) {
      ApiFunctions.throwAsRuntimeException(ex);
    }
    return defaultBoxLayoutId;
  }

  /**
   * Set the default box layout id for the account.
   * 
   * @param defaultBoxLayoutId
   * @param accountId
   * @throws Exception
   */
  public static void setDefaultBoxLayoutIdByAccountId(String defaultBoxLayoutId, String accountId) throws Exception {
    
    try {
      ArdaisaccountKey accountKey = new ArdaisaccountKey(accountId);
      ArdaisaccountAccessBean accountAB = new ArdaisaccountAccessBean(accountKey);
      
      accountAB.setDefaultBoxLayoutId(defaultBoxLayoutId);
      accountAB.commitCopyHelper();
    }
    catch (Exception ex) {
      ApiFunctions.throwAsRuntimeException(ex);
    }
  }

  /**
   * Get the account box layouts for the accounts that have visibility to the specified box layout,
   * as a list ordered by case-insensitive account name.
   * 
   * @param boxLayoutId the box layout id
   * 
   * @return the list of the account box layouts ({@link AccountBoxLayoutDto} objects).
   */
  public static List getAccountBoxLayoutDtosByBoxLayoutId(String boxLayoutId) {
    List result = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      
      query.append("SELECT ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_NAME);
      query.append(", ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".*");
      query.append(" FROM ");
      query.append(DbConstants.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(" ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(", ");
      query.append(DbConstants.TABLE_ARDAIS_ACCOUNT);
      query.append(" ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_ARDAIS_ACCT_KEY);
      query.append(" = ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_KEY);
      query.append(" AND ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_BOX_LAYOUT_ID);
      query.append(" = ?");
      query.append(" ORDER BY upper(");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_NAME);
      query.append(") ASC");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, boxLayoutId);
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      while (rs.next()) {
        AccountBoxLayoutDto abldto = new AccountBoxLayoutDto();
        abldto.populateFromResultSet(columns, rs);
        result.add(abldto);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return result;
  }

  /**
   * Return information about the account box layout with the specified id.
   * 
   * @param accountBoxLayoutId The primary key of the account box layout.
   * @param exceptionIfNotExists If true, throw an exception if there is no account box layout
   *     with the specified id.  If false, return null if the box layout doesn't exist.
   * @param resultClass The class of object to create to hold the result.  This must be
   *     {@link AccountBoxLayoutDto} or a class that extends it.  This method only populates
   *     fields that exist on the AccountBoxLayoutDto class itself.
   * @return the account box layout information
   */
  public static AccountBoxLayoutDto getAccountBoxLayoutDto(
    int accountBoxLayoutId,
    boolean exceptionIfNotExists,
    Class resultClass) {

    AccountBoxLayoutDto result = null;
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      StringBuffer query = new StringBuffer(256);
      
      query.append("SELECT ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_NAME);
      query.append(", ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".*");
      query.append(" FROM ");
      query.append(DbConstants.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(" ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(", ");
      query.append(DbConstants.TABLE_ARDAIS_ACCOUNT);
      query.append(" ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_ARDAIS_ACCT_KEY);
      query.append(" = ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_KEY);
      query.append(" AND ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_ID);
      query.append(" = ?");
      
      result = (AccountBoxLayoutDto) resultClass.newInstance();

      result.setAccountBoxLayoutId(accountBoxLayoutId);

      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setBigDecimal(1, new BigDecimal(accountBoxLayoutId));
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      if (rs.next()) {
        result.populateFromResultSet(columns, rs);
      }
      else if (exceptionIfNotExists){
        throw new ApiException("Could not retrieve account box layout information for account box layout id = " + accountBoxLayoutId);
      }
      else {
        result = null;
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return result;
  }

  /**
   * Return information about the account box layout with the specified account box layout id.
   * 
   * @param accountBoxLayoutId The primary key of the account box layout.
   * @return the account box layout information
   */
  public static AccountBoxLayoutDto getAccountBoxLayoutDto(int accountBoxLayoutId) {
    return getAccountBoxLayoutDto(accountBoxLayoutId, true, AccountBoxLayoutDto.class);
  }

  /**
   * Return true if there is an account box layout with the specified id.
   * 
   * @param accountBoxLayoutId The primary key of the account box layout.
   * @return true if there is an account box layout with the specified id.
   */
  public static boolean isExistingAccountBoxLayout(int accountBoxLayoutId) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean exists = false;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(" ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setBigDecimal(1, new BigDecimal(accountBoxLayoutId));
      rs = ApiFunctions.queryDb(pstmt, con);

      exists = rs.next();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return exists;
  }

  /**
   * Return true if the box layout id is part of the account box layout group.
   * 
   * @param boxLayoutId The box layout id to check against the account.
   * @param accountId The account to check against.
   * @return true if there is a box layout within the specified account.
   */
  public static boolean isValidBoxLayoutForAccount(String boxLayoutId, String accountId) {
    // The system wide default box layout is always valid.
    if (BoxLayoutUtils.DEFAULT_BOX_LAYOUT.equals(boxLayoutId)) {
      return true;
    }

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean exists = false;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(" ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_BOX_LAYOUT_ID);
      query.append(" = ?");
      query.append(" AND ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_ARDAIS_ACCT_KEY);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, boxLayoutId);
      pstmt.setString(2, accountId);
      rs = ApiFunctions.queryDb(pstmt, con);

      exists = rs.next();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return exists;
  }
}
