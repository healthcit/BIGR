// Tie class generated by rmic, do not edit.
// Contents subject to change without notice.

package com.ardais.bigr.iltds.beans;

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

public class _EJSRemoteCMPSamplebox_Tie extends org.omg.CORBA_2_3.portable.ObjectImpl implements Tie {
    
    private EJSRemoteCMPSamplebox target = null;
    private ORB orb = null;
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.iltds.beans.Samplebox:0000000000000000", 
        "RMI:javax.ejb.EJBObject:0000000000000000", 
        "RMI:com.ibm.ivj.ejb.runtime.CopyHelper:0000000000000000", 
        "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
        "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000"
    };
    
    public void setTarget(Remote target) {
        this.target = (EJSRemoteCMPSamplebox) target;
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
                case -2088433050: 
                    if (method.equals("_set_box_check_out_date")) {
                        return _set_box_check_out_date(in, reply);
                    }
                case -2086442075: 
                    if (method.equals("getManifest")) {
                        return getManifest(in, reply);
                    }
                case -2076203351: 
                    if (method.equals("J_copyFromEJB")) {
                        return J_copyFromEJB(in, reply);
                    }
                case -2038476131: 
                    if (method.equals("secondarySetManifest")) {
                        return secondarySetManifest(in, reply);
                    }
                case -1850664190: 
                    if (method.equals("_set_box_status")) {
                        return _set_box_status(in, reply);
                    }
                case -1833073595: 
                    if (method.equals("_set_storageTypeCid")) {
                        return _set_storageTypeCid(in, reply);
                    }
                case -1774535026: 
                    if (method.equals("_get_box_note")) {
                        return _get_box_note(in, reply);
                    }
                case -1755650233: 
                    if (method.equals("_get_box_checkout_request_staff_id")) {
                        return _get_box_checkout_request_staff_id(in, reply);
                    }
                case -1550521068: 
                    if (method.equals("_get_EJBHome")) {
                        return _get_EJBHome(in, reply);
                    }
                case -1359433931: 
                    if (method.equals("_get_box_check_in_date")) {
                        return _get_box_check_in_date(in, reply);
                    }
                case -1344065897: 
                    if (method.equals("secondaryAddSample")) {
                        return secondaryAddSample(in, reply);
                    }
                case -1231132342: 
                    if (method.equals("getBoxlocation")) {
                        return getBoxlocation(in, reply);
                    }
                case -1011244123: 
                    if (method.equals("_get_primaryKey")) {
                        return _get_primaryKey(in, reply);
                    }
                case -934610812: 
                    if (method.equals("remove")) {
                        return remove(in, reply);
                    }
                case -933159198: 
                    if (method.equals("secondaryRemoveSample")) {
                        return secondaryRemoveSample(in, reply);
                    }
                case -871110834: 
                    if (method.equals("removeSample")) {
                        return removeSample(in, reply);
                    }
                case -830480644: 
                    if (method.equals("_set_box_check_out_reason")) {
                        return _set_box_check_out_reason(in, reply);
                    }
                case -724088858: 
                    if (method.equals("_get_manifest_order")) {
                        return _get_manifest_order(in, reply);
                    }
                case -664830904: 
                    if (method.equals("secondaryRemoveBoxlocation")) {
                        return secondaryRemoveBoxlocation(in, reply);
                    }
                case -225542398: 
                    if (method.equals("_set_box_note")) {
                        return _set_box_note(in, reply);
                    }
                case -204765176: 
                    if (method.equals("_get_manifestKey")) {
                        return _get_manifestKey(in, reply);
                    }
                case -78927986: 
                    if (method.equals("_get_box_status")) {
                        return _get_box_status(in, reply);
                    }
                case -26399608: 
                    if (method.equals("_get_box_check_out_reason")) {
                        return _get_box_check_out_reason(in, reply);
                    }
                case -16835157: 
                    if (method.equals("addSample")) {
                        return addSample(in, reply);
                    }
                case 30298911: 
                    if (method.equals("addBoxlocation")) {
                        return addBoxlocation(in, reply);
                    }
                case 160480040: 
                    if (method.equals("_get_boxLayoutId")) {
                        return _get_boxLayoutId(in, reply);
                    }
                case 176000952: 
                    if (method.equals("J_copyToEJB")) {
                        return J_copyToEJB(in, reply);
                    }
                case 195944794: 
                    if (method.equals("_set_manifest_order")) {
                        return _set_manifest_order(in, reply);
                    }
                case 440197201: 
                    if (method.equals("privateSetManifestKey")) {
                        return privateSetManifestKey(in, reply);
                    }
                case 531363251: 
                    if (method.equals("secondaryAddBoxlocation")) {
                        return secondaryAddBoxlocation(in, reply);
                    }
                case 786340000: 
                    if (method.equals("getSample")) {
                        return getSample(in, reply);
                    }
                case 893405938: 
                    if (method.equals("_get_box_check_out_date")) {
                        return _get_box_check_out_date(in, reply);
                    }
                case 1071232564: 
                    if (method.equals("_set_boxLayoutId")) {
                        return _set_boxLayoutId(in, reply);
                    }
                case 1176777025: 
                    if (method.equals("_set_box_check_in_date")) {
                        return _set_box_check_in_date(in, reply);
                    }
                case 1264756395: 
                    if (method.equals("isIdentical")) {
                        return isIdentical(in, reply);
                    }
                case 1433737052: 
                    if (method.equals("removeBoxlocation")) {
                        return removeBoxlocation(in, reply);
                    }
                case 1541860049: 
                    if (method.equals("_get_storageTypeCid")) {
                        return _get_storageTypeCid(in, reply);
                    }
                case 1944413392: 
                    if (method.equals("_get_handle")) {
                        return _get_handle(in, reply);
                    }
                case 2104997715: 
                    if (method.equals("_set_box_checkout_request_staff_id")) {
                        return _set_box_checkout_request_staff_id(in, reply);
                    }
                case 2119945393: 
                    if (method.equals("setManifest")) {
                        return setManifest(in, reply);
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
    
    private OutputStream addSample(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Sample arg0 = (Sample) in.read_Object(Sample.class);
        target.addSample(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_box_check_in_date(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp result = target.getBox_check_in_date();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Timestamp.class);
        return out;
    }
    
    private OutputStream _get_box_check_out_date(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp result = target.getBox_check_out_date();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Timestamp.class);
        return out;
    }
    
    private OutputStream _get_box_check_out_reason(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getBox_check_out_reason();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_box_checkout_request_staff_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getBox_checkout_request_staff_id();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_box_note(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getBox_note();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_box_status(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getBox_status();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream getManifest(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Manifest result;
        try {
            result = target.getManifest();
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
    
    private OutputStream _get_manifestKey(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        ManifestKey result = target.getManifestKey();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,ManifestKey.class);
        return out;
    }
    
    private OutputStream getSample(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Enumeration result;
        try {
            result = target.getSample();
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
    
    private OutputStream privateSetManifestKey(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        ManifestKey arg0 = (ManifestKey) in.read_value(ManifestKey.class);
        target.privateSetManifestKey(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream removeSample(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Sample arg0 = (Sample) in.read_Object(Sample.class);
        target.removeSample(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryAddSample(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Sample arg0 = (Sample) in.read_Object(Sample.class);
        target.secondaryAddSample(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryRemoveSample(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Sample arg0 = (Sample) in.read_Object(Sample.class);
        target.secondaryRemoveSample(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondarySetManifest(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Manifest arg0 = (Manifest) in.read_Object(Manifest.class);
        target.secondarySetManifest(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_box_check_in_date(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp arg0 = (Timestamp) in.read_value(Timestamp.class);
        target.setBox_check_in_date(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_box_check_out_date(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp arg0 = (Timestamp) in.read_value(Timestamp.class);
        target.setBox_check_out_date(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_box_check_out_reason(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setBox_check_out_reason(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_box_checkout_request_staff_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setBox_checkout_request_staff_id(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_box_note(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setBox_note(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_box_status(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setBox_status(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream setManifest(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Manifest arg0 = (Manifest) in.read_Object(Manifest.class);
        target.setManifest(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryAddBoxlocation(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Boxlocation arg0 = (Boxlocation) in.read_Object(Boxlocation.class);
        target.secondaryAddBoxlocation(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryRemoveBoxlocation(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Boxlocation arg0 = (Boxlocation) in.read_Object(Boxlocation.class);
        target.secondaryRemoveBoxlocation(arg0);
        OutputStream out = reply.createReply();
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
    
    private OutputStream addBoxlocation(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Boxlocation arg0 = (Boxlocation) in.read_Object(Boxlocation.class);
        target.addBoxlocation(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream removeBoxlocation(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Boxlocation arg0 = (Boxlocation) in.read_Object(Boxlocation.class);
        target.removeBoxlocation(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_manifest_order(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        BigDecimal result = target.getManifest_order();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,BigDecimal.class);
        return out;
    }
    
    private OutputStream _set_manifest_order(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        BigDecimal arg0 = (BigDecimal) in.read_value(BigDecimal.class);
        target.setManifest_order(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_boxLayoutId(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getBoxLayoutId();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _set_boxLayoutId(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setBoxLayoutId(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_storageTypeCid(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getStorageTypeCid();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _set_storageTypeCid(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setStorageTypeCid(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
}
