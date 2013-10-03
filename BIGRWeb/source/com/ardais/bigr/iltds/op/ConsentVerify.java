package com.ardais.bigr.iltds.op;

import java.util.Calendar;
import java.util.Enumeration;

import javax.ejb.ObjectNotFoundException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.ConsentKey;
import com.ardais.bigr.iltds.databeans.PolicyData;
import com.ardais.bigr.iltds.helpers.IltdsUtils;

/**
 * Checks all Consent Linked Verification Information.
 */
public class ConsentVerify extends com.ardais.bigr.iltds.op.StandardOperation {

  public ConsentVerify(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }

  private boolean checkGeoLocation(ConsentAccessBean consent, String userId) throws Exception {
    ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(userId));
    String userLocation = staff.getGeolocation_location_address_id();

    String consentLocation = consent.getGeolocationKey().location_address_id;

    return consentLocation.equals(userLocation);
  }

  /**
   * Check to see if a Consent ID has previously been entered.
   */
  private boolean consentIdExists(String consentId) throws Exception {
    try {
      new ConsentAccessBean(new ConsentKey(consentId));
      return true;
    }
    catch (ObjectNotFoundException e) {
      return false;
    }
  }

  public void invoke() throws Exception {
    String myConsentId =
      ApiFunctions.safeString(ApiFunctions.safeTrim(request.getParameter("consentId_1")));
    String myArdaisId =
      ApiFunctions.safeString(ApiFunctions.safeTrim(request.getParameter("ardaisId_1")));
    String myConsentId_2 =
      ApiFunctions.safeString(ApiFunctions.safeTrim(request.getParameter("consentId_2")));
    String myArdaisId_2 =
      ApiFunctions.safeString(ApiFunctions.safeTrim(request.getParameter("ardaisId_2")));

    String myHours = ApiFunctions.safeString(ApiFunctions.safeTrim(request.getParameter("hours")));
    String myMinutes =
      ApiFunctions.safeString(ApiFunctions.safeTrim(request.getParameter("minutes")));
    String myAmpm = ApiFunctions.safeString(ApiFunctions.safeTrim(request.getParameter("ampm")));

    if (!myConsentId.equals(myConsentId_2)) {
      retry("Case IDs do not match or are empty.");
      return;
    }
    else if (!myArdaisId.equals(myArdaisId_2)) {
      retry("Donor IDs do not match or are empty.");
      return;
    }
    else if ( (ValidateIds.validateId(myConsentId, ValidateIds.TYPESET_CASE_LINKED, false) == null) &&
    (ValidateIds.validateId(myConsentId, ValidateIds.TYPESET_CASE_IMPORTED, false) == null) ) {      
      retry("Invalid Case ID format.");
      return;
    }
    // MR 7777 using validateId.  note that this will not allow imported ardais ids... 
    else if ( (ValidateIds.validateId(myArdaisId, ValidateIds.TYPESET_DONOR_LINKED, false) == null)) {
      retry("Invalid Donor ID format.");
      return;
    }
    
    // MR8221 be sure to call this check prior to verifying policy.
    if (!consentIdExists(myConsentId)) {
      retry("Initial consent information for " + myConsentId + " has not been entered.");
      return;
    }
        
    // MR 8021: check to see if verify is required by the policy
    PolicyData policy = IltdsUtils.getPolicyForConsent(myConsentId);
    if (!"Y".equalsIgnoreCase(policy.getVerifyRequired())) {
      retry("Verification is not required for this case.");
      return;
    }

    if (ApiFunctions.isEmpty(myHours)) {
      retry("Please enter a value for Hours.");
      return;
    }
    else if (ApiFunctions.isEmpty(myMinutes)) {
      retry("Please enter a value for Minutes.");
      return;
    }

    int myIntEnteredHours = Integer.parseInt(myHours);
    int myIntEnteredMinutes = Integer.parseInt(myMinutes);

    // Check hours/minutes
    {
      if (myIntEnteredHours < 1 || myIntEnteredHours > 12) {
        retry("Please enter a valid value for Hours.");
        return;
      }
      else if (myIntEnteredMinutes < 0 || myIntEnteredMinutes > 59) {
        retry("Please enter a valid value for Minutes.");
        return;
      }
    }

    ConsentAccessBean myConsent = new ConsentAccessBean(new ConsentKey(myConsentId));
    String userId = (String) request.getSession(false).getAttribute("user");

    if (!myArdaisId.equals(myConsent.getArdais_id())) {
      retry(
        "Consent "
          + myConsentId
          + " exists with Donor ID:"
          + myConsent.getArdais_id()
          + ", not "
          + myArdaisId);
      return;
    }
    else if (myConsent.getForm_verified_datetime() != null) {
      retry("Consent " + myConsentId + " has already been already verified.");
      return;
    }
    else if (!checkGeoLocation(myConsent, userId)) {
      retry("Consent " + myConsentId + " belongs to a different donor institution.");
      return;
    }

    // Check that the entered consent time matches the actual consent time from the database.
    {
      Calendar myCalendar = Calendar.getInstance();
      myCalendar.setTime(new java.util.Date(myConsent.getConsent_datetime().getTime()));
      int myIntActualHours = myCalendar.get(Calendar.HOUR);
      if (myIntActualHours == 0) {
        // User enters noon/midnight as 12 not 0.  Adjust.
        myIntActualHours = 12;
      }
      int myIntActualMinutes = myCalendar.get(Calendar.MINUTE);
      String myActualAMPM = ((myCalendar.get(Calendar.AM_PM) == Calendar.AM) ? "am" : "pm");
      if (!myAmpm.equals(myActualAMPM)
        || (myIntActualHours != myIntEnteredHours)
        || (myIntActualMinutes != myIntEnteredMinutes)) {
        retry("Hours and minutes do not match initial consent. ");
        return;
      }
    } // end check entered/actual consent time

    servletCtx.getRequestDispatcher("/hiddenJsps/iltds/consent/consentVerifyConfirm.jsp").forward(
      request,
      response);
  }

  private void retry(String myError) throws Exception {
    request.setAttribute("myError", myError);
    servletCtx.getRequestDispatcher("/iltds/Dispatch?op=ConsentStartVerify").forward(
      request,
      response);
  }
}
