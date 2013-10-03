package com.gulfstreambio.kc.web.support;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.SystemNameUtils;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;

/**
 */
public class HtmlElementNamer {
 
  private HtmlElementNamer() {
    super();
  }

  /**
   * Returns the name of the parameter that holds the data element system name 
   * in an ADE popup edit window. 
   *
   * @return  The parameter name.
   */
  public static String getAdePopupDataElementSystemProperty() {
    return "dataElementSystemName";
  }  
  
  public static String getJavaScriptReferenceQueryElements() {
    // DEVELOPER NOTE: This is hardcoded in queryElements.js, so if you change it here, then
    // change it there as well.
    return "GSBIO.kc.query.Elements";
  }
  
  public static String getJavaScriptQueryElementsToRequestParameter() {
    return getJavaScriptReferenceQueryElements() + ".toRequestParameter();";
  }
  
}
