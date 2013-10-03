package com.ardais.bigr.orm.btx;

import java.io.Serializable;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * Represent the details of starting to modify a user.
 * 
 * <p>The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setUserData(UserDto)}: A UserDto containing the id of the user.</li>
 * <li>{@link #setSelectedPrivileges(String[])}: A String array containing the privilege ids
 *      to which the user should be given access.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setPrivilegeFilter(String)}: an indicator as to which functional area of privileges 
 * the user wishes to view.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setAccessiblePrivileges(List)}: The privileges to which the 
 * user currently has access.</li>
 * <li>{@link #setAllPrivileges(List)}: All privileges.</li>
 * <li>{@link #setRemovedPrivileges(List)}: Any privileges to which the user just 
 *      had access removed.</li>
 * <li>{@link #setAddedPrivileges(List)}: Any privileges to which the user just 
 *      had access added.</li>
 * </ul>
 */

public class BtxDetailsManagePrivilegesStart extends BtxDetailsManagePrivileges implements Serializable {
  private static final long serialVersionUID = -8715833307885399843L;

  public BtxDetailsManagePrivilegesStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MANAGE_PRIVILEGES_START;
  }
}
