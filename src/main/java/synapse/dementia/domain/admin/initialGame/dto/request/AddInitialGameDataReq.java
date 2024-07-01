package synapse.dementia.domain.admin.initialGame.dto.request;

public record AddInitialGameDataReq(
	String topic,
	String answer,
	String question
) {
}
