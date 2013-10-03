package com.ardais.bigr.orm.helpers;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.ApiResources;

/**
 * Insert the type's description here.
 * Creation date: (3/7/2001 5:08:33 PM)
 * @author: Jake Thompson
 */
public class FormLogic {
  
  static {
    int defaultAttempts = 5;
    
    //initialize maximum login attempts allowed
    String maxAttempts = ApiProperties.getProperty(ApiResources.MAX_LOGIN_ATTEMPTS);
    int myAttempts;
    try {
      myAttempts = Integer.valueOf(maxAttempts).intValue();
    }
    catch (Exception e) {
      myAttempts = defaultAttempts;
    }
    MAX_LOGIN_ATTEMPTS = myAttempts;
    
    //initialize maximum security answer attempts allowed
    maxAttempts = ApiProperties.getProperty(ApiResources.MAX_SECURITY_QUESTION_ATTEMPTS);
    try {
      myAttempts = Integer.valueOf(maxAttempts).intValue();
    }
    catch (Exception e) {
      myAttempts = defaultAttempts;
    }
    MAX_SECURITY_ANSWER_ATTEMPTS = myAttempts;
  }

  /*
   * This many failed login or failed security answer attempts will result in the user's 
   * account being disabled.  Initialized above.
   */
  public final static int MAX_LOGIN_ATTEMPTS;
  public final static int MAX_SECURITY_ANSWER_ATTEMPTS;
  
  public final static int LENGTH_PASSWORD = 80;

  public final static String ALL = "ALL";
  public final static String ESTORE = "ES";
  public final static String ILTDS = "ILTDS";
  public final static String ORM = "ORM";
  public final static String LIMS = "LIMS";
  public final static String PDC = "PDC";
  public final static String DDC = "DDC";
  public final static String IMPORT = "IMP";
  public final static String ALL_APP = "All";
  public final static String BIGR_LIB_APP = "BIGR Library";
  public final static String ILTDS_APP = "ILTDS";
  public final static String DDC_APP = "DDC";
  public final static String LIMS_APP = "LIMS";
  public final static String ONM_APP = "O&M";
  public final static String IMPORT_APP = "Sample Registration";
  public final static String SEQ_IRBPROTOCOL_ID = "ARD_IRB_SEQ";
  public final static String SEQ_CONSENT_VERSION_ID = "ARD_CONSENT_VERSION_SEQ";

  public static final String SESSION_PROPERTY_LAST_NON_KEEPALIVE_ACTIVITY =
    "com.ardais.bigr.lastNonKeepAliveActivity";

  private static final String API_PROPERTY_SESSION_KEEPALIVE_INACTIVE =
    "api.session.keepalive.inactive";
  // The default number of seconds after which to time out the session if there hasn't
  // been any activity other than keepAlive page requests.
  // The number of seconds in a day is 60 * 60 * 24.
  //
  private static final long SESSION_KEEPALIVE_INACTIVE_DEFAULT = 7 * (60 * 60 * 24);

  private static final String LETTERS_AND_DIGITS =
    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
  // User ids can contain letters and digits plus any of the characters in this string.
  //
  private static final String VALID_USERID_OTHER_CHARS = "_-.";

  // User ids must be at least 3 characters in length
  public static final int MINIMUM_USER_ID_LENGTH = 3;

  // User ids must be at most 12 characters in length
  public static final int MAXIMUM_USER_ID_LENGTH = 12;
  
