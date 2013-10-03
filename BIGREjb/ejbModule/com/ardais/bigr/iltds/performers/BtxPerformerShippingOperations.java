package com.ardais.bigr.iltds.performers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.iltds.assistants.Address;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.beans.SampleboxAccessBean;
import com.ardais.bigr.iltds.beans.SampleboxKey;
import com.ardais.bigr.iltds.beans.SamplestatusAccessBean;
import com.ardais.bigr.iltds.bizlogic.RequestFinder;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.btx.BtxDetailsConfirmBoxOnManifest;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateManifest;
import com.ardais.bigr.iltds.btx.BtxDetailsPrintManifest;
import com.ardais.bigr.iltds.btx.BtxDetailsReceiptVerification;
import com.ardais.bigr.iltds.btx.BtxDetailsScanAndStore;
import com.ardais.bigr.iltds.btx.BtxDetailsSetBoxLocation;
import com.ardais.bigr.iltds.btx.BtxDetailsShipManifest;
import com.ardais.bigr.iltds.btx.BtxDetailsShipmentReceiptVerification;
import com.ardais.bigr.iltds.btx.BtxDetailsShippingOperation;
import com.ardais.bigr.iltds.btx.BtxDetailsUpdateRequestsAfterShipment;
import com.ardais.bigr.iltds.databeans.DateData;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.RequestFilter;
import com.ardais.bigr.iltds.helpers.RequestSelect;
import com.ardais.bigr.iltds.helpers.RequestState;
import com.ardais.bigr.iltds.helpers.RequestType;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.ManifestDto;
import com.ardais.bigr.javabeans.RequestDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.EjbHomes;
import com.gulfstreambio.gboss.GbossFactory;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

/**
 * Performs shipping-related ILTDS BTX business transactions.
 */
public class BtxPerformerShippingOperations extends BtxTransactionPerformerBase {

  public BtxPerformerShippingOperations() {
    super();
  }

  /**
   * Performs validation for creating a new shipping manifest.  The precise validations
   * done depend on the setting of the btxDetails's <code>forRequest</code> parameter,
   * which indicates whether the manifest is for shipping a transfer request or not.
   * As side effects, this
   * <ul>
   * <li>If <code>forRequest</code> is true and if there are no validation problems, it sets
   *     the btxDetails's ManifestDto's ShipToAddressId to the address id specified on the
   *     request(s) the boxes are on (one of the validation rules is that all of the boxes
   *     are on requests going to the same shipping address).</li>
   * </ul> 
   * 
   * <p>This is a BTX validator method.
   */
  private BTXDetails validatePerformCreateManifest(BtxDetailsCreateManifest btxDetails)
    throws Exception {

    // Validation rules:
    //   - The manifest has to have at least one box on it.
    //   - The boxes must all exist and not already be on a manifest.
    //   - If btxDetails.isForRequest:
    //     - All of the boxes must be in the CHECKEDOUT state.
    //     - All of the boxes must be on an open transfer request that is in the Fulfilled state.
    //     - All such requests across all boxes on the manifest must specify the same ship to
    //       address id.
    //   - If NOT btxDetails.isForRequest:
    //     - The ship-to address must exist and be a valid ship-to address id.

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    boolean isForRequest = btxDetails.isForRequest();

    ManifestDto dto = btxDetails.getManifestDto();
    List boxIds = dto.getBoxIdList();

    if (!isForRequest) {
      checkShipToAddressId(btxDetails);
    }

    if (boxIds == null || boxIds.size() == 0) {
      btxDetails.addActionError(new BtxActionError("iltds.error.shipping.createManifest.noBoxes"));
      return btxDetails;
    }

    Iterator boxIdIterator = boxIds.iterator();
    // This method no longer accepts non-canonical ids as input, to protect against data entry
    // errors, but I left in the code that supported non-canonical ids and just changed the
    // validation calls so that they won't be accepted.
    List canonicalBoxIds = new ArrayList(boxIds.size());
    // The next three variables are used in checking whether all boxes are on transfer requests
    // that are being shipped to the same destination.
    String shippingAddressId = null;
    String shippingAddressName = null;
    String shippingAddressFromBoxId = null;
    while (boxIdIterator.hasNext()) {
      String boxId = (String) boxIdIterator.next();
      // We don't check that the box exists here because we have to query for it later
      // anyways to see whether it is on a manifest or not.
      String validatedBoxId = IltdsUtils.checkBoxId(boxId, btxDetails, false, true, false);

      // canonicalBoxIds needs to end up with the same elements as boxIds and in the
      // same order, except with the valid ids replaced by their canonical form and the
      // invalid ids retained as-is.  SEE ABOVE: we no longer accept non-canonical ids
      // so in reality, boxId will equal validatedBoxId.
      if (validatedBoxId == null) {
        canonicalBoxIds.add(boxId);
        continue;
      }
      else {
        canonicalBoxIds.add(validatedBoxId);
      }

      boxId = validatedBoxId; // set to canonical form

      BoxDto box = getBox(boxId);
      if (box == null) {
        btxDetails.addActionError(new BtxActionError("iltds.error.general.notFoundBoxId", boxId));
        continue;
      }

      if (box.getManifestId() != null) {
        btxDetails.addActionError(
          new BtxActionError(
            "iltds.error.shipping.createManifest.boxOnManifest",
            boxId,
            box.getManifestId()));
      }

      if (isForRequest) {
        if (!FormLogic.BX_CHECKEDOUT.equals(box.getBoxStatus())) {
          btxDetails.addActionError(
            new BtxActionError("iltds.error.shipping.createManifest.boxNotCheckedout", boxId));
        }
        List requestsForBox = findOpenFulfilledTransferRequestsForBox(securityInfo, boxId);
        if (requestsForBox.isEmpty()) {
          btxDetails.addActionError(
            new BtxActionError("iltds.error.shipping.createManifest.boxNotOnRequest", boxId));
        }
        else { // box is on request(s)
          // The next chunk is here to check that all of the requests for all of the
          // boxes have the same shipping destination.  We don't currently expect a box to
          // be able to be on more than one request that matches our "open fulfilled transfer"
          // criteria, but the code it written to accomodate that situation if it happens.
          Iterator requestIter = requestsForBox.iterator();
          while (requestIter.hasNext()) {
            RequestDto request = (RequestDto) requestIter.next();
            //all transfer requests should have a shipping address associated with them
            //so if we find one that doesn't raise an exception
            if (request.getShippingAddress() == null) {
              throw new ApiException("Transfer request " + request.getId() + " has no shipping address");
            }
            String thisShippingAddress = request.getShippingAddress().getAddressID();
            //if this is the first box, store it's shipping info
            if (shippingAddressId == null) {
              shippingAddressId = thisShippingAddress;
              shippingAddressName = request.getDestinationName();
              //if the destination is out of network, we need to provide more info in the
              //shipping address name since two requests might both be out of network but
              //are going to different places
              if (request.getDestinationId().equalsIgnoreCase(IltdsUtils.OUT_OF_NETWORK_LOCATION)) {
                shippingAddressName = shippingAddressName + " (" + request.getShippingAddress().getLocationAddress1() + ")";
              }
              shippingAddressFromBoxId = boxId;
            }
            else if (!shippingAddressId.equals(thisShippingAddress)) {
              String thisShippingAddressName = request.getDestinationName();
              //if the destination is out of network, we need to provide more info in the
              //shipping address name since two requests might both be out of network but
              //are going to different places
              if (request.getDestinationId().equalsIgnoreCase(IltdsUtils.OUT_OF_NETWORK_LOCATION)) {
                thisShippingAddressName = thisShippingAddressName + " (" + request.getShippingAddress().getLocationAddress1() + ")";
              }
              btxDetails.addActionError(
                new BtxActionError(
                  "iltds.error.shipping.createManifest.boxDestinationMismatch",
                  boxId, thisShippingAddressName,
                  shippingAddressFromBoxId, shippingAddressName));
            }
          } // end while (requestIter.hasNext())
        } // end else // box is on request
      } // end if isForRequest
    } // end while (allBoxes.hasNext())

    // Replace the input box ids list with the box ids converted to canonical form.
    // Invalid ids in the list are retained as they were on input.  SEE ABOVE: we no longer
    // accept non-canonical ids so in reality, canonicalBoxIds will be the same as the input
    // box ids.
    //
    dto.setBoxIdList(canonicalBoxIds);

    if (isForRequest && btxDetails.getActionErrors().empty()) {
      // There were no validation problems.  If isForRequest, then we need to set the ship-to
      // address as an output parameter of the transaction.  The performer method expects it
      // to already be set when it is called.
      //
      dto.setShipToAddressId(shippingAddressId);
    }

    return btxDetails;
  }

