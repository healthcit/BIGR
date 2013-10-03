<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"N"); // "N" => user not required to be logged in
%>
<%@ taglib uri="/tld/struts-html"   prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/tld/bigr" prefix="bigr"%>
<jsp:useBean id="chkBrowser" class="com.ardais.bigr.orm.helpers.BrowserData"/>
<%
  chkBrowser.setUserAgent(request.getHeader("User-Agent"));
  //boolean isBrowserSupported = chkBrowser.isBrowserSupported();
    boolean isBrowserSupported = true;
  boolean isTimeoutError = false;
  if ((request.getParameter("TimeOutError") != null)
      && ((request.getParameter("TimeOutError")).toString().trim().equalsIgnoreCase("Y"))) {
    isTimeoutError = true;
  }
%>
<html>
<head>
<title>Welcome to BIGR&#174; - Please Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="shortcut icon" href="<html:rewrite page="/images/favicon.ico"/>" type="image/x-icon">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">


<script language="JavaScript">
<!--
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function validateForm()
{
  return validateUserID() && validateAccountID() && validatePwd();
}

function validateUserID() {
		var elem = document.forms[0].userId;
		var val = elem.value;
		
		// check for a value in the field.
		if (val == '') {
		  inputError(elem, 'Please enter your User ID.');
		  return false;
		}

		return true;
}

function validateAccountID() {
		var elem = document.forms[0].accountId;
		var val = elem.value;
		
		// check for a value in the field.
		if (val == '') {
		  inputError(elem, 'Please enter your Account ID.');
		  return false;
		}

		return true;
}

function validatePwd() {
		var elem = document.forms[0].password;
		var val = elem.value;
		
		// check for a value in the field.
		if (val == '') {
		  inputError(elem, 'Please enter your password.');
		  return false;
		}
		return true;
}
	
function inputError(elem, msg) {
  elem.select();
  elem.focus();
  alert(msg);
}

function setInitialFocus() {
  var f = document.forms[0];
  if (f.userId.value == "") {
    f.userId.focus();
  }
  else if (f.accountId.value == "") {
    f.accountId.focus();
  }
  else {
    f.password.focus();
  }
}

// Open the login window in top-level window (not inside a frame).
// If we're in a BIGR popup, try again in the parent window because
// we don't want the user to log in inside a popup.  We don't want to
// try in the parent if it isn't in the same domain as BIGR (see MR 5827).
// This returns true if the page should continue loading in the current
// window/frame.
//
function loginInMainWindow() {
    <% if (isTimeoutError) { %>
      var loginURL = "<html:rewrite page='/nosession.jsp?TimeOutError=Y'/>";
    <% } else { %>
      var loginURL = "<html:rewrite page='/login.jsp'/>";
    <% } %>

    if (window.opener != null) {
      var openerDomain = null;

      try {
        openerDomain = window.opener.document.domain;
      }
      catch (e) {
        // Do nothing, openerDomain will remain null
      }

      if (openerDomain == document.domain) {
        window.opener.top.location.href = loginURL;
        window.close();
        return false;
      }
    }

    // break out of frames
    if (window.top != window) {
      window.top.location.href = loginURL;
      return false;
    }
    
    return true;
}

function initPage() {
<% if (isBrowserSupported) { %>
     var continueLoading = loginInMainWindow();
     if (! continueLoading) return;
     setInitialFocus();
<% } // end if isBrowserSupported %>
}

//-->
</script>
</head>
<body class="bigr" onload="initPage();">
<center><a href="http://www.healthcit.com/" target="_blank">
          <img src="<html:rewrite page="/images/gsbio_185px.jpg"/>"
               alt="HealthCare IT Corporation" border="0"></a></center>
