<%@ page language="java" import="com.ardais.bigr.iltds.helpers.FormLogic,java.text.*,java.util.*,java.text.*,com.ardais.bigr.api.*,com.ardais.bigr.security.*,com.ardais.bigr.util.WebUtils"%>
<%@ page import="com.ardais.bigr.javabeans.UrlDto" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ taglib uri="/tld/struts-html"   prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
	java.util.Vector profile = null;
	java.util.List menuUrls = null;
	HttpSession mySession = request.getSession(false);
	if (mySession != null) {
	    profile = (java.util.Vector)mySession.getAttribute("userprofile");
	    menuUrls = (java.util.List)mySession.getAttribute("menuUrls");
	}
	java.util.Vector applicationLevel = new java.util.Vector();
	
	int currentTOP = 0;
	int currentSUB = 0;
	int currentOPT = 0;

if ((profile == null) || (profile.size() == 0)) {
    this.getServletContext().getRequestDispatcher("/nosession.jsp").forward(request, response);
}	
else {
  // For MR6250, we are now implementing a session-keepalive mechanism
  // for Ardais users.  This mechanism automatically periodically pings
  // a page on the server to keep the user's session alive as long as they
  // currently have this Menu page open in some browser window.
  
  SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
  Set keepAliveRoles =
    new HashSet(Arrays.asList(ApiFunctions.splitAndTrim(
      ApiProperties.getProperty("api.session.keepalive.roles",""), ",")));
  boolean includeKeepAlive = (securityInfo.isInAnyRole(keepAliveRoles));
  int maxInactiveSeconds = request.getSession(false).getMaxInactiveInterval();
  int keepAliveIntervalSeconds = maxInactiveSeconds / 2;
  if (maxInactiveSeconds < 0) { // sessions never expire, no need to keep alive
    includeKeepAlive = false;
  }
  else if (keepAliveIntervalSeconds < 60) {
    // Never do keepalive pings more than once a minute.
    keepAliveIntervalSeconds = 60;
  }
  else if (keepAliveIntervalSeconds > 3600) {
    // Never do keepalive pings less than once an hour.
    keepAliveIntervalSeconds = 3600;
  }
  
  // lk test:
  //includeKeepAlive = false;
  
  int keepAliveIntervalMillis = 0;
  String keepAliveUrlArgs = null;
  if (includeKeepAlive) {
    keepAliveIntervalMillis = 1000 * keepAliveIntervalSeconds;
    // The keepalive URL args don't serve any function in the code,
    // they're just there so that we'll see whose connections are
    // being kept alive in the web server logs.  It would be bad to
    // use these URL arguments for anything real, since we can't
    // prevent them from being altered.
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
    String sessionCreated = dateFormat.format(new Date(request.getSession(false).getCreationTime()));
    keepAliveUrlArgs = "userid=" + Escaper.urlEncode(securityInfo.getUsername()) +
                       "&account=" + Escaper.urlEncode(securityInfo.getAccount()) +
                       "&since=" + Escaper.urlEncode(sessionCreated);
  }
%>

<!-- html XMLNS:IE-->

<html>
<head>
<title>HealthCare IT Corporation BIGR(TM)</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">

<style type="text/css">
#menuDiv {
    background-color: white;
    position: absolute;
    top: 0px;
    left: 0px;
    height: 100%;
    overflow: auto;
    border-right: thin solid #0066CC;
}

.menuDivInner {
   // background-color: #336666;
    background-color: #336699
    color:white;
}

#toggleDiv {
    background-color: white;
    position: absolute;
    top: 0px;
    left: 0px;
    height: 100%;
    overflow: hidden;
    padding: 1px;
}

#showHideMenuImage {
    position: relative;
}

.menu_level_1 {
		//color: white;
		color: #FFFFCC;
    padding-left: 2px;
    padding-right: 1px;
    FONT-WEIGHT: bold;
    
}

.menu_level_2 {
   // BORDER-LEFT-COLOR:   #336666;
   // BORDER-LEFT-COLOR: 	 #0066CC;
    BORDER-LEFT-COLOR: 	 #336699;
    BORDER-LEFT-WIDTH:   20;
    BORDER-LEFT-STYLE:   solid;
    padding-left: 2px;
    padding-right: 1px;
    color: #336699;
    FONT-WEIGHT: bold;
    FONT-SIZE:  9pt;
}

.menu_level_3 {
    //BORDER-LEFT-COLOR:   #336666;
    //BORDER-LEFT-COLOR: 	 #0066CC;
    BORDER-LEFT-COLOR: 	 #336699;
    BORDER-LEFT-WIDTH:   40;
    BORDER-LEFT-STYLE:   solid;
    padding-left: 2px;
    padding-right: 1px;
    color: #000099;
    FONT-SIZE:  9pt;
}
</style>

<script language="JavaScript" src="<html:rewrite page="/js/menu.js"/>"></script>
<script type="text/javascript">
<!--
var ids = new Array();
var images = new Array();
var clicks = new Array();

