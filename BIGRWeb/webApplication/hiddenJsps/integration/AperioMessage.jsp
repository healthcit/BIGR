<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<div style="font-weight: bold; font-size: x-small">
<logic:present name="com.ardais.BTX_ACTION_MESSAGES">
<bigr:btxActionMessages/>
<p><input type="button" value="Close" style="font-size:xx-small" onclick="GSBIO.bigr.integration.aperio.HeImage.closeDialog();"/>
</logic:present>
<logic:present name="org.apache.struts.action.ERROR">
<% org.apache.struts.action.ActionErrors actionErrors = (org.apache.struts.action.ActionErrors)request.getAttribute("org.apache.struts.action.ERROR");
	java.util.Iterator iter = actionErrors.get();
	while(iter.hasNext())
	{
	  Object[] values = ((org.apache.struts.action.ActionError)iter.next()).getValues();
	  for(int i=0; i<values.length;i++)
	  {
	  %>
    <%=values[i]%>
    
<% 	
	  }
	  }
%>
</logic:present>
</div>
