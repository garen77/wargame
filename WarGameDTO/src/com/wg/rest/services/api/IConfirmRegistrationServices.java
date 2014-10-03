package com.wg.rest.services.api;

import javax.xml.bind.JAXBException;

public interface IConfirmRegistrationServices {

	public static final String SERVICE_NAME = "confirmRegistrationServices";
	
	public String confirmUser(long idUser)  throws JAXBException;
}
