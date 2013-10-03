package com.ardais.bigr.iltds.op;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.beans.SampleboxAccessBean;
import com.ardais.bigr.iltds.beans.SampleboxKey;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.gulfstreambio.gboss.GbossFactory;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

public class ReceiptExpectedSamples extends StandardOperation {

  public ReceiptExpectedSamples(
    HttpServletRequest req,
    HttpServletResponse res,
    ServletContext ctx) {
    super(req, res, ctx);
  }

  public void invoke() throws Exception {
    // Get boxID, retreive samples and display in the page in a table.

    String boxID = request.getParameter("boxID");
    SampleboxAccessBean sampleBox = null;

    try {
      sampleBox = new SampleboxAccessBean(new SampleboxKey(boxID));
    }
    catch (javax.ejb.ObjectNotFoundException e) {
      retry("Box ID " + boxID + " not found in system.");
    }
    
    BoxLayoutDto boxLayoutDto = BoxLayoutUtils.getBoxLayoutDto(sampleBox.getBoxLayoutId());
    request.setAttribute("boxLayoutDto", boxLayoutDto);
    
    List pulledSampleList = new ArrayList();

    SampleAccessBean sample = new SampleAccessBean();
    AccessBeanEnumeration sampleEnum =
      (AccessBeanEnumeration) sample.findSampleBySamplebox((SampleboxKey) sampleBox.__getKey());

    while (sampleEnum.hasMoreElements()) {
      SampleAccessBean smpl = (SampleAccessBean) sampleEnum.nextElement();
      String sampleId = ((SampleKey) smpl.__getKey()).sample_barcode_id;
      String sampleType = GbossFactory.getInstance().getDescription(smpl.getSampleTypeCid());
      String sampleAlias = smpl.getCustomerId();
      //check to see if the sample is revoked.
      if (IltdsUtils.sampleCasePulledOrRevoked(sampleId)) {
        request.setAttribute("samplePulled" + smpl.getCell_ref_location(), "true");
        pulledSampleList.add(sampleId);
      }
      request.setAttribute("sample" + smpl.getCell_ref_location(), sampleId);
      request.setAttribute("sampleType" + smpl.getCell_ref_location(), sampleType);
      request.setAttribute("sampleAlias" + smpl.getCell_ref_location(), sampleAlias);
    }
    request.setAttribute("pulledSampleList", pulledSampleList);

    servletCtx.getRequestDispatcher(
      "/hiddenJsps/iltds/receipt/receiptExpectedSamples.jsp").forward(
      request,
      response);
  }

  private void retry(String myError) throws Exception {
    // FIX ME!!!
    super.retry(myError, "/hiddenJsps/iltds/receipt/receiptScanStore.jsp");
  }
}
