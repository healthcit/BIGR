package com.ardais.bigr.es.javabeans;

import java.util.ArrayList;

import com.ardais.bigr.iltds.databeans.LabelValueBean;
/**
 * Insert the type's description here.
 * Creation date: (2/15/2002 9:43:41 AM)
 * @author: Jake Thompson
 */
public class QueryBean implements java.io.Serializable {
    private static java.util.Collection ageAtOptions;
    private static java.util.Collection ageAtOptionsStart;
    private static java.util.Collection ageAtOptionsEnd;

    private String accountID;
    private String userID;
    private String donorID;
    private String caseID;
    private String shoppingCartUserId;
    private String orderNumber;

    private String[] samples;

    private String op;

    private String ageAtStart;
    private String ageAtEnd;

    private String fromDate;
    private String toDate;

    private String diagnosisLike;
    private String tissueLike;

    private String[] gender = { "" };
    private String[] appearance = { "" };
    private boolean pathVerified = false;
    private boolean restricted = false;
    private boolean paired = false;
    private String[] linked = { "" };
    private boolean anyLinked = true;
    private boolean sampleDx = false;

    private java.util.ArrayList diagnosisList = new java.util.ArrayList();
    private java.util.ArrayList tissueList = new java.util.ArrayList();
    private java.util.ArrayList caseList = new java.util.ArrayList();
    private java.util.ArrayList sampleList = new java.util.ArrayList();

