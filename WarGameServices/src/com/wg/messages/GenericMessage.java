package com.wg.messages;

import java.io.Serializable;

public class GenericMessage implements Serializable {

	private String key;
	
	private Object[] parameters;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
	
	
}