<br/>
<%
	if(! isBrowserSupported)
	{
%>
<font face="Verdana, Arial, Helvetica, sans-serif">
<p><font size="3">We're sorry.</font></p>
<p><font size="3">
To access BIGR&#174;, you must use Microsoft Internet Explorer 5.5
or later on Windows.
</font></p>
</font> 

<%} else { // browser is supported %>

	<%if(isTimeoutError)
	{%>
	 <div align="center"> 
     <table border="0" cellspacing="0" cellpadding="0" class="background" width="300">
      <tr> 
       <td>
         <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
           <tr class="green">
            <td>
	          <div align="center">
	            <font face="Verdana, Arial, Helvetica, sans-serif">
	              <p> <b>Your session has expired. Please log in again.</b> </p>
	            </font>
	          </div>
	        </td>
           </tr>
         </table>
       </td>
      </tr>
     </table>
     </div>
     <BR> 
	<%} // end if timeouterror %>
	
<div align="center">
  <table border="0" cellspacing="0" cellpadding="0" class="background" width="300">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
          <tr align="center" class="lightBlue"> 
            <td> <b><font size="+1">Welcome to BIGR&#174;</font></b> 
            </td>
          </tr>
          <logic:present name="org.apache.struts.action.ERROR">
          <tr class="green">
			<td><div align="left"><font color="#FF0000"><b><html:errors/></b></font></div></td>
		  </tr>
          </logic:present>
          <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
          <tr class="green"> 
            <td> 
              <div align="center"><font color="#000000"><b><bigr:btxActionMessages/></b></font></div>
            </td>
          </tr>
          </logic:present>
          <tr class="grey"> 
            <td> 
              <div align="center"><b>Please login below.</b> </div>
            </td>
          </tr>
          <tr class="lightgrey"> 
            <td> 
                <table border="0" cellspacing="0" cellpadding="0" class="background" width="100%">
                  <tr> 
                    <td> 
                      <table class="foreground" cellpadding="3" cellspacing="1" border="0" width="100%">
					   <html:form action="/orm/login" method="post" onsubmit="return(validateForm());">
						<tr class="white"> 
                          <td class="grey"> 
                            <div align="right"><b>User ID:</b></div>
                          </td>
                          <td> 
                            <html:text property="userId" size="15" maxlength="12"/>
                          </td>
                        </tr>
                        <tr class="white"> 
                          <td class="grey"> 
                            <div align="right"><b>Account ID:</b></div>
                          </td>
                          <td> 
                            <html:text property="accountId" size="15" maxlength="10"/>
                          </td>
                        </tr>
                        <tr class="white"> 
                          <td class="grey"> 
                            <div align="right"><b>Password:</b></div>
                          </td>
                          <td> 
                            <html:password property="password" size="15" maxlength="60"/>
                          </td>
                        </tr>
                        <tr class="grey"> 
                          <td colspan="2"> 
                            <div align="center"> 
                              <html:submit property="btnLogin">Log In</html:submit>
                            </div>
                          </td>
                        </tr></html:form>
                      </table>
                    </td>
                  </tr>
                </table>
              
            </td>
          </tr>
		  <tr class="lightBlue">
            <td> 
              <div align="center"> 
                <p>If you do not have an account<br>
                  please contact HealthCare IT, Inc. at:<br>
                  9210 Corporate Boulevard, Suite 400<br>
                  Rockville, MD 20850<br>
                  Phone: 301-990-4953<br>
                  <a href="http://www.healthcit.com/" target="_blank"> <span class="lightBlue"> www.healthcit.com</span></a><br>
                  <a href="javascript:void(0)" onclick="window.open('<html:rewrite page="/orm/DispatchLogin?op=ForgotPasswordStart"/>','ForgotPassword','width=500,height=500')">
                  <span class="lightBlue">Forgotten Password</span><br>
                  <%
					String custservVal = com.ardais.bigr.api.ApiProperties.getProperty("api.custserv.email");
					%>	
					</a><a href="mailto:<%=custservVal%>"><span class="lightBlue">CustomerService</span></a></p>
                </div>
            </td></tr>
        </table>
      </td>
    </tr>
  </table>
</div>
  <%} // end if browser is supported %>
</html>
