<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean"   prefix="bean" %>
<%@ taglib uri="/tld/struts-logic"  prefix="logic"%>

<bean:define id="icpType" name="icpData" property="type" type="java.lang.String" />

<script type="text/javascript">
function icpQueryCheckTab(event) { 	
	var code = 0;
	code = event.keyCode;
  if(code == 9){
    if(document.forms["icpQuery"].elements["id"].value.length > 0){
      document.icpQuery.submit();
    }
  }
}
</script>

<div align="right"> 
  <form action="<html:rewrite page='/iltds/Dispatch'/>" name="icpQuery" method="get">
    <table cellpadding="0" cellspacing="0" border="0" class="background">
      <tr> 
        <td> 
          <table border="0" cellspacing="1" cellpadding="3" class="foreground-small">
            <tr class="yellow"> 
              <td nowrap>
                <b>Enter/Scan Inventory Item:</b>
              </td>
            </tr>
            <tr class="white">
              <td nowrap>
                <div align="center"> 
                  <input type="text" name="id" size="15" maxlength="30" onkeydown="icpQueryCheckTab(event);">
                  <input type="hidden" name="op" value="IcpQuery">
                  <input type="submit" name="Submit" value="Go">
                </div>
              </td>
            </tr>
        	</table>
        </td>
      </tr>
    </table>
  </form>
</div>

<script type="text/javascript">
  document.forms["icpQuery"].elements["id"].focus();
</script>
