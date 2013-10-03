package com.ardais.bigr.api;

import java.io.*;
import com.ardais.bigr.api.*;
import java.util.*;

/**
 * This class is useful in some of our custom externalization methods to
 * increase the time/space efficiency of externalization and de-externalization.
 * <p>
 * It works by keeping track of strings that have already been written by
 * associating them with a unique integer handle.  The actual string contents
 * are written only once, thereafter only the string handle is written.
 * String handles are unique only within a given StringExternalizer instance,
 * so incorrect behavior will result if you use multiple StringExternalizer
 * objects to write your output and then read them all in using the same
 * StringInternalizer object.
 * <p>
 * All of the work is done with the {@link #writeString(ObjectOutput, String) writeString}
 * method.
 * <p>
 * This class goes hand-in-hand with {@link StringInternalizer}, which is the
 * class to use to read data that is written by StringExternalizer.
 */
public final class StringExternalizer {
	public static final int SERIALIZATION_TYPE_NULL = 0;
	public static final int SERIALIZATION_TYPE_HASSTRING = 1;
	public static final int SERIALIZATION_TYPE_NOSTRING = 2;

	// This class is most useful when we're trying to reduce
	// a large set of String objects to a smaller set of unique String
	// objects to improve serialization performance and memory use.  So we
	// initialize the HashMap to a fairly large size (a prime number) to
	// minimize rehashing.  If this class ever becomes used for smaller
	// sets of strings for some reason, we might want to provide a
	// constructor that allows the initial size to be specified.
	//
	private Map _handleMap = new HashMap(4001);

	private int _nextHandle = Integer.MIN_VALUE;
public StringExternalizer() {
	super();
}
/**
 * Write a string to the specific object output destination.  If the string
 * already has a handle mapping, only the handle is written, not the string
 * contents.  This is useful implementing custom object externalization.
 *
 * @param out the object output destination to write to
 * @param s the string to write
 */
public void writeString(ObjectOutput out, String s) throws IOException {
	// s can be null, it is treated specially and doesn't get assigned a handle.

	// IMPORTANT:  When you make changes here, you will most likely need
	// to make corresponding changes in StringInternalizer.readString.

	if (s == null) {
		out.writeByte(SERIALIZATION_TYPE_NULL);
	}
	else {
		boolean mustWriteString = false;
		
		Integer handle = (Integer) _handleMap.get(s);
		
		if (handle == null) {
			handle = new Integer(_nextHandle);
			_nextHandle++;
			_handleMap.put(s, handle);
			
			mustWriteString = true;
			
			out.writeByte(SERIALIZATION_TYPE_HASSTRING);
		}
		else {
			out.writeByte(SERIALIZATION_TYPE_NOSTRING);
		}
		
		out.writeInt(handle.intValue());
		
		if (mustWriteString) {
			out.writeUTF(s);
		}
	}
}
}
