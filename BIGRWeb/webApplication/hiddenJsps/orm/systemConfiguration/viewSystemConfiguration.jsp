<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.concept.BigrConcept" %>
<%@ page import="com.ardais.bigr.concept.BigrConceptList" %>
<%@ page import="com.ardais.bigr.configuration.SimpleList" %>
<%@ page import="com.ardais.bigr.configuration.SystemConfiguration" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValue" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet" %>
<%@ page import="com.ardais.bigr.iltds.databeans.Attribute" %>
<%@ page import="com.ardais.bigr.iltds.databeans.SampleType" %>
<%@ page import="com.ardais.bigr.iltds.databeans.ParentSampleType" %>
<%@ page import="com.ardais.bigr.iltds.databeans.ChildSampleType" %>
<%@ page import="com.ardais.bigr.iltds.databeans.StorageType" %>
<%@ page import="com.ardais.bigr.iltds.databeans.DerivativeOperation" %>
<%@ page import="com.ardais.bigr.kc.form.def.BigrFormDefinition" %>
<%@ page import="com.gulfstreambio.kc.form.def.Tag" %>
<%@ page import="com.gulfstreambio.kc.form.def.results.ResultsFormDefinition" %>
<%@ page import="com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement" %>
<%@ page import="com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionHostElement" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="javax.servlet.http.*" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>View System Configuration</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">
var myBanner = 'View System Configuration';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
}

//expand or collapse the information for an element list
function showElementListMembers(listId, imgId){
var image;
var alt;
var displayValue;
var table = document.all(listId);
var img = document.all(imgId);
var isClosed = (table.style.display == 'none');
if (isClosed) {
	image = '<html:rewrite page="/images/MenuOpened.gif"/>';
	alt = "Close element list";
	displayValue = 'inline';
}
else {
	image = '<html:rewrite page="/images/MenuClosed.gif"/>';
	alt = "Open element list";
	displayValue = 'none';
}
table.style.display = displayValue;
img.src=image;
img.alt=alt;
}

// expand or collapse the information for a concept list
function showConceptListMembers(listId, imgId){
	var image;
	var alt;
	var displayValue;
	var table = document.all(listId);
	var img = document.all(imgId);
	var isClosed = (table.style.display == 'none');
	if (isClosed) {
		image = '<html:rewrite page="/images/MenuOpened.gif"/>';
		alt = "Close concept list";
		displayValue = 'inline';
	}
	else {
		image = '<html:rewrite page="/images/MenuClosed.gif"/>';
		alt = "Open concept list";
		displayValue = 'none';
	}
	table.style.display = displayValue;
	img.src=image;
	img.alt=alt;
}

//expand or collapse the information for a concept list
function showSimpleListMembers(listId, imgId){
var image;
var alt;
var displayValue;
var table = document.all(listId);
var img = document.all(imgId);
var isClosed = (table.style.display == 'none');
if (isClosed) {
	image = '<html:rewrite page="/images/MenuOpened.gif"/>';
	alt = "Close simple list";
	displayValue = 'inline';
}
else {
	image = '<html:rewrite page="/images/MenuClosed.gif"/>';
	alt = "Open simple list";
	displayValue = 'none';
}
table.style.display = displayValue;
img.src=image;
img.alt=alt;
}

//expand or collapse the information for a legal value set
function showLegalValueSetMembers(listId, imgId){
var image;
var alt;
var displayValue;
var table = document.all(listId);
var img = document.all(imgId);
var isClosed = (table.style.display == 'none');
if (isClosed) {
	image = '<html:rewrite page="/images/MenuOpened.gif"/>';
	alt = "Close legal value set";
	displayValue = 'inline';
}
else {
	image = '<html:rewrite page="/images/MenuClosed.gif"/>';
	alt = "Open legal value set";
	displayValue = 'none';
}
table.style.display = displayValue;
img.src=image;
img.alt=alt;
}

// expand or collapse the information for a sample type
function showSampleType(sampleTypeId, imgId){
	var image;
	var alt;
	var displayValue;
	var storageTable = document.all(sampleTypeId + 'StorageTypes');
	var img = document.all(imgId);
	var isClosed = (storageTable.style.display == 'none');
	if (isClosed) {
		image = '<html:rewrite page="/images/MenuOpened.gif"/>';
		alt = "Close sample type";
		displayValue = 'inline';
	}
	else {
		image = '<html:rewrite page="/images/MenuClosed.gif"/>';
		alt = "Open sample type";
		displayValue = 'none';
	}
	storageTable.style.display = displayValue;
	img.src=image;
	img.alt=alt;
}

