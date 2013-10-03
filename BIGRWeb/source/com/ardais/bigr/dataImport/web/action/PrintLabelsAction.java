package com.ardais.bigr.dataImport.web.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.configuration.LabelPrintingConfiguration;
import com.ardais.bigr.dataImport.web.form.PrintLabelsForm;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.btx.BtxActionMessages;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.bigr.Sample;
import com.gulfstreambio.bigr.error.BigrValidationError;
import com.gulfstreambio.bigr.error.BigrValidationException;
import com.gulfstreambio.bigr.id.SampleIdService;
import com.gulfstreambio.bigr.labels.LabelService;

public class PrintLabelsAction extends BigrAction {
  
  private final String PROCESS_STEP1 = "step1";
  private final String PROCESS_STEP2 = "step2";
  private final String PROCESS_STEP3 = "step3";

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {
    
    ActionForward myActionForward = null;
    PrintLabelsForm labelForm = (PrintLabelsForm)form;
    SecurityInfo securityInfo = getSecurityInfo(request);
    
    //try to populate the label printing data so we can populate the template and printer dropdowns.  
    //If there is no such data, return an error.
    Map labelPrintingData = null;
    String objectType = labelForm.getObjectType();
    if (!ApiFunctions.isEmpty(objectType)) {
      ActionErrors errors = new ActionErrors();
      try {
        labelPrintingData = LabelPrintingConfiguration.getLabelTemplateDefinitionsAndPrintersForObjectType(securityInfo.getAccount(), objectType);
        if (ApiFunctions.isEmpty(labelPrintingData)) {
          ActionError error = new ActionError("dataImport.error.label.unsupportedObjectType", ApiFunctions.capitalize(objectType));
          errors.add(ActionErrors.GLOBAL_ERROR, error);
        }
      }
      catch (IllegalStateException ise) {
        //log the error so the problems are documented
        ApiLogger.log("Label Printing Configuration refresh failed.", ise);
        ActionError error = new ActionError("orm.error.label.invalidPrintingConfiguration");
        errors.add(ActionErrors.GLOBAL_ERROR, error);
      }
      if (!errors.isEmpty()) {
        super.saveErrors(request, errors);
        myActionForward = mapping.findForward("retry");
        return myActionForward;
        
      }
    }
    labelForm.setLabelPrintingData(labelPrintingData);
    
    //get the step in the process that we are dealing with
    String step = mapping.getTag();
    
    //if the data provided isn't valid return an error
    if (!isDataValid(step, labelForm, request)) {
        myActionForward = mapping.findForward("retry");
        return myActionForward;
    }
    
    //if we're in the 3rd step of the process (where the details have been provided and the labels
    //are to be printed), try to print the labels.  If label printing fails, return the error(s)
    if (PROCESS_STEP3.equalsIgnoreCase(step)) {
      try {
        LabelService labelService = new LabelService();
        BtxActionMessage message = null;
        String action = labelForm.getAction();
        if (Constants.LABEL_PRINTING_ACTION_PREPRINT.equalsIgnoreCase(action)) {
          int preprintedLabelCount = new Integer(labelForm.getPreprintedLabelCount()).intValue();
          List sampleList = new ArrayList();
          for (int i=0; i<preprintedLabelCount; i++) {
            Sample sample = new Sample();
            sample.setSampleId(SampleIdService.getInstance().generateId(securityInfo));
            sampleList.add(sample);
          }
          Sample[] sampleArray = new Sample[sampleList.size()];
          Iterator sampleIterator = sampleList.iterator();
          for (int i = 0; sampleIterator.hasNext(); i++) {
            Sample sample = (Sample) sampleIterator.next();
            sampleArray[i] = sample;
          }
          labelService.printSampleLabels(securityInfo, sampleArray, labelForm.getLabelCount(), 
              labelForm.getTemplateDefinitionName(), labelForm.getPrinterName());
          if (preprintedLabelCount == 1) {
            message = new BtxActionMessage("orm.message.label.labelPrinted", "the specified", objectType);
          }
          else {
            message = new BtxActionMessage("orm.message.label.labelsPrinted", "the specified", objectType + "s");
          }
          BtxActionMessages messages = new BtxActionMessages();
          messages.add(message);
          super.saveMessages(request, messages);
        }
        else if (Constants.LABEL_PRINTING_ACTION_REPRINT.equalsIgnoreCase(action)) {
          if (Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE.equalsIgnoreCase(objectType)) {
            labelService.printSampleLabels(securityInfo, labelForm.getIds(), labelForm.getLabelCount(), 
                labelForm.getTemplateDefinitionName(), labelForm.getPrinterName());
          }
          else if (Constants.LABEL_PRINTING_OBJECT_TYPE_CASE.equalsIgnoreCase(objectType)) {
            labelService.printCaseLabels(securityInfo, labelForm.getIds(), labelForm.getLabelCount(), 
                labelForm.getTemplateDefinitionName(), labelForm.getPrinterName());
          }
          else if (Constants.LABEL_PRINTING_OBJECT_TYPE_DONOR.equalsIgnoreCase(objectType)) {
            labelService.printDonorLabels(securityInfo, labelForm.getIds(), labelForm.getLabelCount(), 
                labelForm.getTemplateDefinitionName(), labelForm.getPrinterName());
          }
          if (labelForm.getIds().length == 1) {
            message = new BtxActionMessage("orm.message.label.labelPrinted", "the specified", objectType);
          }
          else {
            message = new BtxActionMessage("orm.message.label.labelsPrinted", "the specified", objectType + "s");
          }
          BtxActionMessages messages = new BtxActionMessages();
          messages.add(message);
          super.saveMessages(request, messages);
        }
        myActionForward = mapping.findForward("success");
      }
      catch (BigrValidationException bve) {
        ActionErrors errors = new ActionErrors();
        BigrValidationError[] bigrErrors = bve.getErrors().getErrors();
        for (int i = 0; i < bigrErrors.length; i++) {
          ActionError error = new ActionError("generic.message", bigrErrors[i].getMessage());
          errors.add(ActionErrors.GLOBAL_ERROR, error);
        }
        super.saveErrors(request, errors);
        myActionForward = mapping.findForward("retry");
      }
    }
    //otherwise, return success so the next step in the process can be performed
    else {
      myActionForward = mapping.findForward("success");
    }
    return myActionForward;
  }
  
