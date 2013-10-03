package com.ardais.bigr.iltds.op;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.ObjectNotFoundException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.BoxlocationAccessBean;
import com.ardais.bigr.iltds.beans.GeolocationKey;
import com.ardais.bigr.iltds.beans.ManifestAccessBean;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.beans.SampleboxAccessBean;
import com.ardais.bigr.iltds.beans.SampleboxKey;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.orm.helpers.BoxScanData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.WebUtils;
import com.gulfstreambio.gboss.GbossFactory;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

public class BoxScanInitial extends StandardOperation {
  private static final String RETRY_PATH = "/hiddenJsps/iltds/storage/boxScan.jsp";

  public BoxScanInitial(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }

  private boolean boxLocationCheck(String boxID) throws Exception {

    // Grab the location of the box.  If the box doesn't have a location return true.
    //
    BoxlocationAccessBean location = new BoxlocationAccessBean();
    AccessBeanEnumeration enumLocation =
      (AccessBeanEnumeration) location.findBoxlocationBySamplebox(new SampleboxKey(boxID));
    if (!enumLocation.hasMoreElements())
      return true;
    location = (BoxlocationAccessBean) enumLocation.nextElement();
    GeolocationKey geolocationKey = location.getGeolocationKey();
    if (geolocationKey == null)
      return true;
    String boxLoc = geolocationKey.location_address_id;

    // Grab the location of the user.  If the user doesn't exist or their
    // location is unknown, return false (to be conservative).
    //
    String user = (String) request.getSession(false).getAttribute("user");
    String account = (String) request.getSession(false).getAttribute("account");
    ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean();
    AccessBeanEnumeration enumStaff =
      (AccessBeanEnumeration) staff.findLocByUserProf(user, account);
    if (!enumStaff.hasMoreElements())
      return false;
    staff = (ArdaisstaffAccessBean) enumStaff.nextElement();
    String userLoc = staff.getGeolocation_location_address_id();
    if (ApiFunctions.safeStringLength(userLoc) == 0)
      return false;

    //check if they are the same
    return (userLoc.equals(boxLoc));
  }

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

  private List getSamplesInBox(String boxId) throws Exception {
    SampleAccessBean mySample = new SampleAccessBean();
    AccessBeanEnumeration sampleEnum =
      (AccessBeanEnumeration) mySample.findSampleBySamplebox(new SampleboxKey(boxId));

    List results = new ArrayList();
    while (sampleEnum.hasMoreElements()) {
      results.add(sampleEnum.nextElement());
    }
    return results;
  }
  
  
  public void invoke() throws Exception {
    String myBoxID = request.getParameter("boxID");
    if (!FormLogic.validBoxID(myBoxID)) {
      retry("The box id format is invalid.");
      return;
    }
    // Make sure the box is in no manifest that has not been received
    if (boxManifestCheck(myBoxID)) {
      retry("This box is on a manifest for a shipment that hasn't been received yet.");
      return;
    }
    if (!boxLocationCheck(myBoxID)) {
      retry("This box belongs to a different location.");
      return;
    }
    // Check if box is unshipped on an open request
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
        return;
      }
      BoxLayoutDto boxLayoutDto = BoxLayoutUtils.getBoxLayoutDto(boxLayoutId);
      request.setAttribute("boxLayoutDto", boxLayoutDto);
    }
    else {
      retry("The account does not have a box layout defined. Please contact customer service.");
      return;
    }

    // Get the samples that are currently in the box, and add the sample barcodes and types 
    // as request attributes so we can display them for partial box scan.
    List samples = getSamplesInBox(myBoxID);
    int numSamples = samples.size();
    for (int i = 0; i < numSamples; i++) {
      SampleAccessBean sample = (SampleAccessBean) samples.get(i);
      String cellRef = sample.getCell_ref_location();
      String barcode = ((SampleKey) sample.__getKey()).sample_barcode_id;
      String sampleType = sample.getSampleTypeCid();
      String sampleAlias = sample.getCustomerId();
      sampleType = GbossFactory.getInstance().getDescription(sampleType);
      request.setAttribute("smpl" + cellRef, barcode);
      request.setAttribute("smplType" + cellRef, sampleType);        
      request.setAttribute("smplAlias" + cellRef, sampleAlias);        
    }
    
    // Determine if we should confirm that the selected box layout agrees with what is currently 
    // stored in the system.  We'll do this confirmation if:
    //   - There are any samples in the box, and 
    //   - We're not being directed to display an empty box, and 
    //   - We're not being directed to explicitly not confirm this (since we already did the 
    //     confirmation, which submits back to this op), and
    //   - The selected box layout does not match the layout stored in the system
    boolean confirmLayout = numSamples > 0;
    String empty = request.getParameter("empty");
    confirmLayout = confirmLayout && !"true".equals(empty);    
    String confirm = request.getParameter("confirm");
    confirmLayout = confirmLayout && !"false".equals(confirm);
    
    boolean isExpectedLayout = false;
    SampleboxAccessBean sampleBox = null;
    try {
      sampleBox = new SampleboxAccessBean(new SampleboxKey(myBoxID));
    }
    catch (ObjectNotFoundException e) {
      //intentionally do nothing
    }
    if (sampleBox != null) {
      isExpectedLayout = boxLayoutId.equals(sampleBox.getBoxLayoutId());
      request.setAttribute("systemBoxLayoutId", sampleBox.getBoxLayoutId());
    }
    confirmLayout = confirmLayout && !isExpectedLayout; 
    
    // If we're going to confirm the layout, then get all box scan data.
    if (confirmLayout) {
      BoxScanData bsd = BoxLayoutUtils.prepareForBoxScan(WebUtils.getSecurityInfo(request));
      request.setAttribute("boxScanData", bsd);      
    }

    // If the layout the user chose is not the expected layout, then set the empty attribute
    // to true, to indicate that the cells should be shown as empty.
    if (!isExpectedLayout) {
      request.setAttribute("empty", "true");
    }
    
    // Forward to the correct JSP based on whether we need to confirm the layout or not.
    String jsp = confirmLayout ? "boxScanConfirmLayout.jsp" : "boxScanDetails.jsp";
    servletCtx.getRequestDispatcher("/hiddenJsps/iltds/storage/" + jsp).forward(request,response);      
  }

  private void retry(String myError) throws IOException, ServletException, Exception {
    BoxScanData bsd = BoxLayoutUtils.prepareForBoxScan(WebUtils.getSecurityInfo(request));
    request.setAttribute("boxScanData", bsd);

    retry(myError, RETRY_PATH);
  }
}
