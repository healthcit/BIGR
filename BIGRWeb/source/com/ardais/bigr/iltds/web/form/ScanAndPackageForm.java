package com.ardais.bigr.iltds.web.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxDetailsConfirmBoxOnManifest;
import com.ardais.bigr.iltds.btx.BtxDetailsGetManifest;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.ManifestDto;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author gyost
 *
 * This Struts ActionForm is shared by the actions involved in the Scan and Package Manifest
 * transaction.
 */
public class ScanAndPackageForm extends BigrActionForm {
  private ManifestDto _manifestDto = new ManifestDto();
  private String _boxId = null;
  private String[] _allBoxIds = null;
  private List _allBoxesList = null;
  private String[] _confirmedBoxIds = null;
  private Set _confirmedBoxIdSet = null;

  // Inner class that represents boxes and whether or not their presence on the manifest
  // has been confirmed.  This represents the information in a way that's easy to use on a
  // JSP page.  The allBoxes property is a list of BoxStatus objects.
  //
  public static class BoxStatus {
    private String _boxId = null;
    private boolean _confirmed = false;
    BoxStatus(String boxId, boolean isConfirmed) {
      _boxId = boxId;
      _confirmed = isConfirmed;
    }
    public String getBoxId() {
      return _boxId;
    }
    public boolean isConfirmed() {
      return _confirmed;
    }
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _manifestDto = new ManifestDto();
    _boxId = null;
    _allBoxIds = null;
    _allBoxesList = null;
    _confirmedBoxIds = null;
    _confirmedBoxIdSet = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    return null;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails)
   */
  public void populateFromBtxDetails(BTXDetails btxDetails0) {
    super.populateFromBtxDetails(btxDetails0);

    // There's some additional result information that we need to copy from btxDetails
    // if the transaction completed successfully.
    //
    if (btxDetails0.isTransactionCompleted()) {
      if (btxDetails0 instanceof BtxDetailsGetManifest) {
        // If we're in the action that processes the manifest id that the user entered, then
        // we need to populate the AllBoxIds property with the list of add boxes on the manifest
        // in manifest order.

        BtxDetailsGetManifest btxDetails = (BtxDetailsGetManifest) btxDetails0;
        List boxes = btxDetails.getManifestDto().getBoxes();
        if (boxes == null) {
          setAllBoxIds(ApiFunctions.EMPTY_STRING_ARRAY);
        }
        else {
          String[] boxIds = new String[boxes.size()];
          Iterator iter = boxes.iterator();
          int i = 0;
          while (iter.hasNext()) {
            BoxDto box = (BoxDto) iter.next();
            boxIds[i++] = box.getBoxId();
          }
          setAllBoxIds(boxIds);
        }
      }
      else if (btxDetails0 instanceof BtxDetailsConfirmBoxOnManifest) {
        // If we're in the action that confirms that a scanned box id is valid and on the
        // manifest, then we need to add all valid/confirmed box ids to _confirmedBoxIdSet.

        BtxDetailsConfirmBoxOnManifest btxDetails = (BtxDetailsConfirmBoxOnManifest) btxDetails0;
        if (btxDetails.isConfirmed()) {
          addConfirmedBoxId(btxDetails.getBoxId());
        }
      }
    }
  }

  /**
   * @return
   */
  private String[] getAllBoxIds() {
    return _allBoxIds;
  }

  /**
   * @return An iterator over a list of BoxStatus objects (inner class) representing all boxes on
   *   the manifest together with their confirmation status.  The list is sorted in manifest order.
   */
  public Iterator getAllBoxes() {
    if (_allBoxesList == null) {
      String[] boxIds = getAllBoxIds();
      if (boxIds == null) {
        _allBoxesList = Collections.EMPTY_LIST;
      }
      else {
        Set confirmedBoxIds = getConfirmedBoxIdSet();
        _allBoxesList = new ArrayList();
        for (int i = 0; i < boxIds.length; i++) {
          String boxId = boxIds[i];
          _allBoxesList.add(new BoxStatus(boxId, confirmedBoxIds.contains(boxId)));
        }
      }
    }
    return Collections.unmodifiableList(_allBoxesList).iterator();
  }

  /**
   * @return True if all of boxes on the manifest have been confirmed.
   */
  public boolean isAllBoxesConfirmed() {
    return (getAllBoxesCount() == getConfirmedBoxIdsCount());
  }

  /**
   * @return The number of boxes on the manifest.
   */
  public int getAllBoxesCount() {
    String[] boxIds = getAllBoxIds();
    return ((boxIds == null) ? 0 : boxIds.length);
  }

  /**
   * @return
   */
  public String getBoxId() {
    return _boxId;
  }

  /**
   * Return the array of box ids that the user has entered and that we've confirmed are on the
   * manifest.  See also {@link #getConfirmedBoxIdSet()}, which returns these ids as a set.
   * In some situations, the set it returns is more up-to-date than the array that this
   * returns.  This method is here primarily to allow Struts to automatically copy the ids
   * in from a submitted web page. 
   * 
   * @return The confirmed box ids. 
   */
  private String[] getConfirmedBoxIds() {
    return _confirmedBoxIds;
  }

  /**
   * Return the set of box ids that the user has entered and that we've confirmed are on the
   * manifest.  See also {@link #getConfirmedBoxIds()}, which returns these ids as an array.
   * In some situations, the set this returns is more up-to-date than the array that method
   * returns.
   * 
   * @return The confirmed box ids. 
   */
  private Set getConfirmedBoxIdSet() {
    if (_confirmedBoxIdSet == null) {
      String[] boxIds = getConfirmedBoxIds();
      if (boxIds == null) {
        _confirmedBoxIdSet = new HashSet();
      }
      else {
        _confirmedBoxIdSet = new HashSet(Arrays.asList(boxIds));
      }
    }
    return _confirmedBoxIdSet;
  }

  /**
   * @return The number of confirmed boxes on the manifest.
   */
  public int getConfirmedBoxIdsCount() {
    return getConfirmedBoxIdSet().size();
  }

  /**
   * Add a box to the ConfirmedBoxIdSet.  This affects the value returned by getConfirmedBoxIdSet
   * but not the one returned by getConfirmedBoxIds.
   * 
   * @param boxId The box id.
   */
  private void addConfirmedBoxId(String boxId) {
    // Have to call getConfirmedBoxIdSet because _confirmedBoxIdSet doesn't get initialized
    // until then.
    //
    getConfirmedBoxIdSet().add(boxId);
    // Force _allBoxesList to be recomputed the next time getAllBoxes is called.
    _allBoxesList = null;
  }

  /**
   * @return
   */
  public ManifestDto getManifestDto() {
    return _manifestDto;
  }

  /**
   * @param strings
   */
  public void setAllBoxIds(String[] strings) {
    _allBoxIds = strings;
    // Force _allBoxesList to be recomputed the next time getAllBoxes is called.
    _allBoxesList = null;
  }

  /**
   * @param string
   */
  public void setBoxId(String string) {
    _boxId = string;
  }

  /**
   * @param strings
   */
  public void setConfirmedBoxIds(String[] strings) {
    _confirmedBoxIds = strings;
  }

  /**
   * @param dto
   */
  public void setManifestDto(ManifestDto dto) {
    _manifestDto = dto;
  }

}
