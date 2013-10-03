package com.ardais.bigr.lims.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a sample to be reported.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setPathologyEvaluationId(String) pathologyEvaluationId}: The id of the pathology evaluation. </li>
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
public class BTXDetailsReportPathologyEvaluation extends BTXDetails implements Serializable {
	private static final long serialVersionUID = -1068959134666820845L;
	private String _pathologyEvaluationId;
	private String _sampleId;
	private String _slideId;
	private boolean _sampleUnreleased;
	private boolean _sampleUnQCPosted;
	private String _unreportedEvaluationId;
  /* The following boolean was put in as a hack in order to implement MR6177.
   * This MR called for the most recent reportable evaluation to be reported
   * when a sample was unpulled (if requested by the user).  BtxPerformerPathologyOperation.performMarkSampleUnpulled
   * marks the sample unpulled and then calls performReportPathologyEvaluation.
   * Unfortunately when that method does a JDBC call to see if the sample belonging
   * to the evaluation we're trying to report is pulled, it comes back as true (i.e.
   * it doesn't have visibility to the fact we've just marked the sample unpulled)
   */
  private boolean _ignoreSampleChecks = false;
	
	private String _message;

	/**
	 * Constructor
	 */
	public BTXDetailsReportPathologyEvaluation() {
		super();
	}
	
	/**
	 * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
	 * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
	 * method. This method must not make use of any fields that aren't set by the 
	 * populateFromHistoryRecord method. For this object type, the fields we can use here 
	 * are:
	 * getPathologyEvaluationId();
	 * getSampleId();
	 * getSlideId();
	 * isSampleUnreleased()
	 * isSampleUnQCPosted()
	 * getUnreportedEvaluationId()
	 *
	 * @return an HTML string that defines how the transaction details are presented
	 * in a transaction-history web page
	 */
	protected String doGetDetailsAsHTML() {
		// The result has this form:
		//    <pathologist> reported pathology evaluation <evaluation id>
		//    (created using slide <slide id> for sample <sample id>).
		//If the sample was unreleased as a result of this evaluation being reported, the result 
		//includes another line:
		//    This caused the sample to be marked unreleased.
		//If the sample was unQCPosted as a result of this evaluation being reported, the result 
		//includes another line:
		//    This caused the sample to be marked unQCPosted.
		//If an evaluation was unreported as a result of this evaluation being reported, the result
		//includes another line:
		//    This caused pathology evaluation <id> to be marked unreported.
       
        SecurityInfo securityInfo = getLoggedInUserSecurityInfo();
		
		StringBuffer sb = new StringBuffer(1024);
    Escaper.htmlEscape(getUserId(), sb);
		sb.append(" reported pathology evaluation ");
		sb.append(IcpUtils.prepareLink(getPathologyEvaluationId(), securityInfo));
		sb.append(" (created using slide ");
		sb.append(IcpUtils.prepareLink(getSlideId(), securityInfo));
		sb.append(" for sample ");
		sb.append(IcpUtils.prepareLink(getSampleId(), securityInfo));
		sb.append(").");
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
		return BTX_TYPE_REPORT_PATH_EVAL;
	}

	/**
	 * Return a set of the object ids of the objects that are directly involved
	 * in this transaction.  This set does not contain the ids of objects that
	 * are considered to be indirectly involved in the transaction, and it does
	 * not include the user id of the user who performed the transaction.
	 * <p>
	 * For example, a transaction that scans a box of samples directly involves the box
	 * object and each of the sample objects, and indirectly involves the
	 * asm, asm form, case and donor objects for each sample.
	 *
	 * @return the set of directly involved object ids.
	 */
	public Set getDirectlyInvolvedObjects() {
		Set set = new HashSet();
		
		if (getSampleId() != null)
			set.add(getSampleId());
		if (getSlideId() != null)	
			set.add(getSlideId());
		if (getPathologyEvaluationId() != null)	
			set.add(getPathologyEvaluationId());
		
		return set;
	}
	
	/**
	 * Fill a business transaction history record object with information
	 * from this transaction details object.  This method will set <b>all</b>
	 * fields on the history record, even ones not used by the this type of
	 * transaction.  Fields that aren't used by this transaction type will be
	 * set to their initial default values.
	 * <p>
	 * This method is only meant to be used internally by the business
	 * transaction framework implementation.  Please don't use it anywhere else.
	 *
	 * @param history the history record object that will have its fields set to
	 *    the transaction information.
	 */
	public void describeIntoHistoryRecord(BTXHistoryRecord history) {
		super.describeIntoHistoryRecord(history);
				
		history.setAttrib1(getPathologyEvaluationId());
		history.setAttrib2(getSampleId());
		history.setAttrib3(getSlideId());
		if (isSampleUnreleased()) {
			history.setAttrib4(LimsConstants.LIMS_DB_YES);
		}
		history.setAttrib5(getUnreportedEvaluationId());
		if (isSampleUnQCPosted()) {
			history.setAttrib6(LimsConstants.LIMS_DB_YES);
		}
	}
	
