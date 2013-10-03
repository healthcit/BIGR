<%@ page import="javax.servlet.jsp.JspException" %>
<%@ page import="com.gulfstreambio.kc.form.def.query.QueryFormDefinition" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.gulfstreambio.kc.web.support.KcUiContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.HtmlElementNamer" %>
<%@ page import="com.gulfstreambio.kc.web.support.WebUtils" %>
<%@ taglib uri="/kctaglib" prefix="kc" %>

<%
KcUiContext kcUiContext = KcUiContext.getKcUiContext(request);

// The form definition must be specified, so if it is not throw an exception.
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
QueryFormDefinition formDefinition = formContext.getQueryFormDefinition();
if (formDefinition == null) {
  throw new JspException("FormDefinition not stored in form context.");
}

// The base URL that should be used to fetch pages using AJAX when a page link
// is clicked.  If not specified, then we will not use AJAX to fetch pages, and
// instead build the entire set of pages when first requested.
String useAjax = (kcUiContext.getPageLinkBaseUrl() == null) ? "false" : "true";

// Create the JavaScript to clear the collection of query elements and set
// the form definition id, and append it to the global JavaScript buffer.
StringBuffer buf = WebUtils.getJavaScriptBuffer(pageContext);
buf.append("GSBIO.tabs.AllTabs.getTabs('kcquery').clear();");
buf.append(HtmlElementNamer.getJavaScriptReferenceQueryElements());
buf.append(".clear();");
buf.append(HtmlElementNamer.getJavaScriptReferenceQueryElements());
buf.append(".setFormDefinitionId('");
buf.append(formDefinition.getFormDefinitionId());
buf.append("');");
%>
<kc:queryFormPageLinks useAjax="<%=useAjax%>"/>
<kc:queryFormPages useAjax="<%=useAjax%>"/>
