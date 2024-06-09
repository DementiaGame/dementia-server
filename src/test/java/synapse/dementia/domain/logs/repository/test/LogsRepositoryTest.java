package synapse.dementia.domain.logs.repository.test;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import synapse.dementia.domain.logs.domain.Logs;
import synapse.dementia.domain.logs.repository.LogsRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class LogsRepositoryTest {
	@Autowired
	private LogsRepository logsRepository;

	@Test
	@DisplayName("로그 저장")
	public void 로그_저장() {
		// given
		final Logs log = Logs.builder()
			.serverIp("192.168.0.1")
			.requestURL("http://example.com/api")
			.requestMethod("GET")
			.responseStatus(200)
			.clientIp("192.168.0.100")
			.request("request data")
			.response("response data")
			.requestTime(LocalDateTime.now())
			.responseTime(LocalDateTime.now().plusSeconds(1))
			.build();

		// when
		final Logs result = logsRepository.save(log);

		// then
		assertThat(result.getLogsSeq()).isNotNull();
		assertThat(result.getServerIp()).isEqualTo("192.168.0.1");
		assertThat(result.getRequestURL()).isEqualTo("http://example.com/api");
		assertThat(result.getRequestMethod()).isEqualTo("GET");
		assertThat(result.getResponseStatus()).isEqualTo(200);
		assertThat(result.getClientIp()).isEqualTo("192.168.0.100");
		assertThat(result.getRequest()).isEqualTo("request data");
		assertThat(result.getResponse()).isEqualTo("response data");
		assertThat(result.getRequestTime()).isNotNull();
		assertThat(result.getResponseTime()).isNotNull();
	}
}
