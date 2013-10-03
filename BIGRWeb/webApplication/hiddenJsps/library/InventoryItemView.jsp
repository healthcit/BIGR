<%--
	** This page should only be called from the JSP tag implementation class
	** for the bigr:inventoryItemView tag (InventoryItemViewTag.java).
  --%> 
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ardais.bigr.api.ApiException"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions"%>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ page import="com.ardais.bigr.api.IdGenerator"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%@ page import="com.ardais.bigr.library.web.column.SampleColumn"%>
<%@ page import="com.ardais.bigr.library.web.column.SampleRowParams"%>
<%@ page import="com.ardais.bigr.library.SampleViewData"%>
<%@ page import="com.ardais.bigr.library.web.helper.SampleSelectionHelper"%>
<%@ page import="com.ardais.bigr.api.ApiProperties"%>
<%@ page import="com.ardais.bigr.javabeans.UrlDto" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>

<%
		// Get data to output in the actual table (row numbers and sample data)
		// These two request attributes are set by the InventoryItemViewTag class.
		SampleViewData viewData = (SampleViewData) request.getAttribute("iivTagViewData");

		List sampleHelpers = viewData.getSampleHelpers();
		List columns = viewData.getColumns().getColumns();
	  String inventoryItemSelectorName = viewData.getItemSelectorName();
	  boolean isIncludeItemSelector = viewData.isIncludeItemSelector();
	  String inventoryItemViewElemId = viewData.getViewElementId();
	  String allItemsElementId = viewData.getAllItemsElementId();
	  String invItemViewElementsStartedCallback = viewData.getItemViewElementsStartedCallback();
	  boolean isGroupedByCase = viewData.isGroupedByCase();
	  boolean isAnyItems = (sampleHelpers.size() > 0);
	  String htmlForEmptyDisplay = viewData.getHtmlForEmptyDisplay();
	  boolean isSpecialEmptyDisplay =
	    (!isAnyItems && !ApiFunctions.isEmpty(htmlForEmptyDisplay));
	    
	  //determine if we should emit a "Select View" link
	  boolean includeSelectViewLink = ("true".equalsIgnoreCase(request.getParameter("includeSelectViewLink")));
	  //if we are to include a "Select View" link, make sure the id of the current view
	  //as well as a list of view choices has been provided
	  String resultsFormDefinitionId = null;
	  List resultsFormDefinitions = null;
	  if (includeSelectViewLink) {
	    resultsFormDefinitionId = (String)request.getAttribute("resultsFormDefinitionId");
	    resultsFormDefinitions = (List)request.getAttribute("resultsFormDefinitions");
	    if (ApiFunctions.isEmpty(resultsFormDefinitionId)) {
	      throw new ApiException("The id of the current results view was not supplied");
	    }
	    if (resultsFormDefinitions == null) {
	      throw new ApiException("A list of candidate results view was not supplied");
	    }
	  }
	  
	  // Create unique ids for the tables of column headers and item details rows.
	  // The prefixes used below are arbitrary, they aren't significant. 
	  String headerTableId = IdGenerator.genUniqueId("iiht");
	  String detailsTableId = IdGenerator.genUniqueId("iidt");
%>
<%--
  ** DIV for overlib, which is used for tooltips.  This JSP may be included
  ** multiple times on a page and this part must only be written once
  ** per page -- the overlibDiv tag takes care of that for us.
  --%>
<bigr:overlibDiv/>

<%--
  ** DIV for popup menu for the sample action.  This JSP may be included
  ** multiple times on a page and this part must only be written once
  ** per page.
 	--%>
<bigr:emitOnce contentId="invItemViewActionMenu">
<div id="sampleActionMenuContainer" 
onclick="sampleActionMenuClick();"
onmouseover="sampleActionMouseOver();">

<div menuFunction="openPatientCaseReport();">Display Donor Case Report</div>

<bigr:isInRole role1="<%=SecurityInfo.ROLE_SYSTEM_OWNER%>">
	<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_ORM_ACCESS_DONOR_VIEW%>">
		<div menuFunction="openDonorInfo();">Display Donor Information</div>
	</bigr:hasPrivilege>
	<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_ORM_ACCESS_CASE_VIEW%>">
		<div menuFunction="openCaseInfo();">Display Case Information</div>
	</bigr:hasPrivilege>
	<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_ORM_ACCESS_SAMPLE_VIEW%>">
		<div menuFunction="openSampleInfo();">Display Sample Information</div>
	</bigr:hasPrivilege>
	<div menuFunction="openRawPath();">Display RAW Path Report</div>
</bigr:isInRole>

<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_VIEW_CONSENT%>">
<div menuFunction="openConsentText();">Consent Text</div>
</bigr:hasPrivilege>

