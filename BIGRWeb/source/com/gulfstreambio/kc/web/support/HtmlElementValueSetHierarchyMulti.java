package com.gulfstreambio.kc.web.support;

import javax.servlet.ServletRequest;

import com.gulfstreambio.kc.det.DetValueSet;

/**
 * An HTML element value set for a hierarchical set of values for a multivalued element.
 */
public class HtmlElementValueSetHierarchyMulti extends HtmlElementValueSetHierarchy {

  /**
   * Creates a new <code>HtmlElementValueSetHierarchyMulti</code> from the specified
   * <code>DetValueSet</code>, for an HTML element with the specified property.
   * 
   * @param request the servlet request
   * @param context the <code>DataFormElementContext</code> that represents the data element
   *        currently being rendered
   * @param valueSet the element value set
   * @param selectedValues the set of values to select when the HTML is generated. This may be null
   *          or an empty set to indicate that no values should be selected
   * @param includeOther <code>true</code> if the "other" value should be included when generating
   *          HTML, if it is present; <code>false</code> otherwise
   */
  public HtmlElementValueSetHierarchyMulti(ServletRequest request, 
                                           DataFormElementContext context, DetValueSet valueSet, 
                                           String[] selectedValues, boolean includeOther) {
    super(request, context, valueSet, selectedValues, includeOther);
  }

  /**
   * Creates and returns an {@link HtmlElementValueHierarchyMulti}instance with a null parent. To
   * create an <code>HtmlElementValueHierarchyMulti</code> with a parent, call
   * {@link HtmlElementValueHierarchyMulti#createHtmlElementValue(String, String)} on the parent
   * value.
   * 
   * @param value the submitted value
   * @param display the display value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @return The newly created <code>HtmlElementValueHierarchyMulti</code>.
   */
  public HtmlElementValue createHtmlElementValue(String value, String display, String name, 
                                                 String id, boolean selected) {
    return new HtmlElementValueHierarchyMulti(value, display, name, id, selected, null);
  }
}
