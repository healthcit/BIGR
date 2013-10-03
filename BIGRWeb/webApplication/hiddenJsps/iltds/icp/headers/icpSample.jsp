<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.util.WebUtils" %> 
<%@ page import="com.ardais.bigr.security.SecurityInfo" %> 
<%@ page import="com.ardais.bigr.api.ApiProperties"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%@ taglib uri="/tld/datetime"     prefix="dt"   %>

<bean:define id="icpData" name="icpData"
  type="com.ardais.bigr.iltds.databeans.IcpData" />
<bean:define id="sampleBean" name="icpData" property="data"
  type="com.ardais.bigr.iltds.databeans.SampleData"/>
  
<%
	SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
	boolean isIcpSuperUser = securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER);
	
	// The samples Donor Institution is only displayed to ICP Superusers and
	// users who are in the same account that the same came from.
	boolean isDiVisible = isIcpSuperUser;
	if (! isDiVisible) {
	  isDiVisible = securityInfo.getAccount().equals(sampleBean.getAccountId());
	}
%>

<div align="left"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
          <tr class="green"> 
            <td colspan="2" align="center"> 
              <b>Sample Information: <%= sampleBean.getSample_id_v(securityInfo, false) %>
                <logic:present name="sampleBean" property="topLine">
                  <logic:present name="sampleBean" property="topLine.sampleTypeDisplay">
                    (<%=sampleBean.getTopLine().getSampleTypeDisplay()%>)
                  </logic:present>
                </logic:present>
              </b>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Donor Id:</b>
            </td>
            <td>
              <logic:present name="sampleBean" property="parent">
                <logic:present name="sampleBean" property="parent.parent">
                  <logic:present name="sampleBean" property="parent.parent.parent">
                    <bigr:icpLink><bean:write name="sampleBean" property="parent.parent.parent.donor_id" ignore="true"/></bigr:icpLink> 
                  <logic:notEmpty name="sampleBean" property="parent.parent.parent.customer_id">
                    (<bean:write name="sampleBean" property="parent.parent.parent.customer_id" ignore="true" filter="true"/>)
                  </logic:notEmpty>
                  </logic:present>
                </logic:present>
              </logic:present>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Case Id:</b>
            </td>
            <td>
              <logic:present name="sampleBean" property="parent">
                <logic:present name="sampleBean" property="parent.parent">
                  <bigr:icpLink><bean:write name="sampleBean" property="parent.parent.case_id" ignore="true"/></bigr:icpLink> 
                  <logic:notEmpty name="sampleBean" property="parent.parent.customer_id">
                    (<bean:write name="sampleBean" property="parent.parent.customer_id" ignore="true" filter="true"/>)
                  </logic:notEmpty>
                </logic:present>
              </logic:present>
            </td>
          </tr>
          <logic:present name="sampleBean" property="source">
            <tr class="white"> 
              <td class="grey" align="right"> 
                <b>Source:</b>
              </td>
              <td>
                <bean:write name="sampleBean" property="source" ignore="true"/>
              </td>
            </tr>
          </logic:present>
          <logic:present name="sampleBean" property="asm_position">
            <tr class="white"> 
              <td class="grey" align="right"> 
                <b>ASM Position:</b>
              </td>
              <td>
                <bean:write name="sampleBean" property="asm_position" ignore="true"/>
              </td>
            </tr>
          </logic:present>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Diagnosis/Tissue:</b>
            </td>
            <td>
               <logic:notPresent name="sampleBean" property="topLine.diagnosis">
                  Diagnosis and Tissue Information Not Available
               </logic:notPresent>
               <logic:present name="sampleBean" property="topLine.diagnosis">
                  <bean:write name="sampleBean" property="topLine.diagnosis" ignore="true"/>
                  <logic:present name="sampleBean" property="dxOther">
                  		( <bean:write name="sampleBean" property="dxOther" ignore="true"/> )
                  </logic:present>
                   - [
                  <bean:write name="sampleBean"  property="topLine.tissue" ignore="true"/>
                  <logic:equal name="sampleBean" property="topLine.tissue" value="Other tissue">
                  	<logic:present name="sampleBean" property="tcOtherOrigin">
                  			( <bean:write name="sampleBean" property="tcOtherOrigin" ignore="true"/> )
                  	</logic:present>
                  </logic:equal>
                  <logic:present name="sampleBean" property="topLine.tissueFinding">
                    / <bean:write  name="sampleBean"  property="topLine.tissueFinding" ignore="true"/>
                  	<logic:present name="sampleBean" property="tcOtherFinding">
                  		( <bean:write name="sampleBean" property="tcOtherFinding" ignore="true"/> )
                  	</logic:present>
                  </logic:present>	
                   ] - 
                  <bean:write name="sampleBean" property="topLine.sampleTypeDisplay" ignore="true"/> - 
                  <bean:write name="sampleBean" property="topLine.appearance" ignore="true"/>
               </logic:present>
            </td>
          </tr>
<% if (isDiVisible) { %>
          <tr class="white">
            <td class="grey" align="right"> 
              <b>Registration Site:</b>
            </td>
            <td>
              <bean:write name="sampleBean" property="account_name" ignore="true"/>
            </td>
          </tr>
<% } // if isDiVisible %>
<% if (ApiProperties.isEnabledLegacy()) { %>
          <tr class="white">
            <td class="grey" align="right" width="25%">
              <b>Slides:</b>
            </td>
            <td>
              <bean:write name="sampleBean" property="slide_count" ignore="true"/>
              <logic:present name="sampleBean" property="latestSlide">
                (Latest slide: 
                <bean:write name="sampleBean" property="latestSlide.slide_id" ignore="true"/>
                <logic:present name="sampleBean" property="latestSlide.creation_date">
                  created
                  <dt:format pattern="MMM d, yyyy h:mm a">
                    <dt:parse pattern="yyyy-MM-dd HH:mm:ss.SS">
                      <bean:write name="sampleBean" property="latestSlide.creation_date.timestamp" ignore="true"/>
                    </dt:parse>
                  </dt:format>
                </logic:present>
                )
              </logic:present>
            </td>
          </tr>
<% } // if legacy %>          
          <tr class="white">
            <td class="grey" align="right"> 
              <b>Repeat Donor:</b>
            </td>
            <td>
	   	      <logic:present name="sampleBean" property="parent">
	            <logic:present name="sampleBean" property="parent.parent">
	              <logic:present name="sampleBean" property="parent.parent.parent">
	                <bean:write name="sampleBean" property="parent.parent.parent.repeatDonorDisplay" ignore="true"/> 
	              </logic:present>
	            </logic:present>
	          </logic:present>
			</td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
