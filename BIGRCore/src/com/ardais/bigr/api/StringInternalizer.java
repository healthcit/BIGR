package com.ardais.bigr.api;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Map;

/**
 * Read data that was written by {@link StringExternalizer}.  See that class
 * for details on what this class is used for and how to use it.
 */
public final class StringInternalizer {
    // This class is most useful when we're trying to reduce
    // a large set of String objects to a smaller set of unique String
    // objects to improve serialization performance and memory use.  So we
    // initialize the HashMap to a fairly large size (a prime number) to
    // minimize rehashing.  If this class ever becomes used for smaller
    // sets of strings for some reason, we might want to provide a
    // constructor that allows the initial size to be specified.
    //
    private Map _handleMap = new HashMap(4001);

    public StringInternalizer() {
        super();
    }

    /**
     * Read a string from the specific object input source.  This is useful
     * implementing custom object externalization.  See
     * {@link StringExternalizer#writeString(ObjectOutput, String)} for details
     * of how the data is written.
     *
     * @param in the object input source to read from
     */
    public String readString(ObjectInput in)
        throws IOException, ClassNotFoundException {

        // IMPORTANT:  When you make changes here, you will most likely need
        // to make corresponding changes in StringExternalizer.writeString.

        String s = null;

        byte typeMarker = in.readByte();
        Integer handle;

        switch (typeMarker) {
            case StringExternalizer.SERIALIZATION_TYPE_NULL :
                s = null;
                break;

            case StringExternalizer.SERIALIZATION_TYPE_HASSTRING :
                handle = new Integer(in.readInt());
                s = in.readUTF();
                _handleMap.put(handle, s);
                break;

            case StringExternalizer.SERIALIZATION_TYPE_NOSTRING :
                handle = new Integer(in.readInt());
                s = (String) _handleMap.get(handle);
                if (s == null) {
                    throw new StreamCorruptedException(
                        "Undefined string handle encountered: "
                            + handle.intValue());
                }
                break;

            default :
                throw new StreamCorruptedException(
                    "Unexpected type marker read: " + typeMarker);
        }

        return s;
    }
}
