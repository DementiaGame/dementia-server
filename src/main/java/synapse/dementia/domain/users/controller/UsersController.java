package synapse.dementia.domain.users.controller;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import synapse.dementia.domain.users.dto.request.UsersInfoUpdateReq;
import synapse.dementia.domain.users.dto.request.UsersSignUpReq;
import synapse.dementia.domain.users.dto.response.UsersInfoRes;
import synapse.dementia.domain.users.dto.response.UsersSignUpRes;
import synapse.dementia.domain.users.service.UsersService;
import synapse.dementia.global.base.BaseResponse;

@RestController
@RequestMapping("/users")
public class UsersController {
	private final UsersService usersService;

	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}

	@PostMapping("/signup")
	public BaseResponse signUp(@Validated @RequestBody UsersSignUpReq dto) {
		UsersSignUpRes usersSignUpRes = usersService.signUp(dto);
		BaseResponse<UsersSignUpRes> response = BaseResponse.ofSuccess(usersSignUpRes);

		return response;
	}

	@GetMapping()
	public BaseResponse getUserInfo() {
		UsersInfoRes usersInfoRes = usersService.findUser();
		BaseResponse<UsersInfoRes> response = BaseResponse.ofSuccess(usersInfoRes);

		return response;
	}

	@PutMapping()
	public BaseResponse updateUserInfo(@Validated @RequestBody UsersInfoUpdateReq dto) {
		UsersInfoRes usersInfoRes = usersService.updateUser(dto);
		BaseResponse<UsersInfoRes> response = BaseResponse.ofSuccess(usersInfoRes);

		return response;
	}

	@DeleteMapping()
	public BaseResponse deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Users의 deleted만 true로 변경
		usersService.deletedUser();
		//usersService.updateUser();

		// 현재 세션에서 로그아웃 처리
		// JSESSIONID 쿠키는 그대로 있지만 비활성된 세션
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		new SecurityContextLogoutHandler().logout(request, response, authentication);

		//response.sendRedirect("http://localhost:8080/auth/signout");
		return BaseResponse.ofSuccess("delete success");
	}
}
