<%@ page language="java"%>
<%@ page import="com.gulfstreambio.kc.det.DetConcept" %>
<%@ page import="com.gulfstreambio.kc.detviewer.DetViewerQuery" %>
<%@ page import="com.gulfstreambio.kc.detviewer.DetViewerQueryResults" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="/kctaglib" prefix="kc" %>
<%
int NUM_COLS = 2;

// Perform the requested query.
DetViewerQuery query = new DetViewerQuery(request);
DetViewerQueryResults results = query.find();

int numResults = results.getResultsCount();

// No results, so just inform the user.
if (numResults == 0) {
%>
<div id="resultCount">
<h1>Results (<%=String.valueOf(numResults)%>)</h1>
There are no DET concepts that match your parameters
</div>
<%
}

// Exactly one result, so display all of the details of the concept.
else if (numResults == 1) {
%>
<div id="resultCount">
<h1>Results (1)</h1>
There is 1 
<% if (results.getDataElements().length > 0) { %>
Data Element
<% } else if (results.getAdes().length > 0) { %>
ADE
<% } else if (results.getAdeElements().length > 0) { %>
ADE Element
<% } else if (results.getCategories().length > 0) { %>
Category
<% } else if (results.getUnits().length > 0) { %>
Unit
<% } else if (results.getValueSets().length > 0) { %>
Value Set
<% } else if (results.getValues().length > 0) { %>
Value
<% } %>
concept that matches your parameters
</div>

<div id="singleResult" onclick="GSBIO.kc.det.viewer.History.handleResultsClick();">
<table cellspacing="0">
<tr>
<td id="basicInfoContainer">
<div id="basicInfo">
  <kc:detViewerBasic results="<%=results%>"/>
</div>
</td>
<td>
<div id="associated">
  <kc:detViewerAssociated results="<%=results%>"/>
</div>
</td>
</tr>
</table>
</div>
<%
}

// More than one result, so display the descriptions of all results as links.
else {
%>
<div id="resultCount" onclick="GSBIO.kc.det.viewer.History.handleResultsClick();">
<h1>Results (<%=String.valueOf(numResults)%>)</h1>
<%
  List conceptTypes = new ArrayList();
  conceptTypes.add("ADEs");
	conceptTypes.add("ADE Elements");
	conceptTypes.add("Categories");
	conceptTypes.add("Data Elements");
	conceptTypes.add("Units");
	conceptTypes.add("Value Sets");
	conceptTypes.add("Values");
	List conceptsByType = new ArrayList();
	conceptsByType.add(results.getAdes());
	conceptsByType.add(results.getAdeElements());
	conceptsByType.add(results.getCategories());
	conceptsByType.add(results.getDataElements());
	conceptsByType.add(results.getUnits());
	conceptsByType.add(results.getValueSets());
	conceptsByType.add(results.getValues());

	for (int n = 0; n < conceptsByType.size(); n++) {
	  DetConcept[] concepts = (DetConcept[]) conceptsByType.get(n);
	  int totalByType = concepts.length;
	  if (totalByType > 0) { 
	    int perCol = totalByType / NUM_COLS;
	    if ((totalByType % NUM_COLS) > 0) {
	      perCol++;
	    }
%>
<h2><%=(String)conceptTypes.get(n)%> (<%=String.valueOf(totalByType)%>)</h2>
<table cellspacing="0">
<%
	    for (int i = 0; i < perCol; i++) {
%>
<tr>
<%
	      for (int j = 0; j < NUM_COLS; j++) {
	        int index = i+(perCol*j);
	        if (index < totalByType) {
%>
<td><a href="#" id="<%=concepts[index].getCui()%>"><%=concepts[index].getDescription()%></a></td>
<%
	        }
	        else {
%>
<td>&nbsp;</td>
<%
	        }
	      }
%>
</tr>
<%
	    }
%>
</table>
<%
	  }
	}
}
%>
</div>