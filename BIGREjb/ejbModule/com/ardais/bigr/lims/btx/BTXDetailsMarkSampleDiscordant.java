package com.ardais.bigr.lims.btx;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a mark sample discordant business transaction.
 * Since this is a specialized case of marking a sample pulled, this object
 * extends the BTXDetailsMarkSamplePulled class.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setSampleId(String) sampleId}: The id of the sample to mark discordant.</li>
 * <li>{@link #setReason(String) reason}: The reason the sample is being marked discordant.</li>
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
public class BTXDetailsMarkSampleDiscordant
	extends BTXDetailsMarkSamplePulled implements java.io.Serializable  {
	private static final long serialVersionUID = -7318189996865907643L;
	
	/** 
	 * Constructor
	 */
	public BTXDetailsMarkSampleDiscordant() {
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
	 *      isSampleUnreleased()
	 * 		isSampleUnQCPosted()
	 * 		getUnreportedEvaluationId()
	 *
	 * @return an HTML string that defines how the transaction details are presented
	 * in a transaction-history web page
	 */
  protected String doGetDetailsAsHTML() {
       
        SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

		StringBuffer sb = new StringBuffer(128);

		// The result has this form:
		//    Sample <sample id> was marked discordant with a reason of "<reason>".
		//If the sample was unreleased as a result of it being marked discordant, the result includes
		//another line:
		//    This caused the sample to be marked unreleased.
		//If the sample was unQCPosted as a result of it being marked discordant, the result includes
		//another line:
		//    This caused the sample to be marked unQCPosted.
		//If an evaluation was unreported as a result of this sample being marked discordant, the result
		//includes another line:
		//    This caused pathology evaluation <id> to be marked unreported.		
		sb.append("Sample ");
		sb.append(IcpUtils.prepareLink(getSampleId(), securityInfo));
		sb.append(" was marked discordant with a reason of \"");
		Escaper.htmlEscape(getReason(),sb);
		sb.append("\".");
		boolean sampleUnreleased = isSampleUnreleased();
		boolean sampleUnQCPosted = isSampleUnQCPosted();
		String evalId = getUnreportedEvaluationId();
		boolean andIt = false;
		boolean periodRequired = false;
		if (sampleUnreleased) {
			if (andIt) {
				sb.append(", and caused ");
			}
			else {
				sb.append(" This caused ");
			}
			sb.append("the sample to be marked unreleased");
			andIt = true;
			periodRequired = true;
		}
		if (sampleUnQCPosted) {
			if (andIt) {
				sb.append(", and caused ");
			}
			else {
				sb.append(" This caused ");
			}
			sb.append("the sample to be marked unQCPosted");
			andIt = true;
			periodRequired = true;
		}
		if (evalId != null) {
			if (andIt) {
				sb.append(", and caused ");
			}
			else {
				sb.append(" This caused ");
			}
			sb.append("the pathology evaluation with id ");
			sb.append(IcpUtils.prepareLink(evalId, securityInfo));
			sb.append(" to be marked unreported");
			andIt = true;
			periodRequired = true;
		}
		if (periodRequired) {
			sb.append(".");
		}

		return sb.toString();
	}

	
	/**
	 * Return the business transaction type code for the transaction that this
	 * class represents.
	 *
	 * @return the transaction type code.
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_MARK_SAMPLE_DISCORDANT;
	}

}
