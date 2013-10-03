package com.ardais.bigr.orm.helpers;

import com.ardais.bigr.api.*;

/**
 * Determine if the user's browser is supported by our application.
 * To use this class, pass the browser's HTTP User-Agent string to
 * the <code>setUserAgent</code> method and then call
 * <code>isBrowserSupported</code>.  You can get the User-Agent string
 * from the servlet request object.
 *
 * Creation date: (10/25/2001 3:50:30 PM)
 * @author: Gregg Yost
 */
public class BrowserData {
	private String _browser;
	private int _majorVersion;
	private int _minorVersion;
	private String _platform;
/**
 * BrowserData constructor comment.
 */
public BrowserData() {
	super();
	clear();
}
/**
 * Sets all of the browser-info fields to empty/initial values.
 *.
 * Creation date: (10/25/2001 5:48:28 PM)
 */
private void clear() {
	_browser = "";
	_platform = "Other";
	_majorVersion = 0;
	_minorVersion = 0;
}
private String getBrowser() {
	return _browser;
}
private int getMajorVersion() {
	return _majorVersion;
}
private int getMinorVersion() {
	return _minorVersion;
}
private String getPlatform() {
	return _platform;
}
/**
 * Returns true if the user's browser (as indicated by the HTTP User-Agent
 * string passed to <code>setUserAgent</code>) is supported by our application.
 *
 * Creation date: (10/25/2001 3:55:51 PM)
 */
public boolean isBrowserSupported() {
	String propValue = ApiProperties.getProperty(ApiResources.SUPPORT_NEWER_BROWSERS);
  //System.out.println("propValue: "+propValue);
	boolean supportNewerBrowsers = ((propValue != null) && propValue.equals("true"));
  //System.out.println(isWin32()+" *** "+isIE5_5Up()+" *** "+isIE5_5());
  //System.out.println("isBrowserSupported: "+(supportNewerBrowsers ? isIE5_5Up() : isIE5_5()));
  
	return (isWin32() && (supportNewerBrowsers ? isIE5_5Up() : isIE5_5()));
}
/**
 * Returns true if the browser is IE, any version.
 *
 * Creation date: (10/25/2001 3:55:51 PM)
 */
private boolean isIE() {
	return (getBrowser().equalsIgnoreCase("MSIE"));
}
/**
 * Returns true if the browser is IE, version 5.5.
 *
 * Creation date: (10/25/2001 3:55:51 PM)
 */
private boolean isIE5_5() {
	return (isIE() && (getMajorVersion() == 5) && (getMinorVersion() == 5));
}
/**
 * Returns true if the browser is IE, version 5.5 or later.
 *
 * Creation date: (10/25/2001 3:55:51 PM)
 */
private boolean isIE5_5Up() {
	return (isIE() && (isIE5_5UpButNotV6() || (getMajorVersion() >= 6)));
}
/**
 * Returns true if the browser is IE, version 5.5 or later,
 * but only if the major version is still 5 (for example,
 * version 5.6 would be ok (if there was one), but v6 wouldn't be).
 *
 * Creation date: (10/25/2001 3:55:51 PM)
 */
private boolean isIE5_5UpButNotV6() {
	return (isIE() && (getMajorVersion() == 5) && (getMinorVersion() >= 5));
}
/**
 * Return true if the platform is any of the various 32-bit varieties
 * of Windows.  This will need to be updated periodically as new OS
 * versions are released.
 *
 * Creation date: (10/25/2001 3:55:51 PM)
 */
private boolean isWin32() {
  String platform = getPlatform().toUpperCase();
  return (platform.equals("NT") ||
	      platform.equals("32") ||
	      platform.equals("98") ||
	      platform.equals("9X") ||
	      platform.equals("95"));
}
/**
 * Parse the specified user agent string and set properties
 * of the class accordingly.
 *
 * Creation date: (10/25/2001 3:55:51 PM)
 *
 * @param ua the HTTP User-Agent string to parse
 */
private void parseUserAgent(String ua) {
	// Since we currently support only IE 5.5 or later, thisis only designed
	// to parse HTTP User-Agent strings sent by IE 5.5 or later, and it may not
	// work for earlier versions of IE or for non-IE browsers.
  
	clear();
 // ua="Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)";
  //System.out.println("User-Agent: "+ua);
  
	// Search for MSIE.
	//
	int bPos = ua.indexOf("MSIE");
	{
		if (bPos < 0) return;
		_browser = "MSIE";
	}
    
	// The next char must be a single whitespace character.
	//
	bPos = bPos + 4;
	char nextChar = ua.charAt(bPos);
	if (! Character.isWhitespace(nextChar)) return;
    
	// Get the major version.
	//
	{
		bPos = bPos + 1;
		StringBuffer majorVerBuf = new StringBuffer();
		boolean haveNumber = true;
		while (haveNumber) {
 			nextChar = ua.charAt(bPos);
			if (Character.isDigit(nextChar)) {
				majorVerBuf.append(nextChar);
				bPos = bPos + 1;
			}
			else {
				haveNumber = false;
			}
		}
		String majorVer = majorVerBuf.toString();
   // System.out.println("majorVer: "+majorVer);
		if (majorVer.length() > 0) {
			_majorVersion = Integer.parseInt(majorVer);
		}
	}

	// The next character must be a "."
	//
	if (nextChar != '.') return;
  
	// Get the minor version.
	//
	{
		bPos = bPos + 1;
		StringBuffer minorVerBuf = new StringBuffer();
		boolean haveNumber = true;
		while (haveNumber) {
 			nextChar = ua.charAt(bPos);
			if (Character.isDigit(nextChar)) {
				minorVerBuf.append(nextChar);
				bPos = bPos + 1;
			}
			else {
				haveNumber = false;
			}
		}
		String minorVer = minorVerBuf.toString();
     //System.out.println("minorVer: "+minorVer);
		if (minorVer.length() > 0) {
			_minorVersion = Integer.parseInt(minorVer);
		}
	}
  
	// Look for the platform.
	//
	if (ua.indexOf("95", bPos) >= 0) {
		_platform = "95";
		return;
	}

	if (ua.indexOf("98", bPos) >= 0) {
		_platform = "98";
		return;
	}

	if (ua.indexOf("NT", bPos) >= 0) {
		_platform = "NT";
		return;
	}

	if (ua.indexOf("9x", bPos) >= 0) {
		_platform = "9x";
		return;
	}

	if (ua.indexOf("32", bPos) >= 0) {
		_platform = "32";
		return;
	}

	if (ua.indexOf("3.1", bPos) >= 0) {
		_platform = "3.1";
		return;
	}

	if (ua.indexOf("Mac", bPos) >= 0) {
		_platform = "Mac";
		return;
	}

	if (ua.indexOf("X11", bPos) >= 0) {
		_platform = "X11";
		return;
	}
}
/**
 * Sets the HTTP User-Agent string used by this class.
 * This must be called before calling any of the informational
 * methods such as <code>isBrowserSupported</code>.
 *
 * Creation date: (10/25/2001 3:56:14 PM)
 */
public void setUserAgent(String userAgent) {
	parseUserAgent(userAgent);
}
}
