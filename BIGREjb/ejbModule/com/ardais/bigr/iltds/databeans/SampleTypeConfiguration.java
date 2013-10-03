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

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.concept.BigrConcept;
import com.ardais.bigr.concept.BigrConceptList;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.security.SecurityInfo;
import com.gulfstreambio.gboss.GbossFactory;

public class SampleTypeConfiguration implements Serializable {
  static final long serialVersionUID = -977319061664950970L;

  private Map _sampleTypeMap = new HashMap();  //used for quick reference to a sample type
  private List _sampleTypeList = new ArrayList();  //used for ordered reference to sample types
  private boolean _immutable = false;

  public SampleTypeConfiguration() {
    super();
  }

  /**
   * Add a sample type object to the sample type map and list. For each sample type represented in the
   * policy, capture the data within a sample type object. This method is called from the digester
   * when a sample type element is encountered while parsing the XML string.
   * 
   * @param sampleType The sample type object to add to the sample type map and list.
   */
  public void addSampleType(SampleType sampleType) {
    checkImmutable();
    if (sampleType != null) {
      _sampleTypeMap.put(sampleType.getCui(), sampleType);
      _sampleTypeList.add(sampleType);
    }
  }

  /**
   * Convert the sample type configuration object into XML. This method in turn will call the toXml
   * method of each sample type object contained within this object. The order of the sample type
   * is derived from the sample type concept graph.
   * 
   * @return The XML representation of the sample type configuration object.
   */
  public String toXml() {
    StringBuffer xml = new StringBuffer();

    xml.append("<sampleTypeConfiguration>\n");

    // Get the list of all sample types. Iterate through that list. The order of the list is
    // defined by the concept graph. Fetch each sample type object from the hash map.
    BigrConceptList sampleTypeList = 
      SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_SAMPLE_TYPES);
    Iterator iterator = sampleTypeList.iterator();

    // Call the toXml method for each sample type stored within the sample type map.
    while (iterator.hasNext()) {
      String sampleTypeCui = ((BigrConcept)iterator.next()).getCode();
      SampleType st = (SampleType) _sampleTypeMap.get(sampleTypeCui);
      if (st != null) {
        xml.append(st.toXml());
      }
    }
    xml.append("</sampleTypeConfiguration>\n");

    return xml.toString();
  }

  /**
   * 
   * @return
   */
  public String toHtml(String encoding, SecurityInfo securityInfo) {
    StringBuffer html = new StringBuffer();

    html.append("<br>Sample Type Configuration: <ul>");

    // Get the list of all sample types. Iterate through that list. The order of the list is
    // defined by the concept graph. Fetch each sample type object from the hash map.
    BigrConceptList sampleTypeList = 
      SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_SAMPLE_TYPES);
    Iterator iterator = sampleTypeList.iterator();

    while (iterator.hasNext()) {
      String sampleTypeCui = ((BigrConcept) iterator.next()).getCode();
      SampleType st = (SampleType) _sampleTypeMap.get(sampleTypeCui);
      if (st != null) {
        html.append("<li>");
        html.append(st.toHtml(encoding, securityInfo));
        html.append("</li>");
      }
    }
    html.append("</ul>");

    return html.toString();
  }

  /**
   * Return the sample type map.
   * 
   * @return A read-only version of this object's sample type map.
   */
  public Map getSampleTypeMap() {
    if (_sampleTypeMap instanceof UnmodifiableMap) {
      return _sampleTypeMap;
    }
    else {
      return Collections.unmodifiableMap(_sampleTypeMap);
    }
  }

  /**
   * Return the sample type list.
   * 
   * @return A read-only version of this object's sample type list.
   */
  public List getSampleTypeList() {
    if (_sampleTypeList instanceof UnmodifiableList) {
      return _sampleTypeList;
    }
    else {
      return Collections.unmodifiableList(_sampleTypeList);
    }
  }

  /**
   * This method returns the supported sample types as defined by the policy in a legal value set
   * form.
   * 
   * @return The supported sample types as a LegalValueSet, in order as defined by the sample type
   * concept graph.
   */
  public LegalValueSet getSupportedSampleTypesAsLVS() {
    LegalValueSet sampleTypesLVS = new LegalValueSet();
    Iterator sampleTypes = getSupportedSampleTypes().iterator();
    while (sampleTypes.hasNext()) {
      String sampleTypeCui = (String) sampleTypes.next();
      String description = GbossFactory.getInstance().getDescription(sampleTypeCui);
      sampleTypesLVS.addLegalValue(sampleTypeCui, description);      
    }
    return sampleTypesLVS;
  }

  /**
   * This method returns the supported sample types CUIs as defined by the policy.
   * 
   * @return The list of supported sample types CUIs, in the order as defined by the sample type
   *         concept list.
   */
  public List getSupportedSampleTypes() {
    List sampleTypes = new ArrayList();

    Iterator allSampleTypesIterator = 
       SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_SAMPLE_TYPES).iterator();
    while (allSampleTypesIterator.hasNext()) {
      String sampleTypeCui = ((BigrConcept)allSampleTypesIterator.next()).getCode();
      if (isSampleTypeSupported(sampleTypeCui)) {
        sampleTypes.add(sampleTypeCui);
      }
    }
    return sampleTypes;
  }

  /**
   * This method determines if the sample type in question is supported or not. Return true if
   * the sample type is supported, false otherwise. If the sample type is unknown by the policy,
   * return false.
   * 
   * @param sampleTypeCui The sample type to check against.
   * 
   * @return True, if sample type is supported, false if not.
   */
  public boolean isSampleTypeSupported(String sampleTypeCui) {
    // Validate sample type.
    validateSampleTypeCui(sampleTypeCui);

    boolean supported = false;

    SampleType sampleType = (SampleType) _sampleTypeMap.get(sampleTypeCui);
    if (sampleType != null) {
      supported = sampleType.isSupported();
    }
    return supported;
  }

  /**
   * This method validates the sample type cui. The sample type cui cannot be null or invalid,
   * otherwise and exception is thrown.
   * 
   * @param sampleTypeCui The sample type to validate against.
   */
  private void validateSampleTypeCui(String sampleTypeCui) {
    // Validate the sample type.
    if (ApiFunctions.isEmpty(sampleTypeCui)) {
      throw new ApiException("Sample type CUI is null.");
    }
    else {
      BigrConceptList knownSampleTypes = 
         SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_SAMPLE_TYPES);
      if (!knownSampleTypes.containsConcept(sampleTypeCui)) {
        throw new ApiException("Invalid sample type CUI: " + sampleTypeCui + ".");
      }
    }
  }

  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    _immutable = true;
    Iterator sampleTypeIterator = _sampleTypeList.iterator();
    while (sampleTypeIterator.hasNext()) {
      ((SampleType)sampleTypeIterator.next()).setImmutable();
    }
    _sampleTypeMap = Collections.unmodifiableMap(_sampleTypeMap);
    _sampleTypeList = Collections.unmodifiableList(_sampleTypeList);
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
  private void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable SampleTypeConfiguration: " + this);
    }
  }

  /**
  * Get the sample type matching the specified cui
  * @param sampleTypeCui
  * @return  the SampleType (if any) matching the specified cui
  */  
  public SampleType getSampleType(String sampleTypeCui) {
    validateSampleTypeCui(sampleTypeCui);
    return (SampleType)getSampleTypeMap().get(sampleTypeCui);
  }
}
