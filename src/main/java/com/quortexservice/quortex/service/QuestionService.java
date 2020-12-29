package com.quortexservice.quortex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.quortexservice.quortex.exception.QuortexException;
import com.quortexservice.quortex.util.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quortexservice.quortex.model.Answer;
import com.quortexservice.quortex.model.AnswerFeedback;
import com.quortexservice.quortex.model.Follower;
import com.quortexservice.quortex.model.QuortexResponse;
import com.quortexservice.quortex.model.Question;
import com.quortexservice.quortex.model.QuestionFeedback;
import com.quortexservice.quortex.model.QuestionSearch;
import com.quortexservice.quortex.model.User;
import com.quortexservice.quortex.model.UserProgressLevel;
import com.quortexservice.quortex.repository.AnswerFeedbackRepository;
import com.quortexservice.quortex.repository.AnswerRepository;
import com.quortexservice.quortex.repository.FollowerRepository;
import com.quortexservice.quortex.repository.QuestionFeedbackRepository;
import com.quortexservice.quortex.repository.QuestionRepository;
import com.quortexservice.quortex.repository.UserRepository;

@Service("questionService")
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private FollowerRepository followerRepository;
	
	@Autowired
	private AnswerFeedbackRepository answerFeedbackRepository;
	
	@Autowired
	private QuestionFeedbackRepository questionFeedbackRepository;
		
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NotificationService notificationService;
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	@Value("${login.url}")
	private String loginUrl;
	
	private static final Logger log = LoggerFactory.getLogger(QuestionService.class);

	public ResponseEntity<Object> createQuestion(Question question) throws QuortexException {
		log.info(question.toString());
		questionRepository.save(question);
		Follower follower = new Follower();
		follower.setQuestionId(question.getQuestionId());
		follower.setUserId(question.getUserId());
		follower.setFollowed(true);
		followerRepository.save(follower);
		return new ResponseEntity<>(new QuortexResponse(ConstantUtil.QUESTION_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true,null), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> createAnswer(Answer answer) throws QuortexException {
		log.info("Saving answer details :: " + answer.toString());
		//answerRepository.findByQuestionIdAndUserId(answer.getQuestionId(), answer.getUserId()).ifPresent(ans -> answer.setAnswerId(ans.getAnswerId()));
		answerRepository.save(answer);
		List<String> emailIds = new ArrayList<>();
		log.info("Sending notification mail to the questioned user...");
		followerRepository.findByQuestionId(answer.getQuestionId()).forEach(follower -> { //get question details for that
			userRepository.findByUserId(follower.getUserId()).ifPresent(user -> {
				emailIds.add(user.getEmail());
			});
		});
		if(!emailIds.isEmpty()) {
			String text = "<p>Hi There!</p><p>A question you've followed was just answered by @"+userRepository.findByUserId(answer.getUserId()).orElse(new User()).getNickName()+":</p>"
					+ "<p>Question: "+questionRepository.findByQuestionId(answer.getQuestionId()).orElse(new Question()).getQuestionDesc()+"</p>"
					+ "<p>Answer: "+answer.getAnswerDesc()+"</p>"
					+ "<p>Login <a href=\""+loginUrl+"\">Here</a> and click the Following tab to check it out!</p>"
					+ "<p>For any questions, feedback, or concerns, shoot us an email at: <a href=\"mailto:"+fromEmail+",\">"+fromEmail+",</a></p><p>Thanks,</p><p>Quortex Support Team</p>";
			notificationService.sendBccNotification(emailIds.stream().toArray(String[]::new), "A Question You've Followed Has Been Answered in Quortex", text);
		}
		
		return new ResponseEntity<>(new QuortexResponse(ConstantUtil.ANSWER_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true,fetchUserProgressLevel(answer.getUserId())), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> deleteAnswer(Integer answerId) throws QuortexException {
		answerRepository.findByAnswerId(answerId).ifPresent(ans -> {
			answerFeedbackRepository.findByAnswerIdAndUserId(ans.getAnswerId(), ans.getUserId()).forEach(ansfdback -> {
				log.info("deleteing answer feedback details :: " + ansfdback.toString());
				answerFeedbackRepository.delete(ansfdback);
			});
			log.info("deleteing answer details :: " + ans.toString());
			answerRepository.delete(ans);
		});		
		return new ResponseEntity<>(new QuortexResponse(ConstantUtil.ANSWER_DELETED_MESSAGE,ConstantUtil.SUCCESS_CODE,true,null), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> createFollower(Follower follower) throws QuortexException {
		log.info(follower.toString());
		followerRepository.findByQuestionIdAndUserId(follower.getQuestionId(), follower.getUserId()).ifPresent(flwer -> follower.setFolllowerId(flwer.getFolllowerId()));
		if(follower.getFollowed()) {
			followerRepository.save(follower);
			return new ResponseEntity<>(new QuortexResponse(ConstantUtil.FOLLOWER_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true,null), HttpStatus.OK);
		}else {
			followerRepository.delete(follower);
			return new ResponseEntity<>(new QuortexResponse(ConstantUtil.FOLLOWER_DELETED_MESSAGE,ConstantUtil.SUCCESS_CODE,true,null), HttpStatus.OK);
		}
	}
	
	public ResponseEntity<Object> createQuestionFeedback(QuestionFeedback questionFeedback) {
		log.info(questionFeedback.toString());
		questionFeedbackRepository.findByQuestionIdAndUserId(questionFeedback.getQuestionId(), questionFeedback.getUserId())
					.forEach(qback -> questionFeedback.setFeedbackId(qback.getFeedbackId()));
		questionFeedbackRepository.save(questionFeedback);
		return new ResponseEntity<>(new QuortexResponse(ConstantUtil.USER_FEEDBACK_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true,null), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> createAnswerFeedback(AnswerFeedback answerFeedback) {
		log.info(answerFeedback.toString());
		answerFeedbackRepository.findByAnswerIdAndUserId(answerFeedback.getAnswerId(), answerFeedback.getUserId())
					.forEach(fback -> {
						answerFeedback.setFeedbackId(fback.getFeedbackId());
						if(answerFeedback.getReportDesc()!=null && !answerFeedback.getReportDesc().isEmpty()) {
							answerFeedback.setLiked(fback.getLiked());
							answerFeedback.setUnliked(fback.getUnliked());
						}else {
							answerFeedback.setReportDesc(fback.getReportDesc());
						}
					});
		answerFeedbackRepository.save(answerFeedback);
		return new ResponseEntity<>(new QuortexResponse(ConstantUtil.USER_FEEDBACK_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true,null), HttpStatus.OK);
	}
	
	public List<Question> findAllQuestions(Integer userId) {
		List<Question> questionList = questionRepository.findByOrderByCreateDateDesc();
		return fetchAnswersAndFeedbacks(questionList, userId);
	}
	
	public List<Question> findAllQuestionsForAdmin(Integer userId) {
		List<Question> questionList = new ArrayList<>();
		List<Integer> questionIdList = new ArrayList<>();
		answerFeedbackRepository.findByReportDescNotNull().forEach(ansFdBk ->{
			answerRepository.findByAnswerId(ansFdBk.getAnswerId()).ifPresent(ans ->{
				questionIdList.add(ans.getQuestionId());
			});
		});
		questionRepository.findDistinctByQuestionIdInOrderByCreateDateDesc(questionIdList).forEach(que -> {
			List<Answer> answerList = new ArrayList<>();
			answerRepository.findByQuestionIdOrderByCreateDateDesc(que.getQuestionId()).stream().forEach(ans -> {
				List<AnswerFeedback> ansFbackList = answerFeedbackRepository.findByAnswerIdAndReportDescNotNull(ans.getAnswerId());
				if (ansFbackList!=null && ansFbackList.size() > 0) {
					userRepository.findByUserId(ans.getUserId()).ifPresent(usr -> ans.setNickName(usr.getNickName()));
					ans.setNoOfDislikes(answerFeedbackRepository.countByAnswerIdAndUnliked(ans.getAnswerId(), true));
					ans.setNoOfLikes(answerFeedbackRepository.countByAnswerIdAndLiked(ans.getAnswerId(), true));
					ansFbackList.forEach(ansfc -> userRepository.findByUserId(ansfc.getUserId()).ifPresent(usr -> ansfc.setNickName(usr.getNickName())));
					ans.setAnswerFeedbackList(ansFbackList);
					answerList.add(ans);
				}
			});
			userRepository.findByUserId(que.getUserId()).ifPresent(usr -> que.setNickName(usr.getNickName()));
			que.setAnswerList(answerList);
			que.setNoOfLikes(questionFeedbackRepository.countByQuestionIdAndLiked(que.getQuestionId(), true));
			que.setNoOfDislikes(questionFeedbackRepository.countByQuestionIdAndLiked(que.getQuestionId(), true));
			que.setNoOfAnswers(answerRepository.countByQuestionId(que.getQuestionId()));
			que.setNoOfFollowers(followerRepository.countByQuestionId(que.getQuestionId()));
			questionList.add(que);
		});
		return questionList;
	}

	public List<Question> findAllQuestionsByUser(Integer userId) {
		List<Question> questionList = questionRepository.findByUserIdOrderByCreateDateDesc(userId);
		return fetchAnswersAndFeedbacks(questionList, userId);
	}

	public List<Question> findAllByFollower(Integer userId) {
		List<Question> questionList = new ArrayList<>();
		followerRepository.findByUserId(userId).forEach(follwer -> questionList.add(findAllQuestionsByLoginUser(follwer.getQuestionId(),userId)));
		return fetchAnswersAndFeedbacks(questionList, userId);
	}

	public List<Question> findAllByAnswer(Integer userId) {
		List<Question> questionList = new ArrayList<>();
		answerRepository.findByUserIdOrderByCreateDateDesc(userId).forEach(answer -> questionList.add(findAllQuestionsByLoginUser(answer.getQuestionId(), userId)));
		return fetchAnswersAndFeedbacks(questionList, userId);
	}
	
	public List<Question> findAllBySubjectTopic(QuestionSearch questionSearch) {
		log.info("findAllBySubjectTopic ::" + questionSearch.toString());
		
		List<Question> questionList =(questionSearch.getSubject()!=null && !questionSearch.getSubject().isEmpty())?
				fetchAnswersAndFeedbacks(questionRepository.findBySubjectAndTopicIgnoreCaseContainingAndQuestionDescIgnoreCaseContainingOrderByCreateDateDesc(questionSearch.getSubject(), questionSearch.getTopic(), questionSearch.getQuestionDesc()), questionSearch.getUserId()) : 
					fetchAnswersAndFeedbacks(questionRepository.findByTopicIgnoreCaseContainingAndQuestionDescIgnoreCaseContainingOrderByCreateDateDesc(questionSearch.getTopic(), questionSearch.getQuestionDesc()), questionSearch.getUserId());
		if(questionSearch.getSearchType()!=null && questionSearch.getSearchType().equalsIgnoreCase("ALL")) {
			return questionList;
		}else if(questionSearch.getSearchType()!=null && questionSearch.getSearchType().equalsIgnoreCase("WITHOUTANSWER")) {
			List<Question> questionWithoutAnsList = new ArrayList<>();
			questionList.forEach(que -> {
				if(que.getNoOfAnswers()==0) questionWithoutAnsList.add(que);
			});
			return questionWithoutAnsList;
		}else {
			List<Question> questionWithAnsList = new ArrayList<>();
			questionList.forEach(que -> {
				if(que.getNoOfAnswers()>0) questionWithAnsList.add(que);
			});
			return questionWithAnsList;
		}
	}
	
	private Question findAllQuestionsByLoginUser(Integer QuestionId, Integer userId) {
		Question question = questionRepository.findById(QuestionId).get();
		return fetchAnswersAndFeedbacksByQuestion(question, userId);
	}
	
	private List<Question> fetchAnswersAndFeedbacks(List<Question> questionList, Integer userId) {
		questionList.stream().forEach(que -> {
										userRepository.findByUserId(que.getUserId()).ifPresent(usr -> que.setNickName(usr.getNickName()));
										fetchAnswersAndFeedbacksByQuestion(que, userId);
									});
		return questionList;
	}
	
	private Question fetchAnswersAndFeedbacksByQuestion(Question question, Integer userId) {
		List<Answer> answerList = answerRepository.findByQuestionIdOrderByCreateDateDesc(question.getQuestionId());
		answerList.stream().forEach(ans -> fetchAnswerDetails(ans, userId));
		question.setAnswerList(answerList);
		List<QuestionFeedback> queFeedbkList =  questionFeedbackRepository.findByQuestionIdAndUserId(question.getQuestionId(), userId);
		if(queFeedbkList!=null && !queFeedbkList.isEmpty()) {
			question.setQuestionFeedbackByCurrentUser(queFeedbkList.stream().findFirst().get());
			queFeedbkList.stream().skip(1).forEach(dupl -> {
				log.info("Deleting duplicate questionfeedback ::" + dupl.toString());
				questionFeedbackRepository.delete(dupl);
			});
		}
		question.setNoOfLikes(questionFeedbackRepository.countByQuestionIdAndLiked(question.getQuestionId(), true));
		question.setNoOfDislikes(questionFeedbackRepository.countByQuestionIdAndLiked(question.getQuestionId(), true));
		question.setNoOfAnswers(answerRepository.countByQuestionId(question.getQuestionId()));
		question.setNoOfFollowers(followerRepository.countByQuestionId(question.getQuestionId()));
		followerRepository.findByQuestionIdAndUserId(question.getQuestionId(),userId).ifPresent(follower -> question.setFollowerByCurrentUser(follower));
		return question;
	}
	
	private Answer fetchAnswerDetails(Answer answer, Integer userId) {
		userRepository.findByUserId(answer.getUserId()).ifPresent(usr -> answer.setNickName(usr.getNickName()));
		answer.setNoOfDislikes(answerFeedbackRepository.countByAnswerIdAndUnliked(answer.getAnswerId(), true));
		answer.setNoOfLikes(answerFeedbackRepository.countByAnswerIdAndLiked(answer.getAnswerId(), true));
		List<AnswerFeedback> ansFeedbkList =  answerFeedbackRepository.findByAnswerIdAndUserId(answer.getAnswerId(),userId);
		if(ansFeedbkList!=null && !ansFeedbkList.isEmpty()) {
			answer.setAnswerFeedbackByCurrentUser(ansFeedbkList.stream().findFirst().get());
			ansFeedbkList.stream().skip(1).forEach(dupl -> {
				log.info("Deleting duplicate answerfeedback ::" + dupl.toString());
				answerFeedbackRepository.delete(dupl);
			});
		}
		return answer;
	}
	
	public UserProgressLevel fetchUserProgressLevel(Integer userId) {
		UserProgressLevel progressLevel = new UserProgressLevel();
		
		long totalSpendTimeByUser = Optional.ofNullable(answerRepository.sumTimeTakenByQuestionId(userId)).orElse(0L);
		int currentLevel =0;
		long currentTotalLevelTime = ConstantUtil.USER_TOTAL_TIME_FOR_FIRST_LEVEL ;
		long currentLevelTime = totalSpendTimeByUser;
		long totalLevelTime = currentTotalLevelTime;
		progressLevel.setLevel(currentLevel+1);
		progressLevel.setCurrentLevelTime(currentLevelTime);
		progressLevel.setCurrentTotalLevelTime(currentTotalLevelTime);
		progressLevel.setTotalSpendTimeByUser(totalSpendTimeByUser);
		log.info(progressLevel.toString());
		for(;currentLevelTime>currentTotalLevelTime;currentLevel++) {
			currentLevelTime = currentLevelTime - (ConstantUtil.USER_TOTAL_TIME_FOR_FIRST_LEVEL + (currentLevel*ConstantUtil.USER_INCREASE_TIME_FROM_SECOND_LEVEL));
			totalLevelTime = totalLevelTime + currentTotalLevelTime;
			currentTotalLevelTime = (ConstantUtil.USER_TOTAL_TIME_FOR_FIRST_LEVEL + (currentLevel*ConstantUtil.USER_INCREASE_TIME_FROM_SECOND_LEVEL));
			progressLevel.setLevel(currentLevel+2);
			progressLevel.setCurrentLevelTime(currentLevelTime);
			progressLevel.setCurrentTotalLevelTime(currentTotalLevelTime);
			progressLevel.setTotalSpendTimeByUser(totalSpendTimeByUser);
			log.info(progressLevel.toString());
		}
		return progressLevel;
	}

	public List<User> findTopTenUsers() {
		List<User> topUsersList = new ArrayList<>();
		answerRepository.findTopTenUsers().stream().limit(10).forEach(obj -> {
			userRepository.findByUserId((Integer) obj[0]).ifPresent(user -> {
				user.setUserProgressLevel(fetchUserProgressLevel(user.getUserId()));
				topUsersList.add(user);
			});
		});
		return topUsersList;
	}
}
