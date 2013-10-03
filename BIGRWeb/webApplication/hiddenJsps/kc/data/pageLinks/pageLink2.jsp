<%@ page import="javax.servlet.jsp.JspException" %>
<%@ page import="com.ardais.bigr.util.UniqueIdGenerator" %>
<%@ page import="com.gulfstreambio.kc.web.support.KcUiContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.gulfstreambio.kc.web.support.PageLink" %>
<%@ page import="com.gulfstreambio.kc.web.support.WebUtils" %>
<%
// Generate a second-level link.  This involves generating the LI/A that 
// implements the link itself with the appropriate call to select the link, and 
// appropriate JavaScript to create a GSBIO.tabs.Tab object to select the link.
UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();

// Get the page link corresponding to the parent page so we can add the JavaScript 
// GSBIO.tabs.Tab object for this page link to the parent page's GSBIO.tabs.Tab object.
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
PageLink parentPageLink = formContext.getParentPageLink();

// Get the page link corresponding to the page.
PageLink pageLink = formContext.getPageLink();

// Generate the unique id of the link itself.
String linkId = idgen.getUniqueId();
pageLink.setId(linkId);

// Construct the onclick handler of the link to select the tab.
String onclickHandler = "GSBIO.tabs.AllTabs.getTabs('kcdata').selectTab('" + String.valueOf(linkId) + "');";

// Generate the unique id of the contents that correspond to the link.
String contentsId = idgen.getUniqueId();
pageLink.setContentsId(contentsId);

// Create the JavaScript for the tab, and append it to the global JavaScript
// buffer.
StringBuffer buf = WebUtils.getJavaScriptBuffer(pageContext);
buf.append("GSBIO.tabs.AllTabs.getTabs('kcdata').getTab('");
buf.append(parentPageLink.getId());
buf.append("').addTab(new GSBIO.tabs.Tab({");
buf.append("type:'kc'");
buf.append(",id:'");
buf.append(linkId);
buf.append("'");
buf.append(",contentsId:'");
buf.append(contentsId);
buf.append("'");
buf.append(",displayName:'");
buf.append(pageLink.getDisplayName());
buf.append("'");
buf.append(",selectClassName:'kcPageSelected'");
if (!pageLink.isSelected()) {
  if (pageLink.isUseAjax()) {
    buf.append(",ajax:true");
  }
  String url = pageLink.getUrl();
  if (url != null) {
    buf.append(",url:'");
    buf.append(url);
    buf.append("'");
  }
}
buf.append("}));");
%>
<li>
<a id="<%=linkId%>" href="#" onclick="<%=onclickHandler%>" <%=pageLink.isSelected() ? "class=\"kcPageSelected\"" : ""%>>
<%
if (pageLink.isHasRequired()) {
%>
<span class="kcRequiredInd">*</span>
<%
}
%>
<%=pageLink.getDisplayName()%>
</a>
</li>