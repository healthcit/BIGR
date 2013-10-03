package com.ardais.bigr.library.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.gboss.GbossValueSet;

public class DxTcHierarchyForm extends BigrActionForm {
    private String[] _codes;
    private String _type;
    private String _txType;
    private String _id;
    private String _clear;
    private Integer _intI;
    private Integer _intJ;
    private String process;

    public DxTcHierarchyForm() {
        reset();
    }

    /**
     * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
     */
    protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
        reset();
    }

    private void reset() {
        _codes = null;
        _type = null;
        _id = null;
        _clear = null;
        _intI = null;
        _intJ = null;
        process = null;
    }

    /**
     * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
     */
    public ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {

        return null;
    }

    public GbossValueSet getDxHierarchy() {
      return BigrGbossData.getReceivedDiagnosisValueSet();
    }

    public GbossValueSet getTcHierarchy() {
      return BigrGbossData.getReceivedTissueValueSet();
    }
    
    /**
     * Returns the codes.
     * @return String[]
     */
    public String[] getCodes() {
        return _codes;
    }

    /**
     * Sets the codes.
     * @param codes The codes to set
     */
    public void setCodes(String[] codes) {
        _codes = codes;
    }

    /**
     * Returns the type.
     * @return String
     */
    public String getType() {
        return _type;
    }

    /**
     * Sets the type.
     * @param type The type to set
     */
    public void setType(String type) {
        _type = type;
    }

    /**
     * Returns the id.
     * @return String
     */
    public String getId() {
        return _id;
    }

    /**
     * Sets the id.
     * @param id The id to set
     */
    public void setId(String id) {
        _id = id;
    }

    /**
     * Returns the clear.
     * @return String
     */
    public String getClear() {
        return _clear;
    }

    /**
     * Sets the clear.
     * @param clear The clear to set
     */
    public void setClear(String clear) {
        _clear = clear;
    }

    /**
     * Returns the intI.
     * @return Integer
     */
    public Integer getIntI() {
        return _intI;
    }

    /**
     * Returns the intJ.
     * @return Integer
     */
    public Integer getIntJ() {
        return _intJ;
    }

    /**
     * Sets the intI.
     * @param intI The intI to set
     */
    public void setIntI(Integer intI) {
        _intI = intI;
    }

    /**
     * Sets the intJ.
     * @param intJ The intJ to set
     */
    public void setIntJ(Integer intJ) {
        _intJ = intJ;
    }

    /**
     * Returns the process.
     * @return String
     */
    public String getProcess() {
        return process;
    }

    /**
     * Sets the process.
     * @param process The process to set
     */
    public void setProcess(String process) {
        this.process = process;
    }

    /**
     * Returns the txType.
     * @return String
     */
    public String getTxType() {
        return _txType;
    }

    /**
     * Sets the txType.
     * @param txType The txType to set
     */
    public void setTxType(String txType) {
        _txType = txType;
    }

}
