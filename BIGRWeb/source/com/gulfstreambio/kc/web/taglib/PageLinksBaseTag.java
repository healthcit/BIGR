package com.gulfstreambio.kc.web.taglib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionCategory;
import com.gulfstreambio.kc.form.def.FormDefinitionElement;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.PageLink;
import com.gulfstreambio.kc.web.support.PageLinkCollection;
import com.gulfstreambio.kc.web.support.RequestParameterConstants;
import com.gulfstreambio.kc.web.support.WebUtils;

/**
 * Base tag handler for tags that render the display of the links to the pages that are specified 
 * within a form definition.
 */
public abstract class PageLinksBaseTag extends KcBaseTag {

  /**
   * An indication of whether AJAX will be used to fetch the contents of the pages.
   */
  private String _useAjax;
  
  public PageLinksBaseTag() {
    super();
  }

  public int doStartTag() throws JspException {

    // Create the collection of UI page links, and process it by including appropriate JSP 
    // fragments to generate HTML for the page links.  If there are no pages (because there are 
    // no categories in the form definition with category type equal to "page"), then do nothing.
    PageLinkCollection pages = createPageLinks();
    if (pages != null) {
      
      // Make sure a page link is selected at all levels.  If a selected page was not specified  
      // in this tag, then select the first page at all levels.  If a page was selected, then the
      // appropriate pages at all levels were marked selected when the pages were created.
      if (ApiFunctions.isEmpty(getSelectedPage())) {
        selectFirstPageLink(pages.getPageLinks());
      }

      try {
        FormContext formContext = getFormContext();
        formContext.setPageLinks(pages);
        push(formContext);

        includeJspFragments(pages.getPageLinks(), 1);
        includeJspFragmentsNextLevel(pages.getPageLinks(), 1);
        
        // Note that we intentionally do not pop the stack here since we want to keep the 
        // collection of page links in the form context in case the code downstream that
        // generates the rest of the page wants to use them.
      }
      catch (Exception e) {
        throw new JspException(e);
      }        
    }
    
    return EVAL_BODY_INCLUDE;
  }

  /**
   * Includes the appropriate JSP fragment for each page link in the specified iterator.  Call
   * the abstract methods to get the JSP fragments to include, leaving the decision to subclasses.
   *  
   * @param  pageLinks  the Iterator of page links at the specified level
   * @param  level  the 0-based level in the hierarchy of page links
   */
  private void includeJspFragments(Iterator pageLinks, int level) throws JspException {
    String jspPath = WebUtils.getContextRelativePath();
    try {
      if (pageLinks.hasNext()) {
        String jsp = getPageLinksStartJsp(level);
        if (!ApiFunctions.isEmpty(jsp)) {
          pageContext.include(jspPath + jsp);          
        }

        while (pageLinks.hasNext()) {
          FormContext formContext = getFormContext();
          formContext.setPageLink((PageLink) pageLinks.next());
          push(formContext);

          jsp = getPageLinkJsp(level);
          if (!ApiFunctions.isEmpty(jsp)) {
            pageContext.include(jspPath + jsp);          
          }
          
          pop();
        }      

        jsp = getPageLinksEndJsp(level);
        if (!ApiFunctions.isEmpty(jsp)) {
          pageContext.include(jspPath + jsp);          
        }
      }
    }
    catch (Exception e) {
      throw new JspException(e);
    }            
  }

