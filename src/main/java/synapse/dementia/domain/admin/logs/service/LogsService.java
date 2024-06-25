package synapse.dementia.domain.admin.logs.service;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import synapse.dementia.domain.admin.logs.domain.ApiErrorLogs;
import synapse.dementia.domain.admin.logs.domain.ApiSuccessLogs;
import synapse.dementia.domain.admin.config.logs.CustomHttpServletResponseWrapper;

public interface LogsService {
	List<ApiErrorLogs> findAllErrorLogs();

	List<ApiSuccessLogs> findAllSuccessLogs();

	void saveSuccessLogs(
		HttpServletRequest request,
		CustomHttpServletResponseWrapper responseWrapper,
		String serverIp,
		long start,
		long end
	);

	Optional<String> getServerIp();

	void saveErrorLogs(
		HttpServletRequest request,
		int status,
		String errorMessage
	);
}
