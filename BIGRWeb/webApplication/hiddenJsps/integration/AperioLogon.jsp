<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<bean:define name="aperioForm" id="myForm" type="com.gulfstreambio.bigr.integration.aperio.AperioForm"/>
<div style="font-weight: bold; font-size: x-small">
<html:errors/>
</div>
<div style="font-size: x-small">
Please enter your Spectrum username and password
<table style="font-size: x-small">
<tr>
<td>Username:</td>
<td><input type="text" id="spectrumUsername" name="spectrumUsername" value="<%=myForm.getSpectrumUsername()%>"/></td>
</tr>
<tr>
<td>Password:</td>
<td><input type="password" id="spectrumPassword" name="spectrumPassword" value="<%=myForm.getSpectrumPassword()%>"/></td>
</tr>
</table>
<input type="button" value="Logon" style="font-size: xx-small" onclick="GSBIO.bigr.integration.aperio.HeImage.logonSpectrum();"/>
<input type="button" value="Cancel" style="font-size: xx-small" onclick="GSBIO.bigr.integration.aperio.HeImage.closeDialog();"/>
</div>