<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="helper" id="summary" type="com.ardais.bigr.pdc.helpers.ClinicalDataSummaryHelper"/>

<html>
<head>
<title>Summary of Clinical Data Extraction</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
function initPage() {
	showBanner('Summary of Clinical Data Extraction');
}
</script>
</head>

<body class="bigr" onLoad="initPage();">
  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <tr class="white"> 
              <td class="yellow" align="right"><b>Donor</b></td>
              <td>
              	<html:link page="/ddc/Dispatch" name="summary" property="donorLinkParams">
                <bean:write name="summary" property="ardaisId"/>
                </html:link>
                <logic:notEmpty name="summary" property="donorCustomerId">
                  (<bean:write name="summary" property="donorCustomerId" />)
                </logic:notEmpty>
              </td>
            </tr>
            <tr class="white">
              <td class="yellow" align="right"><b>Case</b></td>
              <td>
                <bean:write name="summary" property="consentId" />
                <logic:notEmpty name="summary" property="consentCustomerId">
                  (<bean:write name="summary" property="consentCustomerId" />)
                </logic:notEmpty>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
  
  <bean:write name="summary" property="startCategories"/>
  <logic:iterate name="summary" property="categoryList.iterator" id="legalValue">
    <p><table class="foreground" border="0" cellpadding="3" cellspacing="0" width="100%" id='ID<bean:write name="summary" property="currentCategory"/>'>
      <tr class="green"> 
        <td align="left" style="border: 1px solid #336699; border-right: none;"> 
          <b><bean:write name="summary" property="currentCategoryDisplay"/></b>
        </td>
        <td align="right" style="border: 1px solid #336699; border-left: none;">
          <input type="button" style="font-size: xx-small;" value="Edit" onclick="window.location.href='<html:rewrite page="/ddc/Dispatch" name="summary" property="currentEditLinkParams"/>';">
        </td>
      </tr>
      <logic:present name="summary" property="currentClinicalData">
        <tr><td colspan="2" style="border: 1px solid #336699; border-top: none;">
          <bigr:beanWrite name="summary" property="currentClinicalData.clinicalData" filter="true" whitespace="true"/>
        </td></tr>
      </logic:present>
      <logic:notPresent name="summary" property="currentClinicalData">
        <tr><td colspan="2">
          &nbsp;&lt;No <bean:write name="summary" property="currentCategoryDisplay"/> data has been entered.&gt;
        </td></tr>
      </logic:notPresent>
    </table></p>
    <bean:write name="summary" property="nextCategory"/>
  </logic:iterate>
  <logic:present name="summary" property="lastEditedCategory">
	<script language="JavaScript">
	   document.all.ID<%=summary.getLastEditedCategory()%>.scrollIntoView(true);
	</script>
  </logic:present>    
</body>
</html>
