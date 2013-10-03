<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<html:html>
<head>
<title>Help: Maintain URLs</title>
<link rel="stylesheet" type="text/css"
	href='<html:rewrite page="/css/bigr.css"/>'>

<style type="text/css">
LI {
  margin-top: 8px;
}
</style>

<script type="text/javascript">
var myBanner = 'Help: Maintain URLs';  

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
}
</script>
</head>
<body class="bigr" onload="initPage();">

<h3>Help: Maintain URLs</h3>


<h4>Object Type</h4>
Select one of the object types listed:
<ul>
  <li><b>Main Menu:</b>  Marking a URL as belonging to the Main Menu will
  result in that URL appearing under a Left-hand menu item titled 
  "Additional Links".
  </li>
  <li><b>Donor, Case, and Sample:</b>  Marking a URL as belonging to Donor,
  Case, or Sample will result in that URL appearing on the "Case Menu" for
  any Sample Selection type result set.  Note that these values do not dictate
  where or for which specific object in the "Case Menu" the URL will appear - 
  these values simply indicate the URL is a "Sample Selection" result set URL.
  </li>
</ul>


<h4>URL</h4>
Enter the URL into the text box.  URLs should be prefixed with "http://".

For "Sample Selection" result set URLs, there are a variety of insertion
strings you may include in your URL.  These insertion string choices are:
<ul>
  <li><b>$$donor_id$$:</b>  Instructs the system to insert the donor id from
  the current row of the Sample Selection result set into the URL in place
  of the insertion string.
  </li>
  <li><b>$$donor_alias$$:</b>  Instructs the system to insert the donor alias from
  the current row of the Sample Selection result set into the URL in place
  of the insertion string.
  </li>
  <li><b>$$case_id$$:</b>  Instructs the system to insert the case id from
  the current row of the Sample Selection result set into the URL in place
  of the insertion string.
  </li>
  <li><b>$$case_alias$$:</b>  Instructs the system to insert the case alias from
  the current row of the Sample Selection result set into the URL in place
  of the insertion string.
  </li>
  <li><b>$$sample_id$$:</b>  Instructs the system to insert the sample id from
  the current row of the Sample Selection result set into the URL in place
  of the insertion string.
  </li>
  <li><b>$$sample_alias$$:</b>  Instructs the system to insert the sample alias from
  the current row of the Sample Selection result set into the URL in place
  of the insertion string.
  </li>
</ul>

For example, if you have a URL of "http://www.samplesRUs.com?theSamp=$$sample_id$$"
and you are viewing a Sample Selection result set that has sample PA0000BBBA as
it's current line, when you invoke the URL BIGR will open the URL
"http://www.samplesRUs.com?theSamp=PA0000BBBA"


<h4>Label</h4>
Enter the label for your URL.  Using the above URL as an example, you might
enter "Samples R Us".


<h4>Target</h4>
The target value controls the window in which your URL will appear.  Common
choices include:
<ul>
  <li><b>A name of your choice:</b>  Enter in a name starting with an alphanumeric
  character.  The first time the URL is invoked a new window will be opened, and
  every time thereafter the URL is invoked the contents of that window will be
  replaced with the contents from the new URL invocation.
  </li>
  <li><b>No value:</b>  Leaving the target blank will result in a new window being
  opened everytime the URL is invoked.  For example, if you invoke the URL above
  three times, three windows will be opened (one per invocation).
  </li>
  <li><b>_blank:</b>  Same as no value.
  <li><b>_self:</b>  A value of "_self" causes the URL to be loaded in the same
  window in which it was invoked.  In other words, the contents of the current
  window will be replaced with the contents of the URL invocation.
  </li>
</ul>


</body>
</html:html>
