package com.ardais.bigr.orm.btx;

import java.io.Serializable;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * Represent the details of starting a manage roles transaction.
 * 
 * <p>The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setUserData(UserDto)}: A UserDto containing the id of the user.</li>
 * <li>{@link #setSelectedRoles(String[])}: A String array containing the role ids
 *      to which the user should be given access.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setAssignedRoles(List)}: The roles to which the user currently has access.</li>
 * <li>{@link #setAllRoles(List)}: A list of all roles.</li>
 * <li>{@link #setRemovedRoles(List)}: Any roles to which the user just had access removed.</li>
 * <li>{@link #setAddedRoles(List)}: Any roles to which the user just had access added.</li>
 * </ul>
 * 
 */
public class BtxDetailsManageRolesStart extends BtxDetailsManageRoles implements Serializable {

  public BtxDetailsManageRolesStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MANAGE_ROLES_START;
  }
}
