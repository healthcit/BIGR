// Tie class generated by rmic, do not edit.
// Contents subject to change without notice.

package com.ardais.bigr.iltds.beans;

import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.assistants.LegalValueSetHierarchy;
import java.io.Serializable;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.rmi.Remote;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
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

public class _EJSRemoteStatelessListGenerator_18f40e11_Tie extends org.omg.CORBA_2_3.portable.ObjectImpl implements Tie {
    
    private EJSRemoteStatelessListGenerator_18f40e11 target = null;
    private ORB orb = null;
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.iltds.beans.ListGenerator:0000000000000000", 
        "RMI:javax.ejb.EJBObject:0000000000000000", 
        "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
        "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000"
    };
    
    public void setTarget(Remote target) {
        this.target = (EJSRemoteStatelessListGenerator_18f40e11) target;
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
                case 11: 
                    if (method.equals("_get_handle")) {
                        return _get_handle(in, reply);
                    } else if (method.equals("isIdentical")) {
                        return isIdentical(in, reply);
                    }
                case 12: 
                    if (method.equals("getArdLookup")) {
                        return getArdLookup(in, reply);
                    } else if (method.equals("getPtsOrders")) {
                        return getPtsOrders(in, reply);
                    } else if (method.equals("_get_EJBHome")) {
                        return _get_EJBHome(in, reply);
                    }
                case 15: 
                    if (method.equals("_get_primaryKey")) {
                        return _get_primaryKey(in, reply);
                    } else if (method.equals("_get_ptsClients")) {
                        return _get_ptsClients(in, reply);
                    } else if (method.equals("getUserRealName")) {
                        return getUserRealName(in, reply);
                    }
                case 17: 
                    if (method.equals("lookupDiseaseCode")) {
                        return lookupDiseaseCode(in, reply);
                    } else if (method.equals("lookupDiseaseName")) {
                        return lookupDiseaseName(in, reply);
                    }
                case 18: 
                    if (method.equals("getSampleFromSlide")) {
                        return getSampleFromSlide(in, reply);
                    } else if (method.equals("_get_specimenTypes")) {
                        return _get_specimenTypes(in, reply);
                    } else if (method.equals("_get_donorAccounts")) {
                        return _get_donorAccounts(in, reply);
                    }
                case 19: 
                    if (method.equals("_get_donorLocations")) {
                        return _get_donorLocations(in, reply);
                    } else if (method.equals("getPtsShoppingCarts")) {
                        return getPtsShoppingCarts(in, reply);
                    } else if (method.equals("lookupDIAccountName")) {
                        return lookupDIAccountName(in, reply);
                    }
                case 20: 
                    if (method.equals("getPtsUsersByAccount")) {
                        return getPtsUsersByAccount(in, reply);
                    } else if (method.equals("trackingNumberExists")) {
                        return trackingNumberExists(in, reply);
                    }
                case 21: 
                    if (method.equals("findBoxesByManifestID")) {
                        return findBoxesByManifestID(in, reply);
                    }
                case 22: 
                    if (method.equals("findPrintableManifests")) {
                        return findPrintableManifests(in, reply);
                    }
                case 24: 
                    if (method.equals("_get_logicalRepositories")) {
                        return _get_logicalRepositories(in, reply);
                    } else if (method.equals("findNextLocationDropDown")) {
                        return findNextLocationDropDown(in, reply);
                    }
                case 26: 
                    if (method.equals("lookupArdLookupDescription")) {
                        return lookupArdLookupDescription(in, reply);
                    }
                case 27: 
                    if (method.equals("findAvailableBoxLocationAll")) {
                        return findAvailableBoxLocationAll(in, reply);
                    } else if (method.equals("_get_finishedProductFormats")) {
                        return _get_finishedProductFormats(in, reply);
                    } else if (method.equals("getWorkOrderStatusesForType")) {
                        return getWorkOrderStatusesForType(in, reply);
                    }
                case 28: 
                    if (method.equals("lookupArdaisStaffByAccountId")) {
                        return lookupArdaisStaffByAccountId(in, reply);
                    }
                case 30: 
                    if (method.equals("_get_workOrderTypesAndStatuses")) {
                        return _get_workOrderTypesAndStatuses(in, reply);
                    }
                case 31: 
                    if (method.equals("_get_workOrderStatusTypeMapping")) {
                        return _get_workOrderStatusTypeMapping(in, reply);
                    }
                case 45: 
                    if (method.equals("findActiveConsentVersions__CORBA_WStringValue")) {
                        return findActiveConsentVersions__CORBA_WStringValue(in, reply);
                    }
                case 63: 
                    if (method.equals("findActiveConsentVersions__CORBA_WStringValue__boolean__boolean")) {
                        return findActiveConsentVersions__CORBA_WStringValue__boolean__boolean(in, reply);
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
    
    private OutputStream findAvailableBoxLocationAll(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        Vector result = target.findAvailableBoxLocationAll(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Vector.class);
        return out;
    }
    
    private OutputStream findBoxesByManifestID(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        Vector result = target.findBoxesByManifestID(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Vector.class);
        return out;
    }
    
    private OutputStream findActiveConsentVersions__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        List result = target.findActiveConsentVersions(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,List.class);
        return out;
    }
    
    private OutputStream findActiveConsentVersions__CORBA_WStringValue__boolean__boolean(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        boolean arg1 = in.read_boolean();
        boolean arg2 = in.read_boolean();
        List result = target.findActiveConsentVersions(arg0, arg1, arg2);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,List.class);
        return out;
    }
    
    private OutputStream _get_logicalRepositories(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        List result = target.getLogicalRepositories();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,List.class);
        return out;
    }
    
    private OutputStream findNextLocationDropDown(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        String arg1 = (String) in.read_value(String.class);
        String arg2 = (String) in.read_value(String.class);
        String arg3 = (String) in.read_value(String.class);
        String arg4 = (String) in.read_value(String.class);
        Vector result = target.findNextLocationDropDown(arg0, arg1, arg2, arg3, arg4);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Vector.class);
        return out;
    }
    
    private OutputStream getArdLookup(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        LegalValueSet result = target.getArdLookup(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,LegalValueSet.class);
        return out;
    }
    
    private OutputStream _get_donorLocations(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        LegalValueSet result = target.getDonorLocations();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,LegalValueSet.class);
        return out;
    }
    
    private OutputStream _get_finishedProductFormats(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        LegalValueSet result = target.getFinishedProductFormats();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,LegalValueSet.class);
        return out;
    }
    
    private OutputStream _get_ptsClients(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        LegalValueSet result = target.getPtsClients();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,LegalValueSet.class);
        return out;
    }
    
    private OutputStream getPtsOrders(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        LegalValueSet result = target.getPtsOrders(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,LegalValueSet.class);
        return out;
    }
    
    private OutputStream getPtsShoppingCarts(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        LegalValueSet result = target.getPtsShoppingCarts(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,LegalValueSet.class);
        return out;
    }
    
    private OutputStream getPtsUsersByAccount(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        LegalValueSet result = target.getPtsUsersByAccount(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,LegalValueSet.class);
        return out;
    }
    
    private OutputStream getSampleFromSlide(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        String result = target.getSampleFromSlide(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_specimenTypes(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        LegalValueSet result = target.getSpecimenTypes();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,LegalValueSet.class);
        return out;
    }
    
    private OutputStream getUserRealName(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        String arg1 = (String) in.read_value(String.class);
        String result = target.getUserRealName(arg0, arg1);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream getWorkOrderStatusesForType(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        LegalValueSet result = target.getWorkOrderStatusesForType(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,LegalValueSet.class);
        return out;
    }
    
    private OutputStream _get_workOrderStatusTypeMapping(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        LegalValueSet result = target.getWorkOrderStatusTypeMapping();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,LegalValueSet.class);
        return out;
    }
    
    private OutputStream _get_workOrderTypesAndStatuses(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        LegalValueSetHierarchy result = target.getWorkOrderTypesAndStatuses();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,LegalValueSetHierarchy.class);
        return out;
    }
    
    private OutputStream lookupArdaisStaffByAccountId(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        Vector result = target.lookupArdaisStaffByAccountId(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Vector.class);
        return out;
    }
    
    private OutputStream lookupArdLookupDescription(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        String arg1 = (String) in.read_value(String.class);
        String result = target.lookupArdLookupDescription(arg0, arg1);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream lookupDIAccountName(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        String result = target.lookupDIAccountName(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream lookupDiseaseCode(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        String result = target.lookupDiseaseCode(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream lookupDiseaseName(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        String result = target.lookupDiseaseName(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream trackingNumberExists(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        boolean result = target.trackingNumberExists(arg0);
        OutputStream out = reply.createReply();
        out.write_boolean(result);
        return out;
    }
    
    private OutputStream _get_donorAccounts(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Collection result = target.getDonorAccounts();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value((Serializable)result,Collection.class);
        return out;
    }
    
    private OutputStream findPrintableManifests(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        Vector result = target.findPrintableManifests(arg0);
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Vector.class);
        return out;
    }
}
