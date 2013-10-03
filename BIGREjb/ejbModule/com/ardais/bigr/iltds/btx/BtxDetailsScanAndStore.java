package com.ardais.bigr.iltds.btx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a scan-and-store business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setBox(BoxDto) BoxDto}: The box to scan.  This must include both
 *     the box id and the box contents.</li>
 * <li>{@link #setManifestId(String) Manifest id}: The id of the shipping manifest that
 *     accompanied the box.</li>
 * <li>{@link #setTrackingNumber(String) Tracking number}: The shipper's tracking number
 *     for the shipment that included this box.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setStorableSampleIdList(IdList) Storable sample id list}: The list of ids that
 *     can be stored at this site.</li>
 * <li>{@link #setUnstorableSampleIdList(IdList) Unstorable sample id list}: The list of ids that
 *     cannot be stored at this site.</li>
 * <li>{@link #setSamples(List) samples}: The samples in the box.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setBoxLocation(BTXBoxLocation) BoxDto location}: The storage location that was
 *     assigned to the box.</li>
 * </ul>
 */
public class BtxDetailsScanAndStore extends BtxDetailsShippingOperation implements Serializable {
  private static final long serialVersionUID = 6059280027971380918L;

  private BTXBoxLocation _boxLocation = null;
  private String _manifestId = null;
  private String _trackingNumber = null;
  private String _scannedBoxId = null;
  private BoxDto _box = null;
  private IdList _storableSampleIdList = null;
  private IdList _unstorableSampleIdList = null;
  private List _samples = null;

  public BtxDetailsScanAndStore() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_SCAN_AND_STORE;
  }

  public java.util.Set getDirectlyInvolvedObjects() {
    Set set = null;

    if (_box == null) {
      set = new HashSet();
    }
    else {
      set = _box.getDirectlyInvolvedObjects();
    }

    if (_manifestId != null) {
      set.add(_manifestId);
    }

    if (_trackingNumber != null) {
      set.add(_trackingNumber);
    }

    return set;
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

    history.setBox(getBox());
    history.setBoxLocation1(getBoxLocation());
    history.setAttrib1(getManifestId());
    history.setAttrib2(getTrackingNumber());
    history.setIdList1(getStorableSampleIdList());
    history.setIdList2(getUnstorableSampleIdList());
    history.setHistoryObject(describeAsHistoryObject());
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

    setBox(history.getBoxDto());
    setBoxLocation(history.getBoxLocation1());
    setManifestId(history.getAttrib1());
    setTrackingNumber(history.getAttrib2());
    setStorableSampleIdList(history.getIdList1());
    setUnstorableSampleIdList(history.getIdList2());
    BtxHistoryAttributes attributes = (BtxHistoryAttributes)history.getHistoryObject();
    if (attributes != null) {
      List samples = new ArrayList();
      setSamples(samples);
      Map map = attributes.asMap();
      if (!map.isEmpty()) {
        Iterator sampleIdIterator = map.keySet().iterator();
        while (sampleIdIterator.hasNext()) {
          SampleData sample = new SampleData();
          sample.setSampleId((String)sampleIdIterator.next());
          populateSampleAttributesFromHistoryObject(attributes, sample);
          samples.add(sample);
        }
      }
    }
  }

  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    //   getBox
    //   getBoxLocation
    //   getManifestId
    //   getTrackingNumber
    //   getSamples

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(2048);

    // The result has this form:
    //    Box <box id> stored at <location description>, from <manifest id> (tracking #: <tracking number>)
    //    <icon of box and its contents>
    //    OR
    //    Box <box id> from <manifest id> (tracking #: <tracking number>) Warning: <warning>
    //    <icon of box and its contents>
    
    BoxDto box = getBox();

    sb.append("Box ");
    sb.append(IcpUtils.prepareLink(box.getBoxId(), securityInfo));
    
    BTXBoxLocation boxLocation = getBoxLocation();
    
    if (boxLocation != null) {
      sb.append(" stored at ");
      boxLocation.appendICPHTML(sb, securityInfo);
      sb.append(",");
    }

    String manifestId = getManifestId();
    if (ApiFunctions.safeStringLength(manifestId) > 0) {
      sb.append(" from ");
      sb.append(IcpUtils.prepareLink(manifestId, securityInfo));
    }

    String trackingNumber = getTrackingNumber();
    if (ApiFunctions.safeStringLength(trackingNumber) > 0) {
      sb.append(" (tracking #: ");
      sb.append(IcpUtils.prepareLink(trackingNumber, securityInfo));
      sb.append(')');
    }
    
    if (boxLocation == null) {
      sb.append(" Warning: ");
      
      IdList storableSampleIdList = getStorableSampleIdList();
      IdList unstorableSampleIdList = getUnstorableSampleIdList();
      HashMap storageMap = new HashMap();
      storageMap.put(IltdsUtils.STORABLE_SAMPLE_ID_LIST, storableSampleIdList);
      storageMap.put(IltdsUtils.UNSTORABLE_SAMPLE_ID_LIST, unstorableSampleIdList);

      IltdsUtils.determineStorageTypeWarning(securityInfo, storageMap, true, sb);
    }

    
    //if there were samples passed in, use them to update the box contents so
    //we can display the alias values in addition to the sample ids for each
    //sample that has an alias
    List samples = getSamples();
    if (!ApiFunctions.isEmpty(samples)) {
      Map existingContents = box.getContents();
      Iterator cellRefIterator = existingContents.keySet().iterator();
      while (cellRefIterator.hasNext()) {
        String cellRef = (String)cellRefIterator.next();
        String cellContent = (String)existingContents.get(cellRef);
        if (!ApiFunctions.isEmpty(cellContent)) {
          Iterator sampleIterator = samples.iterator();
          SampleData matchingSample = null;
          while (sampleIterator.hasNext() && matchingSample == null) {
            SampleData sample = (SampleData)sampleIterator.next();
            if (sample.getSampleId().equalsIgnoreCase(cellContent)) {
              matchingSample = sample;
            }
          }
          StringBuffer newCellContent = new StringBuffer(100);
          newCellContent.append(IcpUtils.prepareLink(matchingSample.getSampleId(), securityInfo));
          if (!ApiFunctions.isEmpty(matchingSample.getSampleAlias())) {
            newCellContent.append(" (");
            Escaper.htmlEscapeAndPreserveWhitespace(matchingSample.getSampleAlias(), newCellContent);
            newCellContent.append(")");
          }
          box.setCellContent(
              cellRef,
              newCellContent.toString(),
              true);
        }
      }
    }
    box.appendICPHTML(sb, securityInfo);

    return sb.toString();
  }
  
  /**
   * @return
   */
  public BoxDto getBox() {
    return _box;
  }

  /**
   * @return
   */
  public BTXBoxLocation getBoxLocation() {
    return _boxLocation;
  }

  /**
   * @return
   */
  public String getManifestId() {
    return _manifestId;
  }

  /**
   * @return
   */
  public String getScannedBoxId() {
    return _scannedBoxId;
  }

  /**
   * @return
   */
  public IdList getStorableSampleIdList() {
    return _storableSampleIdList;
  }

  /**
   * @return
   */
  public String getTrackingNumber() {
    return _trackingNumber;
  }

  /**
   * @return
   */
  public IdList getUnstorableSampleIdList() {
    return _unstorableSampleIdList;
  }

  /**
   * @param dto
   */
  public void setBox(BoxDto dto) {
    _box = dto;
  }

  /**
   * @param location
   */
  public void setBoxLocation(BTXBoxLocation location) {
    _boxLocation = location;
  }

  /**
   * @param string
   */
  public void setManifestId(String string) {
    _manifestId = string;
  }

  /**
   * @param string
   */
  public void setScannedBoxId(String string) {
    _scannedBoxId = string;
  }

  /**
   * @param list
   */
  public void setStorableSampleIdList(IdList list) {
    _storableSampleIdList = list;
  }

  /**
   * @param string
   */
  public void setTrackingNumber(String string) {
    _trackingNumber = string;
  }

  /**
   * @param list
   */
  public void setUnstorableSampleIdList(IdList list) {
    _unstorableSampleIdList = list;
  }
  
  /**
   * @return Returns the samples.
   */
  public List getSamples() {
    return _samples;
  }
  
  /**
   * @param samples The samples to set.
   */
  public void setSamples(List samples) {
    _samples = samples;
  }
  
  /**
   * Returns a BtxHistoryAttributes that describes the  samples involved
   * in the box scan.  This method creates an attribute for each sample id, with a value
   * of a BtxHistoryAttributes object that contains various values for each sample.  For now
   * the only value that we store is the sample alias, but additional attributes may be added
   * as needed.
   */
  private Object describeAsHistoryObject() {
    BtxHistoryAttributes attributes = null;
    List samples = getSamples();
    if (!ApiFunctions.isEmpty(samples)) {
      attributes = new BtxHistoryAttributes();
      Iterator sampleIterator = samples.iterator();
      while (sampleIterator.hasNext()) {
        SampleData sample = (SampleData) sampleIterator.next();
        BtxHistoryAttributes sampleAttributes = new BtxHistoryAttributes();
        attributes.setAttribute(sample.getSampleId(), sampleAttributes);
        sampleAttributes.setAttribute("sampleAlias", sample.getSampleAlias());
      }
    }
    return attributes;
  }
  
  private void populateSampleAttributesFromHistoryObject(BtxHistoryAttributes attributes, SampleData sample) {
    if (attributes != null) {
      BtxHistoryAttributes sampleAttributes = (BtxHistoryAttributes)attributes.getAttribute(sample.getSampleId());
      if (sampleAttributes != null) {
        if (sampleAttributes.containsAttribute("sampleAlias")) {
          sample.setSampleAlias((String)sampleAttributes.getAttribute("sampleAlias"));
        }
      }
    }
  }
}