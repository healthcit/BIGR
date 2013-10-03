package com.gulfstreambio.kc.web.taglib; 

import javax.servlet.jsp.JspException;

import com.gulfstreambio.kc.web.support.WebUtils;

/**
 * Tag handler for the dataElementEdit tag, which serves as a container tag to other 
 * KnowledgeCapture tags that render the user interface for editing a single data element.  
 * This tag generates the container and appropriate JavaScript for the data element in the form 
 * context on the top of the form context stack.
 */
public class DataElementEditTag extends DataElementBaseTag {

  public DataElementEditTag() {
    super();
  }

  public void release() {
    super.release();
  }

  public int doStartTag() throws JspException {
    StringBuffer buf = new StringBuffer(256);
    createContainerStart(buf);
    WebUtils.tagWrite(pageContext, buf.toString());    

    StringBuffer scriptBuf = WebUtils.getJavaScriptBuffer(pageContext);
    createJavascriptObject(scriptBuf);
    
    return EVAL_BODY_INCLUDE;
  }
  
  public int doEndTag() throws JspException {
    StringBuffer buf = new StringBuffer(256);
    createContainerEnd(buf);
    WebUtils.tagWrite(pageContext, buf.toString());    

    StringBuffer scriptBuf = WebUtils.getJavaScriptBuffer(pageContext);
    setJavascriptHtmlIds(scriptBuf);
    registerJavascriptEventHandler(scriptBuf);
    WebUtils.writeJavaScriptBuffer(pageContext);

    return EVAL_PAGE;
  }

}