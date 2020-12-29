package com.quortexservice.quortex.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "QUESTIONFEEDBACK")
public class QuestionFeedback {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qfeedback_seq")
	@SequenceGenerator(name="qfeedback_seq", sequenceName = "qfeedback_seq")
	@Column(name = "FEEDBACK_ID", updatable = false)
	private Integer feedbackId;
	
	@Column(name = "QUESTION_ID", nullable = false)
	private Integer questionId;

	@Column(name = "USER_ID", nullable = false)
	private Integer userId;
	
	@Column(name = "LIKED", nullable = false)
	private Boolean liked = false;
	
	@Column(name = "UNLIKED", nullable = false)
	private Boolean unliked = false;
	
	public Integer getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(Integer feedbackId) {
		this.feedbackId = feedbackId;
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

	public Boolean getLiked() {
		return liked;
	}

	public void setLiked(Boolean liked) {
		this.liked = liked;
	}

	public Boolean getUnliked() {
		return unliked;
	}

	public void setUnliked(Boolean unliked) {
		this.unliked = unliked;
	}

	@Override
	public String toString() {
		return "QuestionFeedback [feedbackId=" + feedbackId + ", questionId=" + questionId + ", userId=" + userId
				+ ", liked=" + liked + ", unliked=" + unliked + "]";
	}
	
}
