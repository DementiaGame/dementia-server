package synapse.dementia.domain.users.game.memorygame.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import synapse.dementia.domain.users.game.memorygame.dto.request.MemoryGameResultRequest;
import synapse.dementia.domain.users.game.memorygame.dto.response.MemoryGameResultResponse;
import synapse.dementia.domain.users.game.memorygame.service.MemoryGameResultService;
import synapse.dementia.global.base.BaseResponse;

@RestController
@RequestMapping("/api/memorygame")
public class MemoryGameResultController {

	private final MemoryGameResultService memoryGameResultService;

	public MemoryGameResultController(MemoryGameResultService memoryGameResultService) {
		this.memoryGameResultService = memoryGameResultService;
	}

	@PostMapping("/{userIdx}")
	public BaseResponse postGameResult(@PathVariable Long userIdx, @RequestBody MemoryGameResultRequest dto) {
		// 난이도 선택 화면에서 호출
		MemoryGameResultResponse memoryGameResultResponse = memoryGameResultService.postGameResult(userIdx, dto);
		BaseResponse<MemoryGameResultResponse> response = BaseResponse.ofSuccess(memoryGameResultResponse);
		return response;
	}
}
