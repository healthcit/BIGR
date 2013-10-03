package com.ardais.bigr.ddc.donor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.kc.form.FormInstanceHistoryObject;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.FormDefinition;

/**
 * Represent the details of a DDC create donor business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link com.ardais.bigr.iltds.btx.BTXDetails BTXDetails} for fields that 
 * are shared by all business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setDonorData(donorData) donorData}: A
 * DonorData data bean holding all information for the donor.
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BTXDetailsCreateDonor extends BTXDetails implements Serializable {

  private DonorData _donorData;
  private BtxHistoryAttributes _historyObject;

  /**
   * Constructor for BTXDetailsCreateDonor.
   */
  public BTXDetailsCreateDonor() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CREATE_DONOR;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {

    Set set = new HashSet();
    
    set.add(_donorData.getArdaisId());

    return set;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();
    StringBuffer sb = new StringBuffer(256);

    // The result has this form:
    // Donor <ardais id> was created [with the following attributes: Attr1: value, Attr2: value...]

    sb.append("Donor ");
    sb.append(IcpUtils.prepareLink(_donorData.getArdaisId(), securityInfo));
    if (!ApiFunctions.isEmpty(_donorData.getCustomerId())) {
      sb.append(" (");
      Escaper.htmlEscape(_donorData.getCustomerId(), sb);
      sb.append(")");
    }
    sb.append(" was created");
    StringBuffer more = new StringBuffer(200);
    if (!ApiFunctions.isEmpty(_donorData.getYyyyDob())) {
      more.append("<li>");
      more.append("Year of birth: ");
      Escaper.htmlEscape(_donorData.getYyyyDob(), more);
      more.append("</li>");
    }
    if (!ApiFunctions.isEmpty(_donorData.getGender())) {
      more.append("<li>");
      more.append("Gender: ");
      Escaper.htmlEscape(_donorData.getGender(), more);
      more.append("</li>");
    }
    if (!ApiFunctions.isEmpty(_donorData.getZipCode())) {
      more.append("<li>");
      more.append("Zip Code: ");
      Escaper.htmlEscape(_donorData.getZipCode(), more);
      more.append("</li>");
    }
    if (!ApiFunctions.isEmpty(_donorData.getEthnicCategory())) {
      more.append("<li>");
      more.append("Ethnicity: ");
      Escaper.htmlEscape(_donorData.getEthnicCategory(), more);
      more.append("</li>");
    }
    if (!ApiFunctions.isEmpty(_donorData.getRace())) {
      more.append("<li>");
      more.append("Race: ");
      Escaper.htmlEscape(_donorData.getRace(), more);
      more.append("</li>");
    }
    if (!ApiFunctions.isEmpty(_donorData.getCountryOfBirth())) {
      more.append("<li>");
      more.append("Country of Birth: ");
      Escaper.htmlEscape(_donorData.getCountryOfBirth(), more);
      more.append("</li>");
    }

    if (_historyObject != null) {
      FormInstanceHistoryObject historyObject = new FormInstanceHistoryObject();
      historyObject.doGetDetailsAsHTMLDataElementsOnly(_historyObject, more);      
    }
    
    if (more.length() > 0) {
      sb.append(" with the following attributes: ");
      sb.append("<ul>");
      sb.append(more.toString());
      sb.append("</ul>");
    }
    else {
      sb.append(".");
    }
    return sb.toString();
  }

  /**
   * @see com.ardais.bigr.ddc.donor.HasDonorData#getDonorData()
   */
  public DonorData getDonorData() {
    return _donorData;
  }

  /**
   * @see com.ardais.bigr.ddc.donor.HasDonorData#setDonorData(DonorData)
   */
  public void setDonorData(DonorData donorData) {
    _donorData = donorData;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);

    DonorData donorData = getDonorData();
    history.setAttrib1(donorData.getArdaisId());
    if (!ApiFunctions.isEmpty(_donorData.getCustomerId())) {
      history.setAttrib2(_donorData.getCustomerId());
    }
    if (!ApiFunctions.isEmpty(_donorData.getYyyyDob())) {
      history.setAttrib3(_donorData.getYyyyDob());
    }
    if (!ApiFunctions.isEmpty(_donorData.getGender())) {
      history.setAttrib4(Constants.getGenderText(_donorData.getGender()));
    }
    if (!ApiFunctions.isEmpty(_donorData.getZipCode())) {
      history.setAttrib5(_donorData.getZipCode());
    }
    if (!ApiFunctions.isEmpty(_donorData.getEthnicCategory())) {
      history.setAttrib6(GbossFactory.getInstance().getDescription(_donorData.getEthnicCategory()));
    }
    if (!ApiFunctions.isEmpty(_donorData.getRace())) {
      history.setAttrib7(GbossFactory.getInstance().getDescription(_donorData.getRace()));
    }
    if (!ApiFunctions.isEmpty(_donorData.getCountryOfBirth())) {
      history.setAttrib8(GbossFactory.getInstance().getDescription(_donorData.getCountryOfBirth()));
    }
    history.setClob1(_donorData.getDonorProfileNotes());

    FormInstance form = donorData.getFormInstance();
    FormDefinition formDef = donorData.getRegistrationForm();
    if ((form != null) && (formDef != null)) {
      FormInstanceHistoryObject historyObject = new FormInstanceHistoryObject();
      historyObject.setFormInstance(form);
      historyObject.setFormDefinition(formDef);
      history.setHistoryObject(historyObject.describeAsHistoryObject());      
    }
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);

    DonorData donorData = new DonorData();
    donorData.setArdaisId(history.getAttrib1());
    donorData.setCustomerId(history.getAttrib2());
    donorData.setYyyyDob(history.getAttrib3());
    donorData.setGender(history.getAttrib4());
    donorData.setZipCode(history.getAttrib5());
    donorData.setEthnicCategory(history.getAttrib6());
    donorData.setRace(history.getAttrib7());
    donorData.setCountryOfBirth(history.getAttrib8());
    donorData.setDonorProfileNotes(history.getClob1());
    setDonorData(donorData);

    _historyObject = (BtxHistoryAttributes)history.getHistoryObject();
  }

}
