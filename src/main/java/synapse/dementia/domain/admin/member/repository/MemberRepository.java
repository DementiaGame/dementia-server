package synapse.dementia.domain.admin.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import synapse.dementia.domain.users.member.domain.Users;

@Repository
public interface MemberRepository extends JpaRepository<Users, Long> {

}
