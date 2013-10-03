package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.generator.gen.ColumnMetaData;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ColumnCountMetaData extends ColumnMetaData{
    /**
     * Creates a new <code>ColumnCountMetaData</code> from the given table alias,
     * column name and column alias.
     * 
     * @param  tableAlias  the database table's alias 
     * @param  column  the actual column name
     * @param  columnAlias  the column's alias
     */
    public ColumnCountMetaData(String tableAlias, String column, String columnAlias) {
        super(tableAlias, column, columnAlias); 
        setSelectFragment("count( " + tableAlias + "." + column + " ) AS \"" + columnAlias + "\"");
    }

    /**
     * Creates a new <code>ColumnCountMetaData</code> from the given table alias and
     * column name.  The column will not be aliased.
     * 
     * @param  tableAlias  the database table's alias 
     * @param  column  the actual column name
     */
    ColumnCountMetaData(String tableAlias, String column) {
        this(tableAlias, column, column);
    }
}
