package com.ardais.bigr.iltds.btx;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a box scan business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setBox(BoxDto) BoxDto}: The box to scan.  This must include both
 *     the box id and the box contents.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setSamples(List) samples}: The samples in the box.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setBoxLocation(BTXBoxLocation) BoxDto location}: The storage location that was
 *     assigned to the box.</li>
 * <li>{@link #setSourceBoxList(IdList) Source box id list}: The list of box ids for the
 *     boxes that contained the scanned samples prior to the box scan.</li>
 * <li>{@link #setEmptyBoxList(IdList) Empty box id list}: The list of box ids for the
 *     boxes that became empty as a result of the box scan.</li>
 * <li>{@link #setEmptyBoxLocationList(List) Empty box location list}: The list of box locations
 *     for the boxes that became empty as a result of the box scan.  Each list element is a
 *     {@link BTXBoxLocation} object.</li>
 * </ul>
 */
public class BTXDetailsBoxScan
    extends BTXDetails
    implements java.io.Serializable {
    private static final long serialVersionUID = -2623913577636355346L;

    private BTXBoxLocation _boxLocation = null;
    private BoxDto _box = null;
    private IdList _emptyBoxList = null;
    private List _emptyBoxLocationList = null;
    private IdList _sourceBoxList = null;
    private List _samples = null;

    public BTXDetailsBoxScan() {
        super();
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
        history.setIdList1(getSourceBoxList());
        history.setHistoryObject(describeAsHistoryObject());
    }

    protected String doGetDetailsAsHTML() {
        // NOTE: This method must not make use of any fields that aren't
        // set by the populateFromHistoryRecord method.
        //
        // For this object type, the fields we can use here are:
        //   getBox
        //   getBoxLocation
        //   getSourceBoxList
        //   getSamples
        
        SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

        StringBuffer sb = new StringBuffer(2048);

        // The result has this form:
        //    Box <boxid> stored at <location description>
        //    <icon of box and its contents>

        BoxDto box = getBox();

        sb.append("Box ");
        sb.append(IcpUtils.prepareLink(box.getBoxId(), securityInfo));
        sb.append(" stored at ");
        getBoxLocation().appendICPHTML(sb, securityInfo);
        
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

        IdList sourceBoxes = getSourceBoxList();
        if (sourceBoxes != null && sourceBoxes.size() > 0) {
            sb.append(
                "Some or all of the samples were in these boxes at the time of this box scan: ");
            sourceBoxes.appendICPHTML(sb, securityInfo);
        }

        return sb.toString();
    }

    /**
     * Return the box involved in the transaction.
     *
     * @return the box.
     */
    public BoxDto getBox() {
        return _box;
    }

    /**
     * Return the location assigned to the box involved in the transaction.
     *
     * @return the box location.
     */
    public BTXBoxLocation getBoxLocation() {
        return _boxLocation;
    }

    public String getBTXType() {
        return BTXDetails.BTX_TYPE_BOX_SCAN;
    }

    public Set getDirectlyInvolvedObjects() {
        Set set = null;

        if (_box == null) {
            set = new HashSet();
        }
        else {
            set = _box.getDirectlyInvolvedObjects();
        }

        if (_sourceBoxList != null) {
            set.addAll(_sourceBoxList.getDirectlyInvolvedObjects());
        }

        return set;
    }

    /**
     * Return the list of box ids for the boxes that became empty as
     * a result of the box scan.
     *
     * @return the list of box ids.
     */
    public IdList getEmptyBoxList() {
        return _emptyBoxList;
    }

    /**
     * Return the list of box locations for the boxes that became empty as
     * a result of the box scan.  Each list element is a {@link BTXBoxLocation}
     * object
     *
     * @return the list of box locations.
     */
    public List getEmptyBoxLocationList() {
        return _emptyBoxLocationList;
    }

    /**
     * Return the list of box ids for the boxes that contained the scanned
     * samples prior to the box scan.
     *
     * @return the list of box ids.
     */
    public IdList getSourceBoxList() {
        return _sourceBoxList;
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
        setSourceBoxList(history.getIdList1());
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

        // These fields don't correspond to anything in the history record
        // but we must set them anyways.
        //
        setEmptyBoxList(null);
        setEmptyBoxLocationList(null);
        
    }

    /**
     * Set the box involved in the transaction.
     *
     * @param newBox the box.
     */
    public void setBox(BoxDto newBox) {
        _box = newBox;
    }

    /**
     * Set the location assigned to the box involved in the transaction.
     *
     * @param newBoxLocation the box location.
     */
    public void setBoxLocation(BTXBoxLocation newBoxLocation) {
        _boxLocation = newBoxLocation;
    }

    /**
     * Set the list of box ids for the boxes that became empty as
     * a result of the box scan.
     *
     * @param newBoxIdList the list of box ids.
     */
    public void setEmptyBoxList(IdList newBoxIdList) {
        _emptyBoxList = newBoxIdList;
    }

    /**
     * Set the list of box locations for the boxes that became empty as
     * a result of the box scan.  Each list element must be a
     * {@link BTXBoxLocation} object.
     *
     * @param newBoxLocationList the list of box locations.
     */
    public void setEmptyBoxLocationList(List newBoxLocationList) {
        _emptyBoxLocationList = newBoxLocationList;
    }

    /**
     * Set the list of box ids for the boxes that contained the scanned
     * samples prior to the box scan.
     *
     * @param newBoxIdList the list of box ids.
     */
    public void setSourceBoxList(IdList newBoxIdList) {
        _sourceBoxList = newBoxIdList;
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