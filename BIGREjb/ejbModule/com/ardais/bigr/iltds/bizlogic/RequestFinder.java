package com.ardais.bigr.iltds.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.assistants.Address;
import com.ardais.bigr.iltds.helpers.ProductType;
import com.ardais.bigr.iltds.helpers.RequestFilter;
import com.ardais.bigr.iltds.helpers.RequestSelect;
import com.ardais.bigr.iltds.helpers.RequestState;
import com.ardais.bigr.iltds.helpers.RequestType;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.javabeans.RequestBoxDto;
import com.ardais.bigr.javabeans.RequestDto;
import com.ardais.bigr.javabeans.RequestItemDto;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.DbUtils;

/**
 * This class provides static methods that return {@link RequestDto} objects that
 * match various criteria.
 * 
 * <p>When you select full box details using the {@link RequestSelect#BOX_INFO_DETAILS} selector
 * code, the query results returned represents the box contents at request fulfillment time.  That 
 * is, if box XYZ was used to fulfill a request and sample 1 was placed into cell A1, sample 2 was 
 * placed into cell B5, and sample 3 was placed into cell C7 during the fulfillment, then the box 
 * details returned will show sample 1 in cell A1, sample 2 in cell B5, and sample 3 in cell C7
 * even though that box may currently hold a completely different set of samples.
 */
public class RequestFinder {
  private static final String TABLE_ALIAS_REQUEST = "req";

  /**
   * This class has only static methods so make this private to prevent instantiation. 
   */
  private RequestFinder() {
    super();
  }

  /**
   * Return the requests that match the specified filters.
   * Please see the comments at the beginning of this class for important information
   * about the contents of box information in query results in certain situations.
   * 
   * @param secInfo The security info of the current user.
   * @param selector The specification of which request details to populate in the result.
   * @param filter The specification of which requests to return.
   * @return The list of request, or an empty list if there are no matching requests.  Each
   *   item in the list will be a {@link RequestDto} object.
   */
  public static List find(SecurityInfo secInfo, RequestSelect selector, RequestFilter filter) {
    if (selector == null) {
      throw new IllegalArgumentException("The selector must not be null.");
    }
    if (filter == null) {
      throw new IllegalArgumentException("The filter must not be null.");
    }
    if (!selector.isSomethingSelected()) {
      throw new IllegalArgumentException("The selector must specify that at least one piece of information is to be included.");
    }

    String requestWhereClauses = filter.getSqlWhereClauses(TABLE_ALIAS_REQUEST);

    // We always do a basic request query to find the requests and put them into the
    // proper order, even if selector.isIncludeBasic is false.  When selector.isIncludeBasic
    // is false, the only basic property that will be defined in the resulting RequestDto
    // objects will be the request id.
    //
    List requests = queryRequestBasics(selector, filter, requestWhereClauses);
    // The next call is a noop if item info isn't selected in selector.
    Map requestMap = queryRequestItems(secInfo, requests, selector, filter, requestWhereClauses);
    // The next call is a noop if box info isn't selected in selector.
    requestMap =
      queryRequestBoxes(secInfo, requests, requestMap, selector, filter, requestWhereClauses);

    return requests;
  }

  /**
   * Return the request with the specified id if one exists, otherwise return null.
   * Please see the comments at the beginning of this class for important information
   * about the accuracy of box information in query results in certain situations.
   * 
   * @param secInfo The security info of the current user.
   * @param selector The specification of which request details to populate in the result.
   * @param requestId The id of the request to return.
   * @return The request, or null if there is no request with the specified id.
   */
  public static RequestDto find(SecurityInfo secInfo, RequestSelect selector, String requestId) {
    List matches = find(secInfo, selector, new RequestFilter(requestId));

    // The requestId uniquely identifies a request, so the list of matches will have
    // either zero or one element.
    //
    if (matches.size() == 0) {
      return null;
    }
    else {
      return (RequestDto) matches.get(0);
    }
  }

