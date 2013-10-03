package com.ardais.bigr.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * This class allows programmatic retrieval of web pages from Java code.
 */
public final class WebPageGetter {

  /**
   * There's no reason to instantiate this class, so we make its constructor
   * private.
   */
  private WebPageGetter() {
    super();
  }

  /**
   * Get the web page whose URL is specified as the command-line
   * argument and print it to System.out.
   * 
   * <p>There are three optional command-line arguments (if any of them
   * are specified, they must all be specified).  They support accessing
   * BIGR URLs that require login.  The parameters, in order, are
   * (1) the user id; (2) the account id; and (3) the password.
   * 
   * <p>This never throws an exception, it writes any errors to System.err.
   *
   * @param args an array of command-line arguments
   */
  public static void main(String[] args) {
    // (IMPORTANT NOTE: This isn't invoked through MainRunner and the
    // corresponding MainRunner.sh shell script as most main methods are.
    // The reason is that the JSSE classes that support https didn't
    // properly support Thawte server certificates in JDK 1.2.2, which
    // is what WAS 3.5 used.  Now that we're using WAS 4 and JDK 1.3
    // it is possible that this would work via MainRunner but it hasn't
    // been tested.
    //
    // Because this isn't being run through MainRunner and we can't use
    // any of our classes such as ApiLogger, this writes any exceptions
    // to System.err instead of throwing them and having them be logged
    // by MainRunner, as our other main methods typically do.  If ever
    // start to use MainRunner to run this, this should be changed
    // to work like our other main methods and throw exceptions.

    if (args == null || (args.length != 1 && args.length != 4)) {
      System.err.println("ERROR: Usage: WebPageGetter <url> [<userid> <accountid> <password>]");
      return;
    }

    BufferedReader in = null;
    try {
      URL url = new URL(args[0]);

      String sessionCookie = null;

      if (args.length > 1) {
        sessionCookie = doBigrLogin(url, args[1], args[2], args[3]);
      }

      URLConnection conn = url.openConnection();
      conn.setAllowUserInteraction(false);
      conn.setDoOutput(false);
      conn.setDoInput(true);
      if (sessionCookie != null) {
        conn.setRequestProperty("Cookie", sessionCookie);
      }
      conn.connect();

      in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

      String inputLine = null;

      while ((inputLine = in.readLine()) != null) {
        System.out.println(inputLine);
      }
    }
    catch (Exception e) {
      System.err.println("ERROR: Failed to retrieve web page: " + args[0]);
      e.printStackTrace(System.err);
    }
    finally {
      if (in != null) {
        try {
          in.close();
          in = null;
        }
        catch (IOException e1) {
          System.err.println("ERROR closing web page retrieval stream for " + args[0]);
          e1.printStackTrace(System.err);
        }
      }
    }
  }

  /**
   * Log in to BIGR with the specified user/account/password credentials.
   * If the login fails, throw a runtime exception, otherwise return the
   * session cookie for the HTTP session.
   * 
   * @param webAppUrl any URL in the BIGR web application that we want
   *     to log in to
   * @param userId the user id
   * @param accountId the account id
   * @param password the password
   * 
   * @return the session cookie for the HTTP session if the login is
   *     successful.
   */
  private static String doBigrLogin(
    URL webAppUrl,
    String userId,
    String accountId,
    String password)
    throws Exception {

    String sessionCookie = null;
    Writer out = null;
    BufferedReader in = null;
    try {
      URL url = getBigrLoginHandlerUrl(webAppUrl);

      // When we open the connection we turn off automatic redirect
      // following.  We do this for two reasons.  First, we expect the
      // BIGR login to redirect us to BIGRMain.jsp, but the automatic
      // redirection doesn't send the JSESSIONID cookie along and so
      // BIGR ends up redirecting to a login page instead, the same as
      // if the login credentials had been incorrect.  Second, testing
      // for getting a redirect response to BIGRMain.jsp is one of the
      // ways we use below to detect a successful login.
      //
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setAllowUserInteraction(false);
      conn.setInstanceFollowRedirects(false);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      // Pretend we're IE 6.0 so that we pass any browser checks we might encounter.
      conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
      conn.setDoOutput(true);
      conn.setDoInput(true);

      out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "8859_1"));
      out.write("noOfAttempts=");
      out.write(URLEncoder.encode("0", "UTF-8"));
      out.write("&userId=");
      out.write(URLEncoder.encode(userId, "UTF-8"));
      out.write("&accountId=");
      out.write(URLEncoder.encode(accountId, "UTF-8"));
      out.write("&password=");
      out.write(URLEncoder.encode(password, "UTF-8"));
      out.write("&submit=");
      out.write(URLEncoder.encode("Log In", "UTF-8"));
      out.flush();
      out.close();
      out = null;