  /**
   * FormLogic constructor comment.
   */
  public FormLogic() {
    super();
  }
  /**
   * Insert the method's description here.
   * Creation date: (6/5/01 12:40:31 PM)
   * @return boolean
   * @param phone java.lang.String
   */
  public static boolean checkNumeric(String phone) {
    try {
      new Long(phone);
      return true;
    }
    catch (NumberFormatException e) {
      return false;
    }

    //return false;
  }
  /**
   * Insert the method's description here.
   * Creation date: (6/5/01 12:36:03 PM)
   * @return boolean
   * @param phone java.lang.String
   */
  public static boolean checkPhoneFormat(String phone) {
    if ((phone != null) && (phone.length() > 0)) {
      java.util.StringTokenizer phoneNum = new java.util.StringTokenizer(phone, "-");
      java.lang.String temp[] = new java.lang.String[3];
      int i = 0;
      try {
        while (phoneNum.hasMoreTokens()) {
          temp[i] = phoneNum.nextToken();
          i++;
        }
      }
      catch (java.lang.ArrayIndexOutOfBoundsException a) {
        return false;
      }

      for (int j = 0; j < temp.length; j++) {
        if (j < 2) {
          if (temp[j].length() == 3) {
            if (!(checkNumeric(temp[j])))
              return false;
          }
          else {
            return false;
          }
        }
        else {
          if (temp[j].length() == 4) {
            if (!(checkNumeric(temp[j]))) {
              return false;
            }
          }
          else {
            return false;
          }
        }
      }

    }

    return true;
  }
  
  /**
   * This method is used to verify that an email address
   * has the format (non-empty)@(non-empty).(non-empty)
   * Creation date: (4/22/05 11:00:00 AM)
   * @return boolean
   * @param phone java.lang.String
   */
  public static boolean checkEmailAddressFormat(String email_address) {
    /*
     * pattern is:
     * 1) word character, one or more times {\\w+}
     * 2) . followed by 1 or more word characters, 0-N times {(\u002E\\w+)*}
     * 3) @ {@}
     * 4) word character, one or more times {\\w+}
     * 5) . {\u002E}
     * 6) word character, one or more times {\\w+}
     * 7) . followed by 1 or more word characters, 0-N times {(\u002E\\w+)*}
     * 
     */
    
    boolean returnValue = false;
    //Note - Anne found a problem with this code (see MR8739).  It appears that given a large
    //email_address (i.e. 100 characters) containing no "." or "@" the matcher gets confused.  So,
    //we do a quick search for these characters up front.  If they are found we proceed, and if not
    //we quit.
    if (!ApiFunctions.isEmpty(email_address)) {
      if (email_address.indexOf(".") > 0 && email_address.indexOf("@") > 0) {
        Pattern p = Pattern.compile("\\w+(\u002E\\w+)*@\\w+\u002E\\w+(\u002E\\w+)*");
        Matcher m = p.matcher(email_address);
        returnValue = m.matches();
      }
    }
    return returnValue;
  } 

  /**
   * Insert the method's description here.
   * Creation date: (11/27/01 11:12:23 AM)
   * @return boolean
   */
  public static boolean commonPageActions(
    HttpServletRequest req,
    HttpServletResponse res,
    ServletContext ctx,
    String pageDispatchArg) {
    String pageDispatch = ApiFunctions.safeString(ApiFunctions.safeTrim(pageDispatchArg));

    boolean retVal = commonPageActionsNoForward(req, res, pageDispatch);

    if (!retVal) {
      if (pageDispatch.equalsIgnoreCase("P")) {
        // Use P in JSP pages that require login.  In this case this method
        // takes care of forwarding to the error page.

        onErrorForward(req, res, ctx);
      }
    }

    return retVal;
  }

