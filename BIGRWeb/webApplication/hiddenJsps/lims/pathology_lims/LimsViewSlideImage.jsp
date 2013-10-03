<%@ page language="java" contentType="text/html; charset=WINDOWS-1252" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"   prefix="bean" %>
<%@ taglib uri="/tld/struts-logic"  prefix="logic"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>View Slide Image</TITLE>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</HEAD>
<BODY>
<div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            
          </table>
        </td>
      </tr>
    </table>
  </div>
  <p>

  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <tr class="white"> 
              <td class="yellow" align="right"><b>Slide ID</b></td>
              <td>                
                <logic:present name="image" property="slideId">
                <bean:write  name="image" property="slideId" filter="false"/>
                </logic:present>                
              </td>
              <td class="yellow" align="right"><b>Magnification</b></td>
              <td>
                <logic:present name="image" property="magnification">
                <bean:write  name="image" property="magnification" filter="false"/>
                </logic:present>  
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>  
  <p>
<table border="0" cellspacing="0" cellpadding="0" class="background">
  <tr> 
    <td> 
      <table border="0" cellspacing="1" cellpadding="3" class="foreground">
        <tr> 
          <td colspan="2" class="white"> 
			<div align="center">
			  <logic:present name="image" property="imageFilename">
              <img border="0" src="<bean:write  name="image" property="imageUrl" filter="false"/>"> 
              </logic:present>
            </div>
          </td>
          </tr>
        </table>
      </td>
    </tr>    
  </table>
  <p>  
    <div align="center">
      <input type="button" value="Close" onclick="window.close();">&nbsp;      
      <input type="button" value="Print" onclick="window.print();">
    </div>
  </div>
</BODY>
</HTML>
