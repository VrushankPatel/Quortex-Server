package com.quortexservice.quortex.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "ANSWERS")
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answers_seq")
	@SequenceGenerator(name="answers_seq", sequenceName = "answers_seq")
	@Column(name = "ANSWER_ID", updatable = false)
	private Integer answerId;
	
	@Column(name = "QUESTION_ID", nullable = false)
	private Integer questionId;

	@Column(name = "USER_ID", nullable = false)
	private Integer userId;
	
	@Column(name = "ANSWER_DESC", nullable = false, length = 2500)
	private String answerDesc;
	
	@Column(name="TIME_TAKEN", nullable = false)
	private Long timeTaken;

	@Column(name = "CREATE_DATE", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date createDate;

	@JsonInclude()
	@Transient
	private String nickName;
	
	@JsonInclude()
	@Transient
	private AnswerFeedback answerFeedbackByCurrentUser;
	
	@JsonInclude()
	@Transient
	private List<AnswerFeedback> answerFeedbackList;
	
	
	@JsonInclude()
	@Transient
	private Integer noOfLikes;
	
	@JsonInclude()
	@Transient
	private Integer noOfDislikes;

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
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

	public String getAnswerDesc() {
		return answerDesc;
	}

	public void setAnswerDesc(String answerDesc) {
		this.answerDesc = answerDesc;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(Long timeTaken) {
		this.timeTaken = timeTaken;
	}

	public AnswerFeedback getAnswerFeedbackByCurrentUser() {
		return answerFeedbackByCurrentUser;
	}

	public void setAnswerFeedbackByCurrentUser(AnswerFeedback answerFeedbackByCurrentUser) {
		this.answerFeedbackByCurrentUser = answerFeedbackByCurrentUser;
	}

	public Integer getNoOfLikes() {
		return noOfLikes;
	}

	public void setNoOfLikes(Integer noOfLikes) {
		this.noOfLikes = noOfLikes;
	}

	public Integer getNoOfDislikes() {
		return noOfDislikes;
	}

	public void setNoOfDislikes(Integer noOfDislikes) {
		this.noOfDislikes = noOfDislikes;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public List<AnswerFeedback> getAnswerFeedbackList() {
		return answerFeedbackList;
	}

	public void setAnswerFeedbackList(List<AnswerFeedback> answerFeedbackList) {
		this.answerFeedbackList = answerFeedbackList;
	}

	@Override
	public String toString() {
		return "Answer [answerId=" + answerId + ", questionId=" + questionId + ", userId=" + userId + ", answerDesc="
				+ answerDesc + ", timeTaken=" + timeTaken + ", createDate=" + createDate + ", noOfLikes=" + noOfLikes
				+ ", noOfDislikes=" + noOfDislikes + "]";
	}
}
