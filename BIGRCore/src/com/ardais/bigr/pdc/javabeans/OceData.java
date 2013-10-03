package com.ardais.bigr.pdc.javabeans;

import java.io.Serializable;
import java.util.List;

/**
 * @author Nagraj
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OceData implements Serializable {
	
	private String _tableName;
	private String _attribute;
	//private String _attributeName;
	private String _status;
	private String _listOrder;	
  private String _startDate;
  private String _endDate;
	private String _user;	
	private List _list;
	private List _idList;
	
	/**
	 * Returns the list.
	 * @return List
	 */
	public List getList() {
		return _list;
	}

	/**
	 * Returns the listOrder.
	 * @return String
	 */
	public String getListOrder() {
		return _listOrder;
	}

	/**
	 * Returns the status.
	 * @return String
	 */
	public String getStatus() {
		return _status;
	}

	/**
	 * Returns the tableName.
	 * @return String
	 */
	public String getTableName() {
		return _tableName;
	}

	/**
	 * Sets the list.
	 * @param list The list to set
	 */
	public void setList(List list) {
		_list = list;
	}

	/**
	 * Sets the listOrder.
	 * @param listOrder The listOrder to set
	 */
	public void setListOrder(String listOrder) {
		_listOrder = listOrder;
	}

	/**
	 * Sets the status.
	 * @param status The status to set
	 */
	public void setStatus(String status) {
		_status = status;
	}

	/**
	 * Sets the tableName.
	 * @param tableName The tableName to set
	 */
	public void setTableName(String tableName) {
		_tableName = tableName;
	}

	/**
	 * Returns the attribute.
	 * @return String
	 */
	public String getAttribute() {
		return _attribute;
	}

	/**
	 * Sets the attribute.
	 * @param attribute The attribute to set
	 */
	public void setAttribute(String attribute) {
		_attribute = attribute;
	}

	/**
	 * Returns the _tableName.
	 * @return String
	 */
	
	/**
	 * Returns the user.
	 * @return String
	 */
	public String getUser() {
		return _user;
	}

	/**
	 * Sets the user.
	 * @param user The user to set
	 */
	public void setUser(String user) {
		_user = user;
	}

	/**
	 * Returns the idList.
	 * @return List
	 */
	public List getIdList() {
		return _idList;
	}

	/**
	 * Sets the idList.
	 * @param idList The idList to set
	 */
	public void setIdList(List idList) {
		_idList = idList;
	}

  /**
   * @return
   */
  public String getEndDate() {
    return _endDate;
  }

  /**
   * @return
   */
  public String getStartDate() {
    return _startDate;
  }

  /**
   * @param string
   */
  public void setEndDate(String string) {
    _endDate = string;
  }

  /**
   * @param string
   */
  public void setStartDate(String string) {
    _startDate = string;
  }

}
