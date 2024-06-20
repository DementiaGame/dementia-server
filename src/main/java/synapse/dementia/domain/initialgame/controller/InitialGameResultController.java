package synapse.dementia.domain.initialgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import synapse.dementia.domain.initialgame.dto.request.InitialGameResultRequest;
import synapse.dementia.domain.initialgame.dto.response.InitialGameResultResponse;
import synapse.dementia.domain.initialgame.service.InitialGameResultService;
import synapse.dementia.global.base.BaseResponse;

@RestController
@RequestMapping("/api/initial/results")
public class InitialGameResultController {

    private final InitialGameResultService initialGameResultService;

    @Autowired
    public InitialGameResultController(InitialGameResultService initialGameResultService) {
        this.initialGameResultService = initialGameResultService;
    }

    @PostMapping("/check")
    public BaseResponse<InitialGameResultResponse> checkAnswer(@RequestBody InitialGameResultRequest request) {
        InitialGameResultResponse response = initialGameResultService.checkAnswer(request);
        return BaseResponse.ofSuccess(response);
    }
}
