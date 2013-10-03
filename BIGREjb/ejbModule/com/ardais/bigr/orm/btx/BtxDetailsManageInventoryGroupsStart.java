package com.ardais.bigr.orm.btx;

import java.io.Serializable;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * Represent the details of starting a manage inventory groups transaction.
 * 
 * <p>The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setUserData(UserDto)}: A UserDto containing the id of the user.  OR</li>
 * <li>{@link #setAccountData(AccountDto)}: An AccountDto containing the id of the account.</li>
 * <li>{@link #setSelectedInventoryGroups(String[])}: A String array containing the inventory group ids
 *      to which the user or account should be given access.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setAssignedInventoryGroups(List)}: The inventory groups to which the 
 * user or account currently has access.</li>
 * <li>{@link #setAllInventoryGroups(List)}: A list of all inventory groups.</li>
 * <li>{@link #setRemovedInventoryGroups(List)}: Any inventory groups to which the user or account
 *      just had access removed.</li>
 * <li>{@link #setAddedInventoryGroups(List)}: Any inventory groups to which the user or account 
 *      just had access added.</li>
 * </ul>
 * 
 */
public class BtxDetailsManageInventoryGroupsStart extends BtxDetailsManageInventoryGroups implements Serializable {
  //private static final long serialVersionUID = -8715833307885399843L;

  public BtxDetailsManageInventoryGroupsStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MANAGE_INVENTORY_GROUPS_START;
  }
}
