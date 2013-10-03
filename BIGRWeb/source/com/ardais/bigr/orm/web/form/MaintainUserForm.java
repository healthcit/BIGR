package com.ardais.bigr.orm.web.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.javabeans.LocationData;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class MaintainUserForm extends BigrActionForm {

  //holds search criteria when looking for users, or user data when creating/modifying users
  private UserDto _userData;
  
  //a LegalValueSet of accounts accessible to the user as either search criteria or 
  //user creation input
  private LegalValueSet _accountChoices;
  
  //a LegalValueSet of titles for user creation/modification
  private LegalValueSet _titleChoices;
  
  //a List of UserDtos populated when a user search is performed
  private List _matchingUsers;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _userData = new UserDto();
    _accountChoices = null;
    resetTitleChoices();
    _matchingUsers = null;
  }
  
  private void resetTitleChoices() {
    _titleChoices = new LegalValueSet();
    _titleChoices.addLegalValue("", "Select");
    _titleChoices.addLegalValue("Mr","Mr.");
    _titleChoices.addLegalValue("Ms","Ms.");
    _titleChoices.addLegalValue("Dr","Dr.");
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
     return null; 
  }
  
  /**
   * @return
   */
  public LegalValueSet getAccountChoices() {
    return _accountChoices;
  }

  /**
   * @return
   */
  public UserDto getUserData() {
    if (_userData == null) {
      _userData = new UserDto();
      _userData.setAddress(new LocationData());
    }
    return _userData;
  }
  
  /**
   * @return
   */
  public List getMatchingUsers() {
    return _matchingUsers;
  }

  /**
   * @return
   */
  public LegalValueSet getTitleChoices() {
    return _titleChoices;
  }

  /**
   * @param list
   */
  public void setAccountChoices(LegalValueSet set) {
    _accountChoices = set;
  }

  /**
   * @param dto
   */
  public void setUserData(UserDto dto) {
    _userData = dto;
  }

  /**
   * @param list
   */
  public void setMatchingUsers(List list) {
    _matchingUsers = list;
  }

}
