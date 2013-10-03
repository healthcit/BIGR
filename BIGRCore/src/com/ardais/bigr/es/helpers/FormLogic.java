package com.ardais.bigr.es.helpers;

import java.util.Hashtable;
/**
 * Form Logic contains logic code and common keys.
 * Creation date: (3/7/2001 5:08:33 PM)
 * @author: Jake Thompson
 */
public class FormLogic {
    public final static String SEQ_REQUISITION = "ES_ARDAIS_REQUISITION_SEQ";
    public final static String SEQ_ORDER_NUMBER = "ARD_ORDER_NUMBER_SEQ";
    public final static String SEQ_SHIP_NUMBER = "ARD_ORDER_SHIPNO_SEQ";
    public final static String ORDER_INPROCESS = "IP";
    public final static String ORDER_SHIPPED = "SH";
    public final static String ORDER_OPEN = "OP";
    public final static String ORDER_PENDING_PAYMENT = "PP";
    public final static String ORDER_AWAITING_SHIPMENT = "WS";
    public final static String ORDER_PENDING_APPROVAL = "PA";
    public final static String ORDER_RECEIPT_CONFIRMED = "RC";
    public final static String ORDER_CANCELLED = "CA";
    public final static String ADDRESS_TYPE_SHIPPING = "shipto";
    public final static String ADDRESS_TYPE_CONTACT = "contact";
    public final static String ADDRESS_TYPE_BILLING = "Bill to";
    public final static String ADDRESS_TYPE_SYSTEM_GENERATED = "System Gen";
    public final static String ORDER_CLOSED = "CL";
    public final static String SEQ_ADDRESS_ID = "ARD_ADDRID_SEQ";
    public final static String ADDR_SHIP_TO = "Ship To";
    public final static String ADDR_CONTACT = "Contact";

    public final static String CONSORTIUM_MEMBER = "T";
    public final static String CONSORTIUM_NON_MEMBER = "F";
    public final static String RESERVED_IND = "R";

    // Session ans request keys
    public final static String USER_KEY = "user";
    public final static String ACCOUNT_KEY = "account";
    public final static String ORDERS_KEY = "orders";
    public final static String ORDER_KEY = "order";
    public final static String SHIPTO_KEY = "shipAddress";
    public final static String BILLTO_KEY = "billAddress";
    public final static String SAMPLES_KEY = "samples";
    public final static String SAMPLE_KEY = "sample";
    public final static String TOTAL_KEY = "total";
    public final static String PONUMBER_KEY = "poNumber";
    public final static String TISSUE_KEY = "tissue";
    public final static String DIAGNOSIS_KEY = "diagnosis";
    public final static String APPEARANCE_KEY = "appearance";
    public final static String DONOR_KEY = "donor";
    public final static String CASE_KEY = "case";
    public final static String GENDER_KEY = "gender";
    public final static String LINKED_KEY = "linked";
    public final static String DATE_KEY = "date";
    public final static String DATEFROM_KEY = "dateFrom";
    public final static String DATETO_KEY = "dateTo";
    public final static String PATHVERIFIED_KEY = "pathverified";
    public final static String PAIRED_KEY = "paired";

    public final static String THUMBURL_KEY = "api.pathimage.thumbURL";
    public final static String IMAGEURL_KEY = "api.pathimage.imageURL";

    // -- RNA Image Type codes
    public static final String RNA_IMAGE_PATH4X_TYPE = "PATH4X";
    public static final String RNA_IMAGE_PATH20X_TYPE = "PATH20X";
    public static final String RNA_IMAGE_BIO_TYPE = "BIO";
    public static final String RNA_IMAGE_GEL_TYPE = "GEL";
    public static final String RNA_IMAGE_RTPCR_TYPE = "RTPCR";
    
    
    public static final String RNA_IMAGE_URL_ROOT = "api.rnaimageroot";
    
