package com.wg.services.api;

import java.lang.reflect.InvocationTargetException;

import com.wg.dto.UserDTO;
import com.wg.result.RegisterResult;

public interface IRegisterServices extends IService {

	public static final String SERVICE_NAME = "registerServices";
	
	public RegisterResult  register(UserDTO userDto) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;
	
}
