package com.gulfstreambio.kc.web.taglib;

import javax.servlet.jsp.JspException;

import org.json.JSONException;
import org.json.JSONObject;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.SystemNameUtils;
import com.gulfstreambio.kc.det.DetAde;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValue;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueTypes;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.FormContextStack;
import com.gulfstreambio.kc.web.support.QueryFormAdeElementContext;
import com.gulfstreambio.kc.web.support.QueryFormElementContext;
import com.gulfstreambio.kc.web.support.WebUtils;

/**
 * Tag handler for the queryElement tag, which serves as a container tag to other KnowledgeCapture
 * tags that render certain aspects of display for entering query criteria based on a single data 
 * element.  This tag generates appropriate JavaScript for the specified data element.  The element 
 * context of the data element must be specified.  
 */
public class QueryElementTag extends KcBaseTag {

  public QueryElementTag() {
    super();
  }

  private QueryFormElementContext getElementContext() {
    return getFormContext().getQueryFormElementContext();
  }
  
  public int doStartTag() throws JspException {
    // The ADE popup URL must be specified, so make sure it is.
    if (ApiFunctions.isEmpty(getAdePopupUrl())) {
      throw new JspException("The ADE popup URL was not specified in the KnowledgeCapture UI context");
    }

    QueryFormElementContext context = getElementContext();

    // Generate and write the DIV that encloses the query element.
    StringBuffer buf = new StringBuffer(128);
    buf.append("<div");
    WebUtils.writeHtmlAttribute(buf, "id", "kc" + SystemNameUtils.convertToUpperFirst(context.getSystemName()));
    WebUtils.writeHtmlAttribute(buf, "class", "kcElement");
    buf.append(">");
    WebUtils.tagWrite(pageContext, buf.toString());    

    return EVAL_BODY_INCLUDE;
  }  
  
  public int doEndTag() throws JspException {
    // Generate the JavaScript to create the appropriate JavaScript query element instance.
    QueryFormElementContext context = getElementContext();

    // Generate and write the end of the DIV.
    StringBuffer divBuf = new StringBuffer(64);
    divBuf.append("</div>");
    divBuf.append("<!-- kc");
    divBuf.append(SystemNameUtils.convertToUpperFirst(context.getSystemName()));
    divBuf.append("-->");
    WebUtils.tagWrite(pageContext, divBuf.toString());

    String ref = context.getJavascriptReference();
    StringBuffer buf = WebUtils.getJavaScriptBuffer(pageContext);
    buf.append(ref);
    buf.append(" = ");
    if (context.isRenderAsDiscrete()) {
      buf.append("GSBIO.kc.query.ElementDiscrete(\"");
      buf.append(context.getSystemName());
      buf.append("\", \"");
      buf.append(context.getDisplayNameWithUnits());
      buf.append("\", ");
      try {
        JSONObject properties = new JSONObject();
        properties.put("any", context.getPropertyAny());
        properties.put("logOp", context.getPropertyLogicalOperator());
        properties.put("value", context.getPropertyValue());
        properties.put("adeSummary", context.getPropertyAdeSummary());
        properties.put("adePopupUrl", getAdePopupUrl());
        buf.append(properties.toString());
      }
      catch (JSONException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      buf.append(");");
    }
    else if (context.isRenderAsRange()) {
      buf.append("GSBIO.kc.query.ElementRange(\"");
      buf.append(context.getSystemName());
      buf.append("\", \"");
      buf.append(context.getDisplayNameWithUnits());
      buf.append("\", ");
      try {
        JSONObject properties = new JSONObject();
        properties.put("any", context.getPropertyAny());
        properties.put("logOp", context.getPropertyLogicalOperator());
        properties.put("compOp1", context.getPropertyComparisonOperator1());
        properties.put("compOp2", context.getPropertyComparisonOperator2());
        properties.put("value1", context.getPropertyValue1());
        properties.put("value2", context.getPropertyValue2());
        properties.put("adeSummary", context.getPropertyAdeSummary());
        properties.put("adePopupUrl", getAdePopupUrl());
        buf.append(properties.toString());
      }
      catch (JSONException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      buf.append(");");
    }
    else {
      throw new JspException("It is unclear how query element (" + context.getCui() + ") should be rendered.");
    }

    // Add the JavaScript query element instance to the collection of all query elements.
    buf.append(context.getJavascriptReferenceQueryElements());
    buf.append(".addElement(");
    buf.append(ref);
    buf.append(");");

    addJavaScriptAdeElements(buf, ref);
        
    return EVAL_PAGE;
  }

  private void addJavaScriptAdeElements(StringBuffer buf, String ref) throws JspException {
    QueryFormElementContext context = getElementContext();
    if (context.isHasAde()) {
      FormContextStack stack = getStack(); 
      FormContext formContext = getFormContext(); 
      DetAde adeMetadata = formContext.getDetDataElement().getAde();
      DetAdeElement[] adeElements = adeMetadata.getAdeElements();
      for (int i = 0; i < adeElements.length; i++) {
        DetAdeElement detAdeElement = adeElements[i];
        formContext.setDetAdeElement(detAdeElement);
        stack.push(formContext);

        // Get the new form context and get the ADE element context to be used for
        // generating the JavaScript ADE element object for each ADE element.  Only
        // generate the JavaScript object for an ADE element if it is present in the query
        // form and it has value(s).
        formContext = stack.peek();
        QueryFormAdeElementContext adeElementContext = formContext.getQueryFormAdeElementContext();
        QueryFormDefinitionValue[] values = adeElementContext.getValues();
        if (values.length > 0) {
          if (adeElementContext.isRenderAsRange()) {
            buf.append("var ae = GSBIO.kc.query.ElementRange();");          
          }
          else if (adeElementContext.isRenderAsDiscrete()) {
            buf.append("var ae = GSBIO.kc.query.ElementDiscrete();");          
          }
          else {
            throw new JspException("It is unclear how to create a JavaScript object for ADE element: " + adeElementContext.getSystemName());
          }
          buf.append("ae.elementId = \"");
          buf.append(adeElementContext.getSystemName());
          buf.append("\";");
          buf.append("ae.anyValue = ");
          buf.append(adeElementContext.isValueAny());
          buf.append(";");
          buf.append("ae.displayName = \"");
          buf.append(adeElementContext.getDisplayNameWithUnits());
          buf.append("\";");
          String logOp = adeElementContext.getValueLogicalOperator();
          if (ApiFunctions.isEmpty(logOp)) {
            buf.append("ae.valueSet = GSBIO.kc.query.ValueSet();");            
          }
          else {
            buf.append("ae.valueSet = GSBIO.kc.query.ValueSet(\"");
            buf.append(adeElementContext.getValueLogicalOperator());
            buf.append("\");");            
          }
          for (int j = 0; j < values.length; j++) {
            QueryFormDefinitionValue value = values[j];
            if (value.getValueType().equals(QueryFormDefinitionValueTypes.COMPARISON)) {
              QueryFormDefinitionValueComparison concreteValue = 
                (QueryFormDefinitionValueComparison) value;
              buf.append("ae.valueSet.addValue(\"");
              buf.append(concreteValue.getValue());
              buf.append("\", \"");
              buf.append(concreteValue.getOperator());
              buf.append("\");");
            }
          }
          buf.append(ref);
          buf.append(".addAdeElement(ae);");
          
        } // has one or more values
        stack.pop();
      } // all ADE elements
    } // element has an ADE
  }
}
