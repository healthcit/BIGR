<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
<!--
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
myBanner = '<h3><font color="#336699">Confirm ASM Form Information</font></h3>';
//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">
<table width="640" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td> 
    
        
      <div align="center"> <br>
        <table border="0" cellspacing="0" cellpadding="1" bgcolor="#336699">
            <tr> 
              <td>
                <table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#FFFFFF">
                  <tr> 
                    <td>
                    <div align="center">
<form name="form1" method="post" action="<html:rewrite page="/iltds/Dispatch"/>">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td> 
                              <div align="right">ASM Form ID: </div>
                            </td>
                            <td> 
                              <div align="left"><b><%= ((request.getParameter("asmFormID")!=null)?request.getParameter("asmFormID"):"") %> 
                                <input type="hidden" name="asmFormID" value="<%= ((request.getParameter("asmFormID")!=null)?request.getParameter("asmFormID"):"") %>">
                                </b></div>
                            </td>
                            <td width="20">&nbsp;</td>
                            <td> 
                              <div align="right">Consent ID:</div>
                            </td>
                            <td> 
                              <div align="left"><b><%= ((request.getParameter("consentID")!=null)?request.getParameter("consentID"):"") %> 
                                <input type="hidden" name="consentID" value="<%= ((request.getParameter("consentID")!=null)?request.getParameter("consentID"):"") %>">
                                </b></div>
                            </td>
                          </tr>
                        </table>
                        <br>
						<%
						String removeHour   = request.getParameter("removeHour");
						String removeMinute = request.getParameter("removeMinute");
						String removeAmpm   = request.getParameter("removeAmpm");
						String grossHour    = request.getParameter("grossHour");
						String grossMinute  = request.getParameter("grossMinute");
						String grossAmpm    = request.getParameter("grossAmpm");
						
						if (removeHour.equals("-1")){
							removeHour = "Hr";
						} else if (removeMinute.equals("-1")){
							removeMinute = "Min";
						} else if (grossHour.equals("-1")){
							grossHour = "Hr";
						} else if (grossMinute.equals("-1")){
							grossMinute = "Min";
						} else if (removeMinute.length() == 1){
							removeMinute = "0" + removeMinute;
						} else if (grossMinute.length() == 1){
							grossMinute = "0" + grossMinute;
						}
						%>
                        <table border="0" cellspacing="1" cellpadding="0">
                          <tr> 
                            <td> 
                              <div align="right">Surgical Specimen:</div>
                            </td>
                            <td width="10">&nbsp;</td>
                            <td><b><%= ((request.getParameter("surgicalSpecimen")!=null)?request.getParameter("surgicalSpecimen"):"") %> 
                              <input type="hidden" name="surgicalSpecimen" value="<%= ((request.getParameter("surgicalSpecimen")!=null)?request.getParameter("surgicalSpecimen"):"") %>">
                              </b> </td>
                          </tr>
                          <tr> 
                            <td> 
                              <div align="right">Time of Surgical Removal:</div>
                            </td>
                            <td>&nbsp;</td>
                            <td><b><%= removeHour %> 
                              <input type="hidden" name="removeHour" value="<%= ((request.getParameter("removeHour")!=null)?request.getParameter("removeHour"):"") %>">
                              :<%= removeMinute %> 
                              <input type="hidden" name="removeMinute" value="<%= ((request.getParameter("removeMinute")!=null)?request.getParameter("removeMinute"):"") %>">
                              <%= removeAmpm %> 
                              <input type="hidden" name="removeAmpm" value="<% if(request.getParameter("removeAmpm").equals("am")) out.print("0"); else out.print("1"); %>">
                              </b></td>
                          </tr>
                          <tr> 
                            <td> 
                              <div align="right">Time of Grossing:</div>
                            </td>
                            <td>&nbsp;</td>
                            <td> <b><%= grossHour %> 
                              <input type="hidden" name="grossHour" value="<%= ((request.getParameter("grossHour")!=null)?request.getParameter("grossHour"):"") %>">
                              :<%= grossMinute %> 
                              <input type="hidden" name="grossMinute" value="<%= ((request.getParameter("grossMinute")!=null)?request.getParameter("grossMinute"):"") %>">
                              <%= grossAmpm %> 
                              <input type="hidden" name="grossAmpm" value="<% if(request.getParameter("grossAmpm").equals("am")) out.print("0"); else out.print("1"); %>">
                              </b></td>
                          </tr>
                          <tr> 
                            <td> 
                              <div align="right">Employee Name:</div>
                            </td>
                            <td>&nbsp;</td>
                            <td><b><%= ((request.getParameter("employee")!=null)?request.getParameter("employee"):"") %> 
                              <input type="hidden" name="employee" value="<%= ((request.getParameter("employee")!=null)?request.getParameter("employee"):"") %>">
                              </b></td>
                          </tr>
                          <tr> 
                            <td height="25" colspan="3"> 
                              <div align="center"> 
                                <input type="hidden" name="op" value="ASMFormInfoInsert">
                                <input type="hidden" name="title" value="<%= ((request.getParameter("title")!=null)?request.getParameter("title"):"") %>">
                                <input type="submit" name="Submit" value="Confirm">
                              </div>
                            </td>
                          </tr>
                        </table>
                      </form>
                      <p>&nbsp;</p>
                      
                    </div>
                      </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </div>
      
    </td>
  </tr>
</table>
 <script language="JavaScript">
		document.form1.Submit.focus()
		</script>
</body>
</html>
