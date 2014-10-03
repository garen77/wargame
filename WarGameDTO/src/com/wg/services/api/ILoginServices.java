package com.wg.services.api;

import com.wg.dto.UserDTO;

public interface ILoginServices {

	public UserDTO login(UserDTO userDto)  throws IllegalArgumentException, IllegalAccessException;
	
}
