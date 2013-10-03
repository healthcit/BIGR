package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.query.ViewParams;

/**
 */
public class TissueImagesColumn extends ImagesColumn {

  public TissueImagesColumn(ViewParams cp) {
    super(
      "library.ss.result.header.images.label",
      "library.ss.result.header.images.tissue.comment",
      36,
      cp);
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getBodyText(SampleRowParams)
   * Hide any body text that is not for a Tissue sample 
   */
  protected String getBodyText(SampleRowParams rp) throws IOException {
    if(rp.getItemHelper().isRna())
      return "<td/>";
    else
      return super.getBodyText(rp);
  }

}
