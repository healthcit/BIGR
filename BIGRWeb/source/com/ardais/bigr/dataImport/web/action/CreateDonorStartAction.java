package com.ardais.bigr.dataImport.web.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.configuration.LabelPrintingConfiguration;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateImportedCase;
import com.ardais.bigr.pdc.helpers.DonorHelper;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;

public class CreateDonorStartAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    ActionForward actionForward = mapping.findForward("success");
    ActionErrors errors = new ActionErrors();
    SecurityInfo securityInfo = getSecurityInfo(request);
    DonorData data = new DonorData();
    data.setImportedYN("Y");
    data.setArdaisAccountKey(securityInfo.getAccount());
    DonorHelper donorHelper = new DonorHelper(data);
    donorHelper.setNew(true);
    Collection messages = (Collection)request.getAttribute("msg");
    if (!ApiFunctions.isEmpty(messages)) {
      Iterator iterator = messages.iterator();
      while (iterator.hasNext()) {
        donorHelper.getMessageHelper().addMessage((String)iterator.next());
      }
    }
    
    // Get the form definitions for donors in the user's account.
    donorHelper.findFormDefinitions(securityInfo);
    request.setAttribute("helper", donorHelper);
    
    // Find the donor registration form.  If we could not find it, then report an error.
    DataFormDefinition regForm = donorHelper.findRegistrationForm(securityInfo);
    if (regForm != null) {
      donorHelper.setupKcFormContext(request, regForm);
    }
    else {
      ActionError error = new ActionError("dataImport.error.noDonorRegistrationFormSpecifiedForAccount");
      errors.add(BtxActionErrors.GLOBAL_ERROR, error);
      actionForward = mapping.getRetryActionForward();
    }
    
    //populate the label printing information.  If it is invalid then return an error
    //letting the user know that label printing is unavailable.
    String accountId = securityInfo.getAccount();
    try {
      Map labelPrintingData = LabelPrintingConfiguration.getLabelTemplateDefinitionsAndPrintersForObjectType(securityInfo.getAccount(), Constants.LABEL_PRINTING_OBJECT_TYPE_DONOR);
      donorHelper.setLabelPrintingData(labelPrintingData);
      //default the number of labels to print to 1
      donorHelper.setLabelCount("1");
    }
    catch (IllegalStateException ise) {
      //log the error so the problems are documented
      ApiLogger.log("Label Printing Configuration refresh failed.", ise);
      donorHelper.setLabelPrintingData(new HashMap());
      ActionError error = new ActionError("orm.error.label.invalidPrintingConfiguration");
      errors.add(BtxActionErrors.GLOBAL_ERROR, error);
    }
    
    if (!errors.isEmpty()) {
      super.saveErrors(request, errors);
    }
    
    return actionForward;      
  }
}
