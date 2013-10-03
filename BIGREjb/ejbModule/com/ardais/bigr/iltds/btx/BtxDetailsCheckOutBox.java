package com.ardais.bigr.iltds.btx;

import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.javabeans.BoxLayoutDto;

/**
 * Represents the details of a business transaction to check out box.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setBoxId(String) boxId}: The box id to check out.</li>
 * <li>{@link #setBoxLayoutDto(BoxLayoutDto) boxLayoutDto}: The box layout to use.</li>
 * <li>{@link #setSamples(Vectors) samples}: The samples in the box.</li>
 * <li>{@link #setReason(String) reason}: The reason for the check out.</li>
 * <li>{@link #setRequestedBy(String) requestedBy}: The requested username.</li>
 * <li>{@link #setClearSampleBoxInformation(boolean) flag}: Clear box location.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setComment(String) comment}: Comment field.</li>
 * <li>{@link #setSampleIdsNotToBeOrphaned(List list) sampleIdsNotToBeOrphaned}: A list of sample 
 * ids that should not be orphaned as part of checking out the box.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setEmptyBoxes(Vectors) emptyBoxes}: The empty boxes left behind from a checkout.</li>
 * </ul>
 */
public class BtxDetailsCheckOutBox extends BTXDetails {
  static final long serialVersionUID = -5703025936017186987L;
  
  private String _boxId;
  private BoxLayoutDto _boxLayoutDto;
  private Vector _samples;
  private String _reason;
  private String _comment;
  private String _requestedBy;
  private boolean _clearSampleBoxInformation;
  private String _requestId;
  private Vector _oldBoxIds;
  private Vector _emptyBoxes;
  private List _fulfilledSampleIds;
  
  public BtxDetailsCheckOutBox() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTX_TYPE_CHECK_OUT_BOX;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    throw new ApiException("Called in error, this BTXDetails type is not logged.");
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    throw new ApiException("Called in error, this BTXDetails type is not logged.");
  }

  /**
   * @return
   */
  public String getBoxId() {
    return _boxId;
  }

  /**
   * @return
   */
  public BoxLayoutDto getBoxLayoutDto() {
    return _boxLayoutDto;
  }

  /**
   * @return
   */
  public boolean isClearSampleBoxInformation() {
    return _clearSampleBoxInformation;
  }

  /**
   * @return
   */
  public String getComment() {
    return _comment;
  }

  /**
   * @return
   */
  public Vector getEmptyBoxes() {
    return _emptyBoxes;
  }

  /**
   * @return
   */
  public Vector getOldBoxIds() {
    return _oldBoxIds;
  }

  /**
   * @return
   */
  public String getReason() {
    return _reason;
  }

  /**
   * @return
   */
  public String getRequestedBy() {
    return _requestedBy;
  }

  /**
   * @return
   */
  public String getRequestId() {
    return _requestId;
  }

  /**
   * @return
   */
  public Vector getSamples() {
    return _samples;
  }

  /**
   * @param string
   */
  public void setBoxId(String string) {
    _boxId = string;
  }

  /**
   * @param dto
   */
  public void setBoxLayoutDto(BoxLayoutDto dto) {
    _boxLayoutDto = dto;
  }

  /**
   * @param b
   */
  public void setClearSampleBoxInformation(boolean b) {
    _clearSampleBoxInformation = b;
  }

  /**
   * @param string
   */
  public void setComment(String string) {
    _comment = string;
  }

  /**
   * @param vector
   */
  public void setEmptyBoxes(Vector vector) {
    _emptyBoxes = vector;
  }

  /**
   * @param vector
   */
  public void setOldBoxIds(Vector vector) {
    _oldBoxIds = vector;
  }

  /**
   * @param string
   */
  public void setReason(String string) {
    _reason = string;
  }

  /**
   * @param string
   */
  public void setRequestedBy(String string) {
    _requestedBy = string;
  }

  /**
   * @param string
   */
  public void setRequestId(String string) {
    _requestId = string;
  }

  /**
   * @param vector
   */
  public void setSamples(Vector vector) {
    _samples = vector;
  }
  
  /**
   * @return
   */
  public List getFulfilledSampleIds() {
    return _fulfilledSampleIds;
  }

  /**
   * @param map
   */
  public void setFulfilledSampleIds(List list) {
    _fulfilledSampleIds = list;
  }
}
