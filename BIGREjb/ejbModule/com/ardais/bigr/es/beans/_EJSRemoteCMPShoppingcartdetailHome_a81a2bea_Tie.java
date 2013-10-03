// Tie class generated by rmic, do not edit.
// Contents subject to change without notice.

package com.ardais.bigr.es.beans;

import java.io.Serializable;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.math.BigDecimal;
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

public class _EJSRemoteCMPShoppingcartdetailHome_a81a2bea_Tie extends org.omg.CORBA_2_3.portable.ObjectImpl implements Tie {
    
    private EJSRemoteCMPShoppingcartdetailHome_a81a2bea target = null;
    private ORB orb = null;
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.es.beans.ShoppingcartdetailHome:0000000000000000", 
        "RMI:javax.ejb.EJBHome:0000000000000000", 
        "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
        "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000", 
        "RMI:javax.ejb.EJBObject:0000000000000000"
    };
    
    public void setTarget(Remote target) {
        this.target = (EJSRemoteCMPShoppingcartdetailHome_a81a2bea) target;
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
            switch (method.charAt(12)) {
                case 65: 
                    if (method.equals("create__CORBA_WStringValue__CORBA_WStringValue__java_math_BigDecimal__CORBA_WStringValue__CORBA_WStringValue__java_math_BigDecimal")) {
                        return create__CORBA_WStringValue__CORBA_WStringValue__java_math_BigDecimal__CORBA_WStringValue__CORBA_WStringValue__java_math_BigDecimal(in, reply);
                    }
                case 68: 
                    if (method.equals("_get_EJBMetaData")) {
                        return _get_EJBMetaData(in, reply);
                    }
                case 95: 
                    if (method.equals("remove__java_lang_Object")) {
                        return remove__java_lang_Object(in, reply);
                    } else if (method.equals("create__java_math_BigDecimal__com_ardais_bigr_es_beans_Shoppingcart")) {
                        return create__java_math_BigDecimal__com_ardais_bigr_es_beans_Shoppingcart(in, reply);
                    }
                case 99: 
                    if (method.equals("findByUserAccountOrderByLineNumber")) {
                        return findByUserAccountOrderByLineNumber(in, reply);
                    } else if (method.equals("findShoppingcartdetailByShoppingcart")) {
                        return findShoppingcartdetailByShoppingcart(in, reply);
                    }
                case 100: 
                    if (method.equals("_get_homeHandle")) {
                        return _get_homeHandle(in, reply);
                    }
                case 101: 
                    if (method.equals("findByBarcodeUserAccount")) {
                        return findByBarcodeUserAccount(in, reply);
                    }
                case 120: 
                    if (method.equals("remove__javax_ejb_Handle")) {
                        return remove__javax_ejb_Handle(in, reply);
                    }
                case 121: 
                    if (method.equals("findByPrimaryKey")) {
                        return findByPrimaryKey(in, reply);
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
    
    private OutputStream create__java_math_BigDecimal__com_ardais_bigr_es_beans_Shoppingcart(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        BigDecimal arg0 = (BigDecimal) in.read_value(BigDecimal.class);
        Shoppingcart arg1 = (Shoppingcart) in.read_Object(Shoppingcart.class);
        Shoppingcartdetail result;
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
    
    private OutputStream create__CORBA_WStringValue__CORBA_WStringValue__java_math_BigDecimal__CORBA_WStringValue__CORBA_WStringValue__java_math_BigDecimal(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        String arg1 = (String) in.read_value(String.class);
        BigDecimal arg2 = (BigDecimal) in.read_value(BigDecimal.class);
        String arg3 = (String) in.read_value(String.class);
        String arg4 = (String) in.read_value(String.class);
        BigDecimal arg5 = (BigDecimal) in.read_value(BigDecimal.class);
        Shoppingcartdetail result;
        try {
            result = target.create(arg0, arg1, arg2, arg3, arg4, arg5);
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
    
    private OutputStream findByPrimaryKey(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        ShoppingcartdetailKey arg0 = (ShoppingcartdetailKey) in.read_value(ShoppingcartdetailKey.class);
        Shoppingcartdetail result;
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
    
    private OutputStream findByBarcodeUserAccount(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        String arg1 = (String) in.read_value(String.class);
        String arg2 = (String) in.read_value(String.class);
        Enumeration result;
        try {
            result = target.findByBarcodeUserAccount(arg0, arg1, arg2);
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
    
    private OutputStream findByUserAccountOrderByLineNumber(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        String arg1 = (String) in.read_value(String.class);
        Enumeration result;
        try {
            result = target.findByUserAccountOrderByLineNumber(arg0, arg1);
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
    
    private OutputStream findShoppingcartdetailByShoppingcart(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        ShoppingcartKey arg0 = (ShoppingcartKey) in.read_value(ShoppingcartKey.class);
        Enumeration result;
        try {
            result = target.findShoppingcartdetailByShoppingcart(arg0);
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
}
