/*
 * Created on Mar 3, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.iltds.btx;

import java.util.Set;

import com.ardais.bigr.api.ApiException;

/**
 * Represents the details of a business transaction to confirm that a specified box
 * is on a specified manifest.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setManifestDto(ManifestDto) manifestDto}: The ManifestDto containing all 
 *      necessary input data.  The only field that must be filled in is the
 *      {@link ManifestDto#setManifestNumber(String)} field, which must contain a
 *      valid manifest number (which can be in its abbreviated for, e.g. MNFT3001).</li>
 * <li>{@link #setBoxId(String) box id}: The box id.  This must contain a
 *      valid box id (which can be in its abbreviated form, e.g. BX12).</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setManifestDto() manifestDto}: The manifest number is replaced by its full
 *     canonical form.</li>
 * <li>{@link #setBoxId(String) box id}: The box id is replaced by its full
 *     canonical form.</li>
 * <li>{@link #setConfirmed(boolean) confirmed}: Set to false if either the manifest number or box
 *     id is invalid or if the box is not on the manifest, otherwise set to true.</li>
 * </ul>
 */
public class BtxDetailsConfirmBoxOnManifest extends BtxDetailsShippingOperation {
  static final long serialVersionUID = 8874429980217904287L;
  
  private String _boxId = null;
  private boolean _confirmed = false;

  public BtxDetailsConfirmBoxOnManifest() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTX_TYPE_CONFIRM_BOX_ON_MANIFEST;
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
  public boolean isConfirmed() {
    return _confirmed;
  }

  /**
   * @param string
   */
  public void setBoxId(String string) {
    _boxId = string;
  }

  /**
   * @param b
   */
  public void setConfirmed(boolean b) {
    _confirmed = b;
  }

}
