package com.ardais.bigr.kc.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.kc.form.BigrFormInstance;
import com.ardais.bigr.kc.form.BtxDetailsKcFormInstance;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.ardais.bigr.web.form.PopulatesRequestFromBtxDetails;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.FormContextStack;
import com.gulfstreambio.kc.web.support.KcUiContext;
import com.gulfstreambio.kc.web.support.WebUtils;

public class FormInstanceForm extends BigrActionForm implements PopulatesRequestFromBtxDetails {

  public static String REQUEST_ATTRIBUTE_BIGR_FORM_DEFINITION = "BigrFormDefinition";
  private String _formInstanceId;
  private String _formDefinitionId;
  private String _form;
  private String _domainObjectId;
  private String _domainObjectType;
  
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _formInstanceId = null;
    _formDefinitionId = null;
    _form = null;
  }

  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void describeIntoBtxDetails(BTXDetails btxDetails0, BigrActionMapping mapping,
                                     HttpServletRequest request) {

    FormInstance form = null;

    // If a JSON form instance was specified, then convert it to a FormInstance, otherwise
    // create a new FormInstance and populate it with ids submitted in the request.
    String jsonForm = getForm(); 
    if (jsonForm != null) {
      form = WebUtils.convertToFormInstance(jsonForm);      
    }
    else {
      form = new FormInstance();
      BigrBeanUtilsBean.SINGLETON.copyProperties(form, this);
    }
    
    // Populate the BTX details with the form instance.
    BtxDetailsKcFormInstance formBtxDetails = (BtxDetailsKcFormInstance) btxDetails0;
    formBtxDetails.setFormInstance(new BigrFormInstance(form));
  }

  public void populateRequestFromBtxDetails(BTXDetails btxDetails, BigrActionMapping mapping,
                                            HttpServletRequest request) {
    super.populateRequestFromBtxDetails(btxDetails, mapping, request);

    BtxDetailsKcFormInstance formBtxDetails = (BtxDetailsKcFormInstance) btxDetails;
    
    BigrFormInstance form = formBtxDetails.getFormInstance();
    String domainObjectId = form.getDomainObjectId();
    String formInstanceId = (form == null) ? "" : form.getFormInstanceId();

    BigrFormDefinition formDef = formBtxDetails.getFormDefinition();
    if ((formDef == null) && (form != null)) {
      formDef = form.getFormDefinition(); 
    }
    String objectType = null;
    if (formDef != null) {
      objectType = formDef.getObjectType();
      objectType = objectType.substring(0,1).toUpperCase() + objectType.substring(1).toLowerCase();
    }

    FormContextStack stack = FormContextStack.getFormContextStack(request);
    FormContext formContext = stack.peek();
    if (formDef != null) {
      formContext.setDataFormDefinition((DataFormDefinition) formDef.getKcFormDefinition());      
    }
    formContext.setFormInstance(form.getKcFormInstance());
    formContext.setDomainObjectId(domainObjectId);
    stack.push(formContext);
      
    KcUiContext kcUiContext = KcUiContext.getKcUiContext(request);
    String contextPath = request.getContextPath();
    kcUiContext.setAdePopupUrl(contextPath + "/kc/ade/popup.do");

    String baseUrl = null;
    if ("summary".equals(mapping.getTag())) {
      baseUrl = contextPath + "/kc/form/summary.do";
    }
    else {
      if (ApiFunctions.isEmpty(formInstanceId)) {
        baseUrl = contextPath + "/kc/form/getFormInstanceCreatePage.do";      
      }
      else {
        baseUrl = contextPath + "/kc/form/getFormInstanceUpdatePage.do";
      }      
    }
    kcUiContext.setPageLinkBaseUrl(baseUrl);

    // Set the flag in the context that dictates whether we will use AJAX to fetch the pages
    // based on whether the transaction completed successfully.  We prefer to use AJAX to minimize
    // page generation and loading, but if there is an error we need to generated all of the pages
    // for all of the tabs.  If we do not generate all of the pages in this response, the values
    // will be lost since they are only present in this request and are not saved elsewhere.
    // 1/27/09: Turns out there is a problem with this.  If there is an error and the user
    // only visited one of the tabs initially, then the data for the other tabs will not be
    // loaded.  Therefore we can't really use AJAX here.  I'll leave this here though in case
    // we determine a clever way to get around the problem.
    //kcUiContext.setUseAjax(btxDetails.isTransactionCompleted());
    kcUiContext.setUseAjax(false);

    //put the BigrFormDefinition into the request so FormInstanceCreate.jsp can make use
    //of it
    request.setAttribute(REQUEST_ATTRIBUTE_BIGR_FORM_DEFINITION, formDef);
  }
  
  public String getFormDefinitionId() {
    return _formDefinitionId;
  }

  public void setFormDefinitionId(String string) {
    _formDefinitionId = string;
  }

  public String getFormInstanceId() {
    return _formInstanceId;
  }

  public void setFormInstanceId(String string) {
    _formInstanceId = string;
  }
  
  public String getForm() {
    return _form;
  }

  public void setForm(String form) {
    _form = form;
  }

  public String getDomainObjectId() {
    return _domainObjectId;
  }

  public void setDomainObjectId(String string) {
    _domainObjectId = string;
  }

  public String getDomainObjectType() {
    return _domainObjectType;
  }

  public void setDomainObjectType(String string) {
    _domainObjectType = string;
  }
}
