package synapse.dementia.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorResult {

	// CLIENT
	DTO_Request_BAD_REQUEST(HttpStatus.BAD_REQUEST, "DTO_Request_BAD_REQUEST", "DRB001"),
	NICK_NAME_DUPLICATION_CONFLICT(HttpStatus.CONFLICT, "NICK_NAME_DUPLICATION_CONFLICT", "NNDC001"),
	PASSWORD_MISMATCH_BAD_REQUEST(HttpStatus.BAD_REQUEST, "PASSWORD_MISMATCH_BAD_REQUEST", "PMB002"),
	// SERVER
	UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Exception", "S500"),
	;

	private final HttpStatus httpStatus;
	private final String message;
	private final String code;
}
