package com.ardais.bigr.lims.performers;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.ejb.CreateException;
import javax.ejb.ObjectNotFoundException;
import javax.naming.NamingException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.lims.beans.LimsDataValidator;
import com.ardais.bigr.lims.beans.LimsDataValidatorHome;
import com.ardais.bigr.lims.beans.LimsOperation;
import com.ardais.bigr.lims.beans.LimsOperationHome;
import com.ardais.bigr.lims.beans.SlideAccessBean;
import com.ardais.bigr.lims.btx.BTXDetailsCreateSlides;
import com.ardais.bigr.lims.btx.BTXDetailsCreateSlidesPrepare;
import com.ardais.bigr.lims.btx.BTXDetailsCreateSlidesSingle;
import com.ardais.bigr.lims.btx.BTXDetailsHistoQCSamples;
import com.ardais.bigr.lims.btx.BTXDetailsHistoQCSamplesSingle;
import com.ardais.bigr.lims.btx.BTXDetailsPathLabSlides;
import com.ardais.bigr.lims.btx.BTXDetailsPathLabSlidesSingle;
import com.ardais.bigr.lims.btx.BTXDetailsPrintSlides;
import com.ardais.bigr.lims.btx.BTXDetailsSetSlideLocations;
import com.ardais.bigr.lims.btx.BTXDetailsSetSlideLocationsSingle;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.lims.javabeans.CreateSlidesPrepareData;
import com.ardais.bigr.lims.javabeans.SlideData;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.EjbHomes;

public class BtxPerformerHistologyOperation extends BtxTransactionPerformerBase {

  // do not reference these member variables directly in the code, instead call their private get methods 
  private LimsDataValidator validator = null;
  private LimsOperation operation = null;
  
  public BtxPerformerHistologyOperation() {
    super();
  }

  private LimsDataValidator getValidator() throws NamingException, RemoteException, CreateException, ClassNotFoundException {
    if (this.validator == null) {
      LimsDataValidatorHome ldvHome = (LimsDataValidatorHome) EjbHomes.getHome(LimsDataValidatorHome.class);
      this.validator = ldvHome.create();
    }
    return this.validator;
  }
  
