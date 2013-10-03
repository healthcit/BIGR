package com.ardais.bigr.library.performers;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.FinderException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.es.beans.ArdaisaccountKey;
import com.ardais.bigr.es.beans.ArdaisuserAccessBean;
import com.ardais.bigr.es.beans.ArdaisuserKey;
import com.ardais.bigr.es.beans.ShoppingcartAccessBean;
import com.ardais.bigr.es.beans.ShoppingcartKey;
import com.ardais.bigr.es.beans.ShoppingcartdetailAccessBean;
import com.ardais.bigr.es.beans.ShoppingcartdetailKey;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.beans.SamplestatusAccessBean;
import com.ardais.bigr.iltds.bizlogic.RequestFinder;
import com.ardais.bigr.iltds.btx.BTXActionForward;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.RequestFilter;
import com.ardais.bigr.iltds.helpers.RequestSelect;
import com.ardais.bigr.iltds.helpers.RequestState;
import com.ardais.bigr.iltds.helpers.RequestType;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.javabeans.RequestDto;
import com.ardais.bigr.javabeans.RequestItemDto;
import com.ardais.bigr.javabeans.RnaData;
import com.ardais.bigr.library.btx.BTXDetailsAddToHoldList;
import com.ardais.bigr.library.btx.BTXDetailsGetSampleSummaryNoHistory;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.btx.BTXDetailsRemoveFromHoldList;
import com.ardais.bigr.library.helpers.ImplicitFilterContext;
import com.ardais.bigr.query.SampleSelectionSummary;
import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterSet;
import com.ardais.bigr.query.filters.RnaFilters;
import com.ardais.bigr.query.filters.SampleFilters;
import com.ardais.bigr.query.generator.FilterConsentNotPulled;
import com.ardais.bigr.query.generator.FilterConsentNotRevoked;
import com.ardais.bigr.query.generator.FilterNotInProject;
import com.ardais.bigr.query.generator.FilterNotOnHold;
import com.ardais.bigr.query.generator.FilterNotOnHoldForUser;
import com.ardais.bigr.query.generator.FilterRnaId;
import com.ardais.bigr.query.generator.FilterSampleId;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.userprofile.QueryProfile;
import com.ardais.bigr.util.IdValidator;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

public class BtxPerformerHoldListManager extends BtxTransactionPerformerBase {

