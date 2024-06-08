package synapse.dementia.domain.userlogs.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/users/log")
public class UsersLogApi {

	@GetMapping()
	public String basicController() {
		return "logs";
	}
}
