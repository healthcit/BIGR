package com.gulfstreambio.kc.web.support;

import com.ardais.bigr.api.Escaper;
import com.gulfstreambio.kc.det.DetValueSetValue;

/**
 * Holds a single value of a hierarchical {@link HtmlElementValueSetHierarchySingle} for a
 * singlevalued element that is generated as a radio button.
 */
public class HtmlElementValueHierarchySingle extends HtmlElementValueHierarchy {

  /**
   * Creates a new <code>HtmlElementValueHierarchySingle</code> from the specified submitted value
   * and display value.
   * 
   * @param value the submitted value
   * @param display the display value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @param parent the parent value. Should be null for root values.
   */
  public HtmlElementValueHierarchySingle(String value, String display, String name, String id,
                                         boolean selected, HtmlElementValueHierarchySingle parent) {
    super(value, display, name, id, selected, parent);
  }

  /**
   * Creates a new <code>HtmlElementValueHierarchySingle</code> from the specified
   * <code>DetValueSetValue</code>.
   * 
   * @param value the <code>DetValueSetValue</code> that holds the value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @param parent the parent value. Should be null for root values.
   */
  public HtmlElementValueHierarchySingle(DetValueSetValue value, String name, String id,
                                         boolean selected, HtmlElementValueHierarchySingle parent) {
    super(value, name, id, selected, parent);
  }

  /**
   * Creates and returns an <code>HtmlElementValueHierarchySingle</code> instance whose parent is
   * this instance.
   * 
   * @param value the value
   * @param display the display value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @return The <code>HtmlElementValueHierarchySingle</code>.
   */
  public HtmlElementValue createHtmlElementValue(String value, String display, String name, 
                                                 String id, boolean selected) {
    return new HtmlElementValueHierarchySingle(value, display, name, id, selected, this);
  }

  /**
   * Creates the HTML for this value that represents a leaf node in the hierarchy of a singlevalued
   * element. The generated HTML consists of a radio button within a TR, which is checked if the
   * value represented is the selected value.
   * 
   * @param contextPath the servlet context path
   * @param buf the StringBuffer to which the HTML should be appended
   */
  protected void toHtmlLeaf(String contextPath, StringBuffer buf) {
    buf.append("<tr><td>");
    buf.append("<input");
    WebUtils.writeHtmlAttribute(buf, "type", "radio");
    WebUtils.writeHtmlAttribute(buf, "name", getName());
    WebUtils.writeHtmlAttribute(buf, "id", getId());
    WebUtils.writeHtmlAttribute(buf, "value", getValue());
    WebUtils.writeHtmlAttribute(buf, "style", "margin: 0");
    if (isSelected()) {
      buf.append(" checked");
    }
    buf.append('>');
    Escaper.htmlEscape(getDisplay(), buf);
    buf.append("&nbsp;");
    buf.append("</td></tr>");
  }

}
