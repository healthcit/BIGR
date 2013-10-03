<%@ page errorPage="/hiddenJsps/reportError/errorReport.jsp" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>

<html>
<head>
<SCRIPT language="JavaScript">

function scanUser() {
    event.returnValue = false; // for MR 5103
	document.forms[0].action = '<html:rewrite page="/lims/limsSetLocation.do"/>'; 
	document.forms[0].submit();
}

function scanSlide() {
    event.returnValue = false; // for MR 5103
	document.forms[0].action='<html:rewrite page="/lims/limsSetLocation.do"/>'; 
	document.forms[0].submit();
}

  function checkEnter(event){ 	
	var code = 0;
	code = event.keyCode;
	
	//alert(code);
  
	  if(code == 13){
 	    return false;
      }
  }


mybanner = 'Set Slide Location';

</SCRIPT>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>

<body class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML=mybanner;">

<html:form action="/lims/limsSetLocation.do">
  <div align="center">
  
    <table class="fgTableSmall" cellpadding="2" cellspacing="1" border="0">
            <logic:present name="org.apache.struts.action.ERROR">
     		<tr class="yellow"> 
       			<td colspan="2"> 
         		<div align="center">
           			<font color="#FF0000"><b><html:errors/></b></font>
         		</div>
       			</td>
     		</tr>
    		</logic:present>
    		<logic:present name="limsSetLocationForm" property="message">
     		<tr class="yellow"> 
       			<td colspan="2"> 
         		<div align="center">
           			<font color="#FF0000"><b><bean:write name="limsSetLocationForm" property="message"/></b></font>
         		</div>
       			</td>
     		</tr>
    		</logic:present>
            <tr id="header" class="white"> 
              <td class="grey"> 
                <div align="right">User ID Scan:</div>
              </td>
              <td> 
                <logic:present name="limsSetLocationForm" property="limsUserId">
               	  <b><bean:write name="limsSetLocationForm" property="limsUserId"/></b>
               	  <html:hidden name="limsSetLocationForm" property="limsUserId"/>
               </logic:present>
               <logic:notPresent name="limsSetLocationForm" property="limsUserId">
                  <html:password property="limsUserId" size="12" maxlength="12" onchange="scanUser();"/>
                  <script>document.forms[0].limsUserId.focus();</script>
               </logic:notPresent>
              </td>
             
            </tr>
            <tr id="header" class="white"> 
              <td class="grey"> 
                <div align="right">Slide Label Scan:</div>
              </td>
              <td> 
              
	            <logic:present name="limsSetLocationForm" property="limsUserId">
                	<logic:present name="limsSetLocationForm" property="slideId">
                		<bean:write name="limsSetLocationForm" property="slideId"/>
                		<html:hidden name="limsSetLocationForm" property="slideId"/>
                	</logic:present>
                	<logic:notPresent name="limsSetLocationForm" property="slideId">
                		<html:text property="slideId" size="12" maxlength="10" onchange="scanSlide();" onkeydown='return checkEnter(event);'/>
                		<script>document.forms[0].slideId.focus();</script>
                	</logic:notPresent>
                </logic:present>
                <logic:notPresent name="limsSetLocationForm" property="limsUserId">
                <html:text property="slideId" size="12" maxlength="12" onchange="scanSlide();" disabled="true"/>
                </logic:notPresent>
    
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right">Location:</div>
              </td>
              <td> 
              	<logic:present name="limsSetLocationForm" property="slideId">
                <html:select property="location">
                  
                  <html:options collection="locationChoices"
                  				property="value" labelProperty="label"/>
                </html:select>
                </logic:present>
              </td>
            </tr>
            <logic:present name="limsSetLocationForm" property="slideId">
	            <tr class="white"> 
	              <td colspan="2"> 
	                <div align="center"> 
	                  <input type="Submit" name="setLocation" value="Set Location">
	                </div>
	              </td>
	            </tr>
            </logic:present>
          </table>
        </td>
      </tr>
    </table>
  </div>
</html:form>
</body>
</html>
