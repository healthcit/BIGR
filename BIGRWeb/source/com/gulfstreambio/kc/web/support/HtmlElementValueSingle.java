package com.gulfstreambio.kc.web.support;

import com.ardais.bigr.api.Escaper;
import com.gulfstreambio.kc.det.DetValueSetValue;

/**
 * Holds a single value of a non-hierarchical {@link HtmlElementValueSetSingle}. 
 */
public class HtmlElementValueSingle extends HtmlElementValue {

  /**
   * Creates a new <code>HtmlElementValueSingle</code> from the specified submitted value and 
   * display value.
   * 
   * @param  value  the submitted value
   * @param  display  the display value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @param  parent  the parent value.  Should be null for root values. 
   */
  public HtmlElementValueSingle(String value, String display, String name, String id, 
                                boolean selected, HtmlElementValueSingle parent) {
    super(value, display, name, id, selected, parent);
  }

  /**
   * Creates a new <code>HtmlElementValueSingle</code> from the specified <code>DetValueSetValue</code>.
   * 
   * @param  value  the <code>DetValueSetValue</code> that holds the value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @param  parent  the parent value.  Should be null for root values. 
   */
  public HtmlElementValueSingle(DetValueSetValue value, String name, String id, boolean selected, 
                                HtmlElementValueSingle parent) {
    super(value, name, id, selected, parent);
  }

  /**
   * Creates and returns an <code>HtmlElementValueSingle<code> instance whose parent is this 
   * instance.
   * 
   * @param  value  the value
   * @param  display  the display value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @return  The <code>HtmlElementValueSingle<code>.
   */
  public HtmlElementValue createHtmlElementValue(String value, String display, String name, 
                                                 String id, boolean selected) {
    return new HtmlElementValueSingle(value, display, name, id, selected, this); 
  }

  public void toHtml(String contextPath, StringBuffer buf) {
    // We intentionally do not use name or id with the options since the name and id are applied
    // to the parent SELECT element.
    buf.append("<option");
    WebUtils.writeHtmlAttribute(buf, "value", getValue());
    if (isSelected()) {
      buf.append(" selected");
    }
    buf.append('>');
    Escaper.htmlEscape(getDisplay(), buf);
    buf.append("</option>");
  }

}
