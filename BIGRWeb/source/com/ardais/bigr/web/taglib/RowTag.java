package com.ardais.bigr.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.logic.IterateTag;
/**
 * <p>This tag generates table rows (i.e. &lt;tr&gt;....&lt;/tr&gt; elements) with the
 * background color set differently for alternating odd and even rows. This tag only operates
 * properly if embedded in an IterateTag.</p>
 *
 * <p>The following parameters can be specified for this Tag:</p>
 * <ul>
 * <li><code>oddColor </code> - The color for Odd numbered rows
 * <li><code>evenColor</code> - The color for Even numbered rows
 * <li><code>oddStyleClass</code> - The style class for Odd numbered rows
 * <li><code>evenStyleClass</code> - The style class for Even numbered rows
 * <li><code>align</code> - The alignment for the table row
 * <li><code>valign</code> - The vertical alignment for the table row
 * </ul>
 *
 * <p>Additionally this tag inherits the Event Handler and Style attributes
 * from the BaseHandlerTag which can also be specified</p>
 *
 * @author Amarda Business Systems Ltd
 * @version 1.0
 */

public class RowTag extends org.apache.struts.taglib.html.BaseHandlerTag {

  // ----------------------------------------------------- Instance Variables

  protected final static String QUOTE = "\"";

  /**
   *  Color of Odd rows in a table
   */
  protected String oddColor = null;

  /**
   *  Color of Even rows in a table
   */
  protected String evenColor = null;

  /**
   *  StyleClass of Odd rows in a table
   */
  protected String oddStyleClass = null;

  /**
   *  Style Class of Even rows in a table
   */
  protected String evenStyleClass = null;

  /**
   *  Alignment of the table row
   */
  protected String align = null;

  /**
   *  Vertical Alignment of the table row
   */
  protected String valign = null;

  /**
   * End of Tag Processing
   *
   * @exception JspException if a JSP exception occurs
   */
  public int doEndTag() throws JspException {

    StringBuffer buffer = new StringBuffer();

    // Create a <tr> element based on the parameters
    buffer.append("<tr");

    // Prepare this HTML elements attributes
    prepareAttributes(buffer);

    buffer.append(">");

    // Add Body Content
    if (bodyContent != null)
      buffer.append(bodyContent.getString().trim());

    buffer.append("</tr>");

    // Render this element to our writer
    JspWriter writer = pageContext.getOut();
    try {
      writer.print(buffer.toString());
    } catch (IOException e) {
      throw new JspException("Exception in RowTag doEndTag():" + e.toString());
    }

    return EVAL_PAGE;
  }
  // ----------------------------------------------------- Public Methods

  /**
   * Start of Tag processing
   *
   * @exception JspException if a JSP exception occurs
   */
  public int doStartTag() throws JspException {

    // Continue processing this page
    return EVAL_BODY_BUFFERED;

  }
  /**
   *  Return the Alignment
   */
  public String getAlign() {
    return (this.align);
  }
  /**
   *  Return the color of Even rows
   */
  public String getEvenColor() {
    return (this.evenColor);
  }
  /**
   *  Return the Style Class of Even rows
   */
  public String getEvenStyleClass() {
    return (this.evenStyleClass);
  }
  /**
   * Return the color of Odd rows
   */
  public String getOddColor() {
    return (this.oddColor);
  }
  /**
   * Return the Style Class of Odd rows
   */
  public String getOddStyleClass() {
    return (this.oddStyleClass);
  }
  /**
   * Determine the Row Number - from the IterateTag
   */
  protected int getRowNumber() {

    // Determine if embedded in an IterateTag
    Tag tag = findAncestorWithClass(this, IterateTag.class);
    if (tag == null)
      return 1;

    // Determine the current row number
    IterateTag iterator = (IterateTag) tag;
    //        return iterator.getLengthCount() + 1;
    return iterator.getIndex() + 1;
  }
  /**
   *  Return the Vertical Alignment
   */
  public String getValign() {
    return (this.valign);
  }
  /**
   * Format attribute="value" from the specified attribute & value
   */
  protected String prepareAttribute(String attribute, String value) {

    return value == null ? "" : " " + attribute + "=" + QUOTE + value + QUOTE;

  }
  /**
   * Prepare the attributes of the HTML element
   */
  protected void prepareAttributes(StringBuffer buffer) throws JspException {

    // Determine if it is an "Odd" or "Even" row
    boolean evenNumber = (getRowNumber() % 2) == 0 ? true : false;

    // Append bgcolor parameter
    buffer.append(prepareBgcolor(evenNumber));

    // Append CSS class parameter
    buffer.append(prepareClass(evenNumber));

    // Append "align" parameter
    buffer.append(prepareAttribute("align", align));

    // Append "valign" parameter
    buffer.append(prepareAttribute("valign", valign));

    // Append Event Handler details
    buffer.append(prepareEventHandlers());

    // Append Style details
    buffer.append(prepareStyles());

  }
  /**
   * Format the bgcolor attribute depending on whether
   * the row is odd or even.
   *
   * @param evenNumber Boolean set to true if an even numbered row
   *
   */
  protected String prepareBgcolor(boolean evenNumber) {

    if (evenNumber)
      return prepareAttribute("bgcolor", evenColor);
    else
      return prepareAttribute("bgcolor", oddColor);

  }
  /**
   * Format the Style sheet class attribute depending on whether
   * the row is odd or even.
   *
   * @param evenNumber Boolean set to true if an even numbered row
   *
   */
  protected String prepareClass(boolean evenNumber) {

    if (evenNumber)
      return prepareAttribute("class", evenStyleClass);
    else
      return prepareAttribute("class", oddStyleClass);

  }
  /**
   * Release resources after Tag processing has finished.
   */
  public void release() {

    super.release();

    oddColor = null;
    evenColor = null;
    oddStyleClass = null;
    evenStyleClass = null;
    align = null;
    valign = null;

  }
  /**
   * Set the Alignment
   *
   * @param Value for Alignment
   */

  public void setAlign(String align) {
    this.align = align;
  }
  /**
   * Set the color of Even rows
   *
   * @param color HTML bgcolor value for Even rows
   */
  public void setEvenColor(String color) {
    this.evenColor = color;
  }
  /**
   * Set the styleClass of Even rows
   *
   * @param styleClass HTML Style Class value for Even rows
   */
  public void setEvenStyleClass(String styleClass) {
    this.evenStyleClass = styleClass;
  }
  /**
   * Set the color of Odd rows
   *
   * @param color HTML bgcolor value for Odd rows
   */
  public void setOddColor(String color) {
    this.oddColor = color;
  }
  /**
   * Set the Style Class of Odd rows
   *
   * @param styleClass HTML Style Class value for Odd rows
   */
  public void setOddStyleClass(String styleClass) {
    this.oddStyleClass = styleClass;
  }
  /**
   * Set the Vertical Alignment
   *
   * @param Value for Vertical Alignment
   */

  public void setValign(String valign) {
    this.valign = valign;
  }
}
