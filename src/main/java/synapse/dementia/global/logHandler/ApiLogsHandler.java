package synapse.dementia.global.logHandler;

import static synapse.dementia.domain.logs.constant.LogsConstants.*;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.logs.service.LogsService;

@Component
@Aspect
@RequiredArgsConstructor
public class ApiLogsHandler {

	private static final Logger logger = LoggerFactory.getLogger(ApiLogsHandler.class);
	private final LogsService logsService;

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	@Pointcut("within(synapse.dementia.domain..*) && !execution(* synapse.dementia.domain.logs.service.LogsServiceImpl.getServerIp(..)) && !execution(* synapse.dementia.domain.logs.service.LogsServiceImpl.saveSuccessLogs(..)) && !execution(* synapse.dementia.domain.logs.service.LogsServiceImpl.saveErrorLogs(..))")
	public void onRequest() {
	}

	@Around("synapse.dementia.global.logHandler.ApiLogsHandler.onRequest()")
	public Object requestLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		String serverIp = logsService.getServerIp().orElse(UNKNOWN_SERVER);

		try {
			request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

			if (response == null) {
				logger.warn(HTTP_SERVLET_RESPONSE_NULL);
				return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
			}

			CustomHttpServletResponseWrapper responseWrapper = new CustomHttpServletResponseWrapper(response);

			long start = System.currentTimeMillis();
			Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
			long end = System.currentTimeMillis();
			logsService.saveSuccessLogs(request, responseWrapper, serverIp, start, end);
			return result;
		} catch (IllegalStateException e) {
			logger.error(ILLEGAL_STATE_EXCEPTION_ENCOUNTERED, e);
			return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
		}
	}
}