var imageOpen = new Image();
var imageClose = new Image();
imageOpen.src = '<html:rewrite page="/images/MenuOpened.gif"/>';
imageClose.src = '<html:rewrite page="/images/MenuClosed.gif"/>';

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function positionItemsOnce() {
  //document.all. is not taken by safari and firefox, remove it, noted by EZ
 // var theToggleDiv = document.all.toggleDiv;
 // var theMenuDiv = document.all.menuDiv;
  var theToggleDiv = document.getElementById("toggleDiv");
  var theMenuDiv = document.getElementById("menuDiv");
    
  var menuDivLeft = theToggleDiv.offsetLeft + theToggleDiv.offsetWidth;
  var menuDivWidth = document.body.clientWidth - menuDivLeft;

  theMenuDiv.style.pixelLeft = menuDivLeft;
  theMenuDiv.style.pixelWidth = menuDivWidth;
}

function positionItems() {
  //var theToggleDiv = document.all.toggleDiv;
  //var theShowHideMenuImage = document.all.showHideMenuImage;
  var theToggleDiv = document.getElementById("toggleDiv");
  var theShowHideMenuImage = document.getElementById("showHideMenuImage");
    
  var showHideMenuImageTop = (theToggleDiv.clientHeight - theShowHideMenuImage.offsetHeight) / 2;
  if (showHideMenuImageTop < 0) {
    showHideMenuImageTop = 0;
  }

  theShowHideMenuImage.style.top = showHideMenuImageTop;
}

function initPage() {
  //initializeMenuState();
  //document.all.everything.style.display = 'block';
  document.getElementById("everything").style.display = 'block';
  positionItemsOnce();
  positionItems();
<% if (includeKeepAlive) { %>
  startKeepAlive();
<% } // end if includeKeepAlive %>
}

var priorResizeHeight = 0;

function onResizePage() {
  var currentHeight = document.body.clientHeight;
  if (currentHeight == priorResizeHeight) return;
  priorResizeHeight = currentHeight;
  positionItems();
}

window.onresize = onResizePage;
  
<% if (includeKeepAlive) { 
   // In the keepAliveCallback function we look for a specific string in the response that
   // the server returned from requesting keepAlive.do.  keepAlive.jsp places this string
   // in the response to indicate that we should stop requesting keepAlive pages (for
   // example, because the user session is gone, see keepAlive.jsp for more details). %>
   
var keepAliveTimerId = null;

function keepAliveCallback(s) {
  var stopTimer = (s && (s.indexOf('STOP_KEEPALIVE') >= 0));
  if (stopTimer && (keepAliveTimerId != null)) {
    window.clearInterval(keepAliveTimerId);
    keepAliveTimerId = null;
  }
}

function hitKeepAlivePage() {
  var theURL = '<html:rewrite page="/general/keepAlive.do"/>?<%= keepAliveUrlArgs %>';
  //document.all.oDownload.startDownload(theURL, keepAliveCallback);
  document.getElementById("oDownload").startDownload(theURL, keepAliveCallback);
}

function startKeepAlive() {
  if (keepAliveTimerId != null) {
    window.clearInterval(keepAliveTimerId);
  }
  keepAliveTimerId = window.setInterval(hitKeepAlivePage, <%= keepAliveIntervalMillis %>, 'JavaScript');
}
<% } // end if includeKeepAlive %>

-->
</script>
</head>
<body class="lightBlue" onload="initPage();" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<div id="everything" style="display: none;">

<% if (includeKeepAlive) { %>
  <IE:Download id="oDownload" style="display:block; behavior:url(#default#download);" />
<% } // end if includeKeepAlive %>



<div id="toggleDiv" onclick="window.parent.expandOrCollapseMenuFrame();"
     onselectstart="event.returnValue = false;">
      <html:img styleId="showHideMenuImage" page="/images/show_hide_menubar.gif"  align="absmiddle" alt="Click here to show/hide the menu"/> 
</div>



