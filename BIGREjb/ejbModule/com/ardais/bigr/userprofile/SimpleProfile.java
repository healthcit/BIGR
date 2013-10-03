package com.ardais.bigr.userprofile;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SimpleProfile implements UserProfileTopic, Serializable {

  /// ===================================================

  public static final String SIMPLE_PROFILE_KEY = "key";
  public static final String SIMPLE_PROFILE_VALUE = "value";

  /*pkg private*/
  static final String XML_TOPIC_NAME = "simpleProfile";
  
  private String _key = null;
  private String _value = null;
  private SimpleProfile _defaultSimpleProfile = null;


  /**
   * Constructor for ProfileTopic.
   */
  public SimpleProfile() {
  }

  /**
   * @return
   */
  public SimpleProfile getDefaultSimpleProfile() {
    return _defaultSimpleProfile;
  }

  /**
   * @return
   */
  public String getKey() {
    return _key;
  }

  /**
   * @return
   */
  public String getValue() {
    return _value;
  }

  /**
   * @param profile
   */
  public void setDefaultSimpleProfile(SimpleProfile profile) {
    _defaultSimpleProfile = profile;
  }

  /**
   * @param string
   */
  public void setKey(String string) {
    _key = string;
  }

  /**
   * @param string
   */
  public void setValue(String string) {
    _value = string;
  }

  /**
   * @see com.ardais.bigr.userprofile.UserProfileTopic#addToUserProfileTopics(java.lang.String, com.ardais.bigr.userprofile.UserProfileTopics)
   */
  public void addToUserProfileTopics(String name, UserProfileTopics topics) {
    topics.addSimpleProfile(name, this);
  }

  /**
   * @see com.ardais.bigr.persist.SerDes#serialize(Object)
   * 
   * writes XML of the form:
   * &lt;columnConfig name="nnnn" include="colKey1,colkey2,thirdcolkey,fourth"&gt;&lt;/columnConfig&gt;
   */
  public String toXml(String name) {
    StringBuffer result = new StringBuffer(512);
    Map attrs = new HashMap();
    attrs.put(SIMPLE_PROFILE_KEY, name); //getName());
    attrs.put(SIMPLE_PROFILE_VALUE, getValue());

    UserProfileTopicSerializer.appendStartTagWithAttrs(XML_TOPIC_NAME, attrs, result);
    UserProfileTopicSerializer.appendEndTag(XML_TOPIC_NAME, result); // empty body
    result.append('\n');

    return result.toString();
  }

  /**
   * Method isDefault.
   * @return boolean
   */
  public boolean isDefaultProfile() {
    return this.equals(_defaultSimpleProfile);
  }

  public boolean equals(Object o) {
    if (!(o instanceof SimpleProfile))
      return false;
    SimpleProfile sp = (SimpleProfile) o;
    return _key.equals(sp._key);
    // HIDDEN stuff is for Config support, not equality     
  }
}
