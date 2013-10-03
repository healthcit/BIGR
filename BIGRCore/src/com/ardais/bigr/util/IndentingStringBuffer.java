package com.ardais.bigr.util;

import java.util.Arrays;

/**
 * A class that accumulates indented text by the StringBuffer style "append" methods.
 * It also has a configurable indententation so that all newlines are indented properly.
 */
public class IndentingStringBuffer {

    private StringBuffer sb;
    private char[] indentPadding = new char[0];
    
    /**
     * Constructor for IndentingStringBuffer.
     */
    public IndentingStringBuffer() {
        sb = new StringBuffer();
    }

    /**
     * Constructor
     * @param initSize  initial space reserved.
     */
    public IndentingStringBuffer(int initSize) {
        sb = new StringBuffer(initSize);
    }
    
    /**
     * Append the string.  for any newline, precede it by the indentation padding characters
     */
    public void append(String s) {
        int len = s.length();
        for (int i=0; i<len; i++) {
            char c = s.charAt(i);
            sb.append(c);
            if (c=='\n')
                sb.append(indentPadding);
        }
    }
    
    public void append(char c) {
        sb.append(c);
        if (c=='\n')
            sb.append(indentPadding);
    }

    public void setIndent(int numspaces) {
        if (numspaces <= 0)
            indentPadding = new char[0];
        else {
            indentPadding = new char[numspaces];
            Arrays.fill(indentPadding, ' ');
        }
    }
    
    public void increaseIndent(int additionalspaces) {
        setIndent(indentPadding.length + additionalspaces);
    }

    public void decreaseIndent(int additionalspaces) {
        setIndent(indentPadding.length - additionalspaces);
    }
    
    public String toString() {
        return sb.toString();
    }
}
