package com.ardais.bigr.iltds.performers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.ApiResources;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.TemporaryClob;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.es.beans.ArdaisaccountAccessBean;
import com.ardais.bigr.es.beans.ArdaisaccountKey;
import com.ardais.bigr.es.beans.SequenceGenAccessBean;
import com.ardais.bigr.iltds.assistants.Address;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.beans.BoxlocationAccessBean;
import com.ardais.bigr.iltds.beans.GeolocationKey;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.beans.SampleOperation;
import com.ardais.bigr.iltds.beans.SampleOperationHome;
import com.ardais.bigr.iltds.beans.SampleboxKey;
import com.ardais.bigr.iltds.beans.SamplestatusAccessBean;
import com.ardais.bigr.iltds.bizlogic.RequestFinder;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.btx.BtxDetailsCheckOutBox;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateRequest;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateResearchRequest;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateTransferRequest;
import com.ardais.bigr.iltds.btx.BtxDetailsFindRequestDetails;
import com.ardais.bigr.iltds.btx.BtxDetailsFindRequests;
import com.ardais.bigr.iltds.btx.BtxDetailsFulfillRequest;
import com.ardais.bigr.iltds.btx.BtxDetailsRejectRequest;
import com.ardais.bigr.iltds.btx.BtxDetailsRequestItemsBoxed;
import com.ardais.bigr.iltds.btx.BtxDetailsRequestOperation;
import com.ardais.bigr.iltds.btx.BtxDetailsUpdateRequestsAfterShipment;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.ProductType;
import com.ardais.bigr.iltds.helpers.RequestFilter;
import com.ardais.bigr.iltds.helpers.RequestSelect;
import com.ardais.bigr.iltds.helpers.RequestState;
import com.ardais.bigr.iltds.helpers.RequestType;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.javabeans.RequestBoxDto;
import com.ardais.bigr.javabeans.RequestDto;
import com.ardais.bigr.javabeans.RequestItemDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.javabeans.ShippingPartnerDto;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.btx.BTXDetailsRemoveFromHoldList;
import com.ardais.bigr.orm.beans.AccountSrchSB;
import com.ardais.bigr.orm.beans.AccountSrchSBHome;
import com.ardais.bigr.orm.helpers.BoxScanData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.IdValidator;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

/**
 * This performs request-related ILTDS BTX business transactions.
 */
public class BtxPerformerRequestOperations extends BtxTransactionPerformerBase {

  private static int MAX_COMMENT_LENGTH = 4000;

  private static int MAX_ADDRESS_FIRST_NAME = 35;

  private static int MAX_ADDRESS_MIDDLE_NAME = 2;

  private static int MAX_ADDRESS_LAST_NAME = 30;

  private static int MAX_ADDRESS_ADDRESS1 = 60;

  private static int MAX_ADDRESS_ADDRESS2 = 60;

  private static int MAX_ADDRESS_CITY = 25;

  private static int MAX_ADDRESS_STATE = 25;

  private static int MAX_ADDRESS_ZIP = 15;

  private static int MAX_ADDRESS_COUNTRY = 35;

  public BtxPerformerRequestOperations() {
    super();
  }

  /**
   * Performs validation for starting the process of creating a new research request. Validates that
   * the btxDetails contains a RequestDto containing a non-empty list of RequestItemDtos, each of
   * which has an item id value (item type is derived from the item id value if possible, and if not
   * an appropriate message is returned). Also validates that the items on the request are local to
   * the user (in a box at the current user's location) and are not already on a pending request.
   */
  private BTXDetails validatePerformCreateRequestStart(BtxDetailsCreateResearchRequest btxDetails)
      throws Exception {
    RequestDto dto = btxDetails.getInputRequestDto();
    if (dto == null) {
      btxDetails.addActionError(new BtxActionError("iltds.error.request.general.noInput"));
    }
    //MR7132 - make sure all the boxed items are local
    //MR7254 - remove the restriction preventing a mix of bms and non-bms items on a request.
    //         Although Ardais users will still be prevented from doing this in the UI (since
    //         the same UI is used not only for creating transfer requests but also for Path
    //         and R&D picklists and we don't want to allow a mix for those operations), there
    //         is no business reason to prevent the mix for a transfer request. In fact, now
    //         that DI users will be creating transfer requests we need to allow a mix.
    validateItemList(btxDetails, dto.getItems(), true, false, true);
    return btxDetails;
  }

  /**
   * Performs validation for starting the process of creating a new transfer request. Validates that
   * the btxDetails contains a RequestDto containing a non-empty list of RequestItemDtos, each of
   * which has an item id value (item type is derived from the item id value if possible, and if not
   * an appropriate message is returned). Also validates that the items on the request are local to
   * the user (in a box at the current user's location) and are not already on a pending request.
   * Finally, validates that there are shipping destinations available to the user.
   */
  private BTXDetails validatePerformCreateRequestStart(BtxDetailsCreateTransferRequest btxDetails)
      throws Exception {
    RequestDto dto = btxDetails.getInputRequestDto();
    if (dto == null) {
      btxDetails.addActionError(new BtxActionError("iltds.error.request.general.noInput"));
    }
    //MR7132 - make sure all the boxed items are local
    //MR7254 - remove the restriction preventing a mix of bms and non-bms items on a request.
    //         Although Ardais users will still be prevented from doing this in the UI (since
    //         the same UI is used not only for creating transfer requests but also for Path
    //         and R&D picklists and we don't want to allow a mix for those operations), there
    //         is no business reason to prevent the mix for a transfer request. In fact, now
    //         that DI users will be creating transfer requests we need to allow a mix.
    validateItemList(btxDetails, dto.getItems(), true, false, true);
    //don't allow the user to proceed further if there are no shipping destinations specified for
    //their account
    if (ApiFunctions.isEmpty(IltdsUtils.getAssignedShippingPartnersByAccount(btxDetails
        .getLoggedInUserSecurityInfo().getAccount()))) {
      btxDetails.addActionError(new BtxActionError(
          "iltds.error.request.createRequest.noAvailableDestinations"));
    }

    return btxDetails;
  }

  /**
   * Retrieve any information necessary for the process of creating a new research request. Note: We
   * currently do not show any information about the requested items on the "Create Research
   * Request" page, so this method doesn't retrieve any information for them. If we decide to show
   * information about the requested items, this method can be altered to retrieve that information.
   */
  private BTXDetails performCreateRequestStart(BtxDetailsCreateResearchRequest btxDetails)
      throws Exception {
    RequestDto inputDto = btxDetails.getInputRequestDto();
    RequestDto outputDto = (RequestDto) inputDto.clone();

    //set the id and name of the current user
    String user = btxDetails.getLoggedInUserSecurityInfo().getUsername();
    String account = btxDetails.getLoggedInUserSecurityInfo().getAccount();
    outputDto.setRequesterId(user);
    ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
    ListGenerator list = home.create();
    outputDto.setRequesterName(list.getUserRealName(user, account));

    btxDetails.setOutputRequestDto(outputDto);
    btxDetails.setActionForwardTXCompleted("success");
    //mark this transaction as incomplete, to prevent this transaction from being logged.
    //Note that all necessary work for this transaction is in fact completed.
    btxDetails.setTransactionCompleted(false);
    return btxDetails;
  }

  /**
   * Retrieve any information necessary for the process of creating a new transfer request. Note: We
   * currently do not show any information about the requested items on the "Create Transfer
   * Request" page, so this method doesn't retrieve any information for them. If we decide to show
   * information about the requested items, this method can be altered to retrieve that information.
   */
  private BTXDetails performCreateRequestStart(BtxDetailsCreateTransferRequest btxDetails)
      throws Exception {
    RequestDto inputDto = btxDetails.getInputRequestDto();
    RequestDto outputDto = (RequestDto) inputDto.clone();

    //set the id and name of the current user
    String user = btxDetails.getLoggedInUserSecurityInfo().getUsername();
    String account = btxDetails.getLoggedInUserSecurityInfo().getAccount();
    outputDto.setRequesterId(user);
    ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
    ListGenerator list = home.create();
    outputDto.setRequesterName(list.getUserRealName(user, account));

    //set the list of available shipping partners
    outputDto.setShippingPartners(IltdsUtils.getAssignedShippingPartnersByAccount(account));

    btxDetails.setOutputRequestDto(outputDto);
    btxDetails.setActionForwardTXCompleted("success");
    //mark this transaction as incomplete, to prevent this transaction from being logged.
    //Note that all necessary work for this transaction is in fact completed.
    btxDetails.setTransactionCompleted(false);
    return btxDetails;
  }

