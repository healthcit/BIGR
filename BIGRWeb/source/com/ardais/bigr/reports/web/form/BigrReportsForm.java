package com.ardais.bigr.reports.web.form;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateClinicalFinding;
import com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails;
import com.ardais.bigr.javabeans.ClinicalFindingData;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.ardais.bigr.web.form.PopulatesRequestFromBtxDetails;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.reports.DonorAndCaseReportVO;

public class BigrReportsForm
    extends BigrActionForm{

  
 private DonorAndCaseReportVO donorAndCaseReportVO;
  



/**
* Constructor for ManualIncentivesForm.
*/
public BigrReportsForm() {
super();
}

protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
  donorAndCaseReportVO = new DonorAndCaseReportVO();
}


/**
 * @return Returns the donorAndCaseReportVO.
 */
public DonorAndCaseReportVO getDonorAndCaseReportVO() {
  return donorAndCaseReportVO;
}

/**
 * @param donorAndCaseReportVO The donorAndCaseReportVO to set.
 */
public void setDonorAndCaseReportVO(DonorAndCaseReportVO donorAndCaseReportVO) {
  this.donorAndCaseReportVO = donorAndCaseReportVO;
}




/**
 * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
 */
protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
  return null;
}

}
