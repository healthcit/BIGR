package com.ardais.bigr.btx.framework;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.javabeans.IdList;

/**
 * This class provides static utility methods related to processing BTX History Objects.
 * 
 * <p>A BTX History Object is any of a constrained set of object types that can be stored in
 * the historyObject property of a BTX history record.  Because of the complexities of reading
 * and writing objects in general to the database tables that store BTX history records, we
 * limit the kinds of objects that are valid BTX History Objects (see
 * {@link #isValidHistoryObject(Object)} for details).  The valid BTX History Object types
 * include a set of generic types such as certain lists, arrays, and mappings from attribute names
 * to attribute values.  When creating a history record including a History Object, you need to
 * map the data in your own objects into appropriate data structures build out of these generic
 * BTX History Object types.
 * 
 * <p>A particularly useful kind of BTX History Object is {@link BtxHistoryAttributes}.  It allows
 * you to store a collection of attribute/value associations, where there values can be any
 * valid BTX History Object, including other BtxHistoryAttributes instances.  It has several
 * constructors that make it easy to create a BtxHistoryAttributes object from selected
 * properties of an arbitrary Java bean.  See the class description for further details.
 */
public class BtxHistoryObjectUtils {
  /**
   * Version 1 of our scheme for encoding a History Object as an XML string.
   */
  public static final String ENCODING_SCHEME_XML1 = "XML1";

  private static final String XML_DOCUMENT_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

  private static final String DOCTYPE_ENCODING_XML1 =
    "<!DOCTYPE historyObject PUBLIC"
      + "\n    \"-//Ardais Corporation//DTD BTX History Object Encoding XML1//EN\""
      + "\n    \"btxHistoryObjectXML1.dtd\">";

  /**
   * This class has static methods only, so make the constructor private to prevent instantiation.
   */
  private BtxHistoryObjectUtils() {
    super();
  }

  /**
   * Encode a history object as a string.  This makes a special case of null,
   * and instead of returning a complex string (e.g. an XML document) it returns null in
   * BtxHistoryObjectEncoding.getEncodedObject.  This is done so that in the common case where
   * we're writing a BTX history record and there's nothing in the history object field, we'll
   * write a null to the database column instead of a potentially complex string that encodes
   * nothing more than null.
   * 
   * <p>We currently don't support encoding a history object if its object graph contains cycles
   * (for example, a list that contains itself at any level of nesting is invalid).  If the
   * object being encoded has any cycles a runtime exception will be thrown.
   * 
   * @param historyObject the object to encode.
   * @return the encoded object.
   */
  public static BtxHistoryObjectEncoding encode(Object historyObject) {
    // checkValidHistoryObject isn't smart about cycles (it would recurse infinitely), so we
    // have to check for cycles first.

    checkAcyclicHistoryObject(historyObject);
    
    checkValidHistoryObject(historyObject);

    if (historyObject == null) {
      return BtxHistoryObjectEncoding.NULL_ENCODING;
    }

    return encodeAsXml1(historyObject);
  }

  /**
   * Check that the specified object is a valid BTX History Object, and if it is not throw a
   * runtime exception.
   * 
   * @param historyObject the object.
   */
  public static void checkValidHistoryObject(Object object) {
    if (!isValidHistoryObject(object)) {
      // null was a valid history object when this was written, but for robustness we handle
      // the possibility that it won't be someday.
      //
      if (object == null) {
        throw new ApiException("Invalid BTX History Object: null");
      }
      else {
        throw new ApiException(
          "Invalid BTX History Object.  Object type is " + object.getClass().getName());
      }
    }
  }

