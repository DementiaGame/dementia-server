package synapse.dementia.global.exception;

import java.util.List;
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
import synapse.dementia.domain.admin.logs.service.LogsService;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final LogsService logsService;

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
		logsService.saveErrorLogs(
			(HttpServletRequest)request,
			HttpStatus.BAD_REQUEST.value(),
			errorList.toString()
		);
		return makeErrorResponseEntity(errorList.toString());
	}

	@ExceptionHandler({ServerException.class})
	public ResponseEntity<ErrorResponse> handleServerException(final ServerException exception,
		HttpServletRequest request, HttpServletResponse response) {

		log.warn("ServerException occur: ", exception);
		logsService.saveErrorLogs(
			request,
			exception.getErrorResult().getHttpStatus().value(),
			exception.getErrorResult().getMessage()
		);
		response.setStatus(exception.getErrorResult().getHttpStatus().value());

		return makeErrorResponseEntity(exception.getErrorResult());
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> handleException(final Exception exception, HttpServletRequest request,
		HttpServletResponse response) {
		log.warn("Exception occur: ", exception);
		logsService.saveErrorLogs(
			request,
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			ErrorResult.UNKNOWN_EXCEPTION.getMessage()
		);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		return makeErrorResponseEntity(ErrorResult.UNKNOWN_EXCEPTION);
	}

	@ExceptionHandler({NotFoundException.class})
	public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException exception,
		HttpServletRequest request, HttpServletResponse response) {
		log.warn("NotFoundException occur: ", exception);
		logsService.saveErrorLogs(
			request,
			exception.getErrorResult().getHttpStatus().value(),
			exception.getErrorResult().getMessage()
		);
		response.setStatus(exception.getErrorResult().getHttpStatus().value());

		return makeErrorResponseEntity(exception.getErrorResult());
	}

	@ExceptionHandler({BadRequestException.class})
	public ResponseEntity<ErrorResponse> handleBadRequestException(final BadRequestException exception,
		HttpServletRequest request, HttpServletResponse response) {
		log.warn("ConflictException occur: ", exception);
		logsService.saveErrorLogs(
			request,
			exception.getErrorResult().getHttpStatus().value(),
			exception.getErrorResult().getMessage()
		);
		response.setStatus(exception.getErrorResult().getHttpStatus().value());

		return makeErrorResponseEntity(exception.getErrorResult());
	}

	@ExceptionHandler({ConflictException.class})
	public ResponseEntity<ErrorResponse> handleConflictException(final ConflictException exception,
		HttpServletRequest request, HttpServletResponse response) {
		log.warn("ConflictException occur: ", exception);
		logsService.saveErrorLogs(
			request,
			exception.getErrorResult().getHttpStatus().value(),
			exception.getErrorResult().getMessage()
		);
		response.setStatus(exception.getErrorResult().getHttpStatus().value());

		return makeErrorResponseEntity(exception.getErrorResult());
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
