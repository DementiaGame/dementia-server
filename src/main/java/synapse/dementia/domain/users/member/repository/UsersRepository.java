package synapse.dementia.domain.users.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import synapse.dementia.domain.users.member.domain.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByNickName(String nickName);
}
