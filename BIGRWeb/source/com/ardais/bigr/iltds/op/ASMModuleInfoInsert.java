package com.ardais.bigr.iltds.op;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.beans.ASMOperation;
import com.ardais.bigr.iltds.beans.ASMOperationHome;
import com.ardais.bigr.iltds.beans.AsmAccessBean;
import com.ardais.bigr.iltds.beans.AsmKey;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.btx.BtxDetailsDeleteSamples;
import com.ardais.bigr.iltds.databeans.AsmData;
import com.ardais.bigr.iltds.databeans.SampleData;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.icp.ejb.session.IcpOperation;
import com.ardais.bigr.iltds.icp.ejb.session.IcpOperationHome;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.oce.util.OceUtil;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;

public class ASMModuleInfoInsert extends com.ardais.bigr.iltds.op.StandardOperation {
  private List errors = null;

  public ASMModuleInfoInsert(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }

  public void invoke() throws Exception {
    errors = new ArrayList();
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);

    String module = request.getParameter("module");

    String organ = request.getParameter("tissue_type");
    String otherOrgan = request.getParameter("other_tissue");
    
    //delete any samples that were unchecked by the user. If any errors occur,
    //they will be added to the errors list.  We still allow the isValid() method 
    //to be invoked in case there were additional things wrong.
    Map deletedSamples = deleteSamples(securityInfo, module);

    //isValid collects all the request objects and puts them into the bean structure
    //and validates the beans in the process.
    AsmData asmData = isValid(securityInfo, deletedSamples);

    if (errors.size() != 0) {
      retry(asmData);
      return;
    }

    AsmAccessBean asmBean = new AsmAccessBean(new AsmKey(module));
    String currentOtherOrgan = asmBean.getOrgan_site_concept_id_other();

    if (organ.equals(FormLogic.OTHER_TISSUE)) {

      List pKeys = new ArrayList();
      pKeys.add(module);

      OceUtil.createOce(
        OceUtil.ILTDS_ASM_OTHER_ORGANSITE,
        pKeys,
        otherOrgan,
        (String) request.getSession(false).getAttribute("user"));

    }
    //Following code added for MR 5044.
    // If the new "other" organ is empty and an "other" organ existed,
    // then update OCE to indicate that(Mark as Obsolete).
    if (!ApiFunctions.isEmpty(currentOtherOrgan) && ApiFunctions.isEmpty(otherOrgan)) {

      OceUtil.markStatusObsolete(
        OceUtil.ILTDS_ASM_OTHER_ORGANSITE,
        module,
        (String) request.getSession(false).getAttribute("user"));
    }

    ASMOperationHome asmHome = (ASMOperationHome) EjbHomes.getHome(ASMOperationHome.class);
    ASMOperation asmOp = asmHome.create();
    asmOp.updateASMModuleInfo(asmData, securityInfo);

