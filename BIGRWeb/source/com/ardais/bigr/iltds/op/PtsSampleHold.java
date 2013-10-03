package com.ardais.bigr.iltds.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.beans.PtsOperation;
import com.ardais.bigr.iltds.beans.PtsOperationHome;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.iltds.helpers.ProjectHelper;
import com.ardais.bigr.util.EjbHomes;

/**
 * <code>SampleSelectCustomer</code> is used to select samples
 * from a project for a customer.
 */
public class PtsSampleHold extends StandardOperation {
  /**
   * Creates a SampleSelectCustomer op.
   *
   * @param  req  the HttpServletRequest
   * @param  res  the HttpServletResponse
   * @param  ctx  the ServletContext
   */
  public PtsSampleHold(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }
  /**
   */
  public void invoke() throws IOException, ServletException, ApiException {
    String projectId = request.getParameter(ProjectHelper.ID_PROJECT_ID);
    if (projectId == null) {
      throw new ServletException(
        "Required parameter '" + ProjectHelper.ID_PROJECT_ID + "' is not present.");
    }
    String account = request.getParameter(ProjectHelper.ID_CLIENT);
    if (JspHelper.isEmpty(account)) {
      throw new ServletException(
        "Required parameter '" + ProjectHelper.ID_CLIENT + "' is not present.");
    }
    String user = request.getParameter(ProjectHelper.ID_SHOPPING_CART);
    if (JspHelper.isEmpty(user)) {
      throw new ServletException(
        "Required parameter '" + ProjectHelper.ID_SHOPPING_CART + "' is not present.");
    }
    String samples[] = request.getParameterValues("sample");
    if ((samples == null) || (samples.length == 0)) {
      request.setAttribute(JspHelper.ID_MESSAGE, "No samples were selected.");
    }
    else {
      try {
        PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
        PtsOperation cartOp = home.create();
        String error = cartOp.addSamplesToHoldForProject(samples, user, account);
        if (error != null) {
          request.setAttribute(
            JspHelper.ID_MESSAGE,
            "The following samples have been removed from the system and could not be placed on hold: "
              + error
              + ".  The remaining samples were placed on hold.");
        }
        else {
          if (samples.length == 1) {
            request.setAttribute(JspHelper.ID_MESSAGE, "1 sample was placed on hold");
          }
          else {
            request.setAttribute(
              JspHelper.ID_MESSAGE,
              String.valueOf(samples.length) + " samples were placed on hold");
          }
        }
      }
      catch (javax.ejb.CreateException e) {
        throw new ApiException(e);
      }
      catch (javax.naming.NamingException e) {
        throw new ApiException(e);
      }
      catch (ClassNotFoundException e) {
        throw new ApiException(e);
      }
    }
    (new PtsSampleHoldPrepare(request, response, servletCtx)).invoke();
  }
}
