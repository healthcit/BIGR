package com.ardais.bigr.btx.framework;

/**
 * @author gyost
 *
 * This class represents the result of encoding a History Object
 * (see {@link BtxHistoryObjectUtils}) as a string.  The encodingScheme property represents
 * the encoding scheme used, and the encodedObject property represents the string encoding
 * of the history object.
 */
public class BtxHistoryObjectEncoding {
  /**
   * Represent a null encoding.  All fields of the encoding are null, including the encoding
   * scheme.  It is important that these be null in this case, as we write this information
   * into database columns in BTXHistoryRecorderBean in situations where we want the database
   * columns to end up with null in them.
   */
  public static final BtxHistoryObjectEncoding NULL_ENCODING =
    new BtxHistoryObjectEncoding(null, null);

  private String _encodingScheme = null;
  private String _encodedObject = null;

  public BtxHistoryObjectEncoding() {
    super();
  }

  public BtxHistoryObjectEncoding(String encodingScheme, String encodedObject) {
    this();

    setEncodingScheme(encodingScheme);
    setEncodedObject(encodedObject);
  }

  /**
   * @return the String representation of the history object.
   */
  public String getEncodedObject() {
    return _encodedObject;
  }

  /**
   * @return the scheme by which the history object is encoded as a string.
   */
  public String getEncodingScheme() {
    return _encodingScheme;
  }

  /**
   * @param encodedObject the String representation of the history object.
   */
  public void setEncodedObject(String encodedObject) {
    _encodedObject = encodedObject;
  }

  /**
   * @param scheme the scheme by which the history object is encoded as a string.
   */
  public void setEncodingScheme(String scheme) {
    _encodingScheme = scheme;
  }

}