<%
		List urls = viewData.getUrls();
		if (!ApiFunctions.isEmpty(urls)) {
			Iterator iterator = urls.iterator();
			while (iterator.hasNext()) {
				UrlDto url = (UrlDto)iterator.next();
				if (ApiFunctions.isEmpty(url.getTarget())) {
					url.setTarget("_blank");
				}
%>
<div menuFunction="openUrl('<%=url.getUrl()%>','<%=Escaper.jsEscapeInXMLAttr(url.getTarget())%>');"><%=Escaper.htmlEscape(url.getLabel())%></div>
<%
			}
		}
%>

</div>
</bigr:emitOnce>

	<%-- The next DIV encloses the entire inventory item view content. --%>
	<div id="<%= inventoryItemViewElemId %>" class="inventoryItemView"
	  <% if (isIncludeItemSelector) { %> selectorName="<%= inventoryItemSelectorName %>" <% } %>
	  >
	
	<%-- If we're doing a special display when there are no items to display,
	  ** We still include all of the basic elements on the page that we would
	  ** have included if we weren't doing a special display, we just make
	  ** them invisible.  This is potentially helpful for pages that try to
	  ** use some of these items even when the page is empty (for example,
	  ** the allItems* hidden input element, or resizing code for the details
	  ** table).
	  --%>
	  
	<% if (isSpecialEmptyDisplay) {
       out.write(htmlForEmptyDisplay); %>
     <div style="display: none;">
  <% } // end if isSpecialEmptyDisplay %>
  
  <%--emit Select View link if there are items and we've been requested to do so --%>
  <%
  		if (isAnyItems && includeSelectViewLink) {
  %>
  <div style="margin-top: 2px; margin-bottom: 2px;">
    <bigr:selectViewLinkMenu
      currentResultsFormDefinitionId="<%=resultsFormDefinitionId%>"
      resultsFormDefinitions="<%=resultsFormDefinitions%>"
      menuId="selectViewMenu"
    />
    <bigr:selectViewLink
      currentResultsFormDefinitionId="<%=resultsFormDefinitionId%>"
      resultsFormDefinitions="<%=resultsFormDefinitions%>"
      menuId="selectViewMenu"
    />
  </div>
  <%
  		}
  %>
  
	<%-- DIVs/Table for the column headers --%>
  <div id="<%= headerTableId %>"
       style="display: block; width: 100%; overflow: hidden; border-width: 1px 2px 0px 2px; border-style: solid; border-color: #336699;">	
		<div class="background">
			<table id='<%= "headerTable" + headerTableId %>' class="invItemTable" cellspacing="1">

				<% {
				  Iterator columnIter = columns.iterator();
					while (columnIter.hasNext()) {
						SampleColumn col = (SampleColumn) columnIter.next();
						try {
							out.write(col.getWidthHeader()); 
						} catch (Exception e) {
							String msg = "Error writing width for column header " + col.getClass().getName();
							throw new ApiException(msg, e);
						}
					}
				} %>
				<%-- add buffer column that will be used either to line up the header and detail columns
				     if a scroll bar is in use or to fill the table if too few columns have been specified
				     to do so --%>
				<col id='<%= "headerBufferColumn" + headerTableId %>' width="20"> 	
		
				<tr class="green">
				<% {
				  Iterator columnIter = columns.iterator();
					while (columnIter.hasNext()) {
						SampleColumn col = (SampleColumn) columnIter.next();
						out.write(col.getHeader());
						out.write('\n'); // newlines between columns, but no other whitespace
					}
				} %>
				  <%-- add extra TD for buffer column --%>
					<td>&nbsp;</td>
				</tr>
			</table>
		</div>	
	</div>
		
	<%-- DIVs/Table for the rows of data --%>
	<% 
	   // allItemIds will be a comma-separated list of all of the item ids
	   // in this view, in order.  The ids will be separated *only* by commas,
	   // with no whitespace or other characters between them.
	   StringBuffer allItemIds = new StringBuffer(4096);
	%>
	<div id="<%= detailsTableId %>"
			 style="display: block; position: relative; overflow-x: scroll; overflow-y: auto; border-width: 1px 2px 0 2px; border-style: solid; border-color: #336699;">
    <script type="text/javascript">
      {
        var iidt = document.all.<%= detailsTableId %>;
        document.all.<%= headerTableId %>.setExpression('scrollLeft',
          'document.all.<%= detailsTableId %>.scrollLeft');
        iidt.style.setExpression('pixelWidth',
  		    'document.all.<%= headerTableId %>.offsetWidth');
        document.recalc(true);
        <% if (!ApiFunctions.isEmpty(invItemViewElementsStartedCallback)) { %>
          <%= invItemViewElementsStartedCallback %>(iidt);
        <% } %>
      }
    </script>
    
		<div class="background">
			<table id="detailsTable" class="invItemTable" cellspacing="1">

				<% {
				  Iterator columnIter = columns.iterator();
					while (columnIter.hasNext()) {
						SampleColumn col = (SampleColumn) columnIter.next();
						out.write(col.getWidthBody());
					}
				} %>
				<%-- add extra column in case too few columns were chosen to fill the table --%>
				<col id='<%= "detailsBufferColumn" + headerTableId %>' style="display: none; width: 100%"> 	
					
				<% // Loop over item data
				Iterator rowIter = sampleHelpers.iterator();
				String prevConsentId = null;
				boolean firstItem = true;
				for (int i = 0; rowIter.hasNext(); i++) {
					SampleSelectionHelper ssHelper = (SampleSelectionHelper) rowIter.next();
				  
				  String itemId = ssHelper.getId();
					String consentId = ssHelper.getConsentId();
					
					// Append itemId to allItemIds
					{
  					if (firstItem) {
	  				  firstItem = false;
		  			}
			  		else {
				  	  allItemIds.append(',');
					  }
				    allItemIds.append(itemId);
				  }
					
					SampleRowParams rp = new SampleRowParams(i, ssHelper, viewData);
			        
					if (isGroupedByCase && (prevConsentId != null) && !consentId.equals(prevConsentId)) {
				    if (!viewData.getIsSorted())
					{
						// At the beginning of each new case put out an empty row that will
						// appear as a thick line dividing cases.  Only do this when the
						// rows are grouped by case.
			      		%><tr/><%
					}
			    } // end if consentId <> prevConsentId
					prevConsentId = consentId; 
					
					// Here are the data rows.
					out.write(rp.getRowTagHtml());
					
					// Here are the data row columns.
				  Iterator columnIter = columns.iterator();
					while (columnIter.hasNext()) {
						SampleColumn col = (SampleColumn) columnIter.next();
						try {
							out.write(col.getBody(rp));
						} catch (Exception e) {
							String msg = "Error writing contents for column " + col.getClass().getName() +
											" row " + rp.getItemIndexInOverallResultSet();
							throw new ApiException(msg, e);
						}
					}
				%>
				  <%-- add extra TD for detail buffer column. --%>
					<td>&nbsp;</td>
				<%--
				--%></tr>
<%--		--%><% } // end of loop over all the Sample Helpers (that write the data rows) %>
			</table>
			<input type="hidden" name="<%= allItemsElementId %>"
			  value="<%= allItemIds.toString() %>" />
		</div>
	</div> <%-- end Details div --%>
	
	<% if (isSpecialEmptyDisplay) { %>
     </div>
  <% } // end if isSpecialEmptyDisplay %>
  
