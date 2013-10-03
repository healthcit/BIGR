<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.lims.helpers.LimsConstants" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	LegalValueSet reasonSet = (LegalValueSet) request.getAttribute("reasonList"); 
	int reasonCount = reasonSet.getCount();
	LegalValueSet sourceSet = (LegalValueSet) request.getAttribute("reasonSource"); 
	int sourceCount;
	if (sourceSet != null) {
	  sourceCount = sourceSet.getCount();
	}
	else {
	  sourceCount = 0;
	}
	String actionCode = (String) request.getAttribute("actionCode");
%>
<html>
<head>
<title>
  Enter Reason 
  <logic:present name="reasonText">  
  	for 
    <bean:write name="reasonText"/>
  </logic:present> 
</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>

  //method to enable/disable the "additional info" textbox for a reason checkbox
  function handleReasonClick(index) {
    if (<%= reasonCount %> == 1) {
      if (document.forms[0].reasonCheckBox.checked) {
        document.forms[0].otherText.disabled=false;
        document.forms[0].otherText.focus();
      }
      else {
        document.forms[0].otherText.disabled=true;
        document.forms[0].otherText.value="";
      }
    }
    else {
      if (document.forms[0].reasonCheckBox[index].checked) {
        document.forms[0].otherText[index].disabled=false;
        document.forms[0].otherText[index].focus();
        //if this is the "% in wrong field" checkbox clear and disable the additional checkboxes
        if (document.forms[0].reasonCode[index].value == "<%= LimsConstants.INCIDENT_RE_PV_REASON_WRONG_FIELD %>") {
          document.forms[0].WRONGFIELDNORMAL.disabled = false;
          document.forms[0].WRONGFIELDLESION.disabled = false;
          document.forms[0].WRONGFIELDTUMOR.disabled = false;
          document.forms[0].WRONGFIELDCELLSTROMA.disabled = false;
          document.forms[0].WRONGFIELDHYPOSTROMA.disabled = false;
          document.forms[0].WRONGFIELDNECROSIS.disabled = false; 
        }
      }
      else {
        document.forms[0].otherText[index].disabled=true;
        document.forms[0].otherText[index].value="";
        //if this is the "% in wrong field" checkbox clear and disable the additional checkboxes
        if (document.forms[0].reasonCode[index].value == "<%= LimsConstants.INCIDENT_RE_PV_REASON_WRONG_FIELD %>") {
          document.forms[0].WRONGFIELDNORMAL.checked = false;
          document.forms[0].WRONGFIELDNORMAL.disabled = true;
          document.forms[0].WRONGFIELDLESION.checked = false;
          document.forms[0].WRONGFIELDLESION.disabled = true;
          document.forms[0].WRONGFIELDTUMOR.checked = false;
          document.forms[0].WRONGFIELDTUMOR.disabled = true;
          document.forms[0].WRONGFIELDCELLSTROMA.checked = false;
          document.forms[0].WRONGFIELDCELLSTROMA.disabled = true;
          document.forms[0].WRONGFIELDHYPOSTROMA.checked = false;
          document.forms[0].WRONGFIELDHYPOSTROMA.disabled = true;
          document.forms[0].WRONGFIELDNECROSIS.checked = false;
          document.forms[0].WRONGFIELDNECROSIS.disabled = true;      
        }
      }
    }
  }

  //method to validate that a reason was entered
  function checkReason() {
  	var otherReason_otherText = false;
    var reasonChecked = false;
    var sourceIndicated = false;
    var sourceRequired = false;
    var sourceCode = "";
    var sourceText = "";
    //see if at least one reason was selected
    if (<%= reasonCount %> == 1) {
      reasonChecked = document.forms[0].reasonCheckBox.checked;
    }
    else {
      for (var i=0; i<<%= reasonCount %>; i++) {
        if (document.forms[0].reasonCheckBox[i].checked) {
          reasonChecked = true;
          // MR 6141 if Other reason chosen, must fill in additional info...
          if (document.forms[0].reasonCheckBox[i].value == "Other reason") {
          	if (document.forms[0].otherText[i].value == "") {
	          	otherReason_otherText = true;
	          	}
          	}
          break;
        }
      }
    }
    //if a source is required, see if a source was selected
    var sourceRequired = (<%= actionCode.equals(LimsConstants.INCIDENT_ACTION_REPV) %>);
    if (sourceRequired) {
      if (<%= sourceCount %> == 1) {
        sourceIndicated = document.forms[0].sourceRadioButton.checked
        sourceCode = document.forms[0].sourceRadioButton.value;
        sourceText = document.forms[0].sourceDisplayValue.value;
      }
      else {
        for (var i=0; i<<%= sourceCount %>; i++) {
          if (document.forms[0].sourceRadioButton[i].checked) {
            sourceIndicated = true;
            sourceCode = document.forms[0].sourceRadioButton[i].value;
            sourceText = document.forms[0].sourceDisplayValue[i].value;
            break;
          }
        }
      }
    }
    //if a reason or source (when required) has not been specified, tell the user and return

    if (!reasonChecked || (sourceRequired && !sourceIndicated) || (otherReason_otherText)) {
      var msg = "The following information must be specified: ";
      var commaNeeded = false;
      if (!reasonChecked) {
        if (commaNeeded) {
          msg = msg + ", ";
        }
       	msg = msg + "reason";
       	commaNeeded = true;
      }
      if (sourceRequired && !sourceIndicated) {
        if (commaNeeded) {
          msg = msg + ", ";
        }
       	msg = msg + "source";
       	commaNeeded = true;
      }
      if (otherReason_otherText) {
      	msg = msg + "when selecting Other reason, you must fill in additional info";
      }
      alert(msg);
      return;
    }
    //build the reason and return an object holding the reason, and the code for the source (if required)
    var reason = buildReason();
    if (sourceText != null && sourceText != "") {
      reason = sourceText + " (" + reason + ")";
    }
    window.returnValue = new reasonObject(reason, sourceCode);
    window.close();
  }
  
  function buildReason() {
    var reason = "";
    var semicolonNeeded = false;
    //if there are multiple reason choices there will be an array of text and codes,
    //otherwise there will just be one
    if (<%= reasonCount %> > 1) {
      for (var i=0; i<<%= reasonCount %>; i++) {
        if (document.forms[0].reasonCheckBox[i].checked) {
          if (semicolonNeeded) {
            reason = reason + "; "
          }
          reason = reason + document.forms[0].reasonCheckBox[i].value;
          //if this is the "% in wrong field" checkbox capture the text from the additional checkboxes
          //that have been checked, if any
          if (document.forms[0].reasonCode[i].value == "<%= LimsConstants.INCIDENT_RE_PV_REASON_WRONG_FIELD %>") {
            var subString = getRePVWrongFieldFieldText();
            if (subString != "") {
              reason = reason + " [" + subString + "]";
            }
          }
          //get any additional info that has been specified
          var otherText = document.forms[0].otherText[i].value;
          if (otherText != "") {
            reason = reason + " - " + otherText;
          }
          semicolonNeeded = true;
        }
      }
    }
    else {
      if (document.forms[0].reasonCheckBox.checked) {
        reason = reason + document.forms[0].reasonCheckBox.value;
        //if this is the "% in wrong field" checkbox capture the text from the additional checkboxes
        //that have been checked, if any
        if (document.forms[0].reasonCode.value == "<%= LimsConstants.INCIDENT_RE_PV_REASON_WRONG_FIELD %>") {
          var subString = getRePVWrongFieldFieldText();
          if (subString != "") {
            reason = reason + " [" + subString + "]";
          }
        }
        //get any additional info that has been specified
        var otherText = document.forms[0].otherText.value;
        if (otherText != "") {
          reason = reason + " - " + otherText;
        }
      }
    }
    return reason;
  }
  
  //method to capture the text that should be included in the re-PV return reason
  //for the additional checkboxes under the "wrong %" checkbox
  function getRePVWrongFieldFieldText() {
    var subString = "";
    var commaNeeded = false;
    if (document.forms[0].WRONGFIELDNORMAL.checked) {
      if (commaNeeded) {
        subString = subString + ", ";
      }
      subString = subString + document.forms[0].WRONGFIELDNORMAL.value;
      commaNeeded = true;
    }
    if (document.forms[0].WRONGFIELDLESION.checked) {
      if (commaNeeded) {
        subString = subString + ", ";
      }
      subString = subString + document.forms[0].WRONGFIELDLESION.value;
      commaNeeded = true;
    }
    if (document.forms[0].WRONGFIELDTUMOR.checked) {
      if (commaNeeded) {
        subString = subString + ", ";
      }
      subString = subString + document.forms[0].WRONGFIELDTUMOR.value;
      commaNeeded = true;
    }
    if (document.forms[0].WRONGFIELDCELLSTROMA.checked) {
      if (commaNeeded) {
        subString = subString + ", ";
      }
      subString = subString + document.forms[0].WRONGFIELDCELLSTROMA.value;
      commaNeeded = true;
    }
    if (document.forms[0].WRONGFIELDHYPOSTROMA.checked) {
      if (commaNeeded) {
        subString = subString + ", ";
      }
      subString = subString + document.forms[0].WRONGFIELDHYPOSTROMA.value;
      commaNeeded = true;
    }
    if (document.forms[0].WRONGFIELDNECROSIS.checked) {
      if (commaNeeded) {
        subString = subString + ", ";
      }
      subString = subString + document.forms[0].WRONGFIELDNECROSIS.value;
      commaNeeded = true;
    }
    return subString;
  }
  
  //method to create an object to hold the information returned from this form
  function reasonObject(reasonText, requestorCode) {
  	this.reasonText = reasonText;
  	this.requestorCode = requestorCode;
  }
