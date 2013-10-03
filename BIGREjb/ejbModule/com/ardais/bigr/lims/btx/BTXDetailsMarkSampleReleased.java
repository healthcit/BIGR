package com.ardais.bigr.lims.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a release sample business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setSampleId(String) sampleId}: The id of the sample to release.</li>
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
public class BTXDetailsMarkSampleReleased extends BTXDetails implements java.io.Serializable {
	private static final long serialVersionUID = 7098659194013169235L;

	private String _sampleId;
  //NOTE - neither the reportedEvaluationId nor the amount of time that has elapsed since
  //this transaction and the time the reportedEvaluation was reported are specified in the input, 
  //as that info is not required for any business functionality for this transaction.  It is, 
  //however, required for logging purposes.  Therefore the method that performs the business 
  //functionality for this transaction (BtxPerformerPathologyOperation.performMarkSampleReleased) will 
  //need to (and does) set both the reportedEvaluationId and elapsedTime so they can be logged.
  private String _reportedEvaluationId;
  private Long _elapsedTime;
	
	/** 
	 * Constructor
	 */
	public BTXDetailsMarkSampleReleased() {
		super();
	}

	/**
	 * Returns the sampleId.
	 * @return String
	 */
	public String getSampleId() {
		return _sampleId;
	}

	/**
	 * Sets the sampleId.
	 * @param sampleId The sampleId to set
	 */
	public void setSampleId(String sampleId) {
		_sampleId = sampleId;
	}

  /**
   * Returns the reportedEvaluationId.
   * @return String
   */
  public String getReportedEvaluationId() {
    return _reportedEvaluationId;
  }

  /**
   * Sets the reportedEvaluationId.
   * @param reportedEvaluationId The reportedEvaluationId to set
   */
  public void setReportedEvaluationId(String reportedEvaluationId) {
    _reportedEvaluationId = reportedEvaluationId;
  }

  /**
   * Returns the elapsedTime.
   * @return Long
   */
  public Long getElapsedTime() {
    return _elapsedTime;
  }

  /**
   * Sets the elapsedTime.
   * @param elapsedTime The elapsedTime to set
   */
  public void setElapsedTime(Long elapsedTime) {
    _elapsedTime = elapsedTime;
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
		history.setAttrib1(getSampleId());
    history.setAttrib2(getReportedEvaluationId());
    history.setAttrib3(getElapsedTime().toString());
	}
	
	/**
	 * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
	 * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
	 * method. This method must not make use of any fields that aren't set by the 
	 * populateFromHistoryRecord method. For this object type, the fields we can use here 
	 * are:
	 * 		getSampleId()
   *    getReportedEvaluationId()
   *    getElapsedTime()
	 *
	 * @return an HTML string that defines how the transaction details are presented
	 * in a transaction-history web page
	 */
  protected String doGetDetailsAsHTML() {
       
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

		StringBuffer sb = new StringBuffer(128);

		// If no reported evaluation was recorded (i.e. prior to MR5645), the result has 
    // this form:
		//    Sample <sample id> was marked released.
    // Otherwise, it has this form:
    //    Sample <sample id> was marked released, <elapsed time> after 
    //    evaluation <reported eval id> was reported.

		sb.append("Sample ");
		sb.append(IcpUtils.prepareLink(getSampleId(), securityInfo));
		sb.append(" was marked released");
    if (!ApiFunctions.safeString(getReportedEvaluationId()).equals("") &&
        getElapsedTime() != null) {
      //elapsed time was stored in minutes (see BtxPerformerPathologyOperation.performMarkSampleReleased)
      long minutes = getElapsedTime().longValue();
      sb.append(", ");
      boolean daysIncluded = false;
      //divide the number of minutes by the number of minutes in a day 
      //(60 minutes per hour * 24 hours per day = 1440)
      long numberOfDays = minutes/1440;
      long remainder = minutes%1440;
      if (numberOfDays > 0) {
        sb.append(numberOfDays);
        if (numberOfDays == 1) {
          sb.append(" day");
        }
        else {
          sb.append(" days");
        }
        daysIncluded = true;
      }
      //divide the remaining minutes by the number of minutes in an hour
      long numberOfHours = remainder/60;
      remainder = remainder%60;
      //round the remaining minutes to the nearest hour
      if (remainder >= 30) {
        numberOfHours = numberOfHours + 1;
      }
      if (numberOfHours > 0) {
        if (daysIncluded) {
          sb.append(" and ");
        }
        sb.append(numberOfHours);
        if (numberOfHours == 1) {
          sb.append(" hour");
        }
        else {
          sb.append(" hours");
        }
      }
      //handle the situation where both the number of days and number of hours are 0 (the code
      //above will not have written anything in that case
      if (numberOfDays == 0 && numberOfHours == 0) {
        sb.append("less than 1 hour");
      }
      sb.append(" after evaluation ");
      sb.append(IcpUtils.prepareLink(getReportedEvaluationId(), securityInfo));
      sb.append(" was reported");
    }
    sb.append(".");

		return sb.toString();
	}

	
	/**
	 * Return the business transaction type code for the transaction that this
	 * class represents.
	 *
	 * @return the transaction type code.
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_MARK_SAMPLE_RELEASED;
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
	public java.util.Set getDirectlyInvolvedObjects() {
		Set set = new HashSet();

		set.add(getSampleId());
    set.add(getReportedEvaluationId());

		return set;
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
		setSampleId(history.getAttrib1());
    setReportedEvaluationId(history.getAttrib2());
    String elapsedTime = history.getAttrib3();
    if (ApiFunctions.safeString(elapsedTime).equals("")) {
      setElapsedTime(null);
    }
    else {
      setElapsedTime(new Long(history.getAttrib3()));
    }
	}

}