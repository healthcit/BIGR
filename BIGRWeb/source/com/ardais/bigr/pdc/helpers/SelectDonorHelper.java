package com.ardais.bigr.pdc.helpers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.javabeans.ConsentData;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;

/**
 */
public class SelectDonorHelper extends JspHelper {

	// Select Donor properties.
	private String _ardaisId;
	private String _op;
	private String _listHeader;
	private String _pathOp;
	private String _ctx;
	private String _pageTitle;
	private Map _donorLinkParams;
	private String _donorLinkText;
	private Map _donorAndCaseLinkParams;
	private String _donorAndCaseLinkText;
  private String _importedYN;
  private String _customerId;
/**
 * Create a new <code>SelectDonorHelper</code>.
 */
public SelectDonorHelper() {
	super();
}

/**
 * Create a new <code>SelectDonorHelper</code>.
 */
public SelectDonorHelper(HttpServletRequest request) {
	super();
	String context = request.getParameter(JspHelper.ID_CONTEXT);
	if (JspHelper.isEmpty(context)) {
		throw new ApiException("Required parameter '" + JspHelper.ID_CONTEXT + "' is not present.");
	}
	
	StringBuffer buff = new StringBuffer(50);
	buff.append("Select Donor");
	
	// Set the context attributes that indicate the transaction
	// that is to be initiated after the search.  Also set up any
	// links that we want to provide on the Select Donor page.
	if (context.equals(JspHelper.SEARCH_CONTEXT_DONOR_PROFILE)) {
    setOp("DonorProfileSummaryPrepare");
    setPathOp("");
    buff.append(" (Donor Information)");
		determineLinkInformation(request,context,getOp(),getPathOp());
	}	
	else if (context.equals(JspHelper.SEARCH_CONTEXT_PATH_FULL)) {
		setOp("CaseListPrepare");
		setPathOp("PathRawPrepare");
		buff.append(" (Path Report Full Text)");
		determineLinkInformation(request,context,getOp(),getPathOp());
	}	
	else if (context.equals(JspHelper.SEARCH_CONTEXT_PATH_ABSTRACT)) {
		setOp("CaseListPrepare");
		setPathOp("PathAbstractSummaryPrepare");
		buff.append(" (Path Report Abstraction)");
		determineLinkInformation(request,context,getOp(),getPathOp());
	}	
	else if (context.equals(JspHelper.SEARCH_CONTEXT_CLINICAL_DATA_EXTRACTION)) {
		setOp("CaseListPrepare");
		setPathOp("ClinicalDataSummaryPrepare");
		buff.append(" (Clinical Data Extraction)");
		determineLinkInformation(request,context,getOp(),getPathOp());
	}	
	else if (context.equals(JspHelper.SEARCH_CONTEXT_CASE_PROFILE_ENTRY)) {
		setOp("CaseListPrepare");
		setPathOp("CaseProfileNotesPrepare");
		buff.append(" (Case Profile Entry)");
		determineLinkInformation(request,context,getOp(),getPathOp());
	}
	
	//add the context to the helper so it's available to the code that's checking
	//access to the donor
	setCtx(context);
  
  //set the importedYN value
  setImportedYN(request.getParameter(JspHelper.ID_IMPORTED_YN));
  
  //set any Ardais and/or customer id values
  setArdaisId(request.getParameter(JspHelper.ID_ARDAIS_ID));
  setCustomerId(request.getParameter(JspHelper.ID_CUSTOMER_ID));
	
	//set the page title
  if ("Y".equalsIgnoreCase(getImportedYN())) {
    setPageTitle("Select Donor to Modify");    
  }
  else {
    setPageTitle(buff.toString());
  }
}

/**
 * Returns the donor id.
 *
 * @return  The donor id.
 */
public String getArdaisId() {
	return _ardaisId;
}
/**
 * Returns the header of the list of cases to be displayed for some of the
 * DDC transactions.
 *
 * @return  The list header.
 */
public String getListHeader() {
	return _listHeader;
}
/**
 * Returns the op to which control should be forwarded after the
 * donor is selected.
 *
 * @return  The next op.
 */
public String getOp() {
	return _op;
}
/**
 * Returns the op of the pathology transaction to which control 
 * should be forwarded after the donor is selected and the list
 * of cases is shown.
 *
 * @return  The pathology transaction op.
 */
public String getPathOp() {
	return _pathOp;
}

/**
 * Returns the ctx.
 * @return String
 */
public String getCtx() {
	return _ctx;
}

/**
 * Sets the donor id.
 *
 * @param  id  the donor id
 */
public void setArdaisId(String id) {
	_ardaisId = id;
}
/**
 * Sets the header of the list of cases to be displayed for some of the
 * DDC transactions.
 *
 * @param  header  The list header.
 */
public void setListHeader(String header) {
	_listHeader = header;
}
/**
 * Sets the op to which control should be forwarded after the
 * donor is selected.
 *
 * @param  op  The next op.
 */
public void setOp(String op) {
	_op = op;
}
/**
 * Sets the op of the pathology transaction to which control 
 * should be forwarded after the donor is selected and the list
 * of cases is shown.
 *
 * @param  op  The pathology transaction op.
 */
public void setPathOp(String op) {
	_pathOp = op;
}


//public String getMessages()
//{
//	return super.getMessages();
//}
/**
 * Sets the ctx.
 * @param ctx The ctx to set
 */
public void setCtx(String ctx) {
	_ctx = ctx;
}

