package com.ardais.bigr.btx.framework;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.javabeans.IdList;

/**
 * BtxHistoryAttributes is one of the classes that can play the role of a BTX History Object
 * (see {@link BtxHistoryObjectUtils}).  It represents a bundle of attributes and their values.
 * Attribute names can be any non-null string, and attribute values can be any object for which
 * {@link BtxHistoryObjectUtils#isValidHistoryObject(Object)} is true.
 */
public class BtxHistoryAttributes implements Serializable {
  private static final long serialVersionUID = 279006233438158678L;

  private HashMap _attributes = new HashMap();

  /**
   * Create a BtxHistoryAttributes object containing no attributes.
   */
  public BtxHistoryAttributes() {
    super();
  }

  /**
   * Create a BtxHistoryAttributes object with attributes and values gathered from properties
   * of the specified bean.  The attributes/values
   * are those that would be placed into a Map by
   * {@link org.apache.commons.beanutils.PropertyUtilsBean#describe(Object)
   *   BigrBeanUtilsBean.SINGLETON.getPropertyUtils().describe(bean)}.
   * Note that it is possible that some of the bean property values may be objects that are not
   * valid BTX History Object types.  If this happens, the constructor will throw a runtime
   * exception.
   */
  public BtxHistoryAttributes(Object bean) {
    this(bean, false);
  }

  /**
   * Create a BtxHistoryAttributes object with attributes and values gathered from properties
   * of the specified bean.  The propertyNames array specifies the names of the bean properties
   * to copy into the BtxHistoryAttributes object.  The bean properties are retrieved from the
   * bean using
   * {@link org.apache.commons.beanutils.PropertyUtilsBean#getProperty(Object, String)
   *   BigrBeanUtilsBean.SINGLETON.getPropertyUtils().getProperty(bean, propertyName)}.
   * Note that it is possible that some of the bean property values may be objects that are not
   * valid BTX History Object types.  If this happens, the constructor will throw a runtime
   * exception.
   * 
   * <p>Because we use the BeanUtils getProperty function to retrieve the bean properties,
   * the property names can be any names that getProperty recognizes, such as indexed, mapped,
   * or nested property names.  Likewise, the bean may be any kind of object that getProperty
   * recognizes, including a DynaBean.
   */
  public BtxHistoryAttributes(Object bean, String[] propertyNames) {
    this(bean, propertyNames, false);
  }

