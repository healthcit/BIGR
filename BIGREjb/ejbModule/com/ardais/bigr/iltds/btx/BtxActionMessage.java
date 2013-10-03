package com.ardais.bigr.iltds.btx;

/**
 * An encapsulation of an individual message being reported in
 * the course of performing a business transaction, consisting
 * of a message key (to be used to look up message text in an appropriate
 * Struts message resources database) plus up to four placeholder objects that
 * can be used for parametric replacement in the message text.
 *
 * <p>The placeholder objects are referenced in the message text using the same
 * syntax used by the JDK <code>MessageFormat</code> class. Thus, the first
 * placeholder is '{0}', the second is '{1}', etc.</p>
 * 
 * <p>See the <code>com.ardais.bigr.iltds.btx.BtxActionError</code> class for details.</p>
 */
public class BtxActionMessage extends BtxActionError {

  /**
   * Constructor for BtxActionMessage.
   * @param key
   */
  public BtxActionMessage(String key) {
    super(key);
  }

  /**
   * Constructor for BtxActionMessage.
   * @param key
   * @param value0
   */
  public BtxActionMessage(String key, String value0) {
    super(key, value0);
  }

  /**
   * Constructor for BtxActionMessage.
   * @param key
   * @param value0
   * @param value1
   */
  public BtxActionMessage(String key, String value0, String value1) {
    super(key, value0, value1);
  }

  /**
   * Constructor for BtxActionMessage.
   * @param key
   * @param value0
   * @param value1
   * @param value2
   */
  public BtxActionMessage(String key, String value0, String value1, String value2) {
    super(key, value0, value1, value2);
  }

  /**
   * Constructor for BtxActionMessage.
   * @param key
   * @param value0
   * @param value1
   * @param value2
   * @param value3
   */
  public BtxActionMessage(String key, String value0, String value1, String value2, String value3) {
    super(key, value0, value1, value2, value3);
  }

}
