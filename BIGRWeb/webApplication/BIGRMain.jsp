<%
    com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>

<!-- DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!-- html-->
<head>
<title>HealthCare IT Corporation BIGR(R)</title>
<link rel="shortcut icon" href="<html:rewrite page="/images/favicon.ico"/>" type="image/x-icon">

<script type="text/javascript">
<!--
function expandOrCollapseMenuFrame(){
   
  /*
  * EZ: rewrite code for Safari, the following code only work in IE 
  var bottomFrameset = document.all.bottomSet;
  var menuDocument = document.frames.leftFrame.document; 
  var theMenuDiv = menuDocument.all.menuDiv; */
  
  var bottomFrameset = document.getElementById("bottomSet");

  var menuDocument = window.parent.leftFrame.document; 
  //var theMenuDiv = menuDocument.all.menuDiv;
  var theMenuDiv = menuDocument.getElementById("menuDiv");
  var collapsedFrameWidth = theMenuDiv.offsetLeft + 2;
  var newFramesetCols = collapsedFrameWidth + ",*";
 
  if (bottomFrameset.cols != newFramesetCols) {
    bottomFrameset.cols = newFramesetCols;
    theMenuDiv.style.display='none';
  } else {
    bottomFrameset.cols = "200,*";
    theMenuDiv.style.display='block';
  }
}
-->
</script>

</head>
<frameset rows="52,*" cols="*" frameborder="NO" border="0" framespacing="0"> 
  <frame name="topFrame" scrolling="NO" noresize src="<html:rewrite page="/orm/top.do"/>" >
   <frameset id="bottomSet" cols="200,*" frameborder="YES" border="0" framespacing="0"> 
    <frame name="leftFrame" noresize src="<html:rewrite page="/MenuLHS.jsp"/>">
    <frame name="mainFrame" src="<html:rewrite page="/center.jsp"/>">
  </frameset>
</frameset>

 

</html>
