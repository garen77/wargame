package com.wg.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="result")
public class SimpleResult {

	private boolean found;

	public boolean isFound() {
		return found;
	}

	@XmlElement(name="found")
	public void setFound(boolean found) {
		this.found = found;
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("");
		res.append(" found : ").append(this.found);
		return res.toString();
	}

	
}
