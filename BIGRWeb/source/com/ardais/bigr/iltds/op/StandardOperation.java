package com.ardais.bigr.iltds.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.util.StrutsUtils;

public abstract class StandardOperation {
  protected HttpServletRequest request = null;
  protected HttpServletResponse response = null;
  protected ServletContext servletCtx = null;

  public StandardOperation(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {

    super();

    request = req;
    response = res;
    servletCtx = ctx;
  }
  public abstract void invoke() throws Exception;

  /**
   * Call this if a user error (not system error) is detected while performing
   * this operation.  It forwards back to the requesting page, setting a property
   * that will cause that page to display an error message.  Examples of kinds of errors
   * this should be used for are errors about invalid user data input.  Examples
   * of kinds of errors that this should NOT be used for are errors that weren't
   * caused by user input that the user can change (for example, a database error).
   *
   * @param message The message to display to the user.  This should always be
   *    a user-friendly message, never a Java exception message.
   * @param retryPath the JSP page or servlet URL to forward to.
   */
  protected void retry(String message, String retryPath) throws IOException, ServletException {
    request.setAttribute("myError", message);
    servletCtx.getRequestDispatcher(retryPath).forward(request, response);
  }

  /**
   * This is similar to {@link #retry(String, String)} but takes in a BTXDetails
   * object instead of a message string, and retries with messages taken from
   * the BTXDetails object.  This is useful in transactions that have been
   * partially converted to the BTX way of doing things, where we have an op class that
   * calls on or more BTX transactions to do part of the work.
   * 
   * <p>The page that this forwards to must have &lt;html:errors&gt; tag to display the errors.
   * 
   * @param btxDetails The BTXDetails object.
   * @param retryPath The retry URL.
   */
  protected void retry(BTXDetails btxDetails, String retryPath)
    throws IOException, ServletException {

    BtxActionErrors btxErrors = btxDetails.getActionErrors();
    ActionErrors errors = StrutsUtils.convertBtxActionErrorsToStruts(btxErrors);

    // The following code was borrowed from the Struts 1.0.2 Action.saveErrors method.
    // TODO: This may need to change when we upgrade to Struts 1.1.

    // Remove any error messages attribute if none are required
    if ((errors == null) || errors.isEmpty()) {
      request.removeAttribute(Globals.ERROR_KEY);
    }
    else {
      // Save the error messages we need
      request.setAttribute(Globals.ERROR_KEY, errors);
    }

    servletCtx.getRequestDispatcher(retryPath).forward(request, response);
  }
}
