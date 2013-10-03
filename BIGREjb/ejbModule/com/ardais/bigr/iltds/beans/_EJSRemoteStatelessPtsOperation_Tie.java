// Tie class generated by rmic, do not edit.
// Contents subject to change without notice.

package com.ardais.bigr.iltds.beans;

import com.ardais.bigr.es.javabeans.QueryBean;
import com.ardais.bigr.iltds.assistants.LineItemDataBean;
import com.ardais.bigr.iltds.assistants.ProjectDataBean;
import com.ardais.bigr.iltds.assistants.SampleQueryBuilder;
import com.ardais.bigr.iltds.assistants.WorkOrderDataBean;
import com.ardais.bigr.security.SecurityInfo;
import java.io.Serializable;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.rmi.Remote;
import java.util.List;
import javax.ejb.DuplicateKeyException;
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

public class _EJSRemoteStatelessPtsOperation_Tie extends org.omg.CORBA_2_3.portable.ObjectImpl implements Tie {
    
    private EJSRemoteStatelessPtsOperation target = null;
    private ORB orb = null;
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.iltds.beans.PtsOperation:0000000000000000", 
        "RMI:javax.ejb.EJBObject:0000000000000000", 
        "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
        "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000"
    };
    
    public void setTarget(Remote target) {
        this.target = (EJSRemoteStatelessPtsOperation) target;
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
                case 10: 
                    if (method.equals("getProject")) {
                        return getProject(in, reply);
                    }
                case 11: 
                    if (method.equals("_get_handle")) {
                        return _get_handle(in, reply);
                    } else if (method.equals("isIdentical")) {
                        return isIdentical(in, reply);
                    } else if (method.equals("getLineItem")) {
                        return getLineItem(in, reply);
                    } else if (method.equals("getProjects")) {
                        return getProjects(in, reply);
                    }
                case 12: 
                    if (method.equals("getLineItems")) {
                        return getLineItems(in, reply);
                    } else if (method.equals("getWorkOrder")) {
                        return getWorkOrder(in, reply);
                    } else if (method.equals("_get_EJBHome")) {
                        return _get_EJBHome(in, reply);
                    }
                case 13: 
                    if (method.equals("createProject")) {
                        return createProject(in, reply);
                    } else if (method.equals("getWorkOrders")) {
                        return getWorkOrders(in, reply);
                    } else if (method.equals("updateProject")) {
                        return updateProject(in, reply);
                    }
                case 14: 
                    if (method.equals("createLineItem")) {
                        return createLineItem(in, reply);
                    } else if (method.equals("updateLineItem")) {
                        return updateLineItem(in, reply);
                    }
                case 15: 
                    if (method.equals("_get_primaryKey")) {
                        return _get_primaryKey(in, reply);
                    } else if (method.equals("createWorkOrder")) {
                        return createWorkOrder(in, reply);
                    } else if (method.equals("getTotalSamples")) {
                        return getTotalSamples(in, reply);
                    } else if (method.equals("updateWorkOrder")) {
                        return updateWorkOrder(in, reply);
                    }
                case 16: 
                    if (method.equals("getProjectByName")) {
                        return getProjectByName(in, reply);
                    } else if (method.equals("getSamplesOnHold")) {
                        return getSamplesOnHold(in, reply);
                    }
                case 17: 
                    if (method.equals("availableInvQuery")) {
                        return availableInvQuery(in, reply);
                    }
                case 18: 
                    if (method.equals("getSamplesToRemove")) {
                        return getSamplesToRemove(in, reply);
                    }
                case 19: 
                    if (method.equals("addSamplesToProject")) {
                        return addSamplesToProject(in, reply);
                    }
                case 21: 
                    if (method.equals("getCompleteSampleInfo")) {
                        return getCompleteSampleInfo(in, reply);
                    } else if (method.equals("getSamplesToAddToHold")) {
                        return getSamplesToAddToHold(in, reply);
                    } else if (method.equals("getTotalSamplesOnHold")) {
                        return getTotalSamplesOnHold(in, reply);
                    } else if (method.equals("removeSamplesFromHold")) {
                        return removeSamplesFromHold(in, reply);
                    }
                case 24: 
                    if (method.equals("removeSamplesFromProject")) {
                        return removeSamplesFromProject(in, reply);
                    }
                case 25: 
                    if (method.equals("buildSamplesToRemoveQuery")) {
                        return buildSamplesToRemoveQuery(in, reply);
                    }
                case 26: 
                    if (method.equals("addSamplesToHoldForProject")) {
                        return addSamplesToHoldForProject(in, reply);
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
    
    private OutputStream addSamplesToProject(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String[] arg0 = (String[]) in.read_value(String[].class);
        String arg1 = (String) in.read_value(String.class);
        String arg2 = (String) in.read_value(String.class);
        target.addSamplesToProject(arg0, arg1, arg2);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream availableInvQuery(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        QueryBean arg0 = (QueryBean) in.read_value(QueryBean.class);
        SecurityInfo arg1 = (SecurityInfo) in.read_value(SecurityInfo.class);
        List result = target.availableInvQuery(arg0, arg1);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,List.class);
        return out;
    }
    
    private OutputStream buildSamplesToRemoveQuery(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        SampleQueryBuilder result = target.buildSamplesToRemoveQuery();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,SampleQueryBuilder.class);
        return out;
    }
    
    private OutputStream createLineItem(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        LineItemDataBean arg0 = (LineItemDataBean) in.read_value(LineItemDataBean.class);
        LineItemDataBean result = target.createLineItem(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,LineItemDataBean.class);
        return out;
    }
    
    private OutputStream createProject(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        ProjectDataBean arg0 = (ProjectDataBean) in.read_value(ProjectDataBean.class);
        ProjectDataBean result;
        try {
            result = target.createProject(arg0);
        } catch (DuplicateKeyException ex) {
            String id = "IDL:javax/ejb/DuplicateKeyEx:1.0";
            org.omg.CORBA_2_3.portable.OutputStream out = 
                (org.omg.CORBA_2_3.portable.OutputStream) reply.createExceptionReply();
            out.write_string(id);
            out.write_value(ex,DuplicateKeyException.class);
            return out;
        }
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,ProjectDataBean.class);
        return out;
    }
    
    private OutputStream createWorkOrder(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        WorkOrderDataBean arg0 = (WorkOrderDataBean) in.read_value(WorkOrderDataBean.class);
        WorkOrderDataBean result = target.createWorkOrder(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,WorkOrderDataBean.class);
        return out;
    }
    
    private OutputStream getCompleteSampleInfo(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        List result = target.getCompleteSampleInfo(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,List.class);
        return out;
    }
    
    private OutputStream getLineItem(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        LineItemDataBean result = target.getLineItem(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,LineItemDataBean.class);
        return out;
    }
    
    private OutputStream getLineItems(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        List result = target.getLineItems(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,List.class);
        return out;
    }
    
    private OutputStream getProject(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        ProjectDataBean result = target.getProject(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,ProjectDataBean.class);
        return out;
    }
    
    private OutputStream getProjectByName(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        ProjectDataBean result = target.getProjectByName(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,ProjectDataBean.class);
        return out;
    }
    
    private OutputStream getProjects(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        List result = target.getProjects(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,List.class);
        return out;
    }
    
    private OutputStream getSamplesOnHold(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        List result = target.getSamplesOnHold(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,List.class);
        return out;
    }
    
    private OutputStream getSamplesToAddToHold(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        List result = target.getSamplesToAddToHold(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,List.class);
        return out;
    }
    
    private OutputStream getSamplesToRemove(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        List result = target.getSamplesToRemove(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,List.class);
        return out;
    }
    
    private OutputStream getTotalSamples(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        int result = target.getTotalSamples(arg0);
        OutputStream out = reply.createReply();
        out.write_long(result);
        return out;
    }
    
    private OutputStream getTotalSamplesOnHold(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        int result = target.getTotalSamplesOnHold(arg0);
        OutputStream out = reply.createReply();
        out.write_long(result);
        return out;
    }
    
    private OutputStream getWorkOrder(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        WorkOrderDataBean result = target.getWorkOrder(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,WorkOrderDataBean.class);
        return out;
    }
    
    private OutputStream getWorkOrders(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        List result = target.getWorkOrders(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,List.class);
        return out;
    }
    
    private OutputStream addSamplesToHoldForProject(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String[] arg0 = (String[]) in.read_value(String[].class);
        String arg1 = (String) in.read_value(String.class);
        String arg2 = (String) in.read_value(String.class);
        String result = target.addSamplesToHoldForProject(arg0, arg1, arg2);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream removeSamplesFromHold(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String[] arg0 = (String[]) in.read_value(String[].class);
        String[] arg1 = (String[]) in.read_value(String[].class);
        String[] arg2 = (String[]) in.read_value(String[].class);
        int result = target.removeSamplesFromHold(arg0, arg1, arg2);
        OutputStream out = reply.createReply();
        out.write_long(result);
        return out;
    }
    
    private OutputStream removeSamplesFromProject(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        String[] arg1 = (String[]) in.read_value(String[].class);
        String[] arg2 = (String[]) in.read_value(String[].class);
        int result = target.removeSamplesFromProject(arg0, arg1, arg2);
        OutputStream out = reply.createReply();
        out.write_long(result);
        return out;
    }
    
    private OutputStream updateLineItem(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        LineItemDataBean arg0 = (LineItemDataBean) in.read_value(LineItemDataBean.class);
        LineItemDataBean result = target.updateLineItem(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,LineItemDataBean.class);
        return out;
    }
    
    private OutputStream updateProject(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        ProjectDataBean arg0 = (ProjectDataBean) in.read_value(ProjectDataBean.class);
        ProjectDataBean result = target.updateProject(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,ProjectDataBean.class);
        return out;
    }
    
    private OutputStream updateWorkOrder(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        WorkOrderDataBean arg0 = (WorkOrderDataBean) in.read_value(WorkOrderDataBean.class);
        WorkOrderDataBean result = target.updateWorkOrder(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,WorkOrderDataBean.class);
        return out;
    }
}