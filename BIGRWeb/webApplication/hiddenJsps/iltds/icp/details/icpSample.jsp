<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.util.WebUtils" %> 
<%@ page import="com.ardais.bigr.security.SecurityInfo" %> 
<%@ page import="com.ardais.bigr.api.ApiProperties"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/datetime" prefix="dt" %>
<%
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
%>

<bean:define id="icpData" name="icpData"
  type="com.ardais.bigr.iltds.databeans.IcpData" />
<bean:define id="sampleBean" name="icpData" property="data"
  type="com.ardais.bigr.iltds.databeans.SampleData"/>

<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
  <tr>
    <td>
      <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
        <tr class="white">
          <td valign="top">
            <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
              <tr>
                <td>
                  <table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
                    <tr class="yellow">
                      <td colspan="4" align="center">
                        <b>Current Sample Information</b>
                      </td>
                    </tr>
                    <tr class="white"> 
                      <td nowrap class="grey" align="right" width="30%"> 
                        <b>Box Barcode ID:</b>
                      </td>
                      <td nowrap width="20%">
                        <logic:present name="sampleBean" property="box">
                          <bigr:icpLink><bean:write name="sampleBean" property="box.boxId" ignore="true"/></bigr:icpLink>
                        </logic:present>
                      </td>
                      <td nowrap class="grey" align="right"> 
                        <b>Cell Location:</b>
                      </td>
                      <td nowrap>
                        <bean:write name="sampleBean" property="cell_ref_location_v" ignore="true"/>
                      </td>
                    </tr>
                    <tr class="white">
                      <td class="grey" align="right">
                        <b>Box Location:</b>
                      </td>
                      <td colspan="3">
                        <%= sampleBean.getSampleLocationDisplay(securityInfo) %>
                      </td>
                    </tr>
                    <tr class="white">
                      <td class="grey" align="right">
                        <b>Inventory Status:</b>
                      </td>
                      <td colspan="3">
                        <bean:write name="sampleBean" property="inventoryStatusDisplay" ignore="true"/>
                      </td>
                    </tr>
<% if (ApiProperties.isEnabledLegacy()) { %>                   
                    <tr class="white"> 
                      <td nowrap class="grey" align="right"> 
                        <b>Pathology QC:</b>
                      </td>
                      <td colspan="3">
                        <%= sampleBean.getPathQcStatusDisplay(securityInfo) %>
                      </td>
                    </tr>
<% } %>      
<logic:equal name="sampleBean" property="hasHistoInfo" value="true">
                    <tr class="white">
                      <td class="grey" align="right">
                        <b>Histology:</b>
                      </td>
                      <td colspan="3">
                        <%= sampleBean.getHistoInfoHtml(securityInfo) %>
                      </td>
                    </tr>
</logic:equal>                    
                    <tr class="white">
                      <td class="grey" align="right">
                        <b>Parent Sample(s):</b>
                      </td>
                      <td colspan="3">
                        <%= sampleBean.getParentSampleIdsHtml(securityInfo) %>
                      </td>
                    </tr>
                    <tr class="white">
                      <td class="grey" align="right">
                        <b>Child Sample(s):</b>
                      </td>
                      <td colspan="3">
                        <%= sampleBean.getChildSampleIdsHtml(securityInfo) %>
                      </td>
                    </tr>
                    <tr class="white">
                      <td class="grey" align="right">
                        <b>Inventory Groups:</b>
                      </td>
                      <td colspan="3">
                        <%= sampleBean.getLogicalRepositoryNamesHtml(securityInfo) %>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
          <td valign="top"> 
            <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
                    <tr class="yellow"> 
                      <td colspan="2" align="center"> 
                        <b>Sample Date Information</b>
                      </td>
                    </tr>
                    <tr class="white">
                      <td nowrap class="grey" align="right"> 
                        <b>Creation Date:</b>
                      </td>
                      <td>
                        <bean:write name="sampleBean" property="bornDateDisplay" ignore="true"/>
                      </td>
                    </tr>
                    <tr class="white"> 
                      <td nowrap class="grey" align="right"> 
                        <b>Case Release Date:</b>
                      </td>
                      <td>
                        <logic:present name="sampleBean" property="parent">
                        <logic:present name="sampleBean" property="parent.parent">
                        <logic:present name="sampleBean" property="parent.parent.release_date">
                        <logic:present name="sampleBean" property="parent.parent.release_date.timestamp">
                          <dt:format pattern="MMM d, yyyy h:mm a">
                            <dt:parse pattern="yyyy-MM-dd HH:mm:ss.SS">
                              <bean:write name="sampleBean" property="parent.parent.release_date.timestamp" ignore="true"/>
                            </dt:parse>
                          </dt:format>
                        </logic:present>
                        </logic:present>
                        </logic:present>
                        </logic:present>
                      </td>
                    </tr>
                    <tr class="white"> 
                      <td nowrap class="grey" align="right"> 
                        <b>Consent Verified Date:</b>
                      </td>
                      <td>
                        <logic:present name="sampleBean" property="parent">
                        <logic:present name="sampleBean" property="parent.parent">
                        <logic:present name="sampleBean" property="parent.parent.verified_date">
                        <logic:present name="sampleBean" property="parent.parent.verified_date.timestamp">
                          <dt:format pattern="MMM d, yyyy h:mm a">
                            <dt:parse pattern="yyyy-MM-dd HH:mm:ss.SS">
                              <bean:write name="sampleBean" property="parent.parent.verified_date.timestamp" ignore="true"/>
                            </dt:parse>
                          </dt:format>
                        </logic:present>
                        </logic:present>
                        </logic:present>
                        </logic:present>
                      </td>
                    </tr>
                    <tr class="white"> 
                      <td nowrap class="grey" align="right"> 
                        <b>Last Transfer Date:</b>
                      </td>
                      <td>
                        <logic:present name="sampleBean" property="last_transfer_status.date">
                          <dt:format pattern="MMM d, yyyy h:mm a">
                            <dt:parse pattern="yyyy-MM-dd HH:mm:ss.SS">
                              <bean:write name="sampleBean" property="last_transfer_status.date.timestamp" ignore="true"/>
                            </dt:parse>
                          </dt:format>
                        </logic:present>
                      </td>
                    </tr>
                    <tr class="white"> 
                      <td nowrap class="grey" align="right"> 
                        <b>First Arrived at Supplier Biorepository:</b>
                      </td>
                      <td nowrap>
                        <logic:present name="sampleBean" property="receipt_date">
                        <logic:present name="sampleBean" property="receipt_date.timestamp">
                          <dt:format pattern="MMM d, yyyy h:mm a">
                            <dt:parse pattern="yyyy-MM-dd HH:mm:ss.SS">
                              <bean:write name="sampleBean" property="receipt_date.timestamp" ignore="true"/>
                            </dt:parse>
                          </dt:format>
                        </logic:present>
                        </logic:present>
                      </td>
                    </tr>
                  </table>
                </td>
            </table>
          </td>
        </tr>
        </table>
      </td>
  </tr>
</table>
<br/>
