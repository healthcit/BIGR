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

public class _ProjectsampleHome_Stub extends Stub implements ProjectsampleHome {
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.iltds.beans.ProjectsampleHome:0000000000000000", 
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
    
    public Projectsample create(String arg0, LineitemKey arg1, ProjectKey arg2) throws CreateException, RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("create", true);
                        out.write_value(arg0,String.class);
                        out.write_value(arg1,LineitemKey.class);
                        out.write_value(arg2,ProjectKey.class);
                        in = (InputStream)_invoke(out);
                        return (Projectsample) in.read_Object(Projectsample.class);
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
                ServantObject so = _servant_preinvoke("create",com.ardais.bigr.iltds.beans.ProjectsampleHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    Object[] copies = Util.copyObjects(new Object[]{arg0,arg1,arg2},_orb());
                    String arg0Copy = (String) copies[0];
                    LineitemKey arg1Copy = (LineitemKey) copies[1];
                    ProjectKey arg2Copy = (ProjectKey) copies[2];
                    Projectsample result = ((com.ardais.bigr.iltds.beans.ProjectsampleHome)so.servant).create(arg0Copy, arg1Copy, arg2Copy);
                    return (Projectsample)Util.copyObject(result,_orb());
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
    
    public Projectsample findByPrimaryKey(ProjectsampleKey arg0) throws RemoteException, FinderException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("findByPrimaryKey", true);
                        out.write_value(arg0,ProjectsampleKey.class);
                        in = (InputStream)_invoke(out);
                        return (Projectsample) in.read_Object(Projectsample.class);
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
                ServantObject so = _servant_preinvoke("findByPrimaryKey",com.ardais.bigr.iltds.beans.ProjectsampleHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    ProjectsampleKey arg0Copy = (ProjectsampleKey) Util.copyObject(arg0,_orb());
                    Projectsample result = ((com.ardais.bigr.iltds.beans.ProjectsampleHome)so.servant).findByPrimaryKey(arg0Copy);
                    return (Projectsample)Util.copyObject(result,_orb());
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
    
    public Enumeration findProjectsampleByLineitem(LineitemKey arg0) throws RemoteException, FinderException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("findProjectsampleByLineitem", true);
                        out.write_value(arg0,LineitemKey.class);
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
                ServantObject so = _servant_preinvoke("findProjectsampleByLineitem",com.ardais.bigr.iltds.beans.ProjectsampleHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    LineitemKey arg0Copy = (LineitemKey) Util.copyObject(arg0,_orb());
                    Enumeration result = ((com.ardais.bigr.iltds.beans.ProjectsampleHome)so.servant).findProjectsampleByLineitem(arg0Copy);
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
    
    public Enumeration findProjectsampleByProject(ProjectKey arg0) throws RemoteException, FinderException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        org.omg.CORBA_2_3.portable.OutputStream out = 
                            (org.omg.CORBA_2_3.portable.OutputStream)
                            _request("findProjectsampleByProject", true);
                        out.write_value(arg0,ProjectKey.class);
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
                ServantObject so = _servant_preinvoke("findProjectsampleByProject",com.ardais.bigr.iltds.beans.ProjectsampleHome.class);
                if (so == null) {
                    continue;
                }
                try {
                    ProjectKey arg0Copy = (ProjectKey) Util.copyObject(arg0,_orb());
                    Enumeration result = ((com.ardais.bigr.iltds.beans.ProjectsampleHome)so.servant).findProjectsampleByProject(arg0Copy);
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
}
