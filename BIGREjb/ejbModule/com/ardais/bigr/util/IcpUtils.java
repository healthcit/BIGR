package com.ardais.bigr.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.security.SecurityInfo;

/**
 * ICP-related static utility methods.
 */
public class IcpUtils {

  private static final SimpleDateFormat DEFAULT_DATE_FORMATTER =
    new SimpleDateFormat("MMM d, yyyy h:mm a");

  /**
   * Constructor is private to prevent instantiation.
   */
  private IcpUtils() {
    super();
  }

  public static String formatDate(Date date, boolean escapeForHtml) {
    if (date == null) {
      return ApiFunctions.EMPTY_STRING;
    }

    String formattedDate = DEFAULT_DATE_FORMATTER.format(date);

    if (escapeForHtml) {
      return Escaper.htmlEscape(formattedDate);
    }
    else {
      return formattedDate;
    }
  }

  public static boolean isHasIcpPrivilege(SecurityInfo securityInfo) {
    if (securityInfo == null) {
      return false;
    }

    return securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP);
  }
  
  public static String prepareLinkToRenderText(
   String textToShow,
   String linkText,
   SecurityInfo securityInfo,
   boolean popup) {

   if (!isHasIcpPrivilege(securityInfo)) {
     return linkText;
   }

   StringBuffer sb = new StringBuffer(256);
   sb.append("<span class=\"fakeLink\" onclick=\"showText('");
   Escaper.jsEscapeInXMLAttr(textToShow, sb);
   sb.append("', ");
   sb.append(popup ? "true" : "false");
   sb.append(");\">");
   Escaper.htmlEscape(linkText, sb);
   sb.append("</span>");
   return sb.toString();
 }


  public static String prepareLink(
    String id,
    String linkText,
    SecurityInfo securityInfo,
    boolean popup) {

    if ((!isHasIcpPrivilege(securityInfo)) || (!ValidateIds.isIcpId(id))) {
      return linkText;
    }

    StringBuffer sb = new StringBuffer(256);
    sb.append("<span class=\"fakeLink\" onclick=\"toIcp('");
    Escaper.jsEscapeInXMLAttr(id, sb);
    sb.append("', ");
    sb.append(popup ? "true" : "false");
    sb.append(");\">");
    Escaper.htmlEscape(linkText, sb);
    sb.append("</span>");
    return sb.toString();
  }

  public static String prepareLink(String id, String linkText, SecurityInfo securityInfo) {
    return prepareLink(id, linkText, securityInfo, false);
  }

  public static String prepareLink(String id, SecurityInfo securityInfo) {
    return prepareLink(id, id, securityInfo, false);
  }

  public static String preparePopupLink(String id, String linkText, SecurityInfo securityInfo) {
    return prepareLink(id, linkText, securityInfo, true);
  }

  public static String preparePopupLink(String id, SecurityInfo securityInfo) {
    return prepareLink(id, id, securityInfo, true);
  }

  /**
   * Return true if the specified user has inventory group access to the
   * specified item id (sample, RNA, ...).  This considers only inventory group
   * access, not other aspects of the sample that may limit its accessibility in
   * some contexts (e.g. it does not factor in Sample Selection's implicit filters).
   * 
   * <p>If you already have a {@link SampleData} object that contains the sample's logical
   * repository information, it is more efficient to perform this test with the
   * {@link #isSampleAccessibleToUser(SampleData, SecurityInfo)} method instead (you avoid
   * an additional database query).
   * 
   * @param securityInfo The SecurityInfo for the logged-in user.
   * @param itemId The item id.
   * @return true if the user ahs inventory group access to the item.
   */
  public static boolean isItemAccessibleToUserByInvGroup(
    SecurityInfo securityInfo,
    String itemId) {
    boolean returnValue = false;

    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
      returnValue = true;
    }
    else if (ApiFunctions.isEmpty(itemId)) {
      return false;
    }
    else {
      String queryString =
        "select 1 from dual where exists (select 1 "
          + "\nfrom ard_logical_repos_item i, ard_logical_repos_user u "
          + "\nwhere i.item_id = ? "
          + "\n  and u.ardais_user_id = ? "
          + "\n  and i.repository_id = u.repository_id)";

      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(queryString);
        pstmt.setString(1, itemId);
        pstmt.setString(2, securityInfo.getUsername());
        rs = ApiFunctions.queryDb(pstmt, con);

        returnValue = rs.next();
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
        return false; // unreached, keep compiler happy
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }

    return returnValue;
  }

  /**
   * Return true if the specified user has inventory group access to the
   * specified case.  This considers only inventory group
   * access, not other aspects of the sample that may limit its accessibility in
   * some contexts (e.g. it does not factor in Sample Selection's implicit filters).
   * A user is considered to have inventory group access to a case if they either have
   * the PRIV_VIEW_ALL_LOGICAL_REPOS privilege or they are permitted to view at least one
   * of the inventory groups of at least one of the samples in the case.
   * 
   * @param securityInfo The SecurityInfo for the logged-in user.
   * @param caseId The case id.
   * @return true if the user has inventory group access to the case.
   */
  private static boolean isCaseAccessibleToUserByInvGroup(
    SecurityInfo securityInfo,
    String caseId) {

    boolean returnValue = false;

    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
      returnValue = true;
    }
    else if (ApiFunctions.isEmpty(caseId)) {
      return false;
    }
    else {
      String queryString =
        "select 1 from dual where exists (select 1 "
          + "\nfrom ard_logical_repos_item i, ard_logical_repos_user u, iltds_sample s "
          + "\nwhere s.consent_id = ? "
          + "\n  and u.ardais_user_id = ? "
          + "\n  and s.sample_barcode_id = i.item_id "
          + "\n  and i.repository_id = u.repository_id)";

      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(queryString);
        pstmt.setString(1, caseId);
        pstmt.setString(2, securityInfo.getUsername());
        rs = ApiFunctions.queryDb(pstmt, con);

        returnValue = rs.next();
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
        return false; // unreached, keep compiler happy
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }

    return returnValue;
  }

  /**
   * Return true if the specified user has inventory group access to the
   * specified donor.  This considers only inventory group
   * access, not other aspects of the sample that may limit its accessibility in
   * some contexts (e.g. it does not factor in Sample Selection's implicit filters).
   * A user is considered to have inventory group access to a donor if they either have
   * the PRIV_VIEW_ALL_LOGICAL_REPOS privilege or they are permitted to view at least one
   * of the inventory groups of at least one of the donor's samples.
   * 
   * @param securityInfo The SecurityInfo for the logged-in user.
   * @param donorId The donor id.
   * @return true if the user has inventory group access to the donor.
   */
  private static boolean isDonorAccessibleToUserByInvGroup(
    SecurityInfo securityInfo,
    String donorId) {

    boolean returnValue = false;

    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
      returnValue = true;
    }
    else if (ApiFunctions.isEmpty(donorId)) {
      return false;
    }
    else {
      String queryString =
        "select 1 from dual where exists (select 1 "
          + "\nfrom ard_logical_repos_item i, ard_logical_repos_user u, iltds_sample s "
          + "\nwhere s.ardais_id = ? "
          + "\n  and u.ardais_user_id = ? "
          + "\n  and s.sample_barcode_id = i.item_id "
          + "\n  and i.repository_id = u.repository_id)";

      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(queryString);
        pstmt.setString(1, donorId);
        pstmt.setString(2, securityInfo.getUsername());
        rs = ApiFunctions.queryDb(pstmt, con);

        returnValue = rs.next();
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
        return false; // unreached, keep compiler happy
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }

    return returnValue;
  }

  /**
   * Return true if the specified user has ICP access to the specified donor by virtue
   * of an account match.  There is an account match if the user is in the account that
   * created the donor or one of the donor's cases.  We have to check both the donor account
   * and the donor's cases' accounts because currently, we sometimes have case records for a
   * donor but no donor record in PDC_ARDAIS_DONOR.
   * 
   * @param securityInfo The SecurityInfo for the logged-in user.
   * @param donorId The donor id.
   * @return true if the user has account-based access to the donor.
   */
  public static boolean isDonorAccessibleToUserByAccount(
    SecurityInfo securityInfo,
    String donorId) {

    boolean returnValue = false;

    if (ApiFunctions.isEmpty(donorId)) {
      return false;
    }

    // First check whether we have a donor record that matches the user's account.
    {
      String queryString =
        "select 1 from pdc_ardais_donor x "
          + "\nwhere x.ardais_id = ? "
          + "\n  and x.ardais_acct_key = ? ";

      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(queryString);
        pstmt.setString(1, donorId);
        pstmt.setString(2, securityInfo.getAccount());
        rs = ApiFunctions.queryDb(pstmt, con);

        returnValue = rs.next();
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
        return false; // unreached, keep compiler happy
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }

    // If we didn't find a donor record that matches the user's account, look for a matching
    // case record.  We need to do this because currently, we sometimes have cases for donors
    // who don't have a records in PDC_ARDAIS_DONOR.
    if (!returnValue) {
      String queryString =
        "select 1 from iltds_informed_consent x "
          + "\nwhere x.ardais_id = ? "
          + "\n  and x.ardais_acct_key = ? ";

      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(queryString);
        pstmt.setString(1, donorId);
        pstmt.setString(2, securityInfo.getAccount());
        rs = ApiFunctions.queryDb(pstmt, con);

        returnValue = rs.next();
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
        return false; // unreached, keep compiler happy
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }

    return returnValue;
  }

  /**
   * Return true if the specified user has ICP access to the specified box layout by virtue
   * of how it is used relative to them.  The user has access if either the box layout is mapped
   * to their account or there is at least one box stored at the user's location that uses the
   * layout.
   * 
   * @param securityInfo The SecurityInfo for the logged-in user.
   * @param boxLayoutId The donor id.
   * @return true if the user has use-based access to the box layout.
   */
  private static boolean isBoxLayoutAccessibleToUserByUsage(
    SecurityInfo securityInfo,
    String boxLayoutId) {

    boolean returnValue = false;

    if (ApiFunctions.isEmpty(boxLayoutId)) {
      return false;
    }

    // First check whether we have a donor record that matches the user's account.
    {
      String queryString =
        "select 1 from dual"
          + "\nwhere exists (select 1 from iltds_box_location l, iltds_sample_box b, iltds_box_layout ly"
          + "\n        where l.BOX_BARCODE_ID = b.BOX_BARCODE_ID"
          + "\n          and b.BOX_LAYOUT_ID = ly.BOX_LAYOUT_ID"
          + "\n          and l.LOCATION_ADDRESS_ID = ?"
          + "\n          and ly.BOX_LAYOUT_ID = ?)"
          + "\n   or exists (select 1 from iltds_account_box_layout ly"
          + "\n        where ly.ARDAIS_ACCT_KEY = ?"
          + "\n          and ly.BOX_LAYOUT_ID = ?)";

      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(queryString);
        pstmt.setString(1, securityInfo.getUserLocationId());
        pstmt.setString(2, boxLayoutId);
        pstmt.setString(3, securityInfo.getAccount());
        pstmt.setString(4, boxLayoutId);
        rs = ApiFunctions.queryDb(pstmt, con);

        returnValue = rs.next();
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
        return false; // unreached, keep compiler happy
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }

    return returnValue;
  }

  /**
   * Return true if the specified user is authorized to view the ICP page for a given case.
   * 
   * @param securityInfo - the logged in user's security info
   * @param caseId - the case id they want to view
   * @return boolean - true if the user can view the case, false otherwise
   */
  public static boolean isAuthorizedToViewCase(SecurityInfo securityInfo, String caseId) {
    //The user can see the case if any of the following are true:
    //1) they are an ICP super-user
    //2) they have access to any sample in the case via inventory groups
    //3) they are in the account that created the case
    return securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)
      || securityInfo.getAccount().equalsIgnoreCase(IltdsUtils.getAccountForCase(caseId))
      || IcpUtils.isCaseAccessibleToUserByInvGroup(securityInfo, caseId);
  }

  /**
   * Return true if the specified user is authorized to view the ICP page for a given donor.
   * 
   * @param securityInfo - the logged in user's security info
   * @param donorId - the donor id they want to view
   * @return boolean - true if the user can view the donor, false otherwise
   */
  public static boolean isAuthorizedToViewDonor(SecurityInfo securityInfo, String donorId) {
    //The user can see the case if any of the following are true:
    //1) they are an ICP super-user
    //2) they have access to any of the donor's samples via inventory groups
    //3) they are in the account that created the donor or one of the donor's cases
    // We have to check both the donor account and the donor's cases' accounts because currently,
    // we sometimes have case records for a donor but no donor record in PDC_ARDAIS_DONOR.
    return securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)
      || IcpUtils.isDonorAccessibleToUserByAccount(securityInfo, donorId)
      || IcpUtils.isDonorAccessibleToUserByInvGroup(securityInfo, donorId);
  }

  /**
   * Return true if the specified user is authorized to view ICP pages for inventory groups.
   * This doesn't necessarily mean that they'll be able to view the page for a specific
   * group, since there may be additional group-specific authorization rules.  It just means
   * that they at least have a chance of having ICP access.
   * 
   * @param securityInfo
   * @return
   */
  public static boolean isAuthorizedToViewInventoryGroupIcpPages(SecurityInfo securityInfo) {
    return (
      securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)
        || securityInfo.isHasPrivilege(SecurityInfo.PRIV_ORM_MAINTAIN_LOGICAL_REPOS));
  }

  /**
   * Return true if the specified user is authorized to view the ICP page for a given box layout.
   * 
   * @param securityInfo - the logged in user's security info
   * @param boxLayoutId - the box layout id they want to view
   * @return boolean - true if the user can view the box layout, false otherwise
   */
  public static boolean isAuthorizedToViewBoxLayout(
    SecurityInfo securityInfo,
    String boxLayoutId) {
    return securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)
      || IcpUtils.isBoxLayoutAccessibleToUserByUsage(securityInfo, boxLayoutId);
  }

  /**
   * Return true if the object id exist in the involved object table.
   * 
   * @param securityInfo The SecurityInfo for the logged-in user.
   * @param objectId The object id.
   * @return true if the object exist in the ILTDS_BTX_INVOLVED_OBJECT table.
   */
  public static boolean isInvolvedObjectId(
    SecurityInfo securityInfo,
    String objectId) {

    boolean returnValue = false;

    if (ApiFunctions.isEmpty(objectId)) {
      return false;
    }

    // Check to see if objectId has a record in the involved object table.
    String queryString = "SELECT 1 FROM iltds_btx_involved_object WHERE object_id = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, objectId);
      rs = ApiFunctions.queryDb(pstmt, con);

      returnValue = rs.next();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return false; // unreached, keep compiler happy
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return returnValue;
  }
}
