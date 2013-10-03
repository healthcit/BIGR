package com.ardais.bigr.es.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.es.op.DXTCHierarchy;
import com.ardais.bigr.es.op.PathInfoPathReport;
import com.ardais.bigr.es.op.ReportError;
import com.ardais.bigr.es.op.StandardOperation;

public class Dispatch extends javax.servlet.http.HttpServlet {

  public Dispatch() {
    super();
  }

  public StandardOperation createApplicableOperation(
    String opname,
    HttpServletRequest request,
    HttpServletResponse response) {

    if (!(com
      .ardais
      .bigr
      .orm
      .helpers
      .FormLogic
      .commonPageActions(request, response, this.getServletContext(), "D"))) {
      return null;
    }

    if (opname.equals("DXTCHierarchy")) {
      return (new DXTCHierarchy(request, response, this.getServletContext()));
    }
    // START PATH INFO
    else if (opname.equals("PathInfoStart")) {
      return (new PathInfoPathReport(request, response, this.getServletContext()));
    }
    else if (opname.equals("PathInfoPathReport")) {
      return (new PathInfoPathReport(request, response, this.getServletContext()));
    }
    else if (opname.equals("PathInfoDonorDX")) {
      return (new PathInfoPathReport(request, response, this.getServletContext()));
    }
    else if (opname.equals("PathInfoDonorTreat")) {
      return (new PathInfoPathReport(request, response, this.getServletContext()));
    }

    throw new ApiException("Unknown op name: " + opname);
  }

  /**
   * Process incoming HTTP GET requests 
   * 
   * @param request Object that encapsulates the request to the servlet 
   * @param response Object that encapsulates the response from the servlet
   */
  public void doGet(
    javax.servlet.http.HttpServletRequest request,
    javax.servlet.http.HttpServletResponse response)
    throws javax.servlet.ServletException, java.io.IOException {

    performTask(request, response);
  }

  /**
   * Process incoming HTTP POST requests 
   * 
   * @param request Object that encapsulates the request to the servlet 
   * @param response Object that encapsulates the response from the servlet
   */
  public void doPost(
    javax.servlet.http.HttpServletRequest request,
    javax.servlet.http.HttpServletResponse response)
    throws javax.servlet.ServletException, java.io.IOException {

    performTask(request, response);
  }

  /**
   * Returns the servlet info string.
   */
  public String getServletInfo() {
    return super.getServletInfo();
  }

  /**
   * Initializes the servlet.
   */
  public void init() {
    // insert code to initialize the servlet here
  }

  /**
   * Process incoming requests for information
   * 
   * @param request Object that encapsulates the request to the servlet 
   * @param response Object that encapsulates the response from the servlet
   */
  public void performTask(
    javax.servlet.http.HttpServletRequest request,
    javax.servlet.http.HttpServletResponse response)
    throws ServletException, IOException {

    String myOp = request.getParameter("op");
    try {
      if (myOp == null) {
        throw new ApiException("Unspecified operation");
      }

      StandardOperation op = createApplicableOperation(myOp, request, response);

      if (op == null) {
        //log session time out. MR 5348
        if (ApiFunctions
          .safeEquals(ApiProperties.getProperty("api.session.timeout.log"), "true")) {
          StringBuffer logBuff = new StringBuffer(256);
          logBuff.append("Session time out called from " + this.getClass().getName());
          logBuff.append(" for op name = " + myOp);
          ApiLogger.getLog().info(logBuff.toString());
        }
        request.setAttribute("TimeOutError", "Y");
        this.getServletContext().getRequestDispatcher("/nosession.jsp").forward(request, response);

        return;
      }

      op.invoke();
    }
    catch (Exception e) {
      // If we get an exception, attempt to report it via the ReportError op.
      // If that op throws an exception, then throw it back to the servlet engine
      // and let it report the exception.
      //
      if (!ApiLogger.isIgnorableException(e)) {
        ApiLogger.log(e);
        ReportError err = new ReportError(request, response, this.getServletContext());
        err.setFromOp((myOp == null) ? "null" : myOp);
        err.setErrorMessage(e.toString());
        try {
          err.invoke();
        }
        catch (Exception e1) {
          ApiLogger.log("Error while attempting to report an exception", e1);
          throw new ServletException(e1);
        }
      }
    }
  }
}
