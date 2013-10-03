package com.ardais.bigr.iltds.performers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.ObjectNotFoundException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.ConsentKey;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxDetailsFindById;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.util.EjbHomes;

/**
 * This performs case-related ILTDS BTX business transactions.
 */
public class BtxPerformerFinderOperations extends BtxTransactionPerformerBase {

  public BtxPerformerFinderOperations() {
    super();
  }

  /**
   * Do BTX transaction validation for the performFindById performer method.
   */
  private BTXDetails validatePerformFindById(BtxDetailsFindById btxDetails) throws Exception {
    //make sure an id has been specified
    btxDetails.setArdaisId(ApiFunctions.safeTrim(btxDetails.getArdaisId()));
    String bigrId = btxDetails.getArdaisId();
    boolean validBigrId = true;
    //pad zeroes as appropriate if an Ardais id was specified
    if (!ApiFunctions.isEmpty(bigrId)) {
      String tempId = ValidateIds.validateId(bigrId, ValidateIds.TYPESET_DONOR_IMPORTED, true);
      if (ApiFunctions.isEmpty(tempId)) {
        tempId = ValidateIds.validateId(bigrId, ValidateIds.TYPESET_CASE_IMPORTED, true);
      }
      if (ApiFunctions.isEmpty(tempId)) {
        tempId = ValidateIds.validateId(bigrId, ValidateIds.TYPESET_SAMPLE_IMPORTED, true);
      }
      if (!ApiFunctions.isEmpty(tempId)) {
        btxDetails.setArdaisId(tempId);
        bigrId = btxDetails.getArdaisId();
      }
      else {
        validBigrId = false;
      }
    }
    btxDetails.setCustomerId(ApiFunctions.safeTrim(btxDetails.getCustomerId()));
    String customerId = btxDetails.getCustomerId();
    
    //make sure the user entered a BIGR id or an alias
    if (ApiFunctions.isEmpty(bigrId) && ApiFunctions.isEmpty(customerId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "id or alias"));
    }
    //make sure the user has not specified both a BIGR id and an alias
    else if ((!ApiFunctions.isEmpty(bigrId) && !ApiFunctions.isEmpty(customerId))) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.cannotMixArdaisAndCustomerIds"));
    }
    //if the user specified a BIGR id make sure it was valid
    else if (!ApiFunctions.isEmpty(bigrId) && !validBigrId) {
      btxDetails.addActionError(new BtxActionError("dataImport.error.invalidValue", "BIGR ID"));
    }
    //if the user specified an alias make sure they indicated what object types to find
    else if (!ApiFunctions.isEmpty(customerId)) {
      String findDonors = btxDetails.getFindDonors();
      String findCases = btxDetails.getFindCases();
      String findSamples = btxDetails.getFindSamples();
      if (!"Y".equalsIgnoreCase(findDonors) &&
          !"Y".equalsIgnoreCase(findCases) &&
          !"Y".equalsIgnoreCase(findSamples)) {
        btxDetails.addActionError(new BtxActionError("dataImport.error.noObjectsSpecified"));
      }
    }

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that finds objects by id.
   */
  private BTXDetails performFindById(BtxDetailsFindById btxDetails) throws Exception {
    boolean findDonors = false;
    boolean findCases = false;
    boolean findSamples = false;

    String account = btxDetails.getLoggedInUserSecurityInfo().getAccount();
    
    //if an Ardais id is specified, figure out what type of object it is
    String ardaisId = btxDetails.getArdaisId();
    if (!ApiFunctions.isEmpty(ardaisId)) {
      if (!ApiFunctions.isEmpty(ValidateIds.validateId(ardaisId, ValidateIds.TYPESET_DONOR_IMPORTED, true))) {
        findDonors = true;
      }
      else if (!ApiFunctions.isEmpty(ValidateIds.validateId(ardaisId, ValidateIds.TYPESET_CASE_IMPORTED, true))) {
        findCases = true;
      }
      else if (!ApiFunctions.isEmpty(ValidateIds.validateId(ardaisId, ValidateIds.TYPESET_SAMPLE_IMPORTED, true))) {
        findSamples = true;
      }
    }
    else {
      findDonors = "Y".equalsIgnoreCase(btxDetails.getFindDonors());
      findCases = "Y".equalsIgnoreCase(btxDetails.getFindCases());
      findSamples = "Y".equalsIgnoreCase(btxDetails.getFindSamples());
    }
    
    //find donors, if appropriate
    if (findDonors) {
      btxDetails.setDonorsSearched(true);
      List donorList = new ArrayList();
      btxDetails.setDonors(donorList);
      List donorIds = new ArrayList();
      String donorId = btxDetails.getArdaisId();
      donorIds.add(donorId);
      String customerId = btxDetails.getCustomerId();
      if (!ApiFunctions.isEmpty(customerId)) {
        donorIds.clear();
        donorIds = PdcUtils.findDonorIdsFromLikeCustomerId(btxDetails.getCustomerId(), account);
      }
      
      Iterator donorIterator = donorIds.iterator();
      
      if (donorIterator.hasNext()) {
        DDCDonorHome home = null;
        DDCDonor donorOperation = null;
        while (donorIterator.hasNext()) {
          donorId = (String) donorIterator.next();
          // Get the donor profile information.
          DonorData donor = new DonorData();
          donor.setArdaisId(donorId);
          if (home == null) home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
          if (donorOperation == null) donorOperation = home.create();
          DonorData donorData = donorOperation.getDonorProfile(donor);
          boolean isImported = "Y".equalsIgnoreCase(donorData.getImportedYN());
          boolean inAccount = account.equalsIgnoreCase(donorData.getArdaisAccountKey());
          if (isImported && inAccount) {
            donorList.add(donorData);
          }
        }
      }
    }
    
    //find cases, if appropriate
    if (findCases) {
      btxDetails.setCasesSearched(true);
      List caseList = new ArrayList();
      btxDetails.setCases(caseList);
      ConsentData consent = null;
      List consentList = new ArrayList();
      if (!ApiFunctions.isEmpty(btxDetails.getCustomerId())) {
        consentList = IltdsUtils.findConsentsFromLikeCustomerId(btxDetails.getCustomerId(), account);
      }
      else {
        try {
          // Get the case information.
          ConsentAccessBean consentBean = new ConsentAccessBean(new ConsentKey(btxDetails.getArdaisId()));
          consent = new ConsentData(consentBean);
          consentList.add(consent);
        }
        catch (ObjectNotFoundException e){
          //nothing to do here - no case with the specified id exists.
        }
      }
      
      Iterator consentIterator = consentList.iterator();
      if (consentIterator.hasNext()) {
        DDCDonorHome home = null;
        DDCDonor donorOperation = null;
        while (consentIterator.hasNext()) {
          consent = (ConsentData) consentIterator.next(); 
          boolean isImported = "Y".equalsIgnoreCase(consent.getImportedYN());
          boolean inAccount = account.equalsIgnoreCase(consent.getArdaisAcctKey());
          if (isImported && inAccount) {
            //get the donor for the case so we can display the donor customer id
            DonorData donor = new DonorData();
            donor.setArdaisId(consent.getDonorId());
            if (home == null) home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
            if (donorOperation == null) donorOperation = home.create();
            DonorData donorData = donorOperation.getDonorProfile(donor);
            consent.setDonorCustomerId(donorData.getCustomerId());
            caseList.add(consent);
          }
        }
      }
    }
    
    //find samples, if appropriate
    if (findSamples) {
      btxDetails.setSamplesSearched(true);
      if (!ApiFunctions.isEmpty(btxDetails.getCustomerId())) {
        btxDetails.setSamples(IltdsUtils.findSamplesFromLikeCustomerId(btxDetails.getCustomerId(), account));
      }
      else {
        List sampleList = new ArrayList();
        btxDetails.setSamples(sampleList);
        try {
          // Get the sample information.
          SampleAccessBean sampleBean = new SampleAccessBean(new SampleKey(btxDetails.getArdaisId()));
          SampleData sample = new SampleData(sampleBean);
          boolean isImported = "Y".equalsIgnoreCase(sample.getImportedYN());
          boolean inAccount = account.equalsIgnoreCase(sample.getArdaisAcctKey());
          if (isImported && inAccount) {
            sampleList.add(sample);
          }
        }
        catch (ObjectNotFoundException e){
          //nothing to do here - no sample with the specified id exists.
        }
      }
      //for each sample, get the donor and case alias values for display
      if (!ApiFunctions.isEmpty(btxDetails.getSamples())) {
        Iterator iterator = btxDetails.getSamples().iterator();
        DDCDonorHome home = null;
        DDCDonor donorOperation = null;
        while (iterator.hasNext()) {
          SampleData s = (SampleData)iterator.next();
          if (!ApiFunctions.isEmpty(ApiFunctions.safeString(s.getConsentId()))) {
            //get the case for the sample so we can display the sample customer id
            try {
              // Get the case information.
              ConsentAccessBean consentBean = new ConsentAccessBean(new ConsentKey(s.getConsentId()));
              ConsentData consent = new ConsentData(consentBean);
              s.setConsentAlias(consent.getCustomerId());
            }
            catch (ObjectNotFoundException e){
              //nothing to do here - no case with the specified id exists.
            }
          }
          if (!ApiFunctions.isEmpty(ApiFunctions.safeString(s.getArdaisId()))) {
            //get the donor for the sample so we can display the donor customer id
            DonorData donor = new DonorData();
            donor.setArdaisId(s.getArdaisId());
            if (home == null) home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
            if (donorOperation == null) donorOperation = home.create();
            DonorData donorData = donorOperation.getDonorProfile(donor);
            s.setDonorAlias(donorData.getCustomerId());
          }
        }
      }
    }
    
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
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