  /**
   * Returns true if the caller should stop processing the original request and forward
   * to the session timout page instead.
   */
  public static boolean commonPageActionsNoForward(
    HttpServletRequest req,
    HttpServletResponse res,
    String pageDispatchArg) {
    String pageDispatch = ApiFunctions.safeString(ApiFunctions.safeTrim(pageDispatchArg));

    if ((!pageDispatch.equalsIgnoreCase("P"))
      && (!pageDispatch.equalsIgnoreCase("N"))
      && (!pageDispatch.equalsIgnoreCase("D"))) {
      throw new IllegalArgumentException("Unexpected page dispatch code: " + pageDispatch);
    }

    boolean isKeepAliveRequest = isSessionKeepAliveRequest(req);

    if ((!pageDispatch.equalsIgnoreCase("N")) || isSessionKeepAliveRequest(req)) {
      // Use N for pages that don't require the user to be logged in
      // We also need to check whether the session is or should be expired when
      // the request is for the session keepalive URI, even though that request
      // doesn't require the user to be logged in, since if keepAlive has been the
      // only thing keeping the session alive for long enough we may still expire the session.
      if (!validateSession(req, res)) {
        //log session time out. MR 5348
        if (ApiFunctions
          .safeEquals(ApiProperties.getProperty("api.session.timeout.log"), "true")) {
          StringBuffer logBuff = new StringBuffer(256);
          logBuff.append(
            "Session time out called from com.ardais.bigr.orm.helpers.FormLogic.commonPageActionsNoForward()");
          logBuff.append(" for path = " + req.getRequestURI());
          ApiLogger.getLog().info(logBuff.toString());
        }

        // If the request was for the keepalive page, don't forward to nosession.jsp.
        // It is important the the original keepAlive.jsp response be preserved intact
        // since it will have special contents that need to be interpreted by MenuLHS.jsp.
        // See keepAlive.jsp for details.
        if (isKeepAliveRequest) {
          return true;
        }
        else {
          return false;
        }
      }
    }

    updateSessionActivityTracker(req, res);

    noCache(res);

    return true;
  }

  /**
   * Return true if the request is a session keep-alive request.
   */
  private static boolean isSessionKeepAliveRequest(HttpServletRequest req) {
    // Even though the user request for keepalive is initially keepAlive.do,
    // it forwards to keepAlive.jsp.  Both of those requests end up getting
    // routed through here, so we code this to recognize either one.
    //
    return (req.getRequestURI().indexOf("/keepAlive.") >= 0);
  }

  /**
   * If there is a session and the current request isn't a keepalive request,
   * update the session property where we keep track of the last time we saw
   * non-keepalive activity to be the current time.  However, DON'T update
   * the last-activity timestamp if the difference between the current time
   * and the current last-activity timestamp exceeds the timeout threshold
   * that is the value of the "api.session.keepalive.inactive" property in Api.properties.
   * This provision ensure correct behavior in a situation like this:  The user's last
   * activity was long enough ago that the threshold is exceeded at the time
   * of the request, but the current request is for a page that doesn't require
   * the user to be logged in and that isn't the keepAlive page.  If we went ahead
   * and updated the timestamp, then the next page request that requires login would
   * succeed rather than invalidating the session and forcing the user to log in
   * again.  This would be wrong, since that browser was actaully sitting idle long
   * enough to time out.
   * 
   * <p>If we detect this special situation here, we don't just skip updating the
   * last-activity timestamp, we actually invalidate the session so that the next
   * attempt to access a page that requires login will send the user to the login
   * page.  We don't send the user to the login page here because if the page
   * currently being requested doesn't require login that would be inappropriate.
   * Invalidating the session is ok because we don't use the session to store state
   * when the user isn't logged in.
   */
  private static void updateSessionActivityTracker(
    HttpServletRequest req,
    HttpServletResponse res) {
    // Don't create a new session if there isn't one already.
    HttpSession session = req.getSession(false);
    if (session != null) {
      // KeepAlive requests specifically don't cause the activity timestamp to be updated.
      if (!isSessionKeepAliveRequest(req)) {
        // The activity timestamp doesn't need to be super-precise, since the
        // timeout computations that we base on it are long-term (most likely
        // days, but at least an hour most likely)  So, to minimize session
        // update contention, we only update the timestamp if we haven't already
        // updated it in the last 60 seconds or if there's currently no timestamp
        // stored in the session.

        long now = System.currentTimeMillis();
        boolean invalidateSession = false;
        synchronized (session) {
          Long prevValue =
            (Long) session.getAttribute(SESSION_PROPERTY_LAST_NON_KEEPALIVE_ACTIVITY);
          if (prevValue != null) {
            long elapsed = now - prevValue.longValue();
            if (elapsed > getSessionKeepaliveInactiveMillis()) {
              invalidateSession = true;
            }
          }
          if ((!invalidateSession)
            && ((prevValue == null) || ((now - prevValue.longValue()) > 60000))) {
            session.setAttribute(SESSION_PROPERTY_LAST_NON_KEEPALIVE_ACTIVITY, new Long(now));
          }
        }
        if (invalidateSession) {
          invalidateSession(req, res);
        }
      }
    }
  }

