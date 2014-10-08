package com.wg.spring.beans;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;


public class SpringBeans {

	public static ApplicationContext getApplicationContext(){
		ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		if(ctx!=null)
			return ctx;
		else
			return null;
	}
	public static Object getBean(String serviceName){
	    ApplicationContext ctx = SpringBeans.getApplicationContext();    
	    Object bean = null;
	    if(ctx!=null)
	    	bean = ctx.getBean(serviceName);
	    return bean;
	}
	
}
