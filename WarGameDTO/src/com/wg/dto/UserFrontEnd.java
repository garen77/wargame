package com.wg.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user")
public class UserFrontEnd {

	private int idUser;
	
	private String userName;
	
	private String mail;
	
	private long idCharacter;
	
	private List<Long> idFeatures;

	@XmlElement(name="id")
	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	@XmlElement(name="userName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@XmlElement(name="mail")
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@XmlElement(name="idCharacter")
	public long getIdCharacter() {
		return idCharacter;
	}

	public void setIdCharacter(long idCharacter) {
		this.idCharacter = idCharacter;
	}

	@XmlElementWrapper(name="idFeatures")
	@XmlElement(name = "idFeature")
	public List<Long> getIdFeatures() {
		if(idFeatures == null)
		{
			idFeatures = new ArrayList<Long>();
		}
		return idFeatures;
	}

	public void setIdFeatures(List<Long> idFeatures) {
		this.idFeatures = idFeatures;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("");
		res.append(" userName : ").append(this.userName)
			.append(" mail : ").append(this.mail)
			.append(" idCharacter : ").append(this.idCharacter);
		return res.toString();
	}

	
}
