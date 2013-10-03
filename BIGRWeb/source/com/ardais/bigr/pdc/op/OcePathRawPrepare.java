package com.ardais.bigr.pdc.op;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.pdc.beans.DDCPathology;
import com.ardais.bigr.pdc.beans.DDCPathologyHome;
import com.ardais.bigr.pdc.helpers.PathReportHelper;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.util.EjbHomes;

/**
 * @author Nagraj
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OcePathRawPrepare extends StandardOperation {

	/**
 	* Creates a <code>PathRawPrepare</code>.
 	*
	* @param  req  the HTTP servlet request
	* @param  res  the HTTP servlet response
 	* @param  ctx  the servlet context
 	*/
	public OcePathRawPrepare(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
	}

	/**
	 * @see com.ardais.bigr.pdc.op.StandardOperation#invoke()
	 */
	public void invoke() {
		
		// Determine whether this is a popup window or not.
		String context = ("true".equals(request.getParameter("popup"))) ? "popup" : null;
		
		// Get the path report helper.
		PathReportHelper helper = (PathReportHelper)request.getAttribute("helper");
		boolean haveHelper = (helper != null);
		if (!haveHelper)
			helper = new PathReportHelper(request);
			
		// If we don't have a Line id, it is not possible to get pathology report.
		String lineId = request.getParameter("lineId");		
		if (ApiFunctions.isEmpty(lineId)) {
			throw new ApiException("Required parameter 'Line Id' is not present.");	
		}
		// If we do have a line id and don't already have the 
		// helper, then look up the raw path report.
		else if (!haveHelper) {
			try {
        DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
        DDCPathology pathAccessBean = pathHome.create();
				PathReportData pathData = pathAccessBean.getRawPathReport(lineId);
				helper = new PathReportHelper(pathData);	
			} catch (Exception e) {
				throw new ApiException(e);
			}		 
		}
		
		// Determine whether this is a new or existing path report, if we have not
		// figured it out yet.
		if (context == null)
			context = (ApiFunctions.isEmpty(helper.getRawPathReport())) ? "new" : "existing";
		
		// Forward to the raw path report page.
		request.setAttribute("helper", helper);
		request.setAttribute("context", context);
		forward("/hiddenJsps/ddc/path/PathRaw.jsp");
	}
  public void preInvoke(Collection fileItems) {
    //method for potential file attachment, do nothing for now ;
    
  }
}
