<%@ page errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.gulfstreambio.gboss.GbossFactory"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%@ page import="com.ardais.bigr.util.ArtsConstants"%>

<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>

<script>

function checkEitherPerformedOrOthers(testNumber, startIndex, count) {
	var isPerformed = (event.srcElement.value == "performed");
	var isShowResult = (event.srcElement.value == "show");
	var isChecked = (event.srcElement.checked);
	var isOtherChecked = false;
	var boxes = document.all["diagnosticTest.testResult"];
	var performedBox = document.all["diagnosticTest.performed"];
	var showBox = document.all["diagnosticTest.testShow"];
	var upperBound = parseInt(startIndex) + parseInt(count);
	var results = ""
	// If Performed is checked, then clear all others.
	if ((isPerformed) && (isChecked)) {
		for (var i = startIndex; i < upperBound; i++) {
			boxes[i].checked = false;
		}		
		document.all["diagnosticTest.performedHidden"][testNumber].value = "performed";
		document.all["diagnosticTest.testResultHidden"][testNumber].value = "";
	}
	else {

		// determine if anything else is checked.
		for (var i = startIndex; i < upperBound; i++) {
			if (boxes[i].checked) {
				isOtherChecked = true;
				if (results.length > 0) {
					results = results + "," + boxes[i].value;
				}
				else {
					results = boxes[i].value;
				}
			}
		}

		// If anything other than Performed is checked, then clear Performed;
		if (isOtherChecked) {
			performedBox[testNumber].checked = false;
			document.all["diagnosticTest.testResultHidden"][testNumber].value = results;
			document.all["diagnosticTest.performedHidden"][testNumber].value = "";
		}		
	}
	
	if (isChecked) {
		showBox[testNumber].checked = true;
		document.all["diagnosticTest.testShowHidden"][testNumber].value = "true";
	}
	
	if ((!isChecked) && (!isOtherChecked)) {
		showBox[testNumber].checked = false;
		document.all["diagnosticTest.testShowHidden"][testNumber].value = "false";
	}
	
	if ((isShowResult) && (!isChecked)) {
		document.all["diagnosticTest.testShowHidden"][testNumber].value = "false";
	}

	if ((isPerformed) && (!isChecked)) {
		document.all["diagnosticTest.performedHidden"][testNumber].value = "";
	}

	if (!isOtherChecked) {
		document.all["diagnosticTest.testResultHidden"][testNumber].value = "";
	}

}

function checkPsa() {
	// Get the inputs to be manipulated.
	var performed = document.all["diagnosticTest.psaPerformed"];
	var results = document.all["diagnosticTest.psaResult"];
	var show = document.all["diagnosticTest.psaShow"];

	// Manipulate the checkboxes appropriately.
	checkNonDiagTest(performed, results, show);
}

function checkDre() {
	// Get the inputs to be manipulated.
	var performed = document.all["diagnosticTest.drePerformed"];
	var results = document.all["diagnosticTest.dreResult"];
	var show = document.all["diagnosticTest.dreShow"];

	// Manipulate the checkboxes appropriately.
	checkNonDiagTest(performed, results, show);
}

function checkNonDiagTest(performed, results, show) {
	// Set some flags based on what was checked.
	var isPerformed = (event.srcElement.value == "performed");
	var isShow = (event.srcElement.value == "show");
	var isChecked = (event.srcElement.checked);

	// Something was checked.
	if (isChecked) {

		// If performed was checked, then uncheck all results and check show.
		if (isPerformed) {
			for (var i = 0; i < results.length; i++) {
				results[i].checked = false;
			}		
			show.checked = true;
		}
		
		// If show was checked, there's nothing special to do.
		else if (isShow) {
			;
		}
		
		// One of the results was checked, so uncheck performed and check show.
		else {
			performed.checked = false;
			show.checked = true;
		}
	}

	// Something was unchecked.  In this case, the only thing we need to do is
	// uncheck show if neither perform or any of the results remain checked.
	else {
		var uncheck = true;
		for (var i = 0; i < results.length; i++) {
			if (results[i].checked) {
				uncheck = false;
			}
		}
		if (uncheck) {
			uncheck = !performed.checked;
		}
		if (uncheck) {
			show.checked = false;
		}
	}
}

