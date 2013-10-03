package com.ardais.bigr.iltds.btx;

import com.ardais.bigr.api.ApiFunctions;

/**
 * This class indicates what action should be taken after performing
 * a business transaction.  It is normally created by a session bean that
 * implements a BTX business transaction.  The client that invokes the
 * business transaction uses the information in it to determine how to
 * proceed next, typically by constructing a Struts ActionForward object
 * based on information drawn from the BTXActionForward object.
 * BTXActionForward does not extend the Struts ActionForward class due to
 * the Struts restriction that struts.jar must only be in the local library
 * class path of a web application.
 */
public class BTXActionForward implements java.io.Serializable {
    private static final long serialVersionUID = 4463588804097142557L;

    /**
     * The name of the Struts mapping ActionForward that defines where
     * Struts will forward control following the completion of a business
     * transaction. The target mapping name must be valid for
     * the Struts action that is executing this transaction (either a global
     * forward name or the name of a forward defined locally in that action).
     * This is mutually exclusive with the isRetryActionForward property.
     */
    private String _targetName = null;

    /**
     * When this is true, Struts will forward control to a page where
     * the user can re-enter their input.  This is normally used when a
     * BTX session bean detects invalid user input, and is mutually exclusive
     * with the targetName property.  See {@link #isRetryActionForward()}
     * for more details.
     */
    private boolean _retryActionForward = false;

    /**
     * Construct a new instance with default values.  This constructor really
     * only exists because serialization requires it.
     */
    public BTXActionForward() {
        super();
    }

    /**
     * Construct a new instance with the specified Struts target mapping name.
     *
     * @param targetName URI A Struts ActionForward mapping name.  The target
     *     mapping name must be valid for the Struts action that is executing
     *     this transaction (either a global forward name or the name of a
     *     forward defined locally in that action).
     */
    public BTXActionForward(String targetName) {
        this();
        setTargetName(targetName);
    }

    /**
     * When this is true, Struts will forward control to a page where
     * the user can re-enter their input.  This is normally used when a
     * BTX session bean detects invalid user input, and is mutually exclusive
     * with the targetName property.  The controller will first look for a
     * mapping whose name is the value of the constant
     * BigrActionMapping.BTX_ACTION_FORWARD_RETRY, and if it is
     * present, control will be forwarded there.  Otherwise it will forward
     * control to the mapping's input property.  Finally, if the mapping
     * doesn't have an input property a runtime exception will be thrown.
     * 
     * @return true if this is a retry action forward
     */
    public boolean isRetryActionForward() {
        return _retryActionForward;
    }

    /**
     * Return the name of the Struts mapping ActionForward that defines where
     * Struts will forward control following the completion of a business
     * transaction. The target mapping name must be valid for
     * the Struts action that is executing this transaction (either a global
     * forward name or the name of a forward defined locally in that action).
     * This is mutually exclusive with the isRetryActionForward property.
     * 
     * @return the Struts ActionForward mapping name
     */
    public String getTargetName() {
        return _targetName;
    }

    /**
     * Sets the retryActionForward.  When this is true, Struts will forward
     * control to a page where the user can re-enter their input.  This is
     * normally used when a BTX session bean detects invalid user input, and
     * is mutually exclusive with the targetName property.  The controller
     * will first look for a mapping whose name is the value of the constant
     * BigrActionMapping.BTX_ACTION_FORWARD_RETRY, and if it is present,
     * control will be forwarded there.  Otherwise it will forward control to
     * the mapping's input property.  Finally, if the mapping doesn't have an
     * input property a runtime exception will be thrown.
     * 
     * <p>A runtime exception will be thrown if you try to set this to true
     * when getTargetName() is non-empty.
     * 
     * @param retryActionForward The retryActionForward to set
     */
    public void setRetryActionForward(boolean retryActionForward) {
        String targetName = getTargetName();

        if ((!ApiFunctions.isEmpty(targetName)) && retryActionForward) {
            throw new IllegalArgumentException(
                "You cannot set retryActionForward to true when a targetName is also specified: "
                    + targetName);
        }

        _retryActionForward = retryActionForward;
    }

    /**
     * Set the name of the Struts mapping ActionForward that defines where
     * Struts will forward control following the completion of a business
     * transaction. The target mapping name must be valid for
     * the Struts action that is executing this transaction (either a global
     * forward name or the name of a forward defined locally in that action).
     * This is mutually exclusive with the isRetryActionForward property
     * and a runtime exception will be thrown if you try to set the target
     * name to a non-null value when isRetryActionForward() is true.
     * 
     * @return the Struts ActionForward mapping name
     */
    public void setTargetName(String targetName) {
        if (isRetryActionForward() && (!ApiFunctions.isEmpty(targetName))) {
            throw new IllegalArgumentException(
                "You cannot set a target name when isRetryActionForward is true: "
                    + targetName);
        }

        _targetName = targetName;
    }

}
