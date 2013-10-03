package com.gulfstreambio.kc.web.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletRequest;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.UniqueIdGenerator;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.det.DetValueSetValue;

/**
 * A UI-centric representation of a {@link com.gulfstreambio.kc.det.DetValueSet} as a set of
 * values to be displayed for an HTML element. This class creates and uses {@link HtmlElementValue}
 * objects to represent each value within the value set. This abstract base class must be subclassed
 * by concrete subclasses that create instances of concrete <code>HtmlElementValue</code>s and
 * provide the appropriate HTML.
 * <p>
 * This class and its concrete subclasses are meant to be used by JSP tags to generate necessary
 * HTML and JavaScript to display a value set, one or more of which are to be chosen as the value(s)
 * of the HTML element. Both singlevalued and multivalued HTML elements can be supported by concrete
 * subclasses. Both hierarchical and non-hierarchical value sets can be supported by concrete
 * subclasses.
 */
public abstract class HtmlElementValueSet {

  /**
   * The list of <code>HtmlElementValue</code> objects that comprise this
   * <code>HtmlElementValueSet</code>. If this value set is hierarchical, this contains only the
   * root element values.
   */
  private List _elementValues;

  /**
   * The set of values that should be selected when generating the HTML for this
   * <code>HtmlElementValueSet</code>. Note that this set of values may include values that are
   * not present in this <code>HtmlElementValueSet</code>. This allows support of multiple
   * <code>HtmlElementValueSet</code> per element, and allows the caller to simply pass all values
   * that should be selected to each <code>HtmlElementValueSet</code>.
   */
  private String[] _selectedValues;

  /**
   * The set of values that should be excluded when generating the HTML for this
   * <code>HtmlElementValueSet</code>.
   */
  private String[] _excludedValues;

  /**
   * Indicates whether the "other" value should be included when generating HTML for this
   * <code>HtmlElementValueSet</code>.
   */
  private boolean _includeOtherValue;
  
  /**
   * The value of the HTML name attribute for the input control(s) that comprise the value set.
   */
  private String _name;
  
  private boolean _registeredIdWithContext;
  
  /**
   * Creates a new <code>HtmlElementValueSet</code> from the specified
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
  public HtmlElementValueSet(ServletRequest request, DataFormElementContext context,
                             DetValueSet valueSet, String[] selectedValues, boolean includeOther) {

    initialize(request, context, valueSet, selectedValues, null, includeOther);
  }

  /**
   * Creates a new <code>HtmlElementValueSet</code> from the specified
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
  public HtmlElementValueSet(ServletRequest request, DataFormElementContext context,
                             DetValueSet valueSet, String selectedValue, boolean includeOther) {

    String[] selectedValues;
    if (selectedValue != null) {
      selectedValues = new String[] {selectedValue};
    }
    else {
      selectedValues = new String[0];
    }
    initialize(request, context, valueSet, selectedValues, null, includeOther);
  }

  /**
   * Creates a new <code>HtmlElementValueSet</code> from the specified
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
  public HtmlElementValueSet(ServletRequest request, DataFormElementContext context,
                             DetValueSet valueSet, String selectedValue,
                             String[] excludedValues, boolean includeOther) {

    String[] selectedValues;
    if (selectedValue != null) {
      selectedValues = new String[] {selectedValue};
    }
    else {
      selectedValues = new String[0];
    }
    initialize(request, context, valueSet, selectedValues, excludedValues, includeOther);
  }

  /**
   * Initializes a new instance of this class.
   * 
   * @param request the HTTP request
   * @param context the <code>DataFormElementContext</code> that represents the data element
   *        currently being rendered
   * @param valueSet the element value set
   * @param selectedValues the set of values to select when the HTML is generated. This may be
   *          <code>null</code> or an empty set to indicate that no values should be selected
   * @param excludedValues the set of values to exclude when the HTML is generated. This may be
   *          <code>null</code> or an empty set to indicate that no values should be excluded
   * @param includeOther true if the "other" value should be included when generating HTML, if it is
   *          present; false otherwise
   */
  private void initialize(ServletRequest request, DataFormElementContext context,
                          DetValueSet valueSet, String[] selectedValues,
                          String[] excludedValues, boolean includeOther) {

    _elementValues = new ArrayList();

    _includeOtherValue = includeOther;
    _selectedValues = (selectedValues == null) ? new String[0] : selectedValues;
    _excludedValues = (excludedValues == null) ? new String[0] : excludedValues;

    _registeredIdWithContext = false;
    
    // Create all of the HtmlElementValues for this graph.
    UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
    initializeElementValues(idgen, context, valueSet);
  }

  /**
   * Creates an <code>HtmlElementValue</code> for each value in the specified value set, and
   * creates <code>HtmlElementValue</code>s for all descendant values as well.
   * 
   * @param valueSet the element value set
   */
  private void initializeElementValues(UniqueIdGenerator idgen, DataFormElementContext context, 
                                       DetValueSet valueSet) {
    DetValueSetValue[] children = valueSet.getValues();
    _name = idgen.getUniqueId();
    context.setHtmlNameValueSet(_name);
    initializeElementValues(idgen, context, children, null);
  }

