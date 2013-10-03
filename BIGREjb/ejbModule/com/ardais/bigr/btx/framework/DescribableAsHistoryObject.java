package com.ardais.bigr.btx.framework;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * 
 */
public interface DescribableAsHistoryObject {

  /**
   * @return a BTX History Object representing the object that implements this interface.
   *   The returned object must be one for which
   *   {@link BtxHistoryObjectUtils#isValidHistoryObject(Object)} returns true.
   *   See {@link BtxHistoryObjectUtils} for more details on BTX History Objects.
   */
  public Object describeAsHistoryObject();

}
