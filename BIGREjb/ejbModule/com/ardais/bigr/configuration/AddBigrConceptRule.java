package com.ardais.bigr.configuration;

import org.apache.commons.digester.CallMethodRule;
import org.xml.sax.Attributes;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.concept.BigrConcept;
import com.gulfstreambio.gboss.GbossConcept;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * A rule that retrieves a BigrConcept from ORMMasterData and passes the
 * retrieved BigrConcept to the specified method.
 */
public class AddBigrConceptRule extends CallMethodRule {

  /**
   * @see CallMethodRule#CallMethodRule(String, int, Class[])
   */
  public AddBigrConceptRule(String methodName) {
    super(methodName, 1, new Class [] {BigrConcept.class});
  }

  /**
   * Process the start of this element.
   *
   * @param attributes The attribute list for this element
   */
  public void begin(Attributes attributes) throws Exception {
    //make sure a non-empty cui value has been specified
    String cui = ApiFunctions.safeTrim(attributes.getValue("cui"));
    if (ApiFunctions.isEmpty(cui)) {
      throw new ApiException("Empty cui value found in XML document");
    }
    else {
     //create a BigrConcept from GbossConcept.  Gboss will throw
     //an exception if the cui doesn't correspond to a concept so we don't need
     //to check for that ourselves
     //Note: This used to retrieve a BigrConcept from ORMMasterData, but that class was removed
     //as part of the conversion to gboss.  Gboss holds GbossConcepts, not BigrConcepts, so 
     //we have to create a BigrConcept from the GbossConcept.
     GbossConcept gConcept = GbossFactory.getInstance().getConcept(cui);
     BigrConcept bConcept = new BigrConcept(gConcept.getCui(), gConcept.getDescription(), null, 
         null, null, null, null, null, null, null, null, null, null, null);     
     // Push an array to capture the BigrConcept
     Object parameters[] = new Object[1];
     parameters[0] = bConcept;
     digester.pushParams(parameters);
    }
  }

}