  /**
   * Includes the appropriate JSP fragment for each child page link of each page link in the 
   * specified iterator.  Calls {@link #includeJspFragments} to actually include the page link 
   * JSP fragments.  This method and <code>includeJspFragments</code> combine to allow the page 
   * links to be handled breadth-first rather than depth-first, since this is more conducive to
   * generating appropriate HTML.
   *  
   * @param  pageLinks  the Iterator of page links at the specified level
   * @param  level  the 0-based level in the hierarchy of pages
   */
  private void includeJspFragmentsNextLevel(Iterator pageLinks, int level) throws JspException {
    List subPageIterators = new ArrayList();
    
    // First include the JSP fragments for all child pages of each page in the input iterator.
    // Save the iterators of child page links. 
    while (pageLinks.hasNext()) {
      PageLink pageLink = (PageLink) pageLinks.next();
      
      FormContext formContext = getFormContext();
      formContext.setParentPageLink(pageLink);
      push(formContext);

      includeJspFragments(pageLink.getPageLinks(), level + 1);
      subPageIterators.add(pageLink.getPageLinks());
      
      pop();
    }

    // Iterate over the saved iterators, and recursively call this method to include the
    // appropriate JSP fragments for the page links at the next level.
    Iterator i = subPageIterators.iterator();
    while (i.hasNext()) {
      Iterator subPages = (Iterator) i.next(); 
      includeJspFragmentsNextLevel(subPages, level + 1);
    }
  }
  
  /**
   * Creates and returns a <code>PageLinkCollection</code> for the form definition specified as 
   * input to the tag.
   * 
   * @return  The <code>PageLinkCollection</code>, or <code>null</code> if there are no categories
   *          in the form definition with category type equal to "page".
   */
  private PageLinkCollection createPageLinks() throws JspException {
    PageLinkCollection pageLinks = null;

    // Iterates over all of the form definition elements, and for each category, if any,  
    // checks if it's category type is "page".  If so, creates a PageLink and adds it to the
    // PageLinkCollection, and processes its children, creating PageLink instances for all child
    // categories whose category type is "page".
    FormDefinition form = getFormDefinition();
    FormDefinitionElement[] elements = form.getFormElements().getFormElements();
    for (int i = 0; i < elements.length; i++) {
      FormDefinitionElement formElement = elements[i];      
      if (formElement.isCategory()) {
        FormDefinitionCategory category = formElement.getCategory();
        if (category.isPage()) {
          boolean selected = false;
          if (pageLinks == null) {
            pageLinks = new PageLinkCollection();            
          }
          PageLink pageLink = new PageLink();
          pageLink.setUseAjax(isAjax());
          String displayName = category.getDisplayName();
          pageLink.setDisplayName(displayName);
          if (displayName.equals(getSelectedPage())) {
            pageLink.setSelected(true);
            selected = true;
          }          
          setPageLinkAttributes(category, pageLink);
          pageLinks.addPageLink(pageLink);
          
          // Process child categories, and if any child is selected then mark this page selected
          // as well so that there will be one page link selected at all levels.
          boolean childSelected = processChildCategories(category, pageLink, selected);
          if (childSelected) {
            pageLink.setSelected(true);
          }            
        }
      }
    }
    return pageLinks;
  }

  /**
   * Processes the child categories of the specified category, creating <code>PageLink</code> 
   * instances for each child category whose category type is "page" and adding them to the 
   * specified parent <code>PageLink</code>.
   * 
   * @param  category  the parent <code>FormDefinitionCategory</code>
   * @param  parentPage  the parent <code>PageLink</code> 
   * @param  parentSelected  indicates whether the parent page is selected
   * @return  <code>true</code> if one of the child pages corresponds to the selected category;
   *          <code>false</code> otherwise.
   */
  private boolean processChildCategories(FormDefinitionCategory parentCategory, PageLink parentPage,
                                         boolean parentSelected) throws JspException {
    boolean selected = false;

    // Iterates over all of the categories child elements, and for each category, if any,  
    // checks if it's category type is "page".  If so, creates a PageLink and adds it to the
    // parent PageLink, and processes its children, creating PageLink instances for all child
    // categories whose category type is "page".  In addition, if the parent page is selected,
    // then marks the first child page selected, so that a page will be selected at all levels.
    boolean first = true;
    FormDefinitionElement[] elements = parentCategory.getFormElements();
    for (int i = 0; i < elements.length; i++) {
      FormDefinitionElement formElement = elements[i];      
      if (formElement.isCategory()) {
        FormDefinitionCategory category = formElement.getCategory();
        if (category.isPage()) {
          PageLink pageLink = new PageLink();
          pageLink.setUseAjax(isAjax());
          String displayName = category.getDisplayName();
          pageLink.setDisplayName(displayName);
          if (displayName.equals(getSelectedPage()) || (first && parentSelected)) {
            pageLink.setSelected(true);
            selected = true;
          }
          setPageLinkAttributes(category, pageLink);
          parentPage.addPageLink(pageLink);
          first = false;
          
          // Process child categories, and if any child is selected then mark this page selected
          // as well so that there will be one page link selected at all levels.
          boolean childSelected = processChildCategories(category, pageLink, selected);
          if (childSelected) {
            pageLink.setSelected(true);            
          }            
        }
      }      
    }
    return selected;
  }

