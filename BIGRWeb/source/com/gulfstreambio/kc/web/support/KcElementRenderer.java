package com.gulfstreambio.kc.web.support;

import javax.servlet.jsp.PageContext;

import com.ardais.bigr.api.ApiException;

public class KcElementRenderer implements ElementRenderer {

  KcElementRenderer() {
    super();
  }

  public void renderForEdit(String elementId, FormContext formContext, PageContext pageContext) {
    try {
      pageContext.include(WebUtils.getContextRelativePath() + "/data/dataElement/dataElement.jsp");
    }
    catch (Exception e) {
      throw new ApiException(e);
    }            
  }

  public void renderForSummary(String elementId, FormContext formContext, PageContext pageContext) {
    try {
      pageContext.include(WebUtils.getContextRelativePath() + "/data/dataElement/dataElementSummary.jsp");
    }
    catch (Exception e) {
      throw new ApiException(e);
    }            
  }
}
