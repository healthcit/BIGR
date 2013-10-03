<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<%	// -- Standard block for all query pages to find the proper txType
	// -- also MUST put a hidden txType field in the form
	String txType = null;
	com.ardais.bigr.library.web.form.QueryForm qform = 
		(com.ardais.bigr.library.web.form.QueryForm) request.getAttribute("queryForm");
	if (qform != null) {
		txType = qform.getTxType();
	}
	else {
		com.ardais.bigr.library.web.form.ResultsForm rform = 
			(com.ardais.bigr.library.web.form.ResultsForm) request.getAttribute("resultsForm");
		if (rform != null) {
			txType = rform.getTxType();
		}
		else {
			txType = request.getParameter("txType");
		}
	}
%>
<!-- special case, no txType control because this does not forward anywhere -->

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>


<%
String id = request.getParameter("id");
String type = request.getParameter("type");
java.util.ArrayList list = (java.util.ArrayList) session.getAttribute(id + ".label." + txType);
if(list == null) list = new java.util.ArrayList();
%>

<script>
function initPage() {
  var lis;
  var lislen;
  var newNode;
  var node;
  var i;
  var srclen;
  var newtext;
  var oldtext;
  var doc = window.opener.document;

  lis = window.opener.<%= id %>;
  lislen = lis.childNodes.length;
  srclen = <%= list.size() %>;
  if (lislen > srclen) {
    for (i = lislen - 1; i >= srclen; i--) lis.childNodes(i).removeNode(true);
  }
  else if (lislen < srclen) {
    for (i = 1; i <= (srclen - lislen); i++) {
      newNode = doc.createElement("LI");
	  lis.appendChild(newNode);
      newNode.innerText = '';
	}
  }
  <% for(int i = 0; i < list.size(); i++) { %>
    node = lis.childNodes(<%= i %>);
	oldtext = node.innerText;
	newtext = '<%= ((String)list.get(i)).trim() %>';
	if (oldtext != newtext) node.innerText = newtext;
  <% } %>
  
  window.close();
}
</script>

<body bgcolor="#FFFFFF" text="#000000" onload="initPage();">



</body>
</html>
