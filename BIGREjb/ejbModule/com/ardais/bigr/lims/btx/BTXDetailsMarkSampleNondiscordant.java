package com.ardais.bigr.lims.btx;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a mark sample nondiscordant business transaction.
 * Since this is a specialized case of marking a sample unpulled, this object
 * extends the BTXDetailsMarkSampleUnpulled class.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setSampleId(String) sampleId}: The id of the sample to mark nondiscordant.</li>
 * <li>{@link #setReason(String) reason}: The reason the sample is being marked nondiscordant.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BTXDetailsMarkSampleNondiscordant
	extends BTXDetailsMarkSampleUnpulled implements java.io.Serializable  {
	private static final long serialVersionUID = -2814925871271063180L;
	
	/** 
	 * Constructor
	 */
	public BTXDetailsMarkSampleNondiscordant() {
		super();
	}
	
	/**
	 * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
	 * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
	 * method. This method must not make use of any fields that aren't set by the 
	 * populateFromHistoryRecord method. For this object type, the fields we can use here 
	 * are:
	 * 		getSampleId()
	 * 		getReason()
	 *
	 * @return an HTML string that defines how the transaction details are presented
	 * in a transaction-history web page
	 */
  protected String doGetDetailsAsHTML() {
       
        SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

		StringBuffer sb = new StringBuffer(128);

		// The result has this form:
		//    Sample <sample id> was marked nondiscordant with a reason of <reason>.

		sb.append("Sample ");
		sb.append(IcpUtils.prepareLink(getSampleId(), securityInfo));
		sb.append(" was marked nondiscordant with a reason of \"");
		Escaper.htmlEscape(getReason(),sb);
		sb.append("\".");

		return sb.toString();
	}

	
	/**
	 * Return the business transaction type code for the transaction that this
	 * class represents.
	 *
	 * @return the transaction type code.
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_MARK_SAMPLE_NONDISCORDANT;
	}

}
