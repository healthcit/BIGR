package com.ardais.bigr.iltds.op;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.btx.BTXDetailsCheckOutSamples;
import com.ardais.bigr.iltds.btx.BtxDetailsCheckOutBox;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.orm.helpers.BoxScanData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;

/**
 * Insert the type's description here.
 * Creation date: (2/2/2001 2:50:36 PM)
 * @author: Jake Thompson
 */
public class CheckOutBoxInsert extends StandardOperation {

  /**
   * CheckOutBoxInsert constructor comment.
   * @param req javax.servlet.http.HttpServletRequest
   * @param res javax.servlet.http.HttpServletResponse
   * @param ctx javax.servlet.ServletContext
   */
  public CheckOutBoxInsert(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }

  /**
   * Insert the method's description here.
   * Creation date: (2/2/2001 2:50:36 PM)
   */
  public void invoke() throws Exception {

    java.sql.Timestamp btxTimestampStart = new java.sql.Timestamp(System.currentTimeMillis());

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
    }
    BoxLayoutDto boxLayoutDto = BoxLayoutUtils.getBoxLayoutDto(boxLayoutId);

    String user = securityInfo.getUsername();
    String account = securityInfo.getAccount();

    String boxID = request.getParameter("boxID");
    String reason = request.getParameter("reason");
    String comment = request.getParameter("comment");
    String reqBy = request.getParameter("reqBy");

    if (ApiFunctions.isEmpty(reason)) {
      retry("Please enter a reason.");
      return;
    }

    //check for more than 255 characters
    if (comment != null && comment.length() > 255) {
      comment = comment.substring(0, 255);
      request.setAttribute(
        "commentLength",
        "The comments have been trimmed to 255 characters and inserted in the database.");

    }

    if (reqBy.equals("Not Specified"))
    {
      retry("Please enter a valid user for Requested By.");
      return;
    }

    // Assuming all error checking in previous ops (e.g. CheckOutBoxConfirm)
    // then just collect all of the samples and their locations.  We're
    // just blindly building a list of samples here, any validation
    // checks will be done later.
    //
    Vector samples = new Vector();
    List sampleIds = new ArrayList();
    for (int i = 0; i < boxLayoutDto.getBoxCapacity(); i++) {
      String sampleId = ApiFunctions.safeString(request.getParameter("smpl" + (i + 1))).trim();
      if (!ApiFunctions.isEmpty(sampleId)) {
        SampleData sample = getSampleInfo(sampleId, securityInfo);
        sample.setLocation(String.valueOf(i + 1));
        samples.add(sample);
        sampleIds.add(sampleId);
      }
    }


    // Check out box.
    BtxDetailsCheckOutBox checkOutBox = new BtxDetailsCheckOutBox();
    checkOutBox.setLoggedInUserSecurityInfo(securityInfo);
    checkOutBox.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    checkOutBox.setTransactionType("iltds_box_checkOutBox");

    checkOutBox.setBoxId(boxID);
    checkOutBox.setBoxLayoutDto(boxLayoutDto);
    checkOutBox.setSamples(samples);
    checkOutBox.setReason(request.getParameter("reason"));
    checkOutBox.setComment(comment);
    checkOutBox.setRequestedBy(reqBy);
    checkOutBox.setClearSampleBoxInformation(true);

    BtxDetailsCheckOutBox resultCheckOutBox = (BtxDetailsCheckOutBox)Btx.perform(checkOutBox);
    
    Vector oldBoxIds = resultCheckOutBox.getOldBoxIds();
    Vector emptyBoxes = resultCheckOutBox.getEmptyBoxes();

    request.setAttribute("emptyBoxes", emptyBoxes);

    // Record Transaction START
    {
      BTXDetailsCheckOutSamples btxDetails = new BTXDetailsCheckOutSamples();
      btxDetails.setBeginTimestamp(btxTimestampStart);
      btxDetails.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
      btxDetails.setReason(reason);
      btxDetails.setNote(comment);
      btxDetails.setRequestStaffId(reqBy);
      btxDetails.setSourceBoxList(new IdList(oldBoxIds));

      BoxDto boxDto = new BoxDto();
      boxDto.setBoxId(boxID);
      boxDto.setBoxLayoutId(boxLayoutId);
      boxDto.setBoxLayoutDto(boxLayoutDto);
      for (int i = 0; i < samples.size(); i++) {
        SampleData sample = (SampleData) samples.get(i);
        boxDto.setCellContent(sample.getLocation(), sample.getSampleId());
      }

      btxDetails.setBox(boxDto);
      btxDetails.setSamples(samples);
      btxDetails.setTransactionType("iltds_placeholder");
      Btx.perform(btxDetails);
    }
    // END RECORD TRANSACTION

    {
      request.setAttribute("reason", request.getParameter("reason"));
      request.setAttribute(
        "description",
        FormLogic.lookupCheckOutStatusOrReason(request.getParameter("reason")));

      servletCtx
        .getRequestDispatcher("/hiddenJsps/iltds/storage/checkOutBox/checkOutBoxInsert.jsp")
        .forward(request, response);
    }
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
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }
    
    //if the value does not begin with the prefix for any of our sample ids (FR, PA, SX) then
    //assume it is a sample alias and handle the alias as follows:
    // - since error checking was done in CheckOutBoxConfirm we can assume the alias maps to
    //   exactly one existing sample, so retrieve its information.  Any other situation is
    //   unexpected so throw an exception.
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
        throw new ApiException("CheckOutBoxInsert did not find one sample matching the alias " + 
            Escaper.htmlEscapeAndPreserveWhitespace(sampleIdOrAlias));
      }
    }
    //create a sample data to hold the information
    SampleData sample = new SampleData();
    sample.setSampleId(sampleIdOrAlias);
    sample.setSampleAlias(sampleAlias);
    return sample;
  }

  private void retry(String myError) throws Exception {
    request.setAttribute("myError", myError);

    {
      String account = (String) request.getSession(false).getAttribute("account");
      Vector staffNames = new Vector();
      ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
      ListGenerator list = home.create();
      staffNames = list.lookupArdaisStaffByAccountId(account);
      request.setAttribute("staff", staffNames);
    }

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

    servletCtx
      .getRequestDispatcher("/hiddenJsps/iltds/storage/checkOutBox/checkOutBoxConfirm.jsp")
      .forward(request, response);
  }
}
