package com.gulfstreambio.kc.web.support;

import javax.servlet.ServletRequest;

import com.ardais.bigr.util.UniqueIdGenerator;
import com.ardais.bigr.web.StrutsProperties;
import com.gulfstreambio.kc.det.DetValueSet;

/**
 * An HTML element value set for a non-hierarchical set of values for a singlevalued element for the
 * standard set of values. This adds a value that allows exapnding the list to the broad set of
 * values.
 */
public class HtmlElementValueSetSingleStandard extends HtmlElementValueSetSingle {

  /**
   * Creates a new <code>HtmlElementValueSetSingleStandard</code> from the specified
   * <code>DetValueSet</code>, for an HTML element with the specified property.
   * 
   * @param request the servlet request
   * @param context the <code>DataFormElementContext</code> that represents the data element
   *        currently being rendered
   * @param valueSet the element value set
   * @param selectedValue the value to select when the HTML is generated. This may be null to
   *          indicate that no value should be selected.
   * @param includeOther <code>true</code> if the "other" value should be included when generating
   *          HTML, if it is present; <code>false</code> otherwise
   */
  public HtmlElementValueSetSingleStandard(ServletRequest request, 
                                           DataFormElementContext context, DetValueSet valueSet, 
                                           String selectedValue, boolean includeOther) {
    super(request, context, valueSet, selectedValue, includeOther);
    UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
    addElementValue(WebUtils.BROAD_VS_VALUE, 
        StrutsProperties.getInstance().getProperty("ddc.valueset.broad"),
        getName(), idgen.getUniqueId(), false);
  }

  /**
   * Creates a new <code>HtmlElementValueSetSingleStandard</code> from the specified
   * <code>DetValueSet</code>, for an HTML element with the specified property.
   * 
   * @param request the servlet request
   * @param context the <code>DataFormElementContext</code> that represents the data element
   *        currently being rendered
   * @param valueSet the element value set
   * @param selectedValue the value to select when the HTML is generated. This may be null to
   *          indicate that no value should be selected.
   * @param excludedValues values to exclude from the value set
   * @param includeOther <code>true</code> if the "other" value should be included when generating
   *          HTML, if it is present; <code>false</code> otherwise
   */
  public HtmlElementValueSetSingleStandard(ServletRequest request, 
                                           DataFormElementContext context, DetValueSet valueSet,
                                           String selectedValue, String[] excludedValues,
                                           boolean includeOther) {
    super(request, context, valueSet, selectedValue, excludedValues, includeOther);
    UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
    addElementValue(WebUtils.BROAD_VS_VALUE, 
        StrutsProperties.getInstance().getProperty("ddc.valueset.broad"),
        getName(), idgen.getUniqueId(), false);
  }

}
