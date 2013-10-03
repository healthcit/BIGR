package com.ardais.bigr.dataImport.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.dataImport.web.form.CaseForm;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateImportedCase;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.action.BtxAction;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.bigr.error.BigrValidationError;
import com.gulfstreambio.bigr.error.BigrValidationException;
import com.gulfstreambio.bigr.labels.LabelService;

public class ImportedCaseAction extends BtxAction {
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.web.action.BtxAction#doBtxPerform(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, com.ardais.bigr.web.form.BigrActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  protected BTXDetails doBtxPerform(BTXDetails btxDetails0, BigrActionMapping mapping,
                                    BigrActionForm form0, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

    
    // Perform the case based activity (create or modify).
    BtxDetailsCreateImportedCase btxDetails = 
      (BtxDetailsCreateImportedCase) super.doBtxPerform(btxDetails0, mapping, form0, request, response);
    
    //print a label for the case if directed to do so and there were no errors from
    //the btx call above
    SecurityInfo securityInfo = getSecurityInfo(request);
    CaseForm caseForm = (CaseForm)form0;
    boolean txCompleted = btxDetails.isTransactionCompleted() && !btxDetails.isHasException();
    if (txCompleted && caseForm.isPrintCaseLabel()) {
      LabelService labelService = new LabelService();
      try {
        labelService.printCaseLabels(securityInfo, new String[] {btxDetails.getConsentId()},
            caseForm.getLabelCount(), caseForm.getTemplateDefinitionName(), caseForm.getPrinterName());
        btxDetails.addActionMessage(new BtxActionMessage("orm.message.label.labelPrinted", 
            "case", btxDetails.getConsentId()));
      }
      catch (BigrValidationException bve) {
        BigrValidationError[] errors = bve.getErrors().getErrors();
        for (int i = 0; i < errors.length; i++) {
          btxDetails.addActionError(new BtxActionError("generic.message", "A label for the case was not printed. " + errors[i].getMessage()));
        }
      }
    }

    return btxDetails;
  }
}
