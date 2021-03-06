// Tie class generated by rmic, do not edit.
// Contents subject to change without notice.

package com.ardais.bigr.iltds.beans;

import java.io.Serializable;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.rmi.Remote;
import java.util.Enumeration;
import javax.ejb.CreateException;
import javax.ejb.EJBMetaData;
import javax.ejb.FinderException;
import javax.ejb.Handle;
import javax.ejb.HomeHandle;
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

public class _EJSRemoteCMPAsmformHome_Tie extends org.omg.CORBA_2_3.portable.ObjectImpl implements Tie {
    
    private EJSRemoteCMPAsmformHome target = null;
    private ORB orb = null;
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.iltds.beans.AsmformHome:0000000000000000", 
        "RMI:javax.ejb.EJBHome:0000000000000000", 
        "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
        "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000", 
        "RMI:javax.ejb.EJBObject:0000000000000000"
    };
    
    public void setTarget(Remote target) {
        this.target = (EJSRemoteCMPAsmformHome) target;
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
            switch (method.length()) {
                case 15: 
                    if (method.equals("_get_homeHandle")) {
                        return _get_homeHandle(in, reply);
                    } else if (method.equals("findByASMFormID")) {
                        return findByASMFormID(in, reply);
                    }
                case 16: 
                    if (method.equals("_get_EJBMetaData")) {
                        return _get_EJBMetaData(in, reply);
                    } else if (method.equals("findByPrimaryKey")) {
                        return findByPrimaryKey(in, reply);
                    }
                case 20: 
                    if (method.equals("findAsmformByConsent")) {
                        return findAsmformByConsent(in, reply);
                    }
                case 24: 
                    if (method.equals("remove__java_lang_Object")) {
                        return remove__java_lang_Object(in, reply);
                    } else if (method.equals("remove__javax_ejb_Handle")) {
                        return remove__javax_ejb_Handle(in, reply);
                    }
                case 63: 
                    if (method.equals("create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_Consent")) {
                        return create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_Consent(in, reply);
                    }
                case 66: 
                    if (method.equals("create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey")) {
                        return create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey(in, reply);
                    }
                case 86: 
                    if (method.equals("create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey__CORBA_WStringValue")) {
                        return create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey__CORBA_WStringValue(in, reply);
                    }
                case 110: 
                    if (method.equals("create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey__com_ardais_bigr_iltds_beans_ArdaisstaffKey")) {
                        return create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey__com_ardais_bigr_iltds_beans_ArdaisstaffKey(in, reply);
                    }
                case 130: 
                    if (method.equals("create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey__com_ardais_bigr_iltds_beans_ArdaisstaffKey__CORBA_WStringValue")) {
                        return create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey__com_ardais_bigr_iltds_beans_ArdaisstaffKey__CORBA_WStringValue(in, reply);
                    }
            }
            throw new BAD_OPERATION();
        } catch (SystemException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new UnknownException(ex);
        }
    }
    
    private OutputStream remove__javax_ejb_Handle(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Handle arg0 = (Handle) in.read_abstract_interface(Handle.class);
        try {
            target.remove(arg0);
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
    
    private OutputStream remove__java_lang_Object(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Object arg0 = Util.readAny(in);
        try {
            target.remove(arg0);
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
    
    private OutputStream _get_EJBMetaData(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        EJBMetaData result = target.getEJBMetaData();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,EJBMetaData.class);
        return out;
    }
    
    private OutputStream _get_homeHandle(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        HomeHandle result = target.getHomeHandle();
        OutputStream out = reply.createReply();
        Util.writeAbstractObject(out,result);
        return out;
    }
    
    private OutputStream create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        ConsentKey arg1 = (ConsentKey) in.read_value(ConsentKey.class);
        Asmform result;
        try {
            result = target.create(arg0, arg1);
        } catch (CreateException ex) {
            String id = "IDL:javax/ejb/CreateEx:1.0";
            org.omg.CORBA_2_3.portable.OutputStream out = 
                (org.omg.CORBA_2_3.portable.OutputStream) reply.createExceptionReply();
            out.write_string(id);
            out.write_value(ex,CreateException.class);
            return out;
        }
        OutputStream out = reply.createReply();
        Util.writeRemoteObject(out,result);
        return out;
    }
    
    private OutputStream create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        ConsentKey arg1 = (ConsentKey) in.read_value(ConsentKey.class);
        String arg2 = (String) in.read_value(String.class);
        Asmform result;
        try {
            result = target.create(arg0, arg1, arg2);
        } catch (CreateException ex) {
            String id = "IDL:javax/ejb/CreateEx:1.0";
            org.omg.CORBA_2_3.portable.OutputStream out = 
                (org.omg.CORBA_2_3.portable.OutputStream) reply.createExceptionReply();
            out.write_string(id);
            out.write_value(ex,CreateException.class);
            return out;
        }
        OutputStream out = reply.createReply();
        Util.writeRemoteObject(out,result);
        return out;
    }
    
    private OutputStream create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey__com_ardais_bigr_iltds_beans_ArdaisstaffKey(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        ConsentKey arg1 = (ConsentKey) in.read_value(ConsentKey.class);
        ArdaisstaffKey arg2 = (ArdaisstaffKey) in.read_value(ArdaisstaffKey.class);
        Asmform result;
        try {
            result = target.create(arg0, arg1, arg2);
        } catch (CreateException ex) {
            String id = "IDL:javax/ejb/CreateEx:1.0";
            org.omg.CORBA_2_3.portable.OutputStream out = 
                (org.omg.CORBA_2_3.portable.OutputStream) reply.createExceptionReply();
            out.write_string(id);
            out.write_value(ex,CreateException.class);
            return out;
        }
        OutputStream out = reply.createReply();
        Util.writeRemoteObject(out,result);
        return out;
    }
    
    private OutputStream create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey__com_ardais_bigr_iltds_beans_ArdaisstaffKey__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        ConsentKey arg1 = (ConsentKey) in.read_value(ConsentKey.class);
        ArdaisstaffKey arg2 = (ArdaisstaffKey) in.read_value(ArdaisstaffKey.class);
        String arg3 = (String) in.read_value(String.class);
        Asmform result;
        try {
            result = target.create(arg0, arg1, arg2, arg3);
        } catch (CreateException ex) {
            String id = "IDL:javax/ejb/CreateEx:1.0";
            org.omg.CORBA_2_3.portable.OutputStream out = 
                (org.omg.CORBA_2_3.portable.OutputStream) reply.createExceptionReply();
            out.write_string(id);
            out.write_value(ex,CreateException.class);
            return out;
        }
        OutputStream out = reply.createReply();
        Util.writeRemoteObject(out,result);
        return out;
    }
    
    private OutputStream findAsmformByConsent(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        ConsentKey arg0 = (ConsentKey) in.read_value(ConsentKey.class);
        Enumeration result;
        try {
            result = target.findAsmformByConsent(arg0);
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
    
    private OutputStream findByASMFormID(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        Enumeration result;
        try {
            result = target.findByASMFormID(arg0);
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
    
    private OutputStream findByPrimaryKey(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        AsmformKey arg0 = (AsmformKey) in.read_value(AsmformKey.class);
        Asmform result;
        try {
            result = target.findByPrimaryKey(arg0);
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
    
    private OutputStream create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_Consent(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        Consent arg1 = (Consent) in.read_Object(Consent.class);
        Asmform result;
        try {
            result = target.create(arg0, arg1);
        } catch (CreateException ex) {
            String id = "IDL:javax/ejb/CreateEx:1.0";
            org.omg.CORBA_2_3.portable.OutputStream out = 
                (org.omg.CORBA_2_3.portable.OutputStream) reply.createExceptionReply();
            out.write_string(id);
            out.write_value(ex,CreateException.class);
            return out;
        }
        OutputStream out = reply.createReply();
        Util.writeRemoteObject(out,result);
        return out;
    }
}