  /**
   * Selects the first page link at all levels in the page link hierarchy.
   * 
   * @param  pageLinks  an Iterator of page links
   */
  private void selectFirstPageLink(Iterator pageLinks) {
    if (pageLinks.hasNext()) {
      PageLink pageLink = (PageLink) pageLinks.next();
      pageLink.setSelected(true);
      selectFirstPageLink(pageLink.getPageLinks());
    }
  }
  
  protected abstract FormDefinition getFormDefinition();

  protected boolean isAjax() {
    return "true".equals(getUseAjax());    
  }

  private String getUseAjax() {
    return _useAjax;
  }
  
  public void setUseAjax(String useAjax) {
    _useAjax = useAjax;
  }
  
  /**
   * Allows a subclass to set one or more attributes on the specified page link.  This is called
   * during the course of creating the <code>PageLink</code> instances that represent each page 
   * category.  This may be used, for example, to set an attribute containing a URL or JavaScript 
   * function call that is used to handle the user clicking the page link.  The 
   * <code>PageLink</code> instance is ultimately made available to the JSP fragment returned by 
   * {@link #getPageLinkJsp(int) getPageLinkJsp} to form appropriate HTML for the page link, and 
   * thus this method should set whatever attributes are needed by that JSP, if any.
   * 
   * @param category  the category that represents the page
   * @param page  the <code>PageLink</code> created for the page
   * @return  A string containing the URL, function call, etc.
   */
  protected abstract void setPageLinkAttributes(FormDefinitionCategory category, PageLink page);
  
  /**
   * The JSP fragment that is called to handle rendering the link for a page at the specified
   * level.  A {@link PageLink} instance will be made available to the JSP fragment in 
   * the form context on the top of the form context stack.
   * 
   * @param level the 0-based level of the page within the hierarchy of pages
   * @return  A string containing the context relative JSP.  This must start with a '/' and
   *          include any file extension, such as ".jsp".
   */
  protected abstract String getPageLinkJsp(int level);
  
  /**
   * The JSP fragment that is called to handle rendering the start of a set of page links at the 
   * specified level.  A {@link PageLinkCollection} instance that contains the entire 
   * hierarchy of pages will be made available to the JSP fragment in the form context on the 
   * top of the form context stack.
   * 
   * @param level the 0-based level within the hierarchy of the set of pages
   * @return  A string containing the context relative JSP.  This must start with a '/' and
   *          include any file extension, such as ".jsp".
   */
  protected abstract String getPageLinksStartJsp(int level);

  /**
   * The JSP fragment that is called to handle rendering the end of a set of page links at the 
   * specified level.  A {@link PageLinkCollection} instance that contains the entire 
   * hierarchy of pages will be made available to the JSP fragment in the form context on the 
   * top of the form context stack.
   * 
   * @param level the 0-based level within the hierarchy of the set of pages
   * @return  A string containing the context relative JSP.  This must start with a '/' and
   *          include any file extension, such as ".jsp".
   */
  protected abstract String getPageLinksEndJsp(int level);
  
}
