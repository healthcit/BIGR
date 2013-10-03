package com.ardais.bigr.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.library.SampleViewData;

/**
 * Tag handler for the <code>inventoryItemView</code> tag.  This tag
 * generates a table view of details regarding a list of inventory items
 * (e.g. samples).  This is used to display Sample Selection result sets,
 * among other uses.  The itemViewData attribute must be a
 * {@link SampleViewData} instance describing the view.
 * 
 * <p>The context in which this tag is used must satisfy several requirements:
 * <ul>
 * <li>The page must include bigr.css, common.js, ssresults.js, and overlib.js.</li>
 * <li>The page must have a handler for its BODY onload event that calls the
 *     commonInitPage() function defined in ssresults.js.  Also, this handler must
 *     set the variable _isPageReadyForInteraction to true as the very last thing that
 *     the onload handler does.</li>
 * <li>Prior to calling commonInitPage(), the page may optionally set a variable named
 *     myBanner to be the banner text to display in the top frame.  If this is undefined,
 *     commonInitPage won't set the top frame banner text to anything.</li>
 * </ul>
 */
public class InventoryItemViewTag extends TagSupport {

  private SampleViewData _itemViewData = null;

  public InventoryItemViewTag() {
    super();
  }

  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
    _itemViewData = null;
  }

  /**
   * @return
   */
  public SampleViewData getItemViewData() {
    return _itemViewData;
  }

  /**
   * @param data
   */
  public void setItemViewData(SampleViewData data) {
    _itemViewData = data;
  }

  /**
   */
  public int doStartTag() throws JspException {
    pageContext.setAttribute("iivTagViewData", getItemViewData(), PageContext.REQUEST_SCOPE);

    try {
      pageContext.include("/hiddenJsps/library/InventoryItemView.jsp");
    }
    catch (Exception e) {
      String msg = "Exception in JSP tag implementation.";
      ApiLogger.log(msg, e);
      throw new JspException(msg);
    }
    return EVAL_BODY_INCLUDE;
  }

}
