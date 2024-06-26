package synapse.dementia.domain.users.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import synapse.dementia.domain.users.member.domain.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByNickName(String nickName);
}
