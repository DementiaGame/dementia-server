package synapse.dementia.domain.initialgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synapse.dementia.domain.initialgame.dto.request.SelectGameTopicRequest;
import synapse.dementia.domain.initialgame.dto.response.InitialGameTopicResponse;
import synapse.dementia.domain.initialgame.dto.response.SelectedGameTopicResponse;
import synapse.dementia.domain.initialgame.service.InitialGameTopicService;
import synapse.dementia.global.base.BaseResponse;

import java.util.List;

@RestController
@RequestMapping("/api/initial/topics")
public class InitialGameTopicController {

    private final InitialGameTopicService initialGameTopicService;

    @Autowired
    public InitialGameTopicController(InitialGameTopicService initialGameTopicService) {
        this.initialGameTopicService = initialGameTopicService;
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
}
