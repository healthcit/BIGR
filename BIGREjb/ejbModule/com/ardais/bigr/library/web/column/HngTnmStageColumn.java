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
public class HngTnmStageColumn extends CompositeSampleColumn {

  /**
   * Constructor for CompositionValuesColumn.
   * @param description
   * @param cols
   */
  public HngTnmStageColumn(ViewParams cp) {
    super("HNG/TNM/Stage Grouping",
      new SampleColumn[] {
            new HngColumn(cp),
            new TnmColumn(cp),
            new StageGroupingColumn(cp),
          }
        );
    
  }

}
