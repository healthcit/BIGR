<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>


<html>
  <head>
    <title>HealthCare IT Corporation BIGR(R)</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
    
    <script type="text/javascript">
      <!--
      var popupMessage = null;

      function openPopup(pageType){
	    closePopup(); // if a popup is already up, close it first.
        var msg = null;
        if(pageType == "wait"){
          msg="pleasewait";
        } else if(pageType == "searching") {
          msg="searching";
        } else if(pageType == "processing") {
          msg="processing";
        } else {
          msg="processing";
        } 
	    var url ='<html:rewrite page="/popup.jsp"/>' + '?message=' + msg;
	    popupMessage = window.showModelessDialog(url,'',
	      'help:no;unadorned:yes;dialogHeight:10;center:yes;status:no');
      }

      function closePopup(){
	    if (popupMessage) {
	      var popupWin = popupMessage;
	      popupMessage = null;
		  popupWin.close();
	    }
      }

      function openHelpWindow(url) {
        var w = window.open(url,'BIGRHelp','scrollbars=yes,resizable=yes,status=yes,width=900,height=480');
        // Always bring the popup to the front initially, even if the page we're
        // opening doesn't explicitly set focus to itself.
        w.focus();
      }
     //-->
    </script>
  </head>
  <body class="bigrtop" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<bean:define id="displayBanner" name="displayBannerForm" type="com.ardais.bigr.orm.web.form.DisplayBannerForm"/>
	<%
		String strImage = new String("/images/");
		strImage = strImage.concat(displayBanner.getImageLogo());
	%>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1%" height="100%"><img src="<html:rewrite page="<%= strImage %>"/>"></td>
        <td height="1%" width="98%">
          <div align="center" id="banner" class="banner">Welcome to the BIGR&#174; Library for
            Clinical Genomics Research </div>
    	  </td>
        <td height="1%">
          <table class="background" cellpadding="0" cellspacing="0" border="0">
            <tr>
              <td>
                <table class="foreground" cellpadding="3" cellspacing="1" border="0">
                  <tr class="white"> 
                    <td nowrap align="center"><a href="#" onClick="openHelpWindow('<html:rewrite page="/help/help.jsp"/>');">Help</a></td>
                    <td nowrap align="center"><html:link page="/terms/terms.jsp" target="mainFrame">Terms of Use</html:link></td>
                  </tr>
                  <tr class="white">
                    <td colspan="3" nowrap align="center" colspan="2">
                      <html:link page="/orm/DispatchLogin?op=Logout" target="_top">
                        Logout: <bean:write name="displayBannerForm" property="userDisplayName"/>
                      </html:link>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </body>
</html>
