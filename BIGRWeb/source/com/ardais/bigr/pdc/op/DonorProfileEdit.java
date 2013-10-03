package com.ardais.bigr.pdc.op;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionError;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.configuration.LabelPrintingConfiguration;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateImportedCase;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.helpers.DonorHelper;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.StrutsProperties;
import com.gulfstreambio.bigr.error.BigrValidationError;
import com.gulfstreambio.bigr.error.BigrValidationErrors;
import com.gulfstreambio.bigr.error.BigrValidationException;
import com.gulfstreambio.bigr.labels.LabelService;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.FormInstanceService;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;

/**
 * Insert the type's description here.
 * Creation date: (7/15/2002 2:52:08 PM)
 * @author: Jake Thompson
 */
public class DonorProfileEdit extends StandardOperation {
/**
 * DonorProfileEdit constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public DonorProfileEdit(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res, javax.servlet.ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * Insert the method's description here.
 * Creation date: (7/15/2002 2:52:08 PM)
 */
public void invoke() {
  String user = (String)request.getSession(false).getAttribute("user");
  DonorData returnedDonorData = null;
	
  // Create a new donor helper, which will obtain BIGR core element values from the request.
  DonorHelper helper = new DonorHelper(request);
  request.setAttribute("helper", helper);

  // Create a new form instance from the JSON , create DataElement instances to hold all of the input values, 
  // and copy all of the DataElement instances to the form.
  String jsonForm = helper.getForm();
  FormInstance form = ApiFunctions.isEmpty(jsonForm) ? 
      new FormInstance() : com.gulfstreambio.kc.web.support.WebUtils.convertToFormInstance(jsonForm);

  // Validate that we can find the registration form.  
  SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
  DataFormDefinition regForm = helper.findRegistrationForm(securityInfo);
  if (regForm == null) {
    StrutsProperties props = StrutsProperties.getInstance();
    String msg = props.getProperty("dataImport.error.noDonorRegistrationFormSpecifiedForAccount");
    helper.getMessageHelper().addMessage(msg);
    helper.getMessageHelper().setError(true);
    helper.findFormDefinitions(securityInfo);
    helper.getDataBean().setFormInstance(form);
    (new DonorProfilePrepare(request, response, servletCtx)).invoke();
    return;
  }
  
  // Validate the BIGR core element values.
  boolean isValid = helper.isValid(regForm);

  // Validate the KC data element values.
  if (form.getDataElements().length > 0) {
    form.setDomainObjectId(helper.getArdaisId());
    form.setDomainObjectType(TagTypes.DOMAIN_OBJECT_VALUE_DONOR);
    form.setFormDefinitionId(regForm.getFormDefinitionId());
    BtxActionErrors errors = 
      FormInstanceService.SINGLETON.validateCreateFormInstance(form);                                               
    if (!errors.empty()) {
      // Convert action errors to messages.
      StrutsProperties props = StrutsProperties.getInstance();
      Iterator i = errors.get();
      while (i.hasNext()) {
        BtxActionError error = (BtxActionError) i.next();
        String msg = props.getProperty(error.getKey());
        if (msg.startsWith("<li>")) {
          msg = msg.substring(4);
        }
        if (msg.endsWith("</li>")) {
          msg = msg.substring(0, msg.length() - 5);
        }
        String[] values = error.getValues();
        for (int j = 0; j < values.length; j++) {
          if (values[j] == null) {
            break;
          }
          String placeholder = "{" + String.valueOf(j) + "}";
          msg = ApiFunctions.replace(msg, placeholder, values[j]);
        }
        helper.getMessageHelper().addMessage(msg);
      }
      isValid = false;
    }      
  }   
  
  //validate the label printing information.
  if (helper.isPrintDonorLabel()) {
    //since all of the logic for validating the printing of donor labels is contained
    //in the label service, call out to the service and convert any errors returned
    //into BtxActionErrors.
    LabelService labelService = new LabelService();
    BigrValidationErrors bve = labelService.validateCommonLabelPrintingData(securityInfo.getAccount(), 
        helper.getLabelCount() , helper.getTemplateDefinitionName(), 
        helper.getPrinterName(), Constants.LABEL_PRINTING_OBJECT_TYPE_DONOR);
    BigrValidationError[] errors = bve.getErrors();
    for (int i = 0; i < errors.length; i++) {
      helper.getMessageHelper().addMessage(errors[i].getMessage());
      isValid = false;
    }
  }

  if (!isValid) {
    helper.getMessageHelper().setError(true);
    helper.findFormDefinitions(securityInfo);
    helper.getDataBean().setFormInstance(form);
    //populate the label printing data so it is available on the page to which we're returning
    String accountId = securityInfo.getAccount();
    try {
      Map labelPrintingData = LabelPrintingConfiguration.getLabelTemplateDefinitionsAndPrintersForObjectType(securityInfo.getAccount(), Constants.LABEL_PRINTING_OBJECT_TYPE_DONOR);
      helper.setLabelPrintingData(labelPrintingData);
    }
    catch (IllegalStateException ise) {
      //log the error so the problems are documented
      ApiLogger.log("Label Printing Configuration refresh failed.", ise);
      helper.setLabelPrintingData(new HashMap());
      StrutsProperties props = StrutsProperties.getInstance();
      String msg = props.getProperty("orm.error.label.invalidPrintingConfiguration");
      if (msg.startsWith("<li>")) {
        msg = msg.substring(4);
      }
      if (msg.endsWith("</li>")) {
        msg = msg.substring(0, msg.length() - 5);
      }
      helper.getMessageHelper().addMessage(msg);
    }
    (new DonorProfilePrepare(request, response, servletCtx)).invoke();
    return;    
  }
  
	try {
		DonorData donorData = helper.getDataBean();
    donorData.setRegistrationFormId(regForm.getFormDefinitionId());
    donorData.setFormInstance(form);
    donorData.setRegistrationForm(regForm);
		if (helper.isNew()) {
			donorData.setCreateUser(user);
      if (ApiFunctions.isEmpty(donorData.getArdaisAccountKey())) {
        donorData.setArdaisAccountKey(WebUtils.getSecurityInfo(request).getAccount());
      }
      SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
      DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = ddcDonorHome.create();
      returnedDonorData = donorOperation.createDonorProfile(donorData, secInfo);      
		}
		else {
			donorData.setLastUpdateUser(user);
      SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
      DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = ddcDonorHome.create();
      returnedDonorData = donorOperation.updateDonorProfile(donorData, secInfo);
		}
	}
	catch (Exception e) {
		throw new ApiException(e);
	}
  
  StringBuffer donorInfoBuffer = new StringBuffer(50);
  donorInfoBuffer.append(returnedDonorData.getArdaisId());
  if (!ApiFunctions.isEmpty(returnedDonorData.getCustomerId())) {
    donorInfoBuffer.append(" (");
    donorInfoBuffer.append(returnedDonorData.getCustomerId());
    donorInfoBuffer.append(")");
  }
  String donorInfo = donorInfoBuffer.toString();
  
  //if a label is to be printed for the donor, print it now
  List messages = new ArrayList();
  if (helper.isPrintDonorLabel()) {
    LabelService labelService = new LabelService();
    try {
      labelService.printDonorLabels(securityInfo, new String[] {returnedDonorData.getArdaisId()},
          helper.getLabelCount(), helper.getTemplateDefinitionName(), helper.getPrinterName());
      StrutsProperties props = StrutsProperties.getInstance();
      String msg = props.getProperty("orm.message.label.labelPrinted");
      if (msg.startsWith("<li>")) {
        msg = msg.substring(4);
      }
      if (msg.endsWith("</li>")) {
        msg = msg.substring(0, msg.length() - 5);
      }
      msg = ApiFunctions.replace(msg, "{0}", "donor");
      msg = ApiFunctions.replace(msg, "{1}", Escaper.htmlEscapeAndPreserveWhitespace(donorInfo));
      messages.add(msg);
    }
    catch (BigrValidationException bve) {
      BigrValidationError[] errors = bve.getErrors().getErrors();
      for (int i = 0; i < errors.length; i++) {
        messages.add("A label for the donor was not printed. " + errors[i].getMessage());
      }
    }
  }

  //if we're dealing with an imported donor situation, figure out what to do next based
  //on whether we just created a donor or just modified one.
  if ("Y".equalsIgnoreCase(helper.getImportedYN())) {
    //if we just created a new imported donor, determine if we need to return to the create 
    //donor page to let the user create the next donor or if we should proceed to the donor
    //summary page for the newly created donor so the user can create kc forms.  
    //Either way add a message to let them know the Ardais id 
    //for the donor they just created.  Also store the id of the new donor in the session
    //as a convenience for creating the path report, etc.
    if (helper.isNew()) {
      StringBuffer successMessage = new StringBuffer(50);
      successMessage.append("Donor ");
      successMessage.append(Escaper.htmlEscapeAndPreserveWhitespace(donorInfo));
      successMessage.append(" successfully created.");
      messages.add(0, successMessage.toString());
      request.setAttribute("msg", messages);
      //because we're creating a new donor, pass false as the last parameter to ensure that any
      //saved case and sample ids are cleared out.  Any such ids will be cleared out by virtue of
      //the fact we are passing in a donor id that is guaranteed to be different from any that's 
      //currently saved in the session, but passing "false" provides additional protection.
      PdcUtils.storeLastUsedDonorCaseAndSample(request, returnedDonorData.getArdaisId(), 
          returnedDonorData.getCustomerId(), null, null, null, null, false);
      if ("true".equalsIgnoreCase(request.getParameter("createForm"))) {
        (new DonorProfileSummaryPrepare(request, response, servletCtx)).invoke();
      }
      else {
        forward("/dataImport/createDonorStart.do");
      }
      return;
    }
    //if we just modified an imported donor, return to the modify donor page to let
    //the user perform any additional modifications.  Add a message to let them know the donor 
    //was successfully modified.  Also store the id of the donor in the session
    //as a convenience for creating the path report, etc.
    else {
      helper = new DonorHelper(returnedDonorData);
      StringBuffer successMessage = new StringBuffer(50);
      successMessage.append("Donor ");
      successMessage.append(Escaper.htmlEscapeAndPreserveWhitespace(donorInfo));
      successMessage.append(" successfully modified.");
      helper.getMessageHelper().addMessage(successMessage.toString());
      //add any messages from label printing
      Iterator iterator = messages.iterator();
      while (iterator.hasNext()) {
        helper.getMessageHelper().addMessage((String)iterator.next());
      }
      helper.setupKcFormContext(request, regForm, helper.getDataBean().getFormInstance());        
      
      //populate the label printing information.  If it is invalid then return an error
      //letting the user know that label printing is unavailable.
      String accountId = securityInfo.getAccount();
      try {
        Map labelPrintingData = LabelPrintingConfiguration.getLabelTemplateDefinitionsAndPrintersForObjectType(securityInfo.getAccount(), Constants.LABEL_PRINTING_OBJECT_TYPE_DONOR);
        helper.setLabelPrintingData(labelPrintingData);
      }
      catch (IllegalStateException ise) {
        //log the error so the problems are documented
        ApiLogger.log("Label Printing Configuration refresh failed.", ise);
        helper.setLabelPrintingData(new HashMap());
        StrutsProperties props = StrutsProperties.getInstance();
        String msg = props.getProperty("orm.error.label.invalidPrintingConfiguration");
        if (msg.startsWith("<li>")) {
          msg = msg.substring(4);
        }
        if (msg.endsWith("</li>")) {
          msg = msg.substring(0, msg.length() - 5);
        }
        helper.getMessageHelper().addMessage(msg);
      }
      request.setAttribute("helper", helper);

      //we pass "true" for the last parameter because if the user is modifying the same
      //donor as the one currently saved in the session we don't want to erase any saved 
      //case or sample id.  If the user is modifying a different donor then any case and 
      //sample information will be erased regardless of the value of the last parameter.
      PdcUtils.storeLastUsedDonorCaseAndSample(request, returnedDonorData.getArdaisId(), 
          returnedDonorData.getCustomerId(), null, null, null, null, true);
      forward("/hiddenJsps/ddc/donor/DonorProfile.jsp");
      return;
    }
  }

	DonorData donorData = new DonorData();
	donorData.setArdaisId(helper.getArdaisId());
	DonorHelper donorHelper = new DonorHelper(donorData);
	request.setAttribute("helper", donorHelper);
	(new DonorProfileSummaryPrepare(request, response, servletCtx)).invoke();	
}
  public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
}
