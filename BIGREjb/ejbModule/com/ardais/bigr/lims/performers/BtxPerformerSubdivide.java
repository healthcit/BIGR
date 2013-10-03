package com.ardais.bigr.lims.performers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.ObjectNotFoundException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.assistants.SampleStatusAssistant;
import com.ardais.bigr.iltds.beans.Asm;
import com.ardais.bigr.iltds.beans.AsmAccessBean;
import com.ardais.bigr.iltds.beans.AsmformAccessBean;
import com.ardais.bigr.iltds.beans.AsmformKey;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.beans.SamplestatusAccessBean;
import com.ardais.bigr.iltds.bizlogic.Allocation;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.lims.beans.LimsOperation;
import com.ardais.bigr.lims.beans.LimsOperationHome;
import com.ardais.bigr.lims.btx.BTXDetailsSubdivide;
import com.ardais.bigr.lims.btx.BTXDetailsSubdivideSample;
import com.ardais.bigr.lims.btx.BTXDetailsViewSubdivide;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.EjbHomes;

/**
 * This performs the Subdivide Sample BTX business transactions.
 */
public class BtxPerformerSubdivide extends BtxTransactionPerformerBase {

  /**
   * Constructor for BtxPerformerSubdivide.
   */
  public BtxPerformerSubdivide() {
    super();
  }

