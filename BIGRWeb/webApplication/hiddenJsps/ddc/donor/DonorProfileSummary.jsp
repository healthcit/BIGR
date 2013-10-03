<%--@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"--%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.pdc.helpers.DonorHelper" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>
<bean:define name="helper" id="donor" type="com.ardais.bigr.pdc.helpers.DonorHelper"/>
<html>
<head>
<title>Summary of Donor Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/attachment.js"/>"></script>
<script language="JavaScript">
 
  function initPage() {
    var myBanner = 'Summary of Donor Information';  
    if (parent.topFrame && parent.topFrame.document.all.banner) {
      parent.topFrame.document.all.banner.innerText = myBanner;
    }
  }

	function onClickCreate() {
		document.forms[0].action = '<html:rewrite page="/kc/form/createPrepareDonor.do"/>';
		document.forms[0].submit();
	}
	
	
</script>
</head>

<body class="bigr" onLoad="initPage();">
<form name="donorprofilesummary" method="post" action="<html:rewrite page="/ddc/Dispatch"/>" >
  <input type="hidden" name="op" value="DonorProfileSummaryPrepare">
  <!-- input type="hidden" name="op" value="DonorProfilePrepare"-->
  <html:hidden name="donor" property="ardaisId"/>
  <html:hidden name="donor" property="new"/>
  <input type="hidden" name="domainObjectId" value="<%=donor.getArdaisId()%>">
  <input type="hidden" name="domainObjectType" value="donor">
  <input type="hidden" id="deleteAttachId" name="deleteAttachId" value=""/>

  <div id="errorDiv" align="center">
    <table width="100%" border="1" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
      <tr class="yellow"> 
	    <td> 
	      <font color="#FF0000"><b><html:errors/></b></font>
	    </td>
	  </tr>
	  </logic:present>
      <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
	  <tr class="white"> 
	    <td> 
	      <div align="left">
	        <font color="#000000"><b><bigr:btxActionMessages/></b></font>
	      </div>
	    </td>
	  </tr>
	  </logic:present>
	</table>
  </div>
  <logic:notEmpty name="donor" property="messages">
	  <div align="center">
	    <table class="background" cellpadding="0" cellspacing="0" border="0">
	      <tr> 
	        <td> 
	          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
	                        
	            <bean:write name="donor" property="messages" filter="false"/>
	          </table>
	        </td>
	      </tr>
	    </table>
	  </div>
	</logic:notEmpty>
	<br/>
	<br/>

  <!-- Context -->
<logic:equal name="donor" property="donorPresent" value="false">
	<div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">
            <tr class="white"> 
              <td class="yellow" align="right"><b>Donor</b></td>
              <logic:present name="donor" property="ardaisId">
                <td>
                  <bean:write name="donor" property="ardaisId"/>
                </td>
              </logic:present>
              <td class="yellow" align="right"><b> does not exist.</b></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
</logic:equal>
	
  <logic:equal name="donor" property="donorPresent" value="true">
		<div align="center">
			<table class="background" cellpadding="0" cellspacing="0" border="0">
				<tr><td> 
					<table class="foreground" border="0" cellpadding="3" cellspacing="1">
						<tr class="white"> 
							<td class="yellow" align="right"><b>Donor</b></td>
							<td>
							  <bean:write name="donor" property="ardaisId"/>
							  <logic:notEmpty name="donor" property="customerId">
							    &nbsp;(<bean:write name="donor" property="customerId"/>)
							  </logic:notEmpty>
							</td>
						</tr>
					</table>
				</td></tr>
			</table>
		</div>
	
	<p>
			<table class="foreground" cellpadding="3" cellspacing="0" border="0" width="100%">
				<tr class="green">
					<td style="border: 1px solid #336699;"> 
						<b>
						  Existing Forms for Donor <bean:write name="donor" property="ardaisId"/>
					    <logic:notEmpty name="donor" property="customerId">
							    &nbsp;(<bean:write name="donor" property="customerId"/>)
							</logic:notEmpty>
						</b>
					</td>
				</tr>
			</table>
			<%
			  String kcFormLink = "/kc/form/updatePrepare.do";
			%>
      <logic:equal parameter="readOnly" value="true">
        <%
           Map linkParams = donor.getDonorProfileLinkParams();
					 linkParams.put("readOnly", "true");
					 kcFormLink = "/kc/form/summary.do";
        %>
			</logic:equal>

			<table class="foreground" border="0" cellpadding="3" cellspacing="0">
				<tr class="white"> 
					<td style="padding-left: 0.5em;"> 
						<html:link page="/ddc/Dispatch" name="donor" property="donorProfileLinkParams">
						  Donor Details
						</html:link>
                     <logic:iterate name="donor" property="formInstances" id="formInstance" type="com.ardais.bigr.kc.form.BigrFormInstance">
						<br>
						<html:link page="<%=kcFormLink%>" paramId="formInstanceId" paramName="formInstance" paramProperty="formInstanceId">
						  <%=Escaper.htmlEscape(formInstance.getFormDefinition().getName())%> (<%=ApiFunctions.sqlTimestampToString(formInstance.getCreationDateTime())%>)
						</html:link>
					 </logic:iterate>
					</td>
				</tr>
			</table>		
  <logic:notEqual parameter="readOnly" value="true">
	  <logic:greaterThan name="donor" property="formDefinitionsAsLegalValueSet.count" value="0">
	    <p>
			<table class="foreground" cellpadding="3" cellspacing="0" border="0" width="100%">
				<tr class="green">
					<td style="border: 1px solid #336699;"> 
						<b>
						  Create a New Form for Donor 
						  <bean:write name="donor" property="ardaisId"/>
					    <logic:notEmpty name="donor" property="customerId">
							    &nbsp;(<bean:write name="donor" property="customerId"/>)
							</logic:notEmpty>
						</b>
					</td>
				</tr>
			</table>

			<table class="foreground" border="0" cellpadding="3" cellspacing="0">
				<tr class="white">
					<td style="padding-left: 0.5em;"> 
					Form:
					<bigr:selectList
					  name="donor"
				  	property="formDefinitionId"
					  legalValueSetProperty="formDefinitionsAsLegalValueSet"
					  firstValue="" firstDisplayValue="Select a form definition"/>
					&nbsp;<input type="button" name="btnCreate" value="Create" onclick="onClickCreate();"/>
					</td>
				</tr>
			</table>		
		</logic:greaterThan>
	  </logic:notEqual>	

	 <!-- Attachment begin -->			  
	  	 <p>
			<table class="foreground" cellpadding="3" cellspacing="0" border="0" width="100%">
				<tr class="green">
					<td style="border: 1px solid #336699;"> 
						<b>
						  Attachments for Donor <bean:write name="donor" property="ardaisId"/>
						    <logic:notEmpty name="donor" property="customerId">
							    &nbsp;(<bean:write name="donor" property="customerId"/>)
							</logic:notEmpty>
						</b>
					</td>
				</tr>
			</table>
 
