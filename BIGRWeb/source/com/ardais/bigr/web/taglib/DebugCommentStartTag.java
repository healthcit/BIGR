package com.ardais.bigr.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.util.ResponseUtils;

/**
 * Tag handler for the debugCommentStart tag, which generates a debug comment that signifies the 
 * start of some HTML. 
 */
public class DebugCommentStartTag extends BodyTagSupport {
  
  public DebugCommentStartTag() {
    super();
  }

  /* (non-Javadoc)
   * @see javax.servlet.jsp.tagext.Tag#doEndTag()
   */
  public int doEndTag() throws JspException {
    ResponseUtils.write(pageContext, BigrTagUtils.writeDebugCommentStart(pageContext.getPage()));    
    return super.doEndTag();
  }

}
