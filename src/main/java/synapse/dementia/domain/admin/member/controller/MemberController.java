package synapse.dementia.domain.admin.member.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.admin.member.dto.MemberDto;
import synapse.dementia.domain.admin.member.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class MemberController {
	private final MemberService memberService;

	@GetMapping("/users")
	public String getAllUsersLogs(Model model) {
		List<MemberDto> users = memberService.findAllUsers();
		model.addAttribute("users", users);

		return "admin/users";
	}

}
