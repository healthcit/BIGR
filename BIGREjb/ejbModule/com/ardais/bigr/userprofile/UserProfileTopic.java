package com.ardais.bigr.userprofile;

/**
 * @author dfeldman
 *
 * This is the superclass of a piece of configuration information for a user.
 */
public interface UserProfileTopic { 
    
//  public abstract void addToProfile(String name, UserProfileTopic topic) ;
  
  public void addToUserProfileTopics(String name, UserProfileTopics topics) ;
  
  public abstract String toXml(String name) ;
  
}
