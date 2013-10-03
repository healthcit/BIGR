<%@ page import="com.gulfstreambio.kc.web.support.WebUtils" %>
<%
String contextPath = request.getContextPath();
String fullPath = WebUtils.getFullPath(contextPath);
String jsPath = fullPath + "/js/";
String queryJsPath = fullPath + "/query/js/";
String cssPath = fullPath + "/css/";
%>
<link rel="stylesheet" type="text/css" href="<%=cssPath + "kc.css"%>" />

<script type="text/javascript" src="<%=jsPath + "prototype.js"%>"></script>
<script type="text/javascript" src="<%=jsPath + "gsbio.js"%>"></script>
<script type="text/javascript" src="<%=jsPath + "tabs.js"%>"></script>
<script type="text/javascript" src="<%=queryJsPath + "queryElements.js"%>"></script>
<script type="text/javascript">
GSBIO.contextPath = '<%=request.getContextPath()%>';
GSBIO.kc.query.Elements.setPath('<%=fullPath%>');
</script>
<script type="text/javascript">
</script>

