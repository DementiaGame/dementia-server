package synapse.dementia.domain.logs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import synapse.dementia.domain.logs.domain.ApiErrorLogs;

@Repository
public interface ErrorLogsRepository extends JpaRepository<ApiErrorLogs, Long> {
}
