package com.ardais.bigr.library;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.library.btx.BTXDetailsAddToHoldList;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.btx.BTXDetailsRemoveFromHoldList;
import com.ardais.bigr.library.performers.BtxPerformerHoldListManager;
import com.ardais.bigr.query.SampleSelectionSummary;
import com.ardais.bigr.security.SecurityInfo;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class HoldListSampleSet extends SampleSet {

  public HoldListSampleSet(SecurityInfo secInfo) {
    super(secInfo);
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#addToHoldList(List)
   */
  public BTXDetailsAddToHoldList addSamplesByIdWithAmounts(BTXDetailsAddToHoldList btx) {
    try {
      btx.setTransactionType("library_add_to_hold_list");
      btx = (BTXDetailsAddToHoldList) Btx.perform(btx);
      return btx;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return null; // unreached, keep compiler happy
    }
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSampleData()
   */
  public BTXDetailsGetSamples getSampleData(BTXDetailsGetSamples btx) {
    // no sample ids on btx object-- determined by hold list in back end
    try {
      btx.setTransactionType("library_get_hold_list_data");
      btx = (BTXDetailsGetSamples) Btx.perform(btx);

      // Get the sample types from the sample ids.
      String[] sampleIds = btx.getSampleIds();
      Set sampleTypes = IltdsUtils.getSampleTypesBySampleIds(ApiFunctions.safeToList(sampleIds));
      btx.setSampleTypes(sampleTypes);

      return btx;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return null; // unreached, keep compiler happy
    }
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSampleIds()
   */
  public String[] getSampleIds() {
    try {
      BtxPerformerHoldListManager holdBean = new BtxPerformerHoldListManager();
      return holdBean.getHoldListIds(getSecurityInfo());
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return null; // unreached, keep compiler happy
    }
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSummaryHelper()
   */
  public SampleSelectionSummary getSummary() {
    try {
      BtxPerformerHoldListManager holdBean = new BtxPerformerHoldListManager();
      SampleSelectionSummary summ = holdBean.getHoldListSummary(getSecurityInfo());
      return summ;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return null; // unreached, keep compiler happy
    }
  }

  /**
   * @param ids   the Strings representing the ids of samples to remove from hold
   * @return a List with SampleData objects for everything on Hold after remove operation.
   */
  public void removeIds(String[] ids) {
    try {
      BTXDetailsRemoveFromHoldList btx = new BTXDetailsRemoveFromHoldList();
      btx.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
      btx.setLoggedInUserSecurityInfo(getSecurityInfo(), true);
      btx.setSamples(ids);
      btx.setTransactionType("library_remove_from_hold_list");
      btx = (BTXDetailsRemoveFromHoldList) Btx.perform(btx);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#setSampleData(List)
   */
  public void setSampleData(List sampleData) {
    throw new UnsupportedOperationException("Cannot directly set hold list.  Use add() method");
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getSampleAmounts()
   */
  public Map getSampleAmounts() {
    try {
      BtxPerformerHoldListManager holdBean = new BtxPerformerHoldListManager();
      return holdBean.getHoldListAmounts(getSecurityInfo());
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return null; // unreached, keep compiler happy
    }
  }
}
