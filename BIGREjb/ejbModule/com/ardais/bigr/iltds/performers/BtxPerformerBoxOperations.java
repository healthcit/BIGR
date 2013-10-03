package com.ardais.bigr.iltds.performers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.ejb.ObjectNotFoundException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.Asm;
import com.ardais.bigr.iltds.beans.AsmAccessBean;
import com.ardais.bigr.iltds.beans.AsmKey;
import com.ardais.bigr.iltds.beans.AsmformAccessBean;
import com.ardais.bigr.iltds.beans.AsmformKey;
import com.ardais.bigr.iltds.beans.BoxlocationAccessBean;
import com.ardais.bigr.iltds.beans.BoxlocationKey;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.GeolocationAccessBean;
import com.ardais.bigr.iltds.beans.GeolocationKey;
import com.ardais.bigr.iltds.beans.Sample;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.beans.SampleOperation;
import com.ardais.bigr.iltds.beans.SampleOperationHome;
import com.ardais.bigr.iltds.beans.SampleboxAccessBean;
import com.ardais.bigr.iltds.beans.SampleboxKey;
import com.ardais.bigr.iltds.beans.SamplestatusAccessBean;
import com.ardais.bigr.iltds.bizlogic.Allocation;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxDetailsCheckOutBox;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateBox;
import com.ardais.bigr.iltds.btx.BtxDetailsSetBoxLocation;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.userprofile.SimpleProfile;
import com.ardais.bigr.userprofile.UserProfileTopics;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.EjbHomes;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

/**
 * This performs request-related ILTDS BTX business transactions.
 */
public class BtxPerformerBoxOperations extends BtxTransactionPerformerBase {

  private static int MAX_COMMENT_LENGTH = 4000;

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

  public BtxPerformerBoxOperations() {
    super();
  }

