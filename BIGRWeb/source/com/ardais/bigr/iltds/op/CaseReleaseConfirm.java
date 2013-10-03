package com.ardais.bigr.iltds.op;

import java.io.IOException;
import java.util.Enumeration;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.databeans.PolicyData;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.util.EjbHomes;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

/**
 * Insert the type's description here.
 * Creation date: (1/17/2001 6:19:41 PM)
 * @author: Jake Thompson
 */
public class CaseReleaseConfirm
	extends com.ardais.bigr.iltds.op.StandardOperation {
	/**
	 * CaseReleaseConfirm constructor comment.
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param res javax.servlet.http.HttpServletResponse
	 * @param ctx javax.servlet.ServletContext
	 */
	public CaseReleaseConfirm(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	public boolean checkGeoLocation(String consentID) {
		String user = (String) request.getSession(false).getAttribute("user");
		return IltdsUtils.checkGeoLocation(consentID, user);

	}
	/**
	 * Insert the method's description here.
	 * Creation date: (1/26/2001 4:06:01 PM)
	 * @return boolean
	 * @param consentID java.lang.String
	 */
	public boolean consentIDExists(String consentID) {
		ConsentAccessBean myConsent;

		try {

			myConsent = new ConsentAccessBean();
			Enumeration myEnum = myConsent.findByConsentID(consentID);
			if (!myEnum.hasMoreElements()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ReportError err = new ReportError(request, response, servletCtx);
			err.setFromOp(this.getClass().getName());
			err.setErrorMessage(e.toString());
			try {
				err.invoke();
			} catch (Exception _axxx) {
			}
			return false;
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2/13/2001 10:24:08 AM)
	 * @return boolean
	 * @param consentID java.lang.String
	 */
	public boolean consentPulled(String consentID) {
		ConsentAccessBean consent;
		AccessBeanEnumeration enum1;

		try {
			consent = new ConsentAccessBean();
			enum1 = (AccessBeanEnumeration) consent.findByConsentID(consentID);
			if (enum1.hasMoreElements()) {
				if (((ConsentAccessBean) enum1.nextElement())
					.getConsent_pull_datetime()
					== null) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}

		} catch (javax.ejb.ObjectNotFoundException onfe) {
			onfe.printStackTrace();
			ReportError err = new ReportError(request, response, servletCtx);
			err.setFromOp(this.getClass().getName());
			err.setErrorMessage(onfe.toString());
			try {
				err.invoke();
			} catch (Exception _axxx) {
			}
			return false;
		} catch (javax.ejb.FinderException fe) {
			fe.printStackTrace();
			ReportError err = new ReportError(request, response, servletCtx);
			err.setFromOp(this.getClass().getName());
			err.setErrorMessage(fe.toString());
			try {
				err.invoke();
			} catch (Exception _axxx) {
			}
			return false;
		} catch (java.rmi.RemoteException re) {
			re.printStackTrace();
			ReportError err = new ReportError(request, response, servletCtx);
			err.setFromOp(this.getClass().getName());
			err.setErrorMessage(re.toString());
			try {
				err.invoke();
			} catch (Exception _axxx) {
			}
			return false;
		} catch (NamingException ne) {
			ne.printStackTrace();
			ReportError err = new ReportError(request, response, servletCtx);
			err.setFromOp(this.getClass().getName());
			err.setErrorMessage(ne.toString());
			try {
				err.invoke();
			} catch (Exception _axxx) {
			}
			return false;
		} catch (javax.ejb.CreateException ce) {
			ce.printStackTrace();
			ReportError err = new ReportError(request, response, servletCtx);
			err.setFromOp(this.getClass().getName());
			err.setErrorMessage(ce.toString());
			try {
				err.invoke();
			} catch (Exception _axxx) {
			}
			return false;
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (1/17/2001 6:19:41 PM)
	 */
	public void invoke() throws IOException, Exception {

		try {
			String consentID = request.getParameter("consentID_1");
			String diseaseType = request.getParameter("DiseaseType");
			String otherDisease = request.getParameter("OtherDiagnosis");

			if (consentID == null || consentID.equals("")) {
				retry("Please enter a Case ID.");
				return;
			}

      if (
				!consentID.equals(request.getParameter("consentID_2"))) {
				retry("Case ID's do not match");
				return;
			} 
      if ( (ValidateIds.validateId(consentID, ValidateIds.TYPESET_CASE, false) == null)) {
				  retry("Please enter a valid Case ID.");
          return;
			} 
      if (!consentIDExists(consentID)) {
				retry("Case not in system");
				return;
			}  

      // MR 8021: check to see if release is required by the policy
      PolicyData policy = IltdsUtils.getPolicyForConsent(consentID);
      if (!"Y".equalsIgnoreCase(policy.getReleaseRequired())) {
        retry("Release is not required for this case.");
        return;
      }
      if (released(consentID)) {
				retry("Case already Released");
				return;
			}  
      if (consentPulled(consentID)) {
				retry("Case is pulled, cannot release.");
				return;
			} 
      if (diseaseType == null || diseaseType.equals("")) {
				retry("Please enter a diagnosis.");
				return;
			}  
      ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
      ListGenerator list = home.create();
      if (
				diseaseType.equals(list.lookupDiseaseCode("Other diagnosis"))
					&& (otherDisease == null || otherDisease.trim().equals(""))) {
				retry("Please enter text for Other Pathology Diagnosis.");
				return;
			}  
      if (!checkGeoLocation(consentID)) {
				retry("Case ID belongs to a different donor institution.");
				return;
			} 
      
				request.setAttribute(
					"diagnosis",
					list.lookupDiseaseName(diseaseType));
				request.setAttribute("myError", "Confirm");
				servletCtx.getRequestDispatcher(
					"/hiddenJsps/iltds/caseRelease/caseReleaseConfirm.jsp").forward(
					request,
					response);

		} catch (Exception e) {
			e.printStackTrace();
			ReportError err = new ReportError(request, response, servletCtx);
			err.setFromOp(this.getClass().getName());
			err.setErrorMessage(e.toString());
			try {
				err.invoke();
			} catch (Exception _axxx) {
			}
			return;
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (1/26/2001 4:57:23 PM)
	 * @return boolean
	 * @param consentID java.lang.String
	 */
	public boolean released(String consentID) {

		try {
			ConsentAccessBean myConsent = new ConsentAccessBean();
			//ConsentAccessBeanTable myConsentTable = new ConsentAccessBeanTable();
			AccessBeanEnumeration enum1 =
				(AccessBeanEnumeration) myConsent.findByConsentID(consentID);

			while (enum1.hasMoreElements()) {
				ConsentAccessBean myConsentAB =
					(ConsentAccessBean) enum1.nextElement();
				if (myConsentAB.getConsent_release_datetime() != null) {
					return true;
				}
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ReportError err = new ReportError(request, response, servletCtx);
			err.setFromOp(this.getClass().getName());
			err.setErrorMessage(e.toString());
			try {
				err.invoke();
			} catch (Exception _axxx) {
			}
			return false;
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (1/26/2001 4:06:47 PM)
	 * @param myError java.lang.String
	 */
	public void retry(String myError) {
		try {
			request.setAttribute("myError", myError);
			servletCtx
				.getRequestDispatcher("/hiddenJsps/iltds/caseRelease/caseRelease.jsp")
				.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			ReportError err = new ReportError(request, response, servletCtx);
			err.setFromOp(this.getClass().getName());
			err.setErrorMessage(e.toString());
			try {
				err.invoke();
			} catch (Exception _axxx) {
			}
			return;
		}
	}
}
