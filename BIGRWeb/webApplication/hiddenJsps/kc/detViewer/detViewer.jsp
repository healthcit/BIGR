<%@ page language="java"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.gulfstreambio.kc.web.support.KcUiContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.WebUtils" %>
<%@ page import="javax.servlet.jsp.JspException" %>
<%
String contextPath = request.getContextPath();
String fullPath = WebUtils.getFullPath(contextPath);
String jsPath = fullPath + "/js/";
String detViewerPath = fullPath + "/detViewer/";
String backEnabled = detViewerPath + "images/backEnabled.gif";
String backDisabled = detViewerPath + "images/backDisabled.gif";
String forwardEnabled = detViewerPath + "images/forwardEnabled.gif";
String forwardDisabled = detViewerPath + "images/forwardDisabled.gif";
String url = KcUiContext.getKcUiContext(request).getDetViewerResultsUrl();
if (ApiFunctions.isEmpty(url)) {
  throw new JspException("DET Viewer results page URL not specified.");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>DET Viewer</title>
    <script type="text/javascript" src="<%=jsPath + "prototype.js"%>"></script>
    <script type="text/javascript" src="<%=jsPath + "gsbio.js"%>"></script>
    <script type="text/javascript" src="<%=detViewerPath + "js/detViewer.js"%>"></script>
    <script type="text/javascript" src="<%=detViewerPath + "js/scriptaculous.js"%>"></script>
    <link rel="stylesheet" type="text/css" href="<%=detViewerPath + "css/detViewer.css"%>" />
    <script type="text/javascript">
    GSBIO.kc.det.viewer.History.init({backImgEnabled : '<%=backEnabled%>', 
      backImgDisabled : '<%=backDisabled%>', 
      forwardImgEnabled : '<%=forwardEnabled%>', 
      forwardImgDisabled : '<%=forwardDisabled%>',
      url: '<%=url%>'});
    </script>
  </head>
  <body>
    <div id="wholePage">
    <div id="intro">
      <table cellspacing="0">
      <tr><td>
      <h1>DET Viewer</h1>&nbsp;&mdash; Search for DET concepts using one of the following methods
      </td>
      <td id="historyButtons">
        <input type="image" id="back" src="<%=backDisabled%>" onclick="GSBIO.kc.det.viewer.History.back();" disabled/>
        <input type="image" id="forward" src="<%=forwardDisabled%>" onclick="GSBIO.kc.det.viewer.History.forward();" disabled/>
      </td>
      </tr>
      </table>
    </div>
		<div id="queryParams" 
		  onmouseover="GSBIO.kc.det.viewer.History.handleButtonMouseover();"
		  onmouseout="GSBIO.kc.det.viewer.History.handleButtonMouseout();"
		  onclick="GSBIO.kc.det.viewer.History.handleSearchClick();" 
		  onkeypress="GSBIO.kc.det.viewer.History.handleSearchKeypress();">
		  <h1>Parameters</h1>
		  <div id="paramCui">
        1. Enter a CUI or system name (case-sensitive):
        <input type="text" id="cuisys" name="cuisys" size="30"/>
        <input type="button" value="Go" id="cui" class="button"/>
		  </div>
		  <div id="paramDesc">
        2. Enter a description (case-insensitive):
        <input type="text" id="desc" name="desc" size="30"/>
        <input type="button" value="Go" id="description" class="button"/>
		  </div>
		  <div id="paramAll">
        3. <input type="button" value="Show all ADEs" id="allade" class="button"/> OR
           <input type="button" value="Show all categories" id="allcat" class="button"/> OR
           <input type="button" value="Show all units" id="allunit" class="button"/>
		  </div>
		  <div id="paramDe">
		    4. Show data elements with:
        <table cellspacing="0">
	        <tr> 
	          <td>datatype</td>
	          <td>multivalued?</td>
	          <td>unit?</td>
	          <td>other?</td>
	          <td>min?</td>
	          <td>max?</td>
	          <td>MLVS?</td>
	          <td>NOVAL?</td>
	          <td>non-ATV?</td>
	          <td>EAV?</td>
	          <td>ADE?</td>
	        </tr> 
	        <tr> 
	          <td>
	            <select id="deDt">
	              <option value="">Any</option>
	              <option value="cv">cv</option>
	              <option value="date">date</option>
	              <option value="float">float</option>
	              <option value="int">int</option>
	              <option value="report">report</option>
	              <option value="text">text</option>
	              <option value="vpd">vpd</option>
	          	</select>
	          </td>
	          <td>
	            <select id="deMulti">
	              <option value="">Both</option>
	              <option value="y">multi</option>
	              <option value="n">single</option>
	          	</select>
	          </td>
	          <td>
	            <select id="deUnit">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
	            <select id="deOce">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
	            <select id="deMin">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
	            <select id="deMax">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
	            <select id="deMlvs">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
	            <select id="deNoval">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
	            <select id="deNonatv">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
	            <select id="deEav">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
	            <select id="deAde">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
        			<input type="button" value="Go" id="de" class="button"/>
	          </td>
	        </tr> 
	      </table>
		  </div>
		  <div id="paramAdee">
		    5. Show ADE elements with:
        <table cellspacing="0">
	        <tr> 
	          <td>datatype</td>
	          <td>multivalued?</td>
	          <td>unit?</td>
	          <td>other?</td>
	          <td>min?</td>
	          <td>max?</td>
	          <td>MLVS?</td>
	          <td>NOVAL?</td>
	          <td>EAV?</td>
	        </tr> 
	        <tr> 
	          <td>
	            <select id="adeDt">
	              <option value="">Any</option>
	              <option value="cv">cv</option>
	              <option value="date">date</option>
	              <option value="float">float</option>
	              <option value="int">int</option>
	              <option value="report">report</option>
	              <option value="text">text</option>
	              <option value="vpd">vpd</option>
	          	</select>
	          </td>
	          <td>
	            <select id="adeMulti">
	              <option value="">Both</option>
	              <option value="y">multi</option>
	              <option value="n">single</option>
	          	</select>
	          </td>
	          <td>
	            <select id="adeUnit">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
	            <select id="adeOce">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
	            <select id="adeMin">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
	            <select id="adeMax">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
	            <select id="adeMlvs">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
	            <select id="adeNoval">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
	            <select id="adeEav">
	              <option value="">Both</option>
	              <option value="y">yes</option>
	              <option value="n">no</option>
	          	</select>
	          </td>
	          <td>
        			<input type="button" value="Go" id="adee" class="button"/>
	          </td>
	        </tr> 
	      </table>
		  </div>
    </div>
    <div id="results">
    </div>
  </body>
</html>