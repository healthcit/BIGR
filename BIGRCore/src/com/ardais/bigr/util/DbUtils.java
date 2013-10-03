package com.ardais.bigr.util;

import java.sql.*;
import java.util.*;

import com.ardais.bigr.api.ApiFunctions;

/**
 * Contains static utility methods related to JDBC, SQL and general database
 * functionality.
 */
public class DbUtils {

	private DbUtils() {}

  /**
   * Returns a string with special characters used for LIKE queries converted.
   * The characters are converted from how they are input in the UI to their
   * equivalents in SQL.  Specifically, all '*' characters in the input string
   * are converted to '%'.
   * 
   * @param  s  the input string
   * @return  The converted string.
   */
  public static String convertContainSpecialChars(String s) {
    if (s == null) {
      return null;
    }
    else {
      String newString = s;
      newString = newString.replace('*', '%');
      return newString;
    }
  }

  /**
   * Returns a converted string used for LIKE queries. The '*' character is converted
   * to the Oracle wildcard character '%'. Please note that Oracle has another special wildcard
   * character: '_'. The underscore character acts as a single character wildcard, as opposed to
   * the '%' which is a multi-character wildcard. In order to avoid a problem with mixing the
   * Oracle wildcarding charaters with the prefered '*' wildcard character, the Oracle characters
   * will be escaped. The SQL query must also include the keyword ESCAPE and the character to
   * escape on. We have choosen to use the '/' character as the escape character, so that character
   * has to be escaped along with the '%' and '_' characters. The SQL query using LIKE must also
   * include : ESCAPE '/'.
   * 
   * @param s - input string to convert
   * @return The converted string.
   */
  public static String convertLikeSpecialChars(String s) {
    if (ApiFunctions.isEmpty(s)) {
      return null;
    }
    else {
      // Can't use a \\_ (escpae)slash because of a java bug.
      String newString = s.replaceAll("/", "//");
      newString = newString.replaceAll("_", "/_");
      newString = newString.replaceAll("%", "/%");
      newString = newString.replace('*', '%');
      return newString;
    }
  }
  
  /**
   * Returns a converted array of strings used for LIKE queries. The '*' character is converted
   * to the Oracle wildcard character '%'. Please note that Oracle has another special wildcard
   * character: '_'. The underscore character acts as a single character wildcard, as opposed to
   * the '%' which is a multi-character wildcard. In order to avoid a problem with mixing the
   * Oracle wildcarding charaters with the prefered '*' wildcard character, the Oracle characters
   * will be escaped. The SQL query must also include the keyword ESCAPE and the character to
   * escape on. We have choosen to use the '/' character as the escape character, so that character
   * has to be escaped along with the '%' and '_' characters. The SQL query using LIKE must also
   * include : ESCAPE '/'.
   * 
   * @param strings - input array of strings to convert
   * @return The converted array of string.
   */
  public static String[] convertLikeSpecialChars(String[] strings) {
    if (ApiFunctions.isAllEmpty(strings)) {
      return null;
    }
    else {
      String newStrings[] = new String[strings.length];
      for (int i = 0; i < strings.length; i++) {
        // Can't use a \\_ (escpae)slash because of a java bug.
        newStrings[i] = convertLikeSpecialChars(strings[i]);
      }
      return newStrings;
    }
  }

  /**
   * Returns a string with special characters that cause InterMedia problems to be escaped.
	 * Specifically, all '('  and ')' characters in the input string
	 * are converted to '\(' and '\)' respectively.
	 * 
	 * @param  s  the input string
	 * @return  The converted string.
	 */
	public static String escapeIntermediaSpecialChars(String s) {
		if (s != null) {
      if (s.indexOf("(") >= 0) {
        StringBuffer buff = new StringBuffer();
        StringTokenizer tokenizer = new StringTokenizer(s, "(", true);
        while (tokenizer.hasMoreTokens()) {
          String token = tokenizer.nextToken();
          if (token.equals("(")) {
            buff.append("\\(");
          }
          else {
            buff.append(token);
         }
        }
        s = buff.toString();
      }
      if (s.indexOf(")") >= 0) {
        StringBuffer buff = new StringBuffer();
        StringTokenizer tokenizer = new StringTokenizer(s, ")", true);
        while (tokenizer.hasMoreTokens()) {
          String token = tokenizer.nextToken();
          if (token.equals(")")) {
            buff.append("\\)");
          }
          else {
            buff.append(token);
         }
        }
        s = buff.toString();
      }
		}
    return s;
	}
  
  /**
   * Returns a string that has all characters that have special meaning or cause problems for
   * InterMedia taken care of.
   * 
   * @param  s  the input string
   * @return  The converted string.
   */
  public static String prepareIntermediaQueryString(String s) {
    s = convertContainSpecialChars(s);
    s = escapeIntermediaSpecialChars(s);
    return s;
  }
	
	/**
	 * Returns a boolean representation of the specified string.  If the string
	 * is non-null and equal to 'y', 'Y', 'yes', 'YES', 'true' or 'TRUE', then
	 * <code>true</code> is returned; otherwise <code>false</code> is returned.
	 * 
	 * @param  s  the string
	 * @return  The boolean representation of the string.
	 */
	public static boolean convertStringToBoolean(String s) {
		if (s == null) {
			return false;
		}
		else if ("Y".equals(s) || "y".equals(s)
				 || "TRUE".equals(s) || "true".equals(s)
				 || "YES".equals(s) || "yes".equals(s)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns an Integer representation of the specified string.  If the string
	 * is null or empty, then null is returned.
	 * 
	 * @param  s  the string
	 * @return  The Integer representation of the string.
	 * @throws  NumberFormatException if the String does not contain a parsable 
	 * 						integer.
	 */
	public static Integer convertStringToInteger(String s) {
		if (ApiFunctions.isEmpty(s)) {
			return null;
		}
		else {
			return new Integer(s);
		}
	}

	/**
	 * Returns a <code>Map<code> of the column names in the specified
	 * <code>ResultSet</code>.  The keys of the <code>Map<code> are the column
	 * names, converted to lower case, and the values are all <code>null</code>.
	 * This facilitates using <code>Map.containsKey()</code> to determine
	 * whether a particular column is present in the <code>ResultSet</code>.
	 * 
	 * @param  rs  the <code>ResultSet</code>
	 * @return  A <code>Map<code> with result set column names as keys.
	 */
	public static Map getColumnNames(ResultSet rs) {
    //TODO: this should really be a Set instead of a Map
        Map columns = new HashMap();	
		try {
			ResultSetMetaData meta = rs.getMetaData();
			int columnCount = meta.getColumnCount();
	        for (int i = 0; i < columnCount; i++) {
	            columns.put(meta.getColumnName(i + 1).toLowerCase(), null);
	        }
		}
		catch (SQLException e) {
			ApiFunctions.throwAsRuntimeException(e);
		}
        return columns;
	}

}
