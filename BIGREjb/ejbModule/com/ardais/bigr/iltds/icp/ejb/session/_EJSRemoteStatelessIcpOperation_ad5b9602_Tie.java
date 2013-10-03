// Tie class generated by rmic, do not edit.
// Contents subject to change without notice.

package com.ardais.bigr.iltds.icp.ejb.session;

import com.ardais.bigr.iltds.databeans.AsmData;
import com.ardais.bigr.iltds.databeans.CaseData;
import com.ardais.bigr.iltds.databeans.LogicalRepositoryExtendedData;
import com.ardais.bigr.iltds.databeans.SampleData;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.security.SecurityInfo;
import java.io.Serializable;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.rmi.Remote;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.RemoveException;
import javax.ejb.SessionContext;
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

public class _EJSRemoteStatelessIcpOperation_ad5b9602_Tie extends org.omg.CORBA_2_3.portable.ObjectImpl implements Tie {
    
    private EJSRemoteStatelessIcpOperation_ad5b9602 target = null;
    private ORB orb = null;
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.iltds.icp.ejb.session.IcpOperation:0000000000000000", 
        "RMI:javax.ejb.EJBObject:0000000000000000", 
        "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
        "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000"
    };
    
    public void setTarget(Remote target) {
        this.target = (EJSRemoteStatelessIcpOperation_ad5b9602) target;
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
            switch (method.charAt(5)) {
                case 69: 
                    if (method.equals("_get_EJBHome")) {
                        return _get_EJBHome(in, reply);
                    }
                case 101: 
                    if (method.equals("remove")) {
                        return remove(in, reply);
                    }
                case 103: 
                    if (method.equals("getLogicalRepositoryData")) {
                        return getLogicalRepositoryData(in, reply);
                    }
                case 104: 
                    if (method.equals("_get_handle")) {
                        return _get_handle(in, reply);
                    }
                case 109: 
                    if (method.equals("getAsmData")) {
                        return getAsmData(in, reply);
                    } else if (method.equals("getSampleData")) {
                        return getSampleData(in, reply);
                    }
                case 110: 
                    if (method.equals("isIdentical")) {
                        return isIdentical(in, reply);
                    }
                case 112: 
                    if (method.equals("_get_primaryKey")) {
                        return _get_primaryKey(in, reply);
                    }
                case 115: 
                    if (method.equals("getCaseData")) {
                        return getCaseData(in, reply);
                    } else if (method.equals("_get_sessionContext")) {
                        return _get_sessionContext(in, reply);
                    }
                case 120: 
                    if (method.equals("getBoxData")) {
                        return getBoxData(in, reply);
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
    
    private OutputStream getAsmData(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        AsmData arg0 = (AsmData) in.read_value(AsmData.class);
        SecurityInfo arg1 = (SecurityInfo) in.read_value(SecurityInfo.class);
        boolean arg2 = in.read_boolean();
        boolean arg3 = in.read_boolean();
        AsmData result = target.getAsmData(arg0, arg1, arg2, arg3);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,AsmData.class);
        return out;
    }
    
    private OutputStream getBoxData(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        BoxDto arg0 = (BoxDto) in.read_value(BoxDto.class);
        SecurityInfo arg1 = (SecurityInfo) in.read_value(SecurityInfo.class);
        boolean arg2 = in.read_boolean();
        BoxDto result = target.getBoxData(arg0, arg1, arg2);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,BoxDto.class);
        return out;
    }
    
    private OutputStream getCaseData(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        CaseData arg0 = (CaseData) in.read_value(CaseData.class);
        SecurityInfo arg1 = (SecurityInfo) in.read_value(SecurityInfo.class);
        boolean arg2 = in.read_boolean();
        CaseData result = target.getCaseData(arg0, arg1, arg2);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,CaseData.class);
        return out;
    }
    
    private OutputStream getSampleData(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        SampleData arg0 = (SampleData) in.read_value(SampleData.class);
        SecurityInfo arg1 = (SecurityInfo) in.read_value(SecurityInfo.class);
        boolean arg2 = in.read_boolean();
        boolean arg3 = in.read_boolean();
        SampleData result = target.getSampleData(arg0, arg1, arg2, arg3);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,SampleData.class);
        return out;
    }
    
    private OutputStream _get_sessionContext(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        SessionContext result = target.getSessionContext();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,SessionContext.class);
        return out;
    }
    
    private OutputStream getLogicalRepositoryData(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        SecurityInfo arg1 = (SecurityInfo) in.read_value(SecurityInfo.class);
        LogicalRepositoryExtendedData result = target.getLogicalRepositoryData(arg0, arg1);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,LogicalRepositoryExtendedData.class);
        return out;
    }
}