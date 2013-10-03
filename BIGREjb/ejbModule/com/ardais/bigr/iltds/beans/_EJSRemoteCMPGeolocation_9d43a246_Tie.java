// Tie class generated by rmic, do not edit.
// Contents subject to change without notice.

package com.ardais.bigr.iltds.beans;

import com.ardais.bigr.iltds.assistants.LocationManagementAsst;
import java.io.Serializable;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.rmi.Remote;
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

public class _EJSRemoteCMPGeolocation_9d43a246_Tie extends org.omg.CORBA_2_3.portable.ObjectImpl implements Tie {
    
    private EJSRemoteCMPGeolocation_9d43a246 target = null;
    private ORB orb = null;
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.iltds.beans.Geolocation:0000000000000000", 
        "RMI:javax.ejb.EJBObject:0000000000000000", 
        "RMI:com.ibm.ivj.ejb.runtime.CopyHelper:0000000000000000", 
        "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
        "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000"
    };
    
    public void setTarget(Remote target) {
        this.target = (EJSRemoteCMPGeolocation_9d43a246) target;
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
                case -2103183347: 
                    if (method.equals("_get_location_city")) {
                        return _get_location_city(in, reply);
                    }
                case -2102863571: 
                    if (method.equals("_get_location_name")) {
                        return _get_location_name(in, reply);
                    }
                case -2076203351: 
                    if (method.equals("J_copyFromEJB")) {
                        return J_copyFromEJB(in, reply);
                    }
                case -1899265357: 
                    if (method.equals("_set_location_zip")) {
                        return _set_location_zip(in, reply);
                    }
                case -1550521068: 
                    if (method.equals("_get_EJBHome")) {
                        return _get_EJBHome(in, reply);
                    }
                case -1435232199: 
                    if (method.equals("addConsent")) {
                        return addConsent(in, reply);
                    }
                case -1390613976: 
                    if (method.equals("_set_location_country")) {
                        return _set_location_country(in, reply);
                    }
                case -1297762216: 
                    if (method.equals("updateGeoLoc")) {
                        return updateGeoLoc(in, reply);
                    }
                case -1231132342: 
                    if (method.equals("getBoxlocation")) {
                        return getBoxlocation(in, reply);
                    }
                case -1123307336: 
                    if (method.equals("_set_location_address_1")) {
                        return _set_location_address_1(in, reply);
                    }
                case -1123307335: 
                    if (method.equals("_set_location_address_2")) {
                        return _set_location_address_2(in, reply);
                    }
                case -1011244123: 
                    if (method.equals("_get_primaryKey")) {
                        return _get_primaryKey(in, reply);
                    }
                case -934610812: 
                    if (method.equals("remove")) {
                        return remove(in, reply);
                    }
                case -780788078: 
                    if (method.equals("getArdaisstaff")) {
                        return getArdaisstaff(in, reply);
                    }
                case -779690572: 
                    if (method.equals("_get_location_country")) {
                        return _get_location_country(in, reply);
                    }
                case -762203380: 
                    if (method.equals("_get_location_phone")) {
                        return _get_location_phone(in, reply);
                    }
                case -759088593: 
                    if (method.equals("_get_location_state")) {
                        return _get_location_state(in, reply);
                    }
                case -664830904: 
                    if (method.equals("secondaryRemoveBoxlocation")) {
                        return secondaryRemoveBoxlocation(in, reply);
                    }
                case -214486640: 
                    if (method.equals("secondaryRemoveArdaisstaff")) {
                        return secondaryRemoveArdaisstaff(in, reply);
                    }
                case -67822529: 
                    if (method.equals("_get_location_zip")) {
                        return _get_location_zip(in, reply);
                    }
                case 157830272: 
                    if (method.equals("_set_location_phone")) {
                        return _set_location_phone(in, reply);
                    }
                case 160945059: 
                    if (method.equals("_set_location_state")) {
                        return _set_location_state(in, reply);
                    }
                case 176000952: 
                    if (method.equals("J_copyToEJB")) {
                        return J_copyToEJB(in, reply);
                    }
                case 223493602: 
                    if (method.equals("secondaryRemoveConsent")) {
                        return secondaryRemoveConsent(in, reply);
                    }
                case 370287821: 
                    if (method.equals("secondaryAddConsent")) {
                        return secondaryAddConsent(in, reply);
                    }
                case 480643175: 
                    if (method.equals("addArdaisstaff")) {
                        return addArdaisstaff(in, reply);
                    }
                case 531363251: 
                    if (method.equals("secondaryAddBoxlocation")) {
                        return secondaryAddBoxlocation(in, reply);
                    }
                case 981707515: 
                    if (method.equals("secondaryAddArdaisstaff")) {
                        return secondaryAddArdaisstaff(in, reply);
                    }
                case 1251631129: 
                    if (method.equals("_set_location_city")) {
                        return _set_location_city(in, reply);
                    }
                case 1251950905: 
                    if (method.equals("_set_location_name")) {
                        return _set_location_name(in, reply);
                    }
                case 1264756395: 
                    if (method.equals("isIdentical")) {
                        return isIdentical(in, reply);
                    }
                case 1858531652: 
                    if (method.equals("_get_location_address_1")) {
                        return _get_location_address_1(in, reply);
                    }
                case 1858531653: 
                    if (method.equals("_get_location_address_2")) {
                        return _get_location_address_2(in, reply);
                    }
                case 1944413392: 
                    if (method.equals("_get_handle")) {
                        return _get_handle(in, reply);
                    }
                case 1988361188: 
                    if (method.equals("getConsent")) {
                        return getConsent(in, reply);
                    }
                case 2146992886: 
                    if (method.equals("removeConsent")) {
                        return removeConsent(in, reply);
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
    
    private OutputStream addArdaisstaff(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Ardaisstaff arg0 = (Ardaisstaff) in.read_Object(Ardaisstaff.class);
        target.addArdaisstaff(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream addConsent(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Consent arg0 = (Consent) in.read_Object(Consent.class);
        target.addConsent(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream getArdaisstaff(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Enumeration result;
        try {
            result = target.getArdaisstaff();
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
    
    private OutputStream getBoxlocation(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Enumeration result;
        try {
            result = target.getBoxlocation();
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
    
    private OutputStream getConsent(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Enumeration result;
        try {
            result = target.getConsent();
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
    
    private OutputStream _get_location_address_1(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getLocation_address_1();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_location_address_2(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getLocation_address_2();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_location_city(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getLocation_city();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_location_name(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getLocation_name();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_location_phone(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getLocation_phone();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_location_state(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getLocation_state();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_location_zip(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getLocation_zip();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream removeConsent(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Consent arg0 = (Consent) in.read_Object(Consent.class);
        target.removeConsent(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryAddArdaisstaff(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Ardaisstaff arg0 = (Ardaisstaff) in.read_Object(Ardaisstaff.class);
        target.secondaryAddArdaisstaff(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryAddBoxlocation(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Boxlocation arg0 = (Boxlocation) in.read_Object(Boxlocation.class);
        target.secondaryAddBoxlocation(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryAddConsent(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Consent arg0 = (Consent) in.read_Object(Consent.class);
        target.secondaryAddConsent(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryRemoveArdaisstaff(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Ardaisstaff arg0 = (Ardaisstaff) in.read_Object(Ardaisstaff.class);
        target.secondaryRemoveArdaisstaff(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryRemoveBoxlocation(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Boxlocation arg0 = (Boxlocation) in.read_Object(Boxlocation.class);
        target.secondaryRemoveBoxlocation(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryRemoveConsent(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Consent arg0 = (Consent) in.read_Object(Consent.class);
        target.secondaryRemoveConsent(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_location_address_1(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setLocation_address_1(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_location_address_2(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setLocation_address_2(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_location_city(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setLocation_city(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_location_name(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setLocation_name(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_location_phone(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setLocation_phone(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_location_state(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setLocation_state(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_location_zip(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setLocation_zip(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream updateGeoLoc(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        LocationManagementAsst arg0 = (LocationManagementAsst) in.read_value(LocationManagementAsst.class);
        target.updateGeoLoc(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_location_country(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getLocation_country();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _set_location_country(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setLocation_country(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
}
