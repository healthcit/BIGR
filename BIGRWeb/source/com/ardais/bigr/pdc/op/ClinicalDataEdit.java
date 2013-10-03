package com.ardais.bigr.pdc.op;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.helpers.ClinicalDataHelper;
import com.ardais.bigr.pdc.javabeans.ClinicalDataData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.security.WebSecurityManager;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;

/**
 */
public class ClinicalDataEdit extends StandardOperation {
	/**
	 * Creates a <code>ClinicalDataEdit</code>.
	 *
	 * @param  req  the HTTP servlet request
	 * @param  res  the HTTP servlet response
	 * @param  ctx  the servlet context
	 */
	public ClinicalDataEdit(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 */
	public void invoke() {
		// Check that the user has permission to perform this transaction.
		HttpSession session = request.getSession(false);
		if (!WebSecurityManager.hasPrivilege(session, SecurityInfo.PRIV_CLINICAL_DATA_EXTRACTION)
        && !WebSecurityManager.hasPrivilege(session, SecurityInfo.PRIV_DATA_IMPORT_CLINICAL_DATA_EXT)) {
			String account = (String) session.getAttribute("account");
			String user = (String) session.getAttribute("user");
			throw new ApiException(
				"SECURITY ALERT ("
					+ account
					+ "/"
					+ user
					+ "): You do not have permission to perform the Clinical Data Extraction transaction.");
		}

		ClinicalDataHelper helper = new ClinicalDataHelper(request);

		// Save the clinical data.
		try {
			String user =
				(String) request.getSession(false).getAttribute("user");
			ClinicalDataData clinicalData = helper.getDataBean();
			clinicalData.setLastUpdateUser(user);
			if (helper.isNew()) {
				clinicalData.setCreateUser(user);
        SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
        DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
        DDCDonor donorOperation = ddcDonorHome.create();
				clinicalData = donorOperation.createClinicalData(clinicalData, secInfo);
			} else {
        SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
        DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
        DDCDonor donorOperation = ddcDonorHome.create();
				clinicalData = donorOperation.updateClinicalData(clinicalData, secInfo);
			}
		} catch (Exception e) {
			ApiFunctions.throwAsRuntimeException(e);
		}

		// Forward to the clinical data summary page.
		(new ClinicalDataSummaryPrepare(request, response, servletCtx))
			.invoke();
	}
  
  public void preInvoke(Collection fileItems) {
    //method for potential file attachment, do nothing for now ;
    
  }
}
