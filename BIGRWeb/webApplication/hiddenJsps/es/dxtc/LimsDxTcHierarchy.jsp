<%@ page language="java" import="java.text.*,java.util.*,com.ardais.bigr.api.*" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.gulfstreambio.gboss.GbossValueSet"%>
<%@ page import="com.gulfstreambio.gboss.GbossValue"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<html>
<head>
<title><% if(request.getParameter("type").equals("D")) { %>Diagnosis
Hierarchy<% } else { %>Lesion Hierarchy<% } %></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">
<script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"> </script>
<script>
<!--
var menuIds = null;
var checkedArray = new Array();

    var excludeSummaryCheckBoxes=true

//method to hide/show a particular part of the hierarchy
function swapMenu(object) {
    var objString = object.toString();
    var representativeItem = document.getElementById(objString);
    if (representativeItem) {
	var isItemDisplayed = (representativeItem.style.display != 'none');
	if (isItemDisplayed) { hideMenu(objString); } else { showMenu(objString); }
    }
}

//method to show a part of the hierarchy
function showMenu(objString) {
    var menuItems = document.getElementsByName(objString);
    setImage("Image_" + objString, '<html:rewrite page="/images/MenuOpened.gif"/>');
    for (j = 0; j < menuItems.length; j++) {
        menuItems[j].style.display = 'inline';
    }
}

//method to hide a part of the hierarchy
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

//method to set the image for a part of the hierarchy
function setImage(imageElementId, imageSrc) {
    document.getElementById(imageElementId).src = imageSrc;
}

//method to toggle on/off the selection of a part of the hierarchy
function changecheck(theID, theBool){
    checkBoxes = document.getElementsByName(theID.toString());
    for(i=0; i<checkBoxes.length;i++){
        checkBoxes[i].checked = theBool;
        updateCheckedArray(checkBoxes[i]);
    }
}

//method to manage the array containing all checked check boxes
function updateCheckedArray(cb) {
   if (cb.type == "checkbox") {
      if (cb.checked) {
         addToCheckedArray(cb);
      } 
      else {
         removeFromCheckedArray(cb);
      }
   }
}

//method to add a check box to the array containing all checked check boxes
function addToCheckedArray(cb) {
   //alert("Adding cb with value " + cb.value + " to array");
   var inArray = false;
   for (var i=0; i<checkedArray.length; i++) {
      if (cb.value == checkedArray[i].value) {
         inArray = true;
         break;
      }
   }
   if (!inArray) {
      checkedArray[checkedArray.length] = cb;
   }
}

//method to remove a check box from the array containing all checked check boxes
function removeFromCheckedArray(cb) {
   //alert("Removing cb with value " + cb.value + " from array");
   //var vals = "";
   //for (var i=0; i<checkedArray.length; i++) {
   //  vals = vals + checkedArray[i].value + ", ";
   //}
   //alert("Array before: " + vals);
   for (var i=0; i<checkedArray.length; i++) {
      if (cb.value == checkedArray[i].value) {
         checkedArray.splice(i,1);
         break;
      }
   }
   //vals = "";
   //for (var i=0; i<checkedArray.length; i++) {
   //  vals = vals + checkedArray[i].value + ", ";
   //}
   //alert("Array after: " + vals);
}

//function to get all of the selected checkboxes (optionally excluding the 
//summary checkboxes i.e. "Disease of Breast", "Disease of Skin", etc)
function returnSelectedChoices() {
    //validate other value
    var span = document.getElementById('otherSpan');
    if (span != null) {
    	for (var j=0; j<span.children.length; j++) {
		if (span.children[j].type == "checkbox") {
			var box = span.children[j];
			if(box.checked == true){
				if(trim(document.all.otherValue.value) == ""){
					alert("Please enter a value for Other.");
					return false;
				}
			}
		}
		}
	}
    
    var returnValues = new Array();
    var count = 0;
    for (var i=0; i<checkedArray.length; i++) {
      var cbName = checkedArray[i].name;
      if (!excludeSummaryCheckBoxes || (excludeSummaryCheckBoxes && cbName.indexOf("_summary") <= 0)) {
        returnValues[count] = trim(checkedArray[i].value);
        count = count + 1;
      }
    }
    window.returnValue = returnValues;
    //alert("Returning array - length = " + returnValues.length + ", values = " + returnValues.toString());
    closeWindow();
}

