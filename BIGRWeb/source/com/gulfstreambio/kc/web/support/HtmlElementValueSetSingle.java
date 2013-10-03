package com.gulfstreambio.kc.web.support;

import java.util.Iterator;

import javax.servlet.ServletRequest;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.det.DetValueSet;

/**
 * An HTML element value set for a non-hierarchical set of values for a singlevalued element.
 */
public class HtmlElementValueSetSingle extends HtmlElementValueSet {

  /**
   * The onchange event handler.
   */
  private String _onchange;
  
  private String _id;

  /**
   * Creates a new <code>HtmlElementValueSetSingle</code> from the specified
   * <code>DetValueSet</code>, for an HTML element with the specified property.
   * 
   * @param request the servlet request
   * @param context the <code>DataFormElementContext</code> that represents the data element
   *        currently being rendered
   * @param valueSet the element value set
   * @param selectedValue the value to select when the HTML is generated. This may be null to
   *          indicate that no value should be selected.
   * @param includeOther <code>true</code> if the "other" value should be included when generating
   *          HTML, if it is present; <code>false</code> otherwise
   */
  public HtmlElementValueSetSingle(ServletRequest request, DataFormElementContext context, 
                                   DetValueSet valueSet, String selectedValue, 
                                   boolean includeOther) {
    super(request, context, valueSet, selectedValue, includeOther);
  }

  /**
   * Creates a new <code>HtmlElementValueSetSingle</code> from the specified
   * <code>DetValueSet</code>, for an HTML element with the specified property.
   * 
   * @param request the servlet request
   * @param context the <code>DataFormElementContext</code> that represents the data element
   *        currently being rendered
   * @param valueSet the element value set
   * @param selectedValue the value to select when the HTML is generated. This may be null to
   *          indicate that no value should be selected.
   * @param excludedValues values to exclude from the value set
   * @param includeOther <code>true</code> if the "other" value should be included when generating
   *          HTML, if it is present; <code>false</code> otherwise
   */
  public HtmlElementValueSetSingle(ServletRequest request, DataFormElementContext context,
                                   DetValueSet valueSet, String selectedValue, 
                                   String[] excludedValues, boolean includeOther) {
    super(request, context, valueSet, selectedValue, excludedValues, includeOther);
  }

  /**
   * Creates and returns an {@link HtmlElementValueSingle} instance with a null parent. To create
   * an <code>HtmlElementValueSingle</code> with a parent, call
   * {@link HtmlElementValueSingle#createHtmlElementValue(String, String)} on the parent value.
   * 
   * @param value the submitted value
   * @param display the display value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @return The newly created <code>HtmlElementValueSingle</code>.
   */
  public HtmlElementValue createHtmlElementValue(String value, String display, String name, 
                                                 String id, boolean selected) {
    // Save the id of the first value, since we'll use it as the id of the SELECT we generate
    // in the toHtml() method.
    if (_id == null) {
      _id = id;
    }
    return new HtmlElementValueSingle(value, display, name, id, selected, null);
  }

  /**
   * Sets the onchange event handler for the HMTL SELECT element generated by this value set. This
   * is especially useful since the onchange event does not bubble.
   * 
   * @param eventHandler the event handler JavaScript
   */
  public void setOnchange(String eventHandler) {
    _onchange = eventHandler;
  }

  /**
   * Returns the JavaScript for the onchange event.
   * 
   * @return The JavaScript.
   */
  public String getOnchange() {
    return _onchange;
  }

  /**
   * Creates the HTML for this value set and appends it to the supplied <code>StringBuffer</code>.
   * The HTML consists of a SELECT element with an OPTION element for each value in this value set.
   * This method delegates to the toHtml method of its contained values to generate the OPTION
   * element for each value.
   * 
   * @param contextPath the servlet context path
   * @param buf the <code>StringBuffer</code> to which the HTML should be appended
   */
  public void toHtml(String contextPath, StringBuffer buf) {
    // Write the SELECT begin tag.
    buf.append("<select");
    WebUtils.writeHtmlAttribute(buf, "name", getName());
    WebUtils.writeHtmlAttribute(buf, "id", _id);
    String onchange = getOnchange();
    if (!ApiFunctions.isEmpty(onchange)) {
      buf.append(" onchange=\"");
      buf.append(onchange);
      buf.append("\"");
    }
    buf.append('>');

    // Create an OPTION for each value in the iterator.
    Iterator values = getElementValues();
    while (values.hasNext()) {
      HtmlElementValue value = (HtmlElementValue) values.next();
      value.toHtml(contextPath, buf);
    }

    // Write the SELECT end tag.
    buf.append("</select>");
  }

}
