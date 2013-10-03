<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>



<html>
<head>
<title>ASM Module Information</title>




<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>

<body class="bigr">

      <form name="blah">
        
          
  <table class="background" cellpadding="0" cellspacing="0" border="0" width="300" align="center">
    <tr> 
              <td> 
                
        <table class="foreground" cellpadding="3" cellspacing="1" border="0">
         
          <tr class="yellow"> 
            <td> <b>Sorry, ASM module <%= ((request.getAttribute("ASM")!=null)?request.getAttribute("ASM"):"")%> has been converted from 
              the SMLR Application and cannot be edited within the BIGR Application.</b> 
            </td>
          </tr>
		 
         
       
          
          
          
          
          <tr class="white"> 
            <td> 
              <div align="center"> 
                <input type="button" name="Button" value="Close" onClick="window.close();">
              </div>
            </td>
          </tr>
        </table>
              </td>
            </tr>
          </table>
        </form>


</body>
<HEAD><META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"></HEAD>     
</html>
