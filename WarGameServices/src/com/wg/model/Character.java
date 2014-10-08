package com.wg.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="\"Character\"")
public class Character extends BaseModel{

	@Id
	@Column(name="id")
	private long id;
	
	@Column(name="des")
	private String des;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "characters")
	private Set<Feature> features = new HashSet<Feature>(0);
	
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

	public Set<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(Set<Feature> features) {
		this.features = features;
	}


}
