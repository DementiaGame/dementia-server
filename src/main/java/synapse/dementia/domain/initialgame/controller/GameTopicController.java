package synapse.dementia.domain.initialgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synapse.dementia.domain.initialgame.dto.response.InitialGameTopicResponse;
import synapse.dementia.domain.initialgame.service.GameTopicService;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class GameTopicController {

    private final GameTopicService gameTopicService;

    @Autowired
    public GameTopicController(GameTopicService gameTopicService) {
        this.gameTopicService = gameTopicService;
    }

    @GetMapping
    public ResponseEntity<List<InitialGameTopicResponse>> getAllTopics() {
        List<InitialGameTopicResponse> topics = gameTopicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }
}
