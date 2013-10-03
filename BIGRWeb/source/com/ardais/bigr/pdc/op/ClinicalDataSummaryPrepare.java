package com.ardais.bigr.pdc.op;

import java.util.Collection;
import java.util.List;

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
import com.ardais.bigr.pdc.helpers.ClinicalDataSummaryHelper;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.pdc.javabeans.ClinicalDataData;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.security.WebSecurityManager;
import com.ardais.bigr.util.EjbHomes;

/**
 */
public class ClinicalDataSummaryPrepare extends StandardOperation {
	/**
	 * Create a new <code>ClinicalDataSummaryPrepare</code>.
	 *
	 * @param  req  the HTTP servlet request
	 * @param  res  the HTTP servlet response
	 * @param  ctx  the servlet context
	 */
	public ClinicalDataSummaryPrepare(
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

		// Get the helper.
		ClinicalDataSummaryHelper helper =
			new ClinicalDataSummaryHelper(request);
    // get the last edited category, so we can preserve it later
    String lastEditedCategory = helper.getLastEditedCategory();

		// Make sure that the required parameters are present, and throw an
		// exception if they're not.	
		if (ApiFunctions.isEmpty(helper.getArdaisId()))
			throw new ApiException("Required parameter 'ardaisId' is not present.");

		if (ApiFunctions.isEmpty(helper.getConsentId()))
			throw new ApiException("Required parameter 'consentId' is not present.");

		// Get the clinical data for the case.
		try {
      ClinicalDataData cdd = helper.getDataBean();
      DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = ddcDonorHome.create();
			List clinicalData =
				donorOperation.getClinicalDataList(cdd);
      String donorImportedYN = helper.getDonorImportedYN();
			if (clinicalData.size() > 0) {
        helper = new ClinicalDataSummaryHelper(clinicalData);
        //carry forward the information about whether the donor was imported
        helper.setDonorImportedYN(donorImportedYN);
			}
      helper.setLastEditedCategory(lastEditedCategory);
      
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
    		
    //store the ardais id and case ids in the session for use in subsequent DDC operations
    //we pass "true" for the last parameter because if the user is modifying the same
    //donor/case as the ones currently saved in the session we don't want to erase any saved 
    //sample id.  If the user is modifying a different donor/case then any sample information 
    //will be erased regardless of the value of the last parameter.
		PdcUtils.storeLastUsedDonorCaseAndSample(request, helper.getArdaisId(), helper.getDonorCustomerId(), 
        helper.getConsentId(), helper.getConsentCustomerId(), null, null, true);

		// Forward to the clinical data summary page.
		request.setAttribute("helper", helper);
		forward("/hiddenJsps/ddc/data/ClinicalDataSummary.jsp");
	}
  public void preInvoke(Collection fileItems) {
    //method for potential file attachment, do nothing for now ;
    
  }
  
}
