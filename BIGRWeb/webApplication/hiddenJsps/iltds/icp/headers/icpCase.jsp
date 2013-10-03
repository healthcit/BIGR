<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%@ page import="com.ardais.bigr.util.WebUtils"%>
<%@ page import="com.ardais.bigr.util.IcpUtils" %>
<%@ page import="com.ardais.bigr.api.ApiProperties"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"   prefix="bean" %>
<%@ taglib uri="/tld/struts-logic"  prefix="logic"%>
<%@ taglib uri="/tld/bigr"          prefix="bigr" %>
<%@ taglib uri="/tld/datetime"      prefix="dt"   %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>

<bean:define id="icpData" name="icpData"
  type="com.ardais.bigr.iltds.databeans.IcpData" />
<bean:define id="caseBean" name="icpData" property="data"
  type="com.ardais.bigr.iltds.databeans.CaseData"/>

<script type="text/javascript">
  function openBrWindow(theURL,winName,features) { //v2.0
    window.open(theURL,winName,features);
  }
</script>

<%
	SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
	boolean isICPSuperUser = securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER);
	boolean isUserAccountEqualCaseAccount = securityInfo.getAccount().equalsIgnoreCase(caseBean.getConsentAccount());
	boolean showDINamePolicyAndIRB = isICPSuperUser || isUserAccountEqualCaseAccount;
%>

<div align="left"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground-small">
          <tr class="yellow"> 
            <td colspan="6"> 
              <div align="center">
                <b>
                  Case Information: <bean:write name="caseBean" property="case_id" ignore="true" filter="false"/>
                  <logic:notEmpty name="caseBean" property="customer_id">
                    (<bean:write name="caseBean" property="customer_id" ignore="true" filter="true"/>)
                  </logic:notEmpty>
                </b>
              </div>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey">
              <div align="right">
                <b>Donor Id</b>
              </div>
            </td>
            <td colspan="5"> 
              <bigr:icpLink>
                <bean:write name="caseBean" property="parent.donor_id" ignore="true" filter="false"/>
              </bigr:icpLink>
              <logic:notEmpty name="caseBean" property="parent.customer_id">
                (<bean:write name="caseBean" property="parent.customer_id" ignore="true" filter="true"/>)
              </logic:notEmpty>
            </td>
          </tr>
          <tr class="white">
            <td class="grey">
              <div align="right">
                <b>Age At Collection</b>
              </div>
            </td>
            <td>
              <bean:write name="caseBean" property="ageAtCollection" ignore="true"/>
            </td>
            <td class="grey">
              <div align="right">
                <b>Gender</b>
              </div>
            </td>
            <td>
              <logic:present name="caseBean" property="parent">
                <bean:write name="caseBean" property="parent.gender" ignore="true"/>
              </logic:present>
              <logic:notPresent name="caseBean" property="parent">
                &nbsp;
              </logic:notPresent>
            </td>
            <td class="grey">
              <div align="right">
                <b>Race</b>
              </div>
            </td>
            <td>
              <logic:present name="caseBean" property="parent">
                <logic:present name="caseBean" property="parent.race">
                  <bean:write name="caseBean" property="parent.race" ignore="true"/>
                </logic:present>
                <logic:notPresent name="caseBean" property="parent.race">
                  &nbsp;
                </logic:notPresent>
              </logic:present>
              <logic:notPresent name="caseBean" property="parent">
                &nbsp;
              </logic:notPresent>
            </td>
          </tr>
          <tr class="white">
            <td class="grey">
              <div align="right">
                <b>Case Procedure</b>
              </div>
            </td>
            <td colspan="6">
              <bean:write name="caseBean" property="procedure" ignore="true"/>
              <logic:present name="caseBean" property="proc_other">
            	( <bean:write name="caseBean" property="proc_other" ignore="true"/> )
              </logic:present>
            </td>
          </tr>
          <tr class="white">
            <td class="grey">
              <div align="right">
                <b>Case Diagnosis - Tissue</b>
              </div>
            </td>
            <td colspan="6">
              <bigr:conditionalWrite name="caseBean" property="diagnosis" trueValue="<%= caseBean.getDiagnosis() %>" falseValue="<%= caseBean.getDiagnosis() %>" nullValue="Diagnosis Not Available from DDC or ILTDS"/>
              <logic:present name="caseBean" property="dx_Other">
                  		( <bean:write name="caseBean" property="dx_Other" ignore="true"/> )
              </logic:present>
              -
              <bigr:conditionalWrite name="caseBean" property="tissue" trueValue="<%= caseBean.getTissue() %>" falseValue="<%= caseBean.getTissue() %>" nullValue="Tissue Not Available from DDC"/>
              <logic:present name="caseBean" property="tissue" >
                <logic:equal name="caseBean" property="tissue" value="Other tissue">
                  <logic:present name="caseBean" property="tc_Origin_Other">
               			( <bean:write name="caseBean" property="tc_Origin_Other" ignore="true"/> )
                  </logic:present>
                </logic:equal>
              </logic:present>
            </td>
          </tr>
          <tr class="white">
            <td class="grey" align="right" onmouseout="return nd();"
                onmouseover="return overlib('<bean:message key="iltds.icp.case.status.comment"/>', FIXX, 10, FIXY, 10);">
              <b>Case Status</b>
            </td>
            <%
               // if the user is an Ardais user show the case status, DDC check,
               // and Raw Path report.  Otherwise, just show the case status
            %>
            <bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'> 
                <td>
                  <bean:write name="caseBean" property="status" ignore="true"/>
                </td>
          <% 
       	  StringBuffer theURLSB = new StringBuffer(250);
				  theURLSB.append("/library/Dispatch?op=PathInfoStart&case_id=");
				  if (caseBean != null) {
					theURLSB.append(caseBean.getCase_id());
				  } 
				  theURLSB.append("&donor_id=");
				  if (caseBean != null) {
					theURLSB.append(caseBean.getParent().getDonor_id());
				  }
				  theURLSB.append("&popUp=true");
				  String theURL = theURLSB.toString();
                %>                
