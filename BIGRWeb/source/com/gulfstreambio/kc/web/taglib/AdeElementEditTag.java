package com.gulfstreambio.kc.web.taglib;

import javax.servlet.jsp.JspException;

import com.gulfstreambio.kc.web.support.WebUtils;

public class AdeElementEditTag extends AdeElementBaseTag {

  public AdeElementEditTag() {
    super();
  }

  protected String getValueIndex() {
    return "0";
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

    return EVAL_PAGE;
  }
  
  public void release() {
    super.release();
  }

}