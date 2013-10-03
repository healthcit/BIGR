package com.ardais.bigr.library.web.form;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.generator.FilterCaseDiagnosisBest;
import com.ardais.bigr.query.generator.FilterCaseDiagnosisDdc;
import com.ardais.bigr.query.generator.FilterCaseDiagnosisIltds;
import com.ardais.bigr.query.generator.FilterCaseDiagnosisLikeBest;
import com.ardais.bigr.query.generator.FilterCaseDiagnosisLikeDdc;
import com.ardais.bigr.query.generator.FilterCaseDiagnosisLikeIltds;
import com.ardais.bigr.query.generator.FilterHng;
import com.ardais.bigr.query.generator.FilterPathologyNotesContains;
import com.ardais.bigr.query.generator.FilterPathologyVerificationNotesContains;
import com.ardais.bigr.query.generator.FilterSamplePathology;
import com.ardais.bigr.query.generator.FilterSamplePathologyLike;
import com.ardais.bigr.query.generator.FilterStageGrouping;
import com.ardais.bigr.query.generator.FilterStageLymphNode;
import com.ardais.bigr.query.generator.FilterStageMetastasis;
import com.ardais.bigr.query.generator.FilterStageTumor;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * This form holds data from the Diagnosis tab of the query form.  It also holds session data
 * for diagnosis heirarchy that is scraped out of the session by this object's 
 * doValidate() method.
 * 
 * 
 */
public class QueryDiagnosisForm extends BigrActionForm {

    private static String[] ANY = new String[] {Constants.ANY};
    
    private String _diagnosisFrom; // best, ILTDS, or DDC
    private String[] _caseDiagnosis;
    private String[] _caseDiagnosisLabel;
    private String _caseDiagnosisContains; // user readable prompts for case dx
    private String[] _samplePathology;
    private String[] _samplePathologyLabel; // user readable prompts for lims dx
    private String _samplePathologyContains;
    private String[] _stage;
    private String[] _tumorStage;
    private String[] _lymphNodeStage;
    private String[] _distantMetastasis;
    private String[] _hng;
    private String _pathNotesContains;
    private String _pvNotesContains;

    public QueryDiagnosisForm() {
        reset();
    }

