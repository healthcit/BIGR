/*
 * Created on Nov 5, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.orm.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;


/**
 * @author sthomashow
 *
 */
public class DisplayBannerForm extends BigrActionForm {
  private String _imageLogo;
  private UserDto _userDto;


  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
      _imageLogo = null;
      _userDto = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
     return null; 
  }
  
  public String getUserDisplayName() {
    String userDisplayName = _userDto.getUserId() + " (" + ApiFunctions.safeString(_userDto.getFirstName()) + " " + ApiFunctions.safeString(_userDto.getLastName()) + ")";
    return userDisplayName;
  }

  /**
   * @return the image logo.
   */
  public String getImageLogo() {
    return _imageLogo;
  }

  /**
   * Set the image logo.
   * 
   * @param imageLogo the image logo to display.  This is an output parameter.
   */
  public void setImageLogo(String imageLogo) {
    _imageLogo = imageLogo;
  }  
  
  /**
   * @return
   */
  public UserDto getUserDto() {
    return _userDto;
  }

  /**
   * @param dto
   */
  public void setUserDto(UserDto dto) {
    _userDto = dto;
  }
}
