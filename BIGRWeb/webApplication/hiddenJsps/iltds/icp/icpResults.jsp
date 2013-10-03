<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %> 
<%@ page import="com.ardais.bigr.iltds.helpers.TypeFinder" %> 
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"   prefix="bean" %>
<%@ taglib uri="/tld/struts-logic"  prefix="logic"%>
<%@ taglib uri="/tld/struts-template"  prefix="template"%>

<bean:define id="icpData" name="icpData"
  type="com.ardais.bigr.iltds.databeans.IcpData" />
<bean:define id="icpType" name="icpData" property="type" type="java.lang.String" />

<%
  String templateName = null;
  String objectId = "";
  if (icpData.isPrinterFriendly()) {
    templateName = "/hiddenJsps/iltds/icp/icpResultsTemplatePrinterFriendly.jsp";
  }
  else {
    templateName = "/hiddenJsps/iltds/icp/icpResultsTemplate.jsp";
  }
  if (!ApiFunctions.isEmpty(icpData.getId())) {
    objectId = icpData.getId();
  }
%>  
  <template:insert template='<%=templateName%>'>
  <template:put name='title' content='ICP' direct="true"/>
  <template:put name='objectId' content='<%=objectId%>' direct="true"/>
  <logic:equal name="icpType" value="<%= TypeFinder.SAMPLE %>">
    <template:put name='title' content='ICP: Sample' direct="true"/>
  </logic:equal>
  <logic:equal name="icpType" value="<%= TypeFinder.BOX %>">
    <template:put name='title' content='ICP: Box' direct="true"/>
  </logic:equal>
  <logic:equal name="icpType" value="<%= TypeFinder.CASE %>">
    <template:put name='title' content='ICP: Case' direct="true"/>
  </logic:equal>
  <logic:equal name="icpType" value="<%= TypeFinder.DONOR %>">
    <template:put name='title' content='ICP: Donor' direct="true"/>
  </logic:equal>
  <logic:equal name="icpType" value="<%= TypeFinder.LOGICALREPOSITORY %>">
    <template:put name='title' content='ICP: Inventory Group' direct="true"/>
  </logic:equal>
  <logic:equal name="icpType" value="<%= TypeFinder.POLICY %>">
    <template:put name='title' content='ICP: Policy' direct="true"/>
  </logic:equal>
  <logic:equal name="icpType" value="<%= TypeFinder.MANIFEST %>">
    <template:put name='title' content='ICP: Manifest' direct="true"/>
  </logic:equal>
  <logic:equal name="icpType" value="<%= TypeFinder.REQUEST %>">
    <template:put name='title' content='ICP: Request' direct="true"/>
  </logic:equal>
  <logic:equal name="icpType" value="<%= TypeFinder.BOXLAYOUT %>">
    <template:put name='title' content='ICP: Box Layout' direct="true"/>
  </logic:equal>
  <logic:equal name="icpType" value="<%= TypeFinder.FORMDEFINITION %>">
    <template:put name='title' content='ICP: Form Definition' direct="true"/>
  </logic:equal>
  <logic:equal name="icpType" value="<%= TypeFinder.ROLE %>">
    <template:put name='title' content='ICP: Role' direct="true"/>
  </logic:equal>
  
  <logic:equal name="icpData" property="showHistoryOnly" value="true">
    <template:put name='query' content='/hiddenJsps/iltds/icp/icpQuerySmall.jsp'/>
    <template:put name='header' content='/hiddenJsps/iltds/icp/headers/icpNoData.jsp'/>
    <%-- There is no detail section in this case. --%>
  </logic:equal>

  <logic:equal name="icpData" property="showHistoryOnly" value="false">
    <logic:notPresent name="icpData" property="data">
      <template:put name='query' content='/hiddenJsps/iltds/icp/icpQuerySmall.jsp'/>
      <template:put name='header' content='/hiddenJsps/iltds/icp/headers/icpNoData.jsp'/>
      <%-- There is no detail section in this case. --%>
    </logic:notPresent>

    <logic:present name="icpData" property="data">
      <logic:equal name="icpType" value="<%= TypeFinder.SAMPLE %>">
        <template:put name='query' content='/hiddenJsps/iltds/icp/icpQuerySmall.jsp'/>
        <template:put name='header' content='/hiddenJsps/iltds/icp/headers/icpSample.jsp'/>
        <template:put name='middle' content='/hiddenJsps/iltds/icp/middles/icpSample.jsp'/>
        <template:put name='detail' content='/hiddenJsps/iltds/icp/details/icpSample.jsp'/>
      </logic:equal>
      <logic:equal name="icpType" value="<%= TypeFinder.BOX %>">
        <template:put name='query' content='/hiddenJsps/iltds/icp/icpQuerySmall.jsp'/>
        <template:put name='header' content='/hiddenJsps/iltds/icp/headers/icpBox.jsp'/>
        <template:put name='detail' content='/hiddenJsps/iltds/icp/details/icpBox.jsp'/>
      </logic:equal>
      <logic:equal name="icpType" value="<%= TypeFinder.CASE %>">
        <template:put name='query' content='/hiddenJsps/iltds/icp/icpQuerySmall.jsp'/> 
        <template:put name='header' content='/hiddenJsps/iltds/icp/headers/icpCase.jsp'/>
        <template:put name='middle' content='/hiddenJsps/iltds/icp/middles/icpCase.jsp'/>
        <%-- There is no detail section in this case. --%>
      </logic:equal>
      <logic:equal name="icpType" value="<%= TypeFinder.DONOR %>">
        <template:put name='query' content='/hiddenJsps/iltds/icp/icpQuerySmall.jsp'/> 
        <template:put name='header' content='/hiddenJsps/iltds/icp/headers/icpDonor.jsp'/>
        <template:put name='middle' content='/hiddenJsps/iltds/icp/middles/icpDonor.jsp'/>
        <%-- There is no detail section in this case. --%>
      </logic:equal>
      <logic:equal name="icpType" value="<%= TypeFinder.LOGICALREPOSITORY %>">
        <template:put name='query' content='/hiddenJsps/iltds/icp/icpQuerySmall.jsp'/> 
        <template:put name='header' content='/hiddenJsps/iltds/icp/headers/icpLogicalRepository.jsp'/>
        <template:put name='detail' content='/hiddenJsps/iltds/icp/details/icpLogicalRepository.jsp'/>
      </logic:equal>
      <logic:equal name="icpType" value="<%= TypeFinder.POLICY %>">
        <template:put name='query' content='/hiddenJsps/iltds/icp/icpQuerySmall.jsp'/> 
        <template:put name='header' content='/hiddenJsps/iltds/icp/headers/icpPolicy.jsp'/>
      </logic:equal>
      <logic:equal name="icpType" value="<%= TypeFinder.MANIFEST %>">
        <template:put name='query' content='/hiddenJsps/iltds/icp/icpQuerySmall.jsp'/> 
        <template:put name='header' content='/hiddenJsps/iltds/icp/headers/icpManifest.jsp'/>
        <%-- There is no detail section in this case. --%>
      </logic:equal>
      <logic:equal name="icpType" value="<%= TypeFinder.REQUEST %>">
        <template:put name='query' content='/hiddenJsps/iltds/icp/icpQuerySmall.jsp'/> 
        <template:put name='header' content='/hiddenJsps/iltds/icp/headers/icpRequest.jsp'/>
        <%-- There is no detail section in this case. --%>
      </logic:equal>
      <logic:equal name="icpType" value="<%= TypeFinder.BOXLAYOUT %>">
        <template:put name='query' content='/hiddenJsps/iltds/icp/icpQuerySmall.jsp'/> 
        <template:put name='header' content='/hiddenJsps/iltds/icp/headers/icpBoxLayout.jsp'/>
      </logic:equal>
      <logic:equal name="icpType" value="<%= TypeFinder.FORMDEFINITION %>">
        <template:put name='query' content='/hiddenJsps/iltds/icp/icpQuerySmall.jsp'/>
        <template:put name='header' content='/hiddenJsps/iltds/icp/headers/icpFormDefinition.jsp'/>
      </logic:equal>
      <logic:equal name="icpType" value="<%= TypeFinder.ROLE %>">
        <template:put name='query' content='/hiddenJsps/iltds/icp/icpQuerySmall.jsp'/> 
        <template:put name='header' content='/hiddenJsps/iltds/icp/headers/icpRole.jsp'/>
      </logic:equal>
      <logic:equal name="icpType" value="<%= TypeFinder.MULTIPLEIDS %>">
        <template:put name='query' content='/hiddenJsps/iltds/icp/icpQuerySmall.jsp'/> 
        <%-- There is no header section in this case. --%>
        <%-- There is no middle section in this case. --%>
        <template:put name='detail' content='/hiddenJsps/iltds/icp/headers/icpMultipleIds.jsp'/>
      </logic:equal>
      
    </logic:present>
  </logic:equal> <%-- End icpData.showHistoryOnly == false --%>
</template:insert>