  private static long getSessionKeepaliveInactiveMillis() {
    // This is stored as seconds in the properties file, we want to return
    // milliseconds here.

    long propertyInSeconds =
      ApiProperties.getPropertyAsLong(
        API_PROPERTY_SESSION_KEEPALIVE_INACTIVE,
        SESSION_KEEPALIVE_INACTIVE_DEFAULT);

    return (1000 * propertyInSeconds);
  }

  private static String getCookieDomain(HttpServletRequest req) {

    String host = req.getServerName();
    String result = null;
    int pos = host.indexOf('.');
    boolean errorDetected = false;

    if (pos >= 0) {
      result = host.substring(pos);
      // Make sure result has at least one "." in it besides the one
      // that it now begins with.
      if (result.indexOf('.', 1) < 0)
        errorDetected = true;
    }
    else {
      errorDetected = true;
    }

    if (errorDetected) {
      throw new RuntimeException("The host name you use in the URL must be a complete name (in the form 'x.y.com').");
    }

    return result;
  }

  public static void invalidateSession(HttpServletRequest req, HttpServletResponse res) {
    HttpSession session = req.getSession(false);
    {
      // LIMS - New process to clean up the temp files
      StringBuffer buff = new StringBuffer(50);
      buff.append(File.separator);
      buff.append("opt");
      buff.append(File.separator);
      buff.append("WebSphere");
      buff.append(File.separator);
      buff.append("AppServer");
      buff.append(File.separator);
      buff.append("bigr");
      buff.append(File.separator);
      buff.append("limsreport");
      buff.append(File.separator);
      String dir = buff.toString();
      try {
        File f;
        if (session != null) {
          f = new File(dir, "report_" + session.getId() + ".csv");
          f.delete();
        }
        f = new File(dir, "user_label.txt");
        f.delete();
        f = new File(dir, "slide_label.txt");
        f.delete();
      }
      catch (SecurityException se) {
        ApiFunctions.throwAsRuntimeException(se);
      }
    }

    // Clean up cookies set for reporting
    //
    {
      String reportCookieName =
        ((session == null) ? null : ((String) session.getAttribute("reportCookieName")));

      if (reportCookieName != null) {
        Cookie cookie = new Cookie(reportCookieName, "");
        cookie.setMaxAge(0); // 0 means delete the cookie
        cookie.setDomain(getCookieDomain(req));
        cookie.setPath("/");
        cookie.setSecure(req.getScheme().equals("https"));
        res.addCookie(cookie);
        session.removeAttribute("reportCookieName");
      }
    }

    // Invalidate the user session.
    //
    if (session != null) {
      session.invalidate();
    }
  }