<% if (ApiProperties.isEnabledDDC_Check()) { %>                
              <td class="grey">
                <div align="right">
                  <b>DDC Check</b>
                </div>
              </td>
              <td>                                 
                <a href="javascript:void(0);" onclick="openBrWindow('<html:rewrite page="<%= theURL %>"/>','DonorProfile<bean:write name="caseBean" property="parent.donor_id" ignore="true" filter="false"/>','scrollbars=yes,resizable=yes,status=yes,width=640,height=480')">
                  <bean:write name="caseBean" property="ddc_check" ignore="true"/>
                </a>
              </td>          
<% } else  { %>
							<td colspan="2">
							</td>
<% } %>
              <td class="grey">   
                <div align="right">
                  <b>Raw Path</b>
                </div>
              </td>
              <td>
                <logic:present name="caseBean" property="ascii_report">
                  <logic:equal name="caseBean" property="ascii_report" value="Y">
            	    <% 
            		  theURLSB = new StringBuffer(250);
					  theURLSB.append("/ddc/Dispatch?op=PathRawPrepare&popup=true&pathReportId=");
					  if (caseBean != null) {
						theURLSB.append(caseBean.getPath_report_id());
					  } 
					  theURL = theURLSB.toString();
            	    %>
                    <a href="javascript:void(0);" onclick="openBrWindow('<html:rewrite page="<%= theURL %>"/>','ASCII<bean:write name="caseBean" property="path_report_id" ignore="true"/>','scrollbars=yes,resizable=yes,status=yes,width=640,height=480')">
                      <bean:write name="caseBean" property="ascii_report" ignore="true"/>
                    </a>
                  </logic:equal>
                  <logic:equal name="caseBean" property="ascii_report" value="N">
                    <bean:write name="caseBean" property="ascii_report" ignore="true"/></a>
                  </logic:equal>
                </logic:present>
                <logic:notPresent name="caseBean" property="ascii_report">
                  &nbsp;
                </logic:notPresent>
              </td>
            </bigr:isInRole>
            <bigr:notIsInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'> 
                <td colspan="5">
                  <bean:write name="caseBean" property="status" ignore="true"/>
                </td>
            </bigr:notIsInRole>
          </tr>
          <tr class="white">
            <td class="grey">
              <div align="right">
                <b>Consent Date</b>
              </div>
            </td>
            <td> 
              <logic:present name="caseBean" property="consentDate.timestamp">
                <%-- We don't only collect month/yeat/time of consent (not day),
                  ** so that's all we display here. --%>
                <dt:format pattern="MMM yyyy h:mm a">
                  <dt:parse pattern="yyyy-MM-dd HH:mm:ss.SS">
                    <bean:write name="caseBean" property="consentDate.timestamp" ignore="true"/>
                  </dt:parse>
                </dt:format>
              </logic:present>
              <logic:notPresent name="caseBean" property="consentDate.timestamp">
                &nbsp;
              </logic:notPresent>
            </td>
            <td class="grey">
              <div align="right">
                <b>Verified Date</b>
              </div>
            </td>
            <td> 
              <logic:present name="caseBean" property="verified_date.timestamp">
                <dt:format pattern="MMM d, yyyy h:mm a">
                  <dt:parse pattern="yyyy-MM-dd HH:mm:ss.SS">
                    <bean:write name="caseBean" property="verified_date.timestamp" ignore="true"/>
                  </dt:parse>
                </dt:format>
              </logic:present>
              <logic:notPresent name="caseBean" property="verified_date.timestamp">
                &nbsp;
              </logic:notPresent>
            </td>
            <td class="grey">
              <div align="right">
                <b>Released Date</b>
              </div>
            </td>
            <td>
              <logic:present name="caseBean" property="release_date.timestamp">
                <dt:format pattern="MMM d, yyyy h:mm a">
                  <dt:parse pattern="yyyy-MM-dd HH:mm:ss.SS">
                    <bean:write name="caseBean" property="release_date.timestamp" ignore="true"/>
                  </dt:parse>
                </dt:format>
              </logic:present>
              <logic:notPresent name="caseBean" property="release_date.timestamp">
                &nbsp;
              </logic:notPresent>
            </td>
          </tr>
          <%
             if (showDINamePolicyAndIRB) {
          %>
              <tr class="white">
                <td class="grey">
                  <div align="right">
                    <b>Registration Site</b>
                  </div>
                </td>
                <td>
                  <bean:write name="caseBean" property="donor_location" ignore="true"/>
                </td>
                <td class="grey">
                  <div align="right">
                    <b>Policy</b>
                  </div>
                </td>
            <%
              String prefixedPolicyId = caseBean.getPrefixedPolicy_id();
              String policyName = caseBean.getPolicy_name();;
              String link = IcpUtils.prepareLink(prefixedPolicyId, policyName, securityInfo);
            %>
                <td>
                  <%= link %>
                </td>
                <td class="grey">
                  <div align="right">
                    <b>IRB Protocol</b>
                  </div>
                </td>
                <td>
                  <logic:present name="caseBean" property="protocol_name">
                    <bean:write name="caseBean" property="protocol_name" ignore="true"/>
                  </logic:present>
                  <logic:notPresent name="caseBean" property="protocol_name">
                    &nbsp;
                  </logic:notPresent>
                </td>  
              </tr>
          <%
             }
          %>
        </table>
      </td>
    </tr>
  </table>
</div>

