package com.ardais.bigr.api;

import com.ardais.bigr.api.*;
import java.util.*;

/**
 * This class is used to convert String objects to a String object instance
 * that uniquely represents that String.  For example:
 * <pre>
 * StringCanonicalizer canonicalizer = new StringCanonicalizer();
 * String a1 = new String("a");
 * String a2 = new String("a");
 * String canonicalA1 = canonicalizer.intern(a1);
 * String canonicalA2 = canonicalizer.intern(a2);
 * // At this point a1.equals(a2), a1 != a2, but canonicalA1 == canonicalA2.
 * </pre>
 *
 * The general rule is that for Strings x and y, intern(x) == intern(y)
 * (that is, they are the exact same Java object instance) if and only if
 * x and y are both null or x.equals(y).  This is true only when the
 * intern methods were called on the same StringCanonicalizer instance.
 * <p>
 * StringCanonicalizer works by maining an internal mapping of Strings
 * that have been passed to the intern method to the String instance that
 * uniquely represents that String.  When intern is called, it first checks
 * to see if the string passed to it is already in the mapping, and if so
 * it returns the previously-interned string instance.  Otherwise it adds
 * the string to the mapping and returns it (that string instance will then
 * become the unique instance to represent that string on future calls to
 * intern).
 * <p>
 * This class can be useful to conserve time or space in several situations.
 * For example, suppose that you have a large set of objects, and that
 * the same strings are likely to appear many times in this objects.  An
 * example would be a list of data-bean objects returned from a database
 * query.  In such a situation, the same diagnosis, tissue, appearance, etc
 * appear many times in the list, and most likely each one is represented
 * by a different String object instance.  This can use a lot or memory, and
 * you can improve memory use by using a StringCanonicalizer when building your
 * data bean objects so that strings that are the same are each represented
 * by only a single string instance.
 * <p>
 * There's also a performance benefit when such large lists are serialized.
 * If all of the strings that are the same are actually represented by the
 * same object instance:  serialization only writes the string contents
 * once, do the serialized data stream is smaller and can be much faster to
 * serialize and deserialize.  This situation arises, for example, when the
 * list of objects is passed to to from an EJB method.
 */
public final class StringCanonicalizer {
	// The StringCanonicalizer is most useful when we're trying to reduce
	// a large set of String objects to a smaller set of unique String
	// object to improve serialization performance and memory use.  So we
	// initialize the HashMap to a fairly large size (a prime number) to
	// minimize rehashing.  If this class ever becomes used for smaller
	// sets of strings for some reason, we might want to provide a
	// constructor that allows the initial size to be specified.
	//
	private Map _symbolTable = new HashMap(4001);
public StringCanonicalizer() {
	super();
}
/**
 * See the class documentation ({@link StringCanonicalizer}) for a
 * description of what this method does and how to use it.
 *
 * @param s the string to canonicalize
 * @return the string instance that uniquely represents the given string
 */
public String intern(String s) {
	if (s == null) {
		return null;
	}
	else {
		String canonicalString = (String) _symbolTable.get(s);
		if (canonicalString == null) {
			canonicalString = s;
			_symbolTable.put(s, s);
		}
		return canonicalString;
	}
}
}
