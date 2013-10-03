package com.ardais.bigr.library.web.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.library.web.helper.LibraryValuesHelper;
import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.generator.FilterCompositionAcellularStroma;
import com.ardais.bigr.query.generator.FilterCompositionCellularStroma;
import com.ardais.bigr.query.generator.FilterCompositionLesion;
import com.ardais.bigr.query.generator.FilterCompositionNecrosis;
import com.ardais.bigr.query.generator.FilterCompositionNormal;
import com.ardais.bigr.query.generator.FilterCompositionTumor;
import com.ardais.bigr.query.generator.FilterSampleAppearanceBest;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class QueryAppearanceForm extends BigrActionForm {

    private String _normalFrom;
    private String _normalTo;
    private String _necrosisFrom;
    private String _necrosisTo;
    private String _lesionFrom;
    private String _lesionTo;
    private String _tumorFrom;
    private String _tumorTo;
    private String _cellularStromaFrom;
    private String _cellularStromaTo;
    private String _acellularStromaFrom;
    private String _acellularStromaTo;
    
    private String[] _appearanceBest;
    
    public QueryAppearanceForm() {
      reset();
    }
   
    /**
     * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
     */
    protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
        reset();
    }

    private void reset() {
        _normalFrom = null;
        _normalTo = null;
        _necrosisFrom = null;
        _necrosisTo = null;
        _lesionFrom = null;
        _lesionTo = null;
        _tumorFrom = null;
        _tumorTo = null;
        _cellularStromaFrom = null;
        _cellularStromaTo = null;
        _acellularStromaFrom = null;
        _acellularStromaTo = null;
        _appearanceBest = new String[] {Constants.ANY};
    }
    
    /**
     * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
     */
    public ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
        return null;
    }

    private boolean isEmptyOrAny(String[] s) {
        return ApiFunctions.isEmpty(s) || (s != null && s.length == 1 && Constants.ANY.equals(s[0]));
    }
    
    /**
     * Returns the compLsnFrom.
     * @return String
     */
    public String getCompLsnFrom() {
        return _lesionFrom;
    }

    /**
     * Returns the compLsnTo.
     * @return String
     */
    public String getCompLsnTo() {
        return _lesionTo;
    }

    /**
     * Returns the compNecFrom.
     * @return String
     */
    public String getCompNecFrom() {
        return _necrosisFrom;
    }

    /**
     * Returns the compNecTo.
     * @return String
     */
    public String getCompNecTo() {
        return _necrosisTo;
    }

    /**
     * Returns the compNrmFrom.
     * @return String
     */
    public String getCompNrmFrom() {
        return _normalFrom;
    }

    /**
     * Returns the compNrmTo.
     * @return String
     */
    public String getCompNrmTo() {
        return _normalTo;
    }

    /**
     * Returns the compTasFrom.
     * @return String
     */
    public String getCompTasFrom() {
        return _acellularStromaFrom;
    }

    /**
     * Returns the compTasTo.
     * @return String
     */
    public String getCompTasTo() {
        return _acellularStromaTo;
    }

    /**
     * Returns the compTcsFrom.
     * @return String
     */
    public String getCompTcsFrom() {
        return _cellularStromaFrom;
    }

    /**
     * Returns the compTcsTo.
     * @return String
     */
    public String getCompTcsTo() {
        return _cellularStromaTo;
    }

    /**
     * Returns the compTmrFrom.
     * @return String
     */
    public String getCompTmrFrom() {
        return _tumorFrom;
    }

    /**
     * Returns the compTmrTo.
     * @return String
     */
    public String getCompTmrTo() {
        return _tumorTo;
    }

    /**
     * Sets the compLsnFrom.
     * @param compLsnFrom The compLsnFrom to set
     */
    public void setCompLsnFrom(String compLsnFrom) {
        _lesionFrom = compLsnFrom;
    }

    /**
     * Sets the compLsnTo.
     * @param compLsnTo The compLsnTo to set
     */
    public void setCompLsnTo(String compLsnTo) {
        _lesionTo = compLsnTo;
    }

    /**
     * Sets the compNecFrom.
     * @param compNecFrom The compNecFrom to set
     */
    public void setCompNecFrom(String compNecFrom) {
        _necrosisFrom = compNecFrom;
    }

    /**
     * Sets the compNecTo.
     * @param compNecTo The compNecTo to set
     */
    public void setCompNecTo(String compNecTo) {
        _necrosisTo = compNecTo;
    }

    /**
     * Sets the compNrmFrom.
     * @param compNrmFrom The compNrmFrom to set
     */
    public void setCompNrmFrom(String compNrmFrom) {
        _normalFrom = compNrmFrom;
    }

    /**
     * Sets the compNrmTo.
     * @param compNrmTo The compNrmTo to set
     */
    public void setCompNrmTo(String compNrmTo) {
        _normalTo = compNrmTo;
    }

    /**
     * Sets the compTasFrom.
     * @param compTasFrom The compTasFrom to set
     */
    public void setCompTasFrom(String compTasFrom) {
        _acellularStromaFrom = compTasFrom;
    }

    /**
     * Sets the compTasTo.
     * @param compTasTo The compTasTo to set
     */
    public void setCompTasTo(String compTasTo) {
        _acellularStromaTo = compTasTo;
    }

    /**
     * Sets the compTcsFrom.
     * @param compTcsFrom The compTcsFrom to set
     */
    public void setCompTcsFrom(String compTcsFrom) {
        _cellularStromaFrom = compTcsFrom;
    }

    /**
     * Sets the compTcsTo.
     * @param compTcsTo The compTcsTo to set
     */
    public void setCompTcsTo(String compTcsTo) {
        _cellularStromaTo = compTcsTo;
    }

    /**
     * Sets the compTmrFrom.
     * @param compTmrFrom The compTmrFrom to set
     */
    public void setCompTmrFrom(String compTmrFrom) {
        _tumorFrom = compTmrFrom;
    }

    /**
     * Sets the compTmrTo.
     * @param compTmrTo The compTmrTo to set
     */
    public void setCompTmrTo(String compTmrTo) {
        _tumorTo = compTmrTo;
    }

    /**
     * Returns the Appearance list the user selected, which differs slightly from the
     * appearance list we will submit to the query engine.
     * @return String
     */
    public String[] getAppearanceBest() {
        return _appearanceBest;
    }

    /**
     * Sets the appearance.
     * @param appearanceBest The appearance to set
     */
    public void setAppearanceBest(String[] appearanceBest) {
      _appearanceBest = appearanceBest;
    }
    
    public Map getFilters() { 
        Map results = new HashMap();
        Filter.addPercentRangeToMap(new FilterCompositionNormal(getCompNrmFrom(), getCompNrmTo()), results);
        Filter.addPercentRangeToMap(new FilterCompositionLesion(getCompLsnFrom(), getCompLsnTo()), results);
        Filter.addPercentRangeToMap(new FilterCompositionNecrosis(getCompNecFrom(), getCompNecTo()), results);
        Filter.addPercentRangeToMap(new FilterCompositionCellularStroma(getCompTcsFrom(), getCompTcsTo()), results);
        Filter.addPercentRangeToMap(new FilterCompositionAcellularStroma(getCompTasFrom(), getCompTasTo()), results);
        Filter.addPercentRangeToMap(new FilterCompositionTumor(getCompTmrFrom(), getCompTmrTo()), results);

        if (!Filter.isEmpty(getAppearanceBest())) {
            Filter appearance = new FilterSampleAppearanceBest(getAppearanceBest());
            results.put(appearance.getKey(), appearance);
        }
        
        return results;
    }    

	/**
	 * Returns the valueList.
	 * @return String[]
	 */
	public String[] getValueList() {
		return LibraryValuesHelper.getAppearanceValueList();
	}

    /**
     * Method setFilters.
     * @param filters
     */
    public void setFilters(Map m) {
        String compnormal = FilterConstants.KEY_COMP_NORMAL;
        String complesion = FilterConstants.KEY_COMP_LESION;
        String compnecrosis = FilterConstants.KEY_COMP_NECROSIS;
        String comptcs = FilterConstants.KEY_COMP_CS;
        String compacs = FilterConstants.KEY_COMP_ACS;
        String comptumor = FilterConstants.KEY_COMP_TUMOR;
        String appearBest = FilterConstants.KEY_SAMPLE_APPEARANCE_BEST;
        if (m.containsKey(compnormal)) {
            FilterCompositionNormal f = (FilterCompositionNormal) m.get(compnormal);
            setCompNrmFrom(str(f.getMin()));
            setCompNrmTo(str(f.getMax()));
        }
        if (m.containsKey(complesion)) {
            FilterCompositionLesion f = (FilterCompositionLesion) m.get(complesion);
            setCompLsnFrom(str(f.getMin()));
            setCompLsnTo(str(f.getMax()));
        }
        if (m.containsKey(compnecrosis)) {
            FilterCompositionNecrosis f = (FilterCompositionNecrosis) m.get(compnecrosis);
            setCompNecFrom(str(f.getMin()));
            setCompNecTo(str(f.getMax()));
        }
        if (m.containsKey(comptcs)) {
            FilterCompositionCellularStroma f = (FilterCompositionCellularStroma) m.get(comptcs);
            setCompTcsFrom(str(f.getMin()));
            setCompTcsTo(str(f.getMax()));
        }
        if (m.containsKey(compacs)) {
            FilterCompositionAcellularStroma f = (FilterCompositionAcellularStroma) m.get(compacs);
            setCompTasFrom(str(f.getMin()));
            setCompTasTo(str(f.getMax()));
        }
        if (m.containsKey(comptumor)) {
            FilterCompositionTumor f = (FilterCompositionTumor) m.get(comptumor);
            setCompTmrFrom(str(f.getMin()));
            setCompTmrTo(str(f.getMax()));
        }
        if (m.containsKey(appearBest)) {
            FilterSampleAppearanceBest f = (FilterSampleAppearanceBest) m.get(appearBest);
            setAppearanceBest(f.getFilterValues());
        }
    }

    private String str(Integer i) {
        if (i==null)
            return "";
        else 
            return i.toString();
    }
}