  /**
   * Return true if the specified object is a valid BTX History Object.  Valid objects types are:
   * <ul>
   * <li>null</li>
   * <li>String</li>
   * <li>{@link BtxHistoryAttributes}</li>
   * <li>{@link IdList}</li>
   * <li>A List or array all of whose elements are valid BTX History Objects</li>
   * </ul>
   * 
   * <p>Although a valid BTX History Object cannot have cycles in its object graph, we don't check
   * that here.  Instead we check it in places where cycles would cause a problem, such as when
   * serializing the object to an XML string.
   *   
   * @param historyObject the object.
   * @return true if the object is a valid history object as described above.
   */
  public static boolean isValidHistoryObject(Object historyObject) {
    return (
      (historyObject == null)
        || (historyObject instanceof String)
        || (historyObject instanceof BtxHistoryAttributes)
        || (historyObject instanceof IdList)
        || isValidHistoryObjectList(historyObject)
        || isValidHistoryObjectArray(historyObject));
  }

  /**
   * @param object
   */
  private static boolean isValidHistoryObjectArray(Object object) {
    if (object == null) {
      return true;
    }

    if (!(object instanceof Object[])) {
      return false;
    }

    Object[] objectArray = (Object[]) object;

    for (int i = 0; i < objectArray.length; i++) {
      if (!isValidHistoryObject(objectArray[i])) {
        return false;
      }
    }

    return true;
  }

  /**
   * @param object
   */
  private static boolean isValidHistoryObjectList(Object object) {
    if (object == null) {
      return true;
    }

    if (!(object instanceof List)) {
      return false;
    }

    Iterator iter = ((List) object).iterator();
    while (iter.hasNext()) {
      if (!isValidHistoryObject(iter.next())) {
        return false;
      }
    }

    return true;
  }

  /**
   * Check that the specified BTX History Object contains no cycles, and if does throw a
   * runtime exception.  This assumes that the object passed in is a valid BTX History Object
   * (this does not detect cycles in arbitrary Java object graphs, only in BTX History Objects).
   * 
   * @param historyObject the object.
   */
  public static void checkAcyclicHistoryObject(Object historyObject) {
    if (!isAcyclicHistoryObject(historyObject)) {
      throw new ApiException(
        "The BTX History Object contains a cycle.  Object type is "
          + historyObject.getClass().getName());
    }
  }

  /**
   * @param historyObject the object.
   */
  private static boolean isAcyclicHistoryObject(Object historyObject) {
    return isAcyclicHistoryObject(historyObject, new HashSet());
  }

  /**
   * @param historyObject the object.
   */
  private static boolean isAcyclicHistoryObject(Object historyObject, Set parentStack) {
    if (historyObject == null) {
      return true;
    }
    else if (historyObject instanceof String) {
      return true;
    }
    else if (historyObject instanceof BtxHistoryAttributes) {
      return isAcyclicHistoryAttributes((BtxHistoryAttributes) historyObject, parentStack);
    }
    else if (historyObject instanceof IdList) {
      // An IdList can only contain strings as elements so it can't have a cycle.
      return true;
    }
    else if (historyObject instanceof List) {
      return isAcyclicHistoryObjectList((List) historyObject, parentStack);
    }
    else if (historyObject instanceof Object[]) {
      return isAcyclicHistoryObjectArray((Object[]) historyObject, parentStack);
    }
    else {
      throw new ApiException(
        "Unexpected history object class: " + historyObject.getClass().getName());
    }
  }

  /**
   * @param historyAttrs
   */
  private static boolean isAcyclicHistoryAttributes(
    BtxHistoryAttributes historyAttrs,
    Set parentStack) {
      
    if (historyAttrs == null) {
      return true;
    }

    parentStack.add(historyAttrs);

    try {
      Iterator iter = historyAttrs.asMap().values().iterator();
      while (iter.hasNext()) {
        Object obj = iter.next();
        if (parentStack.contains(obj)) {
          return false;
        }
        if (!isAcyclicHistoryObject(obj, parentStack)) {
          return false;
        }
      }
    }
    finally {
      parentStack.remove(historyAttrs);
    }

    return true;
  }

  /**
   * @param objectArray
   */
  private static boolean isAcyclicHistoryObjectArray(Object[] objectArray, Set parentStack) {
    if (objectArray == null) {
      return true;
    }

    parentStack.add(objectArray);

    try {
      for (int i = 0; i < objectArray.length; i++) {
        Object obj = objectArray[i];
        if (parentStack.contains(obj)) {
          return false;
        }
        if (!isAcyclicHistoryObject(obj, parentStack)) {
          return false;
        }
      }
    }
    finally {
      parentStack.remove(objectArray);
    }

    return true;
  }

