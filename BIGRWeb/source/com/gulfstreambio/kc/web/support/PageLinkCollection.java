package com.gulfstreambio.kc.web.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class PageLinkCollection {

  /**
   * The list of top level pages in the collection.
   */
  private List _pageLinks;
  
  /**
   *  Creates a new <code>PageLinkCollection</code>.
   */
  public PageLinkCollection() {
    super();
  }

  public void addPageLink(PageLink pageLink) {
    if (_pageLinks == null) {
      _pageLinks = new ArrayList();
    }
    _pageLinks.add(pageLink);
  }
  
  public Iterator getPageLinks() {
    return (_pageLinks == null) ? Collections.EMPTY_LIST.iterator() : _pageLinks.iterator(); 
  }
  
  public PageLink getSelectedPageLink() {
    Iterator i = getPageLinks(); 
    while (i.hasNext()) {
      PageLink page = (PageLink) i.next();
      if (page.isSelected()) {
        return page;
      }
    }
    return null;
  }
  
  public int getCount() {
    return (_pageLinks == null) ? 0 : _pageLinks.size(); 
  }

}