      sessionCookie = extractSessionCookie(conn);

      // Read through the response to try to figure out if the
      // login failed.  This isn't satisfyingly robust but I can't
      // think of a better approach.  We try two different approaches.
      // First, we check to see if the response is a redirect to
      // BIGRMain.jsp (at the time that this was written, that's what it
      // was).  If that test fails, we read through the result set
      // looking for "<frameset".  We do this because that's what we'd
      // find if we ever changed the code to forward to BIGRMain.jsp
      // instead of redirecting there.  If both of these tests fail
      // we assume that the login failed and we throw an exception.
      {
        boolean loginSucceeded = false;

        int responseCode = conn.getResponseCode();
        if ((responseCode >= 300) && (responseCode < 400)) {
          String redirectUrl = conn.getHeaderField("location").toLowerCase();
          if (redirectUrl.indexOf("bigrmain.jsp") >= 0) {
            loginSucceeded = true;
          }
        }

        if (!loginSucceeded) {
          in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

          String inputLine = null;
          while ((inputLine = in.readLine()) != null) {
            if (inputLine.toLowerCase().indexOf("<frameset") >= 0) {
              loginSucceeded = true;
              break;
            }
          }
        }

        if (!loginSucceeded) {
          throw new RuntimeException(
            "Failed to log in to BIGR with userId = " + userId + ", accountId = " + accountId);
        }
      }
    }
    finally {
      if (in != null) {
        in.close();
        in = null;
      }
      if (out != null) {
        out.close();
        out = null;
      }
    }

    return sessionCookie;
  }

  /**
   * Return the URL that handles login request for the BIGR application
   * that contains the supplied URL.
   * 
   * @param webAppUrl any URL in the BIGR web application that we want
   *     to log in to
   * 
   * @return the login handler URL
   */
  private static URL getBigrLoginHandlerUrl(URL webAppUrl) throws Exception {
    // Find the prefix of the URL that contains the web app context string.
    // For example, if the supplied URL was
    // https://integration.ardais.com/BIGR/lims/subdivideStart.do,
    // the the prefix that we want is https://integration.ardais.com/BIGR.
    // Then append the login-handler path to this prefix.

    String urlStr = webAppUrl.toString();
    int i = urlStr.indexOf("//");
    i = urlStr.indexOf('/', i + 2); // the / after the hostname
    i = urlStr.indexOf('/', i + 1); // the / after the web app context
    String prefix;
    if (i < 0) {
      // there was no / after the  web app context string, the prefix
      // string is all of urlStr
      //
      prefix = urlStr;
    }
    else {
      prefix = urlStr.substring(0, i);
    }

    return new URL(prefix + "/orm/login.do");
  }

  /**
   * Return the session cookie from the headers of the specified connection
   * if any, otherwise return null.  This looks for a cookie named JSESSIONID,
   * which is what WebSphere 4 uses.  This method is not necessarily
   * portable to other J2EE app servers, or even to other versions of
   * WebSphere.
   * 
   * @return the session cookie
   */
  private static String extractSessionCookie(URLConnection conn) {
    String sessionCookie = null;

    if (null != conn) {
      int i = 0;
      while (conn.getHeaderField(i) != null) {
        String key = conn.getHeaderFieldKey(i);
        String data = conn.getHeaderField(i);
        if ((null != key)
          && (key.compareToIgnoreCase("Set-cookie") == 0)
          && data.startsWith("JSESSIONID")) {
          sessionCookie = data.substring(0, data.indexOf(";"));
          sessionCookie.trim();
        }
        i++;
      }
    }

    return sessionCookie;
  }
}
