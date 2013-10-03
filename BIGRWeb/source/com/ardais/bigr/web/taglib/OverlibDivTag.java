package com.ardais.bigr.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.ResponseUtils;

/**
 * Tag handler for the <code>overlibDiv</code> tag.  This tag
 * writes the DIV required by the overlib library that we use for tooltips
 * if it hasn't already be written by this tag to the same response page.
 * More specifically, we set a marker in REQUEST_SCOPE the first time
 * and don't write out anything if that marker is already there.
 */
public class OverlibDivTag extends TagSupport {

  /**
   * Creates a new <code>OverlibDivTag</code>.
   */
  public OverlibDivTag() {
    super();
  }

  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
  }

  /**
   */
  public int doStartTag() throws JspException {
    String testAttrName = "emitOnceWroteOverlibDiv";
    if (pageContext.getAttribute(testAttrName, PageContext.REQUEST_SCOPE) == null) {
      pageContext.setAttribute(testAttrName, "true", PageContext.REQUEST_SCOPE);
      ResponseUtils.write(
        pageContext,
        "<div id=\"overDiv\" style=\"position: absolute; visibility: hidden; z-index: 1000;\"></div>");
    }
    return EVAL_BODY_INCLUDE;
  }

}
