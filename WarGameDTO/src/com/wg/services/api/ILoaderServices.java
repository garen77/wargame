package com.wg.services.api;

import com.wg.dto.UserDTO;


public interface ILoaderServices {

	public static final String SERVICE_NAME = "loaderService";
	
	public UserDTO loadUser(long idUser);
}
