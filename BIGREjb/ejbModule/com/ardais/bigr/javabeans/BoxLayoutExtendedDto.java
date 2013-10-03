package com.ardais.bigr.javabeans;

import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.IcpUtils;

public class BoxLayoutExtendedDto extends BoxLayoutDto {
  static final long serialVersionUID = -6598185508827104039L;

  private List _associatedAccountBoxLayoutList = null;

  public BoxLayoutExtendedDto() {
    super();
  }

  public void updateAssociatedAccountBoxLayouts(SecurityInfo securityInfo) {
    setAssociatedAccountBoxLayoutList(
      BoxLayoutUtils.getAccountBoxLayoutDtosByBoxLayoutId(getBoxLayoutId()));
  }

  /**
   * This returns an HTML description of if/how this box layout is used by various accounts.
   * The method (used in the Box Layout ICP page and possibly elsewhere) takes who the current
   * user is into account.  If the current use is an ICP Superuser, the result will include
   * information about all accounts that use the box layout.  Otherwise, it will only include
   * information about how the box layout is used in the user's account.
   * 
   * @return the security-filtered description of what accounts are mapped to this box layout.
   */
  public String getFilteredAccountUsageHtml(SecurityInfo securityInfo) {
    StringBuffer sb = new StringBuffer(256);

    sb.append("<ul style=\"margin-left: 20px; margin-bottom: 0px;\">");

    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)) {
      // The user is an ICP superuser, show information about how the box layout is used
      // in all accounts.

      Iterator iter = getAssociatedAccountBoxLayoutList().iterator();
      while (iter.hasNext()) {
        AccountBoxLayoutDto abldto = (AccountBoxLayoutDto) iter.next();
        sb.append("<li>");
        sb.append(
          IcpUtils.prepareLink(abldto.getAccountId(), abldto.getAccountName(), securityInfo));
        sb.append(": ");
        Escaper.htmlEscape(abldto.getBoxLayoutName(), sb);
        sb.append("</li>");
      }
    }
    else {
      // The user is not an ICP superuser, only show information about how the box layout is used
      // in the user's account.

      String userAccount = securityInfo.getAccount();
      Iterator iter = getAssociatedAccountBoxLayoutList().iterator();
      while (iter.hasNext()) {
        AccountBoxLayoutDto abldto = (AccountBoxLayoutDto) iter.next();
        if (userAccount.equals(abldto.getAccountId())) {
          sb.append("<li>");
          Escaper.htmlEscape(abldto.getBoxLayoutName(), sb);
          sb.append("</li>");
        }
      }
    }

    sb.append("</ul>");

    return sb.toString();
  }

  /**
   * @return
   */
  public List getAssociatedAccountBoxLayoutList() {
    return _associatedAccountBoxLayoutList;
  }

  /**
   * @param list
   */
  public void setAssociatedAccountBoxLayoutList(List list) {
    _associatedAccountBoxLayoutList = list;
  }
}
