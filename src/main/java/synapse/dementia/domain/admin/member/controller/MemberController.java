package synapse.dementia.domain.admin.member.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.admin.member.dto.MemberDto;
import synapse.dementia.domain.admin.member.dto.request.DeleteMemberDto;
import synapse.dementia.domain.admin.member.dto.request.ModifyMemberDto;
import synapse.dementia.domain.admin.member.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class MemberController {
	private final MemberService memberService;

	@GetMapping()
	public String getAllUsersLogs(Model model) {
		List<MemberDto> users = memberService.findAllUsers();
		model.addAttribute("users", users);

		return "admin/users";
	}

	/*
	 댓글 삭제 후 /admin/users HTML 파일로 redirect 하는 과정에서 405 Method 오류 발생
	 redirect 할 때 DELETE 메소드롤 요청을 보내는 문제가 발생했는데

	 관리자모드 Response Header에 PUT, GET 메서드만 허용하기 때문에
	 PostMapping 으로 구현
	*/
	@PostMapping("/delete")
	public String deleteUsers(@RequestBody DeleteMemberDto deleteMemberDto, RedirectAttributes redirectAttributes) {
		try {
			memberService.deleteUsers(deleteMemberDto);
			redirectAttributes.addFlashAttribute("message", "사용자가 성공적으로 삭제되었습니다.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "사용자 삭제 중 오류가 발생했습니다.");
		}
		return "redirect:/admin/users";
	}

	@PostMapping("/edit")
	public String modifyUsers(@RequestBody ModifyMemberDto modifyMemberDto, RedirectAttributes redirectAttributes) {
		try {
			memberService.modifyUsers(modifyMemberDto);
			redirectAttributes.addFlashAttribute("message", "사용자 정보가 성공적으로 수정되었습니다.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "사용자 정보 수정 중 오류가 발생했습니다.");
		}
		return "redirect:/admin/users";
	}
}