<div id="menuDiv">

  <div class="menuDivInner">
      <table  class="foreground" border="0" cellspacing="1" cellpadding="0" width="100%"> 
      
      
        <% for (int k = 0 ; k < profile.size(); k++){
	  	      applicationLevel = (java.util.Vector)profile.get(k);			 
	     for (int i = 0 ; i < applicationLevel.size(); i+=2){
			java.lang.String BIGR = (java.lang.String)applicationLevel.get(i);
			java.util.Vector topLevel = new java.util.Vector();
			topLevel = (java.util.Vector)applicationLevel.get(i+1); 
		   if (BIGR.equals("TOP_MENU")){
		   		java.util.Vector subLevel = new java.util.Vector();
				subLevel = (java.util.Vector)topLevel.get(0);
		   		//java.lang.String sub = (java.lang.String)subLevel.get(0);
				java.util.Vector optionLevel = new java.util.Vector();
		   		optionLevel = (java.util.Vector)subLevel.get(1);
		   		currentOPT = 0;
  		   		java.util.Vector option = (java.util.Vector)optionLevel.get(0);
		  %>
        <tr class="lightBlue"> 
          <td class="menu_level_1"> <html:link page='<%= (String)option.get(1) %>' target="mainFrame"><SPAN class="lightBlue"><%= (String)option.get(0)%></SPAN></html:link>
          </td>
        </tr> 
            <%currentTOP++;}  
			  else {%>
        <tr class="lightBlue"> 
          <td class="menu_level_1"> 
             <div onClick="swapMenu('<%= currentTOP %>')"><img src="<html:rewrite page="/images/MenuClosed.gif"/>" name="Image_<%= currentTOP %>"  >&nbsp;<%= BIGR%>
             </div>
          </td>
        </tr>
        <% java.util.Vector subLevel = new java.util.Vector();
		   currentSUB = 0;
		   for (int j = 0 ; j < topLevel.size() ; j++){
		   		subLevel = (java.util.Vector)topLevel.get(j);
		   		java.lang.String sub = (java.lang.String)subLevel.get(0);
		   if (sub.equals("SUB_MENU")){
		   		java.util.Vector optionLevel = new java.util.Vector();
		   		optionLevel = (java.util.Vector)subLevel.get(1);
		   		currentOPT = 0;
  		   		java.util.Vector option = (java.util.Vector)optionLevel.get(0);
		  %>
	
       <script> 
          ids.push("<%= currentTOP%>");
		  clicks.push("Close");
	   </script>
	
        <tr class="lightGrey" id="<%= currentTOP %>" name="<%=currentTOP %>" style="display: none;"> 
          <td class="menu_level_2"> <html:link page='<%= (String)option.get(1) %>' target="mainFrame"><span class="hyperlink"><%= (String)option.get(0)%></span>
          </html:link>
          </td>
        </tr> 
      
            <%currentSUB++;}  
			  else {%>
            <script> 
                ids.push("<%= currentTOP %>");
				clicks.push("Close");
		    </script>
        <tr class="lightGrey" id="<%= currentTOP %>" name="<%=currentTOP %>" style="display: none;"> 
          <td class="menu_level_2"> 
            <div onClick="swapMenu('<%= currentTOP%>.<%= currentSUB %>')"><img src="<html:rewrite page="/images/MenuClosed.gif"/>" name="Image_<%= currentTOP %>.<%= currentSUB %>">&nbsp;<%= sub %></div>
           </td>
        </tr>
       
        
        <% java.util.Vector optionLevel = new java.util.Vector();
		   optionLevel = (java.util.Vector)subLevel.get(1);
		   currentOPT = 0;
		   for(int n = 0 ; n < optionLevel.size() ; n++){
		   java.util.Vector option = (java.util.Vector)optionLevel.get(n);
		  // String url	 = (String)optionLevel.get(n+1); %>
        <tr class="<% if((n%2) == 0) out.print("grey"); else out.print("white"); %>" id="<%= currentTOP %>.<%= currentSUB %>" name="<%= currentTOP %>.<%= currentSUB %>" style="display: none;">
          <script> ids.push("<%= currentTOP%>.<%= currentSUB %>");
				  clicks.push("Close");
		  </script>
          <td class="menu_level_3"> <html:link page='<%= (String)option.get(1) %>' target="mainFrame">
          <SPAN class="<% if((n%2) == 0) out.print("grey"); else out.print("white"); %>"> <%= (String)option.get(0)%></SPAN>
          </html:link> </td>
        </tr>
        <%   currentOPT++;
			} 
		currentSUB++;
		}
	  
	  }
	 
	 }  currentTOP++; }
}%>
<%
	if (!ApiFunctions.isEmpty(menuUrls)) {
%>
        <tr class="lightBlue"> 
          <td class="menu_level_1"> 
            <div onClick="swapMenu('<%= currentTOP %>')"><img src="<html:rewrite page="/images/MenuClosed.gif"/>" name="Image_<%= currentTOP %>">&nbsp;Additional Links</div>           
          </td>
        </tr>
        <script> ids.push("<%= currentTOP%>");
				 clicks.push("Close");
		</script>
<%
		Iterator iterator = menuUrls.iterator();
		while (iterator.hasNext()) {
			UrlDto urlDto = (UrlDto)iterator.next();
			if (ApiFunctions.isEmpty(urlDto.getTarget())) {
				urlDto.setTarget("_blank");
			}
%>
        <tr class="lightGrey" id="<%= currentTOP %>"  name="<%= currentTOP %>" style="display: none;"> 
          <td class="menu_level_2"> <a href='<%= urlDto.getUrl() %>' target='<%= Escaper.htmlEscapeAndPreserveWhitespace(urlDto.getTarget()) %>'><SPAN class="hyperlink"><%= Escaper.htmlEscapeAndPreserveWhitespace(urlDto.getLabel()) %></SPAN></a> 
            <script> ids.push("<%= currentTOP %>");
				     clicks.push("Close");
			</script>
		  </td>
		</tr>
<%			
		}
	}
%>
      </table>
</div>
  <br/>
  <center>
	  <a href="http://www.healthcit.com" target="_blank">
			  <IMG border="0" src="images/gsbio_powered.jpg"
			       alt="Powered By HealthCare IT Corporation" border="0"></a>
	</center>
</div>
</div>

</body>
</html>
<% } %>
