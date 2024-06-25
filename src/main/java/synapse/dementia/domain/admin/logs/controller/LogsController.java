package synapse.dementia.domain.admin.logs.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String allLogs(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size,
		Model model
	) {
		List<MemberDto> users = memberService.findAllUsers();
		model.addAttribute("users", users);

		Pageable pageable = PageRequest.of(page, size);

		Page<ApiSuccessLogs> successLogsPage = logsService.findAllSuccessLogs(pageable);
		model.addAttribute("successLogs", successLogsPage.getContent());
		model.addAttribute("successLogsPage", successLogsPage);

		Page<ApiErrorLogs> errorLogsPage = logsService.findAllErrorLogs(pageable);
		model.addAttribute("errorLogs", errorLogsPage.getContent());
		model.addAttribute("errorLogsPage", errorLogsPage);

		return "admin/all-logs";
	}

	@GetMapping("/success-logs")
	public String getAllSuccessLogs(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size,
		Model model
	) {
		Pageable pageable = PageRequest.of(page, size);

		Page<ApiSuccessLogs> successLogsPage = logsService.findAllSuccessLogs(pageable);
		model.addAttribute("successLogs", successLogsPage.getContent());
		model.addAttribute("successLogsPage", successLogsPage);

		return "admin/success-logs";
	}

	@GetMapping("/error-logs")
	public String getAllErrorLogs(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size,
		Model model
	) {
		Pageable pageable = PageRequest.of(page, size);

		Page<ApiErrorLogs> errorLogsPage = logsService.findAllErrorLogs(pageable);
		model.addAttribute("errorLogs", errorLogsPage.getContent());
		model.addAttribute("errorLogsPage", errorLogsPage);

		return "admin/error-logs";
	}
}
