// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

package com.ardais.bigr.es.beans;

import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.sql.Timestamp;
import java.util.Hashtable;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.FinderException;
import javax.ejb.Handle;
import javax.ejb.RemoveException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ServantObject;

public class _Shoppingcartdetail_Stub extends Stub implements Shoppingcartdetail {
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.es.beans.Shoppingcartdetail:0000000000000000", 
        "RMI:javax.ejb.EJBObject:0000000000000000", 
        "RMI:com.ibm.ivj.ejb.runtime.CopyHelper:0000000000000000"
    };
    
    public String[] _ids() { 
        return _type_ids;
    }
    
    public EJBHome getEJBHome() throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("_get_EJBHome", true);
                        in = _invoke(out);
                        return (EJBHome) in.read_Object(EJBHome.class);
                    } catch (ApplicationException ex) {
                        in = ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_get_EJBHome",javax.ejb.EJBObject.class);
                if (so == null) {
                    continue;
                }
                try {
                    EJBHome result = ((javax.ejb.EJBObject)so.servant).getEJBHome();
                    return (EJBHome)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public Object getPrimaryKey() throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("_get_primaryKey", true);
                        in = _invoke(out);
                        return Util.readAny(in);
                    } catch (ApplicationException ex) {
                        in = ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_get_primaryKey",javax.ejb.EJBObject.class);
                if (so == null) {
                    continue;
                }
                try {
                    Object result = ((javax.ejb.EJBObject)so.servant).getPrimaryKey();
                    return (Object)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public void remove() throws RemoteException, RemoveException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("remove", true);
                        _invoke(out);
                        return;
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        if (id.equals("IDL:javax/ejb/RemoveEx:1.0")) {
                            throw (RemoveException) in.read_value(RemoveException.class);
                        }
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("remove",javax.ejb.EJBObject.class);
                if (so == null) {
                    continue;
                }
                try {
                    ((javax.ejb.EJBObject)so.servant).remove();
                    return;
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    if (exCopy instanceof RemoveException) {
                        throw (RemoveException)exCopy;
                    }
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public Handle getHandle() throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("_get_handle", true);
                        in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                        return (Handle) in.read_abstract_interface(Handle.class);
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_get_handle",javax.ejb.EJBObject.class);
                if (so == null) {
                    continue;
                }
                try {
                    Handle result = ((javax.ejb.EJBObject)so.servant).getHandle();
                    return (Handle)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public boolean isIdentical(EJBObject arg0) throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("isIdentical", true);
                        Util.writeRemoteObject(out,arg0);
                        in = _invoke(out);
                        return in.read_boolean();
                    } catch (ApplicationException ex) {
                        in = ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("isIdentical",javax.ejb.EJBObject.class);
                if (so == null) {
                    continue;
                }
                try {
                    EJBObject arg0Copy = (EJBObject) Util.copyObject(arg0,_orb());
                    return ((javax.ejb.EJBObject)so.servant).isIdentical(arg0Copy);
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public Hashtable _copyFromEJB() throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("J_copyFromEJB", true);
                        in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                        return (Hashtable) in.read_value(Hashtable.class);
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("J_copyFromEJB",com.ibm.ivj.ejb.runtime.CopyHelper.class);
                if (so == null) {
                    continue;
                }
                try {
                    Hashtable result = ((com.ibm.ivj.ejb.runtime.CopyHelper)so.servant)._copyFromEJB();
                    return (Hashtable)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public void _copyToEJB(Hashtable arg0) throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("J_copyToEJB", true);
                        out.write_value(arg0,Hashtable.class);
                        _invoke(out);
                        return;
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("J_copyToEJB",com.ibm.ivj.ejb.runtime.CopyHelper.class);
                if (so == null) {
                    continue;
                }
                try {
                    Hashtable arg0Copy = (Hashtable) Util.copyObject(arg0,_orb());
                    ((com.ibm.ivj.ejb.runtime.CopyHelper)so.servant)._copyToEJB(arg0Copy);
                    return;
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public BigDecimal getShopping_cart_line_amount() throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("_get_shopping_cart_line_amount", true);
                        in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                        return (BigDecimal) in.read_value(BigDecimal.class);
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_get_shopping_cart_line_amount",com.ardais.bigr.es.beans.Shoppingcartdetail.class);
                if (so == null) {
                    continue;
                }
                try {
                    BigDecimal result = ((com.ardais.bigr.es.beans.Shoppingcartdetail)so.servant).getShopping_cart_line_amount();
                    return (BigDecimal)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public void setShopping_cart_line_amount(BigDecimal arg0) throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("_set_shopping_cart_line_amount", true);
                        out.write_value(arg0,BigDecimal.class);
                        _invoke(out);
                        return;
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_set_shopping_cart_line_amount",com.ardais.bigr.es.beans.Shoppingcartdetail.class);
                if (so == null) {
                    continue;
                }
                try {
                    BigDecimal arg0Copy = (BigDecimal) Util.copyObject(arg0,_orb());
                    ((com.ardais.bigr.es.beans.Shoppingcartdetail)so.servant).setShopping_cart_line_amount(arg0Copy);
                    return;
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public String getBarcode_id() throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("_get_barcode_id", true);
                        in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                        return (String) in.read_value(String.class);
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_get_barcode_id",com.ardais.bigr.es.beans.Shoppingcartdetail.class);
                if (so == null) {
                    continue;
                }
                try {
                    return ((com.ardais.bigr.es.beans.Shoppingcartdetail)so.servant).getBarcode_id();
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public void setBarcode_id(String arg0) throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("_set_barcode_id", true);
                        out.write_value(arg0,String.class);
                        _invoke(out);
                        return;
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_set_barcode_id",com.ardais.bigr.es.beans.Shoppingcartdetail.class);
                if (so == null) {
                    continue;
                }
                try {
                    ((com.ardais.bigr.es.beans.Shoppingcartdetail)so.servant).setBarcode_id(arg0);
                    return;
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public String getSearch_desc() throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("_get_search_desc", true);
                        in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                        return (String) in.read_value(String.class);
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_get_search_desc",com.ardais.bigr.es.beans.Shoppingcartdetail.class);
                if (so == null) {
                    continue;
                }
                try {
                    return ((com.ardais.bigr.es.beans.Shoppingcartdetail)so.servant).getSearch_desc();
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public void setSearch_desc(String arg0) throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("_set_search_desc", true);
                        out.write_value(arg0,String.class);
                        _invoke(out);
                        return;
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_set_search_desc",com.ardais.bigr.es.beans.Shoppingcartdetail.class);
                if (so == null) {
                    continue;
                }
                try {
                    ((com.ardais.bigr.es.beans.Shoppingcartdetail)so.servant).setSearch_desc(arg0);
                    return;
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public Timestamp getCreation_dt() throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("_get_creation_dt", true);
                        in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                        return (Timestamp) in.read_value(Timestamp.class);
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_get_creation_dt",com.ardais.bigr.es.beans.Shoppingcartdetail.class);
                if (so == null) {
                    continue;
                }
                try {
                    Timestamp result = ((com.ardais.bigr.es.beans.Shoppingcartdetail)so.servant).getCreation_dt();
                    return (Timestamp)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public void setCreation_dt(Timestamp arg0) throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("_set_creation_dt", true);
                        out.write_value(arg0,Timestamp.class);
                        _invoke(out);
                        return;
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_set_creation_dt",com.ardais.bigr.es.beans.Shoppingcartdetail.class);
                if (so == null) {
                    continue;
                }
                try {
                    Timestamp arg0Copy = (Timestamp) Util.copyObject(arg0,_orb());
                    ((com.ardais.bigr.es.beans.Shoppingcartdetail)so.servant).setCreation_dt(arg0Copy);
                    return;
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public ShoppingcartKey getShoppingcartKey() throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("_get_shoppingcartKey", true);
                        in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                        return (ShoppingcartKey) in.read_value(ShoppingcartKey.class);
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_get_shoppingcartKey",com.ardais.bigr.es.beans.Shoppingcartdetail.class);
                if (so == null) {
                    continue;
                }
                try {
                    ShoppingcartKey result = ((com.ardais.bigr.es.beans.Shoppingcartdetail)so.servant).getShoppingcartKey();
                    return (ShoppingcartKey)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public Shoppingcart getShoppingcart() throws RemoteException, FinderException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("getShoppingcart", true);
                        in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                        return (Shoppingcart) in.read_Object(Shoppingcart.class);
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        if (id.equals("IDL:javax/ejb/FinderEx:1.0")) {
                            throw (FinderException) in.read_value(FinderException.class);
                        }
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("getShoppingcart",com.ardais.bigr.es.beans.Shoppingcartdetail.class);
                if (so == null) {
                    continue;
                }
                try {
                    Shoppingcart result = ((com.ardais.bigr.es.beans.Shoppingcartdetail)so.servant).getShoppingcart();
                    return (Shoppingcart)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    if (exCopy instanceof FinderException) {
                        throw (FinderException)exCopy;
                    }
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public Integer getQuantity() throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("_get_quantity", true);
                        in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                        return (Integer) in.read_value(Integer.class);
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_get_quantity",com.ardais.bigr.es.beans.Shoppingcartdetail.class);
                if (so == null) {
                    continue;
                }
                try {
                    Integer result = ((com.ardais.bigr.es.beans.Shoppingcartdetail)so.servant).getQuantity();
                    return (Integer)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public void setQuantity(Integer arg0) throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("_set_quantity", true);
                        out.write_value(arg0,Integer.class);
                        _invoke(out);
                        return;
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_set_quantity",com.ardais.bigr.es.beans.Shoppingcartdetail.class);
                if (so == null) {
                    continue;
                }
                try {
                    Integer arg0Copy = (Integer) Util.copyObject(arg0,_orb());
                    ((com.ardais.bigr.es.beans.Shoppingcartdetail)so.servant).setQuantity(arg0Copy);
                    return;
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public String getProductType() throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("_get_productType", true);
                        in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                        return (String) in.read_value(String.class);
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_get_productType",com.ardais.bigr.es.beans.Shoppingcartdetail.class);
                if (so == null) {
                    continue;
                }
                try {
                    return ((com.ardais.bigr.es.beans.Shoppingcartdetail)so.servant).getProductType();
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public void setProductType(String arg0) throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("_set_productType", true);
                        out.write_value(arg0,String.class);
                        _invoke(out);
                        return;
                    } catch (ApplicationException ex) {
                        in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("_set_productType",com.ardais.bigr.es.beans.Shoppingcartdetail.class);
                if (so == null) {
                    continue;
                }
                try {
                    ((com.ardais.bigr.es.beans.Shoppingcartdetail)so.servant).setProductType(arg0);
                    return;
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
}