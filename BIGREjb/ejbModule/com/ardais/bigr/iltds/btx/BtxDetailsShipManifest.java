/*
 * Created on Mar 3, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.iltds.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.ManifestDto;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * @author gyost
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BtxDetailsShipManifest extends BtxDetailsShippingOperation {
  static final long serialVersionUID = 8874429980221345287L;

  public BtxDetailsShipManifest() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTX_TYPE_SHIP_MANIFEST;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();

    ManifestDto manifest = getManifestDto();
    String manifestNumber = manifest.getManifestNumber();
    String trackingNumber = manifest.getTrackingNumber();

    if (!ApiFunctions.isEmpty(manifestNumber)) {
      set.add(manifestNumber);
    }

    if (!ApiFunctions.isEmpty(trackingNumber)) {
      set.add(trackingNumber);
    }

    set.addAll(manifest.getBoxIdList());

    return set;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);

    ManifestDto manifest = getManifestDto();

    history.setAttrib1(manifest.getManifestNumber());
    history.setAttrib2(manifest.getTrackingNumber());
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

    manifest.setManifestNumber(history.getAttrib1());
    manifest.setTrackingNumber(history.getAttrib2());
    manifest.setBoxIdList(history.getIdList1().getList());

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
    //   getManifestDto().getTrackingNumber()
    //   getManifestDto().getBoxIdList()

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(512);

    // The form of the result is:
    //    Packaged and shipped manifest <manifestNumber> (tracking number: <tracking number>).

    ManifestDto manifest = getManifestDto();

    sb.append("Packaged and shipped manifest ");
    sb.append(IcpUtils.prepareLink(manifest.getManifestNumber(), securityInfo));
    sb.append(" (tracking number: ");
    sb.append(IcpUtils.prepareLink(manifest.getTrackingNumber(), securityInfo));
    sb.append(").<br>Boxes: ");
    IdList boxIdList = new IdList(manifest.getBoxIdList());
    boxIdList.appendICPHTML(sb, securityInfo);

    return sb.toString();
  }

}
