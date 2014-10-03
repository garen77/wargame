package com.wg.services.spi;

import java.util.ResourceBundle;

public class GenericService {

	protected static final String RESOURCES_BUNDLE_PATH = "com.wg.resources.messages.";
	protected static final String GENERIC_RESOURCES_BUNDLE = "Messages";
	
	protected ResourceBundle getResourceBundle(String bundle)
	{
		return ResourceBundle.getBundle(RESOURCES_BUNDLE_PATH+bundle);
	}
}
