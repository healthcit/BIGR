package com.ardais.bigr.orm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.orm.op.AddConsentVertoIRB;
import com.ardais.bigr.orm.op.AddIRBandConsentVersion;
import com.ardais.bigr.orm.op.GetDDCDxTcHierarchy;
import com.ardais.bigr.orm.op.GetDDCDxTcHierarchyOther;
import com.ardais.bigr.orm.op.GetDxTcHierarchy;
import com.ardais.bigr.orm.op.GetLIMSDxTcHierarchy;
import com.ardais.bigr.orm.op.IRBConsentStart;
import com.ardais.bigr.orm.op.PrepViewConsent;
import com.ardais.bigr.orm.op.PrepareConsentTexttoIRB;
import com.ardais.bigr.orm.op.PrintUserLabel;
import com.ardais.bigr.orm.op.PrintUserLabelGetUser;
import com.ardais.bigr.orm.op.PrintUserLabelStart;
import com.ardais.bigr.orm.op.ReportError;
import com.ardais.bigr.orm.op.SaveIRBConsentVerRelation;
import com.ardais.bigr.orm.op.StandardOperation;
import com.ardais.bigr.orm.op.WriteConsentTexttoIRB;

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

    // Account Management Start
    else if (opname.equals("IrbConsentStart")) {
      return (new IRBConsentStart(request, response, this.getServletContext()));
    }
    else if (opname.equals("AddConsentvertoIRB")) {
      return (new AddConsentVertoIRB(request, response, this.getServletContext()));
    }
    else if (opname.equals("AddIRBandConsentVersion")) {
      return (new AddIRBandConsentVersion(request, response, this.getServletContext()));
    }
    else if (opname.equals("SaveIRBConsentVerRelation")) {
      return (new SaveIRBConsentVerRelation(request, response, this.getServletContext()));
    }
    else if (opname.equals("PrepareConsentTexttoIRB")) {
      return (new PrepareConsentTexttoIRB(request, response, this.getServletContext()));
    }
    else if (opname.equals("PrepViewConsent")) {
      return (new PrepViewConsent(request, response, this.getServletContext()));
    }
    else if (opname.equals("WriteConsentTexttoIRB")) {
      return (new WriteConsentTexttoIRB(request, response, this.getServletContext()));
    }
    // Account Management End


    else if (opname.equals("GetDxTcHierarchy")) {
      return (new GetDxTcHierarchy(request, response, this.getServletContext()));
    }
    else if (opname.equals("GetLIMSDxTcHierarchy")) {
      return (new GetLIMSDxTcHierarchy(request, response, this.getServletContext()));
    }
    else if (opname.equals("GetDDCDxTcHierarchy")) {
      return (new GetDDCDxTcHierarchy(request, response, this.getServletContext()));
    }
    else if (opname.equals("GetDDCDxTcHierarchyOther")) {
      return (new GetDDCDxTcHierarchyOther(request, response, this.getServletContext()));
    }


    //Print User Label Start
    else if (opname.equals("GetUser")) {
      return (new PrintUserLabelStart(request, response, this.getServletContext()));
    }
    else if (opname.equals("PrintUserLabelGetUser")) {
      return (new PrintUserLabelGetUser(request, response, this.getServletContext()));
    }
    else if (opname.equals("PrintUserLabel")) {
      return (new PrintUserLabel(request, response, this.getServletContext()));
    }
    //End Print User Label

    return null;
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
    throws IOException, ServletException {

    String myOp = null;
    try {
      myOp = request.getParameter("op");

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
