package com.ardais.bigr.library.web.form;

import java.io.FilterOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.generator.FilterTissueFindingLike;
import com.ardais.bigr.query.generator.FilterTissueFindingNotLike;
import com.ardais.bigr.query.generator.FilterTissueOfFinding;
import com.ardais.bigr.query.generator.FilterTissueOfFindingNot;
import com.ardais.bigr.query.generator.FilterTissueOfOrigin;
import com.ardais.bigr.query.generator.FilterTissueOfOriginNot;
import com.ardais.bigr.query.generator.FilterTissueOriginLike;
import com.ardais.bigr.query.generator.FilterTissueOriginNotLike;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * This form holds data from the Tissue tab of the query form.  It also holds session data
 * for tissue and diagnosis heirarchy that is scraped out of the session by this objects 
 * doValidate() method.
 * 
 * 
 */
public class QueryTissueForm extends BigrActionForm {

    private String[] _tissueOrigin;
    private String[] _tissueOriginLabel;  // user readable prompts for tissue codes
    private boolean _tissueOriginNot;
    private String _tissueOriginContains;
    private String[] _tissueFinding;
    private String[] _tissueFindingLabel;  // user readable prompts for tissue codes
    private boolean _tissueFindingNot;
    private String _tissueFindingContains;

    public QueryTissueForm() {
        reset();
    }

