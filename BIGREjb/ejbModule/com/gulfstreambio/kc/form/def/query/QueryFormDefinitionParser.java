package com.gulfstreambio.kc.form.def.query;

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
import com.gulfstreambio.kc.util.KcUtils;

/**
 * Parses a queryFormDefinition XML file and returns a {@link QueryFormDefinition} instance.
 */
class QueryFormDefinitionParser extends BigrXMLParserBase {

  static final String DOCTYPE_PUBLIC_IDENTIFIER_XML1 =
    "-//GulfStream Bioinformatics Corporation//DTD KnowledgeCapture Query Form Definition XML1//EN";
  static final String DOCTYPE_DTD_URI_XML1 = "kcQueryFormDefinitionXML1.dtd";

  /**
   * Creates a new <code>QueryFormDefinitionParser</code>.
   */
  QueryFormDefinitionParser() {
    super();
  }

  /**
   * Parses the specified queryFormDefinition XML document and returns a new 
   * <code>QueryFormDefinition</code> instance that represents the contents of the document.
   * 
   * @param formDefinitionXml the formDefinition XML document
   * @return The <code>QueryFormDefinition</code> instance.
   * @throws ApiException if there is a problem parsing the query form definition XML document.
   */
  QueryFormDefinition performParse(String formDefinitionXml) {

    // Create a new QueryFormDefinition instance to be returned.
    QueryFormDefinition form = new QueryFormDefinition();

    Digester digester = getDigester();

    // Push the new QueryFormDefinition onto the Digester stack.
    digester.push(form);

    // Parse the specified XML document.
    try {
      digester.parse(new StringReader(formDefinitionXml));
      
      // Set the default operators for value sets.  Since the value set operator is not required
      // by the DTD, but we need it to be specified in certain situations, attempt to set an
      // appropriate default based on the value(s) in the value set.
      form.setValueSetDefaultOperators();
    }
    catch (SAXException se) {
      ApiFunctions.throwAsRuntimeException(se);
    }
    catch (IOException ie) {
      ApiFunctions.throwAsRuntimeException(ie);
    }

    // Return the QueryFormDefinition instance.
    return form;

  }

