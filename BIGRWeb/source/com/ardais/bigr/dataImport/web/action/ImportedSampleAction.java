package com.ardais.bigr.dataImport.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.dataImport.web.form.SampleForm;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.btx.BtxDetailsImportedSample;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.action.BtxAction;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.bigr.error.BigrValidationError;
import com.gulfstreambio.bigr.error.BigrValidationException;
import com.gulfstreambio.bigr.id.SampleIdService;
import com.gulfstreambio.bigr.labels.LabelService;

public class ImportedSampleAction extends BtxAction {
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.web.action.BtxAction#doBtxPerform(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, com.ardais.bigr.web.form.BigrActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  protected BTXDetails doBtxPerform(BTXDetails btxDetails0, BigrActionMapping mapping,
                                    BigrActionForm form0, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

    SecurityInfo securityInfo = getSecurityInfo(request);
    SampleForm sampleForm = (SampleForm)form0;
    SampleData sampleData = sampleForm.getSampleData();
    
    //if the mapping has a tag that indicates to check if the specified alias (if any) maps to a
    //box scanned sample, try to do so.  We do this to handle the case where a sample was box 
    //scanned with an alias, and we want to perform the transaction using that previously box 
    //scanned sample instead of creating a brand new sample.
    boolean substituteBoxScannedSample = "CheckAliasForBoxScannedSample".equalsIgnoreCase(ApiFunctions.safeString(mapping.getTag()));
    String alias = ApiFunctions.safeTrim(sampleData.getSampleAlias());
    if (substituteBoxScannedSample && !ApiFunctions.isEmpty(alias)) {
      //first, see if sample alias values must be unique for the users account (this value was set
      //on the sample in the preperation step, and will be the same as the securityInfo account
      //value.  If not, don't proceed further since there may be multiple samples with the specified 
      //alias and for now we only make this substitution in the case where we know there is at most 
      //one such sample
      String accountId = sampleData.getArdaisAcctKey();
      AccountDto accountDto = IltdsUtils.getAccountById(accountId, false, false);
      boolean aliasMustBeUnique = FormLogic.DB_YES.equalsIgnoreCase(accountDto.getRequireUniqueSampleAliases());
      //if the alias must be unique, see if there is a single sample with the specified alias that 
      //has been box scanned but has not gone through full registration (either Create Sample or 
      //Derivation Operations). If so, fill in our sampleData object with that sample's id, so it 
      //will be used.  We intentionally ignore any sample id that may have been specified by the
      //user in this situation as well as any specification to generate a sample id.  If there is
      //a matching sample we assume that is the one to use regardless of anything else.  Otherwise,
      //we'd have to return an error since we can't have 2 samples with the same alias.  That could
      //be confusing to the user, since they won't be able to perform most transactions on a sample
      //that has only been box scanned and thus may not be able to find the existing sample with
      //the alias they are trying to use here.
      if (aliasMustBeUnique) {
        List matchingSamples = IltdsUtils.findBoxScannedSamplesFromCustomerId(alias, accountId);
        if (matchingSamples.size() == 1) {
          SampleData matchingSample = (SampleData)matchingSamples.get(0);
          String sampleId = matchingSample.getSampleId();
          sampleData.setSampleId(sampleId);
          sampleData.getRegistrationFormInstance().setDomainObjectId(sampleId);
          //don't generate a sample id, since we're using the one from the matching sample
          sampleForm.setGenerateSampleId(false);
        }
      }
    }
    
    // If the user requested that the sample id be generated, and there is not already a sample id,
    // then generate the id.  Note that the id may have already been generated, for example if
    // validation for creating the sample failed and the user resubmits, so use the previously 
    // generated id if it exists.  We intentionally generate the id first in a separate 
    // transaction (which is done by the SampleIdService) since we need all other transactions to 
    // recognize that the id is used as soon as we generate it. 
    boolean generateId = 
      ApiFunctions.isEmpty(sampleData.getSampleId()) && sampleForm.isGenerateSampleId();

    if (generateId) {
      String sampleId = sampleForm.getGeneratedSampleId();
      if (ApiFunctions.isEmpty(sampleId)) {
        try {
          sampleId = SampleIdService.getInstance().generateId(securityInfo);
        }
        catch (BigrValidationException e) {
          BigrValidationError[] errors = e.getErrors().getErrors();
          for (int i = 0; i < errors.length; i++) {
            btxDetails0.addActionError(new BtxActionError("generic.message", errors[i].getMessage()));
          }
          return btxDetails0;
        }
      }
      sampleData.setSampleId(sampleId);
      sampleData.getRegistrationFormInstance().setDomainObjectId(sampleId);
    }
    
    // Perform the sample creation.
    BtxDetailsImportedSample btxDetails = 
      (BtxDetailsImportedSample) super.doBtxPerform(btxDetails0, mapping, form0, request, response);
    
    //print a label for the sample if directed to do so and there were no errors from
    //the btx call above
    boolean txCompleted = btxDetails.isTransactionCompleted() && !btxDetails.isHasException();
    if (txCompleted && sampleForm.isPrintSampleLabel()) {
      LabelService labelService = new LabelService();
      try {
        labelService.printSampleLabels(securityInfo, new String[] {sampleForm.getSampleData().getSampleId()},
            sampleForm.getLabelCount(), sampleForm.getTemplateDefinitionName(), sampleForm.getPrinterName());
        StringBuffer sampleMessage = new StringBuffer(50);
        sampleMessage.append(sampleForm.getSampleData().getSampleId());
        if (!ApiFunctions.isEmpty(sampleForm.getSampleData().getSampleAlias())) {
          sampleMessage.append(" (");
          Escaper.htmlEscape(sampleForm.getSampleData().getSampleAlias(), sampleMessage);
          sampleMessage.append(")");
        }
        btxDetails.addActionMessage(new BtxActionMessage("orm.message.label.labelPrinted", 
            "sample", sampleMessage.toString()));
      }
      catch (BigrValidationException bve) {
        BigrValidationError[] errors = bve.getErrors().getErrors();
        for (int i = 0; i < errors.length; i++) {
          btxDetails.addActionError(new BtxActionError("generic.message", "A label for the sample was not printed. " + errors[i].getMessage()));
        }
      }
    }
    
    // If the sample creation did not complete successfully and we generated the id, then copy the 
    // generated sample id to the hidden sample id field and clear the sample id so the user does 
    // not see the generated sample id in the UI which might lead to some confusion.  When the user 
    // fixes the errors and resubmits, we'll use the value of the hidden generated sample id so we 
    // don't have to generate another sample id and waste the previous one.
    if (!txCompleted && generateId) {
      sampleData = btxDetails.getSampleData();
      sampleForm.setGeneratedSampleId(sampleData.getSampleId());
      sampleData.setSampleId(null);       
    }
    
    // If the transaction completed successfully, then make sure that the generated id is null.
    // This may have been set on a previous pass through this transaction that resulted in
    // validation errors.
    if (txCompleted) {
      sampleForm.setGeneratedSampleId(null);
    }
    return btxDetails;
  }
}
