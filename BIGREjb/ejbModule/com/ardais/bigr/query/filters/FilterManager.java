package com.ardais.bigr.query.filters;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ClassLookupManager;

/**
 * @author dfeldman
 *
 * A class that manages the key/class relationships between FilterConstants and Filters,
 * and instantiates filters using the keys.
 * 
 * It also does some string decoding to convert serialized filter strings into filters.
 */
public class FilterManager extends ClassLookupManager {

  public static final DateFormat DATE_FORMAT = new SimpleDateFormat();

  public static final String BOOL = "bool";
  public static final String STRINGS = "strings";
  public static final String STRINGSWITHDESC = "stringsWDescripts";
  public static final String STRING = "string";
  public static final String NOPARAMS = "noParams";
  public static final String DATERANGE = "daterange";
  public static final String NUMRANGEINTEGER = "numericRangeInteger";
  public static final String NUMRANGEDECIMAL = "numericRangeDecimal";
  public static final String CONTAINS = "contains";
  public static final String REPEATDATA = "repeatData";
  public static final String LIKE = "like";
  public static final String USERACCOUNT = "userAccountInfo";

  private static FilterManager instance; 
  
  private Map _filterTypes = new HashMap();
  
  public static FilterManager instance() {
    if (instance==null) 
      instance = new FilterManager();
    return instance;
  }
  
  /**
   * Converts a string representation of a single filter to a filter instance.
   * @param encodedFilter the encoded filter
   * @param secInfo   user info in case the filter needs account or user data to work
   */
  public Filter getInstance(String key, String vals, SecurityInfo secInfo) {
    try {
      String type = (String) _filterTypes.get(key);
      if (type==null) {
        throw new ApiException("No Filter (type) registered for type code: " + key);
      }
      // ELSE ELSE ELSE....
      Object[] params = null;
      Class[] typeOverride = null;
      if (NOPARAMS.equals(type)) {
        params = new Object[0];
      }
      else if (STRINGSWITHDESC.equals(type)) {
        String[] codeDesc = split(vals, ';');
        params = new Object[] {
              StringUtils.split(codeDesc[0], ","), 
              StringUtils.split(codeDesc[1], ",")}; 
      }
      else if (STRINGS.equals(type)) {
        params = new Object[] {StringUtils.split(vals, ",")}; // array with single String[] elem
      }
      else if (STRING.equals(type)) {
        params = new String[] {vals}; // array with single string
      }
      else if (CONTAINS.equals(type)) {
        params = new String[] {vals};
      }
      else if (LIKE.equals(type)) {
        params = new String[] {vals};
      }
      else if (DATERANGE.equals(type)) {
        String[] dates = split(vals, ',');
        String date1 = (dates[0].equals("-")) ?  null : dates[0];
        String date2 = (dates[1].equals("-")) ?  null : dates[1];
        params = new String[] {date1, date2};
        typeOverride = new Class[] {String.class, String.class};
      }
      else if (NUMRANGEINTEGER.equals(type)) {
        String[] nums = split(vals,',');
        Integer int1 = (nums[0].equals("-")) ?  null : new Integer(nums[0]);
        Integer int2 = (nums[1].equals("-")) ?  null : new Integer(nums[1]);
        params = new Integer[] {int1, int2};      
        typeOverride = new Class[] {Integer.class, Integer.class};
      }
      else if (NUMRANGEDECIMAL.equals(type)) {
        // this appears to have to do with saving queries/filters in the user profile, but since
        // it is not currently used and thus not worth the effort to support unpacking the
        // values correctly, we'll just throw an exception.  Of course if we ever get this 
        // exception, then we'll have to write the appropriate code.
        throw new ApiException("Unsupported: NUMRANGEDECIMAL encountered in FilterManager.getInstance");
      }
      else if (BOOL.equals(type)) {
        params = new Object[] {new Boolean(vals)};  // one bool value
      } 
      else if (USERACCOUNT.equals(type)) {
        // if the user and/or account flags are set, pass in the userID and/or account
        String[] usrAcct = split(vals, ',');
        List paramList = new ArrayList(2);
        if (!usrAcct[0].equals("-"))
          paramList.add(secInfo.getUsername());
        if (!usrAcct[1].equals("-"))
          paramList.add(secInfo.getAccount());
        params = paramList.toArray(new Object[0]); // one or two strings
      }
      else {
        throw new ApiException("Undefined filter type '" + type + "' in: " + key+'/'+vals);
      }
      
      Object filtObj = super.getInstance(key, params, typeOverride);
      return (Filter) filtObj;
    } catch (Exception e) {
      throw new ApiException("Could not decode query filter: " + key+'/'+vals, e);
    }
  }
  