</div> <%-- end inventoryItemViewElem div --%>
<%
//if too few columns were specified to fill the page, update the two buffer columns
//to fill the page
%>
<script>
var theHeaderTable = document.getElementById('<%= "headerTable" + headerTableId %>');
var theDiv = document.getElementById('<%= headerTableId %>');
if (theHeaderTable.offsetWidth < theDiv.offsetWidth) {
  document.getElementById('<%= "headerBufferColumn" + headerTableId %>').width="100%";
  document.getElementById('<%= "detailsBufferColumn" + headerTableId %>').style.display = "inline";
}
</script>
<script type="text/javascript">
 function openAperio(sample_id)
 {
  var dataServerHost = "<%= ApiProperties.getProperty("aperio.dataServer.host") %>";
  var imageServerHost = "<%= ApiProperties.getProperty("aperio.imageServer.host") %>";
  var dataServerPort = "<%= ApiProperties.getProperty("aperio.dataServer.port")%>";
  var imageServerPort = "<%= ApiProperties.getProperty("aperio.imageServer.port")%>";
  var dataServerContextString = "<%= ApiProperties.getProperty("aperio.dataServer.contextString")%>";
  var imageServerContextString = "<%= ApiProperties.getProperty("aperio.imageServer.contextString")%>";
  var dataServerUrl = "http://" + dataServerHost + ":" + dataServerPort + dataServerContextString;
  var imageServerUrl = "http://" + imageServerHost + ":" + imageServerPort + imageServerContextString;
 //var dataServerSisPort = "<%= ApiProperties.getProperty("aperio.dataServer.sisPort")%>";
  //var dataServerSisUrl = "http://" + dataServerHost + ":" + dataServerSisPort + dataServerContextString;
 // GSBIO.bigr.integration.aperio.HeImage.init({sampleId:"\'" + sample_id +"\'",dataServerBaseUrl: "\'"+ dataServerUrl +"\'",imageServerBaseUrl: "\'" + imageServerUrl + "\'"});
  GSBIO.bigr.integration.aperio.HeImage.init({sampleId:sample_id ,dataServerBaseUrl: dataServerUrl,imageServerBaseUrl: imageServerUrl});
  GSBIO.bigr.integration.aperio.HeImage.displayHeImage();  
 }
</script>
