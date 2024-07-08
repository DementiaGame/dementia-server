package synapse.dementia.domain.users.game.multiplayergame.dto.response;

public record GameStartResponse(Long gameId, Long roomId, boolean isStarted) {
}
