package com.gulfstreambio.kc.web.taglib;

import java.util.Iterator;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

import org.json.JSONException;
import org.json.JSONObject;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.PageLink;
import com.gulfstreambio.kc.web.support.PageLinkCollection;
import com.gulfstreambio.kc.web.support.WebUtils;

/**
 * Tag handler for the formInstancePagesEdit tag, which renders the display for editing a page
 * within a form instance.  The form definition on which the form instance is based must be 
 * specified in the form context.  The form instance itself can optionally be specified in the
 * form context - it should be specified when editing an existing form instance, and should not be 
 * specified when creating a new form instance.
 * <p>
 * The selected page name may also optionally be specified.  If specified, it must be 
 * the name of a category whose category type is "page", and it indicates that only the data 
 * elements from that page should be shown.  If the selected page is not specified, and the
 * form definition contains pages, then the first page at the lowest level of the page hierarchy
 * is shown by default.  If the form definition does not contain pages, then the entire form
 * instance is displayed.
 */
public class FormInstancePagesEditTag extends KcBaseTag {

  private String _useAjax;
  
  public FormInstancePagesEditTag() {
    super();
  }

  private DataFormDefinition getFormDefinition() {
    FormContext formContext = getFormContext();
    return formContext.getDataFormDefinition();
  }
  
  private String getUseAjax() {
    return _useAjax;
  }
  
  public void setUseAjax(String useAjax) {
    _useAjax = useAjax;
  }
  
  /**
   * Loops through all categories and data elements in the specified form definition 
   * and includes appropriate JSP fragments for generating the HTML to render each  
   * category as a header and the appropriate edit inputs for each data element.
   */
  public int doStartTag() throws JspException {
    // Create the JavaScript form instance.
    createJavascriptFormInstance();

    FormContext formContext = getFormContext();
    PageLinkCollection pageLinks = formContext.getPageLinks();
    if (pageLinks != null) {
      generatePages(pageLinks.getPageLinks());
    }
    else {
      try {
        String jspPath = WebUtils.getContextRelativePath();
        pageContext.include(jspPath + "/data/page/pageContainerStart.jsp");
        FormInstancePageEditHelper helper = 
          new FormInstancePageEditHelper(formContext.getDataFormDefinition(), null, pageContext);
        helper.generatePage();
        pageContext.include(jspPath + "/data/page/pageContainerEnd.jsp");
      }
      catch (Exception e) {
        throw new JspException(e);
      }                        
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

            pageContext.include(jspPath + "/data/page/pageContainerStart.jsp");

            if (!usingAjax || page.isSelected()) {
              FormInstancePageEditHelper helper = 
                new FormInstancePageEditHelper(getFormDefinition(), page.getDisplayName(), 
                    pageContext);
              helper.generatePage();
            }

            pageContext.include(jspPath + "/data/page/pageContainerEnd.jsp");
            
            pop();
          }
          catch (Exception e) {
            throw new JspException(e);
          }                        
        }

        // If the page link does have children page links, then the HTML that should be made 
        // visible when the link is clicked is generated elsewhere, by the formInstancePageLinks 
        // tag.  In this case, recursively call this method to generate empty pages for its child
        // page links.
        else {
          generatePages(page.getPageLinks());
        }
      }    
    }
  }

  private void createJavascriptFormInstance() throws JspException {
    FormContext formContext = getFormContext();
    DataFormDefinition formDef = getFormDefinition();
    FormInstance form = formContext.getFormInstance();
    String domainObjectId = formContext.getDomainObjectId();
    if (ApiFunctions.isEmpty(domainObjectId) && form != null) {
      domainObjectId = form.getDomainObjectId();
    }
    String domainObjectType = formContext.getDomainObjectType();
    if (ApiFunctions.isEmpty(domainObjectType) && form != null) {
      domainObjectType = form.getDomainObjectType();
    }
    
    JSONObject initializer = new JSONObject();
    try {
      initializer.putOpt("id", formContext.getJavascriptObjectId());      
      initializer.putOpt("formDefinitionId", formDef.getFormDefinitionId());
      if (form != null) {
        initializer.putOpt("formInstanceId", form.getFormInstanceId());
      }
      initializer.putOpt("domainObjectId", domainObjectId);
      initializer.putOpt("domainObjectType", domainObjectType);
    }
    catch (JSONException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    StringBuffer buf = WebUtils.getJavaScriptBuffer(pageContext);
    buf.append("GSBIO.kc.data.FormInstances.createFormInstance(");
    buf.append(initializer.toString());
    buf.append(");");
    WebUtils.writeJavaScriptBuffer(pageContext);
  }
  
}
