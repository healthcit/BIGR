<%@ page import="com.gulfstreambio.kc.web.support.WebUtils" %>
<%
String contextPath = request.getContextPath();
String fullPath = WebUtils.getFullPath(contextPath);
String jsPath = fullPath + "/js/";
%>
<script type="text/javascript" src="<%=jsPath + "prototype.js"%>"></script>
<script type="text/javascript" src="<%=jsPath + "gsbio.js"%>"></script>
<script type="text/javascript" src="<%=jsPath + "gsbioUtil.js"%>"></script>
<script type="text/javascript">
GSBIO.contextPath = '<%=request.getContextPath()%>';
</script>
