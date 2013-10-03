<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Help</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body class="bigr">
<table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
  <tr> 
    <td> 
      <table class="foreground" cellpadding="3" cellspacing="1" border="0" width="100%">
        <tr class="yellow"> 
          <td> 
            <div align="center"><b>Using the Biomaterials and Information for 
              Genomic Research (BIGR&#174;) Library </b></div>
          </td>
        </tr>
        <tr class="white"> 
          <td> 
            <ul>
              <li><a href="#1">How do I contact HealthCare IT Corporation customer service? </a> 
              <li><a href="#2">What is the BIGR&#174; Library?</a> 
              <li><a href="#3">What system requirements do I need to run this Web site? </a> 
              <li><a href="#4">How do I log off? </a> 
              <li><html:link page="/terms/terms.jsp">Terms and Conditions</html:link> 
            </ul>
          </td>
        </tr>
        <tr class="green"> 
          <td> <b><a name="1"></a>How do I contact HealthCare IT Corporation customer service?</b> 
          </td>
        <tr class="white">
          <td> 
            <p>We are available to help you Monday through Friday, 9 a.m. to 5 
              p.m., U.S. Eastern time &#151; closed holidays. Simply call us at 
              866-989-0035. You may also email us at
                  <%
					String custservVal = com.ardais.bigr.api.ApiProperties.getProperty("api.custserv.email");
					%>	
					    <a href="mailto:<%=custservVal%>"><%=custservVal%></a>.
              <br>
              <br>
          </td>
        </tr>
        <tr class="green"> 
          <td> <b><a name="2"></a>What is the BIGR&#174; Library?</b> </td>
        <tr class="white">
          <td> 
            <p>The BIGR&#174; Library is a web-based software application providing 
              functionality for browsing and searching a human biological materials 
              inventory online. It contains user-management and reporting functionality. 
              Account and User IDs are required for all users. <br>
              <br>
          </td>
        </tr>
        <tr class="green"> 
          <td> <b><a name="3"></a>What system requirements do I need to run this 
            Web site?</b> </td>
        <tr class="white">
          <td> 
            <p>The minimum system requirements to run BIGR&#174; Library are: 
            <ul>
              <li>A personal computer running Windows 95 or higher
              <li>A Web browser that supports frames and JavaScript, such as Microsoft 
                Internet Explorer 5.5 Service Pack 2 or above
              <li>Secure Socket Layer (SSL) Encryption set to 128 bit. 
              <li> Super VGA 256-color monitor 
              <li> Mouse or compatible pointing device 
              <li> The amount of RAM recommended by your Web browser vendor
              <li> BIGR&#174; Library is optimized to run on a display resolution 
                of 1024x768 pixels. BIGR&#174; Library will run at any resolution, 
                but you may need to scroll the Web pages right and left to see 
                all the information at lower resolutions. 
            </ul>
          </td>
        </tr>
        <tr class="green"> 
          <td> <b><a name="4"></a>How do I log off?</b> </td>
        <tr class="white">
          <td> 
            <p>If you are using a public terminal, you will want to log off, or 
              sign out, before you leave the terminal. To do this, click the link 
              that reads &quot;Logout&quot; from any page. <br><br>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>