  /**
   * @param historyObjectList
   */
  private static boolean isAcyclicHistoryObjectList(List historyObjectList, Set parentStack) {
    if (historyObjectList == null) {
      return true;
    }

    parentStack.add(historyObjectList);

    try {
      Iterator iter = historyObjectList.iterator();
      while (iter.hasNext()) {
        Object obj = iter.next();
        if (parentStack.contains(obj)) {
          return false;
        }
        if (!isAcyclicHistoryObject(obj, parentStack)) {
          return false;
        }
      }
    }
    finally {
      parentStack.remove(historyObjectList);
    }

    return true;
  }

  /**
   * @param historyObject
   * @return
   */
  private static BtxHistoryObjectEncoding encodeAsXml1(Object historyObject) {
    return new BtxHistoryObjectEncoding(ENCODING_SCHEME_XML1, encodeAsXml1Document(historyObject));
  }

  /**
   * @param historyObject
   */
  private static String encodeAsXml1Document(Object historyObject) {
    StringBuffer sb = new StringBuffer(1024);

    sb.append(XML_DOCUMENT_HEADER);
    sb.append('\n');
    sb.append(DOCTYPE_ENCODING_XML1);
    sb.append("\n<historyObject>");

    encodeAsXml1Element(historyObject, sb);

    sb.append("\n</historyObject>\n");

    return sb.toString();
  }

  /**
   * 
   * @param historyObject
   * @param sb
   */
  private static void encodeAsXml1Element(Object historyObject, StringBuffer sb) {
    sb.append('\n');

    if (historyObject == null) {
      sb.append("<null/>");
    }
    else if (historyObject instanceof String) {
      // IMPORTANT: Don't add any whitespace between the end of the start tag and the beginning
      // of the string, or between then end of the string and the beginning of the end tag.
      // When the string is parsed, it is considered to be ALL of the characters between the
      // start and end tags, with any leading and trailing whitespace intact.  If we didn't
      // do this, we wouldn't know whether leading/trailing whitespace was really supposed
      // to be part of the string or whether it was introduced as part of formatting the XML.

      sb.append("<string>");
      Escaper.xmlEscape((String) historyObject, sb);
      // DO NOT put a newline before the ending </string> tag.  See the comment above.
      sb.append("</string>");
    }
    else if (historyObject instanceof BtxHistoryAttributes) {
      Set entries = ((BtxHistoryAttributes) historyObject).asMap().entrySet();

      sb.append("<attrs>");

      Iterator iter = entries.iterator();
      while (iter.hasNext()) {
        Map.Entry entry = (Map.Entry) iter.next();
        String attrName = (String) entry.getKey();
        Object attrValue = entry.getValue();

        sb.append("\n<attr name=\"");
        Escaper.xmlEscape(attrName, sb);
        sb.append("\">");
        encodeAsXml1Element(attrValue, sb);
        sb.append("\n</attr>");
      }

      sb.append("\n</attrs>");
    }
    else if (historyObject instanceof Object[]) {
      Object[] a = (Object[]) historyObject;

      sb.append("<array length=\"");
      sb.append(a.length);
      sb.append("\">");

      for (int i = 0; i < a.length; i++) {
        encodeAsXml1Element(a[i], sb);
      }

      sb.append("\n</array>");
    }
    else if ((historyObject instanceof List) || (historyObject instanceof IdList)) {
      boolean isIdList = (historyObject instanceof IdList);
      List list = (isIdList ? ((IdList) historyObject).getList() : (List) historyObject);

      sb.append(isIdList ? "<idlist>" : "<list>");

      Iterator iter = list.iterator();
      while (iter.hasNext()) {
        encodeAsXml1Element(iter.next(), sb);
      }

      sb.append(isIdList ? "\n</idlist>" : "\n</list>");
    }
    else {
      throw new ApiException("Unexpected object type: " + historyObject.getClass().getName());
    }
  }

}
