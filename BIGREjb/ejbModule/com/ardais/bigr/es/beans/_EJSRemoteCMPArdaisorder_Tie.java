// Tie class generated by rmic, do not edit.
// Contents subject to change without notice.

package com.ardais.bigr.es.beans;

import java.io.Serializable;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.math.BigDecimal;
import java.rmi.Remote;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.FinderException;
import javax.ejb.Handle;
import javax.ejb.RemoveException;
import javax.rmi.CORBA.Tie;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.CORBA.portable.UnknownException;

public class _EJSRemoteCMPArdaisorder_Tie extends org.omg.CORBA_2_3.portable.ObjectImpl implements Tie {
    
    private EJSRemoteCMPArdaisorder target = null;
    private ORB orb = null;
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.es.beans.Ardaisorder:0000000000000000", 
        "RMI:javax.ejb.EJBObject:0000000000000000", 
        "RMI:com.ibm.ivj.ejb.runtime.CopyHelper:0000000000000000", 
        "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
        "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000"
    };
    
    public void setTarget(Remote target) {
        this.target = (EJSRemoteCMPArdaisorder) target;
    }
    
    public Remote getTarget() {
        return target;
    }
    
    public org.omg.CORBA.Object thisObject() {
        return this;
    }
    
    public void deactivate() {
        if (orb != null) {
            orb.disconnect(this);
            _set_delegate(null);
        }
    }
    
    public ORB orb() {
        return _orb();
    }
    
    public void orb(ORB orb) {
        orb.connect(this);
    }
    
    public void _set_delegate(Delegate del) {
        super._set_delegate(del);
        if (del != null)
            orb = _orb();
        else
            orb = null;
    }
    
    public String[] _ids() { 
        return _type_ids;
    }
    
    public OutputStream _invoke(String method, InputStream _in, ResponseHandler reply) throws SystemException {
        try {
            org.omg.CORBA_2_3.portable.InputStream in = 
                (org.omg.CORBA_2_3.portable.InputStream) _in;
            switch (method.hashCode()) {
                case -2076203351: 
                    if (method.equals("J_copyFromEJB")) {
                        return J_copyFromEJB(in, reply);
                    }
                case -2065425360: 
                    if (method.equals("_get_order_po_number")) {
                        return _get_order_po_number(in, reply);
                    }
                case -1805098751: 
                    if (method.equals("_set_bill_to_addr_id")) {
                        return _set_bill_to_addr_id(in, reply);
                    }
                case -1728685359: 
                    if (method.equals("secondarySetArdaisuser")) {
                        return secondarySetArdaisuser(in, reply);
                    }
                case -1624322192: 
                    if (method.equals("secondaryRemoveArdaisorderstatus")) {
                        return secondaryRemoveArdaisorderstatus(in, reply);
                    }
                case -1616085977: 
                    if (method.equals("_set_ship_instruction")) {
                        return _set_ship_instruction(in, reply);
                    }
                case -1550521068: 
                    if (method.equals("_get_EJBHome")) {
                        return _get_EJBHome(in, reply);
                    }
                case -1321315963: 
                    if (method.equals("_set_order_amount")) {
                        return _set_order_amount(in, reply);
                    }
                case -1011244123: 
                    if (method.equals("_get_primaryKey")) {
                        return _get_primaryKey(in, reply);
                    }
                case -1005162573: 
                    if (method.equals("_get_ship_instruction")) {
                        return _get_ship_instruction(in, reply);
                    }
                case -934610812: 
                    if (method.equals("remove")) {
                        return remove(in, reply);
                    }
                case -799944417: 
                    if (method.equals("_set_order_status")) {
                        return _set_order_status(in, reply);
                    }
                case -752134117: 
                    if (method.equals("_set_order_date")) {
                        return _set_order_date(in, reply);
                    }
                case -590814155: 
                    if (method.equals("secondaryAddOrderline")) {
                        return secondaryAddOrderline(in, reply);
                    }
                case -579317287: 
                    if (method.equals("getArdaisuser")) {
                        return getArdaisuser(in, reply);
                    }
                case -450897452: 
                    if (method.equals("_get_ardaisuserKey")) {
                        return _get_ardaisuserKey(in, reply);
                    }
                case -443256078: 
                    if (method.equals("getArdaisorderstatus")) {
                        return getArdaisorderstatus(in, reply);
                    }
                case -400469620: 
                    if (method.equals("getOrderline")) {
                        return getOrderline(in, reply);
                    }
                case -261370891: 
                    if (method.equals("_get_bill_to_addr_id")) {
                        return _get_bill_to_addr_id(in, reply);
                    }
                case 73862154: 
                    if (method.equals("secondaryRemoveOrderline")) {
                        return secondaryRemoveOrderline(in, reply);
                    }
                case 176000952: 
                    if (method.equals("J_copyToEJB")) {
                        return J_copyToEJB(in, reply);
                    }
                case 194813925: 
                    if (method.equals("setArdaisuser")) {
                        return setArdaisuser(in, reply);
                    }
                case 439550106: 
                    if (method.equals("_set_approved_date")) {
                        return _set_approved_date(in, reply);
                    }
                case 510126865: 
                    if (method.equals("_get_order_amount")) {
                        return _get_order_amount(in, reply);
                    }
                case 685814076: 
                    if (method.equals("_set_order_po_number")) {
                        return _set_order_po_number(in, reply);
                    }
                case 733432987: 
                    if (method.equals("secondaryAddArdaisorderstatus")) {
                        return secondaryAddArdaisorderstatus(in, reply);
                    }
                case 882656221: 
                    if (method.equals("privateSetArdaisuserKey")) {
                        return privateSetArdaisuserKey(in, reply);
                    }
                case 1019602087: 
                    if (method.equals("_get_order_date")) {
                        return _get_order_date(in, reply);
                    }
                case 1031498411: 
                    if (method.equals("_get_order_status")) {
                        return _get_order_status(in, reply);
                    }
                case 1197857071: 
                    if (method.equals("_set_approval_user_id")) {
                        return _set_approval_user_id(in, reply);
                    }
                case 1264756395: 
                    if (method.equals("isIdentical")) {
                        return isIdentical(in, reply);
                    }
                case 1379702926: 
                    if (method.equals("_get_approved_date")) {
                        return _get_approved_date(in, reply);
                    }
                case 1808780475: 
                    if (method.equals("_get_approval_user_id")) {
                        return _get_approval_user_id(in, reply);
                    }
                case 1944413392: 
                    if (method.equals("_get_handle")) {
                        return _get_handle(in, reply);
                    }
            }
            throw new BAD_OPERATION();
        } catch (SystemException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new UnknownException(ex);
        }
    }
    
    private OutputStream _get_EJBHome(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        EJBHome result = target.getEJBHome();
        OutputStream out = reply.createReply();
        Util.writeRemoteObject(out,result);
        return out;
    }
    
    private OutputStream _get_primaryKey(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Object result = target.getPrimaryKey();
        OutputStream out = reply.createReply();
        Util.writeAny(out,result);
        return out;
    }
    
    private OutputStream remove(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        try {
            target.remove();
        } catch (RemoveException ex) {
            String id = "IDL:javax/ejb/RemoveEx:1.0";
            org.omg.CORBA_2_3.portable.OutputStream out = 
                (org.omg.CORBA_2_3.portable.OutputStream) reply.createExceptionReply();
            out.write_string(id);
            out.write_value(ex,RemoveException.class);
            return out;
        }
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_handle(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Handle result = target.getHandle();
        OutputStream out = reply.createReply();
        Util.writeAbstractObject(out,result);
        return out;
    }
    
    private OutputStream isIdentical(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        EJBObject arg0 = (EJBObject) in.read_Object(EJBObject.class);
        boolean result = target.isIdentical(arg0);
        OutputStream out = reply.createReply();
        out.write_boolean(result);
        return out;
    }
    
    private OutputStream J_copyFromEJB(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Hashtable result = target._copyFromEJB();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Hashtable.class);
        return out;
    }
    
    private OutputStream J_copyToEJB(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Hashtable arg0 = (Hashtable) in.read_value(Hashtable.class);
        target._copyToEJB(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_approval_user_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getApproval_user_id();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream getArdaisorderstatus(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Enumeration result;
        try {
            result = target.getArdaisorderstatus();
        } catch (FinderException ex) {
            String id = "IDL:javax/ejb/FinderEx:1.0";
            org.omg.CORBA_2_3.portable.OutputStream out = 
                (org.omg.CORBA_2_3.portable.OutputStream) reply.createExceptionReply();
            out.write_string(id);
            out.write_value(ex,FinderException.class);
            return out;
        }
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,Enumeration.class);
        return out;
    }
    
    private OutputStream getArdaisuser(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Ardaisuser result;
        try {
            result = target.getArdaisuser();
        } catch (FinderException ex) {
            String id = "IDL:javax/ejb/FinderEx:1.0";
            org.omg.CORBA_2_3.portable.OutputStream out = 
                (org.omg.CORBA_2_3.portable.OutputStream) reply.createExceptionReply();
            out.write_string(id);
            out.write_value(ex,FinderException.class);
            return out;
        }
        OutputStream out = reply.createReply();
        Util.writeRemoteObject(out,result);
        return out;
    }
    
    private OutputStream _get_ardaisuserKey(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        ArdaisuserKey result = target.getArdaisuserKey();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,ArdaisuserKey.class);
        return out;
    }
    
    private OutputStream _get_bill_to_addr_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        BigDecimal result = target.getBill_to_addr_id();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,BigDecimal.class);
        return out;
    }
    
    private OutputStream _get_order_amount(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        BigDecimal result = target.getOrder_amount();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,BigDecimal.class);
        return out;
    }
    
    private OutputStream _get_order_date(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp result = target.getOrder_date();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Timestamp.class);
        return out;
    }
    
    private OutputStream _get_order_po_number(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getOrder_po_number();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_order_status(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getOrder_status();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream getOrderline(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Enumeration result;
        try {
            result = target.getOrderline();
        } catch (FinderException ex) {
            String id = "IDL:javax/ejb/FinderEx:1.0";
            org.omg.CORBA_2_3.portable.OutputStream out = 
                (org.omg.CORBA_2_3.portable.OutputStream) reply.createExceptionReply();
            out.write_string(id);
            out.write_value(ex,FinderException.class);
            return out;
        }
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,Enumeration.class);
        return out;
    }
    
    private OutputStream _get_ship_instruction(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getShip_instruction();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream privateSetArdaisuserKey(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        ArdaisuserKey arg0 = (ArdaisuserKey) in.read_value(ArdaisuserKey.class);
        target.privateSetArdaisuserKey(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryAddArdaisorderstatus(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Ardaisorderstatus arg0 = (Ardaisorderstatus) in.read_Object(Ardaisorderstatus.class);
        target.secondaryAddArdaisorderstatus(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryAddOrderline(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Orderline arg0 = (Orderline) in.read_Object(Orderline.class);
        target.secondaryAddOrderline(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryRemoveArdaisorderstatus(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Ardaisorderstatus arg0 = (Ardaisorderstatus) in.read_Object(Ardaisorderstatus.class);
        target.secondaryRemoveArdaisorderstatus(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryRemoveOrderline(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Orderline arg0 = (Orderline) in.read_Object(Orderline.class);
        target.secondaryRemoveOrderline(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondarySetArdaisuser(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Ardaisuser arg0 = (Ardaisuser) in.read_Object(Ardaisuser.class);
        target.secondarySetArdaisuser(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_approval_user_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setApproval_user_id(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream setArdaisuser(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Ardaisuser arg0 = (Ardaisuser) in.read_Object(Ardaisuser.class);
        target.setArdaisuser(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_bill_to_addr_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        BigDecimal arg0 = (BigDecimal) in.read_value(BigDecimal.class);
        target.setBill_to_addr_id(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_order_amount(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        BigDecimal arg0 = (BigDecimal) in.read_value(BigDecimal.class);
        target.setOrder_amount(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_order_date(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp arg0 = (Timestamp) in.read_value(Timestamp.class);
        target.setOrder_date(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_order_po_number(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setOrder_po_number(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_order_status(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setOrder_status(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_ship_instruction(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setShip_instruction(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_approved_date(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp result = target.getApproved_date();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Timestamp.class);
        return out;
    }
    
    private OutputStream _set_approved_date(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp arg0 = (Timestamp) in.read_value(Timestamp.class);
        target.setApproved_date(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
}