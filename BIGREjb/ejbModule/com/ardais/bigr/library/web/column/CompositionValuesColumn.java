package com.ardais.bigr.library.web.column;

import com.ardais.bigr.query.ViewParams;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CompositionValuesColumn extends CompositeSampleColumn {

  /**
   * Constructor for CompositionValuesColumn.
   * @param description
   * @param cols
   */
  public CompositionValuesColumn(ViewParams cp) {
    super("Composition Values",
      new SampleColumn[] {
            new CompositionNormalColumn(cp),
            new CompositionLesionColumn(cp),
            new CompositionTumorColumn(cp),
            new CompositionCellStromaColumn(cp),
            new CompositionAcellStromaColumn(cp),
            new CompositionNecrosisColumn(cp)
          }
        );
    
  }

}
