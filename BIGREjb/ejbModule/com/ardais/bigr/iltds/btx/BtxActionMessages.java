package com.ardais.bigr.iltds.btx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * <p>A class that encapsulates the messages being reported in
 * the course of performing a business transaction.</p>
 * 
 * <p>This class mirrors the <code>com.ardais.bigr.iltds.btx.BtxActionErrors</code> 
 * class.
 *
 * <p>Each individual message is described by a <code>BtxActionMessage</code>
 * object, which contains a message key (to be looked up in an appropriate
 * Struts message resources database), and up to four placeholder arguments
 * used for parametric substitution in the resulting message.  Since the
 * mapping of message keys to actual messages is maintained by Struts,
 * a <code>BtxActionMessage</code> object can only be converted to its final
 * message text with web application code (not elsewhere, such as EJB code).</p>
 *
 * <p><strong>IMPLEMENTATION NOTE</strong> - It is assumed that these objects
 * are created and manipulated only within the context of a single thread.
 * Therefore, no synchronization is required for access to internal
 * collections.</p>
 */
public class BtxActionMessages implements Serializable {

  // ARDAIS NOTE: The code for this class is derived directly from the Struts
  // ActionErrors source code, with all due credit to the Struts authors.
  // It has not been rewritten to use Ardais coding conventions.
  //
  // In the BTX framework, every BTXDetails object has a non-null
  // BtxActionMessages object embedded in it.  To reduce space requirements
  // in some situations where many BTXDetails objects are in memory at
  // once (but with no messages), we've made some small changes to this class
  // so that the messages HashMap isn't created until it is actually needed.
  // We've also made a change to the add method so that it won't add the
  // exact same message more than once to the collection.

  // ----------------------------------------------------- Manifest Constants

  /**
   * The marker to use for global messages - for now that's the only kind
   * of message allowed.
   */
  public static final String GLOBAL_MESSAGES = "com.ardais.BTX_ACTION_MESSAGES";

  // ----------------------------------------------------- Instance Variables

  /**
   * The accumulated set of <code>BtxActionMessages</code> objects (represented
   * as an ArrayList) for each property, keyed by property name.
   */
  protected HashMap _messages = null;

  // --------------------------------------------------------- Public Methods

  /**
   * Add a message to the set of messages.
   * This method won't add the BtxActionMessage to the messages if the 
   * messages already contain an <code>equals</code>
   * BtxActionMessage object under the same <code>property</code>.
   *
   * @param message The message to be added
   */
  public void add(BtxActionMessage message) {

    if (_messages == null)
      _messages = new HashMap();

    ArrayList list = (ArrayList) _messages.get(GLOBAL_MESSAGES);
    if (list == null) {
      list = new ArrayList();
      _messages.put(GLOBAL_MESSAGES, list);
    }
    if (!list.contains(message)) {
      list.add(message);
    }
  }
  
  /**
   * Add all of the messages in the supplied collection to this object.
   * 
   * @param newMessages the messages to add.
   */
  public void addAll(BtxActionMessages newMessages) {
    if (newMessages == null) {
      return;
    }
    
    // NOTE: If we ever change to be more like ActionErrors and have a way to add things
    // under multiple key (instead of always under GLOBAL_MESSAGES), this will need to change
    // to add items under the same key that they had in the original collection (newMessages).
    
    Iterator iter = newMessages.get();
    while (iter.hasNext()) {
      add((BtxActionMessage) iter.next());
    }
  }

  /**
   * Clear all messages recorded by this object.
   */
  public void clear() {
    if (_messages != null)
      _messages.clear();
  }

  /**
   * Return <code>true</code> if there are no messages recorded
   * in this collection, or <code>false</code> otherwise.
   */
  public boolean empty() {
    return ((_messages == null) || (_messages.size() == 0));
  }

  /**
   * Return the set of all recorded messages, without distinction
   * by which property the messages are associated with.  If there are
   * no messages recorded, an empty enumeration is returned.
   */
  public Iterator get() {

    if ((_messages == null) || (_messages.size() == 0))
      return (Collections.EMPTY_LIST.iterator());
    ArrayList results = new ArrayList();
    Iterator props = _messages.keySet().iterator();
    while (props.hasNext()) {
      String prop = (String) props.next();
      Iterator messages = ((ArrayList) _messages.get(prop)).iterator();
      while (messages.hasNext())
        results.add(messages.next());
    }
    return (results.iterator());

  }

  /**
   * Return the number of messages recorded.  
   * <strong>NOTE</strong> - it is more efficient to call <code>empty()</code> 
   * if all you care about is whether or not there are any error messages at all.
   */
  public int size() {

    if (_messages == null)
      return 0;

    int total = 0;
    Iterator keys = _messages.keySet().iterator();
    while (keys.hasNext()) {
      String key = (String) keys.next();
      ArrayList list = (ArrayList) _messages.get(key);
      total += list.size();
    }
    return (total);
  }

}
