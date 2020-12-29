package com.quortexservice.quortex.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "ANSWERFEEDBACK")
public class AnswerFeedback {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_seq")
	@SequenceGenerator(name="feedback_seq", sequenceName = "feedback_seq")
	@Column(name = "FEEDBACK_ID", updatable = false)
	private Integer feedbackId;
	
	@Column(name = "ANSWER_ID", nullable = false)
	private Integer answerId;
	

	@Column(name = "USER_ID", nullable = false)
	private Integer userId;
	
	@Column(name = "LIKED", nullable = false)
	private Boolean liked = false;
	
	@Column(name = "UNLIKED", nullable = false)
	private Boolean unliked = false;
	
	@Column(name = "REPORT_DESC", length = 1000)
	private String reportDesc;
	
	@JsonInclude()
	@Transient
	private String nickName;

	public Integer getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(Integer feedbackId) {
		this.feedbackId = feedbackId;
	}

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
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

	public String getReportDesc() {
		return reportDesc;
	}

	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return "AnswerFeedback [feedbackId=" + feedbackId + ", answerId=" + answerId + ", userId=" + userId + ", liked="
				+ liked + ", unliked=" + unliked + ", reportDesc=" + reportDesc + ", nickName=" + nickName + "]";
	}	
}
