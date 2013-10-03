<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>


<%
String requested = request.getParameter("request");
String lims = request.getParameter("lims");
java.util.ArrayList tcList = null;
java.util.ArrayList dxList = null;
java.util.ArrayList caseDxList = null;
java.util.ArrayList samplePathologyList = null;

if (requested != null && !requested.equals("")){
  tcList = (java.util.ArrayList) session.getAttribute("requestlabelTC");
  dxList = (java.util.ArrayList) session.getAttribute("requestlabelDX");
} else if (lims != null && !lims.equals("")){
  caseDxList = (java.util.ArrayList) session.getAttribute("labelCaseDx");
  samplePathologyList = (java.util.ArrayList) session.getAttribute("labelSamplePathology");

} else {
  tcList = (java.util.ArrayList) session.getAttribute("labelTC");
  dxList = (java.util.ArrayList) session.getAttribute("labelDX");
}

if(tcList == null) tcList = new java.util.ArrayList();
if(dxList == null) dxList = new java.util.ArrayList();
if(caseDxList == null) caseDxList = new java.util.ArrayList();
if(samplePathologyList == null) samplePathologyList = new java.util.ArrayList();

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

  <% if (lims == null || lims.equals("")){ %>

  lis = window.opener.dxList;
  lislen = lis.childNodes.length;
  srclen = <%= dxList.size() %>;
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
  <% for(int i = 0; i < dxList.size(); i++) { %>
    node = lis.childNodes(<%= i %>);
	oldtext = node.innerText;
	newtext = '<%= ((String)dxList.get(i)).trim() %>';
	if (oldtext != newtext) node.innerText = newtext;
  <% } %>

  lis = window.opener.tcList;
  lislen = lis.childNodes.length;
  srclen = <%= tcList.size() %>;
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
  <% for(int i = 0; i < tcList.size(); i++) { %>
    node = lis.childNodes(<%= i %>);
	oldtext = node.innerText;
	newtext = '<%= ((String)tcList.get(i)).trim() %>';
	if (oldtext != newtext) node.innerText = newtext;
  <% } %>
  
  
  
  
  
  <% } else if (lims != null && !lims.equals("")){ %>
  
  
  lis = window.opener.caseDxList;
  lislen = lis.childNodes.length;
  srclen = <%= caseDxList.size() %>;
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
  <% for(int i = 0; i < caseDxList.size(); i++) { %>
    node = lis.childNodes(<%= i %>);
	oldtext = node.innerText;
	newtext = '<%= ((String)caseDxList.get(i)).trim() %>';
	if (oldtext != newtext) node.innerText = newtext;
  <% } %>

  lis = window.opener.samplePathologyList;
  lislen = lis.childNodes.length;
  srclen = <%= samplePathologyList.size() %>;
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
  <% for(int i = 0; i < samplePathologyList.size(); i++) { %>
    node = lis.childNodes(<%= i %>);
	oldtext = node.innerText;
	newtext = '<%= ((String)samplePathologyList.get(i)).trim() %>';
	if (oldtext != newtext) node.innerText = newtext;
  <% } %>
  
  
  <% } %>

  window.close();
}
</script>

<body bgcolor="#FFFFFF" text="#000000" onload="initPage();">



</body>
</html>
