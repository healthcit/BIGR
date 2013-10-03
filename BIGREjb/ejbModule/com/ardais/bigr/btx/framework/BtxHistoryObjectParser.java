package com.ardais.bigr.btx.framework;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.CallMethodRule;
import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.NullProxy;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.util.BigrXMLParserBase;
import com.ardais.bigr.util.digester.CallMethodNoTrimRule;

/**
 * This class parses a BTX History Object that is encoded as an XML document.  See
 * {@link BtxHistoryObjectUtils} for details on BTX History Objects.
 */
public class BtxHistoryObjectParser {

  private Object _historyObject = null;

  private static Digester _digester;

  // Initialize the single shared Digester instance we'll reuse for all History Object parsing.
  static {
    Digester digester = (new BigrXMLParserBase()).makeDigester();

    // Register the location of the DTD.  If we supported multiple history object encoding
    // shemes, we might have more than one DTD to register here.  "XML1" is the name we've given
    // to the first version of our XML-based way of encoding a BTX History Object.
    //
    URL dtdURL =
      BtxHistoryObjectParser.class.getResource(
        ApiFunctions.CLASSPATH_RESOURCES_PREFIX + "btxHistoryObjectXML1.dtd");
    digester.register(
      "-//Ardais Corporation//DTD BTX History Object Encoding XML1//EN",
      dtdURL.toString());

    // Define the digester rules.  If we had multiple encoding schemes that we had to support
    // for history objects (perhaps because the format evolves and we still need to be able to
    // parse old history records encoded in older encoding schemes), we'd probably have to change
    // this to define multiple RuleSets instead (see org.apache.commons.digester.RuleSet).
    // For now we only need to support BtxHistoryObjectUtils.ENCODING_SCHEME_XML1, and the single
    // set of rules defined below handles that.

    // Below, when we get strings from body text, we use CallMethodNoTrimRule, an Ardais extension
    // of CallMethodRule that doesn't trim the body text when it is used as the method parameter.
    // This is necessary to accurately preserve strings exactly as they are in the History Object.
    // If the string in the History Object contains whitespace at its beginning or end, that's
    // the way we want to keep it.

    // Rules for historyObject:
    {
      digester.addRule("historyObject/string", new CallMethodNoTrimRule("setHistoryObject", 0));
    }

    // Rules for */attrs and */attr:
    {
      digester.addObjectCreate("*/attrs", BtxHistoryAttributes.class);

      digester
        .addRule("*/attrs", new CallMethodRule(-1, "linkToParent", 2, new Class[] { Object.class
        /* child */
        , Object.class /* parent */
      }));
      digester.addCallParam("*/attrs", 0, 0);
      digester.addCallParam("*/attrs", 1, 1);

      digester.addObjectCreate("*/attr", BtxHistoryAttribute.class);
      digester.addSetProperties("*/attr");

      digester.addRule("*/attr/string", new CallMethodNoTrimRule("setValue", 0));

      digester
        .addRule("*/attr", new CallMethodRule(-1, "linkToParent", 2, new Class[] { Object.class
        /* child */
        , Object.class /* parent */
      }));
      digester.addCallParam("*/attr", 0, 0);
      digester.addCallParam("*/attr", 1, 1);
    }

    // Rules for */idlist:
    {
      digester.addObjectCreate("*/idlist", IdList.class);

      digester.addRule("*/idlist/string", new CallMethodNoTrimRule("add", 0));

      digester
        .addRule("*/idlist", new CallMethodRule(-1, "linkToParent", 2, new Class[] { Object.class
        /* child */
        , Object.class /* parent */
      }));
      digester.addCallParam("*/idlist", 0, 0);
      digester.addCallParam("*/idlist", 1, 1);
    }

    // Rules for */array:
    {
      digester.addObjectCreate("*/array", ArrayList.class);

      digester.addRule("*/array/string", new CallMethodNoTrimRule("add", 0));

      digester
        .addRule(
          "*/array",
          new CallMethodRule(-1, "linkToParentAsArray", 2, new Class[] { List.class
        /* child */
        , Object.class /* parent */
      }));
      digester.addCallParam("*/array", 0, 0);
      digester.addCallParam("*/array", 1, 1);
    }

    // Rules for */list:
    {
      digester.addObjectCreate("*/list", ArrayList.class);

      digester.addRule("*/list/string", new CallMethodNoTrimRule("add", 0));

      digester
        .addRule("*/list", new CallMethodRule(-1, "linkToParent", 2, new Class[] { Object.class
        /* child */
        , Object.class /* parent */
      }));
      digester.addCallParam("*/list", 0, 0);
      digester.addCallParam("*/list", 1, 1);
    }

    // Rules for */null:
    // The Digester has some problems with passing null as parameters to method calls.  Sometimes
    // when a parameter is null it decides not to actually call the method.  The NullProxy object
    // exists to allow us to work around this problem.
    {
      digester.addObjectCreate("*/null", NullProxy.class);

      digester
        .addRule("*/null", new CallMethodRule(-1, "linkToParent", 2, new Class[] { Object.class
        /* child */
        , Object.class /* parent */
      }));
      digester.addCallParam("*/null", 0, 0);
      digester.addCallParam("*/null", 1, 1);
    }

    _digester = digester;
  }