  /**
   * Invoke the specified method on this class.  This is only meant to be
   * called from BtxTransactionPerformerBase, please don't call it from anywhere
   * else as a mechanism to gain access to private methods in this class.  Every
   * object that the BTX framework dispatches to must contain this
   * method definition, and its implementation should be exactly the same
   * in each class.  Please don't alter this method or its implementation
   * in any way.
   */
  protected BTXDetails invokeBtxEntryPoint(Method method, BTXDetails btxDetails) throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }

  /**
   * Retreive the list of sampleData's that are on a persons hold list.
   * @param securityInfo
   * @return A list of {@link ProductDto} objects.
   */
  private BTXDetailsGetSamples performGetHoldListData(BTXDetailsGetSamples btx) throws Exception {
    SecurityInfo securityInfo = btx.getLoggedInUserSecurityInfo();
    String[] ids = getHoldListIds(securityInfo);
    btx.setSampleIds(ids); // the IDs are set to the hold list IDs
    btx.setTransactionType("library_get_details");
    btx = (BTXDetailsGetSamples) Btx.perform(btx);
    categorizeHoldListItems(btx);
    return btx; // new btx with SampleData list and ColumnList
  }

  /**
   * Categorize the items in the supplied BTXDetails object as appropriate for a multi-category
   * hold list view.  Only categories listed in the BTXDetails object's getCategoriesToDetermine()
   * set will be populated.
   * 
   * @param btxDetails The BTXDetails object with the uncategorized hold list items already
   *     populated.
   */
  private void categorizeHoldListItems(BTXDetailsGetSamples btxDetails) {
    Set categoriesToDetermine = btxDetails.getCategoriesToDetermine();
    if (categoriesToDetermine == null || categoriesToDetermine.isEmpty()) {
      btxDetails.setDetailsResultCategories(Collections.EMPTY_MAP);
      return;
    }

    boolean isDetermineArdais =
      categoriesToDetermine.contains(BTXDetailsGetSamples.HOLD_CATEGORY_ARDAIS);
    boolean isDetermineBmsAvail =
      categoriesToDetermine.contains(BTXDetailsGetSamples.HOLD_CATEGORY_BMS_AVAILABLE);
    boolean isDetermineBmsPending =
      categoriesToDetermine.contains(BTXDetailsGetSamples.HOLD_CATEGORY_BMS_ON_PENDING_REQUEST);
    boolean isDetermineBmsUnavail =
      categoriesToDetermine.contains(BTXDetailsGetSamples.HOLD_CATEGORY_BMS_UNAVAILABLE);
    boolean isDetermineAnyBms =
      (isDetermineBmsAvail || isDetermineBmsPending || isDetermineBmsUnavail);

    List ardaisItems = new ArrayList();
    List bmsAvailItems = new ArrayList();
    List bmsPendingItems = new ArrayList();
    List bmsUnavailItems = new ArrayList();

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    List bmsItems = new ArrayList();

    List allItems = btxDetails.getSampleDetailsResult();

    Iterator iterAll = allItems.iterator();
    while (iterAll.hasNext()) {
      ProductDto item = (ProductDto) iterAll.next();

      if (!item.isBmsFromUsersPerspective(securityInfo)) {
        if (isDetermineArdais) {
          ardaisItems.add(item);
        }
      }
      else if (isDetermineAnyBms) {
        bmsItems.add(item);
      }
    }

    // If there are any items that are BMS items from the user's perspective,
    // we need to decide which of the three BMS-related categories it is in:
    //   - Items that are available to the user to be placed on requests.
    //   - Items that are currently on the user's pending research requests.
    //   - All other BMS items (we call this the "Unavailable BMS" category).
    //
    if (bmsItems.size() > 0) {
      // First, gather some information that will make categorizing the individual items faster.
      // Gathering this info is a bit expensive, so before we do it we make sure that there's
      // at least one user-BMS item on hold (bmsItems.size() > 0).  If the btxDetails didn't
      // specify that we should determine any of the BMS categories, bmsItems.size() will be
      // zero so we won't do any of this.  Since the logic for categorizing items within the
      // BMS categories is very interdependent, we always do all of the BMS category tests
      // even if the btxDetails only specifies that some of them should be returned.  For example,
      // the "unavailable" category is defined as BMS items that aren't in either of the other
      // two BMS categories, so to determine it we have to determine the others first.
      // It is possible that there are cases where we wouldn't really need to determine all of
      // the BMS categories, but figuring out exactly how that might work and getting it correct
      // in all cases seems to complicated for the potential benefit for now.

      Set itemIdsOnPendingRequests = getItemIdsOnPendingRequests(securityInfo);
      Set itemsPassingImplicitTests =
        getItemIdsPassingImplicitOnHoldFilters(bmsItems, securityInfo);

      Iterator iterBms = bmsItems.iterator();
      while (iterBms.hasNext()) {
        ProductDto item = (ProductDto) iterBms.next();
        String itemId = item.getId();

        if (itemIdsOnPendingRequests.contains(itemId)) {
          // Do this test before the "BMS Available Items" test because the tests we do to
          // identify available items might be coded to assume that they don't have to
          // exclude items on pending requests.
          bmsPendingItems.add(item);
        }
        else if (
          itemsPassingImplicitTests.contains(itemId) && item.isStoredLocally(securityInfo)) {
          bmsAvailItems.add(item);
        }
        else {
          bmsUnavailItems.add(item);
        }
      }
    }

    // Ok, we've categorized everything we were supposed to categories, now populate the
    // category map and put it onto the btxDetails object.
    {
      Map categoryMap = new HashMap();

      if (isDetermineArdais) {
        categoryMap.put(BTXDetailsGetSamples.HOLD_CATEGORY_ARDAIS, ardaisItems);
      }
      if (isDetermineBmsAvail) {
        categoryMap.put(BTXDetailsGetSamples.HOLD_CATEGORY_BMS_AVAILABLE, bmsAvailItems);
      }
      if (isDetermineBmsPending) {
        categoryMap.put(BTXDetailsGetSamples.HOLD_CATEGORY_BMS_ON_PENDING_REQUEST, bmsPendingItems);
      }
      if (isDetermineBmsUnavail) {
        categoryMap.put(BTXDetailsGetSamples.HOLD_CATEGORY_BMS_UNAVAILABLE, bmsUnavailItems);
      }

      btxDetails.setDetailsResultCategories(categoryMap);
    }
  }


  /**
   * Given a list of items that are already on a user's hold list, return the set of those
   * items that still pass all of the implicit tests that they would have had to have passed
   * to be put on hold in the first place, except for tests that we know wont pass because
   * the samples are already on hold (e.g. it doesn't check that SALES_STATUS is GENRELEASED).
   * This will NOT do the right checks if you pass in
   * samples that aren't on the user's hold list -- for example, it currently checks to see that
   * the samples are on hold but not that they're on hold by the current user, so if you passed
   * in an id from another user's hold list this would return incorrect results.
   * 
   * @param items The list of items ({@link ProductDto} objects), which must be items that
   *    are known to already be on the specified user's hold list.
   * @param securityInfo The user whose hold list the items are on.
   * 
   * @return The set of item ids that match the implicit filters.
   */
  private Set getItemIdsPassingImplicitOnHoldFilters(List items, SecurityInfo securityInfo) {
    if (ApiFunctions.isEmpty(items)) {
      return Collections.EMPTY_SET;
    }

    Set result = new HashSet();
    try {
      String[] allIds = new String[items.size()];
      int i = 0;
      for (Iterator iter = items.iterator(); iter.hasNext(); i++) {
        ProductDto item = (ProductDto) iter.next();
        allIds[i] = item.getId();
      }

      // Run the implicit filters against the tissue ids.
      String[] tissIds = IdValidator.validSampleIds(allIds);
      if (tissIds.length > 0) {
        FilterSet filters = new SampleFilters();
        filters.addFilter(new FilterSampleId(tissIds));

        BTXDetailsGetSampleSummaryNoHistory btx = new BTXDetailsGetSampleSummaryNoHistory();
        // Use implicit filters appropriate to this context, not the default implicit filters:
        btx.setFilterContext(ImplicitFilterContext.ON_HOLD);
        btx.setFilterSet(filters);
        btx.setProductDescription("HoldTissueFilterTest");
        btx.setLoggedInUserSecurityInfo(securityInfo);
        btx.setTransactionType("library_get_sample_summary");
        btx = (BTXDetailsGetSampleSummaryNoHistory) Btx.perform(btx);
        CollectionUtils.addAll(result, btx.getSampleSelectionSummary().getIds());
      }

      String[] rnaIds = IdValidator.validRnaIds(allIds);
      if (rnaIds.length > 0) {
        FilterSet filters = new RnaFilters();
        filters.addFilter(new FilterRnaId(rnaIds));

        BTXDetailsGetSampleSummaryNoHistory btx = new BTXDetailsGetSampleSummaryNoHistory();
        // Use implicit filters appropriate to this context, not the default implicit filters:
        btx.setFilterContext(ImplicitFilterContext.ON_HOLD);
        btx.setFilterSet(filters);
        btx.setProductDescription("HoldRnaFilterTest");
        btx.setLoggedInUserSecurityInfo(securityInfo);
        btx.setTransactionType("library_get_rna_summary");
        btx = (BTXDetailsGetSampleSummaryNoHistory) Btx.perform(btx);
        CollectionUtils.addAll(result, btx.getSampleSelectionSummary().getIds());
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    
    return result;
  }

  /**
   * @return The set of inventory item ids that are currently on pending research
   *    requests requested by the specified user.
   * @param securityInfo The user's SecurityInfo.
   */
  private Set getItemIdsOnPendingRequests(SecurityInfo securityInfo) {
    Set ids = new HashSet();

    RequestFilter filter = new RequestFilter();
    filter.setIncludeClosedRequests(false);
    filter.setRequestState(RequestState.PENDING);
    filter.setRequestType(RequestType.RESEARCH);
    filter.setRequesterUserId(securityInfo.getUsername());

    Iterator requests =
      RequestFinder.find(securityInfo, RequestSelect.ITEM_BASICS_ONLY, filter).iterator();
    while (requests.hasNext()) {
      RequestDto request = (RequestDto) requests.next();
      Iterator items = request.getItems().iterator();
      while (items.hasNext()) {
        RequestItemDto item = (RequestItemDto) items.next();
        ids.add(item.getItemId());
      }
    }

    return ids;
  }

  public SampleSelectionSummary getHoldListSummary(SecurityInfo securityInfo) {
    try {
      String[] allIds = getHoldListIds(securityInfo);
      if (allIds.length > 0) {
        // set up ID filters
        String[] sampleIds = IdValidator.validSampleIds(allIds);
        String[] rnaIds = IdValidator.validRnaIds(allIds);
        Map filterMap = new HashMap();
        if (sampleIds.length > 0) {
          Filter tissfilt = new FilterSampleId(sampleIds);
          filterMap.put(tissfilt.getKey(), tissfilt);
        }
        if (rnaIds.length > 0) {
          Filter rnafilt = new FilterRnaId(rnaIds);
          filterMap.put(rnafilt.getKey(), rnafilt);
        }

        // invoke the BTX query to get all summary data
        BTXDetailsGetSampleSummaryNoHistory btx = new BTXDetailsGetSampleSummaryNoHistory();
        btx.setLoggedInUserSecurityInfo(securityInfo);
        FilterSet filts = new QueryProfile(filterMap);
        btx.setFilterSet(filts);
        btx.setProductDescription("HoldListSummary");
        btx.setTransactionType("library_get_summary_no_implied");
        btx =
          (BTXDetailsGetSampleSummaryNoHistory) Btx.perform(btx);
        return btx.getSampleSelectionSummary();
      }
      else {
        return null;
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
  }

  public String[] getHoldListIds(SecurityInfo securityInfo) {
    try {
      ShoppingcartAccessBean shoppingCart = retrieveCart(securityInfo);
      ShoppingcartdetailAccessBean shoppingCartDetail = new ShoppingcartdetailAccessBean();
      AccessBeanEnumeration detailEnum =
        (AccessBeanEnumeration) shoppingCartDetail.findShoppingcartdetailByShoppingcart(
          (ShoppingcartKey) shoppingCart.__getKey());

      ArrayList sampleIds = new ArrayList();
      while (detailEnum.hasMoreElements()) {
        ShoppingcartdetailAccessBean detail =
          (ShoppingcartdetailAccessBean) detailEnum.nextElement();
        sampleIds.add(detail.getBarcode_id());
      }

      return (String[]) sampleIds.toArray(new String[] {
      });
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
  }

  /**
   * Get a map with all the RNA vial ID's on hold and an associated Integer saying
   * how much (in micrograms) is on hold.
   */
  public Map getHoldListAmounts(SecurityInfo securityInfo) {
    try {
      Map results = new HashMap();
      ShoppingcartAccessBean cart = retrieveCart(securityInfo);
      ShoppingcartKey key = (ShoppingcartKey) cart.__getKey();
      ShoppingcartdetailAccessBean cartDetail = new ShoppingcartdetailAccessBean();
      AccessBeanEnumeration detailEnum =
        (AccessBeanEnumeration) cartDetail.findShoppingcartdetailByShoppingcart(key);
      while (detailEnum.hasMoreElements()) {
        cartDetail = (ShoppingcartdetailAccessBean) detailEnum.nextElement();
        results.put(cartDetail.getBarcode_id(), cartDetail.getQuantity());
      }
      return results;
    }
    catch (Exception e) {
      throw new ApiException(e);
    }

    //      String[] ids = getHoldListIds(securityInfo);
    //      int len = ids.length;
    //      Map idToAmt = new HashMap(len*2);
    //      for (int i=0; i<len; i++) {
    //        Integer amtInt = ShoppingcartdetailAccessBean.getq(ids[i]);
    //        idToAmt.put(ids[i], amtInt);
    //      }
    //      return idToAmt;
  }

  /**
   * remove the items from the hold list by calling a stored procedure that both
   * removes them from es_shopping_cart_detail and also sets the sample status, which
   * in turn triggers an update to the iltds_sample table.
   */
  private BTXDetails performRemoveFromHoldList(BTXDetailsRemoveFromHoldList btx)
    throws SQLException {
    final String sep = ",";
    final int CHUNK_SIZE = 300;
    String[] sampleIds = btx.getSamples();

    if (!ApiFunctions.isEmpty(sampleIds)) {
      Connection conn = ApiFunctions.getDbConnection();
      CallableStatement cstmt = conn.prepareCall("begin BIGR_REMOVE_FROM_HOLD(?,?,?); end;");

      // MR 6787 -- we cannot pass in a limitless set of ids to the procedure
      // due to VARCHAR2 limitations.   Gregg has noted that there is enough
      // space for 363 sample ids, including delimeters, assuming 11 characters
      // which is the accurate number.  HOWEVER, since we do allow space in the
      // database for 12 character ids, I am assuming 13 characters including
      // the delimiter and thus setting CHUNK_SIZE to 300

      // determine number of chunks
      int chunks = (sampleIds.length / CHUNK_SIZE) + 1;
      int lastchunksize = sampleIds.length % CHUNK_SIZE;
      int chunkctr = 0;
      int chunksize = 0;
      // loop through each chunk
      while (chunkctr < chunks) {
        // take the chunk of ids and load them into a temporary array...

        String[] tmp_chunkIds = new String[CHUNK_SIZE];

        // for the last chunk, we need to adjust for 
        // the possibility of a smaller chunk size
        if ((chunkctr + 1) == chunks) {
          chunksize = lastchunksize;
        }
        else {
          chunksize = CHUNK_SIZE;
        }
        // fill up the temporary array
        for (int i = 0; i < chunksize; i++) {
          tmp_chunkIds[i] = sampleIds[(chunkctr * CHUNK_SIZE) + i];
        }
        // move from the temporary array to the re-sized array, so that we can...
        String[] chunkIds = new String[chunksize];
        for (int i = 0; i < chunksize; i++) {
          chunkIds[i] = tmp_chunkIds[i];
        }
        // ...create the String with values and separators to pass into the proc 
        String allIds = StringUtils.join(chunkIds, sep);
        // set userId, IDs, delimeter.  Set the userId to the logged in user,
        // unless the holdListOwner attribute on the btx details was set.
        String userId = btx.getLoggedInUserSecurityInfo().getUsername();
        if (!ApiFunctions.isEmpty(btx.getHoldListOwner())) {
          userId = btx.getHoldListOwner();
        }
        cstmt.setString(1, userId);
        cstmt.setString(2, allIds);
        cstmt.setString(3, sep);
        cstmt.executeUpdate();
        chunkctr++;
      }

    }

    btx.setActionForward(new BTXActionForward("success"));
    return btx;
  }

  /**
   * Add samples to a holdList, first checking to make sure all samples
   * are available to be placed on hold.  It returns a list of samples that cannot be placed on hold
   * or null if all samples were ok.
   * @param sampleIds a list of samples to be placed on hold
   * @param securityInfo the person who is perfoming the operation.
   * @return String[] an array of samplesIds that were not added to the hold list.
   */
  private BTXDetails performAddToHoldList(BTXDetailsAddToHoldList btx) throws Exception {
    Map idsAndAmts = btx.getIdsAndAmounts();
    String[] sampIds = (String[]) idsAndAmts.keySet().toArray(new String[0]);
    SecurityInfo secInfo = btx.getLoggedInUserSecurityInfo();

    // must have rna hold priv if any RNA ids in here
    if (!secInfo.isHasPrivilege(SecurityInfo.PRIV_HOLD_RNA_ADDRMV)
      && IdValidator.validRnaIds(sampIds).length > 0) {
      return btx.setActionForwardRetry(new BtxActionError("error.noPrivilege", "put RNA on Hold"));
    }

    String[] availSampleIds = availableSamples(sampIds, btx.getLoggedInUserSecurityInfo());

    // For externalusers, remove IDs unavailable because there's not enough left      
    //   convert to a set, do set operations, then convert back to String[]
    Set availSet = new HashSet(Arrays.asList(availSampleIds));
    Set insufficientSet = insufficientAmountRnaSamples(idsAndAmts, secInfo);
    if (!secInfo.isInRoleSystemOwner()) {
      availSet.removeAll(insufficientSet);
    }
    else { // for Ardais users, overdrawn amount is a warning, not unavailable
      Iterator it = insufficientSet.iterator();
      while (it.hasNext()) {
        String rnaid = (String) it.next();
        BtxActionError err = new BtxActionError("library.ss.warning.insufficentRnaAmount", rnaid);
        btx.addActionError(err); // warn, but do not set transaction to failed
      }
    }

    availSampleIds = (String[]) availSet.toArray(new String[availSet.size()]);

    if (ApiFunctions.isEmpty(availSampleIds)) {
      btx.setUnavailSamples(sampIds);
      btx.setActionForward(new BTXActionForward("success"));
      return btx;
    }

    // compute the unavailable ids by removing the available from the full list        
    String[] unavailSampleIds = null;
    Set unavailSet = new HashSet(Arrays.asList(sampIds));
    unavailSet.removeAll(availSet); // now only unavailable samples left in unavailSet
    unavailSampleIds = (String[]) unavailSet.toArray(new String[unavailSet.size()]);

    // Add samples to the shopping cart
    ShoppingcartAccessBean cart = retrieveCart(btx.getLoggedInUserSecurityInfo());
    int nextCartLine = getNextCartLine(btx.getLoggedInUserSecurityInfo());

    for (int i = 0; i < availSampleIds.length; i++) {
      placeHoldStatus(availSampleIds[i]);
      try {
        // need prod type, derived from SampleID
        String productType =
          availSampleIds[i].startsWith("RN")
            ? com.ardais.bigr.es.helpers.FormLogic.RNA_PRODUCT_TYPE
            : com.ardais.bigr.es.helpers.FormLogic.TISSUE_PRODUCT_TYPE;

        ShoppingcartdetailAccessBean shoppingCartDetail = new ShoppingcartdetailAccessBean();
        //                shoppingCartDetail.setInit_argShoppingcart((Shoppingcart) cart.getEJBRef());

        shoppingCartDetail.setInit_shopping_cart_line_number(
          new java.math.BigDecimal(nextCartLine++));
        shoppingCartDetail.setInit_acctId(secInfo.getAccount());
        shoppingCartDetail.setInit_userId(secInfo.getUsername());

        // to avoid 2-phase commit bug, obtain and pass in the actual Key data for the relationship
        ShoppingcartKey cartkey = (ShoppingcartKey) cart.getEJBRef().getPrimaryKey();
        shoppingCartDetail.setInit_cartNo(cartkey.shopping_cart_id);

        shoppingCartDetail.setInit_barcode_id((String) availSampleIds[i]);
        shoppingCartDetail.setInit_productType(productType);
        shoppingCartDetail.setCreation_dt(new java.sql.Timestamp((new Date()).getTime()));
        // temp code called here --
        shoppingCartDetail.setQuantity((Integer) idsAndAmts.get(availSampleIds[i]));
        // end temp code call
        shoppingCartDetail.commitCopyHelper();
      }
      catch (Exception e) {
        throw new ApiException(e);
      }

    }
    // Return unavailable samples
    btx.setUnavailSamples(unavailSampleIds);
    btx.setActionForward(new BTXActionForward("success"));
    return btx;
  }

  /************************************************************************
   **********  Removed limit on how much a single external user can have on hold, overall
      // return an error if the request will put the user over their limit.  null if request is OK
      private BtxActionError generateOverLimitError(Map idsAndAmts, SecurityInfo secInfo) {
          String[] ids = (String[]) idsAndAmts.keySet().toArray(new String[0]);
          int holdAmt = getTotalHoldAmount(secInfo.getUsername());
          for (int i=0; i<ids.length; i++) {
            Integer amt = (Integer) idsAndAmts.get(ids[i]);
            if (amt!=null)
              holdAmt += amt.intValue();
          }
          int limit;
          if (secInfo.isInRoleCustomer())
            limit = SampleSelectionConstants.MAX_CUSTOMER_USER_RNA_ON_HOLD;
          else if (secInfo.isInRoleDi())
            limit = SampleSelectionConstants.MAX_DI_USER_RNA_ON_HOLD;
          else if (secInfo.isInRoleSystemOwner()) 
            limit = Integer.MAX_VALUE;
          else
            throw new ApiException("Unknown User Role for " + secInfo.getUsername());
          if (holdAmt > limit)  {
            String limitstr = Integer.toString(limit);
            String amtstr = Integer.toString(holdAmt);
            return new BtxActionError("library.ss.confirm.overHoldLimit", amtstr, limitstr);
          }      
          else {
            return null; // null means no error
          }
      }
      **********************/

  /**
   * Method getTotalHoldAmount.
   * @param userName the User ID of the user to retreive the total for
   */
  /********   not used.  total amount across vialID's is not limited.
  private int getTotalHoldAmount(String userName) {
    String sql = 
      "SELECT SUM(quantity) AS hold_amt FROM es_shopping_cart_detail WHERE product_type = 'RN' and ardais_user_id = ?";
    List amtStrings = ApiFunctions.runQueryForSingleValueList(sql, userName);
    // We now have a List of zero or one Strings that tell us the amount on hold
    if (amtStrings.isEmpty()) // no records means no sum at all.  no records is zero hold amt.
      return 0;
    else 
      return Integer.parseInt((String)amtStrings.get(0));
  }
  *******/
  private Set insufficientAmountRnaSamples(Map idsAndAmounts, SecurityInfo secInfo)
    throws Exception {
    // build list of ids and amounts as Strings
    List ids = new ArrayList(idsAndAmounts.size());
    List amts = new ArrayList(idsAndAmounts.size());
    Iterator it = idsAndAmounts.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry e = (Map.Entry) it.next();
      String id = (String) e.getKey();
      Integer amt = (Integer) e.getValue();
      // only capture RNA IDs with amounts specified
      if (amt != null && IdValidator.validRnaId(id)) {
        ids.add(id);
        amts.add(amt);
      }
    }

    BTXDetailsGetSamples btx = new BTXDetailsGetSamples();
    btx.setLoggedInUserSecurityInfo(secInfo);
    btx.setSampleIds((String[]) ids.toArray(new String[ids.size()]));
    btx.setTransactionType("library_get_details");
    btx = (BTXDetailsGetSamples) Btx.perform(btx);
    if (btx.isHasException()) {
      throw new ApiException(btx.getExceptionText());
    }
    List rna = btx.getSampleDetailsResult();

    Set unavailIds = new HashSet();
    int len = rna.size();
    for (int i = 0; i < len; i++) {
      ProductDto sd = (ProductDto) rna.get(i);
      RnaData rd = sd.getRnaData();
      if (rd == null) // all RNA ids, so all have rna data
        throw new ApiException("No rna data for hold amount check: " + idsAndAmounts.keySet());
      String id = rd.getRnaVialId();
      Integer reqAmt = (Integer) idsAndAmounts.get(id);
      Integer availAmt = rd.getRnaAmountAvailable();
      int avail = availAmt == null ? 0 : availAmt.intValue();
      if (reqAmt != null && avail < reqAmt.intValue()) {
        unavailIds.add(id);
      }
    }
    return unavailIds;
  }

  /**
   * Method retrieveCart.
   * @param user
   * @param account
   * @return ShoppingcartAccessBean
   * @throws Exception
   */
  private ShoppingcartAccessBean retrieveCart(SecurityInfo securityInfo) {
    ShoppingcartAccessBean shoppingCart = null;
    String user = securityInfo.getUsername();
    String account = securityInfo.getAccount();

    try {
      // Find User's Shopping Cart
      shoppingCart =
        new ShoppingcartAccessBean(
          new ShoppingcartKey(
            new java.math.BigDecimal(1),
            new ArdaisuserKey(user, new ArdaisaccountKey(account))));
      // do we really change the update date every time we view or retreive the cart?
      shoppingCart.setLast_update_dt(new java.sql.Timestamp((new Date()).getTime()));
      shoppingCart.commitCopyHelper();
    }
    catch (FinderException fe) {
      // If Cart is not found then create one.
      try {
        ArdaisuserAccessBean userAB =
          new ArdaisuserAccessBean(new ArdaisuserKey(user, new ArdaisaccountKey(account)));

        shoppingCart = new ShoppingcartAccessBean();

        //                shoppingCart.setInit_argArdaisuser((Ardaisuser) userAB.getEJBRef());
        ArdaisuserKey userKey = (ArdaisuserKey) userAB.getEJBRef().getPrimaryKey();
        shoppingCart.setInit_acctId(userKey.ardaisaccount_ardais_acct_key);
        shoppingCart.setInit_userId(userKey.ardais_user_id);

        shoppingCart.setInit_shopping_cart_id(new java.math.BigDecimal(1));
        shoppingCart.setCart_create_date(new java.sql.Timestamp((new Date()).getTime()));
        shoppingCart.setLast_update_dt(new java.sql.Timestamp((new Date()).getTime()));
        shoppingCart.commitCopyHelper();
      }
      catch (Exception e) {
        throw new ApiException(e);
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    return shoppingCart;
  }

  /**
   * Returns a list of the available samples from the list of chosen samples.
   * This contains logic specific to the Ardais Account which will prevent
   * users from ardais from putting items on hold even though they are able to 
   * browse such samples.
   * @param sampleIds
   * @return String[]
   */
  private String[] availableSamples(String[] sampleIds, SecurityInfo securityInfo)
    throws Exception {
    String[] sampIds = availableTissueSamples(sampleIds, securityInfo);
    String[] rnaIds = availableRnaSamples(sampleIds, securityInfo);
    String[] allIds = (String[]) ApiFunctions.concatenate(sampIds, rnaIds);
    return allIds;
  }

  /**
   * do half the work of getting available samples -- get the available tissue samples.
   */
  private String[] availableTissueSamples(String[] allIds, SecurityInfo securityInfo)
    throws Exception {

    // verify that we have some Tissue samples to screen for.
    String[] tissIds = IdValidator.validSampleIds(allIds);
    if (tissIds.length == 0)
      return new String[0];

    FilterSet filters = new SampleFilters();
    filters.addFilter(new FilterConsentNotPulled());  // MR7860
    filters.addFilter(new FilterConsentNotRevoked()); // MR7860
    filters.addFilter(new FilterSampleId(tissIds));
    filters.addFilter(new FilterNotOnHold());
    filters.addFilter(new FilterNotInProject());

    BTXDetailsGetSampleSummaryNoHistory btx = new BTXDetailsGetSampleSummaryNoHistory();
    btx.setFilterSet(filters);
    btx.setProductDescription("HoldTissueSummary");
    btx.setLoggedInUserSecurityInfo(securityInfo);
    btx.setTransactionType("library_get_sample_summary");
    btx = (BTXDetailsGetSampleSummaryNoHistory) Btx.perform(btx);
    return btx.getSampleSelectionSummary().getIds();
  }

  /**
   * do half the work of getting available samples -- get the available RNA samples.
   */
  private String[] availableRnaSamples(String[] allIds, SecurityInfo securityInfo)
    throws Exception {

    // @todo  not in pts project

    // verify there are RNA ids to screen for
    String[] rnaIds = IdValidator.validRnaIds(allIds);
    if (rnaIds.length == 0)
      return new String[0];

    FilterSet filters = new RnaFilters();
    filters.addFilter(new FilterRnaId(rnaIds));
    filters.addFilter(new FilterNotOnHoldForUser(securityInfo.getUsername()));

    BTXDetailsGetSampleSummaryNoHistory btx = new BTXDetailsGetSampleSummaryNoHistory();
    btx.setFilterSet(filters);
    btx.setProductDescription("HoldRnaSummary");
    btx.setLoggedInUserSecurityInfo(securityInfo);
    btx.setTransactionType("library_get_rna_summary");
    btx = (BTXDetailsGetSampleSummaryNoHistory) Btx.perform(btx);
    return btx.getSampleSelectionSummary().getIds();
  }

  /**
   * Method placeHold.
   * @param sampleId
   */
  private void placeHoldStatus(String sampleId) {
    boolean isASample = false;

    ValidateIds validId = new ValidateIds();

    // @todo:  Validate RNA as well
    // for now only validate Tissue Samples b/c SampleBean is for tissue only
    if (!ApiFunctions.isEmpty(validId.validate(sampleId, ValidateIds.TYPESET_SAMPLE, false))) {
      try {
        new SampleAccessBean(new SampleKey(sampleId));
      }
      catch (Exception e) {
        throw new ApiException(e);
      }
      isASample = true;
    }

    if (isASample) {
      // Only samples (not RNAs) can have statuses, so we only create the add-to-cart
      // status in that case
      try {
        SamplestatusAccessBean sampleStatus = new SamplestatusAccessBean();
        sampleStatus.setInit_argSample(new SampleKey(sampleId));
        sampleStatus.setInit_argSample_status_datetime(
          new Timestamp((new java.util.Date()).getTime()));
        sampleStatus.setInit_argStatus_type_code(FormLogic.SMPL_ADDTOCART);
        sampleStatus.commitCopyHelper();
      }
      catch (Exception e) {
        throw new ApiException(e);
      }
    }
  }

  /**
   * Method getNextCartLine.
   * @param securityInfo
   * @return int
   */
  private int getNextCartLine(SecurityInfo securityInfo) {
    String user = securityInfo.getUsername();
    String account = securityInfo.getAccount();
    int nextLine = 0;

    try {
      // Find current cart items and line numbers.

      ShoppingcartdetailAccessBean shoppingCartDetail = new ShoppingcartdetailAccessBean();
      AccessBeanEnumeration scdEnum =
        (AccessBeanEnumeration) shoppingCartDetail.findByUserAccountOrderByLineNumber(
          user,
          account);

      if (scdEnum.hasMoreElements()) {
        ShoppingcartdetailAccessBean tempDetail =
          (ShoppingcartdetailAccessBean) scdEnum.nextElement();
        nextLine =
          ((ShoppingcartdetailKey) tempDetail.__getKey()).shopping_cart_line_number.intValue() + 1;
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    return nextLine;
  }

}
