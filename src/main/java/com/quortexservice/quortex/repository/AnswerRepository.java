package com.quortexservice.quortex.repository;

import java.util.List;
import java.util.Optional;

import com.quortexservice.quortex.model.Answer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository  extends CrudRepository<Answer, Integer>{

	List<Answer> findByQuestionIdOrderByCreateDateDesc(Integer questionId);
	List<Answer> findByUserIdOrderByCreateDateDesc(Integer userId);
	//Optional<Answer> findByQuestionIdAndUserId(Integer questionId, Integer userId);
	Optional<Answer> findByAnswerId(Integer answerId);
	Integer countByQuestionId(Integer questionId);
	
	@Query(value = "SELECT sum(a.timeTaken) FROM Answer a where a.userId = ?1")
	Long sumTimeTakenByQuestionId(Integer userId);
	
	@Query(value = "select userId, sum(timeTaken) as timeTaken from Answer group by userId order by timeTaken desc")
	List<Object[]> findTopTenUsers();
}
