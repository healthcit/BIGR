package com.ardais.bigr.dataImport.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.dataImport.web.form.SampleForm;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class CreateFormForCreatedSampleAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {
    
    //If a user chooses to create a form for a newly created sample, we had been
    //forwarding directly to the url below in the struts-config.xml file.  The problem
    //with doing so is that if the user specified both a sample id and sample alias
    //when creating the sample, the validation method invoked by the url below was
    //complaining since it expected either a sample id or a sample alias, but not both.
    //To get around this problem, I created this action to build the appropriate path
    //and redirect to the url, so that the sample alias is not passed along.  See 
    //MR8906 for more details.
    SampleForm myForm = (SampleForm)form;
    String myTag = ApiFunctions.safeString(mapping.getTag());
    String path = myTag + myForm.getSampleData().getSampleId();
    ActionForward myActionForward = new ActionForward(path, true);
    return myActionForward;
  }

}
