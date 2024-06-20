package synapse.dementia.domain.initialgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import synapse.dementia.domain.initialgame.dto.request.SelectGameTopicRequest;
import synapse.dementia.domain.initialgame.dto.response.InitialGameTopicResponse;
import synapse.dementia.domain.initialgame.dto.response.SelectedGameTopicResponse;
import synapse.dementia.domain.initialgame.service.GameTopicService;
import synapse.dementia.global.base.BaseResponse;

import java.util.List;

@RestController
@RequestMapping("/api/initial/topics")
public class InitialGameTopicController {

    private final GameTopicService gameTopicService;

    @Autowired
    public InitialGameTopicController(GameTopicService gameTopicService) {
        this.gameTopicService = gameTopicService;
    }

    @GetMapping
    public BaseResponse<List<InitialGameTopicResponse>> getAllTopics() {
        List<InitialGameTopicResponse> topics = gameTopicService.getAllTopics();
        return BaseResponse.ofSuccess(topics);
    }

    @PostMapping
    public BaseResponse<SelectedGameTopicResponse> selectTopic(@RequestBody SelectGameTopicRequest request) {
        SelectedGameTopicResponse response = gameTopicService.selectTopic(request);
        return BaseResponse.ofSuccess(response);
    }
}
