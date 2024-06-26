package synapse.dementia.domain.users.game.initialgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synapse.dementia.domain.users.game.initialgame.dto.request.SelectGameTopicRequest;
import synapse.dementia.domain.users.game.initialgame.dto.response.InitialGameQuestionResponse;
import synapse.dementia.domain.users.game.initialgame.service.InitialGameQuestionService;
import synapse.dementia.global.base.BaseResponse;

import java.util.List;

@RestController
@RequestMapping("/api/initial/questions")
public class InitialGameQuestionController {

    private final InitialGameQuestionService initialGameQuestionService;

    @Autowired
    public InitialGameQuestionController(InitialGameQuestionService initialGameQuestionService) {
        this.initialGameQuestionService = initialGameQuestionService;
    }

    @PostMapping("/{userIdx}")
    public ResponseEntity<BaseResponse> getRandomQuestionsByTopic(@PathVariable Long userIdx, @RequestBody SelectGameTopicRequest request) {
        List<InitialGameQuestionResponse> response = initialGameQuestionService.getRandomQuestionsByTopic(userIdx, request);
        return ResponseEntity.ok(BaseResponse.ofSuccess(response));
    }
}
