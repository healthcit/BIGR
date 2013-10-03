// Tie class generated by rmic, do not edit.
// Contents subject to change without notice.

package com.ardais.bigr.pdc.oce;

import com.ardais.bigr.pdc.javabeans.OceData;
import java.io.Serializable;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.rmi.Remote;
import java.util.Map;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
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

public class _EJSRemoteStatelessOce_1c4c8256_Tie extends org.omg.CORBA_2_3.portable.ObjectImpl implements Tie {
    
    private EJSRemoteStatelessOce_1c4c8256 target = null;
    private ORB orb = null;
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.pdc.oce.Oce:0000000000000000", 
        "RMI:javax.ejb.EJBObject:0000000000000000", 
        "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
        "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000"
    };
    
    public void setTarget(Remote target) {
        this.target = (EJSRemoteStatelessOce_1c4c8256) target;
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
                case 6: 
                    if (method.equals("remove")) {
                        return remove(in, reply);
                    }
                case 9: 
                    if (method.equals("updateOce")) {
                        return updateOce(in, reply);
                    } else if (method.equals("createOce")) {
                        return createOce(in, reply);
                    }
                case 10: 
                    if (method.equals("getOceData")) {
                        return getOceData(in, reply);
                    }
                case 11: 
                    if (method.equals("_get_handle")) {
                        return _get_handle(in, reply);
                    } else if (method.equals("isIdentical")) {
                        return isIdentical(in, reply);
                    }
                case 12: 
                    if (method.equals("updateStatus")) {
                        return updateStatus(in, reply);
                    } else if (method.equals("_get_EJBHome")) {
                        return _get_EJBHome(in, reply);
                    }
                case 15: 
                    if (method.equals("_get_primaryKey")) {
                        return _get_primaryKey(in, reply);
                    }
                case 16: 
                    if (method.equals("retrieveCaseInfo")) {
                        return retrieveCaseInfo(in, reply);
                    }
                case 21: 
                    if (method.equals("_get_tableColumnNames")) {
                        return _get_tableColumnNames(in, reply);
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
    
    private OutputStream getOceData(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        OceData arg0 = (OceData) in.read_value(OceData.class);
        OceData result = target.getOceData(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,OceData.class);
        return out;
    }
    
    private OutputStream _get_tableColumnNames(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Map result = target.getTableColumnNames();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,Map.class);
        return out;
    }
    
    private OutputStream updateOce(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        OceData arg0 = (OceData) in.read_value(OceData.class);
        OceData result = target.updateOce(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,OceData.class);
        return out;
    }
    
    private OutputStream createOce(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        String arg1 = (String) in.read_value(String.class);
        String arg2 = (String) in.read_value(String.class);
        String arg3 = (String) in.read_value(String.class);
        String arg4 = (String) in.read_value(String.class);
        String arg5 = (String) in.read_value(String.class);
        String arg6 = (String) in.read_value(String.class);
        target.createOce(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream updateStatus(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        String arg1 = (String) in.read_value(String.class);
        String arg2 = (String) in.read_value(String.class);
        String arg3 = (String) in.read_value(String.class);
        String arg4 = (String) in.read_value(String.class);
        String arg5 = (String) in.read_value(String.class);
        target.updateStatus(arg0, arg1, arg2, arg3, arg4, arg5);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream retrieveCaseInfo(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        Map result = target.retrieveCaseInfo(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,Map.class);
        return out;
    }
}