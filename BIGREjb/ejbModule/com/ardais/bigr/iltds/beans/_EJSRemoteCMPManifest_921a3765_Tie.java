// Tie class generated by rmic, do not edit.
// Contents subject to change without notice.

package com.ardais.bigr.iltds.beans;

import java.io.Serializable;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
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

public class _EJSRemoteCMPManifest_921a3765_Tie extends org.omg.CORBA_2_3.portable.ObjectImpl implements Tie {
    
    private EJSRemoteCMPManifest_921a3765 target = null;
    private ORB orb = null;
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.iltds.beans.Manifest:0000000000000000", 
        "RMI:javax.ejb.EJBObject:0000000000000000", 
        "RMI:com.ibm.ivj.ejb.runtime.CopyHelper:0000000000000000", 
        "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
        "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000"
    };
    
    public void setTarget(Remote target) {
        this.target = (EJSRemoteCMPManifest_921a3765) target;
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
                case -2084702310: 
                    if (method.equals("_set_ship_verify_datetime")) {
                        return _set_ship_verify_datetime(in, reply);
                    }
                case -2076203351: 
                    if (method.equals("J_copyFromEJB")) {
                        return J_copyFromEJB(in, reply);
                    }
                case -1906629130: 
                    if (method.equals("_get_mnft_create_datetime")) {
                        return _get_mnft_create_datetime(in, reply);
                    }
                case -1863456207: 
                    if (method.equals("_get_receipt_verify_staff_id")) {
                        return _get_receipt_verify_staff_id(in, reply);
                    }
                case -1794731402: 
                    if (method.equals("_get_ship_datetime")) {
                        return _get_ship_datetime(in, reply);
                    }
                case -1769026427: 
                    if (method.equals("_get_ship_verify_staff_id")) {
                        return _get_ship_verify_staff_id(in, reply);
                    }
                case -1550521068: 
                    if (method.equals("_get_EJBHome")) {
                        return _get_EJBHome(in, reply);
                    }
                case -1504687426: 
                    if (method.equals("_set_receipt_datetime")) {
                        return _set_receipt_datetime(in, reply);
                    }
                case -1375051054: 
                    if (method.equals("_get_receipt_verify_datetime")) {
                        return _get_receipt_verify_datetime(in, reply);
                    }
                case -1280621274: 
                    if (method.equals("_get_ship_verify_datetime")) {
                        return _get_ship_verify_datetime(in, reply);
                    }
                case -1070355523: 
                    if (method.equals("removeSamplebox")) {
                        return removeSamplebox(in, reply);
                    }
                case -1011244123: 
                    if (method.equals("_get_primaryKey")) {
                        return _get_primaryKey(in, reply);
                    }
                case -934610812: 
                    if (method.equals("remove")) {
                        return remove(in, reply);
                    }
                case -893764022: 
                    if (method.equals("_get_receipt_datetime")) {
                        return _get_receipt_datetime(in, reply);
                    }
                case -814989169: 
                    if (method.equals("_get_shipment_status")) {
                        return _get_shipment_status(in, reply);
                    }
                case -785940153: 
                    if (method.equals("_set_airbill_tracking_number")) {
                        return _set_airbill_tracking_number(in, reply);
                    }
                case -753160045: 
                    if (method.equals("_get_receipt_by_staff_id")) {
                        return _get_receipt_by_staff_id(in, reply);
                    }
                case 176000952: 
                    if (method.equals("J_copyToEJB")) {
                        return J_copyToEJB(in, reply);
                    }
                case 559593531: 
                    if (method.equals("_get_airbill_tracking_number")) {
                        return _get_airbill_tracking_number(in, reply);
                    }
                case 913060820: 
                    if (method.equals("secondaryAddSamplebox")) {
                        return secondaryAddSamplebox(in, reply);
                    }
                case 975109184: 
                    if (method.equals("addSamplebox")) {
                        return addSamplebox(in, reply);
                    }
                case 1071677921: 
                    if (method.equals("_set_ship_staff_id")) {
                        return _set_ship_staff_id(in, reply);
                    }
                case 1085977405: 
                    if (method.equals("_set_receipt_verify_staff_id")) {
                        return _set_receipt_verify_staff_id(in, reply);
                    }
                case 1095851977: 
                    if (method.equals("_set_mnft_create_staff_id")) {
                        return _set_mnft_create_staff_id(in, reply);
                    }
                case 1103405355: 
                    if (method.equals("getSamplebox")) {
                        return getSamplebox(in, reply);
                    }
                case 1264756395: 
                    if (method.equals("isIdentical")) {
                        return isIdentical(in, reply);
                    }
                case 1299111839: 
                    if (method.equals("_set_receipt_by_staff_id")) {
                        return _set_receipt_by_staff_id(in, reply);
                    }
                case 1560083074: 
                    if (method.equals("_set_ship_datetime")) {
                        return _set_ship_datetime(in, reply);
                    }
                case 1574382558: 
                    if (method.equals("_set_receipt_verify_datetime")) {
                        return _set_receipt_verify_datetime(in, reply);
                    }
                case 1577737129: 
                    if (method.equals("secondaryRemoveSamplebox")) {
                        return secondaryRemoveSamplebox(in, reply);
                    }
                case 1584257130: 
                    if (method.equals("_set_mnft_create_datetime")) {
                        return _set_mnft_create_datetime(in, reply);
                    }
                case 1721859833: 
                    if (method.equals("_set_ship_verify_staff_id")) {
                        return _set_ship_verify_staff_id(in, reply);
                    }
                case 1899933013: 
                    if (method.equals("_get_mnft_create_staff_id")) {
                        return _get_mnft_create_staff_id(in, reply);
                    }
                case 1936250267: 
                    if (method.equals("_set_shipment_status")) {
                        return _set_shipment_status(in, reply);
                    }
                case 1944413392: 
                    if (method.equals("_get_handle")) {
                        return _get_handle(in, reply);
                    }
                case 2011830741: 
                    if (method.equals("_get_ship_staff_id")) {
                        return _get_ship_staff_id(in, reply);
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
    
    private OutputStream addSamplebox(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Samplebox arg0 = (Samplebox) in.read_Object(Samplebox.class);
        target.addSamplebox(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_airbill_tracking_number(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getAirbill_tracking_number();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_mnft_create_datetime(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp result = target.getMnft_create_datetime();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Timestamp.class);
        return out;
    }
    
    private OutputStream _get_mnft_create_staff_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getMnft_create_staff_id();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_receipt_by_staff_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getReceipt_by_staff_id();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_receipt_datetime(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp result = target.getReceipt_datetime();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Timestamp.class);
        return out;
    }
    
    private OutputStream _get_receipt_verify_datetime(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp result = target.getReceipt_verify_datetime();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Timestamp.class);
        return out;
    }
    
    private OutputStream _get_receipt_verify_staff_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getReceipt_verify_staff_id();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream getSamplebox(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Enumeration result;
        try {
            result = target.getSamplebox();
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
    
    private OutputStream _get_ship_datetime(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp result = target.getShip_datetime();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Timestamp.class);
        return out;
    }
    
    private OutputStream _get_ship_staff_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getShip_staff_id();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_ship_verify_datetime(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp result = target.getShip_verify_datetime();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Timestamp.class);
        return out;
    }
    
    private OutputStream _get_ship_verify_staff_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getShip_verify_staff_id();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_shipment_status(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getShipment_status();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream removeSamplebox(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Samplebox arg0 = (Samplebox) in.read_Object(Samplebox.class);
        target.removeSamplebox(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryAddSamplebox(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Samplebox arg0 = (Samplebox) in.read_Object(Samplebox.class);
        target.secondaryAddSamplebox(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryRemoveSamplebox(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Samplebox arg0 = (Samplebox) in.read_Object(Samplebox.class);
        target.secondaryRemoveSamplebox(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_airbill_tracking_number(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setAirbill_tracking_number(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_mnft_create_datetime(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp arg0 = (Timestamp) in.read_value(Timestamp.class);
        target.setMnft_create_datetime(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_mnft_create_staff_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setMnft_create_staff_id(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_receipt_by_staff_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setReceipt_by_staff_id(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_receipt_datetime(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp arg0 = (Timestamp) in.read_value(Timestamp.class);
        target.setReceipt_datetime(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_receipt_verify_datetime(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp arg0 = (Timestamp) in.read_value(Timestamp.class);
        target.setReceipt_verify_datetime(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_receipt_verify_staff_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setReceipt_verify_staff_id(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_ship_datetime(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp arg0 = (Timestamp) in.read_value(Timestamp.class);
        target.setShip_datetime(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_ship_staff_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setShip_staff_id(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_ship_verify_datetime(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp arg0 = (Timestamp) in.read_value(Timestamp.class);
        target.setShip_verify_datetime(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_ship_verify_staff_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setShip_verify_staff_id(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_shipment_status(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setShipment_status(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
}
