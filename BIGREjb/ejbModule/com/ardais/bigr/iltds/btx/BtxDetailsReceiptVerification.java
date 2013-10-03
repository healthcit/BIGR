package com.ardais.bigr.iltds.btx;

import java.io.Serializable;
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
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a receipt verification business transaction.
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
 * <li>{@link #setSamples(List) samples}: The samples in the box.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BtxDetailsReceiptVerification extends BTXDetails implements java.io.Serializable {
	private static final long serialVersionUID = 6059289927971380918L;

	private String _manifestId = null;
	private String _trackingNumber = null;
	private BoxDto _box = null;
  private List _samples = null;

	/**
	 * BtxDetailsReceiptVerification constructor comment.
	 */
	public BtxDetailsReceiptVerification() {
		super();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (7/3/2002 10:14:25 AM)
	 * @param history com.ardais.bigr.iltds.btx.BTXHistoryRecord
	 */
	public void describeIntoHistoryRecord(BTXHistoryRecord history) {
		super.describeIntoHistoryRecord(history);

		history.setAttrib1(getTrackingNumber());
		history.setAttrib2(getManifestId());
		history.setBox(getBox());
    history.setHistoryObject(describeAsHistoryObject());
	}
  
	/**
	 * Return an HTML string that defines how the transaction details are presented
	 * in a transaction-history web page.  Derived classes must override this method.
	 * <p>
	 * This method is protected, the corresponding public method is
	 * {@link #getDetailsAsHTML() getDetailsAsHTML}, which calls this method.
	 * getDetailsAsHTML handles common tasks such as returning the
	 * details in the case that the transaction represents a failed transaction
	 * (it has a non-null exceptionText property).  For such a transaction, the
	 * doGetDetailsAsHTML method will not be called.  This framework is intended
	 * to make it easier to implement doGetDetailsAsHTML in derived classes, as
	 * the code there may assume that the transaction succeeded and that the
	 * transaction's data fields aren't malformed.
	 * <p>
	 * <b>Implementation of this method must only make use of fields that are populated
	 * by the populateFromHistory method.</b>
	 *
	 * @return the HTML detail string.
	 */
	protected String doGetDetailsAsHTML() {
		// NOTE: This method must not make use of any fields that aren't
		// set by the populateFromHistoryRecord method.
		//
		// For this object type, the fields we can use here are:
		//   getBox
		//   getManifestId
		//   getTrackingNumber
    //   getSamples
        
        SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

		StringBuffer sb = new StringBuffer(2048);

		// The result has this form:
		//    Receipt verification of manifest <manifest id> with tracking #: <tracking number>
    //    and box <box id and contents>

		String manifestId = getManifestId();
		if (ApiFunctions.safeStringLength(manifestId) > 0) {
			sb.append("Receipt verification of manifest ");
			sb.append(IcpUtils.prepareLink(manifestId, securityInfo));
		}

		String trackingNumber = getTrackingNumber();
		if (ApiFunctions.safeStringLength(trackingNumber) > 0) {
			sb.append(" with tracking #: ");
			sb.append(IcpUtils.prepareLink(trackingNumber, securityInfo));
		}

		sb.append(" and box ");

		sb.append(IcpUtils.prepareLink(_box.getBoxId(), securityInfo));
    
    //if there were samples passed in, use them to update the box contents so
    //we can display the alias values in addition to the sample ids for each
    //sample that has an alias
    List samples = getSamples();
    if (!ApiFunctions.isEmpty(samples)) {
      Map existingContents = _box.getContents();
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
          _box.setCellContent(
              cellRef,
              newCellContent.toString(),
              true);
        }
      }
    }
		_box.appendICPHTML(sb, securityInfo);

		return sb.toString();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (7/26/2002 3:08:14 PM)
	 * @return com.ardais.bigr.iltds.assistants.BoxDto
	 */
	public com.ardais.bigr.javabeans.BoxDto getBox() {
		return _box;
	}
	/**
	 * Return the business transaction type code for the transaction that this
	 * class represents.  Derived classes must override this method.
	 *
	 * @return the transaction type code.
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_RECEIPT_VERIFICATION;
	}
	/**
	 * Return a set of the object ids of the objects that are directly involved
	 * in this transaction.  This set does not contain the ids of objects that
	 * are considered to be indirectly involved in the transaction, and it does
	 * not include the user id of the user who performed the transaction.
	 * <p>
	 * For example, a transaction that scans a box of samples directly involves the box
	 * object and each of the sample objects, and indirectly involves the
	 * asm, asm form, case and donor objects for each sample.
	 *
	 * @return the set of directly involved object ids.
	 */
	public java.util.Set getDirectlyInvolvedObjects() {
		Set set = null;

		if (_box == null) {
			set = new HashSet();
		} else {
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
	 * Return the id of the shipping manifest that accompanied the box.
	 *
	 * @return the manifest id.
	 */
	public java.lang.String getManifestId() {
		return _manifestId;
	}
	/**
	 * Return the shipper's tracking number for the shipment that included this box.
	 *
	 * @return the shipment tracking number.
	 */
	public java.lang.String getTrackingNumber() {
		return _trackingNumber;
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

		setManifestId(history.getAttrib2());
		setTrackingNumber(history.getAttrib1());
		setBox(history.getBoxDto());
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
	/**
	 * Insert the method's description here.
	 * Creation date: (7/26/2002 3:08:14 PM)
	 * @param newBox com.ardais.bigr.iltds.assistants.BoxDto
	 */
	public void setBox(com.ardais.bigr.javabeans.BoxDto newBox) {
		_box = newBox;
	}
	/**
	 * Set the shipper's manifest number for the shipment that included this box.
	 *
	 * @param new_manifestId the shipment manifest number.
	 */
	public void setManifestId(java.lang.String new_manifestId) {
		_manifestId = new_manifestId;
	}
	/**
	 * Set the shipper's tracking number for the shipment that included this box.
	 *
	 * @param newTrackingNumber the shipment tracking number.
	 */
	public void setTrackingNumber(java.lang.String new_trackingNumber) {
		_trackingNumber = new_trackingNumber;
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