package com.gulfstreambio.kc.web.taglib;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.form.DataElement;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement;
import com.gulfstreambio.kc.web.support.ElementRenderer;
import com.gulfstreambio.kc.web.support.ElementRendererFactory;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.WebUtils;

/**
 * Tag handler for the formInstancePageSummary tag, which renders the display for the summary of
 * a page within a form instance.  The form instance must be specified, along with the form 
 * definition on which the form instance is based.
 * <p>
 * The selected page name may also optionally be specified.  If specified, it must be 
 * the name of a category whose category type is "page", and it indicates that only the data 
 * elements from that page should be shown.  If the selected page is not specified, and the
 * form definition contains pages, then the first page at the lowest level of the page hierarchy
 * is shown by default.  If the form definition does not contain pages, then the entire form
 * instance is displayed.
 */
public class FormInstancePageSummaryTag extends KcBaseTag {

  public FormInstancePageSummaryTag() {
    super();
  }

  /**
   * Loops through all categories and data elements in the specified form definition 
   * and includes appropriate JSP fragments for generating the HTML to render each  
   * category as a header and the appropriate summary information for each data element.
   */
  public int doStartTag() throws JspException {
    ServletRequest request = pageContext.getRequest();
    String jspPath = WebUtils.getContextRelativePath();
    
    // Get the current form context and get the form definition from it.
    FormContext formContext = getFormContext();
    DataFormDefinition formDef = formContext.getDataFormDefinition();
    
    // If the form definition has pages, then we have to determine which page to display.  If a
    // page was not explicitly selected at the lowest level in the hierarchy, then we want to 
    // select the first page of the selected page at the lowest level.  For example, if the user 
    // selects a top-level page and it has sub-pages, then we need to select one of the sub-pages, 
    // so we'll select the first one by default.  On the other hand, the user may have explicitly 
    // selected the third sub-page of a top-level page, where that third sub-page has no child 
    // pages, so the third sub-page will be the selected page.  Of course none of this matters 
    // if the form definition does not contain pages.
    DataFormDefinitionElement[] formElementDefs = null;
    if (formDef.isHasPages()) {
      String selectedPage = getSelectedPage();

      // Get the category definition for the selected page.  If no page was selected, 
      // start with the first top-level page.
      DataFormDefinitionCategory category = ApiFunctions.isEmpty(selectedPage) 
        ? getFirstCategory()
        : formDef.getDataCategory(selectedPage);
      if (category == null) {
        throw new JspException("In formInstancePageSummary JSP tag: could not find category " + selectedPage);
      }

      // If the selected category has child categories that are pages, then select the first
      // child category, walking the entire descendant category tree.
      category = getLowestLevelPageCategory(category);

      // Set the category to display in the form context.
      formContext.setDataFormDefinitionCategory(category);
      
      // Get the form elements to display.
      formElementDefs = category.getDataFormElements();
    }
    else {
      // If the form we are displaying does not have categories, then be sure to set the
      // category in the form context to null to indicate that fact.
      formContext.setDataFormDefinitionCategory(null);

      // Get the form elements to display.
      formElementDefs = formDef.getDataFormElements().getDataFormElements();
    }

    try {
      push(formContext);
      pageContext.include(jspPath + "/data/page/summaryPageStart.jsp");
      generateSummaryHtml(formElementDefs);      
      pageContext.include(jspPath + "/data/page/summaryPageEnd.jsp");
    }
    catch (Exception e) {
      throw new JspException(e);
    }        
    finally {
      pop();
    }        

    return EVAL_BODY_INCLUDE;
  }

  private void generateSummaryHtml(DataFormDefinitionElement[] formElementDefinitions) 
    throws JspException {
    String jspPath = WebUtils.getContextRelativePath();
    FormContext formContext = getFormContext();
    FormInstance form = formContext.getFormInstance();

    for (int i = 0; i < formElementDefinitions.length; i++) {
      DataFormDefinitionElement formElementDef = formElementDefinitions[i];

      if (formElementDef.isCategory()) {
        DataFormDefinitionCategory category = formElementDef.getDataCategory();
        try {
          formContext.setDataFormDefinitionCategory(category);
          push(formContext);

          pageContext.include(jspPath + "/data/heading/summaryHeadingStart.jsp");
          generateSummaryHtml(category.getDataFormElements());
          pageContext.include(jspPath + "/data/heading/summaryHeadingEnd.jsp");
        }
        catch (Exception e) {
          throw new JspException(e);
        }        
        finally {
          pop();
        }        
      }

      else if (formElementDef.isDataElement()) {
        DataFormDefinitionDataElement definition = formElementDef.getDataDataElement();
        String cui = definition.getCui();
        DataElement instance = null;          
        if (form != null) {
          instance = form.getDataElement(definition.getCui());          
        }
        formContext.setDataFormDefinitionDataElement(definition);
        formContext.setDataElement(instance);
        push(formContext);

        ElementRendererFactory factory = WebUtils.getKcElementRendererFactory();
        ElementRenderer renderer = factory.getElementRenderer(cui, formContext);
        renderer.renderForSummary(cui, formContext, pageContext);
        pop();
      }

      else if (formElementDef.isHostElement()) {
        DataFormDefinitionHostElement definition = formElementDef.getDataHostElement();
        String hostId = definition.getHostId();
        formContext.setDataFormDefinitionHostElement(definition);
        push(formContext);

        ElementRendererFactory factory = WebUtils.getHostElementRendererFactory();
        ElementRenderer renderer = factory.getElementRenderer(hostId, formContext);
        renderer.renderForSummary(hostId, formContext, pageContext);
        pop();
      }
    }
  }
  
  private DataFormDefinitionCategory getFirstCategory() {
    FormContext formContext = getFormContext();
    DataFormDefinition formDef = formContext.getDataFormDefinition();
    DataFormDefinitionElement[] elements = formDef.getDataFormElements().getDataFormElements();
    for (int i = 0; i < elements.length; i++) {
      DataFormDefinitionElement formElementDef = elements[i];
      if (formElementDef.isCategory()) {
        DataFormDefinitionCategory category = formElementDef.getDataCategory();
        if (category.isPage()) {
          return category;
        }
      }
    }
    return null;
  }

  private DataFormDefinitionCategory getLowestLevelPageCategory(DataFormDefinitionCategory category) {
    DataFormDefinitionCategory lowestLevelCategory = category;
    DataFormDefinitionElement[] elements = category.getDataFormElements();
    for (int i = 0; i < elements.length; i++) {
      DataFormDefinitionElement formElementDef = elements[i];
      if (formElementDef.isCategory()) {
        DataFormDefinitionCategory subCategory = formElementDef.getDataCategory();
        if (subCategory.isPage()) {
          return getLowestLevelPageCategory(subCategory);
        }
      }
    }
    return lowestLevelCategory;
  }

}