//return the unchanged argument list if the window is closed
function cancelWindow(){
	window.returnValue = dialogArguments;
	closeWindow();
}

function closeWindow(){
  window.close();
}

function checkEnter(event){ 	
	var code = 0;
	code = event.keyCode;
	
	//alert(code);
  
	  if(code == 13){
 	    return false;
      }
}

//function to clear all of the selected checkboxes
function clearSelectedChoices() {
    for (var i=0; i<checkedArray.length; i++) {
      checkedArray[i].checked = false;
    }
    checkedArray = new Array();
}

//function to check the checkboxes that should be checked when the form is
//first brought up as a modal form
function initializeCheckBoxes() {
    //alert("Entering initializeCheckBoxes");
    var form = window.document.forms[0];
    for (var i=0; i<dialogArguments.length; i++) {
       var val = dialogArguments[i];
       //get the span element surrounding the checkbox we want to mark checked.  It will
       //have an id equal to the checkbox's value (i.e. the ARTS code)
       var span = document.getElementById(val);
       if (span != null) {
          //alert("Found span with id = " + val + " - it has " + span.children.length + " children");
          //walk through the children.  If the child is a checkbox mark it checked
          for (var j=0; j<span.children.length; j++) {
             //alert("Child has type = " + span.children[j].type);
             if (span.children[j].type == "checkbox") {
                span.children[j].checked = true;
                updateCheckedArray(span.children[j]);
                break;
             }
          }
          //get the id of the tr containing the span, and call showMenu on all of the components
          //of it's id to expand the menus that contain it.  So an id of "2.0"
          //would result in a call to showMenu(2) and then a call to showMenu(2.0).
          var id = span.parentElement.parentElement.id;
          var currentId = "";
          var periodLoc = 0;
          while (periodLoc > -1) {
            periodLoc = id.indexOf(".", periodLoc+1);
            if (periodLoc == -1) {
              currentId = id;
            } else {
              currentId = id.substr(0,periodLoc);
            }
            //alert("Expanding line with id = " + currentId);
            showMenu(currentId);
          }
       }
       else {
          //alert("Unable to find span with id = " + val);
       }
    }
}

function changeOtherValue(obj, val, text){
	
    var form = window.document.forms[0];
    var span = document.getElementById('otherSpan');
    if (span != null) {
    	for (var j=0; j<span.children.length; j++) {
		if (span.children[j].type == "checkbox") {
			var tokens = span.children[j].value.split('!#!#');
			
			var code = tokens[1];
			span.children[j].value = val + '!~!~other!#!#' + code;
//			alert("alert new other value " + span.children[j].value);
		}
	}
    }
}

<%
  Vector hierarchy = null;
  GbossValueSet valueSet = null;
  valueSet = (GbossValueSet) request.getAttribute("valueSet");
  if (valueSet == null) {
    hierarchy = (Vector) request.getAttribute("dxHierarchy");
    if (hierarchy == null) {
      hierarchy = (Vector) request.getAttribute("tcHierarchy");
    }
  }

  Set menuIds = new HashSet();
  int topLevelCount = 0;
  if (valueSet != null) {
    List values = valueSet.getValues();
    topLevelCount = values.size();
	  for (int i = 0; i < topLevelCount; i++) {
	    GbossValue value = (GbossValue)values.get(i);
	    List children = value.getValues();
      menuIds.add(String.valueOf(i));
      for (int j = 0; j < children.size(); j++) {
      	menuIds.add(String.valueOf(i) + "." + String.valueOf(j));
      }
  	}
  }
  else if (hierarchy != null) {
    topLevelCount = hierarchy.size();
	  for (int i = 0; i < hierarchy.size(); i++) { 
	      Vector level1 = (Vector) hierarchy.get(i);
	      Vector level2 = (Vector) level1.get(1);
	
	      menuIds.add(String.valueOf(i));
	
	      for (int j = 0; j < level2.size(); j++) {
	         Vector level3 = (Vector) level2.get(j);
	         String sub_menu = (String) level3.get(0);
	
	         if(!sub_menu.equals("SUB_MENU")) {
	           menuIds.add(String.valueOf(i) + "." + String.valueOf(j));
	         }
	      }
	  }
  }
