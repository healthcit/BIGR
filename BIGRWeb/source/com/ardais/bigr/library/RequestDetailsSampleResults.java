package com.ardais.bigr.library;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.bizlogic.RequestFinder;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.RequestSelect;
import com.ardais.bigr.javabeans.RequestDto;
import com.ardais.bigr.javabeans.RequestItemDto;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.query.SampleResults;
import com.ardais.bigr.query.SampleSelectionSummary;
import com.ardais.bigr.security.SecurityInfo;

/**
 * @author jesielionis
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RequestDetailsSampleResults extends SampleResults {

  private String _requestNumber;

  /**
   * Constructor
   */
  public RequestDetailsSampleResults(SecurityInfo secInfo, String requestNumber) {
    super(secInfo);
    _requestNumber = requestNumber;
 }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSampleData()
   */
  public BTXDetailsGetSamples getSampleData(BTXDetailsGetSamples btx) {
    try {
      btx.setSampleIds(getRequestItemIds());
      btx.setTransactionType("library_get_details");
      btx = (BTXDetailsGetSamples) Btx.perform(btx);

      // Get the sample types from the sample ids.
      String[] sampleIds = btx.getSampleIds();
      Set sampleTypes = IltdsUtils.getSampleTypesBySampleIds(ApiFunctions.safeToList(sampleIds));
      btx.setSampleTypes(sampleTypes);

      return btx;
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
  }
  
  //private method to get a String[] of the item ids on the request
  private String[] getRequestItemIds() {
    RequestSelect select = RequestSelect.ITEM_BASICS_ONLY;
    RequestDto requestDto = RequestFinder.find(getSecurityInfo(), select, _requestNumber);
    int itemCount = requestDto.getItemCount();
    String[] itemIds = new String[itemCount];
    if (itemCount > 0) {
      itemCount = 0;
      Iterator iterator = requestDto.getItems().iterator();
      while (iterator.hasNext()) {
        RequestItemDto requestItem = (RequestItemDto)iterator.next();
        itemIds[itemCount] = requestItem.getItemId();
        itemCount = itemCount + 1;
      }
    }
    return itemIds;
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
    return "Request Details";
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getProductType()
   */
  public String getProductType() {
    return "";
  }


}
