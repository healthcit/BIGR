package com.ardais.bigr.iltds.op;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.ejb.ObjectNotFoundException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.ardais.bigr.iltds.btx.BTXDetailsBoxScan;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateBox;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.orm.helpers.BoxScanData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.WebUtils;
import com.gulfstreambio.bigr.id.SampleIdService;

public class BoxScanInsert extends StandardOperation {
  private static final String LAYOUT_RETRY_PATH = "/hiddenJsps/iltds/storage/boxScan.jsp";
  private static final String STORAGE_TYPE_RETRY_PATH = "/hiddenJsps/iltds/storage/boxScanConfirm.jsp";
  private static final String RETRY_PATH = "/hiddenJsps/iltds/storage/boxScanDetails.jsp";
  private String _errorType;

  public BoxScanInsert(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }

  public void invoke() throws Exception {
    java.sql.Timestamp btxTimestampStart = new java.sql.Timestamp(System.currentTimeMillis());

    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
    
    //Check if format needed to be specified and if so make sure it was
    String mustSpecifyStorageType = request.getParameter("mustSpecifyStorageType");
    String storageTypeCid = null;
    if (FormLogic.DB_YES.equalsIgnoreCase(mustSpecifyStorageType)) {
      storageTypeCid = ApiFunctions.safeString(request.getParameter("storageTypeCid"));
      if (ApiFunctions.isEmpty(storageTypeCid)) {
        _errorType = "storageType";
        retry("Please specify the storage type to be used on the samples in this box.");
        return;
      }
    }

    // Check if box layout is valid for this account.
    String boxLayoutId = request.getParameter("boxLayoutId");
    boxLayoutId = BoxLayoutUtils.checkBoxLayoutId(boxLayoutId, false, true, true);
    if (!ApiFunctions.isEmpty(boxLayoutId)) {
      String accountId = securityInfo.getAccount();
      if (!BoxLayoutUtils.isValidBoxLayoutForAccount(boxLayoutId, accountId)) {
        // The box layout id is not part of the account box layout group.
        _errorType = "layout";
        retry("The box layout selected does not belong to this account.");
        return;
      }
    }
    BoxLayoutDto boxLayoutDto = BoxLayoutUtils.getBoxLayoutDto(boxLayoutId);

    // Assuming all error checking in previous ops (e.g. BoxScanDetails)
    // then just collect all of the samples and their locations.  We're
    // just blindly building a list of samples here, any validation
    // checks will be done later.
    //
    Vector samples = new Vector();
    Vector sampleIds = new Vector();
    Vector cellRefs = new Vector();
    for (int i = 0; i < boxLayoutDto.getBoxCapacity(); i++) {
      String sampleID = ApiFunctions.safeString(request.getParameter("smpl" + (i + 1))).trim();
      if (!ApiFunctions.isEmpty(sampleID)) {
        SampleData sample = getSampleInfo(sampleID, securityInfo);
        samples.add(sample);
        sampleIds.add(sample.getSampleId());
        cellRefs.add(String.valueOf(i + 1));
      }
    }

    String boxId = request.getParameter("boxID");

    //get the old boxes for the samples before they are removed from the box.
    List oldBoxIds = IltdsUtils.getSampleBoxIds(sampleIds, boxId);

    // Get a default location.
    BtxDetailsCreateBox createBoxDetail = new BtxDetailsCreateBox();
    createBoxDetail.setLoggedInUserSecurityInfo(securityInfo);
    createBoxDetail.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    createBoxDetail.setTransactionType("iltds_box_createBox");

    createBoxDetail.setBoxId(boxId);
    createBoxDetail.setBoxLayoutDto(boxLayoutDto);
    createBoxDetail.setSamples(samples);
    createBoxDetail.setCellRefs(cellRefs);
    createBoxDetail.setStorageTypeCid(storageTypeCid);

    BtxDetailsCreateBox resultDetail = (BtxDetailsCreateBox) Btx.perform(createBoxDetail);

    Vector emptyBoxes = IltdsUtils.processEmptyBoxLocations(oldBoxIds, sampleIds, securityInfo.getUsername());

    request.setAttribute("emptyBoxes", emptyBoxes);

    BTXBoxLocation location = resultDetail.getLocation();
    if (location == null) {
      _errorType = "noLocations";
      retry("There are no available locations for this type of box.");
      return;
    }

    request.setAttribute("boxID", boxId);
    request.setAttribute("location", location.getLocationAddressID());
    request.setAttribute("storageType", location.getStorageTypeDesc());
    request.setAttribute("room", location.getRoomID());
    request.setAttribute("freezer", location.getUnitName());
    request.setAttribute("drawer", location.getDrawerID());
    request.setAttribute("slot", location.getSlotID());
    
    // update the checkout date for empty boxes and make lists of the
    // empty box ids and locations for storage as btxDetails result
    // properties.
    //
    IdList emptyBoxList = new IdList();
    List emptyBoxLocationList = new ArrayList();
    if ((emptyBoxes != null) && (emptyBoxes.size() != 0)) {
      for (int i = 0; i < emptyBoxes.size(); i++) {
        Vector emptyBox = (java.util.Vector) emptyBoxes.get(i);
        String emptyBoxID = (java.lang.String) emptyBox.get(0);
        BTXBoxLocation emptyBoxLoc = (BTXBoxLocation)emptyBox.get(1);

        emptyBoxList.add(emptyBoxID);
        emptyBoxLocationList.add(emptyBoxLoc);
      }
    }

    // Record Transaction START
    try {
      BTXDetailsBoxScan btxDetails = new BTXDetailsBoxScan();
      btxDetails.setBeginTimestamp(btxTimestampStart);
      btxDetails.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));

