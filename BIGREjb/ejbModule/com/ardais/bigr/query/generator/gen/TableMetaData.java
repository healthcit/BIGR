
package com.ardais.bigr.query.generator.gen;

import java.sql.Types;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;

/**
 * Holds metadata about a single table, where a table is defined as a 
 * fragment of a SQL FROM clause. <code>TableMetaData</code> can return a 
 * SQL FROM clause fragment that is appropriate for inclusion in a generated 
 * query.  Also holds constants that can be used as keys into data structures
 * that contain <code>TableMetaData</code> objects.
 * <p>
 * <b>Developer's Notes</b>
 * </p>
 * <p>
 * This is a generated file. DO NOT MANUALLY EDIT THIS FILE, AS YOUR CHANGES WILL
 * BE LOST THE NEXT TIME THIS FILE IS GENERATED.  To make changes, modify the 
 * xml file holding the Bigr Library information
 *
 * Generated using version 1.0 of the BigrLibraryMetaData xml file.
 *
 * @see ColumnMetaData
 * @see FilterMetaData
 * @see ProductQueryMetaData
 * @see com.ardais.bigr.util.gen.DbAliases
 * </p>
 */

public class TableMetaData {

    //------------------------------------------------------------
    // Table keys
    //------------------------------------------------------------
    public final static String KEY_TABLE_ASM = "002";
    public final static String KEY_TABLE_ASM_FORM = "012";
    public final static String KEY_TABLE_CONSENT = "003";
    public final static String KEY_TABLE_CONSENT_REVOKED = "009";
    public final static String KEY_TABLE_DONOR = "005";
    public final static String KEY_TABLE_RNA_HOLD_AMOUNT = "031";
    public final static String KEY_TABLE_RNA_GROSS_EXTRACTION = "036";
    public final static String KEY_TABLE_LIMS_PE = "004";
    public final static String KEY_TABLE_PATH = "006";
    public final static String KEY_TABLE_RNA_BATCH_DETAIL = "010";
    public final static String KEY_TABLE_TMA_BLOCK_SAMPLES = "047";
    public final static String KEY_TABLE_RNA_POOL_TISSUE = "011";
    public final static String KEY_TABLE_SAMPLE = "001";
    public final static String KEY_TABLE_SAMPLE1 = "050";
    public final static String KEY_TABLE_SECTION = "007";
    public final static String KEY_TABLE_IMAGES = "032";
    public final static String KEY_TABLE_SLIDE = "033";
    public final static String KEY_TABLE_PTS_PROJECT = "040";
    public final static String KEY_TABLE_PTS_SAMPLE = "039";
    public final static String KEY_TABLE_SHOPPING_CART = "051";
    public final static String KEY_TABLE_SHOPPING_CART_DETAIL = "041";
    public final static String KEY_TABLE_ORDER = "042";
    public final static String KEY_TABLE_ORDER_LINE = "043";
    public final static String KEY_TABLE_RNA_PROJECT = "044";
    public final static String KEY_TABLE_RNA_LIST = "045";
    public final static String KEY_TABLE_RNA_BIOANALYSER = "046";
    public final static String KEY_TABLE_ARDAIS_USER = "048";
    public final static String KEY_TABLE_LOGICAL_REPOS = "052";
    public final static String KEY_TABLE_LOGICAL_REPOS_ITEM = "053";
    public final static String KEY_TABLE_LOGICAL_REPOS_USER = "054";
    public final static String KEY_TABLE_BOX_LOCATION = "055";
    public final static String KEY_TABLE_ARDAIS_STAFF = "56";
    public final static String KEY_TABLE_PATH_REPORT_DIAGNOSTICS = "057";
    public final static String KEY_TABLE_ARD_POLICY = "058";
    public final static String KEY_TABLE_IRB = "060";
    public final static String KEY_TABLE_BOX_LAYOUT = "065";
    public final static String KEY_TABLE_ACCOUNT_BOX_LAYOUT = "070";
    public final static String KEY_TABLE_ARDAIS_ACCOUNT = "075";
    public final static String KEY_TABLE_ILTDS_SHIPPING_PARTNERS = "080";
    public final static String KEY_TABLE_ILTDS_GEOGRAPHY_LOCATION = "085";
    public final static String KEY_TABLE_ILTDS_SAMPLE_BOX = "086";
    public final static String KEY_TABLE_BIGR_ROLE = "087";
    public final static String KEY_TABLE_BIGR_ROLE_PRIVILEGE = "088";
    public final static String KEY_TABLE_BIGR_ROLE_USER = "089";

