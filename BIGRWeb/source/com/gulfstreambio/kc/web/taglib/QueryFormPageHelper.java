package com.gulfstreambio.kc.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionCategory;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionElements;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.FormContextStack;
import com.gulfstreambio.kc.web.support.WebUtils;

/**
 * Generates a page of a query form definition, or the entire query form definition if it does
 * not have pages.  This is only intended to be used by KnowledgeCapture JSP tags such as 
 * {@link QueryFormPageTag} and {@link QueryFormPagesTag}.  
 */
class QueryFormPageHelper {

  /**
   * The query form definition to be used to render the page.
   */
  private QueryFormDefinition _form;
  
  /**
   * The query form definition category corresponding to the page to be rendered.
   */
  private QueryFormDefinitionCategory _category;

  /**
   * The display name of the page within the query form definition, if any.
   */
  private String _pageDisplayName;
  
  /**
   * The JSP's page context.
   */
  private PageContext _pageContext;

  /**
   * Creates a new <code>QueryFormPageHelper</code> for the specified query form definition and
   * page within the query form definition.
   * 
   * @param  form  the query form definition
   * @param  page  the display name of the page within the query form definition 
   * @param  pageContext  the JSP page context
   * @throws JspException if the specified query form definition has pages and a page was not 
   * specified, or the specified page is not found in the form definition or is not a leaf page.
   */
  QueryFormPageHelper(QueryFormDefinition form, String page, PageContext pageContext)
      throws JspException {
    super();
    
    // If the specified query form definition has pages and a page was not specified
    // then throw an exception.  Otherwise find the category that corresponds to the page
    // display name.  If it does not exists or is not a leaf category, then throw an exception, 
    // otherwise save it for later processing.
    if (form.isHasPages()) {
      if (ApiFunctions.isEmpty(page)) {
        throw new JspException("Attempt to create a QueryFormPageHelper without specifying a page for a query form with pages.");        
      }
      else {
        QueryFormDefinitionCategory category = getCategoryForPage(form, page);
        if (category == null) {
          throw new JspException("Attempt to create a QueryFormPageHelper with a page that was not found in the query form (" + page + ").");                  
        }
        else {
          QueryFormDefinitionElement[] elements = category.getQueryFormElements();
          for (int i = 0; i < elements.length; i++) {
            QueryFormDefinitionElement element = elements[i];
            if (element.isCategory()) {
              QueryFormDefinitionCategory subcategory = element.getQueryCategory(); 
              if (subcategory.isPage()) {
                throw new JspException("Attempt to create a QueryFormPageHelper with a page that is not a leaf page in the query form.");                  
              }
            }
          }
        }
        setCategory(category);
      }
    }
    
    setFormDefinition(form);
    setPage(page);
    setPageContext(pageContext);
  }
  
  private QueryFormDefinition getFormDefinition() {
    return _form;
  }
  
  private void setFormDefinition(QueryFormDefinition form) {
    _form = form;
  }
  
  private String getPage() {
    return _pageDisplayName;
  }
  
  private void setPage(String page) {
    _pageDisplayName = page;
  }
  
  private QueryFormDefinitionCategory getCategory() {
    return _category;
  }
  
  private void setCategory(QueryFormDefinitionCategory category) {
    _category = category;
  }
  
  private PageContext getPageContext() {
    return _pageContext;
  }

  private void setPageContext(PageContext pageContext) {
    _pageContext = pageContext;
  }
  
  private QueryFormDefinitionCategory getCategoryForPage(QueryFormDefinition form, String page) {
    QueryFormDefinitionElements elements = form.getQueryFormElements();
    return getCategoryForPage(elements.getQueryFormElements(), page);
  }

  private QueryFormDefinitionCategory getCategoryForPage(QueryFormDefinitionElement[] elements,
                                                         String page) {
    for (int i = 0; i < elements.length; i++) {
      QueryFormDefinitionElement element = elements[i];
      if (element.isCategory()) {
        QueryFormDefinitionCategory category = element.getQueryCategory(); 
        if (category.getDisplayName().equals(page)) {
          return category;
        }
        else {
          category = getCategoryForPage(category.getQueryFormElements(), page);
          if (category != null) {
            return category;
          }
        }
      }
    }
    return null;
  }
  
  void generatePage() throws JspException {
    String jspPath = WebUtils.getContextRelativePath();
    PageContext pageContext = getPageContext(); 
    FormContextStack stack = FormContextStack.getFormContextStack(pageContext.getRequest());
    FormContext formContext = stack.peek();

    // Get the category corresponding to the page to generate and set it in the form context,
    // even if no page was specified and the category is null.
    QueryFormDefinitionCategory category = getCategory();
    formContext.setQueryFormDefinitionCategory(category);

    // Generate the page of elements, either using the elements from the category if specified 
    // or the whole form.
    try {
      stack.push(formContext);

      pageContext.include(jspPath + "/query/page/pageStart.jsp");

      QueryFormDefinitionElement[] elements = (category == null) 
        ? getFormDefinition().getQueryFormElements().getQueryFormElements() 
        : category.getQueryFormElements();      
      generatePage(elements, stack);      

      pageContext.include(jspPath + "/query/page/pageEnd.jsp");
      
      // Write all JavaScript that may have been buffered.
      WebUtils.writeJavaScriptBuffer(pageContext);
    }
    catch (Exception e) {
      throw new JspException(e);
    }        
    finally {
      stack.pop();
    }
  }
  
  private void generatePage(QueryFormDefinitionElement[] elements, FormContextStack stack) 
    throws JspException {
    String jspPath = WebUtils.getContextRelativePath();
    PageContext pageContext = getPageContext(); 
    FormContext formContext = stack.peek();

    try {
      for (int i = 0; i < elements.length; i++) {
        QueryFormDefinitionElement element = elements[i];

        // If the element is a category it must be a header since we verified that we are
        // generating elements on a leaf page.  Therefore recursively call this method on the
        // header category's elements, sandwiched between including the heading start and end
        // JSPs.
        if (element.isCategory()) {
          QueryFormDefinitionCategory category = element.getQueryCategory();
          formContext.setQueryFormDefinitionCategory(category);
          stack.push(formContext);

          pageContext.include(jspPath + "/query/heading/headingStart.jsp");
          generatePage(category.getQueryFormElements(), stack);
          pageContext.include(jspPath + "/query/heading/headingEnd.jsp");
          stack.pop();
        }

        // If the element is a data element then include the JSP to render it.
        else if (element.isDataElement()) {
          QueryFormDefinitionDataElement dataElement = element.getQueryDataElement();
          formContext.setQueryFormDefinitionDataElement(dataElement);
          stack.push(formContext);
          pageContext.include(jspPath + "/query/queryElement/queryElement.jsp");
          stack.pop();
        }

        else {
          throw new JspException("Unrecognized element.");
        }
      }
    }
    catch (Exception e) {
      throw new JspException(e);
    }
  }
  
}
