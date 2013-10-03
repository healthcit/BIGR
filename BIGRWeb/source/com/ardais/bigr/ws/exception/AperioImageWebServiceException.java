package com.ardais.bigr.ws.exception;

import com.ardais.bigr.ws.BigrWebServiceFault;

import javax.xml.ws.WebFault;

/**
 * Represents exception for AperioImageService
 *
 * @author Roman Boris
 * @since 12/6/12
 */
@WebFault(
	name = "AperioImageWebServiceFault",
	faultBean = BigrWebServiceFault.FAULT_BEAN,
	targetNamespace = "http://ardais.com")
public class AperioImageWebServiceException
	extends Exception
{
	private static final long serialVersionUID = 1L;

	protected BigrWebServiceFault faultInfo;

	public AperioImageWebServiceException(BigrWebServiceFault faultInfo)
	{
		this.faultInfo = faultInfo;
	}

	public AperioImageWebServiceException(String message, BigrWebServiceFault faultInfo)
	{
		super(message);
		this.faultInfo = faultInfo;
	}

	public AperioImageWebServiceException(String message, Throwable cause, BigrWebServiceFault faultInfo)
	{
		super(message, cause);
		this.faultInfo = faultInfo;
	}

	public AperioImageWebServiceException(Throwable cause, BigrWebServiceFault faultInfo)
	{
		super(cause);
		this.faultInfo = faultInfo;
	}

	public BigrWebServiceFault getFaultInfo()
	{
		return faultInfo;
	}
}
