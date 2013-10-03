package com.ardais.bigr.iltds.databeans;

import java.io.Serializable;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.util.BigrXmlUtils;

public class ChildSampleType extends SampleType implements Serializable {
  public static final long serialVersionUID = -6546225846263056566L;

  public ChildSampleType() {
    super();
  }

  /**
   * Convert the child sample type object into XML.
   * 
   * @return The XML representation of the child sample type object.
   */
  public String toXml() {
    StringBuffer xml = new StringBuffer();

    xml.append("<childSampleType");

    BigrXmlUtils.writeAttribute(xml, "cui", getCui());
    BigrXmlUtils.writeAttribute(xml, "cuiDescription", getCuiDescription());

    xml.append("/>\n");

    return xml.toString();
  }
  
  /**
   * 
   * @return
   */
  public String toHtml() {
    StringBuffer html = new StringBuffer();

    html.append(Escaper.htmlEscapeAndPreserveWhitespace(getCuiDescription()));
    return html.toString();
  }

}