    /**
     * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
     */
    protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
        reset();
    }

    private void reset() {
        _diagnosisFrom = Constants.BEST;
        _caseDiagnosis = null;
        _caseDiagnosisLabel = null;
        _caseDiagnosisContains = null;
        _samplePathology = null;
        _samplePathologyLabel = null;
        _samplePathologyContains = null;
        _stage = ANY;
        _tumorStage = ANY;
        _lymphNodeStage = ANY;
        _distantMetastasis = ANY;
        _hng = ANY;
        _pathNotesContains = null;
        _pvNotesContains = null;
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

    private boolean isEmptyOrAny(String[] s) {
        return ApiFunctions.isEmpty(s) || (s != null && s.length == 1 && Constants.ANY.equals(s[0]));
    }

    /**
     * Returns the caseDiagnosis.
     * @return String[]
     * @todo Set this in the action from what is set in the session
     */
    public String[] getCaseDiagnosis() {
        return _caseDiagnosis;
    }

    /**
     * Returns the caseDiagnosisContains.
     * @return String
     */
    public String getCaseDiagnosisContains() {
        return _caseDiagnosisContains;
    }

    /**
     * Returns the distantMetastasis.
     * @return String
     */
    public String[] getDistantMetastasis() {
        return _distantMetastasis;
    }

    /**
     * Returns the list of possible values for lymph node stage.
     *
     * @return  A {@link com.ardais.bigr.iltds.assistants.List} of 
     * 					 lymph node stage values.
     */
    public Iterator getDistantMetastasisList() {
        return BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_DISTANT_METASTASIS).getValues().iterator();
    }

    /**
     * Returns the hng.
     * @return String
     */
    public String[] getHng() {
        return _hng;
    }

    /**
     * Returns the list of possible values for HNG.
     *
     * @return  List
     * 					 
     */
    public Iterator getHngList() {
        return BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_HIST_NUCLEAR_GRADE).getValues().iterator();
    }

    /**
     * Returns the lympnodeStage.
     * @return String
     */
    public String[] getLymphNodeStage() {
        return _lymphNodeStage;
    }

    /** Returns the list of possible values for lymph node stage.
     *
     * @return  A {@link com.ardais.bigr.iltds.assistants.List} of 
     * 					 lymph node stage values.
     */
    public Iterator getLymphNodeStageList() {
        return BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_LYMPH_NODE_STAGE_DESC).getValues().iterator();
    }

    /**
     * Returns the pathNotesContains.
     * @return String
     */
    public String getPathNotesContains() {
        return _pathNotesContains;
    }

    /**
     * Returns the pathVerifNotesContains.
     * @return String
     */
    public String getPathVerifNotesContains() {
        return _pvNotesContains;
    }

    /**
     * Returns the samplePathology.
     * @return String[]
     * @todo Set this in the action from what is set in the session
     */
    public String[] getSamplePathology() {
        return _samplePathology;
    }

    /**
     * Returns the samplePathologyContains.
     * @return String
     */
    public String getSamplePathologyContains() {
        return _samplePathologyContains;
    }

    /**
     * Returns the stage.
     * @return String
     */
    public String[] getStage() {
        return _stage;
    }

    /**
     * Returns the list of possible values for stage grouping.
     *
     * @return  List
     * 					 
     */
    public Iterator getStageList() {
        return BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_STAGE_GROUPINGS).getValues().iterator();

    }

    /**
     * Returns the tumorStage.
     * @return String
     */
    public String[] getTumorStage() {
        return _tumorStage;
    }

    /**
     * Returns the list of possible values for tumor stage.
     *
     * @return  A {@link com.ardais.bigr.iltds.assistants.List} of 
     * 					 tumor stage values.
     */
    public Iterator getTumorStageList() {
        return BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_TUMOR_STAGE_DESC).getValues().iterator();

    }

    /**
     * Sets the caseDiagnosis.
     * @param caseDiagnosis The caseDiagnosis to set
     */
    public void setCaseDiagnosis(String[] caseDiagnosis) {
        _caseDiagnosis = caseDiagnosis;
    }

    /**
     * Sets the caseDiagnosisContains.
     * @param caseDiagnosisContains The caseDiagnosisContains to set
     */
    public void setCaseDiagnosisContains(String caseDiagnosisContains) {
        _caseDiagnosisContains = caseDiagnosisContains;
    }

    /**
     * Sets the distantMetastisis.
     * @param distantMetastisis The distantMetastisis to set
     */
    public void setDistantMetastasis(String[] distantMetastasis) {
        _distantMetastasis = distantMetastasis;
    }

    /**
     * Sets the hng.
     * @param hng The hng to set
     */
    public void setHng(String[] hng) {
        _hng = hng;
    }

    /**
     * Sets the lympnodeStage.
     * @param lympnodeStage The lympnodeStage to set
     */
    public void setLymphNodeStage(String[] lymphNodeStage) {
        _lymphNodeStage = lymphNodeStage;
    }

    /**
     * Sets the pathNotesContains.
     * @param pathNotesContains The pathNotesContains to set
     */
    public void setPathNotesContains(String pathNotesContains) {
        _pathNotesContains = pathNotesContains;
    }

    /**
     * Sets the pathVerifNotesContains.
     * @param pathVerifNotesContains The pathVerifNotesContains to set
     */
    public void setPathVerifNotesContains(String pathVerifNotesContains) {
        _pvNotesContains = pathVerifNotesContains;
    }

    /**
     * Sets the samplePathology.
     * @param samplePathology The samplePathology to set
     */
    public void setSamplePathology(String[] samplePathology) {
        _samplePathology = samplePathology;
    }

    /**
     * Sets the samplePathologyContains.
     * @param samplePathologyContains The samplePathologyContains to set
     */
    public void setSamplePathologyContains(String samplePathologyContains) {
        _samplePathologyContains = samplePathologyContains;
    }

    /**
     * Sets the stage.
     * @param stage The stage to set
     */
    public void setStage(String[] stage) {
        _stage = stage;
    }

    /**
     * Sets the tumorStage.
     * @param tumorStage The tumorStage to set
     */
    public void setTumorStage(String[] tumorStage) {
        _tumorStage = tumorStage;
    }

    /**
     * Returns the from.
     * @return String
     */
    public String getFrom() {
        return _diagnosisFrom;
    }

    /**
     * Sets the from.
     * @param from The from to set
     */
    public void setFrom(String from) {
        _diagnosisFrom = from;
    }

    /**
     * Returns the map.
     * @return Map
     */
    public Map getFilters() {
        Map results = new HashMap();

        // handle three "from" cases:  DDC, ILTDS, BEST
        String from = getFrom();
        if (Constants.DDC.equals(from)) {
            Filter.addStringsEqualToMap(new FilterCaseDiagnosisDdc(getCaseDiagnosis(), getCaseDiagnosisLabel()), results);
            Filter.addStringLikeToMap(
                new FilterCaseDiagnosisLikeDdc(getCaseDiagnosisContains()), results);
        } else if (Constants.ILTDS.equals(from)) {
            Filter.addStringsEqualToMap(new FilterCaseDiagnosisIltds(getCaseDiagnosis(), getCaseDiagnosisLabel()), results);
            Filter.addStringLikeToMap(
                new FilterCaseDiagnosisLikeIltds(getCaseDiagnosisContains()), results);
        } else { // "BEST"
            Filter.addStringsEqualToMap(new FilterCaseDiagnosisBest(getCaseDiagnosis(), getCaseDiagnosisLabel()), results);
            Filter.addStringLikeToMap(
                new FilterCaseDiagnosisLikeBest(getCaseDiagnosisContains()), results);
        }

        // handle normal cases, that don't depend on "from" value
        Filter.addStringsEqualToMap(new FilterStageMetastasis(getDistantMetastasis()), results);
        Filter.addStringsEqualToMap(new FilterHng(getHng()), results);
        Filter.addStringsEqualToMap(new FilterStageLymphNode(getLymphNodeStage()), results);
        Filter.addStringsEqualToMap(new FilterStageGrouping(getStage()), results);
        Filter.addStringsEqualToMap(new FilterStageTumor(getTumorStage()), results);
        Filter.addStringsEqualToMap(new FilterSamplePathology(getSamplePathology(), getSamplePathologyLabel()), results);
        Filter.addStringLikeToMap(new FilterSamplePathologyLike(getSamplePathologyContains()), results);
        Filter.addStringContainsToMap(new FilterPathologyNotesContains(getPathNotesContains()), results);
        Filter.addStringContainsToMap(new FilterPathologyVerificationNotesContains(getPathVerifNotesContains()), results);
        return results;
    }

    public void setFilters(Map m) {
        String caseddckey = FilterConstants.KEY_DDCCASEDIAGNOSIS;
        String likeddckey = FilterConstants.KEY_DDCCASEDIAGNOSISLIKE;
        String caseiltdskey = FilterConstants.KEY_ILTDSCASEDIAGNOSIS;
        String likeiltdskey = FilterConstants.KEY_ILTDSCASEDIAGNOSISLIKE;
        String casebestkey = FilterConstants.KEY_BESTCASEDIAGNOSIS;
        String likebestkey = FilterConstants.KEY_BESTCASEDIAGNOSISLIKE;
        String dmkey = FilterConstants.KEY_DISTANTMETASTASIS;
        String hngkey = FilterConstants.KEY_HNG;
        String lymphkey = FilterConstants.KEY_LYMPHNODESTAGE;
        String stagekey = FilterConstants.KEY_STAGE;
        String tumorstagekey = FilterConstants.KEY_TUMORSTAGE;
        String samppathkey = FilterConstants.KEY_SAMPLEPATHOLOGY;
        String samppathlikekey = FilterConstants.KEY_SAMPLEPATHOLOGYLIKE;
        String pathnotescontkey = FilterConstants.KEY_PATHNOTESCONTAINS;
        String pvnotescontkey = FilterConstants.KEY_PVNOTESCONTAINS;
        // three cases for case diagnosis:  ddc, iltds, best
        if (m.containsKey(caseddckey)) {
            FilterCaseDiagnosisDdc f = (FilterCaseDiagnosisDdc) m.get(caseddckey);
            setFrom(Constants.DDC);
            setCaseDiagnosis(f.getFilterValues());
            setCaseDiagnosisLabel(f.getDescriptions());
        }
        if (m.containsKey(caseiltdskey)) {
            FilterCaseDiagnosisIltds f = (FilterCaseDiagnosisIltds) m.get(caseiltdskey);
            setFrom(Constants.ILTDS);
            setCaseDiagnosis(f.getFilterValues());
            setCaseDiagnosisLabel(f.getDescriptions());
        }
        if (m.containsKey(casebestkey)) {
            FilterCaseDiagnosisBest f = (FilterCaseDiagnosisBest) m.get(casebestkey);
            setFrom(Constants.BEST);
            setCaseDiagnosis(f.getFilterValues());
            setCaseDiagnosisLabel(f.getDescriptions());
        }
        // three cases for case diagnosis "like"
        if (m.containsKey(likeddckey)) {
            FilterCaseDiagnosisLikeDdc f = (FilterCaseDiagnosisLikeDdc) m.get(likeddckey);
            setFrom(Constants.DDC);
            setCaseDiagnosisContains(f.getPatternForDisplay());
        }
        if (m.containsKey(likeiltdskey)) {
            FilterCaseDiagnosisLikeIltds f = (FilterCaseDiagnosisLikeIltds) m.get(likeiltdskey);
            setFrom(Constants.ILTDS);
            setCaseDiagnosisContains(f.getPatternForDisplay());
        }
        if (m.containsKey(likebestkey)) {
            FilterCaseDiagnosisLikeBest f = (FilterCaseDiagnosisLikeBest) m.get(likebestkey);
            setFrom(Constants.BEST);
            setCaseDiagnosisContains(f.getPatternForDisplay());
        }
        // other vals
        if (m.containsKey(dmkey)) {
            FilterStageMetastasis f = (FilterStageMetastasis) m.get(dmkey);
            setDistantMetastasis(f.getFilterValues());
        }
        if (m.containsKey(hngkey)) {
            FilterHng f = (FilterHng) m.get(hngkey);
            setHng(f.getFilterValues());
        }
        if (m.containsKey(lymphkey)) {
            FilterStageLymphNode f = (FilterStageLymphNode) m.get(lymphkey);
            setLymphNodeStage(f.getFilterValues());
        }
        if (m.containsKey(stagekey)) {
            FilterStageGrouping f = (FilterStageGrouping) m.get(stagekey);
            setStage(f.getFilterValues());
        }
        if (m.containsKey(tumorstagekey)) {
            FilterStageTumor f = (FilterStageTumor) m.get(tumorstagekey);
            setTumorStage(f.getFilterValues());
        }
        if (m.containsKey(samppathkey)) {
            FilterSamplePathology f = (FilterSamplePathology) m.get(samppathkey);
            setSamplePathology(f.getFilterValues());
            setSamplePathologyLabel(f.getDescriptions());
        }
        if (m.containsKey(samppathlikekey)) {
            FilterSamplePathologyLike f = (FilterSamplePathologyLike) m.get(samppathlikekey);
            setSamplePathologyContains(f.getPatternForDisplay());
        }
        if (m.containsKey(pathnotescontkey)) {
            FilterPathologyNotesContains f = (FilterPathologyNotesContains) m.get(pathnotescontkey);
            setPathNotesContains(f.getPhrase());
        }
        if (m.containsKey(pvnotescontkey)) {
            FilterPathologyVerificationNotesContains f = (FilterPathologyVerificationNotesContains) m.get(pvnotescontkey);
            setPathVerifNotesContains(f.getPhrase());
        }
        
        
    }

    public void updateFromSession(HttpSession session, String txType) {

        Hashtable diagnosisList = (Hashtable) 
            session.getAttribute("libraryCaseDiagnosis.selected."+txType);
        if (diagnosisList != null) {
            String[] str = (String[]) (diagnosisList.keySet().toArray(new String[] {
            }));
            setCaseDiagnosis(str);
        }

        diagnosisList = (Hashtable) 
            session.getAttribute("librarySamplePathology.selected."+txType);
        if (diagnosisList != null) {
            String[] str = (String[]) (diagnosisList.keySet().toArray(new String[] {
            }));
            setSamplePathology(str);
        }
        
        List diagnosisLabels = (List) 
            session.getAttribute("libraryCaseDiagnosis.label."+txType);
        if (diagnosisLabels != null) {
            String[] str = (String[]) (diagnosisLabels.toArray(new String[0]));
            setCaseDiagnosisLabel(str);
        }

        diagnosisLabels = (List) 
            session.getAttribute("librarySamplePathology.label."+txType);
        if (diagnosisLabels != null) {
            String[] str = (String[]) (diagnosisLabels.toArray(new String[0]));
            setSamplePathologyLabel(str);
        }
    }

    /**
     * Returns the caseDiagnosisLabel.
     * @return String[]
     */
    public String[] getCaseDiagnosisLabel() {
        if (_caseDiagnosisLabel==null) // lazy init to simplify display logic
            _caseDiagnosisLabel=new String[0] ;
        return _caseDiagnosisLabel;
    }

    /**
     * Sets the caseDiagnosisLabel.
     * @param caseDiagnosisLabel The caseDiagnosisLabel to set
     */
    public void setCaseDiagnosisLabel(String[] caseDiagnosisLabel) {
        _caseDiagnosisLabel = caseDiagnosisLabel;
    }

    /**
     * Returns the samplePathologyLabel.
     * @return String[]
     */
    public String[] getSamplePathologyLabel() {
        if (_samplePathologyLabel==null) // lazy init to simplify display logic
            _samplePathologyLabel=new String[0] ;
        return _samplePathologyLabel;
    }

    /**
     * Sets the samplePathologyLabel.
     * @param samplePathologyLabel The samplePathologyLabel to set
     */
    public void setSamplePathologyLabel(String[] samplePathologyLabel) {
        _samplePathologyLabel = samplePathologyLabel;
    }

}