  /**
   * 
   */
  private BTXDetails performCheckOutBox(BtxDetailsCheckOutBox btxDetails) throws Exception {

    Vector samples = btxDetails.getSamples();
    String boxId = btxDetails.getBoxId();
    BoxLayoutDto boxLayoutDto = btxDetails.getBoxLayoutDto();
    boolean clearSampleBoxInformation = btxDetails.isClearSampleBoxInformation();
    String reason = btxDetails.getReason();
    String comment = btxDetails.getComment();

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String requestedBy = btxDetails.getRequestedBy();

    // The samples Vector passed in is a Vector of SampleData objects.
    // Most of the calls in here expect a vector of strings, so make such
    // a vector now. Also create a HashMap of ids to SampleData beans so
    // we can easily look up the SampleData later as needed.
    Vector sampleIds = new Vector(samples.size());
    HashMap idToSampleData = new HashMap();
    for (int i = 0; i < samples.size(); i++) {
      SampleData sample = (SampleData) samples.get(i);
      sampleIds.add(sample.getSampleId());
      idToSampleData.put(sample.getSampleId(), sample);
    }

    try {
      java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());

      // For a box check-out, per MR 5006, all of the samples must exist.
      boolean errorOnNonexistentSamples = true;
      
      //per MR7356, don't allow samples on an open request to be checked out
      //(unless this check out is for a request)
      boolean enforceRequestedSampleRestrictions = ApiFunctions.isEmpty(btxDetails.getRequestId());

      SampleOperationHome soHome = (SampleOperationHome) EjbHomes.getHome(SampleOperationHome.class);
      SampleOperation sampleOp = soHome.create();
      String errorMsg =
        sampleOp.validSamplesForBoxScan(sampleIds, errorOnNonexistentSamples, enforceRequestedSampleRestrictions, false, securityInfo, boxLayoutDto);
      if (errorMsg != null) {
        throw new IllegalArgumentException(errorMsg);
      }

      // Get the old boxes for the samples before they are removed from the box.
      Vector oldBoxIds = (Vector) IltdsUtils.getSampleBoxIds(sampleIds, boxId);
      btxDetails.setOldBoxIds(oldBoxIds);

      // Generate and/or find all Samples in the system and put into table
      // The fourth parameter to createSampleTable, if true, will result
      // in a Java exception being thrown if any of the samples don't
      // currently exist in the database.  For a box check-out, per
      // MR 5006, all of the samples must exist.
      ArrayList myNewSamplesInBox =
        createSampleTable(samples, now, errorOnNonexistentSamples, securityInfo);

      // If box was found in system, then get sample in the box.
      ArrayList myOldSamplesInBox = getSamplesInBox(boxId);
      List fulfilledSampleIds = btxDetails.getFulfilledSampleIds();
      //take any samples that are not to be orphaned out of the old sample list
      //see MR8203 for details
      if (!ApiFunctions.isEmpty(fulfilledSampleIds)) {
        Iterator sampleIterator = myOldSamplesInBox.iterator();
        while (sampleIterator.hasNext()) {
          SampleAccessBean sample = (SampleAccessBean)sampleIterator.next();
          String sampleId = ((SampleKey)sample.__getKey()).sample_barcode_id;
          if (fulfilledSampleIds.contains(sampleId)) {
            sampleIterator.remove();
          }
        }
      }

      // Process any orphaned samples.
      orphanSamples(myNewSamplesInBox, myOldSamplesInBox, now);

      removeBoxFromLocationsPrivate(boxId);

      SampleboxAccessBean mySBAB = getSampleBox(boxId);

      // Update properties on the samples that are being checked out.
      // If the flag to do so is true, blank out the box information.
      // This is done because checked-out samples are transported in a box
      // but aren't recorded as being in a box in inventory.  
      // If the flag to do this is false, then update the samples with the
      // new box information. When fulfilling Transfer Requests this is done, 
      // because we need to maintain the location of the sample in the box.
      for (int i = 0; i < myNewSamplesInBox.size(); i++) {
        SampleAccessBean sample = (SampleAccessBean) myNewSamplesInBox.get(i);
        sample.setOrphan_datetime(null);
        if (clearSampleBoxInformation) {
          sample.setSamplebox(null);
          sample.setCell_ref_location(null);
        }
        else {
          // Add the sample to the box
          mySBAB.addSample((Sample) sample.getEJBRef());

          // Get the sampleData for this sample and set the cell ref location.
          SampleKey sampleKey = (SampleKey) sample.getEJBRef().getPrimaryKey();
          String sampleId = sampleKey.sample_barcode_id;
          SampleData sampleData = (SampleData) idToSampleData.get(sampleId);
          sample.setCell_ref_location(sampleData.getLocation());
        }

        sample.setBarcode_scan_datetime(now);
        sample.commitCopyHelper();
      }

      // Update box layout for box.
      mySBAB.setBoxLayoutId(boxLayoutDto.getBoxLayoutId());

      // MR8022 - storage type will be set to null because we do not enforce a storage type outside
      // of our system. MR7194 no longer applies.
      mySBAB.setStorageTypeCid(null);

      mySBAB.setBox_check_out_date(now);
      mySBAB.setBox_status(FormLogic.BX_CHECKEDOUT);
      mySBAB.setBox_check_out_reason(reason);
      mySBAB.setBox_note(comment);
      mySBAB.setBox_checkout_request_staff_id(requestedBy);
      mySBAB.setManifest(null);

      mySBAB.commitCopyHelper();

      if (reason.equals("MICASEPULL")) {
        setSampleStatus(sampleIds, FormLogic.SMPL_MICOCASEPULL, now);
      }
      else if (reason.equals("MVTOPATH")) {
        setSampleStatus(sampleIds, FormLogic.SMPL_ARMVTOPATH, now);
      }
      else if (reason.equals("MICONSREV")) {
        setSampleStatus(sampleIds, FormLogic.SMPL_MICOCONSREV, now);
      }
      else if (reason.equals("MIOTHER")) {
        setSampleStatus(sampleIds, FormLogic.SMPL_MICOOTHER, now);
      }
      else if (reason.equals("ARCASEPULL")) {
        setSampleStatus(sampleIds, FormLogic.SMPL_ARCOCASEPULL, now);
      }
      else if (reason.equals("ARCONSREV")) {
        setSampleStatus(sampleIds, FormLogic.SMPL_ARCOCONSREV, now);
      }
      else if (reason.equals("ARCONSUME")) {
        setSampleStatus(sampleIds, FormLogic.SMPL_COCONSUMED, now);
      }
      else if (reason.equals("ARMVTORND")) {
        setSampleStatus(sampleIds, FormLogic.SMPL_CORND, now);
      }
      else if (
        (reason.equals("AROTHER"))
          || (reason.equals("ARLICENSED"))
          || (reason.equals("ARDESTROYED"))) {
        setSampleStatus(sampleIds, FormLogic.SMPL_ARCOOTHER, now);
      }
      else if (reason.equals(FormLogic.BOX_FULFILLREQUEST)) {
        setSampleStatus(sampleIds, FormLogic.SMPL_CHECKEDOUT, now);
      }

      // The old boxes can be empty, so update their status.
      Vector emptyBoxes = null;
      if (ApiFunctions.isEmpty(fulfilledSampleIds)) {
        emptyBoxes = IltdsUtils.processEmptyBoxLocations(oldBoxIds, sampleIds, securityInfo.getUsername());
      }
      else {
        emptyBoxes = IltdsUtils.processEmptyBoxLocations(oldBoxIds, fulfilledSampleIds, securityInfo.getUsername());
      }
      btxDetails.setEmptyBoxes(emptyBoxes);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    return btxDetails;
  }

