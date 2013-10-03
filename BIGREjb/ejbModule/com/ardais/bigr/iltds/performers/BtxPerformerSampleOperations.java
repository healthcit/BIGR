package com.ardais.bigr.iltds.performers;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.ObjectNotFoundException;
import javax.naming.NamingException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.configuration.LabelPrintingConfiguration;
import com.ardais.bigr.es.beans.ArdaisaccountAccessBean;
import com.ardais.bigr.es.beans.ArdaisaccountKey;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.Asm;
import com.ardais.bigr.iltds.beans.AsmAccessBean;
import com.ardais.bigr.iltds.beans.AsmKey;
import com.ardais.bigr.iltds.beans.AsmformAccessBean;
import com.ardais.bigr.iltds.beans.AsmformKey;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.ConsentKey;
import com.ardais.bigr.iltds.beans.GeolocationKey;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.beans.SampleboxAccessBean;
import com.ardais.bigr.iltds.beans.SampleboxKey;
import com.ardais.bigr.iltds.beans.SamplestatusAccessBean;
import com.ardais.bigr.iltds.bizlogic.Allocation;
import com.ardais.bigr.iltds.bizlogic.SampleFinder;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateImportedCase;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateImportedSample;
import com.ardais.bigr.iltds.btx.BtxDetailsDeleteSamples;
import com.ardais.bigr.iltds.btx.BtxDetailsGetSampleFormSummary;
import com.ardais.bigr.iltds.btx.BtxDetailsGetSampleRegistrationForm;
import com.ardais.bigr.iltds.btx.BtxDetailsImportedSample;
import com.ardais.bigr.iltds.btx.BtxDetailsLogAllocateSingle;
import com.ardais.bigr.iltds.btx.BtxDetailsLogCreateSample;
import com.ardais.bigr.iltds.btx.BtxDetailsLogDeleteSample;
import com.ardais.bigr.iltds.btx.BtxDetailsModifyImportedSample;
import com.ardais.bigr.iltds.btx.BtxDetailsModifyImportedSampleStart;
import com.ardais.bigr.iltds.databeans.IrbVersionData;
import com.ardais.bigr.iltds.databeans.PolicyData;
import com.ardais.bigr.iltds.databeans.SampleType;
import com.ardais.bigr.iltds.databeans.SampleTypeConfiguration;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.SampleSelect;
import com.ardais.bigr.iltds.helpers.TypeFinder;
import com.ardais.bigr.iltds.icp.ejb.session.IcpOperation;
import com.ardais.bigr.iltds.icp.ejb.session.IcpOperationHome;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.ArdaisStaff;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.javabeans.StringList;
import com.ardais.bigr.kc.form.BigrFormInstance;
import com.ardais.bigr.kc.form.BtxDetailsKcFormInstanceDomainObjectSummary;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.form.def.BigrFormDefinitionQueryCriteria;
import com.ardais.bigr.kc.form.def.BtxDetailsKcFormDefinitionLookup;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.BigrValidator;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.IcpUtils;
import com.ardais.bigr.util.PolicyUtils;
import com.ardais.bigr.util.VariablePrecisionDateTime;
import com.gulfstreambio.bigr.Sample;
import com.gulfstreambio.bigr.error.BigrValidationError;
import com.gulfstreambio.bigr.error.BigrValidationErrors;
import com.gulfstreambio.bigr.labels.LabelService;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.form.DataElement;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.FormInstanceQueryCriteria;
import com.gulfstreambio.kc.form.FormInstanceService;
import com.gulfstreambio.kc.form.FormInstanceServiceResponse;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.FormDefinitionTypes;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

/**
 * This performs sample-related ILTDS BTX business transactions.
 */
public class BtxPerformerSampleOperations extends BtxTransactionPerformerBase {

  public BtxPerformerSampleOperations() {
    super();
  }

  /**
   * This is the main BTX entry point for performing the transaction that logs
   * sample allocation.  This transaction was designed to just log an allocation
   * that was performed elsewhere in the code; it doesn't perform the allocation
   * itself.  We do this because allocating a sample isn't really a user-level
   * transaction, it is a back-end function that can be called from several different
   * places.  Nonetheless we need to log it in the BTX history. 
   */
  private BTXDetails performLogAllocateSingle(BtxDetailsLogAllocateSingle btxDetails)
    throws Exception {
    // Since we're just logging here, we expect the inputs to be valid and if they aren't
    // we throw Java exceptions rather than doing what we'd do for a real business transaction
    // (e.g. set properties on the BtxDetails object indicating the problems).  If you are
    // implementing an ordinary business transaction, don't emulate what's done here!

    // Here we ignore most of what is in the btxDetails, all we really need to do is look
    // up the names of the repositories specified in the logicalRepositoryIds input parameter
    // and store them in the logicalRepositoryNames output parameter.  We do make sure that
    // we got a sample id though.

    String sampleId = btxDetails.getSampleBarcodeId();
    if (ApiFunctions.isEmpty(sampleId)) {
      throw new ApiException("btxDetails.getSampleBarcodeId() must not be empty.");
    }

    // Set the btxDetails' logicalRepositoryNames output parameter to be the short names
    // of the repositories identified in btxDetails.getLogicalRepositoryIds().  The order
    // of the list of names must correspond to the order of the list of ids.

    IdList reposIdList = btxDetails.getLogicalRepositoryIds();
    if (reposIdList != null && reposIdList.size() > 0) {
      List reposIds = reposIdList.getList();

      String queryString =
        "select id, short_name from ard_logical_repos where id in "
          + ApiFunctions.makeBindParameterList(reposIds.size());

      // First we'll build a map of ids to short names, then we'll put those into
      // the result list in the same order as the ids in the input list.

      Map nameMap = new HashMap();

      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(queryString);

        ApiFunctions.bindBindParameterList(pstmt, 1, reposIds);

        rs = ApiFunctions.queryDb(pstmt, con);
        while (rs.next()) {
          String reposId = rs.getString(1);
          String reposName = rs.getString(2);
          nameMap.put(reposId, reposName);
        }
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }

      // If a repository id in the input list is invalid, we'll discover that below
      // when we don't find an entry in the map for it.  For this routine to work correctly,
      // we need to be sure that the number of things in the map is the same as the number
      // of things in the input list.
      // 
      StringList reposNames = new StringList();
      Iterator idIter = reposIdList.iterator();
      while (idIter.hasNext()) {
        String reposId = (String) idIter.next();
        String reposName = (String) nameMap.get(reposId);
        if (ApiFunctions.isEmpty(reposName)) {
          // Maybe an invalid repos id (and therefore no entry in the map) or maybe an
          // empty name, in either case not valid.
          throw new ApiException("Could not get short name for logical repository " + reposId);
        }
        reposNames.add(reposName);
      }

      btxDetails.setLogicalRepositoryNames(reposNames);
    } // end populate btxDetails' logicalRepositoryNames output parameter

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that logs
   * sample creation.  This transaction was designed to just log a sample creation
   * that was performed elsewhere in the code; it doesn't perform the creation
   * itself.  We do this because creating a sample isn't really a user-level
   * transaction, it is a back-end function that can be called from several different
   * places.  Nonetheless we need to log it in the BTX history. 
   */
  private BTXDetails performLogCreateSample(BtxDetailsLogCreateSample btxDetails)
    throws Exception {
    // Since we're just logging here, we expect the inputs to be valid and if they aren't
    // we throw Java exceptions rather than doing what we'd do for a real business transaction
    // (e.g. set properties on the BtxDetails object indicating the problems).  If you are
    // implementing an ordinary business transaction, don't emulate what's done here!

    // Here we ignore most of what is in the btxDetails, all we really need to do is look
    // up various things so that we can set the output parameters.
    // and store them in the logicalRepositoryNames output parameter.

    if (ApiFunctions.isEmpty(btxDetails.getSampleBarcodeId())) {
      throw new ApiException("btxDetails.getSampleBarcodeId() must not be empty.");
    }

    //don't check the ASM information if the sample is imported
    boolean importedSample = FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getImportedYN());
    String asmId = btxDetails.getAsmId();
    if (!importedSample) {
      if (ApiFunctions.isEmpty(asmId)) {
        throw new ApiException("btxDetails.getAsmId() must not be empty.");
      }

      if (ApiFunctions.isEmpty(btxDetails.getAsmPosition())) {
        throw new ApiException("btxDetails.getAsmPosition() must not be empty.");
      }
    }

    String accountId = btxDetails.getAccountId();
    if (ApiFunctions.isEmpty(accountId)) {
      throw new ApiException("btxDetails.getAccountId() must not be empty.");
    }

    // Set the ConsentId output parameter.  The ASM object might not exist yet (for example,
    // when the sample is created by a DI box scan prior to ASM Form data entry), and if
    // so we can't determine the case id.  We set ConsentId to null in this situation.
    {
      String consentId = null;
      if (!importedSample) {
        try {
          AsmAccessBean asm = new AsmAccessBean(new AsmKey(asmId));
          consentId = asm.getConsent_id();
        }
        catch (ObjectNotFoundException e) {
          // The ASM object doesn't exist yet.
          // Do nothing, the ConsentId output parameter will end up being null, as it should.
        }
      }
      btxDetails.setConsentId(consentId);
    }

