package com.wg.rest.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.wg.rest.resources.registration.confirm.ConfirmRegistrationResource;

public class WarGameApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();
	
	public WarGameApplication()
	{
		singletons.add(new ConfirmRegistrationResource());
	}
	@Override
	public Set<Class<?>> getClasses() {
		
		return empty;
		
	}
	@Override
	public Set<Object> getSingletons() {
		
		return singletons;
		
	}
	
	
}
