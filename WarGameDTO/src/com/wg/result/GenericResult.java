package com.wg.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GenericResult implements Serializable{

	private String message;

	private List<String> messages;
	
	public List<String> getMessages() {
		if(messages == null)
		{
			messages = new ArrayList<String>();
		}
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}