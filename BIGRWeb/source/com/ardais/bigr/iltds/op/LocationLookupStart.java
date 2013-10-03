package com.ardais.bigr.iltds.op;

import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.BoxlocationAccessBean;
import com.ardais.bigr.iltds.beans.BoxlocationKey;
import com.ardais.bigr.iltds.beans.GeolocationAccessBean;
import com.ardais.bigr.iltds.beans.GeolocationKey;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.beans.SampleboxAccessBean;
import com.ardais.bigr.iltds.beans.SampleboxKey;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.userprofile.SimpleProfile;
import com.ardais.bigr.userprofile.UserProfileTopics;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;
import com.gulfstreambio.gboss.GbossFactory;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

/**
 * Insert the type's description here.
 * Creation date: (5/18/2001 10:47:20 AM)
 * @author: Jake Thompson
 */
public class LocationLookupStart extends com.ardais.bigr.iltds.op.StandardOperation {
  /**
   * LocationLookupStart constructor comment.
   * @param req javax.servlet.http.HttpServletRequest
   * @param res javax.servlet.http.HttpServletResponse
   * @param ctx javax.servlet.ServletContext
   */
  public LocationLookupStart(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }
  /**
   * Insert the method's description here.
   * Creation date: (2/8/2001 6:28:48 PM)
   * @return boolean
   * @param boxID java.lang.String
   */
  public boolean boxLocationCheck(String boxID) throws Exception {
    //grab the location of the box
    BoxlocationAccessBean location = new BoxlocationAccessBean();
    AccessBeanEnumeration locationEnum;
    String boxLoc;
    try {
      locationEnum =
        (AccessBeanEnumeration) location.findBoxlocationBySamplebox(new SampleboxKey(boxID));
      location = (BoxlocationAccessBean) locationEnum.nextElement();
      boxLoc = ((GeolocationKey) location.getGeolocationKey()).location_address_id;
    }
    catch (Exception e) {
      return true;
    }
    //grab the location of the user
    String user = (String) request.getSession(false).getAttribute("user");
    String account = (String) request.getSession(false).getAttribute("account");
    ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean();
    AccessBeanEnumeration staffEnum;
    String userLoc;

    try {
      staffEnum = (AccessBeanEnumeration) staff.findLocByUserProf(user, account);
      staff = (ArdaisstaffAccessBean) staffEnum.nextElement();
      userLoc = staff.getGeolocation_location_address_id();

    }
    catch (Exception e) {
      return false;
    }

    //check if they are the same
    return (userLoc.equals(boxLoc));

  }
  /**
   * Insert the method's description here.
   * Creation date: (5/18/2001 10:47:20 AM)
   */
  public void invoke() throws Exception {
    HttpSession session = request.getSession(false);

    session.removeAttribute("storageTypes");
    session.removeAttribute("roomlist");
    session.removeAttribute("freezerlist");
    session.removeAttribute("drawerlist");
    session.removeAttribute("slotlist");

    String barcodeID = request.getParameter("barcodeID").trim();
    
    if (ApiFunctions.isEmpty(barcodeID)) {
      retry("Please enter a barcode ID.");
      return;
    }

    ValidateIds validator = new ValidateIds();
    String validatedId = validator.validate(barcodeID, ValidateIds.TYPESET_SAMPLE, true);


    if (  validatedId != null  ) {   // we have found a sample id...

        try {
          SampleAccessBean sample = new SampleAccessBean(new SampleKey(validatedId.trim()));
          request.setAttribute("sampleID", validatedId);
          barcodeID = sample.getSamplebox_box_barcode_id();
          if (barcodeID == null) {
            retry("Sample has no location");
            return;
          }
          else if (!boxLocationCheck(barcodeID)) {
            retry("Sample belongs to different location.");
            return;
          }
        }
        catch (Exception e) {
          retry("Sample not found in the system.");
          return;
        }
    }

    if (barcodeID.startsWith(ValidateIds.PREFIX_BOX)) {
      //the barcode id is a box
      if (!boxLocationCheck(barcodeID)) {
        retry("Box belongs to different location.");
        return;
      }
      if (barcodeID.length() != ValidateIds.LENGTH_BOX_ID) {
        retry("Please enter a valid box ID.");
        return;
      }
      if (!FormLogic.validBoxID(barcodeID)) {
        retry("Please enter a valid box ID.");
        return;
      }

      SampleboxAccessBean box = new SampleboxAccessBean();

      //make sure the box is in the system
      try {
        box = new SampleboxAccessBean(new SampleboxKey(barcodeID));
        request.setAttribute("boxID", barcodeID);

        // MR 4805 use this path of logic to process both update box location
        // transaction and to handle override location button on ardais box scan
        String override = request.getParameter("override");
        if ((override == null) || (override.equals("N"))) {
          request.setAttribute("override", "N");
        }
        else {
          request.setAttribute("override", "Y");
        }
      }
      catch (Exception e) {
        retry("Box ID does not exist in the system.");
        return;
      }

      {
        BoxlocationAccessBean location = new BoxlocationAccessBean();
        AccessBeanEnumeration locationEnum;
        BoxlocationKey key = new BoxlocationKey();
        String currentRoom = null;
        String storageTypeCid = null;
        String storageType = "";


        //get the old location of the box
        {
          locationEnum = (AccessBeanEnumeration) location.findBoxLocationByBoxId(barcodeID);
          if (!locationEnum.hasMoreElements()) {
            retry("No location was found for the box");
            return;
          }
          location = (BoxlocationAccessBean) locationEnum.nextElement();
          key = (BoxlocationKey) location.__getKey();

          SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
          Set storageTypeChoices = IltdsUtils.getCommonStorageTypesByAccountAndBoxId(securityInfo, barcodeID);

          session.setAttribute("storageTypes", IltdsUtils.getStorageTypesAsLVS(storageTypeChoices));

          currentRoom = key.room_id;
          
          storageTypeCid = location.getStorageTypeCid();
          storageType = "";
          if (!ApiFunctions.isEmpty(storageTypeCid)) {
            storageType = GbossFactory.getInstance().getDescription(storageTypeCid);
          }
          
          request.setAttribute("storageTypeCid", storageTypeCid);
          request.setAttribute("storageType", storageType);

          request.setAttribute("location", key.geolocation_location_address_id);
          request.setAttribute("room", key.room_id);
          request.setAttribute("freezer", key.unitName);
          request.setAttribute("drawer", key.drawer_id);
          request.setAttribute("slot", key.slot_id);
        }

        Vector results = new Vector();

        String userLocation = lookupUserLocation();
        if (userLocation == null) {
          retry("Error obtaining user's location");
          return;
        }

        {
          ListGeneratorHome home = null;
          ListGenerator list = null;
          try {
            home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
            list = home.create();
            results = list.findNextLocationDropDown(userLocation, null, null, null, storageTypeCid);
          }
          catch (Exception e) {
            retry("Error obtaining new locations.");
            return;
          }
          session.setAttribute("roomlist", results);
          request.setAttribute("newLocation", userLocation);

          String defaultRoom = getDefaultRoom(results, currentRoom);
          if (!ApiFunctions.isEmpty(defaultRoom)) {
            request.setAttribute("roomlist", defaultRoom);
            session.setAttribute(
              "freezerlist",
              list.findNextLocationDropDown(userLocation, defaultRoom, null, null, storageTypeCid));
          }
        }

        if (results != null && results.size() == 0) {
          retry("The system cannot find any other valid locations for the box");
          return;
        }

        {
          BoxLayoutDto boxLayoutDto = BoxLayoutUtils.getBoxLayoutDto(box.getBoxLayoutId());
          
          int numSample = numberSamples(barcodeID);
          String buttonLabel = "";
          if (numSample == boxLayoutDto.getBoxCapacity()) {
            buttonLabel = "Full Box Expected";
          }
          else if (numSample == 1) {
            buttonLabel = "1 Sample Expected";
          }
          else
            buttonLabel = numSample + " Samples Expected";

          request.setAttribute("numSamples", buttonLabel);
        }
      }
    }
    else {
      retry("Please enter a valid Sample or Box ID.");
      return;
    }

    servletCtx.getRequestDispatcher(
      "/hiddenJsps/iltds/locationManagement/boxLocation.jsp").forward(
      request,
      response);
  }

