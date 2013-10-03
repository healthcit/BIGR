package com.ardais.bigr.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.iltds.helpers.TypeFinder;

/**
 * @author sthomashow
 *
 * class that is used to validate an ID in the Ardais BIGR system
 * currently supported classes include:
 *  linked and unlinked cases
 *  linked and unlinked ardais ids
 *  frozen and paraffin samples
 *  frozen and paraffin slides
 *  manifests
 *  asms
 *  boxes
 *  policy
 *  form definition
 */
public class ValidateIds {

  private static final ValidateIds SINGLETON = new ValidateIds();
  private static final int PREFIX_LENGTH_MIN = 2;
  private static final int PREFIX_LENGTH_MAX = 4;

  // NOTE: When you add new constants here, you must also create a map entry for
  // them in the initialize() method.  Also, create a TYPESET_* constant for use
  // as a parameter to the validate(String, Set) method.
  //
  public final static String PREFIX_CASE_UNLINKED = "CU";
  public final static String PREFIX_CASE_LINKED = "CI";
  public final static String PREFIX_CASE_IMPORTED = "CX";
  public final static String PREFIX_MANIFEST = "MNFT";
  public final static String PREFIX_SAMPLE_FROZEN = "FR";
  public final static String PREFIX_SAMPLE_PARAFFIN = "PA";
  public final static String PREFIX_SAMPLE_CUSTFROZEN = "CF";
  public final static String PREFIX_SAMPLE_CUSTPARAFFIN = "CP";
  public final static String PREFIX_SAMPLE_IMPORTED = "SX";
  public final static String PREFIX_ASM = "ASM";
  public final static String PREFIX_ASM_IMPORTED = "ASX";
  public final static String PREFIX_BOX = "BX";
  public final static String PREFIX_DONOR_LINKED = "AI";
  public final static String PREFIX_DONOR_UNLINKED = "AU";
  public final static String PREFIX_DONOR_IMPORTED = "AX";
  public final static String PREFIX_SLIDE_FROZEN = "SF";
  public final static String PREFIX_SLIDE_PARAFFIN = "SP";
  public final static String PREFIX_SLIDE = "SL";
  public final static String PREFIX_LOGICAL_REPOSITORY = "IG"; // IG for Inventory Group
  public final static String PREFIX_POLICY = "PY"; // PY for Policy
  public final static String PREFIX_REQUEST = "RQ";
  public final static String PREFIX_BOX_LAYOUT = "LY";
  public final static String PREFIX_FORMDEFINITION = "FD";
  public final static String PREFIX_ROLE = "RL";
  
