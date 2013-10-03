package com.ardais.bigr.query;

import java.io.Serializable;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.security.SecurityInfo;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ViewParams implements Serializable {
  // for showing/configuring columns
  private int _product = ColumnPermissions.NONE;
  private int _screen = ColumnPermissions.NONE;
  private int _transaction = ColumnPermissions.NONE;
  private int _role = ColumnPermissions.NONE;

  // for ICP and Taglib rendering
  private SecurityInfo _securityInfo;

  public ViewParams(String key, SecurityInfo secInfo) {
    // NOTE must match getKey() method
    int product = ColumnPermissions.NONE;
    int screen = ColumnPermissions.NONE;
    int transaction = ColumnPermissions.NONE;
    StringTokenizer stok = new StringTokenizer(key, ",");
    try {
      product = Integer.parseInt(stok.nextToken());
      screen = Integer.parseInt(stok.nextToken());
      transaction = Integer.parseInt(stok.nextToken());
    }
    catch (Exception e) {
      throw new ApiException("Unable to decode view description key: " + key, e);
    }
    initialize(secInfo, product, screen, transaction);
  }

  public ViewParams(SecurityInfo secInfo, int product, int screen, int transaction) {
    initialize(secInfo, product, screen, transaction);
  }

  private void initialize(SecurityInfo secInfo, int product, int screen, int transaction) {
    _securityInfo = secInfo;
    _product = product;
    _screen = screen;
    _transaction = transaction;

    if (secInfo.isInRoleSystemOwner()) {
      _role = ColumnPermissions.ROLE_SYSTEM_OWNER;
    }
    else if (secInfo.isInRoleDi()) {
      _role = ColumnPermissions.ROLE_DI;
    }
    else if (secInfo.isInRoleCustomer()) {
      _role = ColumnPermissions.ROLE_CUST;
    }
    else {
      _role = ColumnPermissions.NONE;
    }
  }

  /**
   * At one time, the key string (see {@link #getKey()}) contained the user-role piece
   * in addition to the product, screen and transaction pieces.  It was the fourth (last)
   * component in the comma-separated list.  For robust security, we now always set the role
   * piece dynamically based on the current user's role from SecurityInfo, instead of getting
   * a possibly-incorrect value from a different source (parsing it from the key).  This was
   * a real potential issue, since we store these keys in the database as part of the XML that
   * represents a users column configurations (ES_ARDAIS_USER.PROFILE_TOPICS).
   * 
   * <p>This function takes as input a key string that may be in either the old or new format
   * and returns the key in its new format.  This is used when reading keys stored in the database
   * to convert them to their new form.  They'll continue to be in the database in their old form
   * until the user happens to update their column configuration for that key, at which time
   * the old key will be replaced by the new key.
   * 
   * @param key The new or old key.
   * @return The key in its new format.
   */
  public static String getCurrentKeyFormat(String key) {
    // If the key contains three commas, it is in the old format.
    if (StringUtils.countMatches(key, ",") == 3) {
      StringTokenizer stok = new StringTokenizer(key, ",");
      int product = Integer.parseInt(stok.nextToken());
      int screen = Integer.parseInt(stok.nextToken());
      int transaction = Integer.parseInt(stok.nextToken());
      return getKey(product, screen, transaction);
    }
    else {
      return key;
    }
  }

  /**
   * Return a key indicating what unique context this configuration is for.  Currently, the
   * context is driven by the screen, transaction, and product.
   * Keys are stored in the database as part of user's column configurations, so be careful
   * changing the key format.  We've already changed the format once -- see the
   * {@link #getCurrentKeyFormat(String)} method for details, so we already have a slightly
   * complicated situation of recognizing multiple key formats.
   */
  public String getKey() {
    // NOTE must match constructor that takes a string key
    return getKey(getProduct(), getScreen(), getTransaction());
  }

  /**
   * Return a key indicating what unique context a configuration is for.  Currently, the
   * context is driven by the screen, transaction, and product.
   * Keys are stored in the database as part of user's column configurations, so be careful
   * changing the key format.  We've already changed the format once -- see the
   * {@link #getCurrentKeyFormat(String)} method for details, so we already have a slightly
   * complicated situation of recognizing multiple key formats.
   */
  private static String getKey(int product, int screen, int transaction) {
    // NOTE must match constructor that takes a string key
    return Integer.toString(product)
      + ','
      + Integer.toString(screen)
      + ','
      + Integer.toString(transaction);
  }

  /**
   * Returns the product.
   * @return int
   */
  public int getProduct() {
    return _product;
  }

  /**
   * Returns the security.
   * @return int
   */
  public int getRole() {
    return _role;
  }

  /**
   * Returns the securityInfo.
   * @return SecurityInfo
   */
  public SecurityInfo getSecurityInfo() {
    return _securityInfo;
  }

  /**
   * Returns the transaction.
   * @return int
   */
  public int getScreen() {
    return _screen;
  }

  /**
   * Returns the overallTansaction.
   * @return int
   */
  public int getTransaction() {
    return _transaction;
  }

  public String toString() {
    return "trans=" + getTransaction() + " screen=" + getScreen() + " product=" + getProduct();
  }

  /*
   * Bit-checking operations.  Localize this here, since bit ops are tricky and  
   * particular to the internal representation of columns.
   */

  /**
   * Returns True if this ViewParams is for the screen.  screen is one of Columns.SCRN_XXXXX
   */
  public boolean isScreen(int screen) {
    return (getScreen() & screen) != 0;
  }

  /**
   * Returns True if this ViewParams is for the product.  product is one of Columns.PROD_XXXXX
   */
  public boolean isProduct(int product) {
    return (getProduct() & product) != 0;
  }

  /**
   * @return True if the viewpermissions are for an RNA-only tab.
   */
  public boolean isRnaTab() {
    // only the RNA product on the select screen
    return ((isScreen(ColumnPermissions.SCRN_SELECTION) || isScreen(ColumnPermissions.SCRN_ICP)) && isProduct(ColumnPermissions.PROD_RNA));
  }

  /**
   * @return True if the viewpermissions are for an RNA-only tab.
   */
  public boolean isTissueTab() {
    // only the tissue product on the select screen
    return ((isScreen(ColumnPermissions.SCRN_SELECTION) || 
             isScreen(ColumnPermissions.SCRN_ICP) || 
             isScreen(ColumnPermissions.SCRN_DERIV_OPS_SUMMARY)) && 
             isProduct(ColumnPermissions.PROD_TISSUE));
  }

  /**
   * @return True if the viewpermissions are for an generic RNA+Tissue view.
   */
  public boolean isGenericScreen() {
    // any but select and ICP screens
    return (!isScreen(ColumnPermissions.SCRN_SELECTION)&& 
            !isScreen(ColumnPermissions.SCRN_ICP)&& 
            !isScreen(ColumnPermissions.SCRN_DERIV_OPS_SUMMARY));
  }
}
