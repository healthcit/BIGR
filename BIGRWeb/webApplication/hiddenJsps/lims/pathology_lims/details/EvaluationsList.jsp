 <%@ page language="java" 
         import="java.util.*" 
         errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<% com.ardais.bigr.orm.helpers.FormLogic.
							   commonPageActions(request, 
							    				 response, 
							    				 this.getServletContext(),
							    				 "P"); %>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%@ taglib uri="/tld/datetime"     prefix="dt"   %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>

<bigr:overlibDiv/>
<div align="left"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
          <tr class="yellow"> 
            <td colspan="17"> 
              <div align="center"><b>Evaluations</b></div>
            </td>
          </tr>
          <tr class="grey"> 
            
	    <td rowspan="2"><div align="center">
	    	<b>Select</b>
		</div>
	    </td>
	    <td rowspan="2"><div align="center">
	    	<b>ID</b>
		</div>
	    </td>
	    
	    <td rowspan="2"><div align="center">
	    	<b>Slide ID</b>
		</div>
	    </td>
	    
	    <td><div align="center">
	    	<b>Sample Path from PV</b>
		</div>
	    </td>
	    <td rowspan="2"><div align="center">
	    	<b>Microscopic Appearance</b>
		</div>
	    </td>
	    <td colspan="6"><div align="center">
	    	<b>Composition</b>
		</div>
	    </td>
	    
	    <td rowspan="2"><div align="center">
	    	<b>Quality Issues/Comments</b>
		</div>
	    </td>
	    
	   
	    
          </tr>
	  <tr class="grey">
	    <td><div align="center">
	    	<b>Tissue of Site of Origin/ Finding</b>
		</div>
	    </td>
	    <td><div align="center"><b>NRM</b></div></td>
	    <td><div align="center"><b>LSN</b></div></td>
	    <td><div align="center"><b>TMR</b></div></td>
	    <td><div align="center"><b>TCS</b></div></td>
	    <td><div align="center"><b>TAS</b></div></td>
	    <td><div align="center"><b>NEC</b></div></td>
	  </tr>
	  <logic:present name="limsManageEvaluationsForm" property="pathologyEvaluations">
	  <logic:iterate name="limsManageEvaluationsForm" property="pathologyEvaluations" id="pathologyEvaluations" type="com.ardais.bigr.lims.javabeans.PathologyEvaluationData">
	   
	  <logic:equal name="pathologyEvaluations" property="reported" value="true">
	   <tr class="highlight"> 
      </logic:equal> 
      <logic:equal name="pathologyEvaluations" property="reported" value="false">
	   <tr class="white"> 
      </logic:equal> 
           
	    <td rowspan="2"><div align="center">
	    	<bean:define id="evaluationId" name="pathologyEvaluations" property="evaluationId"/>
	    	<bean:define id="slideId" name="pathologyEvaluations" property="slideId"/>
	    	<%String clickString = "setSlideId('" + slideId + "');"; %>
	        <html:radio name="limsManageEvaluationsForm" property="sourceEvaluationId" value="<%=(String)evaluationId%>" onclick="<%=clickString%>"/>	    	
		</div>
	    </td>
	    <td rowspan="2"><div align="center" onmouseover="return overlib('Pathologist: <bean:write name="pathologyEvaluations" property="pathologist" /> <br>Date/Time Created: <bean:write name="pathologyEvaluations" property="creationDate" />');" onmouseout="return nd();">
	    	<bean:write name="pathologyEvaluations" property="evaluationId" />
		</div>
	    </td>
	    
	    <td rowspan="2"><div align="center">
	    	<bean:write name="pathologyEvaluations" property="slideId" />
		</div>
	    </td>
	    
	    <td><div align="center">
	    	<bean:write name="pathologyEvaluations" property="diagnosisName" /> 
		</div>
	    </td>
	    <td rowspan="2"><div align="center">
	    	<bean:write name="pathologyEvaluations" property="microscopicAppearanceName" /> 
		</div>
	    </td>
	    <td rowspan="2"><div align="center">
	    	<bean:write name="pathologyEvaluations" property="normalCells" /> 
		</div>
	    </td>
	    <td rowspan="2"><div align="center">
	    	<bean:write name="pathologyEvaluations" property="lesionCells" /> 
		</div>
	    </td>
	    <td rowspan="2"><div align="center">
	    	<bean:write name="pathologyEvaluations" property="tumorCells" /> 
	    </td>
	    <td rowspan="2"><div align="center">
	    	<bean:write name="pathologyEvaluations" property="cellularStromaCells" /> 
		</div>
	    </td>
	    <td rowspan="2"><div align="center">
	    	<bean:write name="pathologyEvaluations" property="hypoacellularStromaCells" /> 
		</div>
	    </td>
	    <td rowspan="2"><div align="center">
	    	<bean:write name="pathologyEvaluations" property="necrosisCells" /> 
		</div>
	    </td>	    
	    
	    <td rowspan="2"><div align="center">	    	
			<bean:write name="pathologyEvaluations" property="concatenatedInternalData" />
		</div>
	    </td>
	    
          </tr>
          
	  <logic:equal name="pathologyEvaluations" property="reported" value="true">
	   <tr class="highlight"> 
      </logic:equal> 
      <logic:equal name="pathologyEvaluations" property="reported" value="false">
	   <tr class="white"> 
      </logic:equal> 
	    <td><div align="center">
	    <bean:write name="pathologyEvaluations" property="tissueOfOriginName" />/
	    <bean:write name="pathologyEvaluations" property="tissueOfFindingName" />
	    </div></td>
	  </tr>
	  <logic:equal name="pathologyEvaluations" property="reported" value="true">
	   <tr class="highlight"> 
      </logic:equal> 
      <logic:equal name="pathologyEvaluations" property="reported" value="false">
	   <tr class="white"> 
      </logic:equal> 
	  	<td colspan=2><div align="center"> 
	  		<bigr:viewPvReport evaluation='<%= pathologyEvaluations.getEvaluationId() %>' buttontext="View"/>
	    </div>
	    </td>
		
	  	<td colspan="12"><div onmouseover="return overlib('Extended Composition/Features/Comments');" onmouseout="return nd();">
	  	<bigr:beanWrite name="pathologyEvaluations" property="formattedConcatenatedExternalData" filter="false" whitespace="false"/>
		</div>
	    </td>
	  </tr>
	  <tr class="darkgreen">
	  	<td colspan="17"></td>
	  </tr>
       </logic:iterate>  
       </logic:present>  
	  
	  <tr class="white">
	  	<td colspan="3"><div align="center">
	  	<logic:equal name="limsManageEvaluationsForm" property="pulled" value="Yes">
	  	  <input type="button" value="New Evaluation" onClick="alert('Evaluation cannot be added to slides from PULLED samples.');">
	  	</logic:equal>
	  	<logic:notEqual name="limsManageEvaluationsForm" property="pulled" value="Yes">	  		  	
		<logic:present name="limsManageEvaluationsForm" property="slideId">
		  <input type="submit" value="New Evaluation">
        </logic:present>
		<logic:notPresent name="limsManageEvaluationsForm" property="slideId">
		  <input type="button" value="New Evaluation" onClick="alert('You must scan in Slide ID before adding an evaluation.');">
        </logic:notPresent>
        </logic:notEqual>
		</div>
		</td>
		<td colspan="5"><div align="center">	  	
		<input type="submit" name="editCopyButton" value="Edit/Copy" onClick="return copyEvaluation();">
		<html:text name="limsManageEvaluationsForm" property="editCopyId" onkeydown="return checkEnter(event);" maxlength="10"/>
		<input type="hidden" name="isEditCopy">		
		</div>
		</td>
		<td colspan="4"><div align="center">
		<logic:equal name="limsManageEvaluationsForm" property="pulled" value="Yes">
	  	  <input type="button" name="reportEval" value="Report Selected Evaluation" onClick="alert('Evaluation cannot be reported for slides from PULLED sample.');">
	  	</logic:equal>
	  	<logic:notEqual name="limsManageEvaluationsForm" property="pulled" value="Yes">	
		  <input type="button" name="reportEval" value="Report Selected Evaluation" onClick="setReportEvaluation();">
		</logic:notEqual>
		<input type="hidden" name="isReportEvaluation">		
		</div>
		</td>
	  </tr>
        </table>
      </td>
    </tr>
  </table>
</div>


