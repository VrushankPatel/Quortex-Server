package com.quortex.blogapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quortex.blogapi.model.User;
import com.quortex.blogapi.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

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
