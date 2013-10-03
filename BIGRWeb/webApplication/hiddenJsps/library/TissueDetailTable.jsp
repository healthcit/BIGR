<%--
	** This page is intended to be included within another to show
	** details of a chunk of samples.
	**
	** The caller must provide the items described below as input and the
	** calling context must satisfy the requirements described in the
	** bigr:inventoryItemView tag's documentation (see the class comments
	** on InventoryItemViewTag).
	**
	** @param A ResultsHelper must be defined within one of the scopes
	**   under the ResultsHelper.KEY.  This JSP obtains a list
	**	 of sample helpers from the ResultsHelper.
	** @param A request parameter named <code>includeItemSelector</code> may 
	**   optionally be defined to dictate whether the checkbox is included
	**	 or not.  If it is included and set to "true", the detail rows will
	**   support item selection.
	** @param A request parameter named <code>groupedByCase</code> may
	**   optionally be defined to indicate that the results are grouped by
	**   case and that the display should visually indicate case groupings
	**   (currently with a bold line) whenever adjacent rows have different
	**   case numbers.  The default is false.
--%> 
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ardais.bigr.api.ApiException"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions"%>
<%@ page import="com.ardais.bigr.api.ApiLogger"%>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ page import="org.apache.commons.logging.Log"%>
<%@ page import="org.apache.commons.logging.LogFactory"%>
<%@ page import="com.ardais.bigr.library.SampleViewData"%>
<%@ page import="com.ardais.bigr.library.web.helper.ResultsHelper"%>
<%@ page import="org.apache.commons.lang.BooleanUtils" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%!
  // WARNING: This is a declaration block, not a regular scriptlet.  Only
  // put method declarations and static variables here, otherwise you risk
  // having a JSP page that isn't thread-safe.
  //vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
    
	// Logger for logging performance-related items.
	//
	private static Log _perfLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_PERFORMANCE);

  //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  // WARNING: This is a declaration block, not a regular scriptlet.  Only
  // put method declarations and static variables here, otherwise you risk
  // having a JSP page that isn't thread-safe.
%>
<%
  if (_perfLog.isDebugEnabled()) {
    _perfLog.debug("   20: START generate item detail table HTML page");
  }
  long pageStartTime = System.currentTimeMillis();

  // Standard block for all query pages to find the proper txType
	// also MUST put a hidden txType field in the form
	String txType = null;	
	{
  	com.ardais.bigr.library.web.form.QueryForm qform = 
	  	(com.ardais.bigr.library.web.form.QueryForm) request.getAttribute("queryForm");
  	if (qform != null) {
  		txType = qform.getTxType();
  	}
  	else {
  		com.ardais.bigr.library.web.form.ResultsForm rform = 
  			(com.ardais.bigr.library.web.form.ResultsForm) request.getAttribute("resultsForm");
      if (rform != null) {
  			txType = rform.getTxType();
	  	}
		  else {
		    txType = request.getParameter("txType");
	  	}
	  }
	} // end standard txType block
		
  ResultsHelper helper = ResultsHelper.get(txType, request);
	if (helper == null) {
		throw new ApiException("Request attribute " + ResultsHelper.KEY + txType
		  + " not defined in TissueDetailTable.jsp");
	}

	//---------------------------  synch entire request on the ResultsHelper
	synchronized (helper) {
	//---------------------------  synch entire request on the ResultsHelper
		
		// Get data to output in the actual table (row numbers and sample data).
		SampleViewData viewData = helper.getHelpers();
		
		String ccKey = viewData.getViewParams().getKey();

		viewData.setFirstItemIndex(helper.getCurrentChunkIndex()); 
		
		//unless directed to not include a callback method, use the positionButtonsAndDetails
		//method defined below
		String itemViewElementsStartedCallback =
		  (String)request.getParameter("itemViewElementsStartedCallback");
		if (!"none".equalsIgnoreCase(itemViewElementsStartedCallback)) {
		  if (ApiFunctions.isEmpty(itemViewElementsStartedCallback)) {
		    itemViewElementsStartedCallback = "positionButtonsAndDetails";
		  }
		  viewData.setItemViewElementsStartedCallback(itemViewElementsStartedCallback);
		}

		// Default is true.
		boolean includeItemSelector =
		  !("false".equalsIgnoreCase(request.getParameter("includeItemSelector")));
		viewData.setIncludeItemSelector(includeItemSelector);

		// This is used to add case-grouping lines.  Default is false.
		viewData.setGroupedByCase(
		  "true".equalsIgnoreCase((String)request.getParameter("groupedByCase")));
		  
		//If there was a message passed in to be used when there are no samples
		//available for display, use it
		String noSampleMessage = (String)request.getParameter("noSampleMessage");
		if (!ApiFunctions.isEmpty(noSampleMessage)) {
		  viewData.setHtmlForEmptyDisplay(noSampleMessage);
		}
%>

<script type="text/javascript">
<%-- Position/size the buttons and the item details container. --%>
function positionButtonsAndDetails(detailsContainer) {
  var detailsContainerId = detailsContainer.id;
  var buttonsDiv = document.all.Buttons;
  detailsContainer.style.setExpression('pixelHeight',
    'Math.max(100, document.body.clientHeight - document.all.Buttons.offsetHeight - document.all.'
      + detailsContainerId + '.offsetTop)');
  buttonsDiv.style.setExpression('pixelTop',
	  'document.all.' + detailsContainerId + '.offsetTop + document.all.'
	    + detailsContainerId + '.offsetHeight');
  document.recalc(true);
}
</script>

<div id="allItems">
<bigr:inventoryItemView itemViewData="<%= viewData %>" />
</div>
<%
  	long pageElapsedTime = System.currentTimeMillis() - pageStartTime;
		if (_perfLog.isDebugEnabled()) {
			_perfLog.debug("   20: END   generate item detail table HTML page (" + pageElapsedTime + " ms)");
		}
	}	// END RESULTSHELPER SYNCHRONIZED BLOCK
%>