  private LimsOperation getOperation() throws NamingException, RemoteException, CreateException, ClassNotFoundException {
    if (this.operation == null) {
      LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
      this.operation = home.create();
    }
    return this.operation;
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

  private BTXDetails validatePerformCreateSlidesPrepare(BTXDetailsCreateSlidesPrepare btxDetails)
    throws Exception {
    String userId = btxDetails.getLimsUserId();
    if (!getValidator().isValidLimsUser(userId)) {
      return (BTXDetailsCreateSlidesPrepare) btxDetails.setActionForwardRetry(
        "lims.error.invalidLIMSUser");
    }

    //if the btxDetails operation type is to add a sample to the list of samples for
    //which slides will be created, validate the sample Id and return success if it
    //exists and retry if it does not.  If it does exist, we also return information
    //about whether the sample is requested or pulled so the UI can display this info.
    if (btxDetails.getOperationType().equals(BTXDetailsCreateSlidesPrepare.OP_TYPE_ADD_SAMPLE)) {
      //return retry if the sample doesn't exist.
      String sample = btxDetails.getCurrentSample();
      if (!getValidator().doesSampleExist(sample)) {
        BtxActionError btxError =
          new BtxActionError("lims.error.invalidSample", btxDetails.getCurrentSample());
        return (BTXDetailsCreateSlidesPrepare) btxDetails.setActionForwardRetry(btxError);
      }
    }
    return btxDetails;
  }

  //method to do the work for the preparation of slide creation
  private BTXDetailsCreateSlidesPrepare performCreateSlidesPrepare(BTXDetailsCreateSlidesPrepare btxDetails)
    throws Exception {
    //make sure the specified user is allowed to use LIMS. This will already have been done
    //by the UI, but do it here to be safe.
    //if the btxDetails operation type is to scan the user, set a successful action forward
    //and return (since we've validaated the user)
    
    if (btxDetails.getOperationType().equals(BTXDetailsCreateSlidesPrepare.OP_TYPE_SCAN_USER)) {
      return (BTXDetailsCreateSlidesPrepare) btxDetails.setActionForwardTXCompleted(
        LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
    }

    //if the btxDetails operation type is to add a sample to the list of samples for
    //which slides will be created, validate the sample Id and return success if it
    //exists and retry if it does not.  If it does exist, we also return information
    //about whether the sample is requested or pulled so the UI can display this info.
    if (btxDetails.getOperationType().equals(BTXDetailsCreateSlidesPrepare.OP_TYPE_ADD_SAMPLE)) {
      //return info about whether the sample is pulled or not.
      String sample = btxDetails.getCurrentSample();
      btxDetails.setCurrentSamplePulled(getValidator().isSamplePulled(sample));
      //return info about whether the sample is requested or not.
      btxDetails.setCurrentSampleRequested(
          getValidator().isSampleRequested(sample));
      return (BTXDetailsCreateSlidesPrepare) btxDetails.setActionForwardTXCompleted(
        LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
    }

    //if the operation type is anything other than the above, then we need to actually
    //create new slides.  So first, declare the database objects we're going to use
    Connection connection = null;
    PreparedStatement pstmt = null;
    CallableStatement cstmt = null;
    ResultSet results = null;

    //put everything inside a try/finally block so we ensure everything is closed
    //when we're done/when a problem occurs
    try {
      //get a database connection
      connection = ApiFunctions.getDbConnection();

      //create the PreparedStatement that we'll be using to retrieve the
      //existing slides for each sample.
      StringBuffer strQuery = new StringBuffer(200);
      strQuery.append("SELECT SAMPLE_BARCODE_ID, SLIDE_ID, SECTION_PROC, SECTION_LEVEL, ");
      strQuery.append("SECTION_NUMBER, DESTROY_DATE ");
      strQuery.append("FROM LIMS_SLIDE ");
      strQuery.append("WHERE SAMPLE_BARCODE_ID = ? ");
      strQuery.append("ORDER BY SLIDE_ID asc");
      pstmt = connection.prepareStatement(strQuery.toString());

      //create the CallableStatement that we'll be using to create new slides
      //for each sample.
      cstmt = connection.prepareCall("begin LIMS_GET_SLIDELABELS(?,?,?,?,?); end;");

      //loop over the sample id/number of slide pairs, getting the existing slides for 
      //each sample and generating the appropriate number of new slides for that sample.
      Iterator iterator = btxDetails.getCreateSlidesPrepareData().iterator();
      while (iterator.hasNext()) {
        CreateSlidesPrepareData cspData = (CreateSlidesPrepareData) iterator.next();

        //assume we've been given valid data to work with
        boolean validationFailed = false;

        //if the specified sample doesn't exist, update the data bean to reflect that 
        //fact (give it an empty list of slides (so the UI doesn't have to check for 
        //null when getting the slides for the sample(s) sent over) and updating the 
        //btxDetails object to contain an error.  Checking that the sample exists should 
        //already have been done by the UI, but do it here to be safe and to save the code 
        //below that relies on this being a valid sample from needing to check.
        String sampleId = cspData.getSampleId();
        boolean sampleExists = true;
        sampleExists = getValidator().doesSampleExist(sampleId);
        if (!sampleExists) {
          btxDetails.addActionError(
            new BtxActionError("lims.error.createSlidesPrepare.invalidSampleId", sampleId));
          validationFailed = true;
        }

        //if the number of slides to be created is less than 1, update the btxDetails 
        //object to contain an error.
        int numOfSlides = cspData.getNumberOfSlides();
        if (numOfSlides < 1) {
          btxDetails.addActionError(
            new BtxActionError(
              "lims.error.createSlidesPrepare.invalidNumberOfSlides",
              sampleId,
              String.valueOf(numOfSlides)));
          validationFailed = true;
        }

        //if any validation errors occured, remove the data bean from the list passed in and
        //skip this iteration of the loop.
        if (validationFailed) {
          iterator.remove();
          continue;
        }

        //initialize the List holding the slides (both existing and new) for this sample
        ArrayList slidesForSample = new ArrayList();

        //Get any existing slides for the specified sample
        pstmt.setString(1, sampleId);
        results = ApiFunctions.queryDb(pstmt, connection);
        while (results.next()) {
          SlideData slide = new SlideData();
          slide.setSampleId(results.getString("SAMPLE_BARCODE_ID"));
          slide.setSlideId(results.getString("SLIDE_ID"));
          slide.setProcedure(results.getString("SECTION_PROC"));
          slide.setCutLevel(results.getInt("SECTION_LEVEL"));
          slide.setSlideNumber(results.getInt("SECTION_NUMBER"));
          slide.setDestroyed(results.getDate("DESTROY_DATE") != null);
          slide.setNew(false);
          slidesForSample.add(slide);
        }
        ApiFunctions.close(results);

        //Get new slide label(s).  The first 2 parameters, sample id and number of slides,
        //are input parameters.  The last 3 parameters are output parameters.  The first
        //one is a string containing all the new labels (which we'll need to parse out).
        //the second and third are indicators of whether something went wrong in the call.
        //If so, the second parameter will have a value of -1 and the third will contain
        //a message indicating what the problem was.
        cstmt.setString(1, sampleId);
        cstmt.setInt(2, numOfSlides);
        cstmt.registerOutParameter(3, Types.VARCHAR);
        cstmt.registerOutParameter(4, Types.INTEGER);
        cstmt.registerOutParameter(5, Types.VARCHAR);
        cstmt.executeQuery();
        //get the 2nd output parameter to see if there were any problems in the call
        int rowCount = 0;
        Object obj = cstmt.getObject(4);
        if (obj != null) {
          rowCount = Integer.parseInt(obj.toString());
        }
        //if there was a problem, throw an exception
        if (rowCount == -1) {
          obj = cstmt.getObject(5);
          String emsg = null;
          if (obj != null) {
            emsg = obj.toString();
          }
          StringBuffer errorMsg = new StringBuffer(50);
          errorMsg.append("LIMS_GET_SLIDELABELS returned code: -1 with parameters: ");
          errorMsg.append("sample id=");
          errorMsg.append(sampleId);
          errorMsg.append(", num slides=");
          errorMsg.append(numOfSlides);
          errorMsg.append(".");
          if (emsg != null) {
            errorMsg.append(" Error from call was: ");
            errorMsg.append(emsg);
            errorMsg.append(".");
          }
          throw new ApiException(errorMsg.toString());
        }
        //otherwise parse out the new slide labels
        else {
          obj = cstmt.getObject(3);
          if (obj != null) {
            //parse out the slide ids, which start with the character S.
            //Note that the "S" is stripped away during the tokenization,
            //so we must add it back when determing the new slide id
            String slideIdList = obj.toString();
            StringTokenizer tokenizer = new StringTokenizer(slideIdList, "S");
            while (tokenizer.hasMoreTokens()) {
              String slideId = "S" + tokenizer.nextToken();
              SlideData slide = new SlideData();
              slide.setSampleId(sampleId);
              slide.setSlideId(slideId);
              slide.setCutLevel(1);
              slide.setNew(true);
              slidesForSample.add(slide);
            }
          }
        }

        //set the list of slides for this sample on the data bean
        cspData.setSlideData(slidesForSample);
      }
    }
    finally {
      //close all the database objects
      ApiFunctions.close(cstmt);
      ApiFunctions.close(pstmt);
      ApiFunctions.close(results);
      ApiFunctions.close(connection);
    }

    //set a successful action forward.  This transaction is always successful, even if
    //slide creation for individual samples we process in the loop above is not.
    return (BTXDetailsCreateSlidesPrepare) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_GENSLIDES);
  }

  private BTXDetails validatePerformCreateSlides(BTXDetailsCreateSlides btxDetails)
    throws Exception {
    //make sure the specified user is allowed to use LIMS. This will already have been done
    //by the UI, but do it here to be safe.
    String userId = btxDetails.getLimsUserId();
    LimsDataValidatorHome ldvHome = (LimsDataValidatorHome) EjbHomes.getHome(LimsDataValidatorHome.class);
    LimsDataValidator validator = ldvHome.create();
    if (!validator.isValidLimsUser(userId)) {
      return (BTXDetailsCreateSlides) btxDetails.setActionForwardRetry(
        "lims.error.invalidLIMSUser");
    }
    return btxDetails;
  }

  //method to do the work of slide creation.
  private BTXDetailsCreateSlides performCreateSlides(BTXDetailsCreateSlides btxDetails)
    throws Exception {

    //loop over the SlideData beans in the BTXDetails object passed in.  Create a 
    //BTXDetailsCreateSlidesSingle btx object and call perform to actually create
    //a slide in the database.
    Iterator slideDataIterator = btxDetails.getSlideData().iterator();
    while (slideDataIterator.hasNext()) {
      BTXDetailsCreateSlidesSingle singleBTX = new BTXDetailsCreateSlidesSingle();
      SlideData slide = (SlideData) slideDataIterator.next();
      singleBTX.setSlideData(slide);
      singleBTX.setLimsUserId(btxDetails.getLimsUserId());
      singleBTX.setBeginTimestamp(btxDetails.getBeginTimestamp());
      singleBTX.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo(), true);
      singleBTX =
        (BTXDetailsCreateSlidesSingle) Btx.perform(singleBTX, "lims_create_slides_single");
      //if any errors were returned from the btxDetails for the individual transaction, 
      //add them to the btxDetails for this parent transaction, since that is what will
      //be returned to the front end
      Iterator errors = singleBTX.getActionErrors().get();
      boolean hadErrors = false;
      while (errors.hasNext()) {
        hadErrors = true;
        btxDetails.addActionError((BtxActionError) errors.next());
      }
      //in order to indicate to the UI which slides were successfully created,
      //remove any SlideData databeans that were not successfully created.
      if (hadErrors) {
        slideDataIterator.remove();
      }
    }

    //this transaction is always successful (individual slide creation transactions
    //may not be, in which case the btxDetails passed into this method will be updated 
    //with information about why that individual transaction failed, but this parent 
    //transaction always is), so go to the success page
    return (BTXDetailsCreateSlides) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //method to create a single new slide in the database.
  //NOTE:  this method shouldn't be called directly - only the performCreateSlides method 
  //should trigger this method to be called.  We don't have a page to forward to for a 
  //single slide creation, but we do have a page for 1-n slides and that's taken care of
  //in performCreateSlides 
  private BTXDetailsCreateSlidesSingle performCreateSlidesSingle(BTXDetailsCreateSlidesSingle btxDetails)
    throws Exception {
    String histologist = btxDetails.getLimsUserId().trim();
    String sampleId = btxDetails.getSlideData().getSampleId().trim();
    String slideId = btxDetails.getSlideData().getSlideId().trim();
    String procedure = btxDetails.getSlideData().getProcedure().trim();
    int cutLevel = btxDetails.getSlideData().getCutLevel();
    int slideNumber = btxDetails.getSlideData().getSlideNumber();
    boolean validationFailed = false;

    //make sure a slide id has been specified.
    if ((slideId == null) || (slideId.equals(""))) {
      String msgKey = "lims.error.createSlidesSingle.invalidSlideId";
      btxDetails.addActionError(new BtxActionError(msgKey));
      validationFailed = true;
    }
    //make sure a valid sample id has been specified.
    boolean sampleExists = true;
    sampleExists = getValidator().doesSampleExist(sampleId);
    if (!sampleExists) {
      String msgKey = "lims.error.createSlidesSingle.invalidSampleId";
      btxDetails.addActionError(new BtxActionError(msgKey, slideId, sampleId));
      validationFailed = true;
    }
    //make sure a histologist has been specified.
    if ((histologist == null) || (histologist.equals(""))) {
      String msgKey = "lims.error.createSlidesSingle.invalidHistologist";
      btxDetails.addActionError(new BtxActionError(msgKey, slideId, sampleId, histologist));
      validationFailed = true;
    }
    //make sure a procedure has been specified.
    if ((procedure == null) || (procedure.equals(""))) {
      String msgKey = "lims.error.createSlidesSingle.invalidProcedure";
      btxDetails.addActionError(new BtxActionError(msgKey, slideId, sampleId, procedure));
      validationFailed = true;
    }
    //make sure a valid cutLevel has been specified.
    if (cutLevel < 1) {
      String msgKey = "lims.error.createSlidesSingle.invalidCutLevel";
      btxDetails.addActionError(
        new BtxActionError(msgKey, slideId, sampleId, String.valueOf(cutLevel)));
      validationFailed = true;
    }
    //make sure a valid slide number has been specified.
    if (slideNumber < 1) {
      String msgKey = "lims.error.createSlidesSingle.invalidSlideNumber";
      btxDetails.addActionError(
        new BtxActionError(msgKey, slideId, sampleId, String.valueOf(slideNumber)));
      validationFailed = true;
    }

    //if any validation errors occured, set the transaction completed value
    //to false and return the btxDetails object.
    if (validationFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    //now that we've validated that all required data has been provided, create the new slide
    SlideAccessBean slideBean = new SlideAccessBean();
    slideBean.setInit_slideId(slideId);
    slideBean.setInit_createDate(new java.sql.Timestamp(new java.util.Date().getTime()));
    slideBean.setInit_createUser(histologist);
    slideBean.setInit_sampleBarcodeId(sampleId);
    //default location to Histology lab
    slideBean.setCurrentLocation(LimsConstants.LIMS_LOCATION_HISTOLOGY);
    slideBean.setDestroyDate(null);
    slideBean.setSectionLevel(btxDetails.getSlideData().getCutLevel());
    slideBean.setSectionNumber(btxDetails.getSlideData().getSlideNumber());
    slideBean.setSectionProc(btxDetails.getSlideData().getProcedure());
    slideBean.commitCopyHelper();

    //return the btxDetails object passed in, marked as completed so logging takes place.
    return (BTXDetailsCreateSlidesSingle) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  private BTXDetails validatePerformPathLabSlides(BTXDetailsPathLabSlides btxDetails)
    throws Exception {
    //make sure the specified user is allowed to use LIMS. This will already have been done
    //by the UI, but do it here to be safe.
    String userId = btxDetails.getLimsUserId();
    if (!getValidator().isValidLimsUser(userId)) {
      return (BTXDetailsPathLabSlides) btxDetails.setActionForwardRetry(
        "lims.error.invalidLIMSUser");
    }

    //In this case, validate the slide Id.
    if (btxDetails.getOperationType().equals(BTXDetailsPathLabSlides.OP_TYPE_ADD_SLIDE)) {
      //Check whether sample exists or not.
      String slide = btxDetails.getCurrentSlide();
      if (!getValidator().doesSlideExist(slide)) {

        BtxActionError btxError =
          new BtxActionError("lims.error.invalidSlide", btxDetails.getCurrentSlide());
        return (BTXDetailsPathLabSlides) btxDetails.setActionForwardRetry(btxError);
      }
    }
    return btxDetails;
  }

  //method to do the work of slide path lab.
  private BTXDetailsPathLabSlides performPathLabSlides(BTXDetailsPathLabSlides btxDetails)
    throws Exception {

    //set a successful action forward. 
    //In this case, only validate the user Id.
    if (btxDetails.getOperationType().equals(BTXDetailsCreateSlidesPrepare.OP_TYPE_SCAN_USER)) {
      return (BTXDetailsPathLabSlides) btxDetails.setActionForwardTXCompleted(
        LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
    }

    //In this case, validate the slide Id.
    if (btxDetails.getOperationType().equals(BTXDetailsPathLabSlides.OP_TYPE_ADD_SLIDE)) {
      //get the BMS value for the slide
      String slide = btxDetails.getCurrentSlide();
      btxDetails.setCurrentBmsValue(getOperation().getBmsValueForSlide(slide));
      //set a successful action forward. 
      return (BTXDetailsPathLabSlides) btxDetails.setActionForwardTXCompleted(
        LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
    }

    //make sure there are only BMS or non-BMS slides in the list - prevent a mix
    boolean bmsFound = false;
    boolean nonBmsFound = false;
    Iterator iterator = btxDetails.getSlideData().iterator();
    while (iterator.hasNext()) {
      SlideData slide = (SlideData) iterator.next();
      if (FormLogic.DB_YES.equalsIgnoreCase(slide.getBmsYN())) {
        bmsFound = true;
      }
      else {
        nonBmsFound = true;
      }
    }
    if (bmsFound && nonBmsFound) {
      BtxActionError btxError = new BtxActionError("lims.error.pathLabSlide.cannotMixBmsNonBms");
      return (BTXDetailsPathLabSlides) btxDetails.setActionForwardRetry(btxError);
    }

    //loop over the SlideData beans in the BTXDetails object passed in.  Create a 
    //BTXDetailsPathLabSlidesSingle btx object and call performPathLabSlidesSingle
    //to actually pathlab the slide.
    ListIterator slideDataIterator = btxDetails.getSlideData().listIterator();
    while (slideDataIterator.hasNext()) {
      BTXDetailsPathLabSlidesSingle singleBTX = new BTXDetailsPathLabSlidesSingle();
      SlideData slide = (SlideData) slideDataIterator.next();
      singleBTX.setSlideData(slide);
      //note that we set the lims user id here not because it's necessary for the
      //actual act of path labing a slide, but because it's needed for logging
      //purposes
      singleBTX.setLimsUserId(btxDetails.getLimsUserId());
      singleBTX.setBeginTimestamp(btxDetails.getBeginTimestamp());
      singleBTX.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo(), true);
      singleBTX =
        (BTXDetailsPathLabSlidesSingle) Btx.perform(singleBTX, "lims_path_lab_slides_single");
      //if any errors were returned from the btxDetails for the individual transaction, 
      //add them to the btxDetails for this parent transaction, since that is what will
      //be returned to the front end
      Iterator errors = singleBTX.getActionErrors().get();
      boolean hadErrors = false;
      while (errors.hasNext()) {
        hadErrors = true;
        btxDetails.addActionError((BtxActionError) errors.next());
      }
      //in order to indicate to the UI which slides were successfully path lab'd,
      //remove any SlideData databeans that were not successfully path lab'd.
      //Otherwise, update the current SlideData object with the one returned from the
      //"single" transaction inside the loop.
      if (hadErrors) {
        slideDataIterator.remove();
      }
      else {
        slideDataIterator.set(singleBTX.getSlideData());
      }
    }

    //this transaction is always successful (individual slide path lab transactions
    //may not be, in which case the btxDetails passed into this method will be updated 
    //with information about why that individual transaction failed, but this parent 
    //transaction always is), so go to the success page
    return (BTXDetailsPathLabSlides) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_GENREPORT);
  }

  //method to path lab a single slide in the database.
  //NOTE: this method shouldn't be called directly - only the performPathLabSlides method 
  //should trigger this method to be called.  We don't have a page to forward to for a 
  //single slide path lab, but we do have a page for 1-n slides and that's taken care of
  //in performPathLabSlides 
  //NOTE: on a successful path lab, this method *MUST* set the sample id on the SlideData data
  //bean on the btxDetails object passed in.  That information is required for logging this 
  //transaction, and is not yet filled in at this point in the transaction (the UI doesn't 
  //collect it because it's not needed for any business logic in this transaction).
  private BTXDetailsPathLabSlidesSingle performPathLabSlidesSingle(BTXDetailsPathLabSlidesSingle btxDetails)
    throws Exception {
    String slideId = btxDetails.getSlideData().getSlideId().trim();
    boolean validationFailed = false;

    //make sure a valid slide id has been specified.
    boolean slideExists = true;
    slideExists = getValidator().doesSlideExist(slideId);

    if (!slideExists) {
      String msgKey = "lims.error.pathLabSlidesSingle.invalidSlideId";
      btxDetails.addActionError(new BtxActionError(msgKey, slideId));
      validationFailed = true;
    }

    //if any validation errors occured, set the transaction completed value
    //to false and return the btxDetails object.
    if (validationFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    //now that we've validated that all required data has been provided, path lab the slide
    SlideAccessBean slideBean = new SlideAccessBean(slideId);
    slideBean.setCurrentLocation(LimsConstants.LIMS_LOCATION_PATHOLOGY);
    slideBean.commitCopyHelper();

    //update the SlideData object on the btxdetails with information required by the UI (and
    //in the case of sampleId, required by the logging functionality as well)
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    StringBuffer buff = new StringBuffer(1024);
    buff.append("SELECT SLIDE.SAMPLE_BARCODE_ID, ASM.CONSENT_ID, SAMPLE.SAMPLE_TYPE_CID, ");
    buff.append("SAMPLE.BMS_YN, SLIDE.SECTION_PROC, SLIDE.SECTION_LEVEL, LOCATION.LOCATION_NAME ");
    buff.append("FROM LIMS_SLIDE SLIDE, ILTDS_ASM ASM, ILTDS_SAMPLE SAMPLE, ");
    buff.append("ILTDS_GEOGRAPHY_LOCATION LOCATION, ILTDS_INFORMED_CONSENT CONSENT ");
    buff.append("WHERE SLIDE.SLIDE_ID = ? ");
    buff.append("AND SLIDE.SAMPLE_BARCODE_ID = SAMPLE.SAMPLE_BARCODE_ID ");
    buff.append("AND SAMPLE.ASM_ID = ASM.ASM_ID ");
    buff.append("AND ASM.CONSENT_ID = CONSENT.CONSENT_ID ");
    buff.append("AND CONSENT.CONSENT_LOCATION_ADDRESS_ID = LOCATION.LOCATION_ADDRESS_ID");
    try {
      connection = ApiFunctions.getDbConnection();
      pstmt = connection.prepareStatement(buff.toString());
      pstmt.setString(1, slideId);
      results = pstmt.executeQuery();
      //if we found the information we're looking for (and we should've) update the
      //SlideData bean on the btxDetails object
      if (results.next()) {
        SlideData slideData = btxDetails.getSlideData();
        slideData.setSampleId(results.getString("SAMPLE_BARCODE_ID"));
        slideData.setBmsYN(results.getString("BMS_YN"));
        slideData.setCaseId(results.getString("CONSENT_ID"));
        slideData.setSampleTypeCid(results.getString("SAMPLE_TYPE_CID"));
        slideData.setProcedure(results.getString("SECTION_PROC"));
        slideData.setCutLevel(results.getInt("SECTION_LEVEL"));
        slideData.setDonorInstitutionName(results.getString("LOCATION_NAME"));
      }
      //otherwise, add an error to the btxDetails object.
      else {
        String msgKey = "lims.error.pathLabSlidesSingle.slideInfoRetrieveFailed";
        btxDetails.addActionError(
          new BtxActionError(
            msgKey,
            slideId,
            "Query completed successfully but returned no records.  NOTE: BTX logging will be missing sample id."));
      }
    }
    finally {
      ApiFunctions.close(connection, pstmt, results);
    }

    //return the btxDetails object passed in, marked as completed so logging takes place.

    return (BTXDetailsPathLabSlidesSingle) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  private BTXDetails validatePerformPrintSlideLabelFunctions(BTXDetailsPrintSlides btxDetails)
    throws Exception {
    //make sure the specified user is allowed to use LIMS. This will already 
    //have been done by the UI, but do it here to be safe.
    String userId = btxDetails.getLimsUserId();
    if (!getValidator().isValidLimsUser(userId)) {
      return (BTXDetailsPrintSlides) btxDetails.setActionForwardRetry("lims.error.invalidLIMSUser");
    }

    //In this case, validate the slide Id.
    if (BTXDetailsPrintSlides.OP_TYPE_ADD_SLIDE.equals(btxDetails.getOperationType())) {
      //Check whether sample exists or not.
      String slide = btxDetails.getCurrentSlide();
      if (!getValidator().doesSlideExist(slide)) {

        BtxActionError btxError =
          new BtxActionError("lims.error.invalidSlide", btxDetails.getCurrentSlide());
        return (BTXDetailsPrintSlides) btxDetails.setActionForwardRetry(btxError);
      }
    }

    return btxDetails;
  }

  /**
   * Performs some functions for Slide label printing. This method helps to go through the 
   * PrintSlideLabels transaction and to log the printing transactions.
   * @param BTXDetailsPrintSlides 
   * @return BTXDetailsPrintSlides.
   */
  private BTXDetailsPrintSlides performPrintSlideLabelFunctions(BTXDetailsPrintSlides btxDetails)
    throws Exception {
    //set a successful action forward. 
    //In this case, only validate the user Id.
    if (BTXDetailsCreateSlidesPrepare.OP_TYPE_SCAN_USER.equals(btxDetails.getOperationType())) {
      return (BTXDetailsPrintSlides) btxDetails.setActionForwardTXCompleted(
        LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
    }
    //In this case, validate the slide Id.
    if (BTXDetailsPrintSlides.OP_TYPE_ADD_SLIDE.equals(btxDetails.getOperationType())) {
      //set a successful action forward. 
      return (BTXDetailsPrintSlides) btxDetails.setActionForwardTXCompleted(
        LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
    }
    //Get the sampleIds for the slides in btxDetails. Do this operation only
    //if logging is enabled for this btxDetails type.        
    if (btxDetails.isLogged()) {
      IdList list = btxDetails.getSlidesList();
      IdList samples = new IdList();

      Iterator iter = list.iterator();
      while (iter.hasNext()) {
        String slideId = (String) iter.next();
        samples.add(getOperation().getSampleIdForSlide(slideId));
      }
      btxDetails.setSamplesList(samples);
    }
    //This transaction is always successful. This is used to just log the printing 
    //transactions using BTX framework.        
    return (BTXDetailsPrintSlides) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  private BTXDetails validatePerformSetSlideLocations(BTXDetailsSetSlideLocations btxDetails)
    throws Exception {
    //make sure the specified user is allowed to use LIMS. This will already have been done
    //by the UI, but do it here to be safe.
    String userId = btxDetails.getLimsUserId();
    if (!getValidator().isValidLimsUser(userId)) {
      return (BTXDetailsSetSlideLocations) btxDetails.setActionForwardRetry(
        "lims.error.invalidLIMSUser");
    }
    return btxDetails;
  }

  //method to do the work of setting slide locations
  private BTXDetailsSetSlideLocations performSetSlideLocations(BTXDetailsSetSlideLocations btxDetails)
    throws Exception {

    if (btxDetails.getOperationType().equals(BTXDetailsSetSlideLocations.OP_TYPE_SCAN_USER)) {
      return (BTXDetailsSetSlideLocations) btxDetails.setActionForwardTXCompleted(
        LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
    }
    else if (
      btxDetails.getOperationType().equals(BTXDetailsSetSlideLocations.OP_TYPE_SCAN_SLIDE)) {
      String slideId = btxDetails.getSlideId();
      if (!getValidator().doesSlideExist(slideId)) {
        //lims.error.invalidSlide
        BtxActionError newError =
          new BtxActionError("lims.error.invalidSlide", btxDetails.getSlideId());
        return (BTXDetailsSetSlideLocations) btxDetails.setActionForwardRetry(newError);
      }
      else {
        return (BTXDetailsSetSlideLocations) btxDetails.setActionForwardTXCompleted(
          LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
      }
    }
    else if (btxDetails.getOperationType().equals(BTXDetailsSetSlideLocations.OP_TYPE_SET_LOC)) {

      //loop over the SlideData beans in the BTXDetails object passed in.  Create a 
      //BTXDetailsSetSlideLocationsSingle btx object and call performSetSlideLocationsSingle
      //to actually set the location on the slide.
      ListIterator slideDataIterator = btxDetails.getSlideData().listIterator();
      while (slideDataIterator.hasNext()) {
        BTXDetailsSetSlideLocationsSingle singleBTX = new BTXDetailsSetSlideLocationsSingle();
        SlideData slide = (SlideData) slideDataIterator.next();
        singleBTX.setSlideData(slide);
        //note that we set the lims user id here not because it's necessary for the
        //actual act of path labing a slide, but because it's needed for logging
        //purposes
        singleBTX.setLimsUserId(btxDetails.getLimsUserId());
        singleBTX.setBeginTimestamp(btxDetails.getBeginTimestamp());
        singleBTX.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo(), true);
        singleBTX =
          (BTXDetailsSetSlideLocationsSingle) Btx.perform(
            singleBTX,
            "lims_set_slide_locations_single");
        //if any errors were returned from the btxDetails for the individual transaction, 
        //add them to the btxDetails for this parent transaction, since that is what will
        //be returned to the front end
        Iterator errors = singleBTX.getActionErrors().get();
        boolean hadErrors = false;
        while (errors.hasNext()) {
          hadErrors = true;
          btxDetails.addActionError((BtxActionError) errors.next());
        }
        //in order to indicate to the UI which slides were successfully relocated,
        //remove any SlideData databeans that were not successfully relocated.
        //Otherwise, update the current SlideData object with the one returned from the
        //"single" transaction inside the loop.
        if (hadErrors) {
          slideDataIterator.remove();
        }
        else {
          slideDataIterator.set(singleBTX.getSlideData());
        }
      }
    }
    //this transaction is always successful (individual set slide location transactions
    //may not be, in which case the btxDetails passed into this method will be updated 
    //with information about why that individual transaction failed, but this parent 
    //transaction always is), so go to the success page
    return (BTXDetailsSetSlideLocations) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  //method to set the location of a single slide in the database.
  //NOTE: this method shouldn't be called directly - only the performSetSlideLocations method 
  //should trigger this method to be called. 
  //NOTE: on a successful set location, this method *MUST* set the sample id on the SlideData data
  //bean on the btxDetails object passed in.  That information is required for logging this 
  //transaction, and is not yet filled in at this point in the transaction (the UI doesn't 
  //collect it because it's not needed for any business logic in this transaction).
  private BTXDetailsSetSlideLocationsSingle performSetSlideLocationsSingle(BTXDetailsSetSlideLocationsSingle btxDetails)
    throws Exception {
    String slideId = btxDetails.getSlideData().getSlideId().trim();
    boolean validationFailed = false;

    //if the specified slide doesn't exist, update the btxDetails object to contain 
    //an error.  Checking that the slide exists should already have been done by the 
    //UI, but do it here to be safe and to save the code below that relies on this 
    //being a valid slide from needing to check.
    boolean slideExists = true;
    slideExists = getValidator().doesSlideExist(slideId);
    if (!slideExists) {
      String msgKey = "lims.error.setSlideLocationsSingle.invalidSlideId";
      btxDetails.addActionError(new BtxActionError(msgKey, slideId));
      validationFailed = true;
    }

    //if the location is not valid, update the btxDetails 
    //object to contain an error.
    String location = btxDetails.getSlideData().getLocation();
    if (!LimsConstants.VALID_LIMS_LOCATIONS.contains(location)) {
      btxDetails.addActionError(
        new BtxActionError(
          "lims.error.setSlideLocationsSingle.invalidLocation",
          slideId,
          location));
      validationFailed = true;
    }

    //if any validation errors occured, set the transaction completed value
    //to false and return the btxDetails object.
    if (validationFailed) {
      btxDetails.addActionError(
        "message",
        new BtxActionError(
          "lims.error.setLocation.txError",
          btxDetails.getSlideData().getSlideId()));
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    //now that we've validated that all required data has been provided, set the location
    SlideAccessBean slideBean = new SlideAccessBean(slideId);
    slideBean.setCurrentLocation(location);
    //if the new location is Trash, set the destroy date to be the current date
    if (location.equals(LimsConstants.LIMS_LOCATION_TRASH)) {
      slideBean.setDestroyDate(new java.sql.Timestamp(new java.util.Date().getTime()));
    }
    //otherwise, null out the destroy date.  This is unnecessary most of the time,
    //but will allow users to "fix" the destroy date if they accidentally select
    //"Trash" and then change it to whatever they meant to select
    else {
      slideBean.setDestroyDate(null);
    }
    slideBean.commitCopyHelper();

    //update the SlideData object on the btxdetails with sample id (which is
    //required by the logging functionality)
    String sampleId = getOperation().getSampleIdForSlide(slideId);
    btxDetails.getSlideData().setSampleId(sampleId);
    btxDetails.addActionError(
      "message",
      new BtxActionError(
        "lims.message.setLocation.success",
        btxDetails.getSlideData().getSlideId()));

    //return the btxDetails object passed in, marked as completed so logging takes place.
    return (BTXDetailsSetSlideLocationsSingle) btxDetails.setActionForwardTXCompleted(
      LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
  }

  private BTXDetails validatePerformGetHistoQCSample(BTXDetailsHistoQCSamples btxDetails)
    throws Exception {
    String inputId = btxDetails.getInputId();
    SampleData sampleData = null;
    BtxActionError error = null;

    ValidateIds validId = new ValidateIds();

    // Check to see if input id is a slide id.
    if (!ApiFunctions.isEmpty(validId.validate(inputId, ValidateIds.TYPESET_SLIDE, false))) {

      // Get the sample id from the slide.
      String sampleId = getOperation().getSampleIdForSlide(inputId);
      if (sampleId != null) {

        // Get the sample data.
        sampleData = getOperation().getSampleData(sampleId);
        if (sampleData == null) {
          // Sample does not exist. Mark the transaction incomplete and return.
          error = new BtxActionError("lims.error.histoQc.sampleNotFound", inputId);
          btxDetails.addActionError("inputError", error);

          btxDetails.setActionForwardRetry();
          return btxDetails;
        }
      }
      else {
        // Slide does not exist. Mark the transaction incomplete and return.
        error = new BtxActionError("lims.error.histoQc.slideNotFound", inputId);
        btxDetails.addActionError("inputError", error);

        btxDetails.setActionForwardRetry();
        return btxDetails;
      }
    }
    else if (!ApiFunctions.isEmpty(validId.validate(inputId, ValidateIds.TYPESET_SAMPLE, false))) {
      sampleData = getOperation().getSampleData(inputId);
      if (sampleData == null) {
        // Sample does not exist. Mark the transaction incomplete and return.
        error = new BtxActionError("lims.error.histoQc.sampleNotFound", inputId);
        btxDetails.addActionError("inputError", error);

        btxDetails.setActionForwardRetry();
        return btxDetails;
      }
    }
    else {
      // Invalid input id.
      error = new BtxActionError("lims.error.histoQc.invalidSlideOrSampleFormat", inputId);
      btxDetails.addActionError("inputError", error);

      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    // Check for duplicate sample & check for parent/child combos.
    Iterator iterator = btxDetails.getSampleDataList().iterator();
    String sampleId = sampleData.getSampleId();
    String parentId = sampleData.getParentId();
    boolean validateFailed = false;

    while (iterator.hasNext()) {
      SampleData tempSampleData = (SampleData) iterator.next();
      String tempSampleId = tempSampleData.getSampleId();
      String tempParentId = tempSampleData.getParentId();

      if (sampleId.equals(tempSampleId)) {
        // Duplicate sample id.
        error = new BtxActionError("lims.error.histoQc.duplicateSample", sampleId);
        btxDetails.addActionError("inputError", error);

        validateFailed = true;
      }
      else if ((parentId != null) && (parentId.equals(tempSampleId))) {
        // Sample is a child, parent sample already exist in list.
        error = new BtxActionError("lims.error.histoQc.parentExist", sampleId, tempSampleId);
        btxDetails.addActionError("inputError", error);

        validateFailed = true;
      }
      else if ((tempParentId != null) && (tempParentId.equals(sampleId))) {
        // Sample is a parent, child sample already exist in list.
        error = new BtxActionError("lims.error.histoQc.childExist", sampleId, tempSampleId);
        btxDetails.addActionError("inputError", error);

        validateFailed = true;
      }
    }

    if (validateFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    return btxDetails;
  }

  /**
   * This method will get the sample row from the database given a valid slide 
   * or sample id. The method check to see if the input id is a valid slide id
   * or sample id. If the input id is valid then a fetch is made against the
   * database for the sample object. In the case of a slide id, the sample id
   * is derived from the slide object. In the case of a valid sample id, a
   * fetch is made to get the sample object. If the object does not exist in
   * either the slide or sample case, an error is passed back saying that the
   * object could not be found. If the input id was determined to be invalid,
   * an error is passed back saying that the id is invalid.
   */
  private BTXDetailsHistoQCSamples performGetHistoQCSample(BTXDetailsHistoQCSamples btxDetails)
    throws Exception {

    String inputId = btxDetails.getInputId();
    SampleData sampleData = null;

    // Done with the input id, set it to null.
    btxDetails.setInputId(null);

    ValidateIds validId = new ValidateIds();

    // Check to see if input id is a slide id.
    if (!ApiFunctions.isEmpty(validId.validate(inputId, ValidateIds.TYPESET_SLIDE, false))) {

      // Get the sample id from the slide.
      String sampleId = getOperation().getSampleIdForSlide(inputId);
      if (sampleId != null) {

        // Get the sample data.
        sampleData = getOperation().getSampleData(sampleId);
        if (sampleData != null) {
          sampleData.setSlidesExist("Y");
        }
      }
    }
    else if (!ApiFunctions.isEmpty(validId.validate(inputId, ValidateIds.TYPESET_SAMPLE, false))) {
      sampleData = getOperation().getSampleData(inputId);
      if (sampleData != null) {
        // Check if there are slides.
        if (getOperation().getSlidesForSample(inputId).isEmpty()) {
          sampleData.setSlidesExist("N");
        }
        else {
          sampleData.setSlidesExist("Y");
        }
      }
    }

    String sampleId = sampleData.getSampleId();
    ConsentData consentData = getOperation().getConsentData(sampleId);
    if (consentData != null) {
      sampleData.setConsentId(consentData.getConsentId());
    }

    // Add the sample data to the list.
    btxDetails.appendSampleData(sampleData);

    btxDetails.setActionForwardTXCompleted(LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
    return btxDetails;
  }

  private BTXDetails validatePerformUpdateHistoQCSamples(BTXDetailsHistoQCSamples btxDetails)
    throws Exception {
    return btxDetails;
  }

  /**
   * Loop over the sampleData beans in the BTXDetails object passed in. Create
   * a BTXDetailsHistoQCSamplesSingle btx object and call performUpdateHistoQCSamplesSingle
   * to actually update the sample.
   */
  private BTXDetailsHistoQCSamples performUpdateHistoQCSamples(BTXDetailsHistoQCSamples btxDetails)
    throws Exception {

    BtxActionError error = null;

    // Check for duplicate samples.
    List sampleDataList = btxDetails.getSampleDataList();
    if (ApiFunctions.hasDuplicates(sampleDataList)) {

      // Duplicates found, show an error on each duplicate value.
      Set duplicateSet = ApiFunctions.duplicateSet(sampleDataList);

      Iterator iterator = duplicateSet.iterator();
      while (iterator.hasNext()) {
        SampleData sampleData = (SampleData) iterator.next();
        error = new BtxActionError("lims.error.histoQc.duplicateSample", sampleData.getSampleId());
        btxDetails.addActionError("submitError", error);
      }

      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    // Get the dimensions and validate for min/max thickness.
    LegalValueSet dimensions = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_PARAFFIN_DIMENSIONS);

    // Validate changes.
    Iterator validateIterator = sampleDataList.iterator();
    boolean validateFailed = false;
    while (validateIterator.hasNext()) {
      SampleData sampleData = (SampleData) validateIterator.next();
      if (sampleData.getHistoReembedReasonCid().equals(FormLogic.OTHER_HISTO_REEMBED_REASON)
        && ApiFunctions.isEmpty(sampleData.getOtherHistoReembedReason())) {

        error =
          new BtxActionError("lims.error.histoQc.invalidOtherValue", sampleData.getSampleId());
        btxDetails.addActionError("submitError", error);

        validateFailed = true;
      }
      // Allow for a single null entry for either MIN/MAX or both.
      if (ApiFunctions.isEmpty(sampleData.getHistoMinThicknessPfinCid())
        || ApiFunctions.isEmpty(sampleData.getHistoMaxThicknessPfinCid())) {
        continue;
      }
      // Check to see if min is greater then max. Note we are comparing
      // list indexes and the list is sorted in inverse order.
      if (dimensions.indexOf(sampleData.getHistoMinThicknessPfinCid())
        < dimensions.indexOf(sampleData.getHistoMaxThicknessPfinCid())) {

        error =
          new BtxActionError("lims.error.histoQc.invalidMinMaxThickness", sampleData.getSampleId());
        btxDetails.addActionError("submitError", error);
        validateFailed = true;
      }
    }
    if (validateFailed) {
      btxDetails.setActionForwardRetry();
      return btxDetails;
    }

    ValidateIds validId = new ValidateIds();

    // Iterate through sample data list.
    Iterator iterator = sampleDataList.iterator();
    while (iterator.hasNext()) {
      BTXDetailsHistoQCSamplesSingle singleBTX = new BTXDetailsHistoQCSamplesSingle();
      SampleAccessBean sampleBean = null;
      SampleData sampleData = (SampleData) iterator.next();
      singleBTX.setSampleData(sampleData);

      // Check for valid samples.
      String sampleId = sampleData.getSampleId().trim();

      if (!ApiFunctions.isEmpty(validId.validate(sampleId, ValidateIds.TYPESET_SAMPLE, false))) {
        try {
          SampleKey key = new SampleKey(sampleId);
          sampleBean = new SampleAccessBean(key);
        }
        catch (ObjectNotFoundException onfe) {
          // Sample does not exist. Mark the transaction incomplete and return.
          error = new BtxActionError("lims.error.histoQc.sampleNotFound", sampleId);
          btxDetails.addActionError("submitError", error);
        }
      }
      else {
        // Invalid sample id.
        error = new BtxActionError("lims.error.histoQc.invalidSample", sampleId);
        btxDetails.addActionError("submitError", error);
      }

      String histoMinThickness = ApiFunctions.safeString(sampleBean.getHistoMinThicknessPfinCid());
      String histoMaxThickness = ApiFunctions.safeString(sampleBean.getHistoMaxThicknessPfinCid());
      String histoWidthAcross = ApiFunctions.safeString(sampleBean.getHistoWidthAcrossPfinCid());
      String histoReembedReason = ApiFunctions.safeString(sampleBean.getHistoReembedReasonCid());
      String otherHistoReembedReason =
        ApiFunctions.safeString(sampleBean.getOtherHistoReembedReason());
      String histoSubdividable = ApiFunctions.safeString(sampleBean.getHistoSubdividable());
      String histoNotes = ApiFunctions.safeString(sampleBean.getHistoNotes());

      if (histoMinThickness.equals(sampleData.getHistoMinThicknessPfinCid())
        && histoMaxThickness.equals(sampleData.getHistoMaxThicknessPfinCid())
        && histoWidthAcross.equals(sampleData.getHistoWidthAcrossPfinCid())
        && histoReembedReason.equals(sampleData.getHistoReembedReasonCid())
        && otherHistoReembedReason.equals(sampleData.getOtherHistoReembedReason())
        && histoSubdividable.equals(sampleData.getHistoSubdividable())
        && histoNotes.equals(sampleData.getHistoNotes())) {

        // Sample hasn't changed report informational warning.
        error = new BtxActionError("lims.error.histoQc.noChangeDetected", sampleId);
        btxDetails.addActionError("warning", error);
      }
      else {

        // Set btx fields.
        singleBTX.setBeginTimestamp(btxDetails.getBeginTimestamp());
        singleBTX.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo(), true);
        singleBTX.setSampleBean(sampleBean);

        singleBTX =
          (BTXDetailsHistoQCSamplesSingle) Btx.perform(
            singleBTX,
            "lims_update_histo_qc_samples_single");

        // If any errors were returned from the btxDetails for the individual transaction, 
        // add them to the btxDetails for this parent transaction, since that is what will
        // be returned to the front end.
        Iterator errors = singleBTX.getActionErrors().get();
        boolean hadErrors = false;
        while (errors.hasNext()) {
          hadErrors = true;
          btxDetails.addActionError("submitError", (BtxActionError) errors.next());
        }

        // In order to indicate to the UI which sample were successfully relocated,
        // remove any SampleData databeans that were not successfully updated.
        if (hadErrors) {
          iterator.remove();
        }
      }
    }

    // Transaction successfully completed.
    btxDetails.setActionForwardTXCompleted(LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
    return btxDetails;
  }

  /**
   * Validate sample passed in and update the sample bean accordingly. If the
   * sample is a parent sample, update the dimensions of it's children.
   */
  private BTXDetailsHistoQCSamplesSingle performUpdateHistoQCSamplesSingle(BTXDetailsHistoQCSamplesSingle btxDetails)
    throws Exception {

    SampleData sampleData = btxDetails.getSampleData();
    SampleAccessBean sampleBean = btxDetails.getSampleBean();

    // Check for valid samples.
    String sampleId = sampleData.getSampleId().trim();

    // Set the sample bean.
    sampleBean.setHistoMinThicknessPfinCid(sampleData.getHistoMinThicknessPfinCid());
    sampleBean.setHistoMaxThicknessPfinCid(sampleData.getHistoMaxThicknessPfinCid());
    sampleBean.setHistoWidthAcrossPfinCid(sampleData.getHistoWidthAcrossPfinCid());
    sampleBean.setHistoReembedReasonCid(sampleData.getHistoReembedReasonCid());
    sampleBean.setOtherHistoReembedReason(sampleData.getOtherHistoReembedReason());
    sampleBean.setHistoSubdividable(sampleData.getHistoSubdividable());
    sampleBean.setHistoNotes(sampleData.getHistoNotes());

    sampleBean.commitCopyHelper();

    // Apply dimension updates to child samples if sample is a parent.
    if (ApiFunctions.isEmpty(sampleBean.getParent_barcode_id())) {
      List childSamples = getOperation().getChildSampleIdsForSample(sampleId);

      Iterator iterator = childSamples.iterator();
      while (iterator.hasNext()) {
        String childSampleId = (String) iterator.next();

        {
          // SampleAccessBean will throw an ObjectNotFoundException if there's no such
          // sample, but that's a software exception that shouldn't happen, not something
          // that can be caused by user behavior.  So we let it be thrown as an exception
          // rather than reporting back to the user as a BTXActionError.
          // 
          SampleKey childSampleKey = new SampleKey(childSampleId);
          SampleAccessBean childSampleBean = new SampleAccessBean(childSampleKey);

          childSampleBean.setHistoMinThicknessPfinCid(sampleData.getHistoMinThicknessPfinCid());
          childSampleBean.setHistoMaxThicknessPfinCid(sampleData.getHistoMaxThicknessPfinCid());
          childSampleBean.setHistoWidthAcrossPfinCid(sampleData.getHistoWidthAcrossPfinCid());

          childSampleBean.commitCopyHelper();
        }
      }
      IdList childIDs = new IdList(childSamples);
      btxDetails.setChildIdList(childIDs);
    }

    btxDetails.setActionForwardTXCompleted(LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
    return btxDetails;
  }

}
