package com.gulfstreambio.kc.web.taglib;

import javax.servlet.jsp.JspException;

import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.web.support.FormContext;

/**
 * Tag handler for the queryFormPage JSP tag, which renders the specified page of the specified
 * query form definition, or the entire query form definition if it does not have pages.
 */
public class QueryFormPageTag extends KcBaseTag {

  public QueryFormPageTag() {
    super();
  }

  public int doStartTag() throws JspException {
    FormContext formContext = getFormContext();
    QueryFormPageHelper helper = 
      new QueryFormPageHelper(getFormDefinition(), getSelectedPage(), pageContext);
    helper.generatePage();
    return EVAL_BODY_INCLUDE;
  }
  
  private QueryFormDefinition getFormDefinition() {
    FormContext formContext = getFormContext();
    return formContext.getQueryFormDefinition();
  }
  
}
