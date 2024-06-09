package synapse.dementia.domain.logs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import synapse.dementia.domain.logs.domain.Logs;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Long> {
	@Transactional
	@Modifying
	@Query("""
		 		UPDATE 
		 			Logs a
		       SET 
		       	a.responseStatus = :status, 
		       	a.response = :response, 
		       	a.responseTime = CURRENT_TIMESTAMP
		       WHERE a.logsSeq = :seq
		""")
	void updateResponse(@Param("seq") Long logsSeq, @Param("status") Integer status,
		@Param("response") String response);
}