    /**
     * The data type of the input parameters.
     */
    private int _dataType = Types.VARCHAR;

    /**
     * The FROM clause fragment.
     */
    private String _fromFragment;

    /**
     * The inline view.
     */
    private String _inlineView;

    /**
     * The number of parameters for the inline view.
     */
    private int _numberParameters = 0;

    /**
     * The table alias.  This may be the same as the actual table name.
     */
    private String _tableAlias;

    /**
     * The actual table name.
     */
    private String _tableName;


    /**
     * Creates a new <code>TableMetaData</code>.
     */
    public TableMetaData() {
    }

    /**
     * Creates a new <code>TableMetaData</code>, from the given table name
     * and alias.
     * 
     * @param  tableName  the actual name of the database table
     * @param  tableAlias  the database table's alias 
     */
    public TableMetaData(String tableName, String tableAlias) {
    	_tableName = tableName;
    	_tableAlias = tableAlias;
    	buildFromFragment();
    }

    /**
     * Returns the data type of the parameters in this table.
     * 
     * @return  The parameter's data type.
     */
    public int getDataType() {
    	return _dataType;
    }

    /**
     * Returns the FROM fragment corresponding to this table.
     * 
     * @return  The FROM fragment.
     */
    public String getFromFragment() {
    	if (_fromFragment == null) {
    		buildFromFragment();
    	}
    	return _fromFragment;
    }

    /**
     * Returns the inline view.
     * 
     * @return  The inline view, with ? placeholders if they were
     * 					 present when the inline view was set.
     */
    public String getInlineView() {
    	return _inlineView;
    }

    /**
     * Returns the number of parameters.  This is only meaningful if an inline
     * view with one or more placeholders was specified.
     * 
     * @return  The number of parameters.
     */
    public int getNumberParameters() {
    	return _numberParameters;
    }

    /**
     * Returns the table alias.
     * 
     * @return  The table alias.
     */
    public String getTableAlias() {
    	return _tableAlias;
    }

    /**
     * Returns the actual table name.
     * 
     * @return  The actual table name.
     */
    public String getTableName() {
    	return _tableName;
    }

    /**
     * Sets the data type of the parameters in this table.  If not
     * set, the parameter defaults to <code>Types.VARCHAR</code>.
     * 
     * @param  dataType  the parameter's data type; must be one of
     *  <code>java.sql.Types</code>
     */
    public void setDataType(int dataType) {
    	_dataType = dataType;
    }

    /**
     * Sets the inline view.  The inline view parameter must contain () around
     * it.
     * 
     * @param inlineView  the inline view
     */
    public void setInlineView(String inlineView) {
    	_inlineView = inlineView;
    	_numberParameters = ApiFunctions.occurrencesOf('?', inlineView);
    	_fromFragment = null;
    }

    /**
     * Sets the table alias.
     * 
     * @param tableAlias  the table alias
     */
    public void setTableAlias(String tableAlias) {
    	_tableAlias = tableAlias;
    	_fromFragment = null;
    }

    /**
     * Sets the actual name of the table.
     * 
     * @param  tableName  The actual name of the table.
     */
    public void setTableName(String tableName) {
    	_tableName = tableName;
    	_fromFragment = null;
    }

    private void buildFromFragment() {
    	if (getInlineView() != null) {
    		if (getTableAlias() == null) {
    			throw new ApiException("In TableMetaData.buildFromFragment: inline view specified without an alias");
    		}
    		else {
    			_fromFragment = getInlineView() + " " + getTableAlias();
    		}
    	}
    	else {
    		if (getTableName() == null) {
    			throw new ApiException("In TableMetaData.buildFromFragment: table name not specified");
    		}
    		else {
    			if (getTableAlias().equals(getTableName())) {
    				_fromFragment = getTableName();
    			}
    			else {
    				_fromFragment = getTableName() + " " + getTableAlias();
    			}
    		}
    	}
    }

}
