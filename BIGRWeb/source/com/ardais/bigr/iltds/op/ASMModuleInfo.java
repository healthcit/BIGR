package com.ardais.bigr.iltds.op;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.beans.ASMOperation;
import com.ardais.bigr.iltds.beans.ASMOperationHome;
import com.ardais.bigr.iltds.databeans.AsmData;
import com.ardais.bigr.iltds.databeans.SampleData;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.icp.ejb.session.IcpOperation;
import com.ardais.bigr.iltds.icp.ejb.session.IcpOperationHome;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;

/**
 * Insert the type's description here.
 * Creation date: (1/17/2001 3:07:02 PM)
 * @author: Jake Thompson
 */
public class ASMModuleInfo extends com.ardais.bigr.iltds.op.StandardOperation {
	/**
	 * ASMInformation constructor comment.
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param res javax.servlet.http.HttpServletResponse
	 * @param ctx javax.servlet.ServletContext
	 */
	public ASMModuleInfo(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (1/17/2001 3:07:02 PM)
	 */
	public void invoke() throws IOException, Exception {
		try {
      
      SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);

			String module = request.getParameter("module");
      String asmFormID = module.substring(0, module.indexOf('_'));

			//depending on whether it is the 1st, 2nd, or 3rd module, remove sample from the vector

			Vector myFrozen = new Vector();
			Vector myParaffin = new Vector();
			if (!FormLogic.isConversion(asmFormID)) {
				myFrozen = FormLogic.genFrozenIDsFromASMID(module);
				myParaffin = FormLogic.genParaffinIDsFromASMID(module);
			}

			Vector checkBoxes = new Vector();

			//request.setAttribute("moduleLetter", module);

			//set the changed sample vectors
			request.setAttribute("hours", FormLogic.getHourIntVector());
			request.setAttribute("minutes", FormLogic.getMinuteIntVector());

			// create the fixative dropdown
			// changed to use ARTS for MR 4435
			LegalValueSet fixatives = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_TYPE_OF_FIXATIVE);
			request.setAttribute("fixatives", fixatives);

			// new fields that pass in ARTS codes for MR 4435 
			LegalValueSet sampleFormatDetail = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_SAMPLE_FORMAT_DETAIL);
			request.setAttribute("sampleFormat", sampleFormatDetail);
			
			LegalValueSet minThickness = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_PARAFFIN_DIMENSIONS);
			request.setAttribute("minThickness", minThickness);
			
			LegalValueSet maxThickness = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_PARAFFIN_DIMENSIONS);
			request.setAttribute("maxThickness", maxThickness);			
			
			LegalValueSet widthAcross = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_PARAFFIN_DIMENSIONS);
			request.setAttribute("widthAcross", widthAcross);

			LegalValueSet paraffinFeatureSet = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_PARAFFIN_FEATURE);
			request.setAttribute("paraffinFeatureSet", paraffinFeatureSet);

			AsmData asmInfo = new AsmData();
			asmInfo.setAsm_id(module);
      IcpOperationHome icpHome = (IcpOperationHome) EjbHomes.getHome(IcpOperationHome.class);
      IcpOperation icp = icpHome.create();
			asmInfo = (AsmData) icp.getAsmData(asmInfo, securityInfo, false, false);

			SampleData sampleData;

			for (int i = 0; i < myFrozen.size(); i++) {
				sampleData = new SampleData();
				sampleData.setSample_id((String) myFrozen.get(i));
				sampleData = icp.getSampleData(sampleData, securityInfo, false, false);

				if (sampleData.getAsm_id() == null) {
					sampleData.setAsm_id(module);
					sampleData.setAsm_position(
						FormLogic.getASMLocation(sampleData.getSample_id()));
					checkBoxes.add(sampleData.getSample_id());
					sampleData.setExists(false);
				} else {
					sampleData.setExists(true);
          //populate the status values this sample has had so we can determine
          //if it's eligible for deletion via the front end (MR6976)
          sampleData.setStatuses(FormLogic.getStatusValuesForSample(sampleData.getSample_id()));
				}
				asmInfo.addFrozen_sample(sampleData);
			}

			for (int i = 0; i < myParaffin.size(); i++) {
				sampleData = new SampleData();
				sampleData.setSample_id((String) myParaffin.get(i));
				sampleData = icp.getSampleData(sampleData, securityInfo, false, false);

				if (sampleData.getAsm_id() == null) {
					sampleData.setAsm_id(module);
					sampleData.setAsm_position(
						FormLogic.getASMLocation(sampleData.getSample_id()));
					sampleData.setExists(false);
					checkBoxes.add(sampleData.getSample_id());
				} else {
					sampleData.setExists(true);
          //populate the status values this sample has had so we can determine
          //if it's eligible for deletion via the front end (MR6976)
          sampleData.setStatuses(FormLogic.getStatusValuesForSample(sampleData.getSample_id()));
				}
				asmInfo.addParaffin_sample(sampleData);
			}

			for (int i = 0; i < myParaffin.size(); i++) {
				myFrozen.add(myParaffin.get(i));
			}

      ASMOperationHome asmHome = (ASMOperationHome) EjbHomes.getHome(ASMOperationHome.class);
      ASMOperation asmOp = asmHome.create();
			Vector otherSamples = asmOp.nonAsmFormSamples(module, myFrozen);

			for (int i = 0; i < otherSamples.size(); i++) {
				sampleData = new SampleData();
				sampleData.setSample_id((String) otherSamples.get(i));
				sampleData = icp.getSampleData(sampleData, securityInfo, false, false);

				if (sampleData.getAsm_id() == null) {
					sampleData.setAsm_id(module);
					sampleData.setAsm_position(
						FormLogic.getASMLocation(sampleData.getSample_id()));
					sampleData.setExists(false);
				} else {
					sampleData.setExists(true);
          //populate the status values this sample has had so we can determine
          //if it's eligible for deletion via the front end (MR6976)
          sampleData.setStatuses(FormLogic.getStatusValuesForSample(sampleData.getSample_id()));
				}

        // 2.3.05 this will be replaced with new constants...
        // note that we are not supporting SX samples here b/c
        // this code is only used for generating ASMs using 
        // traditional ardais process, not sample intake...
				if (sampleData.getSample_id().startsWith(ValidateIds.PREFIX_SAMPLE_FROZEN)) {
					asmInfo.addFrozen_sample(sampleData);
				} else if (sampleData.getSample_id().startsWith(ValidateIds.PREFIX_SAMPLE_PARAFFIN)) {
					asmInfo.addParaffin_sample(sampleData);
				}

			}

			//if this is a conversion module with no samples, return a message indicating that
			if (FormLogic.isConversion(asmFormID)
				&& asmInfo.getSamples().isEmpty() ) {
				request.setAttribute(
					"criticalError",
					"This conversion ASM module has no samples, and cannot be edited.");
			}

			request.getSession(false).setAttribute("checkboxes", checkBoxes);
			request.setAttribute("asmInfo", asmInfo);
			request.setAttribute("organ", asmInfo.getTissue_type());
			request.setAttribute("otherTissue", asmInfo.getOther_tissue());
			request.setAttribute("moduleLetter", asmInfo.getModule_letter());

			servletCtx
				.getRequestDispatcher("/hiddenJsps/iltds/asm/asmModuleInfo.jsp")
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
}
