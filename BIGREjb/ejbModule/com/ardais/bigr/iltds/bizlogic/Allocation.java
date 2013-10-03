package com.ardais.bigr.iltds.bizlogic;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.beans.AsmAccessBean;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.btx.BtxDetailsLogAllocateSingle;
import com.ardais.bigr.iltds.databeans.PolicyData;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.ProductType;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.BigrSecureRandom;

/**
 * Business logic helpers related to allocation (for example, determining which samples should
 * be restricted and what logical repositories things should go into initially).
 */
public class Allocation {

  /**
   * This is currently a static class so that constructor is private to prevent instantiation.
   */
  private Allocation() {
    super();
  }

  /**
   * Allocate the specified sample if it can be allocated and it has not already been
   * allocated.  Allocation means to decide if the sample should be restricted
   * and set its Allocation_ind property accordingly, and assign the sample to its initial
   * logical repository or repositories.
   * 
   * A sample can be allocated if its case policy is known.  Since currently all cases are
   * required to have a case policy, it should be possible to allocate any sample whose case
   * is known.  If the sample can't be allocated because the case policy is unknown or the
   * sample has already been allocated, this routine quietly doesn't do anything.
   */
  public static void allocate(SampleAccessBean sample, SecurityInfo secInfo) {
    try {
      // If the sample doesn't an ASM, we can't determine its case, so we can't determine its
      // case policy, so we can't allocate it.  Quietly do nothing in this situation.
      // If its does have an ASM, we will be able to determine the case policy since every
      // ASM has a case and every case has a policy.  We also quietly do nothing if the sample
      // has already been allocated, as indicated by an Allocation_ind that is empty or 'X'.
      String currentAlloc = sample.getAllocation_ind();
      if (!ApiFunctions.isEmpty(currentAlloc)
        && !FormLogic.ALL_UNALLOCATED_IND.equals(currentAlloc)) {
        // The sample has already been allocated, don't do it again.
        return;
      }
      AsmAccessBean asm = sample.getAsm();
      if (asm == null) {
        // The sample's ASM isn't known yet so we can't determine its case policy,
        // so we can't allocate it yet.  Samples like this come from being box scanned
        // before ASM Form Data Entry is done.
        return;
      }

      // If we get here, the sample can be allocated.
      PolicyData policy = IltdsUtils.getSamplePolicyFromAsm(asm);
      boolean shouldBeRestricted = shouldSampleBeRestricted(sample, policy);
      String allocationInd = (shouldBeRestricted ? FormLogic.ALL_RESTRICTED_IND : FormLogic.ALL_UNRESTRICTED_IND);
      String reposId =(shouldBeRestricted ? policy.getRestrictedLogicalReposId() : policy.getDefaultLogicalReposId());
      List inventoryGroupIds = new ArrayList();
      inventoryGroupIds.add(reposId);
      doAllocation(sample, allocationInd, inventoryGroupIds, policy.getPolicyName(), secInfo);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * Allocate the specified sample if it can be allocated and it has not already been
   * allocated.  Allocation means to decide if the sample should be restricted
   * and set its Allocation_ind property accordingly, and assign the sample to its initial
   * logical repository or repositories.  This version is used by the Create Imported Sample
   * functionality to create a derivative operation child.
   */
  public static void allocate(SampleAccessBean sample, List inventoryGroupIds, SecurityInfo secInfo) {
    String allocationInd = FormLogic.ALL_UNRESTRICTED_IND;
    doAllocation(sample, allocationInd, inventoryGroupIds, null, secInfo);
  }
  
  private static void doAllocation(SampleAccessBean sample, String allocationInd, List inventoryGroupIds, 
                            String policyName, SecurityInfo secInfo) {
    try {
      //set the allocation indicator
      sample.setAllocation_ind(allocationInd);
      sample.commitCopyHelper();

      //assign the sample to the appropriate inventory groups 
      IdList inventoryGroupList =  assignSampleToInventoryGroups(sample, inventoryGroupIds);
      
      //log the action
      BtxDetailsLogAllocateSingle btxDetails = new BtxDetailsLogAllocateSingle();
      btxDetails.setBeginTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
      btxDetails.setLoggedInUserSecurityInfo(secInfo);
      btxDetails.setSampleBarcodeId(((SampleKey) sample.__getKey()).sample_barcode_id);
      btxDetails.setSampleAlias(sample.getCustomerId());
      btxDetails.setAllocationInd(sample.getAllocation_ind());
      btxDetails.setPolicyName(policyName);
      btxDetails.setLogicalRepositoryIds(inventoryGroupList);
      Btx.perform(btxDetails, "iltds_allocation_logAllocateSingle");
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * Assign the specified sample to its initial logical repositories.
   * 
   * @param sample the sample
   * @param inventoryGroupIds the ids of the inventory groups to which the sample should be assigned
   * 
   * @return a list of the ids of the repositories that the sample was assigned to,
   *   or an empty list if not assigned to any repositories.
   */
  private static IdList assignSampleToInventoryGroups(SampleAccessBean sample, 
                                                      List inventoryGroupIds) throws Exception {
    IdList inventoryGroupList = new IdList();
    String sampleId = ((SampleKey) sample.__getKey()).sample_barcode_id;
    Iterator idIterator = inventoryGroupIds.iterator();
    String sql = "select 1 from ard_logical_repos_item where item_id=? and repository_id=?";
    String sql1 =
      "insert into ard_logical_repos_item (repository_id, item_id, item_type) "
        + "values (?, ?, '" + ProductType.SAMPLE + "')";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    try {
      con = ApiFunctions.getDbConnection();
      while (idIterator.hasNext()) {
        BigDecimal reposId = new BigDecimal((String)idIterator.next());
        // verify that the sample does not already belong to this logical repository -- MR 6778
        boolean exists = false;
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, sampleId);
        pstmt.setBigDecimal(2, reposId);
        results = pstmt.executeQuery();
        exists = results.next();
        results.close();
        // add it if it did not already exist...
        if (!exists) {
          pstmt = con.prepareStatement(sql1);
          pstmt.setBigDecimal(1, reposId);
          pstmt.setString(2, sampleId);
          pstmt.executeQuery();
          inventoryGroupList.add(reposId.toString());
        }
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, null);
    }
    return inventoryGroupList;
  }

  /**
   * Return true if the specified sample should be restricted based on the specified
   * policy.  We assume that the sample's allocation has not already be determined.
   * If the policy's allocation numerator is zero or if its allocation format
   * is "NONE", then the sample is not to be restricted.  If the policy's allocation format
   * is "ALL" then the same is eligible for restriction (though it may or may not actually be
   * made restricted, as described below).
   * 
   * If the sample is eligible for restriction based on the format test, then we use a
   * randomly-generated number to decide whether to restrict the sample.  The numerator
   * and denominator specified in the policy specify the proportion of samples that should
   * be restricted.  For example, if numerator == 1 and denominator == 5 then there should
   * be a 1-in-5 random chance the the sample is to be restricted.
   * 
   * @param sample the sample whose restriction status is to be determined
   * @param policy the policy to use to determine allocation parameters
   * 
   * @return true if the sample should be restricted
   */
  private static boolean shouldSampleBeRestricted(SampleAccessBean sample, PolicyData policy)
    throws Exception {

    String allocationFormat = policy.getAllocationFormat().toUpperCase();
    int numerator = new Integer(policy.getAllocationNumerator()).intValue();

    if (ApiFunctions.isEmpty(allocationFormat)
      || allocationFormat.equals("NONE")
      || numerator == 0) {
      return false;
    }
    if (!allocationFormat.equals("ALL")) {
      return false;
    }

    // If we get here we've passed the format-eligibility test, and we have to roll the dice
    // to see if the sample should be restricted.

    // random will be a number uniformly distributed between 0 (inclusive)
    // and denominator (exclusive).  We restrict the sample if the random
    // number is strictly less than the policy's numerator.
    //
    int random = BigrSecureRandom.getInstance().nextInt(new Integer(policy.getAllocationDenominator()).intValue());

    return (random < numerator);
  }
}
