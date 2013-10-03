package com.gulfstreambio.kc.web.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.gulfstreambio.kc.det.DetValueSetValue;

/**
 * Holds a single value of an {@link HtmlElementValueSet}. An <code>HtmlElementValue</code> is
 * derived from an {@link DetValueSetValue}, and can be part of a hierarchical value set. 
 * This abstract base class must be subclassed to implement the 
 * {@link #toHtml(String, StringBuffer)} method.
 */
public abstract class HtmlElementValue {

  /**
   * The value that is to be submitted.
   */
  private String _value;

  /**
   * The value that is to be displayed.
   */
  private String _display;

  /**
   * The value to be used for the HTML name attribute for this value.
   */
  private String _name;

  /**
   * The value to be used for the HTML id attribute for this value.
   */
  private String _id;

  /**
   * Indicates whether this value should be selected or not when its HMTL is generated.
   */
  private boolean _selected;

  /**
   * The parent value of this value. This only applies for hierarchical value sets and will be null
   * for each value of flat value sets.
   */
  private HtmlElementValue _parent;

  /**
   * The children values of this value. Each entry is an <code>HtmlElementValue</code>. This only
   * applies for hierarchical value sets and will be an empty list for each value of flat value
   * sets.
   */
  private List _childValues;

  /**
   * The 0-based level of this value within its value set. This will be zero for each value of a
   * flat value set.
   */
  private int _level;

  /**
   * Creates a new <code>HtmlElementValue</code> from the specified <code>DetValueSetValue</code>.
   * 
   * @param value the <code>DetValueSetValue</code> that holds the value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @param parent the parent <code>HtmlElementValue</code>. Should be null for root values.
   */
  public HtmlElementValue(DetValueSetValue value, String name, String id, boolean selected, 
                          HtmlElementValue parent) { 
    this(value.getCui(), value.getDescription(), name, id, selected, parent);
  }

  /**
   * Creates a new <code>HtmlElementValue</code> from the specified value and display value.
   * 
   * @param value the submitted value
   * @param display the display value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @param parent the parent <code>HtmlElementValue</code>. Should be null for root values.
   */
  public HtmlElementValue(String value, String display, String name, String id, boolean selected,
                          HtmlElementValue parent) {
    if (value == null) {
      throw new IllegalArgumentException("value is required when creating an HtmlElementValue");
    }
    if (display == null) {
      throw new IllegalArgumentException("display is required when creating an HtmlElementValue");
    }

    _value = value;
    _display = display;
    _name = name;
    _id = id;
    _parent = parent;
    _childValues = new ArrayList();

    // If this value is selected, mark this value and all of its parents as selected.
    setSelected(selected);
    if ((isSelected()) && (parent != null)) {
      for (HtmlElementValue p = parent; p != null; p = p.getParent()) {
        p.setSelected(true);
      }
    }

    // Set the level of this value within its hierarchy.
    _level = 0;
    if (parent != null) {
      for (HtmlElementValue p = parent; p != null; p = p.getParent()) {
        _level++;
      }
    }
  }

  /**
   * Returns the submitted value.
   * 
   * @return the value to be submitted
   */
  protected String getValue() {
    return _value;
  }

  /**
   * Returns the display value.
   * 
   * @return the value to be displayed
   */
  protected String getDisplay() {
    return _display;
  }

  /**
   * Returns the parent value of this value.
   * 
   * @return the parent value. Returns null if there is no parent value.
   */
  private HtmlElementValue getParent() {
    return _parent;
  }

  /**
   * Returns the value to be used for the HTML name attribute for this value.
   * 
   * @return The name.
   */
  protected String getName() {
    return _name;
  }

  /**
   * Returns the value to be used for the HTML id attribute for this value.
   * 
   * @return The id.
   */
  protected String getId() {
    return _id;
  }

