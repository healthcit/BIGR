<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.util.WebUtils" %> 
<%@ page import="com.gulfstreambio.kc.form.def.query.QueryFormDefinition"%>

<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%
// The KC tab of the SampleSelectionQuery JSP.
%>

<bean:define name="queryForm" id="myForm" type="com.ardais.bigr.library.web.form.QueryForm"/>

<%
String txType = request.getParameter("txType");

QueryFormDefinition selectedQueryForm = myForm.getKc().getQueryFormDefinition();
String selectedQueryFormId = (selectedQueryForm == null) ? null : selectedQueryForm.getFormDefinitionId();
%>

	<!-- KC tab -->
	<div id="kcDiv" class="libraryTabContents" style="display: none;">

<script type="text/javascript">
GSBIO.kc.query.Elements.formDefinitionId = '<%=selectedQueryFormId == null ? "" : selectedQueryFormId%>';

function getKcQueryForm() {
  var dd = $("kcFormDefinitionId");
  var id = dd.getValue();
  setActionButtonEnabling(false);
  if (id) {
    var url = '<html:rewrite page="/library/kc/getQueryForm"/>' + '.do';
  	var pars = {"formDefinitionId": id, 
  	            "txType": $("txType").value};
  	var ajaxUpdater = new Ajax.Updater(
  				'kcQueryTab', 
  				url, 
  				{
  				  onComplete: function() {setActionButtonEnabling(true);},
  					method: 'post', 
  					parameters: pars, 
  					evalScripts: true
  				});
  	$('kcQueryTab').up('body').focus();
  }
  else {
    clrQuery('selected_null_KC_form');
  }
}
</script>

<html:hidden property="kc.queryCriteria"/>

    <table border="0" cellspacing="0" cellpadding="0" class="foreground-small" width="100%">
      <col width="5%">
      <col width="90%">
      <col width="5%">
			<tbody>

				<!-- KC.title -->
		    <tr id="kcTitle">
		      <td colspan="3">
		        <table border="0" cellspacing="0" cellpadding="0" class="foreground-small" width="100%">
		          <col width="90%">
				      <col width="5%"> 
				      <col width="5%"> 
							<tbody>
	            	<td id="kcTitle1" class="libraryTabTitle">
									<b>
										<bean:message key="library.ss.query.kc.tabname"/> -
										<bean:message key="library.ss.query.kc.title.label"/>
									</b>
	    		      </td>
	    		      <td id="kcTitle3" class="libraryTabTitle">
	              	<html:button property="clearQueryButton" styleClass="libraryButtons" onclick="clrQuery('kc')"
							onmouseout="return nd()" onmouseover="return overlib('Resets all values on this tab to default values')">
								<bean:message key="library.ss.query.button.clearSection"/>
							</html:button>
	              	</td>
	            	<td id="kcTitle2" class="libraryTabTitle">
			          	<a href="#top">Top</a>
	    		    	</td>
			        </tbody>
		        </table>
		      </td>
		    </tr>

		    <tr class="white">
		      <td style="padding: 1em 0 0 0">&nbsp;</td>
		      <td>
		      Please select a query form:
		      <select id="kcFormDefinitionId" name="queryFormDefinitionId" onchange="getKcQueryForm();">
            <option value="">Select</option>
<%
QueryFormDefinition[] kcQueryForms = myForm.getKc().getQueryFormDefinitions();
for (int i = 0; i < kcQueryForms.length; i++) {
  String queryFormId = kcQueryForms[i].getFormDefinitionId();
%>
            <option value="<%=queryFormId%>" <%=queryFormId.equals(selectedQueryFormId) ? "selected" : "" %>>
  	  	        <%=kcQueryForms[i].getName()%>
   	        </option>
<%
}
%>
		      </select>
		      <br><span style="font-size: smaller; font-style: italic">(Note: only a single query form can be used to run a query)</span>
		      </td>
		      <td>&nbsp;</td>
		    </tr>

		    <tr class="white">
		      <td colspan="3" style="padding: 1em 0 0 0">&nbsp;</td>
		    </tr>

		    <tr class="white">
		      <td>&nbsp;</td>
		      <td id="kcQueryTab" style="padding: 0; padding-top: 0.25em;">
<%
if (selectedQueryForm != null) {
  WebUtils.commonKcQuerySetup(request, txType, selectedQueryForm);
%>
						<jsp:include page="/hiddenJsps/kc/query/queryCriteria.jsp" flush="true"/>
<%
}
%>
		      </td>
		      <td>&nbsp;</td>
		    </tr>
	 		</tbody>
		</table>
	</div>
