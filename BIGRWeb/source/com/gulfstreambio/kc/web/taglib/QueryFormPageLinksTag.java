package com.gulfstreambio.kc.web.taglib;

import javax.servlet.jsp.JspException;

import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionCategory;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.PageLink;
import com.gulfstreambio.kc.web.support.RequestParameterConstants;
import com.gulfstreambio.kc.web.support.WebUtils;

/**
 * Tag handler for the queryFormPageLinks tag, which renders the display for the links to
 * the pages that are specified within a query form definition.  The query form definition must be 
 * specified.  The display name of the selected page may also optionally be specified, and is 
 * interpreted as the page that is selected, i.e. the one whose data elements should be displayed. 
 */
public class QueryFormPageLinksTag extends PageLinksBaseTag {

  public QueryFormPageLinksTag() {
    super();
  }

  public int doStartTag() throws JspException {
    // If the tag user specified to use AJAX but did not supply a URL, then throw an exception. 
    if (isAjax() && (getPageLinkBaseUrl() == null)) {
      throw new JspException("The queryFormPageLinks tag was set to use AJAX, but a URL was not set.");
    }
    return super.doStartTag();
  }
  
  protected void setPageLinkAttributes(FormDefinitionCategory category, PageLink page) {
    // Construct the URL to get the contents of the page from the server when the link is clicked.  
    // Only do so if we're using AJAX to get the contents of the page and this is not the selected 
    // page, since we'll render the contents of the selected page.  We'll add the display name of 
    // the page as a query parameter to the base URL specified on the tag.
    if (page.isUseAjax() && !page.isSelected()) {
      String url = getPageLinkBaseUrl();
      StringBuffer newUrl = new StringBuffer(url);
      if (url.indexOf("?") == -1) {
        newUrl.append('?');
      }
      else {
        newUrl.append('&');  
      }
      WebUtils.appendQueryParameter(newUrl, RequestParameterConstants.SELECTED_PAGE, 
          page.getDisplayName());
      page.setUrl(newUrl.toString());
    }
  }

  protected String getPageLinkJsp(int level) {
    return "/query/pageLinks/pageLink" + String.valueOf(level) + ".jsp";
  }

  protected String getPageLinksStartJsp(int level) {
    return "/query/pageLinks/pageLinksStart" + String.valueOf(level) + ".jsp";
  }

  protected String getPageLinksEndJsp(int level) {
    return "/query/pageLinks/pageLinksEnd" + String.valueOf(level) + ".jsp";
  }
  
  protected FormDefinition getFormDefinition() {
    FormContext formContext = getFormContext();
    return formContext.getQueryFormDefinition();
  }
}
