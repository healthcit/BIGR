package com.ardais.bigr.iltds.btx;

import java.util.Set;
import java.util.Vector;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.javabeans.BoxLayoutDto;

/**
 * Represents the details of a business transaction to create box.
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
 * <li>{@link #setCellRefs(Vectors) cellRefs}: The cell references.</li>
 * <li>{@link #setStorageTypeCid(String) storageTypeCid}: The storage type that will be used for the samples in the box.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setLocation(BTXBoxLocation) location}: The box location.</li>
 * </ul>
 */
public class BtxDetailsCreateBox extends BTXDetails {
  static final long serialVersionUID = -5768245821594397345L;

  private String _boxId;
  private BoxLayoutDto _boxLayoutDto;
  private Vector _samples;
  private Vector _cellRefs;
  private BTXBoxLocation _location;
  private String _storageTypeCid;

  public BtxDetailsCreateBox() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTX_TYPE_CREATE_BOX;
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
  public Vector getCellRefs() {
    return _cellRefs;
  }

  /**
   * @return
   */
  public BTXBoxLocation getLocation() {
    return _location;
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
   * @param vector
   */
  public void setCellRefs(Vector vector) {
    _cellRefs = vector;
  }

  /**
   * @param location
   */
  public void setLocation(BTXBoxLocation location) {
    _location = location;
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
  public String getStorageTypeCid() {
    return _storageTypeCid;
  }

  /**
   * @param string
   */
  public void setStorageTypeCid(String string) {
    _storageTypeCid = string;
  }
}