	/**
	 * Returns the pageTitle.
	 * @return String
	 */
	public String getPageTitle() {
		return _pageTitle;
	}

	/**
	 * Sets the pageTitle.
	 * @param pageTitle The pageTitle to set
	 */
	public void setPageTitle(String pageTitle) {
		_pageTitle = pageTitle;
	}

	/**
	 * Returns the donorAndCaseLinkText.
	 * @return String
	 */
	public String getDonorAndCaseLinkText() {
		return _donorAndCaseLinkText;
	}

	/**
	 * Returns the donorLinkText.
	 * @return String
	 */
	public String getDonorLinkText() {
		return _donorLinkText;
	}

	/**
	 * Sets the donorAndCaseLinkText.
	 * @param donorAndCaseLinkText The donorAndCaseLinkText to set
	 */
	public void setDonorAndCaseLinkText(String donorAndCaseLinkText) {
		_donorAndCaseLinkText = donorAndCaseLinkText;
	}

	/**
	 * Sets the donorLinkText.
	 * @param donorLinkText The donorLinkText to set
	 */
	public void setDonorLinkText(String donorLinkText) {
		_donorLinkText = donorLinkText;
	}

	/**
	 * Returns the donorAndCaseLinkParams.
	 * @return Map
	 */
	public Map getDonorAndCaseLinkParams() {
		return _donorAndCaseLinkParams;
	}

	/**
	 * Returns the donorLinkParams.
	 * @return Map
	 */
	public Map getDonorLinkParams() {
		return _donorLinkParams;
	}

	/**
	 * Sets the donorAndCaseLinkParams.
	 * @param donorAndCaseLinkParams The donorAndCaseLinkParams to set
	 */
	private void setDonorAndCaseLinkParams(Map donorAndCaseLinkParams) {
		_donorAndCaseLinkParams = donorAndCaseLinkParams;
	}

	/**
	 * Sets the donorLinkParams.
	 * @param donorLinkParams The donorLinkParams to set
	 */
	private void setDonorLinkParams(Map donorLinkParams) {
		_donorLinkParams = donorLinkParams;
	}
	
