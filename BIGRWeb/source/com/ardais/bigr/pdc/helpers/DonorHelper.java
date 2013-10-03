package com.ardais.bigr.pdc.helpers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.helpers.DateHelper;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.kc.form.BigrFormInstance;
import com.ardais.bigr.kc.form.BtxDetailsKcFormInstanceDomainObjectSummary;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.form.def.BigrFormDefinitionQueryCriteria;
import com.ardais.bigr.kc.form.def.BtxDetailsKcFormDefinitionLookup;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.javabeans.ConsentData;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.FormDefinitionTypes;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.FormContextStack;
import com.gulfstreambio.kc.web.support.KcUiContext;

/**
 */
public class DonorHelper extends JspHelper {

  // Donor properties.
  private String _ardaisId;
  private String _yyyyDob;
  private String _gender;
  private String _ethnicCategory;
  private String _race;
  private String _zipCode;
  private String _countryOfBirth;
  private String _donorAccount;
  private String _donorProfileNotes;
  private LegalValueSet _ageList;

  private SelectDonorHelper _context;

  private String _defaultString = null;

  // A flag to indicate whether the donor is present in the database
  private boolean _donorPresent;

  // A flag that indicates whether the donor is new.
  private String _new;

  // A flag indicating whether the donor is imported
  private String _importedYN;

  //the account of the user who imported the donor
  private String _ardaisAccountKey;

  //id this donor is known as to the customer importing it
  private String _customerId;
  
  // The JSON representation of the donor registration form instance.
  private String _form;

  // The donor data bean that holds data to be displayed
  // through this helper.
  private DonorData _dataBean;

  // Holds the consents for this donor.  Each entry is a ConsentHelper.
  private List _consents;

  //Holds the attachments if any. Each entry is an AttachmentDataHelper, EZhang
  private List _attachments;

  // Holds the Donor Diagnoses for this donor
  private List _diagnoses;

  private Map _donorLinkParams;

  private Map _editDonorProfileLinkParams;

  private Map _nextOpLinkParams;
  private int _consentsIndex;
  private BigrFormDefinition[] _formDefs;
  private BigrFormInstance[] _formInstances;

  private boolean _printDonorLabel;

  //label printing information (available templates, printers, etc)
  private Map _labelPrintingData;
  
  //number of times each label should be printed
  private String _labelCount;
  
  //selected template definition to use for printing the labels
  private String _templateDefinitionName;
  
  //selected printer to use for printing the labels
  private String _printerName;

  /**
   * Creates a <code>DonorHelper</code>, initializing its data
   * from the specified data bean.
   *
   * @param  dataBean  the <code>DonorData</code> bean holding the
   *		donor data
   */
  public DonorHelper(DonorData dataBean) {
    this(dataBean, null);
  }
  /**
   * Creates a <code>DonorHelper</code>, initializing its data
   * from the specified data bean.
   *
   * @param  dataBean  the <code>DonorData</code> bean holding the
   *		donor data
   */
  public DonorHelper(DonorData dataBean, String defaultString) {
    _dataBean = dataBean;
    _defaultString = defaultString;

    List consents = dataBean.getConsents();
    if (consents != null) {
      for (int i = 0; i < consents.size(); i++) {
        ConsentHelper helper = new ConsentHelper((ConsentData) consents.get(i), _defaultString);
        addConsent(helper);
      }
    }
  }
  /**
   * Creates a <code>DonorHelper</code> from an HTTP request,
   * initializing its properties from the request parameters.
   */
  public DonorHelper(HttpServletRequest request) {
    _ardaisId = JspHelper.safeTrim(request.getParameter("ardaisId"));
    _yyyyDob = JspHelper.safeTrim(request.getParameter("yyyyDob"));
    _gender = JspHelper.safeTrim(request.getParameter("gender"));
    _ethnicCategory = JspHelper.safeTrim(request.getParameter("ethnicCategory"));
    _race = JspHelper.safeTrim(request.getParameter("race"));
    _zipCode = JspHelper.safeTrim(request.getParameter("zipCode"));
    _countryOfBirth = JspHelper.safeTrim(request.getParameter("countryOfBirth"));
    _donorProfileNotes = JspHelper.safeTrim(request.getParameter("donorProfileNotes"));
    _new = JspHelper.safeTrim(request.getParameter("new"));
    _importedYN = JspHelper.safeTrim(request.getParameter("importedYN"));
    _ardaisAccountKey = JspHelper.safeTrim(request.getParameter("ardaisAccountKey"));
    _customerId = JspHelper.safeTrim(request.getParameter("customerId"));
    _form = JspHelper.safeTrim(request.getParameter("form"));
    _printDonorLabel = new Boolean(JspHelper.safeTrim(request.getParameter("printDonorLabel"))).booleanValue();
    _labelCount = JspHelper.safeTrim(request.getParameter("labelCount"));
    _templateDefinitionName = JspHelper.safeTrim(request.getParameter("templateDefinitionName"));
    _printerName = JspHelper.safeTrim(request.getParameter("printerName"));
  }
  /**
   * Adds a <code>ConsentHelper</code> to this <code>DonorHelper</code>.
   * The consent is added to the end of an ordered list.
   *
   * @param  helper  the <code>ConsentHelper</code>
   */
  public void addConsent(ConsentHelper helper) {
    if (_consents == null)
      _consents = new ArrayList();
    _consents.add(helper);
  }
 
