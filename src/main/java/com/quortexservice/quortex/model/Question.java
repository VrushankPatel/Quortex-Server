package com.quortexservice.quortex.model;

import java.io.Serializable;
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
@Table(name = "QUESTIONS")

public class Question implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questions_seq")
	@SequenceGenerator(name="questions_seq", sequenceName = "questions_seq")
	@Column(name = "QUESTION_ID", updatable = false)
	private Integer questionId;

	@Column(name = "USER_ID", nullable = false)
	private Integer userId;
	
	@Column(name = "SUBJECT", nullable = false)
	private String subject;

	@Column(name = "TOPIC", nullable = false)
	private String topic;

	@Column(name = "QUESTION_DESC", nullable = false, length = 2500)
	private String questionDesc;

	@Column(name = "CREATE_DATE", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date createDate;
	
	@JsonInclude()
	@Transient
	private String nickName;
	
	@JsonInclude()
	@Transient
	private Integer noOfAnswers;
	
	@JsonInclude()
	@Transient
	private List<Answer> answerList;
	
	@JsonInclude()
	@Transient
	private Integer noOfFollowers;

	@JsonInclude()
	@Transient
	private Integer noOfLikes;
	
	@JsonInclude()
	@Transient
	private Integer noOfDislikes;
	
	@JsonInclude()
	@Transient
	private Follower followerByCurrentUser;
	
	@JsonInclude()
	@Transient
	private QuestionFeedback questionFeedbackByCurrentUser;

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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getQuestionDesc() {
		return questionDesc;
	}

	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<Answer> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<Answer> answerList) {
		this.answerList = answerList;
	}
	
	public Follower getFollowerByCurrentUser() {
		return followerByCurrentUser;
	}

	public void setFollowerByCurrentUser(Follower followerByCurrentUser) {
		this.followerByCurrentUser = followerByCurrentUser;
	}

	public Integer getNoOfAnswers() {
		return noOfAnswers;
	}

	public void setNoOfAnswers(Integer noOfAnswers) {
		this.noOfAnswers = noOfAnswers;
	}

	public Integer getNoOfFollowers() {
		return noOfFollowers;
	}

	public void setNoOfFollowers(Integer noOfFollowers) {
		this.noOfFollowers = noOfFollowers;
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
	
	public QuestionFeedback getQuestionFeedbackByCurrentUser() {
		return questionFeedbackByCurrentUser;
	}

	public void setQuestionFeedbackByCurrentUser(QuestionFeedback questionFeedbackByCurrentUser) {
		this.questionFeedbackByCurrentUser = questionFeedbackByCurrentUser;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", userId=" + userId + ", subject=" + subject + ", topic=" + topic
				+ ", questionDesc=" + questionDesc + ", createDate=" + createDate + ", noOfAnswers=" + noOfAnswers
				+ ", answerList=" + answerList + ", noOfFollowers=" + noOfFollowers + ", noOfLikes=" + noOfLikes
				+ ", noOfDislikes=" + noOfDislikes  + "]";
	}

	
}
