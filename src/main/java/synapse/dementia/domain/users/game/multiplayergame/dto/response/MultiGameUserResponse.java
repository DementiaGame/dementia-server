package synapse.dementia.domain.users.game.multiplayergame.dto.response;

public record MultiGameUserResponse(Long idx, Long roomIdx, Long usersIdx) {} // 수정: userIdx를 userId로 변경

