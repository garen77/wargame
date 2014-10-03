package com.wg.services.api;

import com.wg.dto.UserDTO;
import com.wg.dto.UserFrontEnd;


public interface IHomeServices extends IService {

	public static final String SERVICE_NAME = "homeServices";
	
	public UserFrontEnd loadUser(UserDTO dto);
	
}
