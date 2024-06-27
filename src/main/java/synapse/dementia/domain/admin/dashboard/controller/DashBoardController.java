package synapse.dementia.domain.admin.dashboard.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.admin.logs.domain.ApiErrorLogs;
import synapse.dementia.domain.admin.logs.domain.ApiSuccessLogs;
import synapse.dementia.domain.admin.logs.service.LogsService;
import synapse.dementia.domain.admin.member.dto.MemberDto;
import synapse.dementia.domain.admin.member.dto.request.DeleteMemberDto;
import synapse.dementia.domain.admin.member.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class DashBoardController {
	private final MemberService memberService;
	private final LogsService logsService;

	@GetMapping()
	public String getDashboard(
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

		return "admin/dashboard";
	}

	@PostMapping("/dashboard/delete")
	public String deleteUsers(@RequestBody DeleteMemberDto deleteMemberDto, RedirectAttributes redirectAttributes) {
		try {
			memberService.deleteUsers(deleteMemberDto);
			redirectAttributes.addFlashAttribute("message", "사용자가 성공적으로 삭제되었습니다.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "사용자 삭제 중 오류가 발생했습니다.");
		}
		return "redirect:/admin/dashboard";
	}
}
