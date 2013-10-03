<%@ page import="com.gulfstreambio.kc.web.support.KcUiContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.RequestParameterConstants" %>
<%@ taglib uri="/kctaglib" prefix="kc" %>
<%
// Get the flag that indicates whether we should use AJAX to fetch tab pages or not.
String useAjax = String.valueOf(KcUiContext.getKcUiContext(request).isUseAjax());

// Get the name of the selected page.  This may be optionally specified if the
// form definition contains pages, and will indicate which page should be
// displayed.  We'll set it as a hidden input so when the form containing this JSP is
// submitted we'll go back to the same page.
String selectedPage = request.getParameter(RequestParameterConstants.SELECTED_PAGE);
%>
<input type="hidden" name="<%=RequestParameterConstants.SELECTED_PAGE%>" value="<%=(selectedPage == null) ? "" : selectedPage%>"/>
<kc:formInstancePageLinks useAjax="<%=useAjax%>"/>
<kc:formInstancePagesEdit useAjax="<%=useAjax%>"/>
