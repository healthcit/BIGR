package com.ardais.bigr.api;

/**
 * @author sthomashow
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SystemIds {

	private String _idPrefix;
	private int _idLength; 
  private boolean _hexAllowed;

  /**
   * Constructor for SystemIds.
   */
  public SystemIds() {
    super();
  }

  /**
   * Constructor for SystemIds.
   */
  public SystemIds(String idPrefix, int idLength, boolean hexAllowed) {
    this();
    setIdPrefix(idPrefix);
    setIdLength(idLength);
    setHexAllowed(hexAllowed);
  }
	
	public String getIdPrefix() {
		return _idPrefix;
	}
	
	public int getIdLength() {
		return _idLength;
	}
  
  public boolean getHexAllowed() {
    return _hexAllowed;
  }
	
	public void setIdPrefix(String id) {
		_idPrefix = id;
	}
	
	public void setIdLength(int length) {
		_idLength = length;
	}
	
  public void setHexAllowed(boolean allowed) {
    _hexAllowed = allowed;
  }	

}