  /**
   * Insert the method's description here.
   * Creation date: (7/12/2002 11:16:36 AM)
   * @return com.ardais.bigr.iltds.assistants.LegalValueSet
   */
  public LegalValueSet getAgeList() {

    // create the list of years that will be used to select YOB
    // upper limit has to be set to today's year + 1 to account for 
    // biospecimen from unborn
    Calendar gcalendar = new GregorianCalendar();
    gcalendar.setTime(new Date());
    
    if (_ageList == null) {
      _ageList = DateHelper.getYearList(1890, gcalendar.get(Calendar.YEAR) + 1);
    }
    return _ageList;
  }

  /**
   * Returns this donor's id.
   *
   * @return  The donor (Ardais) id.
   */
  public String getArdaisId() {
    if (_ardaisId != null)
      return _ardaisId;
    if (_dataBean != null)
      return _dataBean.getArdaisId();
    return _defaultString;
  }
  
//  public String getMessages()
//  {
//	  return super.getMessages();
//  }
  /**
   * Returns the list of consents (<code>ConsentHelper</code>s) that were
   * added to this <code>DonorHelper</code>.  If there are no consents,
   * an empty list is returned.
   *
   * @return  The list of <code>ConsentHelper</code>s.
   */
  public List getConsents() {
    return _consents;
  }
  
  /**
   * Returns the list of attachments (<code>AttachmentData</code>s).  If there are no attachments,
   * an empty list is returned.
   *
   * @return  The list of <code>AttachmentData</code>s.
   */
  public List getAttachments() {
    return _attachments;
  }
    