  /**
   * Do a basic request query to find the requests that match the specified
   * SQL where clauses and put them into their proper sorted order, and return a list of
   * {@link RequestDto} objects containing the query results.
   * 
   * We do this query even if selector.isIncludeBasic() is false.  When selector.isIncludeBasic()
   * is false, the only basic properties that will be defined in the resulting RequestDto
   * objects will be the request id and type.  When selector.isIncludeBasic() is true, all
   * of the basic request properties will be populated
   * (see {@link RequestSelect#isIncludeBasic()}).
   * 
   * The resulting request list is sorted by descending request creation date.
   * 
   * @param selector The specification of which request details to populate in the result.
   * @param filter The specification of which requests to return.
   * @param requestWhereClauses The where clauses that describe which requests to return.
   * @return The sorted list of matching requests, represented as {@link RequestDto} objects.
   */
  private static List queryRequestBasics(
    RequestSelect selector,
    RequestFilter filter,
    String requestWhereClauses) {

    List requests = new ArrayList();
    boolean isIncludeBasic = selector.isIncludeBasic();
    requestWhereClauses = ApiFunctions.safeTrim(requestWhereClauses);
    boolean hasRequestWhereClauses = (!ApiFunctions.isEmpty(requestWhereClauses));

    StringBuffer query = new StringBuffer(512);
    query.append("select ");
    query.append(TABLE_ALIAS_REQUEST);
    query.append(".request_id, ");
    query.append(TABLE_ALIAS_REQUEST);
    query.append(".request_type");
    if (isIncludeBasic) {
      query.append(", ");
      query.append(TABLE_ALIAS_REQUEST);
      query.append(".create_date, ");
      query.append(TABLE_ALIAS_REQUEST);
      query.append(".state, ");
      query.append(TABLE_ALIAS_REQUEST);
      query.append(".closed_yn, ");
      query.append(TABLE_ALIAS_REQUEST);
      query.append(".requester_user_id, ");
      query.append(TABLE_ALIAS_REQUEST);
      query.append(".requester_comments, ");
      query.append(TABLE_ALIAS_REQUEST);
      query.append(".destination_id, ");
      query.append(TABLE_ALIAS_REQUEST);
      query.append(".request_mgr_comments, ");
      query.append(TABLE_ALIAS_REQUEST);
      query.append(".deliver_to_user_id, ");
      query.append("u.user_firstname, u.user_lastname, u1.user_firstname, u1.user_lastname, ");
      query.append("geo.location_name, u.ardais_acct_key, u1.ardais_acct_key, ");
      query.append("addr.address_id, addr.address_1, addr.address_2, addr.addr_city, addr.addr_state, ");
      query.append("addr.addr_zip_code, addr.addr_country, ");
      query.append("addr.first_name, addr.last_name, addr.middle_name ");
    }
    query.append("\nfrom iltds_request ");
    query.append(TABLE_ALIAS_REQUEST);
    if (isIncludeBasic) {
      query.append(", es_ardais_user u, es_ardais_user u1, ");
      query.append(" iltds_geography_location geo, ardais_address addr");
    }
    if (hasRequestWhereClauses || isIncludeBasic) {
      query.append("\nwhere ");
    }
    if (hasRequestWhereClauses) {
      query.append('(');
      query.append(requestWhereClauses);
      query.append(") ");
      if (isIncludeBasic) {
        query.append("\n  and ");
      }
    }
    if (isIncludeBasic) {
      query.append(TABLE_ALIAS_REQUEST);
      query.append(".requester_user_id = u.ardais_user_id");
      query.append("\n  and ");
      query.append(TABLE_ALIAS_REQUEST);
      query.append(".deliver_to_user_id = u1.ardais_user_id(+)");
      query.append("\n  and ");
      query.append(TABLE_ALIAS_REQUEST);
      query.append(".destination_id = geo.location_address_id(+) ");
      query.append("\n  and ");
      query.append(TABLE_ALIAS_REQUEST);
      query.append(".ship_to_addr_id = addr.address_id(+) ");
    }
    query.append("\norder by ");
    query.append(TABLE_ALIAS_REQUEST);
    query.append(".create_date desc");

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      int bindIndex = 1;
      con = ApiFunctions.getDbConnection();

      pstmt = con.prepareStatement(query.toString());
      bindIndex = filter.bindSqlWhereClauses(pstmt, bindIndex);

      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      while (rs.next()) {
        RequestDto requestDto = new RequestDto();

        requestDto.setId(rs.getString(1));
        requestDto.setType(RequestType.getInstance(rs.getString(2)));
        if (isIncludeBasic) {
          requestDto.setCreateDate(rs.getTimestamp(3));
          requestDto.setState(RequestState.getInstance(rs.getString(4)));
          requestDto.setClosed("Y".equals(rs.getString(5)));
          requestDto.setRequesterId(rs.getString(6));
          requestDto.setRequesterComments(rs.getString(7));
          requestDto.setDestinationId(rs.getString(8));
          requestDto.setRequestManagerComments(rs.getString(9));
          requestDto.setDeliverToId(rs.getString(10));
          String firstName = rs.getString(11);
          String lastName = rs.getString(12);
          requestDto.setRequesterName(firstName + " " + lastName);
          firstName = rs.getString(13);
          lastName = rs.getString(14);
          if (!ApiFunctions.isEmpty(firstName) ||
              !ApiFunctions.isEmpty(lastName)) {
                requestDto.setDeliverToName(firstName + " " + lastName);
          }
          requestDto.setDestinationName(rs.getString(15));
          requestDto.setRequesterAccount(rs.getString(16));
          requestDto.setDeliverToAccount(rs.getString(17));
          String addr1 = rs.getString(18);
          if (!ApiFunctions.isEmpty(addr1)) {
            Address address = new Address();
            address.populateFromResultSet(columns, rs);
            requestDto.setShippingAddress(address);
          }
        }
        requests.add(requestDto);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return requests;
  }

  /**
   * Augment the supplied requests with information about the items in the request, to the
   * degree specified in the <code>selector</code> parameter.
   * The requestWhereClauses parameter must contain SQL where clauses that select
   * exactly the same set of requests that are represented in the supplied requests list.
   * 
   * @param secInfo The security info of the current user.
   * @param requests A list of {@link RequestDto} objects.  The items in this list will be
   *    modified to contain the results.
   * @param selector The specification of which request details to populate in the result.
   * @param filter The specification of which requests to return.
   * @param requestWhereClauses The SQL where clauses.
   * @return A map that maps request ids to RequestDto objects.  This map will contain the same
   *   RequestDto objects as the input requests list.  If either the requests list is empty
   *   or not item information is selected inthe specified selector, this will return null
   *   instead of a populated map.
   */
  private static Map queryRequestItems(
    SecurityInfo secInfo,
    List requests,
    RequestSelect selector,
    RequestFilter filter,
    String requestWhereClauses) {

    if (requests.size() == 0) {
      return null;
    }

    int itemInfoCode = selector.getItemInfo();

    if (itemInfoCode == RequestSelect.ITEM_INFO_NONE) {
      // Nothing to do if we're not getting any item information.
      return null;
    }

    // Approach:
    //  - Do a query to get the lists of placeholder RequestItemDto in place
    //    and in the correct item order for each request.  These placeholders only have
    //    item id and item type set, not the item details.
    //  - Place these lists on their corresponding RequestDto objects in the requests
    //    list.  Convert the requests list to a map keyed by request id to make this easier.
    //    If we encounter the same item id more than once (that is, the same item is in one more
    //    than one of the requests), use the same RequestItemDto instance.  Return a map of
    //    these instances for future reference (it will come in handy if we're going to need
    //    to augment these objects with full details below).
    //  - If itemInfoCode is ITEM_INFO_BASIC, stop, we are done.
    //  - If itemInfoCode is anything other than ITEM_INFO_DETAILS, throw an exception
    //    about an unsupported itemInfo code.
    //  - Get the set of distinct item ids across all of the requests in the requests list,
    //    and use sample selection's "performGetDetails" BTX transaction to get a list
    //    of ProductDto objects with the item details.
    //  - Iterate over the placeholder item details lists in all of the requests.  For
    //    each RequestItemDto, set the Item property to the correct corresponding
    //    ProductDto and set the temporary placeholder id/type info in the RequestItemDto
    //    to null.  Converting the list of ProductDtos into a map keyed by item id will
    //    be helpful here.

    Map requestMap = makeRequestMap(requests);

    Map requestItemMap = queryItemBasics(requests, requestMap, filter, requestWhereClauses);

    if (itemInfoCode == RequestSelect.ITEM_INFO_BASIC) {
      // Nothing more to do if we're getting only basic item details (id/type).
      return requestMap;
    }

    if (itemInfoCode != RequestSelect.ITEM_INFO_DETAILS) {
      throw new ApiException("Unsupported itemInfo code: " + itemInfoCode);
    }

    // If we get here, then itemInfoCode == RequestSelect.ITEM_INFO_DETAILS
    // and we need to augment all of the RequestItemDtos that we've attached to the request
    // object with full item details.

    queryItemDetails(secInfo, requestItemMap);

    return requestMap;
  }

  /**
   * Augment each of the requests in the supplied request list with a list of items
   * in the request.  Each item in the request list must be a {@link RequestDto} object,
   * and this method will set the Items properties of those objects to a list of
   * {@link RequestItemDto} objects representing basic information about each item
   * (item id and type only, not full item details).  The item lists will be in the item
   * order specified by the ILTDS_REQUEST_ITEM.ITEM_ORDER column in the database.
   * The requestWhereClauses parameter must contain SQL where clauses that selects
   * exactly the same set of requests that are represented in the supplied requests list.
   * 
   * <p>If the same item appears in more than one request, the same RequestItemDto instance will
   * represent that item in all requests.
   * 
   * @param requests The list of requests.  The requests in this list will be modified to
   *    include the basic item details.
   * @param requestMap A map that maps request ids to RequestDto objects.  This map
   *    must contain exactly the same set of RequestDto
   *    objects as in the requests lists (we don't check that this is true).
   * @param filter The specification of which requests to return.
   * @param requestWhereClauses The SQL where clauses.
   * @return A map that maps item ids to their corresponding RequestItemDto object.
   */
  private static Map queryItemBasics(
    List requests,
    Map requestMap,
    RequestFilter filter,
    String requestWhereClauses) {

    requestWhereClauses = ApiFunctions.safeTrim(requestWhereClauses);
    boolean hasRequestWhereClauses = (!ApiFunctions.isEmpty(requestWhereClauses));

    StringBuffer query = new StringBuffer(512);
    query.append("select distinct i.request_id, i.item_id, i.item_type, i.item_order ");
    query.append("\nfrom iltds_request_item i");
    if (hasRequestWhereClauses) {
      query.append(", iltds_request ");
      query.append(TABLE_ALIAS_REQUEST);
      query.append("\nwhere i.request_id = ");
      query.append(TABLE_ALIAS_REQUEST);
      query.append(".request_id ");
      query.append("\n  and (");
      query.append(requestWhereClauses);
      query.append(") ");
    }
    query.append("\norder by i.request_id, i.item_order");

    Map requestItemMap = new HashMap(311);
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      int bindIndex = 1;
      con = ApiFunctions.getDbConnection();

      pstmt = con.prepareStatement(query.toString());
      bindIndex = filter.bindSqlWhereClauses(pstmt, bindIndex);

      rs = ApiFunctions.queryDb(pstmt, con);

      String prevRequestId = null;
      List requestItems = null;
      while (rs.next()) {
        String currentRequestId = rs.getString(1);
        String itemId = rs.getString(2);
        RequestItemDto item = (RequestItemDto) requestItemMap.get(itemId);
        if (item == null) {
          item = new RequestItemDto();
          item.setItemId(itemId);
          item.setItemType(ProductType.getInstance(rs.getString(3)));
          requestItemMap.put(itemId, item);
        }

        if (!currentRequestId.equals(prevRequestId)) {
          // We've encountered the first item in a new request.  If there was a previous request,
          // attach the item list we've accumulated to it.  In any case, start a new item list.

          if (prevRequestId != null) {
            RequestDto requestDto = (RequestDto) requestMap.get(prevRequestId);
            if (requestDto == null) {
              // Shouldn't happen: the requests list is supposed to match up with the
              // requestWhereClauses that were passed in.
              throw new IllegalStateException(
                "No item in requests list for request id " + prevRequestId);
            }
            Iterator iterator = requestItems.iterator();
            while (iterator.hasNext()) {
              RequestItemDto rid = (RequestItemDto)iterator.next();
              requestDto.addItem(rid);
            }
          }

          prevRequestId = currentRequestId;
          requestItems = new ArrayList();
        }

        requestItems.add(item);
      } // end while (rs.next())

      // The last request we encountered in the loop hasn't had its item list attached yet,
      // do that now.
      //
      if (prevRequestId != null) {
        RequestDto requestDto = (RequestDto) requestMap.get(prevRequestId);
        if (requestDto == null) {
          // Shouldn't happen: the requests list is supposed to match up with the
          // requestWhereClauses that were passed in.
          throw new IllegalStateException(
            "No item in requests list for request id " + prevRequestId);
        }
        Iterator iterator = requestItems.iterator();
        while (iterator.hasNext()) {
          RequestItemDto rid = (RequestItemDto)iterator.next();
          requestDto.addItem(rid);
        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return requestItemMap;
  }

  /**
   * Augment each of the {@link RequestItemDto} items in the supplied map with full item
   * details.  The map must be keyed by item id, and the corresponding value must be a
   * RequestItemDto object that represents that item.  This method will modify each of these
   * RequestItemDto objects by setting its itemId and itemType properties to null
   * and setting its Item property to a ProductDto object containing the item details.
   * The RequestItemDto objects in the input map don't need to have their id, type, or item
   * properties set, and if they are set they will be overwritten.
   *  
   * @param secInfo The security info of the current user.
   * @param requestItemMap The map that specifies the items for which to get details and that
   *   will be modified to contain the results.
   */
  private static void queryItemDetails(SecurityInfo secInfo, Map requestItemMap) {
    String[] itemIds = (String[]) requestItemMap.keySet().toArray(new String[0]);

    if (itemIds.length == 0) {
      return;
    }

    List items = null;
    try {
      BTXDetailsGetSamples btx = new BTXDetailsGetSamples();
      btx.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
      btx.setLoggedInUserSecurityInfo(secInfo, true);
      btx.setSampleIds(itemIds);
      //specify the default results view
      btx.setResultsFormDefinition(SystemConfiguration.getDefaultResultsView());
      btx.setTransactionType("library_get_details");
      btx = (BTXDetailsGetSamples) Btx.perform(btx);
      items = btx.getSampleDetailsResult();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    if (items != null) {
      Iterator iter = items.iterator();
      while (iter.hasNext()) {
        ProductDto item = (ProductDto) iter.next();
        RequestItemDto requestItem = (RequestItemDto) requestItemMap.get(item.getId());
        requestItem.setItemId(null);
        requestItem.setItemType(null);
        requestItem.setItem(item);
      }
    }
  }

  /**
   * Augment the supplied requests with information about the boxes in which samples
   * were placed when the request was fulfilled, to the degree specified in the
   * <code>selector</code> parameter.
   * 
   * <p>The requestWhereClauses parameter must contain SQL where clauses that select
   * exactly the same set of requests that are represented in the supplied requests list.
   * 
   * @param secInfo The security info of the current user.
   * @param requests A list of {@link RequestDto} objects.  The items in this list will be
   *    modified to contain the results.
   * @param requestMap A map that maps request ids to RequestDto objects.  This parameter
   *    may be null but if it is non-null it must contain exactly the same set of RequestDto
   *    objects as in the requests lists (we don't check that this is true).
   * @param selector The specification of which request details to populate in the result.
   * @param filter The specification of which requests to return.
   * @param requestWhereClauses The SQL where clauses.
   * @return A map that maps request ids to RequestDto objects.  If the requestMap parameter is
   *   not null, the return value will be that map.  Otherwise, this method will construct
   *   and return a map that contains the same RequestDto objects as the input requests list.
   *   If either the requests list is empty or no box information is selected inthe specified
   *   selector, this will return null instead of a populated map (however, if the input requestMap
   *   is not null, this method will always return that map).
   */
  private static Map queryRequestBoxes(
    SecurityInfo secInfo,
    List requests,
    Map requestMap,
    RequestSelect selector,
    RequestFilter filter,
    String requestWhereClauses) {

    if (requests.size() == 0) {
      return requestMap;
    }

    int infoCode = selector.getBoxInfo();

    if (infoCode == RequestSelect.BOX_INFO_NONE) {
      // Nothing to do if we're not getting any box information.
      return requestMap;
    }

    // Approach:
    //  - If the map passed in is null, convert the requests list to a map keyed by request id 
    //    to make querying for basic box information easier.
    //  - query for the box basics to get a list of placeholder RequestBoxDto in place
    //    and in the correct order for each request.  These placeholders only have
    //    basic information fields set (those specified by RequestSelect.BOX_INFO_BASIC),
    //    not the complete box details (such as box contents).  Note that if we encounter 
    //    the same box id more than once (that is, the same box is used in one more
    //    than one request in our list), a seperate RequestBoxDto instance is created for 
    //    each request (one per Request in which the box is used).  
    //  - If infoCode is BOX_INFO_BASIC, stop, we are done.
    //  - If infoCode is anything other than BOX_INFO_DETAILS, throw an exception
    //    about an unsupported boxInfo code.
    //  - Modify each RequestBoxDto to have a BoxDto object set into it based on 
    //    the information in the iltds_request_items table (when a request is fulfilled and
    //    items are scanned into boxes, we store the box and cell_ref a sample was placed into
    //    in the iltds_request_item table, and we get this information so we can return the
    //    box contents at request fulfillment time (NOT necessarily the current box contents)).
    
    if (requestMap == null) {
      requestMap = makeRequestMap(requests);
    }

    queryBoxBasics(secInfo, requestMap, filter, requestWhereClauses);

    if (infoCode == RequestSelect.BOX_INFO_BASIC) {
      // Nothing more to do if we're getting only basic box details (id/shipped/...).
      return requestMap;
    }

    if (infoCode != RequestSelect.BOX_INFO_DETAILS) {
      throw new ApiException("Unsupported boxInfo code: " + infoCode);
    }

    return requestMap;
  }

  /**
   * Augment each of the requests in the supplied request map with a list of boxes
   * in that request.  Each item in the request map must be a {@link RequestDto} object,
   * and this method will set the Boxes properties of those objects to a list of
   * {@link RequestBoxDto} objects representing basic information about each box
   * (box id and shipped info only, not full box details).  The box lists will be in the box
   * order specified by the ILTDS_REQUEST_BOX.BOX_ORDER column in the database.
   * The requestWhereClauses parameter must contain SQL where clauses that selects
   * exactly the same set of requests that are represented in the supplied requests map.
   * 
   * <p>If the same box appears in more than one request, a distinct RequestBoxDto instance will
   * represent that box in each request.  This is because the box could have (and likely does
   * have) different box contents in each request
   * 
   * @param requestMap A map that maps request ids to RequestDto objects.
   * @param filter The specification of which requests to return.
   * @param requestWhereClauses The SQL where clauses.
   */
  private static void queryBoxBasics(
    SecurityInfo securityInfo,
    Map requestMap,
    RequestFilter filter,
    String requestWhereClauses) {

    requestWhereClauses = ApiFunctions.safeTrim(requestWhereClauses);
    boolean hasRequestWhereClauses = (!ApiFunctions.isEmpty(requestWhereClauses));

    StringBuffer query = new StringBuffer(512);
    
    query.append("SELECT irb2.request_id, irb2.box_barcode_id, irb2.shipped_yn, irb2.box_order, irb2.box_contents ");
    query.append("\nFROM iltds_request_box irb2 WHERE irb2.id IN (");
    query.append("\nSELECT DISTINCT irb.id FROM iltds_request_box irb");
    if (hasRequestWhereClauses) {
      query.append(", iltds_request ");
      query.append(TABLE_ALIAS_REQUEST);
      query.append("\nwhere irb.request_id = ");
      query.append(TABLE_ALIAS_REQUEST);
      query.append(".request_id ");
      query.append("\n  and (");
      query.append(requestWhereClauses);
      query.append(") ");
    }
    query.append("\n)");
    query.append("\norder by irb2.request_id, irb2.box_order");
    
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      int bindIndex = 1;
      con = ApiFunctions.getDbConnection();

      pstmt = con.prepareStatement(query.toString());
      bindIndex = filter.bindSqlWhereClauses(pstmt, bindIndex);
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        String requestId = rs.getString(1);
        RequestBoxDto requestBoxDto = new RequestBoxDto();
        requestBoxDto.setBoxId(rs.getString(2));
        requestBoxDto.setShipped("Y".equals(rs.getString(3)));
        requestBoxDto.setBoxDto(new BoxDto(rs.getString(2), ApiFunctions.getStringFromClob(rs.getClob(5))));
        
        //update the box dto with alias values for the samples it contains
        Map boxContents = requestBoxDto.getBoxDto().getContents();
        List sampleIds = new ArrayList();
        Map sampleIdToSampleMap = new HashMap();
        Iterator contentIterator = boxContents.keySet().iterator();
        while (contentIterator.hasNext()) {
          String cellNumber = (String)contentIterator.next();
          String sampleId = (String)boxContents.get(cellNumber);
          if (!ApiFunctions.isEmpty(ValidateIds.validateId(sampleId, ValidateIds.TYPESET_SAMPLE, false))) {
            sampleIdToSampleMap.put(sampleId, new RequestItemDto());
          }
        }
        queryItemDetails(securityInfo, sampleIdToSampleMap);
        contentIterator = boxContents.keySet().iterator();
        while (contentIterator.hasNext()) {
          String cellNumber = (String)contentIterator.next();
          String sampleId = (String)boxContents.get(cellNumber);
          RequestItemDto item = (RequestItemDto)sampleIdToSampleMap.get(sampleId);
          requestBoxDto.getBoxDto().setCellSampleAlias(cellNumber, item.getItem().getSampleData().getSampleAlias());
        }

        RequestDto requestDto = (RequestDto) requestMap.get(requestId);
        if (requestDto == null) {
          // Shouldn't happen: the requests map is supposed to match up with the
          // requestWhereClauses that were passed in.
          throw new IllegalStateException(
            "No request in requests map for request id " + requestId);
        }
        requestDto.addBox(requestBoxDto);
      } // end while (rs.next())
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }

  /**
   * Make a map that maps request ids to request objects.
   * 
   * @param requests A list of {@link RequestDto} objects.
   * @return A map containing all of the requests in the lists, keyed by request id.
   */
  private static Map makeRequestMap(List requests) {
    Map map = new HashMap(Math.max(1 + (2 * requests.size()), 11));
    Iterator iter = requests.iterator();
    while (iter.hasNext()) {
      RequestDto requestDto = (RequestDto) iter.next();
      map.put(requestDto.getId(), requestDto);
    }
    return map;
  }
}