  // split s to the left and right of the char, and return both halves in an array
  private String[] split(String s, char sep) {
    String[] result = new String[2];
    int pos = s.indexOf(sep);
    result[0] = s.substring(0,pos);
    result[1] = s.substring(pos+1);
    return result;
  }
  /**
   * @see com.ardais.bigr.util.ClassLookupManager#register(String, Class)
   * 
   * @param specialKey   the key to assoicate the filter class with
   * @param clazz  the Java class to create for this filter key
   * @param type   the category of filter, used to parse and pass in parameters to constructor
   * type is one of the static constants defined in this class.
   */
  public void register(String specialKey, Class clazz) {
    super.register(specialKey, clazz);
    
    String type;
    if (FilterBoolean.class.isAssignableFrom(clazz)) {
      type = BOOL;
      checkConstructor(clazz, new Class[] {Boolean.class});
    }
    else if (FilterStringsEqualWithDescriptions.class.isAssignableFrom(clazz)) {
      type = STRINGSWITHDESC;
      checkConstructor(clazz, new Class[] {String[].class, String[].class});
    }
    else if (FilterStringsEqual.class.isAssignableFrom(clazz)) {
      type = STRINGS;
      checkConstructor(clazz, new Class[] {String[].class});
    }
    else if (FilterStringEquals.class.isAssignableFrom(clazz)) {
      type = STRING;
      checkConstructor(clazz, new Class[] {String.class});
    }
    else if (FilterRepeating.class.isAssignableFrom(clazz)) {
      type = REPEATDATA;
      checkConstructor(clazz, new Class[] {RepeatingFilterData.class});
    }
    else if (FilterNumericRangeInteger.class.isAssignableFrom(clazz)) {
      type = NUMRANGEINTEGER;
      checkConstructor(clazz, new Class[] {Integer.class, Integer.class});
    }
    else if (FilterNumericRangeDecimal.class.isAssignableFrom(clazz)) {
      type = NUMRANGEDECIMAL;
      checkConstructor(clazz, new Class[] {BigDecimal.class, BigDecimal.class});
    }
    else if (FilterDateRange.class.isAssignableFrom(clazz)) {
      type = DATERANGE;
      checkConstructor(clazz, new Class[] {String.class, String.class});
    }
    else if (FilterStringContains.class.isAssignableFrom(clazz)) {
      type = CONTAINS;
      checkConstructor(clazz, new Class[] {String.class});
    }
    else if (FilterStringLike.class.isAssignableFrom(clazz)) {
      type = LIKE;
      checkConstructor(clazz, new Class[] {String.class});
    }
    else if (FilterStringsLike.class.isAssignableFrom(clazz)) {
      type = STRINGS;
      checkConstructor(clazz, new Class[] {String[].class});
    }
    else if (FilterUserAccountInfo.class.isAssignableFrom(clazz)) {
      type = USERACCOUNT;
      checkConstructor(clazz, new Class[] {String.class}); // varies now EITHER user or acct
    }
    else if (FilterNoParameters.class.isAssignableFrom(clazz)) {
      type = NOPARAMS;
      checkConstructor(clazz, new Class[0]);
    }
    else
      throw new ApiException("Overall filter type unknown for internal type: " + clazz);
      
    _filterTypes.put(specialKey, type);
  }

  private void checkConstructor(Class clazz, Class[] types) {
    try {
      clazz.getConstructor(types);
    } catch (Exception e) {
      System.out.println("-- No valid constructor for filter: " + clazz);
//      throw new ApiException("No valid constructor for filter: " + clazz);
    }
  }
}
