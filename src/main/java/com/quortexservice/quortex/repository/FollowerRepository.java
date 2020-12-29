package com.quortexservice.quortex.repository;

import java.util.List;
import java.util.Optional;

import com.quortexservice.quortex.model.Follower;
import org.springframework.data.repository.CrudRepository;

public interface FollowerRepository extends CrudRepository<Follower, Integer>{

	List<Follower> findByQuestionId(Integer questionId);
	List<Follower> findByUserId(Integer userId);
	Optional<Follower> findByQuestionIdAndUserId(Integer questionId, Integer userId);
	Integer countByQuestionId(Integer questionId);
}