  /**
   * Do BTX transaction validation for the performGetSample performer method.
   */
  private BTXDetails validatePerformGetSample(BTXDetailsSubdivideSample btxDetails) throws Exception {
    
    String inputId = btxDetails.getInputId();
    
    // Check if getting another sample row.
    if (!ApiFunctions.isEmpty(inputId)) {
      // Check if the inputId is a valid slide or sample id.
      // TODO: remove the without imported type set once imported samples can be subdivided.
      if (ValidateIds.isValid(inputId, ValidateIds.TYPESET_SAMPLE_AND_SLIDE_WITHOUT_IMPORTED, true)) {
        
        if (ValidateIds.isValid(inputId, ValidateIds.TYPESET_SLIDE, true)) {
          LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
          LimsOperation operation = home.create();
          String sampleId = operation.getSampleIdForSlide(inputId);
          
          if (ApiFunctions.isEmpty(sampleId)) {
            // Slide does not exist. Mark the transaction incomplete and return.
            btxDetails.addActionError(
              "inputError",
              new BtxActionError("lims.error.histoQc.slideNotFound", inputId));
          }
          // TODO: remove the without imported type set once imported samples can be subdivided.
          else if (!ValidateIds.isValid(sampleId, ValidateIds.TYPESET_SAMPLE_WITHOUT_IMPORTED, false)) {
            btxDetails.addActionError(
              "inputError",
              new BtxActionError("lims.error.invalidSlideDerivedFromUnsupportedSampleFormat", inputId));
            btxDetails.setInputId(null);
          }
        }
      }
      else {
        btxDetails.addActionError(
          "inputError",
          new BtxActionError("lims.error.histoQc.invalidSlideOrSampleFormat", inputId));
        btxDetails.setInputId(null);
      }
    }
    else {
      // The input id is empty.
      btxDetails.addActionError(
        "inputError",
        new BtxActionError("lims.error.histoQc.invalidInput"));
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
  private BTXDetails performGetSample(BTXDetailsSubdivideSample btxDetails) throws Exception {

    String inputId = btxDetails.getInputId();
    String sampleId = null;

    // Done with the input id, set it to null.
    btxDetails.setInputId(null);

    ValidateIds validId = new ValidateIds();

    // Check to see if inputId is a sample or slide id.
    if (!ApiFunctions.isEmpty(validId.validate(inputId, ValidateIds.TYPESET_SAMPLE, false))) {
      // If valid set the sampleId.
      sampleId = inputId;
    }
    else if (!ApiFunctions.isEmpty(validId.validate(inputId, ValidateIds.TYPESET_SLIDE, false))) {
      // Get the sample id from the slide.
      LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
      LimsOperation operation = home.create();

      sampleId = operation.getSampleIdForSlide(inputId);
      if (sampleId == null) {

        // Slide does not exist. Mark the transaction incomplete and return.
        btxDetails.addActionError(
          "inputError",
          new BtxActionError("lims.error.histoQc.slideNotFound", inputId));

        return btxDetails.setActionForwardRetry();
      }
    }
    else {
      // Invalid input id.
      btxDetails.addActionError(
        "inputError",
        new BtxActionError("lims.error.histoQc.invalidSlideOrSampleFormat", inputId));

      return btxDetails.setActionForwardRetry();
    }

    SampleAccessBean sample = null;
    List parentStatuses = null;
    try {
      // Get the access bean.
      sample = new SampleAccessBean(new SampleKey(sampleId.trim()));
      parentStatuses = getSortedSampleStatuses(sample);

      // Validate is sample is eligible for subdivison.
      String eligibilityFailureReason = checkEligibleForSubdivision(sample, parentStatuses);

      if (eligibilityFailureReason != null) {
        btxDetails.addActionError(
          "inputError",
          new BtxActionError("iltds.error.subdivide.ineligibleSample", eligibilityFailureReason));

        return btxDetails.setActionForwardRetry();
      }
    }
    catch (ObjectNotFoundException e) {
      btxDetails.addActionError(
        "inputError",
        new BtxActionError("iltds.error.subdivide.nonexistentSample"));

      return btxDetails.setActionForwardRetry();
    }

    // Done with validation. Check for any warnings.
    btxDetails.setWarningInfo(checkForWarningConditions(sample, parentStatuses));

    // Set the sample data within the detail.
    btxDetails.setParentId(sampleId);

    // Get the consent id.
    LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
    LimsOperation operation = home.create();
    ConsentData consentData = operation.getConsentData(sampleId);
    if (consentData != null) {
      btxDetails.setConsentId(consentData.getConsentId());
    }

    // Set the details.
    btxDetails.setAsmPosition(sample.getAsm_position());
    btxDetails.setDiMinThicknessPfinCid(sample.getDiMinThicknessPfinCid());
    btxDetails.setDiMaxThicknessPfinCid(sample.getDiMaxThicknessPfinCid());
    btxDetails.setDiWidthAcrossPfinCid(sample.getDiWidthAcrossPfinCid());
    btxDetails.setHistoMinThicknessPfinCid(sample.getHistoMinThicknessPfinCid());
    btxDetails.setHistoMaxThicknessPfinCid(sample.getHistoMaxThicknessPfinCid());
    btxDetails.setHistoWidthAcrossPfinCid(sample.getHistoWidthAcrossPfinCid());
    btxDetails.setParaffinFeatureCid(sample.getParaffinFeatureCid());
    btxDetails.setOtherParaffinFeature(sample.getOtherParaffinFeature());
    btxDetails.setHistoNotes(sample.getHistoNotes());

    btxDetails.setActionForwardTXCompleted(LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performSubdivide performer method.
   */
  private BTXDetails validatePerformSubdivide(BTXDetailsSubdivide btxDetails) throws Exception {

    String sampleId = btxDetails.getParentId();

    boolean validateFailed = false;

    String minThicknessCid = btxDetails.getHistoMinThicknessPfinCid();
    String maxThicknessCid = btxDetails.getHistoMaxThicknessPfinCid();
    String widthAcrossCid = btxDetails.getHistoWidthAcrossPfinCid();

    if (ApiFunctions.isEmpty(minThicknessCid)) {
      btxDetails.addActionError(
        "submitError",
        new BtxActionError("lims.error.histoQc.noHistoMinThickness"));
      validateFailed = true;
    }

    if (ApiFunctions.isEmpty(maxThicknessCid)) {
      btxDetails.addActionError(
        "submitError",
        new BtxActionError("lims.error.histoQc.noHistoMaxThickness"));
      validateFailed = true;
    }

    if (ApiFunctions.isEmpty(widthAcrossCid)) {
      btxDetails.addActionError(
        "submitError",
        new BtxActionError("lims.error.histoQc.noHistoWidthAcross"));
      validateFailed = true;
    }

    int numberOfChildren = btxDetails.getNumberOfChildren();

    if ((numberOfChildren < 2) || (numberOfChildren > 4)) {
      btxDetails.addActionError(
        "submitError",
        new BtxActionError("iltds.error.subdivide.invalidChildNumber"));
      validateFailed = true;
    }

    // Make sure min and max have a value.
    if (!ApiFunctions.isEmpty(minThicknessCid) && !ApiFunctions.isEmpty(maxThicknessCid)) {
      // Check to see if min is greater then max. Note we are comparing
      // list indexes and the list is sorted in inverse order.

      // Get the dimensions and validate for min/max thickness.
      LegalValueSet dimensions = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_PARAFFIN_DIMENSIONS);
      if (dimensions.indexOf(minThicknessCid) < dimensions.indexOf(maxThicknessCid)) {
        btxDetails.addActionError(
          "submitError",
          new BtxActionError("lims.error.histoQc.invalidMinMaxThickness", sampleId));
        validateFailed = true;
      }
    }

    // Validate the specified sample ID.  It is required, and must be a
    // well-formed sample id for an existing sample.  Also, the sample
    // must satisfy the subdivision eligibility rules.

    if (ApiFunctions.safeStringLength(sampleId) == 0) {
      btxDetails.addActionError(
        "submitError",
        new BtxActionError("iltds.error.subdivide.noSampleId"));
      validateFailed = true;
    }

    if (validateFailed) {
      return btxDetails;
    }

    ValidateIds validId = new ValidateIds();

    if (ApiFunctions.isEmpty(validId.validate(sampleId, ValidateIds.TYPESET_SAMPLE, false))) {
      btxDetails.addActionError(
        "submitError",
        new BtxActionError("iltds.error.subdivide.invalidSampleId"));

      return btxDetails;
    }

    SampleAccessBean sample = null;
    try {
      sample = new SampleAccessBean(new SampleKey(sampleId.trim()));
    }
    catch (ObjectNotFoundException e) {
      btxDetails.addActionError(
        "inputError",
        new BtxActionError("iltds.error.subdivide.nonexistentSample"));

      return btxDetails;
    }

    {
      List parentStatuses = getSortedSampleStatuses(sample);
      String eligibilityFailureReason = checkEligibleForSubdivision(sample, parentStatuses);
      if (eligibilityFailureReason != null) {
        btxDetails.addActionError(
          "submitError",
          new BtxActionError("iltds.error.subdivide.ineligibleSample", eligibilityFailureReason));

        return btxDetails;
      }
    }

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the subdivide
   * business transaction.
   */
  private BTXDetails performSubdivide(BTXDetailsSubdivide btxDetails) throws Exception {
    // Input validation is done by validatePerformSubdivide.

    String sampleId = btxDetails.getParentId();

    SampleAccessBean sample = new SampleAccessBean(new SampleKey(sampleId.trim()));

    LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
    LimsOperation operation = home.create();
    ConsentData consentData = operation.getConsentData(sampleId);
    if (consentData != null) {
      btxDetails.setConsentId(consentData.getConsentId());
    }

    // Check for any warnings.
    List parentStatuses = getSortedSampleStatuses(sample);
    btxDetails.setWarningInfo(checkForWarningConditions(sample, parentStatuses));

    // Set the parent sample fields before doing subdivision.
    sample.setHistoMinThicknessPfinCid(btxDetails.getHistoMinThicknessPfinCid());
    sample.setHistoMaxThicknessPfinCid(btxDetails.getHistoMaxThicknessPfinCid());
    sample.setHistoWidthAcrossPfinCid(btxDetails.getHistoWidthAcrossPfinCid());
    sample.setHistoNotes(btxDetails.getHistoNotes());
    sample.setHistoSubdividable("Y");
    sample.commitCopyHelper();

    // If we get here, the parameters have passed all validation checks
    // and either there were no warnings or the user has indicated to
    // proceed despite the warnings.  So now we can perform the
    // subdivision.  The doSubdivision method performs the subdivision
    // and populates the btxDetails object with attributes describing
    // the results of the subdivision, such as the ids of the children.

    doSubdivision(sample, parentStatuses, btxDetails);

    btxDetails.setActionForwardTXCompleted(LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
    return btxDetails;
  }

  /**
   * This is a BTX transaction validation method for the performViewSubdivide performer method.
   */
  private BTXDetails validatePerformViewSubdivide(BTXDetailsViewSubdivide btxDetails)
    throws Exception {

    String sampleId = ApiFunctions.safeTrim(btxDetails.getParentId());

    // We may have trimmed the parent id above, so make sure the parent id is
    // set to the id we are actually validating.
    //
    btxDetails.setParentId(sampleId);

    // Validate the specified sample ID.  It is required, and must be a
    // well-formed sample id for an existing sample.  Also, the sample
    // must satisfy the subdivision eligibility rules.

    if (ApiFunctions.isEmpty(sampleId)) {
      btxDetails.addActionError(
        "submitError",
        new BtxActionError("iltds.error.subdivide.noSampleId"));

      return btxDetails;
    }

    ValidateIds validId = new ValidateIds();

    if (ApiFunctions.isEmpty(validId.validate(sampleId, ValidateIds.TYPESET_SAMPLE, false))) {
      btxDetails.addActionError(
        "submitError",
        new BtxActionError("iltds.error.subdivide.invalidSampleId"));

      return btxDetails;
    }

    try {
      new SampleAccessBean(new SampleKey(sampleId.trim()));
    }
    catch (ObjectNotFoundException e) {
      btxDetails.addActionError(
        "inputError",
        new BtxActionError("iltds.error.subdivide.nonexistentSample"));

      return btxDetails;
    }

    return btxDetails;
  }

  /**
   * This is a BTX transaction performer method.
   */
  private BTXDetails performViewSubdivide(BTXDetailsViewSubdivide btxDetails) throws Exception {
    // btxDetails validation is done in validatePerformViewSubdivide.

    String sampleId = btxDetails.getParentId();

    SampleAccessBean sample = new SampleAccessBean(new SampleKey(sampleId));

    List parentStatuses = getSortedSampleStatuses(sample);

    LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
    LimsOperation operation = home.create();
    
    ConsentData consentData = operation.getConsentData(sampleId);
    if (consentData != null) {
      btxDetails.setConsentId(consentData.getConsentId());
    }

    // Check for any warnings.
    btxDetails.setWarningInfo(checkForWarningConditions(sample, parentStatuses));

    btxDetails.setSubdivisionTimestamp(sample.getSubdivision_date());
    btxDetails.setAsmPosition(sample.getAsm_position());

    // Get child sample relationship.
    if (ApiFunctions.isEmpty(sample.getParent_barcode_id())) { // no parent => sample not a child
      List childIdList = operation.getChildSampleIdsForSample(sampleId);
      HashMap childAsmPositions = new HashMap();

      Iterator iterator = childIdList.iterator();
      while (iterator.hasNext()) {
        String childSampleId = (String) iterator.next();

        // SampleAccessBean will throw an ObjectNotFoundException if there's no such
        // sample, but that's a software exception that shouldn't happen, not something
        // that can be caused by user behavior.  So we let it be thrown as an exception
        // rather than reporting back to the user as a BTXActionError.
        // 
        SampleKey childSampleKey = new SampleKey(childSampleId);
        SampleAccessBean childSampleBean = new SampleAccessBean(childSampleKey);

        childAsmPositions.put(childSampleId, childSampleBean.getAsm_position());
      }
      btxDetails.setChildIdList(new IdList(childIdList));
      btxDetails.setChildAsmPositions(childAsmPositions);
    }

    // Get QC in process status.
    SampleStatusAssistant qcInProcessStatus = isSampleCurrentlyQCInProcess(parentStatuses);
    if (qcInProcessStatus != null) {
      btxDetails.setQCInProcess(new java.util.Date(qcInProcessStatus.getStatusDateTime()));
    }

    // Get RnD status.
    SampleStatusAssistant rndStatus = isSampleCurrentlyRNDRequested(parentStatuses);
    if (rndStatus != null) {
      btxDetails.setRNDRequested(new java.util.Date(rndStatus.getStatusDateTime()));
    }

    btxDetails.setActionForwardTXCompleted(LimsConstants.BTX_ACTION_FORWARD_SUCCESS);
    return btxDetails;
  }

  /**
   * Create a new sample status record for the sample whose is supplied.
   * The record created will have the specified status type code and status datetime.
   *
   * @param sampleID the id of the sample to add the status to
   * @param statusCode the status type code to use
   * @param statusDateTime the status date/time to use
   */
  private void addSampleStatus(
    String sampleID,
    String statusCode,
    java.sql.Timestamp statusDateTime)
    throws Exception {
    SamplestatusAccessBean sampleStatus = new SamplestatusAccessBean();
    sampleStatus.setInit_argSample(new SampleKey(sampleID));
    sampleStatus.setInit_argStatus_type_code(statusCode);
    sampleStatus.setInit_argSample_status_datetime(statusDateTime);
    sampleStatus.commitCopyHelper();
  }

  /**
   * Determine whether the specified sample is eligible to be a subdivision parent.
   * The business rules that determine this are described in the requirements specification.
   * To summarize:
   * <ul>
   * <li>The parent sample must be a paraffin sample</li>
   * <li>The parent sample cannot have already been consumed (as indicated by the
   *     presence of a COCONSUMED status).</li>
   * <li>The parent sample cannot be the child of a previous subdivision.</li>
   * </ul>
   *
   * @param sample the sample to check for eligibility
   * @param parentStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses of the parent sample.  We assume that this list is sorted by decreasing status datetime.
   *
   * @return null if the sample is eligible for subdivision, otherwise a string that explains
   *   why the sample is not eligible.
   */
  private String checkEligibleForSubdivision(SampleAccessBean sample, List parentStatuses)
    throws Exception {
    String explanation = null;

    // The sample must be a paraffin sample.
    //
    String sampleTypeCid = sample.getSampleTypeCid();
    if (sampleTypeCid == null || (!sampleTypeCid.equals(ArtsConstants.SAMPLE_TYPE_PARAFFIN_TISSUE))) {
      explanation = "Only paraffin samples may be subdivided.";
      return explanation;
    }

    // The sample must not be the child of a prior subdivision.
    //
    String parentBarcodeId = sample.getParent_barcode_id();
    if (!ApiFunctions.isEmpty(parentBarcodeId)) {
      explanation = "It is the child of a previous subdivision and may not be subdivided again.";
      return explanation;
    }

    // The sample must not have already been consumed (either by
    // subdivision or by some other process).
    //
    SampleStatusAssistant consumedStatus = isSampleConsumed(parentStatuses);
    if (consumedStatus != null) {
      String consumedDateTime =
        DateFormat.getDateTimeInstance().format(
          new java.util.Date(consumedStatus.getStatusDateTime()));
      explanation = "It was recorded as being consumed on " + consumedDateTime;
      return explanation;
    }

    if (!FormLogic.ARDAIS_LOCATION.equalsIgnoreCase(sample.getLastknownlocationid())) {
      explanation = "The sample is not at Ardais and cannot be subdivided.";
      return explanation;
    }

    // MR 6581: Don't allow pulled samples to be subdivided.  This resolves problems
    // with the existing code where sample properties aren't inherited correctly for
    // pulled and/or discordant samples.  See the MR for details on these problems.  If
    // this constraint is ever removed, the code will need to be fixed to handle
    // inheritance of all of the pull/discordant fields correctly.
    //
    if (ApiFunctions.safeEquals("Y", sample.getPullYN())) {
      explanation =
        "The sample has been pulled and would need to be un-pulled first in order to subdivide it.";
      return explanation;
    }

    return explanation;
  }

  /**
   * Determine whether the specified sample satisfies any of the subdivision warning
   * conditions.  These conditions describe situations where the sample is allowed to
   * subdivided but the user should be warned of some exceptional situation and asked to
   * confirm that they want to proceed with the subdivision.
   *
   * The business rules that determine the warning conditions are described in the
   * requirements specification.
   *
   * If the specified sample satisfies any of the warning conditions, the supplied
   * request object is populated with attributes describing the situation.  These
   * attributes are used by the page that displays the warnings to the user and asks
   * for confirmation.  Specifically, a request attribute named "warningInfo" is set
   * to a Map whose keys are the status type codes that triggered the warning and whose
   * corresponding values are the status date/time of those statuses (using values of
   * type java.util.Date).
   *
   * @param sample the sample to check for warning conditions.
   * @param parentStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses of the parent sample.  We assume that this list is sorted by decreasing status datetime.
   *
   * @return The map of warnings or null if no warnings.
   */
  private Map checkForWarningConditions(SampleAccessBean parentSample, List parentStatuses)
    throws Exception {

    Map warningInfo = new HashMap();

    // want to present a warning if this is a BMS sample
    if (parentSample.getBms_sample().equalsIgnoreCase("Y")) {
      warningInfo.put(FormLogic.STATE_SAMPLE_BMS, parentSample.getBarcode_scan_datetime());
    }

    // Check if in repository
    if (!ApiFunctions.isEmpty(parentSample.getSamplebox_box_barcode_id())) {
      warningInfo.put(
        FormLogic.STATE_SAMPLE_IN_REPOSITORY,
        parentSample.getBarcode_scan_datetime());
    }

    // Check for current ESSHIPPED status
    SampleStatusAssistant isPresentESSHIPPED =
      findLatestStatus(parentStatuses, new String[] { FormLogic.SMPL_ESSHIPPED }, null);

    if (isPresentESSHIPPED != null) {
      warningInfo.put(
        isPresentESSHIPPED.getStatusTypeCode(),
        new java.util.Date(isPresentESSHIPPED.getStatusDateTime()));
    }

    // Check for current ESSOLD status
    SampleStatusAssistant isPresentESSOLD =
      findLatestStatus(parentStatuses, new String[] { FormLogic.SMPL_ESSOLD }, null);

    if (isPresentESSOLD != null) {
      warningInfo.put(
        isPresentESSOLD.getStatusTypeCode(),
        new java.util.Date(isPresentESSOLD.getStatusDateTime()));
    }

    return (warningInfo.isEmpty() ? null : warningInfo);
  }

  /**
   * Mark the subdivsion parent as consumed.  This method assumes that the sample 
   * has passed all validation checks and that either there were no warnings
   * or the user has indicated to proceed despite the warnings.  This is only one
   * aspect of the complete subdivision process -- the <code>doSubdivision</code> method
   * performs the complete set of tasks.
   *
   * The sample is marked as consumed by giving it a COCONSUMED status.
   *
   * @param parentSample the sample to subdivide
   * @param numberOfChildren the number of child samples to create
   * @param request the request object to populate with information about the subdivision results
   */
  private void consumeParent(SampleAccessBean parentSample) throws Exception {

    java.sql.Timestamp dateTime = new java.sql.Timestamp((new java.util.Date()).getTime());
    parentSample.setSubdivision_date(dateTime);
    parentSample.commitCopyHelper();
    parentSample.refreshCopyHelper();
    addSampleStatus(getSampleID(parentSample), FormLogic.SMPL_COCONSUMED, dateTime);
    // MR 6595 -- add QCAWAITING status for parents that have been subdivided
    addSampleStatus(getSampleID(parentSample), FormLogic.SMPL_QCAWAITING, dateTime);
  }

  /**
   * If the source statuses indicate that the source sample is currently on a pathology pick list,
   * copy a single status to the specified target status list to indicate that it too is currently
   * on a pathology pick list.  See <code>isSampleCurrentlyQCInProcess</code> for specifics
   * of when a sample is considered to be currently on a pathology pick list.
   * If this is on such a list, then the most recent status that indicates that it is is
   * copied to the target sample.
   *
   * The copied record, if any, has the same type code and status date/time as the source status record.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses of the source sample.  We assume that this list is sorted by decreasing status datetime.
   *    Only the status type code and sample status datetime fields of the <code>SampleStatusAssistant</code>
   *    objects are used.
   * @param targetStatuses statuses are copied from sourceStatuses to this list.
   *    No guarantees are made about the order in which items are inserted to the target list.
   *    The <code>SampleStatusAssistant</code> object copied from the source are reused in
   *    the target -- we don't create brand new objects.
   */
  private void copyQCInProcessIfApplicable(List sourceStatuses, List targetStatuses) {

    SampleStatusAssistant status = isSampleCurrentlyQCInProcess(sourceStatuses);

    if (status != null) {
      targetStatuses.add(status);
    }
  }

  /**
   * If the source statuses indicate that the source sample is currently on an R&D pick list,
   * copy a single status to the specified target status list to indicate that it too is currently
   * on an R&D list.  See <code>isSampleCurrentlyRNDRequested</code> for specifics
   * of when a sample is considered to be currently on an R&D pick list.
   * If this is on such a list, then the most recent status that indicates that it is is
   * copied to the target sample.
   *
   * The copied record, if any, has the same type code and status date/time as the source status record.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses of the source sample.  We assume that this list is sorted by decreasing status datetime.
   *    Only the status type code and sample status datetime fields of the <code>SampleStatusAssistant</code>
   *    objects are used.
   * @param targetStatuses statuses are copied from sourceStatuses to this list.
   *    No guarantees are made about the order in which items are inserted to the target list.
   *    The <code>SampleStatusAssistant</code> object copied from the source are reused in
   *    the target -- we don't create brand new objects.
   */
  private void copyRNDRequestIfApplicable(List sourceStatuses, List targetStatuses) {

    SampleStatusAssistant status = isSampleCurrentlyRNDRequested(sourceStatuses);

    if (status != null) {
      targetStatuses.add(status);
    }
  }
  
  /**
   * If the source statuses indicate that the source sample is currently
   * on a request, copy a single status to the specified target status
   * list to indicate that it too is currently on a request.
   * See <code>isSampleCurrentlyRequested</code> for specifics
   * of when a sample is considered to be currently on a request.
   * If it is, then the most recent status that indicates that it is is
   * copied to the target sample.
   *
   * The copied record, if any, has the same type code and status date/time
   * as the source status record.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code>
   *    objects representing the statuses of the source sample.  We assume
   *    that this list is sorted by decreasing status datetime.  Only the
   *    status type code and sample status datetime fields of the
   *    <code>SampleStatusAssistant</code> objects are used.
   * @param targetStatuses statuses are copied from sourceStatuses to this
   *    list.  No guarantees are made about the order in which items are
   *    inserted to the target list.  The <code>SampleStatusAssistant</code>
   *    object copied from the source are reused in the target -- we don't
   *    create brand new objects.
   */
  private void copyRequestedIfApplicable(List sourceStatuses, List targetStatuses) {

    SampleStatusAssistant status = isSampleCurrentlyRequested(sourceStatuses);

    if (status != null) {
      targetStatuses.add(status);
    }
  }
  
  /**
   * If the source statuses indicate that the source sample is currently
   * checked out, copy a single status to the specified target status
   * list to indicate that it too is currently checked out.
   * See <code>isSampleCurrentlyCheckedOut</code> for specifics
   * of when a sample is considered to be currently checked out.
   * If it is, then the most recent status that indicates that it is is
   * copied to the target sample.
   *
   * The copied record, if any, has the same type code and status date/time
   * as the source status record.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code>
   *    objects representing the statuses of the source sample.  We assume
   *    that this list is sorted by decreasing status datetime.  Only the
   *    status type code and sample status datetime fields of the
   *    <code>SampleStatusAssistant</code> objects are used.
   * @param targetStatuses statuses are copied from sourceStatuses to this
   *    list.  No guarantees are made about the order in which items are
   *    inserted to the target list.  The <code>SampleStatusAssistant</code>
   *    object copied from the source are reused in the target -- we don't
   *    create brand new objects.
   */
  private void copyCheckedOutIfApplicable(List sourceStatuses, List targetStatuses) {

    SampleStatusAssistant status = isSampleCurrentlyCheckedOut(sourceStatuses);

    if (status != null) {
      targetStatuses.add(status);
    }
  }

  /**
   * If the source statuses indicate that the source sample is currently
   * moved to pathology, copy a single status to the specified target status
   * list to indicate that it too is currently moved to pathology.
   * See <code>isSampleCurrentlyMovedToPath</code> for specifics
   * of when a sample is considered to be currently moved to pathology.
   * If it is, then the most recent status that indicates that it is is
   * copied to the target sample.
   *
   * The copied record, if any, has the same type code and status date/time
   * as the source status record.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code>
   *    objects representing the statuses of the source sample.  We assume
   *    that this list is sorted by decreasing status datetime.  Only the
   *    status type code and sample status datetime fields of the
   *    <code>SampleStatusAssistant</code> objects are used.
   * @param targetStatuses statuses are copied from sourceStatuses to this
   *    list.  No guarantees are made about the order in which items are
   *    inserted to the target list.  The <code>SampleStatusAssistant</code>
   *    object copied from the source are reused in the target -- we don't
   *    create brand new objects.
   */
  private void copyMoveToPathIfApplicable(List sourceStatuses, List targetStatuses) {

    SampleStatusAssistant status = isSampleCurrentlyMovedToPath(sourceStatuses);

    if (status != null) {
      targetStatuses.add(status);
    }
  }

  /**
   * If the source statuses indicate that the source sample is currently
   * checked out to R&D, copy a single status to the specified target status
   * list to indicate that it too is currently checked out to R&D.
   * See <code>isSampleCurrentlyCheckedOutRND</code> for specifics
   * of when a sample is considered to be currently checked out to R&D.
   * If it is, then the most recent status that indicates that it is is
   * copied to the target sample.
   *
   * The copied record, if any, has the same type code and status date/time
   * as the source status record.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code>
   *    objects representing the statuses of the source sample.  We assume
   *    that this list is sorted by decreasing status datetime.  Only the
   *    status type code and sample status datetime fields of the
   *    <code>SampleStatusAssistant</code> objects are used.
   * @param targetStatuses statuses are copied from sourceStatuses to this
   *    list.  No guarantees are made about the order in which items are
   *    inserted to the target list.  The <code>SampleStatusAssistant</code>
   *    object copied from the source are reused in the target -- we don't
   *    create brand new objects.
   */
  private void copyCheckOutRNDIfApplicable(List sourceStatuses, List targetStatuses) {

    SampleStatusAssistant status = isSampleCurrentlyCheckedOutRND(sourceStatuses);

    if (status != null) {
      targetStatuses.add(status);
    }
  }

  /**
   * If the source statuses indicate that the source sample is currently
   * being transferred, copy a single status to the specified target status
   * list to indicate that it too is currently being transferred (this should never really
   * happen of course, but we're being complete).
   * See <code>isSampleCurrentlyBeingTransferred</code> for specifics
   * of when a sample is considered to be currently in transit.
   * If it is, then the most recent status that indicates that it is is
   * copied to the target sample.
   *
   * The copied record, if any, has the same type code and status date/time
   * as the source status record.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code>
   *    objects representing the statuses of the source sample.  We assume
   *    that this list is sorted by decreasing status datetime.  Only the
   *    status type code and sample status datetime fields of the
   *    <code>SampleStatusAssistant</code> objects are used.
   * @param targetStatuses statuses are copied from sourceStatuses to this
   *    list.  No guarantees are made about the order in which items are
   *    inserted to the target list.  The <code>SampleStatusAssistant</code>
   *    object copied from the source are reused in the target -- we don't
   *    create brand new objects.
   */
  private void copyTransferIfApplicable(List sourceStatuses, List targetStatuses) {

    SampleStatusAssistant status = isSampleCurrentlyBeingTransferred(sourceStatuses);

    if (status != null) {
      targetStatuses.add(status);
    }
  }

  /**
   * Copy status records from one status list to another.  The copied records have the same
   * type codes and status date/times as the source status records.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses of the source sample.  We assume that this list is sorted by decreasing status datetime.
   *    Only the status type code and sample status datetime fields of the <code>SampleStatusAssistant</code>
   *    objects are used.
   * @param targetStatuses statuses are copied from sourceStatuses to this list.
   *    No guarantees are made about the order in which items are inserted to the target list.
   *    The <code>SampleStatusAssistant</code> object copied from the source are reused in
   *    the target -- we don't create brand new objects.
   * @param typesToCopy an array of status type codes.  Only statuses whose type codes are in this array
   *    will be copied.
   */
  private void copyStatusRecords(List sourceStatuses, List targetStatuses, String[] typesToCopy)
    throws Exception {
    Set typesToCopySet = new HashSet(Arrays.asList(typesToCopy));

    Iterator iter = sourceStatuses.iterator();
    while (iter.hasNext()) {
      SampleStatusAssistant thisStatus = (SampleStatusAssistant) iter.next();
      if (typesToCopySet.contains(thisStatus.getStatusTypeCode())) {
        targetStatuses.add(thisStatus);
      }
    }
  }

  /**
   * Create a single subdivided child sample.  This method assumes that the parent sample 
   * has passed all validation checks and that either there were no warnings
   * or the user has indicated to proceed despite the warnings.   This method creates a single
   * child sample record and all required associated records such as status records.  This is only
   * one aspect of the complete subdivision process -- the <code>doSubdivision</code> method
   * performs the complete set of tasks.
   *
   * @param parentSample the parent sample of the child sample we are to create
   * @param childStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses to give to the child sample.  This list is not necessarily sorted.
   * @param childNumber an integer between 1 and the total number of child sample for this parent
   *    that uniquely identifies this child among its siblings.  This method must be passed a
   *    different <code>childNumber</code> value for each child.
   * @param childIDs the sample id of the newly-created child sample is appended to this list
   *    (this is an output parameter).
   * @param asmPositions the ASM position of the newly-created child sample is appended to this list
   *    (this is an output parameter).
   *
   * @return the sample id that was assigned to the new child sample
   */
  private void createChildSample(
    SampleAccessBean parentSample,
    List childStatuses,
    int childNumber,
    IdList childIDs,
    HashMap asmPositions,
    SecurityInfo securityInfo)
    throws Exception {

    String childID =
      createChildSampleRecord(
        parentSample,
        childNumber,
        childIDs,
        asmPositions,
        securityInfo);

    createChildSampleStatuses(childID, childStatuses);
  }

  /**
   * Create a single subdivided child sample record.  This method assumes that the parent sample 
   * has passed all validation checks and that either there were no warnings
   * or the user has indicated to proceed despite the warnings.   This method creates a single
   * child sample record, and does NOT include creating associated records such as statuses.  This is
   * only one aspect of the complete subdivision process -- the <code>doSubdivision</code> method
   * performs the complete set of tasks.
   *
   * @param parentSample the parent sample of the child sample we are to create
   * @param childNumber an integer between 1 and the total number of child sample for this parent
   *    that uniquely identifies this child among its siblings.  This method must be passed a
   *    different <code>childNumber</code> value for each child.
   * @param childIDs the sample id of the newly-created child sample is appended to this list
   *    (this is an output parameter).
   * @param asmPositions the ASM position of the newly-created child sample is appended to this list
   *    (this is an output parameter).
   *
   * @return the sample id that was assigned to the new child sample
   */
  private String createChildSampleRecord(
    SampleAccessBean parentSample,
    int childNumber,
    IdList childIDs,
    HashMap asmPositions,
    SecurityInfo securityInfo)
    throws Exception {

    String parentSampleID = getSampleID(parentSample);
    String childID = getNextChildSampleID(parentSampleID.substring(0, 2));
    String parentAllocationInd = parentSample.getAllocation_ind();
    String parentAccountKey = parentSample.getArdais_acct_key();
    String childASMPosition = (ApiFunctions.isEmpty(parentSample.getAsm_position())) ? ApiFunctions.EMPTY_STRING : (parentSample.getAsm_position() + childNumber);

    SampleAccessBean sample = new SampleAccessBean();
    AsmAccessBean parentAsm = parentSample.getAsm();

    {
      sample.setInit_sample_barcode_id(childID);
      sample.setInit_importedYN(parentSample.getImportedYN());
      sample.setInit_sampleTypeCid(parentSample.getSampleTypeCid());
      sample.setAllocation_ind(parentAllocationInd);
      sample.setArdais_acct_key(parentAccountKey);
      sample.setAsm_note(parentSample.getAsm_note());
      sample.setAsm_position(childASMPosition);
      sample.setParent_barcode_id(parentSampleID);

      sample.setType_of_fixative(parentSample.getType_of_fixative());
      // MR 5038 fields to be inherited upon subdivision
      sample.setHoursInFixative(parentSample.getHoursInFixative());
      sample.setDiMinThicknessPfinCid(parentSample.getDiMinThicknessPfinCid());
      sample.setDiMaxThicknessPfinCid(parentSample.getDiMaxThicknessPfinCid());
      sample.setDiWidthAcrossPfinCid(parentSample.getDiWidthAcrossPfinCid());
      sample.setHistoMinThicknessPfinCid(parentSample.getHistoMinThicknessPfinCid());
      sample.setHistoMaxThicknessPfinCid(parentSample.getHistoMaxThicknessPfinCid());
      sample.setHistoWidthAcrossPfinCid(parentSample.getHistoWidthAcrossPfinCid());
      sample.setHistoSubdividable("N"); // all children should have this set to "N" (MR 5414)
      sample.setParaffinFeatureCid(parentSample.getParaffinFeatureCid()); // MR 5922
      sample.setOtherParaffinFeature(parentSample.getOtherParaffinFeature()); // MR 5922

      // MR 7068 -- set ILTDS_SAMPLE.LAST_KNOWN_LOCATION_ID  
      sample.setLastknownlocationid(parentSample.getLastknownlocationid());  
      
      // MR 7096 -- set ILTDS_SAMPLE.RECEIPT_DATE
      sample.setReceiptDate(parentSample.getReceiptDate());


      if (parentAsm != null) {
        sample.setAsm((Asm) parentAsm.getEJBRef());
        //set the collection and preservation date/time values
        AsmformAccessBean form = new AsmformAccessBean(new AsmformKey(parentAsm.getAsm_form_id()));
        ConsentAccessBean consent = form.getConsent();
        IltdsUtils.setSampleCollectionAndPreservationDates(form, consent, sample);
      }

      sample.commitCopyHelper();
    }

    // MR 6757: Need to insert new rows into ARD_LOGICAL_REPOS_ITEM table
    createChildSampleLogicalRepositories(parentSampleID, childID);

    // Log the sample creation.
    //
    IltdsUtils.logSampleCreation(
      securityInfo,
      childID,
      null,
      sample.getImportedYN(),
      sample.getAsm_asm_id(),
      childASMPosition,
      sample.getArdais_acct_key());

    // We allocate child samples by copying the parent allocation
    // indicator to all children.  But, if the parent indicator is null,
    // then after committing the child sample record insertion we go back
    // and compute its allocation index using the same alogrithm that we
    // use to set the initial allocation of an ordinary (non-child) sample.
    //
    if (ApiFunctions.isEmpty(parentAllocationInd)) {
      Allocation.allocate(sample, securityInfo);
    }

    childIDs.add(childID);
    asmPositions.put(childID, childASMPosition);

    return childID;
  }

  /**
    * Insert rows for the specified logical repositories .
    * 
    * @param parentID the child sample id
    * @param childID the child sample id
    * 
    * @throws SQLException
    */
  private void createChildSampleLogicalRepositories(String parentID, String childID)
    throws SQLException {
    // MR6757: Need to insert new rows into ARD_LOGICAL_REPOS_ITEM table.

    String sql_select = "select repository_id from ARD_LOGICAL_REPOS_ITEM where item_id= ?";

    String sql_insert =
      "insert into ARD_LOGICAL_REPOS_ITEM "
        + "(ID, REPOSITORY_ID, ITEM_ID, ITEM_TYPE) "
        + "values (ard_logical_repos_item_seq.nextval,?,?,'SAMPLE')";

    Connection con = null;
    PreparedStatement pstmt_select = null;
    PreparedStatement pstmt_insert = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt_select = con.prepareStatement(sql_select);
      pstmt_insert = con.prepareStatement(sql_insert);

      // bind the parentID
      pstmt_select.setString(1, parentID);
      ResultSet rs = pstmt_select.executeQuery();

      // loop through all existing rows..
      while (rs.next()) {
        //add new rows       
        pstmt_insert.setString(1, rs.getString(1));
        pstmt_insert.setString(2, childID);
        pstmt_insert.execute();
      }
      rs.close();

    }
    finally {
      ApiFunctions.close(con, pstmt_select, null);
      ApiFunctions.close(con, pstmt_insert, null);
    }
  }

  /**
   * Create the subdivided children of the specified sample.  This method assumes that the sample 
   * has passed all validation checks and that either there were no warnings
   * or the user has indicated to proceed despite the warnings.   This method creates the
   * children and all required associated records such as status records.  This is only one
   * aspect of the complete subdivision process -- the <code>doSubdivision</code> method
   * performs the complete set of tasks.
   *
   * This method populates the supplied transaction details object with attributes
   * describing the results of the subdivision, such as the ids of the children.
   * Specifically, it
   * <ul>
   * <li>sets the childIdList property to be an <code>IdList</code>
   *     of the child sample id strings.</li>
   * <li>sets the childASMPositions property to be an <code>IdList</code>
   *     of the child ASM position strings (ordered the same as the childIDs list).</li>
   * <li>sets the QCInProcess property if the parent sample is currently on
   *     a pathology pick list.  Its value is a <code>java.util.Date</code> indicating when
   *     the sample was last put on such a list.</li>
   * <li>sets the RNDRequested property if the parent sample is currently on
   *     an R&D pick list.  Its value is a <code>java.util.Date</code> indicating when
   *     the sample was last put on such a list.</li>
   * </ul>
   *
   * @param parentSample the sample to subdivide
   * @param parentStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses of the parent sample.  We assume that this list is sorted by decreasing
   *    status datetime.
   * @param request the transaction details object to populate with information about the
   *    subdivision results
   */
  private void createChildSamples(
    SampleAccessBean parentSample,
    List parentStatuses,
    BTXDetailsSubdivide btxDetails)
    throws Exception {

    int numberOfChildren = btxDetails.getNumberOfChildren();

    IdList childIDs = new IdList();
//    IdList childASMPositions = new IdList();
    
    HashMap childASMPositions = new HashMap();
    
    List childStatuses = determineChildStatuses(parentStatuses);

    for (int i = 1; i <= numberOfChildren; i++) {
      createChildSample(
        parentSample,
        childStatuses,
        i,
        childIDs,
        childASMPositions,
        btxDetails.getLoggedInUserSecurityInfo());
    }

    btxDetails.setChildIdList(childIDs);
    btxDetails.setChildAsmPositions(childASMPositions);

    SampleStatusAssistant qcInProcessStatus = isSampleCurrentlyQCInProcess(parentStatuses);
    if (qcInProcessStatus != null) {
      btxDetails.setQCInProcess(new java.util.Date(qcInProcessStatus.getStatusDateTime()));
    }

    SampleStatusAssistant rndStatus = isSampleCurrentlyRNDRequested(parentStatuses);
    if (rndStatus != null) {
      btxDetails.setRNDRequested(new java.util.Date(rndStatus.getStatusDateTime()));
    }
  }

  /**
   * Create the status records for a single subdivided child sample.  This method assumes that the
   * parent sample has passed all validation checks and that either there were no warnings
   * or the user has indicated to proceed despite the warnings.  This is only
   * one aspect of the complete subdivision process -- the <code>doSubdivision</code> method
   * performs the complete set of tasks.
   *
   * @param childID the sample id of the child sample
   * @param childStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses to give to the child sample.  This list is not necessarily sorted.
   */
  private void createChildSampleStatuses(String childID, List childStatuses) throws Exception {
    Iterator iter = childStatuses.iterator();
    while (iter.hasNext()) {
      SampleStatusAssistant thisStatus = (SampleStatusAssistant) iter.next();
      addSampleStatus(
        childID,
        thisStatus.getStatusTypeCode(),
        new java.sql.Timestamp(thisStatus.getStatusDateTime()));
    }
  }

  /**
   * Build a list of the statuses that the child samples should have, based on the
   * list of parent statuses..  This method assumes that the
   * parent sample has passed all validation checks and that either there were no warnings
   * or the user has indicated to proceed despite the warnings.  This is only
   * one aspect of the complete subdivision process -- the <code>doSubdivision</code> method
   * performs the complete set of tasks.
   *
   * @param parentStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses of the parent sample.  We assume that this list is sorted by decreasing status datetime.
   * @return a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses to give to the child sample.  This list is not necessarily sorted.
   */
  private List determineChildStatuses(List parentStatuses) throws Exception {
    List childStatuses = new ArrayList(20);

    copyStatusRecords(
      parentStatuses,
      childStatuses,
      new String[] {
        FormLogic.SMPL_ARCOCASEPULL,
        FormLogic.SMPL_ARCOCONSREV,
        FormLogic.SMPL_ASMPRESENT,
        FormLogic.SMPL_GENRECALLED,
        FormLogic.SMPL_GENRELEASED,
        FormLogic.SMPL_MICOCASEPULL,
        FormLogic.SMPL_MICOCONSREV,
        FormLogic.SMPL_QCAWAITING });

    copyQCInProcessIfApplicable(parentStatuses, childStatuses);

    copyRNDRequestIfApplicable(parentStatuses, childStatuses);
    
    copyTransferIfApplicable(parentStatuses, childStatuses);
    
    copyMoveToPathIfApplicable(parentStatuses, childStatuses);

    copyCheckOutRNDIfApplicable(parentStatuses, childStatuses);

    copyRequestedIfApplicable(parentStatuses, childStatuses);

    copyCheckedOutIfApplicable(parentStatuses, childStatuses);

    return childStatuses;
  }

  /**
   * Subdivide the specified sample.  This method assumes that the sample 
   * has passed all validation checks and that either there were no warnings
   * or the user has indicated to proceed despite the warnings. 
   *
   * This method populates the supplied transaction details object with attributes
   * describing the results of the subdivision, such as the ids of the children.
   * Some attributes are set directly by this method:
   * <ul>
   * <li>the <code>asmID</code> attribute is set to the ASM id of the parent sample
   *     (which will also be the ASM id of the children).</li>
   * </ul>
   *
   * @param parentSample the sample to subdivide
   * @param parentStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses of the parent sample.  We assume that this list is sorted by decreasing status
   *    datetime.
   * @param btxDetails the transaction details object to populate with information
   *    about the subdivision results
   */
  private void doSubdivision(
    SampleAccessBean parentSample,
    List parentStatuses,
    BTXDetailsSubdivide btxDetails)
    throws Exception {

    createChildSamples(parentSample, parentStatuses, btxDetails);

    consumeParent(parentSample);

    btxDetails.setSubdivisionTimestamp(parentSample.getSubdivision_date());
    btxDetails.setAsmPosition(parentSample.getAsm_position());
  }

  /**
   * Search a list of statuses for the latest status having one of a set of specified type codes.
   * This method also accepts a set of type code that are considered to negate a status if it
   * appears more recently than that status.  If we find a negating status that is more recent
   * than one of the statuses we are searching for, or if we don't find any of the statuses
   * we are search for, we return null.  Otherwise we return the most recent status record that
   * has any of the status type codes that we are searching for.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses of the source sample.  We assume that this list is sorted by decreasing status datetime.
   *    Only the status type code and sample status datetime fields of the <code>SampleStatusAssistant</code>
   *    objects are used.
   * @param searchForStatuses the list of status type codes to search for
   * @param negatingStatuses the list of negating status type codes
   *
   * @return see above
   */
  private SampleStatusAssistant findLatestStatus(
    List sourceStatuses,
    String[] searchForStatuses,
    String[] negatingStatuses) {
    SampleStatusAssistant status = null;

    Set searchForSet =
      ((searchForStatuses == null) ? new HashSet() : new HashSet(Arrays.asList(searchForStatuses)));
    Set negatingSet =
      ((negatingStatuses == null) ? new HashSet() : new HashSet(Arrays.asList(negatingStatuses)));

    Iterator iter = sourceStatuses.iterator();
    while (iter.hasNext()) {
      SampleStatusAssistant thisStatus = (SampleStatusAssistant) iter.next();
      String typeCode = thisStatus.getStatusTypeCode();

      if (negatingSet.contains(typeCode))
        break;

      if (searchForSet.contains(typeCode)) {
        status = thisStatus;
        break;
      }
    }

    return status;
  }

  /**
   * Return the sample ID to use for the next child sample that will be created.
   * The sample id number is obtained from the ILTDS_SUBDIVIDED_SAMPLE_SEQ sequence.
   * The sample range reserved for subdivision children is PA15476341 to
   * PA165A0BC0, corresponding to an ASM form range of ASM119000001 to
   * ASM125000000.  Sample ids are expressed in hexidecimal notation.  The decimal
   * value of the sample number for the first sample in this range is
   * 357,000,001 and for the last sample the decimal number is 375,000,000.
   *
   * @param idPrefix the id prefix characters that the id should start with
   * @return the sample id
   */
  private String getNextChildSampleID(String idPrefix) throws Exception {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;

    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement("SELECT ILTDS_SUBDIVIDED_SAMPLE_SEQ.NEXTVAL FROM DUAL");
      rs = ApiFunctions.queryDb(pstmt, con);
      rs.next();
      long sampleIDNumber = rs.getLong(1);

      return FormLogic.makeHexId(idPrefix, sampleIDNumber, ValidateIds.LENGTH_SAMPLE_ID);
    }
    finally {
      if (rs != null)
        rs.close();
      if (pstmt != null)
        pstmt.close();
      if (con != null)
        ApiFunctions.closeDbConnection(con);
    }
  }

  /**
   * Return the sample id of the specified sample.
   *
   * @param sample the sample whose id will be returned
   * @return the sample's id
   */
  private String getSampleID(SampleAccessBean sample) throws Exception {
    return ((SampleKey) sample.__getKey()).sample_barcode_id;
  }

  /**
   * Return an unsorted list of the the sample status records for the specified sample.
   * Each element of the list returned is a <code>SampleStatusAssistant</code>.
   *
   * @param sample the sample whose statuses will be returned
   * @return the list of <code>SampleStatusAssistant</code> objects
   */
  private List getSampleStatuses(SampleAccessBean sample) throws Exception {
    List statuses = new java.util.ArrayList(20);

    SamplestatusAccessBean sampleStatus = new SamplestatusAccessBean();
    Enumeration enum1 = sampleStatus.findSamplestatusBySample((SampleKey) sample.__getKey());
    while (enum1.hasMoreElements()) {
      sampleStatus = (SamplestatusAccessBean) enum1.nextElement();
      statuses.add(
        new SampleStatusAssistant(
          sampleStatus.getSampleKey().sample_barcode_id,
          sampleStatus.getStatus_type_code(),
          ApiFunctions.timestampToMillis(sampleStatus.getSample_status_datetime())));
    }

    return statuses;
  }

  /**
   * Return a sorted list of the the sample status records for the specified sample.
   * Each element of the list returned is a <code>SampleStatusAssistant</code>.
   * The list is sorted by decreasing status datetime (that is, most recent statuses
   * first).  If multiple status records have the same datetime, their relative order
   * in the list is undefined.
   *
   * @param sample the sample whose statuses will be returned
   * @return the list of <code>SampleStatusAssistant</code> objects
   */
  private List getSortedSampleStatuses(SampleAccessBean sample) throws Exception {
    List statuses = getSampleStatuses(sample);

    // Create a comparator that will sort a list of SampleStatusAssistant objects
    // by decreasing status datetime.
    //
    Comparator comparator = new Comparator() {
      public int compare(Object x, Object y) {
        long difference =
          ((SampleStatusAssistant) x).getStatusDateTime()
            - ((SampleStatusAssistant) y).getStatusDateTime();
        return ((difference < 0) ? 1 : ((difference > 0) ? -1 : 0));
      }
    };

    // Sort and return the list of statuses.
    //
    Collections.sort(statuses, comparator);

    return statuses;
  }

  /**
   * If the source statuses indicate that the source sample has been consumed,
   * return the most recent status record that indicate this.  Otherwise return null.
   * The rules for determining whether a sample is consumed may change in the
   * future and the caller should make no assumptions about what the type code of the returned
   * status is.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses of the source sample.  We assume that this list is sorted by decreasing status datetime.
   *    Only the status type code and sample status datetime fields of the <code>SampleStatusAssistant</code>
   *    objects are used.
   *
   * @return see above
   */
  private SampleStatusAssistant isSampleConsumed(List sourceStatuses) {
    return findLatestStatus(sourceStatuses, new String[] { FormLogic.SMPL_COCONSUMED }, null);
  }

  /**
   * If the source statuses indicate that the source sample is currently on a pathology pick list,
   * return the most recent status record that indicates that it is currently on such a list.
   * Specifically, a sample is considered to be currently on a pathology pick
   * list if it has a QCINPROCESS status and there is no status with a later date/time of any
   * of these types: QCAWAITING, QCVERIFIED.
   * If this is the case, then the QCINPROCESS status from the source statuses that has the most
   * recent date/time is returned.  Otherwise null is returned.  These rules may change in the
   * future and the caller should make no assumptions about what the type code of the returned
   * status is.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses of the source sample.  We assume that this list is sorted by decreasing status datetime.
   *    Only the status type code and sample status datetime fields of the <code>SampleStatusAssistant</code>
   *    objects are used.
   *
   * @return see above
   */
  private SampleStatusAssistant isSampleCurrentlyQCInProcess(List sourceStatuses) {
    return findLatestStatus(
      sourceStatuses,
      new String[] { FormLogic.SMPL_QCINPROCESS },
      new String[] { FormLogic.SMPL_QCVERIFIED, FormLogic.SMPL_QCAWAITING });
  }

  /**
   * If the source statuses indicate that the source sample is currently on an R&D pick list,
   * return the most recent status record that indicates that it is currently on such a list.
   * Specifically, a sample is considered to be currently on an R&D pick
   * list if it has a RNDREQUEST status and there is no BOXSCAN status with a later date/time.
   * If this is the case, then the RNDREQUEST status from the source statuses that has the most
   * recent date/time is returned.  Otherwise null is returned.  These rules may change in the
   * future and the caller should make no assumptions about what the type code of the returned
   * status is.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses of the source sample.  We assume that this list is sorted by decreasing status datetime.
   *    Only the status type code and sample status datetime fields of the <code>SampleStatusAssistant</code>
   *    objects are used.
   *
   * @return see above
   */
  private SampleStatusAssistant isSampleCurrentlyRNDRequested(List sourceStatuses) {
    return findLatestStatus(
      sourceStatuses,
      new String[] { FormLogic.SMPL_RNDREQUEST },
      new String[] { FormLogic.SMPL_BOXSCAN });
  }

  /**
   * If the source statuses indicate that the source sample is currently being transferred,
   * return the most recent status record that indicates that it is being transferred.
   * Specifically, a sample is considered to be currently in transit
   * if it has a TRANSFER status and there is no BOXSCAN status with a later date/time.
   * If this is the case, then the TRANSFER status from the source statuses that has the most
   * recent date/time is returned.  Otherwise null is returned.  These rules may change in the
   * future and the caller should make no assumptions about what the type code of the returned
   * status is.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses of the source sample.  We assume that this list is sorted by decreasing status datetime.
   *    Only the status type code and sample status datetime fields of the <code>SampleStatusAssistant</code>
   *    objects are used.
   *
   * @return see above
   */
  private SampleStatusAssistant isSampleCurrentlyBeingTransferred(List sourceStatuses) {
    return findLatestStatus(
      sourceStatuses,
      new String[] { FormLogic.SMPL_TRANSFER },
      new String[] { FormLogic.SMPL_BOXSCAN });
  }

  /**
   * If the source statuses indicate that the source sample is currently on a request,
   * return the most recent status record that indicates that it is currently on a request.
   * Specifically, a sample is considered to be currently on a request
   * if it has a REQUESTED status and there is no CHECKEDOUT or BOXSCAN status with a later date/time.
   * If this is the case, then the REQUESTED status from the source statuses that has the most
   * recent date/time is returned.  Otherwise null is returned.  These rules may change in the
   * future and the caller should make no assumptions about what the type code of the returned
   * status is.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses of the source sample.  We assume that this list is sorted by decreasing status datetime.
   *    Only the status type code and sample status datetime fields of the <code>SampleStatusAssistant</code>
   *    objects are used.
   *
   * @return see above
   */
  private SampleStatusAssistant isSampleCurrentlyRequested(List sourceStatuses) {
    return findLatestStatus(
      sourceStatuses,
      new String[] { FormLogic.SMPL_REQUESTED },
      new String[] { FormLogic.SMPL_BOXSCAN, FormLogic.SMPL_CHECKEDOUT });
  }

  /**
   * If the source statuses indicate that the source sample is currently checked out,
   * return the most recent status record that indicates that it is currently checked out.
   * Specifically, a sample is considered to be checked out
   * if it has a CHECKEDOUT status and there is no BOXSCAN status with a later date/time.
   * If this is the case, then the CHECKEDOUT status from the source statuses that has the most
   * recent date/time is returned.  Otherwise null is returned.  These rules may change in the
   * future and the caller should make no assumptions about what the type code of the returned
   * status is.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code> objects representing the
   *    statuses of the source sample.  We assume that this list is sorted by decreasing status datetime.
   *    Only the status type code and sample status datetime fields of the <code>SampleStatusAssistant</code>
   *    objects are used.
   *
   * @return see above
   */
  private SampleStatusAssistant isSampleCurrentlyCheckedOut(List sourceStatuses) {
    return findLatestStatus(
      sourceStatuses,
      new String[] { FormLogic.SMPL_CHECKEDOUT },
      new String[] { FormLogic.SMPL_BOXSCAN });
  }

  /**
   * If the source statuses indicate that the source sample is currently
   * moved to pathology, return the most recent status record that indicates
   * that.  Specifically, a sample is considered to be currently moved to
   * pathology if it has a ARMVTOPATH status and there is no BOXSCAN
   * status with a later date/time.  If this is the case, then the
   * ARMVTOPATH status from the source statuses that has the most
   * recent date/time is returned.  Otherwise null is returned.  These rules
   * may change in the future and the caller should make no assumptions
   * about what the type code of the returned status is.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code>
   *    objects representing the statuses of the source sample.  We assume
   *    that this list is sorted by decreasing status datetime.  Only the
   *    status type code and sample status datetime fields of the
   *    <code>SampleStatusAssistant</code> objects are used.
   *
   * @return see above
   */
  private SampleStatusAssistant isSampleCurrentlyMovedToPath(List sourceStatuses) {
    return findLatestStatus(
      sourceStatuses,
      new String[] { FormLogic.SMPL_ARMVTOPATH },
      new String[] { FormLogic.SMPL_BOXSCAN });
  }

  /**
   * If the source statuses indicate that the source sample is currently
   * checked out to R&D, return the most recent status record that indicates
   * that.  Specifically, a sample is considered to be currently checked out
   * to R&D if it has a CORND status and there is no BOXSCAN
   * status with a later date/time.  If this is the case, then the
   * CORND status from the source statuses that has the most
   * recent date/time is returned.  Otherwise null is returned.  These rules
   * may change in the future and the caller should make no assumptions
   * about what the type code of the returned status is.
   *
   * @param sourceStatuses a list of <code>SampleStatusAssistant</code>
   *    objects representing the statuses of the source sample.  We assume
   *    that this list is sorted by decreasing status datetime.  Only the
   *    status type code and sample status datetime fields of the
   *    <code>SampleStatusAssistant</code> objects are used.
   *
   * @return see above
   */
  private SampleStatusAssistant isSampleCurrentlyCheckedOutRND(List sourceStatuses) {
    return findLatestStatus(
      sourceStatuses,
      new String[] { FormLogic.SMPL_CORND },
      new String[] { FormLogic.SMPL_BOXSCAN });
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

}
