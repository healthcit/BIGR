package com.ardais.bigr.iltds.performers;

import java.lang.reflect.Method;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.beans.BoxlocationAccessBean;
import com.ardais.bigr.iltds.beans.BoxlocationKey;
import com.ardais.bigr.iltds.beans.GeolocationKey;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.beans.SampleboxAccessBean;
import com.ardais.bigr.iltds.beans.SampleboxKey;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXDetailsUpdateBoxLocation;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.userprofile.SimpleProfile;
import com.ardais.bigr.userprofile.UserProfileTopics;
import com.ardais.bigr.util.EjbHomes;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

public class BtxPerformerUpdateBoxLocation extends BtxTransactionPerformerBase {

  public BtxPerformerUpdateBoxLocation() {
    super();
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
  protected BTXDetails invokeBtxEntryPoint(Method method, BTXDetails btxDetails) throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }

  private ListGenerator getListGenerator() throws Exception {
    ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
    ListGenerator list = home.create();
    return list;
  }
  
  /**
   * This is the main BTX entry point for performing the Update BoxDto Location
   * business transaction.
   */
  private BTXDetails performUpdateBoxLocation(BTXDetailsUpdateBoxLocation btxDetails)
    throws Exception {

    ListGenerator list = null;
    boolean notFinished = false;

    String boxID = btxDetails.getBoxId();
    String update = btxDetails.getUpdatePart();

    BTXBoxLocation newBoxLoc = btxDetails.getNewBoxLocation();
    String newLocation = newBoxLoc.getLocationAddressID();
    String room = newBoxLoc.getRoomID();
    String freezer = newBoxLoc.getUnitName();
    String drawer = newBoxLoc.getDrawerID();
    String slot = newBoxLoc.getSlotID();
    String storageTypeCid = newBoxLoc.getStorageTypeCid();

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    // MR 7186: If a room is specified, store it as the last-used room in the user's profile.
    if (!ApiFunctions.isEmpty(room)) {
      UserProfileTopics prof = UserProfileTopics.load(securityInfo);
      SimpleProfile sp = prof.getSimpleProfile("room");
      if (sp != null) {
        if (!room.equalsIgnoreCase(sp.getValue())) {
          sp.setValue(room);
          prof.persist(securityInfo);
        }
      }
      else {
        sp = new SimpleProfile();
        sp.setKey("room");
        sp.setValue(room);
        prof.addSimpleProfile("room", sp);
        prof.persist(securityInfo);
      }
    }
    
    Set commonStorageTypes = IltdsUtils.getCommonStorageTypesByAccountAndBoxId(btxDetails.getLoggedInUserSecurityInfo(), boxID);
    LegalValueSet storageTypes = IltdsUtils.getStorageTypesAsLVS(commonStorageTypes);
    btxDetails.setStorageTypes(storageTypes);

    if ((update != null && update.equals("storageType")) || room == null || room.equals("")) {
      //We have to get the room list.  shouldn't happen, but just in case.
      if (list == null) list = getListGenerator();
      btxDetails.setRoomList(
        list.findNextLocationDropDown(newLocation, null, null, null, storageTypeCid));
      btxDetails.setUnitList(null);
      btxDetails.setDrawerList(null);
      btxDetails.setSlotList(null);
      notFinished = true;
    }
    else if ((update != null && update.equals("room")) || freezer == null || freezer.equals("")) {
      //we have to get the storage unit list
      if (list == null) list = getListGenerator();
      btxDetails.setUnitList(
        list.findNextLocationDropDown(newLocation, room, null, null, storageTypeCid));
      btxDetails.setDrawerList(null);
      btxDetails.setSlotList(null);
      notFinished = true;
    }
    else if ((update != null && update.equals("freezer")) || drawer == null || drawer.equals("")) {
      //We have to get the drawer list
      if (list == null) list = getListGenerator();
      btxDetails.setDrawerList(
        list.findNextLocationDropDown(newLocation, room, freezer, null, storageTypeCid));
      btxDetails.setSlotList(null);
      notFinished = true;
    }
    else if ((update != null && update.equals("drawer")) || slot == null || slot.equals("")) {
      //We need to get the slot list.   
      if (list == null) list = getListGenerator();
      btxDetails.setSlotList(
        list.findNextLocationDropDown(newLocation, room, freezer, drawer, storageTypeCid));
      notFinished = true;
    }

    // If we have a value for all 5 fields, set the location, otherwise continue
    // getting user input on the new location.
    //
    if (notFinished) {
      return btxDetails.setActionForwardTXIncomplete("needMoreInput");
    }
    else {
      // We're finished getting location input, record the location update.

      BoxlocationAccessBean boxLoc =
        new BoxlocationAccessBean(
          new BoxlocationKey(drawer, room, slot, freezer, new GeolocationKey(newLocation)));

      if (boxLoc == null) {
        //the location does not exist.
        return btxDetails.setActionForwardRetry("iltds.error.updateboxlocation.invalidlocation");
      }
      else if (boxLoc.getSamplebox_box_barcode_id() != null) {
        //there is already a box in the location.
        return btxDetails.setActionForwardRetry(
          "iltds.error.updateboxlocation.unavailablelocation");
      }
      else {
        // No more validations to do, update the box location.

        BoxlocationAccessBean oldLoc = new BoxlocationAccessBean();
        AccessBeanEnumeration table = null;
        table = (AccessBeanEnumeration) oldLoc.findBoxLocationByBoxId(boxID);
        if (table.hasMoreElements()) {
          oldLoc = (BoxlocationAccessBean) table.nextElement();
          oldLoc.setSamplebox_box_barcode_id(null);
          oldLoc.setAvailable_ind("Y");
          oldLoc.commitCopyHelper();

          BoxlocationKey oldLocKey = (BoxlocationKey) oldLoc.getEJBRef().getPrimaryKey();

          BTXBoxLocation oldBoxLoc = new BTXBoxLocation();
          oldBoxLoc.setStorageTypeCid(oldLoc.getStorageTypeCid());
          oldBoxLoc.setDrawerID(oldLocKey.drawer_id);
          oldBoxLoc.setLocationAddressID(oldLoc.getGeolocation_location_address_id());
          oldBoxLoc.setRoomID(oldLocKey.room_id);
          oldBoxLoc.setSlotID(oldLocKey.slot_id);
          oldBoxLoc.setUnitName(oldLocKey.unitName);
          oldBoxLoc.setLocationAddressName(oldLoc.getGeolocation().getLocation_name());
          btxDetails.setOldBoxLocation(oldBoxLoc);
        }

        boxLoc.setSamplebox_box_barcode_id(boxID);
        boxLoc.setAvailable_ind("N");
        boxLoc.commitCopyHelper();
        
        // Update the box storage type because it could have changed.
        SampleboxKey boxKey = new SampleboxKey(boxID);
        SampleboxAccessBean boxAB = new SampleboxAccessBean(boxKey);
        boxAB.setStorageTypeCid(boxLoc.getStorageTypeCid());
        boxAB.commitCopyHelper();

        // This is an unusual case -- we return a retry action but set it to
        // indicate TRANSACTION_COMPLETED.  We do this because in this case the
        // retry is only being used to display the completion popup to the user.
        // A better approach would have been to have a separate page or page attribute
        // to indicate and report success -- so don't emulate this technique elsewhere.
        // I only do this here since this is the way this page has always worked.
        //
        btxDetails.setActionForwardRetry("iltds.error.updateboxlocation.success");
        btxDetails.setTransactionCompleted(true);
        return btxDetails;
      }
    }
  }

}