    public static final String RNA_IMAGE_PATH4X_URL_DIRECTORY = "api.rnaimagedirectory.path4x";
    public static final String RNA_IMAGE_PATH20X_URL_DIRECTORY = "api.rnaimagedirectory.path20x";
    public static final String RNA_IMAGE_BIO_URL_DIRECTORY = "api.rnaimagedirectory.bio";
    public static final String RNA_IMAGE_GEL_URL_DIRECTORY = "api.rnaimagedirectory.gel";
    public static final String RNA_IMAGE_RTPCR_URL_DIRECTORY = "api.rnaimagedirectory.rtpcr";
        
    // Database Codes for storing products of different types
    public static final String RNA_PRODUCT_TYPE = "RN";
    public static final String TISSUE_PRODUCT_TYPE = "TS";
    
/**
 * FormLogic constructor comment.
 */
public FormLogic() {
	super();
}
/**
 * This is used to translate a string to a readable value if it is empty or null.
 * Creation date: (9/20/2001 4:26:07 PM)
 * @return java.lang.String
 * @param string java.lang.String
 */
public String getValue(String value) {
   
    if (value == null) {
        return "Fix me, please!";
    } else if (value.equals(""))
        return ("Data Not Yet Available or Not Applicable");
    else
        return value;
}
/**
 * This is used to translate a string to a readable value if it is empty or null.
 * Creation date: (9/20/2001 4:26:07 PM)
 * @return java.lang.String
 * @param string java.lang.String
 */
public String getValue(Hashtable hash, Object key) {
    String string = (String) hash.get(key);
    if (!hash.containsKey(key))
        return "Fix me, please!";
    else if (string == null || string.equals("") || string.equals(",,"))
        return ("Data Not Yet Available or Not Applicable");
    else
        return string;
}
/**
 * This is used to show the "non-rolled-up" Ardais status
 * @return java.lang.String
 * @param string java.lang.String
 */
public static String translateOrderStatusArdais(String status) {
	if(status == null){
		return "Unknown Status";
	} 
	if (status.equals(ORDER_INPROCESS)) {
		return "In Process";
	}
	if (status.equals(ORDER_SHIPPED)) {
		return "Shipped";
	}
	if (status.equals(ORDER_OPEN)) {
		return "Open";
	}
	if (status.equals(ORDER_PENDING_PAYMENT)) {
		return "Pending Payment";
	}
	if (status.equals(ORDER_AWAITING_SHIPMENT)) {
		return "Awaiting Shipment";
	}
	if (status.equals(ORDER_PENDING_APPROVAL)) {
		return "Pending Approval";
	}
	if (status.equals(ORDER_RECEIPT_CONFIRMED)) {
		return "Received";
	}
	if (status.equals(ORDER_CANCELLED)) {
		return "Cancelled";
	}
	if (status.equals(ORDER_CLOSED)) {
		return "Closed";
	}
	return "Unknown Status";

}
/**
 * This is used to show the "rolled-up" client status
 * @return java.lang.String
 * @param string java.lang.String
 */
public static String translateOrderStatusClient(String status) {
	if(status == null){
		return "Unknown Status";
	} 
	if (status.equals(ORDER_INPROCESS)) {
		return "In Process";
	}
	if (status.equals(ORDER_SHIPPED)) {
		return "Shipped";
	}
	if (status.equals(ORDER_OPEN)) {
		return "Open";
	}
	if (status.equals(ORDER_PENDING_PAYMENT)) {
		return "Received";
	}
	if (status.equals(ORDER_AWAITING_SHIPMENT)) {
		return "In Process";
	}
	if (status.equals(ORDER_PENDING_APPROVAL)) {
		return "Pending Approval";
	}
	if (status.equals(ORDER_RECEIPT_CONFIRMED)) {
		return "Received";
	}
	if (status.equals(ORDER_CANCELLED)) {
		return "Cancelled";
	}
	if (status.equals(ORDER_CLOSED)) {
		return "Received";
	}
	return "Unknown Status";

}
}
