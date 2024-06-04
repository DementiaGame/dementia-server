package synapse.dementia.domain.userlogs.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synapse.dementia.domain.users.domain.Users;
import synapse.dementia.global.domain.BaseEntity;

@Entity
@Table(name = "users_logs")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Builder(toBuilder = true)
public class UsersLogs extends BaseEntity {

	@Id
	@Column(name = "users_logs_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usersLogSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_seq")
	private Users users;

	@Column(name = "auth_time", nullable = false)
	private LocalDateTime authTime;

	@Column(name = "ip_address", nullable = false)
	private String ipAddress;

}
