package com.wg.factory;

import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

public abstract class Factory {

	private static final String PATH_XML_SPRING_FILE = "classpath*:app-defs.xml";
	
	public Object getBean(String beanId)
	{
		BeanFactoryLocator locator = SingletonBeanFactoryLocator.getInstance(PATH_XML_SPRING_FILE);
		BeanFactoryReference bf = locator.useBeanFactory(this.getSpringFactory());
		return bf.getFactory().getBean(beanId);		
	}
	
	public abstract String getSpringFactory();
	

}
