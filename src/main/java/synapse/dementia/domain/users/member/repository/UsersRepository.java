package synapse.dementia.domain.users.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import synapse.dementia.domain.users.member.domain.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByNickName(String nickName);

	@Query("SELECT u FROM Users u WHERE u.nickName = :nickName AND u.deleted = false")
	Optional<Users> findByNickNameAndDeletedFalse(@Param("nickName") String nickName);
}