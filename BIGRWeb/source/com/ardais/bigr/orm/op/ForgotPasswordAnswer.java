package com.ardais.bigr.orm.op;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.ApiResources;
import com.ardais.bigr.es.beans.ArdaisaccountKey;
import com.ardais.bigr.es.beans.ArdaisuserAccessBean;
import com.ardais.bigr.es.beans.ArdaisuserKey;
import com.ardais.bigr.orm.beans.OrmUserManagement;
import com.ardais.bigr.orm.beans.OrmUserManagementHome;
import com.ardais.bigr.orm.helpers.FormLogic;
import com.ardais.bigr.orm.performers.BtxPerformerOrmOperations;
import com.ardais.bigr.util.BigrSecureRandom;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;

public class ForgotPasswordAnswer extends StandardOperation {

  // We exclude letters and digits that can easily be mistaken for other letters and
  // digits, to avoid confusion: 0, O, o, i, I, 1, l.

  private static final char PASSWORD_CHARS[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k',
      'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '2', '3', '4', '5', '6',
      '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q',
      'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

  private static final char PASSWORD_LETTERS[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
      'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
      'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
      'Y', 'Z' };

  private static final char PASSWORD_DIGITS[] = { '2', '3', '4', '5', '6', '7', '8', '9' };

  public ForgotPasswordAnswer(javax.servlet.http.HttpServletRequest req,
                              javax.servlet.http.HttpServletResponse res,
                              javax.servlet.ServletContext ctx) {
    super(req, res, ctx);
  }

  private String generatePassword() {
    StringBuffer pass = new StringBuffer();
    BigrSecureRandom rand = BigrSecureRandom.getInstance();

    for (int i = 0; i < 10; i++) {
      pass.append(PASSWORD_CHARS[rand.nextInt(PASSWORD_CHARS.length)]);
    }

    // Check to see if the password contains both digits and non-digits, which it
    // should according to our preferred password policy. If it doesn't, alter it
    // so that it does.

    String password = pass.toString();

    boolean foundDigit = false;
    boolean foundNonDigit = false;
    char[] temp = password.toCharArray();
    for (int i = 0; i < temp.length; i++) {
      char c = temp[i];
      if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6'
          || c == '7' || c == '8' || c == '9') {
        foundDigit = true;
      }
      else {
        foundNonDigit = true;
      }
    }

    if (!(foundDigit && foundNonDigit)) {
      // Change the password in a simple way that ensures that it contains both a digit and
      // a non-digit.
      temp[0] = PASSWORD_LETTERS[rand.nextInt(PASSWORD_LETTERS.length)];
      temp[1] = PASSWORD_DIGITS[rand.nextInt(PASSWORD_DIGITS.length)];
      password = new String(temp);
    }

    return password;
  }

  public void invoke() throws Exception {
    String userID = request.getParameter("UserID");
    String accountID = request.getParameter("AccountID");
    String verficationQuestion = request.getParameter("verificationQuestion");
    String verifyAnswer = request.getParameter("verificationAnswer");
    if ((verifyAnswer != null) && (verifyAnswer.trim().length() > 0)) {
      String newPassword = this.generatePassword();
      String encryptedPassword = ApiFunctions.encryptPassword(newPassword);
      OrmUserManagementHome home = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
      OrmUserManagement setPass = home.create();
      String email = (String) setPass.verifyAnswer(userID, accountID, verifyAnswer, encryptedPassword);

      if ((email != null) && (email.trim().length() > 0)) {
        String subject = "Your password has been changed";
        String body = " <p>As you have requested we have changed your password. <br>"
            + "The new password to access your account is " + newPassword + "</p>"
            + "<p>&nbsp;</p>" + "<p>&nbsp;</p>" + "<p>&nbsp;</p>" + " Adminstrator <br>"
            + " HealthCare IT Corporation ";

        // MR7170 - do not proceed with transaction if email message cannot be sent.
        if (ApiFunctions.generateEmail(ApiProperties
            .getProperty(ApiResources.API_MAIL_FROM_DEFAULT), email, subject, body)) {
          servletCtx.getRequestDispatcher("/hiddenJsps/orm/PasswordInfo.jsp").forward(request,
              response);
        }
        else {
          request.setAttribute("VerificationQuestion", verficationQuestion);
          request.setAttribute("UserID", userID);
          request.setAttribute("AccountID", accountID);
          retry("<b>The email server is temporarily down, please try again later.</b>",
              "/hiddenJsps/orm/ForgotPassword.jsp");
        }
      }
      else {
        request.setAttribute("VerificationQuestion", verficationQuestion);
        request.setAttribute("UserID", userID);
        request.setAttribute("AccountID", accountID);
        //if the user has not exceeded the maximum number of attempts to answer the
        //question let them try again
        int failedAttempts = 0;
        boolean isActive = false;
        try {
          ArdaisuserAccessBean ardaisUserEB = new ArdaisuserAccessBean(new ArdaisuserKey(userID,
              new ArdaisaccountKey(accountID)));
          failedAttempts = ardaisUserEB.getConsecutive_failed_answers().intValue();
          //if the number of attempts is exceeded and the user's account is not yet disabled,
          //disable it
          isActive = com.ardais.bigr.iltds.helpers.FormLogic.DB_YES.equalsIgnoreCase(ardaisUserEB
              .getUser_active_ind());
        }
        catch (Exception e) {
          //do nothing here, as perhaps the attempt user and/or account is invalid so no update
          //to make
        }
        if (failedAttempts < FormLogic.MAX_SECURITY_ANSWER_ATTEMPTS) {
          retry("<b>Your answer doesn't match our records.  Please verify your answer.</b>",
              "/hiddenJsps/orm/ForgotPassword.jsp");
        }
        //otherwise, disable their account
        else {
          if (isActive) {
            BtxPerformerOrmOperations performer = new BtxPerformerOrmOperations();
            performer.disableUser(accountID, userID,
                "they tried to answer the security question too many times with the wrong answer.",
                WebUtils.getSecurityInfo(request));
          }
          retry(
              "<b>You have exceeded the maximum allowable failed answer attempts, and this account has been disabled. Please contact your system administrator.</b>",
              "/hiddenJsps/orm/ForgotPassword.jsp");
        }
      }
    }
    else {
      request.setAttribute("VerificationQuestion", verficationQuestion);
      request.setAttribute("UserID", userID);
      request.setAttribute("AccountID", accountID);
      retry("<b>Please enter your answer</b>", "/hiddenJsps/orm/ForgotPassword.jsp");
    }
  }

  private void retry(String myError, String page) throws Exception {
    request.setAttribute("myError", myError);
    servletCtx.getRequestDispatcher(page).forward(request, response);
  }
}
