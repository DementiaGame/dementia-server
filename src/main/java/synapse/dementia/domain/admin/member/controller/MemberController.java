package synapse.dementia.domain.admin.member.controller;

	/*
	 댓글 삭제 후 /admin/users HTML 파일로 redirect 하는 과정에서 405 Method 오류 발생
	 redirect 할 때 DELETE 메소드롤 요청을 보내는 문제가 발생했는데

	 관리자모드 Response Header에 PUT, GET 메서드만 허용하기 때문에
	 PostMapping 으로 구현
	*/import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public ResponseEntity<String> deleteUsers(@RequestBody DeleteMemberDto deleteMemberDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		headers.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));

		try {
			memberService.deleteUsers(deleteMemberDto);
			return new ResponseEntity<>("사용자가 성공적으로 삭제되었습니다.", headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("사용자 삭제 중 오류가 발생했습니다.", headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/edit")
	public ResponseEntity<String> modifyUsers(@RequestBody ModifyMemberDto modifyMemberDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		headers.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));

		try {
			memberService.modifyUsers(modifyMemberDto);
			return ResponseEntity.ok("사용자 정보가 성공적으로 수정되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("사용자 정보 수정 중 오류가 발생했습니다.");
		}
	}
}
