package com.ardais.bigr.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.concept.BigrConcept;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.databeans.PolicyData;
import com.ardais.bigr.iltds.databeans.SampleType;
import com.ardais.bigr.iltds.databeans.SampleTypeConfiguration;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.Tag;

/**
 * Utility functions for policy functionality.
 */
public class PolicyUtils {

  public static final Comparator POLICY_NAME_ORDER = new PolicyNameComparator();

  private static class PolicyNameComparator implements Comparator, Serializable {
    static final long serialVersionUID = 6697927412115337444L;
    public int compare(Object o1, Object o2) {
      try {
        PolicyData pd1 = (PolicyData) o1;
        PolicyData pd2 = (PolicyData) o2;
        String pd1Name = pd1.getPolicyName();
        String pd2Name = pd2.getPolicyName();
        return pd1Name.compareToIgnoreCase(pd2Name);
      }
      catch (ClassCastException cce) {
        ApiFunctions.throwAsRuntimeException(cce);
        return 0; // unreached, keep compiler happy
      }
    }
  }

  /**
   * Return information about the policy with the specified id.
   * 
   * @param policyId The primary key of the policy.
   * @param exceptionIfNotExists If true, throw an exception if there is no policy
   *     with the specified id.  If false, return null if the policy doesn't exist.
   * @param resultClass The class of object to create to hold the result.  This must be
   *     {@link PolicyData} or a class that extends it.  This method only populates
   *     fields that exist on the PolicyData class itself.
   * @return the policy information
   */
  public static PolicyData getPolicyData(
    String policyId,
    boolean exceptionIfNotExists,
    Class resultClass) {

    PolicyData result = null;
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_ARD_POLICY);
      query.append(" ");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(".");
      query.append(DbConstants.POLICY_ID);
      query.append(" = ?");
      
      result = (PolicyData) resultClass.newInstance();

      result.setPolicyId(policyId);

      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setBigDecimal(1, new BigDecimal(policyId));
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      if (rs.next()) {
        result.populate(columns, rs);
      }
      else if (exceptionIfNotExists){
        throw new ApiException("Could not retrieve policy information for policy_id = " + policyId);
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

    //clean the policy of any invalid data form definitions
    cleanPolicy(result);

    return result;
  }

  /**
   * Return information about the policy with the specified policy id.
   * 
   * @param policyId The primary key of the policy.
   * @return the policy information
   */
  public static PolicyData getPolicyData(String policyId) {
    return getPolicyData(policyId, true, PolicyData.class);
  }

