package com.wg.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user")
public class UserDTO extends SimpleResult{

	private int idUser;
	private String userName;
	private String password;
	private String mail;
	private String verified;
	
	public int getIdUser() {
		return idUser;
	}
	@XmlElement(name="id")
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getUserName() {
		return userName;
	}
	@XmlElement(name="userName")
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMail() {
		return mail;
	}
	@XmlElement(name="mail")
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getVerified() {
		return verified;
	}
	public void setVerified(String verified) {
		this.verified = verified;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("");
		res.append(" userName : ").append(this.userName)
			.append(" mail : ").append(this.mail);
		return res.toString();
	}
	
	
}
