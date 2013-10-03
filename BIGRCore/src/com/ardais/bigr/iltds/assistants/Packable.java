package com.ardais.bigr.iltds.assistants;

/**
 * Objects that implement this interface are able to convert themselves
 * to and from a machine-readable string representation.  There interface
 * does not define what that representation is, that is left up to each
 * implementing class.  The process of converting to the string representation
 * is called <code>packing</code>, and the process of setting an object's
 * state from a packed string is called <code>unpacking</code>.
 * <p>
 * Implementations must ensure that packing an object and then unpacking
 * it into another object of the same type sets the second object's
 * internal state to exactly the state the original object had when it
 * was packed.
 * <p>
 * This interface is intended to be used in situations when simple objects
 * need to be converted to a machine-readble string, perhaps for storing
 * in a database column.  It is not intended to be a replacement for
 * Java's serialization mechanism (see {@link java.io.Serializable}).
 */
public interface Packable {

  /**
   * Return a packed data string representing the contents of the object.
   *
   * @return the packed data string.
   */
  String pack();

  /**
   * Given a string of packed data, unpack the data into the object.
   * The result is undefined if that packed data is not in the packed
   * format that this object expects.
   *
   * @param packedData the data to unpack
   */
  void unpack(String packedData);
}
