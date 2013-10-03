package com.ardais.bigr.iltds.op;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.iltds.beans.AsmAccessBean;
import com.ardais.bigr.iltds.beans.AsmKey;
import com.ardais.bigr.iltds.beans.AsmformAccessBean;
import com.ardais.bigr.iltds.beans.AsmformKey;
import com.ardais.bigr.iltds.beans.GeolocationKey;
import com.ardais.bigr.iltds.beans.SamplestatusAccessBean;
import com.ardais.bigr.iltds.databeans.AsmFormData;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.ArtsConstants;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;
/**
 * Insert the type's description here.
 * Creation date: (1/16/2001 3:32:21 PM)
 * @author: Jeremy Gilbert
 */
public class ASMFormLookup extends com.ardais.bigr.iltds.op.StandardOperation {
  /**
   * ASMLookup constructor comment.
   * @param req javax.servlet.http.HttpServletRequest
   * @param res javax.servlet.http.HttpServletResponse
   * @param ctx javax.servlet.ServletContext
   */
  public ASMFormLookup(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }
  /**
   * Insert the method's description here.
   * Creation date: (6/20/2001 12:26:58 PM)
   * @return boolean
   * @param samples java.util.Vector
   * @param module int
   * @exception com.ardais.bigr.api.ApiException The exception description.
   */
  private boolean asmPresentCheck(Vector frozen, Vector paraffin, int module) {
    SamplestatusAccessBean status = new SamplestatusAccessBean();

    try {
      //lookup paraffin status
      Enumeration statusEnum =
        status.findBySampleIDStatus((String) paraffin.get(module), FormLogic.SMPL_ASMPRESENT);
      if (statusEnum.hasMoreElements())
        return true;

      for (int i = (module * 6); i < ((module * 6) + 6); i++) {
        statusEnum = status.findBySampleIDStatus((String) frozen.get(i), FormLogic.SMPL_ASMPRESENT);
        if (statusEnum.hasMoreElements())
          return true;
      }
    }
    catch (RemoteException re) {
    }
    catch (FinderException fe) {
    }
    catch (NamingException ne) {
    }

    return false;

  }
  /**
   * Insert the method's description here.
   * Creation date: (4/10/01 12:06:10 PM)
   * @return boolean
   * @param consentID java.lang.String
   */
  public boolean checkGeoLocation(String consentID) {
    String user = (String) request.getSession(false).getAttribute("user");
    return IltdsUtils.checkGeoLocation(consentID, user);
  }
  /**
   * Insert the method's description here.
   * Creation date: (10/18/2001 4:02:37 PM)
   * @return java.util.Vector
   */
  public Vector getLabels(AsmformAccessBean asmForm, String asmFormId) throws Exception {
    Vector labels = new Vector();
    AsmAccessBean asm = null;
    //Enumeration asmEnum =
    //	asm.findAsmByAsmform(((AsmformKey) asmForm.__getKey()).asm_form_id);

    Vector ids = FormLogic.genASMEntryIDs(asmFormId);

    Vector frozen = FormLogic.genFrozenIDs(asmFormId);
    Vector paraffin = FormLogic.genParaffinIDs(asmFormId);

    for (int i = 0; i < ids.size(); i++) {
      //for the 3 asm modules, check to see if the minimum data has been entered
      //if it has, set the button on the next screen to "Edit" instead of "Enter"

      try {
        asm = new AsmAccessBean(new AsmKey((String) ids.get(i)));

        if (asm.getOrgan_site_concept_id() != null) {
          labels.add("Edit");
        }
        else if (asmPresentCheck(frozen, paraffin, i)) {
          labels.add("Edit");
        }
        else {
          Enumeration blah = asm.getSample();
          if (blah.hasMoreElements()) {
            labels.add("Edit");
          }
          else
            labels.add("Enter");
        }
      }
      catch (FinderException ex) {
        // This conversion ASM module has no samples, and cannot be edited.

        labels.add("Enter");
      }

    }
    return labels;
  }
  /**
   * Insert the method's description here.
   * Creation date: (10/18/2001 4:02:37 PM)
   * @return java.util.Vector
   */
  public String getPulled(String consentId) throws Exception {
    if (IltdsUtils.consentPulled(consentId)) {
      return "Case has been pulled";
    }
    if (IltdsUtils.consentRevoked(consentId)) {
      if (IltdsUtils.consentPulled(consentId)) {
        return "Case has been pulled and revoked.";
      }
      else {
        return "Consent has been revoked";
      }
    }
    return null;
  }
  /**
   * Insert the method's description here.
   * Creation date: (10/18/2001 4:02:37 PM)
   * @return java.util.Vector
   */
  public Vector getStaffNames() throws Exception {
    String user = (String) request.getSession(false).getAttribute("user");
    String account = (String) request.getSession(false).getAttribute("account");
    ArdaisstaffAccessBean myStaff = new ArdaisstaffAccessBean();
    AccessBeanEnumeration myStaffEnum =
      (AccessBeanEnumeration) myStaff.findLocByUserProf(user, account);
    myStaff = (ArdaisstaffAccessBean) myStaffEnum.nextElement();
    GeolocationKey key = myStaff.getGeolocationKey();
    AccessBeanEnumeration staffList =
      (AccessBeanEnumeration) myStaff.findArdaisstaffByGeolocation(key);

    Vector staffNames = new Vector();
    while (staffList.hasMoreElements()) {
      myStaff = (ArdaisstaffAccessBean) staffList.nextElement();
      String firstName = myStaff.getArdais_staff_fname();
      if (firstName == null)
        firstName = "";
      String lastName = myStaff.getArdais_staff_lname();
      if (lastName == null)
        lastName = "";
      staffNames.add(firstName + " " + lastName);
      staffNames.add(((ArdaisstaffKey) myStaff.__getKey()).ardais_staff_id);
    }

    return staffNames;
  }
  /**
   * Insert the method's description here.
   * Creation date: (1/16/2001 3:32:21 PM)
   */
  public void invoke() throws IOException, Exception {
    AsmformAccessBean asmForm = new AsmformAccessBean();
    String myASMFormID = request.getParameter("asmID_1");

    try {
      //check to see if a valid asm id was entered.
      // MR 7777 using validateId...
      if ( (ValidateIds.validateId(myASMFormID, ValidateIds.TYPESET_ASM, false) == null) ) {           
        retry("Please enter a valid ASM Form ID.");
        return;
      }
      else if ( (ValidateIds.validateId(myASMFormID, ValidateIds.TYPESET_ASM_IMPORTED, false) != null) ) {
        retry("Cannot use imported ASM IDs.");
        return;
      }      
      try {
        //generate the asm entries from the asm form id
        //i.e. ASM0000000001 would yield ASM0000000001_A, ASM0000000001_B, ASM0000000001_C
        Vector newASMEntries = new Vector(FormLogic.genASMEntryIDs(myASMFormID));

        //check to see if the asm is in the system.
        AccessBeanEnumeration asmFormEnum =
          (AccessBeanEnumeration) asmForm.findByASMFormID(myASMFormID);
        if (!asmFormEnum.hasMoreElements()) {
          retry("ASM Form not found in system.");
          return;
        }
        asmForm = (AsmformAccessBean) asmFormEnum.nextElement();

        Vector labels = getLabels(asmForm, myASMFormID);
        for (int i = 0; i < labels.size(); i++) {
          request.setAttribute("label" + (i + 1), labels.get(i));
        }

        Calendar removeDate = null;
        removeDate = Calendar.getInstance();
        Calendar grossDate = null;
        grossDate = Calendar.getInstance();

        request.setAttribute("staffNames", getStaffNames());

        // MR 4435 pass AsmFormData bean
        AsmFormData asmFormData = new AsmFormData();

        // set the value of the procedure if it already in the database	
        // MR 4865 -- want to allow the creation of ASM Module Info
        //	IFF the ASM Form header has been saved.  Use the existence
        //	 surgical specimen id (procedure) to determine this
        String surgSpec = asmForm.getSurgical_specimen_id();
        if (surgSpec == null) {
          asmFormData.setProcedure("");
          request.setAttribute("formSaved", "N");
        }
        else {
          asmFormData.setProcedure(asmForm.getSurgical_specimen_id());
          request.setAttribute("formSaved", "Y");
        }

        //set the value of other procedure
        String surgSpecOther = asmForm.getSurgical_specimen_id_other();
        if (surgSpecOther != null) {
          asmFormData.setOtherProcedure(surgSpecOther);
        }
        // pass in as a data bean
        request.setAttribute("asmFormData", asmFormData);

        //prepopulate the grossing date field
        if (asmForm.getGrossing_date() == null) {
        }
        else {
          Date gross = new Date(asmForm.getGrossing_date().getTime());
          grossDate.setTime(gross);

          if (grossDate.get(Calendar.HOUR) == 0) {
            request.setAttribute("grossHour", "12");
          }
          else {
            request.setAttribute("grossHour", grossDate.get(Calendar.HOUR) + "");
          }
          request.setAttribute("grossMinute", grossDate.get(Calendar.MINUTE) + "");
          request.setAttribute("grossAmpm", grossDate.get(Calendar.AM_PM) + "");
        }
        //prepopulate the removal date field
        if (asmForm.getRemoval_date() == null) {
        }
        else {
          Date remove = new Date(asmForm.getRemoval_date().getTime());
          removeDate.setTime(remove);
          if (removeDate.get(Calendar.HOUR) == 0) {
            request.setAttribute("removeHour", "12");
          }
          else {
            request.setAttribute("removeHour", removeDate.get(Calendar.HOUR) + "");
          }
          request.setAttribute("removeMinute", removeDate.get(Calendar.MINUTE) + "");
          request.setAttribute("removeAmpm", removeDate.get(Calendar.AM_PM) + "");
        }
        //check to see if the consent belongs to the user's institution or if the user
        //is an ardais user
        if (!checkGeoLocation(asmForm.getConsentKey().consent_id)) {
          retry("Case ID belongs to a different Institution.");
          return;
        }

        request.setAttribute("asmFormID", ((AsmformKey) asmForm.__getKey()).asm_form_id);
        request.setAttribute("consentID", asmForm.getConsentKey().consent_id);
        // MR 6819 we want to process differently for unlinked versus linked cases...
        if (asmForm.getConsentKey().consent_id.startsWith(ValidateIds.PREFIX_CASE_LINKED)) {
          request.setAttribute("linked", "Y");
          // get the stored date, if any...
          if (asmForm.getProcedure_date() == null) {
            request.setAttribute("procedure_date_yn", "N");
          }
          else {
            request.setAttribute("procedure_date_yn", "Y");            
            String the_date = asmForm.getProcedure_date().toString();
            // dates are always stored in the format YYYY-MM-DD plus time...
            request.setAttribute("procedureDay", the_date.substring(8,10));
            request.setAttribute("procedureMonth", the_date.substring(5,7));
            request.setAttribute("procedureYear", the_date.substring(0,4));
          }

          }
        else {  // for unlinked, there will be no values entered
          request.setAttribute("linked", "N");
        }
        request.setAttribute("module1", newASMEntries.get(0));
        request.setAttribute("module2", newASMEntries.get(1));
        request.setAttribute("module3", newASMEntries.get(2));

        //prepopulate the user
        if (asmForm.getArdaisstaffKey() == null) {
        }
        else {
          request.setAttribute("employee", asmForm.getArdaisstaffKey().ardais_staff_id);
        }

        // get the CV list for accuracy of surgical removal time
        LegalValueSet timeRemovals = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_SURGICAL_REMOVAL_TIME_ACCURACY);
        request.setAttribute("timeRemovals", timeRemovals);

        //set the value of the accuracy of surgical removal time if it already in the database	
        String acc_surg_remove_time = asmForm.getAcc_surgical_removal_time();
        if (acc_surg_remove_time == null) {
          request.setAttribute("accSurgRemoval", "");
        }
        else {
          // determine the index position
          for (int i = 0; i < timeRemovals.getCount(); i++) {
            if (timeRemovals.getValue(i).equals(acc_surg_remove_time))
              request.setAttribute("accSurgRemoval", String.valueOf(i));
          }
        }

      }
      catch (ObjectNotFoundException e) {
        retry("ASM Form not found in system");
        return;
      }
      String myConsentID = asmForm.getConsentKey().consent_id;
      //check to see if the consent is pulled or revoked.  If it is, set a flag in the
      //next page
      String pulled = getPulled(myConsentID);
      if (pulled != null) {
        request.setAttribute("pulled", pulled);
      }

      servletCtx.getRequestDispatcher("/hiddenJsps/iltds/asm/asmFormInfo.jsp").forward(
        request,
        response);
    }
    catch (Exception e) {
      e.printStackTrace();
      retry("Please enter a valid ASM Form ID.");

    }
  }

  /**
   * Method String.
   * @param i
   * @return Object
   */
  private Object String(int i) {
    return null;
  }

  /**
   * Insert the method's description here.
   * Creation date: (1/22/2001 3:49:33 PM)
   */
  public void retry(String myError) {
    try {
      request.setAttribute("myError", myError);
      servletCtx.getRequestDispatcher("/hiddenJsps/iltds/asm/asmFormLookup.jsp").forward(
        request,
        response);
    }
    catch (Exception e) {
      e.printStackTrace();
      ReportError err = new ReportError(request, response, servletCtx);
      err.setFromOp(this.getClass().getName());
      err.setErrorMessage(e.toString());
      try {
        err.invoke();
      }
      catch (Exception _axxx) {
      }
      return;
    }

  }
}