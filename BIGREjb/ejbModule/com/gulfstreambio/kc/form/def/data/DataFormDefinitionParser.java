package com.gulfstreambio.kc.form.def.data;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXMLParserBase;
import com.ardais.bigr.util.digester.GsbDigester;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.data.calculation.Calculation;
import com.gulfstreambio.kc.form.def.data.calculation.DataElementDefinitionReference;
import com.gulfstreambio.kc.form.def.data.calculation.Literal;
import com.gulfstreambio.kc.util.KcUtils;

/**
 * Parses a formDefinition XML file and returns a {@link DataFormDefinition}object.
 */
class DataFormDefinitionParser extends BigrXMLParserBase {

  static final String DOCTYPE_PUBLIC_IDENTIFIER_XML1 =
    "-//GulfStream Bioinformatics Corporation//DTD KnowledgeCapture Form Definition XML1//EN";
  static final String DOCTYPE_DTD_URI_XML1 = "kcFormDefinitionXML1.dtd";

  /**
   * Creates a new <code>DataFormDefinitionParser</code>.
   */
  DataFormDefinitionParser() {
    super();
  }

  /**
   * Parses the specified formDefinition XML document and returns a new <code>DataFormDefinition</code>
   * that represents the contents of the document.
   * 
   * @param formDefinitionXml the formDefinition XML document
   * @return The <code>DataFormDefinition</code> instance.
   * @throws ApiException if there is a problem parsing the form definition XML document.
   */
  DataFormDefinition performParse(String formDefinitionXml) {

    // Create a new DataFormDefinition instance to be returned.
    DataFormDefinition formDef = new DataFormDefinition();

    Digester digester = getDigester();

    // Push the new DataFormDefinition onto the Digester stack.
    digester.push(formDef);

    // Parse the specified XML document.
    try {
      digester.parse(new StringReader(formDefinitionXml));
    }
    catch (SAXException se) {
      ApiFunctions.throwAsRuntimeException(se);
    }
    catch (IOException ie) {
      ApiFunctions.throwAsRuntimeException(ie);
    }

    // Return the DataFormDefinition instance.
    return formDef;

  }

