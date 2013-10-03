<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%@ page import="com.ardais.bigr.util.WebUtils"%>
<%@ page import="com.ardais.bigr.util.IcpUtils" %>
<%@ page import="com.ardais.bigr.pdc.helpers.DonorHelper" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"   prefix="bean" %>
<%@ taglib uri="/tld/struts-logic"  prefix="logic"%>
<%@ taglib uri="/tld/bigr"          prefix="bigr" %>
<%@ taglib uri="/tld/datetime"      prefix="dt"   %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>

<%
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
%>

<bean:define id="icpData" name="icpData"
  type="com.ardais.bigr.iltds.databeans.IcpData" />
<bean:define id="donorRawBean" name="icpData" property="data"
  type="com.ardais.bigr.pdc.javabeans.DonorData"/>
<%
  DonorHelper donorBean = new DonorHelper(donorRawBean);
  pageContext.setAttribute("donorBean", donorBean, PageContext.PAGE_SCOPE);
%>

<script type="text/javascript">
  function openBrWindow(theURL,winName,features) { //v2.0
    window.open(theURL,winName,features);
  }
</script>

<div align="left"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground-small">
          <tr class="yellow"> 
            <td colspan="4"> 
              <div align="center">
                <b>
                  Donor Information: <bean:write name="donorBean" property="ardaisId" ignore="true" filter="false"/>
                  <logic:notEmpty name="donorBean" property="customerId">
                    (<bean:write name="donorBean" property="customerId" ignore="true" filter="true"/>)
                  </logic:notEmpty>
                </b>
              </div>
            </td>
          </tr>
          <tr class="white">
            <td class="grey" align="right">
              <b>Year of Birth</b>
            </td>
            <td>
              <bean:write name="donorBean" property="yyyyDob" ignore="true"/>
            </td>
            <td class="grey" align="right">
              <b>Gender</b>
            </td>
            <td>
              <bean:write name="donorBean" property="genderDisplay" ignore="true"/>
            </td>
          </tr>
          <tr class="white">
            <td class="grey" align="right">
              <b>Ethnicity</b>
            </td>
            <td>
              <bean:write name="donorBean" property="ethnicCategoryDisplay" ignore="true"/>
            </td>
            <td class="grey" align="right">
              <b>Race</b>
            </td>
            <td>
              <bean:write name="donorBean" property="raceDisplay" ignore="true"/>
            </td>
          </tr>
          <tr class="white">
            <td class="grey" align="right">
              <b>Country of Birth</b>
            </td>
            <td colspan="3">
              <bean:write name="donorBean" property="countryOfBirthDisplay" ignore="true"/>
            </td>
          </tr>
          <tr class="white">
            <td class="grey" align="right">
              <b>Cases</b>
            </td>
            <td colspan="3">
              <%= donorBean.getConsentIdListDisplayHtml(securityInfo) %>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>

