package com.gulfstreambio.kc.web.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PageLink {

  /**
   * The display name of the page, from the form definition.
   */
  private String _displayName;

  /**
   * An indication of whether this page is the selected page within the PageLinkCollection.
   */
  private boolean _selected = false;

  /**
   * An indication of whether this page has any required elements.
   */
  private boolean _hasRequired = false;

  /**
   * The list of sub-page links of this page.
   */
  private List _pageLinks;
  
  /**
   * The id of the HTML element that serves as the link element.
   */
  private String _id;

  /**
   * The id of the HTML element that serves as the container for the contents associated with
   * this page link. 
   */
  private String _contentsId;

  /**
   * The URL used to get the contents of this page.
   */
  private String _url;

  /**
   * An indication of whether AJAX will be used to fetch the contents of this page.
   */
  private boolean _useAjax;
  
  /**
   *  Creates a new <code>PageLink</code>.
   */
  public PageLink() {
    super();
  }

  public String getDisplayName() {
    return _displayName;
  }
  
  public void setDisplayName(String displayName) {
    _displayName = displayName;
  }
  
  public boolean isSelected() {
    return _selected;
  }
  
  public void setSelected(boolean selected) {
    _selected = selected;
  }

  public boolean isHasRequired() {
    return _hasRequired;
  }
  
  public void setHasRequired(boolean required) {
    _hasRequired = required;
  }

  public boolean isUseAjax() {
    return _useAjax;
  }

  public void setUseAjax(boolean useAjax) {
    _useAjax = useAjax;
  }
  
  public String getId() {
    return _id;
  }

  public void setId(String id) {
    _id = id;
  }
  
  public String getContentsId() {
    return _contentsId;
  }

  public void setContentsId(String contentsId) {
    _contentsId = contentsId;
  }
  
  public String getUrl() {
    return _url;
  }

  public void setUrl(String url) {
    _url = url;
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
