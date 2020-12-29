package com.quortex.blogapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table; 

@Entity
@Table(name = "users")
public class User {

	//mark id as primary key  
	@Id  
	//defining id as column name  
	@Column  
	private int id;  
	//defining name as column name  
	@Column  
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}  
	
}
