package synapse.dementia.domain.logs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.logs.domain.ApiErrorLogs;
import synapse.dementia.domain.logs.domain.ApiSuccessLogs;
import synapse.dementia.domain.logs.repository.ErrorLogsRepository;
import synapse.dementia.domain.logs.repository.SuccessLogsRepository;

@Service
@RequiredArgsConstructor
public class LogsServiceImpl implements LogsService {
	private final ErrorLogsRepository errorLogsRepository;
	private final SuccessLogsRepository successLogsRepository;

	@Override
	@Transactional(readOnly = true)
	public List<ApiErrorLogs> findAllErrorLogs() {
		return errorLogsRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ApiSuccessLogs> findAllSuccessLogs() {
		return successLogsRepository.findAll();
	}
}