    request.setAttribute("myError", "Confirm");
    servletCtx.getRequestDispatcher("/hiddenJsps/iltds/asm/asmModuleInfoInsert.jsp").forward(
      request,
      response);
  }
  
  //delete any samples that exist in the database but have had their checkboxes unchecked by the 
  //user on the ASM Module Info screen. MR6976
  private Map deleteSamples(SecurityInfo securityInfo, String module) throws Exception {
    Map returnValue = new HashMap();
    //get the existing sample ids for the ASM module
    AsmData asmInfo = new AsmData();
    asmInfo.setAsm_id(module);
    IcpOperationHome home = (IcpOperationHome) EjbHomes.getHome(IcpOperationHome.class);
    IcpOperation icp = home.create();
    asmInfo = (AsmData) icp.getAsmData(asmInfo, securityInfo, true, false);
    Iterator frozenIterator = ApiFunctions.safeList(asmInfo.getFrozen_samples()).iterator();    
    Iterator paraffinIterator = ApiFunctions.safeList(asmInfo.getParaffin_samples()).iterator();    
    List existingSampleIds = new ArrayList();
    SampleData sample;
    while (frozenIterator.hasNext()) {
      sample = (SampleData)frozenIterator.next();
      existingSampleIds.add(sample.getSample_id());
    }
    while (paraffinIterator.hasNext()) {
      sample = (SampleData)paraffinIterator.next();
      existingSampleIds.add(sample.getSample_id());
    }
    //for each existing sample, if it has not been checked off on the front end it is a candidate
    //for deletion.  Build a list of candidate ids and if it's not empty pass the list off to
    //BtxPerformerSampleOperation for deletion.  That class will enforce any business rules that
    //ensure the samples can truly be deleted.  We simply need to check for errors and if any
    //ocurred pass them back to the user.
    List samplesToDelete = new ArrayList();
    Iterator existingIterator = existingSampleIds.iterator();
    while (existingIterator.hasNext()) {
      String sampleId = (String)existingIterator.next();
      //if the state is disabled, this is a sample that wasn't allowed to be unchecked
      //so it is not a candidate for deletion.  We cannot check the "present"<sample_id>
      //parameter because disabled controls do not have their values passed in the request
      String state = ApiFunctions.safeString(request.getParameter("present" + sampleId + "state"));
      if (state.indexOf("disabled") < 0) {
        //otherwise, see if there is a parameter indicating the sample is checked.  If not,
        //this sample is a candidate for deletion.  Frozen and paraffin samples have 
        //slightly different parameter names, so get them both
        String frozenCheck = ApiFunctions.safeString(request.getParameter("present" + sampleId));
        String paraffinCheck = ApiFunctions.safeString(request.getParameter("pfin_present" + sampleId));
        if (ApiFunctions.isEmpty(frozenCheck) && ApiFunctions.isEmpty(paraffinCheck)) {
          samplesToDelete.add(sampleId);
        }
      }
    }
    if (samplesToDelete.size() > 0) {
      Timestamp now = new Timestamp(System.currentTimeMillis());
      BtxDetailsDeleteSamples btxDetails = new BtxDetailsDeleteSamples();
      btxDetails.setBeginTimestamp(now);
      btxDetails.setLoggedInUserSecurityInfo(securityInfo);
      List sampleList = new ArrayList();
      Iterator sampleIterator = samplesToDelete.iterator();
      while (sampleIterator.hasNext()) {
        com.ardais.bigr.javabeans.SampleData sampleData = new com.ardais.bigr.javabeans.SampleData();
        sampleData.setSampleId((String)sampleIterator.next());
        sampleData.setConsentId(asmInfo.getCase_id());
        sampleList.add(sampleData);
      }
      btxDetails.setSampleDatas(sampleList);
      //note - I spoke with Gail about providing a default reason for deleting the samples,
      //and she requested that we leave the reason blank.  Most of the time it's because the
      //samples were created in error, but there are occasionally other reasons (i.e. the
      //sample was broken).
      btxDetails = (BtxDetailsDeleteSamples)Btx.perform(btxDetails, "iltds_sample_deleteSamples");
      //if any errors were returned, add them to the errors list
      BtxActionErrors actionErrors = btxDetails.getActionErrors();
      if (actionErrors != null && actionErrors.size() > 0) {
        Iterator errorIterator = actionErrors.get();
        MessageResources messages = (MessageResources)servletCtx.getAttribute(Globals.MESSAGES_KEY);
        while (errorIterator.hasNext()) {
          BtxActionError error = (BtxActionError)errorIterator.next();
          String message = messages.getMessage(error.getKey(), error.getValues());
          //because these messages are shown in plain text, strip off the <li> and </li> tags
          message = StringUtils.replace(message,"<li>"," ");
          message = StringUtils.replace(message,"</li>"," ");
          errors.add(message.trim());
        }
      }
      else {
        //no errors occurred, so return a map of deleted sample ids
        Iterator iterator = samplesToDelete.iterator();
        String sampleId;
        while (iterator.hasNext()) {
          sampleId = (String)iterator.next();
          returnValue.put(sampleId, sampleId);
        }
      }
    }
    return returnValue;
  }

  private AsmData isValid(SecurityInfo securityInfo, Map deletedSamples) throws Exception {
    String module = request.getParameter("module");

    Vector myFrozen = new Vector();
    Vector myParaffin = new Vector();
    // if samples are in the conversion range, they will be processed in the
    // "other" loop below; not in the myFrozen and myParaffin loops...not sure
    // on the history of this...
    if (!FormLogic.isConversion(module.substring(0, module.length() - 2))) {
      myFrozen = FormLogic.genFrozenIDsFromASMID(module);
      myParaffin = FormLogic.genParaffinIDsFromASMID(module);
    }

    AsmData asmInfo = new AsmData();
    asmInfo.setAsm_id(module);
    IcpOperationHome home = (IcpOperationHome) EjbHomes.getHome(IcpOperationHome.class);
    IcpOperation icp = home.create();
    asmInfo = (AsmData) icp.getAsmData(asmInfo, securityInfo, false, false);
    asmInfo.setTissue_type(request.getParameter("tissue_type"));
    asmInfo.setOther_tissue(request.getParameter("other_tissue"));
    asmInfo.setAppearance(request.getParameter("diseased"));

    // set up the new fields for MR 4435
    asmInfo.setPfin_meets_specs(request.getParameter("pfin_yesno_specs"));
    asmInfo.setModule_weight(request.getParameter("moduleWeight"));
    asmInfo.setModule_comments(request.getParameter("moduleComments"));

    validateASM(asmInfo);

    // process frozen samples not in the conversion range
    for (int i = 0; i < myFrozen.size(); i++) {
      SampleData sampleData = new SampleData();
      String sampleID = (String) myFrozen.get(i);
      sampleData.setSample_id(sampleID);
      sampleData = icp.getSampleData(sampleData, securityInfo, false, false);
      if (sampleData.getAsm_id() != null) {
        sampleData.setExists(true);
      }
      else {
        sampleData.setAsm_id(module);
      }
      if (!sampleData.isExists() && !deletedSamples.containsKey(sampleID)) {
        String exists = request.getParameter("present" + sampleID);
        if (ApiFunctions.safeStringLength(exists) == 0) {
          exists = request.getParameter("present" + sampleID + "state");
        }
        sampleData.setExists(ApiFunctions.safeStringLength(exists) > 0);
      }
      sampleData.setComment(request.getParameter("comment" + sampleID));
      sampleData.setAsm_position(FormLogic.getASMLocation(sampleID));

      // set the new fields for MR 4435 frozen samples
      sampleData.setSampleSizeMeetsSpecs(request.getParameter("meets_specs_" + sampleID));
      sampleData.setFormatDetailCid(request.getParameter("format_detail" + sampleID));

      asmInfo.addFrozen_sample(sampleData);
    }

    // process paraffin samples not in the conversion range
    for (int i = 0; i < myParaffin.size(); i++) {
      SampleData sampleData = new SampleData();
      String sampleID = (String) myParaffin.get(i);
      sampleData.setSample_id(sampleID);
      sampleData = icp.getSampleData(sampleData, securityInfo, false, false);
      if (sampleData.getAsm_id() != null) {
        sampleData.setExists(true);
      }
      else {
        sampleData.setAsm_id(module);
      }
      if (!sampleData.isExists() && !deletedSamples.containsKey(sampleID)) {
        String exists = request.getParameter("pfin_present" + sampleID);
        if (ApiFunctions.safeStringLength(exists) == 0) {
          exists = request.getParameter("pfin_present" + sampleID + "state");
        }
        sampleData.setExists(ApiFunctions.safeStringLength(exists) > 0);
      }
      sampleData.setComment(request.getParameter("pfin_comment" + sampleID));
      sampleData.setAsm_position(FormLogic.getASMLocation(sampleID));
      sampleData.setType_fixative(request.getParameter("type_fixative_state"));

      // set the fields for MR 4435 paraffin samples
      // note that we are using the hidden (xxx_state) fields to get around the issue that
      // disabled fields do not pass their values...
      sampleData.setDiMinThicknessCid(request.getParameter("min_thickness" + sampleID + "state"));
      sampleData.setDiMaxThicknessCid(request.getParameter("max_thickness" + sampleID + "state"));
      sampleData.setDiWidthAcrossCid(request.getParameter("width_across" + sampleID + "state"));
      sampleData.setSampleSizeMeetsSpecs(request.getParameter("pfin_meets_specs_" + sampleID));
      String hours = request.getParameter("hoursFixative");
      try {
        if ((hours != null) && (hours != "")) {
          sampleData.setHoursInFixative(new Integer(hours));
        }
      }
      catch (NumberFormatException ne) {
        errors.add("Please enter an integer value for Hours in Fixative");
      }

      // MR4852
      sampleData.setParaffinFeatureCid(request.getParameter("paraffinFeatureCid" + sampleID));
      sampleData.setOtherParaffinFeature(request.getParameter("otherParaffinFeature" + sampleID));

      asmInfo.addParaffin_sample(sampleData);
    }

    for (int i = 0; i < myParaffin.size(); i++) {
      myFrozen.add(myParaffin.get(i));
    }

    ASMOperationHome asmHome = (ASMOperationHome) EjbHomes.getHome(ASMOperationHome.class);
    ASMOperation asmOp = asmHome.create();
    Vector otherSamples = asmOp.nonAsmFormSamples(module, myFrozen);

    // here is where samples in the conversion range are processed...
    for (int i = 0; i < otherSamples.size(); i++) {
      SampleData sampleData = new SampleData();
      String sampleID = (String) otherSamples.get(i);
      sampleData.setSample_id(sampleID);
      sampleData = icp.getSampleData(sampleData, securityInfo, false, false);

      sampleData.setAsm_id(module);
      sampleData.setExists(true);

      // 2.3.05 this will be replaced with new constants...
      // note that we are not supporting SX samples here b/c
      // this code is only used for generating ASMs using 
      // traditional ardais process, not sample intake...
      if (sampleID.startsWith(ValidateIds.PREFIX_SAMPLE_FROZEN)) {

        // the new fields for MR 4435 frozen samples
        sampleData.setComment(request.getParameter("comment" + sampleID));
        sampleData.setSampleSizeMeetsSpecs(request.getParameter("meets_specs_" + sampleID));
        sampleData.setFormatDetailCid(request.getParameter("format_detail" + sampleID));

        asmInfo.addFrozen_sample(sampleData);
      }
      else if (sampleID.startsWith(ValidateIds.PREFIX_SAMPLE_PARAFFIN)) {

        // the new and changed fields for MR 4435 paraffin samples
        // note that we are using the hidden (xxx_state) fields to get around the issue that
        // disabled fields do not pass their values...				
        sampleData.setComment(request.getParameter("pfin_comment" + sampleID));
        sampleData.setType_fixative(request.getParameter("type_fixative_state"));
        sampleData.setDiMinThicknessCid(request.getParameter("min_thickness" + sampleID + "state"));
        sampleData.setDiMaxThicknessCid(request.getParameter("max_thickness" + sampleID + "state"));
        sampleData.setDiWidthAcrossCid(request.getParameter("width_across" + sampleID + "state"));
        sampleData.setSampleSizeMeetsSpecs(request.getParameter("pfin_meets_specs_" + sampleID));
        String hours = request.getParameter("hoursFixative");
        try {
          if ((hours != null) && (hours != "")) {
            sampleData.setHoursInFixative(new Integer(hours));
          }
        }
        catch (NumberFormatException ne) {
          errors.add("Please enter an integer value for Hours in Fixative");
        }

        // MR4852
        sampleData.setParaffinFeatureCid(request.getParameter("paraffinFeatureCid" + sampleID));
        sampleData.setOtherParaffinFeature(request.getParameter("otherParaffinFeature" + sampleID));

        asmInfo.addParaffin_sample(sampleData);
      }
    }

    return asmInfo;
  }

  private void retry(AsmData asmData) throws IOException, ServletException {
    // create the fixative dropdown
    // changed to use ARTS for MR 4435
    LegalValueSet fixatives = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_TYPE_OF_FIXATIVE);
    request.setAttribute("fixatives", fixatives);

    // new fields that pass in ARTS codes for MR 4435 
    LegalValueSet sampleFormatDetail = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_SAMPLE_FORMAT_DETAIL);
    request.setAttribute("sampleFormat", sampleFormatDetail);

    LegalValueSet minThickness = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_PARAFFIN_DIMENSIONS);
    request.setAttribute("minThickness", minThickness);

    LegalValueSet maxThickness = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_PARAFFIN_DIMENSIONS);
    request.setAttribute("maxThickness", maxThickness);

    LegalValueSet widthAcross = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_PARAFFIN_DIMENSIONS);
    request.setAttribute("widthAcross", widthAcross);

    LegalValueSet paraffinFeatureSet = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_PARAFFIN_FEATURE);
    request.setAttribute("paraffinFeatureSet", paraffinFeatureSet);

    //set the changed sample vectors
    request.setAttribute("hours", FormLogic.getHourIntVector());
    request.setAttribute("minutes", FormLogic.getMinuteIntVector());

    request.setAttribute("organ", asmData.getTissue_type());
    request.setAttribute("otherTissue", asmData.getOther_tissue());
    request.setAttribute("moduleLetter", asmData.getModule_letter());

    request.setAttribute("asmInfo", asmData);
    request.setAttribute("myRetry", errors);
    
    //MR6976 - for every sample on the ASM, populate it's statuses.  If this isn't done,
    //the jsp will incorrectly allow the user to uncheck samples that have progressed
    //beyond the ASMPRESENT stage.
    SampleData sampleData;
    Iterator frozenIterator = asmData.getFrozen_samples().iterator();
    while (frozenIterator.hasNext()) {
      sampleData = (SampleData)frozenIterator.next();
      if (sampleData.isExists()) {
        sampleData.setStatuses(FormLogic.getStatusValuesForSample(sampleData.getSample_id()));
      }
    }
    Iterator paraffinIterator = asmData.getParaffin_samples().iterator();
    while (paraffinIterator.hasNext()) {
      sampleData = (SampleData)paraffinIterator.next();
      if (sampleData.isExists()) {
        sampleData.setStatuses(FormLogic.getStatusValuesForSample(sampleData.getSample_id()));
      }
    }

    servletCtx.getRequestDispatcher("/hiddenJsps/iltds/asm/asmModuleInfo.jsp").forward(
      request,
      response);
  }

  private void validateASM(AsmData asmData) {
    String tissue = asmData.getTissue_type();
    String otherTissue = asmData.getOther_tissue();
    String apperance = asmData.getAppearance();

    if (tissue == null || tissue.equals("")) {
      errors.add("Please enter a value for Tissue.");
    }
    else if (
      tissue.equals(FormLogic.OTHER_TISSUE) && (otherTissue == null || otherTissue.equals(""))) {
      errors.add("Please enter a value for Other Tissue.");
    }
    if (apperance == null || apperance.equals("")) {
      errors.add("Please enter a value for Appearance.");
    }

    //MR 4435 -- verify data for module weight
    try {
      if (asmData.getModule_weight() != null && !asmData.getModule_weight().equals("")) {
        new Integer(asmData.getModule_weight());
      }
    }
    catch (NumberFormatException ne) {
      errors.add("Please enter an integer value for Module Weight " + asmData.getModule_weight());
    }

  }
}
