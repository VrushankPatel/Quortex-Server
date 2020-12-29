package com.quortex.blogapi.service;

import java.util.ArrayList;
import java.util.List;

import com.quortex.blogapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quortex.blogapi.model.User;

@Service
public class UserService {

	@Autowired
    UserRepository userRepository;

	public List<User> getAllUser() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(student -> users.add(student));
		return users;
	}

	public User getUserById(int id) {
		return userRepository.findById(id).get();
	}

	public void saveOrUpdate(User student) {
		userRepository.save(student);
	}

	public void delete(int id) {
		userRepository.deleteById(id);
	}
}
