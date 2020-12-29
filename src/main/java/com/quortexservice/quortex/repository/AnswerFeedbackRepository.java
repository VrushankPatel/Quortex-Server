package com.quortexservice.quortex.repository;

import java.util.List;

import com.quortexservice.quortex.model.AnswerFeedback;
import org.springframework.data.repository.CrudRepository;

public interface AnswerFeedbackRepository extends CrudRepository<AnswerFeedback, Integer>{

	List<AnswerFeedback> findByAnswerIdAndUserId(Integer answerId, Integer userId);
	List<AnswerFeedback> findByReportDescNotNull();
	List<AnswerFeedback> findByAnswerIdAndReportDescNotNull(Integer answerId);
	//Integer countByAnswerIdAndReportDescNotNull(Integer answerId);
	Integer countByAnswerIdAndLiked(Integer answerId, Boolean liked);
	Integer countByAnswerIdAndUnliked(Integer answerId, Boolean unliked);
}
