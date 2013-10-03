package com.ardais.bigr.iltds.op;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ejb.ObjectNotFoundException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.iltds.beans.AsmformAccessBean;
import com.ardais.bigr.iltds.beans.AsmformKey;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.pdc.oce.util.OceUtil;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;
/**
 * Insert the type's description here.
 * Creation date: (1/17/2001 4:57:54 PM)
 * @author: Jake Thompson
 */
public class ASMFormInfoInsert
	extends com.ardais.bigr.iltds.op.StandardOperation {

	// Holds all of the request parameters.
	private HashMap _params = new HashMap();

	// Initialize all of the request parameters.
	{
		_params.put("asmFormID", null);
		_params.put("consentID", null);
		_params.put("grossHour", null);
		_params.put("grossMinute", null);
		_params.put("grossAmpm", null);
		_params.put("removeHour", null);
		_params.put("removeMinute", null);
		_params.put("removeAmpm", null);
		_params.put("accSurgRemoval", null);
		_params.put("employeeFirst", null);
		_params.put("employeeLast", null);
		_params.put("employeeDrop", null);
		_params.put("procedure", null);
		_params.put("otherProcedure", null);
		_params.put("module1", null);
		_params.put("module2", null);
		_params.put("module3", null);
		_params.put("formSaved", "N");
    _params.put("procedure_date","N");
	}
	/**
	 * ASMFormInsert constructor comment.
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param res javax.servlet.http.HttpServletResponse
	 * @param ctx javax.servlet.ServletContext
	 */
	public ASMFormInfoInsert(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (1/17/2001 4:57:54 PM)
	 */
	public void invoke() throws IOException, Exception {

		loadParams();
		if (!isValid()) {
			return;
		}

		String user = (String) request.getSession(false).getAttribute("user");

		Calendar removeDate = null;
		Calendar grossDate = null;
    Calendar procedureDate = null;
		int grossHourInt = Integer.parseInt((String) _params.get("grossHour"));
		int grossMinuteInt =
			Integer.parseInt((String) _params.get("grossMinute"));
		int grossAmpmInt = 0;

		if (((String) _params.get("grossAmpm")) != null) {
			if ((((String) _params.get("grossAmpm")).equals("")) == false) {
				if (((String) _params.get("grossAmpm")).equals("pm")) {
					grossAmpmInt = 1;
				}
			}
		}
		int removeHourInt =
			Integer.parseInt((String) _params.get("removeHour"));
		int removeMinuteInt =
			Integer.parseInt((String) _params.get("removeMinute"));
		int removeAmpmInt = 0;
		if (((String) _params.get("removeAmpm")) != null) {
			if ((((String) _params.get("removeAmpm")).equals("")) == false) {
				if (((String) _params.get("removeAmpm")).equals("pm")) {
					removeAmpmInt = 1;
				}
			}
		}

		//checks to see if the user entry fields are null or empty
		//if they are, we use the value from the drop down.
		String employee = (String) _params.get("employeeFirst");
		if (employee == null || employee.equals("")) {
			employee = (String) _params.get("employeeDrop");
		}
		
		try {
			removeDate = Calendar.getInstance();
			grossDate = Calendar.getInstance();
			//make the necessary conversions for the date fields.
			if (grossHourInt == 12) {
				grossHourInt = 0;
			}

			grossDate.set(Calendar.DAY_OF_MONTH, 1);
			grossDate.set(Calendar.MINUTE, grossMinuteInt);
			grossDate.set(Calendar.HOUR, grossHourInt);
			grossDate.set(Calendar.AM_PM, grossAmpmInt);

			if (removeHourInt == 12) {
				removeHourInt = 0;
			}

			removeDate.set(Calendar.DAY_OF_MONTH, 1);
			removeDate.set(Calendar.MINUTE, removeMinuteInt);
			removeDate.set(Calendar.HOUR, removeHourInt);
			removeDate.set(Calendar.AM_PM, removeAmpmInt);
			if (grossDate.getTime().getTime()
				< removeDate.getTime().getTime()) {
				grossDate.set(Calendar.DAY_OF_MONTH, 2);
			}
      // MR 6819 Procedure Date computed here, if available...note that
      // it will be set for update in the bean later...
      if ( (((String) _params.get("procedure_date")) != null)) {
        if ((((String) _params.get("procedure_date")).equals("")) == false) {
          // okay, there is a value, so let's set it...date is always
          //  in the form mm/dd/yyyy
          procedureDate = Calendar.getInstance();
          String theDate = _params.get("procedure_date").toString();
          int year = Integer.parseInt(theDate.substring(6));
          int month = (Integer.parseInt(theDate.substring(0,2))) - 1; // month is "0" based
          int day = Integer.parseInt(theDate.substring(3,5));
          // hardcoding the hour, minute, second to 0 since they are not specified
          procedureDate.set(year, month, day, 0, 0, 0);
        }        
      }

		} catch (Exception e) {
			e.printStackTrace();
			ReportError err = new ReportError(request, response, servletCtx);
			err.setFromOp(this.getClass().getName());
			err.setErrorMessage(e.toString());
			try {
				err.invoke();
			} catch (Exception _axxx) {
			}
			return;
		}

		try {
      ConsentAccessBean myCab = new ConsentAccessBean();
			AccessBeanEnumeration consentParent =
				(AccessBeanEnumeration) myCab.findByConsentID(
					(String) _params.get("consentID"));
			myCab = (ConsentAccessBean) consentParent.nextElement();
		} catch (Exception e) {
			e.printStackTrace();
			ReportError err = new ReportError(request, response, servletCtx);
			err.setFromOp(this.getClass().getName());
			err.setErrorMessage(e.toString());
			try {
				err.invoke();
			} catch (Exception _axxx) {
			}
			return;
		}

	 
		 
		ArdaisstaffAccessBean myStaff = null;

		// get the staff id info... 
		try {
			myStaff = new ArdaisstaffAccessBean(new ArdaisstaffKey(employee));
		} catch (ObjectNotFoundException e) {
        /**********************************
        * removed the logic to create new user on the fly
        * in MR 4435 
        *  sat 12/21/02
        **************************************/
		}

		AsmformAccessBean myASMForm;

		try {
			//lookup the asm form.
			AsmformKey asmFormKey =
				new AsmformKey((String) _params.get("asmFormID"));
			myASMForm = new AsmformAccessBean(asmFormKey);
			String curentOtherProc = myASMForm.getSurgical_specimen_id_other();
			//update the grossing date timestamp.
			if (grossHourInt == -1 || grossMinuteInt == -1) {
				myASMForm.setGrossing_date(null);
			} else {
				myASMForm.setGrossing_date(
					new java.sql.Timestamp(grossDate.getTime().getTime()));
			}
			//update the removal date timestamp.
			if (removeHourInt == -1 || removeMinuteInt == -1) {
				myASMForm.setRemoval_date(null);
			} else {
				myASMForm.setRemoval_date(
					new java.sql.Timestamp(removeDate.getTime().getTime()));
			}
			//update the procedure type.
			String procedure = (String) _params.get("procedure");
			String otherProcedure = (String) _params.get("otherProcedure");
			myASMForm.setSurgical_specimen_id(procedure);
			//set the user to the asm form
			if (employee != null && !employee.equals("")) {
				myASMForm.setArdais_staff_id(
					((ArdaisstaffKey) myStaff.__getKey()).ardais_staff_id);
			}
			
			if ((procedure != null)
				&& (procedure.equalsIgnoreCase(FormLogic.OTHER_PX))) {
				myASMForm.setSurgical_specimen_id_other(
					(String) _params.get("otherProcedure"));
			} else {
				myASMForm.setSurgical_specimen_id_other(null);
			}
			
			
			// get the accuracy of surgical remove time concept id...
			// TODO...change the EJB to add this field...
			myASMForm.setAcc_surgical_removal_time((String) _params.get("accSurgRemoval"));
      
      // MR 6819 set the Procedure date, if available...
      if (procedureDate == null) {
        myASMForm.setProcedure_date(null);
      }
      else {
        myASMForm.setProcedure_date(new java.sql.Timestamp(procedureDate.getTime().getTime()));
      }
			
			myASMForm.commitCopyHelper();
      
      //update the collection and preservation date time values for any samples belonging 
      //to this asm form
      IltdsUtils.setSampleCollectionAndPreservationDates(myASMForm);
			
			
			// Save other procedure.					
			if ((procedure != null) && (procedure.equalsIgnoreCase(FormLogic.OTHER_PX))) {
				List pKeys = new ArrayList();
				pKeys.add(myASMForm.getArdais_id());
				pKeys.add((String) _params.get("consentID"));
				pKeys.add((String) _params.get("asmFormID"));
				
				OceUtil.createOce(
					OceUtil.ILTDS_ASMFORM_OTHER_SURGICAL_SPEC,
					pKeys,
					otherProcedure,
					user);
			}
			//Following code added for MR 5044.	
			// If the new "other" procedure is empty and an "other" procedure existed,
			// then update OCE to indicate that(Mark as Obsolete).
			if (!ApiFunctions.isEmpty(curentOtherProc) && ApiFunctions.isEmpty(otherProcedure)) {
				
				List pKeys = new ArrayList();
				pKeys.add(myASMForm.getArdais_id());
				pKeys.add((String) _params.get("consentID"));
				pKeys.add((String) _params.get("asmFormID"));
				String pkStr = OceUtil.buildWhereClause(OceUtil.SURGICAL_SPEC_WHERE_CLAUSE, pKeys);
				pkStr = StringUtils.replace(pkStr, "'", "''");
				OceUtil.markStatusObsolete(OceUtil.ILTDS_ASMFORM_OTHER_SURGICAL_SPEC, 
					                           pkStr, 
					                           user);			
				
			}


		} catch (Exception e) {
			e.printStackTrace();
			ReportError err = new ReportError(request, response, servletCtx);
			err.setFromOp(this.getClass().getName());
			err.setErrorMessage(e.toString());
			try {
				err.invoke();
			} catch (Exception _axxx) {
			}
			return;
		}

		try {
			request.getSession().removeAttribute("asmID_1");
			request.getSession().removeAttribute("consentID_1");
			request.getSession().removeAttribute("prepd_by");
			request.setAttribute("formSaved", "Y");  // form was successfully saved			

			request.setAttribute(
				"myError",
				"The ASM Form Information has been successfully submitted");
			servletCtx
				.getRequestDispatcher("/hiddenJsps/iltds/asm/asmFormInfoInsert.jsp")
				.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			ReportError err = new ReportError(request, response, servletCtx);
			err.setFromOp(this.getClass().getName());
			err.setErrorMessage(e.toString());
			try {
				err.invoke();
			} catch (Exception _axxx) {
			}
			return;
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (1/22/2001 3:49:33 PM)
	 */
	public boolean isValid() throws Exception {
		String proc = (String) _params.get("procedure");
		String otherProc = (String) _params.get("otherProcedure");

		if ((proc != null)
			&& (proc.equals(FormLogic.OTHER_PX))
			&& ((otherProc == null) || (otherProc.equals("")))) {
			retry("Please enter a value for Other Procedure");
			return false;
		}
		return true;
	}
	/**
	 * Insert all of the request parameters into the _params <code>HashMap</code>.
	 * Creation date: (10/18/2001 8:21:31 AM)
	 */
	private void loadParams() {
		Iterator i = _params.keySet().iterator();
		while (i.hasNext()) {
			String key = (String) i.next();
			_params.put(key, request.getParameter(key));
		}
	}
  
	/**
	 * Insert the method's description here.
	 * Creation date: (1/22/2001 3:49:33 PM)
	 */
	public void retry(String myError) throws Exception {
		Iterator attrs = _params.entrySet().iterator();
		while (attrs.hasNext()) {
			Map.Entry avPair = (Map.Entry) attrs.next();
			request.setAttribute((String) avPair.getKey(), avPair.getValue());
		}
		request.setAttribute("myError", myError);

		request.setAttribute("procedure", _params.get("procedure"));
		request.setAttribute("employee", _params.get("employeeDrop"));
        request.setAttribute("accSurgRemoval", _params.get("accSurgRemoval"));

		if (_params.get("grossAmpm") != null) {
			if (((String) _params.get("grossAmpm")).equals("pm")) {
				request.setAttribute("grossAmpm", "1");
			} else {
				request.setAttribute("grossAmpm", "0");
			}
		}
		if (_params.get("removeAmpm") != null) {
			if (((String) _params.get("removeAmpm")).equals("pm")) {
				request.setAttribute("removeAmpm", "1");
			} else {
				request.setAttribute("removeAmpm", "0");
			}
		}

		ASMFormLookup lookup = new ASMFormLookup(request, response, servletCtx);
		request.setAttribute("staffNames", lookup.getStaffNames());
		String pulled = lookup.getPulled((String) _params.get("consentID"));
		if (pulled != null) {
			request.setAttribute("pulled", pulled);
		}

		AsmformAccessBean asmForm = new AsmformAccessBean();
		String asmFormId = (String) _params.get("asmFormID");
		AccessBeanEnumeration asmFormEnum =
			(AccessBeanEnumeration) asmForm.findByASMFormID(asmFormId);
		if (!asmFormEnum.hasMoreElements()) {
			request.setAttribute("myError", "ASM Form not found in system.");
		} else {
			asmForm = (AsmformAccessBean) asmFormEnum.nextElement();
			Vector labels = lookup.getLabels(asmForm, asmFormId);
			for (int i = 0; i < labels.size(); i++) {
				request.setAttribute("label" + (i + 1), labels.get(i));
			}
		}

		servletCtx
			.getRequestDispatcher("/hiddenJsps/iltds/asm/asmFormInfo.jsp")
			.forward(request, response);
		return;
	}
}