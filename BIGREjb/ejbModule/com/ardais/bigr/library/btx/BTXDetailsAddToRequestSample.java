package com.ardais.bigr.library.btx;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;

/**
 * @author sislam
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BTXDetailsAddToRequestSample extends BTXDetailsAddToHoldList {

  private static final String isPickListDBFlag = "typePickList";
  private static final String notPickListDBFlag = "typeNotPickList";

  private Map _idsAndAmounts = new HashMap();
  private String[] _unavailSamples;
  private boolean _isPickList = false;

  public BTXDetailsAddToRequestSample() {
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_ADD_TO_REQUEST_SAMPLE;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    Set objs = new HashSet();
    objs.addAll(_idsAndAmounts.keySet());
    return objs;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    String msg = "BTXDetailsAddToRequestSample.doGetDetailsAsHTML() method called in error!";
    return msg;
  }

  /**
   * Returns the samples.
   * @return String[]
   */
  public String[] getSamples() {
    return (String[]) _idsAndAmounts.keySet().toArray(new String[0]);
  }

  /**
   * Returns the unavailSamples.
   * @return String[]
   */
  public String[] getUnavailSamples() {
    return _unavailSamples;
  }

  public void setIdsNoAmounts(String[] ids) {
    Map m = new HashMap();
    for (int i = 0; i < ids.length; i++) {
      m.put(ids[i], null);
    }
    setIdsAndAmounts(m);
  }

  /**
   * Sets the sample ids and amounts on hold for each.
   * @param samples The samples to set with an Integer specifying the amoutn requested for hold
   */
  public void setIdsAndAmounts(Map idsAndAmts) {
    _idsAndAmounts = idsAndAmts;
  }
  /**
   * Get the sample ids and the Integer amount on hold for each.
   * @param samples The samples to set
   */
  public Map getIdsAndAmounts() {
    return _idsAndAmounts;
  }

  /**
   * Sets the unavailSamples.
   * @param unavailSamples The unavailSamples to set
   */
  public void setUnavailSamples(String[] unavailSamples) {
    _unavailSamples = unavailSamples;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
  }

  /**
   * Returns the isPickList.
   * @return boolean
   */
  public boolean isPickList() {
    return _isPickList;
  }

  /**
   * Sets the isPickList.
   * @param isPickList The isPickList to set
   */
  public void setIsPickList(boolean isPickList) {
    _isPickList = isPickList;
  }

}
