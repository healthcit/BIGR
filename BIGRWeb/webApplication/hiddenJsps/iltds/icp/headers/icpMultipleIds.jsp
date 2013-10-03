<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %> 
<%@ page import="com.ardais.bigr.api.Escaper" %> 
<%@ page import="com.ardais.bigr.iltds.databeans.CaseData" %> 
<%@ page import="com.ardais.bigr.iltds.databeans.SampleData" %> 
<%@ page import="com.ardais.bigr.pdc.javabeans.DonorData" %> 
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %> 
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"   prefix="bean" %>
<%@ taglib uri="/tld/struts-logic"  prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>

<bean:define id="icpData" name="icpData" type="com.ardais.bigr.iltds.databeans.IcpData" />
<bean:define id="dataMap" name="icpData" property="data" type="java.util.Map"/>
<%
	String alias = Escaper.htmlEscapeAndPreserveWhitespace((String)dataMap.get("alias"));
	List donors = (List)dataMap.get("donors");
	List cases = (List)dataMap.get("cases");
	List samples = (List)dataMap.get("samples");
%>

<div align="left"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
          <tr class="green"> 
            <td align="center"> 
              <b>There are multiple items with the alias "<%=alias%>".  Please select the specific item you wish to view.</b>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
<%
	if (!ApiFunctions.isEmpty(donors)) {
%>
  	<br>
  	<br>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
	  <tr>
	    <td>
		  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
			<tr class="yellow">
			  <td>
				<b>
				  Donors
				</b>
			  </td>
			</tr>
		  </table>
		</td>
	  </tr>
	</table>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
	  <tr class="grey">
		  <td nowrap width="15%">
		    <b>Donor Id</b>
		  </td>
		  <td nowrap width="15%">
		    <b>Donor Alias</b>
		  </td>
		  <td nowrap width="15%">
		    <b>Gender</b>
		  </td>
		  <td nowrap width="55%">
		    <b>Year of Birth</b>
		  </td>
		</tr>
<% 
	    String rowClass = "white";
			Iterator donorIterator = donors.iterator();
			while (donorIterator.hasNext()) {
			  DonorData donor = (DonorData)donorIterator.next();
			  String gender = ApiFunctions.safeString(donor.getGender());
			  String yob = ApiFunctions.safeString(donor.getYyyyDob());
%>
	  	  <tr class="<%=rowClass%>">
	  	    <td nowrap>
			      <bigr:icpLink>
	            <%=donor.getArdaisId()%>
			      </bigr:icpLink>
	        </td>
	        <td nowrap>
	        	<%=Escaper.htmlEscapeAndPreserveWhitespace(donor.getCustomerId())%>
	        </td>
	        <td nowrap>
	        	<%=gender%>
	        </td>
	        <td nowrap>
	        	<%=yob%>
	        </td>
	      </tr>
<%
	 	    if (rowClass.equals("white")) {
 		  	  rowClass = "grey";
	      }
	      else {
	     	  rowClass = "white";
	      }
			}
%>
	</table>
<%
	}
%>
<%
	if (!ApiFunctions.isEmpty(cases)) {
%>
  	<br>
  	<br>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
	  <tr>
	    <td>
		  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
			<tr class="yellow">
			  <td>
				<b>
				  Cases
				</b>
			  </td>
			</tr>
		  </table>
		</td>
	  </tr>
	</table>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
	  <tr class="grey">
		  <td nowrap width="15%">
		    <b>Case Id</b>
		  </td>
		  <td nowrap width="15%">
		    <b>Case Alias</b>
		  </td>
		  <td nowrap width="15%">
		    <b>Donor Id</b>
		  </td>
		  <td nowrap width="15%">
		    <b>Donor Alias</b>
		  </td>
		  <td nowrap width="40%">
		    <b>Linked</b>
		  </td>
		</tr>
<% 
	    String rowClass = "white";
			Iterator caseIterator = cases.iterator();
			while (caseIterator.hasNext()) {
			  CaseData consent = (CaseData)caseIterator.next();
%>
	  	  <tr class="<%=rowClass%>">
	  	    <td nowrap>
			      <bigr:icpLink>
	            <%=consent.getCase_id()%>
			      </bigr:icpLink>
	        </td>
	        <td nowrap>
	        	<%=Escaper.htmlEscapeAndPreserveWhitespace(consent.getCustomer_id())%>
	        </td>
	        <td>
	          <bigr:icpLink>
	        	  <%=consent.getParent().getDonor_id()%>
	          </bigr:icpLink>
	        </td>
	        <td nowrap>
	        	<%=Escaper.htmlEscapeAndPreserveWhitespace(consent.getParent().getCustomer_id())%>
	        </td>
	        <td nowrap>
	        	<%=consent.getLinked() %>
	        </td>
	      </tr>
<%
	 	    if (rowClass.equals("white")) {
 		  	  rowClass = "grey";
	      }
	      else {
	     	  rowClass = "white";
	      }
			}
%>
	</table>
<%
	}
%>
<%
	if (!ApiFunctions.isEmpty(samples)) {
%>
  	<br>
  	<br>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
	  <tr>
	    <td>
		  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
			<tr class="yellow">
			  <td>
				<b>
				  Samples
				</b>
			  </td>
			</tr>
		  </table>
		</td>
	  </tr>
	</table>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
	  <tr class="grey">
		  <td nowrap width="15%">
		    <b>Sample Id</b>
		  </td>
		  <td nowrap width="15%">
		    <b>Sample Alias</b>
		  </td>
		  <td nowrap width="15%">
		    <b>Case Id</b>
		  </td>
		  <td nowrap width="15%">
		    <b>Case Alias</b>
		  </td>
		  <td nowrap width="15%">
		    <b>Donor Id</b>
		  </td>
		  <td nowrap width="25%">
		    <b>Donor Alias</b>
		  </td>
		</tr>
<% 
	    String rowClass = "white";
			Iterator sampleIterator = samples.iterator();
			while (sampleIterator.hasNext()) {
			  SampleData sample = (SampleData)sampleIterator.next();
%>
	  	  <tr class="<%=rowClass%>">
	  	    <td nowrap>
			      <bigr:icpLink>
	            <%=sample.getSample_id()%>
			      </bigr:icpLink>
	        </td>
	        <td nowrap>
	        	<%=Escaper.htmlEscapeAndPreserveWhitespace(sample.getCustomer_id())%>
	        </td>
<%
				if (sample.getParent() != null) {
%>
	        <td>
  	        <bigr:icpLink>
	          	<%=sample.getParent().getParent().getCase_id() %>
	          </bigr:icpLink>
	        </td>
	        <td>
	        	<%=Escaper.htmlEscapeAndPreserveWhitespace(sample.getParent().getParent().getCustomer_id()) %>
	        </td>
	        <td>
	          <bigr:icpLink>
	        	  <%=sample.getParent().getParent().getParent().getDonor_id() %>
	          </bigr:icpLink>
	        </td>
	        <td>
	        	<%=Escaper.htmlEscapeAndPreserveWhitespace(sample.getParent().getParent().getParent().getCustomer_id()) %>
	        </td>
<%
				}
				else {
%>
	        <td colspan="4">
	        	&nbsp;
	        </td>
<%
				}
%>
	      </tr>
<%
	 	    if (rowClass.equals("white")) {
 		  	  rowClass = "grey";
	      }
	      else {
	     	  rowClass = "white";
	      }
			}
%>
	</table>
<%
	}
%>

