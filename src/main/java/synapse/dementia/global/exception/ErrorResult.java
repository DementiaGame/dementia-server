package synapse.dementia.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorResult {

	// BAD_REQUEST
	DTO_Request_BAD_REQUEST(HttpStatus.BAD_REQUEST, "DTO_Request_BAD_REQUEST", "DRB001"),
	PASSWORD_MISMATCH_BAD_REQUEST(HttpStatus.BAD_REQUEST, "PASSWORD_MISMATCH_BAD_REQUEST", "PMB002"),
	SECOND_PASSWORD_MISMATCH_BAD_REQUEST(HttpStatus.BAD_REQUEST, "SECOND_PASSWORD_MISMATCH_BAD_REQUEST", "SPM003"),
	USER_NOT_EXIST_BAD_REQUEST(HttpStatus.BAD_REQUEST, "USER_NOT_EXIST_BAD_REQUEST", "NNB004"),
	GAME_NOT_EXIST_BAD_REQUEST(HttpStatus.BAD_REQUEST, "GAME_NOT_EXIST_BAD_REQUEST", "GNB005"),

	// Conflict
	NICK_NAME_DUPLICATION_CONFLICT(HttpStatus.CONFLICT, "NICK_NAME_DUPLICATION_CONFLICT", "NNDC001"),

	// SERVER
	UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Exception", "S500"),
	;

	private final HttpStatus httpStatus;
	private final String message;
	private final String code;
}
