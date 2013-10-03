package com.ardais.bigr.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.iltds.helpers.TypeFinder;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * This BTXDetails class provides a means to enter ad-hoc notes in the
 * history tables.  Initially history records for "transactions" of this type
 * will be entered via back-end SQL scripts when needed (for example, to
 * describe a back-end data update that has been made).  Someday there may
 * be a GUI front end for this in BIGR.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setNote(String) Note}: Text that will appear in the
 *     transaction details column in ICP.  Anything in the text that
 *     syntactically appears to be an object id for an object type that has
 *     and ICP page will be displayed as an ICP link.</li>
 * <li>{@link #setInvolvedObjects(Set) Involved objects}: The ids of the
 *     objects that will be treated as the Directly Involved Objects
 *     for this transaction.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BTXDetailsHistoryNote extends BTXDetails implements Serializable {
  private static final long serialVersionUID = -5735004982283538032L;

  /**
   * Characters that we don't expect to be part of a BIGR object id (for
   * example a sample or case id).  We use this in the
   * {@link #doGetDetailsAsHTML()} method when scanning the note text for
   * things that look like object ids that we can turn into ICP links.
   */
  private final String OBJECT_ID_DELIMITERS = " \t\n\r\f\"\\~!@#$%^&*()`-={}[]|;:'<>,.?/";

  private String _note = null;
  private Set _involvedObjects = null;

  /**
   * Constructor for BTXDetailsHistoryNote.
   */
  public BTXDetailsHistoryNote() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_HISTORY_NOTE;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    // Copy the _involvedObjects set so that if something ends up
    // modifying the returned set, the original _involvedObjects set
    // won't be changed.

    if (_involvedObjects == null) {
      return new HashSet();
    }
    else {
      return new HashSet(_involvedObjects);
    }
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    // The details are basically just the contents of the getNote()
    // field.  We process it in a few ways, though.  First, we escape
    // any special HTML characters, preserving whitespace.  Then we turn
    // any object ids in the text that could be ICP links into ICP links.

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    String note = Escaper.htmlEscapeAndPreserveWhitespace(ApiFunctions.safeString(getNote()));

    StringBuffer sb = new StringBuffer(2 * note.length() + 1);

    {
      // Scan for things to turn into ICP links.  For efficiency, we
      // don't call prepareLink if the token is shorter than any ICP
      // object id could be.  This keeps us from doing pointless work
      // like passing token-delimiter characters to prepareLink.

      StringTokenizer st = new StringTokenizer(note, OBJECT_ID_DELIMITERS, true);
      while (st.hasMoreTokens()) {
        String token = st.nextToken();

        if (token.length() < TypeFinder.MIN_ICP_ID_LENGTH) {
          sb.append(token);
        }
        else {
          sb.append(IcpUtils.prepareLink(token, securityInfo));
        }
      }
    }

    return sb.toString();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);

    // We don't store the _involvedObjects set in the history record,
    // it's role in the transaction's history is to become the set
    // that will be returned by the getDirectlyInvolvedObjects method.
    //
    history.setClob1(getNote());
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);

    // We don't store the _involvedObjects set in the history record,
    // it's role in the transaction's history is to become the set
    // that will be returned by the getDirectlyInvolvedObjects method.

    setNote(history.getClob1());

    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    //
    setInvolvedObjects(null);
  }

  /**
   * Returns the note.
   * 
   * @return the note
   */
  public String getNote() {
    return _note;
  }

  /**
   * Sets the note.
   * 
   * @param note The note to set
   */
  public void setNote(String note) {
    _note = note;
  }

  /**
   * Returns the set of object ids that are to be considered the
   * directly involved objects for this transaction.  The
   * {@link #getDirectlyInvolvedObjects()} method returns a copy of this
   * set.
   * 
   * @return the set of object ids
   */
  public Set getInvolvedObjects() {
    return _involvedObjects;
  }

  /**
   * Sets the set of object ids that are to be considered the
   * directly involved objects for this transaction.
   * 
   * @param involvedObjects The involvedObjects to set
   */
  public void setInvolvedObjects(Set involvedObjects) {
    _involvedObjects = involvedObjects;
  }
}
