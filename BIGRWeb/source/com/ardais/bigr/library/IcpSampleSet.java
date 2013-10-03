package com.ardais.bigr.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.query.SampleResults;
import com.ardais.bigr.query.SampleSelectionSummary;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * @author jesielionis
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class IcpSampleSet extends SampleResults {

  private String _icpItemId;

  /**
   * Constructor
   */
  public IcpSampleSet(SecurityInfo secInfo, String icpItemId) {
    super(secInfo);
    _icpItemId = icpItemId;
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSampleData()
   */
  public BTXDetailsGetSamples getSampleData(BTXDetailsGetSamples btx) {
    try {
      btx.setSampleIds(getSampleIds(_icpItemId, btx));
      btx.setTransactionType("library_get_details");
      btx = (BTXDetailsGetSamples) Btx.perform(btx);

      // Get the sample types from the sample ids.
      String[] sampleIds = btx.getSampleIds();
      Set sampleTypes = IltdsUtils.getSampleTypesBySampleIds(ApiFunctions.safeToList(sampleIds));
      btx.setSampleTypes(sampleTypes);

      return btx;
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
  }

  /**
   * Method to get a String[] of the item ids on the specified ICP item id.  Note that this method
   * intentionally only filters samples by Inventory Group and nothing else (i.e. it doesn't
   * use BtxPerformerSampleSelection to get the sample summary).  Currently, the ICP item id must
   * be either a case or sample id.
   */
  private String[] getSampleIds(String icpItemId, BTXDetails btx) {
    ValidateIds validator = new ValidateIds();
    String validatedId = validator.validate(icpItemId, null, false);

    if (validatedId == null) {
      throw new ApiException("Invalid id: " + icpItemId);
    }

    String validatedIdType = validator.getType();

    if (ValidateIds.TYPESET_SAMPLE.contains(validatedIdType)) {
      return getSampleSampleIds(validatedId, btx);
    }
    else if (ValidateIds.TYPESET_CASE.contains(validatedIdType)) {
      return getCaseSampleIds(validatedId, btx);
    }
    else if (ValidateIds.TYPESET_DONOR.contains(validatedIdType)) {
      return getDonorSampleIds(validatedId, btx);
    }
    else {
      throw new ApiException("Unsupported id type, id = " + validatedId);
    }
  }

  /**
   * Method to get a String[] of the item ids on the case.  Note that this method
   * intentionally only filters samples by Inventory Group and nothing else (i.e. it doesn't
   * use BtxPerformerSampleSelection to get the sample summary).
   */
  private String[] getCaseSampleIds(String caseId, BTXDetails btx) {
    String[] ids = null;

    SecurityInfo securityInfo = btx.getLoggedInUserSecurityInfo();

    boolean allIG = securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS);

    StringBuffer buff = new StringBuffer(512);
    buff.append("select ");
    // Added hint for MR 7567.  Sometimes very bad query plans were being used.
    if (!allIG) {
      buff.append(" /*+ leading(");
      buff.append(DbAliases.TABLE_SAMPLE);
      buff.append(") index(");
      buff.append(DbAliases.TABLE_SAMPLE);
      buff.append(" ILTDS_SAMPLE_IN7) use_nl(");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_ITEM);
      buff.append(") use_nl(");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
      buff.append(") */ ");
    }
    // Added "distinct" for MR 7386.
    buff.append(" distinct ");
    buff.append(DbAliases.TABLE_SAMPLE);
    buff.append(".");
    buff.append(DbConstants.SAMPLE_ASM_ID);
    buff.append(", ");
    buff.append(DbAliases.TABLE_SAMPLE);
    buff.append(".");
    buff.append(DbConstants.SAMPLE_ID);
    buff.append(" from ");
    buff.append(DbConstants.TABLE_SAMPLE);
    buff.append(" ");
    buff.append(DbAliases.TABLE_SAMPLE);
    if (!allIG) {
      buff.append(", ");
      buff.append(DbConstants.TABLE_LOGICAL_REPOS_ITEM);
      buff.append(" ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_ITEM);
      buff.append(", ");
      buff.append(DbConstants.TABLE_LOGICAL_REPOS_USER);
      buff.append(" ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
    }
    buff.append(" where ");
    buff.append(DbAliases.TABLE_SAMPLE);
    buff.append(".");
    buff.append(DbConstants.SAMPLE_CONSENT_ID);
    buff.append(" = ?");
    if (!allIG) {
      buff.append(" and ");
      buff.append(DbAliases.TABLE_SAMPLE);
      buff.append(".");
      buff.append(DbConstants.SAMPLE_ID);
      buff.append(" = ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_ITEM);
      buff.append(".");
      buff.append(DbConstants.LOGICAL_REPOS_ITEM_ITEM_ID);
      buff.append(" and ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_ITEM);
      buff.append(".");
      buff.append(DbConstants.LOGICAL_REPOS_ITEM_REPOSITORY_ID);
      buff.append(" = ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
      buff.append(".");
      buff.append(DbConstants.LOGICAL_REPOS_USER_REPOSITORY_ID);
      buff.append(" and ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
      buff.append(".");
      buff.append(DbConstants.LOGICAL_REPOS_USER_USER_ID);
      buff.append(" = ?");
    }
    buff.append(" order by ");
    buff.append(DbAliases.TABLE_SAMPLE);
    buff.append(".");
    buff.append(DbConstants.SAMPLE_ASM_ID);
    buff.append(", ");
    buff.append(DbAliases.TABLE_SAMPLE);
    buff.append(".");
    buff.append(DbConstants.SAMPLE_ID);

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(buff.toString());
      pstmt.setString(1, caseId);
      if (!allIG) {
        pstmt.setString(2, securityInfo.getUsername());
      }
      rs = ApiFunctions.queryDb(pstmt, con);
      ArrayList list = new ArrayList();
      while (rs.next()) {
        list.add((String) rs.getString(DbConstants.SAMPLE_ID));
      }
      //convert to a String array
      ids = (String[]) list.toArray(new String[list.size()]);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return ids;
  }

  /**
   * Method to get a String[] of the item ids on the consent.  Note that this method
   * intentionally only filters samples by Inventory Group and nothing else (i.e. it doesn't
   * use BtxPerformerSampleSelection to get the sample summary).
   */
  private String[] getDonorSampleIds(String donorId, BTXDetails btx) {
    String[] ids = null;

    SecurityInfo securityInfo = btx.getLoggedInUserSecurityInfo();

    boolean allIG = securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS);

    StringBuffer buff = new StringBuffer(512);
    buff.append("select ");
    // For the getCaseSampleIds method, we added a hint for MR 7567.
    // Sometimes very bad query plans were being used.  We add a similar hint here for donors.
    if (!allIG) {
      buff.append(" /*+ leading(");
      buff.append(DbAliases.TABLE_SAMPLE);
      buff.append(") index(");
      buff.append(DbAliases.TABLE_SAMPLE);
      buff.append(" ILTDS_SAMPLE_IN8) use_nl(");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_ITEM);
      buff.append(") use_nl(");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
      buff.append(") */ ");
    }
    // Added "distinct" for MR 7386.
    buff.append(" distinct ");
    buff.append(DbAliases.TABLE_SAMPLE);
    buff.append(".");
    buff.append(DbConstants.SAMPLE_CONSENT_ID);
    buff.append(", ");
    buff.append(DbAliases.TABLE_SAMPLE);
    buff.append(".");
    buff.append(DbConstants.SAMPLE_ASM_ID);
    buff.append(", ");
    buff.append(DbAliases.TABLE_SAMPLE);
    buff.append(".");
    buff.append(DbConstants.SAMPLE_ID);
    buff.append(" from ");
    buff.append(DbConstants.TABLE_SAMPLE);
    buff.append(" ");
    buff.append(DbAliases.TABLE_SAMPLE);
    if (!allIG) {
      buff.append(", ");
      buff.append(DbConstants.TABLE_LOGICAL_REPOS_ITEM);
      buff.append(" ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_ITEM);
      buff.append(", ");
      buff.append(DbConstants.TABLE_LOGICAL_REPOS_USER);
      buff.append(" ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
    }
    buff.append(" where ");
    buff.append(DbAliases.TABLE_SAMPLE);
    buff.append(".");
    buff.append(DbConstants.SAMPLE_ARDAIS_ID);
    buff.append(" = ?");
    if (!allIG) {
      buff.append(" and ");
      buff.append(DbAliases.TABLE_SAMPLE);
      buff.append(".");
      buff.append(DbConstants.SAMPLE_ID);
      buff.append(" = ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_ITEM);
      buff.append(".");
      buff.append(DbConstants.LOGICAL_REPOS_ITEM_ITEM_ID);
      buff.append(" and ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_ITEM);
      buff.append(".");
      buff.append(DbConstants.LOGICAL_REPOS_ITEM_REPOSITORY_ID);
      buff.append(" = ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
      buff.append(".");
      buff.append(DbConstants.LOGICAL_REPOS_USER_REPOSITORY_ID);
      buff.append(" and ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
      buff.append(".");
      buff.append(DbConstants.LOGICAL_REPOS_USER_USER_ID);
      buff.append(" = ?");
    }
    buff.append(" order by ");
    buff.append(DbAliases.TABLE_SAMPLE);
    buff.append(".");
    buff.append(DbConstants.SAMPLE_CONSENT_ID);
    buff.append(", ");
    buff.append(DbAliases.TABLE_SAMPLE);
    buff.append(".");
    buff.append(DbConstants.SAMPLE_ASM_ID);
    buff.append(", ");
    buff.append(DbAliases.TABLE_SAMPLE);
    buff.append(".");
    buff.append(DbConstants.SAMPLE_ID);

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(buff.toString());
      pstmt.setString(1, donorId);
      if (!allIG) {
        pstmt.setString(2, securityInfo.getUsername());
      }
      rs = ApiFunctions.queryDb(pstmt, con);
      ArrayList list = new ArrayList();
      while (rs.next()) {
        list.add((String) rs.getString(DbConstants.SAMPLE_ID));
      }
      //convert to a String array
      ids = (String[]) list.toArray(new String[list.size()]);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return ids;
  }

  /**
   * Method to get a String[] of the visible sample id for the given sample.  Note that this method
   * intentionally only filters samples by Inventory Group and nothing else (i.e. it doesn't
   * use BtxPerformerSampleSelection to get the sample summary).  This method returns an array
   * for consistency with other id types, but in practice the result will either have one element
   * or no elements depending on whether the user has inventory group access to the sample or not.
   */
  private String[] getSampleSampleIds(String sampleId, BTXDetails btx) {
    String[] ids = null;

    SecurityInfo securityInfo = btx.getLoggedInUserSecurityInfo();

    boolean allIG = securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS);

    StringBuffer buff = new StringBuffer(512);
    buff.append("select ");
    // Added hint for MR 7567.  Sometimes very bad query plans were being used.
    if (!allIG) {
      buff.append(" /*+ leading(");
      buff.append(DbAliases.TABLE_SAMPLE);
      buff.append(") index(");
      buff.append(DbAliases.TABLE_SAMPLE);
      buff.append(" PK_ILTDS_SAMPLE) use_nl(");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_ITEM);
      buff.append(") use_nl(");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
      buff.append(") */ ");
    }
    // Added "distinct" for MR 7386.
    buff.append(" distinct ");
    buff.append(DbAliases.TABLE_SAMPLE);
    buff.append(".");
    buff.append(DbConstants.SAMPLE_ID);
    buff.append(" from ");
    buff.append(DbConstants.TABLE_SAMPLE);
    buff.append(" ");
    buff.append(DbAliases.TABLE_SAMPLE);
    if (!allIG) {
      buff.append(", ");
      buff.append(DbConstants.TABLE_LOGICAL_REPOS_ITEM);
      buff.append(" ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_ITEM);
      buff.append(", ");
      buff.append(DbConstants.TABLE_LOGICAL_REPOS_USER);
      buff.append(" ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
    }
    buff.append(" where ");
    buff.append(DbAliases.TABLE_SAMPLE);
    buff.append(".");
    buff.append(DbConstants.SAMPLE_ID);
    buff.append(" = ?");
    if (!allIG) {
      buff.append(" and ");
      buff.append(DbAliases.TABLE_SAMPLE);
      buff.append(".");
      buff.append(DbConstants.SAMPLE_ID);
      buff.append(" = ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_ITEM);
      buff.append(".");
      buff.append(DbConstants.LOGICAL_REPOS_ITEM_ITEM_ID);
      buff.append(" and ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_ITEM);
      buff.append(".");
      buff.append(DbConstants.LOGICAL_REPOS_ITEM_REPOSITORY_ID);
      buff.append(" = ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
      buff.append(".");
      buff.append(DbConstants.LOGICAL_REPOS_USER_REPOSITORY_ID);
      buff.append(" and ");
      buff.append(DbAliases.TABLE_LOGICAL_REPOS_USER);
      buff.append(".");
      buff.append(DbConstants.LOGICAL_REPOS_USER_USER_ID);
      buff.append(" = ?");
    }

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(buff.toString());
      pstmt.setString(1, sampleId);
      if (!allIG) {
        pstmt.setString(2, securityInfo.getUsername());
      }
      rs = ApiFunctions.queryDb(pstmt, con);
      ArrayList list = new ArrayList();
      while (rs.next()) {
        list.add((String) rs.getString(DbConstants.SAMPLE_ID));
      }
      //convert to a String array
      ids = (String[]) list.toArray(new String[list.size()]);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return ids;
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSummary()
   */
  public SampleSelectionSummary getSummary() {
    return null;
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSampleIds()
   */
  public String[] getSampleIds(int chunk) {
    throw new UnsupportedOperationException("IcpSampleSet does not support getSampleIds()");
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#clearSelectedIds()
   */
  public void clearSelectedIds() {
    // do nothing - I do not ever have any Ids selected
  }

  public void addSelectedIds(String[] ignore) {
    // do nothing - I do not ever have any Ids selected
  }

  public void clearSelectedIdsForCurrentChunk() {
    // do nothing - I do not ever have any Ids selected
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getSelectedIds()
   */
  public String[] getSelectedIds() {
    return new String[0];
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getSampleAmounts()
   */
  public Map getSampleAmounts() {
    return Collections.EMPTY_MAP;
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getDisplay()
   */
  public String getDisplay() {
    return "Icp Sample Set";
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getProductType()
   */
  public String getProductType() {
    return "";
  }
}
