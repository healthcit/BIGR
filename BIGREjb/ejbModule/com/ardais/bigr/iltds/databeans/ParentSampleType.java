package com.ardais.bigr.iltds.databeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.list.UnmodifiableList;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.util.BigrXmlUtils;

public class ParentSampleType extends SampleType implements Serializable {
  public static final long serialVersionUID = 905640361170331910L;

  private Map _childSampleTypeMap = new HashMap();  //used for quick reference to a child sample type
  private List _childSampleTypeList = new ArrayList();  //used for ordered reference to child sample types

  public ParentSampleType() {
    super();
  }

  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    super.setImmutable();
    Iterator iterator = _childSampleTypeList.iterator();
    while (iterator.hasNext()) {
      ((ChildSampleType)iterator.next()).setImmutable();
    }
    _childSampleTypeMap = Collections.unmodifiableMap(_childSampleTypeMap);
    _childSampleTypeList = Collections.unmodifiableList(_childSampleTypeList);
  }

  /**
   * Add a child sample to the child sample map and list. This method is called
   * from the digester when a child sample type element is encountered while parsing the XML
   * string.
   * 
   * @param sampleType The sample type object to add to the sample type map and list.
   */
  public void addChildSampleType(ChildSampleType childSampleType) {
    checkImmutable();
    if (childSampleType != null) {
      _childSampleTypeMap.put(childSampleType.getCui(), childSampleType);
      _childSampleTypeList.add(childSampleType);
    }
  }

  /**
   * Convert the parent sample type object into XML. This method in turn will call the toXml
   * method of each non-prohibited attribute object (allowed for the sample type) contained 
   * within this object.
   * 
   * @return The XML representation of the sample type object.
   */
  public String toXml() {
    StringBuffer xml = new StringBuffer();

    xml.append("<parentSampleType");

    BigrXmlUtils.writeAttribute(xml, "cui", getCui());
    BigrXmlUtils.writeAttribute(xml, "cuiDescription", getCuiDescription());

    xml.append(">\n");

    //output the child sample types
    Iterator iterator = getChildSampleTypeList().iterator();
    while (iterator.hasNext()) {
      xml.append(((ChildSampleType)iterator.next()).toXml());
    }

    xml.append("</parentSampleType>\n");

    return xml.toString();
  }
  
  /**
   * 
   * @return
   */
  public String toHtml() {
    StringBuffer html = new StringBuffer();

    html.append(Escaper.htmlEscapeAndPreserveWhitespace(getCuiDescription()));
    html.append("<br>");

    //output the child sample type(s)
    html.append("Child sample type(s): ");
    Iterator iterator = getChildSampleTypeList().iterator();
    boolean includeComma = false;
    while (iterator.hasNext()) {
      if (includeComma) {
        html.append(",&nbsp;");
      }
      html.append(((ChildSampleType)iterator.next()).toHtml());
      includeComma = true;
    }

    return html.toString();
  }

  /**
   * @return
   */
  public List getChildSampleTypeList() {
    if (_childSampleTypeList instanceof UnmodifiableList) {
      return _childSampleTypeList;
    }
    else {
      return Collections.unmodifiableList(_childSampleTypeList);
    }
  }

}
