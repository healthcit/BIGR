package com.gulfstreambio.kc.web.taglib;

import javax.servlet.jsp.JspException;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionCategory;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.PageLink;
import com.gulfstreambio.kc.web.support.RequestParameterConstants;
import com.gulfstreambio.kc.web.support.WebUtils;

/**
 * Tag handler for the formInstancePageLinks tag, which renders the display for the links to
 * the pages that are specified within a data form definition.  The data form definition on which 
 * the form instance is based must be specified.  The display name of the selected page may also 
 * optionally be specified, and is interpreted as the page that is selected, i.e. the one whose 
 * data elements should be displayed. 
 */
public class FormInstancePageLinksTag extends PageLinksBaseTag {

  public FormInstancePageLinksTag() {
    super();
  }

  public int doStartTag() throws JspException {
    // If the tag user specified to use AJAX but did not supply a URL, then throw an exception. 
    if (isAjax() && (getPageLinkBaseUrl() == null)) {
      throw new JspException("The formInstancePageLinks tag was set to use AJAX, but a URL was not set.");
    }
    return super.doStartTag();
  }

  protected void setPageLinkAttributes(FormDefinitionCategory category, PageLink page) {
    // Construct the URL to get the contents of the page from the server when the link is clicked.  
    // Only do so if we're using AJAX to get the contents of the page and this is not the selected 
    // page, since we'll render the contents of the selected page.
    if (page.isUseAjax() && !page.isSelected()) {
      FormContext formContext = getFormContext();
      FormInstance formInstance = formContext.getFormInstance();

      String baseUrl = getPageLinkBaseUrl();
      StringBuffer url = new StringBuffer(baseUrl);
      if (baseUrl.indexOf('?') == -1) {
        url.append('?');
      }
      else {
        url.append('&');
      }

      String id = getFormDefinition().getFormDefinitionId();
      WebUtils.appendQueryParameter(url, RequestParameterConstants.FORM_DEFINITION_ID, id);

      if (formInstance != null) {
        id = formInstance.getFormInstanceId();
        if (!ApiFunctions.isEmpty(id)) {
          url.append('&');
          WebUtils.appendQueryParameter(url, RequestParameterConstants.FORM_INSTANCE_ID, id);
        }
      }

      id = formContext.getDomainObjectId();
      if (!ApiFunctions.isEmpty(id)) {
        url.append('&');
        WebUtils.appendQueryParameter(url, RequestParameterConstants.DOMAIN_OBJECT_ID, id);
      }

      String type = formContext.getDomainObjectType();
      if (!ApiFunctions.isEmpty(type)) {
        url.append('&');
        WebUtils.appendQueryParameter(url, RequestParameterConstants.DOMAIN_OBJECT_TYPE, type);
      }
      
      url.append('&');
      WebUtils.appendQueryParameter(url, 
          RequestParameterConstants.SELECTED_PAGE, category.getDisplayName());

      page.setUrl(url.toString());
    }

    // Set whether there are required elements in the category.  We know that the cast is safe
    // since this is the form instance tag so the category must be a data form category.
    page.setHasRequired(((DataFormDefinitionCategory) category).isHasRequiredElements());
  }
  
  protected String getPageLinkJsp(int level) {
    return "/data/pageLinks/pageLink" + String.valueOf(level) + ".jsp";
  }
  
  protected String getPageLinksStartJsp(int level) {
    return "/data/pageLinks/pageLinksStart" + String.valueOf(level) + ".jsp";
  }

  protected String getPageLinksEndJsp(int level) {
    return "/data/pageLinks/pageLinksEnd" + String.valueOf(level) + ".jsp";
  }

  protected FormDefinition getFormDefinition() {
    FormContext formContext = getFormContext();
    return formContext.getDataFormDefinition();
  }
}
