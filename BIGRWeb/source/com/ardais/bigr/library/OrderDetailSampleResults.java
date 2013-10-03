package com.ardais.bigr.library;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.query.SampleResults;
import com.ardais.bigr.query.SampleSelectionSummary;
import com.ardais.bigr.security.SecurityInfo;

/**
 * @author sislam
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OrderDetailSampleResults extends SampleResults {

  private String _orderNumber;

  /**
   * Constructor for OrderDetailSampleSet.
   */
  public OrderDetailSampleResults(SecurityInfo secInfo, String orderNo) {
    super(secInfo);
    try {
      _orderNumber = orderNo;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSampleData()
   */
  public BTXDetailsGetSamples getSampleData(BTXDetailsGetSamples btx) {
    btx.setOrderNumber(_orderNumber); //@todo:  set this in the request, not in constructor
    try {
      btx.setTransactionType("library_get_order_line_data");
      btx = (BTXDetailsGetSamples) Btx.perform(btx);

      // Get the sample types from the sample ids.
      String[] sampleIds = btx.getSampleIds();
      Set sampleTypes = IltdsUtils.getSampleTypesBySampleIds(ApiFunctions.safeToList(sampleIds));
      btx.setSampleTypes(sampleTypes);

      return btx;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return null;
    }
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSummary()
   */
  public SampleSelectionSummary getSummary() {
    return null;
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSampleIds()
   */
  public String[] getSampleIds(int chunk) {
    throw new UnsupportedOperationException("OrderDetailsResultSet does not support getSampleIds()");
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#clearSelectedIds()
   */
  public void clearSelectedIds() {
    // do nothing - I do not ever have any Ids selected
  }

  public void addSelectedIds(String[] ignore) {
    // do nothing - I do not ever have any Ids selected
  }

  public void clearSelectedIdsForCurrentChunk() {
    // do nothing - I do not ever have any Ids selected
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getSelectedIds()
   */
  public String[] getSelectedIds() {
    return new String[0];
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getSampleAmounts()
   */
  public Map getSampleAmounts() {
    return Collections.EMPTY_MAP;
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getDisplay()
   */
  public String getDisplay() {
    return "Order Details";
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getProductType()
   */
  public String getProductType() {
    return "";
  }
}
