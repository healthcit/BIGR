package com.ardais.bigr.web.taglib;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.bean.WriteTag;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.ardais.bigr.api.Escaper;

/**
 */
public class BeanWriteTag extends WriteTag {

  private boolean _whitespace;

  /**
   * Creates a new <code>BeanWrite<code>.
   */
  public BeanWriteTag() {
    super();
  }
  /**
   * Process the start tag.
   *
   * @exception JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException {

    String name = getName();
    
    // Look up the requested bean (if necessary)
    //Object bean = null;
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
    if ((filter) && (_whitespace))
      ResponseUtils.write(pageContext, Escaper.htmlEscapeAndPreserveWhitespace(output));
    else if (filter)
      ResponseUtils.write(pageContext, Escaper.htmlEscape(output));
    else if (_whitespace)
      ResponseUtils.write(pageContext, Escaper.htmlPreserveWhitespace(output));
    else
      ResponseUtils.write(pageContext, output);

    // Continue processing this page
    return (SKIP_BODY);
  }
  /**
   * Returns whether whitespace in the body is to be preserved or not.
   */
  public boolean getWhitespace() {
    return _whitespace;
  }
  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
    _whitespace = false;
  }
  /**
   * Sets whether whitespace in the body is to be preserved or not.
   *
   * @param  preserve  <code>true</code> if whitespace is to be preserved; <code>false</code> otherwise
   */
  public void setWhitespace(boolean preserve) {
    _whitespace = preserve;
  }
  
  /* (non-Javadoc)
   * @see org.apache.struts.taglib.bean.WriteTag#getName()
   */
  public String getName() {
    String name = super.getName();
    if (name == null) {
      if (pageContext.findAttribute(Constants.BEAN_KEY) != null) {  // from Struts html:form
        name = Constants.BEAN_KEY;
      }
    }
    return name;
  }
}
