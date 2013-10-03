<%@ taglib uri='/tld/bigr' prefix='bigr' %>
<%@ taglib uri="/tld/struts-template" prefix="template" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>

<script>

var origDiagnosis = '<bean:write name="limsCreateEvaluationForm" property="diagnosis"/>';
var origDiagnosisOther = '<bean:write name="limsCreateEvaluationForm" property="diagnosisOther"/>';
var origTissueOfOrigin = '<bean:write name="limsCreateEvaluationForm" property="tissueOfOrigin"/>';
var origTissueOfOriginOther = '<bean:write name="limsCreateEvaluationForm" property="tissueOfOriginOther"/>';
var origTissueOfFinding = '<bean:write name="limsCreateEvaluationForm" property="tissueOfFinding"/>';
var origTissueOfFindingOther = '<bean:write name="limsCreateEvaluationForm" property="tissueOfFindingOther"/>';

function restoreOriginalListOptions(oInput, oSel, value)
{
	oInput.value = "";
	var originalListValues = masterListArray[oSel.name];
	
	var selectedIndex = -1;
	if (originalListValues != null) {
		oSel.length = 0;
		for (var i = 0; i < originalListValues.length; i++) {
			oSel.options[oSel.length] = new Option(originalListValues[i].text,
				originalListValues[i].value,
                originalListValues[i].defaultSelected,
                originalListValues[i].selected
                );
            if (originalListValues[i].value == value) {
            	selectedIndex = i;
            }
        }
    }
    if (selectedIndex != -1) {
    	oSel.selectedIndex = selectedIndex;
    }
}

function restoreOriginalOtherForList(selVal, otherName, otherCode, value) {
	if(selVal == otherCode)	{
		if (document.forms[0].elements[otherName].value == 'N/A') {
			document.forms[0].elements[otherName].value = value;
		}
		document.forms[0].elements[otherName].disabled = false;
		document.forms[0].elements[otherName].focus();
	}
	else {
		   document.forms[0].elements[otherName].value = "N/A";
		   document.forms[0].elements[otherName].disabled = true;
	}
}

</script>

<div align="left"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
          <tr class="green"> 
            <td colspan="2"> 
              <div align="center"><b>Verification</b></div>
            </td>
          </tr>
          <tr class="white"> 
			<td colspan="2" nowrap>
			  <div align="center">
			  	<b>Search String:</b>
			  	<input type="text" name="search" size=30 maxlength ="30" tabindex="2" onKeyDown="return checkEnter(event);">
			  	<input type="button" name="clear" tabindex="-1" value="Clear" onClick="this.form.search.value='';">
			  </div>
			</td>
		  </tr>
		  <tr class="white">
			<td class="grey">
			  <div align="right"><b>Sample Pathology:</b></div>
			</td>
			<td class="white" nowrap>
			  <bigr:diagnosisWithOther name="limsCreateEvaluationForm"  property = "diagnosis"  propertyLabel = "Sample Pathology:" firstDisplayValue = "Select Sample Pathology" onChange="showOtherForList(this.value,'diagnosisOther','CA00038D^^');" mode="basic" cssClass="width600"/>					  	
			  <input type="button" name="applyPathology" value="Search" tabindex="3" onClick="findListOptions(this.form.search,this.form.diagnosis);showOtherForList(this.form.diagnosis.value,'diagnosisOther','CA00038D^^');">
			  <input type="button" name="refreshPathology" value="Refresh" tabindex="-1" onClick="restoreOriginalListOptions(this.form.search,this.form.diagnosis,origDiagnosis);restoreOriginalOtherForList(this.form.diagnosis.value,'diagnosisOther','CA00038D^^',origDiagnosisOther);"> 
			  <br>If Other is Selected:&nbsp;
			  <bigr:otherText name="limsCreateEvaluationForm" property="diagnosisOther" 
			  	parentProperty="diagnosis" otherCode="CA00038D^^"
			  	size="50" maxlength="200"  onkeydown="return checkEnter(event);"/>
			</td>		  
		  </tr>		  
		  <tr class="white">
		    <td class="grey">
		      <div align="right">
		      	<b>Tissue of Origin of Dx:</b>
		      </div>
			</td>			
			<td class="white" nowrap>
			  <bigr:tissueWithOther name="limsCreateEvaluationForm"  property = "tissueOfOrigin"  propertyLabel = "Tissue of Origin of Dx::" firstDisplayValue = "Select Tissue of Origin of Dx" onChange="showOtherForList(this.value,'tissueOfOriginOther','91723000');" mode="basic" cssClass="width600"/>
			  <input type="button" name="applyOrigin" value="Search" tabindex="5" onClick="findListOptions(this.form.search,this.form.tissueOfOrigin);showOtherForList(this.form.tissueOfOrigin.value,'tissueOfOriginOther','91723000');">
			  <input type="button" name="refreshFinding" value="Refresh" tabindex="-1" onClick="restoreOriginalListOptions(this.form.search,this.form.tissueOfOrigin,origTissueOfOrigin);restoreOriginalOtherForList(this.form.tissueOfOrigin.value,'tissueOfOriginOther','91723000',origTissueOfOriginOther);"> 
			  <br>If Other is Selected:&nbsp;
			  <bigr:otherText name="limsCreateEvaluationForm" property="tissueOfOriginOther" 
			  	parentProperty="tissueOfOrigin" otherCode="91723000" 
			  	size="50" maxlength="200" onkeydown="return checkEnter(event);"/>&nbsp;&nbsp;
			  <input type="button" tabindex="7" value="Copy to Site of Finding" onClick="copySiteOfFinding();">
			</td>
		  </tr>
		  <tr class="white">
		    <td class="grey">
		      <div align="right">
		      	<b>Tissue of Site of Finding:</b>
		      </div>
			</td>
			<td class="white" nowrap>
			  <bigr:tissueWithOther name="limsCreateEvaluationForm"  property = "tissueOfFinding"  propertyLabel = "Tissue of Site of Finding:" firstDisplayValue = "Select Tissue of Site of Finding" onChange="showOtherForList(this.value,'tissueOfFindingOther','91723000');" mode="basic" cssClass="width600"/>
			  <input type="button" name="applyFinding" value="Search" tabindex="8" onClick="isFindingListFiltered = true; findListOptions(this.form.search,this.form.tissueOfFinding);showOtherForList(this.form.tissueOfFinding.value,'tissueOfFindingOther','91723000');">
			  <input type="button" name="refreshFinding" value="Refresh" tabindex="-1" onClick="isFindingListFiltered = false; restoreOriginalListOptions(this.form.search,this.form.tissueOfFinding,origTissueOfFinding);restoreOriginalOtherForList(this.form.tissueOfFinding.value,'tissueOfFindingOther','91723000',origTissueOfFindingOther);"> 
			  <br>If Other is Selected:&nbsp;
			  <bigr:otherText name="limsCreateEvaluationForm" property="tissueOfFindingOther" 
			  	parentProperty="tissueOfFinding" otherCode= "91723000"
			  	size="50" maxlength="200" onkeydown="return checkEnter(event);"/>			  
			</td>
		  </tr>
		</table>
	  </td>
    </tr>  
  </table>
</div>


