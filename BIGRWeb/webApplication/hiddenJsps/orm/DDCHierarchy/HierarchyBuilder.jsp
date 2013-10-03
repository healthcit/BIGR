<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.gulfstreambio.gboss.GbossValue" %>
<%@ page import="com.gulfstreambio.gboss.GbossValueSet" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Vector" %>
<%  
  // This is common code that builds the diagnosis, procedure or tissue
	// hierarchy.  This JSP is meant to be included in another that shows
	// a hierarchy for a particular purpose.  The following assumptions are
	// made:
	// 		- The request attribute "DxTcHierarchy" holds the hierarchy, up to 
	//			3 levels, where each level is a Vector
	//                          OR
	//      The request attribute "valueSet" holds the hierarchy, in the form
	//      of a ValueSet
	//		- The script file /orm/js/menu.js	is referenced on the 
	//			page that includes this JSP, since it defines the functions that
	//			are called to show and hide menu items.
	//		- The page that includes this one has a function named selectValue
	//			that takes one parameter which is the selected item.
int tabIndex = 1;
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>

<body onFocus="setFocus();">

<script language="JavaScript">
var ids = new Array();
var images = new Array();
var clicks = new Array();
</script>

	<table border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
  	<tr class="darkgreen">
    	<td>
      	<table border="0" cellspacing="1" cellpadding="0" class="foreground">
        	<tr class="green"> 
          	<td class="lhs_1"> 
            	<div align="center">
            	<input type="button" name="Cancel" value="Close" onClick="window.close()" tabIndex="<%= tabIndex++ %>">
            	</div>
          	</td>
        	</tr>
<%
int currentTOP = 0;
int currentSUB = 0;
int currentOPT = 0;

//either profile or valueSet will be non-null
Vector profile = (Vector)request.getAttribute("DxTcHierarchy");
GbossValueSet valueSet = (GbossValueSet)request.getAttribute("valueSet");

if (valueSet != null) {
  List values = valueSet.getValues();
  for (int k = 0 ; k < values.size(); k++) {
    GbossValue value = (GbossValue)values.get(k);
	%>
						<tr class="yellow"> 
	          	<td class="lhs_1" tabIndex="<%= tabIndex++ %>" onKeyPress="swapMenu('<%= currentTOP %>')"> 
								
								<div onClick="swapMenu('<%= currentTOP %>')">
								   <html:img page="/images/MenuClosed.gif" imageName='<%="Image_"+currentTOP%>' onclick="swapMenu('<%= currentTOP %>')" />&nbsp;<%= value.getDescription() %>
								</div>
								   
							</td>
						</tr>
	<%
		currentSUB = 0;
		List children = value.getValues();
		for (int j = 0 ; j < children.size() ; j++) {
		  GbossValue child = (GbossValue)children.get(j);
			if (ApiFunctions.isEmpty(child.getValues())) {
	%>
						<script>
						  ids.push("<%= currentTOP%>");
						  clicks.push("Close");
					  </script>
					  <tr style="display: none" class="green" id="<%= currentTOP %>"  name="<%= currentTOP %>"> 
						  <td class="lhs_2" tabIndex="<%= tabIndex++ %>" onKeyPress="selectValue('<%= response.encodeURL(child.getCui()) %>');">
							  <a name="<%= child.getCui()%>" href="#" onClick="selectValue('<%= response.encodeURL(child.getCui()) %>');"><%= child.getDescription() %></a> 
							</td>
						</tr>
	<%
			}
			else { 
	%>
						<script>
							ids.push("<%= currentTOP %>");
							clicks.push("Close");
						</script>
						<tr style="display: none" class="green" id="<%= currentTOP %>" name="<%=currentTOP %>"> 
							<td class="lhs_2" tabIndex="<%= tabIndex++ %>" onKeyPress="swapMenu('<%= currentTOP%>.<%= currentSUB %>')"> 
								<div onClick="swapMenu('<%= currentTOP%>.<%= currentSUB %>')">
								<html:img page="/images/MenuClosed.gif" imageName='<%="Image_"+currentTOP+"."+currentSUB%>'/>&nbsp;<%= child.getDescription() %></div>
								   
						
								
							</td>
						</tr>
	<%
				currentOPT = 0;
				List grandChildren = child.getValues();
				for (int n = 0 ; n < grandChildren.size() ; n++) {
					GbossValue grandChild = (GbossValue)grandChildren.get(n);
	%>
						<tr style="display: none" class="<% if((n%2) == 0) out.print("white"); else out.print("grey"); %>" id="<%= currentTOP %>.<%= currentSUB %>"  name="<%= currentTOP %>.<%= currentSUB %>"> 
	          	<script>
								ids.push("<%= currentTOP%>.<%= currentSUB %>");
								clicks.push("Close");
							</script>
	    	      <td class="lhs_3" tabIndex="<%= tabIndex++ %>" onKeyPress="selectValue('<%= response.encodeURL(grandChild.getCui()) %>');"> 
	    	        <a name="<%= grandChild.getCui() %>" href="#" onClick="selectValue('<%= response.encodeURL(grandChild.getCui()) %>');"><%= grandChild.getDescription() %></a> 
	    	      </td>
						</tr>
	<%
					currentOPT++;
				} 
			}
		  currentSUB++;
		}
		currentTOP++;
	}
}

