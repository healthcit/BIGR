package com.ardais.bigr.userprofile;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.beanutils.PropertyUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterManager;
import com.ardais.bigr.security.SecurityInfo;

/**
 * @author dfeldman
 *
 * This class supports writing UserTopics (parts of a user profile) to and from XML.  It has
 * generic static methods to support writing XML tags and attributes.  It uses Digester to read
 * in XML, and has Digester rules for all the types of profile objects.  It also serves as the
 * callback handler for the Digester (it has a single callback that adds elements to a 
 * UserProfileTopics instance).
 * 
 * Note on XML de-serialization:  This class handles SAX events, such as tags with attributes 
 * encountered in the input stream.  It delegates much of the construction of Profile
 * objects to objects themselves or ClassManager objects.  Generally, this handles all
 * the SAX Events, but when there's an encoded string as an attribute or tag body that
 * encoded string is passed to other objects, which are non-xml-aware.
 */
public class UserProfileTopicSerializer {

  //  private final static String _listTag = "items";

  // tags identifying different profile subclasses  
  private static final String _columnConfigTag = "columnConfig";

  /**
   * Constructor for SerDes.
   */
  public UserProfileTopicSerializer() {
  }

  // ----------    XML Writing Helper Methods ------------------------------
  private static void append(String text, StringBuffer sb) {
    sb.append(text);
  }

  public static void appendStartTag(String tagName, StringBuffer sb) {
    sb.append('<');
    sb.append(tagName);
    sb.append('>');
  }

  public static void appendTagWithAttrs(
    String tagName,
    String[] attrNames,
    Object bean,
    StringBuffer sb) {
    sb.append('<');
    sb.append(tagName);
    for (int i = 0; i < attrNames.length; i++) {
      String val = "";
      try {
        val = (String) PropertyUtils.getSimpleProperty(bean, attrNames[i]);
      }
      catch (NoSuchMethodException e) {
        throw new ApiException("no method for attribute", e);
      }
      catch (InvocationTargetException e) {
        throw new ApiException(e);
      }
      catch (IllegalAccessException e) {
        throw new ApiException("attribute is not public", e);
      }
      sb.append(' '); // <space>attr="val" 
      sb.append(attrNames[i]);
      sb.append("=\"");
      sb.append(val);
      sb.append('"');
    }
    sb.append("/>");
  }

  public static void appendStartTagWithAttribute(
    String tagName,
    String attrName,
    String attrVal,
    StringBuffer sb) {
    sb.append('<');
    sb.append(tagName);
    sb.append(' ');
    appendAttribute(attrName, attrVal, sb);
    sb.append('>');
  }

  public static void appendStartTagWithAttrs(String tagName, Map attrs, StringBuffer sb) {
    sb.append('<');
    sb.append(tagName);
    Iterator it = attrs.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry e = (Map.Entry) it.next();
      String attr = (String) e.getKey();
      String val = (String) e.getValue();
      sb.append(' '); // <space>attr="val" 
      sb.append(attr);
      sb.append("=\"");
      sb.append(val);
      sb.append('"');
    }
    sb.append('>');
  }

  public static void appendEndTag(String tagName, StringBuffer sb) {
    sb.append('<');
    sb.append('/');
    sb.append(tagName);
    sb.append('>');
  }

  public static void appendAttribute(String name, String val, StringBuffer sb) {
    sb.append(name);
    sb.append('=');
    sb.append('"');
    sb.append(val);
    sb.append('"');
  }

  // --------------------- end helper methods ----------------------

  private static class UserProfSaxHandler extends DefaultHandler {

    private UserProfileTopics _userProfTopics = new UserProfileTopics();
    private SecurityInfo _secInfo;
    private QueryProfile _currentQueryProfile;
    private String _queryProfileName;

    public UserProfSaxHandler(SecurityInfo secInfo) {
      _secInfo = secInfo;
    }

    public UserProfileTopics getUserProfileTopics() {
      return _userProfTopics;
    }

    public void startElement(String namespaceURI, String sName, // simple name
    String qName, // qualified name
    Attributes attrs) throws SAXException {
      if (ViewProfile.XML_TOPIC_NAME.equals(qName)) {
        ViewProfile vp = new ViewProfile(_secInfo);
        vp.setResultsFormDefinitionId(attrs.getValue(ViewProfile.XML_FORM_DEF_ID_NAME));
        String name = ViewParams.getCurrentKeyFormat(attrs.getValue(ViewProfile.XML_NAME_NAME));
        _userProfTopics.addViewProfile(name, vp);
      }
      else if (QueryProfile.XML_TOPIC_NAME.equals(qName)) { // init QueryProfile
        _currentQueryProfile = new QueryProfile();
        _queryProfileName = attrs.getValue("name");
      }
      else if (Filter.XML_TAG_NAME.equals(qName)) { // tack new filter onto QueryProfile
        String vals = attrs.getValue("values");
        String key = attrs.getValue("key");
        Filter f = FilterManager.instance().getInstance(key, vals, _secInfo);
        _currentQueryProfile.addFilter(f);
      }
      else if (
        SimpleProfile.XML_TOPIC_NAME.equals(qName)) { // add SimpleProfile to UserTopics in 1 step
        String key = attrs.getValue(SimpleProfile.SIMPLE_PROFILE_KEY);
        String value = attrs.getValue(SimpleProfile.SIMPLE_PROFILE_VALUE);
        SimpleProfile sp = new SimpleProfile();
        sp.setKey(key);
        sp.setValue(value);
        _userProfTopics.addSimpleProfile(key, sp);
      }
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(String, String, String)
     */
    public void endElement(String uri, String sName, String qName) throws SAXException {
      if (QueryProfile.XML_TOPIC_NAME.equals(qName)) { // QueryProf done, add to UserProfTopics
        _currentQueryProfile.addToUserProfileTopics(_queryProfileName, _userProfTopics);
      }
    }

  }

  private static UserProfileTopics saxparse(String s, SecurityInfo secInfo) {
    UserProfSaxHandler handler = new UserProfSaxHandler(secInfo);

    // Use the default (non-validating) parser
    SAXParserFactory factory = SAXParserFactory.newInstance();
    try {
      if (!ApiFunctions.isEmpty(s)) {
        // Parse the input 
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(new ByteArrayInputStream(s.getBytes()), handler);
      }
    }
    catch (Exception e) {
      throw new ApiException("for xml:" + s, e);
    }
    return handler.getUserProfileTopics();
  }

  public static UserProfileTopics deserializeUserProfileTopics(String s, SecurityInfo secInfo) {
    return saxparse(s, secInfo);
  }

}
