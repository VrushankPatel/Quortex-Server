package com.quortexservice.quortex;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.quortexservice.quortex.model.Role;
import com.quortexservice.quortex.model.User;
import com.quortexservice.quortex.repository.UserRepository;

@Component
public class DataLoader implements ApplicationRunner {

	
	private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

	@Autowired
    private UserRepository userRepository;
    
    @Autowired
	private BCryptPasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
	private String adminEmail;

    @Value("${spring.mail.password}")
	private String adminPswd;

    public void run(ApplicationArguments args) {
    	userRepository.findByEmail(adminEmail).ifPresentOrElse(user -> log.info("Admin["+adminEmail+"] user present in Database::"+ user.toString()), () ->{
    		log.info("Admin["+adminEmail+"] user not exist in Database, Creating Admin user...");
    		User admin = new User();
    		admin.setFirstName("Admin");
    		admin.setLastName("Quortex");
    		admin.setNickName("QuestAdmin");
    		admin.setEmail(adminEmail);
    		admin.setPassword(passwordEncoder.encode(adminPswd));
    		admin.setRole(Role.ADMIN);
    		admin.setBirthdate(new Date(1));
    		admin.setGrade(1);
    		admin.setCountry("India");
    		admin.setSchool("Quortex");
    		userRepository.save(admin);
    	});
    }
}
