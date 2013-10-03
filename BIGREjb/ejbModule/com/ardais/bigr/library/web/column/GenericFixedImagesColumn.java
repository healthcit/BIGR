package com.ardais.bigr.library.web.column;

import com.ardais.bigr.query.ViewParams;

/**
 * Exactly the same as GenericImagesColumn, but is tagged in ColumnConstants as fixed
 * meaning not moveable in the column configuration screen.
 */
public class GenericFixedImagesColumn extends ImagesColumn {

  public GenericFixedImagesColumn(ViewParams cp) {
    super(
      "library.ss.result.header.images.label",
      "library.ss.result.header.images.comment",
      37,
      cp);
  }

}
