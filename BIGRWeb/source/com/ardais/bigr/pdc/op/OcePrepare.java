package com.ardais.bigr.pdc.op;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.pdc.helpers.OceHelper;
import com.ardais.bigr.pdc.oce.Oce;
import com.ardais.bigr.pdc.oce.OceHome;
import com.ardais.bigr.util.EjbHomes;

/**
 * @author Nagraj
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OcePrepare extends StandardOperation {

	/**
	 * Creates an <code>OcePrepare</code>.
	 *
	 * @param  req  the HTTP servlet request
	 * @param  res  the HTTP servlet response
	 * @param  ctx  the servlet context
	 */
	public OcePrepare(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}

	/**
	 * @see com.ardais.bigr.pdc.op.StandardOperation#invoke()
	 */
	public void invoke() {

		HttpSession session = request.getSession(false);
		OceHelper helper = (OceHelper) request.getAttribute("helper");
		boolean haveHelper = (helper != null);

		if (haveHelper) {
			request.setAttribute("helper", helper);
			forward("/hiddenJsps/ddc/oce/OceBlank.jsp");
		} else {
			try {
        OceHome home = (OceHome) EjbHomes.getHome(OceHome.class);
        Oce oceBean = home.create();
				session.setAttribute("TableColumns", oceBean.getTableColumnNames());
				helper = new OceHelper(request);
				session.setAttribute("helper", helper);
				forward("/hiddenJsps/ddc/oce/OceMain.jsp");
			} catch (Exception e) {
				ApiFunctions.throwAsRuntimeException(e);
			}
		}

	}
  public void preInvoke(Collection fileItems) {
    //method for potential file attachment, do nothing for now ;
    
  }
}
