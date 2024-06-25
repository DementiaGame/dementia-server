package synapse.dementia.domain.users.game.initialgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synapse.dementia.domain.users.game.initialgame.dto.request.InitialGameResultRequest;
import synapse.dementia.domain.users.game.initialgame.dto.response.InitialGameResultResponse;
import synapse.dementia.domain.users.game.initialgame.service.InitialGameResultService;
import synapse.dementia.global.base.BaseResponse;

@RestController
@RequestMapping("/api/initial/results")
public class InitialGameResultController {

    private final InitialGameResultService initialGameResultService;

    @Autowired
    public InitialGameResultController(InitialGameResultService initialGameResultService) {
        this.initialGameResultService = initialGameResultService;
    }

    @PostMapping("/check/{userIdx}/{questionIdx}")
    public ResponseEntity<BaseResponse> checkAnswer(@PathVariable Long userIdx, @PathVariable Long questionIdx, @RequestBody InitialGameResultRequest request) {
        InitialGameResultResponse response = initialGameResultService.checkAnswer(userIdx, questionIdx, request);
        return ResponseEntity.ok(BaseResponse.ofSuccess(response));
    }

    @GetMapping("/total-correct/{userIdx}")
    public ResponseEntity<BaseResponse> getTotalCorrectAnswers(@PathVariable Long userIdx) {
        int totalCorrect = initialGameResultService.getTotalCorrectAnswers(userIdx);
        return ResponseEntity.ok(BaseResponse.ofSuccess(totalCorrect));
    }
}