	/**
	 * Populate the fields of this object with information contained in a
	 * business transaction history record object.  This method must set <b>all</b>
	 * fields on this object, as if it had been newly created immediately before
	 * this method was called.  A runtime exception is thrown if the transaction type
	 * represented by the history record doesn't match the transaction type represented
	 * by this object.
	 * <p>
	 * This method is only meant to be used internally by the business
	 * transaction framework implementation.  Please don't use it anywhere else.
	 *
	 * @param history the history record object that will be used as the
	 *    information source.  A runtime exception is thrown if this is null.
	 */	
	public void populateFromHistoryRecord(BTXHistoryRecord history) {
		super.populateFromHistoryRecord(history);
		setPathologyEvaluationId(history.getAttrib1());
		setSampleId(history.getAttrib2());
		setSlideId(history.getAttrib3());
		setSampleUnreleased(ApiFunctions.safeToString(history.getAttrib4()).equals(LimsConstants.LIMS_DB_YES));
		setUnreportedEvaluationId(history.getAttrib5());
		setSampleUnQCPosted(ApiFunctions.safeToString(history.getAttrib6()).equals(LimsConstants.LIMS_DB_YES));
	}

	/**
	 * Returns the pathologyEvaluationId.
	 * @return String
	 */
	public String getPathologyEvaluationId() {
		return _pathologyEvaluationId;
	}

	/**
	 * Returns the sampleId.
	 * @return String
	 */
	public String getSampleId() {
		return _sampleId;
	}

	/**
	 * Returns the slideId.
	 * @return String
	 */
	public String getSlideId() {
		return _slideId;
	}

	/**
	 * Returns the sampleUnreleased.
	 * @return boolean
	 */
	public boolean isSampleUnreleased() {
		return _sampleUnreleased;
	}

	/**
	 * Returns the sampleUnQCPosted.
	 * @return boolean
	 */
	public boolean isSampleUnQCPosted() {
		return _sampleUnQCPosted;
	}

	/**
	 * Returns the unreportedEvaluationId.
	 * @return String
	 */
	public String getUnreportedEvaluationId() {
		return _unreportedEvaluationId;
	}

	/**
	 * Sets the pathologyEvaluationId.
	 * @param pathologyEvaluationId The pathologyEvaluationId to set
	 */
	public void setPathologyEvaluationId(String pathologyEvaluationId) {
		_pathologyEvaluationId = pathologyEvaluationId;
	}

	/**
	 * Sets the sampleId.
	 * @param sampleId The sampleId to set
	 */
	public void setSampleId(String sampleId) {
		_sampleId = sampleId;
	}

	/**
	 * Sets the slideId.
	 * @param slideId The slideId to set
	 */
	public void setSlideId(String slideId) {
		_slideId = slideId;
	}

	/**
	 * Sets the sampleUnreleased.
	 * @param sampleUnreleased The sampleUnreleased to set
	 */
	public void setSampleUnreleased(boolean sampleUnreleased) {
		_sampleUnreleased = sampleUnreleased;
	}

	/**
	 * Sets the sampleUnQCPosted.
	 * @param sampleUnQCPosted The sampleUnQCPosted to set
	 */
	public void setSampleUnQCPosted(boolean sampleUnQCPosted) {
		_sampleUnQCPosted = sampleUnQCPosted;
	}

	/**
	 * Sets the unreportedEvaluationId.
	 * @param unreportedEvaluationId The unreportedEvaluationId to set
	 */
	public void setUnreportedEvaluationId(String unreportedEvaluationId) {
		_unreportedEvaluationId = unreportedEvaluationId;
	}

	/**
	 * Returns the message.
	 * @return String
	 */
	public String getMessage() {
		return _message;
	}

	/**
	 * Sets the message.
	 * @param message The message to set
	 */
	public void setMessage(String message) {
		_message = message;
	}

  /**
   * @return
   */
  public boolean isIgnoreSampleChecks() {
    return _ignoreSampleChecks;
  }

  /**
   * @param b
   */
  public void setIgnoreSampleChecks(boolean b) {
    _ignoreSampleChecks = b;
  }

}