      BoxDto boxDto = new BoxDto();
      boxDto.setBoxId(boxId);
      boxDto.setBoxLayoutId(boxLayoutId);
      boxDto.setBoxLayoutDto(boxLayoutDto);
      for (int i = 0; i < sampleIds.size(); i++) {
        boxDto.setCellContent((String) cellRefs.get(i), (String) sampleIds.get(i));
      }
      btxDetails.setBox(boxDto);

      btxDetails.setBoxLocation(location);
      btxDetails.setSourceBoxList(new IdList(oldBoxIds));
      btxDetails.setEmptyBoxList(emptyBoxList);
      btxDetails.setEmptyBoxLocationList(emptyBoxLocationList);
      btxDetails.setSamples(samples);
      btxDetails.setTransactionType("iltds_placeholder");
      Btx.perform(btxDetails);
    }
    catch (Exception e) {
    }
    // END RECORD TRANSACTION

    servletCtx.getRequestDispatcher("/hiddenJsps/iltds/storage/boxScanSubmitted.jsp").forward(
      request,
      response);
  }
  
  private SampleData getSampleInfo(String sampleIdOrAlias, SecurityInfo securityInfo) {
    String sampleAlias = null;
    
    //if the value begins with the prefix for any of our sample ids (FR, PA, SX) then
    //assume it is a sample id and try to retrieve its information
    if (IltdsUtils.isSystemSampleId(sampleIdOrAlias)) {
      try {
        SampleAccessBean sample = new SampleAccessBean(new SampleKey(sampleIdOrAlias));
        sampleAlias = sample.getCustomerId();
      }
      catch (ObjectNotFoundException e) {
        //nothing to do here - this is a new sample which will not be given an alias
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }
    
    //if the value does not begin with the prefix for any of our sample ids (FR, PA, SX) then
    //assume it is a sample alias and handle the alias as follows:
    // - since error checking was done in BoxScanDetails there is no need to worry about the
    //   alias mapping to multiple existing sample ids.
    // - since error checking was done in BoxScanDetails there is no need to worry about the alias
    //   mapping to a single existing sample id that has already been encountered.  Get the 
    //   information for the single sample to which the alias maps.
    // - if the alias corresponds to no existing sample id assume the user wishes to create a 
    //   new sample so get a new id for it.  Since error checking was done in BoxScanDetails there
    //   is no need to worry about checking the requirement of a unique sample alias.
    else {
    //pass false to the findSampleIdsFromCustomerId call, to find any samples that have been 
    //created via a box scan but have not yet been annotated.
      List existingSamples = IltdsUtils.findSamplesFromCustomerId(sampleIdOrAlias, false);
      if (existingSamples.size() == 1) {
        try {
          SampleData sample = (SampleData)existingSamples.get(0);
          sampleAlias = sample.getSampleAlias();
          sampleIdOrAlias = sample.getSampleId();
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }
      }
      else {
        //set the alias to the value the user entered and retrieve a new sample id
        sampleAlias = sampleIdOrAlias; 
        SampleIdService service = SampleIdService.getInstance();
        try {
          sampleIdOrAlias = service.generateId(securityInfo);
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }
      }
    }
    //create a sample data to hold the information
    SampleData sample = new SampleData();
    sample.setSampleId(sampleIdOrAlias);
    sample.setSampleAlias(sampleAlias);
    return sample;
  }

  private void retry(String myError) throws IOException, ServletException, Exception {
    // Get the security info.
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);

    String boxLayoutId = request.getParameter("boxLayoutId");
    boxLayoutId = BoxLayoutUtils.checkBoxLayoutId(boxLayoutId, false, true, true);
    if (!ApiFunctions.isEmpty(boxLayoutId)) {
      String accountId = securityInfo.getAccount();
      if (!BoxLayoutUtils.isValidBoxLayoutForAccount(boxLayoutId, accountId)) {
        // The box layout id is not part of the account box layout group.
        BoxScanData bsd = BoxLayoutUtils.prepareForBoxScan(securityInfo);
        boxLayoutId = bsd.getDefaultBoxLayoutId();
      }
    }
    BoxLayoutDto boxLayoutDto = BoxLayoutUtils.getBoxLayoutDto(boxLayoutId);
    request.setAttribute("boxLayoutDto", boxLayoutDto);
    
    String mustSpecifyStorageType = request.getParameter("mustSpecifyStorageType");
    if (FormLogic.DB_YES.equalsIgnoreCase(mustSpecifyStorageType)) {

      List samples = new ArrayList();
      for (int i = 0; i < boxLayoutDto.getBoxCapacity(); i++) {
        String sampleID = ApiFunctions.safeString(request.getParameter("smpl" + (i + 1))).trim();

        if (!ApiFunctions.isEmpty(sampleID)) {
          samples.add(sampleID);
        }
      }

      Set sampleTypes = IltdsUtils.getSampleTypesBySampleIds(samples);
      Set accountStorageTypes = IltdsUtils.getStorageTypesByAccount(securityInfo);
      if (!sampleTypes.isEmpty()) {
        accountStorageTypes.retainAll(IltdsUtils.getCommonStorageTypesBySampleTypes(sampleTypes));
      }

      if (accountStorageTypes.size() > 1) {
        request.setAttribute("storageTypeChoices", IltdsUtils.getStorageTypesAsLVS(accountStorageTypes));
        request.setAttribute("confirmBoxStorageType", FormLogic.DB_YES);
      }
      else {
        Iterator iterator = accountStorageTypes.iterator();
        String defaultStorageTypeCid = (String)iterator.next();
        request.setAttribute("defaultStorageTypeCid", defaultStorageTypeCid);
      }
    }

    String path;
    if (_errorType.equalsIgnoreCase("storageType")) {
      path = STORAGE_TYPE_RETRY_PATH;
    }
    else if (_errorType.equalsIgnoreCase("layout")) {
      path = LAYOUT_RETRY_PATH;
    }
    else {
      path = RETRY_PATH;
    }
    
    retry(myError, path);
  }
}
