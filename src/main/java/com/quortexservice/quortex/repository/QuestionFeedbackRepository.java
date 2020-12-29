package com.quortexservice.quortex.repository;

import java.util.List;

import com.quortexservice.quortex.model.QuestionFeedback;
import org.springframework.data.repository.CrudRepository;

public interface QuestionFeedbackRepository extends CrudRepository<QuestionFeedback, Integer>{

	List<QuestionFeedback> findByQuestionIdAndUserId(Integer questionId, Integer userId);
	Integer countByQuestionIdAndLiked(Integer questionId, Boolean liked);
	Integer countByQuestionIdAndUnliked(Integer questionId, Boolean unliked);
}
