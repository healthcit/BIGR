package com.ardais.bigr.userprofile;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.TemporaryClob;
import com.ardais.bigr.security.SecurityInfo;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class UserProfileTopics {

  private Map _viewProfiles = new HashMap();
  private Map _queryProfiles = new HashMap();
  private Map _simpleProfiles = new HashMap();
  
  /**
   * Constructor for UserProfileTopics.
   */
  public UserProfileTopics() {
    super();
  }

  /**
   * Returns the columnProfiles.
   * @return Map
   */
  public ViewProfile getViewProfile(String key) {
    return (ViewProfile) _viewProfiles.get(key);
  }

  /**
   * Returns the simpleProfiles.
   * @return Map
   */
  public SimpleProfile getSimpleProfile(String key) {
    return (SimpleProfile) _simpleProfiles.get(key);
  }

  /**
   * Sets the columnProfiles.
   * @param name the name to key this profile on
   * @param columnProfiles The columnProfiles to set
   */
  public void addViewProfile(String name, ViewProfile prof) {
    _viewProfiles.put(name, prof);
  }

  /**
   * Sets the queryProfile
   * @param name the name to key this profile on
   * @param queryProfile the named query to set
   */
  public void addQueryProfile(String name, QueryProfile queryProfile) {
    _queryProfiles.put(name, queryProfile);
  }

  /**
   * Sets the simpleProfiles.
   * @param name the name to key this profile on
   * @param columnProfiles The columnProfiles to set
   */
  public void addSimpleProfile(String name, SimpleProfile prof) {
    _simpleProfiles.put(name, prof);
  }

// -------------------  begin persistence -----------------------------------------

/*----------------
 * Current persistence strategy is to have this object load and persist itself to the DB,
 * delegating XML encoding of each sub-topic to that object.
 * 
 * XML de-coding (as opposed to encoding) is done using a helper object because it is hard to
 * have a bunch of classes collaborate in deserializing one XML string (unlike XML-fragment
 * generation, which is naturally recursive)
 -----------------*/


  public static UserProfileTopics load(SecurityInfo secInfo) {
    String userId = secInfo.getUsername();
    UserProfileTopics tpcs;

    Connection conn = ApiFunctions.getDbConnection();    
    try {
      String sql = "SELECT profile_topics FROM es_ardais_user WHERE ardais_user_id = ? AND profile_topics_ver = 1";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, userId);
      ResultSet rs = ps.executeQuery();
      
      if (!rs.next()) {
        tpcs = new UserProfileTopics();
      }
      else {
        Clob clob = rs.getClob(1);
        String profileXml = ApiFunctions.getStringFromClob(clob);
        tpcs = UserProfileTopicSerializer.deserializeUserProfileTopics(profileXml, secInfo);
      }
    } catch (SQLException e) {
      throw new ApiException("Could not load user profile" , e);
    } finally {
      ApiFunctions.close(conn);
    }
    return tpcs;
  }

  public void persist(SecurityInfo secInfo) {
    String xml = this.toXml();
    String sql = "UPDATE es_ardais_user SET profile_topics = ?, profile_topics_ver = 1 WHERE ardais_user_id = ? ";
    
    Connection conn = null;
    PreparedStatement ps = null;
    TemporaryClob tClob = null;
    try {
      conn = ApiFunctions.getDbConnection();
      tClob = new TemporaryClob(conn, xml);
      ps = conn.prepareStatement(sql);
      ps.setClob(1, tClob.getSQLClob());
      ps.setString(2, secInfo.getUsername());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new ApiException("Could not save user profile" , e);
    } finally {
      ApiFunctions.close(tClob, conn);
      ApiFunctions.close(conn, ps, null);
    }
  }
// -------------------  end persistence -----------------------------------------

  public String toXml() {
    StringBuffer sb = new StringBuffer(256);
    sb.append("<topics>");
    
    Iterator it = _viewProfiles.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry e = (Map.Entry) it.next();
      String key = (String) e.getKey();
      ViewProfile prof = (ViewProfile) e.getValue();
      sb.append(prof.toXml(key));
    }
    
    it = _queryProfiles.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry e = (Map.Entry) it.next();
      String key = (String) e.getKey();
      QueryProfile prof = (QueryProfile) e.getValue();
      sb.append(prof.toXml(key));
    }

    it = _simpleProfiles.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry e = (Map.Entry) it.next();
      String key = (String) e.getKey();
      SimpleProfile prof = (SimpleProfile) e.getValue();
      sb.append(prof.toXml(key));
    }

    sb.append("</topics>");
    return sb.toString();
  } 
}