  private Digester getDigester() {
    GsbDigester digester = (new BigrXMLParserBase()).makeDigester();

    // Register the location of the DTD.
    URL dtdURL = DataFormDefinitionParser.class.getResource(KcUtils.KC_CLASSPATH_RESOURCES_PREFIX
        + DOCTYPE_DTD_URI_XML1);
    digester.register(DOCTYPE_PUBLIC_IDENTIFIER_XML1, dtdURL.toString());
    digester.setValidating(true);

    // Define the digester rules...

    // Set the properties of the DataFormDefinition object from the formDefinition element.
    digester.addSetProperties("formDefinition");

    // Create the DataFormDefinitionElements object for the formElementDefinitions element, set its
    // properties, and add it to the DataFormDefinition instance.
    digester.addObjectCreate("*/formElementDefinitions", DataFormDefinitionElements.class);
    digester.addSetProperties("*/formElementDefinitions");
    digester.addSetNextIf("*/formElementDefinitions",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinition", "setDataFormElements",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionElements");

    // Create a DataFormDefinitionCategory object for the categoryDefinition element, set its
    // properties, and add it to the DataFormDefinitionElements instance or another
    // CategoryDefinitions instance.
    digester.addObjectCreate("*/categoryDefinition", DataFormDefinitionCategory.class);
    digester.addSetProperties("*/categoryDefinition");
    digester.addSetNextIf("*/categoryDefinition",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionElements", "addDataCategory",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory");
    digester.addSetNextIf("*/categoryDefinition",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory", "addDataCategory",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory");

    // Create a DataFormDefinitionDataElement object for the dataElementDefinition element, set its
    // properties, and add it to the DataFormDefinitionElements instance or CategoryDefinitions
    // instance.
    digester.addObjectCreate("*/dataElementDefinition", DataFormDefinitionDataElement.class);
    digester.addSetProperties("*/dataElementDefinition");
    digester.addSetNextIf("*/dataElementDefinition",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionElements", "addDataDataElement",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement");
    digester.addSetNextIf("*/dataElementDefinition",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory", "addDataDataElement",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement");

    // Create a Tag object for the dataElementDefinition element, set its
    // properties, and add it to the DataFormDefinitionDataElement instance
    digester.addObjectCreate("*/dataElementDefinition/tag", Tag.class);
    digester.addSetProperties("*/dataElementDefinition/tag");
    digester.addSetNextIf("*/dataElementDefinition/tag",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement", "addTag",
        "com.gulfstreambio.kc.form.def.Tag");

    // Create a Calculation object for the calculation element, set its
    // properties, and add it to the DataFormDefinitionDataElement instance
    digester.addObjectCreate("*/dataElementDefinition/calculation", Calculation.class);
    digester.addSetProperties("*/dataElementDefinition/calculation");
    digester.addSetNext("*/dataElementDefinition/calculation",
        "setCalculation",
        "com.gulfstreambio.kc.form.def.data.calculation.Calculation");

    // Create a Calculation object for the calculation element, set its
    // properties, and add it to the Calculation instance
    digester.addObjectCreate("*/calculation/calculation", Calculation.class);
    digester.addSetProperties("*/calculation/calculation");
    digester.addSetNext("*/calculation/calculation",
        "addOperand",
        "com.gulfstreambio.kc.form.def.data.calculation.Operand");

    // Create a DataElementDefinitionReference object for the dataElementDefinitionReference element, 
    // set its properties, and add it to the Calculation instance
    digester.addObjectCreate("*/calculation/dataElementDefinitionReference", DataElementDefinitionReference.class);
    digester.addSetProperties("*/calculation/dataElementDefinitionReference");
    digester.addSetNext("*/calculation/dataElementDefinitionReference",
        "addOperand",
        "com.gulfstreambio.kc.form.def.data.calculation.Operand");

    // Create a Literal object for the literal element, 
    // set its properties, and add it to the Calculation instance
    digester.addObjectCreate("*/calculation/literal", Literal.class);
    digester.addSetProperties("*/calculation/literal");
    digester.addSetNext("*/calculation/literal",
        "addOperand",
        "com.gulfstreambio.kc.form.def.data.calculation.Operand");

    // Create a ResultsFormDefinitionHostElement object for the hostElement element, set its
    // properties, and add it to the ResultsFormDefinitionElements instance
    digester.addObjectCreate("*/hostElement", DataFormDefinitionHostElement.class);
    digester.addSetProperties("*/hostElement");
    digester.addSetNextIf("*/hostElement",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionElements", "addDataHostElement",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement");
    digester.addSetNextIf("*/hostElement",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory", "addDataHostElement",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement");

    // Create a Tag object for the hostElement element, set its
    // properties, and add it to the DataFormDefinitionHostElement instance
    digester.addObjectCreate("*/hostElement/tag", Tag.class);
    digester.addSetProperties("*/hostElement/tag");
    digester.addSetNextIf("*/hostElement/tag",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement", "addTag",
        "com.gulfstreambio.kc.form.def.Tag");

    // Create the DataFormDefinitionValueSets object for the valueSetDefinitions element, set its
    // properties, and add it to the DataFormDefinition instance.
    digester.addObjectCreate("*/valueSetDefinitions", DataFormDefinitionValueSets.class);
    digester.addSetProperties("*/valueSetDefinitions");
    digester.addSetNextIf("*/valueSetDefinitions", "com.gulfstreambio.kc.form.def.data.DataFormDefinition",
        "setValueSets", "com.gulfstreambio.kc.form.def.data.DataFormDefinitionValueSets");

    // Create a DataFormDefinitionValueSet object for the valueSetDefinition element, set its
    // properties, and add it to the DataFormDefinitionValueSets instance.
    digester.addObjectCreate("*/valueSetDefinition", DataFormDefinitionValueSet.class);
    digester.addSetProperties("*/valueSetDefinition");
    digester.addSetNextIf("*/valueSetDefinition",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionValueSets", "addValueSet",
        "com.gulfstreambio.kc.form.def.data.DataFormDefinitionValueSet");

    // Create a DataFormDefinitionValue object for the valueDefinition element, set its
    // properties, and add it to either the DataFormDefinitionValueSets instance or another
    // ValueDefinitions instance.
    digester.addObjectCreate("*/valueDefinition", DataFormDefinitionValue.class);
    digester.addSetProperties("*/valueDefinition");
    digester.addSetNextIf("*/valueDefinition", "com.gulfstreambio.kc.form.def.data.DataFormDefinitionValueSet",
        "addValue", "com.gulfstreambio.kc.form.def.data.DataFormDefinitionValue");
    digester.addSetNextIf("*/valueDefinition", "com.gulfstreambio.kc.form.def.data.DataFormDefinitionValue",
        "addValue", "com.gulfstreambio.kc.form.def.data.DataFormDefinitionValue");

    // Create a DataFormDefinitionAdeElement object for the ade element, set its
    // properties, and add it to the DataFormDefinitionDataElement instance
    digester.addObjectCreate("*/adeElementDefinition", DataFormDefinitionAdeElement.class);
    digester.addSetProperties("*/adeElementDefinition");
    digester.addSetNextIf("*/adeElementDefinition", "com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement",
        "addAdeElement", "com.gulfstreambio.kc.form.def.data.DataFormDefinitionAdeElement");

    return digester;
  }
}
