package com.ardais.bigr.pdc.op;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.ConsentKey;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.helpers.ClinicalDataHelper;
import com.ardais.bigr.pdc.javabeans.ClinicalDataData;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.security.WebSecurityManager;
import com.ardais.bigr.util.EjbHomes;

/**
 * Prepares information for the JSP that allows editing of raw clinical data.
 */
public class ClinicalDataPrepare extends StandardOperation {
	/**
	 * Creates a <code>ClinicalDataPrepare</code>.
	 *
	 * @param  req  the HTTP servlet request
	 * @param  res  the HTTP servlet response
	 * @param  ctx  the servlet context
	 */
	public ClinicalDataPrepare(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 * Looks up the clinical data, if an existing clinical data, sets appropriate 
	 * request attributes, and forwards to the clinical data editing JSP.
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

		ClinicalDataHelper helper =
			(ClinicalDataHelper) request.getAttribute("helper");
		boolean haveHelper = (helper != null);
		if (!haveHelper)
			helper = new ClinicalDataHelper(request);

		// Make sure that the required parameters are present, and throw an
		// exception if they're not.
		String clinicalDataId = helper.getClinicalDataId();
		if (clinicalDataId == null) {
			if (ApiFunctions.isEmpty(helper.getArdaisId()))
				throw new ApiException("Required parameter 'ardaisId' is not present.");

			if (ApiFunctions.isEmpty(helper.getConsentId()))
				throw new ApiException("Required parameter 'consentId' is not present.");

			if (ApiFunctions.isEmpty(helper.getCategory()))
				throw new ApiException("Required parameter 'category' is not present.");
		}

		try {
      String donorImportedYN = helper.getDonorImportedYN();
      DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = ddcDonorHome.create();
			if ((!haveHelper) && (clinicalDataId != null)) {
        ClinicalDataData cdd = helper.getDataBean();
				ClinicalDataData clinicalData =
					donorOperation.getClinicalData(cdd);
				helper = new ClinicalDataHelper(clinicalData);
        //carry forward the information about whether the donor was imported
        helper.setDonorImportedYN(donorImportedYN);
			}
      //get the alias values for both the donor and the case for display on the resulting page
      //we don't check if the donor/case is imported because someday we may provide aliases for
      //non-imported objects and this code will automatically handle it
      DonorData dd = new DonorData();
      dd.setArdaisId(helper.getArdaisId());
      dd = donorOperation.getDonorProfile(dd);
      helper.setDonorCustomerId(dd.getCustomerId());
      ConsentAccessBean consentBean = new ConsentAccessBean(new ConsentKey(helper.getConsentId()));
      ConsentData consent = new ConsentData(consentBean);
      helper.setConsentCustomerId(consent.getCustomerId());
		} catch (Exception e) {
			ApiFunctions.throwAsRuntimeException(e);
		}

		// Forward to the clinical data edit page.
		request.setAttribute("helper", helper);
		forward("/hiddenJsps/ddc/data/ClinicalData.jsp");
	}
  public void preInvoke(Collection fileItems) {
    //method for potential file attachment, do nothing for now ;
    
  }
}
