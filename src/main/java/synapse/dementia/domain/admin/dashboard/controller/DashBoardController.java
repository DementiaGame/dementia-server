package synapse.dementia.domain.admin.dashboard.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.admin.logs.domain.ApiErrorLogs;
import synapse.dementia.domain.admin.logs.domain.ApiSuccessLogs;
import synapse.dementia.domain.admin.logs.service.LogsService;
import synapse.dementia.domain.admin.member.dto.MemberDto;
import synapse.dementia.domain.admin.member.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class DashBoardController {
	private final MemberService memberService;
	private final LogsService logsService;

	@GetMapping()
	public String getDashboard(Model model) {
		List<MemberDto> users = memberService.findAllUsers();
		model.addAttribute("users", users);

		List<ApiSuccessLogs> successLogs = logsService.findAllSuccessLogs();
		model.addAttribute("successLogs", successLogs);

		List<ApiErrorLogs> errorLogs = logsService.findAllErrorLogs();
		model.addAttribute("errorLogs", errorLogs);

		return "admin/dashboard";
	}
}
