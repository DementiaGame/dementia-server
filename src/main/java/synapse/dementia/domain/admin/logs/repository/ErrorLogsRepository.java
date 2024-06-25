package synapse.dementia.domain.admin.logs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import synapse.dementia.domain.admin.logs.domain.ApiErrorLogs;
import synapse.dementia.domain.admin.logs.domain.ApiSuccessLogs;

@Repository
public interface ErrorLogsRepository extends JpaRepository<ApiErrorLogs, Long> {
	@Query("""
			SELECT 
				a
			FROM 
				ApiErrorLogs a
			ORDER BY 
				a.requestTime
			DESC
			""")
	List<ApiErrorLogs> findApiErrorLogsDesc();
}
