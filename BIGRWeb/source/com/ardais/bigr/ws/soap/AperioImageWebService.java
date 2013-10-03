package com.ardais.bigr.ws.soap;

import com.ardais.bigr.aperio.AperioImageService;
import com.ardais.bigr.ws.BigrWebServiceFault;
import com.ardais.bigr.ws.exception.AperioImageWebServiceException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author Roman Boris
 * @since 12/6/12
 */
@WebService (
	name= "AperioImageWebService",
	serviceName = "AperioImageWebService",
	targetNamespace = "http://ardais.com/AperioImageWebService"
)
public class AperioImageWebService
{
	@WebMethod
	public void addImage(
		@WebParam(name = "aperioImageID")
		String aperioImageID,
		@WebParam(name = "bigrSampleId")
		String bigrSampleId)
		throws AperioImageWebServiceException
	{
		try
		{
			AperioImageService.SINGLETON.addImage(aperioImageID, bigrSampleId);
		}
		catch (Exception e)
		{
			throw new AperioImageWebServiceException(e, new BigrWebServiceFault(e.getMessage()));
		}
	}

	@WebMethod
	public void deleteImage(
		@WebParam(name = "bigrSampleId")
		String bigrSampleId)
		throws AperioImageWebServiceException
	{
		try
		{
			AperioImageService.SINGLETON.deleteImage(bigrSampleId);
		}
		catch (Exception e)
		{
			throw new AperioImageWebServiceException(e, new BigrWebServiceFault(e.getMessage()));
		}
	}
}
