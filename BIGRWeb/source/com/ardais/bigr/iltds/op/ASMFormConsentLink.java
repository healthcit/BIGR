package com.ardais.bigr.iltds.op;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.beans.ASMOperation;
import com.ardais.bigr.iltds.beans.ASMOperationHome;
import com.ardais.bigr.iltds.beans.AsmformAccessBean;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.util.EjbHomes;
/**
 * Insert the type's description here.
 * Creation date: (1/16/2001 3:10:08 PM)
 * @author: Jake Thompson
 */
public class ASMFormConsentLink extends com.ardais.bigr.iltds.op.StandardOperation {
  /**
   * ASMLink constructor comment.
   * @param req javax.servlet.http.HttpServletRequest
   * @param res javax.servlet.http.HttpServletResponse
   * @param ctx javax.servlet.ServletContext
   */
  public ASMFormConsentLink(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }
  /**
   * Insert the method's description here.
   * Creation date: (1/22/01 3:31:14 PM)
   * @return boolean
   * @param myASM java.lang.String
   */
  public boolean asmIDExists(String myASM) {

    AsmformAccessBean asmForm = new AsmformAccessBean();
    try {

      Enumeration enum1 = asmForm.findByASMFormID(myASM);
      return enum1.hasMoreElements();
    }
    catch (Exception e) {
      return false;
    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/23/01 3:28:57 PM)
   * @return boolean
   * @param asmID java.lang.String
   */
  public boolean asmInRange(String asmID) {

    //for this phase, asm forms must be under 100 million
    Integer asmNumber = new Integer(asmID.substring(3));
    int asm = asmNumber.intValue();
    if (asm > FormLogic.ASM_RANGE_UPPER_MAX || asm < FormLogic.ASM_RANGE_LOWER_MIN) {
      return false;
    }
    else if (FormLogic.ASM_RANGE_LOWER_MAX < asm && asm < FormLogic.ASM_RANGE_UPPER_MIN) {
      return false;
    }
    else
      return true;
  }
  /**
   * Insert the method's description here.
   * Creation date: (10/29/2001 11:04:01 AM)
   * @return boolean
   * @param asmFormID java.lang.String
   * @param consentID java.lang.String
   */
  public boolean asmInSameRange(String asmFormID, String consentID) {
    try {
      ASMOperationHome home = (ASMOperationHome) EjbHomes.getHome(ASMOperationHome.class);
      ASMOperation asmOp = home.create();
      return asmOp.asmFormExistInRange(asmFormID, consentID);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return false; // unreached, make compiler happy
    }
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
   * Creation date: (1/22/01 3:27:59 PM)
   * @param myConsentID java.lang.String
   */
  public boolean consentIDExists(String consentID) {

    ConsentAccessBean myConsent;

    try {

      myConsent = new ConsentAccessBean();
      Enumeration myEnum = myConsent.findByConsentID(consentID);
      if (!myEnum.hasMoreElements()) {
        return false;
      }
      else {
        return true;
      }
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
      return false;
    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (1/16/2001 3:10:08 PM)
   */
  public void invoke() throws Exception {
    String myASMID = request.getParameter("asmID_1");
    String myConsentID = request.getParameter("consentID_1");

    //check if asm id was entered and not null
    if (myASMID == null || myASMID.equals("")) {
      retry("Please Enter a valid ASM ID");
      return;
      //check if asm id's match	
    }
    else if (!myASMID.equals((String) request.getParameter("asmID_2"))) {
      retry("ASM ID's do not match.");
      return;
      //check to see if it starts with ASM, has the right length, etc. 
    }
    else if ( (ValidateIds.validateId(myASMID, ValidateIds.TYPESET_ASM_IMPORTED, false) != null) ) {
      retry("Imported ASM IDs cannot be linked to consents.");
      return;
    }    
    else if ( (ValidateIds.validateId(myASMID, ValidateIds.TYPESET_ASM_WITHOUT_IMPORTED, false) == null) ) {
      retry("Please Enter a valid ASM ID.");
      return;
    //check to see if the asm is already in the system.
    }
    else if (asmIDExists(myASMID)) {
      retry("ASM ID already Exists.");
      return;
    //check to see if is falls between 0 and 100 million
    }
    else if (!asmInRange(myASMID)) {
      retry("ASM ID out of Range.");
      return;
    //check to see if there is already an ASM in the same range
    }
    else if (asmInSameRange(myASMID, myConsentID)) {
      retry("An ASM Form already exists in that range.  Please select an ASM Form from a different range.");
    return;
    //check to see if the ASM is a conversion ASM
    }
    else if (FormLogic.isConversion(myASMID)) {
      retry("This is a conversion range ASM and cannot be linked to a case.");
      return;
    //check to see if consent ID's match
    }
    else if (!myConsentID.equals((String) request.getParameter("consentID_2"))) {
      retry("Case ID's do not match.");
      return;
    //check to see if it is a valid linked or unlinked case
    }
    else if ( (ValidateIds.validateId(myConsentID, ValidateIds.TYPESET_CASE_LINKED, false) == null) &&
    (ValidateIds.validateId(myConsentID, ValidateIds.TYPESET_CASE_UNLINKED, false) == null)  ) {      
      retry("Please Enter a valid Case ID.");
      return;
      //check to see if the case exists
    }
    else if (!consentIDExists(myConsentID)) {
      retry("Case ID does not Exist.");
      return;
      //check to see if the user belongs to the institution as the consent
      // or if the user is an ardais user.
    }
    else if (!checkGeoLocation(myConsentID)) {
      retry("Case ID belongs to a different Institution.");
      return;
    }
    else {

      request.setAttribute("myError", "Confirm");
      //check to see if the case is pulled or revoked, if it is
      //display a message on the next page
      if (IltdsUtils.consentPulled(myConsentID)) {
        request.setAttribute("pulled", "Case has been pulled");
      }
      if (IltdsUtils.consentRevoked(myConsentID)) {
        if (IltdsUtils.consentPulled(myConsentID)) {
          request.setAttribute("pulled", "Case has been pulled and revoked.");
        }
        else {
          request.setAttribute("pulled", "Consent has been revoked");
        }
      }

      servletCtx.getRequestDispatcher(
        "/hiddenJsps/iltds/asm/asmFormConsentLinkConfirm.jsp").forward(
        request,
        response);
      return;
    }
  }

  /**
   * Insert the method's description here.
   * Creation date: (1/22/2001 11:15:39 AM)
   * @param myError java.lang.String
   */
  private void retry(String myError) throws Exception {
    request.setAttribute("myError", myError);
    servletCtx.getRequestDispatcher("/hiddenJsps/iltds/asm/asmFormConsentLink.jsp").forward(
      request,
      response);
  }
}