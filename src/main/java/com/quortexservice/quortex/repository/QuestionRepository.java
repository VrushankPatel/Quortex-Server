package com.quortexservice.quortex.repository;

import java.util.List;
import java.util.Optional;

import com.quortexservice.quortex.model.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Integer>{
	List<Question> findByUserIdOrderByCreateDateDesc(Integer userId);
	List<Question> findBySubjectAndTopicIgnoreCaseContainingAndQuestionDescIgnoreCaseContainingOrderByCreateDateDesc(String subject, String topic, String questionDesc);
	List<Question> findByTopicIgnoreCaseContainingAndQuestionDescIgnoreCaseContainingOrderByCreateDateDesc(String topic, String questionDesc);
	List<Question> findByOrderByCreateDateDesc();
	List<Question> findDistinctByQuestionIdInOrderByCreateDateDesc(List<Integer> questionId);
	Optional<Question> findByQuestionId(Integer questionId);
}
