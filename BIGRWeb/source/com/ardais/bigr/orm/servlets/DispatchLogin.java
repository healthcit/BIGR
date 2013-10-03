package com.ardais.bigr.orm.servlets;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;

import com.ardais.bigr.orm.op.*;
import com.ardais.bigr.api.*;

public class DispatchLogin extends javax.servlet.http.HttpServlet {

    public DispatchLogin() {
        super();
    }

    public StandardOperation createApplicableOperation(
        String opname,
        HttpServletRequest request,
        HttpServletResponse response) {

        // Session Validation.  The ops that this dispatcher handles are all
        // special in that the user is not required to be logged in to perform
        // them.
        //
        if (!(com
            .ardais
            .bigr
            .orm
            .helpers
            .FormLogic
            .commonPageActions(
                request,
                response,
                this.getServletContext(),
                "N"))) { // "N" => user not required to be logged in
            return null;
        }

        if (opname.equals("Ping")) {
            return (new Ping(request, response, this.getServletContext()));
        }
         // END LOGIN SCREEN
        else if (opname.equals("Logout")) {
            return (
                new InvalidateSession(
                    request,
                    response,
                    this.getServletContext()));
        }
        //Forgot Password
        else if (opname.equals("ForgotPasswordStart")) {
            return (
                new ForgotPasswordStart(
                    request,
                    response,
                    this.getServletContext()));
        }
        else if (opname.equals("GetPasswordQuestion")) {
            return (
                new GetPasswordQuestion(
                    request,
                    response,
                    this.getServletContext()));
        }
        else if (opname.equals("ForgotPasswordAnswer")) {
            return (
                new ForgotPasswordAnswer(
                    request,
                    response,
                    this.getServletContext()));
        }

        //admin
        else if (opname.equals("RefreshCaches")) {
            return (
                new RefreshCaches(request, response, this.getServletContext()));
        }

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

            if (ApiFunctions.isEmpty(myOp)) {
                throw new IllegalArgumentException("The op parameter was not specified");
            }

            StandardOperation op =
                createApplicableOperation(myOp, request, response);

            if (op == null) {
                this.getServletContext().getRequestDispatcher(
                    "/nosession.jsp").forward(
                    request,
                    response);

                return;
            }

            op.invoke();
        }
        catch (Exception e) {
            ApiLogger.log(e);
            ReportError err =
                new ReportError(request, response, this.getServletContext());
            err.setFromOp(myOp);
            err.setErrorMessage(e.toString());
            try {
                err.invoke();
            }
            catch (IOException ioe) {
                ApiLogger.log(
                    "Error while attempting to report an exception",
                    ioe);
                throw ioe;
            }
            catch (Exception e1) {
                ApiLogger.log(
                    "Error while attempting to report an exception",
                    e1);
                throw new ServletException(e1);
            }
        }
    }
}
