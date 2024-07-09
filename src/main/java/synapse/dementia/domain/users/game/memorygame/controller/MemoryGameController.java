package synapse.dementia.domain.users.game.memorygame.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import synapse.dementia.domain.users.game.memorygame.dto.response.MemoryGameLevelInfoResponse;
import synapse.dementia.domain.users.game.memorygame.service.MemoryGameService;
import synapse.dementia.global.base.BaseResponse;

@RestController
@RequestMapping("/api/memorygame")
public class MemoryGameController {
	private final MemoryGameService memoryGameService;

	public MemoryGameController(MemoryGameService memoryGameService) {
		this.memoryGameService = memoryGameService;
	}

	@GetMapping()
	public BaseResponse getGameLevelInfo() {
		// 난이도 선택 화면에서 호출
		MemoryGameLevelInfoResponse memoryGameLevelInfoResponse = memoryGameService.getGameLevelInfo();
		BaseResponse<MemoryGameLevelInfoResponse> response = BaseResponse.ofSuccess(memoryGameLevelInfoResponse);
		return response;
	}

	// @GetMapping("/{level}")
	// public BaseResponse getQuestionByLevel(@PathVariable Integer level) {
	// 	// 선택한 난이도의 게임 데이터 호출
	// 	MemoryGameQuestionResponse memoryGameQuestionResponse = memoryGameService.getQuestionByLevel(level);
	// 	BaseResponse<MemoryGameQuestionResponse> response = BaseResponse.ofSuccess(memoryGameQuestionResponse);
	// 	return response;
	// }
}