  private BTXDetails performCreateBox(BtxDetailsCreateBox btxDetails) throws Exception {
    
    String boxID = btxDetails.getBoxId();
    BoxLayoutDto boxLayoutDto = btxDetails.getBoxLayoutDto();
    Vector samples = btxDetails.getSamples();
    Vector cellRefs = btxDetails.getCellRefs();
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    //create a vector of sample ids from the vector of samples
    Vector sampleIds = new Vector();
    Iterator sampleIterator = samples.iterator();
    while (sampleIterator.hasNext()) {
      SampleData sample = (SampleData)sampleIterator.next();
      sampleIds.add(sample.getSampleId());
    }
    
    try {
      java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());

      String userId = securityInfo.getUsername();
      String account = securityInfo.getAccount();

      // The second parameter to validSamplesForBoxScan, if true, indicates to require
      // that all of the samples currently exist in the database.  Certain DI
      // and Ardais box scans allow sample ids that don't exist because scanning
      // the sample actually creates the sample record.  See MR 7867 for more details.
      //
      boolean errorOnNonexistentSamples = (!securityInfo.isInRoleSystemOwnerOrDi());
      
      //MR7356 Don't allow samples on open requests to be scanned into the new box
      boolean enforceRequestedSampleRestrictions = true;

      SampleOperationHome soHome = (SampleOperationHome) EjbHomes.getHome(SampleOperationHome.class);
      SampleOperation sampleOp = soHome.create();
      String errorMsg = sampleOp.validSamplesForBoxScan(sampleIds, errorOnNonexistentSamples, enforceRequestedSampleRestrictions, true, securityInfo, boxLayoutDto);
      if (errorMsg != null) {
        throw new IllegalArgumentException(errorMsg);
      }

      String storageTypeCid = btxDetails.getStorageTypeCid();

      // Generate and/or find all Samples in the system and put into table
      // The fourth parameter to createSampleTable, if true, will result
      // in a Java exception being thrown if any of the samples don't
      // currently exist in the database.
      //
      ArrayList myNewSamplesInBox =
        createSampleTable(samples, now, errorOnNonexistentSamples, securityInfo);

      // If box was found in system, then get sample in the box
      ArrayList myOldSamplesInBox = getSamplesInBox(boxID);

      // Process any orphaned samples
      orphanSamples(myNewSamplesInBox, myOldSamplesInBox, now);

      {
        SampleboxAccessBean mySBAB = getSampleBox(boxID);
        mySBAB.setBoxLayoutId(boxLayoutDto.getBoxLayoutId());

        // Update properties on the samples that will now be in the box,
        // and place the samples in the box.
        for (int i = 0; i < myNewSamplesInBox.size(); i++) {
          SampleAccessBean sample = (SampleAccessBean) myNewSamplesInBox.get(i);
          mySBAB.addSample((Sample) sample.getEJBRef());
          sample.setBarcode_scan_datetime(now);
          
          // MR7265 - need to explicitly set the box barcode id here. There is
          // a timing issue when both the box and sample don't exist in the DB. So
          // addSample() method doesn't work in that instance. Make sure that this
          // "set" is called after the addSample() method.
          sample.setSamplebox_box_barcode_id(boxID);
          sample.setCell_ref_location((String) cellRefs.get(i));
          sample.commitCopyHelper();
        }
        
        // Set all the Box Values for the box scan
        mySBAB.setStorageTypeCid(storageTypeCid);
        mySBAB.setBox_check_in_date(now);
        mySBAB.setBox_status(FormLogic.BX_CHECKEDIN);
        mySBAB.setManifest(null);

        mySBAB.commitCopyHelper();
      }

      // Set the Status and Event tables to record the new box scan
      if (securityInfo.isInRoleDi()) {
        setSampleStatus(sampleIds, FormLogic.SMPL_BOXSCAN, now);
      }
      else if (securityInfo.isInRoleSystemOwner()) {
        setSampleStatus(sampleIds, FormLogic.SMPL_BOXSCAN, now);
      }
      else {
        throw new IllegalStateException(
          "User " + userId + " must be an Ardais or DI user to scan a box.");
      }

      {
        ArdaisstaffAccessBean ardStaff = new ArdaisstaffAccessBean();
        AccessBeanEnumeration staffEnum = null;

        staffEnum = (AccessBeanEnumeration) ardStaff.findLocByUserProf(userId, account);
        ardStaff = (ArdaisstaffAccessBean) staffEnum.nextElement();

        GeolocationAccessBean geoLoc = ardStaff.getGeolocation();
        GeolocationKey geoLocKey = (GeolocationKey) geoLoc.__getKey();

        // Set the Location of this box to a specific storage unit
        // based on the format
        btxDetails.setLocation(setBoxLocationPrivate(btxDetails, boxID, storageTypeCid, geoLocKey.location_address_id));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    
    return btxDetails;
  }

  private BTXDetails performSetBoxLocation(BtxDetailsSetBoxLocation btxDetails) throws Exception {
    
    String boxId = btxDetails.getBoxId();
    String storageTypeCid = btxDetails.getStorageTypeCid();
    String geoLoc = btxDetails.getGeoLoc();

    btxDetails.setLocation(setBoxLocationPrivate(btxDetails, boxId, storageTypeCid, geoLoc));

    return btxDetails;
  }

  private BTXBoxLocation setBoxLocationPrivate(
    BTXDetails btxDetails,
    String boxId,
    String storageTypeCid,
    String geoLoc) {

    BTXBoxLocation location = null;

    try {
      //SWP-985
      //if the box is currently stored somewhere at the specified site, retrieve that location so
      //the system can propose putting it back in that spot (as long as the storage type of the
      //current location matches the specified storage type
      BoxlocationAccessBean currentLocation = getCurrentBoxLocation(boxId, geoLoc);
      
      //remove the box from it's current location
      removeBoxFromLocationsPrivate(boxId);

      // Determine the new location for the box
      {
        BoxlocationAccessBean boxLoc;
        
        //SWP-985
        //if the old location has the same storage type as was requested, use the old location
        if (currentLocation != null && storageTypeCid.equalsIgnoreCase(currentLocation.getStorageTypeCid())) {
          boxLoc = currentLocation;
        }
        //otherwise, find a new location for the box
        else {
          boxLoc = new BoxlocationAccessBean();
          AccessBeanEnumeration enum1 =
            (AccessBeanEnumeration) boxLoc.findBoxLocationByStorageTypeCid(geoLoc, storageTypeCid, "Y");
          
          if (!enum1.hasMoreElements()) {
            enum1 = (AccessBeanEnumeration) boxLoc.findBoxLocationByBoxId(boxId);
            if (!enum1.hasMoreElements()) {
              btxDetails.addActionError(new BtxActionError("iltds.error.updateboxlocation.noLocationsAvailable"));
              btxDetails.setActionForwardRetry();
              return location;
            }
          }
          
          // Get default room from user preferences. If there is a match use it.
          boxLoc = (BoxlocationAccessBean) enum1.nextElement();
          UserProfileTopics prof = UserProfileTopics.load(btxDetails.getLoggedInUserSecurityInfo());
          SimpleProfile sp = prof.getSimpleProfile("room");
          if (sp != null) {
            String defaultRoom = sp.getValue();
            BoxlocationAccessBean firstLocation = boxLoc;
            boolean foundMatchInDefaultRoom = false;
            while (enum1.hasMoreElements()) {
              BoxlocationKey currentItemBoxLocKey = (BoxlocationKey) boxLoc.__getKey();
              String currentItemRoom = currentItemBoxLocKey.room_id;

              if (currentItemRoom.equalsIgnoreCase(defaultRoom)) {
                foundMatchInDefaultRoom = true;
                break;
              }
              boxLoc = (BoxlocationAccessBean) enum1.nextElement();
            }
            // If we didn't find anything in the default room, use the location that was originally
            // sorted first in the list, regardless of what room it is in.
            if (! foundMatchInDefaultRoom) {
              boxLoc = firstLocation;
            }
          }
        }

        boxLoc.setSamplebox_box_barcode_id(boxId);
        boxLoc.setAvailable_ind("N");

        BoxlocationKey boxLocKey = (BoxlocationKey) boxLoc.__getKey();
        
        location = new BTXBoxLocation();
        location.setLocationAddressID(geoLoc);
        location.setRoomID(boxLocKey.room_id);
        location.setStorageTypeCid(boxLoc.getStorageTypeCid());
        location.setDrawerID(boxLocKey.drawer_id);
        location.setSlotID(boxLocKey.slot_id);
        location.setUnitName(boxLocKey.unitName);

        boxLoc.commitCopyHelper();
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    return location;
  }

  /**
   * This returns a list of SampleAccessBean objects representing the
   * samples supplied in the Vector of sample data objects.  The resulting
   * list will have the same number of elements as the input list, in the
   * same order.  If any of the samples don't already exist, records are
   * created for them.
   * 
   * @param samples the list of samples
   * @param account the account of the user performing this transaction
   * @param createTime the timestamp to use in this routine when we need
   *     to record the current time on various records that we may write.
   * @param errorOnNonexistentSamples if true, throw an exception
   *     if any of the samples don't currently exist in the database.
   * @return the list of SampleAccessBean objects
   */
  private ArrayList createSampleTable(
    Vector samples,
    java.sql.Timestamp createTime,
    boolean errorOnNonexistentSamples,
    SecurityInfo securityInfo)
    throws Exception {

    ArrayList sampleArray = new ArrayList();

    for (int i = 0; i < samples.size(); i++) {
      SampleData sample = (SampleData)samples.get(i);
      String sampleId = sample.getSampleId();
      String sampleAlias = sample.getSampleAlias();

      try {
        SampleAccessBean myCurrentSample = new SampleAccessBean(new SampleKey(sampleId));
        sampleArray.add(myCurrentSample);
      }
      catch (ObjectNotFoundException e) {
        // The sample doesn't currently exist in the database.

        if (errorOnNonexistentSamples) {
          throw new IllegalStateException(
            "Sample id " + sampleId + " does not exist in the database.");
        }
        
        //determine if the user is allowed to create the sample
        if (!IltdsUtils.isSampleCreatableByAccount(sampleId, securityInfo)) {
          throw new IllegalStateException(
            "You are not authorized to create sample " + sampleId + ".");
        }

        //some special actions need to be taken with imported samples, so determine if
        //that's what we're dealing with        
        boolean importedSample = sampleId.length() >= 2 &&
                    ValidateIds.PREFIX_SAMPLE_IMPORTED.equalsIgnoreCase(sampleId.substring(0,2));

        //if the sample is an imported sample, don't bother with the check to see
        //if an asm exists.  It won't, and even if it did we would not be able to
        //determine the asm number from the sample id
        AsmAccessBean asm = null;
        String asmId = null;
        if (!importedSample) {
          //check to see if the ASM is created
          asmId = FormLogic.genASMEntryID(sampleId);
          try {
            asm = new AsmAccessBean(new AsmKey(asmId));
          }
          catch (ObjectNotFoundException onf) {
            // If the ASM doesn't exist we don't try to create it here, it will get
            // created when ASM Form Data entry happens.
          }
        }

        SampleAccessBean myNewSample = new SampleAccessBean();
        myNewSample.setInit_sample_barcode_id(sampleId);
        //if this is an imported sample set imported_yn to "Y", "N" otherwise
        if (importedSample) {
          myNewSample.setInit_importedYN(FormLogic.DB_YES);
        }
        else {
          myNewSample.setInit_importedYN(FormLogic.DB_NO);
        }

        if (sampleId.startsWith(ValidateIds.PREFIX_SAMPLE_FROZEN)) {
          myNewSample.setInit_sampleTypeCid(ArtsConstants.SAMPLE_TYPE_FROZEN_TISSUE);
        }
        else if (sampleId.startsWith(ValidateIds.PREFIX_SAMPLE_PARAFFIN)) {
          myNewSample.setInit_sampleTypeCid(ArtsConstants.SAMPLE_TYPE_PARAFFIN_TISSUE);
        }
        else {
          myNewSample.setInit_sampleTypeCid(ArtsConstants.SAMPLE_TYPE_UNKNOWN);
        }

        myNewSample.setAllocation_ind(null);
        //set the account on the sample.  If the user is an Ardais user working with an
        //imported sample, call a method to determine the account.  Otherwise, use the
        //account of the current user
        String account;
        if (importedSample && securityInfo.isInRoleSystemOwner()) {
          account = IltdsUtils.getAccountAssignedToSample(sampleId);
        }
        else {
          account = securityInfo.getAccount();
        }
        myNewSample.setArdais_acct_key(account);
        if (asm == null) {
          myNewSample.setAsm(null);
        }
        else {
          myNewSample.setAsm((Asm) asm.getEJBRef());
          //set the collection and preservation date/time values
          AsmformAccessBean form = new AsmformAccessBean(new AsmformKey(asm.getAsm_form_id()));
          ConsentAccessBean consent = form.getConsent();
          IltdsUtils.setSampleCollectionAndPreservationDates(form, consent, myNewSample);
        }
        if (!importedSample) {
          myNewSample.setAsm_position(FormLogic.getASMLocation(sampleId));
        }

        myNewSample.setSampleTypeCid(FormLogic.getSampleTypeCid(sampleId));

        // MR 7068 -- set ILTDS_SAMPLE.LAST_KNOWN_LOCATION_ID  
        myNewSample.setLastknownlocationid(securityInfo.getUserLocationId());
        
        //SWP-976 - set the alias on the sample
        myNewSample.setCustomerId(sampleAlias);

        myNewSample.commitCopyHelper();

        sampleArray.add(myNewSample);

        SamplestatusAccessBean status = new SamplestatusAccessBean();
        status.setInit_argSample(new SampleKey(sampleId));
        status.setInit_argStatus_type_code(FormLogic.SMPL_ASMPRESENT);
        status.setInit_argSample_status_datetime(createTime);
        status.commitCopyHelper();
                
        //give the sample a sales status of GENERELEASED if appropriate.  This is necessary
        //because the sample might be being created for an ASM linked to an already released
        //case.  This is highly unlikely to happen but the system doesn't prevent it.  
        IltdsUtils.applyPolicyToSample(myNewSample, IltdsUtils.APPLY_POLICY_FOR_SALES_STATUS);
        
        //if the user is an Ardais user creating an imported sample, set the qc status (MR8160)
        if (securityInfo.isInRoleSystemOwner() && importedSample) {
          status = new SamplestatusAccessBean();
          status.setInit_argSample(new SampleKey(sampleId));
          status.setInit_argStatus_type_code(FormLogic.SMPL_QCAWAITING);
          status.setInit_argSample_status_datetime(createTime);
          status.commitCopyHelper();
        }

        // Log the sample creation.
        //
        String importedYN = (importedSample ? FormLogic.DB_YES : FormLogic.DB_NO);
        IltdsUtils.logSampleCreation(
          securityInfo,
          sampleId,
          sampleAlias,
          importedYN,
          asmId,
          myNewSample.getAsm_position(),
          myNewSample.getArdais_acct_key());

        // Attempt to allocate the new sample.  It may or may not end up getting allocated
        // depending on whether we have all of the information we need to be able
        // to do allocation.  See the Allocation class for more details on when a sample can
        // be allocated.
        //
        Allocation.allocate(myNewSample, securityInfo);
      }
    }

    return sampleArray;
  }

  private ArrayList getSamplesInBox(String boxId) throws Exception {

    ArrayList results = new ArrayList();

    SampleAccessBean mySample = null;

    mySample = new SampleAccessBean();
    AccessBeanEnumeration sampleEnum =
      (AccessBeanEnumeration) mySample.findSampleBySamplebox(new SampleboxKey(boxId));

    while (sampleEnum.hasMoreElements()) {
      results.add(sampleEnum.nextElement());
    }

    return results;
  }

  private void orphanSamples(
    ArrayList newSamplesInBox,
    ArrayList oldSamplesInBox,
    java.sql.Timestamp timestamp)
    throws Exception {

    ArrayList mySampleOrphanTable = getOrphanedSamples(newSamplesInBox, oldSamplesInBox);

    for (int i = 0; i < mySampleOrphanTable.size(); i++) {
      SampleAccessBean sample = (SampleAccessBean) mySampleOrphanTable.get(i);
      sample.setOrphan_datetime(timestamp);
      sample.setSamplebox(null);
      sample.setCell_ref_location(null);
      sample.commitCopyHelper();
    }
  }

  /**
   * Return a list of the samples that are in the box currently but that
   * will not be in the box after the box scan.
   * 
   * @param newSamplesInBox the list of samples that will be in the
   *     box after the box scan
   * @param oldSamplesInBox the list of samples that are currently in the
   *     box.
   * @return the list of samples that are in oldSamplesInBox but not in
   *     newSamplesInBox.
   */
  private ArrayList getOrphanedSamples(ArrayList newSamplesInBox, ArrayList oldSamplesInBox)
    throws Exception {

    ArrayList sampleTable = new ArrayList();

    for (int i = 0; i < oldSamplesInBox.size(); i++) {
      SampleAccessBean currentBoxSample = (SampleAccessBean) oldSamplesInBox.get(i);
      String boxID = ((SampleKey) currentBoxSample.__getKey()).sample_barcode_id;

      boolean foundSample = false;

      for (int j = 0; j < newSamplesInBox.size(); j++) {
        SampleAccessBean currentSample = (SampleAccessBean) newSamplesInBox.get(j);
        String currentID = ((SampleKey) currentSample.__getKey()).sample_barcode_id;

        if (boxID.equals(currentID)) {
          foundSample = true;
          break;
        }
      }

      if (!foundSample) {
        sampleTable.add(currentBoxSample);
      }
    }

    return sampleTable;
  }

  private void removeBoxFromLocationsPrivate(String boxId) throws Exception {
    removeBoxFromLocationsPrivate(boxId, null);
  }

  private void removeBoxFromLocationsPrivate(String boxId, String expectedLocationAddressId)
    throws Exception {

    ArrayList boxLocationTable = getBoxLocations(boxId);

    for (int i = 0; i < boxLocationTable.size(); i++) {
      BoxlocationAccessBean box = (BoxlocationAccessBean) boxLocationTable.get(i);
      if ((expectedLocationAddressId == null)
        || !box.getGeolocation_location_address_id().equals(expectedLocationAddressId)) {
        box.setSamplebox_box_barcode_id(null);
        box.setAvailable_ind("Y");
        box.commitCopyHelper();
      }
    }
  }
  
  private BoxlocationAccessBean getCurrentBoxLocation(String boxId, String locationAddressId) {
    BoxlocationAccessBean returnValue = null;
    
    ArrayList boxLocationTable = getBoxLocations(boxId);
    try {
      for (int i = 0; i < boxLocationTable.size() && returnValue == null; i++) {
        BoxlocationAccessBean boxLocation = (BoxlocationAccessBean) boxLocationTable.get(i);
        if (boxLocation.getGeolocation_location_address_id().equals(locationAddressId)) {
          returnValue = boxLocation;
        }
      }
    }
    catch (Exception e) {
      returnValue = null;
    }
    return returnValue;
  }

  private ArrayList getBoxLocations(String boxId) {
    ArrayList myBoxLocationTable = new ArrayList();

    try {
      BoxlocationAccessBean myBoxLocation = new BoxlocationAccessBean();
      try {
        AccessBeanEnumeration locationEnum =
          (AccessBeanEnumeration) myBoxLocation.findBoxlocationBySamplebox(new SampleboxKey(boxId));
        while (locationEnum.hasMoreElements()) {
          myBoxLocationTable.add((BoxlocationAccessBean) locationEnum.nextElement());
        }
      }
      catch (ObjectNotFoundException ex) {
        // No locations found, nothing to do here.
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    return myBoxLocationTable;
  }

  private SampleboxAccessBean getSampleBox(String boxId) throws Exception {
    SampleboxAccessBean sampleBox = null;

    try {
      sampleBox = new SampleboxAccessBean(new SampleboxKey(boxId));
    }
    catch (ObjectNotFoundException e) {
      sampleBox = new SampleboxAccessBean();
      sampleBox.setInit_box_barcode_id(boxId);
      sampleBox.commitCopyHelper();
    }

    return sampleBox;
  }

  private void setSampleStatus(Vector samples, String statusCd, java.sql.Timestamp dateTime)
    throws Exception {

    for (int i = 0; i < samples.size(); i++) {
      SamplestatusAccessBean sampleStatus = new SamplestatusAccessBean();
      sampleStatus.setInit_argSample(new SampleKey((String) samples.get(i)));
      sampleStatus.setInit_argStatus_type_code(statusCd);
      sampleStatus.setInit_argSample_status_datetime(dateTime);
      sampleStatus.commitCopyHelper();
    }
  }
}
