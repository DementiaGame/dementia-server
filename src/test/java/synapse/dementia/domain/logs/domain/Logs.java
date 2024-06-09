package synapse.dementia.domain.logs.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "logs")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Logs {
	@Id @Column(name = "logs_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long logsSeq;

	@Column(name = "server_ip")
	private String serverIp;

	@Column(name = "request_url", length = 4096)
	private String requestURL;

	@Column(name = "request_method")
	private String requestMethod;

	@Column(name = "response_status")
	private Integer responseStatus;

	@Column(name = "client_ip")
	private String clientIp;

	@Column(name = "request", length = 4096)
	private String request;

	@Column(name = "response", length = 4096)
	private String response;

	@Column(name = "request_time")
	private LocalDateTime requestTime;

	@Column(name = "response_time")
	private LocalDateTime responseTime;
}
