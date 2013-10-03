<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.gulfstreambio.gboss.GbossValueSet"%>
<%@ page import="com.gulfstreambio.gboss.GbossValue"%>
<%@ page import="java.text.*,java.util.*,com.ardais.bigr.api.*"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
        com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<title><% if(request.getParameter("type").equals("D")) { %>Diagnosis Hierarchy<% } else { %>Tissue Hierarchy<% } %></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">
<script>
<!--
var menuIds = null;

function swapMenu(object) {
    var objString = object.toString();
    var representativeItem = document.getElementById(objString);
    if (representativeItem) {
	var isItemDisplayed = (representativeItem.style.display != 'none');
	if (isItemDisplayed) { hideMenu(objString); } else { showMenu(objString); }
    }
}

function showMenu(objString) {
    var menuItems = document.getElementsByName(objString);
    setImage("Image_" + objString, '<html:rewrite page="/images/MenuOpened.gif"/>');
    for (j = 0; j < menuItems.length; j++) {
        menuItems[j].style.display = 'inline';
    }
}

function hideMenu(objString) {
    for (i = 0; i < menuIds.length; i++) {
        var idString = menuIds[i].toString();    
        if (objString == idString.substr(0,objString.length)) {
            var menuItems = document.getElementsByName(idString);
	    setImage("Image_" + idString, '<html:rewrite page="/images/MenuClosed.gif"/>');
            for (j = 0; j < menuItems.length; j++) {
                menuItems[j].style.display = 'none';
            }
        }
    } 
}

function setImage(imageElementId, imageSrc) {
    document.getElementById(imageElementId).src = imageSrc;
}

function changecheck(theID, theBool){
    checkBoxes = document.getElementsByName(theID.toString());
    for(i=0; i<checkBoxes.length;i++){
        checkBoxes[i].checked = theBool;
    }
}

<%
  String requested = request.getParameter("request");
  String lims = request.getParameter("lims");
  String type = request.getParameter("type");
  ArrayList tcList = null;
  ArrayList dxList = null;
  Hashtable tcHash = null;
  Hashtable dxHash = null;
  GbossValueSet hierarchy = (GbossValueSet)request.getAttribute("valueSet");

  if (ApiFunctions.safeStringLength(lims) > 0) {
  	if(type.equals("DCaseDx")){
		dxList = (ArrayList) session.getAttribute("labelCaseDx");
    	dxHash = (Hashtable) session.getAttribute("selectedCaseDx");
    } else if (type.equals("DSamplePathology")){
    	dxList = (ArrayList) session.getAttribute("labelSamplePathology");
    	dxHash = (Hashtable) session.getAttribute("selectedSamplePathology");
    }
  } else if (ApiFunctions.safeStringLength(requested) > 0) {
    tcList = (ArrayList) session.getAttribute("requestlabelTC");
    dxList = (ArrayList) session.getAttribute("requestlabelDX");
    tcHash = (Hashtable) session.getAttribute("requestTC");
    dxHash = (Hashtable) session.getAttribute("requestDX");
  } else {
    tcList = (ArrayList) session.getAttribute("labelTC");
    dxList = (ArrayList) session.getAttribute("labelDX");
    tcHash = (Hashtable) session.getAttribute("selectedTC");
    dxHash = (Hashtable) session.getAttribute("selectedDX");
  }

  if(tcList == null) tcList = new ArrayList();
  if(dxList == null) dxList = new ArrayList();
  if(dxHash == null) dxHash = new Hashtable();
  if(tcHash == null) tcHash = new Hashtable();

  Hashtable selectedHash = new Hashtable();
  
  if(type.startsWith("D") || type.equals("L")) {
    selectedHash = dxHash;
  } else if (type.equals("T")) {
    selectedHash = tcHash;
  }

  int jMax = 0;

  Set checkedItems = new HashSet();
  Set openItems = new HashSet();
  Set menuIds = new HashSet();

  List values = hierarchy.getValues();
  for (int i = 0; i < values.size(); i++) { 
      boolean level1_checked = true;
      boolean level1_found_checked_child = false;
      GbossValue value = (GbossValue) values.get(i);
      String level1_label = (String) value.getDescription();
      String level1_key = level1_label;
      List children = value.getValues();

      menuIds.add(String.valueOf(i));

      for (int j = 0; j < children.size(); j++) {
        GbossValue child = (GbossValue)children.get(j);
        String level2_label;
        String level2_key;

        if (ApiFunctions.isEmpty(child.getValues())) {
          level2_label = child.getDescription();
          level2_key = child.getCui();
          boolean checked = (selectedHash.get(level2_key) != null);
          if (checked) { 
            level1_found_checked_child = true; 
            checkedItems.add(level2_key);
          } 
          else { 
            level1_checked = false; 
          }
        } 
        else {
          level2_label = child.getDescription();
          level2_key = level2_label;
          boolean level2_checked = true;
          boolean level2_found_checked_child = false;

          menuIds.add(String.valueOf(i) + "." + String.valueOf(j));

          List grandChildren = child.getValues();
          for (int k = 0; k < grandChildren.size(); k++) {
  					GbossValue grandChild = (GbossValue)grandChildren.get(k);
            String level3_label = grandChild.getDescription();
            String level3_key = grandChild.getCui();
            boolean checked = (selectedHash.get(level3_key) != null);
            if (checked) { 
              level2_found_checked_child = true;
              checkedItems.add(level3_key);
            } 
            else { 
              level2_checked = false; 
            }
          } // end for k

          boolean level2_open = (level2_found_checked_child && (! level2_checked));
          if (level2_found_checked_child) {
            level1_found_checked_child = true;
          }
          if (! level2_checked) {
            level1_checked = false;
          }
          if (level2_checked) {
            checkedItems.add(level2_key);
          }
          if (level2_open) {
            openItems.add(level2_key);
          }
        } // end else
      } // end for j

      boolean level1_open;
      if (type.equals("D")) {
          // level-1 diagnoses are always open and unchecked (actually,
          // they don't have check boxes).
          level1_checked = false;
          level1_open = true;
      }
      else {
          level1_open = (level1_found_checked_child && (! level1_checked));
      }

     if (level1_checked) checkedItems.add(level1_key);
     if (level1_open) openItems.add(level1_key);
  } // end for i
