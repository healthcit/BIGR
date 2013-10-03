package com.ardais.bigr.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class IdValidator {

  /**
   * Returns true if the sampleId is valid and is of the type specified in 2nd parameter 
   */
  public static boolean validSampleId(String sampleId, String format) {
    ValidateIds validId = new ValidateIds();

    // first, verify that it matches basic sample rules
    if (!ApiFunctions.isEmpty(validId.validate(sampleId, ValidateIds.TYPESET_SAMPLE, false))) {
      // now verify what format the sample is...
      if (sampleId.startsWith(format)) {
        return true;
      }
      else {
        return false;
      }
    }
    else {
      return false;
    }
  }

  public static boolean validRnaId(String rnaId) {

    if (rnaId == null)
      return false;
    else if (rnaId.equals(""))
      return false;
    else if (rnaId.length() != Constants.LENGTH_RNAID)
      return false;
    else if (!(rnaId.startsWith("RN")))
      return false;
    else if (!validHex(rnaId, 2))
      return false;
    else if (!validUpperCase(rnaId, 2))
      return false;
    else
      return true;
  }

  public static boolean validHex(String ID) {
    return validHex(ID, 0);
  }

  // validate the relevant section of the ID string, char by char.
  private static boolean validHex(String ID, int startpos) {
    int len = ID.length();
    for (int i = startpos; i < len; i++) {
      char c = ID.charAt(i);
      if (!Character.isDigit(c) && !(c <= 'F' && c >= 'A'))
        return false;
    }
    return true;
  }

  /**
   *  Returns true if id is a mixture of upper case characters and digits.
   *  @param id - the String to check.
   */
  public static boolean validUpperCase(String id) {
    return validUpperCase(id, 0);
  }

  // checks the string char by char, starting at position: startpos.
  public static boolean validUpperCase(String id, int startpos) {
    boolean valid = true;
    int len = id.length();
    for (int i = startpos; i < len; i++) {
      char c = id.charAt(i);
      if (!Character.isDigit(c) && !Character.isUpperCase(c)) {
        valid = false;
        break;
      }
    }
    return valid;
  }

  public static String[] validSampleIds(String[] strs) {
    ValidateIds validId = new ValidateIds();

    List ids = new ArrayList();
    for (int i = 0; i < strs.length; i++) {
      String id = strs[i];
      if (!ApiFunctions.isEmpty(validId.validate(id, ValidateIds.TYPESET_SAMPLE, false))) {
        ids.add(id);
      }
    }
    return (String[]) ids.toArray(new String[] {
    });
  }

  public static String[] validRnaIds(String[] strs) {
    List ids = new ArrayList();
    for (int i = 0; i < strs.length; i++) {
      String id = strs[i];
      if (validRnaId(id)) {
        ids.add(id);
      }
    }
    return (String[]) ids.toArray(new String[] {
    });
  }

  /**
   * Returns true when the specified keyword string is appropriate for an
   * interMedia/Oracle Text keyword search.
   * 
   * @param  keywordString  the keyword string, which can consist of multiple
   * 												 keywords separated by spaces, AND, OR or NOT and
   * 												 can also include the * or % wildcard character
   */
  public static boolean validKeywordSearch(String keywordString) {

    if (keywordString == null || keywordString.trim().length() == 0) {
      return false;
    }

    // First make sure that there are not any invalid characters.
    // The only valid non-alphanumeric character is a space or *.
    char[] chars = keywordString.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];
      if (!(Character.isLetterOrDigit(c)
        || Character.isSpaceChar(c)
        || (c == '*')
        || (c == '%'))) {
        return false;
      }
    }

    // Make sure that one of the operators AND, OR or NOT are not the first
    // or last token, and that there are not 2 of them in a row.
    StringTokenizer tokens = new StringTokenizer(keywordString, " ");
    boolean operatorOk = false;
    while (tokens.hasMoreTokens()) {
      String token = tokens.nextToken();
      if (token.equalsIgnoreCase("AND")
        || token.equalsIgnoreCase("OR")
        || token.equalsIgnoreCase("NOT")) {
        if (operatorOk) {
          operatorOk = false;
        }
        else {
          return false;
        }
      }
      else {
        operatorOk = true;
      }
    }
    if (!operatorOk) {
      return false;
    }

    return true;
  }

  /**
   *  A quick test for validation methods.
   */
  public static void main(String[] args) {
    boolean validChecks = true;
    validChecks = validChecks && validHex("ABC3452CDF00");
    validChecks = validChecks && validHex("**AAABBBCC2255", 2);
    validChecks = validChecks && validUpperCase("ZZXXABWWC3452CDF00");
    validChecks = validChecks && validUpperCase("DI000FFF");
    validChecks = validChecks && validUpperCase("di00ZZ0FFF", 2);
    validChecks = validChecks && validRnaId("RN00000022");
    validChecks = validChecks && validRnaId("RN00000EC4");
    validChecks = validChecks && validKeywordSearch("breast");
    validChecks = validChecks && validKeywordSearch("breast and lung");
    validChecks = validChecks && validKeywordSearch("breast*");
    validChecks = validChecks && validKeywordSearch("breast%");
    validChecks = validChecks && validKeywordSearch("breast and lung or cervix not normal");
    if (!validChecks)
      System.err.println("Error.  Some VALID string was NOT validated");

    validChecks = false;
    validChecks = validChecks || validHex("ABC3HHHHCDF00");
    validChecks = validChecks || validHex("**AAAB&&CC2255", 2);
    validChecks = validChecks || validUpperCase("ZZ--ABWW//C3452CDF00");
    validChecks = validChecks || validUpperCase("di000FFF");
    validChecks = validChecks || validRnaId("RN000600022");
    validChecks = validChecks || validRnaId("RN0000EC4");
    validChecks = validChecks || validKeywordSearch("and");
    validChecks = validChecks || validKeywordSearch("breast and");
    validChecks = validChecks || validKeywordSearch("breast or");
    validChecks = validChecks || validKeywordSearch("not breast");
    validChecks = validChecks || validKeywordSearch("breast and not lung");
    validChecks = validChecks || validKeywordSearch("breas&");
    if (validChecks)
      System.err.println("Error.  Some INvalid string WAS validated");

  }
}
