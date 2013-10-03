<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<table cellpadding="0" cellspacing="0" border="0" class="background" width="100%">
 <tr> 
   <td> 
     <table border="0" cellspacing="1" cellpadding="1" class="foreground" width="100%">
       <tr class="yellow"> 
         <td colspan=2> 
           <div align="center">
             <b>Sample Composition</b>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <button type="button" name="submitPvReport" accesskey="i" accesskey="I"  tabindex="-1" onClick="viewPvReport();">
             V<u>i</u>ew PV Report
             </button>
           </div>
         </td>
       </tr>
       <tr class="white" align="center">
		 <td valign="top" width="70%">
           <jsp:include page="/hiddenJsps/lims/pathology_lims/includes/nonTumorComponent.jsp" flush="true"/>
		 </td>
         <td nowrap align="center" valign="top" width="30%">
           <jsp:include page="/hiddenJsps/lims/pathology_lims/includes/IntExtComposition.jsp" flush="true"/>
         </td>
		</tr>
		<tr class="white" align="center">
		  <td valign="center" align="center" colspan="2">
            <button type="submit" name="submitReport" accesskey="s" accesskey="S" tabindex="32" onClick="setWindowTarget('_self'); reportForm();">
            <u>S</u>ubmit / Report
            </button>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="hidden" name="reportEvaluation">
			<logic:present name="popup">
    			<input type="button" name="cancel" value="Cancel" tabindex="33" onClick="window.close()">
    		</logic:present>
			<logic:notPresent name="popup">
    			<input type="button" name="cancel" value="Cancel" onClick="cancelPage();" tabindex="33"/> 
    		</logic:notPresent>           
			
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="submitNotReport" value="Submit / Not Report" tabindex="34"
			  onClick="setWindowTarget('_self'); setConfirmSubmitNoReport(true);"
			  onBlur="document.limsCreateEvaluationForm.addInflammationButton.focus();">
            
          </td>
		</tr>
      </table>
    </td>
  </tr>
</table>
