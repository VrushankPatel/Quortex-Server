package com.qaservice.Quortex.controller;

import java.util.List;

import com.qaservice.Quortex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qaservice.Quortex.model.User;


@RestController
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/")
	private String showWelcomeMsg() {
		return "Welcome to Questa Back-end APIs....";
	}
	
	@GetMapping("/user")
	private List<User> getAllUser() {
		return userService.getAllUser();
	}

	@GetMapping("/user/{id}")
	private User getUser(@PathVariable("id") int id) {
		return userService.getUserById(id);
	}

	@DeleteMapping("/student/{id}")
	private void deleteStudent(@PathVariable("id") int id) {
		userService.delete(id);
	}

	@PostMapping("/student")
	private int saveStudent(@RequestBody User student) {
		userService.saveOrUpdate(student);
		return student.getId();
	}
}
