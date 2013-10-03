package com.gulfstreambio.kc.web.taglib;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.gulfstreambio.kc.form.DataElement;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement;
import com.gulfstreambio.kc.form.def.data.calculation.Calculation;
import com.gulfstreambio.kc.form.def.data.calculation.DataElementDefinitionReference;
import com.gulfstreambio.kc.form.def.data.calculation.Literal;
import com.gulfstreambio.kc.form.def.data.calculation.Operand;
import com.gulfstreambio.kc.web.support.DataFormDataElementContext;
import com.gulfstreambio.kc.web.support.ElementRenderer;
import com.gulfstreambio.kc.web.support.ElementRendererFactory;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.FormContextStack;
import com.gulfstreambio.kc.web.support.WebUtils;

/**
 * Generates an edit page of a data form instance, or the entire data form instance editor if it
 * does not have pages.  This is only intended to be used by KnowledgeCapture JSP tags such as 
 * {@link FormInstancePagesEditTag} and {@link FormInstancePageEditTag}.  
 */
class FormInstancePageEditHelper {

  /**
   * The data form definition to be used to render the page.
   */
  private DataFormDefinition _form;
  
  /**
   * The data form definition category corresponding to the page to be rendered.
   */
  private DataFormDefinitionCategory _category;

  /**
   * The display name of the page within the data form definition, if any.
   */
  private String _pageDisplayName;

  /**
   * The JSP's page context.
   */
  private PageContext _pageContext;

