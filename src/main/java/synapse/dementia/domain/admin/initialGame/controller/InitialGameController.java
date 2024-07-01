package synapse.dementia.domain.admin.initialGame.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.admin.excel.model.ExcelData;
import synapse.dementia.domain.admin.initialGame.repository.InitialGameRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class InitialGameController {
	private final InitialGameRepository initialGameRepository;

	@GetMapping("/initial-game")
	public String initialGame(
		@RequestParam(value = "topic", required = false, defaultValue = "동물1(육지)") String topic,
		Model model
	) {
		List<ExcelData> topicData = initialGameRepository.findAllByTopic(topic);
		model.addAttribute("topicData", topicData);

		return "admin/initial-game";
	}
}
