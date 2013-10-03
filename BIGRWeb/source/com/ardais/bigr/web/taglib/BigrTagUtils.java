package com.ardais.bigr.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.ResponseUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;

/**
 * Contains utility methods for implementing custom tags.
 */
public class BigrTagUtils {

  protected static Log _log = LogFactory.getLog("com.ardais.bigr.HTML_DEBUG_COMMENTS");

  /**
   * Creates a new <code>BigrTagUtils</code>.  Marked as private since
   * this is a class of static utility methods, and thus should never
   * be instantiated.
   */
  private BigrTagUtils() {
    super();
  }
  /**
   * Writes the attribute specified by the name and value parameters
   * to the response stream.  A space is inserted before the attribute,
   * so that multiple calls to this method can be made without the
   * caller having to insert a space into the response stream.  If
   * the value is empty (<code>null</code> or the empty string), then
   * the attribute is not written.
   *
   * @param  name  the attribute name
   * @param  value  the attribute value
   */
  public static void writeAttribute(PageContext pageContext, String name, String value) throws JspException {
    if (!ApiFunctions.isEmpty(value)) {
      ResponseUtils.write(pageContext, " ");
      ResponseUtils.write(pageContext, name);
      ResponseUtils.write(pageContext, "=\"");
      ResponseUtils.write(pageContext, Escaper.htmlEscape(value));
      ResponseUtils.write(pageContext, "\"");
    }
  }

  /**
   * Writes the attribute specified by the name and value parameters
   * to the specified <code>StringBuffer</code>.  A space is inserted before the attribute,
   * so that multiple calls to this method can be made without the
   * caller having to insert a space into the <code>StringBuffer</code>.  If
   * the value is <code>null</code>, then the attribute is not written.
   *
   * @param  buf  the <code>StringBuffer</code>
   * @param  name  the attribute name
   * @param  value  the attribute value
   */
  public static void writeAttribute(StringBuffer buf, String name, String value) {
    // Note: do not change this to check for empty, since we explicitly want to allow
    // empty values to be written (e.g. value="" is acceptable and sometimes desired).
    if (value != null) {
      buf.append(' ');
      buf.append(name);
      buf.append("=\"");
      buf.append(Escaper.htmlEscape(value));
      buf.append("\"");
    }
  }

  /**
   * Writes an HTML comment that contains debug information signifying the start of some HTML.
   * This is typically used to indicate the start of HTML being rendered by the specified object, 
   * where the object is typically a JSP or JSP tag.  To use this from a JSP tag, call it directly, 
   * specifying <code>this</code> as the object.  To use this from a JSP, use the 
   * debugCommentStart tag.  
   * 
   * @param  o  the object
   * @return  An specially formatted HTML comment. 
   */
  public static String writeDebugCommentStart(Object o) {
    if (_log.isDebugEnabled()) {
      StringBuffer buf = new StringBuffer(64);
      writeDebugCommentStart(o, buf);
      return buf.toString();
    }
    else {
      return "";
    }
  }

  /**
   * Writes an HTML comment that contains debug information signifying the start of some HTML to
   * the specified StringBuffer.  This is typically used to indicate the start of HTML being 
   * rendered by the specified object, where the object is typically a JSP or JSP tag.  To use
   * this from a JSP tag, call it directly, specifying <code>this</code> as the object.  To use
   * this from a JSP, use the debugCommentStart tag.  
   * 
   * @param  o  the object
   * @param  buf  the StringBuffer
   * @return  An specially formatted HTML comment. 
   */
  public static void writeDebugCommentStart(Object o, StringBuffer buf) {
    if (_log.isDebugEnabled()) {
      buf.append("\n<!-- debug.");
      buf.append(ApiFunctions.shortClassName(o.getClass().getName()));
      buf.append(".start -->\n");
    }
  }

  /**
   * Writes an HTML comment that contains debug information signifying the end of some HTML.
   * This is typically used to indicate the end of HTML being rendered by the specified object, 
   * where the object is typically a JSP or JSP tag.  To use this from a JSP tag, call it 
   * directly, specifying <code>this</code> as the object.  To use this from a JSP, use the 
   * debugCommentEnd tag. 
   * 
   * @param  o  the object
   * @return  An specially formatted HTML comment. 
   */
  public static String writeDebugCommentEnd(Object o) {
    if (_log.isDebugEnabled()) {
      StringBuffer buf = new StringBuffer(64);
      writeDebugCommentEnd(o, buf);
      return buf.toString();
    }
    else {
      return "";
    }
  }

  /**
   * Writes an HTML comment that contains debug information signifying the end of some HTML to
   * the specified StringBuffer.  This is typically used to indicate the end of HTML being 
   * rendered by the specified object, where the object is typically a JSP or JSP tag.  To use
   * this from a JSP tag, call it directly, specifying <code>this</code> as the object.  To use
   * this from a JSP, use the debugCommentEnd tag.  
   * 
   * @param  o  the object
   * @param  buf  the StringBuffer
   * @return  An specially formatted HTML comment. 
   */
  public static void writeDebugCommentEnd(Object o, StringBuffer buf) {
    if (_log.isDebugEnabled()) {
      buf.append("\n<!-- debug.");
      buf.append(ApiFunctions.shortClassName(o.getClass().getName()));
      buf.append(".end -->\n");
    }
  }
}
