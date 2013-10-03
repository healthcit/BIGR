// Tie class generated by rmic, do not edit.
// Contents subject to change without notice.

package com.ardais.bigr.es.beans;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.math.BigDecimal;
import java.rmi.Remote;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.FinderException;
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

public class _EJSRemoteCMPArdaisuser_3fdc52da_Tie extends org.omg.CORBA_2_3.portable.ObjectImpl implements Tie {
    
    private EJSRemoteCMPArdaisuser_3fdc52da target = null;
    private ORB orb = null;
    
    private static final String[] _type_ids = {
        "RMI:com.ardais.bigr.es.beans.Ardaisuser:0000000000000000", 
        "RMI:javax.ejb.EJBObject:0000000000000000", 
        "RMI:com.ibm.ivj.ejb.runtime.CopyHelper:0000000000000000", 
        "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
        "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000"
    };
    
    public void setTarget(Remote target) {
        this.target = (EJSRemoteCMPArdaisuser_3fdc52da) target;
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
            switch (method.hashCode()) {
                case -2095870068: 
                    if (method.equals("_get_user_title")) {
                        return _get_user_title(in, reply);
                    }
                case -2076203351: 
                    if (method.equals("J_copyFromEJB")) {
                        return J_copyFromEJB(in, reply);
                    }
                case -2038224444: 
                    if (method.equals("_set_consecutive_failed_answers")) {
                        return _set_consecutive_failed_answers(in, reply);
                    }
                case -1803523977: 
                    if (method.equals("_set_updated_by")) {
                        return _set_updated_by(in, reply);
                    }
                case -1714165651: 
                    if (method.equals("_get_user_lastname")) {
                        return _get_user_lastname(in, reply);
                    }
                case -1597297787: 
                    if (method.equals("_get_user_email_address")) {
                        return _get_user_email_address(in, reply);
                    }
                case -1551390981: 
                    if (method.equals("_set_consecutive_failed_logins")) {
                        return _set_consecutive_failed_logins(in, reply);
                    }
                case -1550521068: 
                    if (method.equals("_get_EJBHome")) {
                        return _get_EJBHome(in, reply);
                    }
                case -1436316758: 
                    if (method.equals("_set_user_department")) {
                        return _set_user_department(in, reply);
                    }
                case -1289431662: 
                    if (method.equals("_set_user_phone_number")) {
                        return _set_user_phone_number(in, reply);
                    }
                case -1278676753: 
                    if (method.equals("_get_consecutive_failed_logins")) {
                        return _get_consecutive_failed_logins(in, reply);
                    }
                case -1260558409: 
                    if (method.equals("_set_password")) {
                        return _set_password(in, reply);
                    }
                case -1147794507: 
                    if (method.equals("secondaryAddShoppingcart")) {
                        return secondaryAddShoppingcart(in, reply);
                    }
                case -1011244123: 
                    if (method.equals("_get_primaryKey")) {
                        return _get_primaryKey(in, reply);
                    }
                case -989890628: 
                    if (method.equals("_get_update_date")) {
                        return _get_update_date(in, reply);
                    }
                case -943445602: 
                    if (method.equals("_set_user_address_id")) {
                        return _set_user_address_id(in, reply);
                    }
                case -934610812: 
                    if (method.equals("remove")) {
                        return remove(in, reply);
                    }
                case -910018711: 
                    if (method.equals("_get_create_date")) {
                        return _get_create_date(in, reply);
                    }
                case -797564271: 
                    if (method.equals("_get_user_verif_question")) {
                        return _get_user_verif_question(in, reply);
                    }
                case -794581318: 
                    if (method.equals("_get_user_mobile_number")) {
                        return _get_user_mobile_number(in, reply);
                    }
                case -784538880: 
                    if (method.equals("getArdaisorder")) {
                        return getArdaisorder(in, reply);
                    }
                case -733096663: 
                    if (method.equals("_get_user_verif_answer")) {
                        return _get_user_verif_answer(in, reply);
                    }
                case -359841020: 
                    if (method.equals("_get_user_phone_ext")) {
                        return _get_user_phone_ext(in, reply);
                    }
                case -284169479: 
                    if (method.equals("_set_user_email_address")) {
                        return _set_user_email_address(in, reply);
                    }
                case -240215170: 
                    if (method.equals("_set_user_active_ind")) {
                        return _set_user_active_ind(in, reply);
                    }
                case -218237442: 
                    if (method.equals("secondaryRemoveArdaisorder")) {
                        return secondaryRemoveArdaisorder(in, reply);
                    }
                case -180989518: 
                    if (method.equals("_get_ardaisaccountKey")) {
                        return _get_ardaisaccountKey(in, reply);
                    }
                case -138379478: 
                    if (method.equals("_set_created_by")) {
                        return _set_created_by(in, reply);
                    }
                case -80132712: 
                    if (method.equals("_set_user_affiliation")) {
                        return _set_user_affiliation(in, reply);
                    }
                case -79138104: 
                    if (method.equals("_set_update_date")) {
                        return _set_update_date(in, reply);
                    }
                case -31787773: 
                    if (method.equals("_get_updated_by")) {
                        return _get_updated_by(in, reply);
                    }
                case 733813: 
                    if (method.equals("_set_create_date")) {
                        return _set_create_date(in, reply);
                    }
                case 44936040: 
                    if (method.equals("_get_user_functional_title")) {
                        return _get_user_functional_title(in, reply);
                    }
                case 49416958: 
                    if (method.equals("getShoppingcart")) {
                        return getShoppingcart(in, reply);
                    }
                case 107411102: 
                    if (method.equals("_get_user_department")) {
                        return _get_user_department(in, reply);
                    }
                case 176000952: 
                    if (method.equals("J_copyToEJB")) {
                        return J_copyToEJB(in, reply);
                    }
                case 180646965: 
                    if (method.equals("_set_passwordPolicyCid")) {
                        return _set_passwordPolicyCid(in, reply);
                    }
                case 274483971: 
                    if (method.equals("_set_user_fax_number")) {
                        return _set_user_fax_number(in, reply);
                    }
                case 424892352: 
                    if (method.equals("secondaryRemoveShoppingcart")) {
                        return secondaryRemoveShoppingcart(in, reply);
                    }
                case 427361024: 
                    if (method.equals("_set_user_title")) {
                        return _set_user_title(in, reply);
                    }
                case 469324678: 
                    if (method.equals("_get_user_phone_number")) {
                        return _get_user_phone_number(in, reply);
                    }
                case 476892373: 
                    if (method.equals("addArdaisorder")) {
                        return addArdaisorder(in, reply);
                    }
                case 518546990: 
                    if (method.equals("_set_user_mobile_number")) {
                        return _set_user_mobile_number(in, reply);
                    }
                case 530790692: 
                    if (method.equals("_get_user_affiliation")) {
                        return _get_user_affiliation(in, reply);
                    }
                case 560192632: 
                    if (method.equals("_set_user_phone_ext")) {
                        return _set_user_phone_ext(in, reply);
                    }
                case 600282258: 
                    if (method.equals("_get_user_address_id")) {
                        return _get_user_address_id(in, reply);
                    }
                case 757714135: 
                    if (method.equals("_get_passwordLastChangeDate")) {
                        return _get_passwordLastChangeDate(in, reply);
                    }
                case 802629743: 
                    if (method.equals("_get_user_firstname")) {
                        return _get_user_firstname(in, reply);
                    }
                case 888227700: 
                    if (method.equals("_set_user_functional_title")) {
                        return _set_user_functional_title(in, reply);
                    }
                case 977956713: 
                    if (method.equals("secondaryAddArdaisorder")) {
                        return secondaryAddArdaisorder(in, reply);
                    }
                case 1129951819: 
                    if (method.equals("_set_passwordLastChangeDate")) {
                        return _set_passwordLastChangeDate(in, reply);
                    }
                case 1254707613: 
                    if (method.equals("_set_user_verif_question")) {
                        return _set_user_verif_question(in, reply);
                    }
                case 1264756395: 
                    if (method.equals("isIdentical")) {
                        return isIdentical(in, reply);
                    }
                case 1303512690: 
                    if (method.equals("_get_user_active_ind")) {
                        return _get_user_active_ind(in, reply);
                    }
                case 1485416259: 
                    if (method.equals("_get_password")) {
                        return _get_password(in, reply);
                    }
                case 1633356726: 
                    if (method.equals("_get_created_by")) {
                        return _get_created_by(in, reply);
                    }
                case 1640648825: 
                    if (method.equals("_set_user_lastname")) {
                        return _set_user_lastname(in, reply);
                    }
                case 1722663395: 
                    if (method.equals("_set_user_firstname")) {
                        return _set_user_firstname(in, reply);
                    }
                case 1803114293: 
                    if (method.equals("_set_user_verif_answer")) {
                        return _set_user_verif_answer(in, reply);
                    }
                case 1818211831: 
                    if (method.equals("_get_user_fax_number")) {
                        return _get_user_fax_number(in, reply);
                    }
                case 1939403305: 
                    if (method.equals("_get_passwordPolicyCid")) {
                        return _get_passwordPolicyCid(in, reply);
                    }
                case 1944413392: 
                    if (method.equals("_get_handle")) {
                        return _get_handle(in, reply);
                    }
                case 2002174303: 
                    if (method.equals("getArdaisaccount")) {
                        return getArdaisaccount(in, reply);
                    }
                case 2120949328: 
                    if (method.equals("_get_consecutive_failed_answers")) {
                        return _get_consecutive_failed_answers(in, reply);
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
    
    private OutputStream J_copyFromEJB(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Hashtable result = target._copyFromEJB();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Hashtable.class);
        return out;
    }
    
    private OutputStream J_copyToEJB(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Hashtable arg0 = (Hashtable) in.read_value(Hashtable.class);
        target._copyToEJB(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream addArdaisorder(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Ardaisorder arg0 = (Ardaisorder) in.read_Object(Ardaisorder.class);
        target.addArdaisorder(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream getArdaisorder(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Enumeration result;
        try {
            result = target.getArdaisorder();
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
    
    private OutputStream _get_create_date(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp result = target.getCreate_date();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Timestamp.class);
        return out;
    }
    
    private OutputStream _get_created_by(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getCreated_by();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_password(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getPassword();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_update_date(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp result = target.getUpdate_date();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Timestamp.class);
        return out;
    }
    
    private OutputStream _get_updated_by(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getUpdated_by();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_user_active_ind(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getUser_active_ind();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_user_address_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        BigDecimal result = target.getUser_address_id();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,BigDecimal.class);
        return out;
    }
    
    private OutputStream _get_user_affiliation(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getUser_affiliation();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_user_email_address(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getUser_email_address();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_user_fax_number(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getUser_fax_number();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_user_firstname(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getUser_firstname();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_user_functional_title(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getUser_functional_title();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_user_lastname(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getUser_lastname();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_user_mobile_number(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getUser_mobile_number();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_user_phone_ext(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getUser_phone_ext();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_user_phone_number(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getUser_phone_number();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_user_title(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getUser_title();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_user_verif_answer(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getUser_verif_answer();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _get_user_verif_question(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getUser_verif_question();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream secondaryAddArdaisorder(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Ardaisorder arg0 = (Ardaisorder) in.read_Object(Ardaisorder.class);
        target.secondaryAddArdaisorder(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryRemoveArdaisorder(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Ardaisorder arg0 = (Ardaisorder) in.read_Object(Ardaisorder.class);
        target.secondaryRemoveArdaisorder(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_create_date(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp arg0 = (Timestamp) in.read_value(Timestamp.class);
        target.setCreate_date(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_created_by(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setCreated_by(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_password(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setPassword(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_update_date(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp arg0 = (Timestamp) in.read_value(Timestamp.class);
        target.setUpdate_date(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_updated_by(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setUpdated_by(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_user_active_ind(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setUser_active_ind(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_user_address_id(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        BigDecimal arg0 = (BigDecimal) in.read_value(BigDecimal.class);
        target.setUser_address_id(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_user_affiliation(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setUser_affiliation(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_user_email_address(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setUser_email_address(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_user_fax_number(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setUser_fax_number(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_user_firstname(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setUser_firstname(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_user_functional_title(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setUser_functional_title(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_user_lastname(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setUser_lastname(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_user_mobile_number(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setUser_mobile_number(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_user_phone_ext(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setUser_phone_ext(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_user_phone_number(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setUser_phone_number(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_user_title(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setUser_title(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_user_verif_answer(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setUser_verif_answer(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _set_user_verif_question(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setUser_verif_question(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryAddShoppingcart(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Shoppingcart arg0 = (Shoppingcart) in.read_Object(Shoppingcart.class);
        target.secondaryAddShoppingcart(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream secondaryRemoveShoppingcart(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Shoppingcart arg0 = (Shoppingcart) in.read_Object(Shoppingcart.class);
        target.secondaryRemoveShoppingcart(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream getShoppingcart(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Enumeration result;
        try {
            result = target.getShoppingcart();
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
    
    private OutputStream _get_ardaisaccountKey(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        ArdaisaccountKey result = target.getArdaisaccountKey();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,ArdaisaccountKey.class);
        return out;
    }
    
    private OutputStream getArdaisaccount(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Ardaisaccount result;
        try {
            result = target.getArdaisaccount();
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
    
    private OutputStream _get_passwordPolicyCid(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getPasswordPolicyCid();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _set_passwordPolicyCid(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setPasswordPolicyCid(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_passwordLastChangeDate(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp result = target.getPasswordLastChangeDate();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Timestamp.class);
        return out;
    }
    
    private OutputStream _set_passwordLastChangeDate(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Timestamp arg0 = (Timestamp) in.read_value(Timestamp.class);
        target.setPasswordLastChangeDate(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_user_department(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String result = target.getUser_department();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,String.class);
        return out;
    }
    
    private OutputStream _set_user_department(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        String arg0 = (String) in.read_value(String.class);
        target.setUser_department(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_consecutive_failed_logins(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Integer result = target.getConsecutive_failed_logins();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Integer.class);
        return out;
    }
    
    private OutputStream _set_consecutive_failed_logins(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Integer arg0 = (Integer) in.read_value(Integer.class);
        target.setConsecutive_failed_logins(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
    
    private OutputStream _get_consecutive_failed_answers(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Integer result = target.getConsecutive_failed_answers();
        org.omg.CORBA_2_3.portable.OutputStream out = 
            (org.omg.CORBA_2_3.portable.OutputStream) reply.createReply();
        out.write_value(result,Integer.class);
        return out;
    }
    
    private OutputStream _set_consecutive_failed_answers(org.omg.CORBA_2_3.portable.InputStream in , ResponseHandler reply) throws Throwable {
        Integer arg0 = (Integer) in.read_value(Integer.class);
        target.setConsecutive_failed_answers(arg0);
        OutputStream out = reply.createReply();
        return out;
    }
}