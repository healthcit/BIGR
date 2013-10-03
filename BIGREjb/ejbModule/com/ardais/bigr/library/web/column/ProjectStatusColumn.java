package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;

/**
 */
public class ProjectStatusColumn extends SampleColumnImpl {
  private SecurityInfo _securityInfo = null;

  public ProjectStatusColumn(ViewParams cp) {
    super(
      30,
      "library.ss.result.header.projectStatus.label",
      "library.ss.result.header.projectStatus.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_DI,
      ColumnPermissions.ALL,
      cp);
    _securityInfo = ((cp == null) ? null : cp.getSecurityInfo());
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    String returnValue = null;
    if (_securityInfo.isInRoleSystemOwner()) {
      returnValue = ssh.getProjectDescriptionAsText();
    }
    else if (_securityInfo.isInRoleDi()) {
      if (ssh.isBms()) {
        returnValue = ssh.getProjectDescriptionAsText();          
      }
      else {
        returnValue = "";
      }
    }
    return returnValue;
    
  }

  protected String getBodyText(SampleRowParams rp) throws IOException {
    SampleSelectionHelper ssh = rp.getItemHelper();
    String returnValue = null;
    if (_securityInfo.isInRoleSystemOwner()) {
      returnValue = ssh.getProjectDescriptionAsHtml();
    }
    else if (_securityInfo.isInRoleDi()) {
      if (ssh.isBms()) {
        returnValue = ssh.getProjectDescriptionAsHtml();          
      }
      else {
        returnValue = "<td></td>";
      }
    }
    return returnValue;
  }

}