    /**
     * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
     */
    protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
        reset();
    }
    protected void reset() {
        _tissueOrigin = null;
        _tissueOriginLabel = null;
        _tissueOriginNot = false;
        _tissueOriginContains = null;
        _tissueFinding = null;
        _tissueFindingLabel = null;
        _tissueFindingNot = false;
        _tissueFindingContains = null;
    }

    /**
     * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
     */
    public ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
        // a bit of a hack.  This is not validation, per se, but finishes the job of 
        // copying user input data into the Form.  Some data was stuffed into the session
        // by the DxTcHierarcy Actions, so put in on this form.
        
        String txType = request.getParameter("txType");
        updateFromSession(request.getSession(false), txType);
        
        return null;
    }

    /**
     * Returns the tissueFinding.
     * @return String[]
     */
    public String[] getTissueFinding() {
        return _tissueFinding;
    }

    /**
     * Returns the tissueFindingContains.
     * @return String
     */
    public String getTissueFindingContains() {
        return _tissueFindingContains;
    }

    /**
     * Returns the tissueFindingNot.
     * @return String
     */
    public boolean getTissueFindingNot() {
        return _tissueFindingNot;
    }

    /**
     * Returns the tissueOrigin.
     * @return String[]
     */
    public String[] getTissueOrigin() {
        return _tissueOrigin;
    }

    /**
     * Returns the tissueOriginContains.
     * @return String
     */
    public String getTissueOriginContains() {
        return _tissueOriginContains;
    }

    /**
     * Returns the tissueOriginNot.
     * @return String
     */
    public boolean getTissueOriginNot() {
        return _tissueOriginNot;
    }


    /**
     * Sets the tissueFinding.
     * @param tissueFinding The tissueFinding to set
     */
    public void setTissueFinding(String[] tissueFinding) {
        _tissueFinding = tissueFinding;
    }

    /**
     * Sets the tissueFindingContains.
     * @param tissueFindingContains The tissueFindingContains to set
     */
    public void setTissueFindingContains(String tissueFindingContains) {
        _tissueFindingContains = tissueFindingContains;
    }

    /**
     * Sets the tissueFindingNot.
     * @param tissueFindingNot The tissueFindingNot to set
     */
    public void setTissueFindingNot(boolean tissueFindingNot) {
        _tissueFindingNot = tissueFindingNot;
    }

    /**
     * Sets the tissueOrigin.
     * @param tissueOrigin The tissueOrigin to set
     */
    public void setTissueOrigin(String[] tissueOrigin) {
        _tissueOrigin = tissueOrigin;
    }

    /**
     * Sets the tissueOriginContains.
     * @param tissueOriginContains The tissueOriginContains to set
     */
    public void setTissueOriginContains(String tissueOriginContains) {
        _tissueOriginContains = tissueOriginContains;
    }

    /**
     * Sets the tissueOriginNot.
     * @param tissueOriginNot The tissueOriginNot to set
     */
    public void setTissueOriginNot(boolean tissueOriginNot) {
        _tissueOriginNot = tissueOriginNot;
    }

    /**
     * Returns the filters.
     * @return Map of filters indicated by this page/form.
     */
    public Map getFilters() { 
        Map results = new HashMap();

        // All four data areas are similar.  For each, check the not flag and add terms array and pattern
        
        if (getTissueFindingNot()) {
            Filter.addStringsEqualToMap(new FilterTissueOfFindingNot(getTissueFinding(), getTissueFindingLabel()), results);
            Filter.addStringLikeToMap(new FilterTissueFindingNotLike(getTissueFindingContains()), results);
        } else {
            Filter.addStringsEqualToMap(new FilterTissueOfFinding(getTissueFinding(), getTissueFindingLabel()), results);
            Filter.addStringLikeToMap(new FilterTissueFindingLike(getTissueFindingContains()), results);
        }
            
        if (getTissueOriginNot()) {
            Filter.addStringsEqualToMap(new FilterTissueOfOriginNot(getTissueOrigin(), getTissueOriginLabel()), results);
            Filter.addStringLikeToMap(new FilterTissueOriginNotLike(getTissueOriginContains()), results);
        } else {
            Filter.addStringsEqualToMap(new FilterTissueOfOrigin(getTissueOrigin(), getTissueOriginLabel()), results);
            Filter.addStringLikeToMap(new FilterTissueOriginLike(getTissueOriginContains()), results);
        }
        
        return results; 
        
    }

    /**
     * populate the attributes of the form from the Filters in the Map.
     */
    public void setFilters(Map m) {
        // four finding (all combinations of not and like)
        String tissuefindingnot = FilterConstants.KEY_TISSUEFINDINGNOT;
        String tissuefindingnotlike = FilterConstants.KEY_TISSUEFINDINGLIKENOT;
        String tissuefinding = FilterConstants.KEY_TISSUEFINDING;
        String tissuefindinglike = FilterConstants.KEY_TISSUEFINDINGLIKE;
        // four origin
        String tissueoriginnot = FilterConstants.KEY_TISSUEORIGINNOT;
        String tissueoriginnotlike = FilterConstants.KEY_TISSUEORIGINLIKENOT;
        String tissueorigin = FilterConstants.KEY_TISSUEORIGIN;
        String tissueoriginlike = FilterConstants.KEY_TISSUEORIGINLIKE;
        
        // finding not (array of values and pattern)
        if (m.containsKey(tissuefindingnot)) {
            FilterTissueOfFindingNot f = (FilterTissueOfFindingNot) m.get(tissuefindingnot);
            setTissueFindingNot(true);
            setTissueFinding(f.getFilterValues());
            setTissueFindingLabel(f.getDescriptions());
        }
        if (m.containsKey(tissuefindingnotlike)) {
            FilterTissueFindingNotLike f = (FilterTissueFindingNotLike) m.get(tissuefindingnotlike);
            setTissueFindingNot(true);
            setTissueFindingContains(f.getPatternForDisplay());
        }
        // finding  (array of values and pattern)
        if (m.containsKey(tissuefinding)) {
            FilterTissueOfFinding f = (FilterTissueOfFinding) m.get(tissuefinding);
            setTissueFindingNot(false);  // double negative.  means positive case
            setTissueFinding(f.getFilterValues());
            setTissueFindingLabel(f.getDescriptions());
        }
        if (m.containsKey(tissuefindinglike)) {
            FilterTissueFindingLike f = (FilterTissueFindingLike) m.get(tissuefindinglike);
            setTissueFindingNot(false);
            setTissueFindingContains(f.getPatternForDisplay());
        }

        // origin not (array of values and pattern)
        if (m.containsKey(tissueoriginnot)) {
            FilterTissueOfOriginNot f = (FilterTissueOfOriginNot) m.get(tissueoriginnot);
            setTissueOriginNot(true);
            setTissueOrigin(f.getFilterValues());
            setTissueOriginLabel(f.getDescriptions());
        }
        if (m.containsKey(tissueoriginnotlike)) {
            FilterTissueOriginNotLike f = (FilterTissueOriginNotLike) m.get(tissueoriginnotlike);
            setTissueOriginNot(true);
            setTissueOriginContains(f.getPatternForDisplay());
        }
        // origin  (array of values and pattern)
        if (m.containsKey(tissueorigin)) {
            FilterTissueOfOrigin f = (FilterTissueOfOrigin) m.get(tissueorigin);
            setTissueOriginNot(false);  // double negative.  means positive case
            setTissueOrigin(f.getFilterValues());
            setTissueOriginLabel(f.getDescriptions());
        }
        if (m.containsKey(tissueoriginlike)) {
            FilterTissueOriginLike f = (FilterTissueOriginLike) m.get(tissueoriginlike);
            setTissueOriginNot(false);
            setTissueOriginContains(f.getPatternForDisplay());
        }

    }
    
    public void updateFromSession(HttpSession session, String txType) {

        Hashtable tissueList = (Hashtable) session.getAttribute("libraryTissueOrigin.selected."+txType);
        if (tissueList != null) {
            String[] str = (String[]) (tissueList.keySet().toArray(new String[0]));
            setTissueOrigin(str);
        }

        tissueList = (Hashtable) session.getAttribute("libraryTissueFinding.selected."+txType);
        if (tissueList != null) {
            String[] str = (String[]) (tissueList.keySet().toArray(new String[0]));
            setTissueFinding(str);
        }
    
        List tissueLabelList = (List) session.getAttribute("libraryTissueOrigin.label."+txType);
        if (tissueLabelList != null) {
            String[] str = (String[]) (tissueLabelList.toArray(new String[0]));
            setTissueOriginLabel(str);
        }

        tissueLabelList = (List) session.getAttribute("libraryTissueFinding.label."+txType);
        if (tissueLabelList != null) {
            String[] str = (String[]) (tissueLabelList.toArray(new String[0]));
            setTissueFindingLabel(str);
        }
    }

    /**
     * Returns the tissueFindingLabels.
     * @return String[]
     */
    public String[] getTissueFindingLabel() {
        if (_tissueFindingLabel==null)
            _tissueFindingLabel = new String[0];
        return _tissueFindingLabel;
    }

    /**
     * Returns the tissueOriginLabels.
     * @return String[]
     */
    public String[] getTissueOriginLabel() {
        if (_tissueOriginLabel==null)
            _tissueOriginLabel = new String[0];
        return _tissueOriginLabel;
    }

    /**
     * Sets the tissueFindingLabels.
     * @param tissueFindingLabels The tissueFindingLabels to set
     */
    public void setTissueFindingLabel(String[] tissueFindingLabel) {
        _tissueFindingLabel = tissueFindingLabel;
    }

    /**
     * Sets the tissueOriginLabels.
     * @param tissueOriginLabels The tissueOriginLabels to set
     */
    public void setTissueOriginLabel(String[] tissueOriginLabel) {
        _tissueOriginLabel = tissueOriginLabel;
    }

}
