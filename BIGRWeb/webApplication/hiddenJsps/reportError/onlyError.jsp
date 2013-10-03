<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>

<html>

<head>
	<title>Error Report</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>

<body class="bigr">

	<div id="errorDiv" align="center">
		<table width="100%" border="1" cellspacing="1" cellpadding="1" class="foreground-small">

			<tr class="yellow">
				<td>
					<font color="#FF0000">
						<strong>
							<logic:present name="org.apache.struts.action.ERROR">
								<html:errors/>
							</logic:present>
							<logic:present name="com.ardais.BTX_ACTION_MESSAGES">
								<bigr:btxActionMessages/>
							</logic:present>
							<logic:present name="myError">
								<bean:write name="myError" filter="false" />
							</logic:present>
						</strong>
					</font>
				</td>
			</tr>

		</table>
	</div>

</body>
</html>
