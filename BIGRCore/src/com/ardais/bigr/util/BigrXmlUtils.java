package com.ardais.bigr.util;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;

/**
 * Static utility methods for creating and parsing XML documents.  
 */
public class BigrXmlUtils {

  private static final int NUM_SPACES_PER_INDENT_LEVEL = 2;
  
  /**
   * Creates a new <code>BigrXmlUtils</code>.  Marked as private since
   * this is a class of static utility methods, and thus should never
   * be instantiated.
   */
  private BigrXmlUtils() {
    super();
  }

  /**
   * Writes the specified element start tag to the supplied StringBuffer.  This method will
   * write the opening angle bracket (&lt;) and the tag name, but will not write the closing 
   * angle bracket (&gt;).  In addition, a newline will be written, followed by an appropriate
   * number of spaces as indicated in the indentLevel parameter, before the start tag.   
   *
   * @param  buf  the StringBuffer
   * @param  tagName  the name of the tag
   * @param  indentLevel  the indent level of the tag.  Specify zero for a root element.
   */
  public static void writeElementStartTag(StringBuffer buf, String tagName, int indentLevel) {
    if (ApiFunctions.isEmpty(tagName)) {
      throw new ApiException("Attempt to write XML start tag without supplying tag name.");
    }
    else {
      buf.append("\n");
      writeIndent(buf, indentLevel);
      buf.append('<');
      buf.append(tagName);      
    }
  }

  /**
   * Writes the specified element end tag to the supplied StringBuffer.  This method will
   * write the complete end tag, including the opening angle bracket (&lt;), the slash, the tag 
   * name, and the closing angle bracket (&gt;).  In addition, a newline will be written, followed 
   * by an appropriate number of spaces as indicated in the indent parameter, before the start tag.
   *
   * @param  buf  the StringBuffer
   * @param  tagName  the name of the tag
   * @param  indentLevel  the indent level of the tag.  Specify zero for a root element.
   */
  public static void writeElementEndTag(StringBuffer buf, String tagName, int indentLevel) {
    if (ApiFunctions.isEmpty(tagName)) {
      throw new ApiException("Attempt to write XML start tag without supplying tag name.");
    }
    else {
      buf.append("\n");
      writeIndent(buf, indentLevel);
      buf.append("</");
      buf.append(tagName);      
      buf.append(">");
    }
  }
  
  private static void writeIndent(StringBuffer buf, int indentLevel) {
    int numSpaces = indentLevel * NUM_SPACES_PER_INDENT_LEVEL; 
    for (int i = 0; i < numSpaces; i++) {
      buf.append(' ');        
    }    
  }

  /**
   * Writes the attribute specified by the name and value parameters to the supplied StringBuffer.
   * A space is inserted before the attribute, so that multiple calls to this method can be made 
   * in succession without the caller having to be concerned with spaces.  If the value is empty 
   * (<code>null</code> or the empty string), then the attribute is not written.  The value is
   * escaped appropriately before being written.
   *
   * @param  buf  the StringBuffer
   * @param  name  the attribute name
   * @param  value  the attribute value
   */
  public static void writeAttribute(StringBuffer buf, String name, String value) {
    if (!ApiFunctions.isEmpty(value)) {
      buf.append(' ');
      buf.append(name);
      buf.append("=\"");
      Escaper.xmlEscape(value, buf);
      buf.append('"');
    }
  }

  /**
   * Writes the element value to the supplied StringBuffer, escaping it appropriately before 
   * writing it.  If the value is empty (<code>null</code> or the empty string), then the value 
   * is not written.
   *
   * @param  buf  the StringBuffer
   * @param  value  the element value
   */
  public static void writeElementValue(StringBuffer buf, String value) {
    if (!ApiFunctions.isEmpty(value)) {
      Escaper.xmlEscape(value, buf);
    }
  }
}
