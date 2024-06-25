package synapse.dementia.domain.admin.logs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import synapse.dementia.domain.admin.logs.domain.ApiSuccessLogs;

@Repository
public interface SuccessLogsRepository extends JpaRepository<ApiSuccessLogs, Long> {
}