  /**
   * Insert the method's description here.
   * Creation date: (11/1/2001 3:38:35 PM)
   * @return java.lang.String
   */
  public String lookupUserLocation() {

    ArdaisstaffAccessBean ardStaff = new ArdaisstaffAccessBean();
    AccessBeanEnumeration staffEnum;
    try {
      staffEnum =
        (AccessBeanEnumeration) ardStaff.findLocByUserProf(
          (String) request.getSession(false).getAttribute("user"),
          (String) request.getSession(false).getAttribute("account"));
      try {
        ardStaff = (ArdaisstaffAccessBean) staffEnum.nextElement();
      }
      catch (ArrayIndexOutOfBoundsException exx) {

      }

      GeolocationAccessBean geoLoc = ardStaff.getGeolocation();
      GeolocationKey geoLocKey = (GeolocationKey) geoLoc.__getKey();

      return geoLocKey.location_address_id;
    }
    catch (Exception e) {
      return null;
    }
  }
  public int numberSamples(String boxID) {

    try {
      SampleAccessBean mySample = new SampleAccessBean();
      AccessBeanEnumeration sampleEnum =
        (AccessBeanEnumeration) mySample.findSampleBySamplebox(new SampleboxKey(boxID));
      int count = 0;
      while (sampleEnum.hasMoreElements()) {
        sampleEnum.nextElement();
        count++;
      }

      return count;

    }
    catch (Exception e) {
      e.printStackTrace();
      ReportError err = new ReportError(request, response, servletCtx);
      err.setFromOp(this.getClass().getName());
      err.setErrorMessage(e.toString());
      try {
        err.invoke();
        return -1;
      }
      catch (Exception _axxx) {
      }
      return -1;
    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (1/22/2001 3:49:33 PM)
   */
  private void retry(String myError) throws Exception {
    request.setAttribute("myError", myError);
    servletCtx.getRequestDispatcher(
      "/hiddenJsps/iltds/locationManagement/barcodeLookup.jsp").forward(
      request,
      response);
  }

  private String getDefaultRoom(Vector results, String currentRoom) {
    String defaultRoom = null;

    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
    UserProfileTopics prof = UserProfileTopics.load(securityInfo);
    SimpleProfile sp = prof.getSimpleProfile("room");

    if (sp != null) {
      defaultRoom = sp.getValue();
    }

    if (ApiFunctions.isEmpty(defaultRoom)) {
      defaultRoom = currentRoom;
    }

    // If the default room stored in the user's profile isn't a value choice in the results
    // list, don't use it.
    if (!ApiFunctions.isEmpty(defaultRoom)) {
      for (int i = 0; i < results.size(); i++) {
        if (defaultRoom.equalsIgnoreCase((String) results.elementAt(i))) {
          return defaultRoom;
        }
      }
    }

    return null;
  }
}
