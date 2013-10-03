package com.ardais.bigr.lims.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.lims.javabeans.IncidentReportData;
import com.ardais.bigr.lims.javabeans.IncidentReportLineItem;

/**
 * Represent the details of a create incidents business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setIncidentReportData(IncidentReportData) incidentReportData}: An
 * IncidentReportData bean, holding a list of IncidentReportLineItem beans.</li>
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
public class BTXDetailsCreateIncidents extends BTXDetails implements Serializable {
  private static final long serialVersionUID = -7933233462897333963L;

  private IncidentReportData _incidentReportData;

  /** 
   * Constructor
   */
  public BTXDetailsCreateIncidents() {
    super();
  }

  /**
   * Returns the incidentReportData.
   * @return IncidentReportData
   */
  public IncidentReportData getIncidentReportData() {
    return _incidentReportData;
  }

  /**
   * Sets the incidentReportData.
   * @param incidentReportData The incidentReportData to set
   */
  public void setIncidentReportData(IncidentReportData incidentReportData) {
    _incidentReportData = incidentReportData;
  }

  /**
   * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
   * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
   * method.  Since this transaction is not logged, there won't be any history so this 
   * method should never be called.  If for some reason this method is called it 
   * returns a message to the effect that it was called in error.
   *
   * @return an HTML string that defines how the transaction details are presented
   * in a transaction-history web page
   */
  protected String doGetDetailsAsHTML() {
    String msg = "BTXDetailsCreateIncidents.doGetDetailsAsHTML() method called in error!";
    return msg;
  }

  /**
   * Return the business transaction type code for the transaction that this
   * class represents.
   *
   * @return the transaction type code.
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CREATE_INCIDENTS;
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
    if (getIncidentReportData() != null) {
      List newIncidents = getIncidentReportData().getLineItems();
      Iterator iterator = newIncidents.iterator();
      while (iterator.hasNext()) {
        IncidentReportLineItem incident = (IncidentReportLineItem) iterator.next();
        set.add(incident.getSampleId());
        set.add(incident.getSlideId());
      }
    }

    return set;
  }

}
