package com.ardais.bigr.iltds.btx;

import java.util.Set;

import com.ardais.bigr.api.ApiException;

/**
 * Represents the details of a business transaction to get details of a specified manifest.
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
 *      valid manifest number (which can be in its abbreviated form, e.g. MNFT3001).</li>
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
 *     canonical form, and the basic manifest detail properties are set along with the
 *     list of boxes on the manifest (the {@link ManifestDto#getBoxes()} property).</li>
 * </ul>
 */
public class BtxDetailsGetManifest extends BtxDetailsShippingOperation {
  static final long serialVersionUID = 8874423421407904287L;

  public BtxDetailsGetManifest() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTX_TYPE_GET_MANIFEST;
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
}
