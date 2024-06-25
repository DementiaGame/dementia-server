package synapse.dementia.domain.admin.logs.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.admin.logs.domain.ApiErrorLogs;
import synapse.dementia.domain.admin.logs.service.LogsService;
import synapse.dementia.domain.admin.logs.domain.ApiSuccessLogs;
import synapse.dementia.domain.admin.member.dto.MemberDto;
import synapse.dementia.domain.admin.member.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class LogsController {
	private final LogsService logsService;
	private final MemberService memberService;

	@GetMapping("/all-logs")
	public String allLogs(Model model) {
		List<MemberDto> users = memberService.findAllUsers();
		model.addAttribute("users", users);

		List<ApiSuccessLogs> successLogs = logsService.findAllSuccessLogs();
		model.addAttribute("successLogs", successLogs);

		List<ApiErrorLogs> errorLogs = logsService.findAllErrorLogs();
		model.addAttribute("errorLogs", errorLogs);

		return "admin/all-logs";
	}

	@GetMapping("/success-logs")
	public String getAllSuccessLogs(Model model) {
		List<ApiSuccessLogs> successLogs = logsService.findAllSuccessLogs();

		model.addAttribute("successLogs", successLogs);
		return "admin/success-logs";
	}

	@GetMapping("/error-logs")
	public String getAllFailLogs(Model model) {
		List<ApiErrorLogs> errorLogs = logsService.findAllErrorLogs();

		model.addAttribute("errorLogs", errorLogs);
		return "admin/error-logs";
	}
}
