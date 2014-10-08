package com.wg.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="\"Feature\"")
public class Feature extends BaseModel {

	@Id
	@Column(name="id")
	private long id;
	
	@Column(name="des")
	private String des;

	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "\"CharacterFeature\"", joinColumns = { 
			@JoinColumn(name = "\"idFeature\"", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "\"idCharacter\"", 
					nullable = false, updatable = false) })
	private Set<Character> characters = new HashSet<Character>();
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Set<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(Set<Character> characters) {
		this.characters = characters;
	}

}
