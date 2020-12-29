package com.qaservice.Quortex.repository;
import org.springframework.data.repository.CrudRepository;

import com.qaservice.Quortex.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{

}
