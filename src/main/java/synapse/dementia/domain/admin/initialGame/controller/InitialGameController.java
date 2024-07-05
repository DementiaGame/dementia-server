package synapse.dementia.domain.admin.initialGame.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.admin.excel.model.ExcelData;
import synapse.dementia.domain.admin.initialGame.dto.request.AddInitialGameDataReq;
import synapse.dementia.domain.admin.initialGame.service.InitialGameService;
import synapse.dementia.global.exception.ConflictException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/initial-game")
public class InitialGameController {
	private final InitialGameService initialGameService;

	@GetMapping()
	public String initialGame(
		@RequestParam(value = "topic", required = false, defaultValue = "동물1(육지)") String topic,
		Model model
	) {
		List<ExcelData> topicData = initialGameService.getAllInitialGameDataByTopic(topic);
		model.addAttribute("topicData", topicData);

		return "admin/initial-game";
	}

	@PostMapping("/add")
	public ResponseEntity<String> addInitialGameData(
		@RequestBody AddInitialGameDataReq addInitialGameDataReq
	) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		headers.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));

		try {
			initialGameService.addInitialGameData(addInitialGameDataReq);
			return new ResponseEntity<>("데이터가 성공적으로 추가됐습니다.", headers, HttpStatus.OK);
		} catch (ConflictException e) {
			return new ResponseEntity<>("이미 저장되어 있는 데이터입니다.", headers, HttpStatus.CONFLICT);
		} catch (Exception e) {
			String errorMessage = "데이터 추가 중 오류가 발생했습니다: " + e.getMessage();
			return new ResponseEntity<>(errorMessage, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