  /**
   * Creates a new shipping manifest.  The fields that must be defined and valid on the
   * manifestDto when this is called are shipToAddressId and boxIdList.  This performer
   * method may be used by several BTX transactions.  In some situations
   * the validator method determines shipToAddressId, and in other situations that
   * client specifies shipToAddressId.  
   * 
   * <p>This is a BTX performer method.
   */
  private BTXDetails performCreateManifest(BtxDetailsCreateManifest btxDetails) throws Exception {
    ManifestDto manifestDto = btxDetails.getManifestDto();

    // Get the id of the logged on user. 
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String userId = securityInfo.getUsername();

    // Get the from address as the address of the logged on user. 
    String fromAddressId = getShipFromAddressIdForUser(userId);

    // Create the manifest.
    String nextManifestNumber = getNextManifestNumber();
    manifestDto.setManifestNumber(nextManifestNumber);
    manifestDto.setShipmentStatus(FormLogic.MNFT_MFCREATED);
    manifestDto.setShipFromAddressId(fromAddressId);
    manifestDto.setCreateUserId(userId);
    manifestDto.setCreateDate(btxDetails.getBeginTimestamp());
    createManifest(manifestDto);

    // Get the Address object for the ship-to address because that's one of the output
    // parameters we're supposed to set.
    //
    manifestDto.setShipToAddress(getAddressById(manifestDto.getShipToAddressId()));

    // Associate each box with the manifest.
    Iterator boxIdIterator = sortBoxesByLocation(manifestDto.getBoxIdList()).iterator();
    int i = 0;
    while (boxIdIterator.hasNext()) {
      String boxId = (String) boxIdIterator.next();
      // We have to call getBox to get the current values of fields that we aren't
      // updating here.  If we don't do this, updateBox() would set the fields we aren't
      // updating back to their default values.
      BoxDto box = getBox(boxId);
      box.setManifestId(nextManifestNumber);
      box.setManifestOrder(String.valueOf(i));
      updateBox(box);
      i++;
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Performs validation for printing a shipping manifest. 
   * 
   * <p>This is a BTX validator method.
   */
  private BTXDetails validatePerformPrintManifest(BtxDetailsPrintManifest btxDetails)
    throws Exception {

    // Validation rules:
    //   - The manifest number must be present, syntactically valid, and must exist in the db.

    checkManifestNumber(btxDetails, true, true);

    return btxDetails;
  }

  /**
   * Returns all details necessary to print a manifest. 
   * 
   * <p>This is a BTX performer method.
   */
  private BTXDetails performPrintManifest(BtxDetailsPrintManifest btxDetails) throws Exception {
    ManifestDto manifestDto = btxDetails.getManifestDto();

    // Get the manifest.
    ManifestDto returnDto = getManifestByIdWithBoxesAndLocations(manifestDto.getManifestNumber());

    // Get the name of the user that produced/printed the manifest as the name of the logged on
    // user.
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String userId = securityInfo.getUsername();
    String accountId = securityInfo.getAccount();
    ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
    ListGenerator list = home.create();
    returnDto.setProducedByName(list.getUserRealName(userId, accountId));

    // Populate the manifestDto with info on the samples in each box on the manifest.
    List boxes = returnDto.getBoxes();
    for (int i = 0; i < boxes.size(); i++) {
      BoxDto box = (BoxDto) boxes.get(i);
      getSamplesInBox(box);
    }

    btxDetails.setManifestDto(returnDto);

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Performs validation for getting the details of the manifest specified for the
   * Scan And Package Manifest operation.  The corresponding performer is performGetManifest,
   * which is generic enough to be attached to other transactions that may have different
   * validator methods than this one.
   * 
   * <p>This is a BTX validator method.
   */
  private BTXDetails validatePerformGetManifestForScanAndPackage(BtxDetailsShippingOperation btxDetails)
    throws Exception {

    // Validation rules:
    //   - The manifest number must be present, syntactically valid, and must exist in the db.
    //   - The manifest must be in the MFCREATED state.
    //   - The manifest must have been created at the same location as the current user.

    if (!checkManifestNumber(btxDetails, true, true)) {
      return btxDetails;
    }

    ManifestDto manifest =
      IltdsUtils.getManifestById(btxDetails.getManifestDto().getManifestNumber());
    String manifestNumber = manifest.getManifestNumber();
    String shipmentStatus = manifest.getShipmentStatus();

    if (!FormLogic.MNFT_MFCREATED.equals(shipmentStatus)) {
      if (FormLogic.MNFT_MFPACKAGED.equals(shipmentStatus)) {
        btxDetails.addActionError(
          new BtxActionError("iltds.error.shipping.manifestAlreadyPackaged", manifestNumber));
      }
      else if (
        FormLogic.MNFT_MFSHIPPED.equals(shipmentStatus)
          || FormLogic.MNFT_MFRECEIVED.equals(shipmentStatus)
          || FormLogic.MNFT_MFVERIFIED.equals(shipmentStatus)) {
        btxDetails.addActionError(
          new BtxActionError("iltds.error.shipping.manifestAlreadyShipped", manifestNumber));
      }
      else {
        btxDetails.addActionError(
          new BtxActionError("iltds.error.shipping.unpackagedManifestRequired"));
      }
    }

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String userShipFromAddressId = getShipFromAddressIdForUser(securityInfo.getUsername());
    if (!userShipFromAddressId.equals(manifest.getShipFromAddressId())) {
      btxDetails.addActionError(
        new BtxActionError("iltds.error.shipping.manifestAtWrongLocation", manifestNumber));
    }

    return btxDetails;
  }

  /**
   * Get the details of a manifest. 
   * 
   * <p>This is a BTX performer method.
   */
  private BTXDetails performGetManifest(BtxDetailsShippingOperation btxDetails) throws Exception {
    String manifestNumber = btxDetails.getManifestDto().getManifestNumber();
    ManifestDto manifest = getManifestByIdWithBoxesAndLocations(manifestNumber);

    btxDetails.setManifestDto(manifest);

    btxDetails.setActionForwardTXCompleted("success");

    return btxDetails;
  }

  /**
   * Performs validation for determining whether a box is on a manifest.
   * 
   * <p>This is a BTX validator method.
   */
  private BTXDetails validatePerformConfirmBoxOnManifest(BtxDetailsConfirmBoxOnManifest btxDetails)
    throws Exception {

    // Validation rules:
    //   - The manifest number must be present, syntactically valid, and must exist in the db.
    //   - The box id must be present and syntactically valid.
    //   - The box must be on the manifest.

    // Special situation: Make sure that the "confirmed" output parameter that the performer
    // will set to true is initialized to false here.  If there are validation errors the
    // performer won't be called, and we want to ensure that it always gets set accurately.
    //
    btxDetails.setConfirmed(false);

    // This method no longer accepts non-canonical box ids as input, to protect against data entry
    // errors, but I left in the code that supported non-canonical ids and just changed the
    // validation calls so that they won't be accepted.

    boolean manifestOk = checkManifestNumber(btxDetails, true, true);
    String manifestNumber = btxDetails.getManifestDto().getManifestNumber();

    // We don't check that the box exists here because we have to query for it later
    // anyways to see whether it is on the manifest.
    String boxId = IltdsUtils.checkBoxId(btxDetails.getBoxId(), btxDetails, false, true, false);
    boolean boxOk = (boxId != null);

    // Set to the canonical form of the validated box id.
    if (boxOk) {
      btxDetails.setBoxId(boxId);
    }

    if (manifestOk && boxOk) {
      if (!isBoxOnManifest(boxId, manifestNumber)) {
        btxDetails.addActionError(
          new BtxActionError("iltds.error.shipping.boxNotOnManifest", boxId, manifestNumber));
      }
    }

    return btxDetails;
  }

  /**
   * Determine whether a box is on a manifest. 
   * 
   * <p>This is a BTX performer method.
   */
  private BTXDetails performConfirmBoxOnManifest(BtxDetailsConfirmBoxOnManifest btxDetails)
    throws Exception {

    // All the real work was done in the validation method, if we get here the box is
    // on the manifest.

    btxDetails.setConfirmed(true);

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Performs validation for shipping all of the boxes on a manifest. 
   * 
   * <p>This is a BTX validator method.
   */
  private BTXDetails validatePerformShipManifest(BtxDetailsShipManifest btxDetails)
    throws Exception {

    // Validation rules:
    //   - The manifest number must be present, syntactically valid, and must exist in the db.
    //   - The manifest must be in the MFCREATED state.
    //   - The tracking number must be present (non-empty after trimming).

    // The manifest-related checks we need here are the same as the ones
    // that validatePerformGetManifestForScanAndPackage does, so we just call it for that
    // part of the validation.

    validatePerformGetManifestForScanAndPackage(btxDetails);
    checkTrackingNumber(btxDetails, true);

    return btxDetails;
  }

  /**
   * Ship all of the boxes on a manifest. 
   * 
   * <p>This is a BTX performer method.
   */
  private BTXDetails performShipManifest(BtxDetailsShipManifest btxDetails) throws Exception {
    // This is the manifestDto that has the manifest number and tracking number that
    // we're going to ship.
    //
    ManifestDto manifest = btxDetails.getManifestDto();
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    updateShippedManifestRecord(manifest, securityInfo);
    updateShippedManifestBoxes(manifest, securityInfo);
    updateShippedManifestSamples(manifest, securityInfo);
    updateShippedManifestRequests(manifest, securityInfo);

    // One of the output parameters of BtxDetailsShipManifest is populating the
    // manifestDto's getBoxIdList() property.
    //
    populateWithBoxIdList(manifest);

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Update the manifest record in the database to reflect that it has been packaged
   * and shipped.
   * 
   * <p>This is only one aspect of all of the updates that need to happen when a manifest
   * is shipped.  The performShipManifest method (a BTX performer method) does the complete
   * set of updates.
   * 
   * @param manifest The manifest to ship.
   * @param securityInfo The SecurityInfo representing the user performing the transaction.
   */
  private void updateShippedManifestRecord(ManifestDto manifest, SecurityInfo securityInfo) {
    String userId = securityInfo.getUsername();

    String manifestNumber = manifest.getManifestNumber();
    String trackingNumber = manifest.getTrackingNumber();

    // Sanity checks
    //
    if (ApiFunctions.isEmpty(manifestNumber)) {
      throw new ApiException("manifestNumber must not be empty.");
    }
    if (ApiFunctions.isEmpty(trackingNumber)) {
      throw new ApiException("trackingNumber must not be empty.");
    }

    String sql =
      "update iltds_manifest set "
        + "shipment_status = ?, "
        + "airbill_tracking_number = ?, "
        + "ship_datetime = sysdate, "
        + "ship_staff_id = ?, "
        + "ship_verify_datetime = sysdate, "
        + "ship_verify_staff_id = ? "
        + "where manifest_number = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql);

      pstmt.setString(1, FormLogic.MNFT_MFSHIPPED);
      pstmt.setString(2, trackingNumber);
      pstmt.setString(3, userId);
      pstmt.setString(4, userId);
      pstmt.setString(5, manifestNumber);

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
   * Update the box-related database information to reflect that the boxes on a manifest
   * have been packaged and shipped.
   * 
   * <p>This is only one aspect of all of the updates that need to happen when a manifest
   * is shipped.  The performShipManifest method (a BTX performer method) does the complete
   * set of updates.
   * 
   * @param manifest The manifest to ship.
   * @param securityInfo The SecurityInfo representing the user performing the transaction.
   */
  private void updateShippedManifestBoxes(ManifestDto manifest, SecurityInfo securityInfo) {
    String manifestNumber = manifest.getManifestNumber();

    // Sanity checks
    //
    if (ApiFunctions.isEmpty(manifestNumber)) {
      throw new ApiException("manifestNumber must not be empty.");
    }

    String sql =
      "update iltds_box_location l "
        + "set l.box_barcode_id = null, l.available_ind = 'Y' "
        + "\nwhere l.box_barcode_id in "
        + "\n  (select b.box_barcode_id from iltds_sample_box b where b.manifest_number = ?)";

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql);

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
   * Update the sample-related database information to reflect that the samples in the
   * boxes on a manifest have been packaged and shipped.
   * 
   * <p>This is only one aspect of all of the updates that need to happen when a manifest
   * is shipped.  The performShipManifest method (a BTX performer method) does the complete
   * set of updates.
   * 
   * @param manifest The manifest to ship.
   * @param securityInfo The SecurityInfo representing the user performing the transaction.
   */
  private void updateShippedManifestSamples(ManifestDto manifest, SecurityInfo securityInfo) {
    String manifestNumber = manifest.getManifestNumber();

    // Sanity checks
    //
    if (ApiFunctions.isEmpty(manifestNumber)) {
      throw new ApiException("manifestNumber must not be empty.");
    }
    
    //only update the sample status if the ship to address is in network.  Samples on out of network
    //transfer requests should remain checked out, not in transit
    ManifestDto myManifest = IltdsUtils.getManifestById(manifestNumber);
    String addressType = myManifest.getShipToAddress().getAddressType();
    if (com.ardais.bigr.es.helpers.FormLogic.ADDRESS_TYPE_SYSTEM_GENERATED.equalsIgnoreCase(addressType)) {
      return;
    }


    String sql =
      "insert into iltds_sample_status "
        + "(sample_barcode_id, status_type_code, sample_status_datetime) "
        + "\nselect s.sample_barcode_id, '"
        + FormLogic.SMPL_TRANSFER
        + "', sysdate "
        + "\nfrom iltds_sample s, iltds_sample_box b "
        + "\nwhere s.box_barcode_id = b.box_barcode_id "
        + "\n  and b.manifest_number = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql);

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
   * Update the request-related database information to reflect that the boxes on a manifest
   * have been packaged and shipped.
   * 
   * <p>This is only one aspect of all of the updates that need to happen when a manifest
   * is shipped.  The performShipManifest method (a BTX performer method) does the complete
   * set of updates.  This method delegates to the BtxPerformerRequestOperations bean to do
   * the work of updating request related information. 
   * 
   * @param manifest The manifest to ship.
   * @param securityInfo The SecurityInfo representing the user performing the transaction.
   */
  private void updateShippedManifestRequests(ManifestDto manifest, SecurityInfo securityInfo) {
    BtxDetailsUpdateRequestsAfterShipment btxDetails = new BtxDetailsUpdateRequestsAfterShipment();
    btxDetails.setManifestDto(manifest);
    Timestamp now = new Timestamp(System.currentTimeMillis());
    btxDetails.setBeginTimestamp(now);
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails =
      (BtxDetailsUpdateRequestsAfterShipment) Btx.perform(
        btxDetails,
        "iltds_shipping_updateRequestsAfterShipment");
    if (!btxDetails.isTransactionCompleted()) {
      throw new ApiException("Call to iltds_shipping_updateRequestsAfterShipment did not complete successfully.");
    }
  }

  /**
   * Validate the manifest number in the btxDetails's manifestDto.  Return true if the
   * manifest number is valid, otherwise return false and add appropriate ActionErrors
   * to btxDetails.  If the manifest number is syntactically valid, the number in the
   * btxDetails is replaced by the canonical form of the number (for example, "mnft3001"
   * is replaced by "MNFT0000003001").
   * 
   * @param btxDetails The btxDetails object.
   * @param isRequired True if the manifest number is required.
   * @param mustExist True if the manifest number must specify a manifest that exists in the
   *     database.  If isRequired is false and the id is empty, then the existence check is
   *     not performed, so if you want to be certain that the id is the id of an existing
   *     object, you must pass <code>true</code> to both isRequired and mustExist.
   * @return True if the manifest number is valid.
   */
  private boolean checkManifestNumber(
    BtxDetailsShippingOperation btxDetails,
    boolean isRequired,
    boolean mustExist) {

    boolean isOk = true;
    ManifestDto dto = null;
    String id = null;

    if (btxDetails != null) {
      dto = btxDetails.getManifestDto();
      if (dto != null) {
        id = ApiFunctions.safeString(ApiFunctions.safeTrim(dto.getManifestNumber()));
        dto.setManifestNumber(id);
      }
    }

    id = IltdsUtils.checkManifestNumber(id, btxDetails, true, isRequired, mustExist);
    if (ApiFunctions.isEmpty(id)) {
      isOk = false;
    }
    else {
      // Set to the canonical form of the validated manifest number.
      dto.setManifestNumber(id);
    }

    return isOk;
  }

  /**
   * Validate the tracking number in the btxDetails's manifestDto.  Return true if the
   * tracking number is valid, otherwise return false and add appropriate ActionErrors
   * to btxDetails.  If the tracking number is syntactically valid, the number in the
   * btxDetails is replaced by the canonical form of the number (see, for example,
   * the private parseFexEx method in IltdsUtils, which is called by the
   * {@link IltdsUtils#parseTrackingNumber(String)}) method that we call here.
   * 
   * @param btxDetails The btxDetails object.
   * @param isRequired True if the tracking number is required.
   * @return True if the tracking number is valid.
   */
  private boolean checkTrackingNumber(BtxDetailsShippingOperation btxDetails, boolean isRequired) {
    boolean isOk = true;
    ManifestDto dto = null;
    String id = null;

    if (btxDetails != null) {
      dto = btxDetails.getManifestDto();
      if (dto != null) {
        id = ApiFunctions.safeString(ApiFunctions.safeTrim(dto.getTrackingNumber()));
        dto.setTrackingNumber(id);
      }
    }

    id = IltdsUtils.checkTrackingNumber(id, btxDetails, isRequired, false);
    if (ApiFunctions.isEmpty(id)) {
      isOk = false;
    }
    else {
      // Set to the canonical form of the validated tracking number.
      dto.setTrackingNumber(id);
    }

    return isOk;
  }

  /**
   * Validate the ship-to address id in the btxDetails's manifestDto.  Return true if the
   * id is valid, otherwise return false and add appropriate ActionErrors
   * to btxDetails.  The id must be present and must be the id of an address of type "Ship To"
   * in the database (the ARDAIS_ADDRESS table).
   * 
   * @param btxDetails The btxDetails object.
   * @return True if the ship-to address id is valid.
   */
  private boolean checkShipToAddressId(BtxDetailsShippingOperation btxDetails) {
    boolean isOk = true;
    ManifestDto dto = null;
    String id = null;

    if (btxDetails != null) {
      dto = btxDetails.getManifestDto();
      if (dto != null) {
        id = ApiFunctions.safeString(ApiFunctions.safeTrim(dto.getShipToAddressId()));
        dto.setShipToAddressId(id);
      }
    }

    if (ApiFunctions.isEmpty(id)) {
      btxDetails.addActionError(
        new BtxActionError("iltds.error.shipping.createManifest.requiredShipToAddress"));
      return false;
    }

    Address address = getAddressById(id, false);

    if (address == null) {
      isOk = false;
      btxDetails.addActionError(
        new BtxActionError(
          "iltds.error.shipping.createManifest.notFoundShipToAddress",
          Escaper.htmlEscape(id)));
    }
    else if (!"Ship To".equals(address.getAddressType())) {
      isOk = false;
      btxDetails.addActionError(
        new BtxActionError(
          "iltds.error.shipping.createManifest.wrongTypeShipToAddress",
          Escaper.htmlEscape(id)));
    }

    return isOk;
  }

  /**
   * Creates a new manifest record by performing a SQL INSERT.
   * 
   * @param  dto  the ManifestDto containing the data to insert
   */
  private void createManifest(ManifestDto dto) {
    String sql =
      "INSERT INTO iltds_manifest"
        + " (manifest_number,"
        + " ship_to_addr_id,"
        + " ship_from_addr_id,"
        + " shipment_status,"
        + " mnft_create_staff_id,"
        + " mnft_create_datetime)"
        + " VALUES"
        + " (?, ?, ?, ?, ?, ?)";
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, dto.getManifestNumber());
      pstmt.setString(2, dto.getShipToAddressId());
      pstmt.setString(3, dto.getShipFromAddressId());
      pstmt.setString(4, dto.getShipmentStatus());
      pstmt.setString(5, dto.getCreateUserId());
      pstmt.setTimestamp(6, dto.getCreateDate());
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

  private ManifestDto getManifestByIdWithBoxesAndLocations(String manifestNumber) {
    ManifestDto dto = IltdsUtils.getManifestById(manifestNumber);
    populateWithBoxesAndLocations(dto);
    return dto;
  }

  private boolean isBoxOnManifest(String boxId, String manifestNumber) {
    if (ApiFunctions.isEmpty(boxId) || ApiFunctions.isEmpty(manifestNumber)) {
      return false;
    }

    boolean exists = false;

    String query =
      "SELECT 1 FROM iltds_sample_box b "
        + " WHERE b.box_barcode_id = ? "
        + "   AND b.manifest_number = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, boxId);
      pstmt.setString(2, manifestNumber);
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

  /**
   * Returns the next manifest number to use when creating a manifest.
   * 
   * @return  The ship from address id.
   */
  private String getNextManifestNumber() {
    String query = "SELECT seq_manifest_number.nextval FROM dual";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String manifestNumber = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        String number = rs.getString(1);
        while (number.length() < 10) {
          number = "0" + number;
        }
        manifestNumber = ValidateIds.PREFIX_MANIFEST + number;
      }
      else {
        throw new ApiException("Could not determine next manifest number.");
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return manifestNumber;
  }

  /**
   * Return all of the open transfer requests that are in the fulfilled state and that
   * involve the specified box.  The {@link RequestDto} objects returned have only basic
   * information populated.
   * 
   * @param boxId The box id.
   * @return The list of matching requests.
   */
  private List findOpenFulfilledTransferRequestsForBox(SecurityInfo secInfo, String boxId) {
    RequestFilter filter = new RequestFilter();
    filter.setIncludeClosedRequests(false);
    filter.setRequestState(RequestState.FULFILLED);
    filter.setRequestType(RequestType.TRANSFER);
    filter.setBoxId(boxId);

    return RequestFinder.find(secInfo, RequestSelect.BASIC, filter);
  }

  /**
   * Returns the "ship from" address id based on the specified user id.
   * 
   * @param  userId  the user id
   * @return  The ship from address id.
   */
  private String getShipFromAddressIdForUser(String userId) {
    String query =
      "SELECT a.address_id"
        + " FROM es_ardais_user u, ardais_address a"
        + " WHERE u.ardais_user_id = ?"
        + "   AND u.ardais_acct_key = a.ardais_acct_key"
        + "   AND a.address_type = 'Ship To'";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String addressId = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, userId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        addressId = rs.getString("address_id");
      }
      else {
        throw new ApiException("Could not find account Ship To address for user " + userId);
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


  private Address getAddressById(String addressId) {
    return getAddressById(addressId, true);
  }

  private Address getAddressById(String addressId, boolean exceptionIfNotExists) {
    Address address = null;
    //if the address type is System generated (i.e. was typed in as part of the
    //creation of an out-of-network transfer request), return null as the
    //company description.  Such addresses will have an account_key, but it is the key
    //for the account that created the address and we do no wnat that name being used
    //as the name of the destination company
    StringBuffer query = new StringBuffer(100);
    query.append("SELECT a.*, decode(a.address_type, '");
    query.append(com.ardais.bigr.es.helpers.FormLogic.ADDRESS_TYPE_SYSTEM_GENERATED);
    query.append("', null, c.ardais_acct_company_desc) as ardais_acct_company_desc");
    query.append(" FROM ardais_address a, es_ardais_account c");
    query.append(" WHERE a.address_id = ?");
    query.append(" AND a.ardais_acct_key = c.ardais_acct_key");

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, addressId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        address = new Address();
        Map columns = DbUtils.getColumnNames(rs);
        address.populateFromResultSet(columns, rs);
      }
      else if (exceptionIfNotExists) {
        throw new ApiException("Could not find address for address id " + addressId);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return address;
  }

  /**
   * Returns basic box information for the box with the specified box id.  If the box could not be 
   * found, then null is returned. 
   * 
   * @param  boxId  the box id
   * @return  The box.
   */
  private BoxDto getBox(String boxId) {
    String query = "SELECT * FROM iltds_sample_box b WHERE b.box_barcode_id = ?";
    return doBoxQuery(boxId, query);
  }

  /**
   * Returns basic box information along with box location for the box with the specified box id.
   * If the box could not be found, then null is returned. 
   * 
   * @param  boxId  the box id
   * @return  The box.
   */
  private BoxDto getBoxWithLocation(String boxId) {
    String query =
      "SELECT * FROM iltds_sample_box b, iltds_box_location l "
        + "WHERE b.box_barcode_id = ? "
        + "  AND b.box_barcode_id = l.box_barcode_id(+)";
    return doBoxQuery(boxId, query);
  }

  /**
   * Populate the supplied manifestDto with basic box information along with box location
   * for all boxes in the specified manifest (if there are any).  The list is ordered
   * by the manifest's box order. 
   * 
   * @param  dto  The Manifestdto containing the manifest number.  This
   *   {@link ManifestDto#getBoxes()} property will contain the results after the call.
   * 
   * @see #populateWithBoxIdList(ManifestDto)
   */
  private void populateWithBoxesAndLocations(ManifestDto dto) {
    String manifestNumber = dto.getManifestNumber();
    if (manifestNumber != null) {
      String query =
        "SELECT b.*, l.*"
          + " FROM iltds_sample_box b, iltds_box_location l, iltds_manifest m"
          + " WHERE m.manifest_number = ?"
          + "   AND b.box_barcode_id = l.box_barcode_id(+)"
          + "   AND b.manifest_number = m.manifest_number"
          + " ORDER BY b.manifest_order";
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(query);
        pstmt.setString(1, manifestNumber);
        rs = pstmt.executeQuery();
        Map columns = DbUtils.getColumnNames(rs);
        while (rs.next()) {
          BoxDto box = new BoxDto();
          box.populateFromResultSet(columns, rs);
          dto.addBox(box);
        }
      }
      catch (SQLException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }
  }

  /**
   * Populate the supplied manifestDto with a list of box ids
   * for all boxes in the specified manifest (if there are any).  The list is ordered
   * by the manifest's box order. 
   * 
   * @param  dto  The Manifestdto containing the manifest number.  This
   *   {@link ManifestDto#getBoxIdList()} property will contain the results after the call.
   * 
   * @see #populateWithBoxesAndLocations(ManifestDto)
   */
  private void populateWithBoxIdList(ManifestDto dto) {
    String manifestNumber = dto.getManifestNumber();
    if (manifestNumber != null) {
      String query =
        "SELECT b.box_barcode_id"
          + " FROM iltds_sample_box b, iltds_manifest m"
          + " WHERE m.manifest_number = ?"
          + "   AND b.manifest_number = m.manifest_number"
          + " ORDER BY b.manifest_order";
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(query);
        pstmt.setString(1, manifestNumber);
        rs = pstmt.executeQuery();
        List idList = new ArrayList();
        while (rs.next()) {
          idList.add(rs.getString(1));
        }
        dto.setBoxIdList(idList);
      }
      catch (SQLException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }
  }

  /**
   * Sorts the input list of boxes by box location and returns a new list of sorted boxes.  Both
   * input and output lists are lists of strings, each of which is a box barcode id.  Boxes without
   * a location appear at the end of the returned list, in the order in which they appear in the
   * input list. 
   * 
   * @param  boxes  the List of box barcode ids to sort
   * @return  The List of sorted box barcode ids by location.
   */
  private List sortBoxesByLocation(List boxIds) {
    if (ApiFunctions.isEmpty(boxIds)) {
      return boxIds;
    }
    else {
      // Make a copy of the input boxes list.
      List inputBoxes = new ArrayList(boxIds);

      // Build the query the get the box location of each box in the input list.  Some may not 
      // have locations and will thus not be returned in this query. 
      StringBuffer query = new StringBuffer();
      query.append("SELECT b.box_barcode_id");
      query.append("\nFROM iltds_sample_box b, iltds_box_location l");
      query.append("\nWHERE b.box_barcode_id = l.box_barcode_id");
      query.append("\n  AND b.box_barcode_id IN ");
      query.append(ApiFunctions.makeBindParameterList(boxIds.size()));
      query.append("\nORDER BY l.room_id, l.unit_name, TO_NUMBER(l.drawer_id), l.slot_id");

      // Run the query and append each box barcode id returned to the sorted list.  Also remove
      // from the copied input list so we'll know which boxes have locations and which don't.  
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      List sortedBoxes = new ArrayList();
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(query.toString());
        ApiFunctions.bindBindParameterList(pstmt, 1, boxIds);
        rs = pstmt.executeQuery();
        while (rs.next()) {
          String boxId = rs.getString("box_barcode_id");
          sortedBoxes.add(boxId);
          int boxIndex = inputBoxes.indexOf(boxId);
          if (boxIndex >= 0) {
            inputBoxes.remove(boxIndex);
          }
        }
      }
      catch (SQLException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }

      // If there are any boxes left in the input list (i.e. those that do not have a location),
      // append them all to the end of the sorted list in the input list order. 
      if (!inputBoxes.isEmpty()) {
        sortedBoxes.addAll(inputBoxes);
      }
      return sortedBoxes;
    }
  }

  private BoxDto doBoxQuery(String boxId, String query) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    BoxDto box = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, boxId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        box = new BoxDto();
        Map columns = DbUtils.getColumnNames(rs);
        box.populateFromResultSet(columns, rs);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return box;
  }

  /**
   * Finds the sample barcode id and cell ref location for each sample in the box with  
   * box id specified in the input BoxDto, and adds it to the BoxDto's list of samples.
   * 
   * @param  boxDto  The BoxDto describing the box
   */
  private void getSamplesInBox(BoxDto boxDto) {
    String query =
      "SELECT s.sample_barcode_id, s.customer_id, s.cell_ref_location, s.sample_type_cid"
        + " FROM iltds_sample s"
        + " WHERE s.box_barcode_id = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, boxDto.getBoxId());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String cellRefLocation = rs.getString("cell_ref_location");
        String sampleId = rs.getString("sample_barcode_id");
        String sampleAlias = rs.getString("customer_id");
        StringBuffer cellContent = new StringBuffer(50);
        cellContent.append(sampleId);
        if (!ApiFunctions.isEmpty(sampleAlias)) {
          cellContent.append(" (");
          cellContent.append(sampleAlias);
          cellContent.append(")");
        }
        String sampleType = getSampleTypeDisplay(rs.getString("sample_type_cid"));
        boxDto.setCellContent(cellRefLocation, cellContent.toString(), true);
        boxDto.setCellSampleType(cellRefLocation, sampleType);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }

  private String getSampleTypeDisplay(String sampleTypeCid) {
    return (ApiFunctions.isEmpty(sampleTypeCid))
      ? ""
      : GbossFactory.getInstance().getDescription(sampleTypeCid);
  }

  /**
   * Updates the box with the data in the specified DTO.  All columns are updated, so the DTO 
   * should include information for all columns in order to avoid wiping out pre-existing data.
   * 
   * @param  dto  the box DTO
   */
  private void updateBox(BoxDto dto) {
    String sql =
      "UPDATE iltds_sample_box"
        + " SET box_note = ?,"
        + " box_status = ?,"
        + " box_check_in_date = ?,"
        + " box_check_out_date = ?,"
        + " box_check_out_reason = ?,"
        + " box_checkout_request_staff_id = ?,"
        + " storage_type_cid = ?,"
        + " manifest_number = ?,"
        + " manifest_order = ?"
        + " WHERE box_barcode_id = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, dto.getBoxNote());
      pstmt.setString(2, dto.getBoxStatus());
      DateData date = dto.getCheckInDate();
      if (date != null) {
        pstmt.setTimestamp(3, date.getTimestamp());
      }
      date = dto.getCheckOutDate();
      if (date != null) {
        pstmt.setTimestamp(4, date.getTimestamp());
      }
      pstmt.setString(5, dto.getCheckOutReason());
      pstmt.setString(6, dto.getCheckoutUser());
      pstmt.setString(7, dto.getStorageTypeCid());
      pstmt.setString(8, dto.getManifestId());
      pstmt.setString(9, dto.getManifestOrder());
      pstmt.setString(10, dto.getBoxId());
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
   * Invoke the specified method on this class.  This is only meant to be
   * called from BtxTransactionPerformerBase, please don't call it from anywhere
   * else as a mechanism to gain access to private methods in this class.  Every
   * object that the BTX framework dispatches to must contain this
   * method definition, and its implementation should be exactly the same
   * in each class.  Please don't alter this method or its implementation
   * in any way.
   */
  protected BTXDetails invokeBtxEntryPoint(java.lang.reflect.Method method, BTXDetails btxDetails)
    throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }

  /**
   * Performs validation for shipment receipt verification. 
   * 
   * Validation rules:
   * <ul>
   * <li>The tracking number must be present (non-empty after trimming).</li>
   * <li>The tracking number must exist in the system.</li>
   * <li>The tracking number must contain at least one manifest in the shipped state.</li>
   * <li>The manifests associated with the tracking number that are in the shipped state
   *     must all have shipping destinations that match the users location.</li>
   * </ul>
   * 
   * <p>This is a BTX validator method.
   */
  private BTXDetails validatePerformShipmentReceiptVerification(BtxDetailsShipmentReceiptVerification btxDetails)
    throws Exception {

    String newTrackingNumber =
      IltdsUtils.checkTrackingNumber(btxDetails.getNewTrackingNumber(), btxDetails, true, true);

    if (!ApiFunctions.isEmpty(newTrackingNumber)) {
      // Set the new tracking number to that which has been validated.
      btxDetails.setNewTrackingNumber(newTrackingNumber);

      // Get all manifest from tracking number with shipment status set to MFSHIPPED.
      List manifests = findManifests(newTrackingNumber, FormLogic.MNFT_MFSHIPPED);
      if (ApiFunctions.isEmpty(manifests)) {

        // Check if tracking number has been previously verified.
        List verified = findManifests(newTrackingNumber, FormLogic.MNFT_MFVERIFIED);
        if (!ApiFunctions.isEmpty(verified)) {

          // There are no manifest associated with the tracking number
          // that are in a SHIPPED state.
          btxDetails.addActionError(
            new BtxActionError("iltds.error.shipping.receipt.alreadyVerified", newTrackingNumber));

          // Not needed, but completes the check of locations.
          manifests = verified;
        }
        else {
          // There are no manifest associated with the tracking number
          // that are in a SHIPPED state.
          btxDetails.addActionError(
            new BtxActionError(
              "iltds.error.shipping.receipt.noManifestsToVerify",
              newTrackingNumber));
        }
      }

      String userAddressId =
      IltdsUtils.getShipToAddressIdForGeolocation(
          btxDetails.getLoggedInUserSecurityInfo().getUserLocationId());

      // Find the di location. Match locations or throw another error.
      for (int i = 0; i < manifests.size(); i++) {
        ManifestDto manifestDto = (ManifestDto) manifests.get(i);
        String shipToAddressId = manifestDto.getShipToAddressId();
        if (!userAddressId.equalsIgnoreCase(shipToAddressId)) {
          // Location error.
          btxDetails.addActionError(
            new BtxActionError(
              "iltds.error.shipping.receipt.shipToAddressMismatch",
              newTrackingNumber,
              manifestDto.getManifestNumber()));
        }
      }
    }

    return btxDetails;
  }

  /**
   * Perform shipment receipt verification. Update each (shipped) manifest associated with the
   * tracking number to MNFT_MFVERIFIED
   * <p>This is a BTX performer method.
   */
  private BTXDetails performShipmentReceiptVerification(BtxDetailsShipmentReceiptVerification btxDetails)
    throws Exception {

    // Get all manifest from tracking number with shipment status set to MFSHIPPED.
    List manifests = findManifests(btxDetails.getNewTrackingNumber(), FormLogic.MNFT_MFSHIPPED);
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    String userId = securityInfo.getUsername();

    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    try {
      String sql =
        "update iltds_manifest set "
          + "shipment_status = ?, "
          + "receipt_verify_datetime = sysdate, "
          + "receipt_verify_staff_id = ? "
          + "where manifest_number = ?";

      pstmt = con.prepareStatement(sql);
      for (int i = 0; i < manifests.size(); i++) {
        ManifestDto manifestDto = (ManifestDto) manifests.get(i);

        // Update the manifest record.
        pstmt.setString(1, FormLogic.MNFT_MFVERIFIED);
        pstmt.setString(2, userId);
        pstmt.setString(3, manifestDto.getManifestNumber());

        pstmt.executeUpdate();

        // Process each manifest. Create a history record for each box and check for pulled samples.
        processManifest(btxDetails, manifestDto);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * <p>This is a BTX performer method.
   */
  private BTXDetails performBoxReceiptVerification(BtxDetailsReceiptVerification btxDetails)
    throws Exception {
    // Placeholder, to get transaction logging. 
    return btxDetails;
  }

  private List findManifests(String trackingNumber, String shipmentStatus) {
    List manifests = new ArrayList();

    if (trackingNumber != null) {
      String query =
        "SELECT m.* FROM iltds_manifest m WHERE "
          + "m.airbill_tracking_number = ? AND "
          + "m.shipment_status = ?";
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(query);
        pstmt.setString(1, trackingNumber);
        pstmt.setString(2, shipmentStatus);
        rs = pstmt.executeQuery();

        Map columns = DbUtils.getColumnNames(rs);
        while (rs.next()) {
          ManifestDto dto = new ManifestDto();
          dto.populateFromResultSet(columns, rs);
          manifests.add(dto);
        }
      }
      catch (SQLException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }
    return manifests;
  }

  /*
   * Process manifest. Get all boxes associates with the manifest and create a transaction
   * record. Check for any pulled samples and report back with a list of messages containing
   * the manifest, box, and sample that has been pulled.
   */
  private void processManifest(
    BtxDetailsShipmentReceiptVerification btxDetails,
    ManifestDto manifestDto) {
    try {
      Vector boxes = new Vector();
      String manifestId = manifestDto.getManifestNumber();
      ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
      ListGenerator list = home.create();
      boxes = list.findBoxesByManifestID(manifestId);
      boolean foundPulledOrRevoked = false;

      String manifestNumber = manifestDto.getManifestNumber();
      String trackingNumber = manifestDto.getTrackingNumber();

      BtxDetailsReceiptVerification receiptVerification = new BtxDetailsReceiptVerification();
      receiptVerification.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo());
      receiptVerification.setManifestId(manifestNumber);
      receiptVerification.setTrackingNumber(trackingNumber);

      for (int i = 0; i < boxes.size(); i++) {
        String boxId = (String) boxes.get(i);
        SampleAccessBean sample = new SampleAccessBean();
        AccessBeanEnumeration sampleEnum = null;

        sampleEnum = (AccessBeanEnumeration) sample.findSampleBySamplebox(new SampleboxKey(boxId));
        BoxDto boxDto = new BoxDto();
        boxDto.setBoxId(boxId);
        
        // Get the box layout record needed for logging.
        boxDto.setBoxLayoutDto(BoxLayoutUtils.getBoxLayoutDtoByBoxId(boxDto.getBoxId()));

        List samples = new ArrayList();
        while (sampleEnum.hasMoreElements()) {
          sample = (SampleAccessBean) sampleEnum.nextElement();
          samples.add(new SampleData(sample));
          String sampleId = ((SampleKey) sample.__getKey()).sample_barcode_id;

          boxDto.setCellContent(sample.getCell_ref_location(), sampleId);
          if (foundPulledOrRevoked || IltdsUtils.sampleCasePulledOrRevoked(sampleId)) {
            foundPulledOrRevoked = true;
          }
        }
        receiptVerification.setSamples(samples);

        // Record transaction for each box.
        receiptVerification.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
        receiptVerification.setBox(boxDto);
        receiptVerification.setTransactionType("iltds_receipt_boxVerification");
        Btx.perform(receiptVerification);
      }

      if (foundPulledOrRevoked) {
        btxDetails.addActionMessage(
          new BtxActionMessage(
            "iltds.message.shipping.receipt.pulledSamplefound",
            trackingNumber,
            manifestNumber));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * Performs validation for scan shipment information.
   * Validation rules:
   * - The tracking number must be present, syntatically valid, and must exist in the db.
   * - The manifest number must be present, syntactically valid, and must exist in the db.
   * - The tracking number must match the tracking number associated with the manifest.
   * - The manifest ship to location should match current user's location.
   * - The manifest must be in the MFVERIFIED state.
   * - The manifest must contain boxes.
   *
   * <p>This is a BTX validator method.
   */
  private BTXDetails validatePerformScanShipmentInformation(BtxDetailsShippingOperation btxDetails)
    throws Exception {

    // Get the input manifest.
    ManifestDto inputManifest = btxDetails.getManifestDto();

    // Check if tracking and manifest numbers are valid.
    String trackingNumber = inputManifest.getTrackingNumber();
    String manifestNumber = inputManifest.getManifestNumber();
    trackingNumber = IltdsUtils.checkTrackingNumber(trackingNumber, btxDetails, true, true);
    manifestNumber = IltdsUtils.checkManifestNumber(manifestNumber, btxDetails, false, true, true);

    if ((trackingNumber != null) && (manifestNumber != null)) {
      inputManifest.setTrackingNumber(trackingNumber);
      inputManifest.setManifestNumber(manifestNumber);

      ManifestDto manifestDto = getManifestByIdWithBoxesAndLocations(manifestNumber);

      // Check if tracking number matches the manifest's tracking number.
      if (!trackingNumber.equalsIgnoreCase(manifestDto.getTrackingNumber())) {
        btxDetails.addActionError(
          new BtxActionError(
            "iltds.error.shipping.receipt.trackingNumberMismatch",
            trackingNumber,
            manifestNumber));
      }
      else {
        // Make sure ship to address is the same as user's address.
        validateScanAndStoreLocation(btxDetails, manifestDto);

        // The manifest must be in the MFVERIFIED state.
        validateScanAndStoreShipmentStatus(btxDetails, manifestDto);

        if (ApiFunctions.isEmpty(manifestDto.getBoxes())) {
          btxDetails.addActionError(
            new BtxActionError("iltds.error.shipping.receipt.emptyManifest", manifestNumber));
        }
      }
    }
    return btxDetails;
  }

  /**
   * Get the details of a manifest. 
   * 
   * <p>This is a BTX performer method.
   */
  private BTXDetails performScanShipmentInformation(BtxDetailsShippingOperation btxDetails)
    throws Exception {
    String trackingNumber = btxDetails.getManifestDto().getTrackingNumber();
    String manifestNumber = btxDetails.getManifestDto().getManifestNumber();

    ManifestDto manifestDto = getManifestByIdWithBoxCounts(manifestNumber);
    btxDetails.setManifestDto(manifestDto);

    List boxes = manifestDto.getBoxes();
    boolean foundPulledOrRevoked = false;
    
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    for (int i = 0; i < boxes.size(); i++) {
      BoxDto box = (BoxDto) boxes.get(i);
      String boxId = box.getBoxId();

      // Find the common storage types.
      Set commonStorageTypes = IltdsUtils.getCommonStorageTypesByAccountAndBoxId(securityInfo, boxId);

      // Get the available storage types if there are more than 1 storage types in common.
      box.setAvailableStorageTypes(getAvailableStorageTypes(commonStorageTypes));
      
      // Check for warning messages.
      if (FormLogic.BX_SCANRECEIVED.equals(box.getBoxStatus())) {
        if (commonStorageTypes.isEmpty()) {
          Map storageMap = getStorageMap(boxId, securityInfo);
         
          StringBuffer sb = new StringBuffer();
          IltdsUtils.determineStorageTypeWarning(securityInfo, storageMap, false, sb);

          box.setWarning(sb.toString());
        }
      }

      if (foundPulledOrRevoked || box.isContainsPulledOrRevokedSamples()) {
        foundPulledOrRevoked = true;
      }
    }

    if (foundPulledOrRevoked) {
      btxDetails.addActionMessage(
        new BtxActionMessage(
          "iltds.message.shipping.receipt.pulledSamplefound",
          trackingNumber,
          manifestNumber));
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Performs validation for scan and store box.
   * Validation rules:
   * - The box id must be present, syntactically valid, and must exist in the db.
   * - The box id must be part of the manifest.
   * - The box id should not be processed twice.
   * - The manifest ship to location should match current user's location.
   * - The manifest must be in the MFVERIFIED state.
   *
   * <p>This is a BTX validator method.
   */
  private BTXDetails validatePerformScanAndStoreBox(BtxDetailsScanAndStore btxDetails)
    throws Exception {

    // Get the manifest number.
    String manifestNumber = btxDetails.getManifestDto().getManifestNumber();

    // Get and set the btxDetails manifest dto.
    ManifestDto manifestDto = getManifestByIdWithBoxCounts(manifestNumber);
    btxDetails.setManifestDto(manifestDto);

    // Get the scanned in box id.
    String scannedBoxId = btxDetails.getScannedBoxId();
    scannedBoxId = IltdsUtils.checkBoxId(scannedBoxId, btxDetails, false, true, true);

    if (!ApiFunctions.isEmpty(scannedBoxId)) {
      btxDetails.setScannedBoxId(scannedBoxId);

      boolean boxInManifest = false;
      List boxes = manifestDto.getBoxes();
      for (int i = 0; i < boxes.size(); i++) {
        BoxDto boxDto = (BoxDto) boxes.get(i);
        if (scannedBoxId.equalsIgnoreCase(boxDto.getBoxId())) {
          boxInManifest = true;
          if (!ApiFunctions.isEmpty(boxDto.getLocation())) {
            // Already processed error.
            btxDetails.addActionError(
              new BtxActionError(
                "iltds.error.shipping.receipt.boxAlreadyScanned",
                scannedBoxId,
                manifestNumber));
          }
        }
      }

      if (!boxInManifest) {
        // Box not in manifest error.
        btxDetails.addActionError(
          new BtxActionError(
            "iltds.error.shipping.receipt.boxNotOnManifest",
            scannedBoxId,
            manifestNumber));
      }
    }

    // Make sure ship to address is the same as user's address.
    validateScanAndStoreLocation(btxDetails, manifestDto);

    // The manifest must be in the MFVERIFIED state.
    validateScanAndStoreShipmentStatus(btxDetails, manifestDto);

    return btxDetails;
  }

  /**
   * Perform scan and store box.
   * <p>This is a BTX performer method.
   */
  private BTXDetails performScanAndStoreBox(BtxDetailsScanAndStore btxDetails) throws Exception {

    String scannedBoxId = btxDetails.getScannedBoxId();

    ManifestDto manifestDto = btxDetails.getManifestDto();
    btxDetails.setManifestId(manifestDto.getManifestNumber());
    btxDetails.setTrackingNumber(manifestDto.getTrackingNumber());

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    List boxes = manifestDto.getBoxes();
    for (int i = 0; i < boxes.size(); i++) {
      BoxDto boxDto = (BoxDto) boxes.get(i);
      String boxId = boxDto.getBoxId();

      // Find the common storage types.
      Set commonStorageTypes = IltdsUtils.getCommonStorageTypesByAccountAndBoxId(securityInfo, boxId);

      // Get the available storage types if there are more than 1 storage types in common.
      boxDto.setAvailableStorageTypes(getAvailableStorageTypes(commonStorageTypes));
      
      // Check for warning messages.
      if (FormLogic.BX_SCANRECEIVED.equals(boxDto.getBoxStatus())) {
        if (commonStorageTypes.isEmpty()) {
          Map storageMap = getStorageMap(boxId, securityInfo);
         
          StringBuffer sb = new StringBuffer();
          IltdsUtils.determineStorageTypeWarning(securityInfo, storageMap, false, sb);

          boxDto.setWarning(sb.toString());
        }
      }

      if ((ApiFunctions.isEmpty(boxDto.getLocation()))
        && (scannedBoxId.equalsIgnoreCase(boxDto.getBoxId()))) {
        SampleboxKey boxKey = new SampleboxKey(scannedBoxId);
        SampleboxAccessBean boxAB = new SampleboxAccessBean(boxKey);
        
        String statusTypeCode = null;
        if (commonStorageTypes.isEmpty()) {
          // Mark the box as scan received. The box cannot be processed as is.
          boxAB.setBox_status(FormLogic.BX_SCANRECEIVED);
          boxAB.commitCopyHelper();

          Map storageMap = getStorageMap(boxId, securityInfo);
         
          StringBuffer sb = new StringBuffer();
          IltdsUtils.determineStorageTypeWarning(securityInfo, storageMap, false, sb);

          boxDto.setWarning(sb.toString());
          
          btxDetails.setUnstorableSampleIdList((IdList)storageMap.get(IltdsUtils.UNSTORABLE_SAMPLE_ID_LIST));
          btxDetails.setStorableSampleIdList((IdList)storageMap.get(IltdsUtils.STORABLE_SAMPLE_ID_LIST));

          // Set the samples status type code.
          statusTypeCode = FormLogic.SMPL_CHECKEDOUT;
        }
        else {
          Iterator iterator = commonStorageTypes.iterator();
          String defaultStorageTypeCid = (String)iterator.next();

          // Get a default location.
          BtxDetailsSetBoxLocation boxLocation = new BtxDetailsSetBoxLocation();
          boxLocation.setBoxId(scannedBoxId);
          boxLocation.setStorageTypeCid(defaultStorageTypeCid);
          boxLocation.setGeoLoc(securityInfo.getUserLocationId());
          boxLocation.setLoggedInUserSecurityInfo(securityInfo);
          boxLocation.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
          boxLocation.setTransactionType("iltds_box_setBoxLocation");

          BtxDetailsSetBoxLocation resultDetail =
            (BtxDetailsSetBoxLocation) Btx.perform(boxLocation);
            
          if (resultDetail.isTransactionCompleted()) {
            BTXBoxLocation location = resultDetail.getLocation();

            // Update the stored box location.
            boxDto.setStorageTypeCid(location.getStorageTypeCid());
            boxDto.setLocation(location.getLocationAddressID());
            boxDto.setRoom(location.getRoomID());
            boxDto.setUnitName(location.getUnitName());
            boxDto.setDrawer(location.getDrawerID());
            boxDto.setSlot(location.getSlotID());

            btxDetails.setBoxLocation(location);
          }
          else {
            btxDetails.addActionErrors(resultDetail.getActionErrors());
            btxDetails.setActionForwardRetry();
            return btxDetails;
          }

          // Update the box status.
          boxAB.setStorageTypeCid(defaultStorageTypeCid);
          boxAB.setBox_status(FormLogic.BX_CHECKEDIN);
          boxAB.setBox_check_in_date(btxDetails.getBeginTimestamp());
          boxAB.commitCopyHelper();
          
          // Set the samples status type code.
          statusTypeCode = FormLogic.SMPL_BOXSCAN;
        }

        // Update the samples associated with the box.
        Vector[] samplesAndCells = updateSamples(btxDetails, statusTypeCode);

        Vector samples = samplesAndCells[0];
        Vector cellRefs = samplesAndCells[1];
        for (int j = 0; j < samples.size(); j++) {
          boxDto.setCellContent((String) cellRefs.get(j), ((SampleData)samples.get(j)).getSampleId());
        }
    
        btxDetails.setSamples(samples);
        // Get the box layout record needed for logging.
        boxDto.setBoxLayoutDto(BoxLayoutUtils.getBoxLayoutDtoByBoxId(scannedBoxId));
        btxDetails.setBox(boxDto);
      }
    }
    
    // Automatically close out the manifest if all boxes in the manifest have a location.
    boolean manifestComplete = true;
    for (int i = 0; i < boxes.size(); i++) {
      BoxDto boxDto = (BoxDto) boxes.get(i);
    
      if (ApiFunctions.isEmpty(boxDto.getLocation())) {
        manifestComplete = false;
        break;
      }
    }
    if (manifestComplete) {
      BtxDetailsScanAndStore manifestDetails = new BtxDetailsScanAndStore();
      manifestDetails.setManifestId(btxDetails.getManifestId());
      manifestDetails.setTrackingNumber(btxDetails.getTrackingNumber());
      manifestDetails.setLoggedInUserSecurityInfo(securityInfo);
      manifestDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
      manifestDetails.setTransactionType("iltds_receipt_scanAndStoreManifest");

      BtxDetailsScanAndStore resultDetail =
        (BtxDetailsScanAndStore) Btx.perform(manifestDetails);
    }

    // Reset the scanned in box.
    btxDetails.setScannedBoxId(null);

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Performs validation for scan and store manifest.
   * Validation rules:
   * - The manifest ship to location should match current user's location.
   * - The manifest must be in the MFVERIFIED state.
   *
   * <p>This is a BTX validator method.
   */
  private BTXDetails validatePerformScanAndStoreManifest(BtxDetailsScanAndStore btxDetails)
    throws Exception {

    // Get the manifest number.
    String manifestNumber = btxDetails.getManifestId();

    // Get and set the btxDetails manifest dto.
    ManifestDto manifestDto = getManifestByIdWithBoxCounts(manifestNumber);
    btxDetails.setManifestDto(manifestDto);

    // Make sure ship to address is the same as user's address.
    validateScanAndStoreLocation(btxDetails, manifestDto);

    // The manifest must be in the MFVERIFIED state.
    validateScanAndStoreShipmentStatus(btxDetails, manifestDto);

    return btxDetails;
  }

  /**
   * Perform scan and store manifest. Update the manifest's shipment status to MNFT_MFRECEIVED.
   * <p>This is a BTX performer method.
   */
  private BTXDetails performScanAndStoreManifest(BtxDetailsScanAndStore btxDetails)
    throws Exception {

    // Get the manifest.
    ManifestDto manifestDto = btxDetails.getManifestDto();

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String userId = securityInfo.getUsername();

    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    try {
      String sql =
        "update iltds_manifest set "
          + "shipment_status = ?, "
          + "receipt_datetime = sysdate, "
          + "receipt_by_staff_id = ? "
          + "where manifest_number = ?";

      pstmt = con.prepareStatement(sql);

      // Update the manifest record.
      pstmt.setString(1, FormLogic.MNFT_MFRECEIVED);
      pstmt.setString(2, userId);
      pstmt.setString(3, manifestDto.getManifestNumber());

      pstmt.executeUpdate();
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }

    // Do not log transaction history record.
    btxDetails.setActionForwardTXIncomplete("success");
    return btxDetails;
  }

  /**
   * Performs validation for scan shipment information.
   * Validation rules:
   * - The tracking number must be present, syntatically valid, and must exist in the db.
   * - The manifest number must be present, syntactically valid, and must exist in the db.
   * - The tracking number must match the tracking number associated with the manifest.
   * - The manifest ship to location should match current user's location.
   * - The manifest must contain boxes.
   *
   * <p>This is a BTX validator method.
   */
  private BTXDetails validatePerformPrintPutAwayTicket(BtxDetailsScanAndStore btxDetails)
    throws Exception {

    // Get the input manifest.
    ManifestDto inputManifest = btxDetails.getManifestDto();

    // Check if tracking and manifest numbers are valid.
    String trackingNumber = inputManifest.getTrackingNumber();
    String manifestNumber = inputManifest.getManifestNumber();
    trackingNumber = IltdsUtils.checkTrackingNumber(trackingNumber, btxDetails, true, true);
    manifestNumber = IltdsUtils.checkManifestNumber(manifestNumber, btxDetails, false, true, true);

    if ((trackingNumber != null) && (manifestNumber != null)) {
      inputManifest.setTrackingNumber(trackingNumber);
      inputManifest.setManifestNumber(manifestNumber);

      ManifestDto manifestDto = getManifestByIdWithBoxesAndLocations(manifestNumber);

      // Check if tracking number matches the manifest's tracking number.
      if (!trackingNumber.equalsIgnoreCase(manifestDto.getTrackingNumber())) {
        btxDetails.addActionError(
          new BtxActionError(
            "iltds.error.shipping.receipt.trackingNumberMismatch",
            trackingNumber,
            manifestNumber));
      }
      else {
        // Make sure ship to address is the same as user's address.
        validateScanAndStoreLocation(btxDetails, manifestDto);

        if (ApiFunctions.isEmpty(manifestDto.getBoxes())) {
          btxDetails.addActionError(
            new BtxActionError("iltds.error.shipping.receipt.emptyManifest", manifestNumber));
        }
      }
    }
    return btxDetails;
  }

  /**
   * 
   */
  private BTXDetails performPrintPutAwayTicket(BtxDetailsScanAndStore btxDetails)
    throws Exception {

    // Get the manifest number.
    String manifestNumber = btxDetails.getManifestDto().getManifestNumber();

    // Get and set the btxDetails manifest dto.
    ManifestDto manifest = getManifestByIdWithBoxesAndLocations(manifestNumber);

    // Remove all boxes from the manifest if a box was provided. Otherwise print a put away
    // ticket containing all boxes within the manifest.
    String scannedBoxId = btxDetails.getScannedBoxId();
    if (!ApiFunctions.isEmpty(scannedBoxId)) {
      Iterator boxes = manifest.getBoxes().iterator();
      while (boxes.hasNext()) {
        BoxDto box = (BoxDto) boxes.next();
        if (!scannedBoxId.equalsIgnoreCase(box.getBoxId())) {
          boxes.remove();
        }
      }
    }
    btxDetails.setManifestDto(manifest);

    // Do not log transaction history record.
    btxDetails.setActionForwardTXIncomplete("success");
    return btxDetails;
  }

  /*
   * Validate scan and store location.
   */
  private void validateScanAndStoreLocation(
    BtxDetailsShippingOperation btxDetails,
    ManifestDto manifestDto) {
    // Check if ship to address is correct.
    String userAddressId =
    IltdsUtils.getShipToAddressIdForGeolocation(
        btxDetails.getLoggedInUserSecurityInfo().getUserLocationId());

    // Find the di location. Match locations or throw another error.
    String shipToAddressId = manifestDto.getShipToAddressId();
    if (!userAddressId.equalsIgnoreCase(shipToAddressId)) {
      // Location error.
      btxDetails.addActionError(
        new BtxActionError(
          "iltds.error.shipping.receipt.shipToAddressMismatch",
          manifestDto.getTrackingNumber(),
          manifestDto.getManifestNumber()));
    }
  }

  /*
   * Validate scan and store shipment status.
   */
  private void validateScanAndStoreShipmentStatus(
    BtxDetailsShippingOperation btxDetails,
    ManifestDto manifestDto) {
    String shipmentStatus = manifestDto.getShipmentStatus();

    if (shipmentStatus.equalsIgnoreCase(FormLogic.MNFT_MFRECEIVED)) {
      btxDetails.addActionError(
        new BtxActionError(
          "iltds.error.shipping.receipt.manifestAlreadyReceived",
          manifestDto.getManifestNumber()));
    }
    else if (!shipmentStatus.equalsIgnoreCase(FormLogic.MNFT_MFVERIFIED)) {
      btxDetails.addActionError(
        new BtxActionError(
          "iltds.error.shipping.receipt.manifestNotVerified",
          manifestDto.getManifestNumber()));
    }
  }

  private Vector[] updateSamples(BtxDetailsScanAndStore btxDetails, String statusTypeCode) throws Exception {

    String scannedBoxId = btxDetails.getScannedBoxId();
    Timestamp timestamp = btxDetails.getBeginTimestamp();
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    Vector samples = new Vector();
    Vector cellRefs = new Vector();

    SampleAccessBean sample = new SampleAccessBean();
    AccessBeanEnumeration sampleEnum =
      (AccessBeanEnumeration) sample.findSampleBySamplebox(new SampleboxKey(scannedBoxId));

    while (sampleEnum.hasMoreElements()) {
      sample = (SampleAccessBean) sampleEnum.nextElement();

      SampleKey sampleKey = (SampleKey) sample.__getKey();
      samples.add(new SampleData(sample));
      cellRefs.add(sample.getCell_ref_location());

      SamplestatusAccessBean status = null;

      // For each sample, we need to set the receipt date, if it is
      // not yet set...we don't want to reset b/c we want the earliest date.
      // "receiptDate" isn't a great name -- this field really represents the
      // first time is was received AT ARDAIS, if ever.  It doesn't mean the
      // the first time it was received anywhere.  So, we only set this if
      // it is being received by an Ardais user.  We also only make the sample
      // QCAWAITING and GENRELEASED on its first receipt at Ardais.
      if (sample.getReceiptDate() == null && securityInfo.isInRoleSystemOwner()) {
        sample.setReceiptDate(timestamp);
        // Set the sales status of the sample to GENRELEASED when it first arrives at Ardais.
        IltdsUtils.applyPolicyToSample(sample, IltdsUtils.APPLY_POLICY_FOR_SALES_STATUS);

        status = new SamplestatusAccessBean();
        status.setInit_argSample(sampleKey);
        status.setInit_argStatus_type_code(FormLogic.SMPL_QCAWAITING);
        status.setInit_argSample_status_datetime(timestamp);
        status.commitCopyHelper();
      }

      // MR 6967 set status to BOXSCAN...
      status = new SamplestatusAccessBean();
      status.setInit_argSample(sampleKey);
      status.setInit_argStatus_type_code(statusTypeCode);
      status.setInit_argSample_status_datetime(timestamp);
      status.commitCopyHelper();

      // MR 7047 -- establish LAST KNOWN LOCATION ID
      sample.setLastknownlocationid(securityInfo.getUserLocationId());
      sample.commitCopyHelper();
    }
    return new Vector[] { samples, cellRefs };
  }

  private ManifestDto getManifestByIdWithBoxCounts(String manifestNumber) throws Exception {

    // Get and set the btxDetails manifest dto.
    ManifestDto manifestDto = getManifestByIdWithBoxesAndLocations(manifestNumber);

    boolean foundPulledOrRevoked = false;

    List boxes = manifestDto.getBoxes();
    for (int i = 0; i < boxes.size(); i++) {
      BoxDto box = (BoxDto) boxes.get(i);
      SampleAccessBean sample = new SampleAccessBean();
      AccessBeanEnumeration sampleEnum = null;

      sampleEnum =
        (AccessBeanEnumeration) sample.findSampleBySamplebox(new SampleboxKey(box.getBoxId()));

      int count = 0;
      while (sampleEnum.hasMoreElements()) {
        count++;
        sample = (SampleAccessBean) sampleEnum.nextElement();
        String sampleId = ((SampleKey) sample.__getKey()).sample_barcode_id;

        if (foundPulledOrRevoked || IltdsUtils.sampleCasePulledOrRevoked(sampleId)) {
          foundPulledOrRevoked = true;
          box.setContainsPulledOrRevokedSamples(true);
        }
      }
      box.setBoxSampleCount(count);
    }
    return manifestDto;
  }

  private String getAvailableStorageTypes(Set commonStorageTypes) {
    String availableStorageTypes = null;

    // Get the available storage types if there are more than 1 storage types in common.
    if (commonStorageTypes.size() > 1) {
      Iterator iterator = commonStorageTypes.iterator();
      StringBuffer sb = new StringBuffer();
      while (iterator.hasNext()) {
        String storageTypeCid = (String)iterator.next();
        sb.append(GbossFactory.getInstance().getDescription(storageTypeCid));
        if (iterator.hasNext()) {
          sb.append(", ");
        }
      }
      availableStorageTypes = sb.toString();
    }
    return availableStorageTypes;
  }

  /**
   * The box contains sample types that do not share a common storage type.
   * The list below shows possible solutions as to what the user can do to alleviate the
   * problem:
   * 
   * 1. The box has samples that when divided into compatible storage types, will allow ALL
   * the samples to be processed because the system has determine that all the sample types
   * in the box have a compatable storage type in the system.
   * 
   * 2. The box has samples that when divided into compatible storage types, will allow SOME
   * of the samples to be processed because the system has determine that some of the sample
   * types in the box have a compatable storage type in the system. The samples that cannot
   * be handled need to be addressed offline.
   * 
   * 3. The box has samples that even when divided into compatible storage types, will not
   * be allowed into the system. The system has determined that none of the sample types in
   * the box can be handled. The user needs to address this issue offline.
   * 
   * Please note that there still exist the possibility that once samples have been divided
   * up into "compatable" storage types, there might not be any available slots. In which
   * case the user needs to free up space in the repository or add new storage units.
   * 
   */
  private Map getStorageMap(String boxId, SecurityInfo securityInfo) {
    Set sampleTypes = IltdsUtils.getSampleTypesByBoxId(boxId);
    Set accountStorageTypes = IltdsUtils.getStorageTypesByAccount(securityInfo);
    
    List storableSampleTypeList = new ArrayList();
    List unstorableSampleTypeList = new ArrayList();
          
    Iterator iterator = sampleTypes.iterator();
    while (iterator.hasNext()) {
      String sampleTypeCid = (String)iterator.next();
      Set storageTypes = IltdsUtils.getStorageTypesBySampleType(sampleTypeCid);
      storageTypes.retainAll(accountStorageTypes);
      if (storageTypes.isEmpty()) {
        unstorableSampleTypeList.add(sampleTypeCid);
      }
      else {
        storableSampleTypeList.add(sampleTypeCid);
      }
    }

    Map storageMap = new HashMap();
    storageMap.put(IltdsUtils.STORABLE_SAMPLE_TYPE_LIST, storableSampleTypeList);
    storageMap.put(IltdsUtils.UNSTORABLE_SAMPLE_TYPE_LIST, unstorableSampleTypeList);
    storageMap.put(IltdsUtils.STORABLE_SAMPLE_ID_LIST, IltdsUtils.getSampleIdsByBoxIdAndSampleTypes(boxId, storableSampleTypeList));
    storageMap.put(IltdsUtils.UNSTORABLE_SAMPLE_ID_LIST, IltdsUtils.getSampleIdsByBoxIdAndSampleTypes(boxId, unstorableSampleTypeList));

    return storageMap;
  }
}