  private void initializeElementValues(UniqueIdGenerator idgen, DataFormElementContext context,
                                       DetValueSetValue[] values, HtmlElementValue parent) {
    String name = getName();
    for (int i = 0; i < values.length; i++) {
      DetValueSetValue value = values[i];
      String cui = value.getCui();

      // If this value is the "other" value and we do not want others in the set of values to
      // be displayed, then skip this value.
      if (!_includeOtherValue && value.isOtherValue()) {
        continue;
      }

      // If this value is to be excluded, then skip it.
      if (ApiFunctions.safeArrayContains(_excludedValues, cui)) {
        continue;
      }

      // Determine if this value is to be selected.
      boolean selected = ApiFunctions.safeArrayContains(_selectedValues, cui);
        
      DetValueSetValue[] children = value.getValues(); 
      boolean hasChildren = (children.length > 0);
      
      // Create the HtmlElementValue for this value.
      String id = idgen.getUniqueId();
      if (!hasChildren && !_registeredIdWithContext) {
        context.setHtmlIdValueSet(id);
        _registeredIdWithContext = true;
      }
      HtmlElementValue htmlValue = (parent == null) 
        ? addElementValue(value, name, id, selected) 
        : parent.addElementValue(value, name, id, selected);

      // If this value has children, then create HtmlElementValues for all of its children.
      if (hasChildren) {
        initializeElementValues(idgen, context, children, htmlValue);
      }
    }
  }

  /**
   * Creates a new value from the specified <code>DetValueSetValue</code> and adds it to this
   * <code>HtmlElementValueSet</code>.
   * 
   * @param value the <code>DetValueSetValue</code>
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @return The newly created <code>HtmlElementValue</code>.
   */
  public HtmlElementValue addElementValue(DetValueSetValue value, String name, String id, 
                                          boolean selected) {
    return addElementValue(value.getCui(), value.getDescription(), name, id, selected);
  }

  /**
   * Creates a new value from the specified <code>DetValueSetValue</code> and adds it to this
   * <code>HtmlElementValueSet</code> at the specified index.
   * 
   * @param index the zero-based index
   * @param value the <code>DetValueSetValue</code>
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @return The newly created <code>HtmlElementValue</code>.
   */
  public HtmlElementValue addElementValue(int index, DetValueSetValue value, String name, 
                                          String id, boolean selected) {
    return addElementValue(index, value.getCui(), value.getDescription(), name, id, selected);
  }

  /**
   * Creates a new value from the specified submitted value and display value and adds it to this
   * <code>HtmlElementValueSet</code>.
   * 
   * @param value the submitted value
   * @param display the display value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @return The newly created <code>HtmlElementValue</code>.
   */
  public HtmlElementValue addElementValue(String value, String display, String name, String id,
                                          boolean selected) {
    HtmlElementValue htmlValue = createHtmlElementValue(value, display, name, id, selected);
    _elementValues.add(htmlValue);
    return htmlValue;
  }

  /**
   * Creates a new value from the specified submitted value and display value and adds it to this
   * <code>HtmlElementValueSet</code> at the specified index.
   * 
   * @param index the zero-based index
   * @param value the submitted value
   * @param display the display value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @return The newly created <code>HtmlElementValue</code>.
   */
  public HtmlElementValue addElementValue(int index, String value, String display, String name, 
                                          String id, boolean selected) {
    HtmlElementValue htmlValue = createHtmlElementValue(value, display, name, id, selected);
    _elementValues.add(index, htmlValue);
    return htmlValue;
  }

  /**
   * Creates and returns a new <code>HtmlElementValue</code> from the specified submitted value
   * and display value. This is a factory method that concrete subclasses must override to return a
   * concrete <code>HtmlElementValue</code>.
   * 
   * @param value the submitted value
   * @param display the display value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @return The newly created <code>HtmlElementValue</code>.
   */
  protected abstract HtmlElementValue createHtmlElementValue(String value, String display, 
                                                             String name, String id,
                                                             boolean selected);

  /**
   * Returns the values that comprise this <code>HtmlElementValueSet</code>.
   * 
   * @return An Iterator of <code>HtmlElementValue</code>s.
   */
  protected Iterator getElementValues() {
    return _elementValues.iterator();
  }

  /**
   * Returns the value of the HTML name attribute for the input control(s) that comprise the 
   * value set.
   * 
   * @return The name value.
   */
  protected String getName() {
    return _name;
  }
  
  /**
   * Creates the HTML for this value set and returns it as a string.
   * 
   * @param contextPath the servlet context path
   * @return The HTML.
   * @see #toHtml(StringBuffer)
   */
  public String toHtml(String contextPath) {
    StringBuffer buf = new StringBuffer(1024);
    toHtml(contextPath, buf);
    return buf.toString();
  }

  /**
   * Creates the HTML for this <code>HtmlElementValueSet</code> and appends it to the supplied
   * <code>StringBuffer</code>. This abstract method must be overriden by subclasses to generate
   * HTML that appropriately represents the value set. See the documentation of concrete subclasses
   * for the actual HTML that is generated.
   * 
   * @param contextPath the servlet context path
   * @param buf the StringBuffer to which the HTML should be appended
   */
  public abstract void toHtml(String contextPath, StringBuffer buf);

}
