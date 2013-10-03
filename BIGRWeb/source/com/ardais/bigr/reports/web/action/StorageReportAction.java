package com.ardais.bigr.reports.web.action;

import com.ardais.bigr.report.storagereport.StorageReportService;
import com.ardais.bigr.report.storagereport.model.StorageReportModel;
import com.ardais.bigr.reports.storagereport.BoxInfo;
import com.ardais.bigr.reports.storagereport.StorageInfo;
import com.ardais.bigr.reports.storagereport.StorageLocationInfo;
import com.ardais.bigr.reports.storagereport.StorageReportInfo;
import com.ardais.bigr.reports.web.form.StorageReportForm;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Roman Boris
 * @since 5/17/13
 */
public class StorageReportAction
	extends BigrAction
{
	@Override
	protected ActionForward doPerform(BigrActionMapping mapping, BigrActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (mapping.getParameter().equalsIgnoreCase("VIEW"))
		{
			viewStorageReport(mapping, actionForm, request, response);
		}

		return mapping.findForward("success");
	}

	protected void viewStorageReport(BigrActionMapping mapping, BigrActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		final StorageReportForm form = (StorageReportForm) actionForm;

		final List<StorageReportModel> models = StorageReportService.SINGLETON.getStorageReportInfo();

		final StorageReportInfo reportInfo = new StorageReportInfo();

		for (StorageReportModel model : models)
		{
			final String boxLayout = String.format("%sx%s",
												   model.getColumns(),
												   model.getRows());

			final StorageLocationInfo info = reportInfo.findStorageLocationInfo(model.getLocationAddressId());

			final StorageInfo storageInfo = info.findStorageInfo(model.getStorageTypeCid(),
																 model.getUnitName());

			final int count = model.getColumns() * model.getRows() - model.getInUse();

			storageInfo.getBoxInfos().add(
				new BoxInfo(model.getBoxId(),
							boxLayout,
							String.valueOf(count)));
		}

		form.setReportInfo(reportInfo);
	}
}
