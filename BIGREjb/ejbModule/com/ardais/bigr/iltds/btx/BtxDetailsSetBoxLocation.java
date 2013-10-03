package com.ardais.bigr.iltds.btx;

import java.util.Set;

import com.ardais.bigr.api.ApiException;

/**
 * Represents the details of a business transaction to set box location.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setBoxId(String) boxId}: The box id to check out.</li>
 * <li>{@link #setBoxId(String) format}: The box format (frozen or paraffin).</li>
 * <li>{@link #setBoxId(String) geoLoc}: The box geographical location.</li>
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
public class BtxDetailsSetBoxLocation extends BTXDetails {
  static final long serialVersionUID = 5473938997301577366L;

  private String _boxId;
  private String _storageTypeCid;
  private String _geoLoc;
  private BTXBoxLocation _location;

  public BtxDetailsSetBoxLocation() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTX_TYPE_SET_BOX_LOCATION;
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
  public String getStorageTypeCid() {
    return _storageTypeCid;
  }

  /**
   * @return
   */
  public String getGeoLoc() {
    return _geoLoc;
  }

  /**
   * @return
   */
  public BTXBoxLocation getLocation() {
    return _location;
  }

  /**
   * @param string
   */
  public void setBoxId(String string) {
    _boxId = string;
  }

  /**
   * @param string
   */
  public void setStorageTypeCid(String string) {
    _storageTypeCid = string;
  }

  /**
   * @param string
   */
  public void setGeoLoc(String string) {
    _geoLoc = string;
  }

  /**
   * @param location
   */
  public void setLocation(BTXBoxLocation location) {
    _location = location;
  }
}
