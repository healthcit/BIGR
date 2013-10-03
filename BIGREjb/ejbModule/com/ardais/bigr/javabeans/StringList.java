package com.ardais.bigr.javabeans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.assistants.Packable;
import com.ardais.bigr.security.SecurityInfo;

/**
 * This class represents a list of arbitrary strings.
 */
public class StringList implements java.io.Serializable, Packable {
  private static final long serialVersionUID = 1229836234517114037L;

  private List _list = new ArrayList();

  public StringList() {
    super();
  }

  /**
   * Initialize a list from a packed list contents string.
   *
   * @see #unpack(String) unpack
   * @see Packable
   */
  public StringList(String packedContents) {
    this();
    unpack(packedContents);
  }

  /**
   * Initialize a list from a List.  The supplied list must
   * be a list of strings.
   */
  public StringList(List list) {
    this();

    // First empty out all of the existing contents.
    //
    _list = new ArrayList();

    if (list == null || list.size() == 0)
      return;

    Iterator iter = list.iterator();
    while (iter.hasNext()) {
      String s = (String) iter.next();
      add(s);
    }
  }

  /**
   * Initialize a list from an array of strings.
   */
  public StringList(String[] strings) {
    this();

    // First empty out all of the existing contents.
    //
    _list = new ArrayList();

    if (strings == null || strings.length == 0)
      return;

    for (int i = 0; i < strings.length; i++) {
      add(strings[i]);
    }
  }

  /**
   * Add a string to the list.
   *
   * @param s the string to add
   * @return <code>true</code>, for consistency with the
   *    {@link java.util.Collection} interface, in case this someday
   *    implements the full collection interface.
   */
  public boolean add(String s) {
    _list.add(s);
    return true;
  }

  /**
   * Append to the supplied string buffer an HTML representation
   * of the list contents.
   *
   * @param buffer the string buffer to append to.
   * @param securityInfo security info about the logged-in user.  This
   *     may be used when deciding what HTML to generate.
   */
  public void appendICPHTML(StringBuffer buffer, SecurityInfo securityInfo) {
    Iterator iter = iterator();
    boolean first = true;
    while (iter.hasNext()) {
      String s = (String) iter.next();

      if (first) {
        first = false;
      }
      else {
        buffer.append(", ");
      }

      Escaper.htmlEscape(s, buffer);
    }
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

  /**
   * Returns unmodifiable view of the list of elements.
   *
   * @return list.
   */
  public List getList() {
    return Collections.unmodifiableList(_list);
  }

  public String pack() {
    // The packed form is a comma-separated list of strings.  We include spaces after the
    // commas so that it can be used in places that need to be human readable.  However,
    // to robustly handle strings in the list that contain ", ", we sacrifice a bit of
    // human-readability in some situations.  When we pack a string, we first replace
    // any "\" characters with "\\", then replace any ", " with ",\ ".  When we unpack,
    // we reverse this and replace ",\ " with ", " and then "\\" with "\".
    //
    // For nulls in the list, they are simply empty in the packed form, i.e. two commas in a row
    // separated by a space. 

    // The size we allocate initially for the StringBuffer is just a wild guess
    // at the size the result string will be.
    //
    StringBuffer sb = new StringBuffer(_list.size() * 17);

    Iterator iter = _list.iterator();
    boolean first = true;
    while (iter.hasNext()) {
      String s = (String) iter.next();

      if (first) {
        first = false;
      }
      else {
        sb.append(", ");
      }

      Object escaped = escape(s);
      if (escaped != null) {
        sb.append(escaped);
      }
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

    if (ApiFunctions.isEmpty(packedData)) {
      return;
    }
      
    String[] items = ApiFunctions.separateString(packedData, ", ");
    for (int i = 0; i < items.length; i++) {
      String s = items[i];
      if (s.length() == 0) {
        add(null);
      }
      else {
        add(unescape(items[i]));
      }
    }
  }

  private Object escape(String s) {
    // Escape the string as described in comments in the "pack" method.
    
    String s1 = ApiFunctions.replace(s, "\\", "\\\\");
    s1 = ApiFunctions.replace(s1, ", ", ",\\ ");
    
    return s1;
  }

  private String unescape(String s) {
    // Unescape the string as described in comments in the "pack" method.
    
    String s1 = ApiFunctions.replace(s, ",\\ ", ", ");
    s1 = ApiFunctions.replace(s1, "\\\\", "\\");
    
    return s1;
  }
}