  /**
   * Creates a new <code>BtxHistoryObjectParser</code>.
   */
  public BtxHistoryObjectParser() {
    super();
  }

  /**
   * Parses the History Object XML in the specified BtxHistoryObjectEncoding instance.
   * The parser is not thread safe.  The result of the parse is also available from the
   * {@link #getHistoryObject()} property.
   * 
   * @return the History Object.
   */
  public Object performParse(BtxHistoryObjectEncoding encodedObject) {
    // Since in displaying ICP history we may need to parse
    // many history objects, we reuse a single pre-configured Digester instance for all parses.
    // Digesters aren't thread safe, so we have to synchronize on the shared digester instance.
    // If load ever got to the point where several users were getting ICP history at once, this
    // could become a bottleneck.
    //
    // The parser as a whole is not thread safe because of its use of the _historyObject
    // class variable to store the result of the parsing.

    // An empty string always parses to null regardless of the encoding scheme.
    //
    String xmlString = encodedObject.getEncodedObject();
    if (ApiFunctions.isEmpty(xmlString)) {
      setHistoryObject(null);
      return null;
    }

    synchronized (_digester) {
      Digester digester = _digester;

      Object result = null;

      String encodingScheme = encodedObject.getEncodingScheme();

      // If we supported multiple encoding shemes, we'd need to provide support here for all of
      // the ones that we might encounter in a BTX history record that we read from the database.
      // For example, we might need to install an encoding-specific set of parsing rules into
      // the digester.  For now, though, we only support our initial encoding scheme, so we just
      // check to make sure that's what we got here and throw an exception if we get an
      // unexpected/unsupported encoding scheme.
      //
      if (!BtxHistoryObjectUtils.ENCODING_SCHEME_XML1.equals(encodingScheme)) {
        throw new ApiException("Unexpected history object encoding scheme: " + encodingScheme);
      }

      // Put ourselves on the top of the digester stack.
      digester.push(this);

      // Now that everything is set up, parse the file.
      try {
        // As a side effect of the parsing, this calls setHistoryObject, setting it to the
        // result of the parsing.
        //
        digester.parse(new StringReader(xmlString));

        result = getHistoryObject();
      }
      catch (SAXException se) {
        ApiFunctions.throwAsRuntimeException(se);
      }
      catch (IOException ie) {
        ApiFunctions.throwAsRuntimeException(ie);
      }

      return result;
    }
  }

  /**
   * Link a child object to its parent during XML parsing.  This is public since it needs to
   * be called by the Digester, but it is really only intended for internal use.
   * This only handles certain combinations of child/parent object types that are relevant
   * to what we're parsing here, and a runtime exception will be thrown for other combinations.
   * 
   * @param child the child object.
   * @param parent the parent object.
   */
  public static void linkToParent(Object child, Object parent) {
    // Recognized parent/child combinations:
    //
    // Child                    Parent
    // -----------------------------------------------------
    // BtxHistoryAttribute      BtxHistoryAttributes
    // BtxHistoryAttributes     BtxHistoryAttribute
    // BtxHistoryAttributes     BtxHistoryObjectParser
    // BtxHistoryAttributes     List
    // IdList                   BtxHistoryAttribute
    // IdList                   BtxHistoryObjectParser
    // IdList                   List
    // Array                    BtxHistoryAttribute
    // Array                    BtxHistoryObjectParser
    // Array                    List
    // List                     BtxHistoryAttribute
    // List                     BtxHistoryObjectParser
    // List                     List
    // NullProxy                BtxHistoryAttribute
    // NullProxy                BtxHistoryObjectParser
    // NullProxy                List

    if (child == null) {
      throw new NullPointerException("child cannot be null");
    }
    if (parent == null) {
      throw new NullPointerException("parent cannot be null");
    }

    // If the child is an ArrayList, trim it to its actual size now.
    //
    if (child instanceof ArrayList) {
      ((ArrayList) child).trimToSize();
    }

    if (child instanceof BtxHistoryAttribute) {
      if (parent instanceof BtxHistoryAttributes) {
        BtxHistoryAttribute attr = (BtxHistoryAttribute) child;
        ((BtxHistoryAttributes) parent).setAttribute(attr.getName(), attr.getValue());
      }
      else {
        throwBadTypeCombination(child, parent);
      }
    }
    else if (
      (child instanceof BtxHistoryAttributes)
        || (child instanceof Object[])
        || (child instanceof List)
        || (child instanceof NullProxy)
        || (child instanceof IdList)) {

      // Convert the NullProxy object to an actual null.  See the comments on the NullProxy
      // object for why we need to do this.
      //
      Object realChild = ((child instanceof NullProxy) ? null : child);

      if (parent instanceof BtxHistoryAttribute) {
        ((BtxHistoryAttribute) parent).setValue(realChild);
      }
      else if (parent instanceof List) {
        ((List) parent).add(realChild);
      }
      else if (parent instanceof BtxHistoryObjectParser) {
        ((BtxHistoryObjectParser) parent).setHistoryObject(realChild);
      }
      else {
        throwBadTypeCombination(child, parent);
      }
    }
    else {
      throwBadTypeCombination(child, parent);
    }
  }