  /**
   * Insert the method's description here.
   * Creation date: (7/12/2002 6:03:44 PM)
   * @return java.lang.String
   */
  public String getCountryOfBirth() {
    String countryOfBirth = null;
    if (_countryOfBirth != null)
      countryOfBirth = _countryOfBirth;
    else if (_dataBean != null && _dataBean.getCountryOfBirth() != null)
      countryOfBirth = _dataBean.getCountryOfBirth();

    return countryOfBirth;
  }
  /**
   * Returns this donor's country_of_birth.
   *
   * @return  The donor country_of_birth.
   */
  public String getCountryOfBirthDisplay() {
    String countryOfBirth = getCountryOfBirth();

    return (countryOfBirth == null)
      ? _defaultString
      : GbossFactory.getInstance().getDescription(/*"COUNTRY",*/
    countryOfBirth);
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/12/2002 6:05:59 PM)
   * @return com.ardais.bigr.iltds.assistants.LegalValueSet
   */
  public LegalValueSet getCountryOfBirthList() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_COUNTRY);
  }
  /**
   * Returns the <code>DonorData</code> bean that contains
   * the donor data fields associated with this 
   * <code>DonorData</code>.
   * 
   * @return  The <code>DonorData</code> bean.
   */
  public DonorData getDataBean() {
    if (_dataBean == null) {
      _dataBean = new DonorData();
      _dataBean.setArdaisId(_ardaisId);
      _dataBean.setYyyyDob(_yyyyDob);
      _dataBean.setGender(_gender);
      _dataBean.setEthnicCategory(_ethnicCategory);
      _dataBean.setRace(_race);
      _dataBean.setZipCode(_zipCode);
      _dataBean.setCountryOfBirth(_countryOfBirth);
      _dataBean.setDonorProfileNotes(_donorProfileNotes);
      _dataBean.setImportedYN(_importedYN);
      _dataBean.setArdaisAccountKey(_ardaisAccountKey);
      _dataBean.setCustomerId(_customerId);
    }
    return _dataBean;
  }
  /**
   * Returns the list of diagnoses (<code>DonorDiagnosisHelper</code>s) that were
   * added to this <code>DonorHelper</code>.  If there are no diagnoses,
   * an empty list is returned.
   *
   * @return  The list of <code>DonorDiagnosisHelper</code>s.
   */
  public List getDiagnoses() {
    return _diagnoses;
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/12/2002 3:16:57 PM)
   * @return java.lang.String
   */
  public String getDonorAccount() {
    if (_donorAccount == null) {
      try {
        String ardaisId = getArdaisId();
        DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
        DDCDonor donor = ddcDonorHome.create();
        _donorAccount = donor.getDonorAccount(ardaisId);
      }
      catch (Exception e) {
        throw new ApiException(e);
      }
    }
    return _donorAccount;
  }

  /**
   * Insert the method's description here.
   * Creation date: (7/30/2002 5:03:05 PM)
   * @return boolean
   */
  public boolean getDonorPresent() {
    return _donorPresent;
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/23/2002 4:40:55 PM)
   * @return java.lang.String
   */
  public String getDonorProfileNotes() {
    if (_donorProfileNotes != null)
      return _donorProfileNotes;
    if (_dataBean != null)
      return _dataBean.getDonorProfileNotes();
    return _defaultString;
  }

  /**
   * Insert the method's description here.
   * Creation date: (7/12/2002 5:59:33 PM)
   * @return java.lang.String
   */
  public String getEthnicCategory() {
    String ethnicCategory = null;
    if (_ethnicCategory != null)
      ethnicCategory = _ethnicCategory;
    else if (_dataBean != null && _dataBean.getEthnicCategory() != null)
      ethnicCategory = _dataBean.getEthnicCategory();

    return ethnicCategory;
  }
  /**
   * Returns this donor's ethnic_category.
   *
   * @return  The donor ethnic_category.
   */
  public String getEthnicCategoryDisplay() {

    String ethnicCategory = getEthnicCategory();

    return (ethnicCategory == null)
      ? _defaultString
      : GbossFactory.getInstance().getDescription(/*"ETHNICITY",*/
    ethnicCategory);

  }
  /**
   * Insert the method's description here.
   * Creation date: (7/12/2002 5:56:28 PM)
   * @return com.ardais.bigr.iltds.assistants.LegalValueSet
   */
  public LegalValueSet getEthnicCategoryList() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_ETHNICITY);
  }
  /**
       * Returns this donor's gender.
       *
       * @return  The donor gender.
       */
  public String getGender() {
    String gender = null;

    if (_gender != null)
      gender = _gender;
    else if (_dataBean != null && _dataBean.getGender() != null)
      gender = _dataBean.getGender();

    return gender;

  }
  /**
       * Returns this donor's gender.
       *
       * @return  The donor gender.
       */
  public String getGenderDisplay() {
    String gender = getGender();

    if (gender != null) {
      if (gender.equalsIgnoreCase("F")) {
        gender = "Female";
      }
      else if (gender.equalsIgnoreCase("M")) {
        gender = "Male";
      }
      else if (gender.equalsIgnoreCase("U")) {
        gender = "Unknown";
      }
    }
    return (gender == null) ? _defaultString : gender;

  }
  /**
   * Insert the method's description here.
   * Creation date: (7/12/2002 5:31:53 PM)
   * @return java.lang.String
   */
  public String getRace() {
    String race = null;
    if (_race != null)
      race = _race;
    else if (_dataBean != null && _dataBean.getRace() != null)
      race = _dataBean.getRace();

    return race;
  }
  /**
   * Returns this donor's race.
   *
   * @return  The donor race.
   */
  public String getRaceDisplay() {
    String race = getRace();

    return (race == null) ? _defaultString : GbossFactory.getInstance().getDescription(/*"RACE",*/
    race);
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/12/2002 3:27:30 PM)
   * @return com.ardais.bigr.iltds.assistants.LegalValueSet
   */
  public LegalValueSet getRaceList() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_RACE);
  }
 
  /**
   * Returns this donor's year of birth.
   *
   * @return  The donor year og birth.
   */
  public String getYyyyDob() {
    if (_yyyyDob != null)
      return _yyyyDob;
    else if (_dataBean != null && _dataBean.getYyyyDob() != null)
      return _dataBean.getYyyyDob();
    else
      return _defaultString;
  }
  /**
   * Returns this donor's zip_code.
   *
   * @return  The donor zip_code.
   */
  public String getZipCode() {
    if (_zipCode != null)
      return _zipCode;
    else if (_dataBean != null && _dataBean.getZipCode() != null)
      return _dataBean.getZipCode();
    else
      return _defaultString;
  }
  /**
   */
  public boolean isInformed() {
    String ardaisId = getArdaisId();
    if (ApiFunctions.isEmpty(ardaisId))
      return false;

    return (ardaisId.substring(0, 2).equals(ValidateIds.PREFIX_DONOR_LINKED)) ? true : false;
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/15/2002 4:13:52 PM)
   * @return boolean
   */
  public boolean isNew() {
    if ("true".equals(_new))
      return true;
    if ("false".equals(_new))
      return false;
    if ((_dataBean != null) && !isEmpty(_dataBean.getArdaisId()))
      return false;
    return true;
  }

  /**
   * Returns <code>true</code> if the data associated 
   * with this helper is valid; otherwise returns <code>false</code>.
   *
   * @return  <code>true</code> if all parameters are valid;
   *			<code>false</code> otherwise.
   */
  public boolean isValid(DataFormDefinition regForm) {
    boolean isValid = true;

    // Validate required based on the registration form
    Set allHostAttributes = ArtsConstants.getDonorAttributes();
    DataFormDefinitionHostElement[] hostElements = regForm.getDataHostElements();
    for (int i = 0; i < hostElements.length; i++) {
      DataFormDefinitionHostElement hostElement = hostElements[i];
      String hostId = hostElement.getHostId();
      
      allHostAttributes.remove(hostId);
      
      if (hostElement.isRequired()) {
        if (hostId.equals(ArtsConstants.ATTRIBUTE_DONOR_ETHNICITY)) {
          String ethnicity = getEthnicCategory();
          if ((ethnicity == null) || ApiFunctions.isEmpty(ApiFunctions.safeTrim(ethnicity))) {
            getMessageHelper().addMessage("Value for Ethnicity is missing.");              
            isValid = false;
          }
        }
        else if (hostId.equals(ArtsConstants.ATTRIBUTE_DONOR_GENDER)) {
          String gender = getGender();
          if ((gender == null) || ApiFunctions.isEmpty(ApiFunctions.safeTrim(gender))) {
            getMessageHelper().addMessage("Value for Gender is missing.");              
            isValid = false;
          }
        }
        else if (hostId.equals(ArtsConstants.ATTRIBUTE_DONOR_NOTES)) {
          String notes = getDonorProfileNotes();
          if ((notes == null) || ApiFunctions.isEmpty(ApiFunctions.safeTrim(notes))) {
            getMessageHelper().addMessage("Value for Donor Notes is missing.");              
            isValid = false;
          }
        }
        else if (hostId.equals(ArtsConstants.ATTRIBUTE_DONOR_RACE)) {
          String race = getRace();
          if ((race == null) || ApiFunctions.isEmpty(ApiFunctions.safeTrim(race))) {
            getMessageHelper().addMessage("Value for Race is missing.");              
            isValid = false;
          }
        }
        else if (hostId.equals(ArtsConstants.ATTRIBUTE_DONOR_YOB)) {
          String yob = getYyyyDob();
          if ((yob == null) || ApiFunctions.isEmpty(ApiFunctions.safeTrim(yob))) {
            getMessageHelper().addMessage("Value for Year of Birth is missing.");              
            isValid = false;
          }
        }
        else if (hostId.equals(ArtsConstants.ATTRIBUTE_DONOR_ZIP_CODE)) {
          String zip = getZipCode();
          if ((zip == null) || ApiFunctions.isEmpty(ApiFunctions.safeTrim(zip))) {
            getMessageHelper().addMessage("Value for Zip Code is missing.");              
            isValid = false;
          }
        }
      }        
    }
    
    // Iterate over all of the host elements that were not in the registration form and
    // check that their values are empty.
    Iterator i = allHostAttributes.iterator();
    while (i.hasNext()) {
      String hostId = (String) i.next();
      if (hostId.equals(ArtsConstants.ATTRIBUTE_DONOR_ETHNICITY)) {
        String ethnicity = getEthnicCategory();
        if ((ethnicity != null) && !ApiFunctions.isEmpty(ApiFunctions.safeTrim(ethnicity))) {
          getMessageHelper().addMessage("Value for Ethnicity cannot be specified since it is not an element in the registration form.");              
          isValid = false;
        }
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_DONOR_GENDER)) {
        String gender = getGender();
        if ((gender != null) && !ApiFunctions.isEmpty(ApiFunctions.safeTrim(gender))) {
          getMessageHelper().addMessage("Value for Gender cannot be specified since it is not an element in the registration form.");              
          isValid = false;
        }
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_DONOR_NOTES)) {
        String notes = getDonorProfileNotes();
        if ((notes != null) && !ApiFunctions.isEmpty(ApiFunctions.safeTrim(notes))) {
          getMessageHelper().addMessage("Value for Donor Notes cannot be specified since it is not an element in the registration form.");              
          isValid = false;
        }
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_DONOR_RACE)) {
        String race = getRace();
        if ((race != null) && !ApiFunctions.isEmpty(ApiFunctions.safeTrim(race))) {
          getMessageHelper().addMessage("Value for Race cannot be specified since it is not an element in the registration form.");              
          isValid = false;
        }
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_DONOR_YOB)) {
        String yob = getYyyyDob();
        if ((yob != null) && !ApiFunctions.isEmpty(ApiFunctions.safeTrim(yob))) {
          getMessageHelper().addMessage("Value for Year of Birth cannot be specified since it is not an element in the registration form.");              
          isValid = false;
        }
      }
      else if (hostId.equals(ArtsConstants.ATTRIBUTE_DONOR_ZIP_CODE)) {
        String zip = getZipCode();
        if ((zip != null) && !ApiFunctions.isEmpty(ApiFunctions.safeTrim(zip))) {
          getMessageHelper().addMessage("Value for Zip Code cannot be specified since it is not an element in the registration form.");              
          isValid = false;
        }
      }
    }

    // Make sure that year of birth does not exceed current time
    if (!ApiFunctions.isEmpty(getYyyyDob())) {
      Date currentDate = new Date();
      // note that DateHelper is 0-based for month...
      DateHelper yearOfBirth = new DateHelper("01", "00", getYyyyDob());
      if (currentDate.before(yearOfBirth.getDate())) {
        getMessageHelper().addMessage("Year of Birth cannot be later than current year.");
        isValid = false;
      }
    }
    
    //Make sure that alias is specified for imported donors
    if ("Y".equalsIgnoreCase(_importedYN)) {
      //make sure customer id has been specified
      if (ApiFunctions.isEmpty(_customerId)) {
        getMessageHelper().addMessage("Donor Alias must be specified");
        isValid = false;
      }
      //make sure customer id is unique within the account
      else {
        if (!PdcUtils
          .isCustomerIdUniqueInAccount(isNew(), _ardaisId, _customerId, _ardaisAccountKey)) {
          getMessageHelper().addMessage("A donor with the specified alias already exists.");
          isValid = false;
        }
      }
    }
    
    if (!isValid) {
      getMessageHelper().setError(true);
    }
    
    return isValid;
  }
  /**
  * 
  * 
  * @param defaultString String
  */
  public void setDefaultString(String defaultString) {
    _defaultString = defaultString;
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/30/2002 5:01:53 PM)
   * @param donorPresent boolean
   */
  public void setDonorPresent(boolean donorPresent) {
    _donorPresent = donorPresent;
  }
  /**
   */
  public void setNew(boolean isNew) {
    _new = isNew ? "true" : "false";
  }

  private SelectDonorHelper getContext() {
    return _context;
  }

  public void setContext(SelectDonorHelper context) {
    _context = context;
  }

  public void setAttachments(List attachments) {
    _attachments = attachments;
  }
  
  /**
   * Returns a <code>Map</code> of URL parameters for the donor
   * link, in support of the Struts html:link tag.
   * 
   * @return  The URL parameters.
   */
  public Map getDonorLinkParams() {
    if (_donorLinkParams == null) {
      _donorLinkParams = new HashMap();
      _donorLinkParams.put("op", "DonorProfileSummaryPrepare");
      _donorLinkParams.put("ardaisId", getArdaisId());
    }
    return _donorLinkParams;
  }
  /**
   * Returns a <code>Map</code> of URL parameters for the edit donor
   * profile link, in support of the Struts html:link tag.
   * 
   * @return  The URL parameters.
   */
  public Map getDonorProfileLinkParams() {
    if (_editDonorProfileLinkParams == null) {
      _editDonorProfileLinkParams = new HashMap();
      _editDonorProfileLinkParams.put("op", "DonorProfilePrepare");
      _editDonorProfileLinkParams.put("ardaisId", getArdaisId());
    }
    return _editDonorProfileLinkParams;
  }

  /**
   */
  public String getStartConsents() {
    _consentsIndex = 0;
    _nextOpLinkParams = new HashMap();
    _nextOpLinkParams.put("op", getContext().getPathOp());
    _nextOpLinkParams.put("ardaisId", getArdaisId());
    //add param to indicate if the donor is imported or not
    _nextOpLinkParams.put("donorImportedYN", getImportedYN());
    return null;
  }
  /**
   */
  public String getNextConsent() {
    _consentsIndex++;
    return null;
  }
  /**
   */
  private ConsentHelper getCurrentConsent() {
    return (ConsentHelper) _consents.get(_consentsIndex);
  }
  public Map getCurrentNextOpLinkParams() {
    ConsentHelper consent = getCurrentConsent();
    _nextOpLinkParams.put("pathReportId", consent.getPathReportId());
    _nextOpLinkParams.put("consentId", consent.getConsentId());
    return _nextOpLinkParams;
  }
  
  /**
   * Return a string listing the case ids for this donor, with each case id being an
   * ICP link if permitted for the specified user.
   * 
   * @param securityInfo the security information for the current user
   * @return the case ids display string.
   */
  public String getConsentIdListDisplayHtml(SecurityInfo securityInfo) {
    StringBuffer sb = new StringBuffer(64);
    
    List consents = getConsents();
    if (consents != null) {
      Iterator iter = consents.iterator();
      boolean needComma = false;
      while (iter.hasNext()) {
        ConsentHelper consent = (ConsentHelper) iter.next();
        if (needComma) {
          sb.append(", ");
        }
        else {
          needComma = true;
        }
        sb.append(IcpUtils.prepareLink(consent.getConsentId(), securityInfo));
        if (!ApiFunctions.isEmpty(consent.getCustomerId())) {
          sb.append(" (");
          Escaper.htmlEscapeAndPreserveWhitespace(consent.getCustomerId(),sb);
          sb.append(")");
        }
      }
    }
    
    return sb.toString();
  }
 
  /**
   * @return
   */
  public String getCustomerId() {
    String customerId = null;
    if (_customerId != null) {
      customerId = _customerId;
    }
    else if (_dataBean != null) {
      customerId = _dataBean.getCustomerId();
    }
    return customerId;
  }

  /**
   * @return
   */
  public String getArdaisAccountKey() {
    String ardaisAccountKey = null;
    if (_ardaisAccountKey != null) {
      ardaisAccountKey = _ardaisAccountKey;
    }
    else if (_dataBean != null) {
      ardaisAccountKey = _dataBean.getArdaisAccountKey();
    }
    return ardaisAccountKey;
  }

  /**
   * @return
   */
  public String getImportedYN() {
    String importedYN = null;
    if (_importedYN != null) {
      importedYN = _importedYN;
    }
    else if (_dataBean != null) {
      importedYN = _dataBean.getImportedYN();
    }
    return importedYN;
  }

  /**
   * @param string
   */
  public void setArdaisAccountKey(String string) {
    _ardaisAccountKey = string;
  }

  /**
   * @param string
   */
  public void setImportedYN(String string) {
    _importedYN = string;
  }

  public String getAgeAtCollection() {
    String ageAtCollection = "";
    String minAge = IltdsUtils.getDonorMinAgeAtCollection(getArdaisId());;
    String maxAge = IltdsUtils.getDonorMaxAgeAtCollection(getArdaisId());
    String dashString = "";
  
    if (!ApiFunctions.isEmpty(minAge) && (!ApiFunctions.isEmpty(maxAge))) {
      dashString = "-";
    }
  
    if (minAge.equals(maxAge)) {
      ageAtCollection = minAge;
    }
    else {
      ageAtCollection = minAge + dashString + maxAge;
    }
  
    return ageAtCollection;  
  }

  public String getFormDefinitionId() {
    return "";
  }
  
  public LegalValueSet getFormDefinitionsAsLegalValueSet() {
    LegalValueSet lvs = new LegalValueSet();
    BigrFormDefinition[] formDefs = getFormDefinitions();
    if (formDefs != null) {
      for (int i = 0; i < formDefs.length; i++) {
        BigrFormDefinition formDef = formDefs[i];
        lvs.addLegalValue(formDef.getFormDefinitionId(), formDef.getName());
      }
    }
    return lvs;
  }

  public BigrFormDefinition[] getFormDefinitions() {
    return _formDefs;
  }

  public void findFormDefinitions(SecurityInfo securityInfo) {
    BtxDetailsKcFormDefinitionLookup btxDetailsLookup = new BtxDetailsKcFormDefinitionLookup();
    btxDetailsLookup.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btxDetailsLookup.setLoggedInUserSecurityInfo(securityInfo);
    BigrFormDefinitionQueryCriteria criteria = new BigrFormDefinitionQueryCriteria();
    criteria.setEnabled(true);
    criteria.setAccount(securityInfo.getAccount());
    criteria.addFormType(FormDefinitionTypes.DATA);    
    criteria.addObjectType(TagTypes.DOMAIN_OBJECT_VALUE_DONOR);    
    criteria.setUse(TagTypes.USES_VALUE_ANNOTATION);
    btxDetailsLookup.setQueryCriteria(criteria);
    btxDetailsLookup = 
      (BtxDetailsKcFormDefinitionLookup) Btx.perform(btxDetailsLookup, "kc_form_defs_lookup");
    if (btxDetailsLookup.isTransactionCompleted()) {
      setFormDefinitions(btxDetailsLookup.getFormDefinitions());
    }
    else {
      //TODO: deal with errors 
    }    
  }

  private void setFormDefinitions(BigrFormDefinition[] formDefs) {
    _formDefs = formDefs;
  }

  public BigrFormInstance[] getFormInstances() {
    return _formInstances;
  }

  public void findFormInstances(SecurityInfo securityInfo, String donorId) {
    BigrFormInstance form = new BigrFormInstance();
    form.setDomainObjectId(donorId);
    BtxDetailsKcFormInstanceDomainObjectSummary btxDetailsDos= new BtxDetailsKcFormInstanceDomainObjectSummary();
    btxDetailsDos.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btxDetailsDos.setLoggedInUserSecurityInfo(securityInfo);
    btxDetailsDos.setFormInstance(form);
    btxDetailsDos= 
      (BtxDetailsKcFormInstanceDomainObjectSummary) Btx.perform(btxDetailsDos, "kc_form_inst_domain_object_summary");
    if (btxDetailsDos.isTransactionCompleted()) {
      setFormInstances(btxDetailsDos.getFormInstances());
    }
    else {
      //TODO: deal with errors 
    }
  }
  
  private void setFormInstances(BigrFormInstance[] formInstances) {
    _formInstances = formInstances;
  }
  
  private String findRegistrationFormId(SecurityInfo securityInfo) {
    // If this is a new donor, get it from the account, otherwise it will be on the data bean.
    String regFormId = null;
    if (isNew()) {
      regFormId = IltdsUtils.getAccountDonorRegistrationFormId(securityInfo.getAccount());
    }
    else {
      regFormId = getDataBean().getRegistrationFormId();
      if (regFormId == null) {
        DonorData donor = new DonorData();
        donor.setArdaisId(getArdaisId());
        try {
          DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
          DDCDonor donorOperation = ddcDonorHome.create();
          DonorData donorData = donorOperation.getDonorProfile(donor);
          regFormId = donorData.getRegistrationFormId();
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }        
      }
    }
    return regFormId;
  }
  
  public DataFormDefinition findRegistrationForm(SecurityInfo securityInfo) {
    DataFormDefinition form = null;

    // First get the registration form definition id.
    String regFormId = findRegistrationFormId(securityInfo);
    
    // If we could determine the registration form id, then get the form and make sure that
    // it is enabled if we're creating a new donor.
    if (!ApiFunctions.isEmpty(regFormId)) {
      FormDefinitionServiceResponse r = 
        FormDefinitionService.SINGLETON.findFormDefinitionById(regFormId);
      form = r.getDataFormDefinition();
      if ((form == null) || (isNew() && !form.isEnabled())) {
        form = null;
      }
    }

    return form;
  }

  public void setupKcFormContext(HttpServletRequest request, DataFormDefinition formDef) {
    setupKcFormContext(request, formDef, null);
  }

  public void setupKcFormContext(HttpServletRequest request, DataFormDefinition formDef, FormInstance form) {
    FormContextStack stack = FormContextStack.getFormContextStack(request);
    FormContext formContext = stack.peek();
    formContext.setDataFormDefinition(formDef);
    formContext.setFormInstance(form);
    stack.push(formContext);
   
    KcUiContext kcUiContext = KcUiContext.getKcUiContext(request);
    String contextPath = request.getContextPath();
    kcUiContext.setAdePopupUrl(contextPath + "/kc/ade/popup.do");
  }
  
  public String getForm() {
    return _form;
  }
  
  /**
   * @return Returns the labelCount.
   */
  public String getLabelCount() {
    return _labelCount;
  }
  
  /**
   * @return Returns the labelPrintingData.
   */
  public Map getLabelPrintingData() {
    return _labelPrintingData;
  }
  
  /**
   * @return Returns the printDonorLabel.
   */
  public boolean isPrintDonorLabel() {
    return _printDonorLabel;
  }
  
  /**
   * @return Returns the printerName.
   */
  public String getPrinterName() {
    return _printerName;
  }
  
  /**
   * @return Returns the templateDefinitionName.
   */
  public String getTemplateDefinitionName() {
    return _templateDefinitionName;
  }
  
  /**
   * @param labelCount The labelCount to set.
   */
  public void setLabelCount(String labelCount) {
    _labelCount = labelCount;
  }
  
  /**
   * @param labelPrintingData The labelPrintingData to set.
   */
  public void setLabelPrintingData(Map labelPrintingData) {
    _labelPrintingData = labelPrintingData;
  }
  
  /**
   * @param printDonorLabel The printDonorLabel to set.
   */
  public void setPrintDonorLabel(boolean printDonorLabel) {
    _printDonorLabel = printDonorLabel;
  }
  
  /**
   * @param printerName The printerName to set.
   */
  public void setPrinterName(String printerName) {
    _printerName = printerName;
  }
  
  /**
   * @param templateDefinitionName The templateDefinitionName to set.
   */
  public void setTemplateDefinitionName(String templateDefinitionName) {
    _templateDefinitionName = templateDefinitionName;
  }
}