%>

//method to make sure this form is brought up as a modal popup and to
//initialize the page once it's been loaded
function initPage() {
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
  
  window.returnValue = new Array();
  try {
    document.all.VisibleStuff.style.display = 'inline';
    
    if (dialogArguments) {
      window.returnValue = dialogArguments;
      initializeCheckBoxes();
    }
  }
  catch (e) {
     alert("Developer error - this window is intended for modal use only.  The window will now close.");
     window.close();
  }
}

//-->
</script>

<link rel="stylesheet" type="text/css"
	href="<html:rewrite page="/css/bigr.css"/>">

</head>
<body class="bigr" onload="initPage();focus();">
<div id="VisibleStuff" style="display: none">
<form name="selects">
<% 	int tabIndex = 1; %>
<p align="center"><input type="button" name="Continue" value="Continue"
	onClick="returnSelectedChoices();" tabIndex="<%= tabIndex++ %>"> <input type="button" name="Clear"
	value="Clear All" onClick="clearSelectedChoices();" tabIndex="<%= tabIndex++ %>"> <input
	type="button" name="Cancel" value="Cancel" onClick="cancelWindow()" tabIndex="<%= tabIndex++ %>"></p>

<table border="0" cellspacing="0" cellpadding="0" class="background"
	width="100%">
	<tr class="darkgreen">
		<td>
		<table border="0" cellspacing="1" cellpadding="0" class="foreground"
			width="100%">
			<tr class="green">
				<td class="level_1">
				<div align="center"></div>
				</td>
				<td>Select</td>
			</tr>
			<%  
		    String type = request.getParameter("type");
              String excludeGroups = request.getParameter("excludeGroups");
  		  Vector excludeGroupVals = new Vector();
              if (excludeGroups != null && !excludeGroups.trim().equals("")) {
                  StringTokenizer st = new StringTokenizer(excludeGroups,",");
                  while (st.hasMoreTokens()) {
                    excludeGroupVals.add(st.nextToken().trim());
                  }
              }

              String expandGroups = request.getParameter("expandGroups");
  		  Vector expandGroupVals = new Vector();
              if (expandGroups != null && !expandGroups.trim().equals("")) {
                  StringTokenizer st = new StringTokenizer(expandGroups,",");
                  while (st.hasMoreTokens()) {
                    expandGroupVals.add(st.nextToken().trim());
                  }
              }

              for (int i = 0; i < topLevelCount; i++) {
                String level1_label;
                String level1_key;
                if (valueSet != null) {
                  GbossValue value = (GbossValue)valueSet.getValues().get(i);
                  level1_label = value.getDescription();
                  level1_key = level1_label;
                }
                else {
                  Vector level1 = (Vector) hierarchy.get(i);
                  level1_label = (String) level1.get(0);
                  level1_key = level1_label;
                }
                boolean level1_open = expandGroupVals.contains(level1_label);

      	  if (excludeGroupVals.contains(level1_label)) {
                continue;
              }
          %>
			<tr class="<%= (type.equals("D") || type.equals("L")  || type.equals("S") || type.equals("F")) ? "yellow" : "green" %>">
				<td class="level_1" tabIndex="<%= tabIndex++ %>" onKeyPress="swapMenu('<%= i %>')">
				<div onClick="swapMenu('<%= i %>')"><img
					src="<html:rewrite page="/images/"/>Menu<%= (level1_open ? "Opened" : "Closed") %>.gif"
					id="Image_<%= i %>"><%= level1_label %></div>
				</td>
				<td> &nbsp; </td>
			</tr>
			<% 
							int level2Size = 0;
			        if (valueSet != null) {
			          GbossValue value = (GbossValue)valueSet.getValues().get(i);
			          level2Size = value.getValues().size();
			        }
			        else {
			          Vector level1 = (Vector) hierarchy.get(i);
			          level2Size = ((Vector) level1.get(1)).size();
			        }
				      
               for (int j = 0; j < level2Size; j++) {
                 String sub_menu = null;
                 String level2_label = null;
                 String level2_key = null;
                 Vector option_values2 = null;
                 if (valueSet == null) {
   			           Vector level1 = (Vector) hierarchy.get(i);
   			           Vector level2 = (Vector) level1.get(1);
                   Vector level3 = (Vector) level2.get(j);
                   sub_menu = (String) level3.get(0);
                   option_values2 = (Vector) level3.get(1);
                 }
                   if(sub_menu.equals("SUB_MENU")) {
                       Vector option_value = (Vector) option_values2.get(0);
                       level2_label = (String) option_value.get(0);
                       level2_key = (String) option_value.get(1);
            %>
			<tr class="<%= ((j % 2) == 0) ? "white" : "grey" %>" id="<%= i %>"
				<%= (level1_open ? "" : "style=\"display: none;\"") %>>
				<td class="level_2"><%= level2_label %></td>
				<td><span id="<%= level2_key %>">
					<%         	if(level2_label.startsWith("Other") || level2_label.startsWith("other")){ %>
                           <input type="checkbox"  tabIndex="<%= tabIndex++ %>" name="<%= i %>" value="<%= level2_label + "!#!#" + level2_key %>"
                                  onClick="changecheck('<%=i%>_summary', false);updateCheckedArray(this);document.forms[0].otherValue.disabled=false;document.forms[0].otherValue.value='';document.forms[0].otherValue.focus();">
                    <% } else { %>
                    		<input type="checkbox"  tabIndex="<%= tabIndex++ %>" name="<%= i %>" value="<%= level2_label + "!#!#" + level2_key %>"
                                  onClick="changecheck('<%=i%>_summary', false);updateCheckedArray(this);">
                    
                    <% } %>
                         </span>
                         
                         </td>
                       </tr>
                       <%         	if(level2_label.startsWith("Other") || level2_label.startsWith("other")){ %>
	    		<tr class="<%= ((j % 2) == 0) ? "white" : "grey" %>" id="<%= i %>"  style="display:none;"> 
                           <td class="level_3"><input type="text" tabIndex="<%= tabIndex++ %>" name="otherValue" value="N/A" maxlength="200" size="30" onBlur="changeOtherValue('<%= level2_key %>', this.value, this)" disabled></td>
                           <td>
                             <span id="<%= level2_key %>">
                               
                             </span>
                           </td>
                         </tr>
                       
            <%    }
            		 } else { // not "SUB_MENU"

		                   if (valueSet != null) {
		                     GbossValue value = (GbossValue)((GbossValue)valueSet.getValues().get(i)).getValues().get(j);
		                     level2_label = value.getDescription();
		                     level2_key = level2_label;
		                   }
		                   else {
		     			           Vector level1 = (Vector) hierarchy.get(i);
		     			           Vector level2 = (Vector) level1.get(1);
		                     Vector level3 = (Vector) level2.get(j);
	            		       level2_label = (String) level3.get(0);
	                       level2_key = level2_label;
		                   }
            		   
            %>
                       <tr class="green" id="<%= i %>" <%= (level1_open ? "" : "style=\"display: none;\"") %>> 
                         <td class="level_2" tabIndex="<%= tabIndex++ %>" onKeyPress="swapMenu('<%= i %>.<%= j %>');"> 
                           <div onClick="swapMenu('<%= i %>.<%= j %>');">
                             <img src="<html:rewrite page="/images/MenuClosed.gif"/>" id="Image_<%= i %>.<%= j %>"><%= level2_label %>
                           </div>
                         </td>
                         <td> 
                           <span id="<%= level2_key %>">
                           
                           </span>
                         </td>
                       </tr>
            <%
                        int level3Size = 0;
             			      if (valueSet != null) {
 		                      GbossValue value = (GbossValue)((GbossValue)valueSet.getValues().get(i)).getValues().get(j);
            			        level3Size = value.getValues().size();
            			      }
            			      else {
								          Vector level1 = (Vector) hierarchy.get(i);
								          Vector level2 = (Vector) level1.get(1);
						              Vector level3 = (Vector) level2.get(j);
						              option_values2 = (Vector) level3.get(1);
            			        level3Size = option_values2.size();
            			      }
                       for (int k = 0; k < level3Size; k++) {
                         String level3_label = null;
                         String level3_key = null;
                         if (valueSet == null) {
 								           Vector level1 = (Vector) hierarchy.get(i);
								           Vector level2 = (Vector) level1.get(1);
						               Vector level3 = (Vector) level2.get(j);
						               option_values2 = (Vector) level3.get(1);
                           Vector o_v = (Vector) option_values2.get(k);
                           level3_label = (String) o_v.get(0);
                           level3_key = (String) o_v.get(1);
                         }
                         else {
  		                      GbossValue value = (GbossValue)((GbossValue)((GbossValue)valueSet.getValues().get(i)).getValues().get(j)).getValues().get(k);
                            level3_label = value.getDescription();
                            level3_key = value.getCui();
                         }
            %>
                         <tr class="<%= ((k % 2) == 0) ? "white" : "grey" %>" id="<%= i %>.<%= j %>" style="display: none;" > 
                           <td class="level_3"><%= level3_label %></td>
                           <td>
                             
                              <%         	if(level3_label.startsWith("Other") || level3_label.startsWith("other")){ %>
                              <span id="otherSpan">
                               <input type="checkbox" tabIndex="<%= tabIndex++ %>" name="<%= i %>_<%= j %>" value="<%= level3_label + "!#!#" + level3_key %>"
                                onClick="changecheck('<%=i%>_<%=j%>_summary', false);updateCheckedArray(this);document.forms[0].otherValue.disabled=false;document.forms[0].otherValue.value='';document.forms[0].otherValue.focus();">
                             	</span>
                                <% } else {%>
                                <span id="<%= level3_key %>">
                                <input type="checkbox" tabIndex="<%= tabIndex++ %>" name="<%= i %>_<%= j %>" value="<%= level3_label + "!#!#" + level3_key %>"
                                onClick="changecheck('<%=i%>_<%=j%>_summary', false);updateCheckedArray(this);">
                                </span>
                                <% } %>
                             
                           </td>
                         </tr>
	    <%         	if(level3_label.startsWith("Other") || level3_label.startsWith("other")){ %>
	    		<tr class="<%= ((k % 2) == 0) ? "white" : "grey" %>" id="<%= i %>.<%= j %>"  style="display:none;"> 
                           <td class="level_3"><input type="text" tabIndex="<%= tabIndex++ %>" name="otherValue" maxlength="200" value="N/A" size="30" onKeyDown="return checkEnter(event);" onBlur="changeOtherValue('<%= level3_key%>', this.value, this)" disabled></td>
                           <td>
                             <span id="<%= level3_key %>">
                               
                             </span>
                           </td>
                         </tr>
	    
	    
	    <%		}
            	         } // end for k
                   } // end else not "SUB_MENU"
               } // end for j
             } // end for i
            %>
        </table>
      </td>
    </tr>
  </table>

<p align="center"> 
<input type="button" name="Continue" value="Continue" onClick="returnSelectedChoices();" tabIndex="<%= tabIndex++ %>">
<input type="button" name="Clear" value="Clear All" onClick="clearSelectedChoices();" tabIndex="<%= tabIndex++ %>">
<input type="button" name="Cancel" value="Cancel" onClick="cancelWindow()" tabIndex="<%= tabIndex++ %>">
</p>
</form>
<script>
window.focus();
</script>
</div>
</body>
</html>