    private final static long serialVersionUID = -9057584092171735822L;
/**
 * QueryBean constructor comment.
 */
public QueryBean() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (3/12/2002 7:57:28 AM)
 * @return java.lang.String
 * @param string java.lang.String
 */
private String cleanLike(String string) {
	String newString = string;
	newString = newString.replace('*', '%');
	newString = newString.replace('?', '_');
	return newString;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return java.lang.String
 */
public java.lang.String getAccountID() {
	return accountID;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 2:20:44 PM)
 * @return java.lang.String
 */
public java.lang.String getAgeAtEnd() {
	return ageAtEnd;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 1:52:56 PM)
 * @return java.util.Collection
 */
public java.util.Collection getAgeAtOptions() {
	if(ageAtOptions != null){
		return ageAtOptions;
	}

	ageAtOptions = new ArrayList();
	ageAtOptions.add(new LabelValueBean("0","0"));
    ageAtOptions.add(new LabelValueBean("5","5"));
    ageAtOptions.add(new LabelValueBean("10","10"));
    ageAtOptions.add(new LabelValueBean("15","15"));
    ageAtOptions.add(new LabelValueBean("20","20"));
    ageAtOptions.add(new LabelValueBean("25","25"));
    ageAtOptions.add(new LabelValueBean("30","30"));
    ageAtOptions.add(new LabelValueBean("35","35"));
    ageAtOptions.add(new LabelValueBean("40","40"));
    ageAtOptions.add(new LabelValueBean("45","45"));
    ageAtOptions.add(new LabelValueBean("50","50"));
    ageAtOptions.add(new LabelValueBean("55","55"));
    ageAtOptions.add(new LabelValueBean("60","60"));
    ageAtOptions.add(new LabelValueBean("65","65"));
    ageAtOptions.add(new LabelValueBean("70","70"));
    ageAtOptions.add(new LabelValueBean("75","75"));
    ageAtOptions.add(new LabelValueBean("80","80"));
    ageAtOptions.add(new LabelValueBean("85","85"));
    ageAtOptions.add(new LabelValueBean("90+","90"));
    
	return ageAtOptions;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 1:52:56 PM)
 * @return java.util.Collection
 */
public java.util.Collection getAgeAtOptionsEnd() {
	if(ageAtOptionsEnd != null){
		return ageAtOptionsEnd;
	}

	ageAtOptionsEnd = new ArrayList();
    ageAtOptionsEnd.add(new LabelValueBean("5","5"));
    ageAtOptionsEnd.add(new LabelValueBean("10","10"));
    ageAtOptionsEnd.add(new LabelValueBean("15","15"));
    ageAtOptionsEnd.add(new LabelValueBean("20","20"));
    ageAtOptionsEnd.add(new LabelValueBean("25","25"));
    ageAtOptionsEnd.add(new LabelValueBean("30","30"));
    ageAtOptionsEnd.add(new LabelValueBean("35","35"));
    ageAtOptionsEnd.add(new LabelValueBean("40","40"));
    ageAtOptionsEnd.add(new LabelValueBean("45","45"));
    ageAtOptionsEnd.add(new LabelValueBean("50","50"));
    ageAtOptionsEnd.add(new LabelValueBean("55","55"));
    ageAtOptionsEnd.add(new LabelValueBean("60","60"));
    ageAtOptionsEnd.add(new LabelValueBean("65","65"));
    ageAtOptionsEnd.add(new LabelValueBean("70","70"));
    ageAtOptionsEnd.add(new LabelValueBean("75","75"));
    ageAtOptionsEnd.add(new LabelValueBean("80","80"));
    ageAtOptionsEnd.add(new LabelValueBean("85","85"));
    ageAtOptionsEnd.add(new LabelValueBean("90","90"));
    ageAtOptionsEnd.add(new LabelValueBean("95+","999"));
    
	return ageAtOptionsEnd;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 1:52:56 PM)
 * @return java.util.Collection
 */
public java.util.Collection getAgeAtOptionsStart() {
	if(ageAtOptionsStart != null){
		return ageAtOptionsStart;
	}

	ageAtOptionsStart = new ArrayList();
	ageAtOptionsStart.add(new LabelValueBean("0","0"));
    ageAtOptionsStart.add(new LabelValueBean("5","5"));
    ageAtOptionsStart.add(new LabelValueBean("10","10"));
    ageAtOptionsStart.add(new LabelValueBean("15","15"));
    ageAtOptionsStart.add(new LabelValueBean("20","20"));
    ageAtOptionsStart.add(new LabelValueBean("25","25"));
    ageAtOptionsStart.add(new LabelValueBean("30","30"));
    ageAtOptionsStart.add(new LabelValueBean("35","35"));
    ageAtOptionsStart.add(new LabelValueBean("40","40"));
    ageAtOptionsStart.add(new LabelValueBean("45","45"));
    ageAtOptionsStart.add(new LabelValueBean("50","50"));
    ageAtOptionsStart.add(new LabelValueBean("55","55"));
    ageAtOptionsStart.add(new LabelValueBean("60","60"));
    ageAtOptionsStart.add(new LabelValueBean("65","65"));
    ageAtOptionsStart.add(new LabelValueBean("70","70"));
    ageAtOptionsStart.add(new LabelValueBean("75","75"));
    ageAtOptionsStart.add(new LabelValueBean("80","80"));
    ageAtOptionsStart.add(new LabelValueBean("85","85"));
    ageAtOptionsStart.add(new LabelValueBean("90","90"));
    
	return ageAtOptionsStart;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 2:20:44 PM)
 * @return java.lang.String
 */
public java.lang.String getAgeAtStart() {
	return ageAtStart;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 11:38:21 AM)
 * @return java.lang.String[]
 */
public java.lang.String[] getAppearance() {
	return appearance;
}

/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return java.lang.String
 */
public java.lang.String getCaseID() {
	return caseID;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return java.util.ArrayList
 */
public java.util.ArrayList getCaseList() {
	return caseList;
}
/**
 * Insert the method's description here.
 * Creation date: (3/12/2002 7:55:52 AM)
 * @return java.lang.String
 */
public java.lang.String getDiagnosisLike() {
	return diagnosisLike;
}
/**
 * Insert the method's description here.
 * Creation date: (3/12/2002 7:55:52 AM)
 * @return java.lang.String
 */
public java.lang.String getDiagnosisLikeForQuery() {
	return (diagnosisLike == null) ? null : cleanLike(diagnosisLike).toUpperCase();
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return java.util.ArrayList
 */
public java.util.ArrayList getDiagnosisList() {
	return diagnosisList;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return java.lang.String
 */
public java.lang.String getDonorID() {
	return donorID;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return java.lang.String
 */
public java.lang.String getFromDate() {
	return fromDate;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 11:38:21 AM)
 * @return java.lang.String[]
 */
public java.lang.String[] getGender() {
	return gender;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 11:38:21 AM)
 * @return java.lang.String[]
 */
public java.lang.String[] getLinked() {
	return linked;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return java.lang.String
 */
public java.lang.String getOp() {
	return op;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return java.lang.String
 */
public java.lang.String getOrderNumber() {
	return orderNumber;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return java.util.ArrayList
 */
public java.util.ArrayList getSampleList() {
	return sampleList;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 11:38:21 AM)
 * @return java.lang.String[]
 */
public java.lang.String[] getSamples() {
	return samples;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return java.lang.String
 */
public java.lang.String getShoppingCartUserId() {
	return shoppingCartUserId;
}
/**
 * Insert the method's description here.
 * Creation date: (3/12/2002 7:55:52 AM)
 * @return java.lang.String
 */
public java.lang.String getTissueLike() {
	return tissueLike;
}
/**
 * Insert the method's description here.
 * Creation date: (3/12/2002 7:55:52 AM)
 * @return java.lang.String
 */
public java.lang.String getTissueLikeForQuery() {
	return (tissueLike == null) ? null : cleanLike(tissueLike).toUpperCase();
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return java.util.ArrayList
 */
public java.util.ArrayList getTissueList() {
	return tissueList;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return java.lang.String
 */
public java.lang.String getToDate() {
	return toDate;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return java.lang.String
 */
public java.lang.String getUserID() {
	return userID;
}

/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 11:21:57 AM)
 * @return boolean
 */
public boolean isAnyLinked() {
	return anyLinked;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return boolean
 */
public boolean isPaired() {
	return paired;
}


/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return boolean
 */
public boolean isRestricted() {
	return restricted;
}

public boolean isPathVerified() {
	return pathVerified;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return boolean
 */
public boolean isSampleDx() {
	return sampleDx;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @param newAccountID java.lang.String
 */
public void setAccountID(java.lang.String newAccountID) {
    if (newAccountID != null && !newAccountID.equals(""))
        accountID = newAccountID;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 2:20:44 PM)
 * @param newAgeAtEnd java.lang.String
 */
public void setAgeAtEnd(java.lang.String newAgeAtEnd) {
	ageAtEnd = newAgeAtEnd;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 2:20:44 PM)
 * @param newAgeAtStart java.lang.String
 */
public void setAgeAtStart(java.lang.String newAgeAtStart) {
	ageAtStart = newAgeAtStart;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 11:21:57 AM)
 * @param newAnyLinked boolean
 */
public void setAnyLinked(boolean newAnyLinked) {
	anyLinked = newAnyLinked;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 11:38:21 AM)
 * @param newAppearance java.lang.String[]
 */
public void setAppearance(java.lang.String[] newAppearance) {
	appearance = newAppearance;
}

/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @param newCaseID java.lang.String
 */
public void setCaseID(java.lang.String newCaseID) {
    if (newCaseID != null && !newCaseID.equals(""))
        caseID = newCaseID;
}

public void setCaseList(java.util.ArrayList newCaseList) {
	caseList = newCaseList;
}
/**
 * Insert the method's description here.
 * Creation date: (3/12/2002 7:55:52 AM)
 * @param newDiagnosisLike java.lang.String
 */
public void setDiagnosisLike(java.lang.String newDiagnosisLike) {
	 if (newDiagnosisLike != null && !newDiagnosisLike.equals(""))
        diagnosisLike = newDiagnosisLike;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @param newDiagnosisList java.util.ArrayList
 */
public void setDiagnosisList(java.util.ArrayList newDiagnosisList) {
	diagnosisList = newDiagnosisList;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @param newDonorID java.lang.String
 */
public void setDonorID(java.lang.String newDonorID) {
	if(newDonorID != null && !newDonorID.equals(""))
		donorID = newDonorID;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @param newFromDate java.lang.String
 */
public void setFromDate(java.lang.String newFromDate) {
    if (newFromDate != null && !newFromDate.equals(""))
        fromDate = newFromDate;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 11:38:21 AM)
 * @param newGender java.lang.String[]
 */
public void setGender(java.lang.String[] newGender) {
	gender = newGender;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 11:38:21 AM)
 * @param newLinked java.lang.String[]
 */
public void setLinked(java.lang.String[] newLinked) {
	linked = newLinked;
}

/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return java.lang.String
 */
public void setOp(String newOp) {
	op = newOp;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @return java.lang.String
 */
public void setOrderNumber(String number) {
	orderNumber = number;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @param newPaired boolean
 */
public void setPaired(boolean newPaired) {
	paired = newPaired;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @param newPathVerified boolean
 */
public void setRestricted(boolean newRestricted) {
	restricted = newRestricted;
}

public void setPathVerified(boolean newPathVerified) {
	pathVerified = newPathVerified;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @param newPathVerified boolean
 */
public void setSampleDx(boolean newSampleDx) {
	sampleDx = newSampleDx;
}

public void setSampleList(java.util.ArrayList newSampleList) {
	sampleList = newSampleList;
}
/**
 * Insert the method's description here.
 * Creation date: (6/11/2002 11:38:21 AM)
 * @param newAppearance java.lang.String[]
 */
public void setSamples(java.lang.String[] newSamples) {
	samples = newSamples;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @param newAccountID java.lang.String
 */
public void setShoppingCartUserId(java.lang.String newShoppingCartUserId) {
    if (newShoppingCartUserId != null && !newShoppingCartUserId.equals(""))
        shoppingCartUserId = newShoppingCartUserId;
}
/**
 * Insert the method's description here.
 * Creation date: (3/12/2002 7:55:52 AM)
 * @param newTissueLike java.lang.String
 */
public void setTissueLike(java.lang.String newTissueLike) {
    if (newTissueLike != null && !newTissueLike.equals(""))
        tissueLike = newTissueLike;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @param newTissueList java.util.ArrayList
 */
public void setTissueList(java.util.ArrayList newTissueList) {
	tissueList = newTissueList;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @param newToDate java.lang.String
 */
public void setToDate(java.lang.String newToDate) {
	if(newToDate != null && !newToDate.equals(""))
	toDate = newToDate;
}
/**
 * Insert the method's description here.
 * Creation date: (2/15/2002 9:51:03 AM)
 * @param newUserID java.lang.String
 */
public void setUserID(java.lang.String newUserID) {
    if (newUserID != null && !newUserID.equals(""))
        userID = newUserID;
}
}
