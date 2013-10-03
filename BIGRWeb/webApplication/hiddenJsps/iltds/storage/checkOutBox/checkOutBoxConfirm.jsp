<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.ardais.bigr.javabeans.BoxLayoutDto"%>
<%@ page import="com.ardais.bigr.util.Constants"%>
<%@ taglib uri="/tld/struts-html" prefix="html"%>
<%@ taglib uri="/tld/bigr" prefix="bigr"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Check Box out of Inventory</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>

function onFormSubmit() {
  disableActionButtons();
  return true;
}
  
function disableActionButtons() {
  var f = document.forms[0];
  f.Submit2.disabled = true;
}

function taLimit() {
	var taObj=event.srcElement;
	if (taObj.value.length==taObj.maxLength*1 || (event.keyCode==13 && taObj.value.length==(taObj.maxLength*1)-1)) 
	{
		alert("You can enter no more than 255 characters. Please limit the comment to 255 characters.");	
		return false;
	}	
}

function taCount(visCnt) { 
	var taObj=event.srcElement;
	if (taObj.value.length>taObj.maxLength*1) 
	{
		taObj.value=taObj.value.substring(0,taObj.maxLength*1);
		alert("You can enter no more than 255 characters. Please limit the comment to 255 characters.");
		return false;
	}
	if (visCnt) visCnt.innerText=taObj.maxLength-taObj.value.length;
}

function checkAnswerLength() {
		if(document.forms[0].comment.value.length > 250)
		{			
			alert("Invalid entry!  Description can only have 250 characters!");
			document.forms[0].comment.focus();			
   			return false;
		}
		else
		{
			return true;
		}
	}
<%
	String banner = "";
	if ("MvPath".equals(request.getParameter("tx"))) {
		banner = "Move Samples To Pathology - Confirm Contents";
	}
	else {
		banner = "Check Samples Out of Inventory - Confirm Contents";
	}
%>
myBanner = '<%=banner%>';
</script>
</head>

