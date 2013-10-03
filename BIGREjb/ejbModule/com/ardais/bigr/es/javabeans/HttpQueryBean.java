package com.ardais.bigr.es.javabeans;

import java.util.*;
import javax.servlet.http.*;

/**
 * Insert the type's description here.
 * Creation date: (03/28/2002 11:42:41 AM)
 * @author: Jake Thompson
 */
public class HttpQueryBean extends QueryBean {
/**
 * HttpQueryBean constructor comment.
 */
public HttpQueryBean() {
	super();
}
/**
 * HttpQueryBean constructor comment.
 */
public HttpQueryBean(HttpServletRequest request) {
	this();
    setAccountID((String) request.getSession(false).getAttribute("account"));
    setUserID((String) request.getSession(false).getAttribute("user"));
    
	String donorId = request.getParameter("donorID");
    if (donorId != null) {
		donorId = donorId.trim();
		if (!donorId.equals("")) setDonorID(donorId);
    }

	String caseId = request.getParameter("caseID");
    if (caseId != null) {
		caseId = caseId.trim();
		if (!caseId.equals("")) setCaseID(caseId);
    }

	String cart = request.getParameter("shoppingCart");
	if (cart != null) {
		cart = cart.trim();
		if (!cart.equals("")) setShoppingCartUserId(cart);
	}
    
	String number = request.getParameter("orderNumber");
	if (number != null) {
		number = number.trim();
		if (!number.equals("")) setOrderNumber(number);
	}

	String fromDate = request.getParameter("fromDate");
    if (fromDate != null) {
		fromDate = fromDate.trim();
		if (!fromDate.equals("")) setFromDate(fromDate);
    }
    
	String toDate = request.getParameter("toDate");
    if (toDate != null) {
		toDate = toDate.trim();
		if (!toDate.equals("")) setToDate(toDate);
    }

	String diagnosisLike = request.getParameter("diagnosisLike");
    if (diagnosisLike != null) {
	    diagnosisLike = diagnosisLike.trim();
	    if (!diagnosisLike.equals("")) setDiagnosisLike(diagnosisLike);
	    if (isAllWildcards(diagnosisLike)) {
			throw new IllegalArgumentException("The search criteria in the Or Contains field needs to be more specific than a wildcard by itself.");
	    }
    }

	String tissueLike = request.getParameter("tissueLike");
    if (tissueLike != null) {
	    tissueLike = tissueLike.trim();
	    if (!tissueLike.equals("")) setTissueLike(tissueLike);
	    if (isAllWildcards(tissueLike)) {
			throw new IllegalArgumentException("The search criteria in the Or Contains field needs to be more specific than a wildcard by itself.");
	    }
    }

    setCaseList(toArrayList(request.getParameterValues("caseList")));
    setSampleList(toArrayList(request.getParameterValues("sampleList")));

    String paired = request.getParameter("paired");
    if (paired != null && !paired.equals("")) {
	    setPaired(true);
    }

    String pathverified = request.getParameter("pathverified");
    if (pathverified != null && !pathverified.equals("")) {
	    setPathVerified(true);
    }

    // Hashtables setup in session by DXTC Hierarchy
    setDiagnosisList(toArrayList((Hashtable) request.getSession().getAttribute("selectedDX")));
    setTissueList(toArrayList((Hashtable) request.getSession().getAttribute("selectedTC")));

    
    // Check that if a date is chosen, then both are chosen
    if (getFromDate() != null) {
        if (getToDate() != null) {
            try {
                Integer fromInt = new Integer(getFromDate());
                Integer toInt = new Integer(getToDate());
                if (toInt.intValue() < fromInt.intValue()) {
	                throw new IllegalArgumentException("To Date is less than from date.");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Please enter a valid date.");
            }
        } else {
			throw new IllegalArgumentException("Please enter both dates.");
        }

    }
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:59:31 AM)
 * @return java.util.ArrayList
 * @param vector java.lang.Object
 */
private boolean isAllWildcards(String s) {
	if ((s == null) || (s.length() < 1)) {
		return false;
	}

	StringBuffer buf = new StringBuffer(s);
	for (int i = 0; i < s.length(); i++) {
		char c = buf.charAt(i);
		if ((c != '*') && (c != '%') && (c != '?') && (c != '_')) {
			return false;
		}
	}
	return true;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:59:31 AM)
 * @return java.util.ArrayList
 * @param vector java.lang.Object
 */
private ArrayList toArrayList(Object[] array) {
    ArrayList results = new ArrayList();
    if (array != null) {
        for (int i = 0; i < array.length; i++) {
            results.add(array[i]);
        }
    }

    return results;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:59:31 AM)
 * @return java.util.ArrayList
 * @param vector java.lang.Object
 */
private ArrayList toArrayList(Hashtable hashTable) {
    ArrayList results = new ArrayList();

    if (hashTable != null) {
        Enumeration enum1 = hashTable.keys();
        while (enum1.hasMoreElements()) {
            results.add(enum1.nextElement());
        }
    }
    return results;
}
}