<div class="ln">&nbsp;</div>
 
     <table class="foreground" cellpadding="0" cellspacing="0" border="2" width="80%">
      <tr > 
        <td colspan="5" > 
          <table cellpadding="3" width="100%" cellspacing="1" border="1" class="foreground">
          <tr class="lightGrey"> 
              <td align="center"> 
                <b>
                 File Name</b>
              </td>
              <td align="center" title="Document Contains PHI Information?"> 
                <b>
                 PHI</b>
              </td>
               <td align="center" title="Document Contains PHI Information?"> 
                <b>
                 Comments</b>
              </td>
               <td align="center"> 
                <b>
                 Submitted By</b>
              </td>
               <td align="center"> 
                <b>
                 Submitted Time</b>
              </td>
               <td align="center"> 
                  &nbsp;
              </td>
         </tr>
         <logic:notEmpty name="donor" property="attachments">
            <logic:iterate name="donor" id="attachment" property="attachments">
          
             <bean:define id="attachId"><bean:write name="attachment" property="id"/></bean:define>
             <bean:define id="attachType"><bean:write name="attachment" property="contentType"/></bean:define>
             <tr id="attachId_<%=attachId%>" style="" >
                
                <td><A href="javascript:void(0);" NAME="Attachment" onClick="window.open('<%=request.getContextPath()%>/ddc/AttachmentWriter?fileId=<%=attachId%>&fileType=<%=attachType %>','','width=300,height=400,left=150,top=200,resizable=1,scrollbars=1')";>
                <bean:write name="attachment" property="name"/></A></td>
                <td><bean:write name="attachment" property="isPHIYN"/>&nbsp;</td>
                <td><bean:write name="attachment" property="comments"/>&nbsp;</td>
                <td><bean:write name="attachment" property="createdBy"/>&nbsp;</td>
                <td><bean:write name="attachment" property="createdDate"/>&nbsp;</td>
                <td>                
                   <button type="button" name="btnDelete"  onclick="return onDeleteAttachment('<%=attachId %>', this.form);">Delete</button>
                </td>
             </tr>
            
            </logic:iterate>
          </logic:notEmpty>   
          <logic:empty name="donor" property="attachments">
           <tr><td colspan="6" width="20%">No attachment yet!</td></tr>
          </logic:empty>   
           </table>
          </td>
         </tr>
    </table>
 </form>  
  <form name="attachments" method="post" action="<html:rewrite page="/ddc/MultipartDispatch"/>" enctype="multipart/form-data"> 
    <input type="hidden" name="op" value="DonorProfileSummaryPrepare">
    <html:hidden name="donor" property="ardaisId"/>
    <html:hidden name="donor" property="new"/>
    <input type="hidden" name="domainObjectId" value="<%=donor.getArdaisId()%>">
    <input type="hidden" name="domainObjectType" value="donor">  
    <table class="foreground" cellpadding="0" cellspacing="0" border="2"> 	 
         <tr class="lightGrey"><th>File Name</th><th>PHI</th><th >File</th><th>Comments</th><th>&nbsp;</th></tr>
         <tr class="foreground">       
         <td> <input type="text" id="fileName" name="fileName"  maxLength="100"/></td>
         <td> <input type="checkbox" name="IsPHIFlg"  value="N" onclick="document.getElementById('IsPHIFlg_Value').value='Y';"/>
              <input type="hidden" id="IsPHIFlg_Value" name="IsPHIFlg_Value"  value="N" />
         </td>
         <td>
             <!--For full file path in IE 7+: Tools/Internet options/Security/Internet zone/Custom Level/Enable "Include local directory path when uploading files to a server" -->
             <input type="file" id="btnBrowse" name="btnBrowse" style="background-color:yellow;" size="35%" value="Browse" />
         </td>
          <td>    
            
              <textarea id="fileComments" name="fileComments" rows="2" cols="40" onkeyup="return onKeyInComment(event, this.form);" ></textarea>  
               <!-- textarea name="fileComments" rows="2" cols="40" ></textarea-->    
          </td>
          <td>    
            
               <input type="submit" id="btnAttach" name="btnAttach" value="Attach" onclick="return onSubmitAttach(this.form);"/> 
          </td>
         </tr>
     </table>  
	 <!-- Attachment end --> 
	  
	  
	  <logic:equal parameter="readOnly" value="true">
      <div align="center">
        <br><br>
        <input type="button" value="Close" onclick="window.close();"> 
      </div>
	  </logic:equal>
  </logic:equal>
</form>
</body>
</html>
