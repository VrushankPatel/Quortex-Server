package com.quortexservice.quortex.repository;
import java.util.Optional;

import com.quortexservice.quortex.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>{
	Optional<User> findByEmail(String email);
	Optional<User> findByUserId(Integer userId);
}
