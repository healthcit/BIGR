package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.util.Date;

import com.ardais.bigr.beanutils.BigrBeanUtilsBean;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ShoppingCartData implements Serializable {

  private Date _creation;
  private String _userId;
  private Integer _amount;
  
  /**
   * Constructor for ShoppingCartData.
   */
  public ShoppingCartData() {
    super();
  }
  
  /**
   * Constructor for ShoppingCartData.
   */
  public ShoppingCartData(String user, Date create, Integer amt) {
    _creation = create;
    _userId = user;
    _amount = amt;
  }
  
  /**
   * Constructor for ShoppingCartData.
   */
  public ShoppingCartData(ShoppingCartData shoppingCartData) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, shoppingCartData);
    //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
    //does a shallow copy.
    if (shoppingCartData.getCreation() != null) {
      _creation = (Date) shoppingCartData.getCreation().clone();
    }
  }

  /**
   * Returns the creation.
   * @return Date
   */
  public Date getCreation() {
    return _creation;
  }

  /**
   * Returns the userId.
   * @return String
   */
  public String getUserId() {
    return _userId;
  }

  /**
   * Returns the amount.
   * @return the amount in this entry of the cart, or null if there is no amount.
   */
  public Integer getAmount() {
    return _amount;
  }

}