  /**
   * Insert the method's description here.
   * Creation date: (11/26/01 12:07:14 PM)
   */
  public static void noCache(HttpServletResponse res) {

    res.setHeader("Pragma", "no-cache");
    res.setHeader("Cache-Control", "no-cache, must-revalidate");
    res.setDateHeader("max-age", 0);
    res.setDateHeader("Expires", 0);
  }
  /**
   * Insert the method's description here.
   * Creation date: (11/27/01 1:15:00 PM)
   */
  private static void onErrorForward(
    HttpServletRequest req,
    HttpServletResponse res,
    ServletContext ctx) {
    try {
      //log session time out. MR 5348
      if (ApiFunctions.safeEquals(ApiProperties.getProperty("api.session.timeout.log"), "true")) {
        StringBuffer logBuff = new StringBuffer(256);
        logBuff.append(
          "Session time out called from com.ardais.bigr.orm.helpers.FormLogic.onErrorForward()");
        logBuff.append(" for path = " + req.getRequestURI());
        ApiLogger.getLog().info(logBuff.toString());
      }
      req.setAttribute("TimeOutError", "Y");
      ctx.getRequestDispatcher("/nosession.jsp").forward(req, res);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * Insert the method's description here.
   * Creation date: (11/20/01 9:06:52 AM)
   * @return boolean
   */
  private static boolean validateSession(HttpServletRequest req, HttpServletResponse res) {

    HttpSession session = req.getSession(false);
    String account = null;
    String user = null;
    boolean mustInvalidateSession = false;

    if (session != null) {
      account = (String) session.getAttribute("account");
      user = (String) session.getAttribute("user");
    }

    if (account == null || user == null) {
      mustInvalidateSession = true;
    }

    if (!mustInvalidateSession) {
      // If we haven't already determined that we need to invalidate the session,
      // then check to see whether the session has timed out on the basis of
      // not having received a request other than a keepAlive request for too long.

      long now = System.currentTimeMillis();
      synchronized (session) {
        Long prevValue = (Long) session.getAttribute(SESSION_PROPERTY_LAST_NON_KEEPALIVE_ACTIVITY);
        if (prevValue != null) {
          long elapsed = now - prevValue.longValue();
          if (elapsed > getSessionKeepaliveInactiveMillis()) {
            mustInvalidateSession = true;
          }
        }
      }
    }

    if (mustInvalidateSession) {
      invalidateSession(req, res);
    }

    return (!mustInvalidateSession);
  }

  /**
   * Validate the format of a user id.  User ids must contain at least one letter or digit and
   * must contain only letters, digits, hyphens, underscores, and periods.  User ids must also
   * be at least 3 characters and at most 12 characters in length
   * 
   * @param s the string to validate.
   * @return
   */
  public static boolean checkUserIdFormat(String s) {
    boolean valid = true;
    boolean hasALetterOrDigit = false;

    if (!ApiFunctions.isEmpty(s)) {
      char[] temp = s.toCharArray();
      for (int i = 0; i < temp.length; i++) {
        char c = temp[i];
        if (LETTERS_AND_DIGITS.indexOf(c) >= 0) {
          hasALetterOrDigit = true;
        }
        else if (VALID_USERID_OTHER_CHARS.indexOf(c) < 0) {
          valid = false;
          break;
        }
      }
      
      if (s.length() > MAXIMUM_USER_ID_LENGTH ||
          s.length() < MINIMUM_USER_ID_LENGTH) {
        valid = false;
      }
    }

    if (valid && !hasALetterOrDigit) {
      valid = false;
    }

    return valid;
  }

  public static String translateSlotIdToLetter(int j) {
    String slotId = "";
    if (j == 1)
      slotId = "A";
    else if (j == 2)
      slotId = "B";
    else if (j == 3)
      slotId = "C";
    else if (j == 4)
      slotId = "D";
    else if (j == 5)
      slotId = "E";
    else if (j == 6)
      slotId = "F";
    else if (j == 7)
      slotId = "G";
    else if (j == 8)
      slotId = "H";
    else if (j == 9)
      slotId = "I";
    else if (j == 10)
      slotId = "J";
    else if (j == 11)
      slotId = "K";
    else if (j == 12)
      slotId = "L";
    else if (j == 13)
      slotId = "M";
    else if (j == 14)
      slotId = "N";
    else if (j == 15)
      slotId = "O";
    else if (j == 16)
      slotId = "P";
    else if (j == 17)
      slotId = "Q";
    else if (j == 18)
      slotId = "R";
    else if (j == 19)
      slotId = "S";
    else if (j == 20)
      slotId = "T";
    else if (j == 21)
      slotId = "U";
    else if (j == 22)
      slotId = "V";
    else if (j == 23)
      slotId = "W";
    else if (j == 24)
      slotId = "X";
    else if (j == 25)
      slotId = "Y";
    else if (j == 26) slotId = "Z";
    return slotId;
  }
}
