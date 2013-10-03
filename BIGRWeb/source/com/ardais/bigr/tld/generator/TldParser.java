package com.ardais.bigr.tld.generator;

import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXMLParserBase;

/**
 * Parses a TLD file, generating a {@link JspTaglib} object. 
 */
public class TldParser extends BigrXMLParserBase {

  /**
   * The full path to the TLD file to be parsed.
   */
  private String _tldFileName;

  /**
   * Creates a new TldParser.
   * 
   * @param  tldFileName  the full path to the TLD file  
   */
  public TldParser(String tldFileName) {
    super();
    if (ApiFunctions.isEmpty(tldFileName)) {
      throw new IllegalArgumentException("tldFileName parameter must not be empty");
    }
    setTldFileName(tldFileName);
  }

  /**
   * Parses the tld XML document into a JspTaglib object and returns it.  
   * 
   * @return  The <code>JspTaglib</code>
   */
  public JspTaglib performParse() {
    String tldFilename = getTldFileName();
    System.out.println("Parsing " + tldFilename + "...");

    // Create a default digester.
    Digester digester = makeDigester();

    // Create a JspTaglib instance and put it on the digester stack. 
    JspTaglib taglib = new JspTaglib();
    digester.push(taglib);

    // Define the digester rules...

    // Set the properties of the JspTaglib object.
    digester.addCallMethod("taglib/tlibversion", "setTlibversion", 0);
    digester.addCallMethod("taglib/jspversion", "setJspversion", 0);
    digester.addCallMethod("taglib/shortname", "setShortname", 0);
    digester.addCallMethod("taglib/info", "setInfo", 0);

    // Create a JspTag object for each tag element.
    digester.addObjectCreate("*/tag", JspTag.class);

    // Set the properties of the JspTag object.
    digester.addCallMethod("*/tag/name", "setName", 0);
    digester.addCallMethod("*/tag/tagclass", "setTagclass", 0);
    digester.addCallMethod("*/tag/teiclass", "setTeiclass", 0);
    digester.addCallMethod("*/tag/bodycontent", "setBodycontent", 0);
    digester.addCallMethod("*/tag/info", "setInfo", 0);

    // Add the JspTag object to its parent object on the top of the stack.
    digester.addSetNext("*/tag", "addTag", "com.ardais.bigr.tld.generator.JspTag");

    // Create a JspTagAttribute object for each attribute element.
    digester.addObjectCreate("*/tag/attribute", JspTagAttribute.class);

    // Set the properties of the JspTagAttribute object.
    digester.addCallMethod("*/tag/attribute/name", "setName", 0);
    digester.addCallMethod("*/tag/attribute/required", "setRequired", 0);
    digester.addCallMethod("*/tag/attribute/rtexprvalue", "setRtexprvalue", 0);

    // Add the JspTagAttribute object to its parent object on the top of the stack.
    digester.addSetNext(
      "*/tag/attribute",
      "addAttribute",
      "com.ardais.bigr.tld.generator.JspTagAttribute");

    // Now that everything is set up, parse the file.
    try {
      digester.parse(tldFilename);
    }
    catch (SAXException se) {
      throw new ApiException(se);
    }
    catch (IOException ie) {
      throw new ApiException(ie);
    }

    System.out.println("Completed parsing " + tldFilename + ".");

    return taglib;
  }

  /**
   * Returns the file name of the TLD file, including the full path to the file.
   *   
   * @return  The filename. 
   */
  public String getTldFileName() {
    return _tldFileName;
  }

  /**
   * Sets the file name of the TLD file, including the full path to the file.
   *   
   * @param  filename  the filename 
   */
  public void setTldFileName(String filename) {
    _tldFileName = filename;
  }
}
