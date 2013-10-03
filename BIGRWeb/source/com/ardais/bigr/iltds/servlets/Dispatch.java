package com.ardais.bigr.iltds.servlets;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.iltds.op.ASMStart;
import com.ardais.bigr.iltds.op.ConsentStart;
import com.ardais.bigr.iltds.op.ReportError;
import com.ardais.bigr.iltds.op.StandardOperation;

public class Dispatch extends javax.servlet.http.HttpServlet {

  public Dispatch() {
    super();
  }

  public StandardOperation createApplicableOperation(
    String opname,
    HttpServletRequest request,
    HttpServletResponse response) {

    //Session Validation
    //
    if (!(com
      .ardais
      .bigr
      .orm
      .helpers
      .FormLogic
      .commonPageActions(request, response, this.getServletContext(), "D"))) {
      return null;
    }

    // Attempt to instantiate a StandardOperation object with the same
    // name as the op, using reflection.  If such a class cannot be found,
    // then fall through to the if ... else if... block of statements.
    Constructor constructor = reflectiveDispatch(opname);
    if (constructor != null) {
      Object constructorArgs[] = { request, response, this.getServletContext()};
      StandardOperation op = null;
      try {
        op = (StandardOperation) constructor.newInstance(constructorArgs);
      }
      catch (InvocationTargetException e) {
        op = null;
      }
      catch (InstantiationException e) {
        op = null;
      }
      catch (IllegalAccessException e) {
        op = null;
      }
      if (op != null) {
        return op;
      }
    }

    // START CONSENT
    if (opname.equals("ConsentStartVerify")) {
      return (new ConsentStart(request, response, this.getServletContext()));
    } // END CONSENT
    // START ASM
    else if (opname.equals("ASMStartLink")) {
      return (new ASMStart(request, response, this.getServletContext()));
    }
    else if (opname.equals("ASMStartLookup")) {
      return (new ASMStart(request, response, this.getServletContext()));
    } // END ASM
    // START IMS
    else if (opname.equals("IcpStart")) {
      return (
        new com.ardais.bigr.iltds.icp.op.IcpStart(request, response, this.getServletContext()));
    }
    else if (opname.equals("IcpQuery")) {
      return (
        new com.ardais.bigr.iltds.icp.op.IcpQuery(request, response, this.getServletContext()));
    }
    //END IMS
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
  public void performTask(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

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

  /**
   * Creates and returns a <code>Constructor</code> for a class
   * in the <code>com.ardais.bigr.iltds.op</code> package with the
   * same name as <code>opName</code>.  The constructor must take
   * <code>HttpServletRequest</code>, <code>HttpServletResponse</code>
   * and <code>ServletContext</code> as parameters, in that order.
   * If such a constructor is not found, then <code>null</code> is
   * returned.
   *
   * @param  opName  the name of the op class
   * @return  A <code>Constructor</code> for the op class.
   */
  private Constructor reflectiveDispatch(String opName) {
    try {
      Class opClass = Class.forName("com.ardais.bigr.iltds.op." + opName);
      Class constructorArgs[] =
        {
          Class.forName("javax.servlet.http.HttpServletRequest"),
          Class.forName("javax.servlet.http.HttpServletResponse"),
          Class.forName("javax.servlet.ServletContext")};
      Constructor constructor = opClass.getConstructor(constructorArgs);
      return constructor;
    }
    catch (ClassNotFoundException e1) {
      return null;
    }
    catch (NoSuchMethodException e2) {
      return null;
    }
  }
}
