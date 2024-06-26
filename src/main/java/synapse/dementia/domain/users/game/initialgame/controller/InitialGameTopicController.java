package synapse.dementia.domain.users.game.initialgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synapse.dementia.domain.users.game.initialgame.dto.request.SelectGameTopicRequest;
import synapse.dementia.domain.users.game.initialgame.dto.response.InitialGameQuestionResponse;
import synapse.dementia.domain.users.game.initialgame.dto.response.InitialGameTopicResponse;
import synapse.dementia.domain.users.game.initialgame.dto.response.SelectedGameTopicResponse;
import synapse.dementia.domain.users.game.initialgame.service.InitialGameQuestionService;
import synapse.dementia.domain.users.game.initialgame.service.InitialGameTopicService;
import synapse.dementia.global.base.BaseResponse;

import java.util.List;

@RestController
@RequestMapping("/api/initial/topics")
public class InitialGameTopicController {

    private final InitialGameTopicService initialGameTopicService;
    private final InitialGameQuestionService initialGameQuestionService;

    @Autowired
    public InitialGameTopicController(InitialGameTopicService initialGameTopicService, InitialGameQuestionService initialGameQuestionService) {
        this.initialGameTopicService = initialGameTopicService;
        this.initialGameQuestionService = initialGameQuestionService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAllTopics() {
        List<InitialGameTopicResponse> topics = initialGameTopicService.getAllTopics();
        return ResponseEntity.ok(BaseResponse.ofSuccess(topics));
    }

    @PostMapping("/{userIdx}/select")
    public ResponseEntity<BaseResponse> selectTopic(@PathVariable Long userIdx, @RequestBody SelectGameTopicRequest request) {
        SelectedGameTopicResponse response = initialGameTopicService.selectTopic(userIdx, request);
        return ResponseEntity.ok(BaseResponse.ofSuccess(response));
    }

    @PostMapping("/{userIdx}/select-and-questions")
    public ResponseEntity<BaseResponse> selectTopicAndGetQuestions(@PathVariable Long userIdx, @RequestBody SelectGameTopicRequest request) {
        InitialGameTopicService.SelectAndQuestionsResponse response = initialGameTopicService.selectTopicAndGetQuestions(userIdx, request);
        return ResponseEntity.ok(BaseResponse.ofSuccess(response));
    }
}
