package com.gulfstreambio.kc.web.taglib;

import javax.servlet.jsp.JspException;

import com.gulfstreambio.kc.web.support.WebUtils;

/**
 * Tag handler for the dataElementEdit tag, which serves as a container tag to other 
 * KnowledgeCapture tags that render the user interface for the summary view a single data element.  
 * This tag generates the container for the data element in the form context on the top of the 
 * form context stack.
 */
public class DataElementSummaryTag extends DataElementBaseTag {

  public DataElementSummaryTag() {
    super();
  }

  public void release() {
    super.release();
  }

  public int doStartTag() throws JspException {
    StringBuffer buf = new StringBuffer(256);
    createContainerStart(buf);
    WebUtils.tagWrite(pageContext, buf.toString());    

    return EVAL_BODY_INCLUDE;
  }
  
  public int doEndTag() throws JspException {
    StringBuffer buf = new StringBuffer(256);
    createContainerEnd(buf);
    WebUtils.tagWrite(pageContext, buf.toString());    

    return EVAL_PAGE;
  }
}
