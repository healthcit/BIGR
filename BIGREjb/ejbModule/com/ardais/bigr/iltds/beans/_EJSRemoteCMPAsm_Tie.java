// Tie class generated by rmic, do not edit.
// Contents subject to change without notice.

package com.ardais.bigr.iltds.beans;

import java.io.Serializable;
import java.lang.Integer;
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

public class _EJSRemoteCMPAsm_Tie extends org.omg.CORBA_2_3.portable.ObjectImpl implements Tie {
    
    private EJSRemoteCMPAsm target = null;
    private ORB orb = null;
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.iltds.beans.Asm:0000000000000000", 
        "RMI:javax.ejb.EJBObject:0000000000000000", 
        "RMI:com.ibm.ivj.ejb.runtime.CopyHelper:0000000000000000", 
        "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
        "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000"
    };
    
    public void setTarget(Remote target) {
        this.target = (EJSRemoteCMPAsm) target;
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
                case -2131011204: 
                    if (method.equals("_set_consent_id")) {
                        return _set_consent_id(in, reply);
                    }
                case -2076203351: 
                    if (method.equals("J_copyFromEJB")) {
                        return J_copyFromEJB(in, reply);
                    }
                case -1809029366: 
                    if (method.equals("_get_asm_form_id")) {
                        return _get_asm_form_id(in, reply);
                    }
                case -1766787513: 
                    if (method.equals("_get_asm_entry_date")) {
                        return _get_asm_entry_date(in, reply);
                    }
                case -1687190525: 
                    if (method.equals("_get_module_weight")) {
                        return _get_module_weight(in, reply);
                    }
                case -1550521068: 
                    if (method.equals("_get_EJBHome")) {
                        return _get_EJBHome(in, reply);
                    }
                case -1344065897: 
                    if (method.equals("secondaryAddSample")) {
                        return secondaryAddSample(in, reply);
                    }
                case -1247524614: 
                    if (method.equals("_get_ardais_id")) {
                        return _get_ardais_id(in, reply);
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
                case -898276842: 
                    if (method.equals("_set_asm_form_id")) {
                        return _set_asm_form_id(in, reply);
                    }
                case -871110834: 
                    if (method.equals("removeSample")) {
                        return removeSample(in, reply);
                    }
                case -846753861: 
                    if (method.equals("_set_asm_entry_date")) {
                        return _set_asm_entry_date(in, reply);
                    }
                case -473393402: 
                    if (method.equals("_set_ardais_id")) {
                        return _set_ardais_id(in, reply);
                    }
                case -359275000: 
                    if (method.equals("_get_consent_id")) {
                        return _get_consent_id(in, reply);
                    }
                case -157975436: 
                    if (method.equals("_get_organ_site_concept_id")) {
                        return _get_organ_site_concept_id(in, reply);
                    }
                case -16835157: 
                    if (method.equals("addSample")) {
                        return addSample(in, reply);
                    }
                case 176000952: 
                    if (method.equals("J_copyToEJB")) {
                        return J_copyToEJB(in, reply);
                    }
                case 322149009: 
                    if (method.equals("_set_organ_site_concept_id_other")) {
                        return _set_organ_site_concept_id_other(in, reply);
                    }
                case 407517061: 
                    if (method.equals("_get_organ_site_concept_id_other")) {
                        return _get_organ_site_concept_id_other(in, reply);
                    }
                case 433786485: 
                    if (method.equals("_set_specimen_type")) {
                        return _set_specimen_type(in, reply);
                    }
                case 464843677: 
                    if (method.equals("_set_pfin_meets_specs")) {
                        return _set_pfin_meets_specs(in, reply);
                    }
                case 472415691: 
                    if (method.equals("_set_module_comments")) {
                        return _set_module_comments(in, reply);
                    }
                case 685316224: 
                    if (method.equals("_set_organ_site_concept_id")) {
                        return _set_organ_site_concept_id(in, reply);
                    }
                case 786340000: 
                    if (method.equals("getSample")) {
                        return getSample(in, reply);
                    }
                case 1075767081: 
                    if (method.equals("_get_pfin_meets_specs")) {
                        return _get_pfin_meets_specs(in, reply);
                    }
                case 1264756395: 
                    if (method.equals("isIdentical")) {
                        return isIdentical(in, reply);
                    }
                case 1373939305: 
                    if (method.equals("_get_specimen_type")) {
                        return _get_specimen_type(in, reply);
                    }
                case 1667623951: 
                    if (method.equals("_set_module_weight")) {
                        return _set_module_weight(in, reply);
                    }
                case 1944413392: 
                    if (method.equals("_get_handle")) {
                        return _get_handle(in, reply);
                    }
                case 2016143551: 
                    if (method.equals("_get_module_comments")) {
                        return _get_module_comments(in, reply);
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
    
    private OutputStream _get_asm_entry_date(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp result = target.getAsm_entry_date();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Timestamp.class);
        return out;
    }
    
    private OutputStream _get_organ_site_concept_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getOrgan_site_concept_id();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_organ_site_concept_id_other(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getOrgan_site_concept_id_other();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
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
    
    private OutputStream _get_specimen_type(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getSpecimen_type();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
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
    
    private OutputStream _set_asm_entry_date(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp arg0 = (Timestamp) in.read_value(Timestamp.class);
        target.setAsm_entry_date(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_organ_site_concept_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setOrgan_site_concept_id(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_organ_site_concept_id_other(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setOrgan_site_concept_id_other(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_specimen_type(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setSpecimen_type(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_asm_form_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getAsm_form_id();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _set_asm_form_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setAsm_form_id(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_consent_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getConsent_id();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _set_consent_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setConsent_id(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_ardais_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getArdais_id();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _set_ardais_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setArdais_id(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_pfin_meets_specs(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getPfin_meets_specs();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _set_pfin_meets_specs(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setPfin_meets_specs(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_module_weight(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Integer result = target.getModule_weight();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Integer.class);
        return out;
    }
    
    private OutputStream _set_module_weight(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Integer arg0 = (Integer) in.read_value(Integer.class);
        target.setModule_weight(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_module_comments(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getModule_comments();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _set_module_comments(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setModule_comments(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
}