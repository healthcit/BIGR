package com.gulfstreambio.kc.web.taglib;

import javax.servlet.jsp.JspException;

import com.gulfstreambio.kc.web.support.FormContext;

/**
 * Tag handler for the formInstancePageEdit tag, which renders the display for editing the
 * specified page of the specified data form definition, or the entire data form definition if it 
 * does not have pages.
 */
public class FormInstancePageEditTag extends KcBaseTag {

  public FormInstancePageEditTag() {
    super();
  }

  public int doStartTag() throws JspException {
    FormContext formContext = getFormContext();
    FormInstancePageEditHelper helper = 
      new FormInstancePageEditHelper(formContext.getDataFormDefinition(), getSelectedPage(), 
          pageContext);
    helper.generatePage();
    return EVAL_BODY_INCLUDE;
  }

}
