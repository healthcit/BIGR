package com.ardais.bigr.iltds.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.assistants.Address;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.ManifestDto;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represents the details of a create manifest business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setManifestDto(ManifestDto) manifestDto}: The ManifestDto containing all 
 *      necessary input data.  This must contain one or more box barcode ids.</li>
 * <li>{@link #setForRequest(boolean) forRequest}: Set to true if the manifest is for shipping
 *      a transfer request.  This defaults to true.  Special validation rules apply to
 *      the boxes that can be placed on the manifest when this is true, and the manifestDto's
 *      shipToAddressId is ignored as an input parameter and is instead an output parameter
 *      (the shipping address is obtained from the request(s) that the boxes are on).</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>See the comments regarding the manifest's ship-to address above.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #getManifestDto() manifestDto}: See the output of {@link BtxDetailsPrintManifest}.
 *     Also, see the comments above regarding the manifest's ship-to address sometimes being
 *     an output parameter.</li>
 * </ul>
 */
public class BtxDetailsCreateManifest extends BtxDetailsShippingOperation implements Serializable {
  static final long serialVersionUID = 8257563421407904287L;
  
  private boolean _forRequest = true;

  /**
   * Creates a new BtxDetailsCreateManifest.
   */
  public BtxDetailsCreateManifest() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CREATE_MANIFEST;
  }

  /**
   * @return
   */
  public boolean isForRequest() {
    return _forRequest;
  }

  /**
   * @param b
   */
  public void setForRequest(boolean b) {
    _forRequest = b;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();

    ManifestDto manifest = getManifestDto();

    set.add(manifest.getManifestNumber());
    set.addAll(manifest.getBoxIdList());

    return set;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);

    ManifestDto manifest = getManifestDto();
    Address shipTo = manifest.getShipToAddress();

    history.setAttrib1(manifest.getManifestNumber());
    history.setAttrib2(shipTo.getAddressName());
    history.setAttrib3(shipTo.getLocationAddress1());
    history.setAttrib4(shipTo.getLocationAddress2());
    history.setAttrib5(shipTo.getLocationCity());
    history.setAttrib6(shipTo.getLocationState());
    history.setAttrib7(shipTo.getLocationZip());
    history.setAttrib8(shipTo.getCountry());
    history.setIdList1(new IdList(manifest.getBoxIdList()));
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);

    // This method must set ALL fields on this object, as if it had been newly
    // created immediately before this method was called.  See the javadoc for
    // BTXDetails.populateFromHistoryRecord.

    ManifestDto manifest = new ManifestDto();
    Address shipTo = new Address();

    manifest.setManifestNumber(history.getAttrib1());

    shipTo.setAddressName(history.getAttrib2());
    shipTo.setLocationAddress1(history.getAttrib3());
    shipTo.setLocationAddress2(history.getAttrib4());
    shipTo.setLocationCity(history.getAttrib5());
    shipTo.setLocationState(history.getAttrib6());
    shipTo.setLocationZip(history.getAttrib7());
    shipTo.setCountry(history.getAttrib8());

    manifest.setBoxIdList(history.getIdList1().getList());

    manifest.setShipToAddress(shipTo);
    setManifestDto(manifest);

    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    //
    // <None>
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    //   getManifestDto().getManifestNumber()
    //   getManifestDto().getShipToAddress() [only name,addr1,addr2,city,state,zip,country]
    //   getManifestDto().getBoxIds()

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(512);

    // The form of the result is:
    //    Created manifest <manifestNumber> for shipping to <shipToAddr.name>.
    //    Boxes: <boxIds>

    ManifestDto manifest = getManifestDto();
    Address shipTo = manifest.getShipToAddress();

    sb.append("Created manifest ");
    sb.append(IcpUtils.prepareLink(manifest.getManifestNumber(), securityInfo));
    if (!ApiFunctions.isEmpty(shipTo.getAddressName())) {
      sb.append(" for shipping to ");
      Escaper.htmlEscape(shipTo.getAddressName(), sb);
    }
    sb.append(".<br>Boxes: ");
    IdList boxIdList = new IdList(manifest.getBoxIdList());
    boxIdList.appendICPHTML(sb, securityInfo);

    return sb.toString();
  }

}
