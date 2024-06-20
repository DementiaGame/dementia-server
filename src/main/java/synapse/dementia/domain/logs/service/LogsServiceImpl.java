package synapse.dementia.domain.logs.service;

import static synapse.dementia.domain.logs.constant.LogsConstants.*;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.logs.domain.ApiErrorLogs;
import synapse.dementia.domain.logs.domain.ApiSuccessLogs;
import synapse.dementia.domain.logs.repository.ErrorLogsRepository;
import synapse.dementia.domain.logs.repository.SuccessLogsRepository;
import synapse.dementia.global.logHandler.CustomHttpServletResponseWrapper;

@Service
@RequiredArgsConstructor
public class LogsServiceImpl implements LogsService {
	private static final Logger logger = LoggerFactory.getLogger(LogsServiceImpl.class);
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

	@Override
	public void saveSuccessLogs(
		HttpServletRequest request,
		CustomHttpServletResponseWrapper responseWrapper,
		String serverIp,
		long start,
		long end
	) {
		String requestURL = request.getRequestURL().toString();

		if (requestURL.contains(ADMIN_URL) || requestURL.contains(ACTUATOR_URL)) {
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
		String serverIp = getServerIp().orElse(UNKNOWN_SERVER);
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
			logger.error(IP_ADDRESS_EXCEPTION, e);
		}
		return Optional.empty();
	}
}
