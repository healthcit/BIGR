// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

package com.ardais.bigr.iltds.beans;

import com.ardais.bigr.pdc.javabeans.AttachmentData;
import com.ardais.bigr.security.SecurityInfo;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
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

public class _ConsentOperation_Stub extends Stub implements ConsentOperation {
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.iltds.beans.ConsentOperation:0000000000000000", 
        "RMI:javax.ejb.EJBObject:0000000000000000"
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
    
    public void pullFromESHold(String arg0) throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("pullFromESHold", true);
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
                ServantObject so = _servant_preinvoke("pullFromESHold",com.ardais.bigr.iltds.beans.ConsentOperation.class);
                if (so == null) {
                    continue;
                }
                try {
                    ((com.ardais.bigr.iltds.beans.ConsentOperation)so.servant).pullFromESHold(arg0);
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
    
    public void revokeConsent(String arg0, String arg1, String arg2, String arg3) throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("revokeConsent", true);
                        out.write_value(arg0,String.class);
                        out.write_value(arg1,String.class);
                        out.write_value(arg2,String.class);
                        out.write_value(arg3,String.class);
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
                ServantObject so = _servant_preinvoke("revokeConsent",com.ardais.bigr.iltds.beans.ConsentOperation.class);
                if (so == null) {
                    continue;
                }
                try {
                    ((com.ardais.bigr.iltds.beans.ConsentOperation)so.servant).revokeConsent(arg0, arg1, arg2, arg3);
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
    
    public String getCaseOrigin(String arg0) throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("getCaseOrigin", true);
                        out.write_value(arg0,String.class);
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
                ServantObject so = _servant_preinvoke("getCaseOrigin",com.ardais.bigr.iltds.beans.ConsentOperation.class);
                if (so == null) {
                    continue;
                }
                try {
                    return ((com.ardais.bigr.iltds.beans.ConsentOperation)so.servant).getCaseOrigin(arg0);
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public AttachmentData insertCNTAttachment(AttachmentData arg0, SecurityInfo arg1) throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("insertCNTAttachment", true);
                        out.write_value(arg0,AttachmentData.class);
                        out.write_value(arg1,SecurityInfo.class);
                        in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                        return (AttachmentData) in.read_value(AttachmentData.class);
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
                ServantObject so = _servant_preinvoke("insertCNTAttachment",com.ardais.bigr.iltds.beans.ConsentOperation.class);
                if (so == null) {
                    continue;
                }
                try {
                    Object[] copies = Util.copyObjects(new Object[]{arg0,arg1},_orb());
                    AttachmentData arg0Copy = (AttachmentData) copies[0];
                    SecurityInfo arg1Copy = (SecurityInfo) copies[1];
                    AttachmentData result = ((com.ardais.bigr.iltds.beans.ConsentOperation)so.servant).insertCNTAttachment(arg0Copy, arg1Copy);
                    return (AttachmentData)Util.copyObject(result,_orb());
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