<body class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">

      <div align="center"> 
        <form action="<html:rewrite page='/iltds/Dispatch'/>" method="post"
	          onsubmit="return(onFormSubmit());">
           <table border="0" cellspacing="0" cellpadding="0" class="background">
			<tr> 
        <td> 
        <% BoxLayoutDto boxLayoutDto = (BoxLayoutDto)request.getAttribute("boxLayoutDto");%>
          <table border="0" cellspacing="1" cellpadding="3" class="foreground">
            <%if(request.getAttribute("myError")!=null){%>
            <tr class="green"> 
              <td colspan="<%=boxLayoutDto.getNumberOfColumns()+1%>"> 
                <div align="center"><font color="#FF0000"><b><%= ((request.getAttribute("myError")!=null)?request.getAttribute("myError"):"") %> </b></font></div>
              </td>
            </tr>
			<script>
			alert("<%= request.getAttribute("myError") %>");
			</script>
            <%}%>
            <tr class="white"> 
              <td class="grey"> 
                <div align="left"> <b>Box ID:</b> </div>
              </td>
              <td><%= ((request.getParameter("boxID")!=null)?request.getParameter("boxID"):"") %> 
                <input type="hidden" name="boxID" value="<%= ((request.getParameter("boxID")!=null)?request.getParameter("boxID"):"") %>">
              </td>
            </tr>
	    <tr class="white"> 
	      <td colspan="2"> 
            <bigr:box
              boxLayoutDto="<%=boxLayoutDto%>"
              boxCellContentVar="smpl"
		          sampleTypeVar="smplType"
		          sampleAliasVar="smplAlias"
              persistValue="true"
              cellType="readonly"
            />
	      </td>
	    </tr>
            <input type="hidden" name="boxLayoutId" value="<%=boxLayoutDto.getBoxLayoutId()%>"/>
          </table>
                            </td>
                          </tr>
                        </table>
          <br>
		  <table border="0" cellspacing="0" cellpadding="0" class="background">
		  <tr><td>
          <table border="0" cellspacing="1" cellpadding="3" class="foreground">
            <tr class="white"> 
              <td class="grey"><b> 
                <%if ((request.getParameter("tx")!=null) && (request.getParameter("tx").equals("MvPath"))) {%>
                Reason:<font color="#FF0000"> 
                <%}else {%>
                </font>Reason:<font color="#FF0000">*</font><font color="#FF0000">
                <%}%>
                </font></b></td>
              <td> 
                <%if ((request.getParameter("tx")!=null) && (request.getParameter("tx").equals("MvPath"))) {%>
                Move Samples to Pathology 
                <input type="hidden" name="tx" value="MvPath">
                <input type="hidden" name="reason" value="MVTOPATH">
                <%} else if((request.getParameter("location")!=null) && (request.getParameter("location").equals(Constants.LOCATION_MEDICAL_INSTITUTION))) {
				  String reasonCode = ((request.getParameter("reason")!=null)?request.getParameter("reason"):""); %>
                <select name="reason">
				  <option value="">Select</option>
                  <option value="MICONSREV" <%if ("MICONSREV".equals(reasonCode)) out.print("selected");%>>Consent Revocation</option>
                  <option value="MICASEPULL" <%if ("MICASEPULL".equals(reasonCode)) out.print("selected");%>>Case Pull</option>
                  <option value="MIOTHER" <%if ("MIOTHER".equals(reasonCode)) out.print("selected");%>>Other</option>
                </select>
                <%} else if((request.getParameter("location")!=null) && (request.getParameter("location").equals(Constants.LOCATION_ARDAIS))) {
				  String reasonCode = ((request.getParameter("reason")!=null)?request.getParameter("reason"):""); %>
                <select name="reason">
				  <option value="">Select</option>
                  <option value="ARCONSREV" <%if ("ARCONSREV".equals(reasonCode)) out.print("selected");%>>Consent Revocation</option>
                  <option value="ARCASEPULL" <%if ("ARCASEPULL".equals(reasonCode)) out.print("selected");%>>Case Pull</option>
                  <option value="ARCONSUME" <%if ("ARCONSUME".equals(reasonCode)) out.print("selected");%>>Consumed</option>
                  <option value="ARMVTORND" <%if ("ARMVTORND".equals(reasonCode)) out.print("selected");%>>Move to R&D</option>
                  <option value="ARLICENSED" <%if ("ARLICENSED".equals(reasonCode)) out.print("selected");%>>Licensed</option>
                  <option value="ARDESTROYED" <%if ("ARDESTROYED".equals(reasonCode)) out.print("selected");%>>Destroyed</option>
                  <option value="AROTHER" <%if ("AROTHER".equals(reasonCode)) out.print("selected");%>>Other</option>
                </select>
                <%}%>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey">
                <b>Comment:</b><br>
                (255 char max) &nbsp; </td>
              <td> 
				<textarea name="comment" cols="50" rows="5" maxLength=255 onPaste="return taLimit()" onKeyPress="return taCount()" onKeyUp="return taCount()"><%= ((request.getParameter("comment")!=null)?request.getParameter("comment"):"")%></textarea>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"><b>Requested By:<font color="#FF0000">*</font></b></td>
              <td> 
                <%
					 Vector staff = (Vector) request.getAttribute("staff"); 
					 String reqBy = ((request.getParameter("reqBy")!=null)?request.getParameter("reqBy"):"");
					  %>
                <select name="reqBy">
                  <option value="Not Specified">Select</option>
                  <%for (int i = 0 ; i < (staff.size()-1) ; i += 2) { %>
                  <option value="<%= staff.get(i+ 1)%>" <%if (reqBy.equals((String)staff.get(i+1))) out.print("selected");%>><%= staff.get(i)%> </option>
                  <%}%>
                </select>
              </td>
            </tr>
            <%if ((request.getParameter("tx")!=null) && (request.getParameter("tx").equals("MvPath"))) {%>
            <%}%>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="hidden" name="op" value="CheckOutBoxInsert">
                  <input type="submit" name="Submit2" value="Submit">
                  <input type="hidden" name="location" value="<%=((request.getParameter("location")!=null)?request.getParameter("location"):"")%>">
                  <input type="hidden" name="deliverTo" value="">
                </div>
              </td>
            </tr>
          </table>
              </td>
            </tr>
          </table>
        </form>
      </div>
</body>
</html>