package com.gulfstreambio.kc.web.taglib;

import javax.servlet.jsp.JspException;

import com.gulfstreambio.kc.web.support.WebUtils;

public class AdeElementSummaryTag extends AdeElementBaseTag {

  private String _valueIndex;
  
  public AdeElementSummaryTag() {
    super();
  }

  protected String getValueIndex() {
    return _valueIndex;
  }

  public void setValueIndex(String index) {
    _valueIndex = index;
  }
  
  public int doStartTag() throws JspException {
    StringBuffer scriptBuf = WebUtils.getJavaScriptBuffer(pageContext);
    createJavascriptObject(scriptBuf);
    
    return EVAL_BODY_INCLUDE;
  }
    
  public void release() {
    super.release();
  }
}
