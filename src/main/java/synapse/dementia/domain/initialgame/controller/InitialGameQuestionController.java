package synapse.dementia.domain.initialgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synapse.dementia.domain.initialgame.dto.request.SelectGameTopicRequest;
import synapse.dementia.domain.initialgame.dto.response.InitialGameQuestionResponse;
import synapse.dementia.domain.initialgame.service.InitialGameQuestionService;
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

    @PostMapping
    public ResponseEntity<BaseResponse<List<InitialGameQuestionResponse>>> getRandomQuestionsByTopic(@RequestBody SelectGameTopicRequest request) {
        List<InitialGameQuestionResponse> response = initialGameQuestionService.getRandomQuestionsByTopic(request);
        return ResponseEntity.ok(BaseResponse.ofSuccess(response));
    }
}
