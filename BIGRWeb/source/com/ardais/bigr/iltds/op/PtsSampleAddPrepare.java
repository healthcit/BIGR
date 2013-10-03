package com.ardais.bigr.iltds.op;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.sql.ARRAY;
import org.apache.commons.collections.CollectionUtils;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.es.javabeans.HttpQueryBean;
import com.ardais.bigr.iltds.assistants.SampleDataBean;
import com.ardais.bigr.iltds.beans.PtsOperation;
import com.ardais.bigr.iltds.beans.PtsOperationHome;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.iltds.helpers.LineItemHelper;
import com.ardais.bigr.iltds.helpers.ProjectHelper;
import com.ardais.bigr.iltds.helpers.SampleHelper;
import com.ardais.bigr.iltds.helpers.SampleTableHelper;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;

/**
 * Queries for the samples using the criteria specified
 * in the request, and forwards to the JSP to allow them
 * to be selected.
 */
public class PtsSampleAddPrepare extends StandardOperation {
	/**
	 * Creates the SampleSelectPrepare op.
	 *
	 * @param  req  the HttpServletRequest
	 * @param  res  the HttpServletResponse
	 * @param  ctx  the ServletContext
	 */
	public PtsSampleAddPrepare(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 */
	public void invoke() throws IOException, ServletException, ApiException {
		String lineItemId = request.getParameter(LineItemHelper.ID_LINEITEM_ID);
		if (lineItemId == null) {
			throw new ServletException(
				"Required parameter '"
					+ LineItemHelper.ID_LINEITEM_ID
					+ "' is not present.");
		}

		ProjectHelper projectHelper = ProjectHelper.prepareFromRequest(request);
		LineItemHelper lineItemHelper = null;

		// Create the line item helper and add it to the project helper.
    PtsOperation ptsOp = null;
		try {
      PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
      ptsOp = home.create();
			lineItemHelper = new LineItemHelper(ptsOp.getLineItem(lineItemId));
			projectHelper.addLineItem(lineItemHelper);
		} catch (javax.naming.NamingException e) {
			throw new ApiException(e);
		} catch (javax.ejb.CreateException e) {
      throw new ApiException(e);
    } catch (ClassNotFoundException e) {
      throw new ApiException(e);
    }

		// Either get the search parameters from the request, or clear
		// the search parameters, and place the search into the session.
		HttpQueryBean query = null;
		boolean isClear = !JspHelper.isEmpty(request.getParameter("clear"));
		try {
			if (isClear) {
				query = new HttpQueryBean();
				request.getSession().removeAttribute("selectedDX");
				request.getSession().removeAttribute("selectedTC");
				request.getSession().removeAttribute("labelTC");
				request.getSession().removeAttribute("labelDX");
			} else {
				query = new HttpQueryBean(request);
				query.setAccountID(projectHelper.getDataBean().getClient());
			}
		} catch (IllegalArgumentException e) {
			retry(e.getMessage());
			return;
		} finally {
			request.getSession().setAttribute("pts.samplequery", query);
		}

		// Check that all-all query has not been selected.  This is done afterwards so that all the
		// selected values will be returned to the search page.
		String msg = null;
		if (!isClear) {
			if (((query.getTissueList() == null
				|| query.getTissueList().isEmpty())
				&& (query.getTissueLike() == null)
				&& (query.getDiagnosisList() == null
					|| query.getDiagnosisList().isEmpty()))
				&& (query.getDiagnosisLike() == null)
				&& (query.getDonorID() == null
					&& (query.getCaseList() == null
						|| query.getCaseList().isEmpty())
					&& (query.getSampleList() == null
						|| query.getSampleList().isEmpty())
					&& (query.getOrderNumber() == null)
					&& (query.getShoppingCartUserId() == null))) {
				retry("Please select at least one of: hold list, case IDs, or sample IDs.");
				return;

			}

			// Run query
			try {
				List results = (ArrayList) ptsOp.availableInvQuery(query, WebUtils.getSecurityInfo(request));
				SampleTableHelper tableHelper = new SampleTableHelper();
				tableHelper.setCheckboxIncluded(true);
				tableHelper.setCheckboxColumnHeader("Add");
				tableHelper.setCheckboxColumnTitle(
					"Check to add the sample to the project");
				tableHelper.setCheckboxOnclick("updateTotalSelected();");
				tableHelper.setCheckboxValue(
					SampleTableHelper.CHECKBOX_SAMPLEID);
				tableHelper.setNoSampleText(
					"No samples met the search criteria");
				tableHelper.setTableHeader("Samples");
				lineItemHelper.setSampleTableHelper(tableHelper);

				for (int i = 0; i < results.size(); i++) {
					tableHelper.addSample(
						new SampleHelper((SampleDataBean) results.get(i)));
				}


        // MR 6693 we want to ensure that there is no mix of BMS and NONBMS samples			
        //  in the result set.  If this happens, the user will need to rerun the query	
        if ( results.size() > 0) {
          
          // first thing is to get the ids out of the results, so that can be passed in...
          ArrayList samples = new ArrayList();
          SampleDataBean sample = new SampleDataBean();
          for (int i = 0; i < results.size(); i++) {
            sample = (SampleDataBean) results.get(i);
            samples.add(sample.getId());
          }
          
          StringBuffer msgtext = new StringBuffer();
          ArrayList bms_samples = new ArrayList();
          ArrayList nonbms_samples = new ArrayList();

          bms_samples = IltdsUtils.getBMSSamplesFromList(IltdsUtils.BMS, new ArrayList(samples) );
          nonbms_samples = IltdsUtils.getBMSSamplesFromList(IltdsUtils.NONBMS, new ArrayList(samples) );
          if ( (bms_samples.size() > 0) && (nonbms_samples.size() > 0 )) {
            String sampleId = new String();
            msgtext.append("WARNING. You have selected BMS and non-BMS samples in the same PTS list. This is not allowed.  The following BMS samples were included in this list: ");
            for (int i = 0; i < bms_samples.size(); i++) {
                 sampleId = (String) bms_samples.get(i);
                 msgtext.append(sampleId);
                 msgtext.append(" ");
                 } 
            retry(msgtext.toString());
            return;
          }
        }			
        	
				// we want to determine if some of the samples specified did not get
				// returned due to "business rules". if so, we want to return a separate list
				// of those sample ids...
				if (query.getSampleList().size() > 0) {
					if (results.size() != query.getSampleList().size()) {
						// determine which samples have not been returned...
						Vector resultsSampleIds = new Vector();
						SampleDataBean sampleBean = new SampleDataBean();
						for (int j = 0; j < results.size(); j++ ) {
							sampleBean = (SampleDataBean) results.get(j);
							resultsSampleIds.add(sampleBean.getId());
						}
						
						Vector querySampleIds = new Vector();
						ArrayList queryIds = query.getSampleList();
						for (int k=0; k < query.getSampleList().size(); k++) {
							querySampleIds.add(queryIds.get(k));
						}
							
						Collection notReturnedSampleIds = CollectionUtils.subtract( (Collection) querySampleIds, (Collection) resultsSampleIds );
						if (notReturnedSampleIds.size() > 0) {
							lineItemHelper.setSamplesNotReturned( (List) notReturnedSampleIds);
						}
					}
				}
					
			
			} catch (Exception e) {
				msg =
					"Your query is too broad. Please refine your search and try again.";
			}
		}

		if ((msg != null) || (isClear)) {
			request.setAttribute(ProjectHelper.ID_MESSAGE, msg);
			(new PtsSampleSearchPrepare(request, response, servletCtx))
				.invoke();
		} else {
			servletCtx.getRequestDispatcher("/hiddenJsps/iltds/pts/SampleAdd.jsp").forward(
				request,
				response);
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2/21/2002 10:05:29 AM)
	 * @param myError java.lang.String
	 */
	public void retry(String myError) {
		request.setAttribute("myError", myError);

		try {
			PtsSampleSearchPrepare op =
				new PtsSampleSearchPrepare(request, response, servletCtx);
			op.invoke();
		} catch (Exception e) {

		}

	}
}
