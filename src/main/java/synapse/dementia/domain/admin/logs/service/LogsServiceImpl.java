package synapse.dementia.domain.admin.logs.service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.admin.logs.constant.LogsConstants;
import synapse.dementia.domain.admin.logs.domain.ApiErrorLogs;
import synapse.dementia.domain.admin.logs.domain.ApiSuccessLogs;
import synapse.dementia.domain.admin.logs.repository.ErrorLogsRepository;
import synapse.dementia.domain.admin.logs.repository.SuccessLogsRepository;
import synapse.dementia.domain.admin.config.logs.CustomHttpServletResponseWrapper;

@Service
@RequiredArgsConstructor
public class LogsServiceImpl implements LogsService {
	private static final Logger logger = LoggerFactory.getLogger(LogsServiceImpl.class);
	private final ErrorLogsRepository errorLogsRepository;
	private final SuccessLogsRepository successLogsRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<ApiErrorLogs> findAllErrorLogs(Pageable pageable) {
		return errorLogsRepository.findApiErrorLogsDesc(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ApiSuccessLogs> findAllSuccessLogs(Pageable pageable) {
		return successLogsRepository.findApiSuccessLogsDesc(pageable);
	}

	@Override
	public void saveSuccessLogs(
		HttpServletRequest request,
		CustomHttpServletResponseWrapper responseWrapper,
		String serverIp,
		long start,
		long end
	) {
		String requestURL = request.getRequestURL().toString();

		if (requestURL.contains(LogsConstants.ADMIN_URL) || requestURL.contains(LogsConstants.ACTUATOR_URL)) {
			return;
		}

		ApiSuccessLogs log = ApiSuccessLogs.builder()
			.serverIp(serverIp)
			.requestURL(request.getRequestURL().toString())
			.requestMethod(request.getMethod())
			.responseStatus(responseWrapper.getStatus())
			.clientIp(request.getRemoteAddr())
			.requestTime(LocalDateTime.now())
			.responseTime(LocalDateTime.now().plusNanos(end - start))
			.connectionTime(end - start)
			.build();

		successLogsRepository.save(log);
	}

	@Override
	public void saveErrorLogs(HttpServletRequest request, int status, String errorMessage) {
		String serverIp = getServerIp().orElse(LogsConstants.UNKNOWN_SERVER);
		long start = System.currentTimeMillis();
		long end = System.currentTimeMillis();

		ApiErrorLogs logError = ApiErrorLogs.builder()
			.serverIp(serverIp)
			.requestURL(request.getRequestURL().toString())
			.requestMethod(request.getMethod())
			.responseStatus(status)
			.clientIp(request.getRemoteAddr())
			.requestTime(LocalDateTime.now())
			.responseTime(LocalDateTime.now().plusNanos(end - start))
			.connectionTime(end - start)
			.errorMessage(errorMessage)
			.build();

		errorLogsRepository.save(logError);
	}

	@Override
	public Optional<String> getServerIp() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();
				Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress inetAddress = inetAddresses.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
						return Optional.of(inetAddress.getHostAddress());
					}
				}
			}
		} catch (Exception e) {
			logger.error(LogsConstants.IP_ADDRESS_EXCEPTION, e);
		}
		return Optional.empty();
	}
}
