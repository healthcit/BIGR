package com.ardais.bigr.library.performers;

import java.lang.reflect.Method;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.iltds.btx.BTXActionForward;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.javabeans.RnaData;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;

public class BtxPerformerRnaOperation extends BtxTransactionPerformerBase {

  public BtxPerformerRnaOperation() {
    super();
  }

  /**
   * Invoke the specified method on this class.  This is only meant to be
   * called from BtxTransactionPerformerBase, please don't call it from anywhere
   * else as a mechanism to gain access to private methods in this class.  Every
   * object that the BTX framework dispatches to must contain this
   * method definition, and its implementation should be exactly the same
   * in each class.  Please don't alter this method or its implementation
   * in any way.
   */
  protected BTXDetails invokeBtxEntryPoint(Method method, BTXDetails btxDetails) throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }

  private BTXDetails validatePerformGetRnaWithRepresentativeSample(BTXDetailsGetSamples btxDetails)
    throws Exception {
      String[] rnaVialIds = btxDetails.getSampleIds();

      if (rnaVialIds == null || rnaVialIds.length != 1 || rnaVialIds[0] == null) {
        btxDetails.addActionError(new BtxActionError("error.noValue", "unique RNA identifier"));
        return btxDetails;
      }
      return btxDetails;
  }


  /**
    * Get an {@link ProductDto} object with associated 
    * <code>RnaData and SampleData</code> objects.  The RnaData object is central
    * to this operation.  ProductDto is used as a wrapper to levarage
    * existing GUI code.
    * The <code>SampleData</code>
    * object will be representative of the RNA, which may be derived from it, or pooled
    * from RNA that was derived from it as well as other samples.
    * 
    * The <code>RnaData</code> object retreived will be based on fields (currently the 
    * rnaVialId) set on the RnaData object passed in as a parameter.
    */
  private BTXDetailsGetSamples performGetRnaWithRepresentativeSample(BTXDetailsGetSamples btx) {

    try {
      btx.setTransactionType("library_get_details");
      btx = (BTXDetailsGetSamples) Btx.perform(btx);
      List ssDatas = btx.getSampleDetailsResult();
      if ((ssDatas != null) && !ssDatas.isEmpty()) {
        ProductDto ssData = (ProductDto) ssDatas.get(0);
        RnaData rnaBean = ssData.getRnaData();
        rnaBean.loadImagesFromDB(); 
      }
      btx.setActionForward(new BTXActionForward("success"));
      return btx;
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
  }

}
