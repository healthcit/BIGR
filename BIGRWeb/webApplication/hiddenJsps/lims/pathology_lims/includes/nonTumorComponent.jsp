<%@ taglib uri='/tld/struts-logic' prefix='logic' %>
<%@ taglib uri="/tld/struts-template" prefix="template" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>

    <table cellpadding="0" cellspacing="0" border="0" class="background" width="100%">
      <tr> 
        <td> 
          <table id="composition" border="0" cellspacing="1" cellpadding="3" class="foreground-small" width="100%">
            <col span="2">
	    <col width="10%">
	    <col width="10%">
	    <tr class="green" type="headerntcomponent">
	      <td nowrap colspan="4"><font size="2"><b>Non Tumor Component</b></font>
	      </td>
	    </tr>
	    <tr type="headernormal"><td></td><td></td><td></td><td></td></tr>
	    <tr class="white" type="headernormal">
	      <td  colspan="3" nowrap class="grey">
                <b>Normal</b>
              </td>
	      <td><div align="center">
	      	<html:text property="normalValue" size="3" onblur="updateTotal()" tabindex="10" onkeydown="return checkEnter(event);"/>
		</div>
		</td>
	    </tr>
	    <tr type="headerheader"><td></td><td></td><td></td><td></td></tr>
	    <tr class="white" type="lesionheader">
	      <td nowrap class="grey">
                <span onClick="changeLesion()" tabindex='11' onKeyPress="changeLesion()">
	    		<img id="lesionImg" alt="open menu" src="<html:rewrite page='/images/MenuClosed.gif'/>">
            
                <b>Lesion</b>
                </span>
	      </td>
	      <td colspan="2" class="grey">
              </td>
	      <td class="white" align="center">
	      	<html:text property="lesionValue" size="3" onblur="updateTotal()" tabindex="11" onkeydown="return checkEnter(event);"/>
	      </td>
	      
	    </tr>
	    <tr class="white" id="lesionadd" style="display:none;">
	      <td class="level_2" nowrap>
            	<b>Lesions</b>
            
          </td>
          <td colspan="3">
            <bean:write name="limsCreateEvaluationForm" property="lesionDropDown" filter="false"/>
            <input type="button" value="Add"  tabindex='16' onclick="handleLesions('lesion')">
	      </td>
	      
	    </tr>
	    <tr class="white" id="lesionadd" style="display:none;">
	      <td class="level_2" nowrap>
            <b>Free Form Text</b>
          </td>
          <td colspan="3">
            <input type="text" name="otherLesionText" size="14" maxlength="200" tabindex='16'>
            <input type="button" value="Add"  tabindex='16' onclick="handleLesions('other')">
	      </td>
	      
	    </tr>
	    <tr type="inflammationheader"><td></td><td></td><td></td><td></td></tr>
	    <tr class="white" type="inflammationheader">
	      <td nowrap  class="grey">
                <b>Inflammation</b>
              </td>
            <td class="grey" colspan="2">
            	  <input type="button" name="addInflammationButton"
            	    style="width: 150px;" value="Add Inflammation" tabindex="14"
            	    onClick="getSelectedHierarchy('<html:rewrite page="/library/Dispatch"/>?op=DXTCHierarchy&amp;type=I&amp;excludeSummaryCheckboxes=yes&amp;title=Inflammations','status:yes;resizable:yes;help:no;dialogWidth:500px;dialogHeight:700px', 'inflammation')">
            </td>
            <td class="grey">
            	&nbsp;
            </td> 
	     </tr>
	    <tr type="structureheader"><td></td><td></td><td></td><td></td></tr>
        <tr class="white" type="structureheader">
	      <td nowrap  class="grey">
            <span onClick="changeStructure()" tabindex='16' onKeyPress="changeStructure()">
	    		<img id="structureImg" alt="open menu" src="<html:rewrite page='/images/MenuClosed.gif'/>">
	    		<b>Normal/Lesion Structures</b>
	    	</span>
          </td>
          <td colspan="2" class="grey">
		    
		    <input type="button" value="View Structure List"  tabindex='16' style="width: 150px;" 
		    	onClick="window.showModelessDialog('<html:rewrite page="/library/Dispatch"/>?op=DXTCHierarchy&amp;type=S&amp;title=Structures', '', 'resizable:yes;help:no;dialogWidth:400px;dialogHeight:600px;dialogTop:0px;dialogLeft:0px;status:yes');">
	      </td>
	      <td class="grey">
		   &nbsp;
		   
          </td>    
	    </tr>
	    <tr class="white" id="structureadd" style="display:none;">
	      <td class="level_2" nowrap>
            <b>Tissue</b>
          </td>
          <td colspan="3">
            <bean:write name="limsCreateEvaluationForm" property="tissueDropDown" filter="false"/>
            <input type="button" value="Add"  tabindex='16' onclick="handleStructures('tissue')">
	      </td>
	           
	    </tr>
	    <tr class="white" id="structureadd" style="display:none;">
	      <td class="level_2" nowrap>
            <b>Structure</b>
          </td>
          <td colspan="3">
            <bean:write name="limsCreateEvaluationForm" property="structureDropDown" filter="false"/>
            <input type="button" value="Add"  tabindex='16' onclick="handleStructures('structure')">
	      </td>
	      
	    </tr>
	    <tr class="white" id="structureadd" style="display:none;">
	      <td class="level_2" nowrap>
            <b>Free Form Text</b>
          </td>
          <td colspan="3">
            <input type="text" name="otherStructureText" size="14" maxlength="200" tabindex='16'>
            <input type="button" value="Add"  tabindex='16' onclick="handleStructures('other')">
	      </td>
	      
	    </tr>
	    <tr type="tumorheader" id="structureTotalRow" style="display: none;" class="white"><td colspan="2"><div align="right"><B>Structure Total:</B></div></td><td><b><div id="structureTotalDiv" align="center"></div></b></td><td></td></tr>
	    <tr type="header"><td></td><td></td><td></td><td></td></tr>
	    <tr class="green" type="header">
	    	<td nowrap colspan="4">
		  <font size="2"><b>Tumor Component</b></font>
		</td>
	    </tr>
	    <tr type="header"><td></td><td></td><td></td><td></td></tr>
	    <tr class="white" type="header">
              
            
	    	<td nowrap class="grey"><b>Tumor</b>
		</td>
	    	<td colspan="2" class="grey"><input type="button" value="Add Feature" tabindex="20" style="width: 150px;" 
						    	onClick="getSelectedHierarchy('<html:rewrite page="/library/Dispatch"/>?op=DXTCHierarchy&amp;type=U&amp;excludeSummaryCheckboxes=yes&amp;title=Features',
		    					'status:yes;resizable:yes;help:no;dialogWidth:500px;dialogHeight:700px', 'tumor')"></td>
		  
		<td nowrap >
		  <div align="center" name="tumor"><html:text size="3" maxlength="3" property="tumorValue" onblur="updateTotal()" tabindex="19" onkeydown="return checkEnter(event);"/></div>
		</td>
		
	    </tr>
	    <tr type="hcsheader"><td></td><td></td><td></td><td></td></tr>
	    <tr class="white" type="hcsheader">
	      <td  nowrap   class="grey">
		  <b>Cellular Stroma</b></td><td colspan="2" class="grey"><input type="button" value="Add Feature" tabindex="22" style="width: 150px;" 
						    	onClick="getSelectedHierarchy('<html:rewrite page="/library/Dispatch"/>?op=DXTCHierarchy&amp;type=C&amp;excludeSummaryCheckboxes=yes&amp;title=Features',
		    					'status:yes;resizable:yes;help:no;dialogWidth:500px;dialogHeight:700px', 'cstroma')">
	      </td>
	      <td nowrap >
		  <div align="center" name="hcstroma"><html:text size="3" maxlength="3" property="hcstromaValue" onblur="updateTotal()" tabindex="21" onkeydown="return checkEnter(event);"/></div>
	      </td>
	    </tr>
	    <tr type="hasheader"><td></td><td></td><td></td><td></td></tr>
	    <tr class="white" type="hasheader">
	      <td nowrap  class="grey">
		  <b>Hypo/Acellular Stroma</b></td><td colspan="2" class="grey"><input type="button" value="Add Feature" tabindex="24" style="width: 150px;" 
						    	onClick="getSelectedHierarchy('<html:rewrite page="/library/Dispatch"/>?op=DXTCHierarchy&amp;type=A&amp;excludeSummaryCheckboxes=yes&amp;title=Features',
		    					'status:yes;resizable:yes;help:no;dialogWidth:500px;dialogHeight:700px', 'hastroma')">
	      </td>
	      <td nowrap >
		  <div align="center" name="hastroma"><html:text size="3" maxlength="3" property="hastromaValue" onblur="updateTotal()" tabindex="23" onkeydown="return checkEnter(event);"/></div>
	      </td>
	    </tr>
	    
	    <tr type="necrosisheader"><td></td><td></td><td></td><td></td></tr>
	    <tr class="white" type="necrosisheader">
              <td nowrap class="green" colspan="4">
                <font size="2"><b>Necrosis</b></font>
		
              </td>
            </tr>
	    <tr type="header"><td></td><td></td><td></td><td></td></tr>
	    <tr class="white" type="header">
	    	<td colspan="3" nowrap  class="grey">
		  <b>Necrosis</b>
		</td>
		<td nowrap >
		  <div align="center"><html:text size="3" maxlength="3" property="necrosisValue" onblur="updateTotal()" tabindex="25" onkeydown="return checkEnter(event);"/></div>
		</td>
		
	    </tr>
	    <tr type="totalheader"><td></td><td></td><td></td><td></td></tr>
	    <tr class="white" type="totalheader">
	    	<td colspan="3" nowrap  class="grey"><div align="right"><b>Total:</b></div></td>
		<td nowrap ><b><div id="totalDiv" align="center">0</div></b></td>
	    </tr>
	    
          </table>
        </td>
      </tr>
    </table>

