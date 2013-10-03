package com.ardais.bigr.javabeans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.ardais.bigr.iltds.assistants.Packable;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * This class represents a list of object ids.
 */
public class IdList implements java.io.Serializable, Packable {
  private static final long serialVersionUID = 1220147503317114037L;

  private List _list = new ArrayList();

  public IdList() {
    super();
  }

  /**
   * Initialize an id list from a packed id list contents string.
   *
   * @see #unpack(String) unpack
   * @see Packable
   */
  public IdList(String packedContents) {
    this();
    unpack(packedContents);
  }

  /**
   * Initialize an id list from a List.  The supplied list must
   * be a list of strings, and none of the strings in the list can be null or empty.
   */
  public IdList(List list) {
    this();
    
    int listsize = ((list == null) ? 0 : list.size());

    // First empty out all of the existing contents.
    //
    _list = new ArrayList(listsize);

    if (listsize == 0)
      return;

    Iterator iter = list.iterator();
    while (iter.hasNext()) {
      String s = (String) iter.next();
      add(s);
    }
  }
  
  /**
   * @return True if the supplied list is null or empty.
   * @param list The list.
   */
  public static boolean isEmpty(IdList list) {
    return ((list == null) ? true : (list.size() == 0));
  }

  /**
   * Add an id to the list of object ids.  The id must not be null or empty,
   * It also must not contain commas or spaces.
   *
   * @param id the object id to add.
   * @return <code>true</code>, for consistency with the
   *    {@link java.util.Collection} interface, in case this someday
   *    implements the full collection interface.
   */
  public boolean add(String id) {
    // NOTE: If this is ever changed to permit nulls in the list, empty strings, or
    // ids that contain spaces or commas, the pack and unpack methods will also have
    // to be changed to properly handle these.  Their current implementations are not
    // designed to correctly handle them.

    if (id == null || id.length() == 0) {
      throw new IllegalArgumentException("IdList.add: id must not be null or empty.");
    }

    if ((id.indexOf(' ') >= 0) || (id.indexOf(',') >= 0)) {
      throw new IllegalArgumentException(
        "IdList.add: id must not contain spaces or commas: " + id + ".");
    }

    _list.add(id);
    return true;
  }

  /**
   * Append to the supplied string buffer an HTML representation
   * of the list contents that contains ICP hyperlinks for the object ids.
   * The link text for each item will be taken from the corresponding item
   * in the linkTexts parameter. 
   *
   * @param buffer the string buffer to append to.
   * @param linkTexts the link text to display for each id.  This list must have at least
   *     as many items as this IdList.
   * @param securityInfo security info about the logged-in user.  This
   *     is used when deciding whether to make an item an ICP link or not.
   */
  public void appendICPHTML(StringBuffer buffer, List linkTexts, SecurityInfo securityInfo) {
    Iterator iter = iterator();
    Iterator linkTextIter = linkTexts.iterator();
    boolean first = true;
    while (iter.hasNext()) {
      String id = (String) iter.next();
      String linkText = (String) linkTextIter.next();

      if (first) {
        first = false;
      }
      else {
        buffer.append(", ");
      }

      buffer.append(IcpUtils.prepareLink(id, linkText, securityInfo));
    }
  }

  /**
   * Append to the supplied string buffer an HTML representation
   * of the list contents that contains ICP hyperlinks for the object ids.
   *
   * @param buffer the string buffer to append to.
   * @param securityInfo security info about the logged-in user.  This
   *     is used when deciding whether to make an item an ICP link or not.
   */
  public void appendICPHTML(StringBuffer buffer, SecurityInfo securityInfo) {
    appendICPHTML(buffer, getList(), securityInfo);
  }

  /**
   * Return a set of the object ids of the objects that are directly involved
   * in this id list.  This is simply the set of all elements in the list.
   *
   * <p>This is a helper method for implementations of
   * com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects().
   *
   * @return the set of directly involved object ids.
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();

    if (_list != null) {
      set.addAll(_list);
    }

    return set;
  }

  /**
   * Return an iterator that iterates through all of the list elements in order.
   *
   * @return the iterator.
   */
  public Iterator iterator() {
    return _list.iterator();
  }

  /**
   * Return the number of elements in the list.
   *
   * @return the number of elements in the list.
   */
  public int size() {
    return _list.size();
  }

  public String pack() {
    // The packed form is a comma-separated list of ids.  We include spaces after the
    // commas so that it can be used in places that need to be human readable.

    // The size we allocate initially for the StringBuffer is just a rough guess
    // at the size the result string will be, based on typical id sizes.
    //
    StringBuffer sb = new StringBuffer(_list.size() * 17);

    Iterator iter = _list.iterator();
    boolean first = true;
    while (iter.hasNext()) {
      String id = (String) iter.next();

      if (first) {
        first = false;
      }
      else {
        sb.append(", ");
      }

      sb.append(id);
    }

    return sb.toString();
  }

  public void unpack(String packedData) {
    // See the "pack" method for a description of the packed data format
    // we expect here.  We don't check that it has the expected format, we
    // just assume that it does.

    // First empty out all of the existing contents.
    //
    _list = new ArrayList();

    if (packedData == null || packedData.length() == 0)
      return;

    StringTokenizer st = new StringTokenizer(packedData, ", ", false);
    while (st.hasMoreTokens()) {
      add(st.nextToken());
    }
  }

  /**
   * Returns unmodifiable view of the list of elements.
   *
   * @return list.
   */
  public List getList() {
    return Collections.unmodifiableList(_list);
  }
  
  public String toString() {
    return _list.toString();
  }

}