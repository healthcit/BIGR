package com.ardais.bigr.query;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ColumnPermissions {
  public final static int NONE = 1 << 0;
  public final static int ALL = ~0;

  // roles
  public final static int ROLE_ALL = ~0;
  public final static int ROLE_SYSTEM_OWNER = 1 << 1;
  public final static int ROLE_DI = 1 << 2;
  public final static int ROLE_CUST = 1 << 3;

  // **** IMPORTANT: The values of these constants (products, screen, transactions) are stored
  // **** in the database as part of users' saved column configurations, so don't change them!
  // products
  public final static int PROD_ALL = ~0;
  public final static int PROD_RNA = 1 << 1;
  public final static int PROD_TISSUE = 1 << 2;
  public final static int PROD_GENERIC = 1 << 3;

  // screens
  public final static int SCRN_SELECTION = 1 << 1; // Search result display sample selection (rna+tissue)
  public final static int SCRN_HOLD_LIST = 1 << 2; // hold list display
  public final static int SCRN_SAMP_AMOUNTS = 1 << 3; // set amounts for RNA hold request
  public final static int SCRN_CONFIRM_REQ = 1 << 4; // Confirm Request Screen
  public final static int SCRN_ORDER_DET = 1 << 5; // Order details scrn (only screen for order det transaction)
  public final static int SCRN_ICP = 1 << 6; // Order details scrn (only screen for order det transaction)
  public final static int SCRN_DERIV_OPS_SUMMARY = 1 << 7;  //Derivative operations summary screen
  
  // transactions (pick list vs. sample selection vs. Order view)
  public final static int TX_SAMP_SELECTION = 1 << 1;
  public final static int TX_REQUEST_SAMP = 1 << 2;
  public final static int TX_ORDER_DETAIL = 1 << 3; // show order details, pick deliv type
  public final static int TX_TYPE_ICP = 1 << 4;
  public final static int TX_TYPE_DERIV_OPS = 1 << 5;
  // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  // **** IMPORTANT: The values of these constants (products, screen, transactions) are stored
  // **** in the database as part of users' saved column configurations, so don't change them!

  /**
   * Return true if the value is consistent with any of the bit masks in the array;
   * @param value   the value that defines a set of permissiongs
   * @param anyOfTheseFlags   the flags to check for a match (with any flag)
   * 
   * examples
   * isAny(SCRN_SELECTION, new int[] {SCRN_HOLD_LIST, SCRN_SAMP_AMOUNTS}) returns false
   * isAny(SCRN_SELECTION, new int[] {SCRN_HOLD_LIST, SCRN_SELECTION}) returns true
   * 
   */
  public static boolean isAny(int permission, int[] anyOfTheseFlags) {
    int overallFlag = NONE;
    for (int i=0;i<anyOfTheseFlags.length;i++) 
      overallFlag &= anyOfTheseFlags[i];  // bit-AND sets all allowed bits
    return (permission | overallFlag) != 0; // or checks for match with any allowed bit
  }
}
