// File: gsbioUtil.js
//
// Depends on: gsbio.js, prototype.js.
//
// Description: Utility functions.

// Create the namespace for KC data elements.
GSBIO.namespace("util");

/**
 * Replaces special HTML characters in the supplied string with their entity equivalents.
 */
GSBIO.util.htmlEscape = function(s) {
  if (s == null) return s;
  return s.replace(/&/g,"&amp;").replace(/'/g, "&#39;").replace(/"/g,"&quot;").replace(/</g,"&lt;").replace(/>/g,"&gt;");
};

/**
 * Replaces special HTML characters in the supplied string with their entity equivalents and
 * preserves whitepsace, adding <BR> elements and &nbsp; entities as necessary.
 */
GSBIO.util.htmlEscapeAndPreserveWhitespace = function(s) {
  if (s == null) return s;
  return GSBIO.util.htmlEscape(s).replace(/\n/g,"<br>").replace(/ /g,"&nbsp;");
};