// expand or collapse the information for a sample type's storage types
function showSampleTypeStorageTypes(sampleTypeStorageTypeId, imgId){
	var image;
	var alt;
	var displayValue;
	var table = document.all(sampleTypeStorageTypeId);
	var img = document.all(imgId);
	var isClosed = (table.style.display == 'none');
	if (isClosed) {
		image = '<html:rewrite page="/images/MenuOpened.gif"/>';
		alt = "Close sample type storage types";
		displayValue = 'inline';
	}
	else {
		image = '<html:rewrite page="/images/MenuClosed.gif"/>';
		alt = "Open sample type storage types";
		displayValue = 'none';
	}
	table.style.display = displayValue;
	img.src=image;
	img.alt=alt;
}

// expand or collapse the information for a derivative operation
function showDerivativeOperation(derivativeOperationId, imgId){
	var image;
	var alt;
	var displayValue;
	var table = document.all(derivativeOperationId);
	var sampleTypeTable = document.all(derivativeOperationId + 'SampleTypes');
	var img = document.all(imgId);
	var isClosed = (table.style.display == 'none');
	if (isClosed) {
		image = '<html:rewrite page="/images/MenuOpened.gif"/>';
		alt = "Close derivative operation";
		displayValue = 'inline';
	}
	else {
		image = '<html:rewrite page="/images/MenuClosed.gif"/>';
		alt = "Open derivative operation";
		displayValue = 'none';
	}
	table.style.display = displayValue;
	sampleTypeTable.style.display = displayValue;
	img.src=image;
	img.alt=alt;
}

