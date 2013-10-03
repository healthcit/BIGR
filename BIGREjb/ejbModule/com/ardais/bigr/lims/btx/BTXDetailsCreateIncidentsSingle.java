package com.ardais.bigr.lims.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.lims.javabeans.IncidentReportLineItem;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a create incident business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setIncident(IncidentReportLineItem) incident}: An IncidentReportLineItem data bean, 
 * holding the incident information.</li>
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
public class BTXDetailsCreateIncidentsSingle extends BTXDetails implements java.io.Serializable {
  private static final long serialVersionUID = -1420990177015773284L;

  private IncidentReportLineItem _incident;
  
  /** 
   * Constructor
   */
  public BTXDetailsCreateIncidentsSingle() {
    super();
  }
  
  /**
   * Returns the incident.
   * @return IncidentReportLineItem
   */
  public IncidentReportLineItem getIncident() {
    return _incident;
  }

  /**
   * Sets the incident.
   * @param incident The incident to set
   */
  public void setIncident(IncidentReportLineItem incident) {
    _incident = incident;
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
    history.setAttrib1(getIncident().getIncidentId());
    history.setAttrib2(getIncident().getActionDescription());
    history.setAttrib3(getIncident().getActionOther());
    history.setAttrib4(getIncident().getReason());
    history.setAttrib5(getIncident().getSampleId());
  }
  
  /**
   * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
   * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
   * method. This method must not make use of any fields that aren't set by the 
   * populateFromHistoryRecord method. For this object type, the fields we can use here 
   * are:
   *  getIncident().getIncidentId()
   *  getIncident().getAction()
   *  getIncident().getActionOther()
   *  getIncident().getReason()
   *  getIncident().getSampleId()
   *
   * @return an HTML string that defines how the transaction details are presented
   * in a transaction-history web page
   */
  protected String doGetDetailsAsHTML() {
       
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();        
    StringBuffer sb = new StringBuffer(128);

    // The result has this form:
    //    <user> created incident <incidentId> with an action of <action> and a reason of
    //    <reason> for sample <sampleId>

    Escaper.htmlEscape(getUserId(), sb);
    sb.append(" created incident ");
    sb.append(IcpUtils.prepareLink(getIncident().getIncidentId(), securityInfo));
    sb.append(" with an action of \"");
    Escaper.htmlEscape(getIncident().getActionDescription(), sb);
    if (getIncident().getAction().equals(LimsConstants.INCIDENT_ACTION_OTHER)) {
      sb.append(" (");
      Escaper.htmlEscape(getIncident().getActionOther(),sb);
      sb.append(")");
    }
    sb.append("\" and a reason of \"");
    Escaper.htmlEscape(getIncident().getReason(),sb);
    sb.append("\" for sample ");
    sb.append(IcpUtils.prepareLink(getIncident().getSampleId(), securityInfo));
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
    return BTXDetails.BTX_TYPE_CREATE_INCIDENTS_SINGLE;
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

    if (getIncident() != null) {
      set.add(getIncident().getIncidentId());
      set.add(getIncident().getSampleId());
      String slideId = getIncident().getSlideId();
      if (slideId != null && !slideId.trim().equals("")) {
        set.add(slideId);
      }
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
    IncidentReportLineItem incident = new IncidentReportLineItem();
    incident.setIncidentId(history.getAttrib1());
    incident.setActionDescription(history.getAttrib2());
    incident.setActionOther(history.getAttrib3());
    incident.setReason(history.getAttrib4());
    incident.setSampleId(history.getAttrib5());
    setIncident(incident);
  }

}