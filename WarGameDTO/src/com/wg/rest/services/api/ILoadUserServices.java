package com.wg.rest.services.api;

import javax.xml.bind.JAXBException;

import com.wg.dto.SimpleResult;
import com.wg.dto.UserDTO;


public interface ILoadUserServices {

	public static final String SERVICE_NAME = "loadUserServices";
	
	public String xmlStringUser(long idUser)  throws JAXBException;

	public SimpleResult genericSearch(UserDTO userDto) throws IllegalArgumentException, IllegalAccessException;
}
