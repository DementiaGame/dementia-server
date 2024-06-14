package synapse.dementia.global.exception;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import synapse.dementia.domain.logs.domain.ApiErrorLogs;
import synapse.dementia.domain.logs.repository.ErrorLogsRepository;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final ErrorLogsRepository logsErrorRepository;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers,
		HttpStatusCode status,
		WebRequest request) {
		final List<String> errorList = ex.getBindingResult()
			.getAllErrors()
			.stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.collect(Collectors.toList());

		log.warn("Invalid DTO Parameter errors : {}", errorList);
		saveErrorLog((HttpServletRequest)request, HttpStatus.BAD_REQUEST.value(), errorList.toString());
		return makeErrorResponseEntity(errorList.toString());
	}

	@ExceptionHandler({ServerException.class})
	public ResponseEntity<ErrorResponse> handleServerException(final ServerException exception,
		HttpServletRequest request, HttpServletResponse response) {
		log.warn("ServerException occur: ", exception);
		saveErrorLog(request, exception.getErrorResult().getHttpStatus().value(),
			exception.getErrorResult().getMessage());
		response.setStatus(exception.getErrorResult().getHttpStatus().value());
		return makeErrorResponseEntity(exception.getErrorResult());
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> handleException(final Exception exception, HttpServletRequest request,
		HttpServletResponse response) {
		log.warn("Exception occur: ", exception);
		saveErrorLog(request, HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorResult.UNKNOWN_EXCEPTION.getMessage());
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return makeErrorResponseEntity(ErrorResult.UNKNOWN_EXCEPTION);
	}

	@ExceptionHandler({NotFoundException.class})
	public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException exception,
		HttpServletRequest request, HttpServletResponse response) {
		log.warn("NotFoundException occur: ", exception);
		saveErrorLog(request, exception.getErrorResult().getHttpStatus().value(),
			exception.getErrorResult().getMessage());
		response.setStatus(exception.getErrorResult().getHttpStatus().value());
		return makeErrorResponseEntity(exception.getErrorResult());
	}

	@ExceptionHandler({DuplicateNickNameException.class})
	public ResponseEntity<ErrorResponse> handleDuplicateNickNameException(final DuplicateNickNameException exception,
		HttpServletRequest request, HttpServletResponse response) {
		log.warn("DupicateNickNameException occur: ", exception);
		saveErrorLog(request, exception.getErrorResult().getHttpStatus().value(),
			exception.getErrorResult().getMessage());
		response.setStatus(exception.getErrorResult().getHttpStatus().value());
		return makeErrorResponseEntity(exception.getErrorResult());
	}

	@ExceptionHandler({MismatchPasswordException.class})
	public ResponseEntity<ErrorResponse> handleMismatchPasswordException(final MismatchPasswordException exception,
		HttpServletRequest request, HttpServletResponse response) {
		log.warn("MismatchPasswordException occur: ", exception);
		saveErrorLog(request, exception.getErrorResult().getHttpStatus().value(),
			exception.getErrorResult().getMessage());
		response.setStatus(exception.getErrorResult().getHttpStatus().value());
		return makeErrorResponseEntity(exception.getErrorResult());
	}

	// <-- Exception 정의

	private void saveErrorLog(HttpServletRequest request, int status, String errorMessage) {
		String serverIp = getServerIp().orElse("Unknown");
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

		logsErrorRepository.save(logError);
	}

	private Optional<String> getServerIp() {
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
			log.error("Error while getting server IP address", e);
		}
		return Optional.empty();
	}

	private ResponseEntity<Object> makeErrorResponseEntity(final String errorDescription) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), errorDescription, "B001"));
	}

	private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final ErrorResult errorResult) {
		return ResponseEntity.status(errorResult.getHttpStatus())
			.body(new ErrorResponse(errorResult.getHttpStatus().toString(), errorResult.getMessage(),
				errorResult.getCode()));
	}

	@Getter
	@RequiredArgsConstructor
	public static class ErrorResponse {
		private final String status;
		private final String message;
		private final String code;
	}
}
