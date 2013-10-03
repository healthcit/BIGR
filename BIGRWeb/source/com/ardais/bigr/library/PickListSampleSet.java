package com.ardais.bigr.library;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.library.btx.BTXDetailsAddToHoldList;
import com.ardais.bigr.security.SecurityInfo;

/**
 * @author dfeldman
 *
 * This is a SimpleSampleSet subclass that overrides the behavior of the SimpleSampleSet
 * to check that items being added to the set are appropriate, per the business rules
 * defined in the PickListManager.
 */
public class PickListSampleSet extends SimpleSampleSet {

  public PickListSampleSet(SecurityInfo securityInfo) {
    super(securityInfo);
  }

  /**
   * Sets the Unavailable Sample IDs collection to those that are invalid for picklist
   * because of underlying sample statuses.
   * 
   * @see com.ardais.bigr.library.SampleSet#addToHoldList(String[])
   */
  protected BTXDetailsAddToHoldList getInvalidIds(BTXDetailsAddToHoldList btx) {
    try {
      btx.setTransactionType("library_set_unavailable_for_pick_list");
      btx = (BTXDetailsAddToHoldList) Btx.perform(btx);
      return btx;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return null; // unreached, keep compiler happy
    }
  }
}