  public final static Set TYPESET_CASE_LINKED =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_CASE_LINKED })));
  public final static Set TYPESET_CASE_UNLINKED =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_CASE_UNLINKED })));
  public final static Set TYPESET_CASE_IMPORTED =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_CASE_IMPORTED })));
  public final static Set TYPESET_CASE_WITHOUT_IMPORTED =
    Collections.unmodifiableSet(
      new HashSet(Arrays.asList(new String[] { PREFIX_CASE_LINKED, PREFIX_CASE_UNLINKED })));
  public final static Set TYPESET_CASE =
    Collections.unmodifiableSet(
      new HashSet(
        Arrays.asList(
          new String[] { PREFIX_CASE_LINKED, PREFIX_CASE_UNLINKED, PREFIX_CASE_IMPORTED })));
  public final static Set TYPESET_MANIFEST =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_MANIFEST })));
  public final static Set TYPESET_SAMPLE_FROZEN =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_SAMPLE_FROZEN })));
  public final static Set TYPESET_SAMPLE_PARAFFIN =
    Collections.unmodifiableSet(
      new HashSet(Arrays.asList(new String[] { PREFIX_SAMPLE_PARAFFIN })));
  public final static Set TYPESET_SAMPLE_CUSTFROZEN =
    Collections.unmodifiableSet(
      new HashSet(Arrays.asList(new String[] { PREFIX_SAMPLE_CUSTFROZEN })));
  public final static Set TYPESET_SAMPLE_CUSTPARAFFIN =
    Collections.unmodifiableSet(
      new HashSet(Arrays.asList(new String[] { PREFIX_SAMPLE_CUSTPARAFFIN })));
  public final static Set TYPESET_SAMPLE_IMPORTED =
    Collections.unmodifiableSet(
      new HashSet(Arrays.asList(new String[] { PREFIX_SAMPLE_IMPORTED })));
  public final static Set TYPESET_SAMPLE_WITHOUT_IMPORTED =
    Collections.unmodifiableSet(
      new HashSet(Arrays.asList(new String[] { PREFIX_SAMPLE_FROZEN, PREFIX_SAMPLE_PARAFFIN })));
  public final static Set TYPESET_SAMPLE_INCLUDE_CUSTOM =
    Collections.unmodifiableSet(
      new HashSet(
        Arrays.asList(
          new String[] {
            PREFIX_SAMPLE_FROZEN,
            PREFIX_SAMPLE_PARAFFIN,
            PREFIX_SAMPLE_CUSTFROZEN,
            PREFIX_SAMPLE_CUSTPARAFFIN })));
  public final static Set TYPESET_SAMPLE =
    Collections.unmodifiableSet(
      new HashSet(
        Arrays.asList(
          new String[] { PREFIX_SAMPLE_FROZEN, PREFIX_SAMPLE_PARAFFIN, PREFIX_SAMPLE_IMPORTED })));
  public final static Set TYPESET_ASM =
    Collections.unmodifiableSet(
      new HashSet(Arrays.asList(new String[] { PREFIX_ASM, PREFIX_ASM_IMPORTED })));
  public final static Set TYPESET_ASM_WITHOUT_IMPORTED =
    Collections.unmodifiableSet(
      new HashSet(Arrays.asList(new String[] { PREFIX_ASM })));            
  public final static Set TYPESET_ASM_IMPORTED =
    Collections.unmodifiableSet(
      new HashSet(Arrays.asList(new String[] { PREFIX_ASM_IMPORTED })));      
  public final static Set TYPESET_BOX =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_BOX })));
  public final static Set TYPESET_DONOR_LINKED =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_DONOR_LINKED })));
  public final static Set TYPESET_DONOR_UNLINKED =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_DONOR_UNLINKED })));
  public final static Set TYPESET_DONOR_IMPORTED =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_DONOR_IMPORTED })));
  public final static Set TYPESET_DONOR_WITHOUT_IMPORTED =
    Collections.unmodifiableSet(
      new HashSet(Arrays.asList(new String[] { PREFIX_DONOR_UNLINKED, PREFIX_DONOR_LINKED })));
  public final static Set TYPESET_DONOR =
    Collections.unmodifiableSet(
      new HashSet(
        Arrays.asList(
          new String[] { PREFIX_DONOR_UNLINKED, PREFIX_DONOR_LINKED, PREFIX_DONOR_IMPORTED })));
  public final static Set TYPESET_SLIDE_FROZEN =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_SLIDE_FROZEN })));
  public final static Set TYPESET_SLIDE_PARAFFIN =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_SLIDE_PARAFFIN })));
  public final static Set TYPESET_SLIDE =
    Collections.unmodifiableSet(
      new HashSet(Arrays.asList(new String[] { PREFIX_SLIDE, PREFIX_SLIDE_FROZEN, PREFIX_SLIDE_PARAFFIN })));
  public final static Set TYPESET_LOGICAL_REPOSITORY =
    Collections.unmodifiableSet(
      new HashSet(Arrays.asList(new String[] { PREFIX_LOGICAL_REPOSITORY })));
  public final static Set TYPESET_POLICY =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_POLICY })));
  public final static Set TYPESET_REQUEST =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_REQUEST })));
  public final static Set TYPESET_BOX_LAYOUT =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_BOX_LAYOUT })));
  public final static Set TYPESET_SAMPLE_AND_SLIDE =
    Collections.unmodifiableSet(
      new HashSet(
        Arrays.asList(
          new String[] { PREFIX_SAMPLE_FROZEN, PREFIX_SAMPLE_PARAFFIN, PREFIX_SAMPLE_IMPORTED, PREFIX_SLIDE, PREFIX_SLIDE_FROZEN, PREFIX_SLIDE_PARAFFIN })));
  public final static Set TYPESET_SAMPLE_AND_SLIDE_WITHOUT_IMPORTED =
    Collections.unmodifiableSet(
      new HashSet(
        Arrays.asList(
          new String[] { PREFIX_SAMPLE_FROZEN, PREFIX_SAMPLE_PARAFFIN, PREFIX_SLIDE_FROZEN, PREFIX_SLIDE_PARAFFIN })));
  public final static Set TYPESET_FORMDEFINITION =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_FORMDEFINITION })));
  public final static Set TYPESET_ROLE =
    Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { PREFIX_ROLE })));

  /**
   * This is the set of all id types that ICP supports.
   */
  public final static Set TYPESET_ICP; // Initialized in the static initialization block.

  public final static int SUCCESS = 0;
  public final static int INVALID_CATEGORY = 1;
  public final static int INVALID_LENGTH = 2;
  public final static int INVALID_CHARS = 3;
  public final static int INVALID_BLANK = 4;

  // NOTE: When you add new constants here, you must also create a map entry for
  // them in the initialize() method.
  //
  public final static int LENGTH_CASE_ID = 12;
  public final static int LENGTH_MANIFEST_ID = 14;
  public final static int LENGTH_SAMPLE_ID = 10;
  public final static int LENGTH_ASM_ID = 12;
  public final static int LENGTH_BOX_ID = 12;
  public final static int LENGTH_DONOR_ID = 12;
  public final static int LENGTH_SLIDE_ID = 10;
  public final static int LENGTH_LOGICAL_REPOSITORY_ID = 10;
  public final static int LENGTH_POLICY_ID = 10;
  public final static int LENGTH_REQUEST_ID = 10;
  public final static int LENGTH_BOX_LAYOUT_ID = 8;
  public final static int LENGTH_FORMDEFINITION_ID = 10;
  public final static int LENGTH_ROLE_ID = 10;
  
  // NOTE: When you add new constants here, you must also create a map entry for
  // them in the initialize() method.
  //
  private final static boolean HEX_CASE_FLAG = false;
  private final static boolean HEX_MANIFEST_FLAG = false;
  private final static boolean HEX_SAMPLE_FLAG = true;
  private final static boolean HEX_ASM_FLAG = false;
  private final static boolean HEX_BOX_FLAG = false;
  private final static boolean HEX_DONOR_FLAG = false;
  private final static boolean HEX_SLIDE_FLAG = true;
  private final static boolean HEX_LOGICAL_REPOSITORY_FLAG = false;
  private final static boolean HEX_POLICY_FLAG = false;
  private final static boolean HEX_REQUEST_FLAG = false;
  private final static boolean HEX_BOX_LAYOUT_FLAG = false;
  private final static boolean HEX_FORMDEFINITION_FLAG = false;
  private final static boolean HEX_ROLE_FLAG = true;
  
  private int _errValue;
  private String _type;
  private static HashMap _idTable = new HashMap();

  private static boolean _isInitialized = false;

  static {
    TYPESET_ICP = new HashSet(TYPESET_CASE);
    TYPESET_ICP.addAll(TYPESET_SAMPLE);
    TYPESET_ICP.addAll(TYPESET_BOX);
    TYPESET_ICP.addAll(TYPESET_LOGICAL_REPOSITORY);
    TYPESET_ICP.addAll(TYPESET_POLICY);
    TYPESET_ICP.addAll(TYPESET_MANIFEST);
    TYPESET_ICP.addAll(TYPESET_REQUEST);
    TYPESET_ICP.addAll(TYPESET_DONOR);
    TYPESET_ICP.addAll(TYPESET_BOX_LAYOUT);
    TYPESET_ICP.addAll(TYPESET_FORMDEFINITION);
    TYPESET_ICP.addAll(TYPESET_ROLE);
    
    initialize();
  }

  /**
   * Constructor for ValidateIds.
   */
  public ValidateIds() {
    super();
  }

  /**
    * The first time this is called it initializes the static members of
    * this class.  Subsequent calls do nothing.
    */
  private static synchronized void initialize() {
    if (_isInitialized)
      return;

    // load the HashMap, _idTable
    // The keys on the hashtable entries must have exactly two characters.  See determineIdCategory.

    _idTable.put(
      PREFIX_CASE_UNLINKED,
      new SystemIds(PREFIX_CASE_UNLINKED, LENGTH_CASE_ID, HEX_CASE_FLAG));

    _idTable.put(
      PREFIX_CASE_LINKED,
      new SystemIds(PREFIX_CASE_LINKED, LENGTH_CASE_ID, HEX_CASE_FLAG));

    _idTable.put(
      PREFIX_CASE_IMPORTED,
      new SystemIds(PREFIX_CASE_IMPORTED, LENGTH_CASE_ID, HEX_CASE_FLAG));

    _idTable.put(
      PREFIX_MANIFEST,
      new SystemIds(PREFIX_MANIFEST, LENGTH_MANIFEST_ID, HEX_MANIFEST_FLAG));

    _idTable.put(PREFIX_ASM, new SystemIds(PREFIX_ASM, LENGTH_ASM_ID, HEX_ASM_FLAG));

    _idTable.put(
      PREFIX_ASM_IMPORTED,
      new SystemIds(PREFIX_ASM_IMPORTED, LENGTH_ASM_ID, HEX_ASM_FLAG));

    _idTable.put(PREFIX_BOX, new SystemIds(PREFIX_BOX, LENGTH_BOX_ID, HEX_BOX_FLAG));

    _idTable.put(
      PREFIX_DONOR_UNLINKED,
      new SystemIds(PREFIX_DONOR_UNLINKED, LENGTH_DONOR_ID, HEX_DONOR_FLAG));

    _idTable.put(
      PREFIX_DONOR_LINKED,
      new SystemIds(PREFIX_DONOR_LINKED, LENGTH_DONOR_ID, HEX_DONOR_FLAG));

    _idTable.put(
      PREFIX_DONOR_IMPORTED,
      new SystemIds(PREFIX_DONOR_IMPORTED, LENGTH_DONOR_ID, HEX_DONOR_FLAG));

    _idTable.put(
      PREFIX_SLIDE_FROZEN,
      new SystemIds(PREFIX_SLIDE_FROZEN, LENGTH_SLIDE_ID, HEX_SLIDE_FLAG));

    _idTable.put(
      PREFIX_SLIDE_PARAFFIN,
      new SystemIds(PREFIX_SLIDE_PARAFFIN, LENGTH_SLIDE_ID, HEX_SLIDE_FLAG));

    _idTable.put(
      PREFIX_SLIDE,
      new SystemIds(PREFIX_SLIDE, LENGTH_SLIDE_ID, HEX_SLIDE_FLAG));

    _idTable.put(
      PREFIX_LOGICAL_REPOSITORY,
      new SystemIds(
        PREFIX_LOGICAL_REPOSITORY,
        LENGTH_LOGICAL_REPOSITORY_ID,
        HEX_LOGICAL_REPOSITORY_FLAG));

    _idTable.put(PREFIX_POLICY, new SystemIds(PREFIX_POLICY, LENGTH_POLICY_ID, HEX_POLICY_FLAG));

    _idTable.put(
      PREFIX_REQUEST,
      new SystemIds(PREFIX_REQUEST, LENGTH_REQUEST_ID, HEX_REQUEST_FLAG));

    _idTable.put(
      PREFIX_BOX_LAYOUT,
      new SystemIds(PREFIX_BOX_LAYOUT, LENGTH_BOX_LAYOUT_ID, HEX_BOX_LAYOUT_FLAG));

    _idTable.put(
      PREFIX_SAMPLE_FROZEN,
      new SystemIds(PREFIX_SAMPLE_FROZEN, LENGTH_SAMPLE_ID, HEX_SAMPLE_FLAG));

    _idTable.put(
      PREFIX_SAMPLE_PARAFFIN,
      new SystemIds(PREFIX_SAMPLE_PARAFFIN, LENGTH_SAMPLE_ID, HEX_SAMPLE_FLAG));

    _idTable.put(
      PREFIX_SAMPLE_CUSTPARAFFIN,
      new SystemIds(PREFIX_SAMPLE_CUSTPARAFFIN, LENGTH_SAMPLE_ID, HEX_SAMPLE_FLAG));

    _idTable.put(
      PREFIX_SAMPLE_CUSTFROZEN,
      new SystemIds(PREFIX_SAMPLE_CUSTFROZEN, LENGTH_SAMPLE_ID, HEX_SAMPLE_FLAG));

    _idTable.put(
      PREFIX_SAMPLE_IMPORTED,
      new SystemIds(PREFIX_SAMPLE_IMPORTED, LENGTH_SAMPLE_ID, HEX_SAMPLE_FLAG));

    _idTable.put(
      PREFIX_FORMDEFINITION, 
      new SystemIds(PREFIX_FORMDEFINITION, LENGTH_FORMDEFINITION_ID, HEX_FORMDEFINITION_FLAG));

    _idTable.put(PREFIX_ROLE, new SystemIds(PREFIX_ROLE, LENGTH_ROLE_ID, HEX_ROLE_FLAG));

    _isInitialized = true;
  }

  /**
   * Method that takes an Id and returns a fully formed Id or null, if the id
   * cannot be validated.  If null is returned, the error can be retrieved by
   * calling getErrorValue() method.  The <code>type</code> parameter specifies
   * the expected id types.  If it is null, this just checks that the id is valid for
   * some id type.  If it is not null, it must be a set containing strings that can be returned
   * by the {@link #getType()} method, and to be considered valid the type of the specified
   * id must be one of the types in this set.  This class defines contants for these types,
   * but not all of the type constants are valid return values from getType().  For example,
   * two of the constants are MANIFEST_SHORT and MANIFEST_FULL, but only MANIFEST_FULL
   * is a valid type for getType().  In general, when there are both _SHORT and _FULL versions,
   * use the _FULL version unless something states otherwise.
   * 
   * <p>Id the <code>lenient</code> parameter is true, then this accepts ids in mixed case,
   * with leading or trailing whitespace, and whose number part may need to be padded with
   * zeros to make a truly valid id.  When <code>lenient</code> is true and the id is leniently
   * valid, this method returns the id in its full canonical form.  When <code>lenient</code>
   * is false, the input id must be a fully valid canonical id as-is, and the input id is returned
   * unchanged if it is valid (otherwise it returns null).
   * 
   * <p>Some ids have specific subtype constants.  To make it easier to test for valid ids in
   * contexts where its doesn't matter which subtype it is, this class also predefines
   * constants that list all of the subtypes for a given type.  For example, the TYPESET_DONOR
   * constant lists both the AID_LINKED and AID_UNLINKED type constants.  In general, you should
   * use one of the TYPESET_* constants that this class defines to pass to this method rather than
   * constructing type sets yourself.
   * 
   * <p>If you have an id that you know is valid already and you just need to canonicalize it,
   * use the {@link #canonicalize(String)} method instead.
   */
  public String validate(String id, Set validTypes, boolean lenient) {
    // Reset fields that this method sets as a side effect.
    //
    _errValue = SUCCESS; // reset error value
    _type = null;

    if (ApiFunctions.isEmpty(id)) {
      _errValue = INVALID_BLANK;
      return null;
    }

    // convert all characters to uppercase and trim leading and trailing blanks if we're
    // being lenient.
    String theId = id;
    if (lenient) {
      theId = id.toUpperCase().trim();
    }

    // get the category of the id to validate...
    SystemIds typeInfo = determineIdCategory(theId);
    if (typeInfo == null) { // no category found
      _errValue = INVALID_CATEGORY;
      return null;
    }
    else {
      //  found the category...
      //  next, if a type was specified, check that we got the right type
      if (validTypes != null) {
        if (!validTypes.contains(getType())) {
          _errValue = INVALID_CATEGORY;
          return null;
        }
      }

      //  next, verify that the length is as expected...or less for the lenient case...
      boolean isLengthOk;
      if (lenient) {
        isLengthOk = (theId.length() <= typeInfo.getIdLength());
      }
      else {
        isLengthOk = (theId.length() == typeInfo.getIdLength());
      }
      if (isLengthOk) {
        // now, verify that the correct characters are used in the ids
        // that is, some ids allow hex characters and some do not
        if (validChars(typeInfo, theId)) {
          // ok, we have passed the tests to this point...if we're not lenient, we're
          // done, otherwise now we need to handle the missing zeros and do whatever else
          // we need to to to convert the ids to its canonical database form.
          //
          if (lenient) {
            return makeCanonicalId(typeInfo, theId);
          }
          else {
            return theId;
          }
        }
        else {
          _errValue = INVALID_CHARS;
          return null;
        }
      }
      else { // invalid length for this category
        _errValue = INVALID_LENGTH;
        return null;
      }
    }
  }

  /**
   * This is a static version of {@link #validate(String, Set, boolean)} for callers who
   * don't need to retrieve any of the result properties that <code>validate</code> sets,
   * such as the <code>type</code> and <code>errorValue</code> properties.
   */
  public static String validateId(String id, Set validTypes, boolean lenient) {
    synchronized (SINGLETON) {
      return SINGLETON.validate(id, validTypes, lenient);
    }
  }


  /**
   * This is a static version of {@link #validate(String, Set, boolean)} for callers who
   * don't need to retrieve any of the result properties that <code>validate</code> sets,
   * such as the <code>type</code> and <code>errorValue</code> properties.
   */
  public static boolean isValid(String id, Set validTypes, boolean lenient) {
    synchronized (SINGLETON) {
      return (!ApiFunctions.isEmpty(SINGLETON.validate(id, validTypes, lenient)));
    }
  }


  /**
   * Given a string, check to see whether it is an unabbreviated object id for one of the
   * BIGR object types that has an ICP page (for example, samples and boxes).
   * 
   * <p>
   * This method is optimized to quickly reject many strings that could not possibly
   * be a valid ICP id, for example because they are too short.  This makes it useful in
   * contexts where were are scanning large chunks of text to determine which words are ICP ids
   * and so should be rendered as ICP links.  Calling the ordinary ValidateIds validation 
   * functions and passing in TYPESET_ICP could be much slower if used in this situation.
   * 
   * @param id the string to check
   * @return true if the id is an unabbreviated object id for one of the BIGR object
   *   types that has an ICP page.
   */
  public static boolean isIcpId(String id) {
    // In some contexts this function gets called *many* times on strings
    // that aren't object ids at all (for example, in
    // BTXDetailsHistoryNote.doGetDetailsAsHTML), so we want this to
    // be quick to rule out things that couldn't possibly be an object id.
    // Currently we do that by checking first that the length of the input
    // string isn't shorter than any possible valid id for an object that
    // ICP supports.
    // 
    // NOTE: If this function ever changes to support abbreviated
    // object ids, we'll need to include some other means of making sure
    // that things like BTXDetailsHistoryNote remain efficient.

    if (ApiFunctions.safeStringLength(id) < TypeFinder.MIN_ICP_ID_LENGTH) {
      return false;
    }

    String validatedId = ValidateIds.validateId(id, ValidateIds.TYPESET_ICP, false);

    return (validatedId != null);
  }

  /**
   * Convert the specified id to its canonical form.  If the input id is not "leniently valid"
   * (see {@link #validate(String, Set, boolean)}), this throws an exception.  This method is 
   * useful in situations where you need to canonicalize an id but expect it to have already
   * been validated elsewhere.
   * 
   * @param id The id.
   * @return The canonicalized id.
   */
  public String canonicalize(String id) {
    String canonicalId = validate(id, null, true);
    if (canonicalId == null) {
      throw new IllegalArgumentException("Invalid id '" + id + "'.  " + getErrorMessage(null));
    }
    return canonicalId;
  }

  /**
   * This is a static version of {@link #canonicalize(String)} for callers who
   * don't need to retrieve any of the result properties that <code>canonicalize</code> sets,
   * such as the <code>type</code> property.
   */
  public static String canonicalizeId(String id) {
    synchronized (SINGLETON) {
      return SINGLETON.canonicalize(id);
    }
  }

  /**
   * method to get the error that occurred when validating an id
   */
  public int getErrorValue() {
    return _errValue;
  }

  public String getErrorMessage(String insertionString) {
    switch (getErrorValue()) {
      case SUCCESS :
        // No string, this method isn't really intended to be called in the success case.
        return "";
      case INVALID_CATEGORY :
        if (ApiFunctions.isEmpty(insertionString)) {
          return "The id prefix is not valid.";
        }
        else {
          return "Not a valid prefix for " + insertionString + ".";
        }
      case INVALID_CHARS :
        if (ApiFunctions.isEmpty(insertionString)) {
          return "Invalid characters for an id.";
        }
        else {
          return "Invalid characters for " + insertionString + ".";
        }
      case INVALID_LENGTH :
        if (ApiFunctions.isEmpty(insertionString)) {
          return "Not a valid id length.";
        }
        else {
          return "Not a valid id length for " + insertionString + ".";
        }
      case INVALID_BLANK :
        if (ApiFunctions.isEmpty(insertionString)) {
          return "Please enter an id.";
        }
        else {
          return "Please enter " + insertionString + ".";
        }
      default :
        if (ApiFunctions.isEmpty(insertionString)) {
          return "The id prefix is not valid.";
        }
        else {
          return "The id format is not valid for " + insertionString + ".";
        }
    }
  }

  public String getType() {
    return _type;
  }

  private SystemIds determineIdCategory(String id) {
    // We need to determine if we have a valid prefix.  Start with the first two
    // characters of the prefix and see if we have type info information matching
    // those characters.  If not, increase to three characters and try, etc until
    // either type information is found, we run out of characters in the id, or
    // we reach the maximum number of characters to try

    SystemIds returnValue = null;

    _type = null;

    int charCount = PREFIX_LENGTH_MIN;

    while (returnValue == null && id.length() > charCount && charCount <= PREFIX_LENGTH_MAX) {
      //see if we can find type info for the current prefix size
      SystemIds typeInfo = (SystemIds) _idTable.get(id.substring(0, charCount));
      //if not, increase the size of the prefix to look for
      if (typeInfo == null) {
        charCount = charCount + 1;
      }
      //if so, return it
      else {
        _type = typeInfo.getIdPrefix();
        returnValue = typeInfo;
      }
    }
    return returnValue;
  }

  private boolean validChars(SystemIds typeInfo, String id) {
    // determine if the characters are valid for this category...
    boolean allowHex = typeInfo.getHexAllowed();
    // get all characters after the category prefix
    String idPart = id.substring(typeInfo.getIdPrefix().length(), id.length());
    char chars[] = idPart.toCharArray();
    if (allowHex) {
      // determine if any chars besides 0-F are in this id
      for (int i = 0; i < chars.length; i++) {
        char c = chars[i];
        if (!(((c >= '0') && (c <= '9')) || ((c >= 'A') && (c <= 'F')))) {
          _errValue = INVALID_CHARS;
          return false;
        }
      }
    }
    else {
      // determine if any chars besides 0-9 are in this id
      for (int i = 0; i < chars.length; i++) {
        char c = chars[i];
        if (!((c >= '0') && (c <= '9'))) {
          _errValue = INVALID_CHARS;
          return false;
        }
      }
    }
    return true;
  }

  private String makeCanonicalId(SystemIds typeInfo, String id) {
    // set the completed id = prefix + needed zeros + remainder of id

    String typePrefix = typeInfo.getIdPrefix();

    StringBuffer canonicalId = new StringBuffer();

    canonicalId.append(typePrefix);

    // start by stripping off the category prefix
    String idPart = id.substring(typePrefix.length(), id.length());
    // create needed zeros...
    int neededZeros = typeInfo.getIdLength() - typePrefix.length() - idPart.length();
    for (int i = 0; i < neededZeros; i++) {
      canonicalId.append('0');
    }

    canonicalId.append(idPart);

    return canonicalId.toString();
  }

  private static void test_result(ValidateIds valid, String id) {
    String fullId;
    if ((fullId = valid.validate(id, null, true)) == null)
      System.out.println(id + " failed, with error: " + valid.getErrorMessage(null));
    else {
      System.out.println(id + " successfully validate; returned as " + fullId);
    }
  }

  public static void main(String[] args) {

    String the_id;
    ValidateIds valid = new ValidateIds();

    // bogus...
    the_id = "BR0000000111";
    test_result(valid, the_id);
    the_id = "1BR0000000111";
    test_result(valid, the_id);
    the_id = "1BR0 00000111";
    test_result(valid, the_id);
    the_id = "1BR000000011 ";
    test_result(valid, the_id);
    the_id = " BR000000011 ";
    test_result(valid, the_id);
    the_id = "";
    test_result(valid, the_id);

    // case unlinked...
    the_id = "CU0000000111";
    test_result(valid, the_id);
    the_id = "Cu0000000111";
    test_result(valid, the_id);
    the_id = "cu0000000111";
    test_result(valid, the_id);
    the_id = "CUK00000111";
    test_result(valid, the_id);
    the_id = "CU111";
    test_result(valid, the_id);
    the_id = "CU00111";
    test_result(valid, the_id);
    the_id = "CU001110";
    test_result(valid, the_id);
    the_id = "CU0101010";
    test_result(valid, the_id);
    the_id = "CUCU01010";
    test_result(valid, the_id);
    the_id = "CU0CU010";
    test_result(valid, the_id);
    the_id = "CU000000000c";
    test_result(valid, the_id);
    the_id = "CU000000000000000000001";
    test_result(valid, the_id);
    the_id = " U0000000111";
    test_result(valid, the_id);
    the_id = "C 0000000111";
    test_result(valid, the_id);
    the_id = "cu 000000111";
    test_result(valid, the_id);
    the_id = "CUK 0000111";
    test_result(valid, the_id);
    the_id = "CU1110 ";
    test_result(valid, the_id);
    the_id = "CU 1110";
    test_result(valid, the_id);
    the_id = "CU0FR1110";
    test_result(valid, the_id);
    the_id = "CU PA 010";
    test_result(valid, the_id);

    // case linked...
    the_id = "CI0000000111";
    test_result(valid, the_id);
    the_id = "ci0000000111";
    test_result(valid, the_id);
    the_id = "ci111";
    test_result(valid, the_id);
    the_id = "CI000111";
    test_result(valid, the_id);
    the_id = "CI0000000111m";
    test_result(valid, the_id);
    the_id = "CI0000000111c";
    test_result(valid, the_id);
    the_id = "CI00000111 ";
    test_result(valid, the_id);
    the_id = "ci0000111 ";
    test_result(valid, the_id);
    the_id = "ci111a";
    test_result(valid, the_id);
    the_id = " CI000111";
    test_result(valid, the_id);
    the_id = " I0000000111m";
    test_result(valid, the_id);
    the_id = "C 0000000111c";
    test_result(valid, the_id);
    the_id = " C7I111";
    test_result(valid, the_id);
    the_id = "ci7000111";
    test_result(valid, the_id);
    the_id = " ci111 ";
    test_result(valid, the_id);
    the_id = "CII000111";
    test_result(valid, the_id);
    the_id = "CICI00111";
    test_result(valid, the_id);
    the_id = " CI 000111";
    test_result(valid, the_id);
    the_id = "MNFTcuci021";
    test_result(valid, the_id);

    // manifest...
    the_id = "MNFT0000000021";
    test_result(valid, the_id);
    the_id = "MNFR000000021";
    test_result(valid, the_id);
    the_id = "mFT0000000021";
    test_result(valid, the_id);
    the_id = "mnft0000000021";
    test_result(valid, the_id);
    the_id = "MNFT00000000000000000021";
    test_result(valid, the_id);
    the_id = "MNFT021";
    test_result(valid, the_id);
    the_id = "MNFT21";
    test_result(valid, the_id);
    the_id = " M N F T 0 21";
    test_result(valid, the_id);
    the_id = "mNfT21";
    test_result(valid, the_id);
    the_id = "MnFt21";
    test_result(valid, the_id);
    the_id = "mnftmnft021";
    test_result(valid, the_id);
    the_id = "mnftmnft021 ";
    test_result(valid, the_id);
    the_id = "mnft 021 ";
    test_result(valid, the_id);
    the_id = "CU21";
    test_result(valid, the_id);
    the_id = "MNFT021 ";
    test_result(valid, the_id);
    the_id = "MNFT21 ";
    test_result(valid, the_id);
    the_id = " MNFT021";
    test_result(valid, the_id);
    the_id = " MNFT21";
    test_result(valid, the_id);

    // asm...
    the_id = "ASM085000049";
    test_result(valid, the_id);
    the_id = "ASM85000049";
    test_result(valid, the_id);
    the_id = "ASM49";
    test_result(valid, the_id);
    the_id = "aSm085000049";
    test_result(valid, the_id);
    the_id = "ASM08500004900";
    test_result(valid, the_id);
    the_id = "ASM1";
    test_result(valid, the_id);
    the_id = " ASM1";
    test_result(valid, the_id);
    the_id = "ASM1 ";
    test_result(valid, the_id);
    the_id = "ASM119000001";
    test_result(valid, the_id);
    the_id = "ASM125000001";
    test_result(valid, the_id);
    the_id = "ASM115000001";
    test_result(valid, the_id);
    the_id = "ASM115005000";
    test_result(valid, the_id);
    the_id = "ASM103000001";
    test_result(valid, the_id);
    the_id = "ASM099010101";
    test_result(valid, the_id);
    the_id = "ASM100100100";
    test_result(valid, the_id);
    the_id = "ASM 15000001";
    test_result(valid, the_id);
    the_id = "ASM1 5005000";
    test_result(valid, the_id);
    the_id = " SM103000001";
    test_result(valid, the_id);
    the_id = "asm100100100";
    test_result(valid, the_id);
    the_id = "aSM 15000000";
    test_result(valid, the_id);
    the_id = "Asm  5005000";
    test_result(valid, the_id);
    the_id = " SM103000001";
    test_result(valid, the_id);

    // box...
    the_id = "BX0000000555";
    test_result(valid, the_id);
    the_id = "BXT000000555";
    test_result(valid, the_id);
    the_id = "x0000000555";
    test_result(valid, the_id);
    the_id = "bx0000000555";
    test_result(valid, the_id);
    the_id = "BX055500";
    test_result(valid, the_id);
    the_id = "BX555";
    test_result(valid, the_id);
    the_id = " BX0";
    test_result(valid, the_id);
    the_id = "BX0";
    test_result(valid, the_id);
    the_id = "BX0 ";
    test_result(valid, the_id);
    the_id = "B X 0 ";
    test_result(valid, the_id);
    the_id = " bx010101";
    test_result(valid, the_id);
    the_id = "brx1";
    test_result(valid, the_id);
    the_id = "br ci1";
    test_result(valid, the_id);
    the_id = "BXOOOOOO555";
    test_result(valid, the_id);

    // frozen...
    the_id = "FR65EDEBDA";
    test_result(valid, the_id);
    the_id = "fr65edebda";
    test_result(valid, the_id);
    the_id = "FR65eDEBDA";
    test_result(valid, the_id);
    the_id = "FR65EXEBDA";
    test_result(valid, the_id);
    the_id = "FR0abcDEF0";
    test_result(valid, the_id);
    the_id = "FR000CDEF6";
    test_result(valid, the_id);
    the_id = "FREBDA";
    test_result(valid, the_id);
    the_id = "FR12340";
    test_result(valid, the_id);
    the_id = "FR0";
    test_result(valid, the_id);
    the_id = "FRABCDEFGA";
    test_result(valid, the_id);
    the_id = "FRFFFFFFFF";
    test_result(valid, the_id);
    the_id = "FR00000000";
    test_result(valid, the_id);
    the_id = "FR6 ";
    test_result(valid, the_id);
    the_id = " FR65";
    test_result(valid, the_id);
    the_id = "F R 6 5 ";
    test_result(valid, the_id);
    the_id = "FR09@";
    test_result(valid, the_id);
    the_id = "FR09!";
    test_result(valid, the_id);
    the_id = "<b>Bob</b> & Cheryl O'Hara";
    test_result(valid, the_id);
    the_id = "frfr65f";
    test_result(valid, the_id);
    the_id = "frpafa0";
    test_result(valid, the_id);
    the_id = "fr1";
    test_result(valid, the_id);
    the_id = "cy999";
    test_result(valid, the_id);

    // paraffin... 
    the_id = "PA00003C10";
    test_result(valid, the_id);
    the_id = "pa00003C10";
    test_result(valid, the_id);
    the_id = "pa3C10";
    test_result(valid, the_id);
    the_id = "PAn00003C10";
    test_result(valid, the_id);
    the_id = "P3C10";
    test_result(valid, the_id);
    the_id = "PA0";
    test_result(valid, the_id);
    the_id = "PAABCDEFGA";
    test_result(valid, the_id);
    the_id = "PAFFFFFFFF";
    test_result(valid, the_id);
    the_id = "PA00000000";
    test_result(valid, the_id);
    the_id = "PA6 ";
    test_result(valid, the_id);
    the_id = " PA65";
    test_result(valid, the_id);
    the_id = "P A 6 5 ";
    test_result(valid, the_id);
    the_id = "PA09@";
    test_result(valid, the_id);
    the_id = "PA09!";
    test_result(valid, the_id);
    the_id = "<b>Bob</b> & Cheryl O'Hara";
    test_result(valid, the_id);
    the_id = "papa65f";
    test_result(valid, the_id);
    the_id = "pafrfa0";
    test_result(valid, the_id);
    the_id = "pa1";
    test_result(valid, the_id);
    the_id = "cy999";
    test_result(valid, the_id);

    // ardais linked id (I SUGGEST USING THE SAME CASES AS CI AND CU RESPECTIVELY AND CHANGING THE A TO C)
    the_id = "AI0000005343";
    test_result(valid, the_id);
    the_id = "ai0005343";
    test_result(valid, the_id);
    the_id = "ai5343";
    test_result(valid, the_id);
    the_id = "AI5343";
    test_result(valid, the_id);
    the_id = "AI5343000";
    test_result(valid, the_id);

    // ardais unlinked id
    the_id = "AU0000005343";
    test_result(valid, the_id);
    the_id = "au0000005343";
    test_result(valid, the_id);
    the_id = "au00000005343";
    test_result(valid, the_id);
    the_id = "AU5343";
    test_result(valid, the_id);
    the_id = "au005343";
    test_result(valid, the_id);
    the_id = "Au0000053430";
    test_result(valid, the_id);

    // frozen slide ( SAME CASES AS FR REPLACE SF WITH FR)
    the_id = "SF000095F5";
    test_result(valid, the_id);
    the_id = "sf000095f5";
    test_result(valid, the_id);
    the_id = "SF95F5";
    test_result(valid, the_id);
    the_id = "sf095F5";
    test_result(valid, the_id);
    the_id = "SF8888";
    test_result(valid, the_id);
    the_id = "SF888800";
    test_result(valid, the_id);

    // paraffin slide ( SAME CASES AS PA REPLACE SP WITH PA)
    the_id = "SP00008F74";
    test_result(valid, the_id);
    the_id = "sp00008F74";
    test_result(valid, the_id);
    the_id = "sp8F74";
    test_result(valid, the_id);
    the_id = "SP08F74";
    test_result(valid, the_id);
    the_id = "SP0008F74";
    test_result(valid, the_id);
    the_id = "spP00008F74";
    test_result(valid, the_id);
    
    //form definition
    // bogus...
    the_id = "FD00000011";
    test_result(valid, the_id);
    the_id = "1FD00000011";
    test_result(valid, the_id);
    the_id = "1FD0 000111";
    test_result(valid, the_id);
    the_id = "1FD0000011 ";
    test_result(valid, the_id);
    the_id = " FD000011 ";
    test_result(valid, the_id);
  }
}
