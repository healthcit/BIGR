package com.ardais.bigr.iltds.databeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.list.UnmodifiableList;
import org.apache.commons.collections.map.UnmodifiableMap;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.BigrXmlUtils;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.gboss.GbossFactory;

public class SampleType implements Serializable {
  static final long serialVersionUID = 6003257215688041810L;

  private String _cui = null;
  private String _cuiDescription = null; 
  private boolean _supported = false;
  private boolean _immutable = false;
  private String _registrationFormId = null;
  private String _registrationFormName = null;

  //Note - although attributes are no longer maintained by policies, this data member and
  //it's associated methods must be kept intact so that history records can be rendered correctly.
  private List _attributeList = new ArrayList();  //used for ordered reference to attributes
  
  private Map _storageTypeMap = new HashMap();  //used for quick reference to a storage type
  private List _storageTypeList = new ArrayList();  //used for ordered reference to storage types

  public SampleType() {
    super();
  }

  /**
   * Add an attribute object to the attribute map and list. For each attribute under a sample type
   * represented in the policy, capture the data within an attribute. This method is called from
   * the digester when an attribute sample type element is encountered while parsing the XML string.
   * 
   * @param attribute The attribute object to add to the attribute list.
   * @deprecated
   */
  public void addAttribute(Attribute attribute) {
    checkImmutable();
    if (attribute != null) {
      _attributeList.add(attribute);
    }
  }

  /**
   * Add a storage type object to the storage type map and list.
   * @param storageType The storage type object to add to the storage type map and list.
   */
  public void addStorageType(StorageType storageType) {
    checkImmutable();
    if (storageType != null) {
      _storageTypeMap.put(storageType.getCui(), storageType);
      _storageTypeList.add(storageType);
    }
  }

  /**
   * Convert the sample type object into XML. 
   * @return The XML representation of the sample type object.
   */
  public String toXml() {
    String supported = (isSupported()) ? "true" : "false";

    StringBuffer xml = new StringBuffer();
    xml.append("<sampleType");
    BigrXmlUtils.writeAttribute(xml, "cui", getCui());
    BigrXmlUtils.writeAttribute(xml, "cuiDescription", getCuiDescription());
    BigrXmlUtils.writeAttribute(xml, "supported", supported);
    BigrXmlUtils.writeAttribute(xml, "registrationFormId", getRegistrationFormId());
    BigrXmlUtils.writeAttribute(xml, "registrationFormName", getRegistrationFormName());
    xml.append("/>\n");
    
    //as of Karma, we no longer write any Attributes out to XML as they are no longer
    //used - KC form definitions have replaced them (i.e registrationFormId above)

    return xml.toString();
  }
  
  /**
   * 
   * @return
   */
  public String toHtml(String encoding, SecurityInfo securityInfo) {
    StringBuffer html = new StringBuffer();

    html.append(Escaper.htmlEscapeAndPreserveWhitespace(getCuiDescription()));
    if (isSupported()) {
      html.append(": collected");
    }
    else {
      html.append(": not collected");
    }
    
    //as of Karma, KC form definitions have replaced Attributes.  However, this method 
    //must still handle them so that history records are rendered correctly
    boolean attributesInUse = Constants.ENCODING_SCHEME_XML1.equalsIgnoreCase(encoding);
    boolean formDefinitionsInUse = Constants.ENCODING_SCHEME_XML2.equalsIgnoreCase(encoding);;
    if (attributesInUse) {
      html.append("<br>Attribute values: ");
      boolean anyAttributesDefined = false;
      Iterator iterator = _attributeList.iterator();
      boolean includeComma = false;
      while (iterator.hasNext()) {
        Attribute a = (Attribute)iterator.next();
        anyAttributesDefined = true;
        if (includeComma) {
          html.append(",&nbsp;");
        }
        html.append(a.toHtml());
        includeComma = true;
      }
      if (anyAttributesDefined) {
        html.append(". Attributes not listed (if any) were marked as prohibited.");
      }
      else {
        html.append("all attributes were marked as prohibited.");
      }
    }
    if (formDefinitionsInUse) {
      if (!ApiFunctions.isEmpty(getRegistrationFormId())) {
        html.append(", Registration Form: ");
        String formName = getRegistrationFormName();
        if (ApiFunctions.isEmpty(formName)) {
          formName = getRegistrationFormId();
        }
        html.append(IcpUtils.prepareLink(getRegistrationFormId(), formName, securityInfo));
      }
    }

    return html.toString();
  }

  /**
   * @return
   */
  public String getCui() {
    return _cui;
  }

  /**
   * @return
   */
  public boolean isSupported() {
    return _supported;
  }

  /**
   * @param string
   */
  public void setCui(String string) {
    checkImmutable();
    _cui = string;
    String cuiDescription = determineCuiDescription();
    if (!ApiFunctions.isEmpty(cuiDescription)) {
      setCuiDescription(cuiDescription);
    }
  }
  
  private String determineCuiDescription() {
    String description = null;
    try {
      description = GbossFactory.getInstance().getDescription(getCui());
    }
    catch (Exception e) {
      //couldn't determine the description, so nothing to do
    }
    return description;
  }

  /**
   * @param b
   */
  public void setSupported(boolean b) {
    checkImmutable();
    _supported = b;
  }

  /**
   * Return the storage type map.
   * 
   * @return A read-only version of this object's storage type map.
   */
  public Map getStorageTypeMap() {
    if (_storageTypeMap instanceof UnmodifiableMap) {
      return _storageTypeMap;
    }
    else {
      return Collections.unmodifiableMap(_storageTypeMap);
    }
  }


  /**
   * Return the storage type list.
   * 
   * @return A read-only version of this object's storage type list.
   */
  public List getStorageTypeList() {
    if (_storageTypeList instanceof UnmodifiableList) {
      return _storageTypeList;
    }
    else {
      return Collections.unmodifiableList(_storageTypeList);
    }
  }

  /**
   * @return
   */
  public String getCuiDescription() {
    return _cuiDescription;
  }

  /**
   * @param string
   */
  public void setCuiDescription(String string) {
    checkImmutable();
    _cuiDescription = string;
  }

  public String getRegistrationFormId() {
    return _registrationFormId;
  }
  
  public void setRegistrationFormId(String registrationFormId) {
    _registrationFormId = registrationFormId;
  }

  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    _immutable = true;
    Iterator attributeIterator = _attributeList.iterator();
    while (attributeIterator.hasNext()) {
      ((Attribute)attributeIterator.next()).setImmutable();
    }
    _attributeList = Collections.unmodifiableList(_attributeList);
    Iterator storageTypeIterator = _storageTypeList.iterator();
    while (storageTypeIterator.hasNext()) {
      ((StorageType)storageTypeIterator.next()).setImmutable();
    }
    _storageTypeMap = Collections.unmodifiableMap(_storageTypeMap);
    _storageTypeList = Collections.unmodifiableList(_storageTypeList);
  }

  /**
   * Is this instance immutable.
   */
  public boolean isImmutable() {
    return _immutable;
  }

  /**
   * Throw an exception if this instance is immutable.
   */
  void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable SampleType: " + this);
    }
  }
  
  public String getRegistrationFormName() {
    return _registrationFormName;
  }
  
  public void setRegistrationFormName(String registrationFormName) {
    _registrationFormName = registrationFormName;
  }
}
