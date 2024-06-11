package synapse.dementia.domain.logs.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.logs.domain.ApiErrorLogs;
import synapse.dementia.domain.logs.domain.ApiSuccessLogs;
import synapse.dementia.domain.logs.service.LogsService;

@Controller
@RequiredArgsConstructor
public class LogsController {
	private final LogsService logsService;

	@GetMapping("/v1/admin/success")
	public String getAllSuccessLogs(Model model) {
		List<ApiSuccessLogs> successLogs = logsService.findAllSuccessLogs();
		model.addAttribute("successLogs", successLogs);
		return "successLogs";
	}

	@GetMapping("/v1/admin/error")
	public String getAllFailLogs(Model model) {
		List<ApiErrorLogs> errorLogs = logsService.findAllErrorLogs();
		model.addAttribute("errorLogs", errorLogs);
		return "errorLogs";
	}
}
