<%@ page import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>

<html>
<head>
<SCRIPT LANGUAGE="JavaScript">
</SCRIPT>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<body class="bigr">
<html:form action="/lims/limsLabelGenPrintLabel.do">
 <div align="center">
   <table class="fgTableSmall" cellpadding="2" cellspacing="1" border="0">
   <logic:present name="limsLabelGenForm" property="message">
     <tr class="yellow"> 
       <td colspan="6"> 
         <div align="center">
           <font color="#FF0000"><b><ul><bean:write name="limsLabelGenForm" property="message" filter="false" /></ul></b></font>
         </div>
       </td>
     </tr> 
    </logic:present>
     <tr class="green" > 
       <td colspan="6"> 
         <div align="center"> 
           <b><font color="#000000">Click button to print slide labels</font></b>
         </div>
       </td>
     </tr>    
	<logic:present name="org.apache.struts.action.ERROR">
     <tr class="yellow"> 
       <td colspan="6"> 
         <div align="center">
           <font color="#FF0000"><b><html:errors/></b></font>
         </div>
       </td>
     </tr>
    </logic:present>
     <tr class="green"> 
       <td width="125" align="center"> 
         <b>Block Label</b>
       </td>
       <td width="125" align="center"> 
         <b>Slide Label</b>
       </td>
       <td width="90" align="center"> 
         <b>Procedure</b>
       </td>
       <td width="100" align="center"> 
         <b>Cut Level</b>
       </td>
       <td width="50" align="center"> 
         <b>Slide</b>
       </td>
       <td width="110" align="center"> 
         <b>Print Label</b>
       </td>
     </tr>
    <logic:present name="limsLabelGenForm" property="slideNumberList"> 
    <bean:define id="slideNumberArr" name="limsLabelGenForm" property="slideNumberList" type="java.lang.String[]" /> 
    <bean:define id="sampleArr" name="limsLabelGenForm" property="blockLabelList" type="java.lang.String[]" /> 
    <bean:define id="procedureArr" name="limsLabelGenForm" property="procedureList" type="java.lang.String[]" /> 
    <bean:define id="cutLevelArr" name="limsLabelGenForm" property="cutLevelList" type="java.lang.String[]" />
	<bean:define id="hasBeenPrintedArr" name="limsLabelGenForm" property="hasBeenPrinted" type="java.lang.String[]" />
	
    <logic:iterate id="slideArr" name="limsLabelGenForm" property="slideLabelList" indexId="index">    	
      <tr class="<%= (hasBeenPrintedArr[index.intValue()].equals("true")) ? "grey" :"white" %>" > 
        <td width="125" align="center"> 
      	  <%= sampleArr[index.intValue()]%>
		 <html:hidden property="blockLabelList" value="<%= sampleArr[index.intValue()]%>"/>		  
		 <html:hidden property="hasBeenPrinted" value="<%= hasBeenPrintedArr[index.intValue()] %>"/>
    	</td>
    	<td width="125" align="center"> 
      	  <bean:write name="slideArr" />
      	  <html:hidden property="slideLabelList" value="<%= slideArr.toString()%>"/>
    	</td>
    	<td width="90" align="center"> 
         <%= procedureArr[index.intValue()]%>
		 <html:hidden property="procedureList" value="<%= procedureArr[index.intValue()]%>"/>
    	</td>
    	<td width="100" align="center"> 
          <%= cutLevelArr[index.intValue()]%>
		  <html:hidden property="cutLevelList" value="<%= cutLevelArr[index.intValue()]%>"/>
    	</td>
    	<td width="50" align="center"> 
      	  <%= slideNumberArr[index.intValue()]%>
		  <html:hidden property="slideNumberList" value="<%= slideNumberArr[index.intValue()]%>"/>
    	</td>
    	<td width="110" align="center"> 
          <input type="submit" value="Print"  name="Print" onClick="document.forms[0].printOneSlide.value='<bean:write name="slideArr" />';return true;">
    	</td>      
      </tr>
    </logic:iterate>    
    </logic:present>  
      <tr class="white"> 
        <td colspan="6"> 
          <div align="center"> <b> 
			<input type="submit" value="Print Label(s)" onClick="document.forms[0].printAllSlides.value='true';">
            </b>
            <html:hidden name="limsLabelGenForm" property="printOneSlide"/>
            <html:hidden name="limsLabelGenForm" property="printAllSlides"/>
            <html:hidden name="limsLabelGenForm" property="limsUserId"/>
          </div>
        </td>
      </tr>
  </table>
 </div>
</html:form>
</body>
</html>
