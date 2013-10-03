package com.ardais.bigr.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.util.ResponseUtils;

/**
 * Tag handler for the debugCommentEnd tag, which generates a debug comment that signifies the 
 * end of some HTML. 
 */
public class DebugCommentEndTag extends BodyTagSupport {

  public DebugCommentEndTag() {
    super();
  }

  /* (non-Javadoc)
   * @see javax.servlet.jsp.tagext.Tag#doEndTag()
   */
  public int doEndTag() throws JspException {
    ResponseUtils.write(pageContext, BigrTagUtils.writeDebugCommentEnd(pageContext.getPage()));    
    return super.doEndTag();
  }

}
