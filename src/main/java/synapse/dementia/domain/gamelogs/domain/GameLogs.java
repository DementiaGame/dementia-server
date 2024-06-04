package synapse.dementia.domain.gamelogs.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "game_logs")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Builder(toBuilder = true)
public class GameLogs extends BaseEntity {
	@Id
	@Column(name = "game_logs_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gameLogsSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_seq")
	private Users users;

	@Column(name = "request_api", nullable = false)
	private String requestAPI;

	@Column(name = "api_access_time", nullable = false)
	private LocalDateTime apiAccessTime;

	@Column(name = "ip_address", nullable = false)
	private String ipAddress;

	@Column(name = "dtype", nullable = false)
	@Enumerated(EnumType.STRING)
	private DTYPE dtype;
}
