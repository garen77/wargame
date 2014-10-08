package com.wg.criteria;

import java.util.List;

public class MailSenderCriteria extends GenericCriteria {

	private String fromAddr;
	
	private String toAddr;
	
	private List<String> toAddrCopy;

	public String getFromAddr() {
		return fromAddr;
	}

	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}

	public String getToAddr() {
		return toAddr;
	}

	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}

	public List<String> getToAddrCopy() {
		return toAddrCopy;
	}

	public void setToAddrCopy(List<String> toAddrCopy) {
		this.toAddrCopy = toAddrCopy;
	}
	
	
}
