package com.ardais.bigr.iltds.op;

import javax.ejb.ObjectNotFoundException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.BoxlocationAccessBean;
import com.ardais.bigr.iltds.beans.GeolocationKey;
import com.ardais.bigr.iltds.beans.ManifestAccessBean;
import com.ardais.bigr.iltds.beans.SampleboxAccessBean;
import com.ardais.bigr.iltds.beans.SampleboxKey;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.orm.helpers.BoxScanData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.WebUtils;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;
/**
 * Insert the type's description here.
 * Creation date: (2/2/2001 1:03:18 PM)
 * @author: Jake Thompson
 */
public class CheckOutBoxScan extends StandardOperation {

  private static final String RETRY_PATH = "/hiddenJsps/iltds/storage/checkOutBox/checkOutBox.jsp";

  /**
   * CheckOutBoxScan constructor comment.
   * @param req javax.servlet.http.HttpServletRequest
   * @param res javax.servlet.http.HttpServletResponse
   * @param ctx javax.servlet.ServletContext
   */
  public CheckOutBoxScan(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }

  /**
   * Insert the method's description here.
   * Creation date: (2/2/2001 3:42:42 PM)
   * @return int
   * @param boxID java.lang.String
   */
  private boolean boxExists(String boxID) throws Exception {
    try {
      new SampleboxAccessBean(new SampleboxKey(boxID));
      return true;
    }
    catch (ObjectNotFoundException ex) {
      return false;
    }
  }

  /**
   * Insert the method's description here.
   * Creation date: (2/8/2001 6:28:48 PM)
   * @return boolean
   * @param boxID java.lang.String
   */
  private boolean boxLocationCheck(String boxID) throws Exception {
    //grab the location of the box
    BoxlocationAccessBean location = new BoxlocationAccessBean();
    AccessBeanEnumeration locEnum = null;
    String boxLoc;
    try {
      locEnum =
        (AccessBeanEnumeration) location.findBoxlocationBySamplebox(new SampleboxKey(boxID));
      location = (BoxlocationAccessBean) locEnum.nextElement();
      boxLoc = ((GeolocationKey) location.getGeolocationKey()).location_address_id;
    }
    catch (Exception e) {
      return true;
    }
    //grab the location of the user
    String user = (String) request.getSession(false).getAttribute("user");
    String account = (String) request.getSession(false).getAttribute("account");
    ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean();
    AccessBeanEnumeration staffEnum = null;
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
   * Creation date: (2/8/2001 6:28:48 PM)
   * @return boolean
   * @param boxID java.lang.String
   */
  private boolean boxManifestCheck(String boxID) throws Exception {
    SampleboxAccessBean sampleBox = null;
    // Check the box, if it does not exist, then it has no manifests.
    try {
      sampleBox = new SampleboxAccessBean(new SampleboxKey(boxID));
    }
    catch (ObjectNotFoundException ex) {
      return false;
    }
    ManifestAccessBean manifest = sampleBox.getManifest();
    // If there is no manifest
    if (manifest == null) {
      return false;
    }
    else {
      String shipmentStatus = manifest.getShipment_status();
      if ((FormLogic.MNFT_MFVERIFIED.equalsIgnoreCase(shipmentStatus) ||
           FormLogic.MNFT_MFRECEIVED.equalsIgnoreCase(shipmentStatus)) &&
           FormLogic.BX_CHECKEDIN.equalsIgnoreCase(sampleBox.getBox_status())) {
        return false;
      }
      else {
        return true;
      }
    }
  }

  /**
   * Insert the method's description here.
   * Creation date: (2/2/2001 1:03:18 PM)
   */
  public void invoke() throws Exception {

    String myBoxID = request.getParameter("boxID");
    // If box ID is not valid error and do not continue
    if (!FormLogic.validBoxID(myBoxID)) {
      retry("Invalid Box ID Format");
      return;
    }
    if (boxManifestCheck(myBoxID)) {
      retry("Box exists in manifest");
      return;
    }
    if (!boxLocationCheck(myBoxID)) {
      retry("Box belongs to different location.");
      return;
    }
    // Check if box is on an open request
    if (IltdsUtils.isBoxUnshippedOnOpenRequest(WebUtils.getSecurityInfo(request), myBoxID)) {
      retry("This box is on an open request and cannot be used.");
      return;
    }

    // Get the security info.
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);

    // Check if box layout is valid for this account.
    String boxLayoutId = request.getParameter("boxLayoutId");
    boxLayoutId = BoxLayoutUtils.checkBoxLayoutId(boxLayoutId, false, true, true);

    if (!ApiFunctions.isEmpty(boxLayoutId)) {
      String accountId = securityInfo.getAccount();
      if (!BoxLayoutUtils.isValidBoxLayoutForAccount(boxLayoutId, accountId)) {
        // The box layout id is not part of the account box layout group.
        retry("The box layout selected does not belong to this account.");
      }
      BoxLayoutDto boxLayoutDto = BoxLayoutUtils.getBoxLayoutDto(boxLayoutId);
      request.setAttribute("boxLayoutDto", boxLayoutDto);
    }
    else {
      retry("The account does not have a box layout defined. Please contact customer service.");
      return;
    }

    servletCtx
      .getRequestDispatcher("/hiddenJsps/iltds/storage/checkOutBox/checkOutBoxScan.jsp")
      .forward(request, response);
  }

  /**
   * Insert the method's description here.
   * Creation date: (2/2/2001 2:57:00 PM)
   * @param myError java.lang.String
   */
  private void retry(String myError) throws Exception {
    BoxScanData bsd = BoxLayoutUtils.prepareForBoxScan(WebUtils.getSecurityInfo(request));
    request.setAttribute("boxScanData", bsd);

    retry(myError, RETRY_PATH);
  }
}