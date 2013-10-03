package com.ardais.bigr.pdc.helpers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.pdc.javabeans.ClinicalDataData;
import com.gulfstreambio.gboss.GbossFactory;

/**
 */
public class ClinicalDataHelper extends JspHelper {
	private String _ardaisId;
  private String _donorCustomerId;
	private String _category;
	private String _clinicalData;
	private String _clinicalDataId;
	private String _consentId;
  private String _consentCustomerId;

	private Map _caseLinkParams;
	private Map _donorLinkParams;

	// The clinical data data bean that holds data to be displayed
	// through this helper.
	private ClinicalDataData _dataBean;
  
  private String _donorImportedYN;
/**
 * Creates a new <code>ClinicalDataHelper</code>.
 */
public ClinicalDataHelper() {
	super();
}
/**
 * Creates a <code>ClinicalDataHelper</code>, initializing its properties 
 * from a <code>ClinicalDataData</code> bean.
 *
 * @param  dataBean  a <code>ClinicalDataData</code> bean
 */
public ClinicalDataHelper(ClinicalDataData dataBean) {
	_dataBean = dataBean;

}
/**
 * Creates a <code>ClinicalDataHelper</code> from an HTTP request,
 * initializing its properties from the request parameters.
 */
public ClinicalDataHelper(HttpServletRequest request) {
	_clinicalDataId = ApiFunctions.safeTrim(request.getParameter("clinicalDataId"));
	if (isEmpty(_clinicalDataId)) _clinicalDataId = null;
	_ardaisId = ApiFunctions.safeTrim(request.getParameter("ardaisId"));
	_category = ApiFunctions.safeTrim(request.getParameter("category"));
	_clinicalData = ApiFunctions.safeTrim(request.getParameter("clinicalData"));
	_consentId = ApiFunctions.safeTrim(request.getParameter("consentId"));
  _donorImportedYN = ApiFunctions.safeTrim(request.getParameter("donorImportedYN"));
}
/**
 * Returns this clinical data's donor (Ardais) id.
 *
 * @return  The clinical data's donor id.
 */
public String getArdaisId() {
	if (_ardaisId != null) 
		return _ardaisId;
	else if (_dataBean != null) 
		return _dataBean.getArdaisId();
	else 
		return null;
}
/**
 * Returns this clinical data's category.
 *
 * @return  The clinical data's category.
 */
public String getCategory() {
	if (_category != null) 
		return _category;
	else if (_dataBean != null) 
		return _dataBean.getCategory();
	else 
		return null;
}
/**
 * Returns this clinical data category display value.
 *
 * @return  The category display value.
 */
public String getCategoryDisplay() {
	String code = getCategory();
	return (code != null) ? GbossFactory.getInstance().getDescription(/*"MEDICAL_RECORD_SECTION",*/ code) : null;
}
/**
 * Returns this clinical data's data.
 *
 * @return  The clinical data's data.
 */
public String getClinicalData() {
	if (_clinicalData != null) 
		return _clinicalData;
	else if (_dataBean != null) 
		return _dataBean.getClinicalData();
	else 
		return null;
}
/**
 * Returns this clinical data's id.
 *
 * @return  The clinical data's id.
 */
public String getClinicalDataId() {
	if (_clinicalDataId != null) 
		return _clinicalDataId;
	else if (_dataBean != null) 
		return _dataBean.getClinicalDataId();
	else 
		return null;
}
/**
 * Returns this clinical data's consent id.
 *
 * @return  The clinical data's consent id.
 */
public String getConsentId() {
	if (_consentId != null) 
		return _consentId;
	else if (_dataBean != null) 
		return _dataBean.getConsentId();
	else 
		return null;
}

/**
 * Returns a <code>Map</code> of URL parameters for the case
 * link, in support of the Struts html:link tag.
 * 
 * @return  The URL parameters.
 */
public Map getCaseLinkParams() {
	if (_caseLinkParams == null) {
		_caseLinkParams = new HashMap();
		_caseLinkParams.put("op", "ClinicalDataSummaryPrepare");
		_caseLinkParams.put("ardaisId", getArdaisId());		
		_caseLinkParams.put("consentId", getConsentId());		
    _caseLinkParams.put("category", getCategory());   
    _caseLinkParams.put("donorImportedYN", getDonorImportedYN());   
	}
	return _caseLinkParams;
}

/**
 * Returns a <code>Map</code> of URL parameters for the donor
 * link, in support of the Struts html:link tag.
 * 
 * @return  The URL parameters.
 */
public Map getDonorLinkParams() {
	if (_donorLinkParams == null) {
		_donorLinkParams = new HashMap();
		_donorLinkParams.put("op", "CaseListPrepare");
		_donorLinkParams.put("pathOp", "ClinicalDataSummaryPrepare");
		_donorLinkParams.put("ardaisId", getArdaisId());		
    _donorLinkParams.put("importedYN", getDonorImportedYN());    
	}
	return _donorLinkParams;
}

/**
 * Returns the <code>ClinicalDataData</code> bean that contains
 * the clinical data fields associated with this 
 * <code>ClinicalDataHelper</code>.
 * 
 * @return  The <code>ClinicalDataData</code> bean.
 */
public ClinicalDataData getDataBean() {
	if (_dataBean == null) {
		_dataBean = new ClinicalDataData();
		if (!isNew()) {
			_dataBean.setClinicalDataId(_clinicalDataId);
		}
		_dataBean.setArdaisId(_ardaisId);
		_dataBean.setCategory(_category);
		_dataBean.setClinicalData(_clinicalData);
		_dataBean.setConsentId(_consentId);
	}
	return _dataBean;
}
/**
 * Returns <code>true</code> if this helper is for a new
 * clinical data; <code>false</code> otherwise.
 * 
 * @return  A boolean indicating whether this clinical data is new.
 */
public boolean isNew() {
	return (isEmpty(getClinicalDataId()));
}
  /**
   * @return
   */
  public String getDonorImportedYN() {
    return _donorImportedYN;
  }

  /**
   * @param string
   */
  public void setDonorImportedYN(String string) {
    _donorImportedYN = string;
  }

  /**
   * @return
   */
  public String getConsentCustomerId() {
    return _consentCustomerId;
  }

  /**
   * @return
   */
  public String getDonorCustomerId() {
    return _donorCustomerId;
  }

  /**
   * @param string
   */
  public void setConsentCustomerId(String string) {
    _consentCustomerId = string;
  }

  /**
   * @param string
   */
  public void setDonorCustomerId(String string) {
    _donorCustomerId = string;
  }

}
