package synapse.dementia.domain.users.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
