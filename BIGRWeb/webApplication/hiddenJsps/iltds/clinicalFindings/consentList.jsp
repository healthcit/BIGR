<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Map" %>
<%@ page import="com.gulfstreambio.gboss.GbossFactory" %>
<%@ page import="com.ardais.bigr.javabeans.ClinicalFindingData" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="clinicalFindingsForm" id="myForm" type="com.ardais.bigr.iltds.web.form.ClinicalFindingsForm"/>
<%
	String pageTitle = "List of Cases: Clinical Findings";
%>
<html>
<head>
<title><%=pageTitle%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
function initPage() {
	showBanner('<%=pageTitle%>');
}
</script>
</head>
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="1" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
      <tr class="yellow"> 
	    <td> 
	      <div align="white">
	        <font color="#FF0000"><b><html:errors/></b></font>
	      </div>
	    </td>
	  </tr>
	  </logic:present>
      <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
	  <tr class="white"> 
	    <td> 
	      <div align="left">
	        <font color="#000000"><b><bigr:btxActionMessages/></b></font>
	      </div>
	    </td>
	  </tr>
	  </logic:present>
	</table>
  </div>
  <p>

<body class="bigr" onLoad="initPage();">
  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <tr class="white"> 
              <td class="yellow" align="right"><b>Donor</b></td>
              <td>
                <bean:write name="clinicalFindingsForm" property="ardaisId" />
              </td>
              <logic:empty name="clinicalFindingsForm" property="consentsAndFindings">
                <td class="yellow" align="right"><b>does not have any consents.</b></td>
              </logic:empty>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>

  <logic:notEmpty name="clinicalFindingsForm" property="consentsAndFindings">
    <p><table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
      <tr><td>
        <table class="foreground" cellpadding="3" cellspacing="1" border="0" width="100%">
          <tr class="green"> 
            <td><b>Consent Id</b></td>
            <td><b>Prostate Specific Antigen</b></td>
            <td><b>Digital Rectal Exam</b></td>
            <td><b>Clinical Finding Notes</b></td>
          </tr>
          <logic:iterate name="clinicalFindingsForm" property="consentsAndFindings" id="mapping">
            <tr class="white"> 
              <td>
			    <html:link page="/iltds/clinicalFindings/getClinicalFindings.do" paramId="consentId" paramName="mapping" paramProperty="key">
                <bean:write name="mapping" property="key" /></html:link>
              </td>
              <logic:empty name="mapping" property="value">
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </logic:empty>
              <logic:notEmpty name="mapping" property="value">
                <td><bean:write name="mapping" property="value.psa"/></td>
                <logic:empty name="mapping" property="value.dre">
                  <td>&nbsp;</td>
                </logic:empty>
                <logic:notEmpty name="mapping" property="value.dre">
                  <td><%=GbossFactory.getInstance().getDescription(((ClinicalFindingData)((Map.Entry)mapping).getValue()).getDre())%></td>
                </logic:notEmpty>
                <logic:empty name="mapping" property="value.clinicalFindingNotes">
                  <td>&nbsp;</td>
                </logic:empty>
                <logic:notEmpty name="mapping" property="value.clinicalFindingNotes">
                  <td>
                    <%
                	  String notes = ((ClinicalFindingData)((Map.Entry)mapping).getValue()).getClinicalFindingNotes();
                	  if (notes.length() > 30) {
                		notes = notes.substring(0,30) + "...";
                	  }
                    %>
                    <%=notes%>
                  </td>
                </logic:notEmpty>
              </logic:notEmpty>
            </tr>
          </logic:iterate>
        </table>
      </td></tr>
    </table></p>
  </logic:notEmpty>
  
</body>
</html>