%>

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

  <% if (lims == null || lims.equals("")) { %>

  lis = window.opener.caseDxList;
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

  lis = window.opener.samplePathologyList;
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

  menuIds = new Array(
  <%
    {
        Iterator iter = menuIds.iterator();
        boolean first = true;
        while (iter.hasNext()) {
            String id = (String) iter.next();
            if (first) { first = false; } else { out.println(","); }
            out.print("  \""); out.print(id); out.print("\"");
        }
    }
  %>
  );
  
  <% } else if (lims == null || lims.equals("")) { %>
  
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

  menuIds = new Array(
  <%
    {
        Iterator iter = menuIds.iterator();
        boolean first = true;
        while (iter.hasNext()) {
            String id = (String) iter.next();
            if (first) { first = false; } else { out.println(","); }
            out.print("  \""); out.print(id); out.print("\"");
        }
    }
  %>
  );
  
  <%}%>
  

  document.all.VisibleStuff.style.display = 'inline';
}
//-->
</script>

<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">

</head>
<body class="bigr" onload="initPage();focus();">
<div id="VisibleStuff" style="display: none;">
<form name="selects" action="<html:rewrite page="/library/Dispatch"/>" method="post">
<% if (requested != null) { %>
  <input type="hidden" name="request" value="yes">
<% } %>
<% if (lims != null) { %>
  <input type="hidden" name="lims" value="true">
<% } %>
  <input type="hidden" name="clear" value="">
  <input type="hidden" name="op" value="DXTCHierarchy">
  <input type="hidden" name="type" value="<%= request.getParameter("type") %>">
  <input type="hidden" name="process" value="true">

  <p align="center"> 
    <input type="submit" name="Submit3" value="Continue">
    <input type="submit" name="clearButton2" value="Clear All" onClick="document.selects.clear.value='Yes';">
    <input type="button" name="Submit22" value="Cancel" onClick="window.close();">
  </p>

  <table border="0" cellspacing="0" cellpadding="0" class="background" width="100%">
    <tr class="darkgreen"> 
      <td> 
        <table border="0" cellspacing="1" cellpadding="0" class="foreground" width="100%">
          <tr class="green"><td class="level_1"><div align="center"></div></td><td>Select</td></tr>
          <% for (int i = 0; i < values.size(); i++) { 
               GbossValue value = (GbossValue)values.get(i);
               String level1_label = value.getDescription();
               String level1_key = level1_label;
               boolean level1_checked = checkedItems.contains(level1_key);
               boolean level1_open = openItems.contains(level1_key);
          %>
            <tr class="<%= (type.equals("D")) ? "yellow" : "green" %>"> 
              <td class="level_1"> 
                <% String imgSrc1 = "/images/Menu" + (level1_open ? "Opened" : "Closed") + ".gif"; %>
                <div onClick="swapMenu('<%= i %>')"><img src="<html:rewrite page="<%=imgSrc1%>"/>" id="Image_<%= i %>"><%= level1_label %></div>
              </td>
              <td> 
              <% if(!type.equals("D")) { %>
                <input type="checkbox" name="<%= i %>_p" value="<%= level1_key %>" <%= (level1_checked ? "checked" : "") %> onClick="changecheck('<%= i %>', this.checked)">
              <% } else { %>
                &nbsp; 
              <% } %>
              </td>
            </tr>
            <% 
               List children = value.getValues();
               for (int j = 0; j < children.size(); j++) {
                 jMax++;
                 GbossValue child = (GbossValue)children.get(j);
                 String level2_label;
                 String level2_key;
                           
                 if (ApiFunctions.isEmpty(child.getValues())) {
                   level2_label = child.getDescription();
                   level2_key = child.getCui();
                   boolean level2_checked = checkedItems.contains(level2_key);
            %>
                       <tr class="<%= ((j % 2) == 0) ? "white" : "grey" %>" id="<%= i %>" <%= (level1_open ? "" : "style=\"display: none;\"") %>> 
                         <td class="level_2"><%= level2_label %> </td>
                         <td> 
                           <input type="checkbox" name="<%= i %>" value="<%= level2_key %>"
                                  <%= (level2_checked) ? "checked" : "" %> onClick="changecheck('<%=i%>_p', false);">
                         </td>
                       </tr>
            <%   } 
                 else {
			             level2_label = child.getDescription();
			             level2_key = level2_label;
                   boolean level2_checked = checkedItems.contains(level2_key);
                   boolean level2_open = (level1_open && openItems.contains(level2_key));
            %>
                       <tr class="green" id="<%= i %>" <%= (level1_open ? "" : "style=\"display: none;\"") %>> 
                         <td class="level_2"> 
                           <div onClick="swapMenu('<%= i %>.<%= j %>');">
                			<% String imgSrc2 = "/images/Menu" + (level2_open ? "Opened" : "Closed") + ".gif"; %>
                             <img src="<html:rewrite page="<%=imgSrc2%>"/>" id="Image_<%= i %>.<%= j %>"><%= level2_label %>
                           </div>
                         </td>
                         <td> 
                           <input type="checkbox" name="<%= i %>_<%= j %>_p" value="<%= level2_key %>"
                                  <%= (level2_checked) ? "checked" : "" %> onClick="changecheck('<%= i %>_<%= j %>', this.checked)">
                         </td>
                       </tr>
            <%
                   List grandChildren = child.getValues();
                   for (int k = 0; k < grandChildren.size(); k++) {
                     GbossValue grandChild = (GbossValue) grandChildren.get(k);
                     String level3_label = grandChild.getDescription();
                     String level3_key = grandChild.getCui();
                     boolean level3_checked = checkedItems.contains(level3_key);
            %>
<tr class="<%= ((k % 2) == 0) ? "white" : "grey" %>" id="<%= i %>.<%= j %>" <%= (level2_open ? "" : "style=\"display: none;\"") %>> 
  <td class="level_3"><%= level3_label %></td>
  <td><input type="checkbox" name="<%= i %>_<%= j %>" value="<%= level3_key %>"
      <%= (level3_checked) ? "checked" : "" %> onClick="changecheck('<%=i%>_<%=j%>_p', false);"></td></tr>
            <%     } // end for k
                 } // end else
               } // end for j
             } // end for i
            %>
        </table>
      </td>
    </tr>
  </table>
  <p> 
    <input type="hidden" name="i" value="<%= values.size() %>">
    <% if(type.startsWith("D")) { %> <input type="hidden" name="j" value="<%= jMax %>"> <% } %>
  </p>
  <p align="center"> 
    <input type="submit" name="Submit" value="Continue">
    <input type="submit" name="clearButton" value="Clear All" onClick="document.selects.clear.value='Yes';">
    <input type="button" name="Submit2" value="Cancel" onClick="window.close();">
  </p>
</form>
<script>
window.focus();
</script>
</div>
</body>
</html>
