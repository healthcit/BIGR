<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.gulfstreambio.kc.web.support.PageLink" %>
<%
// Generate an empty page for a page link, to be filled in by some other code
// at the appropriate time.
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
PageLink pageLink = formContext.getPageLink();
if (pageLink != null) {
  String contentsId = pageLink.getContentsId();
%>
<div id="<%=contentsId%>" class="kcElementsContainer" <%=pageLink.isSelected()?"":"style=\"display:none;\""%>>
<%
}
else {
%>
<div class="kcElementsContainer">
<%  
}
%>