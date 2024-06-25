package synapse.dementia.domain.users.member.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import synapse.dementia.domain.users.member.dto.request.UsersSignUpReq;
import synapse.dementia.domain.users.member.dto.response.UsersSignUpRes;
import synapse.dementia.domain.users.member.service.UsersService;
import synapse.dementia.global.base.BaseResponse;

@RestController
@RequestMapping("/users")
public class UsersController {
	private final UsersService usersService;

	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}

	@PostMapping("/signUp")
	public BaseResponse signUp(@Validated @RequestBody UsersSignUpReq dto) {
		UsersSignUpRes usersSignUpRes = usersService.signUp(dto);
		BaseResponse<UsersSignUpRes> response = BaseResponse.ofSuccess(usersSignUpRes);

		return response;
	}
}