    // Set the AccountName output parameter.
    {
      ArdaisaccountAccessBean account =
        new ArdaisaccountAccessBean(new ArdaisaccountKey(accountId));
      btxDetails.setAccountName(account.getArdais_acct_company_desc());
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  //validate that the samples passed in can all be deleted
  private BtxDetailsDeleteSamples validatePerformDeleteSamples(BtxDetailsDeleteSamples btxDetails)
    throws Exception {
    List sampleDatas = btxDetails.getSampleDatas();
    if (ApiFunctions.isEmpty(sampleDatas)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "sample datas to delete"));
    }
    else {
      //validate that each sample is able to be deleted
      Iterator iterator = sampleDatas.iterator();
      String sampleId;
      while (iterator.hasNext()) {
        sampleId = ((SampleData) iterator.next()).getSampleId();
        //make sure the sample exists
        com.ardais.bigr.iltds.databeans.SampleData sampleData =
          new com.ardais.bigr.iltds.databeans.SampleData();
        sampleData.setSample_id(sampleId);
        SecurityInfo secInfo = btxDetails.getLoggedInUserSecurityInfo();
        IcpOperationHome home = (IcpOperationHome) EjbHomes.getHome(IcpOperationHome.class);
        IcpOperation icpOp = home.create();
        sampleData = icpOp.getSampleData(sampleData, secInfo, false, false);
        if (!sampleData.isExists()) {
          btxDetails.addActionError(
            new BtxActionError("iltds.error.general.notFoundSampleId", sampleId));
        }
        else {
          //make sure the sample hasn't progressed past the ASMPRESENT stage
          Iterator statusIterator =
            ApiFunctions.safeList(FormLogic.getStatusValuesForSample(sampleId)).iterator();
          while (statusIterator.hasNext()) {
            if (!FormLogic.SMPL_ASMPRESENT.equalsIgnoreCase((String) statusIterator.next())) {
              btxDetails.addActionError(
                new BtxActionError("iltds.error.deleteSample.sampleCanNotBeDeleted", sampleId));
              break;
            }
          }
        }
      }
    }
    return btxDetails;
  }

  //method to do the work of deleting a list of samples
  private BtxDetailsDeleteSamples performDeleteSamples(BtxDetailsDeleteSamples btxDetails)
    throws Exception {

    List sampleIds = new ArrayList(btxDetails.getSampleDatas().size());
    Iterator iterator = btxDetails.getSampleDatas().iterator();
    while (iterator.hasNext()) {
      sampleIds.add(((SampleData) iterator.next()).getSampleId());
    }

    StringBuffer sampleSql = new StringBuffer();
    sampleSql.append("delete from iltds_sample where sample_barcode_id in ");
    sampleSql.append(ApiFunctions.makeBindParameterList(sampleIds.size()));

    StringBuffer statusSql = new StringBuffer();
    statusSql.append("delete from iltds_sample_status where sample_barcode_id in ");
    statusSql.append(ApiFunctions.makeBindParameterList(sampleIds.size()));

    StringBuffer lrSql = new StringBuffer();
    lrSql.append("delete from ard_logical_repos_item where item_id in ");
    lrSql.append(ApiFunctions.makeBindParameterList(sampleIds.size()));

    Connection conn = null;
    PreparedStatement sampleStmt = null;
    PreparedStatement statusStmt = null;
    PreparedStatement lrStmt = null;
    try {
      conn = ApiFunctions.getDbConnection();
      statusStmt = conn.prepareStatement(statusSql.toString());
      ApiFunctions.bindBindParameterList(statusStmt, 1, sampleIds);
      statusStmt.executeUpdate();
      lrStmt = conn.prepareStatement(lrSql.toString());
      ApiFunctions.bindBindParameterList(lrStmt, 1, sampleIds);
      lrStmt.executeUpdate();
      sampleStmt = conn.prepareStatement(sampleSql.toString());
      ApiFunctions.bindBindParameterList(sampleStmt, 1, sampleIds);
      sampleStmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(conn);
      ApiFunctions.close(sampleStmt);
      ApiFunctions.close(statusStmt);
      ApiFunctions.close(lrStmt);
    }

    //now that we've deleted the samples, log each deletion
    iterator = btxDetails.getSampleDatas().iterator();
    while (iterator.hasNext()) {
      BtxDetailsLogDeleteSample btxLogDetails = new BtxDetailsLogDeleteSample();
      btxLogDetails.setBeginTimestamp(btxDetails.getBeginTimestamp());
      btxLogDetails.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo());
      btxLogDetails.setSampleData((SampleData) iterator.next());
      btxLogDetails.setDeleteReason(btxDetails.getDeleteReason());
      Btx.perform(btxLogDetails, "iltds_sample_logDeleteSample");
    }
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that logs
   * sample deletion.  This transaction was designed to just log a sample deletion
   * that was performed elsewhere in the code; it doesn't perform the deletion itself.
   */
  private BTXDetails performLogDeleteSample(BtxDetailsLogDeleteSample btxDetails)
    throws Exception {
    // Since we're just logging here, we expect the inputs to be valid and if they aren't
    // we throw Java exceptions rather than doing what we'd do for a real business transaction
    // (e.g. set properties on the BtxDetails object indicating the problems).  If you are
    // implementing an ordinary business transaction, don't emulate what's done here!

    if (btxDetails.getSampleData() == null) {
      throw new ApiException("btxDetails.getSampleData() must not be empty.");
    }

    if (ApiFunctions.isEmpty(btxDetails.getSampleData().getSampleId())) {
      throw new ApiException("btxDetails.getSampleData().getSampleId() must not be empty.");
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * creating a new imported sample.
   */
  private BTXDetails performCreateImportedSampleStart(BtxDetailsCreateImportedSample btxDetails)
    throws Exception {
    
    String accountId = btxDetails.getLoggedInUserSecurityInfo().getAccount();
    SampleData sampleData = btxDetails.getSampleData();
    
    sampleData.setImportedYN(FormLogic.DB_YES);
    sampleData.setArdaisAcctKey(accountId);

    ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
    ListGenerator listBean = home.create();
    btxDetails.setConsentChoices(listBean.findActiveConsentVersions(accountId, false, false));
    btxDetails.setPolicyChoices(PolicyUtils.getPoliciesByAccountId(accountId, false, false, true));
    btxDetails.setPreparedByChoices(getSamplePreparedByChoices(btxDetails));
    btxDetails.setActionForwardTXIncomplete("success");

    return btxDetails;
  }

  private BTXDetails performCreateImportedSamplePrepare(BtxDetailsCreateImportedSample btxDetails)
    throws Exception {

    SampleData sampleData = btxDetails.getSampleData();
    String accountId = btxDetails.getLoggedInUserSecurityInfo().getAccount();

    sampleData.setImportedYN(FormLogic.DB_YES);
    sampleData.setArdaisAcctKey(accountId);

    btxDetails.setPreparedByChoices(getSamplePreparedByChoices(btxDetails));
    String policyId = null;
    PolicyData policyData = null;
    
    if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getNewCase())) {
      // if irb is specified
      
      if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getLinkedIndicator())) {
        String consentVersionId = btxDetails.getConsentVersionId();
        IrbVersionData irbData = IltdsUtils.getIrbVersionData(consentVersionId);
        policyId = irbData.getIrbPolicyId();
      }
      else if (FormLogic.DB_NO.equalsIgnoreCase(btxDetails.getLinkedIndicator())) {
        policyId = btxDetails.getPolicyId();
      }
      policyData = PolicyUtils.getPolicyData(policyId);
    }
    else {
      String consentId = sampleData.getConsentId();
      if (ApiFunctions.isEmpty(consentId)) {
        consentId = IltdsUtils.findConsentFromCustomerId(sampleData.getConsentAlias(), accountId).getConsentId();
      }
      policyData = IltdsUtils.getPolicyForConsent(consentId);
      policyId = policyData.getPolicyId();
    }
    btxDetails.setPolicyId(policyId);
    SampleTypeConfiguration config = PolicyUtils.getSampleTypeConfiguration(policyId);
    sampleData.setSampleTypeConfiguration(config);
    
    // If the sample type is specified, then get the registration form for the sample.  This will 
    // be the situation when we've just saved a sample and we're creating another one using the
    // same sample type and thus same registration form.
    String sampleTypeCui = sampleData.getSampleTypeCui();
    if (!ApiFunctions.isEmpty(sampleTypeCui)) {
      String regFormId = 
        policyData.getSampleTypeConfiguration().getSampleType(sampleTypeCui).getRegistrationFormId();
      FormDefinitionServiceResponse response = 
        FormDefinitionService.SINGLETON.findFormDefinitionById(regFormId);
      DataFormDefinition regForm = response.getDataFormDefinition();
      if ((regForm != null) && regForm.isEnabled()) {
        sampleData.setRegistrationForm(regForm);
      }
    }
    
    // Get the map of sample type cuis to registration form ids.
    btxDetails.setSampleRegistrationFormIds(getSampleRegistrationFormIds(config));
    
    // Get the form definitions for samples in the user's account.
    btxDetails.setFormDefinitions(getAvailableFormDefinitions(btxDetails.getLoggedInUserSecurityInfo()));
    
    populateLabelPrintingData(btxDetails);
    
    populateSampleAliasRequired(btxDetails);
    
    btxDetails.setActionForwardTXIncomplete("success");
    return btxDetails;
  }

  private BTXDetails validatePerformCreateImportedSamplePrepare(BtxDetailsCreateImportedSample btxDetails) 
  throws Exception {

    validateDonorAndCaseInfo(btxDetails);    
    if (btxDetails.getActionErrors().size() > 0) {
      return btxDetails;
    }
    
    String policyId = null;
    if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getNewCase())) {
      // if irb is specified      
      if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getLinkedIndicator())) {
        String consentVersionId = btxDetails.getConsentVersionId();
        IrbVersionData irbData = IltdsUtils.getIrbVersionData(consentVersionId);
        policyId = irbData.getIrbPolicyId();
      }
      else if (FormLogic.DB_NO.equalsIgnoreCase(btxDetails.getLinkedIndicator())) {
        policyId = btxDetails.getPolicyId();
      }
    }
    else {
      SampleData sampleData = btxDetails.getSampleData();
      String consentId = sampleData.getConsentId();
      if (ApiFunctions.isEmpty(consentId)) {
        String accountId = btxDetails.getLoggedInUserSecurityInfo().getAccount();
        ConsentData consentData = 
          IltdsUtils.findConsentFromCustomerId(sampleData.getConsentAlias(), accountId);
        consentId = consentData.getConsentId();
      }
      PolicyData policyData = IltdsUtils.getPolicyForConsent(consentId);
      policyId = policyData.getPolicyId();
    }
    if (!ApiFunctions.isEmpty(policyId)) {
      SampleTypeConfiguration sampleTypeConfiguration = 
        PolicyUtils.getSampleTypeConfiguration(policyId);
      if (sampleTypeConfiguration.getSupportedSampleTypesAsLVS().getCount() == 0) {
        PolicyData policyData = PolicyUtils.getPolicyData(policyId);
        btxDetails.addActionError(new BtxActionError("dataImport.error.noCollectibleSampleTypes", policyData.getPolicyName()));   
      }
    }

    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performCreateImportedSample performer method.
   */
  private BTXDetails validatePerformCreateImportedSample(BtxDetailsCreateImportedSample btxDetails)
    throws Exception {

    // Set some lists to be returned if there is an error.
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    btxDetails.setPreparedByChoices(getSamplePreparedByChoices(btxDetails));
    btxDetails.setFormDefinitions(getAvailableFormDefinitions(securityInfo));
    populateLabelPrintingData(btxDetails);
    
    populateSampleAliasRequired(btxDetails);

    // First, make sure that everything that was checked in the preparation step is still valid.
    validatePerformCreateImportedSamplePrepare(btxDetails);
    
    // Validate the sample id.
    String sampleId = validateSampleIdForCreate(btxDetails);
    
    // Validate the policy id.  If the policy id could not be determined, then return since all
    // remaining validation relies on the policy.
    String policyId = validatePolicyId(btxDetails);
    if (ApiFunctions.isEmpty(policyId)) {
      return btxDetails;      
    }
    
    // Get the sample type configuration from the policy and set it in the sample data.  This will 
    // be used by much of the remaining validation.  If we can't find it for some reason, then
    // return an error.
    SampleTypeConfiguration config = PolicyUtils.getSampleTypeConfiguration(policyId);
    if (config == null) {
      btxDetails.addActionError(
          new BtxActionError("generic.message", "The sample type configuration could not be found in the policy."));
      return btxDetails;            
    }
    SampleData sampleData = btxDetails.getSampleData();
    sampleData.setSampleTypeConfiguration(config);
    btxDetails.setSampleRegistrationFormIds(getSampleRegistrationFormIds(config));

    // Validate the sample type.  If the sample type is not valid then return since we won't
    // be able to find the registration form.
    String sampleType = validateSampleType(btxDetails);
    if (ApiFunctions.isEmpty(sampleType)) {
      return btxDetails;
    }    
    
    // Get the registration form from the policy before validating the rest of the sample
    // information.  If we can't find the registration form or it is not enabled, then return
    // since the rest of validation relies on the registration form.
    String regFormId = config.getSampleType(sampleType).getRegistrationFormId();
    if (regFormId == null) {
      btxDetails.addActionError(
          new BtxActionError("orm.error.policy.requiredRegistrationForm", sampleId));
      return btxDetails;
    }
    else {
      FormDefinitionServiceResponse response =
        FormDefinitionService.SINGLETON.findFormDefinitionById(regFormId);
      DataFormDefinition formDef = response.getDataFormDefinition();
      if ((formDef == null) || !formDef.isEnabled()) {
        btxDetails.addActionError(
            new BtxActionError("orm.error.policy.invalidRegistrationForm", sampleId, "could not be found."));
        return btxDetails;
      }
      else {
        sampleData.setRegistrationForm(formDef);
      }
    }
    
    // Validate the remaining BIGR core elements.
    validateSampleInformation(btxDetails, null);
    
    // Validate the KC data elements.
    validateKcFormInstanceForCreate(btxDetails);
    
    //validate all information for printing has been provided.  Although printing is
    //handled outside of this class (in the ImportedSampleAction), we need to make sure 
    //that all printing information has been provided and is valid here. It would be 
    //annoying to the user to let them proceed with creating a sample and then tell them 
    //they provided invalid printing information when it is too late for them to fix the problem.
    validateLabelPrintingData(btxDetails, false);

    return btxDetails;
  }

  private BigrFormDefinition[] getAvailableFormDefinitions(SecurityInfo securityInfo) {    
    Timestamp now = new Timestamp(System.currentTimeMillis());
    BtxDetailsKcFormDefinitionLookup btxDetailsLookup = new BtxDetailsKcFormDefinitionLookup();
    btxDetailsLookup.setBeginTimestamp(now);
    btxDetailsLookup.setLoggedInUserSecurityInfo(securityInfo);
    BigrFormDefinitionQueryCriteria criteria = new BigrFormDefinitionQueryCriteria();
    criteria.setEnabled(true);
    criteria.setAccount(securityInfo.getAccount());
    criteria.addFormType(FormDefinitionTypes.DATA);    
    criteria.addObjectType(TagTypes.DOMAIN_OBJECT_VALUE_SAMPLE);
    criteria.setUse(TagTypes.USES_VALUE_ANNOTATION);
    btxDetailsLookup.setQueryCriteria(criteria);
    btxDetailsLookup = 
      (BtxDetailsKcFormDefinitionLookup) Btx.perform(btxDetailsLookup, "kc_form_defs_lookup");
    if (btxDetailsLookup.isTransactionCompleted()) {
      return btxDetailsLookup.getFormDefinitions();
    }
    else {
      return new BigrFormDefinition[0];
    }    
  }
  
  private void validateDonorAndCaseInfo(BtxDetailsCreateImportedSample btxDetails)
    throws NamingException, CreateException, RemoteException, ClassNotFoundException {
    String donorIdFromDonor = null;
    String donorIdFromCase = null;

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String accountId = securityInfo.getAccount();
    SampleData sampleData = btxDetails.getSampleData();

    //if the user is creating a new donor, make sure a customer id has been specified and
    //that id is unique within the account
    //Note that it would have been preferable to re-use the validation code that
    //create donor is using, but that validation is done in the DonorHelper class and
    //moving that from BIGRWeb to BIGREjb turned out to be painful.  Since there isn't
    //much to validate just replicate the checks here
    if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getNewDonor())) {
      //MR8036 - make sure they have been granted the Create Donor privilege
      if (!btxDetails.getLoggedInUserSecurityInfo().isHasPrivilege(SecurityInfo.PRIV_DATA_IMPORT_CREATE_DONOR)) {
        btxDetails.addActionError(new BtxActionError("error.noPrivilege", "create donors"));
      }
      else {
        //MR7905
        sampleData.setDonorAlias(ApiFunctions.safeTrim(sampleData.getDonorAlias()));
        //make sure customer id has been specified
        if (ApiFunctions.isEmpty(sampleData.getDonorAlias())) {
          btxDetails.addActionError(new BtxActionError("error.noValue", "Donor Alias"));
        }
        //make sure customer id is unique within the account
        else {
          if (!PdcUtils
            .isCustomerIdUniqueInAccount(
              true,
              null,
              sampleData.getDonorAlias(),
              accountId)) {
            btxDetails.addActionError(
              new BtxActionError("dataImport.error.existingEntityWithCustomerId", "donor"));
          }
        }
        // Make sure that an enabled donor registration form is present, and that it does not
        // not have any required elements.
        String regFormId = IltdsUtils.getAccountDonorRegistrationFormId(securityInfo.getAccount());
        if (ApiFunctions.isEmpty(regFormId)) {
          btxDetails.addActionError(
              new BtxActionError("dataImport.error.noDonorRegistrationFormSpecifiedForAccount"));          
        }
        else {
          FormDefinitionServiceResponse r = 
            FormDefinitionService.SINGLETON.findFormDefinitionById(regFormId);
          DataFormDefinition form = r.getDataFormDefinition();
          if ((form == null) || !form.isEnabled()) {
            btxDetails.addActionError(
                new BtxActionError("dataImport.error.noDonorRegistrationFormSpecifiedForAccount"));          
          }
          else {
            if (formHasRequired(form)) {
              btxDetails.addActionError(
                  new BtxActionError("dataImport.error.donorRegistrationFormHasRequired"));              
            }
          }
        }        
      }
    }
    //if they are not creating a new donor, make sure they have specified an Ardais id or 
    //customer id (but not both) and that the specified donor exists and belongs to
    //the user's acccount.  Note that if the sample is being created as part of a derivative
    //operation and the user is in the Ardais account, the donor and case do not have to
    //belong to the user's account.  This is done to allow Cytmoyx users to create child
    //samples either:
    //1) on behalf of their customers (ie. Merck parent sample into Merck child samples)
    //2) for themselves from parent samples they "own" but were collected at other institutions
    //   (see MR8395 for an example - Ardais user trying to create child samples from a
    //    parent collected at Beth Israel but the parent is "owned" by Cytomyx)
    else {
      String ardaisId = sampleData.getArdaisId();
      String donorCustomerId = sampleData.getDonorAlias();
      //make sure the user has specified either donor ids or customer ids
      if (ApiFunctions.isEmpty(ardaisId) && ApiFunctions.isEmpty(donorCustomerId)) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.donorNotSpecified"));
      }
      //make sure the user has not specified both ardais ids and customer ids
      if ((!ApiFunctions.isEmpty(ardaisId)) && (!ApiFunctions.isEmpty(donorCustomerId))) {
        btxDetails.addActionError(
          new BtxActionError("dataImport.error.cannotMixArdaisAndCustomerIds"));
      }
      //make sure that if ardais ids are specified they match
      if (!ApiFunctions.isEmpty(ardaisId)) {
        //if they match, make sure there is an existing donor with that id and 
        //that the donor belongs to this users account
        DonorData donor = new DonorData();
        donor.setArdaisId(ardaisId);
        DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
        DDCDonor donorOperation = home.create();
        DonorData donorData = donorOperation.getDonorProfile(donor);
        boolean inAccount = accountId.equals(
            donorData.getArdaisAccountKey());
        boolean allowNonAccountDonor = btxDetails.isDerivativeOperationAction() &&
                                       btxDetails.getLoggedInUserSecurityInfo().isInRoleSystemOwner();
        if (!inAccount && !allowNonAccountDonor) {
          btxDetails.addActionError(
            new BtxActionError("dataImport.error.nonExistentEntity", "donor", "Ardais Id"));
        }
        else {
          donorIdFromDonor = donorData.getArdaisId();
        }
      }
      //make sure that if customer ids are specified they match
      if (!ApiFunctions.isEmpty(donorCustomerId)) {
        //if they match, make sure there is an existing donor with that id and 
        //that the donor belongs to this users account
        boolean donorFound = false;
        String derivedArdaisId = PdcUtils.findDonorIdFromCustomerId(donorCustomerId, accountId);
        if (!ApiFunctions.isEmpty(derivedArdaisId)) {
          DonorData donor = new DonorData();
          donor.setArdaisId(derivedArdaisId);
          DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
          DDCDonor donorOperation = home.create();
          DonorData donorData = donorOperation.getDonorProfile(donor);
          boolean inAccount = accountId.equals(donorData.getArdaisAccountKey());
          if (inAccount) {
            donorFound = true;
            donorIdFromDonor = donorData.getArdaisId();
          }
        }
        if (!donorFound) {
          btxDetails.addActionError(
            new BtxActionError("dataImport.error.nonExistentEntity", "donor", "alias"));
        }
      }
    }
    
    //now validate the case information
    //since they cannot be creating a new donor but not creating a new case, check for that first
    //and if that is the case return an error and do not performe any further validation on the case
    if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getNewDonor())
      && !FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getNewCase())) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.cannotCreateDonorButNotCase"));
    }
    else {
      //if they are creating a new case, make sure all required information has been provided
      if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getNewCase())) {
        //MR8036 - make sure they have been granted the Create Case privilege
        if (!btxDetails.getLoggedInUserSecurityInfo().isHasPrivilege(SecurityInfo.PRIV_DATA_IMPORT_CREATE_CASE)) {
          btxDetails.addActionError(new BtxActionError("error.noPrivilege", "create cases"));
        }
        else {
          //MR7905
          sampleData.setConsentAlias(ApiFunctions.safeTrim(sampleData.getConsentAlias()));
          //customer id validation
          //make sure an id has been specified
          String customerId = sampleData.getConsentAlias();
          if (ApiFunctions.isEmpty(customerId)) {
            btxDetails.addActionError(new BtxActionError("error.noValue", "Case Alias"));
          }
          //if an id has been specified, make sure there is no other case under this account with the
          //same id
          else {
            ConsentData consent = IltdsUtils.findConsentFromCustomerId(customerId, accountId);
            if (consent != null) {
              btxDetails.addActionError(
                new BtxActionError("dataImport.error.existingEntityWithCustomerId", "case"));
            }
          }
          //linked/unlinked validation
          String linked = btxDetails.getLinkedIndicator();
          if (ApiFunctions.isEmpty(linked)) {
            btxDetails.addActionError(new BtxActionError("dataImport.error.linkedNotSpecified"));
          }
          //if the linked/unlinked information is present, make sure required info baased on that info
          //is present
          else {
            PolicyData policy = null;
            if ("Y".equalsIgnoreCase(linked)) {
              String consentVersion = btxDetails.getConsentVersionId();
              if (ApiFunctions.isEmpty(consentVersion)) {
                btxDetails.addActionError(new BtxActionError("dataImport.error.consentVersionNotSpecified"));
              }
              else {
                String ardaisAcctKey = sampleData.getArdaisAcctKey();
                ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
                ListGenerator list = home.create();
                List validChoices = list.findActiveConsentVersions(ardaisAcctKey, false, false);
                Iterator iterator = validChoices.iterator();
                boolean foundChoice = false;
                IrbVersionData irb = null;
                while (iterator.hasNext() && !foundChoice) {
                  irb = (IrbVersionData)iterator.next();
                  foundChoice = consentVersion.equalsIgnoreCase(irb.getConsentVersionId());
                }
                if (!foundChoice) {
                  btxDetails.addActionError(new BtxActionError("iltds.error.createCase.invalidPolicyOrIRB", "IRB Protocol/Consent Version"));
                }
                else {
                  policy = PolicyUtils.getPolicyData(irb.getIrbPolicyId());                  
                }
              }
            }
            if ("N".equalsIgnoreCase(linked)) {
              String consentPolicy = btxDetails.getPolicyId();
              if (ApiFunctions.isEmpty(consentPolicy)) {
                btxDetails.addActionError(new BtxActionError("dataImport.error.consentPolicyNotSpecified"));
              }
              else {
                List validChoices = PolicyUtils.getPoliciesByAccountId(sampleData.getArdaisAcctKey(), false, false, true);
                Iterator iterator = validChoices.iterator();
                boolean foundChoice = false;
                while (iterator.hasNext() && !foundChoice) {
                  policy = (PolicyData)iterator.next();
                  foundChoice = consentPolicy.equalsIgnoreCase(policy.getPolicyId());
                }
                if (!foundChoice) {
                  btxDetails.addActionError(new BtxActionError("iltds.error.createCase.invalidPolicyOrIRB", "policy"));
                }
              }
            }
            // Make sure that an enabled case registration form is present, and that it does not
            // not have any required elements.
            if (policy != null) {
              String formId = policy.getCaseRegistrationFormId();
              FormDefinitionServiceResponse response = 
                FormDefinitionService.SINGLETON.findFormDefinitionById(formId);
              DataFormDefinition form = response.getDataFormDefinition();
              if ((form == null) || !form.isEnabled()) {
                btxDetails.addActionError(new BtxActionError("dataImport.error.noCaseRegForm"));      
              }
              else {
                if (formHasRequired(form)) {
                  btxDetails.addActionError(
                      new BtxActionError("dataImport.error.caseRegistrationFormHasRequired"));              
                }                
              }
            }
          }
        }
      }
      //if they are not creating a new case, make sure they have specified an Ardais case id
      //or customer id (but not both), and that the specified case exists and belongs to
      //the user's account.
      else {
        String caseId = sampleData.getConsentId();
        String caseCustomerId = sampleData.getConsentAlias();
        //make sure the user has specified either case ids or customer ids
        if (ApiFunctions.isEmpty(caseId)
          && ApiFunctions.isEmpty(caseCustomerId)) {
          btxDetails.addActionError(new BtxActionError("dataImport.error.caseNotSpecified"));
        }
        //make sure the user has not specified both case ids and customer ids
        if ((!ApiFunctions.isEmpty(caseId))
          && (!ApiFunctions.isEmpty(caseCustomerId))) {
          btxDetails.addActionError(
            new BtxActionError("dataImport.error.cannotMixCaseAndCustomerIds"));
        }
        //make sure that if case ids are specified they match
        if (!ApiFunctions.isEmpty(caseId)) {
          //if they match, make sure there is an existing case with that id and 
          //that the case belongs to this users account
          ConsentAccessBean consent = new ConsentAccessBean();
          try {
            AccessBeanEnumeration enum1 = (AccessBeanEnumeration) consent.findByConsentID(caseId);
            consent = (ConsentAccessBean) enum1.nextElement();
            boolean inAccount = accountId.equals(consent.getArdais_acct_key());
            boolean allowNonAccountCase = btxDetails.isDerivativeOperationAction() &&
                                           btxDetails.getLoggedInUserSecurityInfo().isInRoleSystemOwner();
            if (!inAccount && !allowNonAccountCase) {
              btxDetails.addActionError(
                new BtxActionError("dataImport.error.nonExistentEntity", "case", "Case Id"));
            }
            else {
              donorIdFromCase = consent.getArdais_id();
            }
          }
          catch (Exception e) {
            btxDetails.addActionError(
              new BtxActionError("dataImport.error.nonExistentEntity", "case", "Case Id"));
          }
        }
        //make sure that if customer ids are specified they match
        if (!ApiFunctions.isEmpty(caseCustomerId)) {
            //if they match, make sure there is an existing case with that id and 
            //that the case belongs to this users account
          ConsentData consent = IltdsUtils.findConsentFromCustomerId(caseCustomerId, accountId);
          if (consent == null) {
            btxDetails.addActionError(
              new BtxActionError("dataImport.error.nonExistentEntity", "case", "alias"));
          }
          else {
            donorIdFromCase = consent.getDonorId();
          }
        }
      }
    }
    
    //if the user is not creating a new donor or case, make sure the specified case belongs to the
    //specified donor
    if (!FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getNewDonor())
      && !FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getNewCase())) {
      if (!ApiFunctions.isEmpty(donorIdFromDonor)
        && !ApiFunctions.isEmpty(donorIdFromCase)
        && !ApiFunctions.safeString(donorIdFromDonor).equalsIgnoreCase(donorIdFromCase)) {
        btxDetails.addActionError(
          new BtxActionError("dataImport.error.consentDoesNotBelongToDonor"));
      }
    }
  }

  /**
   * This is the main BTX entry point for performing the transaction that creates a new 
   * imported sample.
   */
  private BTXDetails performCreateImportedSample(BtxDetailsCreateImportedSample btxDetails)
    throws Exception {
    //if the user is creating a new donor, create the donor

    SampleData sampleData = btxDetails.getSampleData();
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String accountId = securityInfo.getAccount();

    if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getNewDonor())) {
      DonorData donorData = new DonorData();
      donorData.setCreateUser(btxDetails.getLoggedInUserSecurityInfo().getUsername());
      donorData.setArdaisAccountKey(accountId);
      donorData.setCustomerId(sampleData.getDonorAlias());
      donorData.setImportedYN(FormLogic.DB_YES);
      String regFormId = IltdsUtils.getAccountDonorRegistrationFormId(accountId);
      donorData.setRegistrationFormId(regFormId);
      //SWP-1079 - if the registration form has KC data elements, create a form instance for the
      //donor.  All required validation for this step (making sure a donor registration form has
      //been specified, checking that it has no required data elements, etc) has already been
      //performed by the time we've arrived here (see validateDonorAndCaseInfo() which is called
      //from validatePerformCreateImportedSample() calling 
      //validatePerformCreateImportedSamplePrepare())
      FormDefinitionServiceResponse r = 
        FormDefinitionService.SINGLETON.findFormDefinitionById(regFormId);
      DataFormDefinition formDefinition = r.getDataFormDefinition();
      DataFormDefinitionDataElement[] dataElements = formDefinition.getDataDataElements();
      if (dataElements.length > 0) {
        donorData.setRegistrationForm(formDefinition);
        FormInstance form = new FormInstance();
        //populate information on the form.  Since we do not yet know the id of the 
        //donor, leave it blank and it will be populated inside the createDonorProfile method
        form.setFormDefinitionId(regFormId);
        form.setDomainObjectType(TagTypes.DOMAIN_OBJECT_VALUE_DONOR);
        //create the data elements on the form (all values will be blank)
        for (int i = 0; i < dataElements.length; i++) {
          form.addDataElement(new DataElement(dataElements[i].getCui()));
        }
        donorData.setFormInstance(form);
      }
      
      SecurityInfo secInfo = btxDetails.getLoggedInUserSecurityInfo();
      
      DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = home.create();
      DonorData returnedDonorData =
        donorOperation.createDonorProfile(donorData, secInfo);
      String donorId = returnedDonorData.getArdaisId();
      //set the donor id on the btxDetails since we need it later on
      sampleData.setArdaisId(donorId);
    }
    //otherwise determine the donor from the input data if necessary
    else {
      if (ApiFunctions.isEmpty(sampleData.getArdaisId())) {
        String donorId =
          PdcUtils.findDonorIdFromCustomerId(sampleData.getDonorAlias(), accountId);
        //set the donor id on the btxDetails since we need it later on
        sampleData.setArdaisId(donorId);
      }
    }
    //if the donor alias is not already populated on the sample, populate it now so this information
    //can be included in the history record
    if (ApiFunctions.isEmpty(sampleData.getDonorAlias())) {
      DonorData donor = new DonorData();
      donor.setArdaisId(sampleData.getArdaisId());
      DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = home.create();
      donor = donorOperation.getDonorProfile(donor);
      sampleData.setDonorAlias(donor.getCustomerId());
    }

    //if the user is creating a new case, create the case
    if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getNewCase())) {
      BtxDetailsCreateImportedCase btxDetailsCase = new BtxDetailsCreateImportedCase();
      btxDetailsCase.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo());
      btxDetailsCase.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
      btxDetailsCase.setTransactionType("iltds_create_imported_case");
      btxDetailsCase.setArdaisId(sampleData.getArdaisId());
      btxDetailsCase.setArdaisId2(sampleData.getArdaisId());
      btxDetailsCase.setCustomerId(sampleData.getConsentAlias());
      btxDetailsCase.setImportedYN(FormLogic.DB_YES);
      btxDetailsCase.setUserId(btxDetails.getLoggedInUserSecurityInfo().getUsername());
      btxDetailsCase.setArdaisAcctKey(sampleData.getArdaisAcctKey());
      btxDetailsCase.setLinkedIndicator(btxDetails.getLinkedIndicator());
      if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getLinkedIndicator())) {
        btxDetailsCase.setConsentVersionId(btxDetails.getConsentVersionId());
      }
      else {
        btxDetailsCase.setPolicyId(btxDetails.getPolicyId());
      }
      BtxPerformerCaseOperations btx = new BtxPerformerCaseOperations();
      btx.perform(btxDetailsCase);
      //if the create case transaction didn't complete return the error(s)
      if (!btxDetailsCase.isTransactionCompleted()) {
        if (!btxDetailsCase.getActionErrors().empty()) {
          btxDetails.addActionErrors(btxDetailsCase.getActionErrors());
          btxDetails.setActionForwardTXIncomplete("retry");
          return btxDetails;
        }
        else {
          StringBuffer buff = new StringBuffer(100);
          buff.append("Unable to create new case when creating new sample, and create case returned no errors.");
          throw new ApiException(buff.toString());
        }
      }
      String caseId = btxDetailsCase.getConsentId();
      //set the consent id on the btxDetails, since we need it later on
      sampleData.setConsentId(caseId);
    }
    //otherwise determine the case from the input data
    else {
      if (ApiFunctions.isEmpty(sampleData.getConsentId())) {
        String caseId = IltdsUtils.findConsentFromCustomerId(sampleData.getConsentAlias(), accountId).getConsentId();
        //set the consent id on the btxDetails, since we need it later on
        sampleData.setConsentId(caseId);
      }
    }
    //if the case alias is not already populated on the sample, populate it now so this information
    //can be included in the history record.
    if (ApiFunctions.isEmpty(sampleData.getConsentAlias())) {
      ConsentAccessBean consent = new ConsentAccessBean();
      AccessBeanEnumeration consentEnum = (AccessBeanEnumeration) consent.findByConsentID(sampleData.getConsentId());
      if (consentEnum.hasMoreElements()) {
        consent = (ConsentAccessBean) consentEnum.nextElement();
        sampleData.setConsentAlias(consent.getCustomer_id());
      }
    }

    //now create the asm module and form
    String asmModuleId = createAsmInfo(btxDetails);
    sampleData.setAsmId(asmModuleId);

    //now do the sample.  Note that the sample might already exist (it may
    //have been created via a box scan), so handle that situation as well
    //as the case where the sample doesn't exist
    boolean newSample = createSample(btxDetails, asmModuleId);
    if (newSample) {
      btxDetails.setSampleAction(Constants.OPERATION_CREATE);
    }
    else {
      btxDetails.setSampleAction(Constants.OPERATION_UPDATE);
    }

    // Create the KC form instance if there are any KC data elements.
    FormInstance formInstance = sampleData.getRegistrationFormInstance();
    if (formInstance.getDataElements().length > 0) {
      FormInstanceServiceResponse response = 
        FormInstanceService.SINGLETON.createFormInstance(formInstance);
      FormInstance kcFormInstance = response.getFormInstance();
      if (kcFormInstance != null ) {
        sampleData.setRegistrationFormInstance(kcFormInstance);
      }      
    }
    
    //return messages
    if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getNewDonor())) {
      btxDetails.addActionMessage(
        new BtxActionMessage(
          "dataImport.message.donorAction",
          sampleData.getArdaisId(),
          "created"));
    }
    if (FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getNewCase())) {
      btxDetails.addActionMessage(
        new BtxActionMessage(
          "dataImport.message.caseAction",
          sampleData.getConsentId(),
          sampleData.getArdaisId(),
          "created"));
    }
    //if (newSample) {
    StringBuffer sampleMessage = new StringBuffer(50);
    sampleMessage.append(sampleData.getSampleId());
    if (!ApiFunctions.isEmpty(sampleData.getSampleAlias())) {
      sampleMessage.append(" (");
      sampleMessage.append(Escaper.htmlEscape(sampleData.getSampleAlias()));
      sampleMessage.append(")");
    }
    StringBuffer consentMessage = new StringBuffer(50);
    consentMessage.append(sampleData.getConsentId());
    if (!ApiFunctions.isEmpty(sampleData.getConsentAlias())) {
      consentMessage.append(" (");
      consentMessage.append(Escaper.htmlEscape(sampleData.getConsentAlias()));
      consentMessage.append(")");
    }
    btxDetails.addActionMessage(
      new BtxActionMessage(
        "dataImport.message.sampleAction",
        sampleMessage.toString(),
        consentMessage.toString(),
        "created"));

    //if the user added a sample to a previously pulled case, notify them of that
    if (!FormLogic.DB_YES.equalsIgnoreCase(btxDetails.getNewCase())) {
      ConsentAccessBean consent = new ConsentAccessBean(new ConsentKey(sampleData.getConsentId()));
      if (consent.getConsent_pull_datetime() != null) {
        btxDetails.addActionMessage(
          new BtxActionMessage(
            "dataImport.message.addingSampleToPulledCase",
            sampleData.getConsentId()));
      }
    }

    //return a message to the user if the sample is in a box that has a storage type that
    //is different from any of the storage types that correspond to the sample.  This could happen 
    //if the sample was created via a box scan before the "Create Sample" transaction was performed.
    checkSampleAndBoxStorageTypes(btxDetails);

    if (ApiFunctions.isEmpty(btxDetails.getRememberedData())) {
      //if the user has indicated they wish to proceed to form creation for the new sample
      //bring them there, otherwise return to the create sample functionality.
      if (btxDetails.isCreateForm()) {
        btxDetails.setActionForwardTXCompleted("successWithCreateForm");
      }
      else {
        btxDetails.setActionForwardTXCompleted("success");
      }
    }
    else if (btxDetails.getRememberedData().equalsIgnoreCase(TypeFinder.CASE)) {
      btxDetails.setActionForwardTXCompleted("sameCase");
    }
    else if (btxDetails.getRememberedData().equalsIgnoreCase(TypeFinder.DONOR)) {
      btxDetails.setActionForwardTXCompleted("success");
    }
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performGetSampleFormSummary performer method.
   */
  private BTXDetails validatePerformGetSampleFormSummary(BtxDetailsGetSampleFormSummary btxDetails) throws Exception {

    SampleData sampleData = btxDetails.getSampleData();
    String accountId = btxDetails.getLoggedInUserSecurityInfo().getAccount();

    //make sure an id has been specified
    String sampleId = sampleData.getSampleId();
    String sampleAlias = sampleData.getSampleAlias();
    if (ApiFunctions.isEmpty(sampleId) && ApiFunctions.isEmpty(sampleAlias)) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.sampleNotSpecified"));
    }

    //make sure the user has not specified both a sample id and customer id
    if ((!ApiFunctions.isEmpty(sampleId) && !ApiFunctions.isEmpty(sampleAlias))) {
      btxDetails.addActionError(
        new BtxActionError("dataImport.error.cannotMixSampleAndCustomerId"));
    }
    //otherwise make sure the sample exists
    else {
      //if a sample id has been specified, make sure it is an imported sample, it exists, it 
      //belongs to the current users account, and the current user has access to the sample
      //via inventory groups
      if (!ApiFunctions.isEmpty(sampleId)) {
        boolean invalidId = false;
        if (btxDetails.isReadOnly()) {
          String validatedId =
            (new ValidateIds()).validate(sampleId, ValidateIds.TYPESET_SAMPLE, true);
          if (ApiFunctions.isEmpty(validatedId)) {
            invalidId = true;
          }
          else {
            if (!IcpUtils.isItemAccessibleToUserByInvGroup(btxDetails.getLoggedInUserSecurityInfo(),
                validatedId)) {
              invalidId = true;
            }            
          }
        }
        else {
          String validatedId =
            (new ValidateIds()).validate(sampleId, ValidateIds.TYPESET_SAMPLE_IMPORTED, true);
          if (ApiFunctions.isEmpty(validatedId)) {
            invalidId = true;
          }
          else {
            if (!IltdsUtils.isSampleImported(validatedId)) {
              invalidId = true;
            }
            else {
              if (!IltdsUtils.isSampleAssignedToAccount(validatedId, accountId)) {
                invalidId = true;
              }
              if (!IcpUtils.isItemAccessibleToUserByInvGroup(btxDetails.getLoggedInUserSecurityInfo(),
                                                             validatedId)) {
                invalidId = true;
              }
            }
          }          
        }
        if (invalidId) {
          btxDetails.addActionError(
            new BtxActionError("dataImport.error.nonExistentEntity", "sample", "sample id"));
        }
      }
      //if a customer id has been specified, try to find the sample(s) from it
      else if (!ApiFunctions.isEmpty(sampleAlias)) {
        List samples =
          IltdsUtils.findSamplesFromCustomerId(sampleAlias, accountId);
        if (ApiFunctions.isEmpty(samples)) {
          btxDetails.addActionError(
            new BtxActionError("dataImport.error.nonExistentEntity", "sample", "alias"));
        }
      }
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that gets the form summary
   * information for a sample.
   */
  private BTXDetails performGetSampleFormSummary(BtxDetailsGetSampleFormSummary btxDetails) throws Exception {

	  if (!btxDetails.getLoggedInUserSecurityInfo().isHasPrivilege(SecurityInfo.PRIV_ORM_ACCESS_SAMPLE_VIEW))
	  {
		  btxDetails.addActionError(new BtxActionError("error.noPrivilege", "view sample information"));
		  btxDetails.setActionForwardTXIncomplete("error");
		  return btxDetails;
	  }

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    SampleData inputSampleData = btxDetails.getSampleData();
    SampleData outputSampleData = null;

    String inputSampleId = null;

    //if a customer id was specified, get the samples from it.  If multiple
    //samples are found we're done and forward to the page allowing the user to
    //choose the sample they want, otherwise use the single sample found
    String inputSampleAlias = inputSampleData.getSampleAlias();
    if (!ApiFunctions.isEmpty(inputSampleAlias)) {
      String accountId = securityInfo.getAccount();
      List samples = IltdsUtils.findSamplesFromCustomerId(inputSampleAlias, accountId);
      if (samples.size() > 1) {
        btxDetails.setMatchingSamples(samples);
        btxDetails.setActionForwardTXCompleted("multipleSamples");
        return btxDetails;
      }
      else {
        //set the input sample id from the individual returned sample
        SampleData tempSampleData = (SampleData) samples.get(0);
        inputSampleId = tempSampleData.getSampleId();
      }
    }
    //otherwise use a validated version of the sample id to get a sample data object
    else {
      if (btxDetails.isReadOnly()) {
        inputSampleId = (new ValidateIds()).validate(inputSampleData.getSampleId(), ValidateIds.TYPESET_SAMPLE, true);        
      }
      else {
        inputSampleId = (new ValidateIds()).validate(inputSampleData.getSampleId(), ValidateIds.TYPESET_SAMPLE_IMPORTED, true);        
      }
    }

    outputSampleData = SampleFinder.find(securityInfo, SampleSelect.BASIC_IMPORTED_STATUSES, inputSampleId);
    btxDetails.setSampleData(outputSampleData);
    
    // Get the existing form instances for the case.
    Timestamp now = new Timestamp(System.currentTimeMillis());
    BigrFormInstance form = new BigrFormInstance();
    form.setDomainObjectId(inputSampleId);
    BtxDetailsKcFormInstanceDomainObjectSummary btxDetailsDos= new BtxDetailsKcFormInstanceDomainObjectSummary();
    btxDetailsDos.setBeginTimestamp(now);
    btxDetailsDos.setLoggedInUserSecurityInfo(securityInfo);
    btxDetailsDos.setFormInstance(form);
    btxDetailsDos= 
      (BtxDetailsKcFormInstanceDomainObjectSummary) Btx.perform(btxDetailsDos, "kc_form_inst_domain_object_summary");
    if (btxDetailsDos.isTransactionCompleted()) {
      btxDetails.setFormInstances(btxDetailsDos.getFormInstances());
    }
    else {
      //TODO MC: copy errors/messages to request attribute as in BigrAction.saveErrors
    }
    
    // Get the form definitions for samples in the user's account.
    btxDetails.setFormDefinitions(getAvailableFormDefinitions(securityInfo));

    //return the information
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performModifyImportedCaseStart performer method.
   */
  private BTXDetails validatePerformModifyImportedSampleStart(BtxDetailsModifyImportedSampleStart btxDetails)
    throws Exception {

    SampleData sampleData = btxDetails.getSampleData();
    String accountId = btxDetails.getLoggedInUserSecurityInfo().getAccount();

    //make sure an id has been specified
    String sampleId = sampleData.getSampleId();
    String sampleAlias = sampleData.getSampleAlias();
    if (ApiFunctions.isEmpty(sampleId) && ApiFunctions.isEmpty(sampleAlias)) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.sampleNotSpecified"));
    }

    //make sure the user has not specified both a sample id and customer id
    if ((!ApiFunctions.isEmpty(sampleId) && !ApiFunctions.isEmpty(sampleAlias))) {
      btxDetails.addActionError(
        new BtxActionError("dataImport.error.cannotMixSampleAndCustomerId"));
    }
    //otherwise make sure the sample exists
    else {
      //if a sample id has been specified, make sure it is an imported sample, it exists, it 
      //belongs to the current users account (if we're not in read-only mode), and the current user 
      //has access to the sample via inventory groups
      if (!ApiFunctions.isEmpty(sampleId)) {
        boolean invalidId = false;
        String validatedId =
          (new ValidateIds()).validate(sampleId, ValidateIds.TYPESET_SAMPLE_IMPORTED, true);
        if (ApiFunctions.isEmpty(validatedId)) {
          invalidId = true;
        }
        else {
          if (!IltdsUtils.isSampleImported(validatedId)) {
            invalidId = true;
          }
          else {
            if (!btxDetails.isReadOnly() && 
                !IltdsUtils.isSampleAssignedToAccount(validatedId, accountId)) {
              invalidId = true;
            }
            if (!IcpUtils.isItemAccessibleToUserByInvGroup(btxDetails.getLoggedInUserSecurityInfo(),
                                                           validatedId)) {
              invalidId = true;
            }
          }
        }
        if (invalidId) {
          btxDetails.addActionError(
            new BtxActionError("dataImport.error.nonExistentEntity", "sample", "sample id"));
        }
      }
      //if a customer id has been specified, try to find the sample(s) from it
      else if (!ApiFunctions.isEmpty(sampleAlias)) {
        List samples =
          IltdsUtils.findSamplesFromCustomerId(sampleAlias, accountId);
        if (ApiFunctions.isEmpty(samples)) {
          btxDetails.addActionError(
            new BtxActionError("dataImport.error.nonExistentEntity", "sample", "alias"));
        }
      }
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * modifying a new imported case.
   */
  private BTXDetails performModifyImportedSampleStart(BtxDetailsModifyImportedSampleStart btxDetails)
    throws Exception {
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String accountId = securityInfo.getAccount();

    SampleData inputSampleData = btxDetails.getSampleData();
    SampleData outputSampleData = null;

    String inputSampleId = null;

    //if a customer id was specified, get the samples from it.  If multiple
    //samples are found we're done and forward to the page allowing the user to
    //choose the sample they want, otherwise use the single sample found
    String inputSampleAlias = inputSampleData.getSampleAlias();
    if (!ApiFunctions.isEmpty(inputSampleAlias)) {
      List samples = IltdsUtils.findSamplesFromCustomerId(inputSampleAlias, accountId);
      if (samples.size() > 1) {
        btxDetails.setMatchingSamples(samples);
        btxDetails.setActionForwardTXCompleted("multipleSamples");
        return btxDetails;
      }
      else {
        //set the input sample id from the individual returned sample
        SampleData tempSampleData = (SampleData) samples.get(0);
        inputSampleId = tempSampleData.getSampleId();
      }
    }
    //otherwise use a validated version of the sample id to get a sample data object
    else {
      inputSampleId = (new ValidateIds()).validate(inputSampleData.getSampleId(), ValidateIds.TYPESET_SAMPLE_IMPORTED, true);
    }

    // Find the sample and populate the sample data object. 
    outputSampleData = SampleFinder.find(securityInfo, SampleSelect.BASIC_IMPORTED_CONFIG_STATUSES, inputSampleId);
    btxDetails.setSampleData(outputSampleData);

    // Get the KC form instance, if one exists for the sample.  The find using the criteria
    // returns only basic form information, and does not include the values of the data elements
    // that comprise the form.  Thus, call find by id to get the data element values. 
    FormInstanceQueryCriteria criteria = new FormInstanceQueryCriteria();
    criteria.addDomainObjectId(outputSampleData.getSampleId());
    criteria.addFormDefinitionId(outputSampleData.getRegistrationForm().getFormDefinitionId());
    FormInstanceServiceResponse response = 
      FormInstanceService.SINGLETON.findFormInstances(criteria);
    FormInstance form = response.getFormInstance();
    if (form != null) {
      response = FormInstanceService.SINGLETON.findFormInstanceById(form.getFormInstanceId());
      form = response.getFormInstance();
      if (form != null) {
        outputSampleData.setRegistrationFormInstance(form);
      }
    }      
    
    // Get the map of sample type cuis to registration form ids.
    btxDetails.setSampleRegistrationFormIds(getSampleRegistrationFormIds(outputSampleData.getSampleTypeConfiguration()));
    
    //populate the sample prepared by choices
    btxDetails.setPreparedByChoices(getSamplePreparedByChoices(btxDetails));
    
    populateLabelPrintingData(btxDetails);
    
    populateSampleAliasRequired(btxDetails);

    //return the information
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performModifyImportedSample performer method.
   */
  private BTXDetails validatePerformModifyImportedSample(BtxDetailsModifyImportedSample btxDetails)
    throws Exception {

    // Set some lists to be returned if there is an error.
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    btxDetails.setPreparedByChoices(getSamplePreparedByChoices(btxDetails));
    populateLabelPrintingData(btxDetails);
    
    populateSampleAliasRequired(btxDetails);

    // Validate the sample id.
    String sampleId = validateSampleIdForModify(btxDetails);
    
    // Validate the policy id.  If the policy id could not be determined, then return since all
    // remaining validation relies on the policy.
    String policyId = validatePolicyId(btxDetails);
    if (ApiFunctions.isEmpty(policyId)) {
      return btxDetails;      
    }

    // Get the sample type configuration from the policy and set it in the sample data.  This will 
    // be used by much of the remaining validation.  If we can't find it for some reason, then
    // return an error.
    SampleTypeConfiguration config = PolicyUtils.getSampleTypeConfiguration(policyId);
    if (config == null) {
      btxDetails.addActionError(
          new BtxActionError("generic.message", "The sample type configuration could not be found in the policy."));
      return btxDetails;            
    }
    SampleData sampleData = btxDetails.getSampleData();
    sampleData.setSampleTypeConfiguration(config);
    btxDetails.setSampleRegistrationFormIds(getSampleRegistrationFormIds(config));
    
    // Get the persisted sample.  If it does not exist, then return an error.
    SampleData persistedSample = null; 
    if (!ApiFunctions.isEmpty(sampleId)) {
      persistedSample = 
        SampleFinder.find(securityInfo, SampleSelect.BASIC_IMPORTED_STATUSES, sampleId);    
    }
    if (persistedSample == null) {
      btxDetails.addActionError(
          new BtxActionError("dataImport.error.nonExistentEntity", "sample", "sample id"));      
      return btxDetails;            
    }

    // Validate the sample type.  If the sample type is not valid then return since we won't
    // be able to find the registration form.
    String sampleType = validateSampleType(btxDetails);
    if (ApiFunctions.isEmpty(sampleType)) {
      return btxDetails;
    }    

    // Get the registration form as follows:
    // 1. If a registration form id is not specified and the input sample type is the same as the 
    //    saved sample type, use the existing sample registration form.
    // 2. If a registration form id is not specified and the input sample type is not the same as 
    //    the saved sample type, use the sample registration form from the policy.
    // 3. If a registration form id is specified and the input sample type is the same as the 
    //    saved sample type, use the specified sample registration form if it matches the existing
    //    sample registration form or the registration form id of the sample type in the policy.
    //    This step allows the caller to use the registration form from the policy even if the
    //    sample type has not changed, which may be a new registration form since the sample
    //    was created.
    // 4. If a registration form id is specified and the input sample type is not the same as the 
    //    saved sample type, use the specified sample registration form if it matches the 
    //    registration form id of the sample type in the policy.
    String regFormId = null;
    String inputRegFormId = sampleData.getRegistrationFormId();
    String persistedRegFormId = persistedSample.getRegistrationFormId();
    String policyRegFormId = config.getSampleType(sampleType).getRegistrationFormId();
    if (inputRegFormId != null) {
      if (sampleType.equals(persistedSample.getSampleTypeCui())) {  // rule 3
        if ((inputRegFormId.equals(persistedRegFormId)) 
            || (inputRegFormId.equals(policyRegFormId))) {
          regFormId = inputRegFormId;
        }
        else {
          btxDetails.addActionError(
              new BtxActionError("orm.error.policy.invalidRegistrationForm", sampleId, "does not match that of the existing sample or policy."));          
        }
      }
      else { // rule 4
        if (inputRegFormId.equals(policyRegFormId)) {
          regFormId = inputRegFormId;
        }
        else {
          btxDetails.addActionError(
              new BtxActionError("orm.error.policy.invalidRegistrationForm", sampleId, "does not match that of the policy."));          
        }        
      }
    }
    else {
      if (sampleType.equals(persistedSample.getSampleTypeCui())) {  // rule 1
        regFormId = persistedRegFormId;
      }
      else { // rule 2
        regFormId = policyRegFormId;        
      }
    }
    
    if (regFormId == null) {
      return btxDetails;
    }
    else {
      FormDefinitionServiceResponse response =
        FormDefinitionService.SINGLETON.findFormDefinitionById(regFormId);
      DataFormDefinition formDef = response.getDataFormDefinition();
      // If the persisted registration form is being used, then it does not have to be enabled. 
      if ((formDef == null) || (regFormId != persistedRegFormId && !formDef.isEnabled())) {
        btxDetails.addActionError(
            new BtxActionError("orm.error.policy.invalidRegistrationForm", sampleId, "could not be found."));
        return btxDetails;
      }
      else {
        sampleData.setRegistrationForm(formDef);
        sampleData.setRegistrationFormId(formDef.getFormDefinitionId());
      }
    }

    // Validate the remaining BIGR core elements.
    validateSampleInformation(btxDetails, persistedSample);
    
    // Validate the KC data elements.  If we're not changing the registation form then we'll
    // be modifying the KC form instance (if one exists), otherwise we'll be deleting the
    // existing KC form instance and creating a new one.
    // Note that this validation is only performed if the modification is not occuring as part of a 
    // derivative operation.  Only parent samples can be modified in a derivative operation (all 
    // children are new), and there is a very limited set of attributes that can be modified (weight
    // and volume).  Since none of the modifiable attributes are KC data elements, there is no need
    // to validate the registration form instance.
    boolean validateKcFormInstance = !btxDetails.isDerivativeOperationAction();
    
    if (validateKcFormInstance) {
      if (regFormId.equals(persistedRegFormId)) {
        validateKcFormInstanceForModify(btxDetails);
      }
      else {
        validateKcFormInstanceForCreate(btxDetails);
        validateKcFormInstanceForDelete(btxDetails, persistedRegFormId);
      }
    }
    
    //validate all information for printing has been provided.  Although printing is
    //handled outside of this class (in the ImportedSampleAction), we need to make sure 
    //that all printing information has been provided and is valid here. It would be 
    //annoying to the user to let them proceed with modifying a sample and then tell them 
    //they provided invalid printing information when it is too late for them to fix the problem.
    validateLabelPrintingData(btxDetails, true);

    return btxDetails;
  }

  private void clearNotApplicableAttributes(BtxDetailsImportedSample btxDetails) {
    SampleData sampleData = btxDetails.getSampleData();

    // Get the set of host elements that are not in the registration form.
    Set allHostAttributes = ArtsConstants.getSampleAttributes();
    DataFormDefinition regForm = sampleData.getRegistrationForm();
    DataFormDefinitionHostElement[] hostElements = regForm.getDataHostElements();
    for (int i = 0; i < hostElements.length; i++) {
      allHostAttributes.remove(hostElements[i].getHostId());
    }    

    // Iterate over all of the host elements that were not in the registration form and
    // null their values.
    Iterator i = allHostAttributes.iterator();
    while (i.hasNext()) {
      String attr = (String) i.next();
      if (attr.equals(ArtsConstants.ATTRIBUTE_BUFFER_TYPE)) {
        sampleData.setBufferTypeCui(null);
        sampleData.setBufferTypeOther(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_CELLS_PER_ML)) {
        sampleData.setCellsPerMl(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_COMMENT)) {
        sampleData.setAsmNote(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_CONCENTRATION)) {
        sampleData.setConcentration(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION)) {
        sampleData.setCollectionDateTime(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION)) {
        sampleData.setPreservationDateTime(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE)) {
        sampleData.setFixativeType(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_FORMAT_DETAIL)) {
        sampleData.setFormatDetail(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE)) {
        sampleData.setGrossAppearance(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY)) {
        sampleData.setPercentViability(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_PREPARED_BY)) {
        sampleData.setPreparedBy(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_PROCEDURE)) {
        sampleData.setProcedure(null);
        sampleData.setProcedureOther(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_SOURCE)) {
        sampleData.setSource(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_TISSUE)) {
        sampleData.setTissue(null);
        sampleData.setTissueOther(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS)) {
        sampleData.setTotalNumOfCells(null);
        sampleData.setTotalNumOfCellsExRepCui(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_VOLUME)) {
        sampleData.setVolume(null);
        sampleData.setVolumeUnitCui(null);
        sampleData.setVolumeInUl(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_WEIGHT)) {
        sampleData.setWeight(null);
        sampleData.setWeightInMg(null);
        sampleData.setWeightUnitCui(null);
      }
      else if (attr.equals(ArtsConstants.ATTRIBUTE_YIELD)) {
        sampleData.setYield(null);
      }    
    }
  }

  /**
   * This is the main BTX entry point for performing the transaction that modifies an imported sample.
   */
  private BTXDetails performModifyImportedSample(BtxDetailsModifyImportedSample btxDetails)
    throws Exception {
    SampleData sampleData = btxDetails.getSampleData();

    // Make sure that consumed and volume are in sync.  First, if the volume is zero, then mark the 
    // sample consumed, since we treat these equivalently.  Then if the sample is consumed, make
    // the volume null, which is what we want to store for a consumed sample.
    BigDecimal volume = sampleData.getVolume();
    BigDecimal weight = sampleData.getWeight();
    boolean volumeIsZero = ((volume != null) && volume.compareTo(new BigDecimal(0.0)) == 0);
    boolean weightIsZero = ((weight != null) && weight.compareTo(new BigDecimal(0.0)) == 0);
    if (volumeIsZero || weightIsZero) {
      sampleData.setConsumed(true);
    }
    if (sampleData.isConsumed()) {
      sampleData.setVolume(null);
      sampleData.setVolumeUnitCui(null);
      sampleData.setVolumeInUl(null);
      sampleData.setWeight(null);
      sampleData.setWeightUnitCui(null);
      sampleData.setWeightInMg(null);
    }

    // If the sample was marked consumed, then add a consumed status to the sample, only if it
    // is not already consumed.   
    String sampleId = sampleData.getSampleId();
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    SampleData persistedSample = 
      SampleFinder.find(securityInfo, SampleSelect.BASIC_IMPORTED_STATUSES, sampleId);
    if (sampleData.isConsumed() && (!persistedSample.isConsumed())) {
      SamplestatusAccessBean status = new SamplestatusAccessBean();
      status.setInit_argSample(new SampleKey(sampleData.getSampleId()));
      status.setInit_argStatus_type_code(FormLogic.SMPL_COCONSUMED);
      status.setInit_argSample_status_datetime(
        new java.sql.Timestamp(System.currentTimeMillis()));
      status.commitCopyHelper();
    }

    // If the sample has already been consumed, then make sure to set that it the sample to
    // be returned.
    else if (persistedSample.isConsumed()) {
      sampleData.setConsumed(true);
    }

    //Make sure that all attributes that are not present in the registration form are cleared.
    //Note this is only done if the sample is not being modified as part of a derivation 
    //operation, as there is no way for the user to modify any data on the registration form 
    //for a parent sample
    if (!btxDetails.isDerivativeOperationAction()) {
      clearNotApplicableAttributes(btxDetails);
    }

    SampleAccessBean sample = new SampleAccessBean(new SampleKey(sampleData.getSampleId()));

    //update the sample info
    updateSample(btxDetails, sample);
    
    //update the asm information
    updateAsmInfo(btxDetails, sample);

    //update the registration form.  Note this is only done if the sample is not being
    //modified as part of a derivation operation, as there is no way for the user to
    //modify any data on the registration form for a parent sample
    if (!btxDetails.isDerivativeOperationAction()) {
      // If the registration form of the sample we're modifying is not the same as what is already
      // persisted, then delete the persisted form instance.
      FormInstance formInstance = sampleData.getRegistrationFormInstance();
      String regFormId = sampleData.getRegistrationForm().getFormDefinitionId();
      String persistedRegFormId = persistedSample.getRegistrationForm().getFormDefinitionId();
      if (!regFormId.equals(persistedRegFormId)) {
        FormInstanceQueryCriteria criteria = new FormInstanceQueryCriteria();
        criteria.addDomainObjectId(sampleData.getSampleId());
        criteria.addFormDefinitionId(persistedRegFormId);
        FormInstanceServiceResponse response = 
          FormInstanceService.SINGLETON.findFormInstances(criteria);
        FormInstance form = response.getFormInstance();
        if (form != null) {
          FormInstanceService.SINGLETON.deleteFormInstance(form.getFormInstanceId());
        }
  
        // Set the form instance id to null to signal to the code below that we should be
        // creating a new form instance.
        formInstance.setFormInstanceId(null);
      }
      
      // Either update the existing KC form instance or create a new one.  We'll create a new one
      // if this sample never had a form instance (e.g. its sample type changed such that it's
      // previous registration form had no KC elements, but its new one does) or if we just
      // deleted it.  We'll make the distinction by whether the form instance has a form instance
      // id.
      if (formInstance.getDataElements().length > 0) {
        FormInstanceServiceResponse response = (formInstance.getFormInstanceId() == null) ?
            FormInstanceService.SINGLETON.createFormInstance(formInstance) :
            FormInstanceService.SINGLETON.updateFormInstance(formInstance);
        FormInstance kcFormInstance = response.getFormInstance();
        if (kcFormInstance != null) {
          sampleData.setRegistrationFormInstance(kcFormInstance);
        }      
      }
    }
    
    //if the donor alias is not already populated on the sample, populate it now so this information
    //can be included in the history record
    if (ApiFunctions.isEmpty(sampleData.getDonorAlias())) {
      DonorData donor = new DonorData();
      donor.setArdaisId(sampleData.getArdaisId());
      DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = home.create();
      donor = donorOperation.getDonorProfile(donor);
      sampleData.setDonorAlias(donor.getCustomerId());
    }
    //if the case alias is not already populated on the sample, populate it now so this information
    //can be included in the history record.
    if (ApiFunctions.isEmpty(sampleData.getConsentAlias())) {
      ConsentAccessBean consent = new ConsentAccessBean();
      AccessBeanEnumeration consentEnum = (AccessBeanEnumeration) consent.findByConsentID(sampleData.getConsentId());
      if (consentEnum.hasMoreElements()) {
        consent = (ConsentAccessBean) consentEnum.nextElement();
        sampleData.setConsentAlias(consent.getCustomer_id());
      }
    }

    StringBuffer sampleMessage = new StringBuffer(50);
    sampleMessage.append(sampleData.getSampleId());
    if (!ApiFunctions.isEmpty(sampleData.getSampleAlias())) {
      sampleMessage.append(" (");
      sampleMessage.append(sampleData.getSampleAlias());
      sampleMessage.append(")");
    }
    StringBuffer consentMessage = new StringBuffer(50);
    consentMessage.append(sampleData.getConsentId());
    if (!ApiFunctions.isEmpty(sampleData.getConsentAlias())) {
      consentMessage.append(" (");
      consentMessage.append(sampleData.getConsentAlias());
      consentMessage.append(")");
    }
    btxDetails.addActionMessage(
      new BtxActionMessage(
        "dataImport.message.sampleAction",
        sampleMessage.toString(),
        consentMessage.toString(),
        "modified"));

    //return a message to the user if the sample is in a box that has a storage type that
    //is different from any of the storage types that correspond to the sample.  This could happen 
    //if the sample sample_type was changed.
    checkSampleAndBoxStorageTypes(btxDetails);

    //populate the sample prepared by choices
    btxDetails.setPreparedByChoices(getSamplePreparedByChoices(btxDetails));
    PolicyData policyData = IltdsUtils.getPolicyForConsent(sample.getConsentId());
    String policyId = policyData.getPolicyId();
    sampleData.setSampleTypeConfiguration(PolicyUtils.getSampleTypeConfiguration(policyId));
    btxDetails.setSampleRegistrationFormIds(getSampleRegistrationFormIds(policyData.getSampleTypeConfiguration()));

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performModifyNonImportedSample performer method.
   */
  private BTXDetails validatePerformModifyNonImportedSample(BtxDetailsModifyImportedSample btxDetails)
    throws Exception {

    // NOTE: Intentionally does nothing at this point.    
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that modifies a
   * non-imported sample.
   */
  private BTXDetails performModifyNonImportedSample(BtxDetailsModifyImportedSample btxDetails)
    throws Exception {

    // NOTE: This transaction currently just marks a non-imported sample consumed.  It does not
    // modify anything else about the sample.  We may want to consider having this transaction
    // do more in the future, and potentially merging this performer with
    // performModifyImportedSample. 

    SampleData sampleData = btxDetails.getSampleData();

    if (sampleData.isConsumed()) {
      SamplestatusAccessBean status = new SamplestatusAccessBean();
      status.setInit_argSample(new SampleKey(sampleData.getSampleId()));
      status.setInit_argStatus_type_code(FormLogic.SMPL_COCONSUMED);
      status.setInit_argSample_status_datetime(
        new java.sql.Timestamp(System.currentTimeMillis()));
      status.commitCopyHelper();
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  //private method to create a new asm form and return it's corresponding
  //asm form id.  We do this when importing a sample into the system, and
  //there is a one-to-one correspondence between an imported sample and an
  //ASM that we create for it.
  private String createAsmInfo(BtxDetailsCreateImportedSample btxDetails) {
    SampleData sampleData = btxDetails.getSampleData();

    //First, get the Ardais Id. Although we could just get the Ardais id from the 
    //btxDetails, it seems safer to get it from the consent.  Even though the validation code 
    //is checking that the case belongs to the specified donor, doing this ensures there is no way
    //to end up with an asm belonging to a case that doesn't belong to the same
    //donor as the asm.  Additionally, it allows for the possibility of not requiring
    //a donor be specified in the situation where a new case is not being created.
    String donorId = null;
    ConsentAccessBean myCab = null;
    ConsentKey myCabKey = null;
    try {
      myCab = new ConsentAccessBean();
      AccessBeanEnumeration consentParent =
        (AccessBeanEnumeration) myCab.findByConsentID(sampleData.getConsentId());

      myCab = (ConsentAccessBean) consentParent.nextElement();
      donorId = myCab.getArdais_id();
      myCabKey = (ConsentKey) myCab.__getKey();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    //create the ASM Form and module
    String asmFormId = getIdForNewImportedAsm();
    String asmModuleId = asmFormId + "_A";
    AsmformAccessBean form = new AsmformAccessBean();
    AsmAccessBean asmBean = new AsmAccessBean();
    try {
      //set the generally applicable attributes on the form first
      form.setInit_argConsent(myCabKey);
      form.setInit_argArdais_id(donorId);
      form.setInit_argAsm_form_id(asmFormId);
      form.setLink_staff_id(btxDetails.getLoggedInUserSecurityInfo().getUsername());
      java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
      form.setCreation_time(now);
      form.setArdais_staff_id(sampleData.getPreparedBy());
      form.setSurgical_specimen_id(sampleData.getProcedure());
      form.setSurgical_specimen_id_other(sampleData.getProcedureOther());
      form.commitCopyHelper();

      //set the generally applicable attributes on the module first
      asmBean.setInit_asm_id(asmModuleId);
      asmBean.setArdais_id(donorId);
      asmBean.setConsent_id(myCabKey.consent_id);
      asmBean.setAsm_entry_date(now);
      asmBean.setAsm_form_id(asmFormId);
      asmBean.setOrgan_site_concept_id(sampleData.getTissue());
      asmBean.setSpecimen_type(sampleData.getGrossAppearance());
      asmBean.setOrgan_site_concept_id_other(sampleData.getTissueOther());
      asmBean.commitCopyHelper();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    return asmModuleId;
  }
  
  private void updateSample(BtxDetailsImportedSample btxDetails, SampleAccessBean sample) {
    SampleData sampleData = btxDetails.getSampleData();
    try {
      // Update the sample attributes common to all samples.
      sample.setCustomerId(sampleData.getSampleAlias());
      sample.setSampleTypeCid(sampleData.getSampleTypeCui());
      DataFormDefinition regForm = sampleData.getRegistrationForm();
      if (regForm != null) {
        sample.setSampleRegistrationForm(regForm.getFormDefinitionId());        
      }
      sample.setAsm_note(sampleData.getAsmNote());
      sample.setSource(sampleData.getSource());

      // Collection and preservation date times, and their precision.
      VariablePrecisionDateTime collectionDateTime = sampleData.getCollectionDateTime();
      if (collectionDateTime == null) {
        sample.setCollection_datetime(null);
        sample.setCollection_datetime_dpc(null);
      }
      else {
        sample.setCollection_datetime(collectionDateTime.getTimestamp());
        sample.setCollection_datetime_dpc(collectionDateTime.getPrecision());
      }

      VariablePrecisionDateTime preservationDateTime = sampleData.getPreservationDateTime();
      if (preservationDateTime == null) {
        sample.setPreservation_datetime(null);
        sample.setPreservation_datetime_dpc(null);
      }
      else {
        sample.setPreservation_datetime(preservationDateTime.getTimestamp());
        sample.setPreservation_datetime_dpc(preservationDateTime.getPrecision());
      }
      
      String fixativeType = sampleData.getFixativeType();
      if (ApiFunctions.isEmpty(fixativeType)) {
        sample.setType_of_fixative(null);  
      }
      else {
        sample.setType_of_fixative(fixativeType);  
      }

      String formatDetail = sampleData.getFormatDetail();
      if (ApiFunctions.isEmpty(formatDetail)) {
        sample.setFormatDetailCid(null);  
      }
      else {
        sample.setFormatDetailCid(formatDetail);  
      }

      BigDecimal volume = sampleData.getVolume();
      sample.setVolume(volume);
      if (volume == null) {
        sample.setVolumeUnitCui(null);
        sample.setVolumeInUl(null);
      }
      else {
        sample.setVolume(sampleData.getVolume());
        sample.setVolumeUnitCui(sampleData.getVolumeUnitCui());
        sample.setVolumeInUl(sampleData.getVolumeInUl());
       }
      
      String bufferTypeCui = sampleData.getBufferTypeCui();
      sample.setBufferTypeCui(bufferTypeCui);  
      if (ApiFunctions.isEmpty(bufferTypeCui)) {
        sample.setBufferTypeOther(null); 
      }
      else {
        sample.setBufferTypeOther(sampleData.getBufferTypeOther()); 
      }

      BigDecimal totalNumOfCells = sampleData.getTotalNumOfCells();
      sample.setTotalNumOfCells(totalNumOfCells);
      if (totalNumOfCells == null) {
        sample.setTotalNumOfCellsExRepCui(null);
      }
      else {
        sample.setTotalNumOfCellsExRepCui(sampleData.getTotalNumOfCellsExRepCui());
      }

      sample.setCellsPerMl(sampleData.getCellsPerMl());
      sample.setPercentViability(sampleData.getPercentViability());
      
      sample.setConcentration(sampleData.getConcentration());
      sample.setYield(sampleData.getYield());
      
      BigDecimal weight = sampleData.getWeight();
      sample.setWeight(weight);
      if (weight == null) {
        sample.setWeightInMg(null);
        sample.setWeightUnitCui(null);
      }
      else {
        sample.setWeight(sampleData.getWeight());
        sample.setWeightUnitCui(sampleData.getWeightUnitCui());
        sample.setWeightInMg(sampleData.getWeightInMg());
      }
           
      sample.commitCopyHelper();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  //private method to update an asm form.
  private void updateAsmInfo(BtxDetailsModifyImportedSample btxDetails, SampleAccessBean sample) {
    SampleData sampleData = btxDetails.getSampleData();
    try {
      String asmFormId = sample.getAsm().getAsm_form_id();
      AsmformAccessBean asmForm = new AsmformAccessBean(new AsmformKey(asmFormId));
      
      String preparedBy = sampleData.getPreparedBy();      
      if (ApiFunctions.isEmpty(preparedBy)) {
        asmForm.setArdais_staff_id(null);
      }
      else {
        asmForm.setArdais_staff_id(preparedBy);
      }

      String specimenId = sampleData.getProcedure();      
      if (ApiFunctions.isEmpty(specimenId)) {
        asmForm.setSurgical_specimen_id(null);
      }
      else {
        asmForm.setSurgical_specimen_id(specimenId);
      }
    
      String specimenIdOther = sampleData.getProcedureOther();      
      if (ApiFunctions.isEmpty(specimenIdOther)) {
        asmForm.setSurgical_specimen_id_other(null);
      }
      else {
        asmForm.setSurgical_specimen_id_other(specimenIdOther);
      }

      asmForm.commitCopyHelper();

      AsmAccessBean asmBean = sample.getAsm();
      
      String tissue = sampleData.getTissue();
      if (ApiFunctions.isEmpty(tissue)) {
        asmBean.setOrgan_site_concept_id(null);
      }
      else {
        asmBean.setOrgan_site_concept_id(tissue);
      }

      String tissueOther = sampleData.getTissueOther();
      if (ApiFunctions.isEmpty(tissueOther)) {
        asmBean.setOrgan_site_concept_id_other(null);
      }
      else {
        asmBean.setOrgan_site_concept_id_other(tissueOther);
      }

      String grossAppearance = sampleData.getGrossAppearance();
      if (ApiFunctions.isEmpty(grossAppearance)) {
        asmBean.setSpecimen_type(null);
      }
      else {
        asmBean.setSpecimen_type(grossAppearance);
      }

      asmBean.commitCopyHelper();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  //private method used to get an id for a new imported asm
  private String getIdForNewImportedAsm() {
    String asmId = null;
    Connection connection = null;
    CallableStatement cstmt = null;
    ResultSet results = null;
    try {
      connection = ApiFunctions.getDbConnection();
      cstmt = connection.prepareCall("begin DATA_IMPORT_GET_ASM_ID(?,?); end;");
      cstmt.registerOutParameter(1, Types.VARCHAR);
      cstmt.registerOutParameter(2, Types.VARCHAR);
      cstmt.executeQuery();
      Object obj = cstmt.getObject(2);
      if (obj != null) {
        String emsg = obj.toString();
        //throw an exception
        throw new ApiException(
          "DATA_IMPORT_GET_ASM_ID failed at BtxPerformerSampleOperations.getIdForNewImportedAsm(): "
            + emsg);
      }
      else {
        asmId = cstmt.getString(1);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(connection, cstmt, results);
    }
    return asmId;
  }

  private boolean createSample(BtxDetailsCreateImportedSample btxDetails, String asmId) {
    SampleAccessBean sample = null;
    SampleData sampleData = btxDetails.getSampleData();

    String sampleId = sampleData.getSampleId();
    boolean newSample = false;
    try {
      try {
        // determine if an existing record...
        sample = new SampleAccessBean(new SampleKey(sampleId));
      }
      catch (ObjectNotFoundException onfe) {
        newSample = true;
        // otherwise, we need to create a new record...
        sample = new SampleAccessBean();
        sample.setInit_sample_barcode_id(sampleId);
        sample.setInit_importedYN(FormLogic.DB_YES);
        sample.setInit_sampleTypeCid(sampleData.getSampleTypeCui());
        sample.setLastknownlocationid(btxDetails.getLoggedInUserSecurityInfo().getUserLocationId());
      }
      //now that we've got the sample (new or otherwise), set the appropriate fields
      AsmAccessBean asm = new AsmAccessBean(new AsmKey(asmId));
      sample.setAsm((Asm) asm.getEJBRef());
      //set the ardais_Acct_key field to be the same account as the logged in user.  
      //If the sample already exists it should already have such a value but better 
      //safe than sorry
      sample.setArdais_acct_key(sampleData.getArdaisAcctKey());
      sample.setImportedYN(FormLogic.DB_YES);
      sample.setParent_barcode_id(sampleData.getParentId());
      //call updateSample to set the bulk of the sample attributes
      updateSample(btxDetails, sample);      
      sample.commitCopyHelper();
      //Give the sample an ASMPRESENT status, but only when the sample record is first being
      //created (see MR 7946).
      if (newSample) {
        SamplestatusAccessBean status = new SamplestatusAccessBean();
        status.setInit_argSample(new SampleKey(sampleId));
        status.setInit_argStatus_type_code(FormLogic.SMPL_ASMPRESENT);
        status.setInit_argSample_status_datetime(
          new java.sql.Timestamp(System.currentTimeMillis()));
        status.commitCopyHelper();
      }
      //set the correct sales_status and allocation_ind values
      //Also assign the sample to the correct inventory groups. Must be 
      //called after setting the asm attribute.
      IltdsUtils.applyPolicyToSample(sample, IltdsUtils.APPLY_POLICY_FOR_SALES_STATUS);
      if (btxDetails.isDerivativeOperationAction() && 
          btxDetails.getLoggedInUserSecurityInfo().isInRoleSystemOwner() &&
          !ApiFunctions.isEmpty(btxDetails.getInventoryGroupIds())) {
        Allocation.allocate(sample, btxDetails.getInventoryGroupIds(), btxDetails.getLoggedInUserSecurityInfo());
      }
      else {
        Allocation.allocate(sample, btxDetails.getLoggedInUserSecurityInfo());
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    return newSample;
  }

  //Method to return a message to the user if the sample they created/modified
  //has a storage type that is different from the box containing the sample if the
  //sample is in a box.
  private void checkSampleAndBoxStorageTypes(BtxDetailsImportedSample btxDetails) {
    SampleData sampleData = btxDetails.getSampleData();

    String sampleId = sampleData.getSampleId();
    String sampleTypeCui = sampleData.getSampleTypeCui();
    //if either the sample id or it's sample type are missing, just return
    //since we can't do any further checking
    if (ApiFunctions.isEmpty(sampleId) || ApiFunctions.isEmpty(sampleTypeCui)) {
      return;
    }
    
    //otherwise, see if the sample is in a box (that has a storage type) and if not just 
    //return since there's nothing to check
    SampleAccessBean sample = null;
    SampleboxAccessBean sampleBox = null;
    String boxId = null;
    String boxStorageType = null;
    try {
      sample = new SampleAccessBean(new SampleKey(sampleId));
      sampleBox = sample.getSamplebox();
      if (sampleBox != null) {
        boxId = ((SampleboxKey) sampleBox.__getKey()).box_barcode_id;
        boxStorageType = sampleBox.getStorageTypeCid();
      }
    }
    catch (Exception onfe) {
      return;
    }
    if (sampleBox == null) {
      return;
    }
    if (ApiFunctions.isEmpty(boxStorageType)) {
      return;
    }
    
    //if we made it here, make sure the storage type on the box matches one of
    //the storage types allowed for the sample's sample_type.  To do that
    //get the storage types allowed for the sample type and iterator over those
    //storage types until we find one that matches the box storage type or we run out of 
    //sample storage types.
    boolean matchFound = false;
    Set sampleStorageTypes = IltdsUtils.getStorageTypesBySampleType(sampleTypeCui);
    Iterator iterator = sampleStorageTypes.iterator();
    while (iterator.hasNext()) {
      String sampleStorageType = (String)iterator.next();
      if (sampleStorageType.equalsIgnoreCase(boxStorageType)) {
        matchFound = true;
        break;
      }
    }
    if (!matchFound) {
      btxDetails.addActionMessage(
        new BtxActionMessage(
          "dataImport.message.sampleAndBoxStorageTypeMismatch",
          sampleId,
          GbossFactory.getInstance().getDescription(sampleTypeCui),
          boxId,
          boxStorageType.toLowerCase()));
    }
  }
  
  private void validateKcFormInstanceForCreate(BtxDetailsImportedSample btxDetails) {
    validateKcFormInstanceCommon(btxDetails);
    
    SampleData sampleData = btxDetails.getSampleData();    
    FormInstance formInstance = sampleData.getRegistrationFormInstance();
    if (formInstance.getDataElements().length > 0) {
      BtxActionErrors errors = 
        FormInstanceService.SINGLETON.validateCreateFormInstance(formInstance);                                               
      if (!errors.empty()) {
        btxDetails.addActionErrors(errors);
      }      
    }    
  }

  private void validateKcFormInstanceForModify(BtxDetailsImportedSample btxDetails) {
    SampleData sampleData = btxDetails.getSampleData();

    FormInstance formInstance = sampleData.getRegistrationFormInstance();
    if (formInstance.getDataElements().length > 0) {
      // First get the persisted KC form instance, if one exists for the sample.  The persisted
      // form may not exist if, for example, the sample was created with a registration form that 
      // did not have any KC elements and the sample type was changed such that the corresponding 
      // registration form does have KC elements.
      FormInstanceQueryCriteria criteria = new FormInstanceQueryCriteria();
      criteria.addDomainObjectId(sampleData.getSampleId());
      criteria.addFormDefinitionId(sampleData.getRegistrationForm().getFormDefinitionId());
      FormInstanceServiceResponse response = 
        FormInstanceService.SINGLETON.findFormInstances(criteria);
      FormInstance form = response.getFormInstance();

      // If there is no persisted form, then validate the input form instance for creation.
      if (form == null) {
        validateKcFormInstanceForCreate(btxDetails);
      }
      
      // If there is a persisted form, then validate for modification.  Make sure to set the
      // form instance id, since it may not have been set by the caller.
      else {
        formInstance.setFormInstanceId(form.getFormInstanceId());
        validateKcFormInstanceCommon(btxDetails);
        
        BtxActionErrors errors = 
          FormInstanceService.SINGLETON.validateUpdateFormInstance(formInstance);                                               
        if (!errors.empty()) {
          btxDetails.addActionErrors(errors);
        }      
      }
    }    
  }
  
  private void validateKcFormInstanceForDelete(BtxDetailsImportedSample btxDetails, String formDefinitionId) {
    SampleData sampleData = btxDetails.getSampleData();

    // First get the persisted KC form instance, if one exists for the sample.  The persisted
    // form may not exist if, for example, the sample was created with a registration form that 
    // did not have any KC elements and the sample type was changed such that the corresponding 
    // registration form does have KC elements.
    FormInstanceQueryCriteria criteria = new FormInstanceQueryCriteria();
    criteria.addDomainObjectId(sampleData.getSampleId());
    criteria.addFormDefinitionId(formDefinitionId);
    FormInstanceServiceResponse response = 
      FormInstanceService.SINGLETON.findFormInstances(criteria);
    FormInstance form = response.getFormInstance();

    // If there is a persisted form, then validate for deletion.  If there is no persisted form, 
    // then we'll just do nothing.  The actual performer will know to not delete a form that does 
    // not exist.
    if (form != null) {
      BtxActionErrors errors = 
        FormInstanceService.SINGLETON.validateDeleteFormInstance(form.getFormInstanceId());                                               
      if (!errors.empty()) {
        btxDetails.addActionErrors(errors);
      }      
    }
  }

  private void validateKcFormInstanceCommon(BtxDetailsImportedSample btxDetails) {
    SampleData sampleData = btxDetails.getSampleData();
    String regFormId = sampleData.getRegistrationForm().getFormDefinitionId();
    
    FormInstance formInstance = sampleData.getRegistrationFormInstance();
    if (formInstance.getDataElements().length > 0) {
      String sampleId = sampleData.getSampleId();
      String domainObjectId = formInstance.getDomainObjectId();
      if ((domainObjectId != null) && !domainObjectId.equals(sampleId)) {
        btxDetails.addActionError(new BtxActionError("generic.message", "Domain object id specified for sample registration form (" + domainObjectId + ") does not match sample id (" + sampleId + ")."));
      }
      formInstance.setDomainObjectId(sampleId);

      String domainObjectType = formInstance.getDomainObjectType();
      if ((domainObjectType != null) && !domainObjectType.equals(TagTypes.DOMAIN_OBJECT_VALUE_SAMPLE)) {
        btxDetails.addActionError(new BtxActionError("generic.message", "Domain object type specified for sample registration form (" + domainObjectType + ") must be '" + TagTypes.DOMAIN_OBJECT_VALUE_SAMPLE + "'."));
      }
      formInstance.setDomainObjectType(TagTypes.DOMAIN_OBJECT_VALUE_SAMPLE);

      String formDefinitionId = formInstance.getFormDefinitionId();
      if ((formDefinitionId != null) && !formDefinitionId.equals(regFormId)) {
        btxDetails.addActionError(new BtxActionError("generic.message", "Form definition id specified for sample registration form (" + formDefinitionId + ") does not match expected form definition id (" + regFormId + ")."));        
      }
      formInstance.setFormDefinitionId(regFormId);
    }
    
  }
  
  /*
   * Private method to perform validation on sample information.  Shared between the
   * create and modify imported sample transactions.  At this point, we've determined 
   * the policy and sample type configuration and set the sample type configuration on the
   * SampleData, we've validated the sample type and found the appropriate registration form
   * and set that on the SampleData.
   */
  private void validateSampleInformation(BtxDetailsImportedSample btxDetails, 
                                         SampleData persistedSample) {
    SampleData sampleData = btxDetails.getSampleData();

    // Trim the alias and make sure it is null if it is the empty string.
    String sampleAlias = ApiFunctions.safeTrim(sampleData.getSampleAlias());
    sampleData.setSampleAlias(ApiFunctions.isEmpty(sampleAlias) ? null : sampleAlias);
    
    //validate the sample alias against the settings in the account under which the sample
    //will/does belong
    String sampleId = ApiFunctions.safeTrim(sampleData.getSampleId());
    if (!ApiFunctions.isEmpty(sampleId)) {
      String accountId = IltdsUtils.getAccountAssignedToSample(sampleData.getSampleId());
      AccountDto accountDto = IltdsUtils.getAccountById(accountId, false, false);
      //if we were able to retrieve the account to which the sample will belong, check the alias
      //requirements.  We check to see if we can find an account, because if the user typed in an
      //invalid sample id (i.e. "blah") then no account will be found
      if (accountDto != null) {
        boolean aliasRequired = FormLogic.DB_YES.equalsIgnoreCase(accountDto.getRequireSampleAliases());
        if (aliasRequired && ApiFunctions.isEmpty(sampleData.getSampleAlias())) {
          btxDetails.addActionError(new BtxActionError("error.noValue", "Sample Alias"));              
        }
        boolean aliasMustBeUnique = FormLogic.DB_YES.equalsIgnoreCase(accountDto.getRequireUniqueSampleAliases());
        if (aliasMustBeUnique && !ApiFunctions.isEmpty(sampleData.getSampleAlias())
            && IltdsUtils.isSampleAliasValueInUse(accountId, sampleData.getSampleAlias(), 
                                                  sampleData.getSampleId())) {
          btxDetails.addActionError(new BtxActionError("general.error.invalidValue", sampleData.getSampleAlias(), "Sample Alias.  That value is already in use and alias values must be unique"));              
        }
      }
    }

    Calendar collection = null;
    Calendar preservation = null;

    // Check all of the host elements in the registration form.  Make sure that the required
    // elements have a value and that the value is valid.  In addition, keep track of which host 
    // elements are not in the registration form so we can ensure that they do not have values.
    Set allHostAttributes = ArtsConstants.getSampleAttributes();
    DataFormDefinition regForm = sampleData.getRegistrationForm();
    DataFormDefinitionHostElement[] hostElements = regForm.getDataHostElements();
    for (int i = 0; i < hostElements.length; i++) {
      DataFormDefinitionHostElement hostElement = hostElements[i];
      String hostId = hostElement.getHostId();
      
      allHostAttributes.remove(hostId);
      
      if (hostElement.isRequired()) {
        validateAttributeRequired(btxDetails, hostElement);
      }
      
      if (hostId.equals(ArtsConstants.ATTRIBUTE_BUFFER_TYPE)) {
        validateAttributeBufferType(btxDetails);
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_CELLS_PER_ML)) {
        validateAttributeCellsPerMl(btxDetails);
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_COMMENT)) {
        validateAttributeComment(btxDetails);
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_CONCENTRATION)) {
        validateAttributeConcentration(btxDetails);
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION)) {
        VariablePrecisionDateTime vpd = sampleData.getCollectionDateTime();
        collection = validateAttributeDate(btxDetails, vpd, "Collection");
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION)) {
        VariablePrecisionDateTime vpd = sampleData.getPreservationDateTime();
        preservation = validateAttributeDate(btxDetails, vpd, "Preservation");
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE)) {
        // no validation needed
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_FORMAT_DETAIL)) {
        // no validation needed
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE)) {
        // no validation needed
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY)) {
        validateAttributePercentViability(btxDetails);
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_PREPARED_BY)) {
        // no validation needed
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_PROCEDURE)) {
        validateAttributeProcedure(btxDetails);
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_SOURCE)) {
        validateAttributeSource(btxDetails);
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_TISSUE)) {
        validateAttributeTissue(btxDetails);
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS)) {
        validateAttributeTotalNumberOfCells(btxDetails);
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_VOLUME)) {
        validateAttributeVolume(btxDetails, persistedSample);
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_WEIGHT)) {
        validateAttributeWeight(btxDetails, persistedSample);
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_YIELD)) {
        validateAttributeYield(btxDetails);
      }
    }

    // If both preservation and collection date values were specified, verify that the 
    // preservation date is not after the collection date. 
    if (collection != null && preservation != null) {
      if (collection.getTime().after(preservation.getTime())) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.valueCannotBeAfterValue", "Date/Time of Collection", "Date/Time of Preservation"));
      }
    }

    // Iterate over all of the host elements that were not in the registration form and
    // check that their values are empty.
    Iterator i = allHostAttributes.iterator();
    while (i.hasNext()) {
      String attr = (String) i.next();
      validateAttributeEmpty(btxDetails, attr);      
    }
  }
  
  private String validatePolicyId(BtxDetailsImportedSample btxDetails) {
    String policyId = btxDetails.getPolicyId();
    if (ApiFunctions.isEmpty(policyId)) {
      String consentId = btxDetails.getSampleData().getConsentId();
      if (!ApiFunctions.isEmpty(consentId)) {
        PolicyData policyData = IltdsUtils.getPolicyForConsent(consentId);
        policyId = policyData.getPolicyId();
      }
    }
    if (ApiFunctions.isEmpty(policyId)) {
      btxDetails.addActionError(new BtxActionError("generic.message", "No policy could be found to create sample."));
    }
    return policyId;
  }
  
  private String validateSampleType(BtxDetailsImportedSample btxDetails) {
    SampleData sampleData = btxDetails.getSampleData();
    String sampleType = sampleData.getSampleTypeCui();
    if (ApiFunctions.isEmpty(sampleType)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Sample Type"));
    }

    // Validate that the sample type is supported in the policy.  If not, set the return value
    // to null since that indicates to the caller that the sample type is invalid.
    else {
      SampleTypeConfiguration config = sampleData.getSampleTypeConfiguration();      
      List supportedSampleTypes = ApiFunctions.safeList(config.getSupportedSampleTypes());
      if (!supportedSampleTypes.contains(sampleType)) {
        btxDetails.addActionError(
            new BtxActionError("dataImport.error.unsupportedSampleType", GbossFactory.getInstance().getDescription(sampleType)));
        sampleType = null;
      }
    }
    return sampleType;
  }
  
  private String validateSampleIdForCreate(BtxDetailsCreateImportedSample btxDetails) {
    String sampleId = btxDetails.getSampleData().getSampleId();
    if (ApiFunctions.isEmpty(sampleId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Sample Id"));
    }
    else {
      //make sure the sample id is an import id
      if (ApiFunctions
        .isEmpty(ValidateIds.validateId(sampleId, ValidateIds.TYPESET_SAMPLE_IMPORTED, false))) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.sampleIdFormatInvalid"));
      }
      else {
        SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
        boolean isDeriv = btxDetails.isDerivativeOperationAction();
        //if inventory groups have been specified but this is not a derivative operation or the user 
        //is not in the Ardais role then return an error.  The only time the system should allow
        //inventory group membership to be specified is when an Ardais user is creating sample(s)
        //as children of a derivative operation
        if (!ApiFunctions.isEmpty(btxDetails.getInventoryGroupIds()) && 
            (!isDeriv || !securityInfo.isInRoleSystemOwner())) {
          btxDetails.addActionError(
            new BtxActionError("dataImport.error.invalidValue", "inventory groups, as that information is not specifiable."));
        }
        
        //if this is not a derivative operation, make sure the sample id has been assigned to this
        //account
        String accountId = securityInfo.getAccount();
        if (!isDeriv && !IltdsUtils.isSampleAssignedToAccount(sampleId, accountId)) {
          btxDetails.addActionError(
            new BtxActionError("dataImport.error.sampleNotAssignedToAccount"));
        }
        //if this is a derivative operation, make sure the sample id can be created by this user
        else if (isDeriv && !IltdsUtils.isSampleCreatableByAccount(sampleId, securityInfo)) {
          btxDetails.addActionError(
            new BtxActionError("iltds.error.genealogy.unknownSamples", "child samples", sampleId));
        }
        else {
          //make sure the sample has not already been imported.  This is not as simple as making sure
          //the sample doesn't already exist, because it may have been created by a box scan.
          if (IltdsUtils.isSampleImported(sampleId)) {
            btxDetails.addActionError(
              new BtxActionError("dataImport.error.existingEntity", "sample"));
          }
        }
      }
    }
    return sampleId;
  }

  private String validateSampleIdForModify(BtxDetailsModifyImportedSample btxDetails) {
    //make sure a valid sample has been specified
    //Note that if the sample is being modified as part of a derivative
    //operation and the user is in the Ardais account, the sample does not have to
    //belong to the user's account.  This is done to allow Cytmoyx users to create child
    //samples either:
    //1) on behalf of their customers (ie. Merck parent sample into Merck child samples)
    //2) for themselves from parent samples they "own" but were collected at other institutions
    //   (see MR8395 for an example - Ardais user trying to create child samples from a
    //    parent collected at Beth Israel but the parent is "owned" by Cytomyx)
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String sampleId = btxDetails.getSampleData().getSampleId();
    boolean invalidId = false;
    String validatedId = 
      (new ValidateIds()).validate(sampleId,ValidateIds.TYPESET_SAMPLE_IMPORTED, true);
    if (ApiFunctions.isEmpty(validatedId) || !IltdsUtils.isSampleImported(validatedId)) {
      invalidId = true;
    }
    else {
      boolean allowNonAccountSample = 
        btxDetails.isDerivativeOperationAction() && securityInfo.isInRoleSystemOwner();
      if (!IltdsUtils.isSampleAssignedToAccount(validatedId, securityInfo.getAccount()) 
          && !allowNonAccountSample) {
        invalidId = true;
      }
      if (!IcpUtils.isItemAccessibleToUserByInvGroup(securityInfo, validatedId)) {
        invalidId = true;
      }
    }
    if (invalidId) {
      btxDetails.addActionError(
        new BtxActionError("dataImport.error.nonExistentEntity", "sample", "sample id"));
    }
    return sampleId;
  }
  
  /*
   * Private method to validate that any comment doesn't exceed the maximum length.  Shared
   * between the create and modify imported sample transactions
   */
  private void validateAttributeComment(BtxDetailsImportedSample btxDetails) {
    int MAX_COMMENT_LENGTH = 255;
    SampleData sampleData = btxDetails.getSampleData();
    String comments = sampleData.getAsmNote();
    if (!ApiFunctions.isEmpty(comments) && comments.length() > MAX_COMMENT_LENGTH) {
      btxDetails.addActionError(
        new BtxActionError("error.lengthExceeded", "Comments", MAX_COMMENT_LENGTH + " characters"));
    }
  }

  private void validateAttributeSource(BtxDetailsImportedSample btxDetails) {
    int MAX_SOURCE_LENGTH = 200;
    SampleData sampleData = btxDetails.getSampleData();
    String source = sampleData.getSource();
    if (!ApiFunctions.isEmpty(source) && source.length() > MAX_SOURCE_LENGTH) {
      btxDetails.addActionError(
        new BtxActionError("error.lengthExceeded", "Sample Source", MAX_SOURCE_LENGTH + " characters"));
    }
  }
  
  private void validateAttributeConcentration(BtxDetailsImportedSample btxDetails) {
    // Validate Concentration information.  Value must be between 0.000 and 9999.999
    // and have at most 3 decimal places
    SampleData sampleData = btxDetails.getSampleData();
    String concentrationAsString = sampleData.getConcentrationAsString();
    if (!ApiFunctions.isEmpty(concentrationAsString)) {
      int decimalIndex = concentrationAsString.indexOf(".");
      boolean tooManyDecimals = false;
      if (decimalIndex >= 0) {
        tooManyDecimals = concentrationAsString.substring(decimalIndex + 1).length() > 3;
      }
      BigDecimal concentration = sampleData.getConcentration();
      if ((concentration == null) ||
          (concentration.compareTo(new BigDecimal("0.000")) < 0) ||
          (concentration.compareTo(new BigDecimal("9999.999")) > 0) ||
          tooManyDecimals) {
        btxDetails.addActionError(new BtxActionError("error.badvalue", concentrationAsString, "Concentration", "a number between 0.000 and 9999.999 (with at most 3 decimal places)"));
      }
    }
  }  

  private void validateAttributePercentViability(BtxDetailsImportedSample btxDetails) {
    // Validate percent viability information.  Value must be between 0 and 100
    SampleData sampleData = btxDetails.getSampleData();
    String percentViabilityAsString = sampleData.getPercentViabilityAsString();
    if (!ApiFunctions.isEmpty(percentViabilityAsString)) {
      Integer percentViability = sampleData.getPercentViability();
      if ((percentViability == null) 
          || (percentViability.intValue() < 0) 
          || (percentViability.intValue() > 100)) {
        btxDetails.addActionError(new BtxActionError("error.badvalue", percentViabilityAsString, "Percent Viability", "a number between 0 and 100"));
      }
    }        
  }

  private void validateAttributeBufferType(BtxDetailsImportedSample btxDetails) {
    SampleData sampleData = btxDetails.getSampleData();
    String bufferTypeCui = ApiFunctions.safeString(sampleData.getBufferTypeCui());
    String bufferTypeOther = ApiFunctions.safeString(sampleData.getBufferTypeOther());
    if (bufferTypeCui.equals(ArtsConstants.OTHER_BUFFER_TYPE) 
        && ApiFunctions.isEmptyOrWhitespace(bufferTypeOther)) {
      btxDetails.addActionError(new BtxActionError("error.noOtherText", "Buffer Type"));
    }
    if (!bufferTypeCui.equals(ArtsConstants.OTHER_BUFFER_TYPE) 
        && !ApiFunctions.isEmpty(bufferTypeOther)) {
      btxDetails.addActionError(new BtxActionError("error.otherTextSpecified", "Buffer Type"));
    }    
  }

  private void validateAttributeCellsPerMl(BtxDetailsImportedSample btxDetails) {
    // Validate Cells/mL information.  Value must be between 0.01 and 99,999,999.99
    SampleData sampleData = btxDetails.getSampleData();
    String cellsPerMlAsString = sampleData.getCellsPerMlAsString();
    if (!ApiFunctions.isEmpty(cellsPerMlAsString)) {
      BigDecimal cellsPerMl = sampleData.getCellsPerMl();
      if ((cellsPerMl == null) ||
          (cellsPerMl.compareTo(new BigDecimal("0.01")) < 0) ||
          (cellsPerMl.compareTo(new BigDecimal("99999999.99")) > 0)) {
        btxDetails.addActionError(new BtxActionError("error.badvalue", cellsPerMlAsString, "Cells/mL", "a number between 0.01 and 99999999.99 (with at most 2 decimal places)"));
      }
    }    
  }

  private Calendar validateAttributeDate(BTXDetails btxDetails, VariablePrecisionDateTime vpdt, String dateTimeName) {
    Calendar cal = null;

    if (vpdt != null) {
      // Validate date/time information
      // If no date specified, then no time components may be specified.
      if (ApiFunctions.isEmpty(vpdt.getDate())) {
        if (!ApiFunctions.isEmpty(vpdt.getHour())) {
          btxDetails.addActionError(new BtxActionError("dataImport.error.noValueAllowed", "hour of " + dateTimeName, "date of " + dateTimeName));
        }
        if (!ApiFunctions.isEmpty(vpdt.getMinute())) {
          btxDetails.addActionError(new BtxActionError("dataImport.error.noValueAllowed", "minute of " + dateTimeName, "date of " + dateTimeName));
        }
        if (!ApiFunctions.isEmpty(vpdt.getMeridian())) {
          btxDetails.addActionError(new BtxActionError("dataImport.error.noValueAllowed", "am/pm of " + dateTimeName, "date of " + dateTimeName));
        }
      }
      else {
        // If any time component specified, all must be specified correctly.
        Integer hour = ApiFunctions.safeInteger(vpdt.getHour());
        Integer minute = ApiFunctions.safeInteger(vpdt.getMinute());
        String ampm = ApiFunctions.safeString(vpdt.getMeridian());
        boolean dateValidated = true;
        if (hour != null || minute != null || !ApiFunctions.isEmpty(ampm)) {
          if (hour == null || hour.intValue() < 1 || hour.intValue() > 12) {
            btxDetails.addActionError(new BtxActionError("error.badvalue", vpdt.getHour(), "Date/Time of " + dateTimeName + " (hour)", "an integer between 1 and 12"));
            dateValidated = false;
          }
          if (minute == null || minute.intValue() < 0 || minute.intValue() > 59) {
            btxDetails.addActionError(new BtxActionError("error.badvalue", vpdt.getMinute(), "Date/Time of " + dateTimeName + " (minute)", "an integer between 00 and 59"));
            dateValidated = false;
          }
          if (ApiFunctions.isEmpty(ampm)) {
            btxDetails.addActionError(new BtxActionError("error.noValue", "Date/Time of " + dateTimeName + " (am/pm)"));
            dateValidated = false;
          }
        }
        // Check if the value specified (be it just a date or a date and time) is after today's date .
        if (dateValidated) {
          cal = Calendar.getInstance();
          // Call clear to blank out all values on the calendar.  Without this call it is possible
          // that there will be a value in the millisecond field which could cause problems when
          // comparing collection datetime to preservation datetime.
          cal.clear();
          // Date is always in the form mm/dd/yyyy.
          int year = Integer.parseInt(vpdt.getDate().substring(6));
          int month = (Integer.parseInt(vpdt.getDate().substring(0, 2))) - 1; // month is "0" based
          int day = Integer.parseInt(vpdt.getDate().substring(3, 5));
          int hourVal = 0;
          if (hour != null) {
            hourVal = hour.intValue();
            // If AM was specified and the hour value is 12, make it 0.
            if ("AM".equalsIgnoreCase(vpdt.getMeridian()) && hourVal == 12) {
              hourVal = 0;
            }
            // If PM was specified and the value isn't 12 add 12 to the hour value.
            if ("PM".equalsIgnoreCase(vpdt.getMeridian()) && hourVal != 12) {
              hourVal = hourVal + 12;
            }
          }
          int minuteVal = 0;
          if (minute != null) {
            minuteVal = minute.intValue();
          }
          cal.set(year, month, day, hourVal, minuteVal, 0);
          if (cal.getTime().after(new Date(System.currentTimeMillis()))) {
            btxDetails.addActionError(
              new BtxActionError("error.dateMustBeBeforeCurrentDate", "Date/Time of " + dateTimeName));
          }
        }
      }
    }
    return cal;
  }

  private void validateAttributeProcedure(BtxDetailsImportedSample btxDetails) {
    SampleData sampleData = btxDetails.getSampleData();
    String procedure = ApiFunctions.safeString(sampleData.getProcedure());
    String procedureOther = ApiFunctions.safeString(sampleData.getProcedureOther());
    if (procedure.equals(FormLogic.OTHER_PX) 
        && ApiFunctions.isEmptyOrWhitespace(procedureOther)) {
      btxDetails.addActionError(new BtxActionError("error.noOtherText", "Procedure"));
    }
    if (!procedure.equals(FormLogic.OTHER_PX) && !ApiFunctions.isEmpty(procedureOther)) {
      btxDetails.addActionError(new BtxActionError("error.otherTextSpecified", "Procedure"));
    }
  }

  private void validateAttributeTissue(BtxDetailsImportedSample btxDetails) {
    SampleData sampleData = btxDetails.getSampleData();
    String tissue = ApiFunctions.safeString(sampleData.getTissue());
    String tissueOther = ApiFunctions.safeString(sampleData.getTissueOther());
    if (!ApiFunctions.isEmpty(tissue)) {
      if (tissue.equals(FormLogic.OTHER_TISSUE) && ApiFunctions.isEmptyOrWhitespace(tissueOther)) {
        btxDetails.addActionError(new BtxActionError("error.noOtherText", "Tissue"));
      }
      if (!tissue.equals(FormLogic.OTHER_TISSUE) && !ApiFunctions.isEmpty(tissueOther)) {
        btxDetails.addActionError(new BtxActionError("error.otherTextSpecified", "Tissue"));
      }
    }
  }

  private void validateAttributeTotalNumberOfCells(BtxDetailsImportedSample btxDetails) {
    // Validate total number of cells information.  Value must be between 0.01 and 100.00
    SampleData sampleData = btxDetails.getSampleData();
    String totalNumOfCellsAsString = sampleData.getTotalNumOfCellsAsString();
    String totalNumOfCellsExRepCui = ApiFunctions.safeTrim(sampleData.getTotalNumOfCellsExRepCui());
    if (!ApiFunctions.isEmpty(totalNumOfCellsAsString)) {
      BigDecimal totalNumOfCells = sampleData.getTotalNumOfCells();
      if ((totalNumOfCells == null) ||
          (totalNumOfCells.compareTo(new BigDecimal("0.01")) < 0) ||
          (totalNumOfCells.compareTo(new BigDecimal("100.00")) > 0)) {
        btxDetails.addActionError(new BtxActionError("error.badvalue", totalNumOfCellsAsString, "Total Number Of Cells", "a number between 0.01 and 100.00 (with at most 2 decimal places)"));
      }

      // Make sure an exponential representation was specified
      if (ApiFunctions.isEmpty(totalNumOfCellsExRepCui)) {
        btxDetails.addActionError(new BtxActionError("error.noValue", "Total Number Of Cells exponential representation"));
      }
    }
    else {
      // Make sure an exponential representation was not specified.
      if (!ApiFunctions.isEmpty(totalNumOfCellsExRepCui)) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.noValueAllowed", "Total Number Of Cells exponential representation", "Total Number Of Cells"));
      }
    }
    
  }

  private void validateAttributeYield(BtxDetailsImportedSample btxDetails) {
    // Validate Yield information.  Value must be between 0.000 and 9999.999
    SampleData sampleData = btxDetails.getSampleData();
    String yieldAsString = sampleData.getYieldAsString();
    if (!ApiFunctions.isEmpty(yieldAsString)) {
      int decimalIndex = yieldAsString.indexOf(".");
      boolean tooManyDecimals = false;
      if (decimalIndex >= 0) {
        tooManyDecimals = yieldAsString.substring(decimalIndex + 1).length() > 3;
      }
      BigDecimal yield = sampleData.getYield();
      if ((yield == null) ||
          (yield.compareTo(new BigDecimal("0.000")) < 0) ||
          (yield.compareTo(new BigDecimal("9999.999")) > 0) ||
          tooManyDecimals) {
        btxDetails.addActionError(new BtxActionError("error.badvalue", yieldAsString, "Yield", "a number between 0.000 and 9999.999 (with at most 3 decimal places)"));
      }
    }
  }
  
  private void validateAttributeVolume(BtxDetailsImportedSample btxDetails, SampleData persistedSample) {
    // Validate volume information.
    //   - If the persisted sample is consumed, the volume must not be specified or it must be zero.
    //   - If the input sample is consumed, the volume must not be specified or it must be zero.
    //   - If the sample is not consumed, then the volume must be between 0 and 999KG if it 
    //     was specified.
    SampleData sampleData = btxDetails.getSampleData();
    String volumeAsString = sampleData.getVolumeAsString();
    String volumeUnitCui = ApiFunctions.safeTrim(sampleData.getVolumeUnitCui());
    BigDecimal volume = sampleData.getVolume();
    BigDecimal volumeInUl = null;
    
    boolean volumeGreaterThanZero = ((volume != null) && volume.compareTo(new BigDecimal(0.0)) > 0);
    boolean volumeNonNumeric = !ApiFunctions.isEmpty(volumeAsString) && (volume == null);
        
    if ((persistedSample != null) && persistedSample.isConsumed()) {
      if (volumeGreaterThanZero || volumeNonNumeric ) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.alreadyConsumedAndVolume"));
      }
    }
    else if (sampleData.isConsumed()) {
      if (volumeGreaterThanZero || volumeNonNumeric) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.consumedAndVolume"));
      }
    }
    else if (volumeNonNumeric) {
      btxDetails.addActionError(new BtxActionError("error.badvalue", volumeAsString, "volume", "a number between 0 and 99999999 uL"));      
    }
    else if (!ApiFunctions.isEmpty(volumeAsString)) {
      if (ApiFunctions.isEmpty(volumeUnitCui)) {
        btxDetails.addActionError(new BtxActionError("error.noValue", "unit of volume"));
      }      
      else { //at this point both volume and unit are not null  
        BigDecimal ulConversionFactor = Constants.getVolumeInUlConversionFactor(volumeUnitCui);      
        volumeInUl = volume.multiply(ulConversionFactor); 
        if ((volumeInUl == null) 
            || (volumeInUl.compareTo(new BigDecimal("0.000")) < 0) 
            || (volumeInUl.compareTo(new BigDecimal("99999999")) > 0)) {
          btxDetails.addActionError(new BtxActionError("error.badvalue", volumeAsString, "volume", "a number between 0 and 99999999 uL"));
        }
      }
    }
    // Make sure an unit of volume was not specified while volume is null.
    else if (!ApiFunctions.isEmpty(volumeUnitCui)) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.noValueAllowed", "unit of volume", "Volume"));
    }
  }

  private void validateAttributeWeight(BtxDetailsImportedSample btxDetails, SampleData persistedSample) {
    //  Validate weight information.
    //   - If the persisted sample is consumed, the weight must not be specified or it must be zero.
    //   - If the input sample is consumed, the weight must not be specified or it must be zero.
    //   - If the sample is not consumed, then the weight must be between 0 and 1000KG if it 
    //     was specified.
    SampleData sampleData = btxDetails.getSampleData();
    String weightAsString = sampleData.getWeightAsString();
    String weightUnitCui = ApiFunctions.safeTrim(sampleData.getWeightUnitCui());
    BigDecimal weight = sampleData.getWeight();
    BigDecimal weightInMg = null;
    
    boolean weightGreaterThanZero = 
      ((weight != null) && weight.compareTo(new BigDecimal(0.0)) > 0);
  boolean weightNonNumeric = !ApiFunctions.isEmpty(weightAsString) && (weight == null);
    
    if ((persistedSample != null) && persistedSample.isConsumed()) {
      if (weightGreaterThanZero || weightNonNumeric ) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.alreadyConsumedAndWeight"));
      }
    }
    else if (sampleData.isConsumed()) {
      if (weightGreaterThanZero || weightNonNumeric) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.consumedAndWeight"));
      }
    }
    else if (weightNonNumeric) {
      btxDetails.addActionError(new BtxActionError("error.badvalue", weightAsString, "weight", "a number between 0 and 999999999 mg"));
    }
    else if (!ApiFunctions.isEmpty(weightAsString)) {
      if (ApiFunctions.isEmpty(weightUnitCui)) {
        btxDetails.addActionError(new BtxActionError("error.noValue", "unit of weight"));
      }
      else { //at this point both weight and unit are not null  
        BigDecimal mgConversionFactor = Constants.getWeightInMgConversionFactor(weightUnitCui);
        weightInMg = weight.multiply(mgConversionFactor);
        
        if ((weightInMg == null) ||
            (weightInMg.compareTo(new BigDecimal("0.000")) < 0) ||
            (weightInMg.compareTo(new BigDecimal("999999999")) > 0)) {
          btxDetails.addActionError(new BtxActionError("error.badvalue", weightAsString, "weight", "a number between 0 and 999999999 mg"));
        } 
      }
    }
    // Make sure an unit of weight was not specified while weight is null.
    else if (!ApiFunctions.isEmpty(weightUnitCui)) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.noValueAllowed", "unit of weight", "Weight"));
    }    
  }
  
  private void validateAttributeRequired(BtxDetailsImportedSample btxDetails, 
                                         DataFormDefinitionHostElement hostElement) {
    SampleData sampleData = btxDetails.getSampleData();
    String hostId = hostElement.getHostId();  // note: host id is a CUI
    String propertyName = Constants.getPropertyName(hostId);
    String desc = hostElement.getDisplayName();
    if (ApiFunctions.isEmpty(desc)) {
      desc = GbossFactory.getInstance().getDescription(hostId);          
    }
    //MR 9032 - if this is a derivative operation, include the sample id that has the problem
    if (btxDetails.isDerivativeOperationAction()) {
      desc = desc + " on sample " + sampleData.getSampleId();
    }
    Object value = BigrValidator.getValueAsObject(sampleData, propertyName);
    if (value == null || (ApiFunctions.isEmpty(ApiFunctions.safeTrim(value.toString())))) {

      // If the attribute with no value is volume or weight, then only report an error if
      // the sample is not consumed, since we treat consumed as equivalent to setting the 
      // volume to zero.
      if (hostId.equals(ArtsConstants.ATTRIBUTE_VOLUME) 
          || hostId.equals(ArtsConstants.ATTRIBUTE_WEIGHT)) {
        if (!sampleData.isConsumed()) {
          btxDetails.addActionError(new BtxActionError("error.noValue", desc));
        }              
      }

      // For all other attributes, only report an error if this is not a derivative operation.
      // If we're working with the parent of a derivative operation then volume and weight
      // are the only attributes that can be changed, and we checked those above, so don't
      // report errors for other attributes for parents in derivative operations.
      else if (!(btxDetails.isDerivativeOperationAction() && 
          btxDetails instanceof BtxDetailsModifyImportedSample)) {
        btxDetails.addActionError(new BtxActionError("error.noValue", desc));              
      }
    }        
  }

  private void validateAttributeEmpty(BtxDetailsImportedSample btxDetails, String attr) {
    SampleData sampleData = btxDetails.getSampleData();
    String desc = GbossFactory.getInstance().getDescription(attr);
    
    if (attr.equals(ArtsConstants.ATTRIBUTE_BUFFER_TYPE)) {
      String bufferTypeCui = ApiFunctions.safeTrim(sampleData.getBufferTypeCui());
      String bufferTypeOther = ApiFunctions.safeTrim(sampleData.getBufferTypeOther());
      if (!ApiFunctions.isEmpty(bufferTypeCui) || !ApiFunctions.isEmpty(bufferTypeOther)) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }

    else if (attr.equals(ArtsConstants.ATTRIBUTE_CELLS_PER_ML)) {
      if (!ApiFunctions.isEmpty(sampleData.getCellsPerMlAsString())) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_COMMENT)) {
      if (!ApiFunctions.isEmpty(sampleData.getAsmNote())) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_CONCENTRATION)) {
      if (!ApiFunctions.isEmpty(sampleData.getConcentrationAsString())) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION)) {
      VariablePrecisionDateTime vpd = sampleData.getCollectionDateTime();
      if ((vpd != null) && !vpd.isEmpty()) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));        
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION)) {
      VariablePrecisionDateTime vpd = sampleData.getPreservationDateTime();
      if ((vpd != null) && !vpd.isEmpty()) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));        
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE)) {
      if (!ApiFunctions.isEmpty(sampleData.getFixativeType())) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_FORMAT_DETAIL)) {
      if (!ApiFunctions.isEmpty(sampleData.getFormatDetail())) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE)) {
      if (!ApiFunctions.isEmpty(sampleData.getGrossAppearance())) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY)) {
      if (!ApiFunctions.isEmpty(sampleData.getPercentViabilityAsString())) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_PREPARED_BY)) {
      if (!ApiFunctions.isEmpty(sampleData.getPreparedBy())) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_PROCEDURE)) {
      String procedure = ApiFunctions.safeTrim(sampleData.getProcedure());
      String procedureOther = ApiFunctions.safeTrim(sampleData.getProcedureOther());
      if (!ApiFunctions.isEmpty(procedure) || !ApiFunctions.isEmpty(procedureOther)) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_SOURCE)) {
      if (!ApiFunctions.isEmpty(sampleData.getSource())) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_TISSUE)) {
      String tissue = ApiFunctions.safeTrim(sampleData.getTissue());
      String tissueOther = ApiFunctions.safeTrim(sampleData.getTissueOther());
      if (!ApiFunctions.isEmpty(tissue) || !ApiFunctions.isEmpty(tissueOther)) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS)) {
      String totalNumOfCells = ApiFunctions.safeTrim(sampleData.getTotalNumOfCellsAsString());
      String totalNumOfCellsEx = ApiFunctions.safeTrim(sampleData.getTotalNumOfCellsExRepCui());
      if (!ApiFunctions.isEmpty(totalNumOfCells) || !ApiFunctions.isEmpty(totalNumOfCellsEx)) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_VOLUME)) {
      String volume = ApiFunctions.safeTrim(sampleData.getVolumeAsString());
      String volumeUnit = ApiFunctions.safeTrim(sampleData.getVolumeUnitCui());
      if (!ApiFunctions.isEmpty(volume) || !ApiFunctions.isEmpty(volumeUnit)) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_WEIGHT)) {
      String weight = ApiFunctions.safeTrim(sampleData.getWeightAsString());
      String weightUnit = ApiFunctions.safeTrim(sampleData.getWeightUnitCui());
      if (!ApiFunctions.isEmpty(weight) || !ApiFunctions.isEmpty(weightUnit)) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }
    
    else if (attr.equals(ArtsConstants.ATTRIBUTE_YIELD)) {
      if (!ApiFunctions.isEmpty(sampleData.getYieldAsString())) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInRegForm", desc));
      }
    }    
  }
    /*
   * Private method to retrieve the correct lists of Sample Prepared By choices
   */
  private List getSamplePreparedByChoices(BTXDetails btxDetails) {
    List staffNames = new ArrayList();
    try {
      ArdaisstaffAccessBean myStaff = new ArdaisstaffAccessBean();
      AccessBeanEnumeration staffList =
        (AccessBeanEnumeration) myStaff.findArdaisstaffByGeolocation(new GeolocationKey(btxDetails.getLoggedInUserSecurityInfo().getUserLocationId()));
      while (staffList.hasMoreElements()) {
        staffNames.add(new ArdaisStaff((ArdaisstaffAccessBean) staffList.nextElement()));
      } 
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    return staffNames;   
  }

  private Map getSampleRegistrationFormIds(SampleTypeConfiguration config) {
    Map sampleRegistrationFormIds = new HashMap();
    Iterator sampleTypeCuis = config.getSupportedSampleTypes().iterator();
    while (sampleTypeCuis.hasNext()) {
      String sampleTypeCui = (String) sampleTypeCuis.next();
      SampleType sampleType = config.getSampleType(sampleTypeCui);
      sampleRegistrationFormIds.put(sampleTypeCui, sampleType.getRegistrationFormId());
    }
    return sampleRegistrationFormIds;
  }

  
  private BTXDetails performGetRegistrationForm(BtxDetailsGetSampleRegistrationForm btxDetails) 
    throws Exception {
    
    // Note: The validate method already put the form definition in the BTX details if it was 
    // found, so we don't have to do anything.
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;    
  }  
  
  private BTXDetails validatePerformGetRegistrationForm(BtxDetailsGetSampleRegistrationForm btxDetails) 
    throws Exception {

    btxDetails.setPreparedByChoices(getSamplePreparedByChoices(btxDetails));    

    // The sample type is required.
    SampleData sampleData = btxDetails.getSampleData();
    String sampleTypeCui = sampleData.getSampleTypeCui();
    if (ApiFunctions.isEmpty(sampleTypeCui)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Sample Type"));
      return btxDetails;
    }

    // The policy is also required.  First, we'll check if the policy id was specified.  If not
    // we'll attempt to get the persisted version of the sample and get the policy id from it,
    // which means the sample id must have been specified.
    String policyId = btxDetails.getPolicyId();
    if (ApiFunctions.isEmpty(policyId)) {
      String sampleId = sampleData.getSampleId();
      if (ApiFunctions.isEmpty(sampleId)) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.noSampleRegForm"));      
      }
      SampleData persistedSampleData = 
        SampleFinder.find(btxDetails.getLoggedInUserSecurityInfo(), SampleSelect.BASIC,sampleId);
      if (persistedSampleData != null) {
        policyId = persistedSampleData.getPolicyData().getPolicyId();
      }
      else {
        btxDetails.addActionError(new BtxActionError("dataImport.error.noSampleRegForm"));        
      }
    }

    // Get the sample registration form for the sample type from the policy.  If the form is 
    // not found or disabled, return an error, otherwise add it to the btx details.
    PolicyData policyData = PolicyUtils.getPolicyData(policyId);
    String formId = 
      policyData.getSampleTypeConfiguration().getSampleType(sampleTypeCui).getRegistrationFormId();
    FormDefinitionServiceResponse response = 
      FormDefinitionService.SINGLETON.findFormDefinitionById(formId);
    DataFormDefinition form = response.getDataFormDefinition();
    if ((form != null) && form.isEnabled()) {
      sampleData.setRegistrationForm(form);
    }
    else {
      btxDetails.addActionError(new BtxActionError("dataImport.error.noSampleRegForm"));
    }

    return btxDetails;
  }  

  private boolean formHasRequired(DataFormDefinition form) {
    boolean hasRequired = false;
    DataFormDefinitionDataElement[] dataElements = form.getDataDataElements();
    for (int i = 0; i < dataElements.length && !hasRequired; i++) {
      if (dataElements[i].isRequired()) {
        hasRequired = true;
      }
    }
    DataFormDefinitionHostElement[] hostElements = form.getDataHostElements();
    for (int i = 0; i < hostElements.length && !hasRequired; i++) {
      if (hostElements[i].isRequired()) {
        hasRequired = true;
      }
    }
    return hasRequired;
  }

  private void populateLabelPrintingData(BtxDetailsImportedSample btxDetails) {
    //determine the templates available for each sample type, and the printers available for each
    //template. if the sample label printing configuration information is invalid, return a message 
    //letting the user know about the problem
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String accountId = securityInfo.getAccount();
    try {
      Map sampleTypesToTemplates = LabelPrintingConfiguration.getSampleLabelTemplateDefinitionsAndPrintersBySampleType(accountId);
      btxDetails.setLabelPrintingData(sampleTypesToTemplates);
    }
    catch (IllegalStateException ise) {
      //log the error so the problems are documented
      ApiLogger.log("Label Printing Configuration refresh failed.", ise);
      btxDetails.setLabelPrintingData(new HashMap());
      btxDetails.addActionMessage(
          new BtxActionMessage("orm.error.label.invalidPrintingConfiguration"));
    }
  }

  private void populateSampleAliasRequired(BtxDetailsImportedSample btxDetails) {
    //determine if the account requires that a sample alias value be provided
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String accountId = securityInfo.getAccount();
    AccountDto accountDto = IltdsUtils.getAccountById(accountId, false, false);
    boolean aliasRequired = FormLogic.DB_YES.equalsIgnoreCase(accountDto.getRequireSampleAliases());
    btxDetails.setSampleAliasRequired(aliasRequired);
  }
  
  private void validateLabelPrintingData(BtxDetailsImportedSample btxDetails, boolean sampleExists) {
    if (btxDetails.isPrintSampleLabel()) {
      //since all of the logic for validating the printing of sample labels is contained
      //in the label service, call out to the service and convert any errors returned
      //into BtxActionErrors.
      LabelService labelService = new LabelService();
      BigrValidationErrors bve = null;
      if (sampleExists) {
        String[] sampleIds = new String[1];
        sampleIds[0] = btxDetails.getSampleData().getSampleId();
        bve = labelService.validatePrintSampleLabels(btxDetails.getLoggedInUserSecurityInfo(), 
            sampleIds, btxDetails.getLabelCount() , btxDetails.getTemplateDefinitionName(), btxDetails.getPrinterName());
      }
      else {
        Sample sample = new Sample();
        sample.setSampleId(btxDetails.getSampleData().getSampleId());
        Sample[] sampleArray = new Sample[1];
        sampleArray[0] = sample;
        bve = labelService.validatePrintSampleLabels(btxDetails.getLoggedInUserSecurityInfo(), 
            sampleArray, btxDetails.getLabelCount() , btxDetails.getTemplateDefinitionName(), btxDetails.getPrinterName());
      }
      BigrValidationError[] errors = bve.getErrors();
      for (int i = 0; i < errors.length; i++) {
        btxDetails.addActionError(new BtxActionError("generic.message", errors[i].getMessage()));
      }
    }
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