</script>
<body>
<!-- Diagnostic test tab -->
  <div id="diagnosticTestDiv" class="libraryTabContents" style="display: none;">
	  <table border="0" cellspacing="0" cellpadding="0" width="100%" class="foreground-small">
          
      <!-- Diagnostic test -->
		  <tr id="diagnosticTestTitle">
		    <td colspan="4">
		      <table border="0" cellspacing="0" cellpadding="3" class="foreground-small" width="100%">
		        <col width="90%">
				<col width="5%"> 
				<col width="5%">
				<tr>
					<td id="diagnosticTestTitle1" class="libraryTabTitle">
					<b><bean:message key="library.ss.query.diagnosticTest.tabname"/> -
					<bean:message key="library.ss.query.diagnosticTest.title.label.path"/></b>
	            	</td>
	            	<td id="diagnosticTestTitle3" class="libraryTabTitle">	              	
	              	<html:button property="clearQueryButton" styleClass="libraryButtons" onclick="clrQuery('diagnosticTest')"
							onmouseout="return nd()" onmouseover="return overlib('Resets all values on this tab to default values')">
								<bean:message key="library.ss.query.button.clearSection"/>
							</html:button>
	            	</td>
	            	<td id="diagnosticTestTitle2" class="libraryTabTitle">
	              	<a href="#top">Top</a>
              		</td>
              	</tr>
             </table>
          </td>
        </tr>
	  </table>
	  
	  <table border="0" cellspacing="0" cellpadding="0" width="100%" class="foreground-small">
        <tr>
	        <td style="padding: 0.5em 0 0 0;">
				<table border="0" cellspacing="0" cellpadding="0" width="100%" class="foreground-small" >
				    <col width="3%">
				    <col width="34%"> 
				    <col width="10%">
				    <col width="35%">
				    <col width="15%">
				    <col width="3%">
					<tbody>
			<tr>
					<td style="padding: 1em 1em 0em 1em;" colspan="6">
						<hr style="border: 1px solid #336699; height: 1px;">
					</td>
				</tr>
				
	    <tr>
					<td style="padding: 0em 1em 0em 1em;" colspan="6">
						<b><bean:message key="library.ss.query.diagnosticTest.description.path"/></b>
					</td>
				</tr>
				
				
				<tr>
					<td style="padding: 0em 1em 0em 1em;" colspan="6">
						<hr style="border: 1px solid #336699; height: 1px;">
					</td>
				</tr>
					<tr>
						<td>&nbsp;</td>
						<td colspan="5" align="left" style="padding-top: 1em;"><b>Cell Proliferation Tests</b></td>
					</tr>
					<%
					String desription = GbossFactory.getInstance().getDescription("CA00621C");
					%>
					<bigr:diagnosticTest conceptId="CA00621C" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_CELL_PROLIFERATION%>" resultList="cellProliferationList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA079915");
					%>
					<bigr:diagnosticTest conceptId="CA079915" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_CELL_PROLIFERATION%>" resultList="cellProliferationList" label="<%=desription%>"/>

					<tr>
						<td/>
						<td colspan="5" style="padding-top: 1em;"><b>Gene Studies</b></td>
					</tr>
					<%
					desription = GbossFactory.getInstance().getDescription("CA079951");
					%>
					<bigr:diagnosticTest conceptId="CA079951" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_GENES%>" resultList="genesList" label="<%=desription%>"/>

					<tr>
						<td/>
						<td colspan="5" style="padding-top: 1em;"><b>Immunohistochemistry Tests</b></td>
					</tr>
					<%
					desription = GbossFactory.getInstance().getDescription("CA079958");
					%>
					<bigr:diagnosticTest conceptId="CA079958" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA079956");
					%>
					<bigr:diagnosticTest conceptId="CA079956" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA079954");
					%>
					<bigr:diagnosticTest conceptId="CA079954" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA079953");
					%>
					<bigr:diagnosticTest conceptId="CA079953" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA05870C");
					%>
					<bigr:diagnosticTest conceptId="CA05870C" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA080118");
					%>
					<bigr:diagnosticTest conceptId="CA080118" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA07536C");
					%>
					<bigr:diagnosticTest conceptId="CA07536C" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA080164");
					%>
					<bigr:diagnosticTest conceptId="CA080164" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA080074");
					%>
					<bigr:diagnosticTest conceptId="CA080074" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA079844");
					%>
					<bigr:diagnosticTest conceptId="CA079844" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA079841");
					%>
					<bigr:diagnosticTest conceptId="CA079841" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA080095");
					%>
					<bigr:diagnosticTest conceptId="CA080095" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA079837");
					%>
					<bigr:diagnosticTest conceptId="CA079837" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA079849");
					%>
					<bigr:diagnosticTest conceptId="CA079849" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA079858");
					%>
					<bigr:diagnosticTest conceptId="CA079858" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA079793");
					%>
					<bigr:diagnosticTest conceptId="CA079793" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA079778");
					%>
					<bigr:diagnosticTest conceptId="CA079778" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA079821");
					%>
					<bigr:diagnosticTest conceptId="CA079821" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("63476009");
					%>
					<bigr:diagnosticTest conceptId="63476009" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY%>" resultList="immunoHistoChemistryList" label="<%=desription%>"/>

					<tr>
						<td/>
						<td colspan="5" style="padding-top: 1em;"><b>Immunophenotype Tests</b></td>
					</tr>
					<%
					desription = GbossFactory.getInstance().getDescription("CA03669C");
					%>
					<bigr:diagnosticTest conceptId="CA03669C" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOPHENOTYPE%>" resultList="immunoPhenoTypeList" label="<%=desription%>"/>
					<%
					desription = GbossFactory.getInstance().getDescription("CA03670C");
					%>
					<bigr:diagnosticTest conceptId="CA03670C" valueSet="<%=ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOPHENOTYPE%>" resultList="immunoPhenoTypeList" label="<%=desription%>"/>