if (profile != null) {
  for (int k = 0 ; k < profile.size(); k++) {
		java.util.Vector applicationLevel = (java.util.Vector)profile.get(k);
		for (int i = 0 ; i < applicationLevel.size(); i += 2) {
			java.lang.String BIGR = (java.lang.String)applicationLevel.get(i);
			java.util.Vector topLevel = (java.util.Vector)applicationLevel.get(i+1); 
			if (BIGR.equals("TOP_MENU")) {
				java.util.Vector subLevel = (java.util.Vector)topLevel.get(0);
				java.lang.String sub = (java.lang.String)subLevel.get(0);
				java.util.Vector optionLevel = (java.util.Vector)subLevel.get(1);
				java.util.Vector option = (java.util.Vector)optionLevel.get(0);
				currentOPT = 0;
				currentTOP++;
	%>
						<tr class="yellow"> 
							<td class="lhs_1">
								<html:link page="<%= (String)option.get(1) %>" target="mainFrame"><%= (String)option.get(0)%></html:link> 
							</td>
						</tr>
	<%
			}
			else {
	%>
						<tr class="yellow"> 
	          	<td class="lhs_1" tabIndex="<%= tabIndex++ %>" onKeyPress="swapMenu('<%= currentTOP %>')"> 
								<div onClick="swapMenu('<%= currentTOP %>')">
								<html:img page="/images/MenuClosed.gif" imageName='<%="Image_"+currentTOP%>'/>&nbsp;<%= BIGR%></div>
							 
						
							</td>
						</tr>
	<%
				currentSUB = 0;
				for (int j = 0 ; j < topLevel.size() ; j++) {
					java.util.Vector subLevel = (java.util.Vector)topLevel.get(j);
					java.lang.String sub = (java.lang.String)subLevel.get(0);
					if (sub.equals("SUB_MENU")) {
						java.util.Vector optionLevel = (java.util.Vector)subLevel.get(1);
						currentOPT = 0;
						java.util.Vector option = (java.util.Vector)optionLevel.get(0);
	%>
						<script>
							ids.push("<%= currentTOP%>");
							clicks.push("Close");
						</script>
						<tr style="display: none" class="green" id="<%= currentTOP %>" name="<%=currentTOP %>"> 
							<td class="lhs_2" tabIndex="<%= tabIndex++ %>" onKeyPress="selectValue('<%= response.encodeURL((String)option.get(1)) %>');">
								<a name="<%= (String)option.get(1)%>" href="#" onClick="selectValue('<%= response.encodeURL((String)option.get(1)) %>');"><%= (String)option.get(0)%></a> 
	<%
						currentSUB++;
					}
				  else {
	%>
						<script>
							ids.push("<%= currentTOP %>");
							clicks.push("Close");
						</script>
						<tr style="display: none" class="green" id="<%= currentTOP %>" name="<%=currentTOP %>"> 
							<td class="lhs_2" tabIndex="<%= tabIndex++ %>" onKeyPress="swapMenu('<%= currentTOP%>.<%= currentSUB %>')"> 
								<div onClick="swapMenu('<%= currentTOP%>.<%= currentSUB %>')">
								<html:img page="/images/MenuClosed.gif" imageName='<%="Image_"+currentTOP+"."+currentSUB%>'/>&nbsp;<%= sub %></div>
								 <img src="<html:rewrite page="/images/MenuClosed.gif"/>" onClick="swapMenu('<%= currentTOP %>.<%=currentSUB %>')" name="Image_<%= currentTOP%>.<%=currentSUB%>">&nbsp;<%= sub %>  
						
							</td>
						</tr>
	<%
						java.util.Vector optionLevel = (java.util.Vector)subLevel.get(1);
						currentOPT = 0;
						for (int n = 0 ; n < optionLevel.size() ; n++) {
							java.util.Vector option = (java.util.Vector)optionLevel.get(n);
							// String url	 = (String)optionLevel.get(n+1);
	%>
						<tr style="display: none" class="<% if((n%2) == 0) out.print("white"); else out.print("grey"); %>" id="<%= currentTOP %>.<%= currentSUB %>" name="<%= currentTOP %>.<%= currentSUB %>"> 
	          	<script>
								ids.push("<%= currentTOP%>.<%= currentSUB %>");
								clicks.push("Close");
							</script>
	    	      <td class="lhs_3" tabIndex="<%= tabIndex++ %>" onKeyPress="selectValue('<%= response.encodeURL((String)option.get(1)) %>');"> <a name="<%= (String)option.get(1)%>" href="#" onClick="selectValue('<%= response.encodeURL((String)option.get(1)) %>');"><%= (String)option.get(0)%></a> </td>
						</tr>
	<%
							currentOPT++;
						} 
						currentSUB++;
					}
				}
			}  
			currentTOP++;
		}
	}
}
%>
      	</table>
    	</td>
  	</tr>  
	</table>
</body>
