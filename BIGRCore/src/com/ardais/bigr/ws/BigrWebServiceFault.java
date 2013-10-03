package com.ardais.bigr.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents faults for web services.
 *
 * @author Roman Boris
 * @since 12/11/12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BigrWebServiceFault")
public class BigrWebServiceFault
{
	public static final String FAULT_BEAN = "com.ardais.bigr.ws.BigrWebServiceFault";

	private String message;

	public BigrWebServiceFault()
	{
	}

	public BigrWebServiceFault(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String value)
	{
		this.message = value;
	}
}
