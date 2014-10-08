package com.wg.services.factory;

import com.wg.factory.Factory;

public class ServicesFactory extends Factory {

	private static final String PATH = "com.wg.services";
	
	@Override
	public String getSpringFactory() {
		
		return PATH;
	}

	public Object getService(String beanId)
	{
		return super.getBean(beanId);
	}
	
	
}
