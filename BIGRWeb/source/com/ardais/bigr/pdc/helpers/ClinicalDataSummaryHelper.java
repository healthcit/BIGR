package com.ardais.bigr.pdc.helpers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.javabeans.ClinicalDataData;
import com.ardais.bigr.util.ArtsConstants;
import com.gulfstreambio.gboss.GbossFactory;

/**
 */
public class ClinicalDataSummaryHelper extends JspHelper {
	private String _ardaisId;
  private String _donorCustomerId;
	private String _consentId;
  private String _consentCustomerId;
	private String _consentAccount;
	private ClinicalDataData _dataBean;
	private Map _clinicalData;

	private Map _donorLinkParams;
	private Map _editLinkParams;
	private LegalValueSet _categories;
	private int _categoriesIndex;
  
  private String _lastEditedCategory;
  private String _donorImportedYN;

/**
 * Creates a new <code>ClinicalDataSummaryHelper</code>.
 */
public ClinicalDataSummaryHelper() {
	super();
}
/**
 * Creates a new <code>ClinicalDataSummaryHelper</code>, initializing
 * it from the list of {@link com.ardais.bigr.pdc.javabeans.ClinicalDataData}
 * beans.  Each bean in the list is assumed to belong to the same donor
 * and case.
 *
 * @param  clinicalDataList  the <code>List</code> of data beans
 */
public ClinicalDataSummaryHelper(List clinicalDataList) {
	super();

	_clinicalData = new HashMap();
	Iterator i = clinicalDataList.iterator();
	ClinicalDataData dataBean = null;
	while (i.hasNext()) {
		dataBean = (ClinicalDataData)i.next();
		ClinicalDataHelper helper = new ClinicalDataHelper(dataBean);
		_clinicalData.put(dataBean.getCategory(), helper);
	}
	if (dataBean != null) {
		_ardaisId = dataBean.getArdaisId();
		_consentId = dataBean.getConsentId();
	}
}
/**
 * Creates a <code>ClinicalDataSummaryHelper</code> from an HTTP request,
 * initializing its properties from the request parameters.
 */
public ClinicalDataSummaryHelper(HttpServletRequest request) {
	_ardaisId = ApiFunctions.safeTrim(request.getParameter("ardaisId"));
	_consentId = ApiFunctions.safeTrim(request.getParameter("consentId"));
  _lastEditedCategory = ApiFunctions.safeTrim(request.getParameter("category"));
  _donorImportedYN = ApiFunctions.safeTrim(request.getParameter("donorImportedYN"));
}
/**
 * Returns this clinical data's donor id.
 *
 * @return  The clinical data's donor id.
 */
public String getArdaisId() {
	return _ardaisId;
}

  /**
   * Returns the id of the last category for which the edit button was pressed.
   * @return a String holding the id of the last category for which the edit
   * button was pressed
   */
  public String getLastEditedCategory() {
    return _lastEditedCategory;
  }

  /**
   * @param string
   */
  public void setLastEditedCategory(String string) {
    _lastEditedCategory = string;
  }
  
/**
 * Returns the list of clinical data categories.
 *
 * @return  A <code>LegalValueSet</code> of clinical data categories.
 */
public LegalValueSet getCategoryList() {
	return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_MEDICAL_RECORD_SECTION);
}
/**
 * Returns the {@link ClinicalDataHelper} for the specified 
 * category of clinical data
 *
 * @param  category  the category
 * @return  The {@link ClinicalDataHelper}.
 */
public ClinicalDataHelper getClinicalData(String category) {
	return (ClinicalDataHelper)getClinicalDataMap().get(category);
}
/**
 */
private Map getClinicalDataMap() {
	if (_clinicalData == null)
		_clinicalData = new HashMap();
	return _clinicalData;
}

/**
 * Returns this clinical data's consent id.
 *
 * @return  The clinical data's consent id.
 */
public String getConsentId() {
	return _consentId;
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
 */
public String getStartCategories() {
	if (_editLinkParams == null) {
		_categories = getCategoryList();
		_categoriesIndex = 0;
		_editLinkParams = new HashMap();
		_editLinkParams.put("op", "ClinicalDataPrepare");
		_editLinkParams.put("ardaisId", getArdaisId());
		_editLinkParams.put("consentId", getConsentId());
    _editLinkParams.put("donorImportedYN", getDonorImportedYN());
	}
	return null;
}
/**
 */
public String getNextCategory() {
	_categoriesIndex++;
	return null;
}
/**
 */
public String getCurrentCategory() {
	return _categories.getValue(_categoriesIndex);
}
/**
 */
public String getCurrentCategoryDisplay() {
	return GbossFactory.getInstance().getDescription(/*"MEDICAL_RECORD_SECTION",*/ getCurrentCategory());
}
/**
 */
public ClinicalDataHelper getCurrentClinicalData() {
    return getClinicalData(getCurrentCategory());
}
/**
 * Returns a <code>Map</code> of URL parameters for the edit
 * link, in support of the Struts html:link tag.
 * 
 * @return  The URL parameters.
 */
public Map getCurrentEditLinkParams() {
	_editLinkParams.put("category", getCurrentCategory());
    ClinicalDataHelper helper = getCurrentClinicalData();
    if (helper == null) {
    	_editLinkParams.remove("clinicalDataId");
    }
    else {
    	_editLinkParams.put("clinicalDataId", helper.getClinicalDataId());
    }   
	return _editLinkParams;
}
/**
 * Returns the <code>ClinicalDataData</code> bean that contains
 * the clinical data fields associated with this 
 * <code>ClinicalDataSummaryHelper</code>.
 * 
 * @return  The <code>ClinicalDataData</code> bean.
 */
public ClinicalDataData getDataBean() {
	if (_dataBean == null) {
		_dataBean = new ClinicalDataData();
		_dataBean.setArdaisId(_ardaisId);
		_dataBean.setConsentId(_consentId);
	}
	return _dataBean;
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
