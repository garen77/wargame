package com.wg.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="\"User\"")
public class User extends BaseModel {

	@Id
	@SequenceGenerator(name="User_idUser_seq",
	sequenceName="User_idUser_seq",
	allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator="User_idUser_seq")
	@Column(name = "\"idUser\"", updatable=false)
	private int idUser;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "\"UserCharacter\"", joinColumns = { 
			@JoinColumn(name = "\"idUser\"", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "\"idCharacter\"", 
					nullable = false, updatable = false) })
	private Set<Character> characters = new HashSet<Character>(0);
	
	@Column(name="mail")
	private String mail;

	@Column(name="password")
	private String password;

	@Column(name="nick")
	private String nick;
	
	@Column(name="verified")
	private String verified;
	
	@Column(name="\"regDate\"")
	private Date registerDate;
		
	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Set<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(Set<Character> characters) {
		this.characters = characters;
	}
	
	
}