  /**
   * Create a BtxHistoryAttributes object with attributes and values gathered from properties
   * of the specified bean.  If convertProperties is true, then the attributes/values
   * are those that would be placed into a Map by
   * {@link org.apache.commons.beanutils.BeanUtilsBean#describe(Object)
   *   BigrBeanUtilsBean.SINGLETON.describe(bean))}.
   * If convertProperties is false, then the attributes/values
   * are those that would be placed into a Map by
   * {@link org.apache.commons.beanutils.PropertyUtilsBean#describe(Object)
   *   BigrBeanUtilsBean.SINGLETON.getPropertyUtils().describe(bean)}.
   * The difference is that when convertProperties is true, property values are converted to
   * strings, and when it is false they are used as-is.  Note that when convertProperties is
   * false it is possible that some of the bean property values may be objects that are not
   * valid BTX History Object types.  If this happens, the constructor will throw a runtime
   * exception.
   * 
   * <p>When convertProperties is true, you many get unexpected behavior if the value of a bean
   * property is an object without a useful toString method.  The <code>describe</code> method
   * will convert these objects to strings by simply calling toString, which for many object types
   * just returns a string containing the object's class name and an instance id.  You won't get
   * any exception, for example, about encountering object types that aren't valid BTX History
   * Object types because all objects have a toString method and hence can all technically be
   * converted to a string.  Because of this, it is best to use convertProperties = false when
   * possible.
   */
  public BtxHistoryAttributes(Object bean, boolean convertProperties) {
    this();

    if (bean == null) {
      return;
    }

    Map map = null;
    try {
      if (convertProperties) {
        map = BigrBeanUtilsBean.SINGLETON.describe(bean);
      }
      else {
        map = BigrBeanUtilsBean.SINGLETON.getPropertyUtils().describe(bean);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    // We populate our _attribute map using setAttribute rather than just copying the BeanUtils
    // map because setProperties does extra validation on the attribute name and attribute value.
    //
    Iterator entries = map.entrySet().iterator();
    while (entries.hasNext()) {
      Map.Entry entry = (Map.Entry) entries.next();
      //ignore the "class" attribute
      if (!"class".equalsIgnoreCase((String)entry.getKey())) {
        setAttribute((String) entry.getKey(), entry.getValue());
      }
    }

  }

  /**
   * Create a BtxHistoryAttributes object with attributes and values gathered from properties
   * of the specified bean.  The propertyNames array specifies the names of the bean properties
   * to copy into the BtxHistoryAttributes object.  If convertProperties is true, then the
   * bean properties are retrieved from the bean using
   * {@link org.apache.commons.beanutils.BeanUtilsBean#getProperty(Object, String)
   *   BigrBeanUtilsBean.SINGLETON.getProperty(bean, propertyName))}.
   * If convertProperties is true, then the bean properties are retrieved from the bean using
   * {@link org.apache.commons.beanutils.PropertyUtilsBean#getProperty(Object, String)
   *   BigrBeanUtilsBean.SINGLETON.getPropertyUtils().getProperty(bean, propertyName)}.
   * The difference is that when convertProperties is true, property values are converted to
   * strings, and when it is false they are used as-is.  Note that when convertProperties is
   * false it is possible that some of the bean property values may be objects that are not
   * valid BTX History Object types.  If this happens, the constructor will throw a runtime
   * exception.
   * 
   * <p>Because we use the BeanUtils getProperty function to retrieve the bean properties,
   * the property names can be any names that getProperty recognizes, such as indexed, mapped,
   * or nested property names.  Likewise, the bean may be any kind of object that getProperty
   * recognizes, including a DynaBean.
   * 
   * <p>When convertProperties is true, you many get unexpected behavior if the value of a bean
   * property is an object without a useful toString method.  The <code>describe</code> method
   * will convert these objects to strings by simply calling toString, which for many object types
   * just returns a string containing the object's class name and an instance id.  You won't get
   * any exception, for example, about encountering object types that aren't valid BTX History
   * Object types because all objects have a toString method and hence can all technically be
   * converted to a string.  Because of this, it is best to use convertProperties = false when
   * possible.
   */
  public BtxHistoryAttributes(Object bean, String[] propertyNames, boolean convertProperties) {
    this();

    if (bean == null) {
      return;
    }

    for (int i = 0; i < propertyNames.length; i++) {
      String attrName = propertyNames[i];
      Object attrValue;
      try {
        if (convertProperties) {
          attrValue = BigrBeanUtilsBean.SINGLETON.getProperty(bean, attrName);
        }
        else {
          attrValue = BigrBeanUtilsBean.SINGLETON.getPropertyUtils().getProperty(bean, attrName);
        }
      }
      catch (Exception e) {
        // BeanUtils exceptions involving failure to retrieve properties often don't say which
        // property it was, so we add some extra informations here.
        //
        throw new ApiException("Error getting bean property \"" + attrName + "\"", e);
      }
      setAttribute(attrName, attrValue);
    }
  }

  /**
   * Returns an unmodifiable view of the history attributes as a Map.
   * Query operations on the returned map "read through"
   * to the BtxHistoryAttributes object, and attempts to modify the returned
   * map, whether direct or via its collection views, result in an
   * <tt>UnsupportedOperationException</tt>.
   * 
   * @return an unmodifiable Map view of the history attributes.
   */
  public Map asMap() {
    return Collections.unmodifiableMap(_attributes);
  }

  /**
   * Define the value of the named attribute to be the specified object, replacing the
   * attribute's previous value if it had one.  A runtime exception is thrown if either
   * attributeName is null or attributeValue is not a valid BTX History Object as determined
   * by {@link BtxHistoryObjectUtils#isValidHistoryObject(Object)}.
   * 
   * <p>The version of setAttribute that takes an Object as the attribute value isn't made
   * public.  There are several public versions, each of which declares its attributeValue
   * parameter to be a more constrained type (such as String, IdList, and so forth).  We do this
   * to get better compile-time error checking.  Even with these more constrained versions, it
   * is possible that some type errors won't be detected until runtime.  For example, when the
   * value is a list, we can't check that all of the elements are valid History Object
   * types until runtime.  This method has package visibility rather than private since we call
   * it from {@link BtxHistoryObjectParser#linkToParent(Object, Object)}.  
   * 
   * @param attributeName the attribute name.
   * @param attributeValue the attribute value.
   */
  void setAttribute(String attributeName, Object attributeValue) {
    // See the Javadoc comment above for why this isn't public.

    checkValidAttributeName(attributeName);
    BtxHistoryObjectUtils.checkValidHistoryObject(attributeValue);

    _attributes.put(attributeName, attributeValue);
  }

  /**
   * Define the value of the named attribute to be the specified object, replacing the
   * attribute's previous value if it had one.  A runtime exception is thrown if either
   * attributeName is null or attributeValue is not a valid BTX History Object as determined
   * by {@link BtxHistoryObjectUtils#isValidHistoryObject(Object)}.
   * 
   * @param attributeName the attribute name.
   * @param attributeValue the attribute value.
   */
  public void setAttribute(String attributeName, String attributeValue) {
    // See the Javadoc comment on the version of setAttribute that declared attributeValue
    // as an object for an explanation of why that version is private and we have overloaded
    // public versions that declared attributeValue to be a more specific type.  Basically the
    // reason is that we want to catch as many invalid-type errors at compile time as possible.

    setAttribute(attributeName, (Object) attributeValue);
  }

  /**
   * Define the value of the named attribute to be the specified object, replacing the
   * attribute's previous value if it had one.  A runtime exception is thrown if either
   * attributeName is null or attributeValue is not a valid BTX History Object as determined
   * by {@link BtxHistoryObjectUtils#isValidHistoryObject(Object)}.
   * 
   * @param attributeName the attribute name.
   * @param attributeValue the attribute value.
   */
  public void setAttribute(String attributeName, IdList attributeValue) {
    // See the Javadoc comment on the version of setAttribute that declared attributeValue
    // as an object for an explanation of why that version is private and we have overloaded
    // public versions that declared attributeValue to be a more specific type.  Basically the
    // reason is that we want to catch as many invalid-type errors at compile time as possible.

    setAttribute(attributeName, (Object) attributeValue);
  }

  /**
   * Define the value of the named attribute to be the specified object, replacing the
   * attribute's previous value if it had one.  A runtime exception is thrown if either
   * attributeName is null or attributeValue is not a valid BTX History Object as determined
   * by {@link BtxHistoryObjectUtils#isValidHistoryObject(Object)}.
   * 
   * @param attributeName the attribute name.
   * @param attributeValue the attribute value.
   */
  public void setAttribute(String attributeName, BtxHistoryAttributes attributeValue) {
    // See the Javadoc comment on the version of setAttribute that declared attributeValue
    // as an object for an explanation of why that version is private and we have overloaded
    // public versions that declared attributeValue to be a more specific type.  Basically the
    // reason is that we want to catch as many invalid-type errors at compile time as possible.

    setAttribute(attributeName, (Object) attributeValue);
  }

  /**
   * Define the value of the named attribute to be the specified object, replacing the
   * attribute's previous value if it had one.  A runtime exception is thrown if either
   * attributeName is null or attributeValue is not a valid BTX History Object as determined
   * by {@link BtxHistoryObjectUtils#isValidHistoryObject(Object)}.
   * 
   * @param attributeName the attribute name.
   * @param attributeValue the attribute value.
   */
  public void setAttribute(String attributeName, List attributeValue) {
    // See the Javadoc comment on the version of setAttribute that declared attributeValue
    // as an object for an explanation of why that version is private and we have overloaded
    // public versions that declared attributeValue to be a more specific type.  Basically the
    // reason is that we want to catch as many invalid-type errors at compile time as possible.

    setAttribute(attributeName, (Object) attributeValue);
  }

  /**
   * Returns the value associated with the specified attribute name.  Returns
   * <tt>null</tt> if no value is associated with the attribute.  A return
   * value of <tt>null</tt> does not <i>necessarily</i> indicate that the
   * attribute is undefined; it's also possible that the attribute's value is explicitly
   * defined to be <tt>null</tt>.  The <tt>containsAttribute</tt>
   * operation may be used to distinguish these two cases.
   *
   * @param attributeName the attribute name whose associated value is to be returned.
   * @return the value associated with the named attribute, or
   *         <tt>null</tt> if no value is associated with the attribute.
   * 
   * @see #containsAttribute(String)
   */
  public Object getAttribute(String attributeName) {
    return _attributes.get(attributeName);
  }

  /**
   * Return true if the named attribute explicitly has a value associated with it.
   * 
   * @param attributeName the attribute name.
   */
  public boolean containsAttribute(String attributeName) {
    return _attributes.containsKey(attributeName);
  }

  /**
   * Throw a runtime exception if the specified string is not a valid attribute name.
   * 
   * @param s the string.
   */
  private void checkValidAttributeName(String s) {
    if (s == null) {
      throw new NullPointerException("Attribute name must not be null.");
    }
  }

  public String toString() {
    return _attributes.toString();
  }

/*
  public static void main(String[] args) {
    // Test out the various constructors for creating BtxHistoryAttributes objects from beans.

    String[] properties = new String[] { "manifestId", "trackingNumber",
      //"logged",
      "box.boxNote", "box.drawer",
      //"box.contents",
      "box.cellContent[1]" };

    BoxDto box =
      new BoxDto("BX1", "3,7,CA01807C,CA01805C#1: PA0000460C, 2: PA00004D20, 4: PA00004D56");
    box.setBoxNote("\n  This is my\nnote.\n");
    box.setDrawer("23");

    BtxDetailsScanAndStore details = new BtxDetailsScanAndStore();
    details.setManifestId("MNFT1");
    details.setTrackingNumber("TRK1");
    details.setBox(box);

    BtxHistoryAttributes attrs1 = new BtxHistoryAttributes(details, properties, true);

    BtxHistoryAttributes attrs2 = new BtxHistoryAttributes(details, properties, false);

    System.out.println("Done.");
  }
*/
}
