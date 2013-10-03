package com.ardais.bigr.iltds.web.form;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.assistants.Address;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxDetailsScanAndStore;
import com.ardais.bigr.iltds.btx.BtxDetailsShippingOperation;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.ManifestDto;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * A Struts ActionForm that represents a shipping manifest.
 */
public class ManifestForm extends BigrActionForm {

  private boolean _autoPrint = false;
  private List _boxes;
  private List _boxIdList;
  private String[] _boxIds;
  private String _manifestNumber;
  private String _producedByName;
  private Address _shipFromAddress;
  private Address _shipToAddress;
  private String _trackingNumber;
  
  private String _scannedBoxId;
  private boolean _finished;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    // This deliberately doesn't reset _manifestNumber, since sometimes we have one
    // manifest action forward to another manifest action in the context of the same
    // request, and the second action needs to have the manifestNumber.  For example,
    // the Create Manifest action forwards to the Print Manifest action, and Print Manifest
    // need the manifest number of the manifest to print.
    //
    // Currently none of the actions that we forward to in this way need anything preserved
    // other than the manifest number, so we reset all of the other fields to default values.
    
    _autoPrint = false;
    _boxes = null;
    _boxIds = null;
    _producedByName = null;
    _shipFromAddress = null;
    _shipToAddress = null;
    _trackingNumber = null;
    
    _scannedBoxId = null;
    _finished = false;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    // Nothing to validate here.  All validation takes place in the BTX performer.
    return null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void describeIntoBtxDetails(
    BTXDetails btxDetails0,
    BigrActionMapping mapping,
    HttpServletRequest request) {

    super.describeIntoBtxDetails(btxDetails0, mapping, request);

    // Create the manifest dto and populate it.
    ManifestDto dto = new ManifestDto();
    BigrBeanUtilsBean.SINGLETON.copyProperties(dto, this);
    String[] boxIds = getBoxIds();
    if (!ApiFunctions.isEmpty(boxIds)) {
      for (int i = 0; i < boxIds.length; i++) {
        dto.addBoxId(boxIds[i]);
      }
    }

    // Add the manifest dto to the btx details.
    BtxDetailsShippingOperation btx = (BtxDetailsShippingOperation) btxDetails0;
    btx.setManifestDto(dto);
  }

  /**
   * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails)
   */
  public void populateFromBtxDetails(BTXDetails btxDetails) {
    super.populateFromBtxDetails(btxDetails);

    // Get the manifest dto and populate this form from it.
    BtxDetailsShippingOperation btx = (BtxDetailsShippingOperation) btxDetails;
    ManifestDto dto = btx.getManifestDto();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, dto);

    List boxIds = dto.getBoxIdList();
    if (boxIds != null && boxIds.size() > 0) {
      setBoxIds(ApiFunctions.toStringArray(boxIds));
    }

    _boxes = dto.getBoxes();
    _boxIdList = dto.getBoxIdList();

    Address address = dto.getShipFromAddress();
    if (address != null) {
      _shipFromAddress = new Address();
      BigrBeanUtilsBean.SINGLETON.copyProperties(_shipFromAddress, address);
    }

    address = dto.getShipToAddress();
    if (address != null) {
      _shipToAddress = new Address();
      BigrBeanUtilsBean.SINGLETON.copyProperties(_shipToAddress, address);
    }
    
    // Needed to determine of the scan and store box scan section is complete.
    boolean finished = true;
    for (int i = 0; i < _boxes.size(); i++){
      BoxDto box = (BoxDto)_boxes.get(i);
      if (ApiFunctions.isEmpty(box.getLocation())) {
        finished = false;
        break;
      }
    }
    setFinished(finished);
  }

  public int getTotalSamples() {
    int total = 0;
    Iterator boxes = getBoxes().iterator();
    while (boxes.hasNext()) {
      BoxDto box = (BoxDto) boxes.next();
      total += box.getContents().size();
    }
    return total;
  }

  public String[] getBoxIds() {
    return _boxIds;
  }
  public void setBoxIds(String[] boxIds) {
    _boxIds = boxIds;
  }

  public String getManifestBarcode() {
    String manifestNumber = getManifestNumber();
    if (!ApiFunctions.isEmpty(manifestNumber)) {
      return FormLogic.checkSum(manifestNumber);
    }
    else {
      return null;
    }
  }
  public String getManifestNumber() {
    return _manifestNumber;
  }
  public void setManifestNumber(String manifestNumber) {
    _manifestNumber = manifestNumber;
  }

  public String getTrackingBarcode() {
    String trackingNumber = getTrackingNumber();
    if (!ApiFunctions.isEmpty(trackingNumber)) {
      return FormLogic.checkSum(trackingNumber);
    }
    else {
      return null;
    }
  }
  public String getTrackingNumber() {
    return _trackingNumber;
  }
  public void setTrackingNumber(String trackingNumber) {
    _trackingNumber = trackingNumber;
  }

  /**
   * @return If true, automatically print the manifest form when it opens.  Applies only
   *   to the print-manifest action (this form is shared by several actions).
   */
  public boolean isAutoPrint() {
    return _autoPrint;
  }

  public List getBoxes() {
    if (_boxes == null) {
      _boxes = new ArrayList();
    }
    return _boxes;
  }

  public List getBoxIdList() {
    if (_boxIdList == null) {
      _boxIdList = new ArrayList();
    }
    return _boxIdList;
  }

  public BoxDto getBox(int index) {
    return (BoxDto) getBoxes().get(index);
  }

  public int getBoxCount() {
    return getBoxes().size();
  }

  public Address getShipFromAddress() {
    return _shipFromAddress;
  }

  public Address getShipToAddress() {
    return _shipToAddress;
  }

  /**
   * @param b If true, automatically print the manifest form when it opens.  Applies only
   *   to the print-manifest action (this form is shared by several actions).
   */
  public void setAutoPrint(boolean b) {
    _autoPrint = b;
  }

  public void setBoxes(List list) {
    _boxes = list;
  }

  public void setBoxIdList(List list) {
    _boxIdList = list;
  }

  public void setShipFromAddress(Address address) {
    _shipFromAddress = address;
  }

  public void setShipToAddress(Address address) {
    _shipToAddress = address;
  }

  public String getProducedByName() {
    return _producedByName;
  }

  public void setProducedByName(String string) {
    _producedByName = string;
  }

  /**
   * @return
   */
  public String getScannedBoxId() {
    return _scannedBoxId;
  }

  /**
   * @param string
   */
  public void setScannedBoxId(String string) {
    _scannedBoxId = string;
  }

  /**
   * @return
   */
  public boolean isFinished() {
    return _finished;
  }

  /**
   * @param b
   */
  public void setFinished(boolean b) {
    _finished = b;
  }
}
