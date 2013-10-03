<%@ page language="java" import="java.util.*,com.ardais.bigr.pdc.helpers.*" contentType="text/html; charset=WINDOWS-1252" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<HTML>
<HEAD>
<META http-equiv="Content-Type"	content="text/html; charset=WINDOWS-1252">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>Oce Update Summary</TITLE>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript">
<!--
	function validateForm()
	{
	 alert('No updates have been specified.');
	}
//-->
</script>
</HEAD>
<%
	OceHelper helper = (OceHelper)request.getAttribute("oceHelper");
	List searchResults = helper.getList();
	int returnedRowCount = 0;
	if (searchResults != null) {
		returnedRowCount = searchResults.size();
	}
%>
<BODY bgcolor="#FFFFFF">
<table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr >
      <td>
      	<table class="foreground" cellpadding="3" cellspacing="1" border="0" width="100%">          
          <tr class="green"> 
            <td colspan="3"> 
              <div align="left">
              	<b>
              	  <font size="2">
              	  	Table Name = <bean:write name="oceHelper" property="tableName" filter="false" />,
					Attribute = <bean:write name="oceHelper" property="attributeName" filter="false" />, 
					Status = <bean:write name="oceHelper" property="statusName" filter="false" />
			       <nbsp><nbsp><nbsp><nbsp><nbsp>(<%=returnedRowCount %> rows found)
              	  </font>
              	</b>
              </div>
            </td>
          </tr>
          <tr class="yellow"> 
            <td width="40%">
              <div align = "center"> 
              	<font size="2"><b>OTHER TEXT</b></font>
              </div>
            </td>
            <td width="40%">
			  <div align = "center">          
              	<font size="2"><b>FULLY SPECIFIED NAME</b></font>
              </div>	
            </td>
            <td width="20%">
			  <div align = "center">		            
              	<font size="2"><b>STATUS</b></font>
              </div>	
            </td>
          </tr>
         <logic:iterate name="oceHelper" property="list" id="list">
          <tr class="white"> 
            <td width="40%">
              <div align = "left">	
              	<font size="2">
              	  <bean:write name="list" property="otherText"/>
              </font>
             </div>
            </td>
            <td width="40%">
			 <div align = "left">		            
              <font size="2">
              	  <bean:write name="list" property="fullySpecifiedName"/>
              </font>
             </div> 
            </td>
            <td width="20%">
			 <div align = "center">	           
              <font size="2">
              	<bean:write name="list" property="status"/>
              </font>
             </div> 
            </td>
          </tr>
         </logic:iterate>
      	</table>
      </td>
    </tr>
</table>
</BODY>
</HTML>
