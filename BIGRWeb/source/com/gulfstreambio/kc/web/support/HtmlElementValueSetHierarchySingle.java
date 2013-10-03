package com.gulfstreambio.kc.web.support;

import javax.servlet.ServletRequest;

import com.gulfstreambio.kc.det.DetValueSet;

/**
 * An HTML element value set for a hierarchical set of values for a singlevalued element.
 */
public class HtmlElementValueSetHierarchySingle extends HtmlElementValueSetHierarchy {

  /**
   * Creates a new <code>HtmlElementValueSetHierarchySingle</code> from the specified
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
  public HtmlElementValueSetHierarchySingle(ServletRequest request,
                                            DataFormElementContext context, DetValueSet valueSet, 
                                            String selectedValue, String[] excludedValues,
                                            boolean includeOther) {
    super(request, context, valueSet, selectedValue, excludedValues, includeOther);
  }

  /**
   * Creates and returns an {@link HtmlElementValueHierarchySingle}instance with a null parent. To
   * create an <code>HtmlElementValueHierarchySingle</code> with a parent, call
   * {@link HtmlElementValueHierarchySingle#createHtmlElementValue(String, String)}on the parent
   * value.
   * 
   * @param value the submitted value
   * @param display the display value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @return The newly created <code>HtmlElementValueHierarchySingle</code>.
   */
  public HtmlElementValue createHtmlElementValue(String value, String display, String name, 
                                                 String id, boolean selected) {
    return new HtmlElementValueHierarchySingle(value, display, name, id, selected, null);
  }

}