  FormInstancePageEditHelper(DataFormDefinition form, String page, PageContext pageContext) 
    throws JspException {
    super();

    // If the specified data form definition has pages and a page was not specified
    // then throw an exception.  Otherwise find the category that corresponds to the page
    // display name.  If it does not exists or is not a leaf category, then throw an exception, 
    // otherwise save it for later processing.
    if (form.isHasPages()) {
      if (ApiFunctions.isEmpty(page)) {
        throw new JspException("Attempt to create a FormInstancePageEditHelper without specifying a page for a data form with pages.");        
      }
      else {
        DataFormDefinitionCategory category = form.getDataCategory(page);
        if (category == null) {
          throw new JspException("Attempt to create a FormInstancePageEditHelper with a page that was not found in the data form (" + page + ").");                  
        }
        else {
          DataFormDefinitionElement[] elements = category.getDataFormElements();
          for (int i = 0; i < elements.length; i++) {
            DataFormDefinitionElement element = elements[i];
            if (element.isCategory()) {
              DataFormDefinitionCategory subcategory = element.getDataCategory(); 
              if (subcategory.isPage()) {
                throw new JspException("Attempt to create a FormInstancePageEditHelper with a page that is not a leaf page in the data form.");                  
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

  private PageContext getPageContext() {
    return _pageContext;
  }

  private void setPageContext(PageContext pageContext) {
    _pageContext = pageContext;
  }
  
  private DataFormDefinition getFormDefinition() {
    return _form;
  }
  
  private void setFormDefinition(DataFormDefinition form) {
    _form = form;
  }
  
  private String getPage() {
    return _pageDisplayName;
  }
  
  private void setPage(String page) {
    _pageDisplayName = page;
  }
  
  private DataFormDefinitionCategory getCategory() {
    return _category;
  }
  
  private void setCategory(DataFormDefinitionCategory category) {
    _category = category;
  }
  
  void generatePage() throws JspException {
    String jspPath = WebUtils.getContextRelativePath();
    PageContext pageContext = getPageContext(); 
    FormContextStack stack = FormContextStack.getFormContextStack(pageContext.getRequest());
    FormContext formContext = stack.peek();

    // Get the category corresponding to the page to generate and set it in the form context,
    // even if no page was specified and the category is null.
    DataFormDefinitionCategory category = getCategory();
    formContext.setDataFormDefinitionCategory(category);

    // Generate the page of elements, either using the elements from the category if specified 
    // or the whole form.
    try {
      stack.push(formContext);

      pageContext.include(jspPath + "/data/page/editPageStart.jsp");

      DataFormDefinitionElement[] elements = (category == null) 
        ? getFormDefinition().getDataFormElements().getDataFormElements() 
        : category.getDataFormElements();      
      generatePage(elements, stack);      

      pageContext.include(jspPath + "/data/page/editPageEnd.jsp");
      
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
  
  void generatePage(DataFormDefinitionElement[] formElementDefinitions, FormContextStack stack)
    throws JspException {
    PageContext pageContext = getPageContext(); 
    ServletRequest request = pageContext.getRequest();
    String jspPath = WebUtils.getContextRelativePath();
    FormContext formContext = stack.peek();
    Map renderedDataElementContexts = new HashMap();

    for (int i = 0; i < formElementDefinitions.length; i++) {
      DataFormDefinitionElement formElementDef = formElementDefinitions[i];
  
      if (formElementDef.isCategory()) {
        DataFormDefinitionCategory category = formElementDef.getDataCategory();
        try {
          formContext.setDataFormDefinitionCategory(category);
          stack.push(formContext);
  
          pageContext.include(jspPath + "/data/heading/editHeadingStart.jsp");
          generatePage(category.getDataFormElements(), stack);
          pageContext.include(jspPath + "/data/heading/editHeadingEnd.jsp");
        }
        catch (Exception e) {
          throw new JspException(e);
        }        
        finally {
          stack.pop();
        }        
      }

      else if (formElementDef.isDataElement()) {
        DataFormDefinitionDataElement definition = formElementDef.getDataDataElement();
        String cui = definition.getCui();
        FormInstance form = formContext.getFormInstance();
        DataElement instance = null;          
        if (form != null) {
          instance = form.getDataElement(cui);
        }
        formContext.setDataFormDefinitionDataElement(definition);
        formContext.setDataElement(instance);
        FormContext copiedFormContext = stack.push(formContext);
  
        ElementRendererFactory factory = WebUtils.getKcElementRendererFactory();
        ElementRenderer renderer = factory.getElementRenderer(cui, formContext);
        renderer.renderForEdit(cui, formContext, pageContext);
        renderedDataElementContexts.put(copiedFormContext.getDataFormDataElementContext().getCui(),
            copiedFormContext.getDataFormDataElementContext());
        stack.pop();
      }
  
      else if (formElementDef.isHostElement()) {
        DataFormDefinitionHostElement definition = formElementDef.getDataHostElement();
        String hostId = definition.getHostId();
        formContext.setDataFormDefinitionHostElement(definition);
        stack.push(formContext);
  
        ElementRendererFactory factory = WebUtils.getHostElementRendererFactory();
        ElementRenderer renderer = factory.getElementRenderer(hostId, formContext);
        renderer.renderForEdit(hostId, formContext, pageContext);
        stack.pop();
      }
    }
    //iterate over the rendered elements, generating the appropriate javascript method to calculate
    //the value for any element that is calculated as well as to register listeners on the fields 
    //that serve as inputs into that calculation so the method can be called when there is a change
    //to any of those input values.
    //This has to be done after all elements are rendered, because a calculation for one data 
    //element can (and likely will) refer to one or more other data elements on the page, and there 
    //is no guarantee that the elements referred to in the calculation will have been rendered 
    //before the calculated data element (and thus their ids will not have been assigned yet)
    Iterator keyIterator = renderedDataElementContexts.keySet().iterator();
    while (keyIterator.hasNext()) {
      DataFormDataElementContext dataElementContext = (DataFormDataElementContext)renderedDataElementContexts.get(keyIterator.next());
      if (dataElementContext.isCalculated()) {
        renderJavaScriptForCalculation(renderedDataElementContexts, dataElementContext);
      }
    }
    //SWP-1081
    //iterate over the rendered elements again, calling out to the population methods we just 
    //created for any data elements computed from purely literals. These calls must be made after 
    //all of the calculcation methods are rendered and all listeners registered, so that is any of
    //the fields we're populated are inputs to other fields then those fields will be updated
    keyIterator = renderedDataElementContexts.keySet().iterator();
    while (keyIterator.hasNext()) {
      DataFormDataElementContext dataElementContext = (DataFormDataElementContext)renderedDataElementContexts.get(keyIterator.next());
      if (dataElementContext.isCalculated()) {
        renderJavaScriptForPopulation(renderedDataElementContexts, dataElementContext);
      }
    }
  }
  
  private void renderJavaScriptForCalculation(Map dataElementContextMap, DataFormDataElementContext dataElementContext) {
    //first, make sure data elements referenced by the calculation, if any, are actually available.  
    //If not, there is no need to output anything
    Calculation calculation = dataElementContext.getCalculation();
    Iterator referencedElementCuis = calculation.getReferencedDataElementCuis().iterator();
    while (referencedElementCuis.hasNext()) {
      String elementCui = (String)referencedElementCuis.next();
      DataFormDataElementContext referencedDataElementContext = (DataFormDataElementContext)dataElementContextMap.get(elementCui);
      if (referencedDataElementContext == null) {
        return;
      }
    }
    //if all referenced data elements are available then render the javascript method
    StringBuffer buff = WebUtils.getJavaScriptBuffer(getPageContext());
    String functionName = "update" + dataElementContext.getHtmlIdPrimary();
    buff.append("\n\n//function to calculate the ");
    buff.append(dataElementContext.getLabel());
    buff.append(" (");
    buff.append(dataElementContext.getHtmlIdPrimary());
    buff.append(") ");
    buff.append("value");
    buff.append("\nfunction ");
    buff.append(functionName);
    buff.append("() {");
    buff.append(renderCalculationLogic(dataElementContextMap, dataElementContext));
    buff.append("\n}");
    //if there are referenced data elements in the calculation, update the onchange event of 
    //each of them to fire the javascript function just created.
    if (!ApiFunctions.isEmpty(calculation.getReferencedDataElementCuis())) {
      buff.append("\n\n//set the onchange event of every input to the ");
      buff.append(dataElementContext.getLabel());
      buff.append(" (");
      buff.append(dataElementContext.getHtmlIdPrimary());
      buff.append(") calculation to call the population method");
      referencedElementCuis = calculation.getReferencedDataElementCuis().iterator();
      while (referencedElementCuis.hasNext()) {
        String elementCui = (String)referencedElementCuis.next();
        DataFormDataElementContext referencedDataElementContext = (DataFormDataElementContext)dataElementContextMap.get(elementCui);
        if (referencedDataElementContext != null) {
          buff.append("\nEvent.observe('");
          buff.append(referencedDataElementContext.getHtmlIdPrimary());
          buff.append("','change', ");
          buff.append(functionName);
          buff.append(", false);");
        }
      }
    }
    buff.append("\n");
  }
  
  private void renderJavaScriptForPopulation(Map dataElementContextMap, DataFormDataElementContext dataElementContext) {
    StringBuffer buff = WebUtils.getJavaScriptBuffer(getPageContext());
    Calculation calculation = dataElementContext.getCalculation();
    //if there are no referenced data elements in the calculation the calculation uses only 
    //literals, so if the target data element has no value then call it's population method
    if (ApiFunctions.isEmpty(calculation.getReferencedDataElementCuis())) {
      buff.append("\n\n//if the ");
      buff.append(dataElementContext.getLabel());
      buff.append(" (");
      buff.append(dataElementContext.getHtmlIdPrimary());
      buff.append(") data element has no value, call the population method");
      buff.append("\n  if (!$('");
      buff.append(dataElementContext.getHtmlIdPrimary());
      buff.append("').present()) {");
      buff.append("\n    ");
      String functionName = "update" + dataElementContext.getHtmlIdPrimary();
      buff.append(functionName);
      buff.append("();");
      buff.append("\n  }");
    }
    buff.append("\n");
  }
  
  private String renderCalculationLogic(Map dataElementContextMap, DataFormDataElementContext dataElementContext) {
    StringBuffer buff = new StringBuffer(200);
    Calculation calculation = dataElementContext.getCalculation();
    //if any of the referenced data elements are empty or invalid, clear the target element value
    Collection referencedDataElementCuis = calculation.getReferencedDataElementCuis();
    if (!ApiFunctions.isEmpty(referencedDataElementCuis)) {
      buff.append("\n  //clear the ");
      buff.append(dataElementContext.getLabel());
      buff.append(" value if any of the data elements from which it is calculated are empty or invalid");
      Iterator referencedDataElementCuiIterator = referencedDataElementCuis.iterator();
      while (referencedDataElementCuiIterator.hasNext()) {
        String elementCui = (String)referencedDataElementCuiIterator.next();
        DataFormDataElementContext referencedDataElementContext = (DataFormDataElementContext)dataElementContextMap.get(elementCui);
        buff.append("\n  if (!$('");
        buff.append(referencedDataElementContext.getHtmlIdPrimary());
        buff.append("').present() || !");
        if (referencedDataElementContext.isDatatypeDate()) {
          buff.append("isValidDate");
        }
        if (referencedDataElementContext.isDatatypeInt()) {
          buff.append("isValidInteger");
        }
        if (referencedDataElementContext.isDatatypeFloat()) {
          buff.append("isValidFloat");
        }
        buff.append("($F('");
        buff.append(referencedDataElementContext.getHtmlIdPrimary());
        buff.append("'))) {");
        buff.append("\n    $('");
        buff.append(dataElementContext.getHtmlIdPrimary());
        buff.append("').value = '';");
        buff.append("\n    return;");
        buff.append("\n  }");
      }
    }
    buff.append("\n  //determine the value");
    buff.append("\n  var result = ");
    buff.append(convertCalculationToJavaScript(dataElementContextMap, calculation));
    buff.append(";");
    if (FormUtils.CALCULATION_DATATYPE_DATE.equalsIgnoreCase(calculation.getDataType())) {
      buff.append("\n  var month = result.getMonth()+1;");
      buff.append("\n  if ((month+\"\").length < 2) {");
      buff.append("\n    month = \"0\" + month;");
      buff.append("\n  }");
      buff.append("\n  var day = result.getDate();");
      buff.append("\n  if ((day+\"\").length < 2) {");
      buff.append("\n    day = \"0\" + day;");
      buff.append("\n  }");
      buff.append("\n  result = month + '/' + day + '/' + result.getFullYear()");
    }
    buff.append("\n  //set the new value");
    buff.append("\n  $('");
    buff.append(dataElementContext.getHtmlIdPrimary());
    buff.append("').value = result;");
    buff.append("\n  //fire the onchange event for this field, in case its value is an input to another field");
    buff.append("\n  $('");
    buff.append(dataElementContext.getHtmlIdPrimary());
    buff.append("').fireEvent('onchange');");
    return buff.toString();
  }
  
  private String convertCalculationToJavaScript(Map dataElementContextMap, Calculation calculation) {
    StringBuffer buff = new StringBuffer(200);
    String operation = calculation.getOperation();
    String jsMethod = null;
    if (FormUtils.CALCULATION_OPERATION_ADD.equalsIgnoreCase(operation)) {
      jsMethod = "addOperands";
    }
    else if (FormUtils.CALCULATION_OPERATION_SUBTRACT.equalsIgnoreCase(operation)) {
      jsMethod = "subtractOperands";
    }
    else if (FormUtils.CALCULATION_OPERATION_MULTIPLY.equalsIgnoreCase(operation)) {
      jsMethod = "multiplyOperands";
    }
    else if (FormUtils.CALCULATION_OPERATION_DIVIDE.equalsIgnoreCase(operation)) {
      jsMethod = "divideOperands";
    }
    //Iterate over the operands, building the javascript method call
    List operands = calculation.getOperands();
    for (int i=0; i< operands.size(); i++) {
      Operand operand = (Operand)operands.get(i);
      if (i == 0) {
        String temp = buff.toString();
        buff.append(jsMethod);
        buff.append("(");
        buff.append(getJavaScriptForOperand(dataElementContextMap, (Operand)operands.get(i)));
        buff.append(", ");
      }
      else if (i == 1) {
        buff.append(getJavaScriptForOperand(dataElementContextMap, (Operand)operands.get(i)));
        buff.append(")");
      }
      else {
        //String temp = buff.toString();
        //buff = new StringBuffer(200);
        //buff.append(jsMethod);
        //buff.append("(");
        //buff.append(temp);
        buff.insert(0, "(");
        buff.insert(0, jsMethod);
        buff.append(", ");
        buff.append(getJavaScriptForOperand(dataElementContextMap, (Operand)operands.get(i)));
        buff.append(")");
      }
    }
    if (!ApiFunctions.isEmpty(calculation.getConvertResultToIntMethod())) {
      buff.insert(0, "(");
      buff.insert(0, calculation.getConvertResultToIntMethod());
      buff.append(")");
    }
    return buff.toString();
  }
  
  private String getJavaScriptForOperand(Map dataElementContextMap, Operand operand) {
    StringBuffer buff = new StringBuffer(200);
    if (operand instanceof Calculation) {
      buff.append(convertCalculationToJavaScript(dataElementContextMap, (Calculation)operand));
    }
    else if (operand instanceof Literal) {
      Literal literal = (Literal)operand;
      if (FormUtils.CALCULATION_DATATYPE_INTEGER.equalsIgnoreCase(literal.getDataType())) {
        buff.append("parseInt(");
        buff.append(literal.getValue());
        buff.append(")");
      }
      else if (FormUtils.CALCULATION_DATATYPE_FLOAT.equalsIgnoreCase(literal.getDataType())) {
        buff.append("parseFloat(");
        buff.append(literal.getValue());
        buff.append(")");
      }
      else if (FormUtils.CALCULATION_DATATYPE_DATE.equalsIgnoreCase(literal.getDataType())) {
        if (FormUtils.CALCULATION_DATE_LITERAL_TODAY.equalsIgnoreCase(literal.getValue())) {
          buff.append("new Date((new Date()).getFullYear(), (new Date()).getMonth(), (new Date).getDate())");
        }
        else {
          buff.append("new Date('");
          buff.append(literal.getValue());
          buff.append("')");
        }
      }
    }
    else if (operand instanceof DataElementDefinitionReference) {
      DataElementDefinitionReference reference = (DataElementDefinitionReference)operand;
      String jsId = ((DataFormDataElementContext)dataElementContextMap.get(reference.getCui())).getHtmlIdPrimary();
      if (FormUtils.CALCULATION_DATATYPE_INTEGER.equalsIgnoreCase(reference.getDataType())) {
        buff.append("parseInt($F('");
        buff.append(jsId);
        buff.append("'))");
      }
      else if (FormUtils.CALCULATION_DATATYPE_FLOAT.equalsIgnoreCase(reference.getDataType())) {
        buff.append("parseFloat($F('");
        buff.append(jsId);
        buff.append("'))");
      }
      else if (FormUtils.CALCULATION_DATATYPE_DATE.equalsIgnoreCase(reference.getDataType())) {
        buff.append("new Date($F('");
        buff.append(jsId);
        buff.append("'))");
      }
    }
    return buff.toString();
  }
}
