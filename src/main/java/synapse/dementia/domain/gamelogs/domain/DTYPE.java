package synapse.dementia.domain.gamelogs.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DTYPE {
	INITIAL,
	MEMORY,
	MULTI;

	private String DTYPE;
}
