package synapse.dementia.domain.auth.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import synapse.dementia.domain.auth.domain.CustomUserDetails;
import synapse.dementia.domain.users.domain.Users;
import synapse.dementia.domain.users.repository.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	private final UsersRepository usersRepository;

	public UserDetailsServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.usersRepository = usersRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
		Optional<Users> user = usersRepository.findByNickName(nickName);
		user.orElseThrow(() -> new UsernameNotFoundException("not found user:" + nickName));

		CustomUserDetails customUserDetails = new CustomUserDetails(user.get());

		return customUserDetails;
	}
}
