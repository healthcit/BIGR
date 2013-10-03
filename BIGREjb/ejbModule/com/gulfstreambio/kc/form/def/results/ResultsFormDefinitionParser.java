package com.gulfstreambio.kc.form.def.results;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXMLParserBase;
import com.ardais.bigr.util.digester.GsbDigester;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.util.KcUtils;

 class ResultsFormDefinitionParser extends BigrXMLParserBase {

   public static final String DOCTYPE_PUBLIC_IDENTIFIER_XML1 =
     "-//GulfStream Bioinformatics Corporation//DTD KnowledgeCapture Form Definition XML1//EN";
   public static final String DOCTYPE_DTD_URI_XML1 = "kcResultsFormXML1.dtd";
   
   /**
    * Creates a new <code>ResultsFormDefinitionParser</code>.
    */
   public ResultsFormDefinitionParser() {
     super();
   }

   /**
    * Parses the specified formDefinition XML document and returns a new <code>ResultsFormDefinition</code>
    * that represents the contents of the document.
    * 
    * @param formDefinitionXml the formDefinition XML document
    * @return The <code>ResultsFormDefinition</code> instance.
    * @throws ApiException if there is a problem parsing the form definition XML document.
    */
   public ResultsFormDefinition performParse(String formDefinitionXml) {
     return performParse(new InputSource(new StringReader(formDefinitionXml)));
   }

   /**
    * Parses the specified formDefinition XML document and returns a new <code>ResultsFormDefinition</code>
    * that represents the contents of the document.
    * 
    * @param formDefinitionXml the formDefinition XML document
    * @return The <code>ResultsFormDefinition</code> instance.
    * @throws ApiException if there is a problem parsing the form definition XML document.
    */
   public ResultsFormDefinition performParse(FileInputStream fis) {
     return performParse(new InputSource(fis));
   }
   
   /**
    * Parses the specified formDefinition XML document and returns a new <code>ResultsFormDefinition</code>
    * that represents the contents of the document.
    * 
    * @param formDefinitionXml the formDefinition XML document
    * @return The <code>ResultsFormDefinition</code> instance.
    * @throws ApiException if there is a problem parsing the form definition XML document.
    */
   private ResultsFormDefinition performParse(InputSource inputSource) {

     // Create a new ResultsFormDefinition instance to be returned.
     ResultsFormDefinition formDef = new ResultsFormDefinition();

     Digester digester = getDigester();

     // Push the new ResultsFormDefinition onto the Digester stack.
     digester.push(formDef);

     // Parse the specified XML document.
     try {
       digester.parse(inputSource);
     }
     catch (SAXException se) {
       ApiFunctions.throwAsRuntimeException(se);
     }
     catch (IOException ie) {
       ApiFunctions.throwAsRuntimeException(ie);
     }

     // Return the ResultsFormDefinition instance.
     return formDef;

   }

   private Digester getDigester() {
     GsbDigester digester = (new BigrXMLParserBase()).makeDigester();

     // Register the location of the DTD.
     URL dtdURL = ResultsFormDefinitionParser.class.getResource(KcUtils.KC_CLASSPATH_RESOURCES_PREFIX
         + DOCTYPE_DTD_URI_XML1);
     digester.register(DOCTYPE_PUBLIC_IDENTIFIER_XML1, dtdURL.toString());
     digester.setValidating(true);

     // Define the digester rules...

     // Set the properties of the ResultsFormDefinition object from the resultsFormDefinition element.
     digester.addSetProperties("resultsFormDefinition");

     // Create the ResultsFormDefinitionElements object for the formElements element, set its
     // properties, and add it to the ResultsFormDefinition instance.
     digester.addObjectCreate("*/formElements", ResultsFormDefinitionElements.class);
     digester.addSetProperties("*/formElements");
     digester.addSetNextIf("*/formElements",
         "com.gulfstreambio.kc.form.def.results.ResultsFormDefinition", "setResultsFormElements",
         "com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionElements");

     // Create a ResultsFormDefinitionDataElement object for the dataElement element, set its
     // properties, and add it to the ResultsFormDefinitionElements instance
     digester.addObjectCreate("*/dataElement", ResultsFormDefinitionDataElement.class);
     digester.addSetProperties("*/dataElement");
     digester.addSetNextIf("*/dataElement",
         "com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionElements", "addResultsDataElement",
         "com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement");

     // Create a Tag object for the dataElement element, set its
     // properties, and add it to the ResultsFormDefinitionElements instance
     digester.addObjectCreate("*/dataElement/tag", Tag.class);
     digester.addSetProperties("*/dataElement/tag");
     digester.addSetNextIf("*/dataElement/tag",
         "com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement", "addTag",
         "com.gulfstreambio.kc.form.def.Tag");

     // Create a ResultsFormDefinitionHostElement object for the hostElement element, set its
     // properties, and add it to the ResultsFormDefinitionElements instance
     digester.addObjectCreate("*/hostElement", ResultsFormDefinitionHostElement.class);
     digester.addSetProperties("*/hostElement");
     digester.addSetNextIf("*/hostElement",
         "com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionElements", "addResultsHostElement",
         "com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionHostElement");

     // Create a Tag object for the hostElement element, set its
     // properties, and add it to the ResultsFormDefinitionElements instance
     digester.addObjectCreate("*/hostElement/tag", Tag.class);
     digester.addSetProperties("*/hostElement/tag");
     digester.addSetNextIf("*/hostElement/tag",
         "com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionHostElement", "addTag",
         "com.gulfstreambio.kc.form.def.Tag");
     
     return digester;
   }
}
