package com.ardais.bigr.lims.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.lims.beans.LimsOperation;
import com.ardais.bigr.lims.beans.LimsOperationHome;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a create pathology evaluation business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setPathologyEvaluationData(PathologyEvaluationData) pathologyEvaluationData}: A
 * PathologyEvaluationData data bean holding all information for the pathology evaluation.
 * </li>
 * <li>{@link #setReviewedRawPathReport (boolean) reviewedRawPathReport}: True if the user has
 *     reviewed the raw DDC path report prior to submitting the evaluation.
 * </li>
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
public class BTXDetailsCreatePathologyEvaluation extends BTXDetails implements java.io.Serializable {
	private static final long serialVersionUID = -3170865243593035387L;

	private PathologyEvaluationData _pathologyEvaluationData;
	private PathologyEvaluationData _sourcePathologyEvaluationData;
	private boolean _userWarned = false;
	private boolean _sampleWillBePulled = false;
	private boolean _sampleWillBeDiscordant = false;
  private boolean _reviewedRawPathReport;
	
	private boolean _pvReport = false;
	
	/** 
	 * Constructor
	 */
	public BTXDetailsCreatePathologyEvaluation() {
		super();
	}
	
	/**
	 * Returns the pathologyEvaluationData.
	 * @return PathologyEvaluationData
	 */
	public PathologyEvaluationData getPathologyEvaluationData() {
		return _pathologyEvaluationData;
	}

	/**
	 * Sets the pathologyEvaluationData.
	 * @param pathologyEvaluationData The pathologyEvaluationData to set
	 */
	public void setPathologyEvaluationData(PathologyEvaluationData pathologyEvaluationData) {
		_pathologyEvaluationData = pathologyEvaluationData;
	}
	
	/**
	 * Returns the userWarned.
	 * @return boolean
	 */
	public boolean isUserWarned() {
		return _userWarned;
	}

	/**
	 * Returns the sampleWillBeDiscordant.
	 * @return boolean
	 */
	public boolean isSampleWillBeDiscordant() {
		return _sampleWillBeDiscordant;
	}

	/**
	 * Returns the sampleWillBePulled.
	 * @return boolean
	 */
	public boolean isSampleWillBePulled() {
		return _sampleWillBePulled;
	}

	/**
	 * Sets the userWarned.
	 * @param userWarned The userWarned to set
	 */
	public void setUserWarned(boolean userWarned) {
		_userWarned = userWarned;
	}

	/**
	 * Sets the sampleWillBeDiscordant.
	 * @param sampleWillBeDiscordant The sampleWillBeDiscordant to set
	 */
	public void setSampleWillBeDiscordant(boolean sampleWillBeDiscordant) {
		_sampleWillBeDiscordant = sampleWillBeDiscordant;
	}

	/**
	 * Sets the sampleWillBePulled.
	 * @param sampleWillBePulled The sampleWillBePulled to set
	 */
	public void setSampleWillBePulled(boolean sampleWillBePulled) {
		_sampleWillBePulled = sampleWillBePulled;
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

		//NOTE - Attrib1 is left blank, as the old BTXDetails class used for creating
		//pathology evaluations used to log the pathologist in that column.  We're no
		//longer logging that info (it's the same as the user logged in, which is
		//logged), and MR4640 should've cleared that column, but just to avoid confusion
		//we'll skip that column and start with 2
		history.setAttrib2(getPathologyEvaluationData().getSampleId());
		history.setAttrib3(getPathologyEvaluationData().getSlideId());
		history.setAttrib4(getPathologyEvaluationData().getCreateMethod());
		history.setAttrib5(getPathologyEvaluationData().getEvaluationId());
		String sourceEvalId = getPathologyEvaluationData().getSourceEvaluationId();
		if (sourceEvalId != null && !sourceEvalId.equals("")) {
			PathologyEvaluationData sourceEvaluation;
			try {
        LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
        LimsOperation operation = home.create();
				sourceEvaluation = operation.getPathologyEvaluationData(sourceEvalId);
			}
			catch (Exception e) {
				throw new ApiException("Error calling LimsOperation.getPathologyEvaluationData from BTXDetailsCreatePathologyEvaluation.describeIntoHistoryRecord", e);
			}
			history.setAttrib6(sourceEvalId);
			history.setAttrib7(sourceEvaluation.getSampleId());
			history.setAttrib8(sourceEvaluation.getSlideId());
		}
	}
	
	/**
	 * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
	 * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
	 * method. This method must not make use of any fields that aren't set by the 
	 * populateFromHistoryRecord method. For this object type, the fields we can use here 
	 * are:
	 	getPathologyEvaluationData()
		getPathologyEvaluationData().getSampleId();
		getPathologyEvaluationData().getSlideId();
		getPathologyEvaluationData().getCreateMethod();
		getPathologyEvaluationData().getEvaluationId();
		getPathologyEvaluationData().getPathologist();
		getSourceEvaluationData()
		getSourceEvaluationData().getEvaluationId()
		getSourceEvaluationData().getSampleId()
		getSourceEvaluationData().getSlideId()
	 *
	 * @return an HTML string that defines how the transaction details are presented
	 * in a transaction-history web page
	 */
  protected String doGetDetailsAsHTML() {
       
        SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

		StringBuffer sb = new StringBuffer(2048);

		// The result has this form:
		//    <pathologist> performed pathology verification using 
		//    <slide id> for sample <sample id>, creating evaluation with id <evaluation id>.
		// and including the following line if this evaluation was copied/edited
		//	  <pathologist> used the evaluation with <evaluation id> (created using slide <slide id> for sample <sample id>) as a source.

    Escaper.htmlEscape(getPathologyEvaluationData().getPathologist(), sb);
		sb.append(" created a pathology evaluation using slide ");
		sb.append(IcpUtils.prepareLink(getPathologyEvaluationData().getSlideId(), securityInfo));
		sb.append(" for sample ");
		sb.append(IcpUtils.prepareLink(getPathologyEvaluationData().getSampleId(), securityInfo));
		String evalId = getPathologyEvaluationData().getEvaluationId();
		if (evalId != null && !evalId.equals("")) {
			sb.append(", creating evaluation with id ");
			sb.append(IcpUtils.prepareLink(getPathologyEvaluationData().getEvaluationId(), securityInfo));
		}
		sb.append(".");
		PathologyEvaluationData sourceEvaluation = getSourcePathologyEvaluationData();
		if (sourceEvaluation != null) {
			sb.append(" ");
      Escaper.htmlEscape(getPathologyEvaluationData().getPathologist(), sb);
			sb.append(" used the evaluation with id ");
			sb.append(IcpUtils.prepareLink(sourceEvaluation.getEvaluationId(), securityInfo));
			sb.append(" (created using slide ");
			sb.append(IcpUtils.prepareLink(sourceEvaluation.getSlideId(), securityInfo));
			sb.append(" for sample ");
			sb.append(IcpUtils.prepareLink(sourceEvaluation.getSampleId(), securityInfo));
			sb.append(") as a source.");
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
		return BTXDetails.BTX_TYPE_CREATE_PATH_EVAL;
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

		if (getPathologyEvaluationData() != null) {
			set.add(getPathologyEvaluationData().getSampleId());
			set.add(getPathologyEvaluationData().getSlideId());
			set.add(getPathologyEvaluationData().getEvaluationId());
		}

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
		PathologyEvaluationData pathologyEvaluationData = new PathologyEvaluationData();
		pathologyEvaluationData.setSampleId(history.getAttrib2());
		pathologyEvaluationData.setSlideId(history.getAttrib3());
		pathologyEvaluationData.setCreateMethod(history.getAttrib4());
		pathologyEvaluationData.setEvaluationId(history.getAttrib5());
		pathologyEvaluationData.setPathologist(history.getUserId());
		setPathologyEvaluationData(pathologyEvaluationData);
		//if there is source evaluation information, recreate the source evaluation
		if (history.getAttrib6() != null && !history.getAttrib6().equals("")) {
			PathologyEvaluationData sourceEvaluation = new PathologyEvaluationData();
			sourceEvaluation.setEvaluationId(history.getAttrib6());
			sourceEvaluation.setSampleId(history.getAttrib7());
			sourceEvaluation.setSlideId(history.getAttrib8());
			setSourcePathologyEvaluationData(sourceEvaluation);
		}
	}
	
	//private method used by the populateFromHistoryRecord() method to recreate source
	//pathology evaluation information, if any
	private void setSourcePathologyEvaluationData(PathologyEvaluationData sourcePathologyEvaluationData) {
		_sourcePathologyEvaluationData = sourcePathologyEvaluationData;
	}
	
	//private method used by the doGetDetailsAsHTML() method to retrieve source
	//pathology evaluation information, if any
	private PathologyEvaluationData getSourcePathologyEvaluationData() {
		return _sourcePathologyEvaluationData;
	}
	/**
	 * Returns the pvReport.
	 * @return boolean
	 */
	public boolean isPvReport() {
		return _pvReport;
	}

	/**
	 * Sets the pvReport.
	 * @param pvReport The pvReport to set
	 */
	public void setPvReport(boolean pvReport) {
		_pvReport = pvReport;
	}

  /**
   * Returns the reviewedRawPathReport.
   * @return boolean
   */
  public boolean isReviewedRawPathReport() {
    return _reviewedRawPathReport;
  }

  /**
   * Sets the reviewedRawPathReport.
   * @param reviewedRawPathReport The reviewedRawPathReport to set
   */
  public void setReviewedRawPathReport(boolean reviewedRawPathReport) {
    _reviewedRawPathReport = reviewedRawPathReport;
  }

}