  /**
   * Link a child object that is a List to its parent during XML parsing, converting it to
   * an array before linking it.  This is public since it needs to
   * be called by the Digester, but it is really only intended for internal use.
   * This only handles certain combinations of child/parent object types that are relevant
   * to what we're parsing here, and a runtime exception will be thrown for other combinations.
   * 
   * @param child the child list object.
   * @param parent the parent object.
   */
  public static void linkToParentAsArray(List child, Object parent) {
    if (child == null) {
      throw new NullPointerException("child cannot be null");
    }

    linkToParent(child.toArray(), parent);
  }

  private static void throwBadTypeCombination(Object child, Object parent) {
    throw new ApiException(
      "Unsupported parent/child type combination: parent is "
        + parent.getClass().getName()
        + ", child is "
        + child.getClass().getName());
  }

  /**
   * Calling {@link #performParse(BtxHistoryObjectEncoding)} sets the value of this property to
   * the result of parsing the object.  If there's an error during parsing this will return null
   * (although a return value of null doesn't not imply that there was an error -- null is a
   * valid history object).
   * 
   * @return the history object.
   */
  public Object getHistoryObject() {
    return _historyObject;
  }

  /**
   * @see #getHistoryObject()
   */
  public void setHistoryObject(Object object) {
    // Make sure that the parsed history object is valid.
    //
    BtxHistoryObjectUtils.checkValidHistoryObject(object);

    _historyObject = object;
  }

  /*
      public static void main(String[] args) {
        String xmlA =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "\n\n<!DOCTYPE historyObject PUBLIC"
            + "\n    \"-//Ardais Corporation//DTD BTX History Object Encoding XML1//EN\""
            + "\n    \"btxHistoryObjectXML1.dtd\">"
            + "\n\n<historyObject>"
            + "\n  <attrs>"
            + "\n    <attr name=\"  &quot;asdf&quot;     &amp;&lt;bc&gt;    \">"
            + "\n      <string>    &quot;a1&quot;    &amp;"
            + "\n&lt;more&gt;   </string>"
            + "\n    </attr>"
            + "\n    <attr name=\"attr1\">"
            + "\n      <attrs>"
            + "\n        <attr name=\"attr1_1\">"
            + "\n          <string>a1_1</string>"
            + "\n        </attr>"
            + "\n        <attr name=\"attr2_1\">"
            + "\n          <list>"
            + "\n            <string>b1_1</string>"
            + "\n            <string>b2_1</string>"
            + "\n            <string>b3_1</string>"
            + "\n          </list>"
            + "\n        </attr>"
            + "\n      </attrs>"
            + "\n    </attr>"
            + "\n    <attr name=\"attr2\">"
            + "\n      <list>"
            + "\n        <string>a1</string>"
            + "\n        <null/>"
            + "\n        <array length=\"4\">"
            + "\n          <list>"
            + "\n            <string>b1</string>"
            + "\n            <string>b2</string>"
            + "\n            <string>b3</string>"
            + "\n          </list>"
            + "\n          <string>c1</string>"
            + "\n          <attrs>"
            + "\n            <attr name=\"attr1_2\">"
            + "\n              <string>a1_2</string>"
            + "\n            </attr>"
            + "\n            <attr name=\"attr2_2\">"
            + "\n              <list>"
            + "\n                <string>b1_2</string>"
            + "\n                <string>b2_2</string>"
            + "\n              </list>"
            + "\n            </attr>"
            + "\n          </attrs>"
            + "\n          <idlist>"
            + "\n            <string>d1</string>"
            + "\n            <string>d2</string>"
            + "\n            <string>d3</string>"
            + "\n          </idlist>"
            + "\n        </array>"
            + "\n        <string>a3</string>"
            + "\n      </list>"
            + "\n    </attr>"
            + "\n  </attrs>"
            + "\n</historyObject>";
    
        // Take an XML string, parse it, re-encode the result as a string, then re-parse it.
        // If the parser and encoder are both working right, you should get equivalent parsed
        // objects back both times.
    
        BtxHistoryObjectEncoding encodedObject =
          new BtxHistoryObjectEncoding(BtxHistoryObjectUtils.ENCODING_SCHEME_XML1, xmlA);
    
        BtxHistoryObjectParser parser = new BtxHistoryObjectParser();
    
        Object obj = parser.performParse(encodedObject);
    
        encodedObject = BtxHistoryObjectUtils.encode(obj);
    
        Object obj2 = parser.performParse(encodedObject);
    
        System.out.println("Done.");
      }
  */
}