  /*
   * This method validates that enough information has been provided to call out to the
   * LabelService to print labels.  It leaves validation of the actual printing information to the 
   * LabelService.  For example, this method validates that an action (pre-print or re-print)
   * has been specified, so this class can make the appropriate call to the LabelService.
   * It does not validate that a printer has been specified, as that validation is done in the
   * LabelService and there is no reason to duplicate the logic here.
   */
  private boolean isDataValid(String step, PrintLabelsForm form, HttpServletRequest request) {
    ActionErrors errors = new ActionErrors();
    ActionError error = null;
    
    //verify that an action has been specified so we can determine which LabelService
    //method to call
    String action = form.getAction();
    if (ApiFunctions.isEmpty(action)) {
      error = new ActionError("error.noValue", "action");
      errors.add(ActionErrors.GLOBAL_ERROR, error);
    }
    else {
      if (Constants.LABEL_PRINTING_ACTIONS.get(action) == null) {
        error = new ActionError("general.error.invalidValue", action, "action");
        errors.add(ActionErrors.GLOBAL_ERROR, error);
      }
    }

    //verify that an object type has been specified so we can determine which LabelService
    //method to call
    String objectType = form.getObjectType();
    if (ApiFunctions.isEmpty(objectType)) {
      error = new ActionError("error.noValue", "object type");
      errors.add(ActionErrors.GLOBAL_ERROR, error);
    }
    else {
      if (Constants.LABEL_PRINTING_OBJECT_TYPES.get(objectType) == null) {
        error = new ActionError("general.error.invalidValue", objectType, "object type");
        errors.add(ActionErrors.GLOBAL_ERROR, error);
      }
    }
    
    //if the action is pre-print, the object type must be donor (we do not currently support
    //pre-printing donor or case labels)
    if (!ApiFunctions.isEmpty(action) && !ApiFunctions.isEmpty(objectType) &&
        Constants.LABEL_PRINTING_ACTION_PREPRINT.equalsIgnoreCase(action) &&
        !Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE.equalsIgnoreCase(objectType)) {
      error = new ActionError("dataImport.error.label.prePrintSampleLabelsOnly");
      errors.add(ActionErrors.GLOBAL_ERROR, error);
    }
    
    //if we're in the 3rd step of the process (where the details have been provided and the labels
    //are to be printed), do additional validation
    if (PROCESS_STEP3.equalsIgnoreCase(step)) {
      //if the user wants to pre-print labels, an integer value must have been
      //specified for the number of labels to print so we can call the LabelService
      //with the correct number of new ids
      if (Constants.LABEL_PRINTING_ACTION_PREPRINT.equalsIgnoreCase(action)) {
        String preprintedLabelCount = form.getPreprintedLabelCount();
        if (!ApiFunctions.isPositiveInteger(preprintedLabelCount) || new Integer(preprintedLabelCount).intValue() == 0) {
          error = new ActionError("errors.int", "Number of " + objectType + "s");
          errors.add(ActionErrors.GLOBAL_ERROR, error);
        }
      }
      
      //other validation, such as making sure a label template and printer have been
      //specified, occurs in the LabelService so don't duplicate that logic here.
    }
    super.saveErrors(request, errors);
    return errors.isEmpty();
  }

}
