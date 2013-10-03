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
 * Represent the details of a request items boxed business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setBox(BoxDto) BoxDto}: The box that transports the request items.
 *     This must include both the box id and the box contents.  The box contents are
 *     the request items.</li>
 * <li>{@link #setRequestId(String) requestId}: The id of the request to which the items belong.</li>
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
public class BtxDetailsRequestItemsBoxed extends BTXDetails implements java.io.Serializable {
	private static final long serialVersionUID = 6196376873506097794L;

	private BoxDto _box = null;
	private String _requestId = null;
  private List _samples = null;

	public BtxDetailsRequestItemsBoxed() {
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
		history.setAttrib1(getRequestId());
    history.setHistoryObject(describeAsHistoryObject());
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
  protected String doGetDetailsAsHTML() {
		// NOTE: This method must not make use of any fields that aren't
		// set by the populateFromHistoryRecord method.
		//
		// For this object type, the fields we can use here are:
		//   getBox
		//   getRequestId
    //   getSamples
        
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

		StringBuffer sb = new StringBuffer(2048);

		// The result has this form:
		//    Items were packaged in box <box id> for the fulfillment of request <request id>.
		//    <icon of box and its contents>

		BoxDto box = getBox();
		sb.append("Items were packaged in box ");
		sb.append(IcpUtils.prepareLink(box.getBoxId(), securityInfo));
		sb.append(" for the fulfillment of request ");
    sb.append(IcpUtils.prepareLink(getRequestId(), securityInfo));
    sb.append(".");
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
  
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_REQUEST_ITEMS_BOXED;
	}
  
	public java.util.Set getDirectlyInvolvedObjects() {
		Set set = null;

		if (getBox() == null) {
			set = new HashSet();
		} else {
			set = getBox().getDirectlyInvolvedObjects();
		}

		if (getRequestId() != null) {
			set.add(getRequestId());
		}

		return set;
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
		setRequestId(history.getAttrib1());
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
  
  /**
   * Return the box involved in the transaction.
   *
   * @return the box.
   */
  public BoxDto getBox() {
    return _box;
  }
  
  /**
   * Return the request Id.
   *
   * @return the request Id.
   */
  public String getRequestId() {
    return _requestId;
  }

	/**
	 * Set the id of the box involved in the transaction.
	 *
	 * @param newBox the box id.
	 */
	public void setBox(BoxDto newBox) {
		_box = newBox;
	}

	/**
	 * Set the request Id.
	 *
	 * @param requestId the id of the request.
	 */
	public void setRequestId(String requestId) {
		_requestId = requestId;
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

}