package com.gulfstreambio.kc.web.taglib;

import java.util.Iterator;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.PageLink;
import com.gulfstreambio.kc.web.support.PageLinkCollection;
import com.gulfstreambio.kc.web.support.WebUtils;

public class QueryFormPagesTag extends KcBaseTag {

  private String _useAjax;
  
  public QueryFormPagesTag() {
    super();
  }

  private QueryFormDefinition getFormDefinition() {
    FormContext formContext = getFormContext();
    return formContext.getQueryFormDefinition();
  }
  
  private String getUseAjax() {
    return _useAjax;
  }
  
  public void setUseAjax(String useAjax) {
    _useAjax = useAjax;
  }
  
  public int doStartTag() throws JspException {
    PageLinkCollection pageLinks = getFormContext().getPageLinks();
    if (pageLinks != null) {
      generatePages(pageLinks.getPageLinks());
    }
    else {
      QueryFormPageHelper helper = 
        new QueryFormPageHelper(getFormDefinition(), null, pageContext);
      helper.generatePage();
    }
    return EVAL_BODY_INCLUDE;
  }
  
  private void generatePages(Iterator pageLinks) throws JspException {
    if (pageLinks != null) {
      ServletRequest request = pageContext.getRequest();
      String jspPath = WebUtils.getContextRelativePath();
      boolean usingAjax = "true".equals(getUseAjax());

      while (pageLinks.hasNext()) {
        PageLink page = (PageLink) pageLinks.next();

        // For each page link that does not have children create the contents of the page.
        // If we're using AJAX to lazily fetch pages, only generate the entire page for
        // the selected page, and generate an empty page for all other pages, to be filled in
        // by an AJAX request.  If we're not using AJAX, then generate all of the pages.
        if (page.getCount() == 0) {
          try {
            FormContext formContext = getFormContext();
            formContext.setPageLink(page);
            push(formContext);

            pageContext.include(jspPath + "/query/page/pageContainerStart.jsp");

            if (!usingAjax || page.isSelected()) {
              QueryFormPageHelper helper = 
                new QueryFormPageHelper(getFormDefinition(), page.getDisplayName(), pageContext);
              helper.generatePage();
            }

            pageContext.include(jspPath + "/query/page/pageContainerEnd.jsp");
            
            pop();
          }
          catch (Exception e) {
            throw new JspException(e);
          }                        
        }

        // If the page link does have children page links, then the HTML that should be made 
        // visible when the link is clicked is generated elsewhere, by the queryFormPageLinks tag.
        // In this case, recursively call this method to generate empty pages for its child
        // page links.
        else {
          generatePages(page.getPageLinks());
        }
      }    
    }
  }
}