  /**
   * Performs validation for the creation of a new research request. Validates that the btxDetails
   * contains a RequestDto containing a requester comment value and a non-empty list of
   * RequestItemDtos, each of which has an item id value (item type is derived from the item id
   * value if possible, and if not an appropriate message is returned). Also validates that there is
   * not a mixture of BMS and non-BMS items in the item list, and that the items on the request are
   * not already on a pending request.
   */
  private BTXDetails validatePerformCreateRequest(BtxDetailsCreateResearchRequest btxDetails)
      throws Exception {
    validatePerformCreateRequestStart(btxDetails);
    if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(btxDetails.getInputRequestDto()
        .getRequesterComments()))) {
      btxDetails.addActionError(new BtxActionError("iltds.error.request.commentsRequired"));
    }
    else {
      //make sure the requester comments do not exceed the maximum database length
      if (btxDetails.getInputRequestDto().getRequesterComments().length() > MAX_COMMENT_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Comments",
            MAX_COMMENT_LENGTH + " characters"));
      }
    }
    return btxDetails;
  }

  /**
   * Performs validation for the creation of a new transfer request. Validates that the btxDetails
   * contains a RequestDto containing a a valid destination id value and a non-empty list of
   * RequestItemDtos, each of which has an item id value (item type is derived from the item id
   * value if possible, and if not an appropriate message is returned). Also validates that there is
   * not a mixture of BMS and non-BMS items in the item list, and that the items on the request are
   * not already on a pending request.
   */
  private BTXDetails validatePerformCreateRequest(BtxDetailsCreateTransferRequest btxDetails)
      throws Exception {
    validatePerformCreateRequestStart(btxDetails);
    //make sure the destination information has been specified
    validateDestinationInfo(btxDetails);
    //make sure the requester comments do not exceed the maximum database length
    String comments = btxDetails.getInputRequestDto().getRequesterComments();
    if (!ApiFunctions.isEmpty(comments)) {
      if (comments.length() > MAX_COMMENT_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Comments",
            MAX_COMMENT_LENGTH + " characters"));
      }
    }
    //if any errors are going to be returned, get the list of valid shipping partners
    //for this account so the drop-down contains data
    if (!btxDetails.getActionErrors().empty()) {
      btxDetails.getInputRequestDto().setShippingPartners(
          IltdsUtils.getAssignedShippingPartnersByAccount(btxDetails.getLoggedInUserSecurityInfo()
              .getAccount()));
    }
    return btxDetails;
  }

  /**
   * Create the request in the database.
   */
  private BTXDetails performCreateRequest(BtxDetailsCreateRequest btxDetails) throws Exception {
    RequestDto inputDto = btxDetails.getInputRequestDto();
    RequestDto outputDto = (RequestDto) inputDto.clone();

    ValidateIds validId = new ValidateIds();

    //get the id for the new request, and set it on the output dto
    String requestId = getIdForNewRequest();
    outputDto.setId(requestId);
    //for each item on the request, fill in the item type.
    //grab the sample ids, so we can update the ILTDS_SAMPLE_STATUS table
    List sampleIds = new ArrayList();
    Map sampleIdToRequestItem = new HashMap();
    Iterator itemIterator = outputDto.getItems().iterator();
    while (itemIterator.hasNext()) {
      RequestItemDto item = (RequestItemDto) itemIterator.next();
      String itemId = item.getItemId();
      if (!ApiFunctions.isEmpty(validId.validate(itemId, ValidateIds.TYPESET_SAMPLE, false))) {
        item.setItemType(ProductType.SAMPLE);
        sampleIds.add(item.getItemId());
        sampleIdToRequestItem.put(itemId, item);
      }
      else if (IdValidator.validRnaId(itemId)) {
        item.setItemType(ProductType.RNA);
      }
    }

    //in order to store additional information about the samples on the request (i.e. the sample
    //alias) in the history record, retrieve the samples and populate the output dto with that
    //information.  We do this after collecting all of the sample ids instead of while processing
    //them so we can make one call instead of a call for each sample
    if (!ApiFunctions.isEmpty(sampleIds)) {
      List items = null;
      try {
        BTXDetailsGetSamples btx = new BTXDetailsGetSamples();
        btx.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
        btx.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo(), true);
        btx.setSampleIds((String[]) sampleIds.toArray(new String[0]));
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
          String sampleId = item.getId();
          RequestItemDto requestItem = (RequestItemDto) sampleIdToRequestItem.get(sampleId);
          requestItem.setItem(item);
        }
      }
    }
    
    //for transfer requests, determine the address id to set on the request. If it's an
    //out-of-network request see if there is an existing address matching the one provided and if
    //not, create a new row in the address table If it's an in-network request, just look up the
    //ship to address for the specified destination
    Integer addressId = null;
    if (btxDetails.getRequestType() == RequestType.TRANSFER) {
      if (IltdsUtils.OUT_OF_NETWORK_LOCATION.equalsIgnoreCase(outputDto.getDestinationId())) {
        addressId = getOrCreateIdForOutOfNetworkAddress(btxDetails);
      }
      else {
        addressId = new Integer(IltdsUtils.getShipToAddressIdForGeolocation(outputDto
            .getDestinationId()));
      }
    }

    //now execute the sql statements
    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    boolean isDestinationIdSpecified = !ApiFunctions.isEmpty(outputDto.getDestinationId());
    boolean isResearchRequest = RequestType.RESEARCH.toString().equalsIgnoreCase(
        btxDetails.getRequestType().toString());
    try {
      //insert the request
      StringBuffer requestSql = new StringBuffer(50);
      requestSql = new StringBuffer(50);
      requestSql.append("INSERT INTO ");
      requestSql.append(DbConstants.TABLE_ILTDS_REQUEST);
      requestSql.append(" (");
      requestSql.append(DbConstants.REQUEST_REQUEST_ID);
      requestSql.append(", ");
      requestSql.append(DbConstants.REQUEST_CREATE_DATE);
      requestSql.append(", ");
      requestSql.append(DbConstants.REQUEST_STATE);
      requestSql.append(", ");
      requestSql.append(DbConstants.REQUEST_CLOSED_YN);
      requestSql.append(", ");
      requestSql.append(DbConstants.REQUEST_REQUESTER_USER_ID);
      requestSql.append(", ");
      requestSql.append(DbConstants.REQUEST_REQUESTER_COMMENTS);
      requestSql.append(", ");
      requestSql.append(DbConstants.REQUEST_REQUEST_TYPE);
      if (isDestinationIdSpecified) {
        requestSql.append(", ");
        requestSql.append(DbConstants.REQUEST_DESTINATION_ID);
        requestSql.append(", ");
        requestSql.append(DbConstants.REQUEST_SHIP_TO_ADDR_ID);
      }
      //if we're creating a research request, fill in the current user as the
      //deliver_to person
      if (isResearchRequest) {
        requestSql.append(", ");
        requestSql.append(DbConstants.REQUEST_DELIVER_TO_USER_ID);
      }
      requestSql.append(") VALUES (?, ?, ?, ?, ?, ?, ?");
      if (isDestinationIdSpecified) {
        requestSql.append(", ?, ?");
      }
      if (isResearchRequest) {
        requestSql.append(", ?");
      }
      requestSql.append(")");
      pstmt = con.prepareStatement(requestSql.toString());
      pstmt.setString(1, requestId);
      pstmt.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
      pstmt.setString(3, RequestState.PENDING.toString());
      pstmt.setString(4, FormLogic.DB_NO);
      pstmt.setString(5, btxDetails.getLoggedInUserSecurityInfo().getUsername());
      pstmt.setString(6, outputDto.getRequesterComments());
      pstmt.setString(7, btxDetails.getRequestType().toString());
      if (isDestinationIdSpecified) {
        pstmt.setString(8, outputDto.getDestinationId());
        pstmt.setInt(9, addressId.intValue());
      }
      if (isResearchRequest) {
        int position = 8;
        if (isDestinationIdSpecified) {
          position = 10;
        }
        pstmt.setString(position, btxDetails.getLoggedInUserSecurityInfo().getUsername());
      }
      pstmt.executeUpdate();
      ApiFunctions.close(pstmt);
      pstmt = null;

      //insert the request items
      StringBuffer itemSql = new StringBuffer(50);
      itemSql.append("INSERT INTO ");
      itemSql.append(DbConstants.TABLE_ILTDS_REQUEST_ITEM);
      itemSql.append(" (");
      itemSql.append(DbConstants.REQUEST_ITEM_REQUEST_ID);
      itemSql.append(", ");
      itemSql.append(DbConstants.REQUEST_ITEM_ITEM_ID);
      itemSql.append(", ");
      itemSql.append(DbConstants.REQUEST_ITEM_ITEM_TYPE);
      itemSql.append(", ");
      itemSql.append(DbConstants.REQUEST_ITEM_ITEM_ORDER);
      itemSql.append(") VALUES (?, ?, ?, ?)");
      pstmt = con.prepareStatement(itemSql.toString());
      itemIterator = outputDto.getItems().iterator();
      int order = 0;
      while (itemIterator.hasNext()) {
        order = order + 1;
        RequestItemDto item = (RequestItemDto) itemIterator.next();
        pstmt.setString(1, requestId);
        pstmt.setString(2, item.getItemId());
        pstmt.setString(3, item.getItemType().toString());
        pstmt.setInt(4, order);
        pstmt.executeUpdate();
      }
      ApiFunctions.close(pstmt);
      pstmt = null;

      //update the samples on the item list to have a status of requested
      // calculate the number of frozen and paraffin for display purposes

      if (sampleIds.size() > 0) {
        itemIterator = sampleIds.iterator();
        Timestamp now = new Timestamp(new java.util.Date().getTime());
        while (itemIterator.hasNext()) {
          String itemId = new String((String) (itemIterator.next()));
          // updating the samples
          SamplestatusAccessBean status = new SamplestatusAccessBean();
          status.setInit_argSample(new SampleKey(itemId));
          status.setInit_argStatus_type_code(FormLogic.SMPL_REQUESTED);
          status.setInit_argSample_status_datetime(now);
          status.commitCopyHelper();
        }
      }

      //send email to request manager, informing them of new request
      String accountId = btxDetails.getLoggedInUserSecurityInfo().getAccount();
      ArdaisaccountAccessBean account = new ArdaisaccountAccessBean(new ArdaisaccountKey(accountId));
      String requestMgrEmail = account.getRequest_mgr_email_address();
      if (!ApiFunctions.isEmpty(requestMgrEmail)) {
        StringBuffer mailMessage = new StringBuffer(100);
        mailMessage.append("A new request (");
        mailMessage.append(requestId);
        mailMessage.append(") has been submitted.");
        String requesterComments = outputDto.getRequesterComments();
        if (!ApiFunctions.isEmpty(requesterComments)) {
          mailMessage.append("<p>Requester Comments/Special Instructions:<br>");
          Escaper.htmlEscapeAndPreserveWhitespace(requesterComments, mailMessage);
        }
        ApiFunctions.generateEmail(ApiProperties.getProperty(ApiResources.API_MAIL_FROM_DEFAULT),
            requestMgrEmail, "A new request has been submitted.", mailMessage.toString());
      }

      //return a message confirming the creation of the request
      // 2.17.05 MR 7766 SAT -- note that we are no longer displaying
      //  counts of frozen/paraffin samples in output message
      btxDetails.addActionMessage(new BtxActionMessage("iltds.message.request.created", String
          .valueOf(sampleIds.size()), requestId));
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }

    btxDetails.setOutputRequestDto(outputDto);
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Performs validation for finding requests made by the user. Validates that any filter values
   * specified on the btxDetails are valid.
   */
  private BTXDetails validatePerformFindUserRequests(BtxDetailsFindRequests btxDetails)
      throws Exception {
    validateRequestFilter(btxDetails, btxDetails.getRequestFilter());
    return btxDetails;
  }

  /**
   * Find any requests made by the user that match the filter values specified on the btxDetails, if
   * any.
   */
  private BTXDetails performFindUserRequests(BtxDetailsFindRequests btxDetails) throws Exception {
    RequestFilter filters = btxDetails.getRequestFilter();
    if (filters == null) {
      filters = new RequestFilter();
    }
    //regardless of any value that might already exist in the requester user id filter,
    //make sure the caller only sees requests made by the currently logged in user.
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String userId = securityInfo.getUsername();
    filters.setRequesterUserId(userId);
    findRequests(securityInfo, btxDetails, filters);
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Performs validation for finding the details of a specific request. Validates that the
   * btxDetails contains a RequestDto containing a valid request id (required). Return a
   * BtxActionError if the request id is empty or invalid.
   */
  private BTXDetails validatePerformFindRequestDetails(BtxDetailsFindRequestDetails btxDetails)
      throws Exception {
    RequestDto dto = btxDetails.getInputRequestDto();
    if (dto == null) {
      btxDetails.addActionError(new BtxActionError("iltds.error.request.general.noInput"));
    }
    else {
      String requestId = dto.getId();
      if (ApiFunctions.isEmpty(requestId)) {
        btxDetails
            .addActionError(new BtxActionError("iltds.error.request.findRequest.noRequestId"));
      }
      else {
        RequestSelect select = RequestSelect.BASIC;
        RequestDto request = RequestFinder.find(btxDetails.getLoggedInUserSecurityInfo(), select,
            requestId);
        if (request == null) {
          btxDetails.addActionError(new BtxActionError(
              "iltds.error.request.findRequest.requestNotFound", requestId));
        }
      }
    }

    return btxDetails;
  }

  /**
   * Find the details of a specific request.
   */
  private BTXDetails performFindRequestDetails(BtxDetailsFindRequestDetails btxDetails)
      throws Exception {
    RequestSelect select = btxDetails.getRequestSelect();
    // If the caller hasn't specified what level of detail they want, assume they want everything.
    if (select == null) {
      select = new RequestSelect(false, true, RequestSelect.ITEM_INFO_DETAILS,
          RequestSelect.BOX_INFO_DETAILS);
    }
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    btxDetails.setOutputRequestDto(RequestFinder.find(securityInfo, select, btxDetails
        .getInputRequestDto().getId()));
    //if the caller has specified that additional information necessary to generate a picklist
    //should be retrieved, do that here
    if (btxDetails.isGetPickListInfo()) {
      getPickListInfo(btxDetails);
    }
    //sort the items as specified
    sortRequestItems(btxDetails);

    btxDetails.setActionForwardTXCompleted("success");

    // Get the box scan data for this account.
    btxDetails.setBoxScanData(BoxLayoutUtils.prepareForBoxScan(btxDetails
        .getLoggedInUserSecurityInfo()));

    // Mark this transaction as incomplete, to prevent this transaction from being logged.
    // Note that all necessary work for this transaction is in fact completed. Also, don't
    // assume a BtxDetailsFindRequestDetails object is passed in (logging is off for this
    // detail), a super class object with logging turned on could be passed in.
    btxDetails.setTransactionCompleted(false);

    return btxDetails;
  }

  /**
   * Performs validation for finding requests to be handled by the user (who should be the
   * repository manager). Validates that any filter values specified on the btxDetails are valid.
   */
  private BTXDetails validatePerformManageRequests(BtxDetailsFindRequests btxDetails)
      throws Exception {
    validateRequestFilter(btxDetails, btxDetails.getRequestFilter());
    //in order to have the Requester dropdown populated with valid choices,
    //set the possible choices here. This was being done in the performManageRequests
    //method, but that method isn't invoked if this one adds any action errors to
    //the btxDetails.
    btxDetails.setRequesters(getRequesters(btxDetails.getLoggedInUserSecurityInfo().getAccount()));
    return btxDetails;
  }

  /**
   * Find any requests made by any user in the current users account that match the filter values
   * specified on the btxDetails, if any.
   */
  private BTXDetails performManageRequests(BtxDetailsFindRequests btxDetails) throws Exception {
    RequestFilter filters = btxDetails.getRequestFilter();
    if (filters == null) {
      filters = new RequestFilter();
    }
    //regardless of any value that might already exist in the requester account filter,
    //make sure the caller only sees requests made anyone in the account of the currently
    //logged in user.
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String account = securityInfo.getAccount();
    filters.setRequesterAccount(account);
    findRequests(securityInfo, btxDetails, filters);
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Performs validation for starting the process of rejecting a request. Validates that the
   * btxDetails contains a RequestDto holding a valid request id, and the request that corresponds
   * to that id is pending. Return a BtxActionError if the request id field is empty or invalid, or
   * if the request it points to is not pending.
   */
  private BTXDetails validatePerformRejectRequestStart(BtxDetailsRejectRequest btxDetails)
      throws Exception {
    validatePendingRequest(btxDetails);
    return btxDetails;
  }

  /**
   * Performs validation for the rejection of a request. Validates that the btxDetails contains a
   * RequestDto containing a request manager comments value (required) and a valid request id
   * (required), and that id points to a request that is pending. Return a BtxActionError if the
   * request manager comments value is empty, if the request id field is empty or invalid, or if the
   * request it points to is not pending.
   */
  private BTXDetails validatePerformRejectRequest(BtxDetailsRejectRequest btxDetails)
      throws Exception {
    if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(btxDetails.getInputRequestDto()
        .getRequestManagerComments()))) {
      btxDetails.addActionError(new BtxActionError("iltds.error.request.commentsRequired"));
    }
    else {
      //make sure the request manager comments do not exceed the maximum database length
      if (btxDetails.getInputRequestDto().getRequestManagerComments().length() > MAX_COMMENT_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Comments",
            MAX_COMMENT_LENGTH + " characters"));
      }
    }
    validatePendingRequest(btxDetails);
    //if there were any action errors, because we go back to the reject request page on an
    //action error we need to populate the output dto with any information we display about
    //that request. If we don't do this the reject request jsp won't have the information
    //it needs to display
    if (!btxDetails.getActionErrors().empty()) {
      btxDetails.setOutputRequestDto(RequestFinder
          .find(btxDetails.getLoggedInUserSecurityInfo(), new RequestSelect(false, true,
              RequestSelect.ITEM_INFO_NONE, RequestSelect.BOX_INFO_NONE), btxDetails
              .getInputRequestDto().getId()));
      //restore any request manager comment value the user might have typed in.
      btxDetails.getOutputRequestDto().setRequestManagerComments(
          btxDetails.getInputRequestDto().getRequestManagerComments());
    }
    return btxDetails;
  }

  /**
   * Reject the request.
   */
  private BTXDetails performRejectRequest(BtxDetailsRejectRequest btxDetails) throws Exception {
    RequestDto inputDto = btxDetails.getInputRequestDto();

    //create the sql string to update the request
    StringBuffer requestSql = new StringBuffer(50);
    requestSql.append("Update ");
    requestSql.append(DbConstants.TABLE_ILTDS_REQUEST);
    requestSql.append(" set ");
    requestSql.append(DbConstants.REQUEST_CLOSED_YN);
    requestSql.append(" = ?");
    requestSql.append(", ");
    requestSql.append(DbConstants.REQUEST_STATE);
    requestSql.append(" = ?");
    requestSql.append(", ");
    requestSql.append(DbConstants.REQUEST_REQUEST_MGR_COMMENTS);
    requestSql.append(" = ?");
    requestSql.append(" where ");
    requestSql.append(DbConstants.REQUEST_REQUEST_ID);
    requestSql.append(" = ?");

    //create the sql string to update the request items. As we do in BIGRDataUtils
    //in the case of a canceled picklist, we insert a "BOXSCAN" status into the
    //ILTDS_SAMPLE_STATUS table. This will "overwrite" the "REQUESTED" status that
    //was put into this table when the request was created. Note that this action
    //leaves the items on the requester's hold list (relevant for Research Requests),
    //since this is an inv_status value not a sales_status value.
    StringBuffer itemSql = new StringBuffer(50);
    itemSql
        .append("insert into iltds_sample_status (sample_barcode_id, status_type_code, sample_status_datetime) ");
    itemSql.append("\nselect ri.item_id, '");
    itemSql.append(FormLogic.SMPL_BOXSCAN);
    itemSql.append("', sysdate from iltds_request_item ri ");
    itemSql.append("\n where ri.request_id = ?");

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      // Prepare and execute the request update sql.
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(requestSql.toString());
      List parameters = new ArrayList();
      parameters.add(FormLogic.DB_YES);
      parameters.add(RequestState.REJECTED.toString());
      parameters.add(inputDto.getRequestManagerComments());
      parameters.add(inputDto.getId());
      ApiFunctions.bindBindParameterList(pstmt, 1, parameters);
      pstmt.executeUpdate();
      ApiFunctions.close(pstmt);
      pstmt = null;

      // Prepare and execute the request item update sql.
      pstmt = con.prepareStatement(itemSql.toString());
      parameters = new ArrayList();
      parameters.add(inputDto.getId());
      ApiFunctions.bindBindParameterList(pstmt, 1, parameters);
      pstmt.executeUpdate();
      ApiFunctions.close(pstmt);
      pstmt = null;
    }
    catch (Exception e) {
      ApiLogger.log(e);
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }

    //send email to the requester letting them know the request was rejected
    RequestDto requestDto = RequestFinder.find(btxDetails.getLoggedInUserSecurityInfo(),
        new RequestSelect(false, true, RequestSelect.ITEM_INFO_BASIC, RequestSelect.BOX_INFO_NONE),
        btxDetails.getInputRequestDto().getId());
    String requesterEmail = getUserEmailForUser(requestDto.getRequesterId());
    if (!ApiFunctions.isEmpty(requesterEmail)) {
      StringBuffer mailMessage = new StringBuffer(100);
      mailMessage.append("Your ");
      mailMessage.append(requestDto.getType().toString().toLowerCase());
      mailMessage.append(" request (");
      mailMessage.append(requestDto.getId());
      mailMessage.append(") has been rejected for the following reason: <p> \"");
      Escaper.htmlEscapeAndPreserveWhitespace(requestDto.getRequestManagerComments(), mailMessage);
      mailMessage.append("\"");

      if (!ApiFunctions.generateEmail(
          ApiProperties.getProperty(ApiResources.API_MAIL_FROM_DEFAULT), requesterEmail,
          "Your request (" + requestDto.getId() + ") has been rejected.", mailMessage.toString())) {
        // MR7170 - let request manager know that email is down.
        btxDetails.addActionMessage(new BtxActionMessage("iltds.message.request.emailFailed",
            requesterEmail));
      }
    }

    //set the output dto so the history record is correct (needs the
    //request id and the items)
    btxDetails.setOutputRequestDto(requestDto);
    btxDetails.setActionForwardTXCompleted("success");
    //provide a confirmation message to the user to let them know the request was rejected
    btxDetails.addActionMessage(new BtxActionMessage("iltds.message.request.stateChange",
        requestDto.getId(), RequestState.REJECTED.toString().toLowerCase()));
    return btxDetails;
  }

  /**
   * Performs validation for starting the process of fulfilling a request. Validates that the
   * btxDetails contains a RequestDto holding a valid request id, and the request that corresponds
   * to that id is pending. Return a BtxActionError if the request id field is empty or invalid, or
   * if the request it points to is not pending.
   */
  private BTXDetails validatePerformFulfillRequestStart(BtxDetailsFulfillRequest btxDetails)
      throws Exception {
    // Check for valid request.
    validateFulfillRequest(btxDetails);

    BoxScanData bsd = BoxLayoutUtils.prepareForBoxScan(btxDetails.getLoggedInUserSecurityInfo());
    if (bsd.isEmpty()) {
      btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.noBoxLayoutForAccount"));
    }
    return btxDetails;
  }

  /**
   * Performs validation for the addition of a box to be used in the fulfillment of a request.
   * Validates that the btxDetails contains a RequestDto containing a valid request id (required),
   * and that id points to a request that is pending. Depending upon where in the process of
   * fulfilling the request the user is, the RequestDto may also contain a list of RequestBoxDto
   * beans, each of which contains a BoxDto bean (which contains a box id and the box contents which
   * includes the samples in the box and their locations in the box). The btxDetails might also
   * contain the id of a newly scanned box to be used in the fulfillment of the request. Also, check
   * if box exist in an open request.
   * 
   * Return a BtxActionError if the request id field is empty or invalid or if the request it points
   * to is not pending, if there is either an unexpected or duplicated item in any of the
   * RequestBoxDtos, or if the new box id value is not empty and is invalid (i.e. not a valid box
   * id, it's already been scanned for this request, it's not at the same location as the user, etc.
   */
  private BTXDetails validatePerformFulfillRequestAddBox(BtxDetailsFulfillRequest btxDetails)
      throws Exception {
    // Check for valid request.
    validateFulfillRequest(btxDetails);

    // Check if box layout id is valid.
    String newBoxLayoutId = btxDetails.getNewBoxLayoutId();
    newBoxLayoutId = BoxLayoutUtils.checkBoxLayoutId(newBoxLayoutId, btxDetails, false, true, true);

    if (!ApiFunctions.isEmpty(newBoxLayoutId)) {
      String accountId = btxDetails.getUserAccount();
      if (!BoxLayoutUtils.isValidBoxLayoutForAccount(newBoxLayoutId, accountId)) {
        // The box layout id is not part of the account box layout group.
        btxDetails.addActionError(new BtxActionError(
            "orm.error.boxLayout.invalidBoxLayoutForAccount", newBoxLayoutId));
      }
    }

    // Check if box id is valid. The box id can not be null.
    String newBoxId = btxDetails.getNewBoxId();
    newBoxId = IltdsUtils.checkBoxId(newBoxId, btxDetails, false, true, false);

    if (!ApiFunctions.isEmpty(newBoxId)) {

      // Check box id against existing box id within the boxes list.
      List boxes = btxDetails.getInputRequestDto().getBoxes();
      for (int i = 0; i < boxes.size(); i++) {
        RequestBoxDto requestBox = (RequestBoxDto) boxes.get(i);
        if (requestBox.getBoxId().equalsIgnoreCase(newBoxId)) {
          // Duplicate found.
          btxDetails.addActionError(new BtxActionError(
              "iltds.error.request.fulfillRequest.duplicateBox", newBoxId));
          break;
        }
      }
      // Validate box.
      validateBox(btxDetails, newBoxId);
    }

    // Check that item ids match what is being scaned in.
    validateBoxContents(btxDetails);

    // Populate the output dto with a combination of the request dto and the input request dto.
    RequestDto inputRequest = btxDetails.getInputRequestDto();
    RequestDto request = RequestFinder.find(btxDetails.getLoggedInUserSecurityInfo(), btxDetails
        .getRequestSelect(), inputRequest.getId());
    RequestDto outputRequest = (RequestDto) request.clone();

    outputRequest.setRequestManagerComments(inputRequest.getRequestManagerComments());

    // Get the box layouts and set them.
    List boxes = inputRequest.getBoxes();
    populateBoxLayouts(boxes);
    outputRequest.setBoxes(boxes);

    btxDetails.setOutputRequestDto(outputRequest);

    // Get the box scan data for this account.
    btxDetails.setBoxScanData(BoxLayoutUtils.prepareForBoxScan(btxDetails
        .getLoggedInUserSecurityInfo()));

    return btxDetails;
  }

  /**
   * Create a new box request dto and add it to the output request dto. Create the box and box
   * content objects as well and set the box request object accordingly.
   */
  private BTXDetails performFulfillRequestAddBox(BtxDetailsFulfillRequest btxDetails)
      throws Exception {

    // Populate the output dto with a combination of the request dto and the input request dto.
    RequestDto inputRequest = btxDetails.getInputRequestDto();
    RequestDto request = RequestFinder.find(btxDetails.getLoggedInUserSecurityInfo(), btxDetails
        .getRequestSelect(), inputRequest.getId());
    RequestDto outputRequest = (RequestDto) request.clone();

    outputRequest.setRequestManagerComments(inputRequest.getRequestManagerComments());
    outputRequest.setBoxes(inputRequest.getBoxes());

    // Create new request box dto.
    RequestBoxDto newRequestBoxDto = new RequestBoxDto();

    String newBoxId = btxDetails.getNewBoxId();
    newRequestBoxDto.setBoxId(newBoxId);

    String newBoxLayoutId = btxDetails.getNewBoxLayoutId();

    // Create new box & box content.
    BoxDto boxDto = new BoxDto();
    boxDto.setBoxId(newBoxId);
    boxDto.setBoxLayoutId(newBoxLayoutId);

    // Get the box layout dto.
    BoxLayoutDto boxLayoutDto = BoxLayoutUtils.getBoxLayoutDto(newBoxLayoutId);
    boxDto.setBoxLayoutDto(boxLayoutDto);

    // Set the box.
    newRequestBoxDto.setBoxDto(boxDto);

    // Add box to output request dto.
    outputRequest.addBox(newRequestBoxDto);

    // Clear the new box id. This works nice because on error, the new box id is preserved.
    btxDetails.setNewBoxId(null);
    btxDetails.setNewBoxLayoutId(null);

    // Set the output request.
    btxDetails.setOutputRequestDto(outputRequest);
    btxDetails.setActionForwardTXCompleted("success");

    // Mark this transaction as incomplete, to prevent this transaction from being logged.
    // Note that all necessary work for this transaction is in fact completed. The problem
    // is that the default is to log for this detail, only we don't need to log for this
    // transaction.
    btxDetails.setTransactionCompleted(false);

    return btxDetails;
  }

  /**
   * Performs validation for the fulfillment of a request. Validates that the btxDetails contains a
   * RequestDto containing a valid request id (required), and that id points to a request that is
   * pending. The btxDetails might also contain an indicator that the user has confirmed they wish
   * to do a partial fulfillment of the request.
   * 
   * Return a BtxActionError if the request id field is empty or invalid or if the request it points
   * to is not pending, if there is either an unexpected or duplicated item in any of the
   * RequestBoxDtos, if the list of RequestBoxDtos does contain some items but not all of the items
   * on the request are accounted for and the partial fulfillment indicator is empty, or if the list
   * of RequestBoxDtos does contain some items but not all of the items on the request are accounted
   * for and the partial fulfillment indicator is not empty but there is no RequestManagerComments
   * value. In the scenario where not all items are accounted for and their is no confirmation
   * value, we need to append a sentence to the existing RequestManagerComments value listing the
   * items that are missing.
   */
  private BTXDetails validatePerformFulfillRequest(BtxDetailsFulfillRequest btxDetails)
      throws Exception {
    // Check for valid request.
    validateFulfillRequest(btxDetails);

    // Check box id location against user location. Also check if box is in another open request.
    Iterator iterator = btxDetails.getInputRequestDto().getBoxes().iterator();
    while (iterator.hasNext()) {
      RequestBoxDto rbd = (RequestBoxDto) iterator.next();
      String boxId = rbd.getBoxId();
      validateBox(btxDetails, boxId);
    }

    // Check that item ids match what is being scaned in. Get all item ids from all box contents.
    List boxItemIds = validateBoxContents(btxDetails);

    if (boxItemIds.isEmpty()) {
      // Boxes are empty. There needs to be at least one placed item.
      btxDetails
          .addActionError(new BtxActionError("iltds.error.request.fulfillRequest.emptyBoxes"));
    }
    else {

      // Get the item ids from the request.
      List itemIds = getItemIds(btxDetails);

      // Check if all request items have been placed. If not check for partial.
      // In order to avoid recreating the same test in the perform method, a flag is set in
      // validation method along with the data needed within the perform.
      if (boxItemIds.containsAll(itemIds)) {
        btxDetails.setPartialRequest(false);
      }
      else {
        btxDetails.setPartialRequest(true);
      }
      List diff = (List) CollectionUtils.subtract(itemIds, boxItemIds);
      btxDetails.setUnfulfilledItems(new IdList(diff));
      btxDetails.setFulfilledItems(new IdList(boxItemIds));
    }

    // Populate the output dto with a combination of the request dto and the input request dto.
    RequestDto inputRequest = btxDetails.getInputRequestDto();
    RequestDto request = RequestFinder.find(btxDetails.getLoggedInUserSecurityInfo(), btxDetails
        .getRequestSelect(), inputRequest.getId());
    RequestDto outputRequest = (RequestDto) request.clone();

    outputRequest.setRequestManagerComments(inputRequest.getRequestManagerComments());

    // Get the box layouts and set them.
    List boxes = inputRequest.getBoxes();
    populateBoxLayouts(boxes);
    outputRequest.setBoxes(boxes);

    btxDetails.setOutputRequestDto(outputRequest);

    // Get the box scan data for this account.
    btxDetails.setBoxScanData(BoxLayoutUtils.prepareForBoxScan(btxDetails
        .getLoggedInUserSecurityInfo()));

    return btxDetails;
  }

  /**
   * Mark the request fulfilled. Also validates that the RequestDto contains a non-empty list of
   * RequestBoxDto beans, each of which contains a BoxDto bean. Do not add any empty boxes to the
   * request. Issue a warning for each empty box ignored.
   * 
   * Actions this method must perform: - Remove any empty boxes from the list of BoxDto beans (empty
   * boxes should not be included). - Determine if all request items are included in the boxes. -
   * Update the appropriate row in the ILTDS_REQUEST table with a status of 'Fulfilled'. - Mark all
   * samples in all the boxes as box scanned and checked out of inventory (TBD - what status to
   * use?. Make sure trigger inserts rows into ILTDS_SAMPLE_STATUS as well). - If the request is a
   * Research request, remove all samples in all boxes from the users hold list. - Mark all boxes as
   * checked out of inventory. - For every BoxDto bean, create a row in ILTDS_REQUEST_BOX with a
   * SHIPPED_YN value of 'N'. - Send email to the requestor letting them know the request is ready
   * for pickup (if the request is a Research request) or that the request is ready to be shipped
   * (if the request is a Transfer request). The email should include any repository manager
   * comments, and if this is a partial fulfill it should list any items that were not included.
   */
  private BTXDetails performFulfillRequest(BtxDetailsFulfillRequest btxDetails) throws Exception {

    // Check if this is a partial request.
    if (btxDetails.isPartialRequest()) {
      // Check if partial request has been confirmed.
      if (!btxDetails.isPartialFulfillConfirmed()) {
        // Mark this transaction as incomplete, to prevent this transaction from being logged.
        // Note that all necessary work for this transaction is in fact completed. The problem
        // is that the default is to log for this detail, only we don't need to log for this
        // transaction because the user will need to confirm the partial fulfillment before
        //we actually fulfill this request.
        btxDetails.setActionForwardTXIncomplete("confirm");
        return btxDetails;
      }
    }

    // Populate the output dto with a combination of the request dto and the input request dto.
    RequestDto inputRequest = btxDetails.getInputRequestDto();
    RequestDto request = RequestFinder.find(btxDetails.getLoggedInUserSecurityInfo(), btxDetails
        .getRequestSelect(), inputRequest.getId());
    RequestDto outputRequest = (RequestDto) request.clone();

    outputRequest.setRequestManagerComments(inputRequest.getRequestManagerComments());
    // Get the box layouts and set them.
    List boxes = inputRequest.getBoxes();
    populateBoxLayouts(boxes);
    outputRequest.setBoxes(boxes);
    
    List requestItems = outputRequest.getItems();
    Map itemIdToItemMap = new HashMap();
    if (!ApiFunctions.isEmpty(requestItems)) {
      Iterator itemIterator = requestItems.iterator();
      while (itemIterator.hasNext()) {
        RequestItemDto item = (RequestItemDto)itemIterator.next();
        itemIdToItemMap.put(item.getItemId(), item);
      }
    }

    // Get the request id.
    String requestId = inputRequest.getId();
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    // Remove any empty boxes from the list of boxes.
    StringBuffer emptyBoxIds = new StringBuffer();

    // Get a DB connection.
    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      // Update the request.
      StringBuffer requestSql = new StringBuffer(50);
      requestSql = new StringBuffer(50);
      requestSql.append("UPDATE ");
      requestSql.append(DbConstants.TABLE_ILTDS_REQUEST);
      requestSql.append(" SET ");
      requestSql.append(DbConstants.REQUEST_STATE);
      requestSql.append(" = ?, ");
      requestSql.append(DbConstants.REQUEST_CLOSED_YN);
      requestSql.append(" = ?, ");
      requestSql.append(DbConstants.REQUEST_REQUEST_MGR_COMMENTS);
      requestSql.append(" = ? WHERE REQUEST_ID = ?");

      pstmt = con.prepareStatement(requestSql.toString());

      pstmt.setString(1, RequestState.FULFILLED.toString());
      if (request.getType().equals(RequestType.RESEARCH)) {
        pstmt.setString(2, FormLogic.DB_YES);
      }
      else {
        pstmt.setString(2, FormLogic.DB_NO);
      }
      pstmt.setString(3, inputRequest.getRequestManagerComments());
      pstmt.setString(4, requestId);

      pstmt.executeUpdate();
      ApiFunctions.close(pstmt);
      pstmt = null;

      //update the items that were included on the fulfilled request
      //to indicate they were included
      StringBuffer itemSql = new StringBuffer(50);
      itemSql.append("update ");
      itemSql.append(DbConstants.TABLE_ILTDS_REQUEST_ITEM);
      itemSql.append(" set ");
      itemSql.append(DbConstants.REQUEST_ITEM_FULFILLED_YN);
      itemSql.append(" = ?, ");
      itemSql.append(DbConstants.REQUEST_ITEM_BOX_BARCODE_ID);
      itemSql.append(" = ? where ");
      itemSql.append(DbConstants.REQUEST_ITEM_REQUEST_ID);
      itemSql.append(" = ? and ");
      itemSql.append(DbConstants.REQUEST_ITEM_ITEM_ID);
      itemSql.append(" = ?");

      pstmt = con.prepareStatement(itemSql.toString());
      Iterator boxIterator = outputRequest.getBoxes().iterator();
      List fulfilledSampleIds = new ArrayList();
      while (boxIterator.hasNext()) {
        RequestBoxDto requestBoxDto = (RequestBoxDto) boxIterator.next();
        BoxDto boxDto = requestBoxDto.getBoxDto();

        Set keys = boxDto.getContents().keySet();
        Iterator keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
          String cellRef = (String) keyIterator.next();
          String sampleId = boxDto.getCellContent(cellRef);
          if (!ApiFunctions.isEmpty(sampleId)) {
            //the specified sample id might in fact be an alias.  The call to checkSampleId will return
            //the sample id to which that sample belongs.  Given that we've gone through validation
            //we can safely assume that a sample id can be determined.
            sampleId = checkSampleId(boxDto.getCellContent(cellRef), "", btxDetails,
                false, false, true);
            //add this sample id to an overall list of fulfilled sample ids,
            //so we can remove them all from the hold list in one call below
            fulfilledSampleIds.add(sampleId);
            pstmt.setString(1, FormLogic.DB_YES);
            pstmt.setString(2, boxDto.getBoxId());
            pstmt.setString(3, requestId);
            pstmt.setString(4, sampleId);
            pstmt.executeUpdate();
          }
        }
      }
      ApiFunctions.close(pstmt);
      pstmt = null;

      //MR7062
      //if this is a research request, remove all fulfilled samples
      //from the requesters hold list. We only remove the fulfilled samples, and leave
      //the unfulfilled ones on the hold list. The unfilled items are given an inv_status value of
      //"BOXSCAN" below, so they will no longer be marked as "REQUESTED", but they should
      //continue to remain on the users hold list so the user can either re-request them
      //or manually remove them from their hold list.
      if (request.getType().equals(RequestType.RESEARCH) && fulfilledSampleIds.size() > 0) {
        BTXDetailsRemoveFromHoldList btx = new BTXDetailsRemoveFromHoldList();
        btx.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
        btx.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo(), true);
        btx.setSamples(ApiFunctions.toStringArray(fulfilledSampleIds));
        //populate the sample information on the btxDetails
        List samples = new ArrayList();
        btx.setSampleDtos(samples);
        Iterator fulfilledSampleIdIterator = fulfilledSampleIds.iterator();
        while (fulfilledSampleIdIterator.hasNext()) {
          String fulfilledSampleId = (String)fulfilledSampleIdIterator.next();
          RequestItemDto item = (RequestItemDto)itemIdToItemMap.get(fulfilledSampleId);
          if (item != null && item.getItem().getSampleData() != null) {
            samples.add(item.getItem().getSampleData());
          }
        }
        //set the holdlist owner to be the submitter of the request
        btx.setHoldListOwner(request.getRequesterId());
        btx.setTransactionType("library_remove_from_hold_list");
        Btx.perform(btx);
      }

      //MR7081
      //update any samples on the request that were not included in the
      //fulfillment (i.e. a partial fulfill situation occurred, and these
      //are the samples left off) to have a status of BOXSCAN. To make sure
      //that nobody has fixed these samples in the interim (i.e. the sample
      //wasn't fulfillable because it had been stepped on, and sometime between
      //when the request was created and this point somebody marked the sample
      //as destroyed in the database), only do this for samples that are still
      //in a requested state.
      if (btxDetails.getUnfulfilledItems().size() > 0) {
        StringBuffer unfilledItemSql = new StringBuffer(50);
        unfilledItemSql.append(" select ");
        unfilledItemSql.append(DbConstants.SAMPLE_ID);
        unfilledItemSql.append(" from ");
        unfilledItemSql.append(DbConstants.TABLE_SAMPLE);
        unfilledItemSql.append(" where ");
        unfilledItemSql.append(DbConstants.SAMPLE_INV_STATUS);
        unfilledItemSql.append(" = '");
        unfilledItemSql.append(FormLogic.SMPL_REQUESTED);
        unfilledItemSql.append("' and ");
        unfilledItemSql.append(DbConstants.SAMPLE_ID);
        unfilledItemSql.append(" in (");
        Iterator itemIterator = btxDetails.getUnfulfilledItems().iterator();
        boolean includeComma = false;
        while (itemIterator.hasNext()) {
          if (includeComma) {
            unfilledItemSql.append(",");
          }
          includeComma = true;
          unfilledItemSql.append("'");
          unfilledItemSql.append((String) itemIterator.next());
          unfilledItemSql.append("'");
        }
        unfilledItemSql.append(")");
        pstmt = con.prepareStatement(unfilledItemSql.toString());
        rs = pstmt.executeQuery();
        Timestamp now = new Timestamp(new java.util.Date().getTime());
        while (rs.next()) {
          SamplestatusAccessBean status = new SamplestatusAccessBean();
          status.setInit_argSample(new SampleKey(rs.getString(1)));
          status.setInit_argStatus_type_code(FormLogic.SMPL_BOXSCAN);
          status.setInit_argSample_status_datetime(now);
          status.commitCopyHelper();
        }
        ApiFunctions.close(rs);
        rs = null;
        ApiFunctions.close(pstmt);
        pstmt = null;
      }

      // Insert the requested boxes.
      StringBuffer boxSql = new StringBuffer(50);
      boxSql.append("INSERT INTO ");
      boxSql.append(DbConstants.TABLE_ILTDS_REQUEST_BOX);
      boxSql.append(" (");
      boxSql.append(DbConstants.REQUEST_BOX_REQUEST_ID);
      boxSql.append(", ");
      boxSql.append(DbConstants.REQUEST_BOX_BOX_BARCODE_ID);
      boxSql.append(", ");
      boxSql.append(DbConstants.REQUEST_BOX_SHIPPED_YN);
      boxSql.append(", ");
      boxSql.append(DbConstants.REQUEST_BOX_BOX_ORDER);
      boxSql.append(", ");
      boxSql.append(DbConstants.REQUEST_BOX_BOX_CONTENTS);
      boxSql.append(") VALUES (?, ?, ?, ?, ?)");
      pstmt = con.prepareStatement(boxSql.toString());

      // Use the output request boxes because we are deleting empty boxes and we do not want
      // the empty boxes to show up in the transaction history.
      boxIterator = outputRequest.getBoxes().iterator();
      int order = 0;
      while (boxIterator.hasNext()) {
        RequestBoxDto requestBoxDto = (RequestBoxDto) boxIterator.next();
        BoxDto boxDto = requestBoxDto.getBoxDto();

        // Get the samples to be checked out.
        Vector samples = new Vector();

        Set keys = boxDto.getContents().keySet();
        Iterator keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
          String cellRef = (String) keyIterator.next();
          String sampleId = boxDto.getCellContent(cellRef);
          if (!ApiFunctions.isEmpty(sampleId)) {
            //the specified sample id might in fact be an alias.  The call to checkSampleId will return
            //the sample id to which that sample belongs.  Given that we've gone through validation
            //we can safely assume that a sample id can be determined.
            sampleId = checkSampleId(boxDto.getCellContent(cellRef), "", btxDetails,
                false, false, true);
            //update the cell content to hold the sample id, in case it was holding an alias
            boxDto.setCellContent(cellRef, sampleId);
            //SampleData sample = new SampleData();
            //sample.setSampleId(sampleId);
            //sample.setLocation(cellRef);
            SampleData sample = (SampleData)((RequestItemDto)itemIdToItemMap.get(sampleId)).getItem().getSampleData();
            sample.setLocation(cellRef);
            samples.add(sample);
          }
        }

        // Process any box that is not empty.
        if (!samples.isEmpty()) {

          // Check the box out of inventory. If this is a transfer request do not
          // clear the box information from the samples, as we need to maintain it
          // for shipping purposes.
          boolean clearSampleBoxInformation = !RequestType.TRANSFER.toString().equalsIgnoreCase(
              request.getType().toString());

          // Check out box.
          BtxDetailsCheckOutBox checkOutBox = new BtxDetailsCheckOutBox();
          checkOutBox.setLoggedInUserSecurityInfo(securityInfo);
          checkOutBox.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
          checkOutBox.setTransactionType("iltds_box_checkOutBox");

          checkOutBox.setBoxId(boxDto.getBoxId());
          checkOutBox.setBoxLayoutDto(boxDto.getBoxLayoutDto());
          checkOutBox.setSamples(samples);
          checkOutBox.setReason(FormLogic.BOX_FULFILLREQUEST);
          checkOutBox.setRequestedBy(securityInfo.getUsername());
          checkOutBox.setClearSampleBoxInformation(clearSampleBoxInformation);
          //set the request id, so the check out box code knows that this box is being
          //checked out for a request. This is important because without this
          //information the check out box code will not allows samples on an open request
          //to be checked out.
          checkOutBox.setRequestId(requestId);
          //set the list of samples that should not be orphaned. See MR8203 for details, but
          //any samples on the request that are "fulfilled" should not be orphaned.
          checkOutBox.setFulfilledSampleIds(fulfilledSampleIds);

          Btx.perform(checkOutBox);

          TemporaryClob boxContentsClob = new TemporaryClob(con, boxDto.pack());

          // Create a request box record.
          order = order + 1;
          pstmt.setString(1, requestId);
          pstmt.setString(2, boxDto.getBoxId());
          pstmt.setString(3, FormLogic.DB_NO);
          pstmt.setInt(4, order);
          pstmt.setClob(5, boxContentsClob.getSQLClob());

          pstmt.executeUpdate();

          // For every box used on the request, create a history record to show the
          // contents of the box
          BtxDetailsRequestItemsBoxed btxDetailsBox = new BtxDetailsRequestItemsBoxed();
          btxDetailsBox.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
          btxDetailsBox.setLoggedInUserSecurityInfo(securityInfo);
          btxDetailsBox.setBox(boxDto);
          btxDetailsBox.setRequestId(requestId);
          btxDetailsBox.setSamples(samples);
          btxDetailsBox.setTransactionType("iltds_placeholder");
          Btx.perform(btxDetailsBox);
        }
        else {
          // Account for all boxes that are empty on the request.
          emptyBoxIds.append(boxDto.getBoxId() + " ");
          boxIterator.remove();
        }
      }
      ApiFunctions.close(pstmt);
      pstmt = null;
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(rs);
      ApiFunctions.close(con);
    }

    // Issue a warning for all boxes that were ignored because they were empty.
    if (!ApiFunctions.isEmptyOrWhitespace(emptyBoxIds.toString())) {
      btxDetails.addActionMessage(new BtxActionMessage(
          "iltds.message.request.fulfillRequest.emptyBoxes", requestId, emptyBoxIds.toString()));
    }

    // Send email to the requester letting them know the request is ready (for pickup /to be
    // shipped). It is for pickup if it is a research request, It is to be shipped if it is a
    // transfer request. The email should include any repository manager comments. If
    // this is partial fulfill request, list the items not included.
    String requesterEmail = getUserEmailForUser(request.getRequesterId());
    if (!ApiFunctions.isEmpty(requesterEmail)) {
      StringBuffer mailMessage = new StringBuffer(100);
      if (request.getType().toString().equalsIgnoreCase(RequestType.RESEARCH.toString())) {
        mailMessage.append("Request ");
        mailMessage.append(requestId);
        mailMessage.append(" is ready for pickup.<br>");
      }
      else {
        mailMessage.append("Your ");
        mailMessage.append(request.getType().toString().toLowerCase());
        mailMessage.append(" request (");
        mailMessage.append(requestId);
        mailMessage.append(") has been packaged and is now awaiting shipment.");
        mailMessage.append("  You will receive another email confirming the shipment.<br>");
      }
      String requestManagerComments = inputRequest.getRequestManagerComments();
      if (!ApiFunctions.isEmpty(requestManagerComments)) {
        mailMessage.append("<p>Request Manager Comments:<br>");
        Escaper.htmlEscapeAndPreserveWhitespace(requestManagerComments, mailMessage);
      }
      IdList unfulfilledItems = btxDetails.getUnfulfilledItems();
      if (!ApiFunctions.isEmpty(unfulfilledItems.getList())) {
        mailMessage.append("<p>The following items have not been fulfilled: <br>");
        Iterator iterator = unfulfilledItems.getList().iterator();
        while (iterator.hasNext()) {
          String id = (String) iterator.next();
          mailMessage.append(id + "<br>");
        }
      }
      if (!ApiFunctions.generateEmail(
          ApiProperties.getProperty(ApiResources.API_MAIL_FROM_DEFAULT), requesterEmail,
          "Your request (" + requestId + ") has been fulfilled.", mailMessage.toString())) {
        // MR7170 - let request manager know that email is down.
        btxDetails.addActionMessage(new BtxActionMessage("iltds.message.request.emailFailed",
            requesterEmail));
      }
    }

    btxDetails.setOutputRequestDto(outputRequest);
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * This method is used when the user wants to back out a change through the wizard.
   */
  private BTXDetails performFulfillRequestNoOp(BtxDetailsFulfillRequest btxDetails)
      throws Exception {

    // Populate the output dto with a combination of the request dto and the input request dto.
    RequestDto inputRequest = btxDetails.getInputRequestDto();
    RequestDto request = RequestFinder.find(btxDetails.getLoggedInUserSecurityInfo(), btxDetails
        .getRequestSelect(), inputRequest.getId());
    RequestDto outputRequest = (RequestDto) request.clone();

    outputRequest.setRequestManagerComments(inputRequest.getRequestManagerComments());

    // Get the box layouts and set them.
    List boxes = inputRequest.getBoxes();
    populateBoxLayouts(boxes);
    outputRequest.setBoxes(boxes);

    // Set the output request.
    btxDetails.setOutputRequestDto(outputRequest);
    btxDetails.setActionForwardTXCompleted("success");

    // Get the box scan data for this account.
    btxDetails.setBoxScanData(BoxLayoutUtils.prepareForBoxScan(btxDetails
        .getLoggedInUserSecurityInfo()));

    // Mark this transaction as incomplete, to prevent this transaction from being logged.
    // Note that all necessary work for this transaction is in fact completed. The problem
    // is that the default is to log for this detail, only we don't need to log for this
    // transaction.
    btxDetails.setTransactionCompleted(false);

    return btxDetails;
  }

  private void populateBoxLayouts(List boxes) {
    for (int i = 0; i < boxes.size(); i++) {
      RequestBoxDto requestBoxDto = (RequestBoxDto) boxes.get(i);
      BoxDto boxDto = requestBoxDto.getBoxDto();

      BoxLayoutDto boxLayoutDto = BoxLayoutUtils.getBoxLayoutDto(boxDto.getBoxLayoutId());
      boxDto.setBoxLayoutDto(boxLayoutDto);
    }
  }

  /**
   * Performs validation for updating requests after boxes have been shipped. Validates that a
   * manifest id has been specified on the btxDetails.
   */
  private BTXDetails validatePerformUpdateRequestsAfterShipment(
                                                                BtxDetailsUpdateRequestsAfterShipment btxDetails)
      throws Exception {
    String manifestNumber = btxDetails.getManifestDto().getManifestNumber();
    if (ApiFunctions.isEmpty(manifestNumber)) {
      btxDetails.addActionError(new BtxActionError("iltds.error.general.requiredManifestNumber"));
    }
    return btxDetails;
  }

  /**
   * Update the request-related database information to reflect that the boxes on a manifest have
   * been packaged and shipped.
   */
  private BTXDetails performUpdateRequestsAfterShipment(
                                                        BtxDetailsUpdateRequestsAfterShipment btxDetails)
      throws Exception {

    String manifestNumber = btxDetails.getManifestDto().getManifestNumber();
    //get the requests containing the boxes being shipped
    List requests = determineRequestsContainingBoxes(manifestNumber);
    //update the boxes on the manifest to indicate they've been shipped
    updateShippedManifestRequestBoxes(manifestNumber);
    //update any requests that have now had all of their boxes shipped
    updateTransferRequestState(btxDetails, requests);
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Determine the requests that hold the boxes that are being shipped on the manifest passed in.
   * 
   * @param manifest The manifest to ship.
   * @return List A list of request ids that belong to the boxes being shipped. Specifically, this
   *         returns all request ids for open, fulfilled Transfer requests that have unshipped boxes
   *         on this manifest.
   */
  private List determineRequestsContainingBoxes(String manifestNumber) {
    List requestIds = new ArrayList();
    StringBuffer sql = new StringBuffer();
    sql.append("select distinct rb.request_id from iltds_request_box rb ");
    sql.append("\nwhere rb.shipped_yn = 'N' ");
    sql.append("\nand rb.box_barcode_id in ");
    sql
        .append("\n       (select b.box_barcode_id from iltds_sample_box b where b.manifest_number = ?) ");
    sql.append("\nand rb.request_id in ");
    sql.append("\n       (select r.request_id from iltds_request r ");
    sql.append("\n        where r.closed_yn = 'N'");
    sql.append("\n          and r.request_type = '");
    sql.append(RequestType.TRANSFER.toString());
    sql.append("'");
    sql.append("\n          and r.state = '");
    sql.append(RequestState.FULFILLED.toString());
    sql.append("')");

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());

      pstmt.setString(1, manifestNumber);

      rs = pstmt.executeQuery();
      while (rs.next()) {
        String requestId = rs.getString(1);
        requestIds.add(requestId);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(rs);
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
    return requestIds;
  }

  /**
   * Update database information related to the "shipped" state of request boxes (state stored in
   * ILTDS_REQUEST_BOX) to reflect that the boxes on a manifest have been packaged and shipped.
   * 
   * @param manifest The manifest to ship.
   */
  private void updateShippedManifestRequestBoxes(String manifestNumber) {
    // Set the iltds_request_box shipped_yn status to Y for all boxes that are on the
    // specified manifest and for which the corresponding request is an open transfer
    // request that is in the Fulfilled state.

    StringBuffer sql = new StringBuffer(256);
    sql.append("update iltds_request_box rb set rb.shipped_yn = 'Y' ");
    sql.append("\nwhere rb.shipped_yn = 'N' ");
    sql.append("\n  and rb.box_barcode_id in ");
    sql
        .append("\n       (select b.box_barcode_id from iltds_sample_box b where b.manifest_number = ?) ");
    sql.append("\n  and rb.request_id in ");
    sql.append("\n       (select r.request_id from iltds_request r ");
    sql.append("\n        where r.closed_yn = 'N'");
    sql.append("\n          and r.request_type = '");
    sql.append(RequestType.TRANSFER.toString());
    sql.append("'");
    sql.append("\n          and r.state = '");
    sql.append(RequestState.FULFILLED.toString());
    sql.append("')");

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());

      pstmt.setString(1, manifestNumber);

      pstmt.executeUpdate();
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Update the state and closed yes/no status of transfer requests. This will update transfer
   * requests to have state = Shipped and closed = Y for all transfer requests that are (1)
   * currently open and in the Fulfilled state and (2) all boxes on the request are in the shipped
   * state.
   * 
   * @param List a List of request ids that are candidates to be marked closed.
   */
  private void updateTransferRequestState(BTXDetails btxDetails, List requestIds) {
    // The query tests for both (a) there being at least one box on the request and (b) there
    // being no boxes on the request that aren't shipped for extra robustness. You could imagine
    // just testing (b), but that would result in closing and setting to shipped a fulfilled
    // request that doesn't have any boxes on it. This should never happen, but it doesn't
    // hurt to protect against it.

    if (ApiFunctions.isEmpty(requestIds)) {
      return;
    }

    //find the ids of the requests to mark closed
    StringBuffer buff = new StringBuffer(256);
    buff.append("select r.request_id from iltds_request r ");
    buff.append("\nwhere r.closed_yn = 'N'");
    buff.append("\n  and r.request_type = '");
    buff.append(RequestType.TRANSFER.toString());
    buff.append("'");
    buff.append("\n  and r.state = '");
    buff.append(RequestState.FULFILLED.toString());
    buff.append("'");
    buff.append("\n  and r.request_id in ");
    buff.append(ApiFunctions.makeBindParameterList(requestIds.size()));
    buff
        .append("\n  and exists (select 1 from iltds_request_box rb0 where rb0.request_id = r.request_id)");
    buff.append("\n  and not exists");
    buff.append("\n    (select 1 from iltds_request_box rb1");
    buff.append("\n     where rb1.request_id = r.request_id and rb1.shipped_yn = 'N')");

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List requestIdsToClose = new ArrayList();
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(buff.toString());
      ApiFunctions.bindBindParameterList(pstmt, 1, requestIds);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String requestId = rs.getString(1);
        requestIdsToClose.add(requestId);
      }
      ApiFunctions.close(rs);
      rs = null;
      ApiFunctions.close(pstmt);
      pstmt = null;

      if (!requestIdsToClose.isEmpty()) {
        //mark those requests as closed and shipped
        buff = new StringBuffer(100);
        buff.append("update iltds_request r set r.closed_yn = 'Y', r.state = '");
        buff.append(RequestState.SHIPPED.toString());
        buff.append("'");
        buff.append("\nwhere r.request_id in ");
        buff.append(ApiFunctions.makeBindParameterList(requestIdsToClose.size()));
        pstmt = con.prepareStatement(buff.toString());
        ApiFunctions.bindBindParameterList(pstmt, 1, requestIdsToClose);
        pstmt.executeUpdate();
        ApiFunctions.close(pstmt);
        pstmt = null;
        ApiFunctions.close(con);
        con = null;

        //send email to the submitter of each request we just closed, letting them
        //know their requests have been shipped
        for (int i = 0; i < requestIdsToClose.size(); i++) {
          String requestId = (String) requestIdsToClose.get(i);
          RequestDto request = RequestFinder.find(btxDetails.getLoggedInUserSecurityInfo(),
              RequestSelect.BASIC, requestId);
          String requesterEmail = getUserEmailForUser(request.getRequesterId());
          if (!ApiFunctions.isEmpty(requesterEmail)) {
            StringBuffer mailMessage = new StringBuffer(100);
            mailMessage.append("Your ");
            mailMessage.append(request.getType().toString().toLowerCase());
            mailMessage.append(" request (");
            mailMessage.append(requestId);
            mailMessage.append(") has been shipped.");
            ApiFunctions.generateEmail(ApiProperties
                .getProperty(ApiResources.API_MAIL_FROM_DEFAULT), requesterEmail, "Your request ("
                + requestId + ") has been shipped.", mailMessage.toString());
          }
        }
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(rs);
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /*
   * Check if fulfill request is valid.
   */
  private void validateFulfillRequest(BtxDetailsFulfillRequest btxDetails) {
    validatePendingRequest(btxDetails);
    RequestDto dto = btxDetails.getInputRequestDto();
    if (dto != null) {
      //make sure the requester comments do not exceed the maximum database length
      String comments = dto.getRequestManagerComments();
      if (!ApiFunctions.isEmpty(comments)) {
        if (comments.length() > MAX_COMMENT_LENGTH) {
          btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Comments",
              MAX_COMMENT_LENGTH + " characters"));
        }
      }
    }
  }

  private void validatePendingRequest(BtxDetailsRequestOperation btxDetails) {
    RequestDto dto = btxDetails.getInputRequestDto();
    if (dto == null) {
      btxDetails.addActionError(new BtxActionError("iltds.error.request.general.noInput"));
    }
    else {
      String requestId = dto.getId();
      if (ApiFunctions.isEmpty(requestId)) {
        btxDetails
            .addActionError(new BtxActionError("iltds.error.request.findRequest.noRequestId"));
      }
      else {
        RequestSelect select = RequestSelect.BASIC;
        RequestDto request = RequestFinder.find(btxDetails.getLoggedInUserSecurityInfo(), select,
            requestId);
        if (request == null) {
          btxDetails.addActionError(new BtxActionError(
              "iltds.error.request.findRequest.requestNotFound", requestId));
        }
        else {
          if (!request.getState().equals(RequestState.PENDING)) {
            btxDetails.addActionError(new BtxActionError(
                "iltds.error.request.manageRequest.notPending", requestId));
          }
        }
      }
    }
  }

  /*
   * Check box id location against user location, check if box is already in pending request, and
   * check if box is in transit (existing manifest).
   */
  private void validateBox(BtxDetailsRequestOperation btxDetails, String boxId) {
    validateBoxLocation(btxDetails, boxId);
    validateBoxAgainstOpenRequest(btxDetails, boxId);
    validateBoxAgainstInTransit(btxDetails, boxId);
  }

  /*
   * Check if the box location matches the user location. If it is a new box then no check is
   * performed. If the locations don't match an error is logged.
   */
  private void validateBoxLocation(BtxDetailsRequestOperation btxDetails, String boxId) {
    //grab the location of the box
    BoxlocationAccessBean location = new BoxlocationAccessBean();
    AccessBeanEnumeration locEnum = null;
    String boxLoc = null;
    try {
      locEnum = (AccessBeanEnumeration) location
          .findBoxlocationBySamplebox(new SampleboxKey(boxId));
      if (locEnum.hasMoreElements()) {
        location = (BoxlocationAccessBean) locEnum.nextElement();
        boxLoc = ((GeolocationKey) location.getGeolocationKey()).location_address_id;
      }
      else {
        // New box, therefore no location assigneds, so no need to check.
        // No locations found, nothing to do here.
        return;
      }
    }
    catch (Exception ex) {
      ApiFunctions.throwAsRuntimeException(ex);
    }

    String userLoc = btxDetails.getLoggedInUserSecurityInfo().getUserLocationId();
    if (!userLoc.equalsIgnoreCase(boxLoc)) {
      // Invalid box location error.
      btxDetails.addActionError(new BtxActionError("iltds.error.general.locationMismatch", boxId,
          boxLoc, userLoc));
    }
  }

  /*
   * Check if box already exist within another open request.
   */
  private void validateBoxAgainstOpenRequest(BtxDetailsRequestOperation btxDetails, String boxId) {
    // Set up the filter.
    RequestFilter filter = new RequestFilter();
    filter.setIncludeClosedRequests(false);
    filter.setBoxId(boxId);
    List openRequests = RequestFinder.find(btxDetails.getLoggedInUserSecurityInfo(),
        RequestSelect.BASIC, filter);

    if (openRequests.size() > 0) {
      RequestDto request = (RequestDto) openRequests.get(0);
      // Invalid box location error.
      btxDetails.addActionError(new BtxActionError("iltds.error.general.boxInOpenRequest", boxId,
          request.getId()));
    }
  }

  /*
   * Check if box is already in transit.
   */
  private void validateBoxAgainstInTransit(BtxDetailsRequestOperation btxDetails, String boxId) {

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      // SELECT 1 FROM iltds_sample_box isb, iltds_manifest im
      // WHERE isb.MANIFEST_NUMBER = im.MANIFEST_NUMBER
      // AND im.SHIPMENT_STATUS in ('MFPACKAGED', 'MFSHIPPED', 'MFVERIFIED')
      // AND isb.BOX_STATUS = 'CHECKEDOUT'
      // AND isb.BOX_BARCODE_ID = 'BX1000010002'

      StringBuffer query = new StringBuffer(100);
      query.append("SELECT 1 FROM iltds_sample_box isb, iltds_manifest im ");
      query.append("WHERE isb.MANIFEST_NUMBER = im.MANIFEST_NUMBER ");
      query.append("AND im.SHIPMENT_STATUS in (?, ?, ?) ");
      query.append("AND isb.BOX_STATUS in (?, ?) ");
      query.append("AND isb.BOX_BARCODE_ID = ?");

      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, FormLogic.MNFT_MFPACKAGED);
      pstmt.setString(2, FormLogic.MNFT_MFSHIPPED);
      pstmt.setString(3, FormLogic.MNFT_MFVERIFIED);
      pstmt.setString(4, FormLogic.BX_CHECKEDOUT);
      pstmt.setString(5, FormLogic.BX_SCANRECEIVED);
      pstmt.setString(6, boxId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        // Box is in transit, report an in transit box error.
        btxDetails.addActionError(new BtxActionError(
            "iltds.error.general.fulfillRequest.boxInTransit", boxId));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }

  /*
   * Check the box contents for duplicate entries and if the items truely belong to the request. Add
   * an action error for each duplicate entry violation. Also, add an action error for items that do
   * not belong with the request.
   */
  private List validateBoxContents(BtxDetailsFulfillRequest btxDetails) {

    // Get the item ids from the request.
    List itemIds = getItemIds(btxDetails);

    // Create a hash set to check for duplicate entries.
    Set set = new HashSet();
    ArrayList boxItemIds = new ArrayList();
    //create a map to handle sampleId to Alias information
    Map sampleIdToAlias = new HashMap();

    // Iterate through all the boxes and check for duplicate cell entries as well as items
    // that do not belong in the request.
    Iterator boxes = btxDetails.getInputRequestDto().getBoxes().iterator();
    while (boxes.hasNext()) {
      RequestBoxDto rbd = (RequestBoxDto) boxes.next();
      BoxDto boxDto = rbd.getBoxDto();
      ArrayList thisBoxItems = new ArrayList();

      Set keys = boxDto.getContents().keySet();
      Iterator iterator = keys.iterator();
      while (iterator.hasNext()) {
        String cellRef = (String) iterator.next();
        String specifiedSampleId = boxDto.getCellContent(cellRef);
        //the specified sample id might in fact be an alias.  The call to checkSampleId will return
        //the sample id (if any) to which that sample belongs if it can be determined.
        String sampleId = checkSampleId(boxDto.getCellContent(cellRef), rbd.getBoxId(), btxDetails,
            false, false, true);
        // Ignore empty cell.
        if (!ApiFunctions.isEmpty(sampleId)) {
          //if the returned sample id isn't the same as the specified sample id, assume the
          //specified sample id is an alias so we need to maintain the mapping between ids and
          //aliases.
          if (!sampleId.equalsIgnoreCase(specifiedSampleId)) {
            sampleIdToAlias.put(sampleId, specifiedSampleId);
          }
          if (!set.add(sampleId)) {
            // Item appears in more than one box cell location.  Include the alias (if any) in the
            // error message.
            String alias = (String)sampleIdToAlias.get(sampleId);
            StringBuffer buff = new StringBuffer(50);
            buff.append(sampleId);
            if (!ApiFunctions.isEmpty(alias)) {
              buff.append(" (");
              buff.append(alias);
              buff.append(")");
            }
            btxDetails.addActionError(new BtxActionError(
                "iltds.error.request.fulfillRequest.duplicateBoxItem", Escaper.htmlEscapeAndPreserveWhitespace(buff.toString()), rbd.getBoxId()));
          }
          else {
            boxItemIds.add(sampleId);
            thisBoxItems.add(sampleId);
          }
          if (!itemIds.contains(sampleId)) {
            // Item does not belong with request.  Include the alias (if any) in the error message.
            String alias = (String)sampleIdToAlias.get(sampleId);
            StringBuffer buff = new StringBuffer(50);
            buff.append(sampleId);
            if (!ApiFunctions.isEmpty(alias)) {
              buff.append(" (");
              buff.append(alias);
              buff.append(")");
            }
            btxDetails.addActionError(new BtxActionError(
                "iltds.error.request.fulfillRequest.itemNotInRequest", Escaper.htmlEscapeAndPreserveWhitespace(buff.toString()), rbd.getBoxId()));
          }

          // MR8022 - we no longer enforce a box that contains a single sample type nor do we
          // disallow a box with incompatible storage types.
        }
      }
      //MR7132 - make sure all the boxed items are local
      if (!thisBoxItems.isEmpty()) {
        try {
          SecurityInfo secInfo = btxDetails.getLoggedInUserSecurityInfo();
          SampleOperationHome soHome = (SampleOperationHome) EjbHomes.getHome(SampleOperationHome.class);
          SampleOperation sampleBean = soHome.create();
          List nonLocalSamples = sampleBean.identifyNonLocalSamples(thisBoxItems, secInfo, false, false);
          if (!nonLocalSamples.isEmpty()) {
            //iterate over the nonlocal samples, including the alias value (if one exists)
            Iterator nonLocalSamplesIterator = nonLocalSamples.iterator();
            StringBuffer nonLocalBuffer = new StringBuffer(100);
            boolean includeComma = false;
            while (nonLocalSamplesIterator.hasNext()) {
              String sampleId = (String)nonLocalSamplesIterator.next();
              if (includeComma) {
                nonLocalBuffer.append(", ");
              }
              nonLocalBuffer.append(sampleId);
              String alias = (String)sampleIdToAlias.get(sampleId);
              if (!ApiFunctions.isEmpty(alias)) {
                nonLocalBuffer.append(" (");
                nonLocalBuffer.append(alias);
                nonLocalBuffer.append(")");
              }
              includeComma = true;
            }
            btxDetails.addActionError(new BtxActionError(
                "iltds.error.request.fulfillRequest.nonLocalItems", rbd.getBoxId(), 
                Escaper.htmlEscapeAndPreserveWhitespace(nonLocalBuffer.toString())));
          }
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }
      }
    }
    // Return the list of items collected in all the boxes.
    return boxItemIds;
  }

  /**
   * Validate the specified sample id. Return the canonical form of the sample id if the sample id
   * is valid, otherwise return null and add appropriate ActionErrors to btxDetails. When isRequired
   * is false, a null sample id is considered valid. To make it easier for callers to know whether
   * the id was valid in all situations, this method returns an empty string ("") when the input
   * sample id is null and isRequired is false.
   * 
   * @param sampleId The sample id to validate.
   * @param id The box id the sample is in. *
   * @param btxDetails The btxDetails object to add ActionErrors to.
   * @param lenient True to accept non-canonical forms of the id (e.g. "fr12").
   * @param isRequired True if the sample id is required. This is always treated as if it were true
   *          when the mustExist parameter is true.
   * @param mustExist True if the sample id must specify a sample that exists in the database.
   * @return The sample id in canonical form if it is valid, otherwise null.
   */
  public static String checkSampleId(String sampleId, String boxId, BTXDetails btxDetails,
                                     boolean lenient, boolean isRequired, boolean mustExist) {

    boolean isOk = true;

    if (lenient) {
      sampleId = ApiFunctions.safeString(ApiFunctions.safeTrim(sampleId));
    }

    if (ApiFunctions.isEmpty(sampleId)) {
      if (isRequired) {
        btxDetails.addActionError(new BtxActionError(
            "iltds.error.request.fulfillRequest.requiredSampleId", boxId));
        return null;
      }
      else {
        return ApiFunctions.EMPTY_STRING;
      }
    }

    //check to see if the specified sample id is an alias, and if so try to convert that alias to
    //a system sample id.  If the value does not begin with the prefix for any of our sample ids 
    //(FR, PA, SX) then assume it is a sample alias and handle the alias as follows:
    // - if the alias corresponds to multiple existing sample ids, return an error.
    // - if the alias corresponds to a single existing sample id, see if the account to which that 
    //      sample belongs requires unique aliases.
    //      - if not, return an error since we cannot be positive the sample we found was the one
    //        intended.
    //      - if so, use that sample id
    // - if the alias corresponds to no existing sample id return an error.
    if (!IltdsUtils.isSystemSampleId(sampleId)) {
      //pass true to the findSampleIdsFromCustomerId call, since requests can only contain
      //annotated samples.
      List existingSamples = IltdsUtils.findSamplesFromCustomerId(sampleId, true);
      //if multiple samples with the specified alias were found, return an error.
      if (existingSamples.size() > 1) {
        btxDetails.addActionError(new BtxActionError(
            "iltds.error.request.fulfillRequest.multipleMatchingSamplesForAlias", boxId, Escaper.htmlEscape(sampleId)));
        return null;
      }
      //if a single sample was found, do some further checking
      if (existingSamples.size() == 1) {
        String accountId = ((SampleData)existingSamples.get(0)).getArdaisAcctKey();
        AccountDto accountDto = IltdsUtils.getAccountById(accountId, false, false);
        boolean aliasMustBeUnique = FormLogic.DB_YES.equalsIgnoreCase(accountDto.getRequireUniqueSampleAliases());
        //if the account to which the sample belongs does not require unique sample aliases,
        //return an error since we cannot be sure the sample we found was the one intended.
        if (!aliasMustBeUnique) {
          btxDetails.addActionError(new BtxActionError(
              "iltds.error.request.fulfillRequest.nonUniqueMatchingSampleForAlias", boxId, Escaper.htmlEscape(sampleId)));
          return null;
        }
        else {
          sampleId = ((SampleData)existingSamples.get(0)).getSampleId();
        }
      }
      //if multiple samples with the specified alias were found, return an error.
      if (existingSamples.size() == 0) {
        btxDetails.addActionError(new BtxActionError(
            "iltds.error.request.fulfillRequest.notFoundSampleForAlias", boxId, Escaper.htmlEscape(sampleId)));
        return null;
      }
    }
    
    {
      String validatedId = ValidateIds.validateId(sampleId, ValidateIds.TYPESET_SAMPLE, lenient);
      if (validatedId == null) {
        btxDetails.addActionError(new BtxActionError(
            "iltds.error.request.fulfillRequest.invalidSampleId", boxId, Escaper.htmlEscape(sampleId)));
        return null;
      }
      sampleId = validatedId;
    }

    if (mustExist && !sampleExists(sampleId)) {
      isOk = false;
      btxDetails.addActionError(new BtxActionError(
          "iltds.error.request.fulfillRequest.notFoundSampleId", boxId, sampleId));
    }

    return (isOk ? sampleId : null);
  }

  /**
   * Return true if a sample with the specified id exists in the database.
   * 
   * @param id The sample id.
   * @return True if a sample with the specified id exists in the database.
   */
  private static boolean sampleExists(String id) {
    if (ApiFunctions.isEmpty(id)) {
      return false;
    }

    boolean exists = false;

    String query = "SELECT 1 FROM iltds_sample s WHERE s.sample_barcode_id = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        exists = true;
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return exists;
  }

  /*
   *  
   */
  private List getItemIds(BtxDetailsRequestOperation btxDetails) {
    // Get the input request dto.
    RequestDto inputRequest = btxDetails.getInputRequestDto();

    // Get the actual request dto with items.
    RequestDto request = RequestFinder.find(btxDetails.getLoggedInUserSecurityInfo(),
        RequestSelect.BASIC_PLUS_ITEM_BASICS, inputRequest.getId());

    // Create an id list to compare against.
    ArrayList itemIds = new ArrayList();
    Iterator items = request.getItems().iterator();
    while (items.hasNext()) {
      RequestItemDto rid = (RequestItemDto) items.next();
      itemIds.add(rid.getItemId());
    }
    return itemIds;
  }

  /**
   * Returns the "user_email_address" for the specified user id.
   * 
   * @param userId the user id
   * @return The user email address.
   */
  private String getUserEmailForUser(String userId) {
    String query = "SELECT u.user_email_address" + " FROM es_ardais_user u"
        + " WHERE u.ardais_user_id = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String userEmailAddress = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, userId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        userEmailAddress = rs.getString("user_email_address");
      }
    }
    catch (SQLException e) {
      ApiLogger.log(e);
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return userEmailAddress;
  }

  /**
   * Invoke the specified method on this class. This is only meant to be called from
   * BtxTransactionPerformerBase, please don't call it from anywhere else as a mechanism to gain
   * access to private methods in this class. Every object that the BTX framework dispatches to must
   * contain this method definition, and its implementation should be exactly the same in each
   * class. Please don't alter this method or its implementation in any way.
   */
  protected BTXDetails invokeBtxEntryPoint(java.lang.reflect.Method method, BTXDetails btxDetails)
      throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }

  /*
   * Private method to make sure all items on a request are valid
   */
  private void validateItemList(BTXDetails btxDetails, List requestItems,
                                boolean allowBMSNonBMSMix, boolean itemsCanBeOnPendingRequest,
                                boolean itemsMustBeLocalToUser) {

    ValidateIds validId = new ValidateIds();

    if (ApiFunctions.isEmpty(requestItems)) {
      btxDetails.addActionError(new BtxActionError("iltds.error.rs.noitemsselected", "request"));
    }
    else {
      //make a copy of the item list, so we can remove bogus items without affecting the original
      //item list
      List localList = new ArrayList(requestItems);
      List existingItems = new ArrayList();
      //remove any obviously bogus items
      Iterator itemIterator = localList.iterator();
      while (itemIterator.hasNext()) {
        RequestItemDto item = (RequestItemDto) itemIterator.next();
        //if the RequestItemDto is null, remove it from the list
        if (item == null) {
          btxDetails
              .addActionError(new BtxActionError("iltds.error.request.createRequest.nullItem"));
          itemIterator.remove();
        }
        else {
          String itemId = item.getItemId();
          //if the itemId is null, the empty string, or just whitespace, remove it from the list
          if (ApiFunctions.isEmpty(itemId) || ApiFunctions.isEmpty(itemId.trim())) {
            btxDetails.addActionError(new BtxActionError(
                "iltds.error.request.createRequest.itemWithNoId"));
            itemIterator.remove();
          }
          else {
            //if we cannot determine the item type, remove it from the list
            if (ApiFunctions.isEmpty(validId.validate(itemId, ValidateIds.TYPESET_SAMPLE, false))
                && !IdValidator.validRnaId(itemId)) {
              btxDetails.addActionError(new BtxActionError(
                  "iltds.error.request.createRequest.itemWithUnknownType", itemId));
              itemIterator.remove();
            }
            else {
              //if this item has already been located, remove it from the list (it's a duplicate)
              if (existingItems.contains(itemId)) {
                btxDetails.addActionError(new BtxActionError(
                    "iltds.error.request.createRequest.duplicateItem", itemId));
                itemIterator.remove();
              }
              //we've passed all checks, so add this to the list of existing items so we can check
              //for duplicates
              else {
                existingItems.add(itemId);
              }
            }
          }
        }
      }
      //at this point, the items left in the item list are not ones that are obviously bogus,
      //but we need to do additional validation on them (if any exist)
      if (localList.size() > 0) {
        //divide the items into samples and rna, so we can verify they exist
        ArrayList sampleIds = new ArrayList();
        ArrayList rnaIds = new ArrayList();
        itemIterator = localList.iterator();
        while (itemIterator.hasNext()) {
          RequestItemDto item = (RequestItemDto) itemIterator.next();
          String itemId = item.getItemId();
          if (!ApiFunctions.isEmpty(validId.validate(itemId, ValidateIds.TYPESET_SAMPLE, false))) {
            sampleIds.add(itemId);
          }
          else if (IdValidator.validRnaId(itemId)) {
            rnaIds.add(itemId);
          }
        }
        //for each item id list, return a btxActionError for any ids that cannot be found.
        verifyItemsExist(btxDetails, sampleIds, DbConstants.TABLE_SAMPLE, DbConstants.SAMPLE_ID,
            ProductType.SAMPLE.toString().toLowerCase());
        verifyItemsExist(btxDetails, rnaIds, DbConstants.TABLE_RNA_BATCH_DETAIL,
            DbConstants.RNA_RNAVIALID, ProductType.RNA.toString().toLowerCase());

        //if the requested items cannot be on a pending request, verify items are not on a
        //pending request
        if (!itemsCanBeOnPendingRequest) {
          verifyItemsAreNotOnPendingRequest(btxDetails, localList);
        }

        //if a mix of BMS and non-bms samples are not allowed, verify no mix exists
        if (!allowBMSNonBMSMix) {
          ArrayList bmsSamples = IltdsUtils.getBMSSamplesFromList(IltdsUtils.BMS, sampleIds);
          ArrayList nonBmsSamples = IltdsUtils.getBMSSamplesFromList(IltdsUtils.NONBMS, sampleIds);
          if (bmsSamples.size() > 0 && nonBmsSamples.size() > 0) {
            btxDetails.addActionError(new BtxActionError("iltds.error.rs.cannotmixBMSwithnonBMS",
                "request"));
          }
        }

        //if items must be local to the user, make sure they are local
        if (itemsMustBeLocalToUser) {
          try {
            SecurityInfo secInfo = btxDetails.getLoggedInUserSecurityInfo();
            SampleOperationHome soHome = (SampleOperationHome) EjbHomes.getHome(SampleOperationHome.class);
            SampleOperation sampleBean = soHome.create();
            List nonLocalSamples = sampleBean.identifyNonLocalSamples(sampleIds, secInfo, false, true);
            if (!nonLocalSamples.isEmpty()) {
              btxDetails.addActionError(new BtxActionError(
                  "iltds.error.request.createRequest.nonLocalItems", StringUtils.join(
                      nonLocalSamples.iterator(), ", ")));
            }
          }
          catch (Exception e) {
            ApiFunctions.throwAsRuntimeException(e);
          }
        }
      }
    }
  }

  /*
   * Private method to make sure all items on a list exist in the database
   */
  private void verifyItemsExist(BTXDetails btxDetails, List itemIds, String tableName,
                                String columnName, String itemType) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    //copy the itemIds into a new list that we can modify (code below makes
    //modifications to the list)
    List copyOfItemIds = new ArrayList(itemIds);
    //make sure all specified ids are legit
    if (copyOfItemIds.size() > 0) {
      StringBuffer sql = new StringBuffer(100);
      sql.append("Select ");
      sql.append(columnName);
      sql.append(" from ");
      sql.append(tableName);
      sql.append(" where ");
      sql.append(columnName);
      sql.append(" in (");
      boolean includeComma = false;
      for (int i = 0; i < copyOfItemIds.size(); i++) {
        if (includeComma) {
          sql.append(", ");
        }
        includeComma = true;
        sql.append("?");
      }
      sql.append(")");

      try {
        // Prepare and execute the query.
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(sql.toString());
        for (int i = 0; i < copyOfItemIds.size(); i++) {
          pstmt.setString(i + 1, (String) copyOfItemIds.get(i));
        }
        rs = ApiFunctions.queryDb(pstmt, con);
        while (rs.next()) {
          String val = rs.getString(1);
          copyOfItemIds.remove(val);
        }
      }
      catch (Exception e) {
        ApiLogger.log(e);
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }

      //if there are any ids left in the copyOfItemIds list, they are invalid
      if (copyOfItemIds.size() > 0) {
        StringBuffer invalidIds = new StringBuffer(50);
        includeComma = false;
        for (int i = 0; i < copyOfItemIds.size(); i++) {
          if (includeComma) {
            invalidIds.append(", ");
          }
          includeComma = true;
          invalidIds.append(copyOfItemIds.get(i));
        }
        btxDetails.addActionError(new BtxActionError(
            "iltds.error.request.createRequest.invalidItems", itemType, invalidIds.toString()));
      }
    }
  }

  /*
   * Private method to make sure no items on a list are on a pending request
   */
  private void verifyItemsAreNotOnPendingRequest(BTXDetails btxDetails, List requestItems) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    //make sure none of the items are already on a request
    if (requestItems.size() > 0) {
      StringBuffer sql = new StringBuffer(100);
      sql.append("Select ");
      sql.append(DbAliases.TABLE_ILTDS_REQUEST_ITEM);
      sql.append(".");
      sql.append(DbConstants.REQUEST_ITEM_ITEM_ID);
      sql.append(" from ");
      sql.append(DbConstants.TABLE_ILTDS_REQUEST_ITEM);
      sql.append(" ");
      sql.append(DbAliases.TABLE_ILTDS_REQUEST_ITEM);
      sql.append(",");
      sql.append(DbConstants.TABLE_ILTDS_REQUEST);
      sql.append(" ");
      sql.append(DbAliases.TABLE_ILTDS_REQUEST);
      sql.append(" where ");
      sql.append(DbAliases.TABLE_ILTDS_REQUEST);
      sql.append(".");
      sql.append(DbConstants.REQUEST_REQUEST_ID);
      sql.append(" = ");
      sql.append(DbAliases.TABLE_ILTDS_REQUEST_ITEM);
      sql.append(".");
      sql.append(DbConstants.REQUEST_ITEM_REQUEST_ID);
      sql.append(" and ");
      sql.append(DbAliases.TABLE_ILTDS_REQUEST);
      sql.append(".");
      sql.append(DbConstants.REQUEST_STATE);
      sql.append(" = '");
      sql.append(RequestState.PENDING.toString());
      sql.append("' and ");
      sql.append(DbAliases.TABLE_ILTDS_REQUEST_ITEM);
      sql.append(".");
      sql.append(DbConstants.REQUEST_ITEM_ITEM_ID);
      sql.append(" in (");
      boolean includeComma = false;
      for (int i = 0; i < requestItems.size(); i++) {
        if (includeComma) {
          sql.append(", ");
        }
        includeComma = true;
        sql.append("?");
      }
      sql.append(")");

      List itemsOnPendingRequests = new ArrayList();
      try {
        // Prepare and execute the query.
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(sql.toString());
        for (int i = 0; i < requestItems.size(); i++) {
          pstmt.setString(i + 1, ((RequestItemDto) requestItems.get(i)).getItemId());
        }
        rs = ApiFunctions.queryDb(pstmt, con);
        while (rs.next()) {
          String val = rs.getString(1);
          itemsOnPendingRequests.add(val);
        }
      }
      catch (Exception e) {
        ApiLogger.log(e);
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }

      //if there are any ids in the itemsOnPendingRequests list, they are on a pending request
      if (itemsOnPendingRequests.size() > 0) {
        StringBuffer invalidIds = new StringBuffer(50);
        includeComma = false;
        for (int i = 0; i < itemsOnPendingRequests.size(); i++) {
          if (includeComma) {
            invalidIds.append(", ");
          }
          includeComma = true;
          invalidIds.append(itemsOnPendingRequests.get(i));
        }
        btxDetails.addActionError(new BtxActionError(
            "iltds.error.request.createRequest.itemsOnPendingRequest", invalidIds.toString()));
      }
    }
  }

  /*
   * Private method to make sure a destination id is valid
   */
  private void validateDestinationInfo(BtxDetailsCreateTransferRequest btxDetails) {
    String destinationId = btxDetails.getInputRequestDto().getDestinationId();
    //return an error if no destination was chosen
    if (ApiFunctions.isEmpty(destinationId)) {
      btxDetails.addActionError(new BtxActionError("errors.required", "Destination"));
    }
    else {
      //make sure the destination has been authorized for the user
      List validDestinations = IltdsUtils.getAssignedShippingPartnersByAccount(btxDetails
          .getLoggedInUserSecurityInfo().getAccount());
      Iterator iterator = validDestinations.iterator();
      boolean isValid = false;
      while (iterator.hasNext() && !isValid) {
        ShippingPartnerDto partner = (ShippingPartnerDto) iterator.next();
        if (destinationId.equalsIgnoreCase(partner.getShippingPartnerId())) {
          isValid = true;
        }
      }
      if (!isValid) {
        btxDetails.addActionError(new BtxActionError(
            "iltds.error.request.createRequest.invalidDestination", destinationId));
      }
      //if the destination is "Out of network", make sure the user has specified shipping address
      // info
      if (IltdsUtils.OUT_OF_NETWORK_LOCATION.equalsIgnoreCase(destinationId)) {
        Address address = btxDetails.getInputRequestDto().getShippingAddress();
        //trim the shipping address fields to remove extraneous spaces
        address.setFirstName(ApiFunctions.safeTrim(address.getFirstName()));
        address.setMiddleName(ApiFunctions.safeTrim(address.getMiddleName()));
        address.setLastName(ApiFunctions.safeTrim(address.getLastName()));
        address.setLocationAddress1(ApiFunctions.safeTrim(address.getLocationAddress1()));
        address.setLocationAddress2(ApiFunctions.safeTrim(address.getLocationAddress2()));
        address.setLocationCity(ApiFunctions.safeTrim(address.getLocationCity()));
        address.setLocationState(ApiFunctions.safeTrim(address.getLocationState()));
        address.setLocationZip(ApiFunctions.safeTrim(address.getLocationZip()));
        address.setCountry(ApiFunctions.safeTrim(address.getCountry()));
        //now validate the shipping information
        if (ApiFunctions.isEmpty(address.getFirstName())) {
          btxDetails.addActionError(new BtxActionError("errors.required",
              "Shipping Address First Name"));
        }
        else {
          if (address.getFirstName().length() > MAX_ADDRESS_FIRST_NAME) {
            btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
                "Shipping Address First Name", MAX_ADDRESS_FIRST_NAME + " characters"));
          }
        }
        if (!ApiFunctions.isEmpty(address.getMiddleName())) {
          if (address.getMiddleName().length() > MAX_ADDRESS_MIDDLE_NAME) {
            btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
                "Shipping Address Middle Name", MAX_ADDRESS_MIDDLE_NAME + " characters"));
          }
        }
        if (ApiFunctions.isEmpty(address.getLastName())) {
          btxDetails.addActionError(new BtxActionError("errors.required",
              "Shipping Address Last Name"));
        }
        else {
          if (address.getLastName().length() > MAX_ADDRESS_LAST_NAME) {
            btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
                "Shipping Address Last Name", MAX_ADDRESS_LAST_NAME + " characters"));
          }
        }
        if (ApiFunctions.isEmpty(address.getLocationAddress1())) {
          btxDetails.addActionError(new BtxActionError("errors.required",
              "Shipping Address Address 1"));
        }
        else {
          if (address.getLocationAddress1().length() > MAX_ADDRESS_ADDRESS1) {
            btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
                "Shipping Address Address 1", MAX_ADDRESS_ADDRESS1 + " characters"));
          }
        }
        if (!ApiFunctions.isEmpty(address.getLocationAddress2())) {
          if (address.getLocationAddress2().length() > MAX_ADDRESS_ADDRESS2) {
            btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
                "Shipping Address Address 2", MAX_ADDRESS_ADDRESS2 + " characters"));
          }
        }
        if (ApiFunctions.isEmpty(address.getLocationCity())) {
          btxDetails.addActionError(new BtxActionError("errors.required", "Shipping Address City"));
        }
        else {
          if (address.getLocationCity().length() > MAX_ADDRESS_CITY) {
            btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
                "Shipping Address City", MAX_ADDRESS_CITY + " characters"));
          }
        }
        if (ApiFunctions.isEmpty(address.getLocationState())) {
          btxDetails
              .addActionError(new BtxActionError("errors.required", "Shipping Address State"));
        }
        else {
          if (address.getLocationState().length() > MAX_ADDRESS_STATE) {
            btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
                "Shipping Address State", MAX_ADDRESS_STATE + " characters"));
          }
        }
        if (ApiFunctions.isEmpty(address.getLocationZip())) {
          btxDetails.addActionError(new BtxActionError("errors.required",
              "Shipping Address Zip Code"));
        }
        else {
          if (address.getLocationZip().length() > MAX_ADDRESS_ZIP) {
            btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
                "Shipping Address Zip Code", MAX_ADDRESS_ZIP + " characters"));
          }
        }
        if (ApiFunctions.isEmpty(address.getCountry())) {
          btxDetails.addActionError(new BtxActionError("errors.required",
              "Shipping Address Country"));
        }
        else {
          if (address.getCountry().length() > MAX_ADDRESS_COUNTRY) {
            btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
                "Shipping Address Country", MAX_ADDRESS_COUNTRY + " characters"));
          }
        }
      }
      //otherwise make sure the chosen destination has a ship to address specified
      else {
        String shipToAddressId = IltdsUtils.getShipToAddressIdForGeolocation(destinationId);
        if (ApiFunctions.isEmpty(shipToAddressId)) {
          btxDetails.addActionError(new BtxActionError(
              "iltds.error.request.createRequest.noShipToAddressForDestination"));
        }
      }
    }
  }

  /*
   * Private method to make sure filters on a request filter are valid. Currently only validates
   * date information, as other filters don't require validation
   */
  private void validateRequestFilter(BTXDetails btxDetails, RequestFilter filter) {
    if (filter == null) {
      return;
    }
    Date minDate = filter.getMinRequestCreateDate();
    Date maxDate = filter.getMaxRequestCreateDate();
    //make sure the min date (if specified) is no later than the current date
    if (minDate != null) {
      if (minDate.after(new Date())) {
        btxDetails.addActionError(new BtxActionError("error.dateMustBeBeforeCurrentDate",
            "Submission Date Start Date"));
      }
      if (maxDate != null && minDate.after(maxDate)) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        btxDetails.addActionError(new BtxActionError("error.invalidDateRange", "Submission Date",
            sdf.format(minDate), sdf.format(maxDate)));
      }
    }
  }

  /*
   * Private method to make sure a destination id is valid
   */
  private String getNameForDestinationId(String destinationId) {
    String returnValue = "";
    if (!ApiFunctions.isEmpty(destinationId)) {
      //get the list of valid destinations
      try {
        ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
        ListGenerator list = home.create();
        LegalValueSet destinations = list.getDonorLocations();
        String name = destinations.getDisplayValue(destinationId);
        if (!ApiFunctions.isEmpty(name)) {
          returnValue = name;
        }
      }
      catch (Exception e) {
        ApiLogger.log(e);
        ApiFunctions.throwAsRuntimeException(e);
      }
    }
    return returnValue;
  }

  //private method used to get an id for a new request
  private String getIdForNewRequest() throws Exception {
    SequenceGenAccessBean seq = new SequenceGenAccessBean();
    int nextVal = seq.getSeqNextVal("ILTDS_REQUEST_SEQ");
    int numZeroesNeeded = ValidateIds.LENGTH_REQUEST_ID
        - (ValidateIds.PREFIX_REQUEST + nextVal).length();
    StringBuffer buff = new StringBuffer(ValidateIds.LENGTH_REQUEST_ID);
    buff.append(ValidateIds.PREFIX_REQUEST);
    for (int i = 0; i < numZeroesNeeded; i++) {
      buff.append("0");
    }
    buff.append(nextVal);
    String evalId = buff.toString();
    return evalId;
  }

  //private method used to retrieve the id for an out-of-network address
  //either retrieves the id of an existing address or creates a new address
  //and returns it's id.
  private Integer getOrCreateIdForOutOfNetworkAddress(BtxDetailsCreateRequest btxDetails)
      throws Exception {
    Integer addressId = null;
    Address address = btxDetails.getInputRequestDto().getShippingAddress();
    StringBuffer query = new StringBuffer(100);
    query.append("SELECT address_id FROM ardais_address a");
    query.append(" WHERE a.ardais_acct_key = ?");
    query.append(" AND a.address_type = ?");
    query.append(" AND upper(a.first_name) = ?");
    query.append(" AND upper(a.last_name) = ?");
    query.append(" AND upper(a.address_1) = ?");
    query.append(" AND upper(a.addr_city) = ?");
    query.append(" AND upper(a.addr_state) = ?");
    query.append(" AND upper(a.addr_zip_code) = ?");
    query.append(" AND upper(a.addr_country) = ?");
    if (!ApiFunctions.isEmpty(address.getMiddleName())) {
      query.append(" AND upper(a.middle_name) = ?");
    }
    else {
      query.append(" AND a.middle_name is null");
    }
    if (!ApiFunctions.isEmpty(address.getLocationAddress2())) {
      query.append(" AND upper(a.address_2) = ?");
    }
    else {
      query.append(" AND a.address_2 is null");
    }

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, btxDetails.getLoggedInUserSecurityInfo().getAccount());
      pstmt.setString(2, com.ardais.bigr.es.helpers.FormLogic.ADDRESS_TYPE_SYSTEM_GENERATED);
      pstmt.setString(3, address.getFirstName().toUpperCase());
      pstmt.setString(4, address.getLastName().toUpperCase());
      pstmt.setString(5, address.getLocationAddress1().toUpperCase());
      pstmt.setString(6, address.getLocationCity().toUpperCase());
      pstmt.setString(7, address.getLocationState().toUpperCase());
      pstmt.setString(8, address.getLocationZip().toUpperCase());
      pstmt.setString(9, address.getCountry().toUpperCase());
      int next = 10;
      if (!ApiFunctions.isEmpty(address.getMiddleName())) {
        pstmt.setString(next, address.getMiddleName().toUpperCase());
        next = next + 1;
      }
      if (!ApiFunctions.isEmpty(address.getLocationAddress2())) {
        pstmt.setString(next, address.getLocationAddress2().toUpperCase());
      }
      rs = pstmt.executeQuery();
      //if there is an existing address, return the id
      if (rs.next()) {
        addressId = new Integer(rs.getInt("address_id"));
      }
      //otherwise, create a new row and return the id
      else {
        Vector addressVec = new Vector();
        Hashtable hash = new Hashtable();
        hash.put("strLast", address.getLastName());
        hash.put("strFirst", address.getFirstName());
        if (!ApiFunctions.isEmpty(address.getMiddleName())) {
          hash.put("strMid", address.getMiddleName());
        }
        hash.put("strAdd1", address.getLocationAddress1());
        if (!ApiFunctions.isEmpty(address.getLocationAddress2())) {
          hash.put("strAdd2", address.getLocationAddress2());
        }
        hash.put("strCity", address.getLocationCity());
        hash.put("strState", address.getLocationState());
        hash.put("strZip", address.getLocationZip());
        hash.put("strCount", address.getCountry());
        hash.put("strShip", com.ardais.bigr.es.helpers.FormLogic.ADDRESS_TYPE_SYSTEM_GENERATED);
        hash.put("strBill", ApiFunctions.EMPTY_STRING);
        hash.put("strContact", ApiFunctions.EMPTY_STRING);
        addressVec.add(hash);

        // getNewAddress has side effects, despite its misleading name!
        String account = btxDetails.getLoggedInUserSecurityInfo().getAccount();
        AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
        AccountSrchSB remote = home.create();
        addressId = new Integer(remote.getNewAddress(account, addressVec));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return addressId;
  }

  //private method to sort the items on a request
  private void sortRequestItems(BtxDetailsFindRequestDetails btxDetails) {
    //if there are no items in the request just return
    if (btxDetails.getOutputRequestDto().getItemCount() <= 0) {
      return;
    }
    String sort = btxDetails.getItemSort();
    Comparator comparator = null;
    if (FormLogic.ITEM_SORT_ORDER_ADDED.equalsIgnoreCase(sort)) {
      // Nothing to do in this case, we assume that the list we're sorting below is already in
      // this order.
    }
    else if (FormLogic.ITEM_SORT_CASE_ID.equalsIgnoreCase(sort)) {
      // Create a comparator that will sort a list of ProductDto objects
      // by case Id (and within that, sample Id since many of the samples
      // might have the same value for case Id).
      //
      comparator = new Comparator() {
        public int compare(Object x, Object y) {
          SampleData sample1 = ((RequestItemDto) x).getItem().getSampleData();
          SampleData sample2 = ((RequestItemDto) y).getItem().getSampleData();
          String sample1Value = sample1.getAsmData().getConsentData().getConsentId()
              + sample1.getSampleId();
          String sample2Value = sample2.getAsmData().getConsentData().getConsentId()
              + sample2.getSampleId();
          return sample1Value.compareToIgnoreCase(sample2Value);
        }
      };
    }
    else if (FormLogic.ITEM_SORT_SAMPLE_ID.equalsIgnoreCase(sort)) {
      // Create a comparator that will sort a list of ProductDto objects
      // by sample Id.
      comparator = new Comparator() {
        public int compare(Object x, Object y) {
          SampleData sample1 = ((RequestItemDto) x).getItem().getSampleData();
          SampleData sample2 = ((RequestItemDto) y).getItem().getSampleData();
          String sample1Value = sample1.getSampleId();
          String sample2Value = sample2.getSampleId();
          return sample1Value.compareToIgnoreCase(sample2Value);
        }
      };
    }
    else if (FormLogic.ITEM_SORT_LOCATION.equalsIgnoreCase(sort)) {
      // Sort per requirements in MR 7623.

      // Create a comparator that will sort a list of ProductDto objects
      // by location (and within that, sample Id since many of the samples
      // might have the same value for location).
      comparator = new Comparator() {
        public int compare(Object x, Object y) {
          SampleData sample1 = ((RequestItemDto) x).getItem().getSampleData();
          SampleData sample2 = ((RequestItemDto) y).getItem().getSampleData();
          BTXBoxLocation box1 = sample1.getStorageLocation();
          if (box1 == null) {
            box1 = new BTXBoxLocation();
            box1.setRoomID("");
            box1.setUnitName("");
            box1.setDrawerID("");
            box1.setSlotID("");
          }
          BTXBoxLocation box2 = sample2.getStorageLocation();
          if (box2 == null) {
            box2 = new BTXBoxLocation();
            box2.setRoomID("");
            box2.setUnitName("");
            box2.setDrawerID("");
            box2.setSlotID("");
          }

          String value1 = box1.getRoomID();
          String value2 = box2.getRoomID();

          // Compare rooms
          int result = value1.compareToIgnoreCase(value2);

          if (result != 0) return result;

          // If rooms are the same, compare storage unit names.

          value1 = box1.getUnitName();
          value2 = box2.getUnitName();

          result = value1.compareToIgnoreCase(value2);

          if (result != 0) return result;

          // If room/unit names are the same, compare drawers. Drawer ids are actually numbers,
          // not strings, so we need to compare them as numbers when the strings have different
          // lengths to avoid problems with alphabetic comparisons like "9" coming after "23".

          value1 = box1.getDrawerID();
          value2 = box2.getDrawerID();

          if (value1.length() == value2.length()) {
            // It is safe to compare as strings in this case. We can do a case-sensitive
            // compare since we know that the characters are all digits.

            result = value1.compareTo(value2);
          }
          else {
            // The lengths are different, we have to take special care to make sure the comparison
            // we do comes out in the correct numerical order.

            result = Integer.parseInt(value1) - Integer.parseInt(value2);
          }

          if (result != 0) return result;

          // If room/unit/drawer are the same, compare slots.

          value1 = box1.getSlotID();
          value2 = box2.getSlotID();

          result = value1.compareToIgnoreCase(value2);

          if (result != 0) return result;

          // If room/unit/drawer/slot are the same, then the samples must actually be in the
          // same box and we compare their box cell_ref_location values. Sorting by
          // cell_ref_location values work for Tab Right only. It does not work for Tab Down.
          // We need to use the order cell_ref_location values by tab order for sorting.

          int intValue1 = 0;
          int intValue2 = 0;
          intValue1 = BoxLayoutUtils.getCellRefByTabOrber(sample1.getBoxBarcodeId()).indexOf(
              sample1.getLocation());
          intValue2 = BoxLayoutUtils.getCellRefByTabOrber(sample2.getBoxBarcodeId()).indexOf(
              sample2.getLocation());
          result = intValue1 - intValue2;

          return result;
        }
      };
    }
    else {
      // Got an invalid (unrecognized) sort specification.

      throw new IllegalArgumentException("Unrecognized picklist sort order: " + sort);
    }

    //if we have a comparator, sort the items on the request
    if (comparator != null) {
      Collections.sort(btxDetails.getOutputRequestDto().getItems(), comparator);
    }
  }

  //private method used to get the list of users in this users account who have
  //submitted one or more requests
  private LegalValueSet getRequesters(String account) throws Exception {
    LegalValueSet requesters = new LegalValueSet();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer(100);
    sql.append("Select ");
    sql.append(DbAliases.TABLE_ARDAIS_USER);
    sql.append(".");
    sql.append(DbConstants.ARDAIS_USER_USER_FIRSTNAME);
    sql.append(", ");
    sql.append(DbAliases.TABLE_ARDAIS_USER);
    sql.append(".");
    sql.append(DbConstants.ARDAIS_USER_USER_LASTNAME);
    sql.append(", ");
    sql.append(DbAliases.TABLE_ARDAIS_USER);
    sql.append(".");
    sql.append(DbConstants.ARDAIS_USER_ID);
    sql.append(" from ");
    sql.append(DbConstants.TABLE_ARDAIS_USER);
    sql.append(" ");
    sql.append(DbAliases.TABLE_ARDAIS_USER);
    sql.append(" where ");
    sql.append(DbAliases.TABLE_ARDAIS_USER);
    sql.append(".");
    sql.append(DbConstants.USER_ACCT_KEY);
    sql.append(" = ? and ");
    sql.append(DbAliases.TABLE_ARDAIS_USER);
    sql.append(".");
    sql.append(DbConstants.ARDAIS_USER_ID);
    sql.append(" in (select distinct ");
    sql.append(DbAliases.TABLE_ILTDS_REQUEST);
    sql.append(".");
    sql.append(DbConstants.REQUEST_REQUESTER_USER_ID);
    sql.append(" from ");
    sql.append(DbConstants.TABLE_ILTDS_REQUEST);
    sql.append(" ");
    sql.append(DbAliases.TABLE_ILTDS_REQUEST);
    sql.append(") order by ");
    sql.append(DbAliases.TABLE_ARDAIS_USER);
    sql.append(".");
    sql.append(DbConstants.ARDAIS_USER_USER_LASTNAME);

    try {
      // Prepare and execute the query.
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, account);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        String firstname = rs.getString(DbConstants.ARDAIS_USER_USER_FIRSTNAME);
        String lastname = rs.getString(DbConstants.ARDAIS_USER_USER_LASTNAME);
        String userId = rs.getString(DbConstants.ARDAIS_USER_ID);
        requesters.addLegalValue(userId, firstname + " " + lastname);
      }
    }
    catch (Exception e) {
      ApiLogger.log(e);
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return requesters;
  }

  private void findRequests(SecurityInfo securityInfo, BtxDetailsFindRequests btxDetails,
                            RequestFilter filters) {
    //get the requests that match the filters and seperate them into open/closed
    List requestDtos = RequestFinder.find(securityInfo, RequestSelect.BASIC_PLUS_BOX_BASICS,
        filters);
    List openRequests = new ArrayList();
    List closedRequests = new ArrayList();
    if (!ApiFunctions.isEmpty(requestDtos)) {
      Iterator iterator = requestDtos.iterator();
      while (iterator.hasNext()) {
        RequestDto request = (RequestDto) iterator.next();
        if (request.isClosed()) {
          closedRequests.add(request);
        }
        else {
          openRequests.add(request);
        }
      }
    }
    //set the open requests
    btxDetails.setOpenRequestDtos(openRequests);
    //set the closed requests
    btxDetails.setClosedRequestDtos(closedRequests);
  }

  //private method used to get additional information for items on a request to be used
  //in the generation of a picklist for that request
  private void getPickListInfo(BtxDetailsFindRequestDetails btxDetails) throws Exception {
    Map itemLocations = new HashMap();
    Map mostRecentSlides = new HashMap();
    List itemIds = new ArrayList();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    //get the list of sample ids
    Iterator iterator = btxDetails.getOutputRequestDto().getItems().iterator();
    while (iterator.hasNext()) {
      itemIds.add(((RequestItemDto) iterator.next()).getItem().getId());
    }

    try {
      // Prepare and execute the item location query.
      con = ApiFunctions.getDbConnection();
      StringBuffer itemLocationSql = new StringBuffer(100);
      itemLocationSql.append("SELECT B.SAMPLE_BARCODE_ID, A.ROOM_ID, A.UNIT_NAME,");
      itemLocationSql.append("\n A.DRAWER_ID, A.SLOT_ID");
      itemLocationSql.append("\n FROM ILTDS_BOX_LOCATION A, ILTDS_SAMPLE B");
      itemLocationSql.append("\n WHERE A.BOX_BARCODE_ID = B.BOX_BARCODE_ID");
      itemLocationSql.append("\n AND ");
      itemLocationSql.append(ApiFunctions.makeBindConditionStringOrList("B.SAMPLE_BARCODE_ID",
          itemIds.size()));
      pstmt = con.prepareStatement(itemLocationSql.toString());
      ApiFunctions.bindBindParameterList(pstmt, 1, itemIds);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        BTXBoxLocation boxLocation = new BTXBoxLocation();
        boxLocation.setRoomID(rs.getString("ROOM_ID"));
        boxLocation.setUnitName(rs.getString("UNIT_NAME"));
        boxLocation.setDrawerID(rs.getString("DRAWER_ID"));
        boxLocation.setSlotID(rs.getString("SLOT_ID"));
        itemLocations.put(rs.getString("SAMPLE_BARCODE_ID"), boxLocation);
      }
      ApiFunctions.close(pstmt);
      pstmt = null;
      ApiFunctions.close(rs);
      rs = null;

      // If this is not a research or transfer request (for which we do not show slide information),
      // prepare and execute the slide query.
      RequestType requestType = btxDetails.getOutputRequestDto().getType();
      boolean getSlideInfo = (requestType != RequestType.RESEARCH && requestType != RequestType.TRANSFER);
      if (getSlideInfo) {
        StringBuffer slideSql = new StringBuffer(100);
        slideSql.append("select sample_barcode_id, slide_id from lims_slide where ");
        slideSql.append(ApiFunctions.makeBindConditionStringOrList("sample_barcode_id", itemIds
            .size()));
        slideSql.append(" order by sample_barcode_id, slide_id desc");
        pstmt = con.prepareStatement(slideSql.toString());
        ApiFunctions.bindBindParameterList(pstmt, 1, itemIds);
        rs = ApiFunctions.queryDb(pstmt, con);
        String currentSampleId = "";
        String firstSlideId = "";
        String secondSlideId = "";
        int slideCount = 0;
        while (rs.next()) {
          String sampleId = rs.getString("sample_barcode_id");
          //we've been getting information for a sample and have just moved on to a new sample,
          //so store the information from the previous sample and get ready to work with this new
          // one
          if (!ApiFunctions.isEmpty(currentSampleId) && !currentSampleId.equalsIgnoreCase(sampleId)) {
            //add the slide(s) to an arraylist
            ArrayList slides = new ArrayList();
            slides.add(firstSlideId);
            if (!ApiFunctions.isEmpty(secondSlideId)) {
              slides.add(secondSlideId);
            }
            //store the value in the map
            mostRecentSlides.put(currentSampleId, slides);
            //initialize for new sample
            firstSlideId = "";
            secondSlideId = "";
            slideCount = 0;
          }
          currentSampleId = sampleId;
          //if we haven't already found 2 slides for this sample, grab the slide info
          if (slideCount < 2) {
            if (ApiFunctions.isEmpty(firstSlideId)) {
              firstSlideId = rs.getString("slide_id");
              slideCount = slideCount + 1;
            }
            else if (ApiFunctions.isEmpty(secondSlideId)) {
              secondSlideId = rs.getString("slide_id");
              slideCount = slideCount + 1;
            }
          }
        }
        //we've finished walking the result set, and now have to store the slide info
        //for the sample we were working on
        //add the slide(s) to an arraylist
        ArrayList slides = new ArrayList();
        slides.add(firstSlideId);
        if (!ApiFunctions.isEmpty(secondSlideId)) {
          slides.add(secondSlideId);
        }
        //store the value in the map
        mostRecentSlides.put(currentSampleId, slides);
      }

      //now that we've got the location and slide information, add it to the items
      iterator = btxDetails.getOutputRequestDto().getItems().iterator();
      while (iterator.hasNext()) {
        RequestItemDto item = (RequestItemDto) iterator.next();
        String itemId = item.getItemId();
        SampleData sampleData = item.getItem().getSampleData();
        if (getSlideInfo) {
          sampleData.setSlides((ArrayList) mostRecentSlides.get(itemId));
        }
        sampleData.setStorageLocation((BTXBoxLocation) itemLocations.get(itemId));
      }

    }
    catch (Exception e) {
      ApiLogger.log(e);
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }
}
