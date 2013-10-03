package com.ardais.bigr.library.web.action;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;



public class CreateTransferRequestAction extends BigrAction  {

    /**
     * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
     */
    protected ActionForward doPerform(
        BigrActionMapping mapping,
        BigrActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {


        // stuff we set up from the helper in the synchronized block.
        IdList requestedSamples;        
        
        ResultsHelper helper = ResultsHelper.get(ResultsHelper.TX_TYPE_SAMP_REQUEST, request);
        
        synchronized (helper) {
          String[] ids = helper.getSelectedSamples().getSampleIds();
          requestedSamples = new IdList(Arrays.asList(ids));
          
          ActionErrors errs = new ActionErrors();
          if (requestedSamples.getList().isEmpty()) {
            errs.add("nosampleselected", new ActionError("iltds.error.rs.noitemsselected", "request"));
            saveErrors(request, errs);
          }
          
          //make sure the user has not chosen a combination of BMS and non-BMS items
          ArrayList tissueIds = new ArrayList(ids.length);
          for (int i=0; i< ids.length; i++) {
            tissueIds.add(ids[i]);
          }
          ArrayList bmsSamples = IltdsUtils.getBMSSamplesFromList(IltdsUtils.BMS, tissueIds);
          ArrayList nonBmsSamples = IltdsUtils.getBMSSamplesFromList(IltdsUtils.NONBMS, tissueIds);
          if (bmsSamples.size() > 0 && nonBmsSamples.size() > 0) {
            errs.add(null, new ActionError("iltds.error.rs.cannotmixBMSwithnonBMS", "request"));
            saveErrors(request, errs);
          }

          if (ApiFunctions.isEmpty(request.getParameter("deliverTo"))) {
              errs.add(null, new ActionError("iltds.error.rs.nodeliverto"));
              saveErrors(request, errs);
          }
          
          if (errs.size() > 0) {
              return mapping.findForward("fail");
          }
          
          //  this is from CreatePicklistAction.java
          //  commenting out for now...TODO -- this should happen AFTER the BTX transaction 
          // helper.clearSelectedIds();
 
          //  in CreatePicklistAction.java, update statuses by calling PathologyOperationBean directly
          //  this will not happen here...all of this is wrapped in the BTX code...          
                   
          //  this is from CreatePicklistAction.java
          //  commenting out for now...TODO -- this should happen AFTER the BTX transaction                
          // now that we succeeded, clear the picklist
          //helper.getSelectedSamples().removeIds(helper.getSelectedSamples().getSampleIds());
       

          // ensure all the necessary fields that will ultimately be used in the BTX transaction are passed along
          // note that there is still another page to be displayed and input collected before actually committing
          // the transaction...thus, no changes should be made to the REQUEST_SAMPLE hold list, nor to statuses,
          // nor entries in the request table...
          
          //Collect the information needed for the real name of <created by>
          //set the information to the request
          String user = (String) request.getSession(false).getAttribute("user");
          String account = (String) request.getSession(false).getAttribute("account");
          ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
          ListGenerator list = home.create();
          String createdByRealName = list.getUserRealName(user, account);
          request.setAttribute("createdBy", createdByRealName);
          request.setAttribute("user", user);
  
          // this is from CreatePicklistAction.java
          //  commenting out for now...we do not need to pass  
          //  all this along b/c we are not going to display this
          //  info...see below on what we need to pass.
          // contains samples and all the pertinent info for 
          // displayin on the next page, the pick list page
          // note that this is passed in a big ugly vector...
          //request.setAttribute("samples", sampleLocations);
          
          // we need to pass along:
          //  - selected tissue ids (this is all we are supporting currently) 
          //  - destination id
          // this will be BTX massaged after next page...
          request.setAttribute("tissue_samples", requestedSamples);
          request.setAttribute("deliverTo",request.getParameter("deliverTo"));
 
          return (mapping.findForward("success"));
        } // end synchronized
    }
}
