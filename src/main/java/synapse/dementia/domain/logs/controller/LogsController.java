package synapse.dementia.domain.logs.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.logs.domain.ApiErrorLogs;
import synapse.dementia.domain.logs.domain.ApiSuccessLogs;
import synapse.dementia.domain.logs.service.LogsService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class LogsController {
	private final LogsService logsService;

	@GetMapping()
	public String adminDashBoard() {
		return "admin/dashboard";
	}

	@GetMapping("/users")
	public String getAllUsersLogs(Model model) {

		return "admin/users";
	}

	@GetMapping("/logs/success")
	public String getAllSuccessLogs(Model model) {
		List<ApiSuccessLogs> successLogs = logsService.findAllSuccessLogs().stream()
			.sorted((log1, log2) -> log2.getRequestTime().compareTo(log1.getRequestTime()))
			.collect(Collectors.toList());

		model.addAttribute("successLogs", successLogs);
		return "admin/success-logs";
	}

	@GetMapping("/logs/error")
	public String getAllFailLogs(Model model) {
		List<ApiErrorLogs> errorLogs = logsService.findAllErrorLogs().stream()
			.sorted((log1, log2) -> log2.getRequestTime().compareTo(log1.getRequestTime()))
			.collect(Collectors.toList());

		model.addAttribute("errorLogs", errorLogs);
		return "admin/error-logs";
	}
}
