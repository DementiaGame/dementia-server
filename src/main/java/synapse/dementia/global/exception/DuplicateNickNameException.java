package synapse.dementia.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DuplicateNickNameException extends RuntimeException {
	private final ErrorResult errorResult;
}
