package com.gulfstreambio.kc.util;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.DataElement;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionDataElement;

/**
 * KnowledgeCapture specific utilities.
 */
public class KcUtils {

  /**
   * The preferred classpath location under which KC resources are located.
   * This is the preferred place to put resources that should be considered
   * part of the software itself, rather than part of the description of how
   * to install or deploy the software.  Any resources that we'd expect to be
   * different in different deployments should NOT got here -- possibly
   * Api.properties or some other file under the ApiResources.getResourceDirectory()
   * directory would be a better location for such information.
   */
  public static final String KC_CLASSPATH_RESOURCES_PREFIX = "/com/gulfstreambio/kc/resources/";


  // Cannot be instantiated since this is a class of static methods.
  private KcUtils() {
    super();
  }

  /**
   * Returns the description/display name of the form definition data element.  If the display 
   * name is specified in the form definition then it is returned, otherwise the description of
   * the data element from the DET is returned.
   *  
   * @param dataElement the <code>FormDefinitionDataElement</code>
   * @return The description.
   */
  public static String getDescription(FormDefinitionDataElement dataElement) {
    String formDisplayName = dataElement.getDisplayName();
    if (!ApiFunctions.isEmpty(formDisplayName)) {
      return formDisplayName;
    }
    else {
      DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
      return det.getDataElement(dataElement.getCui()).getDescription();
    }
  }
  
  /**
   * Returns the description/display name of the data element instance from the specified form
   * definition.  If the display name is specified in the form definition then it is returned, 
   * otherwise the description of the data element from the DET is returned.
   *  
   * @param dataElement the <code>DataElement</code> instance
   * @param form the <code>FormDefinition</code>
   * @return The description.
   */
  public static String getDescription(DataElement dataElement, FormDefinition form) {
    return KcUtils.getDescription(form.getDataElement(dataElement.getCuiOrSystemName()));
  }

}
