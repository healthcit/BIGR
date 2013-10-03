package com.ardais.bigr.iltds.databeans;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.BigrXMLParserBase;
import com.ardais.bigr.util.BigrXmlUtils;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;
import com.gulfstreambio.gboss.GbossFactory;

public class PolicyData extends BigrXMLParserBase implements Serializable {
  static final long serialVersionUID = -1710661269833625543L;

  private String _accountId = null;
  private String _allowForUnlinkedCases = null;
  private String _policyId = null;
  private String _policyName = null;
  private String _allocationNumerator = "0";
  private String _allocationDenominator = "0";
  private String _allocationFormatCid = ArtsConstants.ALLOCATION_FORMAT_NONE;
  private String _defaultLogicalReposId = null;
  private String _restrictedLogicalReposId = null;
  
  // MR 8021
  private String _verifyRequired = "N";
  private String _releaseRequired = "N";
  // MR 8006
  private String _additionalQuestionsGrp = null;

  private String _policyData;
  private String _policyDataEncoding;
  
  private SampleTypeConfiguration _sampleTypeConfiguration = null;

  private String _caseRegistrationFormId = null;
  private String _caseRegistrationFormName = null;
  
  private String _associatedIrbs = null;
  
  public PolicyData() {
    super();
  }
  
  /**
   * Populates this <code>PolicyData</code> from the data in the current row of 
   * the result set.
   * 
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.
   * @param  rs  the <code>ResultSet</code>
   */
  public void populate(Map columns, ResultSet rs) {
    try {
      if (columns.containsKey(DbConstants.POLICY_ID )) {
        setPolicyId(rs.getString(DbConstants.POLICY_ID));
      }
      if (columns.containsKey(DbAliases.POLICY_ID)) {
        setPolicyId(rs.getString(DbAliases.POLICY_ID));
      }
      if (columns.containsKey(DbConstants.POLICY_NAME)) {
        setPolicyName(rs.getString(DbConstants.POLICY_NAME));
      }
      if (columns.containsKey(DbAliases.POLICY_NAME)) {
        setPolicyName(rs.getString(DbAliases.POLICY_NAME));
      }
      if (columns.containsKey(DbConstants.POLICY_ALLOCATION_DENOMINATOR)) {
        setAllocationDenominator(new Integer(rs.getInt(DbConstants.POLICY_ALLOCATION_DENOMINATOR)).toString());
      }
      if (columns.containsKey(DbAliases.POLICY_ALLOCATION_DENOMINATOR)) {
        setAllocationDenominator(new Integer(rs.getInt(DbAliases.POLICY_ALLOCATION_DENOMINATOR)).toString());
      }
      if (columns.containsKey(DbConstants.POLICY_ALLOCATION_NUMERATOR)) {
        setAllocationNumerator(new Integer(rs.getInt(DbConstants.POLICY_ALLOCATION_NUMERATOR)).toString());
      }
      if (columns.containsKey(DbAliases.POLICY_ALLOCATION_NUMERATOR)) {
        setAllocationNumerator(new Integer(rs.getInt(DbAliases.POLICY_ALLOCATION_NUMERATOR)).toString());
      }
      if (columns.containsKey(DbConstants.POLICY_ALLOCATION_FORMAT_CID)) {
        setAllocationFormatCid(rs.getString(DbConstants.POLICY_ALLOCATION_FORMAT_CID));
      }
      if (columns.containsKey(DbAliases.POLICY_ALLOCATION_FORMAT_CID)) {
        setAllocationFormatCid(rs.getString(DbAliases.POLICY_ALLOCATION_FORMAT_CID));
      }
      if (columns.containsKey(DbConstants.POLICY_DEFAULT_LOGICAL_REPOS_ID)) {
        setDefaultLogicalReposId(rs.getString(DbConstants.POLICY_DEFAULT_LOGICAL_REPOS_ID));
      }
      if (columns.containsKey(DbAliases.POLICY_DEFAULT_LOGICAL_REPOS_ID)) {
        setDefaultLogicalReposId(rs.getString(DbAliases.POLICY_DEFAULT_LOGICAL_REPOS_ID));
      }
      if (columns.containsKey(DbConstants.POLICY_RESTRICTED_LOGICAL_REPOS_ID)) {
        setRestrictedLogicalReposId(rs.getString(DbConstants.POLICY_RESTRICTED_LOGICAL_REPOS_ID));
      }
      if (columns.containsKey(DbAliases.POLICY_RESTRICTED_LOGICAL_REPOS_ID)) {
        setRestrictedLogicalReposId(rs.getString(DbAliases.POLICY_RESTRICTED_LOGICAL_REPOS_ID));
      }
      if (columns.containsKey(DbConstants.POLICY_CONSENT_VERIFY_REQUIRED)) {
        setVerifyRequired(rs.getString(DbConstants.POLICY_CONSENT_VERIFY_REQUIRED));
      }
      else if (columns.containsKey(DbAliases.POLICY_CONSENT_VERIFY_REQUIRED)) {
        setVerifyRequired(rs.getString(DbAliases.POLICY_CONSENT_VERIFY_REQUIRED));
      }
      if (columns.containsKey(DbConstants.POLICY_CASE_RELEASE_REQUIRED)) {
        setReleaseRequired(rs.getString(DbConstants.POLICY_CASE_RELEASE_REQUIRED));
      }
      else if (columns.containsKey(DbAliases.POLICY_CASE_RELEASE_REQUIRED)) {
        setReleaseRequired(rs.getString(DbAliases.POLICY_CASE_RELEASE_REQUIRED));
      }
      if (columns.containsKey(DbConstants.POLICY_ADDITIONAL_QUESTIONS_GRP)) {
        setAdditionalQuestionsGrp(rs.getString(DbConstants.POLICY_ADDITIONAL_QUESTIONS_GRP));
      }
      else if (columns.containsKey(DbAliases.POLICY_ADDITIONAL_QUESTIONS_GRP)) {
        setAdditionalQuestionsGrp(rs.getString(DbAliases.POLICY_ADDITIONAL_QUESTIONS_GRP));
      }
      if (columns.containsKey(DbConstants.POLICY_ARDAIS_ACCT_KEY)) {
        setAccountId(rs.getString(DbConstants.POLICY_ARDAIS_ACCT_KEY));
      }
      if (columns.containsKey(DbAliases.POLICY_ARDAIS_ACCT_KEY)) {
        setAccountId(rs.getString(DbAliases.POLICY_ARDAIS_ACCT_KEY));
      }
      if (columns.containsKey(DbConstants.POLICY_ALLOW_FOR_UNLINKED_YN)) {
        setAllowForUnlinkedCases(rs.getString(DbConstants.POLICY_ALLOW_FOR_UNLINKED_YN));
      }
      if (columns.containsKey(DbAliases.POLICY_ALLOW_FOR_UNLINKED_YN)) {
        setAllowForUnlinkedCases(rs.getString(DbAliases.POLICY_ALLOW_FOR_UNLINKED_YN));
      }
      if (columns.containsKey(DbConstants.POLICY_POLICY_DATA)) {
        setPolicyData(ApiFunctions.getStringFromClob(rs.getClob(DbConstants.POLICY_POLICY_DATA)));
      }
      if (columns.containsKey(DbAliases.POLICY_POLICY_DATA)) {
        setPolicyData(ApiFunctions.getStringFromClob(rs.getClob(DbAliases.POLICY_POLICY_DATA)));
      }
      if (columns.containsKey(DbConstants.POLICY_POLICY_DATA_ENCODING)) {
        setPolicyDataEncoding(rs.getString(DbConstants.POLICY_POLICY_DATA_ENCODING));
      }
      if (columns.containsKey(DbAliases.POLICY_POLICY_DATA_ENCODING)) {
        setPolicyDataEncoding(rs.getString(DbAliases.POLICY_POLICY_DATA_ENCODING));
      }
      if (columns.containsKey(DbConstants.POLICY_CASE_REGISTRATION_FORM)) {
        setCaseRegistrationFormId(rs.getString(DbConstants.POLICY_CASE_REGISTRATION_FORM));
      }
      if (columns.containsKey(DbAliases.POLICY_CASE_REGISTRATION_FORM)) {
        setCaseRegistrationFormId(rs.getString(DbAliases.POLICY_CASE_REGISTRATION_FORM));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }
  
  /**
   * This method parses the policy data XML string. As certain patterns are matched, the
   * appropriate method is called in return.
   */
  private void parsePolicyData() {
    
    // Get the policy data xml.
    String xml = getPolicyData();
    
    //if there is no policy data, just return
    if (ApiFunctions.isEmpty(xml)) {
      return;
    }
    
    //determine which xml encoding scheme was used
    String xmlEncoding = getPolicyDataEncoding();
    StringBuffer xmlFileBuffer = new StringBuffer(100);
    xmlFileBuffer.append(ApiFunctions.CLASSPATH_RESOURCES_PREFIX); 
    xmlFileBuffer.append("policy");
    xmlFileBuffer.append(xmlEncoding.toUpperCase());
    xmlFileBuffer.append(".dtd");
    StringBuffer registrationBuffer = new StringBuffer(100);
    registrationBuffer.append("-//Ardais Corporation//DTD Policy Encoding "); 
    registrationBuffer.append(xmlEncoding.toUpperCase());
    registrationBuffer.append("//EN");

    // Create a default digester.
    Digester digester = makeDigester();

    // Register the location of the category configuration DTD.
    URL dtdURL = getClass().getResource(xmlFileBuffer.toString());
    digester.register(registrationBuffer.toString(), dtdURL.toString());

    digester.push(this);
    
    // Only a single sampleTypeConfiguration object should exist.
    digester.addObjectCreate("*/sampleTypeConfiguration", SampleTypeConfiguration.class);
    digester.addSetProperties("*/sampleTypeConfiguration");
    digester.addSetRoot("*/sampleTypeConfiguration",
      "setSampleTypeConfiguration",
      "com.ardais.bigr.iltds.databeans.SampleTypeConfiguration");
    
    // A sampleTypeConfiguration element can have multiple sampleType elements.
    digester.addObjectCreate("*/sampleTypeConfiguration/sampleType", SampleType.class);
    digester.addSetProperties("*/sampleTypeConfiguration/sampleType");
    digester.addSetNext("*/sampleTypeConfiguration/sampleType",
      "addSampleType",
      "com.ardais.bigr.iltds.databeans.SampleType");

    // A sampleType element can have multiple attribute elements.  Note that as of version
    // 2 of the dtd this is no longer true, but this code must be maintained to render old
    // history records correctly.
    digester.addObjectCreate("*/sampleType/attribute", Attribute.class);
    digester.addSetProperties("*/sampleType/attribute");
    digester.addSetNext("*/sampleType/attribute",
      "addAttribute",
      "com.ardais.bigr.iltds.databeans.Attribute");

    // Now that everything is set up, parse the file.
    try {
      digester.parse(new StringReader(xml));
    }
    catch (SAXException se) {
      throw new ApiException(se);
    }
    catch (IOException ie) {
      throw new ApiException(ie);
    }
  }

  /**
   * Convert the policy object into XML. This method in turn will call the toXml
   * method of the sample type configuration object contained within this object.
   * NOTE:  the encoding scheme must be set on this object before this method is called
   * 
   * @return The XML representation of the policy object.
   */
  public String toXml() {
    
    //make sure the encoding scheme has been set, as it is used to generate the XML
    if (ApiFunctions.isEmpty(getPolicyDataEncoding())) {
      throw new ApiException("The encoding scheme must be set before calling toXml on a PolicyData object");
    }
    
    StringBuffer xml = new StringBuffer();
    
    xml.append("<?xml");
    BigrXmlUtils.writeAttribute(xml, "version", "1.0");
    BigrXmlUtils.writeAttribute(xml, "encoding", "UTF-8");
    xml.append("?>\n");
    xml.append("<!DOCTYPE policy PUBLIC \"-//Ardais Corporation//DTD Policy Encoding ");
    xml.append(getPolicyDataEncoding().toUpperCase());
    xml.append("//EN\" \"policy");
    xml.append(getPolicyDataEncoding().toUpperCase());
    xml.append(".dtd\">\n");

    xml.append("<policy>\n");
    SampleTypeConfiguration stc = getSampleTypeConfiguration();
    if (stc != null) {
      xml.append(stc.toXml());
    }
    xml.append("</policy>\n");
   
    return xml.toString();
  }

  /**
   * This method will return the sample type configuration object if it is not null.  If the
   * object is null, this method will parse the policy data (XML) to get the sample type
   * configuration. If the XML string is empty, then an empty samply type configuration object is
   * returned.
   * 
   * @return The sample type configuration.
   */
  public SampleTypeConfiguration getSampleTypeConfiguration() {
    if (_sampleTypeConfiguration == null) {
      parsePolicyData();
      if (_sampleTypeConfiguration == null) {
        _sampleTypeConfiguration = new SampleTypeConfiguration();
      }
    }
    return _sampleTypeConfiguration;
  }

  public String getAllocationFormat() {
    return GbossFactory.getInstance().getDescription(_allocationFormatCid);
  }

  public String getAccountName() {
    String accountName = IltdsUtils.getAccountName(_accountId) + " (" + _accountId + ")";
    return accountName;
  }

  /**
   * @return
   */
  public String getAccountId() {
    return _accountId;
  }

  /**
   * @return
   */
  public String getAdditionalQuestionsGrp() {
    return _additionalQuestionsGrp;
  }

  /**
   * @return
   */
  public String getAllocationDenominator() {
    return _allocationDenominator;
  }

  /**
   * @return
   */
  public String getAllocationFormatCid() {
    return _allocationFormatCid;
  }

  /**
   * @return
   */
  public String getAllocationNumerator() {
    return _allocationNumerator;
  }

  /**
   * @return
   */
  public String getAllowForUnlinkedCases() {
    return _allowForUnlinkedCases;
  }

  /**
   * @return
   */
  public String getDefaultLogicalReposId() {
    return _defaultLogicalReposId;
  }

  /**
   * @return
   */
  public String getPolicyId() {
    return _policyId;
  }

  /**
   * @return
   */
  public String getPolicyName() {
    return _policyName;
  }

  /**
   * @return
   */
  public String getReleaseRequired() {
    return _releaseRequired;
  }

  /**
   * @return
   */
  public String getRestrictedLogicalReposId() {
    return _restrictedLogicalReposId;
  }

  /**
   * @return
   */
  public String getVerifyRequired() {
    return _verifyRequired;
  }

  /**
   * @param string
   */
  public void setAccountId(String string) {
    _accountId = string;
  }

  /**
   * @param string
   */
  public void setAdditionalQuestionsGrp(String string) {
    _additionalQuestionsGrp = string;
  }

  /**
   * @param i
   */
  public void setAllocationDenominator(String i) {
    _allocationDenominator = i;
  }

  /**
   * @param string
   */
  public void setAllocationFormatCid(String string) {
    _allocationFormatCid = string;
  }

  /**
   * @param i
   */
  public void setAllocationNumerator(String i) {
    _allocationNumerator = i;
  }

  /**
   * @param string
   */
  public void setAllowForUnlinkedCases(String string) {
    _allowForUnlinkedCases = string;
  }

  /**
   * @param string
   */
  public void setDefaultLogicalReposId(String string) {
    _defaultLogicalReposId = string;
  }

  /**
   * @param string
   */
  public void setPolicyId(String string) {
    _policyId = string;
  }

  /**
   * @param string
   */
  public void setPolicyName(String string) {
    _policyName = string;
  }

  /**
   * @param string
   */
  public void setReleaseRequired(String string) {
    _releaseRequired = string;
  }

  /**
   * @param string
   */
  public void setRestrictedLogicalReposId(String string) {
    _restrictedLogicalReposId = string;
  }

  /**
   * @param configuration
   */
  public void setSampleTypeConfiguration(SampleTypeConfiguration configuration) {
    _sampleTypeConfiguration = configuration;
  }

  /**
   * @param string
   */
  public void setVerifyRequired(String string) {
    _verifyRequired = string;
  }
  /**
   * @return
   */
  public String getPolicyData() {
    return _policyData;
  }

  /**
   * @return
   */
  public String getPolicyDataEncoding() {
    //if no encoding has been specified, assume it's the current encoding scheme
    if (ApiFunctions.isEmpty(_policyDataEncoding)) {
      _policyDataEncoding = Constants.CURRENT_POLICY_ENCODING;
    }
    return _policyDataEncoding;
  }

  /**
   * @param string
   */
  public void setPolicyData(String string) {
    _policyData = string;
  }

  /**
   * @param string
   */
  public void setPolicyDataEncoding(String string) {
    _policyDataEncoding = string;
  }

  public String getCaseRegistrationFormId() {
    return _caseRegistrationFormId;
  }
  
  public void setCaseRegistrationFormId(String caseRegistrationFormId) {
    _caseRegistrationFormId = caseRegistrationFormId;
  }
  
  public String getCaseRegistrationFormName() {
    return _caseRegistrationFormName;
  }
  
  public void setCaseRegistrationFormName(String caseRegistrationFormName) {
    _caseRegistrationFormName = caseRegistrationFormName;
  }

  /**
   * @return
   */
  public String getAssociatedIrbs() {
    return _associatedIrbs;
  }

  /**
   * @param string
   */
  public void setAssociatedIrbs(String string) {
    _associatedIrbs = string;
  }
}