</script>

</head>
<body>
<form name="reason" method="post">
  <br>
  <div align="center"> 
     <table border="0" cellspacing="1" cellpadding="1" class="fgTableSmall">          
       <tr class="yellow"> 
         <td colspan="3"> 
           <div align="center">
             <b>Enter Reason
               <logic:present name="reasonText">
                 for
                 <bean:write name="reasonText"/>
               </logic:present> 
             </b>
           </div>
         </td>
       </tr>
         <logic:present name="reasonList" >
		   <logic:iterate name="reasonList" indexId="index" property="iterator" id="legalValue">
		     <tr class="white">
			   <td align="center" nowrap>
			     <input type=checkbox name="reasonCheckBox" 
			       value="<%= ((com.ardais.bigr.iltds.assistants.LegalValue)legalValue).getDisplayValue() %>"
			       onClick="handleReasonClick(<%= index.intValue() %>);">
			     <input type=hidden name="reasonCode" value="<%= ((com.ardais.bigr.iltds.assistants.LegalValue)legalValue).getValue() %>">
			   </td>
			   <td nowrap>
			     <bean:write name="legalValue" property="displayValue"/>
			  	 <% if (((com.ardais.bigr.iltds.assistants.LegalValue)legalValue).getValue().equals(LimsConstants.INCIDENT_RE_PV_REASON_WRONG_FIELD)) { %>
			  	   <br>Select Field(s): <br>
				   <input type="checkbox" disabled name="WRONGFIELDNORMAL" value="Normal">Normal<br>
				   <input type="checkbox" disabled name="WRONGFIELDLESION" value="Lesion">Lesion<br>
				   <input type="checkbox" disabled name="WRONGFIELDTUMOR" value="Tumor">Tumor<br>
				   <input type="checkbox" disabled name="WRONGFIELDCELLSTROMA" value="Cellular Stroma">Cellular Stroma<br>
				   <input type="checkbox" disabled name="WRONGFIELDHYPOSTROMA" value="Hypo/Acellular Stroma">Hypo/Acellular Stroma<br>
				   <input type="checkbox" disabled name="WRONGFIELDNECROSIS" value="Necrosis">Necrosis<br>
				 <% } %>
			   </td>
			   <td nowrap>
      			 Additional info: <input type="text" name="otherText" maxlength="200" size="40" disabled value="" style="font-size:xx-small">
			   </td>
			 </tr>
		   </logic:iterate>
		 </logic:present>
        <logic:present name="reasonSource" >
	      </table>
	      <br>
          <div align="center"> 
            <table border="0" cellspacing="1" cellpadding="1" class="fgTableSmall">          
              <tr class="yellow">
                <td>
                  <div align="center">
                    <b>Enter Source
                    <logic:present name="reasonText">
                      for
                      <bean:write name="reasonText"/>
                    </logic:present> 
                    </b>
                  </div>
                </td>
              </tr>
		      <logic:iterate name="reasonSource" property="iterator" id="legalValue">
                <tr>
                  <td style="border: none">
			        <input type=radio name="sourceRadioButton" value="<%= ((com.ardais.bigr.iltds.assistants.LegalValue)legalValue).getValue() %>">
			        <bean:write name="legalValue" property="displayValue"/>
			        <input type=hidden name="sourceDisplayValue" value="<%= ((com.ardais.bigr.iltds.assistants.LegalValue)legalValue).getDisplayValue() %>">
			      </td>
			    </tr>
		      </logic:iterate>
            </table>
          </div>
		</logic:present>
	<br>
          <div align="center"> 
			<input type="button" name="Confirm" value="Confirm" onClick="checkReason();">&nbsp;
		    <input type="button" name="Cancel" value="Cancel" onClick="window.close()">
          </div>
</div>
</form>
</body>
</html>

