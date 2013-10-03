package com.gulfstreambio.kc.web.support;

import java.util.Iterator;

import javax.servlet.ServletRequest;

import com.gulfstreambio.kc.det.DetValueSet;

/**
 * An HTML element value set for a hierarchical set of values. This abstract class does not
 * implement the abstract <code>createHtmlElementValue</code> method, which must be implemented by
 * subclasses.
 */
public abstract class HtmlElementValueSetHierarchy extends HtmlElementValueSet {

  /**
   * Creates a new <code>HtmlElementValueSetHierarchy</code> from the specified
   * <code>DetValueSet</code>, for an HTML element with the specified property. This
   * constructor is intended to be used for multivalued elements.
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
  public HtmlElementValueSetHierarchy(ServletRequest request, DataFormElementContext context,
                                      DetValueSet valueSet, String[] selectedValues, 
                                      boolean includeOther) {
    super(request, context, valueSet, selectedValues, includeOther);
  }

  /**
   * Creates a new <code>HtmlElementValueSetHierarchy</code> from the specified
   * <code>DetValueSet</code>, for an HTML element with the specified property. This
   * constructor is intended to be used for multivalued elements.
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
  public HtmlElementValueSetHierarchy(ServletRequest request, DataFormElementContext context,
                                      DetValueSet valueSet, String selectedValue, 
                                      String[] excludedValues, boolean includeOther) {
    super(request, context, valueSet, selectedValue, excludedValues, includeOther);
  }

  /**
   * Creates a new <code>HtmlElementValueSetHierarchy</code> from the specified
   * <code>DetValueSet</code>, for an HTML element with the specified property. This
   * constructor is intended to be used for multivalued elements.
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
  public HtmlElementValueSetHierarchy(ServletRequest request, DataFormElementContext context,
                                      DetValueSet valueSet, String selectedValue, 
                                      boolean includeOther) {
    super(request, context, valueSet, selectedValue, includeOther);
  }

  /**
   * Creates the HTML for this value set and appends it to the supplied <code>StringBuffer</code>.
   * The HTML consists of a TABLE with a row for each value in this value set. This method delegates
   * to the toHtml method of its contained values to generate the HTML for each value.
   * 
   * @param contextPath the servlet context path
   * @param buf the <code>StringBuffer</code> to which the HTML should be appended
   */
  public void toHtml(String contextPath, StringBuffer buf) {
    buf.append("<table");
    WebUtils.writeHtmlAttribute(buf, "cellspacing", "0");
    WebUtils.writeHtmlAttribute(buf, "cellpadding", "0");
    WebUtils.writeHtmlAttribute(buf, "border", "0");
    buf.append('>');

    Iterator values = getElementValues();
    while (values.hasNext()) {
      HtmlElementValue value = (HtmlElementValue) values.next();
      value.toHtml(contextPath, buf);
    }

    buf.append("</table>");
  }

}
