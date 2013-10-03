package com.ardais.bigr.iltds.web.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXDetailsUpdateBoxLocation;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.action.BtxAction;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author GYost
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class UpdateBoxLocationEditAction extends BtxAction {

    /**
     * @see com.ardais.bigr.web.action.BtxAction#doBtxPerform(BTXDetails, BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
     */
    protected BTXDetails doBtxPerform(
        BTXDetails btxDetails0,
        BigrActionMapping mapping,
        BigrActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

        BTXDetailsUpdateBoxLocation btxDetails =
            (BTXDetailsUpdateBoxLocation) btxDetails0;

        btxDetails.setBoxId(request.getParameter("boxID").trim());
        btxDetails.setUpdatePart(request.getParameter("update"));
        {
            BTXBoxLocation newBoxLoc = new BTXBoxLocation();
            newBoxLoc.setStorageTypeCid(
                request.getParameter("storageTypeCid").trim());
            newBoxLoc.setLocationAddressID(
                request.getParameter("newLocation").trim());
            newBoxLoc.setUnitName(
                request.getParameter("freezerlist").trim());
            newBoxLoc.setRoomID(request.getParameter("roomlist").trim());
            newBoxLoc.setDrawerID(request.getParameter("drawerlist").trim());
            newBoxLoc.setSlotID(request.getParameter("slotlist").trim());
            btxDetails.setNewBoxLocation(newBoxLoc);
        }

        HttpSession session = request.getSession(false);

        if (session != null) {
            btxDetails.setStorageTypes((LegalValueSet)session.getAttribute("storageTypes"));
            btxDetails.setRoomList((Vector) session.getAttribute("roomlist"));
            btxDetails.setUnitList(
                (Vector) session.getAttribute("freezerlist"));
            btxDetails.setDrawerList(
                (Vector) session.getAttribute("drawerlist"));
            btxDetails.setSlotList((Vector) session.getAttribute("slotlist"));
        }

        // Perform the transaction.
        //
        BTXDetailsUpdateBoxLocation resultBtxDetails =
            (BTXDetailsUpdateBoxLocation) invokeBusinessTransaction(btxDetails,
                mapping);

        // Transfer some result attributes to the HTTP session object.
        //
        {
            LegalValueSet storageTypes = resultBtxDetails.getStorageTypes();
            Vector roomlist = resultBtxDetails.getRoomList();
            Vector unitlist = resultBtxDetails.getUnitList();
            Vector drawerlist = resultBtxDetails.getDrawerList();
            Vector slotlist = resultBtxDetails.getSlotList();

            if (session != null) {
                if (storageTypes == null) {
                  session.removeAttribute("storageTypes");
                }
                else {
                  session.setAttribute("storageTypes", storageTypes);
                }
                if (roomlist == null) {
                    session.removeAttribute("roomlist");
                }
                else {
                    session.setAttribute("roomlist", roomlist);
                }

                if (unitlist == null) {
                    session.removeAttribute("freezerlist");
                }
                else {
                    session.setAttribute("freezerlist", unitlist);
                }

                if (drawerlist == null) {
                    session.removeAttribute("drawerlist");
                }
                else {
                    session.setAttribute("drawerlist", drawerlist);
                }

                if (slotlist == null) {
                    session.removeAttribute("slotlist");
                }
                else {
                    session.setAttribute("slotlist", slotlist);
                }
            }
        }

        return resultBtxDetails;
    }

}
