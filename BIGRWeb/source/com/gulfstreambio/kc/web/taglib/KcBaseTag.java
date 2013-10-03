package com.gulfstreambio.kc.web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.FormContextStack;
import com.gulfstreambio.kc.web.support.KcUiContext;
import com.gulfstreambio.kc.web.support.RequestParameterConstants;

public abstract class KcBaseTag extends TagSupport {

  public KcBaseTag() {
    super();
  }
  
  protected FormContextStack getStack() {
    return FormContextStack.getFormContextStack(pageContext.getRequest());
  }
  
  protected FormContext getFormContext() {
    return getStack().peek();
  }
  
  protected FormContext push(FormContext formContext) {
    return getStack().push(formContext);
  }

  protected FormContext pop() {
    return getStack().pop();
  }
  
  protected String getContextPath() {
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    return request.getContextPath();
  }
  
  protected String getAdePopupUrl() {
    KcUiContext kcUiContext = KcUiContext.getKcUiContext(pageContext.getRequest());
    return kcUiContext.getAdePopupUrl();
  }

  protected String getPageLinkBaseUrl() {
    KcUiContext kcUiContext = KcUiContext.getKcUiContext(pageContext.getRequest());
    return kcUiContext.getPageLinkBaseUrl();
  }
  
  protected String getSelectedPage() {
    return pageContext.getRequest().getParameter(RequestParameterConstants.SELECTED_PAGE);
  }
  
}
