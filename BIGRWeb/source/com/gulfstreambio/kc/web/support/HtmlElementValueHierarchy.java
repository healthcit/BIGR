package com.gulfstreambio.kc.web.support;

import java.util.Iterator;

import com.ardais.bigr.api.Escaper;
import com.gulfstreambio.kc.det.DetValueSetValue;

/**
 * Holds a single value of a hierarchical {@link HtmlElementValueSetHierarchy}. This abstract base
 * class must be subclassed to implement the {@link #toHtmlLeaf(StringBuffer)}method.
 */
public abstract class HtmlElementValueHierarchy extends HtmlElementValue {

  /**
   * Creates a new <code>HtmlElementValueHierarchy</code> from the specified submitted value and
   * display value.
   * 
   * @param value the submitted value
   * @param display the display value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @param parent the parent value. Should be null for root values.
   */
  public HtmlElementValueHierarchy(String value, String display, String name, String id, 
                                   boolean selected, HtmlElementValueHierarchy parent) {
    super(value, display, name, id, selected, parent);
  }

  /**
   * Creates a new <code>HtmlElementValueHierarchy</code> from the specified
   * <code>DetValueSetValue</code>.
   * 
   * @param value the <code>DetValueSetValue</code> that holds the value
   * @param name the value of the HTML name attribute
   * @param id the value of the HTML id attribute
   * @param selected <code>true</code> if this value is selected; <code>false</code> otherwise  
   * @param parent the parent value. Should be null for root values.
   */
  public HtmlElementValueHierarchy(DetValueSetValue value, String name, String id,
                                   boolean selected, HtmlElementValueHierarchy parent) {
    super(value, name, id, selected, parent);
  }

  /**
   * Creates the HTML for this <code>HtmlElementValueHierarchy</code>. If this value represents a
   * non-leaf node, then the generated HTML consists of a TR with an image that allows the non-leaf
   * to be expanded and collapsed, and another TR with a TABLE that holds all of the children. If
   * this value represents a leaf node, the HTML is dictated by the
   * {@link #toHtmlLeaf(StringBuffer)}method.
   * 
   * @param contextPath the servlet context path
   * @param buf the <code>StringBuffer</code> to which the HTML should be appended
   */
  public void toHtml(String contextPath, StringBuffer buf) {
    String value = getValue();
    String display = getDisplay();

    // This concept has children, so append the HTML to allow it to be expanded and collapsed,
    // start a new table for its children, and call the toHtml method of all children to
    // generate their HTML.
    if (hasChildren()) {
      buf.append("<tr><td");
      WebUtils.writeHtmlAttribute(buf, "style", "cursor: hand; padding-left: 0.5em");
      buf.append('>');

      buf.append("<img");
      if (isSelected()) {
        WebUtils.writeHtmlAttribute(buf, "src", WebUtils.getImageSourceCollapse(contextPath));
      }
      else {
        WebUtils.writeHtmlAttribute(buf, "src", WebUtils.getImageSourceExpand(contextPath));
      }
      WebUtils.writeHtmlAttribute(buf, "width", "12");
      WebUtils.writeHtmlAttribute(buf, "height", "12");
      WebUtils.writeHtmlAttribute(buf, "border", "0");
      buf.append("> ");

      buf.append(Escaper.htmlEscape(display));
      buf.append("</td></tr>");

      buf.append("<tr");
      if (!isSelected()) {
        WebUtils.writeHtmlAttribute(buf, "style", "display: none;");
      }
      buf.append("><td><table");
      WebUtils.writeHtmlAttribute(buf, "cellspacing", "0");
      WebUtils.writeHtmlAttribute(buf, "cellpadding", "0");
      WebUtils.writeHtmlAttribute(buf, "border", "0");
      WebUtils.writeHtmlAttribute(buf, "width", "100%");
      WebUtils.writeHtmlAttribute(buf, "class", "graphHierarchy");
      WebUtils.writeHtmlAttribute(buf, "style", "margin-left: " + String.valueOf(getLevel() + 1)
          + "em;");
      buf.append('>');

      Iterator children = getElementValues();
      while (children.hasNext()) {
        HtmlElementValue childValue = (HtmlElementValue) children.next();
        childValue.toHtml(contextPath, buf);
      }
      buf.append("</table></td></tr>");
    }

    // This concept does not have children, so append the HTML for the appropriate input control.
    else {
      toHtmlLeaf(contextPath, buf);
    }
  }

  /**
   * Creates the HTML for this <code>HtmlElementValueHierarchy</code> that represents a leaf node.
   * Subclasses must implement this method to generate appropriate HTML for the style of input
   * control they represent.
   * 
   * @param contextPath the servlet context path
   * @param buf the <code>StringBuffer</code> to which the HTML should be appended
   */
  protected abstract void toHtmlLeaf(String contextPath, StringBuffer buf);

}