  /**
   * Creates a new value from the specified <code>DetValueSetValue</code> and adds it to this value as
   * a child.
   * 
   * @param value the <code>DetValueSetValue</code> that holds the value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @return The newly created value.
   */
  public HtmlElementValue addElementValue(DetValueSetValue value, String name, String id,
                                          boolean selected) {
    return addElementValue(value.getCui(), value.getDescription(), name, id, selected);
  }

  /**
   * Creates a new value from the specified <code>DetValueSetValue</code> and adds it to this value as
   * a child at the specified index.
   * 
   * @param index the zero-based index
   * @param value the <code>DetValueSetValue</code> that holds the value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @return The newly created value.
   */
  public HtmlElementValue addElementValue(int index, DetValueSetValue value, String name, 
                                          String id, boolean selected) {
    return addElementValue(index, value.getCui(), value.getDescription(), name, id, selected);
  }

  /**
   * Creates a new value from the specified submitted value and display value and adds it to this
   * value as a child.
   * 
   * @param value the submitted value
   * @param display the display value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @return The newly created value.
   */
  public HtmlElementValue addElementValue(String value, String display, String name, String id,
                                          boolean selected) {
    HtmlElementValue elementValue = createHtmlElementValue(value, display, name, id, selected);
    _childValues.add(elementValue);
    return elementValue;
  }

  /**
   * Creates a new value from the specified submitted value and display value and adds it to this
   * value as a child at the specified index.
   * 
   * @param index the zero-based index
   * @param value the submitted value
   * @param display the display value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @return The newly created value.
   */
  public HtmlElementValue addElementValue(int index, String value, String display, String name, 
                                          String id, boolean selected) {
    HtmlElementValue elementValue = createHtmlElementValue(value, display, name, id, selected);
    _childValues.add(index, elementValue);
    return elementValue;
  }

  /**
   * Creates and returns a new <code>HtmlElementValue</code> from the specified submitted value
   * and display value. This is a factory method that concrete subclasses must override to return a
   * concrete <code>HtmlElementValue</code>. Concrete subclasses should return the same concrete
   * type as their own type.
   * 
   * @param value the submitted value
   * @param display the display value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @return The <code>HtmlElementValue</code>.
   */
  protected abstract HtmlElementValue createHtmlElementValue(String value, String display, 
                                                             String name, String id, 
                                                             boolean selected);

  /**
   * Returns all direct child values of this value.
   * 
   * @return An <code>Iterator</code> of child <code>HtmlElementValue</code>s. Returns an empty
   *         iterator if there are no children.
   */
  protected Iterator getElementValues() {
    return _childValues.iterator();
  }

  /**
   * Returns true if this value has children.
   * 
   * @return <code>true</code> if this value has children; <code>false</code> otherwise.
   */
  protected boolean hasChildren() {
    return !_childValues.isEmpty();
  }

  /**
   * Returns the level of this value within its hierarchical <code>HtmlElementValueSet</code>.
   * The level is 0-based, so a root <code>HtmlElementValue</code> has level equal to 0.
   * 
   * @return the level
   */
  protected int getLevel() {
    return _level;
  }

  /**
   * Returns an indication of whether this value will be selected or not when its HMTL is generated.
   * This is determined from the set of selected values in the containing
   * <code>HtmlElementValueSet</code>.
   * 
   * @return <code>true</code> if this value should be selected; <code>false</code> otherwise
   */
  protected boolean isSelected() {
    return _selected;
  }

  /**
   * Sets whether this value should be selected or not when its HMTL is generated.
   * 
   * @param selected <code>true</code> if this value should be selected; <code>false</code>
   *          otherwise
   */
  private void setSelected(boolean selected) {
    _selected = selected;
  }

  /**
   * Creates the HTML for this <code>HtmlElementValue</code> and appends it to the supplied
   * <code>StringBuffer</code>. Subclasses must implement this method to generate appropriate
   * HTML for the style of input control they represent.
   * 
   * @param contextPath the servlet context path
   * @param buf the <code>StringBuffer</code> to which the HTML should be appended
   */
  public abstract void toHtml(String contextPath, StringBuffer buf);
}
