package com.ardais.bigr.iltds.helpers;

import java.util.*;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;

/**
 */
public class MessageHelper {
	
	// Holds the list of messages, which are all assumed to
	// be of type String.
	private ArrayList _msgs;

	// A flag to indicate whether the message indicates an
	// error or not.  This will determine how the message
	// is displayed.
	private boolean _isError = false;
/**
 * Creates a new <code>MessageHelper</code>.
 */
public MessageHelper() {
	super();
}
/**
 * Adds a message to this <code>MessageHelper</code>.
 * The message is added to the end of an ordered list.
 *
 * @param  msg  the message
 */
public void addMessage(String msg) {
	if (_msgs == null) {
		_msgs = new ArrayList();
	}
	_msgs.add(msg);
}
/**
 * Adds a message to this <code>MessageHelper</code>.
 * The message is added to the end of an ordered list.
 *
 * @param  msg  the message
 */
public void addMessages(MessageHelper msgHelper) {
  if (_msgs == null) {
    _msgs = new ArrayList();
  }
  if (!ApiFunctions.isEmpty(msgHelper._msgs)) {
    Iterator iterator = msgHelper._msgs.iterator();
    while (iterator.hasNext()) {
      _msgs.add(iterator.next());
    }
  }
}
/**
 * Returns HTML that contains all messages appropriate for
 * display in BIGR.  If there are no messages in the 
 * <code>MessageHelper</code> then the empty string is returned.
 *
 * @return  The HTML containing the messages.
 */
public String getMessages() {
	return getMessages(-1);
}
/**
 * Returns HTML that contains all messages appropriate for
 * display in BIGR.  <code>includeAlert</code> specifies if an alert is displayed
 * containing the messages.  If there are no messages in the 
 * <code>MessageHelper</code> then the empty string is returned.
 *
 * @return  The HTML containing the messages.
 */

public String getMessages(boolean includeAlert) {
  return getMessages(-1, includeAlert);
}
/**
 * Returns HTML that contains all messages appropriate for
 * display in BIGR.  <code>colspan</code> specifies the colspan 
 * of the single TD within the TR that displays the messages.  If
 * colspan is less than or equal to zero, then the colspan attribute
 * is not used in the TD.  If there are no messages in the 
 * <code>MessageHelper</code> then the empty string is returned.
 *
 * @return  The HTML containing the messages.
 */
public String getMessages(int colspan) {
  return getMessages(colspan, true);
}
/**
 * Returns HTML that contains all messages appropriate for
 * display in BIGR.  <code>colspan</code> specifies the colspan 
 * of the single TD within the TR that displays the messages.  If
 * colspan is less than or equal to zero, then the colspan attribute
 * is not used in the TD.  <code>includeAlert</code> specifies if an alert is displayed
 * containing the messages.  If there are no messages in the 
 * <code>MessageHelper</code> then the empty string is returned.
 *
 * @return  The HTML containing the messages.
 */

public String getMessages(int colspan, boolean includeAlert) {
  if ((_msgs == null) || (_msgs.size() == 0)) {
    return "";
  }
  else {
    StringBuffer buf = new StringBuffer(256);
    StringBuffer msgs = new StringBuffer(256);
    buf.append("<tr class=\"yellow\"><td");
    if (colspan > 0) {
      buf.append(" colspan=\"");
      buf.append(String.valueOf(colspan));
      buf.append("\"");
    }
    buf.append('>');
    if (_isError) {
      buf.append("<div align=\"left\"><font color=\"#FF0000\">");
      buf.append("The following problems were encountered:");
    }
    else {
      buf.append("<div align=\"center\"><font color=\"#FF0000\">");
    }

    int numMsgs = _msgs.size();
    //if (_isError) {
      buf.append("<ul style=\"margin-top: .25em; margin-bottom: .25em;\">");
      for (int i = 0; i < numMsgs; i++) {
        buf.append("<li>");
        buf.append(_msgs.get(i));
        buf.append("</li>");
        msgs.append(Escaper.jsEscapeInScriptTag((String)_msgs.get(i)));
        msgs.append("\\n\\r");
      }   
      buf.append("</ul>");
    //}
    //else {
      //buf.append(_msgs.get(0));
      //msgs.append(_msgs.get(0));
    //}
    
    buf.append("</font></div></td></tr>");
    if (includeAlert) {
      buf.append("<script language=\"JavaScript\">alert(\"");
      buf.append(msgs.toString());
      buf.append("\")</script>");
    }
    return buf.toString();
  }
  
}

/**
 * Sets whether this message represents an error message or
 * not.
 *
 * @param  error  <code>true</code> if this is an error message
 *		<code>false</code> otherwise.
 */
public void setError(boolean error) {
	_isError = error;
}
}