// expand or collapse the information for a sample type's storage types
function showDerivationOperationSampleTypes(derivationOperationSampleTypesId, imgId){
	var image;
	var alt;
	var displayValue;
	var table = document.all(derivationOperationSampleTypesId);
	var img = document.all(imgId);
	var isClosed = (table.style.display == 'none');
	if (isClosed) {
		image = '<html:rewrite page="/images/MenuOpened.gif"/>';
		alt = "Close derivative operation sample types";
		displayValue = 'inline';
	}
	else {
		image = '<html:rewrite page="/images/MenuClosed.gif"/>';
		alt = "Open derivative operation sample types";
		displayValue = 'none';
	}
	table.style.display = displayValue;
	img.src=image;
	img.alt=alt;
}
</script>
</head>
<body class="bigr" onload="initPage();">
  <div align="center">
      <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
        <tr class="white"> 
          <td nowrap><b>Default Results View</b></td>
        </tr>
        <%
          ResultsFormDefinition defaultResultsView = null;
          BigrFormDefinition bigrDefaultResultsView = SystemConfiguration.getDefaultResultsView();
				  if (bigrDefaultResultsView != null) {
				    defaultResultsView = (ResultsFormDefinition) bigrDefaultResultsView.getKcFormDefinition();
				  }
          if (defaultResultsView == null) {
        %>
          <tr class="white"> 
            <td nowrap>No default results view defined.</td>
          </tr>
        <%
          }
          else {
            String elementTypeRowClass = "grey";
		        String elementId = "dataElements";
			      String imgId = elementId + "Img";
			      String altText = "Open data elements";
			  
						request = (HttpServletRequest) pageContext.getRequest(); 
						String contextPath = request.getContextPath(); 
						String imgSource =  contextPath + "/images/MenuClosed.gif";
			  %>
          <tr class="<%=elementTypeRowClass%>">
            <td>
			        <span onClick="showElementListMembers('<%=elementId%>','<%=imgId%>')" onKeyPress="showElementListMembers('<%=elementId%>','<%=imgId%>')">
				        <img id="<%=imgId%>" alt="<%=altText%>" src="<%=imgSource%>"/>
					       &nbsp;<b>Data Elements (<%= defaultResultsView.getResultsDataElements().length %>)</b>
					    </span>
	          </td>
	        </tr>
	        <tr>
	          <td>
   					  <table style="display: none;" id="<%=elementId%>" border="0" cellspacing="1" cellpadding="3" class="foreground">
					    <%
					    ResultsFormDefinitionDataElement[] dataElements = defaultResultsView.getResultsDataElements();
					      int dataElementCount = 0;
					      String dataElementRowClass = null;
					      if (elementTypeRowClass.equalsIgnoreCase("white")) {
					        dataElementRowClass = "grey";
					      }
					      else {
					        dataElementRowClass = "white";
					      }
					      if (dataElements.length == 0) {
							  %>
						      <tr class="<%=dataElementRowClass%>">
						        <td>
							      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;None.
							      </td>
							    </tr>
		            <%
					      }
					      for (int i = 0; i < dataElements.length; i++) {
					        ResultsFormDefinitionDataElement dataElement = dataElements[i];
					        StringBuffer displayValue = new StringBuffer(100);
					        displayValue.append("<b>Cui</b>: ");
					        displayValue.append(dataElement.getCui());
					        if (!ApiFunctions.isEmpty(dataElement.getDisplayName())) {
					          displayValue.append(", <b>display name</b>: ");
					          displayValue.append(dataElement.getDisplayName());
					        }
					        Tag[] tags = dataElement.getTags();
					        if (tags.length > 0) {
					          boolean addComma = false;
					          displayValue.append(", <b>tags</b>: ");
					          for (int j = 0; j < tags.length; j++) {
					            if (addComma) {
					              displayValue.append(", ");
					            }
					            Tag tag = tags[j];
					            displayValue.append(tag.getType());
					            displayValue.append(" = ");
					            displayValue.append(tag.getValue());
					            addComma = true;
					          }
					        }
					    %>
					      <tr class="<%=dataElementRowClass%>">
					        <td>
						      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= displayValue %>
						      </td>
						    </tr>
	            <%
	              dataElementCount = dataElementCount + 1;
					        if (dataElementRowClass.equalsIgnoreCase("white")) {
					          dataElementRowClass = "grey";
					        }
					        else {
					          dataElementRowClass = "white";
					        }
            		}
	            %>
				      </table>
            </td>
          </tr>
       <%
              elementTypeRowClass = "white";
			        elementId = "hostElements";
				      imgId = elementId + "Img";
				      altText = "Open host elements";
				  
							request = (HttpServletRequest) pageContext.getRequest(); 
							contextPath = request.getContextPath(); 
							imgSource =  contextPath + "/images/MenuClosed.gif";
				  %>
	          <tr class="<%=elementTypeRowClass%>">
	            <td>
				        <span onClick="showElementListMembers('<%=elementId%>','<%=imgId%>')" onKeyPress="showElementListMembers('<%=elementId%>','<%=imgId%>')">
					        <img id="<%=imgId%>" alt="<%=altText%>" src="<%=imgSource%>"/>
						       &nbsp;<b>Host Elements (<%= defaultResultsView.getResultsHostElements().length %>)</b>
						    </span>
		          </td>
		        </tr>
		        <tr>
		          <td>
	   					  <table style="display: none;" id="<%=elementId%>" border="0" cellspacing="1" cellpadding="3" class="foreground">
						    <%
						    ResultsFormDefinitionHostElement[] hostElements = defaultResultsView.getResultsHostElements();
						      int hostElementCount = 0;
						      String hostElementRowClass = null;
						      if (elementTypeRowClass.equalsIgnoreCase("white")) {
						        hostElementRowClass = "grey";
						      }
						      else {
						        hostElementRowClass = "white";
						      }
						      if (hostElements.length == 0) {
									  %>
								      <tr class="<%=hostElementRowClass%>">
								        <td>
									      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;None.
									      </td>
									    </tr>
				            <%
							      }
						      for (int i = 0; i < hostElements.length; i++) {
						        ResultsFormDefinitionHostElement hostElement = hostElements[i];
						        StringBuffer displayValue = new StringBuffer(100);
						        displayValue.append("<b>Host id</b>: ");
						        displayValue.append(hostElement.getHostId());
						        if (!ApiFunctions.isEmpty(hostElement.getDisplayName())) {
						          displayValue.append(", <b>display name</b>: ");
						          displayValue.append(hostElement.getDisplayName());
						        }
						        Tag[] tags = hostElement.getTags();
						        if (tags.length > 0) {
						          boolean addComma = false;
						          displayValue.append(", <b>tags</b>: ");
						          for (int j = 0; j < tags.length; j++) {
						            if (addComma) {
						              displayValue.append(", ");
						            }
						            Tag tag = tags[j];
						            displayValue.append(tag.getType());
						            displayValue.append(" = ");
						            displayValue.append(tag.getValue());
						            addComma = true;
						          }
						        }
						    %>
						      <tr class="<%=hostElementRowClass%>">
						        <td>
							      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= displayValue %>
							      </td>
							    </tr>
		            <%
		                hostElementCount = hostElementCount + 1;
						        if (hostElementRowClass.equalsIgnoreCase("white")) {
						          hostElementRowClass = "grey";
						        }
						        else {
						          hostElementRowClass = "white";
						        }
	            		}
		            %>
					      </table>
	            </td>
	          </tr>
	     <%
          }
       %>
      </table>
      <br/>
      <br/>
      <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
        <tr class="white"> 
        <%
          List conceptList = SystemConfiguration.getConceptLists();
          String headerInfo = "";
          if (!ApiFunctions.isEmpty(conceptList)) {
            headerInfo = " (" + conceptList.size() + ")";
          }
        %>
           <td nowrap><b>Concept Lists<%=headerInfo%></b></td>
        </tr>
        <%
          if (ApiFunctions.isEmpty(conceptList)) {
        %>
            <tr class="white"> 
              <td nowrap>No concept lists defined.</td>
            </tr>
        <%
          }
          else {
            int listCount = 0;
            Iterator listIterator = conceptList.iterator();
            String listRowClass = null;
            while (listIterator.hasNext()) {
              BigrConceptList list = (BigrConceptList)listIterator.next();
		      String listId = "concept" + listCount;
			  String imgId = listId + "Img";
			  String altText = "Open concept list";
			  
			  request = (HttpServletRequest) pageContext.getRequest(); 
			  String contextPath = request.getContextPath(); 
			  String imgSource =  contextPath + "/images/MenuClosed.gif";	

			  if (listCount%2 == 0) {
			    listRowClass = "grey";
			  }
			  else {
			    listRowClass = "white";
			  }
			  listCount = listCount + 1;
        %>
              <tr class="<%=listRowClass%>">
                <td>
			      <span onClick="showConceptListMembers('<%=listId%>','<%=imgId%>')" onKeyPress="showConceptListMembers('<%=listId%>','<%=imgId%>')">
				    <img id="<%=imgId%>" alt="<%=altText%>" src="<%=imgSource%>"/>
					&nbsp;<b><%= list.getName() %>&nbsp;&nbsp;(<%=list.getSize()%>)</b>
					  </span>
	                </td>
	              </tr>
	              <tr>
	                <td>
   					  <table style="display: none;" id="<%=listId%>" border="0" cellspacing="1" cellpadding="3" class="foreground">
					<%
					  Iterator conceptIterator = list.iterator();
					  int conceptCount = 0;
					  String conceptRowClass = null;
					  if (listRowClass.equalsIgnoreCase("white")) {
					    conceptRowClass = "grey";
					  }
					  else {
					    conceptRowClass = "white";
					  }
					  while (conceptIterator.hasNext()) {
					    BigrConcept concept = (BigrConcept)conceptIterator.next();
					%>
					    <tr class="<%=conceptRowClass%>">
					      <td>
						    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= concept.getDescription() + " (" + concept.getCode() + ")" %>
						  </td>
						</tr>
	            <%
            		    conceptCount = conceptCount + 1;
					    if (conceptRowClass.equalsIgnoreCase("white")) {
					      conceptRowClass = "grey";
					    }
					    else {
					      conceptRowClass = "white";
					    }
            		  }
	            %>
				  </table>
                </td>
              </tr>
       <%
            }
          }
        %>
      </table>
      <br/>
      <br/>
      <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
        <tr class="white"> 
        <%
          List simpleList = SystemConfiguration.getSimpleLists();
          headerInfo = "";
          if (!ApiFunctions.isEmpty(simpleList)) {
            headerInfo = " (" + simpleList.size() + ")";
          }
        %>
           <td nowrap><b>Simple Lists<%=headerInfo%></b></td>
        </tr>
        <%
          if (ApiFunctions.isEmpty(simpleList)) {
        %>
            <tr class="white"> 
              <td nowrap>No simple lists defined.</td>
            </tr>
        <%
          }
          else {
            int listCount = 0;
            Iterator listIterator = simpleList.iterator();
            String listRowClass = null;
            while (listIterator.hasNext()) {
              SimpleList list = (SimpleList)listIterator.next();
		      String listId = "simple" + listCount;
			  String imgId = listId + "Img";
			  String altText = "Open simple list";
			  
			  request = (HttpServletRequest) pageContext.getRequest(); 
			  String contextPath = request.getContextPath(); 
			  String imgSource =  contextPath + "/images/MenuClosed.gif";	

			  if (listCount%2 == 0) {
			    listRowClass = "grey";
			  }
			  else {
			    listRowClass = "white";
			  }
			  listCount = listCount + 1;
        %>
              <tr class="<%=listRowClass%>">
                <td>
			      <span onClick="showSimpleListMembers('<%=listId%>','<%=imgId%>')" onKeyPress="showSimpleListMembers('<%=listId%>','<%=imgId%>')">
				    <img id="<%=imgId%>" alt="<%=altText%>" src="<%=imgSource%>"/>
					&nbsp;<b><%= list.getName() %>&nbsp;&nbsp;(<%=list.getSize()%>)</b>
					  </span>
	                </td>
	              </tr>
	              <tr>
	                <td>
   					  <table style="display: none;" id="<%=listId%>" border="0" cellspacing="1" cellpadding="3" class="foreground">
					<%
					  Iterator itemIterator = list.iterator();
					  int itemCount = 0;
					  String itemRowClass = null;
					  if (listRowClass.equalsIgnoreCase("white")) {
					    itemRowClass = "grey";
					  }
					  else {
					    itemRowClass = "white";
					  }
					  while (itemIterator.hasNext()) {
					    String item = (String)itemIterator.next();
					%>
					    <tr class="<%=itemRowClass%>">
					      <td>
						    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= item %>
						  </td>
						</tr>
	            <%
            		    itemCount = itemCount + 1;
					    if (itemRowClass.equalsIgnoreCase("white")) {
					      itemRowClass = "grey";
					    }
					    else {
					      itemRowClass = "white";
					    }
            		  }
	            %>
				  </table>
                </td>
              </tr>
       <%
            }
          }
        %>
      </table>

      <br/>
      <br/>
      <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
        <tr class="white"> 
        <%
          List legalValueSetList = SystemConfiguration.getLegalValueSets();
          headerInfo = "";
          if (!ApiFunctions.isEmpty(legalValueSetList)) {
            headerInfo = " (" + legalValueSetList.size() + ")";
          }
        %>
           <td nowrap><b>Legal Value Sets<%=headerInfo%></b></td>
        </tr>
        <%
          if (ApiFunctions.isEmpty(legalValueSetList)) {
        %>
            <tr class="white"> 
              <td nowrap>No legal value sets defined.</td>
            </tr>
        <%
          }
          else {
            int listCount = 0;
            Iterator listIterator = legalValueSetList.iterator();
            String listRowClass = null;
            while (listIterator.hasNext()) {
              LegalValueSet list = (LegalValueSet)listIterator.next();
		      String listId = "lvs" + listCount;
			  String imgId = listId + "Img";
			  String altText = "Open legal value set";
			  
			  request = (HttpServletRequest) pageContext.getRequest(); 
			  String contextPath = request.getContextPath(); 
			  String imgSource =  contextPath + "/images/MenuClosed.gif";	

			  if (listCount%2 == 0) {
			    listRowClass = "grey";
			  }
			  else {
			    listRowClass = "white";
			  }
			  listCount = listCount + 1;
        %>
              <tr class="<%=listRowClass%>">
                <td>
			      <span onClick="showLegalValueSetMembers('<%=listId%>','<%=imgId%>')" onKeyPress="showLegalValueSetMembers('<%=listId%>','<%=imgId%>')">
				    <img id="<%=imgId%>" alt="<%=altText%>" src="<%=imgSource%>"/>
					&nbsp;<b><%= list.getName() %>&nbsp;&nbsp;(<%=list.getCount()%>)</b>
					  </span>
	                </td>
	              </tr>
	              <tr>
	                <td>
   					  <table style="display: none;" id="<%=listId%>" border="0" cellspacing="1" cellpadding="3" class="foreground">
					<%
					  Iterator itemIterator = list.getIterator();
					  int itemCount = 0;
					  String itemRowClass = null;
					  if (listRowClass.equalsIgnoreCase("white")) {
					    itemRowClass = "grey";
					  }
					  else {
					    itemRowClass = "white";
					  }
					  while (itemIterator.hasNext()) {
					    LegalValue item = (LegalValue)itemIterator.next();
					%>
					    <tr class="<%=itemRowClass%>">
					      <td>
						    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=item.getDisplayValue()%> (<%=item.getValue()%>)
						  </td>
						</tr>
	            <%
            		    itemCount = itemCount + 1;
					    if (itemRowClass.equalsIgnoreCase("white")) {
					      itemRowClass = "grey";
					    }
					    else {
					      itemRowClass = "white";
					    }
            		  }
	            %>
				  </table>
                </td>
              </tr>
       <%
            }
          }
        %>
      </table>

      <br/>
      <br/>
      <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
        <tr class="white"> 
        <%
          List sampleTypeList = SystemConfiguration.getSampleTypes();
          headerInfo = "";
          if (!ApiFunctions.isEmpty(sampleTypeList)) {
            headerInfo = " (" + sampleTypeList.size() + ")";
          }
        %>
           <td nowrap><b>Sample Types<%=headerInfo%></b></td>
        </tr>
        <%
          if (ApiFunctions.isEmpty(sampleTypeList)) {
        %>
            <tr class="white"> 
              <td nowrap>No sample types defined.</td>
            </tr>
        <%
          }
          else {
            int sampleTypeCount = 0;
            Iterator sampleTypeIterator = sampleTypeList.iterator();
            String sampleTypeRowClass = null;
            while (sampleTypeIterator.hasNext()) {
              SampleType sampleType = (SampleType)sampleTypeIterator.next();
		      String sampleTypeId = "sampleType" + sampleTypeCount;
			  String imgId = sampleTypeId + "Img";
			  String altText = "Open sample type";
			  request = (HttpServletRequest) pageContext.getRequest(); 
			  String contextPath = request.getContextPath(); 
			  String imgSource =  contextPath +"/images/MenuClosed.gif";
			  if (sampleTypeCount%2 == 0) {
			    sampleTypeRowClass = "grey";
			  }
			  else {
			    sampleTypeRowClass = "white";
			  }
        %>
              <tr class="<%=sampleTypeRowClass%>">
                <td>
			      <span onClick="showSampleType('<%=sampleTypeId%>','<%=imgId%>')" onKeyPress="showSampleType('<%=sampleTypeId%>','<%=imgId%>')">
				    <img id="<%=imgId%>" alt="<%=altText%>" src="<%=imgSource%>"/>
					&nbsp;<b><%= sampleType.getCuiDescription()%></b>
				  </span>
	            </td>
	          </tr>
	          <tr>
	            <td>
   				  <table style="display: none;" id="<%=sampleTypeId%>StorageTypes" border="0" cellspacing="1" cellpadding="3" class="foreground">
   				  <%
   				  	String storageTypesRowClass = null;
					  if (sampleTypeRowClass.equalsIgnoreCase("white")) {
					    storageTypesRowClass = "grey";
					  }
					  else {
					    storageTypesRowClass = "white";
					  }
   				  %>
				      <%
				        if (ApiFunctions.isEmpty(sampleType.getStorageTypeList())) {
				      %>
				    <tr class="<%=storageTypesRowClass%>">
				      <td>
					    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No storage types defined.
			          </td>
			        </tr>
				      <%
				         } 
				         else {
					      String sampleTypeStorageTypesId = "sampleTypeStorageTypes" + sampleTypeCount;
						  imgId = sampleTypeStorageTypesId + "Img";
						  altText = "Open sample type storage types";
						  request = (HttpServletRequest) pageContext.getRequest(); 
						  contextPath = request.getContextPath(); 
						  imgSource =  contextPath +"/images/MenuClosed.gif";	
				      %>
				    <tr class="<%=storageTypesRowClass%>">
				      <td>
					    <span onClick="showSampleTypeStorageTypes('<%=sampleTypeStorageTypesId%>','<%=imgId%>')" onKeyPress="showSampleTypeStorageTypes('<%=sampleTypeStorageTypesId%>','<%=imgId%>')">
						  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img id="<%=imgId%>" alt="<%=altText%>" src="<%=imgSource%>"/>
						  <b>Storage Types&nbsp;(<%=sampleType.getStorageTypeList().size()%>)</b>
						</span>
			          </td>
			        </tr>
			        <tr>
			          <td>
		   				<table style="display: none;" id="<%=sampleTypeStorageTypesId%>" border="0" cellspacing="1" cellpadding="3" class="foreground">
						<%
						  Iterator storageTypeIterator = sampleType.getStorageTypeList().iterator();
						  int storageTypeCount = 0;
						  String storageTypeRowClass = null;
						  if (storageTypesRowClass.equalsIgnoreCase("white")) {
						    storageTypeRowClass = "grey";
						  }
						  else {
						    storageTypeRowClass = "white";
						  }
						  while (storageTypeIterator.hasNext()) {
						    StorageType storageType = (StorageType)storageTypeIterator.next();
						%>
						    <tr class="<%=storageTypeRowClass%>">
						      <td>
							    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							    <%= storageType.getCuiDescription() + " (" + storageType.getCui() + ")" %>
							  </td>
							</tr>
				      <%
	            		    storageTypeCount = storageTypeCount + 1;
						    if (storageTypeRowClass.equalsIgnoreCase("white")) {
						      storageTypeRowClass = "grey";
						    }
						    else {
						      storageTypeRowClass = "white";
						    }
	            		  }
	            	  %>
				        </table>
					  </td>
					</tr>
					<%
				      	 }
				      %>
				  </table>
                </td>
              </tr>
       <%
			  sampleTypeCount = sampleTypeCount + 1;
            }
          }
        %>
      </table>
      <br/>
      <br/>
      <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
        <tr class="white"> 
        <%
          List derivativeOperationList = SystemConfiguration.getDerivativeOperations();
          headerInfo = "";
          if (!ApiFunctions.isEmpty(derivativeOperationList)) {
            headerInfo = " (" + derivativeOperationList.size() + ")";
          }
        %>
           <td nowrap><b>Derivative Operations<%=headerInfo%></b></td>
        </tr>
        <%
          if (ApiFunctions.isEmpty(derivativeOperationList)) {
        %>
            <tr class="white"> 
              <td nowrap>No derivative operations defined.</td>
            </tr>
        <%
          }
          else {
            int derivativeOperationCount = 0;
            Iterator derivativeOperationIterator = derivativeOperationList.iterator();
            String derivativeOperationRowClass = null;
            while (derivativeOperationIterator.hasNext()) {
              DerivativeOperation derivativeOperation = (DerivativeOperation)derivativeOperationIterator.next();
		      String derivativeOperationId = "derivativeOperation" + derivativeOperationCount;
			  String imgId = derivativeOperationId + "Img";
			  String altText = "Open derivative operation";
			  request = (HttpServletRequest) pageContext.getRequest(); 
			  String contextPath = request.getContextPath(); 
			  String imgSource =  contextPath +"/images/MenuClosed.gif";	
			  if (derivativeOperationCount%2 == 0) {
			    derivativeOperationRowClass = "grey";
			  }
			  else {
			    derivativeOperationRowClass = "white";
			  }
        %>
              <tr class="<%=derivativeOperationRowClass%>">
                <td>
			      <span onClick="showDerivativeOperation('<%=derivativeOperationId%>','<%=imgId%>')" onKeyPress="showDerivativeOperation('<%=derivativeOperationId%>','<%=imgId%>')">
				    <img id="<%=imgId%>" alt="<%=altText%>" src="<%=imgSource%>"/>
					&nbsp;<b><%= derivativeOperation.getCuiDescription()%></b>
				  </span>
	            </td>
	          </tr>
	          <tr>
	            <td>
   			      <table style="display: none;" id="<%=derivativeOperationId%>" border="0" cellspacing="1" cellpadding="3" class="foreground">
   				  <%
   				  	String attributesRowClass = null;
					  if (derivativeOperationRowClass.equalsIgnoreCase("white")) {
					    attributesRowClass = "grey";
					  }
					  else {
					    attributesRowClass = "white";
					  }
   				  %>
				    <tr class="<%=attributesRowClass%>">
					  <td>
					    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Multiple Parents&nbsp;=&nbsp;<%= derivativeOperation.isMultipleParents() %>
					  </td>
					</tr>
				  </table>
				</td>
			  </tr>
	          <tr>
	            <td>
   				  <table style="display: none;" id="<%=derivativeOperationId%>SampleTypes" border="0" cellspacing="1" cellpadding="3" class="foreground">
   				  <%
					  if (attributesRowClass.equalsIgnoreCase("white")) {
					    attributesRowClass = "grey";
					  }
					  else {
					    attributesRowClass = "white";
					  }
   				  %>
				      <%
				        if (ApiFunctions.isEmpty(derivativeOperation.getParentSampleTypeList())) {
				      %>
				    <tr class="<%=attributesRowClass%>">
				      <td>
					    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No sample type information defined.
			          </td>
			        </tr>
				      <%
				         } 
				         else {
					      String derivationOperationSampleTypesId = "derivationOperationSampleTypes" + derivativeOperationCount;
						  imgId = derivationOperationSampleTypesId + "Img";
						  altText = "Open sample types";
						  request = (HttpServletRequest) pageContext.getRequest(); 
						  contextPath = request.getContextPath(); 
						  imgSource =  contextPath +"/images/MenuClosed.gif";	
				      %>
				    <tr class="<%=attributesRowClass%>">
				      <td>
					    <span onClick="showDerivationOperationSampleTypes('<%=derivationOperationSampleTypesId%>','<%=imgId%>')" onKeyPress="showDerivationOperationSampleTypes('<%=derivationOperationSampleTypesId%>','<%=imgId%>')">
						  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img id="<%=imgId%>" alt="<%=altText%>" src="<%=imgSource%>"/>
						  <b>Sample Type Information&nbsp;(<%=derivativeOperation.getParentSampleTypeList().size()%>)</b>
						</span>
			          </td>
			        </tr>
			        <tr>
			          <td>
		   				<table style="display: none;" id="<%=derivationOperationSampleTypesId%>" border="0" cellspacing="1" cellpadding="3" class="foreground">
						  <%
						    if (attributesRowClass.equalsIgnoreCase("white")) {
						      attributesRowClass = "grey";
						    }
						    else {
						      attributesRowClass = "white";
						    }
						  %>
		   				  <tr class="<%=attributesRowClass%>">
		   				    <td>
		   				      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Parent type(s)
		   				    </td>
		   				    <td>
		   				      Child type(s)
		   				    </td>
		   				  </tr>
						  <%
						    Iterator parentSampleTypeIterator = derivativeOperation.getParentSampleTypeList().iterator();
						    int sampleTypeCount = 0;
						    if (attributesRowClass.equalsIgnoreCase("white")) {
						      attributesRowClass = "grey";
						    }
						    else {
						      attributesRowClass = "white";
						    }
						    while (parentSampleTypeIterator.hasNext()) {
						      ParentSampleType parentSampleType = (ParentSampleType)parentSampleTypeIterator.next();
						  %>
						    <tr class="<%=attributesRowClass%>">
						      <td>
							    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							    <%= parentSampleType.getCuiDescription() + " (" + parentSampleType.getCui() + ")" %>
							  </td>
							  <td>
							    &nbsp;&nbsp;&nbsp;
						        <%
						          if (attributesRowClass.equalsIgnoreCase("white")) {
						            attributesRowClass = "grey";
						          }
						          else {
						            attributesRowClass = "white";
						          }
						          boolean includeComma = false;
						          Iterator childSampleTypeIterator = parentSampleType.getChildSampleTypeList().iterator();
						          while (childSampleTypeIterator.hasNext()) {
						            ChildSampleType childSampleType = (ChildSampleType)childSampleTypeIterator.next();
						            if (includeComma) {
						        %>
						              ,&nbsp;
						        <%
						            }
						        %>
							        <%= childSampleType.getCuiDescription() + " (" + childSampleType.getCui() + ")" %>
							    <%
							        includeComma = true;
							      }
							    %>
							  </td>
							</tr>
				      <%
						    if (attributesRowClass.equalsIgnoreCase("white")) {
						      attributesRowClass = "grey";
						    }
						    else {
						      attributesRowClass = "white";
						    }
	            		  }
	            	   %>
				        </table>
					  </td>
					</tr>
					<%
				      	 }
				      %>
				  </table>
                </td>
              </tr>
	            <%
            		    derivativeOperationCount = derivativeOperationCount + 1;
            		}
            	  }
	            %>
      </table>
  </div>
</body>
</html>
