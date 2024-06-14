package synapse.dementia.domain.logs.service;

import java.util.List;

import synapse.dementia.domain.logs.domain.ApiErrorLogs;
import synapse.dementia.domain.logs.domain.ApiSuccessLogs;

public interface LogsService {
	List<ApiErrorLogs> findAllErrorLogs();

	List<ApiSuccessLogs> findAllSuccessLogs();
}
