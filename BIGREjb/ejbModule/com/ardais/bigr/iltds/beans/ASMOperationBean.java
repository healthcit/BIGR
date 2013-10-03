package com.ardais.bigr.iltds.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.ejb.EJBException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.SessionBean;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.bizlogic.Allocation;
import com.ardais.bigr.iltds.databeans.AsmData;
import com.ardais.bigr.iltds.databeans.SampleData;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;


/**
 * This is a Session Bean Class
 */
public class ASMOperationBean implements SessionBean {
  private javax.ejb.SessionContext mySessionCtx = null;
  final static long serialVersionUID = 3206093459760846163L;

  public boolean asmFormExistInRange(String asmFormID, String consentID) {
    String queryString = "SELECT asm_form_id FROM ILTDS_ASM_FORM where CONSENT_ID = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);

      pstmt.setString(1, consentID);

      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        String resultASM = rs.getString(1);
        if (FormLogic.asmInSameRange(resultASM, asmFormID)) {
          return true;
        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return false;
  }

  public void associateASMForm(String consentID, String asmFormID, SecurityInfo secInfo) {

    try {
      java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());

      String userId = secInfo.getUsername();
      String accountId = secInfo.getAccount();

      ConsentAccessBean consentBean = new ConsentAccessBean(new ConsentKey(consentID));
      String ardaisID = consentBean.getArdais_id();

      //create the ASM Form
      AsmformAccessBean form = new AsmformAccessBean();
      {
        form.setInit_argAsm_form_id(asmFormID);
        form.setInit_argConsent((ConsentKey) consentBean.__getKey());
        form.setLink_staff_id(userId);
        form.setArdais_staff_id(userId);
        form.setCreation_time(now);
        form.setInit_argArdais_id(ardaisID); // changed by sat/rn 12/23/02
        form.commitCopyHelper();
      }

      Vector asmIDs = new Vector();
      asmIDs = FormLogic.genASMEntryIDs(asmFormID);

      Vector frozenIDs = new Vector();
      Vector paraffinIDs = new Vector();
      frozenIDs = FormLogic.genFrozenIDs(asmFormID);
      paraffinIDs = FormLogic.genParaffinIDs(asmFormID);

      int counter = 0;
      int delimiter = FormLogic.NUM_SAMPLE_FR / FormLogic.NUM_SAMPLE_PA;

      // Create ASMs if necessary and update any previously created samples.
      // 
      for (int i = 0; i < paraffinIDs.size(); i++) {
        String asmId = (String) asmIDs.get(i);
        String paraffinId = (String) paraffinIDs.get(i);
        List frozenIdsInAsm = new ArrayList();
        while (counter < (delimiter * (i + 1))) {
          frozenIdsInAsm.add(frozenIDs.get(counter));
          counter++;
        }
        createAsmAndUpdateSamples(asmId, frozenIdsInAsm, paraffinId, accountId, form, now, secInfo);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  private void createAsmAndUpdateSamples(
    String asmID,
    List frozenSamples,
    String paraffinSample,
    String ardaisAccountKey,
    AsmformAccessBean asmForm,
    java.sql.Timestamp timestamp,
    SecurityInfo secInfo)
    throws Exception {

    AsmAccessBean asmBean = new AsmAccessBean();
    asmBean.setInit_asm_id(asmID);
    asmBean.setAsm_form_id(((AsmformKey) asmForm.__getKey()).asm_form_id);
    asmBean.setConsent_id(asmForm.getConsentKey().consent_id);
    asmBean.setArdais_id(asmForm.getArdais_id());
    asmBean.setAsm_entry_date(timestamp);
    asmBean.setOrgan_site_concept_id(null);
    asmBean.setOrgan_site_concept_id_other(null);
    asmBean.setSpecimen_type(null);
    asmBean.setModule_comments(null);
    asmBean.commitCopyHelper();

    // Update any pre-existing samples.  There might be some of these since sample
    // records can be created in a box scan before ASM Form Data entry has happened.
    // Also, attempt to allocate the samples.  They may or may not end up getting allocated
    // depending on whether we have all of the information we need to be able
    // to do allocation.  See the Allocation class for more details on when a sample can
    // be allocated.  We need to try here since this might be the first time the sample
    // had its ASM field filled in and that's the point at which a sample can become
    // allocatable.  We also need to determine if the sample is to be shipped to Ardais
    // at this point and if it should be given a GENRELEASED sales status, since we now have 
    //the ASM and that is needed to make these decisions.

    Iterator frozenIter = frozenSamples.iterator();
    while (frozenIter.hasNext()) {
      try {
        String sampleId = (String) frozenIter.next();
        SampleAccessBean sample = new SampleAccessBean(new SampleKey(sampleId));
        sample.setAsm((Asm) asmBean.getEJBRef());
        sample.setAsm_position(FormLogic.getASMLocation(sampleId));
        //set the sales status attribute if appropriate.  Must be called after
        //setting the asm attribute.
        IltdsUtils.applyPolicyToSample(sample, IltdsUtils.APPLY_POLICY_FOR_SALES_STATUS);
        //update the collection and preservation date/time values
        IltdsUtils.setSampleCollectionAndPreservationDates(asmForm, asmForm.getConsent(), sample);
        sample.commitCopyHelper();

        Allocation.allocate(sample, secInfo);
      }
      catch (ObjectNotFoundException e) {
        //if the sample does not exist yet, we are not going to create it  MR 3257
      }
    }

    try {
      SampleAccessBean sample = new SampleAccessBean(new SampleKey(paraffinSample));
      sample.setAsm((Asm) asmBean.getEJBRef());
      sample.setAsm_position(FormLogic.getASMLocation(paraffinSample));
      //set the sales status attribute if appropriate.  Must be called after
      //setting the asm attribute.
      IltdsUtils.applyPolicyToSample(sample, IltdsUtils.APPLY_POLICY_FOR_SALES_STATUS);
      //update the collection and preservation date/time values
      IltdsUtils.setSampleCollectionAndPreservationDates(asmForm, asmForm.getConsent(), sample);
      sample.commitCopyHelper();

      Allocation.allocate(sample, secInfo);
    }
    catch (ObjectNotFoundException e) {
      //if the sample does not exist yet, we are not going to create it  MR 3257
    }
  }

  private String getAccountKey(String asmID) throws Exception {
    String queryString =
      "select e.ARDAIS_ACCT_KEY "
        + "from iltds_informed_consent c, iltds_asm a, es_ardais_account e "
        + "where a.consent_id = c.consent_id "
        + "  and c.CONSENT_LOCATION_ADDRESS_ID = e.PRIMARY_LOCATION  "
        + "  and a.asm_id = ?";

    String result = "";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, asmID);

      rs = ApiFunctions.queryDb(pstmt, con);
      if (rs.next()) {
        result = rs.getString(1);
      }
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return result;
  }

  /**
   * Return a list of sample ids for all samples that are in the specified ASM but
   * that are not in the list of specified sample ids.
   * 
   * @param asmID the ASM id
   * @param samples the samples ids to exclude from the result
   * @return the filtered list of sample ids
   */
  public Vector nonAsmFormSamples(String asmID, Vector samples) {
    String queryString = "select sample_barcode_id from iltds_sample where asm_id = ? ";
    if (samples.size() > 0) {
      queryString += " and sample_barcode_id not in "
        + ApiFunctions.makeBindParameterList(samples.size());
    }

    Vector result = new Vector();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);

      pstmt.setString(1, asmID);
      ApiFunctions.bindBindParameterList(pstmt, 2, samples);

      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        result.add(rs.getString(1));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return result;
  }

  public void updateASMFormInfo(
    String asmFormID,
    String surgicalProcedure,
    java.sql.Date timeOfRemoval,
    java.sql.Date timeOfGrossing,
    String employeeName) {

    Connection con = null;
    PreparedStatement pstmt = null;

    int questionCounter = 1;
    int surgInt = 0;
    int removeInt = 0;
    int grossInt = 0;
    int employeeInt = 0;

    try {
      con = ApiFunctions.getDbConnection();

      String updateASMForm = "UPDATE iltds_asm_form SET ";

      if (surgicalProcedure != null) {
        updateASMForm = updateASMForm + " SURGICAL_SPECIMEN_ID = ?, ";
        surgInt = questionCounter;
        questionCounter++;
      }
      if (timeOfRemoval != null) {
        updateASMForm = updateASMForm + " REMOVAL_DATE = ?, ";
        removeInt = questionCounter;
        questionCounter++;
      }
      if (timeOfGrossing != null) {
        updateASMForm = updateASMForm + " GROSSING_DATE = ? ";
        grossInt = questionCounter;
        questionCounter++;
      }
      if (employeeName != null) {
        updateASMForm = updateASMForm + " ARDAIS_STAFF_ID = ? ";
        employeeInt = questionCounter;
        questionCounter++;
      }
      updateASMForm = " ASM_FORM_ID = ? " + " WHERE 	asm_form_id = ?";

      pstmt = con.prepareStatement(updateASMForm);

      if (surgInt != 0) {
        pstmt.setString(surgInt, surgicalProcedure);
      }
      if (removeInt != 0) {
        pstmt.setDate(removeInt, timeOfRemoval);
      }
      if (grossInt != 0) {
        pstmt.setDate(grossInt, timeOfGrossing);
      }
      if (employeeInt != 0) {
        pstmt.setString(employeeInt, employeeName);
      }
      pstmt.setString(questionCounter, asmFormID);
      pstmt.setString((questionCounter + 1), asmFormID);

      pstmt.executeUpdate();
      
      //because we may have updated the removal or grossing dates, update any
      //samples belonging to this form
      AsmformAccessBean form = new AsmformAccessBean(new AsmformKey(asmFormID));
      IltdsUtils.setSampleCollectionAndPreservationDates(form);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  public void updateASMModuleInfo(AsmData asmData, SecurityInfo secInfo) {
    // update the ASM record
    updateAsmModuleInfo(
      asmData.getAsm_id(),
      asmData.getAppearance(),
      asmData.getTissue_type(),
      asmData.getOther_tissue(),
      asmData.getModule_comments(),
      asmData.getModule_weight(),
      asmData.getPfin_meets_specs());

    Iterator frozenSamplesIter = asmData.getFrozen_samples().iterator();
    while (frozenSamplesIter.hasNext()) {
      SampleData sample = (SampleData) frozenSamplesIter.next();
      if (sample.isExists()) {
        // create/update each frozen sample record
        createOrUpdateSample(sample, secInfo);
      }
    }

    Iterator paraffinSamplesIter = asmData.getParaffin_samples().iterator();
    while (paraffinSamplesIter.hasNext()) {
      SampleData sample = (SampleData) paraffinSamplesIter.next();
      if (sample.isExists()) {
        // create/update each paraffin sample record
        createOrUpdateSample(sample, secInfo);
      }
    }
  }

  private void updateAsmModuleInfo(
    String asmID,
    String specimenType,
    String tissueType,
    String otherTissueType,
    String moduleComments,
    String moduleWeight,
    String pfin_meets_specs) {

    Connection con = null;
    PreparedStatement pstmt = null;

    int questionCounter = 1;
    int specInt = 0;
    int tissueInt = 0;
    int otherTissueInt = 0;
    int modCommentsInt = 0;
    int asmIDInt = 0;
    int modWeightInt = 0;
    int modPfinInt = 0;

    try {
      con = ApiFunctions.getDbConnection();

      String updateASM = "UPDATE iltds_asm SET ";

      if (specimenType != null) {
        updateASM = updateASM + " specimen_type = ?, ";
        specInt = questionCounter;
        questionCounter++;
      }
      if (tissueType != null) {
        updateASM = updateASM + " organ_site_concept_id = ?, ";
        tissueInt = questionCounter;
        questionCounter++;

        // MR 5864 need to set those that were formerly others and changed to null
        //  so let's set other type to null unless actually has a value...
        updateASM = updateASM + " organ_site_concept_id_other = ?, ";
        otherTissueInt = questionCounter;
        questionCounter++;

      }

      if (!ApiFunctions.isEmpty(moduleWeight)) {
        updateASM = updateASM + " MODULE_WEIGHT = ?, ";
        modWeightInt = questionCounter;
        questionCounter++;
      }

      // added for MR 4435
      updateASM = updateASM + " MODULE_COMMENTS = ?, ";
      modCommentsInt = questionCounter;
      questionCounter++;

      updateASM = updateASM + " PFIN_MEETS_SPECS = ?, ";
      modPfinInt = questionCounter;
      questionCounter++;

      updateASM = updateASM + " ASM_ID = ? " + " WHERE asm_id = ?";
      asmIDInt = questionCounter;
      questionCounter += 2;

      pstmt = con.prepareStatement(updateASM);

      if (specInt != 0) {
        pstmt.setString(specInt, specimenType);
      }
      if (tissueInt != 0) {
        pstmt.setString(tissueInt, tissueType);
      }
      if (otherTissueInt != 0) {
        if (tissueType.equals(FormLogic.OTHER_TISSUE)) {
          pstmt.setString(otherTissueInt, otherTissueType);
        }
        else {
          pstmt.setString(otherTissueInt, null);
        }
      }
      if (modWeightInt != 0) {
        pstmt.setInt(modWeightInt, Integer.parseInt(moduleWeight));
      }

      pstmt.setString(modCommentsInt, moduleComments);
      pstmt.setString(modPfinInt, pfin_meets_specs);
      pstmt.setString(asmIDInt, asmID);
      pstmt.setString((asmIDInt + 1), asmID);

      pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  private void createOrUpdateSample(SampleData sampleData, SecurityInfo secInfo) {
    SampleAccessBean sample = null;

    try {
      String sampleId = sampleData.getSample_id();
      try {
        // determine if an existing record...
        sample = new SampleAccessBean(new SampleKey(sampleId));
        sample.setAsm_note(sampleData.getComment());
        // 2.3.05 note that we are not supporting SX samples here b/c
        // this code is only used for generating ASMs using 
        // traditional ardais process, not sample intake...
        if (sampleId.startsWith(ValidateIds.PREFIX_SAMPLE_PARAFFIN)) {
          sample.setType_of_fixative(sampleData.getType_fixative());
          // MR 4435 - paraffin only fields
          sample.setDiMinThicknessPfinCid(sampleData.getDiMinThicknessCid());
          sample.setDiMaxThicknessPfinCid(sampleData.getDiMaxThicknessCid());
          sample.setDiWidthAcrossPfinCid(sampleData.getDiWidthAcrossCid());
          sample.setHoursInFixative(sampleData.getHoursInFixative());

          // MR4852
          sample.setParaffinFeatureCid(sampleData.getParaffinFeatureCid());
          sample.setOtherParaffinFeature(sampleData.getOtherParaffinFeature());
        }
        else { // frozen
          sample.setFormatDetailCid(sampleData.getFormatDetailCid());
        }

        //MR 4435 changes for both paraffin and frozen
        sample.setSampleSizeMeetsSpecs(sampleData.getSampleSizeMeetsSpecs());

        sample.commitCopyHelper();
      }
      catch (ObjectNotFoundException onfe) {
        java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());

        String asmId = sampleData.getAsm_id();

        AsmAccessBean asm = new AsmAccessBean(new AsmKey(asmId));

        // otherwise, we need to create a new record...
        sample = new SampleAccessBean();
        sample.setInit_sample_barcode_id(sampleId);
        //default importedYN to N
        sample.setInit_importedYN(FormLogic.DB_NO);
        //set the sample type value
        if (sampleId.startsWith(ValidateIds.PREFIX_SAMPLE_FROZEN)) {
          sample.setInit_sampleTypeCid(ArtsConstants.SAMPLE_TYPE_FROZEN_TISSUE);
        }
        else {
          sample.setInit_sampleTypeCid(ArtsConstants.SAMPLE_TYPE_PARAFFIN_TISSUE);
        }
        sample.setAsm_note(sampleData.getComment());
        sample.setAsm((Asm) asm.getEJBRef());
        sample.setAsm_position(FormLogic.getASMLocation(sampleId));
        sample.setAllocation_ind(null);
        if (sampleId.startsWith(ValidateIds.PREFIX_SAMPLE_PARAFFIN)) {
          sample.setType_of_fixative(sampleData.getType_fixative());
          sample.setParaffinFeatureCid(sampleData.getParaffinFeatureCid());
          sample.setOtherParaffinFeature(sampleData.getOtherParaffinFeature());
        }
        sample.setArdais_acct_key(getAccountKey(asmId));

        //MR 4435 changes.
        sample.setDiMinThicknessPfinCid(sampleData.getDiMinThicknessCid());
        sample.setDiMaxThicknessPfinCid(sampleData.getDiMaxThicknessCid());
        sample.setDiWidthAcrossPfinCid(sampleData.getDiWidthAcrossCid());
        sample.setFormatDetailCid(sampleData.getFormatDetailCid());
        sample.setSampleSizeMeetsSpecs(sampleData.getSampleSizeMeetsSpecs());
        sample.setHoursInFixative(sampleData.getHoursInFixative());

        // MR 7068 -- set ILTDS_SAMPLE.LAST_KNOWN_LOCATION_ID  
        sample.setLastknownlocationid(secInfo.getUserLocationId());
        
        //set the collection and preservation date/time values
        AsmformAccessBean form = new AsmformAccessBean(new AsmformKey(asm.getAsm_form_id()));
        ConsentAccessBean consent = form.getConsent();
        IltdsUtils.setSampleCollectionAndPreservationDates(form, consent, sample);

        sample.commitCopyHelper();

        SamplestatusAccessBean status = new SamplestatusAccessBean();
        status.setInit_argSample(new SampleKey(sampleId));
        status.setInit_argStatus_type_code(FormLogic.SMPL_ASMPRESENT);
        status.setInit_argSample_status_datetime(now);

        status.commitCopyHelper();
                
        //give the sample a sales status of GENERELEASED if appropriate.  This is necessary
        //because the sample might be being created for an ASM linked to an already released
        //case.  This is highly unlikely to happen but the system doesn't prevent it.  
        IltdsUtils.applyPolicyToSample(sample, IltdsUtils.APPLY_POLICY_FOR_SALES_STATUS);

        // Log the sample creation.
        //
        IltdsUtils.logSampleCreation(
          secInfo,
          sampleId,
          null,
          FormLogic.DB_NO,
          asmId,
          sample.getAsm_position(),
          sample.getArdais_acct_key());

        // Attempt to allocate the sample.
        //
        Allocation.allocate(sample, secInfo);
      } // end of inner try block
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  public void ejbActivate() throws java.rmi.RemoteException {
  }
  public void ejbCreate() throws javax.ejb.CreateException, EJBException {
  }
  public void ejbPassivate() throws java.rmi.RemoteException {
  }
  public void ejbRemove() throws java.rmi.RemoteException {
  }
  public javax.ejb.SessionContext getSessionContext() {
    return mySessionCtx;
  }
  public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException {
    mySessionCtx = ctx;
  }

}
