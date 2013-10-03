package com.ardais.bigr.pdc.servlets;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.pdc.op.ReportError;
import com.ardais.bigr.pdc.op.StandardOperation;


/**
 * <code>Dispatch</code> is the controller servlet for DDC.  It instantiates
 * the appropriate op class based on the "op" request parameter and calls
 * its <code>invoke()</code> method.
 */
public class Dispatch extends javax.servlet.http.HttpServlet {
    
  
  /**
   * Creates a new <code>Dispatch</code> servlet.
   */
  public Dispatch() {
    super();
  }
  /**
   * Process incoming requests for information
   * 
   * @param request Object that encapsulates the request to the servlet 
   * @param response Object that encapsulates the response from the servlet
   */
  protected void doDispatch(HttpServletRequest request, HttpServletResponse response, List fileItems)
    throws ServletException, IOException {
    if (!com
      .ardais
      .bigr
      .orm
      .helpers
      .FormLogic
      .commonPageActions(request, response, getServletContext(), "D")) {
      //log session time out. MR 5348
      if (ApiFunctions.safeEquals(ApiProperties.getProperty("api.session.timeout.log"), "true")) {
        StringBuffer logBuff = new StringBuffer(256);
        logBuff.append("Session time out called from " + this.getClass().getName());
        logBuff.append(" for op name = " + request.getParameter("op"));
        ApiLogger.getLog().info(logBuff.toString());
      }
      request.setAttribute("TimeOutError", "Y");
      getServletContext().getRequestDispatcher("/nosession.jsp").forward(request, response);
      return;
    }

    String opName = null;
    StandardOperation op = null;
    try {
      // Get the name of the op from the request, and try to instantiate
      // the op class with the same name as the op.
      opName = request.getParameter("op");
      
      //this is the case that the request is from a multipart/form, e.g.: file upload
      if(fileItems != null && opName == null) {
      //Process the uploaded items
        Iterator iter = fileItems.iterator();
        while (iter.hasNext()) {
        FileItem item = (FileItem) iter.next();

        if (item.isFormField()) {
          
          if("op".equals(item.getFieldName())) {
            opName = item.getString();
            break;
          }
        } 
       } 
      }
      

      if (opName == null) {
        throw new ApiException("Unspecified operation");
      }
      else {
        try {
          Class opClass = Class.forName("com.ardais.bigr.pdc.op." + opName);
          Class constructorArgs[] =
            {
              Class.forName("javax.servlet.http.HttpServletRequest"),
              Class.forName("javax.servlet.http.HttpServletResponse"),
              Class.forName("javax.servlet.ServletContext")};
          Constructor constructor = opClass.getConstructor(constructorArgs);
          if (constructor != null) {
            Object args[] = { request, response, this.getServletContext()};
            op = (StandardOperation) constructor.newInstance(args);
          }
        }

        // Catch all of the exceptions that can occur when attempting to
        // reflectively find the op class and its constructor, and trying
        // to instantiate the object via the constructor.  Intentionally do
        // nothing, since we'll report an error below if there was a problem.
        catch (ClassNotFoundException e1) {
        }
        catch (NoSuchMethodException e2) {
        }
        catch (InvocationTargetException e) {
        }
        catch (InstantiationException e) {
        }
        catch (IllegalAccessException e) {
        }
      }

      // If we've instantiated an op, then invoke it.
      if (op != null) {
        
        if(fileItems != null)
        { //invoke multipart/form, now, only happen in donor
          op.preInvoke(fileItems);
        }
        else op.invoke();
      }
      // If we could not find the op, then throw an exception.
      else {
        throw new ApiException("Could not find op class '" + opName + "'");
      }
    }
    catch (Exception e) {
      // If we get an exception, attempt to report it via the ReportError op.
      // If that op throws an exception, then throw it back to the servlet engine
      // and let it report the exception.
      //
      if (!ApiLogger.isIgnorableException(e)) {
        ApiLogger.log(e);
        ReportError err = new ReportError(request, response, this.getServletContext());
        err.setFromOp((opName == null) ? "null" : opName);
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
   * Process all GET requests by dispatching to the appropriate op.
   * 
   * @param  request  the servlet request
   * @param  response  the servlet response
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    doDispatch(request, response, null);
  }
  /**
   * Process all POST requests by dispatching to the appropriate op.
   * 
   * @param  request  the servlet request
   * @param  response  the servlet response
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    doDispatch(request, response, null);
  }
}
