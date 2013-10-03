package com.ardais.bigr.iltds.databeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.gboss.GbossFactory;

public class DerivativeOperation implements Serializable {
  public static final long serialVersionUID = 1690179150384237119L;

  private String _cui = null;
  private String _cuiDescription = null; 
  private boolean _multipleParents = false;
  private boolean _immutable = false;
  private Map _parentSampleTypeMap = new HashMap();  //used for quick reference to a parent sample type
  private List _parentSampleTypeList = new ArrayList();  //used for ordered reference to parent sample types

  public DerivativeOperation() {
    super();
  }

  /**
   * Convert the derivative operation object into XML.
   * 
   * @return The XML representation of the derivative operation object.
   */
  public String toXml() {
    StringBuffer xml = new StringBuffer();

    xml.append("<derivativeOperation");

    String multipleParents = (isMultipleParents()) ? "true" : "false";

    BigrXmlUtils.writeAttribute(xml, "cui", getCui());
    BigrXmlUtils.writeAttribute(xml, "cuiDescription", getCuiDescription());
    BigrXmlUtils.writeAttribute(xml, "multipleParents", multipleParents);


    xml.append(">\n");

    //output the parent sample types
    Iterator iterator = getParentSampleTypeList().iterator();
    while (iterator.hasNext()) {
      xml.append(((ParentSampleType)iterator.next()).toXml());
    }

    xml.append("</derivativeOperation>\n");

    return xml.toString();
  }
  
  /**
   * 
   * @return
   */
  public String toHtml() {
    StringBuffer html = new StringBuffer();

    html.append(Escaper.htmlEscapeAndPreserveWhitespace(getCuiDescription()));
    html.append(": ");

    String multipleParents = (isMultipleParents()) ? "true" : "false";

    html.append("multipleParents = ");
    html.append((isMultipleParents()) ? "true" : "false");
    html.append(".");

    //output the parent sample type(s)
    html.append("Parent sample type(s): ");
    Iterator iterator = getParentSampleTypeList().iterator();
    boolean includeComma = false;
    while (iterator.hasNext()) {
      if (includeComma) {
        html.append(",&nbsp;");
      }
      html.append(((ParentSampleType)iterator.next()).toHtml());
      includeComma = true;
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

  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    _immutable = true;
    Iterator iterator = _parentSampleTypeList.iterator();
    while (iterator.hasNext()) {
      ((ParentSampleType)iterator.next()).setImmutable();
    }
    _parentSampleTypeMap = Collections.unmodifiableMap(_parentSampleTypeMap);
    _parentSampleTypeList = Collections.unmodifiableList(_parentSampleTypeList);
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
      throw new IllegalStateException("Attempted to modify an immutable DerivativeOperation: " + this);
    }
  }

  /**
   * @return
   */
  public boolean isMultipleParents() {
    return _multipleParents;
  }

  /**
   * @param b
   */
  public void setMultipleParents(boolean b) {
    _multipleParents = b;
  }

  /**
   * Add a parent sample to the parent sample map and list. This method is called
   * from the digester when a parent sample type element is encountered while parsing the XML
   * string.
   * 
   * @param sampleType The parent sample type object to add to the parent sample type map and list.
   */
  public void addParentSampleType(ParentSampleType parentSampleType) {
    checkImmutable();
    if (parentSampleType != null) {
      _parentSampleTypeMap.put(parentSampleType.getCui(), parentSampleType);
      _parentSampleTypeList.add(parentSampleType);
    }
  }

  /**
   * @return
   */
  public List getParentSampleTypeList() {
    return _parentSampleTypeList;
  }
  
  /**
   * Determine if a sample type cui is a valid parent sample type
   * for this operation
   */
  public boolean isSampleTypeValidParent(String sampleTypeCui) {
    return (getParentSampleTypeMap().get(sampleTypeCui) != null);
  }
  
  /**
   * Find the ParentSampleType corresponding to a sample type cui, if any
   * @param parentSampleTypeCui
   * @return the ParentSampleType corresponding to parentSampleTypeCui if it exists,
   * null otherwise
   */
  private ParentSampleType getParentSampleType(String parentSampleTypeCui) {
    return (ParentSampleType) getParentSampleTypeMap().get(parentSampleTypeCui);
  }
  
  /**
   * Find the child sample types allowed for a parent sample type cui
   * @param parentSampleTypeCui
   * @return a List containing the ChildSampleTypes for the ParentSampleType corresponding
   * to parentSampleTypeCui if any, empty list otherwise.
   */
  private List getChildSampleTypesForParent(String parentSampleTypeCui) {
    ParentSampleType parent = getParentSampleType(parentSampleTypeCui);
    if (parent == null) {
      return new ArrayList();
    }
    else {
      return parent.getChildSampleTypeList();
    }
  }
  
  /**
   * Return a List of cuis corresponding to the union of all child sample types allowed
   * across the parent types passed in for this operation.  Any given cui will appear at
   * most once in the list, even if multiple parent types allow it as a child
   * @param parentSampleTypeCuis
   * @return a List of cuis corresponding to the union of all child sample types allowed
   * across the parent types passed in for this operation if any, an empty list other
   */
  public List getChildSampleTypeCuisForParentTypeCuis(List parentSampleTypeCuis) {
    List returnValue = new ArrayList();
    if (!ApiFunctions.isEmpty(parentSampleTypeCuis)) {
      Iterator parentSampleTypeCuisIterator = parentSampleTypeCuis.iterator();
      while (parentSampleTypeCuisIterator.hasNext()) {
        String parentSampleTypeCui = (String)parentSampleTypeCuisIterator.next();
        List myChildren = getChildSampleTypesForParent(parentSampleTypeCui);
        Iterator myChildrenIterator = myChildren.iterator();
        while (myChildrenIterator.hasNext()) {
          String childSampleTypeCui = ((ChildSampleType)myChildrenIterator.next()).getCui();
          if (!returnValue.contains(childSampleTypeCui)) {
            returnValue.add(childSampleTypeCui);
          }
        }
      }
    }
    return returnValue;
  }

  /**
   * @return
   */
  private Map getParentSampleTypeMap() {
    return _parentSampleTypeMap;
  }

}
