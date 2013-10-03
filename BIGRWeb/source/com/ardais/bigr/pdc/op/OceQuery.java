package com.ardais.bigr.pdc.op;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.pdc.helpers.OceHelper;
import com.ardais.bigr.pdc.javabeans.OceData;
import com.ardais.bigr.pdc.oce.Oce;
import com.ardais.bigr.pdc.oce.OceHome;
import com.ardais.bigr.pdc.oce.util.OceUtil;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.iltds.helpers.FormLogic;

/**
 * @author Nagraj
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OceQuery extends StandardOperation {

/**
 * Creates a <code>OceQuery</code>.
 *
 * @param  req  the HTTP servlet request
 * @param  res  the HTTP servlet response
 * @param  ctx  the servlet context
 */
public OceQuery(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
		super(req, res, ctx);
	}

	/**
	 * @see com.ardais.bigr.pdc.op.StandardOperation#invoke()
	 */
public void invoke() {
	// Get the path report helper.
	OceHelper helper = (OceHelper) request.getAttribute("helper");
	boolean haveHelper = (helper != null);
	if (!haveHelper)
		helper = new OceHelper(request);

	if (helper.isValid()) {
		try {
			OceData databean = helper.getDataBean();
      OceHome home = (OceHome) EjbHomes.getHome(OceHome.class);
      Oce oceBean = home.create();
			databean = oceBean.getOceData(databean);
			helper = new OceHelper(databean);
			request.setAttribute("oceHelper", helper);
			if (ApiFunctions.safeEquals(OceUtil.OCE_STATUS_ALL_IND, helper.getStatus())||
				ApiFunctions.safeEquals(OceUtil.OCE_STATUS_FIXED_IND, helper.getStatus())||
				ApiFunctions.safeEquals(OceUtil.OCE_STATUS_OBSOLETE_IND, helper.getStatus())) 
				forward("/hiddenJsps/ddc/oce/OceUpdateSummary.jsp");
			else
				forward("/hiddenJsps/ddc/oce/OceSearchResults.jsp");
		} catch (Exception e) {
			throw new ApiException(e);
		}
	} else {
		//Validation failed. Forward back to main page with errors.  Set the error flag on the message
    //helper so that all problems are shown, not just the first one.
    helper.getMessageHelper().setError(true);
		request.setAttribute("helper", helper);
		new OcePrepare(request, response, servletCtx).invoke();		
	}
}
public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
}
