// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

package com.ardais.bigr.iltds.beans;

import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.util.Enumeration;
import javax.ejb.CreateException;
import javax.ejb.EJBMetaData;
import javax.ejb.FinderException;
import javax.ejb.Handle;
import javax.ejb.HomeHandle;
import javax.ejb.RemoveException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ServantObject;
import org.omg.CORBA_2_3.portable.InputStream;

public class _AsmformHome_Stub extends Stub implements AsmformHome {
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.iltds.beans.AsmformHome:0000000000000000", 
        "RMI:javax.ejb.EJBHome:0000000000000000"
    };
    
    public String[] _ids() { 
        return _type_ids;
    }
    
    public void remove(Handle arg0) throws RemoteException, RemoveException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("remove__javax_ejb_Handle", true);
                        Util.writeAbstractObject(out,arg0);
                        _invoke(out);
                        return;
                    } catch (ApplicationException ex) {
                        in = (InputStream) ex.getInputStream();
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
                ServantObject so = _servant_preinvoke("remove__javax_ejb_Handle",javax.ejb.EJBHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    Handle arg0Copy = (Handle) Util.copyObject(arg0,_orb());
                    ((javax.ejb.EJBHome)so.servant).remove(arg0Copy);
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
    
    public void remove(Object arg0) throws RemoteException, RemoveException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("remove__java_lang_Object", true);
                        Util.writeAny(out,arg0);
                        _invoke(out);
                        return;
                    } catch (ApplicationException ex) {
                        in = (InputStream) ex.getInputStream();
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
                ServantObject so = _servant_preinvoke("remove__java_lang_Object",javax.ejb.EJBHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    Object arg0Copy = (Object) Util.copyObject(arg0,_orb());
                    ((javax.ejb.EJBHome)so.servant).remove(arg0Copy);
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
    
    public EJBMetaData getEJBMetaData() throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("_get_EJBMetaData", true);
                        in = (InputStream)_invoke(out);
                        return (EJBMetaData) in.read_value(EJBMetaData.class);
                    } catch (ApplicationException ex) {
                        in = (InputStream) ex.getInputStream();
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
                ServantObject so = _servant_preinvoke("_get_EJBMetaData",javax.ejb.EJBHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    EJBMetaData result = ((javax.ejb.EJBHome)so.servant).getEJBMetaData();
                    return (EJBMetaData)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public HomeHandle getHomeHandle() throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("_get_homeHandle", true);
                        in = (InputStream)_invoke(out);
                        return (HomeHandle) in.read_abstract_interface(HomeHandle.class);
                    } catch (ApplicationException ex) {
                        in = (InputStream) ex.getInputStream();
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
                ServantObject so = _servant_preinvoke("_get_homeHandle",javax.ejb.EJBHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    HomeHandle result = ((javax.ejb.EJBHome)so.servant).getHomeHandle();
                    return (HomeHandle)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public Asmform create(String arg0, ConsentKey arg1) throws CreateException, RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey", true);
                        out.write_value(arg0,String.class);
                        out.write_value(arg1,ConsentKey.class);
                        in = (InputStream)_invoke(out);
                        return (Asmform) in.read_Object(Asmform.class);
                    } catch (ApplicationException ex) {
                        in = (InputStream) ex.getInputStream();
                        String id = in.read_string();
                        if (id.equals("IDL:javax/ejb/CreateEx:1.0")) {
                            throw (CreateException) in.read_value(CreateException.class);
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
                ServantObject so = _servant_preinvoke("create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey",com.ardais.bigr.iltds.beans.AsmformHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    Object[] copies = Util.copyObjects(new Object[]{arg0,arg1},_orb());
                    String arg0Copy = (String) copies[0];
                    ConsentKey arg1Copy = (ConsentKey) copies[1];
                    Asmform result = ((com.ardais.bigr.iltds.beans.AsmformHome)so.servant).create(arg0Copy, arg1Copy);
                    return (Asmform)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    if (exCopy instanceof CreateException) {
                        throw (CreateException)exCopy;
                    }
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public Asmform create(String arg0, ConsentKey arg1, String arg2) throws CreateException, RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey__CORBA_WStringValue", true);
                        out.write_value(arg0,String.class);
                        out.write_value(arg1,ConsentKey.class);
                        out.write_value(arg2,String.class);
                        in = (InputStream)_invoke(out);
                        return (Asmform) in.read_Object(Asmform.class);
                    } catch (ApplicationException ex) {
                        in = (InputStream) ex.getInputStream();
                        String id = in.read_string();
                        if (id.equals("IDL:javax/ejb/CreateEx:1.0")) {
                            throw (CreateException) in.read_value(CreateException.class);
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
                ServantObject so = _servant_preinvoke("create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey__CORBA_WStringValue",com.ardais.bigr.iltds.beans.AsmformHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    Object[] copies = Util.copyObjects(new Object[]{arg0,arg1,arg2},_orb());
                    String arg0Copy = (String) copies[0];
                    ConsentKey arg1Copy = (ConsentKey) copies[1];
                    String arg2Copy = (String) copies[2];
                    Asmform result = ((com.ardais.bigr.iltds.beans.AsmformHome)so.servant).create(arg0Copy, arg1Copy, arg2Copy);
                    return (Asmform)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    if (exCopy instanceof CreateException) {
                        throw (CreateException)exCopy;
                    }
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public Asmform create(String arg0, ConsentKey arg1, ArdaisstaffKey arg2) throws CreateException, RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey__com_ardais_bigr_iltds_beans_ArdaisstaffKey", true);
                        out.write_value(arg0,String.class);
                        out.write_value(arg1,ConsentKey.class);
                        out.write_value(arg2,ArdaisstaffKey.class);
                        in = (InputStream)_invoke(out);
                        return (Asmform) in.read_Object(Asmform.class);
                    } catch (ApplicationException ex) {
                        in = (InputStream) ex.getInputStream();
                        String id = in.read_string();
                        if (id.equals("IDL:javax/ejb/CreateEx:1.0")) {
                            throw (CreateException) in.read_value(CreateException.class);
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
                ServantObject so = _servant_preinvoke("create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey__com_ardais_bigr_iltds_beans_ArdaisstaffKey",com.ardais.bigr.iltds.beans.AsmformHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    Object[] copies = Util.copyObjects(new Object[]{arg0,arg1,arg2},_orb());
                    String arg0Copy = (String) copies[0];
                    ConsentKey arg1Copy = (ConsentKey) copies[1];
                    ArdaisstaffKey arg2Copy = (ArdaisstaffKey) copies[2];
                    Asmform result = ((com.ardais.bigr.iltds.beans.AsmformHome)so.servant).create(arg0Copy, arg1Copy, arg2Copy);
                    return (Asmform)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    if (exCopy instanceof CreateException) {
                        throw (CreateException)exCopy;
                    }
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public Asmform create(String arg0, ConsentKey arg1, ArdaisstaffKey arg2, String arg3) throws CreateException, RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey__com_ardais_bigr_iltds_beans_ArdaisstaffKey__CORBA_WStringValue", true);
                        out.write_value(arg0,String.class);
                        out.write_value(arg1,ConsentKey.class);
                        out.write_value(arg2,ArdaisstaffKey.class);
                        out.write_value(arg3,String.class);
                        in = (InputStream)_invoke(out);
                        return (Asmform) in.read_Object(Asmform.class);
                    } catch (ApplicationException ex) {
                        in = (InputStream) ex.getInputStream();
                        String id = in.read_string();
                        if (id.equals("IDL:javax/ejb/CreateEx:1.0")) {
                            throw (CreateException) in.read_value(CreateException.class);
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
                ServantObject so = _servant_preinvoke("create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_ConsentKey__com_ardais_bigr_iltds_beans_ArdaisstaffKey__CORBA_WStringValue",com.ardais.bigr.iltds.beans.AsmformHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    Object[] copies = Util.copyObjects(new Object[]{arg0,arg1,arg2,arg3},_orb());
                    String arg0Copy = (String) copies[0];
                    ConsentKey arg1Copy = (ConsentKey) copies[1];
                    ArdaisstaffKey arg2Copy = (ArdaisstaffKey) copies[2];
                    String arg3Copy = (String) copies[3];
                    Asmform result = ((com.ardais.bigr.iltds.beans.AsmformHome)so.servant).create(arg0Copy, arg1Copy, arg2Copy, arg3Copy);
                    return (Asmform)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    if (exCopy instanceof CreateException) {
                        throw (CreateException)exCopy;
                    }
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
    
    public Enumeration findAsmformByConsent(ConsentKey arg0) throws RemoteException, FinderException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("findAsmformByConsent", true);
                        out.write_value(arg0,ConsentKey.class);
                        in = (InputStream)_invoke(out);
                        return (Enumeration) in.read_value(Enumeration.class);
                    } catch (ApplicationException ex) {
                        in = (InputStream) ex.getInputStream();
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
                ServantObject so = _servant_preinvoke("findAsmformByConsent",com.ardais.bigr.iltds.beans.AsmformHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    ConsentKey arg0Copy = (ConsentKey) Util.copyObject(arg0,_orb());
                    Enumeration result = ((com.ardais.bigr.iltds.beans.AsmformHome)so.servant).findAsmformByConsent(arg0Copy);
                    return (Enumeration)Util.copyObject(result,_orb());
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
    
    public Enumeration findByASMFormID(String arg0) throws RemoteException, FinderException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("findByASMFormID", true);
                        out.write_value(arg0,String.class);
                        in = (InputStream)_invoke(out);
                        return (Enumeration) in.read_value(Enumeration.class);
                    } catch (ApplicationException ex) {
                        in = (InputStream) ex.getInputStream();
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
                ServantObject so = _servant_preinvoke("findByASMFormID",com.ardais.bigr.iltds.beans.AsmformHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    Enumeration result = ((com.ardais.bigr.iltds.beans.AsmformHome)so.servant).findByASMFormID(arg0);
                    return (Enumeration)Util.copyObject(result,_orb());
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
    
    public Asmform findByPrimaryKey(AsmformKey arg0) throws FinderException, RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("findByPrimaryKey", true);
                        out.write_value(arg0,AsmformKey.class);
                        in = (InputStream)_invoke(out);
                        return (Asmform) in.read_Object(Asmform.class);
                    } catch (ApplicationException ex) {
                        in = (InputStream) ex.getInputStream();
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
                ServantObject so = _servant_preinvoke("findByPrimaryKey",com.ardais.bigr.iltds.beans.AsmformHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    AsmformKey arg0Copy = (AsmformKey) Util.copyObject(arg0,_orb());
                    Asmform result = ((com.ardais.bigr.iltds.beans.AsmformHome)so.servant).findByPrimaryKey(arg0Copy);
                    return (Asmform)Util.copyObject(result,_orb());
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
    
    public Asmform create(String arg0, Consent arg1) throws CreateException, RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_Consent", true);
                        out.write_value(arg0,String.class);
                        Util.writeRemoteObject(out,arg1);
                        in = (InputStream)_invoke(out);
                        return (Asmform) in.read_Object(Asmform.class);
                    } catch (ApplicationException ex) {
                        in = (InputStream) ex.getInputStream();
                        String id = in.read_string();
                        if (id.equals("IDL:javax/ejb/CreateEx:1.0")) {
                            throw (CreateException) in.read_value(CreateException.class);
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
                ServantObject so = _servant_preinvoke("create__CORBA_WStringValue__com_ardais_bigr_iltds_beans_Consent",com.ardais.bigr.iltds.beans.AsmformHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    Consent arg1Copy = (Consent) Util.copyObject(arg1,_orb());
                    Asmform result = ((com.ardais.bigr.iltds.beans.AsmformHome)so.servant).create(arg0, arg1Copy);
                    return (Asmform)Util.copyObject(result,_orb());
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    if (exCopy instanceof CreateException) {
                        throw (CreateException)exCopy;
                    }
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
}
