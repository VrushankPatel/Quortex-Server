package com.quortexservice.quortex.util;

public class ConstantUtil {
	
	public static final Long USER_TOTAL_TIME_FOR_FIRST_LEVEL = 1800L; // 30 mins each progress level
	public static final Long USER_INCREASE_TIME_FROM_SECOND_LEVEL = 600L; // 10 mins each progress level
	
	//UserController end-points
	public static final String WELCOME_ENDPOINT = "/";
	public static final String LOGIN_ENDPOINT = "/api/login";
	public static final String SIGNUP_ENDPOINT = "/api/signup";
	public static final String UPDATE_USER_PROFILE_ENDPOINT = "/api/updateuserprofile";
	public static final String RESET_PASSWORD_ENDPOINT = "/api/resetpassword";
	public static final String GETUSER_ENDPOINT = "/api/getuser/{userId}";
	public static final String GETFULLUSER_ENDPOINT = "/api/getfulluser/{userId}";
	
	//QuestionController end-points
	public static final String CREATE_QUESTION_ENDPOINT = "/api/createquestion";
	public static final String CREATE_ANSWER_ENDPOINT = "/api/createanswer";
	public static final String DELETE_ANSWER_ENDPOINT = "/api/deleteanswer";
	public static final String CREATE_FOLLWER_ENDPOINT = "/api/createfollower";
	public static final String CREATE_ANSWER_FEEDBACK_ENDPOINT = "/api/createfeedback";
	public static final String CREATE_QUESTION_FEEDBACK_ENDPOINT = "/api/createquestionfeedback";
	public static final String FIND_ALL_QUESTIONS_ENDPOINT = "/api/findallquestions/{userId}";
	public static final String FIND_ALL_QUESTIONS_FOR_ADMIN_ENDPOINT = "/api/findallquestionsforadmin/{userId}";
	public static final String FIND_ALL_BY_FOLLOWER_ENDPOINT = "/api/findallbyfollower/{userId}";
	public static final String FIND_ALL_BY_ANSWER_ENDPOINT = "/api/findallbyanswer/{userId}";
	public static final String FIND_ALL_BY_SUBJECT_TOPIC_ENDPOINT = "/api/findallbysubjecttopic";
	public static final String FIND_TOP_TEN_USERS_ENDPOINT = "/api/findtoptenusers";
	
	public static final String[] AUTH_IGNORE_ENDPOINT = {WELCOME_ENDPOINT,LOGIN_ENDPOINT,SIGNUP_ENDPOINT,RESET_PASSWORD_ENDPOINT};

	public static final String PRODUCE_APP_JSON = "application/json";

	public static final Integer BACKEND_SERVICE_LIVE_SUCCESS_CODE = 268;

	public static final Integer EMAIL_SERVICE_FAILURE_CODE = 551;
	
	public static final Integer FAILURE_CODE = 550;
	public static final Integer SUCCESS_CODE = 200;
	public static final Integer EMAIL_ERROR_CODE = 531;
	public static final String EMAIL_ERROR_MESSAGE = "Email already exist in Database";
	
	public static final Integer INCORRECT_LOGIN_ERROR_CODE = 532;
	public static final String INCORRECT_LOGIN_ERROR_MESSAGE = "Incorrect username or password";
	
	public static final Integer AUTH_FAILED_ERROR_CODE = 555;
	public static final String AUTH_FAILED_ERROR_MESSAGE = "Authentication failed";
	
	
	public static final Integer USER_NOFOUNT_ERROR_CODE = 533;
	public static final String USER_NOFOUNT_ERROR_MESSAGE = "Username not found";
	
	public static final String USER_CREATED_MESSAGE = "User successfully created...";
	public static final String USER_PROFILE_UPDATED_MESSAGE = "User profile successfully updated...";
	public static final String USER_PASSWARD_RESET_MESSAGE = "User password reset successfully and send mail to user...";
	public static final String USER_PROFILE_NOT_FOUND_MESSAGE = "User profile not found...";
	public static final String QUESTION_CREATED_MESSAGE = "Question successfully created...";
	public static final String ANSWER_CREATED_MESSAGE = "Answer successfully created...";
	public static final String ANSWER_DELETED_MESSAGE = "Answer deleted successfully created...";
	public static final String USER_FEEDBACK_CREATED_MESSAGE = "User feedback successfully created...";
	public static final String FOLLOWER_CREATED_MESSAGE = "Follower successfully created...";
	public static final String FOLLOWER_DELETED_MESSAGE = "Follower successfully deleted...";
	
}
