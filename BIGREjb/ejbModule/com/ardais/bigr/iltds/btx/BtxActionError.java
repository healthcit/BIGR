package com.ardais.bigr.iltds.btx;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * An encapsulation of an individual error message being reported in
 * the course of performing a business transaction, consisting
 * of a message key (to be used to look up message text in an appropriate
 * Struts message resources database) plus up to four placeholder objects that
 * can be used for parametric replacement in the message text.
 *
 * <p>The placeholder objects are referenced in the message text using the same
 * syntax used by the JDK <code>MessageFormat</code> class. Thus, the first
 * placeholder is '{0}', the second is '{1}', etc.</p>
 * 
 * <p>This class mirrors the Struts <code>ActionError</code> class.
 * We can't use the <code>ActionError</code> class directly in the BTX
 * framework, since the Struts classes are designed to be used only within
 * a web application and not, for example, in an EJB component.  The BTX
 * framework provides a way to construct a Struts <code>ActionError</code>
 * object that represents the same errors as a given
 * <code>BtxActionError</code> object.</p>
 * 
 * <p><code>BtxActionError</code> objects are serialized fromthe EJB server
 * to the EJB client when a business transaction returns its results.
 * To prevent developers from inadvertently passing complex objects
 * as replacement parameters to the <code>BtxActionError</code> constructors,
 * this class explicitly requires replacement parameters to be strings,
 * not generic objects as in the Struts <code>ActionError</code> class.
 *
 * <p>Since the mapping of message keys to actual messages is maintained by
 * Struts, a <code>BtxActionError</code> object can only be converted to its
 * final message text with web application code (not elsewhere, such as EJB
 * code).</p>
 */
public class BtxActionError implements Serializable {
    // NOTE: The code for this class is derived directly from the Struts
    // ActionError source code, with all due credit to the Struts authors.
    // It has not been rewritten to use Ardais coding conventions.
    // It has been modified in some ways, for example to add "hashCode" and
    // "equals" implementations.

    // ----------------------------------------------------------- Constructors

    /**
     * Construct an action error with no replacement values.
     *
     * @param key Message key for this error message
     */
    public BtxActionError(String key) {

        this(key, null, null, null, null);

    }

    /**
     * Construct an action error with the specified replacement values.
     *
     * @param key Message key for this error message
     * @param value0 First replacement value
     */
    public BtxActionError(String key, String value0) {

        this(key, value0, null, null, null);

    }

    /**
     * Construct an action error with the specified replacement values.
     *
     * @param key Message key for this error message
     * @param value0 First replacement value
     * @param value1 Second replacement value
     */
    public BtxActionError(String key, String value0, String value1) {

        this(key, value0, value1, null, null);

    }

    /**
     * Construct an action error with the specified replacement values.
     *
     * @param key Message key for this error message
     * @param value0 First replacement value
     * @param value1 Second replacement value
     * @param value2 Third replacement value
     */
    public BtxActionError(
        String key,
        String value0,
        String value1,
        String value2) {

        this(key, value0, value1, value2, null);

    }

    /**
     * Construct an action error with the specified replacement values.
     *
     * @param key Message key for this error message
     * @param value0 First replacement value
     * @param value1 Second replacement value
     * @param value2 Third replacement value
     * @param value3 Fourth replacement value
     */
    public BtxActionError(
        String key,
        String value0,
        String value1,
        String value2,
        String value3) {

        super();
        this.key = key;
        values[0] = value0;
        values[1] = value1;
        values[2] = value2;
        values[3] = value3;

    }

    // ----------------------------------------------------- Instance Variables

    /**
     * The message key for this error message.
     */
    private String key = null;

    /**
     * The replacement values for this error mesasge.
     */
    private String values[] = { null, null, null, null };

    // --------------------------------------------------------- Public Methods

    /**
     * Get the message key for this error message.
     */
    public String getKey() {

        return (this.key);

    }

    /**
     * Get the replacement values for this error message.
     */
    public String[] getValues() {

        return (this.values);

    }

    /**
     * Determine if this object is equal to another object.
     * Two BtxActionError objects are equals if their keys and all of their
     * replacement parameters are equals.
     * 
     * @param obj the object to compare to
     * @return true if this object is equal to the specified object
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        else if (!(obj instanceof BtxActionError)) {
            return false;
        }
        else {
            BtxActionError o2 = (BtxActionError) obj;

            // Are the keys non-equals?
            if (key == null) {
                if (o2.key != null) {
                    return false;
                }
            }
            else if (!key.equals(o2.key)) {
                return false;
            }

            // Are the replacement values non-equals?
            if (values.length != o2.values.length) {
                return false;
            }
            for (int i = 0; i < values.length; i++) {
                String v1 = values[i];
                String v2 = o2.values[i];
                if (v1 == null) {
                    if (v2 != null) {
                        return false;
                    }
                }
                else if (!v1.equals(v2)) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * Return a hash code that is consistent with the
     * {@link #equals(Object) equals} method in that it depends only on the
     * same set of features that <code>equals</code> depends on.
     * 
     * @return the hash code
     */
    public int hashCode() {
        // HashCodeBuilder is a class in
        // org.apache.commons.lang.builder that generates good hash codes
        // that comply with the guidelines Joshua Bloch lays out in
        // "Effective Java: Programming Language Guide".
        //
        // If you're copying code from here to other hashCode implementations,
        // please read the Javadoc for HashCodeBuilder first, and ideally
        // Bloch's book as well.  There are a lot of guidelines that need
        // to be followed to create a hashCode function that obeys the
        // general contract it must observe and still yields good hash
        // distributions.  One thing to note is that the multiplier and
        // initialValue constants must both be odd, and ideally the
        // multiplier should be a prime number.

        return new HashCodeBuilder(103, 77)
            .append(key)
            .append(values)
            .toHashCode();
    }

    public String toString() {
        return key
            + ": "
            + values[0]
            + ", "
            + values[1]
            + ", "
            + values[2]
            + ", "
            + values[3];
    }

}
