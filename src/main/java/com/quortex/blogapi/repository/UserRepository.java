package com.quortex.blogapi.repository;
import org.springframework.data.repository.CrudRepository;

import com.quortex.blogapi.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{

}
