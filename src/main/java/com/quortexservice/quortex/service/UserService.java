package com.quortexservice.quortex.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.quortexservice.quortex.exception.QuortexException;
import com.quortexservice.quortex.util.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.quortexservice.quortex.model.AuthenticationRequest;
import com.quortexservice.quortex.model.AuthenticationResponse;
import com.quortexservice.quortex.model.QuortexResponse;
import com.quortexservice.quortex.model.Role;
import com.quortexservice.quortex.model.User;
import com.quortexservice.quortex.model.UserProgressLevel;
import com.quortexservice.quortex.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

	@Autowired
	private AuthenticationManager authenticationManager;

	private JwtUtil jwtUtil = new JwtUtil();

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private QuestionService questionService;
	
	@Lazy
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private NotificationService notificationService;
	
	@Value("${spring.mail.username}")
	private String fromEmail;

	@Value("${login.url}")
	private String loginUrl;
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Override
	public UserDetails loadUserByUsername(String email) {
		Optional<User> user = userRepository.findByEmail(email);

		user.orElseThrow(() -> new QuortexException(ConstantUtil.USER_NOFOUNT_ERROR_MESSAGE,ConstantUtil.USER_NOFOUNT_ERROR_CODE));

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.get().getRole().toString()));
		return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(),
				authorities);
	}

	public AuthenticationResponse createAuthenticationToken(AuthenticationRequest authenticationRequest)
			throws QuortexException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException | InternalAuthenticationServiceException e) {
			throw new QuortexException(ConstantUtil.INCORRECT_LOGIN_ERROR_MESSAGE,ConstantUtil.INCORRECT_LOGIN_ERROR_CODE);
		}

		UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());
		Optional<User> user = userRepository.findByEmail(authenticationRequest.getUsername());
		UserProgressLevel userProgressLevel = questionService.fetchUserProgressLevel(user.get().getUserId());
		return new AuthenticationResponse(jwtUtil.generateToken(userDetails),ConstantUtil.SUCCESS_CODE, true,user.get().getUserId(),user.get().getRole(), userProgressLevel);
	}

	public ResponseEntity<Object> createUser(User user) throws QuortexException {
		Optional<User> userExist = userRepository.findByEmail(user.getEmail());
		if (userExist.isPresent()) throw new QuortexException(ConstantUtil.EMAIL_ERROR_MESSAGE,ConstantUtil.EMAIL_ERROR_CODE);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(Role.USER); // Back-end api will only allow to create user with "USER" role
		userRepository.save(user);
		log.info("User created ["+user.toString()+"]");
		String text = "<p>Hi "+user.getNickName() + "!</p><p>Hope you are doing well amid the pandemic.</p><p>Welcome to Quortex. We're delighted to have you here!</p><p>Login <a href=\""+loginUrl+"\">Here</a>.</p><p>For any questions, feedback, or concerns, shoot us an email at: <a href=\"mailto:"+fromEmail+",\">"+fromEmail+",</a></p><p>Thanks,</p><p>Quortex Support Team</p>";
		notificationService.sendNotification(user.getEmail(), "New Account Created in Quortex", text);
		return new ResponseEntity<>(new QuortexResponse(ConstantUtil.USER_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true,null), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> updateUserProfile(User user) throws QuortexException {
		Optional<User> userExist = userRepository.findByUserId(user.getUserId());
		if (userExist.isPresent()) {
			user.setPassword(StringUtils.hasText(user.getPassword())? passwordEncoder.encode(user.getPassword()):userExist.get().getPassword());
			if(user.getEmail()!=null && !user.getEmail().isEmpty() && !userExist.get().getEmail().equals(user.getEmail())) {
				Optional<User> emailExist = userRepository.findByEmail(user.getEmail());
				if(emailExist.isPresent()) {
					return new ResponseEntity<>(new QuortexResponse(ConstantUtil.EMAIL_ERROR_MESSAGE,ConstantUtil.EMAIL_ERROR_CODE,true,null), HttpStatus.OK);
				}
			}
			userRepository.save(user);
			log.info("User profile updated ["+user.toString()+"]");
			String text = "<p>Hi "+user.getNickName() + "!</p><p>Your profile was recently updated in Quortex.</p><p>Login <a href=\""+loginUrl+"\">Here</a>.</p><p>For any questions, feedback, or concerns, shoot us an email at: <a href=\"mailto:"+fromEmail+",\">"+fromEmail+",</a></p><p>Thanks,</p><p>Quortex Support Team</p>";
			notificationService.sendNotification(user.getEmail(), "Profile Recently Updated in Quortex", text);
			return new ResponseEntity<>(new QuortexResponse(ConstantUtil.USER_PROFILE_UPDATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true,null), HttpStatus.OK);
		}
		log.info("User profile not found ["+user.toString()+"]");
		return new ResponseEntity<>(new QuortexResponse(ConstantUtil.USER_PROFILE_NOT_FOUND_MESSAGE,ConstantUtil.FAILURE_CODE,true,null), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> resetUserPassword(String email) throws QuortexException {
		Optional<User> userExist = userRepository.findByEmail(email);
		if (userExist.isPresent()) {
			User user = userExist.get();
			String newPassword = "Welcome" + ((int)(Math.random()*1000));
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);
			log.info("User found ["+user.toString()+"]");
			String text = "<p>Hi "+user.getNickName() + "!</p><p>Don't worry if you forgot your password. It happens to all of us :)</p><p>Use password [<strong><em>"+newPassword+"</em></strong>] to login in the Quortex and change the password in the Edit Profile tab for security reasons.</p><p>Login <a href=\""+loginUrl+"\">Here</a>.</p><p>For any questions, feedback, or concerns, shoot us an email at: <a href=\"mailto:"+fromEmail+",\">"+fromEmail+",</a></p><p>Thanks,</p><p>Quortex Support Team</p>";
			notificationService.sendNotification(user.getEmail(), "Quest Account Password Reset", text);
			return new ResponseEntity<>(new QuortexResponse(ConstantUtil.USER_PASSWARD_RESET_MESSAGE,ConstantUtil.SUCCESS_CODE,true,null), HttpStatus.OK);
		}
		log.info("User not found ["+email+"]");
		return new ResponseEntity<>(new QuortexResponse(ConstantUtil.USER_PROFILE_NOT_FOUND_MESSAGE,ConstantUtil.FAILURE_CODE,true,null), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> getUserdetails(Integer userId) throws QuortexException {
		log.info("Fetching record for userid["+userId+"]");
		Optional<User> user = userRepository.findById(userId);
		return new ResponseEntity<>(user.get(), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> getFullUserdetails(Integer userId) throws QuortexException {
		log.info("Fetching record for userid["+userId+"]");
		User user = userRepository.findById(userId).get();
		user.setQuestionList(questionService.findAllQuestionsByUser(userId));
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	public String extractUsername(String token) {
		return jwtUtil.extractUsername(token);
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		return jwtUtil.validateToken(token, userDetails);
	}

}

class JwtUtil {

	private String SECRET_KEY = "secret";

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
