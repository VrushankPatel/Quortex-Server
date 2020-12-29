package com.quortexservice.quortex.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude; 

@Entity
@Table(name = "USERS")

public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
	@SequenceGenerator(name="users_seq", sequenceName = "users_seq")
	@Column(name = "USER_ID", updatable = false, nullable = false)
	private Integer userId;
	
	@Column(name="FIRST_NAME", nullable = false)
	private String firstName;
	
	@Column(name="LAST_NAME", nullable = false)
	private String lastName;
	
	@Column(name="NICKNAME", nullable = false)
	private String nickName;
	
	@Column(name="EMAIL", nullable = false, unique = true)
	private String email;
	
	@Column(name="PASSWORD", nullable = false)
	private String password;
	
	@Column(name="ROLE", nullable = false) 
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name="BIRTH_DATE", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date birthdate;

	@Column(name="GRADE")
	private Integer grade;

	@Column(name="COUNTRY")
	private String country;

	@Column(name="SCHOOL")
	private String school;

	@JsonInclude()
	@Transient
	private UserProgressLevel userProgressLevel;
	
	@JsonInclude()
	@Transient
	private List<Question> questionList;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	

	public UserProgressLevel getUserProgressLevel() {
		return userProgressLevel;
	}

	public void setUserProgressLevel(UserProgressLevel userProgressLevel) {
		this.userProgressLevel = userProgressLevel;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", nickName="
				+ nickName + ", email=" + email + ", role=" + role + ", birthdate="
				+ birthdate + ", grade=" + grade + ", country=" + country + ", school=" + school + "]";
	}


}

