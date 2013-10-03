package com.ardais.bigr.web.taglib;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.bean.WriteTag;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.ardais.bigr.api.Escaper;

/**
 * This tag writes a formatted chunk of text into an HTML table.
 * 
 * It writes each line as a row in a table (e.g. bracketed by tr tags) 
 * and converts spaces to indentation via .css padding-left attributes.
 * 
 * It expects a table with one column as a container for its output.
 * 
 */
public class IndentedTextToTableTag extends WriteTag {

  static int NUMPADS = 1; // number of padding-left spaces (in em's) for each space
  private int colspan = 1;

  /**
   * Creates a new <code>BeanWrite<code>.
   */
  public IndentedTextToTableTag() {
    super();
  }
  /**
   * Process the start tag.
   *
   * @exception JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException {

    // Look up the requested bean (if necessary)
    if (ignore) {
      if (RequestUtils.lookup(pageContext, name, scope) == null)
        return (SKIP_BODY); // Nothing to output
    }

    // Look up the requested property value
    Object value = RequestUtils.lookup(pageContext, name, property, scope);
    if (value == null)
      return (SKIP_BODY); // Nothing to output

    // Print this property value to our output writer, suitably filtered
    String output = value.toString();
    if (filter) // rewrite special html characters, if filter is set
      output = Escaper.htmlEscape(output);

    String formatted = formatTextAsHtml(output);
    ResponseUtils.write(pageContext, formatted);

    // Continue processing this page
    return (SKIP_BODY);
  }

  private String formatTextAsHtml(String output) {
    StringBuffer sb = new StringBuffer();
    int len = output.length();

    // use a state machine to parse the formatted text and output HTML rows and cells
    final int START = 0; // start of line
    final int INSPACE = 1; // counting initial spaces, which count as indents
    final int INTEXT = 2; // in the text after initial spaces
    int state = START;
    int spaceCount = 0;
    for (int i = 0; i < len; i++) {
      char c = output.charAt(i);
      if (state == START) {
        sb.append("<tr>"); // start whole thing with new row
        if (c == ' ') { // space
          spaceCount = 1;
          state = INSPACE;
        }
        else if (c == '\n') { // new row
          sb.append("</tr>");
          state = START;
        }
        else { // normal text
          sb.append("<td colspan=\"");
          sb.append(getColspan());
          sb.append("\">");

          sb.append(c);
          state = INTEXT;
        }
      }
      else if (state == INSPACE) {
        if (c == ' ') {
          spaceCount++;
        }
        else { // anything but leading spaces terminate indentation
          sb.append("<td");

          sb.append(" colspan=\"");
          sb.append(getColspan());
          sb.append('"');

          sb.append("style=\"padding-left: ");
          sb.append(Integer.toString(spaceCount * NUMPADS)); // add indent
          sb.append("em;\">");

          sb.append(c); // consume the text character
          state = INTEXT;
        }
      }
      else if (state == INTEXT) {
        if (c == '\n') {
          sb.append("</td></tr>");
          state = START;
        }
        else {
          sb.append(c);
        }
      }

    }
    // implicit DONE state
    if (state == INTEXT) {
      // ended in text
      sb.append("</tr>"); // end the last row
      // other cases can be ignored:
      // in space:  indent, but no text, so discard
      // start:     all lines done, expected state
    }
    return sb.toString();
  }

  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
  }
  /**
   * Returns the colspan.
   * @return int
   */
  public int getColspan() {
    return colspan;
  }

  /**
   * Sets the colspan.
   * @param colspan The colspan to set
   */
  public void setColspan(int colspan) {
    this.colspan = colspan;
  }

  public static void main(String[] args) {
    String s = " one indent\n";
    String sf = new IndentedTextToTableTag().formatTextAsHtml(s);
    printHTMLTable(sf);

    StringBuffer sb1 = new StringBuffer();
    sb1.append("    indent 4 \n");
    sb1.append("no indent at all with funny chars,,@!$#%$%\n");
    sb1.append(" one indent\n");
    String result = new IndentedTextToTableTag().formatTextAsHtml(sb1.toString());
    printHTMLTable(result);
  }
  private static void printHTMLTable(String fragment) {
    System.out.println("TABLE:  (paste in browser viewer to check) \n\n");
    System.out.println("<html><table>");
    System.out.println(fragment);
    System.out.println("</table></html>\n\n");
  }
}