<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_PSA_DRE%>">          

      <!-- Clinical Laboratory Tests -->

			<tr><td colspan="6">&nbsp;</td></tr>

		  <tr id="clinLabTestTitle">
		    <td colspan="6">
		      <table border="0" cellspacing="0" cellpadding="3" class="foreground-small" width="100%">
		        <col width="90%">
				<col width="5%"> 
				<col width="5%">
				<tr>
					<td id="clinLabTestTitle1" class="libraryTabTitle">
					<b><bean:message key="library.ss.query.diagnosticTest.tabname"/> -
					<bean:message key="library.ss.query.diagnosticTest.title.label.clintests"/></b>
	            	</td>
	            	<td id="clinLabTestTitle3" class="libraryTabTitle">	              	
	              	<span class="fakeLink" onclick="clrQuery('clinLabTest');"><bean:message key="library.ss.query.button.clearSection"/></span>
	            	</td>
	            	<td id="clinLabTestTitle2" class="libraryTabTitle">
	              	<a href="#top">Top</a>
              		</td>
              	</tr>
             </table>
          </td>
        </tr>

				<tr>
					<td/>
					<td align="left" style="padding-top: 0.5em;">
						<bean:message key="library.ss.query.tests.psa"/>
	        </td>
					<td nowrap>
						<html:checkbox property="diagnosticTest.psaPerformed" value="performed" onclick="checkPsa();"/>Performed&nbsp;
					</td>
					<td align="center"> 
						<logic:iterate 	name="queryForm"
						              	property="diagnosticTest.psaResultList" 
                     				id="psaResult"
                     				type="com.gulfstreambio.gboss.GbossValue">
            	<html:multibox property="diagnosticTest.psaResult" onclick="checkPsa();" value="<%=psaResult.getCui()%>"/>
            		<%=psaResult.getDescription()%><br>
						</logic:iterate>
					</td>
					<td nowrap align="right"> 
						<html:checkbox property="diagnosticTest.psaShow" value="show" onclick="checkPsa();"/>Show Results&nbsp;
					</td>
					<td/>
				</tr>
          
      <!-- Clinical Findings -->

			<tr><td>&nbsp;</td></tr>

		  <tr id="clinFindTitle">
		    <td colspan="6">
		      <table border="0" cellspacing="0" cellpadding="3" class="foreground-small" width="100%">
		        <col width="90%">
				<col width="5%"> 
				<col width="5%">
				<tr>
					<td id="clinFindTitle1" class="libraryTabTitle">
					<b><bean:message key="library.ss.query.diagnosticTest.tabname"/> -
					<bean:message key="library.ss.query.diagnosticTest.title.label.clinfindings"/></b>
	            	</td>
	            	<td id="clinFindTitle3" class="libraryTabTitle">	              	
	              	<span class="fakeLink" onclick="clrQuery('clinFind');"><bean:message key="library.ss.query.button.clearSection"/></span>
	            	</td>
	            	<td id="clinFindTitle2" class="libraryTabTitle">
	              	<a href="#top">Top</a>
              		</td>
              	</tr>
             </table>
          </td>
        </tr>

				<tr>
					<td/>
					<td align="left" style="padding-top: 0.5em;">
						<bean:message key="library.ss.query.tests.dre"/>
	        </td>
					<td nowrap>
						<html:checkbox property="diagnosticTest.drePerformed" value="performed" onclick="checkDre();"/>Performed&nbsp;
					</td>
					<td align="center"> 
						<logic:iterate 	name="queryForm"
						              	property="diagnosticTest.dreResultList" 
                     				id="dreResult"
                     				type="com.gulfstreambio.gboss.GbossValue">
            	<html:multibox property="diagnosticTest.dreResult" onclick="checkDre();" value="<%=dreResult.getCui()%>"/>
            		<%=dreResult.getDescription()%>
						</logic:iterate>
					</td>
					<td nowrap align="right"> 
						<html:checkbox property="diagnosticTest.dreShow" value="show" onclick="checkDre();"/>Show Results&nbsp;
					</td>
					<td/>
				</tr>

			<tr><td>&nbsp;</td></tr>

</bigr:hasPrivilege>

						</tbody>
					</table>
        </td>
      </tr>
		</table>

	</div>
</body>