  /**
   * Return true if there is a policy with the specified id.
   * 
   * @param repositoryId The primary key of the policy.
   * @return true if there is a policy with the specified id.
   */
  public static boolean isExistingPolicy(String policyId) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_ARD_POLICY);
      query.append(" ");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(".");
      query.append(DbConstants.POLICY_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setBigDecimal(1, new BigDecimal(policyId));
      rs = ApiFunctions.queryDb(pstmt, con);

      return rs.next();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return false; // unreached, keep compiler happy
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }

  /**
   * Get a map of all policies.
   * @return the map of all policies.
   */
  public static Map getAllPolicyMap() {
    Map policyMap = new HashMap();
    
    List listPolicies = getAllPolicies();
    Iterator policies = listPolicies.iterator();
    
    while (policies.hasNext()) {
      PolicyData pd = (PolicyData) policies.next();
      policyMap.put(pd.getPolicyId(), pd);      
    }
    return policyMap;
  }


  /**
   * Get a list of all policies, sorted by name.
   * @return the list of all policies.
   */
  public static List getAllPolicies() {
    List result = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      
      // SELECT * FROM ard_policy p, es_ardais_account a
      // WHERE p.ARDAIS_ACCT_KEY = a.ARDAIS_ACCT_KEY
      // ORDER BY upper(a.ARDAIS_ACCT_COMPANY_DESC) ASC, upper(p.NAME) ASC

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_ARD_POLICY);
      query.append(" ");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(", ");
      query.append(DbConstants.TABLE_ARDAIS_ACCOUNT);
      query.append(" ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(".");
      query.append(DbConstants.POLICY_ARDAIS_ACCT_KEY);
      query.append(" = ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_KEY);
      query.append(" ORDER BY upper(");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_NAME);
      query.append(") ASC, upper(");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(".");
      query.append(DbConstants.POLICY_NAME);
      query.append(") ASC");

      pstmt = con.prepareStatement(query.toString());
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      while (rs.next()) {
        PolicyData pd = new PolicyData();
        pd.populate(columns, rs);
        result.add(pd);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    
    //clean the policies of any invalid data form definitions
    Iterator policyIterator = result.iterator();
    while (policyIterator.hasNext()) {
      cleanPolicy((PolicyData)policyIterator.next());
    }

    return result;
  }

  /**
   * Get a list of policies by id, sorted by name.
   * 
   * @param ids the ids of the policies to retrieve
   * 
   * @return the list of policies matching the ids passed in.
   */
  public static List getPoliciesByIds(List ids) {
    List result = new ArrayList();

    //if no ids were passed in then just return
    if (ids.size() < 1) {
      return result;
    }

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_ARD_POLICY);
      query.append(" ");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(".");
      query.append(DbConstants.POLICY_ID);
      query.append(" IN (");
      boolean addComma = false;
      for (int i = 0; i < ids.size(); i++) {
        if (addComma) {
          query.append(",");
        }
        query.append((String) ids.get(i));
        addComma = true;
      }
      query.append(")");
      query.append(" ORDER BY upper(");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(".");
      query.append(DbConstants.POLICY_NAME);
      query.append(") ASC");

      pstmt = con.prepareStatement(query.toString());
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      while (rs.next()) {
        PolicyData pd = new PolicyData();
        pd.populate(columns, rs);
        result.add(pd);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    //clean the policies of any invalid data form definitions
    Iterator policyIterator = result.iterator();
    while (policyIterator.hasNext()) {
      cleanPolicy((PolicyData)policyIterator.next());
    }

    return result;
  }

  /**
   * Get the policies to which the account has visiblity as a list ordered by name.
   * 
   * @param userid the user id
   * 
   * @return a list of the visible policies.
   */
  public static List getPoliciesByAccountId(String accountId) {
    return getPoliciesByAccountId(accountId, true, true, true);
  }
  
  /**
   * Get the policies to which the account has visiblity as a list ordered by name.
   * 
   * @param userid the user id
   * 
   * @return a list of the visible policies.
   */
  public static List getPoliciesByAccountId(String accountId,
    boolean allowCaseReleaseRequired,
    boolean allowConsentVerifyRequired,
    boolean allowForUnlinkedCases)
  {
    List result = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_ARD_POLICY);
      query.append(" WHERE ");
      query.append(DbConstants.POLICY_ARDAIS_ACCT_KEY);
      query.append(" = ? ");
      if (!allowCaseReleaseRequired) {
        query.append(" AND ");
        query.append(DbConstants.POLICY_CASE_RELEASE_REQUIRED);
        query.append(" = '");
        query.append(FormLogic.DB_NO);
        query.append("'");
      }
      if (!allowConsentVerifyRequired) {
        query.append(" AND ");
        query.append(DbConstants.POLICY_CONSENT_VERIFY_REQUIRED);
        query.append(" = '");
        query.append(FormLogic.DB_NO);
        query.append("'");
      }
      if (allowForUnlinkedCases) {
        query.append(" AND ");
        query.append(DbConstants.POLICY_ALLOW_FOR_UNLINKED_YN);
        query.append(" = '");
        query.append(FormLogic.DB_YES);
        query.append("'");
      }
      query.append(" ORDER BY upper(");
      query.append(DbConstants.POLICY_NAME);
      query.append(") ASC");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, accountId);
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      while (rs.next()) {
        PolicyData pd = new PolicyData();
        pd.populate(columns, rs);
        result.add(pd);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    //clean the policies of any invalid data form definitions
    Iterator policyIterator = result.iterator();
    while (policyIterator.hasNext()) {
      cleanPolicy((PolicyData)policyIterator.next());
    }
    
    return result;
  }

  /**
   * Get the policies that refer to the specified logical repository, sorted by policy name.
   * 
   * @param repositoryId The logical repository id.
   * 
   * @return The ordered list of related policies.
   */
  public static List getPoliciesRelatedToLogicalRepository(String repositoryId) {
    List result = new ArrayList();

    ResultSet rs = null;
    PreparedStatement pstmt = null;
    Connection con = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT * FROM ");
      query.append(DbConstants.TABLE_ARD_POLICY);
      query.append(" ");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(" WHERE (");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(".");
      query.append(DbConstants.POLICY_DEFAULT_LOGICAL_REPOS_ID);
      query.append(" = ? OR ");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(".");
      query.append(DbConstants.POLICY_RESTRICTED_LOGICAL_REPOS_ID);
      query.append(" = ?) ORDER BY upper(");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(".");
      query.append(DbConstants.POLICY_NAME);
      query.append(")");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, repositoryId);
      pstmt.setString(2, repositoryId);
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      while (rs.next()) {
        PolicyData pd = new PolicyData();
        pd.populate(columns, rs);
        result.add(pd);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    //clean the policies of any invalid data form definitions
    Iterator policyIterator = result.iterator();
    while (policyIterator.hasNext()) {
      cleanPolicy((PolicyData)policyIterator.next());
    }
    return result;
  }

  /**
   * Get the list of irb protocols related to the policy.
   * 
   * SELECT irbprotocol FROM es_ardais_irb WHERE policy_id = ?
   * 
   * @param policyId The policy id.
   * 
   * @return The list of related irb protocols.
   */
  public static List getIrbsRelatedToPolicy(String policyId) {
    List result = new ArrayList();

    ResultSet rs = null;
    PreparedStatement pstmt = null;
    Connection con = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT ");
      query.append(DbConstants.IRB_PROTOCOL);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_IRB);
      query.append(" WHERE ");
      query.append(DbConstants.IRB_POLICY_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, policyId);
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        String accountId = rs.getString(1);
        result.add(accountId);
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
   * The policy id as stored in the database is just a number.  In some situations
   * we need a prefixed, fixed-length id -- for example to have an identifiable id type
   * that can be returned by getDirectlyInvolvedObjects() to index a transaction.
   * This function returns the policy id in that form (or null/empty if the policy id is
   * null/empty). 
   * 
   * @return the list of prefixed policy ids.
   */
  public static IdList makePrefixedPolicyIds(IdList policyIds) {

    if (policyIds == null || policyIds.size() == 0) {
      return policyIds;
    }

    IdList prefixedPolicyIds = new IdList();
    Iterator iter = policyIds.iterator();
    while (iter.hasNext()) {
      String id = (String) iter.next();
      prefixedPolicyIds.add(FormLogic.makePrefixedPolicyId(id));
    }

    return prefixedPolicyIds;
  }
  
  public static SampleTypeConfiguration getSampleTypeConfiguration(String policyId) {
    SampleTypeConfiguration sampleTypeConfiguration = null;
    PolicyData policyData = getPolicyData(policyId);
    sampleTypeConfiguration = policyData.getSampleTypeConfiguration();
    return sampleTypeConfiguration;
  }
  
  //method to ensure that all data form definition references within a policy are to form
  //definitions that exist, are registration forms, and are enabled.
  public static void cleanPolicy(PolicyData policy) {
    FormDefinitionServiceResponse response;
    
    //first, make sure that any case registration form is valid
    String caseRegistrationFormId = policy.getCaseRegistrationFormId();
    if (!ApiFunctions.isEmpty(caseRegistrationFormId)) {
      response = FormDefinitionService.SINGLETON.findFormDefinitionById(caseRegistrationFormId);
      FormDefinition[] kcForms = response.getFormDefinitions();
      //if anything other than one form was found, return false
      if (kcForms.length != 1) {
        policy.setCaseRegistrationFormId(null);
      }
      else {
        if (!FormUtils.isRegistrationFormValid(kcForms[0], policy.getAccountId(), TagTypes.DOMAIN_OBJECT_VALUE_CASE)) {
          policy.setCaseRegistrationFormId(null);
        }
      }
    }
    
    //now, do the same for the sample types
    Iterator sampleTypeIterator = policy.getSampleTypeConfiguration().getSampleTypeList().iterator();
    while (sampleTypeIterator.hasNext()) {
      SampleType sampleType = (SampleType) sampleTypeIterator.next();
      String sampleTypeRegistrationFormId = sampleType.getRegistrationFormId();
      if (!ApiFunctions.isEmpty(sampleTypeRegistrationFormId)) {
        response = FormDefinitionService.SINGLETON.findFormDefinitionById(sampleTypeRegistrationFormId);
        FormDefinition[] kcForms = response.getFormDefinitions();
        //if anything other than one form was found, return false
        if (kcForms.length != 1) {
          sampleType.setRegistrationFormId(null);
        }
        else {
          if (!FormUtils.isRegistrationFormValid(kcForms[0], policy.getAccountId(), TagTypes.DOMAIN_OBJECT_VALUE_SAMPLE)) {
            sampleType.setRegistrationFormId(null);
          }
        }
      }
    }
  }
  
}

