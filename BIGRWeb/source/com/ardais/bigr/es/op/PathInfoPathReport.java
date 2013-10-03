package com.ardais.bigr.es.op;

import com.ardais.bigr.pdc.helpers.*;
import com.ardais.bigr.pdc.javabeans.*;
import com.ardais.bigr.pdc.beans.*;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.es.*;
import com.ardais.bigr.es.beans.*;
import com.ardais.bigr.es.helpers.*;
import com.ardais.bigr.es.op.*;
import com.ardais.bigr.es.servlets.*;

import java.io.*;
import java.io.PrintWriter;
import java.util.*;
import javax.naming.*;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 * Insert the type's description here.
 * Creation date: (9/18/2001 12:21:59 PM)
 * @author: Jake Thompson
 */
public class PathInfoPathReport extends com.ardais.bigr.es.op.StandardOperation {
    /**
     * PathInfoPathReportNew constructor comment.
     * @param req javax.servlet.http.HttpServletRequest
     * @param res javax.servlet.http.HttpServletResponse
     * @param ctx javax.servlet.ServletContext
     */
    public PathInfoPathReport(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
        super(req, res, ctx);
    }
    /**
     * Insert the method's description here.
     * Creation date: (9/18/2001 12:21:59 PM)
     */
    public void invoke() throws Exception, IOException {
        //BIGRLibraryOperationAccessBean bigrOp = new BIGRLibraryOperationAccessBean();
        String pathOp = request.getParameter("op");
        String donor_id = request.getParameter("donor_id");
        String case_id = request.getParameter("case_id");
        request.setAttribute("donor_id", donor_id);
        String path_id = request.getParameter("path_id");

        try {
            DonorData donorData = new DonorData();
            donorData.setArdaisId(donor_id);
            DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
            DDCDonor remote = home.create();
            donorData = remote.buildDonorData(donorData);
            DonorHelper donorHelper = new DonorHelper(donorData);
            request.getSession().setAttribute("donor", donorHelper);

            if (path_id != null) {
                PathReportData pathData = new PathReportData();
                if (!"null".equalsIgnoreCase(path_id)) {
                    pathData.setPathReportId(path_id);
                    DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
                    DDCPathology pab = pathHome.create();
                    pathData = pab.buildPathReport(pathData);
                }
                PathReportHelper pathHelper =
                    new PathReportHelper(pathData);
                request.getSession().setAttribute("pathReport", pathHelper);

            }

            if (case_id != null) {
                ConsentHelper consent = null;
                for (int i = 0; i < donorHelper.getConsents().size(); i++) {
                    consent = (ConsentHelper) donorHelper.getConsents().get(i);
                    if (case_id.equals(consent.getConsentId())) {
                        request.getSession().setAttribute("consent", consent);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            ReportError err = new ReportError(request, response, servletCtx);
            err.setFromOp(this.getClass().getName());
            err.setErrorMessage(e.toString());
            err.invoke();
            return;
        }

        String pathPage = "";
        if (pathOp.equals("PathInfoStart")) {
            pathPage = "/hiddenJsps/es/pathInfo/DonorProfile.jsp";
        } else if (pathOp.equals("PathInfoDonorDX")) {
            pathPage = "/hiddenJsps/es/pathInfo/DonorDiagnosis.jsp";
        } else if (pathOp.equals("PathInfoDonorTreat")) {
            pathPage = "/hiddenJsps/es/pathInfo/DonorTreatment.jsp";
        } else if (pathOp.equals("PathInfoPathReport")) {
            pathPage = "/hiddenJsps/es/pathInfo/PathologyReport.jsp";
        }

        try {
            request.setAttribute("donor_id", donor_id);
            servletCtx.getRequestDispatcher(pathPage).forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            ReportError err = new ReportError(request, response, servletCtx);
            err.setFromOp(this.getClass().getName());
            err.setErrorMessage(e.toString());
            err.invoke();
            return;
        }

    }
}