	public void determineLinkInformation(HttpServletRequest request, String context, String op, String pathOp) {		
		//determine if a donor and/or case id is stored in the session.
		String donorId = null;
    String donorAlias = null;
		String caseId = null;
    String caseAlias = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
      donorId = (String) session.getAttribute(Constants.MOST_RECENT_DONOR_ID);
      donorAlias = (String) session.getAttribute(Constants.MOST_RECENT_DONOR_ALIAS);
      caseId = (String)session.getAttribute(Constants.MOST_RECENT_CASE_ID);
      caseAlias = (String)session.getAttribute(Constants.MOST_RECENT_CASE_ALIAS);
		}		
	
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
		// Set up any links that we want to provide on the Select Donor page.
		if (context.equals(JspHelper.SEARCH_CONTEXT_DONOR_PROFILE)) {
			//if there's a previously selected donor, provide a link to use them
      //Only provide the link if the user has the appropriate privilege (determined
      //by whether the donor is imported or not)
      boolean showLink = false;
      if (!ApiFunctions.isEmpty(donorId)) {
        donorId = donorId.trim();
        if (donorId.substring(0, 2).equalsIgnoreCase(ValidateIds.PREFIX_DONOR_IMPORTED))  {
          if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_DATA_IMPORT_MODIFY_DONOR)) {
            showLink = true;
          }
        }
        else {
          if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_DONOR_PROFILE)) {
            showLink = true;
          }
        }
      }
			if (showLink) {
				HashMap _donorLinkParams = new HashMap();
				_donorLinkParams.put("op", op);
				_donorLinkParams.put("pathOp", pathOp);
				_donorLinkParams.put("ardaisId", donorId);
        StringBuffer donorLinkTextBuffer = new StringBuffer(50);
        donorLinkTextBuffer.append("Select Donor ");
        donorLinkTextBuffer.append(donorId);
        if (donorId.substring(0, 2).equalsIgnoreCase(ValidateIds.PREFIX_DONOR_IMPORTED))	{
          _donorLinkParams.put("importedYN", "Y");
          if (!ApiFunctions.isEmpty(donorAlias)) {
            donorLinkTextBuffer.append(" (");
            donorLinkTextBuffer.append(donorAlias);
            donorLinkTextBuffer.append(")");
          }
        }
				setDonorLinkParams(_donorLinkParams);
				setDonorLinkText(donorLinkTextBuffer.toString());
				//there is no case link for donor profile, so null out that data
				setDonorAndCaseLinkParams(null);
				setDonorAndCaseLinkText(null);
			}
			else {
				setDonorLinkParams(null);
				setDonorLinkText(null);
				setDonorAndCaseLinkParams(null);
				setDonorAndCaseLinkText(null);
			}
		}	
		else if (context.equals(JspHelper.SEARCH_CONTEXT_PATH_FULL) ||
				  context.equals(JspHelper.SEARCH_CONTEXT_PATH_ABSTRACT)) {
      //if there's a previously selected donor, provide a link to use them
      //Only provide the link if the user has the appropriate privilege (determined
      //by whether the donor is imported or not)
      boolean showLink = false;
      if (!ApiFunctions.isEmpty(donorId)) {
        donorId = donorId.trim();
        if (donorId.substring(0, 2).equalsIgnoreCase(ValidateIds.PREFIX_DONOR_IMPORTED))  {
          if (context.equals(JspHelper.SEARCH_CONTEXT_PATH_FULL) &&
              securityInfo.isHasPrivilege(SecurityInfo.PRIV_DATA_IMPORT_PATH_REPORT_FULL)) {
            showLink = true;
          }
          if (context.equals(JspHelper.SEARCH_CONTEXT_PATH_ABSTRACT) &&
              securityInfo.isHasPrivilege(SecurityInfo.PRIV_DATA_IMPORT_PATH_REPORT_ABS)) {
            showLink = true;
          }
        }
        else {
          if (context.equals(JspHelper.SEARCH_CONTEXT_PATH_FULL) &&
              securityInfo.isHasPrivilege(SecurityInfo.PRIV_PATH_REPORT_FULL)) {
            showLink = true;
          }
          if (context.equals(JspHelper.SEARCH_CONTEXT_PATH_ABSTRACT) &&
              securityInfo.isHasPrivilege(SecurityInfo.PRIV_PATH_REPORT_ABSTRACT)) {
            showLink = true;
          }
        }
      }
      if (showLink) {
				HashMap _donorLinkParams = new HashMap();
				_donorLinkParams.put("op", op);
				_donorLinkParams.put("pathOp", pathOp);
				_donorLinkParams.put("ardaisId", donorId);		
        if (donorId.substring(0, 2).equalsIgnoreCase(ValidateIds.PREFIX_DONOR_IMPORTED)) {
          _donorLinkParams.put("importedYN", "Y");
        }
				setDonorLinkParams(_donorLinkParams);
        StringBuffer donorLinkTextBuffer = new StringBuffer(50);
        donorLinkTextBuffer.append("Select Donor ");
        donorLinkTextBuffer.append(donorId);
        if (donorId.substring(0, 2).equalsIgnoreCase(ValidateIds.PREFIX_DONOR_IMPORTED))  {
          if (!ApiFunctions.isEmpty(donorAlias)) {
            donorLinkTextBuffer.append(" (");
            donorLinkTextBuffer.append(donorAlias);
            donorLinkTextBuffer.append(")");
          }
        }
        setDonorLinkText(donorLinkTextBuffer.toString());
				//if there's a previously selected case, provide a link to use it
				if (caseId != null) {
					HashMap _donorAndCaseLinkParams = new HashMap();
					try {
						// Get the summary of the donor's cases.
            DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
            DDCDonor donorOperation = ddcDonorHome.create();
						DonorData donor = donorOperation.getDonorCaseSummary(donorId,false);
						//find the consent that matches the one we're looking for and
						//use it to set the map entries
						Iterator iterator = donor.getConsents().iterator();
						while (iterator.hasNext()) {
							ConsentData consent = (ConsentData)iterator.next();
							if (consent.getConsentId().equalsIgnoreCase(caseId)) {
								_donorAndCaseLinkParams.put("pathReportId",consent.getPathReportId());
								_donorAndCaseLinkParams.put("consentId",caseId);
								_donorAndCaseLinkParams.put("ardaisId",donorId);
                if (donorId.substring(0, 2).equalsIgnoreCase(ValidateIds.PREFIX_DONOR_IMPORTED)) {
                  _donorAndCaseLinkParams.put("donorImportedYN", "Y");
                }
								_donorAndCaseLinkParams.put("op",pathOp);
								break;
							}
						}
					}
					catch (Exception e) {
						throw new ApiException(e);
					}
					setDonorAndCaseLinkParams(_donorAndCaseLinkParams);
          StringBuffer donorAndCaseLinkTextBuffer = new StringBuffer(100);
          donorAndCaseLinkTextBuffer.append("Select Donor ");
          donorAndCaseLinkTextBuffer.append(donorId);
          if (donorId.substring(0, 2).equalsIgnoreCase(ValidateIds.PREFIX_DONOR_IMPORTED))  {
            if (!ApiFunctions.isEmpty(donorAlias)) {
              donorAndCaseLinkTextBuffer.append(" (");
              donorAndCaseLinkTextBuffer.append(donorAlias);
              donorAndCaseLinkTextBuffer.append(")");
            }
          }
          donorAndCaseLinkTextBuffer.append(" and Case ");
          donorAndCaseLinkTextBuffer.append(caseId);
          if (caseId.substring(0, 2).equalsIgnoreCase(ValidateIds.PREFIX_CASE_IMPORTED))  {
            if (!ApiFunctions.isEmpty(caseAlias)) {
              donorAndCaseLinkTextBuffer.append(" (");
              donorAndCaseLinkTextBuffer.append(caseAlias);
              donorAndCaseLinkTextBuffer.append(")");
            }
          }
          setDonorAndCaseLinkText(donorAndCaseLinkTextBuffer.toString());
				}
				else {
					setDonorAndCaseLinkParams(null);
					setDonorAndCaseLinkText(null);
				}
			}
			else {
				setDonorLinkParams(null);
				setDonorLinkText(null);
				setDonorAndCaseLinkParams(null);
				setDonorAndCaseLinkText(null);
			}
		}	
		else if (context.equals(JspHelper.SEARCH_CONTEXT_CLINICAL_DATA_EXTRACTION) ||
				  context.equals(JspHelper.SEARCH_CONTEXT_CASE_PROFILE_ENTRY)) {
			//if there's a previously selected donor, provide a link to use them
			//this op is only for linked cases, so don't include the links if the
			//last donor was unlinked. Only show an imported donor if they have one 
      //or more linked cases.      
      //Only provide the link if the user has the appropriate privilege (determined
      //by whether the donor is imported or not)
      boolean showLink = false;
      if (!ApiFunctions.isEmpty(donorId)) {
        donorId = donorId.trim();
        if (donorId.substring(0, 2).equalsIgnoreCase(ValidateIds.PREFIX_DONOR_IMPORTED)) {
          boolean linked = false;
          try {
            DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
            DDCDonor donorBean = ddcDonorHome.create();
            linked = !ApiFunctions.isEmpty(donorBean.getDonorCaseSummary(donorId, true).getConsents());
          }
          catch (Exception e) {
            throw new ApiException(e);
          }
          if (linked) {
            if (context.equals(JspHelper.SEARCH_CONTEXT_CLINICAL_DATA_EXTRACTION) &&
                securityInfo.isHasPrivilege(SecurityInfo.PRIV_DATA_IMPORT_CLINICAL_DATA_EXT)) {
              showLink = true;
            }
            if (context.equals(JspHelper.SEARCH_CONTEXT_CASE_PROFILE_ENTRY) &&
                securityInfo.isHasPrivilege(SecurityInfo.PRIV_DATA_IMPORT_CASE_PROFILE_ENTRY)) {
              showLink = true;
            }
          }
        }
        else if (donorId.substring(0, 2).equals(ValidateIds.PREFIX_DONOR_LINKED)) {
          if (context.equals(JspHelper.SEARCH_CONTEXT_CLINICAL_DATA_EXTRACTION) &&
              securityInfo.isHasPrivilege(SecurityInfo.PRIV_CLINICAL_DATA_EXTRACTION)) {
            showLink = true;
          }
          if (context.equals(JspHelper.SEARCH_CONTEXT_CASE_PROFILE_ENTRY) &&
              securityInfo.isHasPrivilege(SecurityInfo.PRIV_CASE_PROFILE_ENTRY)) {
            showLink = true;
          }
        }
      }
			if (showLink) {
				HashMap _donorLinkParams = new HashMap();
				_donorLinkParams.put("op", op);
				_donorLinkParams.put("pathOp", pathOp);
				_donorLinkParams.put("ardaisId", donorId);		
        if (donorId.substring(0, 2).equalsIgnoreCase(ValidateIds.PREFIX_DONOR_IMPORTED)) {
          _donorLinkParams.put("importedYN", "Y");
        }
				setDonorLinkParams(_donorLinkParams);
        StringBuffer donorLinkTextBuffer = new StringBuffer(50);
        donorLinkTextBuffer.append("Select Donor ");
        donorLinkTextBuffer.append(donorId);
        if (donorId.substring(0, 2).equalsIgnoreCase(ValidateIds.PREFIX_DONOR_IMPORTED))  {
          if (!ApiFunctions.isEmpty(donorAlias)) {
            donorLinkTextBuffer.append(" (");
            donorLinkTextBuffer.append(donorAlias);
            donorLinkTextBuffer.append(")");
          }
        }
				setDonorLinkText(donorLinkTextBuffer.toString());
				//if there's a previously selected case, provide a link to use it
				if (caseId != null) {
					HashMap _donorAndCaseLinkParams = new HashMap();
          boolean caseFound = false;
					try {
						// Get the summary of the donor's cases.
            // Try to find the consent that matches the one we're looking for and
            // if we find it use it to set the map entries.
            DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
            DDCDonor donorOperation = ddcDonorHome.create();
						DonorData donor = donorOperation.getDonorCaseSummary(donorId,true);
            if (donor.getConsents() != null) {
              Iterator iterator = donor.getConsents().iterator();
              while (iterator.hasNext()) {
                ConsentData consent = (ConsentData)iterator.next();
                if (consent.getConsentId().equalsIgnoreCase(caseId)) {
                  caseFound = true;
                  _donorAndCaseLinkParams.put("pathReportId",consent.getPathReportId());
                  _donorAndCaseLinkParams.put("consentId",caseId);
                  _donorAndCaseLinkParams.put("ardaisId",donorId);
                  if (donorId.substring(0, 2).equalsIgnoreCase(ValidateIds.PREFIX_DONOR_IMPORTED)) {
                    _donorAndCaseLinkParams.put("donorImportedYN", "Y");
                  }
                  _donorAndCaseLinkParams.put("op",pathOp);
                  break;
                }
              }
            }
					}
					catch (Exception e) {
						throw new ApiException(e);
					}
          if (caseFound) {
            setDonorAndCaseLinkParams(_donorAndCaseLinkParams);
            StringBuffer donorAndCaseLinkTextBuffer = new StringBuffer(100);
            donorAndCaseLinkTextBuffer.append("Select Donor ");
            donorAndCaseLinkTextBuffer.append(donorId);
            if (donorId.substring(0, 2).equalsIgnoreCase(ValidateIds.PREFIX_DONOR_IMPORTED))  {
              if (!ApiFunctions.isEmpty(donorAlias)) {
                donorAndCaseLinkTextBuffer.append(" (");
                donorAndCaseLinkTextBuffer.append(donorAlias);
                donorAndCaseLinkTextBuffer.append(")");
              }
            }
            donorAndCaseLinkTextBuffer.append(" and Case ");
            donorAndCaseLinkTextBuffer.append(caseId);
            if (caseId.substring(0, 2).equalsIgnoreCase(ValidateIds.PREFIX_CASE_IMPORTED))  {
              if (!ApiFunctions.isEmpty(caseAlias)) {
                donorAndCaseLinkTextBuffer.append(" (");
                donorAndCaseLinkTextBuffer.append(caseAlias);
                donorAndCaseLinkTextBuffer.append(")");
              }
            }
            setDonorAndCaseLinkText(donorAndCaseLinkTextBuffer.toString());
          }
          else {
            setDonorAndCaseLinkParams(null);
            setDonorAndCaseLinkText(null);
          }
				}
				else {
					setDonorAndCaseLinkParams(null);
					setDonorAndCaseLinkText(null);
				}
			} //donor is linked
			else {
				setDonorLinkParams(null);
				setDonorLinkText(null);
				setDonorAndCaseLinkParams(null);
				setDonorAndCaseLinkText(null);
			}
		}	
	}

  /**
   * @return
   */
  public String getImportedYN() {
    return _importedYN;
  }

  /**
   * @param string
   */
  public void setImportedYN(String string) {
    _importedYN = string;
  }

  /**
   * @return
   */
  public String getCustomerId() {
    return _customerId;
  }

  /**
   * @param string
   */
  public void setCustomerId(String string) {
    _customerId = string;
  }

}
