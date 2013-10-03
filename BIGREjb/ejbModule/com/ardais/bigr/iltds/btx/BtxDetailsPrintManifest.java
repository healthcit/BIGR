package com.ardais.bigr.iltds.btx;

import java.io.Serializable;
import java.util.Set;

import com.ardais.bigr.api.ApiException;

/**
 * Represents the details of a print manifest business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setManifestDto(ManifestDto) manifestDto}: The ManifestDto containing a manifest 
 *      number.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #getManifestDto() manifestDto}: The ManifestDto containing all details of the 
 *      manifest necessary to print the manifest, including boxes in the manifest and their
 *      samples.</li>
 * </ul>
 */
public class BtxDetailsPrintManifest extends BtxDetailsShippingOperation implements Serializable {
  static final long serialVersionUID = -3581465322011352383L;
  
  /**
   * Creates a new BtxDetailsPrintManifest.
   */
  public BtxDetailsPrintManifest() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_PRINT_MANIFEST;
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