  private Digester getDigester() {
    GsbDigester digester = (new BigrXMLParserBase()).makeDigester();

    // Register the location of the DTD.
    URL dtdURL = QueryFormDefinitionParser.class.getResource(KcUtils.KC_CLASSPATH_RESOURCES_PREFIX
        + DOCTYPE_DTD_URI_XML1);
    digester.register(DOCTYPE_PUBLIC_IDENTIFIER_XML1, dtdURL.toString());
    digester.setValidating(true);

    // Define the digester rules...

    // Set the properties of the QueryFormDefinition object from the queryFormDefinition element.
    digester.addSetProperties("queryFormDefinition");

    // Create the QueryFormDefinitionElements object for the formElements element, set its
    // properties, and add it to the QueryFormDefinition instance.
    digester.addObjectCreate("*/formElements", QueryFormDefinitionElements.class);
    digester.addSetProperties("*/formElements");
    digester.addSetNextIf("*/formElements",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinition", "setQueryFormElements",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionElements");

    // Create a QueryFormDefinitionCategory object for the category element, set its
    // properties, and add it to the QueryFormDefinitionElements instance or another
    // QueryFormDefinitionCategory instance.
    digester.addObjectCreate("*/category", QueryFormDefinitionCategory.class);
    digester.addSetProperties("*/category");
    digester.addSetNextIf("*/category",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionElements", "addQueryCategory",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionCategory");
    digester.addSetNextIf("*/category",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionCategory", "addQueryCategory",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionCategory");

    // Create a QueryFormDefinitionDataElement object for the dataElement element, set its
    // properties, and add it to the QueryFormDefinitionElements instance or 
    // QueryFormDefinitionCategory instance.
    digester.addObjectCreate("*/dataElement", QueryFormDefinitionDataElement.class);
    digester.addSetProperties("*/dataElement");
    digester.addSetNextIf("*/dataElement",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionElements", "addQueryDataElement",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement");
    digester.addSetNextIf("*/dataElement",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionCategory", "addQueryDataElement",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement");

    // Create a Tag object for the tag element, set its properties, and add it to the 
    // QueryFormDefinitionDataElement instance.
    digester.addObjectCreate("*/dataElement/tag", Tag.class);
    digester.addSetProperties("*/dataElement/tag");
    digester.addSetNextIf("*/dataElement/tag",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement", "addTag",
        "com.gulfstreambio.kc.form.def.Tag");

    // Create a QueryFormDefinitionAdeElement object for the ADE element, set its properties, and 
    // add it to the QueryFormDefinitionDataElement instance.
    digester.addObjectCreate("*/dataElement/adeElement", QueryFormDefinitionAdeElement.class);
    digester.addSetProperties("*/dataElement/adeElement");
    digester.addSetNextIf("*/dataElement/adeElement",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement", "addAdeElement",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionAdeElement");

    // Create a QueryFormDefinitionValueSet object for the valueSet element, set its properties, 
    // and add it to the QueryFormDefinitionAdeElement instance.
    digester.addObjectCreate("*/adeElement/valueSet", QueryFormDefinitionValueSet.class);
    digester.addSetProperties("*/adeElement/valueSet");
    digester.addSetNextIf("*/adeElement/valueSet",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionAdeElement", "setValueSet",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet");

    // Create a QueryFormDefinitionValueSet object for the valueSet element, set its properties, 
    // and add it to the QueryFormDefinitionDataElement instance.
    digester.addObjectCreate("*/dataElement/valueSet", QueryFormDefinitionValueSet.class);
    digester.addSetProperties("*/dataElement/valueSet");
    digester.addSetNextIf("*/dataElement/valueSet",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement", "setValueSet",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet");

    digester.addObjectCreate("*/valueSet/valueComparison", QueryFormDefinitionValueComparison.class);
    digester.addSetProperties("*/valueSet/valueComparison");
    digester.addSetNextIf("*/valueSet/valueComparison",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet", "addValue",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison");

    digester.addObjectCreate("*/valueSet/valueAny", QueryFormDefinitionValueAny.class);
    digester.addSetProperties("*/valueSet/valueAny");
    digester.addSetNextIf("*/valueSet/valueAny",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet", "addValue",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueAny");

    // Create the QueryFormDefinitionRollupValueSets object for the rollupValueSets element, set
    // its properties, and add it to the QueryFormDefinition instance.
    digester.addObjectCreate("*/rollupValueSets", QueryFormDefinitionRollupValueSets.class);
    digester.addSetProperties("*/rollupValueSets");
    digester.addSetNextIf("*/rollupValueSets",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinition", "setRollupValueSets",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValueSets");

    // Create the QueryFormDefinitionRollupValueSet object for the rollupValueSet element, set
    // its properties, and add it to the QueryFormDefinitionRollupValueSets instance.
    digester.addObjectCreate("*/rollupValueSet", QueryFormDefinitionRollupValueSet.class);
    digester.addSetProperties("*/rollupValueSet");
    digester.addSetNextIf("*/rollupValueSet",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValueSets", 
        "addValueSet",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValueSet");

    // Create the QueryFormDefinitionRollupValue object for the rollupValue element, set
    // its properties, and add it to the QueryFormDefinitionRollupValueSet instance.
    digester.addObjectCreate("*/rollupValue", QueryFormDefinitionRollupValue.class);
    digester.addSetProperties("*/rollupValue");
    digester.addSetNextIf("*/rollupValue",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValueSet", "addValue",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValue");

    // Create the QueryFormDefinitionValueSet object for the rollupValue/valueSet element, set
    // its properties, and add it to the QueryFormDefinitionRollupValue instance.
    digester.addObjectCreate("*/rollupValue/valueSet", QueryFormDefinitionValueSet.class);
    digester.addSetProperties("*/rollupValue/valueSet");
    digester.addSetNextIf("*/rollupValue/valueSet",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValue", "setValueSet",
        "com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet");    

    return digester;
  }

}
