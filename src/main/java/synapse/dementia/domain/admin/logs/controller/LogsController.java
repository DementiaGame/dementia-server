package synapse.dementia.domain.admin.logs.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.admin.logs.domain.ApiErrorLogs;
import synapse.dementia.domain.admin.logs.service.LogsService;
import synapse.dementia.domain.admin.logs.domain.ApiSuccessLogs;
import synapse.dementia.domain.users.member.dto.UsersDto;
import synapse.dementia.domain.users.member.service.UsersService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class LogsController {
	private final LogsService logsService;
	private final UsersService usersService;

	@GetMapping()
	public String getDashboard(Model model) {
		List<UsersDto> users = usersService.findAllUsers();
		model.addAttribute("users", users);

		List<ApiSuccessLogs> successLogs = logsService.findAllSuccessLogs().stream()
			.sorted((log1, log2) -> log2.getRequestTime().compareTo(log1.getRequestTime()))
			.collect(Collectors.toList());
		model.addAttribute("successLogs", successLogs);

		List<ApiErrorLogs> errorLogs = logsService.findAllErrorLogs().stream()
			.sorted((log1, log2) -> log2.getRequestTime().compareTo(log1.getRequestTime()))
			.collect(Collectors.toList());
		model.addAttribute("errorLogs", errorLogs);

		return "admin/dashboard";
	}

	@GetMapping("/all-logs")
	public String allLogs(Model model) {
		List<UsersDto> users = usersService.findAllUsers();
		model.addAttribute("users", users);

		List<ApiSuccessLogs> successLogs = logsService.findAllSuccessLogs().stream()
			.sorted((log1, log2) -> log2.getRequestTime().compareTo(log1.getRequestTime()))
			.collect(Collectors.toList());
		model.addAttribute("successLogs", successLogs);

		List<ApiErrorLogs> errorLogs = logsService.findAllErrorLogs().stream()
			.sorted((log1, log2) -> log2.getRequestTime().compareTo(log1.getRequestTime()))
			.collect(Collectors.toList());
		model.addAttribute("errorLogs", errorLogs);

		return "admin/all-logs";
	}

	@GetMapping("/users")
	public String getAllUsersLogs(Model model) {
		List<UsersDto> users = usersService.findAllUsers();
		model.addAttribute("users", users);
		return "admin/users";
	}

	@GetMapping("/success-logs")
	public String getAllSuccessLogs(Model model) {
		List<ApiSuccessLogs> successLogs = logsService.findAllSuccessLogs().stream()
			.sorted((log1, log2) -> log2.getRequestTime().compareTo(log1.getRequestTime()))
			.collect(Collectors.toList());

		model.addAttribute("successLogs", successLogs);
		return "admin/success-logs";
	}

	@GetMapping("/error-logs")
	public String getAllFailLogs(Model model) {
		List<ApiErrorLogs> errorLogs = logsService.findAllErrorLogs().stream()
			.sorted((log1, log2) -> log2.getRequestTime().compareTo(log1.getRequestTime()))
			.collect(Collectors.toList());

		model.addAttribute("errorLogs", errorLogs);
		return "admin/error-logs";
	}
}
