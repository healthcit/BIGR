package com.ardais.bigr.util;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SearchTermValidator {
    
    public static class ValidationException extends Exception {
        public ValidationException(String reason) {
            super(reason);
        }
    }

    private static final List _reservedIntermediaWords = Arrays.asList(new String[] {
        "WILDCARD", "FUZZY", "STEM", "SOUNDEX", "EQUIV", "AND", 
        "WEIGHT", "THRESHOLD", "MINUS", "ACCUM", "NEAR", "WITHIN", "SYN", "ABOUT",
        "BT", "BTG", "BTI", "BTP", "HASPATH", "INPATH", "NOT", "NT", "NTG", "NTI", 
        "NTP", "OR", "PT", "RT", "SQE", "TR", "TRSYN", "TT"});
    //note - both the * and % symbol are included as valid Interdemia characters to avoid
    //having to worry about the order of substituting % for * and the validation of the string    
    private static final String _validIntermediaCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 *%";
    private static final String _reservedDbLikeSymbols = "%_";
        
    /**
     * Constructor for SearchTermValidator.
     */
    public SearchTermValidator() {
        super();
    }

    /**
     * Returns true if the string has at least a certain number of contiguous
     * alphanumeric characters.
     * @param s the string to check
     * @param min the minimum number of contiguous alpahnumeric characters.
     */
    public static boolean hasContiguousAlphaNumeric(String s, int min) {
        int numSoFar=0;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                numSoFar++;
                if (numSoFar>=min)
                    return true;
            } else {
                numSoFar=0;
            }
        }
        return false;
    }

    /**
     * Returns true if the characters after position startpos are all valid
     * intermedia characters.
     */
    public static boolean isAllValidIntermediaChars(String s) {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (!isValidIntermediaChar(c)) {
                return false;
            }
        }
        return true;
    }
    
    private static String findInvalidCondition(String s, String validChars) {
        String result = "";
        for (int i=0; i<s.length(); i++) {
            if (validChars.indexOf(s.charAt(i)) == -1) {
                if (result.length()==0)
                    result = result + s.charAt(i);
                else
                    result = result + ", " + s.charAt(i);
            }
        }
        return result;
    }
    /**
     * Returns true if the characters after position startpos are all valid
     * intermedia characters.
     */
    public static boolean isAllValidDbLikeChars(String s) {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (!isValidDBLikeChar(c)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Return true if the string is of the form of an optional asterix
     * followed by a sequence of alphanumeric chars, followed by an optional
     * asterix.
     * @deprecated - allow internal asterixes for now
     */
    private static boolean hasInternalAsterixes(String s) {
        int len = s.length();        
        for (int i = 1; i < len-1; i++) {
            char c = s.charAt(i);
            if (c=='*') { 
                return false; 
            }
        }
        return true;
    }

    /**
     * Throws a ValidationError if the String is not a valid as a single search term for
     * any type of search.  Only checks for an alphanumeric character.
     */
    private static void validateSearchSubTerm(String s) throws ValidationException {
        final int numContig = 1;
        if (!hasContiguousAlphaNumeric(s, numContig))
            throw new ValidationException("Term (" + s + ") must have at least " + numContig + " alphanumeric character");
    }
    
    /**
     * Return a valid search term for LIKE searches in standard SQL, by checking and 
     * possibly altering the input string.
     * 
     * Invalid situations are:
     * starts with OR
     * ends with OR or NOT
     * contains a reserved word, such as AND, STEM, NEAR, or other reserved word
     * 
     * The only modification done at this time is to remove extra spaces, if any.
     * 
     * @param the string to check.
     */
    public static String validateDBLikeString(String s) throws ValidationException {
        String result = s.trim();
        validateSearchSubTerm(result);
        if (!isAllValidDbLikeChars(s))
            throw new ValidationException(s + " contains invalid character(s): " + findInvalidCondition(s, _reservedDbLikeSymbols));
        return result;
    }
    
    /**
     * Return a valid search term for intermedia, by checking and possibly altering the input string.
     * Invalid situations are:
     * starts with OR
     * ends with OR or NOT
     * contains a reserved word, such as AND, STEM, NEAR, or other reserved word
     * 
     * The only modification done at this time is to remove extra spaces, if any.
     * 
     * @param the string to check.
     */
    public static String validateIntermediaSearchString(String s) throws ValidationException {
        StringBuffer result = new StringBuffer();
        StringTokenizer st = new StringTokenizer(s, " ");
        if (!st.hasMoreTokens())
            throw new ValidationException("The search term is all spaces.");
        boolean isFirst = true;
        while (st.hasMoreTokens()) {
            String trimtok = st.nextToken().trim();
            validateSearchSubTerm(trimtok);
            String tokUpper = trimtok.toUpperCase();
            if (!isAllValidIntermediaChars(tokUpper))
                throw new ValidationException(s + " contains invalid character(s): " + findInvalidCondition(s, _validIntermediaCharacters));
            if (_reservedIntermediaWords.contains(tokUpper))
                throw new ValidationException(trimtok + " is a reserved word");
            
            if (isFirst) { // first tok
                if (tokUpper.equals("OR") || tokUpper.equals("NOT"))
                    throw new ValidationException("Search string cannot begin with 'OR' or 'NOT'");
            }
            if (!st.hasMoreTokens()) { // last tok
                if (tokUpper.equals("OR") || tokUpper.equals("NOT"))
                    throw new ValidationException("Search string cannot end with 'OR' or 'NOT'");
            }

            if (!isFirst)
                result.append(' '); // separate by spaces
            result.append(trimtok);// use trimmed subclauses

            isFirst = false;
        }
        //MR6902 - make sure every wildcard character has a letter or digit immediately
        //adjacent to it on at least one side
        String tempString = result.toString();
        //to avoid having to deal with both wildcard chars, convert % to *
        tempString = tempString.replace('%','*');
        int wildcardPos = tempString.indexOf("*");
        char left;
        char right;
        //if wildcardPos >= 0 we know there is at least one * in the string, so find 
        //them all and verify that there is a letter or digit immediately adjacent to 
        //every instance.
        while (wildcardPos >=0) {
          //if wildcardPos is 0, that means the very first character
          //is a wildcard and in that case we only check the character to the
          //right of it
          if (wildcardPos == 0) {
            right = tempString.charAt(wildcardPos + 1);
            if (!Character.isLetterOrDigit(right)) {
              throw new ValidationException("All wildcard characters must have a letter or digit immediately adjacent to it on at least one side.");              
            }
          }
          //if wildcardPos is the same as the length of the string-1, that means the very 
          //last character is a wildcard and in that case we only check the character to 
          //the left of it
          else if (wildcardPos == tempString.length()-1) {
            left = tempString.charAt(wildcardPos - 1);
            if (!Character.isLetterOrDigit(left)) {
              throw new ValidationException("All wildcard characters must have a letter or digit immediately adjacent to it on at least one side.");              
            }
          }
          //otherwise we're somewhere in the middle so check on either side
          else {
            left = tempString.charAt(wildcardPos - 1);
            right = tempString.charAt(wildcardPos + 1);
            if (!Character.isLetterOrDigit(left) && !Character.isLetterOrDigit(right)) {
              throw new ValidationException("All wildcard characters must have a letter or digit immediately adjacent to it on at least one side.");              
            }
          }
          //find the next occurance, if it exists
          wildcardPos = tempString.indexOf("*", wildcardPos + 1);
        }
        return result.toString();
    }
    
    // for our purposes here (searchin) space is "alphanumeric
//    private final static boolean isAlphaNumSpace(char c) {
//        return Character.isLetterOrDigit(c) || Character.isSpaceChar(c);
//    }
    
    private final static boolean isValidIntermediaChar(char c) {
        return _validIntermediaCharacters.indexOf(c) > -1;
    }
    
    private final static boolean isValidDBLikeChar(char c) {
        return _reservedDbLikeSymbols.indexOf(c) == -1;
    }
    
    
    
    
    /*****************************************************************************
     * ***************************************************************************
     * ***************************************************************************
     *              TEST CASES
     * ***************************************************************************
     * ***************************************************************************
     * ***************************************************************************/
    
    public static void main(String[] args) {
        // strings that are valid for both searches
        String ss[] = {"*breast*", "breast", "*breast", "breast*", "breast colon",
            "*breast colon*", "x", "breast OR colon", "breast OR colon",
            "brea*st", "()xya**", "*brea*st*", "breast* colon*", "breast* or *arcinoma", 
            "nottingham", "hello not goodbye",
        };
        String s = "";
        
        for (int i=0; i<ss.length; i++) {
            try {
                s = ss[i];
                validateDBLikeString(s);
                validateIntermediaSearchString(s);
            } catch (Exception e) {
                System.out.println("*** Error validating '" + ss[i] + "' (" + e.getMessage() + ")");
            }
        }
        System.out.println("Positive cases run.  Errors (if any) printed above");


        // strings invalid for both
        ss = new String[] {"%adsf", "ab%cd", " ", "  ", "*", "**", "_abc", "* *"
        };
        for (int i=0; i<ss.length; i++) {
            boolean errFlag = false;
            try {
                s = ss[i];
                validateDBLikeString(s);
            }
            catch (Exception e) {
                try {
                    validateIntermediaSearchString(s);
                } catch (Exception ee) {
                    errFlag = true;  // must fail both with errors to be BOTH invalid
                }
            }
            if (!errFlag)
                System.out.println("*** Did not catch bad search term (both): " + s);
        }
        System.out.println("Negative cases run.  Errors (if any) printed above");



        // valid Intermedia terms that are NOT valid for DB LIKE
        ss = new String[] { 
        };
        for (int i=0; i<ss.length; i++) {
            s = ss[i];
            try {
                validateIntermediaSearchString(s);
            } catch (Exception e) {
                System.out.println("*** Error validating (Intermedia) '" + ss[i] + "' (" + e.getMessage() + ")");
            }
            
            boolean errFlag = false;
            try {
                validateDBLikeString(s);
            } catch (Exception e) {
                errFlag = true;
            }
            if (!errFlag)
                System.out.println("*** Did not catch bad search term (db like): " + s);
        }
        System.out.println("Mixed (intermedia positive, db like negative) run");



        // test terms that are valid db like, but not valid intermedia        
        ss = new String[] { "OR", "NOT", "breast not", "or breast", "a ** b",
            "NEAR", "near", "hello near", "near hello", "WITHIN", "AND", "breast not", 
            "breast and", "not breast", "syn", "colon syn skin", "about hello",
        };
        for (int i=0; i<ss.length; i++) {
            s = ss[i];
            try {
                validateDBLikeString(s);
            } catch (Exception e) {
                System.out.println("*** Error validating (DB LIKE) '" + ss[i] + "' (" + e.getMessage() + ")");
            }
            
            boolean errFlag = false;
            try {
                validateIntermediaSearchString(s);
            } catch (Exception e) {
                errFlag = true;
            }
            if (!errFlag)
                System.out.println("*** Did not catch bad search term (intermedia): " + s);
        }
        System.out.println("Mixed (intermedia positive, db like negative) run");

        
    }
}
