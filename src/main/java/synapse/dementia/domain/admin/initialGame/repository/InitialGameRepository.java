package synapse.dementia.domain.admin.initialGame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import synapse.dementia.domain.admin.excel.model.ExcelData;

@Repository
public interface InitialGameRepository extends JpaRepository<ExcelData, Long> {
	@Query("""
				SELECT 
					ed
				FROM 
					ExcelData ed
				WHERE 
					ed.topic = :topic
			""")
	List<ExcelData> findAllByTopic(@Param("topic") String topic);
}
