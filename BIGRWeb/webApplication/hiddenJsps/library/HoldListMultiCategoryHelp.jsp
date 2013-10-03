<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<html:html>
<head>
<title>Help: Items on Hold</title>
<link rel="stylesheet" type="text/css"
	href='<html:rewrite page="/css/bigr.css"/>'>

<style type="text/css">
LI {
  margin-top: 8px;
}
</style>

<script type="text/javascript">
var myBanner = 'Help: Items on Hold';  

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
}
</script>
</head>
<body class="bigr" onload="initPage();">

<h3>Help: Items on Hold</h3>

<p>The list of items that you have placed on hold is divided into categories.
Each category has its own set of actions that you can take on items in that
category.  In addition, there are several actions that you can apply to all
items on your hold list, regardless of what category they are in.
</p>

<p>Each of the categories has a heading with a list of items in that
category displayed below it.  Buttons that appear inside a category heading
apply only to the items in that category.  Buttons that appear elsewhere on
the screen apply to items across all categories.
</p>

The actions that apply to items across all categories are:
<ul>
  <li><b>View: View Name.</b>  This link shows the name of the view currently in use
    for displaying the data.  When clicked, this link shows a menu from which you may select a 
    different view or from which you may display the Manage Views screen.
  </li>
  <li><b>Clipboard Actions.</b>  Clicking on this brings up a menu with
      two choices.
      <ul>
        <li><b>Copy All Sample Ids to Clipboard.</b>  Copy the sample ids for all of the
            items in all categories to your computer's clipboard.
        </li>
        <li><b>Copy Selected Sample Ids to Clipboard.</b>  Copy the sample ids for all selected
            items in all categories to your computer's clipboard.  Indicate which items you
            want to select by using the check box on the item's row.
        </li>
      </ul>
  </li>
  <li><b>Help.</b>  Display this help page.
  </li>
  <bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_SAMPLE_SELECT%>">
    <li><b>Select Additional Items.</b>  Return to the screen where you
      search for items to place on hold.
    </li>
  </bigr:hasPrivilege>
  <li><b>Remove Selected Items.</b>  Remove items from your hold list.
    Indicate which items you want to remove by selecting the check box
    on the item's row.  The items will not be removed from your hold list
    until you click the Remove Selected Items button.
  </li>
</ul>

The categories of items are:
<ol>
<li><b>Items in your supplier biorepositories.</b>  These are items from your supplier's general inventory
  that you may license for your research.
  The actions that apply specifically to this category are:
  <ul>
    <li><b>Order These Items.</b>  This informs your supplier that you want
     to license the items in this category. You will be taken to a screen
      where you may provide additional information that will help fulfill your order.
       Your supplier will then contact you to ensure that you have all of the
        information you need to make your ordering decisions and complete the
         licensing process. You may also order items by contacting your supplier by phone or email. 
    </li>
    <li><b>Clipboard Actions.</b>  Clicking on this brings up a menu with
      two choices.
      <ul>
        <li><b>Copy Sample Ids to Clipboard.</b>  Copy the sample ids for
          all of the items in this category to your computer's clipboard.
        </li>
        <li><b>Copy Section to Clipboard.</b>  Copy all of the information for
          the items in this category to your computer's clipboard.
          When you paste this information into Microsoft&#174; Excel, it
          preserves the data's row and column arrangement.
        </li>
      </ul>
    </li>
  </ul>
</li>

<li><b>Available items in your biorepositories.</b>  This category and the
  remaining two other categories all involve items that are in proprietary
  specimen repositories that you are authorized to access.  These items
  are managed using HealthCare IT Corporation software, but the items don't belong to HealthCare IT Corporation.
  When you want these items for your research, you request them for
  direct delivery to you through your institution's repository managers.
  <p>Items in this category are currently available for you to request
  for your research, although your site may have additional policies
  governing item availability and specimen use.  
  The actions that apply specifically to this category are:
  <ul>
    <li><b>Request These Items.</b>  Place a <i>Research Request</i> to have
      the items in this category delivered to you for use in your research.
      You will be taken to a screen where you can provide additional information
      about your request, including any authorizations that your
      site may require for specimen requests.  You will receive email when
      your request has been packaged and is ready for pickup or delivery.
    </li>
    <li><b>Clipboard Actions.</b>  See the description in the Items in your supplier biorepository category.
    </li>
  </ul>
</li>

<li><b>Items on your pending requests.</b>  These items are on Research
  Requests that you have placed but that have not yet been completed.
  You cannot remove items that are on pending requests from your hold list.
  If you need to make changes after you have placed a request, contact your
  site's repository manager for assistance.
  The actions that apply specifically to this category are:
  <ul>
    <bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_ILTDS_VIEW_REQUESTS%>">
      <li><b>View My Requests.</b>  Go to a screen where you can review all of
        your current and past Research Requests.
      </li>
    </bigr:hasPrivilege>
    <li><b>Clipboard Actions.</b>  See the description in the Items in your supplier biorepository category.
    </li>
  </ul>
</li>

<li><b>Items in your biorepositories that are currently unavailable.</b>
  These items are in proprietary specimen repositories that you are authorized
  to access, but are not currently available for Research Requests and are
  not on any of your pending requests.  Items may be unavailable for a variety
  of reasons.  For example, unavailable items include items that are not
  currently stored in your local repository, that other researchers currently
  have on hold, or for which the donor has withdrawn their consent.
  The actions that apply specifically to this category are:
  <ul>
    <li><b>Clipboard Actions.</b>  See the description in the Items in your supplier biorepository category.
    </li>
  </ul>
</li>
</ol>

</body>
</html:html>
