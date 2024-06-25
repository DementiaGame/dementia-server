package synapse.dementia.domain.users.game.initialgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synapse.dementia.domain.users.member.domain.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
}
