package com.quortexservice.quortex.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FOLLOWERS")
public class Follower {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "followers_seq")
	@SequenceGenerator(name="followers_seq", sequenceName = "followers_seq")
	@Column(name = "FOLLOWER_ID", updatable = false)
	private Integer folllowerId;
	
	@Column(name = "QUESTION_ID", nullable = false)
	private Integer questionId;

	@Column(name = "USER_ID", nullable = false)
	private Integer userId;

	@Column(name = "FOLLOWED", nullable = false)
	private Boolean followed = false;
	
	public Integer getFolllowerId() {
		return folllowerId;
	}

	public void setFolllowerId(Integer folllowerId) {
		this.folllowerId = folllowerId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Boolean getFollowed() {
		return followed;
	}

	public void setFollowed(Boolean followed) {
		this.followed = followed;
	}

	@Override
	public String toString() {
		return "Follower [folllowerId=" + folllowerId + ", questionId=" + questionId + ", userId=" + userId
				+ ", followed=" + followed + "]";
	}
	
}
