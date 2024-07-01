package synapse.dementia.domain.admin.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import synapse.dementia.domain.admin.excel.model.ExcelData;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExcelDataRepository extends JpaRepository<ExcelData, Long> {
	@Query("SELECT DISTINCT e.topic FROM ExcelData e")
	List<String> findDistinctTopics();

	List<ExcelData> findByTopic(String topic);

	@Query("""
		 SELECT 
		     e
		 FROM 
		     ExcelData e
		 WHERE 
		     e.topic = :topic
		     AND 
		     e.answer = :answer
		     AND 
		     e.question = :question
		           
		""")
	Optional<ExcelData> findExcelDataByQuestionAndAnswerAndTopic(
		@Param("topic") String topic,
		@Param("answer") String answer,
		@Param("question") String question
	);
}
